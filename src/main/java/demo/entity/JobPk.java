package demo.entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import lombok.Data;

@Data
public class JobPk implements Serializable{


	public JobPk(Integer id, LocalDate date) {
		this.id = id;
		this.date = date;
	}
	public JobPk() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private LocalDate date;
}
