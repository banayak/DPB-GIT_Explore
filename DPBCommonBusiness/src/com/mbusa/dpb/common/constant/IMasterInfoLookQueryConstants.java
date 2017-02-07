/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: IMasterInfoLookQueryConstants.java
 * Program Version			: 1.0
 * Program Description		: This class will keep all query related to cache.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 29, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/package com.mbusa.dpb.common.constant;


public interface IMasterInfoLookQueryConstants {

	public final static String RETRIVE_MENU_LIST 		= " SELECT MNU.ID_DPB_MNU,MNU.ID_DPB_PAR_MNU,MNU.IND_DPB_MNU_ENABL ,MNU.TXT_DPB_MNU_LBL,MNU.NAM_DPB_MNU_MTHD, "+
															" MNU.NUM_DPB_MNU_SEQ ,MNU.TXT_DPB_MNU_ALT,REL.CDE_DPB_USR_ROLE  FROM DPB_MNU  MNU "+
															" join DPB_MNU_USR_ROLE REL on REL.ID_DPB_MNU = MNU.ID_DPB_MNU and REL.CDE_DPB_USR_ROLE = ? "+
															" and   MNU.IND_DPB_MNU_ENABL = 'Y' "+
															" ORDER BY MNU.ID_DPB_MNU WITH UR";

	//ID_KPI, NAM_KPI,DTE_INACT
	public final static String RETRIVE_KPI_LIST 		= " SELECT ID_KPI,NAM_KPI,DTE_KPI_EFF,DTE_INACT FROM DPB_KPI_FDRT " +
														  " where  DTE_KPI_EFF  <= current date and  (DTE_INACT >= current date  or DTE_INACT is null ) "+
														  " order by UPPER(NAM_KPI) WITH UR ";
	
	public final static String RETRIVE_KPI_OLD_LIST 	= " SELECT ID_KPI,NAM_KPI,DTE_KPI_EFF,DTE_INACT FROM DPB_KPI_FDRT " +
														  " order by UPPER(NAM_KPI) WITH UR ";
	//public final static String RETRIVE_KPI_LIST 		= " SELECT ID_KPI,DES_KPI,PCT_KPI FROM KPI order by ID_KPI";
	/*public final static String RETRIVE_KPI_LIST 		= " SELECT ID_KPI,DES_KPI,DPB_PCT_MAXI_AMT,DTE_INACT FROM KPI " +
												  		  "	WHERE 	DTE_INACT = NULL ";*/
	
	
	public final static String RETRIVE_VEHICLE_LIST 	= " SELECT CDE_VEH_TYP,DES_VEH_TYP FROM VEH_TYP_FDRT ";
	        
	/*public final static String RETRIVE_CONDITION_CODE   = " SELECT CDE_CND,DES_CND_CDE from DPB_CND_CDE";*/

	public final static String RETRIVE_CONDITION_CODE   = " SELECT CDE_DPB_CND,DES_DPB_CND_CDE from DPB_CND_CDE";
		
	/*public final static String RETRIVE_CO_CDE 			= " SELECT CDE_CO, DES_CO from CO_CDE ";*/
	
	public final static String RETRIVE_CO_CDE 			= " SELECT CDE_CO, DES_CO_CDE from CO_CDE ";
	         
	public final static String RETRIVE_NATIONAL_TYP		= " SELECT CDE_NATL_TYPE,DES_NATL_TYP from NATIONAL_TYPE ";
	
	/*public final static String RETRIVE_DPB_CND_TYP 		= " SELECT CDE_CND_TYP,DES_CND_TYP_CDE from DPB_CND_TYP ";*/
	
	public final static String RETRIVE_DPB_CND_TYP 		= " SELECT CDE_DPB_CND_TYP,DES_DPB_CND_TYP from DPB_CND_TYP ";
	
	/*public final static String RETRIVE_DPB_DEFN_STS 	= " SELECT CDE_DEFN_STS,DES_DEFN_STS from DPB_DEFN_STS ";*/
	       
	public final static String RETRIVE_DPB_DEFN_STS 	= " SELECT CDE_DPB_STS,DES_DPB_STS from DPB_STS WHERE DTE_INACT IS NULL";
	
	/*public final static String RETRIVE_DPB_PROC_STS 	= " SELECT CDE_PROC_STS,NAM_PROC_STS from DPB_PROC_STS ";*/
	
	public final static String RETRIVE_DPB_PROC_STS 	= " SELECT CDE_DPB_PROC_STS,DES_DPB_PROC_STS from DPB_PROC_STS ";
	        
	/*public final static String RETRIVE_DPB_PROC_TYP		= " SELECT CDE_PROC_TYP,DES_PROC_TYP from DPB_PROC_TYP ";*/
	
	public final static String RETRIVE_DPB_PROC_TYP		= " SELECT CDE_DPB_PROC_TYP,DES_DPB_PROC_TYP from DPB_PROC_TYP ";
	
	/*public final static String RETRIVE_DPB_SCHD			= " SELECT CDE_SCHD,DES_SCHD from DPB_SCHD ";*/
	
