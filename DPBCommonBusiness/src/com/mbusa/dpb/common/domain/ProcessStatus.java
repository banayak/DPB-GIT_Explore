package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class ProcessStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	private String processStatusCd;
	private String processStatusName;
	public String getProcessStatusCd() {
		return processStatusCd;
	}
	public void setProcessStatusCd(String processStatusCd) {
		this.processStatusCd = processStatusCd;
	}
	public String getProcessStatusName() {
		return processStatusName;
	}
	public void setProcessStatusName(String processStatusName) {
		this.processStatusName = processStatusName;
	}
	

}
