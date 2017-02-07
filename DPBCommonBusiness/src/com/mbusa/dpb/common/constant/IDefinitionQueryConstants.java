/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: IDefinitionQueryConstants.java
 * Program Version			: 1.0
 * Program Description		: 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 27, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.constant;



/**
 * 
 * 
 * @author CB5002578
 *
 */
public class IDefinitionQueryConstants {
	
	/**
	 * Retrieve AMG Dealer Name
	 */
	public static final String RETRIEVE_AMG_DEALER_NAME= "SELECT NAM_DLR FROM DEALER_FDRT WHERE ID_DLR = ?";
	
	/**
	 * Retrieve AMG Dealer Info
	 */
	public static final String RETRIEVE_AMG_DEALER_INFO = " SELECT DLR.ID_DLR, FDRT.NAM_DLR, DLR.CDE_AMG_PGM, DLR.DTE_AMG_MBR_EFF, DLR.DTE_AMG_MBR_END  \r\n" + 
			"FROM DPB_DLR_AMG_PGM DLR, DEALER_FDRT FDRT WHERE  DLR.ID_DLR = FDRT.ID_DLR ";
	
	/**
	 * Modify AMG Dealer Info
	 */
	public static final String MODIFY_AMG_DEALER_INFO = " MERGE INTO DPBUSER.DPB_DLR_AMG_PGM AS T\r\n" + 
			"  USING (VALUES(? , ? ,\r\n" + 
			"  ? , ? ,CURRENT_TIMESTAMP, 'SYSTEM',CURRENT_TIMESTAMP,'SYSTEM')) \r\n" + 
			"    AS DAT(ID_DLR,DTE_AMG_MBR_EFF, DTE_AMG_MBR_END,\r\n" + 
			"CDE_AMG_PGM,DTS_CREA,ID_CREA_USER,\r\n" + 
			"DTS_LAST_UPDT,ID_LAST_UPDT_USER)\r\n" + 
			"  ON T.ID_DLR = DAT.ID_DLR\r\n" + 
			"  WHEN MATCHED THEN UPDATE\r\n" + 
			"     SET T.CDE_AMG_PGM = DAT.CDE_AMG_PGM, \r\n" + 
			"     T.DTE_AMG_MBR_EFF = DAT.DTE_AMG_MBR_EFF, \r\n" + 
			"     T.DTE_AMG_MBR_END = DAT.DTE_AMG_MBR_END \r\n" + 
			"  WHEN NOT MATCHED THEN\r\n" + 
			"    INSERT(ID_DLR,DTE_AMG_MBR_EFF,DTE_AMG_MBR_END,\r\n" + 
			"CDE_AMG_PGM,DTS_CREA,ID_CREA_USER,\r\n" + 
			"DTS_LAST_UPDT,ID_LAST_UPDT_USER)\r\n" + 
			"    VALUES (DAT.ID_DLR,DAT.DTE_AMG_MBR_EFF, DAT.DTE_AMG_MBR_END,\r\n" + 
			"DAT.CDE_AMG_PGM,DAT.DTS_CREA,DAT.ID_CREA_USER,\r\n" + 
			"DAT.DTS_LAST_UPDT,DAT.ID_LAST_UPDT_USER)";

	public static final String UPDATE_DPB_FILE_PROCESS ="UPDATE DPB_PROC SET CDE_TYP_PROC=?, ID_DEFN=?, ID_RTL_DTE=(select ID_RTL_DTE from CD5001193SCHMA.RTL_CAL where DTE_RTL =?) WHERE ID_DEFN=? AND CDE_TYP_PROC=?";
	/**
	 * File Definition Queries start
	 */	
		public static final String CREATE_FILE_DEF_QYERY = "INSERT INTO DPB_FILE (ID_DPB_FILE_FMT, NAM_DPB_FIL, DES_DPB_BNS_FIL, TXT_DPB_FMT_FILE_NAM, TME_FILE_IN, TME_FILE_PROC, TXT_DPB_BASE_DPTH, CDE_DPB_SCHD, CDE_DPB_PROC_INIT_TYP, IND_DPB_FIL_CND, CDE_DPB_STS, ID_CREA_USER, ID_LAST_UPDT_USER, DTS_LAST_UPDT, DTS_CREA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
		public static final String INSERT_DPB_FILE_PROCESS ="INSERT INTO DPB_PROC (DTE_CAL, DTE_DPB_ACTL_PROC, ID_DPB_FILE, CDE_DPB_PROC_TYP, ID_CREA_USER, ID_LAST_UPDT_USER, DTS_LAST_UPDT, DTS_CREA) VALUES (?, ?, ?, ?, ?, ?, CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
		public static final String INSERT_DPB_RPT_PROCESS ="INSERT INTO DPB_PROC (DTE_CAL, DTE_DPB_ACTL_PROC, ID_DPB_RPT, CDE_DPB_PROC_TYP, ID_CREA_USER, ID_LAST_UPDT_USER, DTS_LAST_UPDT, DTS_CREA) VALUES (?, ?, ?, ?, ?, ?, CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
		public static final String UPDATE_FILE_DEF_QUERY = "UPDATE DPB_FILE SET  ID_DPB_FILE_FMT=?, NAM_DPB_FIL=?, DES_DPB_BNS_FIL=?, TXT_DPB_FMT_FILE_NAM=?, TME_FILE_IN=?, TME_FILE_PROC=?, TXT_DPB_BASE_DPTH=?, CDE_DPB_SCHD=?, CDE_DPB_PROC_INIT_TYP=?, IND_DPB_FIL_CND=?, CDE_DPB_STS=?, ID_LAST_UPDT_USER=?, DTS_LAST_UPDT=CURRENT TIMESTAMP WHERE ID_DPB_FILE=?";
		public static final String UPDATE_DPB_FILE_INACTIVE = "UPDATE DPB_FILE SET CDE_DPB_STS=?, ID_LAST_UPDT_USER=?, DTE_INACT=? WHERE ID_DPB_FILE=?";
		public static final String FILE_FORMAT_NAME_LIST_QUERY = "SELECT ID_DPB_FILE_FMT, NAM_DPB_FIL_FMT FROM DPB_FILE_FMT WHERE CDE_DPB_STS='A'";
		public static final String RETRIEVE_FILE_EDIT_QUERY = "SELECT file.ID_DPB_FILE, file.ID_DPB_FILE_FMT, file.NAM_DPB_FIL, file.DES_DPB_BNS_FIL, file.TXT_DPB_FMT_FILE_NAM, file.TME_FILE_IN, file.TME_FILE_PROC, file.TXT_DPB_BASE_DPTH, file.CDE_DPB_SCHD, file.CDE_DPB_PROC_INIT_TYP, file.IND_DPB_FIL_CND, file.CDE_DPB_STS, fmt.NAM_DPB_FIL_FMT FROM DPB_FILE file LEFT JOIN DPB_FILE_FMT fmt ON file.ID_DPB_FILE_FMT = fmt.ID_DPB_FILE_FMT WHERE file.ID_DPB_FILE =?";
		public static final String RETRIEVE_FILE_DEF_LIST_QUERY = "SELECT file.ID_DPB_FILE, file.ID_DPB_FILE_FMT, file.NAM_DPB_FIL, file.TME_FILE_IN, file.TME_FILE_PROC, file.CDE_DPB_PROC_INIT_TYP, file.CDE_DPB_STS, fmt.NAM_DPB_FIL_FMT FROM DPB_FILE file JOIN DPB_FILE_FMT fmt ON file.ID_DPB_FILE_FMT = fmt.ID_DPB_FILE_FMT order by file.ID_DPB_FILE asc";
	/**
	 * File Definition Queries end
	 */	
		public static final String CondtionsDetailsQuery = "INSERT INTO CD5001193SCHMA.DPB_COND VALUES(?,?,?,?,?,?,?)";
		public static final String rtlMonthStartDateQuery = "select max(DTE_RTL)from cd5001193schma.RTL_CLNDR";
	/**
	 * File Format Definition Queries start
	 */		
		public static final String CREATE_FILE_FORMAT_QUERY = "INSERT INTO DPB_FILE_FMT (NAM_DPB_FIL_FMT, DES_DPB_FIL_FMT, IND_INBND_FILE, IND_HDR, TXT_DLM, CNT_CPL, CNT_COL, NAM_DPB_TBL, CDE_DPB_STS, ID_CREA_USER, ID_LAST_UPDT_USER, DTS_LAST_UPDT, DTS_CREA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
		public static final String UPDATE_FILE_FORMAT_QUERY = "UPDATE DPB_FILE_FMT SET NAM_DPB_FIL_FMT=?, DES_DPB_FIL_FMT=?, IND_INBND_FILE=?, IND_HDR=?, TXT_DLM=?, CNT_CPL=?, CNT_COL=?, NAM_DPB_TBL=?, CDE_DPB_STS=?, ID_LAST_UPDT_USER=?, DTS_LAST_UPDT=CURRENT TIMESTAMP WHERE ID_DPB_FILE_FMT=?";
		public static final String CHECK_INACTIVE_FILE_STATUS = "SELECT file.NAM_DPB_FIL from DPB_FILE_FMT fmt, DPB_FILE file WHERE fmt.ID_DPB_FILE_FMT = ? AND file.ID_DPB_FILE_FMT = fmt.ID_DPB_FILE_FMT AND file.CDE_DPB_STS = 'A'";
		public static final String UPDATE_FILE_FORMAT_INACTIVE_QUERY = "UPDATE DPB_FILE_FMT SET CDE_DPB_STS=?, ID_LAST_UPDT_USER=?, DTS_LAST_UPDT=CURRENT TIMESTAMP WHERE ID_DPB_FILE_FMT=?";	
		public static final String RETRIEVE_FILE_FORMAT_EDIT_QUERY = "SELECT fmt.ID_DPB_FILE_FMT, fmt.NAM_DPB_FIL_FMT, fmt.DES_DPB_FIL_FMT, fmt.IND_INBND_FILE, fmt.IND_HDR, fmt.TXT_DLM,fmt.CNT_CPL, fmt.CNT_COL, fmt.NAM_DPB_TBL, fmt.CDE_DPB_STS, m.ID_DPB_FILE_COL_REL, m.NUM_FILE_COL_SEQ, m.TXT_DPB_FILE_COL_FMT, m.NUM_FILE_COL_LNTH, m.NAM_TBL, m.NAM_COL, m.ID_KPI,k.NAM_KPI FROM DPB_FILE_FMT fmt LEFT JOIN DPB_FILE_COL_REL m ON fmt.ID_DPB_FILE_FMT = m.ID_DPB_FILE_FMT LEFT JOIN DPB_KPI_FDRT k ON m.ID_KPI = k.ID_KPI WHERE fmt.ID_DPB_FILE_FMT = ? order by m.NUM_FILE_COL_SEQ asc";
		public static final String RETRIEVE_FILE_FORMAT_LIST_QUERY = "SELECT ID_DPB_FILE_FMT, NAM_DPB_FIL_FMT, IND_INBND_FILE, IND_HDR, TXT_DLM, CNT_COL, CDE_DPB_STS FROM DPB_FILE_FMT order by ID_DPB_FILE_FMT asc";
		public static final String CREATE_FIELD_MAP_QUERY = "INSERT INTO DPB_FILE_COL_REL (ID_DPB_FILE_FMT, NUM_FILE_COL_SEQ, TXT_DPB_FILE_COL_FMT, NUM_FILE_COL_LNTH, NAM_TBL, NAM_COL, ID_KPI, ID_CREA_USER, ID_LAST_UPDT_USER, DTS_LAST_UPDT, DTS_CREA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
		public static final String UPDATE_FIELD_MAP_QUERY = "DELETE FROM DPB_FILE_COL_REL WHERE ID_DPB_FILE_FMT=?";
	/**
	 * File Format Definition Queries start
	 */
	
	/**
	 * DPB Process Queries
	 */
/*		 public static final String INSERT_DPB_DEFINITION = "INSERT INTO DPB_PGM " +
					"(NAM_DPB_PGM,DES_DPB_PGM,DTE_DPB_PGM_START,DTE_DPB_PGM_END," +
					"IND_DPB_RBT_PMT,IND_DPB_COMSN_PMT,AMT_DPB_COMSN_PMT,PCT_DPB_COMSN_PMT,PCT_FXD_PMT," +
					"CDE_COMSN_BNS_SCHD, CDE_COMSN_BNS_TRGR, CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_TRGR, CDE_FXD_BNS_SCHD,CDE_FXD_BNS_TRGR,CDE_FXD_PMT_SCHD,CDE_FXD_PMT_TRGR,IND_DPB_FXD_PMT,IND_MAX_VAR_PMT,PCT_VAR_PMT," +
					"CDE_VAR_BNS_SCHD,CDE_VAR_BNS_TRGR,CDE_VAR_PMT_SCHD,CDE_VAR_PMT_TRGR," +
					"IND_SPL_DPB_PGM,ID_KPI,CDE_DPB_STS," +
					"ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA)" +
					"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, CURRENT TIMESTAMP, CURRENT TIMESTAMP )";
*/
	
	 public static final String INSERT_DPB_DEFINITION = "INSERT INTO DPB_PGM " +
														"(NAM_DPB_PGM,DES_DPB_PGM,DTE_DPB_PGM_START,DTE_DPB_PGM_END," +
														"IND_DPB_RBT_PMT,IND_DPB_COMSN_PMT,AMT_DPB_COMSN_PMT,PCT_FXD_PMT," +
														"CDE_COMSN_BNS_PROC_SCHD, CDE_COMSN_PROC_INIT_TYP, CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_PROC_INIT_TYP, CDE_FXD_BNS_SCHD,CDE_FXD_BNS_PROC_INIT_TYP,CDE_FXD_PMT_SCHD,CDE_FXD_PMT_PROC_INIT_TYP,IND_DPB_FXD_PMT,IND_MAX_VAR_PMT,PCT_VAR_PMT," +
														"CDE_VAR_BNS_SCHD,CDE_VAR_BNS_PROC_INIT_TYP,CDE_VAR_PMT_SCHD,CDE_VAR_PMT_PROC_INIT_TYP," +
														"CDE_DPB_PGM_TYP,ID_KPI,CDE_DPB_STS," +
														"ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA)" +
														"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, CURRENT TIMESTAMP, CURRENT TIMESTAMP )";
	 
	 public static final String INSERT_VEHICLE_TYPE = "INSERT INTO DPB_PGM_VEH_REL " +
													 "(ID_DPB_PGM,CDE_VEH_TYP,CDE_DPB_STS,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA)" +
													 " VALUES (?,?,?,?,?,CURRENT TIMESTAMP,CURRENT TIMESTAMP)";
	
