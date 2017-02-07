/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: RetailPaymentReportAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle request all request to generate 
 * 							  Retail payment Report.
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 19, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.Reports.action;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.DealerBonusVehReport;
import com.mbusa.dpb.common.domain.DealerVehicleReport;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.delegate.ReportDelegate;
import com.mbusa.dpb.web.form.GenerateReportForm;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;


public class RetailPaymentReportAction extends DPBAction{
	
	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = RetailPaymentReportAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(RetailPaymentReportAction.class);
	private String actionForward = "errorPage";
	private List<String> vehicleType;
	private String fromDate;
	private String toDate;
	private String viewAccountVin;
	private String  viewDealerLevel;
	private int viewTotVehQuarter;
	private String viewTotVeh;
	private String vinQuarter;
	private int viewDealerLevelQuarter;
	private int dealerQuarter;
	private int vehicleQuarter;
	private ReportDelegate rDelegate  = new ReportDelegate();
	private GenerateReportForm reportForm ;	
	private List <VehicleType>vehicleList = new ArrayList<VehicleType>(); 
	private List<DealerVehicleReport> vehicleTypeList = new ArrayList<DealerVehicleReport>();
	private List <DealerBonusVehReport> vehicleCond = new ArrayList <DealerBonusVehReport>();
	private List <DealerBonusVehReport> totVehList = new ArrayList <DealerBonusVehReport>();

	public String displayRetailPaymentParamPage(){
		final String methodName = "displayRetailPaymentParamPage";
		LOGGER.enter(CLASSNAME, methodName);
		actionForward = "generateReport";
		vehicleList = MasterDataLookup.getInstance().getVehicleList();
		return actionForward;
	}
	
	@SuppressWarnings("unchecked")
	public String generateReport()
	{
		final String methodName = "generateReport";
		LOGGER.enter(CLASSNAME, methodName);
		try{
			int dealer =0;
			vehicleList = MasterDataLookup.getInstance().getVehicleList();
			Date fDate = null ;
			Date tDate = null;
			actionForward = "generateReportAction";
			if(viewAccountVin.equalsIgnoreCase("D")){
				dealer= viewDealerLevelQuarter;
			} else if(viewAccountVin.equalsIgnoreCase("VIN")) {
				fDate = WebHelper.convertStringToDate(fromDate);
				tDate = WebHelper.convertStringToDate(toDate);
			} else{
				dealer= viewTotVehQuarter;
			}
			Map<String,Object> genreport = rDelegate.generateReport(vehicleType,viewAccountVin,fDate,tDate,dealer, new java.util.Date().getYear());
			if(genreport.get("VINMAP") != null)
			{
				vehicleTypeList=(List<DealerVehicleReport>) genreport.get("VINMAP");
			}
			if(genreport.get("DEALER") !=null)
			{
				vehicleCond=(List<DealerBonusVehReport>)genreport.get("DEALER");
			}
			if(genreport.get("TOTVEH") !=null)
			{
				totVehList=(List<DealerBonusVehReport>)genreport.get("TOTVEH");
			}
		}catch(BusinessException be){
			LOGGER.info("BusinessException occured");
		}catch (TechnicalException te) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, te);
		}catch (PersistenceException pe) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, pe);
		}
		catch (Exception e) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, e);
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public GenerateReportForm getReportForm() {
		return reportForm;
	}

	public void setReportForm(GenerateReportForm reportForm) {
		this.reportForm = reportForm;
	}

	public List<VehicleType> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(List<VehicleType> vehicleList) {
		this.vehicleList = vehicleList;
	}
	public List<String> getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(List<String> vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getViewAccountVin() {
		return viewAccountVin;
	}

	public void setViewAccountVin(String viewAccountVin) {
		this.viewAccountVin = viewAccountVin;
	}

	public int getDealerQuarter() {
		return dealerQuarter;
	}

	public void setDealerQuarter(int dealerQuarter) {
		this.dealerQuarter = dealerQuarter;
	}

	public int getVehicleQuarter() {
		return vehicleQuarter;
	}

	public void setVehicleQuarter(int vehicleQuarter) {
		this.vehicleQuarter = vehicleQuarter;
	}

	public List<DealerVehicleReport> getVehicleTypeList() {
		return vehicleTypeList;
	}

	public void setVehicleTypeList(List<DealerVehicleReport> vehicleTypeList) {
		this.vehicleTypeList = vehicleTypeList;
	}

	public List<DealerBonusVehReport> getVehicleCond() {
		return vehicleCond;
	}

	public String getViewTotVeh() {
		return viewTotVeh;
	}

	public int getViewDealerLevelQuarter() {
		return viewDealerLevelQuarter;
	}

	public void setViewDealerLevelQuarter(int viewDealerLevelQuarter) {
		this.viewDealerLevelQuarter = viewDealerLevelQuarter;
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

	public String getViewDealerLevel() {
		return viewDealerLevel;
	}

	public void setViewDealerLevel(String viewDealerLevel) {
		this.viewDealerLevel = viewDealerLevel;
	}

	public List<DealerBonusVehReport> getTotVehList() {
		return totVehList;
	}

	public void setTotVehList(List<DealerBonusVehReport> totVehList) {
		this.totVehList = totVehList;
	}

	public int getViewTotVehQuarter() {
		return viewTotVehQuarter;
	}

	public void setViewTotVehQuarter(int viewTotVehQuarter) {
		this.viewTotVehQuarter = viewTotVehQuarter;
	}

	public void setVehicleCond(List<DealerBonusVehReport> vehicleCond) {
		this.vehicleCond = vehicleCond;
	}

	
	
}