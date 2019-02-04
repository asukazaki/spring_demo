package demo.entity;

import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Table(name = "user")
@Entity
@Data
public class User {

	@Id
	@Column (name="id")
	@JsonProperty("id")
	private Integer id;
	
	@Column (name="user_name", nullable=false)
	private String userName;
	
	@Column (name="shift_start_time", nullable=false)
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime shiftStartTime;
		
	@Column (name="shift_end_time", nullable=false)
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime shiftEndTime;
	
//	@OneToOne(cascade = CascadeType.ALL, mappedBy="user")
//	private JobShift jobShift;
}
