/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: BusinessDelegate.java
 * Program Version			: 1.0
 * Program Description		: This class is used to delegate request to Bean
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 27, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.delegate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
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
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.form.LdrspAccountIDMappingForm;
import com.mbusa.dpb.web.helper.IWebConstants;

public class BusinessDelegate {
	private LocalServiceFactory local =  new LocalServiceFactory();
	private static DPBLog LOGGER = DPBLog.getInstance(BusinessDelegate.class);
	static final private String CLASSNAME = BusinessDelegate.class.getName();

	public ProgramDefinition submitDealerProgram(ProgramDefinition programDef,Map<String, List<java.sql.Date>> aProcDteListMap) throws BusinessException,TechnicalException{	
		LOGGER.enter(CLASSNAME, "submitDealerProgram()");
		try {
			local.getDefinitionService().submitDealerProgram(programDef,aProcDteListMap);
		}catch (TechnicalException te) {
			LOGGER.error("DPB.DEF.PGM.DEL.001", te);
			throw new TechnicalException("",te.getMessageKey());
		}
		return programDef;
}
	
	public List<ProgramDefinition> getDlrProgramsList() throws BusinessException,TechnicalException{
		LOGGER.enter(CLASSNAME, "getDlrProgramsList()");
		List<ProgramDefinition> prgList = new ArrayList<ProgramDefinition>();
		try {
			prgList = local.getDefinitionService().getDlrProgramsList();
		}catch (TechnicalException te) {
			LOGGER.error("DPB.DEF.PGM.DEL.002", te);
			throw new TechnicalException("",te.getMessageKey());
		}
		return prgList;
}
	
	public void getDlrPrograms(ProgramDefinition dlrPrgm) throws BusinessException,TechnicalException{
		LOGGER.enter(CLASSNAME, "getDlrPrograms()");
		try {
			local.getDefinitionService().getDlrProgram(dlrPrgm);
		}catch (TechnicalException te) {
			LOGGER.error("DPB.DEF.PGM.DEL.003", te);
			throw new TechnicalException("",te.getMessageKey());
		}
}
	public boolean validateEndDate(java.sql.Date endDate)throws BusinessException,TechnicalException {
		LOGGER.enter(CLASSNAME, "validateEndDate()");
		boolean isValidEndDate =false;
		try{
			isValidEndDate = local.getDefinitionService().validateEndDate(endDate);
		}catch(TechnicalException te) {
			LOGGER.error("DPB.DEF.PGM.DEL.003", te);
			throw new TechnicalException("",te.getMessageKey());
		}
		return isValidEndDate;
}

