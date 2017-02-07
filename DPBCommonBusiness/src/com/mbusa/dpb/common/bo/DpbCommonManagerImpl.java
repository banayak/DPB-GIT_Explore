/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: DpbCommonManagerImpl.java
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.dao.DPBCommonDAOImpl;
import com.mbusa.dpb.common.dao.IDPBCommonDAO;
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
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.DPBCommonHelper;

public class DpbCommonManagerImpl implements IDpbCommonManager{
	
	private static DPBLog LOGGER = DPBLog.getInstance(DpbCommonManagerImpl.class);
	static final private String CLASSNAME = DpbCommonManagerImpl.class.getName();
	IDPBCommonDAO daoImpl = new DPBCommonDAOImpl();
	private DPBCommonHelper commonHelper = new DPBCommonHelper();
	static PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
	
	public List<VehicleConditions> getVehicleConditions(String condType) {
		final String methodName = "getVehicleConditions";
		LOGGER.enter(CLASSNAME, methodName);
		List<VehicleConditions> conditions = daoImpl.getVehicleConditions(condType);
		LOGGER.exit(CLASSNAME, methodName);
		return conditions;
	}
	
	public void insertIntoProcessLog (FileProcessLogMessages message) {
    	final String methodName = "insertIntoProcessLog";
		LOGGER.enter(CLASSNAME, methodName);
		daoImpl.insertIntoProcessLog(message);
		LOGGER.exit(CLASSNAME, methodName);
    }
	
	public List<String> checkForManualTasks() {
		final String methodName = "checkForManualTasks";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> manualTasks = daoImpl.checkForManualTasks();
		LOGGER.exit(CLASSNAME, methodName);
		return manualTasks;
	}
	
	public List<SchedulerProcess> getPrevDayProcs() {
		final String methodName = "getPrevDayProcs";
		LOGGER.enter(CLASSNAME, methodName);
		List<SchedulerProcess> processes = daoImpl.getPrevDayProcs();
		LOGGER.exit(CLASSNAME, methodName);
		return processes;
	}

	@Override
	public void savePrevDayProcsIntoLogs(List<SchedulerProcess> preDayProcess) {
		final String methodName = "savePrevDayProcsIntoLogs";
		LOGGER.enter(CLASSNAME, methodName);
		daoImpl.savePrevDayProcsIntoLogs(preDayProcess);
		LOGGER.exit(CLASSNAME, methodName);
		
	}

	@Override
	public void createProcForPrevDayProcs(List<SchedulerProcess> preDayProcess) {
		final String methodName = "createProcForPrevDayProcs";
		LOGGER.enter(CLASSNAME, methodName);
		daoImpl.createProcForPrevDayProcs(preDayProcess);
		LOGGER.exit(CLASSNAME, methodName);
	}

	@Override
	public List<SchedulerProcess> getTodayTasks() {
		final String methodName = "getTodayTasks";
		LOGGER.enter(CLASSNAME, methodName);
		List<SchedulerProcess> processes = daoImpl.getTodayTasks();
		LOGGER.exit(CLASSNAME, methodName);
		return processes;
	}

	@Override
	public void saveFailedTasksIntoLogs(List<SchedulerProcess> processes,int count) {
		final String methodName = "saveFailedTasksIntoLogs";
		LOGGER.enter(CLASSNAME, methodName);
		daoImpl.saveFailedTasksIntoLogs(processes, count);
		LOGGER.exit(CLASSNAME, methodName);
	}

