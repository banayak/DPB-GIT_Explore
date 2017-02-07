/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: DPBCommonHelper.java
 * Program Version			: 1.0
 * Program Description		: This Class is used for common methods.
 * 
 * Modification History		: 
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * 
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;

import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.FileProcessLogMessages;
import com.mbusa.dpb.common.domain.InvalidReason;
import com.mbusa.dpb.common.domain.ProgramDefinition;
import com.mbusa.dpb.common.domain.VehicleConditions;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.FilePropertyManager;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.props.ResourceLoader;

public class DPBCommonHelper {
	static PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
	static final private String CLASSNAME = DPBCommonHelper.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(DPBCommonHelper.class);
	private static EmailManager emailManager;
	private static int test =0; 
	SendMailDTO sendMailDTO = new SendMailDTO();
	
	public String retrieveVehicleType (Map<String, Map<Integer, Object>> columnMap, Map<String, List<VehicleConditions>> vehTypConditionsMap) throws BusinessException{
		/*final String methodName = "retrieveVehicleType";
		LOGGER.enter(CLASSNAME, methodName);*/
		String vehType = null;
		String actualVehType = null;
		Set<String> keySet = vehTypConditionsMap.keySet();
		Iterator<String> keySetIter = keySet.iterator();
		boolean typeFound = false;
		try{
			while(keySetIter.hasNext()){
				vehType = keySetIter.next();
				List<VehicleConditions> vehConds = vehTypConditionsMap.get(vehType);
				if(!typeFound && IConstants.VAN.equalsIgnoreCase(vehType)){
					boolean condFailed = false;
					for (VehicleConditions vCond: vehConds) {
						String value = null;
						int dataType = 0;
						Map<Integer, Object> columnMeta = columnMap.get(vCond.getVarName().trim());
						Set<Integer> keySet1 = columnMeta.keySet();
						Iterator<Integer> keySet1Iter = keySet1.iterator();
						if(keySet1Iter.hasNext()){
							dataType = keySet1Iter.next();
							value = String.valueOf(columnMeta.get(dataType));
							condFailed = verifyVehicleTypeCondition(vCond, dataType, value, vehType);
							if(!condFailed){
								actualVehType = vehType;
							}
						}
					}
					if(!condFailed){
						typeFound = true;
					}
				}
				
				if(!typeFound && IConstants.FTL.equalsIgnoreCase(vehType)){
					boolean condFailed = false;
					for (VehicleConditions vCond: vehConds) {
						String value = null;
						int dataType = 0;
						Map<Integer, Object> columnMeta = columnMap.get(vCond.getVarName().trim());
						Set<Integer> keySet1 = columnMeta.keySet();
						Iterator<Integer> keySet1Iter = keySet1.iterator();
						if(keySet1Iter.hasNext()){
							dataType = keySet1Iter.next();
							value = String.valueOf(columnMeta.get(dataType));
							condFailed = verifyVehicleTypeCondition(vCond, dataType, value, vehType);
							if(!condFailed){
								actualVehType = vehType;
							}
						}
					}
					if(!condFailed){
						typeFound = true;
					}
				}
				
				if(!typeFound && IConstants.SMART.equalsIgnoreCase(vehType)){
					boolean condFailed = false;
					for (VehicleConditions vCond: vehConds) {
						String value = null;
						int dataType = 0;
						Map<Integer, Object> columnMeta = columnMap.get(vCond.getVarName().trim());
						Set<Integer> keySet1 = columnMeta.keySet();
						Iterator<Integer> keySet1Iter = keySet1.iterator();
						boolean condsFailed = false;
						if(keySet1Iter.hasNext()){
							dataType = keySet1Iter.next();
							value = String.valueOf(columnMeta.get(dataType));
							condsFailed = verifyVehicleTypeCondition(vCond, dataType, value, vehType);
						}
						condFailed = condFailed || condsFailed;
						if(!condFailed){
							actualVehType = vehType;
						}else{
							actualVehType = "";
						}
					}
					if(!condFailed){
						typeFound = true;
					}
				}
			}
			if(actualVehType == null || "".equalsIgnoreCase(actualVehType)){
				actualVehType = IConstants.PCAR;
			}
		}catch(Exception e){
			throw new BusinessException("vehMsg", "Exception while checking vehicle conditions");	
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
		return actualVehType;
	}
	
	public boolean verifyVehicleTypeCondition(VehicleConditions vCond, int dataType, String value, String vehType){
		/*final String methodName = "verifyVehicleTypeCondition";
		LOGGER.enter(CLASSNAME, methodName);*/
		boolean condFailed = false;
		if (vCond.getVarName() != null 
				&& value != null && vCond.getCondtype() != null) {
			value = value!=null && value.trim().length() > 0 ? value.trim() :"";
			String checkValue = vCond.getCheckValue() != null ? vCond.getCheckValue().trim() : 
				vCond.getRegularExp() != null ? vCond.getRegularExp().trim() : "";
				try {
					switch (IConstants.COND_TYPE.valueOf(vCond.getCondtype().trim())) {
					// Equal
					case EQ:
						switch(dataType) {
						case 1: // integer
							if (Integer.parseInt(value) == Integer.parseInt(checkValue)) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 2: // date
							if (DateCalenderUtil.convertStringToSQLDate(value).equals(
									DateCalenderUtil.convertStringToDate(checkValue)) ) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 3: // double
							if (Double.parseDouble(value) == Double.parseDouble(checkValue)) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 4: // time
							if (Time.valueOf(value).equals(Time.valueOf(checkValue))) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						default: // string
							if (value.equalsIgnoreCase(checkValue)) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						}
						break;
						// Greater Than
					case GT:
						switch(dataType) {
						case 1: // integer
							if (Integer.parseInt(value) > Integer.parseInt(checkValue)) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 2: // date
							if (DateCalenderUtil.convertStringToSQLDate(value).after(
									DateCalenderUtil.convertStringToDate(checkValue))) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 3: // double
							if (Double.parseDouble(value) > Double.parseDouble(checkValue)) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 4: // time
							if (Time.valueOf(value).after(Time.valueOf(checkValue))) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						default: // string
							break;
						}
						break;
						// Greater Than And Equal
					case GE:
						switch(dataType) {
						case 1: // integer
							if (Integer.parseInt(value) >= Integer.parseInt(checkValue)) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 2: // date
							if ((DateCalenderUtil.convertStringToSQLDate(value).after(
									DateCalenderUtil.convertStringToDate(checkValue))) ||
									(DateCalenderUtil.convertStringToSQLDate(value).equals(
											DateCalenderUtil.convertStringToDate(checkValue)))) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 3: // double
							if (Double.parseDouble(value) >= Double.parseDouble(checkValue)) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 4: // time
							if (Time.valueOf(value).after(Time.valueOf(checkValue)) ||
									Time.valueOf(value).equals(Time.valueOf(checkValue))) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						default: // string
							break;
						}
						break;
						// Less Than
					case LT:
						switch(dataType) {
						case 1: // integer
							if (Integer.parseInt(value) < Integer.parseInt(checkValue)) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 2: // date
							if (DateCalenderUtil.convertStringToSQLDate(value).before(
									DateCalenderUtil.convertStringToDate(checkValue))) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 3: // double
							if (Double.parseDouble(value) < Double.parseDouble(checkValue)) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 4: // time
							if (Time.valueOf(value).before(Time.valueOf(checkValue))) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						default: // string
							break;
						}
						break;
						// Less Than And Equal
					case LE:
						switch(dataType) {
						case 1: // integer
							if (Integer.parseInt(value) <= Integer.parseInt(checkValue)) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 2: // date
							if ((DateCalenderUtil.convertStringToSQLDate(value).before(
									DateCalenderUtil.convertStringToDate(checkValue))) ||
									(DateCalenderUtil.convertStringToSQLDate(value).equals(
											DateCalenderUtil.convertStringToDate(checkValue)))) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 3: // double
							if (Double.parseDouble(value) <= Double.parseDouble(checkValue)) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						case 4: // time
							if (Time.valueOf(value).before(Time.valueOf(checkValue)) ||
									Time.valueOf(value).equals(Time.valueOf(checkValue))) {
								vehType = vCond.getVehType();
							} else {
								condFailed = true;
							}
							break;
						default: // string
							break;
						}
						break;
						// Lead Character
					case LD:
						if ((Pattern.compile(vCond.getCheckValue().toLowerCase() + ".*")).matcher(value.toLowerCase()).matches()) {
							vehType = vCond.getVehType();
						} else {
							condFailed = true;
						}
						break;
						// Trail Character
					case TL:
						if ((Pattern.compile(".*" + vCond.getCheckValue().toLowerCase())).matcher(value.toLowerCase()).matches()) {
							vehType = vCond.getVehType();
						} else {
							condFailed = true;
						}
						break;
						// Yes
					case Y:
						if(IConstants.CONSTANT_Y.equalsIgnoreCase(value)) {
							vehType = vCond.getVehType();
						} else {
							condFailed = true;
						}
						break;
						// No
					case N:
						if(IConstants.CONSTANT_N.equalsIgnoreCase(value)) {
							vehType = vCond.getVehType();
						} else {
							condFailed = true;
						}
						break;
						// Regular Expression Format
					case R:
						if ((Pattern.compile(vCond.getRegularExp())).matcher(value).matches()) {
							vehType = vCond.getVehType();
						} else {
							condFailed = true;
						}
						break;
					default:
						break;
					}
				} catch (Exception e) {vehType = "FAIL";
				LOGGER.debug("Vehicle type condition was failed");}
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
		return condFailed;
	}
	
	public String getVehicleType (String colName, String value, List<VehicleConditions> vcList,
			int dataType) {
		/*final String methodName = "getVehicleType";*/
		value = value!=null && value.trim().length() > 0 ? value.trim():IConstants.EMPTY_STR;
		/*LOGGER.enter(CLASSNAME, methodName);*/
		String vType = "";
		String checkValue; 
		boolean isFailed = false;
		for (VehicleConditions vCond: vcList) {
			if (vCond.getVarName() != null && colName.equalsIgnoreCase(vCond.getVarName().trim()) 
					&& value != null && vCond.getCondtype() != null) {
				checkValue = vCond.getCheckValue() != null ? vCond.getCheckValue().trim() : 
					vCond.getRegularExp() != null ? vCond.getRegularExp().trim() : "";
				try {
					switch (IConstants.COND_TYPE.valueOf(vCond.getCondtype().trim())) {
						// Equal
					case EQ:
						switch(dataType) {
						case 1: // integer
							if (Integer.parseInt(value) == Integer.parseInt(checkValue)) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 2: // date
							if (DateCalenderUtil.convertStringToSQLDate(value).equals(
									DateCalenderUtil.convertStringToDate(checkValue)) ) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 3: // double
							if (Double.parseDouble(value) == Double.parseDouble(checkValue)) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 4: // time
							if (Time.valueOf(value).equals(Time.valueOf(checkValue))) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						default: // string
							if (value.equalsIgnoreCase(checkValue)) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						}
						break;
						// Greater Than
					case GT:
						switch(dataType) {
						case 1: // integer
							if (Integer.parseInt(value) > Integer.parseInt(checkValue)) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 2: // date
							if (DateCalenderUtil.convertStringToSQLDate(value).after(
									DateCalenderUtil.convertStringToDate(checkValue))) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 3: // double
							if (Double.parseDouble(value) > Double.parseDouble(checkValue)) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 4: // time
							if (Time.valueOf(value).after(Time.valueOf(checkValue))) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						default: // string
							break;
						}
						break;
						// Greater Than And Equal
					case GE:
						switch(dataType) {
						case 1: // integer
							if (Integer.parseInt(value) >= Integer.parseInt(checkValue)) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 2: // date
							if ((DateCalenderUtil.convertStringToSQLDate(value).after(
									DateCalenderUtil.convertStringToDate(checkValue))) ||
									(DateCalenderUtil.convertStringToSQLDate(value).equals(
											DateCalenderUtil.convertStringToDate(checkValue)))) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 3: // double
							if (Double.parseDouble(value) >= Double.parseDouble(checkValue)) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 4: // time
							if (Time.valueOf(value).after(Time.valueOf(checkValue)) ||
									Time.valueOf(value).equals(Time.valueOf(checkValue))) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						default: // string
							break;
						}
						break;
						// Less Than
					case LT:
						switch(dataType) {
						case 1: // integer
							if (Integer.parseInt(value) < Integer.parseInt(checkValue)) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 2: // date
							if (DateCalenderUtil.convertStringToSQLDate(value).before(
									DateCalenderUtil.convertStringToDate(checkValue))) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 3: // double
							if (Double.parseDouble(value) < Double.parseDouble(checkValue)) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 4: // time
							if (Time.valueOf(value).before(Time.valueOf(checkValue))) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						default: // string
							break;
						}
						break;
						// Less Than And Equal
					case LE:
						switch(dataType) {
						case 1: // integer
							if (Integer.parseInt(value) <= Integer.parseInt(checkValue)) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 2: // date
							if ((DateCalenderUtil.convertStringToSQLDate(value).before(
									DateCalenderUtil.convertStringToDate(checkValue))) ||
									(DateCalenderUtil.convertStringToSQLDate(value).equals(
											DateCalenderUtil.convertStringToDate(checkValue)))) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 3: // double
							if (Double.parseDouble(value) <= Double.parseDouble(checkValue)) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						case 4: // time
							if (Time.valueOf(value).before(Time.valueOf(checkValue)) ||
									Time.valueOf(value).equals(Time.valueOf(checkValue))) {
								vType = vCond.getVehType();
							} else {
								isFailed = true;
							}
							break;
						default: // string
							break;
						}
						break;
						// Lead Character
					case LD:
						if ((Pattern.compile(vCond.getCheckValue() + "*")).matcher(value).find()) {
							vType = vCond.getVehType();
						} else {
							isFailed = true;
						}
						break;
						// Trail Character
					case TL:
						if ((Pattern.compile("*" + vCond.getCheckValue())).matcher(value).find()) {
							vType = vCond.getVehType();
						} else {
							isFailed = true;
						}
						break;
						// Yes
					case Y:
						if(IConstants.CONSTANT_Y.equalsIgnoreCase(value)) {
							vType = vCond.getVehType();
						} else {
							isFailed = true;
						}
						break;
						// No
					case N:
						if(IConstants.CONSTANT_N.equalsIgnoreCase(value)) {
							vType = vCond.getVehType();
						} else {
							isFailed = true;
						}
						break;
						// Regular Expression Format
					case R:
						if ((Pattern.compile(vCond.getRegularExp())).matcher(value).find()) {
							vType = vCond.getVehType();
						} else {
							isFailed = true;
						}
						break;
					default:
						break;
					}
				} catch (Exception e) {vType = "FAIL";}
			}
		}
		if (isFailed) {
			LOGGER.debug("Vehicle type condition was failed");
			vType = "FAIL";
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
		return vType;
	}
	
	public static boolean checkDateCondition (String cond, Date actValue, String checkValue) throws BusinessException {
		/*final String methodName = "checkDateCondition";
		LOGGER.enter(CLASSNAME, methodName);*/
		boolean isBlocked = false;
		switch (IConstants.COND_TYPE.valueOf(cond)) {
				// Equal
			case EQ:
				if (actValue.equals(
						DateCalenderUtil.convertStringToDate(checkValue)) ) {
					isBlocked = true;
				} 
				break;
				// Greater Than
			case GT:
				if (actValue.after(
						DateCalenderUtil.convertStringToDate(checkValue)) ) {
					isBlocked = true;
				}
				break;
				// Greater Than And Equal
			case GE:
				if (actValue.after(
						DateCalenderUtil.convertStringToDate(checkValue)) ||
						(actValue.equals(
								DateCalenderUtil.convertStringToDate(checkValue)))) {
					isBlocked = true;
				}
				break;
				// Less Than
			case LT:
				if (actValue.before(
						DateCalenderUtil.convertStringToDate(checkValue)) ) {
					isBlocked = true;
				}
				break;
				// Less Than And Equal
			case LE:
				if ((actValue.before(
						DateCalenderUtil.convertStringToDate(checkValue))) ||
						(actValue.equals(
								DateCalenderUtil.convertStringToDate(checkValue)))) {
					isBlocked = true;
				}
				break;
			default:
				break;
			}
		/*LOGGER.exit(CLASSNAME, methodName);*/
		return isBlocked;
	}
	
	public static boolean checkStringCondition (String cond, String actValue, String checkValue) {
		/*final String methodName = "checkStringCondition";
		LOGGER.enter(CLASSNAME, methodName);*/
		boolean isBlocked = false;
		switch (IConstants.COND_TYPE.valueOf(cond)) {
			// Equal
			case EQ:
				if (actValue.equalsIgnoreCase(checkValue)) {
					isBlocked = true;
				}
				break;
				// Lead Character
			case LD:
				if ((Pattern.compile(checkValue + ".*")).matcher(actValue).matches()) {
					isBlocked = true;
				}
				break;
				// Trail Character
			case TL:
				if ((Pattern.compile(".*" + checkValue)).matcher(actValue).matches()) {
					isBlocked = true;
				}
				break;
				// Regular Expression Format
			case R:
				if ((Pattern.compile(checkValue)).matcher(actValue).matches()) {
					isBlocked = true;
				}
				break;
				// Yes
			case Y:
				if("Y".equalsIgnoreCase(actValue)) {
					isBlocked = true;
				}
				break;
				// No
			case N:
				if("N".equalsIgnoreCase(actValue)) {
					isBlocked = true;
				}
				break;
			default:
				break;
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
		return isBlocked;
	}
	
	public static boolean checkIntCondition (String cond, int actValue, String checkValue) {
		/*final String methodName = "checkIntCondition";
		LOGGER.enter(CLASSNAME, methodName);*/
		boolean isBlocked = false;
		switch (IConstants.COND_TYPE.valueOf(cond)) {
				// Equal
			case EQ:
				if (actValue == Integer.parseInt(checkValue)) {
					isBlocked = true;
				} 
				break;
				// Greater Than
			case GT:
				if (actValue > Integer.parseInt(checkValue)) {
					isBlocked = true;
				}
				break;
				// Greater Than And Equal
			case GE:
				if (actValue >= Integer.parseInt(checkValue)) {
					isBlocked = true;
				}
				break;
				// Less Than
			case LT:
				if (actValue < Integer.parseInt(checkValue)) {
					isBlocked = true;
				}
				break;
				// Less Than And Equal
			case LE:
				if (actValue <= Integer.parseInt(checkValue)) {
					isBlocked = true;
				}
				break;
			default:
				break;
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
		return isBlocked;
	}
	
	public boolean checkTimeCondition (String cond, Time actValue, String checkValue) {
		/*final String methodName = "checkTimeCondition";
		LOGGER.enter(CLASSNAME, methodName);*/
		boolean isBlocked = false;
		switch (IConstants.COND_TYPE.valueOf(cond)) {
				// Equal
			case EQ:
				if (actValue.equals(Time.valueOf(checkValue))) {
					isBlocked = true;
				}
				break;
				// Greater Than
			case GT:
				if (actValue.after(Time.valueOf(checkValue))) {
					isBlocked = true;
				}
				break;
				// Greater Than And Equal
			case GE:
				if (actValue.after(Time.valueOf(checkValue)) ||
						actValue.equals(Time.valueOf(checkValue))) {
					isBlocked = true;
				}
				break;
				// Less Than
			case LT:
				if (actValue.before(Time.valueOf(checkValue))) {
					isBlocked = true;
				}
				break;
				// Less Than And Equal
			case LE:
				if (actValue.before(Time.valueOf(checkValue)) ||
						actValue.equals(Time.valueOf(checkValue))) {
					isBlocked = true;
				}
				break;
			default:
				break;
			}
		/*LOGGER.exit(CLASSNAME, methodName);*/
		return isBlocked;
	}
	
	public static boolean checkDoubleCondition (String cond, double actValue, String checkValue) {
		/*final String methodName = "checkDoubleCondition";
		LOGGER.enter(CLASSNAME, methodName);*/
		boolean isBlocked = false;
				// Equal
		switch (IConstants.COND_TYPE.valueOf(cond)) {
			case EQ:
				if (actValue == Double.parseDouble(checkValue)) {
					isBlocked = true;
				} 
				break;
				// Greater Than
			case GT:
				if (actValue > Double.parseDouble(checkValue)) {
					isBlocked = true;
				}
				break;
				// Greater Than And Equal
			case GE:
				if (actValue >= Double.parseDouble(checkValue)) {
					isBlocked = true;
				}
				break;
				// Less Than
			case LT:
				if (actValue < Double.parseDouble(checkValue)) {
					isBlocked = true;
				}
				break;
				// Less Than And Equal
			case LE:
				if (actValue <= Double.parseDouble(checkValue)) {
					isBlocked = true;
				}
				break;
			default:
				break;
		}
		/*LOGGER.exit(CLASSNAME, methodName);*/
		return isBlocked;
	}
	
	
	public FileProcessLogMessages getProcessLog(int processID,
			String message, String status,String createdBy) {
		final String methodName = "getProcessLog";
		LOGGER.enter(CLASSNAME, methodName);

		FileProcessLogMessages msg = new FileProcessLogMessages();
		msg.setProcessDefId(processID);
		msg.setProcessMessage(message);
		msg.setProcessStatusCode(status);
		msg.setUserId(createdBy);
		LOGGER.exit(CLASSNAME, methodName);
		return msg;
	}
	/**
	 * 
	 * This method return schedule like Daily, Monthly etc for payment process.
	 * @param pgm
	 * @return
	 */
	public String getPaymentSchedulerType(ProgramDefinition pgm) {
        String schedulerType = IConstants.EMPTY_STR;
        if (pgm.isSpecialProgram()){
	      	if(IConstants.FIXED_PAYMENT_PROCESS.equalsIgnoreCase(pgm.getProcessType())) {
	      	  schedulerType = pgm.getFixedPymtSchedule();
	      	}else if(IConstants.VARIABLE_PAYMENT_PROCESS.equalsIgnoreCase(pgm.getProcessType())){
	      	  schedulerType = pgm.getVariablePymtSchedule();
	      	}else if(IConstants.COMMISSION_PAYMENT_PROCESS.equalsIgnoreCase(pgm.getProcessType())){
	      		schedulerType = pgm.getComsnPymtSchedule();
	      	}
	    }else{
        	if(IConstants.FIXED_PAYMENT_PROCESS.equalsIgnoreCase(pgm.getProcessType())) {
        		schedulerType = pgm.getFixedPymtSchedule();
	      	}else if(IConstants.VARIABLE_PAYMENT_PROCESS.equalsIgnoreCase(pgm.getProcessType())){
		      	schedulerType = pgm.getVariablePymtSchedule();
	      	}else if(IConstants.COMMISSION_PAYMENT_PROCESS.equalsIgnoreCase(pgm.getProcessType())){
	      		schedulerType = pgm.getComsnPymtSchedule();
	      	}
        }
        return schedulerType;
    }
	/**
	 * 
	 * This method return schedule like Daily, Monthly etc for bonus process.
	 * @param pgm
	 * @return
	 */
	public String getBonusSchedulerType(ProgramDefinition pgm) {
        String schedulerType = IConstants.EMPTY_STR;
        if(pgm.isSpecialProgram()){
	      	if(IConstants.FIXED_BONUS_PROCESS.equalsIgnoreCase(pgm.getProcessType())) {
	      	  schedulerType = pgm.getFixedPymtBonusSchedule();
	      	}else if(IConstants.VARIABLE_BONUS_PROCESS.equalsIgnoreCase(pgm.getProcessType())){
	      	  schedulerType = pgm.getVariablePymtBonusSchedule();
	      	}else if(IConstants.COMMISSION_BONUS_PROCESS.equalsIgnoreCase(pgm.getProcessType())){
	      		schedulerType = pgm.getComsnProcessSchedule();
	      	}
	    }else{
        	if(IConstants.FIXED_BONUS_PROCESS.equalsIgnoreCase(pgm.getProcessType())) {
        		schedulerType = pgm.getFixedPymtBonusSchedule();
	      	}else if(IConstants.VARIABLE_BONUS_PROCESS.equalsIgnoreCase(pgm.getProcessType())){
		      	schedulerType = pgm.getVariablePymtBonusSchedule();
	      	}else if(IConstants.COMMISSION_BONUS_PROCESS.equalsIgnoreCase(pgm.getProcessType())){
	      		schedulerType = pgm.getComsnProcessSchedule();
	      	}
        }
        return schedulerType;
    }
	public String getMonth(Date date){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int period  = c1.get(Calendar.MONTH) + 1;
		return String.valueOf(period);
	}
	public int getYear(Date date){
		Calendar c1 =  new GregorianCalendar();;
		c1.setTime(date);
		int year  = c1.get(Calendar.YEAR);
		return year;
	}
     
	/**
	 * @Description This method is used to send mail to user.
	 * @return void
	 * 
	 */
	public static void sendEmail(SendMailDTO sendMailDTO) {
		final String methodName = "fileProcessSendEmail";
		LOGGER.enter(CLASSNAME, methodName);
		if(sendMailDTO == null) {
			sendMailDTO = new SendMailDTO();
		}
		sendMailDTO.setFrom(PROPERTY_MANAGER.getPropertyAsString("mail.fromMail"));
		sendMailDTO.addTORecipient(PROPERTY_MANAGER.getPropertyAsString("mail.toRecipient"));
		try {
			emailManager = EmailManager.getInstance();
			emailManager.sendMail(sendMailDTO);
		} catch (TechnicalException te) {
			LOGGER.error("Error Occuered While Sending Mail");
			//new TechnicalException(te.getMessage());
		}
		LOGGER.exit(CLASSNAME, methodName);

	}
	/**
	 * @Description This method is used to send mail to user.
	 * @return void
	 * 
	 */
	public static void sendMailWithAttachment(SendMailDTO sendMailDTO) {
		final String methodName = "fileProcessSendEmail";
		LOGGER.enter(CLASSNAME, methodName);
		if(sendMailDTO == null) {
			sendMailDTO = new SendMailDTO();
		}
		sendMailDTO.setFrom(PROPERTY_MANAGER.getPropertyAsString("mail.fromMail"));
		sendMailDTO.addTORecipient(PROPERTY_MANAGER.getPropertyAsString("mail.toRecipient"));
		try {
			emailManager = EmailManager.getInstance();
			emailManager.sendMailWithAttachment(sendMailDTO);
		} catch (TechnicalException te) {
			LOGGER.error("Error Occuered While Sending Mail");
			//new TechnicalException(te.getMessage());
		}
		LOGGER.exit(CLASSNAME, methodName);

	}
	public static int calculateQyarter(int month)
	{
		int quarter=0;
		
		switch (month) {

			case 1:
			case 2:
			case 3:
	
					quarter=1;
					break;
			case 4:
			case 5:
			case 6:
	
					quarter=2;
					break;
			
			case 7:
			case 8:
			case 9:
		
					quarter=3;
					break;
			case 10:
			case 11:
			case 12:
		
					quarter=4;
					break;
		}
		
		return quarter;
	}

	/**
	 * Method is Used for creating error file and writing failed records into file 
	 * @param fLines
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static byte[] writeFailedRecords(List<InvalidReason> fLines, String filePath) throws Exception {

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
			String header  = createFileHeader();
			pw.println(header);
			if(fLines != null) {
				for(InvalidReason line: fLines) {
					//pw.write("\n" + line);	
					pw.println(createFileRecordLine(line));
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
	public static String createFileHeader(){
		return 
		padCharAtLeft(IConstants.BLANK_STR, 6, IConstants.ERROR_FILE_COLUMN_CD) + IConstants.BLANK_STR +
			padCharAtLeft(IConstants.BLANK_STR, 10, IConstants.ERROR_FILE_COLUMN_PID) + IConstants.BLANK_STR +
			padCharAtRight(IConstants.BLANK_STR, 13, IConstants.ERROR_FILE_COLUMN_PTYPE) +IConstants.BLANK_STR +
			padCharAtRight(IConstants.BLANK_STR, 10, IConstants.ERROR_FILE_COLUMN_DLR) +IConstants.BLANK_STR +
			padCharAtRight(IConstants.BLANK_STR, 17, IConstants.ERROR_FILE_COLUMN_VIN) +IConstants.BLANK_STR +
			padCharAtLeft(IConstants.BLANK_STR, 12, IConstants.ERROR_FILE_COLUMN_ADATE) +IConstants.BLANK_STR +
			padCharAtRight(IConstants.BLANK_STR, 10, IConstants.ERROR_FILE_COLUMN_RS_STS) +IConstants.BLANK_STR +
			padCharAtLeft(IConstants.BLANK_STR, 10, IConstants.ERROR_FILE_COLUMN_VID) +IConstants.BLANK_STR +
			padCharAtLeft(IConstants.BLANK_STR, 7, IConstants.ERROR_FILE_COLUMN_PGM_ID) + IConstants.BLANK_STR +
			padCharAtRight(IConstants.BLANK_STR, 100, IConstants.ERROR_FILE_COLUMN_REASON) ;
		
	}
	public static String createFileRecordLine(InvalidReason invalidRsn){
		return 
		padCharAtLeft(IConstants.BLANK_STR, 6, getValue(invalidRsn.getCode())) + IConstants.BLANK_STR +
			padCharAtLeft(IConstants.BLANK_STR, 10, getValue(String.valueOf(invalidRsn.getProcId()))) +IConstants.BLANK_STR +
			padCharAtRight(IConstants.BLANK_STR, 13,getValue(invalidRsn.getProcType())) +IConstants.BLANK_STR +
			padCharAtRight(IConstants.BLANK_STR, 10, getValue(invalidRsn.getDealerId())) +IConstants.BLANK_STR +
			padCharAtRight(IConstants.BLANK_STR, 17, getValue(invalidRsn.getVinNum())) +IConstants.BLANK_STR +
			padCharAtLeft(IConstants.BLANK_STR, 12,getValue(String.valueOf(invalidRsn.getActlRtlDate()))) +IConstants.BLANK_STR +
			padCharAtRight(IConstants.BLANK_STR, 10, getValue(invalidRsn.getRcStatus())) +IConstants.BLANK_STR +
			padCharAtLeft(IConstants.BLANK_STR, 10,getValue(String.valueOf(invalidRsn.getUnBlkId()))) +IConstants.BLANK_STR +
			padCharAtLeft(IConstants.BLANK_STR, 7, getValue(String.valueOf(invalidRsn.getPgmId()))) + IConstants.BLANK_STR +
			padCharAtRight(IConstants.BLANK_STR, 100, getValue(invalidRsn.getReason())) ;
		
	}
	public static String getValue(String val){
		if(!isEmptyOrNullString(val)){
			return val;
		}else{
			return IConstants.EMPTY_STR;
		}	
	}
    public static String padCharAtLeft(String charToPad, int totalChar, String str) {
        str = str != null && str.length() > 0 ? str.trim() : IConstants.EMPTY_STR;
        int length = totalChar - str.length() - 1;
        for (int i = 0; i <= length; i++) {
      	str = charToPad + str;
        }
        return str;
    }
    /**
     * This method pad give char to left hand side of given String based on
     * require.
     * 
     * @param charToPad
     * @param totalChar
     * @param str
     * @return
     */
    public static String padCharAtRight(String charToPad, int totalChar, String str) {
        str = str != null && str.length() > 0 ? str.trim() : IConstants.EMPTY_STR;
        int length = totalChar - str.length() - 1;
        for (int i = 0; i <= length; i++) {
      	str = str + charToPad;
        }
        return str;
    }
    public static List<VehicleType> getStaticVehicleList(){
    	List<VehicleType> vList = new ArrayList<VehicleType>();
    	//PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
    	String code = PROPERTY_MANAGER.getPropertyAsString(IConstants.CAR_VEH_TYP_CODE_PROP_NAME);
    	String desc = PROPERTY_MANAGER.getPropertyAsString(IConstants.CAR_VEH_TYP_DESC_PROP_NAME);
    	if(!isEmptyOrNullString(code) && !isEmptyOrNullString(desc)){
    		VehicleType vType = new	VehicleType();
    		vType.setId(code);
    		vType.setVehicleType(desc);
    		vList.add(vType);
    	}
    	code = PROPERTY_MANAGER.getPropertyAsString(IConstants.SMART_VEH_TYP_CODE_PROP_NAME);
    	desc = PROPERTY_MANAGER.getPropertyAsString(IConstants.SMART_VEH_TYP_DESC_PROP_NAME);
    	if(!isEmptyOrNullString(code) && !isEmptyOrNullString(desc)){
    		VehicleType vType = new	VehicleType();
    		vType.setId(code);
    		vType.setVehicleType(desc);
    		vList.add(vType);
    	}    	
    	code = PROPERTY_MANAGER.getPropertyAsString(IConstants.VAN_VEH_TYP_CODE_PROP_NAME);
    	desc = PROPERTY_MANAGER.getPropertyAsString(IConstants.VAN_VEH_TYP_DESC_PROP_NAME);
    	if(!isEmptyOrNullString(code) && !isEmptyOrNullString(desc)){
    		VehicleType vType = new	VehicleType();
    		vType.setId(code);
    		vType.setVehicleType(desc);
    		vList.add(vType);
    	}
    	code = PROPERTY_MANAGER.getPropertyAsString(IConstants.FRIGHTLINER_VEH_TYP_CODE_PROP_NAME);
    	desc = PROPERTY_MANAGER.getPropertyAsString(IConstants.FRIGHTLINER_VEH_TYP_DESC_PROP_NAME);
    	if(!isEmptyOrNullString(code) && !isEmptyOrNullString(desc)){
    		VehicleType vType = new	VehicleType();
    		vType.setId(code);
    		vType.setVehicleType(desc);
    		vList.add(vType);
    	}
    	return vList;
    }
    /**
	 * Generic method to verify if a String is NULL or empty("") 
	 * @param s
	 * @return true if s is null/empty else false.
	 */
	public static boolean isEmptyOrNullString(String s){
		boolean flag = true;
		if(s != null && s.trim().length() > 0){
			flag = false;
		}
		return flag;
	}
	/**
	 * If List is Null, this method returns false and if List has non null value, then true
	 * is returned as it is.<br> This method is to be used to avoid possible NullPointerException on List.
	 * @param List<String> s
	 * @return nonNullString
	 */
	public static boolean isListEmpty(List<String> checkList){
		boolean flag =  false;
		if(checkList != null && checkList.size() > 0){
			flag = true;
		}	
		return flag;
	}
	/**
	 * If List is Null, this method returns false and if List has non null value, then true
	 * is returned as it is.<br> This method is to be used to avoid possible NullPointerException on List.
	 * @param List<String> s
	 * @return nonNullString
	 */
	public static boolean isGivenListEmpty(List checkList){
		boolean flag =  false;
		if(checkList != null && checkList.size() > 0){
			flag = true;
		}	
		return flag;
	}
	public static boolean isListNullOrEmpty(List checkList){
		boolean flag =  false;
		if(checkList == null || (checkList!= null && checkList.isEmpty())){
			flag = true;
		}	
		return flag;
	}
	/**
	 * 
	 * @param bnsFailRecords
	 * @param rtlRecord
	 * @param pDef
	 * @param status
	 * @param msg
	 * @param code
	 */
	public static void addIntoBonusAttachmentList(List<InvalidReason> bnsFailRecords,
				VistaFileProcessing rtlRecord,ProgramDefinition pDef,
					String status,String msg,String code ){
			InvalidReason reason = new InvalidReason();
			reason.setProcId(pDef.getProcessId());
			reason.setPgmId(pDef.getProgramId());	
			reason.setUnBlkId(rtlRecord.getUnBlkVehId());
			reason.setDealerId(rtlRecord.getDealerId());
			reason.setActlRtlDate(pDef.getActlProcDte());
			reason.setRcStatus(status);
			reason.setCode(String.valueOf(code));
			reason.setReason(msg);
			bnsFailRecords.add(reason);
	}
	
	
	/**
	 * This method fetches status of automatic scheduler from properties file - Kshitija
	 * @return String
	 */
	public static String getSchedulerStatus(){
		final String methodName = "getSchedulerStatus";
		LOGGER.enter(CLASSNAME, methodName);
		InputStream in = null;
		String schedulerStatus = null;
		Properties props = new Properties();
		try {
			in = ResourceLoader
					.getInstance()
					.getResourceAsStream(
							FilePropertyManager.BASE_DIR
									+ "/"
									+ IConstants.PROPERTY_FILE_NAME_FOR_SCHEDULER_STATUS
									+ ".properties");
			if (in != null) {
				props.load(in);
				in.close();
			}
		} catch (IOException e) {
			LOGGER.error("IOException occured", e);
		}
		if (props != null) {
			schedulerStatus = props.getProperty("scheduler.status");
		}
		if(schedulerStatus != null)
		{
			schedulerStatus = schedulerStatus.trim();
		}
		LOGGER.exit(CLASSNAME, methodName);
		return schedulerStatus;
	}
	
	/**
	 * This method updates the status of automatic scheduler in properties file - Kshitija
	 * @param schedulerStatus
	 */
	public static void updateSchedulerStatus(String schedulerStatus) {
		final String methodName = "getSchedulerStatus";
		LOGGER.enter(CLASSNAME, methodName);
		PropertiesConfiguration config = null;
		try {
			config = new PropertiesConfiguration(FilePropertyManager.BASE_DIR
					+ "/" + IConstants.PROPERTY_FILE_NAME_FOR_SCHEDULER_STATUS
					+ ".properties");
			if (config != null) {
				config.setProperty("scheduler.status", schedulerStatus);
				config.save();
			}
		} catch (ConfigurationException e) {
			LOGGER.error("ConfigurationException occured", e);
		}
		LOGGER.exit(CLASSNAME, methodName);
	}
	// AMG Changes - START
		/**
		 * Gets the list of AMG Elite Dealers from properties
		 * @return List<String>
		 */
		public static List<String> getAmgEliteDealers() {
			String[] amgEliteDlrsArr = null;
			List<String> amgEliteDealersList = new ArrayList<String>(); 
			String amgEliteDlrsL = PropertyManager.getPropertyManager().getPropertyAsString("dpb.amg.elite.dealers");
			if (amgEliteDlrsL != null && !amgEliteDlrsL.isEmpty()) {
				amgEliteDlrsArr = amgEliteDlrsL.split(",");
				amgEliteDealersList = Arrays.asList(amgEliteDlrsArr);
			}
			return amgEliteDealersList;
		}
		
		/**
		 * Gets the list of AMG Performance Dealers from properties
		 * @return List<String>
		 */
		public static List<String> getAmgPerfDealers() {
			String[] amgPerfDlrsArr = null;
			List<String> amgPerfDealersList = new ArrayList<String>(); 
			String amgPerfDlrsL = PropertyManager.getPropertyManager().getPropertyAsString("dpb.amg.perf.dealers");
			if (amgPerfDlrsL != null && !amgPerfDlrsL.isEmpty()) {
				amgPerfDlrsArr = amgPerfDlrsL.split(",");
				amgPerfDealersList = Arrays.asList(amgPerfDlrsArr);
			}
			return amgPerfDealersList;
		}
		// AMG Changes - END
		//Dealer Compliance Summary Report
		public static String getMonthName(int monthNo)
		{
			String monthName = new DateFormatSymbols().getMonths()[monthNo-1];
			monthName = monthName.substring(0, 3);
			return monthName;
		}
		
}