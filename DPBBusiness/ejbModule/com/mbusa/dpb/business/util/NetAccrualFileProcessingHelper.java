/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: NetAccrualFileProcessingHelper.java
 * Program Version			: 1.0
 * Program Description		: This Class is used for CoFiCo Net Accural file processing
 * 
 * Modification History		:   This Class is used for Vista file processing 
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * 
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.business.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.FieldColumnMapBean;
import com.mbusa.dpb.common.domain.FileFormatBean;
import com.mbusa.dpb.common.domain.NetAccrualFileProcessing;
import com.mbusa.dpb.common.helper.FileProcessingHelperAbstract;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DateCalenderUtil;


/**
 * 
 * @author SK5008848
 * @version 1.0
 * 
 */
public class NetAccrualFileProcessingHelper extends FileProcessingHelperAbstract {

	private static DPBLog LOGGER = DPBLog.getInstance(NetAccrualFileProcessingHelper.class);
	static final private String CLASSNAME = NetAccrualFileProcessingHelper.class.getName();


	List<String> failedLines = null;
	List<NetAccrualFileProcessing> validRecords = null;
	List<NetAccrualFileProcessing> failedRecords = null;
	boolean isErrorOccured = false;
	FileFormatBean formatBean = null;
	List<FieldColumnMapBean> fileMapping = null;
	String compCode = "";
	Date postDate = null;
	
	/**
	 * @Description Processing File
	 * @param formatBean
	 * @param inProcessPath
	 * @throws Exception
	 */
	public boolean processNetAccuralFile(String inProcessPath, FileFormatBean formatBean, 
			List<NetAccrualFileProcessing> validRecords, List<NetAccrualFileProcessing> failedRecords,
			List<String> failedLines,int processID, String userId) throws Exception {

		final String methodName = "processFile";
		LOGGER.enter(CLASSNAME, methodName);

		NetAccrualFileProcessing fileBean = null;
		BufferedReader reader = null;
		String line = null;
		
		this.validRecords = validRecords;
		this.failedLines = failedLines;
		this.failedRecords = failedRecords;
		
		this.formatBean = formatBean;
		String delimiter = formatBean.getIndDelimited();
		fileMapping = formatBean.getFileMapingList();
		
		
		try {
			reader = new BufferedReader(new FileReader(inProcessPath));
			for (int count = 1; (line = reader.readLine()) != null; count++) {
				if (line.trim().length() == 0 && count == 1) {
					isErrorOccured = true;
					LOGGER.error("Header not found in the file "+inProcessPath);
					return isErrorOccured;
				} else if (line.trim().length() == 0) {
					continue;
				}
				
				if (count == 1) {
					readHeader(line);
					if (isErrorOccured) {
						return isErrorOccured;
					} else {
						continue;
					}
				}
					fileBean = new NetAccrualFileProcessing();
					fileBean.setDpbProcessId(processID);
					fileBean.setLineString(line);
					fileBean.setLineNumber(count);
					fileBean.setCreatedUserId(userId);
					fileBean.setLastUpdtUserId(userId);
					processRecord(delimiter,fileBean);
			}
			if (failedRecords.size() > 0) {
				isErrorOccured = true;
				LOGGER.info("Net accrual file reading failed, No.of records failed-"+failedRecords.size());
			}

		} finally {
			if (reader != null)
				reader.close();
		}
		LOGGER.exit(CLASSNAME, methodName);
		return isErrorOccured;
	}

