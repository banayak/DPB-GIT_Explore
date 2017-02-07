package com.mbusa.dpb.common.constant;

/**
 * 
 * 
 * @author CB5002578
 *
 */
public interface IReportQueryConstants {

	public static final String createReportDefQuery = "";
	public static final String getPaymentDeatilsQuery = "select TXT_PROC_MSG, CDE_DPB_PROC_STS  from CD5001193SCHMA.DPB_PROC_LOG where ID_PROC_DEFN =?";
	public static final String GEN_RPT_QRY = "select rel.ID_DPB_RPT, rel.ID_DPB_RPT_CTNT, rel.ID_DPB_RPT_QRY, rel.ID_DPB_RPT_CTNT_FTR," +
			"rel.NUM_DPB_RPT_SEQ, ctnt.CNT_DPB_RPT_QRY_RSLT_LPP, ctnt.TXT_DPB_RPT_CTNT, qry.TXT_DPB_RPT_QRY, ctntFtr.TXT_DPB_RPT_CTNT as FOOTER " +
			"from DPB_RPT_QRY_CTNT_REL rel left outer join DPB_RPT_CTNT ctnt on rel.ID_DPB_RPT_CTNT = ctnt.ID_DPB_RPT_CTNT " +
			"left outer join DPB_RPT_QRY qry on rel.ID_DPB_RPT_QRY = qry.ID_DPB_RPT_QRY left outer join DPB_RPT_CTNT ctntFtr on rel.ID_DPB_RPT_CTNT_FTR = ctntFtr.ID_DPB_RPT_CTNT " +
			"where rel.ID_DPB_RPT = ? order by rel.NUM_DPB_RPT_SEQ with ur";
	public static final String GEN_RPT_LIST_QRY = "SELECT ID_DPB_RPT,DES_DPB_RPT,NAM_DPB_RPT,CDE_DPB_SCHD FROM DPB_RPT WHERE CDE_DPB_STS = 'A'  and id_dpb_rpt!= 7  order by NAM_DPB_RPT";
	//public static final String BLOCKING_RPT_QRY ="select ID_DLR,NUM_PO,DTS_LAST_UPDT,NUM_VIN,DTE_RTL,ID_DPB_CND from DPB_BLK_VEH where"; 
	public static final String BLOCKING_RPT_QRY ="select BLK.ID_DLR,BLK.NUM_PO,BLK.DTS_LAST_UPDT,BLK.NUM_VIN,BLK.DTE_RTL , "+
												//-- BLK.ID_DPB_CND,TXT_DPB_CHK_VAL,NAM_DPB_VAR,
												" case when trim(NAM_DPB_VAR) = 'CDE_WHSLE_INIT_TYP' then  'Blocked due to the wholesale initiation type is '|| TXT_DPB_CHK_VAL "+ 
												" else case when trim(NAM_DPB_VAR) = 'CDE_RGN' then  'Blocked due to the Code Region is '|| TXT_DPB_CHK_VAL  "+
												" else case when trim(NAM_DPB_VAR) = 'CDE_VEH_DDR_STS' then  'Blocked due to the vehicle status is '|| TXT_DPB_CHK_VAL "+ 
												" else case when trim(NAM_DPB_VAR) = 'IND_FLT' then  'Blocked due to the fleet indicator is '|| TXT_DPB_CHK_VAL  "+
												" else case when trim(NAM_DPB_VAR) = 'IND_USED_VEH_DDRS' then  'Blocked due to the used car indicator is '|| TXT_DPB_CHK_VAL "+ 
												" else  'NA' "+
												" end end end end end as reason , CDE_VEH_DDR_USE"+ 
												" from DPB_BLK_VEH BLK join DPB_CND CD on BLK.ID_DPB_CND = CD.ID_DPB_CND where"; 
	public static final String  LBREPORT_DEFAULT_QRY ="";	
	public static final String  DEFAULT_LBPREPORT_VINS_QUERY =" with RTL_CAL (DTE_CAL, RTL_QTR,NUM_RETL_YR) as (  "+    
															  " select DTE_CAL, NUM_RETL_QTR,NUM_RETL_YR from DPB_DAY dim   "+   
															  "	where dim.DTE_CAL between ? and ? ),   "+
															  "	VEH_FRAN_MAP (VEH_TYP, FRAN_TYP) as (       "+
															  "	values(cast('P' as char(1)), cast('PC' as char(2))) union    "+   
															  "	values(cast('P' as char(1)), cast('MB' as char(2))) union    "+  
															  "	values(cast('P' as char(1)), cast('LT' as char(2))) union    "+ 
															  "	values(cast('S' as char(1)), cast('SM' as char(2))) union      "+
															  "	values(cast('V' as char(1)), cast('SP' as char(2))) union      "+
															  "	values(cast('F' as char(1)), cast('SP' as char(2)))) ,  "+
																 
