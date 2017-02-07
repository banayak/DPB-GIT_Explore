/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: BlockedVehiclesReportAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all request to BlockedVehiclesReportAction.
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 19, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.Reports.action;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.common.domain.BlockedVehicle;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.delegate.ReportDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;


public class BlockedVehiclesReportAction extends DPBAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = BlockedVehiclesReportAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(BlockedVehiclesReportAction.class);
	
	private String dealerId;
	private String vehicleId;
	private String fromDate;
	private String toDate;	
	private ReportDelegate rDelegate  = new ReportDelegate();
	private String actionForward = "errorPage";
	private List<BlockedVehicle> blockedList = new ArrayList<BlockedVehicle>();
	private List<BlockedVehicle> blkList = new ArrayList<BlockedVehicle>();
	private BlockedVehicle blkVehObj;
	
	public String displayBlockVehiclePage(){
		this.setMenuTabFocus(IWebConstants.REPORTS_ID);
		final String methodName = "viewVehicleConditions";
		LOGGER.enter(CLASSNAME, methodName);
		actionForward = "blockVehicleReport";
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}
	
	public String generateBlockVehicleReport(){
		final String methodName = "viewVehicleConditions";
		LOGGER.enter(CLASSNAME, methodName);
		try{
			actionForward = "generateblckVehRpt";
			Date fdate = WebHelper.convertStringToDate(fromDate);
			Date tDate = WebHelper.convertStringToDate(toDate);
			String vId="";
			boolean isWhitespace =this.getVehicleId().matches("^\\s*$");
			if(this.getVehicleId().indexOf(",") > - 1)
			{
				String arr[] = this.getVehicleId().split(",");
				if(arr.length == 0)
				{
					addActionError("Enter Valid VIN(s).");
				}
				
				
			}
			
			if(!hasActionErrors())
			{
					if(isWhitespace)
					{
						this.setVehicleId(this.getVehicleId().replaceAll("\\s",""));
					}
			
					if(this.getVehicleId().startsWith(",")== true && (this.getVehicleId() !=null && !this.getVehicleId().equals("")) )
					{
						vId=this.getVehicleId().substring(1,this.getVehicleId().length());
					}
					else if(this.getVehicleId().endsWith(",")== true && this.getVehicleId() !=null && !this.getVehicleId().equals(""))
					{
						vId=this.getVehicleId().substring(0,this.getVehicleId().lastIndexOf(","));
					}
					else 
					{
						vId=vehicleId.trim();
					}
					blockedList = rDelegate.getBlockVehicleReport(dealerId,vId,fdate,tDate);
					for(BlockedVehicle bVeh:blockedList){
						
						bVeh.setDisplayDate(WebHelper.formatDate(bVeh.getUpdatedDate()));
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
	public String getDealerId() {
		return dealerId;
	}
	
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	
	public String getVehicleId() {
		return vehicleId;
	}
	
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public BlockedVehicle getBlkVehObj() {
		return blkVehObj;
	}

	public void setBlkVehObj(BlockedVehicle blkVehObj) {
		this.blkVehObj = blkVehObj;
	}

	public List<BlockedVehicle> getBlkList() {
		return blkList;
	}

	public void setBlkList(List<BlockedVehicle> blkList) {
		this.blkList = blkList;
	}
	
}