	public final static String RETRIVE_DPB_SCHD			= " SELECT CDE_DPB_SCHD,DES_DPB_SCHD from DPB_SCHD WHERE DTE_INACT IS NULL";
	
	/*public final static String RETRIVE_DPB_TRGR 		= " SELECT CDE_TRGR,DES_TRGR from DPB_TRGR ";*/
	
	public final static String RETRIVE_DPB_TRGR 		= " SELECT CDE_DPB_PROC_INIT_TYP,DES_DPB_PROC_INIT_TYP from DPB_PROC_INIT_TYP WHERE DTE_INACT IS NULL";
	    
	/*public final static String RETRIVE_DPB_USR_ROLE 	= " SELECT CDE_USER_ROLE,DES_USR_ROLE from DPB_USR_ROLE ";*/
	
	public final static String RETRIVE_DPB_USR_ROLE 	= " SELECT CDE_DPB_USR_ROLE,DES_DPB_USR_ROLE from DPB_USR_ROLE ";
	
	public final static String RETRIVE_USE_CODE 		= " SELECT CDE_USE, DES_USE_CDE from USE_CODE ";
	       
	public final static String RETRIVE_VEH_STAT 		= " SELECT CDE_VEH_STAT,DES_VEH_STAT from DPB_VEH_STAT ";
	            
	public final static String RETRIVE_REGION 			= " SELECT CDE_RGN, DES_RGN from RGN_CDE ";
	
	/*
		public final static String RETRIVE_VEHICLE_LIST =" SELECT * FROM VEH_TYP ";
		public final static String RETRIVE_CONDITION_CODE = "SELECT CDE_CON,NAM_CND from DPB_CND_CDE"; 
		public final static String RETRIVE_DPB_CND_TYP = " SELECT CDE_CND_TYP,NAM_CND_TYP from DPB_CND_TYP ";
		public final static String RETRIVE_DPB_DEFN_STS = " SELECT CDE_DEFN_STS,NAM_DEFN_STS from DPB_DEFN_STS ";
		public final static String RETRIVE_DPB_PROC_STS = " SELECT CDE_PROC_STS,NAM_PROC_STS from DPB_PROC_STS ";	
		public final static String RETRIVE_DPB_PROC_TYP = " SELECT CDE_PROC_STS,DES_PROC_STS from DPB_PROC_TYP ";
		public final static String RETRIVE_DPB_SCHD = " SELECT CDE_SCHD,NAM_SCHD from DPB_SCHD ";
		public final static String RETRIVE_DPB_TRGR = " SELECT CDE_TRGR,NAM_TRGR from DPB_TRGR ";
		public final static String RETRIVE_DPB_USR_ROLE = " SELECT CDE_USR_ROLE,NAM_USR_ROLE from DPB_USR_ROLE ";
		public final static String RETRIVE_VEH_STAT = " SELECT CDE_EVENT,CDE_VEH_STAT from VEH_STAT ";
		public final static String RETRIVE_REGION 	= " SELECT CDE_RGN, NAM_RGN from REGION ";	
	*/
	

	
	
/*	ID_DAY,DTE_CAL,DES_DAY,DTE_RETL_MTH_BEG,DTE_RETL_MTH_END,NUM_RETL_MTH,NAM_RETL_MTH,NUM_DY_RETL_MTH       
	DTE_RETL_QTR_BEG,DTE_RETL_QTR_END,NUM_RETL_QTR,DTE_RETL_YR_BEG,DTE_RETL_YR_END,NUM_RETL_YR */          
	
	public final static String RETRIVE_CALENDER_INFO = " SELECT DTE_CAL,DES_DAY,DTE_CAL_WK_BEG,DTE_CAL_WK_END,DTE_CAL_MTH_BEG,DTE_CAL_MTH_END,DTE_RETL_MTH_BEG, "+
														" DTE_RETL_MTH_END,DTE_CAL_YR_BEG,DTE_CAL_YR_END,DTE_RETL_YR_BEG,DTE_RETL_YR_END,NUM_DY_CAL_MTH, "+
														" NUM_DY_RETL_MTH,NAM_CAL_MTH,NUM_CAL_MTH,NUM_CAL_YR,NAM_RETL_MTH,NUM_RETL_MTH,NUM_RETL_YR,NUM_JULN_DY, "+
														" IND_MBUSA_HO_HLDY,IND_USA_BNK_HLDY,IND_WKDY,NAM_DY_OF_WK,NUM_HO_CMTH_WRKDY,IND_PDC_HLDY,IND_PDC_PRTL_CREW, "+
														" QTY_HO_CMTH_WRKDY,NUM_CAL_QTR,DTE_CAL_QTR_BEG,DTE_CAL_QTR_END,NUM_RETL_QTR,DTE_RETL_QTR_BEG, "+
														" DTE_RETL_QTR_END "+
														" FROM DPB_DAY where DTE_CAL between '2013-02-01' and " +
														" (select max(DTE_CAL)  from DPB_DAY where " +
														" NUM_RETL_YR is not null and DTE_RETL_MTH_END is not null " +
														" and DTE_RETL_QTR_END is not null and  DTE_CAL != '9999-12-31')";
	