															  "	DLR_ELG (ID_DLR, RTL_QTR, NUM_VIN,ID_DPB_UNBLK_VEH,CAL_AMT,AMT_ELG,veh_sts) as (        "+
															  "	select distinct elg.ID_DLR, elg.DTE_QTR, rtl.NUM_VIN, rtl.ID_DPB_UNBLK_VEH ,             "+
															  "	calc.AMT_DPB_BNS_CALC,calc.AMT_DPB_MAX_BNS_ELG,rtl.CDE_VEH_DDR_STS       "+
															  "	from DPB_LDR_QTR_FDRT elg, VEH_FRAN_MAP vehRfan, DPB_UNBLK_VEH rtl, RTL_CAL cal , DPB_CALC calc,LDRSP_BNS_PGM pgm        "+
															  "	where elg.IND_DPB_LDR = 'Y'   "+
															  "	and elg.DTE_YR = cal.NUM_RETL_YR "+
															  "	and elg.DTE_QTR = cal.RTL_QTR     "+
															  "	and rtl.DTE_RTL in (cal.DTE_CAL)    "+
															  "	and elg.ID_DLR = rtl.ID_DLR     "+
															  "	and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH   "+
															  "	and vehRfan.VEH_TYP in ('P','S','V','F')      "+  
															  "	and elg.CDE_DLR_FRAN_TYP in (vehRfan.FRAN_TYP)   "+
															  "	and rtl.CDE_VEH_TYP in (vehRfan.VEH_TYP)  "+
															  "	and calc.ID_LDRSP_BNS_PGM is not null   "+
															  "	and pgm.ID_LDRSP_BNS_PGM = calc.ID_LDRSP_BNS_PGM and pgm.CDE_DPB_STS = 'A'),  "+
															  "	CANCEL_VEH (NUM_VIN, ID_DPB_UNBLK_VEH)    as      "+
															  "	(select rtl.NUM_VIN, max (rtl.ID_DPB_UNBLK_VEH)      "+
															  "	from DLR_ELG dlr     "+
															  "	left outer join DPB_UNBLK_VEH rtl on dlr.NUM_VIN = rtl.NUM_VIN     "+
															  "	group by rtl.NUM_VIN ) ,     "+
															  "	final_veh(NUM_VIN) as      "+
															  "	(select cv.NUM_VIN from CANCEL_VEH cv, DPB_UNBLK_VEH rtl      "+
															  "	where rtl.ID_DPB_UNBLK_VEH = cv.ID_DPB_UNBLK_VEH and rtl.CDE_VEH_DDR_STS != 'I2') "+
													
															  "	select elg.NUM_VIN, elg.RTL_QTR, sum(elg.AMT_ELG - abs(elg.CAL_AMT))  "+
														      "	from DLR_ELG elg,final_veh fv  "+
															  "	where elg.NUM_VIN in (fv.NUM_VIN) "+
															  "	group by elg.NUM_VIN, elg.RTL_QTR ";

	public static final String  LBPREPORT_VINS_QUERY =	" with RTL_CAL (DTE_CAL, RTL_QTR,NUM_RETL_YR) as (  "+    
													  " select DTE_CAL, NUM_RETL_QTR,NUM_RETL_YR from DPB_DAY dim   "+   
													  "	where dim.DTE_CAL between ? and ? ),   "+
													  "	VEH_FRAN_MAP (VEH_TYP, FRAN_TYP) as (       "+
													  "	values(cast('P' as char(1)), cast('PC' as char(2))) union    "+   
													  "	values(cast('P' as char(1)), cast('MB' as char(2))) union    "+  
													  "	values(cast('P' as char(1)), cast('LT' as char(2))) union    "+ 
													  "	values(cast('S' as char(1)), cast('SM' as char(2))) union      "+
													  "	values(cast('V' as char(1)), cast('SP' as char(2))) union      "+
													  "	values(cast('F' as char(1)), cast('SP' as char(2)))) ,  "+
														 
