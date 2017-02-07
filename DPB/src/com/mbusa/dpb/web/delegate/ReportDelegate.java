/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportDelegate.java
 * Program Version			: 1.0
 * Program Description		: This class is used to delegate report request to report Bean
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 27, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.delegate;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.business.util.GenerateReportHelper;
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
import com.mbusa.dpb.common.logger.DPBLog;

public class ReportDelegate {
	
	private static DPBLog LOGGER = DPBLog.getInstance(ReportDelegate.class);
	static final private String CLASSNAME = ReportDelegate.class.getName();

	private LocalServiceFactory local =  new LocalServiceFactory();
	private GenerateReportHelper rptHelper = new GenerateReportHelper();
	/**
	 * 
	 * @param dealerId
	 * @param vehicleId
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public List<BlockedVehicle> getBlockVehicleReport(String dealerId,String vehicleId,Date fromDate,Date toDate) throws BusinessException,TechnicalException{ 
		List<BlockedVehicle> bList = null;
		final String methodName = "getBlockVehicleReport";
		LOGGER.enter(CLASSNAME, methodName);
		try {
			bList = local.getReportsService().getBlockVehicleReport(dealerId,vehicleId,fromDate,toDate);
		}catch (TechnicalException te) {
			LOGGER.error("DPB.VEHICLE.BLOCK.REPORT.001", te);
			throw new TechnicalException("",te.getMessageKey());
		}
		LOGGER.exit(CLASSNAME, methodName);
		return bList;
	}
	/**
	 * 
	 * @param vehicleType
	 * @param viewAccountVin
	 * @param fromDate
	 * @param toDate
	 * @param dealer
	 * @return
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public Map<String,Object> generateReport(List<String> vehicleType,String viewAccountVin, Date fromDate, Date toDate, int dealer, int year) throws BusinessException,TechnicalException{ 
		Map<String,Object> genreport = null;
		final String methodName = "generateReport";
		LOGGER.enter(CLASSNAME, methodName);
		try {
			genreport = local.getReportsService().generateReport(vehicleType,viewAccountVin,dealer,fromDate,toDate,year);
		}catch (TechnicalException te) {
			LOGGER.error("DPB.VEHICLE.BLOCK.REPORT.001", te);
			throw new TechnicalException("",te.getMessageKey());
		}
		LOGGER.exit(CLASSNAME, methodName);
		return genreport;
	}
	/**
	 * 
	 * @param procId
	 * @return
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId) throws BusinessException,TechnicalException {
		final String methodName = "getProcessLogsDeatils";
		LOGGER.enter(CLASSNAME, methodName);
		List<DPBProcessLogBean> procesDetail = null;
		try {
			procesDetail = local.getReportsService().getProcessLogsDeatils(procId);
		} catch (TechnicalException te) {
			throw new TechnicalException("",te.getMessageKey());
		}
		LOGGER.exit(CLASSNAME, methodName);
		return procesDetail;
	}
	/**
	 * 
	 * @param vType
	 * @param vehicleId
	 * @param fromDate
	 * @param toDate
	 * @param level
	 * @return
	 * @throws BusinessException
	 * @throws TechnicalException
	*/
	public List <RetailsAndPaymentsReport> generateRtlAndPayReport(List<String> vType,String vehicleId,java.sql.Date fromDate,java.sql.Date toDate,String level) throws BusinessException, TechnicalException
	{
		final String methodName = "generateRtlAndPayReport";
		LOGGER.enter(CLASSNAME, methodName);
		List<RetailsAndPaymentsReport> bList = null;		
		try {
			bList = local.getReportsService().generateRtlAndPayReport(vType,vehicleId,fromDate,toDate,level);
		}catch (TechnicalException te) {
			LOGGER.error("DPB.VEHICLE.BLOCK.REPORT.002", te);
			throw new TechnicalException("",te.getMessageKey());
		}
		return bList; 
	}
	/**
	 * 
	 * @return
	 * @throws TechnicalException
	 * @throws BusinessException
	 */
	public List<ReportDefinitionBean> generateReportList() throws TechnicalException,BusinessException{

		final String methodName = "generateReportList";
		LOGGER.enter(CLASSNAME, methodName);
		List<ReportDefinitionBean> rList = null;
		
			rList  = local.getReportsService().generateReportList();

			LOGGER.exit(CLASSNAME, methodName);
		return rList;
	}
	/**
	 * 
	 * @param reportId
	 * @return
	 * @throws TechnicalException
	 */
	public StringBuffer reportsGenerate(NetStarReport reportCriteria) throws TechnicalException{
		final String methodName = "reportsGenerate";
		LOGGER.enter(CLASSNAME, methodName);
		StringBuffer rList = null;
		try{
			rList  = rptHelper.reportsGenerate(reportCriteria);
		}
		catch (TechnicalException te) {
			throw new TechnicalException("",te.getMessageKey());
		}
		LOGGER.exit(CLASSNAME, methodName);
		return rList;
	}
	/**
	 * 
	 * @return
	 */
	public List<VistaFileProcessing> getDlrReserveExceptionList(List<String>  vehicleType, String month ,String year, String dealerIds)throws BusinessException,TechnicalException{
		final String methodName = "getVehicleExceptionList";
		LOGGER.enter(CLASSNAME, methodName);
		List<VistaFileProcessing> vehExpceptionList = local.getReportsService().getDlrReserveExceptionList(vehicleType, month ,year, dealerIds);
		LOGGER.exit(CLASSNAME, methodName);
		return vehExpceptionList;
		
	}
	public List<DleRsrvSumRptBean> getDlrRsrvSumReport(List<String> vehicles,int year,int month)throws BusinessException,TechnicalException{
		final String methodName = "getVehicleExceptionList";
		LOGGER.enter(CLASSNAME, methodName);
		List<DleRsrvSumRptBean> rList = local.getReportsService().getDlrRsrvSumReport(vehicles,year,month);
		LOGGER.exit(CLASSNAME, methodName);
		return rList;
		
	}
	//Vehicle Details Report-start
	public List<List<List<String>>> reportsGenerateStatic (NetStarReport reportCriteria, Boolean isGenerateExcel) throws TechnicalException {
		final String methodName = "reportsGenerateStatic";
		List<List<List<String>>> rList  = rptHelper.reportsGenerateStatic(reportCriteria, isGenerateExcel);
		//Vehicle Details Report-end
		LOGGER.enter(CLASSNAME, methodName);
		LOGGER.exit(CLASSNAME, methodName);
		return rList;
	}
	
	public StringBuffer reportsGenerate1(LeadershipReport reportCriteria) throws TechnicalException{
		final String methodName = "reportsGenerate";
		LOGGER.enter(CLASSNAME, methodName);
		StringBuffer rList = null;
		try{
			rList  = rptHelper.reportsGenerate1(reportCriteria);
		}
		catch (TechnicalException te) {
			throw new TechnicalException("",te.getMessageKey());
		}
		LOGGER.exit(CLASSNAME, methodName);
		return rList;
	}
	
	
}
