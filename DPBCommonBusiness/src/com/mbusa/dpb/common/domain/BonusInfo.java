/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: BonusInfo.java
 * Program Version			: 1.0
 * Program Description		: This class is used to hold Bonus Calculated Data.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   August 27, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class BonusInfo implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	private Integer 	calcId;
	private Integer 	processId;
	private String  	poNumber;	
	private Integer 	dpbUnblkVehId;
	private String  	dlrId;
	private int 		ldrspBonusId;
	private int 		pgmId;
	private double  	bnsCalcAmt;
	private double  	maxBnsEligibleAmt;
	private String 		adjIndicator;
	private String    	status;
	private String 		vehicleType;
	private String 		vehicleIdentificationNum;	
	private String 		creditAcctId;
	private String 		debitAcctId;
	private String 		pgmName;	
	private String    	model;
	private String    	year;	
	private String 		userId;		
	private Timestamp  	updateTimeStamp;	
	private Timestamp	createTimeStamp;	
	private double 		unUsedAmt;
	private double 		unUsedAmtAcrl;
	private java.sql.Date 		actualDate;
	private String 		subProcType;
	// Start for Bonus adjuestment
	private String 		kpiId;
	private double 		totalBnsAmt;
	private String  	cdeVehDdrUse; //Use code
	private String  	cdeVehDdrSts; //CDE_VEH_DDR_STS
	
	// End for Bonus adjuestment
	//Unearned Performance Bonus calculation start - kshitija
	private double amtUnernd;
	//Unearned Performance Bonus calculation end - kshitija
	/**
	 * @return the calcId
	 */
	public Integer getCalcId() {
		return calcId;
	}
	/**
	 * @param calcId the calcId to set
	 */
	public void setCalcId(Integer calcId) {
		this.calcId = calcId;
	}
	/**
	 * @return the processId
	 */
	public Integer getProcessId() {
		return processId;
	}
	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	/**
	 * @return the poNumber
	 */
	public String getPoNumber() {
		return poNumber;
	}
	/**
	 * @param poNumber the poNumber to set
	 */
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}	
	/**
	 * @return the dpbUnblkVehId
	 */
	public Integer getDpbUnblkVehId() {
		return dpbUnblkVehId;
	}
	/**
	 * @param dpbUnblkVehId the dpbUnblkVehId to set
	 */
	public void setDpbUnblkVehId(Integer dpbUnblkVehId) {
		this.dpbUnblkVehId = dpbUnblkVehId;
	}
	/**
	 * @return the dlrId
	 */
	public String getDlrId() {
		return dlrId;
	}
	/**
	 * @param dlrId the dlrId to set
	 */
	public void setDlrId(String dlrId) {
		this.dlrId = dlrId;
	}
	/**
	 * @return the ldrspBonusId
	 */
	public int getLdrspBonusId() {
		return ldrspBonusId;
	}
	/**
	 * @param ldrspBonusId the ldrspBonusId to set
	 */
	public void setLdrspBonusId(int ldrspBonusId) {
		this.ldrspBonusId = ldrspBonusId;
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
	/**
	 * @return the bnsCalcAmt
	 */
	public double getBnsCalcAmt() {
		return bnsCalcAmt;
	}
	/**
	 * @param bnsCalcAmt the bnsCalcAmt to set
	 */
	public void setBnsCalcAmt(double bnsCalcAmt) {
		this.bnsCalcAmt = bnsCalcAmt;
	}
	/**
	 * @return the maxBnsEligibleAmt
	 */
	public double getMaxBnsEligibleAmt() {
		return maxBnsEligibleAmt;
	}
	/**
	 * @param maxBnsEligibleAmt the maxBnsEligibleAmt to set
	 */
	public void setMaxBnsEligibleAmt(double maxBnsEligibleAmt) {
		this.maxBnsEligibleAmt = maxBnsEligibleAmt;
	}
	/**
	 * @return the adjIndicator
	 */
	public String getAdjIndicator() {
		return adjIndicator;
	}
	/**
	 * @param adjIndicator the adjIndicator to set
	 */
	public void setAdjIndicator(String adjIndicator) {
		this.adjIndicator = adjIndicator;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}	
	/**
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		if(vehicleType != null && vehicleType.length() > 0){
			return vehicleType.trim();
		}
		return vehicleType;
	}
	/**
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	/**
	 * @return the vehicleIdentificationNum
	 */
	public String getVehicleIdentificationNum() {
		return vehicleIdentificationNum;
	}
	/**
	 * @param vehicleIdentificationNum the vehicleIdentificationNum to set
	 */
	public void setVehicleIdentificationNum(String vehicleIdentificationNum) {
		this.vehicleIdentificationNum = vehicleIdentificationNum;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the pgmName
	 */
	public String getPgmName() {
		return pgmName;
	}
	/**
	 * @param pgmName the pgmName to set
	 */
	public void setPgmName(String pgmName) {
		this.pgmName = pgmName;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * @return the updateTimeStamp
	 */
	public Timestamp getUpdateTimeStamp() {
		return updateTimeStamp;
	}
	/**
	 * @param updateTimeStamp the updateTimeStamp to set
	 */
	public void setUpdateTimeStamp(Timestamp updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}
	/**
	 * @return the createTimeStamp
	 */
	public Timestamp getCreateTimeStamp() {
		return createTimeStamp;
	}
	/**
	 * @param createTimeStamp the createTimeStamp to set
	 */
	public void setCreateTimeStamp(Timestamp createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}
	public double getUnUsedAmt() {
		return unUsedAmt;
	}
	public void setUnUsedAmt(double unUsedAmt) {
		this.unUsedAmt = unUsedAmt;
	}
	public double getUnUsedAmtAcrl() {
		return unUsedAmtAcrl;
	}
	public void setUnUsedAmtAcrl(double unUsedAmtAcrl) {
		this.unUsedAmtAcrl = unUsedAmtAcrl;
	}
	/**
	 * @return the actualDate
	 */
	public java.sql.Date getActualDate() {
		return actualDate;
	}
	/**
	 * @param actualDate the actualDate to set
	 */
	public void setActualDate(java.sql.Date actualDate) {
		this.actualDate = actualDate;
	}
	/**
	 * @return the subProcType
	 */
	public String getSubProcType() {
		return subProcType;
	}
	/**
	 * @param subProcType the subProcType to set
	 */
	public void setSubProcType(String subProcType) {
		this.subProcType = subProcType;
	}
	/**
	 * @return the kpiId
	 */
	public String getKpiId() {
		return kpiId;
	}
	/**
	 * @param kpiId the kpiId to set
	 */
	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}
	/**
	 * @return the totalBnsAmt
	 */
	public double getTotalBnsAmt() {
		return totalBnsAmt;
	}
	/**
	 * @param totalBnsAmt the totalBnsAmt to set
	 */
	public void setTotalBnsAmt(double totalBnsAmt) {
		this.totalBnsAmt = totalBnsAmt;
	}
	/**
	 * @return the creditAcctId
	 */
	public String getCreditAcctId() {
		return creditAcctId;
	}
	/**
	 * @param creditAcctId the creditAcctId to set
	 */
	public void setCreditAcctId(String creditAcctId) {
		this.creditAcctId = creditAcctId;
	}
	/**
	 * @return the debitAcctId
	 */
	public String getDebitAcctId() {
		return debitAcctId;
	}
	/**
	 * @param debitAcctId the debitAcctId to set
	 */
	public void setDebitAcctId(String debitAcctId) {
		this.debitAcctId = debitAcctId;
	}
	/**
	 * @return the cdeVehDdrUse
	 */
	public String getCdeVehDdrUse() {
		return cdeVehDdrUse;
	}
	/**
	 * @param cdeVehDdrUse the cdeVehDdrUse to set
	 */
	public void setCdeVehDdrUse(String cdeVehDdrUse) {
		this.cdeVehDdrUse = cdeVehDdrUse;
	}
	/**
	 * @return the cdeVehDdrSts
	 */
	public String getCdeVehDdrSts() {
		return cdeVehDdrSts;
	}
	/**
	 * @param cdeVehDdrSts the cdeVehDdrSts to set
	 */
	public void setCdeVehDdrSts(String cdeVehDdrSts) {
		this.cdeVehDdrSts = cdeVehDdrSts;
	}
	/**
	 * @return the amtUnernd
	 */
	public double getAmtUnernd() {
		return amtUnernd;
	}
	/**
	 * @param amtUnernd the amtUnernd to set
	 */
	public void setAmtUnernd(double amtUnernd) {
		this.amtUnernd = amtUnernd;
	}	
	
}