	public List<ConditionDefinition> getMasterConditionList(String splProgram)throws BusinessException,TechnicalException {
		LOGGER.enter(CLASSNAME, "getMasterConditionList()");
		List<ConditionDefinition> masterList = null;
		try{
			masterList = local.getDefinitionService().getMasterCondList(splProgram);
		}catch(TechnicalException te) {
			LOGGER.error("DPB.DEF.PGM.DEL.004", te);
			throw new TechnicalException("",te.getMessageKey());
		}
		return masterList;
}
	public ConditionDefinition createCondition(ConditionDefinition cDef) throws BusinessException, TechnicalException{	
		LOGGER.enter(CLASSNAME, "createCondition()");
		int cId;
		try {
			cId = local.getDefinitionService().createCondition(cDef);
			if (cDef.getId() <= 0 && cId > 0) {
				cDef.setId(cId);
			}
		} catch (TechnicalException te) {
			throw new TechnicalException("",te.getMessageKey());
		}
		return cDef;	
	}

public List<ConditionDefinition> getConditionList() throws BusinessException,TechnicalException {
	LOGGER.enter(CLASSNAME, "getConditionList()");
	List<ConditionDefinition> cDefList = null;
	try {
		cDefList = local.getDefinitionService().getConditionList();		
	}catch (TechnicalException te) {
		throw new TechnicalException("",te.getMessageKey());
	}
	return cDefList;

}
public ConditionDefinition getCondtionDetails(int cId) throws BusinessException,TechnicalException {
	LOGGER.enter(CLASSNAME, "getCondtionDetails()");
	ConditionDefinition cDef = null;
	try {
		cDef = local.getDefinitionService().getCondtionDetails(cId);
	}catch (TechnicalException te) {
		throw new TechnicalException("",te.getMessageKey());
	}
	return cDef;
	
}



//**************************************************************************

public FileDefinitionBean saveFileDetails(FileDefinitionBean fDef, List<java.sql.Date> aSchdDatesList) throws BusinessException, TechnicalException{
	final String methodName = "saveFileDetails";
	LOGGER.enter(CLASSNAME, methodName);
		fDef = local.getDefinitionService().saveFileDefinition(fDef, aSchdDatesList);
		LOGGER.exit(CLASSNAME, methodName);
	return fDef;
}

public java.sql.Date getEndDate(java.sql.Date endDate)throws BusinessException,TechnicalException {
	LOGGER.enter(CLASSNAME, "validateEndDate()");
	java.sql.Date rEndDate = null;
		rEndDate = local.getDefinitionService().getEndDate(endDate);
	return rEndDate;
}

public List<FileFormatBean> populateformatNameList() throws BusinessException, TechnicalException{
	final String methodName = "populateformatNameList";
	LOGGER.enter(CLASSNAME, methodName);
	List<FileFormatBean> fileFormat = null;
				fileFormat = local.getDefinitionService().populateformatNameList();
				LOGGER.exit(CLASSNAME, methodName);
		return fileFormat;
}

public FileDefinitionBean getEditFileDetails(String fileId)  throws BusinessException, TechnicalException{
	final String methodName = "getEditFileDetails";
	LOGGER.enter(CLASSNAME, methodName);
	FileDefinitionBean fDetails = null;
			fDetails = local.getDefinitionService().getEditFileDetails(fileId);
			LOGGER.exit(CLASSNAME, methodName);
	return fDetails;
}
//to get list of file Definitions

public List<FileDefinitionBean> getFileDefinitionList()  throws BusinessException, TechnicalException{
	final String methodName = "getFileDefinitionList";
	LOGGER.enter(CLASSNAME, methodName);
	List<FileDefinitionBean> fDefList = null;
			fDefList = local.getDefinitionService().getFileDefinitionList();
			LOGGER.exit(CLASSNAME, methodName);
	return fDefList;
}

public FileFormatBean saveFileFormatDetails(FileFormatBean formatBean)  throws BusinessException, TechnicalException{
	final String methodName = "saveFileFormatDetails";
	LOGGER.enter(CLASSNAME, methodName);
	formatBean= local.getDefinitionService().saveFileFormatDetails(formatBean);
	LOGGER.exit(CLASSNAME, methodName);
	return formatBean;
}

public FileFormatBean getEditFileFormatDetails(String fileId)  throws BusinessException, TechnicalException{
	final String methodName = "getEditFileFormatDetails";
	LOGGER.enter(CLASSNAME, methodName);
	FileFormatBean formatBean = null;
			formatBean = local.getDefinitionService().getEditFileFormatDetails(fileId);
			LOGGER.exit(CLASSNAME, methodName);
	return formatBean;
}

public List<FileFormatBean> getFileFormatListDetails()  throws BusinessException, TechnicalException{
	final String methodName = "getFileFormatListDetails";
	LOGGER.enter(CLASSNAME, methodName);
	List<FileFormatBean> fileFormatList = null;
			fileFormatList = local.getDefinitionService().getFileFormatListDetails();
			LOGGER.exit(CLASSNAME, methodName);
	return fileFormatList;
}

public List<String> populateColumns(String tableName)  throws BusinessException, TechnicalException{
	final String methodName = "populateColumns";
	LOGGER.enter(CLASSNAME, methodName);
	List<String> columnNames = null;
			columnNames = local.getDefinitionService().populateColumns(tableName);
			LOGGER.exit(CLASSNAME, methodName);
	return columnNames;
}

public List<VehicleConditionMapping> getVehiclMappedCondtion(String cType) throws BusinessException,TechnicalException{
	final String methodName = "getVehiclMappedCondtion";
	LOGGER.enter(CLASSNAME, methodName);
	List<VehicleConditionMapping> vcList = null;
	vcList = local.getDefinitionService().getVehicleMappedCondition(cType);
	LOGGER.exit(CLASSNAME, methodName);
	return vcList;
}
public void saveVehicleMappedCondition(List<VehicleConditionMapping> vList,String cType) throws BusinessException, TechnicalException{	
	final String methodName = "saveVehicleMappedCondition";
	LOGGER.enter(CLASSNAME, methodName);
	List<ConditionDefinition> condList = null;		
	try {
		local.getDefinitionService().saveVehicleMappedCondition(vList,cType);
	} catch (TechnicalException te) {
		throw new TechnicalException("",te.getMessageKey());
	}
	LOGGER.exit(CLASSNAME, methodName);
		
}
//LDRSP Bonus Start
		/**
		 * @param ldrshipbnsdtls
		 * @return
		 */
		public String getLeadershipBonusDetails(LeadershipBonusDetails ldrshipbnsdtls) throws BusinessException,TechnicalException {
			final String methodName = "getLeadershipBonusDetails";
			LOGGER.enter(CLASSNAME, methodName);
			try {
				local.getDefinitionService().getLdrshipBonusDefDetails(ldrshipbnsdtls);

			} catch (TechnicalException te) {
				throw new TechnicalException("",te.getMessageKey());
			}
			LOGGER.exit(CLASSNAME, methodName);
			return "ldrShipBonusView";

		}

