package com.mbusa.dpb.web.Reports.action;

import java.sql.Date;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.BlockedVehicle;
import com.mbusa.dpb.common.domain.DealerBonusVehReport;
import com.mbusa.dpb.common.domain.DealerVehicleReport;
import com.mbusa.dpb.common.domain.NetStarReport;
import com.mbusa.dpb.common.domain.ReportDefinitionBean;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DPBCommonHelper;
import com.mbusa.dpb.common.util.ReportCache;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.delegate.ReportDelegate;
import com.mbusa.dpb.web.delegate.DashBoardDelegate;
import com.mbusa.dpb.web.form.CVPForm;
import com.mbusa.dpb.web.form.DealerComplianceReportFormForCars;
import com.mbusa.dpb.web.form.DealerComplianceReportFormForFreightliner;
import com.mbusa.dpb.web.form.DealerComplianceReportFormForSmart;
import com.mbusa.dpb.web.form.DlrCompSummForm;
import com.mbusa.dpb.web.form.ExceptionReportForm;
import com.mbusa.dpb.web.form.MBDealForm;
import com.mbusa.dpb.web.form.ReportDefinitionForm;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.web.form.UnearnedForm;
import com.mbusa.dpb.web.form.VehDetailsForm;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class ReportsAction extends DPBAction {
	
	HttpSession session = null;
	
	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = ReportsAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(ReportsAction.class);
	private String actionForward = "errorPage";
	private NetStarReport netStartRpt = new NetStarReport();
	private  ReportDefinitionBean rptDef;
	ReportDefinitionForm reportDefForm;
	public BusinessDelegate bDelegate  = new BusinessDelegate();
	public ReportDelegate rDelegate  = new ReportDelegate();
	List<ReportDefinitionBean> beanList = new ArrayList<ReportDefinitionBean>();
	List<ReportDefinitionForm> formList = new ArrayList<ReportDefinitionForm>();
	private List <VehicleType>vehicleList = new ArrayList<VehicleType>(); 
	private List <VehicleType>vehicleListRd = new ArrayList<VehicleType>();
	private List <VehicleType>vehListRd = new ArrayList<VehicleType>();
	private List<DealerComplianceReportFormForFreightliner> dealerComplianceList = new ArrayList<DealerComplianceReportFormForFreightliner>();
	private List<DealerComplianceReportFormForSmart> dealerComplianceListForSmart = new ArrayList<DealerComplianceReportFormForSmart>();
	private List<DealerComplianceReportFormForCars> dealerComplianceListForCar = new ArrayList<DealerComplianceReportFormForCars>();
	List<List<List<String>>> list = null;
	private boolean draftrptFlag = false;
	private boolean draftrptSmartFlag = false;
	//Vehicle Details Report-start
	private Boolean isGenerateExcel = false;
	private Boolean isLastPage = false;
	BigDecimal amtMsrpBase =new BigDecimal(0);
	BigDecimal amtMsrpTotaCsry =new BigDecimal(0);
	BigDecimal amtDlrRbt =new BigDecimal(0);
	BigDecimal amtMsrp =new BigDecimal(0);
	boolean custExpflag = true;
	
	//Vehicle Details Report-end
	
	public boolean isCustExpflag() {
		return custExpflag;
	}

	public void setCustExpflag(boolean custExpflag) {
		this.custExpflag = custExpflag;
	}



	private String vehicleId;
	private boolean showResultMsg = true ;
    private boolean reportGenerated = true;
    private boolean reportGenerationInProcess;
	private List<BlockedVehicle> blockedList = new ArrayList<BlockedVehicle>();
	private List<BlockedVehicle> blkList = new ArrayList<BlockedVehicle>();
	
	private List<DealerVehicleReport> vehicleTypeList = new ArrayList<DealerVehicleReport>();	 
	private List <DealerBonusVehReport> vehicleCond = new ArrayList <DealerBonusVehReport>();
	private List <DealerBonusVehReport> totVehList = new ArrayList <DealerBonusVehReport>();
	
	//File f = new File("C:/Users/RRAMABA/data.xls");   //local
	File f = new File("/usr/appdata/share/sapfin-cofico/dpb/files/data.xls");  //Dev
	//File f = new File("/usr/appdata/share/COFICO/DPB/files/data.xls");  //QA or Prod
	//File f = new File("C:/data.xls"); 
	
	//FootNote Date - Start
		DashBoardDelegate dashBrdDelegate  = new DashBoardDelegate();
		List<RtlCalenderDefinition> listRtlCal = new ArrayList<RtlCalenderDefinition>();
		String enteredYear;
		Date dteRetlMthBeg;
		
		public List<RtlCalenderDefinition> getListRtlCal() {
			return listRtlCal;
		}

		public void setListRtlCal(List<RtlCalenderDefinition> listRtlCal) {
			this.listRtlCal = listRtlCal;
		}

		public String getEnteredYear() {
			return enteredYear;
		}

		public void setEnteredYear(String enteredYear) {
			this.enteredYear = enteredYear;
		}

		public Date getDteRetlMthBeg() {
			return dteRetlMthBeg;
		}

		public void setDteRetlMthBeg(Date dteRetlMthBeg) {
			this.dteRetlMthBeg = dteRetlMthBeg;
		}
		
		
		
		//FootNote Date - End
	
	//Dealer Compliance Quarterly Payouts Report - Start
	private String qtr;
	private String yr;
	private String dsName;
	private String custExpSalesName;
	 
	NumberFormat format = NumberFormat.getCurrencyInstance();
	public String getCustExpSalesName() {
		return custExpSalesName;
	}

	public void setCustExpSalesName(String custExpSalesName) {
		this.custExpSalesName = custExpSalesName;
	}

	public String getCustExpServiceName() {
		return custExpServiceName;
	}

	public void setCustExpServiceName(String custExpServiceName) {
		this.custExpServiceName = custExpServiceName;
	}

	private String custExpServiceName;
	private String poName;
	private String seName;
	private String bsName;
	private String preOwnAvg;
	private String custExpSales;
	public String getCustExpSales() {
		return custExpSales;
	}

	public void setCustExpSales(String custExpSales) {
		this.custExpSales = custExpSales;
	}

	public String getCustExpService() {
		return custExpService;
	}

	public void setCustExpService(String custExpService) {
		this.custExpService = custExpService;
	}

	private String custExpService;
	private String salesEffAvg;
	private String mbcsiAvg;
	private String msrp;
	private String sumVarAvg;
	private String varTotal;
	private String dlrStandAvg;
	private String perc1;
	private String perc2;
	private String perc3;
	private String perc4;
	private String cy13Total = "0";
	
	private String dsTotal  = "0";
	private String preTotal= "0";
	private String slsTotal  = "0";
	private String mbTotal  = "0";
	private String perc5;
	private String perc6;
	private String custExpSalesTotal= "0";
	private String custExpServiceTotal= "0";
	private String unEarnedTotal  = "0";
	private String totalPayout  = "0";
	private String mbCSINewAvg;
	private String mbCSINewTotal="0";
	private String mbCSINewName;
	//Vehicle Details Report-start
	private List<Integer> pageSizeList =  Arrays.asList(IConstants.PAGE_SIZE_LIST);
	//From year To year persist issue - start
	private LinkedHashMap<String,String> yearList = new LinkedHashMap<String,String>();
	
	
	public LinkedHashMap<String, String> getYearList() {
		
		int tYear = Calendar.getInstance().get(Calendar.YEAR);
        int j=0;
        yearList.put("-1", "Select");
        while (j<7)
        {
        	String key = tYear-j+"";
        	String value = tYear-j+"";
        	yearList.put(key, value);
        j++; 
        }
		return yearList;
	}

	public void setYearList(LinkedHashMap<String, String> yearList) {
		this.yearList = yearList;
	}

	//From year To year persist issue - end
	public List<Integer> getPageSizeList() {
		return pageSizeList;
	}
	
	public void setPageSizeList(List<Integer> pageSizeList) {
		this.pageSizeList = pageSizeList;
	}
	//Vehicle Details Report-end
	public String getPerc4() {
		return perc4;
	}

	public void setPerc4(String perc4) {
		this.perc4 = perc4;
	}

	/**
	 * @return the qtr
	 */
	public String getQtr() {
		return qtr;
	}

	/**
	 * @param qtr the qtr to set
	 */
	public void setQtr(String qtr) {
		this.qtr = qtr;
	}
	/**
	 * @return the yr
	 */
	public String getYr() {
		return yr;
	}

	/**
	 * @param yr the yr to set
	 */
	public void setYr(String yr) {
		this.yr = yr;
	}
	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}
	public String getPoName() {
		return poName;
	}
	public void setPoName(String poName) {
		this.poName = poName;
	}
	public String getSeName() {
		return seName;
	}

	public void setSeName(String seName) {
		this.seName = seName;
	}

	public String getBsName() {
		return bsName;
	}

	public void setBsName(String bsName) {
		this.bsName = bsName;
	}
	public String getPreOwnAvg() {
		return preOwnAvg;
	}

	public void setPreOwnAvg(String preOwnAvg) {
		this.preOwnAvg = preOwnAvg;
	}

	public String getSalesEffAvg() {
		return salesEffAvg;
	}

	public void setSalesEffAvg(String salesEffAvg) {
		this.salesEffAvg = salesEffAvg;
	}

	public String getMbcsiAvg() {
		return mbcsiAvg;
	}

	public void setMbcsiAvg(String mbcsiAvg) {
		this.mbcsiAvg = mbcsiAvg;
	}
	public String getMsrp() {
		return msrp;
	}

	public void setMsrp(String msrp) {
		this.msrp = msrp;
	}

	public String getSumVarAvg() {
		return sumVarAvg;
	}

	public void setSumVarAvg(String sumVarAvg) {
		this.sumVarAvg = sumVarAvg;
	}
	public String getVarTotal() {
		return varTotal;
	}

	public void setVarTotal(String varTotal) {
		this.varTotal = varTotal;
	}
	public String getDlrStandAvg() {
		return dlrStandAvg;
	}

	public void setDlrStandAvg(String dlrStandAvg) {
		this.dlrStandAvg = dlrStandAvg;
	}
	public String getPerc1() {
		return perc1;
	}

	public void setPerc1(String perc1) {
		this.perc1 = perc1;
	}

	public String getPerc2() {
		return perc2;
	}

	public void setPerc2(String perc2) {
		this.perc2 = perc2;
	}

	public String getPerc3() {
		return perc3;
	}

	public void setPerc3(String perc3) {
		this.perc3 = perc3;
	}
	
	public String getCy13Total() {
		return cy13Total;
	}

	public void setCy13Total(String cy13Total) {
		this.cy13Total = cy13Total;
	}

	public String getDsTotal() {
		return dsTotal;
	}

	public void setDsTotal(String dsTotal) {
		this.dsTotal = dsTotal;
	}

	public String getPreTotal() {
		return preTotal;
	}

	public void setPreTotal(String preTotal) {
		this.preTotal = preTotal;
	}

	public String getSlsTotal() {
		return slsTotal;
	}

	public void setSlsTotal(String slsTotal) {
		this.slsTotal = slsTotal;
	}

	public String getMbTotal() {
		return mbTotal;
	}

	public void setMbTotal(String mbTotal) {
		this.mbTotal = mbTotal;
	}
	public String getPerc5() {
		return perc5;
	}

	public void setPerc5(String perc5) {
		this.perc5 = perc5;
	}

	public String getPerc6() {
		return perc6;
	}

	public void setPerc6(String perc6) {
		this.perc6 = perc6;
	}
	public String getCustExpSalesTotal() {
		return custExpSalesTotal;
	}

	public void setCustExpSalesTotal(String custExpSalesTotal) {
		this.custExpSalesTotal = custExpSalesTotal;
	}

	public String getCustExpServiceTotal() {
		return custExpServiceTotal;
	}

	public void setCustExpServiceTotal(String custExpServiceTotal) {
		this.custExpServiceTotal = custExpServiceTotal;
	}
	public String getUnEarnedTotal() {
		return unEarnedTotal;
	}

	public void setUnEarnedTotal(String unEarnedTotal) {
		this.unEarnedTotal = unEarnedTotal;
	}
	public String getTotalPayout() {
		return totalPayout;
	}

	public void setTotalPayout(String totalPayout) {
		this.totalPayout = totalPayout;
	}
	public String getMbCSINewAvg() {
		return mbCSINewAvg;
	}

	public void setMbCSINewAvg(String mbCSINewAvg) {
		this.mbCSINewAvg = mbCSINewAvg;
	}

	public String getMbCSINewTotal() {
		return mbCSINewTotal;
	}

	public void setMbCSINewTotal(String mbCSINewTotal) {
		this.mbCSINewTotal = mbCSINewTotal;
	}
	public String getMbCSINewName() {
		return mbCSINewName;
	}

	public void setMbCSINewName(String mbCSINewName) {
		this.mbCSINewName = mbCSINewName;
	}
	//Dealer Compliance Quarterly Payouts Report - End
	
	// Courtesy Vehicle Report start
	private List<CVPForm> cvpList = new ArrayList<CVPForm>();
		

	public List<CVPForm> getCvpList() {
		return cvpList;
	}

	public void setCvpList(List<CVPForm> cvpList) {
		this.cvpList = cvpList;
	}
	
	// Courtesy Vehicle Report end
	
	// Exception Report-Start
	private List<ExceptionReportForm> expRptList = new ArrayList<ExceptionReportForm>();
		

	public List<ExceptionReportForm> getExpRptList() {
			return expRptList;
	}

	public void setExpRptList(List<ExceptionReportForm> expRptList) {
		this.expRptList = expRptList;
	}		
		
	// Exception Report-end

	// MBDeal Report start
	private List<MBDealForm> mbDealList = new ArrayList<MBDealForm>();
	

	public List<MBDealForm> getMbDealList() {
			return mbDealList;
	}

	public void setMbDealList(List<MBDealForm> mbDealList) {
		this.mbDealList = mbDealList;
	}			
	// MBDeal Report end

	//Vehicle Details Report-start
	private int pageSize;
	private int resultSize;
	List<List<List<String>>> list1 = null;
	private List<VehDetailsForm> vehDetailsList = new ArrayList<VehDetailsForm>();
	private String sumAmtMsrpBase;
	
	public Boolean getIsLastPage() {
		return isLastPage;
	}

	public void setIsLastPage(Boolean isLastPage) {
		this.isLastPage = isLastPage;
	}
	public String getSumAmtMsrpBase() {
		return sumAmtMsrpBase;
	}

	public void setSumAmtMsrpBase(String sumAmtMsrpBase) {
		this.sumAmtMsrpBase = sumAmtMsrpBase;
	}

	public String getSumAmtMsrpTotAcsry() {
		return sumAmtMsrpTotAcsry;
	}

	public void setSumAmtMsrpTotAcsry(String sumAmtMsrpTotAcsry) {
		this.sumAmtMsrpTotAcsry = sumAmtMsrpTotAcsry;
	}

	public String getSumAmtDlrRbt() {
		return sumAmtDlrRbt;
	}

	public void setSumAmtDlrRbt(String sumAmtDlrRbt) {
		this.sumAmtDlrRbt = sumAmtDlrRbt;
	}

	public String getSumAmtMsrp() {
		return sumAmtMsrp;
	}

	public void setSumAmtMsrp(String sumAmtMsrp) {
		this.sumAmtMsrp = sumAmtMsrp;
	}

	private String sumAmtMsrpTotAcsry;
	private String sumAmtDlrRbt;
	private String sumAmtMsrp;
	
	public List<VehDetailsForm> getVehDetailsList() {
		return vehDetailsList;
	}

	public void setVehDetailsList(List<VehDetailsForm> vehDetailsList) {
		this.vehDetailsList = vehDetailsList;
	}

	public int getResultSize() {
		return resultSize;
	}

	public void setResultSize(int resultSize) {
		this.resultSize = resultSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public List<DealerComplianceReportFormForFreightliner> getDealerComplianceList() {
		return dealerComplianceList;
	}

	public void setDealerComplianceList(
			List<DealerComplianceReportFormForFreightliner> dealerComplianceList) {
		this.dealerComplianceList = dealerComplianceList;
	}

	public List<DealerComplianceReportFormForSmart> getDealerComplianceListForSmart() {
		return dealerComplianceListForSmart;
	}

	public void setDealerComplianceListForSmart(
			List<DealerComplianceReportFormForSmart> dealerComplianceListForSmart) {
		this.dealerComplianceListForSmart = dealerComplianceListForSmart;
	}

	public List<DealerComplianceReportFormForCars> getDealerComplianceListForCar() {
		return dealerComplianceListForCar;
	}

	public void setDealerComplianceListForCar(
			List<DealerComplianceReportFormForCars> dealerComplianceListForCar) {
		this.dealerComplianceListForCar = dealerComplianceListForCar;
	}

	//Vehicle Details Report-end
	//Dealer Performance Unearned Bonus Report - FNC27 - Start
	private List<UnearnedForm> unearnedList = new ArrayList<UnearnedForm>();
	public List<UnearnedForm> getUnearnedList() {
		return unearnedList;
	}
	public void setUnearnedList(List<UnearnedForm> unearnedList) {
		this.unearnedList = unearnedList;
	}
	//Dealer Performance Unearned Bonus Report - FNC27 - End
	//Dealer Compliance Summary Report - Start
		LinkedList<DlrCompSummForm> monthlyDCSRList = new LinkedList<DlrCompSummForm>();
		LinkedList<DlrCompSummForm> qtrlyDCSRList = new LinkedList<DlrCompSummForm>();
		String qtrName;
		String monthName;
		
		public LinkedList<DlrCompSummForm> getMonthlyDCSRList() {
			return monthlyDCSRList;
		}

		public void setMonthlyDCSRList(LinkedList<DlrCompSummForm> monthlyDCSRList) {
			this.monthlyDCSRList = monthlyDCSRList;
		}

		public LinkedList<DlrCompSummForm> getQtrlyDCSRList() {
			return qtrlyDCSRList;
		}

		public void setQtrlyDCSRList(LinkedList<DlrCompSummForm> qtrlyDCSRList) {
			this.qtrlyDCSRList = qtrlyDCSRList;
		}

		public String getQtrName() {
			return qtrName;
		}

		public void setQtrName(String qtrName) {
			this.qtrName = qtrName;
		}
		public String getMonthName() {
			return monthName;
		}

		public void setMonthName(String monthName) {
			this.monthName = monthName;
		}
		
		//Dealer Compliance Summary Report - End

	public String netStarReportGen()  {
		final String methodName = "netStarReportGen";
		LOGGER.enter(CLASSNAME, methodName);
		List<String> vehtype = new ArrayList<String>();
		List<String> vehTypeRad = new ArrayList<String>();
		try{
			this.setMenuTabFocus(IWebConstants.REPORTS_ID);
			showResultMsg = false;
			vehicleList = MasterDataLookup.getInstance().getVehicleList();
			vehicleListRd = MasterDataLookup.getInstance().getVehicleList();
			for (VehicleType vType: vehicleListRd){
				if(!vType.getId().equalsIgnoreCase("P")){
					vehListRd.add(vType);
				}
			}
			beanList=rDelegate.generateReportList();
			vehtype.add("P");
			vehTypeRad.add("S");
			netStartRpt.setVehicleTypeRd(vehtype);
			netStartRpt.setVehTypeRadio(vehTypeRad);
			
			//Vehicle Details Report-start
			netStartRpt.setDataTypeRadio("All");
			//Vehicle Details Report-end
			list = null;
			
			listRtlCal = dashBrdDelegate.getRetailCalender(Calendar.getInstance().get(Calendar.YEAR));
			if(listRtlCal !=null && listRtlCal.size() > 0) {
				
				//To get Retail Month April Start date for the current year
				dteRetlMthBeg = listRtlCal.get(3).getDteRetlMthBeg();
			}
			
			session = request.getSession();
			session.setAttribute("BonusYearStartdate", dteRetlMthBeg);
			
		}catch (BusinessException be) {
			LOGGER.error("BusinessException occured");
			if("msg".equals(be.getMessageKey())){
				addActionError(be.getMessage());
			}
		}
		catch (TechnicalException te) {
			LOGGER.error("TechnicalException occured");
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (PersistenceException pe) {
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			LOGGER.error("Exception occured");
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		actionForward = "homeOffice";
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;

	}
	
	@SuppressWarnings("unchecked")
	public String genStaticReport()  {
		
		session = request.getSession();
		
		final String methodName = "genStatNetStarRpt";
		LOGGER.enter(CLASSNAME, methodName);
		draftrptFlag = false;
		draftrptSmartFlag = false;
		String oldPORange ="";
		String oldVehRange ="";
		String oldDealerRange ="";
		String dealerRange = "";
		String poRange = "";
		String vehRange = "";
		actionForward = "genStatNetStarRpt";
		List <String> vehTypeList = new ArrayList<String>();
		//Vehicle Details report-start
		Integer resSize = null;
		List<List<String>> totList1 = null;
		List<List<String>> totList2 = null;
		//Vehicle Details report-end
		
		//AMG-Start		
		List <String> amgProgramList = new ArrayList<String>();
		//AMG-End
		
		try{
			this.setMenuTabFocus(IWebConstants.REPORTS_ID);
			netStartRpt.setStatic(true);
			vehicleList = MasterDataLookup.getInstance().getVehicleList();
			vehicleListRd = MasterDataLookup.getInstance().getVehicleList();
			for (VehicleType vType: vehicleListRd){
				if(!vType.getId().equalsIgnoreCase("P")){
					vehListRd.add(vType);
				}
			}
			//beanList=rDelegate.generateReportList();
			//String schedule=netStartRpt.getDynamicReport().substring(netStartRpt.getDynamicReport().indexOf("_")+1);
			//String rptId=netStartRpt.getDynamicReport().substring(0,netStartRpt.getDynamicReport().indexOf("_")-1);
			//Unearned Performance Bonus calculation start -  added report id 14
			if(netStartRpt!= null && (netStartRpt.getRptId() == 3 || netStartRpt.getRptId() == 5 || netStartRpt.getRptId() == 14
					|| netStartRpt.getRptId() == 12 || netStartRpt.getRptId() == 13 || netStartRpt.getRptId() == 15)) { // AMG Changes 12 and 13
				//Unearned Performance Bonus calculation end -  added report id 14
				String vehtype = null;
				if(netStartRpt.getVehicleTypeRd() != null && netStartRpt.getVehicleTypeRd().size() > 0 && !netStartRpt.getVehicleTypeRd().isEmpty()) {
					for (int i= 0;i < netStartRpt.getVehicleTypeRd().size() ; i++) {
						vehtype = netStartRpt.getVehicleTypeRd().get(i);
						if(vehtype.equalsIgnoreCase("P")) {
							draftrptFlag = true;
							break;
						}
						else if(vehtype.equalsIgnoreCase("S")) {
							draftrptSmartFlag = true;
							break;
						}
					}
				} else {
					vehTypeList.add("P");
					draftrptFlag = true;
					netStartRpt.setVehicleTypeRd(vehTypeList);
					
				}
			}
			
			//Business decided to pull both Elite and Base
		/*	if(netStartRpt!= null && netStartRpt.getRptId() == 12 ) {
				
				//Need to add dynamically based on amg program radio selected
				if(netStartRpt.getAmgProgramRadio() == 1) {
					
					amgProgramList.add("142");
					amgProgramList.add("143");
					
				}
				else if(netStartRpt.getAmgProgramRadio() == 2) {					
					
					amgProgramList.add("143");
					
				}
				else {
					amgProgramList.add("142");					
				}
				
				netStartRpt.setAmgProgramList(amgProgramList);
			}*/
			
			
			// Dealer compilance payout report - Start
			int pageNo = 1;
			//Vehicle Details Report-start
			if(netStartRpt!= null && netStartRpt.getRptId() == 11 )
			{
				if(!netStartRpt.getPoNumber().isEmpty()){
					if(netStartRpt.getPoNumber().contains(" ")){
						oldPORange = netStartRpt.getPoNumber();
						poRange = netStartRpt.getPoNumber().replaceAll(" ",",");
						netStartRpt.setPoNumber(poRange);
					}
				}
				if(!netStartRpt.getVehicleRange().isEmpty()){
					if(netStartRpt.getVehicleRange().contains(" ")){
						oldVehRange = netStartRpt.getVehicleRange();
						vehRange = netStartRpt.getVehicleRange().replaceAll(" ",",");
						netStartRpt.setVehicleRange(vehRange);
					}
				}
				if(!netStartRpt.getDealer().isEmpty()){
					if(netStartRpt.getDealer().contains(" ")){
						oldDealerRange = netStartRpt.getDealer();
						dealerRange = netStartRpt.getDealer().replaceAll(" ",",");
						netStartRpt.setDealer(dealerRange);
					}
				}

				resSize =  (Integer) request.getSession().getAttribute("resultSize");
				totList1 =  (List<List<String>>) request.getSession().getAttribute("totalList1");
				if(netStartRpt.getDataTypeRadio().equalsIgnoreCase("All")){
				totList2 =  (List<List<String>>) request.getSession().getAttribute("totalList2");
				}
				String pageParameter = request.getParameter((new ParamEncoder("vehDetails").encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
				//execute total query only on last page
				if(pageParameter != null)
				{
					totList1 =  (List<List<String>>) request.getSession().getAttribute("totalList1");
				}
				else
				{
					request.getSession().setAttribute("totalList1", null );
				}
				setPageSize(getPageSize());
				pageSize = getPageSize();
				if(pageParameter != null)
				{
					pageNo = Integer.parseInt(pageParameter);
				}
				
				long startIndex= 1;
				long endIndex = pageSize;
				if(pageParameter != null)
				{
					startIndex = 	(pageNo - 1) * pageSize+1;
					
				}
				endIndex = pageNo*pageSize;
				System.out.println("startIndex is"+startIndex);
				if(pageParameter != null && resSize != null)
				{
					netStartRpt.setFetchReportResultCount(false);
					
					netStartRpt.setFetchReportResultTotal(false);
					
				}
				else
				{
					netStartRpt.setFetchReportResultCount(true);
					//execute total query only on last page
					//netStartRpt.setFetchReportResultTotal(true);
				}
				netStartRpt.setStartIndex(startIndex);
				netStartRpt.setEndIndex(endIndex);
				//Vehicle Details Report -end
			}
			//Courtesy Vehicle Report-start
			if(netStartRpt!= null && netStartRpt.getRptId() == 1 )
			{
				resSize =  (Integer) request.getSession().getAttribute("resultSize");
				
				String pageParameter = request.getParameter((new ParamEncoder("cvpReport").encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
				setPageSize(getPageSize());
				pageSize = getPageSize();
				if(pageParameter != null)
				{
					pageNo = Integer.parseInt(pageParameter);
				}
				
				long startIndex= 1;
				long endIndex = pageSize;
				if(pageParameter != null)
				{
					startIndex = 	(pageNo - 1) * pageSize+1;
					
				}
				endIndex = pageNo*pageSize;
				
				if(pageParameter != null && resSize != null)
				{
					netStartRpt.setFetchReportResultCount(false);					
					netStartRpt.setFetchReportResultTotal(false);
				}
				else
				{
					netStartRpt.setFetchReportResultCount(true);					
				}
				netStartRpt.setStartIndex(startIndex);
				netStartRpt.setEndIndex(endIndex);
				
			}		
			//Courtesy Vehicle Report -end
			
			//MBDeal Report start
			if(netStartRpt!= null && netStartRpt.getRptId() == 2 )
			{
				resSize =  (Integer) request.getSession().getAttribute("resultSize");
				
				String pageParameter = request.getParameter((new ParamEncoder("mbDealReport").encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
				setPageSize(getPageSize());
				pageSize = getPageSize();
				if(pageParameter != null)
				{
					pageNo = Integer.parseInt(pageParameter);
				}
				
				long startIndex= 1;
				long endIndex = pageSize;
				if(pageParameter != null)
				{
					startIndex = 	(pageNo - 1) * pageSize+1;
					
				}
				endIndex = pageNo*pageSize;
				System.out.println("startIndex is"+startIndex);
				System.out.println("endIndex is"+endIndex);
				if(pageParameter != null && resSize != null)
				{
					netStartRpt.setFetchReportResultCount(false);					
					netStartRpt.setFetchReportResultTotal(false);
				}
				else
				{
					netStartRpt.setFetchReportResultCount(true);					
				}
				netStartRpt.setStartIndex(startIndex);
				netStartRpt.setEndIndex(endIndex);
				
			}		
			//MBDeal Report end
			
			//Exception Report start
			if(netStartRpt!= null && netStartRpt.getRptId() == 6 )
			{
				resSize =  (Integer) request.getSession().getAttribute("resultSize");
				
				String pageParameter = request.getParameter((new ParamEncoder("exceptionReport").encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
				setPageSize(getPageSize());
				pageSize = getPageSize();
				if(pageParameter != null)
				{
					pageNo = Integer.parseInt(pageParameter);
				}
				
				long startIndex= 1;
				long endIndex = pageSize;
				if(pageParameter != null)
				{
					startIndex = 	(pageNo - 1) * pageSize+1;
					
				}
				endIndex = pageNo*pageSize;
				
				if(pageParameter != null && resSize != null)
				{
					netStartRpt.setFetchReportResultCount(false);					
					netStartRpt.setFetchReportResultTotal(false);
				}
				else
				{
					netStartRpt.setFetchReportResultCount(true);					
				}
				netStartRpt.setStartIndex(startIndex);
				netStartRpt.setEndIndex(endIndex);
				
			}		
			//Exception Report end
			
			//Dealer Performance Unearned Bonus Report - FNC27 - Start
			if(netStartRpt!= null && netStartRpt.getRptId() == 14 )
			{
			resSize =  (Integer) request.getSession().getAttribute("resultSize");
			String pageParameter = request.getParameter((new ParamEncoder("unearned").encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
			//execute total query only on last page
			if(pageParameter != null)
			{
				totList1 =  (List<List<String>>) request.getSession().getAttribute("totalList1");
			}
			else
			{
				request.getSession().setAttribute("totalList1", null );
			}
			setPageSize(getPageSize());
			pageSize = getPageSize();
			if(pageParameter != null)
			{
				pageNo = Integer.parseInt(pageParameter);
			}
			
			long startIndex= 1;
			long endIndex = pageSize;
			if(pageParameter != null)
			{
				startIndex = 	(pageNo - 1) * pageSize+1;
				
			}
			endIndex = pageNo*pageSize;
			System.out.println("startIndex is"+startIndex);
			if(pageParameter != null && resSize != null)
			{
				netStartRpt.setFetchReportResultCount(false);
				netStartRpt.setFetchReportResultTotal(false);
				
			}
			else
			{
				netStartRpt.setFetchReportResultCount(true);
				//execute total query only on last page
				//netStartRpt.setFetchReportResultTotal(true);
			}
			netStartRpt.setStartIndex(startIndex);
			netStartRpt.setEndIndex(endIndex);
			}
			//Dealer Performance Unearned Bonus Report - FNC27 - End
			//Dealer Compliance Summary Report - Start
			if(netStartRpt!= null && netStartRpt.getRptId() == IConstants.DLR_COMPL_SUM_REPORT_ID )
			{
				if(netStartRpt.getMonth() == 1 || netStartRpt.getMonth() == 4 || netStartRpt.getMonth() == 7 || netStartRpt.getMonth() == 10)
				{
					netStartRpt.setQtrStart(true);
				}
				else
				{
					netStartRpt.setQtrStart(false);
				}
			}
			//Dealer Compliance Summary Report - End
			if(netStartRpt!= null && netStartRpt.getRptId() == 10 )
			{
				if(!netStartRpt.getPoNumber().isEmpty()){
					if(netStartRpt.getPoNumber().contains(" ")){
						oldPORange = netStartRpt.getPoNumber();
						poRange = netStartRpt.getPoNumber().replaceAll(" ",",");
						netStartRpt.setPoNumber(poRange);
					}
				}
				if(!netStartRpt.getVehicleRange().isEmpty()){
					if(netStartRpt.getVehicleRange().contains(" ")|| netStartRpt.getVehicleRange().contains(",")){
						oldVehRange = netStartRpt.getVehicleRange();
						vehRange = netStartRpt.getVehicleRange().replaceAll(" ",",");
						netStartRpt.setVehicleRange(vehRange);
					}
				}
			}
			if(netStartRpt.getRptId()==3) {
				String key = null;
				ReportCache<String, Object> reportCache = ReportCache.getInstance();
				String sDealer = netStartRpt.getDealer();
				if(sDealer!= null && sDealer.length() > 0) {
					key = netStartRpt.getRptId() + netStartRpt.getVehicleTypeRd().toString().replaceAll("[\\[\\]]", "")+
							netStartRpt.getMonth() + netStartRpt.getYear()+netStartRpt.getDealer();
				}else {
					key = netStartRpt.getRptId() + netStartRpt.getVehicleTypeRd().toString().replaceAll("[\\[\\]]", "")+
							netStartRpt.getMonth() + netStartRpt.getYear();
				}
				LOGGER.info("Session Key " + key);
				//Modified By Basanta
				if(reportCache.containsKey(key)){
					System.out.println("Report fetched from cache , Key ::"+key);
					list = (List<List<List<String>>>) reportCache.get(key); 
				}
				else{
					LOGGER.info("Unable to find key from cache , Retreiving data from database ... ");
					//if(!ReportCache.dlrCompReportProcessing) {
						list = rDelegate.reportsGenerateStatic(netStartRpt, isGenerateExcel);
					if(StringUtils.isBlank(sDealer)  && checkValidCacheSelection(netStartRpt.getYear(),netStartRpt.getMonth()) 
								&& (list != null && ! list.isEmpty())) { 
							reportCache.put(key, list);
						}
					//}
					/*else{
						list = new ArrayList<List<List<String>>>();
						setReportGenerationInProcess(true);
						setReportGenerated(false);
					}*/
				}
				
			}
			
			else {
				list = rDelegate.reportsGenerateStatic(netStartRpt, isGenerateExcel);
			}
			
			//Vehicle Details Report-end
			
			
			//Fixes-Start
			
			if(netStartRpt.getRptId() == 12){
				
				list = convertFormat(list);
				
			}
			
			if(netStartRpt.getRptId() == 16) {
				
				list = constructFixedMarginSummarySection(list, netStartRpt.getMonth());
				list = convertFormat(list);
				
				
			}
			//Fixes-End
			
			if(!oldDealerRange.isEmpty()){
				netStartRpt.setDealer(oldDealerRange);
			}
			if(!oldPORange.isEmpty()){
				netStartRpt.setPoNumber(oldPORange);
			}
			if(!oldVehRange.isEmpty()){
				netStartRpt.setVehicleRange(oldVehRange);
			}
			if(list.size() == 0){
				list  = new ArrayList<List<List<String>>>();
				setReportGenerated(false);
			}else if(list.isEmpty()){
				list  = new ArrayList<List<List<String>>>();
				setReportGenerated(false);
			}else if(list.get(0).isEmpty()){
				list  = new ArrayList<List<List<String>>>();
				setReportGenerated(false);
			}
			else if((netStartRpt.getRptId() == 11 || netStartRpt.getRptId() == 14)
					&& netStartRpt.isFetchReportResultTotal() == true && list.get(0) != null && list.get(0).get(0) != null
					&& list.get(0).get(0).get(0) != null && list.get(0).get(0).get(0).trim().equals("0"))
			{
				list  = new ArrayList<List<List<String>>>();
				setReportGenerated(false);
			}
			else if(netStartRpt.getRptId() == 14 &&list.get(0) != null && list.get(0).get(0).get(0).trim().equals("0") ){
				list  = new ArrayList<List<List<String>>>();
				setReportGenerated(false);
			}
			
			
//Dealer Compliance Quarterly Payouts Report-Old Start
			
			else if(netStartRpt!= null && netStartRpt.getRptId() == 15 && list.get(0).isEmpty()){
				list  = new ArrayList<List<List<String>>>();
				setReportGenerated(false);
			}else if(netStartRpt!= null && netStartRpt.getRptId() == 15 && list.get(1).isEmpty()) {
				list  = new ArrayList<List<List<String>>>();
				setReportGenerated(false);				
			}else if(netStartRpt!= null && netStartRpt.getRptId() == 15 && list.get(2).isEmpty()) {
				list  = new ArrayList<List<List<String>>>();
				setReportGenerated(false);				
			}
			//Dealer Compliance Quarterly Payouts Report-Old End
			else if(netStartRpt!= null && netStartRpt.getRptId() == 3 && (list.get(0).isEmpty())){
				list  = new ArrayList<List<List<String>>>();
				setReportGenerated(false);
			}
			//Dealer Compliance Quarterly Payouts Report - Start
			else if(netStartRpt!= null && netStartRpt.getRptId() == 3 && (list.get(0) == null || (list.get(0)!= null && list.get(0).isEmpty()))) {
				list  = new ArrayList<List<List<String>>>();
				setReportGenerated(false);
			
			}
			//Vehicle Details Report-start
			else if(netStartRpt!= null && netStartRpt.getRptId() == 11) {
				System.out.println(getResultSize());
				List<List<String>> resultList;
				List<List<String>> totalList1 = new ArrayList<List<String>>();
				if(netStartRpt.isFetchReportResultCount()==true)
				{
					resultSize = Integer.parseInt(list.get(0).get(0).get(0).trim());
				}
				else
				{
					resultSize = resSize;
				}
				
				int lastPage=0,cnt;
				double totalPage;
				
				if(resultSize !=0){
					totalPage=resultSize/pageSize;
					lastPage =(int) Math.ceil(totalPage);
					cnt=resultSize%pageSize;
					if(cnt !=0){
						lastPage=lastPage+1;
					}
					
				}
				
				if(lastPage==pageNo){
					setIsLastPage(true);
					netStartRpt.setIsLastPage(true);
					
				}
				
			    if(netStartRpt.getIsLastPage() == true)
				{
			    	int lastPageSize = pageSize +1;
			    	setPageSize(lastPageSize);
			    	//execute total query only on last page
			    	netStartRpt.setFetchReportResultTotal(true);
			    	if(totList1 == null)
			    	{
			    	List<List<List<String>>> totalList = rDelegate.reportsGenerateStatic(netStartRpt, isGenerateExcel);
			    	if(totalList != null)
			    	{
			    		totalList1 = totalList.get(0);
			    	}
			    	}
			    	else
			    	{
			    		totalList1.addAll(totList1);
			    	}
			    	request.getSession().setAttribute("totalList1", totalList1);
				}
				if(netStartRpt.isFetchReportResultCount()==true)
				{
					resultSize = Integer.parseInt(list.get(0).get(0).get(0).trim());
					resultList = list.get(1);
					//execute total query only on last page
				}
				else
				{
					resultSize = resSize;
					resultList = list.get(0);
					//execute total query only on last page
				}
					if(netStartRpt.getIsLastPage() == true)
					{
							if(netStartRpt.getDataTypeRadio().equalsIgnoreCase("All")){
								if(netStartRpt.getIsLastPage()){
									amtMsrpBase=(new BigDecimal(totalList1.get(0).get(1).trim()));
									amtMsrpTotaCsry=new BigDecimal(totalList1.get(0).get(2).trim());
									amtDlrRbt=new BigDecimal(totalList1.get(0).get(3).trim());
									amtMsrp=new BigDecimal(totalList1.get(0).get(4).trim());
									setSumAmtMsrpBase(amtMsrpBase+"");
									setSumAmtMsrpTotAcsry(amtMsrpTotaCsry+"");
									setSumAmtDlrRbt(amtDlrRbt+"");
									setSumAmtMsrp(amtMsrp+"");
							}
							}
					}
				setResultSize(resultSize);
				request.getSession().setAttribute("resultSize", resultSize);
				//execute total query only on last page
				//request.getSession().setAttribute("totalList1", totalList1);
				for(List<String> l:resultList)
				{
					VehDetailsForm vehDetailsForm = new VehDetailsForm();
					
					vehDetailsForm.setDteRtl(l.get(1));
					vehDetailsForm.setIdDlr(l.get(2));
					vehDetailsForm.setNumPo(l.get(3));
					vehDetailsForm.setNumVin(l.get(4));
					vehDetailsForm.setCdeVehSts(l.get(5));
					vehDetailsForm.setCdeUseVeh(l.get(6));
					vehDetailsForm.setIndUsedVeh(l.get(7));
					vehDetailsForm.setCdeVehDdrSts(l.get(8));
					vehDetailsForm.setCdeVehDdrUse(l.get(9));
					vehDetailsForm.setIndUsedVehDdrs(l.get(10));
					vehDetailsForm.setTmeRtl(l.get(11));
					vehDetailsForm.setIdEmpPurCtrl(l.get(12));
					vehDetailsForm.setDteModlyr(l.get(13));
					vehDetailsForm.setDesModl(l.get(14));
					vehDetailsForm.setCdeRgn(l.get(15));
					vehDetailsForm.setNamRtlCusLst(l.get(16));
					vehDetailsForm.setNamRtlCusFir(l.get(17));
					vehDetailsForm.setNamRtlCusMid(l.get(18));
					vehDetailsForm.setDteTrans(l.get(19));
					vehDetailsForm.setTmeTrans(l.get(20));
					vehDetailsForm.setIndFlt(l.get(21));
					vehDetailsForm.setCdeWhsleInitType(l.get(22));
					vehDetailsForm.setCdeNatlType(l.get(23));
					vehDetailsForm.setDteVehDemoSrv(l.get(24));
					vehDetailsForm.setCdeVehTyp(l.get(25));
					vehDetailsForm.setAmtMsrpBase(convertNumberWithReadbleFormat(checkNegative(l.get(26))));
					vehDetailsForm.setAmtMsrpTotAcsry(convertNumberWithReadbleFormat(checkNegative(l.get(27))));
					vehDetailsForm.setAmtDlrRbt(convertNumberWithReadbleFormat(checkNegative(l.get(28))));
					vehDetailsForm.setAmtMsrp(convertNumberWithReadbleFormat(checkNegative(l.get(29))));
					if(netStartRpt.getDataTypeRadio().startsWith("A") || netStartRpt.getDataTypeRadio().startsWith("B")){
						vehDetailsForm.setBlockedDate(l.get(30));
						vehDetailsForm.setBlockedReason(l.get(31));
					
					}
					vehDetailsList.add(vehDetailsForm);
					
				}
				setVehDetailsList(vehDetailsList);
				if(netStartRpt.getIsLastPage() == true){
					if(netStartRpt.getDataTypeRadio().equalsIgnoreCase("All")){
					 VehDetailsForm vehDetailsForm = new VehDetailsForm();
					vehDetailsForm.setDteRtl("");
					vehDetailsForm.setIdDlr("");
					vehDetailsForm.setNumPo("");
					vehDetailsForm.setNumVin("");
					vehDetailsForm.setCdeVehSts("");
					vehDetailsForm.setCdeUseVeh("");
					vehDetailsForm.setIndUsedVeh("");
					vehDetailsForm.setCdeVehDdrSts("");
					vehDetailsForm.setCdeVehDdrUse("");
					vehDetailsForm.setIndUsedVehDdrs("");
					vehDetailsForm.setTmeRtl("");
					vehDetailsForm.setIdEmpPurCtrl("");
					vehDetailsForm.setDteModlyr("");
					vehDetailsForm.setDesModl("");
					vehDetailsForm.setCdeRgn("");
					vehDetailsForm.setNamRtlCusLst("");
					vehDetailsForm.setNamRtlCusFir("");
					vehDetailsForm.setNamRtlCusMid("");
					vehDetailsForm.setDteTrans("");
					vehDetailsForm.setTmeTrans("");
					vehDetailsForm.setIndFlt("");
					vehDetailsForm.setCdeWhsleInitType("");
					vehDetailsForm.setCdeNatlType("");
					vehDetailsForm.setDteVehDemoSrv("");
					vehDetailsForm.setCdeVehTyp("Total:");
					vehDetailsForm.setAmtMsrpBase(convertNumberWithReadbleFormat(checkNegative(getSumAmtMsrpBase())));
					vehDetailsForm.setAmtMsrpTotAcsry(convertNumberWithReadbleFormat(checkNegative(getSumAmtMsrpTotAcsry())));
					vehDetailsForm.setAmtDlrRbt(convertNumberWithReadbleFormat(checkNegative(getSumAmtDlrRbt())));
					vehDetailsForm.setAmtMsrp(convertNumberWithReadbleFormat(checkNegative(getSumAmtMsrp())));
					vehDetailsForm.setBlockedDate("");
					vehDetailsForm.setBlockedReason("");
					vehDetailsList.add(vehDetailsForm);
					setVehDetailsList(vehDetailsList);
				}
					if(netStartRpt.getDataTypeRadio().equalsIgnoreCase("Blocked")){
						VehDetailsForm vehDetailsForm = new VehDetailsForm();
						vehDetailsForm.setDteRtl("");
						vehDetailsForm.setIdDlr("");
						vehDetailsForm.setNumPo("");
						vehDetailsForm.setNumVin("");
						vehDetailsForm.setCdeVehSts("");
						vehDetailsForm.setCdeUseVeh("");
						vehDetailsForm.setIndUsedVeh("");
						vehDetailsForm.setCdeVehDdrSts("");
						vehDetailsForm.setCdeVehDdrUse("");
						vehDetailsForm.setIndUsedVehDdrs("");
						vehDetailsForm.setTmeRtl("");
						vehDetailsForm.setIdEmpPurCtrl("");
						vehDetailsForm.setDteModlyr("");
						vehDetailsForm.setDesModl("");
						vehDetailsForm.setCdeRgn("");
						vehDetailsForm.setNamRtlCusLst("");
						vehDetailsForm.setNamRtlCusFir("");
						vehDetailsForm.setNamRtlCusMid("");
						vehDetailsForm.setDteTrans("");
						vehDetailsForm.setTmeTrans("");
						vehDetailsForm.setIndFlt("");
						vehDetailsForm.setCdeWhsleInitType("");
						vehDetailsForm.setCdeNatlType("");
						vehDetailsForm.setDteVehDemoSrv("");
						vehDetailsForm.setCdeVehTyp("Total:");
						vehDetailsForm.setAmtMsrpBase(convertNumberWithReadbleFormat(checkNegative(totalList1.get(0).get(1))));
						vehDetailsForm.setAmtMsrpTotAcsry(convertNumberWithReadbleFormat(checkNegative(totalList1.get(0).get(2))));
						vehDetailsForm.setAmtDlrRbt(convertNumberWithReadbleFormat(checkNegative(totalList1.get(0).get(3))));
						vehDetailsForm.setAmtMsrp(convertNumberWithReadbleFormat(checkNegative(totalList1.get(0).get(4))));
						vehDetailsForm.setBlockedDate("");
						vehDetailsForm.setBlockedReason("");
						vehDetailsList.add(vehDetailsForm);
						setVehDetailsList(vehDetailsList);
				}
					if(netStartRpt.getDataTypeRadio().equalsIgnoreCase("Unblocked")){
						VehDetailsForm vehDetailsForm = new VehDetailsForm();
						vehDetailsForm.setDteRtl("");
						vehDetailsForm.setIdDlr("");
						vehDetailsForm.setNumPo("");
						vehDetailsForm.setNumVin("");
						vehDetailsForm.setCdeVehSts("");
						vehDetailsForm.setCdeUseVeh("");
						vehDetailsForm.setIndUsedVeh("");
						vehDetailsForm.setCdeVehDdrSts("");
						vehDetailsForm.setCdeVehDdrUse("");
						vehDetailsForm.setIndUsedVehDdrs("");
						vehDetailsForm.setTmeRtl("");
						vehDetailsForm.setIdEmpPurCtrl("");
						vehDetailsForm.setDteModlyr("");
						vehDetailsForm.setDesModl("");
						vehDetailsForm.setCdeRgn("");
						vehDetailsForm.setNamRtlCusLst("");
						vehDetailsForm.setNamRtlCusFir("");
						vehDetailsForm.setNamRtlCusMid("");
						vehDetailsForm.setDteTrans("");
						vehDetailsForm.setTmeTrans("");
						vehDetailsForm.setIndFlt("");
						vehDetailsForm.setCdeWhsleInitType("");
						vehDetailsForm.setCdeNatlType("");
						vehDetailsForm.setDteVehDemoSrv("");
						vehDetailsForm.setCdeVehTyp("Total:");
						vehDetailsForm.setAmtMsrpBase(convertNumberWithReadbleFormat(checkNegative(totalList1.get(0).get(1))));
						vehDetailsForm.setAmtMsrpTotAcsry(convertNumberWithReadbleFormat(checkNegative(totalList1.get(0).get(2))));
						vehDetailsForm.setAmtDlrRbt(convertNumberWithReadbleFormat(checkNegative(totalList1.get(0).get(3))));
						vehDetailsForm.setAmtMsrp(convertNumberWithReadbleFormat(checkNegative(totalList1.get(0).get(4))));
						vehDetailsForm.setBlockedDate("");
						vehDetailsForm.setBlockedReason("");
						vehDetailsList.add(vehDetailsForm);
						setVehDetailsList(vehDetailsList);
					}
				}
				//Vehicle Details Report-end
			}
			
			
			//Courtesy Vehicle Report-start			
			else if(netStartRpt!= null && netStartRpt.getRptId() == 1){
				
				System.out.println(getResultSize());
				List<List<String>> resultList=new ArrayList<List<String>>();
				List<List<String>> netList=new ArrayList<List<String>>();
				List<List<String>> totalList1 = new ArrayList<List<String>>();
				if(netStartRpt.isFetchReportResultCount()==true)
				{
					resultSize = Integer.parseInt(list.get(0).get(0).get(0).trim());
				}
				else
				{
					resultSize = resSize;
				}
				
				int lastPage=0,cnt;
				double totalPage;
				
				if(resultSize !=0){
					totalPage=resultSize/pageSize;
					lastPage =(int) Math.ceil(totalPage);
					cnt=resultSize%pageSize;
					if(cnt !=0){
						lastPage=lastPage+1;
					}					
				}
				
				if(lastPage==pageNo){
					setIsLastPage(true);
					netStartRpt.setIsLastPage(true);
					
				}
				
			    if(netStartRpt.getIsLastPage() == true)
				{
			    	int lastPageSize = pageSize +1;
			    	setPageSize(lastPageSize);
			    	
			    	netStartRpt.setFetchReportResultTotal(true);
			    	
			    	List<List<List<String>>> totalList = rDelegate.reportsGenerateStatic(netStartRpt, isGenerateExcel);
			    	if(totalList != null)
			    	{			    		
			    		netList = totalList.get(0);
			    		
			    	}
				}			    
		    	
		    	if(netStartRpt.isFetchReportResultCount()==true)
				{
					resultSize = Integer.parseInt(list.get(0).get(0).get(0).trim());
					resultList = list.get(1);
				}
				else
				{
					resultSize = resSize;
					resultList = list.get(0);
				}
					
				setResultSize(resultSize);
				request.getSession().setAttribute("resultSize", resultSize);
			    	
			    			
				for(List<String> res:resultList)
				{
					CVPForm cvpForm = new CVPForm();
					
					cvpForm.setPoNum(res.get(0));
					cvpForm.setModelYr(res.get(2));
					cvpForm.setModel(res.get(3));					
					cvpForm.setSerial(res.get(4));
					cvpForm.setDealer(res.get(5));
					
					//Fixes-Start
					cvpForm.setRtlDate(convertNumberWithReadbleFormat(checkNegative(res.get(6))));
					cvpForm.setFlrPlan(convertNumberWithReadbleFormat(checkNegative(res.get(7))));					
					cvpForm.setCustExp(convertNumberWithReadbleFormat(checkNegative(res.get(8))));
					cvpForm.setCustSls(convertNumberWithReadbleFormat(checkNegative(res.get(9))));
					cvpForm.setCustSvc(convertNumberWithReadbleFormat(checkNegative(res.get(10))));
					cvpForm.setPreOwned(convertNumberWithReadbleFormat(checkNegative(res.get(11))));
					cvpForm.setNvSalBns(convertNumberWithReadbleFormat(checkNegative(res.get(12))));
					cvpForm.setBrdStd(convertNumberWithReadbleFormat(checkNegative(res.get(13))));
					cvpForm.setTotal(convertNumberWithReadbleFormat(checkNegative(res.get(14))));
					//Fixes-End
					
					cvpList.add(cvpForm);
					
				}
				if(netStartRpt.getIsLastPage() == true){
					
					for(List<String> res:netList)
					{						
						CVPForm cvpForm = new CVPForm();
						
						cvpForm.setPoNum(res.get(0));
						cvpForm.setModelYr(res.get(1));
						cvpForm.setModel(res.get(2));					
						cvpForm.setSerial(res.get(3));
						cvpForm.setDealer(res.get(4));
						cvpForm.setRtlDate(res.get(5));
						
						//Fixes-Start
						cvpForm.setFlrPlan(convertNumberWithReadbleFormat(checkNegative(res.get(6))));
						cvpForm.setCustExp(convertNumberWithReadbleFormat(checkNegative(res.get(7))));	
						cvpForm.setCustSls(convertNumberWithReadbleFormat(checkNegative(res.get(8))));
						cvpForm.setCustSvc(convertNumberWithReadbleFormat(checkNegative(res.get(9))));
						cvpForm.setPreOwned(convertNumberWithReadbleFormat(checkNegative(res.get(10))));
						cvpForm.setNvSalBns(convertNumberWithReadbleFormat(checkNegative(res.get(11))));
						cvpForm.setBrdStd(convertNumberWithReadbleFormat(checkNegative(res.get(12))));
						cvpForm.setTotal(convertNumberWithReadbleFormat(checkNegative(res.get(13))));
						//Fixes-End
						
						cvpList.add(cvpForm);						
					}
					
				}
				setCvpList(cvpList);				
			}
			//Courtesy Vehicle Report-end	
			
			
			
			//Exception Report-start			
			else if(netStartRpt!= null && netStartRpt.getRptId() == 6){
				
				System.out.println(getResultSize());
				List<List<String>> resultList=new ArrayList<List<String>>();
				List<List<String>> netList=new ArrayList<List<String>>();
				List<List<String>> totalList1 = new ArrayList<List<String>>();
				if(netStartRpt.isFetchReportResultCount()==true)
				{
					resultSize = Integer.parseInt(list.get(0).get(0).get(0).trim());
				}
				else
				{
					resultSize = resSize;
				}
				
				int lastPage=0,cnt;
				double totalPage;
				
				if(resultSize !=0){
					totalPage=resultSize/pageSize;
					lastPage =(int) Math.ceil(totalPage);
					cnt=resultSize%pageSize;
					if(cnt !=0){
						lastPage=lastPage+1;
					}					
				}
				
				if(lastPage==pageNo){
					setIsLastPage(true);
					netStartRpt.setIsLastPage(true);
					
				}
				
			    if(netStartRpt.getIsLastPage() == true)
				{
			    	int lastPageSize = pageSize +1;
			    	setPageSize(lastPageSize);
			    	
			    	netStartRpt.setFetchReportResultTotal(true);
			    	
			    	List<List<List<String>>> totalList = rDelegate.reportsGenerateStatic(netStartRpt, isGenerateExcel);
			    	if(totalList != null)
			    	{			    		
			    		netList = totalList.get(0);
			    		
			    	}
				}			    
		    	
		    	if(netStartRpt.isFetchReportResultCount()==true)
				{
					resultSize = Integer.parseInt(list.get(0).get(0).get(0).trim());
					resultList = list.get(1);
				}
				else
				{
					resultSize = resSize;
					resultList = list.get(0);
				}
					
				setResultSize(resultSize);
				request.getSession().setAttribute("resultSize", resultSize);
			    	
			    			
				for(List<String> res:resultList)
				{
					ExceptionReportForm expRptForm = new ExceptionReportForm();
					
					expRptForm.setRtlDate(res.get(0));
					expRptForm.setModelYr(res.get(2));
					expRptForm.setModel(res.get(3));
					expRptForm.setSerial(res.get(4));
					expRptForm.setPoNum(res.get(5));
					expRptForm.setDealer(res.get(6));
					expRptForm.setRegion(res.get(7));
					expRptForm.setCarCount(checkNegative(res.get(8)));
					expRptForm.setReason(res.get(9));
					
					
					expRptList.add(expRptForm);
					
				}
				if(netStartRpt.getIsLastPage() == true){
					
					for(List<String> res:netList)
					{						
						ExceptionReportForm expRptForm = new ExceptionReportForm();
						
						expRptForm.setCarCount(checkNegative(res.get(0)));
						expRptForm.setReason(checkNegative(res.get(1)));
											
						expRptList.add(expRptForm);				
					}
					
				}
				setExpRptList(expRptList);				
			}
			//Exception Report-end				
			
			
			//MBDeal Report-start			
			else if(netStartRpt!= null && netStartRpt.getRptId() == 2){
				
				System.out.println(getResultSize());
				List<List<String>> resultList=new ArrayList<List<String>>();
				List<List<String>> netList=new ArrayList<List<String>>();
				List<List<String>> totalList1 = new ArrayList<List<String>>();
				if(netStartRpt.isFetchReportResultCount()==true)
				{
					resultSize = Integer.parseInt(list.get(0).get(0).get(0).trim());
				}
				else
				{
					resultSize = resSize;
				}
				
				int lastPage=0,cnt;
				double totalPage;
				
				if(resultSize !=0){
					totalPage=resultSize/pageSize;
					lastPage =(int) Math.ceil(totalPage);
					cnt=resultSize%pageSize;
					if(cnt !=0){
						lastPage=lastPage+1;
					}					
				}
				
				if(lastPage==pageNo){
					setIsLastPage(true);
					netStartRpt.setIsLastPage(true);
					
				}
				
			    if(netStartRpt.getIsLastPage() == true)
				{
			    	int lastPageSize = pageSize +1;
			    	setPageSize(lastPageSize);
			    	
			    	netStartRpt.setFetchReportResultTotal(true);
			    	
			    	List<List<List<String>>> totalList = rDelegate.reportsGenerateStatic(netStartRpt, isGenerateExcel);
			    	if(totalList != null)
			    	{			    		
			    		netList = totalList.get(0);
			    		
			    	}
				}			    
		    	
		    	if(netStartRpt.isFetchReportResultCount()==true)
				{
					resultSize = Integer.parseInt(list.get(0).get(0).get(0).trim());
					resultList = list.get(1);
				}
				else
				{
					resultSize = resSize;
					resultList = list.get(0);
				}
					
				setResultSize(resultSize);
				request.getSession().setAttribute("resultSize", resultSize);
			    	
			    			
				for(List<String> res:resultList)
				{
					MBDealForm mbDealForm = new MBDealForm();
					
					mbDealForm.setPoNum(res.get(1));
					mbDealForm.setCntl(res.get(2));
					mbDealForm.setModelYear(res.get(3));
					mbDealForm.setModel(res.get(4));					
					mbDealForm.setSerial(res.get(5));
					mbDealForm.setDealer(res.get(6));
					mbDealForm.setRtlDate(res.get(7));
					
					//Fixes-Start
					mbDealForm.setCommission(convertNumberWithReadbleFormat(checkNegative(res.get(8))));					
					mbDealForm.setDlrRes(convertNumberWithReadbleFormat(checkNegative(res.get(9))));
					mbDealForm.setFloorPlan(convertNumberWithReadbleFormat(checkNegative(res.get(10))));
					mbDealForm.setTotal(convertNumberWithReadbleFormat(checkNegative(res.get(11))));
					//Fixes-End
					
					mbDealList.add(mbDealForm);
					
				}
				if(netStartRpt.getIsLastPage() == true){
					
					for(List<String> res:netList)
					{						

						MBDealForm mbDealForm = new MBDealForm();
						
						
						mbDealForm.setPoNum(res.get(0));
						mbDealForm.setCntl(res.get(1));
						mbDealForm.setModelYear(res.get(2));
						mbDealForm.setModel(res.get(3));					
						mbDealForm.setSerial(res.get(4));
						mbDealForm.setDealer(res.get(5));
						mbDealForm.setRtlDate(res.get(6));
						mbDealForm.setCommission(convertNumberWithReadbleFormat(checkNegative(res.get(7))));					
						mbDealForm.setDlrRes(convertNumberWithReadbleFormat(checkNegative(res.get(8))));
						mbDealForm.setFloorPlan(convertNumberWithReadbleFormat(checkNegative(res.get(9))));
						mbDealForm.setTotal(convertNumberWithReadbleFormat(checkNegative(res.get(10))));
						
						mbDealList.add(mbDealForm);		
					}
					
				}
				setMbDealList(mbDealList);				
			}
			//MBDeal Report-end		
			
			//Dealer Performance Unearned Bonus Report - FNC27 -Start
			else if(netStartRpt!= null && netStartRpt.getRptId() == 14) 
			{
			System.out.println(getResultSize());
			List<List<String>> resultList;
			List<List<String>> totalList1 = new ArrayList<List<String>>();
			
			if(netStartRpt.isFetchReportResultCount()==true)
			{
				resultSize = Integer.parseInt(list.get(0).get(0).get(0).trim());
			}
			else
			{
				resultSize = resSize;
			}
			int lastPage=0,cnt;
			double totalPage;
			if(resultSize !=0){
				totalPage=resultSize/pageSize;
				lastPage =(int) Math.ceil(totalPage);
				cnt=resultSize%pageSize;
				if(cnt !=0){
					lastPage=lastPage+1;
				}
				
			}
			
			if(lastPage==pageNo){
				setIsLastPage(true);
				netStartRpt.setIsLastPage(true);
				
			}
			
		    if(netStartRpt.getIsLastPage() == true)
			{
		    	int lastPageSize = pageSize +1;
		    	setPageSize(lastPageSize);
		    	//execute total query only on last page
		    	netStartRpt.setFetchReportResultTotal(true);
		    	if(totList1 == null)
		    	{
		    	List<List<List<String>>> totalList = rDelegate.reportsGenerateStatic(netStartRpt, isGenerateExcel);
		    	if(totalList != null)
		    	{
		    		totalList1 = totalList.get(0);
		    	}
		    	}
		    	else
		    	{
		    		totalList1.addAll(totList1);
		    	}
		    	request.getSession().setAttribute("totalList1", totalList1);
			}
			if(netStartRpt.isFetchReportResultCount()==true)
			{
				resultSize = Integer.parseInt(list.get(0).get(0).get(0).trim());
				resultList = list.get(1);
				//execute total query only on last page
				/*if(netStartRpt.isFetchReportResultTotal() == true)
				{
					totalList1 = list.get(2);
				}
				else
				{
					totalList1.addAll(totList1);
				}*/
			}
			
			else
			{
				resultSize = resSize;
				resultList = list.get(0);
				//execute total query only on last page
				/*if(netStartRpt.isFetchReportResultTotal() == true)
				{
					totalList1 = list.get(1);
				}
				else
				{
					totalList1.addAll(totList1);
				}*/
			}
				
			setResultSize(resultSize);
			request.getSession().setAttribute("resultSize", resultSize);
			//execute total query only on last page
			//request.getSession().setAttribute("totalList1", totalList1);
			for(List<String> l:resultList)
			{
				UnearnedForm unearnedForm = new UnearnedForm();
				unearnedForm.setDteRtl(l.get(1));
				unearnedForm.setIdDlr(l.get(2));
				unearnedForm.setNumPo(l.get(3));
				unearnedForm.setDesModl(l.get(4));
				if(netStartRpt.getVehicleTypeRd().contains("F") || netStartRpt.getVehicleTypeRd().contains("V"))
				{
					
					unearnedForm.setCustExp(convertNumberWithReadbleFormat(checkNegative(l.get(5))));
					unearnedForm.setUnearnedCustExp(convertNumberWithReadbleFormat(checkNegative(l.get(6))));
					double unearnedCustExp = getFormattedNumber(unearnedForm.getUnearnedCustExp());
					unearnedForm.setBrdStd(convertNumberWithReadbleFormat(checkNegative(l.get(7))));
					unearnedForm.setUnearnedBrdStd(convertNumberWithReadbleFormat(checkNegative(l.get(8))));
					double unearnedBrdStd = getFormattedNumber(unearnedForm.getUnearnedBrdStd());
					unearnedForm.setSalesBonus(convertNumberWithReadbleFormat(checkNegative(l.get(9))));
					unearnedForm.setUnearnedSalesBonus(convertNumberWithReadbleFormat(checkNegative(l.get(10))));
					double unearnedSalesBonus = getFormattedNumber(unearnedForm.getUnearnedSalesBonus());
					unearnedForm.setDlrResv(convertNumberWithReadbleFormat(checkNegative(l.get(11))));
					double unearnedTotal = unearnedCustExp+unearnedBrdStd+unearnedSalesBonus;
					unearnedForm.setUnearnedTotal(convertNumberWithReadbleFormat(checkNegative(unearnedTotal+"")));
					unearnedForm.setNumVin(l.get(12));
				}
				else if(netStartRpt.getVehicleTypeRd().contains("S"))
				{
					unearnedForm.setBrdStd(convertNumberWithReadbleFormat(checkNegative(l.get(5))));
					unearnedForm.setDlrResv(convertNumberWithReadbleFormat(checkNegative(l.get(6))));
					unearnedForm.setUnearnedBrdStd(convertNumberWithReadbleFormat(checkNegative(l.get(7))));
					unearnedForm.setUnearnedTotal(convertNumberWithReadbleFormat(checkNegative(l.get(7))));
					unearnedForm.setNumVin(l.get(8));
				}
				else if(netStartRpt.getVehicleTypeRd().contains("P"))
				{
					unearnedForm.setCustExpSales(convertNumberWithReadbleFormat(checkNegative(l.get(5))));
					unearnedForm.setUnearnedCustExpSales(convertNumberWithReadbleFormat(checkNegative(l.get(6))));
					double unearnedCustExpSales = getFormattedNumber(unearnedForm.getUnearnedCustExpSales());
					unearnedForm.setCustExpService(convertNumberWithReadbleFormat(checkNegative(l.get(7))));
					unearnedForm.setUnearnedCustExpService(convertNumberWithReadbleFormat(checkNegative(l.get(8))));
					double unearnedCustExpService = getFormattedNumber(unearnedForm.getUnearnedCustExpService());
					unearnedForm.setPoSales(convertNumberWithReadbleFormat(checkNegative(l.get(9))));
					unearnedForm.setUnearnedPoSales(convertNumberWithReadbleFormat(checkNegative(l.get(10))));
					double unearnedPoSales =getFormattedNumber(unearnedForm.getUnearnedPoSales());
					unearnedForm.setNvSales(convertNumberWithReadbleFormat(checkNegative(l.get(11))));
					unearnedForm.setUnearnedNvSales(convertNumberWithReadbleFormat(checkNegative(l.get(12))));
					double unearnedNvSales = getFormattedNumber(unearnedForm.getUnearnedNvSales());
					unearnedForm.setBrdStd(convertNumberWithReadbleFormat(checkNegative(l.get(13))));
					unearnedForm.setUnearnedBrdStd(convertNumberWithReadbleFormat(checkNegative(l.get(14))));
					double unearnedBrdStd = getFormattedNumber(unearnedForm.getUnearnedBrdStd());
					unearnedForm.setFlrPlan(convertNumberWithReadbleFormat(checkNegative(l.get(15))));
					double unearnedTotal = unearnedCustExpSales+unearnedCustExpService+unearnedPoSales+unearnedNvSales+unearnedBrdStd;
					unearnedForm.setUnearnedTotal(convertNumberWithReadbleFormat(checkNegative(unearnedTotal+"")));
					unearnedForm.setNumVin(l.get(16));
				}
				unearnedList.add(unearnedForm);
				
			}
			setUnearnedList(unearnedList);
			if(netStartRpt.getIsLastPage() == true){
				for(List<String> l:totalList1)
				{
					UnearnedForm unearnedForm = new UnearnedForm();
					unearnedForm.setDteRtl("Totals:");
					unearnedForm.setIdDlr(l.get(2));
					unearnedForm.setNumPo(l.get(3));
					unearnedForm.setDesModl(l.get(4));
					if(netStartRpt.getVehicleTypeRd().contains("F") || netStartRpt.getVehicleTypeRd().contains("V"))
					{
						unearnedForm.setCustExp(convertNumberWithReadbleFormat(checkNegative(l.get(5))));
						unearnedForm.setUnearnedCustExp(convertNumberWithReadbleFormat(checkNegative(l.get(6))));
						BigDecimal unearnedCustExp = getFormattedBigDecimal(unearnedForm.getUnearnedCustExp());
						unearnedForm.setBrdStd(convertNumberWithReadbleFormat(checkNegative(l.get(7))));
						unearnedForm.setUnearnedBrdStd(convertNumberWithReadbleFormat(checkNegative(l.get(8))));
						BigDecimal unearnedBrdStd = getFormattedBigDecimal(unearnedForm.getUnearnedBrdStd());
						unearnedForm.setSalesBonus(convertNumberWithReadbleFormat(checkNegative(l.get(9))));
						unearnedForm.setUnearnedSalesBonus(convertNumberWithReadbleFormat(checkNegative(l.get(10))));
						BigDecimal unearnedSalesBonus = getFormattedBigDecimal(unearnedForm.getUnearnedSalesBonus());
						unearnedForm.setDlrResv(convertNumberWithReadbleFormat(checkNegative(l.get(11))));
						BigDecimal unearnedTotal = unearnedCustExp.add(unearnedBrdStd).add(unearnedSalesBonus);
						unearnedForm.setUnearnedTotal(convertNumberWithReadbleFormat(checkNegative(unearnedTotal+"")));
					}
					else if(netStartRpt.getVehicleTypeRd().contains("S"))
					{
						unearnedForm.setBrdStd(convertNumberWithReadbleFormat(checkNegative(l.get(5))));
						unearnedForm.setDlrResv(convertNumberWithReadbleFormat(checkNegative(l.get(6))));
						unearnedForm.setUnearnedBrdStd(convertNumberWithReadbleFormat(checkNegative(l.get(7))));
						unearnedForm.setUnearnedTotal(convertNumberWithReadbleFormat(checkNegative(l.get(7))));
					}
					else if(netStartRpt.getVehicleTypeRd().contains("P"))
					{
						unearnedForm.setCustExpSales(convertNumberWithReadbleFormat(checkNegative(l.get(5))));
						unearnedForm.setUnearnedCustExpSales(convertNumberWithReadbleFormat(checkNegative(l.get(6))));
						BigDecimal unearnedCustExpSales = getFormattedBigDecimal(unearnedForm.getUnearnedCustExpSales());
						unearnedForm.setCustExpService(convertNumberWithReadbleFormat(checkNegative(l.get(7))));
						unearnedForm.setUnearnedCustExpService(convertNumberWithReadbleFormat(checkNegative(l.get(8))));
						BigDecimal unearnedCustExpService = getFormattedBigDecimal(unearnedForm.getUnearnedCustExpService());
						unearnedForm.setPoSales(convertNumberWithReadbleFormat(checkNegative(l.get(9))));
						unearnedForm.setUnearnedPoSales(convertNumberWithReadbleFormat(checkNegative(l.get(10))));
						BigDecimal unearnedPoSales = getFormattedBigDecimal(unearnedForm.getUnearnedPoSales());
						unearnedForm.setNvSales(convertNumberWithReadbleFormat(checkNegative(l.get(11))));
						unearnedForm.setUnearnedNvSales(convertNumberWithReadbleFormat(checkNegative(l.get(12))));
						BigDecimal unearnedNvSales = getFormattedBigDecimal(unearnedForm.getUnearnedNvSales());
						unearnedForm.setBrdStd(convertNumberWithReadbleFormat(checkNegative(l.get(13))));
						unearnedForm.setUnearnedBrdStd(convertNumberWithReadbleFormat(checkNegative(l.get(14))));
						BigDecimal unearnedBrdStd = getFormattedBigDecimal(unearnedForm.getUnearnedBrdStd());
						unearnedForm.setFlrPlan(convertNumberWithReadbleFormat(checkNegative(l.get(15))));
						BigDecimal unearnedTotal = unearnedCustExpSales.add(unearnedCustExpService).add(unearnedPoSales).add(unearnedNvSales).add(unearnedBrdStd);
						unearnedForm.setUnearnedTotal(convertNumberWithReadbleFormat(checkNegative(unearnedTotal+"")));
					}
					unearnedList.add(unearnedForm);
				}
			}
			}
			//Dealer Performance Unearned Bonus Report - FNC27 - End
			else if(netStartRpt!= null && netStartRpt.getRptId() == 3) {
				if(list != null && list.size() > 0){
					/*setQtr(list.get(0).get(0).get(10));
					setYr(list.get(0).get(0).get(11));
					//if(draftrptFlag){
*/					if(netStartRpt.getVehicleTypeRd().contains("P"))
					{
						//CUST EXP CHANGES - START
						//custExpflag = validateNegativeCustExp();
						//setCustExpflag(custExpflag);
						//CUST EXP CHANGES - END
						setDCQPRVariablesForCars(list);
						
					}
					else if(netStartRpt.getVehicleTypeRd().contains("S"))
					{
						setDCQPRVariablesForSmart(list);
					}
					else{
						setDCQPRVariablesForFTLOrVan(list);
					}
					
				}
				
			}
			//Dealer Compliance Quarterly Payouts Report - End
			//Dealer Compliance Summary Report - Start
			else if(netStartRpt!= null && netStartRpt.getRptId() == IConstants.DLR_COMPL_SUM_REPORT_ID) {
				List<List<String>> resultList = list.get(0);
				int qtr = DPBCommonHelper.calculateQyarter(netStartRpt.getMonth());
				String qtrName = "Q"+qtr;
				setQtrName(qtrName);
				String monthName= DPBCommonHelper.getMonthName(netStartRpt.getMonth());
				setMonthName(monthName);
				if(netStartRpt.getVehicleTypeRd().contains("P")){
				fillDlrCompSummList(resultList);
				}
				else if(netStartRpt.getVehicleTypeRd().contains("S")){
					fillDlrCompSummListSmart(resultList);
				}
				else if(netStartRpt.getVehicleTypeRd().contains("V")){
					fillDlrCompSummListVans(resultList);
				}	
				else if(netStartRpt.getVehicleTypeRd().contains("F")){
					fillDlrCompSummListFtl(resultList);
				}
			if(monthlyDCSRList != null && qtrlyDCSRList!= null && monthlyDCSRList.isEmpty() && qtrlyDCSRList.isEmpty())
			{
				setReportGenerated(false);
			}
			}
			
			//Dealer Compliance Summary Report - End
		}
		catch (TechnicalException te) {
			LOGGER.error("TechnicalException occured");
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (PersistenceException pe) {
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			LOGGER.error("Exception occured");
			request.setAttribute (IWebConstants.jspExe, e);
			e.printStackTrace();
			System.out.println("Exception===>"+e.getMessage());
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;

	}

	
	/**
	 * added By Basanta
	 * @param inputNumber
	 * Converted the number with decimal value to only Number e.g 125.00 will become 125
	 * also converted  number to readable format like 1451 to 1,451
	 * @return
	 */
	private String convertNumberWithReadbleFormat(String inputNumber) {
		if(StringUtils.isNotBlank(inputNumber) && NumberUtils.isNumber(inputNumber.trim())){
			inputNumber = NumberFormat.getIntegerInstance(Locale.US).format(NumberUtils.createDouble(inputNumber).intValue());
		}
		return inputNumber;
	}

	/**
	 * @return 
	 * 
	 */
	private String checkNegative(String input){
		
		if(input!=null)
		{
			if (input.contains("-")) {
				input = input.replaceAll("-", "").trim();
				input= "-" + input;
				input = input.replaceAll("[.][0-9]+$", "");
				}
		}
			return input;
	}
	
	private String checkNegativeForExcel(String input){
		if(input!=null){
		if (input.contains("-")) {
			input = input.replaceAll("-", "");
			input= input.trim();
			input= "-" + input;
			}
		
		}
		return input;
	}
	private boolean checkValidCacheSelection(int selectionYear,int selectionMonth) {
		boolean eligibleForCaching = true;
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH)+1;
		if( (selectionYear == currentYear) && (selectionMonth == currentMonth)){
			eligibleForCaching = false;
		}
		else
			System.out.println("Valid Selection for caching !!!.");
		return eligibleForCaching;
	}

	/**
	 * Updated on 10/06/2016 for Performance Improvement 
	 * @param list2
	 */
	private void setDCQPRVariablesForCars(List<List<List<String>>> list3) {

		int listNo = 0;
		
		/*setQtr(list.get(listNo).get(0).get(9));
		setYr(list.get(listNo).get(0).get(10));
*/
//		setDsName(list.get(listNo).get(0).get(7));
		setCustExpSalesName("MB Customer Experience Sales");
		setCustExpServiceName("MB Customer Experience Service");
		setPoName(list.get(listNo).get(0).get(10));
		setSeName(list.get(listNo).get(0).get(11));
		setBsName(list.get(listNo).get(0).get(12));
		setPerc1(list.get(listNo).get(0).get(13));
		setPerc5(list.get(listNo).get(0).get(14));
		setPerc6(list.get(listNo).get(0).get(15));
		setPerc2(list.get(listNo).get(0).get(16));
		setPerc3(list.get(listNo).get(0).get(17));
		setPerc4(list.get(listNo).get(0).get(18));
		setQtr(list.get(listNo).get(0).get(19));
		setYr(list.get(listNo).get(0).get(20));
		setSalesEffAvg(list.get(listNo).get(0).get(4));
		setMbcsiAvg(list.get(listNo).get(0).get(5));
		setMsrp(convertNumberWithReadbleFormat(checkNegative(list.get(listNo).get(0).get(6))));
		setDlrStandAvg(list.get(listNo).get(0).get(0));
		setCustExpSales(list.get(listNo).get(0).get(1));
		setCustExpService(list.get(listNo).get(0).get(2));
		setPreOwnAvg(list.get(listNo).get(0).get(3));
		double fixedPct;
		setSumVarAvg(Double.parseDouble(getSalesEffAvg().replace("%", ""))
				+ Double.parseDouble(getMbcsiAvg().replace("%", ""))
				+ Double.parseDouble(getPreOwnAvg().replace("%", ""))
				+ Double.parseDouble(getCustExpSales().replace("%", ""))
				+ Double.parseDouble(getCustExpService().replace("%", "")) + "");
		if (netStartRpt.getVehicleTypeRd().contains("P")) {
			fixedPct = 8;
		} else {
			fixedPct = 10;
		}
		setVarTotal(fixedPct + Double.parseDouble(getSumVarAvg().replace("%", "")) + "");

		
		int cy13Value = 0;

		for(int k=0; k<list.size();  k++){
			//CUST EXP CHANGES - CHANGED INDEX TO 42
			int c = 43;
			cy13Value = cy13Value + Integer.valueOf(list.get(k).get(0).get(++c).trim().replace(",", ""));
			setCy13Total(String.valueOf(cy13Value));
            //setCy13Total(Double.parseDouble(getCy13Total().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(41).replace(",", ""))+"")+"");
           // setDsTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getDsTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
            //Need to give ++c here as previously while setting ds total itwas getting incremented.
			c=c+1;
            setCustExpSalesTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getCustExpSalesTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
            setCustExpServiceTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getCustExpServiceTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
            setPreTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getPreTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
            setSlsTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getSlsTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
            setMbTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getMbTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
            setUnEarnedTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getUnEarnedTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
     }

		double totalPayout = 0;
		//CUST EXP CHANGES - added condition
	/*	if(custExpflag == false)
		{
			totalPayout = Double.parseDouble(getDsTotal().replace(",", "")) + Double.parseDouble(getCustExpSalesTotal().replace(",", "")) +
		 			  Double.parseDouble(getCustExpServiceTotal().replace(",", ""))+Double.parseDouble(getPreTotal().replace(",", "")) +
		 			  Double.parseDouble(getSlsTotal().replace(",", "")) + Double.parseDouble(getMbTotal().replace(",", ""));
		}
		else
		{*/
			totalPayout = Double.parseDouble(getCustExpSalesTotal().replace(",", "")) +
		 			  Double.parseDouble(getCustExpServiceTotal().replace(",", ""))+Double.parseDouble(getPreTotal().replace(",", "")) +
		 			  Double.parseDouble(getSlsTotal().replace(",", "")) + Double.parseDouble(getMbTotal().replace(",", ""));
		//}
		
    		 setTotalPayout("$" + (convertNumberWithReadbleFormat(checkNegative(totalPayout+""))));
	}

	/**
	 * Updated on 10/06/2016 for Performance Improvement 
	 * @param list2
	 */
	private void setDCQPRVariablesForFTLOrVan(List<List<List<String>>> list2) {

		int listNo = 0;
		int forExcel = -1;
		if (isGenerateExcel == false
				&& netStartRpt.isFetchReportResultCount() == true) {
			listNo = 0;
		}
		if(isGenerateExcel == true){
			forExcel = -1;
		}
		
		setQtr(list.get(listNo).get(0).get(11 +forExcel));
		setYr(list.get(listNo).get(0).get(12 +forExcel));

		setDsName(list.get(listNo).get(0).get(5 +forExcel).trim());
		setSeName(list.get(listNo).get(0).get(6 +forExcel).trim());
		setBsName(list.get(listNo).get(0).get(7 +forExcel).trim());
		setPerc1(list.get(listNo).get(0).get(8 +forExcel));
		setPerc2(list.get(listNo).get(0).get(9 +forExcel));
		setPerc3(list.get(listNo).get(0).get(10 +forExcel));
		setSalesEffAvg(list.get(listNo).get(0).get(2 +forExcel));
		setMbcsiAvg(list.get(listNo).get(0).get(3 +forExcel));
		setMsrp(convertNumberWithReadbleFormat(checkNegative(list.get(listNo).get(0).get(4 +forExcel))));
		setDlrStandAvg(list.get(listNo).get(0).get(1 +forExcel));
		System.out.println(list.get(listNo).get(0).get(11 +forExcel));
		setSumVarAvg(Double.parseDouble(getSalesEffAvg().replace("%", ""))
				+ Double.parseDouble(getMbcsiAvg().replace("%", ""))
				+ Double.parseDouble(getDlrStandAvg().replace("%", "")) + "");
		double fixedPct;
		if (netStartRpt.getVehicleTypeRd().contains("P")) {
			fixedPct = 8;
		} else {
			fixedPct = 10;
		}
		setVarTotal(fixedPct + Double.parseDouble(getSumVarAvg().replace("%", "")) + "");
			
		
		int cy13Value = 0;
			
		for(int k=0; k<list.size();  k++){
			int c = 26;
			cy13Value = cy13Value + Integer.valueOf(list.get(k).get(0).get(++c).trim().replace(",", ""));
			setCy13Total(String.valueOf(cy13Value));
		    // setCy13Total(Double.parseDouble(getCy13Total().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(26).replace(",", ""))+"")+"");
            setDsTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getDsTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
            setSlsTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getSlsTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
            setMbTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getMbTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
            setUnEarnedTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getUnEarnedTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
     }
	
    // BigDecimal totalPayout = getFormattedBigDecimal(getDsTotal()).add(getFormattedBigDecimal(getSlsTotal())).add(getFormattedBigDecimal(getMbTotal()));
    
     double totalPayout = Double.parseDouble(getDsTotal().replace(",", "")) +  Double.parseDouble(getSlsTotal().replace(",", ""))
    		 +Double.parseDouble(getMbTotal().replace(",", ""));
     setTotalPayout("$" + (convertNumberWithReadbleFormat(checkNegative( totalPayout+""))));
	}

	private String convertToDecimalFormat(double input) {
		DecimalFormat myFormatter = new DecimalFormat("#,###,##0");
		String output = myFormatter.format(input);
	//System.out.println(input + " >>> Formated to >>> " + output);
	return output;
	}

	private Double getFormattedNumber(String amt) {
		// TODO Auto-generated method stub
		double d = 0;
		if(amt != null)
		{
		String s = amt.trim();
		s = s.replaceAll("\\$", "");
		s= s.replaceAll("\\,", "");
		if(s.contains("-"))
		{
			s = s.replaceAll("-", "").trim();
			s = "-"+s;
		}
		d = Double.parseDouble(s);
		}
		return d;
	}
	
	private BigDecimal getFormattedBigDecimal(String amt) {
		// TODO Auto-generated method stub
		BigDecimal d = new BigDecimal(0);
		if(amt != null)
		{
		String s = amt.trim();
		s = s.replaceAll("\\$", "");
		s= s.replaceAll("\\,", "");
		if(s.contains("-"))
		{
			s = s.replaceAll("-", "").replaceAll("^\\s+", "").replaceAll(" ","");
			s = "-"+s;
		}
		d = new BigDecimal(s);
		}
		return d;
	}
	
	private Integer getFormattedInteger(String amt)
	{
		// TODO Auto-generated method stub
		Integer i = 0;
		if(amt != null)
		{
		String s = amt.trim();
		s = s.replaceAll("\\$", "");
		s= s.replaceAll("\\,", "");
		if(s.contains("-"))
		{
			s = s.replaceAll("-", "").replaceAll("^\\s+", "").replaceAll(" ","");
			s = "-"+s;
		}
		i = Integer.parseInt(s);
		}
		return i;
	}

	public String generateBlockVehicleReport(){
		final String methodName = "generateBlockVehicleReport";
		LOGGER.enter(CLASSNAME, methodName);
		
		
		try{
			actionForward = "generateblckVehRpt";
			vehicleList = MasterDataLookup.getInstance().getVehicleList();
			vehicleListRd = MasterDataLookup.getInstance().getVehicleList();
			for (VehicleType vType: vehicleListRd){
				if(!vType.getId().equalsIgnoreCase("P")){
					vehListRd.add(vType);
				}
			}
			java.sql.Date fdate = WebHelper.convertStringToDate(netStartRpt.getFromDate());
			java.sql.Date tDate = WebHelper.convertStringToDate(netStartRpt.getToDate());
			String vId="";
	
			boolean isWhitespace =netStartRpt.getVehicleId().matches("^\\s*$");
			if(netStartRpt.getVehicleId().indexOf(",") > - 1)
			{
				String arr[] = netStartRpt.getVehicleId().split(",");
				for(String vins :  arr)
				{
					vId=vId+""+vins;
					vId=vId.trim();
					if(!vId.equals(""))
					{
						vId=vId+",";
					}
				}
				if( vId.endsWith(",")== true )
				{
					vId=vId.substring(0,vId.lastIndexOf(","));
				}
				else
				{
					netStartRpt.setVehicleId(vId);
				}
				if(arr.length == 0)
				{
					addActionError("Enter Valid VIN(s).");
				}
			
			}
			
			if(!hasActionErrors())
			{
					if(isWhitespace)
					{
						netStartRpt.setVehicleId(netStartRpt.getVehicleId().replaceAll("\\s",""));
					}
			
					if((netStartRpt.getVehicleId() !=null && !netStartRpt.getVehicleId().equals("")) && netStartRpt.getVehicleId().startsWith(",")== true  )
					{
						vId=this.getVehicleId().substring(1,this.getVehicleId().length());
					}
					else if((netStartRpt.getVehicleId() !=null && !netStartRpt.getVehicleId().equals("")) && netStartRpt.getVehicleId().endsWith(",")== true )
					{
						vId=netStartRpt.getVehicleId().substring(0,netStartRpt.getVehicleId().lastIndexOf(","));
					}
					else if( netStartRpt.getVehicleId() !=null && !netStartRpt.getVehicleId().equals(""))
					{
						vId=netStartRpt.getVehicleId().trim();
					}
					blockedList = rDelegate.getBlockVehicleReport(netStartRpt.getDealerId(),vId,fdate,tDate);
					
					if(blockedList.isEmpty())
					{
						setReportGenerated(false);
					}
					for(BlockedVehicle bVeh:blockedList){
						
						bVeh.setDisplayDate(WebHelper.formatDate(bVeh.getUpdatedDate()));
						bVeh.setDisplayetailDate(WebHelper.formatDate(bVeh.getRetailDate()));
						bVeh.setPoNo(bVeh.getPoNumber());
						bVeh.setReason(bVeh.getTxtBlckReason());
						blkList.add(bVeh);
					}
					
			}
		}catch(BusinessException be){
			LOGGER.info("BusinessException occured");
			
		}catch (TechnicalException te) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, te);
		}catch (PersistenceException pe) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, pe);
		}
		catch (Exception e) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, e);
		}
		return actionForward;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String exportToExcel() {
		
		String oldPORange ="";
		String oldVehRange ="";
		String oldDealerRange ="";
		String dealerRange = "";
		String poRange = "";
		String vehRange = "";
		actionForward = null;
		//actionForward = "exportToExcel";
		//Vehicle Details report-start
		String datatypeRadioValue = netStartRpt.getDataTypeRadio();
		isGenerateExcel = true;
		//Vehicle Details report-end 
		
		//AMG-Start		
		List <String> amgProgramList = new ArrayList<String>();
		//AMG-End
		
		List <String> vehTypeList = new ArrayList<String>();
		String colNames[] = {"Retail Date","Dealer No","PO Number","Model","Cust Exp","Unearn Cust Exp","PO Sales","Unearn PO Sls" ,
							 "NV Sales","Unearn NV Sls","Brd Std","Unearn Brd Std","Unearn Total","Floor Plan",
							 "Cust Exp","Unearn Cus Exp","Smart Fran","Unearn Smt Frn","Sales Bonus","Unearn Sls Bns","Unearn Total","Dealer Reserve",
							 "Cust Exp","Unearn Cus Exp","CV Brd Std","Unearn CV BStd","Sales Bonus","Unearn Sls Bns","Unearn Total","Dealer Reserve",
							 "Cust Exp","Unearn Cus Exp","CV Brd Std","Unearn CV BStd","Sales Bonus","Unearn Sls Bns","Unearn Total","Dealer Reserve"};
		String colNames_Unearned_New[] = {"Retail Date","Dealer No","PO Number","VIN Number","Model","Cex Sales","Cex Sales Unearned",
				"CV Brand Std","CV Brand Std Unearned","Sales Bonus","Sales Bonus Unearned","Dealer Reserve","Total Unearned","Cex Sales","Cex Sales Unearned",
				"Cex Service","Cex Service Unearned","PO Sales","PO Sales Unearned","NV Sales","NV Sales Unearned","Brand Std",
				"Brand Std Unearned","Floor Plan","Total Unearned","Brand Std","Dealer Reserve","Brand Std Unearned","Total Unearned","Cex Sales","Cex Sales Unearned",
				"CV Brand Std","CV Brand Std Unearned","Sales Bonus","Sales Bonus Uneanred","Dealer Reserve","Total Unearned"};
		String colNames_Unearned_New_VanOrFreightliner[] = {"Retail Date","Dealer No","PO Number","VIN Number","Model","Cust Exp","Cust Exp Unearned",
				"Brand Std","Brand Std Unearned","Sales Bonus","Sales Bonus Unearned","Dealer Reserve","Total Unearned","Cust Exp","Cust Exp Unearned",
				"Cex Service","Cex Service Unearned","PO Sales","PO Sales Unearned","NV Sales","NV Sales Unearned","Brand Std",
				"Brand Std Unearned","Floor Plan","Total Unearned","Brand Std","Dealer Reserve","Brand Std Unearned","Total Unearned","Cust Exp","Cust Exp Unearned",
				"Brand Std","Brand Std Unearned","Sales Bonus","Sales Bonus Uneanred","Dealer Reserve","Total Unearned"};
		String colNames_Unearned_New_Smart[] = {"Retail Date","Dealer No","PO Number","VIN Number","Model","Cex Sales","Cex Sales Unearned",
				"CV Brand Std","CV Brand Std Unearned","Sales Bonus","Sales Bonus Unearned","Dealer Reserve","Total Unearned","Cex Sales","Cex Sales Unearned",
				"Cex Service","Cex Service Unearned","PO Sales","PO Sales Unearned","NV Sales","NV Sales Unearned","Brand Std",
				"Brand Std Unearned","Floor Plan","Total Unearned","Brand Std","Brand Std Unearned","Dealer Reserve","Total Unearned","Cex Sales","Cex Sales Unearned",
				"CV Brand Std","CV Brand Std Unearned","Sales Bonus","Sales Bonus Uneanred","Dealer Reserve","Total Unearned"};
		String cvp_colNames[] = {"PO#","MY","Model","Serial","Dealer","Retail Date","Floor Plan",
				"Cex Sales","Cex Service","PO Sales","NV Sales","Brand Std","Total"};
		
		String cvp_colNames_van_ftliner[] = {"PO#","MY","Model","Serial","Dealer","Retail Date","Dealer Reserve",
				"Cust Exp","NV Sales","Brand Std","Total"};
		
		String cvp_colNames_smart[] = {"PO#","MY","Model","Serial","Dealer","Retail Date","Dealer Reserve",
				"Brand Std","Total"};
		
		String fixed_margin_colNames[] = {"Programs","Monthly Units","Monthly Payment","Quarterly Units","Quarterly Payment","Yearly Units","Yearly Payment"};
		
		String mbdeal_colNames_vehicle_type_car[] = {"PO#","CNTL#","MY","Model","Serial","Dealer","Retail Date",
				"Commission","Floor plan","Total"};
		
		String mbdeal_colNames_vehicle_type_Dealer_Reserve[] = {"PO#","CNTL#","MY","Model","Serial","Dealer","Retail Date",
				"Commission","Dealer Reserve","Total"};
		
		
		try {
			this.setMenuTabFocus(IWebConstants.REPORTS_ID);
			netStartRpt.setStatic(true);
			vehicleList = MasterDataLookup.getInstance().getVehicleList();
			vehicleListRd = MasterDataLookup.getInstance().getVehicleList();
			for (VehicleType vType: vehicleListRd){
				if(!vType.getId().equalsIgnoreCase("P")){
					vehListRd.add(vType);
				}
			}
			//Unearned Performance Bonus calculation start: added report id 14
			if(netStartRpt!= null && (netStartRpt.getRptId() == 3 || netStartRpt.getRptId() == 5 || netStartRpt.getRptId() == 14)) 
			////Unearned Performance Bonus calculation end: added report id 14
			{
				String vehtype = null;
				if(netStartRpt.getVehicleTypeRd() != null && netStartRpt.getVehicleTypeRd().size() > 0 && !netStartRpt.getVehicleTypeRd().isEmpty()) {
					for (int i= 0;i < netStartRpt.getVehicleTypeRd().size() ; i++) {
						vehtype = netStartRpt.getVehicleTypeRd().get(i);
						if(vehtype.equalsIgnoreCase("P")) {
							draftrptFlag = true;
							break;
						}
						else if(vehtype.equalsIgnoreCase("S")) {
							draftrptSmartFlag = true;
							break;
						}
					}
				} else {
					vehTypeList.add("P");
					draftrptFlag = true;
					netStartRpt.setVehicleTypeRd(vehTypeList);
					
				}
			}
			if(netStartRpt!= null && netStartRpt.getRptId() == 11)
			{
				if(!netStartRpt.getPoNumber().isEmpty()){
					if(netStartRpt.getPoNumber().contains(" ")){
						oldPORange = netStartRpt.getPoNumber();
						poRange = netStartRpt.getPoNumber().replaceAll(" ",",");
						netStartRpt.setPoNumber(poRange);
					}
				}
				if(!netStartRpt.getVehicleRange().isEmpty()){
					if(netStartRpt.getVehicleRange().contains(" ")){
						oldVehRange = netStartRpt.getVehicleRange();
						vehRange = netStartRpt.getVehicleRange().replaceAll(" ",",");
						netStartRpt.setVehicleRange(vehRange);
					}
				}
				if(!netStartRpt.getDealer().isEmpty()){
					if(netStartRpt.getDealer().contains(" ")){
						oldDealerRange = netStartRpt.getDealer();
						dealerRange = netStartRpt.getDealer().replaceAll(" ",",");
						netStartRpt.setDealer(dealerRange);
					}
				}
			}
			
			
			if(netStartRpt!= null && netStartRpt.getRptId() == IConstants.DLR_COMPL_SUM_REPORT_ID )
			{
				if(netStartRpt.getMonth() == 1 || netStartRpt.getMonth() == 4 || netStartRpt.getMonth() == 7 || netStartRpt.getMonth() == 10)
				{
					netStartRpt.setQtrStart(true);
					list = rDelegate.reportsGenerateStatic(netStartRpt, isGenerateExcel);
				}
				else
				{
					netStartRpt.setQtrStart(false);
					list = rDelegate.reportsGenerateStatic(netStartRpt, isGenerateExcel);
				}
			}
			else if(netStartRpt.getRptId()==3) {
				String key = null;
				@SuppressWarnings("unchecked")
				ReportCache<String, Object> reportCache = ReportCache.getInstance();
				String sDealer = netStartRpt.getDealer();
				if(sDealer!= null && sDealer.length() > 0) {
					key = netStartRpt.getRptId() + netStartRpt.getVehicleTypeRd().toString().replaceAll("[\\[\\]]", "")+
							netStartRpt.getMonth() + netStartRpt.getYear()+netStartRpt.getDealer();
				}else {
					key = netStartRpt.getRptId() + netStartRpt.getVehicleTypeRd().toString().replaceAll("[\\[\\]]", "")+
							netStartRpt.getMonth() + netStartRpt.getYear();
				}

				LOGGER.info("Session Key " + key);
				//Modified By Basanta
				if(reportCache.containsKey(key)){
					System.out.println("Report fetched from cache , Key ::"+key);
					list = (List<List<List<String>>>) reportCache.get(key); 
				}
				else{
					LOGGER.info("Unable to find key from cache , Retreiving data from database ... ");
					list = rDelegate.reportsGenerateStatic(netStartRpt, isGenerateExcel);
					if(StringUtils.isBlank(sDealer) && checkValidCacheSelection(netStartRpt.getYear(),netStartRpt.getMonth())) { 
							reportCache.put(key, list);
					}
				}
				
			}

			else{
				list = rDelegate.reportsGenerateStatic(netStartRpt, isGenerateExcel);
			}
			
			
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			boolean isVehicleTypecar = false;
			
			if ( netStartRpt.getRptId() == 1){
				
				list = removeNegativeAndDecimalInExcel(list);
				
				if(netStartRpt.getVehicleType().contains("P")) {
					
					workbook = cvpExcel(workbook,cvp_colNames,list);
					
				}
				else if(netStartRpt.getVehicleType().contains("V") ||  netStartRpt.getVehicleType().contains("F")) {
					
					workbook = cvpExcelForVanAndFrghtLner(workbook,cvp_colNames_van_ftliner,list);					
									
				}
				else if(netStartRpt.getVehicleType().contains("S")) {
					
					workbook = cvpExcelForSmart(workbook,cvp_colNames_smart,list);
				}
			    getExcelReport(workbook);				
			}
			else if ( netStartRpt.getRptId() == 2){
				
				if (netStartRpt.getVehicleType() != null)  {
					 if("P".equalsIgnoreCase(netStartRpt.getVehicleType().get(0))) {
						 isVehicleTypecar = true;
					 }	 
				}
				
				list = removeNegativeAndDecimalInExcel(list);
				if(isVehicleTypecar) {
					workbook = mbDealExcel(workbook,mbdeal_colNames_vehicle_type_car,list,true);
				}
				else {
					workbook = mbDealExcel(workbook,mbdeal_colNames_vehicle_type_Dealer_Reserve,list,false);
				}
				getExcelReport(workbook);
			}
			
			else if ( netStartRpt.getRptId() == 3){
				
				if((list != null && list.size()== 0) || (list.size()>0 && (list.get(0) == null || (list.get(0)!= null && list.get(0).isEmpty())))){
					setReportGenerated(false);
					actionForward =  "genStatNetStarRpt";
				}
				else{
					if(netStartRpt.getVehicleTypeRd().contains("P"))
					{
						//custExpflag = validateNegativeCustExp();
						setDCQPRVariablesForCars(list);
						workbook = qtrPayoutExcelForCarsWithoutCustExp(workbook, list);
						
					}
					else
					if(netStartRpt.getVehicleTypeRd().contains("F") || netStartRpt.getVehicleTypeRd().contains("V")){
						setDCQPRVariablesForFTLOrVan(list);
						workbook = qtrPayoutExcelForFTLOrVan(workbook,list);
					}
					else
						if(netStartRpt.getVehicleTypeRd().contains("S")){
							setDCQPRVariablesForSmart(list);
							workbook = qtrPayoutExcelForSmart(workbook,list);
						}

					getExcelReport(workbook);
				}
			}
			else if ( netStartRpt.getRptId() == 4)
			{
				workbook = complainceSummaryExcel(workbook,list);	
				getExcelReport(workbook);
			}
			//Ratna start
			else if ( netStartRpt.getRptId() == IConstants.DLR_COMPL_SUM_REPORT_ID)
			{
				workbook = complainceSummaryExcelNew(workbook,list);	
			getExcelReport(workbook);
			}
			//Ratna end
			else if ( netStartRpt.getRptId() == 5)
			{
				workbook = unearnedBonusExcelOld(workbook,colNames,list, netStartRpt);	
				getExcelReport(workbook);
			}
			//Unearned Performance Bonus calculation start
			else if ( netStartRpt.getRptId() == 14)
			{
				if(netStartRpt.getVehicleTypeRd().contains("V") ||  netStartRpt.getVehicleTypeRd().contains("F")){
					colNames_Unearned_New = colNames_Unearned_New_VanOrFreightliner;
				}
				else if(netStartRpt.getVehicleTypeRd().contains("S")){
					colNames_Unearned_New = colNames_Unearned_New_Smart;
				}
				SXSSFWorkbook workbook1 = unearnedBonusExcel(workbook,colNames_Unearned_New,list, netStartRpt);
				getExcelReport1(workbook1);
			}
			//Unearned Performance Bonus calculation start
			else if ( netStartRpt.getRptId() == 6)
			{
				workbook = flrPlanExceptionExcel(workbook,list);
				getExcelReport(workbook);
			}
			else if ( (netStartRpt.getRptId() == 7) || (netStartRpt.getRptId() == 8))
			{
				workbook = dlrReserveExcel(workbook,list);
				getExcelReport(workbook);
			}
			//Vehicle Details Report-start
			else if ( netStartRpt.getRptId() == 11)
			{	SXSSFWorkbook workbook1 = null;
				//actionForward =  "genStatNetStarRpt";
				if(netStartRpt.getDataTypeRadio().startsWith("A") || netStartRpt.getDataTypeRadio().startsWith("B")){
					workbook1 = vehDetailsBlockedOrAllExcel(workbook,list);
				}
				else{
					workbook1 = vehDetailsUnblockedExcel(workbook,list);
				}
				getExcelReport1(workbook1);
			
			//getExcelReport(workbook);
			this.netStartRpt.setDataTypeRadio(datatypeRadioValue);
			}
			else if ( netStartRpt.getRptId() == 16){
				
				list = constructFixedMarginSummarySection(list, netStartRpt.getMonth() );
				list = removeNegativeAndDecimalInExcel(list);				
				workbook = fixedMarginExcel(workbook,fixed_margin_colNames,list);
			    getExcelReport(workbook);				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		return actionForward;
	}
	
	private XSSFWorkbook qtrPayoutExcelForCarsWithoutCustExp(
			XSSFWorkbook workbook, List<List<List<String>>> list) {


		System.out.println(getQtr());


		//int j = 0;
		while (workbook.getNumberOfSheets() > 1){
			workbook.removeSheetAt(0);
		}
		XSSFSheet sheet = workbook.createSheet("Compliance Sheet");

		XSSFCellStyle style = workbook.createCellStyle(); //Create new style
		style.setWrapText(true);

		XSSFFont headerfont = workbook.createFont();
		headerfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerfont.setFontHeightInPoints((short) 10);

		XSSFCellStyle headerstyle = workbook.createCellStyle();
		headerstyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		headerstyle.setFont(headerfont);
		headerstyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		/*		headerstyle.setWrapText(true);*/
		XSSFRow headerRow;
		XSSFCell headerCell; 

		XSSFRow row = null;
		XSSFCell cell = null;



		/*headerRow = sheet.createRow(0);

		headerCell = headerRow.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));*/
		//section 1 start
		/*headerCell.setCellValue("Report Period : 	" + getQtr() +	"quarter " +  getYr());
		headerstyle.setAlignment(CellStyle.ALIGN_LEFT);
		headerCell.setCellStyle(headerstyle);


		headerRow = sheet.createRow(1);

		headerCell = headerRow.createCell(1);
		if(draftrptFlag){
			sheet.addMergedRegion(new CellRangeAddress(1,1,1,5));
			headerCell.setCellValue("Effective Payout Summary");
			headerstyle.setAlignment(CellStyle.ALIGN_CENTER);
			headerCell.setCellStyle(headerstyle);
		}
		else
		{
			sheet.addMergedRegion(new CellRangeAddress(1,1,1,4));
			headerCell.setCellValue("Effective Payout Summary");
			headerstyle.setAlignment(CellStyle.ALIGN_CENTER);
			headerCell.setCellStyle(headerstyle);
		}

		row = sheet.createRow(2);

		cell = row.createCell(1);
		cell.setCellValue(getDsName());
		cell = row.createCell(2);
		cell.setCellValue(getCustExpSalesName());
		cell = row.createCell(3);
		cell.setCellValue(getCustExpServiceName());
		if(draftrptFlag){
			cell = row.createCell(4);
			cell.setCellValue(getPoName());
		}
		cell = row.createCell(5);
		cell.setCellValue(getSeName());
		cell = row.createCell(6);
		cell.setCellValue(getBsName());
		cell = row.createCell(7);
		cell.setCellValue("Total Eff. Payout");

		row = sheet.createRow(3);

		cell = row.createCell(1);
		cell.setCellValue(getDlrStandAvg());
		cell = row.createCell(2);
		cell.setCellValue(getCustExpSales());
		cell = row.createCell(3);
		cell.setCellValue(getCustExpService());
		if(draftrptFlag){
			cell = row.createCell(4);
			cell.setCellValue(getPreOwnAvg());
		}
		cell = row.createCell(5);
		cell.setCellValue(getSalesEffAvg());
		cell = row.createCell(6);
		cell.setCellValue(getMbcsiAvg());

		row = sheet.createRow(4);

		cell = row.createCell(1);
		cell.setCellValue("");
		if(draftrptFlag){
			cell = row.createCell(2);
			cell.setCellValue("");
		}
		cell = row.createCell(3);
		cell.setCellValue("");
		cell = row.createCell(4);
		cell.setCellValue("");
		cell = row.createCell(5);
		cell.setCellValue("");
		cell = row.createCell(6);
		cell.setCellValue("* Fixed:");
		cell = row.createCell(7);
		if(draftrptFlag){
			cell.setCellValue("8.00%");
		}
		else{
			cell.setCellValue("10.00%");
		}


		row = sheet.createRow(5);

		cell = row.createCell(1);
		cell.setCellValue("Total MSRP Avg:");
		cell = row.createCell(2);
		cell.setCellValue(getMsrp());
		if(draftrptFlag){
			cell = row.createCell(3);
			cell.setCellValue("");
		}
		cell = row.createCell(4);
		cell.setCellValue("");
		cell = row.createCell(5);
		cell.setCellValue("");
		cell = row.createCell(6);
		cell.setCellValue("Variable:");
		cell = row.createCell(7);
		cell.setCellValue(getSumVarAvg() + "%");


		row = sheet.createRow(6);

		if(draftrptFlag){
			cell = row.createCell(1);
			cell.setCellValue("");
		}
		cell = row.createCell(2);
		cell.setCellValue("");
		cell = row.createCell(3);
		cell.setCellValue("");
		cell = row.createCell(4);
		cell.setCellValue("");
		cell = row.createCell(5);
		cell.setCellValue("");
		cell = row.createCell(6);
		cell.setCellValue("");

		cell = row.createCell(7);
		if(sumVarAvg != null){
			cell.setCellValue(getVarTotal() + "%");
		}


		row = sheet.createRow(7);
		row = sheet.createRow(8);
		row = sheet.createRow(9);

		cell = row.createCell(3);
		sheet.addMergedRegion(new CellRangeAddress(9,9,3,8));

		if(draftrptFlag){
			cell.setCellValue("* assumes full 7% trade margin and 1% floor plan");
			cell.setCellStyle(headerstyle);
			style.setAlignment(CellStyle.ALIGN_LEFT);
		}
		else{
			cell.setCellValue("* assumes full 7% trade margin and 3% dealer reserve");
			cell.setCellStyle(headerstyle);
			style.setAlignment(CellStyle.ALIGN_LEFT);
		}*/
		//section 1 end
		//section 2 start
		headerRow = sheet.createRow(0);
		//headerRow = sheet.createRow(1);
		headerCell = headerRow.createCell(0);
		headerCell.setCellValue("Dealer");
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("Region");
		sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("Market");
		sheet.addMergedRegion(new CellRangeAddress(0,1,2,2));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("City");
		sheet.addMergedRegion(new CellRangeAddress(0,1,3,3));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("State");
		sheet.addMergedRegion(new CellRangeAddress(0,1,4,4));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(5);
		headerCell.setCellValue("Retail Sales (units)");
		sheet.addMergedRegion(new CellRangeAddress(0,1,5,5));
		headerCell.setCellStyle(headerstyle);

		headerCell = headerRow.createCell(6);
		headerCell.setCellValue(getCustExpSalesName());
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(7);
		headerCell.setCellValue(getCustExpServiceName());
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(8);
		headerCell.setCellValue(getPoName());
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(9);
		headerCell.setCellValue(getSeName());
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(10);
		headerCell.setCellValue(getBsName());
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(11);
		sheet.addMergedRegion(new CellRangeAddress(0,1,11,11));
		headerCell.setCellValue("Effective Payout*");
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(12);
		sheet.addMergedRegion(new CellRangeAddress(0,0,12,17));
		headerCell.setCellValue("Performance Bonus Payout $");
		headerstyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCell.setCellStyle(headerstyle);

		row = sheet.createRow(1);

		cell = row.createCell(6);
		cell.setCellValue(getPerc5() + "Total Potential");
		cell.setCellStyle(headerstyle);
		cell = row.createCell(7);
		cell.setCellValue(getPerc6() + "Total Potential");
		cell.setCellStyle(headerstyle);
		cell = row.createCell(8);
		cell.setCellValue(getPerc2() + "Total Potential");
		cell.setCellStyle(headerstyle);
		cell = row.createCell(9);
		cell.setCellValue(getPerc3() + "Total Potential");
		cell.setCellStyle(headerstyle);
		cell = row.createCell(10);
		cell.setCellValue(getPerc4() + "Total Potential");
		cell.setCellStyle(headerstyle);
		/*cell = row.createCell(12);
		cell.setCellValue(getDsName());
		cell.setCellStyle(headerstyle);*/
		cell = row.createCell(12);
		cell.setCellValue(getCustExpSalesName());
		cell.setCellStyle(headerstyle);
		cell = row.createCell(13);
		cell.setCellValue(getCustExpServiceName());
		cell.setCellStyle(headerstyle);
		cell = row.createCell(14);
		cell.setCellValue(getPoName());
		cell.setCellStyle(headerstyle);
		cell = row.createCell(15);
		cell.setCellValue(getSeName());
		cell.setCellStyle(headerstyle);
		cell = row.createCell(16);
		cell.setCellValue(getBsName());
		cell.setCellStyle(headerstyle);
		cell = row.createCell(17);
		cell.setCellValue("Total Payout");
		cell.setCellStyle(headerstyle);
		cell = row.createCell(18);
		cell.setCellValue("Total Unearned");
		cell.setCellStyle(headerstyle);

		int rowindex = 2;
		
		List<List<String>> outerTempList = null;
		for(int k = 0; k<list.size(); k++){
			if(list.get(k) != null){
				outerTempList = new ArrayList<List<String>>();
				outerTempList.addAll(list.get(k));
				for (List<String> list2 : outerTempList) {
					row = sheet.getRow(rowindex);
					int columnIndex = 0;
					if(row == null){
						row = sheet.createRow(rowindex);
					}
					//CUST EXP CHANGES - changed max index to 40 from 39
					for(int j= 21 ;j<=43;j++ )
					{
						/*if(j == 38)
						{
							cell = row.createCell(columnIndex);
							columnIndex++;
							cell.setCellValue("");
							cell = row.createCell(columnIndex);
							columnIndex++;
							cell.setCellValue(leftRightTrim(list2.get(j + 2)));
						}*/
						/*else
						{*/
							if(j >= 21 && j < 27){
							cell = row.createCell(columnIndex);
							columnIndex++;
							cell.setCellValue(leftRightTrim(list2.get(j)));
							}
							if(j >= 27 && j<33){
								if(j == 27)
								{
									continue;
								}
								cell = row.createCell(columnIndex);
								columnIndex++;
								cell.setCellValue(leftRightTrim(list2.get(j)));
							}
							if(j>=33){
								//CUST EXP CHANGES - START
								if(j == 42)
								{
									continue;
								}
								
								if(j == 34)
								{
									continue;
								}
								if(j == 35)
								{
									continue;
								}
								//CUST EXP CHANGES - END
								cell = row.createCell(columnIndex);
								columnIndex++;
								cell.setCellValue(leftRightTrim(list2.get(j)));
							}
						/*}*/

					}

					rowindex++;
				}
			}
		}
		//rowindex++;
		row = sheet.createRow(rowindex);
		cell = row.createCell(5);

		cell.setCellValue(getCy13Total());

		cell = row.createCell(12);
		cell.setCellValue("$" + getCustExpSalesTotal());
		style.setAlignment(CellStyle.ALIGN_LEFT);
		cell.setCellStyle(style);
		cell = row.createCell(13);
		cell.setCellValue("$" + getCustExpServiceTotal());
		style.setAlignment(CellStyle.ALIGN_LEFT);
		cell.setCellStyle(style);

		cell = row.createCell(14);

		cell.setCellValue("$" + getPreTotal());

		cell = row.createCell(15);
		cell.setCellValue("$" + getSlsTotal());
		cell = row.createCell(16);
		cell.setCellValue("$" + getMbTotal());
		cell = row.createCell(17);
		cell.setCellValue(getTotalPayout());
		cell = row.createCell(18);
		cell.setCellValue("$" + getUnEarnedTotal());

		return workbook;

	}

	/*private boolean validateNegativeCustExp() {
		boolean flag = true;
		List<List<String>> outerTempList = null;
		for(int k = 0; k<list.size(); k++){
			outerTempList = new ArrayList<List<String>>();
			outerTempList.addAll(list.get(k));
			for (List<String> list2 : outerTempList) {
				//CUST EXP CHANGES  - CORRECTION OF INDEX
				if(Double.valueOf(list2.get(22).trim().replaceAll("^\\s+", "").replaceAll(" ","").replace("$", "")) < 0.00) {
				if(getFormattedNumber(list2.get(35).trim()) < 0.00) {
					System.out.println("Found Negative amount ..."+list2);
					flag = false;
				}
				if(!flag) {
					break;
				}
			}
			if(!flag) {
				break;
			}
		}
		return flag;
	}
*/

	private void getExcelReport1(SXSSFWorkbook workbook1) throws IOException {
		//Added By Basanta
		ByteArrayOutputStream byteArrayOutputStream;
		byteArrayOutputStream = new ByteArrayOutputStream();
		workbook1.write(byteArrayOutputStream);
		byte [] outArray = byteArrayOutputStream.toByteArray();
		response.setContentLength(outArray.length);
		response.setHeader("Expires:", "0"); // eliminates browser caching
		if(netStartRpt != null){
			if(netStartRpt.getRptId() == 11){
				response.setHeader("Content-Disposition", "attachment; filename=Vehicle_Details_report.xlsx");
			}
			else if(netStartRpt.getRptId() == 3){
				response.setHeader("Content-Disposition", "attachment; filename=Dealer_Compliance_Quarterly_Payout_Report.xlsx");
			}
			else if(netStartRpt.getRptId() == 14){
				response.setHeader("Content-Disposition", "attachment; filename=Dealer_Performance_Unearned_Bonus_Report.xlsx");
			}
			else{
				response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");
			}
		}
		
		OutputStream outStream = response.getOutputStream();
		outStream.write(outArray);
		outStream.flush();
		byteArrayOutputStream.flush();
		outStream.close();
		byteArrayOutputStream.close();
	}

	//Vehicle Details Report-start
	public SXSSFWorkbook vehDetailsUnblockedExcel(XSSFWorkbook inWorkbook,
			List<List<List<String>>> list) {
		
		String vehicle_colNames[] ={"Date Retail","ID_DLR","NUM_PO","NUM_VIN","CDE_VEH_STS",
									"CDE_USE_VEH","IND_USED_VEH","CDE_VEH_DDR_STS","CDE_VEH_DDR_USE",
									"IND_USED_VEH_DDRS","TME_RTL","ID_EMP_PUR_CTRL","DTE_MODL_YR","DES_MODL",
									"CDE_RGN","NAM_RTL_CUS_LST","NAM_RTL_CUS_FIR","NAM_RTL_CUS_MID","DTE_TRANS",
									"TME_TRANS","IND_FLT","CDE_WHSLE_INIT_TYP","CDE_NATL_TYPE","DTE_VEH_DEMO_SRV",
									"CDE_VEH_TYP","AMT_MSRP_BASE","AMT_MSRP_TOT_ACSRY","AMT_DLR_RBT","AMT_MSRP"};
																																			
		XSSFSheet tempSheet = inWorkbook.createSheet("Report");
		SXSSFWorkbook workbook = new SXSSFWorkbook(inWorkbook);
		workbook.setCompressTempFiles(true);
		SXSSFSheet sheet = (SXSSFSheet) workbook.getSheetAt(0);
		// keep 500 rows in memory, exceeding rows will be flushed to disk
		sheet.setRandomAccessWindowSize(500);
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		//font.setBold(true);
		style.setFont(font);
		
		for (int i=0; i < vehicle_colNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(vehicle_colNames[i]);
		}
		
		for (int i=0; i < list.size(); i++) {
			for (int j=0; j < list.get(i).size(); j++) {
				if( i >= 1){
					row = sheet.createRow(list.get(0).size()+(j+1));
					//sheet.addMergedRegion(new CellRangeAddress(list.get(0).size()+(j+1),list.get(0).size()+(j+1),0,24));
					cell = row.createCell(24);
					style.setAlignment(CellStyle.ALIGN_RIGHT);
					cell.setCellStyle(style);
					cell.setCellValue(list.get(i).get(j).get(0));
					cell = row.createCell(25);
					cell.setCellStyle(style);
					cell.setCellValue(list.get(i).get(j).get(1));
					cell = row.createCell(26);
					cell.setCellStyle(style);
					cell.setCellValue(list.get(i).get(j).get(2));
					cell = row.createCell(27);
					cell.setCellStyle(style);
					cell.setCellValue(list.get(i).get(j).get(3));
					cell = row.createCell(28);
					cell.setCellStyle(style);
					cell.setCellValue(list.get(i).get(j).get(4));
				}
				else
					row = sheet.createRow(j+1);
				for(int k=0; k < list.get(i).get(j).size(); k++){
					if (i >= 1){
						continue;
					}
					else
						cell = row.createCell(k);
						cell.setCellValue(list.get(i).get(j).get(k));
				}
			}
		}
		return workbook;
	
		
	}
	//Vehicle Details Report-end
	private void getExcelReport(XSSFWorkbook workbook)
			throws FileNotFoundException, IOException {
		//Added By Basanta
		ByteArrayOutputStream byteArrayOutputStream;
		byteArrayOutputStream = new ByteArrayOutputStream();
		workbook.write(byteArrayOutputStream);
		byte [] outArray = byteArrayOutputStream.toByteArray();
		response.setContentLength(outArray.length);
		response.setHeader("Expires:", "0"); // eliminates browser caching
		if(netStartRpt != null){
			if(netStartRpt.getRptId() == 11){
				response.setHeader("Content-Disposition", "attachment; filename=Vehicle_Details_report.xlsx");
			}
			else if(netStartRpt.getRptId() == 3){
				response.setHeader("Content-Disposition", "attachment; filename=Dealer_Compliance_Quarterly_Payout_Report.xlsx");
			}
			else if(netStartRpt.getRptId() == 14){
				response.setHeader("Content-Disposition", "attachment; filename=Dealer_Performance_Unearned_Bonus_Report.xlsx");
			}
			else if(netStartRpt.getRptId() == 1){
				response.setHeader("Content-Disposition", "attachment; filename=Courtesy_Vehicle_Program_Payout_Report.xlsx");
			}
			else if(netStartRpt.getRptId() == 2 ){
				response.setHeader("Content-Disposition", "attachment; filename=MBDeal_Report.xlsx");
			}
			else if(netStartRpt.getRptId() == 16){
				response.setHeader("Content-Disposition", "attachment; filename=Fixed_Margin_Report.xlsx");
			}
			else if(netStartRpt.getRptId() == 6 ){
				response.setHeader("Content-Disposition", "attachment; filename=Retail_Exception_Report.xlsx");
			}
			else if(netStartRpt.getRptId() == IConstants.DLR_COMPL_SUM_REPORT_ID ){
				response.setHeader("Content-Disposition", "attachment; filename=Dealer_Compliance_Summary_Report.xlsx");
			}
			else{
				response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");
			}
			
		}
		
		OutputStream outStream = response.getOutputStream();
		outStream.write(outArray);
		outStream.flush();
		byteArrayOutputStream.flush();
		outStream.close();
		byteArrayOutputStream.close();
	}
		
		
	public XSSFWorkbook cvpExcel(XSSFWorkbook workbook,String cvp_colNames[],List<List<List<String>>> list){
		
		XSSFSheet sheet = workbook.createSheet("new sheet"); 
		XSSFRow row = sheet.createRow(0); 
		XSSFCell cell = row.createCell(0);
		
		for (int i=0; i < cvp_colNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(cvp_colNames[i]);
		}
		
		for (int i=0; i < list.size(); i++) {
			for (int j=0; j < list.get(i).size(); j++) {
				if( i >= 1)
					row = sheet.createRow(list.get(0).size()+(j+1));
				else
					row = sheet.createRow(j+1);
				for(int k=0; k < list.get(i).get(j).size(); k++){
					
					//This is to Skip Cust.Exp , as it is split in to Cust.sales and Cust.Service 
					if( k == 7 )
						continue;
					else if(k > 7){
						cell = row.createCell(k-1);
						cell.setCellValue(list.get(i).get(j).get(k));
					}
					else {
						cell = row.createCell(k);
						cell.setCellValue(list.get(i).get(j).get(k));
					}
					
				}
			}
		}
		return workbook;
	}
	
public XSSFWorkbook cvpExcelForVanAndFrghtLner(XSSFWorkbook workbook,String cvp_colNames_van_ftliner[],List<List<List<String>>> list){
		
		XSSFSheet sheet = workbook.createSheet("new sheet"); 
		XSSFRow row = sheet.createRow(0); 
		XSSFCell cell = row.createCell(0);
		
		for (int i=0; i < cvp_colNames_van_ftliner.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(cvp_colNames_van_ftliner[i]);
		}
		
		for (int i=0; i < list.size(); i++) {
			for (int j=0; j < list.get(i).size(); j++) {
				if( i >= 1)
					row = sheet.createRow(list.get(0).size()+(j+1));
				else
					row = sheet.createRow(j+1);
				for(int k=0; k < list.get(i).get(j).size(); k++){
					
					//This is to Skip  Cust.sales and Cust.Service as, it is Cust Exp only
					if( k == 8 || k == 9 || k == 10 )
						continue;
					else if(k > 10){
						cell = row.createCell(k-3);
						cell.setCellValue(list.get(i).get(j).get(k));
					}
					else {
						cell = row.createCell(k);
						cell.setCellValue(list.get(i).get(j).get(k));
					}
					
				}
			}
		}
		return workbook;
	}


public XSSFWorkbook cvpExcelForSmart(XSSFWorkbook workbook,String cvp_colNames_smart[],List<List<List<String>>> list){
	
	XSSFSheet sheet = workbook.createSheet("new sheet"); 
	XSSFRow row = sheet.createRow(0); 
	XSSFCell cell = row.createCell(0);
	
	for (int i=0; i < cvp_colNames_smart.length; i++) {
		cell = row.createCell(i);
		cell.setCellValue(cvp_colNames_smart[i]);
	}
	
	for (int i=0; i < list.size(); i++) {
		for (int j=0; j < list.get(i).size(); j++) {
			if( i >= 1)
				row = sheet.createRow(list.get(0).size()+(j+1));
			else
				row = sheet.createRow(j+1);
			for(int k=0; k < list.get(i).get(j).size(); k++){
				
				//This is to Skip  Cust Exp,Cust.sales,Cust.svc, NV Sales and PO Sales
				if( k >=7 && k <=11  )
					continue;
				else if(k > 11){
					cell = row.createCell(k-5);
					cell.setCellValue(list.get(i).get(j).get(k));
				}
				else {
					cell = row.createCell(k);
					cell.setCellValue(list.get(i).get(j).get(k));
				}
				
			}
		}
	}
	return workbook;
}
	
public XSSFWorkbook mbDealExcel(XSSFWorkbook workbook,String cvp_colNames[],List<List<List<String>>> list,boolean isVehTypeCar){
		
		XSSFSheet sheet = workbook.createSheet("new sheet"); 
		XSSFRow row = sheet.createRow(0); 
		XSSFCell cell = row.createCell(0);
		
		for (int i=0; i < cvp_colNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(cvp_colNames[i]);
		}
		
		for (int i=0; i < list.size(); i++) {
			for (int j=0; j < list.get(i).size(); j++) {
				if( i >= 1)
					row = sheet.createRow(list.get(0).size()+(j+1));
				else
					row = sheet.createRow(j+1);
				for(int k=0; k < list.get(i).get(j).size(); k++){
					//To Skip Dealer Reserve
					if(isVehTypeCar) {
						
						if( k==8)
							continue;
						else if(k<8) {
							cell = row.createCell(k);
							cell.setCellValue(list.get(i).get(j).get(k));
						}
						else{
							cell = row.createCell(k-1);
							cell.setCellValue(list.get(i).get(j).get(k));
						}
					}
					else {	
						
						//To Skip Floor Plan
						if( k==9)
							continue;
						else if(k<9) {
							cell = row.createCell(k);
							cell.setCellValue(list.get(i).get(j).get(k));
						}
						else{
							cell = row.createCell(k-1);
							cell.setCellValue(list.get(i).get(j).get(k));
						}
						
					}
				}
			}
		}
		return workbook;
	}
	
	
//Fixed Margin-Start
	
	public XSSFWorkbook fixedMarginExcel(XSSFWorkbook workbook,String fixed_margin_colNames[],List<List<List<String>>> list){
		
		try {

			XSSFSheet sheet = workbook.createSheet("new sheet"); 
			XSSFRow row = sheet.createRow(0); 
			XSSFCell cell = row.createCell(0);
			int count = 1;
			
			for (int i=0; i < fixed_margin_colNames.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(fixed_margin_colNames[i]);
			}
			
			if (list != null && list.size() > 0) {
				
				for (int i=0; i < list.size(); i++) {				
					
					for (int j=0; j < list.get(i).size(); j++) {
						
						if(i ==0) {
							row = sheet.createRow(j+1);
						}
						else if(i ==1) {
							row = sheet.createRow(list.get(0).size()+(j+1));
						}
						else{
							row = sheet.createRow(list.get(0).size()+list.get(1).size()+(j+1));
						}					
						for(int k=0; k < list.get(i).get(j).size(); k++){
							
							// 1. Table Section containing all Info
							//To skip S.no column value that is returned in the incoming list						
							if((i==0) && k< list.get(i).get(j).size()-1){
								
								cell = row.createCell(k);
								cell.setCellValue(list.get(i).get(j).get(k+1));
							}
							
							// 2. For Standard retail Section
							if(i == 1){
								
								if(k ==0 ){
									cell = row.createCell(k);
								}
								//To adjust cell column as per screen layout
								else{
									cell = row.createCell(k+count);
									count++;
								}							
								cell.setCellValue(list.get(i).get(j).get(k));	
								
							}
							
							//3. Summary Section
							if((i ==2) && k< list.get(i).get(j).size()-1){
								
								if(k ==0)
								  cell = row.createCell(k);
								else
								  cell = row.createCell(6);
								cell.setCellValue(list.get(i).get(j).get(k+1));
							}
						}
					}
				}			
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			
			return workbook;
		}
	//Fixed Margin-End
	
	
	public XSSFWorkbook qtrPayoutExcelForCars(XSSFWorkbook workbook,List<List<List<String>>> list){
		
		//System.out.println(getQtr());
		
		
		//int j = 0;
		while (workbook.getNumberOfSheets() > 1){
			workbook.removeSheetAt(0);
		}
		XSSFSheet sheet = workbook.createSheet("Compliance Sheet");

		XSSFCellStyle style = workbook.createCellStyle(); //Create new style
		style.setWrapText(true);

		XSSFFont headerfont = workbook.createFont();
		headerfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerfont.setFontHeightInPoints((short) 10);

		XSSFCellStyle headerstyle = workbook.createCellStyle();
		headerstyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		headerstyle.setFont(headerfont);
		headerstyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
/*		headerstyle.setWrapText(true);*/
		XSSFRow headerRow;
		XSSFCell headerCell; 

		XSSFRow row = null;
		XSSFCell cell = null;
		
		
	
		/*headerRow = sheet.createRow(0);

		headerCell = headerRow.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));*/
		//section 1 start
	/*	headerCell.setCellValue("Report Period : 	" + getQtr() +	"quarter " +  getYr());
		headerstyle.setAlignment(CellStyle.ALIGN_LEFT);
		headerCell.setCellStyle(headerstyle);


		headerRow = sheet.createRow(1);

		headerCell = headerRow.createCell(1);
		if(draftrptFlag){
			sheet.addMergedRegion(new CellRangeAddress(1,1,1,5));
			headerCell.setCellValue("Effective Payout Summary");
			headerstyle.setAlignment(CellStyle.ALIGN_CENTER);
			headerCell.setCellStyle(headerstyle);
		}
		else
		{
			sheet.addMergedRegion(new CellRangeAddress(1,1,1,4));
			headerCell.setCellValue("Effective Payout Summary");
			headerstyle.setAlignment(CellStyle.ALIGN_CENTER);
			headerCell.setCellStyle(headerstyle);
		}

		row = sheet.createRow(2);

		cell = row.createCell(1);
		cell.setCellValue(getDsName());
		cell = row.createCell(2);
		cell.setCellValue(getCustExpSalesName());
		cell = row.createCell(3);
		cell.setCellValue(getCustExpServiceName());
		if(draftrptFlag){
			cell = row.createCell(4);
			cell.setCellValue(getPoName());
		}
		cell = row.createCell(5);
		cell.setCellValue(getSeName());
		cell = row.createCell(6);
		cell.setCellValue(getBsName());
		cell = row.createCell(7);
		cell.setCellValue("Total Eff. Payout");

		row = sheet.createRow(3);

		cell = row.createCell(1);
		cell.setCellValue(getDlrStandAvg());
		cell = row.createCell(2);
		cell.setCellValue(getCustExpSales());
		cell = row.createCell(3);
		cell.setCellValue(getCustExpService());
		if(draftrptFlag){
			cell = row.createCell(4);
			cell.setCellValue(getPreOwnAvg());
		}
		cell = row.createCell(5);
		cell.setCellValue(getSalesEffAvg());
		cell = row.createCell(6);
		cell.setCellValue(getMbcsiAvg());

		row = sheet.createRow(4);

		cell = row.createCell(1);
		cell.setCellValue("");
		if(draftrptFlag){
			cell = row.createCell(2);
			cell.setCellValue("");
		}
		cell = row.createCell(3);
		cell.setCellValue("");
		cell = row.createCell(4);
		cell.setCellValue("");
		cell = row.createCell(5);
		cell.setCellValue("");
		cell = row.createCell(6);
		cell.setCellValue("* Fixed:");
		cell = row.createCell(7);
		if(draftrptFlag){
			cell.setCellValue("8.00%");
		}
		else{
			cell.setCellValue("10.00%");
		}


		row = sheet.createRow(5);

		cell = row.createCell(1);
		cell.setCellValue("Total MSRP Avg:");
		cell = row.createCell(2);
		cell.setCellValue(getMsrp());
		if(draftrptFlag){
			cell = row.createCell(3);
			cell.setCellValue("");
		}
		cell = row.createCell(4);
		cell.setCellValue("");
		cell = row.createCell(5);
		cell.setCellValue("");
		cell = row.createCell(6);
		cell.setCellValue("Variable:");
		cell = row.createCell(7);
		cell.setCellValue(getSumVarAvg() + "%");


		row = sheet.createRow(6);

		if(draftrptFlag){
			cell = row.createCell(1);
			cell.setCellValue("");
		}
		cell = row.createCell(2);
		cell.setCellValue("");
		cell = row.createCell(3);
		cell.setCellValue("");
		cell = row.createCell(4);
		cell.setCellValue("");
		cell = row.createCell(5);
		cell.setCellValue("");
		cell = row.createCell(6);
		cell.setCellValue("");
		
		cell = row.createCell(7);
		if(sumVarAvg != null){
		cell.setCellValue(getVarTotal() + "%");
		}


		row = sheet.createRow(7);
		row = sheet.createRow(8);
		row = sheet.createRow(9);
		
		cell = row.createCell(3);
		sheet.addMergedRegion(new CellRangeAddress(9,9,3,8));

		if(draftrptFlag){
			cell.setCellValue("* assumes full 7% trade margin and 1% floor plan");
			cell.setCellStyle(headerstyle);
			style.setAlignment(CellStyle.ALIGN_LEFT);
		}
		else{
			cell.setCellValue("* assumes full 7% trade margin and 3% dealer reserve");
			cell.setCellStyle(headerstyle);
			style.setAlignment(CellStyle.ALIGN_LEFT);
		}*/
		//section 1 end
		//section 2 start
		headerRow = sheet.createRow(0);
		//headerRow = sheet.createRow(11);
		headerCell = headerRow.createCell(0);
		headerCell.setCellValue("Dealer");
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("Region");
		sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("Market");
		sheet.addMergedRegion(new CellRangeAddress(0,1,2,2));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("City");
		sheet.addMergedRegion(new CellRangeAddress(0,1,3,3));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("State");
		sheet.addMergedRegion(new CellRangeAddress(0,1,4,4));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(5);
		headerCell.setCellValue("Retail Sales (units)");
		sheet.addMergedRegion(new CellRangeAddress(0,1,5,5));
		headerCell.setCellStyle(headerstyle);
		if(draftrptFlag){
			headerCell = headerRow.createCell(6);
			headerCell.setCellValue(getDsName());
			headerCell.setCellStyle(headerstyle);
			headerCell = headerRow.createCell(7);
			headerCell.setCellValue(getCustExpSalesName());
			headerCell.setCellStyle(headerstyle);
			headerCell = headerRow.createCell(8);
			headerCell.setCellValue(getCustExpServiceName());
			headerCell.setCellStyle(headerstyle);
			headerCell = headerRow.createCell(9);
			headerCell.setCellValue(getPoName());
			headerCell.setCellStyle(headerstyle);
			headerCell = headerRow.createCell(10);
			headerCell.setCellValue(getSeName());
			headerCell.setCellStyle(headerstyle);
			headerCell = headerRow.createCell(11);
			headerCell.setCellValue(getBsName());
			headerCell.setCellStyle(headerstyle);
			headerCell = headerRow.createCell(12);
			sheet.addMergedRegion(new CellRangeAddress(0,1,12,12));
			headerCell.setCellValue("Effective Payout*");
			headerCell.setCellStyle(headerstyle);
			headerCell = headerRow.createCell(13);
			sheet.addMergedRegion(new CellRangeAddress(0,0,13,19));
			headerCell.setCellValue("Performance Bonus Payout $");
			headerstyle.setAlignment(CellStyle.ALIGN_CENTER);
			headerCell.setCellStyle(headerstyle);
			
			
		}
		
		
		row = sheet.createRow(1);
		if(draftrptFlag){
			cell = row.createCell(6);
			cell.setCellValue(getPerc1() + "Total Potential");
			cell.setCellStyle(headerstyle);
			cell = row.createCell(7);
			cell.setCellValue(getPerc5() + "Total Potential");
			cell.setCellStyle(headerstyle);
			cell = row.createCell(8);
			cell.setCellValue(getPerc6() + "Total Potential");
			cell.setCellStyle(headerstyle);
			cell = row.createCell(9);
			cell.setCellValue(getPerc2() + "Total Potential");
			cell.setCellStyle(headerstyle);
			cell = row.createCell(10);
			cell.setCellValue(getPerc3() + "Total Potential");
			cell.setCellStyle(headerstyle);
			cell = row.createCell(11);
			cell.setCellValue(getPerc4() + "Total Potential");
			cell.setCellStyle(headerstyle);
			cell = row.createCell(13);
			cell.setCellValue(getDsName());
			cell.setCellStyle(headerstyle);
			cell = row.createCell(14);
			cell.setCellValue(getCustExpSalesName());
			cell.setCellStyle(headerstyle);
			cell = row.createCell(15);
			cell.setCellValue(getCustExpServiceName());
			cell.setCellStyle(headerstyle);
			cell = row.createCell(16);
			cell.setCellValue(getPoName());
			cell.setCellStyle(headerstyle);
			cell = row.createCell(17);
			cell.setCellValue(getSeName());
			cell.setCellStyle(headerstyle);
			cell = row.createCell(18);
			cell.setCellValue(getBsName());
			cell.setCellStyle(headerstyle);
			cell = row.createCell(19);
			cell.setCellValue("Total Payout");
			cell.setCellStyle(headerstyle);
			cell = row.createCell(20);
			cell.setCellValue("Total Unearned");
			cell.setCellStyle(headerstyle);
			
		}
		
		
		
		int rowindex = 2;
		List<List<String>> outerTempList = null;
		for(int k = 0; k<list.size(); k++){
			if(list.get(k) != null){
				outerTempList = new ArrayList<List<String>>();
				outerTempList.addAll(list.get(k));
				for (List<String> list2 : outerTempList) {
					row = sheet.getRow(rowindex);
					int columnIndex = 0;
					if(row == null){
						row = sheet.createRow(rowindex);
					}
					for(int j= 21 ;j<=list2.size()-9;j++ )
					{
						/*if(j == list2.size()-9)
						{
							cell = row.createCell(columnIndex);
							columnIndex++;
							cell.setCellValue("");
							cell = row.createCell(columnIndex);
							columnIndex++;
							cell.setCellValue(leftRightTrim(list2.get(j)));
						}
						else
						{*/
						//CUST EXP CHANGES - START
						if(j == 41)
						{
							continue;
						}
						if(j == 33)
						{
							continue;
						}
						//CUST EXP CHANGES - END
							cell = row.createCell(columnIndex);
							columnIndex++;
							cell.setCellValue(leftRightTrim(list2.get(j)));
						/*}*/

					}

					rowindex++;
				}
			}
		}
		//rowindex++;
		row = sheet.createRow(rowindex);
		cell = row.createCell(5);

		cell.setCellValue(getCy13Total());
		if(draftrptFlag){	
			cell = row.createCell(13);
			cell.setCellValue("$" + getDsTotal());
			style.setAlignment(CellStyle.ALIGN_LEFT);
			cell.setCellStyle(style);
			cell = row.createCell(14);
			cell.setCellValue("$" + getCustExpSalesTotal());
			style.setAlignment(CellStyle.ALIGN_LEFT);
			cell.setCellStyle(style);
			cell = row.createCell(15);
			cell.setCellValue("$" + getCustExpServiceTotal());
			style.setAlignment(CellStyle.ALIGN_LEFT);
			cell.setCellStyle(style);
			
			cell = row.createCell(16);
			
			cell.setCellValue("$" + getPreTotal());
		}
		
		cell = row.createCell(17);
		cell.setCellValue("$" + getSlsTotal());
		cell = row.createCell(18);
		cell.setCellValue("$" + getMbTotal());
		cell = row.createCell(19);
		cell.setCellValue(getTotalPayout());
		cell = row.createCell(20);
		cell.setCellValue("$" + getUnEarnedTotal());
		
		return workbook;
	}
	
	private XSSFWorkbook qtrPayoutExcelForSmart(XSSFWorkbook workbook,
			List<List<List<String>>> list2) {
		//int i = 0;
		//int j = 0;
		while (workbook.getNumberOfSheets() > 1){
			workbook.removeSheetAt(0);
		}
		XSSFSheet sheet = workbook.createSheet("Compliance Sheet");

		XSSFCellStyle style = workbook.createCellStyle(); //Create new style
		style.setWrapText(true);

		XSSFFont headerfont = workbook.createFont();
		headerfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerfont.setFontHeightInPoints((short) 10);

		XSSFCellStyle headerstyle = workbook.createCellStyle();
		headerstyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		headerstyle.setFont(headerfont);
		headerstyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		/*		headerstyle.setWrapText(true);*/
		XSSFRow headerRow;
		XSSFCell headerCell;

		XSSFRow row = null;
		XSSFCell cell = null;


		/*headerRow = sheet.createRow(0);

		headerCell = headerRow.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));*/
		/*headerCell.setCellValue("Report Period : 	" + qtr +	"quarter " +  yr);
		headerstyle.setAlignment(CellStyle.ALIGN_LEFT);
		headerCell.setCellStyle(headerstyle);


		headerRow = sheet.createRow(1);

		headerCell = headerRow.createCell(1);
		sheet.addMergedRegion(new CellRangeAddress(1,1,1,5));
		headerCell.setCellValue("Effective Payout Summary");
		headerstyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCell.setCellStyle(headerstyle);
		
		row = sheet.createRow(2);

		cell = row.createCell(1);
		cell.setCellValue(dsName);
		cell = row.createCell(2);
		cell.setCellValue(seName);
		cell = row.createCell(3);
		cell.setCellValue(bsName);
		cell = row.createCell(4);
		cell.setCellValue("SM Brand Standards");
		cell = row.createCell(5);
		cell.setCellValue("Total Eff. Payout");
		

		row = sheet.createRow(3);

		cell = row.createCell(1);
		cell.setCellValue(dlrStandAvg);
		
		cell = row.createCell(2);
		cell.setCellValue(salesEffAvg);
		cell = row.createCell(3);
		cell.setCellValue(mbcsiAvg);
		cell = row.createCell(4);
		cell.setCellValue(mbCSINewAvg);

		row = sheet.createRow(4);

		cell = row.createCell(1);
		cell.setCellValue("");
		
		cell = row.createCell(2);
		cell.setCellValue("");
		cell = row.createCell(3);
		cell.setCellValue("");
		cell = row.createCell(4);
		cell.setCellValue("* Fixed:");
		cell = row.createCell(5);
		if(draftrptFlag){
			cell.setCellValue("8.00%");
		}
		else{
			cell.setCellValue("10.00%");
		}


		row = sheet.createRow(5);

		cell = row.createCell(1);
		cell.setCellValue("Total MSRP Avg:");
		cell = row.createCell(2);
		cell.setCellValue(msrp);
		
		cell = row.createCell(3);
		cell.setCellValue("");
		cell = row.createCell(4);
		cell.setCellValue("Variable:");
		cell = row.createCell(5);
		sumVarAvg = Double.parseDouble(salesEffAvg.replace("%", ""))+Double.parseDouble(mbcsiAvg.replace("%", ""))+Double.parseDouble(dlrStandAvg.replace("%", ""))+"";
		cell.setCellValue(sumVarAvg+"%");


		row = sheet.createRow(6);

		cell = row.createCell(1);
		cell.setCellValue("");
		cell = row.createCell(2);
		cell.setCellValue("");
		cell = row.createCell(3);
		cell.setCellValue("");
		cell = row.createCell(4);
		cell.setCellValue("");

		double fixedPct;
		if(netStartRpt.getVehicleTypeRd().contains("P"))
		{
			fixedPct = 8;
		}
		else
		{
			fixedPct = 10;
		}

		cell = row.createCell(5);
		if(sumVarAvg != null){
			cell.setCellValue(fixedPct+Double.parseDouble(sumVarAvg)+"%"+"");
		}


		row = sheet.createRow(7);
		row = sheet.createRow(8);
		row = sheet.createRow(9);

		cell = row.createCell(3);
		sheet.addMergedRegion(new CellRangeAddress(9,9,3,8));

		
		cell.setCellValue("* assumes full 7% trade margin and 3% dealer reserve");
		cell.setCellStyle(headerstyle);
		style.setAlignment(CellStyle.ALIGN_LEFT);*/

		headerRow = sheet.createRow(0);
		//headerRow = sheet.createRow(11);
		headerCell = headerRow.createCell(0);
		headerCell.setCellValue("Dealer");
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("Region");
		sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("Market");
		sheet.addMergedRegion(new CellRangeAddress(0,1,2,2));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("City");
		sheet.addMergedRegion(new CellRangeAddress(0,1,3,3));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("State");
		sheet.addMergedRegion(new CellRangeAddress(0,1,4,4));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(5);
		headerCell.setCellValue("Retail Sales (units)");
		sheet.addMergedRegion(new CellRangeAddress(0,1,5,5));
		headerCell.setCellStyle(headerstyle);
		

		/*headerCell = headerRow.createCell(6);
		headerCell.setCellValue(dsName);
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(7);
		headerCell.setCellValue(seName);
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(8);
		headerCell.setCellValue(bsName);
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(9);
		headerCell.setCellValue("SM Brand Standards");
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(10);
		sheet.addMergedRegion(new CellRangeAddress(0,1,10,10));
		headerCell.setCellValue("Effective Payout*");
		headerCell.setCellStyle(headerstyle);
		
		headerCell = headerRow.createCell(11);
		sheet.addMergedRegion(new CellRangeAddress(0,0,11,15));*/
         
		headerCell = headerRow.createCell(6);
		headerCell.setCellValue("SM Brand Standards");
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(7);
		sheet.addMergedRegion(new CellRangeAddress(0,1,7,7));
		headerCell.setCellValue("Effective Payout*");
		headerCell.setCellStyle(headerstyle);
		
		headerCell = headerRow.createCell(8);
		sheet.addMergedRegion(new CellRangeAddress(0,0,8,12));
		
		headerCell.setCellValue("Performance Bonus Payout $");
		headerstyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCell.setCellStyle(headerstyle);


		row = sheet.createRow(1);
		

	/*	cell = row.createCell(6);
		cell.setCellValue(perc1 + "Total Potential");
		cell.setCellStyle(headerstyle);
		cell = row.createCell(7);
		cell.setCellValue(perc2 + "Total Potential");
		cell.setCellStyle(headerstyle);
		cell = row.createCell(8);
		cell.setCellValue(perc3 + "Total Potential");
		cell.setCellStyle(headerstyle);
		cell = row.createCell(9);
		cell.setCellValue(perc4 + "Total Potential");
		cell.setCellStyle(headerstyle);
		cell = row.createCell(11);
		cell.setCellValue(dsName);
		cell.setCellStyle(headerstyle);
		cell = row.createCell(12);
		cell.setCellValue(seName);
		cell.setCellStyle(headerstyle);
		cell = row.createCell(13);
		cell.setCellValue(bsName);
		cell.setCellStyle(headerstyle);
		cell = row.createCell(14);
		cell.setCellValue("SM Brand Standards");
		cell.setCellStyle(headerstyle);
		cell = row.createCell(15);
		cell.setCellValue("Total Payout");
		cell.setCellStyle(headerstyle);
		
		cell = row.createCell(16);
		cell.setCellValue("Unearned Amount");
		cell.setCellStyle(headerstyle);*/
           
		cell = row.createCell(6);
		cell.setCellValue(perc4 + "Total Potential");
		cell.setCellStyle(headerstyle);
	
		cell = row.createCell(8);
		cell.setCellValue("SM Brand Standards");
		cell.setCellStyle(headerstyle);
		cell = row.createCell(9);
		cell.setCellValue("Total Payout");
		cell.setCellStyle(headerstyle);
		
		cell = row.createCell(10);
		cell.setCellValue("Unearned Amount");
		cell.setCellStyle(headerstyle);
		
		
		
		
		int rowindex = 2;

		List<List<String>> outerTempList = null;
		for(int k = 0; k<list.size(); k++){
			outerTempList = new ArrayList<List<String>>();
			outerTempList.addAll(list.get(k));
			for (List<String> listSmart : outerTempList) {
				row = sheet.getRow(rowindex);
				int columnIndex = 0;
				if(row == null){
					row = sheet.createRow(rowindex);
				}
				int size = (listSmart.size()-6);
				for (int j = 15; j < size; j++) {
					if(j == 21 || j == 22 ||j == 23 || j == 26 || j == 27 ||j == 28)
					{
						continue;
					}
					cell = row.createCell(columnIndex);
					cell.setCellValue(leftRightTrim(listSmart.get(j)));
					columnIndex++;
				}
				rowindex++;
			}
		}
		row = sheet.createRow(rowindex++);
		cell = row.createCell(5);

		cell.setCellValue(getCy13Total());

/*		cell = row.createCell(11);
		cell.setCellValue("$" + getDsTotal());
		style.setAlignment(CellStyle.ALIGN_LEFT);
		cell.setCellStyle(style);


		cell = row.createCell(12);
		cell.setCellValue("$" + getSlsTotal());
		cell = row.createCell(13);
		cell.setCellValue("$" + getMbTotal());*/
		cell = row.createCell(8);
		cell.setCellValue("$" + getMbCSINewTotal());
		cell = row.createCell(9);
		cell.setCellValue(getTotalPayout());
		
		cell = row.createCell(10);
		cell.setCellValue("$" + getUnEarnedTotal());

		return workbook;
	}
	
	public XSSFWorkbook qtrPayoutExcelForFTLOrVan(XSSFWorkbook workbook,List<List<List<String>>> list){


		int i = 0;
		//int j = 0;
		while (workbook.getNumberOfSheets() > 1){
			workbook.removeSheetAt(0);
		}
		XSSFSheet sheet = workbook.createSheet("Compliance Sheet");

		XSSFCellStyle style = workbook.createCellStyle(); //Create new style
		style.setWrapText(true);

		XSSFFont headerfont = workbook.createFont();
		headerfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerfont.setFontHeightInPoints((short) 10);

		XSSFCellStyle headerstyle = workbook.createCellStyle();
		headerstyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		headerstyle.setFont(headerfont);
		headerstyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		/*		headerstyle.setWrapText(true);*/
		XSSFRow headerRow;
		XSSFCell headerCell;

		XSSFRow row = null;
		XSSFCell cell = null;

		/*headerRow = sheet.createRow(0);

		headerCell = headerRow.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));*/
		/*headerCell.setCellValue("Report Period : 	" + qtr +	"quarter " +  yr);
		headerstyle.setAlignment(CellStyle.ALIGN_LEFT);
		headerCell.setCellStyle(headerstyle);


		headerRow = sheet.createRow(1);

		headerCell = headerRow.createCell(1);
		sheet.addMergedRegion(new CellRangeAddress(1,1,1,4));
		headerCell.setCellValue("Effective Payout Summary");
		headerstyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCell.setCellStyle(headerstyle);
		
		row = sheet.createRow(2);

		cell = row.createCell(1);
		cell.setCellValue(dsName);
		cell = row.createCell(2);
		cell.setCellValue(seName);
		cell = row.createCell(3);
		cell.setCellValue(bsName);
		cell = row.createCell(4);
		cell.setCellValue("Total Eff. Payout");
	

		row = sheet.createRow(3);

		cell = row.createCell(1);
		cell.setCellValue(dlrStandAvg);
	
		cell = row.createCell(2);
		cell.setCellValue(salesEffAvg);
		cell = row.createCell(3);
		cell.setCellValue(mbcsiAvg);

		row = sheet.createRow(4);

		cell = row.createCell(1);
		cell.setCellValue("");
		
		cell = row.createCell(2);
		cell.setCellValue("");
		cell = row.createCell(3);
		cell.setCellValue("* Fixed:");
		cell = row.createCell(4);
		if(draftrptFlag){
			cell.setCellValue("8.00%");
		}
		else{
			cell.setCellValue("10.00%");
		}


		row = sheet.createRow(5);

		cell = row.createCell(1);
		cell.setCellValue("Total MSRP Avg:");
		cell = row.createCell(2);
		cell.setCellValue(msrp);
		
		cell = row.createCell(3);
		cell.setCellValue("Variable:");
		cell = row.createCell(4);
		//sumVarAvg = Double.parseDouble(salesEffAvg)+Double.parseDouble(mbcsiAvg)+Double.parseDouble(dlrStandAvg)+"";
		cell.setCellValue(sumVarAvg+"%");


		row = sheet.createRow(6);

	
		cell = row.createCell(1);
		cell.setCellValue("");
		cell = row.createCell(2);
		cell.setCellValue("");
		cell = row.createCell(3);
		cell.setCellValue("");


		double fixedPct;
		if(netStartRpt.getVehicleTypeRd().contains("P"))
		{
			fixedPct = 8;
		}
		else
		{
			fixedPct = 10;
		}

		cell = row.createCell(4);
		if(sumVarAvg != null){
			cell.setCellValue(fixedPct+Double.parseDouble(sumVarAvg)+"%"+"");
		}


		row = sheet.createRow(7);
		row = sheet.createRow(8);
		row = sheet.createRow(9);

		cell = row.createCell(3);
		sheet.addMergedRegion(new CellRangeAddress(9,9,3,8));


		cell.setCellValue("* assumes full 7% trade margin and 3% dealer reserve");
		cell.setCellStyle(headerstyle);
		style.setAlignment(CellStyle.ALIGN_LEFT);*/

		headerRow = sheet.createRow(0);
		//headerRow = sheet.createRow(11);
		headerCell = headerRow.createCell(0);
		headerCell.setCellValue("Dealer");
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("Region");
		sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("Market");
		sheet.addMergedRegion(new CellRangeAddress(0,1,2,2));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("City");
		sheet.addMergedRegion(new CellRangeAddress(0,1,3,3));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("State");
		sheet.addMergedRegion(new CellRangeAddress(0,1,4,4));
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(5);
		headerCell.setCellValue("Retail Sales (units)");
		sheet.addMergedRegion(new CellRangeAddress(0,1,5,5));
		headerCell.setCellStyle(headerstyle);
		

		headerCell = headerRow.createCell(6);
		headerCell.setCellValue(dsName);
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(7);
		headerCell.setCellValue(seName);
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(8);
		headerCell.setCellValue(bsName);
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(9);
		sheet.addMergedRegion(new CellRangeAddress(0,1,9,9));
		headerCell.setCellValue("Effective Payout*");
		headerCell.setCellStyle(headerstyle);
		headerCell = headerRow.createCell(10);
		sheet.addMergedRegion(new CellRangeAddress(0,0,10,12));

		headerCell.setCellValue("Performance Bonus Payout $");
		headerstyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCell.setCellStyle(headerstyle);

		headerCell = headerRow.createCell(13);
		sheet.addMergedRegion(new CellRangeAddress(0,1, 13, 13));
		headerCell.setCellValue("Total Payout");
		headerstyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCell.setCellStyle(headerstyle);
		
		headerCell = headerRow.createCell(14);
		sheet.addMergedRegion(new CellRangeAddress(0,1,14,14));
		headerCell.setCellValue("Unearned Amount");
		headerstyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCell.setCellStyle(headerstyle);

		row = sheet.createRow(1);
		
		cell = row.createCell(6);
		cell.setCellValue(perc1 + "Total Potential");
		cell.setCellStyle(headerstyle);
		cell = row.createCell(7);
		cell.setCellValue(perc2 + "Total Potential");
		cell.setCellStyle(headerstyle);
		cell = row.createCell(8);
		cell.setCellValue(perc3 + "Total Potential");
		cell.setCellStyle(headerstyle);
		cell = row.createCell(10);
		cell.setCellValue(dsName);
		cell.setCellStyle(headerstyle);
		cell = row.createCell(11);
		cell.setCellValue(seName);
		cell.setCellStyle(headerstyle);
		cell = row.createCell(12);
		cell.setCellValue(bsName);
		cell.setCellStyle(headerstyle);

		int rowindex = 2;
		List<List<String>> outerTempList = null;
		for(int k = 0; k<list.size(); k++){
			outerTempList = new ArrayList<List<String>>();
			outerTempList.addAll(list.get(k));
			for (List<String> list2 : outerTempList) {
				row = sheet.getRow(rowindex);
				int columnIndex = 0;
				if(row == null){
					row = sheet.createRow(rowindex);
				}
				int size = (list2.size()-5);
				for (int j = 12; j < size; j++) {
					cell = row.createCell(columnIndex);
					cell.setCellValue(leftRightTrim(list2.get(j)));
					columnIndex++;
				}
				rowindex++;
			}
		}

		row = sheet.createRow(rowindex++);
		cell = row.createCell(5);
		
		cell.setCellValue(getCy13Total());

		cell = row.createCell(10);
		cell.setCellValue("$" + getDsTotal());
		style.setAlignment(CellStyle.ALIGN_LEFT);
		cell.setCellStyle(style);


		cell = row.createCell(11);
		cell.setCellValue("$" + getSlsTotal());
		cell = row.createCell(12);
		cell.setCellValue("$" + getMbTotal());
		cell = row.createCell(13);
		cell.setCellValue(getTotalPayout());
		cell = row.createCell(14);
		cell.setCellValue("$" + getUnEarnedTotal());

		return workbook;
	}
	
	
	private String leftRightTrim(String input) {
		//System.out.println("input >>>>>  "+input );
		String output =input;
		float val =0;
		try {
			if(input.startsWith("$")) {
				//System.out.println("Inside $ values " );
				//val = Float.parseFloat(input.replaceAll("$", "").trim());	
				output = output.trim().replaceAll("^\\s+", "").replaceAll(" ","");
							}else {
				val = Float.parseFloat(input.replaceAll(",", "").trim());	
				output = output.trim().replaceAll("^\\s+", "").replaceAll(" ","");
			}
		} catch(NumberFormatException e) {
			output = output.trim().replaceAll("^\\s+", "");
		}
		//System.out.println("output  >>>"+output );
		return output;
	}
	
	
	
	private void setDCQPRVariablesForSmart(List<List<List<String>>> list2) {
		int listNo = 0;
		int forExcel = -1;
		
		setQtr(list.get(listNo).get(0).get(14 +forExcel ));
		setYr(list.get(listNo).get(0).get(15 +forExcel));

		setDsName(list.get(listNo).get(0).get(6 +forExcel));
		setSeName(list.get(listNo).get(0).get(7 +forExcel));
		setBsName(list.get(listNo).get(0).get(8 +forExcel));
		/* setMbCSINewName(list.get(0).get(0).get(8)); */

		setMbCSINewName("SM Brand Standards");

		setPerc1(list.get(listNo).get(0).get(10 +forExcel));
		setPerc2(list.get(listNo).get(0).get(11 +forExcel));
		setPerc3(list.get(listNo).get(0).get(12 +forExcel));
		setPerc4(list.get(listNo).get(0).get(13 +forExcel));

		setSalesEffAvg(list.get(listNo).get(0).get(2 +forExcel));
		setMbcsiAvg(list.get(listNo).get(0).get(3 +forExcel));
		setMbCSINewAvg(list.get(listNo).get(0).get(4+forExcel));

		setMsrp(convertNumberWithReadbleFormat(checkNegative(list.get(listNo).get(0).get(5 +forExcel))));
		setDlrStandAvg(list.get(listNo).get(0).get(1 +forExcel));

		setSumVarAvg(Double.parseDouble(getSalesEffAvg().replace("%", ""))
				+ Double.parseDouble(getMbcsiAvg().replace("%", ""))
				+ Double.parseDouble(getDlrStandAvg().replace("%", ""))+ "");
		double fixedPct;
		if (netStartRpt.getVehicleTypeRd().contains("P")) {
			fixedPct = 8;
		} else {
			fixedPct = 10;
		}
		setVarTotal(fixedPct + Double.parseDouble(getSumVarAvg().replace("%", "")) + "");
		int cy13Value = 0;
		for(int k=0; k<list.size();  k++){
			int c = 31;
			cy13Value = cy13Value + Integer.valueOf(list.get(k).get(0).get(++c).trim().replace(",", ""));
			setCy13Total(String.valueOf(cy13Value));
			//setCy13Total(String.valueOf((Integer.parseInt(((Double.parseDouble(getCy13Total().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(31).replace(",", ""))+""))+"")) )));
            setDsTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getDsTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
            setSlsTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getSlsTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
            setMbTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getMbTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
            setMbCSINewTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getMbCSINewTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
            setUnEarnedTotal(convertNumberWithReadbleFormat(checkNegative(Double.parseDouble(getUnEarnedTotal().replace(",", ""))+Double.parseDouble(getFormattedNumber(list.get(k).get(0).get(++c).replace(",", ""))+"")+"")));
     }
    /* double totalPayout = Double.parseDouble(getDsTotal().replace(",", "")) +  Double.parseDouble(getSlsTotal().replace(",", ""))
    		            +Double.parseDouble(getMbCSINewTotal().replace(",", ""));*/
     double totalPayout = Double.parseDouble(getMbCSINewTotal().replace(",", ""));
     setTotalPayout("$" + (convertNumberWithReadbleFormat(checkNegative( totalPayout+""))));


	}


	private String getOnlyDigits(String string) {
		Pattern pattern = Pattern.compile("[^0-9]");
	    Matcher matcher = pattern.matcher(string);
	    String number = matcher.replaceAll("");
	    return number;
	}

	private void addToExcel(HSSFRow row, HSSFSheet sheet, HSSFCell cell, String dlrList, int rowindex, int columnIndex) {
		
		row = sheet.getRow(rowindex);
		if(row == null){
			row = sheet.createRow(rowindex);
		}
		
		cell = row.createCell(columnIndex);
		cell.setCellValue(dlrList);
	}

	//Ratna Start
public XSSFWorkbook complainceSummaryExcelNew(XSSFWorkbook workbook, List<List<List<String>>> list){
		
		String complaince_colNames1[] = {" ","Units","Cust Exp Bns","Cex Sales","Cex Service","NV Sales","PO Sales","Brand Stds","Total Earned","Total Unearned"};
		
		String complaince_colNames2[] = {" ","Units","Cust Exp Bns","NV Sales","Smart Fran","Brand Stds","Total Earned","Total Unearned"};
		
		String complaince_colNames3[] = {" ","Units","Cust Exp Bns","NV Sales","Brand Stds","Total Earned","Total Unearned"};
		
		XSSFSheet sheet = workbook.createSheet("new sheet");
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		
		if (netStartRpt.getVehicleTypeRd().contains("P")){
			String complaince_colNames_Cars[] = {" ","Units","Cex Sales","Cex Service","NV Sales","PO Sales","Brand Stds","Total Earned","Total Unearned"};
		for (int i=0; i < complaince_colNames_Cars.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(complaince_colNames_Cars[i]);
		}
		}
		else if (netStartRpt.getVehicleTypeRd().contains("S")){
			String complaince_colNames_smart[] = {" ","Units","Brand Stds","Total Earned","Total Unearned"};
			for (int i=0; i < complaince_colNames_smart.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(complaince_colNames_smart[i]);
			}
		}
		else
		{
			for (int i=0; i < complaince_colNames3.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(complaince_colNames3[i]);
			}
		}
		
		List<List<String>> resultList = list.get(0);
		int qtr = DPBCommonHelper.calculateQyarter(netStartRpt.getMonth());
		String qtrName = "Q"+qtr;
		setQtrName(qtrName);
		String monthName= DPBCommonHelper.getMonthName(netStartRpt.getMonth());
		setMonthName(monthName);
		
		if(netStartRpt.getVehicleTypeRd().contains("P")){
		fillDlrCompSummList(resultList);
		}
		else if(netStartRpt.getVehicleTypeRd().contains("S")){
			fillDlrCompSummListSmart(resultList);
		}
		else if(netStartRpt.getVehicleTypeRd().contains("V")){
			fillDlrCompSummListVans(resultList);
		}	
		else if(netStartRpt.getVehicleTypeRd().contains("F")){
			fillDlrCompSummListFtl(resultList);
		}
	
			
			row = sheet.createRow(1);
			cell=row.createCell(0);
			cell.setCellValue(monthName);
			
			
			for (int i=2; i < monthlyDCSRList.size()+2; i++) {

				row = sheet.createRow(i);
				int j=0;
			cell=row.createCell(j++);
			cell.setCellValue(monthlyDCSRList.get(i-2).getBonusType());
			cell=row.createCell(j++);
			cell.setCellValue(monthlyDCSRList.get(i-2).getPoCount());
			if(!netStartRpt.getVehicleTypeRd().contains("P") && !netStartRpt.getVehicleTypeRd().contains("S")){
				cell=row.createCell(j++);
				cell.setCellValue(monthlyDCSRList.get(i-2).getCustExp());
			}
			
			
			if(netStartRpt.getVehicleTypeRd().contains("P")){
				cell=row.createCell(j++);
				cell.setCellValue(monthlyDCSRList.get(i-2).getCustExpSales());
				cell=row.createCell(j++);
				cell.setCellValue(monthlyDCSRList.get(i-2).getCustExpService());
			}
			if(!netStartRpt.getVehicleTypeRd().contains("S")){
			cell=row.createCell(j++);
			cell.setCellValue(monthlyDCSRList.get(i-2).getNvSales());
			}
			if(netStartRpt.getVehicleTypeRd().contains("P")){
				cell=row.createCell(j++);
				cell.setCellValue(monthlyDCSRList.get(i-2).getPreOwned());
			}
			/*if(netStartRpt.getVehicleTypeRd().contains("S")){
				cell=row.createCell(j++);
				//cell.setCellValue(monthlyDCSRList.get(i-2).getSmFran());
				cell.setCellValue(monthlyDCSRList.get(i-2).getBrdStd());
			}*/
			
			cell=row.createCell(j++);
			cell.setCellValue(monthlyDCSRList.get(i-2).getBrdStd());
			cell=row.createCell(j++);
			cell.setCellValue(monthlyDCSRList.get(i-2).getTotal());
			cell=row.createCell(j++);
			cell.setCellValue(monthlyDCSRList.get(i-2).getUnearnedBonus());
			
			}
			
			row = sheet.createRow(monthlyDCSRList.size()+2);
			cell=row.createCell(0);
			cell.setCellValue(qtrName);
			
			int nextRowNum=monthlyDCSRList.size()+3;
			
			for (int i=nextRowNum; i < qtrlyDCSRList.size()+nextRowNum; i++) {

				row = sheet.createRow(i);
				int j=0;
				
				cell=row.createCell(j++);
				cell.setCellValue(qtrlyDCSRList.get(i-nextRowNum).getBonusType());
				cell=row.createCell(j++);
				cell.setCellValue(qtrlyDCSRList.get(i-nextRowNum).getPoCount());
				if(!netStartRpt.getVehicleTypeRd().contains("P")&& !netStartRpt.getVehicleTypeRd().contains("S")){
					cell=row.createCell(j++);
					cell.setCellValue(qtrlyDCSRList.get(i-nextRowNum).getCustExp());
				}
				
				
				if(netStartRpt.getVehicleTypeRd().contains("P")){
					cell=row.createCell(j++);
					cell.setCellValue(qtrlyDCSRList.get(i-nextRowNum).getCustExpSales());
					cell=row.createCell(j++);
					cell.setCellValue(qtrlyDCSRList.get(i-nextRowNum).getCustExpService());
				}
				if(!netStartRpt.getVehicleTypeRd().contains("S")){
				cell=row.createCell(j++);
				cell.setCellValue(qtrlyDCSRList.get(i-nextRowNum).getNvSales());
				}
				if(netStartRpt.getVehicleTypeRd().contains("P")){
					cell=row.createCell(j++);
					cell.setCellValue(qtrlyDCSRList.get(i-nextRowNum).getPreOwned());
				}
				/*if(netStartRpt.getVehicleTypeRd().contains("S")){
					cell=row.createCell(j++);
					cell.setCellValue(qtrlyDCSRList.get(i-nextRowNum).getSmFran());
				}*/
				
				cell=row.createCell(j++);
				cell.setCellValue(qtrlyDCSRList.get(i-nextRowNum).getBrdStd());
				cell=row.createCell(j++);
				cell.setCellValue(qtrlyDCSRList.get(i-nextRowNum).getTotal());
				cell=row.createCell(j++);
				cell.setCellValue(qtrlyDCSRList.get(i-nextRowNum).getUnearnedBonus());
				
			}
			
		return workbook;
	}
	//Ratna End
	public XSSFWorkbook complainceSummaryExcel(XSSFWorkbook workbook, List<List<List<String>>> list){
		
		String complaince_colNames1[] = {" ","Units","Cust Exp Bns","NV Sales Bns","PO Sales Bns","Brand Stds Bns","Facility Bonus",
											"CVP Bonus","Total","Unearned Bonus"};
		
		String complaince_colNames2[] = {" ","Units","Cust Exp Bns","NV Sales Bns","Smart Franchise","Total","Unearned Bonus"};
		
		String complaince_colNames3[] = {" ","Units","Cust Exp Bns","NV Sales Bns","Brand Stds Bns","Total","Unearned Bonus"};
		
		
		XSSFSheet sheet = workbook.createSheet("new sheet");
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		
		if (netStartRpt.getVehicleTypeRd().contains("P")){
			String complaince_colNames_Cars[] = {" ","Units","Cex Sales","Cex Service","NV Sales","PO Sales","Brand Stds","Total Earned","Total Unearned"};
		for (int i=0; i < complaince_colNames_Cars.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(complaince_colNames_Cars[i]);
		}
		
		}
		else if (netStartRpt.getVehicleTypeRd().contains("S")){
			String complaince_colNames_smart[] = {" ","Units","Brand Stds","Total Earned","Total Unearned"};
			for (int i=0; i < complaince_colNames_smart.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(complaince_colNames_smart[i]);
			}
		}
		else
		{
			for (int i=0; i < complaince_colNames3.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(complaince_colNames3[i]);
			}
		}
		
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(list.get(0).get(1).get(1));
		
		boolean b = true;
		for (int i=0; i < list.size(); i++) {
			for (int j=0; j < list.get(i).size(); j++) {
				if ((list.get(i).get(j).get(1).equalsIgnoreCase("Q1") && b) || (list.get(i).get(j).get(1).equalsIgnoreCase("Q2") && b)
					|| (list.get(i).get(j).get(1).equalsIgnoreCase("Q3") && b) || (list.get(i).get(j).get(1).equalsIgnoreCase("Q4") && b)){
					row = sheet.createRow(j+2);
					cell = row.createCell(0);
					cell.setCellValue(list.get(i).get(j).get(1));
					b= false;
				}
				if (b == true)
					row = sheet.createRow(j+2);
				else
					row = sheet.createRow(j+3);
				for(int k=0; k < list.get(i).get(j).size()-2; k++){
					cell = row.createCell(k);
					 if (netStartRpt.getVehicleTypeRd().contains("P")){
						 cell.setCellValue(list.get(i).get(j).get(k+2)); 
					 }
					 else{ 
						 if ((k==4))
							 cell.setCellValue(list.get(i).get(j).get(k+3));
						 else if (k>4 && k<7)
							 cell.setCellValue(list.get(i).get(j).get(k+5));
						 else if (k>6)
							 continue;
						 else 
							 cell.setCellValue(list.get(i).get(j).get(k+2));
					 	}
				}
			}
		}
		return workbook;
	}
	
    public SXSSFWorkbook  unearnedBonusExcel(XSSFWorkbook workbook,String colNames[],List<List<List<String>>> list,NetStarReport netStartRpt) throws IOException{
		XSSFSheet tempsheet = workbook.createSheet("Unearned_Bonus_Report");
		SXSSFWorkbook workbook1 = new SXSSFWorkbook(workbook);
		workbook1.setCompressTempFiles(true);
		SXSSFSheet sheet = (SXSSFSheet) workbook1.getSheetAt(0);
		// keep 500 rows in memory, exceeding rows will be flushed to disk
		sheet.setRandomAccessWindowSize(500);
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		
		BigDecimal unearnedCustExp;
		BigDecimal unearnedBrdStd;
		BigDecimal unearnedSalesBonus;
		BigDecimal unearnedTotal;
		BigDecimal unearnedCustExpSales;
		BigDecimal unearnedCustExpService;
		BigDecimal unearnedPoSales;
		BigDecimal unearnedNvSales;

		for (int i=0; i < colNames.length; i++) {
			cell = row.createCell(i);
			if (i<5){ 
			cell.setCellValue(colNames[i]);
			}
			else if (netStartRpt.getVehicleTypeRd().contains("F") && (i>4 && i<13))
				cell.setCellValue(colNames[i]);
			else if (netStartRpt.getVehicleTypeRd().contains("P") && (i>4 && i<17))
				cell.setCellValue(colNames[i+8]);
			else if (netStartRpt.getVehicleTypeRd().contains("S") && (i>4 && i<9))
				cell.setCellValue(colNames[i+20]);
			else if (netStartRpt.getVehicleTypeRd().contains("V") && (i>4 && i<13))
				cell.setCellValue(colNames[i+24]);
		}
		
		for (int i=0; i < list.size(); i++) {
			for (int j=0; j < list.get(i).size(); j++) {
				if( i >= 1){
					row = sheet.createRow(list.get(0).size()+(j+1));
				}
				else
					row = sheet.createRow(j+1);
				if(netStartRpt.getVehicleTypeRd().contains("S"))
				{
					for(int k=0; k < list.get(i).get(j).size()-1; k++){
						cell = row.createCell(k);
						if(k==6)
						{
							cell.setCellValue(checkNegativeForExcel(list.get(i).get(j).get(8)));
						}
						else if(k==7){
							cell.setCellValue(checkNegativeForExcel(list.get(i).get(j).get(7)));
						}
						else
						cell.setCellValue(checkNegativeForExcel(list.get(i).get(j).get(k+1)));
						
					}
				}
				else{
				for(int k=0; k < list.get(i).get(j).size()-1; k++){
					cell = row.createCell(k);
					cell.setCellValue(checkNegativeForExcel(list.get(i).get(j).get(k+1)));
					
				}
				}
				cell = row.createCell(list.get(i).get(j).size()-1);
				if(netStartRpt.getVehicleTypeRd().contains("F"))
				{

					unearnedCustExp = getFormattedBigDecimal(list.get(i).get(j).get(7));
					unearnedBrdStd = getFormattedBigDecimal(list.get(i).get(j).get(9));
					unearnedSalesBonus = getFormattedBigDecimal(list.get(i).get(j).get(11));
					unearnedTotal = unearnedCustExp.add(unearnedBrdStd.add(unearnedSalesBonus));
					cell.setCellValue(checkNegativeForExcel(unearnedTotal+""));
				}
				else if(netStartRpt.getVehicleTypeRd().contains("V")){
					unearnedCustExp = getFormattedBigDecimal(list.get(i).get(j).get(7));
					unearnedBrdStd = getFormattedBigDecimal(list.get(i).get(j).get(9));
					unearnedSalesBonus = getFormattedBigDecimal(list.get(i).get(j).get(11));
					unearnedTotal = unearnedCustExp.add(unearnedBrdStd.add(unearnedSalesBonus));
					cell.setCellValue(checkNegativeForExcel(unearnedTotal+""));
				}
				else if(netStartRpt.getVehicleTypeRd().contains("S"))
				{
					cell.setCellValue(checkNegativeForExcel(list.get(i).get(j).get(8)));
				}
				else if(netStartRpt.getVehicleTypeRd().contains("P"))
				{
					unearnedCustExpSales = getFormattedBigDecimal(list.get(i).get(j).get(7));
					unearnedCustExpService = getFormattedBigDecimal(list.get(i).get(j).get(9));
					unearnedPoSales = getFormattedBigDecimal(list.get(i).get(j).get(11));
					unearnedNvSales = getFormattedBigDecimal(list.get(i).get(j).get(13));
					unearnedBrdStd = getFormattedBigDecimal(list.get(i).get(j).get(15));
					unearnedTotal = unearnedCustExpSales.add(unearnedCustExpService.add(unearnedPoSales.add(unearnedNvSales.add(unearnedBrdStd))));
					cell.setCellValue(checkNegativeForExcel(unearnedTotal+""));
				}

			} 
		}
		return workbook1;
		
		}
    
public XSSFWorkbook unearnedBonusExcelOld(XSSFWorkbook workbook,String colNames[],List<List<List<String>>> list,NetStarReport netStartRpt){
		
    	XSSFSheet sheet = workbook.createSheet("new sheet");
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(0);
    	
    	for (int i=0; i < colNames.length; i++) {
			cell = row.createCell(i);
			if (i<4) 
			cell.setCellValue(colNames[i]);
			//else if (netStartRpt.getVehicleTypeRd().contains("F") && (i>4 && i<12))
			else if (netStartRpt.getVehicleTypeRd().contains("P") && (i>3 && i<14))
				cell.setCellValue(colNames[i]);
			//else if (netStartRpt.getVehicleTypeRd().contains("P") && (i>4 && i<16))
			else if (netStartRpt.getVehicleTypeRd().contains("S") && (i>3 && i<12))
				cell.setCellValue(colNames[i+10]);
			//else if (netStartRpt.getVehicleTypeRd().contains("S") && (i>4 && i<8))
			else if (netStartRpt.getVehicleTypeRd().contains("V") && (i>3 && i<12))
				cell.setCellValue(colNames[i+18]);
			//else if (netStartRpt.getVehicleTypeRd().contains("V") && (i>4 && i<12))
			else if (netStartRpt.getVehicleTypeRd().contains("F") && (i>3 && i<12))
				cell.setCellValue(colNames[i+26]);
		}
		
		for (int i=0; i < list.size(); i++) {
			for (int j=0; j < list.get(i).size(); j++) {
				if( i >= 1){
					row = sheet.createRow(list.get(0).size()+(j+1));
				}
				else
					row = sheet.createRow(j+1);
				for(int k=0; k < list.get(i).get(j).size(); k++){
					cell = row.createCell(k);
					if (k<4) 
					cell.setCellValue(list.get(i).get(j).get(k));
					else if (netStartRpt.getVehicleTypeRd().contains("P") && (k>3 && k<14))
						cell.setCellValue(list.get(i).get(j).get(k));
					else if (netStartRpt.getVehicleTypeRd().contains("S") && (k>3 && k<12))
						cell.setCellValue(list.get(i).get(j).get(k+10));
					else if (netStartRpt.getVehicleTypeRd().contains("V") && (k>3 && k<12))
						cell.setCellValue(list.get(i).get(j).get(k+18));
					else if (netStartRpt.getVehicleTypeRd().contains("F") && (k>3 && k<12))
						cell.setCellValue(list.get(i).get(j).get(k+26));
				}
			} 
		}
		return workbook;
	}

	//Exception Report-Start
	public XSSFWorkbook flrPlanExceptionExcel(XSSFWorkbook workbook,List<List<List<String>>> list){
	
		String flrPlan_colNames[] = {"S.No","Retail Date","MY","Model","SERIAL","PO NUMBER","DEALER","REGION","CAR COUNT","REASON"};
		
		XSSFSheet sheet = workbook.createSheet("new sheet");
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		
		XSSFCellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		//font.setBold(true);
		style.setFont(font);
		
		for (int i=0; i < flrPlan_colNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(flrPlan_colNames[i]);
		}
		
		for (int i=0; i < list.size(); i++) {
			for (int j=0; j < list.get(i).size(); j++) {
				if( i >= 1){
					row = sheet.createRow(list.get(0).size()+(j+1));
					
					cell = row.createCell(8);
					cell.setCellStyle(style);
					cell.setCellValue(list.get(i).get(j).get(0));
					
					cell = row.createCell(9);
					cell.setCellStyle(style);
					cell.setCellValue(checkNegative(list.get(i).get(j).get(1)));					
				}
				else
					row = sheet.createRow(j+1);
				
				for(int k=0; k < list.get(i).get(j).size(); k++){
					if (i>=1)
						continue;
					cell = row.createCell(k);
					cell.setCellValue(checkNegative(list.get(i).get(j).get(k)));
				}
			}
		}
	return workbook;
	}	
	//Exception Report-End
	
	public XSSFWorkbook dlrReserveExcel(XSSFWorkbook workbook,List<List<List<String>>> list){
		
		
		XSSFCellStyle style1 = workbook.createCellStyle();
		XSSFFont font1 = workbook.createFont();
		font1.setFontName(HSSFFont.FONT_ARIAL);
		//font1.setBold(true);
		style1.setFont(font1);
		style1.setAlignment(CellStyle.ALIGN_CENTER);
		
		
		XSSFCellStyle style2 = workbook.createCellStyle();
		XSSFFont font2 = workbook.createFont();
		font2.setFontName(HSSFFont.FONT_ARIAL);
		//font2.setBold(true);
		style2.setFont(font2);
		
		XSSFSheet sheet = workbook.createSheet("new sheet");
		XSSFRow row = sheet.createRow(0);
		row.setRowStyle(style1);
		XSSFCell cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("Programs");
		
		sheet.addMergedRegion(new CellRangeAddress(0,0,1,2));
		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell.setCellValue("492343");
		
		sheet.addMergedRegion(new CellRangeAddress(0,0,3,4));
		cell = row.createCell(3);
		cell.setCellStyle(style1);
		cell.setCellValue("Sub Total");
		
		row = sheet.createRow(1);
		row.setRowStyle(style2);
		sheet.addMergedRegion(new CellRangeAddress(1,1,0,4));
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		if ( netStartRpt.getRptId() == 7)
			cell.setCellValue("RETAILED");
		else 
			cell.setCellValue("ACCRUED");
		
		row = sheet.createRow(10);
		row.setRowStyle(style2);
		sheet.addMergedRegion(new CellRangeAddress(10,10,0,4));
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("PAYMENTS");
		
		row = sheet.createRow(13);
		row.setRowStyle(style2);
		sheet.addMergedRegion(new CellRangeAddress(13,13,0,4));
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("OWED");
		
		for (int i=0; i < list.size(); i++) {
			for (int j=0; j < list.get(i).size(); j++) {
				
				if( i == 1){
					
					row = sheet.createRow(list.get(0).size()+(j+3));
					sheet.addMergedRegion(new CellRangeAddress(list.get(0).size()+(j+3),list.get(0).size()+(j+3),0,3));
					cell = row.createCell(0);
					cell.setCellValue(list.get(i).get(j).get(0));
					
					cell = row.createCell(4);
					cell.setCellValue(list.get(i).get(j).get(1));
					
				}
				else if( i == 2){
					row = sheet.createRow(list.get(0).size()+list.get(1).size()+(j+4));
					
					sheet.addMergedRegion(new CellRangeAddress(list.get(0).size()+list.get(1).size()+(j+4),list.get(0).size()+list.get(1).size()+(j+4),0,3));
					cell = row.createCell(0);
					cell.setCellValue(list.get(i).get(j).get(1));
					
					cell = row.createCell(4);
					cell.setCellValue(list.get(i).get(j).get(2));
					
				}
				else
					row = sheet.createRow(j+2);
				
				for(int k=0; k < list.get(i).get(j).size()-1; k++){
					if (i>=1)
						continue;
					cell = row.createCell(k);
					cell.setCellValue(list.get(i).get(j).get(k+1));
				}
			}
		}
		return workbook;
		
	}
	
	public HSSFWorkbook flrPlanSummaryExcel(HSSFWorkbook workbook,String cvp_colNames[],List<List<List<String>>> list){
		
		return workbook;
	}
	
	//Vehicle Details Report-start
	public SXSSFWorkbook vehDetailsBlockedOrAllExcel(XSSFWorkbook inWorkbook,List<List<List<String>>> list){
		
		String vehicle_colNames[] ={"Date Retail","ID_DLR","NUM_PO","NUM_VIN","CDE_VEH_STS",
									"CDE_USE_VEH","IND_USED_VEH","CDE_VEH_DDR_STS","CDE_VEH_DDR_USE",
									"IND_USED_VEH_DDRS","TME_RTL","ID_EMP_PUR_CTRL","DTE_MODL_YR","DES_MODL",
									"CDE_RGN","NAM_RTL_CUS_LST","NAM_RTL_CUS_FIR","NAM_RTL_CUS_MID","DTE_TRANS",
									"TME_TRANS","IND_FLT","CDE_WHSLE_INIT_TYP","CDE_NATL_TYPE","DTE_VEH_DEMO_SRV",
									"CDE_VEH_TYP","AMT_MSRP_BASE","AMT_MSRP_TOT_ACSRY","AMT_DLR_RBT","AMT_MSRP","BLOCKED_DATE","BLOCKED_REASON"};
																																			
		XSSFSheet tempSheet = inWorkbook.createSheet("Report");
		SXSSFWorkbook workbook = new SXSSFWorkbook(inWorkbook);
		workbook.setCompressTempFiles(true);
		SXSSFSheet sheet = (SXSSFSheet) workbook.getSheetAt(0);
		// keep 500 rows in memory, exceeding rows will be flushed to disk
		sheet.setRandomAccessWindowSize(500);
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		
		CellStyle style =  workbook.createCellStyle();
		Font font =  workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		//font.setBold(true);
		style.setFont(font);
		
		for (int i=0; i < vehicle_colNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(vehicle_colNames[i]);
		}
		
		for (int i=0; i < list.size(); i++) {
			for (int j=0; j < list.get(i).size(); j++) {
				if( i >= 1){
					row = sheet.createRow(list.get(0).size()+(j+1));
					//sheet.addMergedRegion(new CellRangeAddress(list.get(0).size()+(j+1),list.get(0).size()+(j+1),0,24));
					cell = row.createCell(24);
					style.setAlignment(CellStyle.ALIGN_RIGHT);
					cell.setCellStyle(style);
					cell.setCellValue(list.get(i).get(j).get(0));
					if(netStartRpt.getDataTypeRadio().startsWith("A")){
						
						amtMsrpBase=new BigDecimal((list.get(1).get(0).get(1)).trim()).add(new BigDecimal((list.get(2).get(0).get(1)).trim()));
						amtMsrpTotaCsry=new BigDecimal((list.get(1).get(0).get(2)).trim()).add(new BigDecimal((list.get(2).get(0).get(2)).trim()));
						amtDlrRbt=new BigDecimal((list.get(1).get(0).get(3)).trim()).add(new BigDecimal((list.get(2).get(0).get(3)).trim()));
						amtMsrp=new BigDecimal((list.get(1).get(0).get(4)).trim()).add(new BigDecimal((list.get(2).get(0).get(4)).trim()));
						cell = row.createCell(25);
						cell.setCellStyle(style);
						cell.setCellValue(amtMsrpBase+"");
						cell = row.createCell(26);
						cell.setCellStyle(style);
						cell.setCellValue(amtMsrpTotaCsry+"");
						cell = row.createCell(27);
						cell.setCellStyle(style);
						cell.setCellValue(amtDlrRbt+"");
						cell = row.createCell(28);
						cell.setCellStyle(style);
						cell.setCellValue(amtMsrp+"");
					}
					else if(netStartRpt.getDataTypeRadio().startsWith("B")){
					cell = row.createCell(25);
					cell.setCellStyle(style);
					cell.setCellValue(list.get(i).get(j).get(1));
					cell = row.createCell(26);
					cell.setCellStyle(style);
					cell.setCellValue(list.get(i).get(j).get(2));
					cell = row.createCell(27);
					cell.setCellStyle(style);
					cell.setCellValue(list.get(i).get(j).get(3));
					cell = row.createCell(28);
					cell.setCellStyle(style);
					cell.setCellValue(list.get(i).get(j).get(4));
					}
				}
				else
					row = sheet.createRow(j+1);
				for(int k=0; k < list.get(i).get(j).size(); k++){
					if (i >= 1){
						continue;
					}
					else
						cell = row.createCell(k);
						cell.setCellValue(list.get(i).get(j).get(k));
				}
			}
		}
		return workbook;
	}
	//Vehicle Details Report-end
	
	
	public List<ReportDefinitionForm> getFormList() {
		return formList;
	}

	public void setFormList(List<ReportDefinitionForm> formList) {
		this.formList = formList;
	}
	
	public ReportDefinitionBean getRptDef() {
		return rptDef;
	}
	
	public void setRptDef(ReportDefinitionBean rptDef) {
		this.rptDef = rptDef;
	}
	
	public List<ReportDefinitionBean> getBeanList() {
		return beanList;
	}
	
	public void setBeanList(List<ReportDefinitionBean> beanList) {
		this.beanList = beanList;
	}
	
	public List<VehicleType> getVehicleList() {
		return vehicleList;
	}
	
	public void setVehicleList(List<VehicleType> vehicleList) {
		this.vehicleList = vehicleList;
	}

	public NetStarReport getNetStartRpt() {
		return netStartRpt;
	}

	public void setNetStartRpt(NetStarReport netStartRpt) {
		this.netStartRpt = netStartRpt;
	}
	
	public List<List<List<String>>> getList() {
		return list;
	}

	public void setList(List<List<List<String>>> list) {
		this.list = list;
	}

	public List<BlockedVehicle> getBlkList() {
		return blkList;
	}

	public void setBlkList(List<BlockedVehicle> blkList) {
		this.blkList = blkList;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public List<BlockedVehicle> getBlockedList() {
		return blockedList;
	}

	
	public void setBlockedList(List<BlockedVehicle> blockedList) {
		this.blockedList = blockedList;
	}

	public boolean isShowResultMsg() {
		return showResultMsg;
	}

	public void setShowResultMsg(boolean showResultMsg) {
		this.showResultMsg = showResultMsg;
	}

	public boolean isReportGenerated() {
		return reportGenerated;
	}

	public void setReportGenerated(boolean reportGenerated) {
		this.reportGenerated = reportGenerated;
	}

	public boolean isReportGenerationInProcess() {
		return reportGenerationInProcess;
	}

	public void setReportGenerationInProcess(boolean reportGenerationInProcess) {
		this.reportGenerationInProcess = reportGenerationInProcess;
	}

	public List<DealerVehicleReport> getVehicleTypeList() {
		return vehicleTypeList;
	}

	public void setVehicleTypeList(List<DealerVehicleReport> vehicleTypeList) {
		this.vehicleTypeList = vehicleTypeList;
	}

	public List<DealerBonusVehReport> getVehicleCond() {
		return vehicleCond;
	}

	public void setVehicleCond(List<DealerBonusVehReport> vehicleCond) {
		this.vehicleCond = vehicleCond;
	}

	public List<DealerBonusVehReport> getTotVehList() {
		return totVehList;
	}

	public void setTotVehList(List<DealerBonusVehReport> totVehList) {
		this.totVehList = totVehList;
	}

	public boolean isDraftrptFlag() {
		return draftrptFlag;
	}

	public void setDraftrptFlag(boolean draftrptFlag) {
		this.draftrptFlag = draftrptFlag;
	}

	
	public boolean isDraftrptSmartFlag() {
		return draftrptSmartFlag;
	}

	public void setDraftrptSmartFlag(boolean draftrptSmartFlag) {
		this.draftrptSmartFlag = draftrptSmartFlag;
	}

	public List<VehicleType> getVehicleListRd() {
		return vehicleListRd;
	}

	public void setVehicleListRd(List<VehicleType> vehicleListRd) {
		this.vehicleListRd = vehicleListRd;
	}

	public List<VehicleType> getVehListRd() {
		return vehListRd;
	}

	public void setVehListRd(List<VehicleType> vehListRd) {
		this.vehListRd = vehListRd;
	}
	
	
	public String exportBlockVehicleReport(){
		
		final String methodName = "generateBlockVehicleReport";
		LOGGER.enter(CLASSNAME, methodName);
		String blocked_colNames[] ={"SR No.","Dealer Id","Purchase order","Block Date","Retail Date","VIN","Blocked Reason"};
		
		try{
			actionForward = "exportblckVehRpt";
			java.sql.Date fdate = WebHelper.convertStringToDate(netStartRpt.getFromDate());
			java.sql.Date tDate = WebHelper.convertStringToDate(netStartRpt.getToDate());
			String vId="";
	
			boolean isWhitespace =netStartRpt.getVehicleId().matches("^\\s*$");
			if(netStartRpt.getVehicleId().indexOf(",") > - 1)
			{
				String arr[] = netStartRpt.getVehicleId().split(",");
				for(String vins :  arr)
				{
					vId=vId+""+vins;
					vId=vId.trim();
					if(!vId.equals(""))
					{
						vId=vId+",";
					}
				}
				if( vId.endsWith(",")== true )
				{
					vId=vId.substring(0,vId.lastIndexOf(","));
				}
				else
				{
					netStartRpt.setVehicleId(vId);
				}
				if(arr.length == 0)
				{
					addActionError("Enter Valid VIN(s).");
				}
			
			}
			
			if(!hasActionErrors())
			{
					if(isWhitespace)
					{
						netStartRpt.setVehicleId(netStartRpt.getVehicleId().replaceAll("\\s",""));
					}
			
					if((netStartRpt.getVehicleId() !=null && !netStartRpt.getVehicleId().equals("")) && netStartRpt.getVehicleId().startsWith(",")== true  )
					{
						vId=this.getVehicleId().substring(1,this.getVehicleId().length());
					}
					else if((netStartRpt.getVehicleId() !=null && !netStartRpt.getVehicleId().equals("")) && netStartRpt.getVehicleId().endsWith(",")== true )
					{
						vId=netStartRpt.getVehicleId().substring(0,netStartRpt.getVehicleId().lastIndexOf(","));
					}
					else if( netStartRpt.getVehicleId() !=null && !netStartRpt.getVehicleId().equals(""))
					{
						vId=netStartRpt.getVehicleId().trim();
					}
					blockedList = rDelegate.getBlockVehicleReport(netStartRpt.getDealerId(),vId,fdate,tDate);
					
					if(blockedList.isEmpty())
					{
						setReportGenerated(false);
					}
					for(BlockedVehicle bVeh:blockedList){
						
						bVeh.setDisplayDate(WebHelper.formatDate(bVeh.getUpdatedDate()));
						bVeh.setDisplayetailDate(WebHelper.formatDate(bVeh.getRetailDate()));
						bVeh.setPoNo(bVeh.getPoNumber());
						bVeh.setReason(bVeh.getTxtBlckReason());
						blkList.add(bVeh);
					}
					
			}	
					
					XSSFWorkbook workbook = new XSSFWorkbook(); 
					XSSFSheet sheet = workbook.createSheet("new sheet");
					XSSFRow row = sheet.createRow(0);
					XSSFCell cell = row.createCell(0);
					
					for (int i=0; i < blocked_colNames.length; i++) {
						cell = row.createCell(i);
						cell.setCellValue(blocked_colNames[i]);
					}
					
					int i= 1,j=1;
					for(BlockedVehicle bVeh:blockedList){
							row = sheet.createRow(i);
							cell = row.createCell(0);
							cell.setCellValue(j);
							cell = row.createCell(1);
							cell.setCellValue(bVeh.getIdDealer());
							cell = row.createCell(2);
							cell.setCellValue(bVeh.getPoNumber());
							cell = row.createCell(3);
							cell.setCellValue(bVeh.getDisplayDate());
							cell = row.createCell(4);
							cell.setCellValue(bVeh.getDisplayetailDate());
							cell = row.createCell(5);
							cell.setCellValue(bVeh.getVinNo());
							cell = row.createCell(6);
							cell.setCellValue(bVeh.getTxtBlckReason());
							i++;
							j++;
					}
					getExcelReport(workbook);
					
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return actionForward;
	}

	//Dealer Compliance Summary Report - Start
		private void fillDlrCompSummList(List<List<String>> resultList) {
			/*List<List<String>> resultList = list.get(0);
			int qtr = DPBCommonHelper.calculateQyarter(netStartRpt.getMonth());
			String qtrName = "Q"+qtr;
			setQtrName(qtrName);
			String monthName= DPBCommonHelper.getMonthName(netStartRpt.getMonth());
			setMonthName(monthName);*/
			BigDecimal poCountTotal = new BigDecimal(0);
			BigDecimal custExpTotal = new BigDecimal(0);
			BigDecimal custExpSalesTot = new BigDecimal(0);
			BigDecimal custExpServiceTot = new BigDecimal(0);
			BigDecimal nvSalesTotal = new BigDecimal(0);
			BigDecimal preOwnedTotal = new BigDecimal(0);
			BigDecimal brdStdTotal = new BigDecimal(0);
			BigDecimal unearnedBonusTotal = new BigDecimal(0);
			
			BigDecimal poCountTotalQtr = new BigDecimal(0);
			BigDecimal custExpTotalQtr = new BigDecimal(0);
			BigDecimal custExpSalesTotQtr = new BigDecimal(0);
			BigDecimal custExpServiceTotQtr = new BigDecimal(0);
			BigDecimal nvSalesTotalQtr = new BigDecimal(0);
			BigDecimal preOwnedTotalQtr = new BigDecimal(0);
			BigDecimal brdStdTotalQtr = new BigDecimal(0);
			BigDecimal unearnedBonusTotalQtr = new BigDecimal(0);
			
			DlrCompSummForm dlrCompSummFormCVPMonthly = null;
			DlrCompSummForm dlrCompSummFormCVPQtrly = null;
			int cvpIndexMonthly = 0;
			int countMonthly = 0;
		int cvpIndexQtrly = 0;
		int countQtrly = 0;
			
		LinkedHashMap<String,DlrCompSummForm> qtrlyDCSRMap = new LinkedHashMap<String,DlrCompSummForm>();
			
			for(List<String> l:resultList)
			{
				
				if(l.get(3) != null &&  !l.get(3).equalsIgnoreCase(""))
				{
				DlrCompSummForm dlrCompSummForm = new DlrCompSummForm();
				//write method
				dlrCompSummForm.setSeq(getFormattedInteger(l.get(0)));
				dlrCompSummForm.setBonusType(l.get(2));
				
				
				
				BigDecimal poCount = getFormattedBigDecimal(l.get(9));
				dlrCompSummForm.setPoCount(String.valueOf(poCount));
				
//				BigDecimal custExp = getFormattedBigDecimal(l.get(3));
//				dlrCompSummForm.setCustExp(String.valueOf(custExp));
				
				BigDecimal custExpSales = getFormattedBigDecimal(l.get(3));
				dlrCompSummForm.setCustExpSales(String.valueOf(custExpSales));
				
				BigDecimal custExpService = getFormattedBigDecimal(l.get(4));
				dlrCompSummForm.setCustExpService(String.valueOf(custExpService));
				
				BigDecimal nvSales = getFormattedBigDecimal(l.get(5));
				dlrCompSummForm.setNvSales(String.valueOf(nvSales));
				
				BigDecimal preOwned = getFormattedBigDecimal(l.get(6));
				dlrCompSummForm.setPreOwned(String.valueOf(preOwned));
				
				BigDecimal brdStd = getFormattedBigDecimal(l.get(7));
				dlrCompSummForm.setBrdStd(String.valueOf(brdStd));
				
				dlrCompSummForm.setTotal(custExpSales.add(custExpService).add(nvSales).add(preOwned).add(brdStd)+"");
				
				BigDecimal unearnedBonus = getFormattedBigDecimal(l.get(8));
				dlrCompSummForm.setUnearnedBonus(String.valueOf(unearnedBonus));
//				decreased index by 1
				
				
				if(l.get(1).equalsIgnoreCase("M"))
				{
					
					if((dlrCompSummForm.getBonusType().equalsIgnoreCase("CVP")))
					{
						cvpIndexMonthly = countMonthly;
						dlrCompSummFormCVPMonthly = dlrCompSummForm;
					}
					countMonthly++;
					if(dlrCompSummForm.getBonusType().equalsIgnoreCase("Retails") || dlrCompSummForm.getBonusType().equalsIgnoreCase("MBDEAL")||dlrCompSummForm.getBonusType().equalsIgnoreCase("CVP") ){
						poCountTotal = poCountTotal.add(poCount);
					}
//						custExpTotal = custExpTotal.add(custExp);
						custExpSalesTot = custExpSalesTot.add(custExpSales);
						custExpServiceTot = custExpServiceTot.add(custExpService);
						nvSalesTotal = nvSalesTotal.add(nvSales);
						preOwnedTotal = preOwnedTotal.add(preOwned);
						brdStdTotal = brdStdTotal.add(brdStd);
						unearnedBonusTotal = unearnedBonusTotal.add(unearnedBonus);
					
					
					monthlyDCSRList.add(dlrCompSummForm);
					
				}
				
				else if(!netStartRpt.isQtrStart() && l.get(1).equalsIgnoreCase("Q"))
				{
					
					/*if((dlrCompSummForm.getBonusType().equalsIgnoreCase("CVP")))
					{
						cvpIndexQtrly = countQtrly;
						dlrCompSummFormCVPQtrly = dlrCompSummForm;
					}
					countQtrly++;
					poCountTotalQtr = poCountTotalQtr.add(poCount);
					custExpTotalQtr = custExpTotalQtr.add(custExp);
					custExpSalesTotQtr = custExpSalesTotQtr.add(custExpSales);
					custExpServiceTotQtr = custExpServiceTotQtr.add(custExpService);
					nvSalesTotalQtr = nvSalesTotalQtr.add(nvSales);
					preOwnedTotalQtr = preOwnedTotalQtr.add(preOwned);
					brdStdTotalQtr = brdStdTotalQtr.add(brdStd);
					unearnedBonusTotalQtr = unearnedBonusTotalQtr.add(unearnedBonus);*/
					qtrlyDCSRMap.put(dlrCompSummForm.getBonusType(), dlrCompSummForm);
					//qtrlyDCSRList.add(dlrCompSummForm);
					
				}
				
				}
				//Algo -
				//1. check object at 0th index if it is retail/mbdeal
				//2. check object at 1st index if it is retail/mbdeal
				//3.
			
			/*unearnedForm.setIdDlr(l.get(2));
			unearnedForm.setNumPo(l.get(3));
			unearnedForm.setDesModl(l.get(4));*/
			}
			if(!netStartRpt.isQtrStart())
			{
				updateQuarterlyList(qtrlyDCSRMap);
				for(DlrCompSummForm q:qtrlyDCSRList)
				{if((q.getBonusType().equalsIgnoreCase("CVP")))
				{
					cvpIndexQtrly = countQtrly;
					dlrCompSummFormCVPQtrly = q;
				}
				countQtrly++;
				if(q.getBonusType().equalsIgnoreCase("Retails") || q.getBonusType().equalsIgnoreCase("MBDEAL")||q.getBonusType().equalsIgnoreCase("CVP") ){
					poCountTotalQtr = poCountTotalQtr.add(getFormattedBigDecimal(q.getPoCount()));
				}
					custExpTotalQtr = custExpTotalQtr.add(getFormattedBigDecimal(q.getCustExp()));
					custExpSalesTotQtr = custExpSalesTotQtr.add(getFormattedBigDecimal(q.getCustExpSales()));
					custExpServiceTotQtr = custExpServiceTotQtr.add(getFormattedBigDecimal(q.getCustExpService()));
					nvSalesTotalQtr = nvSalesTotalQtr.add(getFormattedBigDecimal(q.getNvSales()));
					preOwnedTotalQtr = preOwnedTotalQtr.add(getFormattedBigDecimal(q.getPreOwned()));
					brdStdTotalQtr = brdStdTotalQtr.add(getFormattedBigDecimal(q.getBrdStd()));
					unearnedBonusTotalQtr = unearnedBonusTotalQtr.add(getFormattedBigDecimal(q.getUnearnedBonus()));
				
				
			}
			
			}
			
			
			//setTotals
			if(monthlyDCSRList != null && !monthlyDCSRList.isEmpty())
			{
				DlrCompSummForm dlrCompSummForm1 = new DlrCompSummForm();

				
				dlrCompSummForm1.setBonusType("Total");
				
				dlrCompSummForm1.setPoCount(poCountTotal+"");
//				dlrCompSummForm1.setCustExp(custExpTotal+"");
				dlrCompSummForm1.setCustExpSales(custExpSalesTot+"");
				dlrCompSummForm1.setCustExpService(custExpServiceTot+"");
				dlrCompSummForm1.setNvSales(nvSalesTotal+"");
				dlrCompSummForm1.setPreOwned(preOwnedTotal+"");
				dlrCompSummForm1.setBrdStd(brdStdTotal+"");
				dlrCompSummForm1.setTotal(custExpTotal.add(custExpSalesTot).add(custExpServiceTot).add(nvSalesTotal).add(preOwnedTotal).add(brdStdTotal)+"");
				
				dlrCompSummForm1.setUnearnedBonus(unearnedBonusTotal+"");
				monthlyDCSRList.add(dlrCompSummForm1);
				updateDCSRListWithSubTotal(monthlyDCSRList,dlrCompSummFormCVPMonthly,cvpIndexMonthly);
			}
					
			if(qtrlyDCSRList != null && !qtrlyDCSRList.isEmpty())
			{
				if(!netStartRpt.isQtrStart())		
				{
		DlrCompSummForm dlrCompSummForm2 = new DlrCompSummForm();

						
		dlrCompSummForm2.setBonusType("Total");
						
		dlrCompSummForm2.setPoCount(poCountTotalQtr+"");
		dlrCompSummForm2.setCustExp(custExpTotalQtr+"");
		dlrCompSummForm2.setCustExpSales(custExpSalesTotQtr+"");
		dlrCompSummForm2.setCustExpService(custExpServiceTotQtr+"");
		dlrCompSummForm2.setNvSales(nvSalesTotalQtr+"");
		dlrCompSummForm2.setPreOwned(preOwnedTotalQtr+"");
		dlrCompSummForm2.setBrdStd(brdStdTotalQtr+"");
		dlrCompSummForm2.setTotal(custExpTotalQtr.add(custExpSalesTotQtr).add(custExpServiceTotQtr).add(nvSalesTotalQtr).add(preOwnedTotalQtr).add(brdStdTotalQtr)+"");
						
		dlrCompSummForm2.setUnearnedBonus(unearnedBonusTotalQtr+"");
		qtrlyDCSRList.add(dlrCompSummForm2);
		updateDCSRListWithSubTotal(qtrlyDCSRList,dlrCompSummFormCVPQtrly,cvpIndexQtrly);
		//qtrlyDCSRMap.put(dlrCompSummForm2.getBonusType(), dlrCompSummForm2);
				}
			}
			
			
			
			//updateQuarterlyList
			
			
			/*List<Item> list;
			Map<Key,Item> map = new HashMap<Key,Item>();
			for (Item i : list) map.put(i.getKey(),i);
			*/
			
			
			
			
			
			//monthlyDCSRList
			//list to map
			//for(monthlyDCSRList)
			if(netStartRpt.isQtrStart())
			{
				qtrlyDCSRList.addAll(monthlyDCSRList);
			}
			setMonthlyDCSRList(monthlyDCSRList);
			setQtrlyDCSRList(qtrlyDCSRList);
			
		}
		private void updateQuarterlyList(
				LinkedHashMap<String, DlrCompSummForm> qtrlyDCSRMap) {
			//Iterate over monthlylist
			for (DlrCompSummForm m : monthlyDCSRList) {
				//check bonus type
				String bonusType = m.getBonusType();
				DlrCompSummForm q = qtrlyDCSRMap.get(bonusType);
				if(q != null)
				{
					//do addition
					if(netStartRpt.getVehicleTypeRd().contains("P"))
					{
					BigDecimal custExpSales1= getFormattedBigDecimal(m.getCustExpSales());
					BigDecimal custExpSales2= getFormattedBigDecimal(q.getCustExpSales());
					BigDecimal custExpSales = custExpSales1.add(custExpSales2);
					
						q.setCustExpSales(custExpSales+"");
						BigDecimal custExpService1= getFormattedBigDecimal(m.getCustExpService());
						BigDecimal custExpService2= getFormattedBigDecimal(q.getCustExpService());
						BigDecimal custExpService = custExpService1.add(custExpService2);
						q.setCustExpService(custExpService+"");
						BigDecimal preOwned1= getFormattedBigDecimal(m.getPreOwned());
						BigDecimal preOwned2= getFormattedBigDecimal(q.getPreOwned());
						BigDecimal preOwned = preOwned1.add(preOwned2);
						q.setPreOwned(preOwned+"");
						}
					if(netStartRpt.getVehicleTypeRd().contains("S"))
					{
						/*BigDecimal smFran1= getFormattedBigDecimal(m.getSmFran());
						BigDecimal smFran2= getFormattedBigDecimal(q.getSmFran());
						BigDecimal smFran = smFran1.add(smFran2);
						q.setSmFran(smFran+"");*/
						BigDecimal brdStd1= getFormattedBigDecimal(m.getBrdStd());
						BigDecimal brdStd2= getFormattedBigDecimal(q.getBrdStd());
						BigDecimal brdStd = brdStd1.add(brdStd2);
						q.setBrdStd(brdStd+"");
						
					}
					else{
					BigDecimal custExp1= getFormattedBigDecimal(m.getCustExp());
					BigDecimal custExp2= getFormattedBigDecimal(q.getCustExp());
					BigDecimal custExp = custExp1.add(custExp2);
					q.setCustExp(custExp+"");
					BigDecimal brdStd1= getFormattedBigDecimal(m.getBrdStd());
					BigDecimal brdStd2= getFormattedBigDecimal(q.getBrdStd());
					BigDecimal brdStd = brdStd1.add(brdStd2);
					q.setBrdStd(brdStd+"");
					BigDecimal nvSales1= getFormattedBigDecimal(m.getNvSales());
					BigDecimal nvSales2= getFormattedBigDecimal(q.getNvSales());
					BigDecimal nvSales = nvSales1.add(nvSales2);
					q.setNvSales(nvSales+"");
					}
					BigDecimal pocount1= getFormattedBigDecimal(m.getPoCount());
					BigDecimal pocount2= getFormattedBigDecimal(q.getPoCount());
					BigDecimal pocount = pocount1.add(pocount2);
					q.setPoCount(pocount+"");
					BigDecimal unearnedBonus1= getFormattedBigDecimal(m.getUnearnedBonus());
					BigDecimal unearnedBonus2= getFormattedBigDecimal(q.getUnearnedBonus());
					BigDecimal unearnedBonus = unearnedBonus1.add(unearnedBonus2);
					q.setUnearnedBonus(unearnedBonus+"");
					BigDecimal total1= getFormattedBigDecimal(m.getTotal());
					BigDecimal total2= getFormattedBigDecimal(q.getTotal());
					BigDecimal total = total1.add(total2);
					q.setTotal(total+"");
					//qtrlyDCSRList.add(q);
				}
				//add element from monthlylist to quarterlymap as it is
				else
				{
					qtrlyDCSRMap.put(m.getBonusType(),m);
					//qtrlyDCSRMap.
					//qtrlyDCSRList.add(q);
				}
				
			}
			qtrlyDCSRList = new LinkedList<DlrCompSummForm>(qtrlyDCSRMap.values());
			 Collections.sort(qtrlyDCSRList, new Comparator<DlrCompSummForm>() {
		            public int compare(DlrCompSummForm o1, DlrCompSummForm o2) {
		                return o1.getSeq().compareTo(o2.getSeq());
		            }
		        });


			//qtrlyDCSRList = new LinkedList<DlrCompSummForm>(qtrlyDCSRMap.values());
			
			
			
		}

			//Ratna Start
			private void fillDlrCompSummListFtl(List<List<String>> resultList) {
			/*List<List<String>> resultList = list.get(0);
			int qtr = DPBCommonHelper.calculateQyarter(netStartRpt.getMonth());
			String qtrName = "Q"+qtr;
			setQtrName(qtrName);
			String monthName= DPBCommonHelper.getMonthName(netStartRpt.getMonth());
			setMonthName(monthName);*/
			BigDecimal poCountTotal = new BigDecimal(0);
			BigDecimal custExpTotal = new BigDecimal(0);
			BigDecimal custExpSalesTot = new BigDecimal(0);
			BigDecimal custExpServiceTot = new BigDecimal(0);
			BigDecimal nvSalesTotal = new BigDecimal(0);
			BigDecimal preOwnedTotal = new BigDecimal(0);
			BigDecimal brdStdTotal = new BigDecimal(0);
			BigDecimal unearnedBonusTotal = new BigDecimal(0);
			
			BigDecimal poCountTotalQtr = new BigDecimal(0);
			BigDecimal custExpTotalQtr = new BigDecimal(0);
			BigDecimal custExpSalesTotQtr = new BigDecimal(0);
			BigDecimal custExpServiceTotQtr = new BigDecimal(0);
			BigDecimal nvSalesTotalQtr = new BigDecimal(0);
			BigDecimal preOwnedTotalQtr = new BigDecimal(0);
			BigDecimal brdStdTotalQtr = new BigDecimal(0);
			BigDecimal unearnedBonusTotalQtr = new BigDecimal(0);
			BigDecimal poCount= new BigDecimal(0);
			BigDecimal custExp= new BigDecimal(0);
			BigDecimal nvSales= new BigDecimal(0);
			BigDecimal unearnedBonus= new BigDecimal(0);
			BigDecimal brdStd= new BigDecimal(0);
			DlrCompSummForm dlrCompSummFormCVPMonthly = null;
			DlrCompSummForm dlrCompSummFormCVPQtrly = null;
			int cvpIndexMonthly = 0;
			int countMonthly = 0;
			int cvpIndexQtrly = 0;
			int countQtrly = 0;
			LinkedHashMap<String,DlrCompSummForm> qtrlyDCSRMap = new LinkedHashMap<String,DlrCompSummForm>();
			
				
			for(List<String> l:resultList)
			{
				if(l.get(3) != null && l.get(3).length()>0 )//&& l.get(2)!="MBDEAL A"
				{
				DlrCompSummForm dlrCompSummForm = new DlrCompSummForm();
				dlrCompSummForm.setSeq(getFormattedInteger(l.get(0)));
				dlrCompSummForm.setBonusType(l.get(2));
				
				if (l.get(3)!=null && l.get(3).length()>0){
					custExp = getFormattedBigDecimal(l.get(3));
					dlrCompSummForm.setCustExp(String.valueOf(custExp));
				}
				
				if(l.get(4)!=null && l.get(4).length()>0){
					brdStd = getFormattedBigDecimal(l.get(4));
					dlrCompSummForm.setBrdStd(String.valueOf(brdStd));
				}
				
				if(l.get(5)!=null && l.get(5).length()>0){
				nvSales = getFormattedBigDecimal(l.get(5));
				dlrCompSummForm.setNvSales(String.valueOf(nvSales));
				}
				
				if(l.get(6)!=null && l.get(6).length()>0){
				unearnedBonus = getFormattedBigDecimal(l.get(6));
				dlrCompSummForm.setUnearnedBonus(String.valueOf(unearnedBonus));
				}
				
				if (l.get(7) != null && l.get(7).length()>0){
					poCount = getFormattedBigDecimal(l.get(7));
					dlrCompSummForm.setPoCount(String.valueOf(poCount));
				}
				
				dlrCompSummForm.setTotal(custExp.add(brdStd).add(nvSales)+"");
			
				if(l.get(1).equalsIgnoreCase("M"))
				{
					if((dlrCompSummForm.getBonusType().equalsIgnoreCase("CVP")))
					{
						cvpIndexMonthly = countMonthly;
						dlrCompSummFormCVPMonthly = dlrCompSummForm;
					}
					countMonthly++;
					if(dlrCompSummForm.getBonusType().equalsIgnoreCase("Retails") || dlrCompSummForm.getBonusType().equalsIgnoreCase("MBDEAL")||dlrCompSummForm.getBonusType().equalsIgnoreCase("CVP") ){
					poCountTotal = poCountTotal.add(poCount);
					}
					custExpTotal = custExpTotal.add(custExp);
					//custExpSalesTot = custExpSalesTot.add(custExpSales);
					//custExpServiceTot = custExpServiceTot.add(custExpService);
					nvSalesTotal = nvSalesTotal.add(nvSales);
					//preOwnedTotal = preOwnedTotal.add(preOwned);
					brdStdTotal = brdStdTotal.add(brdStd);
					
					unearnedBonusTotal = unearnedBonusTotal.add(unearnedBonus);
					monthlyDCSRList.add(dlrCompSummForm);
					
				}
				else if(l.get(1).equalsIgnoreCase("Q"))
				{
					/*if((dlrCompSummForm.getBonusType().equalsIgnoreCase("CVP")))
					{
						cvpIndexQtrly = countQtrly;
						dlrCompSummFormCVPQtrly = dlrCompSummForm;
					}
					countQtrly++;*/
					/*poCountTotalQtr = poCountTotalQtr.add(poCount);
					custExpTotalQtr = custExpTotalQtr.add(custExp);
					//custExpSalesTotQtr = custExpSalesTotQtr.add(custExpSales);
					//custExpServiceTotQtr = custExpServiceTotQtr.add(custExpService);
					nvSalesTotalQtr = nvSalesTotalQtr.add(nvSales);
					//preOwnedTotalQtr = preOwnedTotalQtr.add(preOwned);
					brdStdTotalQtr = brdStdTotalQtr.add(brdStd);
					
					unearnedBonusTotalQtr = unearnedBonusTotalQtr.add(unearnedBonus);*/
					//qtrlyDCSRList.add(dlrCompSummForm);
					qtrlyDCSRMap.put(dlrCompSummForm.getBonusType(), dlrCompSummForm);
						
				}
				}
				//Algo -
				//1. check object at 0th index if it is retail/mbdeal
				//2. check object at 1st index if it is retail/mbdeal
				//3.
			
			/*unearnedForm.setIdDlr(l.get(2));
			unearnedForm.setNumPo(l.get(3));
			unearnedForm.setDesModl(l.get(4));*/
			}
			if(!netStartRpt.isQtrStart())
			{
				updateQuarterlyList(qtrlyDCSRMap);	
				for(DlrCompSummForm q:qtrlyDCSRList)
				{
					if((q.getBonusType().equalsIgnoreCase("CVP")))
					{
						cvpIndexQtrly = countQtrly;
						dlrCompSummFormCVPQtrly = q;
						//break;
					}
					countQtrly++;
					if(q.getBonusType().equalsIgnoreCase("Retails") || q.getBonusType().equalsIgnoreCase("MBDEAL")||q.getBonusType().equalsIgnoreCase("CVP") ){
					poCountTotalQtr = poCountTotalQtr.add(getFormattedBigDecimal(q.getPoCount()));
					}
					custExpTotalQtr = custExpTotalQtr.add(getFormattedBigDecimal(q.getCustExp()));
					//custExpSalesTotQtr = custExpSalesTotQtr.add(custExpSales);
					//custExpServiceTotQtr = custExpServiceTotQtr.add(custExpService);
					nvSalesTotalQtr = nvSalesTotalQtr.add(getFormattedBigDecimal(q.getNvSales()));
					//preOwnedTotalQtr = preOwnedTotalQtr.add(preOwned);
					brdStdTotalQtr = brdStdTotalQtr.add(getFormattedBigDecimal(q.getBrdStd()));
					
					unearnedBonusTotalQtr = unearnedBonusTotalQtr.add(getFormattedBigDecimal(q.getUnearnedBonus()));
				}
			}
			
			
			
			
			
			
			//setTotals
			if(monthlyDCSRList != null && !monthlyDCSRList.isEmpty())
			{
					DlrCompSummForm dlrCompSummForm1 = new DlrCompSummForm();

					
					dlrCompSummForm1.setBonusType("Total");
					
					dlrCompSummForm1.setPoCount(poCountTotal+"");
					dlrCompSummForm1.setCustExp(custExpTotal+"");
					dlrCompSummForm1.setNvSales(nvSalesTotal+"");
					dlrCompSummForm1.setBrdStd(brdStdTotal+"");
					dlrCompSummForm1.setTotal(custExpTotal.add(nvSalesTotal).add(brdStdTotal)+"");
					dlrCompSummForm1.setUnearnedBonus(unearnedBonusTotal+"");
					monthlyDCSRList.add(dlrCompSummForm1);
					updateDCSRListWithSubTotal(monthlyDCSRList,dlrCompSummFormCVPMonthly,cvpIndexMonthly);
			}
			if(qtrlyDCSRList != null && !qtrlyDCSRList.isEmpty())
			{
					if(!netStartRpt.isQtrStart())
					{
		DlrCompSummForm dlrCompSummForm2 = new DlrCompSummForm();

					
		dlrCompSummForm2.setBonusType("Total");
					
		dlrCompSummForm2.setPoCount(poCountTotalQtr+"");
		dlrCompSummForm2.setCustExp(custExpTotalQtr+"");
		dlrCompSummForm2.setNvSales(nvSalesTotalQtr+"");
		dlrCompSummForm2.setBrdStd(brdStdTotalQtr+"");
		dlrCompSummForm2.setTotal(custExpTotalQtr.add(nvSalesTotalQtr).add(brdStdTotalQtr)+"");
					
		dlrCompSummForm2.setUnearnedBonus(unearnedBonusTotalQtr+"");
		qtrlyDCSRList.add(dlrCompSummForm2);
		updateDCSRListWithSubTotal(qtrlyDCSRList,dlrCompSummFormCVPQtrly,cvpIndexQtrly);
		//qtrlyDCSRMap.put(dlrCompSummForm2.getBonusType(), dlrCompSummForm2);
					}
			}
			
			
			

			
			//list to map
			//for(monthlyDCSRList)
			if(netStartRpt.isQtrStart())
			{
				qtrlyDCSRList.addAll(monthlyDCSRList);
			}
			setMonthlyDCSRList(monthlyDCSRList);
			setQtrlyDCSRList(qtrlyDCSRList);
			
			
			
			
			
			
			
		}
			private void fillDlrCompSummListSmart(List<List<String>> resultList) {
				/*List<List<String>> resultList = list.get(0);
				int qtr = DPBCommonHelper.calculateQyarter(netStartRpt.getMonth());
				String qtrName = "Q"+qtr;
				setQtrName(qtrName);
				String monthName= DPBCommonHelper.getMonthName(netStartRpt.getMonth());
				setMonthName(monthName);*/
				BigDecimal poCountTotal = new BigDecimal(0);
				BigDecimal custExpTotal = new BigDecimal(0);
				BigDecimal custExpSalesTot = new BigDecimal(0);
				BigDecimal custExpServiceTot = new BigDecimal(0);
				BigDecimal nvSalesTotal = new BigDecimal(0);
				BigDecimal preOwnedTotal = new BigDecimal(0);
				BigDecimal brdStdTotal = new BigDecimal(0);
				BigDecimal smFranTotal = new BigDecimal(0);
				BigDecimal unearnedBonusTotal = new BigDecimal(0);
				
				BigDecimal poCountTotalQtr = new BigDecimal(0);
				BigDecimal custExpTotalQtr = new BigDecimal(0);
				BigDecimal custExpSalesTotQtr = new BigDecimal(0);
				BigDecimal custExpServiceTotQtr = new BigDecimal(0);
				BigDecimal nvSalesTotalQtr = new BigDecimal(0);
				BigDecimal preOwnedTotalQtr = new BigDecimal(0);
				BigDecimal brdStdTotalQtr = new BigDecimal(0);
				BigDecimal smFranTotalQtr = new BigDecimal(0);
				BigDecimal unearnedBonusTotalQtr = new BigDecimal(0);
				BigDecimal poCount= new BigDecimal(0);
				BigDecimal custExp= new BigDecimal(0);
				BigDecimal nvSales= new BigDecimal(0);
				BigDecimal unearnedBonus= new BigDecimal(0);
				BigDecimal brdStd= new BigDecimal(0);
				BigDecimal smFran= new BigDecimal(0);
				DlrCompSummForm dlrCompSummFormCVPMonthly = null;
				DlrCompSummForm dlrCompSummFormCVPQtrly = null;
				int cvpIndexMonthly = 0;
				int countMonthly = 0;
				int cvpIndexQtrly = 0;
				int countQtrly = 0;
				LinkedHashMap<String,DlrCompSummForm> qtrlyDCSRMap = new LinkedHashMap<String,DlrCompSummForm>();
				
					
				for(List<String> l:resultList)
				{
		//need to ask about the condition l.get(3) != null && l.get(3).length()>0 to Kshitija
					if(l.get(3) != null && l.get(3).length()>0 )//&& l.get(2)!="MBDEAL A"
					{
					DlrCompSummForm dlrCompSummForm = new DlrCompSummForm();
					dlrCompSummForm.setSeq(getFormattedInteger(l.get(0)));
					dlrCompSummForm.setBonusType(l.get(2));
					
					/*if (l.get(3)!=null && l.get(3).length()>0){
						custExp = getFormattedBigDecimal(l.get(3));
						dlrCompSummForm.setCustExp(String.valueOf(custExp));
					}
					
					if(l.get(4)!=null && l.get(4).length()>0){
						nvSales = getFormattedBigDecimal(l.get(4));
						dlrCompSummForm.setNvSales(String.valueOf(nvSales));
					}
					
					if(l.get(5)!=null && l.get(5).length()>0){
					smFran = getFormattedBigDecimal(l.get(5));
					dlrCompSummForm.setSmFran(String.valueOf(smFran));
					}*/
					
					if(l.get(3)!=null && l.get(3).length()>0){
					brdStd = getFormattedBigDecimal(l.get(3));
					dlrCompSummForm.setBrdStd(String.valueOf(brdStd));
					}
					
					if (l.get(4) != null && l.get(4).length()>0){
						unearnedBonus = getFormattedBigDecimal(l.get(4));
						dlrCompSummForm.setUnearnedBonus(String.valueOf(unearnedBonus));
					}
					
					if (l.get(5) != null && l.get(5).length()>0){
						poCount = getFormattedBigDecimal(l.get(5));
						dlrCompSummForm.setPoCount(String.valueOf(poCount));
					}
					
					dlrCompSummForm.setTotal((brdStd)+"");
					
					
					if(l.get(1).equalsIgnoreCase("M"))
					{
						if((dlrCompSummForm.getBonusType().equalsIgnoreCase("CVP")))
						{
							cvpIndexMonthly = countMonthly;
							dlrCompSummFormCVPMonthly = dlrCompSummForm;
						}
						countMonthly++;
						if(dlrCompSummForm.getBonusType().equalsIgnoreCase("Retails") || dlrCompSummForm.getBonusType().equalsIgnoreCase("MBDEAL")||dlrCompSummForm.getBonusType().equalsIgnoreCase("CVP") ){
						poCountTotal = poCountTotal.add(poCount);
						}
						//custExpTotal = custExpTotal.add(custExp);
						//custExpSalesTot = custExpSalesTot.add(custExpSales);
						//custExpServiceTot = custExpServiceTot.add(custExpService);
						//nvSalesTotal = nvSalesTotal.add(nvSales);
						//smFranTotal = smFranTotal.add(smFran);
						//preOwnedTotal = preOwnedTotal.add(preOwned);
						brdStdTotal = brdStdTotal.add(brdStd);
						unearnedBonusTotal = unearnedBonusTotal.add(unearnedBonus);
						monthlyDCSRList.add(dlrCompSummForm);
						
					}
					else if(l.get(1).equalsIgnoreCase("Q"))
					{
						/*if((dlrCompSummForm.getBonusType().equalsIgnoreCase("CVP")))
						{
							cvpIndexQtrly = countQtrly;
							dlrCompSummFormCVPQtrly = dlrCompSummForm;
						}
						countQtrly++;
						poCountTotalQtr = poCountTotalQtr.add(poCount);
						custExpTotalQtr = custExpTotalQtr.add(custExp);
						//custExpSalesTotQtr = custExpSalesTotQtr.add(custExpSales);
						//custExpServiceTotQtr = custExpServiceTotQtr.add(custExpService);
						nvSalesTotalQtr = nvSalesTotalQtr.add(nvSales);
						//preOwnedTotalQtr = preOwnedTotalQtr.add(preOwned);
						smFranTotalQtr = smFranTotalQtr.add(smFran);
						brdStdTotalQtr = brdStdTotalQtr.add(brdStd);
						unearnedBonusTotalQtr = unearnedBonusTotalQtr.add(unearnedBonus);*/
						//qtrlyDCSRList.add(dlrCompSummForm);
						qtrlyDCSRMap.put(dlrCompSummForm.getBonusType(), dlrCompSummForm);
						
					}
					}
					//Algo -
					//1. check object at 0th index if it is retail/mbdeal
					//2. check object at 1st index if it is retail/mbdeal
					//3.
				
				/*unearnedForm.setIdDlr(l.get(2));
				unearnedForm.setNumPo(l.get(3));
				unearnedForm.setDesModl(l.get(4));*/
				}
				if(!netStartRpt.isQtrStart())
				{
					updateQuarterlyList(qtrlyDCSRMap);	
					for(DlrCompSummForm q:qtrlyDCSRList)
					{
						if((q.getBonusType().equalsIgnoreCase("CVP")))
					{
						cvpIndexQtrly = countQtrly;
						dlrCompSummFormCVPQtrly = q;
					}
					countQtrly++;
					if(q.getBonusType().equalsIgnoreCase("Retails") || q.getBonusType().equalsIgnoreCase("MBDEAL")||q.getBonusType().equalsIgnoreCase("CVP") ){
					poCountTotalQtr = poCountTotalQtr.add(getFormattedBigDecimal(q.getPoCount()));
					}
					//custExpTotalQtr = custExpTotalQtr.add(getFormattedBigDecimal(q.getCustExp()));
					//custExpSalesTotQtr = custExpSalesTotQtr.add(custExpSales);
					//custExpServiceTotQtr = custExpServiceTotQtr.add(custExpService);
					//nvSalesTotalQtr = nvSalesTotalQtr.add(getFormattedBigDecimal(q.getNvSales()));
					//preOwnedTotalQtr = preOwnedTotalQtr.add(preOwned);
					//smFranTotalQtr = smFranTotalQtr.add(getFormattedBigDecimal(q.getSmFran()));
					brdStdTotalQtr = brdStdTotalQtr.add(getFormattedBigDecimal(q.getBrdStd()));
					unearnedBonusTotalQtr = unearnedBonusTotalQtr.add(getFormattedBigDecimal(q.getUnearnedBonus()));
					}
				}
				
				
				//setTotals
				if(monthlyDCSRList != null && !monthlyDCSRList.isEmpty())
				{
					DlrCompSummForm dlrCompSummForm1 = new DlrCompSummForm();

					
					dlrCompSummForm1.setBonusType("Total");
					
					dlrCompSummForm1.setPoCount(poCountTotal+"");
					//dlrCompSummForm1.setCustExp(custExpTotal+"");
					//dlrCompSummForm1.setSmFran(smFranTotal+"");
					//dlrCompSummForm1.setNvSales(nvSalesTotal+"");
					dlrCompSummForm1.setBrdStd(brdStdTotal+"");
					dlrCompSummForm1.setTotal(custExpTotal.add(smFranTotal).add(nvSalesTotal).add(brdStdTotal)+"");
					
					dlrCompSummForm1.setUnearnedBonus(unearnedBonusTotal+"");
					monthlyDCSRList.add(dlrCompSummForm1);
					updateDCSRListWithSubTotal(monthlyDCSRList,dlrCompSummFormCVPMonthly,cvpIndexMonthly);
				}
				if(qtrlyDCSRList != null && !qtrlyDCSRList.isEmpty())
				{
					if(!netStartRpt.isQtrStart())
					{
		DlrCompSummForm dlrCompSummForm2 = new DlrCompSummForm();

					
		dlrCompSummForm2.setBonusType("Total");
					
		dlrCompSummForm2.setPoCount(poCountTotalQtr+"");
		//dlrCompSummForm2.setCustExp(custExpTotalQtr+"");
		//dlrCompSummForm2.setSmFran(smFranTotalQtr+"");
		//dlrCompSummForm2.setNvSales(nvSalesTotalQtr+"");
		dlrCompSummForm2.setBrdStd(brdStdTotalQtr+"");
		dlrCompSummForm2.setTotal(custExpTotalQtr.add(smFranTotalQtr).add(nvSalesTotalQtr).add(brdStdTotalQtr)+"");
					
		dlrCompSummForm2.setUnearnedBonus(unearnedBonusTotalQtr+"");
		qtrlyDCSRList.add(dlrCompSummForm2);
		updateDCSRListWithSubTotal(qtrlyDCSRList,dlrCompSummFormCVPQtrly,cvpIndexQtrly);
					}
				}
					
						
				
				
				
				
				
				
				
				
				//monthlyDCSRList
				
				
				
			
			
			

			
			//list to map
			//for(monthlyDCSRList)
			if(netStartRpt.isQtrStart())
			{
				qtrlyDCSRList.addAll(monthlyDCSRList);
			}
			setMonthlyDCSRList(monthlyDCSRList);
			setQtrlyDCSRList(qtrlyDCSRList);
				
				
			}
			
			private void fillDlrCompSummListVans(List<List<String>> resultList) {
				/*List<List<String>> resultList = list.get(0);
				int qtr = DPBCommonHelper.calculateQyarter(netStartRpt.getMonth());
				String qtrName = "Q"+qtr;
				setQtrName(qtrName);
				String monthName= DPBCommonHelper.getMonthName(netStartRpt.getMonth());
				setMonthName(monthName);*/
				BigDecimal poCountTotal = new BigDecimal(0);
				BigDecimal custExpTotal = new BigDecimal(0);
				BigDecimal custExpSalesTot = new BigDecimal(0);
				BigDecimal custExpServiceTot = new BigDecimal(0);
				BigDecimal nvSalesTotal = new BigDecimal(0);
				BigDecimal preOwnedTotal = new BigDecimal(0);
				BigDecimal brdStdTotal = new BigDecimal(0);
				BigDecimal unearnedBonusTotal = new BigDecimal(0);
				
				BigDecimal poCountTotalQtr = new BigDecimal(0);
				BigDecimal custExpTotalQtr = new BigDecimal(0);
				BigDecimal custExpSalesTotQtr = new BigDecimal(0);
				BigDecimal custExpServiceTotQtr = new BigDecimal(0);
				BigDecimal nvSalesTotalQtr = new BigDecimal(0);
				BigDecimal preOwnedTotalQtr = new BigDecimal(0);
				BigDecimal brdStdTotalQtr = new BigDecimal(0);
				BigDecimal unearnedBonusTotalQtr = new BigDecimal(0);
				BigDecimal poCount= new BigDecimal(0);
				BigDecimal custExp= new BigDecimal(0);
				BigDecimal nvSales= new BigDecimal(0);
				BigDecimal unearnedBonus= new BigDecimal(0);
				BigDecimal brdStd= new BigDecimal(0);
				
				DlrCompSummForm dlrCompSummFormCVPMonthly = null;
				DlrCompSummForm dlrCompSummFormCVPQtrly = null;
				int cvpIndexMonthly = 0;
				int countMonthly = 0;
				int cvpIndexQtrly = 0;
				int countQtrly = 0;
				LinkedHashMap<String,DlrCompSummForm> qtrlyDCSRMap = new LinkedHashMap<String,DlrCompSummForm>();
				for(List<String> l:resultList)
				{
					if(l.get(3) != null && l.get(3).length()>0 )
					{
					DlrCompSummForm dlrCompSummForm = new DlrCompSummForm();
					dlrCompSummForm.setSeq(getFormattedInteger(l.get(0)));
					dlrCompSummForm.setBonusType(l.get(2));
					
					if (l.get(3)!=null && l.get(3).length()>0){
						custExp = getFormattedBigDecimal(l.get(3));
						dlrCompSummForm.setCustExp(String.valueOf(custExp));
					}
					
					if(l.get(4)!=null && l.get(4).length()>0){
						brdStd = getFormattedBigDecimal(l.get(4));
						dlrCompSummForm.setBrdStd(String.valueOf(brdStd));
					}
					
					if(l.get(5)!=null && l.get(5).length()>0){
						nvSales = getFormattedBigDecimal(l.get(5));
						dlrCompSummForm.setNvSales(String.valueOf(nvSales));
					}
					
					if (l.get(6) != null && l.get(6).length()>0){
						unearnedBonus = getFormattedBigDecimal(l.get(6));
						dlrCompSummForm.setUnearnedBonus(String.valueOf(unearnedBonus));
					}
					
					if (l.get(7) != null && l.get(7).length()>0){
						poCount = getFormattedBigDecimal(l.get(7));
						dlrCompSummForm.setPoCount(String.valueOf(poCount));
					}
					
					dlrCompSummForm.setTotal(custExp.add(brdStd).add(nvSales)+"");
					
					
					if(l.get(1).equalsIgnoreCase("M"))
					{
						if((dlrCompSummForm.getBonusType().equalsIgnoreCase("CVP")))
						{
							cvpIndexMonthly = countMonthly;
							dlrCompSummFormCVPMonthly = dlrCompSummForm;
						}
						countMonthly++;
						if(dlrCompSummForm.getBonusType().equalsIgnoreCase("Retails") || dlrCompSummForm.getBonusType().equalsIgnoreCase("MBDEAL")||dlrCompSummForm.getBonusType().equalsIgnoreCase("CVP") ){
						poCountTotal = poCountTotal.add(poCount);
						}
						custExpTotal = custExpTotal.add(custExp);
						nvSalesTotal = nvSalesTotal.add(nvSales);
						brdStdTotal = brdStdTotal.add(brdStd);
						unearnedBonusTotal = unearnedBonusTotal.add(unearnedBonus);
						monthlyDCSRList.add(dlrCompSummForm);
						
					}
					else if(l.get(1).equalsIgnoreCase("Q"))
					{
						/*if((dlrCompSummForm.getBonusType().equalsIgnoreCase("CVP")))
						{
							cvpIndexQtrly = countQtrly;
							dlrCompSummFormCVPQtrly = dlrCompSummForm;
						}
						countQtrly++;
						
						poCountTotalQtr = poCountTotalQtr.add(poCount);
						custExpTotalQtr = custExpTotalQtr.add(custExp);
						nvSalesTotalQtr = nvSalesTotalQtr.add(nvSales);
						brdStdTotalQtr = brdStdTotalQtr.add(brdStd);
						unearnedBonusTotalQtr = unearnedBonusTotalQtr.add(unearnedBonus);*/
						//qtrlyDCSRList.add(dlrCompSummForm);
						qtrlyDCSRMap.put(dlrCompSummForm.getBonusType(), dlrCompSummForm);
						
					}
					}
					//Algo -
					//1. check object at 0th index if it is retail/mbdeal
					//2. check object at 1st index if it is retail/mbdeal
					//3.
				
				/*unearnedForm.setIdDlr(l.get(2));
				unearnedForm.setNumPo(l.get(3));
				unearnedForm.setDesModl(l.get(4));*/
				}
				if(!netStartRpt.isQtrStart())
				{
					updateQuarterlyList(qtrlyDCSRMap);	
					for(DlrCompSummForm q:qtrlyDCSRList)
					{
						if((q.getBonusType().equalsIgnoreCase("CVP")))
					{
						cvpIndexQtrly = countQtrly;
						dlrCompSummFormCVPQtrly = q;
					}
					countQtrly++;
					if(q.getBonusType().equalsIgnoreCase("Retails") || q.getBonusType().equalsIgnoreCase("MBDEAL")||q.getBonusType().equalsIgnoreCase("CVP") ){
					poCountTotalQtr = poCountTotalQtr.add(getFormattedBigDecimal(q.getPoCount()));
					}
					custExpTotalQtr = custExpTotalQtr.add(getFormattedBigDecimal(q.getCustExp()));
					nvSalesTotalQtr = nvSalesTotalQtr.add(getFormattedBigDecimal(q.getNvSales()));
					brdStdTotalQtr = brdStdTotalQtr.add(getFormattedBigDecimal(q.getBrdStd()));
					unearnedBonusTotalQtr = unearnedBonusTotalQtr.add(getFormattedBigDecimal(q.getUnearnedBonus()));
					}
				}
				
				//setTotals
				if(monthlyDCSRList != null && !monthlyDCSRList.isEmpty())
				{
					DlrCompSummForm dlrCompSummForm1 = new DlrCompSummForm();

					
					dlrCompSummForm1.setBonusType("Total");
					
					dlrCompSummForm1.setPoCount(poCountTotal+"");
					dlrCompSummForm1.setCustExp(custExpTotal+"");
					dlrCompSummForm1.setNvSales(nvSalesTotal+"");
					dlrCompSummForm1.setBrdStd(brdStdTotal+"");
					dlrCompSummForm1.setTotal(custExpTotal.add(nvSalesTotal).add(brdStdTotal)+"");
					dlrCompSummForm1.setUnearnedBonus(unearnedBonusTotal+"");
					monthlyDCSRList.add(dlrCompSummForm1);
					updateDCSRListWithSubTotal(monthlyDCSRList,dlrCompSummFormCVPMonthly,cvpIndexMonthly);
				}
						
					if(qtrlyDCSRList != null && !qtrlyDCSRList.isEmpty())
					{
						if(!netStartRpt.isQtrStart())
						{
			DlrCompSummForm dlrCompSummForm2 = new DlrCompSummForm();

						
			dlrCompSummForm2.setBonusType("Total");
						
			dlrCompSummForm2.setPoCount(poCountTotalQtr+"");
			dlrCompSummForm2.setCustExp(custExpTotalQtr+"");
			dlrCompSummForm2.setNvSales(nvSalesTotalQtr+"");
			dlrCompSummForm2.setBrdStd(brdStdTotalQtr+"");
			dlrCompSummForm2.setTotal(custExpTotalQtr.add(nvSalesTotalQtr).add(brdStdTotalQtr)+"");
			dlrCompSummForm2.setUnearnedBonus(unearnedBonusTotalQtr+"");
			qtrlyDCSRList.add(dlrCompSummForm2);
			updateDCSRListWithSubTotal(qtrlyDCSRList,dlrCompSummFormCVPQtrly,cvpIndexQtrly);
				
						}
					}
						
				
				
				
				
				
				
				
				//monthlyDCSRList
				
				
				
				
				
				//monthlyDCSRList
				/*updateDCSRListWithSubTotal(monthlyDCSRList);
				updateDCSRListWithSubTotal(qtrlyDCSRList);*/
				//monthlyDCSRList
				
				
				

				
				//list to map
				//for(monthlyDCSRList)
				if(netStartRpt.isQtrStart())
				{
					qtrlyDCSRList.addAll(monthlyDCSRList);
				}
				setMonthlyDCSRList(monthlyDCSRList);
				setQtrlyDCSRList(qtrlyDCSRList);
				
			}
			//Ratna End
		
		
		
		
		
		
		private void updateDCSRListWithSubTotal(
				LinkedList<DlrCompSummForm> dcsrList, DlrCompSummForm dlrCompSummFormCVP, int cvpIndex) {
			String bonusType = dcsrList.get(0).getBonusType();
			boolean isRetail = false;
			boolean isMBDeal = false;
			boolean isSubTotal1 = false;
			DlrCompSummForm dlrCompSummSubTotal1Form = null;
			if(bonusType.equalsIgnoreCase("Retails"))
			{
				isRetail = true;
			}
			else if(bonusType.equalsIgnoreCase("MBDEAL"))
			{
				isMBDeal = true;
			}
			if (isMBDeal == false)
			{
				if(dcsrList.size()>1 && dcsrList.get(1).getBonusType().equalsIgnoreCase("MBDEAL"))
				{
					isMBDeal = true;
				}
			}
			if(isRetail == true && isMBDeal == true)
			{
				isSubTotal1 = true;
				dlrCompSummSubTotal1Form = new DlrCompSummForm();
				dlrCompSummSubTotal1Form.setBonusType("Subtotal");
				BigDecimal pocount1= getFormattedBigDecimal(dcsrList.get(0).getPoCount());
				BigDecimal pocount2= getFormattedBigDecimal(dcsrList.get(1).getPoCount());
				BigDecimal pocount = pocount1.add(pocount2);//subtotal
				dlrCompSummSubTotal1Form.setPoCount(pocount+"");
				BigDecimal custExp1= getFormattedBigDecimal(dcsrList.get(0).getCustExp());
				BigDecimal custExp2= getFormattedBigDecimal(dcsrList.get(1).getCustExp());
				BigDecimal custExp = custExp1.add(custExp2);
				dlrCompSummSubTotal1Form.setCustExp(custExp+"");
				if(netStartRpt.getVehicleTypeRd().contains("P"))
				{
				BigDecimal custExpSales1= getFormattedBigDecimal(dcsrList.get(0).getCustExpSales());
				BigDecimal custExpSales2= getFormattedBigDecimal(dcsrList.get(1).getCustExpSales());
				BigDecimal custExpSales = custExpSales1.add(custExpSales2);
				
				dlrCompSummSubTotal1Form.setCustExpSales(custExpSales+"");
					BigDecimal custExpService1= getFormattedBigDecimal(dcsrList.get(0).getCustExpService());
					BigDecimal custExpService2= getFormattedBigDecimal(dcsrList.get(1).getCustExpService());
					BigDecimal custExpService = custExpService1.add(custExpService2);
					dlrCompSummSubTotal1Form.setCustExpService(custExpService+"");
					BigDecimal preOwned1= getFormattedBigDecimal(dcsrList.get(0).getPreOwned());
					BigDecimal preOwned2= getFormattedBigDecimal(dcsrList.get(1).getPreOwned());
					BigDecimal preOwned = preOwned1.add(preOwned2);
					dlrCompSummSubTotal1Form.setPreOwned(preOwned+"");
				}
				if(netStartRpt.getVehicleTypeRd().contains("S"))
				{
					/*BigDecimal smFran1= getFormattedBigDecimal(dcsrList.get(0).getSmFran());
					BigDecimal smFran2= getFormattedBigDecimal(dcsrList.get(1).getSmFran());
					BigDecimal smFran = smFran1.add(smFran2);
					dlrCompSummSubTotal1Form.setSmFran(smFran+"");*/
					BigDecimal brdStd1= getFormattedBigDecimal(dcsrList.get(0).getBrdStd());
					BigDecimal brdStd2= getFormattedBigDecimal(dcsrList.get(1).getBrdStd());
					BigDecimal brdStd = brdStd1.add(brdStd2);
					dlrCompSummSubTotal1Form.setBrdStd(brdStd+"");
				}
				else{
				BigDecimal nvSales1= getFormattedBigDecimal(dcsrList.get(0).getNvSales());
				BigDecimal nvSales2= getFormattedBigDecimal(dcsrList.get(1).getNvSales());
				BigDecimal nvSales = nvSales1.add(nvSales2);
				dlrCompSummSubTotal1Form.setNvSales(nvSales+"");
				BigDecimal brdStd1= getFormattedBigDecimal(dcsrList.get(0).getBrdStd());
				BigDecimal brdStd2= getFormattedBigDecimal(dcsrList.get(1).getBrdStd());
				BigDecimal brdStd = brdStd1.add(brdStd2);
				dlrCompSummSubTotal1Form.setBrdStd(brdStd+"");
			}
				BigDecimal unearnedBonus1= getFormattedBigDecimal(dcsrList.get(0).getUnearnedBonus());
				BigDecimal unearnedBonus2= getFormattedBigDecimal(dcsrList.get(1).getUnearnedBonus());
				BigDecimal unearnedBonus = unearnedBonus1.add(unearnedBonus2);
				dlrCompSummSubTotal1Form.setUnearnedBonus(unearnedBonus+"");
				BigDecimal total1= getFormattedBigDecimal(dcsrList.get(0).getTotal());
				BigDecimal total2= getFormattedBigDecimal(dcsrList.get(1).getTotal());
				BigDecimal total = total1.add(total2);
				dlrCompSummSubTotal1Form.setTotal(total+"");
				dcsrList.add(2, dlrCompSummSubTotal1Form);
			}
			else if((isRetail == true && isMBDeal == false) || (isRetail == false && isMBDeal == true))
			{
				isSubTotal1 = true;
				dlrCompSummSubTotal1Form = new DlrCompSummForm(dcsrList.get(0));
				dlrCompSummSubTotal1Form.setBonusType("Subtotal");
				dcsrList.add(1, dlrCompSummSubTotal1Form);
			}
			
			
			//Retail Adjustment

			String bonusTypeAdj = "";
			if(dcsrList.size()-2 >= 0 )
			{
			bonusTypeAdj =dcsrList.get(dcsrList.size()-2).getBonusType();
			}
			boolean isRetailAdj = false;
			boolean isMBDealAdj = false;
			if(bonusTypeAdj.equalsIgnoreCase("Ret Adjustments"))
			{
				isRetailAdj = true;
			}
			else if(bonusTypeAdj.equalsIgnoreCase("MBDEAL Adjustments"))
			{
				isMBDealAdj = true;
			}
			if (isMBDealAdj == true)
			{
				if(dcsrList.get(dcsrList.size()-3).getBonusType().equalsIgnoreCase("Ret Adjustments"))
				{
					isRetailAdj = true;
				}
			}
			if(isRetailAdj == true && isMBDealAdj == true)
			{
				DlrCompSummForm dlrCompSummForm = new DlrCompSummForm();
				dlrCompSummForm.setBonusType("Subtotal (Adjustments)");
				BigDecimal pocount1= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-2).getPoCount());
				BigDecimal pocount2= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-3).getPoCount());
				BigDecimal pocount = pocount1.add(pocount2);
				
				dlrCompSummForm.setPoCount(pocount+"");
				BigDecimal custExp1= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-2).getCustExp());
				BigDecimal custExp2= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-3).getCustExp());
				BigDecimal custExp = custExp1.add(custExp2);
				System.out.println("custExp:::"+custExp);
				dlrCompSummForm.setCustExp(custExp+"");
				if(netStartRpt.getVehicleTypeRd().contains("P"))
				{
				BigDecimal custExpSales1= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-2).getCustExpSales());
				BigDecimal custExpSales2= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-3).getCustExpSales());
				BigDecimal custExpSales = custExpSales1.add(custExpSales2);
				
				dlrCompSummForm.setCustExpSales(custExpSales+"");
				BigDecimal custExpService1= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-2).getCustExpService());
				BigDecimal custExpService2= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-3).getCustExpService());
				BigDecimal custExpService = custExpService1.add(custExpService2);
				
				dlrCompSummForm.setCustExpService(custExpService+"");
				BigDecimal preOwned1= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-2).getPreOwned());
				BigDecimal preOwned2= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-3).getPreOwned());
				BigDecimal preOwned = preOwned1.add(preOwned2);
				
				dlrCompSummForm.setPreOwned(preOwned+"");
				}
				if(netStartRpt.getVehicleTypeRd().contains("S"))
				{
					/*BigDecimal smFran1= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-2).getSmFran());
					BigDecimal smFran2= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-3).getSmFran());
					BigDecimal smFran = smFran1.add(smFran2);
					
					dlrCompSummForm.setSmFran(smFran+"");*/
					BigDecimal brdStd1= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-2).getBrdStd());
					BigDecimal brdStd2= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-3).getBrdStd());
					BigDecimal brdStd = brdStd1.add(brdStd2);
					
					dlrCompSummForm.setBrdStd(brdStd+"");
				}
				else{
					
				BigDecimal nvSales1= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-2).getNvSales());
				BigDecimal nvSales2= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-3).getNvSales());
				BigDecimal nvSales = nvSales1.add(nvSales2);
				
				dlrCompSummForm.setNvSales(nvSales+"");
				
				BigDecimal brdStd1= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-2).getBrdStd());
				BigDecimal brdStd2= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-3).getBrdStd());
				BigDecimal brdStd = brdStd1.add(brdStd2);
				
				dlrCompSummForm.setBrdStd(brdStd+"");
				}
				BigDecimal unearnedBonus1= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-2).getUnearnedBonus());
				BigDecimal unearnedBonus2= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-3).getUnearnedBonus());
				BigDecimal unearnedBonus = unearnedBonus1.add(unearnedBonus2);
				
				dlrCompSummForm.setUnearnedBonus(unearnedBonus+"");
				BigDecimal total1= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-2).getTotal());
				BigDecimal total2= getFormattedBigDecimal(dcsrList.get(dcsrList.size()-3).getTotal());
				BigDecimal total = total1.add(total2);
				
				dlrCompSummForm.setTotal(total+"");
				//adding subtotal1 and subtotal2 - needs refactoring\
				//Subtotal (Adjustments) for units, earned (144,145,48,49,50,total) should be just sum of ‘ret adjustments’ and ‘MBDEAL Adjustments’ [in both Month and Quarter Sections]
				/*if(dlrCompSummSubTotal1Form != null)
				{
					//pocount = pocount.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getPoCount()));
					dlrCompSummForm.setPoCount(getFormattedBigDecimal(dlrCompSummForm.getPoCount()).add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getPoCount()))+"");
					//custExp = custExp.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getCustExp()));
					dlrCompSummForm.setCustExp(getFormattedBigDecimal(dlrCompSummForm.getCustExp()).add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getCustExp()))+"");
					if(netStartRpt.getVehicleTypeRd().contains("P"))
					{
						//custExpSales = custExpSales.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getCustExpSales()));
						dlrCompSummForm.setCustExpSales(getFormattedBigDecimal(dlrCompSummForm.getCustExpSales()).add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getCustExpSales()))+"");
						//custExpService = custExpService.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getCustExpService()));
						dlrCompSummForm.setCustExpService(getFormattedBigDecimal(dlrCompSummForm.getCustExpService()).add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getCustExpService()))+"");
						//preOwned = preOwned.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getPreOwned()));
						dlrCompSummForm.setPreOwned(getFormattedBigDecimal(dlrCompSummForm.getPreOwned()).add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getPreOwned()))+"");
					}
					if(netStartRpt.getVehicleTypeRd().contains("S"))
					{
						//smFran = smFran.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getSmFran()));
						dlrCompSummForm.setSmFran(getFormattedBigDecimal(dlrCompSummForm.getSmFran()).add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getSmFran()))+"");
					}
						//nvSales = nvSales.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getNvSales()));
						dlrCompSummForm.setNvSales(getFormattedBigDecimal(dlrCompSummForm.getNvSales()).add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getNvSales()))+"");
						//brdStd = brdStd.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getBrdStd()));
						dlrCompSummForm.setBrdStd(getFormattedBigDecimal(dlrCompSummForm.getBrdStd()).add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getBrdStd()))+"");
						//unearnedBonus = unearnedBonus.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getUnearnedBonus()));
						dlrCompSummForm.setUnearnedBonus(getFormattedBigDecimal(dlrCompSummForm.getUnearnedBonus()).add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getUnearnedBonus()))+"");
						//total = total.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getTotal()));
						dlrCompSummForm.setTotal(getFormattedBigDecimal(dlrCompSummForm.getTotal()).add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getTotal()))+"");
								
				}*/
				dcsrList.add(dcsrList.size()-1,dlrCompSummForm);
			}
			else if((isRetailAdj == true && isMBDealAdj == false) || (isRetailAdj == false && isMBDealAdj == true))
			{
				
				//DlrCompSummForm dlrCompSummForm = ((LinkedList<DlrCompSummForm>) dcsrList.clone()).get(dcsrList.size()-2);
				//DlrCompSummForm dlrCompSummForm1 = new DlrCompSummForm();
				//dlrCompSummForm1
				//dlrCompSummForm1.clone
				//dlrCompSummForm.setBonusType("SubTotal");
				/*DlrCompSummForm dlrCompSummForm = dcsrList.get(dcsrList.size()-2);
				dcsrList.add(dcsrList.size()-2,dlrCompSummForm);
				System.out.println(dcsrList.size()-2);*/
				
				DlrCompSummForm dlrCompSummForm = new DlrCompSummForm(dcsrList.get(dcsrList.size()-2));
				dlrCompSummForm.setBonusType("Subtotal (Adjustments)");
				//adding subtotal1 and subtotal2 - needs refactoring
				if(dlrCompSummSubTotal1Form != null)
				{
					//pocount = pocount.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getPoCount()));
					dlrCompSummForm.setPoCount(getFormattedBigDecimal(dlrCompSummForm.getPoCount())+"");
					//custExp = custExp.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getCustExp()));
					dlrCompSummForm.setCustExp(getFormattedBigDecimal(dlrCompSummForm.getCustExp())+"");
					if(netStartRpt.getVehicleTypeRd().contains("P"))
					{
						//custExpSales = custExpSales.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getCustExpSales()));
						dlrCompSummForm.setCustExpSales(getFormattedBigDecimal(dlrCompSummForm.getCustExpSales())+"");
						//custExpService = custExpService.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getCustExpService()));
						dlrCompSummForm.setCustExpService(getFormattedBigDecimal(dlrCompSummForm.getCustExpService())+"");
						//preOwned = preOwned.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getPreOwned()));
						dlrCompSummForm.setPreOwned(getFormattedBigDecimal(dlrCompSummForm.getPreOwned())+"");
					}
					if(netStartRpt.getVehicleTypeRd().contains("S"))
					{
						//smFran = smFran.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getSmFran()));
						//dlrCompSummForm.setSmFran(getFormattedBigDecimal(dlrCompSummForm.getSmFran()).add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getSmFran()))+"");
						dlrCompSummForm.setBrdStd(getFormattedBigDecimal(dlrCompSummForm.getBrdStd())+"");
					}
					else{
						//nvSales = nvSales.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getNvSales()));
						dlrCompSummForm.setNvSales(getFormattedBigDecimal(dlrCompSummForm.getNvSales())+"");
						//brdStd = brdStd.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getBrdStd()));
						dlrCompSummForm.setBrdStd(getFormattedBigDecimal(dlrCompSummForm.getBrdStd())+"");
						//unearnedBonus = unearnedBonus.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getUnearnedBonus()));
					}
						dlrCompSummForm.setUnearnedBonus(getFormattedBigDecimal(dlrCompSummForm.getUnearnedBonus())+"");
						//total = total.add(getFormattedBigDecimal(dlrCompSummSubTotal1Form.getTotal()));
						dlrCompSummForm.setTotal(getFormattedBigDecimal(dlrCompSummForm.getTotal())+"");
								
				}
				dcsrList.add(dcsrList.size()-1,dlrCompSummForm);
				//dcsrList.
				//DlrCompSummForm dlrCompSummForm1 = dcsrList.get(dcsrList.size()-2);
				//dlrCompSummForm1.setBonusType("SubTotal");
				//dcsrList.get(dcsrList.size()-2).setBonusType("SubTotal");
			
				
				
				//dcsrList.addLast(dlrCompSummForm);
			}
			if(dlrCompSummFormCVP != null)
			{
				if(isSubTotal1 == true)
				{
					dcsrList.remove(cvpIndex+1);//add condition to check only if subtotal 1 is added
				}
				else
				{
					dcsrList.remove(cvpIndex);
				}
				dcsrList.add(dcsrList.size()-1,dlrCompSummFormCVP);
			}
		
			//dcsrList.indexOf(dlrCompSummForm.get)
		}

		//Dealer Compliance Summary Report - End
		
	//Fixes-Start
	
		private List<List<List<String>>> convertFormat(List<List<List<String>>> list) {
			
			String temp = "";
			
			for(int i =0; i <list.size(); i ++) {
				
				if(list.get(i) != null) {
					
					for(int j =0; j <list.get(i).size(); j ++) {
						
						if(list.get(i).get(j) != null) {
							
							for(int k =0; k <list.get(i).get(j).size() ; k ++) {
								
								temp = list.get(i).get(j).get(k);
								
								if(temp != null && !"".equalsIgnoreCase(temp)) {
									
									if(temp.contains("."))  {
										
										temp = convertNumberWithReadbleFormat(checkNegative(temp));
										
										list.get(i).get(j).set(k, temp);									
										
									}
								}					
							}
						}
					}
				}
			}
			return list;
		}
		
	/*private List<List<List<String>>> removeNegativeAndDecimalInExcel(List<List<List<String>>> list) {
			
			String temp = "";
			
			for(int i =0; i <list.size(); i ++) {
				
				if(list.get(i) != null) {
					
					for(int j =0; j <list.get(i).size(); j ++) {
						
						if(list.get(i).get(j) != null) {
							
							for(int k =0; k <list.get(i).get(j).size() ; k ++) {
								
								temp = list.get(i).get(j).get(k);
								
								if(temp != null && !"".equalsIgnoreCase(temp)) {
									
									if(temp.contains("-"))  {
										
										temp = temp.replaceAll("-", "");
										temp= "-" + temp;
										temp = temp.replaceAll("[.0]+$", "");									
										list.get(i).get(j).set(k, temp);
										
									}
								}					
							}
						}
					}
				}
			}
			return list;
		}*/
	
