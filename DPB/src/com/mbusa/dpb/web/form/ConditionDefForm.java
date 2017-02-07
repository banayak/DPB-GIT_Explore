package com.mbusa.dpb.web.form;

import java.sql.Timestamp;
public class ConditionDefForm {
	
	private int id;
	private String conditionName;
	private String condition;
	private String variableName;
	private String conditionDesc;
	private String checkValue;	
	private String conditionType;
	private String regularExp;	
	private String status;	
	private String creaUserId;
	private String lstUpdateUser;
	private Timestamp lstDateUpdate;
	private Timestamp creaDate;
	private boolean flagActive;
	private String statusChange;
	
	/**
	 * @return the statusChange
	 */
	public String getStatusChange() {
		return statusChange;
	}
	/**
	 * @param statusChange the statusChange to set
	 */
	public void setStatusChange(String statusChange) {
		this.statusChange = statusChange;
	}
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
	public int getId() {
		return id;
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
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public String getConditionDesc() {
		return conditionDesc;
	}
	public void setConditionDesc(String conditionDesc) {
		this.conditionDesc = conditionDesc;
	}
	public String getCheckValue() {
		return checkValue;
	}
	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	public String getConditionType() {
		return conditionType;
	}
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}
	public String getRegularExp() {
		return regularExp;
	}
	public void setRegularExp(String regularExp) {
		this.regularExp = regularExp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreaUserId() {
		return creaUserId;
	}
	public void setCreaUserId(String creaUserId) {
		this.creaUserId = creaUserId;
	}
	public String getLstUpdateUser() {
		return lstUpdateUser;
	}
	public void setLstUpdateUser(String lstUpdateUser) {
		this.lstUpdateUser = lstUpdateUser;
	}
	public Timestamp getLstDateUpdate() {
		return lstDateUpdate;
	}
	public void setLstDateUpdate(Timestamp lstDateUpdate) {
		this.lstDateUpdate = lstDateUpdate;
	}
	public Timestamp getCreaDate() {
		return creaDate;
	}
	public void setCreaDate(Timestamp creaDate) {
		this.creaDate = creaDate;
	}
}
	
