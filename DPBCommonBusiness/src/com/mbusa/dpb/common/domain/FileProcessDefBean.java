package com.mbusa.dpb.common.domain;

import java.sql.Time;
import java.util.Calendar;
import com.mbusa.dpb.common.util.DateCalenderUtil;    

public class FileProcessDefBean {
	
	private int processDefinitionId;
	private int definitionId;
	private String definitionType;
	private Calendar processDate;
	private String processDateString;
	private String reasonForUpdate;
	private String processingTrigger;
	private Time processingTime;
	private String status;
	private boolean flag;
	private String user;
	private String processType;
	private boolean reProcessFlag;
	private int recCount; 
	private int progId;
	private java.sql.Date insertDate;
	private java.sql.Date scheduledDate;
	public java.sql.Date getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(java.sql.Date scheduledDate) {
		this.scheduledDate = scheduledDate;
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
	public int getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(int processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public int getDefinitionId() {
		return definitionId;
	}
	public void setDefinitionId(int definitionId) {
		this.definitionId = definitionId;
	}
	public String getDefinitionType() {
		return definitionType;
	}
	public void setDefinitionType(String definitionType) {
		this.definitionType = definitionType;
	}
	public Calendar getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Calendar processDate) {
		this.processDate = processDate;
	}
	public String getReasonForUpdate() {
		return reasonForUpdate;
	}
	public void setReasonForUpdate(String reasonForUpdate) {
		this.reasonForUpdate = reasonForUpdate;
	}
	public String getProcessingTrigger() {
		return processingTrigger;
	}
	public void setProcessingTrigger(String processingTrigger) {
		this.processingTrigger = processingTrigger;
	}
	public Time getProcessingTime() {
		return processingTime;
	}
	public void setProcessingTime(Time processingTime) {
		this.processingTime = processingTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProcessDateString() {
		processDateString = DateCalenderUtil.getDatefromCalendar(processDate);
		if(processDateString == null){
			processDateString = "";
		}
		return processDateString;
	}
	public void setProcessDateString(String processDateString) {
		this.processDateString = processDateString;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return the reProcessFlag
	 */
	public boolean isReProcessFlag() {
		return reProcessFlag;
	}
	/**
	 * @param reProcessFlag the reProcessFlag to set
	 */
	public void setReProcessFlag(boolean reProcessFlag) {
		this.reProcessFlag = reProcessFlag;
	}
	/**
	 * @return the recCount
	 */
	public int getRecCount() {
		return recCount;
	}
	/**
	 * @param recCount the recCount to set
	 */
	public void setRecCount(int recCount) {
		this.recCount = recCount;
	}
	/**
	 * @return the progId
	 */
	public int getProgId() {
		return progId;
	}
	/**
	 * @return the insertDate
	 */
	public java.sql.Date getInsertDate() {
		return insertDate;
	}
	/**
	 * @param insertDate the insertDate to set
	 */
	public void setInsertDate(java.sql.Date insertDate) {
		this.insertDate = insertDate;
	}
	/**
	 * @param progId the progId to set
	 */
	public void setProgId(int progId) {
		this.progId = progId;
	}
	
}
