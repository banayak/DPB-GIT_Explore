/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: RetailDateChangeAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used for retail end date change in the day federated 
 * 							  table, depending on the end date change the relevant processes also gets postponed. 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Feb 19, 2014     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.common.actions;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.ibm.ws.gridcontainer.exceptions.PersistenceException;
import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.RetailDate;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.form.RetailDtChangeForm;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;
/**
 * 
 * @author CB5002578
 *
 */
public class RetailDateChangeAction extends DPBAction {

	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = RetailDateChangeAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(ReportsAction.class);
	String actionForward = "";
	private BusinessDelegate businessDel = new BusinessDelegate();
	RetailDtChangeForm dateForm = new RetailDtChangeForm();
	Map<Integer, String> monthnameList = new HashMap<Integer, String>();
	
	public String viewPage(){
		final String methodName = "viewPage";
		LOGGER.enter(CLASSNAME, methodName);
		this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
		actionForward = "rtlDtChange";	
		RetailDate retailDate = new RetailDate();

		String nextDay = DateCalenderUtil.getNextDate();
		String month="";
		String year="";
		String dbMonth = "";
		int rYear =0;		
		boolean isCurrentMonth = false;
		boolean rtlDataFlag = false;
		try{
			if(WebHelper.isEmptyOrNullString(dateForm.getRtlStartDate())){
				java.sql.Date processDate = DateCalenderUtil.convertStringToDate(nextDay);
				RtlCalenderDefinition rtlDate = MasterDataLookup.getInstance().getRtlCalByDate(processDate);
				rtlDataFlag = isRetailDataValid(rtlDate);
				month = rtlDate.getRetlMthNum();
				year = rtlDate.getRetlYearNum();			
				isCurrentMonth = businessDel.checkValidMonth(month,year);// Check
				if(!isCurrentMonth){//current month monthly processes not executed
					retailDate.setCurrentYear(year);
					retailDate.setCurrentMonth(month);
					retailDate.setRtlStartDate(DateCalenderUtil.convertDateToString(rtlDate.getDteRetlMthBeg()));
					retailDate.setRtlEndDate(DateCalenderUtil.convertDateToString(rtlDate.getDteRetlMthEnd()));
				}else{
					int rMonth = Integer.parseInt(month);
					rYear = Integer.parseInt(year);
					rMonth++;
					if(rMonth > 12 ){
						rMonth = rMonth -12;
						rYear++;
					}
					if(rMonth > 9){
						dbMonth = 	String.valueOf(rMonth);
					}else {
						dbMonth = (String.valueOf(rMonth)).replace(String.valueOf(rMonth), "0"+String.valueOf(rMonth));
					}
					String dbYear = String.valueOf(rYear);
					retailDate = businessDel.getRetailData(dbMonth,dbYear); // Fetch only one record from DPB_DAY
				}			
			}else{
				BeanUtils.copyProperties(retailDate,dateForm);
				retailDate = businessDel.getRetailData(retailDate.getCurrentMonth(),retailDate.getCurrentYear());
			}			
			if(retailDate!= null){
				BeanUtils.copyProperties(dateForm,retailDate);	
			}else{
				throw new BusinessException("DPB.RTL.INVALID.DATA","Retail Data not valid for selected Month and Year. Please contact system administrator or Database Team for more help.");
			}
			
	
		}catch(BusinessException be){
			LOGGER.error("RetailDateChangeAction:BusinessException occured:"+be.getMessage());
			if(be.getMessageKey()!= null && "DPB.RTL.INVALID.DATA".equalsIgnoreCase( be.getMessageKey())){
				addActionError(be.getMessage());
			}else{
				addActionError(IWebConstants.BUSS_EXC_RETV);
			}
		}catch (TechnicalException te) {			
			LOGGER.error("RetailDateChangeAction:TechnicalException occured:"+te.getMessage());
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (PersistenceException pe) {
			LOGGER.error("RetailDateChangeAction:PersistenceException occured:"+pe.getMessage());
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			LOGGER.error("RetailDateChangeAction:Exception occured:"+e.getMessage());
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}
	public boolean isRetailDataValid(RtlCalenderDefinition rtDate){
		boolean validFlag = false;
		if(rtDate!= null && rtDate.getDteCal()!= null && 
				rtDate.getDteRetlMthBeg()!= null && rtDate.getDteRetlMthEnd()!= null 
				&& rtDate.getDteRetlQtrBeg()!= null && rtDate.getDteRetlQtrEnd()!= null 
				&& rtDate.getDteRetlYearBeg()!= null && rtDate.getDteRetlYearEnd()!= null 
				&& rtDate.getRetlMthName()!= null && rtDate.getRetlMthNum()!= null 
				&& rtDate.getRetlQtrNum()!= null && rtDate.getRetlYearNum()!= null){
			validFlag = true;
		}
		return validFlag;
	}
	/**
	 * 
	 * 
	 * @return
	 */	
	public String submitDateChange(){
		final String methodName = "viewPage";
		LOGGER.enter(CLASSNAME, methodName);
		actionForward = "rtlDtChange";		
		if(dateForm!=null){
			String userID = WebHelper.makeNonNullString(this.getUserId());
			validateFormData(dateForm,businessDel);
			if(hasActionErrors()){
				actionForward = "rtlDtChange";
				return actionForward;
			}else {
			Date retailStartDate = DateCalenderUtil.convertStringToDate(dateForm.getRtlStartDate());	
			RtlCalenderDefinition rtlDate = MasterDataLookup.getInstance().getRtlCalByDate(retailStartDate);
			java.sql.Date oldRtlEndDate = rtlDate.getDteRetlMthEnd(); // +1 day
			java.sql.Date oldRtlProcessingDate = DateCalenderUtil.scheduleNextDate(oldRtlEndDate);
			java.sql.Date newRtlEndDate = DateCalenderUtil.convertStringToDate(dateForm.getRtlEndDate()); 
			java.sql.Date newRtlProcessingDate = DateCalenderUtil.scheduleNextDate(newRtlEndDate);
			RtlCalenderDefinition newRtlDate = MasterDataLookup.getInstance().getRtlCalByDate(newRtlEndDate);
			String rtlStartDate = ""; 
			String newEndRtlDate = "";
			int cYear = 0;//current Year
			int cMonth  = 0; //current Month
			int nYear =0; // Next year
			//Update DPB_PROC
			try {
				if(oldRtlEndDate!=null && newRtlEndDate!=null){
					rtlStartDate = DateCalenderUtil.getDateInGivenFormat(retailStartDate, "yyyy-MM-dd");
					newEndRtlDate = DateCalenderUtil.getDateInGivenFormat(newRtlEndDate, "yyyy-MM-dd");
					cYear = Integer.parseInt(rtlStartDate.substring(0,4));//2013-01-25
					cMonth  =  Integer.parseInt(rtlStartDate.substring(5,7));
					nYear = cYear + 1;
					businessDel.updateProcessesRetailDate(oldRtlProcessingDate, newRtlProcessingDate,
																rtlStartDate,newEndRtlDate,
																	cYear,cMonth, nYear,userID);
					MasterDataLookup.getInstance().refreshCacheValue("RTL_CAL_LIST");
				}
				addActionError("Retail End Date Updated Successfully");
			}catch(BusinessException be){
				LOGGER.error("RetailDateChangeAction:BusinessException occured:"+be.getMessage());
				addActionError(IWebConstants.BUSS_EXC_RETV);
			}catch (TechnicalException te) {			
				LOGGER.error("RetailDateChangeAction:TechnicalException occured:"+te.getMessage());
				request.setAttribute (IWebConstants.jspExe, te);
				actionForward =  IWebConstants.DPB_ERROR_URL;
			}catch (PersistenceException pe) {
				LOGGER.error("RetailDateChangeAction:PersistenceException occured:"+pe.getMessage());
				request.setAttribute (IWebConstants.jspExe, pe);
				actionForward =  IWebConstants.DPB_ERROR_URL;
			}catch(Exception e){
				LOGGER.error("RetailDateChangeAction:Exception occured:"+e.getMessage());
				request.setAttribute (IWebConstants.jspExe, e);
				actionForward =  IWebConstants.DPB_ERROR_URL;
			}
			}
			LOGGER.exit(CLASSNAME, methodName);
			return actionForward;
		}else{
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		/**
		 * Step 1
		 * Fetch all the processes from DPB_PROC
		 * Step 2
		 * Update all the processes from DPB_PROC - Update Retail Date and Actual Date
		 * Step 3
		 * Fetch the records from DPB_DAY based on previous retail end date
		 * 
		 * Update the required columns based on the the retail end date of DPB_DAY
		 * 
		 */
		return actionForward;
	}


	/**
	 * @return the dateForm
	 */
	public RetailDtChangeForm getDateForm() {
		return dateForm;
	}


	/**
	 * @param dateForm the dateForm to set
	 */
	public void setDateForm(RetailDtChangeForm dateForm) {
		this.dateForm = dateForm;
	}


	/**
	 * @return the monthnameList
	 */
	public Map<Integer, String> getMonthnameList() {
		return monthnameList;
	}


	/**
	 * @param monthnameList the monthnameList to set
	 */
	public void setMonthnameList(Map<Integer, String> monthnameList) {
		this.monthnameList = monthnameList;
	}


	/**
	 * @return the monthnameList
	 */
	

	private void validateFormData(RetailDtChangeForm dateForm,
			BusinessDelegate businessDel) {
		RetailDate rtlDate = new RetailDate();
		
		if(dateForm.getCurrentYear().isEmpty()){
			addActionError("Please enter Year");
		}else{
			
			try {
				rtlDate = businessDel.getRetailData(dateForm.getCurrentMonth(), dateForm.getCurrentYear());
				if(rtlDate == null){
					addActionError("Retail Data not available for the month and year");
				}else if(DateCalenderUtil.isEmptyOrNullString(rtlDate.getRtlEndDate())){
					addActionError("Retail Data not available for the month and year");
				}else if(!DateCalenderUtil.isEmptyOrNullString(rtlDate.getRtlEndDate())){
					java.sql.Date newRtlEndDate = DateCalenderUtil.convertStringToDate(dateForm.getRtlEndDate()); 
					RtlCalenderDefinition rtlDateEndDate = MasterDataLookup.getInstance().getRtlCalByDate(newRtlEndDate);
					if(rtlDateEndDate.getDteRetlMthEnd()==null || (rtlDateEndDate.getDteCal().compareTo(newRtlEndDate)!=0) || rtlDateEndDate.getRetlYearNum()==null){
						addActionError("Retail Data not available for the Retail End Date");	
					}
				}
			} catch (TechnicalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	/*  
	 * 
	 * int lastDate1 = c1.getActualMaximum(Calendar.DATE);// max number of the month
		c1.set(Calendar.DATE, lastDate1);
		String dateC1 = DateCalenderUtil.getDatefromCalendar(c1);
		
		java.sql.Date startDate = DateCalenderUtil.getCurrentDateTime();
		java.sql.Date endDate = DateCalenderUtil.convertStringToDate(dateC1);
		List<java.sql.Date> monthEnd = DateCalenderUtil.monthlySchedule(startDate, endDate);
		String nextDay = DateCalenderUtil.getNextDayDate();
		boolean isCurrentMonth = false;
		RetailDate rtlDate = new RetailDate();
		if(!monthEnd.isEmpty() && monthEnd!=null && monthEnd.size() > 0){
			String mnth = DateCalenderUtil.convertDateToString(monthEnd.get(0));
			if((nextDay.compareTo(mnth)  == nextDay.compareTo(dateC1)) ){
				isCurrentMonth = true;
			}
			
			java.sql.Date 
			RtlCalenderDefinition rtlDef = MasterDataLookup.getInstance().getRtlCalByDate(startDate);
			
			if(isCurrentMonth){
				int currentMonth =  c2.get(Calendar.MONTH);
				int currentYear = c2.get(Calendar.YEAR);
				currentMonth = currentMonth + 1;
				rtlDate.setCurrentMonth(currentMonth);
				rtlDate.setCurrentYear(currentYear);
			}else {
				int currentMonth = c2.get(Calendar.MONTH);
				int currentYear = c2.get(Calendar.YEAR);
				
				if(currentMonth > 12){
					currentMonth = currentMonth - 12;
					currentYear = currentYear + 1;
				}else{
					currentMonth= currentMonth + 2;
				}
				
//				rtlDate.setCurrentMonth(currentMonth);
//				rtlDate.setCurrentYear(currentYear);
//				
				List mnthList = new ArrayList();
				// month 2
				// 2-12
				int  j = 0;
				List monthnameList = new ArrayList();
				monthnameList.add(1,"January");
				monthnameList.add(2,"Febuary");monthnameList.add(6,"June");monthnameList.add(10,"October");
				monthnameList.add(3,"March");monthnameList.add(7,"July");monthnameList.add(11,"Novemeber");
				monthnameList.add(4,"April");monthnameList.add(8,"August");monthnameList.add(12,"December");
				monthnameList.add(5,"May");monthnameList.add(9,"September");
				for( int i = currentMonth; i<=12; i++){
					mnthList.add(i,monthnameList.get(i) );
				}
				
			}
		}
	 */
}