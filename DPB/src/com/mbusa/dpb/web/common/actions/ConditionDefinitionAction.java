/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: ConditionDefinitionAction.java
 * Program Version			: 1.0
 * Program Description		: This class is handle all request creating and display conditions. 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 16, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.common.actions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.constant.IConstants;
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

public class ConditionDefinitionAction extends DPBAction {

	

	private static final long serialVersionUID = 1L;
	private static DPBLog LOGGER = DPBLog.getInstance(ConditionDefinitionAction.class);
	private ConditionDefForm conditionsDefForm = null;
	private ConditionDefinition conditionDef = null;
	private BusinessDelegate busDel =  new BusinessDelegate();
	private String defId;
	private String conditionView;
	private String message ="";
	private String actionForward = IWebConstants.DPB_ERROR_URL;
	private List<ConditionType> condType =  new ArrayList<ConditionType>();
	private List<CondtionCode> condCode =  new ArrayList<CondtionCode>();
	private List<DefStatus> defSts = new ArrayList<DefStatus>();
	boolean fromDPBList;
	
	public String viewCondition(){
	try
	{		
		this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
		condType = MasterDataLookup.getInstance().getConditionTypes();
		condCode = MasterDataLookup.getInstance().getConditionCodes();
		defSts = MasterDataLookup.getInstance().getDefStatusCodes();
		conditionsDefForm = new ConditionDefForm();
		conditionsDefForm.setStatus(IConstants.STATUS_D);
		actionForward =  "conditionDefView";
		
		if(defId != null ){
			fromDPBList = true;
			int id = Integer.parseInt(defId);
			conditionDef = busDel.getCondtionDetails(id);	
			populateConditionDefForm();		
			if(IConstants.STATUS_A.equalsIgnoreCase(conditionDef.getStatus()) || (IConstants.STATUS_I.equalsIgnoreCase(conditionDef.getStatus()))) {
				conditionsDefForm.setFlagActive(true);
			}
			if("view".equals(conditionView)){
				actionForward =  "conditionDefReadOnlyView";
				this.conditionView = "";
			}
		}
		
	}catch(BusinessException be){
		LOGGER.error("DPB.CND.ACT.001"+ "BusinessException occured"+ be.getMessage() + be.getMessageKey());
		actionForward =  IWebConstants.DPB_ERROR_URL;
	}catch (TechnicalException te) {
		LOGGER.error("DPB.CND.ACT.002"+ "Techical Exception occured"+ te.getMessage() +te.getMessageKey());
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, te);
	}
	catch (PersistenceException pe) {
		LOGGER.error("DPB.CND.ACT.003"+ "PersistenceException occured"+ pe.getMessage());
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, pe);
	}
	catch (Exception e) {
		LOGGER.error("DPB.CND.ACT.004"+ "Exception occured"+ e.getMessage());
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, e);
	}
	return actionForward;
}

public String saveCondition(){	
	try {	
		actionForward = "blockConditionDefine";
		condType = MasterDataLookup.getInstance().getConditionTypes();
		condCode = MasterDataLookup.getInstance().getConditionCodes();
		defSts = MasterDataLookup.getInstance().getDefStatusCodes();
		populateConditionDefinition();
		validateCondition();//conditionDef);
		int conditionId = conditionsDefForm.getId();
		if(hasActionErrors()){
			actionForward = "blockConditionDefine";
			return actionForward;
		}else {
		busDel.createCondition(conditionDef);
		}
		if(IConstants.STATUS_A.equalsIgnoreCase(conditionDef.getStatus()) || (IConstants.STATUS_I.equalsIgnoreCase(conditionDef.getStatus()))) {
			conditionsDefForm.setFlagActive(true);
		}
		if(conditionId != conditionDef.getId()){
			addActionError(" Condition Created Successfully.");
		}else {
			addActionError(" Condition Updated Successfully.");
		}
		populateConditionID();
	}catch(BusinessException be){
		LOGGER.error("DPB.CND.ACT.005"+ "BusinessException occured"+ be.getMessage() + be.getMessageKey());
		addActionError(be.getMessage());
		if("Warning1".equalsIgnoreCase(be.getMessageKey())){
			conditionsDefForm.setStatus("A");
			actionForward =  "blockConditionDefine";
		}
	}catch (TechnicalException te) {
		LOGGER.error("DPB.CND.ACT.006"+ "TechnicalException occured"+ te.getMessage() + te.getMessageKey());
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, te);
	}
	catch (PersistenceException pe) {
		LOGGER.error("DPB.CND.ACT.007"+ "PersistenceException occured"+ pe.getMessage());
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, pe);
	}
	catch (Exception e) {
		LOGGER.error("DPB.CND.ACT.008"+ "Exception occured"+ e.getMessage());
		if(e.getMessage().contains("DuplicateKeyException")){
			actionForward =  "blockConditionDefine";
			addActionError(" Condition should have unique Variable Name, Condition Type, Condition Code and Check Value/Regular Expression");
		}else{
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, e);
		}
	}
	return actionForward;	
}

private void validateCondition() throws TechnicalException {
	if(conditionsDefForm.getId()>0 && IConstants.STATUS_A.equalsIgnoreCase(conditionsDefForm.getStatus()) && conditionsDefForm.isFlagActive()){
		addActionError(" Cannot re-submit an Active Condition.");
	}
	if(conditionsDefForm.getId()==0 && IConstants.STATUS_I.equalsIgnoreCase(conditionsDefForm.getStatus())){
		addActionError(" Cannot create a new Inactive Condition.");
	}/*
	if(conditionsDefForm.getId()==0){
	boolean isValidCondition = busDel.validateCondition(conditionDef);
	
	if(isValidCondition){
		addActionError(" Condition should have unique Variable Name, Condition Type, Condition Code and Check Value");
		addActionError("  Please refer Condition Definition List");
	}
	}
	*/
	
}

