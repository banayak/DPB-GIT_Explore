package com.mbusa.dpb.common.domain;

import java.sql.Date;

public class InvalidReason {
	private String code ;
	private String reason;	
	private String dealerId;
	private String vinNum;
	private Date actlRtlDate;
	private String rcStatus; //record status
	private Integer procId;
	private Integer unBlkId;
	private String  procType;
	private int pgmId;
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the vinNum
	 */
	public String getVinNum() {
		return vinNum;
	}
	/**
	 * @param vinNum the vinNum to set
	 */
	public void setVinNum(String vinNum) {
		this.vinNum = vinNum;
	}
	/**
	 * @return the actlRtlDate
	 */
	public Date getActlRtlDate() {
		return actlRtlDate;
	}
	/**
	 * @param actlRtlDate the actlRtlDate to set
	 */
	public void setActlRtlDate(Date actlRtlDate) {
		this.actlRtlDate = actlRtlDate;
	}
	/**
	 * @return the rcStatus
	 */
	public String getRcStatus() {
		return rcStatus;
	}
	/**
	 * @param rcStatus the rcStatus to set
	 */
	public void setRcStatus(String rcStatus) {
		this.rcStatus = rcStatus;
	}
	/**
	 * @return the procId
	 */
	public Integer getProcId() {
		return procId;
	}
	/**
	 * @param procId the procId to set
	 */
	public void setProcId(Integer procId) {
		this.procId = procId;
	}
	/**
	 * @return the unBlkId
	 */
	public Integer getUnBlkId() {
		return unBlkId;
	}
	/**
	 * @param unBlkId the unBlkId to set
	 */
	public void setUnBlkId(Integer unBlkId) {
		this.unBlkId = unBlkId;
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
	 * @return the pgmId
	 */
	public int getPgmId() {
		return pgmId;
	}
	/**
	 * @param pgmId the pgmId to set
	 */
	public void setPgmId(int pgmId) {
		this.pgmId = pgmId;
	}
	public String getStringForPayment(){
		return "Program Id:"+this.getPgmId() + ":Reason: "+this.getReason();
	}
}
