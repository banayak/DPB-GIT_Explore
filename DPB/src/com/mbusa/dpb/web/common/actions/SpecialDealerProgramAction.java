package com.mbusa.dpb.web.common.actions;

import com.mbusa.dpb.common.domain.ProgramDefinition;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.web.delegate.BusinessDelegate;

import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.common.logger.DPBLog;

import javax.persistence.PersistenceException;
public class SpecialDealerProgramAction extends DealerProgramAction {

	private static DPBLog LOGGER = DPBLog.getInstance(SpecialDealerProgramAction.class);
	private static final long serialVersionUID = 1L;
	BusinessDelegate businessDel = new BusinessDelegate();
	private ProgramDefinition specialDlrPrgm;
	String actionForward="";

public String saveSpecialProgram(){

	try {
		submitDealerProgram();
		actionForward = "specialProgramSave";
	} catch (PersistenceException pe) {
		LOGGER.info("PersistenceException occured");
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, pe);
	}
	catch (Exception e) {
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, e);
	}
	return actionForward;
}

public String viewSpecialProgram() throws BusinessException{
	String dlrProgram = viewDealerProgram();
	 if("dealerPrgEdit".equalsIgnoreCase(dlrProgram)){
		 actionForward ="dealerPrgView";
	 }
	actionForward= "specialProgramView";
	
	return actionForward;
}

public ProgramDefinition getSpecialDlrPrgm() {
	return specialDlrPrgm;
}
public void setSpecialDlrPrgm(ProgramDefinition specialDlrPrgm) {
	this.specialDlrPrgm = specialDlrPrgm;
}
	
}