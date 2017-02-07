package com.mbusa.dpb.business.view;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.common.domain.CompanyCode;
import com.mbusa.dpb.common.domain.ConditionDefinition;
import com.mbusa.dpb.common.domain.ConditionType;
import com.mbusa.dpb.common.domain.CondtionCode;
import com.mbusa.dpb.common.domain.DefStatus;
import com.mbusa.dpb.common.domain.Kpi;
import com.mbusa.dpb.common.domain.KpiFile;
import com.mbusa.dpb.common.domain.NationalType;
import com.mbusa.dpb.common.domain.ProcessStatus;
import com.mbusa.dpb.common.domain.ProcessTypes;
import com.mbusa.dpb.common.domain.Region;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.domain.Scheduler;
import com.mbusa.dpb.common.domain.Trigger;
import com.mbusa.dpb.common.domain.UseCode;
import com.mbusa.dpb.common.domain.UserRoles;
import com.mbusa.dpb.common.domain.VehicleStatus;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.helper.MenuNode;

public interface MasterDataBeanLocal {
	
    public List<MenuNode> getMenuItems(String uRole);
    public List<Kpi> getKPIList();
    public List<Kpi> getKPIWithOldList();
    public List<VehicleType> getVehicleList();

	public List<CondtionCode> getConditionCodes();
	public List<ConditionType> getConditionTypes();
	public List<Region> getRegionCodes();
	public List<VehicleStatus> getVehicleStatusCodes();
	
	public List<Scheduler> getSchedulerCodes();
	public List<Trigger> getTriggerCodes();
	public List<DefStatus> getDefStatusCodes(); 
	public List<ProcessStatus> getProcessStatusCodes() ;
	public List<ProcessTypes> getProcessTypeCodes();
	public List<UserRoles> getUserRoles();
	public List<CompanyCode> getCoCodes();	
	public List<NationalType> getNationalTypes();	
	public List<UseCode> getUseCode();
	public List<ConditionDefinition> getConditionList();
	public List<ConditionDefinition> getSpecialConditionList();
	public List<ConditionDefinition> getVehicleConditionList();
	public List<ConditionDefinition> getBlockedConditionList();
	public Date getRetailCalenderEndDate(Date eDate);
	public List<RtlCalenderDefinition> getRetailCalender();
	public List<String> getColumnNames(String tableName);
	public Map<String, String> getReportContentList();
	public Map<String, String> getReportQryList();
	public Map<String, String> getDealerList();
	public List<KpiFile> getKpiPercentageforYearQtrKpiId(String period, int year);
}
