/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: IDefinitionDAO.java
 * Program Version			: 1.0
 * Program Description		: This class is used handle Database related functionality like CRUD operation 
 * 							  business type validation if require. 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 29, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.common.domain.AMGDealer;
import com.mbusa.dpb.common.domain.AccountMapping;
import com.mbusa.dpb.common.domain.ConditionDefinition;
import com.mbusa.dpb.common.domain.FieldColumnMapBean;
import com.mbusa.dpb.common.domain.FileDefinitionBean;
import com.mbusa.dpb.common.domain.FileFormatBean;
import com.mbusa.dpb.common.domain.FileProcessDefBean;
import com.mbusa.dpb.common.domain.FileProcessLogMessages;
import com.mbusa.dpb.common.domain.GeneratePaymentFile;
import com.mbusa.dpb.common.domain.GenerateReportFile;
import com.mbusa.dpb.common.domain.LeadershipBonusDetails;
import com.mbusa.dpb.common.domain.ProcessBonus;
import com.mbusa.dpb.common.domain.ProcessCalDefBean;
import com.mbusa.dpb.common.domain.ProcessInputFile;
import com.mbusa.dpb.common.domain.ProcessLdrBonus;
import com.mbusa.dpb.common.domain.ProgramDefinition;
import com.mbusa.dpb.common.domain.ReScheduleProcess;
import com.mbusa.dpb.common.domain.ReportContentDefinitionBean;
import com.mbusa.dpb.common.domain.ReportDefinitionBean;
import com.mbusa.dpb.common.domain.ReportQuery;
import com.mbusa.dpb.common.domain.RetailDate;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.domain.RtlMonthDefinition;
import com.mbusa.dpb.common.domain.VehicleConditionMapping;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;

public interface IDefinitionDAO {	
	/* Program Definition start */
	public int saveDlrProgram(ProgramDefinition programDef);
	public List<ProgramDefinition> getDlrProgramList();
	public void getDlrProgram(ProgramDefinition prgmDef) ;
	public void updateDlrProgram(ProgramDefinition programDef);
	public void insertVehicleType(ProgramDefinition programDef, int programId) ;
	public void updateVehicleType(ProgramDefinition programDef, int programId);
	public void deleteExistingVehTypes(int programId);
	public void insertCondition(ProgramDefinition programDef, int programId);
	public void deleteExistingConditions(ProgramDefinition programDef, int programId);
	public void updateExistingConditions(ProgramDefinition programDef, int programId);
	public void insertDPBProcessDef(ProgramDefinition programDef,Map<String, List<java.sql.Date>> aProcDteListMap) throws TechnicalException;
	public boolean validateEndDate(java.sql.Date endDate);
	public void updateInactiveDate(ProgramDefinition programDef);
	
	/* Program Definition End */
	
	public RtlMonthDefinition getStartDate();
	
/*	public List<FileFormatBean> getFileFormatListDetails() throws Exception;	*/	
	public void updateDPBProcess(FileDefinitionBean fileDef) throws Exception;
	public List<ConditionDefinition> getConditionList();
	public int  createCondition(ConditionDefinition cDef);
	public int updateConditionDetails(ConditionDefinition cDef);
	public ConditionDefinition getCondtionDetails(int cId);
	public boolean validateCondition(int cId);
	public List<ProcessCalDefBean> getCurrentMonthProcess();
	public boolean inactivateCondition(ConditionDefinition cDef) throws BusinessException;
	
	
	
