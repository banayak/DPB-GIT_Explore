package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class AccountMapping implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String vehType;
	private String pgmname;
	private String accountId;
	private String status;
	private String indpgm;
	private int idpgm;
	
	public String getVehType() {
		return vehType;
	}
	public void setVehType(String vehType) {
		this.vehType = vehType;
	}
	public String getPgmname() {
		return pgmname;
	}
	public void setPgmname(String pgmname) {
		this.pgmname = pgmname;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIndpgm() {
		return indpgm;
	}
	public void setIndpgm(String indpgm) {
		this.indpgm = indpgm;
	}
	public int getIdpgm() {
		return idpgm;
	}
	public void setIdpgm(int idpgm) {
		this.idpgm = idpgm;
	}

}
