/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: IBonusProcessingQueryConstants.java
 * Program Version			: 1.0
 * Program Description		: This class is used Query used for Bonus.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 31, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.constant;

public interface IBonusProcessingQueryConstants {

	
	public static final String GET_CDDB_KPI_LIST =     " SELECT ID_DLR,QTR_FSCL_KPI," +
													// " FSCL_YEAR ," +
													   " ID_KPI, PCT_KPI " +			
													   " FROM KPI_FILE_CDDB  " +												   " WHERE DTE_DLR_TERM  NOT NULL";

	
	public static final String GET_DAILY_VISTA_RETAIL_DATA = " SELECT NUM_PO ,NUM_VIN,DTE_RTL,TME_RTL,CDE_VEH_STAT,ID_DLR,IND_USED_CAR,DTE_DEMO_SRV," +
															 " ID_EMP_PUR,DTE_MDL_YR, NAM_MDL, IND_USED_CARS,	" +
															 " CDE_REG, NAM_LAST, NAM_FST,	NAM_MID, DTE_TRANS," +
															 " TME_TRANS, AMT_MSRP_BAS,AMT_MSRP_OPT,AMT_RBT," +
															 " IND_FLT,TYP_WS_INIT FROM VSTA_RTL_FILE";
	
	
	
	/*	public static final String RETRIVR_DLR_PAYMENT =" with payment (bonusVal ,NUM_PO,ID_DPB_PGM ,ID_DLR ,AMT_DPB_MAX_BNS_ELG) as " +
												" ( select sum(AMT_DPB_BNS_CALC) as bonusVal ,NUM_PO,ID_DPB_PGM ,ID_DLR ,AMT_DPB_MAX_BNS_ELG" +
												" from  DPB_CALC  group by rollup (NUM_PO,ID_DLR,ID_DPB_PGM,AMT_DPB_MAX_BNS_ELG) order by NUM_PO "+
												" ), temp ( bonusVal ,NUM_PO,ID_DPB_PGM ,ID_DLR ,AMT_DPB_MAX_BNS_ELG) as " +
												" ( select bonusVal ,NUM_PO,ID_DPB_PGM ,ID_DLR "+
												" ,AMT_DPB_MAX_BNS_ELG from payment p where (p.ID_DLR is not null) "+
												" except "+
												" select bonusVal ,NUM_PO,ID_DPB_PGM ,ID_DLR ,AMT_DPB_MAX_BNS_ELG from payment p"+  
												" where AMT_DPB_MAX_BNS_ELG is null and ID_DPB_PGM is not null "+
												" order by  NUM_PO, ID_DPB_PGM "+
												" )"+
												" select p.NUM_PO,p.BONUSVAL,p.ID_DPB_PGM,p.ID_DLR,v.NUM_VIN,v.TXT_MODL,"+
												" v.DTE_MDL_YR,v.CDE_VEH_TYP from temp p , DPB_VEH_RTL_EXTRT v" +
												" " + //,DPB_PGM_CFC_ACCT_REL acc " +
												" where p.NUM_PO = v.NUM_PO  " +
												//" and acc.ID_DPB_PGM = p.ID_DPB_PGM "
												"";*/
	
