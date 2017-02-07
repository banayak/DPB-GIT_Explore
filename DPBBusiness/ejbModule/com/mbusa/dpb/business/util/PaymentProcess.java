/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: PaymentProcess.java
 * Program Version			: 1.0
 * Program Description		: This class is used to generate Payment file
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 31, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.business.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.Format;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import com.mbusa.dpb.business.factories.LocalServiceFactory;
import com.mbusa.dpb.business.view.DpbCommonBeanLocal;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.BonusInfo;
import com.mbusa.dpb.common.domain.InvalidReason;
import com.mbusa.dpb.common.domain.LeadershipBonusDetails;
import com.mbusa.dpb.common.domain.LeadershipInfo;
import com.mbusa.dpb.common.domain.PaymentAmount;
import com.mbusa.dpb.common.domain.PaymentDealerInfo;
import com.mbusa.dpb.common.domain.PaymentInfo;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.DPBCommonHelper;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.common.util.MailAttachment;
import com.mbusa.dpb.common.util.SendMailDTO;

public class PaymentProcess {

  private static DPBLog LOGGER = DPBLog.getInstance(PaymentProcess.class);
  PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
  static final private String CLASSNAME = PaymentProcess.class.getName();
  private LocalServiceFactory local = new LocalServiceFactory();
  private DPBCommonHelper commonHelper = new DPBCommonHelper();
  private List <InvalidReason> invalidRcrdsList = null;
  private String failReason = IConstants.EMPTY_STR;
  MailAttachment attachment = null;
  MailAttachment[] attachArray = new MailAttachment[1];
  private String schedulerType = IConstants.EMPTY_STR;
  private Integer currentProcPgmID;
  public enum VehicleTypeEnum {	P, V, S, F;  }
  
  
    public boolean startPaymentProcess(int processId, String processType, String userId,String callType,java.sql.Date d,String s, PaymentAmount pa) {
	  final String methodName = "startPaymentProcess";
	  LOGGER.enter(CLASSNAME, methodName);
	  boolean procStatus = true;  
	  boolean isPaymentConsider = true;       
	  String paymentFileName = null;
	  String paymentFileName1 = null;
	  String paymentFileName2 = null;
	  String paymentFileName3 = null;
	  String paymentFileName4 = null;
	  String paymentFileName5 = null;
	  String paymentFileNameAMG = null;
	  String paymentFilePath = PropertyManager.getPropertyManager().getPropertyAsString("payment.path");
	  String paymentFilePath2 = PropertyManager.getPropertyManager().getPropertyAsString("payment.path2");
	  String tType = !isEmptyOrNullString(callType) && IConstants.TRIGGER_SYSTEM_INITIATION.equalsIgnoreCase(callType)? IConstants.TRIGGER_SYSTEM_INITIATION_DESC :IConstants.TRIGGER_USER_INITIATION_DESC;
	  java.sql.Date procDate = null;
	  DpbCommonBeanLocal commonBean = null;
	  List<String> lList = new ArrayList<String>();
	  java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
	  RtlCalenderDefinition rtlDim = MasterDataLookup.getInstance().getRtlCalByDate(currentDate);
	  java.sql.Date monthEndDate = rtlDim.getDteRetlMthBeg();
	  java.sql.Date qtrEndDate = rtlDim.getDteRetlQtrBeg();
	  List<LeadershipBonusDetails> ldrspProgramList = null; 
	  Integer pgmID = null;
	  java.sql.Date startDate = null;
	  java.sql.Date endDate = null;
	  java.sql.Date rtlstartDate = null;
	  java.sql.Date rtlendDate = null;
	  boolean ldrsp = false;
	  Integer id = Integer.valueOf(processId);
	  
	  try
	  {
		  LOGGER.info("Payment Process started");  
		  commonBean = local.getDpbCommonService();
		 // if(processType!= null && !IConstants.LEADERSHIP_PAYMENT_PROCESS.equalsIgnoreCase(processType)){
		  
		  paymentFileName = getPaymentFileName(processType);
		  paymentFileName1 = getPaymentFileName1();
		  paymentFileName2 = getPaymentFileName2();
		  paymentFileName3 = getPaymentFileName3();
		  paymentFileName4 = getPaymentFileName4();
		  paymentFileNameAMG = getAMGPaymentFileName();
		  
		  //commonBean = local.getDpbCommonService();
		  
		  if (s.equalsIgnoreCase("Bulk")){
			  procDate = d;
			  currentDate = d;
			  RtlCalenderDefinition rtlDim1 = MasterDataLookup.getInstance().getRtlCalByDate(currentDate);
			  monthEndDate = rtlDim1.getDteRetlMthBeg();
			  qtrEndDate = rtlDim1.getDteRetlQtrBeg();
		  }
		  else
		  procDate = currentDate;
		  
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(procDate);
		  
		  cal.add(Calendar.DATE, -1);
		  java.sql.Date prevDate = new java.sql.Date(cal.getTime().getTime());
		  RtlCalenderDefinition rtlDim2 = MasterDataLookup.getInstance().getRtlCalByDate(prevDate);
		  java.sql.Date beginDate = rtlDim2.getDteRetlMthBeg();
		  java.sql.Date qtrBeginDate = rtlDim2.getDteRetlQtrBeg();
		  
		  cal.setTime(qtrBeginDate);
		  cal.add(Calendar.DATE, 1);
		  java.sql.Date qtrDate = new java.sql.Date(cal.getTime().getTime());
		  
		  if(processType!= null && !IConstants.LEADERSHIP_PAYMENT_PROCESS.equalsIgnoreCase(processType)){
		  
		  List<Integer> procIdList = commonBean.getProcIdDetails(currentDate);
		  for (Iterator<Integer> procId = procIdList.iterator(); procId.hasNext();){
			  	Integer pid = procId.next();
			  	commonBean.insertIntoProcessLog(commonHelper.getProcessLog(pid,	
							 IConstants.START_PAYMENT_PROCESS ,	IConstants.PROC_STATUS_IN_PROCESS, IConstants.CREATED_USER_ID));
		  }
		  LOGGER.info("current date..."+ currentDate );
		  LOGGER.info("month end  date..."+ monthEndDate );
		  LOGGER.info("quarter begin date..."+ qtrBeginDate );
		  LOGGER.info("quarter end date..."+ qtrEndDate );
		  LOGGER.info("begin date..."+ beginDate );
		  LOGGER.info("previous date..."+ prevDate );
		  LOGGER.info("Qtr date..."+ qtrDate );
		  		  
		  int n=0;
		  if(currentDate.toString().equals(qtrEndDate.toString()) || (currentDate.toString().equals(monthEndDate.toString())))
			  n=6;//n=5; Separating AMG Payment file from regular Cars payment file (11-Jul-2016). 
		  else 
			  n=1;
		  // DPB daily payment details 0032484965 start - Kshitija
		  double cvpTotal = 0;
		  double mbDealTotal = 0;
		  String dlrId = null;
		  // DPB daily payment details 0032484965 end - Kshitija
		  //DPB monthly reconciliation details start
		  LinkedHashMap<String, Double> ftlTotalMap = new LinkedHashMap<String,Double>();
		  LinkedHashMap<String,Double> smartTotalMap = new LinkedHashMap<String,Double>();
		  LinkedHashMap<String, Double> vanTotalMap = new LinkedHashMap<String,Double>();
		  LinkedHashMap<String,Double> carTotalMap = new LinkedHashMap<String,Double>();
		  LinkedHashMap<String,Double> amgPerfTotalMap = new LinkedHashMap<String,Double>();
		  LinkedHashMap<String,Double> amgEliteTotalMap = new LinkedHashMap<String,Double>();
		  double ftlTotalDlrWise = 0;
		  double smartTotalDlrWise = 0;
		  double vanTotalDlrWise = 0;
		  double carTotalDlrWise = 0;
		  double amgTotalDlrWise = 0;
		  List<String> amgPerfDealers =  DPBCommonHelper.getAmgPerfDealers();
		  List<String> amgEliteDealers = DPBCommonHelper.getAmgEliteDealers();
		  //DPB monthly reconciliation details end 
		  for (int k=0; k<n;k++){
			  String dlrLine = IConstants.EMPTY_STR;
			  int vehCount = 0;
			  int counter = 0;
			  List<PaymentDealerInfo> dealerList = commonBean.getDealerData(beginDate, monthEndDate,prevDate,currentDate,qtrEndDate,qtrBeginDate,qtrDate,k);
			  List<PaymentInfo> paymentList = commonBean.getPaymentData(beginDate, monthEndDate,prevDate,currentDate,qtrEndDate,qtrBeginDate,qtrDate,k);
			  
			  dlrId = null;
			  for (Iterator<PaymentDealerInfo> dlr = dealerList.iterator(); dlr.hasNext();){
				  PaymentDealerInfo dealer = dlr.next();
				  dlrLine = this.createDealerLine(dealer,procDate,monthEndDate,k);
				// DPB daily payment details 0032484965 start - Kshitija
				 if(k == 0 && pa != null)
				 {
				  if(dealer.getText().equalsIgnoreCase("CVP"))
				  {
					  //calculate totals
					  cvpTotal = cvpTotal+dealer.getTotal();
				  }
				  else if(dealer.getText().equalsIgnoreCase("MBDeal"))
				  {
					  mbDealTotal = mbDealTotal+dealer.getTotal();
				  }
				 }
				 // DPB daily payment details 0032484965 end - Kshitija
				//DPB monthly reconciliation details start
				 if(k == 1 && pa != null)
				 {
					  if(dlrId == null || (dlrId != null && !dlrId.equalsIgnoreCase(dealer.getDlrId())))
					  {
						  ftlTotalDlrWise = 0;
					  }
					  ftlTotalDlrWise = ftlTotalDlrWise+dealer.getTotal();
					  dlrId = dealer.getDlrId();
					  ftlTotalMap.put(dealer.getDlrId(), ftlTotalDlrWise);
				 }
				 if(k == 2 && pa != null)
				 {
					 if(dlrId == null || (dlrId != null && !dlrId.equalsIgnoreCase(dealer.getDlrId())))
					  {
						  smartTotalDlrWise = 0;
					  }
					  smartTotalDlrWise = smartTotalDlrWise+dealer.getTotal();
					  dlrId = dealer.getDlrId();
					  smartTotalMap.put(dealer.getDlrId(), smartTotalDlrWise);
				 }
				 if(k == 3 && pa != null)
				 {
					 if(dlrId == null || (dlrId != null && !dlrId.equalsIgnoreCase(dealer.getDlrId())))
					  {
						 vanTotalDlrWise = 0;
					  }
					  vanTotalDlrWise = vanTotalDlrWise+dealer.getTotal();
					  dlrId = dealer.getDlrId();
					  vanTotalMap.put(dealer.getDlrId(), vanTotalDlrWise);
				 }
				 if(k == 4 && pa != null)
				 {
					 if(dlrId == null || (dlrId != null && !dlrId.equalsIgnoreCase(dealer.getDlrId())))
					  {
						  carTotalDlrWise = 0;
					  }
					  carTotalDlrWise = carTotalDlrWise+dealer.getTotal();
					  dlrId = dealer.getDlrId();
					  carTotalMap.put(dealer.getDlrId(), carTotalDlrWise);
				 }
				 if(k == 5 && pa != null)
				 {
					 if(dlrId == null || (dlrId != null && !dlrId.equalsIgnoreCase(dealer.getDlrId())))
					  {
						  amgTotalDlrWise = 0;
					  }
					  amgTotalDlrWise = amgTotalDlrWise+dealer.getTotal();
					  dlrId = dealer.getDlrId();
					  if(amgPerfDealers.contains(dlrId))
					  {
						  amgPerfTotalMap.put(dealer.getDlrId(), amgTotalDlrWise);
					  }
					  else if(amgEliteDealers.contains(dlrId))
					  {
						  amgEliteTotalMap.put(dealer.getDlrId(), amgTotalDlrWise);
					  }
				 }
				//DPB monthly reconciliation details end
				  int j= vehCount,m=0,u=1;
				  if(dealer.getVehCount()>950){
					dlrLine = bigDealer(dlrLine,dealer,paymentList,j);
				  }
				 				  
				  lList.add(dlrLine);
				  vehCount = dealer.getVehCount()+ counter;
				  String bnsLine = IConstants.EMPTY_STR;
				  
				  m = j+950;
				  for (int i = counter; i < vehCount; i++){
					  j++;
					  PaymentInfo pymt = paymentList.get(i);
					  bnsLine = this.createBonusLine(pymt);
					  if(j > m){
						  dlrLine = bigDealer2(dlrLine,dealer,paymentList,m,vehCount,u);
						  lList.add(dlrLine);
						  m = (j-1)+950;
						  u++;	
					  }
					  if (!(pymt.getBnsCalcAmt()== 0))
						  lList.add(bnsLine);
				      counter++;
				  }
			  }
							
		      String footer = createFooterRow(lList.size() + 2);
		      lList.add(footer);
		      List<String> fLines = new ArrayList<String>();
		      String paymentHeaderRow = createHeaderLineItem();
			fLines.add(paymentHeaderRow);
			fLines.addAll(lList);
				
			if(k==0)
				createPaymentFile(fLines, paymentFilePath2 + paymentFileName);
			else if (k==1)
				createPaymentFile(fLines, paymentFilePath2 + paymentFileName1);
			else if (k==2)
				createPaymentFile(fLines, paymentFilePath2 + paymentFileName2);
			else if (k==3)
				createPaymentFile(fLines, paymentFilePath2 + paymentFileName3);
			else if (k==4)
				createPaymentFile(fLines, paymentFilePath2 + paymentFileName4);
			else if (k==5)
				createPaymentFile(fLines, paymentFilePath2 + paymentFileNameAMG);
				
			isPaymentConsider = true;
			procStatus = true;
			lList.clear();
			LOGGER.info("Payment process ended ");
				
			if(procStatus && isPaymentConsider){
				commonBean.updateCalcData(beginDate,prevDate,currentDate,monthEndDate,qtrEndDate,qtrBeginDate,k);
				List<Integer> procIdList1 = commonBean.getProcIdDetails(currentDate);
				for (Iterator<Integer> procId = procIdList1.iterator(); procId.hasNext();){
					Integer pid = procId.next();
					commonBean.insertIntoProcessLog(commonHelper.getProcessLog(pid,	
								 IConstants.PAYMENT_PROCESS_FILE_GENERATED_SUCCESSFULLY , 
					        	 IConstants.PROC_STATUS_SUCCESS , IConstants.CREATED_USER_ID));
					sendProcessNotificationMail(IConstants.PROC_STATUS_SUCCESS,pid,processType,procDate,tType);
				}
			}
			else{
				procStatus = false;
				List<Integer> procIdList2 = commonBean.getProcIdDetails(currentDate);
				for (Iterator<Integer> procId = procIdList2.iterator(); procId.hasNext();){
					Integer pid = procId.next();
					commonBean.insertIntoProcessLog(commonHelper.getProcessLog(pid,	
							 IConstants.PAYMENT_PROCESS_FILE_GENERATION_FAIL , 
				        	 IConstants.PROC_STATUS_FAILURE , IConstants.CREATED_USER_ID));
					sendNotificationAndUpdateProcessLogs(commonBean,pid,userId,processType,procDate,tType);
				}
			}
				
		  }
		  //DPB daily payment details 0032484965 start- Kshitija
		  if(pa != null)
		  {  
		  if(cvpTotal != 0)
		  {
			  pa.setCVPTotal(cvpTotal);
		  }
		  if(mbDealTotal != 0)
		  {
			  pa.setMBDealTotal(mbDealTotal);
		  }
		//DPB monthly reconciliation details start
		  if(n==6)
		  {
			  pa.setFtlTotalMap(ftlTotalMap);
			  pa.setSmartTotalMap(smartTotalMap);
			  pa.setVanTotalMap(vanTotalMap);
			  pa.setCarTotalMap(carTotalMap);
			  pa.setAmgPerfTotalMap(amgPerfTotalMap);
			  pa.setAmgEliteTotalMap(amgEliteTotalMap);
		  }
		//DPB monthly reconciliation details start
		  }
		  //DPB daily payment details 0032484965 end- Kshitija
		  }
		  else{
			//ldrshp bonus
			  String dlrLine = IConstants.EMPTY_STR;
			  String bnsLine = IConstants.EMPTY_STR;
			  List<String> fLines = new ArrayList<String>();
			  paymentFileName5 = getPaymentFileName5();
			  
			  commonBean.insertIntoProcessLog(commonHelper.getProcessLog(processId,IConstants.PAYMENT_PROCESS_SCHEDULED,
		        		IConstants.PROC_STATUS_IN_PROCESS, userId));
			  ldrspProgramList = commonBean.retrieveLdrShipProcessDetails(id,callType);
			  for(int i = 0;i < ldrspProgramList.size();i++) {
				  pgmID = ldrspProgramList.get(i).getLdrshipid();
				  String rtlstDte = ldrspProgramList.get(i).getRtlStartDate();
				  rtlstartDate = DateCalenderUtil.convertStringToSQLFormat(rtlstDte);
				  String rtlstendDte = ldrspProgramList.get(i).getRtlEndDate();
				  rtlendDate = DateCalenderUtil.convertStringToSQLFormat(rtlstendDte);
				  
				  ldrsp = true;
				  
				  List<LeadershipInfo> ldrList = commonBean.getLdrshipData(pgmID);
				  
				  for (Iterator<LeadershipInfo> ldr = ldrList.iterator(); ldr.hasNext();){
					  LeadershipInfo leader = ldr.next();
					  dlrLine = this.ldrshipDealerLine(leader,procDate,monthEndDate);
					  lList.add(dlrLine);
					  bnsLine = this.ldrshipBonusLine(leader,procDate,monthEndDate);
					  lList.add(bnsLine);
				  }
				  
				  String footer = createFooterRow(lList.size() + 2);
			      lList.add(footer);
			      String headerRow = createHeaderLineItem();
			      fLines.add(headerRow);
			      fLines.addAll(lList);
			      createPaymentFile(fLines, paymentFilePath + paymentFileName5); 
					  
			      procStatus = true; 
			      isPaymentConsider = true;
			      
			      
			      if(procStatus && isPaymentConsider){
					commonBean.insertIntoProcessLog(commonHelper.getProcessLog(id,IConstants.PAYMENT_PROCESS_FILE_GENERATED_SUCCESSFULLY , 
					IConstants.PROC_STATUS_SUCCESS , IConstants.CREATED_USER_ID));
				    sendProcessNotificationMail(IConstants.PROC_STATUS_SUCCESS,id,processType,procDate,tType);
				  }
				  else{
					procStatus = false;
					commonBean.insertIntoProcessLog(commonHelper.getProcessLog(id,IConstants.PAYMENT_PROCESS_FILE_GENERATION_FAIL , 
				    IConstants.PROC_STATUS_FAILURE , IConstants.CREATED_USER_ID));
					sendNotificationAndUpdateProcessLogs(commonBean,id,userId,processType,procDate,tType);
				  }
			  }        	
		
		  }
      }catch (TechnicalException te) {
    	  sendNotificationAndUpdateProcessLogs(commonBean,processId,userId,processType,procDate,tType);
          procStatus = false;
          LOGGER.error("Technical Exception occured while doing Payment process ", te);
          te.printStackTrace();
      }catch (BusinessException be) {
    	  sendNotificationAndUpdateProcessLogs(commonBean,processId,userId,processType,procDate,tType);
    	  procStatus = false;
    	  LOGGER.error("Business Exception occured while doing Payment process.", be);
    	  be.printStackTrace();
    }
	   LOGGER.exit(CLASSNAME, methodName);
       return procStatus;
    }
      
    
    
