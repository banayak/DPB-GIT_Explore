/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: NetAccrualFileProcessing.java
 * Program Version			: 1.0
 * Program Description		: This Class is used for holds Net Accrual file data.
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
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author SK5008848
 * @version 1.0
 */
public class NetAccrualFileProcessing implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String poNo;
	private String coCode;
	private Date ladspBnsAcrlPostdate;
	private Double ladrspBnsAmt;
	private String vehTypeCode;
	private Timestamp createdDts;
	private String createdUserId;
	private Timestamp lastUpdtDts;
	private String lastUpdtUserId;
	private Integer dpbProcessId;
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
	 * @return the lineString
	 */
	public String getLineString() {
		return lineString;
	}

	/**
	 * @param lineString
	 *            the lineString to set
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
	 * @param lineNumber
	 *            the lineNumber to set
	 */
	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return the poNo
	 */
	public String getPoNo() {
		return poNo;
	}

	/**
	 * @param poNo
	 *            the poNo to set
	 */
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	/**
	 * @return the coCode
	 */
	public String getCoCode() {
		return coCode;
	}

	/**
	 * @param coCode
	 *            the coCode to set
	 */
	public void setCoCode(String coCode) {
		this.coCode = coCode;
	}

	/**
	 * @return the ladspBnsAcrlPostdate
	 */
	public Date getLadspBnsAcrlPostdate() {
		return ladspBnsAcrlPostdate;
	}

	/**
	 * @param ladspBnsAcrlPostdate
	 *            the ladspBnsAcrlPostdate to set
	 */
	public void setLadspBnsAcrlPostdate(Date ladspBnsAcrlPostdate) {
		this.ladspBnsAcrlPostdate = ladspBnsAcrlPostdate;
	}

	/**
	 * @return the ladrspBnsAmt
	 */
	public Double getLadrspBnsAmt() {
		return ladrspBnsAmt;
	}

	/**
	 * @param ladrspBnsAmt
	 *            the ladrspBnsAmt to set
	 */
	public void setLadrspBnsAmt(Double ladrspBnsAmt) {
		this.ladrspBnsAmt = ladrspBnsAmt;
	}

	/**
	 * @return the vehType
	 */
	public String getVehTypeCode() {
		return vehTypeCode;
	}

	/**
	 * @param vehType
	 *            the vehType to set
	 */
	public void setVehType(String vehTypeCode) {
		this.vehTypeCode = vehTypeCode;
	}

	/**
	 * @return the createdDts
	 */
	public Timestamp getCreatedDts() {
		return createdDts;
	}

	/**
	 * @param createdDts
	 *            the createdDts to set
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
	 * @param createdUserId
	 *            the createdUserId to set
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
	 * @param lastUpdtDts
	 *            the lastUpdtDts to set
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
	 * @param lastUpdtUserId
	 *            the lastUpdtUserId to set
	 */
	public void setLastUpdtUserId(String lastUpdtUserId) {
		this.lastUpdtUserId = lastUpdtUserId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
