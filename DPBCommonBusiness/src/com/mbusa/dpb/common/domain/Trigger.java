package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class Trigger implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String triggerCode;
	private String triggerName;
	public String getTriggerCode() {
		return triggerCode;
	}
	public void setTriggerCode(String triggerCode) {
		this.triggerCode = triggerCode;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

}
