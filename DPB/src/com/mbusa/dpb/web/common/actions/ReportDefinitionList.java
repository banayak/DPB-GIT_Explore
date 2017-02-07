/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportDefinitionList.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle request to display list of report definitions.
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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.DefStatus;
import com.mbusa.dpb.common.domain.ReportDefinitionBean;
import com.mbusa.dpb.common.domain.Scheduler;
import com.mbusa.dpb.common.domain.Trigger;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class ReportDefinitionList extends DPBAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = ReportDefinitionList.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(ReportDefinitionList.class);

	private String actionForward = "errorPage";
	private List<ReportDefinitionBean> DefList = new ArrayList<ReportDefinitionBean>();
	private List<ReportDefinitionBean> reportDefList = new ArrayList<ReportDefinitionBean>();
	
	BusinessDelegate bDelegate = new BusinessDelegate();
	boolean role = false;
	
	public String getReportDefintionList(){
		final String methodName = "getReportDefintionList";
		LOGGER.enter(CLASSNAME, methodName);
		try{
			if(IConstants.USER_ROLE_ADMIN.equalsIgnoreCase(this.getUserRole())){
				role = true;
			}
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			actionForward = "reportDefListView";
			DefList = bDelegate.getReportDefinitionList();
			List<Scheduler> defSchd = MasterDataLookup.getInstance().getSchedulerCodes();
			List<Trigger> defTgr = MasterDataLookup.getInstance().getTriggerCodes();
			List<DefStatus> defSts = MasterDataLookup.getInstance().getDefStatusCodes();
			if(DefList.size() <= 0){
				addActionMessage(IWebConstants.NO_REPORT_DEF_DEFINED);
			}
			for(ReportDefinitionBean reportDefBean: DefList){
				reportDefBean.setScheduleCode(WebHelper.getScheduleString(reportDefBean.getScheduleCode(),defSchd));
				reportDefBean.setDefStatusCode(WebHelper.getStatusString(reportDefBean.getDefStatusCode(),defSts));
				reportDefBean.setTriggerCode(WebHelper.getTriggerString(reportDefBean.getTriggerCode(),defTgr));
				reportDefList.add(reportDefBean);
			}
		}catch(BusinessException be){
			LOGGER.info("BusinessException occured");
			addActionError("Business Exception occured while retrieving record");
		}catch (TechnicalException te) {
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (PersistenceException pe) {
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			LOGGER.info("BusinessException occured");
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}

	public List<ReportDefinitionBean> getReportDefList() {
		return reportDefList;
	}

	public void setReportDefList(List<ReportDefinitionBean> reportDefList) {
		this.reportDefList = reportDefList;
	}

	public boolean isRole() {
		return role;
	}

	public void setRole(boolean role) {
		this.role = role;
	}
	
}
