package com.mbusa.dpb.common.domain;


import java.io.Serializable;
import java.util.Currency;
import java.util.Date;

/**
 * 
 * @author AH33812
 *	ProgramDefinitionBean class defines the variables for Dealer Definition
 */
public class ProgramDefinitionBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int programId;
	private String programName;
	private Date startDate;
	private Date endDate;
	private String commissionPayment; // to confirm
	private Currency fixedPayment; // to confirm
	private boolean maxVariablePayment; // to confirm
	private Currency variablePayment;	
	private boolean isSpecialProgram;
	private VehicleType vehicleType;
	private String KPI;
	private String KPIValue;
	private int accountId;
	private String programStatus; // To confirm - Alekhya
	private String description; // to confirm
	

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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCommissionPayment() {
		return commissionPayment;
	}
	public void setCommissionPayment(String commissionPayment) {
		this.commissionPayment = commissionPayment;
	}
	public Currency getFixedPayment() {
		return fixedPayment;
	}
	public void setFixedPayment(Currency fixedPayment) {
		this.fixedPayment = fixedPayment;
	}
	
	public boolean isMaxVariablePayment() {
		return maxVariablePayment;
	}
	public void setMaxVariablePayment(boolean maxVariablePayment) {
		this.maxVariablePayment = maxVariablePayment;
	}
	public Currency getVariablePayment() {
		return variablePayment;
	}
	public void setVariablePayment(Currency variablePayment) {
		this.variablePayment = variablePayment;
	}
	
	public boolean isSpecialProgram() {
		return isSpecialProgram;
	}
	public void setSpecialProgram(boolean isSpecialProgram) {
		this.isSpecialProgram = isSpecialProgram;
	}
	public VehicleType getVehicleType() {
		if(vehicleType == null){
			vehicleType = new VehicleType();
		}
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getKPI() {
		return KPI;
	}
	public void setKPI(String kPI) {
		KPI = kPI;
	}
	
	public String getKPIValue() {
		return KPIValue;
	}
	public void setKPIValue(String kPIValue) {
		KPIValue = kPIValue;
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
	public String toString(){
		
		return "programName:\n"+getProgramName()+":Desc:\n"+getDescription()+"StartDate:\n"+getStartDate()+"End Date"+getEndDate()+"KPI:"+getKPI()
				+"Status"+getProgramStatus()+"KPI-Value"+getKPIValue()+"Commision Payment"+getCommissionPayment()+"Fixed Payment"+getFixedPayment()+"Variable Payment"+getVariablePayment();
	}
}
