/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: VistaFileProcessingHelper.java
 * Program Version			: 1.0
 * Program Description		: This Class is used for Vista file reading.
 * 
 * Modification History		: 
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * 
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.business.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.business.view.DpbCommonBeanLocal;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.ConditionDefinition;
import com.mbusa.dpb.common.domain.FieldColumnMapBean;
import com.mbusa.dpb.common.domain.FileFormatBean;
import com.mbusa.dpb.common.domain.ProgramDefinitionBean;
import com.mbusa.dpb.common.domain.VehicleConditions;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.helper.FileProcessingHelperAbstract;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.DPBCommonHelper;
//import com.mbusa.dpb.business.util.SpecialBonusProgram.VISTA_FILE_ENUM;

/**
 * 
 * @author SK5008848
 * @version 1.0
 * 
 */

public class VistaFileProcessingHelper extends FileProcessingHelperAbstract {

	private static DPBLog LOGGER = DPBLog.getInstance(VistaFileProcessingHelper.class);
	static final private String CLASSNAME = VistaFileProcessingHelper.class.getName();
	
	List<String> failedLines = null;
	List<VistaFileProcessing> failedRecords = null;
	List<VistaFileProcessing> validRecords = null;
	List<VistaFileProcessing> blockedRecords = null;
	List<String> ignoreRecords = null;
	List<VehicleConditions> vehConditions = null;
	List<VehicleConditions> blockConditions = null;
	List<FieldColumnMapBean> fileMapping = null;
	Map<Integer,List<ConditionDefinition>> specialConditions = null;
	List<ProgramDefinitionBean> specialPrograms = null;
	DpbCommonBeanLocal commonBean = null;
	DPBCommonHelper commonHelper = null;
	Map<String, List<VehicleConditions>> vehTypConditionsMap = null;
	Map<String,String> dlrIds = null;
	private LocalServiceFactory local = new LocalServiceFactory();

	/**
	 * @Description Used for reading file and process records
	 * @param formatBean
	 * @param inProcessPath
	 * @return
	 * @throws Exception
	 */
	public boolean processVistaFile(String inProcessPath,
			FileFormatBean formatBean, List<VistaFileProcessing> validRecords,
			List<VistaFileProcessing> blockedRecords, List<VistaFileProcessing> failedRecords, 
			List<String> failedLines,int processID, String userId,List<String> ignoreRecords)
			throws Exception {

		final String methodName = "processFile";
		LOGGER.enter(CLASSNAME, methodName);

		VistaFileProcessing fileBean = null;
		BufferedReader reader = null;
		String delimiter = formatBean.getIndDelimited();
		String line = null;
		String lineHeader = null;
		boolean isFailed = false;
		try {
			commonBean = local.getDpbCommonService();
			vehConditions = commonBean
					.getVehicleConditions(IConstants.VEHICLE_CONDITION);
			
			vehTypConditionsMap = createVehTypConditionsMap(vehConditions);
			blockConditions = commonBean
					.getVehicleConditions(IConstants.BLOCK_CONDITION);
			specialPrograms = commonBean.getSpecialPrograms();
			specialConditions = commonBean.getSpecialConditions(specialPrograms);
			dlrIds = MasterDataLookup.getInstance().getDealerList();
			fileMapping = formatBean.getFileMapingList();
			commonHelper = new DPBCommonHelper();

			this.validRecords = validRecords;
			this.blockedRecords = blockedRecords;
			this.failedLines = failedLines;
			this.failedRecords = failedRecords;
			this.ignoreRecords = ignoreRecords;

			File fileInput = new File(inProcessPath);
			if (fileInput.exists() && fileInput.canRead()) {
				reader = new BufferedReader(new FileReader(fileInput));
				for (int count = 1; (line = reader.readLine()) != null; count++) {
					lineHeader = formatBean.getIndHeader();
					if (lineHeader != null && IConstants.CONSTANT_Y.equalsIgnoreCase(lineHeader)
							&& count == 1) {
						continue;
					}
					fileBean = new VistaFileProcessing();
					fileBean.setDpbProcessId(processID);
					fileBean.setLineString(line);
					fileBean.setLineNumber(count);
					fileBean.setCreatedUserId(userId);
					fileBean.setLastUpdtUserId(userId);
					processRecord(delimiter, fileBean);

				}
				if (failedRecords.size() > 0) {
					isFailed = true;
					LOGGER.info("Vista file reading failed, No.of records failed-"+failedRecords.size());
				}
			}
		}
		finally {
			if (reader != null)
				reader.close();
		}
		LOGGER.exit(CLASSNAME, methodName);
		return isFailed;
	}

