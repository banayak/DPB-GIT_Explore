/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportContentDefList.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle request to display list of report content definitions.
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
import com.mbusa.dpb.common.domain.ReportContentDefinitionBean;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class ReportContentDefList extends DPBAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = ReportContentDefList.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(ReportContentDefList.class);
	
	private String actionForward = "";
	private List<ReportContentDefinitionBean> defList = new ArrayList<ReportContentDefinitionBean>();
	private List<ReportContentDefinitionBean> rptCntList = new ArrayList<ReportContentDefinitionBean>();
	BusinessDelegate bDelegate = new BusinessDelegate();
	boolean role = false;
	
	public String getReportContentList(){
		final String methodName = "getReportContentList";
		LOGGER.enter(CLASSNAME, methodName);
		try{
			if(IConstants.USER_ROLE_ADMIN.equalsIgnoreCase(this.getUserRole())){
				role = true;
			}
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			actionForward = "reportContentList";
			defList = bDelegate.getReportContentList();
			List<DefStatus> defSts = MasterDataLookup.getInstance().getDefStatusCodes();
			if(defList.size() <= 0){
				addActionMessage(IWebConstants.NO_RPT_CTNT_DEFINED);
			}
			for(ReportContentDefinitionBean contentBean: defList){
				contentBean.setDefStatusCode(WebHelper.getStatusString(contentBean.getDefStatusCode(),defSts));
				rptCntList.add(contentBean);
			}
		}catch(BusinessException be){
			LOGGER.error("BusinessException occured");
			addActionError("Business Exception occured while retrieving record");
		}catch (TechnicalException te) {
			LOGGER.error("BusinessException occured");
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (PersistenceException pe) {
			LOGGER.error("BusinessException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			LOGGER.error("BusinessException occured");
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}
	public List<ReportContentDefinitionBean> getRptCntList() {
		return rptCntList;
	}
	public void setRptCntList(List<ReportContentDefinitionBean> rptCntList) {
		this.rptCntList = rptCntList;
	}
	public boolean isRole() {
		return role;
	}
	public void setRole(boolean role) {
		this.role = role;
	}
		
}
