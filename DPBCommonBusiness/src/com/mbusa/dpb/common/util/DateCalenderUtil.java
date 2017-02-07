package com.mbusa.dpb.common.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.logger.DPBLog;


public class DateCalenderUtil {

	private static final String BLANK_STRING = "";
	public final static int JUSTIFY_CENTER = 2;
	public final static int JUSTIFY_LEFT = 1;
	public final static int JUSTIFY_RIGHT = 3;
	private static DPBLog LOGGER = DPBLog.getInstance(DateCalenderUtil.class);

	public static Timestamp utilTimestamp(String str){
		final Timestamp timestamp =
				Timestamp.valueOf(
						new SimpleDateFormat("yyyy-MM-dd ")
						.format(new Date()) // get the current date as String
						.concat(str)        // and append the time
						);
		return timestamp;
	}

	public static java.sql.Date convertStringToDate(String strDate) {

		java.util.Date date = new Date();
		java.sql.Date sqlDate = null;

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

		try {
			date =  formatter.parse(strDate);
			sqlDate = new java.sql.Date(date.getTime());
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return sqlDate;
	}
	public static String convertDateToString(java.sql.Date date) {
		SimpleDateFormat sdf1=new SimpleDateFormat("MM/dd/yyyy");
		String strDate = "";
		try {
			if(date!=null){
			strDate=sdf1.format(date);
			}else{
				return makeNonNullString(strDate);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return makeNonNullString(strDate);
	}

	public static Timestamp getCurrentTimeStamp(){
		java.util.Date date= new java.util.Date();

		return new Timestamp(date.getTime());
	}
	public static Timestamp getTimestampByPassingDate(java.sql.Date aDate){
		return new Timestamp(aDate.getTime());
	}

	
	public static String getDatefromCalendar(Calendar calendar) {

		StringBuffer dateString = new StringBuffer();
		try {
			if(calendar != null) {
				StringBuffer monthString = new StringBuffer();
				StringBuffer dayString = new StringBuffer();
				int date = calendar.get(Calendar.DAY_OF_MONTH);
				if(date < 10){
					dayString.append("0");
					dayString.append(String.valueOf(date));
				}else{
					dayString.append(String.valueOf(date));
				}

				int month = calendar.get(Calendar.MONTH) + 1;
				if(month < 10){
					monthString.append("0");
					monthString.append(String.valueOf(month));
				}
				else{
					monthString.append(String.valueOf(month));
				}

				dateString.append(monthString.toString());
				dateString.append("/");
				dateString.append(dayString.toString());
				dateString.append("/");
				dateString.append(String.valueOf(calendar.get(Calendar.YEAR)));

			}

		} catch (Exception e) {

			return null;
		}
		return dateString.toString();
	}


	public static java.sql.Date convertStringToSQLFormat(String strDate) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date date = null;
		java.sql.Date sqlDate = null;
		try {
			date =  formatter.parse(strDate);
			sqlDate = new java.sql.Date(date.getTime());
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return sqlDate;
	}

	/*Jeevan Changes Start*/

	public static java.util.Date SqlDateToUtilDate(Date sqlDate) 
	{  
		java.util.Date utilDate = null;  if (sqlDate != null)  
			utilDate = new java.util.Date(sqlDate.getTime());  
		return utilDate; 
	}

	public static String formatDatetoString(Date sDate) 
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String forDate=sdf.format(sDate);
		return  forDate;
	}


	public static String dateCheckUtil(){
		SimpleDateFormat dtefmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String dteChk = dtefmt.format(date);
		return dteChk;
	}

	public static int calculateQuarter(int month)
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

	public static String calculateDateCounter(String conDate)
	{

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate;
		String eDate=null;
		try {
			newDate = format.parse(conDate);
			newDate = addDays(newDate, 1);
			eDate=format.format(newDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return eDate;
	}

	public static Date addDays(Date date, int days)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); //minus number would decrement the days
		return cal.getTime();
	}


	public static Date convertStringToUtilDate(String strDate) {
		java.util.Date myDate = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			myDate = formatter.parse(strDate);
		} catch (Exception e) {
		}
		return myDate;
	}

	public static int dateDifference(Date d1, Date d2) 
	{
		int diff=0;
		Calendar cal1 = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();

		cal1.setTime(d1); 
		cal2.setTime(d2);
		diff=daysBetween(cal1.getTime(),cal2.getTime());

		return diff;
	}

	public static int daysBetween(Date d1, Date d2){
		return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}


	public static String getActualStatus(int status)
	{
		String actualSatatus=null;

		switch (status) {

		case 1:
			actualSatatus="Draft";
			break;
		case 2:
			actualSatatus="Active";
			break;

		case 3:
			actualSatatus="Inactive";
			break;

		}

		return actualSatatus;

	}

	public static java.sql.Date convertToSqlDate(java.util.Date oDate) {
		return (new java.sql.Date(oDate.getTime()));
	} 


	public static int compaireDates(Date date1,Date date2)
	{

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);

		if(cal1.after(cal2)){
			return 1;
		}

		else if(cal1.before(cal2)){
			return -1;
		}

		else {
			return 0;
		}

	}