	@Override
	public void updateFailedProcs(List<SchedulerProcess> processes, int count) {
		final String methodName = "updateFailedProcs";
		LOGGER.enter(CLASSNAME, methodName);
		daoImpl.updateFailedProcs(processes, count);
		LOGGER.exit(CLASSNAME, methodName);
	}
public ProgramDefinition retrivePaymentDefinition (int processID){
    	final String methodName = "retrivePaymentDefinition";
		LOGGER.enter(CLASSNAME, methodName);
		ProgramDefinition pDefinition = daoImpl.retrivePaymentDefinition(processID);
		LOGGER.exit(CLASSNAME, methodName);
		return pDefinition;
    }
	public List<BonusInfo> retrievePaymentData (String paymentType,Date startDate,Date endDate,boolean ldrsp,Integer pgmId){
    	final String methodName = "retrievePaymentData";
		LOGGER.enter(CLASSNAME, methodName);		
		List<BonusInfo> bnsInfoList = daoImpl.retrievePaymentData(paymentType, startDate,endDate,ldrsp,pgmId);
		LOGGER.exit(CLASSNAME, methodName);
		return bnsInfoList;
    }	
	public List<DealerInfo> getTerminatedDealerList(Date actualRtlDate){
    	final String methodName = "getTerminatedDealerList";
		LOGGER.enter(CLASSNAME, methodName);
		List<DealerInfo> tDealer = daoImpl.getTerminatedDealerList(actualRtlDate);
		LOGGER.exit(CLASSNAME, methodName);
		return tDealer;
	}
	public List<String> getDealerFranchises(String dealerId ){
    	final String methodName = "getDealerFranchises";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> franChises = daoImpl.getDealerFranchises(dealerId);
		LOGGER.exit(CLASSNAME, methodName);
		return franChises;
	}
	public List<BonusInfo> getBonusDetailsOnPONumber(String dealerId){
    	final String methodName = "getTerminatedDealerList";
		LOGGER.enter(CLASSNAME, methodName);
		List<BonusInfo> bnsInfo = daoImpl.getBonusDetailsOnPONumber(dealerId);
		LOGGER.exit(CLASSNAME, methodName);
		return bnsInfo;
	}
	public List<BonusInfo> getBonusDetailsForCancel(String unblkVehId, int pgmId){
    	final String methodName = "getBonusDetailsForCancel";
		LOGGER.enter(CLASSNAME, methodName);
		List<BonusInfo> bnsInfo = daoImpl.getBonusDetailsForCancel(unblkVehId, pgmId);
		LOGGER.exit(CLASSNAME, methodName);
		return bnsInfo;
	}	
	public void insertBonusCalculationRecord(List<BonusInfo> bnsInfo,Integer parentProcId,String uId,boolean isTrCompleted,boolean ldrsp){
    	final String methodName = "insertBonusCalculationRecord";
		LOGGER.enter(CLASSNAME, methodName);
		daoImpl.insertIntoProcessLog(commonHelper.getProcessLog(parentProcId,"Insertion of bonus calculated records in database is in progress.",
				IConstants.PROC_STATUS_IN_PROCESS,uId));
		daoImpl.insertBonusCalculationRecord(bnsInfo,ldrsp);
		java.sql.Date aProcDate = daoImpl.getActualProcessDate(parentProcId); 
		List<Integer> logsUpdated  = new ArrayList<Integer>();
		/*for(Iterator<BonusInfo> itr =bnsInfo.iterator();itr.hasNext();){			
			BonusInfo bns = itr.next();
			Integer tProcId = PROPERTY_MANAGER.getPropertyAsInteger("termination.bonus.process.id");
			Integer cProcId = PROPERTY_MANAGER.getPropertyAsInteger("cancelation.bonus.process.id");
			if(IConstants.BONUS_STATUS_TERMINATED_CALCULATED.equalsIgnoreCase(bns.getStatus())
					&& logsUpdated != null && !logsUpdated.contains(tProcId)){
				daoImpl.insertIntoProcessTerminateCancelationLog(commonHelper.getProcessLog(tProcId,
											"Termination Process considered. ",
											IConstants.PROC_STATUS_SUCCESS,bns.getUserId()),aProcDate);
				logsUpdated.add(tProcId);
			}
			if(IConstants.SUB_PROC_CANCELATION_PO_STATUS.equalsIgnoreCase(bns.getStatus())
							&& logsUpdated != null && !logsUpdated.contains(bns.getProcessId())){
				daoImpl.insertIntoProcessTerminateCancelationLog(commonHelper.getProcessLog(cProcId,
						"Cancelation process completed.",IConstants.PROC_STATUS_SUCCESS,bns.getUserId()),aProcDate);
				logsUpdated.add(cProcId);
			}
		}*/
		if(isTrCompleted){
			Integer tProcId = PROPERTY_MANAGER.getPropertyAsInteger("termination.bonus.process.id");			
			daoImpl.insertIntoProcessTerminateCancelationLog(commonHelper.getProcessLog(tProcId,
											"Termination Process considered. ",
												IConstants.PROC_STATUS_SUCCESS,uId),aProcDate);
			logsUpdated.add(tProcId);
			/*
			Integer cProcId = PROPERTY_MANAGER.getPropertyAsInteger("cancelation.bonus.process.id");
			daoImpl.insertIntoProcessTerminateCancelationLog(commonHelper.getProcessLog(cProcId,
											"Cancelation process completed.",IConstants.PROC_STATUS_SUCCESS,uId),aProcDate);
			logsUpdated.add(cProcId);*/
		}
		daoImpl.insertIntoProcessLog(commonHelper.getProcessLog(parentProcId,"Bonus calculated records save successfully. Bonus process completed. Respective person will receive email notification soon.",
										IConstants.PROC_STATUS_SUCCESS,uId));
		LOGGER.exit(CLASSNAME, methodName);
	}
	
