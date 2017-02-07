/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: DefinitionManagerImpljava
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.dao.DefinitionDAOImpl;
import com.mbusa.dpb.common.dao.IDefinitionDAO;
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
import com.mbusa.dpb.common.domain.ReScheduleProcess;
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
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DateCalenderUtil;
/**
 * @author CB5002578
 *
 */

public class DefinitionManagerImpl implements IDefinitionManager {
	
private IDefinitionDAO prgDefDAO = new DefinitionDAOImpl();	
private static DPBLog LOGGER = DPBLog.getInstance(DefinitionManagerImpl.class);
static final private String CLASSNAME = DefinitionManagerImpl.class.getName();

public void submitDealerProgram(ProgramDefinition programDef,Map<String, List<java.sql.Date>> aProcDteListMap) throws TechnicalException,PersistanceException{
	LOGGER.enter(CLASSNAME, "submitDealerProgram()");
	//ProgramDefinitionBean if programId is not blank then update other wise save
	int programDefId = programDef.getProgramId();
	if(programDefId > 0){
		if(IConstants.STATUS_I.equalsIgnoreCase(programDef.getProgramStatus())){
			updateInactiveDate(programDef); // To be implemented
		}
		else if(IConstants.STATUS_D.equalsIgnoreCase(programDef.getProgramStatus()) || !programDef.isFlagActive()){
			updateDlrProgram(programDef,aProcDteListMap);
		}
		else if(IConstants.STATUS_A.equalsIgnoreCase(programDef.getProgramStatus()) && programDef.isFlagActive()){
			programDef.setFlagActive(true);
		}
	} else {
		saveDlrProgram(programDef,aProcDteListMap);
	}
}

public void saveDlrProgram(ProgramDefinition programDef,Map<String, List<java.sql.Date>> aProcDteListMap) throws TechnicalException, PersistanceException{
	LOGGER.enter(CLASSNAME, "saveDlrProgram()");
	int pId = prgDefDAO.saveDlrProgram(programDef);
		if(pId > 0){
			String specialProgram= programDef.getSpecialProgram();
			if(IConstants.SPECIAL_PGM.equalsIgnoreCase(specialProgram)){
				if(programDef.getCondition()!=null){
					prgDefDAO.insertCondition(programDef, pId);
				}
				if(programDef.getVehicleType()!=null){
					prgDefDAO.insertVehicleType(programDef,pId);
				}
			}else {
				if(programDef.getVehicleType()!=null){
					prgDefDAO.insertVehicleType(programDef,pId);
				}
			}	
			if(IConstants.STATUS_A.equalsIgnoreCase(programDef.getProgramStatus())){
				prgDefDAO.insertDPBProcessDef(programDef,aProcDteListMap);
				programDef.setFlagActive(true);
			}
		}
}

public void updateDlrProgram(ProgramDefinition programDef,Map<String,List<java.sql.Date>> aProcDteListMap) throws TechnicalException, PersistanceException{
	LOGGER.enter(CLASSNAME, "updateDlrProgram()");
	prgDefDAO.updateDlrProgram(programDef);
	int prgId = programDef.getProgramId();
	String specialProgram = programDef.getSpecialProgram();
	if(prgId > 0 && IConstants.SPECIAL_PGM.equalsIgnoreCase(specialProgram)){
		prgDefDAO.updateVehicleType(programDef, prgId);
		prgDefDAO.updateExistingConditions(programDef, prgId);
	}
	else {
		prgDefDAO.updateVehicleType(programDef, prgId);
	}
	if(IConstants.STATUS_A.equalsIgnoreCase(programDef.getProgramStatus())){
		prgDefDAO.insertDPBProcessDef(programDef,aProcDteListMap);
		programDef.setFlagActive(true);
	}
}

public List<ProgramDefinition> getDlrProgramList(){
	LOGGER.enter(CLASSNAME, "getDlrProgramList()");
	List<ProgramDefinition> prgList = new ArrayList<ProgramDefinition>();
	prgList=prgDefDAO.getDlrProgramList();

	return prgList;
}

public void getDlrProgram(ProgramDefinition prgmDef){
	LOGGER.enter(CLASSNAME, "getDlrProgram()");
	prgDefDAO.getDlrProgram(prgmDef);
	if(IConstants.SPECIAL_PGM.equalsIgnoreCase(prgmDef.getSpecialProgram())){
	prgmDef.setMasterCondList(prgDefDAO.getVCMappingList("S"));
	}
}

public boolean validateEndDate(java.sql.Date endDate){
	LOGGER.enter(CLASSNAME, "validateEndDate()");
	boolean isValidDate=prgDefDAO.validateEndDate(endDate);
	return isValidDate;
}
public void updateInactiveDate(ProgramDefinition programDef){
	LOGGER.enter(CLASSNAME, "updateInactiveDate()");
	prgDefDAO.updateInactiveDate(programDef);
		List<String> processTypes = new ArrayList<String>();
		processTypes.add(IConstants.COMMISSION_BONUS_PROCESS);	processTypes.add(IConstants.COMMISSION_PAYMENT_PROCESS);
		processTypes.add(IConstants.FIXED_BONUS_PROCESS);	processTypes.add(IConstants.FIXED_PAYMENT_PROCESS);
		processTypes.add(IConstants.VARIABLE_BONUS_PROCESS);	processTypes.add(IConstants.VARIABLE_PAYMENT_PROCESS);
		prgDefDAO.deleteProcesses(programDef.getProgramId(), processTypes);
		programDef.setInactiveDate(DateCalenderUtil.getCurrentDateTime());
}
@Override
public List<ConditionDefinition> getMasterCondList(String cType) {
	List<ConditionDefinition> masterList = null;
	masterList =prgDefDAO.getVCMappingList("S");
	return masterList;
}

//Condition Method start 
	
public int createCondition(ConditionDefinition cDef) throws BusinessException {
	LOGGER.enter(CLASSNAME, "createCondition()");
	int cId  = cDef.getId() ;	
	if(cDef.getId() > 0 && IConstants.STATUS_I.equalsIgnoreCase(cDef.getStatus())){
		prgDefDAO.inactivateCondition(cDef);
	}else if (cDef != null && cDef.getId() > 0) {
		prgDefDAO.updateConditionDetails(cDef);
	} else {
		cId = prgDefDAO.createCondition(cDef);
		if (cDef.getId() <= 0 && cId > 0) {
			cDef.setId(cId);
		}
	}			
	return cId;		
}
	


//Condition Method start 
	public List<ConditionDefinition> getConditionList(){		
		List<ConditionDefinition> cDefList = prgDefDAO.getConditionList();	
		return cDefList;	
	}
	public void updateConditionDetails(ConditionDefinition cDef){	
		try {
			if(cDef!=null && cDef.getId()  > 0 ){
				prgDefDAO.updateConditionDetails(cDef);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public ConditionDefinition getCondtionDetails(int cId){
		ConditionDefinition cDef = prgDefDAO.getCondtionDetails(cId);		
		return cDef;	
	}
	
	//***********************************************************************************************************************
	public List<ProcessCalDefBean> getCurrentMonthProcess(){
		List<ProcessCalDefBean> processCalView = null;
		try {
			
			processCalView = prgDefDAO.getCurrentMonthProcess();
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return processCalView;
	}
//*********************************
	public FileDefinitionBean  saveFileDefinition(FileDefinitionBean fileDef, List<java.sql.Date> aSchdDatesList) throws BusinessException{
		final String methodName = "saveFileDetails";
		LOGGER.enter(CLASSNAME, methodName);
		int fileDefId = 0;
			if (fileDef.getFileDefId() > 0) {
				updateFileDefinition(fileDef, aSchdDatesList);
			}else{
				fileDefId = prgDefDAO.saveFileDefinition(fileDef);
				fileDef.setFileDefId(fileDefId);
			}
			if("I".equals(fileDef.getDefStatusCode())){
				//prgDefDAO.updateFileInactiveStatus(fileDef);
				List<String> processTypes = new ArrayList<String>();
                processTypes.add(IConstants.FILE_PROCESS_NAME);
                processTypes.add(IConstants.FILE_PRCS_ADJ);
                prgDefDAO.deleteProcesses(fileDef.getFileDefId(), processTypes);
			}
			if("A".equals(fileDef.getDefStatusCode())){
				prgDefDAO.insertDPBFileProcess(fileDef, aSchdDatesList, IConstants.FILE_PROCESS_NAME);
				if(fileDef.getBaseFolder().toLowerCase().contains(IConstants.KPI_BASE_FOLDER)){
					prgDefDAO.insertDPBFileProcess(fileDef, aSchdDatesList, IConstants.FILE_PRCS_ADJ);
				}
			}
				List<FileFormatBean> fileFormat = prgDefDAO.populateformatNameList();
				fileDef.setFileFormat(fileFormat);
			LOGGER.exit(CLASSNAME, methodName);
		return fileDef;
	}
	
	public java.sql.Date getEndDate(java.sql.Date endDate)throws BusinessException{
		LOGGER.enter(CLASSNAME, "validateEndDate()");
		java.sql.Date rEndDate = null;
			rEndDate = prgDefDAO.getEndDate(endDate);
		return rEndDate;
	}

	
	public List<FileFormatBean> populateformatNameList(){
		final String methodName = "populateformatNameList";
		LOGGER.enter(CLASSNAME, methodName);
		List<FileFormatBean> fileFormat = null;
		fileFormat  = prgDefDAO.populateformatNameList();
		LOGGER.exit(CLASSNAME, methodName);
		return fileFormat;
	}

	public void updateFileDefinition(FileDefinitionBean fileDef, List<java.sql.Date> aSchdDatesList){
		final String methodName = "updateFileDefinition";
		LOGGER.enter(CLASSNAME, methodName);
				if("I".equals(fileDef.getDefStatusCode())){
					prgDefDAO.updateFileInactiveStatus(fileDef);
				}else{
				prgDefDAO.updateFileDefinition(fileDef);
				}
				List<FileFormatBean> fileFormat = prgDefDAO.populateformatNameList();
				fileDef.setFileFormat(fileFormat);
				LOGGER.exit(CLASSNAME, methodName);
		}
	public List<FileDefinitionBean> getFileDefinitionList(){
		final String methodName = "getFileDefinitionList";
		LOGGER.enter(CLASSNAME, methodName);
		List<FileDefinitionBean> fDefList = null;
			fDefList = prgDefDAO.getFileDefList();
			LOGGER.exit(CLASSNAME, methodName);
		return fDefList;	
	}
	
	public FileDefinitionBean getEditFileDetails(String fileId){
		final String methodName = "getEditFileDetails";
		LOGGER.enter(CLASSNAME, methodName);
		FileDefinitionBean fDetails = null;
			List<FileFormatBean> fileFormat =prgDefDAO.populateformatNameList();
			fDetails = prgDefDAO.getEditFileDetails(fileId);
			fDetails.setFileFormat(fileFormat);
			LOGGER.exit(CLASSNAME, methodName);
		return fDetails;
		}


	public FileFormatBean saveFileFormatDetails(FileFormatBean formatBean) throws BusinessException{
		final String methodName = "saveFileFormatDetails";
		LOGGER.enter(CLASSNAME, methodName);
		if("0".equals(formatBean.getTableName())){
			formatBean.setTableName("");
		}
		if(formatBean != null && formatBean.getFileFormatId() > 0){
			updateFileFormatDetails(formatBean);
		}else{
			int	fileFormatId = prgDefDAO.saveFileFormatDetails(formatBean);
			formatBean.setFileFormatId(fileFormatId);
		}
			formatBean = prgDefDAO.populateColumnMapList(formatBean);
		LOGGER.exit(CLASSNAME, methodName);
		return formatBean;
		}

	public void updateFileFormatDetails(FileFormatBean formatBean) throws BusinessException{
		final String methodName = "updateFileFormatDetails";
		LOGGER.enter(CLASSNAME, methodName);
		if("I".equals(formatBean.getDefStatusCode())){
			prgDefDAO.updateFileFormatInactiveStatus(formatBean);
		}else{
			prgDefDAO.updateFileFormatDetails(formatBean);
		}
		LOGGER.exit(CLASSNAME, methodName);
	}


	public List<FileFormatBean> getFileFormatListDetails(){
		final String methodName = "getFileFormatListDetails";
		LOGGER.enter(CLASSNAME, methodName);
		List<FileFormatBean> fileFormatList=null;
			fileFormatList = prgDefDAO.getFileFormatListDetails();
			LOGGER.exit(CLASSNAME, methodName);
		return fileFormatList;
	}

	public List<String> populateColumns(String tableName) {
		final String methodName = "populateColumns";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> columnNames = null;
			columnNames = prgDefDAO.populateColumns(tableName);
			LOGGER.exit(CLASSNAME, methodName);
		return columnNames;
	}

	public FileFormatBean getEditFileFormatDetails(String fileId) {
		final String methodName = "getEditFileFormatDetails";
		LOGGER.enter(CLASSNAME, methodName);
		FileFormatBean formatBean = null;			
			formatBean = prgDefDAO.getEditFileFormatDetails(fileId);
			LOGGER.exit(CLASSNAME, methodName);
		return formatBean;
	}


public int createRetailMonthEntry(RtlMonthDefinition rtlMonthDef){	
	int idRtlDte=0;	
		idRtlDte=prgDefDAO.createRetailMonthEntry(rtlMonthDef);
		return idRtlDte;
	
}
public List<VehicleConditionMapping> getVehicleMappedCondition(String cType){
	final String methodName = "getVehiclMappedCondtion";
	LOGGER.enter(CLASSNAME, methodName);
	List<VehicleConditionMapping> vehicleCond = null;
	vehicleCond = prgDefDAO.getVehicleMappedCondition(cType);
	LOGGER.exit(CLASSNAME, methodName);
	return vehicleCond;
}
public void saveVehicleMappedCondition(List <VehicleConditionMapping> vcList,String cType){	
	final String methodName = "saveVehicleMappedCondition";
	LOGGER.enter(CLASSNAME, methodName);
	List<VehicleConditionMapping> vehicleCond = null;	
	vehicleCond = prgDefDAO.getVehicleMappedCondition(cType);
	prgDefDAO.deleteMappingConditions(vehicleCond,cType);
	prgDefDAO.saveVehicleMappedCondition(vcList,cType);	
	LOGGER.exit(CLASSNAME, methodName);
}

public RtlMonthDefinition getStartDate()
{ 
	RtlMonthDefinition rtlMonthDef=null;
	rtlMonthDef=prgDefDAO.getStartDate();
	return rtlMonthDef;
}
public List<ProcessInputFile> getProcInputFileDetails()
{
	List<ProcessInputFile> procInputFile=null;
		procInputFile = prgDefDAO.getProcInputFileDetails();
		return procInputFile;
			
}

public List<GeneratePaymentFile> genPayFile()
{
	List<GeneratePaymentFile> genPaymentFile=null;
	genPaymentFile = prgDefDAO.genPayFile();
	return genPaymentFile;
			
}

public List<GenerateReportFile> genReportFile()
{
	List<GenerateReportFile> genReportFile=null;
	return genReportFile;		
}



public List<ProcessBonus> procBonusProc()
{
	List<ProcessBonus> ProcessBonus=null;	
	ProcessBonus = prgDefDAO.procBonusProc();
	return ProcessBonus;
			
}





public List<ProcessLdrBonus> processLdrBonus()
{
	List<ProcessLdrBonus> processLdrBonus=null;
	processLdrBonus = prgDefDAO.processLdrBonus();
	return processLdrBonus;
			
}


public void  saveReportQueryDef(ReportQuery reportQuery){
	
	
	if(reportQuery.getReportQueryId()>0)
	{
		prgDefDAO.updateReportQueryDef(reportQuery);
	}
	else
	{
		prgDefDAO.saveReportQueryDef(reportQuery);
		
	}
	
}

public ReportQuery getReportQueryEdit(String rId)
{
	ReportQuery reportQuery;
	
		reportQuery = prgDefDAO.getReportQueryEdit(rId);		

return reportQuery;
	
}

public List<ReportQuery> getReportQueryList()
{
	List<ReportQuery> lst;
	lst=prgDefDAO.getReportQueryList();		
	return lst;
}
//LDRSP Bonus Start
public LeadershipBonusDetails saveLdrshipBonusDef(LeadershipBonusDetails ldrshipbnsdtls){	
	final String methodName = "saveLdrshipBonusDef";
	LOGGER.enter(CLASSNAME, methodName);
	int retailId = 0;
	if(ldrshipbnsdtls.getStatus().equalsIgnoreCase(IConstants.STATUS_D)  && ldrshipbnsdtls.getLdrshipid() == 0) {
			prgDefDAO.saveLdrshipBonusDef(ldrshipbnsdtls);	
			prgDefDAO.insertPrgVehMap(ldrshipbnsdtls.getLdrshipid(),ldrshipbnsdtls.getLdrshipAppVeh(),ldrshipbnsdtls.getUserId(),ldrshipbnsdtls.getStatus());
	} 
	else if ((ldrshipbnsdtls.getStatus().equalsIgnoreCase(IConstants.STATUS_D) && ldrshipbnsdtls.getLdrshipid() != 0  )|| !ldrshipbnsdtls.isFlagActive() && ldrshipbnsdtls.getStatus().equalsIgnoreCase(IConstants.STATUS_A) && ldrshipbnsdtls.getLdrshipid() != 0) {
			prgDefDAO.updateLdrshipBonusDef(ldrshipbnsdtls);
			prgDefDAO.deleteLdrspVehMapping(ldrshipbnsdtls.getLdrshipid());
			prgDefDAO.insertPrgVehMap(ldrshipbnsdtls.getLdrshipid(), ldrshipbnsdtls.getLdrshipAppVeh(),ldrshipbnsdtls.getUserId(),ldrshipbnsdtls.getStatus());
			//Draft to Active status change
			if(!ldrshipbnsdtls.isFlagActive() && ldrshipbnsdtls.getStatus().equalsIgnoreCase(IConstants.STATUS_A)){
				//retailId = prgDefDAO.getRTLCalId(ldrshipbnsdtls.getProcessDate());
				//ldrshipbnsdtls.setRetailId(retailId);
				prgDefDAO.insertDPBProc(ldrshipbnsdtls);
				ldrshipbnsdtls.setFlagActive(true);
			}
	}
	else if(ldrshipbnsdtls.getStatus().equalsIgnoreCase(IConstants.STATUS_A) && ldrshipbnsdtls.getLdrshipid() == 0) {
			prgDefDAO.saveLdrshipBonusDef(ldrshipbnsdtls);	
			prgDefDAO.insertPrgVehMap(ldrshipbnsdtls.getLdrshipid(),ldrshipbnsdtls.getLdrshipAppVeh(),ldrshipbnsdtls.getUserId(),ldrshipbnsdtls.getStatus());
			//retailId = prgDefDAO.getRTLCalId(ldrshipbnsdtls.getProcessDate());
			//ldrshipbnsdtls.setRetailId(retailId);
			prgDefDAO.insertDPBProc(ldrshipbnsdtls);
			ldrshipbnsdtls.setFlagActive(true);
	}
	else if (ldrshipbnsdtls.getStatus().equalsIgnoreCase(IConstants.STATUS_A) && ldrshipbnsdtls.getLdrshipid() != 0) {
			ldrshipbnsdtls.setFlagActive(true);
	}	
	else if (ldrshipbnsdtls.getStatus().equalsIgnoreCase(IConstants.STATUS_I) && ldrshipbnsdtls.getLdrshipid() != 0) {
		// Inactive Program
		String inActiveDate = DateCalenderUtil.getNextDayDate();
		prgDefDAO.updateInactiveDate(ldrshipbnsdtls.getLdrshipid(), inActiveDate, ldrshipbnsdtls.getStatus(),ldrshipbnsdtls.getUserId());
		List<String> processTypes = new ArrayList<String>();
		processTypes.add(IConstants.LEADERSHIP_BONUS_PROCESS);
		processTypes.add(IConstants.LEADERSHIP_PAYMENT_PROCESS);
		prgDefDAO.deleteProcesses(ldrshipbnsdtls.getLdrshipid(), processTypes);

		ldrshipbnsdtls.setFlagActive(true);
	}
	LOGGER.exit(CLASSNAME, methodName);
	return ldrshipbnsdtls;	
}	

/* (non-Javadoc)
* @see com.mbusa.dpb.web.bo.ILeadershipBonusDefinationBO#ldrshipBonusDtlsFYRChange(com.mbusa.dpb.definitions.beans.LeadershipBonusDetailsBean)
*/
/*	public LeadershipBonusDetails ldrshipBonusDtlsFYRChange(LeadershipBonusDetails ldrshipbnsdtls) {
		final String methodName = "ldrshipBonusDtlsFYRChange";
		LOGGER.enter(CLASSNAME, methodName);
		double unusedAmt = 0.00;
		if (ldrshipbnsdtls.getLdrshipbnsfisyr() != 0) {
			ldrshipbnsdtls = prgDefDAO.getLdrshipBonusAmt(ldrshipbnsdtls);
			//ldrshipbnsdtls.setUnusdamt(unusedAmt);
		}
		LOGGER.exit(CLASSNAME, methodName);
		return ldrshipbnsdtls;
	}*/
/* (non-Javadoc)
* @see com.mbusa.dpb.web.bo.ILeadershipBonusDefinationBO#getLdrshipBonusList()
*/
	public List<LeadershipBonusDetails> getLdrshipBonusList() {
		final String methodName = "getLdrshipBonusList";
		LOGGER.enter(CLASSNAME, methodName);
		List<LeadershipBonusDetails> ldrshipBnsList = null;
		ldrshipBnsList = new ArrayList<LeadershipBonusDetails>();
		ldrshipBnsList = prgDefDAO.getLdrshipBonusList();
		LOGGER.exit(CLASSNAME, methodName);
		return ldrshipBnsList;
	}
/* (non-Javadoc)
* @see com.mbusa.dpb.web.bo.ILeadershipBonusDefinationBO#getLdrshipBonusDefDetails(com.mbusa.dpb.definitions.beans.LeadershipBonusDetailsBean)
*/
	public LeadershipBonusDetails getLdrshipBonusDefDetails(LeadershipBonusDetails ldrshipbnsdtls) {
		final String methodName = "getLdrshipBonusDefDetails";
		LOGGER.enter(CLASSNAME, methodName);
		double unusedAmt = 0.0;
		double ldrshipBnsAmt = 0.0;
		double busrsrvAmt = 0.0;
		prgDefDAO.getLdrshipBonusDefDetails(ldrshipbnsdtls);
		prgDefDAO.getApplicationVehicle(ldrshipbnsdtls);
	/*	unusedAmt = prgDefDAO.getLdrshipBonusAmt(ldrshipbnsdtls);
		ldrshipbnsdtls.setUnusdamt(unusedAmt);
		if (ldrshipbnsdtls.getUnusdamt() != 0.0 && ldrshipbnsdtls.getBusresvamt() != 0.0) {
			unusedAmt = ldrshipbnsdtls.getUnusdamt();
			busrsrvAmt = ldrshipbnsdtls.getBusresvamt();
			ldrshipBnsAmt = unusedAmt - busrsrvAmt;
			ldrshipbnsdtls.setLdrbnsamt(ldrshipBnsAmt);
		}*/
		if (!ldrshipbnsdtls.getStatus().equalsIgnoreCase("D")) {
			ldrshipbnsdtls.setFlagActive(true);
		} else {
			ldrshipbnsdtls.setFlagActive(false);
		}
		LOGGER.exit(CLASSNAME, methodName);
		return ldrshipbnsdtls;
	}

	public LeadershipBonusDetails getLdrshipBonusAmt(LeadershipBonusDetails ldrshipbnsdtls) {
		final String methodName = "getLdrshipBonusAmt";
		LOGGER.enter(CLASSNAME, methodName);
		//double unUsedAmt = 0.0;
		ldrshipbnsdtls = prgDefDAO.getLdrshipBonusAmt(ldrshipbnsdtls);
		LOGGER.exit(CLASSNAME, methodName);
		return ldrshipbnsdtls;
	}
	public boolean validateProcessDate(String procDate) {
		final String methodName = "validateProcessDate";
		LOGGER.enter(CLASSNAME, methodName);
		// TODO Auto-generated method stub
		boolean isValidDate = prgDefDAO.validateProcessDate(procDate);
		LOGGER.exit(CLASSNAME, methodName);
		return isValidDate;
	}
	public double validateUnearnedAmount(String acrlstartDate,String acrlendDate) {
		final String methodName = "validateUnearnedAmount";
		LOGGER.enter(CLASSNAME, methodName);
		double unEarnedAmount = 0.00;
		unEarnedAmount = prgDefDAO.validateUnearnedAmount(acrlstartDate,acrlendDate);
		LOGGER.exit(CLASSNAME, methodName);
		return unEarnedAmount;
	}

//LDRSP Bonus End
//AccountID Mapping Start
	public Map<String, Object> getAccountIDMaping() {
		final String methodName = "validateProcessDate";
		LOGGER.enter(CLASSNAME, methodName);
		Map<String, Object> vehicleCond = null;
		vehicleCond = prgDefDAO.getAccountIDMaping();
		LOGGER.exit(CLASSNAME, methodName);
		return vehicleCond;
	}

	public void saveAccountIDMapping(List<AccountMapping> acctMapList,String userId) {
		final String methodName = "validateProcessDate";
		LOGGER.enter(CLASSNAME, methodName);
		//List<ConditionDefinition> conditionList = null;
		prgDefDAO.saveAccountIDMapping(acctMapList, userId);
		LOGGER.exit(CLASSNAME, methodName);
	}

//AccountID Mapping End
//Nikhil changes start
@Override
public ReportDefinitionBean createReportDefinition(ReportDefinitionBean reportDef, List<java.sql.Date> aSchdDatesList) throws BusinessException{
	final String methodName = "createReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	int pId = 0;
		if(reportDef != null && reportDef.getReportDefId() > 0){
			updateReportDefinition(reportDef);
		}
		else{
			pId = prgDefDAO.createReportDefinition(reportDef);
			reportDef.setReportDefId(pId);
		}
			reportDef = prgDefDAO.populateQCRelations(reportDef);
		if("A".equals(reportDef.getDefStatusCode())){
			prgDefDAO.createDPBReportProcess(reportDef, aSchdDatesList);
		}
		LOGGER.exit(CLASSNAME, methodName);
		return reportDef;
}

public List<ReportDefinitionBean> getReportDefinitionList(){
	final String methodName = "getReportDefinitionList";
	LOGGER.enter(CLASSNAME, methodName);
	List<ReportDefinitionBean> reportDefList = null;
	reportDefList = prgDefDAO.getReportDefinitionList();
	LOGGER.exit(CLASSNAME, methodName);
	return reportDefList;
}


public ReportDefinitionBean getEditReportDefinition(String reportDefId){
	final String methodName = "getEditReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	ReportDefinitionBean reportDef = null;
	reportDef = prgDefDAO.getEditReportDefinition(reportDefId);
	LOGGER.exit(CLASSNAME, methodName);
	return reportDef;
}

public void updateReportDefinition(ReportDefinitionBean reportDef){
	final String methodName = "updateReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	if("I".equals(reportDef.getDefStatusCode())){
		prgDefDAO.updateReportInactiveStatus(reportDef);
		List<String> processTypes = new ArrayList<String>();
        processTypes.add(IConstants.REPORT_PROCESS_NAME);
        prgDefDAO.deleteProcesses(reportDef.getReportDefId(), processTypes);
	}else{
		prgDefDAO.updateReportDefinition(reportDef);
	}
	LOGGER.exit(CLASSNAME, methodName);
}

/*@Override
public ReportDefinitionBean retrieveDefaultReportDefinition(ReportDefinitionBean reportDefBean){
	final String methodName = "retrieveDefaultReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	//ReportDefinitionBean rDefBean = null;		
		reportDefBean = prgDefDAO.retrieveDefaultReportDefinition(reportDefBean);
		LOGGER.exit(CLASSNAME, methodName);
	return reportDefBean;
}*/

@Override
public FileProcessDefBean getFileProcessDefination(int processId) {
	final String methodName = "getFileProcessDefination";
	LOGGER.enter(CLASSNAME, methodName);
	FileProcessDefBean fileProcessDefBean = prgDefDAO.getFileProcessDefination(processId);
	LOGGER.exit(CLASSNAME, methodName);
	return fileProcessDefBean;
}

@Override
public int updateFileProcessDefinition(FileProcessDefBean fileProcessDefBean) {
	int processId =prgDefDAO.updateFileProcessDefinition(fileProcessDefBean);
	return processId;
}@Override
public int createReportContentDefinition(ReportContentDefinitionBean reportContentDefBean) throws BusinessException{
	final String methodName = "createReportContentDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	int pId = 0;
		pId = reportContentDefBean.getReportContentDefId();
		if(pId == 0){
			pId = prgDefDAO.createReportContentDefinition(reportContentDefBean);		
		}
		else if("I".equals(reportContentDefBean.getDefStatusCode())){
				prgDefDAO.updateReportContentInactive(reportContentDefBean);
			}else{
			prgDefDAO.updateReportContentDefinition(reportContentDefBean);
			}
		LOGGER.exit(CLASSNAME, methodName);
	return pId;
}
//Nikhil changes end
public List<ReportContentDefinitionBean> getReportContentList(){
	final String methodName = "getReportContentList";
	LOGGER.enter(CLASSNAME, methodName);
	List<ReportContentDefinitionBean> rptCntList = null;
	rptCntList = prgDefDAO.getReportContentList();
	LOGGER.exit(CLASSNAME, methodName);
	return rptCntList;
}
public ReportContentDefinitionBean getEditReportContent(String rptCntDefId){
	final String methodName = "getEditReportContent";
	LOGGER.enter(CLASSNAME, methodName);
	ReportContentDefinitionBean reportContentDefBean = null;
	reportContentDefBean = prgDefDAO.getEditReportContent(rptCntDefId);
	LOGGER.exit(CLASSNAME, methodName);
	return reportContentDefBean;
}

public String getReportDefStatus(ReportQuery reportQuery)
{
		String str="";
		str =  prgDefDAO.getReportDefStatus(reportQuery);	
		return str;
}
	
@Override
public FileDefinitionBean fetchFileDefinition(int processID) {
	// TODO Auto-generated method stub
	FileDefinitionBean fileDefinitionBean = prgDefDAO.fetchFileDefinition (processID);
	return fileDefinitionBean;
}

public void insertIntoProcessLog (FileProcessLogMessages message) {
	final String methodName = "insertIntoProcessLog";
	LOGGER.enter(CLASSNAME, methodName);
	prgDefDAO.insertIntoProcessLog(message);
	LOGGER.exit(CLASSNAME, methodName);
}

@Override
public void deleteVistaProcessedData(int processId) {
	final String methodName = "deleteVistaProcessedData";
	LOGGER.enter(CLASSNAME, methodName);
	prgDefDAO.deleteVistaProcessedData(processId);
	LOGGER.exit(CLASSNAME, methodName);
	
}

@Override
public void deleteBlockedData(int processId) {
	final String methodName = "deleteBlockedData";
	LOGGER.enter(CLASSNAME, methodName);
	prgDefDAO.deleteBlockedData(processId);
	LOGGER.exit(CLASSNAME, methodName);
	
}

@Override
public void updateBonusProcesses(java.sql.Date actualDate,  String processType,String message, String userId) {
	final String methodName = "updateBonusProcesses";
	LOGGER.enter(CLASSNAME, methodName);
	prgDefDAO.updateBonusProcesses(actualDate, processType, message,userId);
	LOGGER.exit(CLASSNAME, methodName);
	
}

@Override
public void reProcessingVistaFile(FileDefinitionBean definitionBean, int processId, String message, String processType, String userId, boolean isLdrShpBonus) {
	final String methodName = "reProcessingVistaFile";
	LOGGER.enter(CLASSNAME, methodName);
	performBonusReset(processId, userId, processType, isLdrShpBonus, false, false);
	deleteBlockedData(processId);
	deleteVistaProcessedData(processId);
	
	/*if(IConstants.PROC_STATUS_RESET.equalsIgnoreCase(processType)){
	performBonusReset(processId, userId, processType, isLdrShpBonus, false);	
	}else {
	updateBonusProcesses(definitionBean.getActualDate(), processType, message, userId);
	}*/
	
}

@Override
public void reProcessingKpiFile(FileDefinitionBean definitionBean,
		int processId, String message,String processType, String userId, boolean isLdrShpBonus) {
	final String methodName = "reProcessingKpiFile";
	LOGGER.enter(CLASSNAME, methodName);
	boolean isKpi = true;
	deleteKpiData(processId);
	performBonusReset(processId, userId, processType, isLdrShpBonus, false, isKpi);	
	
	/*if(IConstants.PROC_STATUS_RESET.equalsIgnoreCase(processType)){
		performBonusReset(processId, userId, processType, isLdrShpBonus, false);	
	}else {
	updateBonusProcesses(definitionBean.getActualDate(), processType, message, userId);
	}*/
	
}

private void deleteKpiData(int processId) {
	final String methodName = "deleteKpiData";
	LOGGER.enter(CLASSNAME, methodName);
	prgDefDAO.deleteKpiData(processId);
	LOGGER.exit(CLASSNAME, methodName);
}

@Override
public void reProcessingAccrualFile(FileDefinitionBean definitionBean,
		int processId, String message, String processType, String userId, boolean isLdrShpBonus) {
	final String methodName = "reProcessingAccrualFile";
	LOGGER.enter(CLASSNAME, methodName);
	performBonusReset(processId, userId, processType, isLdrShpBonus, false, false);	
	deleteAccrualData(processId);
	/*if(IConstants.PROC_STATUS_RESET.equalsIgnoreCase(processType)){
		performBonusReset(processId, userId, processType, isLdrShpBonus, false);	
	}else {
	updateAccrualBonusProcesses(definitionBean.getActualDate(), message, processType, userId);
	}*/
	
}

private void updateAccrualBonusProcesses(java.sql.Date actualDate, String message, String processType,
		String userId) {
	final String methodName = "updateAccrualBonusProcesses";
	LOGGER.enter(CLASSNAME, methodName);
	prgDefDAO.updateAccrualBonusProcesses(actualDate, message, processType, userId);
	LOGGER.exit(CLASSNAME, methodName);
	
}

private void deleteAccrualData(int processId) {
	final String methodName = "deleteKpiData";
	LOGGER.enter(CLASSNAME, methodName);
	prgDefDAO.deleteAccrualData(processId);
	LOGGER.exit(CLASSNAME, methodName);
	
}

@Override
public void performBonusReprocess(int processId, String userId, String processType, boolean ldrShpBonusProcess) {
	final String methodName = "performBonusReprocess";
	LOGGER.enter(CLASSNAME, methodName);
	prgDefDAO.performBonusReset(processId, userId, processType, ldrShpBonusProcess, true, false);
	LOGGER.exit(CLASSNAME, methodName);
}

@Override
public void performBonusReset(int processId, String userId, String processType,boolean ldrShpBonusProcess, boolean isBonus, boolean isKpi) {
	final String methodName = "performBonusReset";
	LOGGER.enter(CLASSNAME, methodName);
	prgDefDAO.performBonusReset(processId, userId, processType, ldrShpBonusProcess, isBonus, isKpi);
	LOGGER.exit(CLASSNAME, methodName);
}

@Override
public boolean validateCondition(ConditionDefinition conditionDef) {
	final String methodName = "validateCondition";
	LOGGER.enter(CLASSNAME, methodName);
	boolean isValidCondition = prgDefDAO.validateUniqueCondition(conditionDef);
	LOGGER.exit(CLASSNAME, methodName);
	return isValidCondition;
}

@Override
public boolean validateProgram(int programId, String programName, Date startDate) {
	final String methodName = "validateProgram";
	LOGGER.enter(CLASSNAME, methodName);
	boolean isValidProgram = prgDefDAO.validateProgram(programId,programName, startDate);
	LOGGER.exit(CLASSNAME, methodName);
	return isValidProgram;
}

@Override
public boolean checkPaymentProcesses(int processId) {
	final String methodName = "checkPaymentProcesses";
	LOGGER.enter(CLASSNAME, methodName);
	boolean isPaymentSuccess = prgDefDAO.checkPaymentProcesses(processId);
	LOGGER.exit(CLASSNAME, methodName);
	return isPaymentSuccess;
}

@Override
public int updateProcessDefinition(FileProcessDefBean fileProcessDefBean, int fileType, boolean bonusProcess, boolean ldrshpBonus) {
	List<ReScheduleProcess> processIdList = new ArrayList<ReScheduleProcess>();
	if(fileType > 0){
		processIdList = prgDefDAO.getProcessesForFileReschedule(fileProcessDefBean , fileType);
	}
	if(bonusProcess || ldrshpBonus){
		processIdList = prgDefDAO.getProcessesForBonusReschedule(fileProcessDefBean);
	}
	if(processIdList.size() > 0){
		//	prgDefDAO.updateProcessDefinition(fileProcessDefBean, processIdList);
		prgDefDAO.insertReportProcessForReSchedule(fileProcessDefBean, processIdList);

		if(ldrshpBonus){
		prgDefDAO.insertLeaderShipBonusProcessForReSchedule(fileProcessDefBean, processIdList);
		}else{
			prgDefDAO.insertBonusProcessForReSchedule(fileProcessDefBean, processIdList);// Includes Payment Also
		}
	}
	prgDefDAO.insertReScheduleProcessLogList(processIdList,fileProcessDefBean);
	int processId =prgDefDAO.updateFileProcessDefinition(fileProcessDefBean);
	return processId;
}

@Override
public RetailDate getRetailData(String dbMonth, String dbYear) {
	final String methodName = "getRetailData";
	LOGGER.enter(CLASSNAME, methodName);
	RetailDate rtlDate = new RetailDate();
	rtlDate = prgDefDAO.getRetailData(dbMonth, dbYear);
	LOGGER.exit(CLASSNAME, methodName);
	return rtlDate;
}

@Override
public boolean checkValidMonth(String month, String year) {
	final String methodName = "checkValidMonth";
	LOGGER.enter(CLASSNAME, methodName);
	boolean isValidMonth = false;
	isValidMonth = prgDefDAO.checkValidMonth(month, year);
	LOGGER.exit(CLASSNAME, methodName);
	return isValidMonth;
}

@Override
public void updateProcessesRetailDate(Date oldRtlProcessingDate, Date newRtlProcessingDate,String currentStartDte,String newEndDte,int cYear,int cMonth, int nYear,String userID) {
	final String methodName = "updateProcessesRetailDate";
	LOGGER.enter(CLASSNAME, methodName);
	List<Integer> processesList = new ArrayList<Integer>();
	prgDefDAO.updateDatesInDpbDay(currentStartDte, newEndDte,cYear,cMonth, nYear,userID);
	processesList = prgDefDAO.getRtlMonthEndProcesses(oldRtlProcessingDate);
	if(processesList!=null && !processesList.isEmpty()){
		prgDefDAO.updateProcessesRtlEndDates(processesList,newRtlProcessingDate,userID);		
	}	
	LOGGER.exit(CLASSNAME, methodName);
}

public List<AMGDealer> getDlrsInfoList(String dealerId){
	final String methodName = "getDlrsInfoList";
	LOGGER.enter(CLASSNAME, methodName);
	List<AMGDealer> dealerInfoList = null;
	dealerInfoList = prgDefDAO.getDlrsInfoList(dealerId);
	LOGGER.exit(CLASSNAME, methodName);
	return dealerInfoList;
}

public Boolean modifyAMGDealerInfo(AMGDealer amgDealer){
	final String methodName = "modifyAMGDealerInfo";
	LOGGER.enter(CLASSNAME, methodName);
	Boolean modified= false;
	DateFormat sdff = new SimpleDateFormat("MM/dd/yyyy");
   
	//DAO to get retail start date a based on year and month
	RetailDate rtlDate1 = prgDefDAO.getRetailData(amgDealer.getRetailStartMonth(), amgDealer.getRetailYear());
	//DAO to get retail end date a based on year and month
	RetailDate rtlDate2 = prgDefDAO.getRetailData(amgDealer.getRetailEndMonth(), amgDealer.getRetailEndYear());
	try {
		
		if(rtlDate1 != null)
			amgDealer.setStartDate(sdff.parse(rtlDate1.getRtlStartDate()));
		
		if(rtlDate2 !=null)
			amgDealer.setEndDate(sdff.parse(rtlDate2.getRtlEndDate()));
		
		//The Default end date for all AMG dealers is marked as Jan 2020. 
		//This needs to be replaced after active/inactive flag is added deletion in DB later	
		else
			amgDealer.setEndDate(sdff.parse("01/03/2020"));	
		
	} catch (ParseException e) {		
		e.printStackTrace();
	}
	
	modified = prgDefDAO.modifyAMGDealerInfo(amgDealer);
	LOGGER.exit(CLASSNAME, methodName);
	return modified;
}

public String getAMGDealerName(String dlrId) {
	LOGGER.enter(CLASSNAME, "getAMGDealerName");
	String dealerName = "";
	dealerName = prgDefDAO.getAMGDealerName(dlrId);
	LOGGER.exit(CLASSNAME,  "getAMGDealerName");
	return dealerName;
}



}