public String resetCondition(){	
	try{
		condType = MasterDataLookup.getInstance().getConditionTypes();
		condCode = MasterDataLookup.getInstance().getConditionCodes();
		defSts = MasterDataLookup.getInstance().getDefStatusCodes();
		if(defId != null ){
			int id = Integer.parseInt(defId);
			conditionDef = busDel.getCondtionDetails(id);	
			populateConditionDefForm();	
			actionForward = "conditionReset";
		}
	}catch(BusinessException be){
		LOGGER.error("DPB.CND.ACT.009"+ "BusinessException occured"+ be.getMessage() +be.getMessageKey());
		actionForward =  IWebConstants.DPB_ERROR_URL;
	}catch (TechnicalException te) {
		LOGGER.error("DPB.CND.ACT.010"+ "TechnicalException occured"+ te.getMessage() +te.getMessageKey());
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, te);
	}
	catch (PersistenceException pe) {
		LOGGER.error("DPB.CND.ACT.011"+ "PersistencException occured"+ pe.getMessage());
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, pe);
	}
	catch (Exception e) {
		LOGGER.error("DPB.CND.ACT.012"+ "Exception occured"+ e.getMessage());
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, e);
	}
	return actionForward;
}

public String cancelcondition(){

	try {
		// Return to Dashboard when a program is cancelled.
	}catch (PersistenceException pe) {
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, pe);
	}
	catch (Exception e) {
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, e);
	}
	return "HomePage";
}

	public void populateConditionDefForm(){
		conditionsDefForm = new ConditionDefForm();
		conditionsDefForm.setConditionName(WebHelper.makeNonNullString(conditionDef.getConditionName()).trim());
		
		conditionsDefForm.setConditionDesc(WebHelper.makeNonNullString(conditionDef.getConditionDesc()).trim());
		conditionsDefForm.setId(conditionDef.getId());
		
		conditionsDefForm.setCheckValue(WebHelper.makeNonNullString(conditionDef.getCheckValue()).trim());
		conditionsDefForm.setRegularExp(WebHelper.makeNonNullString(conditionDef.getRegularExp()).trim());
		conditionsDefForm.setVariableName(WebHelper.makeNonNullString(conditionDef.getVariableName()).trim());
		conditionsDefForm.setStatusChange(conditionDef.getStatus());
		if("view".equals(conditionView)){
			conditionsDefForm.setCondition(WebHelper.getConditionCode(conditionDef.getCondition(),condCode));
			conditionsDefForm.setConditionType(WebHelper.getConditionType(conditionDef.getConditionType(),condType));
			conditionsDefForm.setStatus(WebHelper.getStatusString(conditionDef.getStatus(),defSts));
			
		}else {
			conditionsDefForm.setCondition(conditionDef.getCondition());
			conditionsDefForm.setConditionType(conditionDef.getConditionType());
			conditionsDefForm.setStatus(conditionDef.getStatus());
			
		}
	}

	public void populateConditionDefinition(){
		conditionDef =  new ConditionDefinition();
		conditionDef.setConditionName(conditionsDefForm.getConditionName());
		conditionDef.setCheckValue(conditionsDefForm.getCheckValue());
		conditionDef.setConditionDesc(conditionsDefForm.getConditionDesc());
		conditionDef.setCondition(conditionsDefForm.getCondition());
		conditionDef.setId(conditionsDefForm.getId());
		conditionDef.setConditionType(conditionsDefForm.getConditionType());
		conditionDef.setRegularExp(conditionsDefForm.getRegularExp());
		conditionDef.setVariableName(conditionsDefForm.getVariableName());
		conditionDef.setStatus(conditionsDefForm.getStatus());
		conditionDef.setCurrentUser(this.getUserId());
		conditionDef.setFlagActive(conditionsDefForm.isFlagActive());
	}
	public void populateConditionID(){
		conditionsDefForm.setId(conditionDef.getId());
		conditionsDefForm.setStatusChange(WebHelper.makeNonNullString(conditionDef.getStatus()));
	}
	public ConditionDefForm getConditionsDefForm() {
		return conditionsDefForm;
	}
	
	public void setConditionsDefForm(ConditionDefForm conditionsDefForm) {
		this.conditionsDefForm = conditionsDefForm;
	}
	public String getDefId() {
		return defId;
	}	
	public void setDefId(String defId) {
		this.defId = defId;
	}
	public String getConditionView() {
		return conditionView;
	}
	
	public void setConditionView(String conditionView) {
		this.conditionView = conditionView;
	}

	public List<ConditionType> getCondType() {
		return condType;
	}

	public void setCondType(List<ConditionType> condType) {
		this.condType = condType;
	}

	public List<CondtionCode> getCondCode() {
		return condCode;
	}

	public void setCondCode(List<CondtionCode> condCode) {
		this.condCode = condCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the fromDPBList
	 */
	public boolean isFromDPBList() {
		return fromDPBList;
	}

	/**
	 * @param fromDPBList the fromDPBList to set
	 */
	public void setFromDPBList(boolean fromDPBList) {
		this.fromDPBList = fromDPBList;
	}

	/**
	 * @return the defSts
	 */
	public List<DefStatus> getDefSts() {
		return defSts;
	}

	/**
	 * @param defSts the defSts to set
	 */
	public void setDefSts(List<DefStatus> defSts) {
		this.defSts = defSts;
	}	
}