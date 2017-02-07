/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportsAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle request all request to generate 
 * 							  Retail payment Report.
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 19, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.common.actions;

import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.helper.IWebConstants;

public class ReportsAction extends DPBAction {
	
	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = ReportsAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(ReportsAction.class);
	private String actionForward = "errorPage";
	
	public String netStarReportGen(){
		final String methodName = "viewVehicleConditions";
		LOGGER.enter(CLASSNAME, methodName);
		//try {
			this.setMenuTabFocus(IWebConstants.REPORTS_ID);
			actionForward = "netStarReportGen";
			/*}catch(BusinessException be){
			LOGGER.info("BusinessException occured");
		}catch (TechnicalException te) {
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}*/
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
		
	}
}