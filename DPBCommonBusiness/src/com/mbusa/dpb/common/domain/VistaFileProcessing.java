package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.util.DPBCommonHelper;

public class VistaFileProcessing implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1405835891067457015L;
	private String poNo;
	private String vinNum;
	private Date retailDate;
	private Time retailTime;
	private String dealerId;
	private String usedCarIndicator;
	private String empPurCtrlId;
	private String modelYearDate;
	private String modelText;
	private String regionCode;
	private String retailCustLastName;
	private String retailCustFirstName;
	private String retailCustMiddleName;
	private Date transDate;
	private Time transTime;
	private String useCode;
	private Double msrpBaseAmount;
	private Double msrpTotAmtAcsry;
	private Double DlrRebateAmt;
	private String fleetIndicator;
	private String wholeSaleInitType;
	private String vehStatusCode;
	private String vehTypeCode;
	private String nationalTypeCode;
	private String createdUserId;
	private String lastUpdtUserId;
	private Timestamp createdDts;
	private Timestamp lastUpdtDts;
	private Integer dpbProcessId;
	private String lineString;
	private Integer lineNumber;
	private String reason;
	private String salesTypeCode;
	private Date demoServiceDate;
	private String carStatusCode;
	private String usedCarIndicator2;
	private Double msrpTotalsAmt;
	private Integer progType;
	private String vehicleCount;
	private int condId;
	private Integer unBlkVehId;

	// AMG Changes - START
	private String indAmg;
		
	public String getIndAmg() {
		return indAmg;
	}

	public void setIndAmg(String indAmg) {
		this.indAmg = indAmg;
	}
	// AMG Changes - END
	
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
	 * @return the vinNum
	 */
	public String getVinNum() {
		return vinNum;
	}

	/**
	 * @param vinNum
	 *            the vinNum to set
	 */
	public void setVinNum(String vinNum) {
		this.vinNum = vinNum;
	}

	/**
	 * @return the retailDate
	 */
	public Date getRetailDate() {
		return retailDate;
	}

	/**
	 * @param retailDate
	 *            the retailDate to set
	 */
	public void setRetailDate(Date retailDate) {
		this.retailDate = retailDate;
	}

	/**
	 * @return the retailTime
	 */
	public Time getRetailTime() {
		return retailTime;
	}

	/**
	 * @param retailTime
	 *            the retailTime to set
	 */
	public void setRetailTime(Time retailTime) {
		this.retailTime = retailTime;
	}

	/**
	 * @return the dealerId
	 */
	public String getDealerId() {
		return dealerId;
	}

	/**
	 * @param dealerId
	 *            the dealerId to set
	 */
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	/**
	 * @return the usedCarIndicator
	 */
	public String getUsedCarIndicator() {
		return usedCarIndicator;
	}

	/**
	 * @param usedCarIndicator
	 *            the usedCarIndicator to set
	 */
	public void setUsedCarIndicator(String usedCarIndicator) {
		this.usedCarIndicator = usedCarIndicator;
	}

	/**
	 * @return the empPurCtrlId
	 */
	public String getEmpPurCtrlId() {
		return empPurCtrlId;
	}

	/**
	 * @param empPurCtrlId
	 *            the empPurCtrlId to set
	 */
	public void setEmpPurCtrlId(String empPurCtrlId) {
		this.empPurCtrlId = empPurCtrlId;
	}

	/**
	 * @return the modelYearDate
	 */
	public String getModelYearDate() {
		return modelYearDate;
	}

	/**
	 * @param modelYearDate
	 *            the modelYearDate to set
	 */
	public void setModelYearDate(String modelYearDate) {
		this.modelYearDate = modelYearDate;
	}

	/**
	 * @return the modelText
	 */
	public String getModelText() {
		return modelText;
	}

	/**
	 * @param modelText
	 *            the modelText to set
	 */
	public void setModelText(String modelText) {
		this.modelText = modelText;
	}

	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode
	 *            the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * @return the retailCustLastName
	 */
	public String getRetailCustLastName() {
		return retailCustLastName;
	}

	/**
	 * @param retailCustLastName
	 *            the retailCustLastName to set
	 */
	public void setRetailCustLastName(String retailCustLastName) {
		this.retailCustLastName = retailCustLastName;
	}

	/**
	 * @return the retailCustFirstName
	 */
	public String getRetailCustFirstName() {
		return retailCustFirstName;
	}

	/**
	 * @param retailCustFirstName
	 *            the retailCustFirstName to set
	 */
	public void setRetailCustFirstName(String retailCustFirstName) {
		this.retailCustFirstName = retailCustFirstName;
	}

	/**
	 * @return the retailCustMiddleName
	 */
	public String getRetailCustMiddleName() {
		return retailCustMiddleName;
	}

	/**
	 * @param retailCustMiddleName
	 *            the retailCustMiddleName to set
	 */
	public void setRetailCustMiddleName(String retailCustMiddleName) {
		this.retailCustMiddleName = retailCustMiddleName;
	}

	/**
	 * @return the transTime
	 */
	public Time getTransTime() {
		return transTime;
	}

	/**
	 * @param transTime
	 *            the transTime to set
	 */
	public void setTransTime(Time transTime) {
		this.transTime = transTime;
	}

	/**
	 * @return the useCode
	 */
	public String getUseCode() {
		return useCode;
	}

	/**
	 * @param useCode
	 *            the useCode to set
	 */
	public void setUseCode(String useCode) {
		this.useCode = useCode;
	}

	/**
	 * @return the msrpBaseAmount
	 */
	public Double getMsrpBaseAmount() {
		return msrpBaseAmount;
	}

	/**
	 * @param msrpBaseAmount
	 *            the msrpBaseAmount to set
	 */
	public void setMsrpBaseAmount(Double msrpBaseAmount) {
		this.msrpBaseAmount = msrpBaseAmount;
	}

	/**
	 * @return the msrpTotAmtAcsry
	 */
	public Double getMsrpTotAmtAcsry() {
		return msrpTotAmtAcsry;
	}

	/**
	 * @param msrpTotAmtAcsry
	 *            the msrpTotAmtAcsry to set
	 */
	public void setMsrpTotAmtAcsry(Double msrpTotAmtAcsry) {
		this.msrpTotAmtAcsry = msrpTotAmtAcsry;
	}

	/**
	 * @return the fleetIndicator
	 */
	public String getFleetIndicator() {
		if(DPBCommonHelper.isEmptyOrNullString(fleetIndicator)){
			return IConstants.CONSTANT_N;
		}
		return fleetIndicator;
	}

	/**
	 * @param fleetIndicator
	 *            the fleetIndicator to set
	 */
	public void setFleetIndicator(String fleetIndicator) {
		this.fleetIndicator = fleetIndicator;
	}

	/**
	 * @return the wholeSaleInitType
	 */
	public String getWholeSaleInitType() {
		return wholeSaleInitType;
	}

	/**
	 * @param wholeSaleInitType
	 *            the wholeSaleInitType to set
	 */
	public void setWholeSaleInitType(String wholeSaleInitType) {
		this.wholeSaleInitType = wholeSaleInitType;
	}

	/**
	 * @return the vehStatusCode
	 */
	public String getVehStatusCode() {
		return vehStatusCode;
	}

	/**
	 * @param vehStatusCode
	 *            the vehStatusCode to set
	 */
	public void setVehStatusCode(String vehStatusCode) {
		this.vehStatusCode = vehStatusCode;
	}

	/**
	 * @return the vehTypeCode
	 */
	public String getVehTypeCode() {
		return vehTypeCode;
	}

	/**
	 * @param vehTypeCode
	 *            the vehTypeCode to set
	 */
	public void setVehTypeCode(String vehTypeCode) {
		this.vehTypeCode = vehTypeCode;
	}

	/**
	 * @return the nationalTypeCode
	 */
	public String getNationalTypeCode() {
		return nationalTypeCode;
	}

	/**
	 * @param nationalTypeCode
	 *            the nationalTypeCode to set
	 */
	public void setNationalTypeCode(String nationalTypeCode) {
		this.nationalTypeCode = nationalTypeCode;
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
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the salesTypeCode
	 */
	public String getSalesTypeCode() {
		return salesTypeCode;
	}

	/**
	 * @param salesTypeCode the salesTypeCode to set
	 */
	public void setSalesTypeCode(String salesTypeCode) {
		this.salesTypeCode = salesTypeCode;
	}
	/**
	 * @return the carStatusCode
	 */
	public String getCarStatusCode() {
		return carStatusCode;
	}

	/**
	 * @param carStatusCode the carStatusCode to set
	 */
	public void setCarStatusCode(String carStatusCode) {
		this.carStatusCode = carStatusCode;
	}

	/**
	 * @return the usedCarIndicator2
	 */
	public String getUsedCarIndicator2() {
		return usedCarIndicator2;
	}

	/**
	 * @param usedCarIndicator2 the usedCarIndicator2 to set
	 */
	public void setUsedCarIndicator2(String usedCarIndicator2) {
		this.usedCarIndicator2 = usedCarIndicator2;
	}

	public Double getMsrpTotalsAmt() {
		return msrpTotalsAmt;
	}

	public void setMsrpTotalsAmt(Double msrpTotalsAmt) {
		this.msrpTotalsAmt = msrpTotalsAmt;
	}

	/**
	 * @return the transDate
	 */
	public Date getTransDate() {
		return transDate;
	}

	/**
	 * @param transDate the transDate to set
	 */
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	/**
	 * @return the dlrRebateAmt
	 */
	public Double getDlrRebateAmt() {
		return DlrRebateAmt;
	}

	/**
	 * @param dlrRebateAmt the dlrRebateAmt to set
	 */
	public void setDlrRebateAmt(Double dlrRebateAmt) {
		DlrRebateAmt = dlrRebateAmt;
	}

	/**
	 * @return the progType
	 */
	public Integer getProgType() {
		return progType;
	}

	/**
	 * @param progType the progType to set
	 */
	public void setProgType(Integer progType) {
		this.progType = progType;
	}
	/**
	 * @return the vehicleCount
	 */
	public String getVehicleCount() {
		return vehicleCount;
	}

	/**
	 * @param vehicleCount the vehicleCount to set
	 */
	public void setVehicleCount(String vehicleCount) {
		this.vehicleCount = vehicleCount;
	}

	/**
	 * @return the condId
	 */
	public int getCondId() {
		return condId;
	}

	/**
	 * @param condId the condId to set
	 */
	public void setCondId(int condId) {
		this.condId = condId;
	}

	public Date getDemoServiceDate() {
		return demoServiceDate;
	}

	public void setDemoServiceDate(Date demoServiceDate) {
		this.demoServiceDate = demoServiceDate;
	}

	/**
	 * @return the unBlkVehId
	 */
	public Integer getUnBlkVehId() {
		return unBlkVehId;
	}

	/**
	 * @param unBlkVehId the unBlkVehId to set
	 */
	public void setUnBlkVehId(Integer unBlkVehId) {
		this.unBlkVehId = unBlkVehId;
	}
	public String toString(){
		return "poNo:"+getPoNo()+"vinNum:"+vinNum+":retailDate:"+retailDate+":retailTime:"+retailTime
				+":dealerId:"+dealerId
				+":	usedCarIndicator:"+usedCarIndicator
			+":empPurCtrlId:"+empPurCtrlId
			+":modelYearDate:"+modelYearDate
			+":modelText:"+modelText
			+":regionCode:"+regionCode
			+":retailCustLastName:"+retailCustLastName
			+":retailCustFirstName:"+retailCustFirstName
			+":retailCustMiddleName:"+retailCustMiddleName
			+":private Date transDate:"+transDate
			+":private Time transTime:"+transTime
			+":useCode:"+useCode
			+":private Double msrpBaseAmount:"+msrpBaseAmount
			+":private Double msrpTotAmtAcsry:"+msrpTotAmtAcsry
			+":private Double DlrRebateAmt:"+DlrRebateAmt
			+":fleetIndicator:"+fleetIndicator
			+":wholeSaleInitType:"+wholeSaleInitType
			+":vehStatusCode:"+vehStatusCode
			+":vehTypeCode:"+vehTypeCode
			+":nationalTypeCode:"+nationalTypeCode
			+":createdUserId:"+createdUserId
			+":lastUpdtUserId:"+lastUpdtUserId
			+":private Timestamp createdDts:"+createdDts
			+":private Timestamp lastUpdtDts:"+lastUpdtDts
			+":private Integer dpbProcessId:"+dpbProcessId
			+":lineString:"+lineString
			+":private Integer lineNumber:"+lineNumber
			+":reason:"+reason
		+":salesTypeCode:"+salesTypeCode
		+":private Date demoServiceDate:"+demoServiceDate
		+":carStatusCode:"+carStatusCode
		+":usedCarIndicator2:"+usedCarIndicator2
		+":private Double msrpTotalsAmt:"+msrpTotalsAmt
		+":private Integer progType:"+progType
		+":vehicleCount:"+vehicleCount
		+":private int condId:"+condId
		+":private Integer unBlkVehId:"+unBlkVehId;
	}
}
