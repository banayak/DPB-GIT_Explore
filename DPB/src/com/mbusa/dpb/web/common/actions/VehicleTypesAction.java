package com.mbusa.dpb.web.common.actions;

import com.opensymphony.xwork2.ActionSupport;

public class VehicleTypesAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
public String getVehicleTypes(){
		
		try {
			//Connection conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "vehicleTypesView";
		
	}
}