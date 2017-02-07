package com.mbusa.dpb.web.common.actions;
/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ConditionListAction.java
 * Program Version			: 1.0
 * Program Description		: This class is handle Condition list request. 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 16, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.ConditionDefinition;
import com.mbusa.dpb.common.domain.ConditionType;
import com.mbusa.dpb.common.domain.CondtionCode;
import com.mbusa.dpb.common.domain.DefStatus;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.form.ConditionDefForm;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class ConditionListAction extends DPBAction {
	
	private static final long serialVersionUID = 1L;
	private BusinessDelegate busDel =  new BusinessDelegate();
	private ConditionDefForm conditionsDefForm;
	private ConditionDefinition condDef;
	private List <ConditionDefinition> cDefList =  new ArrayList<ConditionDefinition>();	
	private List <ConditionDefinition> cDef =  new ArrayList<ConditionDefinition>();
	private String actionForward = IWebConstants.DPB_ERROR_URL;
	private static DPBLog LOGGER = DPBLog.getInstance(ConditionListAction.class);
	
	public String getCondtionsList(){	
		try{
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			actionForward  = "conditionList" ;
			cDef = busDel.getConditionList();
			List<DefStatus> defSts = MasterDataLookup.getInstance().getDefStatusCodes();
			List<ConditionType> cndType = MasterDataLookup.getInstance().getConditionTypes();
			List<CondtionCode> cndCode = MasterDataLookup.getInstance().getConditionCodes();
			if(cDef.isEmpty() || cDef == null){
				addActionError(IWebConstants.NO_PROGRAMS_DEFINED);
			}
			for(ConditionDefinition cBean : cDef){
				cBean.setStatus(WebHelper.getStatusString(cBean.getStatus(),defSts));
				cBean.setCondition(WebHelper.getConditionCode(cBean.getCondition(),cndCode));
				cBean.setConditionType(WebHelper.getConditionType(cBean.getConditionType(),cndType));
				cDefList.add(cBean);
				}
			
		}catch(BusinessException be){
			LOGGER.info("BusinessException occured");
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (TechnicalException te) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, te);
		}
		catch (PersistenceException pe) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, pe);
		}
		catch (Exception e) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, e);
		}
		return actionForward;
	}
	public String getConditionView(){		
		try {
			//busDel.getCondtionDetails(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "conditionView";	
	}	
	
	
	
	public ConditionDefForm getConditionsDefForm() {
		return conditionsDefForm;
	}
	public void setConditionsDefForm(ConditionDefForm conditionsDefForm) {
		this.conditionsDefForm = conditionsDefForm;
	}
	public ConditionDefinition getCondDef() {
		return condDef;
	}
	public void setCondDef(ConditionDefinition condDef) {
		this.condDef = condDef;
	}	
	public List<ConditionDefinition> getCDefList() {
		return cDefList;
	}
	public void setCDefList(List<ConditionDefinition> cDefList) {
		this.cDefList = cDefList;
	}

	public BusinessDelegate getBusDel() {
		return busDel;
	}

	public void setBusDel(BusinessDelegate busDel) {
		this.busDel = busDel;
	}

	public String getActionForward() {
		return actionForward;
	}

	public void setActionForward(String actionForward) {
		this.actionForward = actionForward;
	}
	
}