	public static byte[] toByteArray(char[] array) {
		return toByteArray(array, Charset.defaultCharset());
	}

	public static byte[] toByteArray(char[] array, Charset charset) {
		CharBuffer cbuf = CharBuffer.wrap(array);
		ByteBuffer bbuf = charset.encode(cbuf);
		return bbuf.array();
	}
	/*Jeevan Changes Ends*/
	// LDRSP Bonus Start
	public static String getNextDayDate() {

		String s;
		Date date;
		Format formatter;
		Calendar calendar = Calendar.getInstance();
		date = calendar.getTime();
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		s = formatter.format(date);
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		formatter = new SimpleDateFormat("MM-dd-yyyy");
		s = formatter.format(date);
		return s;
	}
	//LDRSP Bonus End
	public static List<String> lastDayOfYear(String startDate, String endDate) {
		GregorianCalendar cal = new GregorianCalendar();
		Calendar cal1 =  new GregorianCalendar();
		List<String> dateList = null;
		try{


			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			cal.setTime(sdf.parse(startDate));
			int endYear= cal.get(Calendar.YEAR);

			SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
			cal1.setTime(sdf1.parse(endDate));
			int expectedDt= cal1.get(Calendar.YEAR);

			dateList = new ArrayList<String>();
			for(int i=endYear;i<=expectedDt;i++){

				/*  cal.set(Calendar.YEAR, i);
				   cal.set(Calendar.DAY_OF_YEAR,0);
				   System.out.println(cal.getTime().toString());
				   int weekday = cal.get(Calendar.DAY_OF_WEEK);
				   System.out.println("Weekday: " + weekday);
				   System.out.println();
				   String datesList = getDatefromCalendar(cal);
				   dateList.add(datesList);*/
				cal.set(Calendar.YEAR,i);
				cal.set(Calendar.MONTH, 1);
				cal.set(Calendar.DAY_OF_YEAR,1);
				//cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));   

				System.out.println(sdf.format(cal.getTime()));  
				String datesList = getDatefromCalendar(cal);
				dateList.add(datesList);

			}



		} catch (Exception e) {
			// TODO: handle exception
		}
		return dateList;
	}
	@SuppressWarnings("deprecation")
	public static Time convertStringToTime(String time) {
		Time time1;
		StringTokenizer toString = new StringTokenizer(time, ":");
		int hr = 0;
		int min = 0;
		if (time != null) {
			if (toString.hasMoreElements()) {
				hr = Integer.parseInt((String) toString.nextElement());
				min = Integer.parseInt((String) toString.nextElement());
			}
			time1 = new Time(hr, min, 0);
		} else
			time1 = null;

		return time1;
	}
	/**
	 * @Description This is method is for File Processing
	 * @return
	 */
	public static String dateFormat(){
		DateFormat dateFmt = new SimpleDateFormat("yyyyMMdd.hhmmss");
		Date date = new Date();
		String format =  dateFmt.format(date);
		return format;
	}
	/**
	 * @Description This is method is for File Processing
	 * @param strDate
	 * @return
	 */
	public static java.sql.Date convertStringToSQLDate(String strDate) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		java.util.Date date = null;
		java.sql.Date sqlDate = null;
		try {
			date =  formatter.parse(strDate);
			sqlDate = new java.sql.Date(date.getTime());
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return sqlDate;
	}
	/**
	 * @Description This is method is for File Processing
	 * @param utilDate
	 * @return
	 */
	public static java.sql.Date convertUtiltoSQLDate(java.util.Date utilDate){
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
		
	}
	//Changes by Nikhil start
	public static Calendar getCalendarFromDateString(String dateString) {		
		Calendar calendar = new GregorianCalendar();
		try {
			if(dateString != null) {
				
				calendar.setTimeInMillis(convertStringToDate(dateString).getTime());
			}
			
		} catch (Exception e) {
			
			return null;
		}
		return calendar;
	}
	public static Timestamp getCurrentTimestamp(){
		Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
		return timestamp;
	}
	//Changes by Nikhil end
	/**
	 * 
	 * 
	 */
	public static List<java.sql.Date> quarterlySchedule(java.sql.Date startDate,java.sql.Date endDate){
		List<java.sql.Date> dateList = null;
		java.sql.Date processDatesList = null;

		Calendar c1 = new GregorianCalendar(); 
		Calendar c2 = Calendar.getInstance();   

		c1.setTime(startDate);
		c2.setTime(endDate);   
		boolean isEndGtLast = true;
		int factor = 0;
		//
		dateList = new ArrayList<java.sql.Date>();
	while(!(c1.getTime().compareTo(c2.getTime())> 0) && isEndGtLast) {
		int month = c1.get(Calendar.MONTH);
		if (month == Calendar.JANUARY
			|| month == Calendar.APRIL
			|| month == Calendar.JULY
			|| month == Calendar.OCTOBER) {
			factor = 2;
		} else if (
			month == Calendar.FEBRUARY
			|| month == Calendar.MAY
			|| month == Calendar.AUGUST
			|| month == Calendar.NOVEMBER) {
			factor = 1;
			} else {
				factor = 0;
		}
			//First Quarter
		
			c1.add(Calendar.MONTH,factor);
			c1.set(Calendar.DATE, c1.getActualMaximum(Calendar.DATE));
			if(c1.compareTo(c2) < 0 || c1.compareTo(c2) == 0){
			String datesList = getDatefromCalendar(c1);
			processDatesList = convertStringToSQLFormat(datesList);
			dateList.add(processDatesList);
			c1.add(Calendar.MONTH, 3);
			}else{
				isEndGtLast =  false;
			}
		}
		if(DateCalenderUtil.isListEmpty(dateList)){
			return dateList;
		} else {
			
		}
	return dateList;
	}
	/**
	 * Weekly Schedule
	 * 
	 * @return a list of weekly sunday's from two given dates.
	 */
	public static List<java.sql.Date> weeklySchedule(java.sql.Date startDate, java.sql.Date endDate){
		List<java.sql.Date> dateList = null;
		java.sql.Date processDatesList = null;
		
		Calendar c1 = Calendar.getInstance();   
		Calendar c2 = Calendar.getInstance();   
		Calendar c3 = Calendar.getInstance();
		c1.setTime(startDate);
		c2.setTime(endDate);   
		c3.setTime(startDate);
		
		int WeekEnds = 0;
		
		if(!startDate.equals(endDate)){
		dateList = new ArrayList<java.sql.Date>();
		int startDay = c1.getActualMinimum(Calendar.DATE);
		//int endDay = c2.getActualMaximum(Calendar.DATE);
		c1.set(Calendar.DATE, startDay); 
	//	c2.set(Calendar.DATE, endDay);
		if((c1.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)){
			while(!(c1.getTime().compareTo(c2.getTime())> 0)) {
				
				if(c1.get(Calendar.DAY_OF_WEEK) ==(Calendar.SUNDAY)){
					if(c1.compareTo(c3) > 0 && c1.compareTo(c2) <0){
					WeekEnds++;
					String datesList = getDatefromCalendar(c1);
					processDatesList = convertStringToSQLFormat(datesList);
					dateList.add(processDatesList);
					}
				}
				c1.add(Calendar.DATE,1);
			}//end of while 

		}else{

			while(c2.after(c1) ) {
				
			if(c1.get(Calendar.DAY_OF_WEEK) ==(Calendar.SUNDAY)){
				if((c1.compareTo(c3) > 0 && c1.compareTo(c2) <0) || c1.compareTo(c2) ==0 || c1.compareTo(c3) == 0){
				WeekEnds++;
				String datesList = getDatefromCalendar(c1);
				processDatesList = convertStringToSQLFormat(datesList);
				dateList.add(processDatesList);
				}
			}
				c1.add(Calendar.DATE,1);

			}//end of while

		}//end of else
		}
		
		
		return dateList;

		}

