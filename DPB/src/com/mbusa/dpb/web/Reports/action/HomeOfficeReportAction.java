/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: HomeOfficeReportAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all request to HomeOfficeReport.
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 19, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.Reports.action;

import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.helper.IWebConstants;

public class HomeOfficeReportAction extends DPBAction{

	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = HomeOfficeReportAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(HomeOfficeReportAction.class);
	private String actionForward = "errorPage";
	
	
	public String viewHomeOfficePage(){
		final String methodName = "viewHomeOfficePage";
		LOGGER.enter(CLASSNAME, methodName);
		actionForward = "homeOffice";
		this.setMenuTabFocus(IWebConstants.REPORTS_ID);
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}	
}
