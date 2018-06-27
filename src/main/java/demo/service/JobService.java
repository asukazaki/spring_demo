package demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.bean.JobInputBean;
import demo.entity.Job;
import demo.entity.JobPk;
import demo.repository.JobsRepository;

@Service
@Transactional
public class JobService {

	private final String DELIMITER_DATE = "/";
	
	@Autowired
	JobsRepository jobsRepository;
	
	public List<Job> getJobsPerMonth(int id, String year, String month){
		
		StringBuilder sb = new StringBuilder(year);
		sb.append(DELIMITER_DATE);
		sb.append(month);	
//		sb.append("%");
		List<Job>list = jobsRepository.findJobsPerMonth(id, sb.toString());
//		List<Job>list = jobsRepository.findByIdAndDateLike(id, sb.toString());	
		
		/*
		 * 残業時間計算したり、、
		 */
		
		return list;		
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
