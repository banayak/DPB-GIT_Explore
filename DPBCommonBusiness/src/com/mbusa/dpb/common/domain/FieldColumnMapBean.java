/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: FieldColumnMapBean.java
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
package com.mbusa.dpb.common.domain;

import java.io.Serializable;


/**
 * @author RK5005820
 * */
public class FieldColumnMapBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int fileColumnMapId;
	private int fileColumnSeqNumber;
	private String fileColumnformatText;
	private int fileColumnLength;
	private String tableName;
	private String columnName;
	private String createdUserId;
	private String lastUpdUserId;
	private Kpi kpi;
	
	public Kpi getKpi() {
		if(kpi == null){
			kpi = new Kpi();
		}
		return kpi;
	}
	public void setKpi(Kpi kpi) {
		this.kpi = kpi;
	}

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
	
}
