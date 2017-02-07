package com.mbusa.dpb.web.form;

import java.util.ArrayList;

public class LdrspAccountIDMappingForm {
	private ArrayList<String> PcConditionList =  new ArrayList <String>();
	private ArrayList<String> vanConditionList = new ArrayList <String>();
	private ArrayList<String> smartConditionList = new ArrayList <String>() ;	
	private ArrayList<String> ftlConditionList = new ArrayList <String>();
	private String pcStatus; 
	private String vanStatus;
	private String smartStatus;
	private String ftlStatus;
	private String pcId; 
	private String vanId;
	private String smartId;
	private String ftlId;
	private String condType;
	private String pcCustExpid;
	
	private ArrayList<String> pcAccountIdList =  new ArrayList <String>();
	private ArrayList<String> vanAccountIdList =  new ArrayList <String>();
	private ArrayList<String> smartAccountIdList =  new ArrayList <String>();
	private ArrayList<String> ftlAccountIdList =  new ArrayList <String>();
	
	private ArrayList<String> pcStatusList = new ArrayList <String>();
	private ArrayList<String> vanStatusList = new ArrayList <String>();
	private ArrayList<String> smartStatusList = new ArrayList <String>();
	private ArrayList<String> ftlStatusList = new ArrayList <String>();
	
	private ArrayList<String> pcIndicatorList = new ArrayList <String>();	
	private ArrayList<String> vanIndicatorList = new ArrayList <String>();
	private ArrayList<String> smartIndicatorList = new ArrayList <String>();
	private ArrayList<String> ftlIndicatorList = new ArrayList <String>();
	
	private ArrayList<Integer> pcLdrspBnsPgmIdList =  new ArrayList <Integer>();
	private ArrayList<Integer> vanLdrspBnsPgmIdList =  new ArrayList <Integer>();
	private ArrayList<Integer> smartLdrspBnsPgmIdList =  new ArrayList <Integer>();
	private ArrayList<Integer> ftlLdrspBnsPgmIdList =  new ArrayList <Integer>();
	private String userId;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public ArrayList<String> getPcIndicatorList() {
		return pcIndicatorList;
	}
	public void setPcIndicatorList(ArrayList<String> pcIndicatorList) {
		this.pcIndicatorList = pcIndicatorList;
	}
	public ArrayList<String> getVanIndicatorList() {
		return vanIndicatorList;
	}
	public void setVanIndicatorList(ArrayList<String> vanIndicatorList) {
		this.vanIndicatorList = vanIndicatorList;
	}
	public ArrayList<String> getSmartIndicatorList() {
		return smartIndicatorList;
	}
	public void setSmartIndicatorList(ArrayList<String> smartIndicatorList) {
		this.smartIndicatorList = smartIndicatorList;
	}
	public ArrayList<String> getFtlIndicatorList() {
		return ftlIndicatorList;
	}
	public void setFtlIndicatorList(ArrayList<String> ftlIndicatorList) {
		this.ftlIndicatorList = ftlIndicatorList;
	}
	public ArrayList<Integer> getPcLdrspBnsPgmIdList() {
		return pcLdrspBnsPgmIdList;
	}
	public void setPcLdrspBnsPgmIdList(ArrayList<Integer> pcLdrspBnsPgmIdList) {
		this.pcLdrspBnsPgmIdList = pcLdrspBnsPgmIdList;
	}
	public ArrayList<Integer> getVanLdrspBnsPgmIdList() {
		return vanLdrspBnsPgmIdList;
	}
	public void setVanLdrspBnsPgmIdList(ArrayList<Integer> vanLdrspBnsPgmIdList) {
		this.vanLdrspBnsPgmIdList = vanLdrspBnsPgmIdList;
	}
	public ArrayList<Integer> getSmartLdrspBnsPgmIdList() {
		return smartLdrspBnsPgmIdList;
	}
	public void setSmartLdrspBnsPgmIdList(ArrayList<Integer> smartLdrspBnsPgmIdList) {
		this.smartLdrspBnsPgmIdList = smartLdrspBnsPgmIdList;
	}
	public ArrayList<Integer> getFtlLdrspBnsPgmIdList() {
		return ftlLdrspBnsPgmIdList;
	}
	public void setFtlLdrspBnsPgmIdList(ArrayList<Integer> ftlLdrspBnsPgmIdList) {
		this.ftlLdrspBnsPgmIdList = ftlLdrspBnsPgmIdList;
	}
	
