/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: SchedulerBean.java
 * Program Version			: 1.0
 * Program Description		: This class is used for process the Scheduler Tasks.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 01, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.business;
import java.rmi.RemoteException;

import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.ibm.websphere.scheduler.TaskStatus;
import com.mbusa.dpb.business.util.SchedulerHelper;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DPBCommonHelper;
//import com.mbusa.dpb.common.bo.ISchedulerManager;
//import com.mbusa.dpb.common.bo.SchedulerManagerImpl;

/**
 * @author OS5011279
 * Bean implementation class for Enterprise Bean: Scheduler
 */
@Stateless
public class SchedulerBean  {
	private static DPBLog LOGGER = DPBLog.getInstance(SchedulerBean.class);
	static final private String CLASSNAME = SchedulerBean.class.getName();
	//private IFileProcessingManager fileProcessMgr  = (FileProcessingManagerImpl) BOFactory.getInstance().getImplementation(IFileProcessingManager.class);
	//private ISchedulerManager schedulerMgr = (SchedulerManagerImpl)BOFactory.getInstance().getImplementation(ISchedulerManager.class);
	//private LocalServiceFactory local = new LocalServiceFactory();
	/**
	 * 
	 * @param taskStatus
	 * @throws RemoteException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process(TaskStatus taskStatus) throws RemoteException {
		final String methodName = "process";
		LOGGER.enter(CLASSNAME, methodName);
		String schedulerStatus = null;
		//execute processes only when scheduler status in properties file is on - Kshitija
		schedulerStatus = DPBCommonHelper.getSchedulerStatus();
		if(schedulerStatus!= null && schedulerStatus.trim().equalsIgnoreCase("on"))
		{
		SchedulerHelper schedulerHelper = new SchedulerHelper();
		schedulerHelper.doProcess();
		}
		LOGGER.exit(CLASSNAME, methodName);
	}
	public EJBHome getEJBHome() throws RemoteException {  
		// TODO Auto-generated method stub  
		return null;  
	}  

	public Handle getHandle() throws RemoteException {  
		// TODO Auto-generated method stub  
		return null;  
	} 

	public Object getPrimaryKey() throws RemoteException {  
		// TODO Auto-generated method stub  
		return null;  
	}  

	public boolean isIdentical(EJBObject obj) throws RemoteException {  
		// TODO Auto-generated method stub  
		return false;  
	}  

	public void remove() throws RemoteException, RemoveException {  
		// TODO Auto-generated method stub  
	}  

}


