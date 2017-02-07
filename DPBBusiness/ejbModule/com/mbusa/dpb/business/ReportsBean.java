/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportsBean.java
 * Program Version			: 1.0
 * Program Description		: This class is used to provide Reports service. 
 * 							  
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 27, 2013     	  1.0        First Draft
 **------------------------------------------------------------------------------------------
 **********************************************************************************************/
package com.mbusa.dpb.business;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.mbusa.dpb.business.view.ReportsBeanLocal;
import com.mbusa.dpb.common.bo.IReportsManager;
import com.mbusa.dpb.common.bo.ReportsManagerImpl;
import com.mbusa.dpb.common.domain.BlockedVehicle;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.DleRsrvSumRptBean;
import com.mbusa.dpb.common.domain.LeadershipReport;
import com.mbusa.dpb.common.domain.NetStarReport;
import com.mbusa.dpb.common.domain.ReportDefinitionBean;
import com.mbusa.dpb.common.domain.RetailsAndPaymentsReport;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.factories.BOFactory;
import com.mbusa.dpb.common.logger.DPBLog;

/**
 * Session Bean implementation class ReportsBean
 */
@Stateless
@Local(ReportsBeanLocal.class)
public class ReportsBean implements ReportsBeanLocal {

	/**
	 * Default constructor. 
	 */
	private IReportsManager reportsMgr  = (ReportsManagerImpl) BOFactory.getInstance().getImplementation(IReportsManager.class);
	private static DPBLog LOGGER = DPBLog.getInstance(ReportsBean.class);
	static final private String CLASSNAME = ReportsBean.class.getName();

	public ReportsBean() {

	}
	public List<BlockedVehicle> getBlockVehicleReport(String dealerId,String vehicleId,Date fromDate,Date toDate){ 
		final String methodName = "getBlockVehicleReport";
		LOGGER.enter(CLASSNAME, methodName);
		List<BlockedVehicle> bList = null;
		bList = reportsMgr.getBlockVehicleReport(dealerId,vehicleId,fromDate,toDate);
		LOGGER.exit(CLASSNAME, methodName);
		return bList;
	}

	public Map<String, Object> generateReport(List<String> vehicleType,String viewAccountVin, int dealer, Date fromDate, Date toDate, int year) {
		final String methodName = "generateReport";
		LOGGER.enter(CLASSNAME, methodName);
		Map<String, Object> genreport  = reportsMgr.generateReport(vehicleType,viewAccountVin,dealer,fromDate,toDate, year);
		LOGGER.exit(CLASSNAME, methodName);
		return genreport;
	}
	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId) {
		final String methodName = "generateReport";
		LOGGER.enter(CLASSNAME, methodName);
		List<DPBProcessLogBean> procesDetail = reportsMgr.getProcessLogsDeatils(procId);	
		LOGGER.exit(CLASSNAME, methodName);
		return procesDetail;
	}

	public List <RetailsAndPaymentsReport> generateRtlAndPayReport(List<String> vType,String vehicleId,java.sql.Date fromDate,java.sql.Date toDate,String level)
	{
		final String methodName = "generateRtlAndPayReport";
		LOGGER.enter(CLASSNAME, methodName);
		List<RetailsAndPaymentsReport> bList = reportsMgr.generateRtlAndPayReport(vType,vehicleId,fromDate,toDate,level);
		LOGGER.exit(CLASSNAME, methodName);
		return bList; 
	}
	public List<ReportDefinitionBean> generateReportList()throws BusinessException{
		final String methodName = "generateReportList";
		LOGGER.enter(CLASSNAME, methodName);
		List<ReportDefinitionBean> rList = reportsMgr.generateReportList();
		LOGGER.exit(CLASSNAME, methodName);
		return rList;

	}
	public StringBuffer reportsGenerate(NetStarReport reportCriteria, boolean isFromScheduler){
		final String methodName = "reportsGenerate";
		LOGGER.enter(CLASSNAME, methodName);
		StringBuffer rList = reportsMgr.reportsGenerate(reportCriteria, isFromScheduler);
		LOGGER.exit(CLASSNAME, methodName);
		return rList;
	}
	public ReportDefinitionBean retrieveReportId(int processId){
		final String methodName = "retrieveReportId";
		LOGGER.enter(CLASSNAME, methodName);
		ReportDefinitionBean reportDefBean = reportsMgr.retrieveReportId(processId);
		LOGGER.exit(CLASSNAME, methodName);
		return reportDefBean;
	}
	public List<VistaFileProcessing> getDlrReserveExceptionList(List<String>  vehicleType, String month ,String year, String dealerIds){
		final String methodName = "getVehicleExceptionList";
		LOGGER.enter(CLASSNAME, methodName);
		List<VistaFileProcessing> rList = null;		
		rList = reportsMgr.getDlrReserveExceptionList(vehicleType, month ,year, dealerIds);
		LOGGER.exit(CLASSNAME, methodName);
		return rList;
	}
	public List<DleRsrvSumRptBean> getDlrRsrvSumReport(List<String> vehicles,int year,int month)throws BusinessException,TechnicalException{
		final String methodName = "getDlrRsrvSumReport";
		LOGGER.enter(CLASSNAME, methodName);
		List<DleRsrvSumRptBean> rList = reportsMgr.getDlrRsrvSumReport(vehicles,year,month);
		LOGGER.exit(CLASSNAME, methodName);
		return rList;
	}
	//Vehicle Details Report-start
	public List<List<List<String>>> reportsGenerateStatic (NetStarReport reportCriteria, Boolean isGenerateExcel) {
		final String methodName = "reportsGenerateStatic";
		LOGGER.enter(CLASSNAME, methodName);
		List<List<List<String>>> rList = reportsMgr.reportsGenerateStatic(reportCriteria, isGenerateExcel);
		//Vehicle Details Report-end
		LOGGER.exit(CLASSNAME, methodName);
		return rList;
	}
	
	public StringBuffer reportsGenerate1(LeadershipReport reportCriteria, boolean isFromScheduler){
		final String methodName = "reportsGenerate";
		LOGGER.enter(CLASSNAME, methodName);
		StringBuffer rList = reportsMgr.reportsGenerate1(reportCriteria, isFromScheduler);
		LOGGER.exit(CLASSNAME, methodName);
		return rList;
	}
	
}
