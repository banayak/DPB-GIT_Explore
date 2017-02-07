/**
 * 
 */
package com.mbusa.dpb.common.domain;


import java.io.Serializable;
import java.util.Date;
/**
 * @author RK5005820
 *
 */
public class DPBProcessLogBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dpbProcessId;
	private int processDefinitionId;
	private String processMessage;
	private String dpbProcessStatus;
	private Date creationDate;
	private int createdUserId;
	private Date lastUpdatedDate;
	private int lastUpdatedUserId;
	private DPBProcessBean dpbProcess;
	private int serialNo;
	private String processDay;
	private java.sql.Date procDate;
	private String displayDate;
	private String actProcType;
	private String PrgType;
	
	public String getProcessDay() {
		return processDay;
	}
	public void setProcessDay(String processDay) {
		this.processDay = processDay;
	}
	/**
	 * @return the dpbProcess
	 */
	public DPBProcessBean getDpbProcess() {
		if(dpbProcess != null) {
			return dpbProcess;
		}else {
			dpbProcess = new DPBProcessBean();
		return dpbProcess;
		}
	}
	/**
	 * @param dpbProcess the dpbProcess to set
	 */
	public void setDpbProcess(DPBProcessBean dpbProcess) {
		this.dpbProcess = dpbProcess;
	}
	/**
	 * @return the dpbProcessId
	 */
	public int getDpbProcessId() {
		return dpbProcessId;
	}
	/**
	 * @return the processDefinitionId
	 */
	public int getProcessDefinitionId() {
		return processDefinitionId;
	}
	/**
	 * @return the processMessage
	 */
	public String getProcessMessage() {
		return processMessage;
	}
	/**
	 * @return the dpbProcessStatus
	 */
	public String getDpbProcessStatus() {
		return dpbProcessStatus;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @return the createdUserId
	 */
	public int getCreatedUserId() {
		return createdUserId;
	}
	/**
	 * @return the lastUpdatedDate
	 */
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	/**
	 * @return the lastUpdatedUserId
	 */
	public int getLastUpdatedUserId() {
		return lastUpdatedUserId;
	}
	/**
	 * @param dpbProcessId the dpbProcessId to set
	 */
	public void setDpbProcessId(int dpbProcessId) {
		this.dpbProcessId = dpbProcessId;
	}
	/**
	 * @param processDefinitionId the processDefinitionId to set
	 */
	public void setProcessDefinitionId(int processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	/**
	 * @param processMessage the processMessage to set
	 */
	public void setProcessMessage(String processMessage) {
		this.processMessage = processMessage;
	}
	/**
	 * @param dpbProcessStatus the dpbProcessStatus to set
	 */
	public void setDpbProcessStatus(String dpbProcessStatus) {
		this.dpbProcessStatus = dpbProcessStatus;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @param createdUserId the createdUserId to set
	 */
	public void setCreatedUserId(int createdUserId) {
		this.createdUserId = createdUserId;
	}
	/**
	 * @param lastUpdatedDate the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	/**
	 * @param lastUpdatedUserId the lastUpdatedUserId to set
	 */
	public void setLastUpdatedUserId(int lastUpdatedUserId) {
		this.lastUpdatedUserId = lastUpdatedUserId;
	}
	/**
	 * @param dpbProcess the dpbProcess to set
	 */
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return the procDate
	 */
	public java.sql.Date getProcDate() {
		return procDate;
	}
	/**
	 * @param procDate the procDate to set
	 */
	public void setProcDate(java.sql.Date procDate) {
		this.procDate = procDate;
	}
	/**
	 * @return the displayDate
	 */
	public String getDisplayDate() {
		return displayDate;
	}
	/**
	 * @param displayDate the displayDate to set
	 */
	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}
	/**
	 * @return the actProcType
	 */
	public String getActProcType() {
		return actProcType;
	}
	/**
	 * @param actProcType the actProcType to set
	 */
	public void setActProcType(String actProcType) {
		this.actProcType = actProcType;
	}
	/**
	 * @return the prgType
	 */
	public String getPrgType() {
		return PrgType;
	}
	/**
	 * @param prgType the prgType to set
	 */
	public void setPrgType(String prgType) {
		PrgType = prgType;
	}
		
}
