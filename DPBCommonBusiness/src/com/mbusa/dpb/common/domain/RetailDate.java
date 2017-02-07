package com.mbusa.dpb.common.domain;

public class RetailDate{
	
	String currentMonth;
	String currentYear;
	String rtlStartDate;
	String rtlEndDate;
	String currentQuarter;
	
	 
	public String getRtlStartDate() {
		return rtlStartDate;
	}
	public void setRtlStartDate(String rtlStartDate) {
		this.rtlStartDate = rtlStartDate;
	}
	public String getRtlEndDate() {
		return rtlEndDate;
	}
	public void setRtlEndDate(String rtlEndDate) {
		this.rtlEndDate = rtlEndDate;
	}
	public String getCurrentMonth() {
		return currentMonth;
	}
	public void setCurrentMonth(String currentMonth) {
		this.currentMonth = currentMonth;
	}
	public String getCurrentYear() {
		return currentYear;
	}
	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}
	public String getCurrentQuarter() {
		return currentQuarter;
	}
	public void setCurrentQuarter(String currentQuarter) {
		this.currentQuarter = currentQuarter;
	}
}