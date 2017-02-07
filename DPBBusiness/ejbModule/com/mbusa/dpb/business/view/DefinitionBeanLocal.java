/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: CB5002578 
 * Program Name				: DefinitionBeanLocal.java
 * Program Version			: 1.0
 * Program Description		: This class is used to provide Definition service. 
 * 							  
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * CB5002578   July 27, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.business.view;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.common.domain.AMGDealer;
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
import com.mbusa.dpb.common.domain.AccountMapping;
public interface DefinitionBeanLocal {	

	public ProgramDefinition submitDealerProgram(ProgramDefinition programDef,Map<String, List<java.sql.Date>> aProcDteListMap) throws TechnicalException;
	public List<ProgramDefinition> getDlrProgramsList()throws BusinessException;
	public void getDlrProgram(ProgramDefinition dlrPrgm)throws BusinessException;
	public boolean validateEndDate(java.sql.Date endDate) throws BusinessException;
	public List<ConditionDefinition> getMasterCondList(String cType) throws BusinessException;
	
	public int createCondition(ConditionDefinition cDef) throws BusinessException;		    
	public List<ConditionDefinition> getConditionList();
	public ConditionDefinition getCondtionDetails(int cId);
	
	public int createRetailMonthEntry(RtlMonthDefinition rtlMonthDef) throws BusinessException;
	public RtlMonthDefinition getStartDate() throws BusinessException;
	
	public FileDefinitionBean saveFileDefinition(FileDefinitionBean fDef, List<java.sql.Date> aSchdDatesList) throws BusinessException;
	public java.sql.Date getEndDate(java.sql.Date endDate)throws BusinessException;
	public List<FileFormatBean> populateformatNameList() throws BusinessException;
	public FileDefinitionBean getEditFileDetails(String fileId)  throws BusinessException;
	public List<FileDefinitionBean> getFileDefinitionList() throws BusinessException;
	public FileFormatBean saveFileFormatDetails(FileFormatBean formatBean) throws BusinessException;
	public FileFormatBean getEditFileFormatDetails(String fileId) throws BusinessException;
	public List<FileFormatBean> getFileFormatListDetails() throws BusinessException;
	public List<String> populateColumns(String tableName) throws BusinessException;
	
	
	public List<VehicleConditionMapping> getVehicleMappedCondition(String cType) throws BusinessException;
	public void saveVehicleMappedCondition(List<VehicleConditionMapping> vList,String cType) throws BusinessException;
		
// LDRSP Bonus Start
	public LeadershipBonusDetails saveLdrshipBonusDef(LeadershipBonusDetails ldrshipbnsdtls) throws BusinessException;	
	public LeadershipBonusDetails ldrshipBonusDtlsFYRChange(LeadershipBonusDetails ldrshipbnsdtls) throws BusinessException;		
	public List<LeadershipBonusDetails> getLdrshipBonusList() throws BusinessException ;		
	public LeadershipBonusDetails getLdrshipBonusDefDetails(LeadershipBonusDetails ldrshipbnsdtls) throws BusinessException;	
	public boolean validateProcessDate(String procDate) throws BusinessException ;
	public double validateUnearnedAmount(String acrlstartDate,String acrlendDate) throws BusinessException ;
// LDRSP Bonus End
	
// AccountID Mapping Start
	public Map<String,Object> getAccountIDMaping() throws BusinessException; 	
	public void saveAccountIDMapping(List<AccountMapping> acctMapList,String userId) throws BusinessException;
// AccountID Mapping End	
	
	public List<ProcessInputFile> getProcInputFileDetails()throws BusinessException;
	public List<GeneratePaymentFile> genPayFile()throws BusinessException;
	public List<GenerateReportFile> genReportFile()throws BusinessException;
	public List<ProcessBonus> procBonusProc();public List<ProcessLdrBonus> processLdrBonus()throws BusinessException;
	public void saveReportQueryDef(ReportQuery reportQuery)throws BusinessException;
	public List<ReportQuery> getReportQueryList()throws BusinessException;
	public ReportQuery getReportQueryEdit(String rId) throws BusinessException;
	
	//changes by Nikhil start
	public ReportDefinitionBean createReportDefinition(ReportDefinitionBean rDef, List<java.sql.Date> aSchdDatesList) throws BusinessException;
	public List<ReportDefinitionBean> getReportDefinitionList() throws BusinessException;
	public ReportDefinitionBean getEditReportDefinition(String reportDefId) throws BusinessException;
	//public ReportDefinitionBean retrieveDefaultReportDefinition(ReportDefinitionBean reportDefBean) throws BusinessException;
	public List<ProcessCalDefBean> getCurrentMonthProcess() throws BusinessException;
	public FileProcessDefBean getFileProcessDefination(int processId) throws BusinessException;
	public int updateFileProcessDefinition(FileProcessDefBean fileProcessDefBean) throws BusinessException;
	public int insertReportContentDefinition(ReportContentDefinitionBean reportContentDefBean) throws BusinessException;
	public int createReportContentDefinition(ReportContentDefinitionBean reportContentDefBean) throws BusinessException;
	public List<ReportContentDefinitionBean> getReportContentList() throws BusinessException;
	public ReportContentDefinitionBean getEditReportContent(String rptCntDefId) throws BusinessException;
	//changes by Nikhil end
	public String getReportDefStatus(ReportQuery reportQuery)throws BusinessException;
	public FileDefinitionBean fetchFileDefinition(int processID) throws TechnicalException;
	public void insertIntoProcessLog(FileProcessLogMessages message);
	public void reProcessingVistaFile(FileDefinitionBean definitionBean, int processId, String message,String processType, String userId, boolean isLdrShpBonus);
	public void reProcessingKpiFile(FileDefinitionBean definitionBean,
			int processId, String message, String processType, String userId , boolean isLdrShpBonus);
	public void reProcessingAccrualFile(FileDefinitionBean definitionBean,
			int processId, String message,String processType, String userId , boolean isLdrShpBonus);
	public void performBonusReprocess(int processId, String userId, String processType, boolean performBonusReprocess);
	public boolean validateCondition(ConditionDefinition conditionDef);
	public boolean validateProgram(int programId, String programName, Date startDate);
	public boolean checkPaymentProcesses(int processId);
	public int updateProcessDefinition(FileProcessDefBean fileProcessDefBean, int fileType, boolean bonusProcess, boolean ldrshpBonus) throws BusinessException;
	public RetailDate getRetailData(String dbMonth, String dbYear);
	public boolean checkValidMonth(String month, String year);
	public void updateProcessesRetailDate(Date oldRtlProcessingDate, Date newRtlProcessingDate,String currentStartDte,String newEndDte,int cYear,int cMonth, int nYear,String userID);
	
	public List<AMGDealer> getDlrsInfoList(String dealerID)throws BusinessException;
	public Boolean modifyAMGDealerInfo(AMGDealer amgDealer) throws BusinessException,TechnicalException;
	public String getAMGDealerName (String dealerID)throws BusinessException;
	
}