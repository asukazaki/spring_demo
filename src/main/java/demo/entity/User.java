package demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="user")
	private JobShift jobShift;
}
