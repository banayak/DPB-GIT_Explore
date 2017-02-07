/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportContentDefinitionBean.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle report content definition data.
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

public class ReportContentDefinitionBean implements Serializable{

	private static final long serialVersionUID = 1613747483590617972L;
	
	private int reportContentDefId;
	private String reportContentName;
	private String reportContentDescription;
	private String reportContent;
	private int qryRsltLpp;
	private String defStatusCode;
	private String reportFooter;
	private Timestamp updatedDt;
	private Timestamp createdDt;
	private String createdByUser;
	private String updatedByUser;
	/**
	 * @return the reportContentDefId
	 */
	public int getReportContentDefId() {
		return reportContentDefId;
	}
	/**
	 * @param reportContentDefId the reportContentDefId to set
	 */
	public void setReportContentDefId(int reportContentDefId) {
		this.reportContentDefId = reportContentDefId;
	}
	/**
	 * @return the reportContentName
	 */
	public String getReportContentName() {
		return reportContentName;
	}
	/**
	 * @param reportContentName the reportContentName to set
	 */
	public void setReportContentName(String reportContentName) {
		this.reportContentName = reportContentName;
	}
	/**
	 * @return the reportContentDescription
	 */
	public String getReportContentDescription() {
		return reportContentDescription;
	}
	/**
	 * @param reportContentDescription the reportContentDescription to set
	 */
	public void setReportContentDescription(String reportContentDescription) {
		this.reportContentDescription = reportContentDescription;
	}
	/**
	 * @return the reportContent
	 */
	public String getReportContent() {
		return reportContent;
	}
	/**
	 * @param reportContent the reportContent to set
	 */
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
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
	 * @param updatedByUser the updatedByUser to set
	 */
	public void setUpdatedByUser(String updatedByUser) {
		this.updatedByUser = updatedByUser;
	}
	public int getQryRsltLpp() {
		return qryRsltLpp;
	}
	public void setQryRsltLpp(int qryRsltLpp) {
		this.qryRsltLpp = qryRsltLpp;
	}
	public String getReportFooter() {
		return reportFooter;
	}
	public void setReportFooter(String reportFooter) {
		this.reportFooter = reportFooter;
	}

}
