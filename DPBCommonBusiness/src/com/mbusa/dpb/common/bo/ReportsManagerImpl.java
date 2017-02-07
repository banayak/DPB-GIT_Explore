package com.mbusa.dpb.common.bo;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang3.StringUtils;

import com.mbusa.dpb.common.constant.ICommonQueryConstants;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.constant.IReportQueryConstants;
import com.mbusa.dpb.common.dao.IReportsDAO;
import com.mbusa.dpb.common.dao.ReportsDAOImpl;
import com.mbusa.dpb.common.domain.BlockedVehicle;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.domain.DleRsrvSumRptBean;
import com.mbusa.dpb.common.domain.LeadershipReport;
import com.mbusa.dpb.common.domain.NetStarReport;
import com.mbusa.dpb.common.domain.ReportContentDefinitionBean;
import com.mbusa.dpb.common.domain.ReportDefinitionBean;
import com.mbusa.dpb.common.domain.ReportQuery;
import com.mbusa.dpb.common.domain.RetailDate;
import com.mbusa.dpb.common.domain.RetailsAndPaymentsReport;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.PersistanceException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.common.util.ReportCache;

public class ReportsManagerImpl implements IReportsManager {

	private static DPBLog LOGGER = DPBLog.getInstance(ReportsManagerImpl.class);
	static final private String CLASSNAME = ReportsManagerImpl.class.getName();
	IReportsDAO reportDAO = new ReportsDAOImpl();
	String dateRange = "";
	String yearRange = "";
	String monthRange = "";
	String quaterRange = "";
	String dateRangeForAllDates = "";
	String prioDateRange = "";
	String payDateRange = "";
	String owedDateRange = "";
	String vehTypeRange = "";
	String dealerRange = "";
	String yearsCriteria = "";
	String kpiDateRange = "";
	String kpiDealerRange = "";
	String poRange = "";
	String vinRange = "";
	String adjustmentDateRange = "";
	String adjDatePay="";
	String adjDateReport="";
	String separator = System.getProperty("line.separator");
	String oldPayoutDealerRange = "";
	String vehType1 = "";
	String vehType2 = "";
	String dateRange1 = "";
	String qtrRange1 = "";
	String qtrRange2 = "";
	String qtrRange3 = "";
	String header = "";
	String ubToDate = "";
	String ubFromDate = "";
	private List<String>dealerList;
	
	//AMG -Start
	
	//private String programID="";
	//AMG-End
	
	//Fixed Margin-Start
	String allDateRange = "";
	String monthlyDateRange = "";
	String quarterlyDateRange = "";
	String yearlyDateRange = "";
	String bonusDateRange = "";
	//Fixed Margin-End
	
	
	/**
	 * @return the dealerList
	 */
	public List<String> getDealerList() {
		return dealerList;
	}

	/**
	 * @param dealerList the dealerList to set
	 */
	public void setDealerList(List<String> dealerList) {
		this.dealerList = dealerList;
	}

	public List<BlockedVehicle> getBlockVehicleReport(String dealerId,String vehicleId,Date fromDate,Date toDate){ 
		List<BlockedVehicle> bList = null;
		LOGGER.enter(CLASSNAME, "getBlckReport()");

		bList = reportDAO.getBlockVehicleReport(dealerId,vehicleId,fromDate,toDate);
		LOGGER.exit(CLASSNAME, "getBlckReport()");
		return bList;
	}

