/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: CB5002578 
 * Program Name				: BulkSchedulerInfo.java
 * Program Version			: 1.0
 * Program Description		: This class is used to hold Bulk Scheduler Info
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * CB5002578   March 12, 2014     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.sql.Date;

public class BulkSchedulerInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Date todaysDte;
	Date scheduleStartDate;
	Date processingDate;
	int noOfDays ;
	/**
	 * @return the todaysDte
	 */
	public Date getTodaysDte() {
		return todaysDte;
	}
	/**
	 * @param todaysDte the todaysDte to set
	 */
	public void setTodaysDte(Date todaysDte) {
		this.todaysDte = todaysDte;
	}
	/**
	 * @return the scheduleStartDate
	 */
	public Date getScheduleStartDate() {
		return scheduleStartDate;
	}
	/**
	 * @param scheduleStartDate the scheduleStartDate to set
	 */
	public void setScheduleStartDate(Date scheduleStartDate) {
		this.scheduleStartDate = scheduleStartDate;
	}
	/**
	 * @return the noOfDays
	 */
	public int getNoOfDays() {
		return noOfDays;
	}
	/**
	 * @param noOfDays the noOfDays to set
	 */
	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	
	//Data Cleanup for process rerun start
	/**
	 * @return the processingDate
	 */
	public Date getProcessingDate() {
		return processingDate;
	}
	
	/**
	 * @param processingDate the processingDate to set
	 */
	public void setProcessingDate(Date processingDate) {
		this.processingDate=processingDate;
	}
	
	//Data Cleanup for process rerun end
}
