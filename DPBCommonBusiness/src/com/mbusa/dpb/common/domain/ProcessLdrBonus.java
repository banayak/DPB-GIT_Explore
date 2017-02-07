package com.mbusa.dpb.common.domain;

import java.util.Date;

public class ProcessLdrBonus {
	
	private String  procDpbID;
	private int defID;
	private String processName;
	private String procStatus;
	private String processMsg;
	private Date creationDate;
	private int createdUserId;
	private Date lastUpdatedDate;
	private int lastUpdatedUserId;
	private String flag;
	private int progId;
	private String prgType;
	private String procActStat;
	private java.sql.Date procDate;
	private String displayDate;
	private String actProcType;
	private String reprocess;
	/**
	 * @return the reprocess
	 */
	public String getReprocess() {
		return reprocess;
	}
	/**
	 * @param reprocess the reprocess to set
	 */
	public void setReprocess(String reprocess) {
		this.reprocess = reprocess;
	}
	private String reset;
	
	/**
	 * @return the reset
	 */
	public String getReset() {
		return reset;
	}
	/**
	 * @param reset the reset to set
	 */
	public void setReset(String reset) {
		this.reset = reset;
	}
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
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getProgId() {
		return progId;
	}
	public void setProgId(int progId) {
		this.progId = progId;
	}
	public String getPrgType() {
		return prgType;
	}
	public void setPrgType(String prgType) {
		this.prgType = prgType;
	}
	public String getProcActStat() {
		return procActStat;
	}
	public void setProcActStat(String procActStat) {
		this.procActStat = procActStat;
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
	
	

}
