package com.mbusa.dpb.common.domain;


import java.io.Serializable;
import java.util.Calendar;

public class DealerProgramBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9145374723791848127L;
	private String dealerProgramName;
	private String description;
	private String startDate;
	private Calendar endDate;
	private String fixedPymntSchedule;
	private String fixedPaymentTrigger;
	private String variablePaymentSchedule;
	private String variablePaymentTrigger;
	private String kpi;
	private Integer kpiValue;
	private String applicableVehicles;
	private String programStatus;
	public String getDealerProgramName() {
		return dealerProgramName;
	}
	public void setDealerProgramName(String dealerProgramName) {
		this.dealerProgramName = dealerProgramName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Calendar getEndDate() {
		return endDate;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	public String getFixedPymntSchedule() {
		return fixedPymntSchedule;
	}
	public void setFixedPymntSchedule(String fixedPymntSchedule) {
		this.fixedPymntSchedule = fixedPymntSchedule;
	}
	public String getFixedTrigger() {
		return fixedPaymentTrigger;
	}
	public void setFixedTrigger(String fixedTrigger) {
		this.fixedPaymentTrigger = fixedTrigger;
	}
	public String getVariablePaymentSchedule() {
		return variablePaymentSchedule;
	}
	public void setVariablePaymentSchedule(String variablePaymentSchedule) {
		this.variablePaymentSchedule = variablePaymentSchedule;
	}
	public String getVariablePaymentTrigger() {
		return variablePaymentTrigger;
	}
	public void setVariablePaymentTrigger(String variablePaymentTrigger) {
		this.variablePaymentTrigger = variablePaymentTrigger;
	}
	public String getKpi() {
		return kpi;
	}
	public void setKpi(String kpi) {
		this.kpi = kpi;
	}
	public Integer getKpiValue() {
		return kpiValue;
	}
	public void setKpiValue(Integer kpiValue) {
		this.kpiValue = kpiValue;
	}
	public String getApplicableVehicles() {
		return applicableVehicles;
	}
	public void setApplicableVehicles(String applicableVehicles) {
		this.applicableVehicles = applicableVehicles;
	}
	public String getProgramStatus() {
		return programStatus;
	}
	public void setProgramStatus(String programStatus) {
		this.programStatus = programStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
