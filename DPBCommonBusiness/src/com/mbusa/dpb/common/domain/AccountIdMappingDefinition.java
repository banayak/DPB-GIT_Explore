package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class AccountIdMappingDefinition implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String programComponentName;
	//private int accountId;
	private String statusMapping;
	private String indicator;	
	private int idBnsPgm;
	private String accountCFCCR;
	private String accountCFCDT;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProgramComponentName() {
		return programComponentName;
	}
	public void setProgramComponentName(String programComponentName) {
		this.programComponentName = programComponentName;
	}
	/*public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}*/
	public String getStatusMapping() {
		return statusMapping;
	}
	public void setStatusMapping(String statusMapping) {
		this.statusMapping = statusMapping;
	}
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	public int getIdBnsPgm() {
		return idBnsPgm;
	}
	public void setIdBnsPgm(int idBnsPgm) {
		this.idBnsPgm = idBnsPgm;
	}
	public String getAccountCFCCR() {
		return accountCFCCR;
	}
	public void setAccountCFCCR(String accountCFCCR) {
		this.accountCFCCR = accountCFCCR;
	}
	public String getAccountCFCDT() {
		return accountCFCDT;
	}
	public void setAccountCFCDT(String accountCFCDT) {
		this.accountCFCDT = accountCFCDT;
	}
	
	
	

}
