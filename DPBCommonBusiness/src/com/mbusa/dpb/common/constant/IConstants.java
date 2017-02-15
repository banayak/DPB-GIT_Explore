package com.mbusa.dpb.common.constant;

public interface IConstants {
	
	public static final String USER_ROLE_REPORTS = "1";
	public static final String USER_ROLE_TREASURY = "2";
	public static final String USER_ROLE_ADMIN = "3";
	public static final String USER_ROLE_SRD = "4";
	public static final String USER_ROLE_ACCOUNT = "5";
	
	public static final String FIXED_BONUS_PROCESS 			= "FB";
	public static final String FIXED_BONUS_PROCESS1 			= "FB";
	public static final String VARIABLE_BONUS_PROCESS 		= "VB";
	public static final String COMMISSION_BONUS_PROCESS 	= "CB";
	public static final String FIXED_PAYMENT_PROCESS 		= "FP";
	public static final String VARIABLE_PAYMENT_PROCESS 	= "VP";
	public static final String COMMISSION_PAYMENT_PROCESS 	= "CP";
	public static final String ADJUSTMENT_PROCESS 			= "AJ";
	public static final String TERMINATION_PROCESS 			= "TR";
	public static final String CANCELATION_PROCESS 			= "CL";
	public static final String LEADERSHIP_BONUS_PROCESS 	= "LB";
	public static final String LEADERSHIP_PAYMENT_PROCESS 	= "LP";
	public static final String FILE_PROCESS_NAME 		 	= "PF";
	public static final String REPORT_PROCESS_NAME 		 	= "RP";
	public static final String PAYMENT_TYPE_FIXED 	 		= "F";
	public static final String PAYMENT_TYPE_VARIABLE 		= "V";
	
	public static final String GENERAL_PROGRAM_TYPE_FIXED 	 = "Y";
	public static final String GENERAL_PROGRAM_TYPE_VARIABLE = "N";
	
	public static final String FILE_PRCS_ADJ				= "AJ";
	public static final String RETAIL_CANCEL_CODE	 		= "I2";
	public static final String CDE_CAR_STS			 		= "I2";
	public static final String CANCEL = "CANCEL";
	public static final String CDE_VEH_DDR_USE_Q3 		= "Q3";
	public static final String CDE_VEH_DDR_STS_I1 		= "I1";
	
	public static final String PROCESS_RESET				= "T";
	public static final String PROCESS_REPROCESS			= "P";
	
	public static final String COND_TYPE_B = "B";
	public static final String COND_TYPE_V = "V";
	public static final String COND_TYPE_S = "S";
	
        public static final String SPECIAL_PGM  = "S";
	public static final String CONSTANT_Y 	= "Y";
	public static final String CONSTANT_N 	= "N";
	
	public static final String PROGRAM_TYPE_GENERAL 	= "G";
	public static final String PROGRAM_TYPE_SPECIAL 	= "S";
	
	
	
	public static final String CREATED_USER_ID 		= "SYSTEM";
	public static final String LAST_UPDATED_USER_ID = "SYSTEM";
	
	public static final String STATUS_A = "A";
	public static final String STATUS_I = "I";
	public static final String STATUS_D = "D";
	
	/*public static enum VISTA_FILE_ENUM {
		NUM_PO,NUM_VIN,DTE_RTL,TME_RTL,ID_DLR,IND_USED_CAR,ID_EMP_PUR_CTRL,DTE_MDL_YR,TXT_MODL,CDE_RGN,
		NAM_RTL_CUS_LST, NAM_RTL_CUS_FIR, NAM_RTL_CUS_MID,DTE_TRANS, TME_TRANS, CDE_USE, AMT_MSRP_BASE, 
		AMT_MSRP_TOT_ACSRY, AMT_DLR_RBT,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_VEH_STS,CDE_NATL_TYPE,CDE_VEH_TYP,
		CDE_SLE_TYP,DTE_DEMO_SRV,CDE_CAR_STS,IND_USED_CAR2,AMT_MSRP_OPTS,FILLER_COLUMN
	}*/
	public static enum VISTA_FILE_ENUM {
		ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,
		CDE_VEH_DDR_USE,IND_USED_VEH_DDRS,DTE_RTL,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,
		DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,DTE_TRANS,
		TME_TRANS,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,IND_FLT,CDE_WHSLE_INIT_TYP,
		CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,ID_DPB_PGM,CDE_VEH_TYP,AMT_MSRP,FILLER_COLUMN,IND_AMG
	}
	public static final String PROC_STATUS_SUCCESS 		= "S";
	public static final String PROC_STATUS_IN_PROCESS 	= "I";
	public static final String PROC_STATUS_FAILURE 		= "F";
	public static final String PROC_STATUS_RESCHEDULE 	= "R";
	public static final String PROC_STATUS_RESET		= "T";
	public static final String PROC_STATUS_REPROCESS	= "P";
	public static final String SUB_PROC_DEALER_TERMINATED_STATUS			 = "T";
	public static final String SUB_PROC_CANCELATION_PO_STATUS		 	     = "C";
	public static final String SUB_PROC_ADJUESTMENT_STATUS		 	     	 = "A";
	
