/**
 * 
 */
package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author SK5008848
 *
 */
public class FileProcessLogMessages implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer processId;
	private Integer processDefId;
	private String processMessage;
	private String processStatusCode;
	private Timestamp createdDTS; 
	private String userId;
	private Timestamp lastUpdtDTS;
	/**
	 * @return the processId
	 */
	public Integer getProcessId() {
		return processId;
	}
	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	/**
	 * @return the processDefId
	 */
	public Integer getProcessDefId() {
		return processDefId;
	}
	/**
	 * @param processDefId the processDefId to set
	 */
	public void setProcessDefId(Integer processDefId) {
		this.processDefId = processDefId;
	}
	/**
	 * @return the processMessage
	 */
	public String getProcessMessage() {
		return processMessage;
	}
	/**
	 * @param processMessage the processMessage to set
	 */
	public void setProcessMessage(String processMessage) {
		this.processMessage = processMessage;
	}
	/**
	 * @return the processStatusCode
	 */
	public String getProcessStatusCode() {
		return processStatusCode;
	}
	/**
	 * @param processStatusCode the processStatusCode to set
	 */
	public void setProcessStatusCode(String processStatusCode) {
		this.processStatusCode = processStatusCode;
	}
	/**
	 * @return the createdDTS
	 */
	public Timestamp getCreatedDTS() {
		return createdDTS;
	}
	/**
	 * @param createdDTS the createdDTS to set
	 */
	public void setCreatedDTS(Timestamp createdDTS) {
		this.createdDTS = createdDTS;
	}
	/**
	 * @return the lastUpdtDTS
	 */
	public Timestamp getLastUpdtDTS() {
		return lastUpdtDTS;
	}
	/**
	 * @param lastUpdtDTS the lastUpdtDTS to set
	 */
	public void setLastUpdtDTS(Timestamp lastUpdtDTS) {
		this.lastUpdtDTS = lastUpdtDTS;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
