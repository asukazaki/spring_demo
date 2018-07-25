package demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "job_shift")
@Entity
@Data
public class JobShift {

	@Id
	@Column (name="id")
	private Integer id;
	
	@Column (name="shift_start_time", nullable=false)
	private String shiftStartTime;
	
	@OneToOne
	@JoinColumn(name="id")
	private User user;
}
