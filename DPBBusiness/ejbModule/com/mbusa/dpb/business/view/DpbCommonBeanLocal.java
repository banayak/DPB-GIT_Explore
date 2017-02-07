/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: DpbCommonBeanLocal.java
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

package com.mbusa.dpb.business.view;

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
import com.mbusa.dpb.common.exceptions.BusinessException;

public interface DpbCommonBeanLocal {
	public List<VehicleConditions> getVehicleConditions(String condType);
	public void insertIntoProcessLog (FileProcessLogMessages message);
	public List<String> checkForManualTasks();
	public List<SchedulerProcess> getPrevDayProcs();
	public void savePrevDayProcs(List<SchedulerProcess> preDayProcess);
	public List<SchedulerProcess> getTodayTasks();
	public void updateTasksAsFailed(List<SchedulerProcess> processes,int count);
    public ProgramDefinition retrivePaymentDefinition (int processID);
	public List<BonusInfo> retrievePaymentData (String paymentType,Date startDate,Date endDate,boolean ldrsp,Integer pgmId) throws BusinessException;
	public List<DealerInfo> getTerminatedDealerList(Date actualRtlDate) throws BusinessException;
	public List<String> getDealerFranchises(String dealerId ) throws BusinessException;
	public List<BonusInfo> getBonusDetailsOnPONumber(String dealerId ) throws BusinessException;
	public void insertBonusCalculationRecord(List<BonusInfo> bnsInfo,Integer parentProcId,String uId,boolean isTrConsider,boolean ldrsp) throws BusinessException;
	//public List<VistaFileProcessing> getNonSettledVehicleDtlsForTerminatedDlr(List<String> calPoList);
	public List<VistaFileProcessing> getNonSettledVehicleDtlsForTerminatedDlr(String dlrId,java.sql.Date startDate, java.sql.Date endDate,List<String> pVehList)  throws BusinessException;
	public List<ProgramDefinition> getActiveProgramsForVehicle(VistaFileProcessing vistaFileProcessing)  throws BusinessException;
	public Map<String, List<ProgramDefinition>> retrieveDayVehicleProgramMap(Date rtlDate) throws BusinessException;
	public Map<String, List<ProgramDefinition>> retrieveVehicleProgramMap(String vehicleType, String paymentType) throws BusinessException;
	public KpiFile retrieveKpiFileListForDealer(String dealerId,String period, int year, int kpiId) throws BusinessException;
	public List<KpiFile> retrieveInnerQuarterAdjustList(String dealerId, String quarter, int year) throws BusinessException;
	public List<BonusInfo> retrieveCancelledPoList() throws BusinessException;
	//public List<DealerInfo> getKpiInformation(boolean isScheduler);
	public List<ConditionDefinition> getConditionDefinitions(int programId) throws BusinessException;
	public Map<String, List<ProgramDefinition>> retrieveProcessDetails(Integer pId, String callType,String pType) throws BusinessException;
	public List<VistaFileProcessing> retrieveRtlVehicleDataGivenPeriod(java.sql.Date startDate, java.sql.Date endDate,String vType) throws BusinessException;
	public List<BonusInfo> calculateLdrshipBonusBasedOnProgram(List<BonusInfo> bonusInfo,int procId,String processType,String user) throws BusinessException;
	public List<LeadershipBonusDetails> retrieveLdrShipProcessDetails(Integer pId, String callType) throws BusinessException;
	public void updateBonusPaidRecords (Date startDate,Date endDate,String paymentType,String uId,Integer pId,boolean ldrsp,Integer pgId) throws BusinessException;
	public List<VistaFileProcessing> retrieveRtlVehicleDataGivenPeriodAndType(java.sql.Date startDate, java.sql.Date endDate,List<String> vType, boolean isSpecialPgm);
	public String checkTerminateAdjustmentCancelationStatus(java.sql.Date aDate);
	public java.sql.Date getActualProcessDate(Integer procId);
	public List<ProgramDefinitionBean> getSpecialPrograms();
	public Map<Integer,List<ConditionDefinition>> getSpecialConditions(List<ProgramDefinitionBean> specialPrograms);
	public List<KpiFile> getUpdatedKpiWithPercentageChange()  throws BusinessException;
	public List<BonusInfo> getPreviousBonusRecordsForAdjuestment(String kpiId,String dlrId,java.sql.Date startDate, java.sql.Date endDate) throws BusinessException;
	public List<BonusInfo> getPreviousBonusRecordsForCancel(String poNumber,String dlrId,java.sql.Date retailDate, java.sql.Time retailTime) throws BusinessException;
	public List<BonusInfo> getBonusDetailsForCancel(String dpbUnblkVehId, int pgmId) throws BusinessException;
	public boolean isDealerTerminationCancelationProcLogs(Date aDate);
	public void insertIntoProcessTerminateCancelationLog (FileProcessLogMessages message,java.sql.Date aDate);
	public void updateDayIdAsCurrentDayId(java.sql.Date procDate,java.sql.Date updateDate,boolean flag);
	public List<DealerInfo> getTerminatedDlrListTillActualDate(Date aProcate);
	public List<Integer> getProcIdDetails(java.sql.Date procDate);
	public void updateCalcData(java.sql.Date beginDate,java.sql.Date prevDate, Date currentDate, Date monthEndDate, Date qtrEndDate, Date qtrBeginDate, int k);
	public List<PaymentDealerInfo> getDealerData(java.sql.Date beginDate,java.sql.Date monthEndDate, java.sql.Date prevDate,java.sql.Date currentDate, Date qtrEndDate, Date qtrBeginDate, Date qtrDate, int n);
	public List<PaymentInfo> getPaymentData(java.sql.Date beginDate,java.sql.Date monthEndDate, java.sql.Date prevDate,	java.sql.Date currentDate, Date qtrEndDate, Date qtrBeginDate, Date qtrDate, int n);
	public List<LeadershipInfo> getLdrshipData(Integer pgmId);
	
	//For Data Cleanup for process rerun
	public Map<Integer, String> cleanUpData(String procDate) throws Exception;
	
}
