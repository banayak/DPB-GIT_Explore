/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ProcessLdrBonusAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used For Process Leadership Bonus Process.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 10, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.bonusProcess.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.FileProcessDefBean;
import com.mbusa.dpb.common.domain.ProcessLdrBonus;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.delegate.BonusProcessingDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class ProcessLdrBonusAction extends DPBAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProcessLdrBonus processLdrBonus;
	private List<ProcessLdrBonus> processLdrBonusList=null;
	private String actionForward = "ErrorPage";
	private static DPBLog LOGGER = DPBLog.getInstance(ProcessLdrBonusAction.class);
	BonusProcessingDelegate bDelegate=new BonusProcessingDelegate();
	private int procId;
	private String prgType;
	private String processType;
	private boolean userActionRole;
	private boolean resetAllowed;
	
public String processLeadershipBonus(){
		
		try {
			this.setMenuTabFocus(IWebConstants.BONUS_PROCESSING_ID);
			actionForward = "processLdrBonus";
			processLdrBonusList=new ArrayList<ProcessLdrBonus>();
			String isResetAllowed = PropertyManager.getPropertyManager().getPropertyAsString("reset.development");
			if(IConstants.USER_ROLE_ADMIN.equalsIgnoreCase(this.getUserRole())){
				setUserActionRole(true);
			}
			if(IConstants.USER_ROLE_ADMIN.equalsIgnoreCase(this.getUserRole()) && IConstants.TRUE.equalsIgnoreCase(isResetAllowed)){
				setResetAllowed(true);
			}
			processLdrBonusList=bDelegate.processLdrBonus();
			Map<String,String> prcsTypeMap = MasterDataLookup.getInstance().getProcessTypeAsMap();
			for(ProcessLdrBonus procLdrBonus: processLdrBonusList)
			{
				procLdrBonus.setDisplayDate(WebHelper.formatDate(procLdrBonus.getProcDate()));
				procLdrBonus.setActProcType(prcsTypeMap.get(procLdrBonus.getPrgType()));
			}
		}catch(BusinessException be){
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
		this.setMenuTabFocus(IWebConstants.BONUS_PROCESSING_ID);
		actionForward="startProcessBnsProc";
		String userRole = this.getUserId();
		if(IConstants.PROC_STATUS_RESET.equalsIgnoreCase(processType)){
			
			boolean isLdrShpBonus = false;
			boolean bonusProcess = false;
			
			FileProcessDefBean fileProcessDefBean = new FileProcessDefBean();
			fileProcessDefBean.setUser(this.getUserId());
			
			String procDpbID=request.getParameter("procDpbID");
			String prgType=request.getParameter("prgType");
			
			fileProcessDefBean.setDefinitionId(procId);
			fileProcessDefBean.setDefinitionType(prgType);
			fileProcessDefBean.setProcessType(processType);
			if(IConstants.LEADERSHIP_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType())){
				isLdrShpBonus = true;
			}			
			userRole = fileProcessDefBean.getUser();
			bDelegate.performBonusReprocess(fileProcessDefBean.getDefinitionId(), userRole, fileProcessDefBean.getProcessType(), isLdrShpBonus);
		}
		bDelegate.startProcBonusDetails(procId,prgType,userRole,false,null);
		Thread.sleep(6000);
		LOGGER.info("StartProcessCall of Leadership Bonus completed for process Id:" + procId);
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



public ProcessLdrBonus getProcessLdrBonus() {
	return processLdrBonus;
}

public void setProcessLdrBonus(ProcessLdrBonus processLdrBonus) {
	this.processLdrBonus = processLdrBonus;
}

public List<ProcessLdrBonus> getProcessLdrBonusList() {
	return processLdrBonusList;
}

public void setProcessLdrBonusList(List<ProcessLdrBonus> processLdrBonusList) {
	this.processLdrBonusList = processLdrBonusList;
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
 * @return the processType
 */
public String getProcessType() {
	return processType;
}

/**
 * @param processType the processType to set
 */
public void setProcessType(String processType) {
	this.processType = processType;
}
/**
 * @return the userActionRole
 */
public boolean isUserActionRole() {
	return userActionRole;
}

/**
 * @param userActionRole the userActionRole to set
 */
public void setUserActionRole(boolean userActionRole) {
	this.userActionRole = userActionRole;
}

/**
 * @return the resetAllowed
 */
public boolean isResetAllowed() {
	return resetAllowed;
}

/**
 * @param resetAllowed the resetAllowed to set
 */
public void setResetAllowed(boolean resetAllowed) {
	this.resetAllowed = resetAllowed;
}
}