													  "	DLR_ELG (ID_DLR, RTL_QTR, NUM_VIN,ID_DPB_UNBLK_VEH,CAL_AMT,AMT_ELG,veh_sts) as (        "+
													  "	select distinct elg.ID_DLR, elg.DTE_QTR, rtl.NUM_VIN, rtl.ID_DPB_UNBLK_VEH ,             "+
													  "	calc.AMT_DPB_BNS_CALC,calc.AMT_DPB_MAX_BNS_ELG,rtl.CDE_VEH_DDR_STS       "+
													  "	from DPB_LDR_QTR_FDRT elg, VEH_FRAN_MAP vehRfan, DPB_UNBLK_VEH rtl, RTL_CAL cal , DPB_CALC calc,LDRSP_BNS_PGM pgm        "+
													  "	where elg.IND_DPB_LDR = 'Y'   "+
													  "	and elg.DTE_YR = cal.NUM_RETL_YR "+
													  "	and elg.DTE_QTR = cal.RTL_QTR     "+
													  "	and rtl.DTE_RTL in (cal.DTE_CAL)    "+
													  "	and elg.ID_DLR = rtl.ID_DLR     "+
													  "	and calc.ID_DPB_UNBLK_VEH = rtl.ID_DPB_UNBLK_VEH   "+
													  "	and vehRfan.VEH_TYP in (?)      "+  
													  "	and elg.CDE_DLR_FRAN_TYP in (vehRfan.FRAN_TYP)   "+
													  "	and rtl.CDE_VEH_TYP in (vehRfan.VEH_TYP)  "+
													  "	and calc.ID_LDRSP_BNS_PGM is not null   "+
													  "	and pgm.ID_LDRSP_BNS_PGM = calc.ID_LDRSP_BNS_PGM and pgm.CDE_DPB_STS = 'A'),  "+
													  "	CANCEL_VEH (NUM_VIN, ID_DPB_UNBLK_VEH)    as      "+
													  "	(select rtl.NUM_VIN, max (rtl.ID_DPB_UNBLK_VEH)      "+
													  "	from DLR_ELG dlr     "+
													  "	left outer join DPB_UNBLK_VEH rtl on dlr.NUM_VIN = rtl.NUM_VIN     "+
													  "	group by rtl.NUM_VIN ) ,     "+
													  "	final_veh(NUM_VIN) as      "+
													  "	(select cv.NUM_VIN from CANCEL_VEH cv, DPB_UNBLK_VEH rtl      "+
													  "	where rtl.ID_DPB_UNBLK_VEH = cv.ID_DPB_UNBLK_VEH and rtl.CDE_VEH_DDR_STS != 'I2') "+
											
													  "	select elg.NUM_VIN, elg.RTL_QTR, sum(elg.AMT_ELG - abs(elg.CAL_AMT))  "+
												      "	from DLR_ELG elg,final_veh fv  "+
													  "	where elg.NUM_VIN in (fv.NUM_VIN) "+
													  "	group by elg.NUM_VIN, elg.RTL_QTR ";

public static final String  DEFAULT_LBPREPORT_DEALER_QUERY="  with VEH_FRAN_MAP (VEH_TYP, FRAN_TYP) as (    "+
														   " values(cast('P' as char(1)), cast('PC' as char(2))) union    "+
														   "  values(cast('P' as char(1)), cast('MB' as char(2))) union   "+
														   " values(cast('P' as char(1)), cast('LT' as char(2))) union     "+
														   " values(cast('S' as char(1)), cast('SM' as char(2))) union    "+
														   " values(cast('V' as char(1)), cast('SP' as char(2))) union    "+
														   " values(cast('F' as char(1)), cast('SP' as char(2)))),  "+
														   "	RTL_CAL (DTE_CAL, RTL_QTR,NUM_RETL_YR) as (       "+
														   "	select DTE_CAL, NUM_RETL_QTR,NUM_RETL_YR from DPB_DAY dim   "+    
														   "	where dim.NUM_RETL_QTR = ? and NUM_RETL_YR = ?), "+
													
