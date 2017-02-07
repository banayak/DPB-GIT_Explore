package com.mbusa.dpb.common.dao;

import java.util.List;

import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;


public interface IDashBoardDAO {
	public List<DPBProcessLogBean> getLastTwoDaysProcesses();
	
	/**
	 * added to view processes datewise - Amit 
	 * @param date
	 * @return
	 */
	public List<DPBProcessLogBean> getProcessesForDate(String date);
	
	/**
	 * This method will retrieve the retail calendar info for the given year.
	 */
	public List<RtlCalenderDefinition> getRetailCalender(int year);

}