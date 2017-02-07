package com.mbusa.dpb.common.constant;

/**
 * 
 * 
 * @author CB5002578
 *
 */
public interface IFileProcessingQueryConstants {

	public static final String RETREIVE_PROCESS_DEFINITION_DETAIL = " Select ID_DPB_PROC,ID_DAY,ID_DPB_APP_ENT,DTE_DPB_ACTL_PROC,CDE_DPB_PROC_TYP,ID_OVRD_PROC_DTE                                                        " +
																	" TME_OVRD_PROC,CDE_DPB_OVRD_TRGR,TXT_RSN_PROC_UPD,                                                                                                    " +
																	" CDE_COMSN_BNS_SCHD, CDE_COMSN_BNS_TRGR, CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_TRGR, CDE_FXD_BNS_SCHD,CDE_FXD_BNS_TRGR,CDE_FXD_PMT_SCHD,CDE_FXD_PMT_TRGR, " +
																	" CDE_VAR_BNS_SCHD,CDE_VAR_BNS_TRGR,CDE_VAR_PMT_SCHD,CDE_VAR_PMT_TRGR,IND_SPL_DPB_PGM                                                                  " +
																	" from DPB_PROC join DPB_PGM pgm on ID_DPB_PGM = ID_DPB_APP_ENT where ID_DPB_PROC = ?";
	public static final String PROCESS_START_VIEW_QUERY  ="  with PROC (PROC_ID, PROG_ID_RPT, PROG_ID_FILE, PROG_ID_PGM, PROG_ID_LDR_PGM, "+ 
													     " 	PROG_TYP, PROC_DATE, PROC_ACT_DTE, OVER_PROC_DATE, " + 
													     " 	OVER_TME_PROC, OVER_STATUS,  LST_UPD_USR) as ( "+  
													     "  select proc.ID_DPB_PROC, proc.ID_DPB_RPT, proc.ID_DPB_FILE, proc.ID_DPB_PGM, proc.ID_LDRSP_BNS_PGM, "+
														 "  proc.CDE_DPB_PROC_TYP, proc.DTE_CAL, "+  
														 "  proc.DTE_DPB_ACTL_PROC, proc.DTE_PROC_OVRD, proc.TME_PROC_OVRD, "+  
														 "  proc.CDE_DPB_OVRD_PROC_INIT_TYP, proc.ID_LAST_UPDT_USER "+  
														 "  from DPB_PROC proc "+  
														 "  where proc.DTE_CAL <= current date), "+  
														 "  PROC_LOG (ID_PROC_LOG, PROC_ID) as ( "+  
														 "  select max(log.ID_DPB_PROC_LOG), log.ID_DPB_PROC "+  
														 "  from DPB_PROC_LOG log, PROC proc "+  
														 "  where log.ID_DPB_PROC in (proc.PROC_ID) "+  
														 "  group by log.ID_DPB_PROC), "+  
														 "  PROC_LOG_TXT (ID_PROC_LOG, PROC_ID, LOG_STATUS, LOG_TXT, LST_PROC_DATE) as ( "+  
														 "  select log.ID_DPB_PROC_LOG, log.ID_DPB_PROC, trim(log.CDE_DPB_PROC_STS), log.TXT_DPB_PROC_MSG, date(log.DTS_LAST_UPDT) as LST_PROC_DATE "+  
														 "  from DPB_PROC_LOG log, PROC_LOG plog "+  
														 "  where log.ID_DPB_PROC_LOG = plog.ID_PROC_LOG ), "+ 
														 "  PROC_LOG_FINAL (PROC_ID, LOG_STATUS, LOG_TXT, LST_PROC_DATE) as ( "+  
														 "  select proc.PROC_ID, log.LOG_STATUS, log.LOG_TXT, log.LST_PROC_DATE "+  
														 "  from PROC proc "+  
														 "  left outer join PROC_LOG_TXT log on proc.PROC_ID = log.PROC_ID ), "+ 
														 "  FILE_PROC (PROC_ID, PROG_ID, PROG_TYP, PROC_ACT_DTE, "+
														 "  PROG_NAME, TME_FILE_IN, LOG_STATUS, LOG_TXT, FINAL_TRIGGER, LST_PROC_DATE) as ( "+  
														 "  select proc.PROC_ID, proc.PROG_ID_FILE, proc.PROG_TYP, proc.PROC_ACT_DTE, "+ 
														 "  file.NAM_DPB_FIL, file.TME_FILE_IN, log.LOG_STATUS, log.LOG_TXT, "+ 
														 "  CASE WHEN (proc.OVER_STATUS is not null and "+ 
														 "  ((proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) or "+ 
														 "   (proc.LST_UPD_USR = 'SYSTEM'))) THEN proc.OVER_STATUS "+
														 "   ELSE file.CDE_DPB_PROC_INIT_TYP END, log.LST_PROC_DATE "+  
														 "   from PROC proc, DPB_FILE file, PROC_LOG_FINAL log "+  
														 "   where file.ID_DPB_FILE in (proc.PROG_ID_FILE) and proc.PROG_TYP in ('PF') "+  
														 "    and proc.PROC_ID = log.PROC_ID "+  
														 "    and (file.CDE_DPB_STS = 'A' or (file.CDE_DPB_STS = 'I' and file.DTE_INACT > current date))), "+  
														 "  BONUS_PROC (PROC_ID, PROG_ID, PROG_TYP, PROC_ACT_DTE, "+  
														 "    PROG_NAME, LOG_STATUS, LOG_TXT, FINAL_TRIGGER, LST_PROC_DATE) as ( "+ 
														 "    select proc.PROC_ID, proc.PROG_ID_FILE, proc.PROG_TYP, proc.PROC_ACT_DTE, "+ 
														 "    'ADJUSTMENT PROCESS', log.LOG_STATUS, log.LOG_TXT, "+  
														 "   CASE WHEN (proc.OVER_STATUS is not null and "+ 
														 "   ((proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) or "+ 
														 "    (proc.LST_UPD_USR = 'SYSTEM'))) THEN proc.OVER_STATUS "+
														 "    ELSE file.CDE_DPB_PROC_INIT_TYP END, log.LST_PROC_DATE "+  
														 "    from PROC proc, DPB_FILE file, PROC_LOG_FINAL log "+  
														 "    where file.ID_DPB_FILE in (proc.PROG_ID_FILE) and proc.PROG_TYP in ('AJ') "+  
														 "    and proc.PROC_ID = log.PROC_ID "+  
														 "    and (file.CDE_DPB_STS = 'A' or (file.CDE_DPB_STS = 'I' and file.DTE_INACT > current date)) "+ 
														 "    union "+   
														 "    select proc.PROC_ID, proc.PROG_ID_PGM, proc.PROG_TYP, proc.PROC_ACT_DTE, "+  
														 "    pgm.NAM_DPB_PGM, log.LOG_STATUS, log.LOG_TXT, "+  
														 "    CASE WHEN (proc.OVER_STATUS is not null and  "+
														 "    ((proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) or "+ 
														 "    (proc.LST_UPD_USR = 'SYSTEM'))) THEN proc.OVER_STATUS "+
														 "    ELSE pgm.CDE_COMSN_PROC_INIT_TYP END, log.LST_PROC_DATE "+  
														 "    from PROC proc, DPB_PGM pgm, PROC_LOG_FINAL log "+  
														 "    where pgm.ID_DPB_PGM in (proc.PROG_ID_PGM) and proc.PROG_TYP in ('CB') "+  
														 "    and proc.PROC_ID = log.PROC_ID "+  
														 "    and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > current date)) "+  
														 "     union "+  
														 "    select proc.PROC_ID, proc.PROG_ID_PGM, proc.PROG_TYP, proc.PROC_ACT_DTE, "+ 
														 "    pgm.NAM_DPB_PGM, log.LOG_STATUS, log.LOG_TXT, "+  
														 "    CASE WHEN (proc.OVER_STATUS is not null and "+ 
														 "    ((proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) or "+ 
														 "    (proc.LST_UPD_USR = 'SYSTEM'))) THEN proc.OVER_STATUS "+
														 "    ELSE pgm.CDE_FXD_BNS_PROC_INIT_TYP END, log.LST_PROC_DATE "+ 
														 "    from PROC proc, DPB_PGM pgm, PROC_LOG_FINAL log "+  
														 "     where pgm.ID_DPB_PGM in (proc.PROG_ID_PGM) and proc.PROG_TYP in ('FB') "+  
														 "    and proc.PROC_ID = log.PROC_ID "+  
														 "    and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > current date)) "+  
														 "    union "+   
														 "    select proc.PROC_ID, proc.PROG_ID_PGM, proc.PROG_TYP, proc.PROC_ACT_DTE, "+  
														 "    pgm.NAM_DPB_PGM, log.LOG_STATUS, log.LOG_TXT,   "+
														 "    CASE WHEN (proc.OVER_STATUS is not null and "+ 
														 "    ((proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) or "+ 
														 "    (proc.LST_UPD_USR = 'SYSTEM'))) THEN proc.OVER_STATUS "+
														 "    ELSE pgm.CDE_VAR_BNS_PROC_INIT_TYP END, log.LST_PROC_DATE "+  
														 "    from PROC proc, DPB_PGM pgm, PROC_LOG_FINAL log "+   
														 "    where pgm.ID_DPB_PGM in (proc.PROG_ID_PGM) and proc.PROG_TYP in ('VB') "+  
														 "    and proc.PROC_ID = log.PROC_ID "+  
														 "    and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > current date)) "+  
														 "    union "+   
														 "    select proc.PROC_ID, proc.PROG_ID_LDR_PGM, proc.PROG_TYP, proc.PROC_ACT_DTE, "+  
														 "    ldr.NAM_LDRSP_BNS, log.LOG_STATUS, log.LOG_TXT, "+  
														 "    CASE WHEN (proc.OVER_STATUS is not null and "+ 
														 "    ((proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) or "+ 
														 "    (proc.LST_UPD_USR = 'SYSTEM'))) THEN proc.OVER_STATUS "+
														 "    ELSE 'S' END, log.LST_PROC_DATE  "+  
														 "    from PROC proc, LDRSP_BNS_PGM ldr, PROC_LOG_FINAL log "+  
														 "    where ldr.ID_LDRSP_BNS_PGM = proc.PROG_ID_LDR_PGM and proc.PROG_TYP = 'LB' "+  
														 "    and proc.PROC_ID = log.PROC_ID "+  
														 "    and (ldr.CDE_DPB_STS = 'A' or (ldr.CDE_DPB_STS = 'I' and ldr.DTE_INACT > current date))), "+  
														 "	 PAYMENT_PROC (PROC_ID, PROG_ID, PROG_TYP, PROC_ACT_DTE, "+  
														 "    PROG_NAME, LOG_STATUS, LOG_TXT, FINAL_TRIGGER) as ( "+   
														 "    select proc.PROC_ID, proc.PROG_ID_PGM, proc.PROG_TYP, proc.PROC_ACT_DTE, "+  
														 "    pgm.NAM_DPB_PGM, log.LOG_STATUS, log.LOG_TXT, "+  
														 "    CASE WHEN (proc.OVER_STATUS is not null and "+ 
														 "     ((proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) or "+ 
														 "    (proc.LST_UPD_USR = 'SYSTEM'))) THEN proc.OVER_STATUS "+
														 "    ELSE pgm.CDE_COMSN_PMT_PROC_INIT_TYP END "+
														 "    from PROC proc, DPB_PGM pgm, PROC_LOG_FINAL log "+    
														 "    where pgm.ID_DPB_PGM in (proc.PROG_ID_PGM) and proc.PROG_TYP in ('CP') "+   
														 "    and proc.PROC_ID = log.PROC_ID "+   
														 "    and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > current date)) "+  
														 "    union "+   
														 "    select proc.PROC_ID, proc.PROG_ID_PGM, proc.PROG_TYP, proc.PROC_ACT_DTE, "+  
														 "    pgm.NAM_DPB_PGM, log.LOG_STATUS, log.LOG_TXT, "+   
														 "    CASE WHEN (proc.OVER_STATUS is not null and "+ 
														 "    ((proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) or "+ 
														 "    (proc.LST_UPD_USR = 'SYSTEM'))) THEN proc.OVER_STATUS "+
														 "     ELSE pgm.CDE_FXD_PMT_PROC_INIT_TYP END "+  
														 "    from PROC proc, DPB_PGM pgm, PROC_LOG_FINAL log "+   
														 "    where pgm.ID_DPB_PGM in (proc.PROG_ID_PGM) and proc.PROG_TYP in ('FP')  "+
														 "    and proc.PROC_ID = log.PROC_ID "+  
														 "   and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > current date)) "+  
														 "    union "+  
														 "    select proc.PROC_ID, proc.PROG_ID_PGM, proc.PROG_TYP, proc.PROC_ACT_DTE, "+  
														 "    pgm.NAM_DPB_PGM, log.LOG_STATUS, log.LOG_TXT, "+  
														 "    CASE WHEN (proc.OVER_STATUS is not null and "+ 
														 "    ((proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) or "+ 
														 "    (proc.LST_UPD_USR = 'SYSTEM'))) THEN proc.OVER_STATUS "+
														 "    ELSE pgm.CDE_VAR_PMT_PROC_INIT_TYP END "+  
														 "   from PROC proc, DPB_PGM pgm, PROC_LOG_FINAL log "+   
														 "    where pgm.ID_DPB_PGM in (proc.PROG_ID_PGM) and proc.PROG_TYP in ('VP') "+   
														 "    and proc.PROC_ID = log.PROC_ID "+  
														 "    and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > current date)) "+  
														 "    union "+  
														 "    select proc.PROC_ID, proc.PROG_ID_LDR_PGM, proc.PROG_TYP, proc.PROC_ACT_DTE, "+  
														 "    ldr.NAM_LDRSP_BNS, log.LOG_STATUS, log.LOG_TXT, "+  
														 "    CASE WHEN (proc.OVER_STATUS is not null and "+ 
														 "    ((proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) or "+ 
														 "    (proc.LST_UPD_USR = 'SYSTEM'))) THEN proc.OVER_STATUS "+
														 "    ELSE 'S' END "+  
														 "    from PROC proc, LDRSP_BNS_PGM ldr, PROC_LOG_FINAL log "+  
														 "    where ldr.ID_LDRSP_BNS_PGM = proc.PROG_ID_LDR_PGM and proc.PROG_TYP = 'LP' "+  
														 "    and proc.PROC_ID = log.PROC_ID "+  
														 "    and (ldr.CDE_DPB_STS = 'A' or (ldr.CDE_DPB_STS = 'I' and ldr.DTE_INACT > current date))), "+  
														 "  REPORT_PROC (PROC_ID, PROG_ID, PROG_TYP, PROC_ACT_DTE, "+  
														 "    PROG_NAME, LOG_STATUS, LOG_TXT, FINAL_TRIGGER) as ( "+   
														 "    select proc.PROC_ID, proc.PROG_ID_RPT, proc.PROG_TYP, proc.PROC_ACT_DTE, "+  
														 "    rpt.NAM_DPB_RPT, log.LOG_STATUS, log.LOG_TXT, "+  
														 "    CASE WHEN (proc.OVER_STATUS is not null and "+ 
														 "    ((proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DATE <= current date) or "+ 
														 "    (proc.LST_UPD_USR = 'SYSTEM'))) THEN proc.OVER_STATUS "+
														 "    ELSE rpt.CDE_DPB_PROC_INIT_TYP END "+
														 "    from PROC proc, DPB_RPT rpt, PROC_LOG_FINAL log "+  
														 "    where rpt.ID_DPB_RPT = proc.PROG_ID_RPT and proc.PROG_TYP = 'RP' "+  
														 "    and proc.PROC_ID = log.PROC_ID "+  
														 "    and (rpt.CDE_DPB_STS = 'A' or (rpt.CDE_DPB_STS = 'I' and rpt.DTE_INACT > current date))) ";