	public final static String RETRIVE_RTL_ID 		 = " SELECT DTE_CAL,DES_DAY,DTE_CAL_WK_BEG,DTE_CAL_WK_END,DTE_CAL_MTH_BEG,DTE_CAL_MTH_END,DTE_RETL_MTH_BEG, "+
														" DTE_RETL_MTH_END,DTE_CAL_YR_BEG,DTE_CAL_YR_END,DTE_RETL_YR_BEG,DTE_RETL_YR_END,NUM_DY_CAL_MTH, "+
														" NUM_DY_RETL_MTH,NAM_CAL_MTH,NUM_CAL_MTH,NUM_CAL_YR,NAM_RETL_MTH,NUM_RETL_MTH,NUM_RETL_YR,NUM_JULN_DY, "+
														" IND_MBUSA_HO_HLDY,IND_USA_BNK_HLDY,IND_WKDY,NAM_DY_OF_WK,NUM_HO_CMTH_WRKDY,IND_PDC_HLDY,IND_PDC_PRTL_CREW, "+
														" QTY_HO_CMTH_WRKDY,NUM_CAL_QTR,DTE_CAL_QTR_BEG,DTE_CAL_QTR_END,NUM_RETL_QTR,DTE_RETL_QTR_BEG, "+
														" DTE_RETL_QTR_END "+
														" FROM DPB_DAY WHERE DTE_CAL = ? ";


	public final static String RETIRVE_ACTIVE_CONDTION_TYPE_AS_SPECIAL = " SELECT ID_DPB_CND,NAM_DPB_CND,DES_DPB_CND,CDE_DPB_CND,NAM_DPB_VAR," +
																			"TXT_DPB_CHK_VAL,CDE_DPB_CND_TYP,TXT_DPB_REG_EX,CDE_DPB_STS " +
																		 " from DPB_CND where CDE_DPB_CND_TYP = 'S' and CDE_DPB_STS = 'A' ";	
	
	public final static String RETIRVE_ACTIVE_CONDTION_TYPE_AS_VEHICLE = " SELECT ID_DPB_CND,NAM_DPB_CND,DES_DPB_CND,CDE_DPB_CND,NAM_DPB_VAR," +
																		"TXT_DPB_CHK_VAL,CDE_DPB_CND_TYP,TXT_DPB_REG_EX,CDE_DPB_STS " +
																		 " from DPB_CND where CDE_DPB_CND_TYP = 'V' and CDE_DPB_STS = 'A' ";	
	
	
	public final static String RETIRVE_ACTIVE_CONDTION_TYPE_AS_BLOCKING = " SELECT ID_DPB_CND,NAM_DPB_CND,DES_DPB_CND,CDE_DPB_CND,NAM_DPB_VAR," +
																		"TXT_DPB_CHK_VAL,CDE_DPB_CND_TYP,TXT_DPB_REG_EX,CDE_DPB_STS " +
			 															  " from DPB_CND where CDE_DPB_CND_TYP = 'B' and CDE_DPB_STS = 'A' ";
	
	public static final String RETRIEVE_REPORT_CONTENT = "SELECT ID_DPB_RPT_CTNT, NAM_DPB_RPT_CTNT from DPB_RPT_CTNT WHERE CDE_DPB_STS='A' order by ID_DPB_RPT_CTNT";
	
	public static final String RETRIEVE_REPORT_QUERY = "SELECT ID_DPB_RPT_QRY, NAM_DPB_RPT_QRY  from DPB_RPT_QRY WHERE CDE_DPB_STS='A' order by ID_DPB_RPT_QRY";
	
	public static final String RETRIEVE_DLR_LIST = "SELECT ID_DLR FROM DEALER_FDRT where CDE_RGN not in ('00')";
	public static final String GET_KPI_FILE = " with LST_KPI (LST_UPDT,ID_DLR,QTR,YR,ID_KPI) AS (   " +
									" SELECT MAX(DTS_LAST_UPDT), ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI " +
									" from DPB_KPI_FIL_EXTRT dlrkpi where  DTE_FSCL_YR = ? and  DTE_FSCL_QTR = ?  " +									
									" group by ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI ) " +
									" select kpi.ID_DLR, kpi.DTE_FSCL_QTR, kpi.DTE_FSCL_YR, kpi.ID_KPI,kpi.PCT_KPI " +
									" from DPB_KPI_FIL_EXTRT kpi,LST_KPI lstkpi " +
									" where kpi.DTS_LAST_UPDT = lstkpi.LST_UPDT and kpi.ID_DLR = lstkpi.ID_DLR " + 
									" and kpi.DTE_FSCL_QTR = lstkpi.QTR and kpi.DTE_FSCL_YR = lstkpi.YR and kpi.ID_KPI = lstkpi.ID_KPI " ; 

}
