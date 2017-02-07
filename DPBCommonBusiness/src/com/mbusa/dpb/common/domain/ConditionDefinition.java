/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				:ConditionDefinition.java
 * Program Version			: 1.0
 * Program Description		: This class is used to hold  condition data.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel    July 16, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.domain;

import java.io.Serializable;

import java.sql.Timestamp;


public class ConditionDefinition implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String conditionName;	
	private String conditionDesc;
	private String condition;
	private String variableName;	
	private String checkValue;	
	private String conditionType;
	private String regularExp;		
	private String status;	
	private String currentUser;
	private boolean flagActive;
	
	/**
	 * @return the flagActive
	 */
	public boolean isFlagActive() {
		return flagActive;
	}
	/**
	 * @param flagActive the flagActive to set
	 */
	public void setFlagActive(boolean flagActive) {
		this.flagActive = flagActive;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the conditionName
	 */
	public String getConditionName() {
		return conditionName;
	}
	/**
	 * @param conditionName the conditionName to set
	 */
	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}
	/**
	 * @return the conditionDesc
	 */
	public String getConditionDesc() {
		return conditionDesc;
	}
	/**
	 * @param conditionDesc the conditionDesc to set
	 */
	public void setConditionDesc(String conditionDesc) {
		this.conditionDesc = conditionDesc;
	}
	/**
	 * @return the condtion
	 */
	public String getCondition() {
		if(condition!= null && condition.length() > 0){
			return condition.trim();
		}
		return condition;
	}
	/**
	 * @param condtion the condtion to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	/**
	 * @return the variableName
	 */
	public String getVariableName() {
		return variableName;
	}
	/**
	 * @param variableName the variableName to set
	 */
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	/**
	 * @return the checkValue
	 */
	public String getCheckValue() {
		return checkValue;
	}
	/**
	 * @param checkValue the checkValue to set
	 */
	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	/**
	 * @return the conditiontype
	 */
	public String getConditionType() {
		return conditionType;
	}
	/**
	 * @param conditiontype the conditiontype to set
	 */
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}
	/**
	 * @return the regularExp
	 */
	public String getRegularExp() {
		return regularExp;
	}
	/**
	 * @param regularExp the regularExp to set
	 */
	public void setRegularExp(String regularExp) {
		this.regularExp = regularExp;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the currentUser
	 */
	public String getCurrentUser() {
		return currentUser;
	}
	/**
	 * @param currentUser the currentUser to set
	 */
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}		
}