private List<List<List<String>>> removeNegativeAndDecimalInExcel(List<List<List<String>>> list) {
		
		String temp = "";
		
		for(int i =0; i <list.size(); i ++) {
			
			if(list.get(i) != null) {
				
				for(int j =0; j <list.get(i).size(); j ++) {
					
					if(list.get(i).get(j) != null) {
						
						for(int k =0; k <list.get(i).get(j).size() ; k ++) {
							
							temp = list.get(i).get(j).get(k);
							
							if(temp != null && !"".equalsIgnoreCase(temp)) {
								
								if(temp.contains("-"))  {
									
									temp = temp.replaceAll("-", "");
									temp= "-" + temp;
									temp = temp.replaceAll("[.][0-9]+$", "");									
									list.get(i).get(j).set(k, temp);
									
								}
							}					
						}
					}
				}
			}
		}
		return list;
	}
	private List<List<List<String>>> constructFixedMarginSummarySection(List<List<List<String>>> list, int month) {	
		
		List<List<String>> paymentSection =new ArrayList<List<String>>();
		List<String> innerPaymentSection = new ArrayList<String>();
		
		List<List<String>> summarySection =new ArrayList<List<String>>();
		List<String> innerSummarySection1 = new ArrayList<String>();
		List<String> innerSummarySection2 = new ArrayList<String>();
		List<String> innerSummarySection3 = new ArrayList<String>();
		String monthlyAmt;
		String quarterlyAmt;
		String yearlyAmt;
		String totalBonus;
		String nonRetail="";
		String mbdeal="";
		String cvp="";
		
		if(list != null && list.get(0)!= null) {
			
			if( list.get(0).get(0) !=null) {
				
				//Only if month is Retail Quarter End month(March,June,September,December) display the Payment Section value
				if( month % 3  == 0) {
					
					monthlyAmt = list.get(0).get(0).get(3);
					quarterlyAmt = list.get(0).get(0).get(5);
					yearlyAmt = list.get(0).get(0).get(7);
					
					innerPaymentSection.add("Standard Retails");
					innerPaymentSection.add(monthlyAmt);
					innerPaymentSection.add(quarterlyAmt);
					innerPaymentSection.add(yearlyAmt);
					
				}			
				else {
					
					innerPaymentSection.add("No Payments Made");
					innerPaymentSection.add("0");
					innerPaymentSection.add("0");
					innerPaymentSection.add("0");
					
				}
				
				
				paymentSection.add(innerPaymentSection);
			}		
			
			if(list.get(0).get(4) !=null) {
				
				totalBonus = list.get(0).get(4).get(7);
				
				innerSummarySection1.add("1");
				innerSummarySection1.add("Total Bonus Paid (Retail Month April Onwards)");
				innerSummarySection1.add(totalBonus);
				
				innerSummarySection2.add("2");
				innerSummarySection2.add("Standard Retails Paid (Retail Month April Onwards)");
				innerSummarySection2.add(list.get(0).get(0).get(7));
				
				innerSummarySection3.add("3");
				innerSummarySection3.add("CVP + MBDEAL Paid (Retail Month April Onwards)");
				
				if(list.get(0).get(1) !=null && list.get(0).get(3) !=null) {
					
					if(list.get(0).get(1).get(7) !=null) {
						
						mbdeal = list.get(0).get(1).get(7).trim();
						cvp = list.get(0).get(3).get(7).trim();				
						BigDecimal sum = new BigDecimal(mbdeal).add(new BigDecimal(cvp));
						nonRetail = sum.toString();	
					}
					
				}
				innerSummarySection3.add(nonRetail);
				
				summarySection.add(0,innerSummarySection1);
				summarySection.add(1,innerSummarySection2);			
				summarySection.add(2,innerSummarySection3);			
			}
			
		}
		list.add(1, paymentSection);
		list.add(2, summarySection);
		
		return list;
	}

	//Fixes-End		
}
