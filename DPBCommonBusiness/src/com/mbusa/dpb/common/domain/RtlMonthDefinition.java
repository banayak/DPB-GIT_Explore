package com.mbusa.dpb.common.domain;

import java.util.Date;

public class RtlMonthDefinition {
	
	private String status;
	private Date startDate;
	private Date endDate;
	
	private int yearSelection;
	private int monthSelection;
	private String actualStatus;
	private int quarter;//Hidden
	private int id;
	private int noOfDays;
	private String lastUserId;
	private String createdUserId;
	private java.sql.Date lastDate;
	private java.sql.Date currentDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/*public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}*/
	

	public int getYearSelection() {
		return yearSelection;
	}
	public void setYearSelection(int yearSelection) {
		this.yearSelection = yearSelection;
	}
	public int getMonthSelection() {
		return monthSelection;
	}
	public void setMonthSelection(int monthSelection) {
		this.monthSelection = monthSelection;
	}
	public String getActualStatus() {
		return actualStatus;
	}
	public void setActualStatus(String actualStatus) {
		this.actualStatus = actualStatus;
	}
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getLastUserId() {
		return lastUserId;
	}
	public void setLastUserId(String lastUserId) {
		this.lastUserId = lastUserId;
	}
	public String getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}
	public java.sql.Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(java.sql.Date lastDate) {
		this.lastDate = lastDate;
	}
	public java.sql.Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(java.sql.Date currentDate) {
		this.currentDate = currentDate;
	}
	
	
	

}
