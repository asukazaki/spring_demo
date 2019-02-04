package demo.entity;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Table(name = "jobs")
@Entity
@Data
@IdClass(value=JobPk.class)
public class Job {

	@Version
	@Column(name="version")
	private Timestamp version;
	
	@Id
	@Column (name="id")
	private Integer id;
	
	@Id
	@Column (name="date", nullable=false)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@JsonFormat(pattern = "yyyy/MM/dd")
	private LocalDate date;
	
	@Column (name="start_time", nullable=false)
//	@DateTimeFormat(pattern = "HH:mm")
	@JsonFormat(pattern = "HH:mm")
	private LocalTime startTime;
	
	@Column (name="end_time", nullable=false)
	@DateTimeFormat(pattern = "HH:mm")
	@JsonFormat(pattern = "HH:mm")
	private LocalTime endTime;
	
	@Column (name="rest_start_time", nullable=false)
	@DateTimeFormat(pattern = "HH:mm")
	@JsonFormat(pattern = "HH:mm")
	private LocalTime restStartTime;
	
	@Column (name="rest_end_time", nullable=false)
	@DateTimeFormat(pattern = "HH:mm")
	@JsonFormat(pattern = "HH:mm")
	private LocalTime restEndTime;
	
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
	@Column (name="approval_flag", nullable=true)
	private int approvalFlag;
	
//	@Transient
//	private String dayOfWeek;
	
	@Transient
	private String dayOfWeekDisplayName;
	
	// TODO: DayOfWeek型でjsonをintにしたい。。
	@Transient
	private int dayOfWeek;
	
	@Transient
	private boolean isHoliday;
	@Transient
	private String holydayName;
	
	@Transient
	private boolean isFutureDate;
	
	@Transient
	private boolean isDakokuError;
	@Transient
	private List<String> errorMessages;
	
	@Transient
	private String workPerDay;
	
	@Transient 
	private int restPerDay;
	
	@Transient 
	private String overTimePerDay;
	
	public void addErrorMessage(String message) {
		this.errorMessages.add(message);
	}
	
}
