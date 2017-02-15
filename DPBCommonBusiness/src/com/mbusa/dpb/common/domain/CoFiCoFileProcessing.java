/**
 * 
 */
package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;


import com.mbusa.dpb.common.util.DateCalenderUtil;

/**
 * @author SK5008848
 * @version 1.0
 */
public class CoFiCoFileProcessing implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String poNo;
	private String poNo1;
	private String coCode;
	private Date ladspBnsAcrlPostdate;
	private Double ladrspBnsAmt;
	private String vehType;
	private Timestamp createdDts = DateCalenderUtil.getCurrentTimeStamp();
	private String createdUserId;
	private Timestamp lastUpdtDts = DateCalenderUtil.getCurrentTimeStamp();
	private String lastUpdtUserId;
	private String lineString;
	private Integer lineNumber;
	private String reason;
	

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
	public String getVehType() {
		return vehType;
	}

	/**
	 * @param vehType
	 *            the vehType to set
	 */
	public void setVehType(String vehType) {
		this.vehType = vehType;
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
