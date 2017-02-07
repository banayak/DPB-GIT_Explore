package com.mbusa.dpb.web.common.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.DashBoardDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;

public class RtlCalAction extends DPBAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = RtlCalAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(RtlCalAction.class);
	DashBoardDelegate dashBrdDelegate  = new DashBoardDelegate();

	List<RtlCalenderDefinition> listRtlCal = new ArrayList<RtlCalenderDefinition>();
	String enteredYear;
	/**
	 * This method will retrieve the retail calendar info for the given year.
	 * @return
	 */
	public String viewRetailCalendar() {
		final String methodName = "viewRetailCalendar";
		LOGGER.enter(CLASSNAME, methodName);
		String actionForward = "viewRtlCalpopup";
		Integer year = Calendar.getInstance().get(Calendar.YEAR);
		try {
			if (enteredYear != null && !enteredYear.isEmpty()) {
				year = Integer.parseInt(enteredYear.trim());				
			} else {
				enteredYear = year.toString();
			}
			
			if(year <= 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			listRtlCal = dashBrdDelegate.getRetailCalender(year);
			LOGGER.info("Retail Calendar data for the year : " + year);
		} catch (PersistenceException pe) {
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
			pe.printStackTrace();
		} catch(Exception e){
			LOGGER.error("Exception occured");
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
			e.printStackTrace();
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}
	
	public List<RtlCalenderDefinition> getListRtlCal() {
		return listRtlCal;
	}
	public void setListRtlCal(List<RtlCalenderDefinition> listRtlCal) {
		this.listRtlCal = listRtlCal;
	}
	public String getEnteredYear() {
		return enteredYear;
	}
	public void setEnteredYear(String enteredYear) {
		this.enteredYear = enteredYear;
	}
}