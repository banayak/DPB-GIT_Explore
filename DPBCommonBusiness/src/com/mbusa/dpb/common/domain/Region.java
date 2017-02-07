package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class Region implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String RegionCode;
	private String RegionName;
	public String getRegionCode() {
		return RegionCode;
	}
	public void setRegionCode(String regionCode) {
		RegionCode = regionCode;
	}
	public String getRegionName() {
		return RegionName;
	}
	public void setRegionName(String regionName) {
		RegionName = regionName;
	}
	

}
