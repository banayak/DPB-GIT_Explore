package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class ProcessTypes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String processTypeCode;
	private String processTypeName;
	public String getProcessTypeCode() {
		return processTypeCode;
	}
	public void setProcessTypeCode(String processTypeCode) {
		this.processTypeCode = processTypeCode;
	}
	public String getProcessTypeName() {
		return processTypeName;
	}
	public void setProcessTypeName(String processTypeName) {
		this.processTypeName = processTypeName;
	}
	
	
	

}
