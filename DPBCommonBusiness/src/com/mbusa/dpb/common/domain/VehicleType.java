/**
 * 
 */
package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RK5005820
 *
 */
public class VehicleType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String vehicleType;
	List <ConditionDefinition> conditionList =  new ArrayList <ConditionDefinition>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
}
