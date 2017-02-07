/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: SchedulerHelper.java
 * Program Version			: 1.0
 * Program Description		: This class is used for process the Scheduler Tasks.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Sep 05, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.business.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.business.view.DpbCommonBeanLocal;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.BulkSchedulerInfo;
import com.mbusa.dpb.common.domain.PaymentAmount;
import com.mbusa.dpb.common.domain.ReportAmount;
import com.mbusa.dpb.common.domain.SchedulerProcess;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.DPBCommonHelper;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.common.util.MailAttachment;
import com.mbusa.dpb.common.util.SendMailDTO;


public class SchedulerHelper {

	private static DPBLog LOGGER = DPBLog.getInstance(SchedulerHelper.class);
	static final private String CLASSNAME = SchedulerHelper.class.getName();
	DpbCommonBeanLocal commonBean = null;
	SendMailDTO sendMailDTO = new SendMailDTO();
	static PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
	private LocalServiceFactory local = new LocalServiceFactory();
	//DPB monthly reconciliation details start
	public static String x = null;//used in writeDifferenceListToExcel method
	//DPB monthly reconciliation details end
	public void doProcess() {
		final String methodName = "doProcess";
		LOGGER.enter(CLASSNAME, methodName);
		String s = "Auto";
		
		try {
			commonBean = local.getDpbCommonService();
			
			// Fetching previous day processes
			List<SchedulerProcess> processes = commonBean.getPrevDayProcs();
			// Saving previous day processes into Proc log and Proc tables if any
			if (processes.size() > 0) {
				commonBean.savePrevDayProcs(processes);
			}
			// Fetch all the processes which will be run today.
			// We are using the same list preDayProcess. So reinitialize to null
			//processes = null;
			processes = commonBean.getTodayTasks();
			
			// Actual process start here
			if (processes.size() > 0) {
				doSchedulerTasks(processes,s);
			} else {
				// send mail to all like there are no tasks scheduled today.
				sendMailDTO.setContent(PropertyManager
						.getPropertyManager().getPropertyAsString("schedule.info1"));
				sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("schedule.report.mail.mailSubject"));
				DPBCommonHelper.sendEmail(sendMailDTO);
				
			}			
			/*if (checkForManualTasks()) {
				// insert all the processes into dpb_proc table with new process ids
				// and skip the remaining processes.
			} else {
				// start processing
			}*/
			
			/*commonBean.updateDayIdAsCurrentDayId(originalDteCal,dteCal,false);
			Calendar c1 = Calendar.getInstance();			
			c1.setTime(originalDteCal);
			c1.add(Calendar.DATE, 1);
			originalDteCal  = new java.sql.Date(c1.getTime().getTime());			
			}	*/
			
		} catch (TechnicalException e) {
			LOGGER.error("Exception occured while calling the bean", e);
		} catch (Exception e) {
			LOGGER.error("Exception occured while doing scheduler tasks", e);
			// send mail to group when it failed
			sendMailDTO.setContent(PropertyManager
					.getPropertyManager().getPropertyAsString("schedule.info2"));
			sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("schedule.report.mail.mailSubject"));
			DPBCommonHelper.sendEmail(sendMailDTO);
		}		
		LOGGER.exit(CLASSNAME, methodName);
	}
	
	private void doSchedulerTasks(List<SchedulerProcess> processes,String s) {
		final String methodName = "doSchedulerTasks";
		LOGGER.enter(CLASSNAME, methodName);
		int count = 0;
		boolean isSuccess = false;
		boolean flag = false;
		FileReadingHelper fileReadingHelper = new FileReadingHelper();
		BonusProcess bonusProcess = new BonusProcess();
		PaymentProcess paymentProcess = new PaymentProcess();
		GenerateReportHelper reportHelper = new GenerateReportHelper();
		//DPB daily payment details 0032484965 start - Kshitija
		ReportAmount ra = new ReportAmount();
		PaymentAmount pa =  new PaymentAmount();
		Date processDate = null;
		if(processes != null && !processes.isEmpty())
		{
			processDate = processes.get(0).getActProcDate();
		}
		//DPB daily payment details 0032484965 end - Kshitija
		// Loop thru all the processes
		// 1. Check if any manual process present (not Report Process), stop the process and update all rest process as failed in Proc Log table.
		//    Update the Proc table for all the processes as manual by updating the over trigger as Manual and updated user is SYSTEM
		// 2. Process all the todays tasks based on the actual process date and type of the process (File, Bonus, Payment and Report)
		//		The Process Type sequence will be as below.
		//		PF	1 (File Process)
		//		AJ  2 (Adjustment Process)
		//		FB	3 (Fixed Bonus)
		//		VB	4 (Variable Bonus)
		//		CB	5 (Commission Bonus)
		//		LB	6 (Leader Ship Bonus)
		//		FP	7 (Fixed Payment)
		//		VP	8 (Variable Payment)
		//		CP	9 (Commission Payment)
		//		LP  10 (Leader Ship Payment)
		//		RP	11 (Report Process)
		String oldProc = IConstants.EMPTY_STR;
		int i = 0;
		for (SchedulerProcess process: processes) {
						   			 
			// Manual task check.
			if (IConstants.TRIGGER_USER_INITIATION.equalsIgnoreCase(process.getFinalTriggerSts())) {
				// if it is Report Process continue the remaining processes
				if (process.getProcTypeSeq() == 11) {
					// send mail
					sendMailDTO.setContent(PropertyManager
							.getPropertyManager().getPropertyAsString("schedule.info3"));
					sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("schedule.report.mail.mailSubject"));
					DPBCommonHelper.sendEmail(sendMailDTO);
					count++;
					continue;
				} // if it is Payment Process continue the remaining processes
				else if (process.getProcTypeSeq() == 7 || process.getProcTypeSeq() == 8 ||
						process.getProcTypeSeq() == 9 ||process.getProcTypeSeq() == 10 ) {
					// send mail
					if (process.getProcTypeSeq() == 7) {
						sendMailDTO.setContent(PropertyManager
								.getPropertyManager().getPropertyAsString("schedule.info7"));
					} else if (process.getProcTypeSeq() == 8) {
						sendMailDTO.setContent(PropertyManager
								.getPropertyManager().getPropertyAsString("schedule.info8"));
					} else if (process.getProcTypeSeq() == 9) {
						sendMailDTO.setContent(PropertyManager
								.getPropertyManager().getPropertyAsString("schedule.info9"));
					} else if (process.getProcTypeSeq() == 10) {
						sendMailDTO.setContent(PropertyManager
								.getPropertyManager().getPropertyAsString("schedule.info10"));
					}
					sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("schedule.payment.mail.mailSubject"));
					DPBCommonHelper.sendEmail(sendMailDTO);
					count++;
					continue;
				} else {
					// do step 1.
					updateTasksAsFailed(processes, count);
					// Send mail to users that manual task is pending, so skip the scheduler process
					sendMailDTO.setContent(PropertyManager
							.getPropertyManager().getPropertyAsString("schedule.info4"));
					sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("schedule.report.mail.mailSubject"));
					DPBCommonHelper.sendEmail(sendMailDTO);
					break;
				}
			} else {
				// do the process
				if(process!= null && oldProc.trim().length() > 0 && !process.getProcType().contains(oldProc)){
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				switch (process.getProcTypeSeq()) {
					case 1:
						// Call the file process helper to process the file. This will return true or false
						isSuccess = fileReadingHelper.processFile(process.getProcId(), IConstants.CREATED_USER_ID, process.getActProcDate());
						oldProc = "PF";
						break;
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
						// Call the Bonus process helper to process the bonus. This will return true or false
						if ((process.getProcType().equalsIgnoreCase("LB")))
							break;
						isSuccess = bonusProcess.startBonusProcess(process.getProcId(), process.getActProcDate(), 
								process.getProcType(), IConstants.TRIGGER_SYSTEM_INITIATION, IConstants.CREATED_USER_ID, IConstants.TRIGGER_SYSTEM_INITIATION);
						oldProc = "B";
						break;
						
					case 7:
					case 8:
					case 9:
					case 10:
						// Call the Payment process helper to process the payment. This will return true or false
						// If return false payment process is failed.
						// Don't skip the rest of the processes.
						if (i==1)
							break;
						//DPB daily payment details 0032484965 (Added parameter)- Kshitija
						flag = paymentProcess.startPaymentProcess(process.getProcId(), process.getProcType(), IConstants.CREATED_USER_ID,
								IConstants.TRIGGER_SYSTEM_INITIATION, process.getActProcDate(),s,pa);
						// if the process failed make the process for manual for today
						if (!flag) {
							List<SchedulerProcess> failedProcess = new ArrayList<SchedulerProcess>();
							failedProcess.add(process);
							updateTasksAsFailed(failedProcess, 0);
						}
						isSuccess = true;
						oldProc = "P";
						i++;
						break;
					case 11:
						// Call the Report process helper to process the report. This will return true or false
						// If return false report process is failed.
						// Don't skip the rest of the processes.
						//DPB daily payment details 0032484965 (Added parameter)- Kshitija
						flag = reportHelper.reportsGenerateScheduler(process.getProcId(), IConstants.CREATED_USER_ID, process.getActProcDate(),ra);
						// if the process failed make the process for manual for today
						oldProc = "R";
						if (!flag) {
							List<SchedulerProcess> failedProcess = new ArrayList<SchedulerProcess>();
							failedProcess.add(process);
							updateTasksAsFailed(failedProcess, 0);
						}
						isSuccess = true;
						break;
					default:
						break;
				}
			}
			if (!isSuccess) {
				updateTasksAsFailed(processes, count);
				// Send mail to users that Auto task is failed, so skip the scheduler process
				sendMailDTO.setContent(PropertyManager
						.getPropertyManager().getPropertyAsString("schedule.info5"));
				sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("schedule.report.mail.mailSubject"));
				DPBCommonHelper.sendEmail(sendMailDTO);
				break;
			} else {
				count++;
			}
		}
		//DPB daily payment details 0032484965  start - Kshitija
		//Add if condition to check if scheduler is Auto once testing is complete - Kshitija
		sendDailyPaymentDetailMail(ra,pa,processDate);
		//DPB daily payment details 0032484965  end - Kshitija
		//DPB monthly reconciliation details start
		if(ra!= null && ra.isMonthlyProcess())
		{
			sendMonthlyPaymentDetailMail(ra,pa,processDate);
		}
		//DPB monthly reconciliation details end
		LOGGER.exit(CLASSNAME, methodName);
	}
	//DPB monthly reconciliation details start
	/**
	 * @param ra
	 * @param pa
	 * @param processDate
	 */
	private void sendMonthlyPaymentDetailMail(ReportAmount ra,
			PaymentAmount pa, Date processDate) {
		LinkedHashMap<String, Double> reportHashMapFTL = ra.getFtlTotalMap();
		LinkedHashMap<String, Double> paymentHashMapFTL = pa.getFtlTotalMap();
		LinkedHashMap<String, List<Double>> finalHashMapFTL = getFinalPaymentReportHashMap(reportHashMapFTL, paymentHashMapFTL);
		LinkedHashMap<String, Double> reportHashMapSmart = ra.getSmartTotalMap();
		LinkedHashMap<String, Double> paymentHashMapSmart = pa.getSmartTotalMap();
		LinkedHashMap<String, List<Double>> finalHashMapSmart =  getFinalPaymentReportHashMap(reportHashMapSmart, paymentHashMapSmart);
		LinkedHashMap<String, Double> reportHashMapVan = ra.getVanTotalMap();
		LinkedHashMap<String, Double> paymentHashMapVan = pa.getVanTotalMap();
		LinkedHashMap<String, List<Double>> finalHashMapVan = getFinalPaymentReportHashMap(reportHashMapVan, paymentHashMapVan);
		LinkedHashMap<String, Double> reportHashMapCars = ra.getPassengerTotalMap();
		LinkedHashMap<String, Double> paymentHashMapCars = pa.getCarTotalMap();
		LinkedHashMap<String, List<Double>> finalHashMapCars = getFinalPaymentReportHashMap(reportHashMapCars, paymentHashMapCars);
		LinkedHashMap<String, Double> reportHashMapAmgPerf = ra.getAmgPerfTotalMap();
		LinkedHashMap<String, Double> paymentHashMapAmgPerf = pa.getAmgPerfTotalMap();
		LinkedHashMap<String, List<Double>> finalHashMapAmgPerf = getFinalPaymentReportHashMap(reportHashMapAmgPerf, paymentHashMapAmgPerf);
		LinkedHashMap<String, Double> reportHashMapAmgElite = ra.getAmgEliteTotalMap();
		LinkedHashMap<String, Double> paymentHashMapAmgElite = pa.getAmgEliteTotalMap();
		LinkedHashMap<String, List<Double>> finalHashMapAmgElite = getFinalPaymentReportHashMap(reportHashMapAmgElite, paymentHashMapAmgElite);
		System.out.println("Final hash map Freightliner: " + finalHashMapFTL);
		System.out.println("Final hash map Smart: " + finalHashMapSmart);
		System.out.println("Final hash map Van: " + finalHashMapVan);
		System.out.println("Final hash map Passenger Cars: " + finalHashMapCars);
		System.out.println("Final hash map AMG Perf: " + finalHashMapAmgPerf);
		System.out.println("Final hash map AMG Elite: " + finalHashMapAmgElite);
		String filePath = PropertyManager.getPropertyManager().getPropertyAsString("monthly.payment.details.file.path");
		String monthlyPaymentDetailsFilePath = filePath+processDate+".xls";
		byte[] bytes= exportToExcel(finalHashMapFTL,finalHashMapSmart,finalHashMapVan, finalHashMapCars,finalHashMapAmgPerf,finalHashMapAmgElite,monthlyPaymentDetailsFilePath);
        SendMailDTO sendMailDTO = new SendMailDTO();
        sendMailDTO.setFrom(PROPERTY_MANAGER.getPropertyAsString("monthly.payment.details.fromMail"));
        String subject = PropertyManager.getPropertyManager().getPropertyAsString("monthly.payment.details.email.subject");
		subject = MessageFormat.format(subject,new Object[]{DateCalenderUtil.getDateInGivenFormat(processDate,IConstants.DATE_FORMAT_MMDDYYYY_WITH_SLASH)});
		String toRecipient = PropertyManager.getPropertyManager().getPropertyAsString("monthly.payment.details.toRecipient");
		String cnt = "PFA file for monthly payment details";
		sendMailDTO.addTORecipient(toRecipient);
		sendMailDTO.setSubject(subject);
		sendMailDTO.setContent(cnt);
        MailAttachment attachment = null;
	    MailAttachment[] attachArray = new MailAttachment[1];
	    attachment = new MailAttachment("Monthly Payment Details_"+processDate+".xls","application/vnd.ms-excel",bytes);
	    attachArray[0] = attachment;
	    sendMailDTO.setAttachments(attachArray);					 
	    DPBCommonHelper.sendMailWithAttachment(sendMailDTO);
	    File file = new File(monthlyPaymentDetailsFilePath);
	    if(file.exists())
	    {
	    	file.delete();
	    }

	}
	//DPB monthly reconciliation details end
	
	//DPB monthly reconciliation details start
	/**
	 * @param finalHashMapFTL
	 * @param finalHashMapSmart
	 * @param finalHashMapVan
	 * @param finalHashMapCars
	 * @param finalHashMapAmgPerf
	 * @param finalHashMapAmgElite
	 * @param monthlyPaymentDetailsFilePath
	 * @return
	 */
	private byte[] exportToExcel(
			LinkedHashMap<String, List<Double>> finalHashMapFTL,
			LinkedHashMap<String, List<Double>> finalHashMapSmart,
			LinkedHashMap<String, List<Double>> finalHashMapVan,
			LinkedHashMap<String, List<Double>> finalHashMapCars,
			LinkedHashMap<String, List<Double>> finalHashMapAmgPerf,
			LinkedHashMap<String, List<Double>> finalHashMapAmgElite,
			String monthlyPaymentDetailsFilePath) {

		File file = new File(monthlyPaymentDetailsFilePath);
		if (!file.exists()) {

			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style = workbook.createCellStyle();

		int rowIndex = 1;
		// FTL
		HSSFSheet differenceSheetFTL = workbook.createSheet("Freightliner");
		HSSFRow rowheadFTL = differenceSheetFTL.createRow(0);
		style.setWrapText(true);
		rowIndex = 1;
		HSSFCell cell = rowheadFTL.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue("Dealer ID");
		HSSFCell cell2 = rowheadFTL.createCell(1);
		cell2.setCellStyle(style);
		cell2.setCellValue("Amount from Report File");
		HSSFCell cell3 = rowheadFTL.createCell(2);
		cell3.setCellStyle(style);
		cell3.setCellValue("Amount From Payment File");

		for (Map.Entry<String, List<Double>> entry : finalHashMapFTL.entrySet()) {
			HSSFRow row = differenceSheetFTL.createRow(rowIndex++);
			int cellIndex = 0;
			HSSFCell cel = row.createCell(cellIndex++);
			cel.setCellStyle(style);
			cel.setCellValue(entry.getKey());// dealer
			if (entry.getValue() != null) {
				HSSFCell cel25 = row.createCell(cellIndex++);
				cel25.setCellStyle(style);
				if (0 < entry.getValue().size()) {
					cel25.setCellValue(entry.getValue().get(0));// amount from
																// report
				}
				HSSFCell cel36 = row.createCell(cellIndex++);
				cel36.setCellStyle(style);
				if (1 < entry.getValue().size()) {
					cel36.setCellValue(entry.getValue().get(1));// amount from
																// payment
				}
			}
		}
		differenceSheetFTL.autoSizeColumn(0);
		differenceSheetFTL.autoSizeColumn(1);
		differenceSheetFTL.autoSizeColumn(2);

		// Smart
		HSSFSheet differenceSheetSmart = workbook.createSheet("Smart");
		HSSFRow rowheadSmart = differenceSheetSmart.createRow(0);
		style.setWrapText(true);
		rowIndex = 1;
		cell = rowheadSmart.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue("Dealer ID");
		cell2 = rowheadSmart.createCell(1);
		cell2.setCellStyle(style);
		cell2.setCellValue("Amount from Report File");
		cell3 = rowheadSmart.createCell(2);
		cell3.setCellStyle(style);
		cell3.setCellValue("Amount From Payment File");

		for (Map.Entry<String, List<Double>> entry : finalHashMapSmart
				.entrySet()) {
			HSSFRow row = differenceSheetSmart.createRow(rowIndex++);
			int cellIndex = 0;
			HSSFCell cel = row.createCell(cellIndex++);
			cel.setCellStyle(style);
			cel.setCellValue(entry.getKey());// dealer
			if (entry.getValue() != null) {
				HSSFCell cel25 = row.createCell(cellIndex++);
				cel25.setCellStyle(style);
				if (0 < entry.getValue().size()) {
					cel25.setCellValue(entry.getValue().get(0));
				}
				HSSFCell cel36 = row.createCell(cellIndex++);
				cel36.setCellStyle(style);
				if (1 < entry.getValue().size()) {
					cel36.setCellValue(entry.getValue().get(1));
				}
			}
		}
		differenceSheetSmart.autoSizeColumn(0);
		differenceSheetSmart.autoSizeColumn(1);
		differenceSheetSmart.autoSizeColumn(2);

		// Van
		HSSFSheet differenceSheetVan = workbook.createSheet("Van");
		HSSFRow rowheadVan = differenceSheetVan.createRow(0);
		style.setWrapText(true);
		rowIndex = 1;
		cell = rowheadVan.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue("Dealer ID");
		cell2 = rowheadVan.createCell(1);
		cell2.setCellStyle(style);
		cell2.setCellValue("Amount from Report File");
		cell3 = rowheadVan.createCell(2);
		cell3.setCellStyle(style);
		cell3.setCellValue("Amount From Payment File");

		for (Map.Entry<String, List<Double>> entry : finalHashMapVan.entrySet()) {
			HSSFRow row = differenceSheetVan.createRow(rowIndex++);
			int cellIndex = 0;
			HSSFCell cel = row.createCell(cellIndex++);
			cel.setCellStyle(style);
			cel.setCellValue(entry.getKey());// dealer
			if (entry.getValue() != null) {
				HSSFCell cel25 = row.createCell(cellIndex++);
				cel25.setCellStyle(style);
				if (0 < entry.getValue().size()) {
					cel25.setCellValue(entry.getValue().get(0));
				}
				HSSFCell cel36 = row.createCell(cellIndex++);
				cel36.setCellStyle(style);
				if (1 < entry.getValue().size()) {
					cel36.setCellValue(entry.getValue().get(1));
				}
			}
		}
		differenceSheetVan.autoSizeColumn(0);
		differenceSheetVan.autoSizeColumn(1);
		differenceSheetVan.autoSizeColumn(2);

		// Cars
		HSSFSheet differenceSheetCars = workbook.createSheet("Cars");
		HSSFRow rowheadCars = differenceSheetCars.createRow(0);
		style.setWrapText(true);
		rowIndex = 1;
		cell = rowheadCars.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue("Dealer ID");
		cell2 = rowheadCars.createCell(1);
		cell2.setCellStyle(style);
		cell2.setCellValue("Amount from Report File");
		cell3 = rowheadCars.createCell(2);
		cell3.setCellStyle(style);
		cell3.setCellValue("Amount From Payment File");

		for (Map.Entry<String, List<Double>> entry : finalHashMapCars
				.entrySet()) {
			HSSFRow row = differenceSheetCars.createRow(rowIndex++);
			int cellIndex = 0;
			HSSFCell cel = row.createCell(cellIndex++);
			cel.setCellStyle(style);
			cel.setCellValue(entry.getKey());// dealer
			if (entry.getValue() != null) {
				HSSFCell cel25 = row.createCell(cellIndex++);
				cel25.setCellStyle(style);
				if (0 < entry.getValue().size()) {
					cel25.setCellValue(entry.getValue().get(0));
				}
				HSSFCell cel36 = row.createCell(cellIndex++);
				cel36.setCellStyle(style);
				if (1 < entry.getValue().size()) {
					cel36.setCellValue(entry.getValue().get(1));
				}
			}
		}
		differenceSheetCars.autoSizeColumn(0);
		differenceSheetCars.autoSizeColumn(1);
		differenceSheetCars.autoSizeColumn(2);
		// AMG Perf
		HSSFSheet differenceSheetAmgPerf = workbook.createSheet("AMG Perf");
		HSSFRow rowheadAmgPerf = differenceSheetAmgPerf.createRow(0);
		style.setWrapText(true);
		rowIndex = 1;
		cell = rowheadAmgPerf.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue("Dealer ID");
		cell2 = rowheadAmgPerf.createCell(1);
		cell2.setCellStyle(style);
		cell2.setCellValue("Amount from Report File");
		cell3 = rowheadAmgPerf.createCell(2);
		cell3.setCellStyle(style);
		cell3.setCellValue("Amount From Payment File");

		for (Map.Entry<String, List<Double>> entry : finalHashMapAmgPerf
				.entrySet()) {
			HSSFRow row = differenceSheetAmgPerf.createRow(rowIndex++);
			int cellIndex = 0;
			HSSFCell cel = row.createCell(cellIndex++);
			cel.setCellStyle(style);
			cel.setCellValue(entry.getKey());// dealer
			if (entry.getValue() != null) {
				HSSFCell cel25 = row.createCell(cellIndex++);
				cel25.setCellStyle(style);
				if (0 < entry.getValue().size()) {
					cel25.setCellValue(entry.getValue().get(0));
				}
				HSSFCell cel36 = row.createCell(cellIndex++);
				cel36.setCellStyle(style);
				if (1 < entry.getValue().size()) {
					cel36.setCellValue(entry.getValue().get(1));
				}
			}
		}
		differenceSheetAmgPerf.autoSizeColumn(0);
		differenceSheetAmgPerf.autoSizeColumn(1);
		differenceSheetAmgPerf.autoSizeColumn(2);

		// AMG Elite
		HSSFSheet differenceSheetAmgElite = workbook.createSheet("AMG Elite");
		HSSFRow rowheadAmgElite = differenceSheetAmgElite.createRow(0);
		style.setWrapText(true);
		rowIndex = 1;
		cell = rowheadAmgElite.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue("Dealer ID");
		cell2 = rowheadAmgElite.createCell(1);
		cell2.setCellStyle(style);
		cell2.setCellValue("Amount from Report File");
		cell3 = rowheadAmgElite.createCell(2);
		cell3.setCellStyle(style);
		cell3.setCellValue("Amount From Payment File");

		for (Map.Entry<String, List<Double>> entry : finalHashMapAmgElite
				.entrySet()) {
			HSSFRow row = differenceSheetAmgElite.createRow(rowIndex++);
			int cellIndex = 0;
			HSSFCell cel = row.createCell(cellIndex++);
			cel.setCellStyle(style);
			cel.setCellValue(entry.getKey());// dealer
			if (entry.getValue() != null) {
				HSSFCell cel25 = row.createCell(cellIndex++);
				cel25.setCellStyle(style);
				if (0 < entry.getValue().size()) {

					cel25.setCellValue(entry.getValue().get(0));
				}
				HSSFCell cel36 = row.createCell(cellIndex++);
				cel36.setCellStyle(style);
				if (1 < entry.getValue().size()) {

					cel36.setCellValue(entry.getValue().get(1));
				}
			}
		}
		differenceSheetAmgElite.autoSizeColumn(0);
		differenceSheetAmgElite.autoSizeColumn(1);
		differenceSheetAmgElite.autoSizeColumn(2);

		FileOutputStream fos = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bytes = null;
		try {
			fos = new FileOutputStream(file);
			workbook.write(fos);
			workbook.write(bos);
			bytes = bos.toByteArray();
			fos.flush();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				/*if (workbook != null) {
					workbook.close();
				}*/
				if (bos != null) {
					bos.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;

	}
	//DPB monthly reconciliation details end
	
	//DPB monthly reconciliation details start
	/**
	 * @param reportHashMap
	 * @param paymentHashMap
	 * @return
	 */
	private LinkedHashMap<String, List<Double>> getFinalPaymentReportHashMap(
			HashMap<String, Double> reportHashMap,
			HashMap<String, Double> paymentHashMap) {
		String key2 = null;
		LinkedHashMap<String, List<Double>> finalHashMap = new LinkedHashMap<String, List<Double>>();
		for (Map.Entry<String, Double> entry : reportHashMap.entrySet()) {
			ArrayList<Double> arr1 = new ArrayList<Double>();
			String key = entry.getKey();
			arr1.add(entry.getValue());
			int i = 0;
			for (Map.Entry<String, Double> entry2 : paymentHashMap.entrySet()) {
				key2 = entry2.getKey();
				if (key.equals(key2)) {
					arr1.add(entry2.getValue());
					break;
				} else {
					i = 1;
				}
			}
			if ((i == 1) && (arr1.size() < 2)) {
				arr1.add(0.0);
			}
			finalHashMap.put(key, arr1);
		}
		for (Map.Entry<String, Double> entry : paymentHashMap.entrySet()) {
			ArrayList<Double> arr2 = new ArrayList<Double>();
			String key = entry.getKey();
			arr2.add(entry.getValue());
			int i = 0;
			for (Map.Entry<String, Double> entry2 : reportHashMap.entrySet()) {
				key2 = entry2.getKey();
				if (key.equals(key2)) {
					arr2.add(entry2.getValue());
					break;
				} else {
					i = 1;
				}
			}
			if ((i == 1) && (arr2.size() < 2)) {
				arr2.add(0.0);
			}
			Collections.reverse(arr2);
			finalHashMap.put(key, arr2);
		}
		return finalHashMap;
	}
	//DPB monthly reconciliation details end
	
	/**
	 * DPB daily payment details 0032484965 (To send Mail)- Kshitija
	 * @param ra
	 * @param pa
	 * @param processDate
	 */
	private void sendDailyPaymentDetailMail(ReportAmount ra, PaymentAmount pa, Date processDate) {
		System.out.println(ra.getCVPTotal());
		System.out.println(ra.getMBDealTotal());
		System.out.println(pa.getCVPTotal());
		System.out.println(pa.getMBDealTotal());
		//DPB monthly reconciliation details start
		System.out.println(ra.getFtlTotalMap());
		System.out.println(pa.getFtlTotalMap());
		//DPB monthly reconciliation details end
		double totalFromPaymentFile = pa.getCVPTotal() + pa.getMBDealTotal();
		double totalFromReportFile = ra.getMBDealTotal()+ra.getCVPTotal();
		SendMailDTO sendMailDTO = new SendMailDTO();
		String subject = PropertyManager.getPropertyManager().getPropertyAsString("daily.payment.details.email.subject");
		subject = MessageFormat.format(subject,new Object[]{DateCalenderUtil.getDateInGivenFormat(processDate,IConstants.DATE_FORMAT_MMDDYYYY_WITH_SLASH)});
		String cnt = "<html><table BORDER=1><tr bgcolor='#808080'><th>Type of Bonus</th><th>Amount From Payment File</th><th>Amount From Report File</th></tr>" +
				"<tr><td>Total CVP</td><td>"+pa.getCVPTotal()+"</td><td>"+ra.getCVPTotal()+"</td></tr>" +
						"<tr><td>Total MB Deal</td><td>"+pa.getMBDealTotal()+"</td><td>"+ra.getMBDealTotal()+"</td>" +
								"<tr><td>Total For The Day</td><td>"+totalFromPaymentFile+"</td><td>"+totalFromReportFile+"</td></tr></table></html>";
		String toRecipient = PropertyManager.getPropertyManager().getPropertyAsString("daily.payment.details.toRecipient");
		sendMailDTO.addTORecipient(toRecipient);
		sendMailDTO.setSubject(subject);
		sendMailDTO.setContent(cnt);	
		DPBCommonHelper.sendEmail(sendMailDTO);
	}

	private void updateTasksAsFailed(List<SchedulerProcess> processes,
			int count) {
		final String methodName = "updateTasksAsFailed";
		LOGGER.enter(CLASSNAME, methodName);
		try {
			commonBean.updateTasksAsFailed (processes, count);
		} catch (Exception e) {
			LOGGER.error("Records failed while updating the failed tasks.", e);
			// send mail to user
			sendMailDTO.setContent(PropertyManager
					.getPropertyManager().getPropertyAsString("schedule.info6"));
			sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("schedule.report.mail.mailSubject"));
			DPBCommonHelper.sendEmail(sendMailDTO);
		}
		LOGGER.exit(CLASSNAME, methodName);
	}

	private boolean checkForManualTasks () {
		final String methodName = "checkForManualTasks";
		LOGGER.enter(CLASSNAME, methodName);
		boolean isManualTaskAvailable = false;
		List<String> manualTasks = commonBean.checkForManualTasks ();
		if (manualTasks.size() > 0) {
			// Send mail to user for using manual tasks list
			sendMailDTO.setContent(PropertyManager
					.getPropertyManager().getPropertyAsString("schedule.info7"));
			sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("schedule.report.mail.mailSubject"));
			DPBCommonHelper.sendEmail(sendMailDTO);
			isManualTaskAvailable = true;
		} 
		LOGGER.exit(CLASSNAME, methodName);
		return isManualTaskAvailable;
	}
	/**
	 * This is a temporary method used for manual execution.
	 * @param processDate
	 */
	public void doProcess(BulkSchedulerInfo bulkInfo) {
		final String methodName = "doProcess";
		LOGGER.enter(CLASSNAME, methodName);
		try {
			commonBean = local.getDpbCommonService();
			
			java.sql.Date todaysDate = DateCalenderUtil.getCurrentDateTime();
			java.sql.Date originalDteCal = bulkInfo.getScheduleStartDate();// 8311;
			java.sql.Date d = null;
			String s = "Bulk";
			
			String mailCnt = "Scheduler bulk process started from:" +bulkInfo.getScheduleStartDate()+ " for next :" +bulkInfo.getNoOfDays() +" days including "+ bulkInfo.getScheduleStartDate()+"."; 
			sendBulkexecutionnotificationMail(mailCnt,"Scheduler bulk process start notification");
			todaysDate = bulkInfo.getTodaysDte() ;// DateCalenderUtil.convertStringToDate(todaysDate);
			String exeDtes = "";
			for(int i = 0; i < bulkInfo.getNoOfDays();i++){					
				commonBean.updateDayIdAsCurrentDayId(todaysDate,originalDteCal,true);
				List<SchedulerProcess> processes = null;//commonBean.getPrevDayProcs();
				processes = null;
				processes = commonBean.getTodayTasks();
				if (processes.size() > 0) {
					doSchedulerTasks(processes,s);
				} else {
					// send mail to all like there are no tasks scheduled today.
					sendMailDTO.setContent(PropertyManager
							.getPropertyManager().getPropertyAsString("schedule.info1"));
					sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("schedule.report.mail.mailSubject"));
					DPBCommonHelper.sendEmail(sendMailDTO);						
				}			
									
				commonBean.updateDayIdAsCurrentDayId(originalDteCal,todaysDate,false);
				exeDtes = exeDtes + " "+originalDteCal.toString();
				Calendar c1 = Calendar.getInstance();			
				c1.setTime(originalDteCal);
				c1.add(Calendar.DATE, 1);
				originalDteCal  = new java.sql.Date(c1.getTime().getTime());			
				}
			mailCnt = "Scheduler bulk process started from:" +bulkInfo.getScheduleStartDate()+ " for next :" +bulkInfo.getNoOfDays() +" days including "+ bulkInfo.getScheduleStartDate()+"."+
					" List of days consider during this execution is as "+exeDtes;
			sendBulkexecutionnotificationMail(mailCnt,"Scheduler bulk process Completed notification");
		 
		
				
		} catch (TechnicalException e) {
			LOGGER.error("Exception occured while calling the bean", e);
		} catch (Exception e) {
			LOGGER.error("Exception occured while doing scheduler tasks", e);
			e.printStackTrace();
			// send mail to group when it failed
			sendMailDTO.setContent(PropertyManager
					.getPropertyManager().getPropertyAsString("schedule.info2"));
			sendMailDTO.setSubject(PROPERTY_MANAGER.getPropertyAsString("schedule.report.mail.mailSubject"));
			DPBCommonHelper.sendEmail(sendMailDTO);
		}		
		LOGGER.exit(CLASSNAME, methodName);
	}
	
	//Data Cleanup for process rerun start
	/**
	 * @param procDate
	 * @return isSuccesss
	 */
	public boolean cleanUpData(java.sql.Date procDate) {
		final String methodName = "cleanUpData";
		LOGGER.enter(CLASSNAME, methodName);
		boolean isSuccess = false;
		Map<Integer, String> result = new HashMap<Integer, String>();
		TreeMap<Integer, String> tMap = new TreeMap<Integer, String>(result);
		StringBuffer mailCnt = new StringBuffer();
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			commonBean = local.getDpbCommonService();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String procDte= sdf.format(procDate);
			String procLogTabCount = "0";
			String procTabCount = "0";
			String calcTabCount = "0";
			String kpiFilExtrtTabCount = "0";
			result = commonBean.cleanUpData(procDte);
			
			tMap.putAll(result);
			for(Map.Entry<Integer, String>entry: tMap.entrySet()){
				if(entry.getKey() == 5){
					throw new Exception();
				}
				else
				if(entry.getKey()==1){
					procLogTabCount = entry.getValue();
				}
				else if(entry.getKey() == 2){
					calcTabCount = entry.getValue();
				}
				else if(entry.getKey() == 3){
					procTabCount = entry.getValue();
				}
				else if(entry.getKey() == 4){
					kpiFilExtrtTabCount = entry.getValue();
				}
			}
			
			//Form the mail content
			mailCnt.append("<html><body><h3>Cleanup process output : ").append(procDte)
				.append("</h3><br><table border='3' width='400'><tr><th>Table Name</th><th>No. of records</th><th>Action</th></tr><tr><td>DPB_PROC_LOG</td><td>")
				.append(procLogTabCount)
				.append("</td><td>Deleted</td></tr><tr><td>DPB_CALC</td><td>")
				.append(calcTabCount)
				.append("</td><td>Deleted</td></tr><tr><td>DPB_KPI_FIL_EXTRT</td><td>")
				.append(kpiFilExtrtTabCount)
				.append("</td><td>Deleted</td></tr><tr><td>DPB_PROC</td><td>")
				.append(procTabCount)
				.append("</td><td>Updated</td></tr></table></body></html>");
			
			sendBulkexecutionnotificationMail(mailCnt.toString(),"Clean Up Process Success notification");
				
		} catch (TechnicalException e) {
			LOGGER.error("Exception occured while calling the bean", e);
			e.printStackTrace(pw); 
			mailCnt.append(sw.toString());
			sendBulkexecutionnotificationMail(mailCnt.toString(),"Clean Up Process failed notification");
		} catch (Exception e) {
			LOGGER.error("Exception occured while doing scheduler tasks", e);
			e.printStackTrace(pw); 
			mailCnt.append(sw.toString());
			sendBulkexecutionnotificationMail(mailCnt.toString(),"Clean Up Process failed notification");
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
				if (sw != null) {
					sw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return isSuccess;
	}
	//Data Cleanup for process rerun end
	public void sendBulkexecutionnotificationMail(String msg,String subject){
		sendMailDTO = new SendMailDTO();
		sendMailDTO.setContent(msg);
		sendMailDTO.setFrom(PROPERTY_MANAGER.getPropertyAsString("mail.bulk.fromRecipient",""));
		sendMailDTO.setSubject(subject);
		sendMailDTO.addTORecipient(PROPERTY_MANAGER.getPropertyAsString("mail.bulk.toRecipient",""));
		sendMailDTO.setSubject(subject);
		DPBCommonHelper.sendEmail(sendMailDTO);
	}
}

