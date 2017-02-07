/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: BonusProcessingDelegate.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all request related to report definition.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 07, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.delegate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.common.db.ConnectionFactory;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.ProcessBonus;
import com.mbusa.dpb.common.domain.ProcessLdrBonus;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;

public class BonusProcessingDelegate {

	private LocalServiceFactory local =  new LocalServiceFactory();
	private static DPBLog LOGGER = DPBLog.getInstance(ConnectionFactory.class);
	static final private String CLASSNAME = BonusProcessingDelegate.class.getName();
	
	public List<ProcessBonus> procBonusProc()throws BusinessException,TechnicalException
	{
		List<ProcessBonus> ProcessBonus=null;		
		try {			
			LOGGER.enter(CLASSNAME, "procBonusProc");
			ProcessBonus =  local.getBonusProcessingService().procBonusProc();
		} catch (TechnicalException te) {
			throw new TechnicalException("",te.getMessageKey());
		}
		LOGGER.exit(CLASSNAME, "procBonusProc");
		return ProcessBonus;
	}

	public List<ProcessLdrBonus> processLdrBonus()throws BusinessException,TechnicalException
	{
		List<ProcessLdrBonus> processLdrBonus=null;
		try {
			LOGGER.enter(CLASSNAME, "processLdrBonus");
			processLdrBonus =  local.getBonusProcessingService().processLdrBonus();		
		} catch (TechnicalException te) {
			throw new TechnicalException("",te.getMessageKey());
		}
		LOGGER.exit(CLASSNAME, "processLdrBonus");
		return processLdrBonus;			
	}
	
	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId) throws BusinessException,TechnicalException {
		List<DPBProcessLogBean> procesDetail = null;
		try {
			procesDetail = local.getBonusProcessingService().getProcessLogsDeatils(procId);
		} catch (Exception e) {
		}
		return procesDetail;
	}
	
	public  void startProcBonusDetails(int procId,String procType,String userId,boolean flag,java.sql.Date date) throws BusinessException,TechnicalException 
	{
			try {
			   local.getBonusProcessingService().startProcBonusDetails(procId,procType,userId,flag,date);
			} catch (Exception e) {
		}
	}
	
	public void performBonusReprocess(int processId, String userId,String processType, boolean ldrShpBonusProcess) throws TechnicalException {
		final String methodName = "performBonusReprocess";
		LOGGER.enter(CLASSNAME, methodName);
		local.getDefinitionService().performBonusReprocess(processId, userId, processType, ldrShpBonusProcess);
		LOGGER.exit(CLASSNAME, methodName);
	}
}
