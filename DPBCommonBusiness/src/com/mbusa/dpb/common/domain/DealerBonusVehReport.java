package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.sql.Date;

public class DealerBonusVehReport implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dealerId;
	private String dealerName;
	private String poNumber;
	private int totalVehicles;
	private int srNo;
	private boolean flag;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDealerId() {
		return dealerId;
	}
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public int getTotalVehicles() {
		return totalVehicles;
	}
	public void setTotalVehicles(int totalVehicles) {
		this.totalVehicles = totalVehicles;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