	/* public static final String GET_DEALER_PROGRAM =	"SELECT a.ID_DPB_PGM,a.NAM_DPB_PGM,a.DES_DPB_PGM,a.DTE_DPB_PGM_START," +
														"a.DTE_DPB_PGM_END,a.IND_DPB_RBT_PMT, a.IND_DPB_COMSN_PMT,a.AMT_DPB_COMSN_PMT,a.PCT_DPB_COMSN_PMT, a.PCT_FXD_PMT," +
				"a.CDE_COMSN_BNS_SCHD, a.CDE_COMSN_BNS_TRGR, a.CDE_COMSN_PMT_SCHD, a.CDE_COMSN_PMT_TRGR, a.CDE_FXD_BNS_SCHD,a.CDE_FXD_BNS_TRGR,a.CDE_FXD_PMT_SCHD,a.CDE_FXD_PMT_TRGR,a.IND_DPB_FXD_PMT,a.IND_MAX_VAR_PMT," +
				"a.PCT_VAR_PMT,a.CDE_VAR_BNS_SCHD,a.CDE_VAR_BNS_TRGR,a.CDE_VAR_PMT_SCHD,a.CDE_VAR_PMT_TRGR,a.IND_SPL_DPB_PGM," +
				"a.ID_KPI,a.CDE_DPB_STS," +
				"b.CDE_VEH_TYP,C.ID_DPB_CND" +
				" FROM DPB_PGM A " +
				"LEFT JOIN DPB_PGM_VEH_REL B" +
				" ON A.ID_DPB_PGM=B.ID_PGM" +
				" LEFT JOIN DPB_PGM_CND_REL C ON A.ID_DPB_PGM=C.ID_DPB_PGM"+
				" WHERE A.ID_DPB_PGM = ? AND (B.IND_LDRSP_BNS_PGM = 'N' or B.IND_LDRSP_BNS_PGM IS NULL)" ;
	 */
	/* public static final String GET_DEALER_PROGRAM =	"SELECT a.ID_DPB_PGM,a.NAM_DPB_PGM,a.DES_DPB_PGM,a.DTE_DPB_PGM_START," +
														"a.DTE_DPB_PGM_END,a.IND_DPB_RBT_PMT, a.IND_DPB_COMSN_PMT,a.AMT_DPB_COMSN_PMT, a.PCT_FXD_PMT," +
														"a.CDE_COMSN_BNS_SCHD, a.CDE_COMSN_BNS_TRGR, a.CDE_COMSN_PMT_SCHD, a.CDE_COMSN_PMT_TRGR, a.CDE_FXD_BNS_SCHD,a.CDE_FXD_BNS_TRGR,a.CDE_FXD_PMT_SCHD,a.CDE_FXD_PMT_TRGR,a.IND_DPB_FXD_PMT,a.IND_MAX_VAR_PMT," +
														"a.PCT_VAR_PMT,a.CDE_VAR_BNS_SCHD,a.CDE_VAR_BNS_TRGR,a.CDE_VAR_PMT_SCHD,a.CDE_VAR_PMT_TRGR,a.IND_SPL_DPB_PGM," +
														"a.ID_KPI,a.CDE_DPB_STS," +
														"b.CDE_VEH_TYP,C.ID_DPB_CND" +
														" FROM DPB_PGM A " +
														"LEFT JOIN DPB_PGM_VEH_REL B" +
														" ON A.ID_DPB_PGM=B.ID_PGM" +
														" LEFT JOIN DPB_PGM_CND_REL C ON A.ID_DPB_PGM=C.ID_DPB_PGM"+
														" WHERE A.ID_DPB_PGM = ? AND (B.IND_LDRSP_BNS_PGM = 'N' or B.IND_LDRSP_BNS_PGM IS NULL)" ;
	 */
	 public static final String GET_DEALER_PROGRAM = "SELECT A.ID_DPB_PGM,A.NAM_DPB_PGM,A.DES_DPB_PGM,A.DTE_DPB_PGM_START,A.DTE_DPB_PGM_END,A.ID_KPI,A.IND_DPB_RBT_PMT,A.IND_DPB_COMSN_PMT,A.AMT_DPB_COMSN_PMT, "+
													" A.PCT_FXD_PMT,A.CDE_FXD_PMT_SCHD,A.CDE_FXD_PMT_PROC_INIT_TYP,A.CDE_FXD_BNS_PROC_INIT_TYP,A.IND_MAX_VAR_PMT,A.PCT_VAR_PMT,A.CDE_VAR_PMT_SCHD,A.CDE_VAR_PMT_PROC_INIT_TYP, "+
													" A.CDE_DPB_PGM_TYP,A.CDE_DPB_STS,A.IND_DPB_FXD_PMT,A.CDE_VAR_BNS_SCHD,A.CDE_VAR_BNS_PROC_INIT_TYP,A.CDE_FXD_BNS_SCHD,A.CDE_COMSN_BNS_PROC_SCHD,A.CDE_COMSN_PMT_SCHD, "+
													" A.CDE_COMSN_PROC_INIT_TYP,A.CDE_COMSN_PMT_PROC_INIT_TYP,A.DTE_INACT, B.CDE_VEH_TYP, C.ID_DPB_CND "+
													" FROM DPB_PGM A LEFT JOIN DPB_PGM_VEH_REL B ON a.ID_DPB_PGM = B.ID_DPB_PGM LEFT JOIN DPB_PGM_CND_REL C ON A.ID_DPB_PGM=C.ID_DPB_PGM WHERE B.ID_LDRSP_BNS_PGM IS NULL AND A.ID_DPB_PGM = ? WITH UR";
	public static final String RETRIEVE_VALID_ENDDATE = " SELECT max(dte_cal) as DTE_CAL FROM DPB_DAY where trim(des_day) != 'Maximum Date' and  DTE_CAL between ? " +
														" and (select max(DTE_CAL)  from DPB_DAY "+ 
														" where NUM_RETL_YR is not null and DTE_RETL_MTH_END is not null "+
														" and DTE_RETL_QTR_END is not null and "+
														" DTE_CAL != '9999-12-31') ";//Hard coded remove this condition and add DTE_CAL >= ? once all data correct in this table or increament year every end of year.
												
	public static final String INSERT_DPB_PROCESS ="INSERT INTO DPB_PROC " +
													"(ID_DPB_PGM,CDE_DPB_PROC_TYP," +
													"DTE_CAL,DTE_DPB_ACTL_PROC, ID_CREA_USER,ID_LAST_UPDT_USER,DTS_CREA, DTS_LAST_UPDT)  " +
													"values (?,?,?,?,?,?,CURRENT TIMESTAMP, CURRENT TIMESTAMP) ";
	public static final String INSERT_DPB_PROCESS_NEW ="INSERT INTO DPB_PROC " +
			"(ID_DPB_PROC,ID_DPB_PGM,CDE_DPB_PROC_TYP,DTE_CAL," +
			"DTE_DPB_ACTL_PROC, ID_CREA_USER,ID_LAST_UPDT_USER,DTS_CREA, DTS_LAST_UPDT)  " +
			"values (((select max(ID_DPB_PROC) from DPB_PROC)+1),?,?,?,?,?,?,CURRENT TIMESTAMP, CURRENT TIMESTAMP) ";
											
	 public static final String INACTIVATE_DPB_PROGRAM = "UPDATE DPB_PGM SET " +
														"DTE_INACT=?, CDE_DPB_STS =?, ID_LAST_UPDT_USER =?, DTS_LAST_UPDT = CURRENT TIMESTAMP" +
														" where ID_DPB_PGM= ?";
	 public static final String DELETE_PROCESSES = "DELETE FROM DPB_PROC WHERE ID_DPB_PGM = ? " +
	 												"AND DTE_DPB_ACTL_PROC > CURRENT DATE ";

	 public static final String DELETE_FILE_PROCESSES = "DELETE FROM DPB_PROC WHERE ID_DPB_FILE = ? " +
														"AND DTE_DPB_ACTL_PROC > CURRENT DATE ";
	 
	 public static final String DELETE_RPT_PROCESSES = "DELETE FROM DPB_PROC WHERE ID_DPB_RPT = ? " +
														"AND DTE_DPB_ACTL_PROC > CURRENT DATE ";
	/*	public static final String UPDATE_DPB_DEFINITION = "UPDATE DPB_PGM SET " +
														"NAM_DPB_PGM =?,DES_DPB_PGM =?,DTE_DPB_PGM_START =?,DTE_DPB_PGM_END =?," +
														"IND_DPB_RBT_PMT =?,IND_DPB_COMSN_PMT =?,AMT_DPB_COMSN_PMT =?,PCT_DPB_COMSN_PMT =?,PCT_FXD_PMT =?," +
				"CDE_COMSN_BNS_SCHD =?, CDE_COMSN_BNS_TRGR =?, CDE_COMSN_PMT_SCHD =?, CDE_COMSN_PMT_TRGR =?, CDE_FXD_BNS_SCHD =?,CDE_FXD_BNS_TRGR =?,CDE_FXD_PMT_SCHD =?,CDE_FXD_PMT_TRGR =?,IND_DPB_FXD_PMT =?," +
				"IND_MAX_VAR_PMT =?,PCT_VAR_PMT =?,CDE_VAR_BNS_SCHD =?,CDE_VAR_BNS_TRGR =?,CDE_VAR_PMT_SCHD =?," +
				"CDE_VAR_PMT_TRGR =?,IND_SPL_DPB_PGM =?,ID_KPI =?,CDE_DPB_STS =?,ID_LAST_UPDT_USER =? ,DTS_LAST_UPDT = CURRENT TIMESTAMP " +
				"WHERE ID_DPB_PGM = ?";
		*/
	public static final String UPDATE_DPB_DEFINITION = "UPDATE DPB_PGM SET " +
														"NAM_DPB_PGM =?,DES_DPB_PGM =?,DTE_DPB_PGM_START =?,DTE_DPB_PGM_END =?," +
														"IND_DPB_RBT_PMT =?,IND_DPB_COMSN_PMT =?,AMT_DPB_COMSN_PMT =?,PCT_FXD_PMT =?," +
														"CDE_COMSN_BNS_PROC_SCHD =?, CDE_COMSN_PROC_INIT_TYP =?, CDE_COMSN_PMT_SCHD =?, CDE_COMSN_PMT_PROC_INIT_TYP =?, CDE_FXD_BNS_SCHD =?,CDE_FXD_BNS_PROC_INIT_TYP =?,CDE_FXD_PMT_SCHD =?,CDE_FXD_PMT_PROC_INIT_TYP =?,IND_DPB_FXD_PMT =?," +
														"IND_MAX_VAR_PMT =?,PCT_VAR_PMT =?,CDE_VAR_BNS_SCHD =?,CDE_VAR_BNS_PROC_INIT_TYP =?,CDE_VAR_PMT_SCHD =?," +
														"CDE_VAR_PMT_PROC_INIT_TYP =?, CDE_DPB_PGM_TYP =?,ID_KPI =?,CDE_DPB_STS =?,ID_LAST_UPDT_USER =? ,DTS_LAST_UPDT = CURRENT TIMESTAMP " +
														"WHERE ID_DPB_PGM = ?";
	
	
	 public static final String RETRIEVE_DLR_LIST = "SELECT ID_DPB_PGM,NAM_DPB_PGM,DTE_DPB_PGM_START,DTE_DPB_PGM_END," +
													"ID_KPI,CDE_DPB_STS ,CDE_DPB_PGM_TYP" +
													" FROM DPB_PGM ORDER BY ID_DPB_PGM asc WITH UR";
	
	public static final String DELETE_VEH_TYPES = "DELETE FROM  DPB_PGM_VEH_REL WHERE ID_DPB_PGM=?  AND ID_LDRSP_BNS_PGM IS NULL ";
	 
	public static final String DELETE_CONDITIONS = "DELETE FROM  DPB_PGM_CND_REL WHERE ID_DPB_PGM=?";
	
	public static final String INSERT_DPB_CONDITION = "INSERT INTO DPB_PGM_CND_REL (ID_DPB_PGM,ID_DPB_CND,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA) VALUES (?,?,?,?,CURRENT TIMESTAMP,CURRENT TIMESTAMP)"; 
	
	//Start Condition definition related Query 
																	//,,,,,,,,ID_CREA_USER,,,DTS_CREA 
	public static final String RETRIVE_FROM_DPB_CONDITION = " SELECT ID_DPB_CND,NAM_DPB_CND,DES_DPB_CND,CDE_DPB_CND,NAM_DPB_VAR,TXT_DPB_CHK_VAL, CDE_DPB_CND_TYP,TXT_DPB_REG_EX,CDE_DPB_STS from DPB_CND  where ID_DPB_CND = ? WITH UR";
	
	public static final String INSERT_INTO_DPB_CONDITION = 		" INSERT INTO DPB_CND (" +
														   		" NAM_DPB_CND,DES_DPB_CND,CDE_DPB_CND,NAM_DPB_VAR,TXT_DPB_CHK_VAL, CDE_DPB_CND_TYP,TXT_DPB_REG_EX,CDE_DPB_STS," +
														   		" ID_CREA_USER,DTS_CREA,ID_LAST_UPDT_USER,DTS_LAST_UPDT) " +
														   		" VALUES(?,?,?,?,?,?,?,?,?, CURRENT TIMESTAMP,?,CURRENT TIMESTAMP)";
	
	public static final String UPDATE_ON_DPB_CONDITION   = 		" UPDATE  DPB_CND SET NAM_DPB_CND = ?,DES_DPB_CND = ?," +
														   		" CDE_DPB_CND = ?,NAM_DPB_VAR = ?,TXT_DPB_CHK_VAL = ?," +
														   		" CDE_DPB_CND_TYP = ?,TXT_DPB_REG_EX = ?,CDE_DPB_STS = ?," +
														   		" ID_LAST_UPDT_USER = ?,DTS_LAST_UPDT = CURRENT TIMESTAMP " +
														   		" where ID_DPB_CND = ? " ;
	public static final String INACTIVE_CONDITION = 	"UPDATE DPB_CND SET CDE_DPB_STS = ? , ID_LAST_UPDT_USER =?, " +
														"DTS_LAST_UPDT =  CURRENT TIMESTAMP  WHERE ID_DPB_CND = ?";
	
	public static final String RETRIVE_LIST_FROM_DPB_CONDITION = " SELECT ID_DPB_CND,NAM_DPB_CND,DES_DPB_CND,CDE_DPB_CND,NAM_DPB_VAR,TXT_DPB_CHK_VAL, CDE_DPB_CND_TYP,TXT_DPB_REG_EX,CDE_DPB_STS from DPB_CND ORDER BY ID_DPB_CND WITH UR";
			
