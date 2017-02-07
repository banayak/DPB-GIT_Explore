/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: KpiFileProcessingHelper.java
 * Program Version			: 1.0
 * Program Description		: This Class is used for CDDB Kpi file reading.
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.FieldColumnMapBean;
import com.mbusa.dpb.common.domain.FileFormatBean;
import com.mbusa.dpb.common.domain.KpiFileProcessing;
import com.mbusa.dpb.common.helper.FileProcessingHelperAbstract;
import com.mbusa.dpb.common.logger.DPBLog;

public class KpiFileProcessingHelper extends FileProcessingHelperAbstract {

	private static DPBLog LOGGER = DPBLog.getInstance(KpiFileProcessingHelper.class);
	static final private String CLASSNAME = KpiFileProcessingHelper.class.getName();

	List<String> failedLines = new ArrayList<String>();
	List<KpiFileProcessing> failedRecords = null;
	List<KpiFileProcessing> validRecords = null;
	List<FieldColumnMapBean> fileMapping = null;
	List<String> ignoreRecords = null;
	Map<String,String> dlrIds = null;
	int processID = 0;
	String userId;

	/**
	 * @Description Used for reading file and process records
	 * @param formatBean
	 * @param inProcessPath
	 * @return
	 * @throws Exception
	 */
	public boolean processKpiFile(String inProcessPath,
			FileFormatBean formatBean, List<KpiFileProcessing> validRecords, List<KpiFileProcessing> failedRecords,
			List<String> failedLines,int processID, String userId, List<String> ignoreRecords) throws Exception {

		final String methodName = "processKpiFile";
		LOGGER.enter(CLASSNAME, methodName);

		KpiFileProcessing fileBean = null;
		BufferedReader reader = null;
		String delimiter = formatBean.getIndDelimited();
		String line = null;
		String lineHeader = null;
		boolean isFailed = false;
		this.userId = userId;
		try {
			fileMapping = formatBean.getFileMapingList();
			dlrIds = MasterDataLookup.getInstance().getDealerList();
			
			this.validRecords = validRecords;
			this.failedLines = failedLines;
			this.processID = processID;
			this.failedRecords = failedRecords;
			this.ignoreRecords = ignoreRecords;
			
			File fileInput = new File(inProcessPath);
			if (fileInput.exists() && fileInput.canRead()) {
				reader = new BufferedReader(new FileReader(fileInput));
				for (int count = 1; (line = reader.readLine()) != null; count++) {

					lineHeader = formatBean.getIndHeader();
					if (lineHeader != null && IConstants.CONSTANT_Y.equalsIgnoreCase(lineHeader)&& count == 1) {
						continue;
					}
					fileBean = new KpiFileProcessing();
					fileBean.setDpbProcessId(processID);
					fileBean.setLineString(line);
					fileBean.setLineNumber(count);
					fileBean.setCreatedUserId(userId);
					fileBean.setLastUpdtUserId(userId);
					processRecord(delimiter, fileBean);
				}
				if (failedRecords.size() > 0) {
					isFailed = true;
					LOGGER.info("KPI file reading failed, No.of records failed-"+failedRecords.size());
				}
			}
		} finally {
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
	public void processRecord(String delimiter, KpiFileProcessing fileProcessing)
			throws Exception {

		/*final String methodName = "processRecord";
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
	 */
	private void processRecordWithNoDelimiter(String delimiter,
			KpiFileProcessing fileProcessing) {

		/*final String methodName = "processRecordWithNoDelimiter";
		LOGGER.enter(CLASSNAME, methodName);*/

		String columnName = "";
		String columnFormat = "";
		int length = 0;
		String line = fileProcessing.getLineString();
		int subStringFrom = 0;
		String data = "";
		String dealerId = null;
		Integer year = null;
		String quarter = null;
		boolean dlrExists = false;
		try {
			for (FieldColumnMapBean columnMapBean : fileMapping) {
				columnName = columnMapBean.getColumnName();
				columnFormat = columnMapBean.getFileColumnformatText();
				length = columnMapBean.getFileColumnLength();

				switch (IConstants.KPI_FILE_ENUM.valueOf(columnName.trim())) {
				case ID_DLR:
					data = line.substring(subStringFrom, length + subStringFrom);
					dlrExists = dlrIds.containsKey(data!=null?data.trim():"");
				if(dlrExists){
					if (checkFormat(columnFormat, data, 1)) {
						dealerId = data;
						fileProcessing.setDealerId(dealerId);
						subStringFrom = subStringFrom + length;
					} else {
						LOGGER.error("Invalid Dealer Id");
						fileProcessing.setReason("Invalid Dealer Id");
						failedRecords.add(fileProcessing);
						failedLines.add(fileProcessing.getLineString());
						return;
					}
				}else{
					ignoreRecords.add(fileProcessing.getLineString());
					LOGGER.info("Redord ignored due to invalid Dealer Id-"+data+" at Line No-"+fileProcessing.getLineNumber());
					return;
				}
					break;

				case DTE_FSCL_QTR:
					data = line.substring(subStringFrom, length + subStringFrom);
					if (checkFormat(columnFormat, data, 1)) {
						quarter = data;
						fileProcessing.setKpiFiscalPeriod(data);
						subStringFrom = subStringFrom + length;
					} else {
						LOGGER.error("Invalid KPI Fiscal Quarter");
						fileProcessing.setReason("Invalid KPI Fiscal Quarter");
						failedRecords.add(fileProcessing);
						failedLines.add(fileProcessing.getLineString());
						return;
					}
					break;
				case DTE_FSCL_YR:
					data = line.substring(subStringFrom, length + subStringFrom);
					
					if (checkFormat(columnFormat, data, 2)) {
						year = Integer.parseInt(data);
						fileProcessing.setKpiYear(year);
						subStringFrom = subStringFrom + length;
					} else {
						LOGGER.error("Invalid Year");
						fileProcessing.setReason("Invalid Year");
						failedRecords.add(fileProcessing);
						failedLines.add(fileProcessing.getLineString());
						return;
					}
					break;
				case PCT_KPI:
					fileProcessing = new KpiFileProcessing();
					fileProcessing.setKpiId(columnMapBean.getKpi().getKpiId());
					data = line.substring(subStringFrom, length + subStringFrom);
					if (checkFormat(columnFormat, data, 5)) {
						// KPI % format changing 
						Double kpiPer = getKPIPercentageValue(data);
						
						//fileProcessing.setKpiPercentage(getPercentageValue(data));
						fileProcessing.setKpiPercentage(kpiPer);
						fileProcessing.setDealerId(dealerId);
						fileProcessing.setKpiYear(year);
						fileProcessing.setKpiFiscalPeriod(quarter);
						fileProcessing.setDpbProcessId(processID);
						fileProcessing.setCreatedUserId(userId);
						fileProcessing.setLastUpdtUserId(userId);
						subStringFrom = subStringFrom + length;
						validRecords.add(fileProcessing);
					} else {
						LOGGER.error("Invalid KPI Percentage");
						fileProcessing.setReason("Invalid KPI Percentage");
						failedRecords.add(fileProcessing);
						failedLines.add(fileProcessing.getLineString());
						return;
					}
					break;

				}
			}
		} catch (Exception e) {
			LOGGER.error("Can not typecast the data");
			fileProcessing.setReason("Can not typecast the data");
			failedLines.add(fileProcessing.getLineString());
			failedRecords.add(fileProcessing);
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
	}

	/**
	 * @Description Processing file based on Delimiter indicator
	 * @param line
	 * @param fileMapping
	 * @param delimiter
	 * @return
	 */
	private void processRecordWithDelimiter(String delimiter,
			KpiFileProcessing fileProcessing) {

		/*final String methodName = "processRecordWithDelimiter";
		LOGGER.enter(CLASSNAME, methodName);*/

		String columnName = null;
		String columnFormat = null;
		String dealerId = "";
		Integer year = null;
		String quarter = "";
		boolean dlrExists = false;
		try {
			String[] recordArray = fileProcessing.getLineString().split(
					delimiter);

			Iterator<FieldColumnMapBean> itr = fileMapping.iterator();
			FieldColumnMapBean mappingBean = null;
			if (recordArray != null && (fileMapping.size() > recordArray.length)) {
				throw new Exception("File Record have invalid data");
			}
			for (int count = 0; (itr.hasNext() && recordArray.length > count);) {
				mappingBean = itr.next();
				columnName = mappingBean.getColumnName();
				columnFormat = mappingBean.getFileColumnformatText();
				switch (IConstants.KPI_FILE_ENUM.valueOf(columnName.trim())) {
				case ID_DLR:
					dlrExists = dlrIds.containsKey(recordArray[count]!=null?recordArray[count].trim():"");
				if(dlrExists){
					if (checkFormat(columnFormat, recordArray[count], 1)) {
						dealerId = recordArray[count];
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
					count++;
					break;
				case DTE_FSCL_QTR:
					if (checkFormat(columnFormat, recordArray[count], 1)) {
						quarter = recordArray[count];
					} else {
						LOGGER.error("Invalid KPI Fiscal Quarter");
						fileProcessing.setReason("Invalid Dealer Id");
						failedRecords.add(fileProcessing);
						failedLines.add(fileProcessing.getLineString());
						return;
					}
					count++;
					break;
				case DTE_FSCL_YR:
					if (checkFormat(columnFormat, recordArray[count], 2)) {
						year = Integer.parseInt(recordArray[count]);
					} else {
						LOGGER.error("Invalid Year");
						fileProcessing.setReason("Invalid Year");
						failedRecords.add(fileProcessing);
						failedLines.add(fileProcessing.getLineString());
						return;
					}
					count++;
					break;
				case PCT_KPI:
					fileProcessing = new KpiFileProcessing();
					fileProcessing.setKpiId(mappingBean.getKpi().getKpiId());
					if (checkFormat(columnFormat, recordArray[count], 5)) {
						fileProcessing.setKpiPercentage(getPercentageValue(recordArray[count]));
						fileProcessing.setDealerId(dealerId);
						fileProcessing.setKpiYear(year);
						fileProcessing.setKpiFiscalPeriod(quarter);
						fileProcessing.setDpbProcessId(processID);
						fileProcessing.setCreatedUserId(userId);
						fileProcessing.setLastUpdtUserId(userId);
						validRecords.add(fileProcessing);
					} else {
						LOGGER.error("Invalid Year");
						fileProcessing.setReason("Invalid Year");
						failedRecords.add(fileProcessing);
						failedLines.add(fileProcessing.getLineString());
						return;
					}
					count++;
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Can not typecast the data");
			fileProcessing.setReason("Can not typecast the data");
			failedLines.add(fileProcessing.getLineString());
			failedRecords.add(fileProcessing);
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
	}

}
