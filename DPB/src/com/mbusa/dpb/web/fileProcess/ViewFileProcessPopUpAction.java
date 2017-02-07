package com.mbusa.dpb.web.fileProcess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.delegate.FileProcessingDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;
import com.mbusa.dpb.common.logger.DPBLog;

public class ViewFileProcessPopUpAction extends DPBAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int procDpbID; 
	private String programType;
	private FileProcessingDelegate bDelegate  =  new FileProcessingDelegate();
	List<DPBProcessLogBean> procesDetail  = new ArrayList<DPBProcessLogBean>();
	List<DPBProcessLogBean> procesActDetail  = new ArrayList<DPBProcessLogBean>();
	private static DPBLog LOGGER = DPBLog.getInstance(ViewFileProcessPopUpAction.class);
	private String actionForward = "";
	List<String> inpProcList;
	private int pdpb;
	private String prgType;

	public String viewFileProcpopup(){
		try
		{ 
			this.setMenuTabFocus(IWebConstants.FILE_PROCESSING_ID);
			actionForward="viewFileProcpopup";
			procesDetail = bDelegate.getProcessLogsDeatils(procDpbID);
				
			
				if(procesDetail.size() > 0)
				{
					int size=procesDetail.size()-1;
					size = "IN-PROCESS".equalsIgnoreCase(((DPBProcessLogBean)procesDetail.get(size)).getDpbProcessStatus())
							? procesDetail.size() : size;
				
						int count = 1;
						if("IN-PROCESS".equalsIgnoreCase(((DPBProcessLogBean)procesDetail.get(procesDetail.size()-1)).getDpbProcessStatus()))
						{
							for(DPBProcessLogBean procUpdetDet : procesDetail)
							{
								if(count != size && "IN-PROCESS".equalsIgnoreCase(procUpdetDet.getDpbProcessStatus())) {
								procUpdetDet.setDpbProcessStatus(IWebConstants.DISP_INP_PROC_STAT);
								//procesActDetail.add(procUpdetDet);
								} 
							
								count++;
							}
						}
						
						else if( ! "IN-PROCESS".equalsIgnoreCase(((DPBProcessLogBean)procesDetail.get(procesDetail.size()-1)).getDpbProcessStatus()))
						{
							for(DPBProcessLogBean procUpdetDet : procesDetail)
							{
								if( "IN-PROCESS".equalsIgnoreCase(procUpdetDet.getDpbProcessStatus())) {
								procUpdetDet.setDpbProcessStatus(IWebConstants.DISP_INP_PROC_STAT);
								//procesActDetail.add(procUpdetDet);
								} 
							
								//count++;
							}
						}
				}
			
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
	
	

	public int getProcDpbID() {
		return procDpbID;
	}

	public void setProcDpbID(int procDpbID) {
		this.procDpbID = procDpbID;
	}

	public List<DPBProcessLogBean> getProcesDetail() {
		return procesDetail;
	}

	public void setProcesDetail(List<DPBProcessLogBean> procesDetail) {
		this.procesDetail = procesDetail;
	}

	public List<String> getInpProcList() {
		return inpProcList;
	}

	public void setInpProcList(List<String> inpProcList) {
		this.inpProcList = inpProcList;
	}

	public int getPdpb() {
		return pdpb;
	}

	public void setPdpb(int pdpb) {
		this.pdpb = pdpb;
	}



	public String getPrgType() {
		return prgType;
	}


	public void setPrgType(String prgType) {
		this.prgType = prgType;
	}


	public String getProgramType() {
		return programType;
	}


	public void setProgramType(String programType) {
		this.programType = programType;
	}



	/**
	 * @return the procesActDetail
	 */
	public List<DPBProcessLogBean> getProcesActDetail() {
		return procesActDetail;
	}



	/**
	 * @param procesActDetail the procesActDetail to set
	 */
	public void setProcesActDetail(List<DPBProcessLogBean> procesActDetail) {
		this.procesActDetail = procesActDetail;
	}


}

