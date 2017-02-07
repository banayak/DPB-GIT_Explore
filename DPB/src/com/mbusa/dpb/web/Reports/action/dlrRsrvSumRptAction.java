/**
 * 
 */
package com.mbusa.dpb.web.Reports.action;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.DleRsrvSumRptBean;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.common.actions.InputFileAction;
import com.mbusa.dpb.web.delegate.ReportDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;

public class dlrRsrvSumRptAction extends DPBAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = InputFileAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(InputFileAction.class);
	private String actionForward = "";
	private String dealerId=null;
	private int year;
	private int month;
	List<VehicleType> vehList = null;
	List<DleRsrvSumRptBean> list = new ArrayList<DleRsrvSumRptBean>();;
	List<String> vehicles = null;
	
	ReportDelegate rDelegate = new ReportDelegate();
	public String viewDlrRsrvSumReport(){
		final String methodName = "getFileDetails";
		LOGGER.enter(CLASSNAME, methodName);
		actionForward="dlrRsrvSumReport";
		vehList = MasterDataLookup.getInstance().getVehicleList();
		try{
			if(year>0){
				if(vehicles == null || vehicles.size() == 0){
					
				}
				list = rDelegate.getDlrRsrvSumReport(vehicles,year,month);
			}
		}/*catch(BusinessException be){
			LOGGER.error("BusinessException occured");
			addActionError(IWebConstants.BUSS_EXC_RETV);
		}catch (TechnicalException te) {
			LOGGER.error("TechnicalException occured");
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}*/catch (PersistenceException pe) {
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			LOGGER.error("Exception occured");
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}
	/**
	 * @return the dealerId
	 */
	public String getDealerId() {
		return dealerId;
	}
	/**
	 * @param dealerId the dealerId to set
	 */
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	/**
	 * @return the list
	 */
	public List<DleRsrvSumRptBean> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<DleRsrvSumRptBean> list) {
		this.list = list;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	/**
	 * @return the vehList
	 */
	public List<VehicleType> getVehList() {
		return vehList;
	}
	/**
	 * @param vehList the vehList to set
	 */
	public void setVehList(List<VehicleType> vehList) {
		this.vehList = vehList;
	}
	/**
	 * @return the vehicles
	 */
	public List<String> getVehicles() {
		return vehicles;
	}
	/**
	 * @param vehicles the vehicles to set
	 */
	public void setVehicles(List<String> vehicles) {
		this.vehicles = vehicles;
	}
	
}

//date = WebHelper.getDateTimeMonthYear("MM/dd/yyyy");
//month = WebHelper.getDateTimeMonthYear("MMMMMMMM");
//year = WebHelper.getDateTimeMonthYear("y");
//time = WebHelper.getDateTimeMonthYear("hh:mm:ss a");