	public static final String BONUS_STATUS_CALCULATED 	 			 = "BC";
	public static final String BONUS_STATUS_PAID 	 				 = "BP";
	
	public static final String BONUS_STATUS_TERMINATED_CALCULATED 	 = "TC";
	public static final String BONUS_STATUS_TERMINATED_PAID		 	 = "TP";
	
	public static final String BONUS_STATUS_REPROCESS_CALCULATION 	 = "RC";
	
	
	
	
	public static final String LDR_BONUS_STATUS_CALCULATED  	 	 = "LC";
	public static final String LDR_BONUS_STATUS_PAID 	 		 	 = "LP";
	
	
	
	             
	               
	public static final String COMPANY_NAME = "MB";
	public static final String FILE_NAME 	= "DPB";
	public static final String FILE_TYPE 	= "Payment";
	public static final String BONUS_FILE_TYPE 	= "Bonus";
	public static final String FILE_EXT 	= "txt";
	public static final String DASH		    = "-";
	public static final String EMPTY_STR	 = "";
	public static final String BLANK_STR 	 = " ";
	public static final String UNDER_SCORE   = "_";
	public static final String DOT_STR		 = ".";
	public static final String PROFIT_CENTER = "1100";	
	public static final String SCHD_DAILY 	 = "D";
	public static final String SCHD_WEEKLY 	 = "W";	
	public static final String COLN_STR 	 = ":";
	public static final String CURRENCY 	 = "USD";		
	public static final String TOTAL_LINE 	 = "T";
	public static final String VENDOR_NAME 	 = "DEALERBONU";
	public static final String DEBIT 	 	 = "DB";
	public static final String CREDIT 	 	 = "CR";
	public static final String DOUBLE_ZERO_CONSTANT = "00";
	public static final String ZERO_CONSTANT ="0";
	public static final String LDRSHP ="LEADERSHIP BONUS";
	
	public static final String HEADER_LINE_COL_1 = "H";
	public static final String DLR_LINE_COL_1 	 = "0";
	public static final String BONUS_LINE_COL_1  = "1";	
	public static final String SCHD_BI_WEEKLY 	 = "B";
	public static final String SCHD_MONTH 		 = "M";
	public static final String SCHD_QUERTERLY 	 = "Q";
	public static final String SCHD_YEARLY 		 = "Y";
	
	public static final String TRIGGER_SYSTEM_INITIATION 	 	 = "S";
	public static final String TRIGGER_USER_INITIATION 		 = "U";
	public static final String TRIGGER_SYSTEM_INITIATION_DESC	 	 = "System Initiation";
	public static final String TRIGGER_USER_INITIATION_DESC 		 = "User Initiation";
	//public static final String PAYMENT_FILE_PATH 	= "C://DPB//Development//";
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String DATE_FORMAT_MMDDYYYY = "MMddyyyy";
	public static final String DATE_FORMAT_MMDDYY = "MMddyy";
	public static final String DATE_FORMAT_MMDDYYYY_WITH_SLASH = "MM/dd/yyyy";
	public static final String TIME_FORMAT_HHMMSS 	= "HH:mm:ss";	
	
	public static final String START_PAYMENT_PROCESS 			= "Payment Process started...";
	public static final String PAYMENT_PROCESS_SCHEDULED 		= "Payment Process schedule for:";
	public static final String PAYMENT_PROCESS_RETRIEVE_STARTED = "Payment Process ";
	public static final String PAYMENT_PROCESS_FILE_CREATED 	= "Payment Process schedule for:";
	
