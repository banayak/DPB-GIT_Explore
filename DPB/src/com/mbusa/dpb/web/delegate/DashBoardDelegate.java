package com.mbusa.dpb.web.delegate;

import java.util.List;

import com.mbusa.dpb.common.bo.DashBoardManagerImpl;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;

public class DashBoardDelegate {
	
	DashBoardManagerImpl dashBrdMgr = new DashBoardManagerImpl();
	public List<DPBProcessLogBean> getLastTwoDaysProcesses(){
		List<DPBProcessLogBean> processlog = null;
		try {
			//EJB call 
			//call 
			processlog = dashBrdMgr.getLastTwoDaysProcesses();
			//BO--> DAO
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return processlog; 
	}
	
	/**
	 * added to view processes datewise - Amit
	 * @param date
	 * @return
	 */
	public List<DPBProcessLogBean> getProcessesForDate(String date){
		List<DPBProcessLogBean> processlog = null;
		try {
			//EJB call 
			//call 
			processlog = dashBrdMgr.getProcessesForDate(date);
			//BO--> DAO
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return processlog; 
	}
	
	/**
	 * This method will retrieve the retail calendar info for the given year.
	 */
	public List<RtlCalenderDefinition> getRetailCalender(int year) {
		List<RtlCalenderDefinition> listRtlCal = null;
		try {
			listRtlCal = dashBrdMgr.getRetailCalender(year);
		} catch (Exception e) {
			// TODO Auto-generated catch block
						e.printStackTrace();
		}
		return listRtlCal;
	}
}
