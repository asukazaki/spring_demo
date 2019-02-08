package demo.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.bean.JobInputBean;
import demo.bean.JobOutputBean;
//import demo.entity.Holiday;
import demo.entity.Job;
import demo.entity.JobPk;
import demo.entity.User;
import demo.repository.JobsRepository;
import demo.repository.UserRepository;
import demo.util.constance.JobStatus;
import demo.utils.time.HolidayDate;

@Service
@Transactional
public class JobService {

	private final String DELIMITER_DATE = "/";

	@Autowired
	JobsRepository jobsRepository;	
	@Autowired
	UserRepository userRepository;
	
	HolidayDate holidayDate = new HolidayDate();

	public JobOutputBean getJobsPerMonth(int id, String year, String month){

		StringBuilder sb = new StringBuilder(year);
		sb.append(DELIMITER_DATE);
		sb.append(month);	
		//		sb.append("%");
//		List<Job>list = jobsRepository.findJobsPerMonth(id, sb.toString());
		//		List<Job>list = jobsRepository.findByIdAndDateLike(id, sb.toString());	
		
		LocalDate targetMonthFirtstDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1);
		List<Job>list = jobsRepository.findByIdAndDateBetween(id, targetMonthFirtstDate, targetMonthFirtstDate.plusMonths(1).minusDays(1));

		/*
		 * 残業時間計算したり、、
		 */

		// 穴埋め試案１
		Map<String, Job> map = fillInTheBlank(id,year,month,list);

		// 祝日参照
		setHolyday(year,month,map);

		// 残業時間、総労働時間計算
		JobOutputBean result = calcOverTime(id,year,month,map);

