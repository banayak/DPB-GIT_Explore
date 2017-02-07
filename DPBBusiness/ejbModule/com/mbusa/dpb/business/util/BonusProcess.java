/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: BonusProcess.java
 * Program Version			: 1.0
 * Program Description		:  This class is calculate bonus.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 11, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.business.util;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.business.view.DpbCommonBeanLocal;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.BonusInfo;
import com.mbusa.dpb.common.domain.ConditionDefinition;
import com.mbusa.dpb.common.domain.DealerInfo;
import com.mbusa.dpb.common.domain.FileProcessLogMessages;
import com.mbusa.dpb.common.domain.InvalidReason;
import com.mbusa.dpb.common.domain.ProcessTypes;
import com.mbusa.dpb.common.domain.ProgramDefinition;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.PersistanceException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.DPBCommonHelper;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.common.util.MailAttachment;
import com.mbusa.dpb.common.util.SendMailDTO;

public class BonusProcess{

	private Map<String, List<ProgramDefinition>> masterVehicleProgramMap = null;
	private static DPBLog LOGGER = DPBLog.getInstance(BonusProcess.class);	
	static final private String CLASSNAME = BonusProcess.class.getName();
	private LocalServiceFactory local = new LocalServiceFactory();
	//public static final String userId = "System";
	private DPBCommonHelper commonHelper = new DPBCommonHelper();
	SendMailDTO sendMailDTO = new SendMailDTO();
	static PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
	