	public List<VistaFileProcessing> getNonSettledVehicleDtlsForTerminatedDlr(String dlrId,java.sql.Date startDate, java.sql.Date endDate,List<String> pVehList){
    	final String methodName = "getNonSettledVehicleDtlsForTerminatedDlr";
		LOGGER.enter(CLASSNAME, methodName);
		List<VistaFileProcessing> rtlVehList = daoImpl.getNonSettledVehicleDtlsForTerminatedDlr(dlrId,startDate,endDate,pVehList);
		LOGGER.exit(CLASSNAME, methodName);
		return rtlVehList;
	}
	public List<ProgramDefinition> getActiveProgramsForVehicle(VistaFileProcessing vistaFileProcessing){
    	final String methodName = "getActiveProgramsForVehicle";
		LOGGER.enter(CLASSNAME, methodName);
		List<ProgramDefinition> pgmDef  = daoImpl.getActiveProgramsForVehicle(vistaFileProcessing);
		LOGGER.exit(CLASSNAME, methodName);
		return pgmDef;
	}
	
	public Map<String, List<ProgramDefinition>> retrieveDayVehicleProgramMap(Date rtlDate){
    	final String methodName = "retrieveDayVehicleProgramMap";
		LOGGER.enter(CLASSNAME, methodName);
		Map<String, List<ProgramDefinition>> pgmMap = daoImpl.retrieveDayVehicleProgramMap(rtlDate);
		LOGGER.exit(CLASSNAME, methodName);
		return pgmMap;
	}
	public Map<String, List<ProgramDefinition>> retrieveVehicleProgramMap(String vehicleType, String paymentType){
    	final String methodName = "retrieveVehicleProgramMap";
		LOGGER.enter(CLASSNAME, methodName);
		Map<String, List<ProgramDefinition>> pgmMap = daoImpl.retrieveVehicleProgramMap(vehicleType,paymentType);
		LOGGER.exit(CLASSNAME, methodName);
		return pgmMap;
	}
	public KpiFile retrieveKpiFileListForDealer(String dealerId,String period, int year,int kpiId){
    	final String methodName = "retrieveKpiFileListForDealer";
		LOGGER.enter(CLASSNAME, methodName);
		KpiFile kpiExtract = daoImpl.retrieveKpiFileListForDealer(dealerId,period,year,kpiId);
		LOGGER.exit(CLASSNAME, methodName);
		return kpiExtract;
	}
	public List<KpiFile> retrieveInnerQuarterAdjustList(String dealerId, String quarter, int year)
	{
    	final String methodName = "retrieveInnerQuarterAdjustList";
		LOGGER.enter(CLASSNAME, methodName);
		List<KpiFile> kpiList = daoImpl.retrieveInnerQuarterAdjustList(dealerId,quarter,year);
		LOGGER.exit(CLASSNAME, methodName);
		return kpiList;
	}
	public List<BonusInfo> retrieveCancelledPoList(){
    	final String methodName = "retrieveCancelledPoList";
		LOGGER.enter(CLASSNAME, methodName);
		List<BonusInfo> calcelRtlVehList = daoImpl.retrieveCancelledPoList();
		LOGGER.exit(CLASSNAME, methodName);
		return calcelRtlVehList;
	}
	public List<ConditionDefinition> getConditionDefinitions(int programId){
		final String methodName = "getConditionDefinitions";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = daoImpl.getConditionDefinitions(programId);
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}
	public Map<String, List<ProgramDefinition>> retrieveProcessDetails(Integer pId, String callType,String pType){
    	final String methodName = "retrieveProcessDetails";
		LOGGER.enter(CLASSNAME, methodName);
		Map<String, List<ProgramDefinition>> pgmMap = daoImpl.retrieveProcessDetails(pId, callType,pType);
		LOGGER.exit(CLASSNAME, methodName);
		return pgmMap;
	}
	public List<VistaFileProcessing> retrieveRtlVehicleDataGivenPeriod(java.sql.Date startDate, java.sql.Date endDate,String vType){
    	final String methodName = "retrieveRtlVehicleDataGivenPeriod";
		LOGGER.enter(CLASSNAME, methodName);
		List<VistaFileProcessing> rtlVehicleList= daoImpl.retrieveRtlVehicleDataGivenPeriod(startDate,endDate,vType);
		LOGGER.exit(CLASSNAME, methodName);
		return rtlVehicleList;
	}
	public List<BonusInfo> calculateLdrshipBonusBasedOnProgram (List<BonusInfo> bonusInfo,int procId,String processType,String user) {
    	final String methodName = "calculateLdrshipBonusBasedOnProgram";
    	LOGGER.enter(CLASSNAME, methodName);	
    	List<BonusInfo> lstldrsp = daoImpl.calculateLdrshipBonusBasedOnProgram(bonusInfo,procId,processType,user);
		LOGGER.exit(CLASSNAME, methodName);
		return lstldrsp;
    }
	public List<LeadershipBonusDetails> retrieveLdrShipProcessDetails(Integer pId, String callType){
    	final String methodName = "retrieveLdrShipProcessDetails";
		LOGGER.enter(CLASSNAME, methodName);
		List<LeadershipBonusDetails> pgmMap = daoImpl.retrieveLdrShipProcessDetails(pId, callType);
		LOGGER.exit(CLASSNAME, methodName);
		return pgmMap;
	}
	public void updateBonusPaidRecords (Date startDate,Date endDate,String paymentType,String uId,Integer currentProdId,boolean ldrsp,Integer pgId ){
		final String methodName = "updateBonusPaidRecords";
		LOGGER.enter(CLASSNAME, methodName);
		List<Integer> updateRecord = daoImpl.retriveBonusPaidRecords(startDate,endDate,paymentType, ldrsp,pgId);
		daoImpl.updateBonusPaidStatusRecords(updateRecord, paymentType, uId,ldrsp);
		daoImpl.insertIntoProcessLog(commonHelper.getProcessLog(currentProdId.intValue(),"Payment generation is completed. Notification sent by e-mail.",IConstants.PROC_STATUS_SUCCESS,uId));
		LOGGER.exit(CLASSNAME, methodName);		
	}
	public List<VistaFileProcessing> retrieveRtlVehicleDataGivenPeriodAndType(java.sql.Date startDate, java.sql.Date endDate,List<String> vType, boolean isSpecialPgm){
    	final String methodName = "retrieveRtlVehicleDataGivenPeriod";
		LOGGER.enter(CLASSNAME, methodName);
		List<VistaFileProcessing> rtlVehicleList= daoImpl.retrieveRtlVehicleDataGivenPeriodAndType(startDate,endDate,vType,isSpecialPgm);
		LOGGER.exit(CLASSNAME, methodName);
		return rtlVehicleList;
	}
	
