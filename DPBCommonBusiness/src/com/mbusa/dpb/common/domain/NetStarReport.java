/**
 * 
 */
package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.sql.Date;
/* Vehicle Details Report - Start */
import java.util.ArrayList;
/* Vehicle Details Report - End */ 
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author RK5005820
 *
 */
public class NetStarReport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   /* Vehicle Details Report - Start */ 
	private String dataTypeRadio;
	 /* Vehicle Details Report - End */ 
	private String schedule;
	private int rptId;
	private String staticReport;
	private String vehicle;
	private int year;
	private int month;
	private String fromDate;
	private String toDate;
	private String fromYear;
	private String toYear;
	private String fromMonth;
	private String toMonth;
	private String fromQuarter;
	private String toQuarter;
	private String dealer;
	private String dynamicReport;
	private List<String> vehicleType;
	private boolean isStatic;
	private String reportDate;
	private Date actualDate;
	private String dealerId;
	private String vehicleId;
	private String viewAccountVin;
	private String  viewDealerLevel;
	private int viewTotVehQuarter;
	private String viewTotVeh;
	private String vinQuarter;
	private int viewDealerLevelQuarter;
	private int dealerQuarter;
	//Changed to handle exception - Amit
	//private int dealerQuarterYear;
	private String dealerQuarterYear;
	private int vehicleQuarter;
	//Changed to handle exception - Amit
	//private int vehicleQuarterYear;
	private String vehicleQuarterYear;
	private String poNumber;
	private String vehicleRange;
	private List<String> vehicleTypeRd;
	private List<String> vehTypeRadio;
	
	private int recCount = 0;
	//DPB daily payment details 0032484965 - Kshitija
	private double totalReportAmount;
	//DPB monthly reconciliation details start
	//monthly payment details - Kshitija
	private LinkedHashMap<String,Double> totalFtlMthlyAmt = new LinkedHashMap<String, Double>();
	private LinkedHashMap<String,Double> totalVanMnthlyAmt = new LinkedHashMap<String, Double>();
	private LinkedHashMap<String,Double> totalSmartMnthlyAmt = new LinkedHashMap<String, Double>();
	private LinkedHashMap<String,Double> totalPassengerMnthlyAmt = new LinkedHashMap<String, Double>();
	private LinkedHashMap<String,Double> totalAmgEliteMnthlyAmnt = new LinkedHashMap<String,Double>();
	private LinkedHashMap<String,Double> totalAmgPerfMnthlyAmnt = new LinkedHashMap<String,Double>();
	//DPB monthly reconciliation details end
	//Vehicle Details Report-start
	private boolean fetchReportResultCount;
	private boolean fetchReportResultTotal;
	private long startIndex;
	private long endIndex;
	private Boolean isLastPage = false;
	//Dealer Compliance Summary Report - Start
	private boolean isQtrStart;
	//Dealer Compliance Summary Report - End
	
	//AMG-Start
	private String programID;
	private int amgProgramRadio;			
	
	
	
	public int getAmgProgramRadio() {
		return amgProgramRadio;
	}
	public void setAmgProgramRadio(int amgProgramRadio) {
		this.amgProgramRadio = amgProgramRadio;
	}
	public String getProgramID() {
		return programID;
	}
	public void setProgramID(String programID) {
		this.programID = programID;	
	}
	
	public List <String>amgProgramList = new ArrayList<String>();
	
	public List<String> getAmgProgramList() {
		return amgProgramList;
	}
	public void setAmgProgramList(List<String> amgProgramList) {
		this.amgProgramList = amgProgramList;
	}	
	//AMG-End
	
	public Boolean getIsLastPage() {
		return isLastPage;
	}
	public void setIsLastPage(Boolean isLastPage) {
		this.isLastPage = isLastPage;
	}
	public List <String>dataTypeList = new ArrayList<String>();
	public List<String> getDataTypeListRd() {
		return dataTypeList;
	}
	public void setDataTypeListRd(List<String> dataTypeList) {
		this.dataTypeList = dataTypeList;
	}
	public long getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(long endIndex) {
		this.endIndex = endIndex;
	}
	public boolean isFetchReportResultCount() {
		return fetchReportResultCount;
	}
	public void setFetchReportResultCount(boolean fetchReportResultCount) {
		this.fetchReportResultCount = fetchReportResultCount;
	}
	
	public boolean isFetchReportResultTotal() {
		return fetchReportResultTotal;
	}
	public void setFetchReportResultTotal(boolean fetchReportResultTotal) {
		this.fetchReportResultTotal = fetchReportResultTotal;
	}
	public long getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(long startIndex) {
		this.startIndex = startIndex;
	}
	public String getDataTypeRadio() {
		return dataTypeRadio;
	}
	public void setDataTypeRadio(String dataTypeRadio) {
		this.dataTypeRadio = dataTypeRadio;
	}
	
	//Vehicle Details Report-end
	/**
	 * @return the isStatic
	 */
	public boolean isStatic() {
		return isStatic;
	}
	/**
	 * @param isStatic the isStatic to set
	 */
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	/**
	 * @return the schedule
	 */
	public String getSchedule() {
		return schedule;
	}
	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	/**
	 * @return the rptId
	 */
	public int getRptId() {
		return rptId;
	}
	/**
	 * @param rptId the rptId to set
	 */
	public void setRptId(int rptId) {
		this.rptId = rptId;
	}
	
	
	/**
	 * @return the staticReport
	 */
	public String getStaticReport() {
		return staticReport;
	}
	/**
	 * @param staticReport the staticReport to set
	 */
	public void setStaticReport(String staticReport) {
		this.staticReport = staticReport;
	}
	/**
	 * @return the vehicle
	 */
	public String getVehicle() {
		return vehicle;
	}
	/**
	 * @param vehicle the vehicle to set
	 */
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the dealer
	 */
	public String getDealer() {
		return dealer;
	}
	/**
	 * @param dealer the dealer to set
	 */
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	/**
	 * @return the dynamicReport
	 */
	public String getDynamicReport() {
		return dynamicReport;
	}
	public String getViewAccountVin() {
		return viewAccountVin;
	}
	public void setViewAccountVin(String viewAccountVin) {
		this.viewAccountVin = viewAccountVin;
	}
	public String getViewDealerLevel() {
		return viewDealerLevel;
	}
	public void setViewDealerLevel(String viewDealerLevel) {
		this.viewDealerLevel = viewDealerLevel;
	}
	public int getViewTotVehQuarter() {
		return viewTotVehQuarter;
	}
	public void setViewTotVehQuarter(int viewTotVehQuarter) {
		this.viewTotVehQuarter = viewTotVehQuarter;
	}
	public String getViewTotVeh() {
		return viewTotVeh;
	}
	public void setViewTotVeh(String viewTotVeh) {
		this.viewTotVeh = viewTotVeh;
	}
	public String getVinQuarter() {
		return vinQuarter;
	}
	public void setVinQuarter(String vinQuarter) {
		this.vinQuarter = vinQuarter;
	}
	public int getViewDealerLevelQuarter() {
		return viewDealerLevelQuarter;
	}
	public void setViewDealerLevelQuarter(int viewDealerLevelQuarter) {
		this.viewDealerLevelQuarter = viewDealerLevelQuarter;
	}
	public int getDealerQuarter() {
		return dealerQuarter;
	}
	public void setDealerQuarter(int dealerQuarter) {
		this.dealerQuarter = dealerQuarter;
	}
	/*public int getDealerQuarterYear() {
		return dealerQuarterYear;
	}
	public void setDealerQuarterYear(int dealerQuarterYear) {
		this.dealerQuarterYear = dealerQuarterYear;
	}	*/
	
	public int getVehicleQuarter() {
		return vehicleQuarter;
	}
	public String getDealerQuarterYear() {
		return dealerQuarterYear;
	}
	public void setDealerQuarterYear(String dealerQuarterYear) {
		this.dealerQuarterYear = dealerQuarterYear;
	}
	public void setVehicleQuarter(int vehicleQuarter) {
		this.vehicleQuarter = vehicleQuarter;
	}
	/*
	 * public int getVehicleQuarterYear() {
		return vehicleQuarterYear;
	}
	public void setVehicleQuarterYear(int vehicleQuarterYear) {
		this.vehicleQuarterYear = vehicleQuarterYear;
	}	*/
	
	
	/**
	 * @param dynamicReport the dynamicReport to set
	 */
	public void setDynamicReport(String dynamicReport) {
		this.dynamicReport = dynamicReport;
	}
	public String getVehicleQuarterYear() {
		return vehicleQuarterYear;
	}
	public void setVehicleQuarterYear(String vehicleQuarterYear) {
		this.vehicleQuarterYear = vehicleQuarterYear;
	}
	/**
	 * @return the vehicleType
	 */
	public List<String> getVehicleType() {
		return vehicleType;
	}
	/**
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(List<String> vehicleType) {
		this.vehicleType = vehicleType;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the reportDate
	 */
	public String getReportDate() {
		return reportDate;
	}
	/**
	 * @param reportDate the reportDate to set
	 */
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	/**
	 * @return the actualDate
	 */
	public Date getActualDate() {
		return actualDate;
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
	 * @return the vehicleId
	 */
	public String getVehicleId() {
		return vehicleId;
	}
	/**
	 * @param vehicleId the vehicleId to set
	 */
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	/**
	 * @param actualDate the actualDate to set
	 */
	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
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
	 * @return the vehicleRange
	 */
	public String getVehicleRange() {
		return vehicleRange;
	}
	/**
	 * @param vehicleRange the vehicleRange to set
	 */
	public void setVehicleRange(String vehicleRange) {
		this.vehicleRange = vehicleRange;
	}
	
	public int getRecCount() {
		return recCount;
	}
	public void setRecCount(int recCount) {
		this.recCount = recCount;
	}
	public List<String> getVehicleTypeRd() {
		return vehicleTypeRd;
	}
	public void setVehicleTypeRd(List<String> vehicleTypeRd) {
		this.vehicleTypeRd = vehicleTypeRd;
	}
	public List<String> getVehTypeRadio() {
		return vehTypeRadio;
	}
	public void setVehTypeRadio(List<String> vehTypeRadio) {
		this.vehTypeRadio = vehTypeRadio;
	}
	/**
	 * @return the totalReportAmount
	 */
	public double getTotalReportAmount() {
		return totalReportAmount;
	}
	/**
	 * @param totalReportAmount the totalReportAmount to set
	 */
	public void setTotalReportAmount(double totalReportAmount) {
		this.totalReportAmount = totalReportAmount;
	}
	//DPB monthly reconciliation details start
	/**
	 * @return the totalFtlMthlyAmt
	 */
	public LinkedHashMap<String, Double> getTotalFtlMthlyAmt() {
		return totalFtlMthlyAmt;
	}
	/**
	 * @param totalFtlMthlyAmt the totalFtlMthlyAmt to set
	 */
	public void setTotalFtlMthlyAmt(LinkedHashMap<String, Double> totalFtlMthlyAmt) {
		this.totalFtlMthlyAmt = totalFtlMthlyAmt;
	}
	
	/**
	 * @return the totalVanMnthlyAmt
	 */
	public LinkedHashMap<String, Double> getTotalVanMthlyAmt() {
		return totalVanMnthlyAmt;
	}
	/**
	 * @param totalVanMnthlyAmt the totalVanMnthlyAmt to set
	 */
	public void setTotalVanMthlyAmt(LinkedHashMap<String, Double> totalVanMnthlyAmt) {
		this.totalVanMnthlyAmt = totalVanMnthlyAmt;
	}
	
	/**
	 * @return the totalSmartMnthlyAmt
	 */
	public LinkedHashMap<String, Double> getTotalSmartMthlyAmt() {
		return totalSmartMnthlyAmt;
	}
	
	/**
	 * @param totalSmartMnthlyAmt the totalSmartMnthlyAmt
	 */
	public void setTotalSmartMthlyAmt(
			LinkedHashMap<String, Double> totalSmartMnthlyAmt) {
		this.totalSmartMnthlyAmt = totalSmartMnthlyAmt;
	}
	

	/**
	 * @return the totalPassengerMnthlyAmt
	 */
	public LinkedHashMap<String, Double> getTotalPassengerMthlyAmt() {
		return totalPassengerMnthlyAmt;
	}
	/**
	 * @param totalPassengerMnthlyAmt the totalPassengerMnthlyAmt
	 */
	public void setTotalPassengerMthlyAmt(
			LinkedHashMap<String, Double> totalPassengerMnthlyAmt) {
		this.totalPassengerMnthlyAmt = totalPassengerMnthlyAmt;
	}
	/**
	 * @return the totalAmgEliteMnthlyAmnt
	 */
	public LinkedHashMap<String, Double> getTotalAmgEliteMnthlyAmnt() {
		return totalAmgEliteMnthlyAmnt;
	}
	/**
	 * @param totalAmgEliteMnthlyAmnt the totalAmgEliteMnthlyAmnt to set
	 */
	public void setTotalAmgEliteMnthlyAmnt(
			LinkedHashMap<String, Double> totalAmgEliteMnthlyAmnt) {
		this.totalAmgEliteMnthlyAmnt = totalAmgEliteMnthlyAmnt;
	}
	/**
	 * @return the totalAmgPerfMnthlyAmnt
	 */
	public LinkedHashMap<String, Double> getTotalAmgPerfMnthlyAmnt() {
		return totalAmgPerfMnthlyAmnt;
	}
	/**
	 * @param totalAmgPerfMnthlyAmnt the totalAmgPerfMnthlyAmnt to set
	 */
	public void setTotalAmgPerfMnthlyAmnt(
			LinkedHashMap<String, Double> totalAmgPerfMnthlyAmnt) {
		this.totalAmgPerfMnthlyAmnt = totalAmgPerfMnthlyAmnt;
	}
	//DPB monthly reconciliation details end

	public String getFromYear() {
		return fromYear;
	}
	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}
	public String getToYear() {
		return toYear;
	}
	public void setToYear(String toYear) {
		this.toYear = toYear;
	}
	public String getFromMonth() {
		return fromMonth;
	}
	public void setFromMonth(String fromMonth) {
		this.fromMonth = fromMonth;
	}
	public String getToMonth() {
		return toMonth;
	}
	public void setToMonth(String toMonth) {
		this.toMonth = toMonth;
	}
	public String getFromQuarter() {
		return fromQuarter;
	}
	public void setFromQuarter(String fromQuarter) {
		this.fromQuarter = fromQuarter;
	}
	public String getToQuarter() {
		return toQuarter;
	}
	public void setToQuarter(String toQuarter) {
		this.toQuarter = toQuarter;
	}
	//Dealer Compliance Summary Report - Start
	public boolean isQtrStart() {
		return isQtrStart;
	}
	public void setQtrStart(boolean isQtrStart) {
		this.isQtrStart = isQtrStart;
	}
	//Dealer Compliance Summary Report - End
	
}