		/**
		 * @param ldrshipbnsdtls
		 * @return
		 */
		public String saveLeadershipBonusDetails(LeadershipBonusDetails ldrshipbnsdtls) throws BusinessException,TechnicalException {
			final String methodName = "saveLeadershipBonusDetails";
			LOGGER.enter(CLASSNAME, methodName);
			List<String> appVehicle = new ArrayList<String>();
			try {
				if (ldrshipbnsdtls.getLdrshipAppVehs().size() > 0) {
					int appVeh = ldrshipbnsdtls.getLdrshipAppVehs().size();
					for (int i = 0; i < appVeh; i++) {
						String appCode = (String) ldrshipbnsdtls.getLdrshipAppVehs().get(i);
						appVehicle.add(appCode);
					}
					ldrshipbnsdtls.setLdrshipAppVeh(appVehicle);
				}
				local.getDefinitionService().saveLdrshipBonusDef(ldrshipbnsdtls);
			} catch (TechnicalException te) {
				throw new TechnicalException("",te.getMessageKey());
			}
			LOGGER.exit(CLASSNAME, methodName);
			return "ldrShipBonusView";
		}

		/**
		 * @param ldrshipbnsdtls
		 * @return
		 */
		public String ldrshipBonusDtlsFYRChange(LeadershipBonusDetails ldrshipbnsdtls) throws BusinessException,TechnicalException {
			final String methodName = "ldrshipBonusDtlsFYRChange";
			LOGGER.enter(CLASSNAME, methodName);
			List<String> appVehicle = new ArrayList<String>();
			try {
				if(ldrshipbnsdtls.getLdrshipAppVehs().size() > 0) {
					int appVeh = ldrshipbnsdtls.getLdrshipAppVehs().size();
					for (int i = 0; i < appVeh ;i++) {
						String appCode =  (String) ldrshipbnsdtls.getLdrshipAppVehs().get(i);
						appVehicle.add(appCode);
					}
					ldrshipbnsdtls.setLdrshipAppVeh(appVehicle);
				}
				local.getDefinitionService().ldrshipBonusDtlsFYRChange(ldrshipbnsdtls);
			} catch (TechnicalException te) {
				throw new TechnicalException("",te.getMessageKey());
			}
			LOGGER.exit(CLASSNAME, methodName);
			return "ldrShipBonusView";
		}

		/**
		 * @return
		 */
		public List<LeadershipBonusDetails> getLdrshipBonusList(LeadershipBonusDetails ldrshipbnsdtls) throws BusinessException,TechnicalException {
			final String methodName = "getLdrshipBonusList";
			LOGGER.enter(CLASSNAME, methodName);
			List<LeadershipBonusDetails> ldrshipBnsList = null;
			try {
				ldrshipBnsList = new ArrayList<LeadershipBonusDetails>();
				ldrshipBnsList = local.getDefinitionService().getLdrshipBonusList();
			} catch (TechnicalException te) {
				throw new TechnicalException("",te.getMessageKey());
			}
			LOGGER.exit(CLASSNAME, methodName);
			return ldrshipBnsList;
		}

