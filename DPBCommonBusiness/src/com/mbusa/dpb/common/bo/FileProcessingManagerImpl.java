/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: FileProcessingManagerImpl.java
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
package com.mbusa.dpb.common.bo;

import java.util.List;

import com.mbusa.dpb.common.dao.FileProcessingDAOImpl;
import com.mbusa.dpb.common.dao.IFileProcessingDAO;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.FieldColumnMapBean;
import com.mbusa.dpb.common.domain.FileDefinitionBean;
import com.mbusa.dpb.common.domain.GeneratePaymentFile;
import com.mbusa.dpb.common.domain.GenerateReportFile;
import com.mbusa.dpb.common.domain.KpiFileProcessing;
import com.mbusa.dpb.common.domain.NetAccrualFileProcessing;
import com.mbusa.dpb.common.domain.ProcessInputFile;
import com.mbusa.dpb.common.domain.ProgramDefinition;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.logger.DPBLog;

/**
 * 
 * @author SK5008848
 * @version 1.0
 * 
 */
public class FileProcessingManagerImpl implements IFileProcessingManager {

	private static DPBLog LOGGER = DPBLog
			.getInstance(FileProcessingManagerImpl.class);
	static final private String CLASSNAME = FileProcessingManagerImpl.class
			.getName();
	IFileProcessingDAO daoImpl = new FileProcessingDAOImpl();


	/**
	 * 
	 * @see com.mbusa.dpb.common.bo.IFileProcessingManager#processFile(java.lang.Integer)
	 * @param processID
	 * @return
	 */
	

	public List<ProcessInputFile> getProcInputFileDetails() {
		List<ProcessInputFile> procInputFile = null;
		procInputFile = daoImpl.getProcInputFileDetails();
		return procInputFile;

	}
	
	public FileDefinitionBean fetchFileDefinition (int processID) {
    	final String methodName = "fetchFileDefinition";
		LOGGER.enter(CLASSNAME, methodName);
		FileDefinitionBean fileDefinitionBean = daoImpl.fetchFileDefinition (processID);
		LOGGER.exit(CLASSNAME, methodName);
		return fileDefinitionBean;
    }
	
	public List<FieldColumnMapBean> getFileMappingDetails (int fileFormatId) {
    	final String methodName = "getFileMappingDetails";
		LOGGER.enter(CLASSNAME, methodName);
		List<FieldColumnMapBean> mapBeanList = daoImpl.getFileMappingDetails(fileFormatId);
		LOGGER.exit(CLASSNAME, methodName);
		return mapBeanList;
    }
	
	public void saveVistaFileDetails (List<VistaFileProcessing> validRecords) {
    	final String methodName = "saveVistaFileDetails";
		LOGGER.enter(CLASSNAME, methodName);
		daoImpl.saveVistaFileDetails(validRecords);
		LOGGER.exit(CLASSNAME, methodName);
    }
	
	public void saveBlockedRecords (List<VistaFileProcessing> blockedRecords) {
    	final String methodName = "saveBlockedRecords";
		LOGGER.enter(CLASSNAME, methodName);
		daoImpl.saveBlockedRecords(blockedRecords);
		LOGGER.exit(CLASSNAME, methodName);
    }
	
	public List<String> saveAccuralFileDetails(List<NetAccrualFileProcessing> accuralValidRecords) {
		final String methodName = "saveAccuralFileDetails";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> negativeRecords = null;
		negativeRecords = daoImpl.saveAccuralFileDetails(accuralValidRecords);
		LOGGER.exit(CLASSNAME, methodName);
		return negativeRecords;
	}
	
	public void saveKpiFileDetails(List<KpiFileProcessing> kpiValidRecords) {
		final String methodName = "saveKpiFileDetails";
		LOGGER.enter(CLASSNAME, methodName);
		daoImpl.saveKpiFileDetails(kpiValidRecords);
		LOGGER.exit(CLASSNAME, methodName);
	}

	public List<GeneratePaymentFile> genPayFile() {
		List<GeneratePaymentFile> genPaymentFile = null;
		genPaymentFile = daoImpl.genPayFile();
		return genPaymentFile;

	}

	public List<GenerateReportFile> genReportFile() {
		List<GenerateReportFile> genReportFile = null;
		genReportFile = daoImpl.genReportFile();
		return genReportFile;
	}
	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId){
		List<DPBProcessLogBean> procesDetail = null;		
			procesDetail = daoImpl.getProcessLogsDeatils(procId);		
		return procesDetail;
	}
}
