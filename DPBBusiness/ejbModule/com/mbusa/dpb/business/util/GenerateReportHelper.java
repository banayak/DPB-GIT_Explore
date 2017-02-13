/**
 * 
 */
package com.mbusa.dpb.business.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.business.view.DpbCommonBeanLocal;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.LeadershipReport;
import com.mbusa.dpb.common.domain.NetStarReport;
import com.mbusa.dpb.common.domain.ReportAmount;
import com.mbusa.dpb.common.domain.ReportDefinitionBean;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.DPBCommonHelper;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.common.util.SendMailDTO;


public class GenerateReportHelper {
	
	

	private static DPBLog LOGGER = DPBLog.getInstance(FileReadingHelper.class);
	static final private String CLASSNAME = FileReadingHelper.class.getName();
	PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
	
	private LocalServiceFactory local =  new LocalServiceFactory();
	SendMailDTO sendMailDTO;
	
	public StringBuffer reportsGenerate(NetStarReport reportCriteria) throws TechnicalException{

		final String methodName = "reportsGenerate";
		LOGGER.enter(CLASSNAME, methodName);
		StringBuffer rList  = local.getReportsService().reportsGenerate(reportCriteria, false);
		LOGGER.exit(CLASSNAME, methodName);
		return rList;
	}
	//Vehicle Details Report-start
	public List<List<List<String>>> reportsGenerateStatic (NetStarReport reportCriteria, Boolean isGenerateExcel)throws TechnicalException {
		final String methodName = "reportsGenerateStatic";
		LOGGER.enter(CLASSNAME, methodName);
		List<List<List<String>>> rList  = local.getReportsService().reportsGenerateStatic(reportCriteria, isGenerateExcel);
		//Vehicle Details Report-end
		LOGGER.exit(CLASSNAME, methodName);
		return rList;
	}
	
