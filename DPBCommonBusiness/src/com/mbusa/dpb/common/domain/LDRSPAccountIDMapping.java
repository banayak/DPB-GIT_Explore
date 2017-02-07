package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class LDRSPAccountIDMapping implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String conditionName;
	private String conditiontype;
	private String variableName;
	private String conditionDesc;
	private String checkValue;	
	private String isBlock;
	private String regularExp;	
	private String description;
	private String status;	
	private String pcCustExpid;
	

	public String getPcCustExpid() {
		return pcCustExpid;
	}

	public void setPcCustExpid(String pcCustExpid) {
		this.pcCustExpid = pcCustExpid;
	}

	public int getId() {
		return id;
	}
	
	public String getConditiontype() {
		return conditiontype;
	}

	public void setConditiontype(String conditiontype) {
		this.conditiontype = conditiontype;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getConditionName() {
		return conditionName;
	}
	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public String getCheckValue() {
		return checkValue;
	}
	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	
	public String getRegularExp() {
		return regularExp;
	}
	public void setRegularExp(String regularExp) {
		this.regularExp = regularExp;
	}	
	public String getIsBlock() {
		return isBlock;
	}
	public void setIsBlock(String isBlock) {
		this.isBlock = isBlock;
	}
	public String getConditionDesc() {
		return conditionDesc;
	}

	public void setConditionDesc(String conditionDesc) {
		this.conditionDesc = conditionDesc;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