      public void sendNotificationAndUpdateProcessLogs(DpbCommonBeanLocal commonBean,int pId,String uId,String pTyp,java.sql.Date aProcDate,String tType){
    	  commonBean.insertIntoProcessLog(commonHelper.getProcessLog(pId,
									IConstants.PAYMENT_PROCESS_FILE_GENERATION_FAIL, 
									IConstants.PROC_STATUS_FAILURE, uId));
    	  
		  sendProcessNotificationMail(IConstants.PROC_STATUS_FAILURE,pId,pTyp,aProcDate,tType);		
      }
	
      public void sendProcessNotificationMail(String pStatus,Integer pId,String pTyp,java.sql.Date aProcDate,String tType) {
	    final String methodName = "sendProcessNotificationMail";
        LOGGER.enter(CLASSNAME, methodName);
        try{      
	         SendMailDTO sendMailDTO = new SendMailDTO();
	         sendMailDTO.setFrom(PROPERTY_MANAGER.getPropertyAsString("payment.process.email.fromMail"));
	         sendMailDTO.addTORecipient(PROPERTY_MANAGER.getPropertyAsString("payment.process.email.toRecipient"));
	         String pDesc = MasterDataLookup.getInstance().getProcessDesc(pTyp);
	         if(IConstants.PROC_STATUS_SUCCESS.equalsIgnoreCase(pStatus)){
	        	 String pSub =  PROPERTY_MANAGER.getPropertyAsString("payment.process.success.email.subject");
	        	 String subject = MessageFormat.format(pSub, new Object[]{tType}); 
	        	 sendMailDTO.setSubject(subject);
	        	 String pCnt = PropertyManager.getPropertyManager().getPropertyAsString("payment.process.success.email.content");
		         String cnt = MessageFormat.format(pCnt,new Object[]{pDesc,DateCalenderUtil.getDateInGivenFormat(aProcDate,IConstants.DATE_FORMAT_MMDDYYYY_WITH_SLASH),pId});
		         sendMailDTO.setContent(cnt);
		         DPBCommonHelper.sendEmail(sendMailDTO);
	         }else{
	        	 String pSub = PROPERTY_MANAGER.getPropertyAsString("payment.process.fail.email.subject");
	        	 String subject = MessageFormat.format(pSub, new Object[]{tType}); 
	        	 sendMailDTO.setSubject(subject);
	        	 String pCnt = PropertyManager.getPropertyManager().getPropertyAsString("payment.process.fail.email.subject");
	        	 String mailContent = MessageFormat.format(pCnt,new Object[]{pDesc,DateCalenderUtil.getDateInGivenFormat(aProcDate,IConstants.DATE_FORMAT_MMDDYYYY_WITH_SLASH),pId});
		         mailContent = mailContent +IConstants.BR +this.getFailReason();
		         //mailContent = mailContent +IConstants.BR+IConstants.BR; 
		        		 //+PropertyManager.getPropertyManager().getPropertyAsString("email.payment.process.regards",IConstants.EMPTY_STR);		         
		          sendMailDTO.setContent(mailContent);
		         
		         if(invalidRcrdsList!= null && !invalidRcrdsList.isEmpty()){
					  String  errorPath = PropertyManager.getPropertyManager().getPropertyAsString("dpb.common.error.path");
					  byte[] bytes = DPBCommonHelper.writeFailedRecords(invalidRcrdsList, errorPath + getFailureListFileName(pTyp,pId));
					  attachment = new MailAttachment(getFailureListFileName(pTyp,pId),"text/plain", bytes);
					  attachArray[0] = attachment;
					  sendMailDTO.setAttachments(attachArray);					 
					  DPBCommonHelper.sendMailWithAttachment(sendMailDTO);
		         }else{
		        	 DPBCommonHelper.sendEmail(sendMailDTO);
		         }				  
	         }	        
          } catch (Exception e) {
        	LOGGER.error("Error Occuered While Sending Mail");
          }
      }

