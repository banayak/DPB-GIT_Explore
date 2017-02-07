package com.mbusa.dpb.web.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.ConditionDefinition;
import com.mbusa.dpb.common.domain.ConditionType;
import com.mbusa.dpb.common.domain.CondtionCode;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.DefStatus;
import com.mbusa.dpb.common.domain.FileDefinitionBean;
import com.mbusa.dpb.common.domain.FileProcessDefBean;
import com.mbusa.dpb.common.domain.FileProcessLogMessages;
import com.mbusa.dpb.common.domain.Kpi;
import com.mbusa.dpb.common.domain.ProcessStatus;
import com.mbusa.dpb.common.domain.ProcessTypes;
import com.mbusa.dpb.common.domain.Scheduler;
import com.mbusa.dpb.common.domain.Trigger;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.helper.MenuNode;
import com.mbusa.dpb.common.helper.Tree;
import com.mbusa.dpb.common.helper.TreeNode;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.DPBCommonHelper;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.web.delegate.BusinessDelegate;

public class WebHelper {
	private static DPBLog LOGGER = DPBLog.getInstance(DPBCommonHelper.class);
	static final private String CLASSNAME = DPBCommonHelper.class.getName();
	public static final String BLANK_STRING = "";
	private static String pathSeparator = System.getProperty("file.separator");
	
	public static String getUserRole(String userId, String costCenter){
		String userRole = "-1";
		//Start:Login changes to fetch user roles from property file - Amit
		try {
			Properties userdata = PropertyManager.getPropertyManager(
					"userRoles").getUserRole(userId);
			if (userdata != null) {
				userRole = userdata.getProperty(userId);
				if (userRole != null)
					return userRole;
				else
					userRole = "-1";
			}else{//Added to get roles by cost Center - Amit
				Properties userdataByCostCenter = PropertyManager.getPropertyManager(
						"userRoles").getUserRole(costCenter);
				if (userdataByCostCenter != null && !userdataByCostCenter.isEmpty()) {
					userRole = userdataByCostCenter.getProperty(costCenter);
					return userRole;
						
				}
			}
		//End: Login changes to fetch user roles from property file - Amit			

/*			Accounting - 457
			Treasury -  245
			SRD - 128
			-1 BLOCK USER
			1	REPORT USER                                  
			2	NORMAL USER                             
			3	ADMIN                                   
			4	BUSINESS USER
			5   ACCOUNTING USER*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userRole;
	}
	public static List<DPBProcessLogBean> todayView(List<DPBProcessLogBean> list,List<DPBProcessLogBean> todayList){
			
		for(DPBProcessLogBean dpbLog: list){
			if("TODAY".equalsIgnoreCase(dpbLog.getProcessDay())){
				todayList.add(dpbLog);
			}
			/*String dateStamp = dateCheckUtil(dpbLog.getLastUpdatedDate());
			if(dateStamp.equals(dateCheckUtil())){
				todayList.add(dpbLog);
			}*/
		}
		return todayList;
	}
	
	public static List<DPBProcessLogBean> yesterdayView(List<DPBProcessLogBean> list,List<DPBProcessLogBean> yesterdayList){
		for(DPBProcessLogBean dpbLog: list){
			if("YDAY".equalsIgnoreCase(dpbLog.getProcessDay())){
				yesterdayList.add(dpbLog);
			}
			/*String dateStamp = dateCheckUtil(dpbLog.getLastUpdatedDate());
			if(!dateStamp.equals(dateCheckUtil())){
				yesterdayList.add(dpbLog);
			}*/
		}
		return yesterdayList;
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
		SimpleDateFormat sdf=new SimpleDateFormat("MM-dd-yyyy");
	
		String forDate=sdf.format(sDate);
		 
		return  forDate;
	}

public static Date getCurrentDate()
{
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
    //get current date time with Date()
    Date date = new Date();
    
    try {
		date=dateFormat.parse(dateFormat.format(date));
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    return date;
}

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
	formatter = new SimpleDateFormat("yyyy-MM-dd");
	s = formatter.format(date);
	return s;
}
	
	public static String dateCheckUtil(){
		SimpleDateFormat dtefmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String dteChk = dtefmt.format(date);
		return dteChk;
	}
	
