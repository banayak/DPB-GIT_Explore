/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ProgramDefinitionForm.java
 * Program Version			: 1.0
 * Program Description		: 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 27, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/

package com.mbusa.dpb.web.form;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.ConditionDefinition;

/*
 * 
 */
public class ProgramDefinitionForm {
	private static final long serialVersionUID = 1L;
	private int programId;
	private String programName;
	private String startDate;
	private String endDate;
	private List<String> vehicleType =  new ArrayList<String>();
	private String kpiValue;
	
	private int kpiId;
	private List <String>condition = new ArrayList<String>();
	private int accountId;
	private String programStatus;
	private String description; 
	private String specialProgram;
	private double fixedPayment; 
	private double variablePayment;
	
	private String comsnProcessSchedule;
	private String comsnProcessTrigger;

	private String comsnPymtSchedule;
	private String comsnPymtTrigger;
	
	private String fixedPymtSchedule;
	private String fixedPymtTrigger;

	private String fixedPymtBonusSchedule;
	private String fixedPymtBonusTrigger;
	
	private String variablePymtSchedule;
	private String variablePymtTrigger;
	
	private String variablePymtBonusSchedule;
	private String variablePymtBonusTrigger;
	
	private String paymentType;
	private boolean flagActive;
	private String rebateAmt = null;
	private String commPayment = null;
	//private double commPercent;
	private double commAmount;
	private String maxVarPayment = null;
	private String inactiveDate;
	private List<ConditionDefinition> masterCondList = new ArrayList<ConditionDefinition>();
	public List<ConditionDefinition> getMasterCondList() {
		return masterCondList;
	}
	public void setMasterCondList(List<ConditionDefinition> masterCondList) {
		this.masterCondList = masterCondList;
	}
	public String getMaxVarPayment() {
		if(maxVarPayment==null){
			return IConstants.CONSTANT_N;
		}
		return maxVarPayment;
	}
	public void setMaxVarPayment(String maxVarPayment) {
		this.maxVarPayment = maxVarPayment;
	}
	public String getCommPayment() {
		if(commPayment==null){
			return IConstants.CONSTANT_N;
		}
		return commPayment;
	}
	public void setCommPayment(String commPayment) {
		this.commPayment = commPayment;
	}
/*	public double getCommPercent() {
		return commPercent;
	}
	public void setCommPercent(double d) {
		this.commPercent = d;
	}*/
	public double getCommAmount() {
		return commAmount;
	}
	public void setCommAmount(double d) {
		this.commAmount = d;
	}
	public String getRebateAmt() {
		if(rebateAmt==null){
			return IConstants.CONSTANT_N;
		}
		return rebateAmt;
	}
	public void setRebateAmt(String rebateAmt) {
		this.rebateAmt = rebateAmt;
	}
	public boolean isFlagActive() {
		return flagActive;
	}
	public void setFlagActive(boolean flagActive) {
		this.flagActive = flagActive;
	}

