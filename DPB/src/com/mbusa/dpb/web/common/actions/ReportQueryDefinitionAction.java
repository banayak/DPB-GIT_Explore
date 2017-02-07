/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportQueryDefinitionAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to perform Report Query Def. Insert/update/Delete.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 01, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.common.actions;

import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.DefStatus;
import com.mbusa.dpb.common.domain.ReportQuery;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.form.ReportQueryForm;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class ReportQueryDefinitionAction  extends DPBAction{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static DPBLog LOGGER = DPBLog.getInstance(ReportQueryDefinitionAction.class);
	static final private String CLASSNAME = ReportQueryDefinitionAction.class.getName();
	private ReportQuery reportQuery;
	private ReportQueryForm reportQueryForm;
	private int rqIdHiiden;
	private String rId;
	String view;
	private String actionForward = "";
	boolean fromDPBList;
	BusinessDelegate bDelegate  =  new BusinessDelegate();
	

public String saveReportQueryDefinition(){
		
		
		try {	
				this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
				actionForward="reportQueryDef";
				validateReportDefinition();
				populateReportQuery();
				if(!hasActionErrors()){
				
			
					if(reportQuery.getReportQueryId() > 0)
					{
							if("I".equalsIgnoreCase(reportQuery.getStatus()))
							{
								String status=bDelegate.getReportDefStatus(reportQuery);
								if(status.equalsIgnoreCase("A"))
								{
									resetFormStatus();
									addActionError("Report Def. for Query is in Active status.");
								}	
								else
								{
									bDelegate.saveReportQueryDef(reportQuery);
									MasterDataLookup.getInstance().refreshCacheValue(IWebConstants.RPT_QRY_RERS);
									addActionMessage(IWebConstants.MSG_UPDATE);	
								}
						}
							
							else
							{
								bDelegate.saveReportQueryDef(reportQuery);
								MasterDataLookup.getInstance().refreshCacheValue(IWebConstants.RPT_QRY_RERS);
								addActionMessage(IWebConstants.MSG_UPDATE);	
							}
							
					}
					else
					{
						bDelegate.saveReportQueryDef(reportQuery);
						MasterDataLookup.getInstance().refreshCacheValue(IWebConstants.RPT_QRY_RERS);
						addActionMessage(IWebConstants.MSG_SAVE);
						
					}
					populateReportQueryForm();	
		}	
			
		} catch(BusinessException be){
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
		return actionForward;
		
	}



public void validateReportDefinition() throws BusinessException, TechnicalException{
	final String methodName = "validateReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	if("I".equalsIgnoreCase(reportQueryForm.getStatus()) && reportQueryForm.getReportQueryId() <= 0){
		addActionError("Please create definition in Draft or Active status.");
	}
}

public String getReportQueryDefinition(){
	
	
	try {
		this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
		if(rId == null  ){
			reportQueryForm=new ReportQueryForm();
			reportQueryForm.setStatus("D");
		}
		
		
		if((rId !=null && !rId.equals("")) && (view.equals("") || view == null))
		{
			fromDPBList = true;
			actionForward="reportQueryEdit";
			if(Integer.parseInt(rId) > 0)
			{
				reportQuery=bDelegate.getReportQueryEdit(rId);
				
				
			}
			else
			{
				reportQuery=bDelegate.getReportQueryEdit(rId);
				addActionMessage(IWebConstants.MSG_SAVE);
			}
			reportQueryForm=new ReportQueryForm();
			populateOnEdit(reportQuery,reportQueryForm);
			
			return actionForward;	
		}
		else if((view !=null && view.equalsIgnoreCase("view")) && (rId !=null && !rId.equals("")) )
		{
			actionForward="reportQueryReadOnly";
			if(Integer.parseInt(rId) > 0)
			{
				reportQuery=bDelegate.getReportQueryEdit(rId);
			}
			else
			{
				reportQuery=bDelegate.getReportQueryEdit(rId);
				addActionMessage(IWebConstants.MSG_SAVE);
			}
			reportQueryForm=new ReportQueryForm();
			populateOnEdit(reportQuery,reportQueryForm);
			
			return actionForward;
		}
		
		actionForward="reportQueryDefView";
		
	} catch(BusinessException be){
		LOGGER.info("BusinessException occured");
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
	return actionForward;
	
} 

private void populateOnEdit(ReportQuery reportQuery,ReportQueryForm reportQueryForm)
{
	List<DefStatus> defSts = MasterDataLookup.getInstance().getDefStatusCodes();
	reportQueryForm.setReportQueryId(reportQuery.getReportQueryId());
	reportQueryForm.setReportQueryName(reportQuery.getReportQueryName());
	reportQueryForm.setDescription(reportQuery.getDescription());
	reportQueryForm.setQuery(reportQuery.getQuery());
	reportQueryForm.setStatus(reportQuery.getStatus());
	/*if("A".equals(reportQuery.getStatus()) || "I".equals(reportQuery.getStatus()) )
	{
		reportQueryForm.setFlagActive(true);
	}
	
	if("D".equals(reportQuery.getStatus()))
	{
		reportQueryForm.setFlagDraft(true);
	}*/
	if("A".equals(reportQuery.getStatus())  )
	{
		reportQueryForm.setFlagActive(true);
		reportQueryForm.setFlagDraft(false);
		reportQueryForm.setFlagInActive(false);
	}
	if( "I".equals(reportQuery.getStatus()))
	{
		reportQueryForm.setFlagInActive(true);
		reportQueryForm.setFlagDraft(false);
		reportQueryForm.setFlagActive(false);
	}
	if("D".equals(reportQuery.getStatus()))
	{	
		reportQueryForm.setFlagDraft(true);
		reportQueryForm.setFlagActive(false);
		reportQueryForm.setFlagInActive(false);
	}

	if(reportQueryForm.getDescription()!=null)
	{
		reportQueryForm.setDesc(reportQueryForm.getDescription().split(IWebConstants.DESC_EXPSN));
	}
	if(reportQueryForm.getQuery() !=null)
	{
		reportQueryForm.setArrquery(reportQueryForm.getQuery().split(IWebConstants.DESC_EXPSN));
	}
	reportQueryForm.setShowStat(WebHelper.getStatusString(reportQuery.getStatus(),defSts));
}
private void populateReportQuery()
{
	
	reportQuery =new ReportQuery();
	reportQuery.setReportQueryId(reportQueryForm.getReportQueryId());
	reportQuery.setReportQueryName(reportQueryForm.getReportQueryName());
	reportQuery.setDescription(reportQueryForm.getDescription().trim());
	reportQuery.setQuery(reportQueryForm.getQuery().trim());
	reportQuery.setStatus(reportQueryForm.getStatus());
	reportQuery.setNoOfLines(reportQueryForm.getNoOfLines());
	reportQuery.setUser(this.getUserId());
}
private void populateReportQueryForm()
{	
	reportQueryForm.setReportQueryId(reportQuery.getReportQueryId());	
	if("A".equals(reportQuery.getStatus())  )
	{
		reportQueryForm.setFlagActive(true);
		reportQueryForm.setFlagDraft(false);
		reportQueryForm.setFlagInActive(false);
	}
	if( "I".equals(reportQuery.getStatus()))
	{
		reportQueryForm.setFlagInActive(true);
		reportQueryForm.setFlagDraft(false);
		reportQueryForm.setFlagActive(false);
	}
	if("D".equals(reportQuery.getStatus()))
	{	
		reportQueryForm.setFlagDraft(true);
		reportQueryForm.setFlagActive(false);
		reportQueryForm.setFlagInActive(false);
	}	
	

}

private void resetFormStatus()
{
	reportQueryForm.setStatus("A");
}



public ReportQueryForm getReportQueryForm() {
	return reportQueryForm;
}

public void setReportQueryForm(ReportQueryForm reportQueryForm) {
	this.reportQueryForm = reportQueryForm;
}



public int getRqIdHiiden() {
	return rqIdHiiden;
}

public void setRqIdHiiden() {
	this.rqIdHiiden = reportQueryForm.getReportQueryId();
}


public String getRId() {
	return rId;
}


public void setRId(String rId) {
	this.rId = rId;
}

public String getView() {
	return view;
}

public void setView(String view) {
	this.view = view;
}

public boolean isFromDPBList() {
	return fromDPBList;
}

public void setFromDPBList(boolean fromDPBList) {
	this.fromDPBList = fromDPBList;
}

}