	public static String dateCheckUtil(Date date){
		SimpleDateFormat dtefmt = new SimpleDateFormat("yyyy-MM-dd");
		//Date date = new Date();
		String dteChk = dtefmt.format(date);
		return dteChk;
	}
	
	public static Tree generateTreeMenu(List<MenuNode> menuList) {		
		Tree tree = new Tree();
		MenuNode mn = null;
		TreeNode node = null;
		MenuNode mnParent = null;
		TreeNode parent = null;
		for(MenuNode item: menuList) {
			if(item.getEnabled() != 'N') {
				mn = new MenuNode(item.getLabelName(),item.getMethodName(),item.getNodeID(),item.getParentNode(),item.getSequence(),item.getMouseOverText());
				node = new TreeNode(mn,null);
				if(mn.getParentNode() == 0){
					tree.addChild(node);
				}
				else {
					mnParent = new MenuNode(item.getParentNode());
					parent = tree.findNode(mnParent);
					if(parent!=null){
						parent.addChild(node);
					}
				}
			}
		}
		return tree;		
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
	
	public static String calculateDateCounter(String conDate)
	{
		
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
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
				SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
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
	
	
	public static String getActualStatus(String status)
	{
		String actualSatatus=null;
		int stat=Integer.parseInt(status);
		
		switch (stat) {

		case 1:
				actualSatatus="D";
				break;
		case 2:
				actualSatatus="A";
				break;
		
		case 3:
				actualSatatus="I";
				break;
		
					}
	
		return actualSatatus;
		
	}
	
	public static String setStatus(String status)
	{
		if(status.equals("D"))
		{
			return "1";
		}
		
		else if(status.equals("A"))
		{
			return "2";
		}
		else 
		{
			return "3";
		}
		
	}
	/*Jeevan Changes Ends*/

	public static boolean isListEmpty(String str){
		boolean flag = false;
		if(str!= null && str.length() > 0){
			flag = true;
		}
		return flag;

		}
	/**
	 * 
	 * 
	 * @param strDate
	 * @return
	 */
	public static java.sql.Date convertStringToDate(String strDate) {
		java.util.Date date = null;
		java.sql.Date sqlDate = null;
		if(WebHelper.isListEmpty(strDate)){
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");			
			try {
				date =  formatter.parse(strDate);
				sqlDate = new java.sql.Date(date.getTime());
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return sqlDate;
	}
	public static Time convertStringToTime(String time) {
		java.sql.Time time1 = null;
		if(WebHelper.isListEmpty(time)){		
			StringTokenizer toString = new StringTokenizer(time, ":");
			int hr = 0;
			int min = 0;
			if (time != null) {
				if (toString.hasMoreElements()) {
					hr = Integer.parseInt((String) toString.nextElement());
					min = Integer.parseInt((String) toString.nextElement());
				}
				time1 = new java.sql.Time(hr, min, 0);
			} else
				time1 = null;
		}
		return time1;
	}
	
	public static String convertTimeToString(Time time) {
		String time1 = time.toString();
			StringTokenizer toString = new StringTokenizer(time1, ":");
			String hr = "";
			String min = "";
			if (time1 != null) {
				if (toString.hasMoreElements()) {
					hr = (String) toString.nextElement();
					min = (String) toString.nextElement();
				}
				time1 = hr+":"+min;
			} else
				time1 = ":";
		return time1;
	}
	
	/**
	 * This method decide role of user based on department received from session. 
	 * if session don't have any value we are taking from user info received from LDAP.
	 * @param user
	 * @param dept
	 * @return String
	 */
	public static String decideRole(UserInformation user,String dept){
		String uRole = "";		//treasury TRSRY	
		if((dept == null || dept.length() <= 0) && user!= null && user.getRole()!= null){
			dept = user.getRole();
		}
	/*	if("1".equalsIgnoreCase(dept)){//admin
			//uRole = "A";
			uRole = "3";
		}else if("2".equalsIgnoreCase(dept)){//SRD
			uRole = "S";
		}else  if("3".equalsIgnoreCase(dept)){	//treasury TRSRY{
			uRole ="T";
		}else {//Accout
			uRole ="F";
		} */
		return dept;
	}
	public static boolean validateEndDate(java.sql.Date rEndDate,java.sql.Date prgmEndDate){
		int checkDate = rEndDate.compareTo(prgmEndDate); // same 0 , r> p  =1 r<p -1
		if(checkDate >=0)
			return true;
		else{
			return false;
		}
	}
	
	public static java.sql.Date convertToSqlDate(java.util.Date oDate) {
		return (new java.sql.Date(oDate.getTime()));
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
	
	/**
	 * Generic method to verify if a String is NULL
	 * @param s
	 * @return true if s is null else false
	 */
	public static boolean isNullString(String s){
		boolean flag = true;
		if(s != null){
			flag = false;
		}
		return flag;
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
	 * 
	 * 
	 * @param vehType - Pass the vehicle List of Id's
	 * @param vehList - Pass the Vehicle List which consists of Vehicle Type and Id - (Cache) 
	 * @return List of vehicleNames 
	 */
	public static List<String> getVehicleName(List<String> vehType,List<VehicleType> vehList){
		List<String> displayName = new ArrayList<String>();
		if(vehType!= null && vehType.size()  > 0 && vehList != null && vehList.size() > 0){
			displayName = new ArrayList<String>(vehList.size()); 
			for(int i = 0 ; i < vehType.size() ;i++){
				for(Iterator<VehicleType> vType = vehList.iterator() ; vType.hasNext();){
					VehicleType vehicle = vType.next();
					if(vehicle.getId().equals(vehType.get(i))){
						displayName.add(i, vehicle.getVehicleType().trim());
					}
				}
			}
		}
		return displayName;			
	}
	
	/**
	 * 
	 * 
	 * @param blkCType - pass the list of condition Id's
	 * @param blockedCList - pass the list consists of id's and condition names.
	 * @return a list of Blocked Condition Names
	 */ 
	public static List<String> getBlockedConditionName(List<String> blkCType,List<ConditionDefinition> blockedCList){
		List<String> displayName = new ArrayList<String>();
		if(blkCType!= null && blkCType.size()  > 0 && blockedCList != null && blockedCList.size() > 0){
			displayName = new ArrayList<String>(blockedCList.size()); 
			for(int i = 0 ; i < blkCType.size() ;i++){
				for(Iterator<ConditionDefinition> cType = blockedCList.iterator() ; cType.hasNext();){
					ConditionDefinition splCondition = cType.next();
					String cId= new Integer(splCondition.getId()).toString();
					
					if(cId.equals(blkCType.get(i))){
						displayName.add(i, splCondition.getConditionName().trim());
					}
				}
			}
		}
		return displayName;			
	}
	
	/**
	 * 
	 * @param Condition Code
	 * @return
	 */
	public static String getConditionCode(String conditionCode,List<CondtionCode> cndValue){
		String displayName = IWebConstants.EMPTY_STR;
		//List<CondtionCode> cndValue = MasterDataLookup.getInstance().getConditionCodes();
		for(CondtionCode cVal: cndValue){
			if(conditionCode != null && conditionCode.equals(cVal.getConditionCode())){
				displayName = cVal.getConditionName();
			}
		}
		return makeNonNullString(displayName);		
	}
	
	/**
	 * 
	 * @param cndCode
	 * @param cndList
	 * @return
	 */
	public static String getConditionType(String conditionCode,List<ConditionType> cndValue){
		String displayName = IWebConstants.EMPTY_STR;
		//List<ConditionType> cndValue = MasterDataLookup.getInstance().getConditionTypes();
		for(ConditionType cVal: cndValue){
			if(conditionCode != null && conditionCode.equals(cVal.getConditionTypeCode())){
				displayName = cVal.getConditionCodeName();
			}
		}
		return makeNonNullString(displayName);		
	}
	
	/**
	 * 
	 * @param code - Pass the status code - A- Active, D- Draft, I - Inactive
	 * @return Status
	 */

	public static String getStatusString(String code,List<DefStatus> defSts){
		String status = IWebConstants.EMPTY_STR;
		//List<DefStatus> defSts = MasterDataLookup.getInstance().getDefStatusCodes();
		for(DefStatus stsCode: defSts){
			if(code!=null && code.equals(stsCode.getDefStatusCode())){
				status = stsCode.getDefStatusName().trim();
			}
		}
		return makeNonNullString(status);
	}
	
	/**
	 * 
	 * @param code - Pass the Trigger Code - A - Auto, M- Manual
	 * @return
	 */
	public static String getTriggerString(String code,List<Trigger> defTgr){
		String trigger = IWebConstants.EMPTY_STR;
		//List<Trigger> defTgr = MasterDataLookup.getInstance().getTriggerCodes();
		for(Trigger tCode: defTgr){
			if(code!=null && code.equals(tCode.getTriggerCode())){
				trigger = tCode.getTriggerName().trim();
			}
		}
		return makeNonNullString(trigger);
	}
	
	/**
	 * 
	 * @param code Pass the schedule String 
	 * @return Schedule Type
	 */
	public static String getScheduleString(String code,List<Scheduler> defSchd){
		String schedule = IWebConstants.EMPTY_STR;
		//List<Scheduler> defSchd = MasterDataLookup.getInstance().getSchedulerCodes();
		for(Scheduler schCode: defSchd){
			if(code!=null && code.equals(schCode.getScheduleCode())){
				schedule = schCode.getScheduleName().trim();
			}
		}
		return makeNonNullString(schedule);
	}
	
	/**
	 * 
	 * @param code  - Y - Yes /N- No
	 * @return Yes/No
	 */
	public static String getYesOrNoString(String code){
		String YorN = IWebConstants.EMPTY_STR;
		if(code!=null && code.equals(IWebConstants.CONSTANT_N)){
			YorN = IWebConstants.NO;
		}else{
			YorN = IWebConstants.YES;
		}
		return makeNonNullString(YorN);
	}
	
	/**
	 * 
	 * @param code  - Y - Fixed /N- Variable
	 * @return Yes/No
	 */
	public static String getPaymentType(String code){
		String YorN = IWebConstants.EMPTY_STR;
		if(code!=null && code.equals(IWebConstants.CONSTANT_N)){
			YorN = IWebConstants.PAYMENT_TYPE_VARIABLE;
		}else{
			YorN = IWebConstants.PAYMENT_TYPE_FIXED;
		}
		return makeNonNullString(YorN);
	}
	/**
	 * 
	 * @param kpiId
	 * @return kpiValue
	 */
	public static String getKPIValue(int kpiId,List<Kpi> kpiValue){
		String kpi = IWebConstants.EMPTY_STR;
		//List<Kpi> kpiValue = MasterDataLookup.getInstance().getKPIList();
		for(Kpi kVal: kpiValue){
			if(kpiId == kVal.getKpiId()){
				kpi = kVal.getKpiName().trim();
			}
		}
		return makeNonNullString(kpi);
	}
	
	public static String formatDate(Date sqlDate) 
	{
			java.util.Date utilDate = null;  
			if (sqlDate != null)  
			utilDate = new java.util.Date(sqlDate.getTime());  
			String sDate="";
			Date rDate=null;
			try {
					SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
					sDate=formatter.format(utilDate);
	
		} catch (Exception e) {
		}
		return sDate; 
	}
	
	public static String getProcessStatusString(String code,List<ProcessStatus> prcsStatus){
		String pStatus = IWebConstants.EMPTY_STR;
		//List<ProcessStatus> prcsStatus = MasterDataLookup.getInstance().getProcessStatus();
		for(ProcessStatus prcsCode: prcsStatus){
			if(code!=null && code.equals(prcsCode.getProcessStatusCd())){
				pStatus = prcsCode.getProcessStatusName().trim();
			}
		}
		return makeNonNullString(pStatus);
	}
	
	public static String getProcessTypeString(String code,List<ProcessTypes> prcsType){
		String pType = IWebConstants.EMPTY_STR;
		//List<ProcessTypes> prcsType = MasterDataLookup.getInstance().getProcessTypeCodes();
		for(ProcessTypes prcsTypeCode: prcsType){
			if(code!=null && code.equals(prcsTypeCode.getProcessTypeCode())){
				pType = prcsTypeCode.getProcessTypeName().trim();
			}
		}
		return makeNonNullString(pType);
	}
	public static String getDateTimeMonthYear(String req){
		Date date = new Date();
		SimpleDateFormat reqFormat = new SimpleDateFormat(req);
		req = reqFormat.format(date);
		return req;
	}
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
	public static boolean copyFileTo(String sourcePath, String targetPath,
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
	
	public static FileProcessLogMessages getProcessLog(int processID,
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
	
	public static boolean reProcess(FileDefinitionBean definitionBean, int processDefinitionId, FileProcessDefBean fileProcessDefBean, boolean isLdrShpBonus) throws TechnicalException {
		BusinessDelegate busDelegate = new BusinessDelegate();
		int fileType =0;
		String newFileName = IConstants.EMPTY_STR;
		String archiveFileName = IConstants.EMPTY_STR;
		String filePath = IConstants.EMPTY_STR;
		String fileName = IConstants.EMPTY_STR;
		PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
		String archivePath = IConstants.EMPTY_STR;
		String type = IConstants.EMPTY_STR;
		String companyName = IConstants.EMPTY_STR;
		boolean returnFlag = true;
		if(definitionBean.getBaseFolder().toUpperCase().contains("VISTA")){
			fileType = 1;
		}else if(definitionBean.getBaseFolder().toUpperCase().contains("ACCRUAL")){
			fileType = 2;
			isLdrShpBonus = true;
		}else if(definitionBean.getBaseFolder().toUpperCase().contains("KPI")){
			fileType = 3;
		} else {
			fileType =0;
		}
		if(fileType > 0){
		fileName = definitionBean.getFileNameFormat().trim();
		filePath = PROPERTY_MANAGER.getPropertyAsString("file.process.in") + fileName + IConstants.DOT_STR + DateCalenderUtil.convertSqlDateToDateString(definitionBean.getActualDate()) +  ".txt";

		switch (fileType) {
		case 1:
			archivePath = PropertyManager.getPropertyManager().getPropertyAsString("vista.archivepath");
			type = PropertyManager.getPropertyManager().getPropertyAsString("vista.filetype");
			companyName = PropertyManager.getPropertyManager().getPropertyAsString("vista.originatingcompany.name");
			break;
		case 2:
			archivePath = PropertyManager.getPropertyManager().getPropertyAsString("netaccrual.archivepath");
			type = PropertyManager.getPropertyManager().getPropertyAsString("netaccrual.filetype");
			companyName = PropertyManager.getPropertyManager().getPropertyAsString("netaccrual.originatingcompany.name");
			break;
		case 3:
			archivePath = PropertyManager.getPropertyManager().getPropertyAsString("kpi.archivepath");
			type = PropertyManager.getPropertyManager().getPropertyAsString("kpi.filetype");
			companyName = PropertyManager.getPropertyManager().getPropertyAsString("kpi.originatingcompany.name");
			break;
		default:
			break;
		}
		archiveFileName =  companyName + "_"
				+  fileName + IConstants.DOT_STR + DateCalenderUtil.convertSqlDateToDateString(definitionBean.getActualDate())+  IConstants.DOT_STR + type
				+ IConstants.DOT_STR + DateCalenderUtil.convertSqlDateToDateString(definitionBean.getActualDate())+  IConstants.DOT_STR +  IConstants.FILE_EXT;
		newFileName = fileName +IConstants.DOT_STR + DateCalenderUtil.convertSqlDateToDateString(definitionBean.getActualDate()) + IConstants.DOT_STR + IConstants.FILE_EXT;
		String sourcePath = archivePath + archiveFileName;
		if (!WebHelper.copyFileTo(sourcePath, filePath, true, newFileName)) {
			busDelegate.insertIntoProcessLog(WebHelper.getProcessLog(
					processDefinitionId, "File not available for Re-process/Reset", IConstants.PROC_STATUS_FAILURE, fileProcessDefBean.getUser() ));
			returnFlag  = false;
		}

		if(fileType == 1){
			busDelegate.reProcessingVistaFile(definitionBean, processDefinitionId, fileProcessDefBean.getReasonForUpdate(),fileProcessDefBean.getProcessType(), fileProcessDefBean.getUser(), isLdrShpBonus);
		}else if(fileType == 2){
			busDelegate.reProcessingAccrualFile(definitionBean, processDefinitionId, fileProcessDefBean.getReasonForUpdate(),fileProcessDefBean.getProcessType(), fileProcessDefBean.getUser(), isLdrShpBonus);
		}else if(fileType == 3){
			busDelegate.reProcessingKpiFile(definitionBean, processDefinitionId, fileProcessDefBean.getReasonForUpdate(),fileProcessDefBean.getProcessType(), fileProcessDefBean.getUser(), isLdrShpBonus);
		}
		}else {
			returnFlag = false;
		}
		return returnFlag;
	}
}