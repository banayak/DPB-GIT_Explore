/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: DealerProgramAction.java
 * Program Version			: 1.0
 * Program Description		: 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 27, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/

package com.mbusa.dpb.web.common.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.ConditionDefinition;
import com.mbusa.dpb.common.domain.CondtionCode;
import com.mbusa.dpb.common.domain.DefStatus;
import com.mbusa.dpb.common.domain.Kpi;
import com.mbusa.dpb.common.domain.ProgramDefinition;
import com.mbusa.dpb.common.domain.RtlCalenderDefinition;
import com.mbusa.dpb.common.domain.Scheduler;
import com.mbusa.dpb.common.domain.Trigger;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.form.ProgramDefinitionForm;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class DealerProgramAction extends DPBAction {
	private static final String VIEW = "view";
	private static DPBLog LOGGER = DPBLog.getInstance(DealerProgramAction.class);
	static final private String CLASSNAME = DealerProgramAction.class.getName();
	private static final long serialVersionUID = 1L;
	private ProgramDefinitionForm dlrPrgmForm;
	private ProgramDefinition dlrPrgm;
	private BusinessDelegate businessDel = new BusinessDelegate();
	List<ProgramDefinition> prgList= new ArrayList<ProgramDefinition>();
	List<ProgramDefinition> prgDealerList= new ArrayList<ProgramDefinition>();
	private int programIdD;
	private String programListView;
	private String actionForward = IConstants.EMPTY_STR;
	private List <Kpi>kpiList = new ArrayList<Kpi>(); 
	private List <VehicleType>vehicleList = new ArrayList<VehicleType>(); 
	private List<CondtionCode> vehicleCond = new ArrayList <CondtionCode>();	 
	private List<ConditionDefinition> specialConditions = new ArrayList <ConditionDefinition>();	 
	private List<Trigger> triggerList = new ArrayList<Trigger>();
	private List<Scheduler> defSchd = new ArrayList<Scheduler>();
	private List<DefStatus> defSts = new ArrayList<DefStatus>();
	boolean adminUser;
	boolean fromDPBList;
	public String submitDealerProgram(){
		LOGGER.enter(CLASSNAME, "submitDealerProgram()");
		try {
			loadCache();
			if(IWebConstants.ADMIN_ROLE_CODE.equalsIgnoreCase(this.getUserRole())){
				setAdminUser(true);
			}
			if(dlrPrgmForm!=null){ 
				actionForward = "dealerPrgView";
				Map<String, List<java.sql.Date>> actualProcDteListMap = new HashMap<String, List<java.sql.Date>>();
				dlrPrgm = new ProgramDefinition();
				dlrPrgmForm.setMasterCondList(specialConditions);
				dlrPrgm = populatePrgDefinition(dlrPrgmForm,dlrPrgm);
				int programId = dlrPrgm.getProgramId();
				boolean flag = validateDealerProgram();
				if(!flag){
					if(IConstants.STATUS_A.equalsIgnoreCase(dlrPrgm.getProgramStatus())){
						actualProcDteListMap = decideExecutionProcessesDates(dlrPrgm);
					}
				}
				if(hasActionErrors()){
					actionForward = "dealerPrgView";
					return actionForward;
				}
				else{
					businessDel.submitDealerProgram(dlrPrgm,actualProcDteListMap);
					dlrPrgm.setMasterCondList(specialConditions);
					populatePrgDefinitionForm(dlrPrgmForm,dlrPrgm);
					if (IConstants.STATUS_A.equalsIgnoreCase(dlrPrgmForm.getProgramStatus()) || (IConstants.STATUS_I.equalsIgnoreCase(dlrPrgmForm.getProgramStatus()))) {
						dlrPrgmForm.setFlagActive(true);
					}
					if(programId != dlrPrgm.getProgramId()){
						addActionMessage("Program Created Successfully ");
					} else {
						addActionMessage("Program Updated Successfully ");	
					}
					if(dlrPrgm.getMessages()!=null){
						addActionError(dlrPrgm.getMessages().get(0));
						addActionError(dlrPrgm.getMessages().get(1));
					}
				}
				actionForward =  "dealerPrgView";
			}
		} catch(BusinessException be){
			if("DAY_FEDERATED_EXCEPTION".equals(be.getMessageKey())){ 
				request.setAttribute (IWebConstants.jspExe, be);
				actionForward =  IWebConstants.DPB_ERROR_URL;
			}else{
			LOGGER.error("DPB.PGM.ACT.001"+ "BusinessException occured"+ be.getMessage() + be.getMessageKey());
			addActionError(be.getMessage());
			//request.setAttribute (IWebConstants.jspExe, be);
			actionForward =  "dealerPrgView";
			}
		}catch (TechnicalException te) {
			LOGGER.error("DPB.PGM.ACT.002"+ "TechnicalException occured"+ te.getMessage() + te.getMessageKey());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, te);
		}
		catch (PersistenceException pe) {
			LOGGER.error("DPB.PGM.ACT.003"+ "PersistenceException occured"+ pe.getMessage());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, pe);
		}
		catch (Exception e) {
			LOGGER.error("DPB.PGM.ACT.004"+ "Exception occured"+ e.getMessage());
			if(e.getMessage().contains("DuplicateKeyException")){
				actionForward =  "dealerPrgView";
				addActionError("Program Name and Start Date should be unique");
			}else{
				actionForward =  IWebConstants.DPB_ERROR_URL;
				request.setAttribute (IWebConstants.jspExe, e);
			}
		}
		LOGGER.exit(CLASSNAME, "submitDealerProgram() ");
		return actionForward;	
	}
	public Map<String, List<java.sql.Date>> decideExecutionProcessesDates(ProgramDefinition programDef) throws BusinessException{
		LOGGER.enter(CLASSNAME, "decideExecutionProcessesDates()");
		String scheduleProcess =IConstants.EMPTY_STR;
		List<java.sql.Date> datesList = null;
		List<java.sql.Date> aSchdDatesList = new ArrayList<java.sql.Date>();
		boolean flag = false;
		int updateCount = 0;
		int batchSize =0;

		if(IConstants.SPECIAL_PGM.equalsIgnoreCase(programDef.getSpecialProgram())){
			batchSize = 6;
		}else {
			batchSize = 2;
		}		
		Map<String, List<java.sql.Date>> procDteListMap = new HashMap<String, List<java.sql.Date>>();
		while(batchSize!=updateCount){
			if(!IConstants.SPECIAL_PGM.equalsIgnoreCase(programDef.getSpecialProgram())){
				datesList = new ArrayList<java.sql.Date>();
				if(programDef.getFixedPayment() > 0){
					if(!flag && updateCount==0){		
						datesList = new ArrayList<java.sql.Date>();
						aSchdDatesList = new ArrayList<java.sql.Date>();
						scheduleProcess = programDef.getFixedPymtBonusSchedule();
						if(!scheduleProcess.isEmpty()){
							datesList = DateCalenderUtil.scheduleList(programDef.getStartDate(), programDef.getEndDate(), scheduleProcess);
						}
						if(datesList == null || datesList.size()==0 && !scheduleProcess.isEmpty()){
							addActionError("DPB Process could not be scheduled");
							break;
						}
						if(!datesList.isEmpty()){
							flag = true;						
							programDef.setDpbProcess(IConstants.FIXED_BONUS_PROCESS);
						}
					}
					if(!flag && updateCount==1){
						datesList = new ArrayList<java.sql.Date>();
						aSchdDatesList = new ArrayList<java.sql.Date>();
						scheduleProcess = programDef.getFixedPymtSchedule();
						if(!scheduleProcess.isEmpty()){		
							datesList = DateCalenderUtil.scheduleList(programDef.getStartDate(), programDef.getEndDate(), scheduleProcess);
						}
						if(datesList == null || datesList.size()==0 && !scheduleProcess.isEmpty()){
							addActionError("DPB Process could not be scheduled");
							break;
						}
						if(!datesList.isEmpty()){
							flag = true;
							programDef.setDpbProcess(IConstants.FIXED_PAYMENT_PROCESS);
						}
					}
				}else {	
					if(programDef.getVariablePayment() > 0){
						datesList = new ArrayList<java.sql.Date>();
						aSchdDatesList = new ArrayList<java.sql.Date>();
						if(!flag && updateCount==0){
							scheduleProcess = programDef.getVariablePymtBonusSchedule();
							if(!scheduleProcess.isEmpty()){
								datesList = DateCalenderUtil.scheduleList(programDef.getStartDate(), programDef.getEndDate(), scheduleProcess);
							}
							if(datesList == null || datesList.size()==0 && !scheduleProcess.isEmpty()){
								addActionError("DPB Process could not be scheduled");
								break;
							}
							if(!datesList.isEmpty()){
								flag = true;
								programDef.setDpbProcess(IConstants.VARIABLE_BONUS_PROCESS);
							}
						}

						if(!flag && updateCount==1){
							datesList = new ArrayList<java.sql.Date>();
							aSchdDatesList = new ArrayList<java.sql.Date>();
							scheduleProcess = programDef.getVariablePymtSchedule();
							if(!scheduleProcess.isEmpty()){
								datesList = DateCalenderUtil.scheduleList(programDef.getStartDate(), programDef.getEndDate(), scheduleProcess);
							}
							if(datesList == null || datesList.size()==0 && !scheduleProcess.isEmpty()){
								addActionError("DPB Process could not be scheduled");
								break;
							}
							if(!datesList.isEmpty()){
								flag = true;
								programDef.setDpbProcess(IConstants.VARIABLE_PAYMENT_PROCESS);
							}
						}
					}
				}
			}
			//IF SPECIAL PROGRAM
			if(IConstants.SPECIAL_PGM.equalsIgnoreCase(programDef.getSpecialProgram())){
				if(!flag && updateCount==0){
					if(IConstants.CONSTANT_Y.equalsIgnoreCase(programDef.getRebateAmt()) || programDef.getCommAmount()>0 ) //removed programDef.getCommPercent()>0 
					{
						datesList = new ArrayList<java.sql.Date>();
						aSchdDatesList = new ArrayList<java.sql.Date>();
						scheduleProcess = programDef.getComsnProcessSchedule();
						if(!scheduleProcess.isEmpty()){
							datesList = DateCalenderUtil.scheduleList(programDef.getStartDate(), programDef.getEndDate(), scheduleProcess);
						}
						if(datesList == null || datesList.size()==0 && !scheduleProcess.isEmpty()){
							addActionError("DPB Process could not be scheduled");
							break;
						}
						if(!datesList.isEmpty()){
							flag = true;
							programDef.setDpbProcess(IConstants.COMMISSION_BONUS_PROCESS);
						}
					}
				}
				if(!flag && updateCount==1){
					if(IConstants.CONSTANT_Y.equalsIgnoreCase(programDef.getRebateAmt()) || ( programDef.getCommAmount()>0)){ //removed programDef.getCommPercent()>0 
						datesList = new ArrayList<java.sql.Date>();
						aSchdDatesList = new ArrayList<java.sql.Date>();
						scheduleProcess = programDef.getComsnPymtSchedule();
						if(!scheduleProcess.isEmpty()){
							datesList = DateCalenderUtil.scheduleList(programDef.getStartDate(), programDef.getEndDate(), scheduleProcess);
						}
						if(datesList == null || datesList.size()==0 && !scheduleProcess.isEmpty()){
							addActionError("DPB Process could not be scheduled");
							break;
						}
						if(!datesList.isEmpty()){
							flag = true;
							programDef.setDpbProcess(IConstants.COMMISSION_PAYMENT_PROCESS);
						}
					}
				}
				if(!flag && updateCount==2){
					//if(IConstants.CONSTANT_Y.equalsIgnoreCase(programDef.getRebateAmt()) || programDef.getCommAmount()>0 ) //removed programDef.getCommPercent()>0
					datesList = new ArrayList<java.sql.Date>();
					aSchdDatesList = new ArrayList<java.sql.Date>();
					scheduleProcess = programDef.getFixedPymtBonusSchedule();
					if(!scheduleProcess.isEmpty()){
						datesList = DateCalenderUtil.scheduleList(programDef.getStartDate(), programDef.getEndDate(), scheduleProcess);
					}
					if(datesList == null || datesList.size()==0 && !scheduleProcess.isEmpty()){
						addActionError("DPB Process could not be scheduled");
						break;
					}
					if(!datesList.isEmpty()){
						flag = true;
						programDef.setDpbProcess(IConstants.FIXED_BONUS_PROCESS);
					}
				}
				if(!flag && updateCount==3){
					//if(IConstants.CONSTANT_Y.equalsIgnoreCase(programDef.getRebateAmt()) || ( programDef.getCommAmount()>0)){ //removed programDef.getCommPercent()>0
					datesList = new ArrayList<java.sql.Date>();
					aSchdDatesList = new ArrayList<java.sql.Date>();
					scheduleProcess = programDef.getFixedPymtSchedule();
					if(!scheduleProcess.isEmpty()){
						datesList = DateCalenderUtil.scheduleList(programDef.getStartDate(), programDef.getEndDate(), scheduleProcess);
					}
					if(datesList == null || datesList.size()==0 && !scheduleProcess.isEmpty()){
						addActionError("DPB Process could not be scheduled");
						break;
					}
					if(!datesList.isEmpty()){
						flag = true;
						programDef.setDpbProcess(IConstants.FIXED_PAYMENT_PROCESS);
					}
				}
				if(!flag && updateCount==4){
					datesList = new ArrayList<java.sql.Date>();
					aSchdDatesList = new ArrayList<java.sql.Date>();
					scheduleProcess = programDef.getVariablePymtBonusSchedule();
					if(!scheduleProcess.isEmpty()){
						datesList = DateCalenderUtil.scheduleList(programDef.getStartDate(), programDef.getEndDate(), scheduleProcess);
					}
					if(datesList == null || datesList.size()==0 && !scheduleProcess.isEmpty()){
						addActionError("DPB Process could not be scheduled");
						break;
					}
					if(!datesList.isEmpty()){
						flag = true;
						programDef.setDpbProcess(IConstants.VARIABLE_BONUS_PROCESS);
					}
				}
				if(!flag && updateCount==5){
					datesList = new ArrayList<java.sql.Date>();
					aSchdDatesList = new ArrayList<java.sql.Date>();
					scheduleProcess = programDef.getVariablePymtSchedule();
					if(!scheduleProcess.isEmpty()){
						datesList = DateCalenderUtil.scheduleList(programDef.getStartDate(), programDef.getEndDate(), scheduleProcess);
					}
					if(datesList == null || datesList.size()==0 && !scheduleProcess.isEmpty()){
						addActionError("DPB Process could not be scheduled");
						break;
					}
					if(!datesList.isEmpty()){
						flag = true;
						programDef.setDpbProcess(IConstants.VARIABLE_PAYMENT_PROCESS);
					}
				}
			}
			if(flag){
				if(scheduleProcess.equalsIgnoreCase(IConstants.SCHD_MONTH) || scheduleProcess.equalsIgnoreCase(IConstants.SCHD_QUERTERLY) || scheduleProcess.equalsIgnoreCase(IConstants.SCHD_YEARLY)){
					for(Iterator<java.sql.Date> sDate = datesList.iterator();sDate.hasNext();){
						java.sql.Date schdDate = sDate.next();
						java.sql.Date schdActualDate = null;
						RtlCalenderDefinition rtlDef = MasterDataLookup.getInstance().getRtlCalByDate(schdDate);

						if(rtlDef.getDteRetlYearEnd() == null || rtlDef.getDteRetlQtrEnd() == null || rtlDef.getDteRetlMthEnd() == null){
							throw new BusinessException("DAY_FEDERATED_EXCEPTION","Day Federated Data is not available to schedule processes");
						}else{
							if(scheduleProcess.equalsIgnoreCase(IConstants.SCHD_MONTH)){
								schdActualDate =DateCalenderUtil.scheduleNextDate(rtlDef.getDteRetlMthEnd());
								if(schdActualDate == null){
									addActionError("DPB Process could not be scheduled");
									break;
								}else{
									aSchdDatesList.add(schdActualDate);
								}

							}else if(scheduleProcess.equalsIgnoreCase(IConstants.SCHD_QUERTERLY)){
								schdActualDate =DateCalenderUtil.scheduleNextDate(rtlDef.getDteRetlQtrEnd());
								if(schdActualDate == null){
									addActionError("DPB Process could not be scheduled");
									break;
								}else{
									aSchdDatesList.add(schdActualDate);
								}
							}else if(scheduleProcess.equalsIgnoreCase(IConstants.SCHD_YEARLY)){
								schdActualDate =DateCalenderUtil.scheduleNextDate(rtlDef.getDteRetlYearEnd());
								if(schdActualDate == null){
									addActionError("DPB Process could not be scheduled");
									break;
								}else{
									aSchdDatesList.add(schdActualDate);
								}
							}/*else if(scheduleProcess.equalsIgnoreCase(IConstants.SCHD_WEEKLY)){
						schdActualDate =DateCalenderUtil.scheduleNextDate(rtlDef.getDteRetlWeekEnd());
						if(schdActualDate == null){
							addActionError("DPB Process could not be scheduled");
							break;
						}else{
						aSchdDatesList.add(schdActualDate);
						}
					}*/
						}
					}
				}else{
					aSchdDatesList = datesList;
				}
				procDteListMap.put(programDef.getDpbProcess(), aSchdDatesList);

			}
			updateCount++;
			flag = false;
		}
		LOGGER.exit(CLASSNAME, "decideExecutionProcessesDates()");
		return procDteListMap;
	}
	public String cancelDealerProgram(){

		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "dashBoardView";
	}

	public String viewDealerProgram() {
		LOGGER.enter(CLASSNAME, "viewDealerProgram()");
		try{
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			loadCache();
			actionForward = "dealerPrgView";
			if(IWebConstants.ADMIN_ROLE_CODE.equalsIgnoreCase(this.getUserRole())){
				setAdminUser(true);
			}
			if(dlrPrgmForm == null){
				dlrPrgmForm = new ProgramDefinitionForm();
				dlrPrgmForm.setProgramStatus(IConstants.STATUS_D);
				dlrPrgmForm.setMasterCondList(specialConditions);
			}else if (dlrPrgmForm!=null){ 
				fromDPBList = true;
				dlrPrgm = new ProgramDefinition();		
				dlrPrgmForm.setProgramId(programIdD); 
				dlrPrgm = populatePrgDefinition(dlrPrgmForm,dlrPrgm);
				businessDel.getDlrPrograms(dlrPrgm);
				dlrPrgm.setMasterCondList(specialConditions);
				populatePrgDefinitionForm(dlrPrgmForm,dlrPrgm);
			}			
			if(VIEW.equalsIgnoreCase(programListView) && !IConstants.EMPTY_STR.equalsIgnoreCase(programListView))
			{
				if(IConstants.SPECIAL_PGM.equalsIgnoreCase(dlrPrgm.getSpecialProgram())){
					actionForward = "retrieveSpecialDlrProgram";
				}else {
					actionForward = "retrieveDealerProgram";
				}	
				this.programListView = "";
			} else{
				if (IConstants.STATUS_A.equalsIgnoreCase(dlrPrgmForm.getProgramStatus()) || (IConstants.STATUS_I.equalsIgnoreCase(dlrPrgmForm.getProgramStatus()))) {
					dlrPrgmForm.setFlagActive(true);
				}
				actionForward = "dealerPrgView";
			}		
		}catch(BusinessException be){
			LOGGER.error("DPB.PGM.ACT.005"+ "BusinessException occured"+ be.getMessage() + be.getMessageKey());
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (TechnicalException te) {
			LOGGER.error("DPB.PGM.ACT.006"+ "TechnicalException occured"+ te.getMessage() + te.getMessageKey());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, te);
		}
		catch (PersistenceException pe) {
			LOGGER.error("DPB.PGM.ACT.007"+ "PersistenceException occured"+ pe.getMessage() );
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, pe);
		}
		catch (Exception e) {
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, e);
		}
		LOGGER.exit(CLASSNAME, "viewDealerProgram()");
		return actionForward;
	}

	public String viewDealerListProgram(){
		LOGGER.enter(CLASSNAME, "viewDealerListProgram()");
		this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
		try {
			loadCache();
			if(IWebConstants.ADMIN_ROLE_CODE.equalsIgnoreCase(this.getUserRole())){
				setAdminUser(true);
			}
			actionForward = "viewPrgList";
			prgDealerList = businessDel.getDlrProgramsList();
			if(prgDealerList.isEmpty() || prgDealerList == null){
				addActionError(IWebConstants.NO_PROGRAMS_DEFINED);
			}
			for(ProgramDefinition dlrPrgm : prgDealerList){
				dlrPrgm.setProgramStatus(WebHelper.getStatusString(dlrPrgm.getProgramStatus(),defSts));
				dlrPrgm.setKpiValue(WebHelper.getKPIValue(dlrPrgm.getKpiId(),kpiList));
				prgList.add(dlrPrgm);
			}
		}catch(BusinessException be){
			LOGGER.error("DPB.PGM.ACT.008"+ "BusinessException occured"+ be.getMessage() + be.getMessageKey());
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (TechnicalException te) {
			LOGGER.error("DPB.PGM.ACT.009"+ "TechinicalException occured"+ te.getMessage() + te.getMessageKey());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, te);
		}
		catch (PersistenceException pe) {
			LOGGER.error("DPB.PGM.ACT.010"+ "PersistenceException occured"+ pe.getMessage());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, pe);
		}
		catch (Exception e) {
			LOGGER.error("DPB.PGM.ACT.011"+ "Exception occured"+ e.getMessage());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, e);
		}
		LOGGER.exit(CLASSNAME, "viewDealerListProgram()");
		return actionForward;
	}
	private void populatePrgDefinitionForm(ProgramDefinitionForm dlrPrgmForm,ProgramDefinition dlrPrgm) {
		LOGGER.enter(CLASSNAME, "populatePrgDefinitionForm()");
		dlrPrgmForm.setProgramId(dlrPrgm.getProgramId());
		dlrPrgm.setFlagActive(dlrPrgm.isFlagActive());

		dlrPrgmForm.setProgramName(WebHelper.makeNonNullString(dlrPrgm.getProgramName()).trim());
		dlrPrgmForm.setDescription(dlrPrgm.getDescription().trim());
		dlrPrgmForm.setStartDate(WebHelper.makeNonNullString(DateCalenderUtil.convertDateToString(dlrPrgm.getStartDate())).trim());
		dlrPrgmForm.setEndDate(WebHelper.makeNonNullString(DateCalenderUtil.convertDateToString(dlrPrgm.getEndDate())).trim());

		dlrPrgmForm.setFixedPayment(dlrPrgm.getFixedPayment());
		dlrPrgmForm.setVariablePayment(dlrPrgm.getVariablePayment());

		dlrPrgmForm.setCommAmount(dlrPrgm.getCommAmount());
		//dlrPrgmForm.setCommPercent(dlrPrgm.getCommPercent());

		dlrPrgmForm.setSpecialProgram(dlrPrgm.getSpecialProgram());
		dlrPrgmForm.setMasterCondList(dlrPrgm.getMasterCondList());
		dlrPrgmForm.setInactiveDate(WebHelper.makeNonNullString(DateCalenderUtil.convertDateToString(dlrPrgm.getInactiveDate())).trim());

		if(VIEW.equalsIgnoreCase(programListView) && !IConstants.EMPTY_STR.equalsIgnoreCase(programListView)){
			dlrPrgmForm.setVehicleType(WebHelper.getVehicleName(dlrPrgm.getVehicleType(),vehicleList));
			dlrPrgmForm.setCondition(WebHelper.getBlockedConditionName(dlrPrgm.getCondition(),specialConditions));
			dlrPrgmForm.setKpiValue(WebHelper.getKPIValue(dlrPrgm.getKpiId(),kpiList));
			dlrPrgmForm.setProgramStatus(WebHelper.getStatusString(dlrPrgm.getProgramStatus(),defSts));

			dlrPrgmForm.setComsnPymtSchedule(WebHelper.getScheduleString(dlrPrgm.getComsnPymtSchedule(),defSchd));
			dlrPrgmForm.setComsnPymtTrigger(WebHelper.getTriggerString(dlrPrgm.getComsnPymtTrigger(),triggerList));
			dlrPrgmForm.setFixedPymtSchedule(WebHelper.getScheduleString(dlrPrgm.getFixedPymtSchedule(),defSchd));
			dlrPrgmForm.setFixedPymtTrigger(WebHelper.getTriggerString(dlrPrgm.getFixedPymtTrigger(),triggerList));
			dlrPrgmForm.setVariablePymtSchedule(WebHelper.getScheduleString(dlrPrgm.getVariablePymtSchedule(),defSchd));
			dlrPrgmForm.setVariablePymtTrigger(WebHelper.getTriggerString(dlrPrgm.getVariablePymtTrigger(),triggerList));

			dlrPrgmForm.setComsnProcessSchedule(WebHelper.getScheduleString(dlrPrgm.getComsnProcessSchedule(),defSchd));
			dlrPrgmForm.setComsnProcessTrigger(WebHelper.getTriggerString(dlrPrgm.getComsnProcessTrigger(),triggerList));
			dlrPrgmForm.setFixedPymtBonusSchedule(WebHelper.getScheduleString(dlrPrgm.getFixedPymtBonusSchedule(),defSchd));
			dlrPrgmForm.setFixedPymtBonusTrigger(WebHelper.getTriggerString(dlrPrgm.getFixedPymtBonusTrigger(),triggerList));
			dlrPrgmForm.setVariablePymtBonusSchedule(WebHelper.getScheduleString(dlrPrgm.getVariablePymtBonusSchedule(),defSchd));
			dlrPrgmForm.setVariablePymtBonusTrigger(WebHelper.getTriggerString(dlrPrgm.getVariablePymtBonusTrigger(),triggerList));

			dlrPrgmForm.setPaymentType(WebHelper.getPaymentType(dlrPrgm.getPaymentType()));
			dlrPrgmForm.setRebateAmt(WebHelper.getYesOrNoString(dlrPrgm.getRebateAmt()));
			dlrPrgmForm.setMaxVarPayment(WebHelper.getYesOrNoString(dlrPrgm.getMaxVarPayment()));
			dlrPrgmForm.setCommPayment(WebHelper.getYesOrNoString(dlrPrgm.getCommPayment()));
		}else{
			dlrPrgmForm.setVehicleType(dlrPrgm.getVehicleType());
			dlrPrgmForm.setCondition(dlrPrgm.getCondition());
			dlrPrgmForm.setKpiId(dlrPrgm.getKpiId());
			dlrPrgmForm.setProgramStatus(dlrPrgm.getProgramStatus());

			dlrPrgmForm.setComsnPymtSchedule(dlrPrgm.getComsnPymtSchedule());
			dlrPrgmForm.setComsnPymtTrigger(dlrPrgm.getComsnPymtTrigger());
			dlrPrgmForm.setFixedPymtSchedule(dlrPrgm.getFixedPymtSchedule());
			dlrPrgmForm.setFixedPymtTrigger(dlrPrgm.getFixedPymtTrigger());
			dlrPrgmForm.setVariablePymtSchedule(dlrPrgm.getVariablePymtSchedule());
			dlrPrgmForm.setVariablePymtTrigger(dlrPrgm.getVariablePymtTrigger());

			dlrPrgmForm.setComsnProcessSchedule(dlrPrgm.getComsnProcessSchedule());
			dlrPrgmForm.setComsnProcessTrigger(dlrPrgm.getComsnProcessTrigger());			
			dlrPrgmForm.setFixedPymtBonusSchedule(dlrPrgm.getFixedPymtBonusSchedule());
			dlrPrgmForm.setFixedPymtBonusTrigger(dlrPrgm.getFixedPymtBonusTrigger());
			dlrPrgmForm.setVariablePymtBonusSchedule(dlrPrgm.getVariablePymtBonusSchedule());
			dlrPrgmForm.setVariablePymtBonusTrigger(dlrPrgm.getVariablePymtBonusTrigger());

			dlrPrgmForm.setPaymentType(dlrPrgm.getPaymentType());
			dlrPrgmForm.setRebateAmt(dlrPrgm.getRebateAmt());
			dlrPrgmForm.setMaxVarPayment(dlrPrgm.getMaxVarPayment());
			dlrPrgmForm.setCommPayment(dlrPrgm.getCommPayment());

			LOGGER.exit(CLASSNAME, "populatePrgDefinitionForm()");
		}
	}					
	private ProgramDefinition populatePrgDefinition(ProgramDefinitionForm dlrPrgm,ProgramDefinition dlrPrgmBean) {
		LOGGER.enter(CLASSNAME, "populatePrgDefinition()");
		dlrPrgmBean.setPaymentType(dlrPrgm.getPaymentType());
		dlrPrgmBean.setProgramId(dlrPrgm.getProgramId());
		dlrPrgmBean.setProgramName(dlrPrgm.getProgramName());
		dlrPrgmBean.setDescription(dlrPrgm.getDescription());
		dlrPrgmBean.setStartDate(WebHelper.convertStringToDate(dlrPrgm.getStartDate()));
		dlrPrgmBean.setEndDate(WebHelper.convertStringToDate(dlrPrgm.getEndDate()));
		dlrPrgmBean.setRebateAmt(dlrPrgm.getRebateAmt());
		dlrPrgmBean.setCommPayment(dlrPrgm.getCommPayment());
		dlrPrgmBean.setCommAmount(dlrPrgm.getCommAmount());
		//dlrPrgmBean.setCommPercent(dlrPrgm.getCommPercent());
		dlrPrgmBean.setComsnProcessSchedule(dlrPrgm.getComsnProcessSchedule());
		dlrPrgmBean.setComsnProcessTrigger(dlrPrgm.getComsnProcessTrigger());

		dlrPrgmBean.setComsnPymtSchedule(dlrPrgm.getComsnPymtSchedule());
		dlrPrgmBean.setComsnPymtTrigger(dlrPrgm.getComsnPymtTrigger());
		dlrPrgmBean.setFixedPayment(dlrPrgm.getFixedPayment());
		dlrPrgmBean.setFixedPymtSchedule(dlrPrgm.getFixedPymtSchedule());
		dlrPrgmBean.setFixedPymtTrigger(dlrPrgm.getFixedPymtTrigger());
		dlrPrgmBean.setVariablePayment(dlrPrgm.getVariablePayment());
		dlrPrgmBean.setVariablePymtSchedule(dlrPrgm.getVariablePymtSchedule());
		dlrPrgmBean.setVariablePymtTrigger(dlrPrgm.getVariablePymtTrigger());
		dlrPrgmBean.setFixedPymtBonusSchedule(dlrPrgm.getFixedPymtBonusSchedule());
		dlrPrgmBean.setFixedPymtBonusTrigger(dlrPrgm.getFixedPymtBonusTrigger());
		dlrPrgmBean.setVariablePymtBonusSchedule(dlrPrgm.getVariablePymtBonusSchedule());
		dlrPrgmBean.setVariablePymtBonusTrigger(dlrPrgm.getVariablePymtBonusTrigger());
		dlrPrgmBean.setKpiId(dlrPrgm.getKpiId());
		dlrPrgmBean.setCondition(dlrPrgm.getCondition());
		dlrPrgmBean.setVehicleType(dlrPrgm.getVehicleType());
		dlrPrgmBean.setMaxVarPayment(dlrPrgm.getMaxVarPayment());
		dlrPrgmBean.setProgramStatus(dlrPrgm.getProgramStatus());
		dlrPrgmBean.setSpecialProgram(dlrPrgm.getSpecialProgram());
		dlrPrgmBean.setMasterCondList(dlrPrgm.getMasterCondList());
		dlrPrgmBean.setInactiveDate(WebHelper.convertStringToDate(dlrPrgm.getInactiveDate()));
		dlrPrgmBean.setCreatedBy(this.getUserId());
		dlrPrgmBean.setUpdateBy(this.getUserId());
		LOGGER.exit(CLASSNAME, "populatePrgDefinition()");
		return dlrPrgmBean;
	}

	public boolean validateDealerProgram(){
		LOGGER.enter(CLASSNAME, "validateDealerProgram()");
		boolean flag = false;
		try {
			if(dlrPrgmForm!=null){ 
				dlrPrgm = new ProgramDefinition();
				dlrPrgm = populatePrgDefinition(dlrPrgmForm,dlrPrgm);

				if(dlrPrgm.getProgramId()>0 && IConstants.STATUS_A.equalsIgnoreCase(dlrPrgm.getProgramStatus()) && dlrPrgmForm.isFlagActive()){
					addActionError("Cannot submit an Active program Again");
				}
				if(dlrPrgm.getProgramId()>0 && IConstants.STATUS_I.equalsIgnoreCase(dlrPrgm.getProgramStatus()) && dlrPrgm.getInactiveDate()!=null){
					addActionError("Program has been already In-Activated");
				}
				else{
					// Unique Key Validation
					boolean validateProgram = businessDel.validateProgram(dlrPrgmForm.getProgramId(), dlrPrgmForm.getProgramName() , dlrPrgm.getStartDate());
					if(validateProgram){
						addActionError("Program Name and Start Date should be unique");
					}

					if(IConstants.STATUS_A.equalsIgnoreCase(dlrPrgm.getProgramStatus()) && !dlrPrgmForm.isFlagActive()){
						if(dlrPrgm.getVehicleType().size()==0){
							addActionError(" Select at least one Applicable Vehicle");
						}
						if(IConstants.SPECIAL_PGM.equalsIgnoreCase(dlrPrgmForm.getSpecialProgram()) && dlrPrgm.getCondition().size() == 0){
							addActionError(" Select at least one Special Condition");
						}
						if(IConstants.SPECIAL_PGM.equalsIgnoreCase(dlrPrgmForm.getSpecialProgram()) && IConstants.STATUS_A.equalsIgnoreCase(dlrPrgm.getProgramStatus())){
							if(IConstants.EMPTY_STR.equalsIgnoreCase(dlrPrgm.getFixedPymtBonusSchedule()) && IConstants.EMPTY_STR.equalsIgnoreCase(dlrPrgm.getFixedPymtSchedule()) && 
									IConstants.EMPTY_STR.equalsIgnoreCase(dlrPrgm.getVariablePymtBonusSchedule()) && IConstants.EMPTY_STR.equalsIgnoreCase(dlrPrgm.getVariablePymtSchedule()) &&
									IConstants.EMPTY_STR.equalsIgnoreCase(dlrPrgm.getComsnProcessSchedule()) && IConstants.EMPTY_STR.equalsIgnoreCase(dlrPrgm.getComsnPymtSchedule())
									){
								addActionError(" Select atleast one Schedule Type");
							}
							else {
								if(IConstants.SPECIAL_PGM.equalsIgnoreCase(dlrPrgmForm.getSpecialProgram()) && 
										IConstants.CONSTANT_Y.equalsIgnoreCase(dlrPrgm.getCommPayment()) && 
										(IConstants.EMPTY_STR.equalsIgnoreCase(dlrPrgm.getComsnProcessSchedule()) || 
												IConstants.EMPTY_STR.equalsIgnoreCase(dlrPrgm.getComsnPymtSchedule()))){
									addActionError(" Select Commission Payment schedule");
								}
								if((IConstants.EMPTY_STR.equalsIgnoreCase(dlrPrgm.getFixedPymtBonusSchedule()) || IConstants.EMPTY_STR.equalsIgnoreCase(dlrPrgm.getFixedPymtSchedule()))){
									addActionError(" Select Fixed Payment schedule");
								}if((IConstants.EMPTY_STR.equalsIgnoreCase(dlrPrgm.getVariablePymtBonusSchedule()) || IConstants.EMPTY_STR.equalsIgnoreCase(dlrPrgm.getVariablePymtSchedule()))){
									addActionError(" Select Variable Payment schedule");
								}
							}

						}
						if(dlrPrgm.getEndDate()!=null){
							boolean isEndDate =	businessDel.validateEndDate(dlrPrgm.getEndDate());
							if(!isEndDate){
								addActionError("Retail Calendar is not available in this range. Please select End Date again");
							}
						}
						/*if(!(startDate.compareTo(DateCalenderUtil.getNextDayDate())>=0)){
						addActionError("Start Date should be greater than today's date.");
					}
					else if(!(endDt.compareTo(DateCalenderUtil.getNextDayDate())>=0)){
						addActionError("Start Date should be greater than today's date.");
					}*/
					}
				}
				if(dlrPrgm.getProgramId()==0 && IConstants.STATUS_I.equalsIgnoreCase(dlrPrgm.getProgramStatus())){
					addActionError("Cannot submit an Inactive program");
				}

			}
			if(hasActionErrors()){
				flag = true;
			}

		} catch (TechnicalException te) {
			LOGGER.error("DPB.PGM.ACT.011"+ "TechnicalException occured"+ te.getMessage() + te.getMessageKey());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, te);
		}
		catch (PersistenceException pe) {
			LOGGER.error("DPB.PGM.ACT.012"+ "PersistenceException occured"+ pe.getMessage());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, pe);
		}
		catch (Exception e) {
			LOGGER.error("DPB.PGM.ACT.013"+ "Exception occured"+ e.getMessage());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, e);
		}
		LOGGER.exit(CLASSNAME, "validateDealerProgram()");
		return flag;
	}

	public void loadCache(){
		LOGGER.enter(CLASSNAME, "loadCache()");
		kpiList = MasterDataLookup.getInstance().getKPIList();
		vehicleList = MasterDataLookup.getInstance().getVehicleList();
		vehicleCond = MasterDataLookup.getInstance().getConditionCodes();
		specialConditions = MasterDataLookup.getInstance().getSpecialConditionList();
		defSts = MasterDataLookup.getInstance().getDefStatusCodes();
		triggerList = MasterDataLookup.getInstance().getTriggerCodes();
		defSchd = MasterDataLookup.getInstance().getSchedulerCodes();
		LOGGER.exit(CLASSNAME, "loadCache()");
	}

	public List<ProgramDefinition> getPrgList() {
		return prgList;
	}
	public void setPrgList(List<ProgramDefinition> prgList) {
		this.prgList = prgList;
	}

	public List<Kpi> getKpiList() {
		return kpiList;
	}
	public void setKpiList(List<Kpi> kpiList) {
		this.kpiList = kpiList;
	}
	public List<VehicleType> getVehicleList() {
		return vehicleList;
	}
	public void setVehicleList(List<VehicleType> vehicleList) {
		this.vehicleList = vehicleList;
	}
	public String getProgramListView() {
		return programListView;
	}
	public void setProgramListView(String programListView) {
		this.programListView = programListView;
	}
	public int getProgramIdD() {
		return programIdD;
	}
	public void setProgramIdD(int programIdD) {
		this.programIdD = programIdD;
	}
	public ProgramDefinitionForm getDlrPrgmForm() {
		return dlrPrgmForm;
	}
	public void setDlrPrgmForm(ProgramDefinitionForm dlrPrgmForm) {
		this.dlrPrgmForm = dlrPrgmForm;
	}
	/**
	 * @return the isAdminUser
	 */
	public boolean isAdminUser() {
		return adminUser;
	}
	/**
	 * @param isAdminUser the isAdminUser to set
	 */
	public void setAdminUser(boolean isAdminUser) {
		this.adminUser = isAdminUser;
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
	/**
	 * @return the triggerList
	 */
	public List<Trigger> getTriggerList() {
		return triggerList;
	}
	/**
	 * @param triggerList the triggerList to set
	 */
	public void setTriggerList(List<Trigger> triggerList) {
		this.triggerList = triggerList;
	}
	/**
	 * @return the scheduleList
	 */
	public List<Scheduler> getScheduleList() {
		return defSchd;
	}
	/**
	 * @param scheduleList the scheduleList to set
	 */
	public void setScheduleList(List<Scheduler> defSchd) {
		this.defSchd = defSchd;
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

}