package com.mbusa.dpb.common.domain;

public class VehicleConditions {

	private String vehType;
	private int vehCondId;
	private String condName;
	private String condtype;
	private String varName;
	private String checkValue;
	private String regularExp;

	public String getVehType() {
		return vehType;
	}

	public void setVehType(String vehType) {
		this.vehType = vehType;
	}

	public int getVehCondId() {
		return vehCondId;
	}

	public void setVehCondId(int vehCondId) {
		this.vehCondId = vehCondId;
	}

	public String getCondName() {
		return condName;
	}

	public void setCondName(String condName) {
		this.condName = condName;
	}

	public String getCondtype() {
		return condtype;
	}

	public void setCondtype(String condtype) {
		this.condtype = condtype;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}

	public String getRegularExp() {
		return regularExp;
	}

	public void setRegularExp(String regularExp) {
		this.regularExp = regularExp;
	}
}
