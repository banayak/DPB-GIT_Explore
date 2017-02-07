/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: BonusCancelationProcess.java
 * Program Version			: 1.0
 * Program Description		: This class is used to To Process Bonus.
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
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.business.view.DpbCommonBeanLocal;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.BonusInfo;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.DPBCommonHelper;
import com.mbusa.dpb.common.util.DateCalenderUtil;

public class BonusCancelationProcess {
	private static DPBLog LOGGER = DPBLog.getInstance(BonusProcess.class);
	static final private String CLASSNAME = BonusProcess.class.getName();
	private LocalServiceFactory local = new LocalServiceFactory();
	private DPBCommonHelper commonHelper = new DPBCommonHelper();
	static PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
	/**
	 * 
	 * @param bonusInfo
	 * @param uId
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public void initiateBonusCancelation(List<BonusInfo> bonusInfo,String uId,Date aDate,Integer parentId) throws BusinessException,TechnicalException{
		final String methodName = "initiateBonusCancelation";
		LOGGER.enter(CLASSNAME, methodName);		 
		DpbCommonBeanLocal commonBean = null;	
		Calendar c1 = Calendar.getInstance();
		c1.setTime(aDate);		
		c1.add(Calendar.DATE, -1);
		// check terminate date as (actual date -1 )
		java.sql.Date cancelDate = new java.sql.Date(c1.getTime().getTime());	
		
    	commonBean = local.getDpbCommonService();
    	List<BonusInfo> cancelledPoList = commonBean.retrieveCancelledPoList();
    	
 		LOGGER.info("PO cancelation List:"+cancelledPoList.size());
 		if(cancelledPoList!= null && !cancelledPoList.isEmpty()){
 			doBonusCancelation(cancelledPoList,bonusInfo,uId);
 		}else{
 			Integer cProcId = PROPERTY_MANAGER.getPropertyAsInteger("cancelation.bonus.process.id");
 			commonBean.insertIntoProcessTerminateCancelationLog(commonHelper.getProcessLog(cProcId,
					"No po# found for Cancelation.",IConstants.PROC_STATUS_SUCCESS,uId),
					aDate);
 		}
    	LOGGER.exit(CLASSNAME, methodName);
	}
	
	/**
	 * 
	 * @param bonusCancelationList
	 * @param bnsInfo
	 * @param createdBy
	 */
	public void doBonusCancelation(List<BonusInfo> cancelationPoList,List<BonusInfo> bnsInfo,String createdBy) throws BusinessException,TechnicalException{
		final String methodName = "doBonusCancelation";
		LOGGER.enter(CLASSNAME, methodName);		
		/*DpbCommonBeanLocal commonBean = null;
		commonBean = local.getDpbCommonService();
		List<Integer> cancelPOProcessList  =  new ArrayList<Integer>();*/
		Iterator<BonusInfo> cancelationPoListIter = cancelationPoList.iterator();
		
		while(cancelationPoListIter.hasNext()){
			BonusInfo cancelPoInfo = cancelationPoListIter.next();
			double bnsCalcAmt = - cancelPoInfo.getBnsCalcAmt();
			cancelPoInfo.setBnsCalcAmt(bnsCalcAmt);
			cancelPoInfo.setUserId(createdBy);			
			cancelPoInfo.setCreateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
			cancelPoInfo.setUpdateTimeStamp(DateCalenderUtil.getCurrentTimestamp());
			cancelPoInfo.setStatus(IConstants.BONUS_STATUS_CALCULATED);
			cancelPoInfo.setSubProcType(IConstants.SUB_PROC_CANCELATION_PO_STATUS);
			bnsInfo.add(cancelPoInfo);
			/*if(cancelPOProcessList!= null && !cancelPOProcessList.contains(calInfo.getProcessId())){
					commonBean.insertIntoProcessLog(commonHelper.getProcessLog(calInfo.getProcessId(),"PO number:"+calInfo.getPoNumber()+" has been canceled.",IConstants.CANCELATION_PO_STATUS,createdBy));
			}*/
		}	   
		LOGGER.info("Bonus cancelation completed for size:"+cancelationPoList.size());
		LOGGER.exit(CLASSNAME, methodName);
	}
}
