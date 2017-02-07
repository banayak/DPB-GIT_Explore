/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: BonusProcessingBean.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all request related to Bonus Processing.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 01, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.business;

import java.util.List;

import com.mbusa.dpb.business.util.BonusProcess;
import com.mbusa.dpb.business.view.BonusProcessingBeanLocal;
import com.mbusa.dpb.common.bo.FileProcessingManagerImpl;
import com.mbusa.dpb.common.bo.IBonusProcessingManager;
import com.mbusa.dpb.common.bo.IFileProcessingManager;
import com.mbusa.dpb.common.db.ConnectionFactory;
import com.mbusa.dpb.common.factories.BOFactory;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.bo.BonusProcessingManagerImpl;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.ProcessBonus;
import com.mbusa.dpb.common.domain.ProcessLdrBonus;

import javax.ejb.Asynchronous;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class BonusProcessingBean
 */
@Stateless
@Local(BonusProcessingBeanLocal.class)
public class BonusProcessingBean implements BonusProcessingBeanLocal {

	private static DPBLog LOGGER = DPBLog.getInstance(ConnectionFactory.class);
	static final private String CLASSNAME = DefinitionBean.class.getName();
	private IBonusProcessingManager bonusProcessMgr  = (BonusProcessingManagerImpl) BOFactory.getInstance().getImplementation(IBonusProcessingManager.class);
	
    /**
     * Default constructor. 
     */
    public BonusProcessingBean() {
        // TODO Auto-generated constructor stub
    }
    
    public List<ProcessBonus> procBonusProc()
    {
    	List<ProcessBonus> ProcessBonus=null;		
    	ProcessBonus =  bonusProcessMgr.procBonusProc();
    	return ProcessBonus;
    }

    public List<ProcessLdrBonus> processLdrBonus()
    {
    	List<ProcessLdrBonus> processLdrBonus=null;
    	processLdrBonus =  bonusProcessMgr.processLdrBonus();		
    	return processLdrBonus;			
    }
    
    public List<DPBProcessLogBean> getProcessLogsDeatils(int procId) {
		List<DPBProcessLogBean> procesDetail = null;
		try {
			procesDetail = bonusProcessMgr.getProcessLogsDeatils(procId);
		} catch (Exception e) {
		}
		return procesDetail;
	}
  
   @Asynchronous
   public  void startProcBonusDetails(int procId,String procType,String userId,boolean flag,java.sql.Date date)  
   {
	   BonusProcess bProc=new BonusProcess();
	   boolean result=false;
	   result=bProc.startManualBonusProcess(Integer.valueOf(procId),procType, "M", userId, "M");	
   }
    
}
