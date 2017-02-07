package com.mbusa.dpb.common.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.common.domain.BlockedVehicle;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.DleRsrvSumRptBean;
import com.mbusa.dpb.common.domain.ReportDefinitionBean;
import com.mbusa.dpb.common.domain.RetailDate;
import com.mbusa.dpb.common.domain.RetailsAndPaymentsReport;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.BusinessException;


public interface IReportsDAO {

	public List<BlockedVehicle> getBlockVehicleReport(String dealerId,String vehicleId,Date fromDate,Date toDate);

	public Map<String, Object> generateReport(List<String> vehicleType,String viewAccountVin, int dealer, Date fromDate, Date toDate, int year);

	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId);

	public List<RetailsAndPaymentsReport> generateRtlAndPayReport(List<String> vType,String vehicleId, java.sql.Date fromDate, java.sql.Date toDate, String level);

	public List<ReportDefinitionBean> generateReportList()throws BusinessException;

	public List reportsGenerate(int reportId);

	public List<List<String>> getQueryData(String qry);

	public ReportDefinitionBean retrieveReportId(int processId);
	
	public List<VistaFileProcessing> getDlrReserveExceptionList(List<String>  vehicleType, String month ,String year, String dealerIds);

	public List<DleRsrvSumRptBean> getDlrRsrvSumReport(List<String> vehicles,int year,int month);

	public String fetchQuery(String queryName);
	
	public Map<String,String> fetchQuery(List<String> queryNames);

	public int getReportLpp(int rptId);
	public List<RetailDate> getRetailDates(String fromYr, String toYr, String fromMon, String toMon, String fromQtr, String toQtr, boolean isMonth);
	
	/*DCQPR Start - Performance Improvement 10/06/2016*/
	public List<String> getDealerData(String string1);
	/*DCQPR End Performance Improvement 10/06/2016*/
}