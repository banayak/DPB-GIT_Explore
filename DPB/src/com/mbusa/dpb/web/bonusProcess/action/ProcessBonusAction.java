/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: processBonus.java
 * Program Version			: 1.0
 * Program Description		: This class is used For Process Bonus Process.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 09, 2013     	  1.0        First Draft
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
import com.mbusa.dpb.common.domain.ProcessBonus;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.delegate.BonusProcessingDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;
public class ProcessBonusAction extends DPBAction {
	
	
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ProcessBonus processBonus;
	private List<ProcessBonus> processBonusList=null;
	private List<ProcessBonus> procBonActList=null;
	private String actionForward = "ErrorPage";
	BonusProcessingDelegate bDelegate=new BonusProcessingDelegate();
	private String pdpb;
	private String programType;
	static final private String CLASSNAME = ProcessBonusAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(ProcessBonusAction.class);
	private int procId;
	private String prgType;
	private String processType;
	private boolean userActionRole;
	private boolean resetAllowed;
	
	public String processBonus(){
		long btime=System.currentTimeMillis();
		LOGGER.info("Time in processBonus action:"+btime);
		
		try {
			this.setMenuTabFocus(IWebConstants.BONUS_PROCESSING_ID);
			
			actionForward="processBonus";
			String isResetAllowed = PropertyManager.getPropertyManager().getPropertyAsString("reset.development");
			if(IConstants.USER_ROLE_ADMIN.equalsIgnoreCase(this.getUserRole())){
				setUserActionRole(true);
			}
			if(IConstants.USER_ROLE_ADMIN.equalsIgnoreCase(this.getUserRole()) && IConstants.TRUE.equalsIgnoreCase(isResetAllowed)){
				setResetAllowed(true);
			}
			processBonusList=new ArrayList<ProcessBonus>();
			processBonusList=bDelegate.procBonusProc();
			Map<String,String> prcsTypeMap = MasterDataLookup.getInstance().getProcessTypeAsMap();
			for(ProcessBonus procBonus: processBonusList)
			{
				procBonus.setDisplayDate(WebHelper.formatDate(procBonus.getProcDate()));
				procBonus.setActProcType(prcsTypeMap.get(procBonus.getPrgType()));
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
		long etime=System.currentTimeMillis();
		LOGGER.info("Time in processBonus action:"+etime+":Time Taken for execution::"+(etime-btime));
		return actionForward;
		
	}
public String startProcessCall()
{
	try {
		this.setMenuTabFocus(IWebConstants.BONUS_PROCESSING_ID);
		String userId = this.getUserId();
		actionForward="startProcessBnsProc";
		if(IConstants.PROC_STATUS_RESET.equalsIgnoreCase(processType)){
			
			boolean isLdrShpBonus = false;
			boolean bonusProcess = false;
			
			FileProcessDefBean fileProcessDefBean = new FileProcessDefBean();
			fileProcessDefBean.setUser(WebHelper.makeNonNullString(this.getUserId()));
			
			String procDpbID=request.getParameter("procDpbID");
			String prgType=request.getParameter("prgType");
			
			fileProcessDefBean.setDefinitionId(procId);
			fileProcessDefBean.setDefinitionType(prgType);
			fileProcessDefBean.setProcessType(processType);
			if(IConstants.FIXED_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType()) || IConstants.VARIABLE_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType()) || IConstants.COMMISSION_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType()) || IConstants.LEADERSHIP_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType())){
				bonusProcess = true;
			}
			if(IConstants.LEADERSHIP_BONUS_PROCESS.equalsIgnoreCase(fileProcessDefBean.getDefinitionType())){
				isLdrShpBonus = true;
			}			
			bDelegate.performBonusReprocess(fileProcessDefBean.getDefinitionId(), fileProcessDefBean.getUser(), fileProcessDefBean.getProcessType(), isLdrShpBonus);
			userId = fileProcessDefBean.getUser();
		}
		bDelegate.startProcBonusDetails(procId,prgType,userId,false,null);
		Thread.sleep(6000);
		LOGGER.info("startProcessCall of Bonus Processing completed for process Id:" + procId);
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



public ProcessBonus getProcessBonus() {
	return processBonus;
}


public void setProcessBonus(ProcessBonus processBonus) {
	this.processBonus = processBonus;
}


public List<ProcessBonus> getProcessBonusList() {
	return processBonusList;
}


public void setProcessBonusList(List<ProcessBonus> processBonusList) {
	this.processBonusList = processBonusList;
}


public String getPdpb() {
	return pdpb;
}


public void setPdpb(String pdpb) {
	this.pdpb = pdpb;
}


public String getProgramType() {
	return programType;
}


public void setProgramType(String programType) {
	this.programType = programType;
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

/**
 * @return the procId
 */

}