	public static final String VALIDATE_CONDITION = "WITH PGMACTIVE(ID_DPB_CND) AS " +
														"( SELECT CND.ID_DPB_CND FROM DPB_CND CND JOIN DPB_PGM_CND_REL PR" +
														" ON PR.ID_DPB_CND = CND.ID_DPB_CND JOIN DPB_PGM PG ON PR.ID_DPB_PGM = PG.ID_DPB_PGM AND PG.CDE_DPB_STS = 'A' )" +
														",VCLBLKMAPPEDCND (ID_DPB_CND) AS (SELECT DISTINCT CND.ID_DPB_CND FROM DPB_CND CND , VEH_TYP_CND_REL V ," +
														" VEH_TYP_CND_REL B WHERE CND.ID_DPB_CND = V.ID_DPB_CND OR CND.ID_DPB_CND = B.ID_DPB_CND)" +
														" SELECT * FROM PGMACTIVE UNION SELECT * FROM VCLBLKMAPPEDCND ";		
		//End of Condition definition related Query
		
	/**
	 * Report Content Queries Start 
	 */	
		public static final String CREATE_REPORT_CONTENT_DEF_QUERY = "INSERT INTO DPB_RPT_CTNT (NAM_DPB_RPT_CTNT, DES_DPB_RPT_CTNT, TXT_DPB_RPT_CTNT, CNT_DPB_RPT_QRY_RSLT_LPP, CDE_DPB_STS, ID_CREA_USER, ID_LAST_UPDT_USER, DTS_LAST_UPDT, DTS_CREA) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
		public static final String UPDATE_REPORT_CONTENT_DEF_QUERY = "UPDATE DPB_RPT_CTNT SET NAM_DPB_RPT_CTNT = ?, DES_DPB_RPT_CTNT = ?, TXT_DPB_RPT_CTNT = ?, CNT_DPB_RPT_QRY_RSLT_LPP = ? , CDE_DPB_STS=?, ID_LAST_UPDT_USER =?,DTS_LAST_UPDT =CURRENT TIMESTAMP WHERE ID_DPB_RPT_CTNT = ?";
		public static final String CHECK_INACTIVE_RC_STATUS = "SELECT CDE_DPB_STS from DPB_RPT WHERE ID_DPB_RPT in (SELECT ID_DPB_RPT FROM DPB_RPT_QRY_CTNT_REL where ID_DPB_RPT_CTNT=?)";
		public static final String UPDATE_REPORT_CONTENT_INACT_QUERY = "UPDATE DPB_RPT_CTNT SET CDE_DPB_STS=?, ID_LAST_UPDT_USER=? WHERE ID_DPB_RPT_CTNT=?";
		public static final String RETRIEVE_REPORT_CONTENT_DEF_LIST = "SELECT ID_DPB_RPT_CTNT, NAM_DPB_RPT_CTNT, CNT_DPB_RPT_QRY_RSLT_LPP, CDE_DPB_STS FROM DPB_RPT_CTNT order by ID_DPB_RPT_CTNT";
		public static final String RETRIEVE_REPORT_CONTENT_EDIT_QUERY = "SELECT ID_DPB_RPT_CTNT, NAM_DPB_RPT_CTNT, DES_DPB_RPT_CTNT, TXT_DPB_RPT_CTNT, CNT_DPB_RPT_QRY_RSLT_LPP, CDE_DPB_STS FROM DPB_RPT_CTNT WHERE ID_DPB_RPT_CTNT=?";
	/**
	 * Report Content Queries end 
	 */
		
		/**
		 * LDRSP Bonus Queries Start 
		 */
		public static final String INSERT_LDRSP_BNS_PGM = "INSERT INTO LDRSP_BNS_PGM  (NAM_LDRSP_BNS,DTE_LDRSP_BNS_RTL_STA,DTE_LDRSP_BNS_RTL_END,DTE_LDRSP_BNS_ACRL_STA,DTE_LDRSP_BNS_ACRL_END,AMT_LDRSP_BNS_BUS_RSRV,PCT_LDRSP_BNS_BUS_RSRV,AMT_LDRSP_BNS_PER_UNIT_CALC,AMT_LDRSP_BNS_PER_UNIT_EDIT,CDE_DPB_STS,DTE_LDRSP_BNS_PROC,AMT_DPB_UNUSED,CNT_LDRSP_BNS_VEH,AMT_DPB_LDRSP_BNS,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA )VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
		public static final String INSERT_DPB_PGM_VEH_MAP = "INSERT INTO DPB_PGM_VEH_REL (ID_LDRSP_BNS_PGM,CDE_VEH_TYP,CDE_DPB_STS,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA)  VALUES(?,?,?,?,?, CURRENT TIMESTAMP, CURRENT TIMESTAMP) ";//CHANGE
		//public static final String RETRIEVE_LDRSPS_RTL_CAL = "SELECT ID_DAY FROM DPB_RTL_CAL_DY_DIM WHERE DTE_CAL = ? ";
		public static final String INSERT_LDRSP_DPB_PROC = "INSERT INTO DPB_PROC (DTE_CAL,DTE_DPB_ACTL_PROC, ID_LDRSP_BNS_PGM, CDE_DPB_PROC_TYP,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA) VALUES (?,?,?,?,?,?, CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
		public static final String RETRIEVE_LDRSP_RTL_CAL = "SELECT DTE_CAL FROM DPB_DAY WHERE DTE_CAL = ? "; //CHANGE
		public static final String UPDATE_LDRSP_BNS_PGM = "UPDATE  LDRSP_BNS_PGM SET NAM_LDRSP_BNS=?,DTE_LDRSP_BNS_ACRL_STA=?,DTE_LDRSP_BNS_ACRL_END=?,DTE_LDRSP_BNS_RTL_STA=?,DTE_LDRSP_BNS_RTL_END=?,AMT_LDRSP_BNS_BUS_RSRV=?,PCT_LDRSP_BNS_BUS_RSRV =? ,AMT_LDRSP_BNS_PER_UNIT_CALC = ?,AMT_LDRSP_BNS_PER_UNIT_EDIT =?,CDE_DPB_STS  =?,DTE_LDRSP_BNS_PROC =?,AMT_DPB_UNUSED=?,CNT_LDRSP_BNS_VEH=?,AMT_DPB_LDRSP_BNS =? ,ID_LAST_UPDT_USER =?,DTS_LAST_UPDT = CURRENT TIMESTAMP where ID_LDRSP_BNS_PGM = ? ";
		public static final String DELETE_DPB_PGM_VEH_MAP = "DELETE FROM DPB_PGM_VEH_REL where ID_LDRSP_BNS_PGM = ?";//CHANGE
		public static final String RETRIEVE_DPB_PGM_VEH_MAP = "SELECT CDE_VEH_TYP FROM  DPB_PGM_VEH_REL where ID_LDRSP_BNS_PGM = ? "; //CHANGE
		public static final String RETRIEVE_LDRSP_BNS_ACRL= "SELECT AMT_DPB_LDRSP_BNS FROM LDRSP_BNS_ACRL where (DTE_LDRSP_BNS_ACRL_POST between ? and ?) AND ";
		public static final String RETRIEVE_AMT_LDRSP_BNS= "SELECT AMT_DPB_LDRSP_BNS FROM LDRSP_BNS_ACRL where (DTE_LDRSP_BNS_ACRL_POST between ? and ?)";
		public static final String RETRIEVE_LDRSP_BNS_LIST =  /*"with BNS_PGM (LEADERSHIP_BONUS_PROGRAM_IDENTIFIER,NAM_LDRSP_BNS,DTE_LDRSP_BNS_STA,DTE_LDRSP_BNS_END,AMT_BUS_RSRV,PCT_BUS_RSRV,"+ 
															  "AMT_PER_UNIT_CALC, AMT_PER_UNIT_EDIT,CDE_LDRSP_PGM_STS,PROC_DATE) as (SELECT LEADERSHIP_BONUS_PROGRAM_IDENTIFIER,NAM_LDRSP_BNS,DTE_LDRSP_BNS_STA,"+
															  "DTE_LDRSP_BNS_END,AMT_BUS_RSRV, PCT_BUS_RSRV, AMT_PER_UNIT_CALC, AMT_PER_UNIT_EDIT, CDE_LDRSP_PGM_STS, PROC_DATE from LDRSP_BNS_PGM ),"+
															  "accr_Year (DTE_LDRSP_BNS_ACRL_POST,AMT_DPB_LDRSP_BNS) as (SELECT accr.DTE_LDRSP_BNS_ACRL_POST,accr.AMT_DPB_LDRSP_BNS "+
															  "FROM LDRSP_BNS_ACRL accr, BNS_PGM pgm WHERE accr.DTE_LDRSP_BNS_ACRL_POST between pgm.DTE_LDRSP_BNS_STA and pgm.DTE_LDRSP_BNS_END "+ 
															  "and accr.CDE_VEH_TYP in(SELECT CDE_VEH_TYP FROM DPB_PGM_VEH_MAP,BNS_PGM pgm  WHERE ID_LDRSP_BNS_PGM = pgm. LEADERSHIP_BONUS_PROGRAM_IDENTIFIER AND IND_LDRSP_BNS_PGM = 'Y')" +
															  "group by accr.DTE_LDRSP_BNS_ACRL_POST,accr.AMT_DPB_LDRSP_BNS),YEAR_AMOUNT (DTE_LDRSP_BNS_STA,DTE_LDRSP_BNS_END,unusedamount) as ("+
															  "select pgm.DTE_LDRSP_BNS_STA,pgm.DTE_LDRSP_BNS_END,sum(AMT_DPB_LDRSP_BNS) from accr_Year yr, BNS_PGM pgm where yr.DTE_LDRSP_BNS_ACRL_POST between " +
															  "pgm.DTE_LDRSP_BNS_STA and pgm.DTE_LDRSP_BNS_END group by pgm.DTE_LDRSP_BNS_STA,pgm.DTE_LDRSP_BNS_END) "+
															  "select pgm.*,amt.unusedamount,amt.unusedamount-pgm.AMT_BUS_RSRV as LDRSP_BNS_AMT  from year_amount amt, BNS_PGM pgm where " +
															  "amt.DTE_LDRSP_BNS_STA=pgm.DTE_LDRSP_BNS_STA and amt.DTE_LDRSP_BNS_END=pgm.DTE_LDRSP_BNS_END";*/
															"SELECT  ID_LDRSP_BNS_PGM, NAM_LDRSP_BNS,DTE_LDRSP_BNS_RTL_STA, DTE_LDRSP_BNS_RTL_END, AMT_LDRSP_BNS_BUS_RSRV," +
															" PCT_LDRSP_BNS_BUS_RSRV, AMT_LDRSP_BNS_PER_UNIT_CALC, AMT_LDRSP_BNS_PER_UNIT_EDIT, CDE_DPB_STS,  DTE_LDRSP_BNS_PROC,"+
															"AMT_DPB_UNUSED, CNT_LDRSP_BNS_VEH, AMT_DPB_LDRSP_BNS FROM LDRSP_BNS_PGM order by ID_LDRSP_BNS_PGM asc";  
		
		public static final String RETRIEVE_LDRSP_BNS_PGM= "SELECT ID_LDRSP_BNS_PGM,NAM_LDRSP_BNS,DTE_LDRSP_BNS_RTL_STA,DTE_LDRSP_BNS_RTL_END,DTE_LDRSP_BNS_ACRL_STA,DTE_LDRSP_BNS_ACRL_END,AMT_LDRSP_BNS_BUS_RSRV,PCT_LDRSP_BNS_BUS_RSRV,"+ 
															"AMT_LDRSP_BNS_PER_UNIT_CALC, AMT_LDRSP_BNS_PER_UNIT_EDIT,CDE_DPB_STS,DTE_LDRSP_BNS_PROC,AMT_DPB_UNUSED, CNT_LDRSP_BNS_VEH, AMT_DPB_LDRSP_BNS,DTE_INACT  "+
															"from LDRSP_BNS_PGM WHERE ID_LDRSP_BNS_PGM=?";
		public static final String UPDATE_LDRSP_BNS_PGMS = "UPDATE  LDRSP_BNS_PGM SET CDE_DPB_STS =?,DTE_INACT =? ,ID_LAST_UPDT_USER =?,DTS_LAST_UPDT = CURRENT TIMESTAMP where ID_LDRSP_BNS_PGM = ? ";
		