														   "	DLR_ELG (ID_DLR, RTL_QTR, NUM_PO,ID_DPB_UNBLK_VEH) as (         "+
														   "	select  elg.ID_DLR, elg.DTE_QTR , rtl.NUM_PO, max(rtl.ID_DPB_UNBLK_VEH )     "+
														   "	from DPB_LDR_QTR_FDRT elg, VEH_FRAN_MAP vehRfan, DPB_UNBLK_VEH rtl, RTL_CAL cal        "+
														   "	where elg.IND_DPB_LDR = 'Y'   "+
														   "	and elg.DTE_YR = cal.NUM_RETL_YR "+
														   "	and elg.DTE_QTR = cal.RTL_QTR      "+
														   "  	and rtl.DTE_RTL in (cal.DTE_CAL)     "+
														   "	and elg.ID_DLR = rtl.ID_DLR      "+
														   "	and vehRfan.VEH_TYP in ('P','S','V','F')         "+
														   "	and elg.CDE_DLR_FRAN_TYP in (vehRfan.FRAN_TYP)   "+
														   "	and rtl.CDE_VEH_TYP in (vehRfan.VEH_TYP)     "+  
														   "	group by elg.ID_DLR, elg.DTE_QTR , rtl.NUM_PO), "+
														   "	DLR_CALC (ID_DLR, RTL_QTR, NUM_PO,ID_DPB_UNBLK_VEH) as ( "+
														   "	select distinct elg.ID_DLR, elg.RTL_QTR, elg.NUM_PO,elg.ID_DPB_UNBLK_VEH "+
														   "	from DLR_ELG elg join DPB_CALC calc "+
														   " 	on calc.ID_DLR = elg.ID_DLR "+
														   "	and calc.ID_DPB_UNBLK_VEH = (elg.ID_DPB_UNBLK_VEH) and ID_LDRSP_BNS_PGM is not null "+
														   "	join LDRSP_BNS_PGM pgm on pgm.ID_LDRSP_BNS_PGM = calc.ID_LDRSP_BNS_PGM and pgm.CDE_DPB_STS = 'A' ), "+
													       "		FINAL(ID_DLR, NUM_PO) as   "+
														   "	  (select dlr.ID_DLR,dlr.NUM_PO  "+
														   "	    from DLR_CALC  dlr, DPB_UNBLK_VEH rtl   "+
														   "	  where rtl.ID_DPB_UNBLK_VEH = dlr.ID_DPB_UNBLK_VEH and rtl.CDE_VEH_DDR_STS != 'I2') "+
														   "	select fi.ID_DLR, dlr.NAM_DLR, fi.NUM_PO  "+
														   "	from FINAL fi "+
														   "	left outer join DEALER_FDRT dlr on fi.ID_DLR = dlr.ID_DLR ";


public static final String  LBPREPORT_DEALER_QUERY=	   "  with VEH_FRAN_MAP (VEH_TYP, FRAN_TYP) as (    "+
													   " values(cast('P' as char(1)), cast('PC' as char(2))) union    "+
													   "  values(cast('P' as char(1)), cast('MB' as char(2))) union   "+
													   " values(cast('P' as char(1)), cast('LT' as char(2))) union     "+
													   " values(cast('S' as char(1)), cast('SM' as char(2))) union    "+
													   " values(cast('V' as char(1)), cast('SP' as char(2))) union    "+
													   " values(cast('F' as char(1)), cast('SP' as char(2)))),  "+
													   "	RTL_CAL (DTE_CAL, RTL_QTR,NUM_RETL_YR) as (       "+
													   "	select DTE_CAL, NUM_RETL_QTR,NUM_RETL_YR from DPB_DAY dim   "+    
													   "	where dim.NUM_RETL_QTR = ? and NUM_RETL_YR = ?), "+
												
													   "	DLR_ELG (ID_DLR, RTL_QTR, NUM_PO,ID_DPB_UNBLK_VEH) as (         "+
													   "	select  elg.ID_DLR, elg.DTE_QTR , rtl.NUM_PO, max(rtl.ID_DPB_UNBLK_VEH )     "+
													   "	from DPB_LDR_QTR_FDRT elg, VEH_FRAN_MAP vehRfan, DPB_UNBLK_VEH rtl, RTL_CAL cal        "+
													   "	where elg.IND_DPB_LDR = 'Y'   "+
													   "	and elg.DTE_YR = cal.NUM_RETL_YR "+
													   "	and elg.DTE_QTR = cal.RTL_QTR      "+
													   "  	and rtl.DTE_RTL in (cal.DTE_CAL)     "+
													   "	and elg.ID_DLR = rtl.ID_DLR      "+
													   "	and vehRfan.VEH_TYP in (?)         "+
													   "	and elg.CDE_DLR_FRAN_TYP in (vehRfan.FRAN_TYP)   "+
													   "	and rtl.CDE_VEH_TYP in (vehRfan.VEH_TYP)     "+  
													   "	group by elg.ID_DLR, elg.DTE_QTR , rtl.NUM_PO), "+
													   "	DLR_CALC (ID_DLR, RTL_QTR, NUM_PO,ID_DPB_UNBLK_VEH) as ( "+
													   "	select distinct elg.ID_DLR, elg.RTL_QTR, elg.NUM_PO,elg.ID_DPB_UNBLK_VEH "+
													   "	from DLR_ELG elg join DPB_CALC calc "+
													   " 	on calc.ID_DLR = elg.ID_DLR "+
													   "	and calc.ID_DPB_UNBLK_VEH = (elg.ID_DPB_UNBLK_VEH) and ID_LDRSP_BNS_PGM is not null "+
													   "	join LDRSP_BNS_PGM pgm on pgm.ID_LDRSP_BNS_PGM = calc.ID_LDRSP_BNS_PGM and pgm.CDE_DPB_STS = 'A' ), "+
												       "		FINAL(ID_DLR, NUM_PO) as   "+
													   "	  (select dlr.ID_DLR,dlr.NUM_PO  "+
													   "	    from DLR_CALC  dlr, DPB_UNBLK_VEH rtl   "+
													   "	  where rtl.ID_DPB_UNBLK_VEH = dlr.ID_DPB_UNBLK_VEH and rtl.CDE_VEH_DDR_STS != 'I2') "+
													   "	select fi.ID_DLR, dlr.NAM_DLR, fi.NUM_PO  "+
													   "	from FINAL fi "+
													   "	left outer join DEALER_FDRT dlr on fi.ID_DLR = dlr.ID_DLR ";

public static final String DEFAULT_TOTVEHREPORT_QUERY =" with VEH_FRAN_MAP (VEH_TYP, FRAN_TYP) as (      "+
													   "	 values(cast('P' as char(1)), cast('PC' as char(2))) union     "+
													   "	 values(cast('P' as char(1)), cast('MB' as char(2))) union   "+
													   "	 values(cast('P' as char(1)), cast('LT' as char(2))) union   "+
													   "	 values(cast('S' as char(1)), cast('SM' as char(2))) union   "+
													   "	 values(cast('V' as char(1)), cast('SP' as char(2))) union    "+
													   "	 values(cast('F' as char(1)), cast('SP' as char(2)))),    "+
													   "	RTL_CAL (DTE_CAL, RTL_QTR,NUM_RETL_YR) as (      "+
													   "	select DTE_CAL, NUM_RETL_QTR,NUM_RETL_YR from DPB_DAY dim      "+
													   "	where dim.NUM_RETL_QTR = ? and NUM_RETL_YR = ? ), "+
														  
