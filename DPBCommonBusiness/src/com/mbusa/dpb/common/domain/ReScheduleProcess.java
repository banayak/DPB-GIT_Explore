package com.mbusa.dpb.common.domain;

import java.sql.Date;

public class ReScheduleProcess {

	private int programId;
	private int fileId;
	private int reportId;
	private int leadershipId;
	private int processId;
	private Date dateCal;
	private Date actualDate;
	private String processTypeCode;
	/**
	 * @return the programId
	 */
	public int getProgramId() {
		return programId;
	}
	/**
	 * @param programId the programId to set
	 */
	public void setProgramId(int programId) {
		this.programId = programId;
	}
	/**
	 * @return the fileId
	 */
	public int getFileId() {
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	/**
	 * @return the reportId
	 */
	public int getReportId() {
		return reportId;
	}
	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	/**
	 * @return the leadershipId
	 */
	public int getLeadershipId() {
		return leadershipId;
	}
	/**
	 * @param leadershipId the leadershipId to set
	 */
	public void setLeadershipId(int leadershipId) {
		this.leadershipId = leadershipId;
	}
	/**
	 * @return the dateCal
	 */
	public Date getDateCal() {
		return dateCal;
	}
	/**
	 * @param dateCal the dateCal to set
	 */
	public void setDateCal(Date dateCal) {
		this.dateCal = dateCal;
	}
	/**
	 * @return the actualDate
	 */
	public Date getActualDate() {
		return actualDate;
	}
	/**
	 * @param actualDate the actualDate to set
	 */
	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
	}
	/**
	 * @return the processId
	 */
	public int getProcessId() {
		return processId;
	}
	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	/**
	 * @return the processTypeCode
	 */
	public String getProcessTypeCode() {
		return processTypeCode;
	}
	/**
	 * @param processTypeCode the processTypeCode to set
	 */
	public void setProcessTypeCode(String processTypeCode) {
		this.processTypeCode = processTypeCode;
	}
	
}