		public static final String DELETE_PROCESSES_LDRSP = "DELETE FROM DPB_PROC WHERE ID_LDRSP_BNS_PGM = ? " +
				 											"AND DTE_DPB_ACTL_PROC > CURRENT DATE"; // Chinmaya	
		public static final String VALIDATE_LDRSP_PROGRAM = "SELECT  NAM_LDRSP_BNS,DTE_LDRSP_BNS_RTL_STA FROM LDRSP_BNS_PGM WHERE NAM_LDRSP_BNS = ? AND DTE_LDRSP_BNS_RTL_STA = ?";
		public static final String ACC_ID_MAP_LST =		/*"with VEH_MAP(CDE_VEH_TYP, ID_LDRSP_BNS_PGM, IND_LDRSP_BNS_PGM) as ( "+
														  	"select CDE_VEH_TYP, ID_PGM, IND_LDRSP_BNS_PGM from DPB_PGM_VEH_REL order by CDE_VEH_TYP,ID_PGM),"+
														    "ldr (CDE_VEH_TYP, ID_LDRSP_BNS_PGM, IND_LDRSP_BNS_PGM, NAM_LDRSP_BNS) as ( "+
														  	"select veh.CDE_VEH_TYP, veh.ID_LDRSP_BNS_PGM, veh.IND_LDRSP_BNS_PGM, pgm.NAM_LDRSP_BNS from LDRSP_BNS_PGM pgm, VEH_MAP veh "+
														  	"where pgm.ID_LDRSP_BNS_PGM in (veh.ID_LDRSP_BNS_PGM ) and veh.IND_LDRSP_BNS_PGM = 'Y' and pgm.CDE_DPB_STS = 'A'), "+
														  	"pgm (CDE_VEH_TYP, ID_LDRSP_BNS_PGM, IND_LDRSP_BNS_PGM, NAM_LDRSP_BNS) as (select veh.CDE_VEH_TYP, veh.ID_LDRSP_BNS_PGM, veh.IND_LDRSP_BNS_PGM, pgm.NAM_DPB_PGM "+
														  	"from DPB_PGM pgm, VEH_MAP veh where pgm.ID_DPB_PGM in (veh.ID_LDRSP_BNS_PGM ) and veh.IND_LDRSP_BNS_PGM = 'N' and pgm.CDE_DPB_STS = 'A'), "+
														  	"final (CDE_VEH_TYP, ID_LDRSP_BNS_PGM, IND_LDRSP_BNS_PGM, NAM_LDRSP_BNS,ID_DPB_COFICO_ACCT, CDE_DPB_DEFN_STS) as ( "+
														    "select ldr.CDE_VEH_TYP, ldr.ID_LDRSP_BNS_PGM, ldr.IND_LDRSP_BNS_PGM, ldr.NAM_LDRSP_BNS,acct.ID_DPB_CFC_ACCT, acct.CDE_DPB_STS "+
														    "from ldr ldr left outer join DPB_PGM_CFC_ACCT_REL acct on acct.ID_PGM = ldr.ID_LDRSP_BNS_PGM "+
														  	"and acct.IND_LDRSP_BNS_PGM = ldr.IND_LDRSP_BNS_PGM and acct.CDE_VEH_TYP = ldr.CDE_VEH_TYP "+
														    "union select pgm.CDE_VEH_TYP, pgm.ID_LDRSP_BNS_PGM, pgm.IND_LDRSP_BNS_PGM, pgm.NAM_LDRSP_BNS,acct.ID_DPB_CFC_ACCT, acct.CDE_DPB_STS "+
														    "from pgm pgm left outer join DPB_PGM_CFC_ACCT_REL acct on acct.ID_PGM = pgm.ID_LDRSP_BNS_PGM "+
														  	"and acct.IND_LDRSP_BNS_PGM = pgm.IND_LDRSP_BNS_PGM and acct.CDE_VEH_TYP = pgm.CDE_VEH_TYP) select * from final order by CDE_VEH_TYP";*/
															" with VEH_MAP(ID_VEH_REL, CDE_VEH_TYP, ID_PGM, ID_LDR_PGM, ID_ACCT_CR, ID_ACCT_DT, STATUS) as ( "+
														    " select ID_DPB_PGM_VEH_REL,CDE_VEH_TYP, ID_DPB_PGM, ID_LDRSP_BNS_PGM, ID_DPB_CFC_ACCT_CR, "+
														    " ID_DPB_CFC_ACCT_DT, CDE_DPB_STS "+
														    " from DPB_PGM_VEH_REL "+
														    " order by ID_DPB_PGM_VEH_REL), "+
														    " final (ID_VEH_REL, CDE_VEH_TYP, ID_ACCT_CR, ID_ACCT_DT, NAM_LDRSP_BNS, STATUS) as ( "+
														    " select veh.ID_VEH_REL, veh.CDE_VEH_TYP, veh.ID_ACCT_CR, veh.ID_ACCT_DT, "+
														    " pgm.NAM_LDRSP_BNS, veh.STATUS "+
														    " from VEH_MAP veh, LDRSP_BNS_PGM pgm "+
														    " where veh.ID_PGM is null and pgm.ID_LDRSP_BNS_PGM = veh.ID_LDR_PGM and pgm.CDE_DPB_STS = 'A' "+
														    " union "+
														    " select veh.ID_VEH_REL, veh.CDE_VEH_TYP, veh.ID_ACCT_CR, veh.ID_ACCT_DT, "+
														    " pgm.NAM_DPB_PGM, veh.STATUS "+
														    " from VEH_MAP veh, DPB_PGM pgm "+
														    " where veh.ID_PGM is not null and pgm.ID_DPB_PGM = veh.ID_PGM and pgm.CDE_DPB_STS = 'A') "+
														    " select ID_VEH_REL, CDE_VEH_TYP, ID_ACCT_CR, ID_ACCT_DT, NAM_LDRSP_BNS, STATUS from Final order by CDE_VEH_TYP,NAM_LDRSP_BNS ";
	 
	public static final String INSERT_UPDATE_ACC_ID_MAP = /*"	MERGE INTO DPB_PGM_CFC_ACCT_REL AS ACCT_MAP USING (VALUES (CAST(? AS CHAR), CAST(? AS INTEGER), CAST(? AS INTEGER), "+
															"CAST(? AS CHAR), CAST(? AS CHAR), CAST(? AS CHAR(8)), CAST(? AS CHAR(8)),CAST(current timestamp AS TIMESTAMP), "+
															"CAST(current timestamp AS TIMESTAMP))) AS ACCT_MAP_NEW (CDE_VEH_TYP, ID_DPB_PGM, ID_DPB_CFC_ACCT, "+
															"IND_LDRSP_BNS_PGM, CDE_DPB_STS, ID_CREA_USER, ID_LAST_UPDT_USER,DTS_LAST_UPDT, DTS_CREA) "+
															"ON ACCT_MAP.CDE_VEH_TYP = ACCT_MAP_NEW.CDE_VEH_TYP AND ACCT_MAP.ID_PGM= ACCT_MAP_NEW.ID_DPB_PGM AND "+
															"ACCT_MAP.IND_LDRSP_BNS_PGM = ACCT_MAP_NEW.IND_LDRSP_BNS_PGM WHEN MATCHED THEN "+
															"UPDATE SET ACCT_MAP.ID_DPB_CFC_ACCT = ACCT_MAP_NEW.ID_DPB_CFC_ACCT, ACCT_MAP.CDE_DPB_STS = ACCT_MAP_NEW.CDE_DPB_STS, "+
															"ACCT_MAP.ID_LAST_UPDT_USER = ACCT_MAP_NEW.ID_LAST_UPDT_USER, ACCT_MAP.DTS_LAST_UPDT = ACCT_MAP_NEW.DTS_LAST_UPDT WHEN NOT MATCHED THEN  "+
															"INSERT(CDE_VEH_TYP, ID_PGM, ID_DPB_CFC_ACCT,IND_LDRSP_BNS_PGM, CDE_DPB_STS, ID_CREA_USER, "+
															"ID_LAST_UPDT_USER, DTS_LAST_UPDT,DTS_CREA) VALUES(ACCT_MAP_NEW.CDE_VEH_TYP, ACCT_MAP_NEW.ID_DPB_PGM, "+
															"ACCT_MAP_NEW.ID_DPB_CFC_ACCT,ACCT_MAP_NEW.IND_LDRSP_BNS_PGM, ACCT_MAP_NEW.CDE_DPB_STS, "+
															"ACCT_MAP_NEW.ID_CREA_USER, ACCT_MAP_NEW.ID_LAST_UPDT_USER, ACCT_MAP_NEW.DTS_LAST_UPDT,DTS_CREA) ELSE IGNORE ";*/
															" UPDATE DPB_PGM_VEH_REL set ID_DPB_CFC_ACCT_CR = ?,ID_DPB_CFC_ACCT_DT = ?, CDE_DPB_STS = ?, DTS_LAST_UPDT = current timestamp, "+
															" ID_LAST_UPDT_USER = ? where ID_DPB_PGM_VEH_REL = ? ";

	public static final String VALIDATE_UNUSED_AMOUNT = " with CALC(ID_DPB_UNBLK_VEH,ID_DLR,DTE_CAL,AMT_DPB_BNS_CALC,AMT_DPB_MAX_BNS_ELG ) as "+
														" (select ID_DPB_UNBLK_VEH,ID_DLR,DTE_CAL,AMT_DPB_BNS_CALC,AMT_DPB_MAX_BNS_ELG "+
														" from DPB_CALC WHERE DTE_CAL BETWEEN ? and ? ), "+
														" Vista(ID_DPB_UNBLK_VEH,ID_DLR,DTE_CAL,AMT_DPB_BNS_CALC,AMT_DPB_MAX_BNS_ELG) as "+
														" (select vista.ID_DPB_UNBLK_VEH,vista.ID_DLR,cal.DTE_CAL,cal.AMT_DPB_BNS_CALC,cal.AMT_DPB_MAX_BNS_ELG "+
														" from DPB_UNBLK_VEH vista,CALC cal where vista.ID_DPB_UNBLK_VEH = cal.ID_DPB_UNBLK_VEH), "+
														" SUM_AMT(SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_MAX_BNS_ELG ) as "+
														" (select sum(AMT_DPB_BNS_CALC),sum(AMT_DPB_MAX_BNS_ELG)  from Vista), "+
														" UNEARNED_AMT(UNEARNED_AMOUNT) AS "+
														" (SELECT (case when (SUM_AMT_DPB_BNS_CALC != 0.0 AND SUM_AMT_DPB_MAX_BNS_ELG != 0.0 ) then "+
														" (SUM_AMT_DPB_MAX_BNS_ELG - SUM_AMT_DPB_BNS_CALC) else 0  end)  FROM  SUM_AMT) "+
														" SELECT UNEARNED_AMOUNT FROM UNEARNED_AMT ";
	/**
	 * LDRSP Bonus Queries End 
	 */

	/**
	 * Report Definition Queries start
	 */
		public static final String CREATE_REPORT_DEF_QUERY = "INSERT INTO DPB_RPT (NAM_DPB_RPT, DES_DPB_RPT, QTY_DPB_SUB_RPT, CDE_DPB_SCHD, CDE_DPB_PROC_INIT_TYP, CDE_DPB_STS, ID_CREA_USER, ID_LAST_UPDT_USER, QTY_DPB_RPT_LPP, DTS_LAST_UPDT, DTS_CREA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
		public static final String UPDATE_REPORT_DEF_QUERY = "UPDATE DPB_RPT SET NAM_DPB_RPT = ?, DES_DPB_RPT = ?, QTY_DPB_SUB_RPT=?, CDE_DPB_SCHD=?, CDE_DPB_PROC_INIT_TYP=?, CDE_DPB_STS=?, ID_LAST_UPDT_USER =?, QTY_DPB_RPT_LPP =?, DTS_LAST_UPDT =CURRENT TIMESTAMP WHERE ID_DPB_RPT = ?";
		public static final String UPDATE_REPORT_INACTIVE_QUERY = "UPDATE DPB_RPT SET CDE_DPB_STS=?, ID_LAST_UPDT_USER=?, DTE_INACT=?, DTS_LAST_UPDT=CURRENT TIMESTAMP WHERE ID_DPB_RPT=?";
		public static final String CREATE_QCR_DEF_QUERY = "INSERT INTO DPB_RPT_QRY_CTNT_REL (ID_DPB_RPT_CTNT, ID_DPB_RPT_QRY, ID_DPB_RPT, NUM_DPB_RPT_SEQ, ID_CREA_USER, ID_LAST_UPDT_USER, ID_DPB_RPT_CTNT_FTR, DTS_LAST_UPDT, DTS_CREA) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
		public static final String CREATE_QCR_DEF_QUERY_FTR = "INSERT INTO DPB_RPT_QRY_CTNT_REL (ID_DPB_RPT_CTNT, ID_DPB_RPT_QRY, ID_DPB_RPT, NUM_DPB_RPT_SEQ, ID_CREA_USER, ID_LAST_UPDT_USER, DTS_LAST_UPDT, DTS_CREA) VALUES (?, ?, ?, ?, ?, ?, CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
		public static final String UPDATE_QCR_DEF_QUERY = "DELETE FROM DPB_RPT_QRY_CTNT_REL WHERE ID_DPB_RPT=?";
		public static final String RETRIEVE_REPORT_DEF_EDIT_QUERY = "select rpt.ID_DPB_RPT, rpt.NAM_DPB_RPT, rpt.DES_DPB_RPT, rpt.QTY_DPB_SUB_RPT, rpt.CDE_DPB_SCHD, rpt.CDE_DPB_PROC_INIT_TYP, rpt.QTY_DPB_RPT_LPP, rpt.CDE_DPB_STS, rel.ID_DPB_RPT_QRY_CTNT_REL, rel.ID_DPB_RPT_CTNT, rel.ID_DPB_RPT_QRY, rel.NUM_DPB_RPT_SEQ, rel.ID_DPB_RPT_CTNT_FTR, ctnt.NAM_DPB_RPT_CTNT, qry.NAM_DPB_RPT_QRY, ctntFtr.NAM_DPB_RPT_CTNT as FOOTER from DPB_RPT rpt left outer join DPB_RPT_QRY_CTNT_REL rel on rpt.ID_DPB_RPT = rel.ID_DPB_RPT left outer join DPB_RPT_CTNT ctnt on rel.ID_DPB_RPT_CTNT = ctnt.ID_DPB_RPT_CTNT left outer join DPB_RPT_QRY qry on rel.ID_DPB_RPT_QRY = qry.ID_DPB_RPT_QRY left outer join DPB_RPT_CTNT ctntFtr on rel.ID_DPB_RPT_CTNT_FTR = ctntFtr.ID_DPB_RPT_CTNT where rpt.ID_DPB_RPT = ? order by rel.NUM_DPB_RPT_SEQ with ur";
		public static final String RETRIEVE_REPORT_DEF_LIST = "SELECT ID_DPB_RPT, NAM_DPB_RPT, QTY_DPB_SUB_RPT, CDE_DPB_SCHD, CDE_DPB_PROC_INIT_TYP, QTY_DPB_RPT_LPP, CDE_DPB_STS FROM DPB_RPT order by ID_DPB_RPT";		
		public static final String RETRIEVE_REPORT_CONTENT = "SELECT ID_DPB_RPT_CTNT, NAM_DPB_RPT_CTNT from DPB_RPT_CTNT WHERE CDE_DPB_STS='A'";
		public static final String RETRIEVE_REPORT_QUERY = "SELECT ID_DPB_RPT_QRY, NAM_DPB_RPT_QRY  from DPB_RPT_QRY WHERE CDE_DPB_STS='A'";
		/**
	 * Report Definition Queries start
	 */
		
		public static final String processCalDefQuery = " 	SELECT DES_DPB_PROC_TYP as CDE_DPB_PROC_TYP,ID_DPB_PROC,  "+
														"	rtl_clndr.DTE_CAL as DTE_CAL, (select odt.DTE_CAL from "+  
														"	DPB_DAY odt where odt.DTE_CAL= DTE_PROC_OVRD ) as OVRD_PROC_DTE "+  
														"	FROM DPB_PROC proc LEFT JOIN DPB_DAY rtl_clndr "+ 
														"	ON proc.DTE_CAL= rtl_clndr.DTE_CAL LEFT JOIN DPB_PROC_TYP ptype "+ 
														"	ON proc.CDE_DPB_PROC_TYP= ptype.CDE_DPB_PROC_TYP "+ 
														"	WHERE rtl_clndr.DTE_CAL between rtl_clndr.DTE_RETL_MTH_BEG and "+  
														"	rtl_clndr.DTE_RETL_MTH_END and month(rtl_clndr.DTE_CAL) = month(current date ) "+ 
														"	and year(current date)=year(rtl_clndr.DTE_CAL) and proc.CDE_DPB_PROC_TYP not in ('AJ') With UR " ;
		
