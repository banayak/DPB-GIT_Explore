/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: CB5002578 
 * Program Name				: VehicleConditionDefAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used for displaying existing vehicle condition mapping based on 
 * 							  condition - vehicle or blocking
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * CB5002578   July 16, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.common.actions;

import java.util.ArrayList;
import java.util.List;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.ConditionDefinition;
import com.mbusa.dpb.common.domain.VehicleConditionMapping;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.form.VehicleConditionForm;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class VehicleConditionDefAction extends DPBAction{	
	/**
	 * 
	 */
	static final private String CLASSNAME = VehicleConditionDefAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(VehicleConditionDefAction.class);
	
	private static final long serialVersionUID = 1L;
	private ArrayList<VehicleType> vehicleTypeList = new ArrayList<VehicleType>();
	private List <ConditionDefinition> vehicleCond = new ArrayList <ConditionDefinition>();	
	private VehicleConditionForm  vcMappingForm =  new VehicleConditionForm();
	private BusinessDelegate businessDelegate =  new BusinessDelegate();
 
	private String actionForward = IWebConstants.DPB_ERROR_URL;
	
	public String  viewVehicleConditions(){
		final String methodName = "viewVehicleConditions";
		LOGGER.enter(CLASSNAME, methodName);
		try{
			actionForward = "vehicleConditionView";
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);			
			String type = request.getParameter(IWebConstants.REQUEST_CTYPE);
			vehicleTypeList =  (ArrayList<VehicleType>) MasterDataLookup.getInstance().getVehicleList();
			if(!WebHelper.isEmptyOrNullString(type) && IWebConstants.VEHICLE_TYP_B.equalsIgnoreCase(type) ){				
				vehicleCond =(ArrayList<ConditionDefinition>) MasterDataLookup.getInstance().getBlockedConditionList();				
			}else{
				type = IWebConstants.VEHICLE_TYP_V;	
				vehicleCond =(ArrayList<ConditionDefinition>) MasterDataLookup.getInstance().getVehicleConditionList();
			}
			vcMappingForm.setCondType(type);
			List<VehicleConditionMapping> vcMap = businessDelegate.getVehiclMappedCondtion(type);
			populateVehicleConditionInformation(vcMap);
		}catch(BusinessException be){
			LOGGER.info("BusinessException occured");
		}catch (TechnicalException te) {
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}
	
	
	public String saveVehicleMappedCondition(){		
		final String methodName = "saveVehicleMappedCondition";
		LOGGER.enter(CLASSNAME, methodName);
		try {
			actionForward = "vehicleConditionView";
			
			String type =IWebConstants.VEHICLE_TYP_V;
			LOGGER.info("Type:"+type);	
			if(vcMappingForm!= null && !WebHelper.isEmptyOrNullString(vcMappingForm.getCondType())){				
				type = vcMappingForm.getCondType();	
			}
			if(!WebHelper.isEmptyOrNullString(type) && IWebConstants.VEHICLE_TYP_B.equalsIgnoreCase(type) ){				
				vehicleCond =(ArrayList<ConditionDefinition>) MasterDataLookup.getInstance().getBlockedConditionList();				
			}else{
				type = IWebConstants.VEHICLE_TYP_V;	
				vehicleCond =(ArrayList<ConditionDefinition>) MasterDataLookup.getInstance().getVehicleConditionList();
			}			
			vehicleTypeList =  (ArrayList<VehicleType>) MasterDataLookup.getInstance().getVehicleList();
			List<VehicleConditionMapping>  vcMapping= populateVehicleMaping(vcMappingForm);
			businessDelegate.saveVehicleMappedCondition(vcMapping,type);
		}catch(BusinessException be){
			LOGGER.info("BusinessException occured");
		}catch (TechnicalException te) {
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
		
	}
	@SuppressWarnings("unchecked")
	public void populateVehicleConditionInformation(List<VehicleConditionMapping> vcList){
		if(vcList != null){
			int i = 0;
			for (VehicleConditionMapping temp : vcList) {				
				if(temp!= null && IWebConstants.PCAR.equalsIgnoreCase(temp.getVehicleId())){
					this.vcMappingForm.setPcId(temp.getVehicleId());
					this.vcMappingForm.setPcStatus(temp.getStatus());
					this.vcMappingForm.setPcConditionList((ArrayList<String>) temp.getConditionList().get(i));					
				}
				if(temp!= null && IWebConstants.SMART.equalsIgnoreCase(temp.getVehicleId())){
					this.vcMappingForm.setSmartId(temp.getVehicleId());
					this.vcMappingForm.setSmartStatus(temp.getStatus());
					this.vcMappingForm.setSmartConditionList((ArrayList<String>) temp.getConditionList().get(i));					
				}
				if(temp!= null && IWebConstants.VAN.equalsIgnoreCase(temp.getVehicleId())){
					this.vcMappingForm.setVanId(temp.getVehicleId());
					this.vcMappingForm.setVanStatus(temp.getStatus());
					this.vcMappingForm.setVanConditionList((ArrayList<String>) temp.getConditionList().get(i));					
				}
				if(temp!= null && IWebConstants.FTL.equalsIgnoreCase(temp.getVehicleId())){
					this.vcMappingForm.setFtlId(temp.getVehicleId());
					this.vcMappingForm.setFtlStatus(temp.getStatus());
					this.vcMappingForm.setFtlConditionList((ArrayList<String>) temp.getConditionList().get(i));					
				}
			}		
		}
	}
	
	
	private List<VehicleConditionMapping>  populateVehicleMaping(VehicleConditionForm vcMappingForm) {
		VehicleConditionMapping dlrPrgm = null;
		List <VehicleConditionMapping> vcList =  new ArrayList<VehicleConditionMapping>();
		if(WebHelper.isListEmpty(vcMappingForm.getPcConditionList())){
			dlrPrgm =  new VehicleConditionMapping();
			dlrPrgm.getConditionList().add(vcMappingForm.getPcConditionList());
			dlrPrgm.setStatus(vcMappingForm.getPcStatus());
			dlrPrgm.setVehicleId(vcMappingForm.getPcId());
			dlrPrgm.setUserId(this.getUserId());
			vcList.add(dlrPrgm);
		}
		if(WebHelper.isListEmpty(vcMappingForm.getFtlConditionList())){
			dlrPrgm =  new VehicleConditionMapping();
			dlrPrgm.getConditionList().add(vcMappingForm.getFtlConditionList());
			dlrPrgm.setStatus(vcMappingForm.getFtlStatus());
			dlrPrgm.setVehicleId(vcMappingForm.getFtlId());
			dlrPrgm.setUserId(this.getUserId());
			vcList.add(dlrPrgm);
		}
		if(WebHelper.isListEmpty(vcMappingForm.getVanConditionList())){
			dlrPrgm =  new VehicleConditionMapping();
			dlrPrgm.getConditionList().add(vcMappingForm.getVanConditionList());
			dlrPrgm.setStatus(vcMappingForm.getVanStatus());
			dlrPrgm.setVehicleId(vcMappingForm.getVanId());
			dlrPrgm.setUserId(this.getUserId());
			vcList.add(dlrPrgm);
		}
		if(WebHelper.isListEmpty(vcMappingForm.getSmartConditionList())){
			dlrPrgm =  new VehicleConditionMapping();
			dlrPrgm.getConditionList().add(vcMappingForm.getSmartConditionList());
			dlrPrgm.setVehicleId(vcMappingForm.getSmartId());
			dlrPrgm.setStatus(vcMappingForm.getSmartStatus());
			dlrPrgm.setUserId(this.getUserId());
			vcList.add(dlrPrgm);
		}		
		return vcList;		
	}	

	public ArrayList<VehicleType> getVehicleTypeList() {
		return vehicleTypeList;
	}

	public void setVehicleTypeList(ArrayList<VehicleType> vehicleTypeList) {
		this.vehicleTypeList = vehicleTypeList;
	}

	public List<ConditionDefinition> getVehicleCond() {
		return vehicleCond;
	}

	public void setVehicleCond(List<ConditionDefinition> vehicleCond) {
		this.vehicleCond = vehicleCond;
	}

	public VehicleConditionForm getVcMappingForm() {
		return vcMappingForm;
	}

	public void setVcMappingForm(VehicleConditionForm vcMappingForm) {
		this.vcMappingForm = vcMappingForm;
	}
	
	

}
