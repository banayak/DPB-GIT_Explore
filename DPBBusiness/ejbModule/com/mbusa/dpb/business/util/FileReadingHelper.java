/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: FileReadingHelper.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle to read the file.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   	Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   	Sep 03, 2013     	  1.0        First Draft
 * SK5008848	Sep 05, 2013	      1.1        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.business.util;

import java.util.ArrayList;
import java.util.List;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.business.view.DpbCommonBeanLocal;
import com.mbusa.dpb.business.view.FileProcessingBeanLocal;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.FieldColumnMapBean;
import com.mbusa.dpb.common.domain.FileDefinitionBean;
import com.mbusa.dpb.common.domain.FileFormatBean;
import com.mbusa.dpb.common.domain.KpiFileProcessing;
import com.mbusa.dpb.common.domain.NetAccrualFileProcessing;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.helper.FileProcessingHelperAbstract;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.DPBCommonHelper;
import com.mbusa.dpb.common.util.MailAttachment;
import com.mbusa.dpb.common.util.SendMailDTO;

public class FileReadingHelper extends FileProcessingHelperAbstract {

	private static DPBLog LOGGER = DPBLog.getInstance(FileReadingHelper.class);
	static final private String CLASSNAME = FileReadingHelper.class.getName();
	static PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
	
	private LocalServiceFactory local = new LocalServiceFactory();
	SendMailDTO sendMailDTO = new SendMailDTO();
	MailAttachment attachment = null;
	MailAttachment[] attachArray = new MailAttachment[1];
	FileDefinitionBean definitionBean = null;
	FileFormatBean formatBean = null;
	DpbCommonBeanLocal commonBean = null;
	DPBCommonHelper commonHelper = null;
	String userId;

	List<VistaFileProcessing> vistaValidRecords = new ArrayList<VistaFileProcessing>();
	List<VistaFileProcessing> vistaFailedRecords = new ArrayList<VistaFileProcessing>();
	List<VistaFileProcessing> blockedRecords = new ArrayList<VistaFileProcessing>();
	List<NetAccrualFileProcessing> accuralValidRecords = new ArrayList<NetAccrualFileProcessing>();
	List<NetAccrualFileProcessing> accuralFailedRecords = new ArrayList<NetAccrualFileProcessing>();
	List<KpiFileProcessing> kpiValidRecords = new ArrayList<KpiFileProcessing>();
	List<KpiFileProcessing> kpiFailedRecords = new ArrayList<KpiFileProcessing>();
	List<String> ignoreRecords = new ArrayList<String>();

	String inProcessPath = IConstants.EMPTY_STR;
	String errorPath = IConstants.EMPTY_STR;
	String archivePath = IConstants.EMPTY_STR;
	String type = IConstants.EMPTY_STR;
	String companyName = IConstants.EMPTY_STR;
	List<String> failedLines = new ArrayList<String>();
	String completeFilePath = IConstants.EMPTY_STR;
	String filePath = IConstants.EMPTY_STR;
	boolean isFileAvailable = true;

