/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportDefinitionBean.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all request related to Bonus Process.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 07, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.business.view;

import java.util.List;

import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.ProcessBonus;
import com.mbusa.dpb.common.domain.ProcessLdrBonus;
import com.mbusa.dpb.common.exceptions.BusinessException;

public interface BonusProcessingBeanLocal {
	
	public List<ProcessBonus> procBonusProc()throws BusinessException;
	public List<ProcessLdrBonus> processLdrBonus()throws BusinessException;
	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId)throws BusinessException;
	public void startProcBonusDetails(int procId,String procType,String userId,boolean flag,java.sql.Date date);
}
