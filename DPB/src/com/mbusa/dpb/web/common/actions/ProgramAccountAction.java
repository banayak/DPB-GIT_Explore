package com.mbusa.dpb.web.common.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.VehicleConditionMapping;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.form.LdrspAccountIDMappingForm;
import com.mbusa.dpb.web.helper.IWebConstants;

public class ProgramAccountAction extends DPBAction {	
	private static final long serialVersionUID = 1L;
	private static DPBLog LOGGER = DPBLog.getInstance(ProgramAccountAction.class);
	private ArrayList<VehicleType> accountIDTypeList = new ArrayList<VehicleType>();
	private LdrspAccountIDMappingForm  ldrspAccIDMapForm =  new LdrspAccountIDMappingForm();
	private BusinessDelegate businessDelegate =  new BusinessDelegate();    
	private String actionForward = "";
	int pcAccidListSize = 0;
	int smAccidListSize = 0;
	int vanAccidListSize = 0;
	int ftlAccidListSize = 0;

	public String viewAccountIDMapping() {
		try {
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			actionForward = "programAccountView";
			String userId = getUserId();
			ldrspAccIDMapForm.setUserId(userId);
			accountIDTypeList =  (ArrayList<VehicleType>) MasterDataLookup.getInstance().getVehicleList();
			Map<String,Object> accIDMap = businessDelegate.getAccountIDMaping();
			populateLDRSPAccountIDMapping(accIDMap);
		} catch (BusinessException be) {
			LOGGER.error("DPB.ACCT.ACT.001"+ "BusinessException occured"+ be.getMessage() + be.getMessageKey());
			actionForward =  IWebConstants.DPB_ERROR_URL;
		} catch (TechnicalException te) {
			LOGGER.error("DPB.ACCT.ACT.002"+ "TechnicalException occured"+ te.getMessage() + te.getMessageKey());
			actionForward = IWebConstants.DPB_ERROR_URL;
			request.setAttribute(IWebConstants.jspExe, te);
		} catch (PersistenceException pe) {
			LOGGER.error("DPB.ACCT.ACT.003"+ "PersistenceException occured"+ pe.getMessage());
			actionForward = IWebConstants.DPB_ERROR_URL;
			request.setAttribute(IWebConstants.jspExe, pe);
		} catch (Exception e) {
			LOGGER.error("DPB.ACCT.ACT.004"+ "Exception occured"+ e.getMessage());
			actionForward = IWebConstants.DPB_ERROR_URL;
			request.setAttribute(IWebConstants.jspExe, e);
		}	
		return actionForward;
	}

