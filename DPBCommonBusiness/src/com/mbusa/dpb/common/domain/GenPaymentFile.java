package com.mbusa.dpb.common.domain;

import java.util.Date;

public class GenPaymentFile {
	
	private String  procDpbID;
	private int defID;
	private String processName;
	private String procStatus;
	private String processMsg;
	private Date creationDate;
	private int createdUserId;
	private String lastUpdatedDate;
	private int lastUpdatedUserId;
	
	
	public String getProcDpbID() {
		return procDpbID;
	}
	public void setProcDpbID(String procDpbID) {
		this.procDpbID = procDpbID;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcStatus() {
		return procStatus;
	}
	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public int getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(int createdUserId) {
		this.createdUserId = createdUserId;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public int getLastUpdatedUserId() {
		return lastUpdatedUserId;
	}
	public void setLastUpdatedUserId(int lastUpdatedUserId) {
		this.lastUpdatedUserId = lastUpdatedUserId;
	}
	public int getDefID() {
		return defID;
	}
	public void setDefID(int defID) {
		this.defID = defID;
	}
	public String getProcessMsg() {
		return processMsg;
	}
	public void setProcessMsg(String processMsg) {
		this.processMsg = processMsg;
	}
	
	

}
