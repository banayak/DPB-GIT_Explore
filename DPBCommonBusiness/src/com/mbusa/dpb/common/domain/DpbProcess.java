package com.mbusa.dpb.common.domain;

import java.io.Serializable;
import java.util.Date;

public class DpbProcess implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer processId ;
	private Integer prcDefID;		
	private String processType; 
	private Integer idOverProcDte; 
	private String tmeDpbOvrdTrgr; 
	private String ovrdTrgr;
	private String updateBy;
	private String createdBy;
	private Date createDate;
	private Date updatedDate;
	private Integer parentPgmId ;
	private java.sql.Date procDte;
	private java.sql.Date actlProcDte;
	/**
	 * @return the processId
	 */
	public Integer getProcessId() {
		return processId;
	}
	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	/**
	 * @return the prcDefID
	 */
	public Integer getPrcDefID() {
		return prcDefID;
	}
	/**
	 * @param prcDefID the prcDefID to set
	 */
	public void setPrcDefID(Integer prcDefID) {
		this.prcDefID = prcDefID;
	}
	
	/**
	 * @return the processType
	 */
	public String getProcessType() {
		return processType;
	}
	/**
	 * @param processType the processType to set
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	/**
	 * @return the idOverProcDte
	 */
	public Integer getIdOverProcDte() {
		return idOverProcDte;
	}
	/**
	 * @param idOverProcDte the idOverProcDte to set
	 */
	public void setIdOverProcDte(Integer idOverProcDte) {
		this.idOverProcDte = idOverProcDte;
	}
	/**
	 * @return the tmeDpbOvrdTrgr
	 */
	public String getTmeDpbOvrdTrgr() {
		return tmeDpbOvrdTrgr;
	}
	/**
	 * @param tmeDpbOvrdTrgr the tmeDpbOvrdTrgr to set
	 */
	public void setTmeDpbOvrdTrgr(String tmeDpbOvrdTrgr) {
		this.tmeDpbOvrdTrgr = tmeDpbOvrdTrgr;
	}
	/**
	 * @return the ovrdTrgr
	 */
	public String getOvrdTrgr() {
		return ovrdTrgr;
	}
	/**
	 * @param ovrdTrgr the ovrdTrgr to set
	 */
	public void setOvrdTrgr(String ovrdTrgr) {
		this.ovrdTrgr = ovrdTrgr;
	}
	/**
	 * @return the updateBy
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}
	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	/**
	 * @return the parentPgmId
	 */
	public Integer getParentPgmId() {
		return parentPgmId;
	}
	/**
	 * @param parentPgmId the parentPgmId to set
	 */
	public void setParentPgmId(Integer parentPgmId) {
		this.parentPgmId = parentPgmId;
	}
	/**
	 * @return the procDte
	 */
	public java.sql.Date getProcDte() {
		return procDte;
	}
	/**
	 * @param procDte the procDte to set
	 */
	public void setProcDte(java.sql.Date procDte) {
		this.procDte = procDte;
	}
	/**
	 * @return the actlProcDte
	 */
	public java.sql.Date getActlProcDte() {
		return actlProcDte;
	}
	/**
	 * @param actlProcDte the actlProcDte to set
	 */
	public void setActlProcDte(java.sql.Date actlProcDte) {
		this.actlProcDte = actlProcDte;
	}		
}
