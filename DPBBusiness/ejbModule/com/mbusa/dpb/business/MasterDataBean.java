package com.mbusa.dpb.business;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.mbusa.dpb.business.view.MasterDataBeanLocal;
import com.mbusa.dpb.common.bo.IMasterDataLookUpManager;
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
import com.mbusa.dpb.common.factories.BOFactory;
import com.mbusa.dpb.common.helper.MenuNode;
import com.mbusa.dpb.common.logger.DPBLog;

/**
 * Session Bean implementation class MasterDataBean
 */
@Stateless
@Local(MasterDataBeanLocal.class)
public class MasterDataBean implements MasterDataBeanLocal {

	private IMasterDataLookUpManager masterInfoManager = (IMasterDataLookUpManager) BOFactory
			.getInstance().getImplementation(IMasterDataLookUpManager.class);
	private static DPBLog LOGGER = DPBLog.getInstance(MasterDataBean.class);
	static final private String CLASSNAME = MasterDataBean.class.getName();

	public MasterDataBean() {
		// TODO Auto-generated constructor stub
	}

	public List<MenuNode> getMenuItems(String uRole) {
		final String methodName = "getMenuItems";
		LOGGER.enter(CLASSNAME, methodName);
		List<MenuNode> mList = masterInfoManager.getMenuItems(uRole);
		LOGGER.exit(CLASSNAME, methodName);
		return mList;
	}

	public List<Kpi> getKPIList() {
		final String methodName = "getKPIList";
		LOGGER.enter(CLASSNAME, methodName);
		List<Kpi> mList = masterInfoManager.getKPIList();
		LOGGER.exit(CLASSNAME, methodName);
		return mList;

	}
	
	public List<Kpi> getKPIWithOldList() {
		final String methodName = "getKPIList";
		LOGGER.enter(CLASSNAME, methodName);
		List<Kpi> mList = masterInfoManager.getKPIWithOldList();
		LOGGER.exit(CLASSNAME, methodName);
		return mList;

	}

	public List<VehicleType> getVehicleList() {
		final String methodName = "getVehicleList";
		LOGGER.enter(CLASSNAME, methodName);
		List<VehicleType> mList = null;
		mList = masterInfoManager.getVehicleList();
		LOGGER.exit(CLASSNAME, methodName);
		return mList;
	}

	public List<CondtionCode> getConditionCodes() {
		final String methodName = "getCondtionCode";
		LOGGER.enter(CLASSNAME, methodName);
		List<CondtionCode> conditionCdList = masterInfoManager
				.getConditionCodes();
		LOGGER.exit(CLASSNAME, methodName);
		return conditionCdList;
	}

	public List<ConditionType> getConditionTypes() {
		final String methodName = "getCondtionCode";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionType> condTypes = masterInfoManager.getConditionTypes();
		LOGGER.exit(CLASSNAME, methodName);
		return condTypes;
	}

	public List<Region> getRegionCodes() {
		final String methodName = "getCondtionCode";
		LOGGER.enter(CLASSNAME, methodName);
		List<Region> rCodeList = masterInfoManager.getRegionCodes();
		LOGGER.exit(CLASSNAME, methodName);
		return rCodeList;
	}

	public List<VehicleStatus> getVehicleStatusCodes() {
		final String methodName = "getVehicleStatus";
		LOGGER.enter(CLASSNAME, methodName);
		List<VehicleStatus> vehicleStatusList = masterInfoManager
				.getVehicleStatusCodes();
		LOGGER.exit(CLASSNAME, methodName);
		return vehicleStatusList;
	}

	public List<Scheduler> getSchedulerCodes() {
		final String methodName = "getSchedulerList";
		LOGGER.enter(CLASSNAME, methodName);
		List<Scheduler> schedulerCdList = masterInfoManager.getSchedulerCodes();
		LOGGER.exit(CLASSNAME, methodName);
		return schedulerCdList;
	}

	public List<Trigger> getTriggerCodes() {
		final String methodName = "getTriggerList";
		LOGGER.enter(CLASSNAME, methodName);
		List<Trigger> triggerList = masterInfoManager.getTriggerCodes();
		LOGGER.exit(CLASSNAME, methodName);
		return triggerList;
	}

	public List<DefStatus> getDefStatusCodes() {
		final String methodName = "getDefStatus";
		LOGGER.enter(CLASSNAME, methodName);
		List<DefStatus> defStatusList = masterInfoManager.getDefStatusCodes();
		return defStatusList;
	}