      public java.sql.Date decideEndDateForProcess(String schedulerType, java.sql.Date actualDte) {
  		java.sql.Date endDate = null;
  		java.sql.Date considerDte = null;
  		Calendar c = Calendar.getInstance();
		java.sql.Date tempDate = actualDte;
		c.setTime(tempDate);
		c.add(Calendar.DATE, -1);
		considerDte = new java.sql.Date(c.getTime().getTime());
		// process date for 2 April  file will be 3 April
  		RtlCalenderDefinition rtlDim = MasterDataLookup.getInstance().getRtlCalByDate(considerDte);
          if (IConstants.SCHD_YEARLY.equalsIgnoreCase(schedulerType)) {
        	  tempDate = rtlDim.getDteRetlYearEnd();
	  	      Calendar cYear = Calendar.getInstance();
	  	     // java.sql.Date tempDate = actualDte;
	  	      cYear.setTime(tempDate);
	  	      cYear.add(Calendar.DATE, 1);
	  	      endDate = new java.sql.Date(cYear.getTime().getTime());
          }else if (IConstants.SCHD_QUERTERLY.equalsIgnoreCase(schedulerType)) {  	       
	  	      tempDate = rtlDim.getDteRetlQtrEnd();
	  	      Calendar cQtr = Calendar.getInstance();
	  	     // java.sql.Date tempDate = actualDte;
	  	      cQtr.setTime(tempDate);
	  	      cQtr.add(Calendar.DATE, 1);
	  	      endDate = new java.sql.Date(cQtr.getTime().getTime());
          }else if (IConstants.SCHD_MONTH.equalsIgnoreCase(schedulerType)) {  	      
	  	      tempDate = rtlDim.getDteRetlMthEnd();
	  	      Calendar cMonth = Calendar.getInstance();
	  	      // java.sql.Date tempDate = actualDte;
	  	      cMonth.setTime(tempDate);
	  	      cMonth.add(Calendar.DATE, 1);
	  	      endDate = new java.sql.Date(cMonth.getTime().getTime());
          }else if (IConstants.SCHD_BI_WEEKLY.equalsIgnoreCase(schedulerType)) {
        	  endDate = rtlDim.getDteCal();
          }else if (IConstants.SCHD_WEEKLY.equalsIgnoreCase(schedulerType)) {
        	  endDate = rtlDim.getDteCal();
          }else {// start date and end date is today date
        	  //endDate = rtlDim.getDteCal();
        	  Calendar c1 = Calendar.getInstance();
  			  endDate = rtlDim.getDteCal();
  			  c1.setTime(endDate);
  			  c1.add(Calendar.DATE, 1);
  			  endDate = new java.sql.Date(c1.getTime().getTime());
          }
          return endDate;
      }

