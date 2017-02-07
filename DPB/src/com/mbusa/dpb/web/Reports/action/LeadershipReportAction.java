package com.mbusa.dpb.web.Reports.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.common.domain.LeadershipReport;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.delegate.ReportDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;

public class LeadershipReportAction extends DPBAction   {

	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = ReportGenerateAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(ReportGenerateAction.class);
	private String actionForward = "";
	private LeadershipReport ldrshipRpt;	
	public ReportDelegate rDelegate  = new ReportDelegate();
	private String message = "";
	PrintWriter pw = null;
	private StringBuffer strBuffer;
	private String waitReq;
	private String dealer;
	private String year;
	private String vehicleType; 
	
	public String ldrshipReportGen(){
		final String methodName = "ldrshipReportGen";
		LOGGER.enter(CLASSNAME, methodName);
		this.setMenuTabFocus(IWebConstants.REPORTS_ID);
		String actionForward = "ldrshipReportGen";
		return actionForward;
	}
	
	public String reportsGenerate1() throws Exception{
		final String methodName = "reportsGenerate1";
		LOGGER.enter(CLASSNAME, methodName);
		try {
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			actionForward = "ldrshipReportPopUp";
			
			ldrshipRpt = new LeadershipReport();
			ldrshipRpt.setDealer(dealer);
			ldrshipRpt.setVehicleType(vehicleType);
				
			strBuffer = rDelegate.reportsGenerate1(ldrshipRpt);
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
		}finally {
			if(pw != null){
				pw.close();
			}
		}
		addActionMessage(message);
		LOGGER.exit(CLASSNAME, methodName);
		
		return actionForward;
	}
	
	public StringBuffer getStrBuffer() {
		return strBuffer;
	}
	public void setStrBuffer(StringBuffer strBuffer) {
		this.strBuffer = strBuffer;
	}
	public LeadershipReport getLdrshipRpt() {
		return ldrshipRpt;
	}
	public void setLdrshipRpt(LeadershipReport ldrshipRpt) {
		this.ldrshipRpt = ldrshipRpt;
	}
	
	public String getWaitReq() {
		return waitReq;
	}
	public void setWaitReq(String waitReq) {
		this.waitReq = waitReq;
	}

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	
	

}