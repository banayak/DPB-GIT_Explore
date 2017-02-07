/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: SchedulerProcess.java
 * Program Version			: 1.0
 * Program Description		: This domain class is used for store the process data for Scheduler
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Sep 10, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/

package com.mbusa.dpb.common.domain;

import java.sql.Date;
import java.sql.Time;

public class SchedulerProcess {
	
	private int procId;
	private String logStatus;
	private String logReasonTxt;
	//private int procDayId;
	//private int progId;
	//private int actProcDayId;
	private String procType;
	//private int overRtlId;
	private Time overProcTime;
	private String overTrigger;
	private String overReasonTxt;
	private Date procDate;
	private Date actProcDate;
	private Date overProcDate;
	private String lastUpdatedUser;
	private int procTypeSeq;
	private String finalTriggerSts;
	private int fileProgId;
	private int pgmProgId;
	private int ldrProgId;
	private int rptProgId;
	
	/**
	 * @return the procId
	 */
	public int getProcId() {
		return procId;
	}
	/**
	 * @param procId the procId to set
	 */
	public void setProcId(int procId) {
		this.procId = procId;
	}
	/**
	 * @return the logStatus
	 */
	public String getLogStatus() {
		return logStatus;
	}
	/**
	 * @param logStatus the logStatus to set
	 */
	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}
	/**
	 * @return the logReasonTxt
	 */
	public String getLogReasonTxt() {
		return logReasonTxt;
	}
	/**
	 * @param logReasonTxt the logReasonTxt to set
	 */
	public void setLogReasonTxt(String logReasonTxt) {
		this.logReasonTxt = logReasonTxt;
	}
	/**
	 * @return the procType
	 */
	public String getProcType() {
		return procType;
	}
	/**
	 * @param procType the procType to set
	 */
	public void setProcType(String procType) {
		this.procType = procType;
	}
	/**
	 * @return the overProcTime
	 */
	public Time getOverProcTime() {
		return overProcTime;
	}
	/**
	 * @param overProcTime the overProcTime to set
	 */
	public void setOverProcTime(Time overProcTime) {
		this.overProcTime = overProcTime;
	}
	/**
	 * @return the overTrigger
	 */
	public String getOverTrigger() {
		return overTrigger;
	}
	/**
	 * @param overTrigger the overTrigger to set
	 */
	public void setOverTrigger(String overTrigger) {
		this.overTrigger = overTrigger;
	}
	/**
	 * @return the overReasonTxt
	 */
	public String getOverReasonTxt() {
		return overReasonTxt;
	}
	/**
	 * @param overReasonTxt the overReasonTxt to set
	 */
	public void setOverReasonTxt(String overReasonTxt) {
		this.overReasonTxt = overReasonTxt;
	}
	/**
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return the procTypeSeq
	 */
	public int getProcTypeSeq() {
		return procTypeSeq;
	}
	/**
	 * @param procTypeSeq the procTypeSeq to set
	 */
	public void setProcTypeSeq(int procTypeSeq) {
		this.procTypeSeq = procTypeSeq;
	}
	/**
	 * @return the finalTriggerSts
	 */
	public String getFinalTriggerSts() {
		return finalTriggerSts;
	}
	/**
	 * @param finalTriggerSts the finalTriggerSts to set
	 */
	public void setFinalTriggerSts(String finalTriggerSts) {
		this.finalTriggerSts = finalTriggerSts;
	}
	/**
	 * @return the fileProgId
	 */
	public int getFileProgId() {
		return fileProgId;
	}
	/**
	 * @param fileProgId the fileProgId to set
	 */
	public void setFileProgId(int fileProgId) {
		this.fileProgId = fileProgId;
	}
	/**
	 * @return the pgmProgId
	 */
	public int getPgmProgId() {
		return pgmProgId;
	}
	/**
	 * @param pgmProgId the pgmProgId to set
	 */
	public void setPgmProgId(int pgmProgId) {
		this.pgmProgId = pgmProgId;
	}
	/**
	 * @return the rptProgId
	 */
	public int getRptProgId() {
		return rptProgId;
	}
	/**
	 * @param rptProgId the rptProgId to set
	 */
	public void setRptProgId(int rptProgId) {
		this.rptProgId = rptProgId;
	}
	/**
	 * @return the ldrProgId
	 */
	public int getLdrProgId() {
		return ldrProgId;
	}
	/**
	 * @param ldrProgId the ldrProgId to set
	 */
	public void setLdrProgId(int ldrProgId) {
		this.ldrProgId = ldrProgId;
	}
	/**
	 * @return the procDate
	 */
	public Date getProcDate() {
		return procDate;
	}
	/**
	 * @param procDate the procDate to set
	 */
	public void setProcDate(Date procDate) {
		this.procDate = procDate;
	}
	/**
	 * @return the actProcDate
	 */
	public Date getActProcDate() {
		return actProcDate;
	}
	/**
	 * @param actProcDate the actProcDate to set
	 */
	public void setActProcDate(Date actProcDate) {
		this.actProcDate = actProcDate;
	}
	/**
	 * @return the overProcDate
	 */
	public Date getOverProcDate() {
		return overProcDate;
	}
	/**
	 * @param overProcDate the overProcDate to set
	 */
	public void setOverProcDate(Date overProcDate) {
		this.overProcDate = overProcDate;
	}
	
}
