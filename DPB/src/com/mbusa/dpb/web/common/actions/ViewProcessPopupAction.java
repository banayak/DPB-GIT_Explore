/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ProcessCalDefAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle request related to process calender popup.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 15, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.common.actions;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.FileDefinitionBean;
import com.mbusa.dpb.common.domain.FileProcessDefBean;
import com.mbusa.dpb.common.domain.ProcessStatus;
import com.mbusa.dpb.common.domain.Trigger;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.form.FileProcessDefForm;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;


public class ViewProcessPopupAction extends DPBAction{

	private static final long serialVersionUID = -4551885050468489774L;
	static final private String CLASSNAME = ViewProcessPopupAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(ViewProcessPopupAction.class);
	private String actionForward = "errorPage";
	private BusinessDelegate busDelegate = new BusinessDelegate();
	private int processId;
	private FileProcessDefForm fileProcessDefForm;
	private FileProcessDefBean fileProcessDefBean;
	java.sql.Date rEndDate;
	private String processType;
	private String defType;
	private String shouldRefreshParent = "N";
	boolean isPaymentSuccess = false;
	int fileType =0;
	public String getDefType() {
		return defType;
	}
	/**
	 * @param defType the defType to set
	 */
	public void setDefType(String defType) {
		this.defType = defType;
	}
	

