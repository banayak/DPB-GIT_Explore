package com.mbusa.dpb.web.bonusProcess.action;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.web.common.actions.DPBAction;
import com.mbusa.dpb.web.delegate.BonusProcessingDelegate;
import com.mbusa.dpb.web.delegate.FileProcessingDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;
import com.mbusa.dpb.common.logger.DPBLog;

	public class ViewProcBonusPopupAction extends DPBAction{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int procDpbID; 
		private BonusProcessingDelegate bDelegate  =  new BonusProcessingDelegate();
		List<DPBProcessLogBean> procesDetail  = new ArrayList<DPBProcessLogBean>();
		private static DPBLog LOGGER = DPBLog.getInstance(ViewProcBonusPopupAction.class);
		private String actionForward = "";
		List<String> inpProcList;
		private int pdpb;
		public String viewProcBonusProcpopup(){
			try
			{ 
				this.setMenuTabFocus(IWebConstants.FILE_PROCESSING_ID);
				actionForward="viewProcBonusProcpopup";
				procesDetail = bDelegate.getProcessLogsDeatils(procDpbID);
				if(procesDetail.size() > 0)
				{
					int size=procesDetail.size()-1;
					size = "IN-PROCESS".equalsIgnoreCase(((DPBProcessLogBean)procesDetail.get(size)).getDpbProcessStatus())
							? procesDetail.size() : size;
				
						int count = 1;
						if("IN-PROCESS".equalsIgnoreCase(((DPBProcessLogBean)procesDetail.get(procesDetail.size()-1)).getDpbProcessStatus()))
						{
							for(DPBProcessLogBean procUpdetDet : procesDetail)
							{
								if(count != size && "IN-PROCESS".equalsIgnoreCase(procUpdetDet.getDpbProcessStatus())) {
								procUpdetDet.setDpbProcessStatus(IWebConstants.DISP_INP_PROC_STAT);
								//procesActDetail.add(procUpdetDet);
								} 
							
								count++;
							}
						}
						
						else if( ! "IN-PROCESS".equalsIgnoreCase(((DPBProcessLogBean)procesDetail.get(procesDetail.size()-1)).getDpbProcessStatus()))
						{
							for(DPBProcessLogBean procUpdetDet : procesDetail)
							{
								if( "IN-PROCESS".equalsIgnoreCase(procUpdetDet.getDpbProcessStatus())) {
								procUpdetDet.setDpbProcessStatus(IWebConstants.DISP_INP_PROC_STAT);
								//procesActDetail.add(procUpdetDet);
								} 
							
								//count++;
							}
						}
				}
			} catch(BusinessException be){
				LOGGER.info("BusinessException occured");
			}catch (TechnicalException te) {
				actionForward =  "ErrorPage";
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

		public int getProcDpbID() {
			return procDpbID;
		}

		public void setProcDpbID(int procDpbID) {
			this.procDpbID = procDpbID;
		}

		public List<DPBProcessLogBean> getProcesDetail() {
			return procesDetail;
		}

		public void setProcesDetail(List<DPBProcessLogBean> procesDetail) {
			this.procesDetail = procesDetail;
		}

		public List<String> getInpProcList() {
			return inpProcList;
		}

		public void setInpProcList(List<String> inpProcList) {
			this.inpProcList = inpProcList;
		}

		public int getPdpb() {
			return pdpb;
		}

		public void setPdpb(int pdpb) {
			this.pdpb = pdpb;
		}


}
