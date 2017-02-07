package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class Condition implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public boolean isBlock() {
		return isBlock;
	}
	public void setBlock(boolean isBlock) {
		this.isBlock = isBlock;
	}
	public String getRegularExp() {
		return regularExp;
	}
	public void setRegularExp(String regularExp) {
		this.regularExp = regularExp;
	}
	public Condition getParent() {
		return parent;
	}
	public void setParent(Condition parent) {
		this.parent = parent;
	}
	public boolean isParent() {
		return isParent;
	}
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	private Integer id;
	private String conditionName;
	private String variableName;
	private String checkValue;
	private boolean isBlock;
	
	private String regularExp;
	private Condition parent;
	private boolean isParent;

}
