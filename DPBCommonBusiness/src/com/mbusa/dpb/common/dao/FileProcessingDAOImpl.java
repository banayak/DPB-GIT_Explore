/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: FileProcessingDAOImpl.java
 * Program Version			: 1.0
 * Program Description		: This class is used handle Database related functionality like CRUD operation 
 * 							  business type validation if require. 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/

package com.mbusa.dpb.common.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.io.FileUtils;

import com.mbusa.dpb.common.constant.IBonusProcessingQueryConstants;
import com.mbusa.dpb.common.constant.IFileProcessingQueryConstants;
import com.mbusa.dpb.common.db.ConnectionFactory;
import com.mbusa.dpb.common.domain.BonusInfo;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.FieldColumnMapBean;
import com.mbusa.dpb.common.domain.FileDefinitionBean;
import com.mbusa.dpb.common.domain.FileFormatBean;
import com.mbusa.dpb.common.domain.GeneratePaymentFile;
import com.mbusa.dpb.common.domain.GenerateReportFile;
import com.mbusa.dpb.common.domain.Kpi;
import com.mbusa.dpb.common.domain.KpiFileProcessing;
import com.mbusa.dpb.common.domain.NetAccrualFileProcessing;
import com.mbusa.dpb.common.domain.ProcessInputFile;
import com.mbusa.dpb.common.domain.ProgramDefinition;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.DPBExceptionHandler;
import com.mbusa.dpb.common.logger.DPBLog;

public class FileProcessingDAOImpl implements IFileProcessingDAO {

	private ConnectionFactory conFactory = ConnectionFactory
			.getConnectionFactory();
	private static DPBLog LOGGER = DPBLog
			.getInstance(FileProcessingDAOImpl.class);
	static final private String CLASSNAME = FileProcessingDAOImpl.class
			.getName();

