package com.mbusa.dpb.common.domain;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
/**
 * @author cd5001193
 *
 */
public class LeadershipBonusDetails extends DpbProcess{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ldrshipid;
	private int ldrshipbnsfisyr;
	private double unusdamt;
	private double busresvper;
	private double busresvamt;
	private double ldrbnsamt;
	private double calcamtperunt;
	private double actamtperunt;
	private int unitCount;
	private int unitCountVal;
	private boolean ldrshipidLinkViewBean;
	private String ldrshipname;
	private String startDate;
	private String endDate;
	private String userId;
	private String status = "D";
	private String processDate;
	private int retailId;
	private boolean flagActive = false;
	private String rtlStartDate;	
	private String rtlEndDate;
	private Date inactiveDate;	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	private List ldrshipAppVeh = new ArrayList();
	private List<String> ldrshipAppVehs  = new ArrayList <String>();
	
	
	public List getLdrshipAppVehs() {
		return ldrshipAppVehs;
	}
	public void setLdrshipAppVehs(List ldrshipAppVehs) {
		this.ldrshipAppVehs = ldrshipAppVehs;
	}
	public List getLdrshipAppVeh() {
		return ldrshipAppVeh;
	}
	public void setLdrshipAppVeh(List ldrshipAppVeh) {
		this.ldrshipAppVeh = ldrshipAppVeh;
	}	
	
	public boolean isFlagActive() {
		return flagActive;
	}
	public void setFlagActive(boolean flagActive) {
		this.flagActive = flagActive;
	}
	public int getRetailId() {
		return retailId;
	}
	public void setRetailId(int retailId) {
		this.retailId = retailId;
	}
	/**
	 * @return
	 */
	public int getUnitCountVal() {
		return unitCountVal;
	}
	/**
	 * @param unitCountVal
	 */
	public void setUnitCountVal(int unitCountVal) {
		this.unitCountVal = unitCountVal;
	}
	/**
	 * @return
	 */
	public int getUnitCount() {
		return unitCount;
	}
	/**
	 * @param unitCount
	 */
	public void setUnitCount(int unitCount) {
		this.unitCount = unitCount;
	}
	/**
	 * @return
	 */
	public int getLdrshipid() {
		return ldrshipid;
	}
	/**
	 * @param ldrshipid
	 */
	public void setLdrshipid(int ldrshipid) {
		this.ldrshipid = ldrshipid;
	}
	/**
	 * @return
	 */
	public int getLdrshipbnsfisyr() {
		return ldrshipbnsfisyr;
	}
	/**
	 * @param ldrshipbnsfisyr
	 */
	public void setLdrshipbnsfisyr(int ldrshipbnsfisyr) {
		this.ldrshipbnsfisyr = ldrshipbnsfisyr;
	}
	/**
	 * @return
	 */
	public double getUnusdamt() {
		return unusdamt;
	}
	/**
	 * @param unusdamt
	 */
	public void setUnusdamt(double unusdamt) {
		this.unusdamt = unusdamt;
	}
	/**
	 * @return
	 */
	public double getBusresvper() {
		return busresvper;
	}
	/**
	 * @param busresvper
	 */
	public void setBusresvper(double busresvper) {
		this.busresvper = busresvper;
	}
	/**
	 * @return
	 */
	public double getBusresvamt() {
		return busresvamt;
	}
	/**
	 * @param busresvamt
	 */
	public void setBusresvamt(double busresvamt) {
		this.busresvamt = busresvamt;
	}
	/**
	 * @return
	 */
	public double getLdrbnsamt() {
		return ldrbnsamt;
	}
	/**
	 * @param ldrbnsamt
	 */
	public void setLdrbnsamt(double ldrbnsamt) {
		this.ldrbnsamt = ldrbnsamt;
	}
	/**
	 * @return
	 */
	public double getCalcamtperunt() {
		return calcamtperunt;
	}
	/**
	 * @param calcamtperunt
	 */
	public void setCalcamtperunt(double calcamtperunt) {
		this.calcamtperunt = calcamtperunt;
	}
	/**
	 * @return
	 */
	public double getActamtperunt() {
		return actamtperunt;
	}
	/**
	 * @param actamtperunt
	 */
	public void setActamtperunt(double actamtperunt) {
		this.actamtperunt = actamtperunt;
	}
	/**
	 * @return
	 */
	public boolean isLdrshipidLinkViewBean() {
		return ldrshipidLinkViewBean;
	}
	/**
	 * @param ldrshipidLinkViewBean
	 */
	public void setLdrshipidLinkViewBean(boolean ldrshipidLinkViewBean) {
		this.ldrshipidLinkViewBean = ldrshipidLinkViewBean;
	}
	/**
	 * @return
	 */
	public String getLdrshipname() {
		return ldrshipname;
	}
	/**
	 * @param ldrshipname
	 */
	public void setLdrshipname(String ldrshipname) {
		this.ldrshipname = ldrshipname;
	}
	/**
	 * @return
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}	
	
	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 
	 */
	public void manualReset() {
		busresvper = 0.00;
		busresvamt = 0.00;
		ldrbnsamt = 0.00;
		calcamtperunt = 0.00;
		actamtperunt = 0.00;
	}
	/**
	 * @return
	 */
	public String getProcessDate() {
		return processDate;
	}
	/**
	 * @param processDate
	 */
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}
	/**
	 * @return
	 */
	public String getRtlStartDate() {
		return rtlStartDate;
	}
	/**
	 * @param rtlStartDate
	 */
	public void setRtlStartDate(String rtlStartDate) {
		this.rtlStartDate = rtlStartDate;
	}
	/**
	 * @return
	 */
	public String getRtlEndDate() {
		return rtlEndDate;
	}
	/**
	 * @param rtlEndDate
	 */
	public void setRtlEndDate(String rtlEndDate) {
		this.rtlEndDate = rtlEndDate;
	}
	public Date getInactiveDate() {
		return inactiveDate;
	}
	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}
	
	
}
