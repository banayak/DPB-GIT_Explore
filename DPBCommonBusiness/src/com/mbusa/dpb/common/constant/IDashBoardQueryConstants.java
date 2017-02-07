package com.mbusa.dpb.common.constant;

/**
 * 
 * 
 * @author CB5002578
 *
 */
public interface IDashBoardQueryConstants {

	/*public static final String RETRIVE_DASHBOARD_LIST =" SELECT l.ID_DPB_PROC_LOG, l.TXT_DPB_PROC_MSG, l.CDE_DPB_PROC_STS, " +
			   										   " p.ID_DPB_APP_ENT, p.CDE_DPB_PROC_TYP, VARCHAR_FORMAT(l.DTS_LAST_UPDT, 'YYYY-MM-DD') " +
													   " FROM DPB_PROC_LOG l INNER JOIN DPB_PROC  p ON" +
													   " l.ID_DPB_PROC = p.ID_DPB_PROC AND date(l.DTS_LAST_UPDT) >= (CURRENT DATE - 1 DAYS) order " +
													   " by p.CDE_DPB_PROC_TYP, l.DTS_LAST_UPDT asc";*/

	/*public static final String RETRIVE_DASHBOARD_LIST=  "  with RTL_CAL (DTE_CAL, ID_DAY) as (select DTE_CAL, ID_DAY from DPB_RTL_CAL_DY_DIM "+    
											 			"  where DTE_CAL in(current date , (current date - 1 days))), PROC (PROC_ID, PROG_ID, PROG_TYP, DTE_CAL,PROC_ACT_DTE) as ( "+   
														"  select proc.ID_DPB_PROC, proc.ID_DPB_APP_ENT, proc.CDE_DPB_PROC_TYP, rtl.DTE_CAL,(select DTE_CAL from DPB_RTL_CAL_DY_DIM rtlCal where "+   
													    "  rtlCal.ID_DAY = proc.DTE_DPB_ACTL_PROC) from DPB_PROC proc, RTL_CAL rtl "+   
														"  where proc.ID_DAY in (rtl.ID_DAY)), "+
														"  TASKS (PROC_ID, DTE_CAL, PGM_NAME,PROG_TYP,PROC_ACT_DTE) as (select proc.PROC_ID, "+
														"  proc.DTE_CAL, pgm.NAM_DPB_PGM, proc.PROG_TYP,proc.PROC_ACT_DTE "+  
														"  from PROC proc, DPB_PGM pgm where pgm.ID_DPB_PGM = proc.PROG_ID and proc.PROG_TYP in ('CB','FB','FP','CP','VB','VP') " +
														"  union "+ 
														"  select proc.PROC_ID, proc.DTE_CAL, file.NAM_DPB_FIL,proc.PROG_TYP,proc.PROC_ACT_DTE from PROC proc, DPB_FILE file where file.ID_DPB_FILE in (proc.PROG_ID) "+    
														"  and proc.PROG_TYP in ('PF') "+
														"  union "+ 
														"  select proc.PROC_ID, proc.DTE_CAL, 'ADJUSTMENT PROCESS', proc.PROG_TYP,proc.PROC_ACT_DTE from PROC proc, DPB_FILE file where file.ID_DPB_FILE in (proc.PROG_ID) "+    
														"  and proc.PROG_TYP in ('AJ') "+
														"  union "+ 
														"  select proc.PROC_ID, proc.DTE_CAL, ldr.NAM_LDRSP_BNS,proc.PROG_TYP,proc.PROC_ACT_DTE from PROC proc, LDRSP_BNS_PGM ldr "+   
														"  where ldr.ID_LDRSP_BNS_PGM in (proc.PROG_ID) and proc.PROG_TYP in ('LB','LP') "+
														"  union "+ 
														"  select proc.PROC_ID, proc.DTE_CAL, rpt.NAM_DPB_RPT,proc.PROG_TYP,proc.PROC_ACT_DTE "+    
														"  from PROC proc, DPB_RPT rpt where rpt.ID_DPB_RPT in (proc.PROG_ID) and proc.PROG_TYP in ('RP')" +
														"  )," +
														"  PROC_LOG (ID_PROC_LOG, PROC_ID) as "+    
														"  (select max(log.ID_DPB_PROC_LOG), log.ID_DPB_PROC from DPB_PROC_LOG log, TASKS tasks where log.ID_DPB_PROC in (tasks.PROC_ID) "+   
														"  group by log.ID_DPB_PROC), "+
														"  LOG_FINAL (PROC_ID, PGM_NAME, STATUS, ID_PROC_LOG, DTE_CAL,PROG_TYP,PROC_ACT_DTE) as(select tasks.PROC_ID, tasks.PGM_NAME, "+   
														"  (select sts.DES_DPB_PROC_STS from DPB_PROC_LOG plog,DPB_PROC_STS sts where plog.ID_DPB_PROC_LOG = log.ID_PROC_LOG "+   
														"  and sts.CDE_DPB_PROC_STS = plog.CDE_DPB_PROC_STS) as status, log.ID_PROC_LOG, tasks.DTE_CAL,tasks.PROG_TYP,tasks.PROC_ACT_DTE from TASKS tasks "+   
														"  left outer join PROC_LOG log on tasks.PROC_ID = log.PROC_ID) "+
														"  select final.PROC_ID, final.PGM_NAME, final.STATUS,final.PROG_TYP,final.PROC_ACT_DTE, "+    
														"  case when final.STATUS is null then 'Process yet to start.' else "+   
														"  (select log.TXT_DPB_PROC_MSG from DPB_PROC_LOG log where log.ID_DPB_PROC_LOG = final.ID_PROC_LOG) end as DETAILS, "+
														"  case when  final.DTE_CAL = current date then 'TODAY' else 'YDAY' end as PROC_DAY from LOG_FINAL final "+   
														"  order by PROC_DAY, final.PROC_ID ";*/
	
public static final String RETRIVE_DASHBOARD_LIST = "with PROC (PROC_ID, PROG_ID_RPT, PROG_ID_FILE, PROG_ID_PGM, PROG_ID_LDR_PGM," +
		" PROG_TYP, DTE_CAL, PROC_ACT_DTE) as (" +
		" select proc.ID_DPB_PROC, proc.ID_DPB_RPT, proc.ID_DPB_FILE, proc.ID_DPB_PGM, proc.ID_LDRSP_BNS_PGM, " +
		" proc.CDE_DPB_PROC_TYP, proc.DTE_CAL, proc.DTE_DPB_ACTL_PROC from DPB_PROC proc " +
		" where proc.DTE_CAL in(current date , (current date - 1 days))), " +
		" TASKS (PROC_ID, DTE_CAL, PGM_NAME, PROG_TYP, PROC_ACT_DTE) as (" +
		" select proc.PROC_ID, " +
		" proc.DTE_CAL, pgm.NAM_DPB_PGM, proc.PROG_TYP, proc.PROC_ACT_DTE " +
		" from PROC proc, DPB_PGM pgm where pgm.ID_DPB_PGM = proc.PROG_ID_PGM and proc.PROG_TYP in ('CB','FB','FP','CP','VB','VP')" +
		" union" +
		" select proc.PROC_ID, proc.DTE_CAL, file.NAM_DPB_FIL,proc.PROG_TYP,proc.PROC_ACT_DTE from PROC proc, DPB_FILE file where file.ID_DPB_FILE in (proc.PROG_ID_FILE)" +
		" and proc.PROG_TYP in ('PF')" +
		" union" +
		" select proc.PROC_ID, proc.DTE_CAL, 'ADJUSTMENT PROCESS', proc.PROG_TYP,proc.PROC_ACT_DTE from PROC proc, DPB_FILE file where file.ID_DPB_FILE in (proc.PROG_ID_FILE)" +
		" and proc.PROG_TYP in ('AJ')" +
		" union" +
		" select proc.PROC_ID, proc.DTE_CAL, ldr.NAM_LDRSP_BNS,proc.PROG_TYP,proc.PROC_ACT_DTE from PROC proc, LDRSP_BNS_PGM ldr" +
		" where ldr.ID_LDRSP_BNS_PGM in (proc.PROG_ID_LDR_PGM) and proc.PROG_TYP in ('LB','LP')" +
		" union" +
		" select proc.PROC_ID, proc.DTE_CAL, rpt.NAM_DPB_RPT,proc.PROG_TYP,proc.PROC_ACT_DTE" +
		" from PROC proc, DPB_RPT rpt where rpt.ID_DPB_RPT in (proc.PROG_ID_RPT) and proc.PROG_TYP in ('RP'))," +
		" PROC_LOG (ID_PROC_LOG, PROC_ID) as (" +
		" select max(log.ID_DPB_PROC_LOG), log.ID_DPB_PROC" +
		" from DPB_PROC_LOG log, TASKS tasks" +
		" where log.ID_DPB_PROC in (tasks.PROC_ID)" +
		" group by log.ID_DPB_PROC)," +
		" LOG_FINAL (PROC_ID, PGM_NAME, STATUS, ID_PROC_LOG, DTE_CAL,PROG_TYP,PROC_ACT_DTE) as (" +
		" select tasks.PROC_ID, tasks.PGM_NAME," +
		" (select sts.DES_DPB_PROC_STS from DPB_PROC_LOG plog,DPB_PROC_STS sts where plog.ID_DPB_PROC_LOG = log.ID_PROC_LOG" +
		" and trim(sts.CDE_DPB_PROC_STS) = trim(plog.CDE_DPB_PROC_STS)) as status, log.ID_PROC_LOG, tasks.DTE_CAL,tasks.PROG_TYP,tasks.PROC_ACT_DTE from TASKS tasks" +
		" left outer join PROC_LOG log on tasks.PROC_ID = log.PROC_ID)" +
		" select final.PROC_ID, final.PGM_NAME, final.STATUS,final.PROG_TYP,final.PROC_ACT_DTE," +
		" case when final.STATUS is null then 'Process yet to start.' else" +
		" (select log.TXT_DPB_PROC_MSG from DPB_PROC_LOG log where log.ID_DPB_PROC_LOG = final.ID_PROC_LOG) end as DETAILS," +
		" case when  final.DTE_CAL = current date then 'TODAY' else 'YDAY' end as PROC_DAY from LOG_FINAL final" +
		" order by PROC_DAY, final.PROC_ID";


/**
 * added to view processes datewise - Amit
 */
public static final String RETRIVE_DASHBOARD_LIST_BY_DATE = "with PROC (PROC_ID, PROG_ID_RPT, PROG_ID_FILE, PROG_ID_PGM, PROG_ID_LDR_PGM," +
		" PROG_TYP, DTE_CAL, PROC_ACT_DTE) as (" +
		" select proc.ID_DPB_PROC, proc.ID_DPB_RPT, proc.ID_DPB_FILE, proc.ID_DPB_PGM, proc.ID_LDRSP_BNS_PGM, " +
		" proc.CDE_DPB_PROC_TYP, proc.DTE_CAL, proc.DTE_DPB_ACTL_PROC from DPB_PROC proc " +
		//changed as per comment from Onsite 
//		" where proc.DTE_CAL in(?)), " +
		" where proc.DTE_DPB_ACTL_PROC in(?)), " +
		" TASKS (PROC_ID, DTE_CAL, PGM_NAME, PROG_TYP, PROC_ACT_DTE) as (" +
		" select proc.PROC_ID, " +
		" proc.DTE_CAL, pgm.NAM_DPB_PGM, proc.PROG_TYP, proc.PROC_ACT_DTE " +
		" from PROC proc, DPB_PGM pgm where pgm.ID_DPB_PGM = proc.PROG_ID_PGM and proc.PROG_TYP in ('CB','FB','FP','CP','VB','VP')" +
		" union" +
		" select proc.PROC_ID, proc.DTE_CAL, file.NAM_DPB_FIL,proc.PROG_TYP,proc.PROC_ACT_DTE from PROC proc, DPB_FILE file where file.ID_DPB_FILE in (proc.PROG_ID_FILE)" +
		" and proc.PROG_TYP in ('PF')" +
		" union" +
		" select proc.PROC_ID, proc.DTE_CAL, 'ADJUSTMENT PROCESS', proc.PROG_TYP,proc.PROC_ACT_DTE from PROC proc, DPB_FILE file where file.ID_DPB_FILE in (proc.PROG_ID_FILE)" +
		" and proc.PROG_TYP in ('AJ')" +
		" union" +
		" select proc.PROC_ID, proc.DTE_CAL, ldr.NAM_LDRSP_BNS,proc.PROG_TYP,proc.PROC_ACT_DTE from PROC proc, LDRSP_BNS_PGM ldr" +
		" where ldr.ID_LDRSP_BNS_PGM in (proc.PROG_ID_LDR_PGM) and proc.PROG_TYP in ('LB','LP')" +
		" union" +
		" select proc.PROC_ID, proc.DTE_CAL, rpt.NAM_DPB_RPT,proc.PROG_TYP,proc.PROC_ACT_DTE" +
		" from PROC proc, DPB_RPT rpt where rpt.ID_DPB_RPT in (proc.PROG_ID_RPT) and proc.PROG_TYP in ('RP'))," +
		" PROC_LOG (ID_PROC_LOG, PROC_ID) as (" +
		" select max(log.ID_DPB_PROC_LOG), log.ID_DPB_PROC" +
		" from DPB_PROC_LOG log, TASKS tasks" +
		" where log.ID_DPB_PROC in (tasks.PROC_ID)" +
		" group by log.ID_DPB_PROC)," +
		" LOG_FINAL (PROC_ID, PGM_NAME, STATUS, ID_PROC_LOG, DTE_CAL,PROG_TYP,PROC_ACT_DTE) as (" +
		" select tasks.PROC_ID, tasks.PGM_NAME," +
		" (select sts.DES_DPB_PROC_STS from DPB_PROC_LOG plog,DPB_PROC_STS sts where plog.ID_DPB_PROC_LOG = log.ID_PROC_LOG" +
		" and trim(sts.CDE_DPB_PROC_STS) = trim(plog.CDE_DPB_PROC_STS)) as status, log.ID_PROC_LOG, tasks.DTE_CAL,tasks.PROG_TYP,tasks.PROC_ACT_DTE from TASKS tasks" +
		" left outer join PROC_LOG log on tasks.PROC_ID = log.PROC_ID)" +
		" select final.PROC_ID, final.PGM_NAME, final.STATUS,final.PROG_TYP,final.PROC_ACT_DTE," +
		" case when final.STATUS is null then 'Process yet to start.' else" +
		" (select log.TXT_DPB_PROC_MSG from DPB_PROC_LOG log where log.ID_DPB_PROC_LOG = final.ID_PROC_LOG) end as DETAILS," +
		" case when  final.DTE_CAL = current date then 'TODAY' else 'YDAY' end as PROC_DAY from LOG_FINAL final" +
		" order by PROC_DAY, final.PROC_ID";

	public static final String GET_RETAIL_CAL_FOR_YEAR = "SELECT distinct DTE_RETL_MTH_BEG, DTE_RETL_MTH_END,NAM_RETL_MTH,NUM_RETL_MTH,NUM_RETL_YR " +
		" FROM dpbuser.DPB_DAY where NUM_RETL_YR = ? order by num_retl_mth asc for fetch only with ur";
	
}
