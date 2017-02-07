/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: RetailPaymentReportAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle request all request to generate 
 * 							  Reports.
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 19, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.Reports.action;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.NetStarReport;
import com.mbusa.dpb.common.domain.QCRelationBean;
import com.mbusa.dpb.common.domain.ReportDefinitionBean;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.delegate.ReportDelegate;
import com.mbusa.dpb.web.form.ReportDefinitionForm;
import com.mbusa.dpb.web.helper.IWebConstants;

public class ReportGenerateAction extends DPBAction{

	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = ReportGenerateAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(ReportGenerateAction.class);
	private String actionForward = "errorPage";
	private NetStarReport netStartRpt;	
	private int reportId;
	ReportDefinitionForm reportDefForm;
	List<QCRelationBean> qcrList = new ArrayList<QCRelationBean>();
	List<ReportDefinitionForm> formList = new ArrayList<ReportDefinitionForm>();
	public ReportDelegate rDelegate  = new ReportDelegate();
	private String message = "";
	PrintWriter pw = null;
	private StringBuffer strBuffer;
	private String dynamicReport;
	private String fromDate;
	private String toDate;
	private String reportDate;
	private String dealer;
	private String yearAct;
	private String monthAct;
	private String waitReq;
	private String vehTypeRd;
	List<String> vehtype = new ArrayList<String>();
	List<String> vehTypeRad = new ArrayList<String>();
	List<ReportDefinitionBean> beanList = new ArrayList<ReportDefinitionBean>();
	private List <VehicleType>vehicleList = new ArrayList<VehicleType>(); 
	private List <VehicleType>vehicleListRd = new ArrayList<VehicleType>();
	private List <VehicleType>vehListRd = new ArrayList<VehicleType>();
	List<List<List<String>>> list = null;
	//Performance improvement on netstar report - Start
    String filePath = PropertyManager.getPropertyManager().getPropertyAsString("payment.path5");
    
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	//Performance improvement on netstar report - End	
	public String netStarReportGen()  {
		final String methodName = "netStarReportGen";
		LOGGER.enter(CLASSNAME, methodName);
		
		try{
			this.setMenuTabFocus(IWebConstants.REPORTS_ID);
			netStartRpt = new NetStarReport();
			vehicleList = MasterDataLookup.getInstance().getVehicleList();
			vehicleListRd = MasterDataLookup.getInstance().getVehicleList();
			//vehListRd = MasterDataLookup.getInstance().getVehicleList();
			for (VehicleType vType: vehicleListRd){
				if(!vType.getId().equalsIgnoreCase("P")){
					vehListRd.add(vType);
				}
			}
			beanList=rDelegate.generateReportList();
			vehtype.add("P");
			vehTypeRad.add("S");
			netStartRpt.setVehicleTypeRd(vehtype);
			netStartRpt.setVehTypeRadio( vehTypeRad);
			list = null;
			/*String fpath=getFilePath().replaceAll("\\/", "\\\\");
			setFilePath(fpath);*/
			
		}catch (BusinessException be) {
			LOGGER.error("BusinessException occured");
			if("msg".equals(be.getMessageKey())){
				addActionError(be.getMessage());
			}
		}
		catch (TechnicalException te) {
			LOGGER.error("TechnicalException occured");
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (PersistenceException pe) {
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			LOGGER.error("Exception occured");
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		actionForward = "netStarReportGen";
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;

	}
	public String reportsGenerate() throws Exception{
		final String methodName = "reportsGenerate";
		LOGGER.enter(CLASSNAME, methodName);
		try {
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			actionForward = "netStarReportPopUp";
			String schedule=this.getDynamicReport().substring(this.getDynamicReport().indexOf("_")+1);
			int rptId=Integer.valueOf(this.getDynamicReport().substring(0,this.getDynamicReport().indexOf("_"))).intValue();
			System.out.println("report id " + rptId );
			System.out.println("schedule  " + schedule );
			
			//String vehicle=dynamicReport.substring(dynamicReport.trim().indexOf("-")+1);
			netStartRpt = new NetStarReport();
			netStartRpt.setSchedule(schedule);
			netStartRpt.setRptId(Integer.valueOf(rptId).intValue());
			netStartRpt.setFromDate(fromDate);
			netStartRpt.setToDate(toDate);
			netStartRpt.setReportDate(reportDate);
			netStartRpt.setDealer(dealer);
			if("Y".equalsIgnoreCase(netStartRpt.getSchedule())){
				
				vehtype.add(vehTypeRd);
				netStartRpt.setVehicleTypeRd(vehtype);
				netStartRpt.setVehicleType(vehtype);
			}
			if("D".equalsIgnoreCase(netStartRpt.getSchedule())){
				yearAct = "-1";
			}
			if(yearAct != null || ! "".equals(yearAct))
			{
				netStartRpt.setYear(Integer.valueOf(yearAct).intValue());
			}
			
			if(monthAct !=null || ! "".equals(monthAct))
			{
				netStartRpt.setMonth(Integer.valueOf(monthAct).intValue());
			}
			
			strBuffer = rDelegate.reportsGenerate(netStartRpt);
				
			
		} 
		catch (TechnicalException te) {
			LOGGER.error("TechnicalException occured");
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (PersistenceException pe) {
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			LOGGER.error("Exception occured");
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}finally {
			if(pw != null){
				pw.close();
			}
		}
		addActionMessage(message);
		LOGGER.exit(CLASSNAME, methodName);
		
		return actionForward;
	}
	
	
	
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public List<ReportDefinitionForm> getFormList() {
		return formList;
	}
	public void setFormList(List<ReportDefinitionForm> formList) {
		this.formList = formList;
	}
	public StringBuffer getStrBuffer() {
		return strBuffer;
	}
	public void setStrBuffer(StringBuffer strBuffer) {
		this.strBuffer = strBuffer;
	}
	/**
	 * @return the netStartRpt
	 */
	public NetStarReport getNetStartRpt() {
		return netStartRpt;
	}
	/**
	 * @param netStartRpt the netStartRpt to set
	 */
	public void setNetStartRpt(NetStarReport netStartRpt) {
		this.netStartRpt = netStartRpt;
	}
	/**
	 * @return the dynamicReport
	 */
	public String getDynamicReport() {
		return dynamicReport;
	}
	/**
	 * @param dynamicReport the dynamicReport to set
	 */
	public void setDynamicReport(String dynamicReport) {
		this.dynamicReport = dynamicReport;
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
	 * @return the yearAct
	 */
	public String getYearAct() {
		return yearAct;
	}
	/**
	 * @param yearAct the yearAct to set
	 */
	public void setYearAct(String yearAct) {
		this.yearAct = yearAct;
	}
	/**
	 * @return the monthAct
	 */
	public String getMonthAct() {
		return monthAct;
	}
	/**
	 * @param monthAct the monthAct to set
	 */
	public void setMonthAct(String monthAct) {
		this.monthAct = monthAct;
	}
	/**
	 * @return the beanList
	 */
	public List<ReportDefinitionBean> getBeanList() {
		return beanList;
	}
	public String getVehTypeRd() {
		return vehTypeRd;
	}
	public void setVehTypeRd(String vehTypeRd) {
		this.vehTypeRd = vehTypeRd;
	}
	/**
	 * @param beanList the beanList to set
	 */
	public void setBeanList(List<ReportDefinitionBean> beanList) {
		this.beanList = beanList;
	}
	/**
	 * @return the vehicleList
	 */
	public List<VehicleType> getVehicleList() {
		return vehicleList;
	}
	/**
	 * @param vehicleList the vehicleList to set
	 */
	public void setVehicleList(List<VehicleType> vehicleList) {
		this.vehicleList = vehicleList;
	}
	public List<VehicleType> getVehicleListRd() {
		return vehicleListRd;
	}
	public void setVehicleListRd(List<VehicleType> vehicleListRd) {
		this.vehicleListRd = vehicleListRd;
	}
	public String getWaitReq() {
		return waitReq;
	}
	public void setWaitReq(String waitReq) {
		this.waitReq = waitReq;
	}
	public List<VehicleType> getVehListRd() {
		return vehListRd;
	}
	public void setVehListRd(List<VehicleType> vehListRd) {
		this.vehListRd = vehListRd;
	}
	
}
