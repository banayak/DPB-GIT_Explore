/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: KPI 
 * Program Name				: Kpi.java
 * Program Version			: 1.0
 * Program Description		: This class is used to hold KPI code table data
 * 							  for bonus processing and other functionality.
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 19, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class Kpi implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int kpiId;
	private String kpiName;
	private double maxPctAmt;
	private java.sql.Date effDte ;
	private java.sql.Date inActiveDate;
	/**
	 * @return the kpiId
	 */
	public int getKpiId() {
		return kpiId;
	}
	/**
	 * @param kpiId the kpiId to set
	 */
	public void setKpiId(int kpiId) {
		this.kpiId = kpiId;
	}	
	/**
	 * @return the kpiName
	 */
	public String getKpiName() {
		return kpiName;
	}
	/**
	 * @param kpiName the kpiName to set
	 */
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	/**
	 * @return the maxPctAmt
	 */
	public double getMaxPctAmt() {
		return maxPctAmt;
	}
	/**
	 * @param maxPctAmt the maxPctAmt to set
	 */
	public void setMaxPctAmt(double maxPctAmt) {
		this.maxPctAmt = maxPctAmt;
	}
	/**
	 * @return the inActiveDate
	 */
	public java.sql.Date getInActiveDate() {
		return inActiveDate;
	}
	/**
	 * @param inActiveDate the inActiveDate to set
	 */
	public void setInActiveDate(java.sql.Date inActiveDate) {
		this.inActiveDate = inActiveDate;
	}
	/**
	 * @return the effDte
	 */
	public java.sql.Date getEffDte() {
		return effDte;
	}
	/**
	 * @param effDte the effDte to set
	 */
	public void setEffDte(java.sql.Date effDte) {
		this.effDte = effDte;
	}
}
