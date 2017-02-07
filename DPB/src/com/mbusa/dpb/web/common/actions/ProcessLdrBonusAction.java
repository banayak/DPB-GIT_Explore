package com.mbusa.dpb.web.common.actions;

import java.util.ArrayList;
import java.util.List;

import com.mbusa.dpb.common.domain.ProcessLdrBonus;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.opensymphony.xwork2.ActionSupport;

public class ProcessLdrBonusAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProcessLdrBonus processLdrBonus;
	private List<ProcessLdrBonus> processLdrBonusList=null;
	BusinessDelegate bDelegate=new BusinessDelegate();
	
public String processLeadershipBonus(){
		
		try {
			processLdrBonusList=new ArrayList<ProcessLdrBonus>();
		//	processLdrBonusList=bDelegate.processLdrBonus();
			//Connection conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "processLdrBonus";
		
	}

public ProcessLdrBonus getProcessLdrBonus() {
	return processLdrBonus;
}

public void setProcessLdrBonus(ProcessLdrBonus processLdrBonus) {
	this.processLdrBonus = processLdrBonus;
}

public List<ProcessLdrBonus> getProcessLdrBonusList() {
	return processLdrBonusList;
}

public void setProcessLdrBonusList(List<ProcessLdrBonus> processLdrBonusList) {
	this.processLdrBonusList = processLdrBonusList;
}

}
