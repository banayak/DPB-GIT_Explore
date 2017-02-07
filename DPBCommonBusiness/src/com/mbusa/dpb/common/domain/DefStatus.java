package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class DefStatus implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String defStatusCode;
	private String defStatusName;
	public String getDefStatusCode() {
		return defStatusCode;
	}
	public void setDefStatusCode(String defStatusCode) {
		this.defStatusCode = defStatusCode;
	}
	public String getDefStatusName() {
		return defStatusName;
	}
	public void setDefStatusName(String defStatusName) {
		this.defStatusName = defStatusName;
	}

}
