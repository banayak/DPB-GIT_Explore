package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class ConditionType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String conditionTypeCode;
	private String conditionCodeName;
	public String getConditionTypeCode() {
		return conditionTypeCode;
	}
	public void setConditionTypeCode(String conditionTypeCode) {
		this.conditionTypeCode = conditionTypeCode;
	}
	public String getConditionCodeName() {
		return conditionCodeName;
	}
	public void setConditionCodeName(String conditionCodeName) {
		this.conditionCodeName = conditionCodeName;
	}

}
