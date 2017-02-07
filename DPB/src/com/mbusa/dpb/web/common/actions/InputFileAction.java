/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: InputFileAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all request related to file definition.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 14, 2013     	  1.0        First Draft
 * Syntel   Aug 27, 2013     	  1.0        Done changes as per new requirement
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.common.actions;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.commons.beanutils.BeanUtils;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.DefStatus;
import com.mbusa.dpb.common.domain.FileDefinitionBean;
import com.mbusa.dpb.common.domain.FileFormatBean;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.domain.Scheduler;
import com.mbusa.dpb.common.domain.Trigger;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.form.FileDefinitionForm;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class InputFileAction extends DPBAction {
	static final private String CLASSNAME = InputFileAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(InputFileAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FileDefinitionForm fileDefForm;
	private FileDefinitionBean fileDefBean;
	private String fileId;
	private String view;
	java.sql.Date rEndDate;
	java.sql.Date startDate;
	List<FileDefinitionBean> fileDefList = new ArrayList<FileDefinitionBean>();
	List<FileFormatBean> fileFormat = new ArrayList<FileFormatBean>();
	private String actionForward = IWebConstants.DEFAULT_ERROR_PAGE;
	private String message = "";
	BusinessDelegate bDelegate = new BusinessDelegate();
	List<Trigger> defTgr = null;
	List<Scheduler> defSchd = null;
	List<DefStatus> defSts = null;
	List<java.sql.Date> aSchdDatesList= null;
	boolean fromDPBList;
	
	public String getFileDetails() {
		final String methodName = "getFileDetails";
		LOGGER.enter(CLASSNAME, methodName);
		try {
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			defSchd = MasterDataLookup.getInstance().getSchedulerCodes();
			defTgr = MasterDataLookup.getInstance().getTriggerCodes();
			defSts = MasterDataLookup.getInstance().getDefStatusCodes();
			if (fileId != null) {
				fromDPBList = true;
				actionForward = "inputFileView";
				fileDefBean = bDelegate.getEditFileDetails(fileId);
				fileDefForm = new FileDefinitionForm();
				BeanUtils.copyProperties(fileDefForm, fileDefBean);
				fileDefForm.setStatus(fileDefBean.getDefStatusCode());
				populateFileDefinitionForm();
				if(!"".equalsIgnoreCase(view) && "view".equalsIgnoreCase(view)){
						fileDefForm.setDefStatusCode(WebHelper.getStatusString(fileDefForm.getDefStatusCode(),defSts));
						fileDefForm.setTriggerCode(WebHelper.getTriggerString(fileDefForm.getTriggerCode(),defTgr));
						fileDefForm.setScheduleCode(WebHelper.getScheduleString(fileDefForm.getScheduleCode(),defSchd));
						fileDefForm.setIndCondition(WebHelper.getYesOrNoString(fileDefForm.getIndCondition()));
						if(fileDefForm.getDescription() != null){
							fileDefForm.setFileDescription(fileDefForm.getDescription().split(IWebConstants.DESC_EXPSN));
						}
					actionForward = "inputFileReadOnlyView";
				}
			}
			else if (fileId == null && !"view".equalsIgnoreCase(view)) {
				actionForward = "inputFileView";
					fileDefForm = new FileDefinitionForm();
					fileFormat = bDelegate.populateformatNameList();
					fileDefForm.setFileDefId(0);
					fileDefForm.setIndCondition(IWebConstants.CONSTANT_N);
					fileDefForm.setTriggerCode(IWebConstants.SYSTEM_INITITION);
					fileDefForm.setDefStatusCode(IWebConstants.DRAFT);
					fileDefForm.setFileFormat(fileFormat);
					if(fileFormat.size() == 0){
						message = IWebConstants.FILE_DEFN_NO_ACT_FMT;
					}
			}
		}catch(BusinessException be){
			LOGGER.error("INP.ACT.004" + "BusinessException occured" + be.getMessage() + be.getMessageKey());
			be.printStackTrace();
			addActionError(IWebConstants.BUSS_EXC_RETV);
		}catch (TechnicalException te) {
			LOGGER.error("INP.ACT.005" + "TechincalException occured" + te.getMessage() + te.getMessageKey());
			te.printStackTrace();
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (PersistenceException pe) {
			LOGGER.error("INP.ACT.006" + "PersistenceException occured" + pe.getMessage());
			pe.printStackTrace();
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			LOGGER.error("INP.ACT.007" + "Exception occured" + e.getMessage());
			e.printStackTrace();
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		addActionMessage(message);
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}
public String saveFileDefinition() {
	final String methodName = "SaveFileDefinition";
	LOGGER.enter(CLASSNAME, methodName);
		try {
			LOGGER.info("saveFileDefinition:"+fileDefForm);
			LOGGER.info("saveFileDefinition to String:"+fileDefForm.toString());
			defSchd = MasterDataLookup.getInstance().getSchedulerCodes();
			defTgr = MasterDataLookup.getInstance().getTriggerCodes();
			defSts = MasterDataLookup.getInstance().getDefStatusCodes();
			validateFileDefinition();
			actionForward = "inputFileDefine";
			if(!hasActionErrors()){
				if(fileDefForm.getFileDefId() > 0){
					message = "Input File updated successfully.";
				}else{
					if(fileDefForm.isFlagActive()){
					fileDefForm.getFileFormat().removeAll(fileDefList);
					}
					message = "Input File created successfully." ;
				}fileDefBean = new FileDefinitionBean();
				BeanUtils.copyProperties(fileDefBean, fileDefForm);
				fileDefBean.setCreatedUserId(this.getUserId());
				fileDefBean.setLastUpdUserId(this.getUserId());
				populateFileDefinitionBean();
				bDelegate.saveFileDetails(fileDefBean, aSchdDatesList);
				BeanUtils.copyProperties(fileDefForm, fileDefBean);
				fileDefForm.setStatus(fileDefBean.getDefStatusCode());
				if(IWebConstants.INACTIVE.equals(fileDefBean.getDefStatusCode()) || IWebConstants.ACTIVE.equals(fileDefBean.getDefStatusCode())){
					fileDefForm.setFlagActive(true);
				}
				addActionMessage(message);
			}
		}catch(BusinessException be){
			LOGGER.error("INP.ACT.007" + "BusinessException occured" + be.getMessage() + be.getMessageKey());
			be.printStackTrace();
			if("proc".equals(be.getMessageKey())){
				addActionError(be.getMessage());
				fileDefForm.setDefStatusCode(IWebConstants.DRAFT);
			}else if("invSchd".equals(be.getMessageKey())){
				addActionError(be.getMessage());
				fileDefForm.setDefStatusCode(IWebConstants.DRAFT);
			}else if("DAY_FEDERATED_EXCEPTION".equals(be.getMessageKey())){ 
				request.setAttribute (IWebConstants.jspExe, be);
				actionForward =  IWebConstants.DPB_ERROR_URL;
			}
			else{
				addActionError(IWebConstants.BUSS_EXC_SAVE);
			}
		}catch (TechnicalException te) {
			te.printStackTrace();
			LOGGER.error("INP.ACT.001" + "TechnicalException occured" + te.getMessage() + te.getMessageKey());
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (PersistenceException pe) {
			pe.printStackTrace();
			LOGGER.error("INP.ACT.002" + "PersistenceException occured" + pe.getMessage());
			
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("INP.ACT.003" + "Exception occured" + e.getMessage());
			request.setAttribute (IWebConstants.jspExe, new Exception("FILE.ACT.CLASS.001" + "Exception from InputFileAction"));
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}

	public void populateFileDefinitionBean() {
		fileDefBean.setInputTime(WebHelper.convertStringToTime(fileDefForm.getInTime()));
		fileDefBean.setProceTime(WebHelper.convertStringToTime(fileDefForm.getReadTime()));
	}

	public void populateFileDefinitionForm() {
		fileDefForm.setInTime(WebHelper.convertTimeToString(fileDefBean.getInputTime()));
		fileDefForm.setReadTime(WebHelper.convertTimeToString(fileDefBean.getProceTime()));
		if(IWebConstants.INACTIVE.equals(fileDefBean.getDefStatusCode()) || IWebConstants.ACTIVE.equals(fileDefBean.getDefStatusCode())){
		fileDefForm.setFlagActive(true);
		}
	}
	
	public void validateFileDefinition() throws BusinessException, TechnicalException{
		final String methodName = "getFileFormatDetails";
		LOGGER.enter(CLASSNAME, methodName);
		if(IWebConstants.INACTIVE.equalsIgnoreCase(fileDefForm.getDefStatusCode()) && fileDefForm.getFileDefId() <= 0){
			addActionError("Cannot create a new Inactive Input File.");
		}
		else if(IWebConstants.ACTIVE.equalsIgnoreCase(fileDefForm.getDefStatusCode()) && fileDefForm.isFlagActive() && fileDefForm.getFileDefId() > 0){
			addActionError("Cannot re-submit an Active Input File. ");
		}
		else if("".equals(fileDefForm.getFileDefName()) || fileDefForm.getFileDefName().length() > 40){
			addActionError("Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.");
		}
		else if((IWebConstants.ACTIVE.equalsIgnoreCase(fileDefForm.getDefStatusCode()) && "".equals(fileDefForm.getFileNameFormat())) || fileDefForm.getFileNameFormat().length() > 40){
			addActionError("Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.");
		}
		else if(fileDefForm.getDescription().length() > 255){
			addActionError("Description should be less than 255 characters.");
		}
		else if((IWebConstants.ACTIVE.equalsIgnoreCase(fileDefForm.getDefStatusCode()) && "".equals(fileDefForm.getBaseFolder())) || fileDefForm.getBaseFolder().length() > 255){
			addActionError("Enter base directory (less than 255 characters).");
		}
		else if(IWebConstants.ACTIVE.equalsIgnoreCase(fileDefForm.getDefStatusCode()) && "".equals(fileDefForm.getScheduleCode())){
			addActionError("Select File Process Schedule.");
		}
		else if(fileDefForm.getFileFormatId() == 0){
			fileFormat = bDelegate.populateformatNameList();
			if(fileFormat != null && fileFormat.size() > 0){
				fileDefForm.setFileFormat(fileFormat);
			}
			addActionError("No 'Active' file format selected. Please select one.");
		}else if(IWebConstants.ACTIVE.equalsIgnoreCase(fileDefForm.getDefStatusCode())){
			startDate = Date.valueOf(WebHelper.getNextDayDate());
			// TODO -- Commented until DAY_FEDERATED LOADS WITH PROPER DATA
			rEndDate = bDelegate.getEndDate(startDate);
			if(startDate.compareTo(rEndDate) > 0){
				addActionError("Retail Date not available. Please modify the Retail Date.");
			}else{
				aSchdDatesList = decideExecutionProcessesDates(fileDefForm, startDate, rEndDate);
			}
			//rEndDate = WebHelper.convertStringToDate("12/31/2013");
			aSchdDatesList = decideExecutionProcessesDates(fileDefForm, startDate, rEndDate);
		}
		LOGGER.exit(CLASSNAME, methodName);
	}	

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public FileDefinitionForm getFileDefForm() {
		return fileDefForm;
	}

	public void setFileDefForm(FileDefinitionForm fileDefForm) {
		this.fileDefForm = fileDefForm;
	}
	/**
	 * @return the defTgr
	 */
	public List<Trigger> getDefTgr() {
		return defTgr;
	}
	/**
	 * @param defTgr the defTgr to set
	 */
	public void setDefTgr(List<Trigger> defTgr) {
		this.defTgr = defTgr;
	}
	/**
	 * @return the defSchd
	 */
	public List<Scheduler> getDefSchd() {
		return defSchd;
	}
	/**
	 * @param defSchd the defSchd to set
	 */
	public void setDefSchd(List<Scheduler> defSchd) {
		this.defSchd = defSchd;
	}
	/**
	 * @return the defSts
	 */
	public List<DefStatus> getDefSts() {
		return defSts;
	}
	/**
	 * @param defSts the defSts to set
	 */
	public void setDefSts(List<DefStatus> defSts) {
		this.defSts = defSts;
	}
	
	public boolean isFromDPBList() {
		return fromDPBList;
	}
	public void setFromDPBList(boolean fromDPBList) {
		this.fromDPBList = fromDPBList;
	}
	public List<java.sql.Date> decideExecutionProcessesDates(FileDefinitionForm fileDefForm,java.sql.Date startDate,java.sql.Date rEndDate) throws BusinessException{
		LOGGER.info("decideExecutionProcessesDates Entry");
		String scheduleProcess =IConstants.EMPTY_STR;
		List<java.sql.Date> datesList = null;
			aSchdDatesList = new ArrayList<java.sql.Date>();	
			datesList = new ArrayList<java.sql.Date>();
			scheduleProcess = fileDefForm.getScheduleCode();
			
			if(!scheduleProcess.isEmpty()){
				datesList = DateCalenderUtil.scheduleList(startDate, rEndDate, scheduleProcess);
			}
			
			if(datesList == null || datesList.size()==0 && !scheduleProcess.isEmpty()){
				addActionError("DPB Process could not be scheduled");
			}
			
			if(!datesList.isEmpty()){
				if(scheduleProcess.equalsIgnoreCase(IConstants.SCHD_MONTH) || scheduleProcess.equalsIgnoreCase(IConstants.SCHD_QUERTERLY) || scheduleProcess.equalsIgnoreCase(IConstants.SCHD_YEARLY)){
					for(Iterator<java.sql.Date> sDate = datesList.iterator();sDate.hasNext();){
						java.sql.Date schdDate = sDate.next();
						java.sql.Date schdActualDate = null;
						RtlCalenderDefinition rtlDef = MasterDataLookup.getInstance().getRtlCalByDate(schdDate);
						if(rtlDef.getDteRetlYearEnd() == null || rtlDef.getDteRetlQtrEnd() == null || rtlDef.getDteRetlMthEnd() == null){
							throw new BusinessException("DAY_FEDERATED_EXCEPTION","Day Federated Data is not available to schedule processes");
						}else{
						if(scheduleProcess.equalsIgnoreCase(IConstants.SCHD_MONTH)){
							schdActualDate =DateCalenderUtil.scheduleNextDate(rtlDef.getDteRetlMthEnd());
							if(schdActualDate == null){
								addActionError("DPB Process could not be scheduled");
								break;
							}else if(rEndDate.equals(rtlDef.getDteRetlMthEnd())){
								aSchdDatesList.add(rEndDate);
							}else {
								aSchdDatesList.add(schdActualDate);
							}

						}else if(scheduleProcess.equalsIgnoreCase(IConstants.SCHD_QUERTERLY)){
							schdActualDate =DateCalenderUtil.scheduleNextDate(rtlDef.getDteRetlQtrEnd());
							if(schdActualDate == null){
								addActionError("DPB Process could not be scheduled");
								break;
							}else if(rEndDate.equals(rtlDef.getDteRetlMthEnd())){
								aSchdDatesList.add(rEndDate);
							}else {
								aSchdDatesList.add(schdActualDate);
							}
						}else if(scheduleProcess.equalsIgnoreCase(IConstants.SCHD_YEARLY)){
							schdActualDate =DateCalenderUtil.scheduleNextDate(rtlDef.getDteRetlYearEnd());
							if(schdActualDate == null){
								addActionError("DPB Process could not be scheduled");
								break;
							}else if(rEndDate.equals(rtlDef.getDteRetlMthEnd())){
								aSchdDatesList.add(rEndDate);
							}else {
								aSchdDatesList.add(schdActualDate);
							}
						}
					}
				}
				}else{
					aSchdDatesList = datesList;
				}
			}
		LOGGER.info("decideExecutionProcessesDates Exit");
		return aSchdDatesList;
	}
}