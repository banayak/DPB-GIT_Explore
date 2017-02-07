package com.mbusa.dpb.common.bo;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.common.dao.IMasterDataLookUpDAO;
import com.mbusa.dpb.common.dao.MasterDataLookUpDAOImpl;
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
import com.mbusa.dpb.common.helper.MenuNode;
import com.mbusa.dpb.common.logger.DPBLog;

public class MasterDataLookUpManagerImpl implements IMasterDataLookUpManager {

	IMasterDataLookUpDAO masterInfoDAO = new MasterDataLookUpDAOImpl();
	private static DPBLog LOGGER = DPBLog
			.getInstance(MasterDataLookUpManagerImpl.class);
	static final private String CLASSNAME = MasterDataLookUpDAOImpl.class
			.getName();

	public List<MenuNode> getMenuItems(String uRole) {
		final String methodName = "getMenuItems";
		LOGGER.enter(CLASSNAME, methodName);
		List<MenuNode> menu = null;
		menu = masterInfoDAO.getMenuItems(uRole);
		LOGGER.exit(CLASSNAME, methodName);
		return menu;
	}

	public List<Kpi> getKPIList() {
		final String methodName = "getKPIList";
		LOGGER.enter(CLASSNAME, methodName);
		List<Kpi> kpiList = null;
		kpiList = masterInfoDAO.getKPIList();
		LOGGER.exit(CLASSNAME, methodName);
		return kpiList;
	}
	
	public List<Kpi> getKPIWithOldList() {
		final String methodName = "getKPIList";
		LOGGER.enter(CLASSNAME, methodName);
		List<Kpi> kpiList = null;
		kpiList = masterInfoDAO.getKPIWithOldList();
		LOGGER.exit(CLASSNAME, methodName);
		return kpiList;
	}

	public List<VehicleType> getVehicleList() {
		final String methodName = "getVehicleCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<VehicleType> vList = null;
		vList = masterInfoDAO.getVehicleList();
		LOGGER.exit(CLASSNAME, methodName);
		return vList;
	}

	public List<CondtionCode> getConditionCodes() {
		final String methodName = "getCondtionCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<CondtionCode> conditionCdList = masterInfoDAO.getConditionCodes();
		LOGGER.exit(CLASSNAME, methodName);
		return conditionCdList;
	}

	public List<ConditionType> getConditionTypes() {
		final String methodName = "getConditionTypes";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionType> cTypeList = masterInfoDAO.getConditionTypes();
		LOGGER.exit(CLASSNAME, methodName);
		return cTypeList;
	}

	public List<Region> getRegionCodes() {
		final String methodName = "getRegionCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<Region> rCodeList = masterInfoDAO.getRegionCodes();
		LOGGER.exit(CLASSNAME, methodName);
		return rCodeList;
	}

	public List<VehicleStatus> getVehicleStatusCodes() {
		final String methodName = "getVehicleStatusCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<VehicleStatus> vehicleStatusList = masterInfoDAO
				.getVehicleStatusCodes();
		LOGGER.exit(CLASSNAME, methodName);
		return vehicleStatusList;
	}

	public List<Scheduler> getSchedulerCodes() {
		final String methodName = "getSchedulerCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<Scheduler> schedulerCdList = masterInfoDAO.getSchedulerCodes();
		LOGGER.exit(CLASSNAME, methodName);
		return schedulerCdList;
	}

	public List<Trigger> getTriggerCodes() {
		final String methodName = "getTriggerCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<Trigger> triggerList = masterInfoDAO.getTriggerCodes();
		LOGGER.exit(CLASSNAME, methodName);
		return triggerList;
	}

	public List<DefStatus> getDefStatusCodes() {
		final String methodName = "getDefStatusCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<DefStatus> defStatusList = masterInfoDAO.getDefStatusCodes();
		return defStatusList;
	}

	public List<ProcessStatus> getProcessStatusCodes() {
		final String methodName = "getProcessStatusCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<ProcessStatus> pStatusList = masterInfoDAO.getProcessStatusCodes();
		return pStatusList;
	}

