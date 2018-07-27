package demo.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
		
		// 残業時間、総労働時間計算
		JobOutputBean result = calcOverTime(id,year,month,map);

		return result;		
	}

	/**
	 * ０埋め日付」がkeyのHashMapを作る（ついでに曜日を入れる）
	 * @param id
	 * @param year
	 * @param month
	 * @param list
	 * @return
	 */
	private Map<String, Job> fillInTheBlank(int id, String year, String month,List<Job> list) {
		Map<String, Job> map = new HashMap<>();
		for(Job j : list) {
			map.put(j.getDate().substring(j.getDate().length()-2, j.getDate().length()), j);
		}
		
		// 月の最終日取得
		int lengthOfMonth = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1).lengthOfMonth();
		
		// 「０埋め日付」がkeyのHashMapを作る（ついでに曜日を入れる）
		for(int i = 1; i < lengthOfMonth+1; i ++) {
			String dayKey = String.format("%02d", i);
			
			// listにある
			if(map.containsKey(dayKey)) {
				Job job = map.get(dayKey);
				String dayOfWeekString = getDayOfWeekByString(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), i).getDayOfWeek());
				job.setDayOfWeek(dayOfWeekString);
				map.put(dayKey, job);
			} else {
				// listにない
				Job job = new Job();
				job.setId(id);
				job.setDate(year + DELIMITER_DATE + month + DELIMITER_DATE + dayKey);
				String dayOfWeekString = getDayOfWeekByString(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), i).getDayOfWeek());
				job.setDayOfWeek(dayOfWeekString);
				map.put(dayKey, job);
			}
		}
		
		return map;
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
		String usersShiftStartTime = user.getJobShift().getShiftStartTime();
		
		// 祝日参照
		List<Holiday>holidays = holidayRepository.findByMonth(month);
		for(Holiday holiday : holidays) {
			if(map.containsKey(holiday.getDay())) {
				Job job = map.get(holiday.getDay());
				job.setDayOfWeek(job.getDayOfWeek() + "(" + holiday.getName() + ")");
				map.put(holiday.getDay(), job);
			}
		}
		
		List<Job> resultList = new ArrayList<>();
		Double overTimePerMonth = 0D;
		for(Map.Entry<String, Job> entry :map.entrySet()) {
			Job job = entry.getValue();
			
			// TODO:遅刻かどうかチェック
			// stab
			job.setJobStateCode("0");
			job.setApprovalFlag(0);
			
			// TODO:総労働時間、残業時間、休憩時間を計算
			job.setWorkPerDay(9);
			job.setRestPerDay(1);
			job.setOverTimePerDay(1);
			overTimePerMonth += 1;		
			
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