		public static final String retrieveFileProcessDefQuery = " with PROCESS (ID_DPB_PROC,ID_DPB_FILE,ID_DPB_PGM,ID_DPB_RPT,ID_LDRSP_BNS_PGM,CDE_DPB_PROC_TYP, "+     
																 "	DTE_CAL, TXT_RSN_PROC_UPD, ID_OVRD_PROC_DTE, CDE_DPB_OVRD_PROC_INIT_TYP, TME_OVRD_PROC) as ( "+    
																 "	select proc.ID_DPB_PROC, "+    
																 "	proc.ID_DPB_FILE, "+  
																 "	proc.ID_DPB_PGM, "+  
																 "	proc.ID_DPB_RPT, "+	
																 "  proc.ID_LDRSP_BNS_PGM,	"+
																 "	proc.CDE_DPB_PROC_TYP, "+     
																 "	(select rtlDim.DTE_CAL from DPB_DAY rtlDim where rtlDim.DTE_CAL = proc.DTE_CAL), "+    
																 "	proc.TXT_RSN_PROC_UPD, (select rtlDim.DTE_CAL from DPB_DAY rtlDim where rtlDim.DTE_CAL = proc.DTE_PROC_OVRD), "+   
																 "	proc.CDE_DPB_OVRD_PROC_INIT_TYP, proc.TME_PROC_OVRD "+    
																 "	from DPB_PROC proc "+    
																 "	where proc.ID_DPB_PROC = ? "+
																 "	), "+   
																 "	PROC_LOG (ID_PROC_LOG, ID_DPB_PROC, RECCOUNT) as ( "+    
																 "	select max(log.ID_DPB_PROC_LOG), log.ID_DPB_PROC, count(log.ID_DPB_PROC) "+    
																 "	from DPB_PROC_LOG log, PROCESS proc "+    
																 "	where log.ID_DPB_PROC = proc.ID_DPB_PROC "+    
																 "	group by log.ID_DPB_PROC) "+   
																 "	select proc.ID_DPB_PROC, COALESCE(proc.ID_DPB_FILE,0) as ID_DPB_FILE,COALESCE(proc.ID_DPB_PGM,0) as ID_DPB_PGM ,COALESCE(proc.ID_DPB_RPT,0) as ID_DPB_RPT, COALESCE(proc.ID_LDRSP_BNS_PGM,0) as ID_LDRSP_BNS_PGM , proc.CDE_DPB_PROC_TYP, "+     
																 "	proc.DTE_CAL, proc.TXT_RSN_PROC_UPD, proc.ID_OVRD_PROC_DTE, "+    
																 "	CASE WHEN proc.CDE_DPB_OVRD_PROC_INIT_TYP is not null THEN proc.CDE_DPB_OVRD_PROC_INIT_TYP ELSE "+    
																 "	CASE WHEN proc.CDE_DPB_PROC_TYP in ('PF','AJ') THEN (select file.CDE_DPB_PROC_INIT_TYP from DPB_FILE file where file.ID_DPB_FILE = proc.ID_DPB_FILE and file.CDE_DPB_STS = 'A') ELSE "+   
																 "	CASE WHEN proc.CDE_DPB_PROC_TYP = 'FB' THEN (select pgm.CDE_FXD_BNS_PROC_INIT_TYP from DPB_PGM pgm where pgm.ID_DPB_PGM = proc.ID_DPB_PGM and pgm.CDE_DPB_STS = 'A') ELSE "+   
																 "	CASE WHEN proc.CDE_DPB_PROC_TYP = 'VB' THEN (select pgm.CDE_VAR_BNS_PROC_INIT_TYP from DPB_PGM pgm where pgm.ID_DPB_PGM = proc.ID_DPB_PGM and pgm.CDE_DPB_STS = 'A') ELSE "+   
																 "	CASE WHEN proc.CDE_DPB_PROC_TYP = 'CB' THEN (select pgm.CDE_COMSN_PROC_INIT_TYP from DPB_PGM pgm where pgm.ID_DPB_PGM = proc.ID_DPB_PGM and pgm.CDE_DPB_STS = 'A') ELSE "+   
																 "	CASE WHEN proc.CDE_DPB_PROC_TYP = 'LB' THEN 'A' ELSE "+   
																 "	CASE WHEN proc.CDE_DPB_PROC_TYP = 'FP' THEN (select pgm.CDE_FXD_PMT_PROC_INIT_TYP from DPB_PGM pgm where pgm.ID_DPB_PGM = proc.ID_DPB_PGM and pgm.CDE_DPB_STS = 'A') ELSE "+   
																 "	CASE WHEN proc.CDE_DPB_PROC_TYP = 'VP' THEN (select pgm.CDE_VAR_PMT_PROC_INIT_TYP from DPB_PGM pgm where pgm.ID_DPB_PGM = proc.ID_DPB_PGM and pgm.CDE_DPB_STS = 'A') ELSE "+   
																 "	CASE WHEN proc.CDE_DPB_PROC_TYP = 'CP' THEN (select pgm.CDE_COMSN_PMT_PROC_INIT_TYP from DPB_PGM pgm where pgm.ID_DPB_PGM = proc.ID_DPB_PGM and pgm.CDE_DPB_STS = 'A') ELSE "+   
																 "	CASE WHEN proc.CDE_DPB_PROC_TYP = 'LP' THEN 'A' ELSE "+   
																 "	CASE WHEN proc.CDE_DPB_PROC_TYP = 'RP' THEN (select rpt.CDE_DPB_PROC_INIT_TYP from DPB_RPT rpt where rpt.ID_DPB_RPT = proc.ID_DPB_RPT and rpt.CDE_DPB_STS = 'A') ELSE "+   
																 "	null "+   
																 "	END END END END END END END END END END END as CDE_DPB_OVRD_PROC_INIT_TYP, "+   
																 "	proc.TME_OVRD_PROC, (select plog.CDE_DPB_PROC_STS from DPB_PROC_LOG plog "+    
																 "	where plog.ID_DPB_PROC_LOG = log.ID_PROC_LOG) AS STATUS, COALESCE(log.RECCOUNT,0) as RECCOUNT "+   
																 "	from PROCESS proc "+    
																 "	left outer join PROC_LOG log on proc.ID_DPB_PROC = log.ID_DPB_PROC ";

		/*public static final String retrieveFileProcessDefQuery =  " SELECT proc.ID_DPB_PROC, proc.CDE_DPB_PROC_TYP, rtl_clndr1.DTE_CAL , proc.TXT_RSN_PROC_UPD, (select odt.DTE_CAL from  DPB_RTL_CAL_DY_DIM odt where odt.ID_DAY= ID_OVRD_PROC_DTE ) as ID_OVRD_PROC_DTE, (select log.CDE_DPB_PROC_STS from DPB_PROC_LOG log  where log.ID_DPB_PROC_LOG = " +
				 												  "(select max(log1.ID_DPB_PROC_LOG) from DPB_PROC_LOG log1  where log1.ID_DPB_PROC=proc.ID_DPB_PROC)) as STATUS,"+ " "+
				 												  "CDE_DPB_OVRD_TRGR, TME_OVRD_PROC ,( select count(*) from dpb_proc_LOG where ID_DPB_PROC= ? ) as RECCOUNT  FROM DPB_PROC proc LEFT JOIN DPB_RTL_CAL_DY_DIM rtl_clndr1 ON "+ " "+  
				 												  "proc.ID_DAY= rtl_clndr1.ID_DAY LEFT JOIN DPB_RTL_CAL_DY_DIM rtl_clndr2 on "+ " "+  
				 												  "proc.ID_OVRD_PROC_DTE= rtl_clndr2.ID_DAY  WHERE proc.ID_DPB_PROC=?";*/
		public static final String retrieveRetailDateId ="  select (select DTE_CAL from DPB_DAY RTL where date(RTL.DTE_CAL)= ? ) "+ 
														 "	as ID_DAY,ID_DPB_FILE,ID_DPB_PGM,ID_DPB_RPT,ID_LDRSP_BNS_PGM,DTE_DPB_ACTL_PROC "+ 
														 "	from DPB_DAY dim,DPB_PROC proc where dim.DTE_CAL=proc.DTE_CAL   and ID_DPB_PROC= ? "; 
		public static final String updateFileProcessDefQuery = "UPDATE DPB_PROC SET TXT_RSN_PROC_UPD = ?, ID_OVRD_PROC_DTE = ?, CDE_DPB_OVRD_TRGR = ?, TME_OVRD_PROC = ? WHERE ID_DPB_PROC = ?";
		public static final String insertFileProcessDefQuery = "INSERT INTO  DPB_PROC(DTE_CAL,ID_DPB_FILE,DTE_DPB_ACTL_PROC,CDE_DPB_PROC_TYP,TME_PROC_OVRD,CDE_DPB_OVRD_PROC_INIT_TYP,TXT_RSN_PROC_UPD,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA) VALUES(?,?,?,?,?,?,?,?,?,CURRENT TIMESTAMP,CURRENT TIMESTAMP)";
		public static final String insertPgmProcessDefQuery = "INSERT INTO  DPB_PROC(DTE_CAL,ID_DPB_PGM,DTE_DPB_ACTL_PROC,CDE_DPB_PROC_TYP,TME_PROC_OVRD,CDE_DPB_OVRD_PROC_INIT_TYP,TXT_RSN_PROC_UPD,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA) VALUES(?,?,?,?,?,?,?,?,?,CURRENT TIMESTAMP,CURRENT TIMESTAMP)";
		public static final String insertReportProcessDefQuery =  "INSERT INTO  DPB_PROC(DTE_CAL,ID_DPB_RPT,DTE_DPB_ACTL_PROC,CDE_DPB_PROC_TYP,TME_PROC_OVRD,CDE_DPB_OVRD_PROC_INIT_TYP,TXT_RSN_PROC_UPD,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA) VALUES(?,?,?,?,?,?,?,?,?,CURRENT TIMESTAMP,CURRENT TIMESTAMP)";
		public static final String insertLdrPgmProcessDefQuery = "INSERT INTO  DPB_PROC(DTE_CAL,ID_LDRSP_BNS_PGM,DTE_DPB_ACTL_PROC,CDE_DPB_PROC_TYP,TME_PROC_OVRD,CDE_DPB_OVRD_PROC_INIT_TYP,TXT_RSN_PROC_UPD,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA) VALUES(?,?,?,?,?,?,?,?,?,CURRENT TIMESTAMP,CURRENT TIMESTAMP)";
		public static final String updateFileprocLogQuery = "UPDATE DPB_PROC_LOG SET CDE_DPB_PROC_STS = ?  WHERE ID_DPB_PROC_LOG in (select max(log1.ID_DPB_PROC_LOG) from DPB_PROC_LOG log1  where log1.ID_DPB_PROC = ? ) ";
		public static final String insertFileprocLogQuery = " insert into DPB_PROC_LOG (ID_DPB_PROC, CDE_DPB_PROC_STS, TXT_DPB_PROC_MSG, ID_CREA_USER, ID_LAST_UPDT_USER, DTS_LAST_UPDT, DTS_CREA) values (?,?,?,?,?,CURRENT TIMESTAMP,CURRENT TIMESTAMP) ";
		//public static final String retrieveFileProcessDefLdrBonQuery = "SELECT CDE_LDRSP_PGM_STS FROM CD5001193SCHMA.LDRSP_BNS_PGM WHERE LEADERSHIP_BONUS_PROGRAM_IDENTIFIER = ? AND (CDE_LDRSP_PGM_STS != 'I' OR (CDE_LDRSP_PGM_STS = 'I' AND DTS_IN_ACT > ?))";
		//public static final String retrieveFileProcessDefDpbRprtQuery = "SELECT CDE_RPT_STS FROM CD5001193SCHMA.DPB_RPT WHERE ID_RPT_DEFN = ? AND CDE_RPT_STS != 'I'";
		//public static final String retrieveFileProcessDefDpbPgmQuery = "SELECT CDE_RPT_STS FROM CD5001193SCHMA.DPB_RPT WHERE ID_RPT_DEFN = ? AND CDE_RPT_STS != 'I'";
		//Nikhil changes end
		
	public static final String RETRIVE_COND_LIST_BASED_ON_TYPE = " SELECT ID_DPB_CND, NAM_DPB_CND,DES_DPB_CND,CDE_DPB_CND,NAM_DPB_VAR,TXT_DPB_CHK_VAL,CDE_DPB_CND_TYP,TXT_DPB_REG_EX,CDE_DPB_STS FROM DPB_CND WHERE CDE_DPB_CND_TYP = ?";
		public static final String RETRIVE_FROM_VEH_TYP_CND_REL_MAPPING 	= "SELECT A.ID_DPB_CND,A.NAM_DPB_CND, B.CDE_VEH_TYP FROM DPB_CND A, VEH_TYP_CND_REL B WHERE A.ID_DPB_CND = B.ID_DPB_CND AND CDE_DPB_CND_TYP = ? ORDER BY CDE_VEH_TYP, NAM_DPB_CND";
		/*public static final String RETRIVE_FROM_DPB_VEH_BLK_CND_REL_MAPPING = " SELECT CDE_VEH_TYP, ID_DPB_CND FROM DPB_VEH_BLK_CND_REL  WHERE CDE_VEH_TYP = ? ORDER BY CDE_VEH_TYP ";*/
		public static final String INSERT_INTO_VEH_TYP_CND_REL  			= " INSERT INTO VEH_TYP_CND_REL " +
																			  " (CDE_VEH_TYP, ID_DPB_CND,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_CREA,DTS_LAST_UPDT) " +
																			  " VALUES (?,?,?,?,CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
		public static final String INSERT_INTO_DPB_VEH_BLK_CND_REL 			= " INSERT INTO VEH_TYP_CND_REL " +
																			  " (CDE_VEH_TYP, ID_DPB_CND,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_CREA,DTS_LAST_UPDT) " +
																			  "	VALUES (?,?,?,?,CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
		