		/**
		 * @param procDate
		 * @return
		 */
		public boolean validateProcessDate(String procDate) throws BusinessException,TechnicalException {
			final String methodName = "validateProcessDate";
			LOGGER.enter(CLASSNAME, methodName);
			boolean isValidProcDate = false;
			try {
				isValidProcDate = local.getDefinitionService().validateProcessDate(procDate);
			} catch (TechnicalException te) {
				throw new TechnicalException("",te.getMessageKey());
			}
			LOGGER.exit(CLASSNAME, methodName);
			return isValidProcDate;
		}
		/**
		 * @param acrlstartdate
		 * @param acrlenddate
		 * @return
		 */
		public double validateUnearnedAmount(String acrlstartDate,String acrlendDate) throws BusinessException,TechnicalException  {
			final String methodName = "validateUnearnedAmount";
			LOGGER.enter(CLASSNAME, methodName);
			double unEarnedAmount = 0.00;
			try{
			unEarnedAmount = local.getDefinitionService().validateUnearnedAmount(acrlstartDate,acrlendDate);
			} catch (TechnicalException te) {
				throw new TechnicalException("",te.getMessageKey());
			}
			LOGGER.exit(CLASSNAME, methodName);
			return unEarnedAmount;
		}
		
	// LDRSP Bonus End
		// AccountID Mapping Start
		public Map<String,Object> getAccountIDMaping() throws BusinessException,TechnicalException{
			Map<String,Object> vcMap = null;
			try {
			vcMap = local.getDefinitionService().getAccountIDMaping();
			}
			 catch (TechnicalException te) {
					throw new TechnicalException("",te.getMessageKey());
				}
			return vcMap;
		}
		public void saveAccountIDMapping(LdrspAccountIDMappingForm ldrspAccIDMapForm,String userId) throws BusinessException, TechnicalException{	
			List<String> acctList = ldrspAccIDMapForm.getPcAccountIdList();
			List<Integer> pgmIdList = ldrspAccIDMapForm.getPcLdrspBnsPgmIdList();
			List<String> status = ldrspAccIDMapForm.getPcStatusList();
			List<String> indicator = ldrspAccIDMapForm.getPcIndicatorList();
			List<AccountMapping> acctMapList = new ArrayList<AccountMapping>();
			AccountMapping accountMapping = null;
			try {
				int count = 0;
				for (String acct : acctList) {
					accountMapping = new AccountMapping();
					accountMapping.setAccountId(acct);
					accountMapping.setIdpgm(pgmIdList.get(count));
					accountMapping.setIndpgm(indicator.get(count));
					accountMapping.setStatus(status.get(count));
					accountMapping.setVehType(IWebConstants.PCAR);
					acctMapList.add(accountMapping);
					count++;
				}
				count = 0;
				acctList = ldrspAccIDMapForm.getSmartAccountIdList();
				pgmIdList = ldrspAccIDMapForm.getSmartLdrspBnsPgmIdList();
				status = ldrspAccIDMapForm.getSmartStatusList();
				indicator = ldrspAccIDMapForm.getSmartIndicatorList();
				for (String acclstS : acctList) {
					accountMapping = new AccountMapping();
					accountMapping.setAccountId(acclstS);
					accountMapping.setIdpgm(pgmIdList.get(count));
					accountMapping.setIndpgm(indicator.get(count));
					accountMapping.setStatus(status.get(count));
					accountMapping.setVehType(IWebConstants.SMART);
					acctMapList.add(accountMapping);
					count++;
				}
				count = 0;
				acctList = ldrspAccIDMapForm.getVanAccountIdList();
				pgmIdList = ldrspAccIDMapForm.getVanLdrspBnsPgmIdList();
				status = ldrspAccIDMapForm.getVanStatusList();
				indicator = ldrspAccIDMapForm.getVanIndicatorList();
				for (String acclstV : acctList) {
					accountMapping = new AccountMapping();
					accountMapping.setAccountId(acclstV);
					accountMapping.setIdpgm(pgmIdList.get(count));
					accountMapping.setIndpgm(indicator.get(count));
					accountMapping.setStatus(status.get(count));
					accountMapping.setVehType(IWebConstants.VAN);
					acctMapList.add(accountMapping);
					count++;
				}
				count = 0;
				acctList = ldrspAccIDMapForm.getFtlAccountIdList();
				pgmIdList = ldrspAccIDMapForm.getFtlLdrspBnsPgmIdList();
				status = ldrspAccIDMapForm.getFtlStatusList();
				indicator = ldrspAccIDMapForm.getFtlIndicatorList();
				for (String acclstF : acctList) {
					accountMapping = new AccountMapping();
					accountMapping.setAccountId(acclstF);
					accountMapping.setIdpgm(pgmIdList.get(count));
					accountMapping.setIndpgm(indicator.get(count));
					accountMapping.setStatus(status.get(count));
					accountMapping.setVehType(IWebConstants.FTL);
					acctMapList.add(accountMapping);
					count++;
				}

				local.getDefinitionService().saveAccountIDMapping(acctMapList, userId);
				
			} catch (TechnicalException te) {
				throw new TechnicalException("", te.getMessageKey());
			}

		}
		//AccountID Mapping End
	
