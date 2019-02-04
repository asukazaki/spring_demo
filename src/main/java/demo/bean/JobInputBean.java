package demo.bean;


import java.time.LocalTime;

import javax.validation.constraints.Pattern;

import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class JobInputBean {

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime restStartTime;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime restEndTime;
	
	// 打刻タイプ
	private String dakokuType;
	
	private String jobStateCode;
	private String jobStateName;
}