	public String viewProcess(){
		final String methodName = "viewVehicleConditions";
		LOGGER.enter(CLASSNAME, methodName);
		try{			
			actionForward = "processPopupView";
			fileProcessDefBean = busDelegate.getFileProcessDefination(processId);
			isPaymentSuccess = busDelegate.checkPaymentProcesses(processId);
			fileProcessDefForm = new FileProcessDefForm();
			fileProcessDefBean.getRecCount();
			if (IConstants.PROC_STATUS_FAILURE.equals(fileProcessDefBean.getStatus()) || fileProcessDefBean.getStatus() == null || fileProcessDefBean.getStatus().isEmpty())  
			{
				mapBeanToForm();
			}
			else{
				mapBeanToFormView();
			}
		}catch(BusinessException be){
			LOGGER.info("BusinessException occured");
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (TechnicalException te) {
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		catch (PersistenceException pe) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, pe);
		}
		catch (Exception e) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, e);
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}
	
	public String saveProcess(){
		final String methodName = "viewVehicleConditions";
		LOGGER.enter(CLASSNAME, methodName);
		FileDefinitionBean definitionBean = null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
		try{			
			int newProcessId = 0;
			actionForward = "processPopupView";
			java.sql.Date startDate = Date.valueOf(WebHelper.getNextDayDate());
			java.sql.Date procDate = WebHelper.convertToSqlDate(WebHelper.convertStringToDate(fileProcessDefForm.getProcessDate()));
			
			rEndDate = busDelegate.getEndDate(startDate);
			boolean isReProcess = false;
			boolean bonusProcess = false;
			boolean fileProcess = false;
			boolean ldrShpBonusProcess = false;
			if(!fileProcessDefForm.isReProcessFlag()){
			if(procDate.compareTo(rEndDate) > 0){
				addFieldError("fileProcessDefForm.processDate","Enter Valid date from Retail Calendar.");
			}
			}
			if(WebHelper.isEmptyOrNullString(fileProcessDefForm.getReasonForUpdate()) && !fileProcessDefForm.isReProcessFlag()){
				addFieldError("fileProcessDefForm.reasonForUpdate", "Enter Reason for updating the process.");
			}
			if(((WebHelper.isEmptyOrNullString(fileProcessDefForm.getProcessingTrigger()) || IConstants.TRIGGER_SYSTEM_INITIATION.equalsIgnoreCase(fileProcessDefForm.getProcessingTrigger())) && fileProcessDefForm.isReProcessFlag())){
				addFieldError("fileProcessDefForm.processingTrigger", "Select User Initiation Trigger for Reprocessing");
			}
			if(fileProcessDefForm.isReProcessFlag()){
				Calendar fileProcessTime = DateCalenderUtil.getCalendarFromDateString(fileProcessDefForm.getProcessDate());
				if(fileProcessTime == null){
					addFieldError("fileProcessDefForm.processDate", "Please enter Process Date");
				}
				if(!fileProcessTime.getTime().equals(cal.getTime())){
					addFieldError("fileProcessDefForm.processDate", "Process Date should be today's date for Reprocess");
				}
			}
			if(hasFieldErrors()){
				shouldRefreshParent = "N";
				addActionError("Missing data in mandatory fields. Please enter them and re-submit");
				actionForward = "processPopupView";
			}else{
				fileProcessDefBean = new FileProcessDefBean();
				mapFormToBean();
					if(IConstants.FIXED_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType()) || IConstants.VARIABLE_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType()) || IConstants.COMMISSION_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType()) || IConstants.LEADERSHIP_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType())){
						bonusProcess = true;
					}
					if(IConstants.LEADERSHIP_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType())){
						ldrShpBonusProcess = true;
					}
					if(IConstants.FILE_PROCESS_NAME.equalsIgnoreCase(fileProcessDefBean.getDefinitionType())){
						fileProcess = true;
					}
				if(IConstants.PROC_STATUS_REPROCESS.equalsIgnoreCase(fileProcessDefBean.getProcessType())){
					String message = IConstants.EMPTY_STR;
					String status = IConstants.EMPTY_STR;
					fileProcessDefBean.setInsertDate(procDate);
					// Re-Process / Reset Starts
					if(fileProcess){
						definitionBean = busDelegate.fetchFileDefinition(fileProcessDefBean.getProcessDefinitionId());
						isReProcess = WebHelper.reProcess(definitionBean, fileProcessDefBean.getProcessDefinitionId(),fileProcessDefBean, ldrShpBonusProcess);
						if(isReProcess){
							//newProcessId = busDelegate.updateFileProcessDefinition(fileProcessDefBean);
							if(IConstants.PROC_STATUS_REPROCESS.equalsIgnoreCase(fileProcessDefBean.getProcessType())){
								message = "Process Id:"+ fileProcessDefBean.getProcessDefinitionId() +" has been updated for REPROCESS";
								status = IConstants.PROC_STATUS_REPROCESS;
							} else if(IConstants.PROC_STATUS_RESET.equalsIgnoreCase(fileProcessDefBean.getProcessType())){
								message = "Process Id:"+ fileProcessDefBean.getProcessDefinitionId() +" has been updated for RESET. Defined New Process Id: "+ newProcessId;
								status = IConstants.PROC_STATUS_RESET;
							}
							busDelegate.insertIntoProcessLog(
									WebHelper.getProcessLog(fileProcessDefBean.getProcessDefinitionId(), message , status , fileProcessDefBean.getUser()));
							
						}else{ 
							addActionMessage("Could not schedule reprocess since file not available");
						}
					}else if(bonusProcess){
							busDelegate.performBonusReprocess(fileProcessDefBean.getProcessDefinitionId(), fileProcessDefBean.getUser(), fileProcessDefBean.getProcessType(), ldrShpBonusProcess);
							//newProcessId = busDelegate.updateFileProcessDefinition(fileProcessDefBean);
							if(IConstants.PROC_STATUS_REPROCESS.equalsIgnoreCase(fileProcessDefBean.getProcessType())){
								message = "Process Id:"+ fileProcessDefBean.getProcessDefinitionId() +" has been updated for ReProcessing";
								status = IConstants.PROC_STATUS_REPROCESS;
							}else if(IConstants.PROC_STATUS_RESET.equalsIgnoreCase(fileProcessDefBean.getProcessType())){
								message = "Process Id:"+ fileProcessDefBean.getProcessDefinitionId() +" has been updated for RESET";
								status = IConstants.PROC_STATUS_RESET;
							}
							busDelegate.insertIntoProcessLog(WebHelper.getProcessLog(fileProcessDefBean.getProcessDefinitionId(), message , status , fileProcessDefBean.getUser()));
				}
					busDelegate.updateFileProcessDefinition(fileProcessDefBean);// Re-Process / Reset Ends
				}else{
					if(fileProcess){
						definitionBean = busDelegate.fetchFileDefinition(fileProcessDefBean.getProcessDefinitionId());
						fileType = checkFileType(definitionBean.getBaseFolder());
					}
					if(fileProcess || bonusProcess || ldrShpBonusProcess){
						boolean dateCal = busDelegate.validateProcessDate(fileProcessDefBean.getProcessDateString());
						if(dateCal){
						busDelegate.updateProcessDefinition(fileProcessDefBean, fileType, bonusProcess, ldrShpBonusProcess);
						}else{
							addActionError("Retail Date is not available in Federated Table to schedule");
						}
					} else {
				busDelegate.updateFileProcessDefinition(fileProcessDefBean);	
					}
				}
				
				mapBeanToForm();
				addActionMessage(IWebConstants.MSG_SAVE);
				actionForward =  "processPopupView";
				shouldRefreshParent = "Y";
			}
		}catch(BusinessException be){
				LOGGER.info("BusinessException occured");
		}catch (TechnicalException te) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, te);
		}catch (PersistenceException pe) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, pe);
		}
		catch (Exception e) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, e);
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}
	
	public String closeClick(){
		return "processPopupView";
	}
	
	private void mapFormToBean() {
		if(fileProcessDefForm !=null && fileProcessDefBean != null){
			if(!WebHelper.isEmptyOrNullString(fileProcessDefForm.getProcessDefinitionId())){
				fileProcessDefBean.setProcessDefinitionId(Integer.parseInt(fileProcessDefForm.getProcessDefinitionId()));
			}
			if(!WebHelper.isEmptyOrNullString(fileProcessDefForm.getDefinitionId())){
				fileProcessDefBean.setDefinitionId(Integer.parseInt(fileProcessDefForm.getDefinitionId()));
			}
			
			fileProcessDefBean.setDefinitionType(getDefType());
			fileProcessDefBean.setProcessType(fileProcessDefForm.getProcessType());
			if(!WebHelper.isEmptyOrNullString(fileProcessDefForm.getReasonForUpdate())){
				fileProcessDefBean.setReasonForUpdate(fileProcessDefForm.getReasonForUpdate().trim());
			}
			if(!WebHelper.isEmptyOrNullString(fileProcessDefForm.getProcessingTrigger())){
				fileProcessDefBean.setProcessingTrigger(fileProcessDefForm.getProcessingTrigger().trim());
			}
			if(!WebHelper.isEmptyOrNullString(fileProcessDefForm.getProcessDate())){
				fileProcessDefBean.setProcessDate(DateCalenderUtil.getCalendarFromDateString(fileProcessDefForm.getProcessDate()));
			}
			if(!WebHelper.isEmptyOrNullString(fileProcessDefForm.getProcessingTime())){
				fileProcessDefBean.setProcessingTime(WebHelper.convertStringToTime(fileProcessDefForm.getProcessingTime()));
			}
			fileProcessDefBean.setUser(this.getUserId());
			fileProcessDefBean.setStatus(fileProcessDefBean.getStatus());
			fileProcessDefBean.setReProcessFlag(fileProcessDefForm.isReProcessFlag());
			fileProcessDefBean.setRecCount(fileProcessDefForm.getRecCount());
			fileProcessDefBean.setProgId(fileProcessDefForm.getProgId());
		}
	}
	
	private void mapBeanToFormView(){
		
			if(fileProcessDefForm !=null && fileProcessDefBean != null){
			Map<String,String> prcsTypeMap = MasterDataLookup.getInstance().getProcessTypeAsMap();
			Map<String,String> prcsStatusMap = MasterDataLookup.getInstance().getStatusString();
			List<ProcessStatus> prcsStatus = MasterDataLookup.getInstance().getProcessStatus();
			List<Trigger> defTgr = MasterDataLookup.getInstance().getTriggerCodes();
			String numString;				
			if(fileProcessDefBean.getProcessDefinitionId() == 0){
				numString = WebHelper.BLANK_STRING;
			}
			else{
				numString = String.valueOf(fileProcessDefBean.getProcessDefinitionId());
			}
			fileProcessDefForm.setProcessDefinitionId(numString);
			
			if(fileProcessDefBean.getDefinitionId() == 0){
				numString = WebHelper.BLANK_STRING;
			}
			else{
				numString = String.valueOf(fileProcessDefBean.getDefinitionId());
			}
			fileProcessDefForm.setDefinitionId(numString);
			if(validateReprocess(fileProcessDefBean)) {}else{	
				fileProcessDefForm.setFlag(true);
			}
			setDefType(fileProcessDefBean.getDefinitionType().trim());
			if(!WebHelper.isEmptyOrNullString(fileProcessDefBean.getDefinitionType())){
				fileProcessDefForm.setDefinitionType(prcsTypeMap.get(fileProcessDefBean.getDefinitionType().trim()));
			}
			if(!WebHelper.isEmptyOrNullString(fileProcessDefBean.getReasonForUpdate())){
				fileProcessDefForm.setReasonForUpdate(fileProcessDefBean.getReasonForUpdate().trim());
			}
			if(!WebHelper.isEmptyOrNullString(fileProcessDefBean.getProcessingTrigger())){
				if(validateReprocess(fileProcessDefBean)) {
					fileProcessDefForm.setProcessingTrigger(WebHelper.makeNonNullString(fileProcessDefBean.getProcessingTrigger().trim()));
				}else{
				fileProcessDefForm.setProcessingTrigger(WebHelper.getTriggerString(fileProcessDefBean.getProcessingTrigger().trim(),defTgr));
				}
			}
			if(fileProcessDefBean.getProcessDate()!=null){
				fileProcessDefForm.setProcessDate(DateCalenderUtil.getDatefromCalendar(fileProcessDefBean.getProcessDate()));
			}
			if(fileProcessDefBean.getProcessingTime()!=null){
				fileProcessDefForm.setProcessingTime(WebHelper.convertTimeToString(fileProcessDefBean.getProcessingTime()));
			}
			if(fileProcessDefBean.getStatus()== null || IConstants.EMPTY_STR.equals(fileProcessDefBean.getStatus()))
			{
				fileProcessDefForm.setShowStat("Yet To Start");
			}
			else
			{
				fileProcessDefForm.setShowStat(prcsStatusMap.get(fileProcessDefBean.getStatus()));
			}
			if(validateReprocess(fileProcessDefBean)) {}else{	
				fileProcessDefForm.setFlag(true);
			}
			fileProcessDefForm.setRecCount(fileProcessDefBean.getRecCount());
			fileProcessDefForm.setProgId(fileProcessDefBean.getProgId());
	}
	}

	private void mapBeanToForm() {
		Map<String,String> prcsTypeMap = MasterDataLookup.getInstance().getProcessTypeAsMap();
		if(fileProcessDefForm !=null && fileProcessDefBean != null){
			String numString;
			
			if(fileProcessDefBean.getProcessDefinitionId() == 0){
				numString = WebHelper.BLANK_STRING;
			}
			else{
				numString = String.valueOf(fileProcessDefBean.getProcessDefinitionId());
			}
			fileProcessDefForm.setProcessDefinitionId(numString);
			
			if(fileProcessDefBean.getDefinitionId() == 0){
				numString = WebHelper.BLANK_STRING;
			}
			else{
				numString = String.valueOf(fileProcessDefBean.getDefinitionId());
			}
			fileProcessDefForm.setDefinitionId(numString);
			setDefType(fileProcessDefBean.getDefinitionType().trim());
			if(!WebHelper.isEmptyOrNullString(fileProcessDefBean.getDefinitionType())){
				fileProcessDefForm.setDefinitionType(prcsTypeMap.get(fileProcessDefBean.getDefinitionType().trim()));
			}
			if(!WebHelper.isEmptyOrNullString(fileProcessDefBean.getReasonForUpdate())){
				fileProcessDefForm.setReasonForUpdate(fileProcessDefBean.getReasonForUpdate().trim());
			}
			if(!WebHelper.isEmptyOrNullString(fileProcessDefBean.getProcessingTrigger())){
				fileProcessDefForm.setProcessingTrigger(WebHelper.makeNonNullString(fileProcessDefBean.getProcessingTrigger().trim()));
			}
			if(fileProcessDefBean.getProcessDate()!=null){
				fileProcessDefForm.setProcessDate(DateCalenderUtil.getDatefromCalendar(fileProcessDefBean.getProcessDate()));
			}
			if(fileProcessDefBean.getProcessingTime()!=null){
				fileProcessDefForm.setProcessingTime(WebHelper.convertTimeToString(fileProcessDefBean.getProcessingTime()));
			}
			fileProcessDefForm.setStatus(fileProcessDefBean.getStatus());	
			validateReprocess(fileProcessDefBean);
			fileProcessDefForm.setRecCount(fileProcessDefBean.getRecCount());
			fileProcessDefForm.setProgId(fileProcessDefBean.getProgId());
		}
	}

	/**
	 * @return the processId
	 */
	public int getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(int processId) {
		this.processId = processId;
	}


	public FileProcessDefForm getFileProcessDefForm() {
		return fileProcessDefForm;
	}


	public void setFileProcessDefForm(FileProcessDefForm fileProcessDefForm) {
		this.fileProcessDefForm = fileProcessDefForm;
	}


	public FileProcessDefBean getFileProcessDefBean() {
		return fileProcessDefBean;
	}


	public void setFileProcessDefBean(FileProcessDefBean fileProcessDefBean) {
		this.fileProcessDefBean = fileProcessDefBean;
	}
	
	/**
	 * @return the processType
	 */
	public String getProcessType() {
		return processType;
	}

	/**
	 * @param processType the processType to set
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	
	public boolean validateReprocess(FileProcessDefBean fileProcessDefBean){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    boolean processFlag = false;
	    boolean reProcessFlag = false;
	    if(IConstants.COMMISSION_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType().trim()) || 
	    		IConstants.FIXED_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType().trim()) || 
	    		IConstants.VARIABLE_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType().trim()) || 
	    		IConstants.LEADERSHIP_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType().trim()) || 
	    		IConstants.FILE_PROCESS_NAME.equalsIgnoreCase(fileProcessDefBean.getDefinitionType().trim())){
	    	processFlag = true;
	    }
		if((IConstants.PROC_STATUS_SUCCESS.equals(fileProcessDefBean.getStatus()) || IConstants.PROC_STATUS_REPROCESS.equals(fileProcessDefBean.getStatus()))&& processFlag && IConstants.USER_ROLE_ADMIN.equalsIgnoreCase(this.getUserRole()) && !isPaymentSuccess) {
			if(fileProcessDefBean.getProcessDate().getTime().equals(cal.getTime())){
			 fileProcessDefForm.setReProcessFlag(true);
			 reProcessFlag = true;
		}
		}
		
		return reProcessFlag;
	}
	public String getShouldRefreshParent() {
		return shouldRefreshParent;
	}
	public void setShouldRefreshParent(String shouldRefreshParent) {
		this.shouldRefreshParent = shouldRefreshParent;
	}
	
	public int checkFileType(String baseFolder){
		int fileType =0;
		if(baseFolder.toUpperCase().contains("VISTA")){
			fileType = 1;
		}else if(baseFolder.toUpperCase().contains("ACCRUAL")){
			fileType = 2;
		}else if(baseFolder.toUpperCase().contains("KPI")){
			fileType = 3;
		} else {
			fileType =0;
		}
		return fileType;
	}
}