	public static final String PAYMENT_PROCESS_SCHEDULED_FOR_START_END_DATE = "Payment Process start date and end date.";
	public static final String PAYMENT_PROCESS_FILE_GENERATED_SUCCESSFULLY  = "Payment Process file generated successfully:";
	public static final String PAYMENT_PROCESS_FAIL_DUE_TO_ACCOUNTID_NOt_MAP ="Payment Process not successfully generated file due to missing of account Id mapping. :";
	public static final String PAYMENT_PROCESS_FILE_GENERATION_FAIL 		= "Payment Process file generation failed.";
	public static final String PAYMENT_PROCESS_NOTIFICATION_SENT 			= "Payment Process e-mail notification sent.";

	public static enum COND_TYPE {EQ,GT,GE,LT,LE,LD,TL,Y,N,R;}
	
	public static enum NET_ACCURAL_ENUM {
		FILLER_COLUMN, NUM_PO, AMT_DPB_LDRSP_BNS, CDE_CO, DTE_LDRSP_BNS_ACRL_POST;
	}
	
	public static enum KPI_FILE_ENUM {
		ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI, PCT_KPI;
	}
	
	public static final String VEHICLE_CONDITION = "VEH_COND";
	public static final String BLOCK_CONDITION 	 = "BLOCK";
	public static final String SPECIAL_CONDITION = "SPL_PROGRAM";	
	
	public static final String CANCELLED_STATUS  = "CANCELLED"; 
	public static final String YES   			 = "Y";
	public static final String SCHEDULARE_CALL   			 = "B";//Schedular
	public static final String MANUAL_CALL  			 = "M";//Schedular
	
	public static final String PCAR     = "P";       
	public static final String SMART    = "S";              
	public static final String VAN      = "V";               
	public static final String FTL      = "F";
	public static final String DP      = "DP";
	
	public static final String VISTA_BASE_FOLDER = "vista";
	public static final String ACCURAL_BASE_FOLDER = "accrual";
	public static final String KPI_BASE_FOLDER = "kpi";
	public static final String MTCH_UNUSED_AMT = "Mismatch of Unused Amount";
	public static final String START_LDRSP_BNS_PROCESS = "Leadership bonus process started";
	public static final String END_LDRSP_BNS_PROCESS = "Leadership bonus processed successfully";
	public static final String NEW_LINE = "<br/>";
	public static final String PRE_TAG = "<pre>    </pre>";
	
	public static final String DATE_RANGE_FOR_REPORTS = "DATE_RANGE_FOR_REPORTS";
	public static final String DATE_RANGE_FOR_ALL_DATES = "DATE_RANGE_FOR_ALL_DATES";
	public static final String DATE_RANGE_FOR_PRIO = "DATE_RANGE_FOR_PRIO";
	public static final String DATE_RANGE_FOR_PAY = "DATE_RANGE_FOR_PAY";
	public static final String DATE_RANGE_FOR_OWED = "DATE_RANGE_FOR_OWED";
	public static final String DATE_RANGE_FOR_ADJ_PAY = "DATE_RANGE_FOR_ADJ_PAY";
	public static final String DATE_RANGE_FOR_REP_ADJ = "DATE_RANGE_FOR_REP_ADJ";
	public static final String DATE_RANGE_FOR_ADJUSTMENTS = "DATE_RANGE_FOR_ADJUSTMENTS";
	public static final String VEHICLE_TYPE_RANGE_FOR_REPORTS = "VEHICLE_TYPE_RANGE_FOR_REPORTS";
	public static final String VEHICLE_TYPE1 = "VEHICLE_TYPE1";
	public static final String VEHICLE_TYPE2 = "VEHICLE_TYPE2";
	public static final String DATE_RANGE1 = "DATE_RANGE1";
	public static final String QUARTER_RANGE1 = "QUARTER_RANGE1";
	public static final String QUARTER_RANGE2 = "QUARTER_RANGE2";
	public static final String QUARTER_RANGE3 = "QUARTER_RANGE3";
	public static final String HEADER = "HEADER";
	
	//Fixed Margin-Start
	
