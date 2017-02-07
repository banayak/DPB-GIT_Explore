/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: InputFileListAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle request to display list of file definitions.
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
import com.mbusa.dpb.common.domain.FileDefinitionBean;
import com.mbusa.dpb.common.domain.Trigger;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

/**
 * @author RK5005820
 * 
 */
public class InputFileListAction extends DPBAction {
	static final private String CLASSNAME = InputFileListAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(InputFileListAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String actionForward = "errorPage";
	List<FileDefinitionBean> DefList = new ArrayList<FileDefinitionBean>();
	List<FileDefinitionBean> fileDefList = new ArrayList<FileDefinitionBean>();
	BusinessDelegate bDelegate = new BusinessDelegate();
	boolean role = false;
	// to get list of file definitions
	public String getFileListDetails() {
		final String methodName = "getFileListDetails";
		LOGGER.enter(CLASSNAME, methodName);
		try {
			if(IConstants.USER_ROLE_ADMIN.equalsIgnoreCase(this.getUserRole()) || 
					IConstants.USER_ROLE_TREASURY.equalsIgnoreCase(this.getUserRole())){
				role = true;
			}
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			actionForward = "inputFileListView";
			DefList = bDelegate.getFileDefinitionList();
			List<Trigger> defTgr = MasterDataLookup.getInstance().getTriggerCodes();
			List<DefStatus> defSts = MasterDataLookup.getInstance().getDefStatusCodes();
			if(DefList.size() <= 0){
				addActionMessage(IWebConstants.NO_FILE_DEF_DEFINED);
			}
			for(FileDefinitionBean fileDefBean: DefList){
				fileDefBean.setDefStatusCode(WebHelper.getStatusString(fileDefBean.getDefStatusCode(),defSts));
				fileDefBean.setTriggerCode(WebHelper.getTriggerString(fileDefBean.getTriggerCode(),defTgr));
				fileDefList.add(fileDefBean);
			}
		} catch(BusinessException be){
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
	public List<FileDefinitionBean> getFileDefList() {
		return fileDefList;
	}
	public void setFileDefList(List<FileDefinitionBean> fileDefList) {
		this.fileDefList = fileDefList;
	}
	public boolean isRole() {
		return role;
	}
	public void setRole(boolean role) {
		this.role = role;
	}
	
}
