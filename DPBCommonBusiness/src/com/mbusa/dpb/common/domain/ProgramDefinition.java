/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ProgramDefinition.java
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
package com.mbusa.dpb.common.domain;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.mbusa.dpb.common.constant.IConstants;

/**
 * 
 * @author AH33812
 *	ProgramDefinitionBean class defines the mappings for Dealer Definition
 */
public class ProgramDefinition extends DpbProcess{
	
	public ProgramDefinition() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private static final long serialVersionUID = 1L;
	private int programId;
	private String programName;
	private java.sql.Date startDate;
	private java.sql.Date endDate;
	private String commissionPayment; // to confirm
	private boolean maxVariablePayment; // to confirm
	private boolean isSpecialProgram;
	private List<String> vehicleType;
	private String kpiValue;
	
	private int kpiId;
	private Condition blockCondition;
	private List<String> condition;
	private int accountId;
	private String programStatus; // To confirm - Alekhya
	private String description; // to confirm
	private String specialProgram;
	private java.sql.Time variableTime;
	private String variableTimeFormat;
	private java.sql.Time fixedTime;
	private String fixedTimeFormat;
	private double fixedPayment; // to confirm
	private double variablePayment;
	private String fixedPymtSchedule;
	private String fixedPymtTrigger;
	private String variablePymtSchedule;
	private String variablePymtTrigger;
	private String paymentType;
	private boolean flagActive;
	private String rebateAmt;
	private String commPayment;
	//private double commPercent;
	private double commAmount;
	private String maxVarPayment;
	private String variablePymtBonusSchedule;
	private String variablePymtBonusTrigger;
	private String fixedPymtBonusSchedule;
	private String fixedPymtBonusTrigger;

	private String comsnProcessSchedule;
	private String comsnProcessTrigger;
	private String comsnPymtSchedule;
	private String comsnPymtTrigger;
	
	private String dpbProcess;
	private List<ConditionDefinition> masterCondList = new ArrayList<ConditionDefinition>();
	private List<String> messages;
	private Date inactiveDate;
	public String getDpbProcess() {
		return dpbProcess;
	}
	public void setDpbProcess(String dpbProcess) {
		this.dpbProcess = dpbProcess;
	}
	/**
	 * @return the variablePymtBonusSchedule
	 */
	public String getVariablePymtBonusSchedule() {
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
		return variablePymtBonusTrigger;
	}
	/**
	 * @param variablePymtBonusTrigger the variablePymtBonusTrigger to set
	 */
	public void setVariablePymtBonusTrigger(String variablePymtBonusTrigger) {
		this.variablePymtBonusTrigger = variablePymtBonusTrigger;
	}
	/**
	 * @return the fixedPymtBonusSchedule
	 */
	public String getFixedPymtBonusSchedule() {
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
		return fixedPymtBonusTrigger;
	}
	/**
	 * @param fixedPymtBonusTrigger the fixedPymtBonusTrigger to set
	 */
	public void setFixedPymtBonusTrigger(String fixedPymtBonusTrigger) {
		this.fixedPymtBonusTrigger = fixedPymtBonusTrigger;
	}


