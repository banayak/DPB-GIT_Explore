/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: RetailDateChangeAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used for retail end date change in the day federated 
 * 							  table, depending on the end date change the relevant processes also gets postponed. 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Feb 19, 2014     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.common.actions;

import com.mbusa.dpb.common.domain.BulkSchedulerInfo;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DPBCommonHelper;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.web.delegate.FileProcessingDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
/**
 * 
 * @author AH33812
 *
 */
public class ScheduleTimeAction extends DPBAction {

	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = ScheduleTimeAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(ReportsAction.class);
	String actionForward = "";
	String startDate;
	String processingDate;
	int numberDays;
	String schedulerStatus;
	String cleanUpMessage;
	

	public String getCleanUpMessage() {
		return cleanUpMessage;
	}

	public void setCleanUpMessage(String cleanUpMessage) {
		this.cleanUpMessage = cleanUpMessage;
	}
	

	private FileProcessingDelegate businessDel = new FileProcessingDelegate();
	
	public String previewPage(){
		final String methodName = "previewPage";
		LOGGER.enter(CLASSNAME, methodName);
		this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
		String actionForward = "schTimeView";
		//read scheduler status from properties file - Kshitija
		schedulerStatus = DPBCommonHelper.getSchedulerStatus();
		return actionForward;
	}
	
	public String submitDate(){
		final String methodName = "previewPage";
		LOGGER.enter(CLASSNAME, methodName);
		this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
		String actionForward = "schTimeView";
		//read scheduler status from properties file - Kshitija
		schedulerStatus = DPBCommonHelper.getSchedulerStatus();
		if((startDate != null || !startDate.isEmpty()) && numberDays > 0 ){
			java.sql.Date startDt = DateCalenderUtil.convertStringToDate(startDate);
			BulkSchedulerInfo bulkInfo = new BulkSchedulerInfo();
			try {
				bulkInfo.setScheduleStartDate(startDt);
				bulkInfo.setNoOfDays(numberDays);
				bulkInfo.setTodaysDte(DateCalenderUtil.getCurrentDateTime());
				businessDel.startSchedulerManualCall(bulkInfo);
			} catch (BusinessException e) {
				e.printStackTrace();
				actionForward =  IWebConstants.DPB_ERROR_URL;
			} catch (TechnicalException e) {
				e.printStackTrace();
				actionForward =  IWebConstants.DPB_ERROR_URL;
			}
		} else {
			addActionError("Invalid Data");
		}
		return actionForward;
	}
	
	//Data Cleanup for process rerun start
	/**
	 * This method takes date as input and validates it to be empty or null
	 * If validation returns true then it proceeds to deletion of data
	 * @return actionForward
	 */
	public String cleanUpData() {
		final String methodName = "cleanUpData";
		LOGGER.enter(CLASSNAME, methodName);
		this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
		String actionForward = "schTimeView";
		//read scheduler status from properties file
		schedulerStatus = DPBCommonHelper.getSchedulerStatus();
		DateCalenderUtil calenderUtil = new DateCalenderUtil();
		try {
			if(processingDate != null && !processingDate.isEmpty()) {
				
				if(calenderUtil.validateDate(processingDate)) {
					if(!(calenderUtil.validateFutureDate(processingDate))) {
						try {
							this.cleanUpMessage= "ShowCleanUpMsg";
							setCleanUpMessage("ShowCleanUpMsg");
							businessDel.startCleanUp(DateCalenderUtil.convertStringToDate(processingDate));
						} catch (BusinessException e) {
							e.printStackTrace();
							addActionError("CleanUp process was unsuccessful.");
							actionForward =  IWebConstants.DPB_ERROR_URL;
						} catch (TechnicalException e) {
							e.printStackTrace();
							addActionError("CleanUp process was unsuccessful.");
							actionForward =  IWebConstants.DPB_ERROR_URL;
						} catch (Exception e){
							e.printStackTrace();
							addActionError("The clean Up process was unsuccessful for date: " + processingDate);
						}
						addActionMessage("You entered a valid date input");
					} else {
						addActionError("Data does not exist for future date: " + processingDate + ". Please enter valid date");
					}
				} else {
					addActionError("Please enter valid date format (mm/dd/yyyy): " + processingDate);
				}
			} else {
				addActionError("Invalid processing Date");
			}
		} catch(Exception e)	{
			e.printStackTrace();
			//addActionError("Please enter valid date format (mm/dd/yyyy): " + processingDate);
		}
		return actionForward;
	}
	//Data Cleanup for process rerun end

	
	/**
	 * This method is used to toggle scheduler status in properties file- Kshitija
	 * @return actionForward
	 */
	public String toggleScheduler()
	{
		final String methodName = "toggleScheduler";
		LOGGER.enter(CLASSNAME, methodName);
		String actionForward = "schTimeView";
		DPBCommonHelper.updateSchedulerStatus(schedulerStatus);
		//read scheduler status from properties file - Kshitija
		schedulerStatus = DPBCommonHelper.getSchedulerStatus();
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}	
	

	public String getProcessingDate() {
		return processingDate;
	}

	public void setProcessingDate(String processingDate) {
		this.processingDate = processingDate;
	}

	/**
	 * @return the numberDays
	 */
	public int getNumberDays() {
		return numberDays;
	}

	/**
	 * @param numberDays the numberDays to set
	 */
	public void setNumberDays(int numberDays) {
		this.numberDays = numberDays;
	} 

	/**
	 * @return String
	 */
	public String getSchedulerStatus() {
		return schedulerStatus;
	}

	/**
	 * @param schedulerStatus
	 */
	public void setSchedulerStatus(String schedulerStatus) {
		this.schedulerStatus = schedulerStatus;
	}
	
}