/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: FileFormatForm.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle file format definition.
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

import com.mbusa.dpb.common.domain.FieldColumnMapBean;
import com.mbusa.dpb.common.domain.Kpi;

/**
 * @author RK5005820
 *
 */
public class FileFormatForm {

	private int fileFormatId;
	private String formatName;
	private String formatDescription;
	private String inbountFileIndicator;
	private String indHeader;
	private String indDelimited;
	private int fixedLineWidth;
	private int columnCount;
	private String defStatusCode;
	private String createdTimestamp;
	private int createdUserId;
	private String lastUpdTimestamp;
	private int lastUpdUserId;
	private String tableName;
	private String[] description;
	private List<FieldColumnMapBean> fileMapingList;
	private List<String> columnNames;
	private List<Kpi> kpList;
	private boolean flagActive;
	private String status;
	

	public boolean isFlagActive() {
		return flagActive;
	}
	public void setFlagActive(boolean flagActive) {
		this.flagActive = flagActive;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<Kpi> getKpList() {
		return kpList;
	}
	public void setKpList(List<Kpi> kpList) {
		this.kpList = kpList;
	}
	public List<String> getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}
	public List<FieldColumnMapBean> getFileMapingList() {
		if(fileMapingList == null){
			fileMapingList = new ArrayList<FieldColumnMapBean>();
		}
		return fileMapingList;
	}
	public void setFileMapingList(List<FieldColumnMapBean> fileMapingList) {
		this.fileMapingList = fileMapingList;
	}
	public String getFormatName() {
		return formatName;
	}
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}
	public String getFormatDescription() {
		return formatDescription;
	}
	public void setFormatDescription(String formatDescription) {
		this.formatDescription = formatDescription;
	}
	public int getFileFormatId() {
		return fileFormatId;
	}
	public void setFileFormatId(int fileFormatId) {
		this.fileFormatId = fileFormatId;
	}
	public String getInbountFileIndicator() {
		return inbountFileIndicator;
	}
	public void setInbountFileIndicator(String inbountFileIndicator) {
		this.inbountFileIndicator = inbountFileIndicator;
	}
	public String getIndHeader() {
		return indHeader;
	}
	public void setIndHeader(String indHeader) {
		this.indHeader = indHeader;
	}
	public String getIndDelimited() {
		return indDelimited;
	}
	public void setIndDelimited(String indDelimited) {
		this.indDelimited = indDelimited;
	}
	public int getFixedLineWidth() {
		return fixedLineWidth;
	}
	public void setFixedLineWidth(int fixedLineWidth) {
		this.fixedLineWidth = fixedLineWidth;
	}
	public int getColumnCount() {
		return columnCount;
	}
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
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
	public String[] getDescription() {
		return description;
	}
	public void setDescription(String[] description) {
		this.description = description;
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
