/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: FileFormatListAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle request to display list of file format definitions.
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

import com.ibm.ws.gridcontainer.exceptions.PersistenceException;
import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.DefStatus;
import com.mbusa.dpb.common.domain.FileFormatBean;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class FileFormatListAction extends DPBAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = FileFormatListAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(FileFormatListAction.class);
	
	private String actionForward = "ErrorPage";
	List<FileFormatBean> formatList = new ArrayList<FileFormatBean>();
	List<FileFormatBean> fileFormatList = new ArrayList<FileFormatBean>();
	BusinessDelegate bDelegate  = new BusinessDelegate();
	boolean role = false;
	
	public String getFileFormatListDetails(){
		final String methodName = "getFileFormatListDetails";
		LOGGER.enter(CLASSNAME, methodName);
		try{
			if(IConstants.USER_ROLE_ADMIN.equalsIgnoreCase(this.getUserRole()) || 
					IConstants.USER_ROLE_TREASURY.equalsIgnoreCase(this.getUserRole())){
				role = true;
			}
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			actionForward = "fileFormatListView";
			formatList = bDelegate.getFileFormatListDetails();
			List<DefStatus> defSts = MasterDataLookup.getInstance().getDefStatusCodes();
			if(formatList.size() <= 0){
				addActionMessage(IWebConstants.NO_FORMATS_DEFINED);
			}
			for(FileFormatBean formatBean: formatList){
				formatBean.setDefStatusCode(WebHelper.getStatusString(formatBean.getDefStatusCode(),defSts));
				formatBean.setInbountFileIndicator(WebHelper.getYesOrNoString(formatBean.getInbountFileIndicator()));
				formatBean.setIndHeader(WebHelper.getYesOrNoString(formatBean.getIndHeader()));
				fileFormatList.add(formatBean);
			}
		}catch(BusinessException be){
			LOGGER.error("BusinessException occured");
			addActionError("Business Exception occured while retrieving record");
		}catch (TechnicalException te) {
			LOGGER.error("TechnicalException occured");
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (PersistenceException pe) {
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			LOGGER.error("Exception occured");
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}
	public List<FileFormatBean> getFileFormatList() {
		return fileFormatList;
	}
	public void setFileFormatList(List<FileFormatBean> fileFormatList) {
		this.fileFormatList = fileFormatList;
	}
	public boolean isRole() {
		return role;
	}
	public void setRole(boolean role) {
		this.role = role;
	}
}
