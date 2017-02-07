/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: VehicleConditionMapping.java
 * Program Version			: 1.0
 * Program Description		: This class is used handlecondition Data. 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 29, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VehicleConditionMapping implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String vehicleType;	
	List <List> conditionList =  new ArrayList <List>();
	private ArrayList<String> pcConditionList =  new ArrayList <String>();	
	private String status; 	
	private String vehicleId;	
	private Integer accId; 
	private String userId;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}


	/**
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}


	/**
	 * @return the pcConditionList
	 */
	public ArrayList<String> getPcConditionList() {
		return pcConditionList;
	}

	/**
	 * @param pcConditionList the pcConditionList to set
	 */
	public void setPcConditionList(ArrayList<String> pcConditionList) {
		this.pcConditionList = pcConditionList;
	}
	/**
	 * @return the conditionList
	 */
	public List<List> getConditionList() {
		if(conditionList == null){	
			conditionList = new ArrayList<List>();
		}
		return conditionList;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the vehicleId
	 */
	public String getVehicleId() {
		return vehicleId;
	}


	/**
	 * @param vehicleId the vehicleId to set
	 */
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}


	/**
	 * @return the accId
	 */
	public Integer getAccId() {
		return accId;
	}


	/**
	 * @param accId the accId to set
	 */
	public void setAccId(Integer accId) {
		this.accId = accId;
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


	/**
	 * @param conditionList the conditionList to set
	 */
	public void setConditionList(List<List> conditionList) {
		this.conditionList = conditionList;
	}
	
	
}
