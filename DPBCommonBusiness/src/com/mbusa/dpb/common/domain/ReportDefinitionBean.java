/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportDefinitionBean.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle report definition data.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 14, 2013     	  1.0        First Draft
 * Syntel   Aug 27, 2013     	  1.0        Done changes as per new requirement
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author RK5005820
 *
 */
public class ReportDefinitionBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int reportDefId;
	private String reportName;
	private String description;
	private int subReports;
	private String scheduleCode;
	private String triggerCode;
	private String defStatusCode;
	private Map<String, String> reportContentList;
	private Map<String, String> reportQueryList;
	private List<QCRelationBean> qcrList;
	private boolean flagActive;
	private Timestamp updatedDt;
	private Timestamp createdDt;
	private String createdByUser;
	private String updatedByUser;
	private String flgDailyMonthly;
	private int reportLpp;
	
	
	public boolean isFlagActive() {
		return flagActive;
	}
	public void setFlagActive(boolean flagActive) {
		this.flagActive = flagActive;
	}
	public int getSubReports() {
		return subReports;
	}
	public void setSubReports(int subReports) {
		this.subReports = subReports;
	}
	public List<QCRelationBean> getQcrList() {
		if(qcrList == null){
			qcrList = new ArrayList<QCRelationBean>();
		}
		return qcrList;
	}
	public void setQcrList(List<QCRelationBean> qcrList) {
		this.qcrList = qcrList;
	}
	/**
	 * @return the reportDefId
	 */
	public int getReportDefId() {
		return reportDefId;
	}
	/**
	 * @param reportDefId the reportDefId to set
	 */
	public void setReportDefId(int reportDefId) {
		this.reportDefId = reportDefId;
	}
	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}
	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the reportContentList
	 */
	public Map<String, String> getReportContentList() {
		return reportContentList;
	}
	/**
	 * @param reportContentList the reportContentList to set
	 */
	public void setReportContentList(Map<String, String> reportContentList) {
		this.reportContentList = reportContentList;
	}
	/**
	 * @return the reportQueryList
	 */
	public Map<String, String> getReportQueryList() {
		return reportQueryList;
	}
	/**
	 * @param reportQueryList the reportQueryList to set
	 */
	public void setReportQueryList(Map<String, String> reportQueryList) {
		this.reportQueryList = reportQueryList;
	}
	/**
	 * @return the updatedDt
	 */
	public Timestamp getUpdatedDt() {
		return updatedDt;
	}
	/**
	 * @param updatedDt the updatedDt to set
	 */
	public void setUpdatedDt(Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}
	/**
	 * @return the createdDt
	 */
	public Timestamp getCreatedDt() {
		return createdDt;
	}
	/**
	 * @param createdDt the createdDt to set
	 */
	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}
	/**
	 * @return the createdByUser
	 */
	public String getCreatedByUser() {
		return createdByUser;
	}
	/**
	 * @param createdByUser the createdByUser to set
	 */
	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
	}
	/**
	 * @return the updatedByUser
	 */
	public String getUpdatedByUser() {
		return updatedByUser;
	}
	/**
	 * @return the flgDailyMonthly
	 */
	public String getFlgDailyMonthly() {
		return flgDailyMonthly;
	}
	/**
	 * @param flgDailyMonthly the flgDailyMonthly to set
	 */
	public void setFlgDailyMonthly(String flgDailyMonthly) {
		this.flgDailyMonthly = flgDailyMonthly;
	}
	/**
	 * @param updatedByUser the updatedByUser to set
	 */
	public void setUpdatedByUser(String updatedByUser) {
		this.updatedByUser = updatedByUser;
	}
	/**
	 * @return the scheduleCode
	 */
	public String getScheduleCode() {
		return scheduleCode;
	}
	/**
	 * @param scheduleCode the scheduleCode to set
	 */
	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}
	/**
	 * @return the triggerCode
	 */
	public String getTriggerCode() {
		return triggerCode;
	}
	/**
	 * @param triggerCode the triggerCode to set
	 */
	public void setTriggerCode(String triggerCode) {
		this.triggerCode = triggerCode;
	}
	/**
	 * @return the defStatusCode
	 */
	public String getDefStatusCode() {
		return defStatusCode;
	}
	/**
	 * @param defStatusCode the defStatusCode to set
	 */
	public void setDefStatusCode(String defStatusCode) {
		this.defStatusCode = defStatusCode;
	}
	public int getReportLpp() {
		return reportLpp;
	}
	public void setReportLpp(int reportLpp) {
		this.reportLpp = reportLpp;
	}
	
}