	/*
	 * Monthly Schedule
	 * Returns the list of Last Days of the Month between two given dates.
	 */
	public static List<java.sql.Date> monthlySchedule(java.sql.Date startDate, java.sql.Date endDate){
		
		List<java.sql.Date> dateList = null;
		java.sql.Date processDatesList = null;
		
		Calendar c1 = Calendar.getInstance();   
		Calendar c2 = Calendar.getInstance();   
		
		c1.setTime(startDate);
		c2.setTime(endDate);   
		
		int months = 0;
		
		//if(!startDate.equals(endDate)){
			dateList = new ArrayList<java.sql.Date>();
			boolean isEndGtLast = true;
			while((!(c1.getTime().compareTo(c2.getTime())> 0)) && isEndGtLast) {
				c1.get(Calendar.MONTH);
				int lastDate1 = c1.getActualMaximum(Calendar.DATE);// max number of the month
				//if(lastDate1 <= c2.get(Calendar.DAY_OF_MONTH)){
					c1.set(Calendar.DATE, lastDate1);   
					if(c1.compareTo(c2) < 0 || c1.compareTo(c2) == 0){
					String datesList = getDatefromCalendar(c1);
					processDatesList = convertStringToSQLFormat(datesList);
					dateList.add(processDatesList);

					c1.add(Calendar.MONTH,1);
					months++;
					}else{
						isEndGtLast =  false;
				}
			}
		//}
		return dateList;
	}

