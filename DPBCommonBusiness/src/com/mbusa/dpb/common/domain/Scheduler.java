package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class Scheduler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String scheduleCode;
	private String scheduleName;
	
	public String getScheduleCode() {
		return scheduleCode;
	}
	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

}
