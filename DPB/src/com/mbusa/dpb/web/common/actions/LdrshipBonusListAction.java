package com.mbusa.dpb.web.common.actions;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.DefStatus;
import com.mbusa.dpb.common.domain.LeadershipBonusDetails;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class LdrshipBonusListAction extends DPBAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6376938036068736792L;
	private static DPBLog LOGGER = DPBLog.getInstance(LdrshipBonusListAction.class);
	BusinessDelegate businessdelegate  =  new BusinessDelegate();
	private LeadershipBonusDetails ldrshipbnsdtls = new LeadershipBonusDetails() ;
	private List<LeadershipBonusDetails>  ldrshipBnsList =  new ArrayList<LeadershipBonusDetails>();
	private List<LeadershipBonusDetails>  bnsList =  new ArrayList<LeadershipBonusDetails>();
	private String actionForward = IWebConstants.EMPTY_STR;
	
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
	/**
	 * getLdrShipBonusList  
	 * @return String
	 */
	public String getLdrShipBonusList() throws Exception{		
		try {	
			this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
			actionForward = "ldrShipBonusListView";
			String userId = getUserId();
			ldrshipbnsdtls.setUserId(userId);
			bnsList = businessdelegate.getLdrshipBonusList(ldrshipbnsdtls);
			List<DefStatus> defSts = MasterDataLookup.getInstance().getDefStatusCodes();
			for(LeadershipBonusDetails ldrBnsDtlsBean: bnsList){
				ldrBnsDtlsBean.setStatus(WebHelper.getStatusString(ldrBnsDtlsBean.getStatus(),defSts));
				ldrshipBnsList.add(ldrBnsDtlsBean);
			}
			if(bnsList == null || bnsList.size() == 0 ) {
				addActionError(IWebConstants.NO_PROGRAMS_DEFINED);
			}
		} catch(BusinessException be){
			LOGGER.info("BusinessException occured");
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
	
	/**
	 * @return ldrshipBnsList
	 */
	public List<LeadershipBonusDetails> getLdrshipBnsList() {
		return ldrshipBnsList;
	}
	/**
	 * @param ldrshipBnsList
	 */
	public void setLdrshipBnsList(List<LeadershipBonusDetails> ldrshipBnsList) {
		this.ldrshipBnsList = ldrshipBnsList;
	} 

}
