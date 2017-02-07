 /*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: GenerateReportAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to Generate Reports File Processing.
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
import com.mbusa.dpb.common.domain.GenerateReportFile;
import com.mbusa.dpb.common.domain.ProcessTypes;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.web.bonusProcess.action.ProcessLdrBonusAction;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.delegate.FileProcessingDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;
import com.opensymphony.xwork2.ActionSupport;

public class GenerateReportAction extends DPBAction{
	private static final long serialVersionUID = 1L;
	
	private GenerateReportFile genReportFile;
	private List<GenerateReportFile> genReportFileList=null;
	private String actionForward = "ErrorPage";
	private FileProcessingDelegate bDelegate  =  new FileProcessingDelegate();	
	private static DPBLog LOGGER = DPBLog.getInstance(GenerateReportAction.class);
	private int procId;
	private String prgType;
	private String actDate;
	public String genReports(){
			try {
				this.setMenuTabFocus(IWebConstants.FILE_PROCESSING_ID);
				actionForward="genReports";
				genReportFileList=new ArrayList<GenerateReportFile>();
				genReportFileList=bDelegate.genReportFile();
				Map<String,String> prcsTypeMap = MasterDataLookup.getInstance().getProcessTypeAsMap();
				for(GenerateReportFile genRptFile: genReportFileList)
				{
					genRptFile.setDisplayDate(WebHelper.formatDate(genRptFile.getProcDate()));
					//genRptFile.setActProcType(prcsTypeMap.get(genRptFile.getPrgType()));
					
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
			bDelegate.startProcFileDetails(procId,prgType,this.getUserId(),false,
					DateCalenderUtil.convertStringToDate(actDate));
			genReportFileList=new ArrayList<GenerateReportFile>();
			Thread.sleep(6000);
			LOGGER.info("StartProcessCall of Generate Report completed for process Id:" + procId);
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
	

	public GenerateReportFile getGenReportFile() {
		return genReportFile;
	}
	
	public void setGenReportFile(GenerateReportFile genReportFile) {
		this.genReportFile = genReportFile;
	}
	
	public List<GenerateReportFile> getGenReportFileList() {
		return genReportFileList;
	}
	
	public void setGenReportFileList(List<GenerateReportFile> genReportFileList) {
		this.genReportFileList = genReportFileList;
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
	
	/**
	 * @return the actDate
	 */
	public String getActDate() {
		return actDate;
	}
	/**
	 * @param actDate the actDate to set
	 */
	public void setActDate(String actDate) {
		this.actDate = actDate;
	}
}
