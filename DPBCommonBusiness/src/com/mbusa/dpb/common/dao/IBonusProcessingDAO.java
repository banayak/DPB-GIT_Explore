/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ReportDefinitionBean.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all request related to Bonus Processing.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 10, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.dao;

import java.sql.Date;
import java.util.List;

import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.ProcessBonus;
import com.mbusa.dpb.common.domain.ProcessLdrBonus;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;

public interface IBonusProcessingDAO {	
	
	public void getDailyRetailData();
	public RtlCalenderDefinition getRetailCalender(Date cDate);	
	public List<ProcessBonus> procBonusProc();
	public List<ProcessLdrBonus> processLdrBonus();
	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId);
}
