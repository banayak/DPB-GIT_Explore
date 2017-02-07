/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: DefinitionDAOImpl.java
 * Program Version			: 1.0
 * Program Description		: This class is used handle Database related functionality like CRUD operation 
 * 							  business type validation if require. 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 29, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.mbusa.dpb.common.constant.ICommonQueryConstants;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.constant.IDefinitionQueryConstants;
import com.mbusa.dpb.common.db.ConnectionFactory;
import com.mbusa.dpb.common.domain.AMGDealer;
import com.mbusa.dpb.common.domain.AccountIdMappingDefinition;
import com.mbusa.dpb.common.domain.AccountMapping;
import com.mbusa.dpb.common.domain.ConditionDefinition;
import com.mbusa.dpb.common.domain.FieldColumnMapBean;
import com.mbusa.dpb.common.domain.FileDefinitionBean;
import com.mbusa.dpb.common.domain.FileFormatBean;
import com.mbusa.dpb.common.domain.FileProcessDefBean;
import com.mbusa.dpb.common.domain.FileProcessLogMessages;
import com.mbusa.dpb.common.domain.GeneratePaymentFile;
import com.mbusa.dpb.common.domain.GenerateReportFile;
import com.mbusa.dpb.common.domain.LeadershipBonusDetails;
import com.mbusa.dpb.common.domain.ProcessBonus;
import com.mbusa.dpb.common.domain.ProcessCalDefBean;
import com.mbusa.dpb.common.domain.ProcessInputFile;
import com.mbusa.dpb.common.domain.ProcessLdrBonus;
import com.mbusa.dpb.common.domain.ProgramDefinition;
import com.mbusa.dpb.common.domain.QCRelationBean;
import com.mbusa.dpb.common.domain.ReScheduleProcess;
import com.mbusa.dpb.common.domain.ReportContentDefinitionBean;
import com.mbusa.dpb.common.domain.ReportDefinitionBean;
import com.mbusa.dpb.common.domain.ReportQuery;
import com.mbusa.dpb.common.domain.RetailDate;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.domain.RtlMonthDefinition;
import com.mbusa.dpb.common.domain.VehicleConditionMapping;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.DPBExceptionHandler;
import com.mbusa.dpb.common.exceptions.PersistanceException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DateCalenderUtil;


public class DefinitionDAOImpl implements IDefinitionDAO {
	private ConnectionFactory conFactory = ConnectionFactory.getConnectionFactory();
	private static DPBLog LOGGER = DPBLog.getInstance(DefinitionDAOImpl.class);
	static final private String CLASSNAME = DefinitionDAOImpl.class.getName();
	public static final String LDRSP_BNS_PROC_TYP   = "LB"; 
	public static final String LDRSP_PAY_PROC_TYP   = "LP"; 
	public static final String YES   = "Y";
	