		public static final String DELETE_FROM_VEH_TYP_CND_REL 		= " DELETE FROM  VEH_TYP_CND_REL WHERE ID_DPB_CND = ? ";
		/*public static final String DELETE_FROM_DPB_VEH_BLK_CND_REL  = " DELETE FROM  VEH_TYP_CND_REL WHERE ID_DPB_CND = ? ";*/
		public static final String VALIDATE_PROGRAM = "SELECT ID_DPB_PGM, NAM_DPB_PGM, DTE_DPB_PGM_START FROM DPB_PGM WHERE NAM_DPB_PGM = ? AND DTE_DPB_PGM_START = ? AND ID_DPB_PGM != ?";
		public static final String VALIDATE_UNIQUE_CONDITION = "SELECT CDE_DPB_CND, NAM_DPB_VAR,TXT_DPB_CHK_VAL,CDE_DPB_CND_TYP FROM DPB_CND WHERE CDE_DPB_CND = ? AND NAM_DPB_VAR = ? AND TXT_DPB_CHK_VAL = ? AND CDE_DPB_CND_TYP =? AND TXT_DPB_REG_EX = ? ";
		//RESET/Reprocess Queries
		public static final String GET_BONUS_PROCESSES_FOR_TODAY = "with PROC_LOG (ID_PROC_LOG, PROC_ID) as ( "+   
																	"select max(log.ID_DPB_PROC_LOG), log.ID_DPB_PROC "+      
																	"from DPB_PROC_LOG log "+
																	"where log.ID_DPB_PROC IN (?) "+
																	"group by log.ID_DPB_PROC) "+
																	"select log.ID_DPB_PROC, log.CDE_DPB_PROC_STS "+
																	"from DPB_PROC_LOG log, PROC_LOG plog "+     
																	"where log.ID_DPB_PROC_LOG = plog.ID_PROC_LOG and trim(log.CDE_DPB_PROC_STS) in ('S') with ur";
		public static final String DELETE_LDRSHP_BONUS_RECORDS = "DELETE FROM DPB_CALC WHERE ID_DPB_PROC = ?  AND DTE_CAL = ? " +
																 "AND  CDE_DPB_CALC_STS NOT IN ('BP', 'TP','BC' ,'LP') ";
		public static final String DELETE_BONUS_RECORDS = "DELETE FROM DPB_CALC WHERE ID_DPB_PROC = ?  AND DTE_CAL = ? " +
														  "AND  CDE_DPB_CALC_STS NOT IN ('BP', 'TP','LC' ,'LP') ";
		public static final String FETCH_ACTUAL_DAY = "SELECT DTE_DPB_ACTL_PROC FROM DPB_PROC WHERE  ID_DPB_PROC = ? WITH UR";
		public static final String FETCH_LDRSHP_BONUS_ACTUAL_DAY ="SELECT ID_DPB_PROC,DTE_DPB_ACTL_PROC FROM DPB_PROC WHERE DTE_DPB_ACTL_PROC = (SELECT DTE_DPB_ACTL_PROC FROM DPB_PROC WHERE ID_DPB_PROC = ?) and CDE_DPB_PROC_TYP IN ('LB') WITH UR";
		public static final String FETCH_BONUS_PROCESS_ACTUAL_DAY ="SELECT ID_DPB_PROC,DTE_DPB_ACTL_PROC FROM DPB_PROC WHERE DTE_DPB_ACTL_PROC = (SELECT DTE_DPB_ACTL_PROC FROM DPB_PROC WHERE ID_DPB_PROC = ?) and CDE_DPB_PROC_TYP IN ('CB','FB','VB') WITH UR";
		public static final String FETCH_BONUS_PROCESS_FOR_KPI ="SELECT ID_DPB_PROC,DTE_DPB_ACTL_PROC FROM DPB_PROC WHERE DTE_DPB_ACTL_PROC = (SELECT DTE_DPB_ACTL_PROC FROM DPB_PROC WHERE ID_DPB_PROC = ?) and CDE_DPB_PROC_TYP IN ('AJ','VB') WITH UR";
		public static final String UPDATE_ACCRUAL_BONUS_PROCESS = "SELECT A.CDE_DPB_PROC_TYP, A.ID_DPB_PROC  FROM DPB_PROC A WHERE " +
																  "A.CDE_DPB_PROC_TYP  = 'LB' AND DTE_DPB_ACTL_PROC = ? WITH UR";
		public static final String DELETE_ACCRUAL_DATA = "DELETE FROM LDRSP_BNS_ACRL WHERE ID_DPB_PROC = ?";
		public static final String DELETE_KPI_DATA = "DELETE FROM DPB_KPI_FIL_EXTRT WHERE ID_DPB_PROC = ?";
		public static final String INSERT_BONUS_PROCESS_LOG = "INSERT INTO DPB_PROC_LOG (ID_DPB_PROC, CDE_DPB_PROC_STS, " +
	    													  "TXT_DPB_PROC_MSG, ID_CREA_USER, ID_LAST_UPDT_USER, DTS_LAST_UPDT, DTS_CREA) VALUES (?, ?, ?, ?, ?, CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
		public static final String UPDATE_LDRSHP_BONUS = "UPDATE DPB_CALC SET CDE_DPB_CALC_STS = 'RC' , ID_LAST_UPDT_USER = ?, DTS_LAST_UPDT = CURRENT TIMESTAMP WHERE ID_DPB_PROC = ?  AND DTE_CAL = ? " +
														 " AND  CDE_DPB_CALC_STS NOT IN ('BP', 'TP','BC' ,'LP')";
		public static final String UPDATE_BONUS_CALC = "UPDATE DPB_CALC SET CDE_DPB_CALC_STS = 'RC' , ID_LAST_UPDT_USER = ? , DTS_LAST_UPDT = CURRENT TIMESTAMP WHERE ID_DPB_PROC = ?  AND DTE_CAL = ? " +
														"AND  CDE_DPB_CALC_STS NOT IN ('BP', 'TP','LC' ,'LP')";
		public static final String UPDATE_BONUS_PROCESSES = "SELECT A.CDE_DPB_PROC_TYP, A.ID_DPB_PROC  FROM DPB_PROC A WHERE " +
															"A.CDE_DPB_PROC_TYP IN ('FB','VB', 'CB') AND DTE_DPB_ACTL_PROC = ? WITH UR";
		public static final String DELETE_BLOCKED_DATA =  "DELETE FROM DPB_BLK_VEH WHERE ID_DPB_PROC = ?";
		public static final String DELETE_VISTA_DATA =  "DELETE FROM DPB_UNBLK_VEH WHERE ID_DPB_PROC = ?";
		public static final String GET_PAYMENT_PROCESSES_FOR_TODAY = "WITH ACTUAL_DATE (ACT_DATE) AS ( "+
																	"SELECT DTE_DPB_ACTL_PROC FROM DPB_PROC WHERE ID_DPB_PROC = ? "+                                                                                                                                                                              
																	"), "+
																	"PAY_PROC (PROC_ID) AS ( "+
																	"SELECT ID_DPB_PROC FROM DPB_PROC A , ACTUAL_DATE D WHERE "+
																	"A.DTE_DPB_ACTL_PROC = D.ACT_DATE AND CDE_DPB_PROC_TYP IN ('CP','FP','VP', 'LP') "+
																	") , "+
																	"PROC_LOG (ID_PROC_LOG, PROC_ID) AS (      "+
																	"SELECT MAX (LOG.ID_DPB_PROC_LOG), LOG.ID_DPB_PROC "+         
																	"FROM DPB_PROC_LOG LOG , PAY_PROC PAY "+
																	"WHERE LOG.ID_DPB_PROC IN(PAY.PROC_ID) "+
																	"GROUP BY LOG.ID_DPB_PROC)   "+
																	"SELECT LOG.CDE_DPB_PROC_STS   "+
																	"FROM DPB_PROC_LOG LOG, PROC_LOG PLOG "+        
																	"WHERE LOG.ID_DPB_PROC_LOG = PLOG.ID_PROC_LOG with ur";

		public static final String FETCH_BONUS_PROCESSES_FOR_RESCHEDULE = " WITH ACT_DATE(CDE_DPB_PROC_TYP, DTE_DPB_ACTL_PROC, ID_DPB_PGM, ID_LDRSP_BNS_PGM, ID_DPB_RPT,DTE_CAL) as ( "+    
																			" select CDE_DPB_PROC_TYP,DTE_DPB_ACTL_PROC, ID_DPB_PGM, ID_LDRSP_BNS_PGM,ID_DPB_RPT,DTE_CAL  from dpb_proc where ID_DPB_PROC = ? "+
																			" ),     "+
																			" PROC_TYPES (SRC_TYP,DES_TYP) as ( "+    
																			" values ('CB','CP') union    "+
																			" values ('FB','FP') union    "+
																			" values ('VB','VP') union    "+
																			"values ('LB','LP')    "+
																			" ),    "+
																			" PROC_ID(ID_DPB_PGM, ID_DPB_FILE, ID_DPB_RPT,ID_LDRSP_BNS_PGM,ID_DPB_PROC, DTE_CAL , DTE_DPB_ACTL_PROC, CDE_DPB_PROC_TYP) as  ( "+    
																			" select P.ID_DPB_PGM, P.ID_DPB_FILE, P.ID_DPB_RPT,P.ID_LDRSP_BNS_PGM,P.ID_DPB_PROC, P.DTE_CAL , P.DTE_DPB_ACTL_PROC, P.CDE_DPB_PROC_TYP  from DPB_PROC P, ACT_DATE D where P.DTE_DPB_ACTL_PROC = D.DTE_DPB_ACTL_PROC "+    
																			" and p.ID_DPB_PGM = d.ID_DPB_PGM and p.CDE_DPB_PROC_TYP in (select DES_TYP from PROC_TYPES where SRC_TYP IN (d.CDE_DPB_PROC_TYP))    "+
																			" union     "+
																			" select P.ID_DPB_PGM, P.ID_DPB_FILE, P.ID_DPB_RPT,P.ID_LDRSP_BNS_PGM,P.ID_DPB_PROC, P.DTE_CAL , P.DTE_DPB_ACTL_PROC, P.CDE_DPB_PROC_TYP from DPB_PROC P, ACT_DATE D where P.DTE_DPB_ACTL_PROC = D.DTE_DPB_ACTL_PROC  "+    
																			" and p.ID_LDRSP_BNS_PGM = d.ID_LDRSP_BNS_PGM and p.CDE_DPB_PROC_TYP in (select DES_TYP from PROC_TYPES where SRC_TYP IN (d.CDE_DPB_PROC_TYP))    				 "+
																			" union     "+
																			" select P.ID_DPB_PGM, P.ID_DPB_FILE, P.ID_DPB_RPT,P.ID_LDRSP_BNS_PGM,P.ID_DPB_PROC, P.DTE_CAL , P.DTE_DPB_ACTL_PROC, P.CDE_DPB_PROC_TYP   from DPB_PROC P, ACT_DATE D where P.DTE_DPB_ACTL_PROC = D.DTE_DPB_ACTL_PROC  "+    
																			" and p.CDE_DPB_PROC_TYP IN ('RP')     "+
																			" )select * from proc_id with ur ";
																	
		public static final String FETCH_VISTA_FILE_PROCESSES_FOR_RESCHEDULE = "WITH ACT_DATE (CDE_DPB_PROC_TYP, DTE_DPB_ACTL_PROC, ID_DPB_PGM, ID_LDRSP_BNS_PGM, ID_DPB_RPT,ID_DPB_FILE, DTE_CAL, ID_DPB_PROC) AS ( "+
																	" SELECT CDE_DPB_PROC_TYP, DTE_DPB_ACTL_PROC, ID_DPB_PGM, ID_LDRSP_BNS_PGM, ID_DPB_RPT,ID_DPB_FILE, DTE_CAL, ID_DPB_PROC FROM DPB_PROC WHERE ID_DPB_PROC = ? "+
																	" ), "+
																	" PROC_ID(CDE_DPB_PROC_TYP, DTE_DPB_ACTL_PROC, ID_DPB_PGM, ID_LDRSP_BNS_PGM, ID_DPB_RPT,ID_DPB_FILE, DTE_CAL, ID_DPB_PROC) AS  ( "+
																	" SELECT P.CDE_DPB_PROC_TYP, P.DTE_DPB_ACTL_PROC, P.ID_DPB_PGM, P.ID_LDRSP_BNS_PGM, P.ID_DPB_RPT,P.ID_DPB_FILE, P.DTE_CAL, P.ID_DPB_PROC FROM DPB_PROC P, ACT_DATE D WHERE P.DTE_DPB_ACTL_PROC = D.DTE_DPB_ACTL_PROC  AND P.CDE_DPB_PROC_TYP IN  ('FB','VB','CB','FP','VP','CP','RP') "+
																	" ) SELECT * FROM PROC_ID with ur";
		public static final String CREATE_RESCHEDULED_PROCESSES =  " INSERT INTO DPB_PROC (ID_DPB_RPT, ID_DPB_PGM, ID_DPB_FILE, DTE_CAL, ID_LDRSP_BNS_PGM, CDE_DPB_PROC_TYP, DTE_DPB_ACTL_PROC, ID_LAST_UPDT_USER , ID_CREA_USER,DTS_CREA, DTS_LAST_UPDT) VALUES (?,?,?,?,?,?,?,?,?,CURRENT TIMESTAMP,CURRENT TIMESTAMP)";
		public static final String FETCH_KPI_FILE_PROCESSES_FOR_RESCHEDULE = "WITH ACT_DATE (CDE_DPB_PROC_TYP, DTE_DPB_ACTL_PROC, ID_DPB_PGM, ID_LDRSP_BNS_PGM, ID_DPB_RPT,ID_DPB_FILE, DTE_CAL, ID_DPB_PROC) AS ( "+
																			" SELECT CDE_DPB_PROC_TYP, DTE_DPB_ACTL_PROC, ID_DPB_PGM, ID_LDRSP_BNS_PGM, ID_DPB_RPT,ID_DPB_FILE, DTE_CAL, ID_DPB_PROC FROM DPB_PROC WHERE ID_DPB_PROC = ? "+
																			" ), "+
																			" PROC_ID(CDE_DPB_PROC_TYP, DTE_DPB_ACTL_PROC, ID_DPB_PGM, ID_LDRSP_BNS_PGM, ID_DPB_RPT,ID_DPB_FILE, DTE_CAL, ID_DPB_PROC) AS  ( "+
																			" SELECT P.CDE_DPB_PROC_TYP, P.DTE_DPB_ACTL_PROC, P.ID_DPB_PGM, P.ID_LDRSP_BNS_PGM, P.ID_DPB_RPT,P.ID_DPB_FILE, P.DTE_CAL, P.ID_DPB_PROC FROM DPB_PROC P, ACT_DATE D WHERE P.DTE_DPB_ACTL_PROC = D.DTE_DPB_ACTL_PROC  AND P.CDE_DPB_PROC_TYP IN  ('VB','VP','AJ','RP') "+
																			" ) SELECT * FROM PROC_ID with ur ";
		public static final String FETCH_ACCRUAL_FILE_PROCESSES_FOR_RESCHEDULE = "WITH ACT_DATE (CDE_DPB_PROC_TYP, DTE_DPB_ACTL_PROC, ID_DPB_PGM, ID_LDRSP_BNS_PGM, ID_DPB_RPT,ID_DPB_FILE, DTE_CAL, ID_DPB_PROC) AS ( "+
																			" SELECT CDE_DPB_PROC_TYP, DTE_DPB_ACTL_PROC, ID_DPB_PGM, ID_LDRSP_BNS_PGM, ID_DPB_RPT,ID_DPB_FILE, DTE_CAL, ID_DPB_PROC FROM DPB_PROC WHERE ID_DPB_PROC = ? "+
																			" ), "+
																			" PROC_ID(CDE_DPB_PROC_TYP, DTE_DPB_ACTL_PROC, ID_DPB_PGM, ID_LDRSP_BNS_PGM, ID_DPB_RPT,ID_DPB_FILE, DTE_CAL, ID_DPB_PROC) AS  ( "+
																			" SELECT P.CDE_DPB_PROC_TYP, P.DTE_DPB_ACTL_PROC, P.ID_DPB_PGM, P.ID_LDRSP_BNS_PGM, P.ID_DPB_RPT,P.ID_DPB_FILE, P.DTE_CAL, P.ID_DPB_PROC FROM DPB_PROC P, ACT_DATE D WHERE P.DTE_DPB_ACTL_PROC = D.DTE_DPB_ACTL_PROC  AND P.CDE_DPB_PROC_TYP IN  ('LB','LP') "+
																			" ) SELECT * FROM PROC_ID with ur";

