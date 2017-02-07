/**
 * 
 */
package com.mbusa.dpb.web.form;

import java.util.ArrayList;

/**
 * @author RK5005820
 *
 */
public class VehicleConditionForm {	
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