	public String checkTerminateAdjustmentCancelationStatus(java.sql.Date aDate){
    	final String methodName = "checkTerminateAdjustmentCancelationStatus";
		LOGGER.enter(CLASSNAME, methodName);
		String  status = daoImpl.checkTerminateAdjustmentCancelationStatus(aDate);
		LOGGER.exit(CLASSNAME, methodName);
		return status;
	}
	public java.sql.Date getActualProcessDate(Integer procId){
    	final String methodName = "getActualProcessDate";
		LOGGER.enter(CLASSNAME, methodName);
		java.sql.Date aDate = daoImpl.getActualProcessDate(procId);
		LOGGER.exit(CLASSNAME, methodName);
		return aDate;
	}
	public List<ProgramDefinitionBean> getSpecialPrograms(){
		final String methodName = "getSpecialPrograms";
		LOGGER.enter(CLASSNAME, methodName);
		List<ProgramDefinitionBean> specialPrograms = null;
		specialPrograms = daoImpl.getSpecialPrograms();
		LOGGER.exit(CLASSNAME, methodName);
		return specialPrograms;
	}
	public Map<Integer,List<ConditionDefinition>> getSpecialConditions(List<ProgramDefinitionBean> specialPrograms){
		final String methodName = "getSpecialConditions";
		LOGGER.enter(CLASSNAME, methodName);
		Map<Integer,List<ConditionDefinition>> splCond = null;
		splCond = daoImpl.getSpecialConditions(specialPrograms);
		LOGGER.exit(CLASSNAME, methodName);
		return splCond;
	}	
	public List<KpiFile> getUpdatedKpiWithPercentageChange(){
		final String methodName = "getUpdatedKpiWithPercentageChange";
		LOGGER.enter(CLASSNAME, methodName);
		List<KpiFile> kpiChangeList = null;
		kpiChangeList = daoImpl.getUpdatedKpiWithPercentageChange();
		LOGGER.exit(CLASSNAME, methodName);
		return kpiChangeList;
	}
	public List<BonusInfo> getPreviousBonusRecordsForAdjuestment(String kpiId,String dlrId,java.sql.Date startDate, java.sql.Date endDate){
		final String methodName = "getPreviousBonusRecordsForAdjuestment";
		LOGGER.enter(CLASSNAME, methodName);
		List<BonusInfo> bnsList = null;
		bnsList = daoImpl.getPreviousBonusRecordsForAdjuestment(kpiId,dlrId,startDate,endDate);
		LOGGER.exit(CLASSNAME, methodName);
		return bnsList;
	}
	public List<BonusInfo> getPreviousBonusRecordsForCancel(String poNumber,String dlrId,java.sql.Date retailDate, java.sql.Time retailTime){
		final String methodName = "getPreviousBonusRecordsForCancel";
		LOGGER.enter(CLASSNAME, methodName);
		List<BonusInfo> bnsList = null;
		bnsList = daoImpl.getPreviousBonusRecordsForCancel(poNumber,dlrId,retailDate,retailTime);
		LOGGER.exit(CLASSNAME, methodName);
		return bnsList;
	}	
	
