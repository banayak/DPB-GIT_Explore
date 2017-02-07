package com.mbusa.dpb.web.form;

public class FileProcessDefForm {

	private String processDefinitionId;
	private String definitionId;
	private String definitionType;
	private String processDate;
	private String reasonForUpdate;
	private String processingTrigger;
	private String processingTime;
	private String status;
	private boolean flag;
	private boolean reProcessFlag;
	private String processType;
	private int recCount;
	private int progId;
	
	/**
	 * @return the recCount
	 */
	public int getRecCount() {
		return recCount;
	}

	/**
	 * @param recCount the recCount to set
	 */
	public void setRecCount(int recCount) {
		this.recCount = recCount;
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
	 * @return the reProcessFlag
	 */
	public boolean isReProcessFlag() {
		return reProcessFlag;
	}

	/**
	 * @param reProcessFlag the reProcessFlag to set
	 */
	public void setReProcessFlag(boolean reProcessFlag) {
		this.reProcessFlag = reProcessFlag;
	}

	private String showStat;
	private String showTrigger;
	
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getDefinitionType() {
		return definitionType;
	}

	public void setDefinitionType(String definitionType) {
		this.definitionType = definitionType;
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public String getReasonForUpdate() {
		return reasonForUpdate;
	}

	public void setReasonForUpdate(String reasonForUpdate) {
		this.reasonForUpdate = reasonForUpdate;
	}

	public String getProcessingTrigger() {
		return processingTrigger;
	}

	public void setProcessingTrigger(String processingTrigger) {
		this.processingTrigger = processingTrigger;
	}

	public String getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(String processingTime) {
		this.processingTime = processingTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getShowStat() {
		return showStat;
	}

	public void setShowStat(String showStat) {
		this.showStat = showStat;
	}

	public String getShowTrigger() {
		return showTrigger;
	}

	/**
	 * @return the progId
	 */
	public int getProgId() {
		return progId;
	}

	/**
	 * @param progId the progId to set
	 */
	public void setProgId(int progId) {
		this.progId = progId;
	}

	public void setShowTrigger(String showTrigger) {
		this.showTrigger = showTrigger;
	}


}
