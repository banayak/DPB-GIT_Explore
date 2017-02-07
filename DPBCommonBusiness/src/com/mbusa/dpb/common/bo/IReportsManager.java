package com.mbusa.dpb.common.bo;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.common.domain.BlockedVehicle;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.DleRsrvSumRptBean;
import com.mbusa.dpb.common.domain.LeadershipReport;
import com.mbusa.dpb.common.domain.NetStarReport;
import com.mbusa.dpb.common.domain.ReportDefinitionBean;
import com.mbusa.dpb.common.domain.RetailsAndPaymentsReport;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.BusinessException;


public interface IReportsManager {

	public List<BlockedVehicle> getBlockVehicleReport(String dealerId,String vehicleId,Date fromDate,Date toDate);

	public Map<String, Object> generateReport(List<String> vehicleType,String viewAccountVin, int dealer, Date fromDate, Date toDate, int year);

	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId);

	public List <RetailsAndPaymentsReport> generateRtlAndPayReport(List<String> vType,String vehicleId,java.sql.Date fromDate,java.sql.Date toDate,String level);

	public List<ReportDefinitionBean> generateReportList()throws BusinessException;

	public StringBuffer reportsGenerate(NetStarReport reportCriteria, boolean isFromScheduler);

	public ReportDefinitionBean retrieveReportId(int processId);
	
	public List<VistaFileProcessing> getDlrReserveExceptionList(List<String>  vehicleType, String month ,String year, String dealerIds);
	public List<DleRsrvSumRptBean> getDlrRsrvSumReport(List<String> vehicles,int year,int month);
	//Vehicle Details Report-start
	public List<List<List<String>>> reportsGenerateStatic (NetStarReport reportCriteria, Boolean isGenerateExcel);
	//Vehicle Details Report-end
	
	public StringBuffer reportsGenerate1(LeadershipReport reportCriteria, boolean isFromScheduler);
}
