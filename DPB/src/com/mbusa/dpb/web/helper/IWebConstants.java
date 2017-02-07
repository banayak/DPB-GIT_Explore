package com.mbusa.dpb.web.helper;

public interface IWebConstants {
	
	public int DASHBOARD_ID  		 	= 1;
	public int FILE_PROCESSING_ID    	= 2;
	public int BONUS_PROCESSING_ID   	= 3;
	public int REPORTS_ID  				= 4;
	public int DEFINITION_ID   	 		= 5;
	public static final String PAYMENT_TYPE_VARIABLE = "VARIABLE PAYMENT";
	public static final String PAYMENT_TYPE_FIXED = "FIXED PAYMENT";
	
	public static final String PCAR     = "P";       
	public static final String SMART    = "S";              
	public static final String VAN      = "V";               
	public static final String FTL      = "F"; 
	
	public static final String VEHICLE_TYP_B   = "B"; 
	public static final String VEHICLE_TYP_V   = "V"; 
	public static final String EMPTY_STR       = ""; 
	
	public static final String CONSTANT_Y ="Y";
	public static final String CONSTANT_N ="N";
	public static final String YES ="Yes";
	public static final String NO ="No";
	
	public static final String REQUEST_CTYPE 		= "CType";       
	public static final String CONDITIONS_LIST	    = "CONDITIONS_LIST";              
	public static final String VC_LIST  			= "VC_LIST";               
	public static final String DEFAULT_ERROR_PAGE   = "errorPage";	
	public static final String MENU_ITEMS  			= "MENU_ITEMS"; 	
	public static final String TREE_DISPLAY 		= "displayTree";	
	public static final String jspExe 				= "javax.servlet.jsp.jspException";	
	public static final String DPB_ERROR_URL 		= "errorPage";
	public static final String HOME_PAGE  			= "HomePage";
	public static final String MSG_SAVE  			= "Record Saved Successfully.";
	public static final String RPT_SAVE_NO_SUB_RPT	= "Record Created Successfully(Does not contain sub reports)";
	public static final String MSG_UPDATE  			= "Record Updated Successfully.";
	public static final String SM_USER_SESSION = "SM_USER";
	public static final String USER_DEPT_SESSION = "USER_DEPT";
	public static final String USER_ROLE		 = "USER_ROLE";
	public static final String USER_INFO_SESSION = "USER_INFO";
	public static final String DEFAULT_USER_ID = "DUser";
	public static final String ACTIVE = "A";
	public static final String DRAFT = "D";
	public static final String INACTIVE = "I";
	public static final String FALSE = "false";
	public static final String LDRSP_PGM_INST_SUC="Leadership Bonus Program created successfully.";
	public static final String LDRSP_PGM_UPT_SUC = "Leadership Bonus Program updated successfully.";
	public static final String LDRSP_PGM_INACT_INST_SUC="Leadership Bonus Program has been InActive Successfully.";
	public static final String LDRSP_ACRL_AMT_NO="No unused amount found for this period. Please modify Accrual Start Date or Accrual End Date.";
	public static final String LDRSP_ELEG_COUNT="No Leadership Eligible Units found for this period. Please modify Retail Start Date or Retail End Date.";
	public static final String LDRSP_PGM_REINST_SUC="Cannot submit Active Leadership Bonus Program again.";
	public static final String LDRSP_PGM_ST_EN_COMP="Accrual End Date should be greater than Accrual Start Date.";
	public static final String LDRSP_PGM_RTLST_TRLEN_COMP="Retail End Date should be greater than Retail Start Date.";
	public static final String LDRSP_PGM_VEH = "Select at least one Applicable Vehicle.";
	public static final String LDRSP_PGM_PRCS_EN_COMP="Process Date should be greater than Accrual End date.";
	public static final String LDRSP_PGM_RTL_CAL_FALSE="Retail Calendar is not available in this range. Please modify Process Date again.";
	public static final String LDRSP_PGM_INACT_PGM="Cannot create a new Inactive Leader Bonus Program.";
	public static final String LDRSP_PGM_DATE_FMT="MM/dd/yyyy";
	public static final String PGM_ACCID_MAP="Program updated successfully.";
	public static final String BUSS_EXC_SAVE = "BusinessException occured while saving/update.";
	public static final String BUSS_EXC_RETV = "Business Exception occured while retrieving record";
	
	public static final String FILE_DEFN_NO_ACT_FMT = "No 'Active' file format found, please create active file format";
	public static final String RPT_DEFN_NO_ACT_RPT = "No 'Active' sub reports, please create Query and Content definitions";
	public static final String FMT_NO_MAP_DETL = "Record does not contain file column maping details";
	
	public static final String RPT_CTNT_RERS = "REPORT_CONTENT";
	public static final String RPT_QRY_RERS = "REPORT_QRY";
	
	public static final String TBL_NAM_KPI = "DPB_KPI_FIL_EXTRT";
	public static final String TBL_NAM_VISTA = "DPB_UNBLK_VEH";
	public static final String TBL_NAM_LDRSP_ACRL = "LDRSP_BNS_ACRL";
	public static final String DESC_EXPSN = "(?<=\\G.{100})";
	public static final String PO_EXPSN = "(?<=\\G.{40})";
	public static final String RSN_EXPSN = "(?<=\\G.{75})";
	public static final String DISP_INP_PROC_STAT="COMPLETED";
	public static final String INVALIDE_USER 		= "INVALIDUSER";
	public static final String LDRSP_PGM_PROC_DATE = "Process Date should be greater than today's date.";
	public static final String ADMIN_ROLE_ACCESS ="E";
	public static final String TREASURY_ROLE_ACCESS ="E";
	public static final String VIEWONLY_ROLE_ACCESS ="V";
	/*
	1	REPORT USER                                  
	2	NORMAL USER                             
	3	ADMIN                                   
	4	BUSINESS USER
	5   ACCOUNTING USER
	1	Report User                             
	2	NORMAL USER                             
	3	ADMIN                                   
	4	BUSINESS USER                           
	5	Accounting User                         */
	public static final String REPORT_USER_ROLE_CODE = "1";
	public static final String NORMAL_USER_ROLE_CODE = "2";	
	public static final String ADMIN_ROLE_CODE = "3";
	public static final String BUSINESS_USER_ROLE_CODE = "4";
	public static final String ACCOUNTING_USER_ROLE_CODE = "5";
	public static final String NO_PROGRAMS_DEFINED = "No programs are defined";
	public static final String NO_FORMATS_DEFINED = "No formats are defined";
	public static final String NO_FILE_DEF_DEFINED = "No file definitions are defined";
	public static final String NO_REPORT_DEF_DEFINED = "No report definitions are defined";
	public static final String NO_RPT_CTNT_DEFINED = "No report contents are defined";
	public static final String USER_INITIATION = "U";
	public static final String SYSTEM_INITITION = "S";
	
}
