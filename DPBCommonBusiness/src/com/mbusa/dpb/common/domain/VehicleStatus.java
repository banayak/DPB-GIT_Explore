package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class VehicleStatus implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String vehicleCode;
	private String vehicleStatus;
	
	
	public String getVehicleCode() {
		return vehicleCode;
	}
	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}
	public String getVehicleStatus() {
		return vehicleStatus;
	}
	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}
	
}