	public boolean isDealerTerminationCancelationProcLogs(java.sql.Date aDate){
		final String methodName = "isDealerTerminationCancelationProcLogs";
		LOGGER.enter(CLASSNAME, methodName);
		boolean status = false;
		status = daoImpl.isDealerTerminationCancelationProcLogs(aDate);
		LOGGER.exit(CLASSNAME, methodName);
		return status;
	}
	public void insertIntoProcessTerminateCancelationLog(FileProcessLogMessages message,java.sql.Date aDate) {
    	final String methodName = "insertIntoProcessTerminateCancelationLog";
		LOGGER.enter(CLASSNAME, methodName);
		daoImpl.insertIntoProcessTerminateCancelationLog(message,aDate);
		LOGGER.exit(CLASSNAME, methodName);
    }
	public void updateDayIdAsCurrentDayId(java.sql.Date procDate,java.sql.Date updateDate,boolean flag)
    {
    	final String methodName = "updateDayIdAsCurrentDayId";
		LOGGER.enter(CLASSNAME, methodName);
		daoImpl.updateDayIdAsCurrentDayId(procDate,updateDate,flag);
		LOGGER.exit(CLASSNAME, methodName);
    }
	/**
	 * 
	 */
	public List<DealerInfo> getTerminatedDlrListTillActualDate(Date actualProcDate){
    	final String methodName = "getTerminatedDlrListTillActualDate";
		LOGGER.enter(CLASSNAME, methodName);
		List<DealerInfo> tDealer = daoImpl.getTerminatedDlrListTillActualDate(actualProcDate);
		LOGGER.exit(CLASSNAME, methodName);
		return tDealer;
	}
	
