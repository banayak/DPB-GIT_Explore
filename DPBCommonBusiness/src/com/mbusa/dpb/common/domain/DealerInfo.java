/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: CB5002578 
 * Program Name				: DealerDetail.java
 * Program Version			: 1.0
 * Program Description		: This class is used to hold Dealer Information
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * CB5002578   August 27, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class DealerInfo implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	private String  dealerId;	
	private String  dlrAbbrText;
	private String  dlrName;
	private String  dlrNameDba;
	private String  dlrCity;
	private String	dlrState;
	private Timestamp    dlrTerminatedDate;
	
	/**
	 * @return the dealerId
	 */
	public String getDealerId() {
		return dealerId;
	}
	/**
	 * @param dealerId the dealerId to set
	 */
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}	
	/**
	 * @return the dlrAbbrText
	 */
	public String getDlrAbbrText() {
		return dlrAbbrText;
	}
	/**
	 * @param dlrAbbrText the dlrAbbrText to set
	 */
	public void setDlrAbbrText(String dlrAbbrText) {
		this.dlrAbbrText = dlrAbbrText;
	}
	/**
	 * @return the dlrName
	 */
	public String getDlrName() {
		return dlrName;
	}
	/**
	 * @param dlrName the dlrName to set
	 */
	public void setDlrName(String dlrName) {
		this.dlrName = dlrName;
	}
	/**
	 * @return the dlrNameDba
	 */
	public String getDlrNameDba() {
		return dlrNameDba;
	}
	/**
	 * @param dlrNameDba the dlrNameDba to set
	 */
	public void setDlrNameDba(String dlrNameDba) {
		this.dlrNameDba = dlrNameDba;
	}
	/**
	 * @return the dlrCity
	 */
	public String getDlrCity() {
		return dlrCity;
	}
	/**
	 * @param dlrCity the dlrCity to set
	 */
	public void setDlrCity(String dlrCity) {
		this.dlrCity = dlrCity;
	}
	/**
	 * @return the dlrState
	 */
	public String getDlrState() {
		return dlrState;
	}
	/**
	 * @param dlrState the dlrState to set
	 */
	public void setDlrState(String dlrState) {
		this.dlrState = dlrState;
	}
	/**
	 * @return the dlrTerminatedDate
	 */
	public Timestamp getDlrTerminatedDate() {
		return dlrTerminatedDate;
	}
	/**
	 * @param dlrTerminatedDate the dlrTerminatedDate to set
	 */
	public void setDlrTerminatedDate(Timestamp dlrTerminatedDate) {
		this.dlrTerminatedDate = dlrTerminatedDate;
	}
}