		return result;		
	}

	private void setHolyday(String year, String month, Map<String, Job> map) {
		// 祝日参照
		try {
			List<String[]>targetMonthHolidays = holidayDate.getHolidayTargetMonth(year, month);
			if(!targetMonthHolidays.isEmpty()) {
				for(String[] holidayDateAndName : targetMonthHolidays) {
					Job job = map.get(holidayDateAndName[0]);
		        	job.setHoliday(true);
		        	job.setHolydayName(holidayDateAndName[1]);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//			Month ajdMonth = new Month(Integer.parseInt(year),Integer.parseInt(month));
//
//	    for (AJD day: ajdMonth.getDays()) {	        
//	        Holiday h = Holiday.getHoliday(day);
//	        if(h != null) {
//	        	Job job = map.get(String.format("%02d", day.getDay()));
//	        	job.setHoliday(true);
//	        	job.setHolydayName(h.getName(day));
//	        }
//	      }
//		} catch (NumberFormatException | AJDException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * ０埋め日付」がkeyのTreeMapを作る（ついでに曜日を入れる）
	 * @param id
	 * @param year
	 * @param month
	 * @param list
	 * @return
	 */
	private Map<String, Job> fillInTheBlank(int id, String year, String month,List<Job> list) {
		Map<String, Job> map = new TreeMap<>();
		for(Job j : list) {
			// 1~31
			int dayOfMonth = j.getDate().getDayOfMonth();
			map.put(String.format("%02d", dayOfMonth), j);
		}

		// 月の最終日取得
		int lengthOfMonth = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1).lengthOfMonth();

		// 「０埋め日付」がkeyのTreeMapを作る（ついでに曜日を入れる）
		for(int i = 1; i < lengthOfMonth+1; i ++) {
			String dayKey = String.format("%02d", i);

			// listにある
			if(map.containsKey(dayKey)) {
				Job job = map.get(dayKey);
				DayOfWeek dayOfWeek = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), i).getDayOfWeek();
				job.setDayOfWeek(dayOfWeek.getValue());
				job.setDayOfWeekDisplayName(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.JAPANESE));
				map.put(dayKey, job);
			} else {
				// listにない
				Job job = new Job();
				job.setId(id);
				LocalDate targetDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(dayKey));
				job.setDate(targetDate);
				
				DayOfWeek dayOfWeek = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), i).getDayOfWeek();
				job.setDayOfWeek(dayOfWeek.getValue());
				job.setDayOfWeekDisplayName(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.JAPANESE));
				if(targetDate.isAfter(LocalDate.now())) {
					job.setFutureDate(true);
				}
				map.put(dayKey, job);
			}
		}

		return map;
	}

	// TODO: ここはインターフェイスを実装するようにして
	/**
	 * 残業時間を計算する
	 * @param id
	 * @param year
	 * @param month
	 * @param map
	 * @return List<Job>を含むレスポンスビーン
	 */
	private JobOutputBean calcOverTime(int id, String year, String month, Map<String, Job> map) {

		// シフトタイム取得
		User user = userRepository.getOne(id);
		//		String usersShiftStartTime = user.getJobShift().getShiftStartTime();
		LocalTime usersShiftStartTime = user.getShiftStartTime();
//		LocalTime usersShiftEndTime = user.getShiftEndTime();

		List<Job> resultList = new ArrayList<>();
		Double overTimePerMonth = 0D;
		
		for(Map.Entry<String, Job> entry :map.entrySet()) {
			
			Job job = entry.getValue();
			
			// 現在日付より以前のものだけを計算対象
			if(job.isFutureDate()) {
				resultList.add(job);
				continue;
			}
			job.setErrorMessages(new ArrayList<String>());

			// 打刻時間
			LocalTime startTime = job.getStartTime();

			// 打刻されているか
			if(startTime == null) {
				// 土日祝日か
				if(isHoliday(job)) {
					job.setJobStateCode(JobStatus.NORMAL.getCode());
					job.setDakokuError(false);
				} else {
					// TODO: 未使用　（未打刻か遅刻かとかの判別）
					job.setJobStateCode(JobStatus.BRANK.getCode());
					job.setJobStateName(JobStatus.BRANK.name());
					
					job.setDakokuError(true);
					job.addErrorMessage("打刻されていません");
				}
				resultList.add(job);
				continue;
			}

			// シフト変更届けあるか
//			if(job.getTmpShiftStartTime() !=null && !job.getTmpShiftStartTime().isEmpty()) {
//				usersShiftStartTime = job.getTmpShiftStartTime();
//			}

			// 遅刻か
			// isAfter(),isBefore()は等しい時trueを返す
			if(job.getStartTime().isAfter(usersShiftStartTime)) {
				// TODO: 未使用　（遅刻、未打刻の判別用)
				job.setJobStateCode(JobStatus.LATE.getCode());
				job.setJobStateName(JobStatus.LATE.name());
				
				job.setDakokuError(true);
				job.addErrorMessage("遅刻です");
			} else {
				// iruka?
				job.setJobStateCode(JobStatus.NORMAL.getCode());
				job.setJobStateName(JobStatus.NORMAL.name());
			}
			
			// TODO:休日出勤	
//			if(job.getHolydayStatus() != 0) {
//				
//			}
			
			// 退勤時間、総労働時間
			
			// 当日
			if(job.getDate().getDayOfMonth() == LocalDate.now().getDayOfMonth()) {
				resultList.add(job);
				continue;
			}
			
			// 退勤時間あるか
			if(job.getEndTime() == null) {
				job.setDakokuError(true);
				job.addErrorMessage("退勤時間の打刻がありません");
				resultList.add(job);
				continue;
			}

			// 出勤時間　＜　退勤時間になっているか
			if (job.getStartTime() == null || job.getEndTime() == null) {
				continue;
			} else if(job.getStartTime().isAfter(job.getEndTime())) {
				job.setDakokuError(true);
				job.addErrorMessage("出勤時間が退勤時間より後になっています");
			}
			
			// 総労働時間
			// TODO: 休憩時間引くとか
			/*
			 * シフト労働時間　＞　総労働時間
			 * 　　-> 休憩時間１と計算。残業時間は単純な引き算でだす
			 * シフト労働時間　＜＝　総労働時間
			 * 　　->　休憩時間なし。残業時間０と計算
			 */
			Duration shiftTimeDuration = Duration.between(user.getShiftStartTime(), user.getShiftEndTime());
			
			Duration duration = Duration.between(job.getStartTime(), job.getEndTime());
			
			if(shiftTimeDuration.compareTo(duration) < 0) {
//				duration = duration.minusHours(1);
				Long overTimePerDay = duration.minus(shiftTimeDuration).toMinutes();
				Double overTimePerDayHour = (Double.valueOf(overTimePerDay) / 60D );
				job.setOverTimePerDay(String.format("%.1f",overTimePerDayHour));
				job.setRestPerDay(1);
				// 月の合計残業時間を一応だす
				overTimePerMonth += overTimePerDayHour;
			} else {
				// iruka ??
//				job.setRestPerDay(0);
				job.setOverTimePerDay("-");
			}
			Long durationMinutes = duration.toMinutes();
//			Double workPerDayByHour = Double.valueOf(String.format("%.1f", (Double.valueOf(durationMinutes) / 60D )));
			job.setWorkPerDay(String.format("%.1f", (Double.valueOf(durationMinutes) / 60D )));
			
			resultList.add(job);
		}
		
		// sort
		resultList.sort((a,b) -> a.getDate().compareTo(b.getDate()));
		//		resultList.sort(Comparator.comparing(Job::getDate));

		JobOutputBean result = new JobOutputBean();
		result.setId(id);
		result.setJobList(resultList);
		result.setMonthOverTime(overTimePerMonth);

		return result;
	}

	private boolean isHoliday(Job job) {
		if (job.getDayOfWeek() == DayOfWeek.SATURDAY.getValue() || job.getDayOfWeek() == DayOfWeek.SUNDAY.getValue()
				|| job.isHoliday()) {
			return true;
		}
		return false;
	}

	public List<Job> findById(int id) {
		return jobsRepository.getAllById(id);
	}

	public Job createJob(int id, String year, String month, String day, JobInputBean input) {

		JobPk key = new JobPk();
		key.setId(id);

		LocalDate todayDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
//		key.setDate(java.sql.Date.valueOf(todayDate));
		key.setDate(todayDate);

		Optional<Job> jobOpt = jobsRepository.findById(key);

		Job job = new Job();
		if(jobOpt.isPresent()) {
			job = jobOpt.get();
			// json body からきた情報を追加
			setDakoku(input,job);
		} else {					
			job.setId(id);
			job.setDate(todayDate);
			setDakoku(input,job);
			// ...
		}

		return jobsRepository.saveAndFlush(job);
	}

	private void setDakoku(JobInputBean input, Job job) {
		switch (input.getDakokuType()) {
		case "SYUKKIN":
			job.setStartTime(input.getStartTime());
			break;

		case "TAIKIN":
			job.setEndTime(input.getEndTime());
			break;
		default:
			break;
		}
		
	}

	public List<Job> updateJobs(int id, String year, String month, List<Job> input) {
		
		// Duplicate になってしまう。。
//		List<Job> resultList = jobsRepository.saveAll(input);
		
		// 1つずつアクセスして遅くないか？
		List<Job> updateList = new ArrayList<>();
		for(Job job : input) {			
			JobPk key = new JobPk(job.getId(),job.getDate());
			Optional<Job> jobOpt = jobsRepository.findById(key);
			if(jobOpt.isPresent()) {
				Job oldJob = jobOpt.get();
				
				// この時点でEntityManagerのPersistentContextは更新される。
				// 実際にDBが更新されるのはトランザクション終了時（デフォルトなのでメソッドが例外なく終わった時、多分)
				
				oldJob.setStartTime(job.getStartTime());
				oldJob.setEndTime(job.getEndTime());
				updateList.add(oldJob);
			} else {
				updateList.add(job);
			}
		}
//		try {
//			
//		} catch(){
//			
//		}
		List<Job> resultList = jobsRepository.saveAll(updateList);
		jobsRepository.flush();
		// TODO Auto-generated method stub
		return resultList;
	}
	

}
