package demo.bean;

import java.util.List;

import demo.entity.Job;
import lombok.Data;

@Data
public class JobOutputBean {

	private int id;
	private Double monthOverTime;
	private List<Job> jobList;
}
