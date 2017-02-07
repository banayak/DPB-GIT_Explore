/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: RtlCalenderDefinition.java
 * Program Version			: 1.0
 * Program Description		: This class is used to hold retail calender data.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 27, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.domain;

import java.util.Date;



public class AMGDealer {
	private String dealerID;
	private String dealerName;
	private String dealerType;
	private Date startDate;
	private Date endDate;
	private String retailYear;
	private String retailEndYear;
	
	public String getRetailEndYear() {
		return retailEndYear;
	}
	public void setRetailEndYear(String retailEndYear) {
		this.retailEndYear = retailEndYear;
	}
	private String retailStartMonth;
	private String retailEndMonth;	
	
	
	
	public String getRetailYear() {
		return retailYear;
	}
	public void setRetailYear(String retailYear) {
		this.retailYear = retailYear;
	}
	public String getRetailStartMonth() {
		return retailStartMonth;
	}
	public void setRetailStartMonth(String retailStartMonth) {
		this.retailStartMonth = retailStartMonth;
	}
	public String getRetailEndMonth() {
		return retailEndMonth;
	}
	public void setRetailEndMonth(String retailEndMonth) {
		this.retailEndMonth = retailEndMonth;
	}
	public String getDealerID() {
		return dealerID;
	}
	public void setDealerID(String dealerID) {
		this.dealerID = dealerID;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getDealerType() {
		return dealerType;
	}
	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
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
	
}
