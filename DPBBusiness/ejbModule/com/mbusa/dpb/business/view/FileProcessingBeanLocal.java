/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: FileProcessingBeanLocal.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all request related to File Processing.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 11, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.business.view;

import java.util.List;

import com.mbusa.dpb.common.domain.BulkSchedulerInfo;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.FieldColumnMapBean;
import com.mbusa.dpb.common.domain.FileDefinitionBean;
import com.mbusa.dpb.common.domain.GeneratePaymentFile;
import com.mbusa.dpb.common.domain.GenerateReportFile;
import com.mbusa.dpb.common.domain.KpiFileProcessing;
import com.mbusa.dpb.common.domain.NetAccrualFileProcessing;
import com.mbusa.dpb.common.domain.ProcessInputFile;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.BusinessException;

public interface FileProcessingBeanLocal {
	//public void processFile(Integer processID) throws Exception;
	
	public List<ProcessInputFile> getProcInputFileDetails()throws BusinessException;
	public List<GeneratePaymentFile> genPayFile()throws BusinessException;
	public List<GenerateReportFile> genReportFile()throws BusinessException;
	public FileDefinitionBean fetchFileDefinition (int processID);
	public List<FieldColumnMapBean> getFileMappingDetails (int fileFormatId);
	public void saveVistaFileDetails (List<VistaFileProcessing> validRecords, List<VistaFileProcessing> blockedRecords);
	public List<String> saveAccuralFileDetails(List<NetAccrualFileProcessing> accuralValidRecords);
	public void saveKpiFileDetails(List<KpiFileProcessing> kpiValidRecords);

	//public ProgramDefinition retrivePaymentDefinition (int processID);
	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId) throws BusinessException;
	public  void startProcFileDetails(int procId,String procType,String userId,boolean flag,java.sql.Date date);
	public void startSchedulerManualCall(BulkSchedulerInfo bulkInfo);
	
	//For Data Cleanup for process rerun
	public void startCleanUp(java.sql.Date procDate); 
	
}
