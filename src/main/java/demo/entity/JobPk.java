package demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;

@Data
public class JobPk implements Serializable{


	private Integer id;
	private String date;
}