	public String getPaymentType() {
		if(paymentType==null){
			paymentType=IConstants.CONSTANT_N;
		}
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getSpecialProgram() {
		return specialProgram;
	}
	public void setSpecialProgram(String specialProgram) {
		this.specialProgram = specialProgram;
	}

	public String getDescription() {
		if(description==null){
			description=IConstants.EMPTY_STR;
		}
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getProgramId() {
		return programId;
	}
	public void setProgramId(int programId) {
		this.programId = programId;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
		
	public List<String> getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(List<String> vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	public List<String> getCondition() {
		return condition;
	}
	public void setCondition(List<String> condition) {
		this.condition = condition;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getProgramStatus() {
		return programStatus;
	}
	public void setProgramStatus(String programStatus) {
		this.programStatus = programStatus;
	}
	public String getKpiValue() {
		return kpiValue;
	}
	public void setKpiValue(String kpiValue) {
		this.kpiValue = kpiValue;
	}
	public int getKpiId() {
		return kpiId;
	}
	public void setKpiId(int kpiId) {
		this.kpiId = kpiId;
	}
	public double getFixedPayment() {
		return fixedPayment;
	}
	public void setFixedPayment(Double fixedPayment) {
		this.fixedPayment = fixedPayment;
	}
	public double getVariablePayment() {
		return variablePayment;
	}
	public void setVariablePayment(Double variablePayment) {
		this.variablePayment = variablePayment;
	}
	public String getFixedPymtSchedule() {
		if(fixedPymtSchedule==null){
			return IConstants.SCHD_YEARLY;
		}
		return fixedPymtSchedule;
	}
	public void setFixedPymtSchedule(String fixedPymtSchedule) {
		this.fixedPymtSchedule = fixedPymtSchedule;
	}
	public String getFixedPymtTrigger() {
		if(fixedPymtTrigger==null){
			return IConstants.TRIGGER_SYSTEM_INITIATION;
		}
		return fixedPymtTrigger;
	}
	public void setFixedPymtTrigger(String fixedPymtTrigger) {
		this.fixedPymtTrigger = fixedPymtTrigger;
	}
	public String getVariablePymtSchedule() {
		if(variablePymtSchedule==null){
			return IConstants.SCHD_YEARLY;
		}
		return variablePymtSchedule;
	}
	public void setVariablePymtSchedule(String variablePymtSchedule) {
		this.variablePymtSchedule = variablePymtSchedule;
	}
	public String getVariablePymtTrigger() {
		if(variablePymtTrigger==null){
			return IConstants.TRIGGER_SYSTEM_INITIATION;
		}
		return variablePymtTrigger;
	}
	public void setVariablePymtTrigger(String variablePymtTrigger) {
		this.variablePymtTrigger = variablePymtTrigger;
	}
	/**
	 * @return the fixedPymtBonusSchedule
	 */
	public String getFixedPymtBonusSchedule() {
		if(fixedPymtBonusSchedule==null){
			return IConstants.SCHD_YEARLY;
		}
		return fixedPymtBonusSchedule;
	}
	/**
	 * @param fixedPymtBonusSchedule the fixedPymtBonusSchedule to set
	 */
	public void setFixedPymtBonusSchedule(String fixedPymtBonusSchedule) {
		this.fixedPymtBonusSchedule = fixedPymtBonusSchedule;
	}
	/**
	 * @return the fixedPymtBonusTrigger
	 */
	public String getFixedPymtBonusTrigger() {
		if(fixedPymtBonusTrigger==null){
			return IConstants.TRIGGER_SYSTEM_INITIATION;
		}
		return fixedPymtBonusTrigger;
	}
	/**
	 * @param fixedPymtBonusTrigger the fixedPymtBonusTrigger to set
	 */
	public void setFixedPymtBonusTrigger(String fixedPymtBonusTrigger) {
		this.fixedPymtBonusTrigger = fixedPymtBonusTrigger;
	}
	/**
	 * @return the variablePymtBonusSchedule
	 */
	public String getVariablePymtBonusSchedule() {
		if(variablePymtBonusSchedule==null){
			return IConstants.SCHD_YEARLY;
		}
		return variablePymtBonusSchedule;
	}
	/**
	 * @param variablePymtBonusSchedule the variablePymtBonusSchedule to set
	 */
	public void setVariablePymtBonusSchedule(String variablePymtBonusSchedule) {
		this.variablePymtBonusSchedule = variablePymtBonusSchedule;
	}
	/**
	 * @return the variablePymtBonusTrigger
	 */
	public String getVariablePymtBonusTrigger() {
		if(variablePymtBonusTrigger==null){
			return IConstants.TRIGGER_SYSTEM_INITIATION;
		}
		return variablePymtBonusTrigger;
	}
	/**
	 * @param variablePymtBonusTrigger the variablePymtBonusTrigger to set
	 */
	public void setVariablePymtBonusTrigger(String variablePymtBonusTrigger) {
		this.variablePymtBonusTrigger = variablePymtBonusTrigger;
	}
	public String getComsnProcessSchedule() {
		if(comsnProcessSchedule == null){
			return IConstants.SCHD_YEARLY;
		}
		return comsnProcessSchedule;
	}
	public void setComsnProcessSchedule(String comsnProcessSchedule) {
		this.comsnProcessSchedule = comsnProcessSchedule;
	}
	public String getComsnProcessTrigger() {
		if(comsnProcessTrigger == null){
			return IConstants.TRIGGER_SYSTEM_INITIATION;
		}
		return comsnProcessTrigger;
	}
	public void setComsnProcessTrigger(String comsnProcessTrigger) {
		this.comsnProcessTrigger = comsnProcessTrigger;
	}
	public String getComsnPymtSchedule() {
		if(comsnPymtSchedule == null){
			return IConstants.SCHD_YEARLY;
		}
		return comsnPymtSchedule;
	}
	public void setComsnPymtSchedule(String comsnPymtSchedule) {
		this.comsnPymtSchedule = comsnPymtSchedule;
	}
	public String getComsnPymtTrigger() {
		if(comsnPymtTrigger == null){
			return IConstants.TRIGGER_SYSTEM_INITIATION;
		}
		return comsnPymtTrigger;
	}
	public void setComsnPymtTrigger(String comsnPymtTrigger) {
		this.comsnPymtTrigger = comsnPymtTrigger;
	}
	/**
	 * @return the inactiveDate
	 */
	public String getInactiveDate() {
		return inactiveDate;
	}
	/**
	 * @param inactiveDate the inactiveDate to set
	 */
	public void setInactiveDate(String inactiveDate) {
		this.inactiveDate = inactiveDate;
	}
}