	/**
	 * @return
	 */
	public static List<java.sql.Date> biWeeklySchedule(java.sql.Date startDate, java.sql.Date endDate){
		List<java.sql.Date> dateList = null;
		java.sql.Date processDatesList = null;
		Calendar c1 = Calendar.getInstance();   
		Calendar c2 = Calendar.getInstance();   
		Calendar c3 = Calendar.getInstance();
		c1.setTime(startDate);
		c2.setTime(endDate);   
		c3.setTime(startDate);
		int WeekEnds = 0;

		if(!startDate.equals(endDate)){
			dateList = new ArrayList<java.sql.Date>();
			 int startDay = c1.getActualMinimum(Calendar.DATE);
			 int endDay = c2.getActualMaximum(Calendar.DATE);
			 c1.set(Calendar.DATE, startDay); 
			// c2.set(Calendar.DATE, endDay);
			if((c1.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)){
				while(!(c1.getTime().compareTo(c2.getTime())> 0)) {
					
					if(((c1.get(Calendar.DAY_OF_WEEK) ==(Calendar.SUNDAY)) && (c1.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 2)) || ((c1.get(Calendar.DAY_OF_WEEK) ==(Calendar.SUNDAY)) && (c1.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 4)) ){
						if(c1.compareTo(c3) > 0 && c1.compareTo(c2) <0){
						WeekEnds++;
						String datesList = getDatefromCalendar(c1);
						processDatesList = convertStringToSQLFormat(datesList);
						dateList.add(processDatesList);
						}
					}
					c1.add(Calendar.DATE,1);
				}//end of while 

			}else{

				while(c2.after(c1) ) {
				if(((c1.get(Calendar.DAY_OF_WEEK) ==(Calendar.SUNDAY)) && (c1.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 2)) || ((c1.get(Calendar.DAY_OF_WEEK) ==(Calendar.SUNDAY)) && (c1.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 4)) ){
					if(c1.compareTo(c3) > 0 && c1.compareTo(c2) <0){
					WeekEnds++;
					String datesList = getDatefromCalendar(c1);
					processDatesList = convertStringToSQLFormat(datesList);
					dateList.add(processDatesList);
					}
				}
					c1.add(Calendar.DATE,1);

				}//end of while

			}//end of else
		}
		return dateList;
	}
	
