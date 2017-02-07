package com.mbusa.dpb.common.domain;

import java.io.Serializable;


/**
 * @author RK5005820
 *
 */
public class DPBProcessBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int processDefinitionId;
	private int retailDateId;
	private int definitionId;
	private String processType;
	/**
	 * @return the processDefinitionId
	 */
	public int getProcessDefinitionId() {
		return processDefinitionId;
	}
	/**
	 * @return the retailDateId
	 */
	public int getRetailDateId() {
		return retailDateId;
	}
	/**
	 * @return the definitionId
	 */
	public int getDefinitionId() {
		return definitionId;
	}
	/**
	 * @return the processType
	 */
	public String getProcessType() {
		return processType;
	}
	/**
	 * @param processDefinitionId the processDefinitionId to set
	 */
	public void setProcessDefinitionId(int processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	/**
	 * @param retailDateId the retailDateId to set
	 */
	public void setRetailDateId(int retailDateId) {
		this.retailDateId = retailDateId;
	}
	/**
	 * @param definitionId the definitionId to set
	 */
	public void setDefinitionId(int definitionId) {
		this.definitionId = definitionId;
	}
	/**
	 * @param processType the processType to set
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
	}

}
