package com.mbusa.dpb.web.common.actions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.DefStatus;
import com.mbusa.dpb.common.domain.ReportQuery;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class ReportQueryListAction extends DPBAction {
	
	private static final long serialVersionUID = 1L;
	private static DPBLog LOGGER = DPBLog.getInstance(ReportQueryListAction.class);
	private ReportQuery reportQuery;
	private List<ReportQuery> reportQueryList;
	private List<ReportQuery> rptQryList;
	private String actionForward = "";
	BusinessDelegate bDelegate  =  new BusinessDelegate();
	
public String showReportQueryList(){
		
		try {
				actionForward="reportQueryList";
				this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
				reportQueryList=new ArrayList<ReportQuery>();	
				rptQryList=new  ArrayList<ReportQuery>();
				reportQueryList=bDelegate.getReportQueryList();
				List<DefStatus> defSts = MasterDataLookup.getInstance().getDefStatusCodes();
				for(ReportQuery rptQry: reportQueryList){
					rptQry.setStatus(WebHelper.getStatusString(rptQry.getStatus(),defSts));
					rptQryList.add(rptQry);
				}
			
		} catch(BusinessException be){
			LOGGER.info("BusinessException occured");
		}catch (TechnicalException te) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, te);
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

public ReportQuery getReportQuery() {
	return reportQuery;
}

public void setReportQuery(ReportQuery reportQuery) {
	this.reportQuery = reportQuery;
}

public List<ReportQuery> getReportQueryList() {
	return reportQueryList;
}

public void setReportQueryList(List<ReportQuery> reportQueryList) {
	this.reportQueryList = reportQueryList;
}

public List<ReportQuery> getRptQryList() {
	return rptQryList;
}

public void setRptQryList(List<ReportQuery> rptQryList) {
	this.rptQryList = rptQryList;
}


}