    public java.sql.Date decideStartDateForProcess(String schedulerType, java.sql.Date actualDte) 
  	{
		java.sql.Date startDate = null;
		java.sql.Date considerDte = null;
  		Calendar c = Calendar.getInstance();
		java.sql.Date tempDate = actualDte;
		c.setTime(tempDate);
		c.add(Calendar.DATE, -1);
		considerDte = new java.sql.Date(c.getTime().getTime());
		RtlCalenderDefinition rtlDim = MasterDataLookup.getInstance().getRtlCalByDate(considerDte);
		if (IConstants.SCHD_YEARLY.equalsIgnoreCase(schedulerType)) {
			tempDate = rtlDim.getDteRetlYearBeg();			
  	        Calendar cYear = Calendar.getInstance();
  	        // java.sql.Date tempDate = actualDte;
  	        cYear.setTime(tempDate);
  	        cYear.add(Calendar.DATE, 1);
  	        startDate = new java.sql.Date(cYear.getTime().getTime());
		}else if (IConstants.SCHD_QUERTERLY.equalsIgnoreCase(schedulerType)) {
			tempDate = rtlDim.getDteRetlQtrBeg();
			Calendar cQtr = Calendar.getInstance();
	  	     // java.sql.Date tempDate = actualDte;
	  	    cQtr.setTime(tempDate);
	  	    cQtr.add(Calendar.DATE, 1);
	  	    startDate = new java.sql.Date(cQtr.getTime().getTime());
		}else if (IConstants.SCHD_MONTH.equalsIgnoreCase(schedulerType)) {
			 tempDate = rtlDim.getDteRetlMthEnd();
	  	     Calendar cMonth = Calendar.getInstance();
	  	      // java.sql.Date tempDate = actualDte;
	  	     cMonth.setTime(tempDate);
	  	     cMonth.add(Calendar.DATE, 1);
	  	     startDate = new java.sql.Date(cMonth.getTime().getTime());
		}else if (IConstants.SCHD_BI_WEEKLY.equalsIgnoreCase(schedulerType)) {		
			Calendar c1 = Calendar.getInstance();
			java.sql.Date endDate =  rtlDim.getDteCal();
			c1.setTime(endDate);
			c1.add(Calendar.DATE, -14);
			startDate = new java.sql.Date(c1.getTime().getTime());
		}else if (IConstants.SCHD_WEEKLY.equalsIgnoreCase(schedulerType)) {
			Calendar c1 = Calendar.getInstance();
			java.sql.Date endDate = rtlDim.getDteCal();
			c1.setTime(endDate);
			c1.add(Calendar.DATE, -7);
			startDate  = new java.sql.Date(c1.getTime().getTime());
		}else {// start date and end date is today date
			//Calendar c1 = Calendar.getInstance();
			java.sql.Date endDate = rtlDim.getDteCal();
			//c1.setTime(endDate);
			//c1.add(Calendar.DATE, -1);
			startDate  = endDate;//new java.sql.Date(c1.getTime().getTime());
		}
		return startDate;
  	}
    
