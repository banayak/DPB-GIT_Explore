/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: LdrshipBonusAction.java
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.DefStatus;
import com.mbusa.dpb.common.domain.LeadershipBonusDetails;
import com.mbusa.dpb.common.domain.VehicleType;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.ServiceException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;
import com.mbusa.dpb.common.util.DPBCommonHelper;
import com.mbusa.dpb.common.util.DateCalenderUtil;
import com.mbusa.dpb.common.util.SendMailDTO;
import com.mbusa.dpb.common.props.PropertyManager;


/**
 * @author cd5001193
 *
 */
public class LdrshipBonusAction extends DPBAction {

	private static final long serialVersionUID = 1L;
	private static DPBLog LOGGER = DPBLog.getInstance(LdrshipBonusAction.class);
	BusinessDelegate businessdelegate  =  new BusinessDelegate();
	private LeadershipBonusDetails ldrshipbnsdtls;
	private int ldrshipidL ;
	private String ldrshipidLinkView = IWebConstants.FALSE;	
	private List <VehicleType> vehicleList = new ArrayList<VehicleType>();
	private String actionForward = IWebConstants.EMPTY_STR;
	boolean fromDPBList = false;
	private String inactDate = IWebConstants.EMPTY_STR;
	static PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();

/**
	 * getLdrShipBonus for fetching Program Account ID Mapping List
	 * @return String
*/	
	public String getLdrShipBonus() {
		int ldrshipID = 0;
		try {
			actionForward = "ldrShipBonusView";
			String userId = getUserId();
			ldrshipbnsdtls.setUserId(userId);
			vehicleList = MasterDataLookup.getInstance().getVehicleList();				
			validateDealerProgram();			
			if (!hasActionErrors()) {
				ldrshipID = ldrshipbnsdtls.getLdrshipid();
				businessdelegate.saveLeadershipBonusDetails(ldrshipbnsdtls);
				if (ldrshipbnsdtls.getLdrshipid() != ldrshipID && !ldrshipbnsdtls.getStatus().equalsIgnoreCase(IWebConstants.INACTIVE) ) {
					addActionMessage(IWebConstants.LDRSP_PGM_INST_SUC);
				} else if(ldrshipbnsdtls.getLdrshipid() == ldrshipID && !ldrshipbnsdtls.getStatus().equalsIgnoreCase(IWebConstants.INACTIVE)) {
					addActionMessage(IWebConstants.LDRSP_PGM_UPT_SUC);
				} else{
					if(ldrshipbnsdtls.getStatus().equalsIgnoreCase(IWebConstants.INACTIVE) && ldrshipbnsdtls.isFlagActive()) {
					addActionMessage(IWebConstants.LDRSP_PGM_INACT_INST_SUC);
					}
				}
				
			}
		} catch(BusinessException be){
			LOGGER.error("DPB.LDRSPPGM.ACT.001"+ "BusinessException occured"+ be.getMessage() + be.getMessageKey());
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (TechnicalException te) {
			LOGGER.error("DPB.LDRSPPGM.ACT.002"+ "TechnicalException occured"+ te.getMessage() + te.getMessageKey());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, te);
		}
		catch (PersistenceException pe) {
			LOGGER.error("DPB.LDRSPPGM.ACT.003"+ "PersistenceException occured"+ pe.getMessage());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, pe);
		}
		catch (Exception e) {
			LOGGER.error("DPB.LDRSPPGM.ACT.004"+ "Exception occured"+ e.getMessage());
			if(e.getMessage().contains("DuplicateKeyException")){
			actionForward =  "ldrShipBonusView";
			addActionError("Program Name and Start Date should be unique");
		}else{
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, e);
		}
		}
		return actionForward;
	}
	/**
	 * viewLdrshipBonusDefFYRchange for UnUsed amount 
	 * @return String
	 */
	public String viewLdrshipBonusDefFYRchange() {
		try {
			actionForward = "ldrShipBonusView";
			String userId = getUserId();
			ldrshipbnsdtls.setUserId(userId);
			vehicleList = MasterDataLookup.getInstance().getVehicleList();			
			validateDate();
			if (!hasActionErrors()) {
				businessdelegate.ldrshipBonusDtlsFYRChange(ldrshipbnsdtls);
				if(ldrshipbnsdtls.getUnusdamt() == 0.0) {
					addActionMessage(IWebConstants.LDRSP_ACRL_AMT_NO);
				}
				if(ldrshipbnsdtls.getUnitCount() == 0) {
					addActionMessage(IWebConstants.LDRSP_ELEG_COUNT);
				}
				ldrshipbnsdtls.manualReset();
			}
		} catch(BusinessException be){
			LOGGER.error("DPB.LDRSPPGM.ACT.005"+ "BusinessException occured"+ be.getMessage() + be.getMessageKey());
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (TechnicalException te) {
			LOGGER.error("DPB.LDRSPPGM.ACT.006"+ "TechnicalException occured"+ te.getMessage() + te.getMessageKey());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, te);
		}
		catch (PersistenceException pe) {
			LOGGER.error("DPB.LDRSPPGM.ACT.007"+ "PersistenceException occured"+ pe.getMessage() );
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, pe);
		}
		catch (Exception e) {
			LOGGER.error("DPB.LDRSPPGM.ACT.008"+ "Exception occured"+ e.getMessage());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, e);
		}
		return actionForward;
	}
	/**
	 * viewLdrshipBonusDef for viewing default value
	 * @return String
	 */
	public String viewLdrshipBonusDef() {
		try {
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			ldrshipbnsdtls = new LeadershipBonusDetails();
			actionForward = "ldrShipBonusView";
			String userId = getUserId();
			ldrshipbnsdtls.setUserId(userId);
			vehicleList = MasterDataLookup.getInstance().getVehicleList();
		}
		catch(ServiceException be){
			LOGGER.error("DPB.LDRSPPGM.ACT.009"+ "ServiceException occured"+ be.getMessage() + be.getMessageKey());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, be);
		}
		catch (PersistenceException pe) {
			LOGGER.error("DPB.LDRSPPGM.ACT.010"+ "PersistenceException occured"+ pe.getMessage() );
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, pe);
		}
		catch (Exception e) {
			LOGGER.error("DPB.LDRSPPGM.ACT.011"+ "Exception occured"+ e.getMessage());
			actionForward =  IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, e);
		}
		return actionForward;
	} 
	/**
	 * getLdrshipBonusDefDetails for Leadership Bonus details
	 * @return String
	 */
	public String getLdrshipBonusDefDetails() {
		String actionForward1 =IWebConstants.EMPTY_STR;
		try {
			actionForward ="ldrShipBonusViews";
			actionForward1 = "ldrShipBonusView";
			String userId = getUserId();
			ldrshipbnsdtls.setUserId(userId);
			vehicleList = MasterDataLookup.getInstance().getVehicleList();
			if (getLdrshipidL() != 0) {
				List<DefStatus> defSts = MasterDataLookup.getInstance().getDefStatusCodes();
				ldrshipbnsdtls = new LeadershipBonusDetails();
				ldrshipbnsdtls.setLdrshipid(getLdrshipidL());
				businessdelegate.getLeadershipBonusDetails(ldrshipbnsdtls);
				setInactDate(WebHelper.makeNonNullString(DateCalenderUtil.convertDateToString(ldrshipbnsdtls.getInactiveDate())).trim());
				if (getLdrshipidLinkView().equalsIgnoreCase("true")) {
					ldrshipbnsdtls.setLdrshipAppVehs(WebHelper.getVehicleName(ldrshipbnsdtls.getLdrshipAppVehs(),vehicleList));
					ldrshipbnsdtls.setStatus(WebHelper.getStatusString(ldrshipbnsdtls.getStatus(),defSts));
				}
			}
		} catch (BusinessException be) {
			LOGGER.error("DPB.LDRSPPGM.ACT.012"+ "BusinessException occured"+ be.getMessage() + be.getMessageKey());
		} catch (TechnicalException te) {
			LOGGER.error("DPB.LDRSPPGM.ACT.013"+ "TechnicalException occured"+ te.getMessage() + te.getMessageKey());
			actionForward = IWebConstants.DPB_ERROR_URL;
			request.setAttribute(IWebConstants.jspExe, te);
		} catch (PersistenceException pe) {
			LOGGER.error("DPB.LDRSPPGM.ACT.014"+ "PersistenceException occured"+ pe.getMessage() );
			actionForward = IWebConstants.DPB_ERROR_URL;
			request.setAttribute(IWebConstants.jspExe, pe);
		} catch (Exception e) {
			LOGGER.error("DPB.LDRSPPGM.ACT.015"+ "Exception occured"+ e.getMessage());
			actionForward = IWebConstants.DPB_ERROR_URL;
			request.setAttribute(IWebConstants.jspExe, e);
		}
		if (getLdrshipidLinkView().equalsIgnoreCase("true")) {
			return actionForward;
		} else {
			return actionForward1;

		}
	}
	/**
	 * validateDealerProgram for validating the fields
	 * @return String
	 */
	public void validateDealerProgram() {
		double unEarnedAmount = 0.00;
		 SendMailDTO sendMailDTO = new SendMailDTO();		 
		try {
			
			if (ldrshipbnsdtls != null) {				
				if(ldrshipbnsdtls.getUnusdamt() == 0.0) {
					addActionError(IWebConstants.LDRSP_ACRL_AMT_NO);
				}
				if(ldrshipbnsdtls.getUnitCount() == 0) {
					addActionError(IWebConstants.LDRSP_ELEG_COUNT);
				}
				if(ldrshipbnsdtls.getStatus().equalsIgnoreCase(IWebConstants.ACTIVE) && ldrshipbnsdtls.getLdrshipid() != 0 && ldrshipbnsdtls.isFlagActive()) {
					addActionError(IWebConstants.LDRSP_PGM_REINST_SUC);
				} 
				if(ldrshipbnsdtls.getStatus().equalsIgnoreCase(IWebConstants.INACTIVE) && ldrshipbnsdtls.getLdrshipid() == 0 && !ldrshipbnsdtls.isFlagActive()) {
					addActionError(IWebConstants.LDRSP_PGM_INACT_PGM);
				} 				
				DateFormat sdf = new SimpleDateFormat(IWebConstants.LDRSP_PGM_DATE_FMT);
				Date startDate = sdf.parse(ldrshipbnsdtls.getStartDate());
				Date endDate = sdf.parse(ldrshipbnsdtls.getEndDate());	
				Date rtlstartDate = sdf.parse(ldrshipbnsdtls.getRtlStartDate());
				Date rtlendDate = sdf.parse(ldrshipbnsdtls.getRtlEndDate());
				if ((startDate.compareTo(endDate) >= 0)) {
					addActionError(IWebConstants.LDRSP_PGM_ST_EN_COMP);
				}
				if ((rtlstartDate.compareTo(rtlendDate) >= 0)) {
					addActionError(IWebConstants.LDRSP_PGM_RTLST_TRLEN_COMP);
				}
				if (ldrshipbnsdtls.getLdrshipAppVehs().size() == 0) {
					addActionError(IWebConstants.LDRSP_PGM_VEH);
				}
				Date procDate = sdf.parse(ldrshipbnsdtls.getProcessDate());
				if (procDate.compareTo(endDate) <= 0) {
					addActionError(IWebConstants.LDRSP_PGM_PRCS_EN_COMP);
				}				
				if (ldrshipbnsdtls.getProcessDate() != null) {
					boolean isProcDate = businessdelegate.validateProcessDate(ldrshipbnsdtls.getProcessDate());
					if (!isProcDate) {
						addActionError(IWebConstants.LDRSP_PGM_RTL_CAL_FALSE);
					}
					String nextDayDate = DateCalenderUtil.getNextDayDate();
					DateFormat sdfs = new SimpleDateFormat("MM-dd-yyyy");
		        	Date nextDayDte = sdfs.parse(nextDayDate);	        	

					if((procDate.compareTo(nextDayDte)) < 0){
						addActionError(IWebConstants.LDRSP_PGM_PROC_DATE);
					}
				}
				/*if ( ldrshipbnsdtls.getUnusdamt()!= 0.0) {					
					unEarnedAmount = businessdelegate.validateUnearnedAmount(ldrshipbnsdtls.getStartDate(), ldrshipbnsdtls.getEndDate());
					if(ldrshipbnsdtls.getUnusdamt() != unEarnedAmount ) {
						sendMailDTO.setFrom(PROPERTY_MANAGER.getPropertyAsString("ldrspunearned.process.email.fromMail"));
						sendMailDTO.addTORecipient(PROPERTY_MANAGER.getPropertyAsString("ldrspunearned.process.email.toRecipient"));
						String subject = PROPERTY_MANAGER.getPropertyAsString("ldrspunearned.process.mismatch.email.subject");
						sendMailDTO.setSubject(subject);
						String msg= PROPERTY_MANAGER.getPropertyAsString("ldrspunearned.process.success.email.content");
						sendMailDTO.setContent(msg);
						DPBCommonHelper.sendEmail(sendMailDTO);

						
					}
					
				}*/

			}
		} catch (BusinessException be) {
			LOGGER.error("DPB.LDRSPPGM.ACT.016"+ "BusinessException occured"+ be.getMessage() + be.getMessageKey());
		} catch (TechnicalException te) {
			LOGGER.error("DPB.LDRSPPGM.ACT.017"+ "TechnicalException occured"+ te.getMessage() + te.getMessageKey());
			actionForward = IWebConstants.DPB_ERROR_URL;
			request.setAttribute(IWebConstants.jspExe, te);
		} catch (Exception e) {
			LOGGER.error("DPB.LDRSPPGM.ACT.018"+ "Exception occured"+ e.getMessage());
			actionForward = IWebConstants.DPB_ERROR_URL;
			request.setAttribute(IWebConstants.jspExe, e);
		}

	}

	public void validateDate() {
		try {
			if (ldrshipbnsdtls != null) {
				DateFormat sdf = new SimpleDateFormat(IWebConstants.LDRSP_PGM_DATE_FMT);
				Date startDate = sdf.parse(ldrshipbnsdtls.getStartDate());
				Date endDate = sdf.parse(ldrshipbnsdtls.getEndDate());
				Date rtlStartDate = sdf.parse(ldrshipbnsdtls.getRtlStartDate());
				Date rtlEndDate = sdf.parse(ldrshipbnsdtls.getRtlEndDate());
				if (startDate.compareTo(endDate) >= 0) {
					addActionError(IWebConstants.LDRSP_PGM_ST_EN_COMP);
				}
				if ((rtlStartDate.compareTo(rtlEndDate) >= 0)) {
					addActionError(IWebConstants.LDRSP_PGM_RTLST_TRLEN_COMP);
				}

			}
		} catch (Exception e) {
			LOGGER.error("DPB.LDRSPPGM.ACT.019"+ "Exception occured"+ e.getMessage());
			actionForward = IWebConstants.DPB_ERROR_URL;
			request.setAttribute(IWebConstants.jspExe, e);
		}

	}
	/**
	 * @return vehicleList
	 */
	public List<VehicleType> getVehicleList() {
		return vehicleList;
	}
	/**
	 * @param vehicleList
	 */
	public void setVehicleList(List<VehicleType> vehicleList) {
		this.vehicleList = vehicleList;
	}
	/**
	 * @return ldrshipidLinkView
	 */
	public String getLdrshipidLinkView() {
		return ldrshipidLinkView;
	}
	/**
	 * @param ldrshipidLinkView
	 */
	public void setLdrshipidLinkView(String ldrshipidLinkView) {
		this.ldrshipidLinkView = ldrshipidLinkView;
	}
	/**
	 * @return ldrshipidL
	 */
	public int getLdrshipidL() {
		return ldrshipidL;
	}

	/**
	 * @param ldrshipidL
	 */
	public void setLdrshipidL(int ldrshipidL) {
		this.ldrshipidL = ldrshipidL;
	}

	/**
	 * @return ldrshipbnsdtls
	 */
	public LeadershipBonusDetails getLdrshipbnsdtls() {
		return ldrshipbnsdtls;
	}

	/**
	 * @param ldrshipbnsdtls
	 */
	public void setLdrshipbnsdtls(LeadershipBonusDetails ldrshipbnsdtls) {
		this.ldrshipbnsdtls = ldrshipbnsdtls;
	}
	public boolean isFromDPBList() {
		return fromDPBList;
	}
	public void setFromDPBList(boolean fromDPBList) {
		this.fromDPBList = fromDPBList;
	}
	public String getInactDate() {
		return inactDate;
	}
	public void setInactDate(String inactDate) {
		this.inactDate = inactDate;
	}
	

}