	public static final String  ALL_DATE_RANGE= "ALL_DATE_RANGE";
	public static final String  MONTHLY_DATE_RANGE= "MONTHLY_DATE_RANGE";
	public static final String  QUARTERLY_DATE_RANGE= "QUARTERLY_DATE_RANGE";
	public static final String  YEARLY_DATE_RANGE= "YEARLY_DATE_RANGE";
	public static final String  BONUS_DATE_RANGE= "BONUS_DATE_RANGE";
	//Fixed Margin-End
	
	//AMG-Start
	public static final String  PROGRAM_ID= "PROGRAM_ID";
	//AMG-End
		
	public static final String DEALER_RANGE_FOR_REPORTS = "DEALER_RANGE_FOR_REPORTS";
	public static final String OLDPAYOUT_DEALER_RANGE_FOR_RPRT = "OLDPAYOUT_DEALER_RANGE_FOR_RPRT";
	public static final String DEFAULT_YEARS = "DEFAULT_YEARS";
	public static final String KPI_DATE_RANGE_FOR_REPORTS = "KPI_DATE_RANGE_FOR_REPORTS";
	public static final String KPI_DEALER_RANGE_FOR_REPORTS = "KPI_DEALER_RANGE_FOR_REPORTS";
	public static final String PO_RANGE_FOR_REPORTS = "PO_RANGE_FOR_REPORTS";
	public static final String VIN_RANGE_FOR_REPORTS = "VIN_RANGE_FOR_REPORTS";
	public static final int DEFAULT_LPP_DAILY = 60;
	public static final int DEFAULT_LPP_MONTHLY = 65;
	public static final int DEFAULT_LPP_YEARLY = 65;
	
	public static final String CVP_PAYOUT_1 = "CVP_Payout_1";
	public static final String CVP_PAYOUT_2 = "CVP_Payout_2";
	
	//Courtesy Vehicle Report-start
	
	public static final String CVP_PAYOUT_PAGINATION_ROWS = "CVP_PAYOUT_PAGINATION_ROWS";	
	public static final String CVP_PAYOUT_ALL_ROWS_EXCEL = "CVP_PAYOUT_ALL_ROWS_EXCEL";
	public static final String CVP_PAYOUT_NET = "CVP_PAYOUT_NET";
	public static final String CVP_COUNT = "CVP_COUNT";
	
	//Courtesy Vehicle Report-end
	
	//MBDeal Report-start

	public static final String MBDEAL_PAYOUT_PAGINATION_ROWS = "MBDEAL_PAYOUT_PAGINATION_ROWS";	
	public static final String MBDEAL_PAYOUT_ALL_ROWS_EXCEL = "MBDEAL_PAYOUT_ALL_ROWS_EXCEL";
	public static final String MBDEAL_PAYOUT_NET = "MBDEAL_PAYOUT_NET";
	public static final String MBDEAL_COUNT = "MBDEAL_COUNT";
		
	//MBDeal Report-end
	
	
	//Fixed Margin-Start
	public static final String FIXED_MARGIN ="FIXED_MARGIN";
	public static final String FIXED_MARGIN_PAYMENT ="FIXED_MARGIN_PAYMENT";
	public static final String FIXED_MARGIN_OWED ="FIXED_MARGIN_OWED";
	//Fixed Margin-End
	
	
	//Exception Report-Start
	
	public static final String EXCEPTION_PAGINATION_ROWS = "EXCEPTION_PAGINATION_ROWS";	
	public static final String EXCEPTION_ALL_ROWS_EXCEL = "EXCEPTION_ALL_ROWS_EXCEL";
	public static final String EXCEPTION_PAYOUT_NET = "EXCEPTION_PAYOUT_NET";
	public static final String EXCEPTION_COUNT = "EXCEPTION_COUNT";
	
	//Exception Report-End
	
	
	public static final String DLR_COMP_QTR_PAYOUT_OLD1 = "DlrCmplQtrPayoutsRprtOld2";
	public static final String DLR_COMP_QTR_PAYOUT_OLD2 = "DlrCmplQtrPayoutsRprtOld1";
	public static final String DLR_COMP_QTR_PAYOUT_OLD3 = "DlrCmplQtrPayoutsRprtOld3";
	
