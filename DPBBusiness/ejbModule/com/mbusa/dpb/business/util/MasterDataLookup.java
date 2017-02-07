package com.mbusa.dpb.business.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.common.constant.IConstants;
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
import com.mbusa.dpb.common.exceptions.ServiceException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.helper.MenuNode;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.CacheManager;
import com.mbusa.dpb.common.util.DPBCommonHelper;

public class MasterDataLookup {

	static final private String CLASSNAME = MasterDataLookup.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(MasterDataLookup.class);

	static final PropertyManager PROP_MNGR = PropertyManager
			.getPropertyManager();

	private LocalServiceFactory local = new LocalServiceFactory();

	public static final String CACHE_NAME = MasterDataLookup.class.getName();
	private static final MasterDataLookup SINGLETON = new MasterDataLookup();
	public static final MasterDataLookup INSTANCE = SINGLETON;

	private static final String MENU_LIST_CKEY = "MENU_LIST";

	protected static final Object MENU_LIST_CACHE_LOCK = new Object();

	private static final String KPI_LIST_CKEY = "KPI_LIST";
	protected static final Object KPI_LIST_CACHE_LOCK = new Object();
	
	private static final String KPI_OLD_LIST_CKEY = "KPI_OLD_LIST";
	protected static final Object KPI_OLD_LIST_CACHE_LOCK = new Object();

	private static final String VEHICLE_LIST_CKEY = "VEHICLE_LIST";
	protected static final Object VEHICLE_LIST_CACHE_LOCK = new Object();

	private static final String CND_CDE_LIST_CKEY = "CND_CDE_LIST";
	protected static final Object CND_CDE_LIST_CACHE_LOCK = new Object();

	private static final String CND_CDE_TYP_LIST_CKEY = "CND_CDE_TYP_LIST";
	protected static final Object CND_CDE_TYP_LIST_CACHE_LOCK = new Object();

	private static final String REGION_LIST_CKEY = "REGION_LIST";
	protected static final Object REGION_LIST_CACHE_LOCK = new Object();

	private static final String VEHICLE_STATUS_LIST_CKEY = "VEHICLE_STATUS__LIST";
	protected static final Object VEHICLE_STATUS_LIST_CACHE_LOCK = new Object();

	private static final String SCHEDULAR_LIST_CKEY = "SCHEDULAR_LIST";
	protected static final Object SCHEDULAR_LIST_CACHE_LOCK = new Object();

	private static final String TRIGGER_LIST_CKEY = "TRIGGER_LIST";
	protected static final Object TRIGGER_LIST_CACHE_LOCK = new Object();

	private static final String DEF_STATUS_LIST_CKEY = "DEF_STATUS__LIST";
	protected static final Object DEF_STATUS__LIST_CACHE_LOCK = new Object();

	private static final String PROC_STS_LIST_CKEY = "PROC_STS_LIST";
	protected static final Object PROC_STS_LIST_CACHE_LOCK = new Object();

	private static final String PROC_TYP_LIST_CKEY = "PROC_TYP_LIST";
	protected static final Object PROC_TYP_LIST_CACHE_LOCK = new Object();

	private static final String CND_TYP_LIST_CKEY = "CND_TYP_LIST";
	protected static final Object CND_TYP_LIST_CACHE_LOCK = new Object();

	private static final String USR_ROLE_LIST_CKEY = "USR_ROLE_LIST";
	protected static final Object USR_ROLE_LIST_CACHE_LOCK = new Object();

	private static final String SPECIAL_CONDITION_LIST_CKEY = "SPECIAL_CONDITION_LIST";
	protected static final Object SPECIAL_CONDITION_LIST_CACHE_LOCK = new Object();

	private static final String BLOCKED_CONDITION_LIST_CKEY = "BLOCKED_CONDITION_LIST";
	protected static final Object BLOCKED_CONDITION_LIST_CACHE_LOCK = new Object();

	private static final String RETAIL_CALENDER_END_DATE_CKEY = "RETAIL_CALENDER_END_DATE";
	protected static final Object RETAIL_CALENDER_END_DATE_CACHE_LOCK = new Object();

	private static final String VEHICLE_CONDITION_LIST_CKEY = "VEHICLE_CONDITION_LIST";
	protected static final Object VEHICLE_CONDITION_LIST_CACHE_LOCK = new Object();

	private static final String CONDITION_LIST_CKEY = "CONDITION_LIST";
	protected static final Object CONDITION_LIST_CACHE_LOCK = new Object();
	
