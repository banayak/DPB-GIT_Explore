/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: UseCode.java
 * Program Version			: 1.0
 * Program Description		: This class is used to hold use codes and its description.
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

public class UseCode implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String useCode;
	private String useCodeDesc;
	/**
	 * @return the useCode
	 */
	public String getUseCode() {
		return useCode;
	}
	/**
	 * @param useCode the useCode to set
	 */
	public void setUseCode(String useCode) {
		this.useCode = useCode;
	}
	/**
	 * @return the useCodeDesc
	 */
	public String getUseCodeDesc() {
		return useCodeDesc;
	}
	/**
	 * @param useCodeDesc the useCodeDesc to set
	 */
	public void setUseCodeDesc(String useCodeDesc) {
		this.useCodeDesc = useCodeDesc;
	}
}
