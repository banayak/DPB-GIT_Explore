/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: KPIFileProcessing.java
 * Program Version			: 1.0
 * Program Description		: This Class is used for CDDB KPI File Processing
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * 
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author SK5008848
 * 
 */
public class KpiFileProcessing implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dealerId;
	private Integer kpiYear;
	private String kpiFiscalPeriod;
	private Integer kpiId;
	private Double kpiPercentage;
	private Integer dpbProcessId;
	private Timestamp createdDts;
	private String createdUserId;
	private Timestamp lastUpdtDts;
	private String lastUpdtUserId;
	private String lineString;
	private Integer lineNumber;
	private String reason;
	
	/**
	 * @return the dpbProcessId
	 */
	public Integer getDpbProcessId() {
		return dpbProcessId;
	}

	/**
	 * @param dpbProcessId the dpbProcessId to set
	 */
	public void setDpbProcessId(Integer dpbProcessId) {
		this.dpbProcessId = dpbProcessId;
	}

	/**
	 * @return the kpiYear
	 */
	public Integer getKpiYear() {
		return kpiYear;
	}

	/**
	 * @param kpiYear the kpiYear to set
	 */
	public void setKpiYear(Integer kpiYear) {
		this.kpiYear = kpiYear;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the lineString
	 */
	public String getLineString() {
		return lineString;
	}

	/**
	 * @param lineString the lineString to set
	 */
	public void setLineString(String lineString) {
		this.lineString = lineString;
	}

	/**
	 * @return the lineNumber
	 */
	public Integer getLineNumber() {
		return lineNumber;
	}

	/**
	 * @param lineNumber the lineNumber to set
	 */
	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return the dealerId
	 */
	public String getDealerId() {
		return dealerId;
	}

	/**
	 * @param dealerId the dealerId to set
	 */
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	/**
	 * @return the fiscalQtr
	 */
	public String getKpiFiscalPeriod() {
		return kpiFiscalPeriod;
	}

	/**
	 * @param fiscalQtr the fiscalQtr to set
	 */
	public void setKpiFiscalPeriod(String kpiFiscalPeriod) {
		this.kpiFiscalPeriod = kpiFiscalPeriod;
	}

	/**
	 * @return the kpiId
	 */
	public Integer getKpiId() {
		return kpiId;
	}

	/**
	 * @param kpiId the kpiId to set
	 */
	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}

	/**
	 * @return the kpiPercentage
	 */
	public Double getKpiPercentage() {
		return kpiPercentage;
	}

	/**
	 * @param kpiPercentage the kpiPercentage to set
	 */
	public void setKpiPercentage(Double kpiPercentage) {
		this.kpiPercentage = kpiPercentage;
	}

	/**
	 * @return the createdDts
	 */
	public Timestamp getCreatedDts() {
		return createdDts;
	}

	/**
	 * @param createdDts the createdDts to set
	 */
	public void setCreatedDts(Timestamp createdDts) {
		this.createdDts = createdDts;
	}

	/**
	 * @return the createdUserId
	 */
	public String getCreatedUserId() {
		return createdUserId;
	}

	/**
	 * @param createdUserId the createdUserId to set
	 */
	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	/**
	 * @return the lastUpdtDts
	 */
	public Timestamp getLastUpdtDts() {
		return lastUpdtDts;
	}

	/**
	 * @param lastUpdtDts the lastUpdtDts to set
	 */
	public void setLastUpdtDts(Timestamp lastUpdtDts) {
		this.lastUpdtDts = lastUpdtDts;
	}

	/**
	 * @return the lastUpdtUserId
	 */
	public String getLastUpdtUserId() {
		return lastUpdtUserId;
	}

	/**
	 * @param lastUpdtUserId the lastUpdtUserId to set
	 */
	public void setLastUpdtUserId(String lastUpdtUserId) {
		this.lastUpdtUserId = lastUpdtUserId;
	}
}