	public RtlMonthDefinition createRetailMonthEntry(RtlMonthDefinition rtlMonthDef)throws BusinessException,TechnicalException
	{
		int idRtlDte=0;
		try {
			
			idRtlDte = local.getDefinitionService().createRetailMonthEntry(rtlMonthDef);
			
			if (rtlMonthDef.getId() <=0 &&  idRtlDte > 0 )
			{
				rtlMonthDef.setId(idRtlDte);
			}
		
		} catch (TechnicalException te) {
			throw new TechnicalException("",te.getMessageKey());
		}
		return rtlMonthDef;
	}
	
	public List<ProcessInputFile> getProcInputFileDetails()throws BusinessException,TechnicalException
	{
		List<ProcessInputFile> procInputFile=null;		
		try {			
			procInputFile =  local.getDefinitionService().getProcInputFileDetails();			
		} catch (TechnicalException te) {
			throw new TechnicalException("",te.getMessageKey());
		}
		return procInputFile;				
	}

	public RtlMonthDefinition getStartDate()throws BusinessException,TechnicalException
	{ 
		RtlMonthDefinition rtlMonthDef = null;;		
		try {
			rtlMonthDef=local.getDefinitionService().getStartDate();			
		} catch (TechnicalException te) {
			throw new TechnicalException("",te.getMessageKey());
		}
		return rtlMonthDef;
	}
	
	public List<GeneratePaymentFile> genPayFile()throws BusinessException,TechnicalException
	{
		List<GeneratePaymentFile> genPaymentFile=null;		
		try {			
			genPaymentFile =  local.getDefinitionService().genPayFile();
		} catch (TechnicalException te) {
			throw new TechnicalException("",te.getMessageKey());
		}
		return genPaymentFile;				
	}
	