	private static final String KPI_COULMN_NAMES_KEY = "KPI_COULMN_NAMES_KEY";
	protected static final Object KPI_COULMN_NAMES_LOCK = new Object();
	
	private static final String VISTA_COULMN_NAMES_KEY = "VISTA_COULMN_NAMES_KEY";
	protected static final Object VISTA_COULMN_NAMES_LOCK = new Object();
	
	private static final String DEALER_COULMN_NAMES_KEY = "DEALER_COULMN_NAMES_KEY";
	protected static final Object DEALER_COULMN_NAMES_LOCK = new Object();
	
	private static final String LDR_BONUS_COULMN_NAMES_KEY = "LDR_BONUS_COULMN_NAMES_KEY";
	protected static final Object LDR_BONUS_COULMN_NAMES_LOCK = new Object();
	
	private static final String ACCURAL_COULMN_NAMES_KEY = "ACCURAL_COULMN_NAMES_KEY";
	protected static final Object ACCURAL_COULMN_NAMES_LOCK = new Object();
	
	private static final String FILE_MAP_COULMN_NAMES_KEY = "FILE_MAP_COULMN_NAMES_KEY";
	protected static final Object FILE_MAP_COULMN_NAMES_LOCK = new Object();
	
	
	private static final String CO_CND_LIST_CKEY = "CO_CND_LIST";
	protected static final Object CO_CND_CACHE_LOCK = new Object();

	private static final String USR_CODE_LIST_CKEY = "USR_CODE_LIST";
	protected static final Object USR_CODE_LIST_CACHE_LOCK = new Object();
	
	private static final String NATIONAL_TYP_CD_LIST_CKEY = "NATIONAL_TYP_CD_LIST";
	protected static final Object NATIONAL_TYP_CD_CACHE_LOCK = new Object();
	
	private static final String REPORT_CTNT_LIST_CKEY = "REPORT_CONTENT";
	protected static final Object REPORT_CTNT_CACHE_LOCK = new Object();
	
	private static final String REPORT_QRY_LIST_CKEY = "REPORT_QRY";
	protected static final Object REPORT_QRY_CACHE_LOCK = new Object();
	
	private static final String RTL_CAL_LIST_CKEY = "RTL_CAL_LIST";
	protected static final Object RTL_CAL_CACHE_LOCK = new Object();

/*	private static final String USR_ROLE_LIST_CKEY = "USR_ROLE_LIST";
	protected static final Object USR_ROLE_LIST_CACHE_LOCK = new Object();
*/
	private static final String DPB_DLR_CKEY = "DPB_DLR";
	protected static final Object DPB_DLR_CACHE_LOCK = new Object();
	
	protected static CacheManager CACHE_MNGR = new CacheManager();

	/**
	 * Singleton
	 * 
	 * 
	 * @return MasterInfoLookup
	 */
	public static final MasterDataLookup getInstance() {
		return SINGLETON;
	}

