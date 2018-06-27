package demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Table(name = "jobs")
@Entity
@Data
//@JsonFormat
@IdClass(value=JobPk.class)
public class Job {

	@Id
	@Column (name="id")
	private Integer id;
	
	@Id
	@Column (name="date", nullable=false)
	private String date;
	
	@Column (name="start_time", nullable=false)
	private String startTime;
	
	@Column (name="end_time", nullable=false)
	private String endTime;
	
	@Column (name="rest_start_time", nullable=false)
	private String restStratTime;
	
	@Column (name="rest_end_time", nullable=false)
	private String restEndTime;
	
	@Column (name="job_state_code", nullable=false)
	private String jobStateCode;
	
	@Column (name="job_state_name", nullable=false)
	private String jobStateName;
}