	public static final String RETRIVR_DLR_PAYMENT = "with payment (bonusVal ,ID_DPB_UNBLK_VEH,ID_DPB_PGM ,ID_DLR ,AMT_DPB_MAX_BNS_ELG) as 	"+
													" ( 	"+
													"     select sum(AMT_DPB_BNS_CALC) as bonusVal,ID_DPB_UNBLK_VEH,ID_DPB_PGM,ID_DLR ,AMT_DPB_MAX_BNS_ELG	"+ 
													    " from  DPB_CALC  group by rollup (ID_DPB_UNBLK_VEH,ID_DLR,ID_DPB_PGM,AMT_DPB_MAX_BNS_ELG) 	"+
													    " order by ID_DPB_UNBLK_VEH 	"+
													" ), 	"+
													" temp ( bonusVal ,ID_DPB_UNBLK_VEH,ID_DPB_PGM ,ID_DLR ,AMT_DPB_MAX_BNS_ELG) as	"+ 
													" ( 	"+
													"     select bonusVal ,ID_DPB_UNBLK_VEH,ID_DPB_PGM ,ID_DLR,AMT_DPB_MAX_BNS_ELG	"+ 
													        " from payment p where (p.ID_DLR is not null) 	"+
														" except 	"+
													    " select bonusVal ,ID_DPB_UNBLK_VEH,ID_DPB_PGM ,ID_DLR ,AMT_DPB_MAX_BNS_ELG from payment p	"+ 
													    " where AMT_DPB_MAX_BNS_ELG is null and ID_DPB_PGM is not null 	"+
													    " order by  ID_DPB_UNBLK_VEH, ID_DPB_PGM	"+
													" )	"+
													" select p.ID_DPB_UNBLK_VEH,p.BONUSVAL,p.ID_DPB_PGM,p.ID_DLR,v.NUM_VIN,v.DES_MODL,	"+
													        " v.DTE_MDL_YR,v.CDE_VEH_TYP from temp p , DPB_UNBLK_VEH v 	"+
													        " where p.ID_DPB_UNBLK_VEH = v.ID_DPB_UNBLK_VEH ";	

/*public static final String PROCESS_START_VIEW_QUERY = "with RTL_CAL (PROC_DATE, RTL_ID) as (  " +
															" select DTE_CAL, ID_DAY from DPB_RTL_CAL_DY_DIM where DTE_CAL <= current date), " +
															" PROC (PROC_ID, PROG_ID, PROG_TYP, PROC_DATE, PROC_ACT_DTE_ID, PROC_ACT_DTE, OVER_RTL_ID, " + 
															" OVER_TME_PROC, OVER_STATUS,  OVER_PROC_DATE, LST_UPD_USR) as (  " +
															" select proc.ID_DPB_PROC, proc.ID_DPB_APP_ENT, proc.CDE_DPB_PROC_TYP, rtl.PROC_DATE, " + 
															" proc.DTE_DPB_ACTL_PROC, (select DTE_CAL from DPB_RTL_CAL_DY_DIM rtlCal where  " +
															" rtlCal.ID_DAY = proc.DTE_DPB_ACTL_PROC), proc.ID_OVRD_PROC_DTE, proc.TME_OVRD_PROC,  " +
															" proc.CDE_DPB_OVRD_TRGR,  (select DTE_CAL from DPB_RTL_CAL_DY_DIM rtlCal  " +
															" where rtlCal.ID_DAY = proc.ID_OVRD_PROC_DTE), proc.ID_LAST_UPDT_USER  " +
															" from DPB_PROC proc, RTL_CAL rtl  " +
															" where proc.ID_DAY in (rtl.RTL_ID)),  " +
															" PROC_LOG (ID_PROC_LOG, PROC_ID) as (  " +
															" select max(log.ID_DPB_PROC_LOG), log.ID_DPB_PROC  " +
															" from DPB_PROC_LOG log, PROC proc  " +
															" where log.ID_DPB_PROC in (proc.PROC_ID)  " +
															" group by log.ID_DPB_PROC),  " +
															" PROC_LOG_TXT (ID_PROC_LOG, PROC_ID, LOG_STATUS, LOG_TXT, LST_PROC_DATE) as (  " +
															" select log.ID_DPB_PROC_LOG, log.ID_DPB_PROC, log.CDE_DPB_PROC_STS, log.TXT_DPB_PROC_MSG, date(log.DTS_LAST_UPDT) as LST_PROC_DATE  " +
															" from DPB_PROC_LOG log, PROC_LOG plog  " +
															" where log.ID_DPB_PROC_LOG = plog.ID_PROC_LOG ), " +
															" PROC_LOG_FINAL (PROC_ID, LOG_STATUS, LOG_TXT, LST_PROC_DATE) as ( " + 
															" select proc.PROC_ID, log.LOG_STATUS, log.LOG_TXT, log.LST_PROC_DATE " + 
															" from PROC proc " + 
															" left outer join PROC_LOG_TXT log on proc.PROC_ID = log.PROC_ID ), " +
															" FILE_PROC (PROC_ID, PROG_ID, PROG_TYP, PROC_ACT_DTE_ID, PROC_ACT_DTE, " +
															" PROG_NAME, TME_FILE_IN, LOG_STATUS, LOG_TXT, FINAL_TRIGGER, LST_PROC_DATE) as ( " + 
															" select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_ACT_DTE_ID, proc.PROC_ACT_DTE, " +
															" file.NAM_DPB_FIL, file.TME_FILE_IN, log.LOG_STATUS, log.LOG_TXT, " +
															" CASE WHEN " + 
															" (file.CDE_DPB_TRGR = 'M' or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) " + 
															" or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR = 'SYSTEM')) " + 
															" THEN 'M' ELSE " + 
															" CASE WHEN " + 
															" (file.CDE_DPB_TRGR = 'A' or (proc.OVER_STATUS = 'A' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date)) " + 
															" THEN 'A' ELSE NULL END END, log.LST_PROC_DATE " + 
															" from PROC proc, DPB_FILE file, PROC_LOG_FINAL log  " +
															" where file.ID_DPB_FILE in (proc.PROG_ID) and proc.PROG_TYP in ('PF') " + 
															" and proc.PROC_ID = log.PROC_ID " + 
															" and (file.CDE_DPB_STS = 'A' or (file.CDE_DPB_STS = 'I' and file.DTE_INACT > current date))), " + 
															" BONUS_PROC (PROC_ID, PROG_ID, PROG_TYP, PROC_ACT_DTE_ID, PROC_ACT_DTE, " + 
															" PROG_NAME, LOG_STATUS, LOG_TXT, FINAL_TRIGGER, LST_PROC_DATE) as (select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_ACT_DTE_ID, proc.PROC_ACT_DTE, "+ 
															" 'ADJUSTMENT PROCESS', log.LOG_STATUS, log.LOG_TXT, " + 
															" CASE WHEN " + 
															" (file.CDE_DPB_TRGR = 'M' or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) " + 
															" or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR = 'SYSTEM')) " + 
															" THEN 'M' ELSE  " +
															" CASE WHEN " + 
															" (file.CDE_DPB_TRGR = 'A' or (proc.OVER_STATUS = 'A' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date)) " + 
															" THEN 'A' ELSE NULL END END, log.LST_PROC_DATE  " +
															" from PROC proc, DPB_FILE file, PROC_LOG_FINAL log  " +
															" where file.ID_DPB_FILE in (proc.PROG_ID) and proc.PROG_TYP in ('AJ') " + 
															" and proc.PROC_ID = log.PROC_ID  " +
															" and (file.CDE_DPB_STS = 'A' or (file.CDE_DPB_STS = 'I' and file.DTE_INACT > current date)) " +
															" union " + 
															" select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_ACT_DTE_ID, proc.PROC_ACT_DTE, " + 
															" pgm.NAM_DPB_PGM, log.LOG_STATUS, log.LOG_TXT, " + 
															" CASE WHEN " + 
															" (pgm.CDE_COMSN_BNS_TRGR = 'M' or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) " + 
															" or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR = 'SYSTEM')) " + 
															" THEN 'M' ELSE " + 
															" CASE WHEN " + 
															" (pgm.CDE_COMSN_BNS_TRGR = 'A' or (proc.OVER_STATUS = 'A' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date)) " + 
															" THEN 'A' ELSE NULL END END, log.LST_PROC_DATE " + 
															" from PROC proc, DPB_PGM pgm, PROC_LOG_FINAL log " + 
															" where pgm.ID_DPB_PGM in (proc.PROG_ID) and proc.PROG_TYP in ('CB') " + 
															" and proc.PROC_ID = log.PROC_ID  " +
															" and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > current date)) " + 
															" union  " +
															" select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_ACT_DTE_ID, proc.PROC_ACT_DTE, " +
															" pgm.NAM_DPB_PGM, log.LOG_STATUS, log.LOG_TXT, " + 
															" CASE WHEN " + 
															" (pgm.CDE_FXD_BNS_TRGR = 'M' or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) " + 
															" or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR = 'SYSTEM'))  " +
															" THEN 'M' ELSE  " +
															" CASE WHEN  " +
															" (pgm.CDE_FXD_BNS_TRGR = 'A' or (proc.OVER_STATUS = 'A' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date)) " + 
															" THEN 'A' ELSE NULL END END, log.LST_PROC_DATE " +
															" from PROC proc, DPB_PGM pgm, PROC_LOG_FINAL log  " +
															" where pgm.ID_DPB_PGM in (proc.PROG_ID) and proc.PROG_TYP in ('FB') " + 
															" and proc.PROC_ID = log.PROC_ID  " +
															" and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > current date)) " + 
															" union " +  
															" select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_ACT_DTE_ID, proc.PROC_ACT_DTE, " + 
															" pgm.NAM_DPB_PGM, log.LOG_STATUS, log.LOG_TXT, " + 
															" CASE WHEN " + 
															" (pgm.CDE_VAR_BNS_TRGR = 'M' or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) " + 
															" or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR = 'SYSTEM')) " + 
															" THEN 'M' ELSE " +  
															" CASE WHEN  " +
															" (pgm.CDE_VAR_BNS_TRGR = 'A' or (proc.OVER_STATUS = 'A' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date)) " + 
															" THEN 'A' ELSE NULL END END, log.LST_PROC_DATE " + 
															" from PROC proc, DPB_PGM pgm, PROC_LOG_FINAL log " +  
															" where pgm.ID_DPB_PGM in (proc.PROG_ID) and proc.PROG_TYP in ('VB') " + 
															" and proc.PROC_ID = log.PROC_ID " + 
															" and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > current date)) " + 
															" union " +  
															" select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_ACT_DTE_ID, proc.PROC_ACT_DTE, " + 
															" ldr.NAM_LDRSP_BNS, log.LOG_STATUS, log.LOG_TXT, " + 
															" case when " + 
															" (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) " +  
															" or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR = 'SYSTEM') " + 
															" THEN 'M' ELSE " + 
															" CASE WHEN " + 
															" (proc.OVER_STATUS = 'A' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) " + 
															" THEN 'A' ELSE NULL END END, log.LST_PROC_DATE " + 
															" from PROC proc, LDRSP_BNS_PGM ldr, PROC_LOG_FINAL log " + 
															" where ldr.ID_LDRSP_BNS_PGM = proc.PROG_ID and proc.PROG_TYP = 'LB' " + 
															" and proc.PROC_ID = log.PROC_ID " + 
															" and (ldr.CDE_DPB_STS = 'A' or (ldr.CDE_DPB_STS = 'I' and ldr.DTE_INACT > current date))), " + 
															" PAYMENT_PROC (PROC_ID, PROG_ID, PROG_TYP, PROC_ACT_DTE_ID, PROC_ACT_DTE, " + 
															" PROG_NAME, LOG_STATUS, LOG_TXT, FINAL_TRIGGER) as ( " +  
															" select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_ACT_DTE_ID, proc.PROC_ACT_DTE, " + 
															" pgm.NAM_DPB_PGM, log.LOG_STATUS, log.LOG_TXT, " + 
															" CASE WHEN " +  
															" (pgm.CDE_COMSN_PMT_TRGR = 'M' or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) " + 
															" or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR = 'SYSTEM'))  " +
															" THEN 'M' ELSE " +   
															" CASE WHEN " +  
															" (pgm.CDE_COMSN_PMT_TRGR = 'A' or (proc.OVER_STATUS = 'A' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date)) " + 
															" THEN 'A' ELSE NULL END END   " +
															" from PROC proc, DPB_PGM pgm, PROC_LOG_FINAL log " +   
															" where pgm.ID_DPB_PGM in (proc.PROG_ID) and proc.PROG_TYP in ('CP') " +  
															" and proc.PROC_ID = log.PROC_ID " +  
															" and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > current date)) " + 
															" union   " +
															" select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_ACT_DTE_ID, proc.PROC_ACT_DTE, " + 
															" pgm.NAM_DPB_PGM, log.LOG_STATUS, log.LOG_TXT, " +  
															" CASE WHEN " +   
															" (pgm.CDE_FXD_PMT_TRGR = 'M' or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) " + 
															" or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR = 'SYSTEM')) " + 
															" THEN 'M' ELSE " +   
															" CASE WHEN " +  
															" (pgm.CDE_FXD_PMT_TRGR = 'A' or (proc.OVER_STATUS = 'A' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date)) " + 
															" THEN 'A' ELSE NULL END END " +  
															" from PROC proc, DPB_PGM pgm, PROC_LOG_FINAL log " +  
															" where pgm.ID_DPB_PGM in (proc.PROG_ID) and proc.PROG_TYP in ('FP') " + 
															" and proc.PROC_ID = log.PROC_ID " + 
															" and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > current date)) " + 
															" union  " +
															" select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_ACT_DTE_ID, proc.PROC_ACT_DTE, " + 
															" pgm.NAM_DPB_PGM, log.LOG_STATUS, log.LOG_TXT, " + 
															" CASE WHEN " +  
															" (pgm.CDE_VAR_PMT_TRGR = 'M' or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) " + 
															" or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR = 'SYSTEM'))  " +
															" THEN 'M' ELSE " +  
															" CASE WHEN  " +
															" (pgm.CDE_VAR_PMT_TRGR = 'A' or (proc.OVER_STATUS = 'A' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date)) " +  
															" THEN 'A' ELSE NULL END END  " +
															" from PROC proc, DPB_PGM pgm, PROC_LOG_FINAL log " +  
															" where pgm.ID_DPB_PGM in (proc.PROG_ID) and proc.PROG_TYP in ('VP') " +  
															" and proc.PROC_ID = log.PROC_ID " + 
															" and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > current date)) " + 
															" union " + 
															" select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_ACT_DTE_ID, proc.PROC_ACT_DTE, " + 
															" ldr.NAM_LDRSP_BNS, log.LOG_STATUS, log.LOG_TXT, " + 
															" case when " +  
															" (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) " + 
															" or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR = 'SYSTEM')  " +
															" THEN 'M' ELSE   " +
															" CASE WHEN  " +
															" (proc.OVER_STATUS = 'A' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) " + 
															" THEN 'A' ELSE NULL END END  " +
															" from PROC proc, LDRSP_BNS_PGM ldr, PROC_LOG_FINAL log " + 
															" where ldr.ID_LDRSP_BNS_PGM = proc.PROG_ID and proc.PROG_TYP = 'LP' " + 
															" and proc.PROC_ID = log.PROC_ID " + 
															" and (ldr.CDE_DPB_STS = 'A' or (ldr.CDE_DPB_STS = 'I' and ldr.DTE_INACT > current date))), " + 
															" REPORT_PROC (PROC_ID, PROG_ID, PROG_TYP, PROC_ACT_DTE_ID, PROC_ACT_DTE, " + 
															" PROG_NAME, LOG_STATUS, LOG_TXT, FINAL_TRIGGER) as ( " +  
															" select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_ACT_DTE_ID, proc.PROC_ACT_DTE, " + 
															" rpt.NAM_DPB_RPT, log.LOG_STATUS, log.LOG_TXT,  " +
															" CASE WHEN   " +
															" (rpt.CDE_DPB_TRGR = 'M' or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) " + 
															" or (proc.OVER_STATUS = 'M' and proc.LST_UPD_USR = 'SYSTEM'))  " +
															" THEN 'M' ELSE   " +
															" CASE WHEN  " +
															" (rpt.CDE_DPB_TRGR = 'A' or (proc.OVER_STATUS = 'A' and proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date)) " + 
															" THEN 'A' ELSE NULL END END  " +
															" from PROC proc, DPB_RPT rpt, PROC_LOG_FINAL log " + 
															" where rpt.ID_DPB_RPT = proc.PROG_ID and proc.PROG_TYP = 'RP' " + 
															" and proc.PROC_ID = log.PROC_ID  " +
															" and (rpt.CDE_DPB_STS = 'A' or (rpt.CDE_DPB_STS = 'I' and rpt.DTE_INACT > current date))) ";*/

/*public static final String PROCESS_NORMAL_BONUS_VIEW_START ="  select bp.PROC_ID, bp.PROG_NAME, bp.PROG_ID, bp.PROG_TYP, bp.LOG_STATUS, bp.LOG_TXT, sts.DES_DPB_PROC_STS AS DES_DPB_PROC_STS,bp.PROC_ACT_DTE, "+  
															"	case when bp.FINAL_TRIGGER = 'S' or (bp.LOG_STATUS not in ('F') and bp.LOG_STATUS is not null) then 'V' else "+  
															"	case when (select count(bp1.PROC_ACT_DTE) from BONUS_PROC bp1 where bp1.PROG_TYP in ('AJ','CB','FB','VB') and (bp1.LOG_STATUS in ('F','I','P','T') or bp1.LOG_STATUS is null) and bp.PROC_ACT_DTE > bp1.PROC_ACT_DTE) > 0 then 'V' else "+  
															"	case when (select count(pf.PROC_ACT_DTE) from FILE_PROC pf where (pf.LOG_STATUS in ('F','I','P','T') or pf.LOG_STATUS is null) and bp.PROC_ACT_DTE >= pf.PROC_ACT_DTE) > 0 then 'V' else "+  
															"	'S' "+  
															"	end end end as FINAL_STATUS, "+ 
															"	case when bp.LST_PROC_DATE != current date then '' else "+  
															"	case when bp.LOG_STATUS NOT IN ('S','T') then '' else "+  
															"	case when bp.LOG_STATUS is null then '' else "+  
															"	case when (select count(pp.PROC_ACT_DTE) from PAYMENT_PROC pp where pp.LOG_STATUS = 'S' and bp.PROC_ACT_DTE = pp.PROC_ACT_DTE) > 0 then '' else "+ 
															"	'T' "+ 
															"	end end end end as RESET_STATUS, "+ 
															"	case when bp.LOG_STATUS != 'P' then '' else "+  
															"	case when bp.LOG_STATUS is null then '' else "+  
															"	case when (select count(pp.PROC_ACT_DTE) from PAYMENT_PROC pp where pp.LOG_STATUS = 'S' and bp.PROC_ACT_DTE = pp.PROC_ACT_DTE) > 0 then '' else "+ 
															"	'P' "+ 
															"	end end end as REPROC_STATUS "+ 
															"	from BONUS_PROC bp "+   
															"	left outer join DPB_PROC_STS sts on bp.LOG_STATUS = sts.CDE_DPB_PROC_STS where bp.PROG_TYP in ('AJ','CB','FB','VB') "+  
															"	order by bp.PROC_ACT_DTE ";*/
public static final String PROCESS_NORMAL_BONUS_VIEW_START = " ,BONUS_FINAL (PROC_ID, PROG_NAME, PROG_ID, PROG_TYP, LOG_STATUS, LOG_TXT, DES_DPB_PROC_STS, PROC_ACT_DTE,FINAL_STATUS,RESET_STATUS,REPROC_STATUS) as (" +
		" select bp.PROC_ID, bp.PROG_NAME, bp.PROG_ID, bp.PROG_TYP, bp.LOG_STATUS, bp.LOG_TXT, sts.DES_DPB_PROC_STS AS DES_DPB_PROC_STS,bp.PROC_ACT_DTE, " +
		" case when bp.FINAL_TRIGGER = 'S' or (bp.LOG_STATUS not in ('F') and bp.LOG_STATUS is not null) then 'V' else " +
		" case when (select count(bp1.PROC_ACT_DTE) from BONUS_PROC bp1 where bp1.PROG_TYP in ('AJ') and (bp1.LOG_STATUS in ('F','I','P','T') or bp1.LOG_STATUS is null) and bp.PROC_ACT_DTE > bp1.PROC_ACT_DTE) > 0 then 'V' else " +
		" case when (select count(pf.PROC_ACT_DTE) from FILE_PROC pf where (pf.LOG_STATUS in ('F','I','P','T') or pf.LOG_STATUS is null) and bp.PROC_ACT_DTE >= pf.PROC_ACT_DTE) > 0 then 'V' else " +
		" 'S' " +
		" end end end as FINAL_STATUS, " +
		" case when bp.LST_PROC_DATE != current date then '' else " +
		" case when bp.LOG_STATUS NOT IN ('S','T') then '' else " +
		" case when bp.LOG_STATUS is null then '' else " +
		" case when (select count(pp.PROC_ACT_DTE) from PAYMENT_PROC pp where pp.LOG_STATUS = 'S' and bp.PROC_ACT_DTE = pp.PROC_ACT_DTE) > 0 then '' else " +
		" 'T' " +
		" end end end end as RESET_STATUS, " +
		" case when bp.LOG_STATUS != 'P' then '' else " +
		" case when bp.LOG_STATUS is null then '' else " +
		" case when (select count(pp.PROC_ACT_DTE) from PAYMENT_PROC pp where pp.LOG_STATUS = 'S' and bp.PROC_ACT_DTE = pp.PROC_ACT_DTE) > 0 then '' else " +
		" 'P' " +
		" end end end as REPROC_STATUS " +
		" from BONUS_PROC bp " +
		" left outer join DPB_PROC_STS sts on bp.LOG_STATUS = sts.CDE_DPB_PROC_STS where bp.PROG_TYP in ('AJ') " +
		" union " +
		" select bp.PROC_ID, bp.PROG_NAME, bp.PROG_ID, bp.PROG_TYP, bp.LOG_STATUS, bp.LOG_TXT, sts.DES_DPB_PROC_STS AS DES_DPB_PROC_STS,bp.PROC_ACT_DTE, " +
		" case when bp.FINAL_TRIGGER = 'S' or (bp.LOG_STATUS not in ('F') and bp.LOG_STATUS is not null) then 'V' else " +
		" case when (select count(bp1.PROC_ACT_DTE) from BONUS_PROC bp1 where bp1.PROG_TYP in ('CB','FB','VB') and (bp1.LOG_STATUS in ('F','I','P','T') or bp1.LOG_STATUS is null) and bp.PROC_ACT_DTE > bp1.PROC_ACT_DTE) > 0 then 'V' else " +
		" case when (select count(bp1.PROC_ACT_DTE) from BONUS_PROC bp1 where bp1.PROG_TYP in ('AJ') and (bp1.LOG_STATUS in ('F','I','P','T') or bp1.LOG_STATUS is null) and bp.PROC_ACT_DTE >= bp1.PROC_ACT_DTE) > 0 then 'V' else " +
		" case when (select count(pf.PROC_ACT_DTE) from FILE_PROC pf where (pf.LOG_STATUS in ('F','I','P','T') or pf.LOG_STATUS is null) and bp.PROC_ACT_DTE >= pf.PROC_ACT_DTE) > 0 then 'V' else " +
		" 'S' " +
		" end end end end as FINAL_STATUS, " +
		" case when bp.LST_PROC_DATE != current date then '' else " +
		" case when bp.LOG_STATUS NOT IN ('S','T') then '' else " +
		" case when bp.LOG_STATUS is null then '' else " +
		" case when (select count(pp.PROC_ACT_DTE) from PAYMENT_PROC pp where pp.LOG_STATUS = 'S' and bp.PROC_ACT_DTE = pp.PROC_ACT_DTE) > 0 then '' else " +
		" 'T' " +
		" end end end end as RESET_STATUS, " +
		" case when bp.LOG_STATUS != 'P' then '' else " +
		" case when bp.LOG_STATUS is null then '' else " +
		" case when (select count(pp.PROC_ACT_DTE) from PAYMENT_PROC pp where pp.LOG_STATUS = 'S' and bp.PROC_ACT_DTE = pp.PROC_ACT_DTE) > 0 then '' else " +
		" 'P' " +
		" end end end as REPROC_STATUS " +
		" from BONUS_PROC bp " +
		" left outer join DPB_PROC_STS sts on bp.LOG_STATUS = sts.CDE_DPB_PROC_STS where bp.PROG_TYP in ('CB','FB','VB') ) " +
		" select PROC_ID, PROG_NAME, PROG_ID, PROG_TYP, LOG_STATUS, LOG_TXT, DES_DPB_PROC_STS, PROC_ACT_DTE,FINAL_STATUS,RESET_STATUS,REPROC_STATUS " +
		" from BONUS_FINAL order by PROC_ACT_DTE";

	

public static final String PROCESS_LDR_BONUS_VIEW_START =" select bp.PROC_ID, bp.PROG_NAME, bp.PROG_ID, bp.PROG_TYP, bp.LOG_STATUS, bp.LOG_TXT, sts.DES_DPB_PROC_STS AS DES_DPB_PROC_STS,bp.PROC_ACT_DTE, "+  
														" case when bp.FINAL_TRIGGER = 'S' or (bp.LOG_STATUS not in ('F') and bp.LOG_STATUS is not null) then 'V' else "+  
														" case when (select count(bp1.PROC_ACT_DTE) from BONUS_PROC bp1 where bp1.PROG_TYP in ('LB') and (bp1.LOG_STATUS in ('F','I','P','T') or bp1.LOG_STATUS is null) and bp.PROC_ACT_DTE > bp1.PROC_ACT_DTE) > 0 then 'V' else "+  
														" case when (select count(pf.PROC_ACT_DTE) from FILE_PROC pf where (pf.LOG_STATUS in ('F','I','P','T') or pf.LOG_STATUS is null) and bp.PROC_ACT_DTE >= pf.PROC_ACT_DTE) > 0 then 'V' else "+  
														" 'S' "+  
														" end end end as FINAL_STATUS, "+ 
														" case when bp.LST_PROC_DATE != current date then '' else "+  
														" case when bp.LOG_STATUS NOT IN ('S','T') then '' else "+  
														" case when bp.LOG_STATUS is null then '' else "+  
														" case when (select count(pp.PROC_ACT_DTE) from PAYMENT_PROC pp where pp.LOG_STATUS = 'S' and bp.PROC_ACT_DTE = pp.PROC_ACT_DTE) > 0 then '' else "+ 
														" 'T' "+ 
														" end end end end as RESET_STATUS, "+ 
														" case when bp.LOG_STATUS != 'P' then '' else "+  
														" case when bp.LOG_STATUS is null then '' else "+  
														" case when (select count(pp.PROC_ACT_DTE) from PAYMENT_PROC pp where pp.LOG_STATUS = 'S' and bp.PROC_ACT_DTE = pp.PROC_ACT_DTE) > 0 then '' else "+ 
														" 'P' "+  
														" end end end as REPROC_STATUS "+  
														" from BONUS_PROC bp "+   
														" left outer join DPB_PROC_STS sts on bp.LOG_STATUS = sts.CDE_DPB_PROC_STS  where bp.PROG_TYP in ('LB') "+ 
														" order by bp.PROC_ACT_DTE ";

public static final String GET_DPB_LOG_POPUP_REC =" select ID_DPB_PROC_LOG,TXT_DPB_PROC_MSG,sts.DES_DPB_PROC_STS from DPB_PROC_LOG log,DPB_PROC_STS sts where "+
												  " ID_DPB_PROC=? and sts.CDE_DPB_PROC_STS=log.CDE_DPB_PROC_STS  ORDER BY ID_DPB_PROC_LOG asc ";
}