	public List<ProcessTypes> getProcessTypeCodes() {
		final String methodName = "getProcessTypeCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<ProcessTypes> processTypesList = masterInfoDAO
				.getProcessTypeCodes();
		LOGGER.exit(CLASSNAME, methodName);
		return processTypesList;
	}

	public List<UserRoles> getUserRoles() {
		final String methodName = "getUserRoles";
		LOGGER.enter(CLASSNAME, methodName);
		List<UserRoles> uRoleList = masterInfoDAO.getUserRoles();
		LOGGER.exit(CLASSNAME, methodName);
		return uRoleList;
	}

	public List<ConditionDefinition> getConditionList() {
		final String methodName = "getConditionList()";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = masterInfoDAO.getConditionList();
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}

	public List<ConditionDefinition> getSpecialConditionList() {
		final String methodName = "getSpecialConditionList() ";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> sCondList = masterInfoDAO
				.getSpecialConditionList();
		LOGGER.exit(CLASSNAME, methodName);
		return sCondList;
	}

	public List<ConditionDefinition> getVehicleConditionList() {
		final String methodName = "getVehicleConditionList()";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> vCondList = masterInfoDAO.getVehicleConditionList();
		LOGGER.exit(CLASSNAME, methodName);
		return vCondList;
	}

	public List<ConditionDefinition> getBlockedConditionList() {
		final String methodName = "getBlockedConditionList()";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = masterInfoDAO
				.getBlockedConditionList();
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}

	public Date getRetailCalenderEndDate(Date eDate) {
		final String methodName = "getRetailCalenderEndDate()";
		LOGGER.enter(CLASSNAME, methodName);
		Date endDate = masterInfoDAO.getRetailCalenderEndDate(eDate);
		LOGGER.exit(CLASSNAME, methodName);
		return endDate;
	}

	public List<String> getColumnNames(String tableName) {
		final String methodName = "getColumnNames()";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> columnNames = masterInfoDAO.getColumnNames(tableName);
		LOGGER.exit(CLASSNAME, methodName);
		return columnNames;
	}
	public List<CompanyCode> getCoCodes() {
		final String methodName = "getCondtionCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<CompanyCode> coCdList = masterInfoDAO.getCoCodes();
		LOGGER.exit(CLASSNAME, methodName);
		return coCdList;
	}
	
	public List<NationalType> getNationalTypes() {
		final String methodName = "getCondtionCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<NationalType> nationalCdList = masterInfoDAO.getNationalTypes();
		LOGGER.exit(CLASSNAME, methodName);
		return nationalCdList;
	}
	
	public List<UseCode> getUseCode() {
		final String methodName = "getCondtionCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<UseCode> useCdList = masterInfoDAO.getUseCode();
		LOGGER.exit(CLASSNAME, methodName);
		return useCdList;
	}
	public List<RtlCalenderDefinition> getRetailCalender(){
		final String methodName = "getCondtionCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<RtlCalenderDefinition> rtlCalList = masterInfoDAO.getRetailCalender();
		LOGGER.exit(CLASSNAME, methodName);
		return rtlCalList;
	}

	@Override
	public Map<String, String> getReportContentList() {
		Map<String, String> rptCtntList = null;
		rptCtntList = masterInfoDAO.getReportContentList();
		return rptCtntList;
	}

	@Override
	public Map<String, String> getReportQryList() {
		Map<String, String> rptQryList = null;
		rptQryList = masterInfoDAO.getReportQryList();
		return rptQryList;
	}
	@Override
	public Map<String, String> getDealerList() {
		Map<String, String> dlrIds = null;
		dlrIds = masterInfoDAO.getDealerList();
		return dlrIds;
	}

	public List<KpiFile> getKpiPercentageforYearQtrKpiId(String period, int year){
		final String methodName = "retrieveKpiFileListForDealer";
		LOGGER.enter(CLASSNAME, methodName);
		List<KpiFile> kpiList = masterInfoDAO.getKpiPercentageforYearQtrKpiId(period,year);
		LOGGER.exit(CLASSNAME, methodName);
		return kpiList;
	}
}