	public List<GenerateReportFile> genReportFile()throws BusinessException,TechnicalException
	{
		List<GenerateReportFile> genReportFile=null;
		try {			
			genReportFile =  local.getDefinitionService().genReportFile();			
		} catch (TechnicalException te) {
			throw new TechnicalException("",te.getMessageKey());
		}
		return genReportFile;				
	}	
	public List<ProcessBonus> procBonusProc()throws BusinessException,TechnicalException
	{
		List<ProcessBonus> ProcessBonus=null;		
		try {			
			ProcessBonus =  local.getDefinitionService().procBonusProc();
		} catch (TechnicalException te) {
			throw new TechnicalException("",te.getMessageKey());
		}
		return ProcessBonus;
	}

public List<ProcessLdrBonus> processLdrBonus()throws BusinessException,TechnicalException
{
	List<ProcessLdrBonus> processLdrBonus=null;
	try {
			processLdrBonus =  local.getDefinitionService().processLdrBonus();		
	} catch (TechnicalException te) {
		throw new TechnicalException("",te.getMessageKey());
	}
	return processLdrBonus;			
}

public void  saveReportQueryDef(ReportQuery reportQuery) throws BusinessException,TechnicalException{
	
	try {
			local.getDefinitionService().saveReportQueryDef(reportQuery);		
	} catch (TechnicalException te) {
		throw new TechnicalException("",te.getMessageKey());
	}
	
}

public List<ReportQuery> getReportQueryList() throws BusinessException,TechnicalException
{
	List<ReportQuery> lst=null;
	try {
		lst=local.getDefinitionService().getReportQueryList();		
} catch (TechnicalException te) {
	throw new TechnicalException("",te.getMessageKey());
}
	return lst;
}
/*public ReportQuery getReportQueryID()
{ 
	ReportQuery reportQuery=null;
	try { 
		reportQuery= local.getDefinitionService().getReportQueryID();
		
	} catch (Exception e) {
		// TODO Auto-generated catch  
		e.printStackTrace();
	}
	return reportQuery;
}*/

public ReportQuery getReportQueryEdit(String rId) throws BusinessException,TechnicalException
{
	ReportQuery reportQuery=null;
	try {
		reportQuery =  local.getDefinitionService().getReportQueryEdit(rId);		
} catch (TechnicalException te) {
	throw new TechnicalException("",te.getMessageKey());
}
return reportQuery;
	
}
//Nikhil changes start
public ReportDefinitionBean insertReportDefinition(ReportDefinitionBean rDef, List<java.sql.Date> aSchdDatesList) throws BusinessException,TechnicalException{
	final String methodName = "insertReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
		rDef = local.getDefinitionService().createReportDefinition(rDef, aSchdDatesList);
	LOGGER.exit(CLASSNAME, methodName);
	return rDef;
}

public List<ReportDefinitionBean> getReportDefinitionList() throws BusinessException,TechnicalException{
	final String methodName = "getReportDefinitionList";
	LOGGER.enter(CLASSNAME, methodName);
	List<ReportDefinitionBean> reportDefList = null;
	reportDefList = local.getDefinitionService().getReportDefinitionList();
	LOGGER.exit(CLASSNAME, methodName);
	return reportDefList;
}

public ReportDefinitionBean getEditReportDefinition(String reportDefId) throws BusinessException,TechnicalException{
	final String methodName = "getEditReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	ReportDefinitionBean reportDef = null;
	reportDef = local.getDefinitionService().getEditReportDefinition(reportDefId);
	LOGGER.exit(CLASSNAME, methodName);
	return reportDef;
}

/*public ReportDefinitionBean retrieveDefaultReportDefinition(ReportDefinitionBean reportDefBean) throws BusinessException,TechnicalException{
	final String methodName = "retrieveDefaultReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
		reportDefBean = local.getDefinitionService().retrieveDefaultReportDefinition(reportDefBean);
	LOGGER.exit(CLASSNAME, methodName);
	return reportDefBean;
}*/

public List<ProcessCalDefBean> getCurrentMonthProcess() throws BusinessException,TechnicalException{
	final String methodName = "viewVehicleConditions";
	LOGGER.enter(CLASSNAME, methodName);
	List<ProcessCalDefBean> processCalView = null;
	try {		
		processCalView = local.getDefinitionService().getCurrentMonthProcess();
	}catch (TechnicalException te) {
		throw new TechnicalException("",te.getMessageKey());
	} 
	LOGGER.exit(CLASSNAME, methodName);
	return processCalView;
}

public FileProcessDefBean getFileProcessDefination(int processId) throws BusinessException,TechnicalException{
	final String methodName = "viewVehicleConditions";
	LOGGER.enter(CLASSNAME, methodName);
	FileProcessDefBean fileProcessDefBean = null;
	try {
		fileProcessDefBean = local.getDefinitionService().getFileProcessDefination(processId);
	}  catch (TechnicalException te) {
		throw new TechnicalException("",te.getMessageKey());
	} 
	LOGGER.exit(CLASSNAME, methodName);
	return fileProcessDefBean;
}

public int updateFileProcessDefinition(FileProcessDefBean fileProcessDefBean)throws BusinessException,TechnicalException {
	final String methodName = "viewVehicleConditions";
	int processId =0;
	LOGGER.enter(CLASSNAME, methodName);
	processId = local.getDefinitionService().updateFileProcessDefinition(fileProcessDefBean);
	LOGGER.exit(CLASSNAME, methodName);
	return processId;
}

public int insertReportContentDefinition(ReportContentDefinitionBean reportContentDefBean)throws BusinessException,TechnicalException {
	final String methodName = "insertReportContentDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	int pId = 0;
		pId = local.getDefinitionService().createReportContentDefinition(reportContentDefBean);
	LOGGER.exit(CLASSNAME, methodName);
	return pId;
}
//Nikhil changes end

public List<ReportContentDefinitionBean> getReportContentList() throws BusinessException,TechnicalException{
	final String methodName = "getReportContentList";
	LOGGER.enter(CLASSNAME, methodName);
	List<ReportContentDefinitionBean> rptCntList = null;
	rptCntList = local.getDefinitionService().getReportContentList();
	LOGGER.exit(CLASSNAME, methodName);
	return rptCntList;
}

public ReportContentDefinitionBean getEditReportContent(String rptCntDefId) throws BusinessException,TechnicalException{
	final String methodName = "getEditReportContent";
	LOGGER.enter(CLASSNAME, methodName);
	ReportContentDefinitionBean reportContentDefBean = null;
	reportContentDefBean = local.getDefinitionService().getEditReportContent(rptCntDefId);
	LOGGER.exit(CLASSNAME, methodName);
	return reportContentDefBean;
}
public String getReportDefStatus(ReportQuery reportQuery) throws BusinessException, TechnicalException
{
	String str="";
	try {
		str =  local.getDefinitionService().getReportDefStatus(reportQuery);		
} catch (TechnicalException te) {
	throw new TechnicalException("",te.getMessageKey());
	
}
	return str;
}

public FileDefinitionBean fetchFileDefinition(int processID) throws TechnicalException {
	FileDefinitionBean fileDefinitionBean = null;
	try {
		fileDefinitionBean = local.getDefinitionService().fetchFileDefinition (processID);
} catch (TechnicalException te) {
	throw new TechnicalException("",te.getMessageKey());
	
}
	return fileDefinitionBean;
}

public void insertIntoProcessLog (FileProcessLogMessages message) throws TechnicalException {
	final String methodName = "insertIntoProcessLog";
	LOGGER.enter(CLASSNAME, methodName);
	local.getDefinitionService().insertIntoProcessLog(message);
	LOGGER.exit(CLASSNAME, methodName);
}

public void reProcessingVistaFile(FileDefinitionBean definitionBean, int processId, String message, String processType, String userId, boolean isLdrShpBonus) throws TechnicalException {
	final String methodName = "reProcessingVistaFile";
	LOGGER.enter(CLASSNAME, methodName);
	local.getDefinitionService().reProcessingVistaFile(definitionBean, processId, message, processType, userId, isLdrShpBonus);
	LOGGER.exit(CLASSNAME, methodName);	
}
public void reProcessingKpiFile(FileDefinitionBean definitionBean, int processId, String message,String processType, String userId, boolean isLdrShpBonus) throws TechnicalException {
	final String methodName = "reProcessingKpiFile";
	LOGGER.enter(CLASSNAME, methodName);
	local.getDefinitionService().reProcessingKpiFile(definitionBean, processId, message, processType, userId, isLdrShpBonus);
	LOGGER.exit(CLASSNAME, methodName);
}

public void reProcessingAccrualFile(FileDefinitionBean definitionBean,int processId, String message,String processType, String userId, boolean isLdrShpBonus) throws TechnicalException {
	final String methodName = "reProcessingAccrualFile";
	LOGGER.enter(CLASSNAME, methodName);
	local.getDefinitionService().reProcessingAccrualFile(definitionBean, processId, message, processType, userId, isLdrShpBonus);
	LOGGER.exit(CLASSNAME, methodName);
	
}
public void performBonusReprocess(int processId, String userId,String processType, boolean ldrShpBonusProcess) throws TechnicalException {
	final String methodName = "performBonusReprocess";
	LOGGER.enter(CLASSNAME, methodName);
	local.getDefinitionService().performBonusReprocess(processId, userId, processType, ldrShpBonusProcess);
	LOGGER.exit(CLASSNAME, methodName);
}

public boolean validateCondition(ConditionDefinition conditionDef) throws TechnicalException {
	final String methodName = "validateCondition";
	LOGGER.enter(CLASSNAME, methodName);
	boolean isValidCondition = local.getDefinitionService().validateCondition(conditionDef);
	LOGGER.exit(CLASSNAME, methodName);
	return isValidCondition;
}

public boolean validateProgram(int programId, String programName, Date startDate) throws TechnicalException {
	final String methodName = "validateProgram";
	LOGGER.enter(CLASSNAME, methodName);
	boolean isValidProgram = local.getDefinitionService().validateProgram(programId, programName, startDate);
	LOGGER.exit(CLASSNAME, methodName);
	return isValidProgram;
}

public boolean checkPaymentProcesses(int processId) throws TechnicalException {
	final String methodName = "checkPaymentProcesses";
	LOGGER.enter(CLASSNAME, methodName);
	boolean isPaymentSuccess = local.getDefinitionService().checkPaymentProcesses(processId);
	LOGGER.exit(CLASSNAME, methodName);
	return isPaymentSuccess;
}

//Reschedule Changes Start
public int updateProcessDefinition(FileProcessDefBean fileProcessDefBean, int fileType, boolean bonusProcess, boolean ldrshpBonus)throws BusinessException,TechnicalException {
	final String methodName = "updateProcessDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	int processId = local.getDefinitionService().updateProcessDefinition(fileProcessDefBean, fileType, bonusProcess, ldrshpBonus);
	LOGGER.exit(CLASSNAME, methodName);
	return processId;
}

public RetailDate getRetailData(String dbMonth, String dbYear) throws TechnicalException {
	final String methodName = "getRetailData";
	LOGGER.enter(CLASSNAME, methodName);
	RetailDate rtlDate = new RetailDate();
	rtlDate = local.getDefinitionService().getRetailData(dbMonth, dbYear);
	LOGGER.exit(CLASSNAME, methodName);
	return rtlDate;

}

public boolean checkValidMonth(String month, String year)  throws BusinessException, TechnicalException{
	// TODO Auto-generated method stub
	final String methodName = "updateProcessesRetailDate";
	LOGGER.enter(CLASSNAME, methodName);
	boolean isValidMonth = local.getDefinitionService().checkValidMonth(month, year);
	LOGGER.exit(CLASSNAME, methodName);
	return isValidMonth;
}

public void updateProcessesRetailDate(Date oldRtlProcessingDate, Date newRtlProcessingDate,String currentStartDte,String newEndDte,int cYear,int cMonth, int nYear,String userID) throws BusinessException, TechnicalException {
	final String methodName = "updateProcessesRetailDate";
	LOGGER.enter(CLASSNAME, methodName);
	local.getDefinitionService().updateProcessesRetailDate(oldRtlProcessingDate, newRtlProcessingDate,currentStartDte,newEndDte,cYear,cMonth, nYear,userID);
	LOGGER.exit(CLASSNAME, methodName);
}

public List<AMGDealer> getDlrsInfoList(String dlrId) throws BusinessException,TechnicalException{
	LOGGER.enter(CLASSNAME, "getDlrsInfoList");
	List<AMGDealer> dlrList = new ArrayList<AMGDealer>();
	dlrList = local.getDefinitionService().getDlrsInfoList(dlrId);
	LOGGER.exit(CLASSNAME,  "getDlrsInfoList");
	return dlrList;
}

public Boolean modifyAMGDealerInfo(AMGDealer amgDealer) throws BusinessException,TechnicalException{
	LOGGER.enter(CLASSNAME, "modifyAMGDealerInfo");
	Boolean modified = false;
	modified = local.getDefinitionService().modifyAMGDealerInfo(amgDealer);
	LOGGER.exit(CLASSNAME,  "modifyAMGDealerInfo");
	return modified;
}


public String getAMGDealerName(String dlrId) throws BusinessException,TechnicalException{
	LOGGER.enter(CLASSNAME, "getAMGDealerName");
	String dealerName = "";
	dealerName = local.getDefinitionService().getAMGDealerName(dlrId);
	LOGGER.exit(CLASSNAME,  "getAMGDealerName");
	return dealerName;
}
}