													   "	  DLR_ELG (ID_DLR, RTL_QTR, NUM_PO,ID_DPB_UNBLK_VEH) as (        "+ 
													   "	select  elg.ID_DLR, elg.DTE_QTR , rtl.NUM_PO, max(rtl.ID_DPB_UNBLK_VEH )     "+
													   "	from DPB_LDR_QTR_FDRT elg, VEH_FRAN_MAP vehRfan, DPB_UNBLK_VEH rtl, RTL_CAL cal         "+
													   "	where elg.IND_DPB_LDR = 'Y'   "+
													   "	and elg.DTE_YR = cal.NUM_RETL_YR "+
													   "	and elg.DTE_QTR = cal.RTL_QTR     "+
													   "	and rtl.DTE_RTL in (cal.DTE_CAL)    "+
													   "	and elg.ID_DLR = rtl.ID_DLR     "+
													   "	and vehRfan.VEH_TYP in ('P','S','V','F')        "+
													   "	and elg.CDE_DLR_FRAN_TYP in (vehRfan.FRAN_TYP)   "+
													   "	and rtl.CDE_VEH_TYP in (vehRfan.VEH_TYP)       "+
													   "	group by elg.ID_DLR, elg.DTE_QTR , rtl.NUM_PO), "+
													   "	DLR_CALC (ID_DLR, RTL_QTR, NUM_PO,ID_DPB_UNBLK_VEH) as ( "+
													   "	select distinct elg.ID_DLR, elg.RTL_QTR, elg.NUM_PO,elg.ID_DPB_UNBLK_VEH  "+
													   "	from DLR_ELG elg join DPB_CALC calc  "+
													   "	on calc.ID_DLR = elg.ID_DLR  "+
													   "	and calc.ID_DPB_UNBLK_VEH = (elg.ID_DPB_UNBLK_VEH) and ID_LDRSP_BNS_PGM is not null  "+
													   "	join LDRSP_BNS_PGM pgm on pgm.ID_LDRSP_BNS_PGM = calc.ID_LDRSP_BNS_PGM and pgm.CDE_DPB_STS = 'A'), "+
													   "	FINAL(ID_DLR, NUM_PO) as   "+
													   "	  (select dlr.ID_DLR,dlr.NUM_PO  "+
													   "	    from DLR_CALC  dlr, DPB_UNBLK_VEH rtl   "+
													   "	  where rtl.ID_DPB_UNBLK_VEH = dlr.ID_DPB_UNBLK_VEH and rtl.CDE_VEH_DDR_STS != 'I2')   "+
														  
