/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: DpbCommonBean.java
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

package com.mbusa.dpb.business;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.mbusa.dpb.business.view.DpbCommonBeanLocal;
import com.mbusa.dpb.common.bo.DpbCommonManagerImpl;
import com.mbusa.dpb.common.bo.IDpbCommonManager;
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
import com.mbusa.dpb.common.factories.BOFactory;
import com.mbusa.dpb.common.logger.DPBLog;

/**
 * Session Bean implementation class DpbCommonBean
 */
@Stateless
@Local(DpbCommonBeanLocal.class)
public class DpbCommonBean implements DpbCommonBeanLocal {
	
	private static DPBLog LOGGER = DPBLog.getInstance(DpbCommonBean.class);
	static final private String CLASSNAME = DpbCommonBean.class.getName();
	private IDpbCommonManager commonManager  = (DpbCommonManagerImpl) BOFactory.getInstance().getImplementation(IDpbCommonManager.class);

    /**
     * Default constructor. 
     */
    public DpbCommonBean() {
        // TODO Auto-generated constructor stub
    }

    public List<VehicleConditions> getVehicleConditions(String condType) {
    	final String methodName = "getVehicleConditions";
		LOGGER.enter(CLASSNAME, methodName);
		List<VehicleConditions> conditions = commonManager.getVehicleConditions(condType);
		LOGGER.exit(CLASSNAME, methodName);
		return conditions;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertIntoProcessLog (FileProcessLogMessages message) {
    	final String methodName = "insertIntoProcessLog";
		LOGGER.enter(CLASSNAME, methodName);
		commonManager.insertIntoProcessLog(message);
		LOGGER.exit(CLASSNAME, methodName);
    }

	public List<String> checkForManualTasks() {
		final String methodName = "checkForManualTasks";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> manualTasks = commonManager.checkForManualTasks();
		LOGGER.exit(CLASSNAME, methodName);
		return manualTasks;
	}
	
	public List<SchedulerProcess> getPrevDayProcs() {
		final String methodName = "getPrevDayProcs";
		LOGGER.enter(CLASSNAME, methodName);
		List<SchedulerProcess> processes = commonManager.getPrevDayProcs();
		LOGGER.exit(CLASSNAME, methodName);
		return processes;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void savePrevDayProcs(List<SchedulerProcess> preDayProcess) {
		final String methodName = "savePrevDayProcs";
		LOGGER.enter(CLASSNAME, methodName);
		commonManager.savePrevDayProcsIntoLogs(preDayProcess);
		commonManager.createProcForPrevDayProcs(preDayProcess);
		LOGGER.exit(CLASSNAME, methodName);
	}

	@Override
	public List<SchedulerProcess> getTodayTasks() {
		final String methodName = "getTodayTasks";
		LOGGER.enter(CLASSNAME, methodName);
		List<SchedulerProcess> processes = commonManager.getTodayTasks();
		LOGGER.exit(CLASSNAME, methodName);
		return processes;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateTasksAsFailed(List<SchedulerProcess> processes,
			int count) {
		final String methodName = "getTodayTasks";
		LOGGER.enter(CLASSNAME, methodName);
		commonManager.saveFailedTasksIntoLogs(processes, count);
		commonManager.updateFailedProcs(processes, count);
		LOGGER.exit(CLASSNAME, methodName);
	}
	public ProgramDefinition retrivePaymentDefinition (int processID){
    	final String methodName = "retrivePaymentDefinition";
		LOGGER.enter(CLASSNAME, methodName);
		ProgramDefinition pDefinition =  null;//commonManager.retrivePaymentDefinition(processID);
		LOGGER.exit(CLASSNAME, methodName);
		return pDefinition;
	    }
	public List<BonusInfo> retrievePaymentData (String paymentType,Date startDate,Date endDate,boolean ldrsp,Integer pgmId){
	    final String methodName = "retrivePaymentDefinition";
		LOGGER.enter(CLASSNAME, methodName);
		List<BonusInfo> paymentList = commonManager.retrievePaymentData(paymentType,startDate,endDate, ldrsp,pgmId);
		LOGGER.exit(CLASSNAME, methodName);
		return paymentList;
	}	
	public List<DealerInfo> getTerminatedDealerList(Date actualRtlDate){
    	final String methodName = "getTerminatedDealerList";
		LOGGER.enter(CLASSNAME, methodName);
		List<DealerInfo> tDealer = commonManager.getTerminatedDealerList(actualRtlDate);
		LOGGER.exit(CLASSNAME, methodName);
		return tDealer;
	}
	public List<String> getDealerFranchises(String dealerId ){
    	final String methodName = "getDealerFranchises";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> franChises = commonManager.getDealerFranchises(dealerId);
		LOGGER.exit(CLASSNAME, methodName);
		return franChises;
	}
	public List<BonusInfo> getBonusDetailsOnPONumber(String dealerId){
    	final String methodName = "getBonusDetailsOnPONumber";
		LOGGER.enter(CLASSNAME, methodName);
		List<BonusInfo> bnsInfo = commonManager.getBonusDetailsOnPONumber(dealerId);
		LOGGER.exit(CLASSNAME, methodName);
		return bnsInfo;
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void insertBonusCalculationRecord(List<BonusInfo> bnsInfo,Integer parentProcId,String uId,boolean isTrConsider,boolean ldrsp){
    	final String methodName = "insertBonusCalculationRecord";
		LOGGER.enter(CLASSNAME, methodName);
		commonManager.insertBonusCalculationRecord(bnsInfo,parentProcId,uId,isTrConsider,ldrsp);
		LOGGER.exit(CLASSNAME, methodName);
	}
	public List<VistaFileProcessing> getNonSettledVehicleDtlsForTerminatedDlr(String dlrId,java.sql.Date startDate, java.sql.Date endDate,List<String> pVehList){
    	final String methodName = "getNonSettledVehicleDtlsForTerminatedDlr";
		LOGGER.enter(CLASSNAME, methodName);
		List<VistaFileProcessing> rtlVehList = commonManager.getNonSettledVehicleDtlsForTerminatedDlr(dlrId,startDate,endDate,pVehList);
		LOGGER.exit(CLASSNAME, methodName);
		return rtlVehList;
	}
	public List<ProgramDefinition> getActiveProgramsForVehicle(VistaFileProcessing vistaFileProcessing){
    	final String methodName = "getTerminatedDealerList";
		LOGGER.enter(CLASSNAME, methodName);
		List<ProgramDefinition> pgmDef  = commonManager.getActiveProgramsForVehicle(vistaFileProcessing);
		LOGGER.exit(CLASSNAME, methodName);
		return pgmDef;
	}
	
	public Map<String, List<ProgramDefinition>> retrieveDayVehicleProgramMap(Date rtlDate){
    	final String methodName = "retrieveDayVehicleProgramMap";
		LOGGER.enter(CLASSNAME, methodName);
		Map<String, List<ProgramDefinition>> pgmMap = commonManager.retrieveDayVehicleProgramMap(rtlDate);
		LOGGER.exit(CLASSNAME, methodName);
		return pgmMap;
	}
	public Map<String, List<ProgramDefinition>> retrieveVehicleProgramMap(String vehicleType, String paymentType){
    	final String methodName = "retrieveVehicleProgramMap";
		LOGGER.enter(CLASSNAME, methodName);
		Map<String, List<ProgramDefinition>> pgmMap = commonManager.retrieveVehicleProgramMap(vehicleType,paymentType);
		LOGGER.exit(CLASSNAME, methodName);
		return pgmMap;		
	}
	public KpiFile retrieveKpiFileListForDealer(String dealerId,String period, int year, int kpiId){
    	final String methodName = "retrieveKpiFileListForDealer";
		LOGGER.enter(CLASSNAME, methodName);
		KpiFile kpiExtract = commonManager.retrieveKpiFileListForDealer(dealerId,period,year,kpiId);
		LOGGER.exit(CLASSNAME, methodName);
		return kpiExtract;
	}
	public List<KpiFile> retrieveInnerQuarterAdjustList(String dealerId, String quarter, int year)
	{
    	final String methodName = "retrieveInnerQuarterAdjustList";
		LOGGER.enter(CLASSNAME, methodName);
		List<KpiFile> kpiList = commonManager.retrieveInnerQuarterAdjustList(dealerId,quarter,year);
		LOGGER.exit(CLASSNAME, methodName);
		return kpiList;
	}
	public List<BonusInfo> retrieveCancelledPoList(){
    	final String methodName = "retrieveCancelledPoList";
		LOGGER.enter(CLASSNAME, methodName);
		List<BonusInfo> rtlVehList = commonManager.retrieveCancelledPoList();
		LOGGER.exit(CLASSNAME, methodName);
		return rtlVehList;
	}
	public List<ConditionDefinition> getConditionDefinitions(int programId){
		final String methodName = "getConditionDefinitions";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = commonManager.getConditionDefinitions(programId);
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}
	public Map<String, List<ProgramDefinition>> retrieveProcessDetails(Integer pId, String callType,String pType){
    	final String methodName = "retrieveProcessDetails";
		LOGGER.enter(CLASSNAME, methodName);
		Map<String, List<ProgramDefinition>> pgmMap = commonManager.retrieveProcessDetails(pId, callType,pType);
		LOGGER.exit(CLASSNAME, methodName);
		return pgmMap;
	}
	public List<VistaFileProcessing> retrieveRtlVehicleDataGivenPeriod(java.sql.Date startDate, java.sql.Date endDate,String vType){
    	final String methodName = "retrieveRtlVehicleDataGivenPeriod";
		LOGGER.enter(CLASSNAME, methodName);
		List<VistaFileProcessing> rtlVehicleList= commonManager.retrieveRtlVehicleDataGivenPeriod(startDate,endDate,vType);
		LOGGER.exit(CLASSNAME, methodName);
		return rtlVehicleList;
	}
	@Override
	public List<BonusInfo> calculateLdrshipBonusBasedOnProgram (List<BonusInfo> bonusInfo,int procId,String processType,String user) {
    	final String methodName = "calculateLdrshipBonusBasedOnProgram";
		LOGGER.enter(CLASSNAME, methodName);
		List<BonusInfo> lstldrsp = commonManager.calculateLdrshipBonusBasedOnProgram(bonusInfo,procId,processType,user);
		LOGGER.exit(CLASSNAME, methodName);
		return lstldrsp;
    }
	public List<LeadershipBonusDetails> retrieveLdrShipProcessDetails(Integer pId, String callType) {
		final String methodName = "retrieveLdrShipProcessDetails";
		LOGGER.enter(CLASSNAME, methodName);
		List<LeadershipBonusDetails> pgmMap = commonManager.retrieveLdrShipProcessDetails(pId, callType);
		LOGGER.exit(CLASSNAME, methodName);
		return pgmMap;
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateBonusPaidRecords (Date startDate,Date endDate,String paymentType,String uId, Integer pId,boolean ldrsp,Integer pgId){
		final String methodName = "updateBonusPaidRecords";
		LOGGER.enter(CLASSNAME, methodName);
		commonManager.updateBonusPaidRecords(startDate,endDate,paymentType,uId,pId, ldrsp,pgId);
		LOGGER.exit(CLASSNAME, methodName);		
	}
	@Override
	public List<VistaFileProcessing> retrieveRtlVehicleDataGivenPeriodAndType(java.sql.Date startDate, java.sql.Date endDate,List<String> vType, boolean isSpecialPgm){
    	final String methodName = "retrieveRtlVehicleDataGivenPeriod";
		LOGGER.enter(CLASSNAME, methodName);
		List<VistaFileProcessing> rtlVehicleList= commonManager.retrieveRtlVehicleDataGivenPeriodAndType(startDate,endDate,vType,isSpecialPgm);
		LOGGER.exit(CLASSNAME, methodName);
		return rtlVehicleList;
	}
	@Override
	public String checkTerminateAdjustmentCancelationStatus(java.sql.Date aDate){
    	final String methodName = "checkTerminateAdjustmentCancelationStatus";
		LOGGER.enter(CLASSNAME, methodName);
		String  status = commonManager.checkTerminateAdjustmentCancelationStatus(aDate);
		LOGGER.exit(CLASSNAME, methodName);
		return status;
	}
	@Override
	public java.sql.Date getActualProcessDate(Integer procId){
    	final String methodName = "getActualProcessDate";
		LOGGER.enter(CLASSNAME, methodName);
		java.sql.Date aDate = commonManager.getActualProcessDate(procId);
		LOGGER.exit(CLASSNAME, methodName);
		return aDate;
	}
	public List<ProgramDefinitionBean> getSpecialPrograms(){
		final String methodName = "getSpecialPrograms";
		LOGGER.enter(CLASSNAME, methodName);
		List<ProgramDefinitionBean> specialPrograms = null;
		specialPrograms = commonManager.getSpecialPrograms();
		LOGGER.exit(CLASSNAME, methodName);
		return specialPrograms;
	}
	public Map<Integer,List<ConditionDefinition>> getSpecialConditions(List<ProgramDefinitionBean> specialPrograms){
		final String methodName = "getSpecialConditions";
		LOGGER.enter(CLASSNAME, methodName);
		Map<Integer,List<ConditionDefinition>> splCond = null;
		splCond = commonManager.getSpecialConditions(specialPrograms);
		LOGGER.exit(CLASSNAME, methodName);
		return splCond;
	}
	public List<KpiFile> getUpdatedKpiWithPercentageChange(){
		final String methodName = "getUpdatedKpiWithPercentageChange";
		LOGGER.enter(CLASSNAME, methodName);
		List<KpiFile> kpiChangeList = null;
		kpiChangeList = commonManager.getUpdatedKpiWithPercentageChange();
		LOGGER.exit(CLASSNAME, methodName);
		return kpiChangeList;
	}
	public List<BonusInfo> getPreviousBonusRecordsForAdjuestment(String kpiId,String dlrId,java.sql.Date startDate, java.sql.Date endDate){
		final String methodName = "getPreviousBonusRecordsForAdjuestment";
		LOGGER.enter(CLASSNAME, methodName);
		List<BonusInfo> bnsList = null;
		bnsList = commonManager.getPreviousBonusRecordsForAdjuestment(kpiId,dlrId,startDate,endDate);
		LOGGER.exit(CLASSNAME, methodName);
		return bnsList;
	}
	public List<BonusInfo> getPreviousBonusRecordsForCancel(String poNumber,String dlrId,java.sql.Date retailDate, java.sql.Time retailTime){
		final String methodName = "getPreviousBonusRecordsForCancel";
		LOGGER.enter(CLASSNAME, methodName);
		List<BonusInfo> bnsList = null;
		bnsList = commonManager.getPreviousBonusRecordsForCancel(poNumber,dlrId,retailDate,retailTime);
		LOGGER.exit(CLASSNAME, methodName);
		return bnsList;
	}	
	public List<BonusInfo> getBonusDetailsForCancel(String unblkVehId, int pgmId){
    	final String methodName = "getBonusDetailsForCancel";
		LOGGER.enter(CLASSNAME, methodName);
		List<BonusInfo> bnsInfo = commonManager.getBonusDetailsForCancel(unblkVehId, pgmId);
		LOGGER.exit(CLASSNAME, methodName);
		return bnsInfo;
	}		
	public boolean isDealerTerminationCancelationProcLogs(java.sql.Date aDate){
		final String methodName = "isDealerTerminationCancelationProcLogs";
		LOGGER.enter(CLASSNAME, methodName);
		boolean status = false;
		status = commonManager.isDealerTerminationCancelationProcLogs(aDate);
		LOGGER.exit(CLASSNAME, methodName);
		return status;
	}	 
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertIntoProcessTerminateCancelationLog(FileProcessLogMessages message,java.sql.Date aDate) {
    	final String methodName = "insertIntoProcessTerminateCancelationLog";
		LOGGER.enter(CLASSNAME, methodName);
		commonManager.insertIntoProcessTerminateCancelationLog(message,aDate);
		LOGGER.exit(CLASSNAME, methodName);
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateDayIdAsCurrentDayId(java.sql.Date procDate,java.sql.Date updateDate,boolean flag)
    {
    	final String methodName = "updateDayIdAsCurrentDayId";
		LOGGER.enter(CLASSNAME, methodName);
		commonManager.updateDayIdAsCurrentDayId(procDate,updateDate,flag);
		LOGGER.exit(CLASSNAME, methodName);
    }
    /**
     * 
     */
    public List<DealerInfo> getTerminatedDlrListTillActualDate(Date actualRtlDate){
    	final String methodName = "getTerminatedDlrListTillActualDate";
		LOGGER.enter(CLASSNAME, methodName);
		List<DealerInfo> tDealer = commonManager.getTerminatedDlrListTillActualDate(actualRtlDate);
		LOGGER.exit(CLASSNAME, methodName);
		return tDealer;
	}
   
    public List<PaymentDealerInfo> getDealerData(Date beginDate,Date monthEndDate,Date prevDate,Date currentDate,Date qtrEndDate,Date qtrBeginDate,Date qtrDate,int n){
	    final String methodName = "getDealerData";
		LOGGER.enter(CLASSNAME, methodName);
		List<PaymentDealerInfo> dealerList = commonManager.getDealerData(beginDate, monthEndDate, prevDate,currentDate,qtrEndDate,qtrBeginDate,qtrDate,n);
		LOGGER.exit(CLASSNAME, methodName);
		return dealerList;
	}	
   
    public List<PaymentInfo> getPaymentData(Date beginDate, Date monthEndDate,Date prevDate,Date currentDate,Date qtrEndDate,Date qtrBeginDate,Date qtrDate, int n){
	    final String methodName = "getPaymentData";
		LOGGER.enter(CLASSNAME, methodName);
		List<PaymentInfo> paymentList = commonManager.getPaymentData(beginDate, monthEndDate,prevDate,currentDate,qtrEndDate,qtrBeginDate,qtrDate,n);
		LOGGER.exit(CLASSNAME, methodName);
		return paymentList;
	}	
    
    public List<Integer> getProcIdDetails(Date currentDate){
	    final String methodName = "getProcIdDetails";
		LOGGER.enter(CLASSNAME, methodName);
		List<Integer> procIdList = commonManager.getProcIdDetails(currentDate);
		LOGGER.exit(CLASSNAME, methodName);
		return procIdList;
	}	
    
       
    public void updateCalcData(Date beginDate, Date prevDate,Date currentDate,Date monthEndDate,Date qtrEndDate,Date qtrBeginDate, int k){
	    final String methodName = "updateCalcData";
		LOGGER.enter(CLASSNAME, methodName);
		commonManager.updateCalcData(beginDate, prevDate,currentDate,monthEndDate,qtrEndDate,qtrBeginDate,k);
		LOGGER.exit(CLASSNAME, methodName);
	}	
    
    public List<LeadershipInfo> getLdrshipData(Integer pgmId){
	    final String methodName = "getLdrshipData";
		LOGGER.enter(CLASSNAME, methodName);
		List<LeadershipInfo> ldrList = commonManager.getLdrshipData(pgmId);
		LOGGER.exit(CLASSNAME, methodName);
		return ldrList;
	}

  //Data Cleanup for process rerun start
	/* (non-Javadoc)
	 * @see com.mbusa.dpb.business.view.DpbCommonBeanLocal#cleanUpData(java.lang.String)
	 */
	@Override
	public Map<Integer, String> cleanUpData(String procDate) throws Exception{
		Map<Integer, String> result = new HashMap<Integer, String>();
		final String methodName = "cleanUpData";
		LOGGER.enter(CLASSNAME, methodName);
		result = commonManager.cleanUpData(procDate);
		LOGGER.exit(CLASSNAME, methodName);
		return result;
	}	
	//Data Cleanup for process rerun end
}
