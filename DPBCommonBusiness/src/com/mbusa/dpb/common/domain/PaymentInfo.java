package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class PaymentInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String  	dlrId;
	private String 		vehicleType;
	private int 		pgmId;
	private String 		acctId;
	private Double  		bnsCalcAmt;
	private String  	poNumber;	
	private String 		txtName;	
	
	
	public String getPoNumber() {
		return poNumber;
	}
	
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}	
	
	public String getDlrId() {
		return dlrId;
	}
	
	public void setDlrId(String dlrId) {
		this.dlrId = dlrId;
	}
	
	
	public int getPgmId() {
		return pgmId;
	}
	
	public void setPgmId(int pgmId) {
		this.pgmId = pgmId;
	}
	
	
	public Double getBnsCalcAmt() {
		return bnsCalcAmt;
	}
	
	public void setBnsCalcAmt(Double bnsCalcAmt) {
		this.bnsCalcAmt = bnsCalcAmt;
	}
	
	
	public String getVehicleType() {
		return vehicleType;
	}
	
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	
	public String getTxtName() {
		return txtName;
	}
	
	public void setTxtName(String txtName) {
		this.txtName = txtName;
	}
	
	
	public String getAcctId() {
		return acctId;
	}
	
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
	
}
