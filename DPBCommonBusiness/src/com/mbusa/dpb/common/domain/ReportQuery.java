/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportQuery.java
 * Program Version			: 1.0
 * Program Description		: This class is used as Bean class For Report Query Def. Operations.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 01, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.domain;

import java.util.Date;

public class ReportQuery {
	
	private int reportQueryId;
	private String reportQueryName;
	private String description;
	private String query;
	private String status;
	private int id;
	private String creationDate;
	private String lastUpdtDate;
	private String creationUser;
	private String lastUser;
	private boolean flagActive;
	private int NoOfLines;
	private boolean flagDraft;
	private String user;
	private boolean flagInActive;
	private String showStat;
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getLastUpdtDate() {
		return lastUpdtDate;
	}
	public void setLastUpdtDate(String lastUpdtDate) {
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
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public String getShowStat() {
		return showStat;
	}
	public void setShowStat(String showStat) {
		this.showStat = showStat;
	}
	public boolean isFlagInActive() {
		return flagInActive;
	}
	public void setFlagInActive(boolean flagInActive) {
		this.flagInActive = flagInActive;
	}
	

}
