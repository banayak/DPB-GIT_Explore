package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * @author Kshitija_Vadnerkar
 *
 */
public class ReportAmount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double MBDealTotal;
	private double CVPTotal;
	//DPB monthly reconciliation details start
	private LinkedHashMap<String, Double> ftlTotalMap =  new LinkedHashMap<String,Double>();
	private LinkedHashMap<String, Double> vanTotalMap =  new LinkedHashMap<String,Double>();
	private LinkedHashMap<String, Double> smartTotalMap =  new LinkedHashMap<String,Double>();
	private LinkedHashMap<String, Double> passengerTotalMap =  new LinkedHashMap<String,Double>();
	private LinkedHashMap<String,Double> amgEliteTotalMap = new LinkedHashMap<String,Double>();
	private LinkedHashMap<String,Double> amgPerfTotalMap = new LinkedHashMap<String,Double>();
	private boolean isMonthlyProcess = false;
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
	/**
	 * @return the vanTotalMap
	 */
	public LinkedHashMap<String, Double> getVanTotalMap() {
		return vanTotalMap;
	}
	/**
	 * @param vanTotalMap the vanTotalMap to set
	 */
	public void setVanTotalMap(LinkedHashMap<String, Double> vanTotalMap) {
		this.vanTotalMap = vanTotalMap;
	}
	
	/**
	 * @return the smartTotalMap
	 */
	public LinkedHashMap<String, Double> getSmartTotalMap() {
		return smartTotalMap;
	}
	/**
	 * @param smartTotalMap the smartTotalMap to set
	 */
	public void setSmartTotalMap(LinkedHashMap<String, Double> smartTotalMap) {
		this.smartTotalMap = smartTotalMap;
	}
	
	/**
	 * @return the passengerTotalMap
	 */
	public LinkedHashMap<String, Double> getPassengerTotalMap() {
		return passengerTotalMap;
	}
	/**
	 * @param ftlTotalMap the ftlTotalMap to set
	 */
	public void setPassengerTotalMap(LinkedHashMap<String, Double> passengerTotalMap) {
		this.passengerTotalMap = passengerTotalMap;
	}
	
	/**
	 * @return the ftlTotalMap
	 */
	public LinkedHashMap<String, Double> getFtlTotalMap() {
		return ftlTotalMap;
	}
	/**
	 * @param ftlTotalMap the ftlTotalMap to set
	 */
	public void setFtlTotalMap(LinkedHashMap<String, Double> ftlTotalMap) {
		this.ftlTotalMap = ftlTotalMap;
	}
	/**
	 * @return the amgEliteTotalMap
	 */
	public LinkedHashMap<String, Double> getAmgEliteTotalMap() {
		return amgEliteTotalMap;
	}
	/**
	 * @param amgEliteTotalMap the amgEliteTotalMap to set
	 */
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
	/**
	 * @return the isMonthlyProcess
	 */
	public boolean isMonthlyProcess() {
		return isMonthlyProcess;
	}
	/**
	 * @param isMonthlyProcess the isMonthlyProcess to set
	 */
	public void setMonthlyProcess(boolean isMonthlyProcess) {
		this.isMonthlyProcess = isMonthlyProcess;
	}
	
	//DPB monthly reconciliation details end

}
