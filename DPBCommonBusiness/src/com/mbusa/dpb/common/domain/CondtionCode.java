package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class CondtionCode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String conditionCode;
	private String conditionName;
	public String getConditionCode() {
		return conditionCode;
	}
	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}
	public String getConditionName() {
		return conditionName;
	}
	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}
}