	public static List<java.sql.Date> dailySchedule(java.sql.Date startDate, java.sql.Date endDate){
		
		List<java.sql.Date> dateList = null;
		java.sql.Date processDatesList = null;
		
		Calendar c1 = Calendar.getInstance();   
		Calendar c2 = Calendar.getInstance();   
		
		c1.setTime(startDate);
		c2.setTime(endDate);   
		
		int days =0;
		
			dateList = new ArrayList<java.sql.Date>();
			 	while(!(c1.getTime().compareTo(c2.getTime())> 0)) {
					c1.get(Calendar.MONTH);
					{
						int lastDate1 = c1.get(Calendar.DATE);
						 c1.set(Calendar.DATE, lastDate1);        
					}
					String datesList = getDatefromCalendar(c1);
					processDatesList = convertStringToSQLFormat(datesList);
					dateList.add(processDatesList);
					c1.add(Calendar.DATE,1);
					days++;
					}
			
			return dateList;
	}



	public static List<java.sql.Date> yearlySchedule(java.sql.Date startDate, java.sql.Date endDate){

		List<java.sql.Date> dateList = null;
		java.sql.Date processDatesList = null;
		boolean isEndGtLast = true;
		Calendar c1 = Calendar.getInstance();   
		Calendar c2 = Calendar.getInstance();   

		c1.setTime(startDate);
		c2.setTime(endDate);   

		int years =0;
		//if(!startDate.equals(endDate)){
			dateList = new ArrayList<java.sql.Date>();
			while(!(c1.getTime().compareTo(c2.getTime())> 0) && isEndGtLast) {
				c1.get(Calendar.MONTH);
				if((c1.get(Calendar.MONTH)==Calendar.DECEMBER)){
					{
						int lastDate1 = c1.getActualMaximum(Calendar.DATE);
							c1.set(Calendar.DATE, lastDate1);    
							if(c1.compareTo(c2) < 0 || c1.compareTo(c2) == 0 ){
									
							String datesList = getDatefromCalendar(c1);
							processDatesList = convertStringToSQLFormat(datesList);
							dateList.add(processDatesList);
							years++;
						} else {
							isEndGtLast = false;
						}
					}
				}
				c1.add(Calendar.MONTH,1);

			}
		//}
		return dateList;
	}
	/**
	 * This method returns a list of dates based on 
	 * Schedule Type. Make sure to pass the dates to startDate and endDate.
	 * The date list will be return a list of date values with java.sql.Date Format.
	 * @param startDate - Pass the Start Date
	 * @param endDate - Pass the End date
	 * @param scheduleType - Pass the schedule type string e.g., "D" - Daily, "Y"- Yearly
	 * @return a list of dates (SQL dates) as per the schedule Type.
	 * @return NULL if Schedule Type/Start Date/End Date is null
	 */
	public static List<java.sql.Date> scheduleList(java.sql.Date startDate, java.sql.Date endDate, String scheduleType){
		List<java.sql.Date> listOfDates = null;
		if(IConstants.EMPTY_STR.equalsIgnoreCase(scheduleType) || startDate==null || endDate==null ) {
			listOfDates = new ArrayList<java.sql.Date>();
			listOfDates =null;
		
		} else {
			// Daily Schedule
			if(IConstants.SCHD_DAILY.equalsIgnoreCase(scheduleType)){
				listOfDates = dailySchedule(startDate, endDate);
			}
			// Weekly Schedule
			if(IConstants.SCHD_WEEKLY.equalsIgnoreCase(scheduleType)){
				listOfDates = weeklySchedule(startDate, endDate);
			}
			//Bi-Weekly Schedule
			if(IConstants.SCHD_BI_WEEKLY.equalsIgnoreCase(scheduleType)){
				listOfDates = biWeeklySchedule(startDate, endDate);
			}
			// Monthly Schedule
			if(IConstants.SCHD_MONTH.equalsIgnoreCase(scheduleType)){
				listOfDates = monthlySchedule(startDate, endDate);
			}
			// Quarterly Schedule
			if(IConstants.SCHD_QUERTERLY.equalsIgnoreCase(scheduleType)){
				listOfDates = quarterlySchedule(startDate, endDate);
			}
			// Yearly Schedule
			if(IConstants.SCHD_YEARLY.equalsIgnoreCase(scheduleType)){
				listOfDates = yearlySchedule(startDate, endDate);
			}
		}
		return listOfDates;
	}


