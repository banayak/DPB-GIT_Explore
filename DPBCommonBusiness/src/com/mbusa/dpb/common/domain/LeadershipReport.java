package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.util.List;

public class LeadershipReport implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String dealer;
	private int recCount = 0;
	private String vehicleType;
	
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public int getRecCount() {
		return recCount;
	}
	public void setRecCount(int recCount) {
		this.recCount = recCount;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
}