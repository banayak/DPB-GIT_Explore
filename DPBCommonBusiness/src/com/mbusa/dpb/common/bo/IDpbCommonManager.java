/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: IDpbCommonManager.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all common DB transactions.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Sep 03, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.bo;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.common.domain.BonusInfo;
import com.mbusa.dpb.common.domain.ConditionDefinition;
import com.mbusa.dpb.common.domain.DealerInfo;
import com.mbusa.dpb.common.domain.FileProcessLogMessages;
import com.mbusa.dpb.common.domain.KpiFile;
import com.mbusa.dpb.common.domain.LeadershipBonusDetails;
import com.mbusa.dpb.common.domain.LeadershipInfo;
import com.mbusa.dpb.common.domain.PaymentDealerInfo;
import com.mbusa.dpb.common.domain.PaymentInfo;
import com.mbusa.dpb.common.domain.ProgramDefinition;
import com.mbusa.dpb.common.domain.ProgramDefinitionBean;
import com.mbusa.dpb.common.domain.SchedulerProcess;
import com.mbusa.dpb.common.domain.VehicleConditions;
import com.mbusa.dpb.common.domain.VistaFileProcessing;

public interface IDpbCommonManager {
	
	public List<VehicleConditions> getVehicleConditions(String condType);
	public void insertIntoProcessLog (FileProcessLogMessages message);
	public List<String> checkForManualTasks();
	public List<SchedulerProcess> getPrevDayProcs();
	public void savePrevDayProcsIntoLogs(List<SchedulerProcess> preDayProcess);
	public void createProcForPrevDayProcs(List<SchedulerProcess> preDayProcess);
	public List<SchedulerProcess> getTodayTasks();
	public void saveFailedTasksIntoLogs(List<SchedulerProcess> processes,int count);
	public void updateFailedProcs(List<SchedulerProcess> processes, int count);
    public List<BonusInfo> retrievePaymentData (String paymentType,Date startDate,Date endDate,boolean ldrsp,Integer pgmId);
	
	public List<DealerInfo> getTerminatedDealerList(Date actualRtlDate);
	public List<String> getDealerFranchises(String dealerId );
	public List<BonusInfo> getBonusDetailsOnPONumber(String dealerId );
	public void insertBonusCalculationRecord(List<BonusInfo> bnsInfo,Integer parentProcId,String uId,boolean isTrConsider,boolean ldrsp);
	public List<VistaFileProcessing> getNonSettledVehicleDtlsForTerminatedDlr(String dlrId,java.sql.Date startDate, java.sql.Date endDate,List<String> pVehList);
	public List<ProgramDefinition> getActiveProgramsForVehicle(VistaFileProcessing vistaFileProcessing);
	
	public Map<String, List<ProgramDefinition>> retrieveDayVehicleProgramMap(Date rtlDate);
	public Map<String, List<ProgramDefinition>> retrieveVehicleProgramMap(String vehicleType, String paymentType);
	public KpiFile retrieveKpiFileListForDealer(String dealerId,String period, int year,int kpiId);
	public List<KpiFile> retrieveInnerQuarterAdjustList(String dealerId, String quarter, int year);
	public List<BonusInfo> retrieveCancelledPoList();
	//public List<DealerInfo> getKpiInformation(boolean isScheduler);	
	public List<ConditionDefinition> getConditionDefinitions(int programId);
	public Map<String, List<ProgramDefinition>> retrieveProcessDetails(Integer pId, String callType,String pType);
	public List<VistaFileProcessing> retrieveRtlVehicleDataGivenPeriod(java.sql.Date startDate, java.sql.Date endDate,String vType);
	public List<BonusInfo> calculateLdrshipBonusBasedOnProgram(List<BonusInfo> bonusInfo,int procId,String processType,String user);
	public List<LeadershipBonusDetails> retrieveLdrShipProcessDetails(Integer pId, String callType) ;
	public void updateBonusPaidRecords (Date startDate,Date endDate,String paymentType,String uId,Integer pId,boolean ldrsp,Integer pgId);
	public List<VistaFileProcessing> retrieveRtlVehicleDataGivenPeriodAndType(java.sql.Date startDate, java.sql.Date endDate,List<String> vType, boolean isSpecialPgm);
	public String checkTerminateAdjustmentCancelationStatus(java.sql.Date aDate);
	public java.sql.Date getActualProcessDate(Integer procId);
	public List<ProgramDefinitionBean> getSpecialPrograms();
	public Map<Integer,List<ConditionDefinition>> getSpecialConditions(List<ProgramDefinitionBean> specialPrograms);
	public List<KpiFile> getUpdatedKpiWithPercentageChange();
	public List<BonusInfo> getPreviousBonusRecordsForAdjuestment(String kpiId,String dlrId,java.sql.Date startDate, java.sql.Date endDate);
	public List<BonusInfo> getPreviousBonusRecordsForCancel(String poNumber,String dlrId,java.sql.Date retailDate, java.sql.Time retailTime);
	public List<BonusInfo> getBonusDetailsForCancel(String unblkVehId, int pgmId);
	public boolean isDealerTerminationCancelationProcLogs(Date aDate);
	public void insertIntoProcessTerminateCancelationLog(FileProcessLogMessages message,java.sql.Date aDate);
	public void updateDayIdAsCurrentDayId(java.sql.Date procDate,java.sql.Date updateDate,boolean flag);
	public List<DealerInfo> getTerminatedDlrListTillActualDate(Date aProcate);
	public List<Integer> getProcIdDetails(Date procDate);
	public void updateCalcData(Date beginDate, Date prevDate, Date currentDate, Date monthEndDate, Date qtrEndDate, Date qtrBeginDate, int k);
	public List<PaymentDealerInfo> getDealerData(Date beginDate,Date monthEndDate, Date prevDate, Date currentDate, Date qtrEndDate, Date qtrBeginDate, Date qtrDate, int n);
	public List<PaymentInfo> getPaymentData(Date beginDate, Date monthEndDate,Date prevDate, Date currentDate, Date qtrEndDate, Date qtrBeginDate, Date qtrDate, int n);
	public List<LeadershipInfo> getLdrshipData(Integer pgmId);
	
	//For Data Cleanup for process rerun
	public Map<Integer, String> cleanUpData(String procDate) throws Exception;
	
}
