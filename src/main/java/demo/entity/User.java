package demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "user")
@Entity
@Data
public class User {

	@Id
	@Column (name="id")
	private Integer id;
	
	@Column (name="user_name", nullable=false)
	private String userName;
	
}
