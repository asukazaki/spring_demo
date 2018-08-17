package demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Table(name = "jobs")
@Entity
@Data
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
	private String restStartTime;
	
	@Column (name="rest_end_time", nullable=false)
	private String restEndTime;
	
	/**
	 * -3 未打刻 or エラー
	 * -2 欠勤
	 * −1　遅刻
	 * 0 デフォルト
	 * 1　半休
	 * 2  有給
	 * ３ その他
	 * 
	 */
	@Column (name="job_state_code", nullable=false)
	private int jobStateCode;
	
	@Column (name="job_state_name", nullable=false)
	private String jobStateName;
	
	@Column (name="tmp_shift_start_time", nullable=false)
	private String tmpShiftStartTime;
	
	/*
	 * -1 未申請
	 * 0 デフォルト
	 * 1　申請済み
	 * 2 承認済み
	 */
	@Column (name="approval_falg", nullable=true)
	private int approvalFlag;
	
	@Transient
	private String dayOfWeek;
	
	@Transient
	private int holydayStatus;
	
	@Transient
	private String holydayName;
	
	@Transient
	private String workPerDay;
	
	@Transient 
	private int restPerDay;
	
	@Transient 
	private String overTimePerDay;
	
	
	
}