	/**
	 * This method get call from scheduler as well as from application.
	 * Based on trigger flow will get executed identify type process.
	 * For Manual, only once process should be executed.
	 * @param pId
	 * @param actualDate
	 * @param procType
	 * @param trigger
	 * @param userId
	 * @return
	 * @throws BusinessException 
	 * @throws TechnicalException 
	 */
	public boolean startBonusProcess(Integer pId, Date actualDate,  String procType, String trigger,String userId,String callType ) {
		final String methodName = "startBonusProcess";
		LOGGER.enter(CLASSNAME, methodName);
		boolean procStatus = false;
		List<BonusInfo> bonusInfo = new ArrayList<BonusInfo>();
		List<InvalidReason> invalidReason = new ArrayList<InvalidReason>();
		DpbCommonBeanLocal commBean = null; 		
		boolean ldrsp = false;	
		trigger = IConstants.TRIGGER_SYSTEM_INITIATION_DESC;
		try{
			commBean = local.getDpbCommonService();
			boolean isTrConsider = false;
			//startProgramProcess(bonusInfo,pId,procType,userId,callType,isTrConsider);			

			if(IConstants.FIXED_BONUS_PROCESS.equalsIgnoreCase(procType)){
				isTrConsider = startDealerProgramProcess(bonusInfo,pId,procType,userId,callType,invalidReason,actualDate);
			}else if(IConstants.COMMISSION_BONUS_PROCESS.equalsIgnoreCase(procType)){
				isTrConsider = startDealerProgramProcess(bonusInfo,pId,procType,userId,callType,invalidReason,actualDate);
			}else if(IConstants.VARIABLE_BONUS_PROCESS.equalsIgnoreCase(procType)){
				isTrConsider = 	startDealerProgramProcess(bonusInfo,pId,procType,userId,callType,invalidReason,actualDate);
			}else if(IConstants.LEADERSHIP_BONUS_PROCESS.equalsIgnoreCase(procType)){
				isTrConsider = 	startDealerProgramLdrspProcess(bonusInfo,pId,procType,userId,callType,invalidReason);
				ldrsp = true;
			}else if(IConstants.ADJUSTMENT_PROCESS.equalsIgnoreCase(procType)){
				isTrConsider = startAdjustmentProcess(bonusInfo,pId,actualDate,userId,invalidReason);
			}
			procStatus = true;				
			if(procStatus){
				if(bonusInfo!= null && !bonusInfo.isEmpty()){
					updateBonusCalculation(bonusInfo,pId,userId,isTrConsider,ldrsp);	
				}else{
					if(isTrConsider){
						Integer tProcId = PROPERTY_MANAGER.getPropertyAsInteger("termination.bonus.process.id");
						commBean.insertIntoProcessTerminateCancelationLog(commonHelper.getProcessLog(tProcId,
								"Termination Process completed.",IConstants.PROC_STATUS_SUCCESS,userId),
								actualDate);
					}
					updateProcessLogs(pId,"Bonus Process Completed. Respective person will receive email notification soon..",IConstants.PROC_STATUS_SUCCESS,userId);	
				}
				sendBonusNotificationMail(IConstants.PROC_STATUS_SUCCESS,"",pId,invalidReason,procType,actualDate,trigger);
			}else{
				sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"",pId,invalidReason,procType,actualDate,trigger);
			}
		}catch (BusinessException e) {
			procStatus = false;
			LOGGER.error("Business Exception occured while doing Bonus calculation.", e);
			String key = "DPB.BNS.KPI.003";
			if(key.equalsIgnoreCase(e.getMessageKey())){
			sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,e.getMessage(),pId,invalidReason,procType,actualDate,trigger);		
			}else{
				sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"",pId,invalidReason,procType,actualDate,trigger);
			}
			try {
				updateProcessLogs(pId,"Auto Trigger bonus calculation fail. Email notification sent.",IConstants.PROC_STATUS_FAILURE,userId);
			} catch (TechnicalException e1) {
				LOGGER.error("Auto Trigger Business Exception occured while upating process logs.", e1);
			}
		}catch (TechnicalException e) {
			procStatus = false;
			sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"",pId,invalidReason,procType,actualDate,trigger);
			LOGGER.error("Auto Trigger Technical Exception occured while doing Bonus calculation.", e);
			try {
				updateProcessLogs(pId,"Auto Trigger bonus calculation fail. Email notification sent.",IConstants.PROC_STATUS_FAILURE,userId);
			} catch (TechnicalException e1) {
				LOGGER.error("Auto Trigger Technical Exception occured while upating process logs.", e1);
			}		
		}catch (PersistanceException  pe) {
			procStatus = false;
			LOGGER.error("Auto Trigger Persistance Exception occured while doing Bonus calculation.", pe);
			sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"",pId,invalidReason,procType,actualDate,trigger);
			try {
				updateProcessLogs(pId,"Auto Trigger bonus calculation fail. Email notification sent.",IConstants.PROC_STATUS_FAILURE,userId);
			} catch (TechnicalException e1) {
				LOGGER.error("Auto Trigger Persistance Exception occured while upating process logs.", e1);
			}
		}catch (Exception  e) {
			procStatus = false;
			LOGGER.error("Auto Trigger Exception occured while doing Bonus calculation.", e);
			sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"",pId,invalidReason,procType,actualDate,trigger);
			try {
				updateProcessLogs(pId,"Auto Trigger bonus calculation fail. Email notification sent.",IConstants.PROC_STATUS_FAILURE,userId);
			} catch (TechnicalException e1) {
				LOGGER.error("Auto Trigger Exception occured while upating process logs.", e1);
			}
		}
		return procStatus;
	}
	/**
	 * 
	 * @param pId
	 * @param actualDate
	 * @param procType
	 * @param trigger
	 * @param userId
	 * @param callType
	 * @return
	 */
	public boolean startManualBonusProcess(Integer pId,  String procType, String trigger,String userId,String callType ) {
		final String methodName = "startManulBonusProcess";
		LOGGER.enter(CLASSNAME, methodName);
		boolean procStatus = false;
		List<BonusInfo> bonusInfo = new ArrayList<BonusInfo>();
		List<InvalidReason> invalidReason = new ArrayList<InvalidReason>(); 
		DpbCommonBeanLocal commBean = null; 	
		boolean isTrConsider = false;
		java.sql.Date aDate =  null;	
		trigger = IConstants.TRIGGER_USER_INITIATION_DESC;
		boolean ldrsp = false;	
		try{
			commBean = local.getDpbCommonService();
			if(aDate == null){
				aDate = commBean.getActualProcessDate(pId);
	    	}
			if(IConstants.FIXED_BONUS_PROCESS.equalsIgnoreCase(procType)){
				isTrConsider = startDealerProgramProcess(bonusInfo,pId,procType,userId,callType,invalidReason,aDate);
			}else if(IConstants.COMMISSION_BONUS_PROCESS.equalsIgnoreCase(procType)){
				isTrConsider = startDealerProgramProcess(bonusInfo,pId,procType,userId,callType,invalidReason,aDate);
			}else if(IConstants.VARIABLE_BONUS_PROCESS.equalsIgnoreCase(procType)){
				isTrConsider = startDealerProgramProcess(bonusInfo,pId,procType,userId,callType,invalidReason,aDate);
			}else if(IConstants.LEADERSHIP_BONUS_PROCESS.equalsIgnoreCase(procType)){
				isTrConsider = 	startDealerProgramLdrspProcess(bonusInfo,pId,procType,userId,callType,invalidReason);
				ldrsp = true;
			}else if(IConstants.ADJUSTMENT_PROCESS.equalsIgnoreCase(procType)){
				isTrConsider = startAdjustmentProcess(bonusInfo,pId,aDate,userId,invalidReason);
			}
			procStatus = true;			
			if(procStatus){
				if(bonusInfo!= null && !bonusInfo.isEmpty()){
					updateBonusCalculation(bonusInfo,pId,userId,isTrConsider,ldrsp);
				}else{
					if(isTrConsider){
						Integer tProcId = PROPERTY_MANAGER.getPropertyAsInteger("termination.bonus.process.id");
						commBean.insertIntoProcessTerminateCancelationLog(commonHelper.getProcessLog(tProcId,
								"Termination Process completed.",IConstants.PROC_STATUS_SUCCESS,userId),
								aDate);
					}
					updateProcessLogs(pId,"Bonus Process Completed. Respective person will receive email notification soon..",IConstants.PROC_STATUS_SUCCESS,userId);	
				}
				sendBonusNotificationMail(IConstants.PROC_STATUS_SUCCESS,"",pId,invalidReason,procType,aDate,trigger);
			}else{
				sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"",pId,invalidReason,procType,aDate,trigger);
			}
		}catch (BusinessException e) {
			procStatus = false;
			LOGGER.error("Manual - Business Exception occured while doing Bonus calculation.", e);			
			String key = "DPB.BNS.KPI.003";
			if(key.equalsIgnoreCase(e.getMessageKey())){
				sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,e.getMessage(),pId,invalidReason,procType,aDate,trigger);		
			}else{
				sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"",pId,invalidReason,procType,aDate,trigger);
			}
			try {
				updateProcessLogs(pId,"Manual trigger bonus calculation fail. Email notification sent.",IConstants.PROC_STATUS_FAILURE,userId);
			} catch (TechnicalException e1) {
				LOGGER.error("Manual - TechnicalException Exception occured while upating process logs.", e1);
			}
		}catch(TechnicalException e) {
			procStatus = false;
			LOGGER.error("Manual Technical Exception occured while doing Bonus calculation...", e);
			sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"",pId,invalidReason,procType,aDate,trigger);	
			try {
				updateProcessLogs(pId,"Manual trigger bonus calculation fail. Email notification sent.",IConstants.PROC_STATUS_FAILURE,userId);
			} catch (TechnicalException e1) {
				LOGGER.error("Manual TechnicalException Exception occured while upating process logs.", e1);
			}
		}catch(PersistanceException  pe) {
			procStatus = false;
			LOGGER.error("Manual Persistance Exception occured while doing Bonus calculation.", pe);
			sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"",pId,invalidReason,procType,aDate,trigger);
			try {
				updateProcessLogs(pId,"Manual trigger  bonus calculation fail. Email notification sent.",IConstants.PROC_STATUS_FAILURE,userId);
			} catch (TechnicalException e1) {
				LOGGER.error("Manual TechnicalException Exception occured while upating process logs.", e1);
			}
		}catch (Exception  e) {
			procStatus = false;
			LOGGER.error("Manual Trigger Exception occured while doing Bonus calculation.", e);
			sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"",pId,invalidReason,procType,aDate,trigger);
			try {
				updateProcessLogs(pId,"Manual Trigger bonus calculation fail. Email notification sent.",IConstants.PROC_STATUS_FAILURE,userId);
			} catch (TechnicalException e1) {
				LOGGER.error("Manual Trigger Exception occured while upating process logs.", e1);
			}
		}
		return procStatus;
	}
	public Date getActualProcessDate(Integer pId) throws BusinessException,TechnicalException
	{
		Date aProcDate =  null;
		try{
			DpbCommonBeanLocal commonBean = local.getDpbCommonService();
			aProcDate =  commonBean.getActualProcessDate(pId);
		}catch (TechnicalException te) {
			new TechnicalException("Technical Exception occured in dealer terminate.", te);
		}
		return aProcDate;
	}
	/**
	 * 
	 * @param pStatus
	 */
	public void sendBonusNotificationMail(String pStatus, String msg,Integer pId,List<InvalidReason> failList,
			String pType, java.sql.Date procActlDate, String trigger ) {
	    final String methodName = "sendProcessNotificationMail";
        LOGGER.enter(CLASSNAME, methodName);
         try{  
        	 String pDesc = MasterDataLookup.getInstance().getProcessDesc(pType);
	          SendMailDTO sendMailDTO = new SendMailDTO();
	          sendMailDTO.setFrom(PROPERTY_MANAGER.getPropertyAsString("bonus.process.email.fromMail"));
	          sendMailDTO.addTORecipient(PROPERTY_MANAGER.getPropertyAsString("bonus.process.email.toRecipient"));
	          if(IConstants.PROC_STATUS_SUCCESS.equalsIgnoreCase(pStatus)){
	        	  String pSub = PropertyManager.getPropertyManager().getPropertyAsString("bonus.process.success.email.subject");
	        	  String subject = MessageFormat.format(pSub, new Object[]{trigger});
		          sendMailDTO.setSubject(subject);
		          String pCnt = PropertyManager.getPropertyManager().getPropertyAsString("bonus.process.success.email.content");
		          String cnt = MessageFormat.format(pCnt,new Object[]{pDesc,DateCalenderUtil.getDateInGivenFormat(procActlDate,IConstants.DATE_FORMAT_MMDDYYYY_WITH_SLASH),pId});
		          sendMailDTO.setContent(cnt);
	          }else{		        
		          String subject = MessageFormat.format(PROPERTY_MANAGER.getPropertyAsString("bonus.process.fail.email.subject"),new Object[]{trigger});
		          sendMailDTO.setSubject(subject);
		          if(!"".equalsIgnoreCase(msg)){
		        	  sendMailDTO.setContent(msg);
		          }else{
		        	  String cnt = MessageFormat.format(PropertyManager.getPropertyManager().getPropertyAsString("bonus.process.fail.email.content"),
		        			  new Object[]{pDesc,DateCalenderUtil.getDateInGivenFormat(procActlDate,IConstants.DATE_FORMAT_MMDDYYYY_WITH_SLASH),pId});
		        	  sendMailDTO.setContent(cnt);  
		          }
	          }
	          if(failList!= null && failList.isEmpty()){
	        	  DPBCommonHelper.sendEmail(sendMailDTO);	
	          }else{
	        	  MailAttachment attachment = null;
	        	  MailAttachment[] attachArray = new MailAttachment[1];
	        	  String  errorPath = PropertyManager.getPropertyManager().getPropertyAsString("dpb.common.error.path");
				  byte[] bytes = DPBCommonHelper.writeFailedRecords(failList, errorPath + this.getErrorFailureListFileName(pType, pId));
				  attachment = new MailAttachment(this.getErrorFailureListFileName(pType, pId),"text/plain", bytes);
				  attachArray[0] = attachment;
				  sendMailDTO.setAttachments(attachArray);					 
				  DPBCommonHelper.sendMailWithAttachment(sendMailDTO);
	          }
          } catch (Exception e) {
        	LOGGER.error("Error Occuered While Sending Mail");
          }
         LOGGER.exit(CLASSNAME, methodName);
	}
	//VBIgnoreList
	public String getErrorFailureListFileName(String procType, Integer pId){
		 //IConstants.ERROR_TEXT +IConstants.UNDER_SCORE +
		return IConstants.IGNORE_TEXT + IConstants.UNDER_SCORE + 
			   IConstants.COMPANY_NAME + IConstants.UNDER_SCORE + 
			   IConstants.FILE_NAME + IConstants.DOT_STR + 
			   procType + IConstants.UNDER_SCORE + 
			   IConstants.BONUS_FILE_TYPE + IConstants.UNDER_SCORE + 
		       DateCalenderUtil.getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD) +
           	   IConstants.UNDER_SCORE + DateCalenderUtil.getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR +
           	   IConstants.FILE_EXT;
	}
	/**
	 * This method handle request for manual trigger and apply today available dealer,
	 * special program and leadership bonus program.
	 * @param bonusInfo
	 * @param processId
	 * @param processType
	 * @param user
	 * @return
	 * @throws BusinessException
	 * @throws TechnicalException
		public boolean startProgramProcess(List<BonusInfo> bonusInfo, Integer processId ,String processType,String user,String callType,boolean isTrConsider) throws BusinessException,TechnicalException{		
		final String methodName = "startProgramProcess";
		LOGGER.enter(CLASSNAME, methodName);		
		if(IConstants.LEADERSHIP_BONUS_PROCESS.equals(processType)){
			LOGGER.info(processType+" bonus Process:");
			startLdrshipProgramProcess(bonusInfo,processId,processType,user);
		}else{
			LOGGER.info(processType+" bonus Process:");
			startDealerProgramProcess(bonusInfo,processId,processType,user,callType,isTrConsider);
		}		
		return true;	
	}
	*/

	/**
	 * Apply today available dealer and special program.
	 * @param bonusInfo
	 * @param processId
	 * @param processType
	 * @param userId
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public boolean  startDealerProgramProcess(List<BonusInfo> bonusInfo, Integer processId,String processType,
												String userId,String callType,List<InvalidReason> invalidReason,
												java.sql.Date aDte) 
															throws BusinessException,TechnicalException{
		final String methodName = "startDealerProgramProcess";
		LOGGER.enter(CLASSNAME, methodName);
		String pDesc= MasterDataLookup.getInstance().getProcessDesc(processType);
		updateProcessLogs(processId,pDesc.trim()+" process has been started.",IConstants.PROC_STATUS_IN_PROCESS,userId);
		DpbCommonBeanLocal commonBean = null;		
		commonBean = local.getDpbCommonService();
		boolean isTRCompleted = true;
		if(aDte == null){
			aDte = commonBean.getActualProcessDate(processId);
    	}
		//boolean isTRCompleted = commonBean.isDealerTerminationCancelationProcLogs(aDte);
		//if(!isTRCompleted){
		//	startDealerTerminateProcess(bonusInfo,aDte,userId,processId,invalidReason);
			//startCancelationProcess(bonusInfo,userId,aDate,processId);
		//}
		masterVehicleProgramMap = commonBean.retrieveProcessDetails(processId,callType,processType);
		LOGGER.info("Vehicle Map has been fetch having size as :"+masterVehicleProgramMap.size());
		calculateBonusBasedOnProgram(bonusInfo,masterVehicleProgramMap,userId,processId,invalidReason);		 
		LOGGER.exit(CLASSNAME, methodName);
		return !isTRCompleted;
	}

	/**
	 * 
	 * @param bonusInfoList
	 * @param actualRtlDate
	 * @param uId
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public void startDealerTerminateProcess(List<BonusInfo> bonusInfoList,Date actualRtlDate,String uId,
													Integer parentId,List<InvalidReason> bnsFailReason)
																throws BusinessException,TechnicalException{	   	 
		final String methodName = "startDealerTerminateProcess";
		LOGGER.enter(CLASSNAME, methodName);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(actualRtlDate);		
		c1.add(Calendar.DATE, -1);
		// check terminate date as (actual date -1 )
		java.sql.Date dlrTerminateDate = new java.sql.Date(c1.getTime().getTime());		
		DpbCommonBeanLocal commonBean = null;     
        List<DealerInfo> tDealer =  null;        
        String schedulerType = IConstants.EMPTY_STR;
        try
        {
        	commonBean = local.getDpbCommonService(); 
        	commonBean.insertIntoProcessLog(commonHelper.getProcessLog(parentId,
												"Considering Dealer termination during this process.", 
					        					IConstants.PROC_STATUS_IN_PROCESS,uId));
        	tDealer =  commonBean.getTerminatedDealerList(dlrTerminateDate);        	
			if(tDealer!= null && !tDealer.isEmpty()){			
				LOGGER.info("No. of Dealer terminated: "+tDealer.size()+": on :"+dlrTerminateDate);
				Iterator<DealerInfo> dealer = tDealer.iterator();
				while(dealer != null && dealer.hasNext()){
					DealerInfo dInfo = dealer.next();					
					List<String> vehicleFranchise = commonBean.getDealerFranchises(dInfo.getDealerId());
					LOGGER.info("Dealer having franchise of :"+vehicleFranchise.size()+": vehicle type.");
					/*List<BonusInfo> bonusCalList = commonBean.getBonusDetailsOnPONumber(dInfo.getDealerId());
					if(bonusCalList!=null && !bonusCalList.isEmpty()){
						LOGGER.info("No. of old bonus records need to consider for termination:"+bonusCalList.size());
						Iterator<BonusInfo> bonuscalListIterator = bonusCalList.iterator();
						//List<String> calPoList = new ArrayList<String>(bonusCalList.size());
						while(bonuscalListIterator.hasNext()){					
							BonusInfo bonusCalInfo = bonuscalListIterator.next();
							double totalBonusPaid = bonusCalInfo.getBnsCalcAmt();
							double maxEligibleAmt = bonusCalInfo.getMaxBnsEligibleAmt();
							//calPoList.add("");//bonusCalInfo.getDpbUnblkVehId());
							if(maxEligibleAmt > totalBonusPaid){
								bonusCalInfo.setProcessId(bonusCalInfo.getProcessId());
								bonusCalInfo.setPgmId(bonusCalInfo.getPgmId());
								bonusCalInfo.setDpbUnblkVehId(bonusCalInfo.getDpbUnblkVehId());
								bonusCalInfo.setDlrId(bonusCalInfo.getDlrId());
								bonusCalInfo.setActualDate(actualRtlDate);
								bonusCalInfo.setAdjIndicator(bonusCalInfo.getAdjIndicator());
								bonusCalInfo.setBnsCalcAmt(maxEligibleAmt-totalBonusPaid);
								bonusCalInfo.setStatus(IConstants.BONUS_STATUS_TERMINATED_CALCULATED);
								bonusCalInfo.setUserId(uId);
								bonusCalInfo.setCreateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
								bonusCalInfo.setUpdateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
								LOGGER.info("Records consider during termination pId:"+bonusCalInfo.getProcessId()+
																	":PO:"+bonusCalInfo.getDpbUnblkVehId()+
																	":DLR:"+bonusCalInfo.getDlrId()+
																	":Day:"+bonusCalInfo.getActualDate()+
																	":Old Bns amount:"+totalBonusPaid+
																	":Max Bns:"+maxEligibleAmt+
																	":");
								bonusInfoList.add(bonusCalInfo);
							}
						}
					}
					
*/					Map<Integer,ProgramDefinition> pgmMap  = new HashMap<Integer,ProgramDefinition>();
					Map<Integer, ArrayList<String>> procVehMap  = new HashMap<Integer, ArrayList<String>>();
					ArrayList<String> vehList  = new ArrayList<String>();					
					// it will return you program list based on actual date.
					masterVehicleProgramMap = commonBean.retrieveDayVehicleProgramMap(actualRtlDate);
					if(masterVehicleProgramMap!= null && !masterVehicleProgramMap.isEmpty()){
						for (Object key : masterVehicleProgramMap.keySet()) {
							String vType = (String)key;	
							if(vehicleFranchise != null && vehicleFranchise.contains(vType)){
								List<ProgramDefinition> activeProgDefs   = masterVehicleProgramMap.get(vType);
								if(activeProgDefs!= null && activeProgDefs.size() > 0 ){
									for(Iterator<ProgramDefinition > activeProgDefsIter = activeProgDefs.iterator();
													activeProgDefsIter.hasNext(); ){						
										ProgramDefinition pgmDef = activeProgDefsIter.next();
										if(pgmDef!= null){
											if( pgmMap != null && !pgmMap.containsKey(pgmDef.getProcessId()))
											{
												vehList = new ArrayList<String>();
												//pgmMap.
												pgmMap.put(pgmDef.getProcessId(), pgmDef);	
												vehList.add(vType);
												procVehMap.put(pgmDef.getProcessId(), vehList);
											}else if(pgmMap != null && !pgmMap.isEmpty() && pgmMap.containsKey(pgmDef.getProcessId())){
												ArrayList<String> tempList  = procVehMap.get(pgmDef.getProcessId());
												tempList.add(vType);
												procVehMap.put(pgmDef.getProcessId(), tempList);									
											}
										}
									}
								
								}
							}
						}
						SpecialBonusProgram specialBonus = new SpecialBonusProgram();
						KpiCalculation kpiCalculation = new KpiCalculation();
						
					    java.sql.Date startDate = null;
				        java.sql.Date endDate = null;
				        boolean isFullBns = false;
				        boolean isTerminated = true;
						if(pgmMap!= null && !pgmMap.isEmpty()){							
								updateProcessLogs(parentId,"Considering todays available program for bonus calculation (dealer termination)", 
										IConstants.PROC_STATUS_IN_PROCESS,uId);
							boolean isPgmApplied = false;
							for (Object key : pgmMap.keySet()) {
								boolean isLogUpdate = false;
								ProgramDefinition pgmDef = pgmMap.get(key);
								ArrayList<String> processVehicleList = procVehMap.get(key);
								
								// check based on schedule fetch start date and end date.
								schedulerType = commonHelper.getBonusSchedulerType(pgmDef);
								///decideStartDateEndDateForProcess(schedulerType, startDate,endDate, pgmDef.getActualRtlDateId());
								startDate = decideStartDateForProcess(schedulerType, pgmDef.getActlProcDte());
								endDate  = decideEndDateForProcess(schedulerType, pgmDef.getActlProcDte());
								/*if(IConstants.CONSTANT_Y.equalsIgnoreCase(pgmDef.getSpecialProgram())){
									updateProcessLogs(parentId,"Program is Special Program.", 
											IConstants.PROC_STATUS_IN_PROCESS,uId);
								}else{
									updateProcessLogs(parentId,"Program is General Program.", 
											IConstants.PROC_STATUS_IN_PROCESS,uId);							
								}*/
								List<String> considerPoForTer =  new ArrayList<String>();
							
								List<VistaFileProcessing> vehiclesSoldLastDate = commonBean.getNonSettledVehicleDtlsForTerminatedDlr(dInfo.getDealerId(),startDate,endDate,processVehicleList);
								if(vehiclesSoldLastDate!=null && !vehiclesSoldLastDate.isEmpty()){
									boolean splCondSatisfied = false;
									for(Iterator<VistaFileProcessing> vehiclesSoldLastDateIter = vehiclesSoldLastDate.iterator(); vehiclesSoldLastDateIter.hasNext();)
									{
										VistaFileProcessing vistaFileProcessing = vehiclesSoldLastDateIter.next();
										considerPoForTer.add(vistaFileProcessing.getPoNo());
										if(IConstants.PROGRAM_TYPE_SPECIAL.equalsIgnoreCase(pgmDef.getSpecialProgram())){
											if(vistaFileProcessing!= null 
													&& vistaFileProcessing.getProgType()!= null 
														&& vistaFileProcessing.getProgType() > 0 )
											{
												splCondSatisfied = 	checkConditionsSatisfied(pgmDef, vistaFileProcessing);
												if(splCondSatisfied){
													//boolean isDPP = false;// Need to decide based on condition to give fixed as 750$ -  To do
													isPgmApplied  = true;
													/*updateProcessLogs(parentId,
								        					"Program "+pgmDef.getProgramName()+" is special Program.",IConstants.PROC_STATUS_IN_PROCESS,uId);*/
													specialBonus.applySpecialBonus(bonusInfoList,pgmDef, vistaFileProcessing,uId,isFullBns,isLogUpdate,commonBean,isTerminated,bnsFailReason,endDate);
													isLogUpdate = false;
												}
											}
										}else{
											if(vistaFileProcessing!= null && (vistaFileProcessing.getProgType()== null || vistaFileProcessing.getProgType() <=0 )){
												isPgmApplied  = true;
												/*updateProcessLogs(parentId,
							        					"Program Name "+pgmDef.getProgramName().trim()+" is normal dealer program", 
							        					IConstants.PROC_STATUS_IN_PROCESS,uId);*/
												kpiCalculation.applyGeneralPgm(bonusInfoList,pgmDef, vistaFileProcessing, dInfo.getDealerId(),endDate,uId,isFullBns,isLogUpdate,isTerminated,bnsFailReason, null, null);
												isLogUpdate = false;
											}
										}
									}
								}	
							}
						}//end of null check.
					}
				}
		    }else{
		    	LOGGER.info("No dealer has been terminated on:"+ actualRtlDate);
		    	/*Integer tProcId = PROPERTY_MANAGER.getPropertyAsInteger("termination.bonus.process.id");
		    	commonBean.insertIntoProcessTerminateCancelationLog(commonHelper.getProcessLog(tProcId,
						"No dealer found as terminated.",IConstants.PROC_STATUS_IN_PROCESS,uId),
						actualRtlDate);*/
		   }
		   /*if(bonusInfoList!= null && bonusInfoList.isEmpty()){
				LOGGER.info("Dealer termination completed. No records found:"+ actualRtlDate);
		    	Integer tProcId = PROPERTY_MANAGER.getPropertyAsInteger("termination.bonus.process.id");
		    	commonBean.insertIntoProcessTerminateCancelationLog(commonHelper.getProcessLog(tProcId,
						"No records found.",IConstants.PROC_STATUS_SUCCESS,uId),
						actualRtlDate);	
		   }*/
		   /*if(bonusInfoList!= null && !bonusInfoList.isEmpty()){
				LOGGER.info("Termination completed successfully."+ actualRtlDate);
		    	Integer tProcId = PROPERTY_MANAGER.getPropertyAsInteger("termination.bonus.process.id");
		    	commonBean.insertIntoProcessTerminateCancelationLog(commonHelper.getProcessLog(tProcId,
						"Termination completed successfully",IConstants.PROC_STATUS_SUCCESS,uId),
						actualRtlDate);	
			}*/
        }catch (BusinessException be) {			
        	be.printStackTrace();
        	LOGGER.info("Business Exception occured in dealer terminate.", be);
        	throw new BusinessException("DPB.BNS.PROC.DLR.TRMT.001","Exception occured during Dealer Termination Process");			
		}catch (TechnicalException te) {		
			te.printStackTrace();
			LOGGER.info("Technical Exception occured in dealer terminate.", te);
			throw new TechnicalException("DPB.BNS.PROC.DLR.TRMT.002","Exception occured during Dealer Termination Process");		
		}
	}
	/**
	 * @param bonusInfoList
	 * @param masterVehicleProgramMap
	 * @param uId
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public void calculateBonusBasedOnProgram(List<BonusInfo> bonusInfoList,Map<String, List<ProgramDefinition>> masterVehicleProgramMap,
			String uId,Integer parentId,List<InvalidReason> bnsFailRecords) throws BusinessException,TechnicalException{
		final String methodName = "calculateBonusBasedOnProgram";
		LOGGER.enter(CLASSNAME, methodName);
		String schedulerType = IConstants.EMPTY_STR;
		java.sql.Date startDate = null;
	    java.sql.Date endDate = null;
		DPBCommonHelper commonHelper = new DPBCommonHelper();
		DpbCommonBeanLocal commonBean = null;   
		SpecialBonusProgram specialBonus = new SpecialBonusProgram();
		KpiCalculation kpiCalculation = new KpiCalculation();
		boolean isFullBns = false;
    	commonBean = local.getDpbCommonService();
    	List<String> vTypeList = new ArrayList<String>();
    	ProgramDefinition pgm = null;
    	// program definition
    	if(masterVehicleProgramMap!=null && !masterVehicleProgramMap.isEmpty()){
			for (Object key : masterVehicleProgramMap.keySet()) {
				String vType = (String)key;
				vTypeList.add(vType);
				if(vTypeList!= null && !vTypeList.isEmpty() && vTypeList.size()  == 1 ){
					List<ProgramDefinition> pgmDefList  =  masterVehicleProgramMap.get(vType);				
					if(pgmDefList != null && pgmDefList.size() > 0){
						for(Iterator<ProgramDefinition> pgmDef =  pgmDefList.iterator();pgmDef.hasNext();){
							 pgm = pgmDef.next();	
						}
					}	
				}
			}
		}
    	boolean isDlrTrmt = false;
		boolean isLogUpdate = false;
		schedulerType = commonHelper.getBonusSchedulerType(pgm);
		startDate = decideStartDateForProcess(schedulerType, pgm.getActlProcDte());
		endDate  = decideEndDateForProcess(schedulerType, pgm.getActlProcDte());
		LOGGER.info("Process ID:"+pgm.getProcessId()+":ProgramId :"+pgm.getProgramId()+":schedulerType:"+schedulerType+":Actual Process Date:"+pgm.getActlProcDte()+":startDate :"+startDate+"endDate :"+endDate);
		//startDate = new java.sql.Date((new java.util.GregorianCalendar(2013,3,2).getTime()).getTime());
		//endDate  = new java.sql.Date((new java.util.GregorianCalendar(2013,4,1).getTime()).getTime());
		
		boolean isSpecialPgm = false;
		if(IConstants.PROGRAM_TYPE_SPECIAL.equalsIgnoreCase(pgm.getSpecialProgram())){
			updateProcessLogs(parentId,"Program is Special Program. Bonus calculation is in progress...", 
					IConstants.PROC_STATUS_IN_PROCESS,uId);
			isSpecialPgm = true;
		}else{
			updateProcessLogs(parentId,"Program is General Program. Bonus calculation is in progress...", 
					IConstants.PROC_STATUS_IN_PROCESS,uId);
			isSpecialPgm = false;
		}
		List<DealerInfo> tDlrList =  getTerminatedDlrListTillActualDate(pgm.getActlProcDte(),commonBean); 
		List<VistaFileProcessing> rtlVehicleList = commonBean.retrieveRtlVehicleDataGivenPeriodAndType(startDate, endDate, vTypeList,isSpecialPgm);
		// AMG Changes - START
		List<String> amgPerfDlrs = getAmgPerfDealers();
		List<String> amgEliteDlrs = getAmgEliteDealers();
		// AMG Changes - END
		boolean isPgmApplied = false;
		 
		if(rtlVehicleList!= null && !rtlVehicleList.isEmpty()){
			boolean splCondSatisfied = false;
			LOGGER.info("No. Of vehicle:"+rtlVehicleList.size());
			for(Iterator<VistaFileProcessing> vehiclesSoldLastDateIter = rtlVehicleList.iterator(); vehiclesSoldLastDateIter.hasNext();)
			{	
				// 10 vehicle
				VistaFileProcessing vistaFileProcessing = vehiclesSoldLastDateIter.next();
				if(!DPBCommonHelper.isListNullOrEmpty(tDlrList)){
					isDlrTrmt = tDlrList.contains(vistaFileProcessing.getDealerId());
				}
				/*if(isLogUpdate){
					updateProcessLogs(pgm.getProcessId(),
							pgm.getProcessType()+" special condition:"+splCondSatisfied, 
						IConstants.PROC_STATUS_IN_PROCESS,userId);
				}*/			
				LOGGER.info("Program Type:"+pgm.getSpecialProgram()+":Dealer code:"+vistaFileProcessing.getDealerId()+":PO:"+vistaFileProcessing.getPoNo()+":Vehicle Type:"+vistaFileProcessing.getVehTypeCode()+":Parent PGM ID :"+vistaFileProcessing.getProgType());
				
				if(IConstants.PROGRAM_TYPE_SPECIAL.equalsIgnoreCase(pgm.getSpecialProgram())){
					if(vistaFileProcessing!= null 
								&& vistaFileProcessing.getProgType()!= null 
									&& (vistaFileProcessing.getProgType() != null && vistaFileProcessing.getProgType() > 0 ) ){ // greater then zero means Special program 
						splCondSatisfied = 	checkConditionsSatisfied(pgm, vistaFileProcessing);
						if(splCondSatisfied){
							
							isPgmApplied = true;
							specialBonus.applySpecialBonus(bonusInfoList,pgm, vistaFileProcessing,uId,isFullBns,isLogUpdate,
									commonBean,isDlrTrmt,bnsFailRecords,endDate);
							isLogUpdate = false;
						}
					}
				}else{
						/*	boolean isSplPgmVehicle = false;
					List<ProgramDefinition> splPgmDefList  =  null;
					ProgramDefinition splPgm = null;
					Map<String, List<ProgramDefinition>> vehicleSpecialProgramMap = null;
					vehicleSpecialProgramMap = commonBean.retrieveSpecialVehicleProgramMap(vistaFileProcessing.getVehTypeCode());
					if(vehicleSpecialProgramMap!=null && !vehicleSpecialProgramMap.isEmpty()){
						for (Object key : vehicleSpecialProgramMap.keySet()) {
							String vType = (String)key;
							splPgmDefList  =  vehicleSpecialProgramMap.get(vType);				
						}
					}
					if(splPgmDefList != null && splPgmDefList.size() > 0){
						for(Iterator<ProgramDefinition> pgmDef =  splPgmDefList.iterator();pgmDef.hasNext();){
							splPgm = pgmDef.next();	
							isSplPgmVehicle = checkConditionsSatisfied(splPgm, vistaFileProcessing);
							if(isSplPgmVehicle)
							break;
						}
					}
					
					
					if(!isSplPgmVehicle){
						if(!isSplPgmVehicle){
						isPgmApplied = true;
						kpiCalculation.applyGeneralPgm(bonusInfoList,pgm, vistaFileProcessing, vistaFileProcessing.getDealerId(),null,uId,isFullBns,isLogUpdate);
						isLogUpdate = false;
					}
					*/
					 // less then zero means vehicle applicable for General program 
					if(vistaFileProcessing!= null && (vistaFileProcessing.getProgType()== null || vistaFileProcessing.getProgType() <=0 )){
						isPgmApplied = true;
						kpiCalculation.applyGeneralPgm(bonusInfoList,pgm, vistaFileProcessing, 
									vistaFileProcessing.getDealerId(),endDate,uId,isFullBns,isLogUpdate,isDlrTrmt,bnsFailRecords, amgPerfDlrs, amgEliteDlrs);
						isLogUpdate = false;
					}
				}
			}
			if(!isPgmApplied && !splCondSatisfied){
				updateProcessLogs(pgm.getProcessId(),
						pgm.getProcessType()+" bonus process is completed.Condition failed to satisfy.", 
						IConstants.PROC_STATUS_IN_PROCESS,uId);
			}else {
				if(!isPgmApplied){
					updateProcessLogs(pgm.getProcessId(),
							pgm.getProcessType()+" bonus calculation is over.", 
							IConstants.PROC_STATUS_IN_PROCESS,uId);
				}
			}
			if(isPgmApplied){// no condition satisfy
				updateProcessLogs(parentId,
						"Bonus calculation completed. DPB application will store calculated data in database. ", 
						IConstants.PROC_STATUS_IN_PROCESS,uId);
			}
		}else{
			updateProcessLogs(parentId,
					"No vehicle found. Bonus calculation is over.", 
				    IConstants.PROC_STATUS_IN_PROCESS,uId);
		}
	}
	
	// AMG Changes - START
	/**
	 * Gets the list of AMG Elite Dealers from properties
	 * @return List<String>
	 */
	private List<String> getAmgEliteDealers() {
		String[] amgEliteDlrsArr = null;
		List<String> amgEliteDealersList = new ArrayList<String>(); 
		String amgEliteDlrsL = PropertyManager.getPropertyManager().getPropertyAsString("dpb.amg.elite.dealers");
		if (amgEliteDlrsL != null && !amgEliteDlrsL.isEmpty()) {
			amgEliteDlrsArr = amgEliteDlrsL.split(",");
			amgEliteDealersList = Arrays.asList(amgEliteDlrsArr);
		}
		return amgEliteDealersList;
	}
	
	/**
	 * Gets the list of AMG Performance Dealers from properties
	 * @return List<String>
	 */
	private List<String> getAmgPerfDealers() {
		String[] amgPerfDlrsArr = null;
		List<String> amgPerfDealersList = new ArrayList<String>(); 
		String amgPerfDlrsL = PropertyManager.getPropertyManager().getPropertyAsString("dpb.amg.perf.dealers");
		if (amgPerfDlrsL != null && !amgPerfDlrsL.isEmpty()) {
			amgPerfDlrsArr = amgPerfDlrsL.split(",");
			amgPerfDealersList = Arrays.asList(amgPerfDlrsArr);
		}
		return amgPerfDealersList;
	}
	// AMG Changes - END
	
	/**
	 * 
	 * @param actualDate
	 * @param commonBean
	 * @return
	 */
	public List<DealerInfo> getTerminatedDlrListTillActualDate(java.sql.Date actualDate,DpbCommonBeanLocal commonBean ){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(actualDate);		
		c1.add(Calendar.DATE, -1);
		// check terminate date as (actual date -1 )
		java.sql.Date dlrTerminateDate = new java.sql.Date(c1.getTime().getTime());			     
        List<DealerInfo> tDealer =  commonBean.getTerminatedDlrListTillActualDate(dlrTerminateDate);  
        return tDealer;
	}
	/**
	 * 
	 * @param uId
	 */
	public boolean startAdjustmentProcess(List<BonusInfo> bonusAdjList,Integer pId,Date actualDate,
							String uId,List<InvalidReason> invalidReason) 
										throws BusinessException,TechnicalException{
		DpbCommonBeanLocal commonBean = null; 
    	commonBean = local.getDpbCommonService();
    	updateProcessLogs(pId,
				"Adjustment process has been started.", 
				IConstants.PROC_STATUS_IN_PROCESS,uId);
    	if(actualDate== null){
    		actualDate = commonBean.getActualProcessDate(pId);
    	}
    	boolean isTRCompleted = true;
    	//boolean isTRCompleted = commonBean.isDealerTerminationCancelationProcLogs(actualDate);
		//if(!isTRCompleted){
		//	startDealerTerminateProcess(bonusAdjList,actualDate,uId,pId,invalidReason);
			//startCancelationProcess(bonusAdjList,uId,actualDate,pId);
		//}
    	BonusAdjustmentProcess bnsAdj =  new BonusAdjustmentProcess();
    	bnsAdj.doAdjustment(bonusAdjList,pId,commonBean,actualDate,uId,invalidReason);
    	return !isTRCompleted;
	}
	/**
	 * 
	 * @param bonusInfo
	 * @param userId
	 */
	public void  startCancelationProcess(List<BonusInfo> bonusInfo,String userId,Date aDate,Integer parentProcId) throws BusinessException,TechnicalException{
		BonusCancelationProcess cancelBonus = new  BonusCancelationProcess();
		//what will be start date and end date for termination.
		//based on that we have to pass both date.
		
		cancelBonus.initiateBonusCancelation(bonusInfo,userId,aDate,parentProcId);
	}
	/**
  	 * 
  	 * @param schedulerType
  	 * @param dayID
  	 * @return
  	 */
  	public java.sql.Date decideEndDateForProcess(String schedulerType, java.sql.Date actualDte) {
  		java.sql.Date endDate = null;
  		java.sql.Date considerDte = null;
  		Calendar c = Calendar.getInstance();
		java.sql.Date tempDate = actualDte;
		c.setTime(tempDate);
		c.add(Calendar.DATE, -1);
		considerDte = new java.sql.Date(c.getTime().getTime());
  		RtlCalenderDefinition rtlDim = MasterDataLookup.getInstance().getRtlCalByDate(considerDte);
  		if (IConstants.SCHD_YEARLY.equalsIgnoreCase(schedulerType)) {
  			endDate = rtlDim.getDteRetlYearEnd();
		}else if (IConstants.SCHD_QUERTERLY.equalsIgnoreCase(schedulerType)) {
		  	endDate = rtlDim.getDteRetlQtrEnd();
		}else if (IConstants.SCHD_MONTH.equalsIgnoreCase(schedulerType)) {
		  	endDate = rtlDim.getDteRetlMthEnd();
		}else if (IConstants.SCHD_BI_WEEKLY.equalsIgnoreCase(schedulerType)) {
			endDate = rtlDim.getDteCal();
		}else if (IConstants.SCHD_WEEKLY.equalsIgnoreCase(schedulerType)) {
			endDate = rtlDim.getDteCal();
		}else {// start date and end date is today date
			/*Calendar c1 = Calendar.getInstance();
			endDate = rtlDim.getDteCal();
			c1.setTime(endDate);
			c1.add(Calendar.DATE, -1);
			//startDate  = new java.sql.Date(c1.getTime().getTime());
			endDate = new java.sql.Date(c1.getTime().getTime());*/
			endDate = rtlDim.getDteCal();
		}
		return endDate;
      }
  	/**
  	 * 
  	 * @param schedulerType
  	 * @param dayID
  	 * @return
  	 */
  	public java.sql.Date decideStartDateForProcess(String schedulerType, java.sql.Date actualDte) 
  	{
  		java.sql.Date considerDte = null;
  		Calendar c = Calendar.getInstance();
		java.sql.Date tempDate = actualDte;
		c.setTime(tempDate);
		c.add(Calendar.DATE, -1);
		considerDte = new java.sql.Date(c.getTime().getTime());
		
		java.sql.Date startDate = null;
		RtlCalenderDefinition rtlDim = MasterDataLookup.getInstance().getRtlCalByDate(considerDte);
		if (IConstants.SCHD_YEARLY.equalsIgnoreCase(schedulerType)) {
			startDate = rtlDim.getDteRetlYearBeg();
		}else if (IConstants.SCHD_QUERTERLY.equalsIgnoreCase(schedulerType)) {
			startDate = rtlDim.getDteRetlQtrBeg();
		}else if (IConstants.SCHD_MONTH.equalsIgnoreCase(schedulerType)) {
			startDate = rtlDim.getDteRetlMthBeg();
		}else if (IConstants.SCHD_BI_WEEKLY.equalsIgnoreCase(schedulerType)) {		
			Calendar c1 = Calendar.getInstance();
			java.sql.Date endDate =  rtlDim.getDteCal();
			c1.setTime(endDate);
			c1.add(Calendar.DATE, -14);
			startDate = new java.sql.Date(c1.getTime().getTime());
		}else if (IConstants.SCHD_WEEKLY.equalsIgnoreCase(schedulerType)) {
			Calendar c1 = Calendar.getInstance();
			java.sql.Date endDate = rtlDim.getDteCal();
			c1.setTime(endDate);
			c1.add(Calendar.DATE, -7);
			startDate  = new java.sql.Date(c1.getTime().getTime());
		}else {// start date and end date is today date
			/*Calendar c1 = Calendar.getInstance();
			java.sql.Date endDate = rtlDim.getDteCal();
			c1.setTime(endDate);
			c1.add(Calendar.DATE, -1);
			startDate  = new java.sql.Date(c1.getTime().getTime());*/
			startDate = rtlDim.getDteCal();
		}
		return startDate;
  	}
  	
	/**
	 * 
	 * @param bonusCalculatedList
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public void updateBonusCalculation(List<BonusInfo> bonusCalculatedList,Integer parentProcId,String userId,boolean isTrConsider,boolean ldrsp ) throws BusinessException,TechnicalException{
		DpbCommonBeanLocal commonBean = null; 		
        commonBean = local.getDpbCommonService();
        commonBean.insertBonusCalculationRecord(bonusCalculatedList,parentProcId,userId,isTrConsider,ldrsp);		
	}
	/**
	 * 
	 * @param actualDate
	 * @return
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public String checkTerminateAdjustmentCancelationStatus(Date actualDate) throws BusinessException,TechnicalException{
		DpbCommonBeanLocal commonBean = null; 		
	    commonBean = local.getDpbCommonService();
	    commonBean.checkTerminateAdjustmentCancelationStatus(actualDate);	
		return "";
		
	}
	/**
	 * 
	 * @param bonusCalculatedList
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public void startLdrshipProgramProcess(List<BonusInfo> bonusInfo,int procId,String processType,
				String user,List<InvalidReason> invalidReason) throws BusinessException,TechnicalException {
		final String methodName = "startLdrshipProgramProcess";
		LOGGER.enter(CLASSNAME, methodName);	
			DpbCommonBeanLocal commonBean = null; 
			FileProcessLogMessages message = null;
			double unUsedAmt = 0.0;
			double unUsedAmtAcrl = 0.0;		
			commonBean = local.getDpbCommonService();
			DPBCommonHelper commonHelper = new DPBCommonHelper();
			message = new FileProcessLogMessages();			
			message = commonHelper.getProcessLog(procId,IConstants.START_LDRSP_BNS_PROCESS, IConstants.PROC_STATUS_IN_PROCESS,  user);
			commonBean.insertIntoProcessLog (message);
			bonusInfo = commonBean.calculateLdrshipBonusBasedOnProgram(bonusInfo,procId,processType,user);	
			if(null != bonusInfo &&  bonusInfo.size() > 0) {
				//for (int i = 0; i < bonusInfo.size();i++) {
					unUsedAmt = bonusInfo.get(0).getUnUsedAmt();
					unUsedAmtAcrl = bonusInfo.get(0).getUnUsedAmtAcrl();
					if(unUsedAmt != unUsedAmtAcrl) {
						message = new FileProcessLogMessages();
						message = commonHelper.getProcessLog(procId,IConstants.MTCH_UNUSED_AMT, IConstants.PROC_STATUS_IN_PROCESS, user);
						commonBean.insertIntoProcessLog ( message);						
					}
				//}
			}	
	
}
	/**
	 * 
	 * @param progDef
	 * @param vistaFileProcessing
	 * @return
	 * @throws BusinessException
	 */
	public boolean checkConditionsSatisfied(ProgramDefinition progDef, VistaFileProcessing vistaFileProcessing) throws BusinessException{
		final String methodName = "checkConditionsSatisfied";
		LOGGER.enter(CLASSNAME, methodName);
		
		boolean returnCondition = true;
		try{
			DpbCommonBeanLocal commonBean = null;				
			commonBean = local.getDpbCommonService();
			List<ConditionDefinition> conditionDefs = commonBean.getConditionDefinitions(progDef.getProgramId());;
	
			Iterator<ConditionDefinition> conditionDefIter = conditionDefs.iterator();
			ConditionDefinition conDef = null;
			
			
			while(conditionDefIter.hasNext()){
				boolean splCondSatisfied = true;
				conDef = conditionDefIter.next();
				String checkValue = conDef.getCheckValue() != null && conDef.getCheckValue().trim().length() > 0 ? conDef.getCheckValue().trim() : 
					conDef.getRegularExp() != null ? conDef.getRegularExp().trim() : "";
				
				switch(IConstants.VISTA_FILE_ENUM.valueOf(conDef.getVariableName().trim())){
	
					case ID_EMP_PUR_CTRL : 					
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getEmpPurCtrlId(), checkValue);
						break;
		
					case DTE_RTL : 	
						splCondSatisfied = DPBCommonHelper.checkDateCondition(conDef.getCondition(), vistaFileProcessing.getRetailDate() , checkValue);
						break;
		
					case AMT_MSRP_BASE : 
						splCondSatisfied = DPBCommonHelper.checkDoubleCondition(conDef.getCondition(), vistaFileProcessing.getMsrpBaseAmount() , checkValue);
						break;
		
					case AMT_MSRP : 
						splCondSatisfied = DPBCommonHelper.checkDoubleCondition(conDef.getCondition(), vistaFileProcessing.getMsrpTotalsAmt() , checkValue); 
						break;
						
					case AMT_MSRP_TOT_ACSRY : 
						splCondSatisfied = DPBCommonHelper.checkDoubleCondition(conDef.getCondition(), vistaFileProcessing.getMsrpTotAmtAcsry() , checkValue);
						break;
		
					case TME_TRANS : 
						break; 
		
					case NUM_PO : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getPoNo() , checkValue);
						break;
		
					case NUM_VIN : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getVinNum() , checkValue);
						break;
		
					case ID_DLR : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getDealerId() , checkValue);
						break;
		
					case IND_USED_VEH : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getUsedCarIndicator() , checkValue);
						break;
		
					case DTE_MODL_YR : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getModelYearDate() , checkValue);
						break;
		
					case DES_MODL : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getModelText(), checkValue); 
						break;
		
					case CDE_RGN : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getRegionCode(), checkValue);
						break;
		
					case NAM_RTL_CUS_LST : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getRetailCustLastName(), checkValue);
						break;
		
					case NAM_RTL_CUS_FIR : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getRetailCustFirstName(), checkValue);
						break;
		
					case NAM_RTL_CUS_MID : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getRetailCustMiddleName(),checkValue);
						break;
		
					case CDE_USE_VEH : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getUseCode(), checkValue);
						break;
		
					case AMT_DLR_RBT : 
						splCondSatisfied = DPBCommonHelper.checkDoubleCondition(conDef.getCondition(), vistaFileProcessing.getDlrRebateAmt(), checkValue);
						break;
		
					case IND_FLT : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getFleetIndicator(), checkValue);
						break;
		
					case CDE_WHSLE_INIT_TYP : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getWholeSaleInitType(), checkValue);
						break;
		
					case CDE_VEH_STS : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getVehStatusCode(), checkValue);
						break;
		
					case CDE_NATL_TYPE : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getNationalTypeCode(), checkValue);
						break;
		
					case CDE_VEH_TYP : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getVehTypeCode(), checkValue);
						break;
		
					case IND_USED_VEH_DDRS : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getUsedCarIndicator2(), checkValue);
						break;
						
					case CDE_VEH_DDR_STS : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getCarStatusCode(), checkValue);
						break;
						
					case CDE_VEH_DDR_USE : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getSalesTypeCode(), checkValue);
						break;
						
					default : 
						splCondSatisfied = false;	
				}
				if( !splCondSatisfied){
					returnCondition = splCondSatisfied;
				}
			}			
		}catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		LOGGER.exit(CLASSNAME, methodName);
		return returnCondition;
	}
	/**
	 * 
	 * @param pId
	 * @param txtMessage
	 * @param status
	 * @param userId
	 * @throws TechnicalException
	 */
	public void updateProcessLogs(Integer pId, String txtMessage,String status,String userId) throws TechnicalException{
		DpbCommonBeanLocal commonBean = local.getDpbCommonService();
		commonBean.insertIntoProcessLog(commonHelper.getProcessLog(pId,txtMessage,status,userId));		
	}
	
	/**
	 * Apply today available dealer and special program.
	 * @param bonusInfo
	 * @param processId
	 * @param processType
	 * @param userId
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public boolean  startDealerProgramLdrspProcess(List<BonusInfo> bonusInfo, Integer processId,String processType,
												String userId,String callType,List<InvalidReason> invalidReason) 
															throws BusinessException,TechnicalException{
		final String methodName = "startDealerProgramLdrspProcess";
		LOGGER.enter(CLASSNAME, methodName);
		updateProcessLogs(processId,processType.trim()+":Process has been started.",IConstants.PROC_STATUS_IN_PROCESS,userId);
		DpbCommonBeanLocal commonBean = null;		
		commonBean = local.getDpbCommonService();
		java.sql.Date aDate =  null;
		if(aDate == null){
			aDate = commonBean.getActualProcessDate(processId);
    	}
		boolean isTRCompleted = true;
		/*boolean isTRCompleted = commonBean.isDealerTerminationCancelationProcLogs(aDate);
		if(!isTRCompleted){
			startDealerTerminateProcess(bonusInfo,aDate,userId,processId,invalidReason);
			//startCancelationProcess(bonusInfo,userId,aDate,processId);
		}*/		
		startLdrshipProgramProcess(bonusInfo,processId,processType,userId,invalidReason);	 
		LOGGER.exit(CLASSNAME, methodName);
		return !isTRCompleted;
	}	
	//Unearned Performance Bonus calculation start - Kshitija
		/**
		 * @param maxBnsEligibleAmt
		 * @param bnsCalcAmt
		 * @param carStatusCode
		 * @return
		 */
		public static double calculateUnearnedBonus(double maxBnsEligibleAmt,
				double bnsCalcAmt, String carStatusCode) {
			double amtUnernd = maxBnsEligibleAmt - Math.abs(bnsCalcAmt);
			if(IConstants.RETAIL_CANCEL_CODE.equalsIgnoreCase(carStatusCode))
			{
				amtUnernd = -1*amtUnernd;
			}
			return amtUnernd;
			
		}
	//Unearned Performance Bonus calculation end - Kshitija
}
