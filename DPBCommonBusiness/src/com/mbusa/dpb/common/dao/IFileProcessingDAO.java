/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportDefinitionBean.java
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
package com.mbusa.dpb.common.dao;

import java.util.List;

import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.FieldColumnMapBean;
import com.mbusa.dpb.common.domain.NetAccrualFileProcessing;
import com.mbusa.dpb.common.domain.FileDefinitionBean;
import com.mbusa.dpb.common.domain.GeneratePaymentFile;
import com.mbusa.dpb.common.domain.GenerateReportFile;
import com.mbusa.dpb.common.domain.KpiFileProcessing;
import com.mbusa.dpb.common.domain.ProcessInputFile;
import com.mbusa.dpb.common.domain.ProgramDefinition;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
/**
 * 
 * @author SK5008848
 *
 */
public interface IFileProcessingDAO {
	/**
	 * This method is using for VISTA File Process
	 * @param vistaRecordList
	 * @return
	 */
	public void saveVistaFileDetails(
			List<VistaFileProcessing> validRecords);

	/**
	 * This method is used for get the mapping details and process the file
	 * @param fileFormatID
	 * @return
	 */
	public List<FieldColumnMapBean> getFileMappingDetails(int fileFormatID);

	/**
	 * @param kpiRecords
	 * @return
	 */
	public void saveKpiFileDetails(List<KpiFileProcessing> kpiRecords);
	/**
	 * This method is used for CoFiCo Net Accrual File Process
	 * @param netAccrualRecords
	 * @return
	 */
	public List<String> saveAccuralFileDetails(
			List<NetAccrualFileProcessing> netAccrualRecords);

	/**
	 * This method is using for fetch the data based on the File Definition
	 * @param processID
	 * @return
	 */
	public FileDefinitionBean fetchFileDefinition(int processID);
	public List<ProcessInputFile> getProcInputFileDetails();
	public List<GeneratePaymentFile> genPayFile();
	public List<GenerateReportFile> genReportFile();
	
	public void saveBlockedRecords(
			List<VistaFileProcessing> blockedRecords);
	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId);	
}
