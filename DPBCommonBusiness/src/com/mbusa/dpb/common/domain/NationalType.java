/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: NationalType.java
 * Program Version			: 1.0
 * Program Description		: This class is used to hold NationalType code and its description.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   August 29, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.domain;

import java.io.Serializable;

public class NationalType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nationalTypeCd;
	private String nationalTypeCdDesc;
	/**
	 * @return the nationalTypeCd
	 */
	public String getNationalTypeCd() {
		return nationalTypeCd;
	}
	/**
	 * @param nationalTypeCd the nationalTypeCd to set
	 */
	public void setNationalTypeCd(String nationalTypeCd) {
		this.nationalTypeCd = nationalTypeCd;
	}
	/**
	 * @return the nationalTypeCdDesc
	 */
	public String getNationalTypeCdDesc() {
		return nationalTypeCdDesc;
	}
	/**
	 * @param nationalTypeCdDesc the nationalTypeCdDesc to set
	 */
	public void setNationalTypeCdDesc(String nationalTypeCdDesc) {
		this.nationalTypeCdDesc = nationalTypeCdDesc;
	}
}