	public Map<String,Object> generateReport(List<String> vehicleType,String viewAccountVin, int dealer, Date fromDate, Date toDate, int year) {
		Map<String,Object> genreport = null;
		LOGGER.enter(CLASSNAME, "getBlckReport()");
		genreport = reportDAO.generateReport(vehicleType,viewAccountVin,dealer,fromDate,toDate, year);
		LOGGER.exit(CLASSNAME, "getBlckReport()");
		return genreport;
	}
	public List<DPBProcessLogBean> getProcessLogsDeatils(int procId){
		List<DPBProcessLogBean> procesDetail = null;
		try {			
			procesDetail = reportDAO.getProcessLogsDeatils(procId);		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return procesDetail;
	}

	public List <RetailsAndPaymentsReport> generateRtlAndPayReport(List<String> vType,String vehicleId,java.sql.Date fromDate,java.sql.Date toDate,String level)
	{
		List<RetailsAndPaymentsReport> bList = null;
		LOGGER.enter(CLASSNAME, "generateRtlAndPayReport()");

		bList = reportDAO.generateRtlAndPayReport(vType,vehicleId,fromDate,toDate,level);

		return bList; 
	}
	public List<ReportDefinitionBean> generateReportList()throws BusinessException{
		final String methodName = "generateReportList";
		LOGGER.enter(CLASSNAME, methodName);
		List<ReportDefinitionBean> rList = null;
		try{
		rList = reportDAO.generateReportList();
		}
		catch(PersistanceException pe){
			LOGGER.error("PersistenceException Occured",pe);
		}
		LOGGER.exit(CLASSNAME, methodName);
		return rList;

	}
	public StringBuffer reportsGenerate(NetStarReport reportCriteria, boolean isFromScheduler) {
		final String methodName = "reportsGenerate";
		LOGGER.enter(CLASSNAME, methodName);
		
		int rptLPP = reportDAO.getReportLpp(reportCriteria.getRptId());
		List rptCntList = null;
		rptCntList = reportDAO.reportsGenerate(reportCriteria.getRptId());
		StringBuffer report = new StringBuffer("");
		
		boolean results = false;
		// For netstar popup reports setting hard coded value to easy to print 
		if(!isFromScheduler) {
			//rptLPP = 30;
			// These 5 lines will be added as empty lines at end of each page as footer
			rptLPP = rptLPP - 5;
		} else if (IConstants.SCHD_MONTH.equalsIgnoreCase(reportCriteria.getSchedule()) || 
				IConstants.SCHD_YEARLY.equalsIgnoreCase(reportCriteria.getSchedule())) {
			// These 5 lines will be added as empty lines at end of each page as footer
			rptLPP = rptLPP - 5;
		}
		
		setSearchCriteria (reportCriteria, isFromScheduler);
		
		if (IConstants.SCHD_DAILY.equalsIgnoreCase(reportCriteria.getSchedule())) {
			report = generateDailyReport(rptCntList, rptLPP, reportCriteria, isFromScheduler);
		} 
		else if (IConstants.SCHD_MONTH.equalsIgnoreCase(reportCriteria.getSchedule())) {
			report = generateMonthlyReport(rptCntList, rptLPP, reportCriteria, isFromScheduler);
		}
		/*else if (IConstants.SCHD_YEARLY.equalsIgnoreCase(reportCriteria.getSchedule())) {
			report = generateYearlyReport(rptCntList, rptLPP, reportCriteria, isFromScheduler);
		}*/
		return report;
	}

	private StringBuffer generateYearlyReport(List rptCntList, int rptLPP,LeadershipReport reportCriteria, boolean isFromScheduler) {
	
		ReportQuery rptQry = new ReportQuery();
		ReportContentDefinitionBean rptCnt = new ReportContentDefinitionBean();
		List<ReportQuery> rqList = null;
		List<List<String>> colValList = null;
		List<ReportContentDefinitionBean> rcList = null;
		rcList = (List<ReportContentDefinitionBean>) rptCntList.get(0);
		rqList = (List<ReportQuery>) rptCntList.get(1);
		StringBuffer[] contents = null;
		boolean results = false;
		StringBuffer[] headerContents = null;
		Integer[] pageNumber = null;
		String monthlyCarFooter = "";
		String footer = "";
		
		for (int i=0;i<rcList.size();i++) {       
			rptCnt = rcList.get(i);
			rptQry = rqList.get(i);
			String query = rptQry.getQuery();
			query = replaceCriteria(query);
			colValList = reportDAO.getQueryData(query);
			
			if (colValList != null && colValList.size() > 0) {
				results = true;
			}
			
			if (i == 0) {
				reportCriteria.setRecCount(colValList.size());
				if(colValList == null || colValList.size() == 0) {
					contents = new StringBuffer[1];
					contents[0] = new StringBuffer("");
					headerContents = new StringBuffer[1];
					headerContents[0] = new StringBuffer("");
					pageNumber = new Integer[1];
					pageNumber[0] = 1;
				} 
				else {
					contents = new StringBuffer[colValList.size()];
					headerContents = new StringBuffer[colValList.size()];
					pageNumber = new Integer[colValList.size()];
				}
			}
			
			footer = DateCalenderUtil.makeNonNullString(rptCnt.getReportFooter());
			if(rptCnt.getReportContent().contains("{0}")){
				int sbCount = 0;
				for(List<String> dList: colValList){   
					if(i==0) {
						contents[sbCount] = new StringBuffer("");
						headerContents[sbCount] = new StringBuffer("");
						headerContents[sbCount].append(MessageFormat.format(rptCnt.getReportContent(), dList.toArray()));
						headerContents[sbCount].append(separator).append(separator);
						pageNumber[sbCount] = 1;
					} 
					
					contents[sbCount].append(MessageFormat.format(rptCnt.getReportContent(), dList.toArray()));
					contents[sbCount].append(separator);
					
					if (i == 0) {
						setPageNumber(contents, pageNumber, sbCount);
					} 
					sbCount++;
					
				}   
			} 
			else {
				if (colValList != null && colValList.size() > 0) {
					for(StringBuffer content: contents) {
						content.append(rptCnt.getReportContent());
						content.append(separator);
					}
				} 
				String tempDlr = "";
				int sbCount = -1;
				for(List<String> list:colValList) {   
					int valueCount = 0;
					boolean isDiffDlr = false;
					for(String value :list) {     
						if (valueCount == 0) {
							if (!tempDlr.equalsIgnoreCase(value)) {
								tempDlr = value;
								isDiffDlr = true;
								sbCount++;
							}
						}
						else{
							contents[sbCount].append(value);
					}
						valueCount++;
						
					}  
					contents[sbCount].append(separator);
					StringBuffer buffer = new StringBuffer(contents[sbCount].toString());
					int len = buffer.toString().split(separator).length;
					if (len % rptLPP == 0) {
						contents[sbCount].append(separator).append(separator);
						if (i == 2 && footer.length() != 0) {
							contents[sbCount].append(footer);
						} 
						else {
							contents[sbCount].append(separator);
						}
						contents[sbCount].append(separator).append(separator);
							
						if (!isFromScheduler) {
							contents[sbCount].append("<h1></h1>");
						}
						contents[sbCount].append(headerContents[sbCount]);
						setPageNumber(contents, pageNumber, sbCount);
					}
				}      
				
				if (i == rcList.size()-1) {
					int pageCount = 0;
					if(results) {
						for(StringBuffer content: contents) {
							content.append(separator);
							content.append(footer);
							if(pageCount < contents.length - 1) {
								setEmptyLines(content, rptLPP, pageNumber[pageCount]);
							}
							content.append(separator).append(separator);
							content.append(separator).append(separator);
							if (!isFromScheduler) {
								if(pageCount < contents.length - 1) {
									content.append("<h1></h1>");
								}
							}
							pageCount++;
						}
					}
				}
			}
		}   
		
		if (results)
			return getContentFromArray(contents);
		else
			return new StringBuffer("");
	
	}

	private StringBuffer generateMonthlyReport(List rptCntList, int rptLPP,
			NetStarReport reportCriteria, boolean isFromScheduler) {
		ReportQuery rptQry = new ReportQuery();
		ReportContentDefinitionBean rptCnt = new ReportContentDefinitionBean();
		List<ReportQuery> rqList = null;
		List<List<String>> colValList = null;
		List<ReportContentDefinitionBean> rcList = null;
		rcList = (List<ReportContentDefinitionBean>) rptCntList.get(0);
		rqList = (List<ReportQuery>) rptCntList.get(1);
		StringBuffer[] contents = null;
		boolean results = false;
		boolean reportResults = false;
		StringBuffer[] headerContents = null;
		StringBuffer[] adjustmentHeader = null;
		Integer[] pageNumber = null;
		//String monthlyCarFooter = "";
		String footer = "";
		List<List<String>> adjDetailsColValList = null;
		List<List<String>> adjTotalsColValList = null;
		//DPB monthly reconciliation details start
		LinkedHashMap<String, Double> ftlTotalMap = new LinkedHashMap<String,Double>();
		LinkedHashMap<String, Double> vanTotalMap = new LinkedHashMap<String, Double>();
		LinkedHashMap<String, Double> smartTotalMap = new LinkedHashMap<String, Double>();
		LinkedHashMap<String, Double> passengerTotalMap = new LinkedHashMap<String, Double>();
		LinkedHashMap<String,Double> amgEliteTotalMap = new LinkedHashMap<String,Double>();
		LinkedHashMap<String,Double> amgPerfTotalMap = new LinkedHashMap<String,Double>();
		LinkedList<String> ftlDlrList = new LinkedList<String>();
		LinkedList<String> vanDlrList = new LinkedList<String>();
		LinkedList<String> smartDlrList = new LinkedList<String>();
		LinkedList<String> passengerDlrList = new LinkedList<String>();
		LinkedList<Double> ftlAmntList = new LinkedList<Double>();
		LinkedList<Double> vanAmntList = new LinkedList<Double>();
		LinkedList<Double> smartAmntList = new LinkedList<Double>();
		LinkedList<Double> passengerAmntList = new LinkedList<Double>();
		LinkedList<String> amgEliteDlrList = new LinkedList<String>();
		LinkedList<Double> amgEliteAmntList = new LinkedList<Double>();
		LinkedList<String> amgPerfDlrList = new LinkedList<String>();
		LinkedList<Double> amgPerfAmntList = new LinkedList<Double>();
		//DPB monthly reconciliation details end
		for (int i=0;i<rcList.size();i++) {
			results = false;
			rptCnt = rcList.get(i);
			rptQry = rqList.get(i);
			String query = rptQry.getQuery();
			query = replaceCriteria(query);
			if (i == 6) {
				adjDetailsColValList = reportDAO.getQueryData(query);
				continue;
			} else if (i == 7) {
				adjTotalsColValList = reportDAO.getQueryData(query);
			} else {
				colValList = reportDAO.getQueryData(query);
			}
			if (colValList != null && colValList.size() > 0) {
				if (i == 0) {
					reportResults = true;
				}
				results = true;
			}
			if (i == 0) {
				//Commented Code(Do not break loop even if first query does not return data) - Start - Kshitija 
				/*if (!reportResults) {
					break;
				}*/
				//Commented Code(Do not break loop even if first query does not return data) - End - Kshitija 
				reportCriteria.setRecCount(colValList.size());
				if(colValList == null || colValList.size() == 0) {
					contents = new StringBuffer[1];
					contents[0] = new StringBuffer("");
					headerContents = new StringBuffer[1];
					headerContents[0] = new StringBuffer("");
					adjustmentHeader = new StringBuffer[1];
					adjustmentHeader[0] = new StringBuffer("");
					pageNumber = new Integer[1];
					pageNumber[0] = 1;
				} else {
					contents = new StringBuffer[colValList.size()];
					headerContents = new StringBuffer[colValList.size()];
					adjustmentHeader = new StringBuffer[colValList.size()];
					pageNumber = new Integer[colValList.size()];
				}
			}
			footer = DateCalenderUtil.makeNonNullString(rptCnt.getReportFooter());
			
			if(rptCnt.getReportContent().contains("{0}")){
				
				if(i!= 4 && i!=5 && i!=7 && i!=10) {
					int sbCount = 0;
					for(List<String> dList: colValList){
						if(i==0) {
							contents[sbCount] = new StringBuffer("");
							headerContents[sbCount] = new StringBuffer("");
							headerContents[sbCount].append(MessageFormat.format(rptCnt.getReportContent(), dList.toArray()));
							headerContents[sbCount].append(separator).append(separator);
							pageNumber[sbCount] = 1;
						} else if (i==1) {
							
							headerContents[sbCount].append(MessageFormat.format(rptCnt.getReportContent(), dList.toArray()));
							headerContents[sbCount].append(separator);
						}
						//DPB monthly reconciliation details start
						if(isFromScheduler)
						{
							if(i == 0)
							{
								String dealerID = "";
								if (0 < dList.size() && dList.get(0) != null) {
									dealerID = dList.get(0).trim();
								}
								//FTL
								if (reportCriteria.getRptId() == 6) {
									ftlDlrList.add(dealerID);
								}
								//VAN
								if (reportCriteria.getRptId() == 3) {
									vanDlrList.add(dealerID);
								}
								//Smart
								if (reportCriteria.getRptId() == 4) {
									smartDlrList.add(dealerID);
								}
								//Cars
								if (reportCriteria.getRptId() == 5) {
									passengerDlrList.add(dealerID);
								}
								//AMG Perf
								if (reportCriteria.getRptId() == 8) {
									amgPerfDlrList.add(dealerID);
								}
								//AMG Elite
								if (reportCriteria.getRptId() == 9) {
									amgEliteDlrList.add(dealerID);
								}
							}
							//FTL
							if (i == 3 && reportCriteria.getRptId() == 6) {
								
								String totalDealerRsrvWithComma="";
								if(0<dList.size() && dList.get(0) != null)
								{
								totalDealerRsrvWithComma = dList.get(0)
										.trim();
										
								}
								String totalPerfBonusWithComma = "";
								if(5<dList.size() && dList.get(5)!= null)
								{
								totalPerfBonusWithComma = dList.get(5)
										.trim();
								}
								double totalDealerRsrv = getFormattedBonusAmount(totalDealerRsrvWithComma);
								double totalPerfBonus = getFormattedBonusAmount(totalPerfBonusWithComma);
								double totalDealerBonus = totalDealerRsrv
										+ totalPerfBonus;
								ftlAmntList.add(totalDealerBonus);

							}
							//Van
							if (i == 3 && reportCriteria.getRptId() == 3) {
								String totalDealerRsrvWithComma = "";
								if(0<dList.size() && dList.get(0) != null)
								{
								totalDealerRsrvWithComma = dList.get(0)
										.trim();
								}
								String totalPerfBonusWithComma = "";
								if(5<dList.size() && dList.get(5) != null)
								{
								 totalPerfBonusWithComma = dList.get(5)
										.trim();
								}
								double totalDealerRsrv = getFormattedBonusAmount(totalDealerRsrvWithComma);
								double totalPerfBonus = getFormattedBonusAmount(totalPerfBonusWithComma);
								double totalDealerBonus = totalDealerRsrv
										+ totalPerfBonus;
								vanAmntList.add(totalDealerBonus);

							}
							//Smart
							if (i == 3 && reportCriteria.getRptId() == 4) {
								String totalDealerRsrvWithComma ="";
								String totalPerfBonusWithComma = "";
								if(0<dList.size() && dList.get(0) != null)
								{
								 totalDealerRsrvWithComma = dList.get(0)
										.trim();
								}
								if(3<dList.size() && dList.get(3) != null)
								{
								 totalPerfBonusWithComma = dList.get(3)
										.trim();
								}
								
								double totalDealerRsrv = getFormattedBonusAmount(totalDealerRsrvWithComma);
								double totalPerfBonus = getFormattedBonusAmount(totalPerfBonusWithComma);
								double totalDealerBonus = totalDealerRsrv
										+ totalPerfBonus;
								smartAmntList.add(totalDealerBonus);

							}
							//Cars
							if (i == 3 && reportCriteria.getRptId() == 5) {
								String totalDealerRsrvWithComma ="";
								String totalPerfBonusWithComma = "";
								
								if(2<dList.size() && dList.get(2) != null)
								{
								 totalDealerRsrvWithComma = dList.get(2)
										.trim();
								}
								if(11<dList.size() && dList.get(11) != null)
								{
								 totalPerfBonusWithComma = dList.get(11)
										.trim();
								}
								double totalDealerRsrv = getFormattedBonusAmount(totalDealerRsrvWithComma);
								double totalPerfBonus = getFormattedBonusAmount(totalPerfBonusWithComma);
								double totalDealerBonus = totalDealerRsrv
										+ totalPerfBonus;
								passengerAmntList.add(totalDealerBonus);

							}
							//AMG Elite
							if (i == 3 && reportCriteria.getRptId() == 9) {
								String totalPerfBonusWithComma = "";
								if(5<dList.size() && dList.get(5) != null)
								{
								 totalPerfBonusWithComma = dList.get(5)
										.trim();
								}
								double totalPerfBonus = getFormattedBonusAmount(totalPerfBonusWithComma);
								amgEliteAmntList.add(totalPerfBonus);

							}
							//AMG Perf
							if (i == 3 && reportCriteria.getRptId() == 8) {
								String totalPerfBonusWithComma = "";
								if(5<dList.size() && dList.get(5) != null)
								{
								 totalPerfBonusWithComma = dList.get(5)
										.trim();
								}
								double totalPerfBonus = getFormattedBonusAmount(totalPerfBonusWithComma);
								amgPerfAmntList.add(totalPerfBonus);

							}
						}
						//DPB monthly reconciliation details end
						if (sbCount >= contents.length){
							contents[sbCount-1].append(MessageFormat.format(rptCnt.getReportContent(), dList.toArray()));
							contents[sbCount-1].append(separator);
						}
						else
							
						{
						contents[sbCount].append(MessageFormat.format(rptCnt.getReportContent(), dList.toArray()));
						contents[sbCount].append(separator);
						}
						
						if (i == 0 || i==8) {
							setPageNumber(contents, pageNumber, sbCount);
						} else if (i == 3){
							setEmptyLines(contents[sbCount], rptLPP, pageNumber[sbCount]);
							contents[sbCount].append(separator).append(separator);
							contents[sbCount].append(footer+separator).append(separator);
							contents[sbCount].append(separator);
							
							if (!isFromScheduler) {
								contents[sbCount].append("<h1></h1>");
							}
						}
						sbCount++;
					}
				} else {// Adjustments from below
					if (i == 7) {
						int count = 0;
						String oldDealer = "";
						
						for(List<String> dList: adjTotalsColValList){
							String dealer_tot = dList.get(0);
							String month_tot = dList.get(1);
							String year_tot = dList.get(2);	
							int sbCount = 0;
							String searchStr = "DEALER: " + dealer_tot.trim();
							//Added code to check if any dealer present only with adjustment bonus along with other dealers with normal bonus - Start - Kshitija
							boolean onlyAdjustmentPresent = false;
							List<StringBuffer> contentsList = Arrays.asList(contents);
							//one record with normal bonus is present
							if(contentsList!= null && !contentsList.get(0).toString().equals("") && !contentsList.toString().contains(searchStr))
							{
								onlyAdjustmentPresent = true;
								sbCount = contentsList.size();
								StringBuffer[] newContentsArray = new StringBuffer[contents.length+1];
								System.arraycopy(contents, 0, newContentsArray, 0, contents.length);
								contents = new StringBuffer[contentsList.size()+1];
								System.arraycopy(newContentsArray, 0, contents, 0, newContentsArray.length);
								
								StringBuffer[] newAdjustmentHeader = new StringBuffer[adjustmentHeader.length+1];
								System.arraycopy(adjustmentHeader, 0, newAdjustmentHeader, 0, adjustmentHeader.length);
								adjustmentHeader = new StringBuffer[adjustmentHeader.length+1];
								System.arraycopy(newAdjustmentHeader, 0, adjustmentHeader, 0, newAdjustmentHeader.length);
								
								Integer[] newPageNumber = new Integer[pageNumber.length+1];
								System.arraycopy(pageNumber, 0, newPageNumber, 0, pageNumber.length);
								pageNumber = new Integer[pageNumber.length+1];
								System.arraycopy(newPageNumber, 0, pageNumber, 0, newPageNumber.length);
								
								if(contents[sbCount] == null)
								{
								contents[sbCount] =  new StringBuffer("");
								}
								if(pageNumber[sbCount] == null)
								{
								pageNumber[sbCount] = 1;
								}
								adjustmentHeader[sbCount] = new StringBuffer("");
								
								
								
							}
							//Added code to check if any dealer present only with adjustment bonus along with other dealers with normal bonus - End - Kshitija
							for (StringBuffer content : contents) {
								//commented if -start - Kshitija
								/*if(content.toString().contains(searchStr)) {*/
								//commented if -end - Kshitija
								//added if condition start - kshitija
								if(sbCount >= contents.length){
									break;
								}
								
								if(content == null) {
									
									if(contents[sbCount] == null)
									{
									contents[sbCount] =  new StringBuffer("");
									}
									if(pageNumber[sbCount] == null)
									{
									pageNumber[sbCount] = 1;
									}
								}
								//added if condition end - kshitija
								//if condition Start- Kshitija
								boolean checkContent = true;
								if(onlyAdjustmentPresent == false && contentsList!= null && !contentsList.get(0).toString().equals(""))
								{
								/*if(reportResults == true)
								{*/
									checkContent = content.toString().contains(searchStr);
								/*}*/
								}
								if(checkContent) {
								//if condition End - Kshitija
									int temp = 0;
									List<String> tmpList = new ArrayList<String>();
									for (String str: dList) {
										if(temp>2)
											tmpList.add(str);
										temp++;
									}
									boolean firstRecord = true;
									for (; count<adjDetailsColValList.size();) {
										List<String> detailsList = adjDetailsColValList.get(count);
										String dealer = detailsList.get(0);
										String month = detailsList.get(1);
										String monthName = detailsList.get(2);
										String year = detailsList.get(3);
										if(firstRecord) {
											if (oldDealer.equalsIgnoreCase(dealer_tot)) {
												contents[sbCount].append(adjustmentHeader[sbCount]);
												setPageNumber(contents, pageNumber, sbCount);
											} else {
												oldDealer = dealer_tot;
											}
											contents[sbCount].append(monthName+" "+year+"Sales Adjustments                 |");
											contents[sbCount].append(separator);
											contents[sbCount].append(rcList.get(6).getReportContent());
											contents[sbCount].append(separator);
											firstRecord = false;
										}
										if (dealer_tot.equalsIgnoreCase(dealer) &&
												month_tot.equalsIgnoreCase(month) &&
												year_tot.equalsIgnoreCase(year)) {
											count++;
											temp = 0;
											for (String str: detailsList) {
												if(temp>3)
													contents[sbCount].append(str);
												temp++;
											}
										} else {
											break;
										}
										contents[sbCount].append(separator);
										StringBuffer buffer = new StringBuffer(contents[sbCount].toString());
										int len = buffer.toString().split(separator).length;
										len = len - (pageNumber[sbCount]<=2?0:(pageNumber[sbCount]-2)*5);
										if (len % rptLPP == 0) {
											contents[sbCount].append(separator).append(separator);
											contents[sbCount].append(separator);
											contents[sbCount].append(separator).append(separator);
											
											if (!isFromScheduler) {
												contents[sbCount].append("<h1></h1>");
											}
											contents[sbCount].append(adjustmentHeader[sbCount]);
											setPageNumber(contents, pageNumber, sbCount);
										}
									}
									contents[sbCount].append(MessageFormat.format(rptCnt.getReportContent(), tmpList.toArray()));
									contents[sbCount].append(separator);
									
									setEmptyLines(contents[sbCount], rptLPP, pageNumber[sbCount]);
									contents[sbCount].append(separator).append(separator);
									contents[sbCount].append(footer+separator).append(separator);
									contents[sbCount].append(separator);
									if (!isFromScheduler) {
										contents[sbCount].append("<h1></h1>");
									}
									//commnetd if starts - kshitija
								}
								//commnetd if end - kshitija
								sbCount++;
							}
						}
					} else {
						for(List<String> dList: colValList){
							String dealer = dList.get(0);
							int sbCount = 0;
							String searchStr = "DEALER: " + dealer.trim();
							//Added code to check if any dealer present only with adjustment bonus along with other dealers with normal bonus - Start - Kshitija
							boolean onlyAdjustmentPresent = false;
							List<StringBuffer> contentsList = Arrays.asList(contents);
							//make changes for considering whole report only with adjustment case - added contentsList.toString().trim().equals("")
							if(contentsList!= null  && !contentsList.get(0).toString().equals("") && !contentsList.toString().contains(searchStr))
							{
								onlyAdjustmentPresent = true;
								sbCount = contentsList.size();
								StringBuffer[] newContentsArray = new StringBuffer[contents.length+1];
								System.arraycopy(contents, 0, newContentsArray, 0, contents.length);
								contents = new StringBuffer[contentsList.size()+1];
								System.arraycopy(newContentsArray, 0, contents, 0, newContentsArray.length);
								
								StringBuffer[] newAdjustmentHeader = new StringBuffer[adjustmentHeader.length+1];
								System.arraycopy(adjustmentHeader, 0, newAdjustmentHeader, 0, adjustmentHeader.length);
								adjustmentHeader = new StringBuffer[adjustmentHeader.length+1];
								System.arraycopy(newAdjustmentHeader, 0, adjustmentHeader, 0, newAdjustmentHeader.length);
								
								Integer[] newPageNumber = new Integer[pageNumber.length+1];
								System.arraycopy(pageNumber, 0, newPageNumber, 0, pageNumber.length);
								pageNumber = new Integer[pageNumber.length+1];
								System.arraycopy(newPageNumber, 0, pageNumber, 0, newPageNumber.length);
								
								if(contents[sbCount] == null)
								{
								contents[sbCount] =  new StringBuffer("");
								}
								if(pageNumber[sbCount] == null)
								{
								pageNumber[sbCount] = 1;
								}
								adjustmentHeader[sbCount] = new StringBuffer("");
							}
							//Added code to check if any dealer present only with adjustment bonus along with other dealers with normal bonus - End - Kshitija
							for (StringBuffer content : contents) {
								//added if start - kshitija
								if(sbCount >= contents.length){
									break;
								}
								if(content == null) {
									//added code to avoid NP start- Kshitija
									if(contents[sbCount] == null)
									{
									contents[sbCount] =  new StringBuffer("");
									}
									if(pageNumber[sbCount] == null)
									{
									pageNumber[sbCount] = 1;
									}
									adjustmentHeader[sbCount] = new StringBuffer("");
									//added code to avoid NP end- Kshitija
								}
								//added if end - kshitija
								//removed if start- Kshitija
								//if(content.toString().contains(searchStr)) {
								//removed if end- Kshitija
								//added if condition Start - Kshitija
								boolean checkContent = true;
								if(onlyAdjustmentPresent == false && contentsList!= null && !contentsList.get(0).toString().equals(""))
								{
								/*if(reportResults == true)
								{*/
									checkContent = content.toString().contains(searchStr);
								/*}*/
								}
								if(checkContent) {
									
								//added if condition End - Kshitija
									if(i==4 || i==10) {
										adjustmentHeader[sbCount] = new StringBuffer("");
										adjustmentHeader[sbCount].append(MessageFormat.format(rptCnt.getReportContent(), dList.toArray()));
										adjustmentHeader[sbCount].append(separator).append(separator);
										if(i == 10 && !isFromScheduler) {
											contents[sbCount].append("<h1></h1>");
										}
										contents[sbCount].append(MessageFormat.format(rptCnt.getReportContent(), dList.toArray()));
										contents[sbCount].append(separator);
										
										setPageNumber(contents, pageNumber, sbCount);
									} else {
										adjustmentHeader[sbCount] = new StringBuffer("");
										int temp = 0;
										List<String> tmpList = new ArrayList<String>();
										for (String str: dList) {
											if(temp!=0)
												tmpList.add(str);
											temp++;
										}
										contents[sbCount].append(MessageFormat.format(rptCnt.getReportContent(), tmpList.toArray()));
										contents[sbCount].append(separator);
										
										adjustmentHeader[sbCount].append(MessageFormat.format(rptCnt.getReportContent(), tmpList.toArray()));
										adjustmentHeader[sbCount].append(separator);
										
									}
								//removed if start- Kshitija
								}
								//removed if end- Kshitija
								sbCount++;
							}
						}
					}
				}
			} else {
				if(i!=11 && i!=12) {
					if (colValList != null && colValList.size() > 0) {
						//add condition to match dealer Start - Kshitija
						
						//add condition to match dealer End - Kshitija
						for(StringBuffer content: contents) {
							//add condition to match dealer Start - Kshitija
							boolean toAppend = false;
							for(List<String> dList: colValList){
							
								String dealer = dList.get(0);
								String searchStr = "DEALER: " + dealer.trim();
								if(content.toString().contains(searchStr))
								{
									toAppend = true;
									break;
								}
								
							}
							//add condition to match dealer End - Kshitija
							//add condition to match dealer Start - Kshitija
							if(toAppend == true)
							{
							//add condition to match dealer End - Kshitija
							content.append(rptCnt.getReportContent());
							content.append(separator);
							//add condition to match dealer Start - Kshitija
							}
							//add condition to match dealer End - Kshitija
						}
					} 
					int allRecords = 1;
					String tempDlr = "";
					int sbCount = -1;
					for(List<String> list:colValList) {
						int valueCount = 0;
						boolean isEmpty = false;
						boolean isDiffDlr = false;
						for(String value :list) {
							if (valueCount == 0) {
								if (!tempDlr.equalsIgnoreCase(value)) {
									tempDlr = value;
									isDiffDlr = true;
									sbCount++;
								}
							} else {
								if (valueCount == 1 && list.get(3) == null && list.get(2) == null) {
									isEmpty = true;
									break;
								} else {
									contents[sbCount].append(value);
								}
							}
							valueCount++;
						}
						if(i==9 && sbCount-1 != -1 && isDiffDlr) {
							contents[sbCount-1].append(separator).append(separator);
							contents[sbCount-1].append(footer);
							setEmptyLines(contents[sbCount-1], rptLPP, pageNumber[sbCount-1]);
							contents[sbCount-1].append(separator).append(separator);
							contents[sbCount-1].append(separator).append(separator);
							contents[sbCount-1].append(separator).append(separator);
							
						}


						
						if(!isEmpty) {
							contents[sbCount].append(separator);
						}
						StringBuffer buffer = new StringBuffer(contents[sbCount].toString());
						int len = buffer.toString().split(separator).length;
						len = len - (pageNumber[sbCount]<=2?0:(pageNumber[sbCount]-2)*5);
						if (len % rptLPP == 0) {
							contents[sbCount].append(separator).append(separator);
							if (i == 2 && footer.length() != 0) {
								contents[sbCount].append(footer).append(separator);
							} else {
								contents[sbCount].append(separator);
							}
							contents[sbCount].append(separator).append(separator);
							
							if (!isFromScheduler) {
								contents[sbCount].append("<h1></h1>");
							}
							contents[sbCount].append(headerContents[sbCount]);
							setPageNumber(contents, pageNumber, sbCount);
						}
							
					}if(i==9 && results){
						contents[sbCount].append(separator).append(separator);
						contents[sbCount].append(footer);
						setEmptyLines(contents[sbCount], rptLPP, pageNumber[sbCount]);
						contents[sbCount].append(separator).append(separator);
						contents[sbCount].append(separator).append(separator);
						contents[sbCount].append(separator).append(separator);
					} 
					
				} else {
					int allRecords = 1;
					String tempDlr = "";
					int sbCount = 0;
					int oldSbCount = -1;
					for(List<String> list:colValList) {
						int valueCount = 0;
						boolean isEmpty = false;
						boolean isDiffDlr = false;
						//DPB monthly reconciliation details start
						if(isFromScheduler)
						{
						//Adjustment Summary - Changed condition to include case of cars - Kshitija
						if(i==rcList.size()-1)
						{
							if(list!= null && list.toString().contains("Total"))
							{
								String dlrId = "";
								if(0<list.size() && list.get(0) != null)
								{
								 dlrId = list.get(0).trim();
								}
								String totalPvsAdjWithComma = list.get(list.size()-2);
								double totalPvsAdj= getFormattedBonusAmount(totalPvsAdjWithComma);
								//ftl
								if(reportCriteria.getRptId()==6)
								{
								ftlTotalMap.put(dlrId, totalPvsAdj);
								}
								//van
								if(reportCriteria.getRptId()==3)
								{
								vanTotalMap.put(dlrId, totalPvsAdj);
								}
								//smart
								if(reportCriteria.getRptId()==4)
								{
								smartTotalMap.put(dlrId, totalPvsAdj);
								}
								//passenger
								if(reportCriteria.getRptId()==5)
								{
								passengerTotalMap.put(dlrId, totalPvsAdj);
								}
								//amg elite
								if(reportCriteria.getRptId()==9)
								{
								amgEliteTotalMap.put(dlrId, totalPvsAdj);
								}
								//amg perf
								if(reportCriteria.getRptId()==8)
								{
								amgPerfTotalMap.put(dlrId, totalPvsAdj);
								}
								
								
							}
						}
					}
					//DPB monthly reconciliation details end
						for(String value :list) {
							if (valueCount == 0) {
								String searchStr = "DEALER: " + value.trim();
								//Added code to check if any dealer present only with adjustment bonus along with other dealers with normal bonus - Start - Kshitija
								boolean onlyAdjustmentPresent = false;
								List<StringBuffer> contentsList = Arrays.asList(contents);
								if(contentsList!= null && !contentsList.get(0).toString().equals("") && !contentsList.toString().contains(searchStr))
								{

									onlyAdjustmentPresent = true;
									sbCount = contentsList.size();
									StringBuffer[] newContentsArray = new StringBuffer[contents.length+1];
									System.arraycopy(contents, 0, newContentsArray, 0, contents.length);
									contents = new StringBuffer[contentsList.size()+1];
									System.arraycopy(newContentsArray, 0, contents, 0, newContentsArray.length);
									
									StringBuffer[] newAdjustmentHeader = new StringBuffer[adjustmentHeader.length+1];
									System.arraycopy(adjustmentHeader, 0, newAdjustmentHeader, 0, adjustmentHeader.length);
									adjustmentHeader = new StringBuffer[adjustmentHeader.length+1];
									System.arraycopy(newAdjustmentHeader, 0, adjustmentHeader, 0, newAdjustmentHeader.length);
									
									Integer[] newPageNumber = new Integer[pageNumber.length+1];
									System.arraycopy(pageNumber, 0, newPageNumber, 0, pageNumber.length);
									pageNumber = new Integer[pageNumber.length+1];
									System.arraycopy(newPageNumber, 0, pageNumber, 0, newPageNumber.length);
									
									if(contents[sbCount] == null)
									{
									contents[sbCount] =  new StringBuffer("");
									}
									if(pageNumber[sbCount] == null)
									{
									pageNumber[sbCount] = 1;
									}
									adjustmentHeader[sbCount] = new StringBuffer("");
									
									
									
								
								}
								//Added code to check if any dealer present only with adjustment bonus along with other dealers with normal bonus - End - Kshitija
								if(!value.equalsIgnoreCase(tempDlr)){
									sbCount = 0;
									for (StringBuffer content : contents) {
										//comment start - kshitija
										/*if(content.toString().contains(searchStr)) {*/
										//comment end - kshitija
										//added if to avoid np start - kshitija
										if(content == null)
										{
											if(contents[sbCount] == null)
											{
											contents[sbCount] =  new StringBuffer("");
											}
											if(pageNumber[sbCount] == null)
											{
											pageNumber[sbCount] = 1;
											}
										}
										//added if to avoid np end - kshitija
										//added  if consition Start - Kshitija
										boolean checkContent = true;
										if(onlyAdjustmentPresent == false && contentsList!= null && !contentsList.get(0).toString().equals(""))
										{
										/*if(reportResults == true)
										{*/
											checkContent = content.toString().contains(searchStr);
										/*}*/
										}
										if(checkContent) {
										//added  if condition End - Kshitija
											tempDlr = value;
											isDiffDlr = true;
											if(i == rcList.size()-1 && oldSbCount != -1) {
												contents[oldSbCount].append(separator).append(separator);
												contents[oldSbCount].append(separator).append(separator);
												contents[oldSbCount].append(footer);
												setEmptyLines(contents[oldSbCount], rptLPP, pageNumber[oldSbCount]);
												contents[oldSbCount].append(separator).append(separator);
												//contents[oldSbCount].append(separator).append(separator);
												contents[oldSbCount].append(separator);
											}
											oldSbCount = sbCount;
											
											if(i==12){
												contents[sbCount].append(separator).append(separator);
												contents[sbCount].append(separator).append(separator);
											}
											
											break;
											//comment start - kshitija
										}
										//comment end - kshitija
											//comment start - kshitija
										sbCount++;
											//comment end - kshitija
									}
									
									if (sbCount >= contents.length){
										contents[sbCount-1].append(rptCnt.getReportContent());
										contents[sbCount-1].append(separator);
									}
									else
									{
									contents[sbCount].append(rptCnt.getReportContent());
									contents[sbCount].append(separator);
									}
									
								}
							} else {
								
								if (sbCount >= contents.length)
									contents[sbCount-1].append(value);
								else
									contents[sbCount].append(value);
								
								
							}
							valueCount++;
						}
						if (sbCount >= contents.length){
							
							contents[sbCount-1].append(separator);
							StringBuffer buffer = new StringBuffer(contents[sbCount-1].toString());
							int len = buffer.toString().split(separator).length;
							len = len - (pageNumber[sbCount-1]<=2?0:(pageNumber[sbCount-1]-2)*5);
							if (len % rptLPP == 0) {
								contents[sbCount-1].append(separator).append(separator);
								contents[sbCount-1].append(separator);
								contents[sbCount-1].append(separator).append(separator);
								
								if (!isFromScheduler) {
									contents[sbCount-1].append("<h1></h1>");
								}
								contents[sbCount-1].append(adjustmentHeader[sbCount-1]);
								setPageNumber(contents, pageNumber, sbCount-1);
							}
							
						}
						else
						{
							
						contents[sbCount].append(separator);
						StringBuffer buffer = new StringBuffer(contents[sbCount].toString());
						int len = buffer.toString().split(separator).length;
						len = len - (pageNumber[sbCount]<=2?0:(pageNumber[sbCount]-2)*5);
						if (len % rptLPP == 0) {
							contents[sbCount].append(separator).append(separator);
							contents[sbCount].append(separator);
							contents[sbCount].append(separator).append(separator);
							
							if (!isFromScheduler) {
								contents[sbCount].append("<h1></h1>");
							}
							contents[sbCount].append(adjustmentHeader[sbCount]);
							setPageNumber(contents, pageNumber, sbCount);
						}
						}
						
					}
					
					if(i == rcList.size()-1 && colValList != null && colValList.size() > 0) {
						
						if (sbCount >= contents.length){
							contents[sbCount-1].append(separator).append(separator);
							contents[sbCount-1].append(separator).append(separator);
							contents[sbCount-1].append(footer);
							setEmptyLines(contents[sbCount-1], rptLPP, pageNumber[sbCount-1]);
							contents[sbCount-1].append(separator).append(separator);
							//contents[sbCount].append(separator).append(separator);
							contents[sbCount-1].append(separator);
						}
						else
						{
						contents[sbCount].append(separator).append(separator);
						contents[sbCount].append(separator).append(separator);
						contents[sbCount].append(footer);
						setEmptyLines(contents[sbCount], rptLPP, pageNumber[sbCount]);
						contents[sbCount].append(separator).append(separator);
						//contents[sbCount].append(separator).append(separator);
						contents[sbCount].append(separator);
						}
					}
					
				}
				
				if (i == rcList.size()-1) {
					int pageCount = 0;
					if(reportResults && !isFromScheduler) {
						 for(StringBuffer content: contents) {
							
							if(pageCount < contents.length - 1) {
								content.append("<h1></h1>");
							}
							pageCount++;
						}
					}
				}
			}
			//kshitija
			//finalContentsArrayList.add(contents);
			//kshitija
		}
		//DPB monthly reconciliation details start
		if(isFromScheduler)
		{
		for(int i=0;i<ftlDlrList.size();i++)
		{
			if(ftlTotalMap.get(ftlDlrList.get(i))!= null)
			{
				ftlTotalMap.put(ftlDlrList.get(i), ftlTotalMap.get(ftlDlrList.get(i))+ftlAmntList.get(i));
			}
			else
			{
			ftlTotalMap.put(ftlDlrList.get(i), ftlAmntList.get(i));
			}
		}
		reportCriteria.setTotalFtlMthlyAmt(ftlTotalMap);
		
		for(int i=0;i<vanDlrList.size();i++)
		{
			if(vanTotalMap.get(vanDlrList.get(i))!= null)
			{
				vanTotalMap.put(vanDlrList.get(i), vanTotalMap.get(vanDlrList.get(i))+vanAmntList.get(i));
			}
			else
			{
			vanTotalMap.put(vanDlrList.get(i), vanAmntList.get(i));
			}
		}
		reportCriteria.setTotalVanMthlyAmt(vanTotalMap);
		
		for(int i=0;i<smartDlrList.size();i++)
		{
			if(smartTotalMap.get(smartDlrList.get(i))!= null)
			{
				smartTotalMap.put(smartDlrList.get(i), smartTotalMap.get(smartDlrList.get(i))+smartAmntList.get(i));
			}
			else
			{
				smartTotalMap.put(smartDlrList.get(i), smartAmntList.get(i));
			}
		}
		reportCriteria.setTotalSmartMthlyAmt(smartTotalMap);
		
		for(int i=0;i<passengerDlrList.size();i++)
		{
			if(passengerTotalMap.get(passengerDlrList.get(i))!= null)
			{
				passengerTotalMap.put(passengerDlrList.get(i), passengerTotalMap.get(passengerDlrList.get(i))+passengerAmntList.get(i));
			}
			else
			{
				passengerTotalMap.put(passengerDlrList.get(i), passengerAmntList.get(i));
			}
		}
		reportCriteria.setTotalPassengerMthlyAmt(passengerTotalMap);
		for(int i=0;i<amgEliteDlrList.size();i++)
		{
			if(amgEliteTotalMap.get(amgEliteDlrList.get(i))!= null)
			{
				amgEliteTotalMap.put(amgEliteDlrList.get(i), amgEliteTotalMap.get(amgEliteDlrList.get(i))+amgEliteAmntList.get(i));
			}
			else
			{
				amgEliteTotalMap.put(amgEliteDlrList.get(i), amgEliteAmntList.get(i));
			}
		}
		reportCriteria.setTotalAmgEliteMnthlyAmnt(amgEliteTotalMap);
		for(int i=0;i<amgPerfDlrList.size();i++)
		{
			if(amgPerfTotalMap.get(amgPerfDlrList.get(i))!= null)
			{
				amgPerfTotalMap.put(amgPerfDlrList.get(i), amgPerfTotalMap.get(amgPerfDlrList.get(i))+amgPerfAmntList.get(i));
			}
			else
			{
				amgPerfTotalMap.put(amgPerfDlrList.get(i), amgPerfAmntList.get(i));
			}
		}
		reportCriteria.setTotalAmgPerfMnthlyAmnt(amgPerfTotalMap);
		}
		//DPB monthly reconciliation details end
		//removed if start- kshitija
		/*if (reportResults)*/
		//removed if end- kshitija
			return getContentFromArray(contents);
			//removed else start- kshitija
		/*else
			return new StringBuffer("");*/
			//removed else end- kshitija
	
	}
//kshitija daily/monthly payment details
	private double getFormattedBonusAmount(String totalDealerBonusWithComma) {
		// TODO Auto-generated method stub
		
		String totalDealerBonusWithoutComma = totalDealerBonusWithComma.replaceAll(",", "");
		if(totalDealerBonusWithoutComma.contains("-"))
		{
			totalDealerBonusWithoutComma = totalDealerBonusWithoutComma.replaceAll("-", "");
			totalDealerBonusWithoutComma = "-"+totalDealerBonusWithoutComma;
		}
		double totalDealerBonus = Double.parseDouble(totalDealerBonusWithoutComma);
		return totalDealerBonus;
	}
	//DPB monthly reconciliation details end

	private StringBuffer generateDailyReport(List rptCntList, int rptLPP,
			NetStarReport reportCriteria, boolean isFromScheduler) {
		ReportQuery rptQry = new ReportQuery();
		ReportContentDefinitionBean rptCnt = new ReportContentDefinitionBean();
		List<ReportQuery> rqList = null;
		List<List<String>> colValList = null;
		List<ReportContentDefinitionBean> rcList = null;
		rcList = (List<ReportContentDefinitionBean>) rptCntList.get(0);
		rqList = (List<ReportQuery>) rptCntList.get(1);
		StringBuffer[] contents = null;
		boolean results = false;
		
		for (int i=0;i<rcList.size();i++) {
			rptCnt = rcList.get(i);
			rptQry = rqList.get(i);
			String query = rptQry.getQuery();
			query = replaceCriteria(query);
			colValList = reportDAO.getQueryData(query);
			
			if (colValList != null && colValList.size() > 0) {
				results = true;
			}
			if (i == 0) {
				reportCriteria.setRecCount(colValList.size());
				if(colValList == null || colValList.size() == 0) {
					contents = new StringBuffer[1];
					contents[0] = new StringBuffer("");
				} else {
					contents = new StringBuffer[colValList.size()];
				}
			}
			if(rptCnt.getReportContent().contains("{0}")){
				int sbCount = 0;
				//DPB daily payment details 0032484965 start - Kshitija
				double totalDailyBonus = 0; 
				double totalDealerBonus = 0;
				String totalDealerBonusWithComma = null;
				String totalDealerBonusWithoutComma = null;
				int totalIndexLocation = 0;
				if(reportCriteria!= null && reportCriteria.getRptId() == 1)
				{
					totalIndexLocation = 13;
				}
				else if(reportCriteria!= null && reportCriteria.getRptId() == 2)
				{
					totalIndexLocation = 11;
				}
				//DPB daily payment details 0032484965 end - Kshitija
				for(List<String> dList: colValList) {
					contents[sbCount] = new StringBuffer("");
					if (!isFromScheduler && sbCount == 0) {
						contents[0].append(IConstants.TOTAL_NO_RECORDS + (colValList == null ? 0 : colValList.size()));
						contents[0].append(separator).append(separator);
					}
					contents[sbCount].append(MessageFormat.format(rptCnt.getReportContent(), dList.toArray()));
					contents[sbCount].append(separator);
					//DPB daily payment details 0032484965 start - Kshitija
					if(isFromScheduler == true)
					{
					totalDealerBonusWithComma = dList.get(totalIndexLocation).trim();
					totalDealerBonusWithoutComma = totalDealerBonusWithComma.replaceAll(",", "");
					if(totalDealerBonusWithoutComma.contains("-"))
					{
						totalDealerBonusWithoutComma = totalDealerBonusWithoutComma.replaceAll("-", "");
						totalDealerBonusWithoutComma = "-"+totalDealerBonusWithoutComma;
					}
					totalDealerBonus = Double.parseDouble(totalDealerBonusWithoutComma);
					totalDailyBonus = totalDailyBonus+totalDealerBonus;
					}
					//DPB daily payment details 0032484965 end - Kshitija
					setEmptyLines(contents[sbCount], rptLPP, 0);
					if (!isFromScheduler && sbCount < colValList.size() - 1) {
						contents[sbCount].append("<h1></h1>");
					}
					sbCount++;
				}
				//DPB daily payment details 0032484965 start - Kshitija
				if(colValList!= null && !colValList.isEmpty() && isFromScheduler == true)
				{
					reportCriteria.setTotalReportAmount(totalDailyBonus);
				}
				//DPB daily payment details 0032484965 end - Kshitija
			}
		}
		if (results)
			return getContentFromArray(contents);
		else
			return new StringBuffer("");
	}

	private void setEmptyLines(StringBuffer content, int rptLPP, int pages) {
		StringBuffer buffer = new StringBuffer(content.toString());
		int len = buffer.toString().split(System.getProperty("line.separator")).length;
		len = len - ((pages<=2?0:pages-2)*5);
		for(;len % rptLPP != 0; len++) {
			content.append(separator);
		}
	}

	private void setPageNumber(StringBuffer[] contents, Integer[] pageNumber,
			int sbCount) {
		String number = pageNumber[sbCount] < 10 ? ("0" + pageNumber[sbCount]) : String.valueOf(pageNumber[sbCount]);
		contents[sbCount] = new StringBuffer(contents[sbCount].toString().replaceAll(IConstants.PAGE_NUMBER, number));
		pageNumber[sbCount]++;
	}

	private StringBuffer getContentFromArray(StringBuffer[] contents) {
		StringBuffer sb = new StringBuffer("");
		for (StringBuffer content: contents) {
			sb.append(content);

		}
		return sb;
	}

	private String replaceCriteria(String query) {
		String finalQuery = "";
		finalQuery = query;
		finalQuery = finalQuery.replaceAll(IConstants.KPI_DATE_RANGE_FOR_REPORTS, kpiDateRange);
		finalQuery = finalQuery.replaceAll(IConstants.KPI_DEALER_RANGE_FOR_REPORTS, kpiDealerRange);		
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_REPORTS, dateRange);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_ALL_DATES, dateRangeForAllDates);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_PRIO, prioDateRange);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_PAY, payDateRange);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_OWED, owedDateRange);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_ADJ_PAY, adjDatePay);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_REP_ADJ, adjDateReport);
		finalQuery = finalQuery.replaceAll(IConstants.VEHICLE_TYPE_RANGE_FOR_REPORTS, vehTypeRange);
		finalQuery = finalQuery.replaceAll(IConstants.DEALER_RANGE_FOR_REPORTS, dealerRange);
		finalQuery = finalQuery.replaceAll(IConstants.DEFAULT_YEARS, yearsCriteria);
		finalQuery = finalQuery.replaceAll(IConstants.PO_RANGE_FOR_REPORTS, poRange);
		finalQuery = finalQuery.replaceAll(IConstants.VIN_RANGE_FOR_REPORTS, vinRange);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_ADJUSTMENTS, adjustmentDateRange);
		finalQuery = finalQuery.replaceAll(IConstants.OLDPAYOUT_DEALER_RANGE_FOR_RPRT, oldPayoutDealerRange);
		
		finalQuery = finalQuery.replaceAll(IConstants.VEHICLE_TYPE1, vehType1);
		finalQuery = finalQuery.replaceAll(IConstants.VEHICLE_TYPE2, vehType2);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE1, dateRange1);
		finalQuery = finalQuery.replaceAll(IConstants.QUARTER_RANGE1, qtrRange1);
		finalQuery = finalQuery.replaceAll(IConstants.QUARTER_RANGE2, qtrRange2);
		finalQuery = finalQuery.replaceAll(IConstants.QUARTER_RANGE3, qtrRange3);
		finalQuery = finalQuery.replaceAll(IConstants.HEADER, header);

		//Fixed Margin-Start
		finalQuery = finalQuery.replaceAll(IConstants.ALL_DATE_RANGE, allDateRange);
		finalQuery = finalQuery.replaceAll(IConstants.MONTHLY_DATE_RANGE, monthlyDateRange);
		finalQuery = finalQuery.replaceAll(IConstants.QUARTERLY_DATE_RANGE, quarterlyDateRange);
		finalQuery = finalQuery.replaceAll(IConstants.YEARLY_DATE_RANGE, yearlyDateRange);
		finalQuery = finalQuery.replaceAll(IConstants.BONUS_DATE_RANGE, bonusDateRange);
		//Fixed Margin-End
		
		//AMG-Start
		//finalQuery = finalQuery.replaceAll(IConstants.PROGRAM_ID, programID);
		//AMG-End
		
		return finalQuery;
	}
	
	/**
	 * This method is used to replace varable to actual query parameter.
	 * @param query
	 * @param DealerChunk
	 * @return String  Final query after replacing parameter values.
	 */
	private String replaceCriteriaForDCQPR(String query , String DealerChunk){
		String finalQuery = "";
		finalQuery = query;
		finalQuery = finalQuery.replaceAll(IConstants.KPI_DATE_RANGE_FOR_REPORTS, kpiDateRange);
		finalQuery = finalQuery.replaceAll(IConstants.KPI_DEALER_RANGE_FOR_REPORTS, kpiDealerRange);		
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_REPORTS, dateRange);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_ALL_DATES, dateRangeForAllDates);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_PRIO, prioDateRange);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_PAY, payDateRange);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_OWED, owedDateRange);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_ADJ_PAY, adjDatePay);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_REP_ADJ, adjDateReport);
		finalQuery = finalQuery.replaceAll(IConstants.VEHICLE_TYPE_RANGE_FOR_REPORTS, vehTypeRange);
		finalQuery = finalQuery.replaceAll(IConstants.DEALER_RANGE_FOR_REPORTS, dealerRange);
		finalQuery = finalQuery.replaceAll(IConstants.DEFAULT_YEARS, yearsCriteria);
		finalQuery = finalQuery.replaceAll(IConstants.PO_RANGE_FOR_REPORTS, poRange);
		finalQuery = finalQuery.replaceAll(IConstants.VIN_RANGE_FOR_REPORTS, vinRange);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE_FOR_ADJUSTMENTS, adjustmentDateRange);
		finalQuery = finalQuery.replaceAll(IConstants.OLDPAYOUT_DEALER_RANGE_FOR_RPRT, DealerChunk);
		
		finalQuery = finalQuery.replaceAll(IConstants.VEHICLE_TYPE1, vehType1);
		finalQuery = finalQuery.replaceAll(IConstants.VEHICLE_TYPE2, vehType2);
		finalQuery = finalQuery.replaceAll(IConstants.DATE_RANGE1, dateRange1);
		finalQuery = finalQuery.replaceAll(IConstants.QUARTER_RANGE1, qtrRange1);
		finalQuery = finalQuery.replaceAll(IConstants.QUARTER_RANGE2, qtrRange2);
		finalQuery = finalQuery.replaceAll(IConstants.QUARTER_RANGE3, qtrRange3);
		finalQuery = finalQuery.replaceAll(IConstants.HEADER, header);
		
		
		return finalQuery;
	}
	
	private void setSearchCriteria(NetStarReport reportCriteria,
			boolean isFromScheduler) {
		
		dateRange = "";
		dateRangeForAllDates = "";
		prioDateRange = "";
		payDateRange = "";
		owedDateRange = "";
		vehTypeRange = "";
		dealerRange = "";
		yearsCriteria = "2010";
		kpiDateRange = "";
		kpiDealerRange = "";
		poRange = "";
		vinRange = "";
		adjustmentDateRange = "";
		adjDatePay="";
		adjDateReport="";
		oldPayoutDealerRange = "";
		
		//AMG -Start
		//programID = "";
		//AMG -End
		
		boolean searchConditionForCVP = false;
		
		
		String month = reportCriteria.getMonth() < 10 ? "0"+reportCriteria.getMonth() : ""+reportCriteria.getMonth();
		String adjustmentMonth = reportCriteria.getMonth()-1 < 10 ? "0"+(reportCriteria.getMonth()-1) : ""+(reportCriteria.getMonth()-1);
		
		if (isFromScheduler) {
			if(IConstants.SCHD_DAILY.equalsIgnoreCase(reportCriteria.getSchedule())) {
				dateRange = " where dim.DTE_CAL = date('"+reportCriteria.getActualDate()+"') - 1 day ";
			} else if (IConstants.SCHD_MONTH.equalsIgnoreCase(reportCriteria.getSchedule())) {
				
				dateRangeForAllDates = " where dim.NUM_RETL_YR = " +
						"(select dim1.NUM_RETL_YR from DPB_DAY dim1 where dim1.DTE_CAL =  date('"+reportCriteria.getActualDate()+"') - 1 day ) " +
						"and dim.NUM_RETL_MTH <= (select dim1.NUM_RETL_MTH from DPB_DAY dim1 where dim1.DTE_CAL = date('"+reportCriteria.getActualDate()+"') - 1 day )";
				dateRange = " where (dim.NUM_RETL_YR,dim.NUM_RETL_MTH) in " +
						"(select dim1.NUM_RETL_YR,dim1.NUM_RETL_MTH from DPB_DAY dim1 where dim1.DTE_CAL = date('"+reportCriteria.getActualDate()+"') - 1 day ) ";
				
				
				adjustmentDateRange = " where (dim.NUM_RETL_MTH,dim.NUM_RETL_YR) in " +
						" (select df.NUM_RETL_MTH,df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = date('"+reportCriteria.getActualDate()+"') - 40 day ) ";
				
			} else if (IConstants.SCHD_YEARLY.equalsIgnoreCase(reportCriteria.getSchedule())) {
				dateRange = " where (dim.NUM_RETL_YR,dim.NUM_RETL_MTH) in " +
						"(select dim1.NUM_RETL_YR,dim1.NUM_RETL_MTH from DPB_DAY dim1 where dim1.DTE_CAL = date('"+reportCriteria.getActualDate()+"') - 1 day ) ";
			}
			if (IConstants.SCHD_YEARLY.equalsIgnoreCase(reportCriteria.getSchedule())) {
				vehTypeRange = " and rtl.CDE_VEH_TYP in " +getVehTypes(reportCriteria.getVehicleType());
			} else {
				vehTypeRange = " and rtl.CDE_VEH_TYP in ('P','S','V','F') " ;
			}
		} else if (reportCriteria != null) {
			
			if (reportCriteria.isStatic()) {
			
				if(reportCriteria.getRptId() == 3 ) {
					if(reportCriteria.getDealer() != null && reportCriteria.getDealer().length() > 0) {
						//for issue 30525949
						oldPayoutDealerRange = " where dlr.ID_DLR in " +getDealers(reportCriteria.getDealer());
						//oldPayoutDealerRange = " and dlr.ID_DLR in " +getDealers(reportCriteria.getDealer());
						dealerRange = " AND calc.ID_DLR in " +getDealers(reportCriteria.getDealer());
					}
				}
				//Dealer Compliance Quarterly Payouts Report-Old Start
				if(reportCriteria.getRptId() == 15 ) {
					if(reportCriteria.getDealer() != null && reportCriteria.getDealer().length() > 0) {
						//for issue 30525949
						oldPayoutDealerRange = " where dlr.ID_DLR in " +getDealers(reportCriteria.getDealer());
						//oldPayoutDealerRange = " and dlr.ID_DLR in " +getDealers(reportCriteria.getDealer());
						dealerRange = " AND calc.ID_DLR in " +getDealers(reportCriteria.getDealer());
					}
				}
				//Dealer Compliance Quarterly Payouts Report-Old End
				else if(reportCriteria.getRptId() == 6){
					if(reportCriteria.getDealer() != null && reportCriteria.getDealer().length() > 0) {
						dealerRange = " AND rtl.ID_DLR in " +getDealers(reportCriteria.getDealer());
				}
				}
				//Vehicle Details Report
				else if(reportCriteria.getRptId() != 11){
					if(reportCriteria.getDealer() != null && reportCriteria.getDealer().length() > 0) {
						dealerRange = " and calc.ID_DLR in " +getDealers(reportCriteria.getDealer());
					}
				}
				// for MBDeal and CVP payout reports DPB have criteria  as report date. 
				// AMG Performance Center report and AMG Elite Ceter report
				if(reportCriteria.getRptId() == 1  || reportCriteria.getRptId() == 2 //|| reportCriteria.getRptId() == 5 ||reportCriteria.getRptId() == 14
						|| reportCriteria.getRptId() == 12 || reportCriteria.getRptId() == 13){
					if (reportCriteria.getFromDate() != null && reportCriteria.getFromDate().length() > 0
							&& reportCriteria.getToDate() != null && reportCriteria.getToDate().length() > 0) {
						dateRange = "and rtl.DTE_RTL between '"+ reportCriteria.getFromDate() +"'  and '"+reportCriteria.getToDate()+"'";
						searchConditionForCVP = true;
					} 
					if (reportCriteria.getReportDate() != null && reportCriteria.getReportDate().length() > 0){
						dateRange = " and rtl.DTE_RTL = '"+reportCriteria.getReportDate()+"' ";
					}	
				}
				
				if(reportCriteria.getRptId() == 12) {
					
					//programID = " and calc.ID_DPB_PGM in "+ getPrograms(reportCriteria.getAmgProgramList());
					
				}
				
				// Dealer Performance Unearned Bonus Report
				//Unearned Performance Bonus calculation  : added report id 14
				if(reportCriteria.getRptId() == 5 || reportCriteria.getRptId() == 14 ||
						((reportCriteria.getRptId() == 1 || reportCriteria.getRptId() == 2) && !searchConditionForCVP)){
					setRetailStartAndEndDates(reportCriteria);
					
					LOGGER.info("ubFromDate:"+ubFromDate+"~ubToDate:"+ubToDate);
					//dateRange = "and rtl.DTE_RTL>='"+ ubFromDate+"' and rtl.DTE_RTL< '"+ubToDate +"'";
					dateRange = "and rtl.DTE_RTL between '"+ ubFromDate+"' and '"+ubToDate +"'";
				}
				
				else if(reportCriteria.getRptId() != 11 && !searchConditionForCVP){				
				if (reportCriteria.getFromDate() != null && reportCriteria.getFromDate().length() > 0
						&& reportCriteria.getToDate() != null && reportCriteria.getToDate().length() > 0) {
					dateRange = "where dim.DTE_CAL between '"+ reportCriteria.getFromDate() +"'  and '"+reportCriteria.getToDate()+"'";
				} else if (reportCriteria.getReportDate() != null && reportCriteria.getReportDate().length() > 0){
					dateRange = " where dim.DTE_CAL = '"+reportCriteria.getReportDate()+"' ";
				}
				}
				
				if(reportCriteria.getRptId() == 6){
					if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() > 0) {
						dateRange = " AND YEAR(rtl.DTE_RTL) = '"+ reportCriteria.getYear() +"' and MONTH(rtl.DTE_RTL) = '"+ month +"' ";
					} else if (reportCriteria.getMonth() < 0 && reportCriteria.getYear() > 0) {
						dateRange = " AND YEAR(rtl.DTE_RTL) = '"+ reportCriteria.getYear() +"' ";
					} else if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() < 0) {
						dateRange = " AND MONTH(rtl.DTE_RTL) = '"+ month +"' ";
					}
				}
				//Fixed margin-Start
				else if(reportCriteria.getRptId() == 16) {
					
					allDateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH <= '"+ month +"' ";

					monthlyDateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH = '"+ month +"' ";

					quarterlyDateRange = " where (fdr.NUM_RETL_QTR,fdr.NUM_RETL_YR) in (select NUM_RETL_QTR,dim.NUM_RETL_YR from " 	+
					" ALL_DATES dim where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH = '"+ month +"' " +
					" )and  fdr.NUM_RETL_MTH <= '"+ month +"' ";  

					yearlyDateRange = " where (fdr.NUM_RETL_QTR,fdr.NUM_RETL_YR) in (select NUM_RETL_QTR,dim.NUM_RETL_YR from " 	+
							" ALL_DATES dim where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH <= '"+ month +"' " +
							" )and  fdr.NUM_RETL_MTH <= '"+ month +"' "; 
					
					//If month is JAN/FEB/MAR,the system will have to fetch the retail start date of April of Previous year
					if("01".equalsIgnoreCase(month) || "02".equalsIgnoreCase(month) || "03".equalsIgnoreCase(month)) {
						bonusDateRange = " where dim.NUM_RETL_YR = '"+ (reportCriteria.getYear()- 1) +"' ";	
					}
					else {						
						bonusDateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' ";						
					}
				}
				//Fixed margin-End
				//Dealer Compliance Summary Report  - Added id
				else if( reportCriteria.getRptId() == 7 || reportCriteria.getRptId() == 8 || reportCriteria.getRptId() == 4 || reportCriteria.getRptId() == IConstants.DLR_COMPL_SUM_REPORT_ID){
					if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() > 0) {
						dateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH = '"+ month +"' ";
						dateRangeForAllDates = "where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH <= '"+ month +"' ";
					} else if (reportCriteria.getMonth() < 0 && reportCriteria.getYear() > 0) {
						dateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' " +
								" and dim.NUM_RETL_MTH = (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
						dateRangeForAllDates = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' " +
								" and dim.NUM_RETL_MTH <= (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
					} else if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() < 0) {
						dateRange = " where dim.NUM_RETL_MTH = '"+ month +"' " +
								" and dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) ";
						dateRangeForAllDates = " where dim.NUM_RETL_MTH <= '"+ month +"' " +
								" and dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) ";
					} else {
						dateRange = " where dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) " +
								" and dim.NUM_RETL_MTH = (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
						dateRangeForAllDates = " where dim.NUM_RETL_YR = " +
								"(select dim1.NUM_RETL_YR from DPB_DAY dim1 where dim1.DTE_CAL =  current date ) " +
								"and dim.NUM_RETL_MTH <= (select dim1.NUM_RETL_MTH from DPB_DAY dim1 where dim1.DTE_CAL = current date )";
					}
					//Prior retails date range
					if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() > 0) {
						prioDateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH < '"+ month +"' ";
					} else if (reportCriteria.getMonth() < 0 && reportCriteria.getYear() > 0) {
						prioDateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' " +
								" and dim.NUM_RETL_MTH < (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
					} else if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() < 0) {
						prioDateRange = " where dim.NUM_RETL_MTH < '"+ month +"' " +
								" and dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) ";
					} else {
						prioDateRange = " where dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) " +
								" and dim.NUM_RETL_MTH < (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
					}
					//Payments date range
					if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() > 0) {
						payDateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH = '"+ month +"' )"+
								"and fdr.NUM_RETL_MTH <= '"+ month +"' ";
					} else if (reportCriteria.getMonth() < 0 && reportCriteria.getYear() > 0) {
						payDateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' " +
								" and dim.NUM_RETL_MTH = (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) )"+
								" and fdr.NUM_RETL_MTH <= (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date)";
					} else if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() < 0) {
						payDateRange = " where dim.NUM_RETL_MTH = '"+ month +"' " +
								" and dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) )+" +
								" and fdr.NUM_RETL_MTH <= '"+ month +"' ";
					} else {
						payDateRange = " where dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) " +
								" and dim.NUM_RETL_MTH = (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) )"+
								" and fdr.NUM_RETL_MTH <= (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
					}
					//Payments date range for adjustments
					if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() > 0) {
						adjDatePay = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH = '"+ adjustmentMonth +"' )"+
								"and fdr.NUM_RETL_MTH <= '"+ adjustmentMonth +"' ";
					} else if (reportCriteria.getMonth() < 0 && reportCriteria.getYear() > 0) {
						adjDatePay = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' " +
								" and dim.NUM_RETL_MTH = (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) )"+
								" and fdr.NUM_RETL_MTH <= (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date)";
					} else if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() < 0) {
						adjDatePay = " where dim.NUM_RETL_MTH = '"+ adjustmentMonth +"' " +
								" and dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) )+" +
								" and fdr.NUM_RETL_MTH <= '"+ adjustmentMonth +"' ";
					} else {
						adjDatePay = " where dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) " +
								" and dim.NUM_RETL_MTH = (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) )"+
								" and fdr.NUM_RETL_MTH <= (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
					}
					//Date range for adjustments
					if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() > 0) {
						adjDateReport = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH = '"+ adjustmentMonth +"' ";
					} else if (reportCriteria.getMonth() < 0 && reportCriteria.getYear() > 0) {
						adjDateReport = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' " +
								" and dim.NUM_RETL_MTH = (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
					} else if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() < 0) {
						adjDateReport = " where dim.NUM_RETL_MTH = '"+ adjustmentMonth +"' " +
								" and dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) ";
					} else {
						adjDateReport = " where dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) " +
								" and dim.NUM_RETL_MTH = (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
					}
					//Owed date range
					if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() > 0) {
						owedDateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH <= '"+ month +"' )"+
								"and fdr.NUM_RETL_MTH <= '"+ month +"' ";
					} else if (reportCriteria.getMonth() < 0 && reportCriteria.getYear() > 0) {
						owedDateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' " +
								" and dim.NUM_RETL_MTH <= (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) )"+
								" and fdr.NUM_RETL_MTH <= (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date)";
					} else if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() < 0) {
						owedDateRange = " where dim.NUM_RETL_MTH <= '"+ month +"' " +
								" and dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) )+" +
								" and fdr.NUM_RETL_MTH <= '"+ month +"' ";
					} else {
						owedDateRange = " where dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) " +
								" and dim.NUM_RETL_MTH <= (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) )"+
								" and fdr.NUM_RETL_MTH <= (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
					}
					
				} 
				else if(reportCriteria.getRptId() == 8){
					if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() > 0) {
						dateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH = '"+ month +"' ";
						yearsCriteria = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH <= '"+ month +"' ";
					} else if (reportCriteria.getMonth() < 0 && reportCriteria.getYear() > 0) {
						dateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' " +
								" and dim.NUM_RETL_MTH = (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
						yearsCriteria = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' " +
								" and dim.NUM_RETL_MTH <= (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
						
					} else if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() < 0) {
						dateRange = " where dim.NUM_RETL_MTH = '"+ month +"' " +
								" and dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) ";
						yearsCriteria = " where dim.NUM_RETL_MTH <= '"+ month +"' " +
								" and dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) ";
					} else {
						dateRange = " where dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) " +
								" and dim.NUM_RETL_MTH = (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
						yearsCriteria = " where dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) " +
								" and dim.NUM_RETL_MTH <= (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
					}
				}
				else if(reportCriteria.getRptId() == 3 ) {
					if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() > 0) {
						dateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH = '"+ month +"' ";
					} else if (reportCriteria.getMonth() < 0 && reportCriteria.getYear() > 0) {
						dateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH = month(current date) ";
					} else if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() < 0) {
						dateRange = " where dim.NUM_RETL_MTH = '"+ month +"' and dim.NUM_RETL_YR = year(current date) ";
					} else {
						dateRange = " where (dim.NUM_RETL_YR,dim.NUM_RETL_MTH) in " +
								"(select dim1.NUM_RETL_YR,dim1.NUM_RETL_MTH from DPB_DAY dim1 where dim1.DTE_CAL = current date ) ";
					}
				}
				//Dealer Compliance Quarterly Payouts Report-Old Start
				else if(reportCriteria.getRptId() == 15 ) {
					if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() > 0) {
						dateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH = '"+ month +"' ";
					} else if (reportCriteria.getMonth() < 0 && reportCriteria.getYear() > 0) {
						dateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH = month(current date) ";
					} else if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() < 0) {
						dateRange = " where dim.NUM_RETL_MTH = '"+ month +"' and dim.NUM_RETL_YR = year(current date) ";
					} else {
						dateRange = " where (dim.NUM_RETL_YR,dim.NUM_RETL_MTH) in " +
								"(select dim1.NUM_RETL_YR,dim1.NUM_RETL_MTH from DPB_DAY dim1 where dim1.DTE_CAL = current date ) ";
					}
				}
				//Dealer Compliance Quarterly Payouts Report-Old End
				else {			
					if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() > 0) {
						dateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH = '"+ month +"' ";
					} else if (reportCriteria.getMonth() < 0 && reportCriteria.getYear() > 0) {
						dateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' ";
					} else if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() < 0) {
						dateRange = " where dim.NUM_RETL_MTH = '"+ month +"' ";
					}
				}
				//Unearned Performance Bonus calculation  : added report id 14
				if(reportCriteria.getRptId() != 5 && reportCriteria.getRptId() != 14 && reportCriteria.getRptId() != 3 && reportCriteria.getRptId() != 4 &&
						reportCriteria.getRptId() != 16 && reportCriteria.getRptId() != 7 && reportCriteria.getRptId() != 12 && reportCriteria.getRptId() != 13 && reportCriteria.getRptId() != 15){ // AMG Changes including 12 & 13.){
				if(reportCriteria.getVehicleType() != null && reportCriteria.getVehicleType().size() > 0) {
					vehTypeRange = " and rtl.CDE_VEH_TYP in " +getVehTypes(reportCriteria.getVehicleType());
				} else{
					vehTypeRange = " and rtl.CDE_VEH_TYP in ('P','S','V','F')";
				}
				}else {
					if (reportCriteria.getRptId() == 7) {
						vehTypeRange = " and rtl.CDE_VEH_TYP in "+getVehTypes(reportCriteria.getVehTypeRadio());
					}
					else if(reportCriteria.getVehicleTypeRd() != null && reportCriteria.getVehicleTypeRd().size() > 0) {
						vehTypeRange = " and rtl.CDE_VEH_TYP in " +getVehTypes(reportCriteria.getVehicleTypeRd());
					}  else{
						vehTypeRange = " and rtl.CDE_VEH_TYP in ('P')";
					}
				}
				
				//Vin lookup report -start
				if(reportCriteria.getRptId() == 10){
					// PO range
					if(reportCriteria.getPoNumber() != null && reportCriteria.getPoNumber().length() > 0) {
						poRange = " NUM_PO in " +getDealers(reportCriteria.getPoNumber());
					}
					//Vin Range
					if(reportCriteria.getVehicleRange() != null && reportCriteria.getVehicleRange().length() > 0) {
							vinRange =  " NUM_VIN in " +getDealers(reportCriteria.getVehicleRange());
						
					}
				
				}
				//Vin lookup report -end
				
				//Vehicle Details Report
				if(reportCriteria.getRptId() == 11){
					// PO range
					if(reportCriteria.getPoNumber() != null && reportCriteria.getPoNumber().length() > 0) {
						poRange = " rtl.NUM_PO in " +getDealers(reportCriteria.getPoNumber());
					}
					//Vin Range
					if(reportCriteria.getVehicleRange() != null && reportCriteria.getVehicleRange().length() > 0) {
						if(!DateCalenderUtil.isEmptyOrNullString(poRange)){
							vinRange = " AND "; 
						}
						vinRange = vinRange + " rtl.NUM_VIN in " +getDealers(reportCriteria.getVehicleRange());
					}
					//Dealer range
					if(reportCriteria.getDealer() != null && reportCriteria.getDealer().length() > 0) {
						if(!DateCalenderUtil.isEmptyOrNullString(poRange) ||
								!DateCalenderUtil.isEmptyOrNullString(vinRange)){
							dealerRange = " AND "; 
						}
						dealerRange = dealerRange + "  rtl.ID_DLR in " +getDealers(reportCriteria.getDealer());
					}
					//date range
					if (reportCriteria.getFromDate() != null && reportCriteria.getFromDate().length() > 0
									&& reportCriteria.getToDate() != null && reportCriteria.getToDate().length() > 0) {
						if(!DateCalenderUtil.isEmptyOrNullString(poRange) ||
								!DateCalenderUtil.isEmptyOrNullString(vinRange) || 
								!DateCalenderUtil.isEmptyOrNullString(dealerRange)){
							dateRange = " AND "; 
						}
						dateRange = dateRange + " rtl.DTE_RTL between '"+ reportCriteria.getFromDate() +"'  and '"+reportCriteria.getToDate()+"'";
					}
				}
				// static report criteria code finished.
			} else {
				if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() > 0) {
					dateRangeForAllDates = "where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH <= '"+ month +"' ";
					dateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH = '"+ month +"' ";
				} else if (reportCriteria.getMonth() < 0 && reportCriteria.getYear() > 0) {
					dateRangeForAllDates = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' " +
							" and dim.NUM_RETL_MTH <= (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
					dateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' " +
							" and dim.NUM_RETL_MTH = (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) ";
				} else if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() < 0) {
					dateRangeForAllDates = " where dim.NUM_RETL_MTH <= '"+ month +"' " +
							" and dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) ";
					dateRange = " where dim.NUM_RETL_MTH = '"+ month +"' " +
							" and dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) ";
				} 
				
				if (reportCriteria.getFromDate() != null && reportCriteria.getFromDate().length() > 0
						&& reportCriteria.getToDate() != null && reportCriteria.getToDate().length() > 0) {
					dateRange = "where dim.DTE_CAL between '"+ reportCriteria.getFromDate() +"'  and '"+reportCriteria.getToDate()+"'";
				} else if (reportCriteria.getReportDate() != null && reportCriteria.getReportDate().length() > 0){
					dateRange = " where dim.DTE_CAL = '"+reportCriteria.getReportDate()+"' ";
				}
				if (dateRange.length() == 0 && IConstants.SCHD_MONTH.equalsIgnoreCase(reportCriteria.getSchedule())) {
					dateRangeForAllDates = " where dim.NUM_RETL_YR = " +
							"(select dim1.NUM_RETL_YR from DPB_DAY dim1 where dim1.DTE_CAL =  current date ) " +
							"and dim.NUM_RETL_MTH <= (select dim1.NUM_RETL_MTH from DPB_DAY dim1 where dim1.DTE_CAL = current date )";
					dateRange = " where (dim.NUM_RETL_YR,dim.NUM_RETL_MTH) in " +
							"(select dim1.NUM_RETL_YR,dim1.NUM_RETL_MTH from DPB_DAY dim1 where dim1.DTE_CAL = current date ) ";
				}
				
				if(reportCriteria.getVehicleType() != null && reportCriteria.getVehicleType().size() > 0) {
					vehTypeRange = " and rtl.CDE_VEH_TYP in " +getVehTypes(reportCriteria.getVehicleType());
				} else {
					vehTypeRange = " and rtl.CDE_VEH_TYP in ('P','S','V','F')";
				}
				
				if(reportCriteria.getDealer() != null && reportCriteria.getDealer().length() > 0) {
					dealerRange = " and calc.ID_DLR in " +getDealers(reportCriteria.getDealer());
				}
				
				if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() > 0) {
					adjustmentDateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' and dim.NUM_RETL_MTH = '"+ adjustmentMonth +"' ";
				} else if (reportCriteria.getMonth() < 0 && reportCriteria.getYear() > 0) {
					adjustmentDateRange = " where dim.NUM_RETL_YR = '"+ reportCriteria.getYear() +"' " +
							" and dim.NUM_RETL_MTH = (select df.NUM_RETL_MTH from DPB_DAY df where df.DTE_CAL = current date) -1 ";
				} else if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() < 0) {
					adjustmentDateRange = " where dim.NUM_RETL_MTH = '"+ adjustmentMonth +"' " +
							" and dim.NUM_RETL_YR = (select df.NUM_RETL_YR from DPB_DAY df where df.DTE_CAL = current date) ";
				}
				
				if (adjustmentDateRange.length() == 0 && IConstants.SCHD_MONTH.equalsIgnoreCase(reportCriteria.getSchedule())) {
					adjustmentDateRange = " where (dim.NUM_RETL_YR,dim.NUM_RETL_MTH) in " +
							"(select dim1.NUM_RETL_YR,dim1.NUM_RETL_MTH-1 from DPB_DAY dim1 where dim1.DTE_CAL = current date ) ";
				}
				
				
			}
			
		}
		if(reportCriteria.getDealer() != null && reportCriteria.getDealer().length() > 0) {
			kpiDealerRange = " dlrkpi.ID_DLR in " +getDealers(reportCriteria.getDealer());
		}
		if (reportCriteria.getMonth() > 0 && reportCriteria.getYear() > 0) {
			kpiDateRange = 	" dlrkpi.DTE_FSCL_YR = '"+ reportCriteria.getYear() +"' and dlrkpi.DTE_FSCL_PERD = '"+ (int) Math.ceil(reportCriteria.getMonth() / 3.0)+"' ";
		}
	}
	
	
	/**
	 * This method will set the values for ubFromDate and ubToDate 
	 * for the given year and month/quarter.
	 * @param reportCriteria
	 */
	private void setRetailStartAndEndDates(NetStarReport reportCriteria) {
		final String methodName = "setRetailStartAndEndDates";
		LOGGER.enter(CLASSNAME, methodName);
		
		if (reportCriteria != null) {
			String fromYr = reportCriteria.getFromYear();
			String toYr = reportCriteria.getToYear();
			String fromMon = reportCriteria.getFromMonth();
			String toMon = reportCriteria.getToMonth();
			String fromQtr = reportCriteria.getFromQuarter();
			String toQtr = reportCriteria.getToQuarter();
			List<RetailDate> rtlDteList = null;
			
			if (Integer.parseInt(fromMon) > 0 && Integer.parseInt(toMon) > 0) {
				rtlDteList = reportDAO.getRetailDates(fromYr,toYr,fromMon,toMon,fromQtr,toQtr, true);
			} else if (Integer.parseInt(reportCriteria.getFromQuarter()) > 0  && Integer.parseInt(reportCriteria.getToQuarter()) > 0) {
				 rtlDteList = reportDAO.getRetailDates(fromYr,toYr,fromMon,toMon,fromQtr,toQtr, false);
			}
			
			if(rtlDteList != null ) {
				
				RetailDate rtlBeginDte = null;
				RetailDate rtlEndDte = null;
				// The size will be 2 when the year and month/qtr combination is different for From and To criteria.
				// The size will be 1 when the year and month/qtr combination is same for From and To criteria.
				if(rtlDteList.size()==2) {
					rtlBeginDte = rtlDteList.get(0);
					rtlEndDte = rtlDteList.get(1);
				
					if(rtlBeginDte != null)
						ubFromDate = rtlBeginDte.getRtlStartDate();
					if(rtlEndDte != null)
						ubToDate = rtlEndDte.getRtlEndDate();
				} else if(rtlDteList.size()==1) {
					rtlBeginDte = rtlDteList.get(0);
					
					if(rtlBeginDte != null) {
						ubFromDate = rtlBeginDte.getRtlStartDate();
						ubToDate = rtlBeginDte.getRtlEndDate();
					}
				}
			} else {
				LOGGER.error(methodName+": The retail dates are not available.");
			}
			LOGGER.info("fromYr:"+fromYr+"~toYr:"+toYr+"~fromMon:"+fromMon+"~toMon:"+toMon+"~fromQtr:"+fromQtr+"~toQtr:"+toQtr);
		}
		
		LOGGER.info("ubFromDate:" + ubFromDate + "ubToDate:" + ubToDate);
		LOGGER.exit(CLASSNAME, methodName);
	}
	
	
	//AMG-Start
	
	/*private String getPrograms(String program) {
		String programs = "(";
		String[] pgms = program.split(",");
		int count = 0;
		for(String pgm: pgms) {
			count++;
			programs = programs+"'"+pgm+"'";
			if(count < pgms.length) {
				programs = programs+",";
			}
		}
		programs = programs+")";
		return programs;
	}*/
	
	private String getPrograms(List<String> amgProgram) {
		String amgProgramOutput = "(";
		int count = 0;
		for(String pgm:amgProgram) {
			count++;
			amgProgramOutput = amgProgramOutput+"'"+pgm+"'";
			if(count < amgProgram.size()) {
				amgProgramOutput = amgProgramOutput+",";
			}
		}
		amgProgramOutput = amgProgramOutput+")";
		return amgProgramOutput;
	}
	//AMG-End
	
	private String getDealers(String dealer) {
		String dealers = "(";
		String[] dlrs = dealer.split(",");
		int count = 0;
		for(String dlr: dlrs) {
			count++;
			dealers = dealers+"'"+dlr+"'";
			if(count < dlrs.length) {
				dealers = dealers+",";
			}
		}
		dealers = dealers+")";
		return dealers;
	}
	
	

	private String getVehTypes(List<String> vehicleType) {
		String vehicles = "(";
		int count = 0;
		for(String veh:vehicleType) {
			count++;
			vehicles = vehicles+"'"+veh+"'";
			if(count < vehicleType.size()) {
				vehicles = vehicles+",";
			}
		}
		vehicles = vehicles+")";
		return vehicles;
	}

	@Override
	public ReportDefinitionBean retrieveReportId(int processId) {
		ReportDefinitionBean reportDefBean = null;
		reportDefBean = reportDAO.retrieveReportId(processId);
		return reportDefBean;
	}

	public List<VistaFileProcessing> getDlrReserveExceptionList(List<String>  vehicleType, String month ,String year, String dealerIds){
		List<VistaFileProcessing> rList = null;
		LOGGER.enter(CLASSNAME, "getVehicleExceptionList");
		rList = reportDAO.getDlrReserveExceptionList(vehicleType, month ,year, dealerIds);
		LOGGER.exit(CLASSNAME, "getBlckReport()");
		return rList;
	}
	public List<DleRsrvSumRptBean> getDlrRsrvSumReport(List<String> vehicles,int year,int month){
		final String methodName = "getVehicleExceptionList";
		LOGGER.enter(CLASSNAME, methodName);
		List<DleRsrvSumRptBean> rList = reportDAO.getDlrRsrvSumReport(vehicles,year,month);
		LOGGER.exit(CLASSNAME, methodName);
		return rList;
		
	}
	//Vehicle Details Report-start
	public List<List<List<String>>> reportsGenerateStatic (NetStarReport reportCriteria, Boolean isGenerateExcel) {
		NetStarReport netStartRpt = new NetStarReport();
		//Vehicle Details Report-end
		final String methodName = "reportsGenerateStatic";
		LOGGER.enter(CLASSNAME, methodName);
		List<List<List<String>>> reportData = null;
		List<String> queryNames = new ArrayList<String>();
		//Vehicle Details Report-start
		List<String> queryNames1 = new ArrayList<String>();
		//Vehicle Details Report-end7
		switch (reportCriteria.getRptId()) {
		case 1:
			//Courtesy Vehicle Report-start
			if(isGenerateExcel == false){
				
				if(reportCriteria.isFetchReportResultTotal()){
					
					queryNames.add(IConstants.CVP_PAYOUT_NET);
				}
				else {
					
					if(reportCriteria.isFetchReportResultCount() == true)
					{
						queryNames.add(IConstants.CVP_COUNT);
					}					
					queryNames.add(IConstants.CVP_PAYOUT_PAGINATION_ROWS);
				}
				
				break;
				
			}
			else{
				queryNames.add(IConstants.CVP_PAYOUT_ALL_ROWS_EXCEL);
				queryNames.add(IConstants.CVP_PAYOUT_NET);
				break;				
			}			
			//Courtesy Vehicle Report-end
		case 2:
			//MBDeal Report-start
			if(isGenerateExcel == false){
				
				if(reportCriteria.isFetchReportResultTotal()){
					
					queryNames.add(IConstants.MBDEAL_PAYOUT_NET);
				}
				else {
					
					if(reportCriteria.isFetchReportResultCount() == true)
					{
						queryNames.add(IConstants.MBDEAL_COUNT);
					}					
					queryNames.add(IConstants.MBDEAL_PAYOUT_PAGINATION_ROWS);
				}
				
				break;
				
			}
			else{
				queryNames.add(IConstants.MBDEAL_PAYOUT_ALL_ROWS_EXCEL);
				queryNames.add(IConstants.MBDEAL_PAYOUT_NET);
				break;				
			}
			
			//MBDeal Report-end			
		case 4:
			queryNames.add(IConstants.DLR_COMPL_SUM_REPORT_1);
			//queryNames.add(IConstants.DLR_COMPL_SUM_REPORT_2);
			//queryNames.add(IConstants.DLR_COMPL_SUM_REPORT_3);
			//queryNames.add(IConstants.DLR_COMPL_SUM_REPORT_4);
			break;
			//Dealer Compliance Summary Report - Start
		case IConstants.DLR_COMPL_SUM_REPORT_ID:
			queryNames.add(IConstants.DLR_COMPL_SUM_REPORT);
			break;
			//Dealer Compliance Summary Report - End
		case 5:
			queryNames.add(IConstants.DLR_PERF_UNERND_BNS_RPRT_DFT);
			queryNames.add(IConstants.DLR_PERF_UNERND_BNS_RPRT_DFT_1);
			break;
		/*case 6:
			queryNames.add(IConstants.DLR_RESERVE_EXCEPTION);
			queryNames.add(IConstants.DLR_RESERVE_EXCEPTION_1);
			break;*/
			
		case 6:
			//Exception Report-Start
			if(isGenerateExcel == false){
				
				if(reportCriteria.isFetchReportResultTotal()){
					
					queryNames.add(IConstants.EXCEPTION_PAYOUT_NET);
				}
				else {
					
					if(reportCriteria.isFetchReportResultCount() == true)
					{
						queryNames.add(IConstants.EXCEPTION_COUNT);
					}					
					queryNames.add(IConstants.EXCEPTION_PAGINATION_ROWS);
				}
				
				break;
				
			}
			else{
				queryNames.add(IConstants.EXCEPTION_ALL_ROWS_EXCEL);
				queryNames.add(IConstants.EXCEPTION_PAYOUT_NET);
				break;				
			}			
			//Exception Report-end	
		case 7:
			queryNames.add(IConstants.DEALER_RESERVE_SUMMARY_REPORT_1);
			queryNames.add(IConstants.DEALER_RESERVE_SUMMARY_REPORT_2);
			queryNames.add(IConstants.DEALER_RESERVE_SUMMARY_REPORT_3);
			break;		
		case 8:
			queryNames.add(IConstants.FLOOR_PLAN_SUMMARY_REPORT_1);
			queryNames.add(IConstants.FLOOR_PLAN_SUMMARY_REPORT_2);
			queryNames.add(IConstants.FLOOR_PLAN_SUMMARY_REPORT_3);
			break;
		case 10:
			queryNames.add(IConstants.VIN_Lookup_REPORT);
			break;
			//Vehicle Details Report-start
		case 11:
			if(isGenerateExcel == false){
				//execute total query only on last page
				if(reportCriteria.isFetchReportResultTotal())
				{
					queryNames.add(IConstants.VEHICLE_DETAILS_REPROT_QUERY_1);					
				}
				else
				{
					if(reportCriteria.isFetchReportResultCount() == true)
					{
						queryNames.add(IConstants.VEHICLE_DETAILS_REPROT_COUNT);
					}
					queryNames.add(IConstants.VEHICLE_DETAILS_REPROT_QUERY);	
				}
				break;
			}
			
			//export
			else {
				if(reportCriteria.getDataTypeRadio().equalsIgnoreCase("All")){
					queryNames.add(IConstants.VEHICLE_DETAILS_REPROT_QUERY);
					queryNames.add(IConstants.VEHICLE_DETAILS_REPROT_QUERY_1);
					queryNames.add(IConstants.VEHICLE_DETAILS_REPROT_QUERY_2);
					break;
				}
				else{
					queryNames.add(IConstants.VEHICLE_DETAILS_REPROT_QUERY);
					queryNames.add(IConstants.VEHICLE_DETAILS_REPROT_QUERY_1);
					break;
				}
			}
			
			//Vehicle Details Report-end
		// AMG -START	
		case 12:
			queryNames.add(IConstants.AMG_PERF_CENTER_RPT_NM);
			break;
			
		// AMG -End
		case 13:
			queryNames.add(IConstants.AMG_ELITE_CENTER_RPT_NM);
			break; 
		//Unearned Performance Bonus calculation start
		case 14:
			//Dealer Performance Unearned Bonus Report - FNC27 - Start
			//Pagination
			if(isGenerateExcel == false)
			{
				//execute total query only on last page
				if(reportCriteria.isFetchReportResultTotal())
				{
					queryNames.add(IConstants.UNEARNED_REPORT_TOTAL);					
				}
				else
				{
					if(reportCriteria.isFetchReportResultCount() == true)
					{
						queryNames.add(IConstants.UNEARNED_REPORT_COUNT);
					}
					queryNames.add(IConstants.UNEARNED_REPORT_PAGINATION);	
				}
			}
			//export
			else
			{
				queryNames.add(IConstants.UNEARNED_REPORT_EXCEL);
				break;
				
			}
			/*queryNames.add(IConstants.DLR_PERF_UNERND_BNS_RPRT_DFT_NEW);
			queryNames.add(IConstants.DLR_PERF_UNERND_BNS_RPRT_DFT_NEW_1);*/
			//Dealer Performance Unearned Bonus Report - FNC27 -End
			break;
			//Dealer Compliance Quarterly Payouts Report-Old Start
		case 15:
			queryNames.add(IConstants.DLR_COMP_QTR_PAYOUT_OLD1);
			queryNames.add(IConstants.DLR_COMP_QTR_PAYOUT_OLD3);
			queryNames.add(IConstants.DLR_COMP_QTR_PAYOUT_OLD2);
			break;	
			//Dealer Compliance Quarterly Payouts Report-Old End
		
		//Fixed Margin-Start	
		case 16:
			queryNames.add(IConstants.FIXED_MARGIN);
			//queryNames.add(IConstants.FIXED_MARGIN_PAYMENT);
			//queryNames.add(IConstants.FIXED_MARGIN_OWED);
			break;
		//Fixed Margin-End			
		}
		//Unearned Performance Bonus calculation end
		//Vehicle Details Report-start
		reportData = fetchReportData (queryNames, reportCriteria, isGenerateExcel);
		//Vehicle Details Report-end
		LOGGER.exit(CLASSNAME, methodName);
		return reportData;
	}
	
	/*VINLookup Report */
	public List<List<List<String>>> reportsGenerateStaticVinLookup (NetStarReport reportCriteria) {
		final String methodName = "reportsGenerateStaticVinLookup";
		LOGGER.enter(CLASSNAME, methodName);
		List<List<List<String>>> reportData = null;
		List<String> queryNames = new ArrayList<String>();
		
		//if(!reportCriteria.getPoNumber().isEmpty() || reportCriteria.getVehicleRange().isEmpty()){
			queryNames.add(IConstants.VIN_Lookup_REPORT);
		//}		
		reportData = fetchReportVinLookupData (queryNames, reportCriteria);
		LOGGER.exit(CLASSNAME, methodName);
		return reportData;
	}

	private List<List<List<String>>> fetchReportVinLookupData (List<String> queryNames, NetStarReport reportCriteria) {

		List<List<List<String>>> finalList = new ArrayList<List<List<String>>>();
		setSearchCriteria (reportCriteria, false);
		long startTime = System.currentTimeMillis();
		
		Map<String,String> dbQueryMap = null;
		String vinReport = null;
			String dbQuery = "";
				if(dbQueryMap == null) {
					dbQueryMap = new HashMap<String,String>();
				}
				if(!reportCriteria.getPoNumber().isEmpty())
				{
					vinReport ="Select distinct A.NUM_PO,A.NUM_VIN,A.DTE_RTL,A.ID_DLR,A.DES_MODL,sum(B.AMT_DPB_MAX_BNS_ELG),sum(B.AMT_DPB_BNS_CALC ) from DPB_UNBLK_VEH A LEFT  OUTER JOIN DPB_CALC B ON B.ID_DPB_Unblk_VEH=A.ID_DPB_Unblk_VEH where A.NUM_Po in" +getDealers(reportCriteria.getPoNumber())+"GROUP BY B.ID_DPB_Unblk_VEH, A.NUM_PO,A.NUM_VIN,A.DTE_RTL,A.ID_DLR,A.DES_MODL";
					vinReport=vinReport.trim();	
				
				}
				if(!reportCriteria.getVehicleRange().isEmpty())
				{
				
					vinReport = "Select distinct A.NUM_PO,A.NUM_VIN,A.DTE_RTL,A.ID_DLR,A.DES_MODL,sum(B.AMT_DPB_MAX_BNS_ELG),sum(B.AMT_DPB_BNS_CALC )from DPB_UNBLK_VEH A LEFT OUTER JOIN DPB_CALC B ON B.ID_DPB_Unblk_VEH=A.ID_DPB_Unblk_VEH where A.NUM_VIN in " +getDealers(reportCriteria.getVehicleRange())+"GROUP BY B.ID_DPB_Unblk_VEH, A.NUM_PO,A.NUM_VIN,A.DTE_RTL,A.ID_DLR,A.DES_MODL";
				}
				
				dbQueryMap.put("VIN_Lookup", vinReport );
				
		 
		if(dbQueryMap != null && dbQueryMap.size() > 0) {
			ExecutorService executor = Executors.newFixedThreadPool(dbQueryMap.size());
			MyThread[] runnableArray = new MyThread[dbQueryMap.size()];
			int counter = 0;
			for(String queryName: queryNames) {
				String dbQuery1 = replaceCriteria(dbQueryMap.get(queryName));				
				//reportData = reportDAO.getQueryData(dbQuery);
				runnableArray[counter] = new MyThread(reportDAO,dbQuery1);
				executor.submit(runnableArray[counter]);
				counter++;				
			}	
			executor.shutdown();
			 
			try {
				while(!executor.awaitTermination(1, TimeUnit.SECONDS));
				for(counter=0; counter < runnableArray.length;counter++) {
					finalList.add(runnableArray[counter].innerReportData);
					runnableArray[counter] = null;
				}
				runnableArray = null;
				executor = null;
			} catch(UnhandledException exception) {
				exception.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		long endTime = System.currentTimeMillis();
		
		long differenceTime = endTime - startTime;
		System.out.println("RequestTime:"+differenceTime+"/"+"EndTime:"+endTime+"starttime"+startTime);
		System.out.println("RequestTime:"+TimeUnit.MILLISECONDS.toSeconds(differenceTime)+" sec");
		for(int i=0; i<finalList.size(); i++){
			LOGGER.info("||-->>list values in ReportsManagerImpl"+finalList.size());
		}
		return finalList;
	}
	
	//Vehicle Details Report-start
	private List<List<List<String>>> fetchReportData (List<String> queryNames, NetStarReport reportCriteria, Boolean isGenerateExcel){
	//Vehicle Details Report-end
		List<List<List<String>>> finalList = new ArrayList<List<List<String>>>();
		List<String> dlr_compliance_qry_list = null;
		ArrayList<String> chunkDealer = null;
		setSearchCriteria (reportCriteria, false);
		long startTime = System.currentTimeMillis();
		
		
		Map<String,String> dbQueryMap = null;
		if(reportCriteria.getRptId() == 7) {
			dbQueryMap = reportDAO.fetchQuery(queryNames.subList(0, queryNames.size() < 3 ? queryNames.size():3));
		}
		//Fixed Margin-Start
		
		else if(reportCriteria.getRptId() == 16) {
			
			if(dbQueryMap == null)
			{
				dbQueryMap = new HashMap<String,String>();
			}
			
			dbQueryMap.put(IConstants.FIXED_MARGIN, ICommonQueryConstants.FIXED_MARGIN);
			//dbQueryMap.put(IConstants.FIXED_MARGIN_PAYMENT, ICommonQueryConstants.FIXED_MARGIN_PAYMENT);
			//dbQueryMap.put(IConstants.FIXED_MARGIN_OWED, ICommonQueryConstants.FIXED_MARGIN_OWED);
			
			
		}
		//Fixed Margin-End		
		
		else if(reportCriteria.getRptId() == 4) {
			for(String queryName: queryNames) {
				String dbQuery = "";
				dbQuery = reportDAO.fetchQuery(queryName);
				dbQuery = dbQuery + reportDAO.fetchQuery(IConstants.DLR_COMPL_SUM_REPORT_2);
				dbQuery = dbQuery + reportDAO.fetchQuery(IConstants.DLR_COMPL_SUM_REPORT_3);
				if(dbQueryMap == null) {
					dbQueryMap = new HashMap<String,String>();
				}
				dbQueryMap.put(queryName, dbQuery);
			}
		}
				//Dealer Compliance Summary Report - Start
				else if (reportCriteria.getRptId() == IConstants.DLR_COMPL_SUM_REPORT_ID) 
				{
					if (dbQueryMap == null) 
					{
						dbQueryMap = new HashMap<String, String>();
					}
					if (reportCriteria.getVehicleTypeRd().contains("P")) 
					{
						if(reportCriteria.isQtrStart() == true)
						{
							dbQueryMap.put(IConstants.DLR_COMPL_SUM_REPORT,
									ICommonQueryConstants.DLR_COMPL_SUM_REPORT_MONTHLY_CARS);
						}
						else
						{
							dbQueryMap.put(IConstants.DLR_COMPL_SUM_REPORT,
									ICommonQueryConstants.DLR_COMPL_SUM_REPORT_QTRLY_CARS);
						}
						
					}
					//Ratna Start
					else if (reportCriteria.getVehicleTypeRd().contains("F")) 
					{
							if(reportCriteria.isQtrStart() == true)
							{
								dbQueryMap.put(IConstants.DLR_COMPL_SUM_REPORT,
										ICommonQueryConstants.DLR_COMPL_SUM_REPORT_MONTHLY_FTL);
							}
							else
							{
								dbQueryMap.put(IConstants.DLR_COMPL_SUM_REPORT,
										ICommonQueryConstants.DLR_COMPL_SUM_REPORT_QTRLY_FTL);
							}
					}
					else if (reportCriteria.getVehicleTypeRd().contains("V")) 
					{
							if(reportCriteria.isQtrStart() == true)
							{
								dbQueryMap.put(IConstants.DLR_COMPL_SUM_REPORT,
										ICommonQueryConstants.DLR_COMPL_SUM_REPORT_MONTHLY_VANS);
							}
							else
							{
								dbQueryMap.put(IConstants.DLR_COMPL_SUM_REPORT,
										ICommonQueryConstants.DLR_COMPL_SUM_REPORT_QTRLY_VANS);
								System.out.println("Query: "+ ICommonQueryConstants.DLR_COMPL_SUM_REPORT_QTRLY_VANS);
							}
					}
					else if (reportCriteria.getVehicleTypeRd().contains("S")) 
					{
							if(reportCriteria.isQtrStart() == true)
							{
								dbQueryMap.put(IConstants.DLR_COMPL_SUM_REPORT,
										ICommonQueryConstants.DLR_COMPL_SUM_REPORT_MONTHLY_SMART);
							}
							else
							{
								dbQueryMap.put(IConstants.DLR_COMPL_SUM_REPORT,
										ICommonQueryConstants.DLR_COMPL_SUM_REPORT_QTRLY_SMART);
							}
					}
					//Ratna End

				}
				//Dealer Compliance Summary Report - End
		else if(reportCriteria.getRptId() == 10) {
			if(dbQueryMap == null) {
				dbQueryMap = new HashMap<String,String>();
			}
			StringBuffer VIN_Lookup_Report = new StringBuffer(IReportQueryConstants.VIN_Lookup_REPORT);
			StringBuffer inClauseVinOrPoNos = new StringBuffer();
			if(StringUtils.isNotBlank(reportCriteria.getVehicleRange())){
				String[] vinsInput = reportCriteria.getVehicleRange().split(",");
				for(int index=0; index < vinsInput.length; index++){
					inClauseVinOrPoNos.append("'").append(vinsInput[index]).append("'");
					if(vinsInput.length > 1 && index < vinsInput.length-1)
						inClauseVinOrPoNos.append(",");
				}
				VIN_Lookup_Report.append(" AND VEH.NUM_VIN IN ("+inClauseVinOrPoNos.toString()+") GROUP BY VEH.NUM_VIN, VEH.NUM_PO, CAL.ID_DLR WITH UR");
			}
			else if(StringUtils.isNotBlank(reportCriteria.getPoNumber())){
				String[] posNumberInput = reportCriteria.getPoNumber().split(",");
				for(int index=0; index < posNumberInput.length; index++){
					inClauseVinOrPoNos.append("'").append(posNumberInput[index]).append("'");
					if(posNumberInput.length > 1 && index < posNumberInput.length-1)
						inClauseVinOrPoNos.append(",");
				}
				VIN_Lookup_Report.append(" AND VEH.NUM_PO IN ("+inClauseVinOrPoNos.toString()+") GROUP BY VEH.NUM_VIN, VEH.NUM_PO, CAL.ID_DLR WITH UR");
			}
			dbQueryMap.put("VIN_Lookup", VIN_Lookup_Report.toString());
		}
	
		//Exception Report-start
		else if(reportCriteria.getRptId() ==6)
		{
			if(dbQueryMap == null)
			{
				dbQueryMap = new HashMap<String,String>();
			}					
			if(isGenerateExcel == false)
			{
				if(reportCriteria.isFetchReportResultTotal() == true)
				{
					dbQueryMap.put(IConstants.EXCEPTION_PAYOUT_NET, ICommonQueryConstants.EXCEPTION_PAYOUT_NET);
					
				}
				else {
					
					if(reportCriteria.isFetchReportResultCount() == true)
					{
						dbQueryMap.put(IConstants.EXCEPTION_COUNT, ICommonQueryConstants.EXCEPTION_PAGINATION_ROWS+ICommonQueryConstants.EXCEPTION_COUNT);
					}
						
					String paginationQuery = replacePaginationCriteria(ICommonQueryConstants.EXCEPTION_PAGINATION,reportCriteria.getStartIndex(),reportCriteria.getEndIndex());						
					dbQueryMap.put(IConstants.EXCEPTION_PAGINATION_ROWS, ICommonQueryConstants.EXCEPTION_PAGINATION_ROWS+paginationQuery);							
					
				}
				
			}
			else
			{					
				dbQueryMap.put(IConstants.EXCEPTION_ALL_ROWS_EXCEL, ICommonQueryConstants.EXCEPTION_ALL_ROWS_EXCEL);
				dbQueryMap.put(IConstants.EXCEPTION_PAYOUT_NET, ICommonQueryConstants.EXCEPTION_PAYOUT_NET);
										
			}
		}
		//Exception Report-end
		
		
		//Courtesy Vehcile Report-start
			else if(reportCriteria.getRptId() ==1)
			{
				if(dbQueryMap == null)
				{
					dbQueryMap = new HashMap<String,String>();
				}					
				if(isGenerateExcel == false)
				{
					if(reportCriteria.isFetchReportResultTotal() == true)
					{
						//dbQueryMap.put(IConstants.CVP_PAYOUT_ALL_ROWS, ICommonQueryConstants.CVP_PAYOUT_ALL_ROWS);
						dbQueryMap.put(IConstants.CVP_PAYOUT_NET, ICommonQueryConstants.CVP_PAYOUT_NET);
						
					}
					else {
						
						if(reportCriteria.isFetchReportResultCount() == true)
						{
							dbQueryMap.put(IConstants.CVP_COUNT, ICommonQueryConstants.CVP_PAYOUT_PAGINATION_ROWS+ICommonQueryConstants.CVP_COUNT);
						}
							
						String paginationQuery = replacePaginationCriteria(ICommonQueryConstants.CVP_PAGINATION,reportCriteria.getStartIndex(),reportCriteria.getEndIndex());						
						dbQueryMap.put(IConstants.CVP_PAYOUT_PAGINATION_ROWS, ICommonQueryConstants.CVP_PAYOUT_PAGINATION_ROWS+paginationQuery);							
						
					}
					
				}
				else
				{					
					dbQueryMap.put(IConstants.CVP_PAYOUT_ALL_ROWS_EXCEL, ICommonQueryConstants.CVP_PAYOUT_ALL_ROWS_EXCEL);
					dbQueryMap.put(IConstants.CVP_PAYOUT_NET, ICommonQueryConstants.CVP_PAYOUT_NET);
											
				}
			}
			//Courtesy Vehcile Report-end
		
		
		//MBDeal Report-start
		else if(reportCriteria.getRptId() ==2)
		{
			if(dbQueryMap == null)
			{
				dbQueryMap = new HashMap<String,String>();
			}					
			if(isGenerateExcel == false)
			{
				if(reportCriteria.isFetchReportResultTotal() == true)
				{					
					dbQueryMap.put(IConstants.MBDEAL_PAYOUT_NET, ICommonQueryConstants.MBDEAL_PAYOUT_NET);
					
				}
				else {
					
					if(reportCriteria.isFetchReportResultCount() == true)
					{
						dbQueryMap.put(IConstants.MBDEAL_COUNT, ICommonQueryConstants.MBDEAL_PAYOUT_PAGINATION_ROWS+ICommonQueryConstants.MBDEAL_COUNT);
					}
						
					String paginationQuery = replacePaginationCriteria(ICommonQueryConstants.MBDEAL_PAGINATION,reportCriteria.getStartIndex(),reportCriteria.getEndIndex());						
					dbQueryMap.put(IConstants.MBDEAL_PAYOUT_PAGINATION_ROWS, ICommonQueryConstants.MBDEAL_PAYOUT_PAGINATION_ROWS+paginationQuery);							
					
				}
				
			}
			else
			{					
				dbQueryMap.put(IConstants.MBDEAL_PAYOUT_ALL_ROWS_EXCEL, ICommonQueryConstants.MBDEAL_PAYOUT_ALL_ROWS_EXCEL);
				dbQueryMap.put(IConstants.MBDEAL_PAYOUT_NET, ICommonQueryConstants.MBDEAL_PAYOUT_NET);
										
			}
		}
		//MBDeal Report-end
		
		
		//Vehicle Details Report-start
		else if(reportCriteria.getRptId() ==11)
		{
			if(dbQueryMap == null)
			{
				dbQueryMap = new HashMap<String,String>();
			}
			String value = "";
			String value1 = "";
			String value2 = "";
			if(isGenerateExcel == false)
			{
				if(reportCriteria.getDataTypeRadio().equalsIgnoreCase("All"))
				{
					//execute total query only on last page
					if(reportCriteria.isFetchReportResultTotal() == true)
					{
						dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_QUERY_1, ICommonQueryConstants.VEHICLE_DETAILS_ALL_TOTAL);
					}
					else
					{
						if(reportCriteria.isFetchReportResultCount() == true)
						{
							dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_COUNT, ICommonQueryConstants.VEHICLE_DETAILS_ALL_COMMON+ICommonQueryConstants.VEHICLE_DETAILS_COUNT);
						}
						String paginationQuery = replacePaginationCriteria(ICommonQueryConstants.VEHICLE_DETAILS_PAGINATION,reportCriteria.getStartIndex(),reportCriteria.getEndIndex());
						dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_QUERY, ICommonQueryConstants.VEHICLE_DETAILS_ALL_COMMON+paginationQuery);
					}
				}
				else if (reportCriteria.getDataTypeRadio().equalsIgnoreCase("Blocked"))
				{
					//execute total query only on last page
					if(reportCriteria.isFetchReportResultTotal() == true)
					{
						dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_QUERY_1, ICommonQueryConstants.VEHICLE_DETAILS_BLOCKED_1);
					}
					else
					{
						if(reportCriteria.isFetchReportResultCount() == true)
						{
							dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_COUNT, ICommonQueryConstants.VEHICLE_DETAILS_BLOCKED_COMMON+ICommonQueryConstants.VEHICLE_DETAILS_COUNT);
						}
						String paginationQuery = replacePaginationCriteria(ICommonQueryConstants.VEHICLE_DETAILS_PAGINATION,reportCriteria.getStartIndex(),reportCriteria.getEndIndex());
						dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_QUERY, ICommonQueryConstants.VEHICLE_DETAILS_BLOCKED_COMMON+paginationQuery);
					}
				}
				else if (reportCriteria.getDataTypeRadio().equalsIgnoreCase("Unblocked"))
				{
					//execute total query only on last page
					if(reportCriteria.isFetchReportResultTotal() == true)
					{
						dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_QUERY_1, ICommonQueryConstants.VEHICLE_DETAILS_UNBLCKD_1);
					}
					else
					{
						if(reportCriteria.isFetchReportResultCount() == true)
						{
							dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_COUNT, ICommonQueryConstants.VEHICLE_DETAILS_UNBLOCKED_COMMON+ICommonQueryConstants.VEHICLE_DETAILS_COUNT);
						}
						String paginationQuery = replacePaginationCriteria(ICommonQueryConstants.VEHICLE_DETAILS_PAGINATION,reportCriteria.getStartIndex(),reportCriteria.getEndIndex());
						dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_QUERY, ICommonQueryConstants.VEHICLE_DETAILS_UNBLOCKED_COMMON+paginationQuery);
					}
				}
			}
			else
			{
				if(reportCriteria.getDataTypeRadio().equalsIgnoreCase("All"))
				{
					value = ICommonQueryConstants.VEHICLE_DETAILS_ALL;
					value1 = ICommonQueryConstants.VEHICLE_DETAILS_ALL_BLOCKED_1;
					value2 = ICommonQueryConstants.VEHICLE_DETAILS_ALL_UNBLOCKED_1;
					dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_QUERY, value);
					dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_QUERY_1, value1);
					dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_QUERY_2, value2);
				}
				else if (reportCriteria.getDataTypeRadio().equalsIgnoreCase("Unblocked"))
				{
					value= ICommonQueryConstants.VEHICLE_DETAILS_UNBLCKD;
					value1= ICommonQueryConstants.VEHICLE_DETAILS_UNBLCKD_1;
					dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_QUERY, value);
					dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_QUERY_1, value1);
				}
				else if (reportCriteria.getDataTypeRadio().equalsIgnoreCase("Blocked"))
				{
					value= ICommonQueryConstants.VEHICLE_DETAILS_BLOCKED;
					value1= ICommonQueryConstants.VEHICLE_DETAILS_BLOCKED_1;
					dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_QUERY, value);
					dbQueryMap.put(IConstants.VEHICLE_DETAILS_REPROT_QUERY_1, value1);
				}
			}
		}
		//Vehicle Details Report-end
		//Dealer Performance Unearned Bonus Report - FNC27 - Start
		else if(reportCriteria.getRptId() == 14)
		{
			if(dbQueryMap == null) 
			{
			dbQueryMap = new HashMap<String,String>();
			}
			//pagination
			String value = "";

			if(isGenerateExcel == false)
			{
				
				if(reportCriteria.getVehicleTypeRd().contains("F"))
				{
					
					//execute total query only on last page
					if(reportCriteria.isFetchReportResultTotal() == true)
					{
						dbQueryMap.put(IConstants.UNEARNED_REPORT_TOTAL, ICommonQueryConstants.UNEARNED_REPORT_FTL_TOTAL_QUERY);
					}
					else
					{
						if(reportCriteria.isFetchReportResultCount() == true)
						{
							dbQueryMap.put(IConstants.UNEARNED_REPORT_COUNT, ICommonQueryConstants.UNEARNED_REPORT_FTL_COMMON_QUERY+ICommonQueryConstants.UNEARNED_REPORT_COUNT_QUERY);
						}
						String paginationQuery = replacePaginationCriteria(ICommonQueryConstants.UNEARNED_REPORT_PAGINATION_QUERY,reportCriteria.getStartIndex(),reportCriteria.getEndIndex());
						dbQueryMap.put(IConstants.UNEARNED_REPORT_PAGINATION, ICommonQueryConstants.UNEARNED_REPORT_FTL_COMMON_QUERY+paginationQuery);
					}
				}
				if(reportCriteria.getVehicleTypeRd().contains("S"))
				{
					
					//execute total query only on last page
					if(reportCriteria.isFetchReportResultTotal() == true)
					{
						dbQueryMap.put(IConstants.UNEARNED_REPORT_TOTAL, ICommonQueryConstants.UNEARNED_REPORT_SMART_TOTAL_QUERY);
					}
					else
					{
						if(reportCriteria.isFetchReportResultCount() == true)
						{
							dbQueryMap.put(IConstants.UNEARNED_REPORT_COUNT, ICommonQueryConstants.UNEARNED_REPORT_SMART_COMMON_QUERY+ICommonQueryConstants.UNEARNED_REPORT_COUNT_QUERY);
						}
						String paginationQuery = replacePaginationCriteria(ICommonQueryConstants.UNEARNED_REPORT_PAGINATION_QUERY,reportCriteria.getStartIndex(),reportCriteria.getEndIndex());
						dbQueryMap.put(IConstants.UNEARNED_REPORT_PAGINATION, ICommonQueryConstants.UNEARNED_REPORT_SMART_COMMON_QUERY+paginationQuery);
					}
				}
				if(reportCriteria.getVehicleTypeRd().contains("V"))
				{
					//execute total query only on last page
					if(reportCriteria.isFetchReportResultTotal() == true)
					{
						dbQueryMap.put(IConstants.UNEARNED_REPORT_TOTAL, ICommonQueryConstants.UNEARNED_REPORT_VAN_TOTAL_QUERY);
					}
					else
					{
						if(reportCriteria.isFetchReportResultCount() == true)
						{
							dbQueryMap.put(IConstants.UNEARNED_REPORT_COUNT, ICommonQueryConstants.UNEARNED_REPORT_VAN_COMMON_QUERY+ICommonQueryConstants.UNEARNED_REPORT_COUNT_QUERY);
						}
						String paginationQuery = replacePaginationCriteria(ICommonQueryConstants.UNEARNED_REPORT_PAGINATION_QUERY,reportCriteria.getStartIndex(),reportCriteria.getEndIndex());
						dbQueryMap.put(IConstants.UNEARNED_REPORT_PAGINATION, ICommonQueryConstants.UNEARNED_REPORT_VAN_COMMON_QUERY+paginationQuery);
					}
				}
				if(reportCriteria.getVehicleTypeRd().contains("P"))
				{
					//execute total query only on last page
					if(reportCriteria.isFetchReportResultTotal() == true)
					{
						dbQueryMap.put(IConstants.UNEARNED_REPORT_TOTAL, ICommonQueryConstants.UNEARNED_REPORT_CARS_TOTAL_QUERY);
					}
					else
					{
						if(reportCriteria.isFetchReportResultCount() == true)
						{
							dbQueryMap.put(IConstants.UNEARNED_REPORT_COUNT, ICommonQueryConstants.UNEARNED_REPORT_CARS_COMMON_QUERY+ICommonQueryConstants.UNEARNED_REPORT_COUNT_QUERY);
						}
						String paginationQuery = replacePaginationCriteria(ICommonQueryConstants.UNEARNED_REPORT_PAGINATION_QUERY,reportCriteria.getStartIndex(),reportCriteria.getEndIndex());
						dbQueryMap.put(IConstants.UNEARNED_REPORT_PAGINATION, ICommonQueryConstants.UNEARNED_REPORT_CARS_COMMON_QUERY+paginationQuery);
					}
				}
				
			}
			//export
			else
			{
						if(reportCriteria.getVehicleTypeRd().contains("F"))
						{
							value = ICommonQueryConstants.UNEARNED_REPORT_FTL_EXPORT_QUERY;
						}
						else if(reportCriteria.getVehicleTypeRd().contains("P")){
							value = ICommonQueryConstants.UNEARNED_REPORT_CARS_EXPORT_QUERY;
						}
						else if(reportCriteria.getVehicleTypeRd().contains("V")){
							value = ICommonQueryConstants.UNEARNED_REPORT_VANS_EXPORT_QUERY;
						}
						else if(reportCriteria.getVehicleTypeRd().contains("S")){
							value = ICommonQueryConstants.UNEARNED_REPORT_SMART_EXPORT_QUERY;
						}
						dbQueryMap.put(IConstants.UNEARNED_REPORT_EXCEL, value);
				
			}
			
		}
		else if(reportCriteria.getRptId() == 3){
			dlr_compliance_qry_list = new ArrayList<String>();
			if(reportCriteria.getDealer() != null && reportCriteria.getDealer().length() > 0) {
				ArrayList<String> dealerlst=new ArrayList<String>();
				dealerlst.add(reportCriteria.getDealer());
				setDealerList(dealerlst);
			}else {
				setDealerList(reportDAO.getDealerData(reportCriteria.getVehicleTypeRd().toString().replaceAll("[\\[\\]]", "")));
			}
			
			chunkDealer =  getChunkOfDealer();
			System.out.println("Chunk list Size : " +chunkDealer.size());
			
			if(reportCriteria.getVehicleTypeRd().contains("P"))
			{
				for(int i=0; i< chunkDealer.size(); i++)
				{
					dlr_compliance_qry_list.add(ICommonQueryConstants.DEALER_COMPLIANCE_QUERY_CAR);
				}
			}
			if(reportCriteria.getVehicleTypeRd().contains("F"))
			{
				for(int i=0; i< chunkDealer.size(); i++)
				{
					dlr_compliance_qry_list.add(ICommonQueryConstants.DEALER_COMPLIANCE_QUERY_FTL);
				}
			}
			if(reportCriteria.getVehicleTypeRd().contains("V"))
			{
				for(int i=0; i< chunkDealer.size(); i++)
				{
					dlr_compliance_qry_list.add(ICommonQueryConstants.DEALER_COMPLIANCE_QUERY_VAN);
				}
			}
			if(reportCriteria.getVehicleTypeRd().contains("S"))
			{
				for(int i=0; i< chunkDealer.size(); i++)
				{
					dlr_compliance_qry_list.add(ICommonQueryConstants.DEALER_COMPLIANCE_QUERY_SMART);
				}
			}
		}
		//Dealer Performance Unearned Bonus Report - FNC27 - End
		
		//AMG-Start
		else if(reportCriteria.getRptId() == 12){
			
			if(dbQueryMap == null) 
			{
				dbQueryMap = new HashMap<String,String>();
			}
			
			dbQueryMap.put(IConstants.AMG_PERF_CENTER_RPT_NM, ICommonQueryConstants.AMG_PERF_CENTER_RPT_NM);
		}		
		//AMG-End
		else {
			if(reportCriteria.getRptId() != 3)
			{
				dbQueryMap = reportDAO.fetchQuery(queryNames);
			}
		}
		 
		if(dbQueryMap != null && dbQueryMap.size() > 0) {
			ExecutorService executor = Executors.newFixedThreadPool(dbQueryMap.size());
			MyThread[] runnableArray = new MyThread[dbQueryMap.size()];
			int counter = 0;
			for(String queryName: queryNames) {
				String dbQuery = replaceCriteria(dbQueryMap.get(queryName));				
				//reportData = reportDAO.getQueryData(dbQuery);
				System.out.println(dbQuery);
				runnableArray[counter] = new MyThread(reportDAO,dbQuery);
				executor.submit(runnableArray[counter]);
				counter++;				
			}	
			executor.shutdown();
			 
			try {
				while(!executor.awaitTermination(1, TimeUnit.SECONDS));
				for(counter=0; counter < runnableArray.length;counter++) {
					finalList.add(runnableArray[counter].innerReportData);
					runnableArray[counter] = null;
				}
				runnableArray = null;
				executor = null;
			} catch(UnhandledException exception) {
				exception.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		/*DCQPR Start- Performance Improvement 10/06/2016*/
		else if(dlr_compliance_qry_list != null && !dlr_compliance_qry_list.isEmpty()){
				//Processing Started..
				ReportCache.dlrCompReportProcessing = true;
				ExecutorService executor = null;
				QueryThread[] runnableChunkQueryArray = null;
				int threadPoolSize =8;
				if(dlr_compliance_qry_list.size() < threadPoolSize)
					threadPoolSize = dlr_compliance_qry_list.size();
				executor = Executors.newFixedThreadPool(threadPoolSize);
				runnableChunkQueryArray = new QueryThread[dlr_compliance_qry_list.size()];
				int runnableChunkCounter = 0, dcqprCounter = 0;
				try {
					for (String queryName : dlr_compliance_qry_list) {
						String oldPayoutDealerRange = getDealers(chunkDealer.get(dcqprCounter++).toString());
						String dbQuery = replaceCriteriaForDCQPR(queryName,oldPayoutDealerRange);
						runnableChunkQueryArray[runnableChunkCounter] = new QueryThread(reportDAO, dbQuery);
						executor.submit(runnableChunkQueryArray[runnableChunkCounter++]);
					}
					executor.shutdown();
					while (!executor.awaitTermination(1, TimeUnit.SECONDS));
					for (int counter = 0; counter < runnableChunkQueryArray.length; counter++) {
						if (runnableChunkQueryArray[counter].innerReportData != null 
								&& !runnableChunkQueryArray[counter].innerReportData.isEmpty())
							finalList.add(runnableChunkQueryArray[counter].innerReportData);
						runnableChunkQueryArray[counter] = null;
					}
				} catch (UnhandledException exception) {
					exception.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					//Processing done
					ReportCache.dlrCompReportProcessing = false;
					runnableChunkQueryArray = null;
					executor = null;
				}
		}
		/*DCQPR END- Performance Improvement 10/06/2016*/
		long endTime = System.currentTimeMillis();
		
		long differenceTime = endTime - startTime;
		if(reportCriteria != null){
			if(reportCriteria.getRptId() == 11){
				System.out.println("RequestTime for Vehicle Details Report:"+differenceTime+"/"+"EndTime:"+endTime+"starttime"+startTime);
				System.out.println("RequestTime for Vehicle Details Report:"+TimeUnit.MILLISECONDS.toSeconds(differenceTime)+" sec");
			}
			else if(reportCriteria.getRptId() == 3){
				System.out.println("RequestTime for Dealer Compliance Quarterly Payout Report:"+differenceTime+"/"+"EndTime:"+endTime+"starttime"+startTime);
				System.out.println("RequestTime for Dealer Compliance Quarterly Payout Report:"+TimeUnit.MILLISECONDS.toSeconds(differenceTime)+" sec");
			}
			else if(reportCriteria.getRptId() == 14){
				System.out.println("RequestTime for Dealer Performance Unearned Bonus Report:"+differenceTime+"/"+"EndTime:"+endTime+"starttime"+startTime);
				System.out.println("RequestTime for Dealer Performance Unearned Bonus Report:"+TimeUnit.MILLISECONDS.toSeconds(differenceTime)+" sec");
			}
			else if(reportCriteria.getRptId() == 5){
				System.out.println("RequestTime for Dealer Performance Unearned Bonus Report Old:"+differenceTime+"/"+"EndTime:"+endTime+"starttime"+startTime);
				System.out.println("RequestTime for Dealer Performance Unearned Bonus Report Old:"+TimeUnit.MILLISECONDS.toSeconds(differenceTime)+" sec");
			}
			else{
				System.out.println("RequestTime:"+differenceTime+"/"+"EndTime:"+endTime+"starttime"+startTime);
				System.out.println("RequestTime:"+TimeUnit.MILLISECONDS.toSeconds(differenceTime)+" sec");
			}
		}
		for(int i=0; i<finalList.size(); i++){
			LOGGER.info("||-->>list values in ReportsManagerImpl"+finalList.size());
		}
		return finalList;
	}
	
	/*DCQPR Start - - Performance Improvement 10/06/2016*/
	/**
	 * @return dealerChunkList  List of dealer as per chunck size
	 */
	private ArrayList<String> getChunkOfDealer(){
		ArrayList<String> dealerChunkList = new ArrayList<String>();
		 int inc = 0;
		 double loopCount = 40.00;
			double noOFChunck = Math.ceil((double)dealerList.size()/loopCount);
			//System.out.println("noOFChunck : "+noOFChunck );
		 for(int i=1;i<=noOFChunck;i++){
			 String s=""; int count =0; 
			 int j = 0;
			// System.out.println("Incremnt : "+ inc);
			 for ( j =inc; j < dealerList.size(); j++) {
				 s = s + dealerList.get(j)+","; count++; inc++;
				 if(count == 40 || inc==dealerList.size()){
					 dealerChunkList.add(s);
					 break;
				 }
			}
		 }
		 
/*		 for (int i = 0; i < dealerChunkList.size(); i++) {
				//getDealers(dealerList.toString().replace('[', ' ').replace(']', ' ').replaceAll("\\s+","") );
				System.out.println(dealerChunkList.get(i).toString());
				
			}*/
		 return dealerChunkList;
	}
	/*DCQPR End - Performance Improvement 10/06/2016*/
	
	//Vehicle Details Report-start
	private String replacePaginationCriteria(
			String dlrPerfUnerndBnsRprtPaginationNewQuery, long startIndex,
			long endIndex) {
		String paginationQuery = dlrPerfUnerndBnsRprtPaginationNewQuery;
		paginationQuery =	paginationQuery.replaceAll(IConstants.PAGE_START_INDEX, startIndex+"");
		paginationQuery = paginationQuery.replaceAll(IConstants.PAGE_END_INDEX, endIndex+"");
		return  paginationQuery;
	}
	//Vehicle Details Report-end

	class MyThread implements Runnable {
		 IReportsDAO innerReportDAO = null;
		 String innerDBQuery = null;
		 List<List<String>> innerReportData = null;
		MyThread(IReportsDAO _reportDAO,String _dbQuery) {
			innerReportDAO = _reportDAO;
			innerDBQuery = _dbQuery;
		}
		@Override
		public void run() {
			innerReportData = innerReportDAO.getQueryData(innerDBQuery);
		}
		
		public List<List<String>> getInnerReportData() {
			return innerReportData;
		}
		
	}
	
	//Added By Basanta
		class QueryThread implements Runnable  {
			IReportsDAO innerReportDAO = null;
			 String innerDBQuery = null;
			 List<List<String>> innerReportData = null;
			 QueryThread(IReportsDAO _reportDAO,String _dbQuery) {
				innerReportDAO = _reportDAO;
				innerDBQuery = _dbQuery;
			}
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() +" Running ..");
				innerReportData = innerReportDAO.getQueryData(innerDBQuery);
			}
			
			public List<List<String>> getInnerReportData() {
				return innerReportData;
			}
			
			/*public int getSize() {
				System.out.println("In Thread >> innerReportData .Size : "+innerReportData.size());
				return innerReportData.size();
			}*/
			
		}
	
	public StringBuffer reportsGenerate1(LeadershipReport reportCriteria, boolean isFromScheduler) {
		final String methodName = "reportsGenerate1";
		LOGGER.enter(CLASSNAME, methodName);
		
		int rptLPP = 65;
		List rptCntList = null;
		rptCntList = reportDAO.reportsGenerate(7);
		StringBuffer report = new StringBuffer("");
		
		boolean results = false;
		if(!isFromScheduler) {
			rptLPP = rptLPP - 5;
		} else {
			rptLPP = rptLPP - 5;
		}
		setSearchCriteria1 (reportCriteria, isFromScheduler);
		
		report = generateYearlyReport(rptCntList, rptLPP, reportCriteria, isFromScheduler);
		return report;
	}
	
	
	private void setSearchCriteria1(LeadershipReport reportCriteria,boolean isFromScheduler) {
		
		vehType1 = "";
		vehType2 = "";
		dateRange1 = "";
		qtrRange1 = "";
		qtrRange2 = "";
		qtrRange3 = "";
		header = "";
		
		if(reportCriteria.getVehicleType() != null ){ 
			if (reportCriteria.getVehicleType().equalsIgnoreCase("Car")) {
				vehType1   =  " and calc.CDE_VEH_TYP in ('P') and (calc.CDE_VEH_DDR_USE = 'L4' and DTE_RTL >'2014-06-30' or calc.CDE_VEH_DDR_USE <> 'L4') ";
				vehType2   =  "'MB'" ;
				qtrRange1  =  " DTE_RTL between '2014-04-01' and '2014-06-30' "	;
				qtrRange2  =  " DTE_RTL between '2014-07-01' and '2014-09-30' "	;
				qtrRange3  =  " DTE_RTL between '2014-10-01' and '2015-01-05' "	;
				header     =  "('LDRCARS')" ;
				dateRange1 =  " DTE_RTL between '2014-04-01' and '2015-03-31' " ;
			}
			else if (reportCriteria.getVehicleType().equalsIgnoreCase("Van")){
				vehType1   =  " and calc.CDE_VEH_TYP in ('V') " ;
				vehType2   =  "'SP'" ;
				qtrRange1  =  " DTE_RTL between '2014-01-03' and '2014-03-31' "	;
				qtrRange2  =  " DTE_RTL between '2014-04-01' and '2014-06-30' "	;
				qtrRange3  =  " DTE_RTL between '2014-07-01' and '2014-09-30' "	;
				header     =  "('LDRMVANS')" ;
				dateRange1 =  " DTE_RTL between '2014-01-03' and '2015-01-05' " ;
			}
			
			else if (reportCriteria.getVehicleType().equalsIgnoreCase("Freightliner")){
				vehType1   =  " and calc.CDE_VEH_TYP in ('F') ";
				vehType2   =  "'SP'" ;
				qtrRange1  =  " DTE_RTL between '2014-01-03' and '2014-03-31' "	;
				qtrRange2  =  " DTE_RTL between '2014-04-01' and '2014-06-30' "	;
				qtrRange3  =  " DTE_RTL between '2014-07-01' and '2014-09-30' "	;
				header     =  "('LDRDVANS')" ;
				dateRange1 =  " DTE_RTL between '2014-01-03' and '2015-01-05' " ;
			}
			else
			{
				vehType1   =  " and calc.CDE_VEH_TYP in ('S') " ;
				vehType2   =  "'SM'" ;
				qtrRange1  =  " DTE_RTL between '2014-01-03' and '2014-03-31' "	;
				qtrRange2  =  " DTE_RTL between '2014-04-01' and '2014-06-30' "	;
				qtrRange3  =  " DTE_RTL between '2014-07-01' and '2014-09-30' "	;
				header     =  "('LDRSMT')" ;
				dateRange1 =  " DTE_RTL between '2014-01-03' and '2015-01-05' " ;
			}
		}				
		
		if(reportCriteria.getDealer() != null && reportCriteria.getDealer().length() > 0) {
			dealerRange = " and calc.ID_DLR in " +getDealers(reportCriteria.getDealer());
		}
	
	}
	
}