	public ArrayList<String> getPcAccountIdList() {
		return pcAccountIdList;
	}
	public void setPcAccountIdList(ArrayList<String> pcAccountIdList) {
		this.pcAccountIdList = pcAccountIdList;
	}
	public ArrayList<String> getVanAccountIdList() {
		return vanAccountIdList;
	}
	public void setVanAccountIdList(ArrayList<String> vanAccountIdList) {
		this.vanAccountIdList = vanAccountIdList;
	}
	public ArrayList<String> getSmartAccountIdList() {
		return smartAccountIdList;
	}
	public void setSmartAccountIdList(ArrayList<String> smartAccountIdList) {
		this.smartAccountIdList = smartAccountIdList;
	}
	public ArrayList<String> getFtlAccountIdList() {
		return ftlAccountIdList;
	}
	public void setFtlAccountIdList(ArrayList<String> ftlAccountIdList) {
		this.ftlAccountIdList = ftlAccountIdList;
	}
	public ArrayList<String> getPcStatusList() {
		return pcStatusList;
	}
	public void setPcStatusList(ArrayList<String> pcStatusList) {
		this.pcStatusList = pcStatusList;
	}
	public ArrayList<String> getVanStatusList() {
		return vanStatusList;
	}
	public void setVanStatusList(ArrayList<String> vanStatusList) {
		this.vanStatusList = vanStatusList;
	}
	public ArrayList<String> getSmartStatusList() {
		return smartStatusList;
	}
	public void setSmartStatusList(ArrayList<String> smartStatusList) {
		this.smartStatusList = smartStatusList;
	}
	public ArrayList<String> getFtlStatusList() {
		return ftlStatusList;
	}
	public void setFtlStatusList(ArrayList<String> ftlStatusList) {
		this.ftlStatusList = ftlStatusList;
	}
	public String getPcCustExpid() {
		return pcCustExpid;
	}
	public void setPcCustExpid(String pcCustExpid) {
		this.pcCustExpid = pcCustExpid;
	}
	public String getCondType() {
		return condType;
	}
	public void setCondType(String condType) {
		this.condType = condType;
	}
	public ArrayList<String> getPcConditionList() {
		return PcConditionList;
	}
	public void setPcConditionList(ArrayList<String> pcConditionList) {
		PcConditionList = pcConditionList;
	}
	public ArrayList<String> getVanConditionList() {
		return vanConditionList;
	}
	public void setVanConditionList(ArrayList<String> vanConditionList) {
		this.vanConditionList = vanConditionList;
	}
	public ArrayList<String> getSmartConditionList() {
		return smartConditionList;
	}
	public void setSmartConditionList(ArrayList<String> smartConditionList) {
		this.smartConditionList = smartConditionList;
	}
	public ArrayList<String> getFtlConditionList() {
		return ftlConditionList;
	}
	public void setFtlConditionList(ArrayList<String> ftlConditionList) {
		this.ftlConditionList = ftlConditionList;
	}
	public String getPcStatus() {
		return pcStatus;
	}
	public void setPcStatus(String pcStatus) {
		this.pcStatus = pcStatus;
	}
	public String getVanStatus() {
		return vanStatus;
	}
	public void setVanStatus(String vanStatus) {
		this.vanStatus = vanStatus;
	}
	public String getSmartStatus() {
		return smartStatus;
	}
	public void setSmartStatus(String smartStatus) {
		this.smartStatus = smartStatus;
	}
	public String getFtlStatus() {
		return ftlStatus;
	}
	public void setFtlStatus(String ftlStatus) {
		this.ftlStatus = ftlStatus;
	}
	public String getPcId() {
		return pcId;
	}
	public void setPcId(String pcId) {
		this.pcId = pcId;
	}
	public String getVanId() {
		return vanId;
	}
	public void setVanId(String vanId) {
		this.vanId = vanId;
	}
	public String getSmartId() {
		return smartId;
	}
	public void setSmartId(String smartId) {
		this.smartId = smartId;
	}
	public String getFtlId() {
		return ftlId;
	}
	public void setFtlId(String ftlId) {
		this.ftlId = ftlId;
	}

}
