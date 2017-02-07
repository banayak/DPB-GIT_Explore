/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: FileProcessingDelegate.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all request related to File Processing.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 08, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.delegate;

import java.util.List;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.common.db.ConnectionFactory;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.domain.BulkSchedulerInfo;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.NetStarReport;
import com.mbusa.dpb.common.domain.FileDefinitionBean;
import com.mbusa.dpb.common.domain.ProcessInputFile;
import com.mbusa.dpb.common.domain.GeneratePaymentFile;
import com.mbusa.dpb.common.domain.GenerateReportFile;

public class FileProcessingDelegate {
	
	private LocalServiceFactory local =  new LocalServiceFactory();
	private static DPBLog LOGGER = DPBLog.getInstance(ConnectionFactory.class);
	static final private String CLASSNAME = FileProcessingDelegate.class.getName();
	
	public List<ProcessInputFile> getProcInputFileDetails()throws BusinessException,TechnicalException
	{
		List<ProcessInputFile> procInputFile=null;		
		try {			
			procInputFile =  local.getFileProcessingService().getProcInputFileDetails();			
		} catch (TechnicalException te) {
			throw new TechnicalException("",te.getMessageKey());
		}
		return procInputFile;				
	}
		
	
	public List<GeneratePaymentFile> genPayFile()throws BusinessException,TechnicalException
	{
		List<GeneratePaymentFile> genPaymentFile=null;		
		try {			
			genPaymentFile =  local.getFileProcessingService().genPayFile();
		} catch (TechnicalException te) {
			throw new TechnicalException("",te.getMessageKey());
		}
		return genPaymentFile;				
	}
	
	public List<GenerateReportFile> genReportFile() throws BusinessException,TechnicalException
	{
		List<GenerateReportFile> genReportFile=null;
		genReportFile =  local.getFileProcessingService().genReportFile();			
		return genReportFile;				
	}	
	
	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId) throws BusinessException,TechnicalException {
		List<DPBProcessLogBean> procesDetail = null;
		try {
			procesDetail = local.getFileProcessingService().getProcessLogsDeatils(procId);
		} catch (Exception e) {
		}
		return procesDetail;
	}
	
	public  void startProcFileDetails(int procId,String procType,String userId,boolean flag,java.sql.Date date) throws BusinessException,TechnicalException 
	{
			try {
			   local.getFileProcessingService().startProcFileDetails(procId,procType,userId,flag,date);
			} catch (Exception e) {
		}
	
	}


	public FileDefinitionBean fetchFileDefinition(int procId) throws TechnicalException {
		FileDefinitionBean fileDefinitionBean = null;
	try {
			fileDefinitionBean = local.getDefinitionService().fetchFileDefinition (procId);
	} catch (TechnicalException te) {
		throw new TechnicalException("",te.getMessageKey());
	}
		return fileDefinitionBean;
	}
	
	public  void startProcBonusDetails(int procId,String procType,String userId,boolean flag,java.sql.Date date) throws BusinessException,TechnicalException 
	{
			try {
			   local.getBonusProcessingService().startProcBonusDetails(procId,procType,userId,flag,date);
			} catch (Exception e) {
		}
	}


	public void getBonusProcessforToday(int procId, boolean isLdrShpBonus) {
		// TODO Auto-generated method stub
		
	}
	public  void startSchedulerManualCall(BulkSchedulerInfo bulkInfo) throws BusinessException,TechnicalException 
	{
			try {
			   local.getFileProcessingService().startSchedulerManualCall(bulkInfo);
			} catch (Exception e) {
		}
	
	}

	//Data Cleanup for process rerun start
	/**
	 * @param procDate
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	public void startCleanUp(java.sql.Date procDate) throws BusinessException, TechnicalException{
		try {
			local.getFileProcessingService().startCleanUp(procDate);//Do we need to use File_PROCESSING_JNDI or COMMON_PROCESS_JNDI shall also work
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Data Cleanup for process rerun end
}