	public static final String FILE_PROC_START_VIEW   ="  select pf.PROC_ID, pf.PROG_NAME, pf.PROG_ID, pf.PROG_TYP, pf.LOG_STATUS, pf.LOG_TXT, sts.DES_DPB_PROC_STS AS DES_DPB_PROC_STS,pf.PROC_ACT_DTE, "+  
														" case when pf.FINAL_TRIGGER = 'S' or (pf.LOG_STATUS not in ('F') and pf.LOG_STATUS is not null) then 'V' else "+  
														" case when (pf.PROC_ACT_DTE = current date and pf.TME_FILE_IN > (current time + 10 minute)) then 'V' else "+  
														" case when (select count(pf1.PROC_ACT_DTE) from FILE_PROC pf1 where (pf1.LOG_STATUS in ('F','I','P','T') or pf1.LOG_STATUS is null) and pf.PROC_ACT_DTE > pf1.PROC_ACT_DTE) > 0 then 'V' else "+  
														" case when (select count(bp.PROC_ACT_DTE) from BONUS_PROC bp where (bp.LOG_STATUS in ('F','I','P','T') or bp.LOG_STATUS is null) and pf.PROC_ACT_DTE > bp.PROC_ACT_DTE) > 0 then 'V' else "+   
														" 'S' "+  
														" end end end end as FINAL_STATUS, "+ 
														" case when pf.LST_PROC_DATE != current date then '' else "+  
														" case when pf.LOG_STATUS NOT IN ('S','T') then '' else "+  
														" case when pf.LOG_STATUS is null then '' else "+  
														" case when (select count(pp.PROC_ACT_DTE) from PAYMENT_PROC pp where pp.LOG_STATUS = 'S' and pf.PROC_ACT_DTE = pp.PROC_ACT_DTE) > 0 then '' else "+ 
														" 'T' "+ 
														" end end end end as RESET_STATUS, "+ 
														" case when pf.LOG_STATUS != 'P' then '' else "+  
														" case when pf.LOG_STATUS is null then '' else "+  
														" case when (select count(pp.PROC_ACT_DTE) from PAYMENT_PROC pp where pp.LOG_STATUS = 'S' and pf.PROC_ACT_DTE = pp.PROC_ACT_DTE) > 0 then '' else "+ 
														" 'P' "+  
														" end end end as REPROC_STATUS "+ 
														" from FILE_PROC pf "+    
														" left outer join DPB_PROC_STS sts on pf.LOG_STATUS = sts.CDE_DPB_PROC_STS "+  
														" order by pf.PROC_ACT_DTE ";

           									
public static final String PAYMENT_PROC_START_VIEW =" select pp.PROC_ID, pp.PROG_NAME, pp.PROG_ID, pp.PROG_TYP, pp.LOG_STATUS, pp.LOG_TXT,sts.DES_DPB_PROC_STS, pp.PROC_ACT_DTE, "+
													" case when pp.FINAL_TRIGGER = 'S' or (pp.LOG_STATUS != 'F' and pp.LOG_STATUS is not null) then 'V' else "+ 
													" case when (select count(pp1.PROC_ACT_DTE) from PAYMENT_PROC pp1 where (pp1.LOG_STATUS in ('F','I') or pp1.LOG_STATUS is null) and pp.PROC_ACT_DTE > pp1.PROC_ACT_DTE) > 0 then 'V' else "+ 
													" case when (select count(pf.PROC_ACT_DTE) from FILE_PROC pf where (pf.LOG_STATUS in ('F','I') or pf.LOG_STATUS is null) and pp.PROC_ACT_DTE >= pf.PROC_ACT_DTE) > 0 then 'V' else "+ 
													" case when (select count(bp.PROC_ACT_DTE) from BONUS_PROC bp where (bp.LOG_STATUS in ('F','I') or bp.LOG_STATUS is null) and pp.PROC_ACT_DTE >= bp.PROC_ACT_DTE) > 0 then 'V' else "+ 
													" 'S' "+ 
													" end end end end as FINAL_STATUS "+ 
													" from PAYMENT_PROC pp "+ 
													" left outer join DPB_PROC_STS sts on pp.LOG_STATUS = sts.CDE_DPB_PROC_STS "+ 
													" order by pp.PROC_ACT_DTE ";


public static final String REPORT_PROC_START_VIEW = "  select rp.PROC_ID, rp.PROG_NAME, rp.PROG_ID, rp.PROG_TYP, rp.LOG_STATUS, rp.LOG_TXT,sts.DES_DPB_PROC_STS,rp.PROC_ACT_DTE, "+   
													" case when rp.FINAL_TRIGGER = 'S' or (rp.LOG_STATUS != 'F' and rp.LOG_STATUS is not null) then 'V' else "+   
													" case when (select count(rp1.PROC_ACT_DTE) from REPORT_PROC rp1 where (rp1.LOG_STATUS in ('F','I') or rp1.LOG_STATUS is null) and rp.PROC_ACT_DTE > rp1.PROC_ACT_DTE) > 0 then 'V' else "+   
													" case when (select count(pf.PROC_ACT_DTE) from FILE_PROC pf where (pf.LOG_STATUS in ('F','I','P','T') or pf.LOG_STATUS is null) and rp.PROC_ACT_DTE >= pf.PROC_ACT_DTE) > 0 then 'V' else "+   
													" case when (select count(bp.PROC_ACT_DTE) from BONUS_PROC bp where (bp.LOG_STATUS in ('F','I','P','T') or bp.LOG_STATUS is null) and rp.PROC_ACT_DTE >= bp.PROC_ACT_DTE) > 0 then 'V' else "+   
													" 'S' "+   
													" end end end end as FINAL_STATUS "+   
													" from REPORT_PROC rp "+  
													" left outer join DPB_PROC_STS sts on rp.LOG_STATUS = sts.CDE_DPB_PROC_STS "+ 
													" order by rp.PROC_ACT_DTE ";


public static final String GET_DPB_LOG_POPUP_REC =" select ID_DPB_PROC_LOG,TXT_DPB_PROC_MSG,sts.DES_DPB_PROC_STS from DPB_PROC_LOG log,DPB_PROC_STS sts where "+
												   " ID_DPB_PROC=? and sts.CDE_DPB_PROC_STS=log.CDE_DPB_PROC_STS ORDER BY ID_DPB_PROC_LOG asc ";

public static final String VISTA_INSERT = "MERGE INTO DPB_UNBLK_VEH AS RTL USING (VALUES( " +
		"CAST(? AS CHAR(10)),CAST(? AS CHAR(17)),CAST(? AS DATE),CAST(? AS TIME),CAST(? AS CHAR(2)), " +
		"CAST(? AS CHAR(1)),CAST(? AS CHAR(5)),CAST(? AS CHAR(1)),CAST(? AS CHAR(6)),CAST(? AS CHAR(4)), " +
		"CAST(? AS CHAR(8)),CAST(? AS CHAR(2)),CAST(? AS CHAR(35)),CAST(? AS CHAR(25)),CAST(? AS CHAR(1)), " +
		"CAST(? AS DATE),CAST(? AS TIME),CAST(? AS DOUBLE),CAST(? AS DOUBLE),CAST(? AS DOUBLE),CAST(? AS CHAR(1)), " +
		"CAST(? AS CHAR(3)),CAST(? AS CHAR(3)),CAST(? AS CHAR(2)),CAST(? AS CHAR(8)),CAST(? AS CHAR(8)), " +
		"CAST(CURRENT TIMESTAMP AS TIMESTAMP),CAST(CURRENT TIMESTAMP AS TIMESTAMP),CAST(? AS INTEGER),CAST(? AS CHAR(2)), " +
		"CAST(? AS DATE),CAST(? AS CHAR(2)),CAST(? AS CHAR(1)),CAST(? AS DOUBLE),CAST(? AS INTEGER),CAST(? AS CHAR(1)))) AS RTL_NEW ( " +
		"NUM_PO,NUM_VIN,DTE_RTL,TME_RTL,CDE_VEH_STS,CDE_VEH_TYP,ID_DLR,IND_USED_VEH,ID_EMP_PUR_CTRL, " +
		"DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,DTE_TRANS,TME_TRANS, " +
		"AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,CDE_USE_VEH, " +
		"ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA,ID_DPB_PROC,CDE_VEH_DDR_USE,DTE_VEH_DEMO_SRV, " +
		"CDE_VEH_DDR_STS,IND_USED_VEH_DDRS,AMT_MSRP,ID_DPB_PGM,IND_AMG " +
		") ON RTL.NUM_VIN = RTL_NEW.NUM_VIN AND RTL.TME_RTL = RTL_NEW.TME_RTL AND RTL.DTE_RTL = RTL_NEW.DTE_RTL " +
		"WHEN NOT MATCHED THEN INSERT( " +
		"NUM_PO,NUM_VIN,DTE_RTL,TME_RTL,CDE_VEH_STS,CDE_VEH_TYP,ID_DLR,IND_USED_VEH,ID_EMP_PUR_CTRL, " +
		"DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,DTE_TRANS,TME_TRANS, " +
		"AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,CDE_USE_VEH, " +
		"ID_CREA_USER,ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA,ID_DPB_PROC,CDE_VEH_DDR_USE,DTE_VEH_DEMO_SRV, " +
		"CDE_VEH_DDR_STS,IND_USED_VEH_DDRS,AMT_MSRP,ID_DPB_PGM,IND_AMG " +
		") VALUES ( " +
		"RTL_NEW.NUM_PO,RTL_NEW.NUM_VIN,RTL_NEW.DTE_RTL,RTL_NEW.TME_RTL,RTL_NEW.CDE_VEH_STS,RTL_NEW.CDE_VEH_TYP,RTL_NEW.ID_DLR,RTL_NEW.IND_USED_VEH,RTL_NEW.ID_EMP_PUR_CTRL, " +
		"RTL_NEW.DTE_MODL_YR,RTL_NEW.DES_MODL,RTL_NEW.CDE_RGN,RTL_NEW.NAM_RTL_CUS_LST,RTL_NEW.NAM_RTL_CUS_FIR,RTL_NEW.NAM_RTL_CUS_MID,RTL_NEW.DTE_TRANS,RTL_NEW.TME_TRANS, " +
		"RTL_NEW.AMT_MSRP_BASE,RTL_NEW.AMT_MSRP_TOT_ACSRY,RTL_NEW.AMT_DLR_RBT,RTL_NEW.IND_FLT,RTL_NEW.CDE_WHSLE_INIT_TYP,RTL_NEW.CDE_NATL_TYPE,RTL_NEW.CDE_USE_VEH, " +
		"RTL_NEW.ID_CREA_USER,RTL_NEW.ID_LAST_UPDT_USER,RTL_NEW.DTS_LAST_UPDT,RTL_NEW.DTS_CREA,RTL_NEW.ID_DPB_PROC,RTL_NEW.CDE_VEH_DDR_USE,RTL_NEW.DTE_VEH_DEMO_SRV, " +
		"RTL_NEW.CDE_VEH_DDR_STS,RTL_NEW.IND_USED_VEH_DDRS,RTL_NEW.AMT_MSRP,RTL_NEW.ID_DPB_PGM,RTL_NEW.IND_AMG " +
		") ELSE IGNORE";


public static final String VISTA_BLOCKED_VEH_INSERT = " MERGE INTO DPB_BLK_VEH AS BLK USING ( VALUES( " + 
		" CAST (? AS CHAR(10)),CAST (? AS CHAR(5))," +
		" CAST(? AS CHAR(17)),CAST(? AS INTEGER),CAST(? AS DATE),CAST(? AS TIME),CAST(? AS INTEGER)," +
		" CAST(? AS CHAR(8)),CAST(? AS CHAR(8)),CAST(CURRENT TIMESTAMP AS TIMESTAMP)," +
		" CAST(CURRENT TIMESTAMP AS TIMESTAMP),CAST(? AS CHAR(1)), CAST(? AS CHAR(1)), " +
		" CAST(? AS CHAR(2)),CAST(? AS  CHAR(2)), CAST(? AS  CHAR(1)), CAST(? AS CHAR(2)), CAST(? AS CHAR(2))," +
		" CAST(? AS CHAR(1)), CAST(? AS CHAR(6)), CAST(? AS CHAR(4)), CAST(? AS CHAR(8)), CAST(? AS CHAR(2))," + 
		" CAST(? AS CHAR(35)), CAST(? AS CHAR(25)), CAST(? AS CHAR(1)), CAST(? AS DATE), CAST(? AS TIME)," + 
		" CAST(? AS DOUBLE), CAST(? AS DOUBLE), CAST(? AS DOUBLE), CAST(? AS DOUBLE), " + 
		" CAST(? AS CHAR(1)), CAST(? AS CHAR(3)), CAST(? AS CHAR(3)), CAST(? AS DATE), CAST(? AS INTEGER) " +			
		" ) ) AS BLK_NEW ( NUM_PO,ID_DLR,NUM_VIN,ID_DPB_CND," +
		" DTE_RTL,TME_RTL,ID_DPB_PROC,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_CREA,DTS_LAST_UPDT,CDE_VEH_TYP, IND_AMG, " +
		" CDE_VEH_STS, CDE_USE_VEH, IND_USED_VEH, CDE_VEH_DDR_STS, CDE_VEH_DDR_USE, IND_USED_VEH_DDRS, ID_EMP_PUR_CTRL," +
		" DTE_MODL_YR, DES_MODL, CDE_RGN, NAM_RTL_CUS_LST, NAM_RTL_CUS_FIR, NAM_RTL_CUS_MID, DTE_TRANS, TME_TRANS," +
		" AMT_MSRP_BASE, AMT_MSRP_TOT_ACSRY, AMT_DLR_RBT, AMT_MSRP, IND_FLT, CDE_WHSLE_INIT_TYP, CDE_NATL_TYPE," +
		" DTE_VEH_DEMO_SRV, ID_DPB_PGM" +
		" ) ON BLK.NUM_VIN = BLK_NEW.NUM_VIN AND BLK.ID_DPB_CND = BLK_NEW.ID_DPB_CND" +
		" WHEN NOT MATCHED THEN INSERT ( NUM_PO,ID_DLR,NUM_VIN,ID_DPB_CND," +
		" DTE_RTL,TME_RTL,ID_DPB_PROC,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_CREA,DTS_LAST_UPDT,CDE_VEH_TYP, IND_AMG, " +
		" CDE_VEH_STS, CDE_USE_VEH, IND_USED_VEH, CDE_VEH_DDR_STS, CDE_VEH_DDR_USE, IND_USED_VEH_DDRS, ID_EMP_PUR_CTRL," +
		" DTE_MODL_YR, DES_MODL, CDE_RGN, NAM_RTL_CUS_LST, NAM_RTL_CUS_FIR, NAM_RTL_CUS_MID, DTE_TRANS, TME_TRANS," +
		" AMT_MSRP_BASE, AMT_MSRP_TOT_ACSRY, AMT_DLR_RBT, AMT_MSRP, IND_FLT, CDE_WHSLE_INIT_TYP, CDE_NATL_TYPE," +
		" DTE_VEH_DEMO_SRV, ID_DPB_PGM" +
		" ) VALUES ( BLK_NEW.NUM_PO,BLK_NEW.ID_DLR,BLK_NEW.NUM_VIN,BLK_NEW.ID_DPB_CND," +
		" BLK_NEW.DTE_RTL,BLK_NEW.TME_RTL,BLK_NEW.ID_DPB_PROC,BLK_NEW.ID_CREA_USER,BLK_NEW.ID_LAST_UPDT_USER," +
		" BLK_NEW.DTS_CREA,BLK_NEW.DTS_LAST_UPDT,BLK_NEW.CDE_VEH_TYP, BLK_NEW.IND_AMG , BLK_NEW.CDE_VEH_STS," +
		" BLK_NEW.CDE_USE_VEH, BLK_NEW.IND_USED_VEH, BLK_NEW.CDE_VEH_DDR_STS, BLK_NEW.CDE_VEH_DDR_USE," +
		" BLK_NEW.IND_USED_VEH_DDRS, BLK_NEW.ID_EMP_PUR_CTRL, BLK_NEW.DTE_MODL_YR, BLK_NEW.DES_MODL, BLK_NEW.CDE_RGN," +
		" BLK_NEW.NAM_RTL_CUS_LST, BLK_NEW.NAM_RTL_CUS_FIR, BLK_NEW.NAM_RTL_CUS_MID, BLK_NEW.DTE_TRANS, BLK_NEW.TME_TRANS," +
		" BLK_NEW.AMT_MSRP_BASE, BLK_NEW.AMT_MSRP_TOT_ACSRY, BLK_NEW.AMT_DLR_RBT, BLK_NEW.AMT_MSRP, BLK_NEW.IND_FLT," +
		" BLK_NEW.CDE_WHSLE_INIT_TYP, BLK_NEW.CDE_NATL_TYPE, BLK_NEW.DTE_VEH_DEMO_SRV, BLK_NEW.ID_DPB_PGM" +
		" ) ELSE IGNORE";

}