	/**
	 * If List is Null, this method returns false and if List has non null value, then true
	 * is returned as it is.<br> This method is to be used to avoid possible NullPointerException on List.
	 * @param List<String> s
	 * @return nonNullString
	 */
	public static boolean isListEmpty(List<java.sql.Date> checkList){
		boolean flag =  false;
		if(checkList != null && checkList.size() > 0){
			flag = true;
		}	
		return flag;
	}
	
	public static java.sql.Date convertStringToSQLDateFormat(String strDate) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		java.util.Date date = null;
		java.sql.Date sqlDate = null;
		try {
			date =  formatter.parse(strDate);
			sqlDate = new java.sql.Date(date.getTime());
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return sqlDate;
	}
	
	
	public static java.sql.Date getCurrentDateTime(){

		java.util.Date date = new Date();
		return new java.sql.Date(date.getTime());
	}
	

	/**
	 * If String is Null, this method returns BLANK("") and if String has non null value, then the same String
	 * is returned as it is.<br> This method is to be used to avoid possible NullPointerException on strings.
	 * @param s
	 * @return nonNullString
	 */
	public static String makeNonNullString(String s){
		if(s == null){
			s = BLANK_STRING;
		}
		return s;
	}
	
	/**
	 * Pads and justifies a String with the specified character.
	 *
	 * @param   str the String to pad.
	 * @param   ch the character to pad the String with
	 * @param   len the number of characters to pad the String with.
	 * @param   justify the justification of the padded Strings. Must be one of StringUtils.JUSTIFY_LEFT,
	 * StringUtils.JUSTIFY_RIGHT, StringUtils.JUSTIFY_CENTER
	 */
	public static String stringPad(String str, char ch, int len, int justify)
	{
		int strLen = str.length();

		if (len == strLen)
			return str;

		if (len < strLen)
			strLen = len;

		char[] buffer = new char[len];

		switch (justify)
		{
			case JUSTIFY_CENTER :
				int pad = (int) ((len - strLen) / 2);
				for (int i = 0; i < len; i++)
					buffer[i] = ch;
				for (int i = pad, j = 0; i < len & j < strLen; i++, j++)
					buffer[i] = str.charAt(j);
				break;

			case JUSTIFY_LEFT :
				for (int i = 0; i < strLen; i++)
					buffer[i] = str.charAt(i);
				for (int i = strLen; i < len; i++)
					buffer[i] = ch;
				break;

			case JUSTIFY_RIGHT :
				int lim = len - strLen;
				for (int i = 0; i < lim; i++)
					buffer[i] = ch;
				for (int i = lim, j = 0; i < len & j < strLen; i++, j++)
					buffer[i] = str.charAt(j);
				break;
		}

		return new String(buffer);
	}
	
