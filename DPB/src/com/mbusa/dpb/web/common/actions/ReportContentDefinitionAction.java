/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportContentDefinitionAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all request related to report definition.
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

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.ibm.ws.gridcontainer.exceptions.PersistenceException;
import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.DefStatus;
import com.mbusa.dpb.common.domain.ReportContentDefinitionBean;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.form.ReportContentDefinitionForm;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class ReportContentDefinitionAction extends DPBAction{

	private static final long serialVersionUID = -8513870418684845042L;
	static final private String CLASSNAME = ReportContentDefinitionAction.class.getName();	
	private static DPBLog LOGGER = DPBLog.getInstance(ReportContentDefinitionAction.class);
	
	private String actionForward = IWebConstants.DEFAULT_ERROR_PAGE;
	private String reportContentDefinitionId;
	private ReportContentDefinitionForm reportContentDefForm;
	private ReportContentDefinitionBean reportContentDefBean;
	private String rptCntDefId = null;
	private String view = null;
	private String message = "";
	private BusinessDelegate bDelegate  = new BusinessDelegate();
	List<DefStatus> defSts = null;
	boolean fromDPBList;
	
	public String getReportContentDefinition(){
		final String methodName = "getReportContentDefinition";
		LOGGER.enter(CLASSNAME, methodName);
		try {
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			actionForward = "reportContentDefView";
			defSts = MasterDataLookup.getInstance().getDefStatusCodes();
			if(rptCntDefId == null && reportContentDefForm == null){
				reportContentDefForm = new ReportContentDefinitionForm();
				reportContentDefForm.setDefStatusCode(IWebConstants.DRAFT);
			}
			else if(rptCntDefId != null){
				fromDPBList = true;
				reportContentDefBean = bDelegate.getEditReportContent(rptCntDefId);
				reportContentDefForm = new ReportContentDefinitionForm();
				BeanUtils.copyProperties(reportContentDefForm, reportContentDefBean);
				reportContentDefForm.setStatus(reportContentDefBean.getDefStatusCode());
				if(IWebConstants.INACTIVE.equals(reportContentDefBean.getDefStatusCode()) || IWebConstants.ACTIVE.equals(reportContentDefBean.getDefStatusCode())){
					reportContentDefForm.setFlagActive(true);
					}
			}
			if(!"".equalsIgnoreCase(view) && "view".equalsIgnoreCase(view)){
				reportContentDefForm.setDefStatusCode(WebHelper.getStatusString(reportContentDefForm.getDefStatusCode(),defSts));
				if(reportContentDefForm.getReportContent() != null){
					reportContentDefForm.setContent(reportContentDefForm.getReportContent().split(IWebConstants.DESC_EXPSN));
				}
				if(reportContentDefForm.getReportContentDescription() != null){
					reportContentDefForm.setDescription(reportContentDefForm.getReportContentDescription().split(IWebConstants.DESC_EXPSN));
				
				}
				actionForward = "rprCntDefReadOnlyView";
			}
		}catch(BusinessException be){
			LOGGER.error("BusinessException occured");
			if("msg".equals(be.getMessageKey())){
				addActionError(be.getMessage());
			}else{
				addActionError(IWebConstants.BUSS_EXC_RETV);
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

	
	public String saveReportContentDefinition(){		
		final String methodName = "saveReportContentDefinition";
		LOGGER.enter(CLASSNAME, methodName);
		try {
			actionForward = "reportContentDefView";
			defSts = MasterDataLookup.getInstance().getDefStatusCodes();
			validateReportContent();
			if(!hasActionErrors()){
			if(reportContentDefForm.getReportContentDefId() > 0){
				message = "Report Content updated successfully.";
			}else{
				message = "Report Content created successfully.";
			}
			reportContentDefBean = new ReportContentDefinitionBean();
			BeanUtils.copyProperties(reportContentDefBean, reportContentDefForm);
			reportContentDefBean.setCreatedByUser(this.getUserId());
			reportContentDefBean.setUpdatedByUser(this.getUserId());
			int reportContentDefId = bDelegate.insertReportContentDefinition(reportContentDefBean);
			MasterDataLookup.getInstance().refreshCacheValue(IWebConstants.RPT_CTNT_RERS);
			reportContentDefBean.setReportContentDefId(reportContentDefId);
			BeanUtils.copyProperties(reportContentDefForm, reportContentDefBean);
			reportContentDefForm.setStatus(reportContentDefBean.getDefStatusCode());
			if(IWebConstants.INACTIVE.equals(reportContentDefBean.getDefStatusCode()) || IWebConstants.ACTIVE.equals(reportContentDefBean.getDefStatusCode())){
				reportContentDefForm.setFlagActive(true);
				}
			addActionMessage(message);
		}
		}catch(BusinessException be){
			LOGGER.error("BusinessException occured");
			if("msg".equals(be.getMessageKey())){
				addActionError(be.getMessage());
				reportContentDefForm.setDefStatusCode(IWebConstants.ACTIVE);
			}else{
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
	
	public void validateReportContent(){
		final String methodName = "getFileFormatDetails";
		LOGGER.enter(CLASSNAME, methodName);
		if(IWebConstants.INACTIVE.equalsIgnoreCase(reportContentDefForm.getDefStatusCode()) &&reportContentDefForm.getReportContentDefId() <= 0){
			addActionError("Cannot create a new Inactive Report Content.");
		}
		else if(IWebConstants.ACTIVE.equalsIgnoreCase(reportContentDefForm.getDefStatusCode()) && reportContentDefForm.isFlagActive() && reportContentDefForm.getReportContentDefId() > 0){
			addActionError("Cannot re-submit an Active Report Content. ");
			}
		else if(IWebConstants.ACTIVE.equalsIgnoreCase(reportContentDefForm.getDefStatusCode()) && ("".equals(reportContentDefForm.getReportContentName()) || reportContentDefForm.getReportContentName().length() > 40)){
			addActionError("Name should contain up ato 40 characters. May contain alphanumeric, spaces and underscores.");
			}
		else if(reportContentDefForm.getReportContentDescription().length() > 255){
			addActionError("Description should be less than 255 characters.");
			}
		else if(IWebConstants.ACTIVE.equalsIgnoreCase(reportContentDefForm.getDefStatusCode()) && "".equals(reportContentDefForm.getReportContent())){
			addActionError("Enter content.");
			}
		else if(IWebConstants.DRAFT.equalsIgnoreCase(reportContentDefForm.getDefStatusCode()) && "".equals(reportContentDefForm.getReportContentName())){
			addActionError("Name should contain up ato 40 characters. May contain alphanumeric, spaces and underscores.");
			}
		else if("".equals(reportContentDefForm.getReportContentName()) || reportContentDefForm.getReportContentName().length() > 40){
			addActionError("Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.");
		}
		LOGGER.exit(CLASSNAME, methodName);

	}
	/**
	 * @return the reportContentDefinitionId
	 */
	public String getReportContentDefinitionId() {
		return reportContentDefinitionId;
	}

	/**
	 * @param reportContentDefinitionId the reportContentDefinitionId to set
	 */
	public void setReportContentDefinitionId(String reportContentDefinitionId) {
		this.reportContentDefinitionId = reportContentDefinitionId;
	}

	/**
	 * @return the reportDefForm
	 */
	public ReportContentDefinitionForm getReportContentDefForm() {
		return reportContentDefForm;
	}
	/**
	 * @param reportDefForm the reportDefForm to set
	 */
	public void setReportContentDefForm(ReportContentDefinitionForm reportContentDefForm) {
		this.reportContentDefForm = reportContentDefForm;
	}

	public String getRptCntDefId() {
		return rptCntDefId;
	}

	public void setRptCntDefId(String rptCntDefId) {
		this.rptCntDefId = rptCntDefId;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
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

}