	public List<ConditionDefinition> getMasterCondList() {
		
		return masterCondList;
	}
	public void setMasterCondList(List<ConditionDefinition> masterCondList) {
		this.masterCondList = masterCondList;
	}
	public String getMaxVarPayment() {
		return maxVarPayment;
	}
	public void setMaxVarPayment(String maxVarPayment) {
		this.maxVarPayment = maxVarPayment;
	}
	public String getCommPayment() {
		return commPayment;
	}
	public void setCommPayment(String commPayment) {
		this.commPayment = commPayment;
	}
/*	public double getCommPercent() {
		return commPercent;
	}
	public void setCommPercent(double commPercent) {
		this.commPercent = commPercent;
	}*/
	public double getCommAmount() {
		return commAmount;
	}
	public void setCommAmount(double commAmount) {
		this.commAmount = commAmount;
	}
	public String getRebateAmt() {
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
			return "";
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
	public java.sql.Date getStartDate() {
		return startDate;
	}
	public void setStartDate(java.sql.Date startDate) {
		this.startDate = startDate;
	}
	public java.sql.Date getEndDate() {
		return endDate;
	}
	public void setEndDate(java.sql.Date endDate) {
		this.endDate = endDate;
	}
	public String getCommissionPayment() {
		return commissionPayment;
	}
	public void setCommissionPayment(String commissionPayment) {
		this.commissionPayment = commissionPayment;
	}
	
	
	public boolean isMaxVariablePayment() {
		return maxVariablePayment;
	}
	public void setMaxVariablePayment(boolean maxVariablePayment) {
		this.maxVariablePayment = maxVariablePayment;
	}
		
	public boolean isSpecialProgram() {		
		return specialProgram != null && specialProgram.equalsIgnoreCase(IConstants.PROGRAM_TYPE_SPECIAL) ? true: false;		
		
	}
	public void setSpecialProgram(boolean isSpecialProgram) {
		this.isSpecialProgram = isSpecialProgram;
	}
	public List<String> getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(List<String> vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	public Condition getBlockCondition() {
		return blockCondition;
	}
	public void setBlockCondition(Condition blockCondition) {
		this.blockCondition = blockCondition;
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
	public java.sql.Time getFixedTime() {
		return fixedTime;
	}
	public void setFixedTime(java.sql.Time fixedTime) {
		this.fixedTime = fixedTime;
	}
	public double getFixedPayment() {
		return fixedPayment;
	}
	public void setFixedPayment(double fixedPayment) {
		this.fixedPayment = fixedPayment;
	}
	public double getVariablePayment() {
		return variablePayment;
	}
	public void setVariablePayment(double variablePayment) {
		this.variablePayment = variablePayment;
	}
	public String getFixedPymtSchedule() {
		return fixedPymtSchedule;
	}
	public void setFixedPymtSchedule(String fixedPymtSchedule) {
		this.fixedPymtSchedule = fixedPymtSchedule;
	}
	public String getFixedPymtTrigger() {
		return fixedPymtTrigger;
	}
	public void setFixedPymtTrigger(String fixedPymtTrigger) {
		this.fixedPymtTrigger = fixedPymtTrigger;
	}
	public String getVariablePymtSchedule() {
		return variablePymtSchedule;
	}
	public void setVariablePymtSchedule(String variablePymtSchedule) {
		this.variablePymtSchedule = variablePymtSchedule;
	}
	public String getVariablePymtTrigger() {
		return variablePymtTrigger;
	}
	public void setVariablePymtTrigger(String variablePymtTrigger) {
		this.variablePymtTrigger = variablePymtTrigger;
	}
	public java.sql.Time getVariableTime() {
		return variableTime;
	}
	public void setVariableTime(java.sql.Time variableTime) {
		this.variableTime = variableTime;
	}
	public String getVariableTimeFormat() {
		return variableTimeFormat;
	}
	public void setVariableTimeFormat(String variableTimeFormat) {
		this.variableTimeFormat = variableTimeFormat;
	}
	
	public String getFixedTimeFormat() {
		return fixedTimeFormat;
	}
	public void setFixedTimeFormat(String fixedTimeFormat) {
		this.fixedTimeFormat = fixedTimeFormat;
	}
	/**
	 * @return the messages
	 */
	public List<String> getMessages() {
		return messages;
	}
	/**
	 * @param messages the messages to set
	 */
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	public String getComsnProcessSchedule() {
		return comsnProcessSchedule;
	}
	public void setComsnProcessSchedule(String comsnProcessSchedule) {
		this.comsnProcessSchedule = comsnProcessSchedule;
	}
	public String getComsnProcessTrigger() {
		return comsnProcessTrigger;
	}
	public void setComsnProcessTrigger(String comsnProcessTrigger) {
		this.comsnProcessTrigger = comsnProcessTrigger;
	}
	public String getComsnPymtSchedule() {
		return comsnPymtSchedule;
	}
	public void setComsnPymtSchedule(String comsnPymtSchedule) {
		this.comsnPymtSchedule = comsnPymtSchedule;
	}
	public String getComsnPymtTrigger() {
		return comsnPymtTrigger;
	}
	public void setComsnPymtTrigger(String comsnPymtTrigger) {
		this.comsnPymtTrigger = comsnPymtTrigger;
	}
	public Date getInactiveDate() {
		return inactiveDate;
	}
	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}
}