	/**
	 * @Description Reading Header from the File
	 * @param line
	 * @param delimiter
	 */
	private boolean readHeader(String line) {
		final String methodName = "readHeader";
		LOGGER.enter(CLASSNAME, methodName);
		String delimiter = formatBean.getIndDelimited();
		String columnName = "";
		String columnFormat = "";
		
		if (delimiter != null && delimiter.length() > 0) {
			String[] recordArray = line.split(formatBean.getIndDelimited());
			if (recordArray != null && recordArray.length > 0) {
				Iterator<FieldColumnMapBean> itr = fileMapping.iterator();
				FieldColumnMapBean mapping =null;
				for (int count = 0; (itr.hasNext() && recordArray.length > count); ) {
					mapping = itr.next();
					columnName = mapping.getColumnName();
					columnFormat = mapping.getFileColumnformatText();
					
					switch(IConstants.NET_ACCURAL_ENUM.valueOf(columnName.trim())) {
						case FILLER_COLUMN:
							count++;
							break;
						case CDE_CO:
							if(checkFormat(columnFormat, recordArray[count], 1)) {
								compCode = recordArray[count];
							} else {
								isErrorOccured = true;
								LOGGER.error("Invalid company code");
								//failedReason = "Invalid company code";
								return isErrorOccured;
							}
							count++;
							break;
						case DTE_LDRSP_BNS_ACRL_POST:
							
							if(checkFormat(columnFormat, recordArray[count], 4)) {
								postDate = getSqlDate(recordArray[count], columnFormat);
							} else {
								isErrorOccured = true;
								LOGGER.error("Invalid Post date");
								//failedReason = "Invalid post date";
								return isErrorOccured;
							}
							count++;
							break;
					}
				}
			} else {
				isErrorOccured = true;
				LOGGER.error("Invalid header");
				//failedReason = "Invalid header";
				return isErrorOccured;
			}
		} else if (delimiter == null || delimiter.length() == 0) {
			int subStringFrom = 0;
			for(FieldColumnMapBean mapping:fileMapping) {
				columnName = mapping.getColumnName();
				columnFormat = mapping.getFileColumnformatText();
				
				if (columnName != null && "FILLER_COLUMN".equalsIgnoreCase(columnName.trim())) {
					subStringFrom += mapping.getFileColumnLength();
				}
				if (columnName != null && "CDE_CO".equalsIgnoreCase(columnName.trim())) {
					compCode = line.substring(subStringFrom, subStringFrom + mapping.getFileColumnLength());
					subStringFrom += mapping.getFileColumnLength();
					if(!checkFormat(columnFormat, compCode, 1)) {
						isErrorOccured = true;
						LOGGER.error("Invalid company code");
						//failedReason = "Invalid company code";
						return isErrorOccured;
					}
				} else if (columnName != null && "DTE_LDRSP_BNS_ACRL_POST".equalsIgnoreCase(columnName.trim())) {
					String date = line.substring(subStringFrom, subStringFrom + mapping.getFileColumnLength());
					subStringFrom += mapping.getFileColumnLength();
					if(!checkFormat(columnFormat, date, 4)) {
						isErrorOccured = true;
						LOGGER.error("Invalid Post date");
						//failedReason = "Invalid post date";
						return isErrorOccured;
					} else {
						postDate = getSqlDate(date, columnFormat);
					}
				}
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return isErrorOccured;
	}
	
	/**
	 * @Description Processing record from file
	 * @param line
	 * @param fileMapping
	 * @param delimiter
	 * @return
	 * @throws Exception
	 */
	private void processRecord(String delimiter, NetAccrualFileProcessing fileProcessing)
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
	 * @Description Processing record with no delimiter
	 * @param line
	 * @param fileMapping
	 * @return
	 */
	private void processRecordWithNoDelimiter(String delimiter, NetAccrualFileProcessing fileProcessing) throws Exception {
		/*final String methodName = "processRecord";
		LOGGER.enter(CLASSNAME, methodName);*/
		
		String columnName = "";
		String columnFormat = "";
		int length = 0;
		String line = fileProcessing.getLineString();
		int subStringFrom = 0;
		String data = "";
		boolean minusVal = false;
		
		try {
			for(FieldColumnMapBean columnMapBean: fileMapping) {
				columnName = columnMapBean.getColumnName();
				columnFormat = columnMapBean.getFileColumnformatText();
				length = columnMapBean.getFileColumnLength();
				
				if (columnName!= null && "FILLER_COLUMN".equalsIgnoreCase(columnName.trim())) {
					subStringFrom += length;
				} 
				else if (columnName!= null && "NUM_PO".equalsIgnoreCase(columnName.trim())) {
					data = line
							.substring(subStringFrom, length + subStringFrom);
					subStringFrom += length;
					if(checkFormat(columnFormat, data, 1)) {
						fileProcessing.setPoNo(data != null ? data.trim() : null);
					} else {
						LOGGER.error("Invalid PO number");
						fileProcessing.setReason("Invalid company code");
						failedRecords.add(fileProcessing);
						failedLines.add(fileProcessing.getLineString());
						return;
					}
				} else if (columnName!= null && "AMT_DPB_LDRSP_BNS".equalsIgnoreCase(columnName.trim())) {
					data = line.substring(subStringFrom, length + subStringFrom);
					if(data.contains("-")){
						data = line.substring(subStringFrom, (length + subStringFrom)-1);
						minusVal = true;						
					}
					subStringFrom += length;
					if(checkFormat(columnFormat, data, 5)) {
						if(minusVal){
							fileProcessing.setLadrspBnsAmt((getMinusPercentageValue(data)));
						}
						else
							fileProcessing.setLadrspBnsAmt((getPercentageValue(data)));
					} else {
						LOGGER.error("Invalid amount");
						fileProcessing.setReason("Invalid amount");
						failedRecords.add(fileProcessing);
						failedLines.add(fileProcessing.getLineString());
						return;
					}
				}
			}
			fileProcessing.setCoCode(compCode != null ? compCode.trim() : null);
			fileProcessing.setLadspBnsAcrlPostdate(postDate);
			validRecords.add(fileProcessing);

		} catch (Exception e) {
			LOGGER.error("Can not typecast the data");
			fileProcessing.setReason("Can not typecast the data");
			failedLines.add(fileProcessing.getLineString());
			failedRecords.add(fileProcessing);
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
	}

	/**
	 * @Description Processing record with no delimiter
	 * @param line
	 * @param fileMapping
	 * @param delimiter
	 * @param coFileProcessing 
	 * @return
	 */
	private void processRecordWithDelimiter(String delimiter, NetAccrualFileProcessing fileProcessing) {

		/*final String methodName = "processRecord";
		LOGGER.enter(CLASSNAME, methodName);*/

		String columnName = "";
		String columnFormat = "";
		boolean minusVal = false;
		
		try {
			String[] recordArray = fileProcessing.getLineString().split(delimiter);

			if (recordArray != null && recordArray.length != 3) {
				LOGGER.error("Invalid record ");
				fileProcessing.setReason("Invalid data");
				failedRecords.add(fileProcessing);
				failedLines.add(fileProcessing.getLineString());
				return;
			}
			
			Iterator<FieldColumnMapBean> itr = fileMapping.iterator();
			FieldColumnMapBean mapping =null;
			for (int count = 0; (itr.hasNext() && recordArray.length > count); ) {
				mapping = itr.next();
				columnName = mapping.getColumnName();
				columnFormat = mapping.getFileColumnformatText();
				
				switch(IConstants.NET_ACCURAL_ENUM.valueOf(columnName.trim())) {
					case FILLER_COLUMN:
						count++;
						break;
					case NUM_PO:
						if(checkFormat(columnFormat, recordArray[count], 1)) {
							fileProcessing.setPoNo(recordArray[count] != null ? recordArray[count].trim() : null);
						} else{
							LOGGER.error("Invalid PO number");
							fileProcessing.setReason("Invalid PO Number");
							failedRecords.add(fileProcessing);
							failedLines.add(fileProcessing.getLineString());
							return;
						}
						count++;
						break;
					case AMT_DPB_LDRSP_BNS:
						if(recordArray[count].contains("-")){
							recordArray[count] = recordArray[count].replace("-", "");
							minusVal = true;
						}
						if(checkFormat(columnFormat, recordArray[count], 5)) {
							if(minusVal){
								fileProcessing.setLadrspBnsAmt(getMinusPercentageValue(recordArray[count]));
							}
							else
								fileProcessing.setLadrspBnsAmt(getPercentageValue(recordArray[count]));
						} else {
							LOGGER.error("Invalid amount");
							fileProcessing.setReason("Invalid amount");
							failedRecords.add(fileProcessing);
							failedLines.add(fileProcessing.getLineString());
							return;
						}
						count++;
						break;
				}
			}
			fileProcessing.setCoCode(compCode != null ? compCode.trim() : null);
			fileProcessing.setLadspBnsAcrlPostdate(postDate);
			validRecords.add(fileProcessing);

		} catch (Exception e) {
			LOGGER.error("Can not typecast the data");
			fileProcessing.setReason("Can not typecast the data");
			failedRecords.add(fileProcessing);
			failedLines.add(fileProcessing.getLineString());
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
	}
}
