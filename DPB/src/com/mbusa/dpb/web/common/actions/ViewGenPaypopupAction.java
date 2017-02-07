package com.mbusa.dpb.web.common.actions;

import java.util.ArrayList;
import java.util.List;

import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.web.delegate.ReportDelegate;
import com.opensymphony.xwork2.ActionSupport;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;

public class ViewGenPaypopupAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int processId; 
	private ReportDelegate reportDelegate = new ReportDelegate();
	List<DPBProcessLogBean> procesDetail  = new ArrayList<DPBProcessLogBean>();
	private String actionForward = "errorPage";
	
	public String viewGenPaypopup(){
		
		int procId=0;
		try
		{	actionForward="viewGenPaypopup";
			procId= 41;
			procesDetail = reportDelegate.getProcessLogsDeatils(procId);
		} catch(BusinessException be){
			
		}catch (TechnicalException te) {
			actionForward =  "errorPage";
		}
		return actionForward;	
	}
	
	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public List<DPBProcessLogBean> getProcesDetail() {
		return procesDetail;
	}

	public void setProcesDetail(List<DPBProcessLogBean> procesDetail) {
		this.procesDetail = procesDetail;
	}


}
