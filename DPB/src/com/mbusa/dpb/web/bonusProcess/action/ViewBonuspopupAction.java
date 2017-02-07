package com.mbusa.dpb.web.bonusProcess.action;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.delegate.BonusProcessingDelegate;
import com.mbusa.dpb.web.delegate.FileProcessingDelegate;
import com.mbusa.dpb.web.delegate.ReportDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.opensymphony.xwork2.ActionSupport;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;

public class ViewBonuspopupAction extends DPBAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int processId; 
	private BonusProcessingDelegate bDelegate  =  new BonusProcessingDelegate();
	List<DPBProcessLogBean> procesDetail  = new ArrayList<DPBProcessLogBean>();
	private static DPBLog LOGGER = DPBLog.getInstance(ViewBonuspopupAction.class);
	private String actionForward = "";
	
	
	public String viewProcBonusProcpopup(){
		try
		{
			this.setMenuTabFocus(IWebConstants.FILE_PROCESSING_ID);
			actionForward="viewProcBonusProcpopup";
			procesDetail = bDelegate.getProcessLogsDeatils(processId);
		} catch(BusinessException be){
			LOGGER.info("BusinessException occured");
		}catch (TechnicalException te) {
			actionForward =  "ErrorPage";
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
	
	
	public String viewLdrBonuspopup(){
		try
		{
			this.setMenuTabFocus(IWebConstants.FILE_PROCESSING_ID);
			actionForward="viewLdrBonuspopup";
			procesDetail = bDelegate.getProcessLogsDeatils(processId);
		} catch(BusinessException be){
			LOGGER.info("BusinessException occured");
		}catch (TechnicalException te) {
			actionForward =  "ErrorPage";
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
