package com.mbusa.dpb.common.domain;

public class KpiFile implements Comparable<KpiFile>{

	private String dealerId;
	private String kpiFiscalQuarter;
	private int kpiId;
	private double kpiPct;
	private int kpiYr;
	
	/**
	 * @return the dealerId
	 */
	public String getDealerId() {
		return dealerId;
	}
	/**
	 * @param dealerId the dealerId to set
	 */
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	/**
	 * @return the kpiFiscalQuarter
	 */
	public String getKpiFiscalQuarter() {
		return kpiFiscalQuarter;
	}
	/**
	 * @param kpiFiscalQuarter the kpiFiscalQuarter to set
	 */
	public void setKpiFiscalQuarter(String kpiFiscalQuarter) {
		this.kpiFiscalQuarter = kpiFiscalQuarter;
	}
	/**
	 * @return the kpiId
	 */
	public int getKpiId() {
		return kpiId;
	}
	/**
	 * @param kpiId the kpiId to set
	 */
	public void setKpiId(int kpiId) {
		this.kpiId = kpiId;
	}
	/**
	 * @return the kpiPct
	 */
	public double getKpiPct() {
		return kpiPct;
	}
	/**
	 * @param kpiPct the kpiPct to set
	 */
	public void setKpiPct(double kpiPct) {
		this.kpiPct = kpiPct;
	}
	/**
	 * @return the kpiYr
	 */
	public int getKpiYr() {
		return kpiYr;
	}
	/**
	 * @param kpiYr the kpiYr to set
	 */
	public void setKpiYr(int kpiYr) {
		this.kpiYr = kpiYr;
	}
	
	@Override
	public int compareTo(KpiFile o) {
		Double kpiPctDblObj = new Double(kpiPct); 
		Double compKpiPctDblObj = new Double(o.getKpiPct());
		return kpiPctDblObj.compareTo(compKpiPctDblObj);
	}
	
	@Override
	public boolean equals(Object o) {
		boolean isEqual = false;
		if(o instanceof KpiFile){
			KpiFile kpiFile = (KpiFile) o;
			isEqual = kpiFile.getKpiId() == kpiId?true:false;
		}
		return isEqual;
	}
}