	/*
	 *MB DEAL PAYOUT  
	 */
	public static final String MBDEAL_PAYOUT1 = "MBDEAL_Payout1";
	public static final String MBDEAL_PAYOUT2 = "MBDEAL_Payout2";
	public static final String MBDEAL_PAYOUT3 = "MBDEAL_Payout3";
	
	public static final String VIN_Lookup_REPORT = "VIN_Lookup";
	
	public static final String DLR_RESERVE_EXCEPTION = "Dealer_Reserve_Exception_report";
	public static final String DLR_RESERVE_EXCEPTION_1 = "Dealer_Reserve_Exception_report_1";
	public static final String DEALER_RESERVE_SUMMARY_REPORT_1 = "Dealer_Reserve_Summary_Report_1";
	public static final String DEALER_RESERVE_SUMMARY_REPORT_2 = "Dealer_Reserve_Summary_Report_2";
	public static final String DEALER_RESERVE_SUMMARY_REPORT_3 = "Dealer_Reserve_Summary_Report_3";
	public static final String DEALER_RESERVE_SUMMARY_REPORT_4 = "Dealer_Reserve_Summary_Report_4";
	public static final String DLR_PERF_UNERND_BNS_RPRT_DFT = "DlrPerfUnerndBnsRprtDRAFT";
	public static final String DLR_PERF_UNERND_BNS_RPRT_DFT_1 = "DlrPerfUnerndBnsRprtDRAFT_1";
	public static final String DLR_COMPL_SUM_REPORT_1 = "Dealer_Compliance_Summary_Report_1";
	public static final String DLR_COMPL_SUM_REPORT_2 = "Dealer_Compliance_Summary_Report_2";
	public static final String DLR_COMPL_SUM_REPORT_3 = "Dealer_Compliance_Summary_Report_3";
	//public static final String DLR_COMPL_SUM_REPORT_3 = "Dealer_Compliance_Summary_Report_3";
	//public static final String DLR_COMPL_SUM_REPORT_4 = "Dealer_Compliance_Summary_Report_4";
	public static final String FLOOR_PLAN_SUMMARY_REPORT_1 = "Floor_Plan_Summary_Report_1";
	public static final String FLOOR_PLAN_SUMMARY_REPORT_2 = "Floor_Plan_Summary_Report_2";
	public static final String FLOOR_PLAN_SUMMARY_REPORT_3 = "Floor_Plan_Summary_Report_3";
	
	public static final String VEHICLE_DETAILS_REPROT_QUERY = "Vehicle_Details_Report_Query";
	public static final String VEHICLE_DETAILS_REPROT_QUERY_1 = "Vehicle_Details_Report_Query1";
	//Vehicle Details Report-start
	public static final String VEHICLE_DETAILS_REPROT_QUERY_2 = "Vehicle_Details_Report_Query2";
	//Vehicle Details Report-end
	
	public static final String PAGE_NUMBER = "PAGE_NUMBER";
	public static final String TOTAL_NO_RECORDS = "Total number of records ";
	public static final String FAIL 		= "F";
	public static final String SUCCESS 		= "S";
	public static final String ZEROS		= "0";
	public static final String MINUS_SIGN	= "-";
	public static final String MBDEAL_PGM	= "MBDEAL";
	public static final String CVP_PGM	= "CVP";
	public static final String DEPP_PGM	= "DEPP";
	public static final String TRUE = "true";
	public static final String ACCOUNT_ID_MISSING = "Account program mapping missing.";
	public static final String BR = "</BR>";
	public static final String ERROR_TEXT = "Error";
	public static final String IGNORE_TEXT = "IgnoreList";
	
	public static final String ERROR_FILE_COLUMN_CD = "Code";
	public static final String ERROR_FILE_COLUMN_PID = "Process Id";
	public static final String ERROR_FILE_COLUMN_PTYPE = "Process Type";
	public static final String ERROR_FILE_COLUMN_DLR = "Dealer Id";
	public static final String ERROR_FILE_COLUMN_VIN = "VIN";
	public static final String ERROR_FILE_COLUMN_ADATE = "Actual Date";
	public static final String ERROR_FILE_COLUMN_RS_STS = "RC Status";
	public static final String ERROR_FILE_COLUMN_VID = "Un BLK Id";
	public static final String ERROR_FILE_COLUMN_PGM_ID = "Pgm Id";
	public static final String ERROR_FILE_COLUMN_REASON = "Reason";
	public static final String VEH_TYP_LIGHTTRUCK_DES = "Freightliner";
	