	public String decideProcessType(String paymentType){
		String procType = IConstants.EMPTY_STR;
		if(IConstants.FIXED_PAYMENT_PROCESS.equalsIgnoreCase(paymentType)) {
			procType =  IConstants.FIXED_BONUS_PROCESS;
	      	}else if(IConstants.VARIABLE_PAYMENT_PROCESS.equalsIgnoreCase(paymentType)){
	      		procType =  IConstants.VARIABLE_BONUS_PROCESS;
	      	}else if(IConstants.LEADERSHIP_BONUS_PROCESS.equalsIgnoreCase(paymentType)){
	      		procType =  IConstants.LEADERSHIP_BONUS_PROCESS;
	      	}else if(IConstants.COMMISSION_PAYMENT_PROCESS.equalsIgnoreCase(paymentType)){
	      		procType =  IConstants.COMMISSION_BONUS_PROCESS;
	      	}
		return procType;
	}

      public String getPaymentFileName(String pType) { 
    	  
    	  try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          LOGGER.info("Payment File Name:"	  + IConstants.COMPANY_NAME+ IConstants.UNDER_SCORE
                                    	  + IConstants.FILE_NAME + IConstants.DOT_STR + pType+"P"+IConstants.DOT_STR 
                                    	  + IConstants.FILE_TYPE + IConstants.DOT_STR + getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD)
                                    	  + IConstants.DASH + getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR
                                    	  + IConstants.FILE_EXT);
          