	/**
	 * This method fetches all menu items based on role
	 * 
	 * 
	 * @return List
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<MenuNode> getMenuItems(String uRole) throws ServiceException {
		final String methodName = "getMenuItems";
		LOGGER.enter(CLASSNAME, methodName);
		List<MenuNode> menuList = null;
		try {
			synchronized (MENU_LIST_CACHE_LOCK) {
				menuList = (List<MenuNode>) CACHE_MNGR
						.getFromCache(MasterDataLookup.MENU_LIST_CKEY);
				if (menuList == null) {
					menuList = local.getMasterDataService().getMenuItems(uRole);
					CACHE_MNGR.addtoCache(MasterDataLookup.MENU_LIST_CKEY,
							menuList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return menuList;
	}

	@SuppressWarnings("unchecked")
	/**
	 * This method will return list of active kpi based on inactive date 
	 * along with kpi id, desc, %amt and inactive date.
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<Kpi> getKPIList() throws ServiceException {
		final String methodName = "getMenuItems";
		LOGGER.enter(CLASSNAME, methodName);
		List<Kpi> kpiList = null;
		try {
			synchronized (KPI_LIST_CACHE_LOCK) {
				kpiList = (List<Kpi>) CACHE_MNGR
						.getFromCache(MasterDataLookup.KPI_LIST_CKEY);
				if (kpiList == null) {
					kpiList = local.getMasterDataService().getKPIList();
					CACHE_MNGR.addtoCache(MasterDataLookup.KPI_LIST_CKEY,
							kpiList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return kpiList;
	}

	@SuppressWarnings("unchecked")
	public List<VehicleType> getVehicleList() throws ServiceException {
		final String methodName = "getMenuItems";
		LOGGER.enter(CLASSNAME, methodName);
		List<VehicleType> vList = null;
		try {
			synchronized (VEHICLE_LIST_CACHE_LOCK) {
				vList = (List<VehicleType>) CACHE_MNGR
						.getFromCache(MasterDataLookup.VEHICLE_LIST_CKEY);
				if (vList == null) {
					vList = DPBCommonHelper.getStaticVehicleList();
					CACHE_MNGR.addtoCache(MasterDataLookup.VEHICLE_LIST_CKEY,
							vList);
				}				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return vList;
	}

	@SuppressWarnings("unchecked")
	public List<CondtionCode> getConditionCodes() {
		final String methodName = "getCondtionCode";
		LOGGER.enter(CLASSNAME, methodName);
		List<CondtionCode> conditionCdList = null;
		try {
			synchronized (CND_CDE_LIST_CACHE_LOCK) {
				conditionCdList = (List<CondtionCode>) CACHE_MNGR
						.getFromCache(MasterDataLookup.CND_CDE_LIST_CKEY);
				if (conditionCdList == null) {
					conditionCdList = local.getMasterDataService()
							.getConditionCodes();
					CACHE_MNGR.addtoCache(MasterDataLookup.CND_CDE_LIST_CKEY,
							conditionCdList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return conditionCdList;
	}

	@SuppressWarnings("unchecked")
	public List<ConditionType> getConditionTypes() {
		final String methodName = "getCondtionCode";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionType> condType = null;
		try {
			synchronized (CND_CDE_TYP_LIST_CACHE_LOCK) {
				condType = (List<ConditionType>) CACHE_MNGR
						.getFromCache(MasterDataLookup.CND_CDE_TYP_LIST_CKEY);
				if (condType == null) {
					condType = local.getMasterDataService().getConditionTypes();
					CACHE_MNGR.addtoCache(
							MasterDataLookup.CND_CDE_TYP_LIST_CKEY, condType);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return condType;
	}

	@SuppressWarnings("unchecked")
	public List<Region> getRegionCodes() {
		final String methodName = "getCondtionCode";
		LOGGER.enter(CLASSNAME, methodName);
		List<Region> rCodeList = null;
		try {
			synchronized (REGION_LIST_CACHE_LOCK) {
				rCodeList = (List<Region>) CACHE_MNGR
						.getFromCache(MasterDataLookup.REGION_LIST_CKEY);
				if (rCodeList == null) {
					rCodeList = local.getMasterDataService().getRegionCodes();
					CACHE_MNGR.addtoCache(MasterDataLookup.REGION_LIST_CKEY,
							rCodeList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return rCodeList;
	}

	@SuppressWarnings("unchecked")
	public List<VehicleStatus> getVehicleStatusCodes() {
		final String methodName = "getVehicleStatus";
		LOGGER.enter(CLASSNAME, methodName);
		List<VehicleStatus> vehicleStatusList = null;
		try {
			synchronized (VEHICLE_STATUS_LIST_CACHE_LOCK) {
				vehicleStatusList = (List<VehicleStatus>) CACHE_MNGR
						.getFromCache(MasterDataLookup.VEHICLE_STATUS_LIST_CKEY);
				if (vehicleStatusList == null) {
					vehicleStatusList = local.getMasterDataService()
							.getVehicleStatusCodes();
					CACHE_MNGR.addtoCache(
							MasterDataLookup.VEHICLE_STATUS_LIST_CKEY,
							vehicleStatusList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}

		LOGGER.exit(CLASSNAME, methodName);
		return vehicleStatusList;
	}

	@SuppressWarnings("unchecked")
	public List<Scheduler> getSchedulerCodes() {
		final String methodName = "getSchedulerCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<Scheduler> schedulerCdList = null;
		try {
			synchronized (SCHEDULAR_LIST_CACHE_LOCK) {
				schedulerCdList = (List<Scheduler>) CACHE_MNGR
						.getFromCache(MasterDataLookup.SCHEDULAR_LIST_CKEY);
				if (schedulerCdList == null) {
					schedulerCdList = local.getMasterDataService()
							.getSchedulerCodes();
					CACHE_MNGR.addtoCache(MasterDataLookup.SCHEDULAR_LIST_CKEY,
							schedulerCdList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return schedulerCdList;
	}

	@SuppressWarnings("unchecked")
	public List<Trigger> getTriggerCodes() {
		final String methodName = "getTriggerList";
		LOGGER.enter(CLASSNAME, methodName);
		List<Trigger> triggerList = null;
		try {
			synchronized (TRIGGER_LIST_CACHE_LOCK) {
				triggerList = (List<Trigger>) CACHE_MNGR
						.getFromCache(MasterDataLookup.TRIGGER_LIST_CKEY);
				if (triggerList == null) {
					triggerList = local.getMasterDataService()
							.getTriggerCodes();
					CACHE_MNGR.addtoCache(MasterDataLookup.TRIGGER_LIST_CKEY,
							triggerList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}

		LOGGER.exit(CLASSNAME, methodName);
		return triggerList;
	}

	@SuppressWarnings("unchecked")
	public List<DefStatus> getDefStatusCodes() {
		final String methodName = "getDefStatus";
		LOGGER.enter(CLASSNAME, methodName);
		List<DefStatus> defStatusList = null;
		try {
			synchronized (DEF_STATUS__LIST_CACHE_LOCK) {
				defStatusList = (List<DefStatus>) CACHE_MNGR
						.getFromCache(MasterDataLookup.DEF_STATUS_LIST_CKEY);
				if (defStatusList == null) {
					defStatusList = local.getMasterDataService()
							.getDefStatusCodes();
					CACHE_MNGR.addtoCache(
							MasterDataLookup.DEF_STATUS_LIST_CKEY,
							defStatusList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}

		return defStatusList;
	}

	@SuppressWarnings("unchecked")
	public List<ProcessStatus> getProcessStatus() {
		final String methodName = "getProcessStatus";
		LOGGER.enter(CLASSNAME, methodName);
		List<ProcessStatus> pStatusList = null;
		try {
			synchronized (PROC_STS_LIST_CACHE_LOCK) {
				pStatusList = (List<ProcessStatus>) CACHE_MNGR
						.getFromCache(MasterDataLookup.PROC_STS_LIST_CKEY);
				if (pStatusList == null) {
					pStatusList = local.getMasterDataService()
							.getProcessStatusCodes();
					CACHE_MNGR.addtoCache(MasterDataLookup.PROC_STS_LIST_CKEY,
							pStatusList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		return pStatusList;
	}

	@SuppressWarnings("unchecked")
	public List<ProcessTypes> getProcessTypeCodes() {
		final String methodName = "getProcessTypes";
		LOGGER.enter(CLASSNAME, methodName);
		List<ProcessTypes> processTypesList = null;
		try {
			synchronized (PROC_TYP_LIST_CACHE_LOCK) {
				processTypesList = (List<ProcessTypes>) CACHE_MNGR
						.getFromCache(MasterDataLookup.PROC_TYP_LIST_CKEY);
				if (processTypesList == null) {
					processTypesList = local.getMasterDataService()
							.getProcessTypeCodes();
					CACHE_MNGR.addtoCache(MasterDataLookup.PROC_TYP_LIST_CKEY,
							processTypesList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return processTypesList;
	}
	/**
	 * 
	 * @return
	 */
	public Map<String, String> getProcessTypeAsMap(){
		Map <String, String> pTypeMap = new HashMap <String, String>();
		List<ProcessTypes> prcsType = getProcessTypeCodes();
		for(ProcessTypes prcsTypeCode: prcsType){			
			pTypeMap.put(prcsTypeCode.getProcessTypeCode().trim(), prcsTypeCode.getProcessTypeName().trim());
		}
		return pTypeMap;
	}
	@SuppressWarnings("unchecked")
	public List<UserRoles> getUserRoles() {
		final String methodName = "getUserRoles";
		LOGGER.enter(CLASSNAME, methodName);
		List<UserRoles> uRoleList = null;
		try {
			synchronized (USR_ROLE_LIST_CACHE_LOCK) {
				uRoleList = ((List<UserRoles>) CACHE_MNGR
						.getFromCache(MasterDataLookup.USR_ROLE_LIST_CKEY));
				if (uRoleList == null) {
					uRoleList = local.getMasterDataService().getUserRoles();
					CACHE_MNGR.addtoCache(MasterDataLookup.USR_ROLE_LIST_CKEY,
							uRoleList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return uRoleList;
	}

	@SuppressWarnings("unchecked")
	public List<ConditionDefinition> getConditionList() {
		final String methodName = "getConditionList";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = null;
		try {
			synchronized (CONDITION_LIST_CACHE_LOCK) {
				condList = ((List<ConditionDefinition>) CACHE_MNGR
						.getFromCache(MasterDataLookup.CONDITION_LIST_CKEY));
				if (condList == null) {
					condList = local.getMasterDataService().getConditionList();
					CACHE_MNGR.addtoCache(MasterDataLookup.CONDITION_LIST_CKEY,
							condList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}

	@SuppressWarnings("unchecked")
	public List<ConditionDefinition> getSpecialConditionList() {
		final String methodName = "getSpecialConditionList";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = null;
		try {
			synchronized (SPECIAL_CONDITION_LIST_CACHE_LOCK) {
				condList = ((List<ConditionDefinition>) CACHE_MNGR
						.getFromCache(MasterDataLookup.SPECIAL_CONDITION_LIST_CKEY));
				if (condList == null) {
					condList = local.getMasterDataService()
							.getSpecialConditionList();
					CACHE_MNGR.addtoCache(
							MasterDataLookup.SPECIAL_CONDITION_LIST_CKEY,
							condList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}

	@SuppressWarnings("unchecked")
	public List<ConditionDefinition> getVehicleConditionList() {
		final String methodName = "getVehicleConditionList";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = null;
		try {
			synchronized (VEHICLE_CONDITION_LIST_CACHE_LOCK) {
				condList = ((List<ConditionDefinition>) CACHE_MNGR
						.getFromCache(MasterDataLookup.VEHICLE_CONDITION_LIST_CKEY));
				if (condList == null) {
					condList = local.getMasterDataService()
							.getVehicleConditionList();
					CACHE_MNGR.addtoCache(
							MasterDataLookup.VEHICLE_CONDITION_LIST_CKEY,
							condList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}

	@SuppressWarnings("unchecked")
	public List<ConditionDefinition> getBlockedConditionList() {
		final String methodName = "getBlockedConditionList";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = null;
		try {
			synchronized (BLOCKED_CONDITION_LIST_CACHE_LOCK) {
				condList = ((List<ConditionDefinition>) CACHE_MNGR
						.getFromCache(MasterDataLookup.BLOCKED_CONDITION_LIST_CKEY));
				if (condList == null) {
					condList = local.getMasterDataService()
							.getBlockedConditionList();
					CACHE_MNGR.addtoCache(
							MasterDataLookup.BLOCKED_CONDITION_LIST_CKEY,
							condList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}

	public Date getRetailCalenderEndDate(Date eDate) {
		final String methodName = "getRetailCalenderEndDate";
		LOGGER.enter(CLASSNAME, methodName);
		Date endDate = null;
		try {
			synchronized (RETAIL_CALENDER_END_DATE_CACHE_LOCK) {
				endDate = ((Date) CACHE_MNGR
						.getFromCache(MasterDataLookup.RETAIL_CALENDER_END_DATE_CKEY));
				if (endDate == null) {
					endDate = local.getMasterDataService().getRetailCalenderEndDate(eDate);
					CACHE_MNGR.addtoCache(MasterDataLookup.RETAIL_CALENDER_END_DATE_CKEY,endDate);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return endDate;
	}
	
	public List<String> getColumnNames (String tableName) {
		final String methodName = "getColumnNames";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> columnNames = null;
		if ("DPB_KPI_FIL_EXTRT".equalsIgnoreCase(tableName)) {
			columnNames = getKPIColumnNames();
		} else if ("DPB_UNBLK_VEH".equalsIgnoreCase(tableName)) {
			columnNames = getVistaColumnNames();
			if(!columnNames.contains("FILLER_COLUMN")){
				columnNames.add("FILLER_COLUMN");
			}
		} else if ("DLR_BNS".equalsIgnoreCase(tableName)) {
			columnNames = getDealerColumnNames();
		} else if ("LDRSP_BNS_PGM".equalsIgnoreCase(tableName)) {
			columnNames = getLdrShipBonusColumnNames();
		} else if ("LDRSP_BNS_ACRL".equalsIgnoreCase(tableName)) {
			columnNames = getAccuralColumnNames();
			if(!columnNames.contains("FILLER_COLUMN")){
				columnNames.add("FILLER_COLUMN");
			}
		} else if ("FILE_COL_MAP".equalsIgnoreCase(tableName)) {
			columnNames = getFileMapColumnNames();
		}
		LOGGER.exit(CLASSNAME, methodName);
		return columnNames;
	}

	@SuppressWarnings("unchecked")
	public List<String> getKPIColumnNames() {
		final String methodName = "getKPIColumnNames";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> columnNames = null;
		try {
			synchronized (KPI_COULMN_NAMES_LOCK) {
				columnNames = ((List<String>) CACHE_MNGR
						.getFromCache(MasterDataLookup.KPI_COULMN_NAMES_KEY));
				if (columnNames == null) {
					columnNames = local.getMasterDataService()
							.getColumnNames("DPB_KPI_FIL_EXTRT");
					CACHE_MNGR.addtoCache(
							MasterDataLookup.KPI_COULMN_NAMES_KEY,
							columnNames);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return columnNames;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CompanyCode> getCoCodes() {
		final String methodName = "getCoCodes";
		LOGGER.enter(CLASSNAME, methodName);
		List<CompanyCode> coCdList = null;
		try {
			synchronized (CO_CND_CACHE_LOCK) {
				coCdList = (List<CompanyCode>) CACHE_MNGR
						.getFromCache(MasterDataLookup.CO_CND_LIST_CKEY);
				if (coCdList == null) {
					coCdList = local.getMasterDataService().getCoCodes();
					CACHE_MNGR.addtoCache(MasterDataLookup.CO_CND_LIST_CKEY,
							coCdList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return coCdList;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<NationalType> getNationalTypes() {
		final String methodName = "getNationalTypes";
		LOGGER.enter(CLASSNAME, methodName);
		List<NationalType> nCdList = null;
		try {
			synchronized (NATIONAL_TYP_CD_CACHE_LOCK) {
				nCdList = (List<NationalType>) CACHE_MNGR.getFromCache(MasterDataLookup.NATIONAL_TYP_CD_LIST_CKEY);
				if (nCdList == null) {
					nCdList = local.getMasterDataService().getNationalTypes();
					CACHE_MNGR.addtoCache(MasterDataLookup.NATIONAL_TYP_CD_LIST_CKEY,nCdList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return nCdList;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<UseCode> getUseCode() {
		final String methodName = "getUseCode";
		LOGGER.enter(CLASSNAME, methodName);
		List<UseCode> useCdList = null;
		try {
			synchronized (USR_CODE_LIST_CACHE_LOCK) {
				useCdList = (List<UseCode>) CACHE_MNGR.getFromCache(MasterDataLookup.USR_CODE_LIST_CKEY);
				if (useCdList == null) {
					useCdList = local.getMasterDataService().getUseCode();
					CACHE_MNGR.addtoCache(MasterDataLookup.USR_CODE_LIST_CKEY,useCdList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return useCdList;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<String> getVistaColumnNames() {
		final String methodName = "getVistaColumnNames";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> columnNames = null;
		try {
			synchronized (VISTA_COULMN_NAMES_LOCK) {
				columnNames = ((List<String>) CACHE_MNGR
						.getFromCache(MasterDataLookup.VISTA_COULMN_NAMES_KEY));
				if (columnNames == null) {
					columnNames = local.getMasterDataService()
							.getColumnNames("DPB_UNBLK_VEH");
					CACHE_MNGR.addtoCache(
							MasterDataLookup.VISTA_COULMN_NAMES_KEY,
							columnNames);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return columnNames;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getDealerColumnNames() {
		final String methodName = "getDealerColumnNames";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> columnNames = null;
		try {
			synchronized (DEALER_COULMN_NAMES_LOCK) {
				columnNames = ((List<String>) CACHE_MNGR
						.getFromCache(MasterDataLookup.DEALER_COULMN_NAMES_KEY));
				if (columnNames == null) {
					columnNames = local.getMasterDataService()
							.getColumnNames("DLR_BNS");
					CACHE_MNGR.addtoCache(
							MasterDataLookup.DEALER_COULMN_NAMES_KEY,
							columnNames);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return columnNames;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getLdrShipBonusColumnNames() {
		final String methodName = "getLdrShipBonusColumnNames";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> columnNames = null;
		try {
			synchronized (LDR_BONUS_COULMN_NAMES_LOCK) {
				columnNames = ((List<String>) CACHE_MNGR
						.getFromCache(MasterDataLookup.LDR_BONUS_COULMN_NAMES_KEY));
				if (columnNames == null) {
					columnNames = local.getMasterDataService()
							.getColumnNames("LDRSP_BNS_PGM");
					CACHE_MNGR.addtoCache(
							MasterDataLookup.LDR_BONUS_COULMN_NAMES_KEY,
							columnNames);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return columnNames;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAccuralColumnNames() {
		final String methodName = "getAccuralColumnNames";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> columnNames = null;
		try {
			synchronized (ACCURAL_COULMN_NAMES_LOCK) {
				columnNames = ((List<String>) CACHE_MNGR
						.getFromCache(MasterDataLookup.ACCURAL_COULMN_NAMES_KEY));
				if (columnNames == null) {
					columnNames = local.getMasterDataService()
							.getColumnNames("LDRSP_BNS_ACRL");
					CACHE_MNGR.addtoCache(
							MasterDataLookup.ACCURAL_COULMN_NAMES_KEY,
							columnNames);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return columnNames;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getFileMapColumnNames() {
		final String methodName = "getFileMapColumnNames";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> columnNames = null;
		try {
			synchronized (FILE_MAP_COULMN_NAMES_LOCK) {
				columnNames = ((List<String>) CACHE_MNGR
						.getFromCache(MasterDataLookup.FILE_MAP_COULMN_NAMES_KEY));
				if (columnNames == null) {
					columnNames = local.getMasterDataService()
							.getColumnNames("FILE_COL_MAP");
					CACHE_MNGR.addtoCache(
							MasterDataLookup.FILE_MAP_COULMN_NAMES_KEY,
							columnNames);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return columnNames;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getReportContentList() {
		final String methodName = "getReportContentList";
		LOGGER.enter(CLASSNAME, methodName);
		Map<String, String> rptCtntList = null;
		try {
			synchronized (REPORT_CTNT_CACHE_LOCK) {
				rptCtntList = ((Map<String, String>) CACHE_MNGR
						.getFromCache(MasterDataLookup.REPORT_CTNT_LIST_CKEY));
				if (rptCtntList == null) {
					rptCtntList = local.getMasterDataService()
							.getReportContentList();
					CACHE_MNGR.addtoCache(
							MasterDataLookup.REPORT_CTNT_LIST_CKEY,
							rptCtntList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return rptCtntList;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getReportQryList() {
		final String methodName = "getReportQryList";
		LOGGER.enter(CLASSNAME, methodName);
		Map<String, String> rptQryList = null;
		try {
			synchronized (REPORT_QRY_CACHE_LOCK) {
				rptQryList = ((Map<String, String>) CACHE_MNGR
						.getFromCache(MasterDataLookup.REPORT_QRY_LIST_CKEY));
				if (rptQryList == null) {
					rptQryList = local.getMasterDataService()
							.getReportQryList();
					CACHE_MNGR.addtoCache(
							MasterDataLookup.REPORT_QRY_LIST_CKEY,
							rptQryList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return rptQryList;
	}
	@SuppressWarnings("unchecked")
	public List<RtlCalenderDefinition> getRetailCalender() {
		final String methodName = "getReportQryList";
		LOGGER.enter(CLASSNAME, methodName);
		List<RtlCalenderDefinition> rtlCalList = null;
		try {
			synchronized (RTL_CAL_CACHE_LOCK) {
				rtlCalList = (List<RtlCalenderDefinition>) CACHE_MNGR.getFromCache(MasterDataLookup.RTL_CAL_LIST_CKEY);
				if (rtlCalList == null) {
					rtlCalList = local.getMasterDataService().getRetailCalender();
					CACHE_MNGR.addtoCache(MasterDataLookup.RTL_CAL_LIST_CKEY,rtlCalList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return rtlCalList;
	}
	public RtlCalenderDefinition getRtlCalByDate(java.sql.Date date) {
		final String methodName = "getDayId";
		LOGGER.enter(CLASSNAME, methodName);
		RtlCalenderDefinition rtlCal =  null;
		List<RtlCalenderDefinition> rtlCalList = MasterDataLookup.getInstance().getRetailCalender();
		if (rtlCalList != null) {		
		    for (Iterator<RtlCalenderDefinition> itr = rtlCalList.iterator(); itr.hasNext();) {
			  rtlCal = (RtlCalenderDefinition) itr.next();	
			
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			  try{
				  java.util.Date date1 = (java.util.Date) sdf.parse(rtlCal.getDteCal().toString());
				  java.util.Date cdate = (java.util.Date) sdf.parse(date.toString());
			 
			   	  if (rtlCal != null && rtlCal.getDteCal() != null 
	        			  		&& cdate.compareTo(date1) == 0){                		
	        		  break;
	        	  }else{
	        		  rtlCal =  null;
	        	  }        	  
			  }catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("Exception occured while comparing dates in master data lookup:"+e.getMessage());
			}
		    }   
		}
		LOGGER.exit(CLASSNAME, methodName);
		return rtlCal;
	}
	
	public RtlCalenderDefinition getRtlCalByQtrAndYear(String quarter, String year){
		final String methodName = "getDayId";
		LOGGER.enter(CLASSNAME, methodName);
		RtlCalenderDefinition rtlCal =  null;
		List<RtlCalenderDefinition> rtlCalList = MasterDataLookup.getInstance().getRetailCalender();
		if (rtlCalList != null) {
		    for (Iterator<RtlCalenderDefinition> itr = rtlCalList.iterator(); itr.hasNext();) {
			  rtlCal = (RtlCalenderDefinition) itr.next();
			  if (rtlCal != null && (rtlCal.getRetlQtrNum().equalsIgnoreCase(quarter)
                			  				&& rtlCal.getRetlYearNum().equalsIgnoreCase(year))) {                		
                		break;
                	  }
		    }
		}
		LOGGER.exit(CLASSNAME, methodName);
		return rtlCal;
	}
	public void refreshCacheValue(String cacheName) {
		final String methodName = "refreshCacheValue";
		LOGGER.enter(CLASSNAME, methodName);		
		CACHE_MNGR.removeFromCache(cacheName);
		LOGGER.exit(CLASSNAME, methodName);
	}
	
	public Map getProcessTypeString(){
		Map <String, String> pTypeMap = new HashMap <String, String>();
		List<ProcessTypes> prcsType = getProcessTypeCodes();
		for(ProcessTypes prcsTypeCode: prcsType){			
			pTypeMap.put(prcsTypeCode.getProcessTypeCode().trim(), prcsTypeCode.getProcessTypeName().trim());
		}
		return pTypeMap;
	}
	public Map getStatusString(){
		Map <String, String> pStatusMap = new HashMap <String, String>();
		List<ProcessStatus> prcsStatus = getProcessStatus();
		for(ProcessStatus prcsTypeCode: prcsStatus){			
			pStatusMap.put(prcsTypeCode.getProcessStatusCd().trim(), prcsTypeCode.getProcessStatusName().trim());
		}
		return pStatusMap;
	}
	@SuppressWarnings("unchecked")
	public Map<String, String> getDealerList() {
		final String methodName = "getDealerList";
		LOGGER.enter(CLASSNAME, methodName);
		Map<String, String> dlrIds = null;
		try {
			synchronized (DPB_DLR_CACHE_LOCK) {
				dlrIds = ((Map<String, String>) CACHE_MNGR.getFromCache(MasterDataLookup.DPB_DLR_CKEY));
				if (dlrIds == null) {
					dlrIds = local.getMasterDataService().getDealerList();
					CACHE_MNGR.addtoCache(
							MasterDataLookup.DPB_DLR_CKEY,dlrIds);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return dlrIds;
	}
	
	@SuppressWarnings("unchecked")
	public List<KpiFile> getKpiPercentageforYearQtrKpiId(String qtr, int year) {
		final String methodName = "getDealerList";
		LOGGER.enter(CLASSNAME, methodName);
		List kpiExtractList = null;
		try {
			synchronized (DPB_DLR_CACHE_LOCK) {
				String KPI_MONTHLY_DATA_KEY = qtr + String.valueOf(year);
				kpiExtractList  = ((List<KpiFile>) CACHE_MNGR.getFromCache(KPI_MONTHLY_DATA_KEY));
				if (kpiExtractList == null) {
					kpiExtractList = local.getMasterDataService().getKpiPercentageforYearQtrKpiId(qtr,year);
					CACHE_MNGR.addtoCache(KPI_MONTHLY_DATA_KEY,kpiExtractList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return kpiExtractList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Kpi> getKPIWithOldList() throws ServiceException {
		final String methodName = "getMenuItems";
		LOGGER.enter(CLASSNAME, methodName);
		List<Kpi> kpiList = null;
		try {
			synchronized (KPI_OLD_LIST_CACHE_LOCK) {
				kpiList = (List<Kpi>) CACHE_MNGR
						.getFromCache(MasterDataLookup.KPI_OLD_LIST_CKEY);
				if (kpiList == null) {
					kpiList = local.getMasterDataService().getKPIWithOldList();
					CACHE_MNGR.addtoCache(MasterDataLookup.KPI_OLD_LIST_CKEY,
							kpiList);
				}
			}
		} catch (TechnicalException e) {
			// TODO: handle exception
		}
		LOGGER.exit(CLASSNAME, methodName);
		return kpiList;
	}
	/**
	 * 
	 * @return
	 */
	public String getProcessDesc(String pCode){
		Map <String, String> pTypeMap = getProcessTypeAsMap();
		String pDesc = IConstants.EMPTY_STR;
		if(!pTypeMap.isEmpty()){
			pDesc = (String) pTypeMap.get(pCode);
		}			
		return pDesc;
	}
}
