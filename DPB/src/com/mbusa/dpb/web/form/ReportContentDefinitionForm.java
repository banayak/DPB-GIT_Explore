/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportContentDefinitionForm.java
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
package com.mbusa.dpb.web.form;

public class ReportContentDefinitionForm {
	
	private int reportContentDefId;
	private String reportContentName;
	private String reportContentDescription;
	private String reportContent;
	private String defStatusCode;
	private String[] content;
	private String[] description;
	private int qryRsltLpp;
	private boolean flagActive;
	private String status;
	
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
	public int getQryRsltLpp() {
		return qryRsltLpp;
	}
	public void setQryRsltLpp(int qryRsltLpp) {
		this.qryRsltLpp = qryRsltLpp;
	}
	public boolean isFlagActive() {
		return flagActive;
	}
	public void setFlagActive(boolean flagActive) {
		this.flagActive = flagActive;
	}
	public String[] getContent() {
		return content;
	}
	public void setContent(String[] content) {
		this.content = content;
	}
	public String[] getDescription() {
		return description;
	}
	public void setDescription(String[] description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