	public static java.sql.Date scheduleNextDate(java.sql.Date endDate){
		java.sql.Date scheduleEndDate = null;
		Calendar c1 = Calendar.getInstance();
        c1.setTime(endDate);
        c1.add(Calendar.DATE, +1);
        scheduleEndDate = new java.sql.Date(c1.getTime().getTime());
		return scheduleEndDate;
	}
	public static String convertSqlDateToDateString(java.sql.Date date) {
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
		String strDate = "";
		try {
			if(date!=null){
			strDate=sdf1.format(date);
			}else{
				return makeNonNullString(strDate);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return makeNonNullString(strDate);
	}
	/**
	 * 
	 * @param amt
	 * @return Amount format with 
	 */
	public static String formatAmount(String amt){
		String  amtReplace = IConstants.EMPTY_STR;
		
		String amtFormat = IConstants.EMPTY_STR;
		if(!amt.isEmpty()){

			if(amt.contains(IConstants.DOT_STR)){
			
				 StringTokenizer stringTokenizer = new StringTokenizer(amt, IConstants.DOT_STR);
				 	if (stringTokenizer.hasMoreElements()) {
				 		String amount = stringTokenizer.nextElement().toString();
					    String formatAmt= amount.concat(IConstants.DOUBLE_ZERO_CONSTANT);
					    amt=formatAmt;
			}
} 
			if(amt.contains(IConstants.MINUS_SIGN)){
				amtReplace = amt.replaceAll(IConstants.MINUS_SIGN,IConstants.EMPTY_STR);
				//amtFormat = amtReplace.concat(IConstants.MINUS_SIGN);
				amtFormat = amtReplace;
				
			} else {
				amtFormat = amt;
			}
		}
		return makeNonNullString(amtFormat);
	}
	 /**
     * This method will give currentDate in yyyyMMdd.
     * 
     * @param dateFormat
     * @return
     */
    public static String getTodayDate(String dateFormat) {
        Date date;
        Format formatter;
        Calendar calendar = Calendar.getInstance();
        date = calendar.getTime();
        formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }
  
    /**
     * This method give current time in HHmmSS format.
     * 
     * @param timeFormat
     * @return
     */
    public static String getCurrentTime(String timeFormat) {
        Format formatter;
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        formatter = new SimpleDateFormat(timeFormat);
        return formatter.format(date);
    }
    /**
     * This method will give Given Date in yyyyMMdd.
     * 
     * @param dateFormat
     * @return
     */
    public static String getDateInGivenFormat(java.sql.Date aDate,String dateFormat) {
       if(aDate!= null){
      	 Format formatter;    
      	 formatter = new SimpleDateFormat(dateFormat);
      	 return formatter.format(aDate);
       }else{
      	 return "";
       }
    }
    /**
	 * Generic method to verify if a String is NULL or empty("") 
	 * @param s
	 * @return true if s is null/empty else false.
	 */
	public static boolean isEmptyOrNullString(String s){
		boolean flag = true;
		if(s != null && s.length() > 0){
			flag = false;
		}
		return flag;
	}
	
	public static String getNextDate() {

		String s;
		Date date;
		Format formatter;
		Calendar calendar = Calendar.getInstance();
		date = calendar.getTime();
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		s = formatter.format(date);
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		formatter = new SimpleDateFormat("MM/dd/yyyy");
		s = formatter.format(date);
		return s;
	}

	//Data Cleanup for process rerun start
/**
 * This validates input processing date for the correct format
 * @param dateToValidate
 * @return true for valid date
 */
public boolean validateDate(String dateToValidate){
	String dateFormat = "MM/dd/yyyy";
	Date date = null;
		if(dateToValidate == null){
			return false;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		
		try {
			
			//if not valid, it will throw ParseException
			 date = sdf.parse(dateToValidate);
			LOGGER.info("Input date: "+ date.getTime());
		
		} catch (ParseException e) {
			
			e.printStackTrace();
			LOGGER.error("Invalid Processing Date: " + dateToValidate);
			return false;
		}
		
		return true;
	}


/**
 * Checks if the input processing date provided is greater than current date
 * @param processingDate
 * @return isFutureDate
 */
public boolean validateFutureDate(String processingDate) {
	
	boolean isFutureDate = false;
	java.sql.Date currentDate = DateCalenderUtil.getCurrentDateTime();
	java.sql.Date procDate = DateCalenderUtil.convertStringToDate(processingDate);
		if(processingDate == null){
			return false;
		}

		isFutureDate = currentDate.before(procDate);
	
	return isFutureDate;
}

//Data Cleanup for process rerun end
}