	public boolean reportsGenerateScheduler(int processId, String userId, Date actualDate, ReportAmount ra) {

		final String methodName = "reportsGenerateScheduler";
		LOGGER.enter(CLASSNAME, methodName);
		ReportDefinitionBean reportDefBean;
		StringBuffer content = null;
		DpbCommonBeanLocal commonBean = null;
		DPBCommonHelper commonHelper = new DPBCommonHelper();
		List <VehicleType>vehicleList = new ArrayList<VehicleType>();
		
		boolean flag = true;
		try{
			commonBean = local.getDpbCommonService();
			commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processId,
					"Report generation started",IConstants.PROC_STATUS_IN_PROCESS, userId));
			reportDefBean = local.getReportsService().retrieveReportId(processId);
			NetStarReport criteria = new NetStarReport();
			criteria.setSchedule(reportDefBean.getScheduleCode());
			criteria.setRptId(reportDefBean.getReportDefId());
			criteria.setActualDate(actualDate);
			if (IConstants.SCHD_YEARLY.equalsIgnoreCase(criteria.getSchedule())) {
				vehicleList = MasterDataLookup.getInstance().getVehicleList();
				for (VehicleType vehType: vehicleList) {
					List<String> vTypeList = new ArrayList<String>();
					vTypeList.add(vehType.getId());
					criteria.setVehicleType(vTypeList);
					content = local.getReportsService().reportsGenerate(criteria, true);
					//Performance improvement on netstar report - Start (Added parameter for scheduleType) 
					writeReportFile(content, (reportDefBean.getReportName() != null ? reportDefBean.getReportName().trim():"")+"_"+vehType.getVehicleType(), actualDate,criteria.getSchedule());
					//Performance improvement on netstar report - end (Added parameter for scheduleType) 
				}
			} else {
				content = local.getReportsService().reportsGenerate(criteria, true);
				//DPB daily payment details 0032484965 start- Kshitija
				if(ra != null)
				{
				if(criteria.getRptId() == 1) //MBDeal
				{
					ra.setMBDealTotal(criteria.getTotalReportAmount());
				}
				else if(criteria.getRptId() == 2) //cvp
				{
					ra.setCVPTotal(criteria.getTotalReportAmount());
				}
				//DPB monthly reconciliation details start - Kshitija
				else if(criteria.getRptId() == 3)
				{
					ra.setMonthlyProcess(true);
					ra.setVanTotalMap(criteria.getTotalVanMthlyAmt());
				}
				else if(criteria.getRptId() == 4)
				{
					ra.setSmartTotalMap(criteria.getTotalSmartMthlyAmt());
				}
				else if(criteria.getRptId() == 5)
				{
					ra.setPassengerTotalMap(criteria.getTotalPassengerMthlyAmt());
				}
				else if(criteria.getRptId() == 6)
				{
					ra.setFtlTotalMap(criteria.getTotalFtlMthlyAmt());
				}
				else if(criteria.getRptId() == 9)
				{
					ra.setAmgEliteTotalMap(criteria.getTotalAmgEliteMnthlyAmnt());
				}
				else if(criteria.getRptId() == 8)
				{
					ra.setAmgPerfTotalMap(criteria.getTotalAmgPerfMnthlyAmnt());
				}
				//DPB monthly reconciliation details end - Kshitija
				}
				//DPB daily payment details 0032484965 end- Kshitija
				//Performance improvement on netstar report - Start (Added parameter for scheduleType) 
				writeReportFile(content, reportDefBean.getReportName(), actualDate,criteria.getSchedule());
				//Performance improvement on netstar report - End (Added parameter for scheduleType) 
			}
		}
		catch (Exception e) {
			LOGGER.error("Report process failed for ID " + processId, e);
			flag = false;
		}
		sendMailDTO = new SendMailDTO();
		String rDesc = MasterDataLookup.getInstance().getProcessDesc(IConstants.REPORT_PROCESS_NAME);
		if(!flag){
			LOGGER.info("Report process failed for ID " + processId);
			commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processId,
					"Report generation failed",IConstants.PROC_STATUS_FAILURE, userId));
			String sub = PropertyManager.getPropertyManager().getPropertyAsString("report.process.subject.fail");
	        String subject = MessageFormat.format(sub,new Object[]{rDesc});
	       
			sendMailDTO.setSubject(subject);
			String rCnt = PropertyManager.getPropertyManager().getPropertyAsString("report.process.failed");
	        String cnt = MessageFormat.format(rCnt,new Object[]{rDesc,DateCalenderUtil.getDateInGivenFormat(actualDate,IConstants.DATE_FORMAT_MMDDYYYY_WITH_SLASH),processId});	         
			sendMailDTO.setContent(cnt);			
		}else{
			LOGGER.info("Report process success for ID " + processId);
			commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processId,
					"Report successfully generated",IConstants.PROC_STATUS_SUCCESS, userId));
			String sub = PropertyManager.getPropertyManager().getPropertyAsString("report.process.subject.success");
	        String subject = MessageFormat.format(sub,new Object[]{rDesc});
	       
			sendMailDTO.setSubject(subject);
			
			String rCnt = PropertyManager.getPropertyManager().getPropertyAsString("report.process.success");
	        String cnt = MessageFormat.format(rCnt,new Object[]{rDesc,DateCalenderUtil.getDateInGivenFormat(actualDate,IConstants.DATE_FORMAT_MMDDYYYY_WITH_SLASH),processId});
	       	sendMailDTO.setContent(cnt);
		}
		DPBCommonHelper.sendEmail(sendMailDTO);
		LOGGER.exit(CLASSNAME, methodName);
		return flag;
	}
	
	/**
	 * Method is Used for creating report file 
	 * @param filePath
	 * @param fileName
	 * @param schedule 
	 * @return
	 * @throws Exception
	 */
	public void writeReportFile(StringBuffer content, String fileName, Date actualDate, String schedule) throws Exception {

		final String methodName = "writeReportFile";
		LOGGER.enter(CLASSNAME, methodName);
		PrintWriter pw = null;
		String dateValue = "";
		String filePath = null;
		
		java.sql.Date currentDate = null;
		java.sql.Date prevDate = null;
		  java.sql.Date monthEndDate = null;
		  Calendar c = Calendar.getInstance();
		  currentDate = new java.sql.Date(c.getTime().getTime());
		//Performance improvement on netstar report - Start
		  if(actualDate != null) 
		  {
			  c.setTime(actualDate);
			  
		  }
		  c.add(Calendar.DATE, -1);
		  prevDate =  new java.sql.Date(c.getTime().getTime());
		  RtlCalenderDefinition rtlDim = MasterDataLookup.getInstance().getRtlCalByDate(currentDate);
		  RtlCalenderDefinition rtlDim1 = MasterDataLookup.getInstance().getRtlCalByDate(prevDate);
		//Performance improvement on netstar report - End
		  monthEndDate = rtlDim.getDteRetlMthBeg();
		  
		
		if(actualDate != null) {
			dateValue = actualDate.toString().replaceAll("-", "") +IConstants.DOT_STR;
		}
		String fName = (fileName!=null ? fileName.trim():"")+IConstants.DOT_STR+dateValue+IConstants.FILE_EXT;
		if(currentDate.toString().equals(monthEndDate.toString()))
			 filePath = PropertyManager.getPropertyManager().getPropertyAsString("payment.path2");
		else 
			 filePath = PropertyManager.getPropertyManager().getPropertyAsString("payment.path2");
		File file  = new File(filePath+fName);
		
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			pw = new PrintWriter(new FileWriter(file));
			pw.write(content.toString());
			pw.flush();
			/*String filName = "MB_DPB.FTL.Payment.20160720-123340.txt";
			File file5 = new File("C:\\DPBFileProcess\\stage\\MB_DPB.FTL.Payment.20160720-123340.txt");*/
			//Performance improvement on netstar report - Start
			if(schedule.equalsIgnoreCase(IConstants.SCHD_MONTH))
			{
			copyAndConvertNetstarReports(file,fName, rtlDim1.getRetlMthNum(),rtlDim1.getRetlYearNum());
			}
			//Performance improvement on netstar report - End
		} finally {
			if(pw != null){
			pw.close();
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
	}
	
	public StringBuffer reportsGenerate1(LeadershipReport reportCriteria) throws TechnicalException{

		final String methodName = "reportsGenerate";
		LOGGER.enter(CLASSNAME, methodName);
		StringBuffer rList  = local.getReportsService().reportsGenerate1(reportCriteria, false);
		LOGGER.exit(CLASSNAME, methodName);
		return rList;
	}
	
	
	 /**
	  * 
	  * Performance improvement on netstar report
	 * @param file3
	 * @param fileName
	 * @param retlMthNum
	 * @param retlYearNum
	 * 
	 */
	public static void copyAndConvertNetstarReports(File file3,String fileName, String retlMthNum, String retlYearNum)
     {   
				final String methodName = "makeDirectory";
				LOGGER.enter(CLASSNAME, methodName);
				//String fileName = "MB_DPB.FTL.Payment.20160720-123340.txt";
				//File file = new File("C:\\DPBFileProcess\\stage\\MB_DPB.FTL.Payment.20160720-123340.txt");
				String filePath = null;
				filePath = PropertyManager.getPropertyManager().getPropertyAsString("payment.path5");
				String basename = FilenameUtils.getBaseName(fileName);
				String extension = FilenameUtils.getExtension(fileName);
				String[] splittedFileName = basename.split("\\.");
				String simpleFileName = splittedFileName[splittedFileName.length-1];//removing the extension and saving the last index  of array containing year and month.
				String year=simpleFileName.substring(0,4);//this will take year from the filename
				String month=simpleFileName.substring(4, 6);//this will take month from the filename
			    //File file1 = new File(filePath+year);
			    //File file2 = new File(filePath+year+"\\"+month);
				File file1 = new File(filePath+retlYearNum);
			    File file2 = new File(filePath+retlYearNum+File.separator+retlMthNum);
			      boolean success=false;
			      boolean success1=false;
		    
				if(!file1.exists()){
					success =file1.mkdir();
					
				}
				if(!file2.exists())
				{
					success1=file2.mkdir();
				}
			     try 
			     {
			    	 convertTextToPDF(file3,file2,basename);
				 } 
			     catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				LOGGER.exit(CLASSNAME, methodName);
				}

	
     /**
      * 
      * Performance improvement on netstar report
     * @param file
     * @param file1
     * @param fName
     * @return
     * @throws Exception
     */
    public static boolean convertTextToPDF(File file , File file1, String fName) throws Exception  
     {  
    	 final String methodName = "convertTextToPDF";
		 LOGGER.enter(CLASSNAME, methodName);
         FileInputStream fis=null;  
         DataInputStream in=null;  
         InputStreamReader isr=null;  
         BufferedReader br=null;  
         Document pdfDoc = new Document(); 
         try {  
             String output_file =file1.getPath()+File.separator+fName+".pdf"; 
             PdfWriter writer=PdfWriter.getInstance(pdfDoc,new FileOutputStream(output_file));  
             pdfDoc.open();  
             pdfDoc.setMarginMirroring(true);  
             //pdfDoc.setMargins(36, 72, 108,180);  
             //pdfDoc.setMargins(0, 0, 0,0);
             pdfDoc.topMargin(); 
             BaseFont courier = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.EMBEDDED);
             Font myfont = new Font(courier);  
             Font bold_font = new Font();  
             bold_font.setStyle(Font.NORMAL);  
             bold_font.setSize(6);  
             myfont.setStyle(Font.NORMAL);  
             myfont.setSize(6);  
             pdfDoc.add(new Paragraph("\n"));  
  
             if(file.exists()){  
  
                 fis = new FileInputStream(file);  
                 in = new DataInputStream(fis);  
                 isr=new InputStreamReader(in);  
                 br = new BufferedReader(isr);  
                 String strLine;  
                 int i =0;
                 while ((strLine = br.readLine()) != null)  {  
                 	if(strLine.startsWith("&THS") && i != 0){
                 	pdfDoc.newPage();
                 	}
                 		Paragraph para =new Paragraph(strLine+"\n",myfont);  
                         para.setAlignment(Element.ALIGN_LEFT);  
                         pdfDoc.add(para); 
                 		
                         i++;
                 }    
             }     
             else {  
  
                 System.out.println("no such file exists!");  
                 return false;  
             }  
             pdfDoc.close();   
         }  
  
         catch(Exception e) {  
             System.out.println("Exception: " + e.getMessage());  
         }  
         finally {  
  
             if(br!=null)  
             {  
                 br.close();  
             }  
             if(fis!=null)  
             {  
                 fis.close();  
             }  
             if(in!=null)  
             {  
                 in.close();  
             }  
             if(isr!=null)  
             {  
                 isr.close();  
             }  
  
         }  
         LOGGER.exit(CLASSNAME, methodName);
         return true;  
     }  
	
}
