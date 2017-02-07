package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class UserRoles implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private int userId;
	private String userRole;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	} 
}
