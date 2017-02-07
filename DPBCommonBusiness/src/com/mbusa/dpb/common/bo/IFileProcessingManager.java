/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: IFileProcessingManager.java
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


public interface IFileProcessingManager {
	
	public List<ProcessInputFile> getProcInputFileDetails() ;
	public List<GeneratePaymentFile> genPayFile() ;
	public List<GenerateReportFile> genReportFile() ;
	public FileDefinitionBean fetchFileDefinition (int processID);
	public List<FieldColumnMapBean> getFileMappingDetails (int fileFormatId);
	public void saveVistaFileDetails (List<VistaFileProcessing> validRecords);
	public void saveBlockedRecords (List<VistaFileProcessing> blockedRecords);
	public List<String> saveAccuralFileDetails(List<NetAccrualFileProcessing> accuralValidRecords);
	public void saveKpiFileDetails(List<KpiFileProcessing> kpiValidRecords);
	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId);	
}
