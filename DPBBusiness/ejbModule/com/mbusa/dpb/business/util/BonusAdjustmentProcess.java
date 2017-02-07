package com.mbusa.dpb.business.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.business.view.DpbCommonBeanLocal;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.BonusInfo;
import com.mbusa.dpb.common.domain.InvalidReason;
import com.mbusa.dpb.common.domain.KpiFile;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.DPBCommonHelper;
import com.mbusa.dpb.common.util.DateCalenderUtil;

public class BonusAdjustmentProcess {
	private static DPBLog LOGGER = DPBLog.getInstance(BonusProcess.class);
	PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
	static final private String CLASSNAME = BonusProcess.class.getName();
	private LocalServiceFactory local = new LocalServiceFactory();
	private DPBCommonHelper commonHelper = new DPBCommonHelper();
	/**
	 * 
	 * @param adjustedBnsList
	 * @param pId
	 * @param commonBean
	 * @param aDate
	 * @param userId
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public void doAdjustment(List<BonusInfo> adjustedBnsList,Integer pId,DpbCommonBeanLocal commonBean,Date aDate,
			String userId,List<InvalidReason> invalidReason) throws BusinessException,TechnicalException{
		try {
		List<KpiFile> kpiFileList = commonBean.getUpdatedKpiWithPercentageChange();	
		
		updateProcessLogs(pId,
					kpiFileList.size()+" records found for adjustment.", 
						IConstants.PROC_STATUS_IN_PROCESS,userId);
    	//RtlCalenderDefinition actulRtlDim = MasterDataLookup.getInstance().getRtlCalByDate(aDate);
	 
    	if(!kpiFileList.isEmpty()){
	    	for(Iterator<KpiFile> itr = kpiFileList.iterator();itr.hasNext();){
	    		KpiFile kpiList = (KpiFile)itr.next();
	    		String dlrId = kpiList.getDealerId();;
	    			    		
				java.sql.Date sDate = decideStartDateForadjustmentProcess(kpiList.getKpiFiscalQuarter(), String.valueOf(kpiList.getKpiYr()));
	    		java.sql.Date eDate = decideEndDateForadjustmentProcess(kpiList.getKpiFiscalQuarter(), String.valueOf(kpiList.getKpiYr()));
	    		String kpiId = String.valueOf(kpiList.getKpiId());;
	        	
	        	double updatePercentage = kpiList.getKpiPct();	        	
	        		
	        	if(updatePercentage != 0.0){	        		
	        		List<BonusInfo> listForAdjustment = commonBean.getPreviousBonusRecordsForAdjuestment(kpiId,dlrId,sDate,eDate);
	        			if(listForAdjustment!= null && listForAdjustment.size() > 0 ){
	        				
		    			doAdjustmentBonusCalculation(adjustedBnsList,listForAdjustment,updatePercentage,aDate,userId,invalidReason);
		    		}
	        	}
	    	}
	    }
    	updateProcessLogs(pId,
				"Adjustment Completed, Email notification is pending.", 
				IConstants.PROC_STATUS_IN_PROCESS,userId);
		}catch (Exception e) {
			throw new TechnicalException("DPB.ADJ.PROC.001", e.getMessage());
		}
	}	
	/**
	 * @param bonusCancelationList
	 * @param bnsInfo
	 * @param createdBy
	 */
	public void doAdjustmentBonusCalculation(List<BonusInfo> bonusadjustedList,List<BonusInfo> foradjustment,
			double adjPercentage,Date aDate,String createdBy,List<InvalidReason> inValidReason) 
						throws BusinessException,TechnicalException{
		final String methodName = "doBonusCancelation";
		LOGGER.enter(CLASSNAME, methodName);
		Iterator<BonusInfo> bonusCancelationListIter = foradjustment.iterator();
		ArrayList<Integer> processedRetailVehicleList = new ArrayList<Integer>();
		
		while(bonusCancelationListIter.hasNext()){
			
			BonusInfo adjRecord = bonusCancelationListIter.next();
			
			//sv:adjustment may be negative also.. so for a dlr with full kpi% can reduce to less KPI...
			//sv:we need to check the resultant amount that should not be exceeding the max eligible amount..
			if(!processedRetailVehicleList.contains(adjRecord.getDpbUnblkVehId())){
				processedRetailVehicleList.add(adjRecord.getDpbUnblkVehId());
				//if(adjRecord.getBnsCalcAmt() < adjRecord.getMaxBnsEligibleAmt()){
			
				//double bnsCalcAmt = (adjRecord.getTotalBnsAmt()* adjPercentage)/100 ;
				
				//sv:need to calculate all the records of the given retail vehicle (same po + dealer, i.e. same id_dpb_unblk_veh )
				double busPaid = 0.0;
				for (Iterator<BonusInfo> iterator = foradjustment.iterator(); iterator.hasNext();) {
					BonusInfo bonusInfo = (BonusInfo) iterator.next();
					if(adjRecord.getDpbUnblkVehId().equals(bonusInfo.getDpbUnblkVehId())){
						busPaid += bonusInfo.getBnsCalcAmt();
						
					}
				}
				
				double bnsCalcAmt = Math.floor(((adjRecord.getTotalBnsAmt()* adjPercentage)/100) + 0.5);
				
				
				double adjBnsAmt = bnsCalcAmt - busPaid;
				
								
				double totBns = busPaid + adjBnsAmt;
				
				
				/*adjRecord.setProcessId(adjRecord.getProcessId());
				adjRecord.setPgmId(adjRecord.getProgramId());	
				adjRecord.setPoNumber(vistaFileProcessing.getPoNo());
				adjRecord.setDlrId(vistaFileProcessing.getDealerId());*/
				//adjRecord.setDayId(progDef.getRtlDateId());
				
				if(totBns <= adjRecord.getMaxBnsEligibleAmt()){ //checking if the calculated bonus exceeds the max eligible amount
					adjRecord.setBnsCalcAmt(adjBnsAmt);
					adjRecord.setActualDate(aDate);
					adjRecord.setUserId(createdBy);			
					adjRecord.setCreateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
					adjRecord.setUpdateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
					adjRecord.setAdjIndicator(IConstants.YES);
					adjRecord.setStatus(IConstants.BONUS_STATUS_CALCULATED);
					//Unearned Performance Bonus calculation start - Kshitija
					// In case of adjustments, the unearned amount is going to be just the negative of the earned adjustment amount.
					adjRecord.setAmtUnernd(adjBnsAmt*-1);
					//Unearned Performance Bonus calculation  end - Kshitija
					
					bonusadjustedList.add(adjRecord);
				}else{
					InvalidReason reason  = new InvalidReason();
					reason.setUnBlkId(adjRecord.getDpbUnblkVehId());
					reason.setDealerId(adjRecord.getDlrId());
					reason.setProcId(adjRecord.getProcessId());
					reason.setRcStatus(IConstants.FAIL);
					reason.setPgmId(adjRecord.getPgmId());
					reason.setCode("ADJ");
					reason.setReason("DPB Application can't do adjustment because Newly calculated bonus amount crossing " +
										" max eligibility bonus amount. Bonus paid:"+adjRecord.getBnsCalcAmt()+
										":MAx Eligibility:"+adjRecord.getMaxBnsEligibleAmt()+":current adj %:"+adjPercentage+
										":Total Amt:"+adjRecord.getTotalBnsAmt());
					inValidReason.add(reason);
				}
				
			}/*else{
				InvalidReason reason  = new InvalidReason();
				reason.setUnBlkId(adjRecord.getDpbUnblkVehId());
				reason.setDealerId(adjRecord.getDlrId());
				reason.setProcId(adjRecord.getProcessId());
				reason.setRcStatus(IConstants.FAIL);
				reason.setPgmId(adjRecord.getPgmId());
				reason.setCode("ADJ");
				reason.setReason("DPB Application can't do adjustment because Paid bonus is equal " +
									" to max eligibility bonus amount. Bonus paid:"+adjRecord.getBnsCalcAmt()+
									":MAx Eligibility:"+adjRecord.getMaxBnsEligibleAmt()+
									":current adj %:"+adjPercentage);
				inValidReason.add(reason);
			}*/
		}	   
		LOGGER.info("Bonus Adjustment completed.");
		LOGGER.exit(CLASSNAME, methodName);
	}
	/**
  	 * 
  	 * @param quarter
  	 * @param year
  	 * @return
  	 */
  	public java.sql.Date decideStartDateForadjustmentProcess(String quarter, String year) 
  	{
		java.sql.Date startDate = null;
		RtlCalenderDefinition rtlDim = MasterDataLookup.getInstance().getRtlCalByQtrAndYear(quarter, year);
		java.sql.Date tempDate = rtlDim.getDteRetlQtrBeg();	
	    Calendar cYear = Calendar.getInstance();
        cYear.setTime(tempDate);
        cYear.add(Calendar.DATE, 1);
        startDate = new java.sql.Date(cYear.getTime().getTime());
		return startDate;
  	}
  	/**
  	 * @param quarter
  	 * @param year
  	 * @param isCurrentQtr
  	 * @return
  	 */
	public java.sql.Date decideEndDateForadjustmentProcess(String quarter, String year) 
  	{
		java.sql.Date eDate = null;
		RtlCalenderDefinition rtlDim = MasterDataLookup.getInstance().getRtlCalByQtrAndYear(quarter, year);
		eDate = DateCalenderUtil.getCurrentDateTime();
		
		RtlCalenderDefinition currentRtlDim = MasterDataLookup.getInstance().getRtlCalByDate(eDate);		
		String cQtr = currentRtlDim.getRetlQtrNum();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(eDate);
		String currYear = String.valueOf(cal.get(Calendar.YEAR)); 
		if(!cQtr.equalsIgnoreCase(quarter) || (cQtr.equalsIgnoreCase(quarter) &&  !currYear.equalsIgnoreCase(year))  ){ // both qtr not same then we are doing adj for previous
			java.sql.Date tempDate = rtlDim.getDteRetlQtrEnd();
			
            Calendar cYear = Calendar.getInstance();          
            cYear.setTime(tempDate);
            cYear.add(Calendar.DATE, 1);            
            eDate = new java.sql.Date(cYear.getTime().getTime());            
		}
		return eDate;
  	}
	/**
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
}
