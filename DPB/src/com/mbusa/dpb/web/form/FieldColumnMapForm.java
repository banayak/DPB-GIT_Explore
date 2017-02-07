/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: FieldColumnMapForm.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle file format column map relation data.
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

import java.util.List;

public class FieldColumnMapForm {

	private int fileColumnMapId;
	private int fileColumnSeqNumber;
	private String fileColumnformatText;
	private int fileColumnLength;
	private String tableName;
	private String columnName;
	private String createdTimestamp;
	private int createdUserId;
	private String lastUpdTimestamp;
	private int lastUpdUserId;
	
	
	public String getFileColumnformatText() {
		return fileColumnformatText;
	}
	public void setFileColumnformatText(String fileColumnformatText) {
		this.fileColumnformatText = fileColumnformatText;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
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
	public int getFileColumnMapId() {
		return fileColumnMapId;
	}
	public void setFileColumnMapId(int fileColumnMapId) {
		this.fileColumnMapId = fileColumnMapId;
	}
	public int getFileColumnSeqNumber() {
		return fileColumnSeqNumber;
	}
	public void setFileColumnSeqNumber(int fileColumnSeqNumber) {
		this.fileColumnSeqNumber = fileColumnSeqNumber;
	}
	public int getFileColumnLength() {
		return fileColumnLength;
	}
	public void setFileColumnLength(int fileColumnLength) {
		this.fileColumnLength = fileColumnLength;
	}
	
}
