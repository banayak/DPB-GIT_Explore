/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: FileDefinitionBean.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle file definition.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 14, 2013     	  1.0        First Draft
 * Syntel   Aug 27, 2013     	  1.0        Done changes as per new requirement
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 * @author RK5005820
 **/
public class FileDefinitionBean implements Serializable{
	
	private int fileDefId;
	private String fileDefName;
	private String fileNameFormat;
	private String description;
	private Time inputTime;
	private Time proceTime;	
	private String baseFolder;
	private String scheduleCode;
	private String triggerCode;
	private String defStatusCode;
	private String createdUserId;
	private String lastUpdUserId;
	private String indCondition;
	private int fileFormatId;
	private List<FileFormatBean> fileFormat;
	private static final long serialVersionUID = 1L;
	private String endDate;
	private boolean flagActive;
	private FileFormatBean fileFormats;
	private String actualProcessDate;
	private Date actualDate;
	
	
	
	
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
	public boolean isFlagActive() {
		return flagActive;
	}
	public void setFlagActive(boolean flagActive) {
		this.flagActive = flagActive;
	}
	public Time getInputTime() {
		return inputTime;
	}
	public void setInputTime(Time inputTime) {
		this.inputTime = inputTime;
	}
	public Time getProceTime() {
		return proceTime;
	}
	public void setProceTime(Time proceTime) {
		this.proceTime = proceTime;
	}
	public String getFileNameFormat() {
		return fileNameFormat;
	}
	public void setFileNameFormat(String fileNameFormat) {
		this.fileNameFormat = fileNameFormat;
	}
	public FileFormatBean getFileFormats() {
		if(fileFormats == null){
			fileFormats = new FileFormatBean();
		}
		return fileFormats;
	}
	public void setFileFormats(FileFormatBean fileFormats) {
		this.fileFormats = fileFormats;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getFileFormatId() {
		return fileFormatId;
	}
	public void setFileFormatId(int fileFormatId) {
		this.fileFormatId = fileFormatId;
	}
	public String getIndCondition() {
		return indCondition;
	}
	public void setIndCondition(String indCondition) {
		this.indCondition = indCondition;
	}
	public List<FileFormatBean> getFileFormat() {
		if(fileFormat == null){
			fileFormat = new ArrayList<FileFormatBean>();
			FileFormatBean formatBean = new FileFormatBean();
			fileFormat.add(formatBean);	
		}
		return fileFormat;
	}
	public void setFileFormat(List<FileFormatBean> fileFormat) {
		this.fileFormat = fileFormat;
	}
	public int getFileDefId() {
		return fileDefId;
	}
	public void setFileDefId(int fileDefId) {
		this.fileDefId = fileDefId;
	}
	public String getFileDefName() {
		return fileDefName;
	}
	public void setFileDefName(String fileDefName) {
		this.fileDefName = fileDefName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getBaseFolder() {
		return baseFolder;
	}
	public void setBaseFolder(String baseFolder) {
		this.baseFolder = baseFolder;
	}
	/**
	 * @return the triggerCode
	 */
	public String getTriggerCode() {
		return triggerCode;
	}
	/**
	 * @param triggerCode the triggerCode to set
	 */
	public void setTriggerCode(String triggerCode) {
		this.triggerCode = triggerCode;
	}
	public String getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}
	public String getLastUpdUserId() {
		return lastUpdUserId;
	}
	public void setLastUpdUserId(String lastUpdUserId) {
		this.lastUpdUserId = lastUpdUserId;
	}
	/**
	 * @return the actualProcessDate
	 */
	public String getActualProcessDate() {
		return actualProcessDate;
	}
	/**
	 * @param actualProcessDate the actualProcessDate to set
	 */
	public void setActualProcessDate(String actualProcessDate) {
		this.actualProcessDate = actualProcessDate;
	}
	/**
	 * @return the scheduleCode
	 */
	public String getScheduleCode() {
		return scheduleCode;
	}
	/**
	 * @param scheduleCode the scheduleCode to set
	 */
	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}
	/**
	 * @return the defStatusCode
	 */
	public String getDefStatusCode() {
		return defStatusCode;
	}
	/**
	 * @param defStatusCode the defStatusCode to set
	 */
	public void setDefStatusCode(String defStatusCode) {
		this.defStatusCode = defStatusCode;
	}
	
}
