package com.mbusa.dpb.web.common.actions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.mbusa.dpb.common.domain.ProgramDefinition;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;


public class DealerProgramListAction extends DealerProgramAction {
	private static DPBLog LOGGER = DPBLog.getInstance(DealerProgramListAction.class);
	private static final long serialVersionUID = 1L;
	private BusinessDelegate businessDel = new BusinessDelegate();
	List<ProgramDefinition> prgList= new ArrayList<ProgramDefinition>();
	String actionForward="";
public String viewDealerProgram(){
	try {
		prgList =businessDel.getDlrProgramsList();
		actionForward = "viewPrgList";
	} catch (Exception e) {
		e.printStackTrace();
	}
	return actionForward;
}

public String updateDealerProgram(){
	try{
		submitDealerProgram();
		actionForward = "dealerPrgView";
	} catch (PersistenceException pe) {
		LOGGER.info("PersistenceException occured");
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, pe);
	}
	catch (Exception e) {
		actionForward =  IWebConstants.DPB_ERROR_URL;
		request.setAttribute (IWebConstants.jspExe, e);
	}
	return actionForward ;
}	


public List<ProgramDefinition> getPrgList() {
	return prgList;
}
public void setPrgList(List<ProgramDefinition> prgList) {
	this.prgList = prgList;
}

}