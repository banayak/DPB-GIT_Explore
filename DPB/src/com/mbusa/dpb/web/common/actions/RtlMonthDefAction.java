package com.mbusa.dpb.web.common.actions;

import java.util.Date;

import com.mbusa.dpb.common.domain.RtlMonthDefinition;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.form.RtlMonthDefinitionForm;
import com.mbusa.dpb.web.helper.WebHelper;
public class RtlMonthDefAction extends DPBAction   {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RtlMonthDefinition rtlMonthDef = null;
	private RtlMonthDefinitionForm rtlMonthDefForm ;
	private String actionForward = "errorPage";
	BusinessDelegate bDelegate  =  new BusinessDelegate();
	Date sDate=new Date();

	public String getCurrentRetailMonth() {
		try{
			this.setMenuTabFocus(5);
			actionForward = "rtlMonthView";
			rtlMonthDef = bDelegate.getStartDate();
			rtlMonthDefForm = new RtlMonthDefinitionForm();				
			populateRtlMonthForm(rtlMonthDef,rtlMonthDefForm);			
		} catch(BusinessException be){
			
		}catch (TechnicalException te) {
			actionForward =  "errorPage";
		}
		return actionForward;
	}
	public void populateRtlMonthForm(RtlMonthDefinition rtlMonthDef,RtlMonthDefinitionForm rtlMonthDefForm) {
		
		String conDate;
		if(rtlMonthDef.getStartDate() == null)
		{
			//sDate=WebHelper.addDays(WebHelper.getCurrentDate(),1);
			sDate=WebHelper.addDays(WebHelper.getCurrentDate(),1);
		}
		else
		{
			sDate=WebHelper.SqlDateToUtilDate(rtlMonthDef.getStartDate());
		}
		conDate=WebHelper.formatDatetoString(sDate);
		
		String counter=WebHelper.calculateDateCounter(conDate); /*Date Calculation Starts Here*/
		
		rtlMonthDefForm.setStartDate(conDate);
		rtlMonthDefForm.setDateCounter(counter);/*End Date Start Position */
		rtlMonthDefForm.setStatus("1");
		
	}
	private void populateRtlMonthDefinition(){
		
				int querter=WebHelper.calculateQyarter(rtlMonthDefForm.getMonthSelection());
			
				Date stDate=new Date();
				Date endDate=new Date();
				rtlMonthDef =  new RtlMonthDefinition();				
				
				rtlMonthDef.setYearSelection(rtlMonthDefForm.getYearSelection());
				rtlMonthDef.setMonthSelection(rtlMonthDefForm.getMonthSelection());
				rtlMonthDef.setQuarter(querter);
				rtlMonthDef.setNoOfDays(rtlMonthDefForm.getNoOfDays());
				
				stDate=WebHelper.convertStringToUtilDate(rtlMonthDefForm.getStartDate());
				endDate=WebHelper.convertStringToUtilDate(rtlMonthDefForm.getEndDate());
				rtlMonthDef.setEndDate(endDate);
				rtlMonthDef.setStartDate(stDate);
				rtlMonthDef.setId(rtlMonthDefForm.getId());
				rtlMonthDef.setStatus(WebHelper.getActualStatus(rtlMonthDefForm.getStatus()));
				//Auto poulate dateCounter
				String counter=WebHelper.calculateDateCounter(rtlMonthDefForm.getStartDate()); /*Date Calculation Starts Here*/
				rtlMonthDefForm.setDateCounter(counter);/*End Date Start Position */
				//String status=WebHelper.getActualStatus(rtlMonthDefForm.getStatus());
				//rtlMonthDef.setActualStatus(status);
			/*	
			 * Future Use
			 * rtlMonthDef.setCreatedUserId(createdUserId);rtlMonthDefForm.getCreatedUserId();
				rtlMonthDef.setLastUserId(lastUserId); rtlMonthDefForm.getLastUserId();
				WebHelper.convertToSqlDate()convertToSqlDate
				rtlMonthDef.setCurrentDate(currentDate);rtlMonthDefForm.setCurrentDate();
				rtlMonthDef.setLastDate(lastDate);rtlMonthDefForm.setLastDate(); */
					
	}

	public String saveRetailMonthDef()
	{
		
		
		try {
			
			populateRtlMonthDefinition();
			bDelegate.createRetailMonthEntry(rtlMonthDef);
			
		} catch(BusinessException be){
			
		}catch (TechnicalException te) {
			actionForward =  "ErrorPage";
		}
		return "saveRtlMonth";
		
	}
	
	
	public RtlMonthDefinitionForm getRtlMonthDefForm() {
		return rtlMonthDefForm;
	}
	public void setRtlMonthDefForm(RtlMonthDefinitionForm rtlMonthDefForm) {
		this.rtlMonthDefForm = rtlMonthDefForm;
	}
	
	


	

	
	
	
	
}