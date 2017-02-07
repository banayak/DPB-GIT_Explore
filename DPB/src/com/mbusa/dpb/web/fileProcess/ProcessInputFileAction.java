package com.mbusa.dpb.web.fileProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.FileDefinitionBean;
import com.mbusa.dpb.common.domain.FileProcessDefBean;
import com.mbusa.dpb.common.domain.ProcessInputFile;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.delegate.FileProcessingDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class ProcessInputFileAction extends DPBAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private ProcessInputFile procInpFile= null;
	private int pdpb;
	private List<ProcessInputFile> inpProcList=null;
	private String actionForward = "ErrorPage";
	private FileProcessingDelegate bDelegate  =  new FileProcessingDelegate();	
	private static DPBLog LOGGER = DPBLog.getInstance(ProcessInputFileAction.class);
	private String prgType;
	private int procId;
	private String actDate;
    private String processType;
    private boolean userActionRole;
    private boolean resetAllowed;
    

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

public String processInpFile(){
	long btime=System.currentTimeMillis();
	LOGGER.info("Time in processInpFile action:"+btime);
		try {
				this.setMenuTabFocus(IWebConstants.FILE_PROCESSING_ID);
				
				actionForward="processInpFile";
				inpProcList=new ArrayList<ProcessInputFile>();
				String isResetAllowed = PropertyManager.getPropertyManager().getPropertyAsString("reset.development");
				if(IConstants.USER_ROLE_ADMIN.equalsIgnoreCase(this.getUserRole())){
					setUserActionRole(true);
				}
				if(IConstants.USER_ROLE_ADMIN.equalsIgnoreCase(this.getUserRole()) && IConstants.TRUE.equalsIgnoreCase(isResetAllowed)){
					setResetAllowed(true);
				}
				inpProcList=bDelegate.getProcInputFileDetails();
				Map<String,String> prcsTypeMap = MasterDataLookup.getInstance().getProcessTypeAsMap();
				for(ProcessInputFile procInp: inpProcList)
				{
					procInp.setDisplayDate(WebHelper.formatDate(procInp.getProcDate()));
					procInp.setActProcType(prcsTypeMap.get(procInp.getPrgType()));
					
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
		long etime=System.currentTimeMillis();
		LOGGER.info("Time in processInpFile action:"+etime+":Time Taken for execution::"+(etime-etime));
		return actionForward;
		
	}

public String startProcessCall()
{	
	long btime=System.currentTimeMillis();
	LOGGER.info("Time in startProcessCall action:"+btime);
	try {
		this.setMenuTabFocus(IWebConstants.FILE_PROCESSING_ID);
		actionForward="startProcessInpFile";
		boolean isReset = false;
		String userRole = this.getUserId();
		if(IConstants.PROCESS_RESET.equalsIgnoreCase(processType)){
			FileDefinitionBean definitionBean = null;
		
			FileProcessDefBean fileProcessDefBean = new FileProcessDefBean();
			fileProcessDefBean.setDefinitionId(procId);
			fileProcessDefBean.setProcessType(processType);
			fileProcessDefBean.setDefinitionType(IConstants.FILE_PROCESS_NAME);
			fileProcessDefBean.setUser(this.getUserId());
			boolean isLdrShpBonus = false;
			definitionBean = bDelegate.fetchFileDefinition(procId);
			isReset = WebHelper.reProcess(definitionBean, procId, fileProcessDefBean, isLdrShpBonus);
			userRole = fileProcessDefBean.getUser();
		}
				
		bDelegate.startProcFileDetails(procId,prgType,userRole,false,DateCalenderUtil.convertStringToDate(actDate));
		inpProcList=new ArrayList<ProcessInputFile>();
		Thread.sleep(15000);
		inpProcList=bDelegate.getProcInputFileDetails();
        LOGGER.info("StartProcessCall completed for process Id:" + procId);
        long etime=System.currentTimeMillis();
		LOGGER.info("Time in processInpFile action:"+etime+":Time Taken for execution::"+(etime-etime));
	} catch(BusinessException be){
		LOGGER.info("BusinessException occured");
	}catch (TechnicalException te) {
		actionForward =   IWebConstants.DPB_ERROR_URL;
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
	LOGGER.info("Time in processInpFile action:"+etime+":Time Taken for execution::"+(etime-etime));
	return actionForward;
	
}


public List<ProcessInputFile> getInpProcList() {
	return inpProcList;
}

public void setInpProcList(List<ProcessInputFile> inpProcList) {
	this.inpProcList = inpProcList;
}
public ProcessInputFile getProcInpFile() {
	return procInpFile;
}
public void setProcInpFile(ProcessInputFile procInpFile) {
	this.procInpFile = procInpFile;
}
public String getPrgType() {
	return prgType;
}
public void setPrgType(String prgType) {
	this.prgType = prgType;
}


public int getPdpb() {
	return pdpb;
}


public void setPdpb(int pdpb) {
	this.pdpb = pdpb;
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

/**
 * @return the pId
 */

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

public boolean isResetAllowed() {
	return resetAllowed;
}

public void setResetAllowed(boolean resetAllowed) {
	this.resetAllowed = resetAllowed;
}
}