													   "	select fi.ID_DLR, dlr.NAM_DLR, count(fi.NUM_PO) as COUNT_NUM_PO from FINAL fi "+
													   "	left outer join DEALER_FDRT dlr on fi.ID_DLR = dlr.ID_DLR group by fi.ID_DLR, dlr.NAM_DLR     "+
													   "	  union    "+
													   "	  select null, null, count(NUM_PO) from FINAL ";



public static final String TOTVEH_REPORT_QUERY = " with VEH_FRAN_MAP (VEH_TYP, FRAN_TYP) as (      "+
											   "	 values(cast('P' as char(1)), cast('PC' as char(2))) union     "+
											   "	 values(cast('P' as char(1)), cast('MB' as char(2))) union   "+
											   "	 values(cast('P' as char(1)), cast('LT' as char(2))) union   "+
											   "	 values(cast('S' as char(1)), cast('SM' as char(2))) union   "+
											   "	 values(cast('V' as char(1)), cast('SP' as char(2))) union    "+
											   "	 values(cast('F' as char(1)), cast('SP' as char(2)))),    "+
											   "	RTL_CAL (DTE_CAL, RTL_QTR,NUM_RETL_YR) as (      "+
											   "	select DTE_CAL, NUM_RETL_QTR,NUM_RETL_YR from DPB_DAY dim      "+
											   "	where dim.NUM_RETL_QTR = ? and NUM_RETL_YR = ? ), "+
												  
											   "	  DLR_ELG (ID_DLR, RTL_QTR, NUM_PO,ID_DPB_UNBLK_VEH) as (        "+ 
											   "	select  elg.ID_DLR, elg.DTE_QTR , rtl.NUM_PO, max(rtl.ID_DPB_UNBLK_VEH )     "+
											   "	from DPB_LDR_QTR_FDRT elg, VEH_FRAN_MAP vehRfan, DPB_UNBLK_VEH rtl, RTL_CAL cal         "+
											   "	where elg.IND_DPB_LDR = 'Y'   "+
											   "	and elg.DTE_YR = cal.NUM_RETL_YR "+
											   "	and elg.DTE_QTR = cal.RTL_QTR     "+
											   "	and rtl.DTE_RTL in (cal.DTE_CAL)    "+
											   "	and elg.ID_DLR = rtl.ID_DLR     "+
											   "	and vehRfan.VEH_TYP in (?)        "+
											   "	and elg.CDE_DLR_FRAN_TYP in (vehRfan.FRAN_TYP)   "+
											   "	and rtl.CDE_VEH_TYP in (vehRfan.VEH_TYP)       "+
											   "	group by elg.ID_DLR, elg.DTE_QTR , rtl.NUM_PO), "+
											   "	DLR_CALC (ID_DLR, RTL_QTR, NUM_PO,ID_DPB_UNBLK_VEH) as ( "+
											   "	select distinct elg.ID_DLR, elg.RTL_QTR, elg.NUM_PO,elg.ID_DPB_UNBLK_VEH  "+
											   "	from DLR_ELG elg join DPB_CALC calc  "+
											   "	on calc.ID_DLR = elg.ID_DLR  "+
											   "	and calc.ID_DPB_UNBLK_VEH = (elg.ID_DPB_UNBLK_VEH) and ID_LDRSP_BNS_PGM is not null  "+
											   "	join LDRSP_BNS_PGM pgm on pgm.ID_LDRSP_BNS_PGM = calc.ID_LDRSP_BNS_PGM and pgm.CDE_DPB_STS = 'A'), "+
											   "	FINAL(ID_DLR, NUM_PO) as   "+
											   "	  (select dlr.ID_DLR,dlr.NUM_PO  "+
											   "	    from DLR_CALC  dlr, DPB_UNBLK_VEH rtl   "+
											   "	  where rtl.ID_DPB_UNBLK_VEH = dlr.ID_DPB_UNBLK_VEH and rtl.CDE_VEH_DDR_STS != 'I2')   "+
												  