	/**
	 * This method is used for Fetching file definition and File mapping details
	 * and process the file
	 * @param processID
	 * @return
	 */
	public boolean processFile(int processID, String userId, java.sql.Date actDate){

		final String methodName = "processFile";
		LOGGER.enter(CLASSNAME, methodName);
		LOGGER.info("File Processing Started.");
		
		boolean result = false;
		String formatName = null;
		FileFormatBean fielFormatBean = null;
		List<FieldColumnMapBean> fileMapping = null;
		FileProcessingBeanLocal beanLocal = null;
		DpbCommonBeanLocal commonBean = null;
		DPBCommonHelper commonHelper = new DPBCommonHelper();
		String dateValue = "";
		this.userId = userId;
		try {
			beanLocal = local.getFileProcessingService();
			commonBean = local.getDpbCommonService();
		} catch (TechnicalException e1) {
			LOGGER.error("Exception Occured While Calling EJB");
			return result;
		}
		if(actDate != null) {
			dateValue = actDate.toString().replaceAll("-", "");
		}
		definitionBean = beanLocal.fetchFileDefinition(processID);

		if (definitionBean != null) {
			fielFormatBean = definitionBean.getFileFormats();
			if (fielFormatBean == null) {
				LOGGER.error("File format is not defined for process ID " + processID);
				commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
						"File format is not defined for process ID ", IConstants.PROC_STATUS_FAILURE, userId));
				return result;
			}
			if (fielFormatBean.getFileFormatId() == 0) {
				LOGGER.error("File format ID not defined for process ID " + processID);
				commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
						"File format ID not defined for process ID ", IConstants.PROC_STATUS_FAILURE, userId));
				return result;
			} else {
				fileMapping = beanLocal.getFileMappingDetails(
						fielFormatBean.getFileFormatId());
			}
			if (fileMapping == null) {
				LOGGER.error("File mapping is not defined for definition for process ID " + processID);
				commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
						"File mapping is not defined for definition for process ID ",IConstants.PROC_STATUS_FAILURE, userId));
				return result;
			}
			fielFormatBean.setFileMapingList(fileMapping);
			LOGGER.info("File Definition for definition Id:"+definitionBean.getFileDefId()+", File Format and Column mapping details for format Id:"
			+fielFormatBean.getFileFormatId()+" are retrieved successfully");
			//formatName = fielFormatBean.getFormatName();
			//if (formatName != null && formatName.trim().equalsIgnoreCase("Vista")) {
			if (definitionBean.getBaseFolder() != null && 
					definitionBean.getBaseFolder().toLowerCase().contains(IConstants.VISTA_BASE_FOLDER)) {
				try{
					result = processFileData(processID, 1, actDate);
				
					if (result) {
						try {
							beanLocal.saveVistaFileDetails(vistaValidRecords,blockedRecords);
							commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
											"File processing successfully completed",IConstants.PROC_STATUS_SUCCESS, userId));
							moveFileTo(completeFilePath, archivePath, null);
							sendMailDTO.setContent(PropertyManager
									.getPropertyManager().getPropertyAsString("fileprocess.success")+",for Process ID:"+processID);
							if(ignoreRecords.size() > 0){
								//writeFailedRecords(ignoreRecords, errorPath + "invalidDealers" + IConstants.DOT_STR + actDate + IConstants.DOT_STR + IConstants.FILE_EXT);
								byte[] bytes = writeFailedRecords(ignoreRecords, errorPath + "VISTAInvalidDealers" + IConstants.DOT_STR + dateValue + IConstants.DOT_STR + IConstants.FILE_EXT);
								attachment = new MailAttachment("VISTAInvalidDealers" + IConstants.DOT_STR + dateValue + IConstants.DOT_STR + IConstants.FILE_EXT,"text/plain", bytes);
								attachArray[0] = attachment;
								sendMailDTO.setAttachments(attachArray);
								sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
								DPBCommonHelper.sendMailWithAttachment(sendMailDTO);
							}else{
							sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
							DPBCommonHelper.sendEmail(sendMailDTO);
							}
							result = true;
						} catch (Exception e) {
							LOGGER.error("File processing failed while saving into DB",e);
							commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
									"File processing failed while saving the data into DB",IConstants.PROC_STATUS_FAILURE, userId));
							moveFileTo(completeFilePath, errorPath, null);
							sendMailDTO.setContent(PropertyManager.getPropertyManager().getPropertyAsString(
											"fileprocess.failed"));
							sendMailDTO.setAttachments(attachArray);
							sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
							DPBCommonHelper.sendEmail(sendMailDTO);
	
							result = false;
						}
					} else {
						result = false;
						if (isFileAvailable) {
							commonBean.insertIntoProcessLog(commonHelper.getProcessLog(
									processID, "File not available with valid data",IConstants.PROC_STATUS_FAILURE, userId));
							sendMailDTO.setContent(setMailContentForVista(vistaFailedRecords));
							sendMailDTO.setAttachments(attachArray);
							moveFileTo(completeFilePath, errorPath, null);
							sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
							DPBCommonHelper.sendMailWithAttachment(sendMailDTO);
							
						} else {
							sendMailDTO.setContent(PropertyManager.getPropertyManager().getPropertyAsString(
									"file.not.available")+filePath);
							moveFileTo(completeFilePath, errorPath, null);
							sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
							DPBCommonHelper.sendEmail(sendMailDTO);
						}
						/*moveFileTo(completeFilePath, errorPath, null);
						sendMailDTO.setContent(PropertyManager.getPropertyManager()
								.getPropertyAsString("fileprocess.no.valid.data"));
						sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
						DPBCommonHelper.sendMailWithAttachment(sendMailDTO);*/
					}

			//} else if (formatName != null&& formatName.trim().equalsIgnoreCase("Net Accrual")) {
			}catch(BusinessException be){
				if(be.getMessageKey().equalsIgnoreCase("splMsg")){
					LOGGER.error("File processing failed while checking special conditions",be);
					commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
							"File processing failed while checking special conditions",IConstants.PROC_STATUS_FAILURE, userId));
				}else if(be.getMessageKey().equalsIgnoreCase("blkMsg")){
					LOGGER.error("File processing failed while checking blocking conditions",be);
					commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
							"File processing failed while checking blocking conditions",IConstants.PROC_STATUS_FAILURE, userId));
				}else if(be.getMessageKey().equalsIgnoreCase("vehMsg")){
					LOGGER.error("File processing failed while checking vehicle conditions",be);
					commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
							"File processing failed while checking vehicle conditions",IConstants.PROC_STATUS_FAILURE, userId));
				}
				moveFileTo(completeFilePath, errorPath, null);
				sendMailDTO.setContent(PropertyManager.getPropertyManager().getPropertyAsString(
								be.getMessage()));
				sendMailDTO.setAttachments(attachArray);
				sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
				DPBCommonHelper.sendEmail(sendMailDTO);

				result = false;
			}
			}else if (definitionBean.getBaseFolder() != null && 
					definitionBean.getBaseFolder().toLowerCase().contains(IConstants.ACCURAL_BASE_FOLDER)) {
				try{
				result = processFileData(processID, 2, actDate);
				if (result && accuralValidRecords.size() > 0) {
					try {
						ignoreRecords = beanLocal.saveAccuralFileDetails(accuralValidRecords);
						commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
								"File processing successfully completed",IConstants.PROC_STATUS_SUCCESS, userId));
						moveFileTo(completeFilePath, archivePath, null);
						sendMailDTO.setContent(PropertyManager
								.getPropertyManager().getPropertyAsString(
										"fileprocess.success")+",for Process ID:"+processID);
						sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
						if(ignoreRecords!=null && ignoreRecords.size() > 0){
							byte[] bytes = writeFailedRecords(ignoreRecords, errorPath + "NegativeAmountRecords" + IConstants.DOT_STR + dateValue + IConstants.DOT_STR + IConstants.FILE_EXT);
							attachment = new MailAttachment("NegativeAmountRecords" + IConstants.DOT_STR + dateValue + IConstants.DOT_STR + IConstants.FILE_EXT,"text/plain", bytes);
							attachArray[0] = attachment;
							sendMailDTO.setAttachments(attachArray);
							DPBCommonHelper.sendMailWithAttachment(sendMailDTO);
						}else {
							DPBCommonHelper.sendEmail(sendMailDTO);
						}
						result = true;
					} catch (Exception e) {
						LOGGER.error("File processing failed while saving into DB",e);
						commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
							"File processing failed while saving data into DB",IConstants.PROC_STATUS_FAILURE, userId));
						moveFileTo(completeFilePath, errorPath, null);
						sendMailDTO.setContent(PropertyManager
								.getPropertyManager().getPropertyAsString("fileprocess.failed"));
						sendMailDTO.setAttachments(attachArray);
						sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
						DPBCommonHelper.sendEmail(sendMailDTO);

						result = false;
					}
				} else {
					result = false;
					if (isFileAvailable) {
						commonBean.insertIntoProcessLog(commonHelper.getProcessLog(
								processID, "File not available with valid data",IConstants.PROC_STATUS_FAILURE, userId));
						sendMailDTO.setContent(setMailContentForAccural(accuralFailedRecords));
						sendMailDTO.setAttachments(attachArray);
						moveFileTo(completeFilePath, errorPath, null);
						sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
						DPBCommonHelper.sendMailWithAttachment(sendMailDTO);
					} else {
						sendMailDTO.setContent(PropertyManager.getPropertyManager().getPropertyAsString(
								"file.not.available")+filePath);
						moveFileTo(completeFilePath, errorPath, null);
						sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
						DPBCommonHelper.sendEmail(sendMailDTO);
					}
					/*moveFileTo(completeFilePath, errorPath, null);
					sendMailDTO.setContent(PropertyManager.getPropertyManager()
							.getPropertyAsString("fileprocess.no.valid.data"));
					sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
					DPBCommonHelper.sendEmail(sendMailDTO);*/
				}
			}catch(BusinessException be){
				result = false;
			}
			//} else if (formatName != null && formatName.trim().equalsIgnoreCase("Kpi")) {
			} else if (definitionBean.getBaseFolder() != null && 
					definitionBean.getBaseFolder().toLowerCase().contains(IConstants.KPI_BASE_FOLDER)) {
				try{
				result = processFileData(processID, 3, actDate);
				if (result && kpiValidRecords.size() > 0) {
					try {
						beanLocal.saveKpiFileDetails(kpiValidRecords);
						commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
								"File processing successfully completed",IConstants.PROC_STATUS_SUCCESS, userId));
						moveFileTo(completeFilePath, archivePath, null);
						sendMailDTO.setContent(PropertyManager
								.getPropertyManager().getPropertyAsString(
										"fileprocess.success")+",for Process ID:"+processID);
						if(ignoreRecords.size() > 0){
							//writeFailedRecords(ignoreRecords, errorPath + "invalidDealers" + IConstants.DOT_STR + actDate + IConstants.DOT_STR + IConstants.FILE_EXT);
							byte[] bytes = writeFailedRecords(ignoreRecords, errorPath + "KPIInvalidDealers" + IConstants.DOT_STR + dateValue + IConstants.DOT_STR + IConstants.FILE_EXT);
							attachment = new MailAttachment("KPIInvalidDealers" + IConstants.DOT_STR + dateValue + IConstants.DOT_STR + IConstants.FILE_EXT,"text/plain", bytes);
							attachArray[0] = attachment;
							sendMailDTO.setAttachments(attachArray);
							sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
							DPBCommonHelper.sendMailWithAttachment(sendMailDTO);
						}else{
						sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
						DPBCommonHelper.sendEmail(sendMailDTO);
						}
						/*sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
						DPBCommonHelper.sendEmail(sendMailDTO);*/
						result = true;
					} catch (Exception e) {
						LOGGER.error("File processing failed while saving into DB",e);
						commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
								"File processing failed while saving into DB",IConstants.PROC_STATUS_FAILURE, userId));
						moveFileTo(completeFilePath, errorPath, null);
						sendMailDTO.setContent(PropertyManager
								.getPropertyManager().getPropertyAsString("fileprocess.failed"));
						sendMailDTO.setAttachments(attachArray);
						sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
						DPBCommonHelper.sendEmail(sendMailDTO);
						result = false;
					}
				} else {
					result = false;
					if (isFileAvailable) {
						commonBean.insertIntoProcessLog(commonHelper.getProcessLog(
								processID, "File not available with valid data",IConstants.PROC_STATUS_FAILURE, userId));
						sendMailDTO.setContent(setMailContentForKpi(kpiFailedRecords));
						sendMailDTO.setAttachments(attachArray);
						moveFileTo(completeFilePath, errorPath, null);
						sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
						DPBCommonHelper.sendEmail(sendMailDTO);
					} else {
						sendMailDTO.setContent(PropertyManager.getPropertyManager().getPropertyAsString(
								"file.not.available")+filePath);
						moveFileTo(completeFilePath, errorPath, null);
						sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
						DPBCommonHelper.sendEmail(sendMailDTO);
					}
					/*moveFileTo(completeFilePath, errorPath, null);
					sendMailDTO.setContent(PropertyManager.getPropertyManager()
							.getPropertyAsString("fileprocess.no.valid.data"));
					sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("file.Process.Mail.mailSubject"));
					DPBCommonHelper.sendEmail(sendMailDTO);*/
				}
		}catch(BusinessException be){
			result = false;
		}
		} else {
			LOGGER.error("Invalid Definition Found");
			commonBean.insertIntoProcessLog(
					commonHelper.getProcessLog(processID,
							"Invalid definition found for the process", IConstants.PROC_STATUS_FAILURE, userId));
			return false;
		}
		}
		return result;
	}

	private String setMailContentForKpi(List<KpiFileProcessing> failedRecords) {
		StringBuffer content = new StringBuffer("");
		getContentHeader(content);
		for(KpiFileProcessing proc: failedRecords) {
			content.append("<tr><td>");
			content.append(proc.getLineNumber());
			content.append("</td><td>");
			content.append(proc.getReason());
			content.append("</td></tr>");
		}
		content.append("</table>");
		return content.toString();
	}
	
	private void getContentHeader (StringBuffer content) {
		content.append(PropertyManager.getPropertyManager()
				.getPropertyAsString("fileprocess.no.valid.data"));
		content.append(IConstants.NEW_LINE);
		content.append(IConstants.NEW_LINE);
		content.append("<table><tr><td>");
		content.append("Line No:");
		content.append("</td><td>");
		content.append("Reason:");
		content.append("</td></tr>");
	}
	
	private String setMailContentForVista(List<VistaFileProcessing> failedRecords) {
		StringBuffer content = new StringBuffer("");
		getContentHeader(content);
		for(VistaFileProcessing proc: failedRecords) {
			content.append("<tr><td>");
			content.append(proc.getLineNumber());
			content.append("</td><td>");
			content.append(proc.getReason());
			content.append("</td></tr>");
		}
		content.append("</table>");
		return content.toString();
	}
	
	private String setMailContentForAccural(List<NetAccrualFileProcessing> failedRecords) {
		StringBuffer content = new StringBuffer("");
		getContentHeader(content);
		for(NetAccrualFileProcessing proc: failedRecords) {
			content.append("<tr><td>");
			content.append(proc.getLineNumber());
			content.append("</td><td>");
			content.append(proc.getReason());
			content.append("</td></tr>");
		}
		content.append("</table>");
		return content.toString();
	}

	/**
	 * This method is used for process the file data and moving the file to path
	 * @param processID
	 * @param fileType
	 * @return
	 */
	private boolean processFileData(int processID, int fileType, java.sql.Date actDate)throws BusinessException {
		final String methodName = "processFileData";
		LOGGER.enter(CLASSNAME, methodName);
		formatBean = definitionBean.getFileFormats();
		String newFileName = IConstants.EMPTY_STR;
		String fileName = IConstants.EMPTY_STR;
		boolean isErrorOccured = false;
		String dateValue = "";
		commonHelper = new DPBCommonHelper();
		VistaFileProcessingHelper vistaHelper = new VistaFileProcessingHelper();
		KpiFileProcessingHelper kpiHelper = new KpiFileProcessingHelper();
		NetAccrualFileProcessingHelper netAccrualHelper = new NetAccrualFileProcessingHelper();
		boolean flag = true;
		try {
			commonBean = local.getDpbCommonService();
			if(actDate != null) {
				dateValue = actDate.toString().replaceAll("-", "");
			}
			//filePath = definitionBean.getBaseFolder().trim()+IConstants.DOT_STR+IConstants.FILE_EXT;
			fileName = definitionBean.getFileNameFormat().trim()+IConstants.DOT_STR+dateValue+IConstants.DOT_STR+IConstants.FILE_EXT;
			System.out.println("fileName:"+fileName);
			filePath = PROPERTY_MANAGER.getPropertyAsString("file.process.in") + fileName;
			
			switch (fileType) {
			case 1:
				inProcessPath = PropertyManager.getPropertyManager().getPropertyAsString("vista.inprocesspath");
				errorPath = PropertyManager.getPropertyManager().getPropertyAsString("vista.errorpath");
				archivePath = PropertyManager.getPropertyManager().getPropertyAsString("vista.archivepath");
				type = PropertyManager.getPropertyManager().getPropertyAsString("vista.filetype");
				companyName = PropertyManager.getPropertyManager().getPropertyAsString("vista.originatingcompany.name");
				break;
			case 2:
				inProcessPath = PropertyManager.getPropertyManager().getPropertyAsString("netaccrual.inprocesspath");
				errorPath = PropertyManager.getPropertyManager().getPropertyAsString("netaccrual.errorpath");
				archivePath = PropertyManager.getPropertyManager().getPropertyAsString("netaccrual.archivepath");
				type = PropertyManager.getPropertyManager().getPropertyAsString("netaccrual.filetype");
				companyName = PropertyManager.getPropertyManager().getPropertyAsString("netaccrual.originatingcompany.name");
				break;
			case 3:
				inProcessPath = PropertyManager.getPropertyManager().getPropertyAsString("kpi.inprocesspath");
				errorPath = PropertyManager.getPropertyManager().getPropertyAsString("kpi.errorpath");
				archivePath = PropertyManager.getPropertyManager().getPropertyAsString("kpi.archivepath");
				type = PropertyManager.getPropertyManager().getPropertyAsString("kpi.filetype");
				companyName = PropertyManager.getPropertyManager().getPropertyAsString("kpi.originatingcompany.name");
				break;
			default:
				break;
			}
			if(actDate != null) {
				dateValue = actDate.toString().replaceAll("-", "");
			}
			newFileName = companyName + "_"
					+ fileName.substring(0, fileName.length() - 4) + IConstants.DOT_STR + type
					+ IConstants.DOT_STR + dateValue + IConstants.DOT_STR + IConstants.FILE_EXT;

			completeFilePath = inProcessPath + newFileName;
			LOGGER.info("File In Path: "+filePath);
			LOGGER.info("File InProcess Path: "+inProcessPath+newFileName);
			LOGGER.info("File Error Path: "+errorPath+newFileName);
			LOGGER.info("File Archive Path: "+archivePath+newFileName);
			
			System.out.println("File In Path: "+filePath);
			System.out.println("File InProcess Path: "+inProcessPath+newFileName);
			System.out.println("File Error Path: "+errorPath+newFileName);
			System.out.println("File Archive Path: "+archivePath+newFileName);
			commonBean.insertIntoProcessLog(
					commonHelper.getProcessLog(processID,
							"File moving from In to In-Process Folder", IConstants.PROC_STATUS_IN_PROCESS, userId));

			if (!moveFileTo(filePath, inProcessPath, newFileName)) {
				LOGGER.error("File not available to read");
				commonBean.insertIntoProcessLog(commonHelper.getProcessLog(
						processID, "File not available to read, FileName:"+fileName, IConstants.PROC_STATUS_FAILURE, userId));
				isFileAvailable = false;
				return false;
			}

			commonBean.insertIntoProcessLog(commonHelper.getProcessLog(
					processID, "File Successfully Moved to In-Process folder",IConstants.PROC_STATUS_IN_PROCESS, userId));

			switch (fileType) {
			case 1:
				LOGGER.info("Vista file processing started, file name:"+fileName);
				isErrorOccured = vistaHelper.processVistaFile(completeFilePath,
						formatBean, vistaValidRecords, blockedRecords, vistaFailedRecords,failedLines,processID, userId, ignoreRecords);
				if (isErrorOccured) {
					writeFailedRecords(failedLines, errorPath + newFileName + ".error");
					
					byte[] bytes = writeFailedRecords(failedLines, errorPath+newFileName+".error");
					attachment = new MailAttachment(newFileName,"text/plain", bytes);
					attachArray[0] = attachment;
					sendMailDTO.setAttachments(attachArray);
					LOGGER.info("VISTA file failed records:"+vistaFailedRecords.size());
					System.out.println("VISTA file failed records:"+vistaFailedRecords.size());
					flag = false;
				} else {
					commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
					"File reading is completed and inserting the records into database",IConstants.PROC_STATUS_IN_PROCESS, userId));
					LOGGER.info("Vista file processing is completed and inserting the records into database");
					LOGGER.info("VISTA file valid records:"+vistaValidRecords.size());
					LOGGER.info("VISTA file blocked records:"+blockedRecords.size());
					LOGGER.info("VISTA file invalid dealer records:"+ignoreRecords.size());
					System.out.println("VISTA file valid records:"+vistaValidRecords.size());
					System.out.println("VISTA file blocked records:"+blockedRecords.size());
					System.out.println("VISTA file invalid dealer records:"+ignoreRecords.size());
				}
				break;
			case 2:
				LOGGER.info("Net accrual file processing started, file name:"+fileName);
				isErrorOccured = netAccrualHelper.processNetAccuralFile(
						completeFilePath, formatBean, accuralValidRecords, accuralFailedRecords, failedLines,processID, userId );
				if (isErrorOccured || accuralValidRecords.size() == 0) {
					writeFailedRecords(failedLines, errorPath + newFileName + ".error");
					
					byte[] bytes = writeFailedRecords(failedLines, errorPath+newFileName+".error");
					attachment = new MailAttachment(newFileName,"text/plain", bytes);
					attachArray[0] = attachment;
					sendMailDTO.setAttachments(attachArray);
					LOGGER.info("Net Accrual file failed records:"+accuralFailedRecords.size());
					System.out.println("Net Accrual file failed records:"+accuralFailedRecords.size());
					flag = false;
				} else {
					commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
					"File reading is completed and inserting the records into database",IConstants.PROC_STATUS_IN_PROCESS, userId));
					LOGGER.info("Net accrual file processing is completed and inserting the records into database");
					LOGGER.info("Net Accrual file valid records:"+accuralValidRecords.size());
					System.out.println("Net Accrual file valid records:"+accuralValidRecords.size());
				}
				break;
			case 3:
				LOGGER.info("KPI file processing started, file name:"+fileName);
				isErrorOccured = kpiHelper.processKpiFile(completeFilePath,
						formatBean, kpiValidRecords, kpiFailedRecords, failedLines, processID, userId, ignoreRecords);
				if (isErrorOccured || kpiValidRecords.size() == 0) {
					writeFailedRecords(failedLines, errorPath + newFileName + ".error");
					
					byte[] bytes = writeFailedRecords(failedLines, errorPath+newFileName+".error");
					attachment = new MailAttachment(newFileName,"text/plain", bytes);
					attachArray[0] = attachment;
					sendMailDTO.setAttachments(attachArray);
					LOGGER.info("KPI file failed records:"+kpiFailedRecords.size());
					System.out.println("KPI file failed records:"+kpiFailedRecords.size());
					flag = false;
				} else {
					commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
					"File reading is completed and inserting the records into database",IConstants.PROC_STATUS_IN_PROCESS, userId));
					LOGGER.info("KPI file processing is completed and inserting the records into database");
					LOGGER.info("KPI file valid records:"+kpiValidRecords.size());
					LOGGER.info("KPI file invalid dealer records:"+ignoreRecords.size());
					System.out.println("KPI file valid records:"+kpiValidRecords.size());
					System.out.println("KPI file invalid dealer records:"+ignoreRecords.size());
				}
				break;
			default:
				break;
			}

		} 
		catch(BusinessException be){
			throw be;
		}catch (Exception e) {
			LOGGER.error("File not found or Cannot Type cast the data", e);
			flag = false;
			/*commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processID,
					"File not found in the path or Cannot Type cast the data ", IConstants.PROC_STATUS_FAILURE));
			flag = false;
			sendMailDTO.setContent(PropertyManager
					.getPropertyManager().getPropertyAsString("fileprocess.failed"));
			fileProcessSendEmail(sendMailDTO);*/
		}
		LOGGER.exit(CLASSNAME, methodName);
		return flag;
	}

}
