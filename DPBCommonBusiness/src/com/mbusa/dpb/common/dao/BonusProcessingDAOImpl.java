/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: BonusProcessingDAOImpl.java
 * Program Version			: 1.0
 * Program Description		: This class is used to To Process Bonus.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 11, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.mbusa.dpb.common.constant.IBonusProcessingQueryConstants;
import com.mbusa.dpb.common.constant.IFileProcessingQueryConstants;
import com.mbusa.dpb.common.constant.IMasterInfoLookQueryConstants;
import com.mbusa.dpb.common.db.ConnectionFactory;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.ProcessBonus;
import com.mbusa.dpb.common.domain.ProcessLdrBonus;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.DPBExceptionHandler;
import com.mbusa.dpb.common.logger.DPBLog;



public class BonusProcessingDAOImpl implements IBonusProcessingDAO {

	private ConnectionFactory conFactory = ConnectionFactory.getConnectionFactory();
	private static DPBLog LOGGER = DPBLog.getInstance(BonusProcessingDAOImpl.class);
	static final private String CLASSNAME = BonusProcessingDAOImpl.class.getName();

	public List<ProcessBonus> procBonusProc()
	{
		LOGGER.enter(CLASSNAME, "procBonusProc()");
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<ProcessBonus> processBonusList=null;
		ProcessBonus processBonus=null;
		java.util.Date sDate=new java.util.Date();
		String conDate=null;
		List<ProcessBonus> tempProcBonusList=new ArrayList<ProcessBonus>();
		try {

			con  = conFactory.getConnection();
			processBonusList=new ArrayList<ProcessBonus>();
			
			//String query = IDefinitionQueryConstants.rtlMonthStartDateQuery;
			if (con != null) {
				long btime=System.currentTimeMillis();
				LOGGER.info("Time before prepared statement:"+btime);
				preparedStatement = con.prepareStatement(IFileProcessingQueryConstants.PROCESS_START_VIEW_QUERY +" "+ IBonusProcessingQueryConstants.PROCESS_NORMAL_BONUS_VIEW_START);
				long atime=System.currentTimeMillis();
				LOGGER.info("Time after prepared statement:"+atime+":Time taken to process (ms) :atime-btime:"+(atime-btime));
				resultSet = preparedStatement.executeQuery();
				long etime=System.currentTimeMillis();
				LOGGER.info("Time after query execution"+etime+":Time taken to process (ms) :etime-atime:"+(etime-atime));
				
				int successCount = 0;
				while(resultSet.next()){
					String fSts = resultSet.getString("FINAL_STATUS");
					String lSts = resultSet.getString("LOG_STATUS");
					if((fSts!= null && fSts.equalsIgnoreCase("V")) && (lSts!= null && lSts.equalsIgnoreCase("S"))){
						successCount++;	
					}
					processBonus = new ProcessBonus();

					processBonus.setProcDpbID(resultSet.getString("PROC_ID"));
					processBonus.setProcessName(resultSet.getString("PROG_NAME"));
					processBonus.setProgId(resultSet.getInt("PROG_ID"));
					processBonus.setPrgType(resultSet.getString("PROG_TYP"));
					processBonus.setProcStatus(resultSet.getString("LOG_STATUS"));
					processBonus.setProcessMsg(resultSet.getString("LOG_TXT"));
					processBonus.setFlag(resultSet.getString("FINAL_STATUS"));
					processBonus.setProcActStat(resultSet.getString("DES_DPB_PROC_STS"));
					processBonus.setProcDate(resultSet.getDate("PROC_ACT_DTE"));
					processBonus.setReprocess(resultSet.getString("REPROC_STATUS"));
					processBonus.setReset(resultSet.getString("RESET_STATUS"));
					processBonusList.add(processBonus);
				}
				long rtime=System.currentTimeMillis();
				LOGGER.info("Time after result set get iterated:"+rtime+":Time taken to process (ms) :rtime-etime:"+(rtime-etime));
				LOGGER.info("Total records received from DB:"+processBonusList.size());
				if(processBonusList!= null && !processBonusList.isEmpty()){
				int nextRc = 0;
			//	if(successCount > 0){					
					//int backCount = (successCount==1?1:2);	
					int startCount = (successCount > 10 ? successCount-10:0);
					for(int i = startCount ;i < processBonusList.size();i++ ){
						tempProcBonusList.add(processBonusList.get(i));
						nextRc ++;
						if(nextRc > 35){
							break;
						}
					}
				}else{
					tempProcBonusList = processBonusList;
				}
			/*		}
				}else{
					for(int i = 0 ;i < processBonusList.size();i++ ){
						tempProcBonusList.add(processBonusList.get(i));
						nextRc ++;
						if(nextRc> 5){
							break;
						}
					}
				}*/
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
		return tempProcBonusList;
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

			preparedStatement = con.prepareStatement(IFileProcessingQueryConstants.PROCESS_START_VIEW_QUERY +" "+ IBonusProcessingQueryConstants.PROCESS_LDR_BONUS_VIEW_START );
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){

				processLdrBonus = new ProcessLdrBonus();

				processLdrBonus.setProcDpbID(resultSet.getString("PROC_ID"));
				processLdrBonus.setProcessName(resultSet.getString("PROG_NAME"));
				processLdrBonus.setProgId(resultSet.getInt("PROG_ID"));
				processLdrBonus.setPrgType(resultSet.getString("PROG_TYP"));
				processLdrBonus.setProcStatus(resultSet.getString("LOG_STATUS"));
				processLdrBonus.setProcessMsg(resultSet.getString("LOG_TXT"));
				processLdrBonus.setFlag(resultSet.getString("FINAL_STATUS"));
				processLdrBonus.setProcActStat(resultSet.getString("DES_DPB_PROC_STS"));
				processLdrBonus.setProcDate(resultSet.getDate("PROC_ACT_DTE"));
				processLdrBonus.setReprocess(resultSet.getString("REPROC_STATUS"));
				processLdrBonus.setReset(resultSet.getString("RESET_STATUS"));
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

	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List <DPBProcessLogBean> procesDetail = null;
		DPBProcessLogBean processCalDefBean = null;
		try {
			con  = conFactory.getConnection();
			String query =IBonusProcessingQueryConstants.GET_DPB_LOG_POPUP_REC; //IDefinitionQueryConstants.getPaymentDeatilsQuery;

			if (con != null) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, procId);
				resultSet = preparedStatement.executeQuery();
				procesDetail = new ArrayList<DPBProcessLogBean>();
				int i = 1;
				while (resultSet.next()) {
					processCalDefBean =  new DPBProcessLogBean();
					processCalDefBean.setSerialNo(i);
					processCalDefBean.setDpbProcessId(resultSet.getInt("ID_DPB_PROC_LOG"));
					processCalDefBean.setProcessMessage(resultSet.getString("TXT_DPB_PROC_MSG")!=null ?resultSet.getString("TXT_DPB_PROC_MSG").trim():null);
					processCalDefBean.setDpbProcessStatus(resultSet.getString("DES_DPB_PROC_STS")!=null ?resultSet.getString("DES_DPB_PROC_STS").trim():null);
					procesDetail.add(processCalDefBean);
					i++;
				}
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return procesDetail;
	}

	
	
	public void getDailyRetailData()
	{		
		LOGGER.enter(CLASSNAME, "getDailyRetailData()");
		Connection con = null;
		ResultSet rs= null;		
		PreparedStatement ps = null;
		VistaFileProcessing vistaRtl =  new VistaFileProcessing();
		List<VistaFileProcessing> vistaRtlList =  new ArrayList<VistaFileProcessing>();
		try {
			con = conFactory.getConnection();	
			String query= IBonusProcessingQueryConstants.GET_DAILY_VISTA_RETAIL_DATA;		
			ps = con.prepareStatement(query);		
			rs= ps.executeQuery();

			while(rs.next()){
				/*NUM_PO ,NUM_VIN,DTE_RTL,TME_RTL,CDE_VEH_STAT,ID_DLR,IND_USED_CAR,DTE_DEMO_SRV,ID_EMP_PUR
			DTE_MDL_YR, NAM_MDL, IND_USED_CARS,	CDE_REG, NAM_LAST, NAM_FST,	NAM_MID, DTE_TRANS
			TME_TRANS, AMT_MSRP_BAS,AMT_MSRP_OPT,AMT_RBT,IND_FLT,TYP_WS_INIT
			vistaRtl =  new VistaFileProcessing();
			vistaRtl.setPoNo(rs.getString("NUM_PO"));
			vistaRtl.setVinNum(rs.getString("NUM_VIN"));
			vistaRtl.setRetailDate(rs.getString("DTE_RTL"));
			vistaRtl.setRetailTime(rs.getString("TME_RTL"));
			vistaRtl.setVehStatusCode(rs.getString("CDE_VEH_STAT"));
			vistaRtl.setDealerId(rs.getString("ID_DLR"));
			vistaRtl.setUsedCarIndicator(rs.getString("IND_USED_CAR"));
			vistaRtl.setDemoSrvDate(rs.getString("DTE_DEMO_SRV"));
			vistaRtl.setEmpPurId(rs.getString("ID_EMP_PUR"));
			vistaRtl.setModelYearDate(rs.getString("DTE_MDL_YR"));
			vistaRtl.setModelName(rs.getString("NAM_MDL"));
			vistaRtl.setUsedCarIndicator2(rs.getString("IND_USED_CARS"));
			vistaRtl.setRegionCode(rs.getString("CDE_REG"));
			vistaRtl.setLastName(rs.getString("NAM_LAST"));
			vistaRtl.setFirstName(rs.getString("NAM_FST"));
			vistaRtl.setMiddleName(rs.getString("NAM_MID"));
			vistaRtl.setTransDate(rs.getString("DTE_TRANS"));
			vistaRtl.setTransTime(rs.getString("TME_TRANS"));
			vistaRtl.setMsrpBaseAmount(rs.getString("AMT_MSRP_BAS"));
			vistaRtl.setMsrpBaseAmtOptions(rs.getString("AMT_MSRP_OPT"));
			vistaRtl.setRebateAmt(rs.getString("AMT_RBT"));
			vistaRtl.setFleetIndicator(rs.getString("IND_FLT"));
			vistaRtl.setWholeSaleInitType(rs.getString("TYP_WS_INIT"));		*/					
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
	}

	

	public RtlCalenderDefinition getRetailCalender(Date cDate) {
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
	}
}
