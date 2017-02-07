package com.mbusa.dpb.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import com.mbusa.dpb.common.constant.IDashBoardQueryConstants;
import com.mbusa.dpb.common.db.ConnectionFactory;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.exceptions.DPBExceptionHandler;
import com.mbusa.dpb.common.helper.MenuNode;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;

public class DashBoardDAOImpl implements IDashBoardDAO {	
	
	private ConnectionFactory conFactory = ConnectionFactory.getConnectionFactory();
	private static DPBLog LOGGER = DPBLog.getInstance(DashBoardDAOImpl.class);
	static final private String CLASSNAME = DashBoardDAOImpl.class.getName();
	
	public List<DPBProcessLogBean> getLastTwoDaysProcesses() {	
		final String methodName = "getMenuItems";
		LOGGER.enter(CLASSNAME, methodName);
		List<MenuNode> menu = new ArrayList<MenuNode>();
		List<DPBProcessLogBean> list = new ArrayList<DPBProcessLogBean>();
		SimpleDateFormat dtefmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		DPBProcessLogBean dpbLog = null;
		try {
			con = conFactory.getConnection();
			String query = IDashBoardQueryConstants.RETRIVE_DASHBOARD_LIST;
			statement = con.prepareStatement(query);
			rs=statement.executeQuery();
			  while(rs.next()){
				dpbLog = new DPBProcessLogBean();
				dpbLog.setDpbProcessId(rs.getInt("PROC_ID"));
				dpbLog.setProcessMessage(rs.getString("DETAILS"));
				dpbLog.setDpbProcessStatus(rs.getString("STATUS"));
				//dpbLog.getDpbProcess().setDefinitionId(rs.getInt(4));
				dpbLog.getDpbProcess().setProcessType(rs.getString("PGM_NAME"));
				//String lastDate = rs.getString(6);
				//date = dtefmt.parse(lastDate);
				//dpbLog.setLastUpdatedDate(date);
				dpbLog.setProcessDay(rs.getString("PROC_DAY"));
				dpbLog.setPrgType(rs.getString("PROG_TYP"));
				dpbLog.setProcDate(rs.getDate("PROC_ACT_DTE"));
				list.add(dpbLog);
			  }	
		}catch(SQLException  e){
			LOGGER.error("DPB.DASHBOARD.LIST", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.DASHBOARD.LIST", ne);
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
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}	
		return list;
}
	
	//
	/* 
	 * added to view processes datewise  - Amit
	 * @see com.mbusa.dpb.common.dao.IDashBoardDAO#getProcessesForDate(java.lang.String)
	 */
	@Override
	public List<DPBProcessLogBean> getProcessesForDate(String date) {	
		final String methodName = "getMenuItems";
		LOGGER.enter(CLASSNAME, methodName);
		List<DPBProcessLogBean> list = new ArrayList<DPBProcessLogBean>();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		DPBProcessLogBean dpbLog = null;
		try {
			con = conFactory.getConnection();
			String query = IDashBoardQueryConstants.RETRIVE_DASHBOARD_LIST_BY_DATE;
			statement = con.prepareStatement(query);
			statement.setString(1, date);
			rs=statement.executeQuery();
			  while(rs.next()){
				dpbLog = new DPBProcessLogBean();
				dpbLog.setDpbProcessId(rs.getInt("PROC_ID"));
				dpbLog.setProcessMessage(rs.getString("DETAILS"));
				dpbLog.setDpbProcessStatus(rs.getString("STATUS"));
				//dpbLog.getDpbProcess().setDefinitionId(rs.getInt(4));
				dpbLog.getDpbProcess().setProcessType(rs.getString("PGM_NAME"));
				//String lastDate = rs.getString(6);
				//date = dtefmt.parse(lastDate);
				//dpbLog.setLastUpdatedDate(date);
				dpbLog.setProcessDay(rs.getString("PROC_DAY"));
				dpbLog.setPrgType(rs.getString("PROG_TYP"));
				dpbLog.setProcDate(rs.getDate("PROC_ACT_DTE"));
				list.add(dpbLog);
			  }	
		}catch(SQLException  e){
			LOGGER.error("DPB.DASHBOARD.LIST", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.DASHBOARD.LIST", ne);
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
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}	
		return list;
	}
	
	/**
	 * This method will retrieve the retail calendar info for the given year.
	 */
	public List<RtlCalenderDefinition> getRetailCalender(int year) {
		final String methodName = "getRetailCalender";
		LOGGER.enter(CLASSNAME, methodName);
		List<RtlCalenderDefinition> rtlList = new ArrayList<RtlCalenderDefinition>();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		RtlCalenderDefinition rtlCal = null;
		try {
			con = conFactory.getConnection();
			String query = IDashBoardQueryConstants.GET_RETAIL_CAL_FOR_YEAR;
			statement = con.prepareStatement(query);
			statement.setInt(1, year);
			rs=statement.executeQuery();
			while (rs.next()) {
				//DTE_RETL_MTH_BEG, DTE_RETL_MTH_END,NAM_RETL_MTH,NUM_RETL_MTH,NUM_RETL_YR
				rtlCal = new RtlCalenderDefinition();				
				rtlCal.setDteRetlMthBeg(rs.getDate("DTE_RETL_MTH_BEG"));
				rtlCal.setDteRetlMthEnd(rs.getDate("DTE_RETL_MTH_END"));
				rtlCal.setRetlMthNum(rs.getString("NUM_RETL_MTH"));
				rtlCal.setRetlMthName(rs.getString("NAM_RETL_MTH"));
				rtlCal.setRetlYearNum(rs.getString("NUM_RETL_YR"));
				rtlList.add(rtlCal);
			}
		} catch(SQLException  e){
			LOGGER.error("DPB.DASHBOARD.Calendar", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch(NamingException  ne){
			LOGGER.error("DPB.DASHBOARD.Calendar", ne);
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
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}	
		return rtlList;
	}

}
