/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: KpiCalculation.java
 * Program Version			: 1.0
 * Program Description		:  This class is calculate bonus based on KPI and Fixed or variable % value.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Sep 11, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.business.util;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.business.view.DpbCommonBeanLocal;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.BonusInfo;
import com.mbusa.dpb.common.domain.InvalidReason;
import com.mbusa.dpb.common.domain.KpiFile;
import com.mbusa.dpb.common.domain.ProgramDefinition;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DPBCommonHelper;
import com.mbusa.dpb.common.util.DateCalenderUtil;

public class KpiCalculation {
	private static DPBLog LOGGER = DPBLog.getInstance(BonusProcess.class);	
	static final private String CLASSNAME = BonusProcess.class.getName();
	private LocalServiceFactory local = new LocalServiceFactory();
	DPBCommonHelper commonHelper = new DPBCommonHelper();
	private BonusInfo rtlBns = null;
	
	/**
	 * 
	 * 
	 * @param bonusInfoList
	 * @param progDef
	 * @param vistaFileProcessing
	 * @param dInfo
	 * @param actualDate
	 * @param userId
	 * @param amgPerfDlrs
	 * @param amgEliteDlrs
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public void applyGeneralPgm(List<BonusInfo> bonusInfoList,ProgramDefinition progDef, 
			VistaFileProcessing vistaFileProcessing, String dInfo,Date edate,String userId,
			boolean isFullBns,boolean isLogUpdate,boolean isTerminated,List<InvalidReason> failureReason, 
			List<String> amgPerfDlrs, List<String> amgEliteDlrs ) throws BusinessException,TechnicalException{
		final String methodName = "applyGeneralPgm";
		LOGGER.enter(CLASSNAME, methodName);
		
		RtlCalenderDefinition rtlDim = MasterDataLookup.getInstance().getRtlCalByDate(edate);
		
		int year =  rtlDim.getRetlYearNum()!= null ? Integer.parseInt(rtlDim.getRetlYearNum()): 0; 
		String quarter = rtlDim.getRetlQtrNum()!= null ? rtlDim.getRetlQtrNum(): "0";		
		if(isLogUpdate){
			updateProcessLogs(progDef.getProcessId(),
					progDef.getProcessType()+" bonus calculation in progress.", 
					IConstants.PROC_STATUS_IN_PROCESS,userId);
		}
		if(IConstants.FIXED_BONUS_PROCESS.equalsIgnoreCase(progDef.getProcessType())){			
			applyFixedBonus(bonusInfoList, progDef,quarter,year, vistaFileProcessing, dInfo,userId,isFullBns,isLogUpdate,isTerminated,failureReason);
		}else if(IConstants.VARIABLE_BONUS_PROCESS.equalsIgnoreCase(progDef.getProcessType())){			
			applyVariableBonus(bonusInfoList, progDef,quarter,year, vistaFileProcessing, dInfo,userId,isFullBns,isLogUpdate,isTerminated,failureReason, amgPerfDlrs, amgEliteDlrs);
		}
		LOGGER.exit(CLASSNAME, methodName);
	}
	/**
	 * 
	 * @param bonusInfoList
	 * @param progDef
	 * @param vistaFileProcessing
	 * @param dInfo
	 * @param createdBy
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public void applyVariableBonus(List<BonusInfo> bonusInfoList,ProgramDefinition progDef,String period,
			int year, VistaFileProcessing vistaFileProcessing, String dInfo,String createdBy,
			boolean isFullBonus,boolean isLogUpdate,boolean isTerminate,List<InvalidReason> bnsFailRecords,
			List<String> amgPerfDlrs, List<String> amgEliteDlrs) throws BusinessException,TechnicalException{
		final String methodName = "applyFixedOrVariablePayment";
		LOGGER.enter(CLASSNAME, methodName);		   
		BonusInfo variableBns = new BonusInfo();		
		boolean  isKpiExist = false;
		boolean  isKpiCalculated = false;
		double totalMsrpAmt = vistaFileProcessing.getMsrpBaseAmount() + vistaFileProcessing.getMsrpTotAmtAcsry();
		
		if(isFullBonus){			
			double minBonus = Math.floor(((progDef.getVariablePayment() * totalMsrpAmt)/100) + 0.5);
			double maxBonusEligible = Math.floor((progDef.getVariablePayment() * totalMsrpAmt)/100 + 0.5);		
			variableBns.setProcessId(progDef.getProcessId());
			variableBns.setPgmId(progDef.getProgramId());	
			variableBns.setDpbUnblkVehId(vistaFileProcessing.getUnBlkVehId());
			variableBns.setDlrId(vistaFileProcessing.getDealerId());
			variableBns.setActualDate(progDef.getActlProcDte());
			//variableBns.setBnsCalcAmt(minBonus);
			boolean isRetailCancel = false;
			double totalCalcAmtForPgm = 0;
			if(IConstants.RETAIL_CANCEL_CODE.equalsIgnoreCase(vistaFileProcessing.getCarStatusCode())){
				//sv:get the original retail id,
				DpbCommonBeanLocal commonBean = null; 
		    	commonBean = local.getDpbCommonService();
				List<BonusInfo> listForCancel = commonBean.getPreviousBonusRecordsForCancel(vistaFileProcessing.getPoNo(),vistaFileProcessing.getDealerId(),vistaFileProcessing.getRetailDate(),vistaFileProcessing.getRetailTime());
	    		if(listForCancel!= null && listForCancel.size() > 0 ){
	    			BonusInfo actualRetailBonus = listForCancel.get(0); // Note that only the ID_DPB_UNBLK_VEH is populate in this Object.
	    			
	    			List<BonusInfo> amountForCancel =commonBean.getBonusDetailsForCancel(""+actualRetailBonus.getDpbUnblkVehId(), progDef.getProgramId());
	    			if(amountForCancel!= null && amountForCancel.size() > 0 ){
	        			//doAdjustmentBonusCalculation(adjustedBnsList,listForAdjustment,updatePercentage,aDate,userId,invalidReason);
	        			//sv:if the retail is calculated with DPB bonus- calculate the whole paid amount and revert back....
	    				BonusInfo totalBonusForPgm = amountForCancel.get(0); 
	    				double cancelAmt = totalBonusForPgm.getBnsCalcAmt();
	    				totalCalcAmtForPgm = totalBonusForPgm.getMaxBnsEligibleAmt();
	    				if(cancelAmt <= totalCalcAmtForPgm){
	    					isRetailCancel = true;
	    					variableBns.setBnsCalcAmt(-cancelAmt);
	    				}
	    				else{
	    					//CB: Added into common method and Single mail should be trigger for one bonus process.
	    					DPBCommonHelper.addIntoBonusAttachmentList(bnsFailRecords,vistaFileProcessing,
					    							progDef,IConstants.PROC_STATUS_FAILURE,
					    							IConstants.BNS_MSG_CALC_AMT_GREATER_THAN_MAX_ELIGIBILITY,
					    							IConstants.EMPTY_STR);
	    					//sv:if calculated amount is > max eligible amount throw e-mail
	    					//sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"Calculated amount is greater than Maximum eligible amount",progDef.getProcessId(),bnsFailRecords,progDef.getProcessType(),progDef.getActlProcDte(),progDef.getVariablePymtBonusTrigger());
	    					return;
	    				}
	    			}else {
	    				if(isRtlExistForCanceled(bonusInfoList,actualRetailBonus,progDef)){
	    			
							double cancelAmt = rtlBns.getBnsCalcAmt();	    				
		    				totalCalcAmtForPgm = rtlBns.getMaxBnsEligibleAmt();
		    				if(cancelAmt <= totalCalcAmtForPgm){
		    					isRetailCancel = true;
		    					variableBns.setBnsCalcAmt(-cancelAmt);
		    				}
		    				else{
		    					DPBCommonHelper.addIntoBonusAttachmentList(bnsFailRecords,vistaFileProcessing,
		    							progDef,IConstants.PROC_STATUS_FAILURE,
		    							IConstants.BNS_MSG_CALC_AMT_GREATER_THAN_MAX_ELIGIBILITY,
		    							IConstants.EMPTY_STR);
		    					//sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"Calculated amount is greater than Maximum eligible amount.",progDef.getProcessId(),bnsFailRecords,progDef.getProcessType(),progDef.getActlProcDte(),progDef.getVariablePymtBonusTrigger());
		    					return;
		    				} 
	    				} else {
	    					isRetailCancel = true;
	    				}
	    			}
	    		}else{
	    			//CB: Added into common method and Single mail should be trigger for one bonus process.	    					
	    			DPBCommonHelper.addIntoBonusAttachmentList(bnsFailRecords,vistaFileProcessing,progDef,
													    					IConstants.PROC_STATUS_FAILURE,
													    					IConstants.CANCEL_DONT_HAVE_RTL,
													    					IConstants.EMPTY_STR);
	    			//sv:if not, inform that this cancel doesn't have the retail which is DPB bonus processed..
	    			//sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"This cancel doesn't have the retail which is DPB bonus processed",progDef.getProcessId(),bnsFailRecords,progDef.getProcessType(),progDef.getActlProcDte(),progDef.getVariablePymtBonusTrigger());
	    			return;
	    		}

			}else{
				variableBns.setBnsCalcAmt(minBonus);
			}
			
			if (isRetailCancel) {
				variableBns.setMaxBnsEligibleAmt(totalCalcAmtForPgm);
			} else {
				variableBns.setMaxBnsEligibleAmt(maxBonusEligible);	
			}
			variableBns.setAdjIndicator(IConstants.CONSTANT_N);			
			if(isTerminate){
				variableBns.setStatus(IConstants.BONUS_STATUS_TERMINATED_CALCULATED);
			}else{
				variableBns.setStatus(IConstants.BONUS_STATUS_CALCULATED);
			}		
			variableBns.setCreateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
			variableBns.setUpdateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
			variableBns.setUserId(createdBy);			
			bonusInfoList.add(variableBns);
		}else{				
			int pgmKpi = progDef.getKpiId();
			KpiFile kpiFile = this.getKpiPercentageforDlrYearQtrKpiId(dInfo, period, year,pgmKpi);
			double kpiMaxPct = 0;
			if(kpiFile != null){
				
				// AMG Changes - START
				boolean shouldProcess = true;
				String amgInd = vistaFileProcessing.getIndAmg();
				int progId = progDef.getProgramId();	
				int kpiId = progDef.getKpiId();
				
				LOGGER.info("In variable Bonus : amgInd: " + amgInd + " progId: "+progId+ " pgmKpi: " + pgmKpi);
				
				// If the kpiId is 50000, then it is for AMG bonus. 
				// So only applicable vehicle should get the AMG bonus.
				if (kpiId == 50000) {
					shouldProcess = false;
					if ( amgInd != null && amgInd.equalsIgnoreCase(IConstants.YES) ) {
						// check if the dealer is an AMG dealer or not. If yes, process it. Else NO.
						String dealerId = vistaFileProcessing.getDealerId();
						if (dealerId != null && !dealerId.isEmpty()) {
							if (progId == 142) {
								//Then AMG Performance Center dealers
								//The amgPerfDlrs should be coming from BonusProcess
								if (amgPerfDlrs != null && !amgPerfDlrs.isEmpty() ) {
									shouldProcess = amgPerfDlrs.contains(dealerId);
								}
							} else if (progId == 143) {
							//Then AMG Elite Center dealers
								//The amgEliteDlrs should be coming from BonusProcess
								if (amgEliteDlrs != null && !amgEliteDlrs.isEmpty() ) {
									shouldProcess = amgEliteDlrs.contains(dealerId);
								}
							}
						}
					}
				} 
				
				if (shouldProcess) {
					// Fetching the KPI Percentage value passed in the KPI file for AMG dealer.
					//if (kpiId == 50000) {
						//KpiFile kpiFile = this.getKpiPercentageforDlrYearQtrKpiId(dInfo, period, year,kpiId);
						//if(kpiFile != null) {
							//double kpiPct = kpiFile.getKpiPct();
							//LOGGER.info("kpiPct : " + kpiPct + " kpiMaxPct : " + kpiMaxPct);
							//kpiMaxPct = kpiPct;
						//}
					//}
					// AMG Changes - END
							
					double kpiPct = kpiFile.getKpiPct();
					kpiMaxPct = progDef.getVariablePayment();
					LOGGER.info("In variable Bonus : kpiPct: " + kpiPct+" kpiMaxPct: " + kpiMaxPct);
					isKpiExist = true;
					boolean isRetailCancel = false;
					if((kpiPct >= 0) && (kpiPct <= kpiMaxPct)){
						double minBonus = 0;
						if(kpiPct > 0){
							minBonus = Math.floor(((kpiPct * totalMsrpAmt)/100) + 0.5);
						}
						double maxBonusEligible = Math.floor(((kpiMaxPct * totalMsrpAmt)/100) + 0.5);
						LOGGER.info("In variable Bonus :minBonus: "+minBonus+" maxBonusEligible: "+maxBonusEligible);
						variableBns.setProcessId(progDef.getProcessId());
						variableBns.setPgmId(progDef.getProgramId());	
						variableBns.setDpbUnblkVehId(vistaFileProcessing.getUnBlkVehId());
						variableBns.setDlrId(vistaFileProcessing.getDealerId());
						LOGGER.info("In variable Bonus :vistaFileProcessing.getDealerId(): " + vistaFileProcessing.getDealerId());
						variableBns.setActualDate(progDef.getActlProcDte());
						double totalCalcAmtForPgm = 0;
						if(IConstants.RETAIL_CANCEL_CODE.equalsIgnoreCase(vistaFileProcessing.getCarStatusCode())){
							//sv:get the original retail id,
							DpbCommonBeanLocal commonBean = null; 
					    	commonBean = local.getDpbCommonService();
							List<BonusInfo> listForCancel = commonBean.getPreviousBonusRecordsForCancel(vistaFileProcessing.getPoNo(),vistaFileProcessing.getDealerId(),vistaFileProcessing.getRetailDate(),vistaFileProcessing.getRetailTime());
				    		if(listForCancel!= null && listForCancel.size() > 0 ){
				    			BonusInfo actualRetailBonus = listForCancel.get(0); // Note that only the ID_DPB_UNBLK_VEH is populate in this Object.
				    			
				    			List<BonusInfo> amountForCancel =commonBean.getBonusDetailsForCancel(""+actualRetailBonus.getDpbUnblkVehId(), progDef.getProgramId());
				    			if(amountForCancel!= null && amountForCancel.size() > 0 ){
				        			//doAdjustmentBonusCalculation(adjustedBnsList,listForAdjustment,updatePercentage,aDate,userId,invalidReason);
				        			//sv:if the retail is calculated with DPB bonus- calculate the whole paid amount and revert back....
				    				BonusInfo totalBonusForPgm = amountForCancel.get(0); 
				    				double cancelAmt = totalBonusForPgm.getBnsCalcAmt();
				    				totalCalcAmtForPgm = totalBonusForPgm.getMaxBnsEligibleAmt();
				    				if(cancelAmt <= totalCalcAmtForPgm){
				    					isRetailCancel = true;
				    					variableBns.setBnsCalcAmt(-cancelAmt);
				    				}
				    				else{
				    					//CB: Added into common method and Single mail should be trigger for one bonus process.
				    					DPBCommonHelper.addIntoBonusAttachmentList(bnsFailRecords,vistaFileProcessing,
								    							progDef,IConstants.PROC_STATUS_FAILURE,
								    							IConstants.BNS_MSG_CALC_AMT_GREATER_THAN_MAX_ELIGIBILITY,
								    							IConstants.EMPTY_STR);
						    			//sv:if calculated amount is > max eligible amount throw e-mail
						    			//sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,,progDef.getProcessId(),bnsFailRecords,progDef.getProcessType(),progDef.getActlProcDte(),progDef.getVariablePymtBonusTrigger());
				    					return;
				    				}
				    			}else {
				    				if(isRtlExistForCanceled(bonusInfoList,actualRetailBonus,progDef)){
				    			
				    					double cancelAmt = rtlBns.getBnsCalcAmt();	    				
					    				totalCalcAmtForPgm = rtlBns.getMaxBnsEligibleAmt();
					    				if(cancelAmt <= totalCalcAmtForPgm){
					    					isRetailCancel = true;
					    					variableBns.setBnsCalcAmt(-cancelAmt);
					    				}
					    				else{	
					    					//CB: Added into common method and Single mail should be trigger for one bonus process.
					    					DPBCommonHelper.addIntoBonusAttachmentList(bnsFailRecords,vistaFileProcessing,
									    							progDef,IConstants.PROC_STATUS_FAILURE,
									    							IConstants.BNS_MSG_CALC_AMT_GREATER_THAN_MAX_ELIGIBILITY,
									    							IConstants.EMPTY_STR);			    					
					    					return;
					    				}	    
				    				} else {
				    					isRetailCancel = true;
				    				}
				    			}
				    		}else{
				    			//CB: Added into common method and Single mail should be trigger for one bonus process.	    					
				    			DPBCommonHelper.addIntoBonusAttachmentList(bnsFailRecords,vistaFileProcessing,progDef,
																    					IConstants.PROC_STATUS_FAILURE,
																    					IConstants.CANCEL_DONT_HAVE_RTL,
																    					IConstants.EMPTY_STR);
				    			
				    			//sv:if not, inform that this cancel doesn't have the retail which is DPB bonus processed..
				    			//sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"This cancel doesn't have the retail which is DPB bonus processed",progDef.getProcessId(),bnsFailRecords,progDef.getProcessType(),progDef.getActlProcDte(),progDef.getVariablePymtBonusTrigger());
				    			return;
				    		}
						}else{
							variableBns.setBnsCalcAmt(minBonus);
						}
						
						if(isRetailCancel) {
							variableBns.setMaxBnsEligibleAmt(totalCalcAmtForPgm);
						} else {
							variableBns.setMaxBnsEligibleAmt(maxBonusEligible);
						}
						variableBns.setAdjIndicator(IConstants.CONSTANT_N);						
						if(isTerminate){
							variableBns.setStatus(IConstants.BONUS_STATUS_TERMINATED_CALCULATED);
						}else{
							variableBns.setStatus(IConstants.BONUS_STATUS_CALCULATED);
						}					
						variableBns.setCreateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
						variableBns.setUpdateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
						variableBns.setUserId(createdBy);					
						isKpiCalculated = true;							  
					}			
					LOGGER.info("kpi:"+pgmKpi+":Dealer:"+dInfo+":PO:"+vistaFileProcessing.getPoNo()+":R Day:"+vistaFileProcessing.getRetailDate()+"PGM:"+progDef.getProgramId()+":isKpiExist:"+isKpiExist);
					//Unearned Performance Bonus calculation start - Kshitija
					double amtUnernd = BonusProcess.calculateUnearnedBonus(variableBns.getMaxBnsEligibleAmt(),variableBns.getBnsCalcAmt(),vistaFileProcessing.getCarStatusCode());
					variableBns.setAmtUnernd(amtUnernd);
					//Unearned Performance Bonus calculation end - Kshitija
					if(isKpiExist){
						if(isKpiCalculated){
						bonusInfoList.add(variableBns);
						}
					}else{
						String fMsg  = "Assign KPI missing in extract file/table. Qtr:"+period+":Year:"+year;
						DPBCommonHelper.addIntoBonusAttachmentList(bnsFailRecords,vistaFileProcessing,progDef,
								IConstants.PROC_STATUS_FAILURE,fMsg,String.valueOf(pgmKpi));					
					}
				}
			}else{
				String fMsg  = "KPI is missing in extract file for given dealer.Qtr:"+period+":Year:"+year;
				DPBCommonHelper.addIntoBonusAttachmentList(bnsFailRecords,vistaFileProcessing,progDef,
								IConstants.PROC_STATUS_FAILURE,fMsg,String.valueOf(pgmKpi));				
				//If the dealer is not present in the KPI extract we are getting into this loop. We should add it as a message and send it as email on success/failure.
				//throw new BusinessException("DPB.BNS.KPI.004","Kpi list is missing for Dealer"+dInfo);
				
				/*variableBns.setProcessId(progDef.getProcessId());
				variableBns.setPgmId(progDef.getProgramId());	
				variableBns.setDpbUnblkVehId(vistaFileProcessing.getUnBlkVehId());
				variableBns.setDlrId(vistaFileProcessing.getDealerId());
				variableBns.setActualDate(progDef.getActlProcDte());
				
				double minBonus = Math.floor(((progDef.getVariablePayment() * totalMsrpAmt)/100) + 0.5);
				double maxBonusEligible = Math.floor((progDef.getVariablePayment() * totalMsrpAmt)/100 + 0.5);	
				
				variableBns.setBnsCalcAmt(minBonus);
				variableBns.setMaxBnsEligibleAmt(maxBonusEligible);			
				variableBns.setAdjIndicator(IConstants.CONSTANT_N);		
				
				if(isTerminate){
					variableBns.setStatus(IConstants.BONUS_STATUS_TERMINATED_CALCULATED);
				}else{
					variableBns.setStatus(IConstants.BONUS_STATUS_CALCULATED);
				}		
				variableBns.setCreateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
				variableBns.setUpdateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
				variableBns.setUserId(createdBy);			
				bonusInfoList.add(variableBns);*/
				
			}
		}
		
		LOGGER.exit(CLASSNAME, methodName);
	}

	/**
	 * 
	 * @param bonusInfoList
	 * @param progDef
	 * @param period
	 * @param year
	 * @param vistaFileProcessing
	 * @param dInfo
	 * @param createdBy
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public void applyFixedBonus(List<BonusInfo> bonusInfoList,ProgramDefinition progDef,String period, int year, VistaFileProcessing vistaFileProcessing, String dInfo,String createdBy,boolean isFullBonus,boolean isLogUpdate,boolean isTerminate,List<InvalidReason> failureReason) throws BusinessException,TechnicalException{
		final String methodName = "applyKpi";
		LOGGER.enter(CLASSNAME, methodName);
		BonusInfo variableBns = new BonusInfo();
		double kpiMaxPct = 0;
		kpiMaxPct = progDef.getFixedPayment();
		double totalMsrpAmt = vistaFileProcessing.getMsrpBaseAmount() + vistaFileProcessing.getMsrpTotAmtAcsry();
		double maxBonusEligible = Math.floor(((kpiMaxPct * totalMsrpAmt)/100) + 0.5);
		
		variableBns.setProcessId(progDef.getProcessId());
		
		variableBns.setPgmId(progDef.getProgramId());	
		variableBns.setDpbUnblkVehId(vistaFileProcessing.getUnBlkVehId());
		variableBns.setDlrId(vistaFileProcessing.getDealerId());
		variableBns.setActualDate(progDef.getActlProcDte());
		//variableBns.setBnsCalcAmt(maxBonusEligible);		
		boolean isRetailCancel = false;
		double totalCalcAmtForPgm = 0;
		if(IConstants.RETAIL_CANCEL_CODE.equalsIgnoreCase(vistaFileProcessing.getCarStatusCode())){
			//sv:get the original retail id,
			DpbCommonBeanLocal commonBean = null;			
	    	commonBean = local.getDpbCommonService();
			List<BonusInfo> listForCancel = commonBean.getPreviousBonusRecordsForCancel(vistaFileProcessing.getPoNo(),vistaFileProcessing.getDealerId(),vistaFileProcessing.getRetailDate(),vistaFileProcessing.getRetailTime());
    		if(listForCancel!= null && listForCancel.size() > 0 ){
    			BonusInfo actualRetailBonus = listForCancel.get(0); // Note that only the ID_DPB_UNBLK_VEH is populate in this Object.
    			
    			List<BonusInfo> amountForCancel =commonBean.getBonusDetailsForCancel(""+actualRetailBonus.getDpbUnblkVehId(), progDef.getProgramId());
    			if(amountForCancel!= null && amountForCancel.size() > 0 ){
        			//doAdjustmentBonusCalculation(adjustedBnsList,listForAdjustment,updatePercentage,aDate,userId,invalidReason);
        			//sv:if the retail is calculated with DPB bonus- calculate the whole paid amount and revert back....
    				BonusInfo totalBonusForPgm = amountForCancel.get(0); 
    				double cancelAmt = totalBonusForPgm.getBnsCalcAmt();
    				totalCalcAmtForPgm = totalBonusForPgm.getMaxBnsEligibleAmt();
    				if(cancelAmt <= totalCalcAmtForPgm){
    					isRetailCancel = true;
    					variableBns.setBnsCalcAmt(-cancelAmt);
    				}
    				else{
    					//CB: Added into common method and Single mail should be trigger for one bonus process.
    					DPBCommonHelper.addIntoBonusAttachmentList(failureReason,vistaFileProcessing,
				    							progDef,IConstants.PROC_STATUS_FAILURE,
				    							IConstants.BNS_MSG_CALC_AMT_GREATER_THAN_MAX_ELIGIBILITY,
				    							IConstants.EMPTY_STR);
    					//sv:if calculated amount is > max eligible amount throw e-mail
    				//	sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"Calculated amount is greater than Maximum eligible amount",progDef.getProcessId(),failureReason,progDef.getProcessType(),progDef.getActlProcDte(),progDef.getVariablePymtBonusTrigger());
    					return;
    				}
    			} else {
    				if(isRtlExistForCanceled(bonusInfoList,actualRetailBonus,progDef)){
    			
						double cancelAmt = rtlBns.getBnsCalcAmt();	    				
	    				totalCalcAmtForPgm = rtlBns.getMaxBnsEligibleAmt();
	    				if(cancelAmt <= totalCalcAmtForPgm){
	    					isRetailCancel = true;
	    					variableBns.setBnsCalcAmt(-cancelAmt);
	    				}
	    				else{
	    					DPBCommonHelper.addIntoBonusAttachmentList(failureReason,vistaFileProcessing,
	    							progDef,IConstants.PROC_STATUS_FAILURE,
	    							IConstants.BNS_MSG_CALC_AMT_GREATER_THAN_MAX_ELIGIBILITY,
	    							IConstants.EMPTY_STR);
	    					//sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"Calculated amount is greater than Maximum eligible amount.",progDef.getProcessId(),bnsFailRecords,progDef.getProcessType(),progDef.getActlProcDte(),progDef.getVariablePymtBonusTrigger());
	    					return;
	    				}	
    				} else {
    					isRetailCancel = true;
    				}
    			}
    		}else{
    			//CB: Added into common method and Single mail should be trigger for one bonus process.	    					
    			DPBCommonHelper.addIntoBonusAttachmentList(failureReason,vistaFileProcessing,progDef,
												    					IConstants.PROC_STATUS_FAILURE,
												    					IConstants.CANCEL_DONT_HAVE_RTL,
												    					IConstants.EMPTY_STR);
    			//sv:if not, inform that this cancel doesn't have the retail which is DPB bonus processed..
    			//sendBonusNotificationMail(IConstants.PROC_STATUS_FAILURE,"This cancel doesn't have the retail which is DPB bonus processed",progDef.getProcessId(),failureReason,progDef.getProcessType(),progDef.getActlProcDte(),progDef.getVariablePymtBonusTrigger());
    			return;
    		}
				
			
			
		}else{
			variableBns.setBnsCalcAmt(maxBonusEligible);
		}
		
		if(isRetailCancel) {
			variableBns.setMaxBnsEligibleAmt(totalCalcAmtForPgm);
		} else {
			variableBns.setMaxBnsEligibleAmt(maxBonusEligible);
		}
		variableBns.setAdjIndicator(IConstants.CONSTANT_N);		
		if(isTerminate){
			variableBns.setStatus(IConstants.BONUS_STATUS_TERMINATED_CALCULATED);
		}else{
			variableBns.setStatus(IConstants.BONUS_STATUS_CALCULATED);
		}
		//Unearned Performance Bonus calculation start - Kshitija
		double amtUnernd = BonusProcess.calculateUnearnedBonus(variableBns.getMaxBnsEligibleAmt(),variableBns.getBnsCalcAmt(),vistaFileProcessing.getCarStatusCode());
		variableBns.setAmtUnernd(amtUnernd);
		//Unearned Performance Bonus calculation end - Kshitija
		
		//variableBns.setParentPgmId(progDef.getParentPgmId() != null ? progDef.getParentPgmId() : progDef.getProgramId());
		variableBns.setCreateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
		variableBns.setUpdateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
		variableBns.setUserId(createdBy);
		/*if(isLogUpdate){
			updateProcessLogs(progDef.getProcessId(),
				progDef.getProcessType()+" bonus calculated. Email notification is pending.", 
				IConstants.PROC_STATUS_IN_PROCESS,createdBy);
		}*/
		bonusInfoList.add(variableBns);
		
		LOGGER.exit(CLASSNAME, methodName);
		
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
	 * 
	 * @param period
	 * @param year
	 * @param kpiId
	 * @return
	 */
	public KpiFile getKpiPercentageforDlrYearQtrKpiId(String dlrId,String qtr, int year, int kpiId) {
		final String methodName = "getDealerList";
		LOGGER.enter(CLASSNAME, methodName);
		List<KpiFile> kpiExtractList = null;
		KpiFile dlrKpi = null;
		try {
			kpiExtractList = MasterDataLookup.getInstance().getKpiPercentageforYearQtrKpiId(qtr, year);
			if(kpiExtractList != null && !kpiExtractList.isEmpty()){
				Iterator<KpiFile> kpiFileListIter = kpiExtractList.iterator();
				while(kpiFileListIter.hasNext()){					
					dlrKpi = kpiFileListIter.next();
					if(dlrKpi!= null && dlrKpi.getDealerId().equalsIgnoreCase(dlrId)
							&& dlrKpi.getKpiYr() == year 
							&& dlrKpi.getKpiFiscalQuarter().equalsIgnoreCase(qtr)
							&& dlrKpi.getKpiId() == kpiId){
						break;
					}else{
						dlrKpi = null;
					}
				}
			}else{
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return dlrKpi;
	}
	
	/*public void sendBonusNotificationMail(String pStatus, String msg,Integer pId,List<InvalidReason> failList,
			String pType, java.sql.Date procActlDate, String trigger ) {
	    final String methodName = "sendProcessNotificationMail";
        LOGGER.enter(CLASSNAME, methodName);
         try{  
        	 String pDesc = MasterDataLookup.getInstance().getProcessDesc(pType);
	          SendMailDTO sendMailDTO = new SendMailDTO();
	          sendMailDTO.setFrom(PropertyManager.getPropertyManager().getPropertyAsString("bonus.process.email.fromMail"));
	          sendMailDTO.addTORecipient(PropertyManager.getPropertyManager().getPropertyAsString("bonus.process.email.toRecipient"));
	          if(IConstants.PROC_STATUS_SUCCESS.equalsIgnoreCase(pStatus)){
	        	  String pSub = PropertyManager.getPropertyManager().getPropertyAsString("bonus.process.success.email.subject");
	        	  String subject = MessageFormat.format(pSub, new Object[]{trigger});
		          sendMailDTO.setSubject(subject);
		          String pCnt = PropertyManager.getPropertyManager().getPropertyAsString("bonus.process.success.email.content");
		          String cnt = MessageFormat.format(pCnt,new Object[]{pDesc,DateCalenderUtil.getDateInGivenFormat(procActlDate,IConstants.DATE_FORMAT_MMDDYYYY_WITH_SLASH),pId});
		          sendMailDTO.setContent(cnt);
	          }else{		        
		          String subject = MessageFormat.format(PropertyManager.getPropertyManager().getPropertyAsString("bonus.process.fail.email.subject"),new Object[]{trigger});
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
	}	*/
	//VBIgnoreList
	/**
	 * 
	 * @param procType
	 * @param pId
	 * @return
	 */
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