	public List<ProcessStatus> getProcessStatusCodes() {
		final String methodName = "getProcessStatus";
		LOGGER.enter(CLASSNAME, methodName);
		List<ProcessStatus> pStatusList = masterInfoManager
				.getProcessStatusCodes();
		return pStatusList;
	}

	public List<ProcessTypes> getProcessTypeCodes() {
		final String methodName = "getProcessTypes";
		LOGGER.enter(CLASSNAME, methodName);
		List<ProcessTypes> processTypesList = masterInfoManager
				.getProcessTypeCodes();
		LOGGER.exit(CLASSNAME, methodName);
		return processTypesList;
	}

	public List<UserRoles> getUserRoles() {
		final String methodName = "getUserRoles";
		LOGGER.enter(CLASSNAME, methodName);
		List<UserRoles> uRoleList = masterInfoManager.getUserRoles();
		LOGGER.exit(CLASSNAME, methodName);
		return uRoleList;
	}

	public List<ConditionDefinition> getConditionList() {
		final String methodName = "getConditionList()";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = masterInfoManager
				.getConditionList();
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}

	public List<ConditionDefinition> getSpecialConditionList() {
		final String methodName = "getSpecialConditionList() ";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = masterInfoManager
				.getSpecialConditionList();
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}

	public List<ConditionDefinition> getVehicleConditionList() {
		final String methodName = "getVehicleConditionList()";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = masterInfoManager
				.getVehicleConditionList();
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}

	public List<ConditionDefinition> getBlockedConditionList() {
		final String methodName = "getBlockedConditionList()";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = masterInfoManager
				.getBlockedConditionList();
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}

	public Date getRetailCalenderEndDate(Date eDate) {
		final String methodName = "getRetailCalenderEndDate()";
		LOGGER.enter(CLASSNAME, methodName);
		Date endDate = masterInfoManager.getRetailCalenderEndDate(eDate);
		LOGGER.exit(CLASSNAME, methodName);
		return endDate;
	}
	
	public List<String> getColumnNames(String tableName) {
		final String methodName = "getColumnNames()";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> columnNames = masterInfoManager.getColumnNames(tableName);
		LOGGER.exit(CLASSNAME, methodName);
		return columnNames;
	}
	public List<CompanyCode> getCoCodes() {
		final String methodName = "getCondtionCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<CompanyCode> coCdList = masterInfoManager.getCoCodes();
		LOGGER.exit(CLASSNAME, methodName);
		return coCdList;
	}
	
	public List<NationalType> getNationalTypes() {
		final String methodName = "getCondtionCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<NationalType> nationalCdList = masterInfoManager.getNationalTypes();
		LOGGER.exit(CLASSNAME, methodName);
		return nationalCdList;
	}
	
	public List<UseCode> getUseCode() {
		final String methodName = "getCondtionCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<UseCode> useCdList = masterInfoManager.getUseCode();
		LOGGER.exit(CLASSNAME, methodName);
		return useCdList;
	}
	public List<RtlCalenderDefinition> getRetailCalender(){
		final String methodName = "getCondtionCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<RtlCalenderDefinition> rtlCalList = masterInfoManager.getRetailCalender();
		LOGGER.exit(CLASSNAME, methodName);
		return rtlCalList;
	}

	@Override
	public Map<String, String> getReportContentList() {
		Map<String, String> rptCtntList = null;
		rptCtntList = masterInfoManager.getReportContentList();
		return rptCtntList;
	}

	@Override
	public Map<String, String> getReportQryList() {
		Map<String, String> rptQryList = null;
		rptQryList = masterInfoManager.getReportQryList();
		return rptQryList;
	}
	@Override
	public Map<String, String> getDealerList() {
		Map<String, String> dlrIds = null;
		dlrIds = masterInfoManager.getDealerList();
		return dlrIds;
	}
	@Override
	public List<KpiFile> getKpiPercentageforYearQtrKpiId(String period, int year){
    	final String methodName = "retrieveKpiFileListForDealer";
		LOGGER.enter(CLASSNAME, methodName);
		List<KpiFile> kpiList = masterInfoManager.getKpiPercentageforYearQtrKpiId(period,year);
		LOGGER.exit(CLASSNAME, methodName);
		return kpiList;
	}
}