		public static final String GET_RETAIL_DATA_DETAILS = "SELECT DTE_CAL,DES_DAY,DTE_RETL_MTH_BEG,DTE_RETL_MTH_END, "+
															" DTE_RETL_YR_BEG,DTE_RETL_YR_END,NUM_DY_RETL_MTH,NAM_RETL_MTH,NUM_RETL_MTH,NUM_RETL_YR,IND_WKDY,NAM_DY_OF_WK  "+
															" FROM DPB_DAY where NUM_RETL_MTH = ? and NUM_RETL_YR =  ? fetch first rows only ";
		public static final String CHECK_MONTHLY_PROCESSES = "with PROCS (ID_DPB_PROC, ID_DPB_RPT,ID_DPB_PGM,ID_DPB_FILE,ID_LDRSP_BNS_PGM,CDE_DPB_PROC_TYP,DTE_DPB_ACTL_PROC) as ( "+
															"select ID_DPB_PROC,ID_DPB_RPT,ID_DPB_PGM,ID_DPB_FILE,ID_LDRSP_BNS_PGM,CDE_DPB_PROC_TYP,DTE_DPB_ACTL_PROC "+
															"from DPB_PROC proc "+
															"where proc.DTE_DPB_ACTL_PROC = (select max(DTE_RETL_MTH_END)+ 1 day from DPB_DAY  where NUM_RETL_MTH = ? and NUM_RETL_YR = ?) "+
															"), "+
															"PROCS_DATA (ID_DPB_PROC,CDE_DPB_PROC_TYP,CDE_DPB_SCHD) as ( "+
															"select ID_DPB_PROC, CDE_DPB_PROC_TYP, "+
															"CASE WHEN p.CDE_DPB_PROC_TYP in ('PF','AJ') THEN (select file.CDE_DPB_SCHD from DPB_FILE file    "+
															"    where file.ID_DPB_FILE = p.ID_DPB_FILE and (file.CDE_DPB_STS = 'A' or (file.CDE_DPB_STS = 'I'    "+
															"   and file.DTE_INACT > p.DTE_DPB_ACTL_PROC))) ELSE  "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'FB' THEN (select pgm.CDE_FXD_BNS_SCHD from DPB_PGM pgm    "+
															"    where pgm.ID_DPB_PGM = p.ID_DPB_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'    "+
															"    and pgm.DTE_INACT > p.DTE_DPB_ACTL_PROC))) ELSE "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'VB' THEN (select pgm.CDE_VAR_BNS_SCHD from DPB_PGM pgm    "+
															"    where pgm.ID_DPB_PGM = p.ID_DPB_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'    "+
															"    and pgm.DTE_INACT > p.DTE_DPB_ACTL_PROC))) ELSE "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'CB' THEN (select pgm.CDE_COMSN_BNS_PROC_SCHD from DPB_PGM pgm    "+
															"    where pgm.ID_DPB_PGM = p.ID_DPB_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'    "+
															"    and pgm.DTE_INACT > p.DTE_DPB_ACTL_PROC))) ELSE  "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'LB' THEN 'Y' ELSE  "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'FP' THEN (select pgm.CDE_FXD_PMT_SCHD from DPB_PGM pgm    "+
															"    where pgm.ID_DPB_PGM = p.ID_DPB_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'    "+
															"    and pgm.DTE_INACT > p.DTE_DPB_ACTL_PROC))) ELSE "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'VP' THEN (select pgm.CDE_VAR_PMT_SCHD from DPB_PGM pgm    "+
															"    where pgm.ID_DPB_PGM = p.ID_DPB_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'    "+
															"    and pgm.DTE_INACT > p.DTE_DPB_ACTL_PROC))) ELSE "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'CP' THEN (select pgm.CDE_COMSN_PMT_SCHD from DPB_PGM pgm    "+
															"    where pgm.ID_DPB_PGM = p.ID_DPB_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'    "+
															"    and pgm.DTE_INACT > p.DTE_DPB_ACTL_PROC))) ELSE "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'LP' THEN 'Y' ELSE "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'RP' THEN (select rpt.CDE_DPB_SCHD from DPB_RPT rpt    "+
															"    where rpt.ID_DPB_RPT = p.ID_DPB_RPT and (rpt.CDE_DPB_STS = 'A' or (rpt.CDE_DPB_STS = 'I'    "+
															"   and rpt.DTE_INACT > p.DTE_DPB_ACTL_PROC)))  "+
															"END END END END END END END END END END "+
															"from PROCS p "+
															"), "+
															"PROC_LOG (ID_DPB_PROC, ID_DPB_PROC_LOG) as ( "+
															"select log.ID_DPB_PROC, max(ID_DPB_PROC_LOG) "+
															"from DPB_PROC_LOG log, PROCS_DATA pdata "+
															"where log.ID_DPB_PROC = pdata.ID_DPB_PROC "+
															"group by log.ID_DPB_PROC "+
															"), "+
															"PROC_LOG_FINAL (ID_DPB_PROC, LOG_STATUS) as (     "+
															"select proc.ID_DPB_PROC, (select trim(plog.CDE_DPB_PROC_STS) from DPB_PROC_LOG plog   "+ 
															"where plog.ID_DPB_PROC_LOG = log.ID_DPB_PROC_LOG)      "+
															"from PROCS_DATA proc     "+
															"left outer join PROC_LOG log on proc.ID_DPB_PROC = log.ID_DPB_PROC "+
															"where  proc.CDE_DPB_SCHD != 'D' and proc.CDE_DPB_SCHD is not null "+
															") "+
															"select ID_DPB_PROC, LOG_STATUS from PROC_LOG_FINAL where LOG_STATUS is not null ";
		
		public static final String FETCH_MONTHLY_PROCESSES = "with PROCS (ID_DPB_PROC, ID_DPB_RPT,ID_DPB_PGM,ID_DPB_FILE,ID_LDRSP_BNS_PGM,CDE_DPB_PROC_TYP,DTE_DPB_ACTL_PROC) as ( "+
															"select ID_DPB_PROC,ID_DPB_RPT,ID_DPB_PGM,ID_DPB_FILE,ID_LDRSP_BNS_PGM,CDE_DPB_PROC_TYP,DTE_DPB_ACTL_PROC "+
															"from DPB_PROC proc "+
															"where proc.DTE_DPB_ACTL_PROC = ? "+
															"), "+
															"PROCS_DATA (ID_DPB_PROC,CDE_DPB_PROC_TYP,CDE_DPB_SCHD) as ( "+
															"select ID_DPB_PROC, CDE_DPB_PROC_TYP, "+
															"CASE WHEN p.CDE_DPB_PROC_TYP in ('PF','AJ') THEN (select file.CDE_DPB_SCHD from DPB_FILE file    "+
															"    where file.ID_DPB_FILE = p.ID_DPB_FILE and (file.CDE_DPB_STS = 'A' or (file.CDE_DPB_STS = 'I'    "+
															"   and file.DTE_INACT > p.DTE_DPB_ACTL_PROC))) ELSE  "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'FB' THEN (select pgm.CDE_FXD_BNS_SCHD from DPB_PGM pgm    "+
															"    where pgm.ID_DPB_PGM = p.ID_DPB_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'    "+
															"    and pgm.DTE_INACT > p.DTE_DPB_ACTL_PROC))) ELSE "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'VB' THEN (select pgm.CDE_VAR_BNS_SCHD from DPB_PGM pgm    "+
															"    where pgm.ID_DPB_PGM = p.ID_DPB_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'    "+
															"    and pgm.DTE_INACT > p.DTE_DPB_ACTL_PROC))) ELSE "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'CB' THEN (select pgm.CDE_COMSN_BNS_PROC_SCHD from DPB_PGM pgm    "+
															"    where pgm.ID_DPB_PGM = p.ID_DPB_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'    "+
															"    and pgm.DTE_INACT > p.DTE_DPB_ACTL_PROC))) ELSE  "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'LB' THEN 'Y' ELSE  "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'FP' THEN (select pgm.CDE_FXD_PMT_SCHD from DPB_PGM pgm    "+
															"    where pgm.ID_DPB_PGM = p.ID_DPB_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'    "+
															"    and pgm.DTE_INACT > p.DTE_DPB_ACTL_PROC))) ELSE "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'VP' THEN (select pgm.CDE_VAR_PMT_SCHD from DPB_PGM pgm    "+
															"    where pgm.ID_DPB_PGM = p.ID_DPB_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'    "+
															"    and pgm.DTE_INACT > p.DTE_DPB_ACTL_PROC))) ELSE "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'CP' THEN (select pgm.CDE_COMSN_PMT_SCHD from DPB_PGM pgm    "+
															"    where pgm.ID_DPB_PGM = p.ID_DPB_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'    "+
															"    and pgm.DTE_INACT > p.DTE_DPB_ACTL_PROC))) ELSE "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'LP' THEN 'Y' ELSE "+
															"CASE WHEN p.CDE_DPB_PROC_TYP = 'RP' THEN (select rpt.CDE_DPB_SCHD from DPB_RPT rpt    "+
															"    where rpt.ID_DPB_RPT = p.ID_DPB_RPT and (rpt.CDE_DPB_STS = 'A' or (rpt.CDE_DPB_STS = 'I'    "+
															"   and rpt.DTE_INACT > p.DTE_DPB_ACTL_PROC)))  "+
															"END END END END END END END END END END "+
															"from PROCS p "+
															"), "+
															"PROC_LOG (ID_DPB_PROC, ID_DPB_PROC_LOG) as ( "+
															"select log.ID_DPB_PROC, max(ID_DPB_PROC_LOG) "+
															"from DPB_PROC_LOG log, PROCS_DATA pdata "+
															"where log.ID_DPB_PROC = pdata.ID_DPB_PROC "+
															"group by log.ID_DPB_PROC "+
															"), "+
															"PROC_LOG_FINAL (ID_DPB_PROC, LOG_STATUS) as (     "+
															"select proc.ID_DPB_PROC, (select trim(plog.CDE_DPB_PROC_STS) from DPB_PROC_LOG plog   "+ 
															"where plog.ID_DPB_PROC_LOG = log.ID_DPB_PROC_LOG)      "+
															"from PROCS_DATA proc     "+
															"left outer join PROC_LOG log on proc.ID_DPB_PROC = log.ID_DPB_PROC "+
															"where  proc.CDE_DPB_SCHD != 'D' and proc.CDE_DPB_SCHD is not null "+
															") "+
															"select ID_DPB_PROC, LOG_STATUS from PROC_LOG_FINAL where LOG_STATUS is null ";
		public static final String UPDATE_MONTHLY_PROCESSES_RTL_DATE = "UPDATE DPB_PROC SET DTE_CAL = ? ,  DTE_DPB_ACTL_PROC = ?,DTS_LAST_UPDT = CURRENT TIMESTAMP,ID_LAST_UPDT_USER = ? where ID_DPB_PROC = ?";
		public static final String UPDATE_DPB_DAY = " merge into DPB_DAY dd1 "+
								" using  "+
								" (select DTE_CAL, "+
								" case when d.DTE_CAL between sdates.STR_DATE and sdates.END_DATE then (days(sdates.END_DATE)  - days(sdates.STR_DATE))+1  "+
 " else case when d.DTE_CAL between  (sdates.END_DATE + 1 day) and dd2.DTE_RETL_MTH_END then (days(dd2.DTE_RETL_MTH_END)  - days(date(sdates.END_DATE) + 1 day))+1 "+  
 " else d.NUM_DY_RETL_MTH end end as NUM_DY_RETL_MTH, "+
 " case when d.DTE_CAL between sdates.STR_DATE and sdates.END_DATE then dd3.NUM_RETL_MTH "+  
 " else case when d.DTE_CAL between  (sdates.END_DATE + 1 day) and dd2.DTE_RETL_MTH_END then dd2.NUM_RETL_MTH "+ 
 " else d.NUM_RETL_MTH end end as NUM_RETL_MTH, "+
 " case when d.DTE_CAL between sdates.STR_DATE and sdates.END_DATE then  sdates.STR_DATE "+ 
 " else case when d.DTE_CAL between  (sdates.END_DATE + 1 day) and dd2.DTE_RETL_MTH_END then (sdates.END_DATE + 1 day) "+ 
 " else d.DTE_RETL_MTH_BEG end end as DTE_RETL_MTH_BEG, "+
 " case when d.DTE_CAL between sdates.STR_DATE and sdates.END_DATE then  sdates.END_DATE "+ 
 " else case when d.DTE_CAL between  (sdates.END_DATE + 1 day) and dd2.DTE_RETL_MTH_END then dd2.DTE_RETL_MTH_END "+ 
 " else d.DTE_RETL_MTH_END end end as DTE_RETL_MTH_END, "+
 " case when Integer(sdates.SEL_MONTH)=12  and d.DTE_CAL <= sdates.END_DATE then  dd3.DTE_RETL_YR_BEG "+ 
		 " else case when Integer(sdates.SEL_MONTH)=12  and d.DTE_CAL > sdates.END_DATE then (sdates.END_DATE + 1 day) "+ 
 " else d.DTE_RETL_YR_BEG end end as DTE_RETL_YR_BEG,  "+
 " case when Integer(sdates.SEL_MONTH)=12  and d.DTE_CAL <= sdates.END_DATE then sdates.END_DATE "+ 
		 " else case when Integer(sdates.SEL_MONTH)=12  and d.DTE_CAL > sdates.END_DATE then dd2.DTE_RETL_YR_END "+ 
		 " else d.DTE_RETL_YR_END end end as DTE_RETL_YR_END, "+
 " case when d.DTE_CAL between sdates.STR_DATE and sdates.END_DATE then dd3.NAM_RETL_MTH "+  
 " else case when d.DTE_CAL between  (sdates.END_DATE + 1 day) and dd2.DTE_RETL_MTH_END then dd2.NAM_RETL_MTH "+ 
 " else d.NAM_RETL_MTH end end as NAM_RETL_MTH, "+
 " case when Integer(sdates.SEL_MONTH)=12  and d.DTE_CAL <= sdates.END_DATE then dd3.NUM_RETL_YR "+ 
		 " else case when Integer(sdates.SEL_MONTH)=12  and d.DTE_CAL > sdates.END_DATE then dd2.NUM_RETL_YR "+ 
		 " else d.NUM_RETL_YR end end as NUM_RETL_YR,  "+
 " case when mod(Integer(sdates.SEL_MONTH),3) = 0 and d.DTE_CAL between sdates.STR_DATE and sdates.END_DATE then dd3.NUM_RETL_QTR "+  
		 " else case when mod(Integer(sdates.SEL_MONTH),3) = 0 and d.DTE_CAL between  (sdates.END_DATE + 1 day) and dd2.DTE_RETL_MTH_END then dd2.NUM_RETL_QTR "+ 
		 " else d.NUM_RETL_QTR end end as NUM_RETL_QTR, "+
 " case when mod(Integer(sdates.SEL_MONTH),3) = 0 and d.DTE_CAL between dd3.DTE_RETL_QTR_BEG and sdates.END_DATE then dd3.DTE_RETL_QTR_BEG "+  
		 " else case when mod(Integer(sdates.SEL_MONTH),3) = 0 and d.DTE_CAL between  (sdates.END_DATE + 1 day) and dd2.DTE_RETL_QTR_END then (date(sdates.END_DATE) + 1 day) "+ 
 " else d.DTE_RETL_QTR_BEG end end as DTE_RETL_QTR_BEG, "+
 " case when mod(Integer(sdates.SEL_MONTH),3) = 0 and d.DTE_CAL between dd3.DTE_RETL_QTR_BEG and sdates.END_DATE then sdates.END_DATE "+ 
		 " else case when mod(Integer(sdates.SEL_MONTH),3) = 0 and d.DTE_CAL between  (sdates.END_DATE + 1 day) and dd2.DTE_RETL_QTR_END then dd2.DTE_RETL_QTR_END "+  
		 " else d.DTE_RETL_QTR_END end end as DTE_RETL_QTR_END "+
 " from DPB_DAY d,  "+
								" (select dd.NUM_RETL_MTH,dd.DTE_RETL_MTH_BEG,dd.DTE_RETL_MTH_END,dd.DTE_RETL_YR_BEG,dd.DTE_RETL_YR_END, "+
								" dd.NAM_RETL_MTH,dd.NUM_RETL_YR,dd.NUM_RETL_QTR,dd.DTE_RETL_QTR_BEG,dd.DTE_RETL_QTR_END from DPB_DAY dd "+
								" where dd.DTE_CAL = (cast(? as date) + 20 day)) dd2, "+ //--new end date == date('2014-04-01')
								" (select dd.NUM_DY_RETL_MTH,dd.NUM_RETL_MTH,dd.DTE_RETL_MTH_BEG,dd.DTE_RETL_MTH_END,dd.DTE_RETL_YR_BEG,dd.DTE_RETL_YR_END, "+
								" dd.NAM_RETL_MTH,dd.NUM_RETL_YR,dd.NUM_RETL_QTR,dd.DTE_RETL_QTR_BEG,dd.DTE_RETL_QTR_END from DPB_DAY dd "+
								" where dd.DTE_CAL = cast(? as date)) dd3, "+ // --startdate 
								" (select cast(? as date) as STR_DATE,  "+
								" cast(? as date) as END_DATE, "+
								" cast(? as char(2)) as SEL_MONTH,  "+ //--start date and new end date
								 " cast(? as char(4)) as SEL_YEAR,  "+
								" cast(? as char(4)) as NEXT_YEAR from sysibm.sysdummy1) sdates "+
								" where (d.NUM_RETL_YR = sdates.SEL_YEAR or d.NUM_RETL_YR = sdates.NEXT_YEAR) "+
								" ) "+
								" final on dd1.DTE_CAL = final.DTE_CAL "+
								" when matched then update set "+ 
								" dd1.NUM_DY_RETL_MTH = final.NUM_DY_RETL_MTH, "+ 
								" dd1.DTE_RETL_MTH_BEG = final.DTE_RETL_MTH_BEG, "+
								" dd1.DTE_RETL_MTH_END = final.DTE_RETL_MTH_END, "+
								" dd1.NUM_RETL_MTH = final.NUM_RETL_MTH, "+
								" dd1.DTE_RETL_YR_BEG = final.DTE_RETL_YR_BEG, "+
								" dd1.DTE_RETL_YR_END = final.DTE_RETL_YR_END, "+
								" dd1.NAM_RETL_MTH = final.NAM_RETL_MTH, "+
								" dd1.NUM_RETL_YR = final.NUM_RETL_YR, "+
								" dd1.NUM_RETL_QTR = final.NUM_RETL_QTR, "+
								" dd1.DTE_RETL_QTR_BEG = final.DTE_RETL_QTR_BEG, "+
								" dd1.DTE_RETL_QTR_END = final.DTE_RETL_QTR_END, "+
								" dd1.DTS_LAST_UPDT = current timestamp, "+
								" dd1.ID_LAST_UPDT_USER = ? "; 
		
				/*"merge into DPB_DAY dd1 using "+ 
" (select DTE_CAL,"+
" case when d.DTE_CAL between sdates.STR_DATE and sdates.END_DATE then (days(sdates.END_DATE)  - days(sdates.STR_DATE))+1 "+ 
" else case when d.DTE_CAL between  (date(sdates.END_DATE) + 1 day) and dd2.DTE_RETL_MTH_END then (days(dd2.DTE_RETL_MTH_END)  - days(date(sdates.END_DATE) + 1 day))+1 "+
" else d.NUM_DY_RETL_MTH end end as NUM_DY_RETL_MTH, "+
" case when d.DTE_CAL between sdates.STR_DATE and sdates.END_DATE then dd3.NUM_RETL_MTH "+ 
" else case when d.DTE_CAL between  (date(sdates.END_DATE) + 1 day) and dd2.DTE_RETL_MTH_END then dd2.NUM_RETL_MTH "+
"  else d.NUM_RETL_MTH end end as NUM_RETL_MTH, "+
" case when d.DTE_CAL between sdates.STR_DATE and sdates.END_DATE then  sdates.STR_DATE "+
" else case when d.DTE_CAL between  (date(sdates.END_DATE) + 1 day) and dd2.DTE_RETL_MTH_END then (date(sdates.END_DATE) + 1 day) "+
" else d.DTE_RETL_MTH_BEG end end as DTE_RETL_MTH_BEG, "+
" case when d.DTE_CAL between sdates.STR_DATE and sdates.END_DATE then  sdates.END_DATE "+
" else case when d.DTE_CAL between  (date(sdates.END_DATE) + 1 day) and dd2.DTE_RETL_MTH_END then dd2.DTE_RETL_MTH_END "+
" else d.DTE_RETL_MTH_END end end as DTE_RETL_MTH_END, "+
" case when Integer(sdates.SEL_MONTH)=12  and d.DTE_CAL <= sdates.END_DATE then  dd3.DTE_RETL_YR_BEG "+
" else case when Integer(sdates.SEL_MONTH)=12  and d.DTE_CAL > sdates.END_DATE then (date(sdates.END_DATE) + 1 day) "+
" else d.DTE_RETL_YR_BEG end end as DTE_RETL_YR_BEG, "+
" case when Integer(sdates.SEL_MONTH)=12  and d.DTE_CAL <= sdates.END_DATE then sdates.END_DATE "+
" else case when Integer(sdates.SEL_MONTH)=12  and d.DTE_CAL > sdates.END_DATE then dd2.DTE_RETL_YR_END "+
" else d.DTE_RETL_YR_END end end as DTE_RETL_YR_END, "+
" case when d.DTE_CAL between sdates.STR_DATE and sdates.END_DATE then dd3.NAM_RETL_MTH "+ 
" else case when d.DTE_CAL between  (date(sdates.END_DATE) + 1 day) and dd2.DTE_RETL_MTH_END then dd2.NAM_RETL_MTH "+
" else d.NAM_RETL_MTH end end as NAM_RETL_MTH, "+
" case when Integer(sdates.SEL_MONTH)=12  and d.DTE_CAL <= sdates.END_DATE then dd3.NUM_RETL_YR "+
" else case when Integer(sdates.SEL_MONTH)=12  and d.DTE_CAL > sdates.END_DATE then dd2.NUM_RETL_YR "+
" else d.NUM_RETL_YR end end as NUM_RETL_YR, "+
" case when mod(Integer(sdates.SEL_MONTH),3) = 0 and d.DTE_CAL between sdates.STR_DATE and sdates.END_DATE then dd3.NUM_RETL_QTR "+ 
" else case when mod(Integer(sdates.SEL_MONTH),3) = 0 and d.DTE_CAL between  (date(sdates.END_DATE) + 1 day) and dd2.DTE_RETL_MTH_END then dd2.NUM_RETL_QTR "+
" else d.NUM_RETL_QTR end end as NUM_RETL_QTR, "+
" case when mod(Integer(sdates.SEL_MONTH),3) = 0 and d.DTE_CAL between dd3.DTE_RETL_QTR_BEG and sdates.END_DATE then dd3.DTE_RETL_QTR_BEG "+ 
" else case when mod(Integer(sdates.SEL_MONTH),3) = 0 and d.DTE_CAL between  (date(sdates.END_DATE) + 1 day) and dd2.DTE_RETL_QTR_END then (date(sdates.END_DATE) + 1 day) "+
" else d.DTE_RETL_QTR_BEG end end as DTE_RETL_QTR_BEG, "+
" case when mod(Integer(sdates.SEL_MONTH),3) = 0 and d.DTE_CAL between dd3.DTE_RETL_QTR_BEG and sdates.END_DATE then sdates.END_DATE "+
" else case when mod(Integer(sdates.SEL_MONTH),3) = 0 and d.DTE_CAL between  (date(sdates.END_DATE) + 1 day) and dd2.DTE_RETL_QTR_END then dd2.DTE_RETL_QTR_END "+
" else d.DTE_RETL_QTR_END end end as DTE_RETL_QTR_END "+
" from DPB_DAY d,  "+
" (select dd.NUM_RETL_MTH,dd.DTE_RETL_MTH_BEG,dd.DTE_RETL_MTH_END,dd.DTE_RETL_YR_BEG,dd.DTE_RETL_YR_END, "+
" dd.NAM_RETL_MTH,dd.NUM_RETL_YR,dd.NUM_RETL_QTR,dd.DTE_RETL_QTR_BEG,dd.DTE_RETL_QTR_END from DPB_DAY dd "+
" where dd.DTE_CAL = (select DTE_CAL from DPB_DAY where DTE_CAL = ?) + 20 day) dd2, "+ //--new end date 
" (select dd.NUM_DY_RETL_MTH,dd.NUM_RETL_MTH,dd.DTE_RETL_MTH_BEG,dd.DTE_RETL_MTH_END,dd.DTE_RETL_YR_BEG,dd.DTE_RETL_YR_END, "+
" dd.NAM_RETL_MTH,dd.NUM_RETL_YR,dd.NUM_RETL_QTR,dd.DTE_RETL_QTR_BEG,dd.DTE_RETL_QTR_END from DPB_DAY dd "+
" where dd.DTE_CAL = (select DTE_CAL from DPB_DAY where DTE_CAL = ?)) dd3,  "+ //startdate
" (select (select DTE_CAL from DPB_DAY where DTE_CAL = ?) as STR_DATE, " +
" (select DTE_CAL from DPB_DAY where DTE_CAL = ?) as END_DATE, " +
" (select varchar(NUM_RETL_MTH) from DPB_DAY where DTE_CAL = ?) as SEL_MONTH, "+  // --start date and new end date
" (select varchar(NUM_RETL_YR) from DPB_DAY where DTE_CAL = ?) as SEL_YEAR, " +
" (select varchar(NUM_RETL_YR+1) from DPB_DAY where DTE_CAL = ?) as NEXT_YEAR from sysibm.sysdummy1) sdates "+
" where (d.NUM_RETL_YR = sdates.SEL_YEAR or d.NUM_RETL_YR = sdates.NEXT_YEAR)"+
" )"+
" final on dd1.DTE_CAL = final.DTE_CAL "+
" when matched then update set  "+
" dd1.NUM_DY_RETL_MTH = final.NUM_DY_RETL_MTH, "+ 
" dd1.DTE_RETL_MTH_BEG = final.DTE_RETL_MTH_BEG, "+
" dd1.DTE_RETL_MTH_END = final.DTE_RETL_MTH_END, "+
" dd1.NUM_RETL_MTH = final.NUM_RETL_MTH, "+
" dd1.DTE_RETL_YR_BEG = final.DTE_RETL_YR_BEG, "+
" dd1.DTE_RETL_YR_END = final.DTE_RETL_YR_END, "+
" dd1.NAM_RETL_MTH = final.NAM_RETL_MTH, "+
" dd1.NUM_RETL_YR = final.NUM_RETL_YR, "+
" dd1.NUM_RETL_QTR = final.NUM_RETL_QTR, "+
" dd1.DTE_RETL_QTR_BEG = final.DTE_RETL_QTR_BEG, "+
" dd1.DTE_RETL_QTR_END = final.DTE_RETL_QTR_END, "+
" dd1.DTS_LAST_UPDT = current timestamp, "+
" dd1.ID_LAST_UPDT_USER = ? ";*/
}
