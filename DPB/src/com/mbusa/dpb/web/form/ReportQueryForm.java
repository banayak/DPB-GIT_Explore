/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportQueryForm.java
 * Program Version			: 1.0
 * Program Description		: This class is used to get/Set Report Query Def. Form Fields.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 01, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.form;

import java.util.Date;

public class ReportQueryForm {
	
	private int reportQueryId;
	public String reportQueryName;
	private String description;
	private String query;
	private String status;
	private int id;
	private int rqIdHiiden;
	private Date creationDate;
	private Date lastUpdtDate;
	private String creationUser;
	private String lastUser;
	private boolean flagActive;
	private int NoOfLines;
	private boolean flagDraft;
	private String user;
	private boolean flagInActive;
	private String [] desc;
	private String [] arrquery;
	private String showStat;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getReportQueryName() {
		return reportQueryName;
	}
	public void setReportQueryName(String reportQueryName) {
		this.reportQueryName = reportQueryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getReportQueryId() {
		return reportQueryId;
	}
	public void setReportQueryId(int reportQueryId) {
		this.reportQueryId = reportQueryId;
	}
	public int getRqIdHiiden() {
		return rqIdHiiden;
	}
	public void setRqIdHiiden(int rqIdHiiden) {
		this.rqIdHiiden = rqIdHiiden;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastUpdtDate() {
		return lastUpdtDate;
	}
	public void setLastUpdtDate(Date lastUpdtDate) {
		this.lastUpdtDate = lastUpdtDate;
	}
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	public String getLastUser() {
		return lastUser;
	}
	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}
	public boolean isFlagActive() {
		return flagActive;
	}
	public void setFlagActive(boolean flagActive) {
		this.flagActive = flagActive;
	}
	public int getNoOfLines() {
		return NoOfLines;
	}
	public void setNoOfLines(int noOfLines) {
		NoOfLines = noOfLines;
	}
	public boolean isFlagDraft() {
		return flagDraft;
	}
	public void setFlagDraft(boolean flagDraft) {
		this.flagDraft = flagDraft;
	}
	public String getShowStat() {
		return showStat;
	}
	public void setShowStat(String showStat) {
		this.showStat = showStat;
	}
	public String getUser() {
		return user;
	}
	
	public boolean isFlagInActive() {
		return flagInActive;
	}
	public void setFlagInActive(boolean flagInActive) {
		this.flagInActive = flagInActive;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String[] getDesc() {
		return desc;
	}
	public void setDesc(String[] desc) {
		this.desc = desc;
	}
	public String[] getArrquery() {
		return arrquery;
	}
	public void setArrquery(String[] arrquery) {
		this.arrquery = arrquery;
	}

}
