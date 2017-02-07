/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: SpecialBonusProgram.java
 * Program Version			: 1.0
 * Program Description		: This class is calculate bonus based on Special program.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 11, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.business.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.business.view.DpbCommonBeanLocal;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.BonusInfo;
import com.mbusa.dpb.common.domain.InvalidReason;
import com.mbusa.dpb.common.domain.ProgramDefinition;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.DPBCommonHelper;
import com.mbusa.dpb.common.util.DateCalenderUtil;

public class SpecialBonusProgram{

	private static DPBLog LOGGER = DPBLog.getInstance(BonusProcess.class);
	PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
	static final private String CLASSNAME = BonusProcess.class.getName();
	private LocalServiceFactory local = new LocalServiceFactory();
	DPBCommonHelper commonHelper = new DPBCommonHelper();
	private BonusInfo rtlBns = null;
	public void initiateSpecialBonus(){
		
	}
	public void updateProcessLogs(Integer pId, String txtMessage,String status,String userId) throws TechnicalException{
		DpbCommonBeanLocal commonBean = local.getDpbCommonService();
		commonBean.insertIntoProcessLog(commonHelper.getProcessLog(pId,txtMessage,status,userId));		
	}
	/**
	 * 
	 * 
	 * @param bonusInfoList
	 * @param progDef
	 * @param vistaFileProcessing
	 * @param userId
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public void applySpecialBonus(List<BonusInfo> bonusInfoList,ProgramDefinition progDef, VistaFileProcessing vistaFileProcessing,String userId,boolean isFullBns, boolean isLogUpdate, DpbCommonBeanLocal commonBean,boolean isTerminated,List<InvalidReason> failureReason,java.sql.Date aDate) throws BusinessException,TechnicalException {
		final String methodName = "applySpecialBonus";
		LOGGER.enter(CLASSNAME, methodName);	
		String processType = progDef.getProcessType();
		Map<String, List<ProgramDefinition>> vehicleProgramMap = null;
		if(isLogUpdate){
			updateProcessLogs(progDef.getProcessId(),
				processType+" bonus calculation in progress.", 
				IConstants.PROC_STATUS_IN_PROCESS,userId);
		}

		if(IConstants.COMMISSION_BONUS_PROCESS.equalsIgnoreCase(processType)){	
			if(IConstants.CONSTANT_Y.equalsIgnoreCase(progDef.getCommPayment())){
				calculateSpecialCommissionBonus(bonusInfoList,progDef, vistaFileProcessing,userId,isFullBns,isLogUpdate,isTerminated,failureReason);
			}
		}
		if(IConstants.FIXED_BONUS_PROCESS.equalsIgnoreCase(processType)){				
			/*if(IConstants.CONSTANT_Y.equalsIgnoreCase(progDef.getCommPayment())){
			calculateSpecialCommissionBonus(bonusInfoList,progDef, vistaFileProcessing,userId,isFullBns,isLogUpdate);
			}*/
			KpiCalculation kpiCalculation = new KpiCalculation();
			List<ProgramDefinition> pgmDefList  =  null;
			ProgramDefinition pgm = null;
			vehicleProgramMap = commonBean.retrieveVehicleProgramMap(vistaFileProcessing.getVehTypeCode(), IConstants.GENERAL_PROGRAM_TYPE_FIXED);
			if(vehicleProgramMap!=null && !vehicleProgramMap.isEmpty()){
				for (Object key : vehicleProgramMap.keySet()) {
					String vType = (String)key;
					pgmDefList  =  vehicleProgramMap.get(vType);				
				}
			}
			if(pgmDefList != null && pgmDefList.size() > 0){
				for(Iterator<ProgramDefinition> pgmDef =  pgmDefList.iterator();pgmDef.hasNext();){
					 pgm = pgmDef.next();	
					 pgm.setProcessId(progDef.getProcessId());
					 pgm.setActlProcDte(progDef.getActlProcDte());
					 pgm.setProcessType(IConstants.FIXED_BONUS_PROCESS);
					 pgm.setParentPgmId(progDef.getProgramId());
					 kpiCalculation.applyGeneralPgm(bonusInfoList,pgm, vistaFileProcessing, vistaFileProcessing.getDealerId(),aDate,userId,isFullBns,isLogUpdate,isTerminated,failureReason, null, null);
				}
			}	
			isLogUpdate = false;			
		}			
		if(IConstants.VARIABLE_BONUS_PROCESS.equalsIgnoreCase(processType)){				
			//calculateSpecialVariableBonus(bonusInfoList,progDef, vistaFileProcessing,userId,isFullBns,isLogUpdate);
			isFullBns = progDef.getMaxVarPayment().equals(IConstants.YES) ? true : false; //Max variable pay - refers full payment for general program.
			KpiCalculation kpiCalculation = new KpiCalculation();
			List<ProgramDefinition> pgmDefList  =  null;
			ProgramDefinition pgm = null;
			vehicleProgramMap = commonBean.retrieveVehicleProgramMap(vistaFileProcessing.getVehTypeCode(), IConstants.GENERAL_PROGRAM_TYPE_VARIABLE);
			if(vehicleProgramMap!=null && !vehicleProgramMap.isEmpty()){
				for (Object key : vehicleProgramMap.keySet()) {
					String vType = (String)key;
					pgmDefList  =  vehicleProgramMap.get(vType);				
				}
			}
			if(pgmDefList != null && pgmDefList.size() > 0){
				for(Iterator<ProgramDefinition> pgmDef =  pgmDefList.iterator();pgmDef.hasNext();){
					 pgm = pgmDef.next();	
					 pgm.setProcessId(progDef.getProcessId());
					 pgm.setActlProcDte(progDef.getActlProcDte());
					 pgm.setProcessType(IConstants.VARIABLE_BONUS_PROCESS);
					 pgm.setParentPgmId(progDef.getProgramId());
					 kpiCalculation.applyGeneralPgm(bonusInfoList,pgm, vistaFileProcessing, vistaFileProcessing.getDealerId(),aDate,userId,isFullBns,isLogUpdate,isTerminated,failureReason, null, null);
				}
			}	
			
		}		
		LOGGER.exit(CLASSNAME, methodName);
	}

	/**
	 * 
	 * @param bonusInfoList
	 * @param progDef
	 * @param rtlVeh
	 * @param updatedBy
	 * @param isFullBonus
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	/*private void calculateSpecialVariableBonus(List<BonusInfo> bonusInfoList,ProgramDefinition progDef, VistaFileProcessing rtlVeh,String updatedBy,boolean isFullBonus,boolean isLogUpdate)  throws BusinessException,TechnicalException{
		final String methodName = "calculateSpecialVariableBonus";
		LOGGER.enter(CLASSNAME, methodName);
		try{
			double amountBonus = 0;
			double amountMax = 0;
			
			amountBonus = (progDef.getVariablePayment()* rtlVeh.getMsrpBaseAmount())/100;
			amountMax = amountBonus;			
			BonusInfo specialBns = new BonusInfo();
			specialBns.setProcessId(progDef.getProcessId());
			specialBns.setPgmId(progDef.getProgramId());
			specialBns.setPoNumber(rtlVeh.getPoNo());
			specialBns.setDlrId(rtlVeh.getDealerId());
			specialBns.setDayId(progDef.getRtlDateId());
			specialBns.setBnsCalcAmt(amountBonus);
			specialBns.setMaxBnsEligibleAmt(amountMax);
			specialBns.setAdjIndicator(IConstants.CONSTANT_N);
			specialBns.setLdrBonusIndicator(IConstants.CONSTANT_N);
			specialBns.setStatus(com.mbusa.dpb.common.constant.IConstants.BONUS_STATUS_CALCULATED);		
			specialBns.setCreateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
			specialBns.setUpdateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
			specialBns.setUserId(updatedBy);		
			bonusInfoList.add(specialBns);	
			if(isLogUpdate){
				updateProcessLogs(progDef.getProcessId(),
									progDef.getProcessType()+" bonus calculated. Email notification is pending.", 
									IConstants.PROC_STATUS_IN_PROCESS,updatedBy);
			}
	    }catch (TechnicalException e) {
			e.printStackTrace();
			throw new TechnicalException("BNS.PROC.005","Exception occured while applying KPI to Program");
		}
		LOGGER.exit(CLASSNAME, methodName);
	}*/
	/**
	 * 
	 * @param bonusInfoList
	 * @param progDef
	 * @param rtlVeh
	 * @param updatedBy
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public void calculateSpecialCommissionBonus(List<BonusInfo>bonusInfoList,ProgramDefinition progDef, 
			VistaFileProcessing rtlVeh,String updatedBy,boolean isFullBonus,boolean isLogUpdate,
			boolean isDlrTrmt,List<InvalidReason> bnsFailRecords) throws BusinessException,TechnicalException{		
		final String methodName = "calculateSpecialCommissionBonus";
		LOGGER.enter(CLASSNAME, methodName);
		//try {
			double amountBonus = 0;		
			double amountMax = 0;			
			//if(!isDPP){// if is DPP Program
				if(IConstants.CONSTANT_Y.equalsIgnoreCase(progDef.getCommPayment())){
					if(IConstants.CONSTANT_Y.equalsIgnoreCase(progDef.getRebateAmt())){
						amountBonus = rtlVeh.getDlrRebateAmt();
						amountMax = amountBonus;
					}else{
						if(progDef.getCommAmount() > 0){//This is DEPP condition. i.e. Commission amount
							amountBonus = progDef.getCommAmount();
							amountMax = amountBonus;
						}
						/*if(progDef.getCommPercent() > 0){// This may not happen as the % value will be given in KPI only
														 // Always the special program gives the amount not percent( per Lynn)
							amountBonus = (progDef.getCommPercent()*rtlVeh.getMsrpBaseAmount())/100;
							amountMax = amountBonus;
						}else if(progDef.getCommPercent() < 0){//This is DEPP condition.
							amountBonus = progDef.getCommAmount();
							amountMax = amountBonus;
						}*/
					}		
					BonusInfo specialBns = new BonusInfo();
					specialBns.setProcessId(progDef.getProcessId());
					specialBns.setPgmId(progDef.getProgramId());
					specialBns.setDpbUnblkVehId(rtlVeh.getUnBlkVehId());
					specialBns.setDlrId(rtlVeh.getDealerId());
					specialBns.setActualDate(progDef.getActlProcDte());
					if(IConstants.RETAIL_CANCEL_CODE.equalsIgnoreCase(rtlVeh.getCarStatusCode())){
						//sv:get the original retail id,
						DpbCommonBeanLocal commonBean = null; 
				    	commonBean = local.getDpbCommonService();
						List<BonusInfo> listForCancel = commonBean.getPreviousBonusRecordsForCancel(rtlVeh.getPoNo(),rtlVeh.getDealerId(),rtlVeh.getRetailDate(),rtlVeh.getRetailTime());
			    		if(listForCancel!= null && listForCancel.size() > 0 ){
			    			BonusInfo actualRetailBonus = listForCancel.get(0); // Note that only the ID_DPB_UNBLK_VEH is populate in this Object.
			    			
			    			List<BonusInfo> amountForCancel =commonBean.getBonusDetailsForCancel(""+actualRetailBonus.getDpbUnblkVehId(), progDef.getProgramId());
			    			if(amountForCancel!= null && amountForCancel.size() > 0 ){
			        			//doAdjustmentBonusCalculation(adjustedBnsList,listForAdjustment,updatePercentage,aDate,userId,invalidReason);
			        			//sv:if the retail is calculated with DPB bonus- calculate the whole paid amount and revert back....
			    				BonusInfo totalBonusForPgm = amountForCancel.get(0); 
			    				double cancelAmt = totalBonusForPgm.getBnsCalcAmt();
			    				double totalCalcAmtForPgm = totalBonusForPgm.getMaxBnsEligibleAmt();
			    				if(cancelAmt <= totalCalcAmtForPgm){
			    					specialBns.setBnsCalcAmt(-cancelAmt);
			    				}else{
			    					//CB:
			    					//String fMsg  ="Calculated amount is greater than Maximum eligible amount";
			    					DPBCommonHelper.addIntoBonusAttachmentList(bnsFailRecords,rtlVeh,progDef,
			    								IConstants.PROC_STATUS_FAILURE,IConstants.BNS_MSG_CALC_AMT_GREATER_THAN_MAX_ELIGIBILITY
			    								,IConstants.EMPTY_STR);
			    					//sv:if calculated amount is > max eligible amount throw e-mail
			    					//sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"Calculated amount is greater than Maximum eligible amount",progDef.getProcessId(),bnsFailRecords,progDef.getProcessType(),progDef.getActlProcDte(),progDef.getVariablePymtBonusTrigger());
			    					return;
			    				}
			    			}else if(isRtlExistForCanceled(bonusInfoList,actualRetailBonus,progDef)){
								double cancelAmt = rtlBns.getBnsCalcAmt();	    				
			    				double totalCalcAmtForPgm = rtlBns.getMaxBnsEligibleAmt();
			    				if(cancelAmt <= totalCalcAmtForPgm){
			    					specialBns.setBnsCalcAmt(-cancelAmt);
			    				}else{
			    					DPBCommonHelper.addIntoBonusAttachmentList(bnsFailRecords,rtlVeh,progDef,
			    							IConstants.PROC_STATUS_FAILURE,
			    							IConstants.BNS_MSG_CALC_AMT_GREATER_THAN_MAX_ELIGIBILITY,
			    							IConstants.EMPTY_STR);
			    					//sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"Calculated amount is greater than Maximum eligible amount.",progDef.getProcessId(),bnsFailRecords,progDef.getProcessType(),progDef.getActlProcDte(),progDef.getVariablePymtBonusTrigger());
			    					return;
			    				}	    				
			    			}
			    		}else{
			    			//CB:
			    			//String fMsg  ="This cancel doesn't have the retail which is DPB bonus processed";
			    			DPBCommonHelper.addIntoBonusAttachmentList(bnsFailRecords,rtlVeh,progDef,
			    					IConstants.PROC_STATUS_FAILURE,IConstants.CANCEL_DONT_HAVE_RTL,IConstants.EMPTY_STR);
			    			//sv:if not, inform that this cancel doesn't have the retail which is DPB bonus processed..
			    			//sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"This cancel doesn't have the retail which is DPB bonus processed",progDef.getProcessId(),bnsFailRecords,progDef.getProcessType(),progDef.getActlProcDte(),progDef.getVariablePymtBonusTrigger());
			    			return;
			    		}
			    		
					}else{
						specialBns.setBnsCalcAmt(amountBonus);
					}
					specialBns.setMaxBnsEligibleAmt(amountMax);
					specialBns.setAdjIndicator(IConstants.CONSTANT_N);					
					if(isDlrTrmt){
						specialBns.setStatus(IConstants.BONUS_STATUS_TERMINATED_CALCULATED);
					}else{
						specialBns.setStatus(IConstants.BONUS_STATUS_CALCULATED);
					}
							
					specialBns.setCreateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
					specialBns.setUpdateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
					specialBns.setUserId(updatedBy);
					//Unearned Performance Bonus calculation start - Kshitija
					double amtUnernd = BonusProcess.calculateUnearnedBonus(specialBns.getMaxBnsEligibleAmt(),specialBns.getBnsCalcAmt(),rtlVeh.getCarStatusCode());
					specialBns.setAmtUnernd(amtUnernd);
					//Unearned Performance Bonus calculation end - Kshitija
				  /* if(isLogUpdate){
					   updateProcessLogs(progDef.getProcessId(),
							   				progDef.getProcessType()+" bonus calculated. Email notification is pending.", 
											IConstants.PROC_STATUS_IN_PROCESS,updatedBy);
				   }*/
				    bonusInfoList.add(specialBns);
				}
			/*}else{
				Integer dppFixed = PropertyManager.getPropertyManager().getPropertyAsInteger("special.Fixed");
				BonusInfo specialBns = new BonusInfo();
				specialBns.setProcessId(progDef.getProcessId());
				specialBns.setPgmId(progDef.getProgramId());
				specialBns.setPoNumber(rtlVeh.getPoNo());
				specialBns.setDlrId(rtlVeh.getDealerId());
				specialBns.setDayId(progDef.getRtlDateId());
				double fixedValue = (double)dppFixed.intValue();
				specialBns.setBnsCalcAmt(fixedValue);
				specialBns.setMaxBnsEligibleAmt(amountMax);
				specialBns.setAdjIndicator(IConstants.CONSTANT_N);
				specialBns.setLdrBonusIndicator(IConstants.CONSTANT_N);
				specialBns.setStatus(IConstants.BONUS_STATUS_CALCULATED);		
				specialBns.setCreateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
				specialBns.setUpdateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
				specialBns.setUserId(updatedBy);			
			   if(isLogUpdate){
				   updateProcessLogs(progDef.getProcessId(),
						   				progDef.getProcessType()+" bonus calculated. Email notification is pending.", 
										IConstants.PROC_STATUS_IN_PROCESS,updatedBy);
			   }
			    bonusInfoList.add(specialBns);
			}*/
		/*}catch (TechnicalException e) {
			throw new TechnicalException("DPB.BNS.PROC.004","Exception occured while calculating Special Fixed Bonus");
		}*/
		LOGGER.exit(CLASSNAME, methodName);
	}	
	/**
	 * 
	 * @param bonusInfoList
	 * @param actualRetailBonus
	 * @param progDef
	 * @return
	 */
	public boolean isRtlExistForCanceled(List<BonusInfo> bonusInfoList,
									BonusInfo actualRetailBonus,ProgramDefinition progDef){
		boolean isRtlExist = false;
		BonusInfo rtlBns = null;		
		if(bonusInfoList!= null && bonusInfoList.size()  > 0){			
			for(Iterator<BonusInfo> itr = bonusInfoList.iterator(); itr.hasNext();){
				rtlBns = itr.next();				
				if((rtlBns.getDpbUnblkVehId().compareTo(actualRetailBonus.getDpbUnblkVehId()) == 0)
						&& (rtlBns.getPgmId() == progDef.getProgramId())){
					isRtlExist = true;
					this.setRtlBns(rtlBns);
					break;
				}else{
					rtlBns = null;
					isRtlExist = false;
				}
			}
		}
		return isRtlExist;
	}
	/**
	 * @return the rtlBns
	 */
	public BonusInfo getRtlBns() {
		return rtlBns;
	}
	/**
	 * @param rtlBns the rtlBns to set
	 */
	public void setRtlBns(BonusInfo rtlBns) {
		this.rtlBns = rtlBns;
	}	
}
