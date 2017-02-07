/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: DPBCommonDAOImpl.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all common DB transactions.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Sep 03, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.mbusa.dpb.common.constant.ICommonQueryConstants;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.constant.IMasterInfoLookQueryConstants;
import com.mbusa.dpb.common.db.ConnectionFactory;
import com.mbusa.dpb.common.domain.BonusInfo;
import com.mbusa.dpb.common.domain.ConditionDefinition;
import com.mbusa.dpb.common.domain.DealerInfo;
import com.mbusa.dpb.common.domain.FileProcessLogMessages;
import com.mbusa.dpb.common.domain.KpiFile;
import com.mbusa.dpb.common.domain.KpiFileProcessing;
import com.mbusa.dpb.common.domain.LeadershipBonusDetails;
import com.mbusa.dpb.common.domain.LeadershipInfo;
import com.mbusa.dpb.common.domain.PaymentDealerInfo;
import com.mbusa.dpb.common.domain.PaymentInfo;
import com.mbusa.dpb.common.domain.ProgramDefinition;
import com.mbusa.dpb.common.domain.ProgramDefinitionBean;
import com.mbusa.dpb.common.domain.SchedulerProcess;
import com.mbusa.dpb.common.domain.VehicleConditions;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.DPBExceptionHandler;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.DateCalenderUtil;

