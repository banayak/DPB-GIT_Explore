/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: CB5002578 
 * Program Name				: DefinitionBean.java
 * Program Version			: 1.0
 * Program Description		: This class is used to provide definition service. 
 * 							  
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * CB5002578   July 27, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/

package com.mbusa.dpb.business;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.mbusa.dpb.business.view.DefinitionBeanLocal;
import com.mbusa.dpb.common.bo.DefinitionManagerImpl;
import com.mbusa.dpb.common.bo.IDefinitionManager;
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
import com.mbusa.dpb.common.exceptions.PersistanceException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.factories.BOFactory;
import com.mbusa.dpb.common.logger.DPBLog;
/**
 * Session Bean implementation class DefinitionBean
 */
@Stateless(name="DefinitionBean")
@Local(DefinitionBeanLocal.class)
public class DefinitionBean implements DefinitionBeanLocal {

	private IDefinitionManager defMgr  = (DefinitionManagerImpl) BOFactory.getInstance().getImplementation(IDefinitionManager.class);
	private static DPBLog LOGGER = DPBLog.getInstance(DefinitionBean.class);
	static final private String CLASSNAME = DefinitionBean.class.getName();

    public DefinitionBean() {
        // TODO Auto-generated constructor stub
    }
    
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public ProgramDefinition submitDealerProgram(ProgramDefinition programDef,Map<String, List<java.sql.Date>> aProcDteListMap) throws TechnicalException, PersistanceException{
		LOGGER.enter(CLASSNAME, "saveDlrProgram()");
		defMgr.submitDealerProgram(programDef,aProcDteListMap);			
		
		return programDef;		
	}
public List<ProgramDefinition> getDlrProgramsList(){
	LOGGER.enter(CLASSNAME, "getDlrProgramsList()");
		List<ProgramDefinition> prgList = new ArrayList<ProgramDefinition>();
		prgList = defMgr.getDlrProgramList();
		
		return prgList;		
	}
public void getDlrProgram(ProgramDefinition dlrPrgm){
	LOGGER.enter(CLASSNAME, "getDlrProgram()");
	defMgr.getDlrProgram(dlrPrgm);
}

public boolean validateEndDate(java.sql.Date endDate) {
	LOGGER.enter(CLASSNAME, "validateEndDate()");
	boolean isValidDate = defMgr.validateEndDate(endDate);
	return isValidDate;
}

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public int createCondition(ConditionDefinition cDef) throws BusinessException{
	LOGGER.enter(CLASSNAME, "createCondition()");
	int cId = 0 ;
	cId = defMgr.createCondition(cDef);	
	return cId;	
}  
    
public List<ConditionDefinition> getConditionList() {
	LOGGER.enter(CLASSNAME, "getConditionList()");
	List<ConditionDefinition> cDefList = defMgr.getConditionList();	   	
	return cDefList;
}

public ConditionDefinition getCondtionDetails(int cId) {
	LOGGER.enter(CLASSNAME, "getCondtionDetails()");
	ConditionDefinition cDef = defMgr.getCondtionDetails(cId);	
	return cDef;    	
} 


//**********************************************************************************************************

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public FileDefinitionBean saveFileDefinition(FileDefinitionBean fDef, List<java.sql.Date> aSchdDatesList) throws BusinessException {
	final String methodName = "saveFileDetails";
	LOGGER.enter(CLASSNAME, methodName);
	fDef = defMgr.saveFileDefinition(fDef, aSchdDatesList);
	LOGGER.exit(CLASSNAME, methodName);
	return fDef;
}

public java.sql.Date getEndDate(java.sql.Date endDate)throws BusinessException{
	LOGGER.enter(CLASSNAME, "validateEndDate()");
	java.sql.Date rEndDate = null;
		rEndDate = defMgr.getEndDate(endDate);
	return rEndDate;
}

public List<FileFormatBean> populateformatNameList(){
	final String methodName = "populateformatNameList";
	LOGGER.enter(CLASSNAME, methodName);
	List<FileFormatBean> fileFormat = null;
	fileFormat = defMgr.populateformatNameList();
	LOGGER.exit(CLASSNAME, methodName);
	return fileFormat;
}

public FileDefinitionBean getEditFileDetails(String fileId) {
	final String methodName = "getEditFileDetails";
	LOGGER.enter(CLASSNAME, methodName);
	FileDefinitionBean fDetails = null;	
		fDetails = defMgr.getEditFileDetails(fileId);
		LOGGER.exit(CLASSNAME, methodName);
	return fDetails;
}


public List<FileDefinitionBean> getFileDefinitionList() {
	final String methodName = "getFileDefinitionList";
	LOGGER.enter(CLASSNAME, methodName);
	List<FileDefinitionBean> fDefList = null;
		fDefList = defMgr.getFileDefinitionList();
		LOGGER.exit(CLASSNAME, methodName);
	return fDefList;
}

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public FileFormatBean saveFileFormatDetails(FileFormatBean formatBean) throws BusinessException{
	final String methodName = "saveFileFormatDetails";
	LOGGER.enter(CLASSNAME, methodName);
	formatBean = defMgr.saveFileFormatDetails(formatBean);
	LOGGER.exit(CLASSNAME, methodName);
	return formatBean;
}

public List<String> populateColumns(String tableName){
	final String methodName = "populateColumns";
	LOGGER.enter(CLASSNAME, methodName);
	List<String> columnNames= null;
		columnNames = defMgr.populateColumns(tableName);
		LOGGER.exit(CLASSNAME, methodName);
	return columnNames;
}

public FileFormatBean getEditFileFormatDetails(String fileId){
	final String methodName = "getEditFileFormatDetails";
	LOGGER.enter(CLASSNAME, methodName);
	FileFormatBean formatBean = null;
	formatBean = defMgr.getEditFileFormatDetails(fileId);
	LOGGER.exit(CLASSNAME, methodName);
	return formatBean;
}

public List<FileFormatBean> getFileFormatListDetails(){
	final String methodName = "getFileFormatListDetails";
	LOGGER.enter(CLASSNAME, methodName);
	List<FileFormatBean> fileFormatList=null;
	fileFormatList = defMgr.getFileFormatListDetails();
	LOGGER.exit(CLASSNAME, methodName);
	return fileFormatList;
}

public List<VehicleConditionMapping> getVehicleMappedCondition(String cType){
	final String methodName = "getVehiclMappedCondtion";
	LOGGER.enter(CLASSNAME, methodName);
	List<VehicleConditionMapping> vehicleCond = null;
	vehicleCond =	defMgr.getVehicleMappedCondition(cType);
	LOGGER.exit(CLASSNAME, methodName);
	return vehicleCond;
}

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public void saveVehicleMappedCondition(List<VehicleConditionMapping> vList,String cType)
{	
	final String methodName = "saveVehicleMappedCondition";
	LOGGER.enter(CLASSNAME, methodName);
	defMgr.saveVehicleMappedCondition(vList,cType);	
	LOGGER.exit(CLASSNAME, methodName);	
}

/*public ReportQuery getReportQueryID()
{ 
	ReportQuery reportQuery=null;
	reportQuery= defMgr.getReportQueryID();
	return reportQuery;
}*/
//LDRSP Bonus Start
@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public LeadershipBonusDetails saveLdrshipBonusDef(LeadershipBonusDetails ldrshipbnsdtls) throws BusinessException {
		final String methodName = "saveLdrshipBonusDef";
		LOGGER.enter(CLASSNAME, methodName);
		defMgr.saveLdrshipBonusDef(ldrshipbnsdtls);
		LOGGER.exit(CLASSNAME, methodName);
		return ldrshipbnsdtls;
	}	

/* (non-Javadoc)
* @see com.mbusa.dpb.web.bo.ILeadershipBonusDefinationBO#ldrshipBonusDtlsFYRChange(com.mbusa.dpb.definitions.beans.LeadershipBonusDetailsBean)
*/
public LeadershipBonusDetails ldrshipBonusDtlsFYRChange(LeadershipBonusDetails ldrshipbnsdtls) throws BusinessException {	
		final String methodName = "ldrshipBonusDtlsFYRChange";
		LOGGER.enter(CLASSNAME, methodName);
		int appVehicleSize = ldrshipbnsdtls.getLdrshipAppVeh().size();
		if(ldrshipbnsdtls.getStartDate() != null && ldrshipbnsdtls.getEndDate() != null && ldrshipbnsdtls.getRtlStartDate() != null && ldrshipbnsdtls.getRtlEndDate() != null && appVehicleSize >= 1  ) {
			ldrshipbnsdtls = defMgr.getLdrshipBonusAmt(ldrshipbnsdtls);
		}		
		LOGGER.exit(CLASSNAME, methodName);
	return ldrshipbnsdtls;		
}

/* (non-Javadoc)
* @see com.mbusa.dpb.web.bo.ILeadershipBonusDefinationBO#getLdrshipBonusList()
*/
public List<LeadershipBonusDetails> getLdrshipBonusList() throws BusinessException{   
		final String methodName = "getLdrshipBonusList";
		LOGGER.enter(CLASSNAME, methodName);
		List<LeadershipBonusDetails> ldrshipBnsList = null;
		ldrshipBnsList = new ArrayList<LeadershipBonusDetails>();
		ldrshipBnsList = defMgr.getLdrshipBonusList();
		LOGGER.exit(CLASSNAME, methodName);
		return ldrshipBnsList;
}
/* (non-Javadoc)
* @see com.mbusa.dpb.web.bo.ILeadershipBonusDefinationBO#getLdrshipBonusDefDetails(com.mbusa.dpb.definitions.beans.LeadershipBonusDetailsBean)
*/
public LeadershipBonusDetails getLdrshipBonusDefDetails(LeadershipBonusDetails ldrshipbnsdtls) throws BusinessException {
		final String methodName = "getLdrshipBonusDefDetails";
		LOGGER.enter(CLASSNAME, methodName);
		defMgr.getLdrshipBonusDefDetails(ldrshipbnsdtls);
		LOGGER.exit(CLASSNAME, methodName);
		return ldrshipbnsdtls;
	}
public boolean validateProcessDate(String procDate) throws BusinessException {
		final String methodName = "validateProcessDate";
		LOGGER.enter(CLASSNAME, methodName);
		boolean isValidDate = defMgr.validateProcessDate(procDate);
		LOGGER.exit(CLASSNAME, methodName);
		return isValidDate;
	}
public double validateUnearnedAmount(String acrlstartDate,String acrlendDate) throws BusinessException {
	final String methodName = "validateUnearnedAmount";
	LOGGER.enter(CLASSNAME, methodName);
	double unEarnedAmount = 0.00;
	unEarnedAmount = defMgr.validateUnearnedAmount(acrlstartDate,acrlendDate);
	LOGGER.exit(CLASSNAME, methodName);
	return unEarnedAmount;
}

//LDRSP Bonus End
//AccountID Mapping Start
	public Map<String, Object> getAccountIDMaping() throws BusinessException {
		final String methodName = "getAccountIDMaping";
		LOGGER.enter(CLASSNAME, methodName);
		Map<String, Object> vehicleCond = null;
		vehicleCond = defMgr.getAccountIDMaping();
		LOGGER.exit(CLASSNAME, methodName);
		return vehicleCond;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveAccountIDMapping(List<AccountMapping> acctMapList,String userId) throws BusinessException {
		final String methodName = "saveAccountIDMapping";
		LOGGER.enter(CLASSNAME, methodName);
		defMgr.saveAccountIDMapping(acctMapList, userId);
		LOGGER.exit(CLASSNAME, methodName);
	}
//AccountID Mapping End



public int createRetailMonthEntry(RtlMonthDefinition rtlMonthDef){
	int idRtlDte=0;
	try { 
		idRtlDte=defMgr.createRetailMonthEntry(rtlMonthDef);
			
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return idRtlDte;
}

public RtlMonthDefinition getStartDate()
{ 
	RtlMonthDefinition rtlMonthDef=null;;
	try { 
		rtlMonthDef=defMgr.getStartDate();
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return rtlMonthDef;
}
public List<ProcessInputFile> getProcInputFileDetails()
{
	List<ProcessInputFile> procInputFile=null;		
	procInputFile =  defMgr.getProcInputFileDetails();		
	return procInputFile;		
}

public List<GeneratePaymentFile> genPayFile()
{
	List<GeneratePaymentFile> genPaymentFile=null;		
	genPaymentFile =  defMgr.genPayFile();
	return genPaymentFile;		
}

public List<GenerateReportFile> genReportFile()
{
	List<GenerateReportFile> genReportFile=null;
	genReportFile =  defMgr.genReportFile();		
	return genReportFile;		
}	
public List<ProcessBonus> procBonusProc()
{
	List<ProcessBonus> ProcessBonus=null;		
	ProcessBonus =  defMgr.procBonusProc();
	return ProcessBonus;
}

public List<ProcessLdrBonus> processLdrBonus()
{
	List<ProcessLdrBonus> processLdrBonus=null;
	processLdrBonus =  defMgr.processLdrBonus();		
	return processLdrBonus;			
}

public void  saveReportQueryDef(ReportQuery reportQuery){
	defMgr.saveReportQueryDef(reportQuery);	
}

public List<ReportQuery> getReportQueryList()
{	
	final String methodName = "saveFileDetails";
	LOGGER.enter(CLASSNAME, methodName);
	List<ReportQuery> lst;
	lst = defMgr.getReportQueryList();
	return lst;
}
public ReportQuery getReportQueryEdit(String rId)
{	
	final String methodName = "createReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	ReportQuery reportQuery;
	
	reportQuery = defMgr.getReportQueryEdit(rId);		
	LOGGER.exit(CLASSNAME, methodName);
	return reportQuery;
	
}
//Nikhil changes start
@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public ReportDefinitionBean createReportDefinition(ReportDefinitionBean rDef, List<java.sql.Date> aSchdDatesList) throws BusinessException{	
	final String methodName = "createReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	rDef = defMgr.createReportDefinition(rDef, aSchdDatesList);	
	LOGGER.exit(CLASSNAME, methodName);
	return rDef;
}

public List<ReportDefinitionBean> getReportDefinitionList(){
	final String methodName = "getReportDefinitionList";
	LOGGER.enter(CLASSNAME, methodName);
	List<ReportDefinitionBean> reportDefList = null;
	reportDefList = defMgr.getReportDefinitionList();
	LOGGER.exit(CLASSNAME, methodName);
	return reportDefList;
}

public ReportDefinitionBean getEditReportDefinition(String reportDefId){
	final String methodName = "getEditReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	ReportDefinitionBean reportDef = null;
	reportDef = defMgr.getEditReportDefinition(reportDefId);
	LOGGER.exit(CLASSNAME, methodName);
	return reportDef;
}

/*@Override
public ReportDefinitionBean retrieveDefaultReportDefinition(ReportDefinitionBean reportDefBean){
	final String methodName = "retrieveDefaultReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	//ReportDefinitionBean reportDefBean = null;
		reportDefBean = defMgr.retrieveDefaultReportDefinition(reportDefBean);
	LOGGER.exit(CLASSNAME, methodName);
	return reportDefBean;
}*/

@Override
public List<ProcessCalDefBean> getCurrentMonthProcess(){
	final String methodName = "getCurrentMonthProcess";
	LOGGER.enter(CLASSNAME, methodName);
	List<ProcessCalDefBean> processCalView = null;
	processCalView = defMgr.getCurrentMonthProcess();	
	
	LOGGER.exit(CLASSNAME, methodName);
	return processCalView;
}

@Override
public FileProcessDefBean getFileProcessDefination(int processId) throws BusinessException {
	final String methodName = "getFileProcessDefination";
	LOGGER.enter(CLASSNAME, methodName);
	FileProcessDefBean fileProcessDefBean = defMgr.getFileProcessDefination(processId);
	LOGGER.exit(CLASSNAME, methodName);
	return fileProcessDefBean;
}

@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public int updateFileProcessDefinition(FileProcessDefBean fileProcessDefBean) {
	final String methodName = "updateFileProcessDefination";
	LOGGER.enter(CLASSNAME, methodName);	
	int processId = defMgr.updateFileProcessDefinition(fileProcessDefBean);
	LOGGER.exit(CLASSNAME, methodName);
	return processId;
}


@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public int insertReportContentDefinition(ReportContentDefinitionBean insertReportContentDefinition){
	final String methodName = "insertReportContentDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	int reportContentDefination = 0;
	//reportContentDefination = defMgr.insertReportContentDefinition(insertReportContentDefinition);
	LOGGER.exit(CLASSNAME, methodName);
	return reportContentDefination;
}
@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public int createReportContentDefinition(ReportContentDefinitionBean reportContentDefBean) throws BusinessException{
	final String methodName = "insertReportContentDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	int pId = defMgr.createReportContentDefinition(reportContentDefBean);	
	LOGGER.exit(CLASSNAME, methodName);
	return pId;
}
//Nikhil changes end
@Override
public List<ConditionDefinition> getMasterCondList(String cType)throws BusinessException {
	List<ConditionDefinition> masterList = null;
	masterList = defMgr.getMasterCondList(cType);
	return masterList;
}
public List<ReportContentDefinitionBean> getReportContentList(){
	final String methodName = "getReportContentList";
	LOGGER.enter(CLASSNAME, methodName);
	List<ReportContentDefinitionBean> rptCntList = null;
	rptCntList = defMgr.getReportContentList();
	LOGGER.exit(CLASSNAME, methodName);
	return rptCntList;
}

public ReportContentDefinitionBean getEditReportContent(String rptCntDefId){
	final String methodName = "getEditReportContent";
	LOGGER.enter(CLASSNAME, methodName);
	ReportContentDefinitionBean reportContentDefBean = null;
	reportContentDefBean = defMgr.getEditReportContent(rptCntDefId);
	LOGGER.exit(CLASSNAME, methodName);
	return reportContentDefBean;
}

public String getReportDefStatus(ReportQuery reportQuery)
{
	String str="";
	
		str =  defMgr.getReportDefStatus(reportQuery);	
		
		return str;
}

@Override
public FileDefinitionBean fetchFileDefinition(int processID) throws TechnicalException {
	FileDefinitionBean fileDefinitionBean = defMgr.fetchFileDefinition (processID);
	return fileDefinitionBean;
}
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public void insertIntoProcessLog (FileProcessLogMessages message) {
	final String methodName = "insertIntoProcessLog";
	LOGGER.enter(CLASSNAME, methodName);
	defMgr.insertIntoProcessLog(message);
	LOGGER.exit(CLASSNAME, methodName);
}

@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public void reProcessingVistaFile(FileDefinitionBean definitionBean, int processId, String message, String processType, String userId, boolean isLdrShpBonus) {
	final String methodName = "reProcessingVistaFile";
	LOGGER.enter(CLASSNAME, methodName);
	defMgr.reProcessingVistaFile(definitionBean, processId, message, processType, userId, isLdrShpBonus);
	LOGGER.exit(CLASSNAME, methodName);
}

@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public void reProcessingKpiFile(FileDefinitionBean definitionBean,
		int processId, String message,String processType, String userId, boolean isLdrShpBonus) {
	final String methodName = "reProcessingVistaFile";
	LOGGER.enter(CLASSNAME, methodName);
	defMgr.reProcessingKpiFile(definitionBean, processId, message, processType, userId, isLdrShpBonus);
	LOGGER.exit(CLASSNAME, methodName);
}

@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public void reProcessingAccrualFile(FileDefinitionBean definitionBean,
		int processId, String message,String processType, String userId, boolean isLdrShpBonus) {
	final String methodName = "reProcessingAccrualFile";
	LOGGER.enter(CLASSNAME, methodName);
	defMgr.reProcessingAccrualFile(definitionBean, processId, message,processType, userId, isLdrShpBonus);
	LOGGER.exit(CLASSNAME, methodName);
	
}
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Override
public void performBonusReprocess(int processId, String userId, String processType, boolean ldrShpBonusProcess) {
	final String methodName = "reProcessingAccrualFile";
	LOGGER.enter(CLASSNAME, methodName);
	defMgr.performBonusReprocess(processId, userId, processType, ldrShpBonusProcess);
	LOGGER.exit(CLASSNAME, methodName);
}

@Override
public boolean validateCondition(ConditionDefinition conditionDef) {
	final String methodName = "validateCondition";
	LOGGER.enter(CLASSNAME, methodName);
	boolean isValidCondition =defMgr.validateCondition(conditionDef);
	LOGGER.exit(CLASSNAME, methodName);
	return isValidCondition;
}

@Override
public boolean validateProgram(int programId, String programName, Date startDate) {
	final String methodName = "validateProgram";
	LOGGER.enter(CLASSNAME, methodName);
	boolean isValidProgram =defMgr.validateProgram(programId, programName, startDate);
	LOGGER.exit(CLASSNAME, methodName);
	return isValidProgram;
}

@Override
public boolean checkPaymentProcesses(int processId) {
	final String methodName = "checkPaymentProcesses";
	LOGGER.enter(CLASSNAME, methodName);
	boolean isPaymentSuccess = defMgr.checkPaymentProcesses(processId);
	LOGGER.exit(CLASSNAME, methodName);
	return isPaymentSuccess;
}

@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public int updateProcessDefinition(FileProcessDefBean fileProcessDefBean, int fileType, boolean bonusProcess, boolean ldrshpBonus){
	final String methodName = "updateFileProcessDefination";
	LOGGER.enter(CLASSNAME, methodName);	
	int processId = defMgr.updateProcessDefinition(fileProcessDefBean,fileType, bonusProcess,ldrshpBonus);
	LOGGER.exit(CLASSNAME, methodName);
	return processId;
}

@Override
public RetailDate getRetailData(String dbMonth, String dbYear) {
	final String methodName = "getRetailData";
	LOGGER.enter(CLASSNAME, methodName);
	RetailDate rtlDate = new RetailDate();
	rtlDate = defMgr.getRetailData(dbMonth, dbYear);
	LOGGER.exit(CLASSNAME, methodName);
	return rtlDate;
}

@Override
public boolean checkValidMonth(String month, String year) {
	final String methodName = "checkValidMonth";
	LOGGER.enter(CLASSNAME, methodName);
	boolean isValidMonth =defMgr.checkValidMonth(month, year);	 
	LOGGER.exit(CLASSNAME, methodName);
	return isValidMonth;
}

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public void updateProcessesRetailDate(Date oldRtlProcessingDate, Date newRtlProcessingDate,String currentStartDte,String newEndDte,int cYear,int cMonth, int nYear,String userID) {
	final String methodName = "updateProcessesRetailDate";
	LOGGER.enter(CLASSNAME, methodName);
	defMgr.updateProcessesRetailDate(oldRtlProcessingDate, newRtlProcessingDate,currentStartDte,newEndDte,cYear,cMonth, nYear,userID);
	LOGGER.exit(CLASSNAME, methodName);
}

@Override
public List<AMGDealer> getDlrsInfoList(String dealerId){
	final String methodName = "getReportContentList";
	LOGGER.enter(CLASSNAME, methodName);
	List<AMGDealer> dealerInfoList = null;
	dealerInfoList = defMgr.getDlrsInfoList(dealerId);
	LOGGER.exit(CLASSNAME, methodName);
	return dealerInfoList;
}

@Override
public Boolean modifyAMGDealerInfo(AMGDealer amgDealer){
	final String methodName = "modifyAMGDealerInfo";
	LOGGER.enter(CLASSNAME, methodName);
	Boolean modified = false;
	modified = defMgr.modifyAMGDealerInfo(amgDealer);
	LOGGER.exit(CLASSNAME, methodName);
	return modified;
}

@Override
public String getAMGDealerName(String dlrId) {
	LOGGER.enter(CLASSNAME, "getAMGDealerName");
	String dealerName = "";
	dealerName = defMgr.getAMGDealerName(dlrId);
	LOGGER.exit(CLASSNAME,  "getAMGDealerName");
	return dealerName;
}

}
