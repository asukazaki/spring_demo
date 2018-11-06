package demo.bean;


import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class JobInputBean {

	@Pattern(regexp = "^[0-9]{2}:[0-9]{2}:[0-9]{2}$")
	private String startTime;
	
	private String endTime;
	private String restStartTime;
	private String restEndTime;
	
	// 打刻タイプ
	private String dakokuType;
	
	private String jobStateCode;
	private String jobStateName;
}
