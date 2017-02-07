package com.mbusa.dpb.common.domain;


import java.io.Serializable;
import java.util.Date;

public class LeadershipInfo  implements Serializable {

	
private static final long serialVersionUID = 1L;
	
	private String  	dlrId;
	//private String 		vin;
	//private Date 		rtlDate;
	//private int 		qtr;	
	//private String 		vehType;
	private Double 		total;	
	//private String 		text;
	//private String 		po;
	
	
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
}
