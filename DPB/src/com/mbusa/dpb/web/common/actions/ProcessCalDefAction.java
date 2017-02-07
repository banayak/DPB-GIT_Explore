/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ProcessCalDefAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle request related to process calender.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 01, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.common.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.common.domain.ProcessCalDefBean;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;

public class ProcessCalDefAction extends DPBAction {

	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = ProcessCalDefAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(ProcessCalDefAction.class);
	
	private BusinessDelegate busDelegate = new BusinessDelegate(); 
	private ProcessCalDefBean processCalDefBean;
	private List<Integer> weeksInCurrMonth; 
	private List<String> weekDays;
	private List<String> days;
	private int counter = 0;
	private String currentDay;
	private String monthYr;
	private int processCount;
	List<ProcessCalDefBean> processCalView;
	private ProcessCalDefBean processCalDef; 
	private Map<String, List<ProcessCalDefBean>> processPerDay; 
	private String actionForward = "errorPage";
	
	public String getCurrentMonthProcess(){
		final String methodName = "getCurrentMonthProcess";
		LOGGER.enter(CLASSNAME, methodName);
		try{
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			actionForward = "processCalView";
			processCalView = null;
			processCalDefBean= new ProcessCalDefBean();
			processCalView = busDelegate.getCurrentMonthProcess();	
			initializeScreenRenderingLists();		
		}catch(BusinessException be){
			LOGGER.info("BusinessException occured");
		}catch (TechnicalException te) {
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		catch (PersistenceException pe) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, pe);
		}
		catch (Exception e) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, e);
		}
		LOGGER.exit(CLASSNAME, methodName);	
		return actionForward ;
	}

	private void initializeScreenRenderingLists() {
		weeksInCurrMonth = new ArrayList<Integer>(5);
		Calendar calendar = Calendar.getInstance();
		int maxWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
		for(int i = 1; i <= maxWeeks; i++ ){
			weeksInCurrMonth.add(i);
		}

		monthYr = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + " " + calendar.get(Calendar.YEAR) ;

		weekDays = new ArrayList<String>(7);
		weekDays.add("Sun");
		weekDays.add("Mon");
		weekDays.add("Tue");
		weekDays.add("Wed");
		weekDays.add("Thu");
		weekDays.add("Fri");
		weekDays.add("Sat");

		days = new ArrayList<String>(35);
		calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		GregorianCalendar cal = new GregorianCalendar (calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1); 
		int startDay = cal.get(Calendar.DAY_OF_WEEK);
		for(int i=0; i<startDay-1; i++){
			days.add("");
		}
		int maxEndDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		for(int j=1 ; j<=maxEndDay; j++){
			days.add(String.valueOf(j));
		}

		for(int i=maxEndDay; i<35; i++){
			days.add("");
		}
		
		List<ProcessCalDefBean> processesLst;
		processPerDay = new HashMap<String, List<ProcessCalDefBean>>(35);
		for(int j = 0; j < days.size();j++){
			processesLst = new ArrayList<ProcessCalDefBean>();
			for(int i = 0; i< processCalView.size(); i++ ){
				if(processCalView.get(i).equals(days.get(j))){
					processesLst.add(processCalView.get(i));
					//processPerDay.put(days.get(j), processCalView.get(i));
				}
			}
			processPerDay.put(days.get(j), processesLst);
		}
		
	}

	public ProcessCalDefBean getProcessCalDefBean() {
		return processCalDefBean;
	}

	public void setProcessCalDefBean(ProcessCalDefBean processCalDefBean) {
		this.processCalDefBean = processCalDefBean;
	}

	/**
	 * @return the weeksInCurrMonth
	 */
	public List<Integer> getWeeksInCurrMonth() {

		return weeksInCurrMonth;
	}

	/**
	 * @return the weekDays
	 */
	public List<String> getWeekDays() {

		return weekDays;
	}

	public List<ProcessCalDefBean> getProcessCalView(){
		return processCalView;
	}

	public List<String> getDays(){
		return days;
	}

	public String retrieveDay(){
		String i = days.get(counter);
		currentDay = i;
		return i;
	}

	public List<ProcessCalDefBean> retrieveProcessPerDay(){
		counter++;
		return processPerDay.get(currentDay);
	}

	/**
	 * @return the monthYr
	 */
	public String getMonthYr() {
		return monthYr;
	}

	/**
	 * @return the processCalDef
	 */
	public ProcessCalDefBean getProcessCalDef() {
		return processCalDef;
	}

	/**
	 * @return the processCount
	 */
	public int getProcessCount() {
		return processCount;
	}

}