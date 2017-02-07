/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportDefinitionForm.java
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
package com.mbusa.dpb.web.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.common.domain.QCRelationBean;
//import com.mbusa.dpb.common.domain.ReportDefinitionBean;
import com.mbusa.dpb.web.helper.WebHelper;
/**
 * @author RK5005820
 *
 */
public class ReportDefinitionForm {

	private int reportDefId;
	private String reportName;
	private String description;
	private int subReports;
	private String scheduleCode;
	private String triggerCode;
	private String defStatusCode;
	private int reportLpp;
	private boolean flagActive;
	private String[] reportDescription;
	private Map<String, String> reportContentList;
	private Map<String, String> reportQueryList;
	private List<QCRelationBean> qcrList;
	private String status;
	
	
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
	 * @param reportContentList the reportContentList to set
	 */
	public void setReportContentList(Map<String, String> reportContentList) {
		this.reportContentList = reportContentList;
	}
	
	public Map<String, String> getReportContentList(){
		return reportContentList;
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
	public boolean isFlagActive() {
		return flagActive;
	}
	public void setFlagActive(boolean flagActive) {
		this.flagActive = flagActive;
	}
	public String[] getReportDescription() {
		return reportDescription;
	}
	public void setReportDescription(String[] reportDescription) {
		this.reportDescription = reportDescription;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
