package com.mbusa.dpb.web.form;

import javax.persistence.Entity;

import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;


@Entity

public class RtlMonthDefinitionForm {
	
		private String  status;
		private String startDate;
		private String endDate;
		private int yearSelection;
		private int monthSelection;
		private String actualStatus;
		private int quarter;//Hidden
		private int id;
		private String dateCounter;
		private int noOfDays;
		
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		 
		public String getStartDate() {
			return startDate;
		}
	
		public String getEndDate() {
			return endDate;
		}
		
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
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
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getDateCounter() {
			return dateCounter;
		}
		public void setDateCounter(String dateCounter) {
			this.dateCounter = dateCounter;
		}
		public int getNoOfDays() {
			return noOfDays;
		}
		public void setNoOfDays(int noOfDays) {
			this.noOfDays = noOfDays;
		}
		
		
}