											   "	select fi.ID_DLR, dlr.NAM_DLR, count(fi.NUM_PO) as COUNT_NUM_PO from FINAL fi "+
											   "	left outer join DEALER_FDRT dlr on fi.ID_DLR = dlr.ID_DLR group by fi.ID_DLR, dlr.NAM_DLR     "+
											   "	  union    "+
											   "	  select null, null, count(NUM_PO) from FINAL ";

public static final String DLR_RESERVE_EXCEPTION_RPT_QRY =" SELECT cast(VARCHAR_FORMAT(DTE_RTL,'MM/DD/YYYY') as  char(12)) AS DTE_RTL," +
															" cast(SUBSTRING(DTE_MDL_YR,3,3,OCTETS) as char(7)) DTE_MDL_YR," + 
															" cast(TXT_MODL as char(9)) TXT_MODL ," +
															" cast(SUBSTRING(NUM_VIN,11,16,OCTETS) as char(10)) as NUM_VIN," +
															" cast(NUM_PO as char(13)) NUM_PO," +
															" cast(ID_DLR as char(10)) ID_DLR, " +
															" cast(CDE_RGN as char(10)) CDE_RGN, " +
															" CASE WHEN CDE_VEH_STS = 'I2' THEN cast('1-' as char(10)) ELSE  cast('1' as char(10))  END AS VEH_CNT," +
															" CASE WHEN ID_DPB_PGM is not null AND CDE_USE like'L%' AND CDE_VEH_STS != 'I2' THEN 'ALT TRANS SALE' ||'-'|| CDE_USE ELSE " +
															" CASE WHEN ID_DPB_PGM is not null AND CDE_USE like'L%' AND CDE_VEH_STS = 'I2' THEN 'ALT TRANS SALE(CANCEL)' ||'-'|| CDE_USE ELSE  " +
															" CASE WHEN ID_DPB_PGM is not null AND (CDE_USE like'ER' OR  CDE_USE like'DP')  AND CDE_VEH_STS != 'I2' THEN 'FAMILY MEM' ||'-'|| CDE_USE ELSE 'FAMILY MEM (CANCEL)' ||'-'|| CDE_USE " + 
															" END END END AS REASON,  " +
															" ID_DPB_PGM " +
															" FROM  DPB_VEH_RTL_EXTRT WHERE ID_DPB_PGM  >0";
public static final String DLR_RESERVE_SUM_RPT_QRY = "with GEN_PGM (PGM_TYP, YR, MNT, PGM_ID, AMT_CALC) as (select 'GEN',(select year(DTE_CAL) from DPB_RTL_CAL_DY_DIM dim where dim.ID_DAY = cal.ID_DAY),(select month(DTE_CAL) from DPB_RTL_CAL_DY_DIM dim where dim.ID_DAY = cal.ID_DAY),cal.ID_PGM, cal.AMT_DPB_BNS_CALC from DPB_CALC cal, DPB_PGM pgm, DPB_VEH_RTL_EXTRT rtl where IND_LDRSP_BNS_PGM = 'N' and cal.ID_DAY in (select dim.ID_DAY from DPB_RTL_CAL_DY_DIM dim where dim.NUM_RETL_MTH = 4 ) and cal.ID_PGM = pgm.ID_DPB_PGM and pgm.IND_SPL_DPB_PGM = 'N' and rtl.NUM_PO = cal.NUM_PO and rtl.CDE_VEH_TYP in ('S')), MVP_PGM (PGM_TYP, YR, MNT, PGM_ID, AMT_CALC) as (select 'MBDEAL',(select year(DTE_CAL) from DPB_RTL_CAL_DY_DIM dim where dim.ID_DAY = cal.ID_DAY),(select month(DTE_CAL) from DPB_RTL_CAL_DY_DIM dim where dim.ID_DAY = cal.ID_DAY), cal.ID_PGM, cal.AMT_DPB_BNS_CALC from DPB_CALC cal, DPB_PGM pgm, DPB_VEH_RTL_EXTRT rtl where IND_LDRSP_BNS_PGM = 'N' and cal.ID_DAY in (select dim.ID_DAY from DPB_RTL_CAL_DY_DIM dim where dim.NUM_RETL_MTH = 4 and dim.NUM_RETL_YR = 2013) and cal.ID_PGM = pgm.ID_DPB_PGM and pgm.IND_SPL_DPB_PGM = 'Y' and rtl.NUM_PO = cal.NUM_PO and rtl.CDE_VEH_TYP in ('S')),CVP_PGM (PGM_TYP, YR, MNT, PGM_ID, AMT_CALC) as (select 'CVP',(select year(DTE_CAL) from DPB_RTL_CAL_DY_DIM dim where dim.ID_DAY = cal.ID_DAY),(select month(DTE_CAL) from DPB_RTL_CAL_DY_DIM dim where dim.ID_DAY = cal.ID_DAY), cal.ID_PGM, cal.AMT_DPB_BNS_CALC from DPB_CALC cal, DPB_PGM pgm, DPB_VEH_RTL_EXTRT rtl where IND_LDRSP_BNS_PGM = 'N' and cal.ID_DAY in (select dim.ID_DAY from DPB_RTL_CAL_DY_DIM dim where dim.NUM_RETL_MTH = 4 and dim.NUM_RETL_YR = 2013) and cal.ID_PGM = pgm.ID_DPB_PGM and pgm.IND_SPL_DPB_PGM = 'Y' and rtl.NUM_PO = cal.NUM_PO and rtl.CDE_VEH_TYP in ('S')),ALL_TEMP (PGM_TYP, YR, PGM_ID, AMT_CALC) as (select PGM_TYP, YR, count(PGM_ID), sum(AMT_CALC) from GEN_PGM group by (PGM_TYP, YR) union select PGM_TYP, YR, count(PGM_ID), sum(AMT_CALC) from MVP_PGM group by (PGM_TYP, YR) union select PGM_TYP, YR, count(PGM_ID), sum(AMT_CALC) from CVP_PGM group by (PGM_TYP, YR)),SUM_BY_PGM (PGM_TYP,YR, PGM_ID, AMT_CALC_TOTAL) as (select PGM_TYP,999999999, sum(PGM_ID), sum(AMT_CALC) from ALL_TEMP group by rollup (PGM_TYP)),COMPLETE_PGM (PGM_TYP, YR, PGM_ID, AMT_CALC) as (select PGM_TYP, YR, PGM_ID, AMT_CALC from ALL_TEMP union select PGM_TYP, YR, PGM_ID, AMT_CALC_TOTAL from SUM_BY_PGM where PGM_TYP is not null) select * from COMPLETE_PGM union select 'SUB_TOT1', YR, sum(PGM_ID), sum(AMT_CALC) from COMPLETE_PGM where PGM_TYP in ('GEN','MBDEAL') group by (YR)";

public static final String FETCH_QUERY_BY_NAME = "select TXT_DPB_RPT_QRY from DPB_RPT_QRY where NAM_DPB_RPT_QRY = ? and CDE_DPB_STS = 'A' with ur";
public static final String FETCH_QUERY_BY_NAME_ALL = "select NAM_DPB_RPT_QRY,TXT_DPB_RPT_QRY from DPB_RPT_QRY where CDE_DPB_STS = 'A' AND NAM_DPB_RPT_QRY in ";
public static final String FETCH_RPT_DATA_BY_PROC_ID = "select rpt.ID_DPB_RPT,rpt.NAM_DPB_RPT, rpt.CDE_DPB_SCHD from DPB_RPT rpt left join DPB_PROC proc on rpt.ID_DPB_RPT = proc.ID_DPB_RPT where ID_DPB_PROC = ? with ur";
public static final String FETCH_REPORT_LPP = "select QTY_DPB_RPT_LPP, CDE_DPB_SCHD from DPB_RPT where ID_DPB_RPT = ? with ur";

