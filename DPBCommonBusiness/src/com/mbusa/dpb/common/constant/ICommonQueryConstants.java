/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ICommonQueryConstants.java
 * Program Version			: 1.0
 * Program Description		: This class is used common queries used in common dao class.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Sep 03, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.constant;

public interface ICommonQueryConstants {

	String VEHICLE_COND_SELECT_QUERY = "select cnd_rel.CDE_VEH_TYP, cnd_rel.ID_DPB_CND, cond.NAM_DPB_CND, cond.CDE_DPB_CND, " +
				"cond.NAM_DPB_VAR, cond.TXT_DPB_CHK_VAL, cond.TXT_DPB_REG_EX " +
				"from VEH_TYP_CND_REL cnd_rel, DPB_CND cond " +
				"where cond.ID_DPB_CND in (cnd_rel.ID_DPB_CND) " +
				"and cond.CDE_DPB_CND_TYP = 'V' and cond.CDE_DPB_STS = 'A' " +
				"group by cnd_rel.CDE_VEH_TYP, cnd_rel.ID_DPB_CND, cond.NAM_DPB_CND, cond.CDE_DPB_CND, " +
				"cond.NAM_DPB_VAR, cond.TXT_DPB_CHK_VAL, cond.TXT_DPB_REG_EX  with ur";
	
	String BLOCKED_COND_SELECT_QUERY = "select cnd_rel.CDE_VEH_TYP, cnd_rel.ID_DPB_CND, cond.NAM_DPB_CND, cond.CDE_DPB_CND, " +
			"cond.NAM_DPB_VAR, cond.TXT_DPB_CHK_VAL, cond.TXT_DPB_REG_EX " +
			"from VEH_TYP_CND_REL cnd_rel, DPB_CND cond " +
			"where cond.ID_DPB_CND in (cnd_rel.ID_DPB_CND) " +
			"and cond.CDE_DPB_CND_TYP = 'B' and cond.CDE_DPB_STS = 'A' " +
			"group by cnd_rel.CDE_VEH_TYP, cnd_rel.ID_DPB_CND, cond.NAM_DPB_CND, cond.CDE_DPB_CND, " +
			"cond.NAM_DPB_VAR, cond.TXT_DPB_CHK_VAL, cond.TXT_DPB_REG_EX  with ur";
	
	String INSERT_PROC_LOG = "INSERT INTO DPB_PROC_LOG(CDE_DPB_PROC_STS,ID_DPB_PROC,TXT_DPB_PROC_MSG,ID_CREA_USER," +
			"ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA) VALUES(?,?,?,?,?,current timestamp,current timestamp)";
	String INSERT_PROC_TERMINATION_CANCELATION_LOG = "INSERT INTO DPB_PROC_LOG(CDE_DPB_PROC_STS,ID_DPB_PROC,TXT_DPB_PROC_MSG,ID_CREA_USER," +
			"ID_LAST_UPDT_USER,DTS_LAST_UPDT,DTS_CREA)VALUES(?,?,?,?,?,?,?)";
	
	String CHECK_MANUAL_TASKS = "with S_RTL_CAL (PROC_DATE) as ( " +
			"select DTE_CAL from DPB_DAY where DTE_CAL < current date ), " +
			"S_PROC (PROC_ID, PROG_ID, PROG_TYP, PROC_DATE, PROC_ACT_DTE_ID, PROC_ACT_DTE, OVER_RTL_ID, " +
			"OVER_STATUS, OVER_PROC_DATE, OVER_PROC_UPD_USR) as ( " +
			"select proc.ID_DPB_PROC, proc.ID_DPB_APP_ENT, proc.CDE_DPB_PROC_TYP, rtl.PROC_DATE, " +
			"proc.DTE_DPB_ACTL_PROC, (select DTE_CAL from DPB_RTL_CAL_DY_DIM rtlCal where " +
			"rtlCal.ID_DAY = proc.DTE_DPB_ACTL_PROC), proc.ID_OVRD_PROC_DTE, " +
			"proc.CDE_DPB_OVRD_TRGR, (select DTE_CAL from DPB_RTL_CAL_DY_DIM rtlCal " +
			"where rtlCal.ID_DAY = proc.ID_OVRD_PROC_DTE), proc.ID_LAST_UPDT_USER " +
			"from DPB_PROC proc, S_RTL_CAL rtl " +
			"where proc.ID_DAY in (rtl.RTL_ID)), " +
			"S_MANUAL_TASKS (PROC_ID, PROG_ID, PROG_TYP, PROC_DATE, PROC_ACT_DTE_ID, PROC_ACT_DTE, OVER_RTL_ID, " +
			"OVER_STATUS, OVER_PROC_DATE, OVER_PROC_UPD_USR, PGM_TRGR) as ( " +
			"select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_DATE, proc.PROC_ACT_DTE_ID, " +
			"proc.PROC_ACT_DTE, proc.OVER_RTL_ID, proc.OVER_STATUS, proc.OVER_PROC_DATE, " +
			"proc.OVER_PROC_UPD_USR, pgm.CDE_FXD_BNS_TRGR " +
			"from S_PROC proc, DPB_PGM pgm " +
			"where pgm.ID_DPB_PGM in (proc.PROG_ID) and proc.PROG_TYP in ('FB') " +
			"and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > proc.PROC_DATE)) " +
			"and (pgm.CDE_FXD_BNS_TRGR = 'M' or (proc.OVER_STATUS = 'M' and proc.OVER_PROC_UPD_USR != 'SYSTEM' " +
			"and proc.OVER_PROC_DATE < current date)) " +
			"union " +
			"select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_DATE, proc.PROC_ACT_DTE_ID, " +
			"proc.PROC_ACT_DTE, proc.OVER_RTL_ID, proc.OVER_STATUS, proc.OVER_PROC_DATE, " +
			"proc.OVER_PROC_UPD_USR, pgm.CDE_FXD_PMT_TRGR " +
			"from S_PROC proc, DPB_PGM pgm " +
			"where pgm.ID_DPB_PGM in (proc.PROG_ID) and proc.PROG_TYP in ('FP') " +
			"and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > proc.PROC_DATE)) " +
			"and (pgm.CDE_FXD_PMT_TRGR = 'M' or (proc.OVER_STATUS = 'M' and proc.OVER_PROC_UPD_USR != 'SYSTEM' " +
			"and proc.OVER_PROC_DATE < current date)) " +
			"union " +
			"select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_DATE, proc.PROC_ACT_DTE_ID, " +
			"proc.PROC_ACT_DTE, proc.OVER_RTL_ID, proc.OVER_STATUS, proc.OVER_PROC_DATE, " +
			"proc.OVER_PROC_UPD_USR, pgm.CDE_VAR_BNS_TRGR " +
			"from S_PROC proc, DPB_PGM pgm " +
			"where pgm.ID_DPB_PGM in (proc.PROG_ID) and proc.PROG_TYP in ('VB') " +
			"and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > proc.PROC_DATE)) " +
			"and (pgm.CDE_VAR_BNS_TRGR = 'M' or (proc.OVER_STATUS = 'M' and proc.OVER_PROC_UPD_USR != 'SYSTEM' " +
			"and proc.OVER_PROC_DATE < current date)) " +
			"union " +
			"select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_DATE, proc.PROC_ACT_DTE_ID, " +
			"proc.PROC_ACT_DTE, proc.OVER_RTL_ID, proc.OVER_STATUS, proc.OVER_PROC_DATE, " +
			"proc.OVER_PROC_UPD_USR, pgm.CDE_VAR_PMT_TRGR " +
			"from S_PROC proc, DPB_PGM pgm " +
			"where pgm.ID_DPB_PGM in (proc.PROG_ID) and proc.PROG_TYP in ('VP') " +
			"and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I' and pgm.DTE_INACT > proc.PROC_DATE)) " +
			"and (pgm.CDE_VAR_PMT_TRGR = 'M' or (proc.OVER_STATUS = 'M' and proc.OVER_PROC_UPD_USR != 'SYSTEM' " +
			"and proc.OVER_PROC_DATE < current date)) " +
			"union " +
			"select proc.PROC_ID, proc.PROG_ID, proc.PROG_TYP, proc.PROC_DATE, proc.PROC_ACT_DTE_ID, " +
			"proc.PROC_ACT_DTE, proc.OVER_RTL_ID, proc.OVER_STATUS, proc.OVER_PROC_DATE, " +
			"proc.OVER_PROC_UPD_USR, file.CDE_DPB_TRGR " +
			"from S_PROC proc, DPB_FILE file " +
			"where file.ID_DPB_FILE in (proc.PROG_ID) and proc.PROG_TYP in ('PF') " +
			"and (file.CDE_DPB_STS = 'A' or (file.CDE_DPB_STS = 'I' and file.DTE_INACT > proc.PROC_DATE)) " +
			"and (file.CDE_DPB_TRGR = 'M' or (proc.OVER_STATUS = 'M' and proc.OVER_PROC_UPD_USR != 'SYSTEM' " +
			"and proc.OVER_PROC_DATE < current date))), " +
			"S_PROC_LOG (ID_PROC_LOG, PROC_ID) as ( " +
			"select max(log.ID_DPB_PROC_LOG), log.ID_DPB_PROC " +
			"from DPB_PROC_LOG log, S_MANUAL_TASKS tasks " +
			"where log.ID_DPB_PROC in (tasks.PROC_ID) " +
			"group by log.ID_DPB_PROC), " +
			"S_FINAL (PROC_ID,STATUS) as( " +
			"select tasks.PROC_ID,(select plog.CDE_DPB_PROC_STS from DPB_PROC_LOG plog " +
			"where plog.ID_DPB_PROC_LOG = log.ID_PROC_LOG) as status " +
			"from S_MANUAL_TASKS tasks " +
			"left outer join S_PROC_LOG log on tasks.PROC_ID = log.PROC_ID) " +
			"select PROC_ID from S_FINAL where STATUS = 'F' or STATUS is null";
	
		String FETCH_PREVIOUS_DAY_PROCESSES = "with S_PROC (PROC_ID, PROG_ID_RPT, PROG_ID_FILE, PROG_ID_PGM, PROG_ID_LDR_PGM, PROG_TYP, " + 
											    "PROC_DATE, PROC_ACT_DTE,  OVER_PROC_DTE,  " +
											    "OVER_TME_PROC, OVER_STATUS, OVER_TXT_RSN, OVER_PROC_UPD_USR) as ( " + 
											    "select proc.ID_DPB_PROC, proc.ID_DPB_RPT, proc.ID_DPB_FILE, proc.ID_DPB_PGM, proc.ID_LDRSP_BNS_PGM, " + 
											    "proc.CDE_DPB_PROC_TYP, proc.DTE_CAL,  " +
											    "proc.DTE_DPB_ACTL_PROC, proc.DTE_PROC_OVRD, proc.TME_PROC_OVRD, " + 
											    "proc.CDE_DPB_OVRD_PROC_INIT_TYP, proc.TXT_RSN_PROC_UPD, proc.ID_LAST_UPDT_USER " + 
											    "from DPB_PROC proc  " +
											    "where proc.DTE_CAL = current date - 1 day), " + 
												"S_PROC_LOG (ID_PROC_LOG, PROC_ID) as (  " +
											    "select max(log.ID_DPB_PROC_LOG), log.ID_DPB_PROC " + 
											    "from DPB_PROC_LOG log, S_PROC proc  " +
											    "where log.ID_DPB_PROC in (proc.PROC_ID) " + 
											    "group by log.ID_DPB_PROC),  " +
												"S_PROC_FINAL (PROC_ID, PROG_ID_RPT, PROG_ID_FILE, PROG_ID_PGM, PROG_ID_LDR_PGM, " +
											    "PROG_TYP, PROC_DATE, PROC_ACT_DTE,  OVER_PROC_DTE,  " +
											    "OVER_TME_PROC, OVER_STATUS, OVER_TXT_RSN, OVER_PROC_UPD_USR, LOG_STATUS) as ( " + 
											    "select proc.PROC_ID,  " +
											    "proc.PROG_ID_RPT, proc.PROG_ID_FILE, proc.PROG_ID_PGM, proc.PROG_ID_LDR_PGM, " + 
											    "proc.PROG_TYP, proc.PROC_DATE, proc.PROC_ACT_DTE,  " +
											    "proc.OVER_PROC_DTE, proc.OVER_TME_PROC, proc.OVER_STATUS, proc.OVER_TXT_RSN, proc.OVER_PROC_UPD_USR, " + 
											    "(select trim(plog.CDE_DPB_PROC_STS) from DPB_PROC_LOG plog  " +
											    "where plog.ID_DPB_PROC_LOG = log.ID_PROC_LOG)  " +
											    "from S_PROC proc  " +
											    "left outer join S_PROC_LOG log on proc.PROC_ID = log.PROC_ID) " +
												"select PROC_ID, 'R' as LOG_STATUS, 'Rescheduled to today because yesterday it does not ran.' as LOG_RES_TEXT, " + 
											    "current date as DTE_CAL, PROG_ID_RPT, PROG_ID_FILE, PROG_ID_PGM, PROG_ID_LDR_PGM,  " +
											    "PROC_ACT_DTE, PROG_TYP, OVER_PROC_DTE, OVER_TME_PROC, OVER_STATUS, OVER_TXT_RSN  " +
											    "from S_PROC_FINAL where LOG_STATUS  in ('F','P','T') or LOG_STATUS is null";

	/*String INSERT_INTO_PROC = "INSERT INTO DPB_PROC (ID_DAY, ID_DPB_APP_ENT, DTE_DPB_ACTL_PROC, CDE_DPB_PROC_TYP, " +
			"ID_OVRD_PROC_DTE, TME_OVRD_PROC, CDE_DPB_OVRD_TRGR, TXT_RSN_PROC_UPD, ID_CREA_USER, " +
			"ID_LAST_UPDT_USER, DTS_LAST_UPDT, DTS_CREA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'SYSTEM', 'SYSTEM', " +
			"current timestamp, current timestamp)";*/
	
	String INSERT_INTO_PROC = "INSERT INTO DPB_PROC (ID_DPB_RPT, ID_DPB_PGM, ID_DPB_FILE, DTE_CAL, ID_LDRSP_BNS_PGM, CDE_DPB_PROC_TYP, " + 
			"DTE_DPB_ACTL_PROC, DTE_PROC_OVRD, TME_PROC_OVRD, CDE_DPB_OVRD_PROC_INIT_TYP, TXT_RSN_PROC_UPD, " + 
			"DTS_CREA, ID_CREA_USER, DTS_LAST_UPDT, ID_LAST_UPDT_USER) VALUES " +
			"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current timestamp, ?, current timestamp, ?)";
	
	String FETCH_TODAY_TASKS = "with PROC_TYP_HIER (PROC_TYP, PROC_TYP_SEQ) as (   " +
		    "values (cast('PF' as CHAR(2)), cast(1 as INTEGER)) union   " +
		    "values (cast('AJ' as CHAR(2)), cast(2 as INTEGER)) union   " +
		    "values (cast('FB' as CHAR(2)), cast(3 as INTEGER)) union   " +
		    "values (cast('VB' as CHAR(2)), cast(4 as INTEGER)) union   " +
		    "values (cast('LB' as CHAR(2)), cast(5 as INTEGER)) union   " +
		    "values (cast('CB' as CHAR(2)), cast(6 as INTEGER)) union   " +
		    "values (cast('FP' as CHAR(2)), cast(7 as INTEGER)) union   " +
		    "values (cast('VP' as CHAR(2)), cast(8 as INTEGER)) union   " +
		    "values (cast('LP' as CHAR(2)), cast(9 as INTEGER)) union   " +
		    "values (cast('CP' as CHAR(2)), cast(10 as INTEGER)) union   " +
		    "values (cast('RP' as CHAR(2)), cast(11 as INTEGER))),   " +
			"PROCESS (PROC_ID, PROC_DATE, PROG_ID_RPT, PROG_ID_FILE, PROG_ID_PGM, PROG_ID_LDR_PGM,  " + 
		    "PROG_TYP, PROC_ACT_DTE, OVER_PROC_DTE, TME_OVER_PROC, OVER_STATUS, OVER_TXT_RSN, LST_UPD_USR,  " + 
		    "PROC_TYP_SEQ) as (   " +
		    "select proc.ID_DPB_PROC, proc.DTE_CAL,  " + 
		    "proc.ID_DPB_RPT, proc.ID_DPB_FILE, proc.ID_DPB_PGM, proc.ID_LDRSP_BNS_PGM,  " + 
		    "proc.CDE_DPB_PROC_TYP, proc.DTE_DPB_ACTL_PROC as PROC_ACT_DTE, " +
		    "proc.DTE_PROC_OVRD as  OVER_PROC_DT ,    " +
		    "proc.TME_PROC_OVRD as TME_OVER_PROC, proc.CDE_DPB_OVRD_PROC_INIT_TYP, proc.TXT_RSN_PROC_UPD, proc.ID_LAST_UPDT_USER,  " + 
		    "(select ptHier.PROC_TYP_SEQ from PROC_TYP_HIER ptHier where ptHier.PROC_TYP = proc.CDE_DPB_PROC_TYP)   " +
		    "from DPB_PROC proc   " +
		    "where proc.DTE_CAL = current date),  " + 
			"PROC_LOG (ID_PROC_LOG, PROC_ID) as (    " +
		    "select max(log.ID_DPB_PROC_LOG), log.ID_DPB_PROC  " +  
		    "from DPB_PROC_LOG log, PROCESS proc    " +
		    "where log.ID_DPB_PROC in (proc.PROC_ID)    " +
		    "group by log.ID_DPB_PROC),  " +
			"PROC_LOG_FINAL (PROC_ID, LOG_STATUS) as (  " +  
		    "select proc.PROC_ID, (select trim(plog.CDE_DPB_PROC_STS) from DPB_PROC_LOG plog  " + 
		    "where plog.ID_DPB_PROC_LOG = log.ID_PROC_LOG)     " +
		    "from PROCESS proc    " +
		    "left outer join PROC_LOG log on proc.PROC_ID = log.PROC_ID ),  " +
			"TASKS (PROC_ID, PROC_DATE, PROG_ID_RPT, PROG_ID_FILE, PROG_ID_PGM, PROG_ID_LDR_PGM,  " + 
		    "PROG_TYP, PROC_ACT_DTE, OVER_PROC_DTE, TME_OVER_PROC, OVER_STATUS, OVER_TXT_RSN, LST_UPD_USR,  " + 
		    "PROC_TYP_SEQ, FINAL_TRIG_STATUS) as (   " +
		    "select proc.PROC_ID, proc.PROC_DATE,   " +
		    "proc.PROG_ID_RPT, proc.PROG_ID_FILE, proc.PROG_ID_PGM, proc.PROG_ID_LDR_PGM,  " +
		    "proc.PROG_TYP, proc.PROC_ACT_DTE, proc.OVER_PROC_DTE, proc.TME_OVER_PROC, proc.OVER_STATUS,  " + 
		    "proc.OVER_TXT_RSN, proc.LST_UPD_USR, proc.PROC_TYP_SEQ ,   " +
		    "CASE WHEN (proc.OVER_STATUS is not null and   " +
		    "((proc.LST_UPD_USR != 'SYSTEM' and proc.OVER_PROC_DTE <= current date) or  " + 
		    "(proc.LST_UPD_USR = 'SYSTEM'))) THEN proc.OVER_STATUS ELSE  " +
		    "CASE WHEN proc.PROG_TYP in ('PF','AJ') THEN (select file.CDE_DPB_PROC_INIT_TYP from DPB_FILE file  " + 
		    "where file.ID_DPB_FILE = proc.PROG_ID_FILE and (file.CDE_DPB_STS = 'A' or (file.CDE_DPB_STS = 'I'   " +
		    "and file.DTE_INACT > proc.PROC_DATE))) ELSE   " +
		    "CASE WHEN proc.PROG_TYP = 'FB' THEN (select pgm.CDE_FXD_BNS_PROC_INIT_TYP from DPB_PGM pgm  " + 
		    "where pgm.ID_DPB_PGM = proc.PROG_ID_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'   " +
		    "and pgm.DTE_INACT > proc.PROC_DATE))) ELSE   " +
		    "CASE WHEN proc.PROG_TYP = 'VB' THEN (select pgm.CDE_VAR_BNS_PROC_INIT_TYP from DPB_PGM pgm  " + 
		    "where pgm.ID_DPB_PGM = proc.PROG_ID_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'   " +
		    "and pgm.DTE_INACT > proc.PROC_DATE))) ELSE   " +
		    "CASE WHEN proc.PROG_TYP = 'CB' THEN (select pgm.CDE_COMSN_PROC_INIT_TYP from DPB_PGM pgm  " + 
		    "where pgm.ID_DPB_PGM = proc.PROG_ID_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'   " +
		    "and pgm.DTE_INACT > proc.PROC_DATE))) ELSE  " +
		    "CASE WHEN proc.PROG_TYP = 'LB' THEN 'S' ELSE   " +
		    "CASE WHEN proc.PROG_TYP = 'FP' THEN (select pgm.CDE_FXD_PMT_PROC_INIT_TYP from DPB_PGM pgm  " + 
		    "where pgm.ID_DPB_PGM = proc.PROG_ID_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'   " +
		    "and pgm.DTE_INACT > proc.PROC_DATE))) ELSE   " +
		    "CASE WHEN proc.PROG_TYP = 'VP' THEN (select pgm.CDE_VAR_PMT_PROC_INIT_TYP from DPB_PGM pgm  " + 
		    "where pgm.ID_DPB_PGM = proc.PROG_ID_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'   " +
		    "and pgm.DTE_INACT > proc.PROC_DATE))) ELSE   " +
		    "CASE WHEN proc.PROG_TYP = 'CP' THEN (select pgm.CDE_COMSN_PMT_PROC_INIT_TYP from DPB_PGM pgm  " + 
		    "where pgm.ID_DPB_PGM = proc.PROG_ID_PGM and (pgm.CDE_DPB_STS = 'A' or (pgm.CDE_DPB_STS = 'I'   " +
		    "and pgm.DTE_INACT > proc.PROC_DATE))) ELSE  " +
		    "CASE WHEN proc.PROG_TYP = 'LP' THEN 'S'  ELSE  " +
		    "CASE WHEN proc.PROG_TYP = 'RP' THEN (select rpt.CDE_DPB_PROC_INIT_TYP from DPB_RPT rpt  " + 
		    "where rpt.ID_DPB_RPT = proc.PROG_ID_RPT and (rpt.CDE_DPB_STS = 'A' or (rpt.CDE_DPB_STS = 'I'  " + 
		    "and rpt.DTE_INACT > proc.PROC_DATE)))  " +
		    "END END END END END END END END END END END  " +
		    "from PROCESS proc)   " +
			"select task.PROC_ID, PROC_DATE, PROG_ID_RPT, PROG_ID_FILE, PROG_ID_PGM, PROG_ID_LDR_PGM,  " + 
		    "PROG_TYP, PROC_ACT_DTE, OVER_PROC_DTE, TME_OVER_PROC, OVER_STATUS, OVER_TXT_RSN, LST_UPD_USR,  " + 
		    "PROC_TYP_SEQ, FINAL_TRIG_STATUS   " +
		    "from TASKS task, PROC_LOG_FINAL log   " +
		    "where log.PROC_ID = task.PROC_ID and (log.LOG_STATUS in ('F','P','T') or log.LOG_STATUS is null)  " + 
		    "order by PROC_ACT_DTE, PROC_TYP_SEQ";  
	
	String UPDATE_FAILED_PROCS = "update DPB_PROC set CDE_DPB_OVRD_PROC_INIT_TYP = ?, TXT_RSN_PROC_UPD = ?, " +
								 "ID_LAST_UPDT_USER = ?, DTS_LAST_UPDT = current timestamp where ID_DPB_PROC = ?";
//Bonus query started
	

/*	public static final String RETRIVR_DLR_PAYMENT = "with RTL_DIM (ID_DAY, DTE_CAL) as ( "+
												" select ID_DAY, DTE_CAL from DPB_RTL_CAL_DY_DIM where date(DTE_CAL) between ? and ? "+
												" ), "+
												" CALC (NUM_PO, ID_DLR, ID_PGM, AMT_DPB_BNS_CALC, DTE_CAL) as ( "+
												" select cal.NUM_PO, cal.ID_DLR, cal.ID_PGM, cal.AMT_DPB_BNS_CALC, dim.DTE_CAL "+ 
												" from DPB_CALC cal, RTL_DIM dim,DPB_PROC proc  "+
												" where cal.IND_LDRSP_BNS_PGM = 'N'  "+
												" and cal.ID_DAY in (dim.ID_DAY)  "+
												" and cal.CDE_DPB_CALC_STS = 'BC' and proc.ID_DPB_PROC = cal.ID_DPB_PROC and proc.CDE_DPB_PROC_TYP = ? "+
												" ), "+
												" CALC_SUM (NUM_PO, ID_DLR, ID_PGM, SUM_CALC) as ( "+
												" select cal.NUM_PO, cal.ID_DLR, cal.ID_PGM, sum(AMT_DPB_BNS_CALC) "+
												" from CALC cal "+
												" group by rollup (cal.NUM_PO, cal.ID_DLR, cal.ID_PGM) "+
												" order by cal.ID_DLR, cal.NUM_PO, cal.ID_PGM desc "+
												" ) "+
												" select csum.NUM_PO, csum.ID_DLR, csum.ID_PGM, csum.SUM_CALC, "+
												" pgm.NAM_DPB_PGM as PGM_NAME, veh.TXT_MODL, veh.NUM_VIN, veh.DTE_MDL_YR, veh.CDE_VEH_TYP, "+
												" acct.ID_DPB_CFC_ACCT, (select distinct cal.DTE_CAL from CALC cal  "+
												" where cal.ID_DLR = csum.ID_DLR and cal.NUM_PO = csum.NUM_PO and csum.ID_PGM is null) "+
												" from CALC_SUM csum  "+
												" left outer join DPB_PGM pgm on csum.ID_PGM = pgm.ID_DPB_PGM "+
												" left outer join DPB_VEH_RTL_EXTRT veh on csum.NUM_PO = veh.NUM_PO "+
												" left outer join DPB_PGM_CFC_ACCT_REL acct on csum.ID_PGM = acct.ID_PGM "+ 
												" and acct.IND_LDRSP_BNS_PGM = 'N' and acct.CDE_DPB_STS = 'A'  "+
												" and veh.CDE_VEH_TYP = acct.CDE_VEH_TYP "+
												" where csum.NUM_PO is not null and csum.ID_DLR is not null ";*/
	
	public static final String RETRIVR_DLR_PAYMENT = "with RTL_DIM (DTE_CAL) as (   "+	
													" select DTE_CAL from DPB_DAY 	 "+
													" where date(DTE_CAL) between ? and ? "+
													" ), 	 "+
													" CALC (ID_DPB_PROC, ID_DPB_UNBLK_VEH, ID_DLR, ID_DPB_PGM, AMT_DPB_BNS_CALC, DTE_CAL) as ( "+	 
													" select cal.ID_DPB_PROC,cal.ID_DPB_UNBLK_VEH, cal.ID_DLR, cal.ID_DPB_PGM, 	 "+
													" cal.AMT_DPB_BNS_CALC, dim.DTE_CAL  	 "+
													" from DPB_CALC cal, RTL_DIM dim,(select ID_DPB_PROC from DPB_PROC P where P.CDE_DPB_PROC_TYP = ? "+ 	
													" and  P.ID_DPB_PGM = ? ) proc	    "+
													" where cal.DTE_CAL in (dim.DTE_CAL)   "+ 	
													" and cal.CDE_DPB_CALC_STS in ('BC','TC') and  cal.ID_DPB_PROC = proc.ID_DPB_PROC "+ 
													"  	 "+
													" ),  	 "+
													" CALC_DATA (ID_DPB_UNBLK_VEH, ID_DLR, ID_DPB_PGM, SUM_CALC) as ( "+	  
													" select cal.ID_DPB_UNBLK_VEH, cal.ID_DLR, cal.ID_DPB_PGM, sum(AMT_DPB_BNS_CALC) "+	  
													" from CALC cal  	 "+
													" group by rollup (cal.ID_DPB_UNBLK_VEH, cal.ID_DLR, cal.ID_DPB_PGM) "+	  
													" order by cal.ID_DLR, cal.ID_DPB_UNBLK_VEH, cal.ID_DPB_PGM desc  	 "+
													" ),	   "+
													" CALC_SUM (ID_DPB_UNBLK_VEH,ID_DLR,ID_DPB_PGM,SUM_CALC) as "+	  
													" (  	 "+
													" select ID_DPB_UNBLK_VEH,ID_DLR,ID_DPB_PGM,SUM_CALC from CALC_DATA CD where CD.ID_DLR is not null "+	
													" ), "+  
													" cal_temp (ID_DLR,ID_DPB_UNBLK_VEH,DTE_CAL)            as "+
													" ( "+
													" select distinct ID_DLR,ID_DPB_UNBLK_VEH,DTE_CAL from CALC cal "+ 
													" ),  	 "+
													" CAL_VEH	 "+
													" (	 "+
													" ID_DPB_UNBLK_VEH,NUM_PO, ID_DLR, ID_DPB_PGM, SUM_CALC,	   "+
													" PGM_NAME, DES_MODL, NUM_VIN, DTE_MODL_YR, CDE_VEH_TYP,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,DTE_CAL "+	
													" )as( 	 "+
													" select csum.ID_DPB_UNBLK_VEH, veh.NUM_PO,csum.ID_DLR, csum.ID_DPB_PGM, csum.SUM_CALC, pgm.NAM_DPB_PGM as PGM_NAME, "+	 
													" veh.DES_MODL, veh.NUM_VIN, veh.DTE_MODL_YR, veh.CDE_VEH_TYP,veh.CDE_VEH_DDR_STS,veh.CDE_VEH_DDR_USE, case when csum.ID_DPB_PGM is null then cal.DTE_CAL else null  end as DTE_CAL	 "+													
													" from CALC_SUM csum   	 "+
													" left outer join DPB_PGM pgm on csum.ID_DPB_PGM = pgm.ID_DPB_PGM "+	    
													" left outer join DPB_UNBLK_VEH veh on csum.ID_DPB_UNBLK_VEH = veh.ID_DPB_UNBLK_VEH "+	  
													" left outer join cal_temp cal on cal.ID_DLR = csum.ID_DLR  and cal.ID_DPB_UNBLK_VEH = csum.ID_DPB_UNBLK_VEH "+
													" where csum.ID_DPB_UNBLK_VEH is not null and csum.ID_DLR is not null 	 "+
													" )	 "+
													" SELECT V.*,acct.ID_DPB_CFC_ACCT_CR,acct.ID_DPB_CFC_ACCT_DT , CASE WHEN v.ID_DPB_PGM is not null then 'B' ELSE 'D' END LTYPE FROM CAL_VEH V "+	  
													" left outer join DPB_PGM_VEH_REL acct on V.ID_DPB_PGM= acct.ID_DPB_PGM  	 "+
													" and acct.ID_LDRSP_BNS_PGM is null and acct.CDE_DPB_STS = 'A'   	 "+
													" and V.CDE_VEH_TYP = acct.CDE_VEH_TYP order by NUM_VIN,ID_DPB_PGM desc with ur "; 
	
	public static final String RETRIVR_LDRSHIP_DLR_PAYMENT = " with RTL_DIM ( DTE_CAL) as (   "+
															 " select  DTE_CAL from DPB_DAY where date(DTE_CAL) between ? and ? ), "+  
															 " CALC (ID_DPB_PROC, ID_DPB_UNBLK_VEH, ID_DLR, ID_LDRSP_BNS_PGM, AMT_DPB_BNS_CALC, DTE_CAL) as ( "+   
															 " select cal.ID_DPB_PROC,cal.ID_DPB_UNBLK_VEH, cal.ID_DLR,cal.ID_LDRSP_BNS_PGM, cal.AMT_DPB_BNS_CALC, proc.DTE_DPB_ACTL_PROC "+   
															 " from DPB_CALC cal, DPB_PROC proc  "+ 
															 " where "+  
															 " cal.CDE_DPB_CALC_STS = 'LC' and proc.ID_DPB_PROC = cal.ID_DPB_PROC and proc.ID_LDRSP_BNS_PGM = ?) , "+  
															 " CALC_SUM (ID_DPB_UNBLK_VEH, ID_DLR, ID_LDRSP_BNS_PGM, SUM_CALC) as (  "+ 
															 " select cal.ID_DPB_UNBLK_VEH, cal.ID_DLR, cal.ID_LDRSP_BNS_PGM, sum(AMT_DPB_BNS_CALC) from CALC cal "+   
															 " group by rollup (cal.ID_DPB_UNBLK_VEH, cal.ID_DLR, cal.ID_LDRSP_BNS_PGM) "+   
															 " order by cal.ID_DLR, cal.ID_DPB_UNBLK_VEH, cal.ID_LDRSP_BNS_PGM desc ) "+ 
															 " select csum.ID_DPB_UNBLK_VEH, csum.ID_DLR, csum.ID_LDRSP_BNS_PGM as ID_DPB_PGM, csum.SUM_CALC, "+  
															 " pgm.NAM_LDRSP_BNS as PGM_NAME,veh.NUM_PO, veh.DES_MODL, veh.NUM_VIN, veh.DTE_MODL_YR, veh.CDE_VEH_TYP, veh.CDE_VEH_DDR_STS,veh.CDE_VEH_DDR_USE,"+ 
															 " acct.ID_DPB_CFC_ACCT_CR,acct.ID_DPB_CFC_ACCT_DT, (select distinct cal.DTE_CAL from CALC cal "+   
															 " where cal.ID_DLR = csum.ID_DLR and cal.ID_DPB_UNBLK_VEH = csum.ID_DPB_UNBLK_VEH and csum.ID_LDRSP_BNS_PGM is null) "+  
															 " from CALC_SUM csum "+  
															 " left outer join LDRSP_BNS_PGM pgm on csum.ID_LDRSP_BNS_PGM   = pgm.ID_LDRSP_BNS_PGM  "+ 
															 " left outer join DPB_UNBLK_VEH veh on csum.ID_DPB_UNBLK_VEH = veh.ID_DPB_UNBLK_VEH "+
															 " left outer join DPB_PGM_VEH_REL acct on csum.ID_LDRSP_BNS_PGM = acct.ID_LDRSP_BNS_PGM "+  
															 " and  acct.ID_DPB_PGM is null and acct.CDE_DPB_STS = 'A' and veh.CDE_VEH_TYP = acct.CDE_VEH_TYP  "+ 
															 " where csum.ID_DPB_UNBLK_VEH is not null and csum.ID_DLR is not null order by veh.NUM_VIN ,csum.ID_LDRSP_BNS_PGM desc  ";	
	
	//Done
	//ID_DLR,CDE_DLR_SHT_NAM,NAM_DLR,NAM_DO_BUSN_AS,ADR_PRIM_CITY,CDE_DLR_ST,DTE_MBUSA_TERM,CDE_RGN,CDE_MKT,CDE_MTRO_MKT_GRP
	public static final String GET_TERMINATED_DEALER = 	" SELECT ID_DLR,CDE_DLR_SHT_NAM,NAM_DLR,NAM_DO_BUSN_AS," +
														" ADR_PRIM_CITY,CDE_DLR_ST,DTE_MBUSA_TERM " +
														" FROM DEALER_FDRT   WHERE date(DTE_MBUSA_TERM) = ? " ;
													
	//Done
	public static final String GET_DEALER_FRANCHISES_LIST = " SELECT DISTINCT CDE_VEH_TYP"+
															" FROM DPB_UNBLK_VEH"+
															" WHERE ID_DLR = ?";
	//Done
	public static final String GET_BONUS_INFO_ON_PO_NUMBER 		= " SELECT ID_DPB_PROC, ID_DPB_UNBLK_VEH, ID_DPB_PGM, "+
																 	" SUM(AMT_DPB_BNS_CALC) TOT, AMT_DPB_MAX_BNS_ELG, "+
																 	" IND_DPB_ADJ,DTE_CAL  FROM DPB_CALC  WHERE ID_DLR = ?  and ID_LDRSP_BNS_PGM is null "+
																 	" GROUP BY ID_DPB_UNBLK_VEH, ID_DPB_PGM,ID_DPB_PROC, AMT_DPB_MAX_BNS_ELG,  "+
																 	" ID_DLR,DTE_CAL,IND_DPB_ADJ ";

	public static final String GET_NON_SETTLED_VISTA_RETAIL_DATA = " SELECT NUM_PO, NUM_VIN, DTE_RTL, TME_RTL, CDE_VEH_STS, ID_DLR, IND_USED_VEH, " +
																	" ID_EMP_PUR_CTRL,DTE_MODL_YR, DES_MODL,  " +
																	" CDE_RGN, NAM_RTL_CUS_LST, NAM_RTL_CUS_FIR, NAM_RTL_CUS_MID, DTE_TRANS, " +
																	" TME_TRANS, CDE_USE_VEH, AMT_MSRP_BASE, AMT_MSRP, AMT_MSRP_TOT_ACSRY, AMT_DLR_RBT, " +
																	" IND_FLT, CDE_WHSLE_INIT_TYP, CDE_VEH_TYP, CDE_NATL_TYPE,CDE_VEH_TYP, " +
																	" DTS_LAST_UPDT, DTS_CREA, ID_CREA_USER, ID_LAST_UPDT_USER, " +
																	" ID_DPB_PGM,CDE_VEH_DDR_USE,DTE_VEH_DEMO_SRV,CDE_VEH_DDR_STS,IND_USED_VEH_DDRS " +
																	" FROM DPB_UNBLK_VEH WHERE  " +
																    " ID_DLR = ?  and date(DTE_RTL) between ? and ? and CDE_VEH_TYP in (?)   "; //and CDE_VEH_STS !='I2'
																  
																   //"NUM_PO NOT IN (?)";
	
	
		//Changes for Unearned Performance Bonus calculation - Kshitija (added column amt_apb_unernd)
		public static final String INSERT_BONUS_INFO_FR_TERMINATE_DEALER = " INSERT INTO DPB_CALC (" + 
																		   " ID_DPB_PROC,ID_DPB_UNBLK_VEH,ID_DLR,DTE_CAL,ID_DPB_PGM ," + //5
																		   " AMT_DPB_BNS_CALC, AMT_DPB_MAX_BNS_ELG,IND_DPB_ADJ," + //3
																		   " CDE_DPB_CALC_STS,AMT_DPB_UNERND,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_CREA,DTS_LAST_UPDT) VALUES " + //4
																		   " (?,?,?,?,?,?,?,?,?,?,?,?,CURRENT TIMESTAMP, CURRENT TIMESTAMP)";

		//Changes for Unearned Performance Bonus calculation - Kshitija (added column amt_apb_unernd)
		public static final String INSERT_BONUS_INFO_FR_TERMINATE_DEALER_LDRSP = " INSERT INTO DPB_CALC (" + 
																			   " ID_DPB_PROC,ID_DPB_UNBLK_VEH,ID_DLR,DTE_CAL,ID_LDRSP_BNS_PGM ," + //5
																			   " AMT_DPB_BNS_CALC, AMT_DPB_MAX_BNS_ELG,IND_DPB_ADJ," + //3
																			   " CDE_DPB_CALC_STS,AMT_DPB_UNERND,ID_CREA_USER,ID_LAST_UPDT_USER,DTS_CREA,DTS_LAST_UPDT) VALUES " + //4
																			   " (?,?,?,?,?,?,?,?,?,?,?,?,CURRENT TIMESTAMP, CURRENT TIMESTAMP)";
																					
	
/*	public static final String GET_KPI_FILE = " SELECT KPI_FIL_EXTRT.ID_DLR, KPI_FIL_EXTRT.DTE_FSCL_QTR, "+ 
												" KPI_FIL_EXTRT.DTE_FSCL_YR, KPI_FIL_EXTRT.ID_KPI, max (KPI_FIL_EXTRT.PCT_KPI) as PCT_KPI,  "+
												"  count( KPI_FIL_EXTRT.ID_KPI) "+
												"  FROM DPB.DPB_KPI_FIL_EXTRT AS KPI_FIL_EXTRT "+ 
												"  WHERE KPI_FIL_EXTRT.DTE_FSCL_YR = ? AND KPI_FIL_EXTRT.DTE_FSCL_QTR = ? AND ID_KPI = ? "+ 
												" and KPI_FIL_EXTRT.ID_DLR = ?  "+
												"  GROUP BY  "+
												"  KPI_FIL_EXTRT.ID_DLR, KPI_FIL_EXTRT.DTE_FSCL_QTR, KPI_FIL_EXTRT.DTE_FSCL_YR, KPI_FIL_EXTRT.ID_KPI "+ 			 
												"  HAVING COUNT(KPI_FIL_EXTRT.ID_KPI  "+
												"  ) > 1 or COUNT(KPI_FIL_EXTRT.ID_KPI  "+
												"  ) = 1  "+
												"  ORDER BY KPI_FIL_EXTRT.ID_DLR ASC, KPI_FIL_EXTRT.DTE_FSCL_QTR ASC, "+
												"  KPI_FIL_EXTRT.DTE_FSCL_YR ASC, KPI_FIL_EXTRT.ID_KPI ASC " ; */

	public static final String GET_KPI_FILE = " with LST_KPI (LST_UPDT,ID_DLR,QTR,YR,ID_KPI) AS (   " +
														" SELECT MAX(DTS_LAST_UPDT), ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI " +
														" from DPB_KPI_FIL_EXTRT dlrkpi where  DTE_FSCL_YR = ? and  DTE_FSCL_QTR = ?  " +
														" and  ID_KPI = ? and ID_DLR= ?  " +
														" group by ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI ) " +
														" select kpi.ID_DLR, kpi.DTE_FSCL_QTR, kpi.DTE_FSCL_YR, kpi.ID_KPI,kpi.PCT_KPI " +
														" from DPB_KPI_FIL_EXTRT kpi,LST_KPI lstkpi " +
														" where kpi.DTS_LAST_UPDT = lstkpi.LST_UPDT and kpi.ID_DLR = lstkpi.ID_DLR " + 
														" and kpi.DTE_FSCL_QTR = lstkpi.QTR and kpi.DTE_FSCL_YR = lstkpi.YR and kpi.ID_KPI = lstkpi.ID_KPI " ; 
			
	public static final String CANCELLED_PO_LIST = 	" SELECT NUM_PO,NUM_VIN,DTE_RTL,ID_DLR" +
											   		" FROM DPB_VEH_RTL_EXTRT" +
											   		" WHERE CDE_VEH_TYP = ?";//11
	public static final String CANCELLED_BONUS_RECORD_LIST = " with cancelPO (PO,VIN,DTE_RTL,DLR) as (" +
															" SELECT NUM_PO PO,NUM_VIN VIN,DTE_RTL,ID_DLR DLR FROM DPB_VEH_RTL_EXTRT" +
															" WHERE date(DTE_RTL) between ? and ? and CDE_CAR_STS = 'I2')" +
															" select ID_DPB_PROC,NUM_PO,ID_DLR,ID_DAY,ID_PGM,IND_LDRSP_BNS_PGM,AMT_DPB_BNS_CALC, " +
															" AMT_DPB_MAX_BNS_ELG,IND_DPB_ADJ,CDE_DPB_CALC_STS  from DPB_CALC c  join cancelPO p on " +
															" c.NUM_PO = p.PO and c.ID_DLR = p.DLR " ;

	
	
	public static final String GET_VEHICLE_PROG_MAP_AUTO = 	  " WITH PROC (ID_DPB_PROC, ID_DPB_PGM, CDE_DPB_PROC_TYP, ID_OVRD_RTL_DTE, ID_DPB_ACTL_PROC_DTE) AS"+
															  " ( SELECT ID_DPB_PROC, ID_DPB_PGM, CDE_DPB_PROC_TYP, ID_OVRD_RTL_DTE, ID_DPB_ACTL_PROC_DTE"+
															  " FROM DPB_PROC, DPB_RTL_CAL_DY_DIM CAL WHERE CAL.DTE_CAL = ? AND ID_DPB_ACTL_PROC_DTE = CAL.ID_DAY"+
															  " ), PROG (ID_DPB_PGM, NAM_DPB_PGM, DES_DPB_PGM, DTE_DPB_PGM_START, DTE_DPB_PGM_END," +
															  " IND_DPB_RBT_PMT, IND_DPB_COMSN_PMT, AMT_DPB_COMSN_PMT, PCT_DPB_COMSN_PMT, PCT_FXD_PMT," +
															  " CDE_COMSN_BNS_SCHD, CDE_COMSN_BNS_TRGR, CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_TRGR, CDE_FXD_BNS_SCHD, CDE_FXD_BNS_TRGR, CDE_FXD_PMT_SCHD, CDE_FXD_PMT_TRGR, IND_DPB_FXD_PMT," +
															  " IND_MAX_VAR_PMT, PCT_VAR_PMT, CDE_VAR_BNS_SCHD, CDE_VAR_BNS_TRGR, CDE_VAR_PMT_SCHD, CDE_VAR_PMT_TRGR," +
															  " IND_SPL_DPB_PGM, ID_KPI, CDE_DPB_STS, DTE_INACT, CDE_VEH_TYP) AS (SELECT PGM.ID_DPB_PGM, NAM_DPB_PGM, DES_DPB_PGM, DTE_DPB_PGM_START, DTE_DPB_PGM_END," +
															  " IND_DPB_RBT_PMT, IND_DPB_COMSN_PMT, AMT_DPB_COMSN_PMT, PCT_DPB_COMSN_PMT, PCT_FXD_PMT," +
															  " CDE_COMSN_BNS_SCHD, CDE_COMSN_BNS_TRGR, CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_TRGR, CDE_FXD_BNS_SCHD, CDE_FXD_BNS_TRGR, CDE_FXD_PMT_SCHD, CDE_FXD_PMT_TRGR, IND_DPB_FXD_PMT," +
															  " IND_MAX_VAR_PMT, PCT_VAR_PMT, CDE_VAR_BNS_SCHD, CDE_VAR_BNS_TRGR, CDE_VAR_PMT_SCHD, CDE_VAR_PMT_TRGR," +
															  " IND_SPL_DPB_PGM, ID_KPI, CDE_DPB_STS, DTE_INACT, CDE_VEH_TYP FROM DPB_PGM PGM JOIN DPB_PGM_VEH_REL PGMVEH ON PGM.ID_DPB_PGM = PGMVEH.ID_DPB_PGM )" +
															  " select PROC.ID_DPB_PROC, PROG.ID_DPB_PGM, PROC.CDE_DPB_PROC_TYP, PROG.NAM_DPB_PGM, PROG.DES_DPB_PGM, PROG.DTE_DPB_PGM_START, PROG.DTE_DPB_PGM_END," +
															  " PROG.IND_DPB_RBT_PMT, PROG.IND_DPB_COMSN_PMT, PROG.AMT_DPB_COMSN_PMT, PROG.PCT_DPB_COMSN_PMT, PROG.PCT_FXD_PMT," +
															  " PROG.CDE_COMSN_BNS_SCHD, PROG.CDE_COMSN_BNS_TRGR, PROG.CDE_FXD_BNS_SCHD, PROG.CDE_FXD_BNS_TRGR, PROG.CDE_FXD_PMT_SCHD, PROG.CDE_FXD_PMT_TRGR, PROG.IND_DPB_FXD_PMT," +
															  " PROG.IND_MAX_VAR_PMT, PROG.PCT_VAR_PMT, PROG.CDE_VAR_BNS_SCHD, PROG.CDE_VAR_BNS_TRGR, PROG.CDE_VAR_PMT_SCHD, PROG.CDE_VAR_PMT_TRGR," +
															  " PROG.IND_SPL_DPB_PGM, PROG.ID_KPI, PROG.CDE_DPB_STS, PROG.DTE_INACT, PROG.CDE_VEH_TYP FROM PROC, PROG WHERE PROC.ID_DPB_PGM = PROG.ID_DPB_PGM AND PROC.CDE_DPB_PROC_TYP IN ('FB','VB')";

	/*public static final String GET_VEHICLE_PROG_MAP_MANUAL = " WITH PROC (ID_DPB_PROC, ID_DPB_PGM, CDE_DPB_PROC_TYP, ID_OVRD_RTL_DTE, ID_DPB_ACTL_PROC_DTE) AS"+
														     " ( SELECT ID_DPB_PROC, ID_DPB_PGM, CDE_DPB_PROC_TYP, ID_OVRD_RTL_DTE, ID_DPB_ACTL_PROC_DTE"+
														     " FROM DPB_PROC, DPB_RTL_CAL_DY_DIM CAL WHERE CAL.DTE_CAL = ? AND ((ID_DPB_ACTL_PROC_DTE = CAL.ID_DAY AND ID_OVRD_RTL_DTE = null) OR (ID_OVRD_RTL_DTE = CAL.ID_DAY))"+
															 " ), PROG (ID_DPB_PGM, NAM_DPB_PGM, DES_DPB_PGM, DTE_DPB_PGM_START, DTE_DPB_PGM_END," +
															 " IND_DPB_RBT_PMT, IND_DPB_COMSN_PMT, AMT_DPB_COMSN_PMT, PCT_DPB_COMSN_PMT, PCT_FXD_PMT," +
															 " CDE_COMSN_BNS_SCHD, CDE_COMSN_BNS_TRGR, CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_TRGR, CDE_FXD_BNS_SCHD, CDE_FXD_BNS_TRGR, CDE_FXD_PMT_SCHD, CDE_FXD_PMT_TRGR, IND_DPB_FXD_PMT," +
															 " IND_MAX_VAR_PMT, PCT_VAR_PMT, CDE_VAR_BNS_SCHD, CDE_VAR_BNS_TRGR, CDE_VAR_PMT_SCHD, CDE_VAR_PMT_TRGR," +
															 " IND_SPL_DPB_PGM, ID_KPI, CDE_DPB_STS, DTE_INACT, CDE_VEH_TYP) AS (SELECT PGM.ID_DPB_PGM, NAM_DPB_PGM, DES_DPB_PGM, DTE_DPB_PGM_START, DTE_DPB_PGM_END," +
															 " IND_DPB_RBT_PMT, IND_DPB_COMSN_PMT, AMT_DPB_COMSN_PMT, PCT_DPB_COMSN_PMT, PCT_FXD_PMT," +
															 " CDE_COMSN_BNS_SCHD, CDE_COMSN_BNS_TRGR, CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_TRGR, CDE_FXD_BNS_SCHD, CDE_FXD_BNS_TRGR, CDE_FXD_PMT_SCHD, CDE_FXD_PMT_TRGR, IND_DPB_FXD_PMT," +
															 " IND_MAX_VAR_PMT, PCT_VAR_PMT, CDE_VAR_BNS_SCHD, CDE_VAR_BNS_TRGR, CDE_VAR_PMT_SCHD, CDE_VAR_PMT_TRGR," +
															 " IND_SPL_DPB_PGM, ID_KPI, CDE_DPB_STS, DTE_INACT, CDE_VEH_TYP FROM DPB_PGM PGM JOIN DPB_PGM_VEH_REL PGMVEH ON PGM.ID_DPB_PGM = PGMVEH.ID_DPB_PGM )" +
															 " SELECT PROC.ID_DPB_PROC, PROG.ID_DPB_PGM, PROC.CDE_DPB_PROC_TYP, PROG.NAM_DPB_PGM, PROG.DES_DPB_PGM, PROG.DTE_DPB_PGM_START, PROG.DTE_DPB_PGM_END," +
															 " PROG.IND_DPB_RBT_PMT, PROG.IND_DPB_COMSN_PMT, PROG.AMT_DPB_COMSN_PMT, PROG.PCT_DPB_COMSN_PMT, PROG.PCT_FXD_PMT," +
															 " PROG.CDE_COMSN_BNS_SCHD, PROG.CDE_COMSN_BNS_TRGR, PROG.CDE_COMSN_PMT_SCHD, PROG.CDE_COMSN_PMT_TRGR, PROG.CDE_FXD_BNS_SCHD, PROG.CDE_FXD_BNS_TRGR, PROG.CDE_FXD_PMT_SCHD, PROG.CDE_FXD_PMT_TRGR, PROG.IND_DPB_FXD_PMT," +
															 " PROG.IND_MAX_VAR_PMT, PROG.PCT_VAR_PMT, PROG.CDE_VAR_BNS_SCHD, PROG.CDE_VAR_BNS_TRGR, PROG.CDE_VAR_PMT_SCHD, PROG.CDE_VAR_PMT_TRGR," +
															 " PROG.IND_SPL_DPB_PGM, PROG.ID_KPI, PROG.CDE_DPB_STS, PROG.DTE_INACT, PROG.CDE_VEH_TYP FROM PROC, PROG WHERE PROC.ID_DPB_PGM = PROG.ID_DPB_PGM AND PROC.CDE_DPB_PROC_TYP IN ('FB','VB')";
*/
	public static final String GET_VEHICLE_PROG_MAP_MANUAL = " WITH PROC (  "+
																" ID_DPB_PROC, ID_DPB_PGM, CDE_DPB_PROC_TYP, DTE_DPB_ACTL_PROC,DTE_PROC_OVRD,TME_PROC_OVRD, "+ 
																"     CDE_DPB_OVRD_PROC_INIT_TYP,TXT_RSN_PROC_UPD "+
																" ) AS (  "+
																"     SELECT ID_DPB_PROC, ID_DPB_PGM,CDE_DPB_PROC_TYP,DTE_DPB_ACTL_PROC, "+
																"            DTE_PROC_OVRD ,TME_PROC_OVRD , CDE_DPB_OVRD_PROC_INIT_TYP,TXT_RSN_PROC_UPD "+ 
																"     FROM DPB_PROC PROC, DPB_DAY CAL  "+
																"     WHERE CAL.DTE_CAL = ? AND  ((PROC.DTE_CAL = CAL.DTE_CAL "+ 
																"     AND  PROC.DTE_PROC_OVRD is null) OR (PROC.DTE_PROC_OVRD = CAL.DTE_CAL)) "+  
																" ), "+
																" PROG ( "+
																"   ID_DPB_PGM, NAM_DPB_PGM, DES_DPB_PGM, DTE_DPB_PGM_START, DTE_DPB_PGM_END, IND_DPB_RBT_PMT, "+ 
																"   IND_DPB_COMSN_PMT, AMT_DPB_COMSN_PMT, PCT_FXD_PMT,  "+
																"   CDE_COMSN_BNS_PROC_SCHD , CDE_COMSN_PROC_INIT_TYP, CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_PROC_INIT_TYP, "+ 
																"   CDE_FXD_BNS_SCHD, CDE_FXD_BNS_PROC_INIT_TYP, CDE_FXD_PMT_SCHD, CDE_FXD_PMT_PROC_INIT_TYP,  "+
																"   IND_DPB_FXD_PMT, IND_MAX_VAR_PMT, PCT_VAR_PMT, "+
																"   CDE_VAR_BNS_SCHD, CDE_VAR_BNS_PROC_INIT_TYP, CDE_VAR_PMT_SCHD, CDE_VAR_PMT_PROC_INIT_TYP, "+ 
																"   CDE_DPB_PGM_TYP, ID_KPI,  "+
																"   CDE_DPB_STS, DTE_INACT,CDE_VEH_TYP "+
																" ) AS (  "+
																"   SELECT PGM.ID_DPB_PGM, NAM_DPB_PGM, DES_DPB_PGM, DTE_DPB_PGM_START,DTE_DPB_PGM_END, "+ 
																"   IND_DPB_RBT_PMT, IND_DPB_COMSN_PMT, AMT_DPB_COMSN_PMT, PCT_FXD_PMT,  "+
																"   CDE_COMSN_BNS_PROC_SCHD , CDE_COMSN_PROC_INIT_TYP, CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_PROC_INIT_TYP, "+ 
																"   CDE_FXD_BNS_SCHD, CDE_FXD_BNS_PROC_INIT_TYP, CDE_FXD_PMT_SCHD, CDE_FXD_PMT_PROC_INIT_TYP,  "+
																"   IND_DPB_FXD_PMT, IND_MAX_VAR_PMT, PCT_VAR_PMT, "+
																"   CDE_VAR_BNS_SCHD, CDE_VAR_BNS_PROC_INIT_TYP, CDE_VAR_PMT_SCHD, CDE_VAR_PMT_PROC_INIT_TYP, "+ 
																"   CDE_DPB_PGM_TYP, ID_KPI, "+
																"   PGM.CDE_DPB_STS, DTE_INACT, CDE_VEH_TYP "+  
																"   FROM DPB_PGM PGM JOIN DPB_PGM_VEH_REL PGMVEH ON PGM.ID_DPB_PGM = PGMVEH.ID_DPB_PGM "+ 
																" )  "+
																" SELECT P.ID_DPB_PROC, PROG.ID_DPB_PGM, P.CDE_DPB_PROC_TYP, PROG.NAM_DPB_PGM , PROG.DES_DPB_PGM PGM_NAME, "+ 
																" 	PROG.DTE_DPB_PGM_START, PROG.DTE_DPB_PGM_END, PROG.IND_DPB_RBT_PMT, PROG.IND_DPB_COMSN_PMT,  "+
																"   PROG.AMT_DPB_COMSN_PMT,  PROG.PCT_FXD_PMT, "+ 
																"   PROG.CDE_COMSN_BNS_PROC_SCHD, CDE_COMSN_PROC_INIT_TYP, PROG.CDE_COMSN_PMT_SCHD,PROG.CDE_COMSN_PMT_PROC_INIT_TYP, "+ 
																"   PROG.CDE_FXD_BNS_SCHD, PROG.CDE_FXD_BNS_PROC_INIT_TYP, PROG.CDE_FXD_PMT_SCHD, PROG.CDE_FXD_PMT_PROC_INIT_TYP, "+
																"   PROG.IND_DPB_FXD_PMT, PROG.IND_MAX_VAR_PMT, PROG.PCT_VAR_PMT,  "+
																"   PROG.CDE_VAR_BNS_SCHD, PROG.CDE_VAR_BNS_PROC_INIT_TYP,PROG.CDE_VAR_PMT_SCHD, PROG.CDE_VAR_PMT_PROC_INIT_TYP, "+ 
																"   PROG.CDE_DPB_PGM_TYP, PROG.ID_KPI, PROG.CDE_DPB_STS, PROG.DTE_INACT,  "+
																"   PROG.CDE_VEH_TYP,P.DTE_PROC_OVRD,P.DTE_DPB_ACTL_PROC FROM PROC P, PROG "+ 
																" WHERE P.ID_DPB_PGM = PROG.ID_DPB_PGM AND P.CDE_DPB_PROC_TYP IN ('FB','VB','CB') " ;
	public static final String GET_GEN_PGM_BY_VEH = "with VEH_MAP(CDE_VEH_TYP, ID_DPB_PGM) as ( select CDE_VEH_TYP, ID_DPB_PGM from DPB_PGM_VEH_REL where CDE_VEH_TYP= ? " +
													" order by CDE_VEH_TYP,ID_DPB_PGM), " +
													" pgm (ID_DPB_PGM, NAM_DPB_PGM, DES_DPB_PGM, DTE_DPB_PGM_START, DTE_DPB_PGM_END, IND_DPB_RBT_PMT," +
													" IND_DPB_COMSN_PMT, AMT_DPB_COMSN_PMT,  PCT_FXD_PMT, " +
													" CDE_COMSN_BNS_PROC_SCHD, CDE_COMSN_PROC_INIT_TYP, " +
													" CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_PROC_INIT_TYP,   " +
													" CDE_FXD_BNS_SCHD,CDE_FXD_BNS_PROC_INIT_TYP, " +
													" CDE_FXD_PMT_SCHD,CDE_FXD_PMT_PROC_INIT_TYP,  " +
													" IND_DPB_FXD_PMT, IND_MAX_VAR_PMT,PCT_VAR_PMT,  " +
													" CDE_VAR_BNS_SCHD, CDE_VAR_BNS_PROC_INIT_TYP,  " +
													" CDE_VAR_PMT_SCHD, CDE_VAR_PMT_PROC_INIT_TYP,  " +
													" CDE_DPB_PGM_TYP, ID_KPI, CDE_DPB_STS, DTE_INACT, CDE_VEH_TYP) as " + 
													" (select veh.ID_DPB_PGM AS ID_DPB_PGM, PGM.NAM_DPB_PGM, PGM.DES_DPB_PGM, PGM.DTE_DPB_PGM_START, PGM.DTE_DPB_PGM_END, " +
													" PGM.IND_DPB_RBT_PMT, " +
													" PGM.IND_DPB_COMSN_PMT, PGM.AMT_DPB_COMSN_PMT,  PGM.PCT_FXD_PMT, " + 
													" PGM.CDE_COMSN_BNS_PROC_SCHD, PGM.CDE_COMSN_PROC_INIT_TYP, " +
													" PGM.CDE_COMSN_PMT_SCHD, PGM.CDE_COMSN_PMT_PROC_INIT_TYP,   " +
													" " +
													" PGM.CDE_FXD_BNS_SCHD,PGM.CDE_FXD_BNS_PROC_INIT_TYP, " +
													" PGM.CDE_FXD_PMT_SCHD,PGM.CDE_FXD_PMT_PROC_INIT_TYP, " +
													" PGM.IND_DPB_FXD_PMT, PGM.IND_MAX_VAR_PMT,PGM.PCT_VAR_PMT, " +
													" PGM.CDE_VAR_BNS_SCHD, PGM.CDE_VAR_BNS_PROC_INIT_TYP,  " +
													" PGM.CDE_VAR_PMT_SCHD, PGM.CDE_VAR_PMT_PROC_INIT_TYP, " +
													" " +
													" PGM.CDE_DPB_PGM_TYP, PGM.ID_KPI, CDE_DPB_STS, PGM.DTE_INACT, veh.CDE_VEH_TYP FROM DPB_PGM PGM, VEH_MAP veh " + 
													" where pgm.ID_DPB_PGM in (veh.ID_DPB_PGM ) and pgm.CDE_DPB_STS = 'A' and ( (current date) between DTE_DPB_PGM_START and DTE_DPB_PGM_END ) " +
													" and CDE_DPB_PGM_TYP = 'G' AND IND_DPB_FXD_PMT = ?) " +
													" select * from pgm order by CDE_VEH_TYP  ";

	
	public static final String GET_ACT_PGM_VEH = " SELECT ID_DPB_PGM, ID_KPI, IND_SPL_DPB_PGM"+
												 " FROM DPB_PGM PGM"+
												 " JOIN DPB_PGM_VEH_REL PGMVEH ON PGM.ID_DPB_PGM = PGMVEH.ID_DPB_PGM" +
												 " JOIN DPB_VEH_RTL_EXTRT VEHRTLEXTRT ON PGMVEH.CDE_VEH_TYP = VEHRTLEXTRT.CDE_VEH_TYP"+
												 " WHERE CDE_DPB_STS = 'A' OR (CDE_DPB_STS = 'I' AND DTE_INACT > ?)";

	//Bonus query end
	public static final String RETREIVE_PROCESS_DEFINITION_DETAIL = " Select ID_DPB_PROC,ID_DAY,ID_DPB_APP_ENT,DTE_DPB_ACTL_PROC,CDE_DPB_PROC_TYP,DTE_PROC_OVRD " +
																	" TME_PROC_OVRD,CDE_DPB_OVRD_PROC_INIT_TYP,TXT_RSN_PROC_UPD," +
																	" CDE_COMSN_BNS_SCHD, CDE_COMSN_BNS_TRGR, CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_TRGR, CDE_FXD_BNS_SCHD,CDE_FXD_BNS_TRGR,CDE_FXD_PMT_SCHD,CDE_FXD_PMT_TRGR," +
																	" CDE_VAR_BNS_SCHD,CDE_VAR_BNS_TRGR,CDE_VAR_PMT_SCHD,CDE_VAR_PMT_TRGR,IND_SPL_DPB_PGM " +
																	" from DPB_PROC join DPB_PGM pgm on ID_DPB_PGM = ID_DPB_APP_ENT where ID_DPB_PROC = ?";
	//CB
	public static final String RETRIEVE_PROCESS_PROGRAM_DETAILS_FOR_PROCESS_ID = " WITH PROC  " +
																				" ( " +
																				" 	ID_DPB_PROC, ID_DPB_APP_ENT, CDE_DPB_PROC_TYP, DTE_DPB_ACTL_PROC,DTE_PROC_OVRD,TME_PROC_OVRD, " + 
																				" 	CDE_DPB_OVRD_TRGR,TXT_RSN_PROC_UPD) AS  " +
																				" (  " +
																				" 	SELECT ID_DPB_PROC, ID_DPB_PGM as ID_DPB_APP_ENT,CDE_DPB_PROC_TYP,  DTE_DPB_ACTL_PROC,DTE_PROC_OVRD, " +
																				" 	TME_PROC_OVRD, CDE_DPB_OVRD_PROC_INIT_TYP as CDE_DPB_OVRD_TRGR,TXT_RSN_PROC_UPD FROM DPB_PROC PROC " +
																				" 	WHERE PROC.ID_DPB_PROC = ?),  " +
																				" 	PROG (ID_DPB_PGM, NAM_DPB_PGM, DES_DPB_PGM, DTE_DPB_PGM_START, DTE_DPB_PGM_END, IND_DPB_RBT_PMT, " + 
																				" 	IND_DPB_COMSN_PMT, AMT_DPB_COMSN_PMT, PCT_FXD_PMT,   " +
																				" 	CDE_COMSN_BNS_PROC_SCHD , CDE_COMSN_PROC_INIT_TYP, CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_PROC_INIT_TYP, " + 
																				" 	CDE_FXD_BNS_SCHD, CDE_FXD_BNS_PROC_INIT_TYP, CDE_FXD_PMT_SCHD, CDE_FXD_PMT_PROC_INIT_TYP,  " +
																				" 	IND_DPB_FXD_PMT, IND_MAX_VAR_PMT, PCT_VAR_PMT, " +
																				" 	CDE_VAR_BNS_SCHD, CDE_VAR_BNS_PROC_INIT_TYP, CDE_VAR_PMT_SCHD, CDE_VAR_PMT_PROC_INIT_TYP, " + 
																				" 	CDE_DPB_PGM_TYP, ID_KPI, CDE_DPB_STS, DTE_INACT,CDE_VEH_TYP " +
																				" ) AS ( " +
																				" 	SELECT PGM.ID_DPB_PGM, NAM_DPB_PGM, DES_DPB_PGM, DTE_DPB_PGM_START,DTE_DPB_PGM_END, " + 
																				" 	IND_DPB_RBT_PMT, IND_DPB_COMSN_PMT, AMT_DPB_COMSN_PMT,  PCT_FXD_PMT,  " +
																				" 	CDE_COMSN_BNS_PROC_SCHD , CDE_COMSN_PROC_INIT_TYP, CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_PROC_INIT_TYP, " + 
																				" 	CDE_FXD_BNS_SCHD, CDE_FXD_BNS_PROC_INIT_TYP, CDE_FXD_PMT_SCHD, CDE_FXD_PMT_PROC_INIT_TYP,  " +
																				" 	IND_DPB_FXD_PMT, IND_MAX_VAR_PMT, PCT_VAR_PMT, " +
																				" 	CDE_VAR_BNS_SCHD, CDE_VAR_BNS_PROC_INIT_TYP, CDE_VAR_PMT_SCHD, CDE_VAR_PMT_PROC_INIT_TYP, " + 
																				" 	CDE_DPB_PGM_TYP, ID_KPI, PGM.CDE_DPB_STS, DTE_INACT, CDE_VEH_TYP FROM DPB_PGM PGM  " +
																				" 	JOIN DPB_PGM_VEH_REL PGMVEH ON PGM.ID_DPB_PGM = PGMVEH.ID_DPB_PGM  " +
																				" ) " +
																				" SELECT P.ID_DPB_PROC, PROG.ID_DPB_PGM, P.CDE_DPB_PROC_TYP, PROG.NAM_DPB_PGM , " +
																				" 	PROG.DES_DPB_PGM PGM_NAME, PROG.DTE_DPB_PGM_START, PROG.DTE_DPB_PGM_END, PROG.IND_DPB_RBT_PMT, " +
																				" 	PROG.IND_DPB_COMSN_PMT, PROG.AMT_DPB_COMSN_PMT, PROG.PCT_FXD_PMT, " +
																				" 	PROG.CDE_COMSN_BNS_PROC_SCHD, PROG.CDE_COMSN_PROC_INIT_TYP, PROG.CDE_COMSN_PMT_SCHD, PROG.CDE_COMSN_PMT_PROC_INIT_TYP, " +
																				" 	PROG.CDE_FXD_BNS_SCHD, PROG.CDE_FXD_BNS_PROC_INIT_TYP, PROG.CDE_FXD_PMT_SCHD, PROG.CDE_FXD_PMT_PROC_INIT_TYP, " +
																				" 	PROG.IND_DPB_FXD_PMT, PROG.IND_MAX_VAR_PMT, PROG.PCT_VAR_PMT, " +
																				" 	PROG.CDE_VAR_BNS_SCHD, PROG.CDE_VAR_BNS_PROC_INIT_TYP, PROG.CDE_VAR_PMT_SCHD, PROG.CDE_VAR_PMT_PROC_INIT_TYP, " +
																				" 	PROG.CDE_DPB_PGM_TYP, PROG.ID_KPI, PROG.CDE_DPB_STS, PROG.DTE_INACT, " +
																				" 	PROG.CDE_VEH_TYP,P.DTE_PROC_OVRD,P.DTE_DPB_ACTL_PROC FROM PROC P, PROG " +  
																				" 	WHERE P.ID_DPB_APP_ENT = PROG.ID_DPB_PGM AND " +
																				" 	P.CDE_DPB_PROC_TYP IN (?) " ;							
										
	public static final String RETRIEVE_LDRSHIP_PROCESS_PROGRAM_DETAILS_FOR_PROCESS_ID = " with DPBPROC(ID_PGM) AS (SELECT ID_LDRSP_BNS_PGM FROM DPB_PROC WHERE ID_DPB_PROC = ?), "+
																						 " LDRSP_PGM (ID_LDRSP_BNS_PGM,NAM_LDRSP_BNS,DTE_LDRSP_BNS_RTL_STA,DTE_LDRSP_BNS_RTL_END,DTE_LDRSP_BNS_ACRL_STA,DTE_LDRSP_BNS_ACRL_END, "+
																						 " AMT_LDRSP_BNS_BUS_RSRV,PCT_LDRSP_BNS_BUS_RSRV,AMT_LDRSP_BNS_PER_UNIT_CALC,AMT_LDRSP_BNS_PER_UNIT_EDIT, "+
																						 " CDE_DPB_STS,DTE_LDRSP_BNS_PROC,AMT_DPB_UNUSED,CNT_LDRSP_BNS_VEH,AMT_DPB_LDRSP_BNS) as "+
																						 " (SELECT  ID_LDRSP_BNS_PGM,NAM_LDRSP_BNS,DTE_LDRSP_BNS_RTL_STA,DTE_LDRSP_BNS_RTL_END,DTE_LDRSP_BNS_ACRL_STA,DTE_LDRSP_BNS_ACRL_END, "+
																						 " AMT_LDRSP_BNS_BUS_RSRV,PCT_LDRSP_BNS_BUS_RSRV,AMT_LDRSP_BNS_PER_UNIT_CALC,AMT_LDRSP_BNS_PER_UNIT_EDIT, "+
																						 " CDE_DPB_STS,DTE_LDRSP_BNS_PROC,AMT_DPB_UNUSED,CNT_LDRSP_BNS_VEH,AMT_DPB_LDRSP_BNS "+
																						 " from LDRSP_BNS_PGM PGM,DPBPROC PROC WHERE PGM.ID_LDRSP_BNS_PGM = PROC.ID_PGM) "+	        
																						 " SELECT ID_LDRSP_BNS_PGM,NAM_LDRSP_BNS,DTE_LDRSP_BNS_RTL_STA,DTE_LDRSP_BNS_RTL_END,DTE_LDRSP_BNS_ACRL_STA,DTE_LDRSP_BNS_ACRL_END,DTE_LDRSP_BNS_PROC FROM LDRSP_PGM "; 
	//CB
	public static final String RETRIEVE_ASSIGN_PGM_SPECIAL_CND = " SELECT CND.ID_DPB_CND, CND.NAM_DPB_CND, CND.DES_DPB_CND," +
															    " CND.CDE_DPB_CND, CND.NAM_DPB_VAR, CND.TXT_DPB_CHK_VAL,"+	
															    " CND.CDE_DPB_CND_TYP, CND.TXT_DPB_REG_EX, CND.CDE_DPB_STS" +
															    " FROM DPB_CND CND" +
															    " JOIN DPB_PGM_CND_REL CNDREL ON CNDREL.ID_DPB_CND = CND.ID_DPB_CND AND CNDREL.ID_DPB_PGM = ?";
	public static final String VISTA_RETAIL_VEHICLE_BASED_ON_VEHICLE_TYPE =    " SELECT NUM_PO, NUM_VIN, DTE_RTL, TME_RTL, CDE_VEH_STS, ID_DLR, IND_USED_CAR," +
																			   " ID_EMP_PUR_CTRL,DTE_MDL_YR, TXT_MODL, " +
																			   " CDE_RGN, NAM_RTL_CUS_LST, NAM_RTL_CUS_FIR, NAM_RTL_CUS_MID, DTE_TRANS," +
																			   " TME_TRANS, CDE_USE, AMT_MSRP_BASE, AMT_MSRP_OPTS, AMT_MSRP_TOT_ACSRY, AMT_DLR_RBT," +
																			   " IND_FLT, CDE_WHSLE_INIT_TYP, CDE_VEH_TYP, CDE_NATL_TYPE,CDE_VEH_TYP,"+
																			   " DTS_LAST_UPDT, DTS_CREA, ID_CREA_USER, ID_LAST_UPDT_USER,CDE_SLE_TYP, DTE_DEMO_SRV, CDE_CAR_STS, IND_USED_CAR2 "+
																			   " FROM DPB_VEH_RTL_EXTRT WHERE " +
																			   " date(DTE_RTL) between ? and ?  and CDE_VEH_TYP = ?"; 
       public final static String RETRIVE_DIMENION_INFO_BASED_ON_DAY_ID = " SELECT ID_DAY,DTE_CAL,DES_DAY,DTE_RETL_MTH_BEG,DTE_RETL_MTH_END," +
																		   " NUM_RETL_MTH, NAM_RETL_MTH,NUM_DY_RETL_MTH,DTE_RETL_QTR_BEG,DTE_RETL_QTR_END,"+
																		   " NUM_RETL_QTR,DTE_RETL_YR_BEG,DTE_RETL_YR_END,NUM_RETL_YR" +
																		   " FROM DPB_RTL_CAL_DY_DIM WHERE ID_DAY = ? ";
       public static final String RETRIEVE_LDRSP_PGM_DTLS = " with VEH_FRAN_MAP (VEH_TYP, FRAN_TYP) as ( values(cast('P' as char(1)), cast('PC' as char(2))) union  values(cast('P' as char(1)), " +
       		"    cast('MB' as char(2))) union values(cast('P' as char(1)), cast('LT' as char(2))) union  values(cast('S' as char(1)), " +
       		"    cast('SM' as char(2))) union values(cast('V' as char(1)), cast('SP' as char(2))) union  values(cast('F' as char(1)), " +
       		"    cast('SP' as char(2)))) , " +
       		"LDRSP_CRTRIA(PROC_ID,PROC_TYPE) as (values (cast(? as integer),cast(? as char(2)))), " +
       		"    ACTL_DATE(DTE_DPB_ACTL_PROC) as (  " +
       		"    SELECT DTE_DPB_ACTL_PROC " +
       		"    FROM DPB_PROC,LDRSP_CRTRIA crc " +
       		"    WHERE ID_DPB_PROC = crc.PROC_ID AND CDE_DPB_PROC_TYP=crc.PROC_TYPE), " +
       		"LDRSP_PGM (ID_LDRSP_BNS_PGM,PROC_ID,NAM_LDRSP_BNS,DTE_LDRSP_BNS_RTL_STA,DTE_LDRSP_BNS_RTL_END, DTE_LDRSP_BNS_ACRL_STA,DTE_LDRSP_BNS_ACRL_END, " +
       		"    AMT_LDRSP_BNS_BUS_RSRV,PCT_LDRSP_BNS_BUS_RSRV,AMT_LDRSP_BNS_PER_UNIT_CALC,AMT_LDRSP_BNS_PER_UNIT_EDIT, " +
       		"    CDE_DPB_STS,DTE_LDRSP_BNS_PROC,AMT_DPB_UNUSED,CNT_LDRSP_BNS_VEH,AMT_DPB_LDRSP_BNS) as ( " +
       		"    SELECT  ID_LDRSP_BNS_PGM,crc.PROC_ID,NAM_LDRSP_BNS,DTE_LDRSP_BNS_RTL_STA,DTE_LDRSP_BNS_RTL_END, DTE_LDRSP_BNS_ACRL_STA,DTE_LDRSP_BNS_ACRL_END, " +
       		"    AMT_LDRSP_BNS_BUS_RSRV,PCT_LDRSP_BNS_BUS_RSRV,AMT_LDRSP_BNS_PER_UNIT_CALC,AMT_LDRSP_BNS_PER_UNIT_EDIT, " +
       		"    CDE_DPB_STS,DTE_LDRSP_BNS_PROC,AMT_DPB_UNUSED,CNT_LDRSP_BNS_VEH,AMT_DPB_LDRSP_BNS " +
       		"    FROM LDRSP_BNS_PGM pgm,LDRSP_CRTRIA crc " +
       		"    where pgm.ID_LDRSP_BNS_PGM =(SELECT ID_LDRSP_BNS_PGM FROM DPB_PROC  WHERE ID_DPB_PROC = crc.PROC_ID " +
       		"    AND CDE_DPB_PROC_TYP=crc.PROC_TYPE)), " +
       		"LDRSP_VEH_MAP(ID_LDRSP_BNS_PGM,PROC_ID,VEH_TYPE,AMT_DPB_UNUSED,AMT_LDRSP_BNS_PER_UNIT_CALC, " +
       		"    IND_LDRSP_BNS_PGM) AS (  " +
       		"    SELECT PGM.ID_LDRSP_BNS_PGM,PGM.PROC_ID ,MAP.CDE_VEH_TYP ,PGM.AMT_DPB_UNUSED,PGM.AMT_LDRSP_BNS_PER_UNIT_CALC, " +
       		"    MAP.ID_LDRSP_BNS_PGM " +
       		"    FROM DPB_PGM_VEH_REL MAP ,LDRSP_PGM PGM " +
       		"    WHERE MAP.ID_LDRSP_BNS_PGM = PGM.ID_LDRSP_BNS_PGM ), " +
       		"LDRSP_ACCURAL (ID_LDRSP_BNS_PGM,PROC_ID,NUM_PO, ACRL_POST_DATE, ACRl_AMOUNT, VEH_TYP,AMT_DPB_UNUSED, " +
       		"    AMT_LDRSP_BNS_PER_UNIT_CALC,IND_LDRSP_BNS_PGM) as ( " +
       		"    SELECT VEHMAP.ID_LDRSP_BNS_PGM,VEHMAP.PROC_ID,acrl.NUM_PO, " +
       		"    acrl.DTE_LDRSP_BNS_ACRL_POST, acrl.AMT_DPB_LDRSP_BNS, acrl.CDE_VEH_TYP,VEHMAP.AMT_DPB_UNUSED " +
       		"    ,VEHMAP.AMT_LDRSP_BNS_PER_UNIT_CALC,VEHMAP.IND_LDRSP_BNS_PGM " +
       		"    from LDRSP_BNS_ACRL acrl, LDRSP_PGM dates,LDRSP_VEH_MAP VEHMAP " +
       		"    where acrl.DTE_LDRSP_BNS_ACRL_POST between dates.DTE_LDRSP_BNS_ACRL_STA and dates.DTE_LDRSP_BNS_ACRL_END " +
       		"    and acrl.CDE_VEH_TYP in (VEHMAP.VEH_TYPE)), " +
       		"VEH_DATA (NUM_PO,ID_UNBLK_VEH) as ( " +
       		"    select vista.NUM_PO,max(ID_DPB_UNBLK_VEH) " +
       		"    from DPB_UNBLK_VEH vista, LDRSP_ACCURAL acrl ,LDRSP_PGM dates " +
       		"    where  vista.DTE_RTL  between dates.DTE_LDRSP_BNS_RTL_STA and dates.DTE_LDRSP_BNS_RTL_END " +
       		"    and vista.CDE_VEH_TYP in (acrl.VEH_TYP) group by vista.NUM_PO ) , " +
       		"LDRSP_VISTA (NUM_PO,ID_DPB_UNBLK_VEH,ID_LDRSP_BNS_PGM,PROC_ID, ID_DLR, VEH_TYP, RTL_DTE, ACRL_POST_DATE, " +
       		"   AMT_DPB_UNUSED,AMT_LDRSP_BNS_PER_UNIT_CALC,IND_LDRSP_BNS_PGM) as ( " +
       		"    select  vista.NUM_PO,ID_DPB_UNBLK_VEH,acrl.ID_LDRSP_BNS_PGM,acrl.PROC_ID, vista.ID_DLR, acrl.VEH_TYP, vista.DTE_RTL, acrl.ACRL_POST_DATE, " +
       		"    acrl.AMT_DPB_UNUSED,acrl.AMT_LDRSP_BNS_PER_UNIT_CALC,acrl.IND_LDRSP_BNS_PGM " +
       		"    from VEH_DATA vd ,DPB_UNBLK_VEH vista,LDRSP_ACCURAL acrl  " +
       		"    where vista.ID_DPB_UNBLK_VEH = vd.ID_UNBLK_VEH and vista.CDE_VEH_DDR_STS != 'I2' and " +
       		"    vista.CDE_VEH_TYP = acrl.VEH_TYP and acrl.NUM_PO = vista.NUM_PO), " +
       		"LDRSP_RTL_DIM (ID_LDRSP_BNS_PGM,PROC_ID,NUM_PO,ID_DPB_UNBLK_VEH, ID_DLR, VEH_TYP, RTL_DTE, ACRL_POST_DATE, DLR_QTR, DLR_YEAR,AMT_DPB_UNUSED, " +
       		"    DTE_CAL,AMT_LDRSP_BNS_PER_UNIT_CALC,IND_LDRSP_BNS_PGM) as ( " +
       		"    select vista.ID_LDRSP_BNS_PGM,vista.PROC_ID,vista.NUM_PO,vista.ID_DPB_UNBLK_VEH, vista.ID_DLR, " +
       		"    vista.VEH_TYP, vista.RTL_DTE, vista.ACRL_POST_DATE, dim.NUM_RETL_QTR, dim.NUM_RETL_YR,vista.AMT_DPB_UNUSED,dim.DTE_CAL, " +
       		"    vista.AMT_LDRSP_BNS_PER_UNIT_CALC,vista.IND_LDRSP_BNS_PGM " +
       		"    from LDRSP_VISTA vista, DPB_DAY dim where dim.DTE_CAL = vista.RTL_DTE), " +
       		"B_DLR_ELG (ID_LDRSP_BNS_PGM,PROC_ID,NUM_PO,ID_DPB_UNBLK_VEH, ID_DLR, VEH_TYP, RTL_DTE, ACRL_POST_DATE, DLR_QTR, DLR_YEAR,AMT_DPB_UNUSED, " +
       		"    DTE_CAL,AMT_LDRSP_BNS_PER_UNIT_CALC,IND_LDRSP_BNS_PGM) as ( " +
       		"    select DISTINCT dim.ID_LDRSP_BNS_PGM,dim.PROC_ID,dim.NUM_PO,dim.ID_DPB_UNBLK_VEH, dim.ID_DLR, dim.VEH_TYP, " +
       		"    dim.RTL_DTE, dim.ACRL_POST_DATE, dim.DLR_QTR, dim.DLR_YEAR,dim.AMT_DPB_UNUSED,dim.DTE_CAL,dim.AMT_LDRSP_BNS_PER_UNIT_CALC, " +
       		"    dim.IND_LDRSP_BNS_PGM " +
       		"    from LDRSP_RTL_DIM dim, DPB_LDR_QTR_FDRT elg, VEH_FRAN_MAP vehRfan " +
       		"    where elg.IND_DPB_LDR = 'Y' and elg.ID_DLR = dim.ID_DLR " +
       		"    and elg.DTE_QTR = dim.DLR_QTR and elg.DTE_YR = dim.DLR_YEAR and vehRfan.VEH_TYP in (dim.VEH_TYP) and elg.CDE_DLR_FRAN_TYP IN (vehRfan.FRAN_TYP) ), " +
       		"CRNT_UNUSEDAMT(ACRl_AMOUNT) as ( " +
       		"    select sum(ACRl_AMOUNT) from LDRSP_ACCURAL) " +
       		"SELECT elig.PROC_ID,actl.DTE_DPB_ACTL_PROC,elig.NUM_PO,elig.ID_DPB_UNBLK_VEH, elig.ID_DLR,elig.DTE_CAL,elig.ID_LDRSP_BNS_PGM,elig.IND_LDRSP_BNS_PGM,elig.AMT_LDRSP_BNS_PER_UNIT_CALC, " +
       		"elig.VEH_TYP, elig.RTL_DTE, elig.ACRL_POST_DATE, elig.DLR_QTR, elig.DLR_YEAR,elig.AMT_DPB_UNUSED,crnt.ACRl_AMOUNT " +
       		"FROM B_DLR_ELG elig,CRNT_UNUSEDAMT crnt,ACTL_DATE actl";

       public static String RETRIEVE_PAYMENT_RECORD_FOR_UPDATE = " with RTL_DIM (DTE_CAL) as (   "+
													    		   "  select DTE_CAL from DPB_DAY where date(DTE_CAL) between ? and ?  "+
													    		   "  ),   "+
													    		   " CALC (ID_DPB_UNBLK_VEH, ID_DLR, ID_DPB_PGM, AMT_DPB_BNS_CALC, DTE_CAL,ID_DPB_PROC) as (   "+
													    		   " select cal.ID_DPB_UNBLK_VEH, cal.ID_DLR, cal.ID_DPB_PGM, cal.AMT_DPB_BNS_CALC,  "+
													    		   " dim.DTE_CAL ,proc.ID_DPB_PROC   "+
													    		   " from DPB_CALC cal, RTL_DIM dim,DPB_PROC proc     "+
													    		   " where cal.DTE_CAL in (dim.DTE_CAL)    "+
													    		   " and cal.CDE_DPB_CALC_STS = 'BC' and proc.ID_DPB_PROC = cal.ID_DPB_PROC  "+
													    		   " and proc.CDE_DPB_PROC_TYP = ? and proc.ID_DPB_PGM = ?  "+
													    		   " )  "+
													    		   " select distinct ID_DPB_PROC from CALC ";

       public static String RETRIEVE_LDR_PAYMENT_RECORD_FOR_UPDATE  = 	" with RTL_DIM ( DTE_CAL) as ( "+   
																		" select DTE_CAL from DPB_DAY where date(DTE_CAL) between ? and ? ), "+   
																		" CALC (ID_DPB_UNBLK_VEH, ID_DLR, ID_PGM, AMT_DPB_BNS_CALC, DTE_CAL,ID_DPB_PROC ) as ( "+   
																		" select cal.ID_DPB_UNBLK_VEH, cal.ID_DLR, cal.ID_LDRSP_BNS_PGM, cal.AMT_DPB_BNS_CALC, dim.DTE_CAL ,proc.ID_DPB_PROC "+      
																		" from DPB_CALC cal, RTL_DIM dim ,DPB_PROC proc "+
																		" where "+    
																		" cal.CDE_DPB_CALC_STS = 'LC' "+  
																		" and proc.ID_DPB_PROC = cal.ID_DPB_PROC  and proc.ID_LDRSP_BNS_PGM = ? ) "+
																		" select distinct ID_DPB_PROC  from CALC";
       public static final String RETRIEVE_PROGRAM_DETAILS_FOR_PROCESS_ID = " WITH PROC (ID_DPB_PROC, ID_DPB_APP_ENT, CDE_DPB_PROC_TYP, DTE_DPB_ACTL_PROC,DTE_PROC_OVRD,TME_PROC_OVRD," +
															    		   " CDE_DPB_OVRD_TRGR,TXT_RSN_PROC_UPD) AS ( SELECT ID_DPB_PROC, ID_DPB_APP_ENT,CDE_DPB_PROC_TYP," +
															    		   " DTE_DPB_ACTL_PROC,DTE_PROC_OVRD,TME_PROC_OVRD, CDE_DPB_OVRD_PROC_INIT_TYP,TXT_RSN_PROC_UPD FROM DPB_PROC PROC, "+
															    		   " DPB_RTL_CAL_DY_DIM CAL WHERE PROC.ID_DPB_PROC = ? AND  PROC.ID_DAY = CAL.ID_DAY ), "+
															
															    		   " PROG (ID_DPB_PGM, NAM_DPB_PGM, DES_DPB_PGM, DTE_DPB_PGM_START, DTE_DPB_PGM_END, IND_DPB_RBT_PMT, "+
															    		   " IND_DPB_COMSN_PMT, AMT_DPB_COMSN_PMT, PCT_DPB_COMSN_PMT, PCT_FXD_PMT,  "+
															    		   " CDE_COMSN_BNS_SCHD, CDE_COMSN_BNS_TRGR, CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_TRGR, CDE_FXD_BNS_SCHD, CDE_FXD_BNS_TRGR, CDE_FXD_PMT_SCHD, CDE_FXD_PMT_TRGR, "+
															    		   " IND_DPB_FXD_PMT, IND_MAX_VAR_PMT, PCT_VAR_PMT, CDE_VAR_BNS_SCHD, CDE_VAR_BNS_TRGR, "+
															    		   " CDE_VAR_PMT_SCHD, CDE_VAR_PMT_TRGR, IND_SPL_DPB_PGM, ID_KPI, CDE_DPB_STS, DTE_INACT) "+
															    		   " AS (SELECT PGM.ID_DPB_PGM, NAM_DPB_PGM, DES_DPB_PGM, DTE_DPB_PGM_START,DTE_DPB_PGM_END, IND_DPB_RBT_PMT, IND_DPB_COMSN_PMT, AMT_DPB_COMSN_PMT, PCT_DPB_COMSN_PMT, "+ 
															    		   " PCT_FXD_PMT, CDE_COMSN_BNS_SCHD, CDE_COMSN_BNS_TRGR, CDE_COMSN_PMT_SCHD, CDE_COMSN_PMT_TRGR, CDE_FXD_BNS_SCHD, CDE_FXD_BNS_TRGR, CDE_FXD_PMT_SCHD, CDE_FXD_PMT_TRGR, "+
															    		   " IND_DPB_FXD_PMT, IND_MAX_VAR_PMT, PCT_VAR_PMT, CDE_VAR_BNS_SCHD, CDE_VAR_BNS_TRGR, CDE_VAR_PMT_SCHD, "+ 
															    		   " CDE_VAR_PMT_TRGR, IND_SPL_DPB_PGM, ID_KPI, CDE_DPB_STS, DTE_INACT "+
															    		   " FROM DPB_PGM PGM "+
															    		   "  )  "+
															    		   " SELECT P.ID_DPB_PROC, PROG.ID_DPB_PGM, P.CDE_DPB_PROC_TYP, PROG.NAM_DPB_PGM , "+ 
															    		   " PROG.DES_DPB_PGM PGM_NAME, PROG.DTE_DPB_PGM_START, PROG.DTE_DPB_PGM_END, PROG.IND_DPB_RBT_PMT, "+ 
															    		   " PROG.IND_DPB_COMSN_PMT, PROG.AMT_DPB_COMSN_PMT, PROG.PCT_DPB_COMSN_PMT, PROG.PCT_FXD_PMT,  "+
															    		   " PROG.CDE_COMSN_BNS_SCHD, PROG.CDE_COMSN_BNS_TRGR, PROG.CDE_COMSN_PMT_SCHD, PROG.CDE_COMSN_PMT_TRGR, PROG.CDE_FXD_BNS_SCHD, PROG.CDE_FXD_BNS_TRGR, PROG.CDE_FXD_PMT_SCHD, PROG.CDE_FXD_PMT_TRGR, "+
															    		   " PROG.IND_DPB_FXD_PMT, PROG.IND_MAX_VAR_PMT, PROG.PCT_VAR_PMT, PROG.CDE_VAR_BNS_SCHD, "+
															    		   " PROG.CDE_VAR_BNS_TRGR, PROG.CDE_VAR_PMT_SCHD, PROG.CDE_VAR_PMT_TRGR, PROG.IND_SPL_DPB_PGM, "+ 
															    		   " PROG.ID_KPI, PROG.CDE_DPB_STS, PROG.DTE_INACT, "+
															    		   " P.DTE_PROC_OVRD,P.DTE_DPB_ACTL_PROC FROM PROC P, PROG "+  
															    		   " WHERE P.ID_DPB_APP_ENT = PROG.ID_DPB_PGM" ;//('FB','VB')
   	/*public static final String VISTA_RETAIL_VEHICLE_BASED_ON_VEHICLE_TYPE_LIST = " SELECT NUM_PO, NUM_VIN, DTE_RTL, TME_RTL, CDE_VEH_STS, ID_DLR, IND_USED_CAR," +
																		   " ID_EMP_PUR_CTRL,DTE_MDL_YR, TXT_MODL, " +
																		   " CDE_RGN, NAM_RTL_CUS_LST, NAM_RTL_CUS_FIR, NAM_RTL_CUS_MID, DTE_TRANS," +
																		   " TME_TRANS, CDE_USE, AMT_MSRP_BASE, AMT_MSRP_OPTS, AMT_MSRP_TOT_ACSRY, AMT_DLR_RBT," +
																		   " IND_FLT, CDE_WHSLE_INIT_TYP, CDE_VEH_TYP, CDE_NATL_TYPE,CDE_VEH_TYP, "+
																		   " DTS_LAST_UPDT, DTS_CREA, ID_CREA_USER, ID_LAST_UPDT_USER ,ID_DPB_PGM, "+
																		   " CDE_SLE_TYP, DTE_DEMO_SRV, CDE_CAR_STS, IND_USED_CAR2" +
																		   " FROM DPB_VEH_RTL_EXTRT WHERE " +
																		   " date(DTE_RTL) between ? and ? and CDE_VEH_TYP in (?) order by DTE_RTL,TME_RTL  "; //and CDE_VEH_TYP in (?)
   	*/
   	public static final String VISTA_RETAIL_VEHICLE_BASED_ON_VEHICLE_TYPE_LIST = "	SELECT BLK.ID_DPB_UNBLK_VEH,BLK.NUM_PO, BLK.NUM_VIN, BLK.DTE_RTL, BLK.TME_RTL, BLK.CDE_VEH_STS, BLK.ID_DLR, BLK.IND_USED_VEH, "+
   																				 	" BLK.ID_EMP_PUR_CTRL,BLK.DTE_MODL_YR, BLK.DES_MODL,  "+
																		   			" BLK.CDE_RGN, BLK.NAM_RTL_CUS_LST, BLK.NAM_RTL_CUS_FIR, BLK.NAM_RTL_CUS_MID, BLK.DTE_TRANS, "+
																		   			" BLK.TME_TRANS, BLK.CDE_USE_VEH, BLK.AMT_MSRP_BASE, BLK.AMT_MSRP, BLK.AMT_MSRP_TOT_ACSRY, BLK.AMT_DLR_RBT, "+ 
																		   			" BLK.IND_FLT, BLK.CDE_WHSLE_INIT_TYP, BLK.CDE_VEH_TYP, BLK.CDE_NATL_TYPE,BLK.CDE_VEH_TYP,  "+
																		   			" BLK.DTS_LAST_UPDT, BLK.DTS_CREA, BLK.ID_CREA_USER, BLK.ID_LAST_UPDT_USER ,BLK.ID_DPB_PGM,   "+
																		   			" BLK.CDE_VEH_DDR_USE, BLK.DTE_VEH_DEMO_SRV, BLK.CDE_VEH_DDR_STS, BLK.IND_USED_VEH_DDRS, BLK.IND_AMG  "+
																		   			" FROM DPB_UNBLK_VEH BLK WHERE   "+
																		   			" date(BLK.DTE_RTL) between ? and ? "+
																		   			" and BLK.CDE_VEH_TYP in (?)   ";
   	
	public static final String UDPATE_BP_AND_FP_PAYMENT_STATUS = "	update DPB_CALC set CDE_DPB_CALC_STS = ?,ID_LAST_UPDT_USER = ? ,DTS_LAST_UPDT = current timestamp  " +
																 " where ID_DPB_PROC = ?";
	
	public static final String RETRIEVE_ACTUAL_DATE_ON_PROC_ID = "  SELECT DTE_DPB_ACTL_PROC FROM DPB_PROC PROC  where PROC.ID_DPB_PROC = ?"; 
	
	public static final String UDPATE_LDR_PAYMENT_STATUS = " update DPB_CALC set CDE_DPB_CALC_STS = ?,ID_LAST_UPDT_USER = ? ,DTS_LAST_UPDT = current timestamp  " +
														   " where ID_DPB_PROC = ?";
	public static final String SPECIAL_COND_SELECT_QUERY = " SELECT CND.ID_DPB_CND, CND.NAM_DPB_CND, CND.DES_DPB_CND," +
														    " CND.CDE_DPB_CND, CND.NAM_DPB_VAR, CND.TXT_DPB_CHK_VAL,"+	
														    " CND.CDE_DPB_CND_TYP, CND.TXT_DPB_REG_EX, CND.CDE_DPB_STS" +
														    " FROM DPB_CND CND" +
														    " JOIN DPB_PGM_CND_REL CNDREL ON CNDREL.ID_DPB_CND = CND.ID_DPB_CND AND CNDREL.ID_DPB_PGM = ?";
	
	public static final String SPECIAL_PROG_SELECT_QUERY = "select pgm.ID_DPB_PGM,pgm.NAM_DPB_PGM,veh.CDE_VEH_TYP from DPB_PGM pgm left outer join DPB_PGM_VEH_REL veh on pgm.ID_DPB_PGM = veh.ID_DPB_PGM where pgm.CDE_DPB_STS = 'A' and pgm.CDE_DPB_PGM_TYP = 'S' order by pgm.ID_DPB_PGM asc";
	
	/*public static final String GET_BONUS_RECORD_FOR_ADJUESTMENT = "  with PGM (ID_DPB_PGM, DTE_DPB_PGM_START, DTE_DPB_PGM_END,ID_KPI) as  ( "+  
																	" select ID_DPB_PGM,DTE_DPB_PGM_START, DTE_DPB_PGM_END,ID_KPI from DPB_PGM    "+
																	" where CDE_DPB_PGM_TYP = 'G' and ID_KPI in (?)  and CDE_DPB_STS = 'A' ) "+
																	" ,  "+
																	" RTL_DIM (DTE_CAL) as "+
																	" ( "+   
																	" select DIM.DTE_CAL from DPB_DAY DIM "+  
																	" where  DIM.DTE_CAL between ? AND ?  "+
																	" ),  "+
																	" PROCESS (ID_DPB_PGM, DTE_DPB_PGM_START, DTE_DPB_PGM_END,ID_KPI ,DTE_DPB_ACTL_PROC  ) "+
																	" as( "+
																	" select  PROC.ID_DPB_PGM, DTE_DPB_PGM_START, DTE_DPB_PGM_END,ID_KPI ,PROC.DTE_DPB_ACTL_PROC "+ 
																	" from  DPB_PROC PROC    "+
																	" join  PGM P on P.ID_DPB_PGM = PROC.ID_DPB_PGM AND CDE_DPB_PROC_TYP = 'VB' "+  
																	" AND PROC.DTE_CAL in (SELECT RD.DTE_CAL FROM  RTL_DIM RD))   "+
																	" SELECT CALC.ID_DPB_CALC,CALC.ID_DPB_PROC,CALC.ID_DPB_UNBLK_VEH,CALC.ID_DLR,CALC.DTE_CAL, "+  
																	" CALC.ID_DPB_PGM,ID_KPI,  CALC.AMT_DPB_BNS_CALC,CALC.AMT_DPB_MAX_BNS_ELG,CALC.CDE_DPB_CALC_STS, "+ 
																	" V.AMT_MSRP_BASE + V.AMT_MSRP_TOT_ACSRY Total ,CALC.IND_DPB_ADJ      "+
																	" FROM DPB_CALC CALC   "+
																	" JOIN PROCESS  PR ON PR.DTE_DPB_ACTL_PROC = CALC.DTE_CAL AND "+  
																	" PR.ID_DPB_PGM = CALC.ID_DPB_PGM  "+
																	" JOIN DPB_UNBLK_VEH  V ON CALC.ID_DLR  = V.ID_DLR "+   
																	" AND CALC.ID_DPB_UNBLK_VEH  = V.ID_DPB_UNBLK_VEH "+
																	" where CALC.ID_DLR = ?" ; */
	// Latest tested query start
/*	public static final String GET_BONUS_RECORD_FOR_ADJUESTMENT = " with PGM (ID_DPB_PGM, DTE_DPB_PGM_START, DTE_DPB_PGM_END,ID_KPI) as  (  "+  
																	" select ID_DPB_PGM,DTE_DPB_PGM_START, DTE_DPB_PGM_END,ID_KPI from DPB_PGM    "+
																	" where CDE_DPB_PGM_TYP = 'G' and ID_KPI in (?)  and CDE_DPB_STS = 'A' ) "+
																	" ,  "+
																	" RTL_DIM (DTE_CAL) as "+
																	" (    "+
																	" select DIM.DTE_CAL from DPB_DAY DIM "+  
																	" where  DIM.DTE_CAL between ? AND  ?  "+
																	" ),  "+
																	" PROCESS (ID_DPB_PGM, DTE_DPB_PGM_START, DTE_DPB_PGM_END,ID_KPI ,DTE_DPB_ACTL_PROC,ID_DPB_PROC  ) "+
																	" as( "+
																	" select  PROC.ID_DPB_PGM, DTE_DPB_PGM_START, DTE_DPB_PGM_END,ID_KPI ,PROC.DTE_DPB_ACTL_PROC ,ID_DPB_PROC "+
																	" from  DPB_PROC PROC    "+
																	" join  PGM P on P.ID_DPB_PGM = PROC.ID_DPB_PGM AND CDE_DPB_PROC_TYP = 'VB' "+  
																	" AND PROC.DTE_CAL in (SELECT RD.DTE_CAL FROM  RTL_DIM RD))  , "+
																	" CAL_SUM(ID_DPB_PROC,ID_DLR,ID_DPB_UNBLK_VEH,ID_DPB_PGM,ID_KPI,AMT_DPB_BNS_CALC,AMT_DPB_MAX_BNS_ELG "+																	
																	" ) as ( "+
																	" SELECT CALC.ID_DPB_PROC,CALC.ID_DLR,CALC.ID_DPB_UNBLK_VEH,CALC.ID_DPB_PGM,ID_KPI, "+
																	" sum(CALC.AMT_DPB_BNS_CALC) as AMT_DPB_BNS_CALC "+
																	" ,CALC.AMT_DPB_MAX_BNS_ELG "+
																	" FROM DPB_CALC CALC   "+
																	" JOIN PROCESS  PR ON PR.ID_DPB_PROC = CALC.ID_DPB_PROC AND  PR.ID_DPB_PGM = CALC.ID_DPB_PGM "+  
																	" group by  CALC.ID_DLR,CALC.ID_DPB_PGM,CALC.AMT_DPB_MAX_BNS_ELG,CALC.ID_DPB_UNBLK_VEH,CALC.ID_DPB_PROC, "+
																	" CALC.ID_DPB_PGM,ID_KPI  "+
																	" having  CALC.ID_DLR = ?)  "+
																	" select CM.*, V.AMT_MSRP_BASE + V.AMT_MSRP_TOT_ACSRY Total "+ 
																	" from CAL_SUM CM JOIN DPB_UNBLK_VEH  V ON CM.ID_DLR  = V.ID_DLR "+   
																	" AND CM.ID_DPB_UNBLK_VEH  = V.ID_DPB_UNBLK_VEH ";*/
	// last tested query end
	// below query is updated for cancelation. we should not considered vehicle which is canceled for adjuestment.
	
	/*public static final String GET_BONUS_RECORD_FOR_ADJUSTMENT = "WITH PGM (ID_DPB_PGM, ID_KPI,ID_DPB_PROC,  "+
																	" ID_DLR,ID_DPB_UNBLK_VEH,AMT_DPB_BNS_CALC,AMT_DPB_MAX_BNS_ELG,NUM_PO,TOTAL,DTE_RTL,TME_RTL,CDE_VEH_DDR_STS) as ( "+
																	" select  pgm.ID_DPB_PGM,ID_KPI, "+
																	" CALC.ID_DPB_PROC,CALC.ID_DLR,CALC.ID_DPB_UNBLK_VEH, "+
																	" sum(CALC.AMT_DPB_BNS_CALC) as AMT_DPB_BNS_CALC,  "+
																	" CALC.AMT_DPB_MAX_BNS_ELG,veh.NUM_PO, "+
																	" (veh.AMT_MSRP_BASE + veh.AMT_MSRP_TOT_ACSRY) as Total,veh.DTE_RTL,veh.TME_RTL,veh.CDE_VEH_DDR_STS "+ 
																	" from DPB_PGM pgm, DPB_PROC proc, DPB_CALC CALC,DPB_UNBLK_VEH veh  "+
																	" where pgm.CDE_DPB_PGM_TYP = 'G' and ID_KPI in (?)  and CDE_DPB_STS = 'A' "+
																	" and pgm.ID_DPB_PGM = calc.ID_DPB_PGM AND proc.CDE_DPB_PROC_TYP = 'VB' "+
																	" AND proc.DTE_DPB_ACTL_PROC between ? AND  ? "+
																	" and proc.ID_DPB_PROC = CALC.ID_DPB_PROC "+  //AND  proc.ID_DPB_PGM = CALC.ID_DPB_PGM "+
																	" and calc.ID_DPB_UNBLK_VEH = veh.ID_DPB_UNBLK_VEH    "+
																	" group by pgm.ID_DPB_PGM,ID_KPI, "+
																	" CALC.ID_DPB_PROC,CALC.ID_DLR,CALC.ID_DPB_UNBLK_VEH, "+
																	" CALC.AMT_DPB_MAX_BNS_ELG,veh.NUM_PO,veh.DTE_RTL,veh.TME_RTL,veh.CDE_VEH_DDR_STS,(veh.AMT_MSRP_BASE + veh.AMT_MSRP_TOT_ACSRY) "+
																	" having calc.ID_DLR = ? "+
																	" ) , "+
																	" MAX_VEH (NUM_PO,ID_DPB_UNBLK_VEH) as ( "+
																	"	select veh.NUM_PO, max(veh.ID_DPB_UNBLK_VEH) "+
																	"	from PGM p, DPB_UNBLK_VEH veh "+
																	"	where veh.NUM_PO = p.NUM_PO "+
																	"	group by veh.NUM_PO "+ 
																	"	) "+
																	" select p.ID_DPB_PROC,p.ID_DLR,p.ID_DPB_UNBLK_VEH,p.ID_DPB_PGM,p.ID_KPI,p.AMT_DPB_BNS_CALC,p.AMT_DPB_MAX_BNS_ELG, "+ 
																	" p.TOTAL,p.DTE_RTL,p.TME_RTL "+
																	" from PGM p "+
																	" join MAX_VEH mveh on p.NUM_PO = mveh.NUM_PO and p.ID_DPB_UNBLK_VEH = mveh.ID_DPB_UNBLK_VEH "+
																	" and p.CDE_VEH_DDR_STS != 'I2' and p.AMT_DPB_BNS_CALC != p.AMT_DPB_MAX_BNS_ELG ";*/

	public static final String GET_BONUS_RECORD_FOR_ADJUSTMENT = "WITH PGM (ID_DPB_PGM, ID_KPI,ID_DPB_PROC,  "+
			" ID_DLR,ID_DPB_UNBLK_VEH,AMT_DPB_BNS_CALC,AMT_DPB_MAX_BNS_ELG,NUM_PO,TOTAL,DTE_RTL,TME_RTL,CDE_VEH_DDR_STS,RN) as ( "+
			" select  pgm.ID_DPB_PGM,ID_KPI, "+
			" CALC.ID_DPB_PROC,CALC.ID_DLR,CALC.ID_DPB_UNBLK_VEH, "+
			" sum(CALC.AMT_DPB_BNS_CALC) as AMT_DPB_BNS_CALC,  "+
			" CALC.AMT_DPB_MAX_BNS_ELG,veh.NUM_PO, "+
			" (veh.AMT_MSRP_BASE + veh.AMT_MSRP_TOT_ACSRY) as Total,veh.DTE_RTL,veh.TME_RTL,veh.CDE_VEH_DDR_STS, "+
			" ROW_NUMBER() OVER( PARTITION BY VEH.NUM_PO ORDER BY VEH.DTE_RTL DESC,VEH.TME_RTL DESC) AS RN"+
			" from DPB_PGM pgm, DPB_PROC proc, DPB_CALC CALC,DPB_UNBLK_VEH veh  "+
			" where pgm.CDE_DPB_PGM_TYP = 'G' and ID_KPI in (?)  and CDE_DPB_STS = 'A' "+
			" and pgm.ID_DPB_PGM = calc.ID_DPB_PGM AND proc.CDE_DPB_PROC_TYP = 'VB' "+
			" AND proc.DTE_DPB_ACTL_PROC between ? AND  ? "+
			" and proc.ID_DPB_PROC = CALC.ID_DPB_PROC "+  //AND  proc.ID_DPB_PGM = CALC.ID_DPB_PGM "+
			" and calc.ID_DPB_UNBLK_VEH = veh.ID_DPB_UNBLK_VEH    "+
			" group by pgm.ID_DPB_PGM,ID_KPI, "+
			" CALC.ID_DPB_PROC,CALC.ID_DLR,CALC.ID_DPB_UNBLK_VEH, "+
			" CALC.AMT_DPB_MAX_BNS_ELG,veh.NUM_PO,veh.DTE_RTL,veh.TME_RTL,veh.CDE_VEH_DDR_STS,(veh.AMT_MSRP_BASE + veh.AMT_MSRP_TOT_ACSRY) "+
			" having calc.ID_DLR = ? "+
			" ) , "+
			" MAX_VEH (NUM_PO,ID_DPB_UNBLK_VEH) as ( "+
			" select T.NUM_PO, T.ID_DPB_UNBLK_VEH" + 
			" from (select veh.NUM_PO," + 
			" veh.ID_DPB_UNBLK_VEH,P.RN" + 
			//" row_number() over(partition by p.NUM_PO order by veh.DTE_RTL desc,veh.TME_RTL desc) as rn" + 
			" from PGM p, DPB_UNBLK_VEH veh" + 
			" where veh.NUM_PO = p.NUM_PO) as T" + 
			" where rn = 1  "+ 
			"	) "+
			" select p.ID_DPB_PROC,p.ID_DLR,p.ID_DPB_UNBLK_VEH,p.ID_DPB_PGM,p.ID_KPI,p.AMT_DPB_BNS_CALC,p.AMT_DPB_MAX_BNS_ELG, "+ 
			" p.TOTAL,p.DTE_RTL,p.TME_RTL "+
			" from PGM p "+
			" join MAX_VEH mveh on p.NUM_PO = mveh.NUM_PO and p.ID_DPB_UNBLK_VEH = mveh.ID_DPB_UNBLK_VEH "+
			" and p.CDE_VEH_DDR_STS != 'I2' and p.AMT_DPB_BNS_CALC != p.AMT_DPB_MAX_BNS_ELG with UR";
	
	
	/*public static final String GET_ORIGINAL_RETAIL_FOR_CANCEL_TRANSACTION = "WITH IMM_PREV_RTL(NUM_PO, DTE_RTL, TME_RTL, ID_DPB_UNBLK_VEH, CDE_VEH_DDR_STS, ID_DLR) AS ( " +
																			" SELECT RTL.NUM_PO, RTL.DTE_RTL , RTL.TME_RTL, RTL.ID_DPB_UNBLK_VEH, MAX(CDE_VEH_DDR_STS)," +
																			" MAX(RTL.ID_DLR) FROM DPB_CALC CALC , DPB_UNBLK_VEH RTL "+
																			" WHERE RTL.NUM_PO=? AND " +
																			"( (RTL.DTE_RTL = ? AND RTL.TME_RTL < ?) OR (DTE_RTL < ?) ) " +
																			" AND RTL.ID_DLR=? "+
																			" GROUP BY  (RTL.NUM_PO, RTL.DTE_RTL , RTL.TME_RTL, RTL.ID_DPB_UNBLK_VEH ) "+ 
																			" ORDER BY RTL.NUM_PO ASC , RTL.DTE_RTL DESC , RTL.TME_RTL DESC "+
																			" FETCH FIRST 1 ROWS ONLY "+
																			" ) "+
																			" SELECT DISTINCT(CALC.ID_DPB_UNBLK_VEH) FROM DPB_CALC CALC, IMM_PREV_RTL PREV "+ 
																			" WHERE CALC.ID_DPB_UNBLK_VEH=PREV.ID_DPB_UNBLK_VEH ";
	*/
	public static final String GET_ORIGINAL_RETAIL_FOR_CANCEL_TRANSACTION = " WITH IMM_PREV_RTL(NUM_PO, DTE_RTL, TME_RTL, ID_DPB_UNBLK_VEH, CDE_VEH_DDR_STS, ID_DLR) AS ( "+  
																			" SELECT RTL.NUM_PO, RTL.DTE_RTL , RTL.TME_RTL, RTL.ID_DPB_UNBLK_VEH, MAX(CDE_VEH_DDR_STS), "+
																			" MAX(RTL.ID_DLR) FROM  DPB_UNBLK_VEH RTL "+
																			" WHERE RTL.NUM_PO= ?  "+
																			" AND ( (RTL.DTE_RTL = ?  AND RTL.TME_RTL < ? ) OR (DTE_RTL < ?) )"+  
																			" AND RTL.ID_DLR= ? "+
																			" GROUP BY  (RTL.NUM_PO, RTL.DTE_RTL , RTL.TME_RTL, RTL.ID_DPB_UNBLK_VEH )"+  
																			" ORDER BY RTL.NUM_PO ASC , RTL.DTE_RTL DESC , RTL.TME_RTL DESC "+
																			" FETCH FIRST 1 ROWS ONLY "+
																			"	) "+
																			" SELECT DISTINCT(PREV.ID_DPB_UNBLK_VEH) FROM IMM_PREV_RTL PREV " ;  

	/*public static final String GET_BONUS_INFO_FOR_CANCEL_TRANSACTION   = " SELECT SUM(AMT_DPB_BNS_CALC) TOT, AMT_DPB_MAX_BNS_ELG "+
															 	  		 " FROM DPB_CALC  WHERE ID_DPB_UNBLK_VEH = ?  and ID_DPB_PGM is ? " +
															 	  		 " GROUP BY NUM_PO, AMT_DPB_MAX_BNS_ELG";

*/
	public static final String GET_BONUS_INFO_FOR_CANCEL_TRANSACTION = " SELECT SUM(AMT_DPB_BNS_CALC) TOT, AMT_DPB_MAX_BNS_ELG "+
																		" FROM DPB_CALC WHERE ID_DPB_UNBLK_VEH = ? and ID_DPB_PGM = ? " +
																		" GROUP BY ID_DPB_UNBLK_VEH, AMT_DPB_MAX_BNS_ELG"; 
	public static final String GET_PROCESS_LOGS_TERMINATION_CANCELATION = " SELECT TRIM(CDE_DPB_PROC_STS)  FROM DPB_PROC_LOG "+
																			" WHERE ID_DPB_PROC IN (?)  AND "+ 
																			" DATE(DTS_LAST_UPDT) = ? "+
																			" AND TRIM(CDE_DPB_PROC_STS) = 'S'";
	
	/*public static final String KPI_PERCENTAGE_CHANGE_QTR_OR_MONTHLY_BY_LAST_PROCESSED_KPI_FILE_ID = " with ADJ_KPI(ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI ) as   "+
																									" (  "+
																									" SELECT KPI_FIL_EXTRT.ID_DLR, KPI_FIL_EXTRT.DTE_FSCL_YR , "+ 
																									" KPI_FIL_EXTRT.DTE_FSCL_QTR, KPI_FIL_EXTRT.ID_KPI  "+
																									" FROM DPB_KPI_FIL_EXTRT AS KPI_FIL_EXTRT  "+
																									" where (char(DTE_FSCL_YR) || DTE_FSCL_QTR) in   "+
																									" ( select distinct(char(DTE_FSCL_YR) ||DTE_FSCL_QTR) FROM DPB_KPI_FIL_EXTRT "+  
																									" where id_dpb_proc  = (SELECT KFE1.ID_DPB_PROC FROM DPB_KPI_FIL_EXTRT KFE1  "+
																									" WHERE KFE1.ID_KPI_FIL_EXTRT = (SELECT MAX(KFE2.ID_KPI_FIL_EXTRT) FROM DPB_KPI_FIL_EXTRT KFE2 ) ) ) "+  
																									" GROUP BY  (KPI_FIL_EXTRT.ID_DLR, KPI_FIL_EXTRT.DTE_FSCL_YR,  "+
																									" KPI_FIL_EXTRT.DTE_FSCL_QTR  , KPI_FIL_EXTRT.ID_KPI )  "+
																									" HAVING COUNT(*) > 1   "+
																									" ORDER BY KPI_FIL_EXTRT.ID_KPI ASC "+ 
																									" ),  "+
																									" GET_KPI(ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI , PCT_KPI) as "+  
																									" (select ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI, kpi.PCT_KPI   "+
																									" from DPB_KPI_FIL_EXTRT kpi where (ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI) in "+  
																									" (select ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI from ADJ_KPI adj)  "+
																									" ), "+ 
																									" COMP_KPI(ID_KPI_FIL_EXTRT, ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI ) as "+  
																									" (select distinct(nxt.ID_KPI_FIL_EXTRT), nxt.ID_DLR, nxt.DTE_FSCL_YR ,   "+
																									" nxt.DTE_FSCL_QTR, nxt.ID_KPI  from  GET_KPI as curr   "+
																									" LEFT OUTER JOIN DPB_KPI_FIL_EXTRT as nxt on ( nxt.ID_DLR=curr.ID_DLR and "+  
																									" nxt.DTE_FSCL_YR=curr.DTE_FSCL_YR and nxt.DTE_FSCL_QTR=curr.DTE_FSCL_QTR  "+
																									" and nxt.ID_KPI=curr.ID_KPI )  "+
																									" ),  "+
																									" CURR_KPI(ID_KPI_FIL_EXTRT, ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI ) as "+  
																									" (select max(ID_KPI_FIL_EXTRT), ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI  from COMP_KPI "+  
																									" group by ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI   "+
																									" ),  "+
																									" PRE_KPI( ID_KPI_FIL_EXTRT, ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI , PCT_KPI) as "+  
																									" (select distinct(nxt.ID_KPI_FIL_EXTRT) , nxt.ID_DLR, nxt.DTE_FSCL_YR ,   "+
																									" nxt.DTE_FSCL_QTR, nxt.ID_KPI, nxt.PCT_KPI from  GET_KPI as curr   "+
																									" LEFT OUTER JOIN DPB_KPI_FIL_EXTRT as nxt on ( nxt.ID_DLR=curr.ID_DLR and "+  
																									" nxt.DTE_FSCL_YR=curr.DTE_FSCL_YR and nxt.DTE_FSCL_QTR=curr.DTE_FSCL_QTR   "+
																									" and nxt.ID_KPI=curr.ID_KPI )  "+
																									" ),  "+
																									" EXIST_KPI(  ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI , PCT_KPI) as "+  
																									" (select  ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI , sum(PCT_KPI) from PRE_KPI "+ 
																									" group by  ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI   "+
																									" ),  "+
																									" CURR_KPI_AMT( ID_KPI_FIL_EXTRT, ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI , PCT_KPI) as "+  
																									" (select nxt.ID_KPI_FIL_EXTRT , nxt.ID_DLR, nxt.DTE_FSCL_YR , nxt.DTE_FSCL_QTR,  "+
																									" nxt.ID_KPI, nxt.PCT_KPI from  CURR_KPI as curr   "+
																									" LEFT OUTER JOIN DPB_KPI_FIL_EXTRT as nxt on ( nxt.ID_KPI_FIL_EXTRT=curr.ID_KPI_FIL_EXTRT ) "+ 
																									" ),  "+
																									" RES_KPI( ID_KPI_FIL_EXTRT, ID_DLR, DTE_FSCL_YR , DTE_FSCL_QTR, ID_KPI , PCT_KPI) as "+  
																									" (select curr.ID_KPI_FIL_EXTRT, curr.ID_DLR, curr.DTE_FSCL_YR , curr.DTE_FSCL_QTR,   "+
																									" curr.ID_KPI, ( (2* curr.PCT_KPI) - nxt.PCT_KPI) as PCT_KPI from  CURR_KPI_AMT as curr  "+ 
																									" LEFT OUTER JOIN EXIST_KPI as nxt on ( nxt.ID_DLR=curr.ID_DLR and   "+
																									" nxt.DTE_FSCL_YR=curr.DTE_FSCL_YR and nxt.DTE_FSCL_QTR=curr.DTE_FSCL_QTR "+  
																									" and nxt.ID_KPI=curr.ID_KPI )  "+
																									" )  "+
																									" select * from RES_KPI order by ID_KPI_FIL_EXTRT ";
*/
	public static final String KPI_PERCENTAGE_CHANGE_QTR_OR_MONTHLY_BY_LAST_PROCESSED_KPI_FILE_ID = "with KPI_DATA (ID_KPI_FIL_EXTRT,ID_DPB_PROC,ID_DLR,DTE_FSCL_QTR,DTE_FSCL_YR,ID_KPI,PCT_KPI,ROW_NUM) as (" + 
																									"select ID_KPI_FIL_EXTRT,ID_DPB_PROC,ID_DLR,DTE_FSCL_QTR,DTE_FSCL_YR,ID_KPI,PCT_KPI, "+
																									//"row_number() over(partition by ID_DLR,ID_KPI,DTE_FSCL_QTR,DTE_FSCL_YR order by ID_KPI_FIL_EXTRT," +
																									"row_number() over(partition by ID_DLR,ID_KPI,DTE_FSCL_QTR,DTE_FSCL_YR order by DTS_CREA," +
																									"DTE_FSCL_YR,DTE_FSCL_QTR) from DPB_KPI_FIL_EXTRT) ," +
																									"LatestRC(pID) as (select Id_dpb_proc from DPB_KPI_FIL_EXTRT where DTS_CREA = (SELECT MAX(DTS_CREA) FROM DPB_KPI_FIL_EXTRT) )," +
																									"KPI_CURR_DATA (ID_KPI_FIL_EXTRT,ID_DLR,DTE_FSCL_QTR,DTE_FSCL_YR,ID_KPI,PCT_KPI,ROW_NUM) as ( "+
																									"select kd.ID_KPI_FIL_EXTRT,kd.ID_DLR,kd.DTE_FSCL_QTR,kd.DTE_FSCL_YR,kd.ID_KPI,kd.PCT_KPI,kd.ROW_NUM "+
																									"from KPI_DATA kd  where (kd.ID_DLR,Kd.ID_KPI,kd.DTE_FSCL_QTR,kd.DTE_FSCL_YR, kd.ROW_NUM) in "+
																									"(select ID_DLR,ID_KPI,DTE_FSCL_QTR,DTE_FSCL_YR,max(ROW_NUM)from KPI_DATA SKD ,LatestRC P where SKD.ID_DPB_PROC =p.pID " +
																									"group by ID_DLR,ID_KPI,DTE_FSCL_QTR,DTE_FSCL_YR) and ROW_NUM > 1)," + 
																									"KPI_PREV_DATA (ID_KPI_FIL_EXTRT,ID_DLR,DTE_FSCL_QTR,DTE_FSCL_YR,ID_KPI,PCT_KPI,ROW_NUM) as (" + 
																									"select ID_KPI_FIL_EXTRT,ID_DLR,DTE_FSCL_QTR,DTE_FSCL_YR,ID_KPI,PCT_KPI,ROW_NUM  from KPI_DATA KD " +
																									"where  (kd.ID_DLR,Kd.ID_KPI,kd.DTE_FSCL_QTR,kd.DTE_FSCL_YR, kd.ROW_NUM) in " +
																									"(select ID_DLR,ID_KPI,DTE_FSCL_QTR,DTE_FSCL_YR,max(ROW_NUM)-1 " +
																									"from KPI_DATA SKD ,LatestRC P where SKD.ID_DPB_PROC =p.pID " +
																									"group by ID_DLR,ID_KPI,DTE_FSCL_QTR,DTE_FSCL_YR))," + 
																									"KPI_DIFF (ID_DLR,DTE_FSCL_QTR,DTE_FSCL_YR,ID_KPI,PCT_KPI) as ( "+
																									"select curr.ID_DLR,curr.DTE_FSCL_QTR,curr.DTE_FSCL_YR,curr.ID_KPI,curr.PCT_KPI " + 
																									"from KPI_CURR_DATA curr  where  exists (SELECT prev.ID_DLR,prev.DTE_FSCL_QTR,prev.DTE_FSCL_YR,prev.ID_KPI,prev.PCT_KPI " +
																									"FROM KPI_PREV_DATA prev  where curr.ID_DLR = prev.ID_DLR and curr.DTE_FSCL_QTR = prev.DTE_FSCL_QTR and " +
																									"curr.DTE_FSCL_YR = prev.DTE_FSCL_YR and curr.ID_KPI = prev.ID_KPI and (curr.PCT_KPI-prev.pct_kpi)!= 0  )) " + 
																									"select * from KPI_DIFF  where  pct_kpi is not null " ;


	
	
	public static final String GET_TERMINATED_DEALER_LIST_TILL_GIVEN_DATE = 	" SELECT ID_DLR,CDE_DLR_SHT_NAM,NAM_DLR,NAM_DO_BUSN_AS," +
																				" ADR_PRIM_CITY,CDE_DLR_ST,DTE_MBUSA_TERM " +
																				" FROM DEALER_FDRT   WHERE date(DTE_MBUSA_TERM) <= ? " ;
	
	
	
	public static final String GET_CARS_DEALER_INFO = " with unblk1 (unblk_id,dlr, num_po,vehType) as 	"+
			" (select id_dpb_unblk_veh, id_dlr, num_po,cde_veh_typ from dpb_unblk_veh "+
			" where  cde_veh_typ in ('P') and  id_dpb_pgm is null    "+
			" and dte_rtl between  ? and ?  ),  "+
									 
			" unblk2 (unblk_id,dlr, num_po,vehType) as 	"+
			" (select id_dpb_unblk_veh, id_dlr, num_po,cde_veh_typ from dpb_unblk_veh "+
			" where  cde_veh_typ in ('P') and  id_dpb_pgm in ( 121,123)  "+
			" and dte_rtl between  ? and ?  ),  "+
			
			" unblk3 (unblk_id,dlr, num_po,vehType) as 	"+
			" (select id_dpb_unblk_veh, id_dlr, num_po,cde_veh_typ from dpb_unblk_veh "+
			" where  cde_veh_typ in ('P') and  id_dpb_pgm is null    "+
			" and dte_rtl between  '2013-04-01' and ?   ),  "+
			
			" unblk4 (unblk_id,dlr, num_po,vehType) as 	"+
			" (select id_dpb_unblk_veh, id_dlr, num_po,cde_veh_typ from dpb_unblk_veh "+
			" where  cde_veh_typ in ('P') and  id_dpb_pgm in ( 121,123)  "+
			" and dte_rtl between  '2013-04-01' and ?   ),  "+
			
			" calc1 (dlr,bonus_amount,vehType,pgm_id) as	"+
			" (select cal.id_dlr, cal.amt_dpb_bns_calc,ub.vehType,cal.id_dpb_pgm  "+
			// " from dpb_calc cal, unblk1 ub where id_dpb_pgm in (46,47,48,49,50,142,143) and cal.ind_dpb_adj='N' and  "+
			" from dpb_calc cal, unblk1 ub where id_dpb_pgm in (46,47,144,145,48,49,50) and cal.ind_dpb_adj='N' and  "+
			" cal.id_dpb_unblk_veh = ub.unblk_id  order by vehType,id_dlr,id_dpb_pgm), "+

 			" calc2 (dlr,bonus_amount,vehType,pgm_id) as	 "+
			" (select cal.id_dlr, cal.amt_dpb_bns_calc,ub.vehType,cal.id_dpb_pgm  "+
			//" from dpb_calc cal, unblk2 ub where id_dpb_pgm in (47,48,49,50,142,143) and cal.ind_dpb_adj='N' and  "+
			" from dpb_calc cal, unblk2 ub where id_dpb_pgm in (47,144,145,48,49,50) and cal.ind_dpb_adj='N' and  "+
			" cal.id_dpb_unblk_veh = ub.unblk_id  order by vehType,id_dlr,id_dpb_pgm),  "+

			" calc3 (dlr,bonus_amount,vehType,pgm_id) as	"+
			" (select cal.id_dlr, cal.amt_dpb_bns_calc,ub.vehType,cal.id_dpb_pgm  "+
			// " from dpb_calc cal, unblk3 ub where id_dpb_pgm in (46,47,48,49,50,142,143) and   "+
			" from dpb_calc cal, unblk3 ub where id_dpb_pgm in (46,47,144,145,48,49,50) and   "+
			" cal.id_dpb_unblk_veh = ub.unblk_id  and cal.ind_dpb_adj='Y' and cal.dte_cal between ? and ? " + 
			" order by vehType,id_dlr,id_dpb_pgm), "+
			
			" calc4 (dlr,bonus_amount,vehType,pgm_id) as	 "+
			" (select cal.id_dlr, cal.amt_dpb_bns_calc,ub.vehType,cal.id_dpb_pgm  "+
			//" from dpb_calc cal, unblk4 ub where id_dpb_pgm in (47,48,49,50,142,143) and   "+
			" from dpb_calc cal, unblk4 ub where id_dpb_pgm in (47,144,145,48,49,50) and   "+
			" cal.id_dpb_unblk_veh = ub.unblk_id   and cal.ind_dpb_adj='Y' and cal.dte_cal between ? and ? " +
			" order by vehType,id_dlr,id_dpb_pgm),  "+

			" calc (dlr,bonus_amount,vehType,pgm_id) as	  "+
			" (select * from calc1 union all select * from calc2 union all select * from calc3 union all select * from calc4 ), "+
			
			" total(dlr, total,vehType,vehcount,pgm_id) as  	"+ 
			" (select dlr ,sum(bonus_amount),vehType,count(dlr), pgm_id from calc group by (vehType,dlr,pgm_id )), "+
			
			" temp1( dlr,vehType,vehcount,total, text) as ( select dlr,vehType,sum(vehcount),sum(total), "+
			" 'FL PL CAR 100114FLOOR PLAN'  as text  "+
			" from total where pgm_id in (46)  group by(vehType,dlr)),  "+
									 
			" temp2( dlr, vehType,vehcount,total,text) as ( select dlr,vehType,sum(vehcount),sum(total), "+
			" 'COMPL CAR 100114COMPLIANCE' as text   "+
			//" from total where pgm_id in (47,48,49,50,142,143)  group by (vehType,dlr))  "+
			" from total where pgm_id in (47,144,145,48,49,50)  group by (vehType,dlr))  "+
									 
			" select * from temp1 union all select * from temp2 order by vehType,dlr,vehcount for fetch only with ur ";
			
	//Separating AMG Payment file from regular Cars payment file (11-Jul-2016).  
	public static final String GET_AMG_CARS_DEALER_INFO = " with unblk1 (unblk_id,dlr, num_po,vehType) as 	"+
			" (select id_dpb_unblk_veh, id_dlr, num_po,cde_veh_typ from dpb_unblk_veh "+
			" where  cde_veh_typ in ('P') and  id_dpb_pgm is null    "+
			" and dte_rtl between  ? and ?  ),  "+
			
			" unblk3 (unblk_id,dlr, num_po,vehType) as 	"+
			" (select id_dpb_unblk_veh, id_dlr, num_po,cde_veh_typ from dpb_unblk_veh "+
			" where  cde_veh_typ in ('P') and  id_dpb_pgm is null    "+
			" and dte_rtl between  '2013-04-01' and ?   ),  "+

			" calc1 (dlr,bonus_amount,vehType,pgm_id) as	"+
			" (select cal.id_dlr, cal.amt_dpb_bns_calc,ub.vehType,cal.id_dpb_pgm  "+
			" from dpb_calc cal, unblk1 ub where id_dpb_pgm in (142,143) and cal.ind_dpb_adj='N' and  "+
			" cal.id_dpb_unblk_veh = ub.unblk_id  order by vehType,id_dlr,id_dpb_pgm), "+
			
			" calc3 (dlr,bonus_amount,vehType,pgm_id) as	"+
			" (select cal.id_dlr, cal.amt_dpb_bns_calc,ub.vehType,cal.id_dpb_pgm  "+
			" from dpb_calc cal, unblk3 ub where id_dpb_pgm in (142,143) and   "+
			" cal.id_dpb_unblk_veh = ub.unblk_id  and cal.ind_dpb_adj='Y' and cal.dte_cal between ? and ? " + 
			" order by vehType,id_dlr,id_dpb_pgm), "+

			" calc (dlr,bonus_amount,vehType,pgm_id) as	  "+
			" (select * from calc1 union all select * from calc3  ), "+

			" total(dlr, total,vehType,vehcount,pgm_id) as  	"+ 
			" (select dlr ,sum(bonus_amount),vehType,count(dlr), pgm_id from calc group by (vehType,dlr,pgm_id )), "+
									 
			" temp2( dlr, vehType,vehcount,total,text) as ( select dlr,vehType,sum(vehcount),sum(total), "+
			" 'COMPL AMG 100114COMPLIANCE' as text   "+
			" from total where pgm_id in (142,143)  group by (vehType,dlr))  "+
									 
			" select * from temp2 order by vehType,dlr,vehcount for fetch only with ur  ";
	

	public static final String GET_CARS_PAYMENT_INFO = "with unblk1 (id_dpb_unblk_veh, id_dlr, num_po,Num_vin,vehType) as  "+
			"	(select id_dpb_unblk_veh, id_dlr, num_po,Num_vin, cde_veh_typ from dpb_unblk_veh  "+
			"	where  cde_veh_typ in ('P') and id_dpb_pgm is null  "+
			"	and dte_rtl between ? and ?  ),  "+
			
			" unblk2 (id_dpb_unblk_veh, id_dlr, num_po,Num_vin,vehType) as   "+
			" 	(select id_dpb_unblk_veh, id_dlr, num_po,Num_vin, cde_veh_typ from dpb_unblk_veh  "+
			"	where  cde_veh_typ in ('P') and id_dpb_pgm in (121,123)  "+
			"	 and dte_rtl between ? and ?   ), "+
			
			" unblk3 (id_dpb_unblk_veh, id_dlr, num_po,Num_vin,vehType) as  "+
			"	(select id_dpb_unblk_veh, id_dlr, num_po,Num_vin, cde_veh_typ from dpb_unblk_veh  "+
			"	where  cde_veh_typ in ('P') and id_dpb_pgm is null  "+
			"	and dte_rtl between '2013-04-01' and ?  ),  "+
			
			" unblk4 (id_dpb_unblk_veh, id_dlr, num_po,Num_vin,vehType) as   "+
			" 	(select id_dpb_unblk_veh, id_dlr, num_po,Num_vin, cde_veh_typ from dpb_unblk_veh  "+
			"	where  cde_veh_typ in ('P') and id_dpb_pgm in (121,123)  "+
			"	 and dte_rtl between '2013-04-01' and ?  ), "+
			 
 
 								 
			" calc1 (id_dlr, id_dpb_pgm, dbId, amt_dpb_bns_calc,po,text,vehType) as (select   cal.id_dlr, cal.id_dpb_pgm,  	"+
			"	(case   when cal.id_dpb_pgm = 46 and cal.amt_dpb_bns_calc >= 0 then 'MB_FP_D'  "+
			"	when cal.id_dpb_pgm = 46 and cal.amt_dpb_bns_calc < 0  then 'MB_FP_C'  "+
			"	when cal.id_dpb_pgm = 47 and cal.amt_dpb_bns_calc >= 0 then 'MB_CE_D'  "+
			"	when cal.id_dpb_pgm = 47 and cal.amt_dpb_bns_calc < 0  then 'MB_CE_C'   "+
			"	when cal.id_dpb_pgm = 144 and cal.amt_dpb_bns_calc >= 0 then 'MB_CES_D'  "+
			"	when cal.id_dpb_pgm = 144 and cal.amt_dpb_bns_calc < 0  then 'MB_CES_C'   "+
			"	when cal.id_dpb_pgm = 145 and cal.amt_dpb_bns_calc >= 0 then 'MB_CEV_D'  "+
			"	when cal.id_dpb_pgm = 145 and cal.amt_dpb_bns_calc < 0  then 'MB_CEV_C'   "+
			"       when cal.id_dpb_pgm = 48 and cal.amt_dpb_bns_calc >= 0 then 'MB_NVS_D' 	"+
			"      when cal.id_dpb_pgm = 48 and cal.amt_dpb_bns_calc < 0  then 'MB_NVS_C' "+
			"      when cal.id_dpb_pgm = 49 and cal.amt_dpb_bns_calc >= 0 then 'MB_POS_D'  	"+
			"    when cal.id_dpb_pgm = 49 and cal.amt_dpb_bns_calc < 0  then 'MB_POS_C' 	"+
			"   when cal.id_dpb_pgm = 50 and cal.amt_dpb_bns_calc >= 0 then 'MB_BS_D' 	"+
			"	when cal.id_dpb_pgm = 50 and cal.amt_dpb_bns_calc < 0  then 'MB_BS_C' 	"+
			" end) as dbId, "+
			" cal.amt_dpb_bns_calc, ub.num_po, "+
			" (case   when cal.id_dpb_pgm = 46 then 'FLOOR PLAN'  "+
			"      when cal.id_dpb_pgm = 47 then 'CUSTOMER EXPERIENCE BONUS'  "+
			"      when cal.id_dpb_pgm = 144 then 'CUSTOMER EXPERIENCE SALES BONUS'  "+
			"      when cal.id_dpb_pgm = 145 then 'CUSTOMER EXPERIENCE SERVICE BONUS'  "+
			"      when cal.id_dpb_pgm = 48 then 'NEW VEHICLE SALES BONUS' "+
			"	when cal.id_dpb_pgm = 49 then 'PREOWNED BONUS'  "+
			"	when cal.id_dpb_pgm = 50 then 'BRAND STANDARDS BONUS'  "+
			" end) as text, ub.vehType "+
									 
			//	" from dpb_calc cal, unblk1 ub where id_dpb_pgm in (46,47,48,49,50,142,143) and  cal.ind_dpb_adj='N' and  "+
			" from dpb_calc cal, unblk1 ub where id_dpb_pgm in (46,47,144,145,48,49,50) and  cal.ind_dpb_adj='N' and  "+
			" cal.id_dpb_unblk_veh = ub.id_dpb_unblk_veh  order by vehType , id_dlr,num_po, id_dpb_pgm), "+

			" calc2 (id_dlr, id_dpb_pgm, dbId, amt_dpb_bns_calc,po,text,vehType) as (select   cal.id_dlr, cal.id_dpb_pgm,  	 "+
			" (case  	when cal.id_dpb_pgm = 47 and cal.amt_dpb_bns_calc >= 0 then 'MB_CE_D'  	"+
			"	when cal.id_dpb_pgm = 47 and cal.amt_dpb_bns_calc < 0  then 'MB_CE_C'  	"+
			"  	when cal.id_dpb_pgm = 144 and cal.amt_dpb_bns_calc >= 0 then 'MB_CES_D'  	"+
			"	when cal.id_dpb_pgm = 144 and cal.amt_dpb_bns_calc < 0  then 'MB_CES_C'  	"+
			"   when cal.id_dpb_pgm = 145 and cal.amt_dpb_bns_calc >= 0 then 'MB_CEV_D'  	"+
			"	when cal.id_dpb_pgm = 145 and cal.amt_dpb_bns_calc < 0  then 'MB_CEV_C'  	"+
			"	when cal.id_dpb_pgm = 48 and cal.amt_dpb_bns_calc >= 0 then 'MB_NVS_D'  "+
			"	when cal.id_dpb_pgm = 48 and cal.amt_dpb_bns_calc < 0  then 'MB_NVS_C'  "+
			"	when cal.id_dpb_pgm = 49 and cal.amt_dpb_bns_calc >= 0 then 'MB_POS_D'  	"+
			"	when cal.id_dpb_pgm = 49 and cal.amt_dpb_bns_calc < 0  then 'MB_POS_C' 	"+
			"	when cal.id_dpb_pgm = 50 and cal.amt_dpb_bns_calc >= 0 then 'MB_BS_D' "+
			"	when cal.id_dpb_pgm = 50 and cal.amt_dpb_bns_calc <  0 then 'MB_BS_C'" +
			"	end) as dbId, "+
			" cal.amt_dpb_bns_calc, ub.num_po, 'MBDeal' as text, ub.vehType  "+
									 
			//" from dpb_calc cal, unblk2 ub where id_dpb_pgm in (47,48,49,50,142,143) and  cal.ind_dpb_adj='N' and  "+
			" from dpb_calc cal, unblk2 ub where id_dpb_pgm in (47,144,145,48,49,50) and  cal.ind_dpb_adj='N' and  "+
			" cal.id_dpb_unblk_veh = ub.id_dpb_unblk_veh  order by vehType , id_dlr,num_po, id_dpb_pgm), "+
		 
			" calc3 (id_dlr, id_dpb_pgm, dbId, amt_dpb_bns_calc,po,text,vehType) as (select   cal.id_dlr, cal.id_dpb_pgm,  	"+
			"	(case   when cal.id_dpb_pgm = 46 and cal.amt_dpb_bns_calc >= 0 then 'MB_FP_D'  "+
			"	when cal.id_dpb_pgm = 46 and cal.amt_dpb_bns_calc < 0  then 'MB_FP_C'  "+
			"	when cal.id_dpb_pgm = 47 and cal.amt_dpb_bns_calc >= 0 then 'MB_CE_D'  "+
			"	when cal.id_dpb_pgm = 47 and cal.amt_dpb_bns_calc < 0  then 'MB_CE_C'   "+
			"	when cal.id_dpb_pgm = 144 and cal.amt_dpb_bns_calc >= 0 then 'MB_CES_D'  "+
			"	when cal.id_dpb_pgm = 144 and cal.amt_dpb_bns_calc < 0  then 'MB_CES_C'   "+
			"	when cal.id_dpb_pgm = 145 and cal.amt_dpb_bns_calc >= 0 then 'MB_CEV_D'  "+
			"	when cal.id_dpb_pgm = 145 and cal.amt_dpb_bns_calc < 0  then 'MB_CEV_C'   "+
			"       when cal.id_dpb_pgm = 48 and cal.amt_dpb_bns_calc >= 0 then 'MB_NVS_D' 	"+
			"      when cal.id_dpb_pgm = 48 and cal.amt_dpb_bns_calc < 0  then 'MB_NVS_C' "+
			"      when cal.id_dpb_pgm = 49 and cal.amt_dpb_bns_calc >= 0 then 'MB_POS_D'  	"+
			"    when cal.id_dpb_pgm = 49 and cal.amt_dpb_bns_calc < 0  then 'MB_POS_C' 	"+
			"   when cal.id_dpb_pgm = 50 and cal.amt_dpb_bns_calc >= 0 then 'MB_BS_D' 	"+
			"	when cal.id_dpb_pgm = 50 and cal.amt_dpb_bns_calc < 0  then 'MB_BS_C' 	"+
			" end) as dbId, "+
									   
			" cal.amt_dpb_bns_calc, ub.num_po, "+
			" (case   when cal.id_dpb_pgm = 46 then 'FLOOR PLAN'  "+
			"      when cal.id_dpb_pgm = 47 then 'CUSTOMER EXPERIENCE BONUS'  "+
			"      when cal.id_dpb_pgm = 144 then 'CUSTOMER EXPERIENCE SALES BONUS'  "+
			"      when cal.id_dpb_pgm = 145 then 'CUSTOMER EXPERIENCE SERVICE BONUS'  "+
			"      when cal.id_dpb_pgm = 48 then 'NEW VEHICLE SALES BONUS' "+
			"	when cal.id_dpb_pgm = 49 then 'PREOWNED BONUS'  "+
			"	when cal.id_dpb_pgm = 50 then 'BRAND STANDARDS BONUS'  "+
			" end) as text, ub.vehType "+
									 
			//	" from dpb_calc cal, unblk3 ub where id_dpb_pgm in (46,47,48,49,50,142,143) and   "+
			" from dpb_calc cal, unblk3 ub where id_dpb_pgm in (46,47,144,145,48,49,50) and   "+
			" cal.id_dpb_unblk_veh = ub.id_dpb_unblk_veh  and cal.ind_dpb_adj='Y' and cal.dte_cal between ? and ? " +
			" order by vehType , id_dlr,num_po, id_dpb_pgm), "+

			" calc4 (id_dlr, id_dpb_pgm, dbId, amt_dpb_bns_calc,po,text,vehType) as (select   cal.id_dlr, cal.id_dpb_pgm,  	 "+
			" (case  	when cal.id_dpb_pgm = 47 and cal.amt_dpb_bns_calc >= 0 then 'MB_CE_D'  	"+
			"	when cal.id_dpb_pgm = 47 and cal.amt_dpb_bns_calc < 0  then 'MB_CE_C'  	"+
			" 	when cal.id_dpb_pgm = 144 and cal.amt_dpb_bns_calc >= 0 then 'MB_CES_D'  	"+
			"	when cal.id_dpb_pgm = 144 and cal.amt_dpb_bns_calc < 0  then 'MB_CES_C'  	"+
			"   when cal.id_dpb_pgm = 145 and cal.amt_dpb_bns_calc >= 0 then 'MB_CEV_D'  	"+
			"	when cal.id_dpb_pgm = 145 and cal.amt_dpb_bns_calc < 0  then 'MB_CEV_C'  	"+
			"	when cal.id_dpb_pgm = 48 and cal.amt_dpb_bns_calc >= 0 then 'MB_NVS_D'  "+
			"	when cal.id_dpb_pgm = 48 and cal.amt_dpb_bns_calc < 0  then 'MB_NVS_C'  "+
			"	when cal.id_dpb_pgm = 49 and cal.amt_dpb_bns_calc >= 0 then 'MB_POS_D'  	"+
			"	when cal.id_dpb_pgm = 49 and cal.amt_dpb_bns_calc < 0  then 'MB_POS_C' 	"+
			"	when cal.id_dpb_pgm = 50 and cal.amt_dpb_bns_calc >= 0 then 'MB_BS_D' "+
			"	when cal.id_dpb_pgm = 50 and cal.amt_dpb_bns_calc <  0 then 'MB_BS_C'	 "+
			"	end) as dbId, "+
			" cal.amt_dpb_bns_calc, ub.num_po, 'MBDeal' as text, ub.vehType  "+
			//" from dpb_calc cal, unblk4 ub where id_dpb_pgm in (47,48,49,50,142,143) and   "+
			" from dpb_calc cal, unblk4 ub where id_dpb_pgm in (47,144,145,48,49,50) and   "+
			" cal.id_dpb_unblk_veh = ub.id_dpb_unblk_veh  and cal.ind_dpb_adj='Y' and cal.dte_cal between ? and ? " +
			" order by vehType , id_dlr,num_po, id_dpb_pgm), "+

 			" calc (id_dlr, id_dpb_pgm, dbId, amt_dpb_bns_calc,po,text,vehType) as  "+
 			" (select * from calc1 union all select * from calc2 union all select * from calc3  union all select * from calc4 ) "+
	
			" select id_dlr,vehType, id_dpb_pgm,dbId, amt_dpb_bns_calc,po,text from calc cal   "+
			" order by vehType, id_dlr , id_dpb_pgm  for fetch only with ur ";

	// Separating AMG Payment file from regular Cars payment file (11-Jul-2016). 
	public static final String GET_AMG_CARS_PAYMENT_INFO = "with unblk1 (id_dpb_unblk_veh, id_dlr, num_po,Num_vin,vehType) as  "+
			"	(select id_dpb_unblk_veh, id_dlr, num_po,Num_vin, cde_veh_typ from dpb_unblk_veh  "+
			"	where  cde_veh_typ in ('P') and id_dpb_pgm is null  "+
			"	and dte_rtl between ? and ?  ),  "+
			
			" unblk3 (id_dpb_unblk_veh, id_dlr, num_po,Num_vin,vehType) as  "+
			"	(select id_dpb_unblk_veh, id_dlr, num_po,Num_vin, cde_veh_typ from dpb_unblk_veh  "+
			"	where  cde_veh_typ in ('P') and id_dpb_pgm is null  "+
			"	and dte_rtl between '2013-04-01' and ?  ),  "+
		 								 
			" calc1 (id_dlr, id_dpb_pgm, dbId, amt_dpb_bns_calc,po,text,vehType) as (select   cal.id_dlr, cal.id_dpb_pgm,  	"+
			"	(case when cal.id_dpb_pgm = 142 and cal.amt_dpb_bns_calc >= 0 then 'MB_APB_D'  	"+	
			"	when cal.id_dpb_pgm = 142 and cal.amt_dpb_bns_calc < 0  then 'MB_APB_C'  	"+	
			"	when cal.id_dpb_pgm = 143 and cal.amt_dpb_bns_calc >= 0 then 'MB_AEB_D'  	"+	
			"	when cal.id_dpb_pgm = 143 and cal.amt_dpb_bns_calc < 0  then 'MB_AEB_C' 	"+
			" end) as dbId, "+
			" cal.amt_dpb_bns_calc, ub.num_po, "+
			" (case  when cal.id_dpb_pgm = 142 then 'AMG PERFORMANCE BONUS'   "+
			"	when cal.id_dpb_pgm = 143 then 'AMG ELITE PERFORMANCE BONUS'  "+ 
			" end) as text, ub.vehType "+
			" from dpb_calc cal, unblk1 ub where id_dpb_pgm in (142,143) and  cal.ind_dpb_adj='N' and  "+
			" cal.id_dpb_unblk_veh = ub.id_dpb_unblk_veh  order by vehType , id_dlr,num_po, id_dpb_pgm), "+
			 
			" calc3 (id_dlr, id_dpb_pgm, dbId, amt_dpb_bns_calc,po,text,vehType) as (select   cal.id_dlr, cal.id_dpb_pgm,  	"+
			"	(case when cal.id_dpb_pgm = 142 and cal.amt_dpb_bns_calc >= 0 then 'MB_APB_D'  	"+	
			"	when cal.id_dpb_pgm = 142 and cal.amt_dpb_bns_calc < 0  then 'MB_APB_C'  	"+	
			"	when cal.id_dpb_pgm = 143 and cal.amt_dpb_bns_calc >= 0 then 'MB_AEB_D'  	"+	
			"	when cal.id_dpb_pgm = 143 and cal.amt_dpb_bns_calc < 0  then 'MB_AEB_C' 	"+	       
			" end) as dbId, "+
			" cal.amt_dpb_bns_calc, ub.num_po, "+
			" (case when cal.id_dpb_pgm = 142 then 'AMG PERFORMANCE BONUS'   "+
			"	when cal.id_dpb_pgm = 143 then 'AMG ELITE PERFORMANCE BONUS'  "+ 
			" end) as text, ub.vehType "+
			" from dpb_calc cal, unblk3 ub where id_dpb_pgm in (142,143) and   "+
			" cal.id_dpb_unblk_veh = ub.id_dpb_unblk_veh  and cal.ind_dpb_adj='Y' and cal.dte_cal between ? and ? " +
			" order by vehType , id_dlr,num_po, id_dpb_pgm), "+
			
			" calc (id_dlr, id_dpb_pgm, dbId, amt_dpb_bns_calc,po,text,vehType) as  "+
			" (select * from calc1 union all select * from calc3  ) "+
				
			" select id_dlr,vehType, id_dpb_pgm,dbId, amt_dpb_bns_calc,po,text from calc cal   "+
			" order by vehType, id_dlr , id_dpb_pgm for fetch only with ur ";


public static final String GET_DEALER_INFO = " with unblk1 (unblk_id,dlr, num_po,vehType) as  "+
" (select id_dpb_unblk_veh, id_dlr, num_po,cde_veh_typ from dpb_unblk_veh  "+
" where  cde_veh_typ in (?) and id_dpb_pgm is null and "+
" dte_rtl between ? and ? ),  "+

" unblk2 (unblk_id,dlr, num_po,vehType) as  "+
" (select id_dpb_unblk_veh, id_dlr, num_po,cde_veh_typ from dpb_unblk_veh "+
" where  cde_veh_typ in (?) and id_dpb_pgm is null  and "+
" dte_rtl between '2013-04-01' and ? ), "+

" calc1 (dlr,bonus_amount,vehType,pgm_id) as	"+
" (select cal.id_dlr, cal.amt_dpb_bns_calc,ub.vehType,cal.id_dpb_pgm  "+
" from dpb_calc cal, unblk1 ub where id_dpb_pgm in (51,52,53,54,55,56,57,58,59,60,61,62,141,142,143) and  cal.ind_dpb_adj='N' and "+
" cal.id_dpb_unblk_veh = ub.unblk_id order by vehType,id_dlr,id_dpb_pgm),  "+

" calc2 (dlr,bonus_amount,vehType,pgm_id) as	 "+
" (select cal.id_dlr, cal.amt_dpb_bns_calc,ub.vehType,cal.id_dpb_pgm  "+
" from dpb_calc cal, unblk2 ub where id_dpb_pgm in (51,52,53,54,55,56,57,58,59,60,61,62,141,142,143) and   "+
" cal.id_dpb_unblk_veh = ub.unblk_id  and cal.ind_dpb_adj='Y' and cal.dte_cal between ? and ? "+
" order by vehType,id_dlr,id_dpb_pgm),  "+

" calc (dlr,bonus_amount,vehType,pgm_id) as	 "+
"(select  * from calc1 union all select * from calc2), "+

" total(dlr, total,vehType,vehcount,pgm_id) as  	"+
" (select dlr ,sum(bonus_amount),vehType,count(dlr), pgm_id from calc group by (vehType,dlr,pgm_id ))  ,  "+

" temp1(dlr,vehType,vehcount,total, text) as ( select dlr,vehType,sum(vehcount),sum(total),  "+
" (case   when vehType ='S' then 'RESRV SMT 100114DLR RESERVE'  "+
"       when vehType ='V' then 'RESRV MBV 100114DLR RESERVE'  "+
"        when vehType ='F' then 'RESRV DVU 100114DLR RESERVE'  "+
" end) as text  "+
" from total  where pgm_id in (51,52,53)  group by(vehType,dlr)),  "+
					   
" temp2(dlr, vehType,vehcount,total,text) as ( select dlr,vehType,sum(vehcount),sum(total), "+
" (case   when vehType ='S' then 'COMPL SMT 100114COMPLIANCE'  "+
"        when vehType ='V' then 'COMPL MBV 100114COMPLIANCE'  "+
"         when vehType ='F' then 'COMPL DVU 100114COMPLIANCE'  "+
" end) as text  "+
" from total where pgm_id in (54,55,56,57,58,59,60,61,62,141,142,143) group by (vehType,dlr))  "+
			   				   
" select * from temp1 union all select * from temp2   order by vehType,dlr,vehcount  for fetch only with ur ";
			 
			 

public static final String GET_PAYMENT_INFO = " with unblk1 (id_dpb_unblk_veh, id_dlr, num_po,Num_vin,vehType) as "+
" (select id_dpb_unblk_veh, id_dlr, num_po,Num_vin, cde_veh_typ from dpb_unblk_veh  "+
" where  cde_veh_typ in (?) and id_dpb_pgm is null  "+
" and dte_rtl between ? and ? ),  "+

" unblk2 (id_dpb_unblk_veh, id_dlr, num_po,Num_vin,vehType) as "+
" (select id_dpb_unblk_veh, id_dlr, num_po,Num_vin, cde_veh_typ from dpb_unblk_veh  "+
" where  cde_veh_typ in (?) and id_dpb_pgm is null   "+
" and dte_rtl between '2013-04-01' and ? ), "+
	 
" calc1 (id_dlr, id_dpb_pgm, dbId, amt_dpb_bns_calc,po,text,vehType) as (select   cal.id_dlr, cal.id_dpb_pgm,  "+
" (case   when cal.id_dpb_pgm = 53 and cal.amt_dpb_bns_calc >= 0 then 'DVU_DR_D'  "+
" 	when cal.id_dpb_pgm = 53 and cal.amt_dpb_bns_calc < 0  then  'DVU_DR_C'	 "+
" 	when cal.id_dpb_pgm = 60 and cal.amt_dpb_bns_calc >= 0 then 'DVU_CE_D'  "+
" 	when cal.id_dpb_pgm = 60 and cal.amt_dpb_bns_calc < 0  then 'DVU_CE_C' "+
" 	when cal.id_dpb_pgm = 61 and cal.amt_dpb_bns_calc >= 0 then 'DVU_BS_D' "+
" 	when cal.id_dpb_pgm = 61 and cal.amt_dpb_bns_calc < 0  then 'DVU_BS_C'  "+
" 	when cal.id_dpb_pgm = 62 and cal.amt_dpb_bns_calc >= 0 then 'DVU_DS_D'  "+
" 	when cal.id_dpb_pgm = 62 and cal.amt_dpb_bns_calc < 0  then 'DVU_DS_C'  "+

" when cal.id_dpb_pgm = 51 and cal.amt_dpb_bns_calc >= 0 then 'SM_DR_D'   	"+
"  when cal.id_dpb_pgm = 51 and cal.amt_dpb_bns_calc < 0  then 'SM_DR_C' 	"+
" when cal.id_dpb_pgm = 54 and cal.amt_dpb_bns_calc >= 0 then 'SM_CE_D' 	"+
" when cal.id_dpb_pgm = 54 and cal.amt_dpb_bns_calc < 0  then 'SM_CE_C' "+
" when cal.id_dpb_pgm = 55 and cal.amt_dpb_bns_calc >= 0 then 'SM_DS_D' 	"+
" when cal.id_dpb_pgm = 55 and cal.amt_dpb_bns_calc < 0  then 'SM_DS_C' "+
" when cal.id_dpb_pgm = 141 and cal.amt_dpb_bns_calc >= 0 then 'SM_DS_D' 	"+
" when cal.id_dpb_pgm = 141 and cal.amt_dpb_bns_calc < 0  then 'SM_DS_C' "+
" when cal.id_dpb_pgm = 56 and cal.amt_dpb_bns_calc >= 0 then 'SM_FB_D'  "+
" when cal.id_dpb_pgm = 56 and cal.amt_dpb_bns_calc < 0  then 'SM_FB_C' "+
" when cal.id_dpb_pgm = 52 and cal.amt_dpb_bns_calc >= 0 then 'MBV_DR_D'  "+
" when cal.id_dpb_pgm = 52 and cal.amt_dpb_bns_calc < 0  then 'MBV_DR_C' 	"+
" when cal.id_dpb_pgm = 57 and cal.amt_dpb_bns_calc >= 0 then 'MBV_CE_D' "+
" when cal.id_dpb_pgm = 57 and cal.amt_dpb_bns_calc < 0  then 'MBV_CE_C'  	"+
" when cal.id_dpb_pgm = 58 and cal.amt_dpb_bns_calc >= 0 then 'MBV_BS_D'	"+
" when cal.id_dpb_pgm = 58 and cal.amt_dpb_bns_calc < 0  then 'MBV_BS_C' "+
" when cal.id_dpb_pgm = 59 and cal.amt_dpb_bns_calc >= 0 then 'MBV_DS_D'  "+
" when cal.id_dpb_pgm = 59 and cal.amt_dpb_bns_calc < 0  then 'MBV_DS_C'  "+
" when cal.id_dpb_pgm = 142 and cal.amt_dpb_bns_calc >= 0 then 'MB_APB_D'  "+  
" when cal.id_dpb_pgm = 142 and cal.amt_dpb_bns_calc < 0  then 'MB_APB_C'  "+ 
" when cal.id_dpb_pgm = 143 and cal.amt_dpb_bns_calc >= 0 then 'MB_AEB_D'  "+  
" when cal.id_dpb_pgm = 143 and cal.amt_dpb_bns_calc < 0  then 'MB_AEB_C'  "+
" 	end) as dbId, "+
			   
" cal.amt_dpb_bns_calc, ub.num_po, "+
" (case  when cal.id_dpb_pgm in (54,57,60) then 'CUSTOMER EXPERIENCE BONUS'  "+
" when cal.id_dpb_pgm in (55,59,62) then 'NEW VEHICLE SALES BONUS' "+
" when cal.id_dpb_pgm in (51,52,53) then 'DLR RESERVE'  "+
" when cal.id_dpb_pgm in (58,61) then 'COMMERCIAL VEHICLE BRAND STANDARDS' "+
" when cal.id_dpb_pgm in (56)    then 'SMART FRANCHISE'  "+
" when cal.id_dpb_pgm in (141)    then 'BRAND STANDARDS'  "+
" when cal.id_dpb_pgm in (142)    then 'AMG PERFORMANCE BONUS'  "+
" when cal.id_dpb_pgm in (143)    then 'AMG ELITE PERFORMANCE BONUS'  "+
" 	 end) as text, ub.vehType  "+
			 
" from dpb_calc cal, unblk1 ub where id_dpb_pgm in (51,52,54,55,141,56,57,58,59,53,60,61,62,142,143) and  cal.ind_dpb_adj='N' and "+
" cal.id_dpb_unblk_veh = ub.id_dpb_unblk_veh  order by vehType, id_dlr, num_po, id_dpb_pgm) , "+

" calc2 (id_dlr, id_dpb_pgm, dbId, amt_dpb_bns_calc,po,text,vehType) as (select   cal.id_dlr, cal.id_dpb_pgm,  "+
" (case   when cal.id_dpb_pgm = 53 and cal.amt_dpb_bns_calc >= 0 then 'DVU_DR_D'  "+
" 	when cal.id_dpb_pgm = 53 and cal.amt_dpb_bns_calc < 0  then  'DVU_DR_C'	 "+
" 	when cal.id_dpb_pgm = 60 and cal.amt_dpb_bns_calc >= 0 then 'DVU_CE_D'  "+
" 	when cal.id_dpb_pgm = 60 and cal.amt_dpb_bns_calc < 0  then 'DVU_CE_C' "+
" 	when cal.id_dpb_pgm = 61 and cal.amt_dpb_bns_calc >= 0 then 'DVU_BS_D' "+
" 	when cal.id_dpb_pgm = 61 and cal.amt_dpb_bns_calc < 0  then 'DVU_BS_C' "+
" 	when cal.id_dpb_pgm = 62 and cal.amt_dpb_bns_calc >= 0 then 'DVU_DS_D'  "+
" 	when cal.id_dpb_pgm = 62 and cal.amt_dpb_bns_calc < 0  then 'DVU_DS_C'  "+

" when cal.id_dpb_pgm = 51 and cal.amt_dpb_bns_calc >= 0 then 'SM_DR_D'   	"+
"  when cal.id_dpb_pgm = 51 and cal.amt_dpb_bns_calc < 0  then 'SM_DR_C' 	"+
" when cal.id_dpb_pgm = 54 and cal.amt_dpb_bns_calc >= 0 then 'SM_CE_D' 	"+
" when cal.id_dpb_pgm = 54 and cal.amt_dpb_bns_calc < 0  then 'SM_CE_C' "+
" when cal.id_dpb_pgm = 55 and cal.amt_dpb_bns_calc >= 0 then 'SM_DS_D' 	"+
" when cal.id_dpb_pgm = 55 and cal.amt_dpb_bns_calc < 0  then 'SM_DS_C' "+
" when cal.id_dpb_pgm = 141 and cal.amt_dpb_bns_calc >= 0 then 'SM_DS_D' 	"+
" when cal.id_dpb_pgm = 141 and cal.amt_dpb_bns_calc < 0  then 'SM_DS_C' "+
" when cal.id_dpb_pgm = 56 and cal.amt_dpb_bns_calc >= 0 then 'SM_FB_D'  "+
" when cal.id_dpb_pgm = 56 and cal.amt_dpb_bns_calc < 0  then 'SM_FB_C' "+
" when cal.id_dpb_pgm = 52 and cal.amt_dpb_bns_calc >= 0 then 'MBV_DR_D'  "+
" when cal.id_dpb_pgm = 52 and cal.amt_dpb_bns_calc < 0  then 'MBV_DR_C' 	"+
" when cal.id_dpb_pgm = 57 and cal.amt_dpb_bns_calc >= 0 then 'MBV_CE_D' "+
" when cal.id_dpb_pgm = 57 and cal.amt_dpb_bns_calc < 0  then 'MBV_CE_C'  	"+
" when cal.id_dpb_pgm = 58 and cal.amt_dpb_bns_calc >= 0 then 'MBV_BS_D'	"+
" when cal.id_dpb_pgm = 58 and cal.amt_dpb_bns_calc < 0  then 'MBV_BS_C' "+
" when cal.id_dpb_pgm = 59 and cal.amt_dpb_bns_calc >= 0 then 'MBV_DS_D'  "+
" when cal.id_dpb_pgm = 59 and cal.amt_dpb_bns_calc < 0  then 'MBV_DS_C'  "+
" when cal.id_dpb_pgm = 142 and cal.amt_dpb_bns_calc >= 0 then 'MB_APB_D'  "+  
" when cal.id_dpb_pgm = 142 and cal.amt_dpb_bns_calc < 0  then 'MB_APB_C'  "+ 
" when cal.id_dpb_pgm = 143 and cal.amt_dpb_bns_calc >= 0 then 'MB_AEB_D'  "+  
" when cal.id_dpb_pgm = 143 and cal.amt_dpb_bns_calc < 0  then 'MB_AEB_C'  "+
" 	end) as dbId,  "+
			   
" cal.amt_dpb_bns_calc, ub.num_po, "+
" (case  when cal.id_dpb_pgm in (54,57,60) then 'CUSTOMER EXPERIENCE BONUS'  "+
" when cal.id_dpb_pgm in (55,59,62) then 'NEW VEHICLE SALES BONUS' "+
" when cal.id_dpb_pgm in (51,52,53) then 'DLR RESERVE'  "+
" when cal.id_dpb_pgm in (58,61) then 'COMMERCIAL VEHICLE BRAND STANDARDS' "+
" when cal.id_dpb_pgm in (56)    then 'SMART FRANCHISE'  "+
" when cal.id_dpb_pgm in (141)    then 'BRAND STANDARDS'  "+
" when cal.id_dpb_pgm in (142)    then 'AMG PERFORMANCE BONUS'  "+
" when cal.id_dpb_pgm in (143)    then 'AMG ELITE PERFORMANCE BONUS'  "+
" 	 end) as text, ub.vehType "+
			 
" from dpb_calc cal, unblk2 ub where id_dpb_pgm in (51,52,54,55,56,57,58,59,53,60,61,62,141,142,143) and  "+
" cal.id_dpb_unblk_veh = ub.id_dpb_unblk_veh and cal.ind_dpb_adj='Y' and cal.dte_cal between ? and ? "+
"  order by vehType, id_dlr, num_po, id_dpb_pgm) ,  "+
 
" calc (id_dlr, id_dpb_pgm, dbId, amt_dpb_bns_calc,po,text,vehType) as	  "+
" (select  * from calc1 union all select * from calc2)  "+

" select id_dlr,vehType,id_dpb_pgm,dbId, amt_dpb_bns_calc,po,text from calc  "+
" order by vehType, id_dlr ,id_dpb_pgm for fetch only with ur ";

	
public static final String GET_DAILY_DEALER_INFO = " with unblk1 (unblk_id,dlr,vehType,po,vin) as  "+
" (select id_dpb_unblk_veh,id_dlr,cde_veh_typ,num_po,num_vin from dpb_unblk_veh "+
" where id_dpb_pgm in (121,123) and dte_rtl = ? ),  "+

"  unblk2 (unblk_id,dlr,vehType,po,vin) as "+
" (select id_dpb_unblk_veh,id_dlr,cde_veh_typ,num_po,num_vin from dpb_unblk_veh  "+
" where id_dpb_pgm in (122) and dte_rtl = ? ), "+

" calc1 (dlr,bonus_amount,vehType,pgm_id,po,vin) as	  "+
" (select cal.id_dlr, cal.amt_dpb_bns_calc, ub.vehType, cal.id_dpb_pgm , ub.po, ub.vin  "+
" from dpb_calc cal, unblk1 ub where id_dpb_pgm in (46,121,123) and    "+
" cal.id_dpb_unblk_veh = ub.unblk_id  order by vehType,id_dlr,id_dpb_pgm),  "+

"  calc2 (dlr,bonus_amount,vehType,pgm_id,po,vin) as	 "+
" (select cal.id_dlr, cal.amt_dpb_bns_calc, ub.vehType, cal.id_dpb_pgm , ub.po, ub.vin  "+
//" from dpb_calc cal, unblk2 ub where cal.id_dpb_pgm in (46,51,52,53,47,48,49,50,54,55,56,57,58,59,60,61,62) and   "+
" from dpb_calc cal, unblk2 ub where cal.id_dpb_pgm in (46,51,52,53,144,145,48,49,50,141,57,58,59,60,61,62) and   "+
" cal.id_dpb_unblk_veh = ub.unblk_id  order by vehType,id_dlr,id_dpb_pgm),  "+

" total1(dlr, total,vehType,vehcount,pgm_id,po,vin) as  "+
" (select dlr, sum(bonus_amount),vehType,count(dlr), pgm_id,po,vin from calc1 group by (vehType,dlr,pgm_id,po,vin )), "+

" total2(dlr, total,vehType,vehcount,pgm_id,po,vin) as  "+
" (select dlr, sum(bonus_amount),vehType,count(dlr), pgm_id,po,vin from calc2 group by (vehType,dlr,pgm_id,po,vin )),  "+

" temp1(vehType,dlr,vehcount,total,text,po,vin) as  "+
" (select vehType,dlr,sum(vehcount),sum(total), 'MBDEAL'  as text, po, vin  "+
" from total1  group by(vehType,dlr,po,vin)),  "+
					   
" temp2( vehType,dlr,vehcount,total,text,po,vin) as  "+
" (select vehType,dlr,sum(vehcount),sum(total),  'CVP' as text, po, vin   "+
" from total2  group by (vehType,dlr,po,vin))  "+

" select * from temp1 union all select * from temp2  order by dlr,po  for fetch only with ur ";

		

public static final String GET_DAILY_PAYMENT_INFO = "  with unblk1 (id_dpb_unblk_veh, id_dlr, num_po,Num_vin,vehType) as "+
"(select id_dpb_unblk_veh, id_dlr, num_po,Num_vin, cde_veh_typ from dpb_unblk_veh  "+
" where  id_dpb_pgm in (121,123) and dte_rtl = ?  ),  "+

" unblk2 (id_dpb_unblk_veh, id_dlr, num_po,Num_vin,vehType) as  "+
" (select id_dpb_unblk_veh, id_dlr, num_po,Num_vin, cde_veh_typ from dpb_unblk_veh  "+
" where  id_dpb_pgm in (122)   and dte_rtl = ?  ), "+
			 
" calc1 (id_dlr, id_dpb_pgm, dbId, amt_dpb_bns_calc,po,text,vehType) as  "+
" (select   cal.id_dlr, cal.id_dpb_pgm,  	  "+
" (case   when cal.id_dpb_pgm in (121,123) and cal.amt_dpb_bns_calc >= 0 then 'MBDEAL_MB_DSC_D'  "+
       " when cal.id_dpb_pgm in (121,123) and cal.amt_dpb_bns_calc < 0  then 'MBDEAL_MB_DSC_C' "+
      "  when cal.id_dpb_pgm = 46 and cal.amt_dpb_bns_calc >= 0 then 'MBDEAL_MB_FP_D' 	"+
	" when cal.id_dpb_pgm = 46 and cal.amt_dpb_bns_calc < 0  then 'MBDEAL_MB_FP_C'  "+
	" end) as dbId,  "+
			   
       " cal.amt_dpb_bns_calc, ub.num_po, "+
     "   (case when cal.amt_dpb_bns_calc >= 0 then 'MBDEAL'  "+
	" else 'MBDEAL_CANCEL' end) as text, ub.vehType  "+
" from dpb_calc cal, unblk1 ub where id_dpb_pgm in (46,121,123) and   "+
" cal.id_dpb_unblk_veh = ub.id_dpb_unblk_veh  order by vehType,id_dlr,num_po,id_dpb_pgm), "+

" calc2 (id_dlr, id_dpb_pgm, dbId, amt_dpb_bns_calc,po,text,vehType) as  "+
" (select   cal.id_dlr, cal.id_dpb_pgm,  	 "+

" (case  	when cal.id_dpb_pgm = 46 and cal.amt_dpb_bns_calc >= 0 then 'MB_CVP_FP_D' 	"+
	" when cal.id_dpb_pgm = 46 and cal.amt_dpb_bns_calc < 0  then 'MB_CVP_FP_C'  "+
//	" when cal.id_dpb_pgm = 47 and cal.amt_dpb_bns_calc >= 0 then 'MB_CVP_CE_D'  "+
//     "    when cal.id_dpb_pgm = 47 and cal.amt_dpb_bns_calc < 0  then 'MB_CVP_CE_C'  	"+
" when cal.id_dpb_pgm = 144 and cal.amt_dpb_bns_calc >= 0 then 'MB_CVP_CES_D'  "+
"    when cal.id_dpb_pgm = 144 and cal.amt_dpb_bns_calc < 0  then 'MB_CVP_CES_C'  	"+
" when cal.id_dpb_pgm = 145 and cal.amt_dpb_bns_calc >= 0 then 'MB_CVP_CEV_D'  "+
"    when cal.id_dpb_pgm = 145 and cal.amt_dpb_bns_calc < 0  then 'MB_CVP_CEV_C'  	"+
      "   when cal.id_dpb_pgm = 48 and cal.amt_dpb_bns_calc >= 0 then 'MB_CVP_NVS_D' 	"+
      "   when cal.id_dpb_pgm = 48 and cal.amt_dpb_bns_calc < 0  then 'MB_CVP_NVS_C' "+
	" when cal.id_dpb_pgm = 49 and cal.amt_dpb_bns_calc >= 0 then 'MB_CVP_POS_D'  	"+
     "   when cal.id_dpb_pgm = 49 and cal.amt_dpb_bns_calc < 0  then 'MB_CVP_POS_C' 	"+
      "   when cal.id_dpb_pgm = 50 and cal.amt_dpb_bns_calc >= 0 then 'MB_CVP_BS_D'  "+
      "   when cal.id_dpb_pgm = 50 and cal.amt_dpb_bns_calc <  0 then 'MB_CVP_BS_C'  "+
        
     "    when cal.id_dpb_pgm = 51 and cal.amt_dpb_bns_calc >= 0 then 'SM_CVP_DR_D'  "+
	" when cal.id_dpb_pgm = 51 and cal.amt_dpb_bns_calc < 0  then 'SM_CVP_DR_C' 	"+
	" when cal.id_dpb_pgm = 52 and cal.amt_dpb_bns_calc >= 0 then 'MBV_DR_D'  "+
	" when cal.id_dpb_pgm = 52 and cal.amt_dpb_bns_calc < 0  then 'MBV_DR_C' 	"+
	" when  cal.id_dpb_pgm = 53 and cal.amt_dpb_bns_calc >= 0 then 'DVU_DR_D' "+
	" when cal.id_dpb_pgm = 53 and cal.amt_dpb_bns_calc < 0 then  'DVU_DR_C'	 "+
	      
     "   when cal.id_dpb_pgm = 54 and cal.amt_dpb_bns_calc >= 0 then 'SM_CVP_CE_D' 	"+
	" when cal.id_dpb_pgm = 54 and cal.amt_dpb_bns_calc < 0  then 'SM_CVP_CE_C'  "+
	" when cal.id_dpb_pgm = 55 and cal.amt_dpb_bns_calc >= 0 then 'SM_CVP_DS_D' 	"+
	" when cal.id_dpb_pgm = 55 and cal.amt_dpb_bns_calc < 0  then 'SM_CVP_DS_C'  "+
	" when cal.id_dpb_pgm = 141 and cal.amt_dpb_bns_calc >= 0 then 'SM_CVP_DS_D' 	"+
	" when cal.id_dpb_pgm = 141 and cal.amt_dpb_bns_calc < 0  then 'SM_CVP_DS_C'  "+
	" when cal.id_dpb_pgm = 56 and cal.amt_dpb_bns_calc >= 0 then 'SM_CVP_FB_D'  "+
	" when cal.id_dpb_pgm = 56 and cal.amt_dpb_bns_calc < 0  then 'SM_CVP_FB_C'  "+
	
	" when cal.id_dpb_pgm = 57 and cal.amt_dpb_bns_calc >= 0 then 'MBV_CE_D'  "+
	" when cal.id_dpb_pgm = 57 and cal.amt_dpb_bns_calc < 0  then 'MBV_CE_C'  	"+
	" when cal.id_dpb_pgm = 58 and cal.amt_dpb_bns_calc >= 0 then 'MBV_BS_D'	"+
	" when cal.id_dpb_pgm = 58 and cal.amt_dpb_bns_calc < 0  then 'MBV_BS_C'  "+
	" when cal.id_dpb_pgm = 59 and cal.amt_dpb_bns_calc >= 0 then 'MBV_DS_D'  "+
	" when cal.id_dpb_pgm = 59 and cal.amt_dpb_bns_calc < 0  then 'MBV_DS_C'  "+
	 
	" when cal.id_dpb_pgm = 60 and cal.amt_dpb_bns_calc >= 0 then 'DVU_CE_D' "+
	" when cal.id_dpb_pgm = 60 and cal.amt_dpb_bns_calc < 0  then 'DVU_CE_C'  "+
    " when cal.id_dpb_pgm = 61 and cal.amt_dpb_bns_calc >= 0 then 'DVU_BS_D'  "+
	" when cal.id_dpb_pgm = 61 and cal.amt_dpb_bns_calc < 0  then 'DVU_BS_C'  "+
	" when cal.id_dpb_pgm = 62 and cal.amt_dpb_bns_calc >= 0 then 'DVU_DS_D'  "+
	" when cal.id_dpb_pgm = 62 and cal.amt_dpb_bns_calc < 0  then 'DVU_DS_C'   "+
     "   end) as dbId,  "+
			   
     "   cal.amt_dpb_bns_calc, ub.num_po,  "+
       " (case when cal.amt_dpb_bns_calc >= 0 then 'CVP'  else 'CVP_CANCEL' end) as text, ub.vehType  "+
			 
// " from dpb_calc cal, unblk2 ub where id_dpb_pgm in (46,47,48,49,50,54,55,56,57,58,59,60,61,62,51,52,53) and   "+
//" from dpb_calc cal, unblk2 ub where id_dpb_pgm in (46,144,145,48,49,50,54,55,141,56,57,58,59,60,61,62,51,52,53) and   "+
" from dpb_calc cal, unblk2 ub where id_dpb_pgm in (46,144,145,48,49,50,141,57,58,59,60,61,62,51,52,53) and   "+
 " cal.id_dpb_unblk_veh = ub.id_dpb_unblk_veh  order by vehType,id_dlr, id_dpb_pgm ),  "+
 
" calc (id_dlr, id_dpb_pgm, dbId, amt_dpb_bns_calc,po,text,vehType) as  "+
 "(select * from calc1 union all select * from calc2 order by id_dlr,po)  "+
					
" select * from calc for fetch only with ur ";
					 

public static final String GET_PROCID_INFO = "select id_dpb_proc  from dpb_proc " +
		"where cde_dpb_proc_typ in ('FP','VP','CP') and dte_cal = current date ";

			
public static final String GET_DAILY_CALCID_INFO = "with unblk1 (id_dpb_unblk_veh) as (select id_dpb_unblk_veh from dpb_unblk_veh  " +
" where  id_dpb_pgm in (121,123) and dte_rtl = ?), " +

" unblk2 (id_dpb_unblk_veh) as  (select id_dpb_unblk_veh from dpb_unblk_veh  " +
" where  id_dpb_pgm in (122)   and dte_rtl = ?),  " +
			 
" calc1 (id_dpb_calc) as (select   cal.id_dpb_calc  	 " +
" from dpb_calc cal, unblk1 ub where id_dpb_pgm in (46,121,123) and   " +
" cal.id_dpb_unblk_veh = ub.id_dpb_unblk_veh ),  " +

" calc2 (id_dpb_calc) as (select   cal.id_dpb_calc  " +
//" from dpb_calc cal, unblk2 ub where id_dpb_pgm in (46,47,48,49,50,54,55,56,57,58,59,60,61,62,51,52,53) and   " +
" from dpb_calc cal, unblk2 ub where id_dpb_pgm in (46,47,144,145,48,49,50,54,55,141,56,57,58,59,60,61,62,51,52,53) and   " +
" cal.id_dpb_unblk_veh = ub.id_dpb_unblk_veh)  " +
 
" select * from calc1 union all select * from calc2   " ;


public static final String GET_CALCID_INFO =" with unblk1 (unblk_id) as  "+
" (select id_dpb_unblk_veh from dpb_unblk_veh  "+
" where  cde_veh_typ in (?) and id_dpb_pgm is null and "+
" dte_rtl between ? and ? ),  "+

" unblk2 (unblk_id) as  "+
" (select id_dpb_unblk_veh from dpb_unblk_veh "+
" where  cde_veh_typ in (?) and id_dpb_pgm is null  and "+
" dte_rtl between '2013-04-01' and ?), "+

" calc1 (id_dpb_calc) as	"+
" (select cal.id_dpb_calc   "+
" from dpb_calc cal, unblk1 ub where id_dpb_pgm in (51,52,53,54,55,141,56,57,58,59,60,61,62) and  "+
" cal.id_dpb_unblk_veh = ub.unblk_id ),  "+

" calc2 (id_dpb_calc) as	 "+
" (select cal.id_dpb_calc  "+
" from dpb_calc cal, unblk2 ub where id_dpb_pgm in (51,52,53,54,55,141,56,57,58,59,60,61,62) and   "+
" cal.id_dpb_unblk_veh = ub.unblk_id  and cal.ind_dpb_adj='Y' and cal.dte_cal >= ? ) "+

" select  * from calc1 union all select * from calc2 ";


public static final String GET_CARS_CALCID_INFO = " with unblk1 (unblk_id) as 	"+
		" (select id_dpb_unblk_veh from dpb_unblk_veh "+
		" where  cde_veh_typ in ('P') and  id_dpb_pgm is null    "+
		" and dte_rtl between  ? and ? ),  "+
								 
		" unblk2 (unblk_id) as 	"+
		" (select id_dpb_unblk_veh from dpb_unblk_veh "+
		" where  cde_veh_typ in ('P') and  id_dpb_pgm in ( 121,123)  "+
		" and dte_rtl between  ? and ? ),  "+

		" unblk3 (unblk_id) as 	"+
		" (select id_dpb_unblk_veh from dpb_unblk_veh "+
		" where  cde_veh_typ in ('P') and  id_dpb_pgm is null    "+
		" and dte_rtl between  '2013-04-01' and ? ),  "+

		" unblk4 (unblk_id) as 	"+
		" (select id_dpb_unblk_veh from dpb_unblk_veh "+
		" where  cde_veh_typ in ('P') and  id_dpb_pgm in ( 121,123)  "+
		" and dte_rtl between  '2013-04-01' and ? ),  "+


		" calc1 (id_dpb_calc) as	"+
		" (select cal.id_dpb_calc  "+
//		 " from dpb_calc cal, unblk1 ub where id_dpb_pgm in (46,47,48,49,50) and   "+
		" from dpb_calc cal, unblk1 ub where id_dpb_pgm in (46,47,144,145,48,49,50) and   "+
		" cal.id_dpb_unblk_veh = ub.unblk_id ), "+

		 " calc2 (id_dpb_calc) as	 "+
		" (select cal.id_dpb_calc  "+
//		" from dpb_calc cal, unblk2 ub where id_dpb_pgm in (47,48,49,50) and   "+
		" from dpb_calc cal, unblk2 ub where id_dpb_pgm in (47,144,145,48,49,50) and   "+
		 " cal.id_dpb_unblk_veh = ub.unblk_id ),  "+

		" calc3 (id_dpb_calc) as	"+
		" (select cal.id_dpb_calc  "+
//		 " from dpb_calc cal, unblk3 ub where id_dpb_pgm in (46,47,48,49,50) and   "+
		" from dpb_calc cal, unblk3 ub where id_dpb_pgm in (46,47,144,145,48,49,50) and   "+
		" cal.id_dpb_unblk_veh = ub.unblk_id  and cal.ind_dpb_adj='Y' and cal.dte_cal >= ? ) , " + 

		" calc4 (id_dpb_calc) as	 "+
		" (select cal.id_dpb_calc  "+
//		" from dpb_calc cal, unblk4 ub where id_dpb_pgm in (47,48,49,50) and   "+
		" from dpb_calc cal, unblk4 ub where id_dpb_pgm in (47,144,145,48,49,50) and   "+
		 " cal.id_dpb_unblk_veh = ub.unblk_id   and cal.ind_dpb_adj='Y' and cal.dte_cal >= ? ) " +
		 
		" select  * from calc1 union all select * from calc2 union all select  * from calc3 union all select * from calc4  ";


public static final String GET_AMGCARS_CALCID_INFO = " with unblk1 (unblk_id) as 	"+
		" (select id_dpb_unblk_veh from dpb_unblk_veh "+
		" where  cde_veh_typ in ('P') and  id_dpb_pgm is null    "+
		" and dte_rtl between  ? and ? ),  "+

		" unblk3 (unblk_id) as 	"+
		" (select id_dpb_unblk_veh from dpb_unblk_veh "+
		" where  cde_veh_typ in ('P') and  id_dpb_pgm is null    "+
		" and dte_rtl between  '2013-04-01' and ? ),  "+

		" calc1 (id_dpb_calc) as	"+
		" (select cal.id_dpb_calc  "+
		" from dpb_calc cal, unblk1 ub where id_dpb_pgm in (142,143) and   "+
		" cal.id_dpb_unblk_veh = ub.unblk_id ), "+

		" calc3 (id_dpb_calc) as	"+
		" (select cal.id_dpb_calc  "+
		" from dpb_calc cal, unblk3 ub where id_dpb_pgm in (142,143) and   "+
		" cal.id_dpb_unblk_veh = ub.unblk_id  and cal.ind_dpb_adj='Y' and cal.dte_cal >= ? ) " + 
		 
		" select * from calc1 union all select * from calc3 for fetch only with ur";

					
public static final String UDPATE_PAYMENT_STATUS = "update DPB_CALC set CDE_DPB_CALC_STS = 'BP', ID_LAST_UPDT_USER = 'SYSTEM', " +
													" DTS_LAST_UPDT = current timestamp   where ID_DPB_CALC = ?";



public static final String LDRSHIP_INFO = "with unblk (dlr,yr,qtr) as (Select ID_DLR, '2014' as yr, "+
"(case   when DTE_RTL between '2014-04-01' and '2014-06-30' then '1'  "+
  "      when DTE_RTL between '2014-07-01' and '2014-09-30' then '2'  "+
  " 	when DTE_RTL between '2014-10-01' and '2015-01-05' then '3'  "+
  " else '4'  end ) as QTR from DPB_UNBLK_VEH  calc  "+
  " where   DTE_RTL between '2014-04-01' and '2015-03-31'  "+
  "  and CDE_VEH_TYP = 'P'  and IND_USED_VEH_DDRS = 'N'     "+
  " and (calc.CDE_VEH_DDR_USE = 'L4' and DTE_RTL >'2014-06-30' or calc.CDE_VEH_DDR_USE <> 'L4') "+
  "  and CDE_VEH_DDR_STS = 'I1' and char(DTE_RTL)||char(tme_rtl) = ( select max(char(DTE_RTL)||char(tme_rtl)) from DPB_UNBLK_VEH b  "+
  "  where calc.NUM_VIN = b.NUM_VIN  and  DTE_RTL between '2014-04-01' and '2015-03-31')  order by id_dlr, DTE_RTL , qtr ), "+
  
 
 "  ldr ( dlr,yr,qtr,ind) as ( Select x.ID_DLR,  x.NUM_CAL_YR,x.NUM_CAL_QTR,  "+
 "  case when y.IND_DPB_LDR is not null then y.IND_DPB_LDR else 'N' end  "+
 "  from (Select distinct ID_DLR ,NUM_CAL_YR,NUM_CAL_QTR from DPBUSER.DPB_UNBLK_VEH calc, DPBUSER.DAY_FDRT b  "+
 "  where  b.DTE_CAL between  '2014-01-01' and '2014-12-31') as x   "+
 "  left join DPBUSER.DPB_LDR_QTR_FDRT y on x.id_dlr = y.id_dlr and y.DTE_YR = x.NUM_CAL_YR   "+
 "  and y.CDE_DLR_FRAN_TYP = 'MB'and y.DTE_QTR = x.NUM_CAL_QTR),  "+
      
 "  unblk2(dlr, qtr,dlr2,qtr2,ind) as ( select a.dlr,a.qtr,b.dlr,b.qtr,b.ind  from     "+
 "  unblk  a  full join ldr b on a.dlr = b.dlr and a.yr = b.yr and  a.qtr = b.qtr ),   "+

 "  unblk3 ( dlr, total) as ( select dlr,count(dlr) from unblk2   where ind = 'Y' group by dlr ) "+
   
 "  Select * from unblk3 ";



public static final String LDRSHIP_INFO2 = "with unblk (dlr,yr,qtr) as (Select ID_DLR, '2014' as yr, "+
"(case   when DTE_RTL between '2014-01-03' and '2014-03-31' then '1'  "+
  "      when DTE_RTL between '2014-04-01' and '2014-06-30' then '2'  "+
  " 	when DTE_RTL between '2014-07-01' and '2014-09-30' then '3'  "+
  " else '4'  end ) as QTR from DPB_UNBLK_VEH  calc  "+
  " where   DTE_RTL between '2014-01-03' and '2015-01-05'   "+
  "  and CDE_VEH_TYP = 'V'  and IND_USED_VEH_DDRS = 'N'      "+
  //" and id_dlr in ( 02106,04102,05201,14154,34111,51156,55133,72110,72111,78103,86107) "+
  
  "  and CDE_VEH_DDR_STS = 'I1' and char(DTE_RTL)||char(tme_rtl) = ( select max(char(DTE_RTL)||char(tme_rtl)) from DPB_UNBLK_VEH b  "+
  "  where calc.NUM_VIN = b.NUM_VIN  and  DTE_RTL between '2014-01-03' and '2015-01-05' )  order by id_dlr, DTE_RTL , qtr ), "+
  
 
 "  ldr ( dlr,yr,qtr,ind) as ( Select x.ID_DLR,  x.NUM_CAL_YR,x.NUM_CAL_QTR,  "+
 "  case when y.IND_DPB_LDR is not null then y.IND_DPB_LDR else 'N' end  "+
 "  from (Select distinct ID_DLR ,NUM_CAL_YR,NUM_CAL_QTR from DPBUSER.DPB_UNBLK_VEH calc, DPBUSER.DAY_FDRT b  "+
 "  where  b.DTE_CAL between  '2014-01-01' and '2014-12-31') as x   "+
 "  left join DPBUSER.DPB_LDR_QTR_FDRT y on x.id_dlr = y.id_dlr and y.DTE_YR = x.NUM_CAL_YR   "+
 "  and y.CDE_DLR_FRAN_TYP = 'SP' and y.DTE_QTR = x.NUM_CAL_QTR),  "+
      
 "  unblk2(dlr, qtr,dlr2,qtr2,ind) as ( select a.dlr,a.qtr,b.dlr,b.qtr,b.ind  from     "+
 "  unblk  a  full join ldr b on a.dlr = b.dlr and a.yr = b.yr and  a.qtr = b.qtr ),   "+

 "  unblk3 ( dlr, total) as ( select dlr,count(dlr) from unblk2   where ind = 'Y' group by dlr ) "+
   
 "  Select * from unblk3  where dlr is not null";

//Dealer Compliance Quarterly Payouts Report  - Start
public static final String DCQPR_FTL_QUERY_COMMON = "with DPBDLR(IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) \r\n" + 
		"AS (select DISTINCT dlr.ID_DLR,dlr.CDE_RGN,dlr.CDE_MKT,dlr.CDE_MTRO_MKT_GRP,dlr.ADR_PRIM_CITY,dlr.CDE_DLR_ST\r\n" + 
		"from DEALER_FDRT dlr  \r\n" + 
		"OLDPAYOUT_DEALER_RANGE_FOR_RPRT  \r\n" + 
		"),\r\n" + 
		"DAYFDRTTBL(DTECAL_MIN,DTECAL_MAX,NUMRETLQTR,YEARS ) as (\r\n" + 
		"select min(fdr.DTE_CAL),max(fdr.DTE_CAL),fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR \r\n" + 
		"from DPB_DAY fdr \r\n" + 
		"where (fdr.NUM_RETL_QTR,fdr.NUM_RETL_YR) in (select NUM_RETL_QTR,dim.NUM_RETL_YR from DPB_DAY dim\r\n" + 
		"     DATE_RANGE_FOR_REPORTS   )\r\n" + 
		"group by fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR),\r\n" + 
		"\r\n" + 
		"DLRPGMCALC(IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND,ID_DPB_UNBLK_VEH,NUMRETLQTR,YEARS,NUM_PO,VEH_TYP) as (\r\n" + 
		"select calc.ID_DLR,calc.ID_DPB_PGM,calc.AMT_DPB_BNS_CALC, calc.AMT_DPB_UNERND,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,dyfdrt.NUMRETLQTR,dyfdrt.YEARS, RTL.NUM_PO,rtl.CDE_VEH_TYP\r\n" + 
		"from DAYFDRTTBL dyfdrt\r\n" + 
		"join DPB_UNBLK_VEH rtl on rtl.DTE_RTL between dyfdrt.DTECAL_MIN and dyfdrt.DTECAL_MAX \r\n" + 
		"     VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"join DPB_CALC calc on calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    AND calc.ID_LDRSP_BNS_PGM IS NULL \r\n" + 
		"    --AND calc.ID_DPB_PGM IN (47,48,49,50,54,55,56,57,58,59,60,61,62)\r\n" + 
		"    AND calc.ID_DPB_PGM IN (60,62,61)\r\n" + 
		" DEALER_RANGE_FOR_REPORTS\r\n" + 
		"),\r\n" + 
		"veh_data (ID_DLR, NUM_PO, ID_DPB_UNBLK_VEH) as (\r\n" + 
		"select IDDLRCALC, NUM_PO, max(ID_DPB_UNBLK_VEH)\r\n" + 
		"from DLRPGMCALC\r\n" + 
		"group by IDDLRCALC, NUM_PO\r\n" + 
		"),\r\n" + 
		"VEHCOUNT(ID_DLR,NUM_PO,ID_DPB_UNBLK_VEH,TOTALAMTMSRPOPTS) as (\r\n" + 
		"select rtl.ID_DLR, rtl.num_po,rtl.ID_DPB_UNBLK_VEH, sum(rtl.AMT_MSRP_TOT_ACSRY+rtl.AMT_MSRP_BASE) from veh_data vd\r\n" + 
		"join DPB_UNBLK_VEH rtl\r\n" + 
		"on rtl.ID_DLR = vd.ID_DLR and rtl.ID_DPB_UNBLK_VEH = vd.ID_DPB_UNBLK_VEH\r\n" + 
		"and rtl.num_po = vd.NUM_PO and rtl.CDE_VEH_DDR_STS != 'I2' \r\n" + 
		"group by rtl.ID_DLR,rtl.num_po,rtl.ID_DPB_UNBLK_VEH),\r\n" + 
		"DPBCALCPOCOUNT(TOTALAMTMSRPOPTS) as \r\n" + 
		"(select sum(TOTALAMTMSRPOPTS) from VEHCOUNT cnt),\r\n" + 
		"PO_COUNT(ID_DLR,CY13RetailSales) as (\r\n" + 
		"select ID_DLR, count(NUM_PO) from VEHCOUNT group by ID_DLR\r\n" + 
		"),\r\n" + 
		"DLRPGMCALC_FINAL (IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, NUMRETLQTR,YEARS,VEH_TYP) as (\r\n" + 
		"select calc.IDDLRCALC,calc.ID_DPB_PGM,sum(calc.SUM_AMT_DPB_BNS_CALC),sum(calc.SUM_AMT_DPB_UNERND),calc.NUMRETLQTR,\r\n" + 
		"calc.YEARS,VEH_TYP\r\n" + 
		"from VEHCOUNT veh\r\n" + 
		"left outer join DLRPGMCALC calc on veh.ID_DLR = calc.IDDLRCALC\r\n" + 
		"and veh.NUM_PO = calc.NUM_PO\r\n" + 
		"group by calc.IDDLRCALC,calc.ID_DPB_PGM,calc.NUMRETLQTR,calc.YEARS,VEH_TYP\r\n" + 
		"),\r\n" + 
		"LST_KPI (LST_UPDT,ID_DLR,QTR,YR,ID_KPI ) AS (\r\n" + 
		"SELECT MAX(ID_KPI_FIL_EXTRT), dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI \r\n" + 
		"from DLRPGMCALC_FINAL po\r\n" + 
		"\r\n" + 
		"left outer join DPB_KPI_FIL_EXTRT dlrkpi on \r\n" + 
		"po.IDDLRCALC = dlrkpi.ID_DLR and po.NUMRETLQTR = dlrkpi.DTE_FSCL_QTR\r\n" + 
		"AND PO.YEARS = DLRKPI.DTE_FSCL_YR\r\n" + 
		"group by dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"PGM_DATA (IDDLR,Dealer_Standards,Sales_Effective,MB_CSI,Dealer_Standards_AMT,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,SUM_AMT_DPB_UNERND_AMT,VEH_TYP,CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) as (\r\n" + 
		"select\r\n" + 
		"dlr.IDDLR,\r\n" + 
		"max(case when (kpi.ID_KPI =14000 and ID_DPB_PGM = 60 and VEH_TYP = 'F') then \r\n" + 
		"kpi.PCT_KPI end) as Dealer_Standards, \r\n" + 
		"MAX(case when (kpi.ID_KPI =15000 and ID_DPB_PGM = 62 and VEH_TYP = 'F') then \r\n" + 
		"                      kpi.PCT_KPI  end) as Sales_Effective,\r\n" + 
		"MAX(case when (kpi.ID_KPI =13000 and ID_DPB_PGM = 61 and VEH_TYP = 'F') then \r\n" + 
		"                      kpi.PCT_KPI end) as MB_CSI,\r\n" + 
		"Max(case when ( ID_DPB_PGM  = 60 and VEH_TYP = 'F') then \r\n" + 
		"SUM_AMT_DPB_BNS_CALC end) as Dealer_Standards_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 62 and VEH_TYP = 'F') then SUM_AMT_DPB_BNS_CALC end) as Sales_Effective_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 61 and VEH_TYP = 'F') then SUM_AMT_DPB_BNS_CALC end) as MB_CSI_AMT,\r\n" + 
		"MAX(SUM_AMT_DPB_UNERND) AS SUM_AMT_DPB_UNERND_AMT,\r\n" + 
		"VEH_TYP,pc.CY13RetailSales,\r\n" + 
		"coalesce(REGN,''),coalesce(MKT,''),coalesce(T55,''),coalesce(ADR_DLR_CTY,''),coalesce(ADR_DLR_STA,'')\r\n" + 
		"                      \r\n" + 
		"                       \r\n" + 
		"from DLRPGMCALC_FINAL  f \r\n" + 
		"join LST_KPI lst_kpi on lst_kpi.ID_DLR = IDDLRCALC\r\n" + 
		"join DPB_KPI_FIL_EXTRT kpi on lst_kpi.LST_UPDT = kpi.ID_KPI_FIL_EXTRT\r\n" + 
		"join PO_COUNT pc on pc.ID_DLR = f.IDDLRCALC\r\n" + 
		"right outer join DPBDLR dlr on f.IDDLRCALC = dlr.IDDLR\r\n" + 
		"group by \r\n" + 
		"dlr.IDDLR\r\n" + 
		",VEH_TYP,pc.CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA),\r\n" + 
		"\r\n" + 
		"TOTALCY13RetailSales(TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_SUM_AMT_DPB_UNERND_AMT) AS \r\n" + 
		"(SELECT SUM(CY13RetailSales),SUM(Dealer_Standards_AMT),SUM(Sales_Effective_AMT),\r\n" + 
		"SUM(MB_CSI_AMT),SUM(SUM_AMT_DPB_UNERND_AMT)  \r\n" + 
		"FROM PGM_DATA),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PGM_DATA2 (Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as (\r\n" + 
		"select\r\n" + 
		"max(case when cal.ID_DPB_PGM in (60)  then NAM_DPB_PGM end) as Dealer_Standards_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (62)  then NAM_DPB_PGM end) as Sales_Effective_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (61)  then NAM_DPB_PGM end) as Brand_stand_name,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 60 and VEH_TYP = 'F') then PCT_VAR_PMT end) as Dealer_Standards,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 62 and VEH_TYP = 'F') then PCT_VAR_PMT end) as Sales_Effective,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 61 and VEH_TYP = 'F') then PCT_VAR_PMT end) as MB_CSI,\r\n" + 
		"NUMRETLQTR,cal.YEARS\r\n" + 
		"from DLRPGMCALC  cal \r\n" + 
		"join DPB_PGM pgm on cal.ID_DPB_PGM = pgm.ID_DPB_PGM\r\n" + 
		"group by NUMRETLQTR,YEARS\r\n" + 
		"),\r\n" + 
		"REPORT_DATA3 (IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"TOT_CY13RetailSales,Dealer_Standards_AMT,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_UNERND_AMT,TOT_EFF) as (\r\n" + 
		"select IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"coalesce(CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')) ,\r\n" + 
		"coalesce(TOTAL_CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_MB_CSI_AMT,0),'999,999,990.99')) ,\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')) ,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0)+coalesce(Sales_Effective,0)+coalesce(MB_CSI,0),'90.99') || '%'\r\n" + 
		"from PGM_DATA pgm,\r\n" + 
		"TOTALCY13RetailSales\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"FINAL_DATA2(Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as (\r\n" + 
		"select trim(coalesce(Dealer_Standards_name,'')),\r\n" + 
		"trim(coalesce(Sales_Effective_name,'')), trim(coalesce(Brand_stand_name,'')),\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"case when NUMRETLQTR=1 then '1st' else case when NUMRETLQTR=2 then '2nd' \r\n" + 
		"else case when NUMRETLQTR=3 then '3rd' else '4th' end end end as NUM_RETL_QTR,YEARS\r\n" + 
		"from PGM_DATA2\r\n" + 
		"),  \r\n" + 
		"REPORT_DATA (Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"TOT_CY13RetailSales) as (\r\n" + 
		"select\r\n" + 
		"varchar_format(Float(avg(coalesce(Dealer_Standards,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(Sales_Effective,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(MB_CSI,0))),'90.99'),\r\n" + 
		"sum(coalesce(CY13RetailSales,0))\r\n" + 
		"from PGM_DATA pgm\r\n" + 
		"),\r\n" + 
		"FINAL_DATA (Dealer_Standards,Sales_Effective,MB_CSI,TOTAL_MSRP) as (\r\n" + 
		"select Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"varchar_format((select TOTALAMTMSRPOPTS from DPBCALCPOCOUNT)/TOT_CY13RetailSales,'999,999,999,990.99')\r\n" + 
		"from REPORT_DATA\r\n" + 
		"),\r\n" + 
		"Final_Result(ROWNUM,Dealer_Standards,Sales_Effective,MB_CSI,TOTAL_MSRP,Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,\r\n" +
		"Dealer_Standards2, Sales_Effective2,MB_CSI2,NUMRETLQTR,YEARS,IDDLR,REGN,MKT,ADR_DLR_CTY,ADR_DLR_STA,TOT_CY13RetailSales,Dealer_Standards3,\r\n" +
		"Sales_Effective3,MB_CSI3,TOT_EFF,Dealer_Standards_AMT,Sales_Effective_AMT,MB_CSI_AMT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" +
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_UNERND_AMT) as \r\n"+
		"(select ROW_NUMBER() OVER (ORDER BY iddlr ASC) AS ROWNUM,FD.Dealer_Standards,FD.Sales_Effective,FD.MB_CSI,FD.TOTAL_MSRP,\r\n" + 
		"FD2.Dealer_Standards_name,FD2.Sales_Effective_name,FD2.Brand_stand_name,\r\n" + 
		"FD2.Dealer_Standards as Dealer_Standards2,FD2.Sales_Effective as Sales_Effective2,FD2.MB_CSI as MB_CSI2,FD2.NUMRETLQTR,FD2.YEARS,RD3.IDDLR,RD3.REGN,RD3.MKT,RD3.ADR_DLR_CTY,RD3.ADR_DLR_STA,RD3.TOT_CY13RetailSales,\r\n" + 
		"RD3.Dealer_Standards as Dealer_Standards3,RD3.Sales_Effective as Sales_Effective3,RD3.MB_CSI as MB_CSI3,TOT_EFF,\r\n" + 
		"RD3.Dealer_Standards_AMT,RD3.Sales_Effective_AMT,\r\n" + 
		"RD3.MB_CSI_AMT,RD3.UNERND_AMT,RD3.TOTAL_CY13RetailSales,RD3.TOT_Dealer_Standards_AMT,\r\n" + 
		"RD3.TOT_Sales_Effective_AMT,RD3.TOT_MB_CSI_AMT,RD3.TOT_UNERND_AMT from FINAL_DATA FD,FINAL_DATA2 FD2,REPORT_DATA3 RD3\r\n" + 
		")";

public static final String DCQPR_FTL_QUERY = "with DPBDLR(IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) \r\n" + 
		"AS (select DISTINCT dlr.ID_DLR,dlr.CDE_RGN,dlr.CDE_MKT,dlr.CDE_MTRO_MKT_GRP,dlr.ADR_PRIM_CITY,dlr.CDE_DLR_ST\r\n" + 
		"from DEALER_FDRT dlr  \r\n" + 
		"OLDPAYOUT_DEALER_RANGE_FOR_RPRT  \r\n" + 
		"),\r\n" + 
		"DAYFDRTTBL(DTECAL_MIN,DTECAL_MAX,NUMRETLQTR,YEARS ) as (\r\n" + 
		"select min(fdr.DTE_CAL),max(fdr.DTE_CAL),fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR \r\n" + 
		"from DPB_DAY fdr \r\n" + 
		"where (fdr.NUM_RETL_QTR,fdr.NUM_RETL_YR) in (select NUM_RETL_QTR,dim.NUM_RETL_YR from DPB_DAY dim\r\n" + 
		"     DATE_RANGE_FOR_REPORTS   )\r\n" + 
		"group by fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR),\r\n" + 
		"\r\n" + 
		"DLRPGMCALC(IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND,ID_DPB_UNBLK_VEH,NUMRETLQTR,YEARS,NUM_PO,VEH_TYP) as (\r\n" + 
		"select calc.ID_DLR,calc.ID_DPB_PGM,calc.AMT_DPB_BNS_CALC, calc.AMT_DPB_UNERND,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,dyfdrt.NUMRETLQTR,dyfdrt.YEARS, RTL.NUM_PO,rtl.CDE_VEH_TYP\r\n" + 
		"from DAYFDRTTBL dyfdrt\r\n" + 
		"join DPB_UNBLK_VEH rtl on rtl.DTE_RTL between dyfdrt.DTECAL_MIN and dyfdrt.DTECAL_MAX \r\n" + 
		"     VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"join DPB_CALC calc on calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    AND calc.ID_LDRSP_BNS_PGM IS NULL \r\n" + 
		"    --AND calc.ID_DPB_PGM IN (47,48,49,50,54,55,56,57,58,59,60,61,62)\r\n" + 
		"    AND calc.ID_DPB_PGM IN (60,62,61)\r\n" + 
		" DEALER_RANGE_FOR_REPORTS\r\n" + 
		"),\r\n" + 
		"veh_data (ID_DLR, NUM_PO, ID_DPB_UNBLK_VEH) as (\r\n" + 
		"select IDDLRCALC, NUM_PO, max(ID_DPB_UNBLK_VEH)\r\n" + 
		"from DLRPGMCALC\r\n" + 
		"group by IDDLRCALC, NUM_PO\r\n" + 
		"),\r\n" + 
		"VEHCOUNT(ID_DLR,NUM_PO,ID_DPB_UNBLK_VEH,TOTALAMTMSRPOPTS) as (\r\n" + 
		"select rtl.ID_DLR, rtl.num_po,rtl.ID_DPB_UNBLK_VEH, sum(rtl.AMT_MSRP_TOT_ACSRY+rtl.AMT_MSRP_BASE) from veh_data vd\r\n" + 
		"join DPB_UNBLK_VEH rtl\r\n" + 
		"on rtl.ID_DLR = vd.ID_DLR and rtl.ID_DPB_UNBLK_VEH = vd.ID_DPB_UNBLK_VEH\r\n" + 
		"and rtl.num_po = vd.NUM_PO and rtl.CDE_VEH_DDR_STS != 'I2' \r\n" + 
		"group by rtl.ID_DLR,rtl.num_po,rtl.ID_DPB_UNBLK_VEH),\r\n" + 
		"DPBCALCPOCOUNT(TOTALAMTMSRPOPTS) as \r\n" + 
		"(select sum(TOTALAMTMSRPOPTS) from VEHCOUNT cnt),\r\n" + 
		"PO_COUNT(ID_DLR,CY13RetailSales) as (\r\n" + 
		"select ID_DLR, count(NUM_PO) from VEHCOUNT group by ID_DLR\r\n" + 
		"),\r\n" + 
		"DLRPGMCALC_FINAL (IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, NUMRETLQTR,YEARS,VEH_TYP) as (\r\n" + 
		"select calc.IDDLRCALC,calc.ID_DPB_PGM,sum(calc.SUM_AMT_DPB_BNS_CALC),sum(calc.SUM_AMT_DPB_UNERND),calc.NUMRETLQTR,\r\n" + 
		"calc.YEARS,VEH_TYP\r\n" + 
		"from VEHCOUNT veh\r\n" + 
		"left outer join DLRPGMCALC calc on veh.ID_DLR = calc.IDDLRCALC\r\n" + 
		"and veh.NUM_PO = calc.NUM_PO\r\n" + 
		"group by calc.IDDLRCALC,calc.ID_DPB_PGM,calc.NUMRETLQTR,calc.YEARS,VEH_TYP\r\n" + 
		"),\r\n" + 
		"LST_KPI (LST_UPDT,ID_DLR,QTR,YR,ID_KPI ) AS (\r\n" + 
		"SELECT MAX(ID_KPI_FIL_EXTRT), dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI \r\n" + 
		"from DLRPGMCALC_FINAL po\r\n" + 
		"\r\n" + 
		"left outer join DPB_KPI_FIL_EXTRT dlrkpi on \r\n" + 
		"po.IDDLRCALC = dlrkpi.ID_DLR and po.NUMRETLQTR = dlrkpi.DTE_FSCL_QTR\r\n" + 
		"AND PO.YEARS = DLRKPI.DTE_FSCL_YR\r\n" + 
		"group by dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"PGM_DATA (IDDLR,Dealer_Standards,Sales_Effective,MB_CSI,Dealer_Standards_AMT,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,SUM_AMT_DPB_UNERND_AMT,VEH_TYP,CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) as (\r\n" + 
		"select\r\n" + 
		"dlr.IDDLR,\r\n" + 
		"max(case when (kpi.ID_KPI =14000 and ID_DPB_PGM = 60 and VEH_TYP = 'F') then \r\n" + 
		"kpi.PCT_KPI end) as Dealer_Standards, \r\n" + 
		"MAX(case when (kpi.ID_KPI =15000 and ID_DPB_PGM = 62 and VEH_TYP = 'F') then \r\n" + 
		"                      kpi.PCT_KPI  end) as Sales_Effective,\r\n" + 
		"MAX(case when (kpi.ID_KPI =13000 and ID_DPB_PGM = 61 and VEH_TYP = 'F') then \r\n" + 
		"                      kpi.PCT_KPI end) as MB_CSI,\r\n" + 
		"Max(case when ( ID_DPB_PGM  = 60 and VEH_TYP = 'F') then \r\n" + 
		"SUM_AMT_DPB_BNS_CALC end) as Dealer_Standards_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 62 and VEH_TYP = 'F') then SUM_AMT_DPB_BNS_CALC end) as Sales_Effective_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 61 and VEH_TYP = 'F') then SUM_AMT_DPB_BNS_CALC end) as MB_CSI_AMT,\r\n" + 
		"MAX(SUM_AMT_DPB_UNERND) AS SUM_AMT_DPB_UNERND_AMT,\r\n" + 
		"VEH_TYP,pc.CY13RetailSales,\r\n" + 
		"coalesce(REGN,''),coalesce(MKT,''),coalesce(T55,''),coalesce(ADR_DLR_CTY,''),coalesce(ADR_DLR_STA,'')\r\n" + 
		"                      \r\n" + 
		"                       \r\n" + 
		"from DLRPGMCALC_FINAL  f \r\n" + 
		"join LST_KPI lst_kpi on lst_kpi.ID_DLR = IDDLRCALC\r\n" + 
		"join DPB_KPI_FIL_EXTRT kpi on lst_kpi.LST_UPDT = kpi.ID_KPI_FIL_EXTRT\r\n" + 
		"join PO_COUNT pc on pc.ID_DLR = f.IDDLRCALC\r\n" + 
		"right outer join DPBDLR dlr on f.IDDLRCALC = dlr.IDDLR\r\n" + 
		"group by \r\n" + 
		"dlr.IDDLR\r\n" + 
		",VEH_TYP,pc.CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA),\r\n" + 
		"\r\n" + 
		"TOTALCY13RetailSales(TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_SUM_AMT_DPB_UNERND_AMT) AS \r\n" + 
		"(SELECT SUM(CY13RetailSales),SUM(Dealer_Standards_AMT),SUM(Sales_Effective_AMT),\r\n" + 
		"SUM(MB_CSI_AMT),SUM(SUM_AMT_DPB_UNERND_AMT)  \r\n" + 
		"FROM PGM_DATA),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PGM_DATA2 (Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as (\r\n" + 
		"select\r\n" + 
		"max(case when cal.ID_DPB_PGM in (60)  then NAM_DPB_PGM end) as Dealer_Standards_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (62)  then NAM_DPB_PGM end) as Sales_Effective_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (61)  then NAM_DPB_PGM end) as Brand_stand_name,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 60 and VEH_TYP = 'F') then PCT_VAR_PMT end) as Dealer_Standards,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 62 and VEH_TYP = 'F') then PCT_VAR_PMT end) as Sales_Effective,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 61 and VEH_TYP = 'F') then PCT_VAR_PMT end) as MB_CSI,\r\n" + 
		"NUMRETLQTR,cal.YEARS\r\n" + 
		"from DLRPGMCALC  cal \r\n" + 
		"join DPB_PGM pgm on cal.ID_DPB_PGM = pgm.ID_DPB_PGM\r\n" + 
		"group by NUMRETLQTR,YEARS\r\n" + 
		"),\r\n" + 
		"REPORT_DATA3 (IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"TOT_CY13RetailSales,Dealer_Standards_AMT,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_UNERND_AMT,TOT_EFF) as (\r\n" + 
		"select IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"coalesce(CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')) ,\r\n" + 
		"coalesce(TOTAL_CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_MB_CSI_AMT,0),'999,999,990.99')) ,\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')) ,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0)+coalesce(Sales_Effective,0)+coalesce(MB_CSI,0),'90.99') || '%'\r\n" + 
		"from PGM_DATA pgm,\r\n" + 
		"TOTALCY13RetailSales\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"FINAL_DATA2(Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as (\r\n" + 
		"select trim(coalesce(Dealer_Standards_name,'')),\r\n" + 
		"trim(coalesce(Sales_Effective_name,'')), trim(coalesce(Brand_stand_name,'')),\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"case when NUMRETLQTR=1 then '1st' else case when NUMRETLQTR=2 then '2nd' \r\n" + 
		"else case when NUMRETLQTR=3 then '3rd' else '4th' end end end as NUM_RETL_QTR,YEARS\r\n" + 
		"from PGM_DATA2\r\n" + 
		"),  \r\n" + 
		"REPORT_DATA (Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"TOT_CY13RetailSales) as (\r\n" + 
		"select\r\n" + 
		"varchar_format(Float(avg(coalesce(Dealer_Standards,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(Sales_Effective,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(MB_CSI,0))),'90.99'),\r\n" + 
		"sum(coalesce(CY13RetailSales,0))\r\n" + 
		"from PGM_DATA pgm\r\n" + 
		"),\r\n" + 
		"FINAL_DATA (Dealer_Standards,Sales_Effective,MB_CSI,TOTAL_MSRP) as (\r\n" + 
		"select Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"varchar_format((select TOTALAMTMSRPOPTS from DPBCALCPOCOUNT)/TOT_CY13RetailSales,'999,999,999,990.99')\r\n" + 
		"from REPORT_DATA\r\n" + 
		")\r\n" + 
		"select FD.Dealer_Standards,FD.Sales_Effective,FD.MB_CSI,FD.TOTAL_MSRP,\r\n" + 
		"FD2.Dealer_Standards_name,FD2.Sales_Effective_name,FD2.Brand_stand_name,\r\n" + 
		"FD2.Dealer_Standards as Dealer_Standards2,FD2.Sales_Effective as Sales_Effective2,FD2.MB_CSI as MB_CSI2,FD2.NUMRETLQTR,FD2.YEARS,RD3.IDDLR,RD3.REGN,RD3.MKT,RD3.ADR_DLR_CTY,RD3.ADR_DLR_STA,RD3.TOT_CY13RetailSales,\r\n" + 
		"RD3.Dealer_Standards as Dealer_Standards3,RD3.Sales_Effective as Sales_Effective3,RD3.MB_CSI as MB_CSI3,TOT_EFF,\r\n" + 
		"RD3.Dealer_Standards_AMT,RD3.Sales_Effective_AMT,\r\n" + 
		"RD3.MB_CSI_AMT,RD3.UNERND_AMT,RD3.TOTAL_CY13RetailSales,RD3.TOT_Dealer_Standards_AMT,\r\n" + 
		"RD3.TOT_Sales_Effective_AMT,RD3.TOT_MB_CSI_AMT,RD3.TOT_UNERND_AMT from FINAL_DATA FD,FINAL_DATA2 FD2,REPORT_DATA3 RD3\r\n" + 
		"with ur";

public static final String DCQPR_CARS_QUERY_COMMON = "with DPBDLR(IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) \r\n" + 
		"AS (select DISTINCT dlr.ID_DLR,dlr.CDE_RGN,dlr.CDE_MKT,dlr.CDE_MTRO_MKT_GRP,dlr.ADR_PRIM_CITY,dlr.CDE_DLR_ST\r\n" + 
		"from DEALER_FDRT dlr  \r\n" + 
		"OLDPAYOUT_DEALER_RANGE_FOR_RPRT  \r\n" + 
		"),\r\n" + 
		"DAYFDRTTBL(DTECAL_MIN,DTECAL_MAX,NUMRETLQTR,YEARS ) as (\r\n" + 
		"select min(fdr.DTE_CAL),max(fdr.DTE_CAL),fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR \r\n" + 
		"from DPB_DAY fdr \r\n" + 
		"where (fdr.NUM_RETL_QTR,fdr.NUM_RETL_YR) in (select NUM_RETL_QTR,dim.NUM_RETL_YR from DPB_DAY dim\r\n" + 
		"     DATE_RANGE_FOR_REPORTS   )\r\n" + 
		"group by fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR),\r\n" + 
		"\r\n" + 
		"DLRPGMCALC(IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, ID_DPB_UNBLK_VEH,NUMRETLQTR,YEARS,NUM_PO,VEH_TYP) as (\r\n" + 
		"select calc.ID_DLR,calc.ID_DPB_PGM,calc.AMT_DPB_BNS_CALC,calc.AMT_DPB_UNERND, \r\n" + 
		"calc.ID_DPB_UNBLK_VEH,dyfdrt.NUMRETLQTR,dyfdrt.YEARS, RTL.NUM_PO,rtl.CDE_VEH_TYP\r\n" + 
		"from DAYFDRTTBL dyfdrt\r\n" + 
		"join DPB_UNBLK_VEH rtl on rtl.DTE_RTL between dyfdrt.DTECAL_MIN and dyfdrt.DTECAL_MAX \r\n" + 
		"     VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"join DPB_CALC calc on calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    AND calc.ID_LDRSP_BNS_PGM IS NULL \r\n" + 
		"    --AND calc.ID_DPB_PGM IN (47,48,49,50,54,55,56,57,58,59,60,61,62)\r\n" + 
		"    AND calc.ID_DPB_PGM IN (144,145,47,48,49,50)\r\n" + 
		" DEALER_RANGE_FOR_REPORTS\r\n" + 
		"),\r\n" + 
		"veh_data (ID_DLR, NUM_PO, ID_DPB_UNBLK_VEH) as (\r\n" + 
		"select IDDLRCALC, NUM_PO, max(ID_DPB_UNBLK_VEH)\r\n" + 
		"from DLRPGMCALC\r\n" + 
		"group by IDDLRCALC, NUM_PO\r\n" + 
		"),\r\n" + 
		"VEHCOUNT(ID_DLR,NUM_PO,ID_DPB_UNBLK_VEH,TOTALAMTMSRPOPTS) as (\r\n" + 
		"select rtl.ID_DLR, rtl.num_po,rtl.ID_DPB_UNBLK_VEH, sum(rtl.AMT_MSRP_TOT_ACSRY+rtl.AMT_MSRP_BASE) from veh_data vd\r\n" + 
		"join DPB_UNBLK_VEH rtl\r\n" + 
		"on rtl.ID_DLR = vd.ID_DLR and rtl.ID_DPB_UNBLK_VEH = vd.ID_DPB_UNBLK_VEH\r\n" + 
		"and rtl.num_po = vd.NUM_PO and rtl.CDE_VEH_DDR_STS != 'I2' \r\n" + 
		"group by rtl.ID_DLR,rtl.num_po,rtl.ID_DPB_UNBLK_VEH),\r\n" + 
		"DPBCALCPOCOUNT(TOTALAMTMSRPOPTS) as \r\n" + 
		"(select sum(TOTALAMTMSRPOPTS) from VEHCOUNT cnt),\r\n" + 
		"PO_COUNT(ID_DLR,CY13RetailSales) as (\r\n" + 
		"select ID_DLR, count(NUM_PO) from VEHCOUNT group by ID_DLR\r\n" + 
		"),\r\n" + 
		"DLRPGMCALC_FINAL (IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, NUMRETLQTR,YEARS,VEH_TYP) as (\r\n" + 
		"select calc.IDDLRCALC,calc.ID_DPB_PGM,sum(calc.SUM_AMT_DPB_BNS_CALC),sum(calc.SUM_AMT_DPB_UNERND) ,calc.NUMRETLQTR,\r\n" + 
		"calc.YEARS,VEH_TYP\r\n" + 
		"from VEHCOUNT veh\r\n" + 
		"left outer join DLRPGMCALC calc on veh.ID_DLR = calc.IDDLRCALC\r\n" + 
		"and veh.NUM_PO = calc.NUM_PO\r\n" + 
		"group by calc.IDDLRCALC,calc.ID_DPB_PGM,calc.NUMRETLQTR,calc.YEARS,VEH_TYP\r\n" + 
		"),\r\n" + 
		"LST_KPI (LST_UPDT,ID_DLR,QTR,YR,ID_KPI ) AS (\r\n" + 
		"SELECT MAX(ID_KPI_FIL_EXTRT), dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI \r\n" + 
		"from DLRPGMCALC_FINAL po\r\n" + 
		"\r\n" + 
		"left outer join DPB_KPI_FIL_EXTRT dlrkpi on \r\n" + 
		"po.IDDLRCALC = dlrkpi.ID_DLR and po.NUMRETLQTR = dlrkpi.DTE_FSCL_QTR\r\n" + 
		"AND PO.YEARS = DLRKPI.DTE_FSCL_YR\r\n" + 
		"group by dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"PGM_DATA (IDDLR,Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,\r\n" + 
		"Dealer_Standards_AMT,cust_exp_sales_AMT,cust_exp_service_AMT,Pre_Owned_AM,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,SUM_AMT_DPB_UNERND_AMT,VEH_TYP,CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) as (\r\n" + 
		"select\r\n" + 
		"dlr.IDDLR,\r\n" + 
		"max(case when (kpi.ID_KPI =6000  and ID_DPB_PGM = 47 and VEH_TYP = 'P') then \r\n" + 
		"kpi.PCT_KPI end) as Dealer_Standards,\r\n" + 
		"max(case when (kpi.ID_KPI =6200  and ID_DPB_PGM = 144 and VEH_TYP = 'P') then \r\n" + 
		"kpi.PCT_KPI end) as cust_exp_sales,\r\n" + 
		"max(case when (kpi.ID_KPI =6300  and ID_DPB_PGM = 145 and VEH_TYP = 'P') then \r\n" + 
		"kpi.PCT_KPI end) as cust_exp_service,\r\n" + 
		"max(case when (kpi.ID_KPI =8000 and ID_DPB_PGM = 49 and VEH_TYP = 'P') then kpi.PCT_KPI end) as Pre_Owned , \r\n" + 
		"MAX(case when (kpi.ID_KPI =7000 and ID_DPB_PGM = 48 and VEH_TYP = 'P') then \r\n" + 
		"                      kpi.PCT_KPI  end) as Sales_Effective,\r\n" + 
		"MAX(case when (kpi.ID_KPI =5000 and ID_DPB_PGM = 50 and VEH_TYP = 'P') then \r\n" + 
		"                      kpi.PCT_KPI end) as MB_CSI,\r\n" + 
		"Max(case when ( ID_DPB_PGM = 47 and VEH_TYP = 'P') then \r\n" + 
		"SUM_AMT_DPB_BNS_CALC end) as cust_exp_sales_AMT,\r\n" + 
		"Max(case when ( ID_DPB_PGM = 144 and VEH_TYP = 'P') then \r\n" + 
		"SUM_AMT_DPB_BNS_CALC end) as cust_exp_service_AMT,\r\n" + 
		"Max(case when ( ID_DPB_PGM = 145 and VEH_TYP = 'P') then \r\n" + 
		"SUM_AMT_DPB_BNS_CALC end) as Dealer_Standards_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 49 and VEH_TYP = 'P') then SUM_AMT_DPB_BNS_CALC end) as Pre_Owned_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 48 and VEH_TYP = 'P') then SUM_AMT_DPB_BNS_CALC end) as Sales_Effective_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 50 and VEH_TYP = 'P') then SUM_AMT_DPB_BNS_CALC end) as MB_CSI_AMT,\r\n" + 
		"MAX(SUM_AMT_DPB_UNERND) AS SUM_AMT_DPB_UNERND_AMT,\r\n" + 
		"VEH_TYP,pc.CY13RetailSales,\r\n" + 
		"coalesce(REGN,''),coalesce(MKT,''),coalesce(T55,''),coalesce(ADR_DLR_CTY,''),coalesce(ADR_DLR_STA,'')                                           \r\n" + 
		"from DLRPGMCALC_FINAL  f \r\n" + 
		"join LST_KPI lst_kpi on lst_kpi.ID_DLR = IDDLRCALC\r\n" + 
		"join DPB_KPI_FIL_EXTRT kpi on lst_kpi.LST_UPDT = kpi.ID_KPI_FIL_EXTRT\r\n" + 
		"join PO_COUNT pc on pc.ID_DLR = f.IDDLRCALC\r\n" + 
		"right outer join DPBDLR dlr on f.IDDLRCALC = dlr.IDDLR\r\n" + 
		"group by \r\n" + 
		"dlr.IDDLR\r\n" + 
		",VEH_TYP,pc.CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA),\r\n" + 
		"\r\n" + 
		"TOTALCY13RetailSales(TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,TOT_cust_exp_sales_AMT,TOT_cust_exp_service_AMT,TOT_Pre_Owned_AM,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_SUM_AMT_DPB_UNERND_AMT) AS \r\n" + 
		"(SELECT SUM(CY13RetailSales),SUM(Dealer_Standards_AMT),SUM(cust_exp_sales_AMT),SUM(cust_exp_service_AMT),SUM(Pre_Owned_AM),SUM(Sales_Effective_AMT),\r\n" + 
		"SUM(MB_CSI_AMT),SUM(SUM_AMT_DPB_UNERND_AMT)  \r\n" + 
		"FROM PGM_DATA),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PGM_DATA2 (Dealer_Standards_name,cust_exp_sales_name,cust_exp_service_name,po_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as (\r\n" + 
		"select\r\n" + 
		"max(case when cal.ID_DPB_PGM in (47)  then NAM_DPB_PGM end) as Dealer_Standards_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (144)  then NAM_DPB_PGM end) as cust_exp_sales_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (145)  then NAM_DPB_PGM end) as cust_exp_service_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (49)  then NAM_DPB_PGM end) as po_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (48)  then NAM_DPB_PGM end) as Sales_Effective_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (50)  then NAM_DPB_PGM end) as Brand_stand_name,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 47 and VEH_TYP = 'P') then PCT_VAR_PMT end) as Dealer_Standards,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 144 and VEH_TYP = 'P') then PCT_VAR_PMT end) as cust_exp_sales,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 145 and VEH_TYP = 'P') then PCT_VAR_PMT end) as cust_exp_service,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 49 and VEH_TYP = 'P') then PCT_VAR_PMT end) as Pre_Owned,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 48 and VEH_TYP = 'P') then PCT_VAR_PMT end) as Sales_Effective,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 50 and VEH_TYP = 'P') then PCT_VAR_PMT end) as MB_CSI,\r\n" + 
		"NUMRETLQTR,cal.YEARS\r\n" + 
		"from DLRPGMCALC  cal \r\n" + 
		"join DPB_PGM pgm on cal.ID_DPB_PGM = pgm.ID_DPB_PGM\r\n" + 
		"group by NUMRETLQTR,YEARS\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"REPORT_DATA3 (IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,\r\n" + 
		"TOT_CY13RetailSales,Dealer_Standards_AMT,cust_exp_sales_AMT,cust_exp_service_AMT,Pre_Owned_AM,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,TOT_cust_exp_sales_AMT,TOT_cust_exp_service_AMT,TOT_Pre_Owned_AM,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_UNERND_AMT,TOT_EFF) as (\r\n" + 
		"select IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(cust_exp_sales,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(cust_exp_service,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Pre_Owned,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"coalesce(CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(cust_exp_sales_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(cust_exp_service_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Pre_Owned_AM,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')) AS UNERND,\r\n" + 
		"coalesce(TOTAL_CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_cust_exp_sales_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_cust_exp_service_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Pre_Owned_AM,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_MB_CSI_AMT,0),'999,999,990.99')) ,\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')) ,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0)+coalesce(cust_exp_sales,0)+coalesce(cust_exp_service,0)+coalesce(Pre_Owned,0)+coalesce(Sales_Effective,0)+coalesce(MB_CSI,0),'90.99') || '%'\r\n" + 
		"from PGM_DATA pgm,\r\n" + 
		"TOTALCY13RetailSales\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"FINAL_DATA2(Dealer_Standards_name,cust_exp_sales_name,cust_exp_service_name,po_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as (\r\n" + 
		"select trim(coalesce(Dealer_Standards_name,'')),trim(coalesce(cust_exp_sales_name,'')),trim(coalesce(cust_exp_service_name,'')), trim(coalesce(po_name,'')),\r\n" + 
		"trim(coalesce(Sales_Effective_name,'')), trim(coalesce(Brand_stand_name,'')),\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(cust_exp_sales,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(cust_exp_service,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Pre_Owned,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"case when NUMRETLQTR=1 then '1st' else case when NUMRETLQTR=2 then '2nd' \r\n" + 
		"else case when NUMRETLQTR=3 then '3rd' else '4th' end end end as NUM_RETL_QTR,YEARS\r\n" + 
		"from PGM_DATA2\r\n" + 
		"),  \r\n" + 
		"\r\n" + 
		"REPORT_DATA (Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,\r\n" + 
		"TOT_CY13RetailSales) as (\r\n" + 
		"select\r\n" + 
		"varchar_format(Float(avg(coalesce(Dealer_Standards,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(cust_exp_sales,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(cust_exp_service,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(Pre_Owned,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(Sales_Effective,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(MB_CSI,0))),'90.99'),\r\n" + 
		"sum(coalesce(CY13RetailSales,0))\r\n" + 
		"from PGM_DATA pgm\r\n" + 
		"),\r\n" + 
		"FINAL_DATA (Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,TOTAL_MSRP) as (\r\n" + 
		"select Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,\r\n" + 
		"varchar_format((select TOTALAMTMSRPOPTS from DPBCALCPOCOUNT)/TOT_CY13RetailSales,'999,999,999,990.99')\r\n" + 
		"from REPORT_DATA\r\n" + 
		"), \r\n" + 
		"Final_Result(ROWNUM,Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,TOTAL_MSRP,\r\n" +
		"Dealer_Standards_name,cust_exp_sales_name,cust_exp_service_name,po_name,Sales_Effective_name,Brand_stand_name,\r\n" +
		"Dealer_Standards2,cust_exp_sales2,cust_exp_service2,Pre_Owned2,Sales_Effective2,MB_CSI2,NUMRETLQTR,YEARS,IDDLR,REGN,MKT,\r\n" +
		"ADR_DLR_CTY,ADR_DLR_STA,TOT_CY13RetailSales,Dealer_Standards3,cust_exp_sales3,cust_exp_service3,Pre_Owned3,Sales_Effective3,\r\n" +
		"MB_CSI3,TOT_EFF,Dealer_Standards_AMT,cust_exp_sales_AMT,cust_exp_service_AMT,Pre_Owned_AM,Sales_Effective_AMT,MB_CSI_AMT,\r\n" +
		"UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,TOT_cust_exp_sales_AMT,TOT_cust_exp_service_AMT,TOT_Pre_Owned_AM,\r\n" +
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_UNERND_AMT) as \r\n"+
		"(select ROW_NUMBER() OVER (ORDER BY iddlr ASC) AS ROWNUM,FD.Dealer_Standards,FD.cust_exp_sales,FD.cust_exp_service,FD.Pre_Owned,FD.Sales_Effective,FD.MB_CSI,FD.TOTAL_MSRP,\r\n" + 
		"FD2.Dealer_Standards_name,FD2.cust_exp_sales_name,FD2.cust_exp_service_name,FD2.po_name,FD2.Sales_Effective_name,FD2.Brand_stand_name,\r\n" + 
		"FD2.Dealer_Standards as Dealer_Standards2,FD2.cust_exp_sales as cust_exp_sales2,FD2.cust_exp_service as cust_exp_service2,FD2.Pre_Owned as Pre_Owned2,FD2.Sales_Effective as Sales_Effective2,FD2.MB_CSI as MB_CSI2,FD2.NUMRETLQTR,FD2.YEARS,RD3.IDDLR,RD3.REGN,RD3.MKT,RD3.ADR_DLR_CTY,RD3.ADR_DLR_STA,RD3.TOT_CY13RetailSales,\r\n" + 
		"RD3.Dealer_Standards as Dealer_Standards3,RD3.cust_exp_sales as cust_exp_sales3,RD3.cust_exp_service as cust_exp_service3,RD3.Pre_Owned as Pre_Owned3,RD3.Sales_Effective as Sales_Effective3,RD3.MB_CSI as MB_CSI3,TOT_EFF,\r\n" + 
		"RD3.Dealer_Standards_AMT,RD3.cust_exp_sales_AMT,RD3.cust_exp_service_AMT,RD3.Pre_Owned_AM,RD3.Sales_Effective_AMT,RD3.MB_CSI_AMT,RD3.UNERND_AMT,RD3.TOTAL_CY13RetailSales,\r\n" + 
		"RD3.TOT_Dealer_Standards_AMT,RD3.TOT_cust_exp_sales_AMT,RD3.TOT_cust_exp_service_AMT,RD3.TOT_Pre_Owned_AM,\r\n" + 
		"RD3.TOT_Sales_Effective_AMT,RD3.TOT_MB_CSI_AMT,RD3.TOT_UNERND_AMT from FINAL_DATA FD,FINAL_DATA2 FD2,REPORT_DATA3 RD3\r\n" + 
		")";

public static final String DCQPR_CARS_QUERY = "with DPBDLR(IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) \r\n" + 
		"AS (select DISTINCT dlr.ID_DLR,dlr.CDE_RGN,dlr.CDE_MKT,dlr.CDE_MTRO_MKT_GRP,dlr.ADR_PRIM_CITY,dlr.CDE_DLR_ST\r\n" + 
		"from DEALER_FDRT dlr  \r\n" + 
		"OLDPAYOUT_DEALER_RANGE_FOR_RPRT  \r\n" + 
		"),\r\n" + 
		"DAYFDRTTBL(DTECAL_MIN,DTECAL_MAX,NUMRETLQTR,YEARS ) as (\r\n" + 
		"select min(fdr.DTE_CAL),max(fdr.DTE_CAL),fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR \r\n" + 
		"from DPB_DAY fdr \r\n" + 
		"where (fdr.NUM_RETL_QTR,fdr.NUM_RETL_YR) in (select NUM_RETL_QTR,dim.NUM_RETL_YR from DPB_DAY dim\r\n" + 
		"     DATE_RANGE_FOR_REPORTS   )\r\n" + 
		"group by fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR),\r\n" + 
		"\r\n" + 
		"DLRPGMCALC(IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, ID_DPB_UNBLK_VEH,NUMRETLQTR,YEARS,NUM_PO,VEH_TYP) as (\r\n" + 
		"select calc.ID_DLR,calc.ID_DPB_PGM,calc.AMT_DPB_BNS_CALC,calc.AMT_DPB_UNERND, \r\n" + 
		"calc.ID_DPB_UNBLK_VEH,dyfdrt.NUMRETLQTR,dyfdrt.YEARS, RTL.NUM_PO,rtl.CDE_VEH_TYP\r\n" + 
		"from DAYFDRTTBL dyfdrt\r\n" + 
		"join DPB_UNBLK_VEH rtl on rtl.DTE_RTL between dyfdrt.DTECAL_MIN and dyfdrt.DTECAL_MAX \r\n" + 
		"     VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"join DPB_CALC calc on calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    AND calc.ID_LDRSP_BNS_PGM IS NULL \r\n" + 
		"    --AND calc.ID_DPB_PGM IN (47,48,49,50,54,55,56,57,58,59,60,61,62)\r\n" + 
		"    AND calc.ID_DPB_PGM IN (144,145,47,48,49,50)\r\n" + 
		" DEALER_RANGE_FOR_REPORTS\r\n" + 
		"),\r\n" + 
		"veh_data (ID_DLR, NUM_PO, ID_DPB_UNBLK_VEH) as (\r\n" + 
		"select IDDLRCALC, NUM_PO, max(ID_DPB_UNBLK_VEH)\r\n" + 
		"from DLRPGMCALC\r\n" + 
		"group by IDDLRCALC, NUM_PO\r\n" + 
		"),\r\n" + 
		"VEHCOUNT(ID_DLR,NUM_PO,ID_DPB_UNBLK_VEH,TOTALAMTMSRPOPTS) as (\r\n" + 
		"select rtl.ID_DLR, rtl.num_po,rtl.ID_DPB_UNBLK_VEH, sum(rtl.AMT_MSRP_TOT_ACSRY+rtl.AMT_MSRP_BASE) from veh_data vd\r\n" + 
		"join DPB_UNBLK_VEH rtl\r\n" + 
		"on rtl.ID_DLR = vd.ID_DLR and rtl.ID_DPB_UNBLK_VEH = vd.ID_DPB_UNBLK_VEH\r\n" + 
		"and rtl.num_po = vd.NUM_PO and rtl.CDE_VEH_DDR_STS != 'I2' \r\n" + 
		"group by rtl.ID_DLR,rtl.num_po,rtl.ID_DPB_UNBLK_VEH),\r\n" + 
		"DPBCALCPOCOUNT(TOTALAMTMSRPOPTS) as \r\n" + 
		"(select sum(TOTALAMTMSRPOPTS) from VEHCOUNT cnt),\r\n" + 
		"PO_COUNT(ID_DLR,CY13RetailSales) as (\r\n" + 
		"select ID_DLR, count(NUM_PO) from VEHCOUNT group by ID_DLR\r\n" + 
		"),\r\n" + 
		"DLRPGMCALC_FINAL (IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, NUMRETLQTR,YEARS,VEH_TYP) as (\r\n" + 
		"select calc.IDDLRCALC,calc.ID_DPB_PGM,sum(calc.SUM_AMT_DPB_BNS_CALC),sum(calc.SUM_AMT_DPB_UNERND) ,calc.NUMRETLQTR,\r\n" + 
		"calc.YEARS,VEH_TYP\r\n" + 
		"from VEHCOUNT veh\r\n" + 
		"left outer join DLRPGMCALC calc on veh.ID_DLR = calc.IDDLRCALC\r\n" + 
		"and veh.NUM_PO = calc.NUM_PO\r\n" + 
		"group by calc.IDDLRCALC,calc.ID_DPB_PGM,calc.NUMRETLQTR,calc.YEARS,VEH_TYP\r\n" + 
		"),\r\n" + 
		"LST_KPI (LST_UPDT,ID_DLR,QTR,YR,ID_KPI ) AS (\r\n" + 
		"SELECT MAX(ID_KPI_FIL_EXTRT), dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI \r\n" + 
		"from DLRPGMCALC_FINAL po\r\n" + 
		"\r\n" + 
		"left outer join DPB_KPI_FIL_EXTRT dlrkpi on \r\n" + 
		"po.IDDLRCALC = dlrkpi.ID_DLR and po.NUMRETLQTR = dlrkpi.DTE_FSCL_QTR\r\n" + 
		"AND PO.YEARS = DLRKPI.DTE_FSCL_YR\r\n" + 
		"group by dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"PGM_DATA (IDDLR,Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,\r\n" + 
		"Dealer_Standards_AMT,cust_exp_sales_AMT,cust_exp_service_AMT,Pre_Owned_AM,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,SUM_AMT_DPB_UNERND_AMT,VEH_TYP,CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) as (\r\n" + 
		"select\r\n" + 
		"dlr.IDDLR,\r\n" + 
		"max(case when (kpi.ID_KPI =6000  and ID_DPB_PGM = 47 and VEH_TYP = 'P') then \r\n" + 
		"kpi.PCT_KPI end) as Dealer_Standards,\r\n" + 
		"max(case when (kpi.ID_KPI =6200  and ID_DPB_PGM = 144 and VEH_TYP = 'P') then \r\n" + 
		"kpi.PCT_KPI end) as cust_exp_sales,\r\n" + 
		"max(case when (kpi.ID_KPI =6300  and ID_DPB_PGM = 145 and VEH_TYP = 'P') then \r\n" + 
		"kpi.PCT_KPI end) as cust_exp_service,\r\n" + 
		"max(case when (kpi.ID_KPI =8000 and ID_DPB_PGM = 49 and VEH_TYP = 'P') then kpi.PCT_KPI end) as Pre_Owned , \r\n" + 
		"MAX(case when (kpi.ID_KPI =7000 and ID_DPB_PGM = 48 and VEH_TYP = 'P') then \r\n" + 
		"                      kpi.PCT_KPI  end) as Sales_Effective,\r\n" + 
		"MAX(case when (kpi.ID_KPI =5000 and ID_DPB_PGM = 50 and VEH_TYP = 'P') then \r\n" + 
		"                      kpi.PCT_KPI end) as MB_CSI,\r\n" + 
		"Max(case when ( ID_DPB_PGM = 47 and VEH_TYP = 'P') then \r\n" + 
		"SUM_AMT_DPB_BNS_CALC end) as cust_exp_sales_AMT,\r\n" + 
		"Max(case when ( ID_DPB_PGM = 144 and VEH_TYP = 'P') then \r\n" + 
		"SUM_AMT_DPB_BNS_CALC end) as cust_exp_service_AMT,\r\n" + 
		"Max(case when ( ID_DPB_PGM = 145 and VEH_TYP = 'P') then \r\n" + 
		"SUM_AMT_DPB_BNS_CALC end) as Dealer_Standards_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 49 and VEH_TYP = 'P') then SUM_AMT_DPB_BNS_CALC end) as Pre_Owned_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 48 and VEH_TYP = 'P') then SUM_AMT_DPB_BNS_CALC end) as Sales_Effective_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 50 and VEH_TYP = 'P') then SUM_AMT_DPB_BNS_CALC end) as MB_CSI_AMT,\r\n" + 
		"MAX(SUM_AMT_DPB_UNERND) AS SUM_AMT_DPB_UNERND_AMT,\r\n" + 
		"VEH_TYP,pc.CY13RetailSales,\r\n" + 
		"coalesce(REGN,''),coalesce(MKT,''),coalesce(T55,''),coalesce(ADR_DLR_CTY,''),coalesce(ADR_DLR_STA,'')                                           \r\n" + 
		"from DLRPGMCALC_FINAL  f \r\n" + 
		"join LST_KPI lst_kpi on lst_kpi.ID_DLR = IDDLRCALC\r\n" + 
		"join DPB_KPI_FIL_EXTRT kpi on lst_kpi.LST_UPDT = kpi.ID_KPI_FIL_EXTRT\r\n" + 
		"join PO_COUNT pc on pc.ID_DLR = f.IDDLRCALC\r\n" + 
		"right outer join DPBDLR dlr on f.IDDLRCALC = dlr.IDDLR\r\n" + 
		"group by \r\n" + 
		"dlr.IDDLR\r\n" + 
		",VEH_TYP,pc.CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA),\r\n" + 
		"\r\n" + 
		"TOTALCY13RetailSales(TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,TOT_cust_exp_sales_AMT,TOT_cust_exp_service_AMT,TOT_Pre_Owned_AM,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_SUM_AMT_DPB_UNERND_AMT) AS \r\n" + 
		"(SELECT SUM(CY13RetailSales),SUM(Dealer_Standards_AMT),SUM(cust_exp_sales_AMT),SUM(cust_exp_service_AMT),SUM(Pre_Owned_AM),SUM(Sales_Effective_AMT),\r\n" + 
		"SUM(MB_CSI_AMT),SUM(SUM_AMT_DPB_UNERND_AMT)  \r\n" + 
		"FROM PGM_DATA),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PGM_DATA2 (Dealer_Standards_name,cust_exp_sales_name,cust_exp_service_name,po_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as (\r\n" + 
		"select\r\n" + 
		"max(case when cal.ID_DPB_PGM in (47)  then NAM_DPB_PGM end) as Dealer_Standards_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (144)  then NAM_DPB_PGM end) as cust_exp_sales_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (145)  then NAM_DPB_PGM end) as cust_exp_service_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (49)  then NAM_DPB_PGM end) as po_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (48)  then NAM_DPB_PGM end) as Sales_Effective_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (50)  then NAM_DPB_PGM end) as Brand_stand_name,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 47 and VEH_TYP = 'P') then PCT_VAR_PMT end) as Dealer_Standards,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 144 and VEH_TYP = 'P') then PCT_VAR_PMT end) as cust_exp_sales,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 145 and VEH_TYP = 'P') then PCT_VAR_PMT end) as cust_exp_service,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 49 and VEH_TYP = 'P') then PCT_VAR_PMT end) as Pre_Owned,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 48 and VEH_TYP = 'P') then PCT_VAR_PMT end) as Sales_Effective,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 50 and VEH_TYP = 'P') then PCT_VAR_PMT end) as MB_CSI,\r\n" + 
		"NUMRETLQTR,cal.YEARS\r\n" + 
		"from DLRPGMCALC  cal \r\n" + 
		"join DPB_PGM pgm on cal.ID_DPB_PGM = pgm.ID_DPB_PGM\r\n" + 
		"group by NUMRETLQTR,YEARS\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"REPORT_DATA3 (IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,\r\n" + 
		"TOT_CY13RetailSales,Dealer_Standards_AMT,cust_exp_sales_AMT,cust_exp_service_AMT,Pre_Owned_AM,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,TOT_cust_exp_sales_AMT,TOT_cust_exp_service_AMT,TOT_Pre_Owned_AM,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_UNERND_AMT,TOT_EFF) as (\r\n" + 
		"select IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(cust_exp_sales,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(cust_exp_service,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Pre_Owned,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"coalesce(CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(cust_exp_sales_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(cust_exp_service_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Pre_Owned_AM,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')) AS UNERND,\r\n" + 
		"coalesce(TOTAL_CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_cust_exp_sales_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_cust_exp_service_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Pre_Owned_AM,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_MB_CSI_AMT,0),'999,999,990.99')) ,\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')) ,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0)+coalesce(cust_exp_sales,0)+coalesce(cust_exp_service,0)+coalesce(Pre_Owned,0)+coalesce(Sales_Effective,0)+coalesce(MB_CSI,0),'90.99') || '%'\r\n" + 
		"from PGM_DATA pgm,\r\n" + 
		"TOTALCY13RetailSales\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"FINAL_DATA2(Dealer_Standards_name,cust_exp_sales_name,cust_exp_service_name,po_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as (\r\n" + 
		"select trim(coalesce(Dealer_Standards_name,'')),trim(coalesce(cust_exp_sales_name,'')),trim(coalesce(cust_exp_service_name,'')), trim(coalesce(po_name,'')),\r\n" + 
		"trim(coalesce(Sales_Effective_name,'')), trim(coalesce(Brand_stand_name,'')),\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(cust_exp_sales,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(cust_exp_service,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Pre_Owned,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"case when NUMRETLQTR=1 then '1st' else case when NUMRETLQTR=2 then '2nd' \r\n" + 
		"else case when NUMRETLQTR=3 then '3rd' else '4th' end end end as NUM_RETL_QTR,YEARS\r\n" + 
		"from PGM_DATA2\r\n" + 
		"),  \r\n" + 
		"\r\n" + 
		"REPORT_DATA (Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,\r\n" + 
		"TOT_CY13RetailSales) as (\r\n" + 
		"select\r\n" + 
		"varchar_format(Float(avg(coalesce(Dealer_Standards,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(cust_exp_sales,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(cust_exp_service,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(Pre_Owned,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(Sales_Effective,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(MB_CSI,0))),'90.99'),\r\n" + 
		"sum(coalesce(CY13RetailSales,0))\r\n" + 
		"from PGM_DATA pgm\r\n" + 
		"),\r\n" + 
		"FINAL_DATA (Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,TOTAL_MSRP) as (\r\n" + 
		"select Dealer_Standards,cust_exp_sales,cust_exp_service,Pre_Owned,Sales_Effective,MB_CSI,\r\n" + 
		"varchar_format((select TOTALAMTMSRPOPTS from DPBCALCPOCOUNT)/TOT_CY13RetailSales,'999,999,999,990.99')\r\n" + 
		"from REPORT_DATA\r\n" + 
		")\r\n" + 
		"select FD.Dealer_Standards,FD.cust_exp_sales,FD.cust_exp_service,FD.Pre_Owned,FD.Sales_Effective,FD.MB_CSI,FD.TOTAL_MSRP,\r\n" + 
		"FD2.Dealer_Standards_name,FD2.cust_exp_sales_name,FD2.cust_exp_service_name,FD2.po_name,FD2.Sales_Effective_name,FD2.Brand_stand_name,\r\n" + 
		"FD2.Dealer_Standards as Dealer_Standards2,FD2.cust_exp_sales as cust_exp_sales2,FD2.cust_exp_service as cust_exp_service2,FD2.Pre_Owned as Pre_Owned2,FD2.Sales_Effective as Sales_Effective2,FD2.MB_CSI as MB_CSI2,FD2.NUMRETLQTR,FD2.YEARS,RD3.IDDLR,RD3.REGN,RD3.MKT,RD3.ADR_DLR_CTY,RD3.ADR_DLR_STA,RD3.TOT_CY13RetailSales,\r\n" + 
		"RD3.Dealer_Standards as Dealer_Standards3,RD3.cust_exp_sales as cust_exp_sales3,RD3.cust_exp_service as cust_exp_service3,RD3.Pre_Owned as Pre_Owned3,RD3.Sales_Effective as Sales_Effective3,RD3.MB_CSI,TOT_EFF,\r\n" + 
		"RD3.Dealer_Standards_AMT,RD3.cust_exp_sales_AMT,RD3.cust_exp_service_AMT,RD3.Pre_Owned_AM,RD3.Sales_Effective_AMT,RD3.MB_CSI_AMT,RD3.UNERND_AMT,RD3.TOTAL_CY13RetailSales,\r\n" + 
		"RD3.TOT_Dealer_Standards_AMT,RD3.TOT_cust_exp_sales_AMT,RD3.TOT_cust_exp_service_AMT,RD3.TOT_Pre_Owned_AM,\r\n" + 
		"RD3.TOT_Sales_Effective_AMT,RD3.TOT_MB_CSI_AMT,RD3.TOT_UNERND_AMT from FINAL_DATA FD,FINAL_DATA2 FD2,REPORT_DATA3 RD3\r\n" + 
		"with ur";

public static final String DCQPR_VANS_QUERY_COMMON="with DPBDLR(IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) \r\n" + 
		"AS (select DISTINCT dlr.ID_DLR,dlr.CDE_RGN,dlr.CDE_MKT,dlr.CDE_MTRO_MKT_GRP,dlr.ADR_PRIM_CITY,dlr.CDE_DLR_ST\r\n" + 
		"from DEALER_FDRT dlr  \r\n" + 
		"OLDPAYOUT_DEALER_RANGE_FOR_RPRT  \r\n" + 
		"),\r\n" + 
		"DAYFDRTTBL(DTECAL_MIN,DTECAL_MAX,NUMRETLQTR,YEARS ) as (\r\n" + 
		"select min(fdr.DTE_CAL),max(fdr.DTE_CAL),fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR \r\n" + 
		"from DPB_DAY fdr \r\n" + 
		"where (fdr.NUM_RETL_QTR,fdr.NUM_RETL_YR) in (select NUM_RETL_QTR,dim.NUM_RETL_YR from DPB_DAY dim\r\n" + 
		"     DATE_RANGE_FOR_REPORTS   )\r\n" + 
		"group by fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR),\r\n" + 
		"\r\n" + 
		"DLRPGMCALC(IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND,ID_DPB_UNBLK_VEH,NUMRETLQTR,YEARS,NUM_PO,VEH_TYP) as (\r\n" + 
		"select calc.ID_DLR,calc.ID_DPB_PGM,calc.AMT_DPB_BNS_CALC,calc.AMT_DPB_UNERND, \r\n" + 
		"calc.ID_DPB_UNBLK_VEH,dyfdrt.NUMRETLQTR,dyfdrt.YEARS, RTL.NUM_PO,rtl.CDE_VEH_TYP\r\n" + 
		"from DAYFDRTTBL dyfdrt\r\n" + 
		"join DPB_UNBLK_VEH rtl on rtl.DTE_RTL between dyfdrt.DTECAL_MIN and dyfdrt.DTECAL_MAX \r\n" + 
		"     VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"join DPB_CALC calc on calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    AND calc.ID_LDRSP_BNS_PGM IS NULL \r\n" + 
		"    --AND calc.ID_DPB_PGM IN (47,48,49,50,54,55,56,57,58,59,60,61,62)\r\n" + 
		"    AND calc.ID_DPB_PGM IN (57,59,58)\r\n" + 
		" DEALER_RANGE_FOR_REPORTS\r\n" + 
		"),\r\n" + 
		"veh_data (ID_DLR, NUM_PO, ID_DPB_UNBLK_VEH) as (\r\n" + 
		"select IDDLRCALC, NUM_PO, max(ID_DPB_UNBLK_VEH)\r\n" + 
		"from DLRPGMCALC\r\n" + 
		"group by IDDLRCALC, NUM_PO\r\n" + 
		"),\r\n" + 
		"VEHCOUNT(ID_DLR,NUM_PO,ID_DPB_UNBLK_VEH,TOTALAMTMSRPOPTS) as (\r\n" + 
		"select rtl.ID_DLR, rtl.num_po,rtl.ID_DPB_UNBLK_VEH, sum(rtl.AMT_MSRP_TOT_ACSRY+rtl.AMT_MSRP_BASE) from veh_data vd\r\n" + 
		"join DPB_UNBLK_VEH rtl\r\n" + 
		"on rtl.ID_DLR = vd.ID_DLR and rtl.ID_DPB_UNBLK_VEH = vd.ID_DPB_UNBLK_VEH\r\n" + 
		"and rtl.num_po = vd.NUM_PO and rtl.CDE_VEH_DDR_STS != 'I2' \r\n" + 
		"group by rtl.ID_DLR,rtl.num_po,rtl.ID_DPB_UNBLK_VEH),\r\n" + 
		"DPBCALCPOCOUNT(TOTALAMTMSRPOPTS) as \r\n" + 
		"(select sum(TOTALAMTMSRPOPTS) from VEHCOUNT cnt),\r\n" + 
		"PO_COUNT(ID_DLR,CY13RetailSales) as (\r\n" + 
		"select ID_DLR, count(NUM_PO) from VEHCOUNT group by ID_DLR\r\n" + 
		"),\r\n" + 
		"DLRPGMCALC_FINAL (IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, NUMRETLQTR,YEARS,VEH_TYP) as (\r\n" + 
		"select calc.IDDLRCALC,calc.ID_DPB_PGM,sum(calc.SUM_AMT_DPB_BNS_CALC),sum(calc.SUM_AMT_DPB_UNERND), calc.NUMRETLQTR,\r\n" + 
		"calc.YEARS,VEH_TYP\r\n" + 
		"from VEHCOUNT veh\r\n" + 
		"left outer join DLRPGMCALC calc on veh.ID_DLR = calc.IDDLRCALC\r\n" + 
		"and veh.NUM_PO = calc.NUM_PO\r\n" + 
		"group by calc.IDDLRCALC,calc.ID_DPB_PGM,calc.NUMRETLQTR,calc.YEARS,VEH_TYP\r\n" + 
		"),\r\n" + 
		"LST_KPI (LST_UPDT,ID_DLR,QTR,YR,ID_KPI ) AS (\r\n" + 
		"SELECT MAX(ID_KPI_FIL_EXTRT), dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI \r\n" + 
		"from DLRPGMCALC_FINAL po\r\n" + 
		"\r\n" + 
		"left outer join DPB_KPI_FIL_EXTRT dlrkpi on \r\n" + 
		"po.IDDLRCALC = dlrkpi.ID_DLR and po.NUMRETLQTR = dlrkpi.DTE_FSCL_QTR\r\n" + 
		"AND PO.YEARS = DLRKPI.DTE_FSCL_YR\r\n" + 
		"group by dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"PGM_DATA (IDDLR,Dealer_Standards,Sales_Effective,MB_CSI,Dealer_Standards_AMT,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,SUM_AMT_DPB_UNERND_AMT,VEH_TYP,CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) as (\r\n" + 
		"select\r\n" + 
		"dlr.IDDLR,\r\n" + 
		"max(case when (kpi.ID_KPI =14000  and ID_DPB_PGM = 57 and VEH_TYP = 'V') then \r\n" + 
		"kpi.PCT_KPI end) as Dealer_Standards, \r\n" + 
		"MAX(case when (kpi.ID_KPI =15000 and ID_DPB_PGM = 59 and VEH_TYP = 'V') then \r\n" + 
		"                      kpi.PCT_KPI  end) as Sales_Effective,\r\n" + 
		"MAX(case when (kpi.ID_KPI =13000 and ID_DPB_PGM = 58 and VEH_TYP = 'V') then \r\n" + 
		"                      kpi.PCT_KPI end) as MB_CSI,\r\n" + 
		"Max(case when (ID_DPB_PGM  = 57 and VEH_TYP = 'V') then \r\n" + 
		"SUM_AMT_DPB_BNS_CALC end) as Dealer_Standards_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 59 and VEH_TYP = 'V') then SUM_AMT_DPB_BNS_CALC end) as Sales_Effective_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 58 and VEH_TYP = 'V') then SUM_AMT_DPB_BNS_CALC end) as MB_CSI_AMT,\r\n" + 
		"MAX(SUM_AMT_DPB_UNERND) AS SUM_AMT_DPB_UNERND_AMT,\r\n" + 
		"VEH_TYP,pc.CY13RetailSales,\r\n" + 
		"coalesce(REGN,''),coalesce(MKT,''),coalesce(T55,''),coalesce(ADR_DLR_CTY,''),coalesce(ADR_DLR_STA,'')\r\n" + 
		"                      \r\n" + 
		"                       \r\n" + 
		"from DLRPGMCALC_FINAL  f \r\n" + 
		"join LST_KPI lst_kpi on lst_kpi.ID_DLR = IDDLRCALC\r\n" + 
		"join DPB_KPI_FIL_EXTRT kpi on lst_kpi.LST_UPDT = kpi.ID_KPI_FIL_EXTRT\r\n" + 
		"join PO_COUNT pc on pc.ID_DLR = f.IDDLRCALC\r\n" + 
		"right outer join DPBDLR dlr on f.IDDLRCALC = dlr.IDDLR\r\n" + 
		"group by \r\n" + 
		"dlr.IDDLR\r\n" + 
		",VEH_TYP,pc.CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA),\r\n" + 
		"\r\n" + 
		"TOTALCY13RetailSales(TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_SUM_AMT_DPB_UNERND_AMT) AS \r\n" + 
		"(SELECT SUM(CY13RetailSales),SUM(Dealer_Standards_AMT),SUM(Sales_Effective_AMT),\r\n" + 
		"SUM(MB_CSI_AMT),SUM(SUM_AMT_DPB_UNERND_AMT) \r\n" + 
		"FROM PGM_DATA),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PGM_DATA2 (Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as (\r\n" + 
		"select\r\n" + 
		"max(case when cal.ID_DPB_PGM in (57)  then NAM_DPB_PGM end) as Dealer_Standards_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (59)  then NAM_DPB_PGM end) as Sales_Effective_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (58)  then NAM_DPB_PGM end) as Brand_stand_name,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 57 and VEH_TYP = 'V') then PCT_VAR_PMT end) as Dealer_Standards,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 59 and VEH_TYP = 'V') then PCT_VAR_PMT end) as Sales_Effective,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 58 and VEH_TYP = 'V') then PCT_VAR_PMT end) as MB_CSI,\r\n" + 
		"NUMRETLQTR,cal.YEARS\r\n" + 
		"from DLRPGMCALC  cal \r\n" + 
		"join DPB_PGM pgm on cal.ID_DPB_PGM = pgm.ID_DPB_PGM\r\n" + 
		"group by NUMRETLQTR,YEARS\r\n" + 
		"),\r\n" + 
		"REPORT_DATA3 (IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"TOT_CY13RetailSales,Dealer_Standards_AMT,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_UNERND_AMT,TOT_EFF) as (\r\n" + 
		"select IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"coalesce(CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')),\r\n" + 
		"coalesce(TOTAL_CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_MB_CSI_AMT,0),'999,999,990.99')) ,\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')) ,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0)+coalesce(Sales_Effective,0)+coalesce(MB_CSI,0),'90.99') || '%'\r\n" + 
		"from PGM_DATA pgm,\r\n" + 
		"TOTALCY13RetailSales\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"FINAL_DATA2(Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as (\r\n" + 
		"select trim(coalesce(Dealer_Standards_name,'')),\r\n" + 
		"trim(coalesce(Sales_Effective_name,'')), trim(coalesce(Brand_stand_name,'')),\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"case when NUMRETLQTR=1 then '1st' else case when NUMRETLQTR=2 then '2nd' \r\n" + 
		"else case when NUMRETLQTR=3 then '3rd' else '4th' end end end as NUM_RETL_QTR,YEARS\r\n" + 
		"from PGM_DATA2\r\n" + 
		"),  \r\n" + 
		"REPORT_DATA (Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"TOT_CY13RetailSales) as (\r\n" + 
		"select\r\n" + 
		"varchar_format(Float(avg(coalesce(Dealer_Standards,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(Sales_Effective,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(MB_CSI,0))),'90.99'),\r\n" + 
		"sum(coalesce(CY13RetailSales,0))\r\n" + 
		"from PGM_DATA pgm\r\n" + 
		"),\r\n" + 
		"FINAL_DATA (Dealer_Standards,Sales_Effective,MB_CSI,TOTAL_MSRP) as (\r\n" + 
		"select Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"varchar_format((select TOTALAMTMSRPOPTS from DPBCALCPOCOUNT)/TOT_CY13RetailSales,'999,999,999,990.99')\r\n" + 
		"from REPORT_DATA\r\n" + 
		"),\r\n" + 
		"Final_Result(ROWNUM,Dealer_Standards,Sales_Effective,MB_CSI,TOTAL_MSRP,Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,\r\n" +
		"Dealer_Standards2, Sales_Effective2,MB_CSI2,NUMRETLQTR,YEARS,IDDLR,REGN,MKT,ADR_DLR_CTY,ADR_DLR_STA,TOT_CY13RetailSales,Dealer_Standards3,\r\n" +
		"Sales_Effective3,MB_CSI3,TOT_EFF,Dealer_Standards_AMT,Sales_Effective_AMT,MB_CSI_AMT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" +
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_UNERND_AMT) as \r\n"+
		"(select ROW_NUMBER() OVER (ORDER BY iddlr ASC) AS ROWNUM,FD.Dealer_Standards,FD.Sales_Effective,FD.MB_CSI,FD.TOTAL_MSRP,FD2.Dealer_Standards_name,FD2.Sales_Effective_name,FD2.Brand_stand_name,\r\n" + 
		"FD2.Dealer_Standards as Dealer_Standards2,FD2.Sales_Effective as Sales_Effective2,FD2.MB_CSI as MB_CSI2,FD2.NUMRETLQTR,FD2.YEARS,RD3.IDDLR,RD3.REGN,RD3.MKT,RD3.ADR_DLR_CTY,RD3.ADR_DLR_STA,RD3.TOT_CY13RetailSales,\r\n" + 
		"RD3.Dealer_Standards as Dealer_Standards3,RD3.Sales_Effective as Sales_Effective3,RD3.MB_CSI as MB_CSI3,TOT_EFF,\r\n" + 
		"RD3.Dealer_Standards_AMT,RD3.Sales_Effective_AMT,\r\n" + 
		"RD3.MB_CSI_AMT,RD3.UNERND_AMT,RD3.TOTAL_CY13RetailSales,RD3.TOT_Dealer_Standards_AMT,\r\n" + 
		"RD3.TOT_Sales_Effective_AMT,RD3.TOT_MB_CSI_AMT,RD3.TOT_UNERND_AMT from FINAL_DATA FD,FINAL_DATA2 FD2,REPORT_DATA3 RD3\r\n" + 
		")";
public static final String DCQPR_VANS_QUERY="with DPBDLR(IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) \r\n" + 
		"AS (select DISTINCT dlr.ID_DLR,dlr.CDE_RGN,dlr.CDE_MKT,dlr.CDE_MTRO_MKT_GRP,dlr.ADR_PRIM_CITY,dlr.CDE_DLR_ST\r\n" + 
		"from DEALER_FDRT dlr  \r\n" + 
		"OLDPAYOUT_DEALER_RANGE_FOR_RPRT  \r\n" + 
		"),\r\n" + 
		"DAYFDRTTBL(DTECAL_MIN,DTECAL_MAX,NUMRETLQTR,YEARS ) as (\r\n" + 
		"select min(fdr.DTE_CAL),max(fdr.DTE_CAL),fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR \r\n" + 
		"from DPB_DAY fdr \r\n" + 
		"where (fdr.NUM_RETL_QTR,fdr.NUM_RETL_YR) in (select NUM_RETL_QTR,dim.NUM_RETL_YR from DPB_DAY dim\r\n" + 
		"     DATE_RANGE_FOR_REPORTS   )\r\n" + 
		"group by fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR),\r\n" + 
		"\r\n" + 
		"DLRPGMCALC(IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND,ID_DPB_UNBLK_VEH,NUMRETLQTR,YEARS,NUM_PO,VEH_TYP) as (\r\n" + 
		"select calc.ID_DLR,calc.ID_DPB_PGM,calc.AMT_DPB_BNS_CALC,calc.AMT_DPB_UNERND, \r\n" + 
		"calc.ID_DPB_UNBLK_VEH,dyfdrt.NUMRETLQTR,dyfdrt.YEARS, RTL.NUM_PO,rtl.CDE_VEH_TYP\r\n" + 
		"from DAYFDRTTBL dyfdrt\r\n" + 
		"join DPB_UNBLK_VEH rtl on rtl.DTE_RTL between dyfdrt.DTECAL_MIN and dyfdrt.DTECAL_MAX \r\n" + 
		"     VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"join DPB_CALC calc on calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    AND calc.ID_LDRSP_BNS_PGM IS NULL \r\n" + 
		"    --AND calc.ID_DPB_PGM IN (47,48,49,50,54,55,56,57,58,59,60,61,62)\r\n" + 
		"    AND calc.ID_DPB_PGM IN (57,59,58)\r\n" + 
		" DEALER_RANGE_FOR_REPORTS\r\n" + 
		"),\r\n" + 
		"veh_data (ID_DLR, NUM_PO, ID_DPB_UNBLK_VEH) as (\r\n" + 
		"select IDDLRCALC, NUM_PO, max(ID_DPB_UNBLK_VEH)\r\n" + 
		"from DLRPGMCALC\r\n" + 
		"group by IDDLRCALC, NUM_PO\r\n" + 
		"),\r\n" + 
		"VEHCOUNT(ID_DLR,NUM_PO,ID_DPB_UNBLK_VEH,TOTALAMTMSRPOPTS) as (\r\n" + 
		"select rtl.ID_DLR, rtl.num_po,rtl.ID_DPB_UNBLK_VEH, sum(rtl.AMT_MSRP_TOT_ACSRY+rtl.AMT_MSRP_BASE) from veh_data vd\r\n" + 
		"join DPB_UNBLK_VEH rtl\r\n" + 
		"on rtl.ID_DLR = vd.ID_DLR and rtl.ID_DPB_UNBLK_VEH = vd.ID_DPB_UNBLK_VEH\r\n" + 
		"and rtl.num_po = vd.NUM_PO and rtl.CDE_VEH_DDR_STS != 'I2' \r\n" + 
		"group by rtl.ID_DLR,rtl.num_po,rtl.ID_DPB_UNBLK_VEH),\r\n" + 
		"DPBCALCPOCOUNT(TOTALAMTMSRPOPTS) as \r\n" + 
		"(select sum(TOTALAMTMSRPOPTS) from VEHCOUNT cnt),\r\n" + 
		"PO_COUNT(ID_DLR,CY13RetailSales) as (\r\n" + 
		"select ID_DLR, count(NUM_PO) from VEHCOUNT group by ID_DLR\r\n" + 
		"),\r\n" + 
		"DLRPGMCALC_FINAL (IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, NUMRETLQTR,YEARS,VEH_TYP) as (\r\n" + 
		"select calc.IDDLRCALC,calc.ID_DPB_PGM,sum(calc.SUM_AMT_DPB_BNS_CALC),sum(calc.SUM_AMT_DPB_UNERND), calc.NUMRETLQTR,\r\n" + 
		"calc.YEARS,VEH_TYP\r\n" + 
		"from VEHCOUNT veh\r\n" + 
		"left outer join DLRPGMCALC calc on veh.ID_DLR = calc.IDDLRCALC\r\n" + 
		"and veh.NUM_PO = calc.NUM_PO\r\n" + 
		"group by calc.IDDLRCALC,calc.ID_DPB_PGM,calc.NUMRETLQTR,calc.YEARS,VEH_TYP\r\n" + 
		"),\r\n" + 
		"LST_KPI (LST_UPDT,ID_DLR,QTR,YR,ID_KPI ) AS (\r\n" + 
		"SELECT MAX(ID_KPI_FIL_EXTRT), dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI \r\n" + 
		"from DLRPGMCALC_FINAL po\r\n" + 
		"\r\n" + 
		"left outer join DPB_KPI_FIL_EXTRT dlrkpi on \r\n" + 
		"po.IDDLRCALC = dlrkpi.ID_DLR and po.NUMRETLQTR = dlrkpi.DTE_FSCL_QTR\r\n" + 
		"AND PO.YEARS = DLRKPI.DTE_FSCL_YR\r\n" + 
		"group by dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"PGM_DATA (IDDLR,Dealer_Standards,Sales_Effective,MB_CSI,Dealer_Standards_AMT,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,SUM_AMT_DPB_UNERND_AMT,VEH_TYP,CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) as (\r\n" + 
		"select\r\n" + 
		"dlr.IDDLR,\r\n" + 
		"max(case when (kpi.ID_KPI =14000  and ID_DPB_PGM = 57 and VEH_TYP = 'V') then \r\n" + 
		"kpi.PCT_KPI end) as Dealer_Standards, \r\n" + 
		"MAX(case when (kpi.ID_KPI =15000 and ID_DPB_PGM = 59 and VEH_TYP = 'V') then \r\n" + 
		"                      kpi.PCT_KPI  end) as Sales_Effective,\r\n" + 
		"MAX(case when (kpi.ID_KPI =13000 and ID_DPB_PGM = 58 and VEH_TYP = 'V') then \r\n" + 
		"                      kpi.PCT_KPI end) as MB_CSI,\r\n" + 
		"Max(case when (ID_DPB_PGM  = 57 and VEH_TYP = 'V') then \r\n" + 
		"SUM_AMT_DPB_BNS_CALC end) as Dealer_Standards_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 59 and VEH_TYP = 'V') then SUM_AMT_DPB_BNS_CALC end) as Sales_Effective_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 58 and VEH_TYP = 'V') then SUM_AMT_DPB_BNS_CALC end) as MB_CSI_AMT,\r\n" + 
		"MAX(SUM_AMT_DPB_UNERND) AS SUM_AMT_DPB_UNERND_AMT,\r\n" + 
		"VEH_TYP,pc.CY13RetailSales,\r\n" + 
		"coalesce(REGN,''),coalesce(MKT,''),coalesce(T55,''),coalesce(ADR_DLR_CTY,''),coalesce(ADR_DLR_STA,'')\r\n" + 
		"                      \r\n" + 
		"                       \r\n" + 
		"from DLRPGMCALC_FINAL  f \r\n" + 
		"join LST_KPI lst_kpi on lst_kpi.ID_DLR = IDDLRCALC\r\n" + 
		"join DPB_KPI_FIL_EXTRT kpi on lst_kpi.LST_UPDT = kpi.ID_KPI_FIL_EXTRT\r\n" + 
		"join PO_COUNT pc on pc.ID_DLR = f.IDDLRCALC\r\n" + 
		"right outer join DPBDLR dlr on f.IDDLRCALC = dlr.IDDLR\r\n" + 
		"group by \r\n" + 
		"dlr.IDDLR\r\n" + 
		",VEH_TYP,pc.CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA),\r\n" + 
		"\r\n" + 
		"TOTALCY13RetailSales(TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_SUM_AMT_DPB_UNERND_AMT) AS \r\n" + 
		"(SELECT SUM(CY13RetailSales),SUM(Dealer_Standards_AMT),SUM(Sales_Effective_AMT),\r\n" + 
		"SUM(MB_CSI_AMT),SUM(SUM_AMT_DPB_UNERND_AMT) \r\n" + 
		"FROM PGM_DATA),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PGM_DATA2 (Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as (\r\n" + 
		"select\r\n" + 
		"max(case when cal.ID_DPB_PGM in (57)  then NAM_DPB_PGM end) as Dealer_Standards_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (59)  then NAM_DPB_PGM end) as Sales_Effective_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (58)  then NAM_DPB_PGM end) as Brand_stand_name,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 57 and VEH_TYP = 'V') then PCT_VAR_PMT end) as Dealer_Standards,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 59 and VEH_TYP = 'V') then PCT_VAR_PMT end) as Sales_Effective,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 58 and VEH_TYP = 'V') then PCT_VAR_PMT end) as MB_CSI,\r\n" + 
		"NUMRETLQTR,cal.YEARS\r\n" + 
		"from DLRPGMCALC  cal \r\n" + 
		"join DPB_PGM pgm on cal.ID_DPB_PGM = pgm.ID_DPB_PGM\r\n" + 
		"group by NUMRETLQTR,YEARS\r\n" + 
		"),\r\n" + 
		"REPORT_DATA3 (IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"TOT_CY13RetailSales,Dealer_Standards_AMT,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_UNERND_AMT,TOT_EFF) as (\r\n" + 
		"select IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"coalesce(CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')),\r\n" + 
		"coalesce(TOTAL_CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_MB_CSI_AMT,0),'999,999,990.99')) ,\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')) ,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0)+coalesce(Sales_Effective,0)+coalesce(MB_CSI,0),'90.99') || '%'\r\n" + 
		"from PGM_DATA pgm,\r\n" + 
		"TOTALCY13RetailSales\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"FINAL_DATA2(Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as (\r\n" + 
		"select trim(coalesce(Dealer_Standards_name,'')),\r\n" + 
		"trim(coalesce(Sales_Effective_name,'')), trim(coalesce(Brand_stand_name,'')),\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"case when NUMRETLQTR=1 then '1st' else case when NUMRETLQTR=2 then '2nd' \r\n" + 
		"else case when NUMRETLQTR=3 then '3rd' else '4th' end end end as NUM_RETL_QTR,YEARS\r\n" + 
		"from PGM_DATA2\r\n" + 
		"),  \r\n" + 
		"REPORT_DATA (Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"TOT_CY13RetailSales) as (\r\n" + 
		"select\r\n" + 
		"varchar_format(Float(avg(coalesce(Dealer_Standards,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(Sales_Effective,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(MB_CSI,0))),'90.99'),\r\n" + 
		"sum(coalesce(CY13RetailSales,0))\r\n" + 
		"from PGM_DATA pgm\r\n" + 
		"),\r\n" + 
		"FINAL_DATA (Dealer_Standards,Sales_Effective,MB_CSI,TOTAL_MSRP) as (\r\n" + 
		"select Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"varchar_format((select TOTALAMTMSRPOPTS from DPBCALCPOCOUNT)/TOT_CY13RetailSales,'999,999,999,990.99')\r\n" + 
		"from REPORT_DATA\r\n" + 
		")\r\n" + 
		"select FD.Dealer_Standards,FD.Sales_Effective,FD.MB_CSI,FD.TOTAL_MSRP,FD2.Dealer_Standards_name,FD2.Sales_Effective_name,FD2.Brand_stand_name,\r\n" + 
		"FD2.Dealer_Standards as Dealer_Standards2,FD2.Sales_Effective as Sales_Effective2,FD2.MB_CSI as MB_CSI2,FD2.NUMRETLQTR,FD2.YEARS,RD3.IDDLR,RD3.REGN,RD3.MKT,RD3.ADR_DLR_CTY,RD3.ADR_DLR_STA,RD3.TOT_CY13RetailSales,\r\n" + 
		"RD3.Dealer_Standards as Dealer_Standards3,RD3.Sales_Effective as Sales_Effective3,RD3.MB_CSI as MB_CSI3,TOT_EFF,\r\n" + 
		"RD3.Dealer_Standards_AMT,RD3.Sales_Effective_AMT,\r\n" + 
		"RD3.MB_CSI_AMT,RD3.UNERND_AMT,RD3.TOTAL_CY13RetailSales,RD3.TOT_Dealer_Standards_AMT,\r\n" + 
		"RD3.TOT_Sales_Effective_AMT,RD3.TOT_MB_CSI_AMT,RD3.TOT_UNERND_AMT from FINAL_DATA FD,FINAL_DATA2 FD2,REPORT_DATA3 RD3\r\n" + 
		"with ur";
public static final String DCQPR_SMART_QUERY_COMMON="with DPBDLR(IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) \r\n" + 
		"AS (select DISTINCT dlr.ID_DLR,dlr.CDE_RGN,dlr.CDE_MKT,dlr.CDE_MTRO_MKT_GRP,dlr.ADR_PRIM_CITY,dlr.CDE_DLR_ST\r\n" + 
		"from DEALER_FDRT dlr  \r\n" + 
		"OLDPAYOUT_DEALER_RANGE_FOR_RPRT  \r\n" + 
		"),\r\n" + 
		"DAYFDRTTBL(DTECAL_MIN,DTECAL_MAX,NUMRETLQTR,YEARS ) as (\r\n" + 
		"select min(fdr.DTE_CAL),max(fdr.DTE_CAL),fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR \r\n" + 
		"from DPB_DAY fdr \r\n" + 
		"where (fdr.NUM_RETL_QTR,fdr.NUM_RETL_YR) in (select NUM_RETL_QTR,dim.NUM_RETL_YR from DPB_DAY dim\r\n" + 
		"     DATE_RANGE_FOR_REPORTS   )\r\n" + 
		"group by fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR),\r\n" + 
		"\r\n" + 
		"DLRPGMCALC(IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, ID_DPB_UNBLK_VEH,NUMRETLQTR,YEARS,NUM_PO,VEH_TYP) as (\r\n" + 
		"select calc.ID_DLR,calc.ID_DPB_PGM,calc.AMT_DPB_BNS_CALC,calc.AMT_DPB_UNERND,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,dyfdrt.NUMRETLQTR,dyfdrt.YEARS, RTL.NUM_PO,rtl.CDE_VEH_TYP\r\n" + 
		"from DAYFDRTTBL dyfdrt\r\n" + 
		"join DPB_UNBLK_VEH rtl on rtl.DTE_RTL between dyfdrt.DTECAL_MIN and dyfdrt.DTECAL_MAX \r\n" + 
		"     VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"join DPB_CALC calc on calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    AND calc.ID_LDRSP_BNS_PGM IS NULL \r\n" + 
		"    --AND calc.ID_DPB_PGM IN (47,48,49,50,54,55,56,57,58,59,60,61,62)\r\n" + 
		"    AND calc.ID_DPB_PGM IN (54,55,56,141)\r\n" + 
		" DEALER_RANGE_FOR_REPORTS\r\n" + 
		"),\r\n" + 
		"veh_data (ID_DLR, NUM_PO, ID_DPB_UNBLK_VEH) as (\r\n" + 
		"select IDDLRCALC, NUM_PO, max(ID_DPB_UNBLK_VEH)\r\n" + 
		"from DLRPGMCALC\r\n" + 
		"group by IDDLRCALC, NUM_PO\r\n" + 
		"),\r\n" + 
		"VEHCOUNT(ID_DLR,NUM_PO,ID_DPB_UNBLK_VEH,TOTALAMTMSRPOPTS) as (\r\n" + 
		"select rtl.ID_DLR, rtl.num_po,rtl.ID_DPB_UNBLK_VEH, sum(rtl.AMT_MSRP_TOT_ACSRY+rtl.AMT_MSRP_BASE) from veh_data vd\r\n" + 
		"join DPB_UNBLK_VEH rtl\r\n" + 
		"on rtl.ID_DLR = vd.ID_DLR and rtl.ID_DPB_UNBLK_VEH = vd.ID_DPB_UNBLK_VEH\r\n" + 
		"and rtl.num_po = vd.NUM_PO and rtl.CDE_VEH_DDR_STS != 'I2' \r\n" + 
		"group by rtl.ID_DLR,rtl.num_po,rtl.ID_DPB_UNBLK_VEH),\r\n" + 
		"DPBCALCPOCOUNT(TOTALAMTMSRPOPTS) as \r\n" + 
		"(select sum(TOTALAMTMSRPOPTS) from VEHCOUNT cnt),\r\n" + 
		"PO_COUNT(ID_DLR,CY13RetailSales) as (\r\n" + 
		"select ID_DLR, count(NUM_PO) from VEHCOUNT group by ID_DLR\r\n" + 
		"),\r\n" + 
		"DLRPGMCALC_FINAL (IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, NUMRETLQTR,YEARS,VEH_TYP) as (\r\n" + 
		"select calc.IDDLRCALC,calc.ID_DPB_PGM,sum(calc.SUM_AMT_DPB_BNS_CALC),sum(calc.SUM_AMT_DPB_UNERND), calc.NUMRETLQTR,\r\n" + 
		"calc.YEARS,VEH_TYP\r\n" + 
		"from VEHCOUNT veh\r\n" + 
		"left outer join DLRPGMCALC calc on veh.ID_DLR = calc.IDDLRCALC\r\n" + 
		"and veh.NUM_PO = calc.NUM_PO\r\n" + 
		"group by calc.IDDLRCALC,calc.ID_DPB_PGM,calc.NUMRETLQTR,calc.YEARS,VEH_TYP\r\n" + 
		"),\r\n" + 
		"LST_KPI (LST_UPDT,ID_DLR,QTR,YR,ID_KPI ) AS (\r\n" + 
		"SELECT MAX(ID_KPI_FIL_EXTRT), dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI \r\n" + 
		"from DLRPGMCALC_FINAL po\r\n" + 
		"\r\n" + 
		"left outer join DPB_KPI_FIL_EXTRT dlrkpi on \r\n" + 
		"po.IDDLRCALC = dlrkpi.ID_DLR and po.NUMRETLQTR = dlrkpi.DTE_FSCL_QTR\r\n" + 
		"AND PO.YEARS = DLRKPI.DTE_FSCL_YR\r\n" + 
		"group by dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"PGM_DATA (IDDLR,Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,Dealer_Standards_AMT,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,MB_CSI_NEW_AMT,SUM_AMT_DPB_UNERND_AMT,VEH_TYP,CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) as (\r\n" + 
		"select\r\n" + 
		"dlr.IDDLR,\r\n" + 
		"max(case when (kpi.ID_KPI =34000 and ID_DPB_PGM = 54 and VEH_TYP = 'S') then \r\n" + 
		"kpi.PCT_KPI end) as Dealer_Standards, \r\n" + 
		"MAX(case when (kpi.ID_KPI =35000 and ID_DPB_PGM = 55 and VEH_TYP = 'S') then \r\n" + 
		"                      kpi.PCT_KPI  end) as Sales_Effective,\r\n" + 
		"MAX(case when (kpi.ID_KPI =30000 and ID_DPB_PGM = 56 and VEH_TYP = 'S') then \r\n" + 
		"                      kpi.PCT_KPI end) as MB_CSI,\r\n" + 
		"MAX(case when (kpi.ID_KPI =33000 and ID_DPB_PGM = 141 and VEH_TYP = 'S') then \r\n" + 
		"kpi.PCT_KPI end) as MB_CSI_NEW,\r\n" + 
		"Max(case when ( ID_DPB_PGM  = 54 and VEH_TYP = 'S') then \r\n" + 
		"SUM_AMT_DPB_BNS_CALC end) as Dealer_Standards_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 55 and VEH_TYP = 'S') then SUM_AMT_DPB_BNS_CALC end) as Sales_Effective_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 56 and VEH_TYP = 'S') then SUM_AMT_DPB_BNS_CALC end) as MB_CSI_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 141 and VEH_TYP = 'S') then SUM_AMT_DPB_BNS_CALC end) as MB_CSI_NEW_AMT,\r\n" + 
		"MAX(SUM_AMT_DPB_UNERND) AS SUM_AMT_DPB_UNERND_AMT,\r\n" + 
		"VEH_TYP,pc.CY13RetailSales,\r\n" + 
		"coalesce(REGN,''),coalesce(MKT,''),coalesce(T55,''),coalesce(ADR_DLR_CTY,''),coalesce(ADR_DLR_STA,'')\r\n" + 
		"                                             \r\n" + 
		"from DLRPGMCALC_FINAL  f \r\n" + 
		"join LST_KPI lst_kpi on lst_kpi.ID_DLR = IDDLRCALC\r\n" + 
		"join DPB_KPI_FIL_EXTRT kpi on lst_kpi.LST_UPDT = kpi.ID_KPI_FIL_EXTRT\r\n" + 
		"join PO_COUNT pc on pc.ID_DLR = f.IDDLRCALC\r\n" + 
		"right outer join DPBDLR dlr on f.IDDLRCALC = dlr.IDDLR\r\n" + 
		"group by \r\n" + 
		"dlr.IDDLR\r\n" + 
		",VEH_TYP,pc.CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA),\r\n" + 
		"\r\n" + 
		"TOTALCY13RetailSales(TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_MB_CSI_NEW_AMT,TOT_SUM_AMT_DPB_UNERND_AMT) AS \r\n" + 
		"(SELECT SUM(CY13RetailSales),SUM(Dealer_Standards_AMT),SUM(Sales_Effective_AMT),\r\n" + 
		"SUM(MB_CSI_AMT),SUM(MB_CSI_NEW_AMT),SUM(SUM_AMT_DPB_UNERND_AMT)\r\n" + 
		"FROM PGM_DATA),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PGM_DATA2 (Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,Brand_stand_new_name,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,NUMRETLQTR,YEARS) as (\r\n" + 
		"select\r\n" + 
		"max(case when cal.ID_DPB_PGM in (54)  then NAM_DPB_PGM end) as Dealer_Standards_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (55)  then NAM_DPB_PGM end) as Sales_Effective_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (56)  then NAM_DPB_PGM end) as Brand_stand_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (141)  then NAM_DPB_PGM end) as Brand_stand_new_name,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 54 and VEH_TYP = 'S') then PCT_VAR_PMT end) as Dealer_Standards,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 55 and VEH_TYP = 'S') then PCT_VAR_PMT end) as Sales_Effective,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 56 and VEH_TYP = 'S') then PCT_VAR_PMT end) as MB_CSI,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 141 and VEH_TYP = 'S') then PCT_VAR_PMT end) as MB_CSI_NEW,\r\n" + 
		"NUMRETLQTR,cal.YEARS\r\n" + 
		"from DLRPGMCALC  cal \r\n" + 
		"join DPB_PGM pgm on cal.ID_DPB_PGM = pgm.ID_DPB_PGM\r\n" + 
		"group by NUMRETLQTR,YEARS\r\n" + 
		"),\r\n" + 
		"REPORT_DATA3 (IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,\r\n" + 
		"TOT_CY13RetailSales,Dealer_Standards_AMT,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,MB_CSI_NEW_AMT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_MB_CSI_NEW_AMT,TOT_UNERND_AMT,TOT_EFF) as (\r\n" + 
		"select IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI_NEW,0),'90.99') || '%',\r\n" + 
		"coalesce(CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_NEW_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')),\r\n" + 
		"coalesce(TOTAL_CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_MB_CSI_AMT,0),'999,999,990.99')) ,\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_MB_CSI_NEW_AMT,0),'999,999,990.99')) ,\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')) ,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0)+coalesce(Sales_Effective,0)+coalesce(MB_CSI,0)+coalesce(MB_CSI_NEW,0),'90.99') || '%'\r\n" + 
		"from PGM_DATA pgm,\r\n" + 
		"TOTALCY13RetailSales\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"FINAL_DATA2(Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,Brand_stand_new_name,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,NUMRETLQTR,YEARS) as (\r\n" + 
		"select trim(coalesce(Dealer_Standards_name,'')),\r\n" + 
		"trim(coalesce(Sales_Effective_name,'')), trim(coalesce(Brand_stand_name,'')),trim(coalesce(Brand_stand_new_name,'')),\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI_NEW,0),'90.99') || '%',\r\n" + 
		"case when NUMRETLQTR=1 then '1st' else case when NUMRETLQTR=2 then '2nd' \r\n" + 
		"else case when NUMRETLQTR=3 then '3rd' else '4th' end end end as NUM_RETL_QTR,YEARS\r\n" + 
		"from PGM_DATA2\r\n" + 
		"),  \r\n" + 
		"REPORT_DATA (Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,\r\n" + 
		"TOT_CY13RetailSales) as (\r\n" + 
		"select\r\n" + 
		"varchar_format(Float(avg(coalesce(Dealer_Standards,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(Sales_Effective,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(MB_CSI,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(MB_CSI_NEW,0))),'90.99'),\r\n" + 
		"sum(coalesce(CY13RetailSales,0))\r\n" + 
		"from PGM_DATA pgm\r\n" + 
		"),\r\n" + 
		"FINAL_DATA (Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,TOTAL_MSRP) as (\r\n" + 
		"select Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,\r\n" + 
		"varchar_format((select TOTALAMTMSRPOPTS from DPBCALCPOCOUNT)/TOT_CY13RetailSales,'999,999,999,990.99')\r\n" + 
		"from REPORT_DATA\r\n" + 
		"),\r\n" + 
		"Final_Result(ROWNUM,Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,TOTAL_MSRP,Dealer_Standards_name,Sales_Effective_name,\r\n" +
		"Brand_stand_name,Brand_stand_new_name,Dealer_Standards2,Sales_Effective2,MB_CSI2,MB_CSI_NEW2,NUMRETLQTR,YEARS,IDDLR,REGN,\r\n" +
		"MKT,ADR_DLR_CTY,ADR_DLR_STA,TOT_CY13RetailSales,Dealer_Standards3,Sales_Effective3,MB_CSI3,MB_CSI_NEW3,TOT_EFF,\r\n" +
		"Dealer_Standards_AMT,Sales_Effective_AMT,MB_CSI_AMT,MB_CSI_NEW_AMT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" +
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_MB_CSI_NEW_AMT,TOT_UNERND_AMT) as \r\n"+
		"(select ROW_NUMBER() OVER (ORDER BY iddlr ASC) AS ROWNUM,FD.Dealer_Standards,FD.Sales_Effective,FD.MB_CSI,FD.MB_CSI_NEW,FD.TOTAL_MSRP,\r\n" + 
		"FD2.Dealer_Standards_name,FD2.Sales_Effective_name,FD2.Brand_stand_name,FD2.Brand_stand_new_name,\r\n" + 
		"FD2.Dealer_Standards AS Dealer_Standards2,FD2.Sales_Effective AS Sales_Effective2,FD2.MB_CSI AS MB_CSI2,FD2.MB_CSI_NEW AS MB_CSI_NEW2,FD2.NUMRETLQTR,FD2.YEARS,RD3.IDDLR,RD3.REGN,RD3.MKT,RD3.ADR_DLR_CTY,RD3.ADR_DLR_STA,RD3.TOT_CY13RetailSales,\r\n" + 
		"RD3.Dealer_Standards AS Dealer_Standards3,RD3.Sales_Effective AS Sales_Effective3,RD3.MB_CSI AS MB_CSI3,RD3.MB_CSI_NEW AS MB_CSI_NEW3,TOT_EFF,\r\n" + 
		"RD3.Dealer_Standards_AMT,RD3.Sales_Effective_AMT,\r\n" + 
		"RD3.MB_CSI_AMT,RD3.MB_CSI_NEW_AMT,RD3.UNERND_AMT,RD3.TOTAL_CY13RetailSales,RD3.TOT_Dealer_Standards_AMT,\r\n" + 
		"RD3.TOT_Sales_Effective_AMT,RD3.TOT_MB_CSI_AMT,RD3.TOT_MB_CSI_NEW_AMT,RD3.TOT_UNERND_AMT from FINAL_DATA FD,FINAL_DATA2 FD2,REPORT_DATA3 RD3\r\n" + 
		")";

public static final String DCQPR_SMART_QUERY="with DPBDLR(IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) \r\n" + 
		"AS (select DISTINCT dlr.ID_DLR,dlr.CDE_RGN,dlr.CDE_MKT,dlr.CDE_MTRO_MKT_GRP,dlr.ADR_PRIM_CITY,dlr.CDE_DLR_ST\r\n" + 
		"from DEALER_FDRT dlr  \r\n" + 
		"OLDPAYOUT_DEALER_RANGE_FOR_RPRT  \r\n" + 
		"),\r\n" + 
		"DAYFDRTTBL(DTECAL_MIN,DTECAL_MAX,NUMRETLQTR,YEARS ) as (\r\n" + 
		"select min(fdr.DTE_CAL),max(fdr.DTE_CAL),fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR \r\n" + 
		"from DPB_DAY fdr \r\n" + 
		"where (fdr.NUM_RETL_QTR,fdr.NUM_RETL_YR) in (select NUM_RETL_QTR,dim.NUM_RETL_YR from DPB_DAY dim\r\n" + 
		"     DATE_RANGE_FOR_REPORTS   )\r\n" + 
		"group by fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR),\r\n" + 
		"\r\n" + 
		"DLRPGMCALC(IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, ID_DPB_UNBLK_VEH,NUMRETLQTR,YEARS,NUM_PO,VEH_TYP) as (\r\n" + 
		"select calc.ID_DLR,calc.ID_DPB_PGM,calc.AMT_DPB_BNS_CALC,calc.AMT_DPB_UNERND,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,dyfdrt.NUMRETLQTR,dyfdrt.YEARS, RTL.NUM_PO,rtl.CDE_VEH_TYP\r\n" + 
		"from DAYFDRTTBL dyfdrt\r\n" + 
		"join DPB_UNBLK_VEH rtl on rtl.DTE_RTL between dyfdrt.DTECAL_MIN and dyfdrt.DTECAL_MAX \r\n" + 
		"     VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"join DPB_CALC calc on calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    AND calc.ID_LDRSP_BNS_PGM IS NULL \r\n" + 
		"    --AND calc.ID_DPB_PGM IN (47,48,49,50,54,55,56,57,58,59,60,61,62)\r\n" + 
		"    AND calc.ID_DPB_PGM IN (54,55,56,141)\r\n" + 
		" DEALER_RANGE_FOR_REPORTS\r\n" + 
		"),\r\n" + 
		"veh_data (ID_DLR, NUM_PO, ID_DPB_UNBLK_VEH) as (\r\n" + 
		"select IDDLRCALC, NUM_PO, max(ID_DPB_UNBLK_VEH)\r\n" + 
		"from DLRPGMCALC\r\n" + 
		"group by IDDLRCALC, NUM_PO\r\n" + 
		"),\r\n" + 
		"VEHCOUNT(ID_DLR,NUM_PO,ID_DPB_UNBLK_VEH,TOTALAMTMSRPOPTS) as (\r\n" + 
		"select rtl.ID_DLR, rtl.num_po,rtl.ID_DPB_UNBLK_VEH, sum(rtl.AMT_MSRP_TOT_ACSRY+rtl.AMT_MSRP_BASE) from veh_data vd\r\n" + 
		"join DPB_UNBLK_VEH rtl\r\n" + 
		"on rtl.ID_DLR = vd.ID_DLR and rtl.ID_DPB_UNBLK_VEH = vd.ID_DPB_UNBLK_VEH\r\n" + 
		"and rtl.num_po = vd.NUM_PO and rtl.CDE_VEH_DDR_STS != 'I2' \r\n" + 
		"group by rtl.ID_DLR,rtl.num_po,rtl.ID_DPB_UNBLK_VEH),\r\n" + 
		"DPBCALCPOCOUNT(TOTALAMTMSRPOPTS) as \r\n" + 
		"(select sum(TOTALAMTMSRPOPTS) from VEHCOUNT cnt),\r\n" + 
		"PO_COUNT(ID_DLR,CY13RetailSales) as (\r\n" + 
		"select ID_DLR, count(NUM_PO) from VEHCOUNT group by ID_DLR\r\n" + 
		"),\r\n" + 
		"DLRPGMCALC_FINAL (IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, NUMRETLQTR,YEARS,VEH_TYP) as (\r\n" + 
		"select calc.IDDLRCALC,calc.ID_DPB_PGM,sum(calc.SUM_AMT_DPB_BNS_CALC),sum(calc.SUM_AMT_DPB_UNERND), calc.NUMRETLQTR,\r\n" + 
		"calc.YEARS,VEH_TYP\r\n" + 
		"from VEHCOUNT veh\r\n" + 
		"left outer join DLRPGMCALC calc on veh.ID_DLR = calc.IDDLRCALC\r\n" + 
		"and veh.NUM_PO = calc.NUM_PO\r\n" + 
		"group by calc.IDDLRCALC,calc.ID_DPB_PGM,calc.NUMRETLQTR,calc.YEARS,VEH_TYP\r\n" + 
		"),\r\n" + 
		"LST_KPI (LST_UPDT,ID_DLR,QTR,YR,ID_KPI ) AS (\r\n" + 
		"SELECT MAX(ID_KPI_FIL_EXTRT), dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI \r\n" + 
		"from DLRPGMCALC_FINAL po\r\n" + 
		"\r\n" + 
		"left outer join DPB_KPI_FIL_EXTRT dlrkpi on \r\n" + 
		"po.IDDLRCALC = dlrkpi.ID_DLR and po.NUMRETLQTR = dlrkpi.DTE_FSCL_QTR\r\n" + 
		"AND PO.YEARS = DLRKPI.DTE_FSCL_YR\r\n" + 
		"group by dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"PGM_DATA (IDDLR,Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,Dealer_Standards_AMT,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,MB_CSI_NEW_AMT,SUM_AMT_DPB_UNERND_AMT,VEH_TYP,CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) as (\r\n" + 
		"select\r\n" + 
		"dlr.IDDLR,\r\n" + 
		"max(case when (kpi.ID_KPI =34000 and ID_DPB_PGM = 54 and VEH_TYP = 'S') then \r\n" + 
		"kpi.PCT_KPI end) as Dealer_Standards, \r\n" + 
		"MAX(case when (kpi.ID_KPI =35000 and ID_DPB_PGM = 55 and VEH_TYP = 'S') then \r\n" + 
		"                      kpi.PCT_KPI  end) as Sales_Effective,\r\n" + 
		"MAX(case when (kpi.ID_KPI =30000 and ID_DPB_PGM = 56 and VEH_TYP = 'S') then \r\n" + 
		"                      kpi.PCT_KPI end) as MB_CSI,\r\n" + 
		"MAX(case when (kpi.ID_KPI =33000 and ID_DPB_PGM = 141 and VEH_TYP = 'S') then \r\n" + 
		"kpi.PCT_KPI end) as MB_CSI_NEW,\r\n" + 
		"Max(case when ( ID_DPB_PGM  = 54 and VEH_TYP = 'S') then \r\n" + 
		"SUM_AMT_DPB_BNS_CALC end) as Dealer_Standards_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 55 and VEH_TYP = 'S') then SUM_AMT_DPB_BNS_CALC end) as Sales_Effective_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 56 and VEH_TYP = 'S') then SUM_AMT_DPB_BNS_CALC end) as MB_CSI_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 141 and VEH_TYP = 'S') then SUM_AMT_DPB_BNS_CALC end) as MB_CSI_NEW_AMT,\r\n" + 
		"MAX(SUM_AMT_DPB_UNERND) AS SUM_AMT_DPB_UNERND_AMT,\r\n" + 
		"VEH_TYP,pc.CY13RetailSales,\r\n" + 
		"coalesce(REGN,''),coalesce(MKT,''),coalesce(T55,''),coalesce(ADR_DLR_CTY,''),coalesce(ADR_DLR_STA,'')\r\n" + 
		"                                             \r\n" + 
		"from DLRPGMCALC_FINAL  f \r\n" + 
		"join LST_KPI lst_kpi on lst_kpi.ID_DLR = IDDLRCALC\r\n" + 
		"join DPB_KPI_FIL_EXTRT kpi on lst_kpi.LST_UPDT = kpi.ID_KPI_FIL_EXTRT\r\n" + 
		"join PO_COUNT pc on pc.ID_DLR = f.IDDLRCALC\r\n" + 
		"right outer join DPBDLR dlr on f.IDDLRCALC = dlr.IDDLR\r\n" + 
		"group by \r\n" + 
		"dlr.IDDLR\r\n" + 
		",VEH_TYP,pc.CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA),\r\n" + 
		"\r\n" + 
		"TOTALCY13RetailSales(TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_MB_CSI_NEW_AMT,TOT_SUM_AMT_DPB_UNERND_AMT) AS \r\n" + 
		"(SELECT SUM(CY13RetailSales),SUM(Dealer_Standards_AMT),SUM(Sales_Effective_AMT),\r\n" + 
		"SUM(MB_CSI_AMT),SUM(MB_CSI_NEW_AMT),SUM(SUM_AMT_DPB_UNERND_AMT)\r\n" + 
		"FROM PGM_DATA),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PGM_DATA2 (Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,Brand_stand_new_name,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,NUMRETLQTR,YEARS) as (\r\n" + 
		"select\r\n" + 
		"max(case when cal.ID_DPB_PGM in (54)  then NAM_DPB_PGM end) as Dealer_Standards_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (55)  then NAM_DPB_PGM end) as Sales_Effective_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (56)  then NAM_DPB_PGM end) as Brand_stand_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM in (141)  then NAM_DPB_PGM end) as Brand_stand_new_name,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 54 and VEH_TYP = 'S') then PCT_VAR_PMT end) as Dealer_Standards,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 55 and VEH_TYP = 'S') then PCT_VAR_PMT end) as Sales_Effective,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 56 and VEH_TYP = 'S') then PCT_VAR_PMT end) as MB_CSI,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 141 and VEH_TYP = 'S') then PCT_VAR_PMT end) as MB_CSI_NEW,\r\n" + 
		"NUMRETLQTR,cal.YEARS\r\n" + 
		"from DLRPGMCALC  cal \r\n" + 
		"join DPB_PGM pgm on cal.ID_DPB_PGM = pgm.ID_DPB_PGM\r\n" + 
		"group by NUMRETLQTR,YEARS\r\n" + 
		"),\r\n" + 
		"REPORT_DATA3 (IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,\r\n" + 
		"TOT_CY13RetailSales,Dealer_Standards_AMT,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,MB_CSI_NEW_AMT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_MB_CSI_NEW_AMT,TOT_UNERND_AMT,TOT_EFF) as (\r\n" + 
		"select IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI_NEW,0),'90.99') || '%',\r\n" + 
		"coalesce(CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_NEW_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')),\r\n" + 
		"coalesce(TOTAL_CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Dealer_Standards_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Sales_Effective_AMT,0),'999,999,990.99')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_MB_CSI_AMT,0),'999,999,990.99')) ,\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_MB_CSI_NEW_AMT,0),'999,999,990.99')) ,\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_SUM_AMT_DPB_UNERND_AMT,0),'999,999,990.99')) ,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0)+coalesce(Sales_Effective,0)+coalesce(MB_CSI,0)+coalesce(MB_CSI_NEW,0),'90.99') || '%'\r\n" + 
		"from PGM_DATA pgm,\r\n" + 
		"TOTALCY13RetailSales\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"FINAL_DATA2(Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,Brand_stand_new_name,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,NUMRETLQTR,YEARS) as (\r\n" + 
		"select trim(coalesce(Dealer_Standards_name,'')),\r\n" + 
		"trim(coalesce(Sales_Effective_name,'')), trim(coalesce(Brand_stand_name,'')),trim(coalesce(Brand_stand_new_name,'')),\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI_NEW,0),'90.99') || '%',\r\n" + 
		"case when NUMRETLQTR=1 then '1st' else case when NUMRETLQTR=2 then '2nd' \r\n" + 
		"else case when NUMRETLQTR=3 then '3rd' else '4th' end end end as NUM_RETL_QTR,YEARS\r\n" + 
		"from PGM_DATA2\r\n" + 
		"),  \r\n" + 
		"REPORT_DATA (Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,\r\n" + 
		"TOT_CY13RetailSales) as (\r\n" + 
		"select\r\n" + 
		"varchar_format(Float(avg(coalesce(Dealer_Standards,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(Sales_Effective,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(MB_CSI,0))),'90.99'),\r\n" + 
		"varchar_format(Float(avg(coalesce(MB_CSI_NEW,0))),'90.99'),\r\n" + 
		"sum(coalesce(CY13RetailSales,0))\r\n" + 
		"from PGM_DATA pgm\r\n" + 
		"),\r\n" + 
		"FINAL_DATA (Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,TOTAL_MSRP) as (\r\n" + 
		"select Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,\r\n" + 
		"varchar_format((select TOTALAMTMSRPOPTS from DPBCALCPOCOUNT)/TOT_CY13RetailSales,'999,999,999,990.99')\r\n" + 
		"from REPORT_DATA\r\n" + 
		")\r\n" + 
		"select FD.Dealer_Standards,FD.Sales_Effective,FD.MB_CSI,FD.MB_CSI_NEW,FD.TOTAL_MSRP,\r\n" + 
		"FD2.Dealer_Standards_name,FD2.Sales_Effective_name,FD2.Brand_stand_name,FD2.Brand_stand_new_name,\r\n" + 
		"FD2.Dealer_Standards AS Dealer_Standards2,FD2.Sales_Effective AS Sales_Effective2,FD2.MB_CSI AS MB_CSI2,FD2.MB_CSI_NEW AS MB_CSI_NEW2,FD2.NUMRETLQTR,FD2.YEARS,RD3.IDDLR,RD3.REGN,RD3.MKT,RD3.ADR_DLR_CTY,RD3.ADR_DLR_STA,RD3.TOT_CY13RetailSales,\r\n" + 
		"RD3.Dealer_Standards AS Dealer_Standards3,RD3.Sales_Effective AS Sales_Effective3,RD3.MB_CSI AS MB_CSI3,RD3.MB_CSI_NEW AS MB_CSI_NEW3,TOT_EFF,\r\n" + 
		"RD3.Dealer_Standards_AMT,RD3.Sales_Effective_AMT,\r\n" + 
		"RD3.MB_CSI_AMT,RD3.MB_CSI_NEW_AMT,RD3.UNERND_AMT,RD3.TOTAL_CY13RetailSales,RD3.TOT_Dealer_Standards_AMT,\r\n" + 
		"RD3.TOT_Sales_Effective_AMT,RD3.TOT_MB_CSI_AMT,RD3.TOT_MB_CSI_NEW_AMT,RD3.TOT_UNERND_AMT from FINAL_DATA FD,FINAL_DATA2 FD2,REPORT_DATA3 RD3\r\n" + 
		"with ur";
//Dealer Compliance Quarterly Payouts Report  - End
//Vehicle Details Report-start

//Vehicle Details Report-start
public static final String VEHICLE_DETAILS_ALL="with basic_data(       DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,\r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,DTE_TRANS,\r\n" + 
		"TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP,BLOCKED_DATE, BLOCKED_REASON\r\n" + 
		") as \r\n" + 
		"( select DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,\r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,\r\n" + 
		"DTE_TRANS,TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP,null,null\r\n" + 
		"from DPB_UNBLK_VEH AS rtl \r\n" + 
		"WHERE PO_RANGE_FOR_REPORTS\r\n" + 
		"VIN_RANGE_FOR_REPORTS \r\n" + 
		"DEALER_RANGE_FOR_REPORTS \r\n" + 
		"DATE_RANGE_FOR_REPORTS   \r\n" + 
		"--select * from basic_data with ur\r\n" + 
		"UNION \r\n" + 
		"  --31 columns\r\n" + 
		"-- with basic_data\r\n" + 
		"--(DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,\r\n" + 
		"--IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,DTE_TRANS,\r\n" + 
		"--TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP,BLOCKED_DATE, BLOCKED_REASON\r\n" + 
		"--)as \r\n" + 
		"select DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,\r\n" +        
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,\r\n" + 
		"DTE_TRANS,TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP,\r\n" + 
		"rtl.DTS_LAST_UPDT,(case when trim(NAM_DPB_VAR) = 'CDE_WHSLE_INIT_TYP' then 'Blocked due to the wholesale initiation type is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')\r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'CDE_RGN' then 'Blocked due to the Code Region is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'') \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'CDE_VEH_DDR_STS' then 'Blocked due to the vehicle status is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'') \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'IND_FLT' then 'Blocked due to the fleet indicator is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'') \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'IND_USED_VEH_DDRS' then 'Blocked due to the used car indicator is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'') \r\n" + 
		"else 'NA' \r\n" + 
		"end end end end end)\r\n" + 
		"FROM DPB_BLK_VEH rtl join DPB_CND CD on rtl.ID_DPB_CND = CD.ID_DPB_CND\r\n" + 
		"WHERE PO_RANGE_FOR_REPORTS\r\n" + 
		"VIN_RANGE_FOR_REPORTS \r\n" + 
		"DEALER_RANGE_FOR_REPORTS \r\n" + 
		"DATE_RANGE_FOR_REPORTS  \r\n" + 
		"order by dte_rtl,tme_rtl,id_dlr,num_po,des_modl\r\n" + 
		")\r\n" + 
		"select * from basic_data with ur";

public static final String VEHICLE_DETAILS_ALL_BLOCKED_1="WITH basic_data ( AMT_MSRP_BASE, AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP ) as  \r\n" + 
		"( select COALESCE(AMT_MSRP_BASE,0), COALESCE(AMT_MSRP_TOT_ACSRY,0),COALESCE(AMT_DLR_RBT,0),COALESCE(AMT_MSRP,0) from DPB_BLK_VEH AS rtl \r\n" + 
		"WHERE \r\n" + 
		"PO_RANGE_FOR_REPORTS   \r\n" + 
		"VIN_RANGE_FOR_REPORTS  \r\n" + 
		"DEALER_RANGE_FOR_REPORTS  \r\n" + 
		"DATE_RANGE_FOR_REPORTS   \r\n" + 
		"ORDER BY ID_DLR,DTE_RTL \r\n" + 
		"), \r\n" + 
		"totals(TEXT,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP) as ( \r\n" + 
		"select 'Total:               ' as TEXT, \r\n" + 
		"SUM(AMT_MSRP_BASE) as AMT_MSRP_BASE , \r\n" + 
		"SUM(AMT_MSRP_TOT_ACSRY) as AMT_MSRP_TOT_ACSRY, \r\n" + 
		"SUM(AMT_DLR_RBT) as AMT_DLR_RBT, \r\n" + 
		"SUM(AMT_MSRP) as AMT_MSRP \r\n" + 
		"from basic_data \r\n" + 
		")select * from totals with ur";
public static final String VEHICLE_DETAILS_ALL_UNBLOCKED_1="WITH basic_data ( AMT_MSRP_BASE, AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP ) as  \r\n" + 
		"( select COALESCE(AMT_MSRP_BASE,0), COALESCE(AMT_MSRP_TOT_ACSRY,0),COALESCE(AMT_DLR_RBT,0),COALESCE(AMT_MSRP,0) from DPB_UNBLK_VEH AS rtl   \r\n" + 
		"WHERE  \r\n" + 
		"PO_RANGE_FOR_REPORTS   \r\n" + 
   	    "VIN_RANGE_FOR_REPORTS   \r\n" + 
		"DEALER_RANGE_FOR_REPORTS  \r\n" + 
		"DATE_RANGE_FOR_REPORTS   \r\n" + 
 		"ORDER BY ID_DLR,DTE_RTL \r\n" + 
		"), \r\n" + 
		"totals(TEXT,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP) as ( \r\n" + 
		"select 'Total:               ' as TEXT, \r\n" + 
		"SUM(AMT_MSRP_BASE) as AMT_MSRP_BASE , \r\n" + 
		"SUM(AMT_MSRP_TOT_ACSRY) as AMT_MSRP_TOT_ACSRY, \r\n" + 
		"SUM(AMT_DLR_RBT) as AMT_DLR_RBT, \r\n" + 
		"SUM(AMT_MSRP) as AMT_MSRP  \r\n" + 
		"from basic_data  \r\n" + 
		")select * from totals with ur";
public static final String VEHICLE_DETAILS_ALL_TOTAL="WITH basic_data (AMT_MSRP_BASE, AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP) as \r\n" + 
		"(select COALESCE(AMT_MSRP_BASE,0), COALESCE(AMT_MSRP_TOT_ACSRY,0),COALESCE(AMT_DLR_RBT,0),COALESCE(AMT_MSRP,0) from DPB_UNBLK_VEH AS rtl  \r\n" + 
		"WHERE  \r\n" + 
		"PO_RANGE_FOR_REPORTS   \r\n" + 
		"VIN_RANGE_FOR_REPORTS    \r\n" + 
		"DEALER_RANGE_FOR_REPORTS  \r\n" + 
		"DATE_RANGE_FOR_REPORTS   \r\n" + 
		"UNION ALL \r\n" + 
		"select COALESCE(AMT_MSRP_BASE,0), COALESCE(AMT_MSRP_TOT_ACSRY,0),COALESCE(AMT_DLR_RBT,0),COALESCE(AMT_MSRP,0) from DPB_BLK_VEH AS rtl  \r\n" + 
		"WHERE \r\n" + 
		"PO_RANGE_FOR_REPORTS   \r\n" + 
		"VIN_RANGE_FOR_REPORTS    \r\n" + 
		"DEALER_RANGE_FOR_REPORTS  \r\n" + 
		"DATE_RANGE_FOR_REPORTS   \r\n" + 
		"),\r\n" + 
		"totals(TEXT,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP) as ( \r\n" + 
		"select 'Total:               ' as TEXT, \r\n" + 
		"SUM(AMT_MSRP_BASE) as AMT_MSRP_BASE , \r\n" + 
		"SUM(AMT_MSRP_TOT_ACSRY) as AMT_MSRP_TOT_ACSRY, \r\n" + 
		"SUM(AMT_DLR_RBT) as AMT_DLR_RBT, \r\n" + 
		"SUM(AMT_MSRP) as AMT_MSRP  \r\n" + 
		"from basic_data) \r\n" + 
		"select * from totals with ur";
public static final String VEHICLE_DETAILS_ALL_1="WITH basic_data ( AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP) as \r\n" + 
		"( select distinct (COALESCE(rtl.AMT_MSRP_BASE,0)+COALESCE(rtl1.AMT_MSRP_BASE,0)),(COALESCE(rtl.AMT_MSRP_TOT_ACSRY,0)+COALESCE(rtl1.AMT_MSRP_TOT_ACSRY,0)), \r\n" + 
		"(COALESCE(rtl.AMT_DLR_RBT,0)+COALESCE(rtl1.AMT_DLR_RBT,0)),(COALESCE(rtl.AMT_MSRP,0)+COALESCE(rtl1.AMT_MSRP,0))\r\n" + 
		"　\r\n" + 
		"from DPB_UNBLK_VEH AS rtl1 left outer join DPB_BLK_VEH AS rtl\r\n" + 
		"on rtl.ID_DLR= rtl1.ID_DLR\r\n" + 
		"WHERE PO_RANGE_FOR_REPORTS \r\n" + 
		"VIN_RANGE_FOR_REPORTS \r\n" + 
		"DEALER_RANGE_FOR_REPORTS \r\n" +
		" PO_RANGE_FOR_REPORTS \r\n" + 
		"VIN_RANGE_FOR_REPORTS \r\n" + 
		"DEALER_RANGE_FOR_REPORTS\r\n" + 
		" DATE_RANGE_FOR_REPORTS \r\n" + 
		" DATE_RANGE_FOR_REPORTS\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"totals(TEXT,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP) as (\r\n" + 
		"select 'Total: ' as TEXT,\r\n" + 
		"SUM(AMT_MSRP_BASE) as AMT_MSRP_BASE ,\r\n" + 
		"SUM(AMT_MSRP_TOT_ACSRY) as AMT_MSRP_TOT_ACSRY,\r\n" + 
		"SUM(AMT_DLR_RBT) as AMT_DLR_RBT,\r\n" + 
		"SUM(AMT_MSRP) as AMT_MSRP \r\n" + 
		"from basic_data \r\n" + 
		")\r\n" + 
		"select * from totals with ur";
public static final String VEHICLE_DETAILS_UNBLCKD="with basic_data(	DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,\r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,DTE_TRANS,\r\n" + 
		"TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP\r\n" + 
		") as \r\n" + 
		"( select DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,\r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,\r\n" + 
		"DTE_TRANS,TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP									\r\n" + 
		"from DPB_UNBLK_VEH AS rtl \r\n" + 
		"WHERE PO_RANGE_FOR_REPORTS  \r\n" + 
		"VIN_RANGE_FOR_REPORTS \r\n" + 
		"DEALER_RANGE_FOR_REPORTS \r\n" + 
		"DATE_RANGE_FOR_REPORTS \r\n" + 
		"order by dte_rtl,tme_rtl,id_dlr,num_po,des_modl\r\n" + 
		") select * from basic_data with ur";
public static final String VEHICLE_DETAILS_UNBLCKD_1="WITH basic_data ( AMT_MSRP_BASE, AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP ) as \r\n" + 
		"( select COALESCE(AMT_MSRP_BASE,0), COALESCE(AMT_MSRP_TOT_ACSRY,0),COALESCE(AMT_DLR_RBT,0),COALESCE(AMT_MSRP,0) from DPB_UNBLK_VEH AS rtl \r\n" + 
		"WHERE \r\n" + 
		"PO_RANGE_FOR_REPORTS  \r\n" + 
		"VIN_RANGE_FOR_REPORTS   \r\n" + 
		"DEALER_RANGE_FOR_REPORTS \r\n" + 
		"DATE_RANGE_FOR_REPORTS  \r\n" + 
		"ORDER BY ID_DLR,DTE_RTL\r\n" + 
		"),\r\n" + 
		"totals(TEXT,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP) as (\r\n" + 
		"select 'Total:               ' as TEXT,\r\n" + 
		"SUM(AMT_MSRP_BASE) as AMT_MSRP_BASE ,\r\n" + 
		"SUM(AMT_MSRP_TOT_ACSRY) as AMT_MSRP_TOT_ACSRY,\r\n" + 
		"SUM(AMT_DLR_RBT) as AMT_DLR_RBT,\r\n" + 
		"SUM(AMT_MSRP) as AMT_MSRP \r\n" + 
		"from basic_data \r\n" + 
		")select * from totals with ur";
public static final String VEHICLE_DETAILS_BLOCKED="with basic_data\r\n" + 
		"(DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,\r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,DTE_TRANS,\r\n" + 
		"TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP,BLOCKED_DATE, BLOCKED_REASON\r\n" + 
		") \r\n" + 
		"as  \r\n" + 
		"(select DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,\r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,\r\n" + 
		"DTE_TRANS,TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP,\r\n" + 
		"rtl.DTS_LAST_UPDT, \r\n" + 
		"(case when trim(NAM_DPB_VAR) = 'CDE_WHSLE_INIT_TYP' then 'Blocked due to the wholesale initiation type is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')  \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'CDE_RGN' then 'Blocked due to the Code Region is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')  \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'CDE_VEH_DDR_STS' then 'Blocked due to the vehicle status is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')  \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'IND_FLT' then 'Blocked due to the fleet indicator is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')  \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'IND_USED_VEH_DDRS' then 'Blocked due to the used car indicator is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')  \r\n" + 
		"else 'NA'  \r\n" + 
		"end end end end end) \r\n" + 
		"FROM DPB_BLK_VEH rtl join DPB_CND CD on rtl.ID_DPB_CND = CD.ID_DPB_CND \r\n" + 
		"WHERE PO_RANGE_FOR_REPORTS\r\n" + 
		"VIN_RANGE_FOR_REPORTS\r\n" + 
		"DEALER_RANGE_FOR_REPORTS\r\n" + 
		"DATE_RANGE_FOR_REPORTS \r\n" + 
		"order by dte_rtl,tme_rtl,id_dlr,num_po,des_modl\r\n" +  
		") \r\n" + 
		"select * from basic_data with ur";
public static final String VEHICLE_DETAILS_BLOCKED_1="WITH basic_data ( AMT_MSRP_BASE, AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP ) as \r\n" + 
		"( select COALESCE(AMT_MSRP_BASE,0), COALESCE(AMT_MSRP_TOT_ACSRY,0),COALESCE(AMT_DLR_RBT,0),COALESCE(AMT_MSRP,0) from DPB_BLK_VEH AS rtl \r\n" + 
		"WHERE \r\n" + 
		"PO_RANGE_FOR_REPORTS  \r\n" + 
		"VIN_RANGE_FOR_REPORTS   \r\n" + 
		"DEALER_RANGE_FOR_REPORTS \r\n" + 
		"DATE_RANGE_FOR_REPORTS  \r\n" + 
		"ORDER BY ID_DLR,DTE_RTL\r\n" + 
		"),\r\n" + 
		"totals(TEXT,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP) as (\r\n" + 
		"select 'Total:               ' as TEXT,\r\n" + 
		"SUM(AMT_MSRP_BASE) as AMT_MSRP_BASE ,\r\n" + 
		"SUM(AMT_MSRP_TOT_ACSRY) as AMT_MSRP_TOT_ACSRY,\r\n" + 
		"SUM(AMT_DLR_RBT) as AMT_DLR_RBT,\r\n" + 
		"SUM(AMT_MSRP) as AMT_MSRP \r\n" + 
		"from basic_data \r\n" + 
		")select * from totals with ur";

public static final String VEHICLE_DETAILS_ALL_COMMON="with basic_data(       DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,  \r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,DTE_TRANS,  \r\n" + 
		"TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP,BLOCKED_DATE, BLOCKED_REASON  \r\n" + 
		") as   \r\n" + 
		"( select DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,  \r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,  \r\n" + 
		"DTE_TRANS,TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP,null,null  \r\n" + 
		"from DPB_UNBLK_VEH AS rtl   \r\n" + 
		"WHERE PO_RANGE_FOR_REPORTS  \r\n" + 
		"VIN_RANGE_FOR_REPORTS   \r\n" + 
		"DEALER_RANGE_FOR_REPORTS   \r\n" + 
		"DATE_RANGE_FOR_REPORTS \r\n" + 
		"--where dte_rtl between '2016-08-01' and '2016-08-02'\r\n" + 
		"UNION ALL\r\n" + 
		"select DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,  \r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,  \r\n" + 
		"DTE_TRANS,TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP,  \r\n" + 
		"rtl.DTS_LAST_UPDT,(case when trim(NAM_DPB_VAR) = 'CDE_WHSLE_INIT_TYP' then 'Blocked due to the wholesale initiation type is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || CDE_VEH_DDR_USE   \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'CDE_RGN' then 'Blocked due to the Code Region is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')   \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'CDE_VEH_DDR_STS' then 'Blocked due to the vehicle status is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')   \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'IND_FLT' then 'Blocked due to the fleet indicator is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')   \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'IND_USED_VEH_DDRS' then 'Blocked due to the used car indicator is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')   \r\n" + 
		"else 'NA'   \r\n" + 
		"end end end end end)  \r\n" + 
		"FROM DPB_BLK_VEH rtl join DPB_CND CD on rtl.ID_DPB_CND = CD.ID_DPB_CND  \r\n" + 
		"WHERE PO_RANGE_FOR_REPORTS  \r\n" + 
		"VIN_RANGE_FOR_REPORTS   \r\n" + 
		"DEALER_RANGE_FOR_REPORTS   \r\n" + 
		"DATE_RANGE_FOR_REPORTS \r\n" + 
		"--where dte_rtl between '2016-08-01' and '2016-08-02'   \r\n" + 
		"),\r\n" + 
		"Final_Result(ROWNUM,DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,  \r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,DTE_TRANS,  \r\n" + 
		"TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP,BLOCKED_DATE, BLOCKED_REASON  \r\n" + 
		") \r\n" + 
		"as(  \r\n" + 
		"select  ROW_NUMBER() OVER (ORDER BY dte_rtl, tme_rtl,id_dlr,num_po,des_modl ASC) AS ROWNUM,        DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,  \r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,DTE_TRANS,  \r\n" + 
		"TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP,BLOCKED_DATE, BLOCKED_REASON  \r\n" + 
		" from basic_data order by dte_rtl, tme_rtl,id_dlr,num_po,des_modl)\r\n" + 
		"";
public static final String VEHICLE_DETAILS_BLOCKED_COMMON="with Final_Result  \r\n" + 
		"(ROWNUM,DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,  \r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,DTE_TRANS,  \r\n" + 
		"TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP,BLOCKED_DATE, BLOCKED_REASON  \r\n" + 
		")   \r\n" + 
		"as    \r\n" + 
		"(select ROW_NUMBER() OVER (ORDER BY dte_rtl, tme_rtl,id_dlr,num_po,des_modl ASC) AS ROWNUM,DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,  \r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,  \r\n" + 
		"DTE_TRANS,TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP,  \r\n" + 
		"rtl.DTS_LAST_UPDT,   \r\n" + 
		"(case when trim(NAM_DPB_VAR) = 'CDE_WHSLE_INIT_TYP' then 'Blocked due to the wholesale initiation type is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')    \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'CDE_RGN' then 'Blocked due to the Code Region is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')    \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'CDE_VEH_DDR_STS' then 'Blocked due to the vehicle status is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')    \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'IND_FLT' then 'Blocked due to the fleet indicator is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')    \r\n" + 
		"else case when trim(NAM_DPB_VAR) = 'IND_USED_VEH_DDRS' then 'Blocked due to the used car indicator is '||  COALESCE(TXT_DPB_CHK_VAL,'') || ' ' || COALESCE(CDE_VEH_DDR_USE,'')    \r\n" + 
		"else 'NA'    \r\n" + 
		"end end end end end)   \r\n" + 
		"FROM DPB_BLK_VEH rtl join DPB_CND CD on rtl.ID_DPB_CND = CD.ID_DPB_CND   \r\n" + 
		"WHERE \r\n" + 
		"PO_RANGE_FOR_REPORTS  \r\n" + 
		"VIN_RANGE_FOR_REPORTS  \r\n" + 
		"DEALER_RANGE_FOR_REPORTS  \r\n" + 
		"DATE_RANGE_FOR_REPORTS \r\n" + 
		"order by dte_rtl,tme_rtl,id_dlr,num_po,des_modl\r\n" + 
		") ";
public static final String VEHICLE_DETAILS_UNBLOCKED_COMMON = "with Final_Result\r\n" + 
		"(ROWNUM,DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,\r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,DTE_TRANS,  \r\n" + 
		"TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP  \r\n" + 
		") as   \r\n" + 
		"( select ROW_NUMBER() OVER (ORDER BY dte_rtl, tme_rtl,id_dlr,num_po,des_modl ASC) AS ROWNUM,DTE_RTL,ID_DLR,NUM_PO,NUM_VIN,CDE_VEH_STS,CDE_USE_VEH,IND_USED_VEH,CDE_VEH_DDR_STS,CDE_VEH_DDR_USE,  \r\n" + 
		"IND_USED_VEH_DDRS,TME_RTL,ID_EMP_PUR_CTRL,DTE_MODL_YR,DES_MODL,CDE_RGN,NAM_RTL_CUS_LST,NAM_RTL_CUS_FIR,NAM_RTL_CUS_MID,  \r\n" + 
		"DTE_TRANS,TME_TRANS,IND_FLT,CDE_WHSLE_INIT_TYP,CDE_NATL_TYPE,DTE_VEH_DEMO_SRV,CDE_VEH_TYP,AMT_MSRP_BASE,AMT_MSRP_TOT_ACSRY,AMT_DLR_RBT,AMT_MSRP	\r\n" + 
		"from DPB_UNBLK_VEH AS rtl \r\n" + 
		"WHERE\r\n" + 
		"PO_RANGE_FOR_REPORTS  \r\n" + 
		"VIN_RANGE_FOR_REPORTS \r\n" + 
		"DEALER_RANGE_FOR_REPORTS \r\n" + 
		"DATE_RANGE_FOR_REPORTS  \r\n" + 
		"order by dte_rtl,tme_rtl,id_dlr,num_po,des_modl\r\n" + 
		")";

public static final String VEHICLE_DETAILS_COUNT="select count(*) from Final_Result with ur";

public static final String VEHICLE_DETAILS_PAGINATION = "select * from Final_Result where rownum between "+IConstants.PAGE_START_INDEX+" and "+IConstants.PAGE_END_INDEX+" with ur";
//Vehicle Details Report-end

//MBDEAL Report-Start

public static final String MBDEAL_COUNT="select count(*) from Final_Result with ur";

public static final String MBDEAL_PAGINATION = "select * from Final_Result where rownum between "+IConstants.PAGE_START_INDEX+" and "+IConstants.PAGE_END_INDEX+" with ur";

public static final String MBDEAL_PAYOUT_PAGINATION_ROWS = "with basic_data(ID_DPB_UNBLK_VEH, ID_DPB_PGM, AMT,NUM_PO,VEH_STS,ID_EMP_PUR_CTRL,DTE_MODL_YR,\r\n" + 
		"DES_MODL,SERIAL,ID_DLR,RTL_DTE,CDE_VEH_TYP) as \r\n" + 
		"( select rtl.ID_DPB_UNBLK_VEH, calc.ID_DPB_PGM, coalesce(calc.AMT_DPB_BNS_CALC,0), \r\n" + 
		"rtl.NUM_PO,rtl.CDE_VEH_DDR_STS, rtl.ID_EMP_PUR_CTRL,substr(rtl.DTE_MODL_YR, 3,2),rtl.DES_MODL,\r\n" + 
		"rtl.NUM_VIN as SERIAL,rtl.ID_DLR, \r\n" + 
		"case when rtl.CDE_VEH_DDR_STS = 'I2' then VARCHAR_FORMAT(rtl.DTE_RTL,'MM/DD/YYYY')\r\n" + 
		"else VARCHAR_FORMAT(rtl.DTE_RTL,'MM/DD/YYYY')  end as RTL_DTE,rtl.CDE_VEH_TYP \r\n" +		
		"from DPB_CALC AS calc, DPB_UNBLK_VEH AS rtl \r\n" + 
		"WHERE calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"and calc.IND_DPB_ADJ = 'N'\r\n" + 
		"and rtl.ID_DPB_PGM in (121,123)  \r\n" + 
		"VEHICLE_TYPE_RANGE_FOR_REPORTS \r\n" + 
		"DEALER_RANGE_FOR_REPORTS \r\n" + 
		"DATE_RANGE_FOR_REPORTS\r\n" + 
		"),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select distinct rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL\r\n" + 
		"from DPB_UNBLK_VEH rtl , basic_data bdata\r\n" + 
		"where bdata.VEH_STS = 'I2' and rtl.NUM_PO = bdata.NUM_PO \r\n" + 
		"),\r\n" + 
		"RTL_DATA (ID_DPB_UNBLK_VEH, RTL_DTE) as (\r\n" + 
		"select nd1.ID_DPB_UNBLK_VEH, (select nd3.RTL_DTE from NUM_PO_DATA nd3 where nd3.ID_DPB_UNBLK_VEH = max(nd2.ID_DPB_UNBLK_VEH)) as RTL_DTE\r\n" + 
		"from NUM_PO_DATA nd1, NUM_PO_DATA nd2\r\n" + 
		"where nd1.VEH_STS = 'I2'\r\n" + 
		"and nd2.ID_DPB_UNBLK_VEH < nd1.ID_DPB_UNBLK_VEH\r\n" + 
		"and nd1.NUM_PO = nd2.NUM_PO\r\n" + 
		"group by nd1.ID_DPB_UNBLK_VEH\r\n" + 
		"),\r\n" + 
		"PGM_DATA (ID_DPB_UNBLK_VEH , COMM_PAY, DLR_RES, FLR_PLN)  as \r\n" + 
		"( select ID_DPB_UNBLK_VEH, \r\n" + 
		"SUM(coalesce ((case when ID_DPB_PGM IN (121,123)  then AMT end),0)) as COMM_PAY, \r\n" + 
		"SUM(coalesce ((case when (ID_DPB_PGM = 51 and CDE_VEH_TYP = 'S') or \r\n" + 
		" (ID_DPB_PGM = 52 and CDE_VEH_TYP = 'V') or (ID_DPB_PGM = 53 and CDE_VEH_TYP = 'F')  then AMT end),0)) as DLR_RES, \r\n" + 
		"sum(coalesce ((case when ID_DPB_PGM = 46  then AMT end),0)) as FLR_PLN  \r\n" + 
		"from basic_data \r\n" + 
		"group by ID_DPB_UNBLK_VEH), \r\n" + 
		"\r\n" + 
		"Temp_Result(PO,CNTL,MY,Model,SERIAL,Dealer,Rtl_Date,COMM_PAY,DLR_RES,FLR_PLN,TOTAL) AS (\r\n" + 
		"(select distinct pd.NUM_PO as PO, \r\n" + 
		"pd.ID_EMP_PUR_CTRL as CNTL , \r\n" + 
		"pd.DTE_MODL_YR as MY,\r\n" + 
		"pd.DES_MODL as Model, pd.SERIAL as SERIAL, \r\n" + 
		"pd.ID_DLR as Dealer,\r\n" + 
		"pd.RTL_DTE as Rtl_Date, \r\n" + 
		"VARCHAR_FORMAT(pgmD.COMM_PAY,'999999990.99') as COMM_PAY,\r\n" + 
		"VARCHAR_FORMAT(pgmD.DLR_RES,'999999990.99') as DLR_RES, \r\n" + 
		"VARCHAR_FORMAT(pgmD.FLR_PLN,'999999990.99') as FLR_PLN,\r\n" + 
		"VARCHAR_FORMAT(pgmD.COMM_PAY+pgmD.DLR_RES+pgmD.FLR_PLN,'999999990.99') as TOTAL\r\n" + 
		"from basic_data pd  \r\n" + 
		"left outer join PGM_DATA pgmD\r\n" + 
		"on pd.ID_DPB_UNBLK_VEH = pgmD.ID_DPB_UNBLK_VEH  \r\n" + 
		"left outer join RTL_DATA rd on pd.ID_DPB_UNBLK_VEH = rd.ID_DPB_UNBLK_VEH\r\n" + 
		"order by SERIAL,Rtl_Date)),\r\n" + 
		"\r\n" + 
		"Final_Result(rownum,PO,CNTL,MY,Model,SERIAL,Dealer,Rtl_Date,COMM_PAY,DLR_RES,FLR_PLN,TOTAL)\r\n" + 
		"AS(( SELECT ROW_NUMBER() OVER (ORDER BY Rtl_Date) AS RowNumber,\r\n" + 
		"tmp.PO,tmp.CNTL,tmp.MY,tmp.Model,tmp.SERIAL,tmp.Dealer,tmp.Rtl_Date,tmp.COMM_PAY,tmp.DLR_RES,tmp.FLR_PLN,tmp.TOTAL FROM Temp_Result tmp))";

public static final String MBDEAL_PAYOUT_NET = "with basic_data(ID_DPB_UNBLK_VEH, ID_DPB_PGM,VEH_STS, AMT,CDE_VEH_TYP) as \r\n" + 
		"( select DISTINCT rtl.ID_DPB_UNBLK_VEH, calc.ID_DPB_PGM,rtl.CDE_VEH_DDR_STS, calc.AMT_DPB_BNS_CALC, rtl.CDE_VEH_TYP \r\n" + 
		"from DPB_CALC AS calc, DPB_UNBLK_VEH AS rtl\r\n" + 
		"WHERE calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"and rtl.ID_DPB_PGM in (121,123) \r\n" + 
		"and calc.IND_DPB_ADJ = 'N'\r\n" + 
		"DATE_RANGE_FOR_REPORTS \r\n" + 
		"VEHICLE_TYPE_RANGE_FOR_REPORTS \r\n" + 
		"DEALER_RANGE_FOR_REPORTS\r\n" + 
		"),\r\n" + 
		"PGM_DATA (ID_DPB_UNBLK_VEH,VEH_STS, COMM_PAY, DLR_RES, FLR_PLN) as\r\n" + 
		"( select ID_DPB_UNBLK_VEH,VEH_STS, \r\n" + 
		"SUM(coalesce ((case when ID_DPB_PGM IN (121,123)  then AMT end),0)) as COMM_PAY, \r\n" + 
		"SUM(coalesce ((case when (ID_DPB_PGM = 51 and CDE_VEH_TYP = 'S') or \r\n" + 
		" (ID_DPB_PGM = 52 and CDE_VEH_TYP = 'V') or (ID_DPB_PGM = 53 and CDE_VEH_TYP = 'F')  then AMT end),0)) as DLR_RES, \r\n" + 
		"sum(coalesce ((case when ID_DPB_PGM = 46 then AMT end),0)) as FLR_PLN  \r\n" + 
		"from basic_data\r\n" + 
		"group by ID_DPB_UNBLK_VEH,VEH_STS),\r\n" + 
		"GRS_TOTALS (ID_DPB_UNBLK_VEH, TEXT,GRS_COUNT, COMM_PAY ,  DLR_RES, FLR_PLN, TOTAL, SEQ) as \r\n" + 
		"( select pd.ID_DPB_UNBLK_VEH, '         Gross:' as TEXT,   \r\n" + 
		"count(ID_DPB_UNBLK_VEH) as GRS_COUNT,   \r\n" + 
		"sum(COALESCE(pd.COMM_PAY,0)) as COMM_PAY, \r\n" + 
		"sum(COALESCE(pd.DLR_RES,0)) as DLR_RES, \r\n" + 
		"sum(COALESCE(pd.FLR_PLN,0)) as FLR_PLN, \r\n" + 
		"sum(COALESCE(pd.COMM_PAY,0)+ COALESCE(pd.DLR_RES,0) +  COALESCE(pd.FLR_PLN,0)) as TOTAL,\r\n" + 
		"1 as SEQ  \r\n" + 
		"from PGM_DATA pd \r\n" + 
		"where VEH_STS != 'I2'\r\n" + 
		"group by rollup (pd.ID_DPB_UNBLK_VEH)),\r\n" + 
		"CB_TOTALS (ID_DPB_UNBLK_VEH, TEXT,CB_COUNT,  COMM_PAY ,  DLR_RES, FLR_PLN, TOTAL, SEQ) as \r\n" + 
		"( select pd.ID_DPB_UNBLK_VEH, 'Charge-backs:' as TEXT, \r\n" + 
		"(count(ID_DPB_UNBLK_VEH) * -1) as CB_COUNT,   \r\n" + 
		"COALESCE(sum(COALESCE(pd.COMM_PAY,0)),0) as COMM_PAY, \r\n" + 
		"COALESCE(sum(COALESCE(pd.DLR_RES,0)),0) as DLR_RES, \r\n" + 
		"COALESCE(sum(COALESCE(pd.FLR_PLN,0)),0) as FLR_PLN, \r\n" + 
		"COALESCE(sum(COALESCE(pd.COMM_PAY,0) + COALESCE(pd.DLR_RES,0) +  COALESCE(pd.FLR_PLN,0) ),0) as TOTAL,\r\n" + 
		"2 as SEQ from PGM_DATA pd \r\n" + 
		"where VEH_STS = 'I2'\r\n" + 
		"group by rollup (pd.ID_DPB_UNBLK_VEH)),\r\n" + 
		"NET_TOTALS (ID_DPB_UNBLK_VEH, TEXT,  NET_COUNT,  COMM_PAY ,  DLR_RES, FLR_PLN, TOTAL, SEQ) as \r\n" + 
		"( select ct.ID_DPB_UNBLK_VEH, '         Net:' as TEXT,  \r\n" + 
		"GRS_COUNT - abs(ct.CB_COUNT) as NET_COUNT,   \r\n" + 
		"gt.COMM_PAY - abs(ct.COMM_PAY) as COMM_PAY, \r\n" + 
		"gt.DLR_RES - abs(ct.DLR_RES) as DLR_RES, \r\n" + 
		"gt.FLR_PLN - abs(ct.FLR_PLN) as FLR_PLN, \r\n" + 
		"gt.total - abs(ct.total) as TOTAL,\r\n" + 
		"3 as SEQ  \r\n" + 
		"from CB_TOTALS ct, GRS_TOTALS gt  \r\n" + 
		"where ct.ID_DPB_UNBLK_VEH is null and gt.ID_DPB_UNBLK_VEH is null\r\n" + 
		"),\r\n" + 
		"FINAL (TEXT, \r\n" + 
		"COMM_PAY ,  DLR_RES, FLR_PLN, TOTAL, SEQ) as (\r\n" + 
		"select TEXT, COMM_PAY ,  DLR_RES, FLR_PLN, TOTAL, SEQ\r\n" + 
		"from GRS_TOTALS where ID_DPB_UNBLK_VEH is null\r\n" + 
		"union\r\n" + 
		"select TEXT,  COMM_PAY ,  DLR_RES, FLR_PLN, TOTAL, SEQ\r\n" + 
		"from CB_TOTALS where ID_DPB_UNBLK_VEH is null\r\n" + 
		"union\r\n" + 
		"select TEXT, COMM_PAY ,  DLR_RES, FLR_PLN, TOTAL, SEQ\r\n" + 
		"from NET_TOTALS\r\n" + 
		")\r\n" + 
		"select TEXT, ' ' as text1, ' ' as text2, ' ' as text3, ' ' as text4, ' ' as text5, ' ' as text6,\r\n" + 
		"VARCHAR_FORMAT(COMM_PAY,'999999990.99') as COMM_PAY,\r\n" + 
		"VARCHAR_FORMAT(DLR_RES,'999999990.99') as DLR_RES,\r\n" + 
		"VARCHAR_FORMAT(FLR_PLN,'999999990.99') as FLR_PLN,\r\n" + 
		"VARCHAR_FORMAT(TOTAL,'999999990.99') as TOTAL\r\n" + 
		"from FINAL\r\n" + 
		"order by SEQ FOR FETCH ONLY WITH UR";

public static final String MBDEAL_PAYOUT_ALL_ROWS_EXCEL = "with basic_data(ID_DPB_UNBLK_VEH, ID_DPB_PGM, AMT,NUM_PO,VEH_STS,ID_EMP_PUR_CTRL,DTE_MODL_YR,\r\n" + 
		"DES_MODL,SERIAL,ID_DLR,RTL_DTE,CDE_VEH_TYP) as \r\n" + 
		"( select rtl.ID_DPB_UNBLK_VEH, calc.ID_DPB_PGM, coalesce(calc.AMT_DPB_BNS_CALC,0), \r\n" + 
		"rtl.NUM_PO,rtl.CDE_VEH_DDR_STS, rtl.ID_EMP_PUR_CTRL,substr(rtl.DTE_MODL_YR, 3,2),rtl.DES_MODL,\r\n" + 
		"rtl.NUM_VIN as SERIAL,rtl.ID_DLR, \r\n" + 
		"case when rtl.CDE_VEH_DDR_STS = 'I2' then VARCHAR_FORMAT(rtl.DTE_RTL,'MM/DD/YYYY')\r\n" + 
		"else VARCHAR_FORMAT(rtl.DTE_RTL,'MM/DD/YYYY')  end as RTL_DTE, rtl.CDE_VEH_TYP\r\n" + 
		"from DPB_CALC AS calc, DPB_UNBLK_VEH AS rtl \r\n" + 
		"WHERE calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"and calc.IND_DPB_ADJ = 'N'\r\n" + 
		"and rtl.ID_DPB_PGM in (121,123)  \r\n" + 
		"VEHICLE_TYPE_RANGE_FOR_REPORTS \r\n" + 
		"DEALER_RANGE_FOR_REPORTS \r\n" + 
		"DATE_RANGE_FOR_REPORTS\r\n" + 
		"),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select distinct rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL\r\n" + 
		"from DPB_UNBLK_VEH rtl , basic_data bdata\r\n" + 
		"where bdata.VEH_STS = 'I2' and rtl.NUM_PO = bdata.NUM_PO \r\n" + 
		"),\r\n" + 
		"RTL_DATA (ID_DPB_UNBLK_VEH, RTL_DTE) as (\r\n" + 
		"select nd1.ID_DPB_UNBLK_VEH, (select nd3.RTL_DTE from NUM_PO_DATA nd3 where nd3.ID_DPB_UNBLK_VEH = max(nd2.ID_DPB_UNBLK_VEH)) as RTL_DTE\r\n" + 
		"from NUM_PO_DATA nd1, NUM_PO_DATA nd2\r\n" + 
		"where nd1.VEH_STS = 'I2'\r\n" + 
		"and nd2.ID_DPB_UNBLK_VEH < nd1.ID_DPB_UNBLK_VEH\r\n" + 
		"and nd1.NUM_PO = nd2.NUM_PO\r\n" + 
		"group by nd1.ID_DPB_UNBLK_VEH\r\n" + 
		"),\r\n" + 
		"PGM_DATA (ID_DPB_UNBLK_VEH , COMM_PAY, DLR_RES, FLR_PLN)  as \r\n" + 
		"( select ID_DPB_UNBLK_VEH, \r\n" + 
		"SUM(coalesce ((case when ID_DPB_PGM IN (121,123)  then AMT end),0)) as COMM_PAY, \r\n" + 
		"SUM(coalesce ((case when (ID_DPB_PGM = 51 and CDE_VEH_TYP = 'S') or \r\n" + 
		" (ID_DPB_PGM = 52 and CDE_VEH_TYP = 'V') or (ID_DPB_PGM = 53 and CDE_VEH_TYP = 'F')  then AMT end),0)) as DLR_RES, \r\n" + 
		"sum(coalesce ((case when ID_DPB_PGM = 46  then AMT end),0)) as FLR_PLN  \r\n" + 
		"from basic_data \r\n" + 
		"group by ID_DPB_UNBLK_VEH), \r\n" + 
		"\r\n" + 
		"Temp_Result(PO,CNTL,MY,Model,SERIAL,Dealer,Rtl_Date,COMM_PAY,DLR_RES,FLR_PLN,TOTAL) AS (\r\n" + 
		"(select distinct pd.NUM_PO as PO, \r\n" + 
		"pd.ID_EMP_PUR_CTRL as CNTL , \r\n" + 
		"pd.DTE_MODL_YR as MY,\r\n" + 
		"pd.DES_MODL as Model, pd.SERIAL as SERIAL, \r\n" + 
		"pd.ID_DLR as Dealer,\r\n" + 
		"pd.RTL_DTE as Rtl_Date, \r\n" + 
		"VARCHAR_FORMAT(pgmD.COMM_PAY,'999999990.99') as COMM_PAY,\r\n" + 
		"VARCHAR_FORMAT(pgmD.DLR_RES,'999999990.99') as DLR_RES, \r\n" + 
		"VARCHAR_FORMAT(pgmD.FLR_PLN,'999999990.99') as FLR_PLN,\r\n" + 
		"VARCHAR_FORMAT(pgmD.COMM_PAY+pgmD.DLR_RES+pgmD.FLR_PLN,'999999990.99') as TOTAL\r\n" + 
		"from basic_data pd  \r\n" + 
		"left outer join PGM_DATA pgmD\r\n" + 
		"on pd.ID_DPB_UNBLK_VEH = pgmD.ID_DPB_UNBLK_VEH  \r\n" + 
		"left outer join RTL_DATA rd on pd.ID_DPB_UNBLK_VEH = rd.ID_DPB_UNBLK_VEH\r\n" + 
		"order by SERIAL,Rtl_Date)),\r\n" + 
		"\r\n" + 
		"Final_Result(rownum,PO,CNTL,MY,Model,SERIAL,Dealer,Rtl_Date,COMM_PAY,DLR_RES,FLR_PLN,TOTAL)\r\n" + 
		"AS(( SELECT ROW_NUMBER() OVER (ORDER BY Rtl_Date) AS RowNumber,\r\n" + 
		"tmp.PO,tmp.CNTL,tmp.MY,tmp.Model,tmp.SERIAL,tmp.Dealer,tmp.Rtl_Date,tmp.COMM_PAY,tmp.DLR_RES,tmp.FLR_PLN,tmp.TOTAL FROM Temp_Result tmp))\r\n" + 
		"select PO,CNTL,MY,Model,SERIAL,Dealer,Rtl_Date,COMM_PAY,DLR_RES,FLR_PLN,TOTAL from Final_Result with ur";

//MBDEAL Report-End


//Exception Report - start


public static final String EXCEPTION_COUNT="select count(*) from Final_Result with ur";

public static final String EXCEPTION_PAGINATION = "select * from Final_Result where rownum between "+IConstants.PAGE_START_INDEX+" and "+IConstants.PAGE_END_INDEX+" with ur";

public static final String EXCEPTION_PAGINATION_ROWS = "with Temp_Result(DTE_RTL,DTE_MODL_YR,DES_MODL,NUM_VIN,NUM_PO,ID_DLR,CDE_RGN,VEH_CNT,REASON) AS \r\n" + 
		"((SELECT cast(VARCHAR_FORMAT(DTE_RTL,'MM/DD/YYYY') as  char(12)) AS DTE_RTL,\r\n" + 
		"DTE_MODL_YR as DTE_MODL_YR,cast(DES_MODL as char(9)) DES_MODL ,\r\n" + 
		"NUM_VIN as NUM_VIN,cast(NUM_PO as char(13)) NUM_PO,\r\n" + 
		"cast(ID_DLR as char(10)) ID_DLR, cast(CDE_RGN as char(10)) CDE_RGN, \r\n" + 
		"CASE WHEN CDE_VEH_DDR_STS = 'I2' THEN cast ('-1' as char(10))\r\n" + 
		"ELSE  cast('1' as char(10))  END AS VEH_CNT,\r\n" + 
		"CASE WHEN ID_DPB_PGM in (122) AND CDE_VEH_DDR_STS != 'I2' THEN 'CVP SALE' \r\n" + 
		"ELSE CASE WHEN ID_DPB_PGM in (122) AND CDE_VEH_DDR_STS = 'I2' THEN 'CVP (CANCEL)'  \r\n" + 
		"ELSE CASE WHEN ID_DPB_PGM in (121,123) AND CDE_VEH_DDR_STS != 'I2' THEN 'MBDeal SALE' \r\n" + 
		"ELSE  'MBDeal(CANCEL)' END END END AS REASON FROM  DPB_UNBLK_VEH rtl  WHERE ID_DPB_PGM  > 0 \r\n" + 
		"DATE_RANGE_FOR_REPORTS  VEHICLE_TYPE_RANGE_FOR_REPORTS DEALER_RANGE_FOR_REPORTS )),\r\n" + 
		"Final_Result(DTE_RTL,rownum,DTE_MODL_YR,DES_MODL,NUM_VIN,NUM_PO,ID_DLR,CDE_RGN,VEH_CNT,REASON) AS\r\n" + 
		"((SELECT Temp.DTE_RTL,ROW_NUMBER() OVER (ORDER BY DTE_RTL) AS rownumber, Temp.DTE_MODL_YR,Temp.DES_MODL,Temp.NUM_VIN,Temp.NUM_PO,Temp.ID_DLR,\r\n" + 
		"Temp.CDE_RGN,Temp.VEH_CNT,Temp.REASON FROM  Temp_Result Temp))";

public static final String EXCEPTION_PAYOUT_NET="WITH VEH ( VEH_CNT, VEH_CAN_CNT) AS (SELECT   CASE WHEN CDE_VEH_DDR_STS != 'I2' \r\n" + 
		"THEN cast('1' as char(10))  END AS VEH_CNT,CASE WHEN CDE_VEH_DDR_STS = 'I2' \r\n" + 
		"THEN cast('-1' as char(10))END AS VEH_CAN_CNT\r\n" + 
		"FROM  DPB_UNBLK_VEH rtl  WHERE ID_DPB_PGM  > 0  DATE_RANGE_FOR_REPORTS  VEHICLE_TYPE_RANGE_FOR_REPORTS DEALER_RANGE_FOR_REPORTS\r\n" + 
		"order by DTE_RTL),\r\n" + 
		"PGM_CNT (VEH_RTL_CNT, VEH_CAN_CNT) as (\r\n" + 
		"select  count(VEH_CNT),count(VEH_CAN_CNT) from VEH),\r\n" + 
		"FINAL (TEXT,CNT,SEQ) as (\r\n" + 
		"select 'RETAIL :' as TEXT, VEH_RTL_CNT, 3 AS SEQ from PGM_CNT pgm\r\n" + 
		"union\r\n" + 
		"select 'CANCEL :' as TEXT,  -1 * VEH_CAN_CNT  , 2 AS SEQ from PGM_CNT pgm\r\n" + 
		"union\r\n" + 
		"select 'TOTAL :' as TEXT, pgm.VEH_RTL_CNT+pgm.VEH_CAN_CNT, 1 AS SEQ from  PGM_CNT pgm\r\n" + 
		")\r\n" + 
		"select * from FINAL ORDER BY SEQ FOR FETCH ONLY WITH UR";

public static final String  EXCEPTION_ALL_ROWS_EXCEL = "with Temp_Result(DTE_RTL,DTE_MODL_YR,DES_MODL,NUM_VIN,NUM_PO,ID_DLR,CDE_RGN,VEH_CNT,REASON) AS \r\n" + 
		"((SELECT cast(VARCHAR_FORMAT(DTE_RTL,'MM/DD/YYYY') as  char(12)) AS DTE_RTL,\r\n" + 
		"DTE_MODL_YR as DTE_MODL_YR,cast(DES_MODL as char(9)) DES_MODL ,\r\n" + 
		"NUM_VIN as NUM_VIN,cast(NUM_PO as char(13)) NUM_PO,\r\n" + 
		"cast(ID_DLR as char(10)) ID_DLR, cast(CDE_RGN as char(10)) CDE_RGN, \r\n" + 
		"CASE WHEN CDE_VEH_DDR_STS = 'I2' THEN cast ('-1' as char(10))\r\n" + 
		"ELSE  cast('1' as char(10))  END AS VEH_CNT,\r\n" + 
		"CASE WHEN ID_DPB_PGM in (122) AND CDE_VEH_DDR_STS != 'I2' THEN 'CVP SALE' \r\n" + 
		"ELSE CASE WHEN ID_DPB_PGM in (122) AND CDE_VEH_DDR_STS = 'I2' THEN 'CVP (CANCEL)'  \r\n" + 
		"ELSE CASE WHEN ID_DPB_PGM in (121,123) AND CDE_VEH_DDR_STS != 'I2' THEN 'MBDeal SALE' \r\n" + 
		"ELSE  'MBDeal(CANCEL)' END END END AS REASON FROM  DPB_UNBLK_VEH rtl  WHERE ID_DPB_PGM  > 0 \r\n" + 
		"DATE_RANGE_FOR_REPORTS  VEHICLE_TYPE_RANGE_FOR_REPORTS DEALER_RANGE_FOR_REPORTS )),\r\n" + 
		"Final_Result(rownum,DTE_RTL,DTE_MODL_YR,DES_MODL,NUM_VIN,NUM_PO,ID_DLR,CDE_RGN,VEH_CNT,REASON) AS\r\n" + 
		"((SELECT ROW_NUMBER() OVER (ORDER BY DTE_RTL) AS rownumber,Temp.DTE_RTL,Temp.DTE_MODL_YR,Temp.DES_MODL,Temp.NUM_VIN,Temp.NUM_PO,Temp.ID_DLR,\r\n" + 
		"Temp.CDE_RGN,Temp.VEH_CNT,Temp.REASON FROM  Temp_Result Temp))\r\n" + 
		"SELECT * FROM Final_Result with ur";

//Exception Report - end

//Courtesy Vehicle Report -start

public static final String CVP_COUNT="select count(*) from Final_Result with ur";

public static final String CVP_PAGINATION = "select * from Final_Result where rownum between "+IConstants.PAGE_START_INDEX+" and "+IConstants.PAGE_END_INDEX+" with ur";



public static final String CVP_PAYOUT_PAGINATION_ROWS="with basic_data(ID_DPB_UNBLK_VEH, ID_PGM, AMT,NUM_PO,VEH_STS,ID_EMP_PUR_CTRL,DTE_MODL_YR,\r\n" + 
		"DES_MODL,SERIAL,ID_DLR,RTL_DTE,VEH_TYP) as (\r\n" + 
		"select rtl.ID_DPB_UNBLK_VEH, calc.ID_DPB_PGM, coalesce(calc.AMT_DPB_BNS_CALC,0), \r\n" + 
		"rtl.NUM_PO,rtl.CDE_VEH_DDR_STS, rtl.ID_EMP_PUR_CTRL,substr(rtl.DTE_MODL_YR, 3,2),rtl.DES_MODL, \r\n" + 
		"rtl.NUM_VIN as SERIAL,rtl.ID_DLR,\r\n" + 
		"case when rtl.CDE_VEH_DDR_STS = 'I2' then VARCHAR_FORMAT(rtl.DTE_RTL,'MM/DD/YYYY')\r\n" + 
		"else VARCHAR_FORMAT(rtl.DTE_RTL,'MM/DD/YYYY') END AS RTL_DTE,rtl.CDE_VEH_TYP \r\n" + 
		"from DPB_CALC AS calc, DPB_UNBLK_VEH AS rtl\r\n" + 
		"WHERE calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH AND rtl.ID_DPB_PGM in (122)\r\n" + 
		"and calc.IND_DPB_ADJ = 'N'   \r\n" + 
		"DATE_RANGE_FOR_REPORTS  \r\n" + 
		"VEHICLE_TYPE_RANGE_FOR_REPORTS \r\n" + 
		"DEALER_RANGE_FOR_REPORTS\r\n" + 
		"),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select  rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL\r\n" + 
		"from DPB_UNBLK_VEH rtl , basic_data bdata\r\n" + 
		"where bdata.VEH_STS = 'I2' and rtl.NUM_PO = bdata.NUM_PO \r\n" + 
		"group by rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL\r\n" + 
		"),\r\n" + 
		"RTL_DATA (ID_DPB_UNBLK_VEH, RTL_DTE) as (\r\n" + 
		"select nd1.ID_DPB_UNBLK_VEH, (select nd3.RTL_DTE from NUM_PO_DATA nd3 \r\n" + 
		"where nd3.ID_DPB_UNBLK_VEH = max(nd2.ID_DPB_UNBLK_VEH)) as RTL_DTE\r\n" + 
		"from NUM_PO_DATA nd1, NUM_PO_DATA nd2\r\n" + 
		"where nd1.VEH_STS = 'I2'\r\n" + 
		"and nd2.ID_DPB_UNBLK_VEH < nd1.ID_DPB_UNBLK_VEH\r\n" + 
		"and nd1.NUM_PO = nd2.NUM_PO\r\n" + 
		"group by nd1.ID_DPB_UNBLK_VEH\r\n" + 
		"),\r\n" + 
		"PGM_DATA (ID_DPB_UNBLK_VEH, FLR_PLN, CUST_EXP, CEX_SLS, CEX_SRC, PRE_OWN, NV_SAL, BRD_STDS) as (\r\n" + 
		"select ID_DPB_UNBLK_VEH,\r\n" + 
		"sum(case when (ID_PGM = 46 and VEH_TYP = 'P') or (ID_PGM = 51 and VEH_TYP = 'S') or \r\n" + 
		"    (ID_PGM = 52 and VEH_TYP = 'V') or (ID_PGM = 53 and VEH_TYP = 'F') then AMT end) as FLR_PLN,\r\n" + 
		"sum(case when \r\n" + 
		"    (ID_PGM = 57 and VEH_TYP = 'V') or (ID_PGM = 60 and VEH_TYP = 'F') then AMT end) as CUST_EXP,\r\n" + 
		"sum(case when (ID_PGM = 144 and VEH_TYP = 'P') then AMT end) as CEX_SLS,\r\n" + 
		"sum(case when (ID_PGM = 145 and VEH_TYP = 'P') then AMT end) as CEX_SRC,\r\n" + 
		"sum(case when (ID_PGM = 49 and VEH_TYP = 'P') then AMT end) as PRE_OWN,\r\n" + 
		"sum(case when (ID_PGM = 48 and VEH_TYP = 'P') or \r\n" + 
		"    (ID_PGM = 59 and VEH_TYP = 'V') or (ID_PGM = 62 and VEH_TYP = 'F') then AMT end) as NV_SAL,\r\n" + 
		"sum(case when (ID_PGM = 50 and VEH_TYP = 'P') or (ID_PGM = 141 and VEH_TYP = 'S') or \r\n" + 
		"    (ID_PGM = 58 and VEH_TYP = 'V') or (ID_PGM = 61 and VEH_TYP = 'F') then AMT end) as BRD_STDS\r\n" + 
		"from basic_data\r\n" + 
		"group by ID_DPB_UNBLK_VEH),\r\n" + 
		"Temp_Result(PO,MY,Model,SERIAL,Dealer,Rtl_Date,FLR_PLN,CUST_EXP,CEX_SLS,CEX_SRC,PRE_OWN,NV_SAL,BRD_STDS,TOTAL) AS (\r\n" + 
		"(select distinct pd.NUM_PO as PO,\r\n" + 
		"pd.DTE_MODL_YR as MY, \r\n" + 
		"pd.DES_MODL as Model, pd.SERIAL as SERIAL, \r\n" + 
		"pd.ID_DLR as Dealer, \r\n" + 
		"pd.RTL_DTE as Rtl_Date,\r\n" + 
		"VARCHAR_FORMAT(coalesce(pgmD.FLR_PLN,0),'999999990.99') as FLR_PLN,\r\n" + 
		"VARCHAR_FORMAT(coalesce(pgmD.CUST_EXP,0),'999999990.99') as CUST_EXP,\r\n" + 
		"VARCHAR_FORMAT(coalesce(pgmD.CEX_SLS,0),'999999990.99') as CEX_SLS,\r\n" + 
		"VARCHAR_FORMAT(coalesce(pgmD.CEX_SRC,0),'999999990.99') as CEX_SRC,\r\n" + 
		"VARCHAR_FORMAT(coalesce(pgmD.PRE_OWN,0),'999999990.99') as PRE_OWN,\r\n" + 
		"VARCHAR_FORMAT(coalesce(pgmD.NV_SAL,0),'999999990.99') as NV_SAL,\r\n" + 
		"VARCHAR_FORMAT(coalesce(pgmD.BRD_STDS,0),'999999990.99') as BRD_STDS,\r\n" + 
		"VARCHAR_FORMAT((coalesce(pgmD.FLR_PLN,0)+coalesce(pgmD.CUST_EXP,0)+coalesce(pgmD.CEX_SLS,0)+coalesce(pgmD.CEX_SRC,0)\r\n" + 
		"+coalesce(pgmD.PRE_OWN,0)+coalesce(pgmD.NV_SAL,0)+coalesce(pgmD.BRD_STDS,0)),'999999990.99') as TOTAL\r\n" + 
		"  from basic_data pd \r\n" + 
		"left outer join PGM_DATA pgmD\r\n" + 
		"on pd.ID_DPB_UNBLK_VEH = pgmD.ID_DPB_UNBLK_VEH  \r\n" + 
		"left outer join RTL_DATA rd on pd.ID_DPB_UNBLK_VEH = rd.ID_DPB_UNBLK_VEH \r\n" + 
		"order by SERIAL,Rtl_Date)),\r\n" + 
		"Final_Result(PO,rownum,MY,Model,SERIAL,Dealer,Rtl_Date,FLR_PLN,CUST_EXP,CEX_SLS,CEX_SRC,PRE_OWN,NV_SAL,BRD_STDS,TOTAL)\r\n" + 
		"AS((SELECT temp.PO,ROW_NUMBER() OVER (ORDER BY Rtl_Date) AS rownumber,\r\n" + 
		"temp.MY,temp.Model,temp.SERIAL,temp.Dealer,temp.Rtl_Date,temp.FLR_PLN,temp.CUST_EXP,temp.CEX_SLS,temp.CEX_SRC,\r\n" + 
		"temp.PRE_OWN,temp.NV_SAL,temp.BRD_STDS,temp.TOTAL FROM Temp_Result temp))\r\n" + 
		"";

public static final String CVP_PAYOUT_NET="with basic_data(ID_DPB_UNBLK_VEH, ID_DPB_PGM,VEH_STS, AMT,VEH_TYP) as \r\n" + 
		"( select DISTINCT rtl.ID_DPB_UNBLK_VEH, calc.ID_DPB_PGM,rtl.CDE_VEH_DDR_STS, calc.AMT_DPB_BNS_CALC,rtl.CDE_VEH_TYP  \r\n" + 
		"from DPB_CALC AS calc, DPB_UNBLK_VEH AS rtl\r\n" + 
		"WHERE calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"and rtl.ID_DPB_PGM in (122)\r\n" + 
		"and calc.IND_DPB_ADJ = 'N'   \r\n" + 
		"DATE_RANGE_FOR_REPORTS  \r\n" + 
		"VEHICLE_TYPE_RANGE_FOR_REPORTS \r\n" + 
		"DEALER_RANGE_FOR_REPORTS\r\n" + 
		"),\r\n" + 
		"PGM_DATA (ID_DPB_UNBLK_VEH,VEH_STS, FLR_PLN, CUST_EXP, CEX_SLS, CEX_SRC, PRE_OWN, NV_SAL, BRD_STDS) as\r\n" + 
		"( select ID_DPB_UNBLK_VEH,VEH_STS,\r\n" + 
		"sum(coalesce ((case when (ID_DPB_PGM = 46 and VEH_TYP = 'P') or (ID_DPB_PGM = 51 and VEH_TYP = 'S') or \r\n" + 
		"    (ID_DPB_PGM = 52 and VEH_TYP = 'V') or (ID_DPB_PGM = 53 and VEH_TYP = 'F') then AMT end),0)) as FLR_PLN,\r\n" + 
		"sum(coalesce ((case when \r\n" + 
		"    (ID_DPB_PGM = 57 and VEH_TYP = 'V') or (ID_DPB_PGM = 60 and VEH_TYP = 'F') then AMT end),0)) as CUST_EXP,\r\n" + 
		"sum(coalesce ((case when (ID_DPB_PGM = 144 and VEH_TYP = 'P') then AMT end),0)) as CEX_SLS,\r\n" + 
		"sum(coalesce ((case when (ID_DPB_PGM = 145 and VEH_TYP = 'P') then AMT end),0)) as CEX_SRC,\r\n" + 
		"sum(coalesce ((case when (ID_DPB_PGM = 49 and VEH_TYP = 'P') then AMT end),0)) as PRE_OWN,\r\n" + 
		"sum(coalesce ((case when (ID_DPB_PGM = 48 and VEH_TYP = 'P') or \r\n" + 
		"    (ID_DPB_PGM = 59 and VEH_TYP = 'V') or (ID_DPB_PGM = 62 and VEH_TYP = 'F') then AMT end),0)) as NV_SAL,\r\n" + 
		"sum(coalesce ((case when (ID_DPB_PGM = 50 and VEH_TYP = 'P') or (ID_DPB_PGM = 141 and VEH_TYP = 'S') or \r\n" + 
		"    (ID_DPB_PGM = 58 and VEH_TYP = 'V') or (ID_DPB_PGM = 61 and VEH_TYP = 'F') then AMT end),0)) as BRD_STDS \r\n" + 
		"from basic_data\r\n" + 
		"group by ID_DPB_UNBLK_VEH,VEH_STS),\r\n" + 
		"\r\n" + 
		"GRS_TOTALS (ID_DPB_UNBLK_VEH, TEXT,GRS_COUNT, FLR_PLN, CUST_EXP, CEX_SLS, CEX_SRC, PRE_OWN, NV_SAL, BRD_STDS, TOTAL,SEQ) as\r\n" + 
		"(select pd.ID_DPB_UNBLK_VEH, '         Gross:' as TEXT, \r\n" + 
		"count(ID_DPB_UNBLK_VEH) as GRS_COUNT, \r\n" + 
		"sum(COALESCE(pd.FLR_PLN,0)) as FLR_PLN,\r\n" + 
		"sum(COALESCE(pd.CUST_EXP,0)) as CUST_EXP,\r\n" + 
		"sum(COALESCE(pd.CEX_SLS,0)) as CEX_SLS,\r\n" + 
		"sum(COALESCE(pd.CEX_SRC,0)) as CEX_SRC,\r\n" + 
		"sum(COALESCE(pd.PRE_OWN,0)) as PRE_OWN,\r\n" + 
		"sum(COALESCE(pd.NV_SAL,0)) as NV_SAL,\r\n" + 
		"sum(COALESCE(pd.BRD_STDS,0)) as BRD_STDS,\r\n" + 
		"sum(COALESCE(pd.FLR_PLN,0)+COALESCE(pd.CUST_EXP,0)+COALESCE(pd.CEX_SLS,0)+COALESCE(pd.CEX_SRC,0)+\r\n" + 
		"COALESCE(pd.PRE_OWN,0)+COALESCE(pd.NV_SAL,0)+COALESCE(pd.BRD_STDS,0)) as TOTAL,\r\n" + 
		"1 as SEQ  \r\n" + 
		"from PGM_DATA pd \r\n" + 
		"where VEH_STS != 'I2'\r\n" + 
		"group by rollup (pd.ID_DPB_UNBLK_VEH)),\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"CB_TOTALS (ID_DPB_UNBLK_VEH, TEXT,CB_COUNT, FLR_PLN, CUST_EXP, CEX_SLS, CEX_SRC, PRE_OWN, NV_SAL, BRD_STDS, TOTAL,SEQ) as \r\n" + 
		"( select pd.ID_DPB_UNBLK_VEH, 'Charge-backs:' as TEXT, \r\n" + 
		"(count(ID_DPB_UNBLK_VEH) * -1) as CB_COUNT,   \r\n" + 
		"COALESCE(sum(COALESCE(pd.FLR_PLN,0)),0) as FLR_PLN,\r\n" + 
		"COALESCE(sum(COALESCE(pd.CUST_EXP,0)),0) as CUST_EXP,\r\n" + 
		"COALESCE(sum(COALESCE(pd.CEX_SLS,0)),0) as CEX_SLS,\r\n" + 
		"COALESCE(sum(COALESCE(pd.CEX_SRC,0)),0) as CEX_SRC,\r\n" + 
		"COALESCE(sum(COALESCE(pd.PRE_OWN,0)),0) as PRE_OWN,\r\n" + 
		"COALESCE(sum(COALESCE(pd.NV_SAL,0)),0) as NV_SAL,\r\n" + 
		"COALESCE(sum(COALESCE(pd.BRD_STDS,0)),0) as BRD_STDS,\r\n" + 
		"COALESCE(sum(COALESCE(pd.FLR_PLN,0)+COALESCE(pd.CUST_EXP,0)+COALESCE(pd.CEX_SLS,0)+COALESCE(pd.CEX_SRC,0)+\r\n" + 
		"COALESCE(pd.PRE_OWN,0)+COALESCE(pd.NV_SAL,0)+COALESCE(pd.BRD_STDS,0)),0) as TOTAL,\r\n" + 
		"2 as SEQ from PGM_DATA pd \r\n" + 
		"where VEH_STS = 'I2'\r\n" + 
		"group by rollup (pd.ID_DPB_UNBLK_VEH)),\r\n" + 
		"NET_TOTALS (ID_DPB_UNBLK_VEH, TEXT,  NET_COUNT, FLR_PLN, CUST_EXP, CEX_SLS, CEX_SRC, PRE_OWN, NV_SAL, BRD_STDS, TOTAL,SEQ) as \r\n" + 
		"( select ct.ID_DPB_UNBLK_VEH, '         Net:' as TEXT,  \r\n" + 
		"GRS_COUNT - abs(ct.CB_COUNT) as NET_COUNT,   \r\n" + 
		"gt.FLR_PLN - abs(ct.FLR_PLN) as FLR_PLN, \r\n" + 
		"gt.CUST_EXP - abs(ct.CUST_EXP) as CUST_EXP, \r\n" + 
		"gt.CEX_SLS - abs(ct.CEX_SLS) as CEX_SLS, \r\n" + 
		"gt.CEX_SRC - abs(ct.CEX_SRC) as CEX_SRC, \r\n" + 
		"gt.PRE_OWN - abs(ct.PRE_OWN) as PRE_OWN, \r\n" + 
		"gt.NV_SAL - abs(ct.NV_SAL) as NV_SAL,\r\n" + 
		"gt.BRD_STDS - abs(ct.BRD_STDS) as BRD_STDS, \r\n" + 
		"gt.total - abs(ct.total) as TOTAL,\r\n" + 
		"3 as SEQ  \r\n" + 
		"from CB_TOTALS ct, GRS_TOTALS gt  \r\n" + 
		"where ct.ID_DPB_UNBLK_VEH is null and gt.ID_DPB_UNBLK_VEH is null\r\n" + 
		"),\r\n" + 
		"FINAL (TEXT,  RPT_COUNT,  \r\n" + 
		"FLR_PLN, CUST_EXP,CEX_SLS, CEX_SRC, PRE_OWN, NV_SAL, BRD_STDS, TOTAL, SEQ) as (\r\n" + 
		"select TEXT, GRS_COUNT,FLR_PLN,CUST_EXP, CEX_SLS, CEX_SRC, PRE_OWN, NV_SAL, BRD_STDS, TOTAL, SEQ\r\n" + 
		"from GRS_TOTALS where ID_DPB_UNBLK_VEH is null\r\n" + 
		"union\r\n" + 
		"select TEXT, CB_COUNT,FLR_PLN, CUST_EXP,CEX_SLS, CEX_SRC, PRE_OWN, NV_SAL, BRD_STDS, TOTAL, SEQ\r\n" + 
		"from CB_TOTALS where ID_DPB_UNBLK_VEH is null\r\n" + 
		"union\r\n" + 
		"select TEXT, NET_COUNT,FLR_PLN,CUST_EXP,CEX_SLS, CEX_SRC, PRE_OWN, NV_SAL, BRD_STDS, TOTAL, SEQ\r\n" + 
		"from NET_TOTALS\r\n" + 
		")\r\n" + 
		"\r\n" + 
		"select TEXT, ' ' as text1, ' ' as text2, ' ' as text3, ' ' as text4, ' ' as text5, \r\n" + 
		"VARCHAR_FORMAT(FLR_PLN,'999999990.99') as FLR_PLN,\r\n" + 
		"VARCHAR_FORMAT(CUST_EXP,'999999990.99') as CUST_EXP,\r\n" + 
		"VARCHAR_FORMAT(CEX_SLS,'999999990.99') as CEX_SLS,\r\n" + 
		"VARCHAR_FORMAT(CEX_SRC,'999999990.99') as CEX_SRC,\r\n" + 
		"VARCHAR_FORMAT(PRE_OWN,'999999990.99') as PRE_OWN,\r\n" + 
		"VARCHAR_FORMAT(NV_SAL,'999999990.99') as NV_SAL,\r\n" + 
		"VARCHAR_FORMAT(BRD_STDS,'999999990.99') as BRD_STDS,\r\n" + 
		"VARCHAR_FORMAT(TOTAL,'999999990.99') as TOTAL\r\n" + 
		"from FINAL\r\n" + 
		"order by SEQ FOR FETCH ONLY WITH UR";

public static final String CVP_PAYOUT_ALL_ROWS_EXCEL="with basic_data(ID_DPB_UNBLK_VEH, ID_PGM, AMT,NUM_PO,VEH_STS,ID_EMP_PUR_CTRL,DTE_MODL_YR,\r\n" + 
		"DES_MODL,SERIAL,ID_DLR,RTL_DTE,VEH_TYP) as (\r\n" + 
		"select rtl.ID_DPB_UNBLK_VEH, calc.ID_DPB_PGM, coalesce(calc.AMT_DPB_BNS_CALC,0), \r\n" + 
		"rtl.NUM_PO,rtl.CDE_VEH_DDR_STS, rtl.ID_EMP_PUR_CTRL,substr(rtl.DTE_MODL_YR, 3,2),rtl.DES_MODL, \r\n" + 
		"rtl.NUM_VIN as SERIAL,rtl.ID_DLR,\r\n" + 
		"case when rtl.CDE_VEH_DDR_STS = 'I2' then VARCHAR_FORMAT(rtl.DTE_RTL,'MM/DD/YYYY')\r\n" + 
		"else VARCHAR_FORMAT(rtl.DTE_RTL,'MM/DD/YYYY') END AS RTL_DTE,rtl.CDE_VEH_TYP \r\n" + 
		"from DPB_CALC AS calc, DPB_UNBLK_VEH AS rtl\r\n" + 
		"WHERE calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH AND rtl.ID_DPB_PGM in (122)\r\n" + 
		"and calc.IND_DPB_ADJ = 'N'   \r\n" + 
		"VEHICLE_TYPE_RANGE_FOR_REPORTS \r\n" + 
		"DEALER_RANGE_FOR_REPORTS \r\n" + 
		"DATE_RANGE_FOR_REPORTS\r\n" + 
		"),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select  rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL\r\n" + 
		"from DPB_UNBLK_VEH rtl , basic_data bdata\r\n" + 
		"where bdata.VEH_STS = 'I2' and rtl.NUM_PO = bdata.NUM_PO \r\n" + 
		"group by rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL\r\n" + 
		"),\r\n" + 
		"RTL_DATA (ID_DPB_UNBLK_VEH, RTL_DTE) as (\r\n" + 
		"select nd1.ID_DPB_UNBLK_VEH, (select nd3.RTL_DTE from NUM_PO_DATA nd3 \r\n" + 
		"where nd3.ID_DPB_UNBLK_VEH = max(nd2.ID_DPB_UNBLK_VEH)) as RTL_DTE\r\n" + 
		"from NUM_PO_DATA nd1, NUM_PO_DATA nd2\r\n" + 
		"where nd1.VEH_STS = 'I2'\r\n" + 
		"and nd2.ID_DPB_UNBLK_VEH < nd1.ID_DPB_UNBLK_VEH\r\n" + 
		"and nd1.NUM_PO = nd2.NUM_PO\r\n" + 
		"group by nd1.ID_DPB_UNBLK_VEH\r\n" + 
		"),\r\n" + 
		"PGM_DATA (ID_DPB_UNBLK_VEH, FLR_PLN, CUST_EXP, CEX_SLS, CEX_SRC, PRE_OWN, NV_SAL, BRD_STDS) as (\r\n" + 
		"select ID_DPB_UNBLK_VEH,\r\n" + 
		"sum(case when (ID_PGM = 46 and VEH_TYP = 'P') or (ID_PGM = 51 and VEH_TYP = 'S') or \r\n" + 
		"    (ID_PGM = 52 and VEH_TYP = 'V') or (ID_PGM = 53 and VEH_TYP = 'F') then AMT end) as FLR_PLN,\r\n" + 
		"sum(case when \r\n" + 
		"    (ID_PGM = 57 and VEH_TYP = 'V') or (ID_PGM = 60 and VEH_TYP = 'F') then AMT end) as CUST_EXP,\r\n" + 
		"sum(case when (ID_PGM = 144 and VEH_TYP = 'P') then AMT end) as CEX_SLS,\r\n" + 
		"sum(case when (ID_PGM = 145 and VEH_TYP = 'P') then AMT end) as CEX_SRC,\r\n" + 
		"sum(case when (ID_PGM = 49 and VEH_TYP = 'P') then AMT end) as PRE_OWN,\r\n" + 
		"sum(case when (ID_PGM = 48 and VEH_TYP = 'P') or \r\n" + 
		"    (ID_PGM = 59 and VEH_TYP = 'V') or (ID_PGM = 62 and VEH_TYP = 'F') then AMT end) as NV_SAL,\r\n" + 
		"sum(case when (ID_PGM = 50 and VEH_TYP = 'P') or (ID_PGM = 141 and VEH_TYP = 'S') or \r\n" + 
		"    (ID_PGM = 58 and VEH_TYP = 'V') or (ID_PGM = 61 and VEH_TYP = 'F') then AMT end) as BRD_STDS\r\n" + 
		"from basic_data\r\n" + 
		"group by ID_DPB_UNBLK_VEH),\r\n" + 
		"Temp_Result(PO,MY,Model,SERIAL,Dealer,Rtl_Date,FLR_PLN,CES,CEX_SLS,CEX_SRC,PRE_OWN,NV_SAL,BRD_STDS,TOTAL) AS (\r\n" + 
		"(select distinct pd.NUM_PO as PO,\r\n" + 
		"pd.DTE_MODL_YR as MY, \r\n" + 
		"pd.DES_MODL as Model, pd.SERIAL as SERIAL, \r\n" + 
		"pd.ID_DLR as Dealer, \r\n" + 
		"pd.RTL_DTE as Rtl_Date,\r\n" + 
		"VARCHAR_FORMAT(coalesce(pgmD.FLR_PLN,0),'999999990.99') as FLR_PLN,\r\n" + 
		"VARCHAR_FORMAT(coalesce(pgmD.CUST_EXP,0),'999999990.99') as CES,\r\n" + 
		"VARCHAR_FORMAT(coalesce(pgmD.CEX_SLS,0),'999999990.99') as CEX_SLS,\r\n" + 
		"VARCHAR_FORMAT(coalesce(pgmD.CEX_SRC,0),'999999990.99') as CEX_SRC,\r\n" + 
		"VARCHAR_FORMAT(coalesce(pgmD.PRE_OWN,0),'999999990.99') as PRE_OWN,\r\n" + 
		"VARCHAR_FORMAT(coalesce(pgmD.NV_SAL,0),'999999990.99') as NV_SAL,\r\n" + 
		"VARCHAR_FORMAT(coalesce(pgmD.BRD_STDS,0),'999999990.99') as BRD_STDS,\r\n" + 
		"VARCHAR_FORMAT((coalesce(pgmD.FLR_PLN,0)+coalesce(pgmD.CUST_EXP,0)+coalesce(pgmD.CEX_SLS,0)+coalesce(pgmD.CEX_SRC,0)\r\n" + 
		"+coalesce(pgmD.PRE_OWN,0)+coalesce(pgmD.NV_SAL,0)+coalesce(pgmD.BRD_STDS,0)),'999999990.99') as TOTAL\r\n" + 
		"  from basic_data pd \r\n" + 
		"left outer join PGM_DATA pgmD\r\n" + 
		"on pd.ID_DPB_UNBLK_VEH = pgmD.ID_DPB_UNBLK_VEH  \r\n" + 
		"left outer join RTL_DATA rd on pd.ID_DPB_UNBLK_VEH = rd.ID_DPB_UNBLK_VEH \r\n" + 
		"order by SERIAL,Rtl_Date)),\r\n" + 
		"Final_Result(PO,MY,Model,SERIAL,Dealer,Rtl_Date,FLR_PLN,CES,CEX_SLS,CEX_SRC,PRE_OWN,NV_SAL,BRD_STDS,TOTAL)\r\n" + 
		"AS((SELECT temp.PO,\r\n" + 
		"temp.MY,temp.Model,temp.SERIAL,temp.Dealer,temp.Rtl_Date,temp.FLR_PLN,temp.CES,temp.CEX_SLS,temp.CEX_SRC,\r\n" + 
		"temp.PRE_OWN,temp.NV_SAL,temp.BRD_STDS,temp.TOTAL FROM Temp_Result temp))\r\n" + 
		"select * from Final_Result  with ur";
//Courtesy Vehicle Report -end




//Dealer Performance Unearned Bonus Report - FNC27 - Start
public static final String UNEARNED_REPORT_FTL_COMMON_QUERY = "with basic_data(dte_rtl,tme_rtl,id_dlr,NUM_PO,des_modl,Cust_Exp_4,Unearn_Cus_Exp_4,CV_Brd_Std_4,Unearn_CV_BStd_4,Sales_Bonus_4,Unearn_Sls_Bns_4,Dlr_Reseve_4,VEH_STS,ID_DPB_UNBLK_VEH,NUM_VIN) as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"rtl.dte_rtl,\r\n" + 
		"rtl.tme_rtl,\r\n" + 
		"calc.id_dlr,rtl.NUM_PO,rtl.des_modl,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 60) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Cust_Exp_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 60) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Cus_Exp_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 61) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as CV_Brd_Std_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 61) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_CV_BStd_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 62) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Sales_Bonus_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 62) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Sls_Bns_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 53) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Dlr_Reseve_4,\r\n" + 
		"\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"rtl.ID_DPB_UNBLK_VEH,rtl.NUM_VIN\r\n" + 
		"from dpbuser.DPB_CALC  calc, dpbuser.DPB_UNBLK_VEH  rtl   where\r\n" + 
		"calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"DEALER_RANGE_FOR_REPORTS DATE_RANGE_FOR_REPORTS and calc.ID_DPB_PGM in (60,61,62,53) and rtl.CDE_VEH_TYP = 'F'\r\n" + 
		"group by rtl.ID_DPB_UNBLK_VEH,rtl.dte_rtl,rtl.tme_rtl,calc.id_dlr,rtl.NUM_PO,rtl.des_modl,rtl.CDE_VEH_DDR_STS,rtl.NUM_VIN),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select distinct rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL \r\n" + 
		"from dpbuser.DPB_UNBLK_VEH rtl , basic_data cal\r\n" + 
		"where cal.VEH_STS = 'I2' and rtl.NUM_PO = cal.NUM_PO\r\n" + 
		") ,\r\n" + 
		"NUM_PO_ORDER (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE, ROW_NUM) as (\r\n" + 
		"select NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE,\r\n" + 
		"row_number() over(PARTITION BY NUM_PO order by NUM_PO, ID_DPB_UNBLK_VEH) as ROW_NUM\r\n" + 
		"from NUM_PO_DATA\r\n" + 
		"),\r\n" + 
		"Final_Result(ROWNUM,DTE_RTL,id_dlr,NUM_PO,des_modl,Cust_Exp_4,Unearn_Cus_Exp_4,CV_Brd_Std_4,Unearn_CV_BStd_4,Sales_Bonus_4,Unearn_Sls_Bns_4,Dlr_Reseve_4,NUM_VIN) as \r\n" + 
		"(select ROW_NUMBER() OVER (ORDER BY pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl ASC) AS ROWNUM,\r\n" + 
		"coalesce(case when pd.VEH_STS = 'I2' then VARCHAR_FORMAT(\r\n" + 
		"(select RTL_DTE from NUM_PO_ORDER npo1 where npo1.ROW_NUM = (\r\n" + 
		"select (npo.ROW_NUM-1) from NUM_PO_ORDER npo where npo.ID_DPB_UNBLK_VEH=pd.ID_DPB_UNBLK_VEH) \r\n" + 
		"and npo1.NUM_PO = pd.NUM_PO),'MM/DD/YY') END, \r\n" + 
		"VARCHAR_FORMAT(DTE_RTL,'MM/DD/YY')) as DTE_RTL,\r\n" + 
		"id_dlr,NUM_PO,des_modl,Cust_Exp_4,Unearn_Cus_Exp_4,CV_Brd_Std_4,Unearn_CV_BStd_4,Sales_Bonus_4,Unearn_Sls_Bns_4,Dlr_Reseve_4,NUM_VIN from basic_data pd \r\n" + 
		"order by pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl,pd.NUM_VIN)";

public static final String UNEARNED_REPORT_COUNT_QUERY = "select count(*) from Final_Result with ur";
public static final String UNEARNED_REPORT_PAGINATION_QUERY = "select * from Final_Result where rownum between "+IConstants.PAGE_START_INDEX+" and "+IConstants.PAGE_END_INDEX+" with ur";
public static final String UNEARNED_REPORT_FTL_TOTAL_QUERY = "with basic_data(dte_rtl,id_dlr,NUM_PO,des_modl,Cust_Exp_4,Unearn_Cus_Exp_4,CV_Brd_Std_4,Unearn_CV_BStd_4,Sales_Bonus_4,Unearn_Sls_Bns_4,Dlr_Reseve_4,VEH_STS,ID_DPB_UNBLK_VEH) as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"rtl.dte_rtl\r\n" + 
		",calc.id_dlr,rtl.NUM_PO,rtl.des_modl,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 60) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Cust_Exp_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 60) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Cus_Exp_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 61) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as CV_Brd_Std_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 61) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_CV_BStd_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 62) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Sales_Bonus_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 62) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Sls_Bns_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 53) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Dlr_Reseve_4,\r\n" + 
		"\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"from dpbuser.DPB_CALC  calc, dpbuser.DPB_UNBLK_VEH  rtl   where\r\n" + 
		"calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"DEALER_RANGE_FOR_REPORTS DATE_RANGE_FOR_REPORTS " +
		"and calc.ID_DPB_PGM in (60,61,62,53) and rtl.CDE_VEH_TYP = 'F'\r\n" + 
		"group by rtl.ID_DPB_UNBLK_VEH,rtl.dte_rtl,calc.id_dlr,rtl.NUM_PO,rtl.des_modl,rtl.CDE_VEH_DDR_STS),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select distinct rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL \r\n" + 
		"from dpbuser.DPB_UNBLK_VEH rtl , basic_data cal\r\n" + 
		"where cal.VEH_STS = 'I2' and rtl.NUM_PO = cal.NUM_PO\r\n" + 
		") ,\r\n" + 
		"NUM_PO_ORDER (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE, ROW_NUM) as (\r\n" + 
		"select NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE,\r\n" + 
		"row_number() over(PARTITION BY NUM_PO order by NUM_PO, ID_DPB_UNBLK_VEH) as ROW_NUM\r\n" + 
		"from NUM_PO_DATA\r\n" + 
		")\r\n" + 
		"select -1,null,null,null,null,sum(Cust_Exp_4),sum(Unearn_Cus_Exp_4),sum(CV_Brd_Std_4),sum(Unearn_CV_BStd_4),\r\n" + 
		"sum(Sales_Bonus_4),sum(Unearn_Sls_Bns_4),sum(Dlr_Reseve_4) from\r\n" + 
		"basic_data \r\n" + 
		"with ur";

public static final String UNEARNED_REPORT_SMART_COMMON_QUERY = "with basic_data(dte_rtl,tme_rtl,id_dlr,NUM_PO,des_modl,Sm_Brd_Stds_3,Dlr_Reseve_3,Unearn_Sm_Brd_Stds_3,VEH_STS,ID_DPB_UNBLK_VEH,NUM_VIN) as\r\n" + 
		"\r\n" + 
		"(\r\n" + 
		"\r\n" + 
		"select \r\n" + 
		"\r\n" + 
		"rtl.dte_rtl,\r\n" + 
		"\r\n" + 
		"rtl.tme_rtl,\r\n" + 
		"\r\n" + 
		"calc.id_dlr,rtl.NUM_PO,rtl.des_modl,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 141) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Sm_Brd_Stds_3,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 51) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Dlr_Reseve_3,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm in (141)) then \r\n" + 
		"case when rtl.CDE_VEH_DDR_STS = 'I2' then \r\n" + 
		"	coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"else\r\n" + 
		"    (case when  calc.id_dpb_pgm = 141 then coalesce(calc.AMT_DPB_UNERND,0) end) \r\n" + 
		"end\r\n" + 
		"end ),0)) as Unearn_Sm_Brd_Stds_3,\r\n" + 
		"\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"\r\n" + 
		"rtl.ID_DPB_UNBLK_VEH,rtl.NUM_VIN\r\n" + 
		"\r\n" + 
		"from dpbuser.DPB_CALC  calc, dpbuser.DPB_UNBLK_VEH  rtl   where\r\n" + 
		"\r\n" + 
		"calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"\r\n" + 
		"DEALER_RANGE_FOR_REPORTS DATE_RANGE_FOR_REPORTS and calc.ID_DPB_PGM in (51,54,55,56,141) and rtl.CDE_VEH_TYP = 'S'\r\n" + 
		"\r\n" + 
		"group by rtl.ID_DPB_UNBLK_VEH,rtl.dte_rtl,rtl.tme_rtl,calc.id_dlr,rtl.NUM_PO,rtl.des_modl,rtl.CDE_VEH_DDR_STS,rtl.NUM_VIN),\r\n" + 
		"\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"\r\n" + 
		"select distinct rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL \r\n" + 
		"\r\n" + 
		"from dpbuser.DPB_UNBLK_VEH rtl , basic_data cal\r\n" + 
		"\r\n" + 
		"where cal.VEH_STS = 'I2' and rtl.NUM_PO = cal.NUM_PO\r\n" + 
		"\r\n" + 
		") ,\r\n" + 
		"\r\n" + 
		"NUM_PO_ORDER (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE, ROW_NUM) as (\r\n" + 
		"\r\n" + 
		"select NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE,\r\n" + 
		"\r\n" + 
		"row_number() over(PARTITION BY NUM_PO order by NUM_PO, ID_DPB_UNBLK_VEH) as ROW_NUM\r\n" + 
		"\r\n" + 
		"from NUM_PO_DATA\r\n" + 
		"\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"Final_Result(ROWNUM,DTE_RTL,id_dlr,NUM_PO,des_modl,Sm_Brd_Stds_3,Dlr_Reseve_3,Unearn_Sm_Brd_Stds_3,NUM_VIN) as\r\n" + 
		"\r\n" + 
		"(select ROW_NUMBER() OVER (ORDER BY pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl ASC) AS ROWNUM,\r\n" + 
		"\r\n" + 
		"coalesce(case when pd.VEH_STS = 'I2' then VARCHAR_FORMAT(\r\n" + 
		"\r\n" + 
		"(select RTL_DTE from NUM_PO_ORDER npo1 where npo1.ROW_NUM = (\r\n" + 
		"\r\n" + 
		"select (npo.ROW_NUM-1) from NUM_PO_ORDER npo where npo.ID_DPB_UNBLK_VEH=pd.ID_DPB_UNBLK_VEH) \r\n" + 
		"\r\n" + 
		"and npo1.NUM_PO = pd.NUM_PO),'MM/DD/YY') END, \r\n" + 
		"\r\n" + 
		"VARCHAR_FORMAT(DTE_RTL,'MM/DD/YY')) as DTE_RTL,\r\n" + 
		"\r\n" + 
		"id_dlr,NUM_PO,des_modl,Sm_Brd_Stds_3,Dlr_Reseve_3,Unearn_Sm_Brd_Stds_3,NUM_VIN from basic_data pd \r\n" + 
		"\r\n" + 
		"order by pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl,pd.NUM_VIN)\r\n" + 
		"";
public static final String UNEARNED_REPORT_SMART_TOTAL_QUERY = "with basic_data(dte_rtl,tme_rtl,id_dlr,NUM_PO,des_modl,Sm_Brd_Stds_3,Dlr_Reseve_3,Unearn_Sm_Brd_Stds_3,VEH_STS,ID_DPB_UNBLK_VEH) as\r\n" + 
		"\r\n" + 
		"(\r\n" + 
		"\r\n" + 
		"select \r\n" + 
		"\r\n" + 
		"rtl.dte_rtl,\r\n" + 
		"\r\n" + 
		"rtl.tme_rtl,\r\n" + 
		"\r\n" + 
		"calc.id_dlr,rtl.NUM_PO,rtl.des_modl,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 141) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Sm_Brd_Stds_3,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 51) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Dlr_Reseve_3,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm in (141)) then \r\n" + 
		"case when rtl.CDE_VEH_DDR_STS = 'I2' then \r\n" + 
		"	coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"else\r\n" + 
		"    (case when  calc.id_dpb_pgm = 141 then coalesce(calc.AMT_DPB_UNERND,0) end) \r\n" + 
		"end\r\n" + 
		"end ),0)) as Unearn_Sm_Brd_Stds_3,\r\n" + 
		"\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"\r\n" + 
		"rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"\r\n" + 
		"from dpbuser.DPB_CALC  calc, dpbuser.DPB_UNBLK_VEH  rtl   where\r\n" + 
		"\r\n" + 
		"calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"\r\n" + 
		"DEALER_RANGE_FOR_REPORTS DATE_RANGE_FOR_REPORTS and calc.ID_DPB_PGM in (51,54,55,56,141) and rtl.CDE_VEH_TYP = 'S'\r\n" + 
		"group by rtl.ID_DPB_UNBLK_VEH,rtl.dte_rtl,rtl.tme_rtl,calc.id_dlr,rtl.NUM_PO,rtl.des_modl,rtl.CDE_VEH_DDR_STS),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select distinct rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL \r\n" + 
		"from dpbuser.DPB_UNBLK_VEH rtl , basic_data cal\r\n" + 
		"where cal.VEH_STS = 'I2' and rtl.NUM_PO = cal.NUM_PO\r\n" + 
		") ,\r\n" + 
		"NUM_PO_ORDER (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE, ROW_NUM) as (\r\n" + 
		"select NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE,\r\n" + 
		"row_number() over(PARTITION BY NUM_PO order by NUM_PO, ID_DPB_UNBLK_VEH) as ROW_NUM\r\n" + 
		"from NUM_PO_DATA\r\n" + 
		")\r\n" + 
		"select -1,null,null,null,null,sum(Sm_Brd_Stds_3),sum(Dlr_Reseve_3),sum(Unearn_Sm_Brd_Stds_3) from\r\n" + 
		"\r\n" + 
		"basic_data with ur\r\n" + 
		"";

public static final String UNEARNED_REPORT_VAN_COMMON_QUERY = "with basic_data(dte_rtl,tme_rtl,id_dlr,NUM_PO,des_modl,Cust_Exp_2,Unearn_Cus_Exp_2,CV_Brd_Std_2,Unearn_CV_BStd_2,Sales_Bonus_2,Unearn_Sls_Bns_2,Dlr_Reseve_2,VEH_STS,ID_DPB_UNBLK_VEH,NUM_VIN) as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"rtl.dte_rtl,\r\n" + 
		"rtl.tme_rtl,\r\n" + 
		"calc.id_dlr,rtl.NUM_PO,rtl.des_modl,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 57) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Cust_Exp_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 57) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Cus_Exp_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 58) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as CV_Brd_Std_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 58) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_CV_BStd_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 59) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Sales_Bonus_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 59) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Sls_Bns_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 52) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Dlr_Reseve_2,\r\n" + 
		"\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"rtl.ID_DPB_UNBLK_VEH,rtl.NUM_VIN\r\n" + 
		"from dpbuser.DPB_CALC  calc, dpbuser.DPB_UNBLK_VEH  rtl   where\r\n" + 
		"calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"DEALER_RANGE_FOR_REPORTS DATE_RANGE_FOR_REPORTS and calc.ID_DPB_PGM in (57,58,59,52) and rtl.CDE_VEH_TYP = 'V'\r\n" + 
		"group by rtl.ID_DPB_UNBLK_VEH,rtl.dte_rtl,rtl.tme_rtl,calc.id_dlr,rtl.NUM_PO,rtl.des_modl,rtl.CDE_VEH_DDR_STS,rtl.NUM_VIN),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select distinct rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL \r\n" + 
		"from dpbuser.DPB_UNBLK_VEH rtl , basic_data cal\r\n" + 
		"where cal.VEH_STS = 'I2' and rtl.NUM_PO = cal.NUM_PO\r\n" + 
		") ,\r\n" + 
		"NUM_PO_ORDER (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE, ROW_NUM) as (\r\n" + 
		"select NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE,\r\n" + 
		"row_number() over(PARTITION BY NUM_PO order by NUM_PO, ID_DPB_UNBLK_VEH) as ROW_NUM\r\n" + 
		"from NUM_PO_DATA\r\n" + 
		"),\r\n" + 
		"Final_Result(ROWNUM,DTE_RTL,id_dlr,NUM_PO,des_modl,Cust_Exp_2,Unearn_Cus_Exp_2,CV_Brd_Std_2,Unearn_CV_BStd_2,Sales_Bonus_2,Unearn_Sls_Bns_2,Dlr_Reseve_2,NUM_VIN) as \r\n" + 
		"(select ROW_NUMBER() OVER (ORDER BY pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl ASC) AS ROWNUM,\r\n" + 
		"coalesce(case when pd.VEH_STS = 'I2' then VARCHAR_FORMAT(\r\n" + 
		"(select RTL_DTE from NUM_PO_ORDER npo1 where npo1.ROW_NUM = (\r\n" + 
		"select (npo.ROW_NUM-1) from NUM_PO_ORDER npo where npo.ID_DPB_UNBLK_VEH=pd.ID_DPB_UNBLK_VEH) \r\n" + 
		"and npo1.NUM_PO = pd.NUM_PO),'MM/DD/YY') END, \r\n" + 
		"VARCHAR_FORMAT(DTE_RTL,'MM/DD/YY')) as DTE_RTL,\r\n" + 
		"id_dlr,NUM_PO,des_modl,Cust_Exp_2,Unearn_Cus_Exp_2,CV_Brd_Std_2,Unearn_CV_BStd_2,Sales_Bonus_2,Unearn_Sls_Bns_2,Dlr_Reseve_2,NUM_VIN from basic_data pd \r\n" + 
		"order by pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl,pd.NUM_VIN)";

public static final String UNEARNED_REPORT_VAN_TOTAL_QUERY ="with basic_data(dte_rtl,id_dlr,NUM_PO,des_modl,Cust_Exp_2,Unearn_Cus_Exp_2,CV_Brd_Std_2,Unearn_CV_BStd_2,Sales_Bonus_2,Unearn_Sls_Bns_2,Dlr_Reseve_2,VEH_STS,ID_DPB_UNBLK_VEH) as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"rtl.dte_rtl\r\n" + 
		",calc.id_dlr,rtl.NUM_PO,rtl.des_modl,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 57) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Cust_Exp_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 57) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Cus_Exp_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 58) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as CV_Brd_Std_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 58) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_CV_BStd_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 59) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Sales_Bonus_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 59) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Sls_Bns_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 52) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Dlr_Reseve_2,\r\n" + 
		"\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"from dpbuser.DPB_CALC  calc, dpbuser.DPB_UNBLK_VEH  rtl   where\r\n" + 
		"calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"DEALER_RANGE_FOR_REPORTS DATE_RANGE_FOR_REPORTS and calc.ID_DPB_PGM in (57,58,59,52) and rtl.CDE_VEH_TYP = 'V'\r\n" + 
		"group by rtl.ID_DPB_UNBLK_VEH,rtl.dte_rtl,calc.id_dlr,rtl.NUM_PO,rtl.des_modl,rtl.CDE_VEH_DDR_STS),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select distinct rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL \r\n" + 
		"from dpbuser.DPB_UNBLK_VEH rtl , basic_data cal\r\n" + 
		"where cal.VEH_STS = 'I2' and rtl.NUM_PO = cal.NUM_PO\r\n" + 
		") ,\r\n" + 
		"NUM_PO_ORDER (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE, ROW_NUM) as (\r\n" + 
		"select NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE,\r\n" + 
		"row_number() over(PARTITION BY NUM_PO order by NUM_PO, ID_DPB_UNBLK_VEH) as ROW_NUM\r\n" + 
		"from NUM_PO_DATA\r\n" + 
		")\r\n" + 
		"select -1,null,null,null,null,sum(Cust_Exp_2),sum(Unearn_Cus_Exp_2),sum(CV_Brd_Std_2),sum(Unearn_CV_BStd_2),\r\n" + 
		"sum(Sales_Bonus_2),sum(Unearn_Sls_Bns_2),sum(Dlr_Reseve_2) from\r\n" + 
		"basic_data \r\n" + 
		"with ur";

public static final String UNEARNED_REPORT_CARS_COMMON_QUERY = "with basic_data(dte_rtl,tme_rtl,id_dlr,NUM_PO,des_modl,Cust_Exp_Sls_1,Unearn_Cust_Exp_Sls_1,Cust_Exp_Svs_1,Unearn_Cust_Exp_Svs_1,PO_Sales_1,Unearn_PO_Sls_1,NV_Sales_1,Unearn_NV_Sls_1,Brd_Std_1,Unearn_Brd_Std_1,Flr_Pln,VEH_STS,ID_DPB_UNBLK_VEH,NUM_VIN) as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"rtl.dte_rtl,\r\n" + 
		"rtl.tme_rtl,\r\n" + 
		"calc.id_dlr,rtl.NUM_PO,rtl.des_modl,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm in (47,144)) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Cust_Exp_Sls_1,\r\n" + 
		"--Do not consider kpi 47 after 2016-04-01 for Unearn_Cust_Exp_Sls - Kshitija\r\n" + 
		"case when rtl.dte_rtl < '2016-04-01' then\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm in (47,144)) then \r\n" + 
		"case when rtl.CDE_VEH_DDR_STS = 'I2'  then \r\n" + 
		"	coalesce(calc.AMT_DPB_UNERND,0) \r\n" + 
		"else\r\n" + 
		"    (case when calc.id_dpb_pgm = 144 then coalesce(calc.AMT_DPB_UNERND,0) end)\r\n" + 
		"end\r\n" + 
		"end ),0)) \r\n" + 
		"else\r\n" + 
		"sum(case when calc.id_dpb_pgm = 144 then coalesce(calc.AMT_DPB_UNERND,0) end)\r\n" + 
		"end as Unearn_Cust_Exp_Sls_1," + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 145) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Cust_Exp_Svs_1,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 145) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Cust_Exp_Svs_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 49) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as PO_Sales_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 49) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_PO_Sls_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 48) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as NV_Sales_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 48) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_NV_Sls_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 50 ) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Brd_Std_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 50 ) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Brd_Std_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 46 ) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Flr_Pln,\r\n" + 
		"--Unearn_Cust_Exp_Sls_1+Unearn_Cust_Exp_Svs_1+Unearn_PO_Sls_1+Unearn_NV_Sls_1+Unearn_Brd_Std_1 as Cars_Un_Tot,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"rtl.ID_DPB_UNBLK_VEH,rtl.NUM_VIN\r\n" + 
		"from dpbuser.DPB_CALC  calc, dpbuser.DPB_UNBLK_VEH  rtl   where\r\n" + 
		"calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"DEALER_RANGE_FOR_REPORTS DATE_RANGE_FOR_REPORTS and calc.ID_DPB_PGM in (47,144,145,49,48,50,46) and rtl.CDE_VEH_TYP = 'P'\r\n" + 
		"group by rtl.ID_DPB_UNBLK_VEH,rtl.dte_rtl,rtl.tme_rtl,calc.id_dlr,rtl.NUM_PO,rtl.des_modl,rtl.CDE_VEH_DDR_STS,rtl.NUM_VIN),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select distinct rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL \r\n" + 
		"from dpbuser.DPB_UNBLK_VEH rtl , basic_data cal\r\n" + 
		"where cal.VEH_STS = 'I2' and rtl.NUM_PO = cal.NUM_PO\r\n" + 
		") ,\r\n" + 
		"NUM_PO_ORDER (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE, ROW_NUM) as (\r\n" + 
		"select NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE,\r\n" + 
		"row_number() over(PARTITION BY NUM_PO order by NUM_PO, ID_DPB_UNBLK_VEH) as ROW_NUM\r\n" + 
		"from NUM_PO_DATA\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"Final_Result(ROWNUM,DTE_RTL,id_dlr,NUM_PO,des_modl,Cust_Exp_Sls_1,Unearn_Cust_Exp_Sls_1,Cust_Exp_Svs_1,Unearn_Cust_Exp_Svs_1,PO_Sales_1,Unearn_PO_Sls_1,NV_Sales_1,Unearn_NV_Sls_1,Brd_Std_1,Unearn_Brd_Std_1,Flr_Pln,NUM_VIN)as \r\n" + 
		"(\r\n" + 
		"(select ROW_NUMBER() OVER (ORDER BY pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl ASC) AS ROWNUM,\r\n" + 
		"coalesce(case when pd.VEH_STS = 'I2' then VARCHAR_FORMAT(\r\n" + 
		"(select RTL_DTE from NUM_PO_ORDER npo1 where npo1.ROW_NUM = (\r\n" + 
		"select (npo.ROW_NUM-1) from NUM_PO_ORDER npo where npo.ID_DPB_UNBLK_VEH=pd.ID_DPB_UNBLK_VEH) \r\n" + 
		"and npo1.NUM_PO = pd.NUM_PO),'MM/DD/YY') END, \r\n" + 
		"VARCHAR_FORMAT(DTE_RTL,'MM/DD/YY')) as DTE_RTL,\r\n" + 
		"id_dlr,NUM_PO,des_modl,Cust_Exp_Sls_1,Unearn_Cust_Exp_Sls_1,Cust_Exp_Svs_1,Unearn_Cust_Exp_Svs_1,PO_Sales_1,Unearn_PO_Sls_1,NV_Sales_1,Unearn_NV_Sls_1,Brd_Std_1,Unearn_Brd_Std_1,Flr_Pln,NUM_VIN from basic_data pd \r\n" + 
		"order by pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl,pd.NUM_VIN)\r\n" + 
		")";
public static final String UNEARNED_REPORT_CARS_TOTAL_QUERY = "with basic_data(dte_rtl,tme_rtl,id_dlr,NUM_PO,des_modl,Cust_Exp_Sls_1,Unearn_Cust_Exp_Sls_1,Cust_Exp_Svs_1,Unearn_Cust_Exp_Svs_1,PO_Sales_1,Unearn_PO_Sls_1,NV_Sales_1,Unearn_NV_Sls_1,Brd_Std_1,Unearn_Brd_Std_1,Flr_Pln,VEH_STS,ID_DPB_UNBLK_VEH) as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"rtl.dte_rtl,\r\n" + 
		"rtl.tme_rtl,\r\n" + 
		"calc.id_dlr,rtl.NUM_PO,rtl.des_modl,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm in (47,144)) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Cust_Exp_Sls_1,\r\n" + 
		"--Do not consider kpi 47 after 2016-04-01 for Unearn_Cust_Exp_Sls - Kshitija\r\n" + 
		"case when rtl.dte_rtl < '2016-04-01' then\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm in (47,144)) then \r\n" + 
		"case when rtl.CDE_VEH_DDR_STS = 'I2'  then \r\n" + 
		"	coalesce(calc.AMT_DPB_UNERND,0) \r\n" + 
		"else\r\n" + 
		"    (case when calc.id_dpb_pgm = 144 then coalesce(calc.AMT_DPB_UNERND,0) end)\r\n" + 
		"end\r\n" + 
		"end ),0)) \r\n" + 
		"else\r\n" + 
		"sum(case when calc.id_dpb_pgm = 144 then coalesce(calc.AMT_DPB_UNERND,0) end)\r\n" + 
		"end as Unearn_Cust_Exp_Sls_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 145) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Cust_Exp_Svs_1,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 145) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Cust_Exp_Svs_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 49) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as PO_Sales_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 49) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_PO_Sls_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 48) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as NV_Sales_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 48) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_NV_Sls_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 50 ) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Brd_Std_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 50 ) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Brd_Std_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 46 ) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Flr_Pln,\r\n" + 
		"--Unearn_Cust_Exp_Sls_1+Unearn_Cust_Exp_Svs_1+Unearn_PO_Sls_1+Unearn_NV_Sls_1+Unearn_Brd_Std_1 as Cars_Un_Tot,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"from dpbuser.DPB_CALC  calc, dpbuser.DPB_UNBLK_VEH  rtl   where\r\n" + 
		"calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"DEALER_RANGE_FOR_REPORTS DATE_RANGE_FOR_REPORTS and calc.ID_DPB_PGM in (47,144,145,49,48,50,46) and rtl.CDE_VEH_TYP = 'P'\r\n" + 
		"group by rtl.ID_DPB_UNBLK_VEH,rtl.dte_rtl,rtl.tme_rtl,calc.id_dlr,rtl.NUM_PO,rtl.des_modl,rtl.CDE_VEH_DDR_STS),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select distinct rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL \r\n" + 
		"from dpbuser.DPB_UNBLK_VEH rtl , basic_data cal\r\n" + 
		"where cal.VEH_STS = 'I2' and rtl.NUM_PO = cal.NUM_PO\r\n" + 
		") ,\r\n" + 
		"NUM_PO_ORDER (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE, ROW_NUM) as (\r\n" + 
		"select NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE,\r\n" + 
		"row_number() over(PARTITION BY NUM_PO order by NUM_PO, ID_DPB_UNBLK_VEH) as ROW_NUM\r\n" + 
		"from NUM_PO_DATA\r\n" + 
		")\r\n" + 
		"select -1,null,null,null,null,sum(Cust_Exp_Sls_1),sum(Unearn_Cust_Exp_Sls_1),sum(Cust_Exp_Svs_1),sum(Unearn_Cust_Exp_Svs_1),sum(PO_Sales_1),sum(Unearn_PO_Sls_1),sum(NV_Sales_1),sum(Unearn_NV_Sls_1),sum(Brd_Std_1),sum(Unearn_Brd_Std_1),sum(Flr_Pln) from\r\n" + 
		"basic_data \r\n" + 
		"with ur";

public static final String UNEARNED_REPORT_FTL_EXPORT_QUERY = "with basic_data(dte_rtl,tme_rtl,id_dlr,NUM_PO,NUM_VIN,des_modl,Cust_Exp_4,Unearn_Cus_Exp_4,CV_Brd_Std_4,Unearn_CV_BStd_4,Sales_Bonus_4,Unearn_Sls_Bns_4,Dlr_Reseve_4,VEH_STS,ID_DPB_UNBLK_VEH) as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"rtl.dte_rtl,\r\n" + 
		"rtl.tme_rtl,\r\n" + 
		"calc.id_dlr,rtl.NUM_PO,rtl.NUM_VIN,rtl.des_modl,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 60) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Cust_Exp_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 60) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Cus_Exp_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 61) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as CV_Brd_Std_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 61) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_CV_BStd_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 62) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Sales_Bonus_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 62) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Sls_Bns_4,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 53) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Dlr_Reseve_4,\r\n" + 
		"\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"from dpbuser.DPB_CALC  calc, dpbuser.DPB_UNBLK_VEH  rtl   where\r\n" + 
		"calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"--rtl.dte_rtl between '2015-01-01' and '2016-01-01' \r\n" + 
		"DEALER_RANGE_FOR_REPORTS DATE_RANGE_FOR_REPORTS\r\n" +
		"and calc.ID_DPB_PGM in (60,61,62,53) and rtl.CDE_VEH_TYP = 'F'\r\n" + 
		"group by rtl.ID_DPB_UNBLK_VEH,rtl.dte_rtl,rtl.tme_rtl,calc.id_dlr,rtl.NUM_PO,rtl.des_modl,rtl.CDE_VEH_DDR_STS,rtl.NUM_VIN),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select distinct rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL \r\n" + 
		"from dpbuser.DPB_UNBLK_VEH rtl , basic_data cal\r\n" + 
		"where cal.VEH_STS = 'I2' and rtl.NUM_PO = cal.NUM_PO\r\n" + 
		") ,\r\n" + 
		"NUM_PO_ORDER (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE, ROW_NUM) as (\r\n" + 
		"select NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE,\r\n" + 
		"row_number() over(PARTITION BY NUM_PO order by NUM_PO, ID_DPB_UNBLK_VEH) as ROW_NUM\r\n" + 
		"from NUM_PO_DATA\r\n" + 
		")\r\n" + 
		"\r\n" + 
		"select null,null,null,null,null,'Total:               ' as TEXT,sum(Cust_Exp_4),sum(Unearn_Cus_Exp_4),sum(CV_Brd_Std_4),sum(Unearn_CV_BStd_4),\r\n" + 
		"sum(Sales_Bonus_4),sum(Unearn_Sls_Bns_4),sum(Dlr_Reseve_4) from\r\n" + 
		"basic_data \r\n" + 
		"\r\n" + 
		"union all\r\n" + 
		"\r\n" + 
		"(select ROW_NUMBER() OVER (ORDER BY pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl,pd.NUM_VIN ASC) AS ROWNUM,\r\n" + 
		"coalesce(case when pd.VEH_STS = 'I2' then VARCHAR_FORMAT(\r\n" + 
		"(select RTL_DTE from NUM_PO_ORDER npo1 where npo1.ROW_NUM = (\r\n" + 
		"select (npo.ROW_NUM-1) from NUM_PO_ORDER npo where npo.ID_DPB_UNBLK_VEH=pd.ID_DPB_UNBLK_VEH) \r\n" + 
		"and npo1.NUM_PO = pd.NUM_PO),'MM/DD/YY') END, \r\n" + 
		"VARCHAR_FORMAT(DTE_RTL,'MM/DD/YY')) as DTE_RTL,\r\n" + 
		"id_dlr,NUM_PO,NUM_VIN,des_modl,Cust_Exp_4,Unearn_Cus_Exp_4,CV_Brd_Std_4,Unearn_CV_BStd_4,Sales_Bonus_4,Unearn_Sls_Bns_4,Dlr_Reseve_4 from basic_data pd \r\n" + 
		"order by pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl,pd.NUM_VIN)\r\n" + 
		"with ur";

public static final String UNEARNED_REPORT_CARS_EXPORT_QUERY = "with basic_data(dte_rtl,tme_rtl,id_dlr,NUM_PO,NUM_VIN,des_modl,Cust_Exp_Sls_1,Unearn_Cust_Exp_Sls_1,Cust_Exp_Svs_1,Unearn_Cust_Exp_Svs_1,PO_Sales_1,Unearn_PO_Sls_1,NV_Sales_1,Unearn_NV_Sls_1,Brd_Std_1,Unearn_Brd_Std_1,Flr_Pln,VEH_STS,ID_DPB_UNBLK_VEH) as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"rtl.dte_rtl,\r\n" + 
		"rtl.tme_rtl,\r\n" + 
		"calc.id_dlr,rtl.NUM_PO,rtl.NUM_VIN,rtl.des_modl,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm in (47,144)) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Cust_Exp_Sls_1,\r\n" + 
		"--Do not consider kpi 47 after 2016-04-01 for Unearn_Cust_Exp_Sls - Kshitija\r\n" + 
		"case when rtl.dte_rtl < '2016-04-01' then\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm in (47,144)) then \r\n" + 
		"case when rtl.CDE_VEH_DDR_STS = 'I2'  then \r\n" + 
		"	coalesce(calc.AMT_DPB_UNERND,0) \r\n" + 
		"else\r\n" + 
		"    (case when calc.id_dpb_pgm = 144 then coalesce(calc.AMT_DPB_UNERND,0) end)\r\n" + 
		"end\r\n" + 
		"end ),0)) \r\n" + 
		"else\r\n" + 
		"sum(case when calc.id_dpb_pgm = 144 then coalesce(calc.AMT_DPB_UNERND,0) end)\r\n" + 
		"end as Unearn_Cust_Exp_Sls_1," + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 145) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Cust_Exp_Svs_1,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 145) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Cust_Exp_Svs_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 49) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as PO_Sales_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 49) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_PO_Sls_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 48) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as NV_Sales_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 48) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_NV_Sls_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 50 ) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Brd_Std_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 50 ) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Brd_Std_1,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 46 ) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Flr_Pln,\r\n" + 
		"--Unearn_Cust_Exp_Sls_1+Unearn_Cust_Exp_Svs_1+Unearn_PO_Sls_1+Unearn_NV_Sls_1+Unearn_Brd_Std_1 as Cars_Un_Tot,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"from dpbuser.DPB_CALC  calc, dpbuser.DPB_UNBLK_VEH  rtl   where\r\n" + 
		"calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"--rtl.dte_rtl between '2015-01-01' and '2016-01-01' \r\n" + 
		"DEALER_RANGE_FOR_REPORTS DATE_RANGE_FOR_REPORTS\r\n" +
		"and calc.ID_DPB_PGM in (47,144,145,49,48,50,46) and rtl.CDE_VEH_TYP = 'P'\r\n" + 
		"group by rtl.ID_DPB_UNBLK_VEH,rtl.dte_rtl,rtl.tme_rtl,calc.id_dlr,rtl.NUM_PO,rtl.des_modl,rtl.CDE_VEH_DDR_STS,rtl.NUM_VIN),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select distinct rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL \r\n" + 
		"from dpbuser.DPB_UNBLK_VEH rtl , basic_data cal\r\n" + 
		"where cal.VEH_STS = 'I2' and rtl.NUM_PO = cal.NUM_PO\r\n" + 
		") ,\r\n" + 
		"NUM_PO_ORDER (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE, ROW_NUM) as (\r\n" + 
		"select NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE,\r\n" + 
		"row_number() over(PARTITION BY NUM_PO order by NUM_PO, ID_DPB_UNBLK_VEH) as ROW_NUM\r\n" + 
		"from NUM_PO_DATA\r\n" + 
		")\r\n" + 
		"--,\r\n" + 
		"--Final_data(ROWNUM,DTE_RTL,id_dlr,NUM_PO,des_modl,Cust_Exp_Sls_1,Unearn_Cust_Exp_Sls_1,Cust_Exp_Svs_1,Unearn_Cust_Exp_Svs_1,PO_Sales_1,Unearn_PO_Sls_1,NV_Sales_1,Unearn_NV_Sls_1,Brd_Std_1,Unearn_Brd_Std_1,Flr_Pln,Cars_Un_Tot)as \r\n" + 
		"--(\r\n" + 
		"--)\r\n" + 
		"\r\n" + 
		"select null,null,null,null,null,'Total:               ' as TEXT,sum(Cust_Exp_Sls_1),sum(Unearn_Cust_Exp_Sls_1),sum(Cust_Exp_Svs_1),sum(Unearn_Cust_Exp_Svs_1),sum(PO_Sales_1),sum(Unearn_PO_Sls_1),sum(NV_Sales_1),sum(Unearn_NV_Sls_1),sum(Brd_Std_1),sum(Unearn_Brd_Std_1),sum(Flr_Pln) from\r\n" + 
		"basic_data\r\n" + 
		"\r\n" + 
		"union all\r\n" + 
		"\r\n" + 
		"(select ROW_NUMBER() OVER (ORDER BY pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.NUM_VIN,pd.des_modl ASC) AS ROWNUM,\r\n" + 
		"coalesce(case when pd.VEH_STS = 'I2' then VARCHAR_FORMAT(\r\n" + 
		"(select RTL_DTE from NUM_PO_ORDER npo1 where npo1.ROW_NUM = (\r\n" + 
		"select (npo.ROW_NUM-1) from NUM_PO_ORDER npo where npo.ID_DPB_UNBLK_VEH=pd.ID_DPB_UNBLK_VEH) \r\n" + 
		"and npo1.NUM_PO = pd.NUM_PO),'MM/DD/YY') END, \r\n" + 
		"VARCHAR_FORMAT(DTE_RTL,'MM/DD/YY')) as DTE_RTL,\r\n" + 
		"id_dlr,NUM_PO,NUM_VIN,des_modl,Cust_Exp_Sls_1,Unearn_Cust_Exp_Sls_1,Cust_Exp_Svs_1,Unearn_Cust_Exp_Svs_1,PO_Sales_1,Unearn_PO_Sls_1,NV_Sales_1,Unearn_NV_Sls_1,Brd_Std_1,Unearn_Brd_Std_1,Flr_Pln from basic_data pd \r\n" + 
		"order by pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl,pd.NUM_VIN)\r\n" + 
		"\r\n" + 
		"--select ROWNUM,DTE_RTL,id_dlr,NUM_PO,des_modl,Cust_Exp_Sls_1,Unearn_Cust_Exp_Sls_1,Cust_Exp_Svs_1,Unearn_Cust_Exp_Svs_1,PO_Sales_1,Unearn_PO_Sls_1,NV_Sales_1,Unearn_NV_Sls_1,Brd_Std_1,Unearn_Brd_Std_1,Flr_Pln,Cars_Un_Tot from Final_data\r\n" + 
		"\r\n" + 
		"with ur";

public static final String UNEARNED_REPORT_SMART_EXPORT_QUERY = "with basic_data(dte_rtl,tme_rtl,id_dlr,NUM_PO,NUM_VIN,des_modl,Sm_Brd_Stds_3,Dlr_Reseve_3,Unearn_Sm_Brd_Stds_3,VEH_STS,ID_DPB_UNBLK_VEH) as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"rtl.dte_rtl,\r\n" + 
		"rtl.tme_rtl,\r\n" + 
		"calc.id_dlr,rtl.NUM_PO,rtl.NUM_VIN,rtl.des_modl,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm in (141)) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Sm_Brd_Stds_3,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 51) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Dlr_Reseve_3,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm in (141)) then \r\n" + 
		"case when rtl.CDE_VEH_DDR_STS = 'I2' then \r\n" + 
		"	coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"else\r\n" + 
		"    (case when  calc.id_dpb_pgm = 141 then coalesce(calc.AMT_DPB_UNERND,0) end) \r\n" + 
		"end\r\n" + 
		"end ),0)) as Unearn_Sm_Brd_Stds_3," + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"from dpbuser.DPB_CALC  calc, dpbuser.DPB_UNBLK_VEH  rtl   where\r\n" + 
		"calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"--rtl.dte_rtl between '2015-01-01' and '2016-01-01' \r\n" + 
		"DEALER_RANGE_FOR_REPORTS DATE_RANGE_FOR_REPORTS\r\n" +
		"and calc.ID_DPB_PGM in (51,54,55,56,141) and rtl.CDE_VEH_TYP = 'S'\r\n" + 
		"group by rtl.ID_DPB_UNBLK_VEH,rtl.dte_rtl,rtl.tme_rtl,calc.id_dlr,rtl.NUM_PO,rtl.des_modl,rtl.CDE_VEH_DDR_STS,rtl.NUM_VIN),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select distinct rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL \r\n" + 
		"from dpbuser.DPB_UNBLK_VEH rtl , basic_data cal\r\n" + 
		"where cal.VEH_STS = 'I2' and rtl.NUM_PO = cal.NUM_PO\r\n" + 
		") ,\r\n" + 
		"NUM_PO_ORDER (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE, ROW_NUM) as (\r\n" + 
		"select NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE,\r\n" + 
		"row_number() over(PARTITION BY NUM_PO order by NUM_PO, ID_DPB_UNBLK_VEH) as ROW_NUM\r\n" + 
		"from NUM_PO_DATA\r\n" + 
		")\r\n" + 
		"\r\n" + 
		"select null,null,null,null,null,'Total:               ' as TEXT,sum(Sm_Brd_Stds_3),sum(Dlr_Reseve_3),sum(Unearn_Sm_Brd_Stds_3) from\r\n" + 
		"basic_data \r\n" + 
		"\r\n" + 
		"union all\r\n" + 
		"\r\n" + 
		"(select ROW_NUMBER() OVER (ORDER BY pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl,pd.NUM_VIN ASC) AS ROWNUM,\r\n" + 
		"coalesce(case when pd.VEH_STS = 'I2' then VARCHAR_FORMAT(\r\n" + 
		"(select RTL_DTE from NUM_PO_ORDER npo1 where npo1.ROW_NUM = (\r\n" + 
		"select (npo.ROW_NUM-1) from NUM_PO_ORDER npo where npo.ID_DPB_UNBLK_VEH=pd.ID_DPB_UNBLK_VEH) \r\n" + 
		"and npo1.NUM_PO = pd.NUM_PO),'MM/DD/YY') END, \r\n" + 
		"VARCHAR_FORMAT(DTE_RTL,'MM/DD/YY')) as DTE_RTL,\r\n" + 
		"id_dlr,NUM_PO,NUM_VIN,des_modl,Sm_Brd_Stds_3,Dlr_Reseve_3,Unearn_Sm_Brd_Stds_3 from basic_data pd \r\n" + 
		"order by pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl,pd.NUM_VIN)\r\n" + 
		"with ur";

public static final String UNEARNED_REPORT_VANS_EXPORT_QUERY = "with basic_data(dte_rtl,tme_rtl,id_dlr,NUM_PO,NUM_VIN,des_modl,Cust_Exp_2,Unearn_Cus_Exp_2,CV_Brd_Std_2,Unearn_CV_BStd_2,Sales_Bonus_2,Unearn_Sls_Bns_2,Dlr_Reseve_2,VEH_STS,ID_DPB_UNBLK_VEH) as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"rtl.dte_rtl,\r\n" + 
		"rtl.tme_rtl,\r\n" + 
		"calc.id_dlr,rtl.NUM_PO,rtl.NUM_VIN,rtl.des_modl,\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 57) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Cust_Exp_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 57) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Cus_Exp_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 58) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as CV_Brd_Std_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 58) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_CV_BStd_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 59) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Sales_Bonus_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 59) then \r\n" + 
		"coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"end ),0)) as Unearn_Sls_Bns_2,\r\n" + 
		"\r\n" + 
		"sum(coalesce((case when (calc.id_dpb_pgm = 52) then coalesce(calc.amt_dpb_bns_calc,0) end),0)) as Dlr_Reseve_2,\r\n" + 
		"\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"from dpbuser.DPB_CALC  calc, dpbuser.DPB_UNBLK_VEH  rtl   where\r\n" + 
		"calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"--rtl.dte_rtl between '2015-01-01' and '2016-01-01' \r\n" + 
		"DEALER_RANGE_FOR_REPORTS DATE_RANGE_FOR_REPORTS\r\n" +
		"and calc.ID_DPB_PGM in (57,58,59,52) and rtl.CDE_VEH_TYP = 'V'\r\n" + 
		"group by rtl.ID_DPB_UNBLK_VEH,rtl.dte_rtl,rtl.tme_rtl,calc.id_dlr,rtl.NUM_PO,rtl.des_modl,rtl.CDE_VEH_DDR_STS,rtl.NUM_VIN),\r\n" + 
		"NUM_PO_DATA (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE) as (\r\n" + 
		"select distinct rtl.NUM_PO, rtl.ID_DPB_UNBLK_VEH,rtl.CDE_VEH_DDR_STS, rtl.DTE_RTL \r\n" + 
		"from dpbuser.DPB_UNBLK_VEH rtl , basic_data cal\r\n" + 
		"where cal.VEH_STS = 'I2' and rtl.NUM_PO = cal.NUM_PO\r\n" + 
		") ,\r\n" + 
		"NUM_PO_ORDER (NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE, ROW_NUM) as (\r\n" + 
		"select NUM_PO, ID_DPB_UNBLK_VEH, VEH_STS, RTL_DTE,\r\n" + 
		"row_number() over(PARTITION BY NUM_PO order by NUM_PO, ID_DPB_UNBLK_VEH) as ROW_NUM\r\n" + 
		"from NUM_PO_DATA\r\n" + 
		")\r\n" + 
		"\r\n" + 
		"select null,null,null,null,null,'Total:               ' as TEXT,sum(Cust_Exp_2),sum(Unearn_Cus_Exp_2),sum(CV_Brd_Std_2),sum(Unearn_CV_BStd_2),\r\n" + 
		"sum(Sales_Bonus_2),sum(Unearn_Sls_Bns_2),sum(Dlr_Reseve_2) from\r\n" + 
		"basic_data \r\n" + 
		"\r\n" + 
		"union all\r\n" + 
		"\r\n" + 
		"(select ROW_NUMBER() OVER (ORDER BY pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl,pd.NUM_VIN ASC) AS ROWNUM,\r\n" + 
		"coalesce(case when pd.VEH_STS = 'I2' then VARCHAR_FORMAT(\r\n" + 
		"(select RTL_DTE from NUM_PO_ORDER npo1 where npo1.ROW_NUM = (\r\n" + 
		"select (npo.ROW_NUM-1) from NUM_PO_ORDER npo where npo.ID_DPB_UNBLK_VEH=pd.ID_DPB_UNBLK_VEH) \r\n" + 
		"and npo1.NUM_PO = pd.NUM_PO),'MM/DD/YY') END, \r\n" + 
		"VARCHAR_FORMAT(DTE_RTL,'MM/DD/YY')) as DTE_RTL,\r\n" + 
		"id_dlr,NUM_PO,NUM_VIN,des_modl,Cust_Exp_2,Unearn_Cus_Exp_2,CV_Brd_Std_2,Unearn_CV_BStd_2,Sales_Bonus_2,Unearn_Sls_Bns_2,Dlr_Reseve_2 from basic_data pd \r\n" + 
		"order by pd.DTE_RTL,pd.tme_rtl,pd.ID_DLR,pd.NUM_PO,pd.des_modl,pd.NUM_VIN)\r\n" + 
		"\r\n" + 
		"with ur";

//Dealer Performance Unearned Bonus Report - FNC27 - End
/**
 * Updated on 10/06/2016 for Performance Improvement
 * 1.) Passing chunk of dealers
 * 2.) removed trim and replaced IN clause(with single value) with "="
 * 3.) Removed pagination code.
 * 4.) Removed constants like %,$ etc
 */
public static final String DEALER_COMPLIANCE_QUERY_CAR = "WITH  DPBDLR (IDDLR, REGN, MKT, T55, ADR_DLR_CTY, ADR_DLR_STA) AS (SELECT DISTINCT DLR.ID_DLR, DLR.CDE_RGN, DLR.CDE_MKT, DLR.CDE_MTRO_MKT_GRP,  DLR.ADR_PRIM_CITY, DLR.CDE_DLR_ST FROM \r\n" + 
		"DPBUSER.DEALER_FDRT AS DLR, DPBUSER.DPB_UNBLK_VEH AS RTL  WHERE DLR.ID_DLR = RTL.ID_DLR AND  RTL.ID_DLR IN OLDPAYOUT_DEALER_RANGE_FOR_RPRT),DAYFDRTTBL (DTECAL_MIN, DTECAL_MAX, \r\n" + 
		"NUMRETLQTR, YEARS) AS(SELECT MIN(DTE_CAL), MAX(DTE_CAL), NUM_RETL_QTR, NUM_RETL_YR FROM DPBUSER.DPB_DAY AS FDR WHERE (NUM_RETL_QTR, NUM_RETL_YR) IN (SELECT DIM.NUM_RETL_QTR, \r\n" + 
		"DIM.NUM_RETL_YR FROM DPBUSER.DPB_DAY AS DIM DATE_RANGE_FOR_REPORTS) GROUP BY NUM_RETL_QTR, NUM_RETL_YR)," +
		"DLRPGMCALC (IDDLRCALC, ID_DPB_PGM, SUM_AMT_DPB_BNS_CALC, \r\n" + 
		"SUM_AMT_DPB_UNERND,ID_DPB_UNBLK_VEH, NUMRETLQTR, YEARS, NUM_PO, VEH_TYP) " +
		"AS(SELECT CALC.ID_DLR, CALC.ID_DPB_PGM, CALC.AMT_DPB_BNS_CALC, " +
		"case when rtl.dte_rtl < '2016-04-01' then\r\n" + 
		"case when CALC.ID_DPB_PGM IN (144, 145,47,48,49, 50) then\r\n" + 
		"CALC.AMT_DPB_UNERND\r\n" + 
		"end \r\n" + 
		"else\r\n" + 
		"case when CALC.ID_DPB_PGM IN (144, 145,48,49,50) then\r\n" + 
		"CALC.AMT_DPB_UNERND\r\n" + 
		"end \r\n" + 
		"end" +
		",CALC.ID_DPB_UNBLK_VEH, \r\n" + 
		"DYFDRT.NUMRETLQTR, DYFDRT.YEARS, RTL.NUM_PO,RTL.CDE_VEH_TYP FROM DAYFDRTTBL AS DYFDRT JOIN DPBUSER.DPB_UNBLK_VEH AS RTL ON RTL.DTE_RTL BETWEEN DYFDRT.DTECAL_MIN AND DYFDRT.DTECAL_MAX  \r\n" + 
		"AND RTL.CDE_VEH_TYP = ('P') JOIN DPBUSER.DPB_CALC AS CALC ON CALC.ID_DPB_UNBLK_VEH = RTL.ID_DPB_UNBLK_VEH   AND CALC.ID_LDRSP_BNS_PGM IS NULL AND CALC.ID_DPB_PGM IN (144, 145,48, \r\n" + 
		"49, 50)    AND CALC.ID_DLR IN (SELECT IDDLR FROM DPBDLR)),VEH_DATA (ID_DLR, NUM_PO, ID_DPB_UNBLK_VEH) AS (SELECT IDDLRCALC, NUM_PO, MAX(ID_DPB_UNBLK_VEH) FROM DLRPGMCALC GROUP BY \r\n" + 
		"IDDLRCALC, NUM_PO ),VEHCOUNT (ID_DLR, NUM_PO, ID_DPB_UNBLK_VEH, TOTALAMTMSRPOPTS) AS (SELECT RTL.ID_DLR, RTL.NUM_PO, RTL.ID_DPB_UNBLK_VEH, SUM(RTL.AMT_MSRP_TOT_ACSRY + \r\n" + 
		"RTL.AMT_MSRP_BASE)     FROM VEH_DATA AS VD JOIN DPBUSER.DPB_UNBLK_VEH AS RTL ON RTL.ID_DLR = VD.ID_DLR AND RTL.ID_DPB_UNBLK_VEH = VD.ID_DPB_UNBLK_VEH   AND RTL.NUM_PO = VD.NUM_PO " +
		/*"--AND" + 
		"--RTL.CDE_VEH_DDR_STS <> 'I2'" +*/
		" GROUP BY RTL.ID_DLR, RTL.NUM_PO, RTL.ID_DPB_UNBLK_VEH),DPBCALCPOCOUNT (TOTALAMTMSRPOPTS) AS (SELECT SUM(TOTALAMTMSRPOPTS) FROM VEHCOUNT AS CNT)," +
        "RET_UNITS(ID_DLR,CDE_VEH_DDR_STS) as \r\n" +
        "(SELECT CALC.ID_DLR,RTL.CDE_VEH_DDR_STS \r\n" +
        "FROM DAYFDRTTBL AS DYFDRT JOIN DPBUSER.DPB_UNBLK_VEH AS RTL ON RTL.DTE_RTL BETWEEN DYFDRT.DTECAL_MIN AND DYFDRT.DTECAL_MAX \r\n" +
        "AND RTL.CDE_VEH_TYP = ('P') JOIN DPBUSER.DPB_CALC AS CALC ON CALC.ID_DPB_UNBLK_VEH = RTL.ID_DPB_UNBLK_VEH AND CALC.ID_LDRSP_BNS_PGM IS NULL \r\n" +
        "AND CALC.ID_DPB_PGM IN (46) AND CALC.ID_DLR IN (SELECT IDDLR FROM DPBDLR))\r\n" +
        ",PO_COUNT \r\n" + 
		"(ID_DLR, CY13RETAILSALES) AS   (SELECT ID_DLR,SUM(CASE WHEN (CDE_VEH_DDR_STS = 'I2') THEN -1 ELSE 1 END)FROM RET_UNITS GROUP BY ID_DLR ), DLRPGMCALC_FINAL (IDDLRCALC, ID_DPB_PGM, SUM_AMT_DPB_BNS_CALC, SUM_AMT_DPB_UNERND, \r\n" + 
		"NUMRETLQTR, YEARS, VEH_TYP) AS    (SELECT CALC.IDDLRCALC, CALC.ID_DPB_PGM, SUM(CALC.SUM_AMT_DPB_BNS_CALC), SUM(CALC.SUM_AMT_DPB_UNERND), CALC.NUMRETLQTR, CALC.YEARS, CALC.VEH_TYP      \r\n" + 
		"FROM  VEHCOUNT AS VEH LEFT OUTER JOIN DLRPGMCALC AS CALC ON VEH.ID_DLR = CALC.IDDLRCALC AND VEH.NUM_PO = CALC.NUM_PO      GROUP BY CALC.IDDLRCALC, CALC.ID_DPB_PGM, CALC.NUMRETLQTR, \r\n" + 
		"CALC.YEARS, CALC.VEH_TYP ),  LST_KPI (LST_UPDT, ID_DLR, QTR, YR, ID_KPI) AS (SELECT MAX(ID_KPI_FIL_EXTRT), DLRKPI.ID_DLR, DLRKPI.DTE_FSCL_QTR, DLRKPI.DTE_FSCL_YR, ID_KPI  FROM \r\n" + 
		"DLRPGMCALC_FINAL AS PO LEFT OUTER JOIN DPBUSER.DPB_KPI_FIL_EXTRT AS DLRKPI ON PO.IDDLRCALC = DLRKPI.ID_DLR AND PO.NUMRETLQTR = DLRKPI.DTE_FSCL_QTR         AND PO.YEARS = \r\n" + 
		"DLRKPI.DTE_FSCL_YR     GROUP BY DLRKPI.ID_DLR, DLRKPI.DTE_FSCL_QTR, DLRKPI.DTE_FSCL_YR,ID_KPI), PGM_DATA (IDDLR, DEALER_STANDARDS, CUST_EXP_SALES, CUST_EXP_SERVICE, PRE_OWNED, \r\n" + 
		"SALES_EFFECTIVE, MB_CSI, DEALER_STANDARDS_AMT, CUST_EXP_SALES_AMT, CUST_EXP_SERVICE_AMT, PRE_OWNED_AM, SALES_EFFECTIVE_AMT, MB_CSI_AMT, SUM_AMT_DPB_UNERND_AMT, VEH_TYP, CY13RETAILSALES, \r\n" + 
		"REGN, MKT, T55, ADR_DLR_CTY, ADR_DLR_STA) AS  (SELECT DLR.IDDLR, MAX(CASE WHEN (KPI.ID_KPI = 6000 AND F.ID_DPB_PGM = 47 AND F.VEH_TYP = 'P') THEN KPI.PCT_KPI END) AS \r\n" + 
		"DEALER_STANDARDS,      MAX(CASE WHEN (KPI.ID_KPI = 6200 AND F.ID_DPB_PGM = 144 AND F.VEH_TYP = 'P') THEN KPI.PCT_KPI END) AS CUST_EXP_SALES,     MAX(CASE WHEN (KPI.ID_KPI = 6300 AND \r\n" + 
		"F.ID_DPB_PGM = 145 AND F.VEH_TYP = 'P') THEN KPI.PCT_KPI END) AS CUST_EXP_SERVICE,     MAX(CASE WHEN (KPI.ID_KPI = 8000 AND F.ID_DPB_PGM = 49 AND F.VEH_TYP = 'P') THEN KPI.PCT_KPI END) \r\n" + 
		"AS PRE_OWNED,     MAX(CASE WHEN (KPI.ID_KPI = 7000 AND F.ID_DPB_PGM = 48 AND F.VEH_TYP = 'P') THEN KPI.PCT_KPI END) AS SALES_EFFECTIVE,     MAX(CASE WHEN (KPI.ID_KPI = 5000 AND \r\n" + 
		"F.ID_DPB_PGM = 50 AND F.VEH_TYP = 'P') THEN KPI.PCT_KPI END) AS MB_CSI,    MAX(CASE WHEN (F.ID_DPB_PGM = 47 AND F.VEH_TYP = 'P') THEN F.SUM_AMT_DPB_BNS_CALC END) AS \r\n" + 
		"CUST_EXP_SALES_AMT,   MAX(CASE WHEN (F.ID_DPB_PGM = 144 AND F.VEH_TYP = 'P') THEN F.SUM_AMT_DPB_BNS_CALC END) AS CUST_EXP_SERVICE_AMT,    MAX(CASE WHEN (F.ID_DPB_PGM = 145 AND F.VEH_TYP \r\n" + 
		"= 'P') THEN F.SUM_AMT_DPB_BNS_CALC END) AS DEALER_STANDARDS_AMT,   MAX(CASE WHEN (F.ID_DPB_PGM = 49 AND F.VEH_TYP = 'P') THEN F.SUM_AMT_DPB_BNS_CALC END) AS PRE_OWNED_AMT,    MAX(CASE \r\n" + 
		"WHEN (F.ID_DPB_PGM = 48 AND F.VEH_TYP = 'P') THEN F.SUM_AMT_DPB_BNS_CALC END) AS SALES_EFFECTIVE_AMT,   MAX(CASE WHEN (F.ID_DPB_PGM = 50 AND F.VEH_TYP = 'P') THEN F.SUM_AMT_DPB_BNS_CALC \r\n" + 
		"END) AS MB_CSI_AMT,    (select SUM(SUM_AMT_DPB_UNERND) from DLRPGMCALC_FINAL where IDDLRCALC in(DLR.IDDLR)) AS SUM_AMT_DPB_UNERND_AMT, F.VEH_TYP,   PC.CY13RETAILSALES, COALESCE(DLR.REGN, ''), COALESCE(DLR.MKT, ''),   COALESCE(DLR.T55, ''), \r\n" + 
		"COALESCE(DLR.ADR_DLR_CTY, ''), COALESCE(DLR.ADR_DLR_STA, '')     FROM     DLRPGMCALC_FINAL AS F JOIN LST_KPI AS LST_KPI ON LST_KPI.ID_DLR = F.IDDLRCALC JOIN DPBUSER.DPB_KPI_FIL_EXTRT AS \r\n" + 
		"KPI ON LST_KPI.LST_UPDT = KPI.ID_KPI_FIL_EXTRT JOIN PO_COUNT AS PC ON PC.ID_DLR = F.IDDLRCALC RIGHT OUTER JOIN DPBDLR AS DLR ON F.IDDLRCALC = DLR.IDDLR  GROUP BY DLR.IDDLR, F.VEH_TYP, \r\n" + 
		"PC.CY13RETAILSALES, DLR.REGN,   DLR.MKT, DLR.T55, DLR.ADR_DLR_CTY, DLR.ADR_DLR_STA), TOTALCY13RETAILSALES (TOTAL_CY13RETAILSALES, TOT_DEALER_STANDARDS_AMT, TOT_CUST_EXP_SALES_AMT, \r\n" + 
		"TOT_CUST_EXP_SERVICE_AMT, TOT_PRE_OWNED_AM, TOT_SALES_EFFECTIVE_AMT, TOT_MB_CSI_AMT, TOT_SUM_AMT_DPB_UNERND_AMT) AS (SELECT SUM(CY13RETAILSALES), SUM(DEALER_STANDARDS_AMT), \r\n" + 
		"SUM(CUST_EXP_SALES_AMT),     SUM(CUST_EXP_SERVICE_AMT), SUM(PRE_OWNED_AM), SUM(SALES_EFFECTIVE_AMT),     SUM(MB_CSI_AMT), SUM(SUM_AMT_DPB_UNERND_AMT) FROM PGM_DATA), PGM_DATA2 \r\n" + 
		"(DEALER_STANDARDS_NAME, CUST_EXP_SALES_NAME, CUST_EXP_SERVICE_NAME, PO_NAME, SALES_EFFECTIVE_NAME, BRAND_STAND_NAME, DEALER_STANDARDS, CUST_EXP_SALES, CUST_EXP_SERVICE, PRE_OWNED, \r\n" + 
		"SALES_EFFECTIVE, MB_CSI, NUMRETLQTR, YEARS) AS (SELECT MAX(CASE WHEN CAL.ID_DPB_PGM = (47) THEN NAM_DPB_PGM END) AS DEALER_STANDARDS_NAME,      MAX(CASE WHEN CAL.ID_DPB_PGM = (144) THEN \r\n" + 
		"NAM_DPB_PGM END) AS CUST_EXP_SALES_NAME,     MAX(CASE WHEN CAL.ID_DPB_PGM = (145) THEN NAM_DPB_PGM END) AS CUST_EXP_SERVICE_NAME,      MAX(CASE WHEN CAL.ID_DPB_PGM = (49) THEN \r\n" + 
		"NAM_DPB_PGM END) AS PO_NAME,     MAX(CASE WHEN CAL.ID_DPB_PGM = (48) THEN NAM_DPB_PGM END) AS SALES_EFFECTIVE_NAME,     MAX(CASE WHEN CAL.ID_DPB_PGM = (50) THEN NAM_DPB_PGM END) AS \r\n" + 
		"BRAND_STAND_NAME,     MAX(CASE WHEN (CAL.ID_DPB_PGM = 47 AND CAL.VEH_TYP = 'P') THEN PCT_VAR_PMT END) AS DEALER_STANDARDS,     MAX(CASE WHEN (CAL.ID_DPB_PGM = 144 AND CAL.VEH_TYP = 'P') \r\n" + 
		"THEN PCT_VAR_PMT END) AS CUST_EXP_SALES,     MAX(CASE WHEN (CAL.ID_DPB_PGM = 145 AND CAL.VEH_TYP = 'P') THEN PCT_VAR_PMT END) AS CUST_EXP_SERVICE,     MAX(CASE WHEN (CAL.ID_DPB_PGM = 49 \r\n" + 
		"AND CAL.VEH_TYP = 'P') THEN PCT_VAR_PMT END) AS PRE_OWNED,     MAX(CASE WHEN (CAL.ID_DPB_PGM = 48 AND CAL.VEH_TYP = 'P') THEN PCT_VAR_PMT END) AS SALES_EFFECTIVE,    MAX(CASE WHEN \r\n" + 
		"(CAL.ID_DPB_PGM = 50 AND CAL.VEH_TYP = 'P') THEN PCT_VAR_PMT END) AS MB_CSI,    CAL.NUMRETLQTR, CAL.YEARS  FROM       DLRPGMCALC AS CAL JOIN DPBUSER.DPB_PGM AS PGM ON CAL.ID_DPB_PGM = \r\n" + 
		"PGM.ID_DPB_PGM  GROUP BY CAL.NUMRETLQTR, CAL.YEARS), REPORT_DATA3 (IDDLR, REGN, MKT, T55, ADR_DLR_CTY, ADR_DLR_STA,  DEALER_STANDARDS, CUST_EXP_SALES, CUST_EXP_SERVICE, PRE_OWNED, \r\n" + 
		"SALES_EFFECTIVE, MB_CSI, TOT_CY13RETAILSALES, DEALER_STANDARDS_AMT, CUST_EXP_SALES_AMT, CUST_EXP_SERVICE_AMT, PRE_OWNED_AM, SALES_EFFECTIVE_AMT, MB_CSI_AMT,TOTAL_PAYOUT,TOTAL_PAYOUT_WITH_CUST_EXP,UNERND_AMT, \r\n" + 
		"TOTAL_CY13RETAILSALES, TOT_DEALER_STANDARDS_AMT, TOT_CUST_EXP_SALES_AMT, TOT_CUST_EXP_SERVICE_AMT, TOT_PRE_OWNED_AM, TOT_SALES_EFFECTIVE_AMT, TOT_MB_CSI_AMT, TOT_UNERND_AMT, TOT_EFF, TOT_EFF_WITH_CUST_EXP) \r\n" + 
		"AS   (SELECT PGM.IDDLR, PGM.REGN, PGM.MKT, PGM.T55, PGM.ADR_DLR_CTY,       PGM.ADR_DLR_STA, VARCHAR_FORMAT(COALESCE(PGM.DEALER_STANDARDS, 0), '90.99'),       \r\n" + 
		"VARCHAR_FORMAT(COALESCE(PGM.CUST_EXP_SALES, 0), '90.99'), VARCHAR_FORMAT(COALESCE(PGM.CUST_EXP_SERVICE, 0), '90.99'),       VARCHAR_FORMAT(COALESCE(PGM.PRE_OWNED, 0), '90.99'), \r\n" + 
		"VARCHAR_FORMAT(COALESCE(PGM.SALES_EFFECTIVE, 0), '90.99'),       VARCHAR_FORMAT(COALESCE(PGM.MB_CSI, 0), '90.99'), COALESCE(PGM.CY13RETAILSALES, 0),       \r\n" + 
		"trim(VARCHAR_FORMAT(COALESCE(PGM.DEALER_STANDARDS_AMT, 0), '999,999,990')),      trim(VARCHAR_FORMAT(COALESCE(PGM.CUST_EXP_SALES_AMT, 0), '999,999,990')),      \r\n" + 
		"trim(VARCHAR_FORMAT(COALESCE(PGM.CUST_EXP_SERVICE_AMT, 0), '999,999,990')),      trim(VARCHAR_FORMAT(COALESCE(PGM.PRE_OWNED_AM, 0), '999,999,990')),      \r\n" + 
		"trim(VARCHAR_FORMAT(COALESCE(PGM.SALES_EFFECTIVE_AMT, 0), '999,999,990')),      trim(VARCHAR_FORMAT(COALESCE(PGM.MB_CSI_AMT, 0), '999,999,990')),\r\n" + 
		"trim(VARCHAR_FORMAT(COALESCE(PGM.CUST_EXP_SALES_AMT, 0)+\r\n" + 
		"COALESCE(PGM.CUST_EXP_SERVICE_AMT, 0)+COALESCE(PGM.PRE_OWNED_AM, 0)+COALESCE(PGM.SALES_EFFECTIVE_AMT, 0)\r\n" + 
		"+COALESCE(PGM.MB_CSI_AMT, 0), '999,999,990')),"+ 
		"trim(VARCHAR_FORMAT(COALESCE(PGM.CUST_EXP_SALES_AMT, 0)+COALESCE(PGM.CUST_EXP_SERVICE_AMT, 0)+COALESCE(PGM.PRE_OWNED_AM, 0)+COALESCE(PGM.SALES_EFFECTIVE_AMT, 0)+COALESCE(PGM.MB_CSI_AMT, 0), '999,999,990')),     \r\n" + 
		"trim(VARCHAR_FORMAT(COALESCE(PGM.SUM_AMT_DPB_UNERND_AMT, 0), '999,999,990')) AS UNERND,      COALESCE(TOTALCY13RETAILSALES.TOTAL_CY13RETAILSALES, 0), \r\n" + 
		"(VARCHAR_FORMAT(COALESCE(TOTALCY13RETAILSALES.TOT_DEALER_STANDARDS_AMT, 0),      '999,999,990')), (VARCHAR_FORMAT(COALESCE(TOTALCY13RETAILSALES.TOT_CUST_EXP_SALES_AMT, 0),      \r\n" + 
		"'999,999,990')), (VARCHAR_FORMAT(COALESCE(TOTALCY13RETAILSALES.TOT_CUST_EXP_SERVICE_AMT, 0),      '999,999,990')), (VARCHAR_FORMAT(COALESCE(TOTALCY13RETAILSALES.TOT_PRE_OWNED_AM, \r\n" + 
		"0),      '999,999,990')), (VARCHAR_FORMAT(COALESCE(TOTALCY13RETAILSALES.TOT_SALES_EFFECTIVE_AMT, 0),      '999,999,990')), (VARCHAR_FORMAT(COALESCE(TOTALCY13RETAILSALES.TOT_MB_CSI_AMT, \r\n" + 
		"0),      '999,999,990')), (VARCHAR_FORMAT(COALESCE(TOTALCY13RETAILSALES.TOT_SUM_AMT_DPB_UNERND_AMT, 0),      '999,999,990')), VARCHAR_FORMAT(\r\n" + 
		"COALESCE(PGM.CUST_EXP_SALES, 0) + COALESCE(PGM.CUST_EXP_SERVICE, 0) + COALESCE(PGM.PRE_OWNED, 0) + COALESCE(PGM.SALES_EFFECTIVE, 0) + COALESCE(PGM.MB_CSI, 0),       '90.99'), VARCHAR_FORMAT( COALESCE(PGM.CUST_EXP_SALES, 0) + COALESCE(PGM.CUST_EXP_SERVICE, 0) \r\n"  +
		"+ COALESCE(PGM.PRE_OWNED, 0) + COALESCE(PGM.SALES_EFFECTIVE, 0) + COALESCE(PGM.MB_CSI, 0),       '90.99')    FROM \r\n" + 
		"PGM_DATA AS PGM, TOTALCY13RETAILSALES), FINAL_DATA2 (DEALER_STANDARDS_NAME, CUST_EXP_SALES_NAME, CUST_EXP_SERVICE_NAME,  PO_NAME, SALES_EFFECTIVE_NAME, BRAND_STAND_NAME, \r\n" + 
		"DEALER_STANDARDS,  CUST_EXP_SALES, CUST_EXP_SERVICE, PRE_OWNED, SALES_EFFECTIVE,  MB_CSI, NUMRETLQTR, YEARS) AS   (SELECT trim(COALESCE(DEALER_STANDARDS_NAME, '')), \r\n" + 
		"trim(COALESCE(CUST_EXP_SALES_NAME, '')),      trim(COALESCE(CUST_EXP_SERVICE_NAME, '')), trim(COALESCE(PO_NAME, '')),      trim(COALESCE(SALES_EFFECTIVE_NAME, '')), \r\n" + 
		"trim(COALESCE(BRAND_STAND_NAME, '')),      VARCHAR_FORMAT(COALESCE(DEALER_STANDARDS, 0), '90.99'), VARCHAR_FORMAT(COALESCE(CUST_EXP_SALES, 0), '90.99'),     \r\n" + 
		"VARCHAR_FORMAT(COALESCE(CUST_EXP_SERVICE, 0), '90.99'), VARCHAR_FORMAT(COALESCE(PRE_OWNED, 0), '90.99'),      VARCHAR_FORMAT(COALESCE(SALES_EFFECTIVE, 0), '90.99'), \r\n" + 
		"VARCHAR_FORMAT(COALESCE(MB_CSI, 0), '90.99'),     CASE WHEN NUMRETLQTR = 1 THEN '1st' ELSE CASE WHEN NUMRETLQTR = 2 THEN '2nd' ELSE CASE WHEN NUMRETLQTR = 3 THEN '3rd' ELSE '4th' END \r\n" + 
		"END END AS NUM_RETL_QTR,YEARS   FROM PGM_DATA2), REPORT_DATA (DEALER_STANDARDS, CUST_EXP_SALES, CUST_EXP_SERVICE, PRE_OWNED, SALES_EFFECTIVE, MB_CSI, TOT_CY13RETAILSALES) AS (SELECT \r\n" + 
		"VARCHAR_FORMAT(FLOAT(AVG(COALESCE(DEALER_STANDARDS, 0))), '90.99'),VARCHAR_FORMAT(FLOAT(AVG(COALESCE(CUST_EXP_SALES, 0))), '90.99'),     \r\n" + 
		"VARCHAR_FORMAT(FLOAT(AVG(COALESCE(CUST_EXP_SERVICE, 0))), '90.99'),VARCHAR_FORMAT(FLOAT(AVG(COALESCE(PRE_OWNED, 0))), '90.99'),     VARCHAR_FORMAT(FLOAT(AVG(COALESCE(SALES_EFFECTIVE, \r\n" + 
		"0))), '90.99'), VARCHAR_FORMAT(FLOAT(AVG(COALESCE(MB_CSI, 0))), '90.99'), SUM(COALESCE(CY13RETAILSALES, 0))   FROM PGM_DATA AS PGM),FINAL_DATA (DEALER_STANDARDS, CUST_EXP_SALES, \r\n" + 
		"CUST_EXP_SERVICE,PRE_OWNED, SALES_EFFECTIVE, MB_CSI, TOTAL_MSRP) AS(SELECT DEALER_STANDARDS, CUST_EXP_SALES, CUST_EXP_SERVICE, PRE_OWNED,SALES_EFFECTIVE, MB_CSI, VARCHAR_FORMAT((SELECT \r\n" + 
		"TOTALAMTMSRPOPTS            FROM DPBCALCPOCOUNT),'999,999,999,990') FROM REPORT_DATA),FINAL_RESULT (DEALER_STANDARDS, CUST_EXP_SALES, CUST_EXP_SERVICE,PRE_OWNED, \r\n" + 
		"SALES_EFFECTIVE, MB_CSI, TOTAL_MSRP, DEALER_STANDARDS_NAME, CUST_EXP_SALES_NAME, CUST_EXP_SERVICE_NAME, PO_NAME, SALES_EFFECTIVE_NAME,BRAND_STAND_NAME, DEALER_STANDARDS2, \r\n" + 
		"CUST_EXP_SALES2, CUST_EXP_SERVICE2, PRE_OWNED2, SALES_EFFECTIVE2, MB_CSI2, NUMRETLQTR, YEARS,IDDLR, REGN, MKT, ADR_DLR_CTY, ADR_DLR_STA, TOT_CY13RETAILSALES, DEALER_STANDARDS3, \r\n" + 
		"CUST_EXP_SALES3, CUST_EXP_SERVICE3, PRE_OWNED3,SALES_EFFECTIVE3, MB_CSI3, TOT_EFF,TOT_EFF_WITH_CUST_EXP, DEALER_STANDARDS_AMT, CUST_EXP_SALES_AMT, CUST_EXP_SERVICE_AMT, PRE_OWNED_AM, SALES_EFFECTIVE_AMT, \r\n" + 
		"MB_CSI_AMT,TOTAL_PAYOUT,TOTAL_PAYOUT_WITH_CUST_EXP, UNERND_AMT, TOTAL_CY13RETAILSALES, TOT_DEALER_STANDARDS_AMT, TOT_CUST_EXP_SALES_AMT, TOT_CUST_EXP_SERVICE_AMT, TOT_PRE_OWNED_AM, TOT_SALES_EFFECTIVE_AMT, TOT_MB_CSI_AMT, \r\n" + 
		"TOT_UNERND_AMT) AS  (SELECT concat(FD.DEALER_STANDARDS,'%'), concat(FD.CUST_EXP_SALES,'%'), concat(FD.CUST_EXP_SERVICE,'%'), concat(FD.PRE_OWNED,'%'), concat(FD.SALES_EFFECTIVE,'%'), \r\n" + 
		"concat(FD.MB_CSI,'%'), FD.TOTAL_MSRP,      FD2.DEALER_STANDARDS_NAME, FD2.CUST_EXP_SALES_NAME, FD2.CUST_EXP_SERVICE_NAME,FD2.PO_NAME, FD2.SALES_EFFECTIVE_NAME, FD2.BRAND_STAND_NAME,     \r\n" + 
		"FD2.DEALER_STANDARDS AS DEALER_STANDARDS2, FD2.CUST_EXP_SALES AS CUST_EXP_SALES2, FD2.CUST_EXP_SERVICE AS CUST_EXP_SERVICE2, FD2.PRE_OWNED AS PRE_OWNED2,     FD2.SALES_EFFECTIVE AS \r\n" + 
		"SALES_EFFECTIVE2, FD2.MB_CSI AS MB_CSI2, FD2.NUMRETLQTR, FD2.YEARS, RD3.IDDLR, RD3.REGN, RD3.MKT, RD3.ADR_DLR_CTY,     RD3.ADR_DLR_STA, RD3.TOT_CY13RETAILSALES, \r\n" + 
		"concat(RD3.DEALER_STANDARDS,'%') AS DEALER_STANDARDS3, concat(RD3.CUST_EXP_SALES,'%') AS CUST_EXP_SALES3, concat(RD3.CUST_EXP_SERVICE,'%') AS CUST_EXP_SERVICE3,     \r\n" + 
		"concat(RD3.PRE_OWNED,'%') AS PRE_OWNED3, concat(RD3.SALES_EFFECTIVE,'%') AS SALES_EFFECTIVE3, concat(RD3.MB_CSI,'%') AS MB_CSI3,  concat(RD3.TOT_EFF,'%'),concat(RD3.TOT_EFF_WITH_CUST_EXP, '%'),  \r\n" + 
		"concat('$',RD3.DEALER_STANDARDS_AMT), concat('$',RD3.CUST_EXP_SALES_AMT),  concat('$',RD3.CUST_EXP_SERVICE_AMT),  concat('$',RD3.PRE_OWNED_AM),  \r\n" + 
		"concat('$',RD3.SALES_EFFECTIVE_AMT),  concat('$',RD3.MB_CSI_AMT),concat('$',RD3.TOTAL_PAYOUT),concat('$',RD3.TOTAL_PAYOUT_WITH_CUST_EXP), concat('$',RD3.UNERND_AMT), RD3.TOTAL_CY13RETAILSALES,     RD3.TOT_DEALER_STANDARDS_AMT, RD3.TOT_CUST_EXP_SALES_AMT, \r\n" + 
		"RD3.TOT_CUST_EXP_SERVICE_AMT,     RD3.TOT_PRE_OWNED_AM, RD3.TOT_SALES_EFFECTIVE_AMT, RD3.TOT_MB_CSI_AMT,     RD3.TOT_UNERND_AMT    FROM FINAL_DATA AS FD, FINAL_DATA2 AS FD2, \r\n" + 
		"REPORT_DATA3 AS RD3) SELECT *  FROM FINAL_RESULT  WITH UR";

/**
 * Updated on 10/06/2016 for Performance Improvement
 * 1.) Passing chunk of dealers
 * 2.) removed trim and replaced IN clause(with single value) with "="
 * 3.) Removed pagination code.
 * 4.) Removed constants like %,$ etc
 */
public static final String DEALER_COMPLIANCE_QUERY_FTL = "with DPBDLR(IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) \r\n" + 
		"AS (select DISTINCT dlr.ID_DLR,dlr.CDE_RGN,dlr.CDE_MKT,dlr.CDE_MTRO_MKT_GRP,dlr.ADR_PRIM_CITY,dlr.CDE_DLR_ST\r\n" + 
		"from DEALER_FDRT dlr , DPBUSER.DPB_UNBLK_VEH AS RTL  WHERE DLR.ID_DLR = RTL.ID_DLR AND  \r\n" + 
		"RTL.ID_DLR IN OLDPAYOUT_DEALER_RANGE_FOR_RPRT),\r\n" + 
		"DAYFDRTTBL(DTECAL_MIN,DTECAL_MAX,NUMRETLQTR,YEARS ) as \r\n" + 
		"(select min(fdr.DTE_CAL),max(fdr.DTE_CAL),fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR \r\n" + 
		"from DPB_DAY fdr \r\n" + 
		"where (fdr.NUM_RETL_QTR,fdr.NUM_RETL_YR) in (select NUM_RETL_QTR,dim.NUM_RETL_YR from DPB_DAY dim DATE_RANGE_FOR_REPORTS)\r\n" + 
		"group by fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR),\r\n" + 
		"DLRPGMCALC(IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND,ID_DPB_UNBLK_VEH,NUMRETLQTR,YEARS,NUM_PO,VEH_TYP) as\r\n" + 
		"(select calc.ID_DLR,calc.ID_DPB_PGM,calc.AMT_DPB_BNS_CALC,calc.AMT_DPB_UNERND,calc.ID_DPB_UNBLK_VEH,dyfdrt.NUMRETLQTR,\r\n" + 
		"dyfdrt.YEARS,RTL.NUM_PO,rtl.CDE_VEH_TYP from DAYFDRTTBL as dyfdrt\r\n" + 
		"join DPB_UNBLK_VEH as rtl on rtl.DTE_RTL between dyfdrt.DTECAL_MIN and dyfdrt.DTECAL_MAX \r\n" + 
		"AND RTL.CDE_VEH_TYP = ('F') join DPB_CALC calc on calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"AND calc.ID_LDRSP_BNS_PGM IS NULL AND calc.ID_DPB_PGM IN (60,62,61)\r\n" +
		"AND CALC.ID_DLR IN (SELECT IDDLR FROM DPBDLR)),\r\n"+
		"veh_data (ID_DLR, NUM_PO, ID_DPB_UNBLK_VEH) as (\r\n" + 
		"select IDDLRCALC, NUM_PO, max(ID_DPB_UNBLK_VEH)\r\n" + 
		"from DLRPGMCALC group by IDDLRCALC, NUM_PO),\r\n" + 
		"VEHCOUNT(ID_DLR,NUM_PO,ID_DPB_UNBLK_VEH,TOTALAMTMSRPOPTS) as\r\n" + 
		"(select rtl.ID_DLR, rtl.num_po,rtl.ID_DPB_UNBLK_VEH, sum(rtl.AMT_MSRP_TOT_ACSRY+rtl.AMT_MSRP_BASE) from veh_data vd\r\n" + 
		"join DPB_UNBLK_VEH rtl\r\n" + 
		"on rtl.ID_DLR = vd.ID_DLR and rtl.ID_DPB_UNBLK_VEH = vd.ID_DPB_UNBLK_VEH\r\n" + 
		"and rtl.num_po = vd.NUM_PO \r\n" + 
		"group by rtl.ID_DLR,rtl.num_po,rtl.ID_DPB_UNBLK_VEH),\r\n" + 
		"DPBCALCPOCOUNT(TOTALAMTMSRPOPTS) as \r\n" + 
		"(select sum(TOTALAMTMSRPOPTS) from VEHCOUNT cnt),\r\n" +
        "RET_UNITS(ID_DLR,CDE_VEH_DDR_STS) as (SELECT CALC.ID_DLR,RTL.CDE_VEH_DDR_STS \r\n" +
        "FROM DAYFDRTTBL AS DYFDRT JOIN DPBUSER.DPB_UNBLK_VEH AS RTL ON RTL.DTE_RTL BETWEEN DYFDRT.DTECAL_MIN AND DYFDRT.DTECAL_MAX\r\n" +
        "AND RTL.CDE_VEH_TYP = ('F') JOIN DPBUSER.DPB_CALC AS CALC ON CALC.ID_DPB_UNBLK_VEH = RTL.ID_DPB_UNBLK_VEH AND CALC.ID_LDRSP_BNS_PGM IS NULL\r\n" +
        "AND CALC.ID_DPB_PGM IN (53) AND CALC.ID_DLR IN (SELECT IDDLR FROM DPBDLR)), \r\n" +
        "PO_COUNT(ID_DLR,CY13RetailSales) as (\r\n" + 
		"select ID_DLR, sum(CASE WHEN (CDE_VEH_DDR_STS = 'I2') THEN -1 ELSE 1 END) from RET_UNITS group by ID_DLR),\r\n" + 
		"DLRPGMCALC_FINAL (IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, NUMRETLQTR,YEARS,VEH_TYP) as (\r\n" + 
		"select calc.IDDLRCALC,calc.ID_DPB_PGM,sum(calc.SUM_AMT_DPB_BNS_CALC),sum(calc.SUM_AMT_DPB_UNERND),calc.NUMRETLQTR,\r\n" + 
		"calc.YEARS,VEH_TYP from VEHCOUNT veh\r\n" + 
		"left outer join DLRPGMCALC calc on veh.ID_DLR = calc.IDDLRCALC\r\n" + 
		"and veh.NUM_PO = calc.NUM_PO\r\n" + 
		"group by calc.IDDLRCALC,calc.ID_DPB_PGM,calc.NUMRETLQTR,calc.YEARS,VEH_TYP),\r\n" + 
		"LST_KPI (LST_UPDT,ID_DLR,QTR,YR,ID_KPI ) AS (SELECT MAX(ID_KPI_FIL_EXTRT), dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI \r\n" + 
		"from DLRPGMCALC_FINAL po\r\n" + 
		"left outer join DPB_KPI_FIL_EXTRT dlrkpi on \r\n" + 
		"po.IDDLRCALC = dlrkpi.ID_DLR and po.NUMRETLQTR = dlrkpi.DTE_FSCL_QTR\r\n" + 
		"AND PO.YEARS = DLRKPI.DTE_FSCL_YR\r\n" + 
		"group by dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI),\r\n" + 
		"PGM_DATA (IDDLR,Dealer_Standards,Sales_Effective,MB_CSI,Dealer_Standards_AMT,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,SUM_AMT_DPB_UNERND_AMT,VEH_TYP,CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) as\r\n" + 
		"(select dlr.IDDLR,\r\n" + 
		"max(case when (kpi.ID_KPI =14000 and ID_DPB_PGM = 60 and VEH_TYP = 'F') then kpi.PCT_KPI end) as Dealer_Standards, \r\n" + 
		"MAX(case when (kpi.ID_KPI =15000 and ID_DPB_PGM = 62 and VEH_TYP = 'F') then kpi.PCT_KPI  end) as Sales_Effective,\r\n" + 
		"MAX(case when (kpi.ID_KPI =13000 and ID_DPB_PGM = 61 and VEH_TYP = 'F') then kpi.PCT_KPI end) as MB_CSI,\r\n" + 
		"Max(case when ( ID_DPB_PGM  = 60 and VEH_TYP = 'F') then SUM_AMT_DPB_BNS_CALC end) as Dealer_Standards_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 62 and VEH_TYP = 'F') then SUM_AMT_DPB_BNS_CALC end) as Sales_Effective_AMT,\r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 61 and VEH_TYP = 'F') then SUM_AMT_DPB_BNS_CALC end) as MB_CSI_AMT,\r\n" + 
		"(SELECT SUM(DLRPGMCALC_FINAL.SUM_AMT_DPB_UNERND) FROM DLRPGMCALC_FINAL WHERE DLRPGMCALC_FINAL.IDDLRCALC IN (DLR.IDDLR)) AS SUM_AMT_DPB_UNERND_AMT,\r\n" +
		"VEH_TYP,pc.CY13RetailSales,\r\n" + 
		"coalesce(REGN,''),coalesce(MKT,''),coalesce(T55,''),coalesce(ADR_DLR_CTY,''),coalesce(ADR_DLR_STA,'')\r\n" + 
		"from DLRPGMCALC_FINAL  f \r\n" + 
		"join LST_KPI lst_kpi on lst_kpi.ID_DLR = IDDLRCALC\r\n" + 
		"join DPB_KPI_FIL_EXTRT kpi on lst_kpi.LST_UPDT = kpi.ID_KPI_FIL_EXTRT\r\n" + 
		"join PO_COUNT pc on pc.ID_DLR = f.IDDLRCALC\r\n" + 
		"right outer join DPBDLR dlr on f.IDDLRCALC = dlr.IDDLR\r\n" + 
		"group by dlr.IDDLR,VEH_TYP,pc.CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA),\r\n" + 
		"TOTALCY13RetailSales(TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_SUM_AMT_DPB_UNERND_AMT) AS \r\n" + 
		"(SELECT SUM(CY13RetailSales),SUM(Dealer_Standards_AMT),SUM(Sales_Effective_AMT),\r\n" + 
		"SUM(MB_CSI_AMT),SUM(SUM_AMT_DPB_UNERND_AMT) FROM PGM_DATA),\r\n" + 
		"PGM_DATA2 (Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as (select\r\n" + 
		"max(case when cal.ID_DPB_PGM = (60)  then NAM_DPB_PGM end) as Dealer_Standards_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM = (62)  then NAM_DPB_PGM end) as Sales_Effective_name,\r\n" + 
		"max(case when cal.ID_DPB_PGM = (61)  then NAM_DPB_PGM end) as Brand_stand_name,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 60 and VEH_TYP = 'F') then PCT_VAR_PMT end) as Dealer_Standards,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 62 and VEH_TYP = 'F') then PCT_VAR_PMT end) as Sales_Effective,\r\n" + 
		"max(case when (cal.ID_DPB_PGM = 61 and VEH_TYP = 'F') then PCT_VAR_PMT end) as MB_CSI,\r\n" + 
		"NUMRETLQTR,cal.YEARS from DLRPGMCALC  cal join DPB_PGM pgm on cal.ID_DPB_PGM = pgm.ID_DPB_PGM group by NUMRETLQTR,YEARS),\r\n" + 
		"REPORT_DATA3 (IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"TOT_CY13RetailSales,Dealer_Standards_AMT,Sales_Effective_AMT,\r\n" + 
		"MB_CSI_AMT,TOTAL_PAYOUT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_UNERND_AMT,TOT_EFF) as (\r\n" + 
		"select IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"coalesce(CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Dealer_Standards_AMT,0),'999,999,990')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Sales_Effective_AMT,0),'999,999,990')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_AMT,0),'999,999,990')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(Dealer_Standards_AMT,0)+coalesce(Sales_Effective_AMT,0)+coalesce(MB_CSI_AMT,0),'999,999,990')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(SUM_AMT_DPB_UNERND_AMT,0),'999,999,990')) ,\r\n" + 
		"coalesce(TOTAL_CY13RetailSales,0),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Dealer_Standards_AMT,0),'999,999,990')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Sales_Effective_AMT,0),'999,999,990')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_MB_CSI_AMT,0),'999,999,990')) ,\r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_SUM_AMT_DPB_UNERND_AMT,0),'999,999,990')) ,\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0)+coalesce(Sales_Effective,0)+coalesce(MB_CSI,0),'90.99') || '%'\r\n" + 
		"from PGM_DATA pgm, TOTALCY13RetailSales),\r\n" + 
		"FINAL_DATA2(Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as\r\n" + 
		"(select trim(coalesce(Dealer_Standards_name,'')),\r\n" + 
		"trim(coalesce(Sales_Effective_name,'')),trim(coalesce(Brand_stand_name,'')),\r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%',\r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%',\r\n" + 
		"case when NUMRETLQTR=1 then '1st' else case when NUMRETLQTR=2 then '2nd' \r\n" + 
		"else case when NUMRETLQTR=3 then '3rd' else '4th' end end end as NUM_RETL_QTR,YEARS from PGM_DATA2),  \r\n" + 
		"REPORT_DATA (Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"TOT_CY13RetailSales) as (select\r\n" + 
		"varchar_format(Float(avg(coalesce(Dealer_Standards,0))),'90.99')|| '%',\r\n" + 
		"varchar_format(Float(avg(coalesce(Sales_Effective,0))),'90.99')|| '%',\r\n" + 
		"varchar_format(Float(avg(coalesce(MB_CSI,0))),'90.99')|| '%',\r\n" + 
		"sum(coalesce(CY13RetailSales,0))\r\n" + 
		"from PGM_DATA pgm),\r\n" + 
		"FINAL_DATA (Dealer_Standards,Sales_Effective,MB_CSI,TOTAL_MSRP) as\r\n" + 
		"(select Dealer_Standards,Sales_Effective,MB_CSI,\r\n" + 
		"trim(varchar_format((select TOTALAMTMSRPOPTS from DPBCALCPOCOUNT),'999,999,999,990'))\r\n" + 
		"from REPORT_DATA),\r\n" + 
		"Final_Result(Dealer_Standards,Sales_Effective,MB_CSI,TOTAL_MSRP,Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards2, Sales_Effective2,MB_CSI2,NUMRETLQTR,YEARS,IDDLR,REGN,MKT,ADR_DLR_CTY,ADR_DLR_STA,TOT_CY13RetailSales,Dealer_Standards3,\r\n" + 
		"Sales_Effective3,MB_CSI3,TOT_EFF,Dealer_Standards_AMT,Sales_Effective_AMT,MB_CSI_AMT,TOTAL_PAYOUT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_UNERND_AMT) as \r\n" + 
		"(select FD.Dealer_Standards,FD.Sales_Effective,FD.MB_CSI,FD.TOTAL_MSRP,\r\n" + 
		"FD2.Dealer_Standards_name,FD2.Sales_Effective_name,FD2.Brand_stand_name,\r\n" + 
		"FD2.Dealer_Standards as Dealer_Standards2,FD2.Sales_Effective as Sales_Effective2,FD2.MB_CSI as MB_CSI2,FD2.NUMRETLQTR,FD2.YEARS,RD3.IDDLR,RD3.REGN,RD3.MKT,RD3.ADR_DLR_CTY,RD3.ADR_DLR_STA,RD3.TOT_CY13RetailSales,\r\n" + 
		"RD3.Dealer_Standards as Dealer_Standards3,RD3.Sales_Effective as Sales_Effective3,RD3.MB_CSI as MB_CSI3,TOT_EFF,\r\n" + 
		"RD3.Dealer_Standards_AMT,RD3.Sales_Effective_AMT,\r\n" + 
		"RD3.MB_CSI_AMT,\r\n" + 
		"RD3.TOTAL_PAYOUT,\r\n" + 
		"RD3.UNERND_AMT,RD3.TOTAL_CY13RetailSales,RD3.TOT_Dealer_Standards_AMT,\r\n" + 
		"RD3.TOT_Sales_Effective_AMT,RD3.TOT_MB_CSI_AMT,RD3.TOT_UNERND_AMT from FINAL_DATA FD,FINAL_DATA2 FD2,REPORT_DATA3 RD3) SELECT *  FROM FINAL_RESULT  WITH UR";


/**
 * Updated on 10/06/2016 for Performance Improvement
 * 1.) Passing chunk of dealers
 * 2.) removed trim and replaced IN clause(with single value) with "="
 * 3.) Removed pagination code.
 * 4.) Removed constants like %,$ etc
 */
public static final String DEALER_COMPLIANCE_QUERY_VAN = "with DPBDLR(IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA)  \r\n" + 
		"AS (select DISTINCT dlr.ID_DLR,dlr.CDE_RGN,dlr.CDE_MKT,dlr.CDE_MTRO_MKT_GRP,dlr.ADR_PRIM_CITY,dlr.CDE_DLR_ST \r\n" + 
		"from DEALER_FDRT dlr , DPBUSER.DPB_UNBLK_VEH AS RTL  WHERE DLR.ID_DLR = RTL.ID_DLR AND  \r\n" + 
		"RTL.ID_DLR IN OLDPAYOUT_DEALER_RANGE_FOR_RPRT  \r\n" + 
		"), \r\n" + 
		"DAYFDRTTBL(DTECAL_MIN,DTECAL_MAX,NUMRETLQTR,YEARS ) as ( \r\n" + 
		"select min(fdr.DTE_CAL),max(fdr.DTE_CAL),fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR  \r\n" + 
		"from DPB_DAY fdr  \r\n" + 
		"where (fdr.NUM_RETL_QTR,fdr.NUM_RETL_YR) in (select NUM_RETL_QTR,dim.NUM_RETL_YR from DPB_DAY dim \r\n" + 
		"     DATE_RANGE_FOR_REPORTS   ) \r\n" + 
		"group by fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR), \r\n" + 
		" \r\n" + 
		"DLRPGMCALC(IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND,ID_DPB_UNBLK_VEH,NUMRETLQTR,YEARS,NUM_PO,VEH_TYP) as ( \r\n" + 
		"select calc.ID_DLR,calc.ID_DPB_PGM,calc.AMT_DPB_BNS_CALC,calc.AMT_DPB_UNERND,  \r\n" + 
		"calc.ID_DPB_UNBLK_VEH,dyfdrt.NUMRETLQTR,dyfdrt.YEARS, RTL.NUM_PO,rtl.CDE_VEH_TYP \r\n" + 
		"from DAYFDRTTBL dyfdrt \r\n" + 
		"join DPB_UNBLK_VEH rtl on rtl.DTE_RTL between dyfdrt.DTECAL_MIN and dyfdrt.DTECAL_MAX  \r\n" + 
		"     VEHICLE_TYPE_RANGE_FOR_REPORTS \r\n" + 
		"join DPB_CALC calc on calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"    AND calc.ID_LDRSP_BNS_PGM IS NULL  \r\n" + 
		"    --AND calc.ID_DPB_PGM IN (47,48,49,50,54,55,56,57,58,59,60,61,62) \r\n" + 
		"    AND calc.ID_DPB_PGM IN (57,59,58) \r\n" + 
		" AND CALC.ID_DLR IN (SELECT IDDLR FROM DPBDLR) \r\n" + 
		"), \r\n" + 
		"veh_data (ID_DLR, NUM_PO, ID_DPB_UNBLK_VEH) as ( \r\n" + 
		"select IDDLRCALC, NUM_PO, max(ID_DPB_UNBLK_VEH) \r\n" + 
		"from DLRPGMCALC \r\n" + 
		"group by IDDLRCALC, NUM_PO \r\n" + 
		"), \r\n" + 
		"VEHCOUNT(ID_DLR,NUM_PO,ID_DPB_UNBLK_VEH,TOTALAMTMSRPOPTS) as ( \r\n" + 
		"select rtl.ID_DLR, rtl.num_po,rtl.ID_DPB_UNBLK_VEH, sum(rtl.AMT_MSRP_TOT_ACSRY+rtl.AMT_MSRP_BASE)  from veh_data vd \r\n" + 
		"join DPB_UNBLK_VEH rtl \r\n" + 
		"on rtl.ID_DLR = vd.ID_DLR and rtl.ID_DPB_UNBLK_VEH = vd.ID_DPB_UNBLK_VEH \r\n" + 
		"and rtl.num_po = vd.NUM_PO   \r\n" + 
		"group by rtl.ID_DLR,rtl.num_po,rtl.ID_DPB_UNBLK_VEH), \r\n" + 
		"DPBCALCPOCOUNT(TOTALAMTMSRPOPTS) as  \r\n" + 
		"(select sum(TOTALAMTMSRPOPTS) from VEHCOUNT cnt), \r\n" + 
		"RET_UNITS (ID_DLR, CDE_VEH_DDR_STS) AS \r\n" + 
		"(SELECT CALC.ID_DLR, RTL.CDE_VEH_DDR_STS\r\n" + 
		"FROM DAYFDRTTBL AS DYFDRT JOIN DPBUSER.DPB_UNBLK_VEH AS RTL ON RTL.DTE_RTL BETWEEN DYFDRT.DTECAL_MIN AND DYFDRT.DTECAL_MAX\r\n" + 
		"AND RTL.CDE_VEH_TYP = ('V') JOIN DPBUSER.DPB_CALC AS CALC ON CALC.ID_DPB_UNBLK_VEH = RTL.ID_DPB_UNBLK_VEH AND CALC.ID_LDRSP_BNS_PGM IS NULL\r\n" + 
		"AND CALC.ID_DPB_PGM IN (52) AND CALC.ID_DLR IN (SELECT IDDLR FROM DPBDLR)),\r\n" + 

		"PO_COUNT(ID_DLR,CY13RetailSales) as ( \r\n" + 
		"select ID_DLR, sum(CASE WHEN (CDE_VEH_DDR_STS = 'I2') THEN -1 ELSE 1 END) from RET_UNITS group by ID_DLR \r\n" + 
		"), \r\n" + 
		"DLRPGMCALC_FINAL (IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, NUMRETLQTR,YEARS,VEH_TYP) as ( \r\n" + 
		"select calc.IDDLRCALC,calc.ID_DPB_PGM,sum(calc.SUM_AMT_DPB_BNS_CALC),sum(calc.SUM_AMT_DPB_UNERND), calc.NUMRETLQTR, \r\n" + 
		"calc.YEARS,VEH_TYP \r\n" + 
		"from VEHCOUNT veh \r\n" + 
		"left outer join DLRPGMCALC calc on veh.ID_DLR = calc.IDDLRCALC \r\n" + 
		"and veh.NUM_PO = calc.NUM_PO \r\n" + 
		"group by calc.IDDLRCALC,calc.ID_DPB_PGM,calc.NUMRETLQTR,calc.YEARS,VEH_TYP \r\n" + 
		"), \r\n" + 
		"LST_KPI (LST_UPDT,ID_DLR,QTR,YR,ID_KPI ) AS ( \r\n" + 
		"SELECT MAX(ID_KPI_FIL_EXTRT), dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI  \r\n" + 
		"from DLRPGMCALC_FINAL po \r\n" + 
		" \r\n" + 
		"left outer join DPB_KPI_FIL_EXTRT dlrkpi on  \r\n" + 
		"po.IDDLRCALC = dlrkpi.ID_DLR and po.NUMRETLQTR = dlrkpi.DTE_FSCL_QTR \r\n" + 
		"AND PO.YEARS = DLRKPI.DTE_FSCL_YR \r\n" + 
		"group by dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI \r\n" + 
		"), \r\n" + 
		" \r\n" + 
		"PGM_DATA (IDDLR,Dealer_Standards,Sales_Effective,MB_CSI,Dealer_Standards_AMT,Sales_Effective_AMT, \r\n" + 
		"MB_CSI_AMT,SUM_AMT_DPB_UNERND_AMT,VEH_TYP,CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) as ( \r\n" + 
		"select \r\n" + 
		"dlr.IDDLR, \r\n" + 
		"max(case when (kpi.ID_KPI =14000  and ID_DPB_PGM = 57 and VEH_TYP = 'V') then  \r\n" + 
		"kpi.PCT_KPI end) as Dealer_Standards,  \r\n" + 
		"MAX(case when (kpi.ID_KPI =15000 and ID_DPB_PGM = 59 and VEH_TYP = 'V') then  \r\n" + 
		"                      kpi.PCT_KPI  end) as Sales_Effective, \r\n" + 
		"MAX(case when (kpi.ID_KPI =13000 and ID_DPB_PGM = 58 and VEH_TYP = 'V') then  \r\n" + 
		"                      kpi.PCT_KPI end) as MB_CSI, \r\n" + 
		"Max(case when (ID_DPB_PGM  = 57 and VEH_TYP = 'V') then  \r\n" + 
		"SUM_AMT_DPB_BNS_CALC end) as Dealer_Standards_AMT, \r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 59 and VEH_TYP = 'V') then SUM_AMT_DPB_BNS_CALC end) as Sales_Effective_AMT, \r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 58 and VEH_TYP = 'V') then SUM_AMT_DPB_BNS_CALC end) as MB_CSI_AMT, \r\n" + 
		"(SELECT SUM(DLRPGMCALC_FINAL.SUM_AMT_DPB_UNERND)FROM DLRPGMCALC_FINAL WHERE DLRPGMCALC_FINAL.IDDLRCALC IN (DLR.IDDLR)) AS SUM_AMT_DPB_UNERND_AMT ,\r\n" + 
		"VEH_TYP,pc.CY13RetailSales, \r\n" + 
		"coalesce(REGN,''),coalesce(MKT,''),coalesce(T55,''),coalesce(ADR_DLR_CTY,''),coalesce(ADR_DLR_STA,'') \r\n" + 
		"from DLRPGMCALC_FINAL  f  \r\n" + 
		"join LST_KPI lst_kpi on lst_kpi.ID_DLR = IDDLRCALC \r\n" + 
		"join DPB_KPI_FIL_EXTRT kpi on lst_kpi.LST_UPDT = kpi.ID_KPI_FIL_EXTRT \r\n" + 
		"join PO_COUNT pc on pc.ID_DLR = f.IDDLRCALC \r\n" + 
		"right outer join DPBDLR dlr on f.IDDLRCALC = dlr.IDDLR \r\n" + 
		"group by  \r\n" + 
		"dlr.IDDLR \r\n" + 
		",VEH_TYP,pc.CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA), \r\n" + 
		" \r\n" + 
		"TOTALCY13RetailSales(TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT, \r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_SUM_AMT_DPB_UNERND_AMT) AS  \r\n" + 
		"(SELECT SUM(CY13RetailSales),SUM(Dealer_Standards_AMT),SUM(Sales_Effective_AMT), \r\n" + 
		"SUM(MB_CSI_AMT),SUM(SUM_AMT_DPB_UNERND_AMT)  \r\n" + 
		"FROM PGM_DATA), \r\n" + 
		" \r\n" + 
		" \r\n" + 
		"PGM_DATA2 (Dealer_Standards_name,Sales_Effective_name,Brand_stand_name, \r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as ( \r\n" + 
		"select \r\n" + 
		"max(case when cal.ID_DPB_PGM = (57)  then NAM_DPB_PGM end) as Dealer_Standards_name, \r\n" + 
		"max(case when cal.ID_DPB_PGM = (59)  then NAM_DPB_PGM end) as Sales_Effective_name, \r\n" + 
		"max(case when cal.ID_DPB_PGM = (58)  then NAM_DPB_PGM end) as Brand_stand_name, \r\n" + 
		"max(case when (cal.ID_DPB_PGM = 57 and VEH_TYP = 'V') then PCT_VAR_PMT end) as Dealer_Standards, \r\n" + 
		"max(case when (cal.ID_DPB_PGM = 59 and VEH_TYP = 'V') then PCT_VAR_PMT end) as Sales_Effective, \r\n" + 
		"max(case when (cal.ID_DPB_PGM = 58 and VEH_TYP = 'V') then PCT_VAR_PMT end) as MB_CSI, \r\n" + 
		"NUMRETLQTR,cal.YEARS \r\n" + 
		"from DLRPGMCALC  cal  \r\n" + 
		"join DPB_PGM pgm on cal.ID_DPB_PGM = pgm.ID_DPB_PGM \r\n" + 
		"group by NUMRETLQTR,YEARS \r\n" + 
		"), \r\n" + 
		"REPORT_DATA3 (IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA, \r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI, \r\n" + 
		"TOT_CY13RetailSales,Dealer_Standards_AMT,Sales_Effective_AMT, \r\n" + 
		"MB_CSI_AMT,TOTAL_PAYOUT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT, \r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_UNERND_AMT,TOT_EFF) as ( \r\n" + 
		"select IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA, \r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%', \r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%', \r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%', \r\n" + 
		"coalesce(CY13RetailSales,0), \r\n" + 
		"'$' || trim(varchar_format(coalesce(Dealer_Standards_AMT,0),'999,999,990')), \r\n" + 
		"'$' || trim(varchar_format(coalesce(Sales_Effective_AMT,0),'999,999,990')), \r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_AMT,0),'999,999,990')), \r\n" + 
		"'$' || trim(varchar_format(coalesce(Dealer_Standards_AMT,0)+coalesce(Sales_Effective_AMT,0)+coalesce(MB_CSI_AMT,0),'999,999,990')), \r\n" + 
		"'$' || trim(varchar_format(coalesce(SUM_AMT_DPB_UNERND_AMT,0),'999,999,990')), \r\n" + 
		"coalesce(TOTAL_CY13RetailSales,0), \r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Dealer_Standards_AMT,0),'999,999,990')), \r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Sales_Effective_AMT,0),'999,999,990')), \r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_MB_CSI_AMT,0),'999,999,990')) , \r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_SUM_AMT_DPB_UNERND_AMT,0),'999,999,990')) , \r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0)+coalesce(Sales_Effective,0)+coalesce(MB_CSI,0),'90.99') || '%' \r\n" + 
		"from PGM_DATA pgm, \r\n" + 
		"TOTALCY13RetailSales \r\n" + 
		"), \r\n" + 
		" \r\n" + 
		" \r\n" + 
		"FINAL_DATA2(Dealer_Standards_name,Sales_Effective_name,Brand_stand_name, \r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,NUMRETLQTR,YEARS) as ( \r\n" + 
		"select trim(coalesce(Dealer_Standards_name,'')), \r\n" + 
		"trim(coalesce(Sales_Effective_name,'')), trim(coalesce(Brand_stand_name,'')), \r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%', \r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%', \r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%', \r\n" + 
		"case when NUMRETLQTR=1 then '1st' else case when NUMRETLQTR=2 then '2nd'  \r\n" + 
		"else case when NUMRETLQTR=3 then '3rd' else '4th' end end end as NUM_RETL_QTR,YEARS \r\n" + 
		"from PGM_DATA2 \r\n" + 
		"),   \r\n" + 
		"REPORT_DATA (Dealer_Standards,Sales_Effective,MB_CSI, \r\n" + 
		"TOT_CY13RetailSales) as ( \r\n" + 
		"select \r\n" + 
		"varchar_format(Float(avg(coalesce(Dealer_Standards,0))),'90.99')|| '%', \r\n" + 
		"varchar_format(Float(avg(coalesce(Sales_Effective,0))),'90.99')|| '%', \r\n" + 
		"varchar_format(Float(avg(coalesce(MB_CSI,0))),'90.99')|| '%', \r\n" + 
		"sum(coalesce(CY13RetailSales,0)) \r\n" + 
		"from PGM_DATA pgm \r\n" + 
		"), \r\n" + 
		"FINAL_DATA (Dealer_Standards,Sales_Effective,MB_CSI,TOTAL_MSRP) as ( \r\n" + 
		"select Dealer_Standards,Sales_Effective,MB_CSI, \r\n" + 
		"varchar_format((select TOTALAMTMSRPOPTS from DPBCALCPOCOUNT),'999,999,999,990') \r\n" + 
		"from REPORT_DATA \r\n" + 
		"), \r\n" + 
		"Final_Result(Dealer_Standards,Sales_Effective,MB_CSI,TOTAL_MSRP,Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,\r\n" + 
		"Dealer_Standards2, Sales_Effective2,MB_CSI2,NUMRETLQTR,YEARS,IDDLR,REGN,MKT,ADR_DLR_CTY,ADR_DLR_STA,TOT_CY13RetailSales,Dealer_Standards3,\r\n" + 
		"Sales_Effective3,MB_CSI3,TOT_EFF,Dealer_Standards_AMT,Sales_Effective_AMT,MB_CSI_AMT,TOTAL_PAYOUT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_UNERND_AMT) as \r\n" + 
		"(select FD.Dealer_Standards,FD.Sales_Effective,FD.MB_CSI,FD.TOTAL_MSRP,FD2.Dealer_Standards_name,FD2.Sales_Effective_name,FD2.Brand_stand_name, \r\n" + 
		"FD2.Dealer_Standards as Dealer_Standards2,FD2.Sales_Effective as Sales_Effective2,FD2.MB_CSI as MB_CSI2,FD2.NUMRETLQTR,FD2.YEARS,RD3.IDDLR,RD3.REGN,RD3.MKT,RD3.ADR_DLR_CTY,RD3.ADR_DLR_STA,RD3.TOT_CY13RetailSales, \r\n" + 
		"RD3.Dealer_Standards as Dealer_Standards3,RD3.Sales_Effective as Sales_Effective3,RD3.MB_CSI as MB_CSI3,TOT_EFF, \r\n" + 
		"RD3.Dealer_Standards_AMT,RD3.Sales_Effective_AMT, \r\n" + 
		"RD3.MB_CSI_AMT,\r\n" + 
		"RD3.TOTAL_PAYOUT,\r\n" + 
		"RD3.UNERND_AMT,RD3.TOTAL_CY13RetailSales,RD3.TOT_Dealer_Standards_AMT, \r\n" + 
		"RD3.TOT_Sales_Effective_AMT,RD3.TOT_MB_CSI_AMT,RD3.TOT_UNERND_AMT from FINAL_DATA FD,FINAL_DATA2 FD2,REPORT_DATA3 RD3 \r\n" + 
		")SELECT *  FROM FINAL_RESULT  WITH UR";

/**
 * Updated on 10/06/2016 for Performance Improvement
 * 1.) Passing chunk of dealers
 * 2.) removed trim and replaced IN clause(with single value) with "="
 * 3.) Removed pagination code.
 * 4.) Removed constants like %,$ etc
 */
public static final String DEALER_COMPLIANCE_QUERY_SMART = "with DPBDLR(IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA)  \r\n" + 
		"AS (select DISTINCT dlr.ID_DLR,dlr.CDE_RGN,dlr.CDE_MKT,dlr.CDE_MTRO_MKT_GRP,dlr.ADR_PRIM_CITY,dlr.CDE_DLR_ST \r\n" + 
		"from DEALER_FDRT dlr , DPBUSER.DPB_UNBLK_VEH AS RTL  WHERE DLR.ID_DLR = RTL.ID_DLR AND  \r\n" + 
		"RTL.ID_DLR IN OLDPAYOUT_DEALER_RANGE_FOR_RPRT  \r\n" + 
		"), \r\n" + 
		"DAYFDRTTBL(DTECAL_MIN,DTECAL_MAX,NUMRETLQTR,YEARS ) as ( \r\n" + 
		"select min(fdr.DTE_CAL),max(fdr.DTE_CAL),fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR  \r\n" + 
		"from DPB_DAY fdr  \r\n" + 
		"where (fdr.NUM_RETL_QTR,fdr.NUM_RETL_YR) in (select NUM_RETL_QTR,dim.NUM_RETL_YR from DPB_DAY dim \r\n" + 
		"     DATE_RANGE_FOR_REPORTS   ) \r\n" + 
		"group by fdr.NUM_RETL_QTR, fdr.NUM_RETL_YR), \r\n" + 
		" \r\n" + 
		"DLRPGMCALC(IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, ID_DPB_UNBLK_VEH,NUMRETLQTR,YEARS,NUM_PO,VEH_TYP) as ( \r\n" + 
		"select calc.ID_DLR,calc.ID_DPB_PGM,calc.AMT_DPB_BNS_CALC,calc.AMT_DPB_UNERND, \r\n" + 
		"calc.ID_DPB_UNBLK_VEH,dyfdrt.NUMRETLQTR,dyfdrt.YEARS, RTL.NUM_PO,rtl.CDE_VEH_TYP \r\n" + 
		"from DAYFDRTTBL dyfdrt \r\n" + 
		"join DPB_UNBLK_VEH rtl on rtl.DTE_RTL between dyfdrt.DTECAL_MIN and dyfdrt.DTECAL_MAX  \r\n" + 
		"     VEHICLE_TYPE_RANGE_FOR_REPORTS \r\n" + 
		"join DPB_CALC calc on calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH \r\n" + 
		"    AND calc.ID_LDRSP_BNS_PGM IS NULL  \r\n" + 
		"    --AND calc.ID_DPB_PGM IN (47,48,49,50,54,55,56,57,58,59,60,61,62) \r\n" + 
		"    AND calc.ID_DPB_PGM IN (141) \r\n" + 
		" AND CALC.ID_DLR IN (SELECT IDDLR FROM DPBDLR) \r\n" + 
		"), \r\n" + 
		"veh_data (ID_DLR, NUM_PO, ID_DPB_UNBLK_VEH) as ( \r\n" + 
		"select IDDLRCALC, NUM_PO, max(ID_DPB_UNBLK_VEH) \r\n" + 
		"from DLRPGMCALC \r\n" + 
		"group by IDDLRCALC, NUM_PO \r\n" + 
		"), \r\n" + 
		"VEHCOUNT(ID_DLR,NUM_PO,ID_DPB_UNBLK_VEH,TOTALAMTMSRPOPTS) as ( \r\n" + 
		"select rtl.ID_DLR, rtl.num_po,rtl.ID_DPB_UNBLK_VEH, sum(rtl.AMT_MSRP_TOT_ACSRY+rtl.AMT_MSRP_BASE) from veh_data vd \r\n" + 
		"join DPB_UNBLK_VEH rtl \r\n" + 
		"on rtl.ID_DLR = vd.ID_DLR and rtl.ID_DPB_UNBLK_VEH = vd.ID_DPB_UNBLK_VEH \r\n" + 
		"and rtl.num_po = vd.NUM_PO  \r\n" + 
		"group by rtl.ID_DLR,rtl.num_po,rtl.ID_DPB_UNBLK_VEH), \r\n" + 
		"DPBCALCPOCOUNT(TOTALAMTMSRPOPTS) as  \r\n" + 
		"(select sum(TOTALAMTMSRPOPTS) from VEHCOUNT cnt), \r\n" + 
		"RET_UNITS (ID_DLR, CDE_VEH_DDR_STS) AS \r\n" + 
		"(SELECT CALC.ID_DLR, RTL.CDE_VEH_DDR_STS\r\n" + 
		"FROM DAYFDRTTBL AS DYFDRT JOIN DPBUSER.DPB_UNBLK_VEH AS RTL ON RTL.DTE_RTL BETWEEN DYFDRT.DTECAL_MIN AND DYFDRT.DTECAL_MAX\r\n" + 
		"AND RTL.CDE_VEH_TYP = ('S') JOIN DPBUSER.DPB_CALC AS CALC ON CALC.ID_DPB_UNBLK_VEH = RTL.ID_DPB_UNBLK_VEH AND CALC.ID_LDRSP_BNS_PGM IS NULL\r\n" + 
		 "AND CALC.ID_DPB_PGM IN (51) AND CALC.ID_DLR IN (SELECT IDDLR FROM DPBDLR)),\r\n" + 

		"PO_COUNT(ID_DLR,CY13RetailSales) as ( \r\n" + 
		"select ID_DLR, sum(CASE WHEN (CDE_VEH_DDR_STS = 'I2') THEN -1 ELSE 1 END) from RET_UNITS group by ID_DLR \r\n" + 
		"), \r\n" + 
		"DLRPGMCALC_FINAL (IDDLRCALC,ID_DPB_PGM,SUM_AMT_DPB_BNS_CALC,SUM_AMT_DPB_UNERND, NUMRETLQTR,YEARS,VEH_TYP) as ( \r\n" + 
		"select calc.IDDLRCALC,calc.ID_DPB_PGM,sum(calc.SUM_AMT_DPB_BNS_CALC),sum(calc.SUM_AMT_DPB_UNERND), calc.NUMRETLQTR, \r\n" + 
		"calc.YEARS,VEH_TYP \r\n" + 
		"from VEHCOUNT veh \r\n" + 
		"left outer join DLRPGMCALC calc on veh.ID_DLR = calc.IDDLRCALC \r\n" + 
		"and veh.NUM_PO = calc.NUM_PO \r\n" + 
		"group by calc.IDDLRCALC,calc.ID_DPB_PGM,calc.NUMRETLQTR,calc.YEARS,VEH_TYP \r\n" + 
		"), \r\n" + 
		"LST_KPI (LST_UPDT,ID_DLR,QTR,YR,ID_KPI ) AS ( \r\n" + 
		"SELECT MAX(ID_KPI_FIL_EXTRT), dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI  \r\n" + 
		"from DLRPGMCALC_FINAL po \r\n" + 
		" \r\n" + 
		"left outer join DPB_KPI_FIL_EXTRT dlrkpi on  \r\n" + 
		"po.IDDLRCALC = dlrkpi.ID_DLR and po.NUMRETLQTR = dlrkpi.DTE_FSCL_QTR \r\n" + 
		"AND PO.YEARS = DLRKPI.DTE_FSCL_YR \r\n" + 
		"group by dlrkpi.ID_DLR, DTE_FSCL_QTR, DTE_FSCL_YR, ID_KPI \r\n" + 
		"), \r\n" + 
		" \r\n" + 
		"PGM_DATA (IDDLR,Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,Dealer_Standards_AMT,Sales_Effective_AMT, \r\n" + 
		"MB_CSI_AMT,MB_CSI_NEW_AMT,SUM_AMT_DPB_UNERND_AMT,VEH_TYP,CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA) as ( \r\n" + 
		"select \r\n" + 
		"dlr.IDDLR, \r\n" + 
		"max(case when (kpi.ID_KPI =34000 and ID_DPB_PGM = 54 and VEH_TYP = 'S') then  \r\n" + 
		"kpi.PCT_KPI end) as Dealer_Standards,  \r\n" + 
		"MAX(case when (kpi.ID_KPI =35000 and ID_DPB_PGM = 55 and VEH_TYP = 'S') then  \r\n" + 
		"                      kpi.PCT_KPI  end) as Sales_Effective, \r\n" + 
		"MAX(case when (kpi.ID_KPI =30000 and ID_DPB_PGM = 56 and VEH_TYP = 'S') then  \r\n" + 
		"                      kpi.PCT_KPI end) as MB_CSI, \r\n" + 
		"MAX(case when (kpi.ID_KPI =33000 and ID_DPB_PGM = 141 and VEH_TYP = 'S') then  \r\n" + 
		"kpi.PCT_KPI end) as MB_CSI_NEW, \r\n" + 
		"Max(case when ( ID_DPB_PGM  = 54 and VEH_TYP = 'S') then  \r\n" + 
		"SUM_AMT_DPB_BNS_CALC end) as Dealer_Standards_AMT, \r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 55 and VEH_TYP = 'S') then SUM_AMT_DPB_BNS_CALC end) as Sales_Effective_AMT, \r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 56 and VEH_TYP = 'S') then SUM_AMT_DPB_BNS_CALC end) as MB_CSI_AMT, \r\n" + 
		"MAX(case when ( ID_DPB_PGM  = 141 and VEH_TYP = 'S') then SUM_AMT_DPB_BNS_CALC end) as MB_CSI_NEW_AMT, \r\n" + 
		"MAX(SUM_AMT_DPB_UNERND) AS SUM_AMT_DPB_UNERND_AMT, \r\n" + 
		"VEH_TYP,pc.CY13RetailSales, \r\n" + 
		"coalesce(REGN,''),coalesce(MKT,''),coalesce(T55,''),coalesce(ADR_DLR_CTY,''),coalesce(ADR_DLR_STA,'') \r\n" + 
		"                                              \r\n" + 
		"from DLRPGMCALC_FINAL  f  \r\n" + 
		"join LST_KPI lst_kpi on lst_kpi.ID_DLR = IDDLRCALC \r\n" + 
		"join DPB_KPI_FIL_EXTRT kpi on lst_kpi.LST_UPDT = kpi.ID_KPI_FIL_EXTRT \r\n" + 
		"join PO_COUNT pc on pc.ID_DLR = f.IDDLRCALC \r\n" + 
		"right outer join DPBDLR dlr on f.IDDLRCALC = dlr.IDDLR \r\n" + 
		"group by  \r\n" + 
		"dlr.IDDLR \r\n" + 
		",VEH_TYP,pc.CY13RetailSales,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA), \r\n" + 
		" \r\n" + 
		"TOTALCY13RetailSales(TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT, \r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_MB_CSI_NEW_AMT,TOT_SUM_AMT_DPB_UNERND_AMT) AS  \r\n" + 
		"(SELECT SUM(CY13RetailSales),SUM(Dealer_Standards_AMT),SUM(Sales_Effective_AMT), \r\n" + 
		"SUM(MB_CSI_AMT),SUM(MB_CSI_NEW_AMT),SUM(SUM_AMT_DPB_UNERND_AMT) \r\n" + 
		"FROM PGM_DATA), \r\n" + 
		" \r\n" + 
		" \r\n" + 
		"PGM_DATA2 (Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,Brand_stand_new_name, \r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,NUMRETLQTR,YEARS) as ( \r\n" + 
		"select \r\n" + 
		"max(case when cal.ID_DPB_PGM = (54)  then NAM_DPB_PGM end) as Dealer_Standards_name, \r\n" + 
		"max(case when cal.ID_DPB_PGM = (55)  then NAM_DPB_PGM end) as Sales_Effective_name, \r\n" + 
		"max(case when cal.ID_DPB_PGM = (56)  then NAM_DPB_PGM end) as Brand_stand_name, \r\n" + 
		"max(case when cal.ID_DPB_PGM = (141)  then NAM_DPB_PGM end) as Brand_stand_new_name, \r\n" + 
		"max(case when (cal.ID_DPB_PGM = 54 and VEH_TYP = 'S') then PCT_VAR_PMT end) as Dealer_Standards, \r\n" + 
		"max(case when (cal.ID_DPB_PGM = 55 and VEH_TYP = 'S') then PCT_VAR_PMT end) as Sales_Effective, \r\n" + 
		"max(case when (cal.ID_DPB_PGM = 56 and VEH_TYP = 'S') then PCT_VAR_PMT end) as MB_CSI, \r\n" + 
		"max(case when (cal.ID_DPB_PGM = 141 and VEH_TYP = 'S') then PCT_VAR_PMT end) as MB_CSI_NEW, \r\n" + 
		"NUMRETLQTR,cal.YEARS \r\n" + 
		"from DLRPGMCALC  cal  \r\n" + 
		"join DPB_PGM pgm on cal.ID_DPB_PGM = pgm.ID_DPB_PGM \r\n" + 
		"group by NUMRETLQTR,YEARS \r\n" + 
		"), \r\n" + 
		"REPORT_DATA3 (IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA, \r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW, \r\n" + 
		"TOT_CY13RetailSales,Dealer_Standards_AMT,Sales_Effective_AMT, \r\n" + 
		"MB_CSI_AMT,MB_CSI_NEW_AMT,TOTAL_PAYOUT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT, \r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_MB_CSI_NEW_AMT,TOT_UNERND_AMT,TOT_EFF) as ( \r\n" + 
		"select IDDLR,REGN,MKT,T55,ADR_DLR_CTY,ADR_DLR_STA, \r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%', \r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%', \r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%', \r\n" + 
		"varchar_format(coalesce(MB_CSI_NEW,0),'90.99') || '%', \r\n" + 
		"coalesce(CY13RetailSales,0), \r\n" + 
		"'$' || trim(varchar_format(coalesce(Dealer_Standards_AMT,0),'999,999,990')), \r\n" + 
		"'$' || trim(varchar_format(coalesce(Sales_Effective_AMT,0),'999,999,990')), \r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_AMT,0),'999,999,990')), \r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_NEW_AMT,0),'999,999,990')), \r\n" + 
		"'$' || trim(varchar_format(coalesce(MB_CSI_NEW_AMT,0),'999,999,990')),\r\n" + 
		"'$' || trim(varchar_format(coalesce(SUM_AMT_DPB_UNERND_AMT,0),'999,999,990')), \r\n" + 
		"coalesce(TOTAL_CY13RetailSales,0), \r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Dealer_Standards_AMT,0),'999,999,990')), \r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_Sales_Effective_AMT,0),'999,999,990')), \r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_MB_CSI_AMT,0),'999,999,990')) , \r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_MB_CSI_NEW_AMT,0),'999,999,990')) , \r\n" + 
		"'$' || trim(varchar_format(coalesce(TOT_SUM_AMT_DPB_UNERND_AMT,0),'999,999,990')) , \r\n" + 
		"varchar_format(coalesce(MB_CSI_NEW,0),'90.99') || '%' \r\n" + 
		"from PGM_DATA pgm, \r\n" + 
		"TOTALCY13RetailSales \r\n" + 
		"), \r\n" + 
		" \r\n" + 
		" \r\n" + 
		"FINAL_DATA2(Dealer_Standards_name,Sales_Effective_name,Brand_stand_name,Brand_stand_new_name, \r\n" + 
		"Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,NUMRETLQTR,YEARS) as ( \r\n" + 
		"select trim(coalesce(Dealer_Standards_name,'')), \r\n" + 
		"trim(coalesce(Sales_Effective_name,'')), trim(coalesce(Brand_stand_name,'')),trim(coalesce(Brand_stand_new_name,'')), \r\n" + 
		"varchar_format(coalesce(Dealer_Standards,0),'90.99') || '%', \r\n" + 
		"varchar_format(coalesce(Sales_Effective,0),'90.99') || '%', \r\n" + 
		"varchar_format(coalesce(MB_CSI,0),'90.99') || '%', \r\n" + 
		"varchar_format(coalesce(MB_CSI_NEW,0),'90.99') || '%', \r\n" + 
		"case when NUMRETLQTR=1 then '1st' else case when NUMRETLQTR=2 then '2nd'  \r\n" + 
		"else case when NUMRETLQTR=3 then '3rd' else '4th' end end end as NUM_RETL_QTR,YEARS \r\n" + 
		"from PGM_DATA2 \r\n" + 
		"),   \r\n" + 
		"REPORT_DATA (Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW, \r\n" + 
		"TOT_CY13RetailSales) as ( \r\n" + 
		"select \r\n" + 
		"varchar_format(Float(avg(coalesce(Dealer_Standards,0))),'90.99')|| '%', \r\n" + 
		"varchar_format(Float(avg(coalesce(Sales_Effective,0))),'90.99')|| '%', \r\n" + 
		"varchar_format(Float(avg(coalesce(MB_CSI,0))),'90.99')|| '%', \r\n" + 
		"varchar_format(Float(avg(coalesce(MB_CSI_NEW,0))),'90.99')|| '%', \r\n" + 
		"sum(coalesce(CY13RetailSales,0)) \r\n" + 
		"from PGM_DATA pgm \r\n" + 
		"), \r\n" + 
		"FINAL_DATA (Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,TOTAL_MSRP) as ( \r\n" + 
		"select Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW, \r\n" + 
		"varchar_format((select TOTALAMTMSRPOPTS from DPBCALCPOCOUNT),'999,999,999,990') \r\n" + 
		"from REPORT_DATA \r\n" + 
		"), \r\n" + 
		"Final_Result(Dealer_Standards,Sales_Effective,MB_CSI,MB_CSI_NEW,TOTAL_MSRP,Dealer_Standards_name,Sales_Effective_name,\r\n" + 
		"Brand_stand_name,Brand_stand_new_name,Dealer_Standards2,Sales_Effective2,MB_CSI2,MB_CSI_NEW2,NUMRETLQTR,YEARS,IDDLR,REGN,\r\n" + 
		"MKT,ADR_DLR_CTY,ADR_DLR_STA,TOT_CY13RetailSales,Dealer_Standards3,Sales_Effective3,MB_CSI3,MB_CSI_NEW3,TOT_EFF,\r\n" + 
		"Dealer_Standards_AMT,Sales_Effective_AMT,MB_CSI_AMT,MB_CSI_NEW_AMT,TOTAL_PAYOUT,UNERND_AMT,TOTAL_CY13RetailSales,TOT_Dealer_Standards_AMT,\r\n" + 
		"TOT_Sales_Effective_AMT,TOT_MB_CSI_AMT,TOT_MB_CSI_NEW_AMT,TOT_UNERND_AMT) as \r\n" + 
		"(select FD.Dealer_Standards,FD.Sales_Effective,FD.MB_CSI,FD.MB_CSI_NEW,FD.TOTAL_MSRP, \r\n" + 
		"FD2.Dealer_Standards_name,FD2.Sales_Effective_name,FD2.Brand_stand_name,FD2.Brand_stand_new_name, \r\n" + 
		"FD2.Dealer_Standards AS Dealer_Standards2,FD2.Sales_Effective AS Sales_Effective2,FD2.MB_CSI AS MB_CSI2,FD2.MB_CSI_NEW AS MB_CSI_NEW2,FD2.NUMRETLQTR,FD2.YEARS,RD3.IDDLR,RD3.REGN,RD3.MKT,RD3.ADR_DLR_CTY,RD3.ADR_DLR_STA,RD3.TOT_CY13RetailSales, \r\n" + 
		"RD3.Dealer_Standards AS Dealer_Standards3,RD3.Sales_Effective AS Sales_Effective3,RD3.MB_CSI AS MB_CSI3,RD3.MB_CSI_NEW AS MB_CSI_NEW3,TOT_EFF, \r\n" + 
		"RD3.Dealer_Standards_AMT,RD3.Sales_Effective_AMT, \r\n" + 
		"RD3.MB_CSI_AMT,RD3.MB_CSI_NEW_AMT,\r\n" + 
		"RD3.TOTAL_PAYOUT,\r\n" + 
		"RD3.UNERND_AMT,RD3.TOTAL_CY13RetailSales,RD3.TOT_Dealer_Standards_AMT, \r\n" + 
		"RD3.TOT_Sales_Effective_AMT,RD3.TOT_MB_CSI_AMT,RD3.TOT_MB_CSI_NEW_AMT,RD3.TOT_UNERND_AMT from FINAL_DATA FD,FINAL_DATA2 FD2,REPORT_DATA3 RD3 \r\n" + 
		") SELECT *  FROM FINAL_RESULT  WITH UR";


//Fixed Margin-Start
public static final String FIXED_MARGIN = "with ALL_DATES (DTE_CAL, NUM_RETL_QTR, NUM_RETL_MTH,NUM_RETL_YR) as (\r\n" + 
		"select dim.DTE_CAL, dim.NUM_RETL_QTR, dim.NUM_RETL_MTH, dim.NUM_RETL_YR  from DPB_DAY dim \r\n" + 
		"ALL_DATE_RANGE\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"DAY_CAL (DTE_CAL_MIN,DTE_CAL_MAX, QTR, MN_NUM) as (\r\n" + 
		"select min(dim.DTE_CAL), max(dim.DTE_CAL), dim.NUM_RETL_QTR, dim.NUM_RETL_MTH  from ALL_DATES dim \r\n" + 
		"MONTHLY_DATE_RANGE \r\n" + 
		"group by dim.NUM_RETL_QTR, dim.NUM_RETL_MTH\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"DAY_CAL_QTR(DTE_CAL_MIN,DTE_CAL_MAX) as (\r\n" + 
		"select min(fdr.DTE_CAL),max(fdr.DTE_CAL)\r\n" + 
		"from ALL_DATES fdr \r\n" + 
		"QUARTERLY_DATE_RANGE \r\n" + 
		")\r\n" + 
		",\r\n" +
		" BONUS_MONTH(MON_START) AS (select min(dim.DTE_CAL) from DPB_DAY dim\r\n" +
		" BONUS_DATE_RANGE\r\n" +
		" and dim.NUM_RETL_MTH = '04'),\r\n" +
		"DAY_CAL_YRLY(DTE_CAL_MIN,DTE_CAL_MAX,YR) as (\r\n" + 
		"select bns.MON_START,max(fdr.DTE_CAL),NUM_RETL_YR\r\n" + 
		"from ALL_DATES fdr , BONUS_MONTH bns \r\n" + 
		"YEARLY_DATE_RANGE \r\n" + 
		"group by fdr.NUM_RETL_YR, bns.MON_START\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"CALC(ID_DLR,ID_PGM, ID_UNBLK_VEH, NUM_PO,CAL_AMT,ELG_AMT,VEH_STS,SPL_ID_PGM,PO_NUM,VEH_TYP) as (\r\n" + 
		"select calc.ID_DLR,calc.ID_DPB_PGM, calc.ID_DPB_UNBLK_VEH, rtl.NUM_PO, \r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_BNS_CALC,0)end,\r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_MAX_BNS_ELG,0)end,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,rtl.ID_DPB_PGM,\r\n" + 
		"case when rtl.CDE_VEH_DDR_STS ='I2' then -1 else 1 end as PO_NUM,rtl.CDE_VEH_TYP\r\n" + 
		"from DPB_CALC calc, DPB_UNBLK_VEH rtl, DAY_CAL dim\r\n" + 
		"where calc.ID_DPB_PGM in (51,52,53,46)\r\n" + 
		"VEHICLE_TYPE_RANGE_FOR_REPORTS \r\n" + 
		"    and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    and calc.IND_DPB_ADJ = 'N'\r\n" + 
		"    and rtl.DTE_RTL between dim.DTE_CAL_MIN and dim.DTE_CAL_MAX\r\n" + 
		") ,\r\n" + 
		"CALC_QTR(ID_DLR,ID_PGM, ID_UNBLK_VEH, NUM_PO,CAL_AMT,ELG_AMT,VEH_STS,SPL_ID_PGM,PO_NUM,VEH_TYP) as (\r\n" + 
		"select calc.ID_DLR,calc.ID_DPB_PGM, calc.ID_DPB_UNBLK_VEH, rtl.NUM_PO, \r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_BNS_CALC,0)end,\r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_MAX_BNS_ELG,0)end,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,rtl.ID_DPB_PGM,\r\n" + 
		"case when rtl.CDE_VEH_DDR_STS ='I2' then -1 else 1 end as PO_NUM,rtl.CDE_VEH_TYP\r\n" + 
		"from DPB_CALC calc, DPB_UNBLK_VEH rtl, DAY_CAL_QTR dim\r\n" + 
		"where calc.ID_DPB_PGM in (51,52,53,46)\r\n" + 
		"VEHICLE_TYPE_RANGE_FOR_REPORTS \r\n" + 
		"    and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    and calc.IND_DPB_ADJ = 'N'\r\n" + 
		"    and rtl.DTE_RTL between dim.DTE_CAL_MIN and dim.DTE_CAL_MAX\r\n" + 
		") ,\r\n" + 
		"CALC_YRLY(ID_DLR,ID_PGM, ID_UNBLK_VEH, NUM_PO,CAL_AMT,ELG_AMT,VEH_STS,SPL_ID_PGM,PO_NUM,VEH_TYP) as (\r\n" + 
		"select calc.ID_DLR,calc.ID_DPB_PGM, calc.ID_DPB_UNBLK_VEH, rtl.NUM_PO, \r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_BNS_CALC,0)end,\r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_MAX_BNS_ELG,0)end,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,rtl.ID_DPB_PGM,\r\n" + 
		"case when rtl.CDE_VEH_DDR_STS ='I2' then -1 else 1 end as PO_NUM,rtl.CDE_VEH_TYP\r\n" + 
		"from DPB_CALC calc, DPB_UNBLK_VEH rtl, DAY_CAL_YRLY dim\r\n" + 
		"where calc.ID_DPB_PGM in (51,52,53,46)\r\n" + 
		"VEHICLE_TYPE_RANGE_FOR_REPORTS \r\n" + 
		"    and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    and calc.IND_DPB_ADJ = 'N'\r\n" + 
		"    and rtl.DTE_RTL between dim.DTE_CAL_MIN and dim.DTE_CAL_MAX\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"GEN_PGM (SEQ,PGM_TYP,GEN_PGM_COUNT,GEN_AMT) as (\r\n" + 
		"select 1,'General Rtl' as PGM_TYP, coalesce(sum(cal.PO_NUM),0), coalesce(sum(cal.CAL_AMT),0) \r\n" + 
		"from CALC cal\r\n" + 
		"where cal.SPL_ID_PGM is null \r\n" + 
		")  \r\n" + 
		", \r\n" + 
		"GEN_PGM_QTR (SEQ,PGM_TYP,GEN_PGM_COUNT_QTR,GEN_AMT_QTR) as (\r\n" + 
		"select 1,'General Rtl' as PGM_TYP, coalesce(sum(calqtr.PO_NUM),0), coalesce(sum(calqtr.CAL_AMT),0) \r\n" + 
		"from CALC_QTR calqtr\r\n" + 
		"where calqtr.SPL_ID_PGM is null \r\n" + 
		")\r\n" + 
		",\r\n" + 
		"GEN_PGM_YRLY (SEQ,PGM_TYP,GEN_PGM_COUNT_YR,GEN_AMT_YR) as (\r\n" + 
		"select 1,'General Rtl' as PGM_TYP, coalesce(sum(calyr.PO_NUM),0), coalesce(sum(calyr.CAL_AMT),0) \r\n" + 
		"from CALC_YRLY calyr\r\n" + 
		"where calyr.SPL_ID_PGM is null \r\n" + 
		")\r\n" + 
		",\r\n" + 
		"GEN_PGM_OUTPUT(SEQ,PGM_TYP,MONTHLY_COUNT,MONTHLY_AMT,QTRLY_COUNT,QTRLY_AMT,YRLY_COUNT,YRLY_AMT) AS(\r\n" + 
		"SELECT 1,'Standard Retails' as PGM_TYP, gen.GEN_PGM_COUNT, gen.GEN_AMT ,\r\n" + 
		"genqtr.GEN_PGM_COUNT_QTR, genqtr.GEN_AMT_QTR, genyr.GEN_PGM_COUNT_YR, genyr.GEN_AMT_YR  FROM GEN_PGM gen,GEN_PGM_QTR genqtr,GEN_PGM_YRLY genyr)\r\n" + 
		",\r\n" + 
		"MVP_PGM (SEQ,PGM_TYP,MVP_PGM_COUNT,MVP_AMT) as (\r\n" + 
		"select 2,'MBDeal' as PGM_TYP, coalesce(sum(cal.PO_NUM),0), coalesce(sum(cal.CAL_AMT),0) \r\n" + 
		"from CALC cal\r\n" + 
		"where cal.SPL_ID_PGM in (121,123)\r\n" + 
		") \r\n" + 
		",\r\n" + 
		"MVP_PGM_QTR (SEQ,PGM_TYP,MVP_PGM_COUNT_QTR,MVP_AMT_QTR) as (\r\n" + 
		"select 2,'MBDeal' as PGM_TYP, coalesce(sum(calqtr.PO_NUM),0), coalesce(sum(calqtr.CAL_AMT),0) \r\n" + 
		"from CALC_QTR calqtr\r\n" + 
		"where calqtr.SPL_ID_PGM in (121,123)\r\n" + 
		") \r\n" + 
		",\r\n" + 
		"MVP_PGM_YR (SEQ,PGM_TYP,MVP_PGM_COUNT_YR,MVP_AMT_YR) as (\r\n" + 
		"select 2,'MBDeal' as PGM_TYP, coalesce(sum(calyr.PO_NUM),0), coalesce(sum(calyr.CAL_AMT),0) \r\n" + 
		"from CALC_YRLY calyr\r\n" + 
		"where calyr.SPL_ID_PGM in (121,123)\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"MVP_PGM_OUTPUT(SEQ,PGM_TYP,MONTHLY_COUNT,MONTHLY_AMT,QTRLY_COUNT,QTRLY_AMT,YRLY_COUNT,YRLY_AMT) AS(\r\n" + 
		"select 2,'MBDeal' as PGM_TYP, mvp.MVP_PGM_COUNT, mvp.MVP_AMT,mvpqtr.MVP_PGM_COUNT_QTR,mvpqtr.MVP_AMT_QTR,mvpyr.MVP_PGM_COUNT_YR,\r\n" + 
		"mvpyr.MVP_AMT_YR\r\n" + 
		"from MVP_PGM mvp,MVP_PGM_QTR mvpqtr, MVP_PGM_YR mvpyr)\r\n" + 
		",\r\n" + 
		"SUB_ONE(SEQ,PGM_TYP,SUB_ONE_PGM_COUNT,SUB_ONE_AMT) as (\r\n" + 
		"select 3,'Subtotal*',\r\n" + 
		"sum(gen.GEN_PGM_COUNT+mvp.MVP_PGM_COUNT),sum(gen.GEN_AMT+mvp.MVP_AMT)\r\n" + 
		"from GEN_PGM gen,MVP_PGM mvp \r\n" + 
		")\r\n" + 
		",\r\n" + 
		"SUB_ONE_QTR (SEQ,PGM_TYP,SUB_ONE_PGM_COUNT_QTR,SUB_ONE_AMT_QTR) as (\r\n" + 
		"select 3,'Subtotal*',\r\n" + 
		"sum(genqtr.GEN_PGM_COUNT_QTR+mvpqtr.MVP_PGM_COUNT_QTR),sum(genqtr.GEN_AMT_QTR+mvpqtr.MVP_AMT_QTR)\r\n" + 
		"from GEN_PGM_QTR genqtr,MVP_PGM_QTR mvpqtr \r\n" + 
		") \r\n" + 
		",\r\n" + 
		"SUB_ONE_YR (SEQ,PGM_TYP,SUB_ONE_PGM_COUNT_YR,SUB_ONE_AMT_YR) as (\r\n" + 
		"select 3,'Subtotal*',\r\n" + 
		"sum(genyr.GEN_PGM_COUNT_YR+mvpyr.MVP_PGM_COUNT_YR),sum(genyr.GEN_AMT_YR+mvpyr.MVP_AMT_YR)\r\n" + 
		"from GEN_PGM_YRLY genyr,MVP_PGM_YR mvpyr \r\n" + 
		")\r\n" + 
		",\r\n" + 
		"SUB_ONE_OUTPUT (SEQ,PGM_TYP,MONTHLY_COUNT,MONTHLY_AMT,QTRLY_COUNT,QTRLY_AMT,YRLY_COUNT,YRLY_AMT) as (\r\n" + 
		"select 3,'Subtotal', sub1.SUB_ONE_PGM_COUNT,sub1.SUB_ONE_AMT,sub1qtr.SUB_ONE_PGM_COUNT_QTR,sub1qtr.SUB_ONE_AMT_QTR,\r\n" + 
		"sub1yr.SUB_ONE_PGM_COUNT_YR,sub1yr.SUB_ONE_AMT_YR\r\n" + 
		"from SUB_ONE sub1,SUB_ONE_QTR sub1qtr, SUB_ONE_YR sub1yr\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"CVP_PGM (SEQ,PGM_TYP,CVP_PGM_COUNT,CVP_AMT) as (\r\n" + 
		"select 4,'CVP' as PGM_TYP, coalesce(sum(cal.PO_NUM),0), coalesce(sum(cal.CAL_AMT),0)\r\n" + 
		"from CALC cal\r\n" + 
		"where cal.SPL_ID_PGM = 122\r\n" + 
		") \r\n" + 
		",\r\n" + 
		"CVP_PGM_QTR (SEQ,PGM_TYP,CVP_PGM_COUNT_QTR,CVP_AMT_QTR) as (\r\n" + 
		"select 4,'CVP' as PGM_TYP, coalesce(sum(calqtr.PO_NUM),0), coalesce(sum(calqtr.CAL_AMT),0)\r\n" + 
		"from CALC_QTR calqtr\r\n" + 
		"where calqtr.SPL_ID_PGM = 122\r\n" + 
		"),\r\n" + 
		"CVP_PGM_YR (SEQ,PGM_TYP,CVP_PGM_COUNT_YR,CVP_AMT_YR) as (\r\n" + 
		"select 4,'CVP' as PGM_TYP, coalesce(sum(calyr.PO_NUM),0), coalesce(sum(calyr.CAL_AMT),0)\r\n" + 
		"from CALC_YRLY calyr\r\n" + 
		"where calyr.SPL_ID_PGM = 122\r\n" + 
		"),\r\n" + 
		"CVP_PGM_OUTPUT(SEQ,PGM_TYP,CVP_PGM_COUNT,CVP_AMT,CVP_PGM_COUNT_QTR,CVP_AMT_QTR,CVP_PGM_COUNT_YR,CVP_AMT_YR) AS (\r\n" + 
		"select 4,'CVP' as PGM_TYP,cvp.CVP_PGM_COUNT,cvp.CVP_AMT,cvpqtr.CVP_PGM_COUNT_QTR,cvpqtr.CVP_AMT_QTR,\r\n" + 
		"cvpyr.CVP_PGM_COUNT_YR, cvpyr.CVP_AMT_YR\r\n" + 
		"FROM CVP_PGM cvp,CVP_PGM_QTR cvpqtr,CVP_PGM_YR cvpyr\r\n" + 
		")\r\n" + 
		",\r\n" +
		"SUB_TWO (SEQ,PGM_TYP,SUB2_PGM_COUNT,SUB2_AMT) as (\r\n" + 
		"select 6,'Total',\r\n" + 
		"sum(sub1.SUB_ONE_PGM_COUNT+cvp.CVP_PGM_COUNT),\r\n" + 
		"sum(sub1.SUB_ONE_AMT+cvp.CVP_AMT)\r\n" + 
		"from SUB_ONE sub1,CVP_PGM cvp\r\n" + 
		") \r\n" + 
		",\r\n" + 
		"SUB_TWO_QTR (SEQ,PGM_TYP,SUB2_PGM_COUNT_QTR,SUB2_AMT_QTR) as (\r\n" + 
		"select 6,'Total',\r\n" + 
		"sum(sub1qtr.SUB_ONE_PGM_COUNT_QTR+cvpqtr.CVP_PGM_COUNT_QTR),\r\n" + 
		"sum(sub1qtr.SUB_ONE_AMT_QTR+cvpqtr.CVP_AMT_QTR)\r\n" + 
		"from SUB_ONE_QTR sub1qtr,CVP_PGM_QTR cvpqtr\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"SUB_TWO_YR (SEQ,PGM_TYP,SUB2_PGM_COUNT_YR,SUB2_AMT_YR) as (\r\n" + 
		"select 6,'Total',\r\n" + 
		"sum(sub1yr.SUB_ONE_PGM_COUNT_YR+cvpyr.CVP_PGM_COUNT_YR),\r\n" + 
		"sum(sub1yr.SUB_ONE_AMT_YR+cvpyr.CVP_AMT_YR)\r\n" + 
		"from SUB_ONE_YR sub1yr,CVP_PGM_YR cvpyr\r\n" + 
		") \r\n" + 
		",\r\n" + 
		"SUB_TWO_OUTPUT(SEQ,PGM_TYP,SUB2_PGM_COUNT,SUB2_AMT,SUB2_PGM_COUNT_QTR,SUB2_AMT_QTR,SUB2_PGM_COUNT_YR,SUB2_AMT_YR) AS (\r\n" + 
		"select  6,'Total' as PGM_TYP,sub2.SUB2_PGM_COUNT,sub2.SUB2_AMT,sub2qtr.SUB2_PGM_COUNT_QTR,sub2qtr.SUB2_AMT_QTR,\r\n" + 
		"sub2yr.SUB2_PGM_COUNT_YR,sub2yr.SUB2_AMT_YR\r\n" + 
		"FROM SUB_TWO sub2, SUB_TWO_QTR sub2qtr, SUB_TWO_YR sub2yr)\r\n" + 
		"\r\n" + 
		"SELECT * FROM GEN_PGM_OUTPUT\r\n" + 
		"UNION\r\n" + 
		"SELECT * FROM MVP_PGM_OUTPUT\r\n" + 
		"UNION\r\n" + 
		"SELECT * FROM SUB_ONE_OUTPUT\r\n" + 
		"UNION\r\n" + 
		"SELECT * FROM CVP_PGM_OUTPUT\r\n" + 
		"UNION\r\n" +
		"SELECT * FROM SUB_TWO_OUTPUT\r\n" + 
		"ORDER BY SEQ";



/*public static final String FIXED_MARGIN_PAYMENT = "with ALL_DATES (DTE_CAL, NUM_RETL_MTH,NUM_RETL_QTR, NUM_RETL_YR) as (\r\n" + 
		"select DTE_CAL, NUM_RETL_MTH, NUM_RETL_QTR, NUM_RETL_YR \r\n" + 
		"from DPB_DAY dim \r\n" + 
		"ALL_DATE_RANGE\r\n" + 
		"),\r\n" + 
		"DAY_CAL_MONTHLY (DTE_CAL_MIN,DTE_CAL_MAX, MN_NUM) as (\r\n" + 
		"select min(dim.DTE_CAL),max(dim.DTE_CAL),  dim.NUM_RETL_MTH  from ALL_DATES dim \r\n" + 
		"MONTHLY_DATE_RANGE \r\n" + 
		"group by dim.NUM_RETL_MTH\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"DAY_CAL_QTR(DTE_CAL_MIN,DTE_CAL_MAX) as (\r\n" + 
		"select min(fdr.DTE_CAL),max(fdr.DTE_CAL)\r\n" + 
		"from ALL_DATES fdr \r\n" + 
		"QUARTERLY_DATE_RANGE \r\n" + 
		")\r\n" + 
		",\r\n" + 
		"DAY_CAL_YRLY(DTE_CAL_MIN,DTE_CAL_MAX,YR) as (\r\n" + 
		"select min(fdr.DTE_CAL),max(fdr.DTE_CAL),NUM_RETL_YR\r\n" + 
		"from ALL_DATES fdr \r\n" + 
		"YEARLY_DATE_RANGE \r\n" + 
		"group by fdr.NUM_RETL_YR\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"PAY_CALC(ID_PGM, ID_UNBLK_VEH, NUM_PO,CAL_AMT,ELG_AMT,VEH_STS,SPL_ID_PGM,VEH_TYP) as (\r\n" + 
		"select calc.ID_DPB_PGM, calc.ID_DPB_UNBLK_VEH, rtl.NUM_PO, \r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_BNS_CALC,0)end,\r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_MAX_BNS_ELG,0)end,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,rtl.ID_DPB_PGM,rtl.CDE_VEH_TYP\r\n" + 
		"from DPB_CALC calc, DPB_UNBLK_VEH rtl, DAY_CAL_MONTHLY dim\r\n" + 
		"where calc.ID_DPB_PGM in (51,52,53,46)\r\n" + 
		"VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"    and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    and calc.IND_DPB_ADJ = 'N'\r\n" + 
		"    and rtl.DTE_RTL between dim.DTE_CAL_MIN and dim.DTE_CAL_MAX\r\n" + 
		"    and rtl.ID_DPB_PGM is null\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"PAY_CALC_QTR(ID_PGM, ID_UNBLK_VEH, NUM_PO,CAL_AMT,ELG_AMT,VEH_STS,SPL_ID_PGM,VEH_TYP) as (\r\n" + 
		"select calc.ID_DPB_PGM, calc.ID_DPB_UNBLK_VEH, rtl.NUM_PO, \r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_BNS_CALC,0)end,\r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_MAX_BNS_ELG,0)end,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,rtl.ID_DPB_PGM,rtl.CDE_VEH_TYP\r\n" + 
		"from DPB_CALC calc, DPB_UNBLK_VEH rtl, DAY_CAL_QTR dim\r\n" + 
		"where calc.ID_DPB_PGM in (51,52,53,46)\r\n" + 
		"VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"    and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    and calc.IND_DPB_ADJ = 'N'\r\n" + 
		"    and rtl.DTE_RTL between dim.DTE_CAL_MIN and dim.DTE_CAL_MAX\r\n" + 
		"    and rtl.ID_DPB_PGM is null\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"PAY_CALC_YR(ID_PGM, ID_UNBLK_VEH, NUM_PO,CAL_AMT,ELG_AMT,VEH_STS,SPL_ID_PGM,VEH_TYP) as (\r\n" + 
		"select calc.ID_DPB_PGM, calc.ID_DPB_UNBLK_VEH, rtl.NUM_PO, \r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_BNS_CALC,0)end,\r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_MAX_BNS_ELG,0)end,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,rtl.ID_DPB_PGM,rtl.CDE_VEH_TYP\r\n" + 
		"from DPB_CALC calc, DPB_UNBLK_VEH rtl, DAY_CAL_YRLY dim\r\n" + 
		"where calc.ID_DPB_PGM in (51,52,53,46)\r\n" + 
		"VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"    and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    and calc.IND_DPB_ADJ = 'N'\r\n" + 
		"    and rtl.DTE_RTL between dim.DTE_CAL_MIN and dim.DTE_CAL_MAX\r\n" + 
		"    and rtl.ID_DPB_PGM is null\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"PAY_AMT (TEXT, CAL_AMT) as (\r\n" + 
		"select case when mod((select distinct MN_NUM from DAY_CAL_MONTHLY),3) = 0 or cal.VEH_TYP in ('F') then \r\n" + 
		"'Standard Retails' else 'No payments made.'end as TEXT,\r\n" + 
		"case when mod((select distinct MN_NUM from DAY_CAL_MONTHLY),3) = 0 or cal.VEH_TYP in ('F') then \r\n" + 
		"sum(coalesce(cal.CAL_AMT,0)) else 0 end \r\n" + 
		"from PAY_CALC cal\r\n" + 
		"group by ('Standard Retails'),cal.VEH_TYP\r\n" + 
		") \r\n" + 
		",\r\n" + 
		"PAY_AMT_QTR (TEXT, CAL_AMT_QTR) as (\r\n" + 
		"select case when mod((select distinct MN_NUM from DAY_CAL_MONTHLY),3) = 0 or calqtr.VEH_TYP in ('F') then \r\n" + 
		"'Standard Retails' else 'No payments made.'end as TEXT,\r\n" + 
		"case when mod((select distinct MN_NUM from DAY_CAL_MONTHLY),3) = 0 or calqtr.VEH_TYP in ('F') then \r\n" + 
		"sum(coalesce(calqtr.CAL_AMT,0)) else 0 end \r\n" + 
		"from PAY_CALC_QTR calqtr\r\n" + 
		"group by ('Standard Retails'),calqtr.VEH_TYP\r\n" + 
		") \r\n" + 
		",\r\n" + 
		"PAY_AMT_YR (TEXT, CAL_AMT_YR) as (\r\n" + 
		"select case when mod((select distinct MN_NUM from DAY_CAL_MONTHLY),3) = 0 or calyr.VEH_TYP in ('F') then \r\n" + 
		"'Standard Retails' else 'No payments made.'end as TEXT,\r\n" + 
		"case when mod((select distinct MN_NUM from DAY_CAL_MONTHLY),3) = 0 or calyr.VEH_TYP in ('F') then \r\n" + 
		"sum(coalesce(calyr.CAL_AMT,0)) else 0 end \r\n" + 
		"from PAY_CALC_YR calyr\r\n" + 
		"group by ('Standard Retails'),calyr.VEH_TYP\r\n" + 
		") \r\n" + 
		",\r\n" + 
		"PAY_AMT_OUTPUT(TEXT, CAL_AMT,CAL_AMT_QTR, CAL_AMT_YR) AS (\r\n" + 
		"SELECT mon.TEXT,mon.CAL_AMT, qtr.CAL_AMT_QTR, yr.CAL_AMT_YR  FROM  PAY_AMT mon, PAY_AMT_QTR qtr, PAY_AMT_YR yr\r\n" + 
		") \r\n" + 
		"\r\n" + 
		"select * from PAY_AMT_OUTPUT";
	

public static final String FIXED_MARGIN_OWED = "with ALL_DATES (DTE_CAL, NUM_RETL_MTH,NUM_RETL_QTR, NUM_RETL_YR) as (\r\n" + 
		"select DTE_CAL, NUM_RETL_MTH, NUM_RETL_QTR, NUM_RETL_YR \r\n" + 
		"from DPB_DAY dim \r\n" + 
		"ALL_DATE_RANGE\r\n" + 
		"),\r\n" + 
		"DAY_CAL(DTE_CAL_MIN,DTE_CAL_MAX,YR) as (\r\n" + 
		"select min(fdr.DTE_CAL),max(fdr.DTE_CAL),NUM_RETL_YR\r\n" + 
		"from ALL_DATES fdr \r\n" + 
		"YEARLY_DATE_RANGE\r\n" + 
		"group by fdr.NUM_RETL_YR\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"CALC(ID_DLR, ID_PGM, ID_UNBLK_VEH, NUM_PO,CAL_AMT,ELG_AMT,VEH_STS,SPL_ID_PGM,YR,VEH_TYP) as (\r\n" + 
		"select calc.ID_DLR,calc.ID_DPB_PGM, calc.ID_DPB_UNBLK_VEH, rtl.NUM_PO, \r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_BNS_CALC,0)end,\r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_MAX_BNS_ELG,0)end,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,rtl.ID_DPB_PGM,dim.YR,rtl.CDE_VEH_TYP\r\n" + 
		"from DPB_CALC calc, DPB_UNBLK_VEH rtl, DAY_CAL dim\r\n" + 
		"where calc.ID_DPB_PGM in (51,52,53,46)\r\n" + 
		"VEHICLE_TYPE_RANGE_FOR_REPORTS  \r\n" + 
		"    and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH   \r\n" + 
		"    and rtl.DTE_RTL between dim.DTE_CAL_MIN and dim.DTE_CAL_MAX\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"PAY_CALC( ID_PGM, ID_UNBLK_VEH, NUM_PO,CAL_AMT,ELG_AMT,VEH_STS,SPL_ID_PGM,YR,VEH_TYP) as (\r\n" + 
		"select calc.ID_DPB_PGM, calc.ID_DPB_UNBLK_VEH, rtl.NUM_PO, \r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_BNS_CALC,0)end,\r\n" + 
		"case when calc.ID_DPB_PGM in (51,52,53,46) then COALESCE(calc.AMT_DPB_MAX_BNS_ELG,0)end,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,rtl.ID_DPB_PGM,dim.YR,rtl.CDE_VEH_TYP\r\n" + 
		"from DPB_CALC calc, DPB_UNBLK_VEH rtl, DAY_CAL dim\r\n" + 
		"where calc.ID_DPB_PGM in (51,52,53,46)\r\n" + 
		"VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"    and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"    and calc.IND_DPB_ADJ = 'N'   \r\n" + 
		"    and calc.CDE_DPB_CALC_STS in ('BP','TP','LP')\r\n" + 
		"    and rtl.DTE_RTL between dim.DTE_CAL_MIN and dim.DTE_CAL_MAX\r\n" + 
		"    and rtl.ID_DPB_PGM is null\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"OWED_SUB (SEQ, TEXT, CAL_AMT) as (\r\n" + 
		"select 1, 'Total Bonus Paid (YTD)' as TEXT,sum(coalesce(cal.CAL_AMT,0)) \r\n" + 
		"from CALC cal\r\n" + 
		"group by ('Total Bonus Paid (YTD)')\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"OWED_PAY (SEQ, TEXT, CAL_AMT) as (\r\n" + 
		"select 2,'Standard Retails Paid (YTD)', sum(coalesce(cal.CAL_AMT,0)) * -1\r\n" + 
		"from PAY_CALC cal\r\n" + 
		"right outer join DAY_CAL dim on 1=1\r\n" + 
		"group by ('Standard Retails Paid (YTD)')\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"OWED_TOT (SEQ, TEXT, CAL_AMT) as (\r\n" + 
		"select 3, 'CVP + MBDEAL Paid(YTD)', \r\n" + 
		"(gen.CAL_AMT+mvp.CAL_AMT)\r\n" + 
		"from OWED_SUB gen, OWED_PAY mvp\r\n" + 
		")\r\n" + 
		"select SEQ, TEXT, CAL_AMT from OWED_SUB\r\n" + 
		"union\r\n" + 
		"select SEQ, TEXT, CAL_AMT from OWED_PAY \r\n" + 
		"union\r\n" + 
		"select SEQ, TEXT, CAL_AMT from OWED_TOT order by SEQ";*/


//Fixed Margin-End

//Dealer Compliance Summary Report - Start
public static final String DLR_COMPL_SUM_REPORT_MONTHLY_CARS = "with DAY_CAL1 (CURR_DTE_CAL_MIN,CURR_DTE_CAL_MAX,NUM_RETL_QTR) as (\r\n" + 
		"select min(dim.DTE_CAL),max(dim.DTE_CAL) ,max(dim.NUM_RETL_QTR)\r\n" + 
		"from dpbuser.DPB_DAY dim\r\n" + 
		"DATE_RANGE_FOR_REPORTS\r\n" + 
		"group by dim.NUM_RETL_MTH\r\n" + 
		"),\r\n" + 
		"--select * from DAY_CAL_ST_END with ur;\r\n" + 
		"CALC_DATA(CDE_DPB_CALC_STS,ID_DPB_PGM,IND_DPB_ADJ,DTE_RTL,DTE_CAL,ID_DLR,ID_DPB_UNBLK_VEH,CDE_VEH_DDR_STS,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM) AS (\r\n" + 
		"select calc.CDE_DPB_CALC_STS,\r\n" + 
		"rtl.ID_DPB_PGM,\r\n" + 
		"calc.IND_DPB_ADJ,\r\n" + 
		"rtl.DTE_RTL,\r\n" + 
		"calc.DTE_CAL,\r\n" + 
		"calc.ID_DLR,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 144 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) AS CUST_EXP_SLS,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 145 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) as CUST_EXP_SVC,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 48 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) as NV_SLS,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 49 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0.00 end) as PRE_OWN,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 50 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end)  as BRAND_STD,\r\n" + 
		"sum(case when calc.ID_DPB_PGM in (144,145,49,48,50) then coalesce(calc.AMT_DPB_UNERND,0)else 0  end), \r\n" + 
		"max(case when rtl.CDE_VEH_DDR_STS ='I2' then -1 else 1 end) as PO_NUM\r\n" + 
		"from dpbuser.DPB_CALC calc, dpbuser.DPB_UNBLK_VEH rtl, DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (144,145,48,49,50)\r\n" + 
		"--VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"and rtl.CDE_VEH_TYP in ('P')\r\n" + 
		"and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"--and calc.IND_DPB_ADJ='N'\r\n" + 
		"and ((rtl.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX) or (calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day))\r\n" + 
		"DEALER_RANGE_FOR_REPORTS\r\n" + 
		"and (rtl.ID_DPB_PGM is null or rtl.ID_DPB_PGM in (121,122,123))\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"group by calc.CDE_DPB_CALC_STS,\r\n" + 
		"rtl.ID_DPB_PGM,\r\n" + 
		"calc.IND_DPB_ADJ,\r\n" + 
		"rtl.DTE_RTL,\r\n" + 
		"calc.DTE_CAL,\r\n" + 
		"calc.ID_DLR,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,\r\n" + 
		"rtl.CDE_VEH_DDR_STS\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"\r\n" + 
		"RETAILS_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as (\r\n" + 
		"select\r\n" + 
		"1,\r\n" + 
		"'M',\r\n" + 
		"'Retails', \r\n" + 
		"sum(CUST_EXP_SLS),\r\n" + 
		"sum(CUST_EXP_SVC),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(PRE_OWN),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"sum(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		" ),\r\n" + 
		" \r\n" + 
		" \r\n" + 
		"MBDEAL_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as \r\n" + 
		"(select \r\n" + 
		"3,\r\n" + 
		"'M',\r\n" + 
		"'MBDEAL',\r\n" + 
		"sum(CUST_EXP_SLS),\r\n" + 
		"sum(CUST_EXP_SVC),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(PRE_OWN),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"sum(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.IND_DPB_ADJ='N'),\r\n" + 
		"CVP_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as(\r\n" + 
		"select \r\n" + 
		"7,\r\n" + 
		"'M',\r\n" + 
		"'CVP',\r\n" + 
		"sum(CUST_EXP_SLS),\r\n" + 
		"sum(CUST_EXP_SVC),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(PRE_OWN),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where\r\n" + 
		"calc.ID_DPB_PGM = 122\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		"),\r\n" + 
		"RETAIL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM)\r\n" + 
		"as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"9,\r\n" + 
		"'M',\r\n" + 
		"'Ret Adjustments',\r\n" + 
		"sum(CUST_EXP_SLS),\r\n" + 
		"sum(CUST_EXP_SVC),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(PRE_OWN),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" + */
		"and calc.IND_DPB_ADJ='Y'\r\n" + 
		" ), \r\n" + 
		" MBDEAL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as\r\n" + 
		" (select \r\n" + 
		" 11,\r\n" + 
		"'M',\r\n" + 
		"'MBDEAL Adjustments',\r\n" + 
		"sum(CUST_EXP_SLS),\r\n" + 
		"sum(CUST_EXP_SVC),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(PRE_OWN),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"--and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" + */
		"and calc.IND_DPB_ADJ='Y')\r\n" + 
		"select * from RETAILS_MONTHLY\r\n" + 
		"union\r\n" + 
		"select * from MBDEAL_MONTHLY \r\n" + 
		"union \r\n" + 
		"select * from CVP_MONTHLY \r\n" + 
		"union\r\n" + 
		"select * from RETAIL_ADJ_MONTHLY\r\n" + 
		"union\r\n" + 
		"select * from MBDEAL_ADJ_MONTHLY\r\n" + 
		"ORDER BY SEQ\r\n" + 
		"with ur";

public static final String DLR_COMPL_SUM_REPORT_QTRLY_CARS = "with DAY_CAL1 (CURR_DTE_CAL_MIN,CURR_DTE_CAL_MAX,NUM_RETL_QTR) as (\r\n" + 
		"select min(dim.DTE_CAL),max(dim.DTE_CAL) ,max(dim.NUM_RETL_QTR)\r\n" + 
		"from dpbuser.DPB_DAY dim\r\n" + 
		"DATE_RANGE_FOR_REPORTS\r\n" + 
		"group by dim.NUM_RETL_MTH\r\n" + 
		")\r\n" + 
		"--select * from DAY_CAL1 with ur;\r\n" + 
		",\r\n" + 
		"DAY_CAL(DTE_CAL_MIN,DTE_CAL_MAX) as(\r\n" + 
		"select min(dim.DTE_CAL),max(dim.DTE_CAL) \r\n" + 
		"from dpbuser.DPB_DAY dim\r\n" + 
		"DATE_RANGE_FOR_PRIO\r\n" + 
		"and dim.NUM_RETL_QTR in (select NUM_RETL_QTR from DAY_CAL1))\r\n" + 
		"--select * from MONTH_DATE_RANGE with ur;\r\n" + 
		",\r\n" + 
		"DAY_CAL_ST_END (DTE_CAL_ST,DTE_CAL_END) as (\r\n" + 
		"select min(DTE_CAL_MIN),max(DTE_CAL_MAX) from DAY_CAL\r\n" + 
		")\r\n" + 
		"--select * from DAY_CAL_ST_END with ur;\r\n" + 
		",\r\n" + 
		"CALC_DATA(CDE_DPB_CALC_STS,ID_DPB_PGM,IND_DPB_ADJ,DTE_RTL,DTE_CAL,ID_DLR,ID_DPB_UNBLK_VEH,CDE_VEH_DDR_STS,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM,NUM_VIN) AS (\r\n" + 
		"select calc.CDE_DPB_CALC_STS,\r\n" + 
		"rtl.ID_DPB_PGM,\r\n" + 
		"calc.IND_DPB_ADJ,\r\n" + 
		"rtl.DTE_RTL,\r\n" + 
		"calc.DTE_CAL,\r\n" + 
		"calc.ID_DLR,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 144 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) AS CUST_EXP_SLS,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 145 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) as CUST_EXP_SVC,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 48 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) as NV_SLS,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 49 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0.00 end) as PRE_OWN,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 50 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end)  as BRAND_STD,\r\n" + 
		"sum(case when calc.ID_DPB_PGM in (144,145,49,48,50) then coalesce(calc.AMT_DPB_UNERND,0)  else 0  end), \r\n" + 
		"max(case when rtl.CDE_VEH_DDR_STS ='I2' then -1 else 1 end) as PO_NUM,RTL.NUM_VIN\r\n" + 
		"from dpbuser.DPB_CALC calc, dpbuser.DPB_UNBLK_VEH rtl, DAY_CAL1 dim,DAY_CAL_ST_END dcs \r\n" + 
		"where calc.ID_DPB_PGM in (144,145,48,49,50)\r\n" + 
		"--VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"and rtl.CDE_VEH_TYP in ('P')\r\n" + 
		"and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"--and calc.IND_DPB_ADJ='N'\r\n" + 
		"and ((rtl.DTE_RTL between dcs.DTE_CAL_ST and dim.CURR_DTE_CAL_MAX) or (calc.DTE_CAL between (dcs.DTE_CAL_ST + 1 day) and (dim.CURR_DTE_CAL_MAX + 1 day)))\r\n" + 
		"DEALER_RANGE_FOR_REPORTS\r\n" + 
		"and (rtl.ID_DPB_PGM is null or rtl.ID_DPB_PGM in (121,122,123))\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"group by calc.CDE_DPB_CALC_STS,\r\n" + 
		"rtl.ID_DPB_PGM,\r\n" + 
		"calc.IND_DPB_ADJ,\r\n" + 
		"rtl.DTE_RTL,\r\n" + 
		"calc.DTE_CAL,\r\n" + 
		"calc.ID_DLR,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,RTL.NUM_VIN\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"\r\n" + 
		"RETAILS_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as (\r\n" + 
		"select\r\n" + 
		"1,\r\n" + 
		"'M',\r\n" + 
		"'Retails', \r\n" + 
		"sum(CUST_EXP_SLS),\r\n" + 
		"sum(CUST_EXP_SVC),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(PRE_OWN),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"sum(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		" ),\r\n" + 
		" \r\n" + 
		" RETAILS_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as (\r\n" + 
		"select\r\n" + 
		"2,\r\n" + 
		"'Q',\r\n" + 
		"'Retails', \r\n" + 
		"sum(calc.CUST_EXP_SLS),\r\n" + 
		"sum(calc.CUST_EXP_SVC),\r\n" + 
		"sum(calc.NV_SLS),\r\n" + 
		"sum(calc.PRE_OWN),\r\n" + 
		"sum(calc.BRAND_STD),\r\n" + 
		"sum(calc.AMT_DPB_UNERND),\r\n" + 
		"sum(calc.PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" + 
		"and calc.DTE_RTL between dim.DTE_CAL_ST and dim.DTE_CAL_END\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		" )\r\n" + 
		" ,\r\n" + 
		"MBDEAL_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as \r\n" + 
		"(select \r\n" + 
		"3,\r\n" + 
		"'M',\r\n" + 
		"'MBDEAL',\r\n" + 
		"sum(CUST_EXP_SLS),\r\n" + 
		"sum(CUST_EXP_SVC),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(PRE_OWN),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"sum(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.IND_DPB_ADJ='N'),\r\n" + 
		"\r\n" + 
		"MBDEAL_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as \r\n" + 
		"(select \r\n" + 
		"4,\r\n" + 
		"'Q',\r\n" + 
		"'MBDEAL',\r\n" + 
		"sum(calc.CUST_EXP_SLS),\r\n" + 
		"sum(calc.CUST_EXP_SVC),\r\n" + 
		"sum(calc.NV_SLS),\r\n" + 
		"sum(calc.PRE_OWN),\r\n" + 
		"sum(calc.BRAND_STD),\r\n" + 
		"sum(calc.AMT_DPB_UNERND),\r\n" + 
		"sum(calc.PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.DTE_CAL_ST  and dim.DTE_CAL_END\r\n" + 
		"and calc.IND_DPB_ADJ='N'),\r\n" + 
		"CVP_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as(\r\n" + 
		"select \r\n" + 
		"7,\r\n" + 
		"'M',\r\n" + 
		"'CVP',\r\n" + 
		"sum(CUST_EXP_SLS),\r\n" + 
		"sum(CUST_EXP_SVC),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(PRE_OWN),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where\r\n" + 
		"calc.ID_DPB_PGM = 122\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"CVP_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as(\r\n" + 
		"select\r\n" + 
		"8,\r\n" + 
		"'Q',\r\n" + 
		"'CVP',\r\n" +  
		"sum(calc.CUST_EXP_SLS),\r\n" + 
		"sum(calc.CUST_EXP_SVC),\r\n" + 
		"sum(calc.NV_SLS),\r\n" + 
		"sum(calc.PRE_OWN),\r\n" + 
		"sum(calc.BRAND_STD),\r\n" + 
		"sum(calc.AMT_DPB_UNERND),\r\n" + 
		"SUM(calc.PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" + 
		"where\r\n" + 
		"calc.ID_DPB_PGM = 122\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.DTE_CAL_ST and dim.DTE_CAL_END\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"RETAIL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM)\r\n" + 
		"as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"9,\r\n" + 
		"'M',\r\n" + 
		"'Ret Adjustments',\r\n" + 
		"sum(CUST_EXP_SLS),\r\n" + 
		"sum(CUST_EXP_SVC),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(PRE_OWN),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"COUNT(DISTINCT (CALC.NUM_VIN))\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" + */
		"and calc.IND_DPB_ADJ='Y'\r\n" + 
		" ),\r\n" + 
		" RETAIL_ADJ_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM)\r\n" + 
		"as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"10,\r\n" + 
		"'Q',\r\n" + 
		"'Ret Adjustments',\r\n" + 
		"sum(calc.CUST_EXP_SLS),\r\n" + 
		"sum(calc.CUST_EXP_SVC),\r\n" + 
		"sum(calc.NV_SLS),\r\n" + 
		"sum(calc.PRE_OWN),\r\n" + 
		"sum(calc.BRAND_STD),\r\n" + 
		"sum(calc.AMT_DPB_UNERND),\r\n" + 
		"COUNT(DISTINCT (CALC.NUM_VIN))\r\n" + 
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" + 
		"and calc.DTE_RTL between dim.DTE_CAL_ST and dim.DTE_CAL_END\r\n" + 
		/*"and calc.DTE_CAL between (dim.DTE_CAL_ST + 1 day) and (dim.DTE_CAL_END + 1 day)\r\n" +*/ 
		"and calc.IND_DPB_ADJ='Y'\r\n" + 
		" ),\r\n" + 
		" \r\n" + 
		" MBDEAL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as\r\n" + 
		" (select \r\n" + 
		" 11,\r\n" + 
		"'M',\r\n" + 
		"'MBDEAL Adjustments',\r\n" + 
		"sum(CUST_EXP_SLS),\r\n" + 
		"sum(CUST_EXP_SVC),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(PRE_OWN),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"--and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" + */
		"and calc.IND_DPB_ADJ='Y'),\r\n" + 
		"\r\n" + 
		"MBDEAL_ADJ_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP_SLS,CUST_EXP_SVC,NV_SLS,PRE_OWN,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as\r\n" + 
		" (select \r\n" + 
		" 12,\r\n" + 
		" 'Q',\r\n" + 
		" 'MBDEAL Adjustments',\r\n" + 
		"sum(calc.CUST_EXP_SLS),\r\n" + 
		"sum(calc.CUST_EXP_SVC),\r\n" + 
		"sum(calc.NV_SLS),\r\n" + 
		"sum(calc.PRE_OWN),\r\n" + 
		"sum(calc.BRAND_STD),\r\n" + 
		"sum(calc.AMT_DPB_UNERND),\r\n" + 
		"SUM(calc.PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"--and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.DTE_CAL_ST  and dim.DTE_CAL_END\r\n" + 
		/*"and calc.DTE_CAL between (dim.DTE_CAL_ST + 1 day) and (dim.DTE_CAL_END + 1 day)\r\n" +*/ 
		"and calc.IND_DPB_ADJ='Y')\r\n" + 
		"select * from RETAILS_MONTHLY\r\n" + 
		"union\r\n" + 
		"select * from RETAILS_QTRLY\r\n" + 
		"union\r\n" + 
		"select * from MBDEAL_MONTHLY \r\n" + 
		"union \r\n" + 
		"select * from MBDEAL_QTRLY \r\n" + 
		"union \r\n" + 
		"select * from CVP_MONTHLY \r\n" + 
		"union\r\n" + 
		"select * from CVP_QTRLY \r\n" + 
		"union\r\n" + 
		"select * from RETAIL_ADJ_MONTHLY\r\n" + 
		"union\r\n" + 
		"select * from RETAIL_ADJ_QTRLY\r\n" + 
		"union\r\n" + 
		"select * from MBDEAL_ADJ_MONTHLY\r\n" + 
		"union\r\n" + 
		"select * from MBDEAL_ADJ_QTRLY\r\n" + 
		"ORDER BY SEQ\r\n" + 
		"with ur\r\n" +
		"";

//ratna Start
public static final String DLR_COMPL_SUM_REPORT_MONTHLY_FTL = "with DAY_CAL1 (CURR_DTE_CAL_MIN,CURR_DTE_CAL_MAX,NUM_RETL_QTR) as (\r\n" + 
		"select min(dim.DTE_CAL),max(dim.DTE_CAL) ,max(dim.NUM_RETL_QTR)\r\n" + 
		"from dpbuser.DPB_DAY dim\r\n" + 
		"DATE_RANGE_FOR_REPORTS\r\n" + 
		"group by dim.NUM_RETL_MTH\r\n" + 
		"),\r\n" + 
		"CALC_DATA(CDE_DPB_CALC_STS,ID_DPB_PGM,IND_DPB_ADJ,DTE_RTL,DTE_CAL,ID_DLR,ID_DPB_UNBLK_VEH,CDE_VEH_DDR_STS,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) AS (\r\n" + 
		"select calc.CDE_DPB_CALC_STS,\r\n" + 
		"rtl.ID_DPB_PGM,\r\n" + 
		"calc.IND_DPB_ADJ,\r\n" + 
		"rtl.DTE_RTL,\r\n" + 
		"calc.DTE_CAL,\r\n" + 
		"calc.ID_DLR,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 60 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) AS CUST_EXP,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 61 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) as BRAND_STD,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 62 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) as NV_SLS,\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"sum(coalesce(calc.AMT_DPB_UNERND,0 )) as AMT_DPB_UNERND,\r\n" + 
		"\r\n" + 
		"max(case when rtl.CDE_VEH_DDR_STS ='I2' then -1 else 1 end) as PO_NUM\r\n" + 
		"from dpbuser.DPB_CALC calc, dpbuser.DPB_UNBLK_VEH rtl, DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (60,61,62)\r\n" + 
		"--VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"and rtl.CDE_VEH_TYP in ('F')\r\n" + 
		"and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"--and calc.IND_DPB_ADJ='N'\r\n" + 
		"and ((rtl.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX) or (calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day))\r\n" + 
		"DEALER_RANGE_FOR_REPORTS\r\n" + 
		"and (rtl.ID_DPB_PGM is null or rtl.ID_DPB_PGM in (121,122,123))\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"group by calc.CDE_DPB_CALC_STS,\r\n" + 
		"rtl.ID_DPB_PGM,\r\n" + 
		"calc.IND_DPB_ADJ,\r\n" + 
		"rtl.DTE_RTL,\r\n" + 
		"calc.DTE_CAL,\r\n" + 
		"calc.ID_DLR,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,\r\n" + 
		"rtl.CDE_VEH_DDR_STS\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"\r\n" + 
		"RETAILS_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as (\r\n" + 
		"select\r\n" + 
		"1,\r\n" + 
		"'M',\r\n" + 
		"'Retails', \r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"sum(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		" ),\r\n" + 
		"MBDEAL_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as \r\n" + 
		"(select \r\n" + 
		"3,\r\n" + 
		"'M',\r\n" + 
		"'MBDEAL',\r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"sum(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.IND_DPB_ADJ='N'),\r\n" + 
		"CVP_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as(\r\n" + 
		"select \r\n" + 
		"7,\r\n" + 
		"'M',\r\n" + 
		"'CVP',\r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where\r\n" + 
		"calc.ID_DPB_PGM = 122\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		"),\r\n" + 
		"RETAIL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM)\r\n" + 
		"as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"9,\r\n" + 
		"'M',\r\n" + 
		"'Ret Adjustments',\r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" + */
		"and calc.IND_DPB_ADJ='Y'\r\n" + 
		" ),\r\n" + 
		" MBDEAL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as\r\n" + 
		" (select \r\n" + 
		" 11,\r\n" + 
		"'M',\r\n" + 
		"'MBDEAL Adjustments',\r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"--and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" + */
		"and calc.IND_DPB_ADJ='Y')\r\n" + 
		"select * from RETAILS_MONTHLY\r\n" + 
		"union\r\n" + 
		"select * from MBDEAL_MONTHLY \r\n" + 
		"union \r\n" + 
		"select * from CVP_MONTHLY \r\n" + 
		"union\r\n" + 
		"select * from RETAIL_ADJ_MONTHLY\r\n" + 
		"union\r\n" + 
		"select * from MBDEAL_ADJ_MONTHLY\r\n" + 
		"ORDER BY SEQ\r\n" + 
		"with ur";
public static final String DLR_COMPL_SUM_REPORT_QTRLY_FTL ="with DAY_CAL1 (CURR_DTE_CAL_MIN,CURR_DTE_CAL_MAX,NUM_RETL_QTR) as (\r\n" + 
		"select min(dim.DTE_CAL),max(dim.DTE_CAL) ,max(dim.NUM_RETL_QTR)\r\n" + 
		"from dpbuser.DPB_DAY dim\r\n" + 
		"DATE_RANGE_FOR_REPORTS\r\n" + 
		"group by dim.NUM_RETL_MTH\r\n" + 
		")\r\n" + 
		"--select * from DAY_CAL1 with ur;\r\n" + 
		",\r\n" + 
		"DAY_CAL(DTE_CAL_MIN,DTE_CAL_MAX) as(\r\n" + 
		"select min(dim.DTE_CAL),max(dim.DTE_CAL) \r\n" + 
		"from dpbuser.DPB_DAY dim\r\n" + 
		"DATE_RANGE_FOR_PRIO\r\n" + 
		"and dim.NUM_RETL_QTR in (select NUM_RETL_QTR from DAY_CAL1))\r\n" + 
		"--select * from MONTH_DATE_RANGE with ur;\r\n" + 
		",\r\n" + 
		"DAY_CAL_ST_END (DTE_CAL_ST,DTE_CAL_END) as (\r\n" + 
		"select min(DTE_CAL_MIN),max(DTE_CAL_MAX) from DAY_CAL\r\n" + 
		")\r\n" + 
		"--select * from DAY_CAL_ST_END with ur;\r\n" + 
		",\r\n" + 
		"CALC_DATA(CDE_DPB_CALC_STS,ID_DPB_PGM,IND_DPB_ADJ,DTE_RTL,DTE_CAL,ID_DLR,ID_DPB_UNBLK_VEH,CDE_VEH_DDR_STS,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) AS (\r\n" + 
		"select calc.CDE_DPB_CALC_STS,\r\n" + 
		"rtl.ID_DPB_PGM,\r\n" + 
		"calc.IND_DPB_ADJ,\r\n" + 
		"rtl.DTE_RTL,\r\n" + 
		"calc.DTE_CAL,\r\n" + 
		"calc.ID_DLR,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 60 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) AS CUST_EXP,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 61 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) as BRAND_STD,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 62 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) as NV_SLS,\r\n" + 
		"sum(coalesce(calc.AMT_DPB_UNERND,0 )) as AMT_DPB_UNERND,\r\n" + 
		"max(case when rtl.CDE_VEH_DDR_STS ='I2' then -1 else 1 end) as PO_NUM\r\n" + 
		"from dpbuser.DPB_CALC calc, dpbuser.DPB_UNBLK_VEH rtl, DAY_CAL1 dim,DAY_CAL_ST_END dcs \r\n" + 
		"where calc.ID_DPB_PGM in (60,61,62)\r\n" + 
		"--VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"and rtl.CDE_VEH_TYP in ('F')\r\n" + 
		"and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"--and calc.IND_DPB_ADJ='N'\r\n" + 
		"and ((rtl.DTE_RTL between dcs.DTE_CAL_ST and dim.CURR_DTE_CAL_MAX) or (calc.DTE_CAL between (dcs.DTE_CAL_ST + 1 day) and (dim.CURR_DTE_CAL_MAX + 1 day)))\r\n" + 
		"DEALER_RANGE_FOR_REPORTS\r\n" + 
		"and (rtl.ID_DPB_PGM is null or rtl.ID_DPB_PGM in (121,122,123))\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"group by calc.CDE_DPB_CALC_STS,\r\n" + 
		"rtl.ID_DPB_PGM,\r\n" + 
		"calc.IND_DPB_ADJ,\r\n" + 
		"rtl.DTE_RTL,\r\n" + 
		"calc.DTE_CAL,\r\n" + 
		"calc.ID_DLR,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,\r\n" + 
		"rtl.CDE_VEH_DDR_STS\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"\r\n" + 
		"RETAILS_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as (\r\n" + 
		"select\r\n" + 
		"1,\r\n" + 
		"'M',\r\n" + 
		"'Retails', \r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"sum(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		"),\r\n" + 
		" \r\n" + 
		" RETAILS_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as (\r\n" + 
		"select\r\n" + 
		"2,\r\n" + 
		"'Q',\r\n" + 
		"'Retails', \r\n" + 
		"sum(calc.CUST_EXP),\r\n" + 
		"sum(calc.BRAND_STD),\r\n" + 
		"sum(calc.NV_SLS),\r\n" + 
		"sum(calc.AMT_DPB_UNERND),\r\n" + 
		"sum(calc.PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" + 
		"and calc.DTE_RTL between dim.DTE_CAL_ST and dim.DTE_CAL_END \r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		 ")\r\n" + 
		 ",\r\n" + 
		"MBDEAL_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as \r\n" + 
		"(select \r\n" + 
		"3,\r\n" + 
		"'M',\r\n" + 
		"'MBDEAL',\r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"sum(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.IND_DPB_ADJ='N'),\r\n" + 
		"\r\n" + 
		"MBDEAL_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as \r\n" + 
		"(select \r\n" + 
		"4,\r\n" + 
		"'Q',\r\n" + 
		"'MBDEAL',\r\n" + 
		"sum(calc.CUST_EXP),\r\n" + 
		"sum(calc.BRAND_STD),\r\n" + 
		"sum(calc.NV_SLS),\r\n" + 
		"sum(calc.AMT_DPB_UNERND),\r\n" + 
		"sum(calc.PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL_ST_END dim\r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.DTE_CAL_ST  and dim.DTE_CAL_END \r\n" + 
		"and calc.IND_DPB_ADJ='N'),\r\n" + 
		"CVP_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as(\r\n" + 
		"select \r\n" + 
		"7,\r\n" + 
		"'M',\r\n" + 
		"'CVP',\r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where\r\n" + 
		"calc.ID_DPB_PGM = 122\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"CVP_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as(\r\n" + 
		"select\r\n" + 
		"8,\r\n" + 
		"'Q',\r\n" + 
		"'CVP',\r\n" + 
		"sum(calc.CUST_EXP),\r\n" + 
		"sum(calc.BRAND_STD),\r\n" + 
		"sum(calc.NV_SLS),\r\n" + 
		"sum(calc.AMT_DPB_UNERND),\r\n" + 
		"SUM(calc.PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" + 
		"where\r\n" + 
		"calc.ID_DPB_PGM = 122\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.DTE_CAL_ST and dim.DTE_CAL_END\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		"),\r\n" + 
		"\r\n" + 
		"RETAIL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM)\r\n" + 
		"as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"9,\r\n" + 
		"'M',\r\n" + 
		"'Ret Adjustments',\r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" +
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day \r\n" +*/ 
		"and calc.IND_DPB_ADJ='Y'\r\n" + 
		" ),\r\n" + 
		" RETAIL_ADJ_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM)\r\n" + 
		"as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"10,\r\n" + 
		"'Q',\r\n" + 
		"'Ret Adjustments',\r\n" + 
		"sum(calc.CUST_EXP),\r\n" + 
		"sum(calc.BRAND_STD),\r\n" + 
		"sum(calc.NV_SLS),\r\n" + 
		"sum(calc.AMT_DPB_UNERND),\r\n" + 
		"SUM(calc.PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL_ST_END dim\r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" +
		"and calc.DTE_RTL between dim.DTE_CAL_ST and dim.DTE_CAL_END \r\n" +
		/*"and calc.DTE_CAL between (dim.DTE_CAL_ST + 1 day) and (dim.DTE_CAL_END + 1 day)\r\n" +*/ 
		"and calc.IND_DPB_ADJ='Y'\r\n" + 
		" ),\r\n" + 
		" \r\n" + 
		" MBDEAL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as\r\n" + 
		" (select \r\n" + 
		" 11,\r\n" + 
		"'M',\r\n" + 
		"'MBDEAL Adjustments',\r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"--and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" +*/ 
		"and calc.IND_DPB_ADJ='Y'),\r\n" + 
		"\r\n" + 
		"MBDEAL_ADJ_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as\r\n" + 
		 "(select \r\n" + 
		 "12,\r\n" + 
		 "'Q',\r\n" + 
		 "'MBDEAL Adjustments',\r\n" + 
		"sum(calc.CUST_EXP),\r\n" + 
		"sum(calc.BRAND_STD),\r\n" + 
		"sum(calc.NV_SLS),\r\n" + 
		"sum(calc.AMT_DPB_UNERND),\r\n" + 
		"SUM(calc.PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"--and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.DTE_CAL_ST  and dim.DTE_CAL_END \r\n" +
		/*"and calc.DTE_CAL between (dim.DTE_CAL_ST + 1 day) and (dim.DTE_CAL_END + 1 day)\r\n" +*/ 
		"and calc.IND_DPB_ADJ='Y')\r\n" + 
		"select * from RETAILS_MONTHLY\r\n" + 
		"union\r\n" + 
		"select * from RETAILS_QTRLY\r\n" + 
		"union\r\n" + 
		"select * from MBDEAL_MONTHLY \r\n" + 
		"union \r\n" + 
		"select * from MBDEAL_QTRLY \r\n" + 
		"union \r\n" + 
		"select * from CVP_MONTHLY \r\n" + 
		"union\r\n" + 
		"select * from CVP_QTRLY \r\n" + 
		"union\r\n" + 
		"select * from RETAIL_ADJ_MONTHLY\r\n" + 
		"union\r\n" + 
		"select * from RETAIL_ADJ_QTRLY\r\n" + 
		"union\r\n" + 
		"select * from MBDEAL_ADJ_MONTHLY\r\n" + 
		"union\r\n" + 
		"select * from MBDEAL_ADJ_QTRLY\r\n" + 
		"ORDER BY SEQ\r\n" + 
		"with ur\r\n" +
		"";
public static final String DLR_COMPL_SUM_REPORT_MONTHLY_VANS = "with DAY_CAL1 (CURR_DTE_CAL_MIN,CURR_DTE_CAL_MAX,NUM_RETL_QTR) as (\r\n" + 
		"select min(dim.DTE_CAL),max(dim.DTE_CAL) ,max(dim.NUM_RETL_QTR)\r\n" + 
		"from dpbuser.DPB_DAY dim\r\n" + 
		"DATE_RANGE_FOR_REPORTS\r\n" + 
		"group by dim.NUM_RETL_MTH\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"CALC_DATA(CDE_DPB_CALC_STS,ID_DPB_PGM,IND_DPB_ADJ,DTE_RTL,DTE_CAL,ID_DLR,ID_DPB_UNBLK_VEH,CDE_VEH_DDR_STS,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM,NUM_VIN) AS (\r\n" + 
		"select calc.CDE_DPB_CALC_STS,\r\n" + 
		"rtl.ID_DPB_PGM,\r\n" + 
		"calc.IND_DPB_ADJ,\r\n" + 
		"rtl.DTE_RTL,\r\n" + 
		"calc.DTE_CAL,\r\n" + 
		"calc.ID_DLR,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 57 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) AS CUST_EXP,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 58 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end)  as BRAND_STD,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 59 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) as NV_SLS,\r\n" + 
		"sum(coalesce(calc.AMT_DPB_UNERND,0)) as AMT_DPB_UNERND,\r\n" + 
		"max(case when rtl.CDE_VEH_DDR_STS ='I2' then -1 else 1 end) as PO_NUM,rtl.NUM_VIN\r\n" + 
		"from dpbuser.DPB_CALC calc, dpbuser.DPB_UNBLK_VEH rtl, DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (57,58,59)-- (52,57,58,59)\r\n" + 
		"--VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"and rtl.CDE_VEH_TYP in ('V')\r\n" + 
		"and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"--and calc.IND_DPB_ADJ='N'\r\n" + 
		"and ((rtl.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX) or (calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day))\r\n" + 
		"DEALER_RANGE_FOR_REPORTS\r\n" + 
		"and (rtl.ID_DPB_PGM is null or rtl.ID_DPB_PGM in (121,122,123))\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"group by calc.CDE_DPB_CALC_STS,\r\n" + 
		"rtl.ID_DPB_PGM,\r\n" + 
		"calc.IND_DPB_ADJ,\r\n" + 
		"rtl.DTE_RTL,\r\n" + 
		"calc.DTE_CAL,\r\n" + 
		"calc.ID_DLR,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,rtl.NUM_VIN\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"\r\n" + 
		"RETAILS_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as (\r\n" + 
		"select\r\n" + 
		"1,\r\n" + 
		"'M',\r\n" + 
		"'Retails', \r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"sum(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		" ),\r\n" + 
		" \r\n" + 
		" \r\n" + 
		"MBDEAL_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as \r\n" + 
		"(select \r\n" + 
		"3,\r\n" + 
		"'M',\r\n" + 
		"'MBDEAL',\r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"sum(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.IND_DPB_ADJ='N'),\r\n" + 
		"CVP_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as(\r\n" + 
		"select \r\n" + 
		"7,\r\n" + 
		"'M',\r\n" + 
		"'CVP',\r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where\r\n" + 
		"calc.ID_DPB_PGM = 122\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		"),\r\n" + 
		"RETAIL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM)\r\n" + 
		"as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"9,\r\n" + 
		"'M',\r\n" + 
		"'Ret Adjustments',\r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"count(distinct(calc.NUM_VIN))\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" +*/ 
		"and calc.IND_DPB_ADJ='Y'\r\n" + 
		" ),\r\n" + 
		"\r\n" + 
		" \r\n" + 
		" MBDEAL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as\r\n" + 
		" (select \r\n" + 
		" 11,\r\n" + 
		"'M',\r\n" + 
		"'MBDEAL Adjustments',\r\n" + 
		"sum(CUST_EXP),\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(NV_SLS),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"count(distinct(calc.NUM_VIN))\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"--and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" +
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" + */
		"and calc.IND_DPB_ADJ='Y')\r\n" + 
		"select * from RETAILS_MONTHLY\r\n" + 
		"union\r\n" + 
		"select * from MBDEAL_MONTHLY \r\n" + 
		"union \r\n" + 
		"select * from CVP_MONTHLY \r\n" + 
		"union\r\n" + 
		"select * from RETAIL_ADJ_MONTHLY\r\n" + 
		"union\r\n" + 
		"select * from MBDEAL_ADJ_MONTHLY\r\n" + 
		"ORDER BY SEQ\r\n" + 
		"with ur";
public static final String DLR_COMPL_SUM_REPORT_QTRLY_VANS ="with DAY_CAL1 (CURR_DTE_CAL_MIN,CURR_DTE_CAL_MAX,NUM_RETL_QTR) as (\r\n" +
		"select min(dim.DTE_CAL),max(dim.DTE_CAL) ,max(dim.NUM_RETL_QTR)\r\n" +
		"from dpbuser.DPB_DAY dim\r\n" +
		"DATE_RANGE_FOR_REPORTS\r\n" + 
		"group by dim.NUM_RETL_MTH\r\n" +
		")\r\n" +
		"--select * from DAY_CAL1 with ur;\r\n" +
		",\r\n" +
		"DAY_CAL(DTE_CAL_MIN,DTE_CAL_MAX) as(\r\n" +
		"select min(dim.DTE_CAL),max(dim.DTE_CAL) \r\n" +
		"from dpbuser.DPB_DAY dim\r\n" +
		"DATE_RANGE_FOR_PRIO\r\n" + 
		"and dim.NUM_RETL_QTR in (select NUM_RETL_QTR from DAY_CAL1))\r\n" +
		"--select * from MONTH_DATE_RANGE with ur;\r\n" +
		",\r\n" +
		"DAY_CAL_ST_END (DTE_CAL_ST,DTE_CAL_END) as (\r\n" +
		"select min(DTE_CAL_MIN),max(DTE_CAL_MAX) from DAY_CAL\r\n" +
		")\r\n" +
		"--select * from DAY_CAL_ST_END with ur;\r\n" +
		",\r\n" +
		"CALC_DATA(CDE_DPB_CALC_STS,ID_DPB_PGM,IND_DPB_ADJ,DTE_RTL,DTE_CAL,ID_DLR,ID_DPB_UNBLK_VEH,CDE_VEH_DDR_STS,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM,NUM_VIN) AS (\r\n" +
		"select calc.CDE_DPB_CALC_STS,\r\n" +
		"rtl.ID_DPB_PGM,\r\n" +
		"calc.IND_DPB_ADJ,\r\n" +
		"rtl.DTE_RTL,\r\n" +
		"calc.DTE_CAL,\r\n" +
		"calc.ID_DLR,\r\n" +
		"calc.ID_DPB_UNBLK_VEH,\r\n" +
		"rtl.CDE_VEH_DDR_STS,\r\n" +
		"sum(case when calc.ID_DPB_PGM = 57 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) AS CUST_EXP,\r\n" +
		"sum(case when calc.ID_DPB_PGM = 58 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end)  as BRAND_STD,\r\n" +
		"sum(case when calc.ID_DPB_PGM = 59 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end) as NV_SLS,\r\n" +
		"sum(coalesce(calc.AMT_DPB_UNERND,0)) as AMT_DPB_UNERND,\r\n" +
		"max(case when rtl.CDE_VEH_DDR_STS ='I2' then -1 else 1 end) as PO_NUM,rtl.NUM_VIN\r\n" +
		"from dpbuser.DPB_CALC calc, dpbuser.DPB_UNBLK_VEH rtl, DAY_CAL1 dim,DAY_CAL_ST_END dcs \r\n" +
		"where calc.ID_DPB_PGM in (57,58,59)-- (52,57,58,59)\r\n" +
		"--VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" +
		"and rtl.CDE_VEH_TYP in ('V')\r\n" +
		"and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" +
		"--and calc.IND_DPB_ADJ='N'\r\n" +
		"and ((rtl.DTE_RTL between dcs.DTE_CAL_ST and dim.CURR_DTE_CAL_MAX) or (calc.DTE_CAL between (dcs.DTE_CAL_ST + 1 day) and (dim.CURR_DTE_CAL_MAX + 1 day)))\r\n" +
		"DEALER_RANGE_FOR_REPORTS\r\n" +
		"and (rtl.ID_DPB_PGM is null or rtl.ID_DPB_PGM in (121,122,123))\r\n" +
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"group by calc.CDE_DPB_CALC_STS,\r\n" +
		"rtl.ID_DPB_PGM,\r\n" +
		"calc.IND_DPB_ADJ,\r\n" +
		"rtl.DTE_RTL,\r\n" +
		"calc.DTE_CAL,\r\n" +
		"calc.ID_DLR,\r\n" +
		"calc.ID_DPB_UNBLK_VEH,\r\n" +
		"rtl.CDE_VEH_DDR_STS,rtl.NUM_VIN\r\n" +
		")\r\n" +
		",\r\n" +
		"\r\n" +
		"RETAILS_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as (\r\n" +
		"select\r\n" +
		"1,\r\n" +
		"'M',\r\n" +
		"'Retails', \r\n" +
		"sum(CUST_EXP),\r\n" +
		"sum(BRAND_STD),\r\n" +
		"sum(NV_SLS),\r\n" +
		"sum(AMT_DPB_UNERND),\r\n" +
		"sum(PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" +
		"where \r\n" +
		"calc.ID_DPB_PGM is null\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" +
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.IND_DPB_ADJ='N'\r\n" +
		" ),\r\n" +
		" \r\n" +
		" RETAILS_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as (\r\n" +
		"select\r\n" +
		"2,\r\n" +
		"'Q',\r\n" +
		"'Retails', \r\n" +
		"sum(calc.CUST_EXP),\r\n" +
		"sum(calc.BRAND_STD),\r\n" +
		"sum(calc.NV_SLS),\r\n" +
		"sum(calc.AMT_DPB_UNERND),\r\n" +
		"sum(calc.PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" +
		"where \r\n" +
		"calc.ID_DPB_PGM is null\r\n" +
		"and calc.DTE_RTL between dim.DTE_CAL_ST and dim.DTE_CAL_END\r\n" +
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.IND_DPB_ADJ='N'\r\n" +
		" )\r\n" +
		" ,\r\n" +
		"MBDEAL_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as \r\n" +
		"(select \r\n" +
		"3,\r\n" +
		"'M',\r\n" +
		"'MBDEAL',\r\n" +
		"sum(CUST_EXP),\r\n" +
		"sum(BRAND_STD),\r\n" +
		"sum(NV_SLS),\r\n" +
		"sum(AMT_DPB_UNERND),\r\n" +
		"sum(PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" +
		"where calc.ID_DPB_PGM in (121,123)  \r\n" +
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" +
		"and calc.IND_DPB_ADJ='N'),\r\n" +
		"\r\n" +
		"MBDEAL_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as \r\n" +
		"(select \r\n" +
		"4,\r\n" +
		"'Q',\r\n" +
		"'MBDEAL',\r\n" +
		"sum(calc.CUST_EXP),\r\n" +
		"sum(calc.BRAND_STD),\r\n" +
		"sum(calc.NV_SLS),\r\n" +
		"sum(calc.AMT_DPB_UNERND),\r\n" +
		"sum(calc.PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" +
		"where calc.ID_DPB_PGM in (121,123)  \r\n" +
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.DTE_CAL_ST  and dim.DTE_CAL_END\r\n" +
		"and calc.IND_DPB_ADJ='N'),\r\n" +
		"CVP_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as(\r\n" +
		"select \r\n" +
		"7,\r\n" +
		"'M',\r\n" +
		"'CVP',\r\n" +
		"sum(CUST_EXP),\r\n" +
		"sum(BRAND_STD),\r\n" +
		"sum(NV_SLS),\r\n" +
		"sum(AMT_DPB_UNERND),\r\n" +
		"SUM(PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" +
		"where\r\n" +
		"calc.ID_DPB_PGM = 122\r\n" +
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" +
		"and calc.IND_DPB_ADJ='N'\r\n" +
		"),\r\n" +
		"\r\n" +
		"CVP_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as(\r\n" +
		"select\r\n" +
		"8,\r\n" +
		"'Q',\r\n" +
		"'CVP',\r\n" +
		"sum(calc.CUST_EXP),\r\n" +
		"sum(calc.BRAND_STD),\r\n" +
		"sum(calc.NV_SLS),\r\n" +
		"sum(calc.AMT_DPB_UNERND),\r\n" +
		"SUM(calc.PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" +
		"where\r\n" +
		"calc.ID_DPB_PGM = 122\r\n" +
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.DTE_CAL_ST and dim.DTE_CAL_END\r\n" +
		"and calc.IND_DPB_ADJ='N'\r\n" +
		"),\r\n" +
		"\r\n" +
		"RETAIL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM)\r\n" +
		"as\r\n" +
		"(\r\n" +
		"select \r\n" +
		"9,\r\n" +
		"'M',\r\n" +
		"'Ret Adjustments',\r\n" +
		"sum(CUST_EXP),\r\n" +
		"sum(BRAND_STD),\r\n" +
		"sum(NV_SLS),\r\n" +
		"sum(AMT_DPB_UNERND),\r\n" +
		"count(distinct(calc.NUM_VIN))\r\n" +
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" +
		"where \r\n" +
		"calc.ID_DPB_PGM is null\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" +
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" +*/
		"and calc.IND_DPB_ADJ='Y'\r\n" +
		" ),\r\n" +
		" RETAIL_ADJ_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM)\r\n" +
		"as\r\n" +
		"(\r\n" +
		"select \r\n" +
		"10,\r\n" +
		"'Q',\r\n" +
		"'Ret Adjustments',\r\n" +
		"sum(calc.CUST_EXP),\r\n" +
		"sum(calc.BRAND_STD),\r\n" +
		"sum(calc.NV_SLS),\r\n" +
		"sum(calc.AMT_DPB_UNERND),\r\n" +
		"count(distinct(calc.NUM_VIN))\r\n" +
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" +
		"where \r\n" +
		"calc.ID_DPB_PGM is null\r\n" +
		"and calc.DTE_RTL between dim.DTE_CAL_ST and dim.DTE_CAL_END\r\n" +
		/*"and calc.DTE_CAL between (dim.DTE_CAL_ST + 1 day) and (dim.DTE_CAL_END + 1 day)\r\n" +*/
		"and calc.IND_DPB_ADJ='Y'\r\n" +
		" ),\r\n" +
		" \r\n" +
		" MBDEAL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as\r\n" +
		" (select \r\n" +
		" 11,\r\n" +
		"'M',\r\n" +
		"'MBDEAL Adjustments',\r\n" +
		"sum(CUST_EXP),\r\n" +
		"sum(BRAND_STD),\r\n" +
		"sum(NV_SLS),\r\n" +
		"sum(AMT_DPB_UNERND),\r\n" +
		"count(distinct(calc.NUM_VIN))\r\n" +
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" +
		"where calc.ID_DPB_PGM in (121,123)  \r\n" +
		"--and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" +
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" +*/
		"and calc.IND_DPB_ADJ='Y'),\r\n" +
		"\r\n" +
		"MBDEAL_ADJ_QTRLY(SEQ,PERIOD,BONUS_TYPE,CUST_EXP,BRAND_STD,NV_SLS,AMT_DPB_UNERND,PO_NUM) as\r\n" +
		" (select \r\n" +
		" 12,\r\n" +
		" 'Q',\r\n" +
		" 'MBDEAL Adjustments',\r\n" +
		"sum(calc.CUST_EXP),\r\n" +
		"sum(calc.BRAND_STD),\r\n" +
		"sum(calc.NV_SLS),\r\n" +
		"sum(calc.AMT_DPB_UNERND),\r\n" +
		"count(distinct(calc.NUM_VIN))\r\n" +
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" +
		"where calc.ID_DPB_PGM in (121,123)  \r\n" +
		"--and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.DTE_CAL_ST  and dim.DTE_CAL_END\r\n" +
		/*"and calc.DTE_CAL between (dim.DTE_CAL_ST + 1 day) and (dim.DTE_CAL_END + 1 day)\r\n" +*/
		"and calc.IND_DPB_ADJ='Y')\r\n" +
		"select * from RETAILS_MONTHLY\r\n" +
		"union\r\n" +
		"select * from RETAILS_QTRLY\r\n" +
		"union\r\n" +
		"select * from MBDEAL_MONTHLY \r\n" +
		"union \r\n" +
		"select * from MBDEAL_QTRLY \r\n" +
		"union \r\n" +
		"select * from CVP_MONTHLY \r\n" +
		"union\r\n" +
		"select * from CVP_QTRLY \r\n" +
		"union\r\n" +
		"select * from RETAIL_ADJ_MONTHLY\r\n" +
		"union\r\n" +
		"select * from RETAIL_ADJ_QTRLY\r\n" +
		"union\r\n" +
		"select * from MBDEAL_ADJ_MONTHLY\r\n" +
		"union\r\n" +
		"select * from MBDEAL_ADJ_QTRLY\r\n" +
		"ORDER BY SEQ\r\n" +
		"with ur\r\n" +
		"";


public static final String DLR_COMPL_SUM_REPORT_MONTHLY_SMART = "with DAY_CAL1 (CURR_DTE_CAL_MIN,CURR_DTE_CAL_MAX,NUM_RETL_QTR) as (\r\n" + 
		"select min(dim.DTE_CAL),max(dim.DTE_CAL) ,max(dim.NUM_RETL_QTR)\r\n" + 
		"from dpbuser.DPB_DAY dim\r\n" + 
		"DATE_RANGE_FOR_REPORTS\r\n" + 
		"group by dim.NUM_RETL_MTH\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"CALC_DATA(CDE_DPB_CALC_STS,ID_DPB_PGM,IND_DPB_ADJ,DTE_RTL,DTE_CAL,ID_DLR,ID_DPB_UNBLK_VEH,CDE_VEH_DDR_STS,BRAND_STD,AMT_DPB_UNERND,PO_NUM) AS (\r\n" + 
		"select calc.CDE_DPB_CALC_STS,\r\n" + 
		"rtl.ID_DPB_PGM,\r\n" + 
		"calc.IND_DPB_ADJ,\r\n" + 
		"rtl.DTE_RTL,\r\n" + 
		"calc.DTE_CAL,\r\n" + 
		"calc.ID_DLR,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,\r\n" + 
		"rtl.CDE_VEH_DDR_STS,\r\n" + 
		"sum(case when calc.ID_DPB_PGM = 141 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end)  as BRAND_STD,\r\n" + 
		"--sum(coalesce(calc.AMT_DPB_UNERND,0)) as AMT_DPB_UNERND,\r\n" + 
		"\r\n" + 
		"sum(coalesce((\r\n" + 
		"case when rtl.CDE_VEH_DDR_STS = 'I2' then \r\n" + 
		"	coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"else\r\n" + 
		"    (case when  calc.id_dpb_pgm = 141 then coalesce(calc.AMT_DPB_UNERND,0) end) \r\n" + 
		"end\r\n" + 
		"),0)) as AMT_DPB_UNERND,\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"max(case when rtl.CDE_VEH_DDR_STS ='I2' then -1 else 1 end) as PO_NUM\r\n" + 
		"from dpbuser.DPB_CALC calc, dpbuser.DPB_UNBLK_VEH rtl, DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (141)\r\n" + 
		"--VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" + 
		"and rtl.CDE_VEH_TYP in ('S')\r\n" + 
		"and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" + 
		"--and calc.IND_DPB_ADJ='N'\r\n" + 
		"and ((rtl.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX) or (calc.DTE_CAL= dim.CURR_DTE_CAL_MAX + 1 day))\r\n" + 
		"DEALER_RANGE_FOR_REPORTS\r\n" + 
		"and (rtl.ID_DPB_PGM is null or rtl.ID_DPB_PGM in (121,122,123))\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"group by calc.CDE_DPB_CALC_STS,\r\n" + 
		"rtl.ID_DPB_PGM,\r\n" + 
		"calc.IND_DPB_ADJ,\r\n" + 
		"rtl.DTE_RTL,\r\n" + 
		"calc.DTE_CAL,\r\n" + 
		"calc.ID_DLR,\r\n" + 
		"calc.ID_DPB_UNBLK_VEH,\r\n" + 
		"rtl.CDE_VEH_DDR_STS\r\n" + 
		")\r\n" + 
		",\r\n" + 
		"\r\n" + 
		"RETAILS_MONTHLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as (\r\n" + 
		"select\r\n" + 
		"1,\r\n" + 
		"'M',\r\n" + 
		"'Retails', \r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"sum(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		" ),\r\n" + 
		" \r\n" + 
		" \r\n" + 
		"MBDEAL_MONTHLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as \r\n" + 
		"(select \r\n" + 
		"3,\r\n" + 
		"'M',\r\n" + 
		"'MBDEAL',\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"sum(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.IND_DPB_ADJ='N'),\r\n" + 
		"CVP_MONTHLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as(\r\n" + 
		"select \r\n" + 
		"7,\r\n" + 
		"'M',\r\n" + 
		"'CVP',\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where\r\n" + 
		"calc.ID_DPB_PGM = 122\r\n" + 
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" + 
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" + 
		"and calc.IND_DPB_ADJ='N'\r\n" + 
		"),\r\n" + 
		"RETAIL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM)\r\n" + 
		"as\r\n" + 
		"(\r\n" + 
		"select \r\n" + 
		"9,\r\n" + 
		"'M',\r\n" + 
		"'Ret Adjustments',\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where \r\n" + 
		"calc.ID_DPB_PGM is null\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" +
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" +*/ 
		"and calc.IND_DPB_ADJ='Y'\r\n" + 
		" ),\r\n" + 
		" MBDEAL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as\r\n" + 
		" (select \r\n" + 
		" 11,\r\n" + 
		"'M',\r\n" + 
		"'MBDEAL Adjustments',\r\n" + 
		"sum(BRAND_STD),\r\n" + 
		"sum(AMT_DPB_UNERND),\r\n" + 
		"SUM(PO_NUM)\r\n" + 
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" + 
		"where calc.ID_DPB_PGM in (121,123)  \r\n" + 
		"--and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" +
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" +*/ 
		"and calc.IND_DPB_ADJ='Y')\r\n" + 
		"select * from RETAILS_MONTHLY\r\n" + 
		"union\r\n" + 
		"select * from MBDEAL_MONTHLY \r\n" + 
		"union \r\n" + 
		"select * from CVP_MONTHLY \r\n" + 
		"union\r\n" + 
		"select * from RETAIL_ADJ_MONTHLY\r\n" + 
		"union\r\n" + 
		"select * from MBDEAL_ADJ_MONTHLY\r\n" + 
		"ORDER BY SEQ\r\n" + 
		"with ur";

public static final String DLR_COMPL_SUM_REPORT_QTRLY_SMART ="with DAY_CAL1 (CURR_DTE_CAL_MIN,CURR_DTE_CAL_MAX,NUM_RETL_QTR) as (\r\n" +
		"select min(dim.DTE_CAL),max(dim.DTE_CAL) ,max(dim.NUM_RETL_QTR)\r\n" +
		"from dpbuser.DPB_DAY dim\r\n" +
				"DATE_RANGE_FOR_REPORTS\r\n" + 
		"group by dim.NUM_RETL_MTH\r\n" +
		")\r\n" +
		"--select * from DAY_CAL1 with ur;\r\n" +
		",\r\n" +
		"DAY_CAL(DTE_CAL_MIN,DTE_CAL_MAX) as(\r\n" +
		"select min(dim.DTE_CAL),max(dim.DTE_CAL) \r\n" +
		"from dpbuser.DPB_DAY dim\r\n" +
				"DATE_RANGE_FOR_PRIO\r\n" + 
		"and dim.NUM_RETL_QTR in (select NUM_RETL_QTR from DAY_CAL1))\r\n" + 
		"--select * from MONTH_DATE_RANGE with ur;\r\n" +
		",\r\n" +
		"DAY_CAL_ST_END (DTE_CAL_ST,DTE_CAL_END) as (\r\n" +
		"select min(DTE_CAL_MIN),max(DTE_CAL_MAX) from DAY_CAL\r\n" +
		")\r\n" +
		"--select * from DAY_CAL_ST_END with ur;\r\n" +
		",\r\n" +
		"CALC_DATA(CDE_DPB_CALC_STS,ID_DPB_PGM,IND_DPB_ADJ,DTE_RTL,DTE_CAL,ID_DLR,ID_DPB_UNBLK_VEH,CDE_VEH_DDR_STS,BRAND_STD,AMT_DPB_UNERND,PO_NUM) AS (\r\n" +
		"select calc.CDE_DPB_CALC_STS,\r\n" +
		"rtl.ID_DPB_PGM,\r\n" +
		"calc.IND_DPB_ADJ,\r\n" +
		"rtl.DTE_RTL,\r\n" +
		"calc.DTE_CAL,\r\n" +
		"calc.ID_DLR,\r\n" +
		"calc.ID_DPB_UNBLK_VEH,\r\n" +
		"rtl.CDE_VEH_DDR_STS,\r\n" +
		"sum(case when calc.ID_DPB_PGM = 141 then COALESCE(calc.AMT_DPB_BNS_CALC,0) else 0 end)  as BRAND_STD,\r\n" +
		"sum(coalesce((\r\n" + 
		"case when rtl.CDE_VEH_DDR_STS = 'I2' then \r\n" + 
		"	coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"else\r\n" + 
		"    (case when  calc.id_dpb_pgm = 141 then coalesce(calc.AMT_DPB_UNERND,0) end) \r\n" + 
		"end\r\n" + 
		"),0)) as AMT_DPB_UNERND," +
		"max(case when rtl.CDE_VEH_DDR_STS ='I2' then -1 else 1 end) as PO_NUM\r\n" +
		"from dpbuser.DPB_CALC calc, dpbuser.DPB_UNBLK_VEH rtl, DAY_CAL1 dim,DAY_CAL_ST_END dcs \r\n" +
		"where calc.ID_DPB_PGM in (141)\r\n" +
		"--VEHICLE_TYPE_RANGE_FOR_REPORTS\r\n" +
		"and rtl.CDE_VEH_TYP in ('S')\r\n" +
		"and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH\r\n" +
		"--and calc.IND_DPB_ADJ='N'\r\n" +
		"and ((rtl.DTE_RTL between dcs.DTE_CAL_ST and dim.CURR_DTE_CAL_MAX) or (calc.DTE_CAL between (dcs.DTE_CAL_ST + 1 day) and (dim.CURR_DTE_CAL_MAX + 1 day)))\r\n" +
		"DEALER_RANGE_FOR_REPORTS\r\n" +
		"and (rtl.ID_DPB_PGM is null or rtl.ID_DPB_PGM in (121,122,123))\r\n" +
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"group by calc.CDE_DPB_CALC_STS,\r\n" +
		"rtl.ID_DPB_PGM,\r\n" +
		"calc.IND_DPB_ADJ,\r\n" +
		"rtl.DTE_RTL,\r\n" +
		"calc.DTE_CAL,\r\n" +
		"calc.ID_DLR,\r\n" +
		"calc.ID_DPB_UNBLK_VEH,\r\n" +
		"rtl.CDE_VEH_DDR_STS\r\n" +
		")\r\n" +
		",\r\n" +
		"\r\n" +
		"RETAILS_MONTHLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as (\r\n" +
		"select\r\n" +
		"1,\r\n" +
		"'M',\r\n" +
		"'Retails', \r\n" +
		"sum(BRAND_STD),\r\n" +
		"sum(AMT_DPB_UNERND),\r\n" +
		"sum(PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" +
		"where \r\n" +
		"calc.ID_DPB_PGM is null\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" +
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.IND_DPB_ADJ='N'\r\n" +
		" ),\r\n" +
		" \r\n" +
		" RETAILS_QTRLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as (\r\n" +
		"select\r\n" +
		"2,\r\n" +
		"'Q',\r\n" +
		"'Retails', \r\n" +
		"sum(calc.BRAND_STD),\r\n" +
		"sum(calc.AMT_DPB_UNERND),\r\n" +
		"sum(calc.PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" +
		"where \r\n" +
		"calc.ID_DPB_PGM is null\r\n" +
		"and calc.DTE_RTL between dim.DTE_CAL_ST and dim.DTE_CAL_END\r\n" +
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.IND_DPB_ADJ='N'\r\n" +
		" )\r\n" +
		" ,\r\n" +
		"MBDEAL_MONTHLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as \r\n" +
		"(select \r\n" +
		"3,\r\n" +
		"'M',\r\n" +
		"'MBDEAL',\r\n" +
		"sum(BRAND_STD),\r\n" +
		"sum(AMT_DPB_UNERND),\r\n" +
		"sum(PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" +
		"where calc.ID_DPB_PGM in (121,123)  \r\n" +
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" +
		"and calc.IND_DPB_ADJ='N'),\r\n" +
		"\r\n" +
		"MBDEAL_QTRLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as \r\n" +
		"(select \r\n" +
		"4,\r\n" +
		"'Q',\r\n" +
		"'MBDEAL',\r\n" +
		"sum(calc.BRAND_STD),\r\n" +
		"sum(calc.AMT_DPB_UNERND),\r\n" +
		"sum(calc.PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" +
		"where calc.ID_DPB_PGM in (121,123)  \r\n" +
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.DTE_CAL_ST  and dim.DTE_CAL_END \r\n" +
		"and calc.IND_DPB_ADJ='N'),\r\n" +
		"CVP_MONTHLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as(\r\n" +
		"select \r\n" +
		"7,\r\n" +
		"'M',\r\n" +
		"'CVP',\r\n" +
		"sum(BRAND_STD),\r\n" +
		"sum(AMT_DPB_UNERND),\r\n" +
		"SUM(PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" +
		"where\r\n" +
		"calc.ID_DPB_PGM = 122\r\n" +
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" +
		"and calc.IND_DPB_ADJ='N'\r\n" +
		"),\r\n" +
		"\r\n" +
		"CVP_QTRLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as(\r\n" +
		"select\r\n" +
		"8,\r\n" +
		"'Q',\r\n" +
		"'CVP',\r\n" +
		"sum(calc.BRAND_STD),\r\n" +
		"sum(calc.AMT_DPB_UNERND),\r\n" +
		"SUM(calc.PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" +
		"where\r\n" +
		"calc.ID_DPB_PGM = 122\r\n" +
		"and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.DTE_CAL_ST and dim.DTE_CAL_END\r\n" +
		"and calc.IND_DPB_ADJ='N'\r\n" +
		"),\r\n" +
		"\r\n" +
		"RETAIL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM)\r\n" +
		"as\r\n" +
		"(\r\n" +
		"select \r\n" +
		"9,\r\n" +
		"'M',\r\n" +
		"'Ret Adjustments',\r\n" +
		"sum(BRAND_STD),\r\n" +
		"sum(AMT_DPB_UNERND),\r\n" +
		"SUM(PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" +
		"where \r\n" +
		"calc.ID_DPB_PGM is null\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" +
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" +*/
		"and calc.IND_DPB_ADJ='Y'\r\n" +
		" ),\r\n" +
		" RETAIL_ADJ_QTRLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM)\r\n" +
		"as\r\n" +
		"(\r\n" +
		"select \r\n" +
		"10,\r\n" +
		"'Q',\r\n" +
		"'Ret Adjustments',\r\n" +
		"sum(calc.BRAND_STD),\r\n" +
		"sum(calc.AMT_DPB_UNERND),\r\n" +
		"sum(calc.PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" +
		"where \r\n" +
		"calc.ID_DPB_PGM is null\r\n" +
		"and calc.DTE_RTL between dim.DTE_CAL_ST and dim.DTE_CAL_END\r\n" +
		/*"and calc.DTE_CAL between (dim.DTE_CAL_ST + 1 day) and (dim.DTE_CAL_END + 1 day)\r\n" +*/
		"and calc.IND_DPB_ADJ='Y'\r\n" +
		" ),\r\n" +
		" \r\n" +
		" MBDEAL_ADJ_MONTHLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as\r\n" +
		" (select \r\n" +
		" 11,\r\n" +
		"'M',\r\n" +
		"'MBDEAL Adjustments',\r\n" +
		"sum(BRAND_STD),\r\n" +
		"sum(AMT_DPB_UNERND),\r\n" +
		"SUM(PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL1 dim\r\n" +
		"where calc.ID_DPB_PGM in (121,123)  \r\n" +
		"--and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.CURR_DTE_CAL_MIN and dim.CURR_DTE_CAL_MAX\r\n" +
		/*"and calc.DTE_CAL = dim.CURR_DTE_CAL_MAX + 1 day\r\n" +*/
		"and calc.IND_DPB_ADJ='Y'),\r\n" +
		"\r\n" +
		"MBDEAL_ADJ_QTRLY(SEQ,PERIOD,BONUS_TYPE,BRAND_STD,AMT_DPB_UNERND,PO_NUM) as\r\n" +
		" (select \r\n" +
		" 12,\r\n" +
		" 'Q',\r\n" +
		" 'MBDEAL Adjustments',\r\n" +
		"sum(calc.BRAND_STD),\r\n" +
		"sum(calc.AMT_DPB_UNERND),\r\n" +
		"SUM(calc.PO_NUM)\r\n" +
		"from CALC_DATA calc,DAY_CAL_ST_END dim \r\n" +
		"where calc.ID_DPB_PGM in (121,123)  \r\n" +
		"--and calc.CDE_DPB_CALC_STS in ('BC','BP','LP','LC')\r\n" +
		"and calc.DTE_RTL between dim.DTE_CAL_ST  and dim.DTE_CAL_END \r\n" +
		/*"and calc.DTE_CAL between (dim.DTE_CAL_ST + 1 day) and (dim.DTE_CAL_END + 1 day)\r\n" +*/
		"and calc.IND_DPB_ADJ='Y')\r\n" +
		"select * from RETAILS_MONTHLY\r\n" +
		"union\r\n" +
		"select * from RETAILS_QTRLY\r\n" +
		"union\r\n" +
		"select * from MBDEAL_MONTHLY \r\n" +
		"union \r\n" +
		"select * from MBDEAL_QTRLY \r\n" +
		"union \r\n" +
		"select * from CVP_MONTHLY \r\n" +
		"union\r\n" +
		"select * from CVP_QTRLY \r\n" +
		"union\r\n" +
		"select * from RETAIL_ADJ_MONTHLY\r\n" +
		"union\r\n" +
		"select * from RETAIL_ADJ_QTRLY\r\n" +
		"union\r\n" +
		"select * from MBDEAL_ADJ_MONTHLY\r\n" +
		"union\r\n" +
		"select * from MBDEAL_ADJ_QTRLY\r\n" +
		"ORDER BY SEQ\r\n" +
		"with ur\r\n" +
		"";

//ratna End
//Dealer Compliance Summary Report - End

//AMG-Start
public static final String AMG_PERF_CENTER_RPT_NM = "with data(PO_NUM,RETAIL_DTE, ID_DLR, VIN, \r\n" + 
		"		MODEL, MODEL_YR,PROGRAM_TYPE,BONUS,UNEARNED) as (\r\n" + 
		"		SELECT DISTINCT rtl.NUM_PO,\r\n" + 
		"		rtl.DTE_RTL,calc.ID_DLR,RTL.NUM_VIN,\r\n" + 
		"		rtl.des_modl, rtl.DTE_MODL_YR,(case when calc.ID_DPB_PGM = '143' then 'Elite'  else 'Base' end) as PROGRAM_TYPE,\r\n"  +
		"		coalesce(calc.AMT_DPB_BNS_CALC,0), \r\n" + 		
		"		(case when rtl.CDE_VEH_DDR_STS != 'I2' then coalesce(calc.AMT_DPB_UNERND,0)\r\n" + 
		"		else (coalesce(calc.AMT_DPB_UNERND,0)) *-1 end) as UNEARNED\r\n" + 
		"		 from 	DPB_CALC  calc, DPB_UNBLK_VEH  rtl  \r\n" + 
		"		 WHERE calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH  	 			\r\n" + 
		"		and rtl.ind_amg = 'Y'\r\n" + 
		"		and rtl.CDE_VEH_TYP in ('P')\r\n" + 
		"		and calc.ID_DPB_PGM in ('142','143')\r\n" + 
		"		DEALER_RANGE_FOR_REPORTS \r\n" + 
		"		DATE_RANGE_FOR_REPORTS WITH UR)\r\n" + 
		"		select 'Total :  ' as RETAIL_DTE, '' as ID_DLR, '' as PROGRAM_TYPE, '' as MODEL,'' as MODEL_YR,\r\n" + 
		"		'' as VIN,'' as PO_NUM,sum(bonus) as BONUS, sum(unearned) as UNEARNED from data\r\n" + 
		"		union all\r\n" + 
		"		select VARCHAR_FORMAT(RETAIL_DTE,'YYYY-MM-DD') as RETAIL_DTE, ID_DLR, PROGRAM_TYPE, MODEL, MODEL_YR, VIN,PO_NUM, sum(BONUS), sum(UNEARNED) \r\n" + 
		"		 from data group by RETAIL_DTE,ID_DLR, PROGRAM_TYPE, MODEL, MODEL_YR, VIN,PO_NUM order by RETAIL_DTE,ID_DLR,PO_NUM,MODEL FOR FETCH ONLY WITH UR";
//AMG-End


}