	/**
	 * 
	 * @see com.mbusa.dpb.common.dao.IFileProcessingDAO#getFileMappingDetails(java.lang.Integer)
	 * @param fileFormatId
	 * @return
	 * 
	 */
	public List<FieldColumnMapBean> getFileMappingDetails(int fileFormatId) {

		final String methodName = "getFileMappingDetails";
		LOGGER.enter(CLASSNAME, methodName);

		FieldColumnMapBean mapBean = null;
		Kpi kpiBean = null;
		List<FieldColumnMapBean> mapBeanList = new ArrayList<FieldColumnMapBean>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conFactory.getConnection();
			String query = "select colmap.ID_DPB_FILE_COL_REL,colmap.ID_DPB_FILE_FMT,colmap.NUM_FILE_COL_SEQ,colmap.TXT_DPB_FILE_COL_FMT,"
					+ "colmap.NUM_FILE_COL_LNTH,colmap.NAM_TBL,colmap.NAM_COL,colmap.ID_CREA_USER,colmap.ID_LAST_UPDT_USER,"
					+ "colmap.DTS_LAST_UPDT,colmap.DTS_CREA, colmap.ID_KPI, (select Kpi.NAM_KPI from DPB_KPI_FDRT Kpi "
					+ "where Kpi.ID_KPI =  colmap.ID_KPI) from DPB_FILE_COL_REL as colmap where colmap.ID_DPB_FILE_FMT = ? "
					+ " ORDER BY colmap.NUM_FILE_COL_SEQ ";
			ps = con.prepareStatement(query);
			ps.setInt(1, fileFormatId);
			rs = ps.executeQuery();

			while (rs.next()) {
				mapBean = new FieldColumnMapBean();
				// mapBean.setColumnName(rs.getString("ID_FILE_COL_MAP"));
				// mapBean.setCreatedUserId(rs.getInt("ID_DPB_FILE_FMT"));
				mapBean.setFileColumnSeqNumber(rs.getInt("NUM_FILE_COL_SEQ"));
				mapBean.setFileColumnformatText(rs
						.getString("TXT_DPB_FILE_COL_FMT"));
				mapBean.setFileColumnLength(rs.getInt("NUM_FILE_COL_LNTH"));
				mapBean.setTableName(rs.getString("NAM_TBL"));
				mapBean.setColumnName(rs.getString("NAM_COL"));
				kpiBean = mapBean.getKpi();
				kpiBean.setKpiId(rs.getInt("ID_KPI"));
				kpiBean.setKpiName(rs.getString("NAM_KPI"));
				mapBean.setKpi(kpiBean);
				mapBeanList.add(mapBean);
			}
		} catch (SQLException e) {
			LOGGER.error("Exception while getting mapping details", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("Exception while getting mapping details", ne);
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
		return mapBeanList;
	}

	/**
	 * 
	 * @see com.mbusa.dpb.common.dao.IFileProcessingDAO#saveVistaFileDetails(java.util.List)
	 * @param vistaRecords
	 * @return
	 * 
	 */
	public void saveVistaFileDetails(List<VistaFileProcessing> validRecords) {

		final String methodName = "saveVistaFileDetails";
		LOGGER.enter(CLASSNAME, methodName);

		Connection con = null;
		PreparedStatement psTwo = null;
		ResultSet rs = null;
		try {
			
			con = conFactory.getConnection();
			/*String query = "INSERT INTO DPB_VEH_RTL_EXTRT"
					+ "(NUM_PO,NUM_VIN,DTE_RTL,TME_RTL,CDE_VEH_STS,CDE_VEH_TYP,ID_DLR,IND_USED_CAR,ID_EMP_PUR_CTRL,"
					+ "DTE_MDL_YR,TXT_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,DTE_TRANS,TME_TRANS,"
					+ "AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,CDE_USE,"
					+ "ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA,ID_DPB_PROC,CDE_SLE_TYP,DTE_DEMO_SRV," +
					" CDE_CAR_STS,IND_USED_CAR2,AMT_MSRP_OPTS)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,current timestamp,current timestamp,?,?,?,?,?,?)";*/
			String query = IFileProcessingQueryConstants.VISTA_INSERT;
			int size = validRecords.size();	
			int count=1;
			psTwo = con.prepareStatement(query);
			
				for (VistaFileProcessing fProcess : validRecords) {
					
					insertVistaDetailsPid(fProcess, psTwo);
					if(size == count || count == 500){
						int [] upCount = psTwo.executeBatch();	
						size = size - count;
						count = 0;
					}
					count++;
					
				}				
		}
		catch (SQLException e) {
			LOGGER.error("SQLException while saving the data into DB", e);
			LOGGER.error("SQLException while saving the data into DB", e.getNextException());
			
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (Exception ne) {
			LOGGER.error("Exception while saving the data into DB", ne);
			ne.printStackTrace();
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (psTwo != null) {
					psTwo.close();
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
	
	private void insertVistaDetailsPid(VistaFileProcessing fProcess, PreparedStatement ps) throws SQLException {		
		ps.setString(1, fProcess.getPoNo());
		ps.setString(2, fProcess.getVinNum());
		ps.setDate(3, fProcess.getRetailDate());
		ps.setTime(4, fProcess.getRetailTime());
		ps.setString(5, fProcess.getVehStatusCode());
		ps.setString(6, fProcess.getVehTypeCode());
		ps.setString(7, fProcess.getDealerId());
		ps.setString(8, fProcess.getUsedCarIndicator());
		ps.setString(9, fProcess.getEmpPurCtrlId());
		ps.setString(10, fProcess.getModelYearDate());
		ps.setString(11, fProcess.getModelText());
		ps.setString(12, fProcess.getRegionCode());
		ps.setString(13, fProcess.getRetailCustLastName());
		ps.setString(14, fProcess.getRetailCustFirstName());
		ps.setString(15, fProcess.getRetailCustMiddleName());
		ps.setDate(16, fProcess.getTransDate());
		ps.setTime(17, fProcess.getTransTime());
		ps.setDouble(18, fProcess.getMsrpBaseAmount());
		ps.setDouble(19, fProcess.getMsrpTotAmtAcsry());
		ps.setDouble(20, fProcess.getDlrRebateAmt());
		ps.setString(21, fProcess.getFleetIndicator());
		ps.setString(22, fProcess.getWholeSaleInitType());
		ps.setString(23, fProcess.getNationalTypeCode());
		ps.setString(24, fProcess.getUseCode());
		ps.setString(25, fProcess.getCreatedUserId());
		ps.setString(26, fProcess.getLastUpdtUserId());
		ps.setInt(27, fProcess.getDpbProcessId());
		ps.setString(28, fProcess.getSalesTypeCode());
		ps.setDate(29, fProcess.getDemoServiceDate());
		ps.setString(30, fProcess.getCarStatusCode());
		ps.setString(31, fProcess.getUsedCarIndicator2());
		ps.setDouble(32, fProcess.getMsrpTotalsAmt());
		if(fProcess.getProgType() == null || fProcess.getProgType() == 0){
		ps.setString(33, null);
		}else {
			ps.setInt(33, fProcess.getProgType());
		}
		ps.setString(34, fProcess.getIndAmg());
		ps.addBatch();
	}
	/**
	 * Method is Used for creating error file and writing failed records into file 
	 * @param fLines
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public byte[] writeFailedRecords(List<String> fLines, String filePath) throws Exception {

		final String methodName = "writeFailedRecords";
		LOGGER.enter(CLASSNAME, methodName);
		
		byte[] errorBytes = null;
		PrintWriter pw = null;
		File file  = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		try {
			pw = new PrintWriter(new FileWriter(file));
			if(fLines != null) {
				for(String line: fLines) {
					//pw.write("\n" + line);	
					pw.println(line);
				}
				
			}
			pw.flush();
			errorBytes = FileUtils.readFileToByteArray(file);
		} catch(Exception e) {
			LOGGER.error("Byte Array");
			throw e;
		} finally {
			if(pw != null){
			pw.close();
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return errorBytes;
	}
	
	/**
	 * @see com.mbusa.dpb.common.dao.IFileProcessingDAO#saveCDDBKPIFileDetails(java.util.List)
	 * @param kpiRecords
	 * @return
	 */
	public void saveKpiFileDetails(List<KpiFileProcessing> kpiRecords) {

		final String methodName = "saveKpiFileDetails";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conFactory.getConnection();

			/*
			  String query =
			  "MERGE INTO KPI_FIL_EXTRT AS KPI USING (values(cast(? as INTEGER), "
			  +
			  "CAST(? AS CHAR(5)), CAST(? AS CHAR(1)),CAST(? AS INTEGER), CAST(? AS INTEGER), "
			  +
			  "CAST(? AS DECIMAL), CAST(? AS CHAR(8)),CAST(? AS CHAR(8)), CAST(current timestamp "
			  +
			  "AS TIMESTAMP), CAST(current timestamp AS TIMESTAMP)))AS KPI_NEW (ID_DPB_PROC,ID_DLR,"
			  +
			  "DTE_FSCL_PERD,DTE_FSCL_YR,ID_KPI,PCT_KPI,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,"
			  +
			  "DTS_CREA)ON KPI.ID_DPB_PROC = KPI_NEW.ID_DPB_PROC AND KPI.ID_DLR = KPI_NEW.ID_DLR "
			  +
			  "AND KPI.ID_KPI = KPI_NEW.ID_KPI AND KPI.DTE_FSCL_YR = KPI_NEW.DTE_FSCL_YR AND "
			  +
			  "KPI.DTE_FSCL_PERD = KPI_NEW.DTE_FSCL_PERD WHEN MATCHED THEN UPDATE SET KPI.PCT_KPI = "
			  +
			  "KPI_NEW.PCT_KPI, KPI.ID_LAST_UPDT_USER = KPI_NEW.ID_LAST_UPDT_USER, KPI.DTS_LAST_UPDT "
			  +
			  "= KPI_NEW.DTS_LAST_UPDT WHEN NOT MATCHED INSERT(ID_DPB_PROC,ID_DLR,DTE_FSCL_PERD,"
			  +
			  "DTE_FSCL_YR,ID_KPI,PCT_KPI,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA)VALUES "
			  +
			  "(KPI_NEW.ID_DPB_PROC,KPI_NEW.ID_DLR,KPI_NEW.DTE_FSCL_PERD,KPI_NEW.DTE_FSCL_YR,"
			  +
			  "KPI_NEW.ID_KPI,KPI_NEW.PCT_KPI,KPI_NEW.ID_CREA_USER,KPI_NEW.ID_LAST_UPDT_USER,"
			  + "KPI_NEW.DTS_LAST_UPDT,KPI_NEW.DTS_CREA) ELSE IGNORE";
			 */
			String query = "INSERT INTO DPB_KPI_FIL_EXTRT(ID_DLR,DTE_FSCL_QTR,ID_KPI,PCT_KPI,DTS_CREA,"
					+ "ID_CREA_USER,DTS_LAST_UPDT,ID_LAST_UPDT_USER,DTE_FSCL_YR,ID_DPB_PROC)VALUES(?,"
					+ "?,?,?,current timestamp,?,current timestamp,?,?,?)";
			ps = con.prepareStatement(query);
			int size = kpiRecords.size();
			int count=1;
			for (KpiFileProcessing fileProcessing : kpiRecords) {
					ps.setString(1, fileProcessing.getDealerId());
					ps.setString(2, fileProcessing.getKpiFiscalPeriod());
					ps.setInt(3, fileProcessing.getKpiId());
					ps.setDouble(4, fileProcessing.getKpiPercentage());
					ps.setString(5, fileProcessing.getCreatedUserId());
					ps.setString(6, fileProcessing.getLastUpdtUserId());
					ps.setInt(7, fileProcessing.getKpiYear());
					ps.setInt(8, fileProcessing.getDpbProcessId());
					ps.addBatch();
				if(size == count || count == 500){
				ps.executeBatch();
				size = size - count;
				count = 0;
				}
				count++;
			}
		} catch (SQLException e) {
			LOGGER.error("Exception while saving Kpi File data in the DB", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("Exception while saving Kpi File data in the DB", ne);
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

	/**
	 * @see com.mbusa.dpb.common.dao.IFileProcessingDAO#saveCoFiCoNetAccrualFileDetais(java.util.List)
	 * @param netAccrualRecords
	 * @return
	 */
	public List<String> saveAccuralFileDetails(List<NetAccrualFileProcessing> recList) {

		final String methodName = "saveAccuralFileDetails";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> negativeRecords = new ArrayList<String>();
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps_one = null;
		ResultSet rs = null;
		try {
			con = conFactory.getConnection();
			/*String query = "INSERT INTO LDRSP_BNS_ACRL(NUM_PO,CDE_CO,DTE_LDRSP_BNS_ACRL_POST,AMT_DPB_LDRSP_BNS,"
					+ "CDE_VEH_TYP,DTS_CREA,ID_CREA_USER,DTS_LAST_UPDT,ID_LAST_UPDT_USER,ID_DPB_PROC)"
					+ "VALUES(?,?,?,?,(SELECT DISTINCT CDE_VEH_TYP FROM DPB_UNBLK_VEH FILE WHERE FILE.NUM_PO = ?)"
					+ ",current timestamp,?,current timestamp,?,?)";*/
			String query = " MERGE INTO LDRSP_BNS_ACRL AS LDRSP USING " +
					" ( VALUES(CAST (? AS CHAR(10)),CAST (? AS CHAR(4)),CAST(? AS DATE)," +
					" CAST(? AS DECIMAL),CAST(CURRENT TIMESTAMP AS TIMESTAMP)," +
					" CAST(? AS CHAR(8)),CAST(CURRENT TIMESTAMP AS TIMESTAMP),CAST(? AS CHAR(8))," +
					" CAST(? AS INTEGER))" +
					" ) AS LDRSP_NEW ( NUM_PO,CDE_CO,DTE_LDRSP_BNS_ACRL_POST,AMT_DPB_LDRSP_BNS," +
					" DTS_CREA,ID_CREA_USER,DTS_LAST_UPDT,ID_LAST_UPDT_USER,ID_DPB_PROC" +
					" ) ON LDRSP.NUM_PO = LDRSP_NEW.NUM_PO AND LDRSP.CDE_CO = LDRSP_NEW.CDE_CO" +
					" WHEN NOT MATCHED THEN INSERT ( NUM_PO,CDE_CO,DTE_LDRSP_BNS_ACRL_POST,AMT_DPB_LDRSP_BNS," +
					" CDE_VEH_TYP,DTS_CREA,ID_CREA_USER,DTS_LAST_UPDT,ID_LAST_UPDT_USER,ID_DPB_PROC" +
					" ) VALUES ( LDRSP_NEW.NUM_PO,LDRSP_NEW.CDE_CO,LDRSP_NEW.DTE_LDRSP_BNS_ACRL_POST," +
					" LDRSP_NEW.AMT_DPB_LDRSP_BNS,(SELECT DISTINCT CDE_VEH_TYP FROM DPB_UNBLK_VEH UNBK WHERE UNBK.NUM_PO = LDRSP_NEW.NUM_PO)" +
					" ,LDRSP_NEW.DTS_CREA,LDRSP_NEW.ID_CREA_USER," +
					" LDRSP_NEW.DTS_LAST_UPDT,LDRSP_NEW.ID_LAST_UPDT_USER,LDRSP_NEW.ID_DPB_PROC" +
					" ) WHEN MATCHED THEN UPDATE SET  LDRSP.NUM_PO = LDRSP_NEW.NUM_PO, LDRSP.CDE_CO = LDRSP_NEW.CDE_CO," +
					" LDRSP.DTE_LDRSP_BNS_ACRL_POST = LDRSP_NEW.DTE_LDRSP_BNS_ACRL_POST, LDRSP.AMT_DPB_LDRSP_BNS = (LDRSP.AMT_DPB_LDRSP_BNS + LDRSP_NEW.AMT_DPB_LDRSP_BNS)," +
					" LDRSP.DTS_LAST_UPDT = CURRENT TIMESTAMP, " +
					" LDRSP.ID_LAST_UPDT_USER = LDRSP_NEW.ID_LAST_UPDT_USER," + 
					" LDRSP.ID_DPB_PROC = LDRSP_NEW.ID_DPB_PROC" +					
					" ELSE IGNORE";
			String query_one = "select AMT_DPB_LDRSP_BNS from LDRSP_BNS_ACRL where " +
								"   NUM_PO = ? and CDE_CO = ? and AMT_DPB_LDRSP_BNS >= ?";
			ps = con.prepareStatement(query);
			ps_one = con.prepareStatement(query_one);
			int size = recList.size();
			int count=1;
			for (NetAccrualFileProcessing processing : recList) {
				if(processing.getLadrspBnsAmt() < 0){
					ps_one.setString(1, processing.getPoNo());
					ps_one.setString(2, processing.getCoCode());
					ps_one.setDouble(3, (processing.getLadrspBnsAmt() * -1));
					rs = ps_one.executeQuery();
					if(rs.next()){
						addAccuralFileDetails(processing,ps);
					}else{
						negativeRecords.add(processing.getLineString());				
					}
				}else {
					addAccuralFileDetails(processing,ps);
				}
				if(size == count || count == 500){
				ps.executeBatch();
				size = size - count;
				count = 0;
				}
					count++;
				}			
			}
		catch (SQLException e) {
			e.getNextException();
			LOGGER.error("Exception while inserting Net Accrual data", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("Exception while inserting Net Accrual data", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (ps_one != null) {
					ps_one.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return negativeRecords;
	}

	public void addAccuralFileDetails(NetAccrualFileProcessing processing, PreparedStatement ps) throws SQLException {
		ps.setString(1, processing.getPoNo());
		ps.setString(2, processing.getCoCode());
		ps.setDate(3, processing.getLadspBnsAcrlPostdate());
		ps.setDouble(4, processing.getLadrspBnsAmt());
		//ps.setString(5, processing.getPoNo());
		ps.setString(5, processing.getCreatedUserId());
		ps.setString(6, processing.getLastUpdtUserId());
		ps.setInt(7, processing.getDpbProcessId());
		ps.addBatch();
	}	
	/**
	 * 
	 * @param processID
	 * @param formatbean
	 * @return
	 */
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
			String query = "select file.ID_DPB_FILE, file.TXT_DPB_FMT_FILE_NAM,"
					+ " fmt.ID_DPB_FILE_FMT,fmt.NAM_DPB_FIL_FMT, fmt.IND_INBND_FILE,fmt.IND_HDR,fmt.TXT_DLM,fmt.CNT_COL ,"
					+ " file.NAM_DPB_FIL,"
					+ " file.TXT_DPB_BASE_DPTH "
					+ " from DPB_PROC proc, DPB_FILE file, DPB_FILE_FMT fmt "
					+ " where proc.ID_DPB_PROC = ? "
					+ " and proc.CDE_DPB_PROC_TYP = 'PF'"
					+ " and file.ID_DPB_FILE = proc.ID_DPB_FILE and file.CDE_DPB_STS = 'A'"
					+ " and fmt.ID_DPB_FILE_FMT = file.ID_DPB_FILE_FMT with ur";
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
				formatBean.setInbountFileIndicator(rs
						.getString("IND_INBND_FILE"));
				formatBean.setIndHeader(rs.getString("IND_HDR"));
				formatBean.setIndDelimited(rs.getString("TXT_DLM"));
				formatBean.setColumnCount(rs.getInt("CNT_COL"));
				// formatBean.setFileMapingList(getFileMappingDetails(conn,
				// formatBean.getFileFormatId()));
				definitionBean.setFileFormats(formatBean);
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
		return definitionBean;
	}

	public void saveBlockedRecords(List<VistaFileProcessing> blockedRecords) {
		final String methodName = "saveBlockedRecords";
		LOGGER.enter(CLASSNAME, methodName);
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conFactory.getConnection();
			String query = IFileProcessingQueryConstants.VISTA_BLOCKED_VEH_INSERT;
			ps = con.prepareStatement(query);
			int size = blockedRecords.size();
			LOGGER.info("Valid saveBlockedRecords for save**********************************:"+size);
			int count=1;
			for (VistaFileProcessing records : blockedRecords) {
				LOGGER.info("inside loop successfully inserted"+size);
				insertBlockedVehDetailsPid(records,ps);
				if(size == count || count == 500){
					ps.executeBatch();
					size = size - count;
					count = 0;
				}
				count++;
			}
			
			LOGGER.info("saveBlockedRecords successfully inserted"+size);
		} catch (SQLException e) {
			LOGGER.error("Exception while saving the blocked vehicles", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (Exception ne) {
			LOGGER.error("Exception while saving the blocked vehicles", ne);
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

	/*
	 * This method will be used to set the parameters for saving the blocked vehicles details
	 */
	private void insertBlockedVehDetailsPid(VistaFileProcessing fProcess, PreparedStatement ps) throws SQLException {		
		ps.setString(1, fProcess.getPoNo());					// NUM_PO
		ps.setString(2, fProcess.getDealerId());				// ID_DLR
		ps.setString(3, fProcess.getVinNum());					// NUM_VIN
		ps.setInt(4, fProcess.getCondId());						// ID_DPB_CND
		ps.setDate(5, fProcess.getRetailDate());				// DTE_RTL
		ps.setTime(6, fProcess.getRetailTime());				// TME_RTL
		ps.setInt(7, fProcess.getDpbProcessId());				// ID_DPB_PROC
		ps.setString(8, fProcess.getCreatedUserId());			// ID_CREA_USER
		ps.setString(9, fProcess.getLastUpdtUserId());			// ID_LAST_UPDT_USER
		ps.setString(10, fProcess.getVehTypeCode());			// CDE_VEH_TYP
		ps.setString(11, fProcess.getIndAmg());					// IND_AMG
		ps.setString(12, fProcess.getVehStatusCode());			// CDE_VEH_STS
		ps.setString(13, fProcess.getUseCode());				// CDE_USE_VEH
		ps.setString(14, fProcess.getUsedCarIndicator());		// IND_USED_VEH
		ps.setString(15, fProcess.getCarStatusCode());			// CDE_VEH_DDR_STS
		ps.setString(16, fProcess.getSalesTypeCode());			// CDE_VEH_DDR_USE
		ps.setString(17, fProcess.getUsedCarIndicator2());		// IND_USED_VEH_DDRS
		ps.setString(18, fProcess.getEmpPurCtrlId());			// ID_EMP_PUR_CTRL
		ps.setString(19, fProcess.getModelYearDate());			// DTE_MODL_YR
		ps.setString(20, fProcess.getModelText());				// DES_MODL
		ps.setString(21, fProcess.getRegionCode());				// CDE_RGN
		ps.setString(22, fProcess.getRetailCustLastName());		// NAM_RTL_CUS_LST
		ps.setString(23, fProcess.getRetailCustFirstName());	// NAM_RTL_CUS_FIR
		ps.setString(24, fProcess.getRetailCustMiddleName());	// NAM_RTL_CUS_MID
		ps.setDate(25, fProcess.getTransDate());				// DTE_TRANS
		ps.setTime(26, fProcess.getTransTime());				// TME_TRANS
		ps.setDouble(27, fProcess.getMsrpBaseAmount());			// AMT_MSRP_BASE
		ps.setDouble(28, fProcess.getMsrpTotAmtAcsry());		// AMT_MSRP_TOT_ACSRY
		ps.setDouble(29, fProcess.getDlrRebateAmt());			// AMT_DLR_RBT
		ps.setDouble(30, fProcess.getMsrpTotalsAmt());			// AMT_MSRP
		ps.setString(31, fProcess.getFleetIndicator());			// IND_FLT
		ps.setString(32, fProcess.getWholeSaleInitType());		// CDE_WHSLE_INIT_TYP
		ps.setString(33, fProcess.getNationalTypeCode());		// CDE_NATL_TYPE
		ps.setDate(34, fProcess.getDemoServiceDate());			// DTE_VEH_DEMO_SRV
		if(fProcess.getProgType() == null || fProcess.getProgType() == 0){
			ps.setString(35, null);
		}else {
			ps.setInt(35, fProcess.getProgType());				// ID_DPB_PGM
		}
		ps.addBatch();
	}
	
	public List<BonusInfo> getPaymentsData(String paymentType,
			Date startDate, Date endDate) {
		final String methodName = "getPaymentsData";
		LOGGER.enter(CLASSNAME, methodName);
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<BonusInfo> dlrBnsList = null;
		BonusInfo dlrBns = null;

		try {
			con = conFactory.getConnection();
			dlrBnsList = new ArrayList<BonusInfo>();
			if (con != null) {
				preparedStatement = con.prepareStatement(IBonusProcessingQueryConstants.RETRIVR_DLR_PAYMENT);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					dlrBns = new BonusInfo();
					dlrBns.setDpbUnblkVehId(resultSet.getInt("ID_DPB_UNBLK_VEH"));
					dlrBns.setBnsCalcAmt(resultSet.getDouble("BONUSVAL"));
					dlrBns.setDlrId(resultSet.getString("ID_DLR"));
					dlrBns.setPgmId(resultSet.getInt("ID_DPB_PGM"));
					dlrBns.setModel(resultSet.getString("v.DES_MODL"));
					dlrBns.setVehicleIdentificationNum(resultSet.getString("NUM_VIN"));
					dlrBns.setYear(resultSet.getString("DTE_MDL_YR"));
					dlrBns.setVehicleType(resultSet.getString("CDE_VEH_TYP"));
					dlrBns.setPgmName(resultSet.getString("CDE_VEH_TYP"));					
					dlrBnsList.add(dlrBns);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("DPB.FILE.PAYMENT.001", e);
			DPBExceptionHandler.getInstance().handleDatabaseException(e);
		} catch (NamingException ne) {
			LOGGER.error("DPB.FILE.PAYMENT.002", ne);
			DPBExceptionHandler.getInstance().handleException(ne);
		} catch (Exception e) {
			e.printStackTrace();
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
				LOGGER.error("DPB.FILE.PAYMENT.003", e);
				DPBExceptionHandler.getInstance().handleDatabaseException(e);
			}
		}
		LOGGER.exit(CLASSNAME, "genPayFile()");
		return dlrBnsList;
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
			/*String query = "select file.ID_DPB_FILE,"
					+ " fmt.ID_DPB_FILE_FMT,fmt.NAM_DPB_FIL_FMT, fmt.IND_INBND_FILE,fmt.IND_HDR,fmt.TXT_DLM,fmt.CNT_COL ,"
					+ " Trim(file.TXT_DPB_BASE_DPTH)||'\\'||Trim(file.NAM_DPB_FIL) as path ,file.NAM_DPB_FIL,"
					+ " file.TXT_DPB_BASE_DPTH "
					+ " from DPB_PROC proc, DPB_FILE file, FILE_FMT fmt "
					+ " where proc.ID_DPB_PROC = ? "
					+ " and proc.CDE_DPB_PROC_TYP = 'PF'"
					+ " and file.ID_DPB_FILE = proc.ID_DPB_PGM and file.CDE_DPB_STS = 'A'"
					+ " and fmt.ID_DPB_FILE_FMT = file.ID_DPB_FILE_FMT with ur";*/
			ps = con.prepareStatement(IFileProcessingQueryConstants.RETREIVE_PROCESS_DEFINITION_DETAIL);
			ps.setInt(1, processID);
			rs = ps.executeQuery();
		/*	
			" TME_OVRD_PROC,CDE_DPB_OVRD_TRGR,TXT_RSN_PROC_UPD," +
			" ," +
			" ,,,, " +
			*/
			while (rs.next()) {
				pDefinition.setProcessId(rs.getInt("ID_DPB_PROC"));
				pDefinition.setPrcDefID(rs.getInt("ID_DPB_APP_ENT"));
				//pDefinition.setProcDte(rs.getDate("ID_DAY"));
				//pDefinition.setActlProcDte(rs.getDate("DTE_DPB_ACTL_PROC"));
				pDefinition.setProcessType(rs.getString("CDE_DPB_PROC_TYP"));
				pDefinition.setIdOverProcDte(rs.getInt("ID_OVRD_PROC_DTE"));
				pDefinition.setTmeDpbOvrdTrgr(rs.getString("TME_OVRD_PROC"));
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

	public List<ProcessInputFile> getProcInputFileDetails()
	{
		LOGGER.enter(CLASSNAME, "getProcInputFileDetails()");
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<ProcessInputFile> procInpFileList=null;
		ProcessInputFile processInputFile=null;
		List<ProcessInputFile> tempList=new ArrayList<ProcessInputFile>();
		try {		
			con  = conFactory.getConnection();
			procInpFileList=new ArrayList<ProcessInputFile>();
			//String query = IDefinitionQueryConstants.rtlMonthStartDateQuery;
			int successCount = 0;
			if (con != null) {
				//LOGGER.info("File proces:"+IFileProcessingQueryConstants.PROCESS_START_VIEW_QUERY +" "+IFileProcessingQueryConstants.FILE_PROC_START_VIEW );
				//long btime=System.currentTimeMillis();
				//LOGGER.info("Time before prepared statement:"+btime);
				preparedStatement = con.prepareStatement(IFileProcessingQueryConstants.PROCESS_START_VIEW_QUERY +" "+IFileProcessingQueryConstants.FILE_PROC_START_VIEW );
				//long atime=System.currentTimeMillis();
				//LOGGER.info("Time after prepared statement:"+atime+":Time taken to process (ms) :atime-btime:"+(atime-btime));				
				resultSet = preparedStatement.executeQuery();
				//long etime=System.currentTimeMillis();
				//LOGGER.info("Time after query execution:"+etime+":Time taken to process (ms) :etime-atime:"+(etime-atime));
				
				while(resultSet.next()){					
					String fSts = resultSet.getString("FINAL_STATUS");
					String lSts = resultSet.getString("LOG_STATUS");
					if((fSts!= null && fSts.equalsIgnoreCase("V")) && (lSts!= null && lSts.equalsIgnoreCase("S"))){
						successCount++;	
					}						
					processInputFile = new ProcessInputFile();					
					processInputFile.setProcDpbID(resultSet.getString("PROC_ID"));
					processInputFile.setProcessName(resultSet.getString("PROG_NAME"));
					processInputFile.setProgId(resultSet.getInt("PROG_ID"));
					processInputFile.setPrgType(resultSet.getString("PROG_TYP"));
					processInputFile.setProcStatus(resultSet.getString("LOG_STATUS"));
					processInputFile.setProcessMsg(resultSet.getString("LOG_TXT"));
					processInputFile.setFlag(resultSet.getString("FINAL_STATUS"));
					processInputFile.setProcActStat(resultSet.getString("DES_DPB_PROC_STS"));
					processInputFile.setProcDate(resultSet.getDate("PROC_ACT_DTE"));
					processInputFile.setReprocess(resultSet.getString("REPROC_STATUS"));
					processInputFile.setReset(resultSet.getString("RESET_STATUS"));
					procInpFileList.add(processInputFile);
				}
				//long rtime=System.currentTimeMillis();
				//LOGGER.info("Time after result set get iterated:"+rtime+":Time taken to process (ms) :rtime-etime:"+(rtime-etime));
				}
			
				//LOGGER.info("Total records received file process screen from DB:"+procInpFileList.size());
					if(procInpFileList!= null && !procInpFileList.isEmpty()){
						int nextRc = 0;
						//	if(successCount > 0){					
						//int backCount = (successCount==1?1:2);	
						int startCount = (successCount > 5 ? successCount-5:0);
						for(int i = startCount ;i < procInpFileList.size();i++ ){
							tempList.add(procInpFileList.get(i));
							nextRc ++;
							if(nextRc> 25){
								break;
							}
						}
					}else{
						tempList = procInpFileList;
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
		return tempList;
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
		int successCount = 0;
		List<GeneratePaymentFile> tempList=new ArrayList<GeneratePaymentFile>();	
		try {
		
			con  = conFactory.getConnection();
			genPaymentFileList=new ArrayList<GeneratePaymentFile>();
			//String query = IDefinitionQueryConstants.rtlMonthStartDateQuery;
			if (con != null) {				
				preparedStatement = con.prepareStatement(IFileProcessingQueryConstants.PROCESS_START_VIEW_QUERY +" "+IFileProcessingQueryConstants.PAYMENT_PROC_START_VIEW);
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					String fSts = resultSet.getString("FINAL_STATUS");
					String lSts = resultSet.getString("LOG_STATUS");
					if((fSts!= null && fSts.equalsIgnoreCase("V")) && (lSts!= null && lSts.equalsIgnoreCase("S"))){
						successCount++;	
					}
					genPaymentFile = new GeneratePaymentFile();					
					genPaymentFile.setProcDpbID(resultSet.getString("PROC_ID"));
					genPaymentFile.setProcessName(resultSet.getString("PROG_NAME"));
					genPaymentFile.setProgId(resultSet.getInt("PROG_ID"));
					genPaymentFile.setPrgType(resultSet.getString("PROG_TYP"));
					genPaymentFile.setProcStatus(resultSet.getString("LOG_STATUS"));
					genPaymentFile.setProcessMsg(resultSet.getString("LOG_TXT"));
					genPaymentFile.setFlag(resultSet.getString("FINAL_STATUS"));
					genPaymentFile.setProcActStat(resultSet.getString("DES_DPB_PROC_STS"));
					genPaymentFile.setProcDate(resultSet.getDate("PROC_ACT_DTE"));
					genPaymentFileList.add(genPaymentFile);
				}
				if(genPaymentFileList!= null && !genPaymentFileList.isEmpty()){
					int nextRc = 0;
						//	if(successCount > 0){					
						//int backCount = (successCount==1?1:2);	
						int startCount = (successCount > 10 ? successCount-10:0);
						for(int i = startCount ;i < genPaymentFileList.size();i++ ){
							tempList.add(genPaymentFileList.get(i));
							nextRc ++;
							if(nextRc> 25){
								break;
							}
						}
					}else{
						tempList = genPaymentFileList;
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
		return tempList;
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
		int successCount = 0;
		List<GenerateReportFile> tempList = new ArrayList<GenerateReportFile>();
		try {
		
			con  = conFactory.getConnection();
			genReportFileList=new ArrayList<GenerateReportFile>();
			//String query = IDefinitionQueryConstants.rtlMonthStartDateQuery;
			if (con != null) {
				
				preparedStatement = con.prepareStatement(IFileProcessingQueryConstants.PROCESS_START_VIEW_QUERY +" "+IFileProcessingQueryConstants.REPORT_PROC_START_VIEW);
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){

					String fSts = resultSet.getString("FINAL_STATUS");
					String lSts = resultSet.getString("LOG_STATUS");
					if((fSts!= null && fSts.equalsIgnoreCase("V")) && (lSts!= null && lSts.equalsIgnoreCase("S"))){
						successCount++;	
					}
					genReportFile = new GenerateReportFile();
					
					genReportFile.setProcDpbID(resultSet.getString("PROC_ID"));
					genReportFile.setProcessName(resultSet.getString("PROG_NAME"));
					genReportFile.setProgId(resultSet.getInt("PROG_ID"));
					genReportFile.setPrgType(resultSet.getString("PROG_TYP"));
					genReportFile.setProcStatus(resultSet.getString("LOG_STATUS"));
					genReportFile.setProcessMsg(resultSet.getString("LOG_TXT"));
					genReportFile.setFlag(resultSet.getString("FINAL_STATUS"));
					genReportFile.setProcActStat(resultSet.getString("DES_DPB_PROC_STS"));
					genReportFile.setProcDate(resultSet.getDate("PROC_ACT_DTE"));
					genReportFileList.add(genReportFile);
				}
				if(genReportFileList!= null && !genReportFileList.isEmpty()){
					int nextRc = 0;
						//	if(successCount > 0){					
						//int backCount = (successCount==1?1:2);	
						int startCount = (successCount > 10 ? successCount-10:0);
						for(int i = startCount ;i < genReportFileList.size();i++ ){
							tempList.add(genReportFileList.get(i));
							nextRc ++;
							if(nextRc> 25){
								break;
							}
						}
					}else{
						tempList = genReportFileList;
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
		return tempList;
	}
	
	/* Gen Report File---Ends*/
	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List <DPBProcessLogBean> procesDetail = null;		
		DPBProcessLogBean processCalDefBean = null;
		try {
			con  = conFactory.getConnection();
			String query =IFileProcessingQueryConstants.GET_DPB_LOG_POPUP_REC; //IDefinitionQueryConstants.getPaymentDeatilsQuery;

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

}
