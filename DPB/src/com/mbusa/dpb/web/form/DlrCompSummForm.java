package com.mbusa.dpb.web.form;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class DlrCompSummForm {
	private String poCount;
	private String custExp;
	private String custExpSales;
	private String custExpService;
	private String nvSales;
	private String preOwned;
	private String brdStd;
	private String total;
	private String unearnedBonus;
	private String bonusType;
	private String period;
	private Integer seq;
	
	//Ratna Start
	private String smFran;
	
	
	public String getSmFran() {
		return smFran;
	}
	public void setSmFran(String smFran) {
		this.smFran = convertNumberWithReadbleFormat(smFran);
	}
	//Ratna End

	public String getPoCount() {
		return poCount;
	}
	public void setPoCount(String poCount) {
		this.poCount = convertNumberWithReadbleFormat(poCount);
	}
	public String getCustExp() {
		return custExp;
	}
	public void setCustExp(String custExp) {
		this.custExp = convertNumberWithReadbleFormat(custExp);
	}
	public String getCustExpSales() {
		return custExpSales;
	}
	public void setCustExpSales(String custExpSales) {
		this.custExpSales = convertNumberWithReadbleFormat(custExpSales);
	}
	public String getCustExpService() {
		return custExpService;
	}
	public void setCustExpService(String custExpService) {
		this.custExpService = convertNumberWithReadbleFormat(custExpService);
	}
	public String getNvSales() {
		return nvSales;
	}
	public void setNvSales(String nvSales) {
		this.nvSales = convertNumberWithReadbleFormat(nvSales);
	}
	public String getPreOwned() {
		return preOwned;
	}
	public void setPreOwned(String preOwned) {
		this.preOwned = convertNumberWithReadbleFormat(preOwned);
	}
	public String getBrdStd() {
		return brdStd;
	}
	public void setBrdStd(String brdStd) {
		this.brdStd = convertNumberWithReadbleFormat(brdStd);
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = convertNumberWithReadbleFormat(total);
	}
	public String getUnearnedBonus() {
		return unearnedBonus;
	}
	public void setUnearnedBonus(String unearnedBonus) {
		this.unearnedBonus = convertNumberWithReadbleFormat(unearnedBonus);
	}
	public String getBonusType() {
		return bonusType;
	}
	public void setBonusType(String bonusType) {
		this.bonusType = convertNumberWithReadbleFormat(bonusType);
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = convertNumberWithReadbleFormat(period);
	}
	
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public DlrCompSummForm(DlrCompSummForm form) {
		    this.poCount = form.poCount; // you can access  
		    this.custExp = form.custExp;
		    this.custExpSales = form.custExpSales;
		    this.custExpService = form.custExpService;
		    this.nvSales = form.nvSales;
		    this.preOwned = form.preOwned;
		    this.brdStd = form.brdStd;
		    this.smFran = form.smFran;
		    this.total = form.total;
		    this.unearnedBonus = form.unearnedBonus;
		    this.bonusType = form.bonusType;
		    this.period = form.period;
		  }
	public DlrCompSummForm() {
		// TODO Auto-generated constructor stub
	}

	private String convertNumberWithReadbleFormat(String inputNumber) {
		if(StringUtils.isNotBlank(inputNumber) && NumberUtils.isNumber(inputNumber.trim())){
			inputNumber = NumberFormat.getIntegerInstance(Locale.US).format(NumberUtils.createDouble(inputNumber).intValue());
		}
		return inputNumber;
	}
	

}
