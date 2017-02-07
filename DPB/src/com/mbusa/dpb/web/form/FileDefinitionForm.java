/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: FileDefinitionForm.java
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
package com.mbusa.dpb.web.form;

import java.util.ArrayList;
import java.util.List;

import com.mbusa.dpb.common.domain.FileFormatBean;
import com.mbusa.dpb.common.domain.Trigger;


/**
 * @author RK5005820
 **/
public class FileDefinitionForm{
	private int fileDefId;
	private String fileDefName;
	private String fileNameFormat;
	private String description;
	private String inTime;
	private String readTime;	
	private String baseFolder;
	private String scheduleCode;
	private String triggerCode;
	private String defStatusCode;
	private String createdTimestamp;
	private int createdUserId;
	private String lastUpdTimestamp;
	private int lastUpdUserId;
	private String indCondition;
	private int fileFormatId;
	private String[] fileDescription;
	private boolean flagActive;
	private List<FileFormatBean> fileFormat;
	private FileFormatBean fileFormats;
	private String status;
		
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
	public boolean isFlagActive() {
		return flagActive;
	}
	public void setFlagActive(boolean flagActive) {
		this.flagActive = flagActive;
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
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getReadTime() {
		return readTime;
	}
	public void setReadTime(String readTime) {
		this.readTime = readTime;
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
	public String getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public int getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(int createdUserId) {
		this.createdUserId = createdUserId;
	}
	public String getLastUpdTimestamp() {
		return lastUpdTimestamp;
	}
	public void setLastUpdTimestamp(String lastUpdTimestamp) {
		this.lastUpdTimestamp = lastUpdTimestamp;
	}
	public int getLastUpdUserId() {
		return lastUpdUserId;
	}
	public void setLastUpdUserId(int lastUpdUserId) {
		this.lastUpdUserId = lastUpdUserId;
	}
	public String[] getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String[] fileDescription) {
		this.fileDescription = fileDescription;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