	/*
	File Definition
	*/
	public int saveFileDefinition(FileDefinitionBean fileDef);
	public java.sql.Date getEndDate(java.sql.Date endDate)throws BusinessException;
	public void insertDPBFileProcess(FileDefinitionBean fileDefBean, List<java.sql.Date> aSchdDatesList, String prcsType) throws BusinessException;
	public void updateFileDefinition(FileDefinitionBean fileDef);
	public void updateFileInactiveStatus(FileDefinitionBean fileDef);
	/*public void updateDPBFileProcess(FileDefinitionBean fileDefBean, java.sql.Date startDate);*/
	public List<FileDefinitionBean> getFileDefList();
	public FileDefinitionBean getEditFileDetails(String fileId);
	public List<FileFormatBean> populateformatNameList();
	/*
	File Format Definition
	*/
	public int saveFileFormatDetails(FileFormatBean formatBean);
	public void updateFileFormatDetails(FileFormatBean formatBean);
	public void updateFileFormatInactiveStatus(FileFormatBean formatBean) throws BusinessException;
	public List<FileFormatBean> getFileFormatListDetails();
	public FileFormatBean getEditFileFormatDetails(String fileId);
	public FileFormatBean populateColumnMapList(FileFormatBean formatBean);
	/*
	Field Column Map Defintion
	*/
	//public int saveFieldColumnMapDetails(FieldColumnMapBean colMapBean, FileFormatBean formatBean);
	//public void updateFieldColumnMapDetails(FieldColumnMapBean mapBean, FileFormatBean formatBean);	
	public List<String> populateColumns(String tableName);
	
	/*public void saveFileFormatDetails(FileFormatBean formatBean) throws Exception;
	public void saveFieldColumnMapDetails(FieldColumnMapBean colMapBean, FileFormatBean formatBean) throws Exception;
	public List<String> populateColumns(String tableName) throws Exception;
	public void updateFileFormatDetails(FileFormatBean formatBean) throws Exception;*/
	public void updateFieldColumnMapDetails(FileFormatBean formatBean) throws Exception;
	public List<VehicleConditionMapping> getVehicleMappedCondition(String cType);
	public List<ConditionDefinition> getVCMappingList(String cType);
	public void saveVehicleMappedCondition(List <VehicleConditionMapping> vcList,String cType);
	public void updateVehicleMappedCondition(VehicleConditionMapping vclType);
	public int deleteMappingConditions(List <VehicleConditionMapping> vcList, String cType);
	
	//public RtlMonthDefinition getStartDate();
	public int createRetailMonthEntry(RtlMonthDefinition rtlMonthDef);
	public List<ProcessInputFile> getProcInputFileDetails();
	public List<GeneratePaymentFile> genPayFile();
	public List<GenerateReportFile> genReportFile();
	public List<ProcessBonus> procBonusProc();
	public List<ProcessLdrBonus> processLdrBonus();	
	//public ReportQuery getReportQueryID();
	public void  saveReportQueryDef(ReportQuery reportQuery);
	public ReportQuery getReportQueryEdit(String rId);
	public List<ReportQuery> getReportQueryList();
	public void updateReportQueryDef (ReportQuery reportQuery);
	//public ReportQuery getReportQueryID();
	