	public static final String CAR_VEH_TYP_CODE_PROP_NAME = "dpb.veh.typ.cde.pcar";
	public static final String CAR_VEH_TYP_DESC_PROP_NAME = "dpb.veh.typ.cde.desc.pcar";
	public static final String SMART_VEH_TYP_CODE_PROP_NAME = "dpb.veh.typ.cde.smart";
	public static final String SMART_VEH_TYP_DESC_PROP_NAME = "dpb.veh.typ.cde.desc.smart";
	public static final String VAN_VEH_TYP_CODE_PROP_NAME = "dpb.veh.typ.cde.van";
	public static final String VAN_VEH_TYP_DESC_PROP_NAME = "dpb.veh.typ.cde.desc.van";
	public static final String FRIGHTLINER_VEH_TYP_CODE_PROP_NAME = "dpb.veh.typ.cde.freightliner";
	public static final String FRIGHTLINER_VEH_TYP_DESC_PROP_NAME = "dpb.veh.typ.cde.desc.freightliner";
	
	public static final String BNS_MSG_CALC_AMT_GREATER_THAN_MAX_ELIGIBILITY = "Calculated amount is greater than Maximum eligible amount";
	public static final String CANCEL_DONT_HAVE_RTL = "This cancel doesn't have the retail which is DPB bonus processed";
	
	//Property file name which maintains automatic scheduler status - Kshitija
	public static final String PROPERTY_FILE_NAME_FOR_SCHEDULER_STATUS = "dpb";
	
	public static final String AMG_PERF_CENTER_RPT_NM = "AMG_PERF_CENTER_RPT_NM";
	public static final String AMG_ELITE_CENTER_RPT_NM = "AMG_ELITE_CENTER_RPT_NM";
	//Unearned Performance Bonus calculation start
	public static final String DLR_PERF_UNERND_BNS_RPRT_DFT_NEW = "DlrPerfUnerndBnsRprtDRAFTNew";
	public static final String DLR_PERF_UNERND_BNS_RPRT_DFT_NEW_1 = "DlrPerfUnerndBnsRprtDRAFTNew_1";
	//Unearned Performance Bonus calculation end
	//Dealer Compliance Quarterly Payouts Report - Start
	public static final String DLR_COMP_QTR_PAY_RPT_QRY_NAME= "DCQPR_QUERY";
	//Dealer Compliance Quarterly Payouts Report - End
	
	//Vehicle Details Report-start
	public static final String VEHICLE_DETAILS_REPROT_COUNT = "VEHICLE_DETAILS_REPROT_COUNT";
	
	public static final int DEFAULT_PAGE_SIZE = 100;
	public static final String PAGE_START_INDEX = "PAGE_START_INDEX";
	public static final String PAGE_END_INDEX = "PAGE_END_INDEX";
	public static final Integer[] PAGE_SIZE_LIST= {DEFAULT_PAGE_SIZE, 250,200,150,50,25};

	//Vehicle Details Report-end
	//Dealer Performance Unearned Bonus Report - FNC27 - start
	public static final String UNEARNED_REPORT_COUNT = "UNEARNED_REPORT_COUNT";
	public static final String UNEARNED_REPORT_TOTAL = "UNEARNED_REPORT_TOTAL";
	public static final String UNEARNED_REPORT_PAGINATION = "UNEARNED_REPORT_PAGINATION";
	public static final String UNEARNED_REPORT_EXCEL = "UNEARNED_REPORT_EXCEL";

	//Dealer Performance Unearned Bonus Report - FNC27 - end
	//Dealer Compliance Summary Report - Start
		public static final String DLR_COMPL_SUM_REPORT = "DLR_COMPL_SUM_REPORT";
		public static final int DLR_COMPL_SUM_REPORT_ID = 17;
		/*public static final List<String> DLR_COMPL_SUM_REPORT_SUBTOT_TYPES = 
			    Collections.unmodifiableList(Arrays.asList("Retails", "MBDEAL","Ret.Adjs","MBDEAL A"));*/
		//Dealer Compliance Summary Report - End
	
	
	
	
}
