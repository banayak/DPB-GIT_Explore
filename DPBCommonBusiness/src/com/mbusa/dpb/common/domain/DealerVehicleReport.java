package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.sql.Date;

public class DealerVehicleReport implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int srNo;	
	private String vin;
	private int  quarter;
	private int accountBalance;
	

	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	public int getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	

}