public class DPBCommonDAOImpl implements IDPBCommonDAO {
	static final private String CLASSNAME = DPBCommonDAOImpl.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(DPBCommonDAOImpl.class);
	private ConnectionFactory conFactory = ConnectionFactory.getConnectionFactory();
	static PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
	
	  
	public List<VehicleConditions> getVehicleConditions(String condType) {
		final String methodName = "getVehicleConditions";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;	
		List<VehicleConditions> vCondList = new ArrayList<VehicleConditions>();
		VehicleConditions vCond = null;
		try {
			con  = conFactory.getConnection();		
			String COND_SELECT_QUERY = "";
			if ("VEH_COND".equalsIgnoreCase(condType)) {
				COND_SELECT_QUERY = ICommonQueryConstants.VEHICLE_COND_SELECT_QUERY;
			} else if ("BLOCK".equalsIgnoreCase(condType)) {
				COND_SELECT_QUERY = ICommonQueryConstants.BLOCKED_COND_SELECT_QUERY;
			}/*else if("SPL_PROGRAM".equalsIgnoreCase(condType)){
				COND_SELECT_QUERY = ICommonQueryConstants.SPECIAL_COND_SELECT_QUERY;
			}*/
			ps = con.prepareStatement(COND_SELECT_QUERY);
			rs = ps.executeQuery();	
			while(rs.next()){
				vCond = new VehicleConditions();
				vCond.setVehType(rs.getString("CDE_VEH_TYP"));
				vCond.setVehCondId(rs.getInt("ID_DPB_CND"));
				vCond.setCondName(rs.getString("NAM_DPB_CND"));
				vCond.setCondtype(rs.getString("CDE_DPB_CND"));
				vCond.setVarName(rs.getString("NAM_DPB_VAR"));
				vCond.setCheckValue(rs.getString("TXT_DPB_CHK_VAL"));
				vCond.setRegularExp(rs.getString("TXT_DPB_REG_EX"));
				vCondList.add(vCond);
			}
		}catch(SQLException  e){
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
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
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return vCondList;
	}
	
	public void insertIntoProcessLog(FileProcessLogMessages message) {
		final String methodName = "LogProcessToDPB";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		try {		
			con = conFactory.getConnection();		
			String sqlQuery = ICommonQueryConstants.INSERT_PROC_LOG;
			ps = con.prepareStatement(sqlQuery);
			ps.setString(1, message.getProcessStatusCode());
			ps.setInt(2, message.getProcessDefId());
			ps.setString(3, message.getProcessMessage());
			ps.setString(4, message.getUserId());
			ps.setString(5, message.getUserId());
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Exception Occured while inserting the data", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (Exception ne) {
			LOGGER.error("Exception Occured while inserting the data", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
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
	}
	
	public List<String> checkForManualTasks() {
		final String methodName = "checkForManualTasks";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> manualTasks = new ArrayList<String>();
		try {
			con = conFactory.getConnection();			
			String sqlQuery = ICommonQueryConstants.CHECK_MANUAL_TASKS;
			ps = con.prepareStatement(sqlQuery);
			rs = ps.executeQuery();
			while(rs.next()) {
				manualTasks.add(rs.getString("PROC_ID"));
			}
		} catch (SQLException e) {
			LOGGER.error("Exception Occured while fetching the manual tasks", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("Exception Occured while fetching the manual tasks", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
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
		return manualTasks;
	}
	
	public List<SchedulerProcess> getPrevDayProcs() {
		final String methodName = "checkForManualTasks";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<SchedulerProcess> processes = new ArrayList<SchedulerProcess>();
		SchedulerProcess process = null;
		try {
			con = conFactory.getConnection();			
			String sqlQuery = ICommonQueryConstants.FETCH_PREVIOUS_DAY_PROCESSES;
			ps = con.prepareStatement(sqlQuery);
			rs = ps.executeQuery();
			while(rs.next()) {
				process = new SchedulerProcess();
				process.setProcId(rs.getInt("PROC_ID"));
				process.setLogStatus(rs.getString("LOG_STATUS"));
				process.setLogReasonTxt(rs.getString("LOG_RES_TEXT"));
				process.setProcDate(rs.getDate("DTE_CAL"));
				process.setRptProgId(rs.getInt("PROG_ID_RPT"));
				process.setFileProgId(rs.getInt("PROG_ID_FILE"));
				process.setPgmProgId(rs.getInt("PROG_ID_PGM"));
				process.setLdrProgId(rs.getInt("PROG_ID_LDR_PGM"));
				process.setActProcDate(rs.getDate("PROC_ACT_DTE"));
				process.setProcType(rs.getString("PROG_TYP"));
				process.setOverProcDate(rs.getDate("OVER_PROC_DTE"));
				process.setOverProcTime(rs.getTime("OVER_TME_PROC"));
				process.setOverTrigger(rs.getString("OVER_STATUS"));
				process.setOverReasonTxt(rs.getString("OVER_TXT_RSN"));
				processes.add(process);
			}
		} catch (SQLException e) {
			LOGGER.error("Exception Occured while fetching the previous day processes", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("Exception Occured while fetching the previous day processes", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
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
		return processes;
	}

	@Override
	public void savePrevDayProcsIntoLogs(List<SchedulerProcess> preDayProcess) {
		final String methodName = "savePrevDayProcsIntoLogs";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conFactory.getConnection();
			String sqlQuery = ICommonQueryConstants.INSERT_PROC_LOG;
			ps = con.prepareStatement(sqlQuery);
			for (SchedulerProcess process: preDayProcess) {
				ps.setString(1, process.getLogStatus());
				ps.setInt(2, process.getProcId());
				ps.setString(3, process.getLogReasonTxt());
				ps.setString(4, IConstants.CREATED_USER_ID);
				ps.setString(5, IConstants.LAST_UPDATED_USER_ID);
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			LOGGER.error("Exception Occured while creating new process log for previous day processes", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("Exception Occured while creating new process log for previous day processes", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
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
	}

	@Override
	public void createProcForPrevDayProcs(List<SchedulerProcess> preDayProcess) {
		final String methodName = "createProcForPrevDayProcs";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conFactory.getConnection();
			String sqlQuery = ICommonQueryConstants.INSERT_INTO_PROC;
			ps = con.prepareStatement(sqlQuery);
			for (SchedulerProcess process: preDayProcess) {
				ps.setString(1, process.getRptProgId()==0?null:""+process.getRptProgId());
				ps.setString(2, process.getPgmProgId()==0?null:""+process.getPgmProgId());
				ps.setString(3, process.getFileProgId()==0?null:""+process.getFileProgId());
				ps.setDate(4, process.getProcDate());
				ps.setString(5, process.getLdrProgId()==0?null:""+process.getLdrProgId());
				ps.setString(6, process.getProcType());
				ps.setDate(7, process.getActProcDate());
				ps.setDate(8, process.getOverProcDate());
				ps.setTime(9, process.getOverProcTime());
				ps.setString(10, process.getOverTrigger());
				ps.setString(11, process.getOverReasonTxt());
				ps.setString(12, IConstants.CREATED_USER_ID);
				ps.setString(13, IConstants.LAST_UPDATED_USER_ID);
				ps.addBatch();
/*				
				process.getPgmProgId()+":"+process.getRptProgId());
				ps.executeUpdate();
*/			}
			ps.executeBatch();
		} catch (SQLException e) {
			LOGGER.error("Exception Occured while creating new process for previous day processes", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("Exception Occured while creating new process for previous day processes", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
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
	}

	@Override
	public List<SchedulerProcess> getTodayTasks() {
		final String methodName = "getTodayTasks";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<SchedulerProcess> processes = new ArrayList<SchedulerProcess>();
		SchedulerProcess process = null;
		try {
			con = conFactory.getConnection();			
			String sqlQuery = ICommonQueryConstants.FETCH_TODAY_TASKS;
			System.out.println("sqlQuery -  "+ sqlQuery);
			ps = con.prepareStatement(sqlQuery);			
			rs = ps.executeQuery();
			System.out.println("result set -  "+ rs);
			while(rs.next()) {
				process = new SchedulerProcess();
				process.setProcId(rs.getInt("PROC_ID"));
				process.setProcDate(rs.getDate("PROC_DATE"));
				process.setRptProgId(rs.getInt("PROG_ID_RPT"));
				process.setFileProgId(rs.getInt("PROG_ID_FILE"));
				process.setPgmProgId(rs.getInt("PROG_ID_PGM"));
				process.setLdrProgId(rs.getInt("PROG_ID_LDR_PGM"));
				process.setProcType(rs.getString("PROG_TYP"));
				process.setActProcDate(rs.getDate("PROC_ACT_DTE"));
				process.setOverProcDate(rs.getDate("OVER_PROC_DTE"));
				process.setOverProcTime(rs.getTime("TME_OVER_PROC"));
				process.setOverTrigger(rs.getString("OVER_STATUS"));
				process.setOverReasonTxt(rs.getString("OVER_TXT_RSN"));
				process.setLastUpdatedUser(rs.getString("LST_UPD_USR"));
				process.setProcTypeSeq(rs.getInt("PROC_TYP_SEQ"));
				process.setFinalTriggerSts(rs.getString("FINAL_TRIG_STATUS"));
				processes.add(process);
			}
		} catch (SQLException e) {
			LOGGER.error("Exception Occured while fetching the today tasks", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("Exception Occured while fetching the today tasks", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
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
		return processes;
	}

	@Override
	public void saveFailedTasksIntoLogs(List<SchedulerProcess> processes,
			int count) {
		final String methodName = "savePrevDayProcsIntoLogs";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		SchedulerProcess process = null;
		int failedProcId = 0;
		try {
			con = conFactory.getConnection();
			String sqlQuery = ICommonQueryConstants.INSERT_PROC_LOG;
			ps = con.prepareStatement(sqlQuery);
			for (int i = count; i < processes.size(); i++) {
				process = processes.get(i);
				if (i == count) {
					failedProcId = process.getProcId();
				}
				ps.setString(1, IConstants.PROC_STATUS_FAILURE);
				ps.setInt(2, process.getProcId());
				ps.setString(3, "Process failed due to the Processes ID "+failedProcId);
				ps.setString(4, IConstants.CREATED_USER_ID);
				ps.setString(5, IConstants.LAST_UPDATED_USER_ID);
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			LOGGER.error("Exception Occured while creating new process log for failed processes", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("Exception Occured while creating new process log for failed processes", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
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
	}

	@Override
	public void updateFailedProcs(List<SchedulerProcess> processes, int count) {
		final String methodName = "updateFailedProcs";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		SchedulerProcess process = null;
		int failedProcId = 0;
		try {
			con = conFactory.getConnection();			
			String sqlQuery = ICommonQueryConstants.UPDATE_FAILED_PROCS;
			ps = con.prepareStatement(sqlQuery);
			for (int i = count; i < processes.size(); i++) {
				process = processes.get(i);
				if (i == count) {
					failedProcId = process.getProcId();
				}
				ps.setString(1, IConstants.TRIGGER_USER_INITIATION);
				ps.setString(2, "Process failed due to the Processes ID "+failedProcId);
				ps.setString(3, IConstants.LAST_UPDATED_USER_ID);
				ps.setInt(4, process.getProcId());
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			LOGGER.error("Exception Occured while updating the process for failed processes", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("Exception Occured while updating the process for failed processes", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
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
	}
	/*public ProgramDefinition retrivePaymentDefinition (int processID){
    	final String methodName = "retrivePaymentDefinition";
		LOGGER.enter(CLASSNAME, methodName);
		ProgramDefinition pDefinition = new ProgramDefinition();		
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conFactory.getConnection();
			String query = "select file.ID_DPB_FILE,"
					+ " fmt.ID_DPB_FILE_FMT,fmt.NAM_DPB_FIL_FMT, fmt.IND_INBND_FILE,fmt.IND_HDR,fmt.TXT_DLM,fmt.CNT_COL ,"
					+ " Trim(file.TXT_DPB_BASE_DPTH)||'\\'||Trim(file.NAM_DPB_FIL) as path ,file.NAM_DPB_FIL,"
					+ " file.TXT_DPB_BASE_DPTH "
					+ " from DPB_PROC proc, DPB_FILE file, FILE_FMT fmt "
					+ " where proc.ID_DPB_PROC = ? "
					+ " and proc.CDE_DPB_PROC_TYP = 'PF'"
					+ " and file.ID_DPB_FILE = proc.ID_DPB_PGM and file.CDE_DPB_STS = 'A'"
					+ " and fmt.ID_DPB_FILE_FMT = file.ID_DPB_FILE_FMT with ur";
			ps = con.prepareStatement(query);
			ps.setInt(1, processID);
			rs = ps.executeQuery();
		
			
			while (rs.next()) {
				
			}
		} catch (SQLException e) {
			LOGGER.error(
					"Exception Occured while getting the file definition from DB",
					e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error(
					"Exception Occured while getting the file definition from DB",
					ne);
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
		return pDefinition;
    }*/
	public List<BonusInfo> retrievePaymentData(String paymentType,Date startDate,Date endDate,boolean ldrsp,Integer pgmId)
	{
		final String methodName = "retrievePaymentData";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<BonusInfo> dlrBnsList = null;
		BonusInfo  dlrBns = null;			
		try
		{		
			con  = conFactory.getConnection();
			dlrBnsList = new ArrayList<BonusInfo>();
			if (con != null) {	
				if(ldrsp){
					preparedStatement = con.prepareStatement(ICommonQueryConstants.RETRIVR_LDRSHIP_DLR_PAYMENT);
				}else{
					preparedStatement = con.prepareStatement(ICommonQueryConstants.RETRIVR_DLR_PAYMENT);
				}
				preparedStatement.setDate(1, startDate);
				preparedStatement.setDate(2, endDate);
				LOGGER.info("***************retrievePaymentData:"+ICommonQueryConstants.RETRIVR_DLR_PAYMENT);
				LOGGER.info("**startDate:"+startDate+":endDate:"+endDate+":paymentType:"+paymentType+":pgmId:"+pgmId);
				LOGGER.info("***********************************************************************************");
				if(!ldrsp){					
					preparedStatement.setString(3, paymentType);
					preparedStatement.setInt(4, pgmId);
					//preparedStatement.setDate(5, startDate);
					//preparedStatement.setDate(6, endDate);
				} else {
					preparedStatement.setInt(3, pgmId);
				}
				
				resultSet = preparedStatement.executeQuery();											
				while(resultSet.next()){					
					dlrBns = new BonusInfo();
					//ID_DPB_UNBLK_VEH			
					dlrBns.setPoNumber(resultSet.getString("NUM_PO"));				
					dlrBns.setDlrId(resultSet.getString("ID_DLR"));
					dlrBns.setPgmId(resultSet.getInt("ID_DPB_PGM"));
					dlrBns.setBnsCalcAmt(resultSet.getDouble("SUM_CALC"));	
					dlrBns.setPgmName(resultSet.getString("PGM_NAME"));
					dlrBns.setModel(resultSet.getString("DES_MODL"));
					dlrBns.setVehicleIdentificationNum(resultSet.getString("NUM_VIN"));
					dlrBns.setYear(resultSet.getString("DTE_MODL_YR"));				
					dlrBns.setVehicleType(resultSet.getString("CDE_VEH_TYP"));
					dlrBns.setCdeVehDdrSts(resultSet.getString("CDE_VEH_DDR_STS"));
					dlrBns.setCdeVehDdrUse(resultSet.getString("CDE_VEH_DDR_USE"));
					dlrBns.setActualDate(resultSet.getDate("DTE_CAL"));	
					dlrBns.setCreditAcctId(resultSet.getString("ID_DPB_CFC_ACCT_CR"));	
					dlrBns.setDebitAcctId(resultSet.getString("ID_DPB_CFC_ACCT_DT"));	
							
					dlrBnsList.add(dlrBns);
				}
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.COMMON.DAO.PAYMENT.001",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.COMMON.DAO.PAYMENT.002",ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("DPB.COMMON.DAO.PAYMENT.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, "genPayFile()");	
		return dlrBnsList;
	}
	/*public RtlCalenderDefinition getRetailCalenderForActualDate(Integer dayID) {
		final String methodName = "getRetailCalenderEndDate()";
		LOGGER.enter(CLASSNAME, methodName);		
		RtlCalenderDefinition rtlCal = new RtlCalenderDefinition();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try {			 
			con = conFactory.getConnection();
			ps = con.prepareStatement(ICommonQueryConstants.RETRIVE_DIMENION_INFO_BASED_ON_DAY_ID);
			System.err.println("***************getRetailCalenderForActualDate:"+ICommonQueryConstants.RETRIVE_DIMENION_INFO_BASED_ON_DAY_ID);
			System.err.println("***********************************************************************************");
			
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
	public Integer getRetailCalenderId(Date cDate) {
		final String methodName = "getRetailCalenderEndDate()";
		LOGGER.enter(CLASSNAME, methodName);		
		Integer dayId =  null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try {			 
			con = conFactory.getConnection();
			ps = con.prepareStatement(IMasterInfoLookQueryConstants.RETRIVE_CALENDER_INFO);
			rs = ps.executeQuery();
			while (rs.next()) {
				dayId = rs.getInt("ID_DAY");
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
		return dayId;
	}
	/**
	 * This method return list of dealer terminated on given date.
	 * 
	 * This method is used bonus calculation getting executed from scheduler 
	 * as well as application when click on start process button.
	 *  
	 * @param List<DealerInfo>
	 * @return
	 */
	public List<DealerInfo> getTerminatedDealerList(Date rtlDate)
	{		
		final String methodName = "getTerminatedDealerList()";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		DealerInfo dealerInfo =  new DealerInfo();
		List<DealerInfo> terminateDlrList =  new ArrayList<DealerInfo>();
		try {
			con = conFactory.getConnection();	
			String query= ICommonQueryConstants.GET_TERMINATED_DEALER;	
			LOGGER.info("***************getTerminatedDealerList:"+query+":rtlDate:"+rtlDate);
			
			ps = con.prepareStatement(query);
			ps.setDate(1, rtlDate);
			rs= ps.executeQuery();		
			while(rs.next()){
				dealerInfo =  new DealerInfo();
				dealerInfo.setDealerId(rs.getString("ID_DLR"));				
				dealerInfo.setDlrAbbrText(rs.getString("CDE_DLR_SHT_NAM"));
				dealerInfo.setDlrName(rs.getString("NAM_DLR"));
				dealerInfo.setDlrNameDba(rs.getString("NAM_DO_BUSN_AS"));
				dealerInfo.setDlrCity(rs.getString("ADR_PRIM_CITY"));
				dealerInfo.setDlrState(rs.getString("CDE_DLR_ST"));
				dealerInfo.setDlrTerminatedDate(rs.getTimestamp("DTE_MBUSA_TERM"));	
				terminateDlrList.add(dealerInfo);
			}		
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.TERMINATE.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.TERMINATE.002", ne);
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
				LOGGER.error("DPB.BNS.TERMINATE.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return terminateDlrList;
	}
	public List<String> getDealerFranchises(String dealerId)
	{		
		LOGGER.enter(CLASSNAME, "getDealerFranchises()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;		
		List<String> dlrFranchisesList =  new ArrayList<String>();
		try {
			con = conFactory.getConnection();	
			String query= ICommonQueryConstants.GET_DEALER_FRANCHISES_LIST;		
			LOGGER.info("Dealer Franchises query:"+query);
			ps = con.prepareStatement(query);
			ps.setString(1, dealerId);
			rs= ps.executeQuery();

			while(rs.next()){
				dlrFranchisesList.add(rs.getString(1));					
			}		
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.DLR.FRANCHISES.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.DLR.FRANCHISES.002", ne);
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
				LOGGER.error("DPB.BNS.DLR.FRANCHISES.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}	
		return dlrFranchisesList;
	}
	public List<BonusInfo> getBonusDetailsOnPONumber(String dealerId )
	{		
		LOGGER.enter(CLASSNAME, "getBonusDetailsOnPONummber()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		BonusInfo bnsCalInfo =  new BonusInfo();
		List<BonusInfo> bonusDtlsList =  new ArrayList<BonusInfo>();
		try {
			con = conFactory.getConnection();	
			String query= ICommonQueryConstants.GET_BONUS_INFO_ON_PO_NUMBER;
			LOGGER.info("getBonusDetailsOnPONumber:"+query +":dealerId:"+dealerId);
			ps = con.prepareStatement(query);
			ps.setString(1, dealerId);
		
			rs= ps.executeQuery();
			while(rs.next()){
				bnsCalInfo =  new BonusInfo();				
				bnsCalInfo.setDlrId(dealerId);				
				bnsCalInfo.setProcessId(rs.getInt("ID_DPB_PROC"));
				bnsCalInfo.setDpbUnblkVehId(rs.getInt("ID_DPB_UNBLK_VEH"));
				bnsCalInfo.setPgmId(rs.getInt("ID_DPB_PGM"));
				bnsCalInfo.setBnsCalcAmt(rs.getDouble("TOT"));
				bnsCalInfo.setMaxBnsEligibleAmt(rs.getDouble("AMT_DPB_MAX_BNS_ELG"));
				bnsCalInfo.setAdjIndicator(rs.getString("IND_DPB_ADJ"));
				bnsCalInfo.setActualDate(rs.getDate("DTE_CAL"));				
				bonusDtlsList.add(bnsCalInfo);
			}		
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.DTL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.DTL.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}catch(Exception  ne){
			LOGGER.error("DPB.BNS.DTL.003", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}
		finally {
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
				LOGGER.error("DPB.BNS.KPI.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return bonusDtlsList;
	}
	public void insertBonusCalculationRecord(List<BonusInfo> bnsCalCulatedList,boolean ldrsp)
	{		
		LOGGER.enter(CLASSNAME, "createBonusCalculationRecord()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		final int batchSize = 50;
		int count = 0;
		String query = null;
		try {
			con = conFactory.getConnection();	
					if(ldrsp) {
						query= ICommonQueryConstants.INSERT_BONUS_INFO_FR_TERMINATE_DEALER_LDRSP;
					} else {
						 query= ICommonQueryConstants.INSERT_BONUS_INFO_FR_TERMINATE_DEALER;	
					}				
			if(bnsCalCulatedList!= null && bnsCalCulatedList.size() > 0){	
				int updateCount = 0;
				Iterator<BonusInfo> bonuscalListIterator = bnsCalCulatedList.iterator();
				ps = con.prepareStatement(query);
				while(bonuscalListIterator.hasNext()){
					BonusInfo bnsInfo = bonuscalListIterator.next();				
					ps.setInt(1,bnsInfo.getProcessId());	
					ps.setInt(2,bnsInfo.getDpbUnblkVehId());	
					ps.setString(3,bnsInfo.getDlrId());
					ps.setDate(4,bnsInfo.getActualDate());
					ps.setInt(5, bnsInfo.getPgmId());					
					ps.setDouble(6,bnsInfo.getBnsCalcAmt());
					ps.setDouble(7,bnsInfo.getMaxBnsEligibleAmt());
					ps.setString(8,bnsInfo.getAdjIndicator());
					ps.setString(9, bnsInfo.getStatus());
					//Unearned Performance Bonus calculation start - Kshitija
					ps.setDouble(10, bnsInfo.getAmtUnernd());
					//Unearned Performance Bonus calculation end - Kshitija
					ps.setString(11,bnsInfo.getUserId());
					ps.setString(12,bnsInfo.getUserId());
					//ps.setInt(13, bnsInfo.getParentPgmId());					
					ps.addBatch();
					updateCount ++ ;
					if(updateCount == 500){
						ps.executeBatch();
						updateCount = 0;
					}					
				}
				if(updateCount > 0){
						ps.executeBatch();
				}
			}
		}catch(SQLException  e){
			e.getNextException();
			LOGGER.error("DPB.BNS.RECORD.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(Exception  ne){
			LOGGER.error("DPB.BNS.RECORD.002", ne);
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
				LOGGER.error("DPB.BNS.RECORD.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
	}	
	@Override
	public List<VistaFileProcessing> getNonSettledVehicleDtlsForTerminatedDlr(String dlrId,java.sql.Date startDate, java.sql.Date endDate,List<String> pVehList) {
		LOGGER.enter(CLASSNAME, "getNonSettledVehicleDtlsForTerminatedDlr()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		VistaFileProcessing vistaRtl = null;
		List<VistaFileProcessing> vistaRtlList =  new ArrayList<VistaFileProcessing>();
		try {
			con = conFactory.getConnection();	
					
			int lastIndex = 0;
			StringBuffer query = new StringBuffer(ICommonQueryConstants.GET_NON_SETTLED_VISTA_RETAIL_DATA);		
			for (int j=1; j< pVehList.size() ;j++)
			{
				lastIndex = query.lastIndexOf("?");
				query.insert(lastIndex + 1, ",?");
			}
			LOGGER.info("getNonSettledVehicleDtlsForTerminatedDlr:"+query);
			LOGGER.info("DLR:"+dlrId+"It startDate:"+startDate+":endDate:"+endDate);
			ps = con.prepareStatement(query.toString());
			ps.setString(1, dlrId);
			ps.setDate(2, startDate);
			ps.setDate(3, endDate);
			for(int k = 0;k < pVehList.size();k++)
			{
				ps.setString(k+4, (String)pVehList.get(k));
			}
			rs= ps.executeQuery();

			while(rs.next()){
				vistaRtl =  new VistaFileProcessing();
				vistaRtl.setPoNo(rs.getString("NUM_PO"));
				vistaRtl.setVinNum(rs.getString("NUM_VIN"));
				vistaRtl.setRetailDate(rs.getDate("DTE_RTL"));
				vistaRtl.setRetailTime(rs.getTime("TME_RTL"));
				vistaRtl.setVehStatusCode(rs.getString("CDE_VEH_STS"));
				vistaRtl.setDealerId(rs.getString("ID_DLR"));
				vistaRtl.setUsedCarIndicator(rs.getString("IND_USED_VEH"));				
				vistaRtl.setEmpPurCtrlId(rs.getString("ID_EMP_PUR_CTRL"));
				vistaRtl.setModelYearDate(rs.getString("DTE_MODL_YR"));
				vistaRtl.setModelText(rs.getString("DES_MODL"));				
				vistaRtl.setRegionCode(rs.getString("CDE_RGN"));
				vistaRtl.setRetailCustLastName(rs.getString("NAM_RTL_CUS_LST"));
				vistaRtl.setRetailCustFirstName(rs.getString("NAM_RTL_CUS_FIR"));
				vistaRtl.setRetailCustMiddleName(rs.getString("NAM_RTL_CUS_MID"));
				vistaRtl.setTransDate(rs.getDate("DTE_TRANS"));
				vistaRtl.setTransTime(rs.getTime("TME_TRANS"));
				//CDE_NATL_TYPE			ID_DPB_PGM				
				vistaRtl.setMsrpBaseAmount(rs.getDouble("AMT_MSRP_BASE"));
				vistaRtl.setMsrpTotalsAmt(rs.getDouble("AMT_MSRP"));
				vistaRtl.setMsrpTotAmtAcsry(rs.getDouble("AMT_MSRP_TOT_ACSRY"));
				vistaRtl.setDlrRebateAmt(rs.getDouble("AMT_DLR_RBT"));
				vistaRtl.setFleetIndicator(rs.getString("IND_FLT"));
				vistaRtl.setWholeSaleInitType(rs.getString("CDE_WHSLE_INIT_TYP"));
				vistaRtl.setUseCode(rs.getString("CDE_USE_VEH"));
				vistaRtl.setVehTypeCode(rs.getString("CDE_VEH_TYP"));
				vistaRtl.setProgType(rs.getInt("ID_DPB_PGM"));
				vistaRtl.setCarStatusCode(rs.getString("CDE_VEH_DDR_STS")); 
				vistaRtl.setSalesTypeCode(rs.getString("CDE_VEH_DDR_USE"));
				vistaRtl.setDemoServiceDate(rs.getDate("DTE_VEH_DEMO_SRV"));
				vistaRtl.setUsedCarIndicator2(rs.getString("IND_USED_VEH_DDRS"));
				vistaRtlList.add(vistaRtl);
			}			
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.VISTA.RTL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.VISTA.RTL.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}catch(Exception  ne){
			LOGGER.error("DPB.BNS.VISTA.RTL.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}
		finally {
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
				LOGGER.error("DPB.BNS.VISTA.RTL.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return vistaRtlList;
	}

	@Override
	public List<ProgramDefinition> getActiveProgramsForVehicle(VistaFileProcessing vistaFileProcessing) {
		LOGGER.enter(CLASSNAME, "getActiveProgramsForVehicle()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		ProgramDefinition progDef = null;
		List<ProgramDefinition> activePgmVeh =  new ArrayList<ProgramDefinition>();
		try {
			con = conFactory.getConnection();	
			String query= ICommonQueryConstants.GET_ACT_PGM_VEH;		

			ps = con.prepareStatement(query);
			Calendar cal = Calendar.getInstance();
			ps.setDate(1, new java.sql.Date(cal.getTimeInMillis()));
			rs= ps.executeQuery();

			while(rs.next()){
				progDef =  new ProgramDefinition();

				progDef.setProgramId(rs.getInt("ID_DPB_PGM"));
				progDef.setKpiId(rs.getInt("ID_KPI"));
				progDef.setSpecialProgram(rs.getString("IND_SPL_DPB_PGM"));

				activePgmVeh.add(progDef);
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.VISTA.RTL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.VISTA.RTL.002", ne);
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
				LOGGER.error("DPB.BNS.VISTA.RTL.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return activePgmVeh;
	}
	@Override
	public List<KpiFile> retrieveInnerQuarterAdjustList(String dealerId, String quarter, int year) {
		LOGGER.enter(CLASSNAME, "retrieveInnerQuarterAdjustList()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		List<KpiFile> updatedKpiLst =  new ArrayList<KpiFile>();
		KpiFile kpiFile = null;
		try {
			con = conFactory.getConnection();	
			//String query = ICommonQueryConstants.GET_IN_QUARTER_ADJUST_LST;
			//ps = con.prepareStatement(query);
			ps.setString(1, dealerId);
			ps.setString(2, quarter);
			ps.setInt(3, year);
			rs= ps.executeQuery();
			while(rs.next()){
				kpiFile =  new KpiFile();
				kpiFile.setDealerId(dealerId);
				kpiFile.setKpiId(rs.getInt("ID_KPI"));
				kpiFile.setKpiFiscalQuarter(quarter);
				kpiFile.setKpiPct(rs.getDouble("PCT_KPI"));
				kpiFile.setKpiYr(rs.getInt(year));
				updatedKpiLst.add(kpiFile);
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.VISTA.RTL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.VISTA.RTL.002", ne);
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
				LOGGER.error("DPB.BNS.VISTA.RTL.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return updatedKpiLst;
	}


	@Override
	public List<BonusInfo> retrieveCancelledPoList() {
		LOGGER.enter(CLASSNAME, "retrieveCancelledPoList()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;		
		List<BonusInfo> cancelBonusList = new ArrayList<BonusInfo>();
		BonusInfo cancelBns =  null;
		try {
			con = conFactory.getConnection();	
			String query= ICommonQueryConstants.CANCELLED_BONUS_RECORD_LIST;
			ps = con.prepareStatement(query);
			Calendar cal = Calendar.getInstance();
			Date sdate = new java.sql.Date(cal.getTimeInMillis());
			Date edate = new java.sql.Date(cal.getTimeInMillis());
			LOGGER.info("sdate:"+sdate+"edate:"+edate);
			ps.setDate(1, sdate);
			ps.setDate(2, edate);
			rs= ps.executeQuery();
			while(rs.next()){
				cancelBns = new BonusInfo();
				cancelBns.setProcessId(rs.getInt("ID_DPB_PROC"));
				cancelBns.setPgmId(rs.getInt("ID_PGM"));
				//cancelBns.setPoNumber(rs.getString("NUM_PO"));
				cancelBns.setDlrId(rs.getString("ID_DLR"));				
				cancelBns.setBnsCalcAmt(rs.getDouble("AMT_DPB_BNS_CALC"));
				cancelBns.setMaxBnsEligibleAmt(rs.getDouble("AMT_DPB_MAX_BNS_ELG"));
				cancelBns.setAdjIndicator(rs.getString("IND_DPB_ADJ"));
				cancelBns.setStatus(rs.getString("CDE_DPB_CALC_STS"));				
				cancelBonusList.add(cancelBns);
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.CANCEL.RTL.PO.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.CANCEL.RTL.PO.002", ne);
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
				LOGGER.error("DPB.CANCEL.RTL.PO.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return cancelBonusList;
	}

	@Override
	public Map<String, List<ProgramDefinition>> retrieveDayVehicleProgramMap(Date rtlDate ) {
		LOGGER.enter(CLASSNAME, "retrieveDayVehicleProgramMap()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
        boolean trigger = false;
		Map<String, List<ProgramDefinition>> dayVehicleProgramMap =  new HashMap<String, List<ProgramDefinition>>();
		List<ProgramDefinition> progDefLst = null;
		try {
			con = conFactory.getConnection();	
			String query = null;
			if(trigger){
				query= ICommonQueryConstants.GET_VEHICLE_PROG_MAP_AUTO;
			}
			else{
				query= ICommonQueryConstants.GET_VEHICLE_PROG_MAP_MANUAL;	
			}
			LOGGER.info("Query - retrieveDayVehicleProgramMap:"+query+":rtlDate:"+rtlDate);
			ps = con.prepareStatement(query);
			ps.setDate(1, rtlDate);
			rs= ps.executeQuery();
			String vehicleType = null;

			while(rs.next()){
				vehicleType = rs.getString("CDE_VEH_TYP");				  
				ProgramDefinition progDef = new ProgramDefinition();
				progDef.setProcessId(rs.getInt("ID_DPB_PROC"));
				progDef.setProcessType(rs.getString("CDE_DPB_PROC_TYP"));
				progDef.setProgramId(rs.getInt("ID_DPB_PGM"));
				progDef.setProgramName(rs.getString("NAM_DPB_PGM"));
				progDef.setDescription(rs.getString("PGM_NAME"));
				progDef.setStartDate(rs.getDate("DTE_DPB_PGM_START"));
				progDef.setEndDate(rs.getDate("DTE_DPB_PGM_END"));
				progDef.setRebateAmt(rs.getString("IND_DPB_RBT_PMT"));
				progDef.setCommPayment(rs.getString("IND_DPB_COMSN_PMT"));
				progDef.setCommAmount(rs.getDouble("AMT_DPB_COMSN_PMT"));
				progDef.setFixedPayment(rs.getDouble("PCT_FXD_PMT"));				
				progDef.setComsnProcessSchedule(rs.getString("CDE_COMSN_BNS_PROC_SCHD"));
				progDef.setComsnProcessTrigger(rs.getString("CDE_COMSN_PROC_INIT_TYP"));				
				progDef.setComsnPymtSchedule(rs.getString("CDE_COMSN_PMT_SCHD"));
				progDef.setComsnPymtTrigger(rs.getString("CDE_COMSN_PMT_PROC_INIT_TYP"));				

				progDef.setFixedPymtBonusSchedule(rs.getString("CDE_FXD_BNS_SCHD"));
				progDef.setFixedPymtBonusTrigger(rs.getString("CDE_FXD_BNS_PROC_INIT_TYP"));
				progDef.setFixedPymtSchedule(rs.getString("CDE_FXD_PMT_SCHD"));
				progDef.setFixedPymtTrigger(rs.getString("CDE_FXD_PMT_PROC_INIT_TYP"));
				
				progDef.setPaymentType(rs.getString("IND_DPB_FXD_PMT"));
				progDef.setMaxVarPayment(rs.getString("IND_MAX_VAR_PMT"));
				progDef.setVariablePayment(rs.getDouble("PCT_VAR_PMT"));
								
				progDef.setVariablePymtBonusSchedule(rs.getString("CDE_VAR_BNS_SCHD"));
				progDef.setVariablePymtBonusTrigger(rs.getString("CDE_VAR_BNS_PROC_INIT_TYP"));
				progDef.setVariablePymtSchedule(rs.getString("CDE_VAR_PMT_SCHD"));
				progDef.setVariablePymtTrigger(rs.getString("CDE_VAR_PMT_PROC_INIT_TYP"));
				progDef.setSpecialProgram(rs.getString("CDE_DPB_PGM_TYP"));
				progDef.setKpiId(rs.getInt("ID_KPI"));
				progDef.setProgramStatus(rs.getString("CDE_DPB_STS"));
								
				if(trigger){
					progDef.setActlProcDte(rs.getDate("DTE_DPB_ACTL_PROC"));
				}
				else{
					if(rs.getInt("DTE_PROC_OVRD") > 0){
						progDef.setActlProcDte(rs.getDate("DTE_PROC_OVRD"));
					}
					else {
						progDef.setActlProcDte(rs.getDate("DTE_DPB_ACTL_PROC"));
					}
				}
				if(dayVehicleProgramMap.containsKey(vehicleType)){
					dayVehicleProgramMap.get(vehicleType).add(progDef);
				}
				else{
					progDefLst = new ArrayList<ProgramDefinition>();
					progDefLst.add(progDef);
					dayVehicleProgramMap.put(vehicleType, progDefLst);
				}
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.VISTA.RTL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.VISTA.RTL.002", ne);
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
				LOGGER.error("DPB.BNS.VISTA.RTL.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return dayVehicleProgramMap;
	}
	
	public Map<String, List<ProgramDefinition>> retrieveVehicleProgramMap(String vehicleType, String paymentType) {
		LOGGER.enter(CLASSNAME, "retrieveVehicleProgramMap()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
        boolean trigger = false;
		Map<String, List<ProgramDefinition>> vehicleProgramMap =  new HashMap<String, List<ProgramDefinition>>();
		List<ProgramDefinition> progDefLst = null;
		try {
			con = conFactory.getConnection();	
			String query = null;
			query= ICommonQueryConstants.GET_GEN_PGM_BY_VEH;

			LOGGER.info("************************retrieveVehicleProgramMap:"+query);
			LOGGER.info("****************vehicleType:"+vehicleType+":paymentType:"+paymentType);
			ps = con.prepareStatement(query);
			ps.setString(1, vehicleType);
			ps.setString(2, paymentType);			
			rs= ps.executeQuery();


			while(rs.next()){

				//vehicleType = rs.getString("CDE_VEH_TYP");

				ProgramDefinition progDef = new ProgramDefinition();
				progDef.setProgramId(rs.getInt("ID_DPB_PGM"));
				progDef.setProgramName(rs.getString("NAM_DPB_PGM"));//NAM_DPB_PGM
				progDef.setDescription(rs.getString("DES_DPB_PGM"));
				progDef.setStartDate(rs.getDate("DTE_DPB_PGM_START"));
				progDef.setEndDate(rs.getDate("DTE_DPB_PGM_END"));
				progDef.setRebateAmt(rs.getString("IND_DPB_RBT_PMT"));
				progDef.setCommPayment(rs.getString("IND_DPB_COMSN_PMT"));
				progDef.setCommAmount(rs.getDouble("AMT_DPB_COMSN_PMT"));
				progDef.setFixedPayment(rs.getDouble("PCT_FXD_PMT"));
				progDef.setFixedPymtBonusSchedule(rs.getString("CDE_FXD_BNS_SCHD"));
				progDef.setFixedPymtBonusTrigger(rs.getString("CDE_FXD_BNS_PROC_INIT_TYP"));
				progDef.setFixedPymtSchedule(rs.getString("CDE_FXD_PMT_SCHD"));
				progDef.setFixedPymtTrigger(rs.getString("CDE_FXD_PMT_PROC_INIT_TYP"));
				progDef.setPaymentType(rs.getString("IND_DPB_FXD_PMT"));
				progDef.setMaxVarPayment(rs.getString("IND_MAX_VAR_PMT"));
				progDef.setVariablePayment(rs.getDouble("PCT_VAR_PMT"));
				progDef.setVariablePymtBonusSchedule(rs.getString("CDE_VAR_BNS_SCHD"));
				progDef.setVariablePymtBonusTrigger(rs.getString("CDE_VAR_BNS_PROC_INIT_TYP"));
				progDef.setVariablePymtSchedule(rs.getString("CDE_VAR_PMT_SCHD"));
				progDef.setVariablePymtTrigger(rs.getString("CDE_VAR_PMT_PROC_INIT_TYP"));
				progDef.setSpecialProgram(rs.getString("CDE_DPB_PGM_TYP"));
				progDef.setKpiId(rs.getInt("ID_KPI"));
				progDef.setProgramStatus(rs.getString("CDE_DPB_STS"));
				

				if(vehicleProgramMap.containsKey(vehicleType)){
					vehicleProgramMap.get(vehicleType).add(progDef);
				}
				else{
					progDefLst = new ArrayList<ProgramDefinition>();
					progDefLst.add(progDef);
					vehicleProgramMap.put(vehicleType, progDefLst);
				}
			}
			LOGGER.info("********>>>>>vehicleType:"+vehicleType+":paymentType:"+paymentType+"*********RetrieveVehicleProgramMap:"+query+":Size of Program:"+vehicleProgramMap.size());
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.VISTA.RTL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.VISTA.RTL.002", ne);
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
				LOGGER.error("DPB.BNS.VISTA.RTL.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return vehicleProgramMap;
	}
	@Override
	public KpiFile retrieveKpiFileListForDealer(String dealerId,String period, int year, int kpiId) {
		LOGGER.enter(CLASSNAME, "retrieveKpiFileListForDealer()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		KpiFile kpiFile = null;
		//List<KpiFile> kpiFileList =  new ArrayList<KpiFile>();
		try {
			con = conFactory.getConnection();	
			String query= ICommonQueryConstants.GET_KPI_FILE;		

			ps = con.prepareStatement(query);
			/*  For Old Query
			ps.setString(1, dealerId);
			ps.setString(2, period);
			ps.setInt(3, year);
			*/
			ps.setInt(1, year);
			ps.setString(2, period);
			ps.setInt(3, kpiId);
			ps.setString(4, dealerId);
			rs= ps.executeQuery();
			LOGGER.info("************ Query -->retrieveKpiFileListForDealer:"+query);
			while(rs.next()){
				kpiFile =  new KpiFile();
				kpiFile.setDealerId(dealerId);
				kpiFile.setKpiId(rs.getInt("ID_KPI"));
				kpiFile.setKpiFiscalQuarter(rs.getString("DTE_FSCL_QTR"));				
				kpiFile.setKpiYr(rs.getInt("DTE_FSCL_YR"));
				kpiFile.setKpiPct(rs.getDouble("PCT_KPI"));
				//kpiFileList.add(kpiFile);
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
		return kpiFile;
	}
	public ProgramDefinition retrivePaymentDefinition(int processID) {
		final String methodName = "retrivePaymentDefinition";
		LOGGER.enter(CLASSNAME, methodName);
		ProgramDefinition pDefinition = new ProgramDefinition();		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			con = conFactory.getConnection();			
			ps = con.prepareStatement(ICommonQueryConstants.RETREIVE_PROCESS_DEFINITION_DETAIL);
			ps.setInt(1, processID);
			rs = ps.executeQuery();
		
			while (rs.next()) {
				pDefinition.setProcessId(rs.getInt("ID_DPB_PROC"));
				pDefinition.setPrcDefID(rs.getInt("ID_DPB_APP_ENT"));
				pDefinition.setProcDte(rs.getDate("DTE_CAL"));
				pDefinition.setActlProcDte(rs.getDate("DTE_DPB_ACTL_PROC"));
				pDefinition.setProcessType(rs.getString("CDE_DPB_PROC_TYP"));
				pDefinition.setIdOverProcDte(rs.getInt("DTE_PROC_OVRD"));
				pDefinition.setTmeDpbOvrdTrgr(rs.getString("TME_PROC_OVRD"));
				pDefinition.setOvrdTrgr(rs.getString("CDE_DPB_OVRD_TRGR"));
				//pDefinition.set(rs.getInt("TXT_RSN_PROC_UPD"));
				//pDefinition.set(rs.getInt("CDE_FXD_BNS_SCHD"));
				//pDefinition.setProcessId(rs.getInt("CDE_FXD_BNS_TRGR"));
				

				pDefinition.setComsnPymtSchedule(rs.getString("CDE_COMSN_PMT_SCHD"));
				pDefinition.setComsnPymtTrigger(rs.getString("CDE_COMSN_PMT_TRGR"));
				
				pDefinition.setFixedPymtSchedule(rs.getString("CDE_FXD_PMT_SCHD"));
				pDefinition.setFixedPymtTrigger(rs.getString("CDE_FXD_PMT_TRGR"));
				//pDefinition.setProcessId(rs.getInt("CDE_VAR_BNS_SCHD"));
				//pDefinition.setProcessId(rs.getInt("CDE_VAR_BNS_TRGR"));
				pDefinition.setVariablePymtSchedule(rs.getString("CDE_VAR_PMT_SCHD"));
				pDefinition.setVariablePymtTrigger(rs.getString("CDE_VAR_PMT_TRGR"));
				pDefinition.setSpecialProgram(rs.getString("IND_SPL_DPB_PGM"));
				
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.RETREIVE.PAYMENT.001",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.RETREIVE.PAYMENT.002",ne);
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
				LOGGER.error("DPB.RETREIVE.PAYMENT.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return pDefinition;
	}
	
	public Map<String, List<ProgramDefinition>> retrieveProgramDefinitionVehicleProgramMap(Integer pId ) {
		LOGGER.enter(CLASSNAME, "retrieveProgramDefinitionVehicleProgramMap()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;

		Map<String, List<ProgramDefinition>> dayVehicleProgramMap =  new HashMap<String, List<ProgramDefinition>>();
		List<ProgramDefinition> progDefLst = null;
		try {
			con = conFactory.getConnection();	
			String query = null;
			boolean trigger = false;
			if(trigger){
				query= ICommonQueryConstants.GET_VEHICLE_PROG_MAP_AUTO;
			}
			else{
				query= ICommonQueryConstants.GET_VEHICLE_PROG_MAP_MANUAL;	
			}
			
			ps = con.prepareStatement(query);
			ps.setInt(1, pId);
			rs= ps.executeQuery();
			String vehicleType;

			while(rs.next()){
				//DTE_PROC_OVRD,TME_PROC_OVRD
				vehicleType = rs.getString("CDE_VEH_TYP");

				ProgramDefinition progDef = new ProgramDefinition();
				progDef.setProgramId(rs.getInt("ID_DPB_PGM"));
				progDef.setProgramName(rs.getString("NAM_DPB_PGM"));
				progDef.setDescription(rs.getString("PGM_NAME"));
				progDef.setStartDate(rs.getDate("DTE_DPB_PGM_START"));
				progDef.setEndDate(rs.getDate("DTE_DPB_PGM_END"));
				progDef.setRebateAmt(rs.getString("IND_DPB_RBT_PMT"));
				progDef.setCommPayment(rs.getString("IND_DPB_COMSN_PMT"));
				progDef.setCommAmount(rs.getDouble("AMT_DPB_COMSN_PMT"));
				
				//progDef.setCommPercent(rs.getDouble("PCT_DPB_COMSN_PMT"));
				progDef.setFixedPayment(rs.getDouble("PCT_FXD_PMT"));
				
				progDef.setComsnProcessSchedule(rs.getString("CDE_COMSN_BNS_SCHD"));
				progDef.setComsnProcessTrigger(rs.getString("CDE_COMSN_BNS_TRGR"));
				progDef.setComsnPymtSchedule(rs.getString("CDE_COMSN_PMT_SCHD"));
				progDef.setComsnPymtTrigger(rs.getString("CDE_COMSN_PMT_TRGR"));

				progDef.setFixedPymtBonusSchedule(rs.getString("CDE_FXD_BNS_SCHD"));
				progDef.setFixedPymtBonusTrigger(rs.getString("CDE_FXD_BNS_TRGR"));
				progDef.setFixedPymtSchedule(rs.getString("CDE_FXD_PMT_SCHD"));
				progDef.setFixedPymtTrigger(rs.getString("CDE_FXD_PMT_TRGR"));
				progDef.setPaymentType(rs.getString("IND_DPB_FXD_PMT"));
				progDef.setMaxVarPayment(rs.getString("IND_MAX_VAR_PMT"));
				progDef.setVariablePayment(rs.getDouble("PCT_VAR_PMT"));
				progDef.setVariablePymtBonusSchedule(rs.getString("CDE_VAR_BNS_SCHD"));
				progDef.setVariablePymtBonusTrigger(rs.getString("CDE_VAR_BNS_TRGR"));
				progDef.setVariablePymtSchedule(rs.getString("CDE_VAR_PMT_SCHD"));
				progDef.setVariablePymtTrigger(rs.getString("CDE_VAR_PMT_TRGR"));
				progDef.setSpecialProgram(rs.getString("IND_SPL_DPB_PGM"));
				progDef.setKpiId(rs.getInt("ID_KPI"));
				progDef.setProgramStatus(rs.getString("CDE_DPB_STS"));
				progDef.setPrcDefID(rs.getInt("ID_DPB_PROC"));
				progDef.setProcessType(rs.getString("CDE_DPB_PROC_TYP"));
				if(trigger){
					progDef.setActlProcDte(rs.getDate("DTE_DPB_ACTL_PROC"));
				}
				else{
					if(rs.getInt("DTE_PROC_OVRD") > 0){
						progDef.setActlProcDte(rs.getDate("DTE_PROC_OVRD"));
					}
					else {
						progDef.setActlProcDte(rs.getDate("DTE_DPB_ACTL_PROC"));
					}
				}

				if(dayVehicleProgramMap.containsKey(vehicleType)){
					dayVehicleProgramMap.get(vehicleType).add(progDef);
				}
				else{
					progDefLst = new ArrayList<ProgramDefinition>();
					progDefLst.add(progDef);
					dayVehicleProgramMap.put(vehicleType, progDefLst);
				}
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.VISTA.RTL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.VISTA.RTL.002", ne);
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
				LOGGER.error("DPB.BNS.VISTA.RTL.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return dayVehicleProgramMap;
	}
	public List<ConditionDefinition> getConditionDefinitions(int programId) {
		LOGGER.enter(CLASSNAME, "getConditionDefinitions()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		ConditionDefinition condDef = null;
		List<ConditionDefinition> conDefList =  new ArrayList<ConditionDefinition>();
		try {
			con = conFactory.getConnection();	
			String query= ICommonQueryConstants.RETRIEVE_ASSIGN_PGM_SPECIAL_CND;		

			ps = con.prepareStatement(query);
			ps.setInt(1, programId);
			rs= ps.executeQuery();

			while(rs.next()){
				condDef =  new ConditionDefinition();

				condDef.setId(rs.getInt("ID_DPB_CND"));
				condDef.setConditionName(rs.getString("NAM_DPB_CND"));
				condDef.setConditionDesc(rs.getString("DES_DPB_CND"));
				condDef.setCondition(rs.getString("CDE_DPB_CND"));
				condDef.setVariableName(rs.getString("NAM_DPB_VAR"));
				condDef.setCheckValue(rs.getString("TXT_DPB_CHK_VAL"));
				condDef.setConditionType(rs.getString("CDE_DPB_CND_TYP"));
				condDef.setRegularExp(rs.getString("TXT_DPB_REG_EX"));
				condDef.setStatus(rs.getString("CDE_DPB_STS"));

				conDefList.add(condDef);
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.VISTA.RTL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.VISTA.RTL.002", ne);
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
				LOGGER.error("DPB.BNS.VISTA.RTL.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return conDefList;
	}
	public Map<String, List<ProgramDefinition>> retrieveProcessDetails(Integer pId, String callType,String pType) {
	//public Map<String, List<ProgramDefinition>> retrieveProcessDetails(Integer pId ) {
		LOGGER.enter(CLASSNAME, "retrieveProcessDetails()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;

		Map<String, List<ProgramDefinition>> dayVehicleProgramMap =  new HashMap<String, List<ProgramDefinition>>();
		List<ProgramDefinition> progDefLst = null;
		try {
			con = conFactory.getConnection();	
			String query = null;
			
			query= ICommonQueryConstants.RETRIEVE_PROCESS_PROGRAM_DETAILS_FOR_PROCESS_ID;	
			
			LOGGER.info("************** retrieveProcessDetails:"+query+":pId:"+pId+":pType:"+pType);
			ps = con.prepareStatement(query);
			ps.setInt(1, pId);
			ps.setString(2,pType);			
			rs= ps.executeQuery();
			String vehicleType = null;

			while(rs.next()){
				vehicleType = rs.getString("CDE_VEH_TYP");
				ProgramDefinition progDef = new ProgramDefinition();
				progDef.setProcessId(rs.getInt("ID_DPB_PROC"));
				progDef.setProgramId(rs.getInt("ID_DPB_PGM"));
				progDef.setProcessType(rs.getString("CDE_DPB_PROC_TYP"));				
				progDef.setProgramName(rs.getString("NAM_DPB_PGM"));
				progDef.setDescription(rs.getString("PGM_NAME"));
				progDef.setStartDate(rs.getDate("DTE_DPB_PGM_START"));
				progDef.setEndDate(rs.getDate("DTE_DPB_PGM_END"));
				progDef.setRebateAmt(rs.getString("IND_DPB_RBT_PMT"));
				progDef.setCommPayment(rs.getString("IND_DPB_COMSN_PMT"));
				progDef.setCommAmount(rs.getDouble("AMT_DPB_COMSN_PMT"));
				progDef.setFixedPayment(rs.getDouble("PCT_FXD_PMT"));
				
				progDef.setComsnProcessSchedule(rs.getString("CDE_COMSN_BNS_PROC_SCHD"));
				progDef.setComsnProcessTrigger(rs.getString("CDE_COMSN_PROC_INIT_TYP"));
				progDef.setComsnPymtSchedule(rs.getString("CDE_COMSN_PMT_SCHD"));
				progDef.setComsnPymtTrigger(rs.getString("CDE_COMSN_PMT_PROC_INIT_TYP"));

				progDef.setFixedPymtBonusSchedule(rs.getString("CDE_FXD_BNS_SCHD"));
				progDef.setFixedPymtBonusTrigger(rs.getString("CDE_FXD_BNS_PROC_INIT_TYP"));
				progDef.setFixedPymtSchedule(rs.getString("CDE_FXD_PMT_SCHD"));
				progDef.setFixedPymtTrigger(rs.getString("CDE_FXD_PMT_PROC_INIT_TYP"));
				
				progDef.setPaymentType(rs.getString("IND_DPB_FXD_PMT"));
				progDef.setMaxVarPayment(rs.getString("IND_MAX_VAR_PMT"));
				progDef.setVariablePayment(rs.getDouble("PCT_VAR_PMT"));
				
				progDef.setVariablePymtBonusSchedule(rs.getString("CDE_VAR_BNS_SCHD"));
				progDef.setVariablePymtBonusTrigger(rs.getString("CDE_VAR_BNS_PROC_INIT_TYP"));
				progDef.setVariablePymtSchedule(rs.getString("CDE_VAR_PMT_SCHD"));
				progDef.setVariablePymtTrigger(rs.getString("CDE_VAR_PMT_PROC_INIT_TYP"));
				
				progDef.setSpecialProgram(rs.getString("CDE_DPB_PGM_TYP"));
				progDef.setKpiId(rs.getInt("ID_KPI"));
				progDef.setProgramStatus(rs.getString("CDE_DPB_STS"));
				//DTE_INACT
				if(rs.getInt("DTE_PROC_OVRD") > 0){
					progDef.setActlProcDte(rs.getDate("DTE_PROC_OVRD"));
				}
				else {
					progDef.setActlProcDte(rs.getDate("DTE_DPB_ACTL_PROC"));
				}
				if(dayVehicleProgramMap.containsKey(vehicleType)){
					dayVehicleProgramMap.get(vehicleType).add(progDef);
				}
				else{
					progDefLst = new ArrayList<ProgramDefinition>();
					progDefLst.add(progDef);
					dayVehicleProgramMap.put(vehicleType, progDefLst);
				}
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.VISTA.RTL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.VISTA.RTL.002", ne);
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
				LOGGER.error("DPB.BNS.VISTA.RTL.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return dayVehicleProgramMap;
	}
	public List<VistaFileProcessing> retrieveRtlVehicleDataGivenPeriod(java.sql.Date startDate, java.sql.Date endDate,String vType){
    	final String methodName = "getTerminatedDealerList";
		LOGGER.enter(CLASSNAME, methodName);
		List<VistaFileProcessing> rtlVehicleList= new ArrayList<VistaFileProcessing>();
		VistaFileProcessing vistaRtl = null;		
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;		
		try {
			con = conFactory.getConnection();	
			String query= ICommonQueryConstants.VISTA_RETAIL_VEHICLE_BASED_ON_VEHICLE_TYPE;		
			
			LOGGER.info("retrieveRtlVehicleDataGivenPeriod:"+query);
			LOGGER.info(vType+"It startDate:"+startDate+":endDate:"+endDate);
			ps = con.prepareStatement(query);			
			ps.setDate(1, startDate);
			ps.setDate(2, endDate);
			ps.setString(3, vType);
			rs= ps.executeQuery();

			while(rs.next()){				
				vistaRtl =  new VistaFileProcessing();
				vistaRtl.setPoNo(rs.getString("NUM_PO"));
				vistaRtl.setVinNum(rs.getString("NUM_VIN"));
				vistaRtl.setRetailDate(rs.getDate("DTE_RTL"));
				vistaRtl.setRetailTime(rs.getTime("TME_RTL"));
				vistaRtl.setVehStatusCode(rs.getString("CDE_VEH_STS"));
				vistaRtl.setDealerId(rs.getString("ID_DLR"));
				vistaRtl.setUsedCarIndicator(rs.getString("IND_USED_CAR"));				
				vistaRtl.setEmpPurCtrlId(rs.getString("ID_EMP_PUR_CTRL"));
				vistaRtl.setModelYearDate(rs.getString("DTE_MDL_YR"));
				vistaRtl.setModelText(rs.getString("TXT_MODL"));				
				vistaRtl.setRegionCode(rs.getString("CDE_RGN"));
				vistaRtl.setRetailCustLastName(rs.getString("NAM_RTL_CUS_LST"));
				vistaRtl.setRetailCustFirstName(rs.getString("NAM_RTL_CUS_FIR"));
				vistaRtl.setRetailCustMiddleName(rs.getString("NAM_RTL_CUS_MID"));
				vistaRtl.setTransDate(rs.getDate("DTE_TRANS"));
				vistaRtl.setTransTime(rs.getTime("TME_TRANS"));
				vistaRtl.setMsrpBaseAmount(rs.getDouble("AMT_MSRP_BASE"));
				vistaRtl.setMsrpTotalsAmt(rs.getDouble("AMT_MSRP_TOT"));
				vistaRtl.setMsrpTotAmtAcsry(rs.getDouble("AMT_MSRP_TOT_ACSRY"));
				vistaRtl.setDlrRebateAmt(rs.getDouble("AMT_DLR_RBT"));
				vistaRtl.setFleetIndicator(rs.getString("IND_FLT"));
				vistaRtl.setWholeSaleInitType(rs.getString("CDE_WHSLE_INIT_TYP"));
				vistaRtl.setUseCode(rs.getString("CDE_USE"));
				vistaRtl.setVehTypeCode(rs.getString("CDE_VEH_TYP"));
				vistaRtl.setCarStatusCode(rs.getString("CDE_CAR_STS")); 
				vistaRtl.setSalesTypeCode(rs.getString("CDE_SLE_TYP"));
				vistaRtl.setDemoServiceDate(rs.getDate("DTE_DEMO_SRV"));
				vistaRtl.setUsedCarIndicator2(rs.getString("IND_USED_CAR2"));
				rtlVehicleList.add(vistaRtl);
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.VISTA.RTL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.VISTA.RTL.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}catch(Exception  ne){
			LOGGER.error("DPB.BNS.VISTA.RTL.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}
		finally {
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
				LOGGER.error("DPB.BNS.VISTA.RTL.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return rtlVehicleList;
	}
	/**
	 * 
	 * @param pId
	 * @param callType
	 * @return
	 */
	public List<LeadershipBonusDetails> retrieveLdrShipProcessDetails(Integer pId, String callType) {
			LOGGER.enter(CLASSNAME, "retrieveLdrShipProcessDetails()");
			Connection con = null;
			ResultSet rs= null;		
			PreparedStatement ps = null;
			LeadershipBonusDetails progDef = null;
			List<LeadershipBonusDetails> ldrspProgramList =  new ArrayList<LeadershipBonusDetails>();
			try {
				con = conFactory.getConnection();	
				String query = null;
				boolean trigger = false;				
				query= ICommonQueryConstants.RETRIEVE_LDRSHIP_PROCESS_PROGRAM_DETAILS_FOR_PROCESS_ID;
				LOGGER.info("retrieveLdrShipProcessDetails:"+query);
				LOGGER.info("It pId: "+pId);
				ps = con.prepareStatement(query);
				ps.setInt(1, pId);				
				rs= ps.executeQuery();
				while(rs.next()){			
					progDef = new LeadershipBonusDetails();
					progDef.setLdrshipid(rs.getInt("ID_LDRSP_BNS_PGM"));
					progDef.setLdrshipname(rs.getString("NAM_LDRSP_BNS"));
					progDef.setRtlStartDate(DateCalenderUtil.convertDateToString(rs.getDate("DTE_LDRSP_BNS_RTL_STA")));
					progDef.setRtlEndDate(DateCalenderUtil.convertDateToString(rs.getDate("DTE_LDRSP_BNS_RTL_END")));
					progDef.setStartDate(DateCalenderUtil.convertDateToString(rs.getDate("DTE_LDRSP_BNS_ACRL_STA")));
					progDef.setEndDate(DateCalenderUtil.convertDateToString(rs.getDate("DTE_LDRSP_BNS_ACRL_END")));
					progDef.setProcessDate(DateCalenderUtil.convertDateToString(rs.getDate("DTE_LDRSP_BNS_PROC")));				
					ldrspProgramList.add(progDef);					
				}
			}catch(SQLException  e){
				LOGGER.error("DPB.COMMON.DAO.LDR.001", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}catch(NamingException  ne){
				LOGGER.error("DPB.COMMON.DAO.LDR.002", ne);
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
					LOGGER.error("DPB.COMMON.DAO.LDR.003",e);
					DPBExceptionHandler.getInstance().handleDatabaseException(e);
				}
			}
			return ldrspProgramList;
		}
	public List<BonusInfo> calculateLdrshipBonusBasedOnProgram (List<BonusInfo> bonusInfo,int procId,String processType,String user) {
		final String methodName = "calculateLdrshipBonusBasedOnProgram";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BonusInfo bnsinfo = null;	
		try {
			con = conFactory.getConnection();			
			String sqlQuery = ICommonQueryConstants.RETRIEVE_LDRSP_PGM_DTLS;
			ps = con.prepareStatement(sqlQuery);
			ps.setInt(1, procId);
			ps.setString(2, processType);
			rs = ps.executeQuery();
			while(rs.next()) {
				bnsinfo = new BonusInfo();
				bnsinfo.setProcessId(rs.getInt("PROC_ID"));
				bnsinfo.setDpbUnblkVehId(rs.getInt("ID_DPB_UNBLK_VEH"));
				bnsinfo.setDlrId(rs.getString("ID_DLR"));
				bnsinfo.setActualDate(rs.getDate("DTE_DPB_ACTL_PROC"));
				bnsinfo.setPgmId(rs.getInt("ID_LDRSP_BNS_PGM"));
			//	bnsinfo.setLdrBonusIndicator(rs.getString("IND_LDRSP_BNS_PGM"));
				bnsinfo.setBnsCalcAmt(rs.getDouble("AMT_LDRSP_BNS_PER_UNIT_CALC"));
				bnsinfo.setMaxBnsEligibleAmt(rs.getDouble("AMT_LDRSP_BNS_PER_UNIT_CALC"));
				bnsinfo.setUnUsedAmtAcrl(rs.getDouble("ACRL_AMOUNT"));
				bnsinfo.setUnUsedAmt(rs.getDouble("AMT_DPB_UNUSED"));
				bnsinfo.setAdjIndicator(IConstants.CONSTANT_N);
				bnsinfo.setStatus(IConstants.LDR_BONUS_STATUS_CALCULATED);
				bnsinfo.setUserId(user);
				bonusInfo.add(bnsinfo);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.LDRSP.BNS.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.LDRSP.BNS.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
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
		return bonusInfo;
	}
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param paymentType
	 * @param uId
	 * @return
	 */
	public List<Integer> retriveBonusPaidRecords (Date startDate,Date endDate,String paymentType,boolean ldrsp,Integer pgId){	
			final String methodName = "retriveBonusPaidRecords";
			LOGGER.enter(CLASSNAME, methodName);
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Integer> updateStausFor	= new ArrayList<Integer>();
			try {
				con = conFactory.getConnection();			
				String sqlQuery = "";
				if(!ldrsp){
					sqlQuery = ICommonQueryConstants.RETRIEVE_PAYMENT_RECORD_FOR_UPDATE;
				}else{
					sqlQuery = ICommonQueryConstants.RETRIEVE_LDR_PAYMENT_RECORD_FOR_UPDATE;
					
				}
				ps = con.prepareStatement(sqlQuery);	
				LOGGER.info("Bonus Paid Records Query:"+sqlQuery);
				LOGGER.info("startDate:"+startDate+":endDate:"+endDate+":paymentType:"+paymentType+":pgmId:"+pgId);
				ps = con.prepareStatement(sqlQuery);	
				ps.setDate(1, startDate);
				ps.setDate(2, endDate);
				if(!ldrsp){					
					ps.setString(3, paymentType);
					ps.setInt(4,pgId);
				} else {
					ps.setInt(3,pgId);
				}
							
				rs = ps.executeQuery();
				while(rs.next()) {					
					updateStausFor.add(rs.getInt(1));
				}
			} catch (SQLException e) {
				LOGGER.error("DPB.COMMON.PAYMENT.RECORDS.DAO.001", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			} catch (NamingException ne) {
				LOGGER.error("DPB.COMMON.PAYMENT.RECORDS.DAO.002", ne);
				DPBExceptionHandler.getInstance().handleException(ne);
			} finally {
				try {
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
			return updateStausFor;
	}
	/**
	 * 
	 * @param updateStausFor
	 * @param paymentType
	 */
	public void updateBonusPaidStatusRecords(List<Integer> procIdList,String paymentType, String userId,boolean ldrsp){	
		final String methodName = "updateBonusPaidRecords";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		int updateCount = 0;
		try {
			con = conFactory.getConnection();			
			String sqlQuery = "";
			if(!ldrsp){
				sqlQuery = ICommonQueryConstants.UDPATE_BP_AND_FP_PAYMENT_STATUS;
			}else{
				sqlQuery = ICommonQueryConstants.UDPATE_LDR_PAYMENT_STATUS;
				
			}
			LOGGER.info("pId size:"+procIdList.size()+":****updateBonusPaidStatusRecords:"+sqlQuery);
			if(procIdList!= null && procIdList.size() > 0){	
				Iterator<Integer> procItr = procIdList.iterator();
				ps = con.prepareStatement(sqlQuery);
				while(procItr.hasNext()){
					Integer pId = procItr.next();	
					if(!ldrsp){
						ps.setString(1,IConstants.BONUS_STATUS_PAID);	
					} else {
						ps.setString(1,IConstants.LDR_BONUS_STATUS_PAID);		
					}
					ps.setString(2,userId);
					ps.setInt(3,pId);
					ps.addBatch();
					updateCount++;
					if(updateCount == 300){
						ps.executeBatch();
						updateCount = 0;
					}
				}
				if(updateCount > 0){
					int count[] = ps.executeBatch();
				}			
			}						
		} catch (SQLException e) {
			LOGGER.error("DPB.COMMON.UPDATE.BNS.PAID.DAO.001", e);
			LOGGER.error("DPB.COMMON.UPDATE.BNS.PAID.DAO.0011", e.getNextException());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.COMMON.UPDATE.BNS.PAID.DAO.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
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
	}
	public ProgramDefinition retrieveProgramDefinition(Integer pId, String callType,String pType) {	
		LOGGER.enter(CLASSNAME, "retrieveProcessDetails()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;			
		ProgramDefinition progDef =  null;
		try {
			con = conFactory.getConnection();	
			String query = null;
			query= ICommonQueryConstants.RETRIEVE_PROGRAM_DETAILS_FOR_PROCESS_ID;	

			LOGGER.info("retrieveProcessDetails:"+query);
			LOGGER.info("pId: "+pId);
			ps = con.prepareStatement(query);
			ps.setInt(1, pId);					
			rs= ps.executeQuery();
			while(rs.next()){					
				progDef = new ProgramDefinition();
				progDef.setProgramId(rs.getInt("ID_DPB_PGM"));
				progDef.setProgramName(rs.getString("NAM_DPB_PGM"));
				progDef.setDescription(rs.getString("PGM_NAME"));
				progDef.setStartDate(rs.getDate("DTE_DPB_PGM_START"));
				progDef.setEndDate(rs.getDate("DTE_DPB_PGM_END"));
				progDef.setRebateAmt(rs.getString("IND_DPB_RBT_PMT"));
				progDef.setCommPayment(rs.getString("IND_DPB_COMSN_PMT"));
				progDef.setCommAmount(rs.getDouble("AMT_DPB_COMSN_PMT"));
				
				//progDef.setCommPercent(rs.getDouble("PCT_DPB_COMSN_PMT"));
				progDef.setFixedPayment(rs.getDouble("PCT_FXD_PMT"));
				
				progDef.setComsnProcessSchedule(rs.getString("CDE_COMSN_BNS_SCHD"));
				progDef.setComsnProcessTrigger(rs.getString("CDE_COMSN_BNS_TRGR"));
				progDef.setComsnPymtSchedule(rs.getString("CDE_COMSN_PMT_SCHD"));
				progDef.setComsnPymtTrigger(rs.getString("CDE_COMSN_PMT_TRGR"));

				progDef.setFixedPymtBonusSchedule(rs.getString("CDE_FXD_BNS_SCHD"));
				progDef.setFixedPymtBonusTrigger(rs.getString("CDE_FXD_BNS_TRGR"));
				progDef.setFixedPymtSchedule(rs.getString("CDE_FXD_PMT_SCHD"));
				progDef.setFixedPymtTrigger(rs.getString("CDE_FXD_PMT_TRGR"));
				progDef.setPaymentType(rs.getString("IND_DPB_FXD_PMT"));
				progDef.setMaxVarPayment(rs.getString("IND_MAX_VAR_PMT"));
				progDef.setVariablePayment(rs.getDouble("PCT_VAR_PMT"));
				progDef.setVariablePymtBonusSchedule(rs.getString("CDE_VAR_BNS_SCHD"));
				progDef.setVariablePymtBonusTrigger(rs.getString("CDE_VAR_BNS_TRGR"));
				progDef.setVariablePymtSchedule(rs.getString("CDE_VAR_PMT_SCHD"));
				progDef.setVariablePymtTrigger(rs.getString("CDE_VAR_PMT_TRGR"));
				progDef.setSpecialProgram(rs.getString("IND_SPL_DPB_PGM"));
				progDef.setKpiId(rs.getInt("ID_KPI"));
				progDef.setProgramStatus(rs.getString("CDE_DPB_STS"));
				progDef.setProcessId(rs.getInt("ID_DPB_PROC"));
				progDef.setProcessType(rs.getString("CDE_DPB_PROC_TYP"));					
				if(rs.getInt("DTE_PROC_OVRD") > 0){
					progDef.setActlProcDte(rs.getDate("TME_PROC_OVRD"));
				}
				else {
					progDef.setActlProcDte(rs.getDate("DTE_DPB_ACTL_PROC"));
				}
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.VISTA.RTL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.VISTA.RTL.002", ne);
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
				LOGGER.error("DPB.BNS.VISTA.RTL.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return progDef;
	}
	public List<VistaFileProcessing> retrieveRtlVehicleDataGivenPeriodAndType(java.sql.Date startDate, java.sql.Date endDate,List<String> vList,boolean isSpecial){
    	final String methodName = "getTerminatedDealerList";
		LOGGER.enter(CLASSNAME, methodName);
		List<VistaFileProcessing> rtlVehicleList= new ArrayList<VistaFileProcessing>();
		VistaFileProcessing vistaRtl = null;		
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;		
		try {
			con = conFactory.getConnection();
			int lastIndex = 0;
			StringBuffer query = new StringBuffer(ICommonQueryConstants.VISTA_RETAIL_VEHICLE_BASED_ON_VEHICLE_TYPE_LIST);
			if(isSpecial){
				query = query.append(" and ID_DPB_PGM is not null order by BLK.DTE_RTL,BLK.TME_RTL with ur ");
			}else {
				query = query.append(" and ID_DPB_PGM is null order by BLK.DTE_RTL,BLK.TME_RTL with ur");
			}
			for (int j=1; j< vList.size() ;j++)
			{
				lastIndex = query.lastIndexOf("?");
				query.insert(lastIndex + 1, ",?");
			}
			LOGGER.info("RetrieveRtlVehicleDataGivenPeriodAnd Type:***********"+query);
			LOGGER.info("startDate:"+startDate+":endDate:"+endDate+":vList:"+vList.toArray().toString());
			
			ps = con.prepareStatement(query.toString());
			ps.setDate(1, startDate);			
			ps.setDate(2, endDate);
			for(int k = 0;k < vList.size();k++)
			{
				ps.setString(k+3, (String)vList.get(k));
			}
			rs= ps.executeQuery();
			while(rs.next()){				
				vistaRtl =  new VistaFileProcessing();
				vistaRtl.setUnBlkVehId(rs.getInt("ID_DPB_UNBLK_VEH"));
				vistaRtl.setPoNo(rs.getString("NUM_PO"));
				vistaRtl.setVinNum(rs.getString("NUM_VIN"));
				vistaRtl.setRetailDate(rs.getDate("DTE_RTL"));
				vistaRtl.setRetailTime(rs.getTime("TME_RTL"));
				vistaRtl.setVehStatusCode(rs.getString("CDE_VEH_STS"));
				vistaRtl.setDealerId(rs.getString("ID_DLR"));
				vistaRtl.setUsedCarIndicator(rs.getString("IND_USED_VEH"));				
				vistaRtl.setEmpPurCtrlId(rs.getString("ID_EMP_PUR_CTRL"));
				vistaRtl.setModelYearDate(rs.getString("DTE_MODL_YR"));
				vistaRtl.setModelText(rs.getString("DES_MODL"));				
				vistaRtl.setRegionCode(rs.getString("CDE_RGN"));
				vistaRtl.setRetailCustLastName(rs.getString("NAM_RTL_CUS_LST"));
				vistaRtl.setRetailCustFirstName(rs.getString("NAM_RTL_CUS_FIR"));
				vistaRtl.setRetailCustMiddleName(rs.getString("NAM_RTL_CUS_MID"));
				vistaRtl.setTransDate(rs.getDate("DTE_TRANS"));
				vistaRtl.setTransTime(rs.getTime("TME_TRANS"));
				vistaRtl.setUseCode(rs.getString("CDE_USE_VEH"));
				vistaRtl.setMsrpBaseAmount(rs.getDouble("AMT_MSRP_BASE"));
				vistaRtl.setMsrpTotalsAmt(rs.getDouble("AMT_MSRP"));
				vistaRtl.setMsrpTotAmtAcsry(rs.getDouble("AMT_MSRP_TOT_ACSRY"));
				vistaRtl.setDlrRebateAmt(rs.getDouble("AMT_DLR_RBT"));
				vistaRtl.setFleetIndicator(rs.getString("IND_FLT"));
				vistaRtl.setWholeSaleInitType(rs.getString("CDE_WHSLE_INIT_TYP"));
				//vistaRtl.setUseCode(rs.getString("CDE_USE"));
				vistaRtl.setVehTypeCode(rs.getString("CDE_VEH_TYP"));
				//CDE_NATL_TYPE,DTS_LAST_UPDT,DTS_CREA,ID_CREA_USER,				
				vistaRtl.setProgType(rs.getInt("ID_DPB_PGM"));
				vistaRtl.setSalesTypeCode(rs.getString("CDE_VEH_DDR_USE"));
				vistaRtl.setDemoServiceDate(rs.getDate("DTE_VEH_DEMO_SRV"));
				vistaRtl.setCarStatusCode(rs.getString("CDE_VEH_DDR_STS"));
				vistaRtl.setUsedCarIndicator2(rs.getString("IND_USED_VEH_DDRS"));
				vistaRtl.setIndAmg(rs.getString("IND_AMG"));
				rtlVehicleList.add(vistaRtl);				
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.VISTA.RTL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.VISTA.RTL.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}catch(Exception  ne){
			LOGGER.error("DPB.BNS.VISTA.RTL.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}
		finally {
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
				LOGGER.error("DPB.BNS.VISTA.RTL.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return rtlVehicleList;
	}
	@Override
	public String checkTerminateAdjustmentCancelationStatus(java.sql.Date aDate){
    	final String methodName = "checkTerminateAdjustmentCancelationStatus";
		LOGGER.enter(CLASSNAME, methodName);
		String  status = "";
		LOGGER.exit(CLASSNAME, methodName);
		return status;
	}
	@Override
	public java.sql.Date getActualProcessDate(Integer procId){
    	final String methodName = "getActualProcessDate";
		LOGGER.enter(CLASSNAME, methodName);
		java.sql.Date aDate =  null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conFactory.getConnection();			
			String sqlQuery = "";
			sqlQuery = ICommonQueryConstants.RETRIEVE_ACTUAL_DATE_ON_PROC_ID;
			ps = con.prepareStatement(sqlQuery);	
			LOGGER.info("getActualProcessDate:"+sqlQuery+":procId:"+procId);
			ps = con.prepareStatement(sqlQuery);	
			ps.setInt(1, procId);					
			rs = ps.executeQuery();
			while(rs.next()) {					
				aDate = rs.getDate("DTE_DPB_ACTL_PROC");
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.COMMON.DAO.ACTUAL.PROCESS.DATE.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.COMMON.DAO.ACTUAL.PROCESS.DATE.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("DPB.COMMON.DAO.ACTUAL.PROCESS.DATE.003", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}		
		LOGGER.exit(CLASSNAME, methodName);
		return aDate;
	}
	public List<ProgramDefinitionBean> getSpecialPrograms(){
		final String methodName = "getSpecialPrograms";
		LOGGER.enter(CLASSNAME, methodName);
		List<ProgramDefinitionBean> specialPrograms = new ArrayList<ProgramDefinitionBean>();
		ProgramDefinitionBean progBean = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conFactory.getConnection();			
			String query = "";
			query = ICommonQueryConstants.SPECIAL_PROG_SELECT_QUERY;
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				progBean = new ProgramDefinitionBean();
				progBean.setProgramId(rs.getInt("ID_DPB_PGM"));
				progBean.setProgramName(rs.getString("NAM_DPB_PGM"));
				progBean.getVehicleType().setVehicleType(rs.getString("CDE_VEH_TYP"));
				specialPrograms.add(progBean);
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.COMMON.SPL.PROG.DAO.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.COMMON.SPL.PROG.DAO.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("DPB.COMMON.SPL.PROG.DAO.003", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return specialPrograms;
	}
	public Map<Integer,List<ConditionDefinition>> getSpecialConditions(List<ProgramDefinitionBean> specialPrograms) {
		final String methodName = "getVehicleConditions";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<Integer,List<ConditionDefinition>> splConds = new HashMap<Integer, List<ConditionDefinition>>();
		List<ConditionDefinition> prgConds = null;
		ConditionDefinition condDef = null;
		try {
			con  = conFactory.getConnection();		
			String COND_SELECT_QUERY = "";
			COND_SELECT_QUERY = ICommonQueryConstants.SPECIAL_COND_SELECT_QUERY;
			ps = con.prepareStatement(COND_SELECT_QUERY);
			for(ProgramDefinitionBean programs: specialPrograms){
				prgConds = new ArrayList<ConditionDefinition>();
				ps.setInt(1, programs.getProgramId());
				rs = ps.executeQuery();	
				while(rs.next()){
						condDef =  new ConditionDefinition();
						condDef.setId(rs.getInt("ID_DPB_CND"));
						condDef.setConditionName(rs.getString("NAM_DPB_CND"));
						condDef.setConditionDesc(rs.getString("DES_DPB_CND"));
						condDef.setCondition(rs.getString("CDE_DPB_CND"));
						condDef.setVariableName(rs.getString("NAM_DPB_VAR"));
						condDef.setCheckValue(rs.getString("TXT_DPB_CHK_VAL"));
						condDef.setConditionType(rs.getString("CDE_DPB_CND_TYP"));
						condDef.setRegularExp(rs.getString("TXT_DPB_REG_EX"));
						condDef.setStatus(rs.getString("CDE_DPB_STS"));
						prgConds.add(condDef);
				}
				splConds.put(programs.getProgramId(), prgConds);
			}
		}catch(SQLException  e){
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
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
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return splConds;
	}
	public List<KpiFile> getUpdatedKpiWithPercentageChange(){
		final String methodName = "getUpdatedKpiWithPercentageChange";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<KpiFile> changeKpiList = new ArrayList<KpiFile>();
		KpiFile  adjKpi = null;
		try {
			con  = conFactory.getConnection();		
			String RETRIVE_UPDATED_KPI_WITH_PERCENTAGE = "";
			RETRIVE_UPDATED_KPI_WITH_PERCENTAGE = ICommonQueryConstants.KPI_PERCENTAGE_CHANGE_QTR_OR_MONTHLY_BY_LAST_PROCESSED_KPI_FILE_ID;
			ps = con.prepareStatement(RETRIVE_UPDATED_KPI_WITH_PERCENTAGE);
			//ps.setInt(1, programs.getProgramId());
			rs = ps.executeQuery();	
			while(rs.next()){
					adjKpi =  new KpiFile();						
					adjKpi.setDealerId(rs.getString("ID_DLR"));
					adjKpi.setKpiId(rs.getInt("ID_KPI"));
					adjKpi.setKpiYr(rs.getInt("DTE_FSCL_YR"));
					adjKpi.setKpiPct(rs.getDouble("PCT_KPI"));
					adjKpi.setKpiFiscalQuarter(rs.getString("DTE_FSCL_QTR"));
					changeKpiList.add(adjKpi);
				}
		}catch(SQLException  e){
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
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
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return changeKpiList;
	}
	public List<KpiFileProcessing> getActiveProgramEffectedDueKpiValueChanged(){
		final String methodName = "getUpdatedKpiWithPercentageChange";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;		 
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<KpiFileProcessing> changeKpiList = new ArrayList<KpiFileProcessing>();
		KpiFileProcessing  adjKpi = null;
		try {
			con  = conFactory.getConnection();		
			String RETRIVE_UPDATED_KPI_WITH_PERCENTAGE = "";
			RETRIVE_UPDATED_KPI_WITH_PERCENTAGE = ICommonQueryConstants.SPECIAL_COND_SELECT_QUERY;
			ps = con.prepareStatement(RETRIVE_UPDATED_KPI_WITH_PERCENTAGE);
			//ps.setInt(1, programs.getProgramId());
			rs = ps.executeQuery();	
			while(rs.next()){
					adjKpi =  new KpiFileProcessing();						
					adjKpi.setDealerId(rs.getString("ID_DLR"));
					adjKpi.setKpiId(rs.getInt("ID_KPI"));
					adjKpi.setKpiYear(rs.getInt("DTE_FSCL_YR"));
					adjKpi.setKpiPercentage(rs.getDouble("PERCENTAGE_CHANGED"));
					adjKpi.setKpiFiscalPeriod(rs.getString("DTE_FSCL_QTR"));
					changeKpiList.add(adjKpi);
				}
		}catch(SQLException  e){
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
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
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return changeKpiList;
	}
	public List<BonusInfo> getPreviousBonusRecordsForAdjuestment(String kpiId,String dlrId,java.sql.Date startDate, java.sql.Date endDate){
		final String methodName = "getPreviousBonusRecordsForAdjuestment";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		BonusInfo bnsCalInfo =  new BonusInfo();
		List<BonusInfo> bonusDtlsList =  new ArrayList<BonusInfo>();
		try {
			con = conFactory.getConnection();	
			String query= ICommonQueryConstants.GET_BONUS_RECORD_FOR_ADJUSTMENT;
			ps = con.prepareStatement(query);
			ps.setString(1, kpiId);
			ps.setDate(2, startDate);
			ps.setDate(3, endDate);
			ps.setString(4, dlrId);
			rs= ps.executeQuery();
			while(rs.next()){
				bnsCalInfo =  new BonusInfo();			
				
				//bnsCalInfo.setCalcId(rs.getInt("ID_DPB_CALC"));
				bnsCalInfo.setProcessId(rs.getInt("ID_DPB_PROC"));
				bnsCalInfo.setDpbUnblkVehId(rs.getInt("ID_DPB_UNBLK_VEH"));
				bnsCalInfo.setDlrId(dlrId);
				//bnsCalInfo.setActualDate(rs.getDate("DTE_CAL"));
				bnsCalInfo.setPgmId(rs.getInt("ID_DPB_PGM"));
				bnsCalInfo.setKpiId(rs.getString("ID_KPI"));				
				bnsCalInfo.setBnsCalcAmt(rs.getDouble("AMT_DPB_BNS_CALC"));
				bnsCalInfo.setMaxBnsEligibleAmt(rs.getDouble("AMT_DPB_MAX_BNS_ELG"));
				bnsCalInfo.setTotalBnsAmt(rs.getDouble("TOTAL"));				
				//bnsCalInfo.setAdjIndicator(rs.getString("IND_DPB_ADJ"));
				bonusDtlsList.add(bnsCalInfo);
			}		
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.KPI.ADJ.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.KPI.ADJ.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}catch(Exception  ne){
			LOGGER.error("DPB.BNS.KPI.ADJ.004", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}
		finally {
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
				LOGGER.error("DPB.BNS.KPI.ADJ.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return bonusDtlsList;
	}
	public List<BonusInfo> getPreviousBonusRecordsForCancel(String poNumber,String dlrId,java.sql.Date retailDate, java.sql.Time retailTime){
		final String methodName = "getPreviousBonusRecordsForCancel";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		BonusInfo bnsCalInfo =  new BonusInfo();
		List<BonusInfo> bonusDtlsList =  new ArrayList<BonusInfo>();
		try {
			con = conFactory.getConnection();	
			String query= ICommonQueryConstants.GET_ORIGINAL_RETAIL_FOR_CANCEL_TRANSACTION;
			ps = con.prepareStatement(query);
			ps.setString(1, poNumber);
			ps.setDate(2, retailDate);
			ps.setTime(3, retailTime);
			ps.setDate(4, retailDate);
			ps.setString(5, dlrId);
			rs= ps.executeQuery();
			while(rs.next()){
				bnsCalInfo =  new BonusInfo();			
				bnsCalInfo.setDpbUnblkVehId(rs.getInt("ID_DPB_UNBLK_VEH"));
				bonusDtlsList.add(bnsCalInfo);
			}		
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.CANCEL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.CANCEL.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}catch(Exception  ne){
			LOGGER.error("DPB.BNS.CANCEL.004", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}
		finally {
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
				LOGGER.error("DPB.BNS.CANCEL.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return bonusDtlsList;
	}

	public List<BonusInfo> getBonusDetailsForCancel(String dpbUnblkVehId, int pgmId)
	{		
		LOGGER.enter(CLASSNAME, "getBonusDetailsForCancel()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		BonusInfo bnsCalInfo =  new BonusInfo();
		List<BonusInfo> bonusDtlsList =  new ArrayList<BonusInfo>();
		try {
			con = conFactory.getConnection();	
			String query= ICommonQueryConstants.GET_BONUS_INFO_FOR_CANCEL_TRANSACTION;
			LOGGER.info("getBonusDetailsForCancel:"+query +":UnblkVehId:"+dpbUnblkVehId + ":pgmId:"+pgmId);
			ps = con.prepareStatement(query);
			ps.setString(1, dpbUnblkVehId);
			ps.setInt(2, pgmId);
		
			rs= ps.executeQuery();
			while(rs.next()){
				bnsCalInfo =  new BonusInfo();				
				bnsCalInfo.setBnsCalcAmt(rs.getDouble("TOT"));
				bnsCalInfo.setMaxBnsEligibleAmt(rs.getDouble("AMT_DPB_MAX_BNS_ELG"));
				bonusDtlsList.add(bnsCalInfo);
			}		
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.DTL.CANCEL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.DTL.CANCEL.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}catch(Exception  ne){
			LOGGER.error("DPB.BNS.DTL.CANCEL.003", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}
		finally {
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
				LOGGER.error("DPB.BNS.DTL.CANCEL.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return bonusDtlsList;
	}	
	
	public boolean isDealerTerminationCancelationProcLogs(java.sql.Date aDate){
		final String methodName = "isDealerTerminationCanceltionCompleted";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		String status = null;
		boolean complete  = false;
		try {
			Integer tProcId = PROPERTY_MANAGER.getPropertyAsInteger("termination.bonus.process.id");
			//Integer cProcId = PROPERTY_MANAGER.getPropertyAsInteger("cancelation.bonus.process.id");
			con = conFactory.getConnection();	
			String query = ICommonQueryConstants.GET_PROCESS_LOGS_TERMINATION_CANCELATION;
			ps = con.prepareStatement(query);
			ps.setInt(1, tProcId);
		//	ps.setInt(2, cProcId);
			ps.setDate(2, aDate);
			rs= ps.executeQuery();
			while(rs.next()){
				status = rs.getString(1);
			}
			if(status!= null)
				complete = true;
			
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.LDR.TR.CANCEL.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.LDR.TR.CANCEL.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}catch(Exception  ne){
			LOGGER.error("DPB.BNS.LDR.TR.CANCEL.004", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}
		finally {
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
				LOGGER.error("DPB.BNS.LDR.TR.CANCEL.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return complete;
	}
	public void insertIntoProcessTerminateCancelationLog(FileProcessLogMessages message,java.sql.Date aDate) {
    	final String methodName = "insertIntoProcessLog";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		try {
		
			con = conFactory.getConnection();
			java.sql.Timestamp aTimeStamp  = DateCalenderUtil.getTimestampByPassingDate( aDate);
			String sqlQuery = ICommonQueryConstants.INSERT_PROC_TERMINATION_CANCELATION_LOG;
			ps = con.prepareStatement(sqlQuery);
			ps.setString(1, message.getProcessStatusCode());
			ps.setInt(2, message.getProcessDefId());
			ps.setString(3, message.getProcessMessage());
			ps.setString(4, message.getUserId());
			ps.setString(5, message.getUserId());
			ps.setTimestamp(6, aTimeStamp);
			ps.setTimestamp(7, aTimeStamp);
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Exception Occured while inserting the data", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("Exception Occured while inserting the data", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
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
    }
	public void updateDayIdAsCurrentDayId(java.sql.Date procDate,java.sql.Date updateDate,boolean flag)
    {
    	final String methodName = "updateDayIdAsCurrentDayId";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		try {
			String sqlQuery = null;		
			con = conFactory.getConnection();
			if(flag){
				sqlQuery = "update DPB_PROC set DTE_CAL =  ?  where DTE_CAL = ?";
			}else{
				sqlQuery = "update DPB_PROC set DTE_CAL = ?   where DTE_CAL = ?";
			}
			ps = con.prepareStatement(sqlQuery);			
			ps.setDate(1,procDate);
			ps.setDate(2,updateDate);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Exception Occured while inserting the data", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("Exception Occured while inserting the data", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
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
    }
	public List<DealerInfo> getTerminatedDlrListTillActualDate(Date aProcate){		
		final String methodName = "getTerminatedDealerList()";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		DealerInfo dealerInfo =  new DealerInfo();
		List<DealerInfo> terminateDlrList =  new ArrayList<DealerInfo>();
		try {
			con = conFactory.getConnection();	
			String query= ICommonQueryConstants.GET_TERMINATED_DEALER_LIST_TILL_GIVEN_DATE;	
			LOGGER.info("***************getTerminatedDlrListTillActualDate:"+query+":rtlDate:"+aProcate);
			
			ps = con.prepareStatement(query);
			ps.setDate(1, aProcate);
			rs= ps.executeQuery();		
			while(rs.next()){
				dealerInfo =  new DealerInfo();
				dealerInfo.setDealerId(rs.getString("ID_DLR"));				
				dealerInfo.setDlrAbbrText(rs.getString("CDE_DLR_SHT_NAM"));
				dealerInfo.setDlrName(rs.getString("NAM_DLR"));
				dealerInfo.setDlrNameDba(rs.getString("NAM_DO_BUSN_AS"));
				dealerInfo.setDlrCity(rs.getString("ADR_PRIM_CITY"));
				dealerInfo.setDlrState(rs.getString("CDE_DLR_ST"));
				dealerInfo.setDlrTerminatedDate(rs.getTimestamp("DTE_MBUSA_TERM"));	
				terminateDlrList.add(dealerInfo);
			}		
		}catch(SQLException  e){
			LOGGER.error("DPB.BNS.TERMINATE.LIST.TILL.ACTUAL.DATE.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.BNS.TERMINATE.LIST.TILL.ACTUAL.DATE.002", ne);
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
				LOGGER.error("DPB.BNS.TERMINATE.LIST.TILL.ACTUAL.DATE.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		return terminateDlrList;
	}
	
	
	public List<PaymentDealerInfo> getDealerData(Date beginDate, Date monthEndDate,Date prevDate,Date currentDate,Date qtrEndDate,Date qtrBeginDate,Date qtrDate,int n)
	{
		final String methodName = "getDealerData";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PaymentDealerInfo> dealerList = null;
		PaymentDealerInfo  dlr = null;		
		try
		{		
			con  = conFactory.getConnection();
			dealerList = new ArrayList<PaymentDealerInfo>();
			if (con != null) {	
				if(n == 0 ){
					ps = con.prepareStatement(ICommonQueryConstants.GET_DAILY_DEALER_INFO);
					ps.setDate(1, prevDate);
					ps.setDate(2, prevDate);
					
					rs = ps.executeQuery();	
					
					while(rs.next()){		
						dlr = new PaymentDealerInfo();
						
						dlr.setDlrId(rs.getString("dlr"));
						dlr.setTotal(rs.getDouble("total"));
						dlr.setVehType(rs.getString("vehType"));
						dlr.setVehCount(rs.getInt("vehcount"));
						dlr.setText(rs.getString("text"));
						dlr.setPo(rs.getString("po"));
						dlr.setVin(rs.getString("vin"));
						
						dealerList.add(dlr);
					}
				}
				else if( n == 1){
					
					Date calcDate = beginDate;
					Calendar c = Calendar.getInstance();
					c.setTime(beginDate);
					c.add(Calendar.DATE, 1);
					calcDate = new java.sql.Date(c.getTime().getTime());
					
					ps = con.prepareStatement(ICommonQueryConstants.GET_DEALER_INFO);
					ps.setString(1, IConstants.FTL);
					ps.setDate(2, beginDate);
					ps.setDate(3, prevDate);
					ps.setString(4, IConstants.FTL);
					ps.setDate(5, prevDate);
					ps.setDate(6, calcDate);
					ps.setDate(7, monthEndDate);
					
					rs = ps.executeQuery();	
					
					while(rs.next()){		
						dlr = new PaymentDealerInfo();
						
						dlr.setDlrId(rs.getString("dlr"));
						dlr.setTotal(rs.getDouble("total"));
						dlr.setVehType(rs.getString("vehType"));
						dlr.setVehCount(rs.getInt("vehcount"));
						dlr.setText(rs.getString("text"));
												
						dealerList.add(dlr);
					}
				}
				else if( n == 2){
					ps = con.prepareStatement(ICommonQueryConstants.GET_DEALER_INFO);
					ps.setString(1, IConstants.SMART);
					if (currentDate.toString().equals(qtrEndDate.toString()))
						ps.setDate(2, qtrBeginDate);
					else
						ps.setDate(2, beginDate);
					ps.setDate(3, prevDate);
					ps.setString(4, IConstants.SMART);
					ps.setDate(5, prevDate);
					ps.setDate(6, qtrDate);
					ps.setDate(7, monthEndDate);
					rs = ps.executeQuery();	
					
					while(rs.next()){		
						dlr = new PaymentDealerInfo();
						
						dlr.setDlrId(rs.getString("dlr"));
						dlr.setTotal(rs.getDouble("total"));
						dlr.setVehType(rs.getString("vehType"));
						dlr.setVehCount(rs.getInt("vehcount"));
						dlr.setText(rs.getString("text"));
												
						dealerList.add(dlr);
					}
				}
				else if( n == 3){
					ps = con.prepareStatement(ICommonQueryConstants.GET_DEALER_INFO);
					ps.setString(1, IConstants.VAN);
					if (currentDate.toString().equals(qtrEndDate.toString()))
						ps.setDate(2, qtrBeginDate);
					else
						ps.setDate(2, beginDate);
					ps.setDate(3, prevDate);
					ps.setString(4, IConstants.VAN);
					ps.setDate(5, prevDate);
					ps.setDate(6, qtrDate);
					ps.setDate(7, monthEndDate);
					rs = ps.executeQuery();	
					
					while(rs.next()){		
						dlr = new PaymentDealerInfo();
						
						dlr.setDlrId(rs.getString("dlr"));
						dlr.setTotal(rs.getDouble("total"));
						dlr.setVehType(rs.getString("vehType"));
						dlr.setVehCount(rs.getInt("vehcount"));
						dlr.setText(rs.getString("text"));
												
						dealerList.add(dlr);
					}
				}
				else if( n == 4){
					ps = con.prepareStatement(ICommonQueryConstants.GET_CARS_DEALER_INFO); //quarterly
					if (currentDate.toString().equals(qtrEndDate.toString())){
						ps.setDate(1, qtrBeginDate);
						ps.setDate(2, prevDate);
						ps.setDate(3, qtrBeginDate);
					}
					else{
						ps.setDate(1, beginDate);
						ps.setDate(2, prevDate);
						ps.setDate(3, beginDate);
					}
					ps.setDate(4, prevDate);
					ps.setDate(5, prevDate);
					ps.setDate(6, prevDate);
					ps.setDate(7, qtrDate);
					ps.setDate(8, monthEndDate);
					ps.setDate(9, qtrDate);
					ps.setDate(10, monthEndDate);
					
					rs = ps.executeQuery();	
					
					while(rs.next()){		
						dlr = new PaymentDealerInfo();
						
						dlr.setDlrId(rs.getString("dlr"));
						dlr.setTotal(rs.getDouble("total"));
						dlr.setVehType(rs.getString("vehType"));
						dlr.setVehCount(rs.getInt("vehcount"));
						dlr.setText(rs.getString("text"));
												
						dealerList.add(dlr);
					}
				} //Separating AMG Payment file from regular Cars payment file (11-Jul-2016).  
				else if( n == 5){
					ps = con.prepareStatement(ICommonQueryConstants.GET_AMG_CARS_DEALER_INFO); //quarterly
					if (currentDate.toString().equals(qtrEndDate.toString())){
						ps.setDate(1, qtrBeginDate);
						ps.setDate(2, prevDate);
					}
					else{
						ps.setDate(1, beginDate);
						ps.setDate(2, prevDate);
					}
					
					ps.setDate(3, prevDate);
					ps.setDate(4, qtrDate);
					ps.setDate(5, monthEndDate);
										
					rs = ps.executeQuery();	
					
					while(rs.next()){		
						dlr = new PaymentDealerInfo();
						
						dlr.setDlrId(rs.getString("dlr"));
						dlr.setTotal(rs.getDouble("total"));
						dlr.setVehType(rs.getString("vehType"));
						dlr.setVehCount(rs.getInt("vehcount"));
						dlr.setText(rs.getString("text"));
												
						dealerList.add(dlr);
					}
				}
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.COMMON.DAO.PAYMENT.001",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.COMMON.DAO.PAYMENT.002",ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
				LOGGER.error("DPB.COMMON.DAO.PAYMENT.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);	
		return dealerList;
	}
	
	
	public List<PaymentInfo> getPaymentData(Date beginDate, Date monthEndDate,Date prevDate,Date currentDate,Date qtrEndDate,Date qtrBeginDate,Date qtrDate,int n)
	{
		final String methodName = "getPaymentData";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		List<PaymentInfo> paymentList = null;
		PaymentInfo  dlrPymt = null;	
		try
		{		
			con  = conFactory.getConnection();
			paymentList = new ArrayList<PaymentInfo>();
			if (con != null) {	
				if(n == 0){
					ps = con.prepareStatement(ICommonQueryConstants.GET_DAILY_PAYMENT_INFO);
					ps.setDate(1, prevDate);
					ps.setDate(2, prevDate);
				}
				else if( n == 1){
					Date calcDate = beginDate;
					Calendar c = Calendar.getInstance();
					c.setTime(beginDate);
					c.add(Calendar.DATE, 1);
					calcDate = new java.sql.Date(c.getTime().getTime());
					
					ps = con.prepareStatement(ICommonQueryConstants.GET_PAYMENT_INFO);
					ps.setString(1, IConstants.FTL);
					ps.setDate(2, beginDate);
					ps.setDate(3, prevDate);
					ps.setString(4, IConstants.FTL);
					ps.setDate(5, prevDate);
					ps.setDate(6, calcDate);
					ps.setDate(7, monthEndDate);
					
					LOGGER.info("2: " + beginDate + " --> 3: " + prevDate +
							" --> 5: " + prevDate + " --> 6: " + calcDate + 
							" --> 7: " + monthEndDate);
					
				}
				else if( n == 2) {
					ps = con.prepareStatement(ICommonQueryConstants.GET_PAYMENT_INFO);
					ps.setString(1, IConstants.SMART);
					if (currentDate.toString().equals(qtrEndDate.toString()))
						ps.setDate(2, qtrBeginDate);
					else
						ps.setDate(2, beginDate);
					ps.setDate(3, prevDate);
					ps.setString(4, IConstants.SMART);
					ps.setDate(5, prevDate);
					ps.setDate(6, qtrDate);
					ps.setDate(7, monthEndDate);
				}
				else if( n == 3) {
					ps = con.prepareStatement(ICommonQueryConstants.GET_PAYMENT_INFO);
					ps.setString(1, IConstants.VAN);
					if (currentDate.toString().equals(qtrEndDate.toString()))
						ps.setDate(2, qtrBeginDate);
					else
						ps.setDate(2, beginDate);
					ps.setDate(3, prevDate);
					ps.setString(4, IConstants.VAN);
					ps.setDate(5, prevDate);
					ps.setDate(6, qtrDate);
					ps.setDate(7, monthEndDate);
				}
				else if( n == 4) {
					ps = con.prepareStatement(ICommonQueryConstants.GET_CARS_PAYMENT_INFO);
					if (currentDate.toString().equals(qtrEndDate.toString())){
						ps.setDate(1, qtrBeginDate);
						ps.setDate(2, prevDate);
						ps.setDate(3, qtrBeginDate);
					}
					else{
						ps.setDate(1, beginDate);
						ps.setDate(2, prevDate);
						ps.setDate(3, beginDate);
					}
					ps.setDate(4, prevDate);
					ps.setDate(5, prevDate);
					ps.setDate(6, prevDate);
					ps.setDate(7, qtrDate);
					ps.setDate(8, monthEndDate);
					ps.setDate(9, qtrDate);
					ps.setDate(10, monthEndDate);
					
				} //Separating AMG Payment file from regular Cars payment file (11-Jul-2016). 
				else if( n == 5) {
					ps = con.prepareStatement(ICommonQueryConstants.GET_AMG_CARS_PAYMENT_INFO);
					if (currentDate.toString().equals(qtrEndDate.toString())){
						ps.setDate(1, qtrBeginDate);
						ps.setDate(2, prevDate);
					}
					else{
						ps.setDate(1, beginDate);
						ps.setDate(2, prevDate);
					}
				
					ps.setDate(3, prevDate);
					ps.setDate(4, qtrDate);
					ps.setDate(5, monthEndDate);
				}
			}
			resultSet = ps.executeQuery();											
			while(resultSet.next()){		
			dlrPymt = new PaymentInfo();
					
			dlrPymt.setDlrId(resultSet.getString("id_dlr"));
			dlrPymt.setVehicleType(resultSet.getString("vehType"));
			dlrPymt.setPgmId(resultSet.getInt("id_dpb_pgm"));
			dlrPymt.setAcctId(resultSet.getString("dbId"));
			dlrPymt.setBnsCalcAmt(resultSet.getDouble("amt_dpb_bns_calc"));
			dlrPymt.setPoNumber(resultSet.getString("po"));
			dlrPymt.setTxtName(resultSet.getString("text"));
					
			paymentList.add(dlrPymt);
		}
	}catch(SQLException  e){
			LOGGER.error("DPB.COMMON.DAO.PAYMENT.001",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
			LOGGER.error("DPB.COMMON.DAO.PAYMENT.002",ne);
			DPBExceptionHandler.getInstance().handleException(ne);
	}catch (Exception e) {
			e.printStackTrace();
	}
	finally {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
				LOGGER.error("DPB.COMMON.DAO.PAYMENT.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);	
	return paymentList;
	}
	
	
	public List<Integer> getProcIdDetails(Date currentDate)
	{
		final String methodName = "getProcIdDetails";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Integer> procId	= new ArrayList<Integer>();
		try{		
			con  = conFactory.getConnection();
			ps = con.prepareStatement(ICommonQueryConstants.GET_PROCID_INFO);
			rs = ps.executeQuery();											
			while(rs.next()){		
				procId.add(rs.getInt("id_dpb_proc"));
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.COMMON.DAO.PAYMENT.001",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.COMMON.DAO.PAYMENT.002",ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
				LOGGER.error("DPB.COMMON.DAO.PAYMENT.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);	
		return procId;
	}
	
	
	
	
	public List<Integer> getCalcIdDetails (Date beginDate, Date prevDate,Date currentDate,Date monthEndDate,Date qtrEndDate,Date qtrBeginDate,int k){	
		final String methodName = "getCalcIdDetails";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Integer> calcId	= new ArrayList<Integer>();
		try {
			con = conFactory.getConnection();			
			String sqlQuery = "";
				
			if (k==0){
				sqlQuery = ICommonQueryConstants.GET_DAILY_CALCID_INFO;
				ps = con.prepareStatement(sqlQuery);	
				ps.setDate(1, prevDate);
				ps.setDate(2, prevDate);
			}
			else if (k==1){
				sqlQuery = ICommonQueryConstants.GET_CALCID_INFO;
				ps = con.prepareStatement(sqlQuery);	
				ps.setString(1, IConstants.FTL);
				ps.setDate(2, beginDate);
				ps.setDate(3, prevDate);
				ps.setString(4, IConstants.FTL);
				ps.setDate(5, prevDate);
				ps.setDate(6, monthEndDate);
			}
			else if (k==2){
				sqlQuery = ICommonQueryConstants.GET_CALCID_INFO;
				ps = con.prepareStatement(sqlQuery);	
				ps.setString(1, IConstants.SMART);
				ps.setDate(2, qtrBeginDate);
				ps.setDate(3, prevDate);
				ps.setString(4, IConstants.SMART);
				ps.setDate(5, prevDate);
				ps.setDate(6, monthEndDate);
			}
			
			else if (k==3){
				sqlQuery = ICommonQueryConstants.GET_CALCID_INFO;
				ps = con.prepareStatement(sqlQuery);	
				ps.setString(1, IConstants.VAN);
				ps.setDate(2, qtrBeginDate);
				ps.setDate(3, prevDate);
				ps.setString(4, IConstants.VAN);
				ps.setDate(5, prevDate);
				ps.setDate(6, monthEndDate);
			}
			else if (k==4){
				sqlQuery = ICommonQueryConstants.GET_CARS_CALCID_INFO;
				ps = con.prepareStatement(sqlQuery);	
				ps.setDate(1, qtrBeginDate);
				ps.setDate(2, prevDate);
				ps.setDate(3, qtrBeginDate);
				ps.setDate(4, prevDate);
				ps.setDate(5, prevDate);
				ps.setDate(6, prevDate);
				ps.setDate(7, monthEndDate);
				ps.setDate(8, monthEndDate);
			}
			else if (k==5){
				sqlQuery = ICommonQueryConstants.GET_AMGCARS_CALCID_INFO;
				ps = con.prepareStatement(sqlQuery);	
				ps.setDate(1, qtrBeginDate);
				ps.setDate(2, prevDate);
				ps.setDate(3, prevDate);
				ps.setDate(4, monthEndDate);
			}
			
			
			rs = ps.executeQuery();
			while(rs.next()) {					
				calcId.add(rs.getInt("id_dpb_calc"));
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.COMMON.PAYMENT.RECORDS.DAO.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.COMMON.PAYMENT.RECORDS.DAO.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
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
		return calcId;
}
	
	
	public void updateCalcDetails(List<Integer> calcIdList){	
		final String methodName = "updateCalcDetails";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		int updateCount = 0;
		try {
			con = conFactory.getConnection();			
			String sqlQuery = "";
			
				sqlQuery = ICommonQueryConstants.UDPATE_PAYMENT_STATUS;
				
			if(calcIdList!= null && calcIdList.size() > 0){	
				Iterator<Integer> calcItr = calcIdList.iterator();
				ps = con.prepareStatement(sqlQuery);
				while(calcItr.hasNext()){
					Integer cId = calcItr.next();	
					ps.setInt(1,cId);
					ps.addBatch();
					updateCount++;
					if(updateCount == 300){
						ps.executeBatch();
						updateCount = 0;
					}
				}
				if(updateCount > 0){
					ps.executeBatch();
				}			
			}						
		} catch (SQLException e) {
			LOGGER.error("DPB.COMMON.UPDATE.BNS.PAID.DAO.001", e);
			LOGGER.error("DPB.COMMON.UPDATE.BNS.PAID.DAO.0011", e.getNextException());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.COMMON.UPDATE.BNS.PAID.DAO.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
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
	}
	
	
	public List<LeadershipInfo> getLdrshipData(Integer pgmId)
	{
		final String methodName = "getLdrshipData";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LeadershipInfo  ldr = null;		
		List<LeadershipInfo> ldrList	= new ArrayList<LeadershipInfo>();
		try{		
			con  = conFactory.getConnection();
			ps = con.prepareStatement(ICommonQueryConstants.LDRSHIP_INFO);
			rs = ps.executeQuery();											
			while(rs.next()){	
					
				ldr = new LeadershipInfo();
				ldr.setDlrId(rs.getString("dlr"));
				ldr.setTotal(rs.getDouble("total"));
					
				ldrList.add(ldr);
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.COMMON.DAO.PAYMENT.001",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.COMMON.DAO.PAYMENT.002",ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
				LOGGER.error("DPB.COMMON.DAO.PAYMENT.003",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);	
		return ldrList;
	}
	
	//Data Cleanup for process rerun start
	/* (non-Javadoc)
	 * @see com.mbusa.dpb.common.dao.IDPBCommonDAO#cleanUpData(java.lang.String)
	 */
	@Override
	public Map<Integer, String> cleanUpData(String procDate) throws Exception {
		Map<Integer, String> result = new HashMap<Integer, String>();
    	final String methodName = "cleanUpData";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {	
			con = conFactory.getConnection();
			
			// Cleaning up data from DPB_PROC_LOG table.
			ps = con.prepareStatement("delete from dpb_proc_log where id_dpb_proc in (select id_dpb_proc from dpb_proc where dte_cal = ?) with ur");			
			ps.setString(1,procDate);
			int result1 =ps.executeUpdate();
			ps.clearParameters();

			// Cleaning up data from DPB_CALC table.
			ps = con.prepareStatement("delete from dpb_calc where id_dpb_proc in (select id_dpb_proc from dpb_proc where dte_cal = ?) with ur");			
			ps.setString(1,procDate);
			int result2 =ps.executeUpdate();
			ps.clearParameters();
			
			// Updating data in DPB_PROC table.
			ps = con.prepareStatement("update dpb_proc set cde_dpb_ovrd_proc_init_typ = null where dte_cal = ? and cde_dpb_ovrd_proc_init_typ is not null with ur");
			ps.setString(1,procDate);
			int result3 =ps.executeUpdate();
			ps.clearParameters();
			
			// Cleaning up data from DPB_KPI_FIL_EXTRT table.
			ps = con.prepareStatement("delete from DPB_KPI_FIL_EXTRT where id_dpb_proc in (select id_dpb_proc from dpb_proc where dte_cal = ?) with ur");			
			ps.setString(1,procDate);
			int result4 =ps.executeUpdate();
			ps.clearParameters();
			
			result.put(1, result1+"");
			result.put(2, result2+"");
			result.put(3, result3+"");
			result.put(4, result4+"");
		} catch (SQLException e) {
			LOGGER.error("Exception Occured while deleting/updating the data", e);
			e.printStackTrace(pw); 
			result.put(5, sw.toString());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
			
		} catch (NamingException ne) {
			LOGGER.error("Exception Occured while inserting the data", ne);
			ne.printStackTrace(pw); 
			result.put(5, sw.toString());
			DPBExceptionHandler.getInstance().handleException(ne);
			
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
				if (pw != null) {
					pw.close();
				}
				if (sw != null) {
					sw.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				result.put(5, sw.toString());
				if(sw != null)
					sw.close();
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return result;
	}
	//Data Cleanup for process rerun end
	
}
