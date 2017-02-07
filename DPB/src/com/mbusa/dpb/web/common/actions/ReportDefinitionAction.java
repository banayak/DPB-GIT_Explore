/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportDefinitionBean.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all request related to report definition.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 01, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.common.actions;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.ibm.ws.gridcontainer.exceptions.PersistenceException;
import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.DefStatus;
import com.mbusa.dpb.common.domain.QCRelationBean;
import com.mbusa.dpb.common.domain.ReportDefinitionBean;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.domain.Scheduler;
import com.mbusa.dpb.common.domain.Trigger;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.form.ReportDefinitionForm;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class ReportDefinitionAction extends DPBAction {

	static final private String CLASSNAME = ReportDefinitionAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(ReportDefinitionAction.class);
	
	private static final long serialVersionUID = 1L;
	private ReportDefinitionForm reportDefForm;
	private ReportDefinitionBean reportDefBean;
	private QCRelationBean qcrBean;
	private String reportDefId;
	private String view;
	java.sql.Date rEndDate;
	private List<QCRelationBean> qcrList;
	public BusinessDelegate bDelegate  = new BusinessDelegate();
	private String actionForward = IWebConstants.DEFAULT_ERROR_PAGE;;
	private String message = "";
	List<Scheduler> defSchd = null;
	List<Trigger> defTgr = null;
	List<DefStatus> defSts = null;
	List<java.sql.Date> aSchdDatesList= null;
	boolean fromDPBList;
	
	public String getNewReportDefinition(){
		final String methodName = "getNewReportDefinition";
		LOGGER.enter(CLASSNAME, methodName);		
		try {
		this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			actionForward = "reportDefView";
			defSchd = MasterDataLookup.getInstance().getSchedulerCodes();
			defTgr = MasterDataLookup.getInstance().getTriggerCodes();
			defSts = MasterDataLookup.getInstance().getDefStatusCodes();
			if(reportDefId == null && reportDefForm == null){
				reportDefForm = new ReportDefinitionForm();
				reportDefForm.setTriggerCode(IWebConstants.SYSTEM_INITITION);
				reportDefForm.setDefStatusCode(IWebConstants.DRAFT);
			}else if(reportDefForm != null){
				reportDefBean = new ReportDefinitionBean();
				BeanUtils.copyProperties(reportDefBean, reportDefForm);
				reportDefBean.setReportContentList(MasterDataLookup.getInstance().getReportContentList());
				reportDefBean.setReportQueryList(MasterDataLookup.getInstance().getReportQryList());
				if(reportDefBean != null && reportDefBean.getReportQueryList() 
						!= null && reportDefBean.getReportContentList() != null && reportDefBean.getReportContentList().size() > 0 && 
						reportDefBean.getReportQueryList().size() > 0){
					BeanUtils.copyProperties(reportDefForm, reportDefBean);
					reportDefForm.setStatus(reportDefBean.getDefStatusCode());
					populateSequenceNumberColums(reportDefForm.getSubReports());
				}else{
					message = IWebConstants.RPT_DEFN_NO_ACT_RPT;
					reportDefForm.setSubReports(0);
				}
			}
			else if(reportDefId != null){
				fromDPBList = true;
				reportDefBean  = bDelegate.getEditReportDefinition(reportDefId);
				reportDefBean.setReportContentList(MasterDataLookup.getInstance().getReportContentList());
				reportDefBean.setReportQueryList(MasterDataLookup.getInstance().getReportQryList());
				if(reportDefBean != null && reportDefBean.getReportQueryList() 
						!= null && reportDefBean.getReportContentList() != null && reportDefBean.getReportContentList().size() > 0 && 
						reportDefBean.getReportQueryList().size() > 0 && reportDefBean.getQcrList().size() > 0 && 
						reportDefBean.getQcrList().get(0).getSeqNum() > 0){
				reportDefForm = new ReportDefinitionForm();
				BeanUtils.copyProperties(reportDefForm, reportDefBean);
				reportDefForm.setStatus(reportDefBean.getDefStatusCode());
				if(IWebConstants.INACTIVE.equals(reportDefBean.getDefStatusCode()) || IWebConstants.ACTIVE.equals(reportDefBean.getDefStatusCode())){
					reportDefForm.setFlagActive(true);
					}
				/*message ="Record Retrieved Successfully";*/
				}else {
					populateReportDefinitionForm();
					message = " Record Does not contain sub reports.";
				}
				if(!"".equalsIgnoreCase(view) && "view".equalsIgnoreCase(view)){
					reportDefForm.setScheduleCode(WebHelper.getScheduleString(reportDefForm.getScheduleCode(),defSchd));
					reportDefForm.setDefStatusCode(WebHelper.getStatusString(reportDefForm.getDefStatusCode(),defSts));
					reportDefForm.setTriggerCode(WebHelper.getTriggerString(reportDefForm.getTriggerCode(),defTgr));
					if(reportDefForm.getDescription() != null){
						reportDefForm.setReportDescription(reportDefForm.getDescription().split(IWebConstants.DESC_EXPSN));
					}
					actionForward = "reportDefReadOnlyView";
				}
			}
		}catch(BusinessException be){
			LOGGER.error("BusinessException occured");
			addActionError(IWebConstants.BUSS_EXC_RETV);
		}catch (TechnicalException te) {
			LOGGER.error("TechnicalException occured");
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (PersistenceException pe) {
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			LOGGER.error("Exception occured");
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		addActionMessage(message);
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;

	}
	public String saveReportDefinition(){
		final String methodName = "saveReportDefinition";
		LOGGER.enter(CLASSNAME, methodName);
		try {
			actionForward = "reportDefView";
			defSchd = MasterDataLookup.getInstance().getSchedulerCodes();
			defTgr = MasterDataLookup.getInstance().getTriggerCodes();
			defSts = MasterDataLookup.getInstance().getDefStatusCodes();
			validateReportDefinition();
				if(!hasActionErrors()){
					if(reportDefForm.getReportDefId() == 0){
						if(reportDefForm.getSubReports() == 0 && reportDefForm.getQcrList().size() > 0){
							reportDefForm.getQcrList().removeAll(reportDefForm.getQcrList());
						}
						message = " Report saved successfully.";
					}else {
						message = " Report updated successfully.";
					}
					reportDefBean = new ReportDefinitionBean();
					BeanUtils.copyProperties(reportDefBean, reportDefForm);
					reportDefBean.setCreatedByUser(this.getUserId());
					reportDefBean.setUpdatedByUser(this.getUserId());
					reportDefBean = bDelegate.insertReportDefinition(reportDefBean, aSchdDatesList);
					reportDefForm.setReportDefId(reportDefBean.getReportDefId());
					reportDefBean.setReportContentList(MasterDataLookup.getInstance().getReportContentList());
					reportDefBean.setReportQueryList(MasterDataLookup.getInstance().getReportQryList());
					if(reportDefBean != null && reportDefBean.getReportQueryList() 
							!= null && reportDefBean.getReportContentList() != null && reportDefBean.getReportContentList().size() > 0 && 
							reportDefBean.getReportQueryList().size() > 0){
						BeanUtils.copyProperties(reportDefForm, reportDefBean);
						reportDefForm.setStatus(reportDefBean.getDefStatusCode());
						if(IWebConstants.INACTIVE.equals(reportDefBean.getDefStatusCode()) || IWebConstants.ACTIVE.equals(reportDefBean.getDefStatusCode())){
							reportDefForm.setFlagActive(true);
							}
					}
					else{
						populateReportDefinitionForm();
						message = " Report created successfully (Does not contain sub reports)";
					}
					addActionMessage(message);
				}
		} catch(BusinessException be){
			LOGGER.error("BusinessException occured");
			if("proc".equals(be.getMessageKey())){
				addActionError(be.getMessage());
				reportDefForm.setDefStatusCode(IWebConstants.DRAFT);
			}else if("invSchd".equals(be.getMessageKey())){
				addActionError(be.getMessage());
				reportDefForm.setDefStatusCode(IWebConstants.DRAFT);
			}else if("DAY_FEDERATED_EXCEPTION".equals(be.getMessageKey())){ 
				request.setAttribute (IWebConstants.jspExe, be);
				actionForward =  IWebConstants.DPB_ERROR_URL;
			}else {
				addActionError(IWebConstants.BUSS_EXC_SAVE);
			}
		}catch (TechnicalException te) {
			LOGGER.error("TechnicalException occured");
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (PersistenceException pe) {
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			LOGGER.error("Exception occured");
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
 		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;

	}
	
	private ReportDefinitionForm populateSequenceNumberColums(int colCount) throws BusinessException, TechnicalException {
		final String methodName = "populateSequenceNumberColums";
		LOGGER.enter(CLASSNAME, methodName);
		qcrList = new ArrayList<QCRelationBean>();
		for (int cnt = 1; cnt <= colCount; cnt++) {
			 qcrBean = new QCRelationBean();
			 qcrBean.setSeqNum(cnt);
			 qcrList.add(qcrBean);
		}
		reportDefForm.setQcrList(qcrList);
		LOGGER.exit(CLASSNAME, methodName);
		return reportDefForm;
	}
	public ReportDefinitionForm getReportDefForm() {
		return reportDefForm;
	}

	public void setReportDefForm(ReportDefinitionForm reportDefForm) {
		this.reportDefForm = reportDefForm;
	}
	
public void populateReportDefinitionForm(){
	final String methodName = "populateReportDefinitionForm";
	LOGGER.enter(CLASSNAME, methodName);
	reportDefForm = new ReportDefinitionForm();
	reportDefForm.setReportDefId(reportDefBean.getReportDefId());
	reportDefForm.setReportName(reportDefBean.getReportName());
	reportDefForm.setDescription(reportDefBean.getDescription());
	reportDefForm.setScheduleCode(reportDefBean.getScheduleCode());
	reportDefForm.setTriggerCode(reportDefBean.getTriggerCode());
	reportDefForm.setDefStatusCode(reportDefBean.getDefStatusCode());
	reportDefForm.setReportLpp(reportDefBean.getReportLpp());
	LOGGER.exit(CLASSNAME, methodName);
}

public void validateReportDefinition() throws BusinessException, TechnicalException{
	final String methodName = "getFileFormatDetails";
	LOGGER.enter(CLASSNAME, methodName);
	if(IWebConstants.INACTIVE.equalsIgnoreCase(reportDefForm.getDefStatusCode()) && reportDefForm.getReportDefId() <= 0){
		addActionError("Cannot create a new Inactive Report.");
	}
	else if(IWebConstants.ACTIVE.equalsIgnoreCase(reportDefForm.getDefStatusCode()) && reportDefForm.isFlagActive() && reportDefForm.getReportDefId() > 0){
		addActionError(" Cannot re-submit an Active Report.");
	}
	else if("".equals(reportDefForm.getReportName()) || reportDefForm.getReportName().length() > 40){
		addActionError("Report Name should contain 40 characters or less and should be alphanumeric with spaces.");
	}
	else if(IWebConstants.ACTIVE.equalsIgnoreCase(reportDefForm.getDefStatusCode()) && reportDefForm.getSubReports() <= 0){
		addActionError("Select the no of sub reports.");
	}
	else if(IWebConstants.ACTIVE.equalsIgnoreCase(reportDefForm.getDefStatusCode()) && "".equals(reportDefForm.getScheduleCode())){
		addActionError("Select a process schedule");
	}
	else if(reportDefForm.getDescription().length() > 255){
		addActionError("Description should be less than 255 characters.");
	}
	else if(IWebConstants.ACTIVE.equalsIgnoreCase(reportDefForm.getDefStatusCode())){
		java.sql.Date startDate = Date.valueOf(WebHelper.getNextDayDate());
		// TODO -- Commented until DAY_FEDERATED LOADS WITH PROPER DATA
		rEndDate = bDelegate.getEndDate(startDate);
		if(startDate.compareTo(rEndDate) > 0){
			addActionError("Please Create Retails Month Defintion with valid date.");
		}else{
			aSchdDatesList = decideExecutionProcessesDates(reportDefForm, startDate, rEndDate);
		}
		//rEndDate = WebHelper.convertStringToDate("12/31/2013");
		aSchdDatesList = decideExecutionProcessesDates(reportDefForm, startDate, rEndDate);
		
	}
	LOGGER.exit(CLASSNAME, methodName);
}

	public String getReportDefId() {
		return reportDefId;
	}
	public void setReportDefId(String reportDefId) {
		this.reportDefId = reportDefId;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
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
	public List<java.sql.Date> decideExecutionProcessesDates(ReportDefinitionForm reportDefForm,java.sql.Date startDate,java.sql.Date rEndDate) throws BusinessException{

		String scheduleProcess =IConstants.EMPTY_STR;
		List<java.sql.Date> datesList = null;
			aSchdDatesList = new ArrayList<java.sql.Date>();	
			datesList = new ArrayList<java.sql.Date>();
			scheduleProcess = reportDefForm.getScheduleCode();
			
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
		return aSchdDatesList;
	}
}