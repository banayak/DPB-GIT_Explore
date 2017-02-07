/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: IDefinitionManager.java
 * Program Version			: 1.0
 * Program Description		: This class is used to call DAO layer and provide some 
 * 							  business type validation if require. 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 28, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.bo;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.common.domain.AMGDealer;
import com.mbusa.dpb.common.domain.AccountMapping;
import com.mbusa.dpb.common.domain.ConditionDefinition;
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
import com.mbusa.dpb.common.domain.ReportContentDefinitionBean;
import com.mbusa.dpb.common.domain.ReportDefinitionBean;
import com.mbusa.dpb.common.domain.ReportQuery;
import com.mbusa.dpb.common.domain.RetailDate;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.domain.RtlMonthDefinition;
import com.mbusa.dpb.common.domain.VehicleConditionMapping;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
public interface IDefinitionManager {

	public int  createCondition(ConditionDefinition cDef) throws BusinessException ;
	public void submitDealerProgram(ProgramDefinition programDef,Map<String, List<java.sql.Date>> aProcDteListMap) throws TechnicalException;
	public void saveDlrProgram(ProgramDefinition programDef,Map<String, List<java.sql.Date>> aProcDteListMap) throws TechnicalException;
	public void updateDlrProgram(ProgramDefinition programDef,Map<String,List<java.sql.Date>> aProcDteListMap) throws TechnicalException;
	public List<ProgramDefinition> getDlrProgramList();
	public void getDlrProgram(ProgramDefinition prgmDef);
	public boolean validateEndDate(java.sql.Date endDate);
	public List<ConditionDefinition> getMasterCondList(String cType);
	
	public List<ProcessCalDefBean> getCurrentMonthProcess();

	
	public void updateConditionDetails(ConditionDefinition cDef);
	public List<ConditionDefinition> getConditionList();
	//public int  createCondition(ConditionDefinition cDef) throws Exception;
	public ConditionDefinition getCondtionDetails(int cId);
	
	public FileDefinitionBean saveFileDefinition(FileDefinitionBean fDef, List<java.sql.Date> aSchdDatesList) throws BusinessException;
	public java.sql.Date getEndDate(java.sql.Date endDate)throws BusinessException;
	public void updateFileDefinition(FileDefinitionBean fDef, List<java.sql.Date> aSchdDatesList);
	public List<FileDefinitionBean> getFileDefinitionList();
	public FileDefinitionBean getEditFileDetails(String fileId);
	public List<FileFormatBean> populateformatNameList();
	
	public FileFormatBean saveFileFormatDetails(FileFormatBean formatBean) throws BusinessException;
	public List<FileFormatBean> getFileFormatListDetails();
	public FileFormatBean getEditFileFormatDetails(String fileId);
	
	public List<String> populateColumns(String tableName);
	
	public int createRetailMonthEntry(RtlMonthDefinition rtlMonthDef) ;
	public RtlMonthDefinition getStartDate();
	
	
	public List<VehicleConditionMapping> getVehicleMappedCondition(String cType);
	
	public void saveVehicleMappedCondition(List<VehicleConditionMapping> vcMapping,String cType);
	
	public List<ProcessInputFile> getProcInputFileDetails() ;
	public List<GeneratePaymentFile> genPayFile() ;
	public List<GenerateReportFile> genReportFile() ;
	public List<ProcessBonus> procBonusProc();
	public List<ProcessLdrBonus> processLdrBonus();
	public void saveReportQueryDef(ReportQuery reportQuery);
	public List<ReportQuery> getReportQueryList();
	public ReportQuery getReportQueryEdit(String rId);
	// LDRSP Bonus Start
	public LeadershipBonusDetails saveLdrshipBonusDef(LeadershipBonusDetails ldrshipbnsdtls);		
	//public LeadershipBonusDetails ldrshipBonusDtlsFYRChange(LeadershipBonusDetails ldrshipbnsdtls);	
	public List<LeadershipBonusDetails> getLdrshipBonusList() ;	
	public LeadershipBonusDetails getLdrshipBonusDefDetails(LeadershipBonusDetails ldrshipbnsdtls);		
	public LeadershipBonusDetails getLdrshipBonusAmt(LeadershipBonusDetails ldrshipbnsdtls);
	//public int getLdrshipEligibleVehCount(LeadershipBonusDetails ldrshipbnsdtls);
	public boolean validateProcessDate(String endDate);
	public double validateUnearnedAmount(String acrlstartDate,String acrlendDate);
	// LDRSP Bonus End
	//AccountID Mapping Start
	public Map<String,Object> getAccountIDMaping();
	public void saveAccountIDMapping(List<AccountMapping> acctMapList,String userId);
	//AccountID Mapping End
	//nikhil changes start
	public ReportDefinitionBean createReportDefinition(ReportDefinitionBean reportDef, List<java.sql.Date> aSchdDatesList) throws BusinessException;
	public List<ReportDefinitionBean> getReportDefinitionList();
	public ReportDefinitionBean getEditReportDefinition(String reportDefId);
	//public ReportDefinitionBean retrieveDefaultReportDefinition(ReportDefinitionBean reportDefBean) ;
	public FileProcessDefBean getFileProcessDefination(int processId);
	public int updateFileProcessDefinition(FileProcessDefBean fileProcessDefBean);
	public int createReportContentDefinition(ReportContentDefinitionBean reportContentDefBean) throws BusinessException;
	public List<ReportContentDefinitionBean> getReportContentList();
	public ReportContentDefinitionBean getEditReportContent(String rptCntDefId);
	public String getReportDefStatus(ReportQuery reportQuery);
	//nikhil changes en
	public FileDefinitionBean fetchFileDefinition(int processID);
	public void insertIntoProcessLog(FileProcessLogMessages message);
	public void deleteVistaProcessedData(int processId);
	public void deleteBlockedData(int processId);
	public void updateBonusProcesses(Date actualDate, String message, String processType, String userId);
	public void reProcessingVistaFile(FileDefinitionBean definitionBean, int processId, String message, String processType, String userId, boolean ldrShpBonus);
	public void reProcessingKpiFile(FileDefinitionBean definitionBean,
			int processId, String message,String processType, String userId, boolean ldrShpBonus);
	public void reProcessingAccrualFile(FileDefinitionBean definitionBean,
			int processId, String message,String processType, String userId, boolean ldrShpBonus);
	public void performBonusReprocess(int processId, String userId,String processType, boolean ldrShpBonusProcess);
	public void performBonusReset(int processId, String userId,String processType, boolean ldrShpBonusProcess, boolean isBonus, boolean isKpi);
	public boolean validateCondition(ConditionDefinition conditionDef);
	public boolean validateProgram(int programId, String programName, Date startDate);
	public boolean checkPaymentProcesses(int processId);
	public int updateProcessDefinition(FileProcessDefBean fileProcessDefBean, int fileType, boolean bonusProcess, boolean ldrshpBonus);
	public RetailDate getRetailData(String dbMonth, String dbYear);
	public boolean checkValidMonth(String month, String year);
	public void updateProcessesRetailDate(Date oldRtlProcessingDate, Date newRtlProcessingDate,String currentStartDte,String newEndDte,int cYear,int cMonth, int nYear,String userID);
	
	public List<AMGDealer> getDlrsInfoList(String dealerId);
	
	public Boolean modifyAMGDealerInfo(AMGDealer amgDealer);
	
	public String getAMGDealerName(String dlrId);
}
