/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: FileProcessingHelperAbstract.java
 * Program Version			: 1.0
 * Program Description		: This Class is used for file processing common functionality.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * 
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;

/**
 * @author SK5008848
 * @version 1.0
 * 
 */
public abstract class FileProcessingHelperAbstract {
	
	private static DPBLog LOGGER = DPBLog.getInstance(FileProcessingHelperAbstract.class);
	PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
	static final private String CLASSNAME = FileProcessingHelperAbstract.class.getName();
	
	private static String pathSeparator = System.getProperty("file.separator");
	
	public String intFormat = "\\b\\d+\\b";
	public String stringFormat = "^[a-zA-Z0-9 ]+$";
	public String floatFormat = "";
	

	
	/**
	 * Copies a file. If the targetPath is a directory, the file is copied to
	 * the directory and has the same file name as the source.
	 * 
	 * @param sourcePath
	 *            the file to copy.
	 * @param targetPath
	 *            the new file name. Can be a directory name.
	 * @param overwrite
	 *            indicates if the file should be copied over an existing target
	 *            file.
	 * @throws IOException
	 *             if the source or target file cannot be accessed
	 * 
	 */
	public boolean copyFileTo(String sourcePath, String targetPath,
			boolean overWrite, String newFileName) {
		
		final String methodName = "copyFileTo";
		LOGGER.enter(CLASSNAME, methodName);
		try{
		if (sourcePath == null || sourcePath.length() < 1)
			throw (new IllegalArgumentException("sourcePath"));

		if (targetPath == null || targetPath.length() < 1)
			throw (new IllegalArgumentException("targetPath"));

		File sourceFile = new File(sourcePath);

		if (!sourceFile.exists())
			throw (new FileNotFoundException(sourcePath));

		if (!sourceFile.isFile())
			throw (new UnsupportedOperationException("Directory copy is not supported"));

		File targetFile = new File(targetPath);

		if(newFileName != null && newFileName.length() > 0) {
			if (targetFile.isDirectory())
				targetFile = new File(targetPath + pathSeparator + newFileName);

		} else {
			if (targetFile.isDirectory())
				targetFile = new File(targetPath + pathSeparator + sourceFile.getName());
		}
		if (targetFile.exists() && !overWrite)
			return false;

		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(targetFile));

		byte[] buffer = new byte[32000];
		int readCount;

		while ((readCount = inputStream.read(buffer)) > -1)
			outputStream.write(buffer, 0, readCount);
		
			inputStream.close();
			outputStream.close();
			
		}catch(Exception e){
			LOGGER.error("Error Occured While Copying File");
		}
		LOGGER.exit(CLASSNAME, methodName);
		return true;
	}

	/**
	 * Moves a file, deleting any files that may be in the way, but only if the
	 * fromFile exists.
	 * 
	 * Thread A should not access this method when Thread B is moving a file,
	 * and vice versa. So moveFile should be marked with the synchronized
	 * keyword.
	 */
	public boolean moveFileTo(String fromPath, String toPath, String newFileName) {
		
		final String methodName = "moveFileTo";
		LOGGER.enter(CLASSNAME, methodName);
		
		boolean success = false;
		
		File fromFile = new File(fromPath); 
		File toFile = new File(toPath);
		try {
		if (fromFile.exists()) {
			success = copyFileTo(fromPath, toPath, true,newFileName);
			if (success) {
				if (!fromFile.delete()) {
					success = fromFile.renameTo(new File(fromFile.getAbsolutePath() + ".deleted"));
					
				}
			}
			if (!success) {
			}

		}
		}catch(Exception e) {
			LOGGER.error("While Moving File Error Occured");
		}
		LOGGER.exit(CLASSNAME, methodName);
		return success;
	}
	/**
	 * Creates a directory and parent directories 
	 * if directory or parent directories do not already exist.
	 * 
	 * @param dir
	 */
	public void createDirIfNecessary(File dir)
	{
		if (!dir.exists())
		{
			dir.mkdirs();
		}
	}	
	
	public void createDirIfNecessary(String dir)
	{
		if (dir != null) createDirIfNecessary(new File(dir));
	}

	/**
	 * 
	 * @param data
	 * @param format
	 * @return
	 */
	public boolean validateDataFormat(String format, String data, int dataType) {
		
		/*final String methodName = "validateDataFormat";
		LOGGER.enter(CLASSNAME, methodName);*/
		//Pattern pattern = null;
		
		try {
			switch(dataType) {
				case 1: // String
					//pattern = Pattern.compile(stringFormat);
					break;
				case 2: // Integer
					if (data != null && data.trim().length() > 0) {
						Integer.parseInt(data.trim());
					}
					break;
				case 3: // Double
					if (data != null && data.trim().length() > 0) {
						Double.parseDouble(data);
					}
					break;
				case 4: // Date
					if (data != null && data.trim().length() > 0) {
						if (format.contains("yyyy")) {
							checkDateFormat(data, format);
						} else {
							checkDateFormat(data, "yyyyMMdd");
						}
					}
					break;
				/*case 5: //Time
					checkTimeFormat(data);
					break;*/
				case 5: // Percentage
					if (data != null && data.trim().length() > 0) {
						if (data.contains(".")) {
							Double.parseDouble(data);
						} else {
							double val = Double.parseDouble(data)/100;
						}
					}
					break;
				default: // String
					break;
			}
			
			/*if(format.contains("String") || format.contains("string")) {
				
			}
			
			if(format.contains("Int") || format.contains("int")) {
				pattern = Pattern.compile(intFormat);
			}
			if(format.contains(".") || format.contains("float")) {
				try {
					Float.parseFloat(data); }catch(Exception e) {
					return false;	
				}
			}
			if (format.contains("yyyy")) {
				try {
					checkDateFormat(data, format);
				} catch (Exception e) {
					return false;
				}
			}*/
			/*if(pattern != null ) {
				Matcher matcher = pattern.matcher(data);
				if (matcher.matches()) { 
					return true;
				} else {
					return false; 
				}
			}*/
		} catch (NumberFormatException e) {
			LOGGER.error("Number Format Exception while parsing the data");
			return false;
		} catch (Exception e) {
			LOGGER.error("Exception while parsing the data");
			return false;
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
		return true;
		
	}
	
	public double getPercentageValue (String data) {
		double percentage = 0;
		if (data != null) {
			if (data.contains(".")) {
				percentage = Double.parseDouble(data.trim());
			} else {
				percentage = Double.parseDouble(data.trim())/100;
			}
		}
		return percentage;
	}
	// Added by Soumya
	public double getKPIPercentageValue (String data) {
		double percentage = 0;
		if (data != null) {
			if (data.contains(".")) {
				percentage = Double.parseDouble(data.trim());
			} else {
				percentage = Double.parseDouble(data.trim())/1000;
			}
		}
		return percentage;
	}
	
	public double getMinusPercentageValue (String data) {
		double percentage = 0;
		if (data != null) {
			if (data.contains(".")) {
				percentage = Double.parseDouble(data.trim());
			} else {
				percentage = Double.parseDouble(data.trim())/100;
			}
		}
		return percentage * -1;
	}
	
	private void checkDateFormat(String data, String format) throws Exception {
		Date sqlDate = null;
		java.util.Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		date =  formatter.parse(data.trim());
		sqlDate = new java.sql.Date(date.getTime());
	}
	
	public Date getSqlDate(String data, String format) {
		Date sqlDate = null;
		java.util.Date date = null;
		SimpleDateFormat formatter = null;
		if (format.contains("yyyy")) {
			formatter =new SimpleDateFormat(format);
		} else {
			formatter =new SimpleDateFormat("yyyyMMdd");
		}
		try {
			date =  formatter.parse(data != null ? data.trim() : "");
			sqlDate = new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			LOGGER.info("Exception occured while parsing the date format.");
		}
		return sqlDate;
	}

	/**
	 * Method is Used for creating error file and writing failed records into file 
	 * @param fLines
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public byte[] writeFailedRecords(List<String> fLines, String filePath) throws Exception {

		final String methodName = "writeFailedRecords";
		LOGGER.enter(CLASSNAME, methodName);
		
		byte[] errorBytes = null;
		PrintWriter pw = null;
		File file  = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		try {
			pw = new PrintWriter(new FileWriter(file));
			if(fLines != null) {
				for(String line: fLines) {
					//pw.write("\n" + line);	
					pw.println(line);
				}
				
			}
			pw.flush();
			errorBytes = FileUtils.readFileToByteArray(file);
		} catch(Exception e) {
			LOGGER.error("Byte Array");
			throw e;
		} finally {
			if(pw != null){
			pw.close();
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return errorBytes;
	}
	/**
	 * This method is used for checking whether column format is match or not
	 * @param columnFormat
	 * @param value
	 * @return
	 */
	protected boolean checkFormat(String columnFormat, String value, int dataType) {
		boolean flag = false;
		if (columnFormat == null) {
			columnFormat = "STRING";
		}
		if(validateDataFormat(columnFormat, value, dataType)) {
			flag = true;
		}
		return flag;
	}
	
	public Time checkTimeFormat (String time) {
		Time timeValue = null;
		String temp = "";
		if (time != null && time.length() > 0) {
			String[] str = time.split(":");
			if (str != null && str.length > 1) {
				timeValue = Time.valueOf(time);
			} else {
				if (time.length() == 2) {
					temp += time + ":00:00";
				} else if (time.length() == 4) {
					temp += time + ":00";
				} else if (time.length() == 6) {
					int count = 0;
					for(int i=0;i<time.length();i++) {
						if (count == 2) {
							temp += ":";
							temp += time.charAt(i);
							count = 1;
						} else {
							temp += time.charAt(i);
							count++;
						}
					}
				}
				try{
					if (temp.length() > 0) {
						timeValue = Time.valueOf(temp);
					}
				}catch(NumberFormatException ne){
					LOGGER.info("Exception occured while parsing the date format.");
				}
			}
		}
		return timeValue;
	}
}
