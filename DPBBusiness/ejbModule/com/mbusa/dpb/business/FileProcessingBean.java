/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: FileProcessingBean.java
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
package com.mbusa.dpb.business;

import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.mbusa.dpb.business.util.FileReadingHelper;
import com.mbusa.dpb.business.util.GenerateReportHelper;
import com.mbusa.dpb.business.util.PaymentProcess;
import com.mbusa.dpb.business.util.SchedulerHelper;
import com.mbusa.dpb.business.view.FileProcessingBeanLocal;
import com.mbusa.dpb.common.bo.FileProcessingManagerImpl;
import com.mbusa.dpb.common.bo.IFileProcessingManager;
import com.mbusa.dpb.common.constant.IConstants;
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
import com.mbusa.dpb.common.factories.BOFactory;
import com.mbusa.dpb.common.logger.DPBLog;
/**
 * Session Bean implementation class FileProcessingBean
 */
@Stateless
@Local(FileProcessingBeanLocal.class)
public class FileProcessingBean implements FileProcessingBeanLocal {

	private static DPBLog LOGGER = DPBLog.getInstance(FileProcessingBean.class);
	static final private String CLASSNAME = FileProcessingBean.class.getName();
	private IFileProcessingManager fileProcessMgr  = (FileProcessingManagerImpl) BOFactory.getInstance().getImplementation(IFileProcessingManager.class);
	
	/**
     * Default constructor. 
     */	
	
	
	/**
     * Default constructor. 
     */
	public FileProcessingBean() {
        // TODO Auto-generated constructor stub
    }
	
    
   /* public void processFile(Integer processID) throws Exception{
    	fileProcessMgr.processFile(processID);
    }*/
    
    /**
     * This method fetches the file definition bean object based on the process id.
     */
    public FileDefinitionBean fetchFileDefinition (int processID) {
    	final String methodName = "fetchFileDefinition";
		LOGGER.enter(CLASSNAME, methodName);
		FileDefinitionBean fileDefinitionBean = fileProcessMgr.fetchFileDefinition (processID);
		LOGGER.exit(CLASSNAME, methodName);
		return fileDefinitionBean;
    }
    
    /**
     * This method fetches the file mapping list based on the file format id.
     */
    public List<FieldColumnMapBean> getFileMappingDetails (int fileFormatId) {
    	final String methodName = "getFileMappingDetails";
		LOGGER.enter(CLASSNAME, methodName);
		List<FieldColumnMapBean> mapBeanList = fileProcessMgr.getFileMappingDetails(fileFormatId);
		LOGGER.exit(CLASSNAME, methodName);
		return mapBeanList;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveVistaFileDetails (List<VistaFileProcessing> validRecords, List<VistaFileProcessing> blockedRecords) {
    	final String methodName = "saveVistaFileDetails";
		LOGGER.enter(CLASSNAME, methodName);
		fileProcessMgr.saveVistaFileDetails(validRecords);
		fileProcessMgr.saveBlockedRecords(blockedRecords);
		LOGGER.exit(CLASSNAME, methodName);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<String> saveAccuralFileDetails(List<NetAccrualFileProcessing> accuralValidRecords) {
    	final String methodName = "saveAccuralFileDetails";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> negativeRecords = null;
		negativeRecords = fileProcessMgr.saveAccuralFileDetails(accuralValidRecords);
		LOGGER.exit(CLASSNAME, methodName);
		return negativeRecords;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveKpiFileDetails(List<KpiFileProcessing> kpiValidRecords) {
    	final String methodName = "saveKpiFileDetails";
		LOGGER.enter(CLASSNAME, methodName);
		fileProcessMgr.saveKpiFileDetails(kpiValidRecords);
		LOGGER.exit(CLASSNAME, methodName);
    }
    
    public List<ProcessInputFile> getProcInputFileDetails()
    {
    	List<ProcessInputFile> procInputFile=null;		
    	procInputFile =  fileProcessMgr.getProcInputFileDetails();		
    	return procInputFile;		
    }

    public List<GeneratePaymentFile> genPayFile()
    {
    	List<GeneratePaymentFile> genPaymentFile=null;		
    	genPaymentFile =  fileProcessMgr.genPayFile();
    	return genPaymentFile;		
    }

    public List<GenerateReportFile> genReportFile()
    {
    	List<GenerateReportFile> genReportFile=null;
    	genReportFile =  fileProcessMgr.genReportFile();		
    	return genReportFile;		
    }
   /* public ProgramDefinition retrivePaymentDefinition (int processID){
    	final String methodName = "retrivePaymentDefinition";
		LOGGER.enter(CLASSNAME, methodName);
		ProgramDefinition pDefinition = fileProcessMgr.retrivePaymentDefinition(processID);
		LOGGER.exit(CLASSNAME, methodName);
		return pDefinition;
    }*/
    public List<DPBProcessLogBean> getProcessLogsDeatils(int procId) {
		List<DPBProcessLogBean> procesDetail = null;
		procesDetail = fileProcessMgr.getProcessLogsDeatils(procId);
		return procesDetail;
	}
   
	@Asynchronous
     public  void startProcFileDetails(int procId,String procType,String userId,boolean flag,java.sql.Date date) 
	{
		
		FileReadingHelper filereadHelper=new FileReadingHelper();
		PaymentProcess payProc=new PaymentProcess();
		GenerateReportHelper genReportHelper=new GenerateReportHelper();
		boolean result=false;
		if(IConstants.FILE_PROCESS_NAME.equalsIgnoreCase(procType))
		{
			result=filereadHelper.processFile(procId, userId, date);
		}
		else if(IConstants.COMMISSION_PAYMENT_PROCESS.equalsIgnoreCase(procType) || IConstants.FIXED_PAYMENT_PROCESS.equalsIgnoreCase(procType) || IConstants.VARIABLE_PAYMENT_PROCESS.equalsIgnoreCase(procType) || IConstants.LEADERSHIP_PAYMENT_PROCESS.equalsIgnoreCase(procType))
		{
			//Added one parameter for DPB daily payment details 0032484965 - Kshitija
			result=payProc.startPaymentProcess(procId, procType, userId,"M",null,null,null) ; //M -Manual For Future use
		}
		else if(IConstants.REPORT_PROCESS_NAME.equalsIgnoreCase(procType))
		{
			//Added one parameter for DPB daily payment details 0032484965 - Kshitija
			result = genReportHelper.reportsGenerateScheduler(procId, userId, date,null);
		}
	}
	@Asynchronous
	 @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public  void startSchedulerManualCall(BulkSchedulerInfo bulkInfo)  
	{
		   SchedulerHelper helper =new SchedulerHelper();
		   helper.doProcess(bulkInfo);	
	}

	//Data Cleanup for process rerun start
	/* (non-Javadoc)
	 * @see com.mbusa.dpb.business.view.FileProcessingBeanLocal#startCleanUp(java.sql.Date)
	 */
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public void startCleanUp(java.sql.Date procDate) {
		SchedulerHelper helper =new SchedulerHelper();
		helper.cleanUpData(procDate);
	}
	//Data Cleanup for process rerun end
}
