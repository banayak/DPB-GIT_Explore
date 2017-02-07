/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: GeneratePaymentFileAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to Generate Payment File Processing.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 08, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.fileProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.GeneratePaymentFile;

import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.common.actions.DPBAction;

import com.mbusa.dpb.web.delegate.FileProcessingDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;


public class GeneratePaymentFileAction extends DPBAction{

	private static final long serialVersionUID = 1L;
	
	private GeneratePaymentFile genPaymentFile;
	private List<GeneratePaymentFile> genPaymentFileList=null;	
	private String actionForward = "ErrorPage";
	private FileProcessingDelegate bDelegate  =  new FileProcessingDelegate();	
	private static DPBLog LOGGER = DPBLog.getInstance(GeneratePaymentFileAction.class);
	private int procId;
	private String prgType;
	public String genPaymentFiles(){
		
		try {
			this.setMenuTabFocus(IWebConstants.FILE_PROCESSING_ID);
			actionForward="genPayFile";
			genPaymentFileList=new ArrayList<GeneratePaymentFile>();
			genPaymentFileList=bDelegate.genPayFile();
			Map<String,String> prcsTypeMap = MasterDataLookup.getInstance().getProcessTypeAsMap();
			for(GeneratePaymentFile genPaymentFile: genPaymentFileList)
			{
				genPaymentFile.setDisplayDate(WebHelper.formatDate(genPaymentFile.getProcDate()));
				//genPaymentFile.setActProcType(prcsTypeMap.get(genPaymentFile.getPrgType()));
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

	public String startProcessCall()
	{
		try {
			this.setMenuTabFocus(IWebConstants.FILE_PROCESSING_ID);
			actionForward="startProcessInpFile";
			bDelegate.startProcFileDetails(procId,prgType,this.getUserId(),false,null);
			Thread.sleep(6000);
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


	public GeneratePaymentFile getGenPaymentFile() {
		return genPaymentFile;
	}

	public void setGenPaymentFile(GeneratePaymentFile genPaymentFile) {
		this.genPaymentFile = genPaymentFile;
	}

	public List<GeneratePaymentFile> getGenPaymentFileList() {
		return genPaymentFileList;
	}

	public void setGenPaymentFileList(List<GeneratePaymentFile> genPaymentFileList) {
		this.genPaymentFileList = genPaymentFileList;
	}

	/**
	 * @return the procId
	 */
	public int getProcId() {
		return procId;
	}

	/**
	 * @param procId the procId to set
	 */
	public void setProcId(int procId) {
		this.procId = procId;
	}

	/**
	 * @return the prgType
	 */
	public String getPrgType() {
		return prgType;
	}

	/**
	 * @param prgType the prgType to set
	 */
	public void setPrgType(String prgType) {
		this.prgType = prgType;
	}
	
}