	// LDRSP Bonus Start
	public LeadershipBonusDetails saveLdrshipBonusDef(LeadershipBonusDetails ldrshipbnsdtls);	
	public LeadershipBonusDetails updateLdrshipBonusDef(LeadershipBonusDetails ldrshipbnsdtls);	
	public void deleteLdrspVehMapping(int ldrspId);
	public LeadershipBonusDetails getLdrshipBonusAmt(LeadershipBonusDetails ldrshipbnsdtls);
	//public int getLdrshipEligibleVehCount(LeadershipBonusDetails ldrshipbnsdtls);
	public List getLdrshipBonusList();	
	public LeadershipBonusDetails getLdrshipBonusDefDetails(LeadershipBonusDetails ldrshipbnsdtls);
	public void getApplicationVehicle(LeadershipBonusDetails ldrshipbnsdtls);
	public void insertPrgVehMap(int ldrspId,List appVehi,String userId,String status);
	public boolean validateProcessDate(String procDate);
	public int getRTLCalId(String processDate);
	public void insertDPBProc(LeadershipBonusDetails ldrshipbnsdtls);
	public void updateInactiveDate(int ldrspId,String inactiveDate,String status,String userid);
	public double validateUnearnedAmount(String acrlstartDate,String acrlendDate);
	// LDRSP Bonus End
	//AccountID Mapping Start
	public Map<String,Object> getAccountIDMaping();
	public void saveAccountIDMapping(List<AccountMapping> acctMapList,String userId);
	//AccountID Mapping End
	//nikhil changes start
	public int createReportDefinition(ReportDefinitionBean reportDef);
	public List<ReportDefinitionBean> getReportDefinitionList();
	public ReportDefinitionBean getEditReportDefinition(String reportDefId);
	//public ReportDefinitionBean saveQCRelations(ReportDefinitionBean reportDef);
	public void updateReportDefinition(ReportDefinitionBean reportDef);
	public void updateReportInactiveStatus(ReportDefinitionBean reportDef);
	public void createDPBReportProcess(ReportDefinitionBean reportDef, List<java.sql.Date> aSchdDatesList) throws BusinessException;
	/*public void updateDPBReportProcess();*/
	public ReportDefinitionBean populateQCRelations(ReportDefinitionBean reportDef);
	//public ReportDefinitionBean updateQCRelations(ReportDefinitionBean reportDef);
	//public ReportDefinitionBean retrieveDefaultReportDefinition(ReportDefinitionBean reportDefBean);
	public FileProcessDefBean getFileProcessDefination(int processId);
	public int updateFileProcessDefinition(FileProcessDefBean fileProcessDefBean);
	public int createReportContentDefinition(ReportContentDefinitionBean reportContentDefBean);
	public void updateReportContentDefinition(ReportContentDefinitionBean reportContentDefBean);
	public void updateReportContentInactive(ReportContentDefinitionBean reportContentDefBean) throws BusinessException;
	public List<ReportContentDefinitionBean> getReportContentList();
	public ReportContentDefinitionBean getEditReportContent(String rptCntDefId);
	//nikhil changes end
	public String getReportDefStatus(ReportQuery reportQuery);
	public void deleteProcesses(int programId, List<String> processTypes);
	public FileDefinitionBean fetchFileDefinition(int processID);
	public void insertIntoProcessLog(FileProcessLogMessages message);
	public void deleteVistaProcessedData(int processId);
	public void deleteBlockedData(int processId);
	public void updateBonusProcesses(Date actualDate, String message, String processType, String userId);
	public void deleteKpiData(int processId);
	public void deleteAccrualData(int processId);
	public void updateAccrualBonusProcesses(Date actualDate, String message, String processType, 
			String userId);
	public void performBonusReprocess(int processId, String userId, String processType, boolean ldrShpBonusProcess);
	public void performBonusReset(int processId, String userId,
			String processType, boolean ldrShpBonusProcess, boolean isBonus, boolean isKpi);
	public boolean validateUniqueCondition(ConditionDefinition conditionDef);
	public boolean validateProgram(int programId, String programName, Date startDate);
	public boolean checkPaymentProcesses(int processId);
	public void updateProcessDefinition(FileProcessDefBean fileProcessDefBean, List<ReScheduleProcess> processIdList);
	public List<ReScheduleProcess> getProcessesForFileReschedule(FileProcessDefBean fileProcessDefBean, int fileType);
	public List<ReScheduleProcess> getProcessesForBonusReschedule(FileProcessDefBean fileProcessDefBean);
	public void insertReportProcessForReSchedule(FileProcessDefBean fileProcessDefBean, List<ReScheduleProcess> processIdList);
	public void insertBonusProcessForReSchedule(FileProcessDefBean fileProcessDefBean,List<ReScheduleProcess> processIdList);
	public void insertLeaderShipBonusProcessForReSchedule(FileProcessDefBean fileProcessDefBean,List<ReScheduleProcess> processIdList);
	public void insertReScheduleProcessLogList(List<ReScheduleProcess> processIdList,FileProcessDefBean fileProcessDefBean);
	public RetailDate getRetailData(String dbMonth, String dbYear);
	public boolean checkValidMonth(String month, String year);
	public List<Integer> getRtlMonthEndProcesses(Date oldRtlEndDate);
	public void updateProcessesRtlEndDates(List<Integer> processes,java.sql.Date rtlDate,String userID);
	public void updateDatesInDpbDay(String currentStartDte,String newEndDte,int cYear,int cMonth, int nYear,String userID);
	
	public List<AMGDealer> getDlrsInfoList(String dealerId);
	public Boolean modifyAMGDealerInfo(AMGDealer amgDealer);
	public String getAMGDealerName(String dlrId);

}