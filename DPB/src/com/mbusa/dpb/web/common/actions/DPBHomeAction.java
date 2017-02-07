package com.mbusa.dpb.web.common.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.domain.DPBProcessLogBean;
import com.mbusa.dpb.web.delegate.DashBoardDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class DPBHomeAction extends DPBAction {

	private static final long serialVersionUID = 1L;

	List<DPBProcessLogBean> todayList = new ArrayList<DPBProcessLogBean>();
	List<DPBProcessLogBean> yesterdayList = new ArrayList<DPBProcessLogBean>();
	List<DPBProcessLogBean> processesList = new ArrayList<DPBProcessLogBean>();
	DashBoardDelegate dashBrdDelegate  = new DashBoardDelegate();

	String selectedDate;

	boolean role = false;

public String viewDashBoard(){
		/*	List<DPBProcessLogBean> prcsList = new ArrayList<DPBProcessLogBean>();*/
			List<DPBProcessLogBean> list = new ArrayList<DPBProcessLogBean>();
			List<DPBProcessLogBean> actList = new ArrayList<DPBProcessLogBean>();
			try {
			if(IConstants.USER_ROLE_ADMIN.equalsIgnoreCase(this.getUserRole()) || 
						IConstants.USER_ROLE_TREASURY.equalsIgnoreCase(this.getUserRole())){
					role = true;
				}
				this.setMenuTabFocus(IWebConstants.DASHBOARD_ID);

			// added for date wise processes view -DPB Enhancement by Amit
			// Start
			if (selectedDate != null && !selectedDate.equals("")
					&& selectedDate.trim().length() > 0) {
				processesList = new ArrayList<DPBProcessLogBean>();
				processesList = dashBrdDelegate
						.getProcessesForDate(selectedDate);
				System.out.println("processesList size::"
						+ processesList.size());
				Map<String, String> prcsTypeMap = MasterDataLookup
						.getInstance().getProcessTypeAsMap();
				for (DPBProcessLogBean logBean : processesList) {
					logBean.setDisplayDate(WebHelper.formatDate(logBean
							.getProcDate()));
					logBean.setActProcType(prcsTypeMap.get(logBean.getPrgType()));

				}
			}
			else{
				// delegate call --> EJB -->manager --> DAO
				list = dashBrdDelegate.getLastTwoDaysProcesses();
				/*for(DPBProcessLogBean logBean: prcsList){
					logBean.setDpbProcessStatus(WebHelper.getProcessStatusString(logBean.getDpbProcessStatus()));
					logBean.getDpbProcess().setProcessType(WebHelper.getProcessTypeString(logBean.getDpbProcess().getProcessType()));
					list.add(logBean);
				}*/
				Map<String,String> prcsTypeMap = MasterDataLookup.getInstance().getProcessTypeAsMap();
				for(DPBProcessLogBean logBean: list)
				{
					logBean.setDisplayDate(WebHelper.formatDate(logBean.getProcDate()));
					logBean.setActProcType(prcsTypeMap.get(logBean.getPrgType()));
					actList.add(logBean);
				}
				//Connection conn = ConnectionFactory.getConnection();
				WebHelper.todayView(actList,todayList);
				WebHelper.yesterdayView(actList,yesterdayList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "viewDashBoard";
	}
	/**
	 * @return the todayList
	 */
	public List<DPBProcessLogBean> getTodayList() {
		return todayList;
	}

	/**
	 * @return the yesterdayList
	 */
	public List<DPBProcessLogBean> getYesterdayList() {
		return yesterdayList;
	}

	/**
	 * @param todayList the todayList to set
	 */
	public void setTodayList(List<DPBProcessLogBean> todayList) {
		this.todayList = todayList;
	}

	/**
	 * @param yesterdayList the yesterdayList to set
	 */
	public void setYesterdayList(List<DPBProcessLogBean> yesterdayList) {
		this.yesterdayList = yesterdayList;
	}
	public boolean isRole() {
		return role;
	}
	public void setRole(boolean role) {
		this.role = role;
	}
	public String getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}
	public List<DPBProcessLogBean> getProcessesList() {
		return processesList;
	}
	public void setProcessesList(List<DPBProcessLogBean> processesList) {
		this.processesList = processesList;
	}
}