	public int saveDlrProgram(ProgramDefinition programDefBean) {
		LOGGER.enter(CLASSNAME, "saveDlrProgram()");
			Connection con = null;
			PreparedStatement ps=null;
			ResultSet rs = null;
			int pId = 0;
			try{
				con = conFactory.getConnection();			
				String query= IDefinitionQueryConstants.INSERT_DPB_DEFINITION;
				if(!IConstants.EMPTY_STR.equalsIgnoreCase(query)){
				ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				
				ps.setString(1, programDefBean.getProgramName());
				ps.setString(2, programDefBean.getDescription());
				ps.setDate(3,programDefBean.getStartDate());
				ps.setDate(4, programDefBean.getEndDate());
				ps.setString(5, programDefBean.getRebateAmt());
				ps.setString(6, programDefBean.getCommPayment());
				ps.setDouble(7, programDefBean.getCommAmount());
				//ps.setDouble(8, programDefBean.getCommPercent());
				ps.setDouble(8,programDefBean.getFixedPayment());
				
				ps.setString(9,programDefBean.getComsnProcessSchedule());
				ps.setString(10,programDefBean.getComsnProcessTrigger());
				ps.setString(11, programDefBean.getComsnPymtSchedule());
				ps.setString(12,programDefBean.getComsnPymtTrigger());				
				
				ps.setString(13,programDefBean.getFixedPymtBonusSchedule());
				ps.setString(14,programDefBean.getFixedPymtBonusTrigger());
				ps.setString(15, programDefBean.getFixedPymtSchedule());
				ps.setString(16,programDefBean.getFixedPymtTrigger());
				ps.setString(17, programDefBean.getPaymentType());
				ps.setString(18, programDefBean.getMaxVarPayment());
				ps.setDouble(19, programDefBean.getVariablePayment());
				
				ps.setString(20,programDefBean.getVariablePymtBonusSchedule());
				ps.setString(21,programDefBean.getVariablePymtBonusTrigger());
				ps.setString(22, programDefBean.getVariablePymtSchedule());
				ps.setString(23,programDefBean.getVariablePymtTrigger());
				ps.setString(24,programDefBean.getSpecialProgram());
				ps.setInt(25, programDefBean.getKpiId());
				ps.setString(26,programDefBean.getProgramStatus());
				ps.setString(27, programDefBean.getCreatedBy());
				ps.setString(28, programDefBean.getUpdateBy());
				
				ps.executeUpdate();
				
				 rs = ps.getGeneratedKeys();          
				 if(rs != null && rs.next()){             
					 pId =rs.getInt(1);
					 programDefBean.setProgramId(rs.getInt(1));
				}
				}	
			}catch(SQLException  e){
				LOGGER.error("DPB.DEF.PGM.001", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}catch(NamingException  ne){
				LOGGER.error("DPB.DEF.PGM.002", ne);
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
					LOGGER.error("DPB.DEF.PGM.003", e);
					DPBExceptionHandler.getInstance().handleDatabaseException(e);
				}
			}	
			return pId;
		}
	public void insertVehicleType(ProgramDefinition programDefBean,int pId){
		LOGGER.enter(CLASSNAME, "insertVehicleType()");
			Connection con = null;
			PreparedStatement ps=null;	
			try{
				List<String> vehType = programDefBean.getVehicleType();
				con = conFactory.getConnection();
				String query = IDefinitionQueryConstants.INSERT_VEHICLE_TYPE;

				//ps = con.prepareStatement(query);
				
				ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				for(int i=0; i < vehType.size();i++){
					String vId = vehType.get(i);
					ps.setInt(1, pId);
					ps.setString(2, vId);
					ps.setString(3, "D");
					ps.setString(4, programDefBean.getCreatedBy());
					ps.setString(5, programDefBean.getUpdateBy());
					ps.executeUpdate();
				}

			}catch(SQLException  e){
				LOGGER.error("DPB.DEF.PGM.004", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}catch(NamingException  ne){
				LOGGER.error("DPB.DEF.PGM.005", ne);
				DPBExceptionHandler.getInstance().handleException(ne);
			}finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					LOGGER.error("DPB.DEF.PGM.006", e);
					DPBExceptionHandler.getInstance().handleDatabaseException(e);
				}
			}
		}

		public void getDlrProgram(ProgramDefinition prgmDef){	
			LOGGER.enter(CLASSNAME, "getDlrProgram()");
			Connection con = null;
			ResultSet rs= null;		
			PreparedStatement ps = null;

			try {
				con = conFactory.getConnection();

				String query= IDefinitionQueryConstants.GET_DEALER_PROGRAM;
				ps = con.prepareStatement(query);
				ps.setInt(1, prgmDef.getProgramId());
				List<String> vehType = null;
				List<String> condList = null;
				rs= ps.executeQuery();
				int oldId = 0;
				while(rs.next()){

					int currentId = rs.getInt(1);
					if(oldId != currentId){// first record				
						vehType = new ArrayList<String>();
						condList = new ArrayList<String>();
						vehType.add(rs.getString("CDE_VEH_TYP"));
						condList.add(rs.getString("ID_DPB_CND"));
						prgmDef.setProgramId(rs.getInt("ID_DPB_PGM"));
						prgmDef.setProgramName(rs.getString("NAM_DPB_PGM"));
						prgmDef.setDescription(rs.getString("DES_DPB_PGM"));
						prgmDef.setStartDate(rs.getDate("DTE_DPB_PGM_START"));
						prgmDef.setEndDate(rs.getDate("DTE_DPB_PGM_END"));
						prgmDef.setKpiId(rs.getInt("ID_KPI"));
						prgmDef.setVehicleType(vehType);
						prgmDef.setSpecialProgram(rs.getString("CDE_DPB_STS"));
						prgmDef.setRebateAmt(rs.getString("IND_DPB_RBT_PMT"));
						prgmDef.setCommPayment(rs.getString("IND_DPB_COMSN_PMT"));
						prgmDef.setCommAmount(rs.getDouble("AMT_DPB_COMSN_PMT"));
						
						//prgmDef.setCommPercent(rs.getDouble("PCT_DPB_COMSN_PMT"));
						prgmDef.setFixedPayment(rs.getDouble("PCT_FXD_PMT"));
						
						prgmDef.setComsnProcessSchedule(rs.getString("CDE_COMSN_BNS_PROC_SCHD"));
						prgmDef.setComsnProcessTrigger(rs.getString("CDE_COMSN_PROC_INIT_TYP"));
						prgmDef.setComsnPymtSchedule(rs.getString("CDE_COMSN_PMT_SCHD"));
						prgmDef.setComsnPymtTrigger(rs.getString("CDE_COMSN_PMT_PROC_INIT_TYP"));

						prgmDef.setFixedPymtBonusSchedule(rs.getString("CDE_FXD_BNS_SCHD"));
						prgmDef.setFixedPymtBonusTrigger(rs.getString("CDE_FXD_BNS_PROC_INIT_TYP"));
						prgmDef.setFixedPymtSchedule(rs.getString("CDE_FXD_PMT_SCHD"));
						prgmDef.setFixedPymtTrigger(rs.getString("CDE_FXD_PMT_PROC_INIT_TYP"));
						prgmDef.setPaymentType(rs.getString("IND_DPB_FXD_PMT"));
						prgmDef.setMaxVarPayment(rs.getString("IND_MAX_VAR_PMT"));
						prgmDef.setVariablePayment(rs.getDouble("PCT_VAR_PMT"));
						prgmDef.setVariablePymtBonusSchedule(rs.getString("CDE_VAR_BNS_SCHD"));
						prgmDef.setVariablePymtBonusTrigger(rs.getString("CDE_VAR_BNS_PROC_INIT_TYP"));
						prgmDef.setVariablePymtSchedule(rs.getString("CDE_VAR_PMT_SCHD"));
						prgmDef.setVariablePymtTrigger(rs.getString("CDE_VAR_PMT_PROC_INIT_TYP"));
						prgmDef.setSpecialProgram(rs.getString("CDE_DPB_PGM_TYP"));
						prgmDef.setProgramStatus(rs.getString("CDE_DPB_STS"));
						prgmDef.setInactiveDate(rs.getDate("DTE_INACT"));
						prgmDef.setCondition(condList);
					}else{
						if(!vehType.contains(rs.getString("CDE_VEH_TYP")))
						vehType.add(rs.getString("CDE_VEH_TYP"));
						if(!condList.contains(rs.getString("ID_DPB_CND")))
						condList.add(rs.getString("ID_DPB_CND"));
					}				
					oldId = currentId;	
				}
			}
			catch(SQLException  e){
				LOGGER.error("DPB.DEF.PGM.007", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}catch(NamingException  ne){
				LOGGER.error("DPB.DEF.PGM.008", ne);
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
					LOGGER.error("DPB.DEF.PGM.009", e);
					DPBExceptionHandler.getInstance().handleDatabaseException(e);
				}
			}
	}
		public List<ProgramDefinition> getDlrProgramList(){	
			LOGGER.enter(CLASSNAME, "getDlrProgramList()");
			Connection con = null;
			ResultSet rs= null;
			Statement st = null;
			List<ProgramDefinition> prgList = new ArrayList<ProgramDefinition>();
			try {
				con = conFactory.getConnection();

				String query=IDefinitionQueryConstants.RETRIEVE_DLR_LIST;
				st = con.createStatement();
				rs= st.executeQuery(query);
				ProgramDefinition prgDef =  null;
				while(rs.next()){
						prgDef = new ProgramDefinition();
						prgDef.setProgramId(rs.getInt("ID_DPB_PGM"));
						prgDef.setProgramName(rs.getString("NAM_DPB_PGM"));
						prgDef.setStartDate(rs.getDate("DTE_DPB_PGM_START"));
						prgDef.setEndDate(rs.getDate("DTE_DPB_PGM_END"));
						prgDef.setKpiId(rs.getInt("ID_KPI"));
						prgDef.setProgramStatus(rs.getString("CDE_DPB_STS"));	
						prgDef.setSpecialProgram(rs.getString("CDE_DPB_PGM_TYP"));
						prgList.add(prgDef);
				}		
			}catch(SQLException  e){
				LOGGER.error("DPB.DEF.PGM.010", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}catch(NamingException  ne){
				LOGGER.error("DPB.DEF.PGM.011", ne);
				DPBExceptionHandler.getInstance().handleException(ne);
			}finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (st != null) {
						st.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					LOGGER.error("DPB.DEF.PGM.012", e);
					DPBExceptionHandler.getInstance().handleDatabaseException(e);
				}
			}
			return prgList;
	}

		public void updateDlrProgram(ProgramDefinition programDefBean){		
			LOGGER.enter(CLASSNAME, "updateDlrProgram()");
			Connection con = null;
			PreparedStatement ps=null;
			ResultSet rs = null;
			try{
				con = conFactory.getConnection();

				String query= IDefinitionQueryConstants.UPDATE_DPB_DEFINITION;
				if(!IConstants.EMPTY_STR.equalsIgnoreCase(query)){
					ps = con.prepareStatement(query);
					
					ps.setString(1, programDefBean.getProgramName());
					ps.setString(2, programDefBean.getDescription());
					ps.setDate(3,programDefBean.getStartDate());
					ps.setDate(4, programDefBean.getEndDate());
					ps.setString(5, programDefBean.getRebateAmt());
					ps.setString(6, programDefBean.getCommPayment());
					ps.setDouble(7, programDefBean.getCommAmount());
					//ps.setDouble(8, programDefBean.getCommPercent());
					
					ps.setDouble(8,programDefBean.getFixedPayment());

					ps.setString(9,programDefBean.getComsnProcessSchedule());
					ps.setString(10,programDefBean.getComsnProcessTrigger());
					ps.setString(11, programDefBean.getComsnPymtSchedule());
					ps.setString(12,programDefBean.getComsnPymtTrigger());		
					
					ps.setString(13,programDefBean.getFixedPymtBonusSchedule());
					ps.setString(14,programDefBean.getFixedPymtBonusTrigger());
					ps.setString(15,programDefBean.getFixedPymtSchedule());
					ps.setString(16,programDefBean.getFixedPymtTrigger());
					
					ps.setString(17,programDefBean.getPaymentType());
					ps.setString(18, programDefBean.getMaxVarPayment());
					
					ps.setDouble(19, programDefBean.getVariablePayment());
					ps.setString(20,programDefBean.getVariablePymtBonusSchedule());
					ps.setString(21,programDefBean.getVariablePymtBonusTrigger());
					ps.setString(22,programDefBean.getVariablePymtSchedule());
					ps.setString(23,programDefBean.getVariablePymtTrigger());
					
					ps.setString(24, programDefBean.getSpecialProgram());
					ps.setInt(25, programDefBean.getKpiId());
					ps.setString(26,programDefBean.getProgramStatus());
					ps.setString(27,programDefBean.getUpdateBy());
					ps.setInt(28, programDefBean.getProgramId());
					if(ps!=null){
						ps.executeUpdate();
					}

				}	
			}catch(SQLException  e){
				LOGGER.error("DPB.DEF.PGM.013", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}catch(NamingException  ne){
				LOGGER.error("DPB.DEF.PGM.014", ne);
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
					LOGGER.error("DPB.DEF.PGM.015", e);
					DPBExceptionHandler.getInstance().handleDatabaseException(e);
				}
			}
	}

	@Override
	public void updateVehicleType(ProgramDefinition programDef, int pId){
		LOGGER.enter(CLASSNAME, "updateVehicleType()");
		try{
			deleteExistingVehTypes(pId);
			if(programDef.getVehicleType()!=null){
				insertVehicleType(programDef, pId);
			}

		}catch(Exception e){
			LOGGER.error("DPB.DEF.PGM.016", e);
			e.printStackTrace();
		}
	}

	@Override
	public void deleteExistingVehTypes(int pId) {
		LOGGER.enter(CLASSNAME, "deleteExistingVehTypes()");
		Connection con = null;
		PreparedStatement ps=null;
		try{

			con = conFactory.getConnection();
			String query= IDefinitionQueryConstants.DELETE_VEH_TYPES;
			ps=con.prepareStatement(query);
			ps.setInt(1,pId);
			ps.execute();
		}catch(SQLException  e){
			LOGGER.error("DPB.DEF.PGM.017", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.DEF.PGM.018", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("DPB.DEF.PGM.019", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
	}

	public void insertCondition(ProgramDefinition programDefBean,int pId) {	
		LOGGER.enter(CLASSNAME, "insertCondition()");
		Connection con = null;
		PreparedStatement ps=null;
		try{
			List<String> condition = programDefBean.getCondition();
			con = conFactory.getConnection();
			String query= IDefinitionQueryConstants.INSERT_DPB_CONDITION;

			ps = con.prepareStatement(query);

			for(int i=0; i < condition.size();i++){
				ps.setInt(1, pId);
				int abe = Integer.parseInt((String) condition.get(i));
				ps.setInt(2, abe);
				ps.setString(3, programDefBean.getCreatedBy());
				ps.setString(4, programDefBean.getUpdateBy());
				ps.executeUpdate();
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.DEF.PGM.020", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.DEF.PGM.021", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("DPB.DEF.PGM.022", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
	}
		
		@Override
		public void deleteExistingConditions(ProgramDefinition programDef, int pId){
			LOGGER.enter(CLASSNAME, "deleteExistingConditions()");
			Connection con = null;
			PreparedStatement ps=null;
			try{
				con = conFactory.getConnection();
				String query= IDefinitionQueryConstants.DELETE_CONDITIONS;
				ps=con.prepareStatement(query);
				ps.setInt(1, pId);
				ps.execute();
			}catch(SQLException  e){
				LOGGER.error("DPB.DEF.PGM.023", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}catch(NamingException  ne){
				LOGGER.error("DPB.DEF.PGM.024", ne);
				DPBExceptionHandler.getInstance().handleException(ne);
			}finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					LOGGER.error("DPB.DEF.PGM.025", e);
					DPBExceptionHandler.getInstance().handleDatabaseException(e);
				}
			}
	}

		public void updateExistingConditions(ProgramDefinition programDef, int pId){
			LOGGER.enter(CLASSNAME, "updateExistingConditions()");		
			try{
				deleteExistingConditions(programDef, pId);
				if(programDef.getCondition()!=null){
					insertCondition(programDef, pId);
				}

			}catch(Exception e){
				LOGGER.error("DPB.DEF.PGM.026", e);
				e.printStackTrace();
			}
		}
		
		public void insertDPBProcessDef(ProgramDefinition programDef,Map<String, List<java.sql.Date>> aProcDteListMap) throws TechnicalException, PersistanceException {
			LOGGER.enter(CLASSNAME, "insertDPBProcessDef()");	
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;			
			List<java.sql.Date> datesList = null;			
			String query =IConstants.EMPTY_STR;
			try{
				query = IDefinitionQueryConstants.INSERT_DPB_PROCESS_NEW;
				con = conFactory.getConnection();
				ps = con.prepareStatement(query);
				for (Object key : aProcDteListMap.keySet()) {
					String procTyp = (String)key;
					datesList = aProcDteListMap.get(procTyp);
					for(int i=0; i< datesList.size() ;i++){	
						java.sql.Date rtlDate = datesList.get(i);
						ps.setInt(1, programDef.getProgramId());
						ps.setString(2, procTyp);
						ps.setDate(3, rtlDate);
						ps.setDate(4, rtlDate);
						ps.setString(5, programDef.getCreatedBy());
						ps.setString(6, programDef.getUpdateBy());											
						ps.addBatch();
					}
					ps.executeBatch();
				}
			}catch(SQLException  e){
				LOGGER.error("DPB.DEF.PGM.027", e);
				e.getNextException();
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}catch(NamingException  ne){
				LOGGER.error("DPB.DEF.PGM.028", ne);
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
					LOGGER.error("DPB.DEF.PGM.029", e);
					DPBExceptionHandler.getInstance().handleDatabaseException(e);
				}
			}
		}
		@Override
		public boolean validateEndDate(java.sql.Date endDate){
			LOGGER.enter(CLASSNAME, "validateEndDate()");	
			Connection con = null;
			PreparedStatement ps=null;	
			ResultSet rs = null;
			boolean returnValidEndDate = false;

			try{
				con = conFactory.getConnection();
				String query=IDefinitionQueryConstants.RETRIEVE_VALID_ENDDATE;

				ps = con.prepareStatement(query);
				ps.setDate(1, endDate);
				rs=	ps.executeQuery();
				if(rs.next()){
					if(rs.getDate("DTE_CAL")!=null){
					returnValidEndDate= true;
					}
				}
			}catch(SQLException  e){
				LOGGER.error("DPB.DEF.PGM.030", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}catch(NamingException  ne){
				LOGGER.error("DPB.DEF.PGM.031", ne);
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
					LOGGER.error("DPB.DEF.PGM.032", e);
					DPBExceptionHandler.getInstance().handleDatabaseException(e);
				}
			}

			return returnValidEndDate;
		}
		@Override
		public void updateInactiveDate(ProgramDefinition programDef){
			LOGGER.enter(CLASSNAME, "updateInactiveDate()");
			Connection con = null;
			PreparedStatement ps=null;	

			try{
				java.sql.Date nextDay = DateCalenderUtil.convertStringToSQLDateFormat(DateCalenderUtil.getNextDayDate());
				con = conFactory.getConnection();
				ps = con.prepareStatement(IDefinitionQueryConstants.INACTIVATE_DPB_PROGRAM);
				/*if(programDef.isFlagActive()){*/
					ps.setDate(1,nextDay);
				/*} else {
				ps.setDate(1,todayDate);
				}*/
				ps.setString(2, programDef.getProgramStatus());
				ps.setString(3, programDef.getUpdateBy());
				ps.setInt(4, programDef.getProgramId());
				ps.executeUpdate();
			}catch(SQLException  e){
				LOGGER.error("DPB.DEF.PGM.033", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}catch(NamingException  ne){
				LOGGER.error("DPB.DEF.PGM.034", ne);
				DPBExceptionHandler.getInstance().handleException(ne);
			}finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					LOGGER.error("DPB.DEF.PGM.035", e);
					DPBExceptionHandler.getInstance().handleDatabaseException(e);
				}
			}
	}



	public void updateDPBProcess(FileDefinitionBean fileDefBean){
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = conFactory.getConnection();
			String query = IDefinitionQueryConstants.INSERT_DPB_FILE_PROCESS;
			statement = con.prepareStatement(query);
			statement.setInt(1, fileDefBean.getFileDefId());
			statement.setString(2, "File Process");
			statement.executeUpdate();
			statement.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Override
	public void deleteProcesses(int programId, List<String> processTypes) {
		LOGGER.enter(CLASSNAME, "updateInactiveDate()");
		Connection con = null;
		PreparedStatement ps=null;	

		try{
			StringBuffer pTypeList = null;
			String query = IConstants.EMPTY_STR;
			String newQuery = IConstants.EMPTY_STR;
			int size = processTypes.size();
			if(size > 0){
			con = conFactory.getConnection();
			if(processTypes.contains(IConstants.FILE_PROCESS_NAME) || 
					processTypes.contains(IConstants.FILE_PRCS_ADJ)){
				query = IDefinitionQueryConstants.DELETE_FILE_PROCESSES;
			} else if(processTypes.contains(IConstants.REPORT_PROCESS_NAME)){
				query = IDefinitionQueryConstants.DELETE_RPT_PROCESSES;
			} 
			else if(processTypes.contains(IConstants.LEADERSHIP_PAYMENT_PROCESS)){
				query = IDefinitionQueryConstants.DELETE_PROCESSES_LDRSP;
			}else
				query = IDefinitionQueryConstants.DELETE_PROCESSES;// Defaults to Program Definition temporarily
			
			pTypeList = new StringBuffer(" and CDE_DPB_PROC_TYP in (");
			for(int i =0; i< processTypes.size() ; i++){
				if( i+1 == processTypes.size()){
					pTypeList.append("?");
			}else {
				pTypeList.append("?"+",");
			}
			}
			pTypeList.append(")");
			newQuery = query.concat(pTypeList.toString());
			ps = con.prepareStatement(newQuery);
			ps.setInt(1, programId);
			for(int i =0; i< processTypes.size() ; i++){
			ps.setString(i+2, processTypes.get(i));
			}
			ps.executeUpdate();
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.DEF.PGM.036", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.DEF.PGM.037", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("DPB.DEF.PGM.035", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
}
// *******************************start of condition block ************************************************
	public int createCondition(ConditionDefinition cDef){
		int cId = 0 ;		
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		try {
			con = conFactory.getConnection();
			ps = con.prepareStatement(IDefinitionQueryConstants.INSERT_INTO_DPB_CONDITION,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,cDef.getConditionName());
			ps.setString(2,cDef.getConditionDesc());
			ps.setString(3,cDef.getCondition());
			ps.setString(4,cDef.getVariableName());
			ps.setString(5,cDef.getCheckValue());
			ps.setString(6,cDef.getConditionType());
			ps.setString(7,cDef.getRegularExp());
			ps.setString(8,cDef.getStatus());
			ps.setString(9,cDef.getCurrentUser());
			ps.setString(10,cDef.getCurrentUser());			
			ps.executeUpdate();
				
			rs = ps.getGeneratedKeys();          
			 if(rs != null && rs.next()){             
				 cId = rs.getInt(1);				
			}		
			con.close();
			
		}catch(SQLException  e){
			LOGGER.error("DPB.COND.CREATE.01", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.COND.CREATE.02", ne);
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
				LOGGER.error("DPB.COND.CREATE.03", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}	
		return cId;	
	}
	public int updateConditionDetails(ConditionDefinition cDef){
	    Connection con = null;
	    PreparedStatement ps=null;
	    ResultSet rs = null;
	    int cId=cDef.getId();
	  
	    try{
	        con = conFactory.getConnection();
	      	ps=con.prepareStatement(IDefinitionQueryConstants.UPDATE_ON_DPB_CONDITION);
	      	ps.setString(1, cDef.getConditionName());
	      	ps.setString(2, cDef.getConditionDesc());
	      	ps.setString(3, cDef.getCondition());
			ps.setString(4, cDef.getVariableName());
			ps.setString(5, cDef.getCheckValue());
			ps.setString(6, cDef.getConditionType());
			ps.setString(7, cDef.getRegularExp());
			ps.setString(8, cDef.getStatus());
			ps.setString(9, cDef.getCurrentUser());       
			ps.setInt(10, cDef.getId());	        
			ps.executeUpdate();
			
	    }catch(SQLException  e){
	    	LOGGER.error("DPB.COND.UPDATE.01", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.COND.UPDATE.02", ne);
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
				LOGGER.error("DPB.COND.UPDATE.03", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}	
		return cId;
	}

	public ConditionDefinition getCondtionDetails(int cId){	
		ConditionDefinition condDef= new ConditionDefinition();
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {			
			conn = conFactory.getConnection();
			ps = conn.prepareStatement(IDefinitionQueryConstants.RETRIVE_FROM_DPB_CONDITION);
			ps.setInt(1, cId);			
			rs = ps.executeQuery();			
			while(rs.next()){				
				condDef.setId(rs.getInt("ID_DPB_CND"));
				condDef.setConditionName(rs.getString("NAM_DPB_CND"));
				condDef.setConditionDesc(rs.getString("DES_DPB_CND"));
				condDef.setCondition(rs.getString("CDE_DPB_CND"));
				condDef.setVariableName(rs.getString("NAM_DPB_VAR"));
				condDef.setCheckValue(rs.getString("TXT_DPB_CHK_VAL"));	
				condDef.setConditionType(rs.getString("CDE_DPB_CND_TYP"));	
				condDef.setRegularExp(rs.getString("TXT_DPB_REG_EX"));
				condDef.setStatus(rs.getString("CDE_DPB_STS"));
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.COND.DETAIL.01", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.COND.DETAIL.02", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				LOGGER.error("DPB.COND.DETAIL.03", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}		
		return condDef;
	}
	public List<ConditionDefinition> getConditionList(){
		ArrayList<ConditionDefinition> list=new ArrayList<ConditionDefinition>();
		Connection conn = null;			
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {			
			conn = conFactory.getConnection();			
			ps = conn.prepareStatement(IDefinitionQueryConstants.RETRIVE_LIST_FROM_DPB_CONDITION);
			rs = ps.executeQuery();
			while(rs.next()){
				ConditionDefinition condDef= new ConditionDefinition();
				condDef.setId(rs.getInt("ID_DPB_CND"));
				condDef.setConditionName(rs.getString("NAM_DPB_CND"));
				condDef.setConditionDesc(rs.getString("DES_DPB_CND"));
				condDef.setCondition(rs.getString("CDE_DPB_CND"));
				condDef.setVariableName(rs.getString("NAM_DPB_VAR"));
				condDef.setCheckValue(rs.getString("TXT_DPB_CHK_VAL"));	
				condDef.setConditionType(rs.getString("CDE_DPB_CND_TYP"));	
				condDef.setRegularExp(rs.getString("TXT_DPB_REG_EX"));
				condDef.setStatus(rs.getString("CDE_DPB_STS"));

				list.add(condDef);				
			}		
		}catch(SQLException  e){
			LOGGER.error("DPB.COND.LIST.02", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.COND.LIST.02", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				LOGGER.error("DPB.COND.LIST.03", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}	
		return list;	
}
	

	@Override
	public boolean inactivateCondition(ConditionDefinition cDef) throws BusinessException {
		  Connection con = null;
		    PreparedStatement ps=null;
		    ResultSet rs = null;
		    int cId=cDef.getId();
		    boolean condition = false;
		    try{
		    	if(cDef.isFlagActive()){
		    	condition = validateCondition(cId);
		    	}
		    	if(!condition){
		        con = conFactory.getConnection();
		        ps=con.prepareStatement(IDefinitionQueryConstants.INACTIVE_CONDITION);
		      	ps.setString(1, cDef.getStatus());
		      	ps.setString(2, cDef.getCurrentUser());       
				ps.setInt(3, cDef.getId());	        
				ps.executeUpdate();
		    	} else {
		    		cDef.setStatus("A");
		    		throw new BusinessException("Warning1", "Cannot InActivate Condition as it is being used by other program");
		    		
		    	}
		    }
		
		catch(SQLException  e){
			LOGGER.error("DPB.COND.INACTIVE.01", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.COND.INACTIVE.02", ne);
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
				LOGGER.error("DPB.COND.INACTIVE.003", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}	
		return condition;
}
	
	@Override
	public boolean validateCondition(int cId) {
		final String methodName = "validateInactiveProgram";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExists = false;
		try {
			con = conFactory.getConnection();
			String query = IDefinitionQueryConstants.VALIDATE_CONDITION;
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs != null && rs.next()){
				if(rs.getInt(1) == cId){
					isExists = true;
					break;
				}else {
					isExists = false;
				}
			}

		}catch (SQLException e) {
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
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

		return isExists;
}
	//  **********************************end of condition block***********************************************

	public int saveFileDefinition(FileDefinitionBean fileDef){
		final String methodName = "saveFileDefinition";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int fileDefId = 0;
		
		try {
			con = conFactory.getConnection();
			String query = IDefinitionQueryConstants.CREATE_FILE_DEF_QYERY;
			statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, fileDef.getFileFormatId());
			statement.setString(2, fileDef.getFileDefName());
			statement.setString(3, fileDef.getDescription());
			statement.setString(4, fileDef.getFileNameFormat());
			statement.setTime(5, fileDef.getInputTime());
			statement.setTime(6, fileDef.getProceTime());
			statement.setString(7, fileDef.getBaseFolder());
			statement.setString(8, fileDef.getScheduleCode());
			statement.setString(9, fileDef.getTriggerCode());
			statement.setString(10, fileDef.getIndCondition());
			statement.setString(11, fileDef.getDefStatusCode());
			statement.setString(12, fileDef.getCreatedUserId());
			statement.setString(13, fileDef.getLastUpdUserId());
			statement.executeUpdate();
			rs = statement.getGeneratedKeys(); 
			if(rs != null && rs.next()){ 
			fileDefId = rs.getInt(1);
			}
			statement.close();

		} catch(SQLException e){
			LOGGER.error("DPB.FILE.DEF.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException ne){
			LOGGER.error("DPB.FILE.DEF.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}finally {
            try {
                if (rs != null) {
                      rs.close();
                }
                if (con != null) {
                      con.close();
                }
          } catch (SQLException e) {
        	  LOGGER.error("DPB.FILE.DEF.016", e);
                DPBExceptionHandler.getInstance().handleDatabaseException(e);
          }
    }
		LOGGER.exit(CLASSNAME, methodName);
		return fileDefId;
	}
	
	public java.sql.Date getEndDate(java.sql.Date endDate){
		LOGGER.enter(CLASSNAME, "getEndDate");	
		Connection con = null;
		PreparedStatement ps=null;	
		ResultSet rs = null;
		java.sql.Date returnValidEndDate = null;

		try{
			con = conFactory.getConnection();
			String query=IDefinitionQueryConstants.RETRIEVE_VALID_ENDDATE;

			ps = con.prepareStatement(query);
			ps.setDate(1, endDate);
			rs=	ps.executeQuery();
			if(rs.next()){
				returnValidEndDate = rs.getDate(1);
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.END.DATE.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.END.DATE.002", ne);
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
				LOGGER.error("DPB.END.DATE.003", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}

		return returnValidEndDate;
	}
	
	public void insertDPBFileProcess(FileDefinitionBean fileDefBean, List<java.sql.Date> aSchdDatesList, String prcsType) throws BusinessException{
		LOGGER.enter(CLASSNAME, "insertDPBProcessDef()");     
        //List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //java.sql.Date startDate = DateCalenderUtil.convertStringToSQLDateFormat(DateCalenderUtil.getNextDayDate());
        //PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
		//boolean isDevelopment = PROPERTY_MANAGER.getPropertyAsBoolean("dpb.development");
		try{
		/*if(isDevelopment){
			java.sql.Date rtlDate = DateCalenderUtil.getCurrentDateTime();
			String query=IDefinitionQueryConstants.INSERT_DPB_FILE_PROCESS;
            con = conFactory.getConnection();
            ps = con.prepareStatement(query);
            		 ps.setDate(1, rtlDate);
            		 ps.setDate(2, rtlDate);
                	 ps.setInt(3, fileDefBean.getFileDefId());
                     ps.setString(4, prcsType);
                     ps.setString(5, fileDefBean.getCreatedUserId());
                     ps.setString(6, fileDefBean.getLastUpdUserId());
                     ps.executeUpdate();
         }else{*/
        	//dateList = DateCalenderUtil.scheduleList(startDate, insertDPBFileProcess, fileDefBean.getScheduleCode());
        	if(aSchdDatesList ==  null){
        		throw new BusinessException("proc", "DPB Process could not be scheduled");
            }else if(aSchdDatesList.isEmpty()){
            	throw new BusinessException("invSchd", "Retail dates are not found for selected schedule");
            }
        	else if(!aSchdDatesList.isEmpty()){
        		int size = aSchdDatesList.size();
    			int count=1;
                    String query=IDefinitionQueryConstants.INSERT_DPB_FILE_PROCESS;
                    con = conFactory.getConnection();
                    ps = con.prepareStatement(query);
                        for(java.sql.Date rtlDate:aSchdDatesList ){
                        	 ps.setDate(1, rtlDate);
                        	 ps.setDate(2, rtlDate); 
                        	 ps.setInt(3, fileDefBean.getFileDefId());
                             ps.setString(4, prcsType);
                             ps.setString(5, fileDefBean.getCreatedUserId());
                             ps.setString(6, fileDefBean.getLastUpdUserId());
                          ps.addBatch();
                          if(size == count || count == 500){
                        	ps.executeBatch();
      						size = size - count;
      						count = 0;
      					  }
      					count++;
                        }
             }        	 
        /*}*/
		}
        catch(SQLException  e){
        	LOGGER.error("DPB.FILE.DEF.011", e);
              DPBExceptionHandler.getInstance().handleDatabaseException(e);
        }catch(NamingException  ne){
        	LOGGER.error("DPB.FILE.DEF.012", ne);
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
              } catch (SQLException e) {
            	  LOGGER.error("DPB.FILE.DEF.015", e);
                    DPBExceptionHandler.getInstance().handleDatabaseException(e);
              }
        }
  }
	
	public void updateFileDefinition(FileDefinitionBean fileDef){
		final String methodName = "updateFileDefinition";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement statement = null;
		
		try {
			con = conFactory.getConnection();
			String query = IDefinitionQueryConstants.UPDATE_FILE_DEF_QUERY;
			statement = con.prepareStatement(query);
			statement.setInt(1, fileDef.getFileFormatId());
			statement.setString(2, fileDef.getFileDefName());
			statement.setString(3, fileDef.getDescription());
			statement.setString(4, fileDef.getFileNameFormat());
			statement.setTime(5, fileDef.getInputTime());
			statement.setTime(6, fileDef.getProceTime());
			statement.setString(7, fileDef.getBaseFolder());
			statement.setString(8, fileDef.getScheduleCode());
			statement.setString(9, fileDef.getTriggerCode());
			statement.setString(10, fileDef.getIndCondition());
			statement.setString(11, fileDef.getDefStatusCode());
			statement.setString(12, fileDef.getLastUpdUserId());
			statement.setInt(13, fileDef.getFileDefId());
			statement.executeUpdate();
			statement.close();

		} catch(SQLException e){
			LOGGER.error("DPB.FILE.DEF.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
			//throw new RuntimeException();
		}catch(NamingException ne){
			LOGGER.error("DPB.FILE.DEF.004", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}finally {
            try {
                if (statement != null) {
                      statement.close();
                }
                if (con != null) {
                      con.close();
                }
          } catch (SQLException e) {
        	  LOGGER.error("DPB.FILE.DEF.017", e);
                DPBExceptionHandler.getInstance().handleDatabaseException(e);
          }
    }
		LOGGER.exit(CLASSNAME, methodName);
	}

	public void updateFileInactiveStatus(FileDefinitionBean fileDef){
		final String methodName = "updateFileInactiveStatus";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement statement = null;
		java.sql.Date today = DateCalenderUtil.getCurrentDateTime();
		java.sql.Date nextDay = DateCalenderUtil.convertStringToSQLDateFormat(DateCalenderUtil.getNextDayDate());
		try {
			con = conFactory.getConnection();
			String query = IDefinitionQueryConstants.UPDATE_DPB_FILE_INACTIVE;
			statement = con.prepareStatement(query);
			statement.setString(1, fileDef.getDefStatusCode());
			statement.setString(2, fileDef.getLastUpdUserId());
			statement.setInt(4, fileDef.getFileDefId());
			if(!fileDef.isFlagActive()){
				statement.setDate(3, today);
			}
			else{
				statement.setDate(3, nextDay);
			}
			statement.executeUpdate();
			statement.close();

		} catch(SQLException e){
			LOGGER.error("DPB.FILE.DEF.INACT.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
			//throw new RuntimeException();
		}catch(NamingException ne){
			LOGGER.error("DPB.FILE.DEF.INACT.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		}finally {
            try {
                if (statement != null) {
                      statement.close();
                }
                if (con != null) {
                      con.close();
                }
          } catch (SQLException e) {
        	  LOGGER.error("DPB.FILE.DEF.INACT.003", e);
                DPBExceptionHandler.getInstance().handleDatabaseException(e);
          }
    }
		LOGGER.exit(CLASSNAME, methodName);
	}
	
public List<FileFormatBean> populateformatNameList() {
	final String methodName = "populateformatNameList";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	FileFormatBean fileFormatBean = null;
	List<FileFormatBean> fileFormat = new ArrayList<FileFormatBean>();
	//fileFormat = fileDefForm.getFileFormat();
	try {
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.FILE_FORMAT_NAME_LIST_QUERY;
		statement = con.prepareStatement(query);
		rs = statement.executeQuery();
		while (rs.next()) {
			fileFormatBean = new FileFormatBean();
			fileFormatBean.setFileFormatId(rs.getInt("ID_DPB_FILE_FMT"));
			fileFormatBean.setFormatName(rs.getString("NAM_DPB_FIL_FMT")!=null ? rs.getString("NAM_DPB_FIL_FMT").trim():null);
			fileFormat.add(fileFormatBean);
		}
		//fileDefForm.setFileFormat(fileFormat);
		statement.close();
	} catch(SQLException e){
		LOGGER.error("DPB.FILE.DEF.009", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		//throw new RuntimeException();
	}catch(NamingException ne){
		LOGGER.error("DPB.FILE.DEF.010", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
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
      	LOGGER.error("DPB.FILE.DEF.019", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
	return fileFormat;
}

	
public List<FileDefinitionBean> getFileDefList(){
	final String methodName = "getFileDefList";
	LOGGER.enter(CLASSNAME, methodName);
	List<FileDefinitionBean>  list = new ArrayList<FileDefinitionBean>();
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;

	try {
		con  = conFactory.getConnection();
		String query = IDefinitionQueryConstants.RETRIEVE_FILE_DEF_LIST_QUERY;
		if (con != null) {
			statement = con.prepareStatement(query);
			rs = statement.executeQuery();
			while (rs.next()) {
				FileDefinitionBean fileDef = new FileDefinitionBean();
				fileDef.setFileDefId(rs.getInt("ID_DPB_FILE"));
				fileDef.setFileFormatId(rs.getInt("ID_DPB_FILE_FMT"));
				fileDef.setFileDefName(rs.getString("NAM_DPB_FIL")!=null ? rs.getString("NAM_DPB_FIL").trim():null);
				fileDef.setInputTime(rs.getTime("TME_FILE_IN"));
				fileDef.setProceTime(rs.getTime("TME_FILE_PROC"));
				fileDef.setTriggerCode(rs.getString("CDE_DPB_PROC_INIT_TYP"));
				fileDef.setDefStatusCode(rs.getString("CDE_DPB_STS"));
				fileDef.getFileFormats().setFormatName(rs.getString("NAM_DPB_FIL_FMT")!=null ? rs.getString("NAM_DPB_FIL_FMT").trim():null);
				list.add(fileDef);
			}
		}
	}catch(SQLException e){
		LOGGER.error("DPB.FILE.DEF.005", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		//throw new RuntimeException();
	}catch(NamingException ne){
		LOGGER.error("DPB.FILE.DEF.006", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
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
      	LOGGER.error("DPB.FILE.DEF.020", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
	return list;
}

public FileDefinitionBean getEditFileDetails(String fileId){
	final String methodName = "getEditFileDetails";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	FileDefinitionBean fileDef = new FileDefinitionBean();
	try {
		con  = conFactory.getConnection();
		String query = IDefinitionQueryConstants.RETRIEVE_FILE_EDIT_QUERY;
		if (con != null) {
			statement = con.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(fileId));
			rs = statement.executeQuery();
			while (rs.next()) {
				fileDef.setFileDefId(rs.getInt("ID_DPB_FILE"));
				fileDef.setFileFormatId(rs.getInt("ID_DPB_FILE_FMT"));
				fileDef.setFileDefName(rs.getString("NAM_DPB_FIL")!=null ? rs.getString("NAM_DPB_FIL").trim():null);
				fileDef.setDescription(rs.getString("DES_DPB_BNS_FIL")!=null ? rs.getString("DES_DPB_BNS_FIL").trim():null);
				fileDef.setFileNameFormat(rs.getString("TXT_DPB_FMT_FILE_NAM"));
				fileDef.setInputTime(rs.getTime("TME_FILE_IN"));
				fileDef.setProceTime(rs.getTime("TME_FILE_PROC"));
				fileDef.setBaseFolder(rs.getString("TXT_DPB_BASE_DPTH")!=null ? rs.getString("TXT_DPB_BASE_DPTH").trim():null);
				fileDef.setScheduleCode(rs.getString("CDE_DPB_SCHD"));
				fileDef.setTriggerCode(rs.getString("CDE_DPB_PROC_INIT_TYP"));
				fileDef.setIndCondition(rs.getString("IND_DPB_FIL_CND"));
				fileDef.setDefStatusCode(rs.getString("CDE_DPB_STS"));
				fileDef.getFileFormats().setFormatName(rs.getString("NAM_DPB_FIL_FMT"));
			}
		}
	}catch(SQLException e){
		LOGGER.error("DPB.FILE.DEF.007", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		//throw new RuntimeException();
	}catch(NamingException ne){
		LOGGER.error("DPB.FILE.DEF.008", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
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
      	LOGGER.error("DPB.FILE.DEF.021", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
	return fileDef;
}

public int saveFileFormatDetails(FileFormatBean formatBean){
	final String methodName = "saveFileFormatDetails";
		LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	int fileFormatId = 0;
	try {
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.CREATE_FILE_FORMAT_QUERY;
		statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, formatBean.getFormatName());
		statement.setString(2, formatBean.getFormatDescription());
		statement.setString(3, formatBean.getInbountFileIndicator());
		statement.setString(4, formatBean.getIndHeader());
		statement.setString(5, formatBean.getIndDelimited().equalsIgnoreCase("") ? null:formatBean.getIndDelimited());
		statement.setInt(6, formatBean.getFixedLineWidth());
		statement.setInt(7, formatBean.getColumnCount());
		statement.setString(8, formatBean.getTableName());
		statement.setString(9, formatBean.getDefStatusCode());
		statement.setString(10, formatBean.getCreatedUserId());
		statement.setString(11, formatBean.getLastUpdUserId());
		statement.executeUpdate();
		rs = statement.getGeneratedKeys(); 
		if(rs != null && rs.next()){ 
		fileFormatId = rs.getInt(1);
		}
		statement.close();

	} catch(SQLException e){
		LOGGER.error("DPB.FILE.FMT.DEF.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		//throw new RuntimeException();
	}catch(NamingException ne){
		LOGGER.error("DPB.FILE.FMT.DEF.002", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
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
    	  LOGGER.error("DPB.FILE.FMT.DEF.003", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
	return fileFormatId;
}

public FileFormatBean populateColumnMapList(FileFormatBean formatBean){
	final String methodName = "populateColumnMapList";
	LOGGER.enter(CLASSNAME, methodName);
	/*Iterator<FieldColumnMapBean> itr;
	FieldColumnMapBean mapBean;*/
	List<FieldColumnMapBean> columnMapList = new ArrayList<FieldColumnMapBean>();
	List<FieldColumnMapBean> list = formatBean.getFileMapingList(); 
	if(list!=null){
		updateFieldColumnMapDetails(formatBean);
		for(FieldColumnMapBean mapBean: list){
		/*for (itr=list.iterator();itr.hasNext();){
			mapBean = itr.next();*/
		    int fileColumnMapId = saveFieldColumnMapDetails(mapBean, formatBean);
		   	mapBean.setFileColumnMapId(fileColumnMapId);
		    columnMapList.add(mapBean);
		}
	}
	formatBean.setFileMapingList(columnMapList);
	LOGGER.exit(CLASSNAME, methodName);
	return formatBean;
}
	
public int saveFieldColumnMapDetails(FieldColumnMapBean colMapBean, FileFormatBean formatBean){
	final String methodName = "saveFieldColumnMapDetails";
	LOGGER.enter(CLASSNAME, methodName);
	int fileColumnMapId = 0;
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	try {
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.CREATE_FIELD_MAP_QUERY;
		statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, formatBean.getFileFormatId());
		statement.setInt(2, colMapBean.getFileColumnSeqNumber());
		statement.setString(3, colMapBean.getFileColumnformatText());
		statement.setInt(4, colMapBean.getFileColumnLength());
		statement.setString(5, formatBean.getTableName());
		statement.setString(6, colMapBean.getColumnName());
		statement.setInt(7, colMapBean.getKpi().getKpiId());
		statement.setString(8, formatBean.getCreatedUserId());
		statement.setString(9, formatBean.getLastUpdUserId());
		statement.executeUpdate();
		rs = statement.getGeneratedKeys();
		if(rs != null && rs.next()){ 
		fileColumnMapId =rs.getInt(1);
		}
		statement.close();

	} catch(SQLException e){
		LOGGER.error("DPB.FILE.FMT.DEF.004", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		//throw new RuntimeException();
	}catch(NamingException ne){
		LOGGER.error("DPB.FILE.FMT.DEF.005", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
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
    	  LOGGER.error("DPB.FILE.FMT.DEF.006", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
	return fileColumnMapId;
}

public List<String> populateColumns(String tableName){
	final String methodName = "populateColumns";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	List<String> columnNames = null;
	try {
		columnNames = new ArrayList<String>();
		con  = conFactory.getConnection();
		String query = "SELECT COLNAME from SYSCAT.COLUMNS where TABNAME =?";
		if (con != null) {
			statement = con.prepareStatement(query);
			statement.setString(1, tableName);
			rs = statement.executeQuery();
			while (rs.next()) {
				String column = rs.getString(1);
				columnNames.add(column.trim());
			}
		}
	}catch(SQLException e){
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		//throw new RuntimeException();
	}catch(NamingException ne){
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
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
      	LOGGER.error("DPB.FILE.DEF.020", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
	return columnNames;
}
	
public void updateFileFormatDetails(FileFormatBean formatBean){
	final String methodName = "updateFileFormatDetails";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	try {
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.UPDATE_FILE_FORMAT_QUERY;
		statement = con.prepareStatement(query);
		statement.setString(1, formatBean.getFormatName());
		statement.setString(2, formatBean.getFormatDescription());
		statement.setString(3, formatBean.getInbountFileIndicator());
		statement.setString(4, formatBean.getIndHeader());
		statement.setString(5, formatBean.getIndDelimited().equalsIgnoreCase("") ? null:formatBean.getIndDelimited());
		statement.setInt(6, formatBean.getFixedLineWidth());
		statement.setInt(7, formatBean.getColumnCount());
		statement.setString(8, formatBean.getTableName());
		statement.setString(9, formatBean.getDefStatusCode());
		statement.setString(10, formatBean.getLastUpdUserId());
		statement.setInt(11, formatBean.getFileFormatId());
		statement.executeUpdate();
		statement.close();

	} catch(SQLException e){
		LOGGER.error("DPB.FILE.FMT.DEF.007", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		//throw new RuntimeException();
	}catch(NamingException ne){
		LOGGER.error("DPB.FILE.FMT.DEF.008", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
        try {
            if (statement != null) {
                  statement.close();
            }
            if (con != null) {
                  con.close();
            }
      } catch (SQLException e) {
    	  LOGGER.error("DPB.FILE.FMT.DEF.009", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
}

public void updateFileFormatInactiveStatus(FileFormatBean formatBean) throws BusinessException{
	final String methodName = "updateFileFormatInactiveStatus";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement statement = null;
	try {
		con = conFactory.getConnection();
		String chkQyery = IDefinitionQueryConstants.CHECK_INACTIVE_FILE_STATUS;
		statement = con.prepareStatement(chkQyery);
		statement.setInt(1, formatBean.getFileFormatId());
		rs = statement.executeQuery();
		if(!rs.next()){
			String query = IDefinitionQueryConstants.UPDATE_FILE_FORMAT_INACTIVE_QUERY;
			statement = con.prepareStatement(query);
			statement.setString(1, formatBean.getDefStatusCode());
			statement.setString(2, formatBean.getLastUpdUserId());
			statement.setInt(3, formatBean.getFileFormatId());
			statement.executeUpdate();
			statement.close();
		}else{
			throw new BusinessException("msg", "Can not inactivate, Record is being used by file definition");
		}

	} catch(SQLException e){
		LOGGER.error("DPB.FILE.FMT.INACT.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		//throw new RuntimeException();
	}catch(NamingException ne){
		LOGGER.error("DPB.FILE.FMT.INACT.001", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
        try {
            if (statement != null) {
                  statement.close();
            }
            if (con != null) {
                  con.close();
            }
      } catch (SQLException e) {
    	  LOGGER.error("DPB.FILE.FMT.INACT.001", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
}
	
public FileFormatBean getEditFileFormatDetails(String fileId){
	final String methodName = "getEditFileFormatDetails";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	FileFormatBean formatBean = null;
	FieldColumnMapBean mapBean = null;
	List<FieldColumnMapBean> columnMapList = new ArrayList<FieldColumnMapBean>();
	try {
		con  = conFactory.getConnection();
		String query = IDefinitionQueryConstants.RETRIEVE_FILE_FORMAT_EDIT_QUERY;
		if (con != null) {
			statement = con.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(fileId));
			rs = statement.executeQuery();
			formatBean = new FileFormatBean();
			while (rs.next()) {
				mapBean = new FieldColumnMapBean();
				formatBean.setFileFormatId(rs.getInt("ID_DPB_FILE_FMT"));
				formatBean.setFormatName(rs.getString("NAM_DPB_FIL_FMT")!=null ? rs.getString("NAM_DPB_FIL_FMT").trim():null);
				formatBean.setFormatDescription(rs.getString("DES_DPB_FIL_FMT")!=null ? rs.getString("DES_DPB_FIL_FMT").trim():null);
				formatBean.setInbountFileIndicator(rs.getString("IND_INBND_FILE"));
				formatBean.setIndHeader(rs.getString("IND_HDR"));
				formatBean.setIndDelimited(rs.getString("TXT_DLM")!=null ? rs.getString("TXT_DLM").trim():null);
				formatBean.setFixedLineWidth(rs.getInt("CNT_CPL"));
				formatBean.setColumnCount(rs.getInt("CNT_COL"));
				formatBean.setTableName(rs.getString("NAM_DPB_TBL")!=null ? rs.getString("NAM_DPB_TBL").trim():null);
				formatBean.setDefStatusCode(rs.getString("CDE_DPB_STS"));
				mapBean.setFileColumnMapId(rs.getInt("ID_DPB_FILE_COL_REL"));
				mapBean.setFileColumnSeqNumber(rs.getInt("NUM_FILE_COL_SEQ"));
				mapBean.setFileColumnformatText(rs.getString("TXT_DPB_FILE_COL_FMT")!=null ? rs.getString("TXT_DPB_FILE_COL_FMT").trim():null);
				mapBean.setFileColumnLength(rs.getInt("NUM_FILE_COL_LNTH"));
				mapBean.setTableName(rs.getString("NAM_TBL")!= null ? rs.getString("NAM_TBL").trim():null);
				mapBean.setColumnName(rs.getString("NAM_COL")!= null ? rs.getString("NAM_COL").trim():null);
				mapBean.getKpi().setKpiId(rs.getInt("ID_KPI"));
				mapBean.getKpi().setKpiName(rs.getString("NAM_KPI"));
				columnMapList.add(mapBean);
			}
			formatBean.setFileMapingList(columnMapList);
		}
	}catch(SQLException e){
		LOGGER.error("DPB.FILE.FMT.DEF.010", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		//throw new RuntimeException();
	}catch(NamingException ne){
		LOGGER.error("DPB.FILE.FMT.DEF.011", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
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
    	  LOGGER.error("DPB.FILE.FMT.DEF.012", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
	return formatBean;
}

public void updateFieldColumnMapDetails(FileFormatBean formatBean){
	final String methodName = "updateFieldColumnMapDetails";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	try {
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.UPDATE_FIELD_MAP_QUERY;
		statement = con.prepareStatement(query);
		statement.setInt(1, formatBean.getFileFormatId());
		statement.executeUpdate();
		statement.close();

	} catch(SQLException e){
		LOGGER.error("DPB.FILE.FMT.DEF.013", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		//throw new RuntimeException();
	}catch(NamingException ne){
		LOGGER.error("DPB.FILE.FMT.DEF.014", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
        try {
            if (statement != null) {
                  statement.close();
            }
            if (con != null) {
                  con.close();
            }
      } catch (SQLException e) {
    	  LOGGER.error("DPB.FILE.FMT.DEF.015", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
}
	
public List<FileFormatBean> getFileFormatListDetails(){
	final String methodName = "getFileFormatListDetails";
	LOGGER.enter(CLASSNAME, methodName);
	List<FileFormatBean> fileFormatList=null;
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	try {
		con  = conFactory.getConnection();
		String query = IDefinitionQueryConstants.RETRIEVE_FILE_FORMAT_LIST_QUERY;
		if (con != null) {
			fileFormatList = new ArrayList<FileFormatBean>();
			statement = con.prepareStatement(query);
			rs = statement.executeQuery();
			while (rs.next()) {
				FileFormatBean formatBean = new FileFormatBean();
				formatBean.setFileFormatId(rs.getInt("ID_DPB_FILE_FMT"));
				formatBean.setFormatName(rs.getString("NAM_DPB_FIL_FMT")!=null ? rs.getString("NAM_DPB_FIL_FMT").trim():null);
				formatBean.setInbountFileIndicator(rs.getString("IND_INBND_FILE"));
				formatBean.setIndHeader(rs.getString("IND_HDR"));
				formatBean.setIndDelimited(rs.getString("TXT_DLM"));
				formatBean.setColumnCount(rs.getInt("CNT_COL"));
				formatBean.setDefStatusCode(rs.getString("CDE_DPB_STS"));				fileFormatList.add(formatBean);
			}
		}
	}catch(SQLException e){
		LOGGER.error("DPB.FILE.FMT.DEF.016", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		//throw new RuntimeException();
	}catch(NamingException ne){
		LOGGER.error("DPB.FILE.FMT.DEF.017", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
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
    	  LOGGER.error("DPB.FILE.FMT.DEF.018", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
	return fileFormatList;
}
//******************************************	
	
public List<VehicleConditionMapping> getVehicleMappedCondition(String cType){
	Connection con = null;	 
	PreparedStatement ps = null;
	ResultSet rs = null;	
	VehicleConditionMapping vcMapping = null;
	List<VehicleConditionMapping> vcList = new ArrayList<VehicleConditionMapping>();	
	try {
		String oldId = "";
		con  = conFactory.getConnection();
		
		/*if(cType!= null &&  IConstants.COND_TYPE_B.equalsIgnoreCase(cType)){
			ps = con.prepareStatement(IDefinitionQueryConstants.RETRIVE_FROM_DPB_VEH_BLK_CND_REL_MAPPING);
		}else{*/
			ps = con.prepareStatement(IDefinitionQueryConstants.RETRIVE_FROM_VEH_TYP_CND_REL_MAPPING);
		//}	
			ps.setString(1, cType);
		rs = ps.executeQuery();	
		
		List <String>condtionList = new ArrayList<String>();
		String id = null;			
		while(rs.next()){
			id = rs.getString("CDE_VEH_TYP");			
			if(id.equalsIgnoreCase(oldId)){
				condtionList.add(rs.getString("ID_DPB_CND"));
			}else{
				 if(oldId!= null && !oldId.equalsIgnoreCase(IConstants.EMPTY_STR)){
					vcMapping.getConditionList().add(condtionList);
					vcList.add(vcMapping);
				 }
				vcMapping = new VehicleConditionMapping();				
				condtionList = new ArrayList<String>();
				condtionList.add(rs.getString("ID_DPB_CND"));
				vcMapping.setVehicleId(rs.getString("CDE_VEH_TYP"));				
				oldId = rs.getString("CDE_VEH_TYP");
			}					
		}
		if(oldId!= null && !oldId.equalsIgnoreCase(IConstants.EMPTY_STR)){
			vcMapping.getConditionList().add(condtionList);
			vcList.add(vcMapping);
		}		
	}catch(SQLException  e){
		LOGGER.error("DPB.GET.VEH.COND.MAP.01", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
		LOGGER.error("DPB.GET.VEH.COND.MAP.02", ne);
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
			LOGGER.error("DPB.GET.VEH.COND.MAP.03", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	return vcList;
}
public void saveVehicleMappedCondition(List<VehicleConditionMapping> vList,String cType){
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	VehicleConditionMapping vcMapping = null;
	
	try {	
		con = conFactory.getConnection();
		
		/*if(cType!= null &&  IConstants.COND_TYPE_B.equalsIgnoreCase(cType)){
			ps = con.prepareStatement(IDefinitionQueryConstants.INSERT_INTO_DPB_VEH_BLK_CND_REL);
		}else{ //Commmented for CR
		*/	ps = con.prepareStatement(IDefinitionQueryConstants.INSERT_INTO_VEH_TYP_CND_REL);
		//}
		for(int i =0; i < vList.size();i++){
			vcMapping = vList.get(i);
			List<List> list = (List<List>) vcMapping.getConditionList();
			String id = vcMapping.getVehicleId(); 
			String status = vcMapping.getStatus();
			String userId = vcMapping.getUserId();
			List <String>condtionList = (ArrayList<String>)list.get(0);
			for(int j = 0; j< condtionList.size();j++){				
					int cond = Integer.parseInt((String) condtionList.get(j)); 
					ps.setString(1,id);
					ps.setInt(2, cond);				
					ps.setString(3, userId);
					ps.setString(4, userId);
					ps.addBatch();	
					
			}
		}
		int[] rCount = ps.executeBatch();
	}catch(SQLException  e){
		LOGGER.error("DPB.SAVE.VEH.COND.MAP.03", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
		LOGGER.error("DPB.SAVE.VEH.COND.MAP.03", ne);
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
			LOGGER.error("DPB.SAVE.VEH.COND.MAP.03", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
}
public void updateVehicleMappedCondition(VehicleConditionMapping vclType){
	Connection con = null;
	PreparedStatement ps = null;	
	try {	
		con  = conFactory.getConnection();		
		
		if (con != null) {			
			ps = con.prepareStatement("update VEH_TYP_MAP set ID_VEH_TYP = ? ");
			ps.setInt(1, vclType.getId());
			int updateRecords = ps.executeUpdate();			
		}
	}catch(SQLException  e){
		LOGGER.error("DPB.UPDATE.VEH.COND.MAP.01", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
		LOGGER.error("DPB.UPDATE.VEH.COND.MAP.02", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}	
	finally {
		try {			
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.UPDATE.VEH.COND.MAP.03", e);
			DPBExceptionHandler.getInstance().handleException(e);
		}
	}
}
public int deleteMappingConditions(List<VehicleConditionMapping> vList,String cType)  {
	Connection con = null;
	int dCount = 0;
	PreparedStatement ps=null;
	VehicleConditionMapping vcMapping = null;
	try{	
		con = conFactory.getConnection();		
				
			ps = con.prepareStatement(IDefinitionQueryConstants.DELETE_FROM_VEH_TYP_CND_REL);
			
		// TODO - To check cType for Vehicle Blocked Conditions	
		for(int i =0; i < vList.size();i++){
			vcMapping = vList.get(i);
			List<List> list = (List<List>) vcMapping.getConditionList();
			List <String>condtionList = (ArrayList<String>)list.get(0);
			for(int j = 0; j< condtionList.size();j++){				
					int cond = Integer.parseInt((String) condtionList.get(j)); 
					ps.setInt(1,cond);
					ps.addBatch();	
		}		
		}
		 ps.executeBatch();
	} catch(SQLException  e){
		LOGGER.error("DPB.DELETE.VEH.COND.MAP.01", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
		LOGGER.error("DPB.DELETE.VEH.COND.MAP.02", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	} finally{
		try {
			con.close();
		} catch (SQLException e) {
			LOGGER.error("DPB.DELETE.VEH.COND.MAP.03", e);
		}
	}
	return dCount;
}
public List<ConditionDefinition> getVCMappingList(String cType){
	Connection con = null;		 
	PreparedStatement ps = null;
	ResultSet rs = null;	
	ConditionDefinition condDef = new ConditionDefinition();
	List <ConditionDefinition>vehicleCond = null;
	try {
		con  = conFactory.getConnection();
		ps = con.prepareStatement(IDefinitionQueryConstants.RETRIVE_COND_LIST_BASED_ON_TYPE);
		ps.setString(1, cType);
		rs = ps.executeQuery();	
		vehicleCond = new ArrayList<ConditionDefinition>();
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
			vehicleCond.add(condDef);
		}
	}catch(SQLException  e){
		LOGGER.error("DPB.GET.VEH.COND.MAP.LIST.01", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
		LOGGER.error("DPB.GET.VEH.COND.MAP.LIST.02", ne);
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
			LOGGER.error("DPB.GET.VEH.COND.MAP.LIST.03", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	return vehicleCond;
}

public int createRetailMonthEntry(RtlMonthDefinition rtlMonthDef){
	
	LOGGER.enter(CLASSNAME, "createRetailMonthEntry()");
	int idRtlDte = 0 ;
	int diff=0;
	int days=0;
	java.util.Date  endDatedb=null;
	RtlMonthDefinition toGetendDate=getStartDate();
	
	if(toGetendDate.getStartDate() == null)
	{
		endDatedb=rtlMonthDef.getStartDate();
		idRtlDte=insertStartDateRecord(rtlMonthDef,endDatedb);
		
	}
	else{
	  endDatedb=DateCalenderUtil.SqlDateToUtilDate(toGetendDate.getStartDate());
	}
	diff=endDatedb.compareTo(rtlMonthDef.getEndDate());
	if(diff > 0)
	{//Delete d records by days
		days=DateCalenderUtil.dateDifference(rtlMonthDef.getEndDate(),endDatedb);
		idRtlDte=deleteModDateRecord(days,endDatedb,rtlMonthDef);
		
	}
	else if(diff < 0)
	{
		//insert d records by days
		days=DateCalenderUtil.dateDifference(endDatedb,rtlMonthDef.getEndDate());
		idRtlDte=addAdditionaldateRecord(days,endDatedb,rtlMonthDef);
		
	}			
	
	LOGGER.exit(CLASSNAME, "createRetailMonthEntry()");
	return idRtlDte;

}


public int insertStartDateRecord(RtlMonthDefinition rtlMonthDef,Date endDatedb)
{
	LOGGER.enter(CLASSNAME, "insertStartDateRecord()");
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String query="";
	int idRtlDte = 0 ;
	java.sql.Date convDate = null ;
	
	
	query = "INSERT INTO RTL_CAL(DTE_RTL,QTR_FSCL,YEAR_RTL_FSC,NUM_RETL_YR, NUM_RETL_MTH,CDE_RCAL_DEF_STS,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA) VALUES(?,?,?,?,?,?,?,?,?,TIMESTAMP(CURRENT_DATE,TIME('00:00:00')))";	
	try {		
		con = conFactory.getConnection();
		if (con != null) {
					
			 		convDate = new java.sql.Date(endDatedb.getTime());
					ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);							
					ps.setDate(1,convDate);
					ps.setString(2,Integer.valueOf(rtlMonthDef.getQuarter()).toString());
					ps.setString(3,Integer.valueOf(rtlMonthDef.getYearSelection()).toString());
					ps.setString(4,Integer.valueOf(rtlMonthDef.getYearSelection()).toString());
					ps.setString(5, Integer.valueOf(rtlMonthDef.getMonthSelection()).toString());
					ps.setString(6,rtlMonthDef.getStatus());
					ps.setString(7,"USER1");
					ps.setString(8,"USER1");
					ps.setDate(9,convDate);
					int t = ps.executeUpdate();
				
				rs = ps.getGeneratedKeys(); 
			
				if(rs != null && rs.next()){
					idRtlDte= rs.getInt(1);				
				}
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.DEF.STRD.007",e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
		LOGGER.error("DPB.DEF.STRD.008",ne);
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
			LOGGER.error("DPB.DEF.STRD.009",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	} 
	LOGGER.exit(CLASSNAME,"insertStartDateRecord()");
	return idRtlDte;
}


public int deleteModDateRecord(int days,Date endDatedb,RtlMonthDefinition rtlMonthDef)
{
	LOGGER.enter(CLASSNAME, "deleteModDateRecord()");
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String query="";
	java.util.Date daysAddDay;
	java.sql.Date convDate = null ;
	int idRtlDte = 0 ;
	query = "delete from RTL_CAL where DTE_RTL=?";					
	try {
		con = conFactory.getConnection();
		if (con != null) {				
			for(int i=0;i <days;i++)
			{	
				daysAddDay=DateCalenderUtil.addDays(endDatedb,-i);
				convDate = new java.sql.Date(daysAddDay.getTime());			
				ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);					
				ps.setDate(1,convDate);			
				int t = ps.executeUpdate();
			}
			rs = ps.getGeneratedKeys();
			if(rs != null && rs.next()){
				idRtlDte= rs.getInt(1);				
		}
	}
	}catch(SQLException  e){
		LOGGER.error("DPB.DEF.CRTLM.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
		LOGGER.error("DPB.DEF.CRTLM.002", ne);
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
			LOGGER.error("DPB.DEF.CRTLM.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}	
	LOGGER.exit(CLASSNAME,"deleteModDateRecord()");
	return idRtlDte;
}

public int addAdditionaldateRecord(int days,Date endDatedb,RtlMonthDefinition rtlMonthDef )
{
	
	LOGGER.enter(CLASSNAME, "addAdditionaldateRecord()");
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String query="";
	java.util.Date daysAddDay;
	java.sql.Date convDate = null ;
	int idRtlDte = 0 ;

	
	query = "INSERT INTO RTL_CAL (DTE_RTL, QTR_FSCL,YEAR_RTL_FSC, NUM_RETL_YR, NUM_RETL_MTH, CDE_RCAL_DEF_STS, ID_CREA_USER, ID_LAST_UPDT_USER, DTS_LAST_UPDT, DTS_CREA) VALUES(?,?,?,?,?,?,?,?,?,TIMESTAMP(CURRENT_DATE,TIME('00:00:00')))";
	
	try {
		
			con = conFactory.getConnection();
			
				if (con != null) {
					
					for(int i=1;i <= days;i++)
					{	
							daysAddDay=DateCalenderUtil.addDays(endDatedb,i);
							convDate = new java.sql.Date(daysAddDay.getTime());
						 
							ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
							
							ps.setDate(1,convDate);
							ps.setString(2,Integer.valueOf(rtlMonthDef.getQuarter()).toString());
							ps.setString(3,Integer.valueOf(rtlMonthDef.getYearSelection()).toString());
							ps.setString(4,Integer.valueOf(rtlMonthDef.getYearSelection()).toString());
							ps.setString(5, Integer.valueOf(rtlMonthDef.getMonthSelection()).toString());
							ps.setString(6,rtlMonthDef.getStatus());
							ps.setString(7,"USER1");
							ps.setString(8,"USER1");
							ps.setDate(9,convDate);
							int t = ps.executeUpdate();
					}
					
						
						rs = ps.getGeneratedKeys(); 
						
						 if(rs != null && rs.next()){             
							
							 idRtlDte = rs.getInt(1);				
				}
				 
			}
		}catch(SQLException  e){
			LOGGER.error("DPB.DEF.CRTLM.004", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
			LOGGER.error("DPB.DEF.CRTLM.005",ne);
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
				LOGGER.error("DPB.DEF.CRTLM.006",e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
	LOGGER.exit(CLASSNAME,"addAdditionaldateRecord()");
	return idRtlDte;
}

public RtlMonthDefinition getStartDate()
{
	LOGGER.enter(CLASSNAME, "getStartDate()");
Connection con = null;
PreparedStatement preparedStatement = null;
ResultSet resultSet = null;
RtlMonthDefinition rtlMonthDef=null;

java.util.Date dt=new java.util.Date();
try {

	con  = conFactory.getConnection();
	rtlMonthDef=new RtlMonthDefinition();
	//String query = IDefinitionQueryConstants.rtlMonthStartDateQuery;
	if (con != null) {
		
		preparedStatement = con.prepareStatement("select max(DTE_RTL) as DTE_RTL  from RTL_CAL ");
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			
			rtlMonthDef.setStartDate(resultSet.getDate("DTE_RTL"));
			
		}
		
		
	}
}
catch(SQLException  e){
	LOGGER.error("DPB.DEF.GSTD.001",e);
DPBExceptionHandler.getInstance().handleDatabaseException(e);
}catch(NamingException  ne){
LOGGER.error("DPB.DEF.GSTD.002",ne);
DPBExceptionHandler.getInstance().handleException(ne);
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
		LOGGER.error("DPB.DEF.GSTD.003",e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}
}
LOGGER.exit(CLASSNAME, "getStartDate()");
return rtlMonthDef;

}
public List<ProcessInputFile> getProcInputFileDetails()
{
	LOGGER.enter(CLASSNAME, "getProcInputFileDetails()");
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<ProcessInputFile> procInpFileList=null;
	ProcessInputFile processInputFile=null;
	java.util.Date sDate=new java.util.Date();
	String conDate=null;
	
	
	try {
	
		con  = conFactory.getConnection();
		procInpFileList=new ArrayList<ProcessInputFile>();
		//String query = IDefinitionQueryConstants.rtlMonthStartDateQuery;
		if (con != null) {
			preparedStatement = con.prepareStatement("SELECT ID_DPB_PRCS, TXT_PRCS_MSG, CDE_DPB_PRCS_STS, ID_DEFN, CDE_TYP_PROC, L.DTS_LAST_UPDT FROM DPB_PRCS_LOG l INNER JOIN DPB_PROC  p ON l.ID_PRCS_DEF = p.ID_PROC_DEFN  order by TYP_PRCS,DTS_LAST_UPDT asc");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				
				processInputFile = new ProcessInputFile();
				
				processInputFile.setProcDpbID(resultSet.getString("ID_DPB_PRCS"));
				processInputFile.setProcessMsg(resultSet.getString("TXT_PRCS_MSG"));
				processInputFile.setProcStatus(resultSet.getString("CDE_DPB_PRCS_STS"));
				processInputFile.setDefID(resultSet.getInt("ID_DEFN"));
				processInputFile.setProcessName(resultSet.getString("CDE_TYP_PROC"));
				sDate=DateCalenderUtil.SqlDateToUtilDate(resultSet.getDate("DTS_LAST_UPDT"));
				processInputFile.setLastUpdatedDate(sDate);
				procInpFileList.add(processInputFile);
			}
			
		
		}
	}
catch(SQLException  e){
	LOGGER.error("DPB.DEF.GPIF.001",e);
DPBExceptionHandler.getInstance().handleDatabaseException(e);
}catch(NamingException  ne){
LOGGER.error("DPB.DEF.GPIF.002",ne);
DPBExceptionHandler.getInstance().handleException(ne);
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
			LOGGER.error("DPB.DEF.GPIF.003",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, "getProcInputFileDetails()");	
	return procInpFileList;
}

/* Gen pay File---Start*/
public List<GeneratePaymentFile> genPayFile()
{
	LOGGER.enter(CLASSNAME, "genPayFile()");
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<GeneratePaymentFile> genPaymentFileList=null;
	GeneratePaymentFile genPaymentFile=null;
	java.util.Date sDate=new java.util.Date();
	String conDate=null;
	
	
	try {
	
		con  = conFactory.getConnection();
		genPaymentFileList=new ArrayList<GeneratePaymentFile>();
		//String query = IDefinitionQueryConstants.rtlMonthStartDateQuery;
		if (con != null) {
			preparedStatement = con.prepareStatement("SELECT ID_DPB_PRCS, TXT_PRCS_MSG, CDE_DPB_PRCS_STS, ID_DEFN, CDE_TYP_PROC, L.DTS_LAST_UPDT FROM DPB_PRCS_LOG l INNER JOIN DPB_PROC  p ON l.ID_PRCS_DEF = p.ID_PROC_DEFN  order by TYP_PRCS,DTS_LAST_UPDT asc");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				
				genPaymentFile = new GeneratePaymentFile();
				
				genPaymentFile.setProcDpbID(resultSet.getString("ID_DPB_PRCS"));
				genPaymentFile.setProcessMsg(resultSet.getString("TXT_PRCS_MSG"));
				genPaymentFile.setProcStatus(resultSet.getString("CDE_DPB_PRCS_STS"));
				genPaymentFile.setDefID(resultSet.getInt("ID_DEFN"));
				genPaymentFile.setProcessName(resultSet.getString("CDE_TYP_PROC"));
				sDate=DateCalenderUtil.SqlDateToUtilDate(resultSet.getDate("DTS_LAST_UPDT"));
				genPaymentFile.setLastUpdatedDate(sDate);
				genPaymentFileList.add(genPaymentFile);
			}
			
			
		}
	}
	catch(SQLException  e){
		LOGGER.error("DPB.DEF.GPAY.001",e);
	DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
	LOGGER.error("DPB.DEF.GPAY.002",ne);
	DPBExceptionHandler.getInstance().handleException(ne);
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
			LOGGER.error("DPB.DEF.GPAY.003",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, "genPayFile()");	
	return genPaymentFileList;
}

/* Gen pay File---Ends*/

public List<GenerateReportFile> genReportFile()
{
	LOGGER.enter(CLASSNAME, "genReportFile()");
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<GenerateReportFile> genReportFileList=null;
	GenerateReportFile genReportFile=null;
	java.util.Date sDate=new java.util.Date();
	String conDate=null;
	
	try {
	
		con  = conFactory.getConnection();
		genReportFileList=new ArrayList<GenerateReportFile>();
		//String query = IDefinitionQueryConstants.rtlMonthStartDateQuery;
		if (con != null) {
			
			preparedStatement = con.prepareStatement("SELECT ID_DPB_PRCS, TXT_PRCS_MSG, CDE_DPB_PRCS_STS, ID_DEFN, CDE_TYP_PROC, L.DTS_LAST_UPDT FROM DPB_PRCS_LOG l INNER JOIN DPB_PROC  p ON l.ID_PRCS_DEF = p.ID_PROC_DEFN  order by TYP_PRCS,DTS_LAST_UPDT asc");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				
				genReportFile = new GenerateReportFile();
				
				genReportFile.setProcDpbID(resultSet.getString("ID_DPB_PRCS"));
				genReportFile.setProcessMsg(resultSet.getString("TXT_PRCS_MSG"));
				genReportFile.setProcStatus(resultSet.getString("CDE_DPB_PRCS_STS"));
				genReportFile.setDefID(resultSet.getInt("ID_DEFN"));
				genReportFile.setProcessName(resultSet.getString("CDE_TYP_PROC"));
				sDate=DateCalenderUtil.SqlDateToUtilDate(resultSet.getDate("DTS_LAST_UPDT"));
				genReportFile.setLastUpdatedDate(sDate);
				genReportFileList.add(genReportFile);
			}
			
			
		}
	}
	catch(SQLException  e){
		LOGGER.error("DPB.DEF.GRPT.001",e);
	DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
	LOGGER.error("DPB.DEF.GRPT.002",ne);
	DPBExceptionHandler.getInstance().handleException(ne);
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
			LOGGER.error("DPB.DEF.GRPT.003",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, "genReportFile()");
	return genReportFileList;
}

/* Gen Report File---Ends*/

/*Bonus Proc -- Start*/


public List<ProcessBonus> procBonusProc()
{
	LOGGER.enter(CLASSNAME, "genReportFile()");
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<ProcessBonus> processBonusList=null;
	ProcessBonus processBonus=null;
	java.util.Date sDate=new java.util.Date();
	String conDate=null;
	
	try {
	
		con  = conFactory.getConnection();
		processBonusList=new ArrayList<ProcessBonus>();
		//String query = IDefinitionQueryConstants.rtlMonthStartDateQuery;
		if (con != null) {
			preparedStatement = con.prepareStatement("SELECT ID_DPB_PRCS, TXT_PRCS_MSG, CDE_DPB_PRCS_STS, ID_DEFN, CDE_TYP_PROC, L.DTS_LAST_UPDT FROM DPB_PRCS_LOG l INNER JOIN DPB_PROC  p ON l.ID_PRCS_DEF = p.ID_PROC_DEFN  order by TYP_PRCS,DTS_LAST_UPDT asc");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				
				processBonus = new ProcessBonus();
				
				processBonus.setProcDpbID(resultSet.getString("ID_DPB_PRCS"));
				processBonus.setProcessMsg(resultSet.getString("TXT_PRCS_MSG"));
				processBonus.setProcStatus(resultSet.getString("CDE_DPB_PRCS_STS"));
				processBonus.setDefID(resultSet.getInt("ID_DEFN"));
				processBonus.setProcessName(resultSet.getString("CDE_TYP_PROC"));
				sDate=DateCalenderUtil.SqlDateToUtilDate(resultSet.getDate("DTS_LAST_UPDT"));
				processBonus.setLastUpdatedDate(sDate);
				processBonusList.add(processBonus);
			}
			
		}
	}
	catch(SQLException  e){
		LOGGER.error("DPB.DEF.PBP.001",e);
	DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
	LOGGER.error("DPB.DEF.PBP.002",ne);
	DPBExceptionHandler.getInstance().handleException(ne);
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
			LOGGER.error("DPB.DEF.PBP.003",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, "genReportFile()");
	return processBonusList;
}

/*Bonus Proc --Ends */
	


/* LDR  Bonus Proc -- Start*/


public List<ProcessLdrBonus> processLdrBonus()
{	LOGGER.enter(CLASSNAME, " processLdrBonus()");
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<ProcessLdrBonus> processLdrBonusList=null;
	ProcessLdrBonus processLdrBonus=null;
	java.util.Date sDate=new java.util.Date();
	String conDate=null;
	
	try {
	
		con  = conFactory.getConnection();
		processLdrBonusList=new ArrayList<ProcessLdrBonus>();
		//String query = IDefinitionQueryConstants.rtlMonthStartDateQuery;
		if (con != null) {
			
			preparedStatement = con.prepareStatement("SELECT ID_DPB_PRCS, TXT_PRCS_MSG, CDE_DPB_PRCS_STS, ID_DEFN, CDE_TYP_PROC, L.DTS_LAST_UPDT FROM DPB_PRCS_LOG l INNER JOIN DPB_PROC  p ON l.ID_PRCS_DEF = p.ID_PROC_DEFN  order by TYP_PRCS,DTS_LAST_UPDT asc");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				
				processLdrBonus = new ProcessLdrBonus();
				
				processLdrBonus.setProcDpbID(resultSet.getString("ID_DPB_PRCS"));
				processLdrBonus.setProcessMsg(resultSet.getString("TXT_PRCS_MSG"));
				processLdrBonus.setProcStatus(resultSet.getString("CDE_DPB_PRCS_STS"));
				processLdrBonus.setDefID(resultSet.getInt("ID_DEFN"));
				processLdrBonus.setProcessName(resultSet.getString("CDE_TYP_PROC"));
				sDate=DateCalenderUtil.SqlDateToUtilDate(resultSet.getDate("DTS_LAST_UPDT"));
				processLdrBonus.setLastUpdatedDate(sDate);
				processLdrBonusList.add(processLdrBonus);
			}
			
		}
	}
	catch(SQLException  e){
		LOGGER.error("DPB.DEF.PLB.001",e);
	DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
	LOGGER.error("DPB.DEF.PLB.002",ne);
	DPBExceptionHandler.getInstance().handleException(ne);
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
			LOGGER.error("DPB.DEF.PLB.003",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, " processLdrBonus()");
	return processLdrBonusList;
}

/**
 * saveLdrshipBonusDef for inserting Leadership Bonus details
 * @param ldrshipbnsdtls
 * @return LeadershipBonusDetails
*/
public LeadershipBonusDetails saveLdrshipBonusDef(LeadershipBonusDetails ldrshipbnsdtls) {
	final String methodName = "saveLdrshipBonusDef";
	LOGGER.enter(CLASSNAME, methodName);
	Connection conn = null;
	String query = null;
	PreparedStatement ps = null;
	int i = 0;
	ResultSet rs = null;
	java.sql.Date startDate = null;
	java.sql.Date endDate = null;
	java.sql.Date procDate = null;
	String sd = null;
	String ed = null;
	String pd = null;
	String rtlsd = null;
	String rtled = null;
	java.sql.Date rtlStartDate = null;
	java.sql.Date rtlEndDate = null;
	try {
		conn = conFactory.getConnection();
		if (null != conn) {
			query = IDefinitionQueryConstants.INSERT_LDRSP_BNS_PGM;
			LOGGER.info("INSERT_LDRSP_BNS_PGM Query " + query);
			ps = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,(String) ldrshipbnsdtls.getLdrshipname().trim());
			rtlsd = ldrshipbnsdtls.getRtlStartDate();
			if (rtlsd != null) {
				rtlStartDate = DateCalenderUtil.convertStringToDate(rtlsd);
				ps.setDate(2, rtlStartDate);
			}
			rtled = ldrshipbnsdtls.getRtlEndDate();
			if (rtled != null) {
				rtlEndDate = DateCalenderUtil.convertStringToDate(rtled);
				ps.setDate(3, rtlEndDate);
			}
			sd = ldrshipbnsdtls.getStartDate();
			if (sd != null) {
				startDate = DateCalenderUtil.convertStringToDate(sd);
				ps.setDate(4, startDate);
			}
			ed = ldrshipbnsdtls.getEndDate();
			if (ed != null) {
				endDate = DateCalenderUtil.convertStringToDate(ed);
				ps.setDate(5, endDate);
			}
			ps.setDouble(6, ldrshipbnsdtls.getBusresvamt());
			ps.setDouble(7, ldrshipbnsdtls.getBusresvper());
			ps.setDouble(8, ldrshipbnsdtls.getCalcamtperunt());
			ps.setDouble(9, ldrshipbnsdtls.getActamtperunt());
			ps.setString(10, ldrshipbnsdtls.getStatus());
			pd = ldrshipbnsdtls.getProcessDate();
			if (pd != null) {
				procDate = DateCalenderUtil.convertStringToDate(pd);
				ps.setDate(11, procDate);
			}
			ps.setDouble(12, ldrshipbnsdtls.getUnusdamt());
			ps.setInt(13, ldrshipbnsdtls.getUnitCount());
			ps.setDouble(14, ldrshipbnsdtls.getLdrbnsamt());
			ps.setString(15, ldrshipbnsdtls.getUserId());
			ps.setString(16, ldrshipbnsdtls.getUserId());
			i = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				LOGGER.info("Generated Leadership Id: " + rs.getInt(1));
				ldrshipbnsdtls.setLdrshipid(rs.getInt(1));
			}
		}
	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.LDRSP_BNS_PGM.INSERT.001 ", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.LDRSP_BNS_PGM.INSERT.002 ", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	} finally {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			LOGGER.error("LDRSP_BNS.LDRSP_BNS_PGM.INSERT.003 ", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return ldrshipbnsdtls;
}
/**
 * insertPrgVehMap for inserting Leadership id,vehicle type,indicator
 * @param ldrspId
 * @param appVehi
*/
public void insertPrgVehMap(int ldrspId, List appVehi, String userid,String status) {
	final String methodName = "insertPrgVehMap";
	LOGGER.enter(CLASSNAME, methodName);
	Connection conn = null;
	String query = null;
	PreparedStatement ps = null;
	List appVeh = new ArrayList();
	int i = 0;
	int j = 0;
	try {
		conn = conFactory.getConnection();
		if (null != conn) {
			query = IDefinitionQueryConstants.INSERT_DPB_PGM_VEH_MAP;
			ps = conn.prepareStatement(query);
			LOGGER.info("INSERT_LDRSP_PGM_VEH_MAP Query " + query);
			appVeh = appVehi;
			for (i = 0; i < appVeh.size(); i++) {
				ps.setInt(1, ldrspId);
				String appliVehicles = (String) appVeh.get(i);
				ps.setString(2, appliVehicles);
				ps.setString(3, IConstants.STATUS_D);
				ps.setString(4, userid);
				ps.setString(5, userid);
				j = ps.executeUpdate();
			}
		}

	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.LDRSP_PGM_VEH_MAP.INSERT.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.LDRSP_PGM_VEH_MAP.INSERT.002", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	} finally {
		try {

			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			LOGGER.error("LDRSP_BNS.LDRSP_PGM_VEH_MAP.INSERT.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
/**
 * getRTLCalId for fetching the retail id
 * @param processDate
 * @return retailId
*/
public int getRTLCalId(String processDate) {
	final String methodName = "getRTLCalId";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int retailId = 0;
	java.sql.Date procDate = null;
	String procDte = null;
	try {
		con = conFactory.getConnection();
		if (con != null) {
			String query = IDefinitionQueryConstants.RETRIEVE_LDRSP_RTL_CAL;
			ps = con.prepareStatement(query);
			LOGGER.info("RETRIEVE_LDRSPS_RTL_CAL Query " + query);
			procDte = processDate;
			if (procDte != null) {
				procDate = DateCalenderUtil.convertStringToDate(procDte);
				ps.setDate(1, procDate);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				retailId = rs.getInt("ID_DAY");
			}
		}
	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_RTL_CAL.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_RTL_CAL.002", ne);
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
			LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_RTL_CAL.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return retailId;

}
/**
 * insertDPBProc for inserting Leadership pgm details to DPB process
 * @param ldrshipbnsdtls
*/
public void insertDPBProc(LeadershipBonusDetails ldrshipbnsdtls) {
	final String methodName = "insertDPBProc";
	LOGGER.enter(CLASSNAME, methodName);
	Connection conn = null;
	String query = null;
	PreparedStatement ps = null;
	List<String> typeProcess = null;
	String procDte = null;
	java.sql.Date procDate = null;
	try {
		conn = conFactory.getConnection();
		typeProcess = new ArrayList<String>();
		typeProcess.add(IConstants.LEADERSHIP_BONUS_PROCESS);
		typeProcess.add(IConstants.LEADERSHIP_PAYMENT_PROCESS);			
		if (null != conn) {
			query = IDefinitionQueryConstants.INSERT_LDRSP_DPB_PROC;
			ps = conn.prepareStatement(query);
			for (int i = 0; i < typeProcess.size(); i++) {
				String typeProc = (String) typeProcess.get(i);					
				LOGGER.info("INSERT_LDRSP_DPB_PROC Query " + query);
				procDte = ldrshipbnsdtls.getProcessDate();
				if (procDte != null) {
					procDate = DateCalenderUtil.convertStringToDate(procDte);
					ps.setDate(1, procDate);
					ps.setDate(2, procDate);
				}
				//ps.setInt(1, ldrshipbnsdtls.getProcessDate());
				//ps.setInt(2, ldrshipbnsdtls.getProcessDate());
				ps.setInt(3, ldrshipbnsdtls.getLdrshipid());
				ps.setString(4, typeProc);
				ps.setString(5, ldrshipbnsdtls.getUserId());
				ps.setString(6, ldrshipbnsdtls.getUserId());
				ps.addBatch();
			}
			 ps.executeBatch();				
		}
	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.INSERT_LDRSP_DPB_PROC.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.INSERT_LDRSP_DPB_PROC.002", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	} finally {
		try {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			LOGGER.error("LDRSP_BNS.INSERT_LDRSP_DPB_PROC.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
/**
 * validateProcessDate for validating process date in Retail calendar
 * @param procDate
 * @return boolean
*/
public boolean validateProcessDate(String procDate) {
	final String methodName = "validateProcessDate";
	LOGGER.enter(CLASSNAME, methodName);
	// TODO Auto-generated method stub
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean returnValidProcDate = false;
	try {
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.RETRIEVE_LDRSP_RTL_CAL;
		ps = con.prepareStatement(query);
		LOGGER.info("RETRIEVE_LDRSP_RTL_CAL Query " + query);
		java.sql.Date rtlProcDate = DateCalenderUtil.convertStringToDate(procDate);
		ps.setDate(1, rtlProcDate);
		rs = ps.executeQuery();
		if (rs.next()) {
			returnValidProcDate = true;
		}
	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_RTL_CAL.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_RTL_CAL.002", ne);
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
			LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_RTL_CAL.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return returnValidProcDate;
}

/**
 * validateUnearnedAmount for validating UnearnedAmount
 * @param acrlstartDate
 * @param acrlendDate
 * @return boolean
*/
public double validateUnearnedAmount(String acrlstartDate,String acrlendDate) {
	final String methodName = "validateUnearnedAmount";
	LOGGER.enter(CLASSNAME, methodName);
	// TODO Auto-generated method stub
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	//boolean returnValidProcDate = false;
	double unEarnedAmount = 0.00;
	try {
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.VALIDATE_UNUSED_AMOUNT;
		ps = con.prepareStatement(query);
		LOGGER.info("VALIDATE_UNUSED_AMOUNT Query " + query);
		java.sql.Date acrlstartDte = DateCalenderUtil.convertStringToDate(acrlstartDate);
		ps.setDate(1, acrlstartDte);
		java.sql.Date acrlendDte = DateCalenderUtil.convertStringToDate(acrlendDate);
		ps.setDate(2, acrlendDte);
		rs = ps.executeQuery();
		if (rs.next()) {
			unEarnedAmount = rs.getDouble("UNEARNED_AMOUNT");
		}
	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.VALIDATE_UNEARNED_AMT.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.VALIDATE_UNEARNED_AMT.002", ne);
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
			LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_RTL_CAL.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return unEarnedAmount;
}

/**
 * updateLdrshipBonusDef for updating Leadership Bonus details
 * @param ldrshipbnsdtls
 * @return LeadershipBonusDetails
*/
public LeadershipBonusDetails updateLdrshipBonusDef(LeadershipBonusDetails ldrshipbnsdtls) {
	final String methodName = "updateLdrshipBonusDef";
	LOGGER.enter(CLASSNAME, methodName);
	Connection conn = null;
	String query = null;
	PreparedStatement ps = null;
	int i = 0;
	Date startDate = null;
	Date endDate = null;
	Date procDate = null;
	String sd = null;
	String ed = null;
	String pd = null;
	Date rtlstartDate = null;
	Date rtlendDate = null;
	String rtlsd = null;
	String rtled = null;
	try {
		conn = conFactory.getConnection();
		query = IDefinitionQueryConstants.UPDATE_LDRSP_BNS_PGM;
		ps = conn.prepareStatement(query);
		LOGGER.info("UPDATE_LDRSP_BNS_PGM Query " + query);
		ps.setString(1, ldrshipbnsdtls.getLdrshipname().trim());
		sd = ldrshipbnsdtls.getStartDate();
		if (sd != null) {
			startDate = DateCalenderUtil.convertStringToDate(sd);
			ps.setDate(2, (java.sql.Date) startDate);
		}
		ed = ldrshipbnsdtls.getEndDate();
		if (ed != null) {
			endDate = DateCalenderUtil.convertStringToDate(ed);
			ps.setDate(3, (java.sql.Date) endDate);
		}
		rtlsd = ldrshipbnsdtls.getRtlStartDate();
		if (rtlsd != null) {
			rtlstartDate = DateCalenderUtil.convertStringToDate(rtlsd);
			ps.setDate(4, (java.sql.Date) rtlstartDate);
		}
		rtled = ldrshipbnsdtls.getRtlEndDate();
		if (rtled != null) {
			rtlendDate = DateCalenderUtil.convertStringToDate(rtled);
			ps.setDate(5, (java.sql.Date) rtlendDate);
		}
		ps.setDouble(6, ldrshipbnsdtls.getBusresvamt());
		ps.setDouble(7, ldrshipbnsdtls.getBusresvper());
		ps.setDouble(8, ldrshipbnsdtls.getCalcamtperunt());
		ps.setDouble(9, ldrshipbnsdtls.getActamtperunt());
		ps.setString(10, ldrshipbnsdtls.getStatus());
		pd = ldrshipbnsdtls.getProcessDate();
		if (pd != null) {
			procDate = DateCalenderUtil.convertStringToDate(pd);
			ps.setDate(11, (java.sql.Date) procDate);
		}
		ps.setDouble(12, ldrshipbnsdtls.getUnusdamt());
		ps.setInt(13, ldrshipbnsdtls.getUnitCount());
		ps.setDouble(14, ldrshipbnsdtls.getLdrbnsamt());
		ps.setString(15, ldrshipbnsdtls.getUserId());
		ps.setInt(16, ldrshipbnsdtls.getLdrshipid());
		
		i = ps.executeUpdate();

	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.UPDATE_LDRSP_BNS_PGM.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.UPDATE_LDRSP_BNS_PGM.002", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	} finally {
		try {

			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			LOGGER.error("LDRSP_BNS.UPDATE_LDRSP_BNS_PGM.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return ldrshipbnsdtls;
}
/**
 * deleteLdrspVehMapping for deleting Leadership program and vehicle type mapping
 * @param ldrspId
*/
public void deleteLdrspVehMapping(int ldrspId) {
	final String methodName = "deleteLdrspVehMapping";
	LOGGER.enter(CLASSNAME, methodName);
	// TODO Auto-generated method stub
	Connection con = null;
	PreparedStatement ps = null;
	int i = 0;
	try {
		con = conFactory.getConnection();
		if (con != null) {
			String query = IDefinitionQueryConstants.DELETE_DPB_PGM_VEH_MAP;
			ps = con.prepareStatement(query);
			LOGGER.info("DELETE_DPB_PGM_VEH_MAP Query " + query);
			ps.setInt(1, ldrspId);
			//ps.setString(2,  IConstants.YES);
			i = ps.executeUpdate();
		}
	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.DELETE_DPB_PGM_VEH_MAP.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.DELETE_DPB_PGM_VEH_MAP.002", ne);
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
			LOGGER.error("LDRSP_BNS.DELETE_DPB_PGM_VEH_MAP.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
/**
 * getApplicationVehicle for fetching Leadership program and vehicle type mapping
 * @param ldrshipbnsdtls
*/
public void getApplicationVehicle(LeadershipBonusDetails ldrshipbnsdtls) {
	final String methodName = "getApplicationVehicle";
	LOGGER.enter(CLASSNAME, methodName);
	// TODO Auto-generated method stub
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	List<String> appVehicle = null;
	try {
		con = conFactory.getConnection();
		appVehicle = new ArrayList<String>();
		if (con != null) {
			String query = IDefinitionQueryConstants.RETRIEVE_DPB_PGM_VEH_MAP;
			ps = con.prepareStatement(query);
			LOGGER.info("RETRIEVE_DPB_PGM_VEH_MAP Query " + query);
			ps.setInt(1, ldrshipbnsdtls.getLdrshipid());
			//ps.setString(2, IConstants.YES);
			rs = ps.executeQuery();
			while (rs.next()) {
				appVehicle.add(rs.getString("CDE_VEH_TYP"));
			}
			ldrshipbnsdtls.setLdrshipAppVehs(appVehicle);
		}
	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.RETRIEVE_DPB_PGM_VEH_MAP.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.RETRIEVE_DPB_PGM_VEH_MAP.002", ne);
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
			LOGGER.error("LDRSP_BNS.RETRIEVE_DPB_PGM_VEH_MAP.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}


/**
 * getLdrshipBonusAmt for fetching Leadership Unused amount
 * @param ldrshipbnsdtls
 * @return double
*/
public LeadershipBonusDetails getLdrshipBonusAmt(LeadershipBonusDetails ldrshipbnsdtls) {
	final String methodName = "getLdrshipBonusAmt";
	LOGGER.enter(CLASSNAME, methodName);
	Connection conn = null;
	String query = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int i = 0;		
	double unusedAmtTot = 0.00;
	StringBuffer queryCondition = new StringBuffer();
	String queryCond = "";
	int count = 0;
	try {
		conn = conFactory.getConnection();
		if (null != conn) {
		if (ldrshipbnsdtls.getLdrshipAppVehs().size() > 0 && ldrshipbnsdtls.getLdrshipAppVehs() != null) {
			int appVeHLen = ldrshipbnsdtls.getLdrshipAppVehs().size();
			for (i = 1; i <= appVeHLen; i++) {
				queryCondition.append("?");
				if (appVeHLen >= 1 && i < ldrshipbnsdtls.getLdrshipAppVehs().size()) {
					queryCondition.append(",");
					}
				}
			}
			queryCondition.append(")");
			queryCond = queryCondition.toString();
			if (ldrshipbnsdtls.getLdrshipAppVehs().size() > 0) {
				query = "with VEH_FRAN_MAP (VEH_TYP, FRAN_TYP) as ( values(cast('P' as char(1)), cast('PC' as char(2))) union  values(cast('P' as char(1)), "+ 
						"cast('MB' as char(2))) union values(cast('P' as char(1)), cast('LT' as char(2))) union  values(cast('S' as char(1)), "+
						"cast('SM' as char(2))) union values(cast('V' as char(1)), cast('SP' as char(2))) union  values(cast('F' as char(1)), "+
						"cast('SP' as char(2))))  ," +
						" B_DATES (ST_DATE, END_DATE) as (values (cast(? as DATE),cast(? as DATE))), "+
						"A_DATES (ST_DATE, END_DATE) as (values (cast(? as DATE),cast(? as DATE))), "+
						"B_ACCURAL (NUM_PO, ACRL_POST_DATE, AMOUNT, VEH_TYP) as (select acrl.NUM_PO, acrl.DTE_LDRSP_BNS_ACRL_POST, " +
						"acrl.AMT_DPB_LDRSP_BNS, acrl.CDE_VEH_TYP from LDRSP_BNS_ACRL acrl, B_DATES dates "+
						"where acrl.DTE_LDRSP_BNS_ACRL_POST between dates.ST_DATE and dates.END_DATE and acrl.CDE_VEH_TYP IN ("	+queryCond + "),";
				query = query + "VEH_DATA (NUM_PO,ID_UNBLK_VEH) as ( "+
								" select vista.NUM_PO,max(ID_DPB_UNBLK_VEH) "+
								" from DPB_UNBLK_VEH vista, B_ACCURAL acrl, A_DATES dates "+
								" where "+
								" vista.DTE_RTL  between dates.ST_DATE and dates.END_DATE "+
								" and vista.CDE_VEH_TYP in (acrl.VEH_TYP)"+
								" group by vista.NUM_PO "+
								" ), "+
								" B_VISTA (NUM_PO,ID_DPB_UNBLK_VEH, ID_DLR, VEH_TYP, RTL_DTE, ACRL_POST_DATE) as ( "+
								" select DISTINCT vista.NUM_PO,vista.ID_DPB_UNBLK_VEH, vista.ID_DLR, acrl.VEH_TYP, vista.DTE_RTL,  "+
								" acrl.ACRL_POST_DATE  "+
								" from VEH_DATA vdata "+
								" left outer join DPB_UNBLK_VEH vista on vdata.ID_UNBLK_VEH = vista.ID_DPB_UNBLK_VEH "+
								" and vdata.NUM_PO = vista.NUM_PO and vista.CDE_VEH_DDR_STS != 'I2' "+
								" left outer join B_ACCURAL acrl on vista.CDE_VEH_TYP in (acrl.VEH_TYP) "+
								" ),  "+
								" B_RTL_DIM (NUM_PO, ID_DLR, VEH_TYP, RTL_DTE, ACRL_POST_DATE,  DLR_QTR, DLR_YEAR) as (  "+
								" select vista.NUM_PO, vista.ID_DLR, vista.VEH_TYP, vista.RTL_DTE, vista.ACRL_POST_DATE, "+ 
								" dim.NUM_RETL_QTR, dim.NUM_RETL_YR from B_VISTA vista, DPB_DAY dim "+
								" where  vista.RTL_DTE = dim.DTE_CAL ),  "+
								" B_DLR_ELG (NUM_PO, ID_DLR, VEH_TYP, RTL_DTE, ACRL_POST_DATE, DLR_QTR, DLR_YEAR) as (  "+
								" select DISTINCT dim.NUM_PO, dim.ID_DLR,  dim.VEH_TYP, dim.RTL_DTE, dim.ACRL_POST_DATE, dim.DLR_QTR, "+
								" dim.DLR_YEAR  from B_RTL_DIM dim, DPB_LDR_QTR_FDRT elg, VEH_FRAN_MAP vehRfan "+ 
								" where elg.IND_DPB_LDR = 'Y' and elg.ID_DLR = dim.ID_DLR  and elg.DTE_QTR = dim.DLR_QTR  "+
								" and elg.DTE_YR = dim.DLR_YEAR  and vehRfan.VEH_TYP in (dim.VEH_TYP)"+ 
								" and elg.CDE_DLR_FRAN_TYP IN (vehRfan.FRAN_TYP)  ),  "+
								" B_DLR_ELG_CNT(COUNT_UNIT) as (select count(distinct NUM_PO) from B_DLR_ELG),  "+
								" UNUSED_AMT(AMT_DPB_LDRSP_BNS)  AS (SELECT sum(acrl.AMOUNT) FROM B_ACCURAL acrl)  "+ 
								" SELECT cnt.COUNT_UNIT,amt.AMT_DPB_LDRSP_BNS FROM B_DLR_ELG_CNT cnt,UNUSED_AMT amt";
						LOGGER.info("RETRIEVE_LDRSP_BNS_ACRL Query " + query);
			} else {
				//query = IDefinitionQueryConstants.RETRIEVE_AMT_LDRSP_BNS;
				LOGGER.info("RETRIEVE_AMT_LDRSP_BNS Query " + query);
			}
			ps = conn.prepareStatement(query);
			java.sql.Date startDate = DateCalenderUtil.convertStringToDate(ldrshipbnsdtls.getStartDate());
			java.sql.Date endDate = DateCalenderUtil.convertStringToDate(ldrshipbnsdtls.getEndDate());
			ps.setDate(1, startDate);
			ps.setDate(2, endDate);
			java.sql.Date rtlsStartDate = DateCalenderUtil.convertStringToDate(ldrshipbnsdtls.getRtlStartDate());
			java.sql.Date rtlEndDate = DateCalenderUtil.convertStringToDate(ldrshipbnsdtls.getRtlEndDate());
			ps.setDate(3, rtlsStartDate);
			ps.setDate(4, rtlEndDate);
			if (ldrshipbnsdtls.getLdrshipAppVehs().size() > 0) {
				int placeHoldersCnt = 5;
				int appVehiLen = ldrshipbnsdtls.getLdrshipAppVehs().size();
				if (ldrshipbnsdtls.getLdrshipAppVehs().size() > 0) {
					for (i = 0; i < appVehiLen; i++) {
						ps.setString(placeHoldersCnt++,(String) ldrshipbnsdtls.getLdrshipAppVehs().get(i));
					}
				}
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				unusedAmtTot = rs.getDouble("AMT_DPB_LDRSP_BNS");
				count = rs.getInt("COUNT_UNIT");
				ldrshipbnsdtls.setUnusdamt(unusedAmtTot);
				ldrshipbnsdtls.setUnitCount(count);
			}				

		}

	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_BNS_ACRL.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_BNS_ACRL.002", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	} finally {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_BNS_ACRL.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return ldrshipbnsdtls;
}	
/**
 * getLdrshipBonusList for fetching Leadership Bonus list details
 * @return List
*/
public List<LeadershipBonusDetails> getLdrshipBonusList() {
	final String methodName = "getLdrshipBonusList";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	List<LeadershipBonusDetails> ldrshipBnsList = null;
	PreparedStatement ps = null;
	try {
		con = conFactory.getConnection();
		ldrshipBnsList = new ArrayList<LeadershipBonusDetails>();
		String query = IDefinitionQueryConstants.RETRIEVE_LDRSP_BNS_LIST;
		ps = con.prepareStatement(query);
		LOGGER.info("RETRIEVE_LDRSP_BNS_LIST Query " + query);
		rs = ps.executeQuery();
		int oldId = 0;
		LeadershipBonusDetails ldrBonusDtlsBean = null;
		while (rs.next()) {
			int currentId = rs.getInt("ID_LDRSP_BNS_PGM");
			if (oldId != currentId) {// first record
				ldrBonusDtlsBean = new LeadershipBonusDetails();
				ldrBonusDtlsBean.setLdrshipid(rs.getInt("ID_LDRSP_BNS_PGM"));
				ldrBonusDtlsBean.setLdrshipname(rs.getString("NAM_LDRSP_BNS").trim());
				java.sql.Date startDate = rs.getDate("DTE_LDRSP_BNS_RTL_STA");
				String rtlstrDate = DateCalenderUtil.convertDateToString(startDate);
				ldrBonusDtlsBean.setRtlStartDate(rtlstrDate);
				java.sql.Date endDate = rs.getDate("DTE_LDRSP_BNS_RTL_END");
				String rtlendDte = DateCalenderUtil.convertDateToString(endDate);
				ldrBonusDtlsBean.setRtlEndDate(rtlendDte);
				ldrBonusDtlsBean.setBusresvamt(rs.getDouble("AMT_LDRSP_BNS_BUS_RSRV"));
				ldrBonusDtlsBean.setBusresvper(rs.getDouble("PCT_LDRSP_BNS_BUS_RSRV"));
				ldrBonusDtlsBean.setCalcamtperunt(rs.getDouble("AMT_LDRSP_BNS_PER_UNIT_CALC"));
				ldrBonusDtlsBean.setActamtperunt(rs.getDouble("AMT_LDRSP_BNS_PER_UNIT_EDIT"));
				ldrBonusDtlsBean.setStatus(rs.getString("CDE_DPB_STS"));
				java.sql.Date procDate = rs.getDate("DTE_LDRSP_BNS_PROC");
				String proDte = "";
				if (procDate != null) {
					proDte = DateCalenderUtil.convertDateToString(procDate);
				}
				ldrBonusDtlsBean.setProcessDate(proDte);
				ldrBonusDtlsBean.setUnusdamt(rs.getDouble("AMT_DPB_UNUSED"));
				ldrBonusDtlsBean.setUnitCount(rs.getInt("CNT_LDRSP_BNS_VEH"));
				ldrBonusDtlsBean.setLdrbnsamt(rs.getDouble("AMT_DPB_LDRSP_BNS"));
				ldrshipBnsList.add(ldrBonusDtlsBean);
			} /*else {
				// respected
				// applivehi.add(rs.getInt(13));
			}*/

			oldId = currentId;
		}
	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_BNS_LIST.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_BNS_LIST.002", ne);
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
			LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_BNS_LIST.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	return ldrshipBnsList;
}

/**
 * getLdrshipBonusDefDetails for fetching Leadership Bonus details of program
 * @param ldrshipbnsdtls
 * @return LeadershipBonusDetails
*/
public LeadershipBonusDetails getLdrshipBonusDefDetails(LeadershipBonusDetails ldrshipbnsdtls) {
	final String methodName = "getLdrshipBonusDefDetails";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	List<LeadershipBonusDetails> ldrshipBnsList = null;
	PreparedStatement ps = null;
	try {
		con = conFactory.getConnection();
		ldrshipBnsList = new ArrayList<LeadershipBonusDetails>();
		String query = IDefinitionQueryConstants.RETRIEVE_LDRSP_BNS_PGM;
		ps = con.prepareStatement(query);
		LOGGER.info("RETRIEVE_LDRSP_BNS_PGM Query " + query);
		ps.setInt(1, ldrshipbnsdtls.getLdrshipid());
		rs = ps.executeQuery();
		while (rs.next()) {
			ldrshipbnsdtls.setLdrshipid(rs.getInt("ID_LDRSP_BNS_PGM"));
			ldrshipbnsdtls.setLdrshipname(rs.getString("NAM_LDRSP_BNS").trim());
			
			java.sql.Date rtlStartDate = rs.getDate("DTE_LDRSP_BNS_RTL_STA");				
			String rtlStrDate = DateCalenderUtil.convertDateToString(rtlStartDate);
			ldrshipbnsdtls.setRtlStartDate(rtlStrDate);
			
			java.sql.Date rtlEndDate = rs.getDate("DTE_LDRSP_BNS_RTL_END");
			String rtlEnDate = DateCalenderUtil.convertDateToString(rtlEndDate);
			ldrshipbnsdtls.setRtlEndDate(rtlEnDate);
			
			
			java.sql.Date startDate = rs.getDate("DTE_LDRSP_BNS_ACRL_STA");
			String strDate = DateCalenderUtil.convertDateToString(startDate);
			ldrshipbnsdtls.setStartDate(strDate);
			java.sql.Date endDate = rs.getDate("DTE_LDRSP_BNS_ACRL_END");
			String endDte = DateCalenderUtil.convertDateToString(endDate);
			ldrshipbnsdtls.setEndDate(endDte);
			ldrshipbnsdtls.setBusresvamt(rs.getDouble("AMT_LDRSP_BNS_BUS_RSRV"));
			ldrshipbnsdtls.setBusresvper(rs.getDouble("PCT_LDRSP_BNS_BUS_RSRV"));
			ldrshipbnsdtls.setCalcamtperunt(rs.getDouble("AMT_LDRSP_BNS_PER_UNIT_CALC"));
			ldrshipbnsdtls.setActamtperunt(rs.getDouble("AMT_LDRSP_BNS_PER_UNIT_EDIT"));
			ldrshipbnsdtls.setStatus(rs.getString("CDE_DPB_STS"));
			java.sql.Date procDate = rs.getDate("DTE_LDRSP_BNS_PROC");				
			String procDte = IConstants.EMPTY_STR;
			if (procDate != null) {
				procDte = DateCalenderUtil.convertDateToString(procDate);
			}
			ldrshipbnsdtls.setProcessDate(procDte);
			ldrshipbnsdtls.setUnusdamt(rs.getDouble("AMT_DPB_UNUSED"));
			ldrshipbnsdtls.setUnitCount(rs.getInt("CNT_LDRSP_BNS_VEH"));
			ldrshipbnsdtls.setLdrbnsamt(rs.getDouble("AMT_DPB_LDRSP_BNS"));
			ldrshipbnsdtls.setInactiveDate(rs.getDate("DTE_INACT"));
		}
	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_BNS_PGM.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_BNS_PGM.002", ne);
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
			LOGGER.error("LDRSP_BNS.RETRIEVE_LDRSP_BNS_PGM.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return ldrshipbnsdtls;
}
/**
* updateInactiveDate for updating Inactive date of a Leadership Bonus Program
* @param ldrspId
* @param inactiveDate
* @param status
*/
public void updateInactiveDate(int ldrspId, String inactiveDate,String status,String userid) {
	final String methodName = "updateInactiveDate";
	LOGGER.enter(CLASSNAME, methodName);
	Connection conn = null;
	String query = null;
	PreparedStatement ps = null;
	int i = 0;
	String id = null;
	try {
		conn = conFactory.getConnection();
		query = IDefinitionQueryConstants.UPDATE_LDRSP_BNS_PGMS;
		LOGGER.info("UPDATE_LDRSP_BNS_PGMS Query " + query);
		ps = conn.prepareStatement(query);
		ps.setString(1, status);
		id = inactiveDate;			
		if (id != null) {
			java.sql.Date nextDay = DateCalenderUtil.convertStringToSQLDateFormat(id);
			ps.setDate(2, nextDay);
		}
		ps.setString(3, userid);
		ps.setInt(4, ldrspId);
				
		i = ps.executeUpdate();

	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.UPDATE_LDRSP_BNS_PGMS.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.UPDATE_LDRSP_BNS_PGMS.002", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	} finally {
		try {

			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			LOGGER.error("LDRSP_BNS.UPDATE_LDRSP_BNS_PGMS.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
//LDRSP Bonus End
//AccountID Mapping Start	
/**
 * getAccountIDMaping for fetching Program Account ID Mapping
 * @return Map
*/
public Map<String, Object> getAccountIDMaping() {
	final String methodName = "getAccountIDMaping";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	Map<String, Object> vcMap = new HashMap<String, Object>();
	PreparedStatement ps = null;
	ResultSet rs = null;
	List<AccountIdMappingDefinition> accountIdList = new ArrayList<AccountIdMappingDefinition>();
	List<AccountIdMappingDefinition> accountIdListDT = new ArrayList<AccountIdMappingDefinition>();
	List<AccountIdMappingDefinition> accountStatusList = new ArrayList<AccountIdMappingDefinition>();
	List<AccountIdMappingDefinition> indicatorList = new ArrayList<AccountIdMappingDefinition>();
	List<AccountIdMappingDefinition> idLdrspBnsPgm = new ArrayList<AccountIdMappingDefinition>();
	//int accountid = 0;
	String accountCR =  "";
	String accountDT =  "";		
	String accountStatus = null;
	String indBnsPgm = null;
	int idBnsPgm = 0;
	AccountIdMappingDefinition accMap = new AccountIdMappingDefinition();
	List<AccountIdMappingDefinition> programComponent = getAccoundIDList();
	for (int i = 0; i < programComponent.size(); i++) {
		accMap.setId(programComponent.get(i).getId());

		//accountid = programComponent.get(i).getAccountId();
		//accMap.setAccountId(accountid);
		accountCR = programComponent.get(i).getAccountCFCCR();
		accountDT = programComponent.get(i).getAccountCFCDT();
		accMap.setAccountCFCCR(accountCR);
		accMap.setAccountCFCDT(accountDT);
		if(null != programComponent.get(i).getStatusMapping() && !IConstants.EMPTY_STR.equalsIgnoreCase(programComponent.get(i).getStatusMapping()) ) {
			accountStatus = programComponent.get(i).getStatusMapping();
		} else {
			accountStatus = "D";
		}
		
		accMap.setStatusMapping(accountStatus);

		//indBnsPgm = programComponent.get(i).getIndicator();
		//accMap.setIndicator(indBnsPgm);

		idBnsPgm = programComponent.get(i).getIdBnsPgm();
		accMap.setIdBnsPgm(idBnsPgm);

		//indicatorList.add(accMap);
		idLdrspBnsPgm.add(accMap);
		accountIdList.add(accMap);
		accountIdListDT.add(accMap);
		accountStatusList.add(accMap);
	}

	VehicleConditionMapping vcMapping = null;
	List<VehicleConditionMapping> vcList = new ArrayList<VehicleConditionMapping>();
	vcMap.put("ACCOUNTMAPPING_LIST", programComponent);
	vcMap.put("ACCOUNTIDMAPPING_LIST", accountIdList);
	vcMap.put("ACCOUNTIDMAPPING_LISTDT", accountIdListDT);
	vcMap.put("ACCOUNTSTATUSMAPPING_LIST", accountStatusList);
	//vcMap.put("ACCOUNT_INDICATOR_MAPPING_LIST", indicatorList);
	vcMap.put("ACCOUNT_IDBNSPGM_MAPPING_LIST", idLdrspBnsPgm);
	try {
		String oldId = "";
		con = conFactory.getConnection();
		String query= IDefinitionQueryConstants.ACC_ID_MAP_LST;
		ps = con.prepareStatement(query);
		LOGGER.info("ACC_ID_MAP_LST Query " + query);
		rs = ps.executeQuery();
		programComponent = new ArrayList<AccountIdMappingDefinition>();
		List<String> condtionList = new ArrayList<String>();
		List<String> accountIDList = new ArrayList<String>();
		List<String> accountIDListDT = new ArrayList<String>();
		List<String> accStatus = new ArrayList<String>();
		//List<String> indicator = new ArrayList<String>();
		List<Integer> ldrspbnspgm = new ArrayList<Integer>();
		String id = null;
		while (rs.next()) {
			id = rs.getString("CDE_VEH_TYP");
			if (id.equalsIgnoreCase(oldId)) {
				condtionList.add(rs.getString("NAM_LDRSP_BNS"));
				/*accountIDList.add(rs.getString("ID_ACCT_CR"));
				accountIDListDT.add(rs.getString("ID_ACCT_DT"));*/
				if(rs.getString("ID_ACCT_CR") != null) {
					accountIDList.add(rs.getString("ID_ACCT_CR").trim());
				} else {
					accountIDList.add(rs.getString("ID_ACCT_CR"));
				}
				if(rs.getString("ID_ACCT_DT") != null) {
					accountIDListDT.add(rs.getString("ID_ACCT_DT").trim());
				} else {
					accountIDListDT.add(rs.getString("ID_ACCT_DT"));
				}
				if(null != rs.getString("STATUS") && !IConstants.EMPTY_STR.equalsIgnoreCase(rs.getString("STATUS")) ) {
					accountStatus = rs.getString("STATUS");
				} else {
					accountStatus = "D";
				}
				accStatus.add(accountStatus);
				//indicator.add(rs.getString("IND_LDRSP_BNS_PGM"));
				ldrspbnspgm.add(rs.getInt("ID_VEH_REL"));
			} else {
				if (oldId != null && !oldId.equalsIgnoreCase("")) {
					vcMapping.getConditionList().add(condtionList);
					vcMapping.getConditionList().add(accountIDList);
					vcMapping.getConditionList().add(accountIDListDT);
					vcMapping.getConditionList().add(accStatus);
					//vcMapping.getConditionList().add(indicator);
					vcMapping.getConditionList().add(ldrspbnspgm);
					vcList.add(vcMapping);
				}
				vcMapping = new VehicleConditionMapping();
				condtionList = new ArrayList<String>();
				accountIDList = new ArrayList<String>();
				accStatus = new ArrayList<String>();
				//indicator = new ArrayList<String>();
				accountIDListDT = new ArrayList<String>();
				ldrspbnspgm = new ArrayList<Integer>();
				condtionList.add(rs.getString("NAM_LDRSP_BNS"));
				vcMapping.setVehicleId(rs.getString("CDE_VEH_TYP"));
				if(rs.getString("ID_ACCT_CR") != null) {
					accountIDList.add(rs.getString("ID_ACCT_CR").trim());
				} else {
					accountIDList.add(rs.getString("ID_ACCT_CR"));
				}
				if(rs.getString("ID_ACCT_DT") != null) {
					accountIDListDT.add(rs.getString("ID_ACCT_DT").trim());
				} else {
					accountIDListDT.add(rs.getString("ID_ACCT_DT"));
				}
				
				if(null != rs.getString("STATUS") && !IConstants.EMPTY_STR.equalsIgnoreCase(rs.getString("STATUS")) ) {
					accountStatus = rs.getString("STATUS");
				} else {
					accountStatus = "D";
				}
				accStatus.add(accountStatus);
				//indicator.add(rs.getString("IND_LDRSP_BNS_PGM"));
				ldrspbnspgm.add(rs.getInt("ID_VEH_REL"));
				vcMapping.setStatus(accountStatus);
				oldId = rs.getString("CDE_VEH_TYP");
			}
		}
		if (oldId != null && !oldId.equalsIgnoreCase("")) {
			vcMapping.getConditionList().add(condtionList);
			vcMapping.getConditionList().add(accountIDList);
			vcMapping.getConditionList().add(accountIDListDT);
			vcMapping.getConditionList().add(accStatus);
			//vcMapping.getConditionList().add(indicator);
			vcMapping.getConditionList().add(ldrspbnspgm);
			vcList.add(vcMapping);
		}
		vcMap.put("VC_LIST", vcList);
	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.ACC_ID_MAP_LST.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.ACC_ID_MAP_LST.002", ne);
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
			LOGGER.error("LDRSP_BNS.ACC_ID_MAP_LST.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return vcMap;
}
/**
 * getAccoundIDList for fetching Program Account ID Mapping List
 * @return Map
*/
public List<AccountIdMappingDefinition> getAccoundIDList() {
	final String methodName = "getAccoundIDList";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	AccountIdMappingDefinition accMap = new AccountIdMappingDefinition();
	List<AccountIdMappingDefinition> programComponent = null;
	try {
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.ACC_ID_MAP_LST;
		ps = con.prepareStatement(query);
		LOGGER.info("ACC_ID_MAP_LST Query " + query);
		rs = ps.executeQuery();
		programComponent = new ArrayList<AccountIdMappingDefinition>();
		while (rs.next()) {
			accMap = new AccountIdMappingDefinition();
			accMap.setId(rs.getString("CDE_VEH_TYP"));
			accMap.setProgramComponentName(rs.getString("NAM_LDRSP_BNS"));
			//accMap.setAccountId(rs.getInt("ID_DPB_COFICO_ACCT"));
			if(rs.getString("ID_ACCT_CR") != null) {
				accMap.setAccountCFCCR(rs.getString("ID_ACCT_CR").trim());
			} else {
				accMap.setAccountCFCCR(rs.getString("ID_ACCT_CR"));
			}	
			if(rs.getString("ID_ACCT_DT") != null) {
				accMap.setAccountCFCDT(rs.getString("ID_ACCT_DT").trim());
			} else {
				accMap.setAccountCFCDT(rs.getString("ID_ACCT_DT"));
			}
			
			if(null != rs.getString("STATUS") && !IConstants.EMPTY_STR.equalsIgnoreCase(rs.getString("STATUS")) ) {	
				accMap.setStatusMapping(rs.getString("STATUS"));
			} else {
				accMap.setStatusMapping("D");
			}
			//accMap.setIndicator(rs.getString("IND_LDRSP_BNS_PGM"));
			accMap.setIdBnsPgm(rs.getInt("ID_VEH_REL"));
			programComponent.add(accMap);
		}
	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.ACC_ID_MAP_LST.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.ACC_ID_MAP_LST.002", ne);
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
			LOGGER.error("LDRSP_BNS.ACC_ID_MAP_LST.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return programComponent;
}
/**
 * saveAccountIDMapping for inserting and updating Program Account ID Mapping List
 * @param acctMapList
*/
public void saveAccountIDMapping(List<AccountMapping> acctMapList,String userId) {
	final String methodName = "saveAccountIDMapping";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String query = null;

	try {
		con = conFactory.getConnection();
		if (con != null) {
			query = IDefinitionQueryConstants.INSERT_UPDATE_ACC_ID_MAP;
			LOGGER.info("INSERT_UPDATE_ACCOUNTID_MAPPING Query " + query);
			ps = con.prepareStatement(query);
			for (int i = 0; i < acctMapList.size(); i++) {
				/*ps.setString(1, (String)acctMapList.get(i).getVehType());
				ps.setInt(2, Integer.valueOf(acctMapList.get(i).getIdpgm()));
				ps.setInt(3, Integer.valueOf(acctMapList.get(i).getAccountId()));
				ps.setString(4, acctMapList.get(i).getIndpgm());
				ps.setString(5, acctMapList.get(i).getStatus());*/
				ps.setString(1, acctMapList.get(i).getAccountId());
				ps.setString(2, acctMapList.get(i).getIndpgm());
				ps.setString(3, acctMapList.get(i).getStatus());					
				ps.setString(4, userId);
				ps.setInt(5, Integer.valueOf(acctMapList.get(i).getIdpgm()));
				ps.executeUpdate();
			}
		}
	} catch (SQLException e) {
		LOGGER.error("LDRSP_BNS.INSERT_UPDATE_ACC_ID_MAP.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("LDRSP_BNS.INSERT_UPDATE_ACC_ID_MAP.002", ne);
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
			LOGGER.error("LDRSP_BNS.INSERT_UPDATE_ACC_ID_MAP.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
////AccountID Mapping End	
//Nikhil changes start
@Override
public int createReportDefinition(ReportDefinitionBean reportDef){
	final String methodName = "createReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	int pId = 0;
	try {
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.CREATE_REPORT_DEF_QUERY;
		statement = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, reportDef.getReportName()!=null ? reportDef.getReportName().trim():null);
		statement.setString(2, reportDef.getDescription()!=null ? reportDef.getDescription().trim():null);
		statement.setInt(3, reportDef.getSubReports());
		statement.setString(4, reportDef.getScheduleCode());
		statement.setString(5, reportDef.getTriggerCode());
		statement.setString(6, reportDef.getDefStatusCode());
		statement.setString(7, reportDef.getCreatedByUser());
		statement.setString(8, reportDef.getUpdatedByUser());
		statement.setInt(9, reportDef.getReportLpp());
		statement.executeUpdate();		
		rs = statement.getGeneratedKeys();          
		if(rs != null && rs.next()){
			pId =rs.getInt(1);
		}
	}catch (SQLException e) {
		LOGGER.error("DPB.RPT.DEF.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("DPB.RPT.DEF.002", ne);
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
			LOGGER.error("DPB.RPT.DEF.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return pId;
}

public List<ReportDefinitionBean> getReportDefinitionList(){
	final String methodName = "getReportDefinitionList";
	LOGGER.enter(CLASSNAME, methodName);
	List<ReportDefinitionBean> reportDefList = null;
	ReportDefinitionBean reportDefBean;
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	try {
		con  = conFactory.getConnection();
		String query = IDefinitionQueryConstants.RETRIEVE_REPORT_DEF_LIST;
		if (con != null) {
			reportDefList = new ArrayList<ReportDefinitionBean>();
			statement = con.prepareStatement(query);
			rs = statement.executeQuery();
			while (rs.next()) {
				reportDefBean = new ReportDefinitionBean();
				reportDefBean.setReportDefId(rs.getInt("ID_DPB_RPT"));
				reportDefBean.setReportName(rs.getString("NAM_DPB_RPT")!=null ? rs.getString("NAM_DPB_RPT").trim():null);
				reportDefBean.setSubReports(rs.getInt("QTY_DPB_SUB_RPT"));
				reportDefBean.setScheduleCode(rs.getString("CDE_DPB_SCHD"));
				reportDefBean.setTriggerCode(rs.getString("CDE_DPB_PROC_INIT_TYP"));
				reportDefBean.setDefStatusCode(rs.getString("CDE_DPB_STS"));
				reportDefBean.setReportLpp(rs.getInt("QTY_DPB_RPT_LPP"));
				reportDefList.add(reportDefBean);
			}
		}
	}catch(SQLException e){
		LOGGER.error("DPB.RPT.DEF.013", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException ne){
		LOGGER.error("DPB.RPT.DEF.014", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
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
    	  LOGGER.error("DPB.RPT.DEF.015", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
	return reportDefList;
}

public void createDPBReportProcess(ReportDefinitionBean reportDef, List<java.sql.Date> aProcDteListMap) throws BusinessException{
	LOGGER.enter(CLASSNAME, "createDPBReportProcess()");     
    //List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    //java.sql.Date startDate = DateCalenderUtil.convertStringToSQLDateFormat(DateCalenderUtil.getNextDayDate());
    //PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
	//boolean isDevelopment = PROPERTY_MANAGER.getPropertyAsBoolean("dpb.development");
	try{
		/*if(isDevelopment){
			java.sql.Date rtlDate = DateCalenderUtil.getCurrentDateTime();
			String query=IDefinitionQueryConstants.INSERT_DPB_RPT_PROCESS;
	        con = conFactory.getConnection();
	        	 ps = con.prepareStatement(query);
	        	 ps.setDate(1, rtlDate);
	        	 ps.setDate(2, rtlDate);
	        	 ps.setInt(3, reportDef.getReportDefId());
	             ps.setString(4, IConstants.REPORT_PROCESS_NAME);
	             ps.setString(5, reportDef.getCreatedByUser());
	             ps.setString(6, reportDef.getUpdatedByUser());
	             ps.executeUpdate();
		}
		else{*/
    	 //dateList = DateCalenderUtil.scheduleList(startDate, rEndDate, reportDef.getScheduleCode());
	    	if(aProcDteListMap ==  null){
	       	 	throw new BusinessException("proc", "DPB Process could not be scheduled");
	        }else if(aProcDteListMap.isEmpty()){
            	throw new BusinessException("invSchd", "Retail dates are not found for selected schedule");
            }
			else if(!aProcDteListMap.isEmpty()){
	       
	            String query=IDefinitionQueryConstants.INSERT_DPB_RPT_PROCESS;
	            con = conFactory.getConnection();
	            ps = con.prepareStatement(query);
	                for(java.sql.Date rtlDate:aProcDteListMap ){
	                	 ps.setDate(1, rtlDate);
	                	 ps.setDate(2, rtlDate);
	                	 ps.setInt(3, reportDef.getReportDefId());
	                     ps.setString(4, IConstants.REPORT_PROCESS_NAME);
	                     ps.setString(5, reportDef.getCreatedByUser());
	                     ps.setString(6, reportDef.getUpdatedByUser());
	                  ps.addBatch();
	                }
	                ps.executeBatch();
			}
		/*}*/
	}
    catch(SQLException  e){
    	LOGGER.error("DPB.RPT.PROC.001", e);
          DPBExceptionHandler.getInstance().handleDatabaseException(e);
    }catch(NamingException  ne){
    	LOGGER.error("DPB.RPT.PROC.002", ne);
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
        	  LOGGER.error("DPB.RPT.PROC.003", e);
                DPBExceptionHandler.getInstance().handleDatabaseException(e);
          }
    }
}

public void updateReportInactiveStatus(ReportDefinitionBean reportDef){
	final String methodName = "updateReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	java.sql.Date today = DateCalenderUtil.getCurrentDateTime();
	java.sql.Date nextDay = DateCalenderUtil.convertStringToSQLDateFormat(DateCalenderUtil.getNextDayDate());
	try{		
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.UPDATE_REPORT_INACTIVE_QUERY;
		statement = con.prepareStatement(query);
		statement.setString(1, reportDef.getDefStatusCode());
		statement.setString(2, reportDef.getUpdatedByUser());
		statement.setInt(4, reportDef.getReportDefId());
		if(reportDef.isFlagActive()){
			statement.setDate(3, today);
		}else{
			statement.setDate(3, nextDay);
		}
		statement.executeUpdate();
	}catch (SQLException e) {
		LOGGER.error("DPB.RPT.DEF.016", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("DPB.RPT.DEF.017",ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	} finally {
		try{			
			if (statement != null) {
				statement.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.RPT.DEF.018", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}


public ReportDefinitionBean populateQCRelations(ReportDefinitionBean reportDef){
	final String methodName = "populateQCRelations";
	LOGGER.enter(CLASSNAME, methodName);
	List<QCRelationBean> qcrList = new ArrayList<QCRelationBean>();
	List<QCRelationBean> list = reportDef.getQcrList(); 
	if(list!=null && list.size() > 0){
		updateQCRelations(reportDef);
		for(QCRelationBean qcrBean:list){
		   int qcrId = saveQCRelations(qcrBean, reportDef);
		   qcrBean.setQcrId(qcrId);
		   qcrList.add(qcrBean);
		}
	}
	reportDef.setQcrList(qcrList);
	LOGGER.exit(CLASSNAME, methodName);
	return reportDef;
}

public int saveQCRelations(QCRelationBean qcrBean, ReportDefinitionBean reportDef){
	int qcrid = 0;
	final String methodName = "saveQCRelations";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	String query = null;
	try {
		con = conFactory.getConnection();
		query = IDefinitionQueryConstants.CREATE_QCR_DEF_QUERY;
		if(qcrBean.getFooterId() == 0){
			query = IDefinitionQueryConstants.CREATE_QCR_DEF_QUERY_FTR;
		}statement = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, qcrBean.getContId());
			statement.setInt(2, qcrBean.getQueryId());
			statement.setInt(3, reportDef.getReportDefId());
			statement.setInt(4, qcrBean.getSeqNum());
			statement.setString(5, reportDef.getCreatedByUser());
			statement.setString(6, reportDef.getUpdatedByUser());
		if(qcrBean.getFooterId() != 0){
			statement.setInt(7, qcrBean.getFooterId());
		}statement.executeUpdate();		
		rs = statement.getGeneratedKeys();          
		if(rs != null && rs.next()){
			qcrid =rs.getInt(1);
		}
	}catch (SQLException e) {
		LOGGER.error("DPB.QCR.DEF.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("DPB.QCR.DEF.002", ne);
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
			LOGGER.error("DPB.QCR.DEF.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return qcrid;
}

public void updateQCRelations(ReportDefinitionBean reportDef){
	final String methodName = "updateQCRelations";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	try {
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.UPDATE_QCR_DEF_QUERY;
		statement = con.prepareStatement(query);
		statement.setInt(1, reportDef.getReportDefId());
		statement.executeUpdate();		
	}catch (SQLException e) {
		LOGGER.error("DPB.QCR.DEF.004", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("DPB.QCR.DEF.005", ne);
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
			LOGGER.error("DPB.QCR.DEF.006", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}

@Override
public void updateReportDefinition(ReportDefinitionBean reportDef) {
	final String methodName = "updateReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	try{		
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.UPDATE_REPORT_DEF_QUERY;
		statement = con.prepareStatement(query);
		statement.setString(1, reportDef.getReportName()!=null ? reportDef.getReportName().trim():null);
		statement.setString(2, reportDef.getDescription()!=null ? reportDef.getDescription().trim():null);
		statement.setInt(3, reportDef.getSubReports());
		statement.setString(4, reportDef.getScheduleCode());
		statement.setString(5, reportDef.getTriggerCode());
		statement.setString(6, reportDef.getDefStatusCode());
		statement.setString(7, reportDef.getUpdatedByUser());
		statement.setInt(8, reportDef.getReportLpp());
		statement.setInt(9, reportDef.getReportDefId());
		statement.executeUpdate();
	}catch (SQLException e) {
		LOGGER.error("DPB.RPT.DEF.004", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("DPB.RPT.DEF.005",ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	} finally {
		try{			
			if (statement != null) {
				statement.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.RPT.DEF.006", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}

public ReportDefinitionBean getEditReportDefinition(String reportDefId){
	ReportDefinitionBean reportDef = null;
	QCRelationBean qcrBean = null;
	final String methodName = "getEditFileFormatDetails";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	reportDef = new ReportDefinitionBean();
	List<QCRelationBean> qcrList = reportDef.getQcrList();
	try {
		con  = conFactory.getConnection();
		String query = IDefinitionQueryConstants.RETRIEVE_REPORT_DEF_EDIT_QUERY;
		if (con != null) {
			statement = con.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(reportDefId));
			rs = statement.executeQuery();
			while (rs.next()) {
				qcrBean = new QCRelationBean();
				reportDef.setReportDefId(rs.getInt("ID_DPB_RPT"));
				reportDef.setReportName(rs.getString("NAM_DPB_RPT")!=null ? rs.getString("NAM_DPB_RPT").trim():null);
				reportDef.setDescription(rs.getString("DES_DPB_RPT")!=null ? rs.getString("DES_DPB_RPT").trim():null);
				reportDef.setSubReports(rs.getInt("QTY_DPB_SUB_RPT"));
				reportDef.setScheduleCode(rs.getString("CDE_DPB_SCHD"));
				reportDef.setTriggerCode(rs.getString("CDE_DPB_PROC_INIT_TYP"));
				reportDef.setReportLpp(rs.getInt("QTY_DPB_RPT_LPP"));
				reportDef.setDefStatusCode(rs.getString("CDE_DPB_STS"));
				qcrBean.setQcrId(rs.getInt("ID_DPB_RPT_QRY_CTNT_REL"));
				qcrBean.setContId(rs.getInt("ID_DPB_RPT_CTNT"));
				qcrBean.setQueryId(rs.getInt("ID_DPB_RPT_QRY"));
				qcrBean.setSeqNum(rs.getInt("NUM_DPB_RPT_SEQ"));
				qcrBean.setFooterId(rs.getInt("ID_DPB_RPT_CTNT_FTR"));
				qcrBean.setNameCtnt(rs.getString("NAM_DPB_RPT_CTNT"));
				qcrBean.setNameQry(rs.getString("NAM_DPB_RPT_QRY"));
				qcrBean.setNameFooter(rs.getString("FOOTER"));
				qcrList.add(qcrBean);
			}
			reportDef.setQcrList(qcrList);
		}
	}catch(SQLException e){
		LOGGER.error("DPB.RPT.DEF.007", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		//throw new RuntimeException();
	}catch(NamingException ne){
		LOGGER.error("DPB.RPT.DEF.008", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
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
    	  LOGGER.error("DPB.RPT.DEF.009", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
	return reportDef;
}

/*@Override
public ReportDefinitionBean retrieveDefaultReportDefinition(ReportDefinitionBean reportDefBean){
	final String methodName = "retrieveDefaultReportDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	String query;
	if(reportDefBean == null){
	reportDefBean = new ReportDefinitionBean();
	}
	try {
		con = conFactory.getConnection();
		query = IDefinitionQueryConstants.RETRIEVE_REPORT_CONTENT;
		statement = con.prepareStatement(query);
		rs = statement.executeQuery();
		Map<String, String> reportContentList = new HashMap<String,String>();
		while (rs.next()) {
			reportContentList.put(rs.getString("ID_DPB_RPT_CTNT")!=null ? rs.getString("ID_DPB_RPT_CTNT").trim():null, rs.getString("NAM_DPB_RPT_CTNT")!=null ? rs.getString("NAM_DPB_RPT_CTNT").trim():null);
		}
		reportDefBean.setReportContentList(reportContentList);

		query = IDefinitionQueryConstants.RETRIEVE_REPORT_QUERY;
		statement = con.prepareStatement(query);
		rs = statement.executeQuery();
		Map<String, String> reportQueryList = new HashMap<String,String>();
		while (rs.next()) {
			reportQueryList.put(rs.getString("ID_DPB_RPT_QRY")!=null ? rs.getString("ID_DPB_RPT_QRY").trim():null, rs.getString("NAM_DPB_RPT_QRY")!=null ?  rs.getString("NAM_DPB_RPT_QRY").trim():null);
		}
		reportDefBean.setReportQueryList(reportQueryList);
		con.close();
	}catch (SQLException e) {
		LOGGER.error("DPB.RPT.DEF.010", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("DPB.RPT.DEF.011", ne);
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
			LOGGER.error("DPB.RPT.DEF.012", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return reportDefBean;	
}*/


@Override
public List<ProcessCalDefBean> getCurrentMonthProcess(){
	final String methodName = "getCurrentMonthProcess";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<ProcessCalDefBean> processCalView = new ArrayList<ProcessCalDefBean>();	
	ProcessCalDefBean processCalDefBean = null;
	try {
		con  = conFactory.getConnection();
		String query = IDefinitionQueryConstants.processCalDefQuery;

		if (con != null) {
			preparedStatement = con.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				processCalDefBean = new ProcessCalDefBean();
				processCalDefBean.setProcessType(resultSet.getString("CDE_DPB_PROC_TYP")!=null ? resultSet.getString("CDE_DPB_PROC_TYP").trim():null);
				processCalDefBean.setProcessDefinitionID(String.valueOf(resultSet.getInt("ID_DPB_PROC")));
				Calendar calendar = Calendar.getInstance();
				Timestamp timeStamp = resultSet.getTimestamp("DTE_CAL");
				Timestamp ovrdTimeStamp = resultSet.getTimestamp("OVRD_PROC_DTE");
				if(ovrdTimeStamp != null && ovrdTimeStamp.getTime() > 0){
					calendar.setTimeInMillis(ovrdTimeStamp.getTime());
				} else if(timeStamp != null){
					calendar.setTimeInMillis(timeStamp.getTime());
				}
				processCalDefBean.setRetaildate(calendar);
				processCalView.add(processCalDefBean);
			}
		}
	}catch (SQLException e) {
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		DPBExceptionHandler.getInstance().handleException(ne);
	} finally {
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
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return processCalView;
}

@Override
public FileProcessDefBean getFileProcessDefination(int processId) {
	final String methodName = "getFileProcessDefination";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	FileProcessDefBean fileProcessDefBean = null;
	PreparedStatement ps = null;
	try {
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.retrieveFileProcessDefQuery;
		if (con != null) {
			ps = con.prepareStatement(query);
			ps.setInt(1, processId);
			//ps.setInt(2, processId);
			rs = ps.executeQuery();
			//boolean overLoadedDataAvailable = false;
			while (rs.next()) {
				fileProcessDefBean = new FileProcessDefBean();
				fileProcessDefBean.setProcessDefinitionId(processId);
				fileProcessDefBean.setDefinitionId(rs.getInt("ID_DPB_PROC"));
				int fileId = rs.getInt("ID_DPB_FILE");
				int pgmId = rs.getInt("ID_DPB_PGM");
				int rptId = rs.getInt("ID_DPB_RPT");
				int ldrPgm=rs.getInt("ID_LDRSP_BNS_PGM");
				if(fileId == 0 && pgmId == 0  && ldrPgm == 0)
				{
					fileProcessDefBean.setProgId(rptId);
				}
				 if ( rptId == 0 && fileId == 0 && ldrPgm == 0 )
					{
						fileProcessDefBean.setProgId(pgmId);
					}
				 if( pgmId == 0 &&  rptId == 0 && ldrPgm == 0 )
				{
					fileProcessDefBean.setProgId(fileId);
				}
				 if( pgmId == 0 &&  rptId == 0 && fileId == 0 )
				 {
					 fileProcessDefBean.setProgId(ldrPgm);
				 }
				fileProcessDefBean.setDefinitionType(rs.getString("CDE_DPB_PROC_TYP"));
				Calendar calendar = Calendar.getInstance();
				Timestamp timeStamp = rs.getTimestamp("DTE_CAL");
				Timestamp ovrdTimeStamp = rs.getTimestamp("ID_OVRD_PROC_DTE");
				if(ovrdTimeStamp != null && ovrdTimeStamp.getTime() > 0){
					//overLoadedDataAvailable = true;
					calendar.setTimeInMillis(ovrdTimeStamp.getTime());
				} else if(timeStamp != null){
					calendar.setTimeInMillis(timeStamp.getTime());
				}
				fileProcessDefBean.setProcessDate(calendar);
				fileProcessDefBean.setReasonForUpdate(DateCalenderUtil.makeNonNullString(rs.getString("TXT_RSN_PROC_UPD")).trim());
				fileProcessDefBean.setProcessingTime(rs.getTime("TME_OVRD_PROC"));
				fileProcessDefBean.setProcessingTrigger(rs.getString("CDE_DPB_OVRD_PROC_INIT_TYP"));
				fileProcessDefBean.setStatus(DateCalenderUtil.makeNonNullString(rs.getString("STATUS")).trim());
				fileProcessDefBean.setRecCount(rs.getInt("RECCOUNT"));
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
	LOGGER.exit(CLASSNAME, methodName);
	return fileProcessDefBean;
}
@Override
public int updateFileProcessDefinition(FileProcessDefBean fileProcessDefBean) {
	final String methodName = "updateFileProcessDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	int processId= 0;
	try {
		con = conFactory.getConnection();
		String query = "";
		if (con != null) {
			
			if(fileProcessDefBean.isReProcessFlag()){
				query = "UPDATE DPB_PROC SET TME_PROC_OVRD = ? ,CDE_DPB_OVRD_PROC_INIT_TYP = ?, TXT_RSN_PROC_UPD = ?, ID_LAST_UPDT_USER = ? ,DTS_LAST_UPDT = CURRENT TIMESTAMP WHERE ID_DPB_PROC = ?";
				ps = con.prepareStatement(query);
				ps.setTime(1, fileProcessDefBean.getProcessingTime());
				ps.setString(2, fileProcessDefBean.getProcessingTrigger());
				ps.setString(3, fileProcessDefBean.getReasonForUpdate());
				ps.setString(4,fileProcessDefBean.getUser());
				ps.setInt(5, fileProcessDefBean.getDefinitionId());
				ps.executeUpdate();
				}else{
					query=IDefinitionQueryConstants.retrieveRetailDateId;
					ps = con.prepareStatement(query);
					String ts = fileProcessDefBean.getProcessDateString();
					ps.setString(1, ts);
					ps.setInt(2,fileProcessDefBean.getProcessDefinitionId());
					rs = ps.executeQuery();
					java.sql.Date rtlCalId = null;
					int idAppEnt=0;
					java.sql.Date idActProc = null;
					int fileId =0;
					int pgmId = 0;
					int rptId =0;
					int idLdrPgm=0;
					while(rs.next()){
						rtlCalId = rs.getDate("ID_DAY");
						fileId = rs.getInt("ID_DPB_FILE");
						pgmId = rs.getInt("ID_DPB_PGM");
						rptId = rs.getInt("ID_DPB_RPT");
						idLdrPgm = rs.getInt("ID_LDRSP_BNS_PGM");
						
						idActProc=rs.getDate("DTE_DPB_ACTL_PROC");
					}
					
						if(fileProcessDefBean.getRecCount() == 0)
						{
							if( rtlCalId !=  null ){
								processId = insertLogProcessCal(fileProcessDefBean,rtlCalId,idActProc,fileId,pgmId,rptId,idLdrPgm);
								processId = insertProcProcessCal(fileProcessDefBean,rtlCalId,idActProc,fileId,pgmId,rptId,idLdrPgm);
							}
						}
					else
					{
						if( rtlCalId !=  null ){
							processId = insertLogProcessCal(fileProcessDefBean,rtlCalId,idActProc,fileId,pgmId,rptId,idLdrPgm);
							processId = insertProcProcessCal(fileProcessDefBean,rtlCalId,idActProc,fileId,pgmId,rptId,idLdrPgm);
						}					
					}
				}
		}	
	} catch (SQLException e) {
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		DPBExceptionHandler.getInstance().handleException(ne);
	}catch (Exception e) {
		e.printStackTrace();
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
	return processId;
	}

public void updateLogProcessCal(FileProcessDefBean fileProcessDefBean)
{
	final String methodName = "updateLogProcessCal";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String query="";
	try {
		con = conFactory.getConnection();
		if (con != null) 
		{
			query=IDefinitionQueryConstants.updateFileprocLogQuery;
			ps = con.prepareStatement(query);
			ps.setString(1,fileProcessDefBean.getProcessType());
			ps.setInt(2,fileProcessDefBean.getProcessDefinitionId());
			
			ps.executeUpdate();
		}
	}
	 catch (SQLException e) {
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
}
public int insertProcProcessCal(FileProcessDefBean fileProcessDefBean,java.sql.Date rtlCalId,java.sql.Date idActProc,int fileId,int pgmId,int rptId,int idLdrPgm)
{
	final String methodName = "updateLogProcessCal";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String query="";
	int processId =0;
	try {
		con = conFactory.getConnection();
		if (con != null) 
		{
			if(fileId == 0 && pgmId == 0 &&  idLdrPgm == 0)
			{
				query=IDefinitionQueryConstants.insertReportProcessDefQuery;
				ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				ps.setDate(1,rtlCalId);
				ps.setInt(2,rptId);
				ps.setDate(3,idActProc);
				ps.setString(4,fileProcessDefBean.getDefinitionType());
				ps.setTime(5, fileProcessDefBean.getProcessingTime());
				ps.setString(6, fileProcessDefBean.getProcessingTrigger());
				ps.setString(7, fileProcessDefBean.getReasonForUpdate());
				ps.setString(8,fileProcessDefBean.getUser());
				ps.setString(9,fileProcessDefBean.getUser());
			}
			 if ( rptId == 0 && fileId == 0 && idLdrPgm == 0 )
				{
				 query=IDefinitionQueryConstants.insertPgmProcessDefQuery;
				 ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
					ps.setDate(1,rtlCalId);
					ps.setInt(2,pgmId);
					ps.setDate(3,idActProc);
					ps.setString(4,fileProcessDefBean.getDefinitionType());
					ps.setTime(5, fileProcessDefBean.getProcessingTime());
					ps.setString(6, fileProcessDefBean.getProcessingTrigger());
					ps.setString(7, fileProcessDefBean.getReasonForUpdate());
					ps.setString(8,fileProcessDefBean.getUser());
					ps.setString(9,fileProcessDefBean.getUser());
				}
			 if( pgmId == 0 &&  rptId == 0 && idLdrPgm == 0 )
			{
				 query=IDefinitionQueryConstants.insertFileProcessDefQuery;
				 ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				 ps.setDate(1,rtlCalId);
					ps.setInt(2,fileId);
					ps.setDate(3,idActProc);
					ps.setString(4,fileProcessDefBean.getDefinitionType());
					ps.setTime(5, fileProcessDefBean.getProcessingTime());
					ps.setString(6, fileProcessDefBean.getProcessingTrigger());
					ps.setString(7, fileProcessDefBean.getReasonForUpdate());
					ps.setString(8,fileProcessDefBean.getUser());
					ps.setString(9,fileProcessDefBean.getUser());
			}
			
			 if ( rptId == 0 && fileId == 0 && pgmId == 0  )
				{
				 query=IDefinitionQueryConstants.insertLdrPgmProcessDefQuery;
				 ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
					ps.setDate(1,rtlCalId);
					ps.setInt(2,idLdrPgm);
					ps.setDate(3,idActProc);
					ps.setString(4,fileProcessDefBean.getDefinitionType());
					ps.setTime(5, fileProcessDefBean.getProcessingTime());
					ps.setString(6, fileProcessDefBean.getProcessingTrigger());
					ps.setString(7, fileProcessDefBean.getReasonForUpdate());
					ps.setString(8,fileProcessDefBean.getUser());
					ps.setString(9,fileProcessDefBean.getUser());
				}
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();          
			 if(rs.next()){             
				 processId =rs.getInt(1);
			}
		}
	}
	 catch (SQLException e) {
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
	return processId;
}

private int insertLogProcessCal (FileProcessDefBean fileProcessDefBean,java.sql.Date rtlCalId,java.sql.Date idActProc,int fileId,int pgmId,int rptId,int idLdrPgm)
{
	final String methodName = "updateLogProcessCal";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String query="";
	int processId =0;
	try {
		con = conFactory.getConnection();
		if (con != null) 
		{
			query=IDefinitionQueryConstants.insertFileprocLogQuery;
			ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, fileProcessDefBean.getDefinitionId());
			ps.setString(2,fileProcessDefBean.getProcessType());
			ps.setString(3,fileProcessDefBean.getReasonForUpdate());
			ps.setString(4,fileProcessDefBean.getUser());
			ps.setString(5,fileProcessDefBean.getUser());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();          
			 if(rs.next()){             
				 processId =rs.getInt(1);
			}
		}
	}
	 catch (SQLException e) {
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
	return processId;
}

public int createReportContentDefinition(ReportContentDefinitionBean reportContentDefBean) {
	final String methodName = "createReportContentDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	int pId = 0;
	try {		
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.CREATE_REPORT_CONTENT_DEF_QUERY;
		statement = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);		
		statement.setString(1, reportContentDefBean.getReportContentName());
		statement.setString(2, reportContentDefBean.getReportContentDescription());
		statement.setBytes(3, reportContentDefBean.getReportContent().getBytes());
		statement.setInt(4, reportContentDefBean.getQryRsltLpp());
		statement.setString(5, reportContentDefBean.getDefStatusCode());
		statement.setString(6, reportContentDefBean.getCreatedByUser());
		statement.setString(7, reportContentDefBean.getUpdatedByUser());
		statement.executeUpdate();		
		rs = statement.getGeneratedKeys();          
		if(rs != null && rs.next()){
			pId =rs.getInt(1);
		}
	}  catch (SQLException e) {
		LOGGER.error("DPB.RPT.CNT.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("DPB.RPT.CNT.002", ne);
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
			LOGGER.error("DPB.RPT.CNT.003", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return pId;
}

@Override
public void updateReportContentDefinition(ReportContentDefinitionBean reportContentDefBean) {
	final String methodName = "updateReportContentDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	try {
		con = conFactory.getConnection();
		String query = IDefinitionQueryConstants.UPDATE_REPORT_CONTENT_DEF_QUERY;
		statement = con.prepareStatement(query);
		statement.setString(1, reportContentDefBean.getReportContentName());
		statement.setString(2, reportContentDefBean.getReportContentDescription());
		statement.setBytes(3, reportContentDefBean.getReportContent().getBytes());
		statement.setInt(4, reportContentDefBean.getQryRsltLpp());
		statement.setString(5, reportContentDefBean.getDefStatusCode());
		statement.setString(6, reportContentDefBean.getUpdatedByUser());
		statement.setInt(7, reportContentDefBean.getReportContentDefId());
		statement.executeUpdate();
	
	}catch (SQLException e) {
		LOGGER.error("DPB.RPT.CNT.004", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("DPB.RPT.CNT.005", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	} finally {
		try {			
			if (statement != null) {
				statement.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.RPT.CNT.006", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	}
	
public void updateReportContentInactive(ReportContentDefinitionBean reportContentDefBean) throws BusinessException{
	final String methodName = "updateReportContentInactive";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	try {
		con = conFactory.getConnection();
		String chkQyery = IDefinitionQueryConstants.CHECK_INACTIVE_RC_STATUS;
		statement = con.prepareStatement(chkQyery);
		statement.setInt(1, reportContentDefBean.getReportContentDefId());
		rs = statement.executeQuery();
		if(!rs.next()){
			String query = IDefinitionQueryConstants.UPDATE_REPORT_CONTENT_INACT_QUERY;
			statement = con.prepareStatement(query);
			statement.setString(1, reportContentDefBean.getDefStatusCode());
			statement.setString(2, reportContentDefBean.getUpdatedByUser());
			statement.setInt(3, reportContentDefBean.getReportContentDefId());
			statement.executeUpdate();
	
		}
		else{
			throw new BusinessException("msg", "Can not inactivate, Record is being used by Report definition");
		}
	}catch (SQLException e) {
		LOGGER.error("DPB.RPT.CNT.004", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error("DPB.RPT.CNT.005", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	} finally {
		try {			
			if (statement != null) {
				statement.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.RPT.CNT.006", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}

public List<ReportContentDefinitionBean> getReportContentList(){
	final String methodName = "getReportContentList";
	List<ReportContentDefinitionBean> rptCntList = null;
	ReportContentDefinitionBean reportContentDefBean;
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	try {
		con  = conFactory.getConnection();
		String query = IDefinitionQueryConstants.RETRIEVE_REPORT_CONTENT_DEF_LIST;
		if (con != null) {
			rptCntList = new ArrayList<ReportContentDefinitionBean>();
			statement = con.prepareStatement(query);
			rs = statement.executeQuery();
			while (rs.next()) {
				reportContentDefBean = new ReportContentDefinitionBean();
				reportContentDefBean.setReportContentDefId(rs.getInt("ID_DPB_RPT_CTNT"));
				reportContentDefBean.setReportContentName(rs.getString("NAM_DPB_RPT_CTNT"));
				reportContentDefBean.setQryRsltLpp(rs.getInt("CNT_DPB_RPT_QRY_RSLT_LPP"));
				reportContentDefBean.setDefStatusCode(rs.getString("CDE_DPB_STS"));
				rptCntList.add(reportContentDefBean);
			}
		}
	}catch(SQLException e){
		LOGGER.error("DPB.RPT.CNT.LIST.001", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		//throw new RuntimeException();
	}catch(NamingException ne){
		LOGGER.error("DPB.RPT.CNT.LIST.002", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
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
    	  LOGGER.error("DPB.RPT.CNT.LIST.003", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
	return rptCntList;
}

public ReportContentDefinitionBean getEditReportContent(String rptCntDefId){
	final String methodName = "getEditReportContent";
	LOGGER.enter(CLASSNAME, methodName);
	ReportContentDefinitionBean reportContentDefBean = null;
	Connection con = null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	try {
		con  = conFactory.getConnection();
		String query = IDefinitionQueryConstants.RETRIEVE_REPORT_CONTENT_EDIT_QUERY;
		if (con != null) {
			statement = con.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(rptCntDefId));
			rs = statement.executeQuery();
			while (rs.next()) {
				reportContentDefBean = new ReportContentDefinitionBean();
				reportContentDefBean.setReportContentDefId(rs.getInt("ID_DPB_RPT_CTNT"));
				reportContentDefBean.setReportContentName(rs.getString("NAM_DPB_RPT_CTNT"));
				reportContentDefBean.setReportContentDescription(rs.getString("DES_DPB_RPT_CTNT"));
				String data = new String(rs.getBytes("TXT_DPB_RPT_CTNT"));
				reportContentDefBean.setReportContent(data);
				reportContentDefBean.setQryRsltLpp(rs.getInt("CNT_DPB_RPT_QRY_RSLT_LPP"));
				reportContentDefBean.setDefStatusCode(rs.getString("CDE_DPB_STS"));
			}
		}
	}catch(SQLException e){
		LOGGER.error("DPB.RPT.CNT.007", e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		//throw new RuntimeException();
	}catch(NamingException ne){
		LOGGER.error("DPB.RPT.CNT.008", ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}finally {
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
    	  LOGGER.error("DPB.RPT.CNT.009", e);
            DPBExceptionHandler.getInstance().handleDatabaseException(e);
      }
}
	LOGGER.exit(CLASSNAME, methodName);
	return reportContentDefBean;
}

public void  saveReportQueryDef(ReportQuery reportQuery)
{
	LOGGER.enter(CLASSNAME, " saveReportQueryDef()");
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int idRtlDte=0;
	String query = "insert into DPB_RPT_QRY(TXT_DPB_RPT_QRY,CDE_DPB_STS,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA,NAM_DPB_RPT_QRY,DES_DPB_RPT_QRY) VALUES(?,?,?,?,CURRENT TIMESTAMP,CURRENT TIMESTAMP,?,?)";                       
	try {
		con = conFactory.getConnection();                                                          
		if (con != null) {
			ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);


			ps.setBytes(1,reportQuery.getQuery().getBytes());
			ps.setString(2, reportQuery.getStatus());
			ps.setString(3,reportQuery.getUser() );
			ps.setString(4, reportQuery.getUser());

			ps.setString(5, reportQuery.getReportQueryName());
			ps.setString(6, reportQuery.getDescription());

			int t = ps.executeUpdate();
			rs = ps.getGeneratedKeys();                                                      
			if(rs != null && rs.next()){             
				idRtlDte = rs.getInt(1);
				reportQuery.setReportQueryId(rs.getInt(1));
			}
		}
	}catch(SQLException  e){
		LOGGER.error("DPB.DEF.SRQ.001",e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
		LOGGER.error("DPB.DEF.SRQ.002",ne);
		DPBExceptionHandler.getInstance().handleException(ne);
	}              finally {
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
			LOGGER.error("DPB.DEF.SRQ.003",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, " saveReportQueryDef()");
}
/*public ReportQuery getReportQueryID(){
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                ReportQuery reportQuery = null;             
                try {        
                                con  = ConnectionFactory.getConnection();
                                reportQuery=new ReportQuery();
                                if (con != null) {
                                                ps = con.prepareStatement("select max(ID_RPT_QRY) as ID_RPT_QRY  from RPT_QRY");
                                                rs = ps.executeQuery();
                                                while(rs.next()){                                                              
                                                                reportQuery.setReportQueryId(rs.getInt("ID_RPT_QRY"));
                                                }
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
                return reportQuery;

}*/
public void updateReportQueryDef (ReportQuery reportQuery) 
{
	LOGGER.enter(CLASSNAME, " updateReportQueryDef()");
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int idRtlDte=0;
	String query="";
	if(reportQuery.getStatus().equals("I"))
	{
		 query = "update DPB_RPT_QRY set TXT_DPB_RPT_QRY=? ,CDE_DPB_STS=?,ID_LAST_UPDT_USER=?,DTS_LAST_UPDT =CURRENT TIMESTAMP + 1 DAY,NAM_DPB_RPT_QRY=?,DES_DPB_RPT_QRY=?  where ID_DPB_RPT_QRY=?";
	}
	else
	{
		query = "update DPB_RPT_QRY set TXT_DPB_RPT_QRY=? ,CDE_DPB_STS=?,ID_LAST_UPDT_USER=?,DTS_LAST_UPDT =CURRENT TIMESTAMP,NAM_DPB_RPT_QRY=?,DES_DPB_RPT_QRY=?  where ID_DPB_RPT_QRY=?";
	}
	try {
		con =conFactory.getConnection();                                                           
		if (con != null) {
			ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);


			ps.setBytes(1,reportQuery.getQuery().getBytes());
			ps.setString(2, reportQuery.getStatus());
			ps.setString(3, reportQuery.getUser());
			//ps.setDate(4,new java.sql.Date(new java.util.Date().getTime()));
			ps.setString(4, reportQuery.getReportQueryName());
			ps.setString(5, reportQuery.getDescription());
			ps.setInt(6, reportQuery.getReportQueryId());

			int t = ps.executeUpdate();
			rs = ps.getGeneratedKeys();                                                      
			if(rs != null && rs.next()){             
				idRtlDte = rs.getInt(1);
				reportQuery.setReportQueryId(rs.getInt(1));
			}
		}
	}catch(SQLException  e){
		LOGGER.error("DPB.DEF.URQ.001",e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
		LOGGER.error("DPB.DEF.URQ.002",ne);
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
			LOGGER.error("DPB.DEF.URQ.003",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}


	LOGGER.exit(CLASSNAME, " updateReportQueryDef()");
}
public List<ReportQuery> getReportQueryList()
{
	LOGGER.enter(CLASSNAME, " getReportQueryList()");
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<ReportQuery> reportQueryList=null;
	ReportQuery reportQuery=null;
	java.util.Date sDate=new java.util.Date();
	String conDate=null;


	try {

		con  =conFactory.getConnection();
		reportQueryList=new ArrayList<ReportQuery>();
		//String query = IDefinitionQueryConstants.rtlMonthStartDateQuery;
		if (con != null) {
			preparedStatement = con.prepareStatement("select ID_DPB_RPT_QRY,NAM_DPB_RPT_QRY,CDE_DPB_STS,ID_CREA_USER,ID_LAST_UPDT_USER from DPB_RPT_QRY");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){

				reportQuery = new ReportQuery();

				reportQuery.setReportQueryId(resultSet.getInt("ID_DPB_RPT_QRY"));
				reportQuery.setReportQueryName(resultSet.getString("NAM_DPB_RPT_QRY")!=null ? DateCalenderUtil.makeNonNullString(resultSet.getString("NAM_DPB_RPT_QRY")).trim():null);
				reportQuery.setCreationUser(resultSet.getString("ID_CREA_USER"));
				reportQuery.setStatus(resultSet.getString("CDE_DPB_STS")!=null ? DateCalenderUtil.makeNonNullString(resultSet.getString("CDE_DPB_STS")).trim():null);
				reportQuery.setLastUser(resultSet.getString("ID_LAST_UPDT_USER"));
				reportQueryList.add(reportQuery);
			}


		}
	}
	catch(SQLException  e){
		LOGGER.error("DPB.DEF.GRQ.001",e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
		LOGGER.error("DPB.DEF.GRQ.002",ne);
		DPBExceptionHandler.getInstance().handleException(ne);
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
			LOGGER.error("DPB.DEF.GRQ.003",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, " getReportQueryList()");
	return reportQueryList;
}

public ReportQuery getReportQueryEdit(String rId)
{
	LOGGER.enter(CLASSNAME, " getReportQueryEdit()");
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	ReportQuery reportQuery=null;
	java.util.Date sDate=new java.util.Date();
	String conDate=null;
	int id=Integer.parseInt(rId);


	try {

		con  = conFactory.getConnection();

		//String query = IDefinitionQueryConstants.rtlMonthStartDateQuery;
		if (con != null) {

			preparedStatement = con.prepareStatement("select ID_DPB_RPT_QRY,NAM_DPB_RPT_QRY,DES_DPB_RPT_QRY,TXT_DPB_RPT_QRY,CDE_DPB_STS from DPB_RPT_QRY where ID_DPB_RPT_QRY = ?");
			preparedStatement.setInt(1,id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){

				reportQuery = new ReportQuery();

				reportQuery.setReportQueryId(resultSet.getInt("ID_DPB_RPT_QRY"));
				reportQuery.setReportQueryName(resultSet.getString("NAM_DPB_RPT_QRY")!=null ? DateCalenderUtil.makeNonNullString(resultSet.getString("NAM_DPB_RPT_QRY")).trim():null);
				reportQuery.setDescription(resultSet.getString("DES_DPB_RPT_QRY")!=null ?DateCalenderUtil.makeNonNullString(resultSet.getString("DES_DPB_RPT_QRY")).trim():null);
				byte[] bdata= resultSet.getBytes("TXT_DPB_RPT_QRY");
				String data = new String(bdata);
				reportQuery.setQuery(data !=null ?data.trim():null);
				reportQuery.setStatus(DateCalenderUtil.makeNonNullString(resultSet.getString("CDE_DPB_STS")).trim());

			}

		}
	}
	catch(SQLException  e){
		LOGGER.error("DPB.DEF.GRE.001",e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch(NamingException  ne){
		LOGGER.error("DPB.DEF.GRE.002",ne);
		DPBExceptionHandler.getInstance().handleException(ne);
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
			LOGGER.error("DPB.DEF.GRE.003",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, " getReportQueryEdit()");
	return reportQuery;
}
public String getReportDefStatus(ReportQuery reportQuery)
{
	
	LOGGER.enter(CLASSNAME, " getReportDefStatus()");
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	int id=reportQuery.getReportQueryId();
	String status="";
	
	try {
	
		con  = conFactory.getConnection();
		
		//String query = IDefinitionQueryConstants.rtlMonthStartDateQuery;
		if (con != null) {
			
			preparedStatement = con.prepareStatement("select CDE_DPB_STS from  DPB_RPT where  ID_DPB_RPT in (select ID_DPB_RPT from DPB_RPT_QRY_CTNT_REL where ID_DPB_RPT_QRY=?)");
			preparedStatement.setInt(1,id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
			
				status=resultSet.getString("CDE_DPB_STS");
			
			}
			
		}
	}
	catch(SQLException  e){
		LOGGER.error("DPB.DEF.GRE.STAT.001",e);
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}catch(NamingException  ne){
		LOGGER.error("DPB.DEF.GRE.STAT.002",ne);
		DPBExceptionHandler.getInstance().handleException(ne);
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
			LOGGER.error("DPB.DEF.GRE.STAT.003",e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
}
	LOGGER.exit(CLASSNAME, " getReportDefStatus()");

	
	return status;
}
@Override
public FileDefinitionBean fetchFileDefinition(int processID) {
	final String methodName = "fetchFileDefinition";
	LOGGER.enter(CLASSNAME, methodName);

	FileDefinitionBean definitionBean = null;
	FileFormatBean formatBean = null;
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
		con = conFactory.getConnection();
		String query = "SELECT FILE.ID_DPB_FILE, FILE.TXT_DPB_FMT_FILE_NAM,FMT.ID_DPB_FILE_FMT, FMT.NAM_DPB_FIL_FMT, " +
				"FMT.IND_INBND_FILE,FMT.IND_HDR,FMT.TXT_DLM,FMT.CNT_COL , FILE.NAM_DPB_FIL, FILE.TXT_DPB_BASE_DPTH  ," +
						" PROC.DTE_DPB_ACTL_PROC AS ACTUAL_DATE_ID "+
						" FROM DPB_PROC PROC, "+
						" DPB_FILE FILE, dpb_FILE_FMT FMT WHERE PROC.ID_DPB_PROC = ?  AND PROC.CDE_DPB_PROC_TYP = 'PF' "+
						" AND FILE.ID_DPB_FILE = PROC.ID_DPB_FILE AND FILE.CDE_DPB_STS = 'A' AND "+ 
				"FMT.ID_DPB_FILE_FMT = FILE.ID_DPB_FILE_FMT WITH UR";
		ps = con.prepareStatement(query);
		ps.setInt(1, processID);
		rs = ps.executeQuery();
		definitionBean = new FileDefinitionBean();
		formatBean = new FileFormatBean();
		while (rs.next()) {
			definitionBean.setFileDefName(rs.getString("NAM_DPB_FIL"));
			definitionBean.setFileNameFormat(rs.getString("TXT_DPB_FMT_FILE_NAM"));
			definitionBean.setBaseFolder(rs.getString("TXT_DPB_BASE_DPTH"));
			formatBean.setFormatName(rs.getString("NAM_DPB_FIL_FMT"));
			formatBean.setFileFormatId(rs.getInt("ID_DPB_FILE_FMT"));
			formatBean.setInbountFileIndicator(rs.getString("IND_INBND_FILE"));
			formatBean.setIndHeader(rs.getString("IND_HDR"));
			formatBean.setIndDelimited(rs.getString("TXT_DLM"));
			formatBean.setColumnCount(rs.getInt("CNT_COL"));
			//definitionBean.setActualProcessDate(rs.getString("ACTUAL_DATE"));
			definitionBean.setActualDate(rs.getDate("ACTUAL_DATE_ID"));
			// formatBean.setFileMapingList(getFileMappingDetails(conn,
			// formatBean.getFileFormatId()));
			definitionBean.setFileFormats(formatBean);
		}
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
}
	LOGGER.exit(CLASSNAME, methodName);
	return definitionBean;
}
@Override
public void insertIntoProcessLog(FileProcessLogMessages message) {
	final String methodName = "insertIntoProcessLog";
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
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
@Override
public void deleteVistaProcessedData(int processId) {
	final String methodName = "deleteVistaProcessedData";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	try {
		con = conFactory.getConnection();
		String query= IDefinitionQueryConstants.DELETE_VISTA_DATA;
		ps=con.prepareStatement(query);
		ps.setInt(1,processId);
		ps.execute();
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
@Override
public void deleteBlockedData(int processId) {
	final String methodName = "deleteBlockedData";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	try {
		con = conFactory.getConnection();
		String query= IDefinitionQueryConstants.DELETE_BLOCKED_DATA;
		ps=con.prepareStatement(query);
		ps.setInt(1,processId);
		ps.execute();
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
@Override
public void updateBonusProcesses(java.sql.Date actualDate,  String processType, String message, String userId) {
	final String methodName = "updateBonusProcesses";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	List processIdList = null;
	boolean isLdrShipFlag = false;
	try {
		con = conFactory.getConnection();
		String query= IDefinitionQueryConstants.UPDATE_BONUS_PROCESSES;
		ps=con.prepareStatement(query);
		ps.setDate(1,actualDate);
		rs=  ps.executeQuery();
		processIdList = new ArrayList();
		while(rs.next() && rs!=null){
			processIdList.add(rs.getInt("ID_DPB_PROC"));
		}
		processIdList = getSuccessOrFailedBonusProcesses(processIdList);
		if(!processIdList.isEmpty() && processIdList!= null){
			insertBonusProcessLogStatus(processIdList, processType, message, userId);
		}
		updateExistingBonusCalculatedRecords(processIdList, userId, isLdrShipFlag,actualDate );
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
private void updateExistingBonusCalculatedRecords(List processIdList,String userId, boolean isLdrShpBonus, java.sql.Date actualDate) {
	final String methodName = "updateExistingBonusCalculatedRecords";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	String query = "";
	int count = 0;
	int batchSize = 0;
	try {
			if(isLdrShpBonus){ 
			query = IDefinitionQueryConstants.UPDATE_LDRSHP_BONUS;
			} else{
			query = IDefinitionQueryConstants.UPDATE_BONUS_CALC;
			}
			con = conFactory.getConnection();
			ps = con.prepareStatement(query);
			batchSize = processIdList.size();
			for(int i=0; i< processIdList.size() ;i++){
				ps.setString(1, userId);
				ps.setInt(2, (Integer) processIdList.get(i));
				ps.setDate(3, actualDate);
				ps.addBatch();
				count++;
			}
			if(count == batchSize){
			ps.executeBatch();
			}
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
private void insertBonusProcessLogStatus(List processIdList, String processType, String message,  String userId) {
	final String methodName = "insertBonusProcessLogStatus";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	String query = "";
	int count = 0;
	int batchSize = 0;
	int processId = 0;
	try {
			query = IDefinitionQueryConstants.INSERT_BONUS_PROCESS_LOG;
			con = conFactory.getConnection();
			ps = con.prepareStatement(query);
			batchSize = processIdList.size();
			for(int i=0; i< processIdList.size() ;i++){
				processId = (Integer) processIdList.get(i);
				ps.setInt(1, processId);
				ps.setString(2, processType);
				ps.setString(3, message);
				ps.setString(4, userId);
				ps.setString(5, userId);
				ps.addBatch();
				count++;
			}
			if(count == batchSize){
			ps.executeBatch();
			}
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getNextException()); ;
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
@Override
public void deleteKpiData(int processId) {

	final String methodName = "deleteKpiData";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	try {
		con = conFactory.getConnection();
		String query= IDefinitionQueryConstants.DELETE_KPI_DATA;
		ps=con.prepareStatement(query);
		ps.setInt(1,processId);
		ps.execute();
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
@Override
public void deleteAccrualData(int processId) {

	final String methodName = "deleteAccrualData";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	try {
		con = conFactory.getConnection();
		String query= IDefinitionQueryConstants.DELETE_ACCRUAL_DATA;
		ps=con.prepareStatement(query);
		ps.setInt(1,processId);
		ps.execute();
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
@Override
public void updateAccrualBonusProcesses(java.sql.Date actualDate, String message, String processType,String userId) {
	final String methodName = "updateAccrualBonusProcesses";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	List processIdList = null;
	try {
		con = conFactory.getConnection();
		String query= IDefinitionQueryConstants.UPDATE_ACCRUAL_BONUS_PROCESS;
		ps=con.prepareStatement(query);
		ps.setDate(1,actualDate);
		rs=  ps.executeQuery();
		processIdList = new ArrayList();
		while(rs.next() && rs!=null){
			int processId = rs.getInt("ID_DPB_PROC");
			processIdList.add(processId);
		}
		if(!processIdList.isEmpty() && processIdList!= null){
			insertBonusProcessLogStatus(processIdList, processType,  message,  userId);
		}
		//updateExistingBonusCalculatedRecords(processIdList, userId, isLdrShpBonus, actualDate);
	//	deleteExistingBonusCalculatedRecords(processIdList, idDayList, userId, ldrShpBonusProcess, actualDate)
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
@Override
public void performBonusReprocess(int processId, String userId, String processType, boolean ldrShpBonusProcess){

	final String methodName = "performBonusReprocess";
	LOGGER.enter(CLASSNAME, methodName);
	String message = IConstants.EMPTY_STR;
	List procList = new ArrayList();
	java.sql.Date actualDate = null;
	List processIdList = new ArrayList();
	message = "Re-Processing Bonus for Process Id " + "" + processId ;
	processIdList.add(processId);
	insertBonusProcessLogStatus(processIdList, processType, message, userId);
	actualDate = getDayIDforBonusProcess(processId, ldrShpBonusProcess);
	updateExistingBonusCalculatedRecords(processIdList, userId, ldrShpBonusProcess, actualDate);
	LOGGER.exit(CLASSNAME, methodName);
}

private List getDayIDforProcess(int processId, boolean isLdrShpBonus, List idDayList,boolean isKpi) {
	final String methodName = "getDayIDforProcess";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	java.sql.Date actualDate = null;
	int procIds = 0;
	List procList = new ArrayList();
	String query = IConstants.EMPTY_STR;
	try {
		con = conFactory.getConnection();
		if(isLdrShpBonus){
			query= IDefinitionQueryConstants.FETCH_LDRSHP_BONUS_ACTUAL_DAY;
		}else{
			if(isKpi){
				query= IDefinitionQueryConstants.FETCH_BONUS_PROCESS_FOR_KPI;	
			}else{
			query= IDefinitionQueryConstants.FETCH_BONUS_PROCESS_ACTUAL_DAY;
			}
		}
		ps=con.prepareStatement(query);
		ps.setInt(1,processId);
		rs=  ps.executeQuery();
		while(rs.next() && rs!=null){
			actualDate = rs.getDate("DTE_DPB_ACTL_PROC");
			procIds = rs.getInt("ID_DPB_PROC");
			procList.add(procIds);
			idDayList.add(actualDate);
		}
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return procList;
}
@Override
public void performBonusReset(int processId, String userId, String processType,boolean ldrShpBonusProcess, boolean isBonus, boolean isKpi) {
	final String methodName = "performBonusReset";
	LOGGER.enter(CLASSNAME, methodName);
	String message = IConstants.EMPTY_STR;
	List procList = new ArrayList();
	List idDayList = new ArrayList();
	java.sql.Date bonusIdDay = null;
	List processIdList = new ArrayList();
	if(IConstants.PROCESS_RESET.equalsIgnoreCase(processType)){
		message = "Process RESET for Process Id " + "" + processId ;
	}else{
		message = "Process REPROCESS for Process Id " + "" + processId ;
	}
	processIdList.add(processId);
	insertBonusProcessLogStatus(processIdList, processType, message,  userId);
	if(isBonus){
		bonusIdDay = getDayIDforBonusProcess(processId, ldrShpBonusProcess);
		idDayList.add(bonusIdDay);
		procList.add(processId);
	}else {
	procList = getDayIDforProcess(processId, ldrShpBonusProcess, idDayList, isKpi);
		if(procList.size() >0){
		procList = getSuccessOrFailedBonusProcesses(procList);
		}
	}
	if(procList.size() >0){
	deleteExistingBonusCalculatedRecords(procList, idDayList, userId, ldrShpBonusProcess, bonusIdDay);
	insertBonusProcessLogStatus(procList, processType, message,  userId);
	}
	LOGGER.exit(CLASSNAME, methodName);
}

private List getSuccessOrFailedBonusProcesses(List procList) {
	final String methodName = "getSuccessOrFailedBonusProcesses";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int size = 0;
	int procIds = 0;
	List procListNew = new ArrayList();
	String query = IConstants.EMPTY_STR;
	try {
		con = conFactory.getConnection();
		
			query= IDefinitionQueryConstants.GET_BONUS_PROCESSES_FOR_TODAY;
		ps=con.prepareStatement(query);
		
		size = procList.size();
		for(int i=0; i< size; i++){
		ps.setInt(1, (Integer)procList.get(i));
		rs = ps.executeQuery();
		while(rs.next() && rs!=null){
			procIds = rs.getInt("ID_DPB_PROC");
			if(!procListNew.contains(rs.getInt("ID_DPB_PROC"))){
			procListNew.add(procIds);
		}
		}
		}
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return procListNew;
}
private void deleteExistingBonusCalculatedRecords(List processIdList, List idDayList, String userId, boolean ldrShpBonusProcess, java.sql.Date actualDate) {
	final String methodName = "deleteExistingBonusCalculatedRecords";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	String query = IConstants.EMPTY_STR;
	int count = 0;
	int batchSize = 0;
	int processId = 0;
	try {
				batchSize = processIdList.size();
			if(ldrShpBonusProcess){ 
			query = IDefinitionQueryConstants.DELETE_LDRSHP_BONUS_RECORDS;
			} else{
			query = IDefinitionQueryConstants.DELETE_BONUS_RECORDS;
			}
			con = conFactory.getConnection();
			ps = con.prepareStatement(query);
			for(int i=0; i< batchSize  ;i++){
				processId = (Integer) processIdList.get(i);
				actualDate = (java.sql.Date) idDayList.get(0);
				ps.setInt(1, processId);
				ps.setDate(2, actualDate);
				ps.addBatch();
				count++;
			}
			if(count == batchSize){
				ps.executeBatch();
			}
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
}
private java.sql.Date getDayIDforBonusProcess(int processId, boolean isLdrShpBonus) {
	final String methodName = "getDayIDforBonusProcess";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	java.sql.Date actualId = null;
	String query = IConstants.EMPTY_STR;
	try {
		con = conFactory.getConnection();
		query= IDefinitionQueryConstants.FETCH_ACTUAL_DAY;
		
		ps=con.prepareStatement(query);
		ps.setInt(1,processId);
		rs=  ps.executeQuery();
		if(rs.next() && rs!=null){
			actualId = rs.getDate("DTE_DPB_ACTL_PROC");
		}
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return actualId;
}
@Override
public boolean validateUniqueCondition(ConditionDefinition conditionDef) {
	final String methodName = "validateUniqueCondition";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean validCondition = false;
	String query = IConstants.EMPTY_STR;
	try {
		con = conFactory.getConnection();
		query= IDefinitionQueryConstants.VALIDATE_UNIQUE_CONDITION;
		
		ps=con.prepareStatement(query);
		ps.setString(1, conditionDef.getCondition());
		ps.setString(2, conditionDef.getVariableName());
		ps.setString(3, conditionDef.getCheckValue());
		ps.setString(4, conditionDef.getConditionType());
		ps.setString(5, conditionDef.getRegularExp());
		
		rs=  ps.executeQuery();
		if(rs.next() && rs!=null){
			validCondition = true;
		}
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
return validCondition;
}
@Override
public boolean validateProgram(int programId, String programName, java.sql.Date startDate) {
	final String methodName = "validateProgram";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean validProgram = false;
	String query = IConstants.EMPTY_STR;
	try {
		con = conFactory.getConnection();
		query= IDefinitionQueryConstants.VALIDATE_PROGRAM;
		
		ps=con.prepareStatement(query);
		ps.setString(1, programName);
		ps.setDate(2, startDate);
		ps.setInt(3, programId);
		rs=  ps.executeQuery();
		if(rs.next() && rs!=null){
			validProgram = true;
		}
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
return validProgram;
}
@Override
public boolean checkPaymentProcesses(int processId) {
	final String methodName = "checkPaymentProcesses";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isPaymentSucccess = false;
	String query = IConstants.EMPTY_STR;
	try {
		con = conFactory.getConnection();
		query= IDefinitionQueryConstants.GET_PAYMENT_PROCESSES_FOR_TODAY;
		
		ps=con.prepareStatement(query);
		ps.setInt(1, processId);
		rs=  ps.executeQuery();
		while(rs.next() && rs!=null){
		String status =rs.getString("CDE_DPB_PROC_STS");
		if(IConstants.SUCCESS.equalsIgnoreCase(DateCalenderUtil.makeNonNullString(status).trim())){
			isPaymentSucccess = true;
			break;
		}
		}
	} catch (SQLException e) {
		LOGGER.error(CLASSNAME + methodName+ "SQL Exception occured"+ e.getMessage());
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		LOGGER.error(CLASSNAME + methodName+ "Naming Exception occured"+ ne.getMessage());
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
			LOGGER.error(CLASSNAME + methodName+ "Exception occured"+ e.getMessage());
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}
	LOGGER.exit(CLASSNAME, methodName);
	return isPaymentSucccess;
}

@Override
public List<ReScheduleProcess> getProcessesForFileReschedule(FileProcessDefBean fileProcessDefBean, int fileType) {
	final String methodName = "getProcessesForFileReschedule";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	List<ReScheduleProcess> processIdList = new ArrayList<ReScheduleProcess>();
	ReScheduleProcess reSchedule = null;
	try {
		con = conFactory.getConnection();
		String query = "";
		if(fileType == 1){
		query=IDefinitionQueryConstants.FETCH_VISTA_FILE_PROCESSES_FOR_RESCHEDULE;
		}else if ( fileType == 2){
			query=IDefinitionQueryConstants.FETCH_ACCRUAL_FILE_PROCESSES_FOR_RESCHEDULE;
		}else if (fileType == 3){
			query=IDefinitionQueryConstants.FETCH_KPI_FILE_PROCESSES_FOR_RESCHEDULE;	
		}
		ps = con.prepareStatement(query);
		ps.setInt(1 , fileProcessDefBean.getDefinitionId());
		rs = ps.executeQuery();
		while(rs.next() && rs!=null){
			reSchedule = new ReScheduleProcess();
			reSchedule.setProcessTypeCode(rs.getString("CDE_DPB_PROC_TYP"));
			reSchedule.setProgramId(rs.getInt("ID_DPB_PGM"));
			reSchedule.setFileId(rs.getInt("ID_DPB_FILE"));
			reSchedule.setReportId(rs.getInt("ID_DPB_RPT"));
			reSchedule.setLeadershipId(rs.getInt("ID_LDRSP_BNS_PGM"));
			reSchedule.setProcessId(rs.getInt("ID_DPB_PROC"));
			reSchedule.setDateCal(rs.getDate("DTE_CAL"));
			reSchedule.setActualDate(rs.getDate("DTE_DPB_ACTL_PROC"));
			processIdList.add(reSchedule);
		}
		/*if(processIdList != null && !processIdList.isEmpty()){
			updateProcessDefinition(fileProcessDefBean, processIdList);
			insertProcessLogList(processIdList,fileProcessDefBean );
		}*/
	} catch (SQLException e) {
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		DPBExceptionHandler.getInstance().handleException(ne);
	}catch (Exception e) {
		e.printStackTrace();
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
	return processIdList;
}

public void updateProcessDefinition(FileProcessDefBean fileProcessDefBean,List<ReScheduleProcess> processIdList) {
	final String methodName = "updateProcessDefinition";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String query = "";
	try {
		con = conFactory.getConnection();
		java.sql.Date dateCal = DateCalenderUtil.convertStringToDate(fileProcessDefBean.getProcessDateString());
			for (ReScheduleProcess reSchedule : processIdList) {
				if (con != null) {
				if(reSchedule.getFileId() == 0 && reSchedule.getProgramId() == 0 &&  reSchedule.getLeadershipId() == 0)
				{
					
					query=IDefinitionQueryConstants.insertReportProcessDefQuery;
					ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
					ps.setDate(1,dateCal);
					ps.setInt(2,reSchedule.getReportId());
					ps.setDate(3,reSchedule.getActualDate());
					ps.setString(4,reSchedule.getProcessTypeCode());
					ps.setTime(5, fileProcessDefBean.getProcessingTime());
					ps.setString(6, fileProcessDefBean.getProcessingTrigger());
					ps.setString(7, fileProcessDefBean.getReasonForUpdate());
					ps.setString(8,fileProcessDefBean.getUser());
					ps.setString(9,fileProcessDefBean.getUser());
					ps.addBatch();
				}
				if (reSchedule.getFileId() == 0 && reSchedule.getReportId() == 0 &&  reSchedule.getLeadershipId() == 0)
					{
						query=IDefinitionQueryConstants.insertPgmProcessDefQuery;
						ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
					 	ps.setDate(1,dateCal);
						ps.setInt(2,reSchedule.getProgramId());
						ps.setDate(3,reSchedule.getActualDate());
						ps.setString(4,fileProcessDefBean.getDefinitionType());
						ps.setTime(5, fileProcessDefBean.getProcessingTime());
						ps.setString(6, fileProcessDefBean.getProcessingTrigger());
						ps.setString(7, fileProcessDefBean.getReasonForUpdate());
						ps.setString(8,fileProcessDefBean.getUser());
						ps.setString(9,fileProcessDefBean.getUser());
						ps.addBatch();
					}
				 if (reSchedule.getProgramId() == 0 && reSchedule.getReportId() == 0 &&  reSchedule.getLeadershipId() == 0)
				{
					
					query=IDefinitionQueryConstants.insertFileProcessDefQuery;
					ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				 	ps.setDate(1,dateCal);
					ps.setInt(2,reSchedule.getFileId());
					ps.setDate(3,reSchedule.getActualDate());
					ps.setString(4,fileProcessDefBean.getDefinitionType());
					ps.setTime(5, fileProcessDefBean.getProcessingTime());
					ps.setString(6, fileProcessDefBean.getProcessingTrigger());
					ps.setString(7, fileProcessDefBean.getReasonForUpdate());
					ps.setString(8,fileProcessDefBean.getUser());
					ps.setString(9,fileProcessDefBean.getUser());
					ps.addBatch();
				}
				
				 if (reSchedule.getFileId() == 0 && reSchedule.getReportId() == 0 &&  reSchedule.getProgramId() == 0)
					{
					 	query=IDefinitionQueryConstants.insertLdrPgmProcessDefQuery;
					 	ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
					 	ps.setDate(1,dateCal);
						ps.setInt(2,reSchedule.getLeadershipId());
						ps.setDate(3,reSchedule.getActualDate());
						ps.setString(4,fileProcessDefBean.getDefinitionType());
						ps.setTime(5, fileProcessDefBean.getProcessingTime());
						ps.setString(6, fileProcessDefBean.getProcessingTrigger());
						ps.setString(7, fileProcessDefBean.getReasonForUpdate());
						ps.setString(8,fileProcessDefBean.getUser());
						ps.setString(9,fileProcessDefBean.getUser());
						ps.addBatch();
					}
			}
			
		}
			ps.executeBatch();
	} catch (SQLException e) {
		e.getNextException();
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		DPBExceptionHandler.getInstance().handleException(ne);
	}catch (Exception e) {
		e.printStackTrace();
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
}

public List<ReScheduleProcess> getProcessesForBonusReschedule(FileProcessDefBean fileProcessDefBean) {
	final String methodName = "getProcessesForBonusReschedule";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	List<ReScheduleProcess> processIdList = new ArrayList<ReScheduleProcess>();
	ReScheduleProcess reSchedule = new ReScheduleProcess();
	try {
		con = conFactory.getConnection();
		String query = "";
		if (con != null) {
					
					query=IDefinitionQueryConstants.FETCH_BONUS_PROCESSES_FOR_RESCHEDULE;
					ps = con.prepareStatement(query);
					ps.setInt(1 , fileProcessDefBean.getDefinitionId());
					rs = ps.executeQuery();
					while(rs.next()){
						reSchedule.setProcessTypeCode(rs.getString("CDE_DPB_PROC_TYP"));
						reSchedule.setProgramId(rs.getInt("ID_DPB_PGM"));
						reSchedule.setFileId(rs.getInt("ID_DPB_FILE"));
						reSchedule.setReportId(rs.getInt("ID_DPB_RPT"));
						reSchedule.setLeadershipId(rs.getInt("ID_LDRSP_BNS_PGM"));
						reSchedule.setProcessId(rs.getInt("ID_DPB_PROC"));
						reSchedule.setDateCal(rs.getDate("DTE_CAL"));
						reSchedule.setActualDate(rs.getDate("DTE_DPB_ACTL_PROC"));
						processIdList.add(reSchedule);
					}
					
					/*if(processIdList != null && !processIdList.isEmpty()){
						insertProcessLogList(processIdList,fileProcessDefBean );
						insertReScheduleProcessLogList(processIdList, fileProcessDefBean);
						//insertProcProcessCal();
					}*/
		}	
	} catch (SQLException e) {
		DPBExceptionHandler.getInstance().handleDatabaseException(e);
	} catch (NamingException ne) {
		DPBExceptionHandler.getInstance().handleException(ne);
	}catch (Exception e) {
		e.printStackTrace();
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
	return processIdList;
}
@Override
public void insertReportProcessForReSchedule(FileProcessDefBean fileProcessDefBean, List<ReScheduleProcess> processIdList) {
	final String methodName = "insertReportProcessForReSchedule";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String query = "";
	try {
		con = conFactory.getConnection();
		query=IDefinitionQueryConstants.insertReportProcessDefQuery;
		ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		java.sql.Date dateCal = DateCalenderUtil.convertStringToDate(fileProcessDefBean.getProcessDateString());
			for (ReScheduleProcess reSchedule : processIdList) {
				if (con != null) {
				if(reSchedule.getReportId() > 0)
				{				
					ps.setDate(1,dateCal);
					ps.setInt(2,reSchedule.getReportId());
					ps.setDate(3,reSchedule.getActualDate());
					ps.setString(4,reSchedule.getProcessTypeCode());
					ps.setTime(5, fileProcessDefBean.getProcessingTime());
					ps.setString(6, fileProcessDefBean.getProcessingTrigger());
					ps.setString(7, fileProcessDefBean.getReasonForUpdate());
					ps.setString(8,fileProcessDefBean.getUser());
					ps.setString(9,fileProcessDefBean.getUser());
					ps.addBatch();
				}
			}
			
		}
			ps.executeBatch();
	}
		catch (SQLException e) {
			e.getNextException();
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
}
@Override
public void insertBonusProcessForReSchedule(FileProcessDefBean fileProcessDefBean,List<ReScheduleProcess> processIdList) {
	final String methodName = "insertBonusProcessForReSchedule";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String query = "";
	try {
		con = conFactory.getConnection();
		query=IDefinitionQueryConstants.insertPgmProcessDefQuery;
		ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		java.sql.Date dateCal = DateCalenderUtil.convertStringToDate(fileProcessDefBean.getProcessDateString());
			for (ReScheduleProcess reSchedule : processIdList) {
				if (con != null) {
				if(reSchedule.getProgramId() > 0)
				{				
					ps.setDate(1,dateCal);
					ps.setInt(2,reSchedule.getProgramId());
					ps.setDate(3,reSchedule.getActualDate());
					ps.setString(4,reSchedule.getProcessTypeCode());
					ps.setTime(5, fileProcessDefBean.getProcessingTime());
					ps.setString(6, fileProcessDefBean.getProcessingTrigger());
					ps.setString(7, fileProcessDefBean.getReasonForUpdate());
					ps.setString(8,fileProcessDefBean.getUser());
					ps.setString(9,fileProcessDefBean.getUser());
					ps.addBatch();
				}
			}
		}
			ps.executeBatch();
	}
		catch (SQLException e) {
			e.getNextException();
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
}
@Override
public void insertLeaderShipBonusProcessForReSchedule(FileProcessDefBean fileProcessDefBean,List<ReScheduleProcess> processIdList) {
	final String methodName = "insertLeaderShipBonusProcessForReSchedule";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String query = "";
	try {
		con = conFactory.getConnection();
		query=IDefinitionQueryConstants.insertLdrPgmProcessDefQuery;
		ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		java.sql.Date dateCal = DateCalenderUtil.convertStringToDate(fileProcessDefBean.getProcessDateString());
			for (ReScheduleProcess reSchedule : processIdList) {
				if (con != null) {
				if(reSchedule.getLeadershipId() > 0)
				{				
					ps.setDate(1,dateCal);
					ps.setInt(2,reSchedule.getLeadershipId());
					ps.setDate(3,reSchedule.getActualDate());
					ps.setString(4,reSchedule.getProcessTypeCode());
					ps.setTime(5, fileProcessDefBean.getProcessingTime());
					ps.setString(6, fileProcessDefBean.getProcessingTrigger());
					ps.setString(7, fileProcessDefBean.getReasonForUpdate());
					ps.setString(8,fileProcessDefBean.getUser());
					ps.setString(9,fileProcessDefBean.getUser());
					ps.addBatch();
				}
			}
		}
	ps.executeBatch();
	}
		catch (SQLException e) {
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
	
}
@Override
public void insertReScheduleProcessLogList(List<ReScheduleProcess> processIdList,FileProcessDefBean fileProcessDefBean) {
	final String methodName = "insertReScheduleProcessLogList";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String query="";
	try {
		con = conFactory.getConnection();
		if (con != null) 
		{
			query=IDefinitionQueryConstants.insertFileprocLogQuery;
			ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			for (ReScheduleProcess reSchedule : processIdList) {
				ps.setInt(1, reSchedule.getProcessId());
				ps.setString(2,fileProcessDefBean.getProcessType());
				ps.setString(3,fileProcessDefBean.getReasonForUpdate());
				ps.setString(4,fileProcessDefBean.getUser());
				ps.setString(5,fileProcessDefBean.getUser());
				ps.addBatch();
				
			}
		ps.executeBatch();
		}
	}
	 catch (SQLException e) {
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
}
public RetailDate getRetailData(String dbMonth, String dbYear) {
	final String methodName = "getRetailData";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String query="";
	RetailDate rtlDate = null;
	try {
		con = conFactory.getConnection();
		if (con != null) 
		{
			query=IDefinitionQueryConstants.GET_RETAIL_DATA_DETAILS;
			ps = con.prepareStatement(query);
			ps.setString(1, dbMonth);
			ps.setString(2, dbYear);
			rs = ps.executeQuery();

		}
		if(rs!=null && rs.next()){
			rtlDate = new RetailDate();
			java.sql.Date startDate= rs.getDate("DTE_RETL_MTH_BEG");
			java.sql.Date endDate = rs.getDate("DTE_RETL_MTH_END");
			rtlDate.setRtlStartDate(DateCalenderUtil.convertDateToString(startDate));
			rtlDate.setRtlEndDate(DateCalenderUtil.convertDateToString(endDate));
			rtlDate.setCurrentMonth(rs.getString("NUM_RETL_MTH"));
			rtlDate.setCurrentYear(rs.getString("NUM_RETL_YR"));			
		}
	}
	 catch (SQLException e) {
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
	return rtlDate;
}
@Override
public boolean checkValidMonth(String month, String year) {
	final String methodName = "checkValidMonth";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String query="";
	boolean isValidMonth = false;
	try {
		con = conFactory.getConnection();
		if (con != null) 
		{
			query=IDefinitionQueryConstants.CHECK_MONTHLY_PROCESSES;
			ps = con.prepareStatement(query);
			ps.setString(1, month);
			ps.setString(2, year);
			rs = ps.executeQuery();

		}
		if(rs!=null && rs.next()){
			isValidMonth = true;
		}
	}
	 catch (SQLException e) {
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
	return isValidMonth;
}
@Override
public List<Integer> getRtlMonthEndProcesses(java.sql.Date oldRtlEndDate) {
	final String methodName = "getRtlMonthEndProcesses";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String query="";
	List<Integer> processesList = new ArrayList<Integer>();
	try {
		con = conFactory.getConnection();
		if (con != null) 
		{
			query=IDefinitionQueryConstants.FETCH_MONTHLY_PROCESSES;
			ps = con.prepareStatement(query);
			ps.setDate(1, oldRtlEndDate);
			rs = ps.executeQuery();
		}
		if(rs!=null){
			while(rs!=null && rs.next()){
				processesList.add(rs.getInt("ID_DPB_PROC"));
			}
		}
	}
	 catch (SQLException e) {
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
	return processesList;
}
@Override
public void updateProcessesRtlEndDates(List<Integer> processesList,java.sql.Date newRtlProcessingDate,String uId) {
	final String methodName = "updateProcessesRtlEndDates";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String query="";
	try {
		con = conFactory.getConnection();
		if (con != null) 
		{
			query=IDefinitionQueryConstants.UPDATE_MONTHLY_PROCESSES_RTL_DATE;
			LOGGER.info("************query:"+query);
			LOGGER.info("*********processesList.size:"+processesList.size()+":newRtlProcessingDate:"+newRtlProcessingDate);
			ps = con.prepareStatement(query);
			for(int i = 0; i < processesList.size(); i++){
				ps.setDate(1, newRtlProcessingDate);
				ps.setDate(2, newRtlProcessingDate);
				ps.setString(3, uId);
				ps.setInt(4, (Integer) processesList.get(i));
				ps.addBatch();
			}
			ps.executeBatch();			
		}
	}
	 catch (SQLException e) {
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
}
@Override
public void updateDatesInDpbDay(String currentStartDte,String newEndDte,int cYear,int cMonth, int nYear,String uId) {
	final String methodName = "updateDatesInDpbDay";
	LOGGER.enter(CLASSNAME, methodName);
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	try {		
		con = conFactory.getConnection();
		if (con != null) 
		{				
			ps = con.prepareStatement(IDefinitionQueryConstants.UPDATE_DPB_DAY);
			//ps.setDate(1,DateCalenderUtil.convertStringToSQLDate(newEndDte));
			
			//ps.setDate(1,DateCalenderUtil.convertStringToSQLDate(currentStartDte));
			//ps.setDate(2,DateCalenderUtil.convertStringToSQLDate(currentStartDte));
			//ps.setDate(3,DateCalenderUtil.convertStringToSQLDate(currentStartDte));
			/*ps.setDate(4,DateCalenderUtil.convertStringToSQLDate(newEndDte));
			ps.setDate(5,DateCalenderUtil.convertStringToSQLDate(currentStartDte));
			ps.setDate(6,DateCalenderUtil.convertStringToSQLDate(currentStartDte));
			ps.setDate(7,DateCalenderUtil.convertStringToSQLDate(currentStartDte));	*/
			ps.setString(1, newEndDte);
			ps.setString(2, currentStartDte);
			ps.setString(3, currentStartDte);
			ps.setString(4, newEndDte);
			ps.setString(5, cMonth<10 ? "0"+cMonth : ""+cMonth);
			ps.setString(6, ""+cYear);
			ps.setString(7, ""+nYear);
			ps.setString(8, uId);
			int updateCount = ps.executeUpdate();			
		}
	}catch (SQLException e) {
		LOGGER.info("e.getNextException():::::", e.getNextException());
		e.printStackTrace();
		
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
	}catch (NamingException ne) {
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
}

public List<AMGDealer> getDlrsInfoList(String dealerId){
	ArrayList<AMGDealer> list=new ArrayList<AMGDealer>();
	Connection conn = null;			
	PreparedStatement ps = null;
	ResultSet rs = null;
	String stmt;
	boolean specificDealer= false;
	try {			
		conn = conFactory.getConnection();
		stmt = IDefinitionQueryConstants.RETRIEVE_AMG_DEALER_INFO;
		
		if(dealerId != null && !"".equalsIgnoreCase(dealerId)) {
			
			stmt = stmt.concat("AND  DLR.ID_DLR = ?");
			ps = conn.prepareStatement(stmt);
			ps.setString(1, dealerId);	
			specificDealer = true;			
		}	
	
		if(!specificDealer)  {
			ps = conn.prepareStatement(stmt.concat(" ORDER BY DLR.CDE_AMG_PGM,FDRT.NAM_DLR"));
		}
		rs = ps.executeQuery();
		
		while(rs.next()){
			AMGDealer dlr= new AMGDealer();
			dlr.setDealerID(rs.getString("ID_DLR"));
			dlr.setDealerName(rs.getString("NAM_DLR"));
			dlr.setDealerType(rs.getString("CDE_AMG_PGM"));
			dlr.setStartDate(rs.getDate("DTE_AMG_MBR_EFF"));
			dlr.setEndDate(rs.getDate("DTE_AMG_MBR_END"));		
			list.add(dlr);				
		}		
	}catch(SQLException  e){
		LOGGER.info("getDlrsInfoList SQL Exception :", e.getCause());
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
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}	
	return list;	
}

public Boolean modifyAMGDealerInfo(AMGDealer amgDealer){
	ArrayList<AMGDealer> list=new ArrayList<AMGDealer>();
	Connection conn = null;			
	PreparedStatement ps = null;
	ResultSet rs = null;
	Date today = new Date();
	
	Boolean modify = false;
	try {			
		conn = conFactory.getConnection();			
		ps = conn.prepareStatement(IDefinitionQueryConstants.MODIFY_AMG_DEALER_INFO);
				
		ps.setString(1, amgDealer.getDealerID());
		ps.setDate(2, new java.sql.Date(amgDealer.getStartDate().getTime()));
		ps.setDate(3, new java.sql.Date(amgDealer.getEndDate().getTime()));
		ps.setString(4, amgDealer.getDealerType());
		ps.execute();
		modify = true;
		
	}catch(SQLException  e){
		LOGGER.info("getDlrsInfoList SQL Exception :", e.getCause());
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
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}	
	return modify;	
}



public String getAMGDealerName(String dealerId){
	String dealerName = "";
	Connection conn = null;			
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {			
		conn = conFactory.getConnection();			
		ps = conn.prepareStatement(IDefinitionQueryConstants.RETRIEVE_AMG_DEALER_NAME);
		ps.setString(1, dealerId);
		rs = ps.executeQuery();
		while(rs.next()){
			
			dealerName = rs.getString("NAM_DLR");
		}		
	}catch(SQLException  e){
		LOGGER.info("getDlrsInfoList SQL Exception :", e.getCause());
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
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		}
	}	
	return dealerName;	
}




}