          return IConstants.COMPANY_NAME + IConstants.UNDER_SCORE + IConstants.FILE_NAME + IConstants.DOT_STR +
        		  pType+"P"+IConstants.DOT_STR + IConstants.FILE_TYPE + IConstants.DOT_STR + getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD)
                                	     + IConstants.DASH + getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR
                                	     + IConstants.FILE_EXT;
      }
    
      
      public String getPaymentFileName1( ) { 
    	  
    	  try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          LOGGER.info("Payment File Name:"	  + IConstants.COMPANY_NAME+ IConstants.UNDER_SCORE
                                    	  + IConstants.FILE_NAME + IConstants.DOT_STR + "FTL"+IConstants.DOT_STR 
                                    	  + IConstants.FILE_TYPE + IConstants.DOT_STR + getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD)
                                    	  + IConstants.DASH + getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR
                                    	  + IConstants.FILE_EXT);
          
          return IConstants.COMPANY_NAME + IConstants.UNDER_SCORE + IConstants.FILE_NAME + IConstants.DOT_STR
    	      			     + "FTL"+IConstants.DOT_STR + IConstants.FILE_TYPE + IConstants.DOT_STR + getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD)
                                	     + IConstants.DASH + getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR
                                	     + IConstants.FILE_EXT;
      }
      
      
      public String getPaymentFileName2( ) { 
    	  
    	  try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          LOGGER.info("Payment File Name:"	  + IConstants.COMPANY_NAME+ IConstants.UNDER_SCORE
                                    	  + IConstants.FILE_NAME + IConstants.DOT_STR + "SMART"+IConstants.DOT_STR 
                                    	  + IConstants.FILE_TYPE + IConstants.DOT_STR + getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD)
                                    	  + IConstants.DASH + getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR
                                    	  + IConstants.FILE_EXT);
          
          return IConstants.COMPANY_NAME + IConstants.UNDER_SCORE + IConstants.FILE_NAME + IConstants.DOT_STR
    	      			     + "SMART"+IConstants.DOT_STR + IConstants.FILE_TYPE + IConstants.DOT_STR + getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD)
                                	     + IConstants.DASH + getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR
                                	     + IConstants.FILE_EXT;
      }
      
      public String getPaymentFileName3( ) { 
    	  
    	  try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          LOGGER.info("Payment File Name:"	  + IConstants.COMPANY_NAME+ IConstants.UNDER_SCORE
                                    	  + IConstants.FILE_NAME + IConstants.DOT_STR + "VANS"+IConstants.DOT_STR 
                                    	  + IConstants.FILE_TYPE + IConstants.DOT_STR + getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD)
                                    	  + IConstants.DASH + getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR
                                    	  + IConstants.FILE_EXT);
          
          return IConstants.COMPANY_NAME + IConstants.UNDER_SCORE + IConstants.FILE_NAME + IConstants.DOT_STR
    	      			     + "VANS"+IConstants.DOT_STR + IConstants.FILE_TYPE + IConstants.DOT_STR + getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD)
                                	     + IConstants.DASH + getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR
                                	     + IConstants.FILE_EXT;
      }
      
      public String getPaymentFileName4( ) { 
    	  
    	  try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          LOGGER.info("Payment File Name:"	  + IConstants.COMPANY_NAME+ IConstants.UNDER_SCORE
                                    	  + IConstants.FILE_NAME + IConstants.DOT_STR + "CARS"+IConstants.DOT_STR 
                                    	  + IConstants.FILE_TYPE + IConstants.DOT_STR + getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD)
                                    	  + IConstants.DASH + getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR
                                    	  + IConstants.FILE_EXT);
          
          return IConstants.COMPANY_NAME + IConstants.UNDER_SCORE + IConstants.FILE_NAME + IConstants.DOT_STR
    	      			     + "CARS"+IConstants.DOT_STR + IConstants.FILE_TYPE + IConstants.DOT_STR + getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD)
                                	     + IConstants.DASH + getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR
                                	     + IConstants.FILE_EXT; 
      }
      
      public String getPaymentFileName5( ) { 
    	  
    	  try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          LOGGER.info("Payment File Name:"	  + IConstants.COMPANY_NAME+ IConstants.UNDER_SCORE
                                    	  + IConstants.FILE_NAME + IConstants.DOT_STR + "CARS"+IConstants.DOT_STR 
                                    	  + IConstants.FILE_TYPE + IConstants.DOT_STR + getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD)
                                    	  + IConstants.DASH + getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR
                                    	  + IConstants.FILE_EXT);
          
          return IConstants.COMPANY_NAME + IConstants.UNDER_SCORE + IConstants.FILE_NAME + IConstants.DOT_STR
    	      			     + "LEADERSHIP_CARS"+IConstants.DOT_STR + IConstants.FILE_TYPE + IConstants.DOT_STR + getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD)
                                	     + IConstants.DASH + getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR
                                	     + IConstants.FILE_EXT;
      }
      
      public String getAMGPaymentFileName( ) { 
    	  try {
    		  Thread.sleep(1000);
    	  } catch (InterruptedException e) {
    		  // TODO Auto-generated catch block
    		  e.printStackTrace();
    	  }
    	  
    	  String fileName = IConstants.COMPANY_NAME + IConstants.UNDER_SCORE + IConstants.FILE_NAME + IConstants.DOT_STR
   			     + "AMGCARS"+IConstants.DOT_STR + IConstants.FILE_TYPE + IConstants.DOT_STR + getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD)
        	     + IConstants.DASH + getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR
        	     + IConstants.FILE_EXT;
    	  
          LOGGER.info("AMG Payment File Name:"	  + fileName);
          return fileName; 
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
    
      /**
       * This method create header line which will need to be write in payment file.
       * Total number of char 1 - 8 - 6 = 15
       * 
       * @return
       */
      public String createHeaderLineItem() {
          String paymentHeaderRow = IConstants.EMPTY_STR;
          paymentHeaderRow = paymentHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 0, IConstants.HEADER_LINE_COL_1)
        		  								+ padCharAtLeft(IConstants.BLANK_STR, 7, getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD))
        		  								+ padCharAtLeft(IConstants.BLANK_STR, 5, getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR))
	          								    + padCharAtLeft(IConstants.BLANK_STR, 10, IConstants.VENDOR_NAME);
          return paymentHeaderRow;
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
    
     
      public static String createFooterRow(int rowCnt) {
          String dlrFooterRow = IConstants.EMPTY_STR;
          dlrFooterRow = dlrFooterRow + padCharAtLeft(IConstants.BLANK_STR, 1, IConstants.TOTAL_LINE);
          dlrFooterRow = dlrFooterRow + padCharAtLeft(IConstants.ZEROS, 7, String.valueOf(rowCnt));
          return dlrFooterRow;
      }
      /**
       * This method do substring based on given parameter.
       * @param toSubStr
       * @param start
       * @param end
       * @return
       */
      public static String subString(String toSubStr,int start, int end){
    	  String subStr = IConstants.EMPTY_STR;
    	  if(toSubStr != null && toSubStr.trim().length() > 0){	    	  
	    	  if(end != 0){
	    		  subStr = toSubStr.substring(start, end);	    		  
	    	  }else{
	    		  subStr = toSubStr.substring(start);
	    	  }
    	  }
    	  return subStr;
      }
      /**
       * This method do substring based on given parameter.
       * @param toSubStr
       * @param start
       * @param end
       * @return
       */
      public static String subStringLastGivenChar(String toSubStr,int lastCount){
    	  String subStr = IConstants.EMPTY_STR;
    	  if(toSubStr != null && toSubStr.trim().length() > 0){	    	  
    		  if(toSubStr.length() == 16){
	    		  subStr = toSubStr.substring(lastCount);	    		  
	    	  }else{
	    		  subStr = toSubStr.substring(lastCount+1);
	    	  }
    	  }
    	  return subStr;
      }
    
    public boolean isCreditOrDebitAcctIDExist(BonusInfo bonus)
    {
    	boolean isExist = true;
    	if (bonus != null && bonus.getBnsCalcAmt() >= 0 && isEmptyOrNullString(bonus.getCreditAcctId()) ) {
    		isExist = false;
		} else if (bonus != null && bonus.getBnsCalcAmt() < 0 && isEmptyOrNullString(bonus.getDebitAcctId()) ) {
			isExist = false;
		}
    	return isExist;
    }
      
     
      public static String getPgmName(String pName) {
          if (pName != null && pName.trim().length() > 12) {
        	pName = pName.substring(0, 11);
          }
          return pName;
      }
    
     
      public String getPaymentVehicleType(String vehType) {
          String vType = IConstants.EMPTY_STR;
          switch (VehicleTypeEnum.valueOf(vehType)) {
          case P:
        	vType = "PC";
        	break;
          case V:
        	vType = "VA";
        	break;
          case F:
        	vType = "FR";
        	break;
          case S:
        	vType = "SM";
        	break;
      }
      return vType;
      }
    
      
      public void createPaymentFile(List<String> fLines, String filePath) {
          final String methodName = "createPaymentFile";
          LOGGER.enter(CLASSNAME, methodName);
          
          PrintWriter pw = null;
          try {
        	File file = new File(filePath);
        	if (!file.exists()) {
        	  file.createNewFile();
        	}
        	pw = new PrintWriter(new FileWriter(file));
        	String lineSep = System.getProperty("line.separator");
        	if (fLines != null) {
        	  for (String line : fLines) {
        		pw.write(line);
        		pw.write(lineSep);
        	  }
        	}
          } catch (Exception e) {
        	e.printStackTrace();
          } finally {
        	pw.close();
          }
          LOGGER.exit(CLASSNAME, methodName);
      }
      
  	public static boolean isEmptyOrNullString(String s){
  		boolean flag = true;
  		if(s != null && s.length() > 0){
  			flag = false;
  		}
  		return flag;
  	}
  	
  	public String getFailureListFileName(String procType, Integer pId){
		return IConstants.IGNORE_TEXT + IConstants.UNDER_SCORE + 
			   IConstants.COMPANY_NAME + IConstants.UNDER_SCORE + 
			   IConstants.FILE_NAME + IConstants.DOT_STR + 
			   procType + IConstants.UNDER_SCORE + 
			   IConstants.FILE_TYPE + IConstants.UNDER_SCORE + 
		       IConstants.DOT_STR + DateCalenderUtil.getTodayDate(IConstants.DATE_FORMAT_YYYYMMDD) +
          	   IConstants.DOT_STR + DateCalenderUtil.getCurrentTime(IConstants.TIME_FORMAT_HHMMSS).replaceAll(IConstants.COLN_STR, IConstants.EMPTY_STR) + IConstants.DOT_STR +
          	   IConstants.FILE_EXT;
	}  	
	
	public String getFailReason() {
		return failReason;
	}
	
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}	
	
	public Integer getCurrentProcPgmID() {
		return currentProcPgmID;
	}
	
	public void setCurrentProcPgmID(Integer currentProcPgmID) {
		this.currentProcPgmID = currentProcPgmID;
	}
	
	public String getUniqueNum(){ 
	Random rand = new Random();
	String s2 = "";
	long i = rand.nextLong();
	long j = rand.nextLong();
	long k= Math.abs(i+j);
	String s1 = String.valueOf(k);
	if (s1.length()<6){
		String temp = String.format("%-10s", s1).replace(' ', '0');
		s2 = temp.substring(0,6);
	}
	else {
		s2 = s1.substring(0,6);
	}
	return s2;
	}
	public int getQuarter(){
		Calendar cal = Calendar.getInstance();
		int thisMonth = cal.get(Calendar.MONTH);
		int[] quarters = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
		int thisQuarter = quarters[thisMonth];
 		return thisQuarter;
	}
	
	public char getLastDigitOfYear(){
		Calendar cal = Calendar.getInstance();
		int thisYear = cal.get(Calendar.YEAR);
		String s = String.valueOf(thisYear);
		char c1 = s.charAt(s.length() - 1);
 		return c1;
	}
	
	
	 public String createDealerLine(PaymentDealerInfo dealer, java.sql.Date procDate,java.sql.Date monthEndDate,int k) {
		 		String dlrHeaderRow = IConstants.EMPTY_STR;
         
		 		dlrHeaderRow = dlrHeaderRow+ padCharAtLeft(IConstants.BLANK_STR, 1, IConstants.DLR_LINE_COL_1);
		 		if (dealer != null && dealer.getTotal() >= 0) 
	        	dlrHeaderRow = dlrHeaderRow + padCharAtRight(IConstants.BLANK_STR, 4, IConstants.CREDIT);
		 		else 
	        	dlrHeaderRow = dlrHeaderRow + padCharAtRight(IConstants.BLANK_STR, 4, IConstants.DEBIT);
	          
		 		dlrHeaderRow = dlrHeaderRow+ padCharAtLeft(IConstants.ZERO_CONSTANT, 15,DateCalenderUtil.formatAmount(String.valueOf(dealer.getTotal())));
		 		dlrHeaderRow = dlrHeaderRow+ padCharAtRight(IConstants.BLANK_STR, 4, IConstants.CURRENCY);
		 		dlrHeaderRow = dlrHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 8, getDateInGivenFormat(procDate,IConstants.DATE_FORMAT_MMDDYYYY));
		 		dlrHeaderRow = dlrHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 2,getPaymentVehicleType(dealer.getVehType()));
		 		dlrHeaderRow = dlrHeaderRow + padCharAtRight(IConstants.BLANK_STR, 10, dealer.getDlrId());
		 		
		 		//if(!procDate.toString().equals(monthEndDate.toString())){
		 		if (k==0){
		 			 
		 			dlrHeaderRow = dlrHeaderRow + padCharAtRight(IConstants.BLANK_STR, 16, subStringLastGivenChar(dealer.getVin(),9)+"-" + getQuarter()+ getLastDigitOfYear()+ getUniqueNum());
			        dlrHeaderRow = dlrHeaderRow + padCharAtRight(IConstants.BLANK_STR, 25, dealer.getPo());
			        dlrHeaderRow = dlrHeaderRow + padCharAtRight(IConstants.BLANK_STR, 18, subStringLastGivenChar(dealer.getVin(),9));
			        dlrHeaderRow = dlrHeaderRow + padCharAtRight(IConstants.BLANK_STR, 16, dealer.getText());
		 		 }
		 		 else
		 		 {
		 			dlrHeaderRow = dlrHeaderRow + padCharAtRight(IConstants.BLANK_STR, 16, dealer.getText());
		 			if(dealer.getText().equalsIgnoreCase("FL PL CAR 100114FLOOR PLAN") && dealer.getVehType().equalsIgnoreCase("P")){
		 				dlrHeaderRow = dlrHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 43, "FLOOR PLAN");
		 				dlrHeaderRow = dlrHeaderRow + padCharAtRight(IConstants.BLANK_STR, 40, IConstants.EMPTY_STR);
		 			}
		 			else if (dealer.getText().equalsIgnoreCase("RESRV SMT 100114DLR RESERVE")|| 
		 				 dealer.getText().equalsIgnoreCase("RESRV MBV 100114DLR RESERVE")||
		 			     dealer.getText().equalsIgnoreCase("RESRV DVU 100114DLR RESERVE")){
		 				 dlrHeaderRow = dlrHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 43, "DLR RESERVE");
		 				 dlrHeaderRow = dlrHeaderRow + padCharAtRight(IConstants.BLANK_STR, 39, IConstants.EMPTY_STR);
		 			}
		 			else
		 			{
		 				 dlrHeaderRow = dlrHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 50, "PERFORMANCE BONUS");
		 				 dlrHeaderRow = dlrHeaderRow + padCharAtRight(IConstants.BLANK_STR, 33, IConstants.EMPTY_STR);
		 			}
		 			dlrHeaderRow = dlrHeaderRow.replace(dlrHeaderRow.substring(54,60), getDateInGivenFormat(procDate,IConstants.DATE_FORMAT_MMDDYY));
		 		 }	
		 		dlrHeaderRow = dlrHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 12, IConstants.EMPTY_STR);
		        dlrHeaderRow = dlrHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 12, IConstants.EMPTY_STR);
		        dlrHeaderRow = dlrHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 20, IConstants.EMPTY_STR);
        return dlrHeaderRow;
     }
	 
	 
	 public String createBonusLine(PaymentInfo payment) {
         		String bnsRow = IConstants.EMPTY_STR;
         
         try {
	        	bnsRow = bnsRow + padCharAtLeft(IConstants.BLANK_STR, 0, IConstants.BONUS_LINE_COL_1);
				bnsRow = bnsRow+ padCharAtRight(IConstants.BLANK_STR, 18, payment.getAcctId());
	        	bnsRow = bnsRow + padCharAtLeft("0", 15,DateCalenderUtil.formatAmount(String.valueOf(payment.getBnsCalcAmt())));
	        	if (( payment.getVehicleType().equalsIgnoreCase("P")) && 
	        		((payment.getAcctId().equalsIgnoreCase("MBDEAL_MB_DSC_C")) || (payment.getAcctId().equalsIgnoreCase("MBDEAL_MB_DSC_D"))))
				{
	        		bnsRow = bnsRow + padCharAtLeft(IConstants.BLANK_STR, 1, IConstants.EMPTY_STR);
	        		bnsRow = bnsRow	+ padCharAtRight(IConstants.BLANK_STR, 10, "10166001");
	        		bnsRow = bnsRow + padCharAtLeft(IConstants.BLANK_STR, 20, payment.getPoNumber());
		        	bnsRow = bnsRow + padCharAtLeft(IConstants.BLANK_STR, 8, IConstants.EMPTY_STR);
		        	bnsRow = bnsRow + payment.getTxtName();
				}
	        	else 
	        	{
	        		bnsRow = bnsRow + padCharAtLeft(IConstants.BLANK_STR, 31, payment.getPoNumber());
	        		bnsRow = bnsRow + padCharAtLeft(IConstants.BLANK_STR, 8, IConstants.EMPTY_STR);
	        		bnsRow = bnsRow + payment.getTxtName();
	        	}
         } catch (Exception e) {
        	 e.printStackTrace();
         }
         return bnsRow;
     }
	 
	 public String bigDealer(String dlrLine,PaymentDealerInfo dealer,List<PaymentInfo> paymentList,int j){
		 
		Double amount =0d;
		int k = j+ 950;
		for (int i = j; i<k; i++){
			PaymentInfo pymt = paymentList.get(i);
			amount =  amount+pymt.getBnsCalcAmt();
			j++;
		}
		//0036163837 - incorrect summary line transaction for a group of transactions that result in taking money back from a dealer account.- Start
		if(amount < 0)
		{
			dlrLine = dlrLine.replace(dlrLine.substring(1,3), "DB");
		}
		else if(amount >= 0)
		{
			dlrLine = dlrLine.replace(dlrLine.substring(1,3), "CR");
		}
		//0036163837 - incorrect summary line transaction for a group of transactions that result in taking money back from a dealer account.- End
		String s = DateCalenderUtil.formatAmount(String.valueOf(amount));
		String s2 =  padCharAtLeft(IConstants.ZERO_CONSTANT, 15,s);
		dlrLine = dlrLine.replace(dlrLine.substring(5,20), s2);
		return dlrLine;
	 }
	 
     public String bigDealer2(String dlrLine,PaymentDealerInfo dealer,List<PaymentInfo> paymentList,int c,int a,int u){
		 	
		 int counter = 0;
		 Double amount =0d;
		 int b = a - c;
		 if ( b>950)
			 counter = c + 950;
		 else 
			 counter = a;
		 
		for (int i = c; i<counter; i++){
			PaymentInfo pymt = paymentList.get(i);
			amount =  amount+pymt.getBnsCalcAmt();
			c++;
		}
		//0036163837 - incorrect summary line transaction for a group of transactions that result in taking money back from a dealer account.- Start
		if(amount < 0)
		{
			dlrLine = dlrLine.replace(dlrLine.substring(1,3), "DB");
		}
		else if(amount >= 0)
		{
			dlrLine = dlrLine.replace(dlrLine.substring(1,3), "CR");
		}
		//0036163837 - incorrect summary line transaction for a group of transactions that result in taking money back from a dealer account.- End
		String s = DateCalenderUtil.formatAmount(String.valueOf(amount));
		String s2 =  padCharAtLeft(IConstants.ZERO_CONSTANT, 15,s);
		dlrLine = dlrLine.replace(dlrLine.substring(5,20), s2);
		dlrLine = dlrLine.substring(0,53)+u+dlrLine.substring(54);
		return dlrLine;
	 }
     
     public String ldrshipDealerLine(LeadershipInfo leader, java.sql.Date procDate,java.sql.Date monthEndDate) {
	 		String dlrHeaderRow = IConstants.EMPTY_STR;
  
	 		dlrHeaderRow = dlrHeaderRow+ padCharAtLeft(IConstants.BLANK_STR, 1, IConstants.DLR_LINE_COL_1);
	 		dlrHeaderRow = dlrHeaderRow + padCharAtRight(IConstants.BLANK_STR, 4, IConstants.CREDIT);
       
	 		dlrHeaderRow = dlrHeaderRow+ padCharAtLeft(IConstants.ZERO_CONSTANT, 15,DateCalenderUtil.formatAmount(String.valueOf(leader.getTotal()*199)));
	 		dlrHeaderRow = dlrHeaderRow+ padCharAtRight(IConstants.BLANK_STR, 4, IConstants.CURRENCY);
	 		dlrHeaderRow = dlrHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 8, getDateInGivenFormat(procDate,IConstants.DATE_FORMAT_MMDDYYYY));
	 		dlrHeaderRow = dlrHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 2,"PC");
	 		dlrHeaderRow = dlrHeaderRow + padCharAtRight(IConstants.BLANK_STR, 10, leader.getDlrId());
	 		
	 		dlrHeaderRow = dlrHeaderRow + padCharAtRight(IConstants.BLANK_STR, 16, "LDRSP CARS");
	 		dlrHeaderRow = dlrHeaderRow.replace(dlrHeaderRow.substring(54,60), getDateInGivenFormat(procDate,IConstants.DATE_FORMAT_MMDDYY));
	 		dlrHeaderRow = dlrHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 59, IConstants.LDRSHP);
	 		dlrHeaderRow = dlrHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 12, IConstants.EMPTY_STR);
	        dlrHeaderRow = dlrHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 12, IConstants.EMPTY_STR);
	        dlrHeaderRow = dlrHeaderRow + padCharAtLeft(IConstants.BLANK_STR, 20, IConstants.EMPTY_STR);
	        return dlrHeaderRow;
     }
     
	 public String ldrshipBonusLine(LeadershipInfo leader, java.sql.Date procDate,java.sql.Date monthEndDate) { 
         		String bnsRow = IConstants.EMPTY_STR;
         try {
				bnsRow = bnsRow + padCharAtRight(IConstants.BLANK_STR, 19,"1MB_LDRSPPGMPC_DB");
	        	bnsRow = bnsRow + padCharAtLeft("0", 15,DateCalenderUtil.formatAmount(String.valueOf(leader.getTotal()*199)));
	        	bnsRow = bnsRow + padCharAtLeft(IConstants.BLANK_STR, 31, "LDRSP CARS");
	        	bnsRow = bnsRow + padCharAtLeft(IConstants.BLANK_STR, 24, IConstants.LDRSHP);
	        	bnsRow = bnsRow + padCharAtLeft(IConstants.BLANK_STR, 1, IConstants.EMPTY_STR);
	        	bnsRow = bnsRow + padCharAtLeft(IConstants.BLANK_STR, 20,IConstants.EMPTY_STR);
		        bnsRow = bnsRow + padCharAtLeft(IConstants.BLANK_STR, 8, IConstants.EMPTY_STR);
		        bnsRow = bnsRow + IConstants.EMPTY_STR;
         } catch (Exception e) {
        	 e.printStackTrace();
         }
         return bnsRow;
     }
     
}
