/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: RtlCalenderDefinition.java
 * Program Version			: 1.0
 * Program Description		: This class is used to hold retail calender data.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 27, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.domain;

import java.sql.Date;

public class RtlCalenderDefinition {
	private Date dteCal;
	private String dayDesc;	
	private Date dteRetlMthBeg;
	private Date dteRetlMthEnd;
	private String retlMthNum;
	private String retlMthName;
	private int retlMthDayNum;
	private Date dteRetlQtrBeg;
	private Date dteRetlQtrEnd;
	private String retlQtrNum;
	private Date dteRetlYearBeg;
	private Date dteRetlYearEnd;
	private String retlYearNum;
	private String creatdUser;
	private Date createDate;
	private String updatedUser;
	private Date updateDate;
	private Date dteRetlWeekBeg;
	private Date dteRetlWeekEnd;
	/**
	 * @return the dteCal
	 */
	public Date getDteCal() {
		return dteCal;
	}
	/**
	 * @param dteCal the dteCal to set
	 */
	public void setDteCal(Date dteCal) {
		this.dteCal = dteCal;
	}
	/**
	 * @return the dayDesc
	 */
	public String getDayDesc() {
		return dayDesc;
	}
	/**
	 * @param dayDesc the dayDesc to set
	 */
	public void setDayDesc(String dayDesc) {
		this.dayDesc = dayDesc;
	}
	/**
	 * @return the dteRetlMthBeg
	 */
	public Date getDteRetlMthBeg() {
		return dteRetlMthBeg;
	}
	/**
	 * @param dteRetlMthBeg the dteRetlMthBeg to set
	 */
	public void setDteRetlMthBeg(Date dteRetlMthBeg) {
		this.dteRetlMthBeg = dteRetlMthBeg;
	}
	/**
	 * @return the dteRetlMthEnd
	 */
	public Date getDteRetlMthEnd() {
		return dteRetlMthEnd;
	}
	/**
	 * @param dteRetlMthEnd the dteRetlMthEnd to set
	 */
	public void setDteRetlMthEnd(Date dteRetlMthEnd) {
		this.dteRetlMthEnd = dteRetlMthEnd;
	}
	/**
	 * @return the retlMthNum
	 */
	public String getRetlMthNum() {
		return retlMthNum;
	}
	/**
	 * @param retlMthNum the retlMthNum to set
	 */
	public void setRetlMthNum(String retlMthNum) {
		this.retlMthNum = retlMthNum;
	}
	/**
	 * @return the retlMthName
	 */
	public String getRetlMthName() {
		return retlMthName;
	}
	/**
	 * @param retlMthName the retlMthName to set
	 */
	public void setRetlMthName(String retlMthName) {
		this.retlMthName = retlMthName;
	}
	/**
	 * @return the retlMthDayNum
	 */
	public int getRetlMthDayNum() {
		return retlMthDayNum;
	}
	/**
	 * @param retlMthDayNum the retlMthDayNum to set
	 */
	public void setRetlMthDayNum(int retlMthDayNum) {
		this.retlMthDayNum = retlMthDayNum;
	}
	/**
	 * @return the dteRetlQtrBeg
	 */
	public Date getDteRetlQtrBeg() {
		return dteRetlQtrBeg;
	}
	/**
	 * @param dteRetlQtrBeg the dteRetlQtrBeg to set
	 */
	public void setDteRetlQtrBeg(Date dteRetlQtrBeg) {
		this.dteRetlQtrBeg = dteRetlQtrBeg;
	}
	/**
	 * @return the dteRetlQtrEnd
	 */
	public Date getDteRetlQtrEnd() {
		return dteRetlQtrEnd;
	}
	/**
	 * @param dteRetlQtrEnd the dteRetlQtrEnd to set
	 */
	public void setDteRetlQtrEnd(Date dteRetlQtrEnd) {
		this.dteRetlQtrEnd = dteRetlQtrEnd;
	}
	/**
	 * @return the retlQtrNum
	 */
	public String getRetlQtrNum() {
		return retlQtrNum;
	}
	/**
	 * @param retlQtrNum the retlQtrNum to set
	 */
	public void setRetlQtrNum(String retlQtrNum) {
		this.retlQtrNum = retlQtrNum;
	}
	/**
	 * @return the dteRetlYearBeg
	 */
	public Date getDteRetlYearBeg() {
		return dteRetlYearBeg;
	}
	/**
	 * @param dteRetlYearBeg the dteRetlYearBeg to set
	 */
	public void setDteRetlYearBeg(Date dteRetlYearBeg) {
		this.dteRetlYearBeg = dteRetlYearBeg;
	}
	/**
	 * @return the dteRetlYearEnd
	 */
	public Date getDteRetlYearEnd() {
		return dteRetlYearEnd;
	}
	/**
	 * @param dteRetlYearEnd the dteRetlYearEnd to set
	 */
	public void setDteRetlYearEnd(Date dteRetlYearEnd) {
		this.dteRetlYearEnd = dteRetlYearEnd;
	}
	/**
	 * @return the retlYearNum
	 */
	public String getRetlYearNum() {
		return retlYearNum;
	}
	/**
	 * @param retlYearNum the retlYearNum to set
	 */
	public void setRetlYearNum(String retlYearNum) {
		this.retlYearNum = retlYearNum;
	}
	/**
	 * @return the creatdUser
	 */
	public String getCreatdUser() {
		return creatdUser;
	}
	/**
	 * @param creatdUser the creatdUser to set
	 */
	public void setCreatdUser(String creatdUser) {
		this.creatdUser = creatdUser;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the updatedUser
	 */
	public String getUpdatedUser() {
		return updatedUser;
	}
	/**
	 * @param updatedUser the updatedUser to set
	 */
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the dteRetlWeekBeg
	 */
	public Date getDteRetlWeekBeg() {
		return dteRetlWeekBeg;
	}
	/**
	 * @param dteRetlWeekBeg the dteRetlWeekBeg to set
	 */
	public void setDteRetlWeekBeg(Date dteRetlWeekBeg) {
		this.dteRetlWeekBeg = dteRetlWeekBeg;
	}
	/**
	 * @return the dteRetlWeekEnd
	 */
	public Date getDteRetlWeekEnd() {
		return dteRetlWeekEnd;
	}
	/**
	 * @param dteRetlWeekEnd the dteRetlWeekEnd to set
	 */
	public void setDteRetlWeekEnd(Date dteRetlWeekEnd) {
		this.dteRetlWeekEnd = dteRetlWeekEnd;
	}	
}
