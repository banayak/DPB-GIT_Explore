/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: MasterDataLookUpDAOImpl.java
 * Program Version			: 1.0
 * Program Description		: This class is used handle all database related common functionality which need to keep in cache. 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   August 28, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.constant.IMasterInfoLookQueryConstants;
import com.mbusa.dpb.common.db.ConnectionFactory;
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
import com.mbusa.dpb.common.exceptions.DPBExceptionHandler;
import com.mbusa.dpb.common.helper.MenuNode;
import com.mbusa.dpb.common.logger.DPBLog;

public class MasterDataLookUpDAOImpl implements IMasterDataLookUpDAO {

	private ConnectionFactory conFactory = ConnectionFactory.getConnectionFactory();
	private static DPBLog LOGGER = DPBLog.getInstance(MasterDataLookUpDAOImpl.class);
	static final private String CLASSNAME = MasterDataLookUpDAOImpl.class.getName();

	public List<MenuNode> getMenuItems(String uRole) {
		final String methodName = "getMenuItems";
		LOGGER.enter(CLASSNAME, methodName);
		List<MenuNode> menu = new ArrayList<MenuNode>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MenuNode menuNode = null;
		try { 
			con = conFactory.getConnection();
			if (con != null) {
				ps = con.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_MENU_LIST);
				ps.setString(1, uRole);
				rs = ps.executeQuery();
				while (rs.next()) {
					menuNode = new MenuNode();
					menuNode.setNodeID(rs.getInt("ID_DPB_MNU"));
					menuNode.setParentNode(rs.getInt("ID_DPB_PAR_MNU"));
					menuNode.setEnabled(rs.getString("IND_DPB_MNU_ENABL").charAt(0));
					menuNode.setLabelName(rs.getString("TXT_DPB_MNU_LBL"));
					menuNode.setMethodName(rs.getString("NAM_DPB_MNU_MTHD"));
					menuNode.setSequence(rs.getInt("NUM_DPB_MNU_SEQ"));
					menuNode.setMouseOverText(rs.getString("TXT_DPB_MNU_ALT"));
					menuNode.setRole(rs.getString("CDE_DPB_USR_ROLE"));
					menu.add(menuNode);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.MENU.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.MENU.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return menu;
	}

	public List<Kpi> getKPIList() {
		final String methodName = "getKPIList";
		LOGGER.enter(CLASSNAME, methodName);
		Kpi kpi = null;
		List<Kpi> kpiList = new ArrayList<Kpi>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try { 
			conn = conFactory.getConnection();
			stmt = conn.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_KPI_LIST);
			rs = stmt.executeQuery();
			while (rs.next()) {
				kpi = new Kpi();
				kpi.setKpiId(rs.getInt("ID_KPI"));
				kpi.setKpiName(rs.getString("NAM_KPI"));
				kpi.setEffDte(rs.getDate("DTE_KPI_EFF"));
				kpi.setInActiveDate(rs.getDate("DTE_INACT"));								
				kpiList.add(kpi);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.KPI.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.KPI.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return kpiList;
	}
	
	public List<Kpi> getKPIWithOldList() {
		final String methodName = "getKPIWithOldList";
		LOGGER.enter(CLASSNAME, methodName);
		Kpi kpi = null;
		List<Kpi> kpiList = new ArrayList<Kpi>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try { 
			conn = conFactory.getConnection();
			stmt = conn.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_KPI_OLD_LIST);
			rs = stmt.executeQuery();
			while (rs.next()) {
				kpi = new Kpi();
				kpi.setKpiId(rs.getInt("ID_KPI"));
				kpi.setKpiName(rs.getString("NAM_KPI"));
				kpi.setEffDte(rs.getDate("DTE_KPI_EFF"));
				kpi.setInActiveDate(rs.getDate("DTE_INACT"));								
				kpiList.add(kpi);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.KPI.OLD.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.KPI.OLD.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return kpiList;
	}

	public List<VehicleType> getVehicleList() {
		final String methodName = "getVehicleList";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;
		VehicleType vType = null;
		List<VehicleType> vList = new ArrayList<VehicleType>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			LOGGER.info("getVehicleList:"+IMasterInfoLookQueryConstants.RETRIVE_VEHICLE_LIST);
			stmt = conn
					.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_VEHICLE_LIST);
			rs = stmt.executeQuery();
			while (rs.next()) {
				vType = new VehicleType();
				String vCde=rs.getString("CDE_VEH_TYP");
				vType.setId(vCde);
				if(vCde != null && vCde.trim().length() > 0 && vCde.equalsIgnoreCase(IConstants.FTL)){
					vType.setVehicleType(IConstants.VEH_TYP_LIGHTTRUCK_DES);
				}
				else {
					vType.setVehicleType(rs.getString("DES_VEH_TYP"));
				}
				
				vList.add(vType);
			}
			// This code should be removed. This is added as discussion with onsite on 09/12/2013 with all team member. 
			
			/*vType = new VehicleType();			
			vType.setId("L");
			vType.setVehicleType("Frieghtliner");
			vList.add(vType);*/
			
		} catch (SQLException e) {
			LOGGER.error("DPB.VEHCILE.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.VEHCILE.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return vList;
	}

	public List<CondtionCode> getConditionCodes() {
		final String methodName = "getConditionCodes";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;
		// select CDE_CON,NAM_CND from DPB_CND_CDE

		CondtionCode cCode = null;
		List<CondtionCode> conditionCdList = new ArrayList<CondtionCode>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			stmt = conn
					.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_CONDITION_CODE);
			rs = stmt.executeQuery();
			while (rs.next()) {
				cCode = new CondtionCode();				
				/*cCode.setConditionCode(rs.getString("CDE_CND").trim());
				cCode.setConditionName(rs.getString("DES_CND_CDE").trim());*/
				cCode.setConditionCode(rs.getString("CDE_DPB_CND").trim());
				cCode.setConditionName(rs.getString("DES_DPB_CND_CDE").trim());
				conditionCdList.add(cCode);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.CONDITION.CODE.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.CONDITION.CODE.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					if (stmt != null) {
				}
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return conditionCdList;
	}
	public List<CompanyCode> getCoCodes() {
		final String methodName = "getCoCodes()";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;
		CompanyCode cCode = null;
		List<CompanyCode> conditionCdList = new ArrayList<CompanyCode>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			stmt = conn
					.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_CO_CDE);
			rs = stmt.executeQuery();
			while (rs.next()) {
				cCode = new CompanyCode();				
				cCode.setCode(rs.getString("CDE_CND"));
				cCode.setCodeDesc(rs.getString("DES_CND_CDE"));			
				conditionCdList.add(cCode);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.COMPANY.CODE.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.COMPANY.CODE.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return conditionCdList;
	}
	
	public List<NationalType> getNationalTypes() {
		final String methodName = "getNationalTypes()";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;
		NationalType nCode = null;
		List<NationalType> nationalTypList = new ArrayList<NationalType>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			stmt = conn.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_NATIONAL_TYP);
			rs = stmt.executeQuery();
			while (rs.next()) {
				nCode = new NationalType();				
				nCode.setNationalTypeCd(rs.getString("CDE_NATL_TYPE"));
				nCode.setNationalTypeCdDesc(rs.getString("DES_NATL_TYP"));
				
				nationalTypList.add(nCode);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.CONDITION.CODE.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.CONDITION.CODE.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return nationalTypList;
	}

	public List<ConditionType> getConditionTypes() {
		final String methodName = "getConditionTypes";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;
		List<ConditionType> codeTypes = new ArrayList<ConditionType>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			stmt = conn.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_DPB_CND_TYP);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ConditionType cType = new ConditionType();
				/*cType.setConditionTypeCode(rs.getString("CDE_CND_TYP"));
				cType.setConditionCodeName(rs.getString("DES_CND_TYP_CDE"));*/
				cType.setConditionTypeCode(rs.getString("CDE_DPB_CND_TYP"));
				cType.setConditionCodeName(rs.getString("DES_DPB_CND_TYP"));
				codeTypes.add(cType);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.CONDITION.CODE.TYPE.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.CONDITION.CODE.TYPE.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return codeTypes;
	}

	public List<DefStatus> getDefStatusCodes() {
		final String methodName = "getDefStatusCodes";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;
		DefStatus statusCode = null;
		List<DefStatus> conditionCdList = new ArrayList<DefStatus>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			stmt = conn
					.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_DPB_DEFN_STS);
			rs = stmt.executeQuery();
			while (rs.next()) {
				statusCode = new DefStatus();				
				/*statusCode.setDefStatusCode(rs.getString("CDE_DEFN_STS"));
				statusCode.setDefStatusName(rs.getString("DES_DEFN_STS"));*/
				statusCode.setDefStatusCode(rs.getString("CDE_DPB_STS"));
				statusCode.setDefStatusName(rs.getString("DES_DPB_STS"));
				conditionCdList.add(statusCode);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.DEFINITION.STATUS.CODE.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.DEFINITION.STATUS.CODE.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return conditionCdList;
	}

	public List<ProcessStatus> getProcessStatusCodes() {
		final String methodName = "getProcessStatusCodes";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;
		ProcessStatus pStatus = null;
		List<ProcessStatus> pStatusList = new ArrayList<ProcessStatus>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			stmt = conn.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_DPB_PROC_STS);
			rs = stmt.executeQuery();
			while (rs.next()) {
				pStatus = new ProcessStatus();
				/*pStatus.setProcessStatusCd(rs.getString("CDE_PROC_STS"));
				pStatus.setProcessStatusName(rs.getString("DES_PROC_STS"));*/
				pStatus.setProcessStatusCd(rs.getString("CDE_DPB_PROC_STS"));	
				pStatus.setProcessStatusName(rs.getString("DES_DPB_PROC_STS"));
				pStatusList.add(pStatus);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.PROCESS.STATUS.CODE.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.PROCESS.STATUS.CODE.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return pStatusList;
	}

	public List<ProcessTypes> getProcessTypeCodes() {
		final String methodName = "getProcessTypeCodes";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;
		ProcessTypes pTypes = null;
		List<ProcessTypes> conditionCdList = new ArrayList<ProcessTypes>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			stmt = conn
					.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_DPB_PROC_TYP);
			rs = stmt.executeQuery();
			while (rs.next()) {
				pTypes = new ProcessTypes();
				/*pTypes.setProcessTypeCode(rs.getString("CDE_PROC_TYP"));
				pTypes.setProcessTypeName(rs.getString("DES_PROC_TYP"));*/
				pTypes.setProcessTypeCode(rs.getString("CDE_DPB_PROC_TYP"));
				pTypes.setProcessTypeName(rs.getString("DES_DPB_PROC_TYP"));
				conditionCdList.add(pTypes);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.PROCESS.TYPES.CODE.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.PROCESS.TYPES.CODE.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return conditionCdList;
	}
	public List<Scheduler> getSchedulerCodes() {
		final String methodName = "getSchedulerCodes";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;

		Scheduler schedulerCode = null;
		List<Scheduler> schedulerCdList = new ArrayList<Scheduler>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			stmt = conn
					.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_DPB_SCHD);
			rs = stmt.executeQuery();
			while (rs.next()) {
				schedulerCode = new Scheduler();
				/*schedulerCode.setScheduleCode(rs.getString("CDE_SCHD"));
				schedulerCode.setScheduleName(rs.getString("DES_SCHD"));*/
				schedulerCode.setScheduleCode(rs.getString("CDE_DPB_SCHD"));
				schedulerCode.setScheduleName(rs.getString("DES_DPB_SCHD"));
				schedulerCdList.add(schedulerCode);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.SCHEDULER.CODE.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.SCHEDULER.CODE.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return schedulerCdList;
	}

	public List<Trigger> getTriggerCodes() {
		final String methodName = "getTriggerCodes";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;

		Trigger tCode = null;
		List<Trigger> conditionCdList = new ArrayList<Trigger>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			stmt = conn
					.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_DPB_TRGR);
			rs = stmt.executeQuery();
			while (rs.next()) {
				tCode = new Trigger();
				/*tCode.setTriggerCode(rs.getString("CDE_TRGR"));
				tCode.setTriggerName(rs.getString("DES_TRGR"));*/
				tCode.setTriggerCode(rs.getString("CDE_DPB_PROC_INIT_TYP"));
				tCode.setTriggerName(rs.getString("DES_DPB_PROC_INIT_TYP"));
				conditionCdList.add(tCode);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.TRIGGER.CODE.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.TRIGGER.CODE.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return conditionCdList;
	}

	public List<UserRoles> getUserRoles() {
		final String methodName = "getUserRoles";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;
		// select CDE_CON,NAM_CND from DPB_CND_CDE

		UserRoles uRole = null;
		List<UserRoles> uRoleList = new ArrayList<UserRoles>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			stmt = conn
					.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_DPB_USR_ROLE);
			rs = stmt.executeQuery();
			while (rs.next()) {
				uRole = new UserRoles();
				/*uRole.setUserId(rs.getInt("CDE_USER_ROLE"));
				uRole.setUserRole(rs.getString("DES_USR_ROLE"));*/
				uRole.setUserId(rs.getInt("CDE_DPB_USR_ROLE"));
				uRole.setUserRole(rs.getString("DES_DPB_USR_ROLE"));
				uRoleList.add(uRole);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.USER.ROLE.CODE.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.USER.ROLE.CODE.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return uRoleList;
	}	
	public List<UseCode> getUseCode() {
		final String methodName = "getCoCodes()";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;
		UseCode uCode = null;
		List<UseCode> useCdList = new ArrayList<UseCode>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			stmt = conn
					.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_USE_CODE);
			rs = stmt.executeQuery();
			while (rs.next()) {
				uCode = new UseCode();
				uCode.setUseCode(rs.getString("CDE_USE"));
				uCode.setUseCodeDesc(rs.getString("DES_USE_CDE"));
				/*cCode.setCode(rs.getString("CDE_USE"));
				cCode.setUseCodeDesc(rs.getString("DES_USE_CDE"));*/
				 
				useCdList.add(uCode);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.USE.CODE.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.USE.CODE.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return useCdList;
	}

	public List<VehicleStatus> getVehicleStatusCodes() {
		final String methodName = "getVehicleStatusCodes";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;
		// select CDE_CON,NAM_CND from DPB_CND_CDE

		VehicleStatus vStatus = null;
		List<VehicleStatus> vehicleStatusList = new ArrayList<VehicleStatus>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			stmt = conn
					.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_VEH_STAT);
			rs = stmt.executeQuery();
			while (rs.next()) {
				vStatus = new VehicleStatus();
				vStatus.setVehicleCode(rs.getString("CDE_VEH_STAT"));
				vStatus.setVehicleStatus(rs.getString("DES_VEH_STAT"));
				vehicleStatusList.add(vStatus);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.VEHICLE.STAUS.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.VEHICLE.STAUS.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return vehicleStatusList;
	}
	public List<Region> getRegionCodes() {
		final String methodName = "getRegionCodes";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;
		Region rCode = null;
		List<Region> conditionCdList = new ArrayList<Region>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			stmt = conn
					.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_REGION);
			rs = stmt.executeQuery();
			while (rs.next()) {
				rCode = new Region();				
				rCode.setRegionCode(rs.getString("CDE_RGN"));
				rCode.setRegionName(rs.getString("DES_RGN"));				
				conditionCdList.add(rCode);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.REGION.CODE.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.REGION.CODE.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return conditionCdList;
	}
	public List<CondtionCode> getConditionTypeList() {
		final String methodName = "getCondtionCode";
		LOGGER.enter(CLASSNAME, methodName);
		Connection conn = null;
		// select CDE_CON,NAM_CND from DPB_CND_CDE

		CondtionCode cCode = null;
		List<CondtionCode> conditionCdList = new ArrayList<CondtionCode>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = conFactory.getConnection();
			stmt = conn
					.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_REGION);
			rs = stmt.executeQuery();
			while (rs.next()) {
				cCode = new CondtionCode();
				cCode.setConditionCode(rs.getString("CDE_CON"));
				cCode.setConditionName(rs.getString("NAM_CND"));
				conditionCdList.add(cCode);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.CONDITION.CODE.MASTER.DATA", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.CONDITION.CODE.MASTER.DATA", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return conditionCdList;
	}

	public List<ConditionDefinition> getConditionList() {
		final String methodName = "getConditionList()";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConditionDefinition condDef = new ConditionDefinition();
		try {
			con = conFactory.getConnection();
			String VC_SELECT_QUERY = "select ID_CND, NAM_CND from DPB_COND ";
			ps = con.prepareStatement(VC_SELECT_QUERY);

			rs = ps.executeQuery();
			condList = new ArrayList<ConditionDefinition>();
			while (rs.next()) {
				condDef = new ConditionDefinition();
				condDef.setId(rs.getInt(1));
				condDef.setConditionName(rs.getString(2));
				condList.add(condDef);
			}
		} catch (SQLException e) {
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}

	public List<ConditionDefinition> getSpecialConditionList() {
		final String methodName = "getSpecialConditionList() ";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConditionDefinition condDef = new ConditionDefinition();
		try {
			con = conFactory.getConnection();
			//String RETIRVE_ACTIVE_CONDTION_TYPE_AS_SPECIAL_ = "select ID_CND, NAM_CND from DPB_COND where IND_BLK_CND = 'S' ";//and CDE_DPB_STS = 'A' ";
			
			ps = con.prepareStatement(IMasterInfoLookQueryConstants.RETIRVE_ACTIVE_CONDTION_TYPE_AS_SPECIAL);
		

			rs = ps.executeQuery();
			condList = new ArrayList<ConditionDefinition>();
			while (rs.next()) {
				condDef = new ConditionDefinition();
				condDef.setId(rs.getInt(1));
				condDef.setConditionName(rs.getString(2));
				condList.add(condDef);
			}
		} catch (SQLException e) {
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}

	public List<ConditionDefinition> getVehicleConditionList() {
		final String methodName = "getVehicleConditionList()";
		LOGGER.enter(CLASSNAME, methodName);
		List<ConditionDefinition> condList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConditionDefinition condDef = new ConditionDefinition();
		try {
			con = conFactory.getConnection();
			//String VC_SELECT_QUERY = "select ID_CND, NAM_CND from DPB_COND where IND_BLK_CND = 'V'";
			ps = con.prepareStatement(IMasterInfoLookQueryConstants.RETIRVE_ACTIVE_CONDTION_TYPE_AS_VEHICLE);
			

			rs = ps.executeQuery();
			condList = new ArrayList<ConditionDefinition>();
			while (rs.next()) {
				condDef = new ConditionDefinition();
				condDef.setId(rs.getInt(1));
				condDef.setConditionName(rs.getString(2));
				condList.add(condDef);
			}
		} catch (SQLException e) {
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}

	public List<ConditionDefinition> getBlockedConditionList() {
		final String methodName = "getBlockedConditionList()";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		List<ConditionDefinition> condList = null;
		ResultSet rs = null;
		ConditionDefinition condDef = new ConditionDefinition();
		try {
			con = conFactory.getConnection();
			//String VC_SELECT_QUERY = "select ID_CND, NAM_CND from DPB_COND where IND_BLK_CND = 'B'";
			ps = con.prepareStatement(IMasterInfoLookQueryConstants.RETIRVE_ACTIVE_CONDTION_TYPE_AS_BLOCKING);
			//ps = con.prepareStatement(VC_SELECT_QUERY);

			rs = ps.executeQuery();
			condList = new ArrayList<ConditionDefinition>();
			while (rs.next()) {
				condDef = new ConditionDefinition();
				condDef.setId(rs.getInt(1));
				condDef.setConditionName(rs.getString(2));
				
				condDef.setId(rs.getInt("ID_DPB_CND"));
				condDef.setConditionName(rs.getString("NAM_DPB_CND"));
				condDef.setConditionDesc(rs.getString("DES_DPB_CND"));
				condDef.setVariableName(rs.getString("NAM_DPB_VAR"));
				condDef.setCheckValue(rs.getString("TXT_DPB_CHK_VAL"));
				condDef.setConditionType(rs.getString("CDE_DPB_CND_TYP"));
				condDef.setRegularExp(rs.getString("TXT_DPB_REG_EX"));
				condDef.setStatus(rs.getString("CDE_DPB_STS"));
				condList.add(condDef);
			}
		} catch (SQLException e) {
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return condList;
	}

	public Date getRetailCalenderEndDate(Date endDate) {
		final String methodName = "getRetailCalenderEndDate()";
		LOGGER.enter(CLASSNAME, methodName);
		Date eDate = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		java.util.Date dt = new java.util.Date();
		try {
			con = conFactory.getConnection();
			ps = con.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_RTL_ID);
			rs = ps.executeQuery();
			ps.setDate(1, endDate);
			while (rs.next()) {
				eDate = rs.getDate("DTE_CAL");
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.RTL.END.DATE.ID.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.RTL.END.DATE.ID.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("DPB.RTL.END.DATE.ID.003", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return endDate;
	}
	
	/*public RtlCalenderDefinition getRetailCalender() {
		final String methodName = "getRetailCalenderEndDate()";
		LOGGER.enter(CLASSNAME, methodName);		
		RtlCalenderDefinition rtlCal = new RtlCalenderDefinition();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try {			 
			con = conFactory.getConnection();
			ps = con.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_CALENDER_INFO);
			rs = ps.executeQuery();
			while (rs.next()) {
				rtlCal.setDayid(rs.getInt("ID_DAY"));
				rtlCal.setDteCal(rs.getDate("DTE_CAL"));
				rtlCal.setDayDesc(rs.getString("DES_DAY"));
				rtlCal.setDteRetlMthBeg(rs.getDate("DTE_RETL_MTH_BEG"));
				rtlCal.setDteRetlMthEnd(rs.getDate("DTE_RETL_MTH_END"));
				rtlCal.setRetlMthNum(rs.getString("NUM_RETL_MTH"));
				rtlCal.setRetlMthName(rs.getString("NAM_RETL_MTH"));
				rtlCal.setRetlMthDayNum(rs.getInt("NUM_DY_RETL_MTH"));
				rtlCal.setDteRetlQtrBeg(rs.getDate("DTE_RETL_QTR_BEG"));
				rtlCal.setDteRetlQtrEnd(rs.getDate("DTE_RETL_QTR_END"));
				rtlCal.setRetlQtrNum(rs.getString("NUM_RETL_QTR"));
				rtlCal.setDteRetlYearBeg(rs.getDate("DTE_RETL_YR_BEG"));
				rtlCal.setDteRetlYearEnd(rs.getDate("DTE_RETL_YR_END"));				
				rtlCal.setRetlYearNum(rs.getString("NUM_RETL_YR"));
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.RTL.CALENDER.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.RTL.CALENDER.003", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return rtlCal;
	}*/
	public List<RtlCalenderDefinition> getRetailCalender() {
		final String methodName = "getRetailCalenderEndDate()";
		LOGGER.enter(CLASSNAME, methodName);		
		RtlCalenderDefinition rtlCal = null;
		List<RtlCalenderDefinition> rtlList =  new ArrayList<RtlCalenderDefinition>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try {			 
			con = conFactory.getConnection();
			LOGGER.info("*************getRetailCalender:"+IMasterInfoLookQueryConstants.RETRIVE_CALENDER_INFO);
			ps = con.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_CALENDER_INFO);
			rs = ps.executeQuery();
			while (rs.next()) {
				rtlCal = new RtlCalenderDefinition();				
				rtlCal.setDteCal(rs.getDate("DTE_CAL"));
				rtlCal.setDayDesc(rs.getString("DES_DAY"));
				rtlCal.setDteRetlWeekBeg(rs.getDate("DTE_CAL_WK_BEG"));
				rtlCal.setDteRetlWeekEnd(rs.getDate("DTE_CAL_WK_END"));
				rtlCal.setDteRetlMthBeg(rs.getDate("DTE_RETL_MTH_BEG"));
				rtlCal.setDteRetlMthEnd(rs.getDate("DTE_RETL_MTH_END"));
				rtlCal.setRetlMthNum(rs.getString("NUM_RETL_MTH"));
				rtlCal.setRetlMthName(rs.getString("NAM_RETL_MTH"));
				rtlCal.setRetlMthDayNum(rs.getInt("NUM_DY_RETL_MTH"));
				rtlCal.setDteRetlQtrBeg(rs.getDate("DTE_RETL_QTR_BEG"));
				rtlCal.setDteRetlQtrEnd(rs.getDate("DTE_RETL_QTR_END"));
				rtlCal.setRetlQtrNum(rs.getString("NUM_RETL_QTR"));
				rtlCal.setDteRetlYearBeg(rs.getDate("DTE_RETL_YR_BEG"));
				rtlCal.setDteRetlYearEnd(rs.getDate("DTE_RETL_YR_END"));				
				rtlCal.setRetlYearNum(rs.getString("NUM_RETL_YR"));
				rtlList.add(rtlCal);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.RTL.CALENDER.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.RTL.CALENDER.003", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return rtlList;
	}
	public List<String> getColumnNames(String tableName) {
		final String methodName = "getColumnNames()";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> columnNames = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			columnNames = new ArrayList<String>();
			con  = conFactory.getConnection();
			String query = "SELECT COLNAME from SYSCAT.COLUMNS where TABNAME =?";
			if (con != null) {
				ps = con.prepareStatement(query);
				ps.setString(1, tableName);
				rs = ps.executeQuery();
				while (rs.next()) {
					String column = rs.getString("COLNAME");
					columnNames.add(column.trim());
				}
			}
		} catch (SQLException e) {
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return columnNames;
	}
	
	public Map<String, String> getReportContentList() {
		final String methodName = "getReportContentList";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, String> rptCtntList = null;
		try {
			con = conFactory.getConnection();
			statement = con.prepareStatement(IMasterInfoLookQueryConstants.RETRIEVE_REPORT_CONTENT);
			rs = statement.executeQuery();
			rptCtntList = new HashMap<String,String>();
			while (rs.next()) {
				rptCtntList.put(rs.getString("ID_DPB_RPT_CTNT")!=null ? rs.getString("ID_DPB_RPT_CTNT").trim():null, rs.getString("NAM_DPB_RPT_CTNT")!=null ? rs.getString("NAM_DPB_RPT_CTNT").trim():null);
			}
		}
		catch (SQLException e) {
			LOGGER.error("DPB.RPT.ACT.CTNT.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.RPT.ACT.CTNT.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("DPB.RPT.ACT.CTNT.003", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
	return rptCtntList;
	}

	@Override
	public Map<String, String> getReportQryList() {
		final String methodName = "getReportQryList";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, String> rptQryList = null;
		try {
			con = conFactory.getConnection();
			statement = con.prepareStatement(IMasterInfoLookQueryConstants.RETRIEVE_REPORT_QUERY);
			rs = statement.executeQuery();
			rptQryList = new HashMap<String,String>();
			while (rs.next()) {
				rptQryList.put(rs.getString("ID_DPB_RPT_QRY")!=null ? rs.getString("ID_DPB_RPT_QRY").trim():null, rs.getString("NAM_DPB_RPT_QRY")!=null ?  rs.getString("NAM_DPB_RPT_QRY").trim():null);
			}
		}
		catch (SQLException e) {
			LOGGER.error("DPB.RPT.ACT.QRY.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.RPT.ACT.QRY.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("DPB.RPT.ACT.QRY.003", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return rptQryList;
	}
	@Override
	public Map<String, String> getDealerList() {
		final String methodName = "getDealerList";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, String> dlrIds = null;
		try {
			con = conFactory.getConnection();
			statement = con.prepareStatement(IMasterInfoLookQueryConstants.RETRIEVE_DLR_LIST);
			rs = statement.executeQuery();
			dlrIds = new HashMap<String,String>();
			while (rs.next()) {
				dlrIds.put(rs.getString("ID_DLR")!=null ? rs.getString("ID_DLR").trim():null, rs.getString("ID_DLR")!=null ?  rs.getString("ID_DLR").trim():null);
			}
		}
		catch (SQLException e) {
			LOGGER.error("DPB.DLR.LIST.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.DLR.LIST.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("DPB.DLR.LIST.003", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return dlrIds;
	}
	@Override
	public List<KpiFile> getKpiPercentageforYearQtrKpiId(String period, int year) {
		LOGGER.enter(CLASSNAME, "retrieveKpiFileListForDealer()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		KpiFile kpiFile = null;
		List<KpiFile> kpiFileList =  new ArrayList<KpiFile>();
		try {
			con = conFactory.getConnection();	
			String query= IMasterInfoLookQueryConstants.GET_KPI_FILE;	

			ps = con.prepareStatement(query);			
			ps.setInt(1, year);
			ps.setString(2, period);		

			rs= ps.executeQuery();
			LOGGER.info("************ Query -->retrieveKpiFileListForDealer:"+query);			
			while(rs.next()){
				kpiFile =  new KpiFile();
				kpiFile.setDealerId(rs.getString("ID_DLR"));
				kpiFile.setKpiId(rs.getInt("ID_KPI"));
				kpiFile.setKpiFiscalQuarter(rs.getString("DTE_FSCL_QTR"));
				kpiFile.setKpiPct(rs.getDouble("PCT_KPI"));
				kpiFile.setKpiYr(rs.getInt("DTE_FSCL_YR"));
				kpiFileList.add(kpiFile);
			}			
		}catch(SQLException  e){
			LOGGER.error("DPB.COMMON.DAO.KPI.DEALER.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(Exception  ne){
			LOGGER.error("DPB.COMMON.DAO.KPI.DEALER.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			}catch (SQLException e){
				LOGGER.error("DPB.COMMON.DAO.KPI.DEALER.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return kpiFileList;
	}
}
