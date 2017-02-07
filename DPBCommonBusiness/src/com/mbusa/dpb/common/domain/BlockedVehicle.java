package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.sql.Date;

public class BlockedVehicle implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int srNo;	
	private String vehicleId;
	private String poNumber;
	private String idDealer;
	private String txtBlckReason;
	private Date updatedDate;
	private String displayDate;
	private String poNo;
	private String reason;
	private Date retailDate;
	private String vinNo;
	private String displayetailDate;
	private String ddrUsecode;
	
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String getIdDealer() {
		return idDealer;
	}
	public void setIdDealer(String idDealer) {
		this.idDealer = idDealer;
	}
	public String getTxtBlckReason() {
		return txtBlckReason;
	}
	public void setTxtBlckReason(String txtBlckReason) {
		this.txtBlckReason = txtBlckReason;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date date) {
		this.updatedDate = date;
	}
	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDisplayDate() {
		return displayDate;
	}
	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}
	public Date getRetailDate() {
		return retailDate;
	}
	public void setRetailDate(Date retailDate) {
		this.retailDate = retailDate;
	}
	public String getVinNo() {
		return vinNo;
	}
	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}
	public String getDisplayetailDate() {
		return displayetailDate;
	}
	public void setDisplayetailDate(String displayetailDate) {
		this.displayetailDate = displayetailDate;
	}
	public String getDdrUsecode() {
		return ddrUsecode;
	}
	public void setDdrUsecode(String ddrUsecode) {
		this.ddrUsecode = ddrUsecode;
	}

}
