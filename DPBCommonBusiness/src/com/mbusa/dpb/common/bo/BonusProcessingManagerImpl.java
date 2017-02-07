/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: BonusProcessingManagerImpl.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all request related to Bonus Processing.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 10, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.bo;


import java.util.List;

import com.mbusa.dpb.common.dao.BonusProcessingDAOImpl;
import com.mbusa.dpb.common.dao.IBonusProcessingDAO;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.ProcessBonus;
import com.mbusa.dpb.common.domain.ProcessLdrBonus;


public class BonusProcessingManagerImpl implements IBonusProcessingManager{
	IBonusProcessingDAO bonusProcessingDAO = new BonusProcessingDAOImpl();
	
	public List<ProcessBonus> procBonusProc()
	{
		List<ProcessBonus> ProcessBonus=null;	
		ProcessBonus = bonusProcessingDAO.procBonusProc();
		return ProcessBonus;
				
	}

	public List<ProcessLdrBonus> processLdrBonus()
	{
		List<ProcessLdrBonus> processLdrBonus=null;
		processLdrBonus = bonusProcessingDAO.processLdrBonus();
		return processLdrBonus;
				
	}
	
	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId){
		List<DPBProcessLogBean> procesDetail = null;
		try {			
			procesDetail = bonusProcessingDAO.getProcessLogsDeatils(procId);		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return procesDetail;
	}
}