	public List<PaymentDealerInfo> getDealerData(Date beginDate, Date monthEndDate,Date prevDate,Date currentDate,Date qtrEndDate,Date qtrBeginDate,Date qtrDate,int n){
    	final String methodName = "getDealerData";
		LOGGER.enter(CLASSNAME, methodName);		
		List<PaymentDealerInfo> dealerList = daoImpl.getDealerData(beginDate, monthEndDate, prevDate,currentDate,qtrEndDate,qtrBeginDate,qtrDate,n);
		LOGGER.exit(CLASSNAME, methodName);
		return dealerList;
    }	
	
	
	public List<PaymentInfo> getPaymentData(Date beginDate, Date monthEndDate,Date prevDate,Date currentDate,Date qtrEndDate,Date qtrBeginDate,Date qtrDate,int n){
    	final String methodName = "getPaymentData";
		LOGGER.enter(CLASSNAME, methodName);		
		List<PaymentInfo> paymentList = daoImpl.getPaymentData(beginDate, monthEndDate,prevDate,currentDate,qtrEndDate,qtrBeginDate,qtrDate,n);
		LOGGER.exit(CLASSNAME, methodName);
		return paymentList;
    }	
	
	public List<Integer> getProcIdDetails(Date currentDate){
    	final String methodName = "getProcIdDetails";
		LOGGER.enter(CLASSNAME, methodName);		
		List<Integer> procIdList = daoImpl.getProcIdDetails(currentDate);
		LOGGER.exit(CLASSNAME, methodName);
		return procIdList;
    }	
	
	
	public void updateCalcData(Date beginDate,Date prevDate,Date currentDate,Date monthEndDate,Date qtrEndDate,Date qtrBeginDate,int k){
    	final String methodName = "updateCalcData";
		LOGGER.enter(CLASSNAME, methodName);	
		List<Integer> calcId = daoImpl.getCalcIdDetails(beginDate,prevDate,currentDate,monthEndDate,qtrEndDate,qtrBeginDate,k);
		daoImpl.updateCalcDetails(calcId);
		LOGGER.exit(CLASSNAME, methodName);
	}
	
	public List<LeadershipInfo> getLdrshipData(Integer pgmId){
    	final String methodName = "getLdrshipData";
		LOGGER.enter(CLASSNAME, methodName);		
		List<LeadershipInfo> ldrList = daoImpl.getLdrshipData(pgmId);
		LOGGER.exit(CLASSNAME, methodName);
		return ldrList;
	}

	//Data Cleanup for process rerun start
	/* (non-Javadoc)
	 * @see com.mbusa.dpb.common.bo.IDpbCommonManager#cleanUpData(java.lang.String)
	 */
	@Override
	public Map<Integer, String> cleanUpData(String procDate) throws Exception{
		Map<Integer, String> result = new HashMap<Integer, String>();
		final String methodName = "cleanUpData";
		LOGGER.enter(CLASSNAME, methodName);
		result = daoImpl.cleanUpData(procDate);
		LOGGER.exit(CLASSNAME, methodName);
		return result;
	}
	//Data Cleanup for process rerun end

}
