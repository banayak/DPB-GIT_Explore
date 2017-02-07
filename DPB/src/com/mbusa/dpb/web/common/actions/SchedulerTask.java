/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: SchedulerTask.java
 * Program Version			: 1.0
 * Program Description		: This class is used to Creating MainTask for Scheduler when server starts.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 01, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.common.actions;

import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

import com.ibm.websphere.scheduler.BeanTaskInfo;
import com.ibm.websphere.scheduler.Scheduler;
import com.ibm.websphere.scheduler.TaskHandlerHome;
import com.ibm.websphere.scheduler.TaskInfo;
import com.ibm.websphere.scheduler.TaskStatus;
import com.mbusa.dpb.common.exceptions.DPBExceptionHandler;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;

/**
 * 
 * @author OS5011279
 *
 */
public class SchedulerTask extends HttpServlet  {

	private static DPBLog LOGGER = DPBLog.getInstance(SchedulerTask.class);
	static final private String CLASSNAME = SchedulerTask.class.getName();
	private static final long serialVersionUID = 1L;
	private static final PropertyManager PROP_MNGR = PropertyManager.getPropertyManager();
	
	public void init(ServletConfig config) throws ServletException{
		final String methodName = "init";
		LOGGER.enter(CLASSNAME, methodName);
		BeanTaskInfo taskInfo = null; 
		TaskStatus taskStatus = null;
		String taskId = "";
		try {   
			
			Scheduler scheduler = (Scheduler) new InitialContext().lookup(PROP_MNGR.getPropertyAsString("dpb.scheduler.jndi"));  
			taskInfo = (BeanTaskInfo) scheduler.createTaskInfo(BeanTaskInfo.class);  
			Object o = new InitialContext().lookup("ejb/DPBEAR/DPBBusiness.jar/SchedulerBean#com.ibm.websphere.scheduler.TaskHandlerHome"); 
			TaskHandlerHome home = (TaskHandlerHome) javax.rmi.PortableRemoteObject.narrow(o, TaskHandlerHome.class);
			
			taskInfo.setTaskHandler(home);  
			
			taskInfo.setName("MainTask");
			taskInfo.setStartTimeInterval(PROP_MNGR.getPropertyAsString("dpb.scheduler.start.time")); 
			taskInfo.setRepeatInterval(PROP_MNGR.getPropertyAsString("dpb.schduler.repeat.interval"));
			taskInfo.setQOS(TaskInfo.QOS_ONLYONCE);
			taskInfo.setNumberOfRepeats(-1);
			taskInfo.setUserCalendar(null,"CRON");
			
			taskStatus = scheduler.create(taskInfo);
			taskId = taskStatus.getTaskId();
			
			System.out.println("Task id .."+ taskId);
			
			LOGGER.info("start the processing....");
			
			LOGGER.info("Created task id...." + taskId);

			int rows = removeOldTasks();
			LOGGER.info("No of removed tasks " + rows);
			 
		} catch (Exception ex) { 
			LOGGER.error("Exception occured while initialize the scheduler",ex);
			DPBExceptionHandler.getInstance().handleException(ex);
		}
		LOGGER.exit(CLASSNAME, methodName);
	}

	private int removeOldTasks(){
		PropertyManager PROP_MNGR = PropertyManager.getPropertyManager();
		String datoSourceName = PROP_MNGR.getPropertyAsString("app.database.jndi");
		InitialContext ctx = null;
		DataSource src = null;
		Connection con = null;
		Statement st = null;
		int rowDeleted=0;
		try {
			ctx = new InitialContext();
			src = (DataSource) ctx.lookup(datoSourceName);
			con =  src.getConnection();
			st=con.createStatement();
			rowDeleted = st.executeUpdate("delete from DPB.SCHEDULERTASK where TASKID <  (select (max(TASKID)-1) from DPB.SCHEDULERTASK)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(con !=null){
				try{con.close();}catch(Exception e) {}
			}
			if (st != null) {
				try{st.close();}catch(Exception e) {}
			}
		}
		return rowDeleted;
	}
}