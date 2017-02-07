package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class PaymentDealerInfo implements Serializable {

	
private static final long serialVersionUID = 1L;
	
	private String  	dlrId;
	private String 		vehType;
	private Double 		total;	
	private int 		vehCount;	
	private String 		text;
	private String 		po;
	private String 		vin;
	
	
	public String getDlrId() {
		return dlrId;
	}
	
	public void setDlrId(String dlrId) {
		this.dlrId = dlrId;
	}
	
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public int getVehCount() {
		return vehCount;
	}

	public void setVehCount(int vehCount) {
		this.vehCount = vehCount;
	}

	public String getVehType() {
		return vehType;
	}

	public void setVehType(String vehType) {
		this.vehType = vehType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPo() {
		return po;
	}

	public void setPo(String po) {
		this.po = po;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}
	
}

