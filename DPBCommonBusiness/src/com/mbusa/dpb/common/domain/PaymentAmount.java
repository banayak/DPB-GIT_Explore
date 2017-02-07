package com.mbusa.dpb.common.domain;

import java.util.LinkedHashMap;

public class PaymentAmount {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double MBDealTotal;
	private double CVPTotal;
	//DPB monthly reconciliation details start
	private LinkedHashMap<String, Double> ftlTotalMap =  new LinkedHashMap<String,Double>();
	private LinkedHashMap<String, Double> smartTotalMap =  new LinkedHashMap<String,Double>();
	private LinkedHashMap<String, Double> vanTotalMap =  new LinkedHashMap<String,Double>();
	private LinkedHashMap<String, Double> carTotalMap =  new LinkedHashMap<String,Double>();
	private LinkedHashMap<String,Double> amgPerfTotalMap = new LinkedHashMap<String,Double>();
	private LinkedHashMap<String,Double> amgEliteTotalMap = new LinkedHashMap<String,Double>();
	//DPB monthly reconciliation details end
	public double getMBDealTotal() {
		return MBDealTotal;
	}
	public void setMBDealTotal(double mBDealTotal) {
		MBDealTotal = mBDealTotal;
	}
	public double getCVPTotal() {
		return CVPTotal;
	}
	public void setCVPTotal(double cVPTotal) {
		CVPTotal = cVPTotal;
	}
	//DPB monthly reconciliation details start
	public LinkedHashMap<String, Double> getFtlTotalMap() {
		return ftlTotalMap;
	}
	public void setFtlTotalMap(LinkedHashMap<String, Double> ftlTotalMap) {
		this.ftlTotalMap = ftlTotalMap;
	}
	public LinkedHashMap<String, Double> getSmartTotalMap() {
		return smartTotalMap;
	}
	public void setSmartTotalMap(LinkedHashMap<String, Double> smartTotalMap) {
		this.smartTotalMap = smartTotalMap;
	}
	public LinkedHashMap<String, Double> getVanTotalMap() {
		return vanTotalMap;
	}
	public void setVanTotalMap(LinkedHashMap<String, Double> vanTotalMap) {
		this.vanTotalMap = vanTotalMap;
	}
	public LinkedHashMap<String, Double> getCarTotalMap() {
		return carTotalMap;
	}
	public void setCarTotalMap(LinkedHashMap<String, Double> carTotalMap) {
		this.carTotalMap = carTotalMap;
	}
	
	public LinkedHashMap<String, Double> getAmgEliteTotalMap() {
		return amgEliteTotalMap;
	}
	public void setAmgEliteTotalMap(LinkedHashMap<String, Double> amgEliteTotalMap) {
		this.amgEliteTotalMap = amgEliteTotalMap;
	}
	/**
	 * @return the amgPerfTotalMap
	 */
	public LinkedHashMap<String, Double> getAmgPerfTotalMap() {
		return amgPerfTotalMap;
	}
	/**
	 * @param amgPerfTotalMap the amgPerfTotalMap to set
	 */
	public void setAmgPerfTotalMap(LinkedHashMap<String, Double> amgPerfTotalMap) {
		this.amgPerfTotalMap = amgPerfTotalMap;
	}
	
	//DPB monthly reconciliation details end
	
	

}