	public static final String GET_RTL_DTS_FOR_YR_MNTH = "select max(dte_retl_mth_beg) as dte_retl_mth_beg,max(dte_retl_mth_end) as dte_retl_mth_end,"+ 
			"num_retl_yr,num_retl_mth from dpbuser.dpb_day "+
			"where (num_retl_yr = ? and num_retl_mth = ?) or "+ 
			"(num_retl_yr = ? and num_retl_mth = ?) "+ 
			"group by (num_retl_yr,num_retl_mth ) order by num_retl_yr for fetch only with ur";
	public static final String GET_RTL_DTS_FOR_YR_QTR = "select max(dte_retl_qtr_beg) as dte_retl_qtr_beg,max(dte_retl_qtr_end) as dte_retl_qtr_end,"+ 
			"num_retl_yr,num_retl_qtr from dpbuser.dpb_day "+
			"where (num_retl_yr = ? and num_retl_qtr = ?) or "+ 
			"(num_retl_yr = ? and num_retl_qtr = ?) "+ 
			"group by (num_retl_yr,num_retl_qtr ) order by num_retl_yr for fetch only with ur";
	
	/*DCQPR Start - Performance Improvement 10/06/2016*/
	public static final String DCQPR_DEALER_UNBLK_CARS="select DISTINCT dlr.ID_DLR as dealer from DEALER_FDRT dlr  \r\n" + 
			" WHERE ID_DLR IN (Select distinct id_dlr from DPBUSER.DPB_UNBLK_VEH where CDE_VEH_TYP = ?) with ur";
	/*DCQPR End - Performance Improvement 10/06/2016*/
	public static final String VIN_Lookup_REPORT ="SELECT VEH.NUM_VIN AS VIN, VEH.NUM_PO AS, CAL.ID_DLR AS DEALER,"+
			  " CAST(SUM(CASE WHEN (VEH.CDE_VEH_DDR_STS = 'I2') THEN ABS(CAL.AMT_DPB_MAX_BNS_ELG) * -1 ELSE CASE WHEN (CAL.IND_DPB_ADJ = 'Y') THEN ABS(CAL.AMT_DPB_MAX_BNS_ELG) * 0 ELSE CAL.AMT_DPB_MAX_BNS_ELG END END)AS INT),"+
			  " CAST(SUM(CASE WHEN (VEH.CDE_VEH_DDR_STS = 'I2') THEN ABS(CAL.AMT_DPB_BNS_CALC) * -1 ELSE CAL.AMT_DPB_BNS_CALC END)AS INT)"+
			  " FROM DPBUSER.DPB_CALC AS CAL, DPBUSER.DPB_UNBLK_VEH AS VEH"+
			  " WHERE VEH.ID_DPB_UNBLK_VEH = CAL.ID_DPB_UNBLK_VEH"+
			  " AND CAL.ID_DPB_PGM IN (46, 144, 145, 48, 49, 50, 57, 58, 59, 52, 60,61, 62, 53, 51, 141, 121, 123)";
}
