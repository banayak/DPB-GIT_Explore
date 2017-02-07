package com.mbusa.dpb.web.Reports.action;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.RetailsAndPaymentsReport;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.delegate.ReportDelegate;
import com.mbusa.dpb.web.form.VehicleConditionForm;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class RetailsAndPaymentsReportAction extends DPBAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static DPBLog LOGGER = DPBLog.getInstance(RetailsAndPaymentsReportAction.class);
	
	private String dealerId;
	private String vehicleId;
	private String fromDate;
	private String toDate;	
	private String level;
	private String PCAR = "1";       
	private String SMART = "2";              
	private String VAN  = "3";               
	private String FTL   = "4";
	private ReportDelegate rDelegate  = new ReportDelegate();
	private List<RetailsAndPaymentsReportAction> rtlRptAndPayList;
	private ArrayList<VehicleType> vehicleTypeList ;
	private List<RetailsAndPaymentsReport> retAndPayList;
	private VehicleType vehicleType;
	
	private List<String> vType;

	private VehicleConditionForm  vcMappingForm =  new VehicleConditionForm();
	
	private String actionForward = "errorPage";
	
	
	public String displayRetailsAndPaymentsReport(){
		try{
			actionForward="retailsAndPaymentsRpt";
			this.setMenuTabFocus(IWebConstants.REPORTS_ID);
			vehicleTypeList = (ArrayList<VehicleType>) MasterDataLookup.getInstance().getVehicleList();
			
	}catch (PersistenceException pe) {
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, pe);
	}
	catch (Exception e) {
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, e);
	}
		return actionForward;

}
	
	

	public String generateRetailsAndPaymentsReport(){
		try{
			this.setMenuTabFocus(IWebConstants.REPORTS_ID);
			vehicleTypeList = (ArrayList<VehicleType>) MasterDataLookup.getInstance().getVehicleList();
			actionForward = "genRtlAndPayReport";
			//populateRetailsAndPaymentsReport();
			retAndPayList=new ArrayList<RetailsAndPaymentsReport>();
			java.sql.Date fdate =WebHelper.convertToSqlDate(WebHelper.convertStringToDate(fromDate));
			java.sql.Date tDate = WebHelper.convertToSqlDate(WebHelper.convertStringToDate(toDate));
			
			retAndPayList=rDelegate.generateRtlAndPayReport( vType,vehicleId,fdate,tDate,level);
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
		return actionForward;
	}
	
	/*private void  populateRetailsAndPaymentsReport()
	{
		this.setLevel("V");
		vType.setVehicleType("1");
		
	}*/
	
	public String getDealerId() {
		return dealerId;
	}
	
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	
	public String getVehicleId() {
		return vehicleId;
	}
	
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
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



	public List<RetailsAndPaymentsReportAction> getRtlRptAndPayList() {
		return rtlRptAndPayList;
	}



	public void setRtlRptAndPayList(
			List<RetailsAndPaymentsReportAction> rtlRptAndPayList) {
		this.rtlRptAndPayList = rtlRptAndPayList;
	}
	
	public ArrayList<VehicleType> getVehicleTypeList() {
		return vehicleTypeList;
	}



	public void setVehicleTypeList(ArrayList<VehicleType> vehicleTypeList) {
		this.vehicleTypeList = vehicleTypeList;
	}



	public String getLevel() {
		return level;
	}



	public void setLevel(String level) {
		this.level = level;
	}




	public List<RetailsAndPaymentsReport> getRetAndPayList() {
		return retAndPayList;
	}



	public void setRetAndPayList(List<RetailsAndPaymentsReport> retAndPayList) {
		this.retAndPayList = retAndPayList;
	}



	/**
	 * @return the vehicleType
	 */
	public VehicleType getVehicleType() {
		return vehicleType;
	}



	/**
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}



	/**
	 * @return the vType
	 */
	public List<String> getvType() {
		return vType;
	}



	/**
	 * @param vType the vType to set
	 */
	public void setvType(List<String> vType) {
		this.vType = vType;
	}


	

}
