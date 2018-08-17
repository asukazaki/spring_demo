//package demo.entity;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonRootName;
//
//import lombok.Data;
//
//@Table(name = "job_shift")
//@Entity
//@Data
//@JsonRootName(value = "jobShift")
//public class JobShift {
//
//	@Id
//	@Column (name="id")
//	private Integer id;
//	
//	@Column (name="shift_start_time", nullable=false)
//	private String shiftStartTime;
//	
//	@JsonIgnore
//	@OneToOne
//	@JoinColumn(name="id")
//	private User user;
//}