	/**
	 * @Description Used for Processing records from file
	 * @param line
	 * @param fileMapping
	 * @param delimiter
	 * @return
	 */
	public void processRecord(String delimiter,
			VistaFileProcessing fileProcessing) throws Exception {

		/*final String methodName = "processVistRecord";
		LOGGER.enter(CLASSNAME, methodName);*/

		try{
			if (delimiter != null && delimiter.length() > 0) {
			processRecordWithDelimiter(delimiter, fileProcessing);
			} else {
			processRecordWithNoDelimiter(delimiter, fileProcessing);
			}
		}
		catch(StringIndexOutOfBoundsException s){
			LOGGER.error("Exception occured while reading file content.");
			fileProcessing.setReason("Empty spaces expected at the end of the line");
			failedRecords.add(fileProcessing);
			failedLines.add(fileProcessing.getLineString());
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
	}

	/**
	 * @Description Processing file based on No Delimiter indicator
	 * @param line
	 * @param fileMapping
	 * @return
	 * @throws Exception
	 */
	private void processRecordWithNoDelimiter(String delimiter,
			VistaFileProcessing fileProcessing) throws Exception {

		/*final String methodName = "processRecordWithNoDelimiter";
		LOGGER.enter(CLASSNAME, methodName);*/

		String columnName = "";
		String columnFormat = "";
		int length = 0;
		String line = fileProcessing.getLineString();
		int subStringFrom = 0;
		String data = "";
		String vehType = IConstants.PCAR;
		Integer progType = null;
		/*String tempVehType = "";
		boolean isFailed = false;*/
		boolean dlrExists = false;
		Map<String, Map<Integer,Object>> columnMap = new HashMap<String, Map<Integer,Object>>();  

		for (FieldColumnMapBean columnMapBean : fileMapping) {
			columnName = columnMapBean.getColumnName();
			columnFormat = columnMapBean.getFileColumnformatText();
			length = columnMapBean.getFileColumnLength();
			Map<Integer, Object> columnMeta = new HashMap<Integer, Object>(1);
			switch (IConstants.VISTA_FILE_ENUM.valueOf(columnName.trim())) {
			case DTE_RTL:
				data = line.substring(subStringFrom, length + subStringFrom);
				
				if (checkFormat(columnFormat, data, 4)) {
					Date sqlDate = getSqlDate(data, columnFormat);
					if (sqlDate == null) {
						LOGGER.error("Invalid format for Retail Date");
						fileProcessing.setReason("Invalid format for Retail Date");
						failedRecords.add(fileProcessing);
						failedLines.add(fileProcessing.getLineString());
						return;
					} else {
						fileProcessing.setRetailDate(sqlDate);
						columnMeta.put(2, sqlDate);
						columnMap.put(columnName.trim(), columnMeta);
					}
					subStringFrom = subStringFrom + length;
				} else {
					LOGGER.error("Invalid Retail Date");
					fileProcessing.setReason("Invalid Retail Date");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("DTE_RTL", data,
						vehConditions, 2);*/
				
				
				break;

			case TME_RTL:
				data = line.substring(subStringFrom, length + subStringFrom);
				Time rtlTime = checkTimeFormat(data.trim());
				if (rtlTime != null) {
					fileProcessing.setRetailTime(rtlTime);
					subStringFrom = subStringFrom + length;
					columnMeta.put(4, rtlTime);
					columnMap.put(columnName.trim(), columnMeta);
				} 
				 else {
					LOGGER.error("Invalid Retail Time");
					fileProcessing.setReason("Invalid Retail Time");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("TME_RTL", data,
						vehConditions, 4);*/
				break;
			case CDE_VEH_STS:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setVehStatusCode(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Vehicle Status Code");
					fileProcessing.setReason("Invalid Vehicle Status Code");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_VEH_STS", data,
						vehConditions, 0);*/
				break;
			case CDE_VEH_TYP:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setVehTypeCode(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Vehicle Type Code");
					fileProcessing.setReason("Invalid Vehicle Type Code");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_VEH_TYP", data,
						vehConditions, 0);*/
				break;
			case CDE_NATL_TYPE:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setNationalTypeCode(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Vehicle National Type Code");
					fileProcessing.setReason("Invalid Vehicle National Type Code");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_NATL_TYPE", data,
						vehConditions, 0);*/
				break;

			case CDE_USE_VEH:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setUseCode(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Use Code");
					fileProcessing.setReason("Invalid Use Code");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_USE", data,
						vehConditions, 0);*/
				break;
			case NUM_PO:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setPoNo(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid PO Number");
					fileProcessing.setReason("Invalid PO Number");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("NUM_PO", data,
						vehConditions, 0);*/
				break;
			case NUM_VIN:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setVinNum(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid VIN Number");
					fileProcessing.setReason("Invalid VIN Number");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("NUM_VIN", data,
						vehConditions, 0);*/
				break;

			case ID_DLR:
				data = line.substring(subStringFrom, length + subStringFrom);
				dlrExists = dlrIds.containsKey(data!=null?data.trim():"");
				if(dlrExists){	
					if (checkFormat(columnFormat, data, 1)) {
						fileProcessing.setDealerId(data);
						subStringFrom = subStringFrom + length;
						columnMeta.put(0, data);
						columnMap.put(columnName.trim(), columnMeta);
					} else {
						LOGGER.error("Invalid Dealer Id");
						fileProcessing.setReason("Invalid Dealer Id");
						failedRecords.add(fileProcessing);
						failedLines.add(fileProcessing.getLineString());
						return;
					}
				}else {
					ignoreRecords.add(fileProcessing.getLineString());
					LOGGER.info("Redord ignored due to invalid Dealer Id-"+data+" at Line No-"+fileProcessing.getLineNumber());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("ID_DLR", data,
						vehConditions, 0);*/
				break;
			case IND_USED_VEH:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setUsedCarIndicator(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Used Car Indicator");
					fileProcessing.setReason("Invalid Used Car Indicator");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("IND_USED_CAR", data,
						vehConditions, 0);*/
				break;
			case ID_EMP_PUR_CTRL:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					//fileProcessing.setEmpPurCtrlId(data != null && data.trim().length()> 0 ? Integer.parseInt(data):0);
					fileProcessing.setEmpPurCtrlId(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Emp Purchase ID");
					fileProcessing.setReason("Invalid Emp Purchase ID");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("ID_EMP_PUR_CTRL", data,
						vehConditions, 1);*/
				break;
			case DTE_MODL_YR:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setModelYearDate(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(1, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Model Year Date");
					fileProcessing.setReason("Invalid Model Year Date");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("DTE_MDL_YR", data,
						vehConditions, 1);*/
				break;
			case DES_MODL:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setModelText((data));
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Model Name");
					fileProcessing.setReason("Invalid Model Name");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("TXT_MODL", data,
						vehConditions, 0);*/
				break;
			case CDE_RGN:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setRegionCode(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Region Code");
					fileProcessing.setReason("Invalid Region Code");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_RGN", data,
						vehConditions, 0);*/
				break;
			case NAM_RTL_CUS_LST:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setRetailCustLastName(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Last Name");
					fileProcessing.setReason("Invalid Last Name");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("NAM_RTL_CUS_LST", data,
						vehConditions, 0);*/
				break;
			case NAM_RTL_CUS_FIR:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setRetailCustFirstName(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid First Name");
					fileProcessing.setReason("Invalid First Name");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("NAM_RTL_CUS_FIR", data,
						vehConditions, 0);*/
				break;
			case NAM_RTL_CUS_MID:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setRetailCustMiddleName(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Middle Name");
					fileProcessing.setReason("Invalid Middle Name");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("NAM_RTL_CUS_MID", data,
						vehConditions, 0);*/
				break;
			case TME_TRANS:
				data = line.substring(subStringFrom, length + subStringFrom);
				Time transTime = checkTimeFormat(data.trim());
				//if (transTime != null) { column is nullable so not checking for null
					fileProcessing.setTransTime(transTime);
					subStringFrom = subStringFrom + length;
				/*}  else {
					LOGGER.error("Invalid Transaction Time");
					fileProcessing.setReason("Invalid Transaction Time");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}*/
				/*tempVehType = commonHelper.getVehicleType("TME_TRANS", data,
						vehConditions, 4);*/
				columnMeta.put(4, transTime);
				columnMap.put(columnName.trim(), columnMeta);
				break;
			case DTE_TRANS:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 4)) {
					Date sqlDate = getSqlDate(data, columnFormat);
					fileProcessing.setTransDate(sqlDate);
					subStringFrom = subStringFrom + length;
					columnMeta.put(2, sqlDate);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Transaction Date");
					fileProcessing.setReason("Invalid Transaction Date");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("DTE_TRANS", data,
						vehConditions, 2);*/
				break;
			case AMT_MSRP_BASE:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 5)) {
					fileProcessing.setMsrpBaseAmount(getPercentageValue(data));
					subStringFrom = subStringFrom + length;
					columnMeta.put(3, getPercentageValue(data));
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid MSRP Base Amount");
					fileProcessing.setReason("Invalid MSRP Base Amount");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("AMT_MSRP_BASE", data,
						vehConditions, 3);*/
				break;
			case AMT_MSRP_TOT_ACSRY:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 5)) {
					fileProcessing.setMsrpTotAmtAcsry(getPercentageValue(data));
					subStringFrom = subStringFrom + length;
					columnMeta.put(3, getPercentageValue(data));
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid MSRP Total Acsry");
					fileProcessing
							.setReason("Invalid MSRP Total Acsry");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("AMT_MSRP_TOT_ACSRY", data,
						vehConditions, 3);*/
				break;
			case AMT_DLR_RBT:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 5)) {
					fileProcessing.setDlrRebateAmt(getPercentageValue(data));
					subStringFrom = subStringFrom + length;
					columnMeta.put(3, getPercentageValue(data));
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Rebate Amount");
					fileProcessing.setReason("Invalid Rebate Amount");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("AMT_DLR_RBT", data,
						vehConditions, 3);*/
				break;
			case IND_FLT:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setFleetIndicator(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Fleet Indicator");
					fileProcessing.setReason("Invalid Fleet Indicator");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("IND_FLT", data,
						vehConditions, 0);*/
				break;

			case CDE_WHSLE_INIT_TYP:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setWholeSaleInitType(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Wholesale Initiation Type");
					fileProcessing
							.setReason("Invalid Wholesale Initiation Type");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_WHSLE_INIT_TYP", data,
						vehConditions, 0);*/
				break;
			case CDE_VEH_DDR_USE:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setSalesTypeCode(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Sales Type");
					fileProcessing
							.setReason("Invalid Sales Type");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_SLE_TYP",
						data, vehConditions, 0);*/
				break;
			case DTE_VEH_DEMO_SRV:
				data = line.substring(subStringFrom, length + subStringFrom);
				/*if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setDemoServiceDate(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				}*/ 
				if (checkFormat(columnFormat, data, 4)) {
					Date sqlDate = getSqlDate(data, columnFormat);
					fileProcessing.setDemoServiceDate(sqlDate);
					subStringFrom = subStringFrom + length;
					columnMeta.put(2, sqlDate);
					columnMap.put(columnName.trim(), columnMeta);
				}else {
					LOGGER.error("Invalid Demo Service Date");
					fileProcessing
							.setReason("Invalid Demo Service Date");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("DTE_DEMO_SRV",
						data, vehConditions, 0);*/
				break;
			case CDE_VEH_DDR_STS:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setCarStatusCode(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Car Status Code");
					fileProcessing
							.setReason("Invalid Car Status Code");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_CAR_STS",
						data, vehConditions, 0);*/
				break;
			case IND_USED_VEH_DDRS:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setUsedCarIndicator2(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Used Car Indicator - 2");
					fileProcessing
							.setReason("Invalid Used Car Indicator - 2");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("IND_USED_CAR2",
						data, vehConditions, 0);*/
				break;
			case AMT_MSRP:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 5)) {
					fileProcessing.setMsrpTotalsAmt(getPercentageValue(data));
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, getPercentageValue(data));
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid MSRP Total Amount");
					fileProcessing
							.setReason("Invalid MSRP Total Amount");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("AMT_MSRP_OPTS",
						data, vehConditions, 0);*/
				break;
			// AMG Changes - START
			/*case IND_AMG:
				data = line.substring(subStringFrom, length + subStringFrom);
				if (checkFormat(columnFormat, data, 1)) {
					fileProcessing.setIndAmg(data);
					subStringFrom = subStringFrom + length;
					columnMeta.put(0, data);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid AMG Indicator");
					fileProcessing.setReason("Invalid AMG Indicator");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				break;
			// AMG Changes - END
			 	*/			
				case FILLER_COLUMN:
				subStringFrom = subStringFrom + length;
				break;
			}
			/*if ("FAIL".equalsIgnoreCase(tempVehType)) {
				isFailed = true;
			} else if (!"".equalsIgnoreCase(tempVehType)) {
				vehType = tempVehType;
			}*/

		}
		String indAmg = getAmgInd(fileProcessing.getModelText());
		fileProcessing.setIndAmg(indAmg);
				
		//if (isFailed) {
			//LOGGER.debug("Vehicle condition failed. Setting default vehicle type.");
			//fileProcessing.setVehTypeCode(IConstants.PCAR);
		//} else {
			
			vehType = commonHelper.retrieveVehicleType(columnMap, vehTypConditionsMap);
			fileProcessing.setVehTypeCode(vehType);
		//}
		if (!checkBlockCond(fileProcessing)) {
			progType = checkSpecialConditions(specialPrograms,specialConditions,fileProcessing);
			fileProcessing.setProgType(progType);
			LOGGER.info("Success Line No-"+fileProcessing.getLineNumber()+", Po.No-"+
			fileProcessing.getPoNo()+", Use code-"+fileProcessing.getUseCode()+"Vehicle Type-"+
					fileProcessing.getVehTypeCode());
			validRecords.add(fileProcessing);
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
	}
	// AMG Changes - START
		/**
		 * Checking from the AMG model list defined in the property file to determine whether AMG or not.
		 * @param model
		 * @return indAmg
		 */
		private String getAmgInd(String model) {
			String indAmg = "N";
			
			String amgModels = PropertyManager.getPropertyManager().getPropertyAsString("dpb.veh.model.amg.list");
			if (amgModels != null && !amgModels.isEmpty()) {
				String[] amgModelArray = amgModels.split(",");
				
				if (amgModelArray != null && amgModelArray.length > 0) {
					List<String> amgModelList = Arrays.asList(amgModelArray);
					
					if (model != null && !model.isEmpty()) {
						model = model.trim();
						if (amgModelList != null && !amgModelList.isEmpty()) {
							indAmg = (amgModelList.contains(model) ? "Y" : "N");
						}
					}
				}
			}
			
			return indAmg;
		}
		// AMG Changes - END
	
	private Map<String, List<VehicleConditions>> createVehTypConditionsMap(List<VehicleConditions> vehConditions) {
		Map<String, List<VehicleConditions>> vehTypConditionsMap = new HashMap<String, List<VehicleConditions>>();
		Iterator<VehicleConditions> vehCondIter = vehConditions.iterator();
		while(vehCondIter.hasNext()){
			VehicleConditions vehCondition = vehCondIter.next();
			if(vehTypConditionsMap.containsKey(vehCondition.getVehType())){
				vehTypConditionsMap.get(vehCondition.getVehType()).add(vehCondition);
			}
			else{
				List<VehicleConditions> selVehConditions = new ArrayList<VehicleConditions>();
				selVehConditions.add(vehCondition);
				vehTypConditionsMap.put(vehCondition.getVehType(), selVehConditions);
			}
		}
		return vehTypConditionsMap;
	}

	/**
	 * @Description Processing file based on Delimiter indicator
	 * @param line
	 * @param fileMapping
	 * @param delimiter
	 * @return
	 * @throws Exception
	 */
	private void processRecordWithDelimiter(String delimiter,
			VistaFileProcessing fileProcessing) throws Exception {

		/*final String methodName = "processRecordWithDelimiter";
		LOGGER.enter(CLASSNAME, methodName);*/

		String columnName = null;
		String columnFormat = null;
		String vehType = IConstants.PCAR;
		Integer progType = null;
		/*String tempVehType = "";
		boolean isFailed = false;*/
		boolean dlrExists = false;
		// try {
		String[] recordArray = fileProcessing.getLineString().split(delimiter);
		
		Iterator<FieldColumnMapBean> itr = fileMapping.iterator();
		FieldColumnMapBean mappingBean = null;
		Map<String, Map<Integer,Object>> columnMap = new HashMap<String, Map<Integer,Object>>(); 
		if (recordArray != null && (fileMapping.size() > recordArray.length)) {
			LOGGER.error("File Record have invalid data");
			throw new Exception("File Record have invalid data");
		}
		for (int count = 0; (itr.hasNext() && recordArray.length > count);) {
			Map<Integer, Object> columnMeta = new HashMap<Integer, Object>(1);
			mappingBean = itr.next();
			columnName = mappingBean.getColumnName();
			columnFormat = mappingBean.getFileColumnformatText();
			switch (IConstants.VISTA_FILE_ENUM.valueOf(columnName.trim())) {
			case DTE_RTL:
				if (checkFormat(columnFormat, recordArray[count], 4)) {
					Date sqlDate = getSqlDate(recordArray[count], columnFormat);
					if (sqlDate == null) {
						LOGGER.error("Invalid format for Retail Date");
						fileProcessing.setReason("Invalid format for Retail Date");
						failedRecords.add(fileProcessing);
						failedLines.add(fileProcessing.getLineString());
						return;
					} else {
						fileProcessing.setRetailDate(sqlDate);
						columnMeta.put(2, sqlDate);
						columnMap.put(columnName.trim(), columnMeta);
					}
				} else {
					LOGGER.error("Invalid Retail Date");
					fileProcessing.setReason("Invalid Retail Date");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("DTE_RTL",
						recordArray[count], vehConditions, 2);*/
				count++;
				break;
			case TME_RTL:
				Time time = checkTimeFormat(recordArray[count].trim());
				if (time != null) {
					fileProcessing.setRetailTime(time);
					columnMeta.put(4, time);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Retail Time");
					fileProcessing.setReason("Invalid Retail Time");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("TME_RTL",
						recordArray[count], vehConditions, 4);*/
				count++;
				break;
			case CDE_VEH_STS:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setVehStatusCode(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Vehicle Status Code");
					fileProcessing.setReason("Invalid Vehicle Status Code");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_VEH_STS",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case CDE_VEH_TYP:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setVehTypeCode(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Vehicle Type Code");
					fileProcessing.setReason("Invalid Vehicle Type Code");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_VEH_TYP",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case NUM_PO:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setPoNo(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid PO number");
					fileProcessing.setReason("Invalid PO number");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("NUM_PO",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case CDE_NATL_TYPE:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setNationalTypeCode(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid National Type");
					fileProcessing.setReason("Invalid National Type");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_NATL_TYPE",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case NUM_VIN:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setVinNum(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid VIN number");
					fileProcessing.setReason("Invalid VIN Number");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("NUM_VIN",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;

			case ID_DLR:
				dlrExists = dlrIds.containsKey(recordArray[count]!=null?recordArray[count].trim():"");
				if(dlrExists){
					if (checkFormat(columnFormat, recordArray[count], 1)) {
						fileProcessing.setDealerId(recordArray[count]);
						columnMeta.put(0, recordArray[count]);
						columnMap.put(columnName.trim(), columnMeta);
					} else {
						LOGGER.error("Invalid Dealer Id");
						fileProcessing.setReason("Invalid Dealer Id");
						failedRecords.add(fileProcessing);
						failedLines.add(fileProcessing.getLineString());
						return;
					}
				}else{
					ignoreRecords.add(fileProcessing.getLineString());
					LOGGER.info("Redord ignored due to invalid Dealer Id-"+recordArray[count]+" at Line No-"+fileProcessing.getLineNumber());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("ID_DLR",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case IND_USED_VEH:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setUsedCarIndicator(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Used Car Indicator");
					fileProcessing.setReason("Invalid Used Car Indicator");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("IND_USED_CAR",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case ID_EMP_PUR_CTRL:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					/*fileProcessing.setEmpPurCtrlId(recordArray[count] != null && recordArray[count].trim().length()> 0 ? 
							Integer.parseInt(recordArray[count]):0);*/
					fileProcessing.setEmpPurCtrlId(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				
				} else {
					LOGGER.error("Invalid Emp Purchase Id");
					fileProcessing.setReason("Invalid Emp Purchase Id");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("ID_EMP_PUR_CTRL",
						recordArray[count], vehConditions, 1);*/
				count++;
				break;
			case DTE_MODL_YR:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setModelYearDate(recordArray[count]);
					columnMeta.put(1, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Model Year Date");
					fileProcessing.setReason("Invalid Model Year Date");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("DTE_MDL_YR",
						recordArray[count], vehConditions, 1);*/
				count++;
				break;
			case DES_MODL:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setModelText(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Model Name");
					fileProcessing.setReason("Invalid Model Name");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("TXT_MODL",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case CDE_RGN:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setRegionCode(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Region Code");
					fileProcessing.setReason("Invalid Region Code");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_RGN",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case NAM_RTL_CUS_LST:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setRetailCustLastName(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Last Name");
					fileProcessing.setReason("Invalid Last Name");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("NAM_RTL_CUS_LST",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case NAM_RTL_CUS_FIR:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setRetailCustFirstName(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid First Name");
					fileProcessing.setReason("Invalid First Name");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("NAM_RTL_CUS_FIR",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case NAM_RTL_CUS_MID:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setRetailCustMiddleName(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Middle Name");
					fileProcessing.setReason("Invalid Middle Name");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("NAM_RTL_CUS_MID",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case TME_TRANS:
				Time transTime = checkTimeFormat(recordArray[count].trim());
				columnMeta.put(4, transTime);
				columnMap.put(columnName.trim(), columnMeta);
				//if (transTime != null) { column is nullable so not checking for null
					fileProcessing.setTransTime(transTime);
				/*}  else {
					LOGGER.error("Invalid Transaction Time");
					fileProcessing.setReason("Invalid Transaction Time");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}*/
				/*tempVehType = commonHelper.getVehicleType("TME_TRANS",
						recordArray[count], vehConditions, 4);*/
				count++;
				break;
			case DTE_TRANS:
				if (checkFormat(columnFormat, recordArray[count], 4)) {
					Date sqlDate = getSqlDate(recordArray[count], columnFormat);
					fileProcessing.setTransDate(sqlDate);
					columnMeta.put(2, sqlDate);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Transaction Date");
					fileProcessing.setReason("Invalid Transaction Date");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("DTE_TRANS",
						recordArray[count], vehConditions, 2);*/
				count++;
				break;
			case AMT_MSRP_BASE:
				if (checkFormat(columnFormat, recordArray[count], 5)) {
					fileProcessing.setMsrpBaseAmount(getPercentageValue(recordArray[count]));
					columnMeta.put(3, getPercentageValue(recordArray[count]));
					columnMap.put(columnName.trim(), columnMeta);
				
				} else {
					LOGGER.error("Invalid MSRP Base Amount");
					fileProcessing.setReason("Invalid MSRP Base Amount");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("AMT_MSRP_BASE",
						recordArray[count], vehConditions, 3);*/
				count++;
				break;
			case AMT_MSRP_TOT_ACSRY:
				if (checkFormat(columnFormat, recordArray[count], 5)) {
					fileProcessing.setMsrpTotAmtAcsry(getPercentageValue(recordArray[count]));
					columnMeta.put(3, getPercentageValue(recordArray[count]));
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid MSRP Total Acsry");
					fileProcessing
							.setReason("Invalid MSRP Total Acsry");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("AMT_MSRP_TOT_ACSRY",
						recordArray[count], vehConditions, 3);*/
				count++;
				break;
			case AMT_DLR_RBT:
				if (checkFormat(columnFormat, recordArray[count], 5)) {
					fileProcessing.setDlrRebateAmt(getPercentageValue(recordArray[count]));
					columnMeta.put(3, getPercentageValue(recordArray[count]));
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Rebate Amount");
					fileProcessing.setReason("Invalid Rebate Amount");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("AMT_DLR_RBT",
						recordArray[count], vehConditions, 3);*/
				count++;
				break;

			case IND_FLT:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setFleetIndicator(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Fleet Indicator");
					fileProcessing.setReason("Invalid Fleet Indicator");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("IND_FLT",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case CDE_USE_VEH:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setUseCode(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Use Code");
					fileProcessing.setReason("Invalid Use Code");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_USE",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;

			case CDE_WHSLE_INIT_TYP:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setWholeSaleInitType(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Wholesale Initiation Type");
					fileProcessing
							.setReason("Invalid Wholesale Initiation Type");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_WHSLE_INIT_TYP",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case CDE_VEH_DDR_USE:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setSalesTypeCode(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Sales Type");
					fileProcessing
							.setReason("Invalid Sales Type");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_SLE_TYP",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case DTE_VEH_DEMO_SRV:
				/*if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setDemoServiceDate(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				}*/ 
				if (checkFormat(columnFormat, recordArray[count], 4)) {
					Date sqlDate = getSqlDate(recordArray[count], columnFormat);
					fileProcessing.setDemoServiceDate(sqlDate);
					columnMeta.put(2, sqlDate);
					columnMap.put(columnName.trim(), columnMeta);
				}else {
					LOGGER.error("Invalid Demo Service Date");
					fileProcessing
							.setReason("Invalid Demo Service Date");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("DTE_DEMO_SRV",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case CDE_VEH_DDR_STS:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setCarStatusCode(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Car Status Code");
					fileProcessing
							.setReason("Invalid Car Status Code");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("CDE_CAR_STS",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case IND_USED_VEH_DDRS:
				if (checkFormat(columnFormat, recordArray[count], 1)) {
					fileProcessing.setUsedCarIndicator2(recordArray[count]);
					columnMeta.put(0, recordArray[count]);
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid Used Car Indicator - 2");
					fileProcessing
							.setReason("Invalid Used Car Indicator - 2");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("IND_USED_CAR2",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case AMT_MSRP:
				if (checkFormat(columnFormat, recordArray[count], 5)) {
					fileProcessing.setMsrpTotalsAmt(getPercentageValue(recordArray[count]));
					columnMeta.put(0, getPercentageValue(recordArray[count]));
					columnMap.put(columnName.trim(), columnMeta);
				} else {
					LOGGER.error("Invalid MSRP Options Amount");
					fileProcessing
							.setReason("Invalid MSRP Options Amount");
					failedRecords.add(fileProcessing);
					failedLines.add(fileProcessing.getLineString());
					return;
				}
				/*tempVehType = commonHelper.getVehicleType("AMT_MSRP_OPTS",
						recordArray[count], vehConditions, 0);*/
				count++;
				break;
			case FILLER_COLUMN:
				count++;
				break;
			}
			/*if ("FAIL".equalsIgnoreCase(tempVehType)) {
				isFailed = true;
			} else if (!"".equalsIgnoreCase(tempVehType)) {
				vehType = tempVehType;
			}*/
			
		}
	/*	if (isFailed) {
			LOGGER.debug("Vehicle condition failed. Setting default vehicle type.");
			fileProcessing.setVehTypeCode(IConstants.PCAR);
		} else {
			fileProcessing.setVehTypeCode(vehType);
		}*/
		
		vehType = commonHelper.retrieveVehicleType(columnMap, vehTypConditionsMap);
		fileProcessing.setVehTypeCode(vehType);
		if (!checkBlockCond(fileProcessing)) {
			progType = checkSpecialConditions(specialPrograms, specialConditions,fileProcessing);
			fileProcessing.setProgType(progType);
			validRecords.add(fileProcessing);
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
	}

	private boolean checkBlockCond(VistaFileProcessing fileProcessing) throws BusinessException {
		/*final String methodName = "checkBlockCond";
		LOGGER.enter(CLASSNAME, methodName);*/
		boolean isBlocked = false;
		try{
			if (blockConditions != null && blockConditions.size() > 0) {
		
			for (VehicleConditions bCond : blockConditions) {
				boolean temp = false;
				if (bCond.getVehType().equalsIgnoreCase(
						fileProcessing.getVehTypeCode())
						&& bCond.getVarName() != null) {
					switch (IConstants.VISTA_FILE_ENUM.valueOf(bCond.getVarName().trim())) {
					case DTE_RTL:
						temp = checkStringCondition(bCond,fileProcessing.getRetailDate()+"",fileProcessing);
						break;
					case TME_RTL:
						temp = checkStringCondition(bCond,fileProcessing.getRetailTime()+"",fileProcessing);
						break;
					case CDE_VEH_STS:
						temp = checkStringCondition(bCond,fileProcessing.getVehStatusCode(),fileProcessing);
						break;
					case NUM_PO:
						temp = checkStringCondition(bCond,fileProcessing.getPoNo(),fileProcessing);
						break;
					case NUM_VIN:
						temp = checkStringCondition(bCond,fileProcessing.getVinNum(),fileProcessing);
						break;
					case ID_DLR:
						temp = checkStringCondition(bCond,fileProcessing.getDealerId(),fileProcessing);
						break;
					case IND_USED_VEH:
						temp = checkStringCondition(bCond,
								fileProcessing.getUsedCarIndicator(),
								fileProcessing);
						break;
					case ID_EMP_PUR_CTRL:
						temp = checkStringCondition(bCond,fileProcessing.getEmpPurCtrlId(),fileProcessing);
						break;
					case DTE_MODL_YR:
						temp = checkStringCondition(bCond,fileProcessing.getModelYearDate(),fileProcessing);
						break;
					case DES_MODL:
						temp = checkStringCondition(bCond,fileProcessing.getModelText(),fileProcessing);
						break;
					case CDE_RGN:
						temp = checkStringCondition(bCond,
								fileProcessing.getRegionCode(), fileProcessing);
						break;
					case NAM_RTL_CUS_LST:
						//not required as we don't block by name
						break;
					case NAM_RTL_CUS_FIR:
						//not required as we don't block by name
						break;
					case NAM_RTL_CUS_MID:
						//not required as we don't block by name
						break;
					case TME_TRANS:
						temp = checkStringCondition(bCond,fileProcessing.getTransTime()+"",fileProcessing);
						break;
					case DTE_TRANS:
						temp = checkStringCondition(bCond,fileProcessing.getTransDate()+"",fileProcessing);
						break;
					case AMT_MSRP_BASE:
						temp = checkStringCondition(bCond,fileProcessing.getMsrpBaseAmount()+"",fileProcessing);
						break;
					case AMT_MSRP:
						temp = checkStringCondition(bCond,fileProcessing.getMsrpTotalsAmt()+"",fileProcessing);
						break;						
					case AMT_MSRP_TOT_ACSRY:
						temp = checkStringCondition(bCond,fileProcessing.getMsrpTotAmtAcsry()+"",fileProcessing);
						break;
					case AMT_DLR_RBT:
						temp = checkStringCondition(bCond,fileProcessing.getDlrRebateAmt()+"",fileProcessing);
						break;
					case IND_FLT:
						temp = checkStringCondition(bCond,
								fileProcessing.getFleetIndicator(),
								fileProcessing);
						break;
					case CDE_WHSLE_INIT_TYP:
						temp = checkStringCondition(bCond,
								fileProcessing.getWholeSaleInitType(),
								fileProcessing);
						break;
					case CDE_VEH_TYP:
						temp = checkStringCondition(bCond,fileProcessing.getVehTypeCode(),fileProcessing);
						break;
					case CDE_USE_VEH:
						temp = checkStringCondition(bCond,fileProcessing.getUseCode(),fileProcessing);
						break;
					case CDE_NATL_TYPE:
						temp = checkStringCondition(bCond,fileProcessing.getNationalTypeCode(),fileProcessing);
						break;
					case IND_USED_VEH_DDRS :
						temp = checkStringCondition(bCond,
								fileProcessing.getUsedCarIndicator2(),
								fileProcessing);
						break;
					case CDE_VEH_DDR_STS:
						temp = checkStringCondition(bCond,
								fileProcessing.getCarStatusCode(),
								fileProcessing);
						break;						
					case CDE_VEH_DDR_USE:
						temp = checkStringCondition(bCond,
								fileProcessing.getSalesTypeCode(),
								fileProcessing);
						break;
					}
				}
				if (temp) {
					isBlocked = true;
				}
			}
			}
		}catch (Exception e){
			throw new BusinessException("blkMsg", "Exception while checking blocking conditions");	
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
		return isBlocked;
	}
		

	private boolean checkStringCondition(VehicleConditions bCond, String value,
			VistaFileProcessing fileProcessing) {
		/*final String methodName = "checkStringCondition";
		LOGGER.enter(CLASSNAME, methodName);*/
		boolean isBlocked = false;

		switch (IConstants.COND_TYPE.valueOf(bCond.getCondtype().trim())) {
		case EQ:
			if (value.equalsIgnoreCase(bCond.getCheckValue())) {
				fileProcessing.setReason("Blocked due to the Equal condition for ID "
								+ bCond.getVehCondId());
				fileProcessing.setCondId(bCond.getVehCondId());
				blockedRecords.add(fileProcessing);
				isBlocked = true;
			}
			break;
		// Lead Character
		case LD:
			if ((Pattern.compile(bCond.getCheckValue() + "\\*")).matcher(value)
					.matches()) {
				fileProcessing.setReason("Blocked due to the Lead Chatrecter condition for ID "
								+ bCond.getVehCondId());
				fileProcessing.setCondId(bCond.getVehCondId());
				blockedRecords.add(fileProcessing);
				isBlocked = true;
			}
			break;
		// Trail Character
		case TL:
			if ((Pattern.compile(".*" + bCond.getCheckValue())).matcher(value)
					.matches()) {
				fileProcessing.setReason("Blocked due to the Trail Character condition for ID "
								+ bCond.getVehCondId());
				fileProcessing.setCondId(bCond.getVehCondId());
				blockedRecords.add(fileProcessing);
				isBlocked = true;
			}
			break;
		// Regular Expression Format
		case R:
			if ((Pattern.compile(bCond.getRegularExp())).matcher(value).matches()) {
				fileProcessing.setReason("Blocked due to the Regular Expression condition for ID "
								+ bCond.getVehCondId());
				fileProcessing.setCondId(bCond.getVehCondId());
				blockedRecords.add(fileProcessing);
				isBlocked = true;
			}
			break;
		// Yes
		case Y:
			if (IConstants.CONSTANT_Y.equalsIgnoreCase(value)) {
				fileProcessing.setReason("Blocked due to the Yes condition for ID "
								+ bCond.getVehCondId());
				fileProcessing.setCondId(bCond.getVehCondId());
				blockedRecords.add(fileProcessing);
				isBlocked = true;
			}
			break;
		// No
		case N:
			if (IConstants.CONSTANT_N.equalsIgnoreCase(value)) {
				fileProcessing.setReason("Blocked due to the No condition for ID "
								+ bCond.getVehCondId());
				fileProcessing.setCondId(bCond.getVehCondId());
				blockedRecords.add(fileProcessing);
				isBlocked = true;
			}
			break;
		default:
			break;
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
		return isBlocked;
	}

	private void populateBlockedVehicle(VistaFileProcessing dest,
			VistaFileProcessing orig) {
		dest.setDealerId(orig.getDealerId());
		dest.setPoNo(orig.getPoNo());
		dest.setVinNum(orig.getVinNum());
		dest.setVehTypeCode(orig.getVehTypeCode());
		dest.setCreatedUserId(orig.getCreatedUserId());
		dest.setLastUpdtUserId(orig.getLastUpdtUserId());
	}
	private Integer checkSpecialConditions(List<ProgramDefinitionBean> 
		specialPrograms,Map<Integer,List<ConditionDefinition>> specialConditions,VistaFileProcessing fileProcessing) throws BusinessException{
		/*final String methodName = "checkSpecialConditions";
		LOGGER.enter(CLASSNAME, methodName);*/
		Integer progType = new Integer(0);
		boolean splCond = false;
		for(ProgramDefinitionBean splProgs:specialPrograms){
			if(fileProcessing.getVehTypeCode().equalsIgnoreCase(splProgs.getVehicleType().getVehicleType())){
				List<ConditionDefinition> splConds = specialConditions.get(splProgs.getProgramId());
				if(splConds.size() > 0){
					splCond = checkConditionsSatisfied(splConds, fileProcessing);
					if(splCond){
						progType = splProgs.getProgramId();
						break;
					}
				}
			}
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
		return progType;
	}
	/**
	 * 
	 * @param progId
	 * @param vistaFileProcessing
	 * @return
	 * @throws BusinessException
	 */
	public boolean checkConditionsSatisfied(List<ConditionDefinition> specialConds, VistaFileProcessing vistaFileProcessing)throws BusinessException{
		/*final String methodName = "checkConditionsSatisfied";
		LOGGER.enter(CLASSNAME, methodName);*/
		
		boolean returnCondition = true;
		Iterator<ConditionDefinition> conditionDefIter = specialConds.iterator();
			ConditionDefinition conDef = null;
		try{
			while(conditionDefIter.hasNext()){
				boolean splCondSatisfied = true;
				conDef = conditionDefIter.next();
				String checkValue = conDef.getCheckValue() != null && conDef.getCheckValue().trim().length() > 0 ? conDef.getCheckValue().trim() : 
					conDef.getRegularExp() != null ? conDef.getRegularExp().trim() : "";

				switch(IConstants.VISTA_FILE_ENUM.valueOf(conDef.getVariableName().trim())){
	
					case ID_EMP_PUR_CTRL : 					
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getEmpPurCtrlId(), checkValue);
						break;
		
					case DTE_RTL : 	
						splCondSatisfied = DPBCommonHelper.checkDateCondition(conDef.getCondition(), vistaFileProcessing.getRetailDate() , checkValue);
						break;
		
					case AMT_MSRP_BASE : 
						splCondSatisfied = DPBCommonHelper.checkDoubleCondition(conDef.getCondition(), vistaFileProcessing.getMsrpBaseAmount() , checkValue);
						break;
		
					case AMT_MSRP : 
						splCondSatisfied = DPBCommonHelper.checkDoubleCondition(conDef.getCondition(), vistaFileProcessing.getMsrpTotalsAmt() , checkValue); 
						break;
						
					case AMT_MSRP_TOT_ACSRY : 
						splCondSatisfied = DPBCommonHelper.checkDoubleCondition(conDef.getCondition(), vistaFileProcessing.getMsrpTotAmtAcsry() , checkValue);
						break;
		
					case TME_TRANS : 
						break; 
		
					case NUM_PO : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getPoNo() , checkValue);
						break;
		
					case NUM_VIN : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getVinNum() , checkValue);
						break;
		
					case ID_DLR : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getDealerId() , checkValue);
						break;
		
					case IND_USED_VEH : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getUsedCarIndicator() , checkValue);
						break;
		
					case DTE_MODL_YR : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getModelYearDate() , checkValue);
						break;
		
					case DES_MODL : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getModelText(), checkValue); 
						break;
		
					case CDE_RGN : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getRegionCode(), checkValue);
						break;
		
					case NAM_RTL_CUS_LST : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getRetailCustLastName(), checkValue);
						break;
		
					case NAM_RTL_CUS_FIR : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getRetailCustFirstName(), checkValue);
						break;
		
					case NAM_RTL_CUS_MID : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getRetailCustMiddleName(),checkValue);
						break;
		
					case CDE_USE_VEH : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getUseCode(), checkValue);
						break;
		
					case AMT_DLR_RBT : 
						splCondSatisfied = DPBCommonHelper.checkDoubleCondition(conDef.getCondition(), vistaFileProcessing.getDlrRebateAmt(), checkValue);
						break;
		
					case IND_FLT : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getFleetIndicator(), checkValue);
						break;
		
					case CDE_WHSLE_INIT_TYP : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getWholeSaleInitType(), checkValue);
						break;
		
					case CDE_VEH_STS : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getVehStatusCode(), checkValue);
						break;
		
					case CDE_NATL_TYPE : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getNationalTypeCode(), checkValue);
						break;
		
					case CDE_VEH_TYP : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getVehTypeCode(), checkValue);
						break;
		
					case CDE_VEH_DDR_USE : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getSalesTypeCode(), checkValue);
						break;
					
					case DTE_VEH_DEMO_SRV : 
						splCondSatisfied = DPBCommonHelper.checkDateCondition(conDef.getCondition(), vistaFileProcessing.getDemoServiceDate(), checkValue);
						break;	

					case CDE_VEH_DDR_STS : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getCarStatusCode(), checkValue);
						break;

					case IND_USED_VEH_DDRS : 
						splCondSatisfied = DPBCommonHelper.checkStringCondition(conDef.getCondition(), vistaFileProcessing.getUsedCarIndicator2(), checkValue);
						break;
					
					default : 
						splCondSatisfied = false;	
				}
				if(!splCondSatisfied){
					returnCondition = splCondSatisfied;
					return returnCondition;
				}
			}			
		}catch (Exception e) {
			LOGGER.error("BusinessException occured while checking special conditions");
			throw new BusinessException("splMsg", "Exception while checking special conditions");
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
		return returnCondition;
	}	
}
