package com.mbusa.dpb.web.common.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.apache.commons.collections.map.HashedMap;

import com.ibm.security.krb5.internal.crypto.k;
import com.ibm.security.krb5.internal.crypto.v;
import com.mbusa.dpb.common.domain.AMGDealer;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;

public class AMGDealerAction extends DPBAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = AMGDealerAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(AMGDealerAction.class);
	BusinessDelegate businessDelegate  = new BusinessDelegate();

	List<AMGDealer> listDlrs = new ArrayList<AMGDealer>();
		
	String dealerName;
	String dealerId ;	
	String dealerType;
	String retailYear;
	String retailStartMonth;
	String  retailEndMonth;
	
	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}	
	
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	
	public String getDealerType() {
		return dealerType;
	}

	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}		
		
	public String getRetailYear() {
		return retailYear;
	}

	public void setRetailYear(String retailYear) {
		this.retailYear = retailYear;
	}

	public String getRetailStartMonth() {
		return retailStartMonth;
	}

	public void setRetailStartMonth(String retailStartMonth) {
		this.retailStartMonth = retailStartMonth;
	}

	public String getRetailEndMonth() {
		return retailEndMonth;
	}

	public void setRetailEndMonth(String retailEndMonth) {
		this.retailEndMonth = retailEndMonth;
	}

	public List<AMGDealer> getListDlrs() {
		return listDlrs;
	}
	public void setListDlrs(List<AMGDealer> listDlrs) {
		this.listDlrs = listDlrs;
	}	

	public String previewPage(){
		final String methodName = "previewPage";
		LOGGER.enter(CLASSNAME, methodName);
		this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
		String actionForward = "amgPerfView";
		
		return actionForward;
	}
	
	/**
	 * This method will retrieve the AMG dealer info for the given dealer Id.
	 * @return
	 */
	public String retrieveAMGDealerInfo() {
		final String methodName = "retrieveAMGDealerInfo";
		LOGGER.enter(CLASSNAME, methodName);
		String actionForward = "amgDealerList";
		
		try {
			
			listDlrs = businessDelegate.getDlrsInfoList(dealerId);
			
			for(AMGDealer dlr : listDlrs) {
				if(dlr.getStartDate() !=null) {
					dlr.setRetailYear((new SimpleDateFormat("yyyy-MM-dd").format(dlr.getStartDate())));
					dlr.setRetailStartMonth((new SimpleDateFormat("yyyy-MM-dd").format(dlr.getStartDate())));
				}
				if(dlr.getEndDate() != null) {
					
					dlr.setRetailEndMonth((new SimpleDateFormat("yyyy-MM-dd").format(dlr.getEndDate())));
				}				
			}
			
		} catch (PersistenceException pe) {
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
			pe.printStackTrace();
		} catch(Exception e){
			LOGGER.error("Exception occured");
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
			e.printStackTrace();
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}	
	
	/**
	 * This method will modify the AMG dealer info.
	 * @return
	 */
	public String modifyAMGDealerInfo() {
		final String methodName = "modifyAMGDealerInfo";
		LOGGER.enter(CLASSNAME, methodName);
		String actionForward = "amgDealerList";
		Boolean modified = false;
		
		try {
			//Hard coded for testing to be passed from UI
			AMGDealer dlr = new AMGDealer();
			dlr.setDealerID(dealerId);
			dlr.setDealerType(dealerType);
			dlr.setRetailYear(retailYear);
			dlr.setRetailStartMonth(retailStartMonth);
			
			//The Default end date for all AMG dealers is marked as Jan 2020. 
			//This needs to be replaced after active/inactive flag is added deletion in DB later			
			if("-1".equalsIgnoreCase(retailEndMonth)) {
				
				dlr.setRetailEndYear("2020");
				dlr.setRetailEndMonth("01");
			}
			else{
				dlr.setRetailEndYear(dlr.getRetailYear());
				dlr.setRetailEndMonth(retailEndMonth);		
			}
			
			 modified = businessDelegate.modifyAMGDealerInfo(dlr);
			 
			 if(modified)
				 LOGGER.info("Dealer ID "+dlr.getDealerID()+ " modified successfully");
			 else
				 LOGGER.info("Dealer ID "+dlr.getDealerID()+ " was not modified successfully");
			 
			 actionForward = retrieveAMGDealerInfo();
			 
			 //listDlrs =  businessDelegate.getDlrsInfoList(dlr.getDealerID());
			 
			
		} catch (PersistenceException pe) {
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
			pe.printStackTrace();
		} catch(Exception e){
			LOGGER.error("Exception occured");
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
			e.printStackTrace();
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}
	
	/**
	 * This method will retrieve the AMG dealer name for the given dealer Id.
	 * @return
	 */
	public String retrieveAMGDealerName() {
		final String methodName = "retrieveAMGDealerName";
		LOGGER.enter(CLASSNAME, methodName);
		String actionForward = "amgDealerName";
				
		try {
					
			dealerId = request.getParameter("dealerIdValue");
		
			dealerName = businessDelegate.getAMGDealerName(dealerId);
			
		} catch (PersistenceException pe) {
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
			pe.printStackTrace();
		} catch(Exception e){
			LOGGER.error("Exception occured");
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
			e.printStackTrace();
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}
	
	
	
}