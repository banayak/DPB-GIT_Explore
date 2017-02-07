package com.mbusa.dpb.web.Reports.action;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.domain.VistaFileProcessing;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.delegate.ReportDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class DlrReserveExcetionAction extends DPBAction {

	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = RetailPaymentReportAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(RetailPaymentReportAction.class);
	private String actionForward = "errorPage";
	private List <VehicleType>vehicleList = new ArrayList<VehicleType>();
	private List<VistaFileProcessing> vehExceptionList = new ArrayList<VistaFileProcessing>();
	private ReportDelegate rDelegate  = new ReportDelegate();
	private List<String> vehicleType;
	private String month;
	private String year;
	private String dealerIds;
	@SuppressWarnings("unchecked")
	public String generateReport()
	{
		final String methodName = "generateReport";
		LOGGER.enter(CLASSNAME, methodName);
		try{
			this.setMenuTabFocus(IWebConstants.REPORTS_ID);
			
			vehicleList = MasterDataLookup.getInstance().getVehicleList();
			actionForward = "dlrReserveCriteria";
			if((!WebHelper.isEmptyOrNullString(month) && !WebHelper.isEmptyOrNullString(year))
					|| (vehicleType!= null && vehicleType.size() > 0 )
					|| !WebHelper.isEmptyOrNullString(dealerIds)){
				vehExceptionList =  rDelegate.getDlrReserveExceptionList(vehicleType,month,year,dealerIds);
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
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}

	/**
	 * @return the vehicleList
	 */
	public List<VehicleType> getVehicleList() {
		return vehicleList;
	}

	/**
	 * @param vehicleList the vehicleList to set
	 */
	public void setVehicleList(List<VehicleType> vehicleList) {
		this.vehicleList = vehicleList;
	}

	/**
	 * @return the vehExceptionList
	 */
	public List<VistaFileProcessing> getVehExceptionList() {
		return vehExceptionList;
	}

	/**
	 * @param vehExceptionList the vehExceptionList to set
	 */
	public void setVehExceptionList(List<VistaFileProcessing> vehExceptionList) {
		this.vehExceptionList = vehExceptionList;
	}

	/**
	 * @return the vehicleType
	 */
	public List<String> getVehicleType() {
		return vehicleType;
	}

	/**
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(List<String> vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the dealerIds
	 */
	public String getDealerIds() {
		return dealerIds;
	}

	/**
	 * @param dealerIds the dealerIds to set
	 */
	public void setDealerIds(String dealerIds) {
		this.dealerIds = dealerIds;
	}
}
