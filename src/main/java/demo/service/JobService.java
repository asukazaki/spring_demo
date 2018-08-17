package demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.bean.JobInputBean;
import demo.bean.JobOutputBean;
import demo.entity.Holiday;
import demo.entity.Job;
import demo.entity.JobPk;
import demo.entity.User;
import demo.repository.HolidayRepository;
import demo.repository.JobsRepository;
import demo.repository.UserRepository;
import demo.util.constance.JobStatus;

@Service
@Transactional
public class JobService {

	private final String DELIMITER_DATE = "/";

	@Autowired
	JobsRepository jobsRepository;	
	@Autowired
	UserRepository userRepository;
	@Autowired
	HolidayRepository holidayRepository;

	public JobOutputBean getJobsPerMonth(int id, String year, String month){

		StringBuilder sb = new StringBuilder(year);
		sb.append(DELIMITER_DATE);
		sb.append(month);	
		//		sb.append("%");
		List<Job>list = jobsRepository.findJobsPerMonth(id, sb.toString());
		//		List<Job>list = jobsRepository.findByIdAndDateLike(id, sb.toString());	

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
		List<Holiday>holidays = holidayRepository.findByMonth(month);
		for(Holiday holiday : holidays) {
			if(map.containsKey(holiday.getDay())) {
				Job job = map.get(holiday.getDay());
				job.setDayOfWeek(job.getDayOfWeek() + "(" + holiday.getName() + ")");
				job.setHolydayName(holiday.getName());
				job.setHolydayStatus(3);
				map.put(holiday.getDay(), job);
			}
		}
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
			map.put(j.getDate().substring(j.getDate().length()-2, j.getDate().length()), j);
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
				setDayOfWeek(job,dayOfWeek);
				map.put(dayKey, job);
			} else {
				// listにない
				Job job = new Job();
				job.setId(id);
				job.setDate(year + DELIMITER_DATE + month + DELIMITER_DATE + dayKey);
				//				String dayOfWeekString = getDayOfWeekByString(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), i).getDayOfWeek());
				DayOfWeek dayOfWeek = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), i).getDayOfWeek();
				setDayOfWeek(job,dayOfWeek);
				map.put(dayKey, job);
			}
		}

		return map;
	}

	private void setDayOfWeek(Job job, DayOfWeek dayOfWeek) {
		switch (dayOfWeek) {
		case MONDAY :
			job.setDayOfWeek("月");
			job.setHolydayStatus(0);
			return;
		case TUESDAY:
			job.setDayOfWeek("火");
			job.setHolydayStatus(0);
			return;
		case WEDNESDAY:
			job.setDayOfWeek("水");
			job.setHolydayStatus(0);
			return;
		case THURSDAY:
			job.setDayOfWeek("木");
			job.setHolydayStatus(0);
			return;
		case FRIDAY:
			job.setDayOfWeek("金");
			job.setHolydayStatus(0);
			return;
		case SATURDAY:
			job.setDayOfWeek("土");
			job.setHolydayStatus(1);
			return;
		case SUNDAY:
			job.setDayOfWeek("日");
			job.setHolydayStatus(2);
			return;
		}

	}

	/*
	 * 曜日（数値）を曜日（文字列）に変換
	 */
	private String getDayOfWeekByString(DayOfWeek dayOfWeek) {
		switch (dayOfWeek) {
		case MONDAY :
			return "月";
		case TUESDAY:
			return "火";
		case WEDNESDAY:
			return "水";
		case THURSDAY:
			return "木";
		case FRIDAY:
			return "金";
		case SATURDAY:
			return "土";
		case SUNDAY:
			return "日";
		}
		return null;
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
		String usersShiftStartTime = user.getShiftStartTime();
		String usersShiftEndTime = user.getShiftEndTime();

		List<Job> resultList = new ArrayList<>();
		Double overTimePerMonth = 0D;

		String endDay;
		if(month.equals(String.format("%2d", LocalDate.now().getMonth().getValue()))) {
			endDay = String.format("%02d", LocalDate.now().getDayOfMonth());
		}else {
			endDay = String.format("%02d", LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1).lengthOfMonth());
		}
//		 現在の日付を取得
//		String todayOfMonth = String.format("%02d", LocalDate.now().getDayOfMonth());

		// なんかいけてない…
		SimpleDateFormat toDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat toStringFormat = new SimpleDateFormat("yyyy/MM/dd");
		for(Map.Entry<String, Job> entry :map.entrySet()) {
			// 月初めから現在の日付まで
			if(entry.getKey().compareTo(endDay) > 0) {
				break;
			}

			Job job = entry.getValue();
			Date dateTo = null;
			Date dateFrom = null;

			// 打刻時間
			String startTime = job.getStartTime();

			// 打刻されているか
			if(startTime == null || startTime.isEmpty()) {
				// シフトのある日か
				// TODO: holydayStatusでいい？
				if(job.getHolydayStatus() != 0) {
					job.setJobStateCode(JobStatus.BRANK.getCode());
					job.setJobStateName(JobStatus.BRANK.name());
				}else {
					job.setJobStateCode(JobStatus.NORMAL.getCode());
				}
				resultList.add(job);
				continue;
			}

			// シフト変更届けあるか
			if(job.getTmpShiftStartTime() !=null && !job.getTmpShiftStartTime().isEmpty()) {
				usersShiftStartTime = job.getTmpShiftStartTime();
			}

			// 遅刻か
			if(usersShiftStartTime.compareTo(job.getStartTime()) < 0) {
				job.setJobStateCode(JobStatus.LATE.getCode());
				job.setJobStateName(JobStatus.LATE.name());
			} else {
				job.setJobStateCode(JobStatus.NORMAL.getCode());
			}

			// TODO:休日出勤	
			if(job.getHolydayStatus() != 0) {
				
			}

			// TODO:総労働時間、残業時間、休憩時間を計算
			try {
				dateTo = toDateFormat.parse(job.getDate() + " " + job.getStartTime());
				dateFrom = toDateFormat.parse(job.getDate() + " " + job.getEndTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Double workPerDayByHour =  ((double)(dateFrom.getTime() - dateTo.getTime()) / (1000 * 60 * 60));
			job.setWorkPerDay(workPerDayByHour.toString());
			
			// TODO: stab---------------------------
			job.setRestPerDay(1);
			Double overTimePerDay = workPerDayByHour - 8D;
			job.setOverTimePerDay(String.valueOf(overTimePerDay));
			overTimePerMonth += overTimePerDay;
			// ---------------------------------------

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

	public List<Job> findById(int id) {
		return jobsRepository.getAllById(id);
	}

	public Job createJob(int id, String year, String month, String day, JobInputBean input) {

		JobPk key = new JobPk();
		key.setId(id);

		StringBuilder sb = new StringBuilder(year);
		sb.append(DELIMITER_DATE);
		sb.append(month);
		sb.append(DELIMITER_DATE);
		sb.append(day);
		key.setDate(sb.toString());

		Optional<Job> jobOpt = jobsRepository.findById(key);

		Job job = new Job();
		if(jobOpt.isPresent()) {
			job = jobOpt.get();
			// json body からきた情報を追加
			// job.set...
		} else {					
			job.setId(id);
			job.setDate(sb.toString());
			// ...
		}

		return jobsRepository.save(job);
	}

}