	public String prgDlrAccMapping() {	
		try {
			actionForward = "programAccountView";
			String userId = getUserId();
			ldrspAccIDMapForm.setUserId(userId);
			accountIDTypeList =  (ArrayList<VehicleType>) MasterDataLookup.getInstance().getVehicleList();			
			businessDelegate.saveAccountIDMapping(ldrspAccIDMapForm, userId);
			Map<String,Object> accIDMap = businessDelegate.getAccountIDMaping();
			populateLDRSPAccountIDMapping(accIDMap);
			//if(ldrshipbnsdtls.getLdrshipid() == ldrshipID && !ldrshipbnsdtls.getStatus().equalsIgnoreCase(IWebConstants.INACTIVE)) {
			addActionMessage(IWebConstants.PGM_ACCID_MAP);
			//} 
		} catch (BusinessException be) {
			LOGGER.error("DPB.ACCT.ACT.005"+ "BusinessException occured"+ be.getMessage() + be.getMessageKey());
			actionForward =  IWebConstants.DPB_ERROR_URL;
		} catch (TechnicalException te) {
			LOGGER.error("DPB.ACCT.ACT.006"+ "TechnicalException occured"+ te.getMessage() + te.getMessageKey());
			actionForward = IWebConstants.DPB_ERROR_URL;
			request.setAttribute(IWebConstants.jspExe, te);
		} catch (PersistenceException pe) {
			LOGGER.error("DPB.ACCT.ACT.007"+ "PersistenceException occured"+ pe.getMessage());
			actionForward = IWebConstants.DPB_ERROR_URL;
			request.setAttribute(IWebConstants.jspExe, pe);
		} catch (Exception e) {
			LOGGER.error("DPB.ACCT.ACT.008"+ "Exception occured"+ e.getMessage());
			actionForward = IWebConstants.DPB_ERROR_URL;
			request.setAttribute(IWebConstants.jspExe, e);
		}
		return actionForward;

	}
@SuppressWarnings("unchecked")
public void populateLDRSPAccountIDMapping(Map<String,Object> accIDMap){
	List<VehicleConditionMapping> vcList = (List<VehicleConditionMapping>) accIDMap.get("VC_LIST");
	if(vcList != null){	
		for (VehicleConditionMapping temp : vcList) {				
			if(temp!= null && IWebConstants.PCAR.equalsIgnoreCase(temp.getVehicleId())){
				this.ldrspAccIDMapForm.setPcId(temp.getVehicleId());
				this.ldrspAccIDMapForm.setPcStatus(temp.getStatus());
				this.ldrspAccIDMapForm.setPcConditionList(((ArrayList<String>) temp.getConditionList().get(0)));
				this.ldrspAccIDMapForm.setPcAccountIdList(((ArrayList<String>) temp.getConditionList().get(1)));
				this.ldrspAccIDMapForm.setPcIndicatorList(((ArrayList<String>) temp.getConditionList().get(2)));
				this.ldrspAccIDMapForm.setPcStatusList(((ArrayList<String>) temp.getConditionList().get(3)));				
				this.ldrspAccIDMapForm.setPcLdrspBnsPgmIdList(((ArrayList<Integer>) temp.getConditionList().get(4)));
				pcAccidListSize = this.ldrspAccIDMapForm.getPcLdrspBnsPgmIdList().size();
			}
			if(temp!= null && IWebConstants.SMART.equalsIgnoreCase(temp.getVehicleId())){
				this.ldrspAccIDMapForm.setSmartId(temp.getVehicleId());
				this.ldrspAccIDMapForm.setSmartStatus(temp.getStatus());
				this.ldrspAccIDMapForm.setSmartConditionList(((ArrayList<String>) temp.getConditionList().get(0)));
				this.ldrspAccIDMapForm.setSmartAccountIdList(((ArrayList<String>) temp.getConditionList().get(1)));
				this.ldrspAccIDMapForm.setSmartIndicatorList(((ArrayList<String>) temp.getConditionList().get(2)));
				this.ldrspAccIDMapForm.setSmartStatusList(((ArrayList<String>) temp.getConditionList().get(3)));			
				this.ldrspAccIDMapForm.setSmartLdrspBnsPgmIdList(((ArrayList<Integer>) temp.getConditionList().get(4)));
				smAccidListSize = this.ldrspAccIDMapForm.getSmartLdrspBnsPgmIdList().size();
			}
			if(temp!= null && IWebConstants.VAN.equalsIgnoreCase(temp.getVehicleId())){
				this.ldrspAccIDMapForm.setVanId(temp.getVehicleId());
				this.ldrspAccIDMapForm.setVanStatus(temp.getStatus());
				this.ldrspAccIDMapForm.setVanConditionList(((ArrayList<String>) temp.getConditionList().get(0)));
				this.ldrspAccIDMapForm.setVanAccountIdList(((ArrayList<String>) temp.getConditionList().get(1)));
				this.ldrspAccIDMapForm.setVanIndicatorList(((ArrayList<String>) temp.getConditionList().get(2)));
				this.ldrspAccIDMapForm.setVanStatusList(((ArrayList<String>) temp.getConditionList().get(3)));				
				this.ldrspAccIDMapForm.setVanLdrspBnsPgmIdList(((ArrayList<Integer>) temp.getConditionList().get(4)));
				vanAccidListSize = this.ldrspAccIDMapForm.getVanLdrspBnsPgmIdList().size();
			}
			if(temp!= null && IWebConstants.FTL.equalsIgnoreCase(temp.getVehicleId())){
				this.ldrspAccIDMapForm.setFtlId(temp.getVehicleId());
				this.ldrspAccIDMapForm.setFtlStatus(temp.getStatus());
				this.ldrspAccIDMapForm.setFtlConditionList(((ArrayList<String>) temp.getConditionList().get(0)));
				this.ldrspAccIDMapForm.setFtlAccountIdList(((ArrayList<String>) temp.getConditionList().get(1)));
				this.ldrspAccIDMapForm.setFtlIndicatorList(((ArrayList<String>) temp.getConditionList().get(2)));
				this.ldrspAccIDMapForm.setFtlStatusList(((ArrayList<String>) temp.getConditionList().get(3)));				
				this.ldrspAccIDMapForm.setFtlLdrspBnsPgmIdList(((ArrayList<Integer>) temp.getConditionList().get(4)));
				ftlAccidListSize = this.ldrspAccIDMapForm.getFtlLdrspBnsPgmIdList().size();
			}
		}
	}
}

	public boolean isListEmpty(List<String> checkList) {
		boolean flag = false;
		if (checkList != null && checkList.size() > 0) {
			flag = true;
		}
		return flag;
	}

	public ArrayList<VehicleType> getAccountIDTypeList() {
		return accountIDTypeList;
	}

	public void setAccountIDTypeList(ArrayList<VehicleType> vehicleTypeList) {
		this.accountIDTypeList = accountIDTypeList;
	}

	public LdrspAccountIDMappingForm getLdrspAccIDMapForm() {
		return ldrspAccIDMapForm;
	}

	public void setLdrspAccIDMapForm(LdrspAccountIDMappingForm ldrspAccIDMapForm) {
		this.ldrspAccIDMapForm = ldrspAccIDMapForm;
	}
	
	public int getPcAccidListSize() {
		return pcAccidListSize;
	}

	public void setPcAccidListSize(int pcAccidListSize) {
		this.pcAccidListSize = pcAccidListSize;
	}

	public int getSmAccidListSize() {
		return smAccidListSize;
	}

	public void setSmAccidListSize(int smAccidListSize) {
		this.smAccidListSize = smAccidListSize;
	}

	public int getVanAccidListSize() {
		return vanAccidListSize;
	}

	public void setVanAccidListSize(int vanAccidListSize) {
		this.vanAccidListSize = vanAccidListSize;
	}

	public int getFtlAccidListSize() {
		return ftlAccidListSize;
	}

	public void setFtlAccidListSize(int ftlAccidListSize) {
		this.ftlAccidListSize = ftlAccidListSize;
	}
	

}