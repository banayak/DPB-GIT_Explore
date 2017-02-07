package com.mbusa.dpb.common.bo;

import java.util.List;

import com.mbusa.dpb.common.dao.DashBoardDAOImpl;
import com.mbusa.dpb.common.dao.IDashBoardDAO;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;

public class DashBoardManagerImpl implements IDashBoardManager {
	
	IDashBoardDAO dashBrdDAO = new DashBoardDAOImpl();

	public List<DPBProcessLogBean> getLastTwoDaysProcesses(){
		List<DPBProcessLogBean> processlog = null;
		try {
			//EJB call 
			//call 
			processlog = dashBrdDAO.getLastTwoDaysProcesses();
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
			processlog = dashBrdDAO.getProcessesForDate(date);
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
		List<RtlCalenderDefinition> listRtlCal = dashBrdDAO.getRetailCalender(year);
		return listRtlCal;
	}

}
