package com.mbusa.dpb.common.helper;


import java.io.Serializable; 

/*********************************************************************************************
 * Project Name	: 
 * Module Name	:
 * Program Name	: 
 * Description	:  
 *
 * Created By	: SYNTEL 		Date: Aug 5, 2011		Version: 1.0
 * *******************************************************************************************
 * Modification Details:
 *
 * Name							Date				Purpose
 * -------------------------------------------------------------------------------------------
 * SYNTEL									First Draft
 *
 ********************************************************************************************/

/**
 * 
 * 
 * @author SYNTEL 
 * @version 1.0
 */
public class MenuNode implements Serializable{
	/** 
	 *
	 */
	private static final long serialVersionUID = -1001118203071970712L;
	private String labelName;
	private String methodName;
	private int nodeID;
	private int parentNode;
	private int sequence;
	private String mouseOverText;
	private char enabled;
	private String role;
	
	/**
	 * 
	 */
	public MenuNode(){
	
	}
	/**
	 * 
	 * @param String labelName
	 * @param String methodName
	 * @param int parentNode
	 * @param int sequence
	 * @param int sequence
	 * @param String mouseOverText 
	 */
	public MenuNode(String labelName,String methodName,int nodeID,int parentNode,int sequence,String mouseOverText){
		this.labelName = labelName;
		this.methodName = methodName;
		this.nodeID = nodeID;
		this.parentNode = parentNode;
		this.sequence = sequence;
		this.mouseOverText = mouseOverText;
	}
	
	/**
	 * 
	 * @param node
	 */
	public MenuNode(MenuNode node){
		this.nodeID = node.nodeID;
		this.parentNode = node.parentNode;
	}
	
	
	/**
	 * 
	 * @param int parentNode
	 */
	public MenuNode(int parentNode){
		this.parentNode = parentNode;
	}
	/**
	 * @return the labelName
	 */
	public String getLabelName() {
		return labelName;
	}
	/**
	 * @param labelName the labelName to set
	 */
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return the nodeID
	 */
	public int getNodeID() {
		return nodeID;
	}
	/**
	 * @param nodeID the nodeID to set
	 */
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}
	/**
	 * @return the parentNode
	 */
	public int getParentNode() {
		return parentNode;
	}
	/**
	 * @param int parentNode
	 */
	public void setParentNode(int parentNode) {
		this.parentNode = parentNode;
	}

	/**
	 * @return int
	 */
	public int getSequence() {
		return sequence;
	}

	/**
	 * @param int sequence
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return String
	 */
	public String getMouseOverText() {
		return mouseOverText;
	}

	/**
	 * @param String mouseOverText
	 */
	public void setMouseOverText(String mouseOverText) {
		this.mouseOverText = mouseOverText;
	}
	/**
	 * 
	 * 
	 * 
	 * @return char
	 */
	public char getEnabled() {
		return enabled;
	}
	/**
	 * 
	 * 
	 * 
	 * @param char enabled
	 */
	public void setEnabled(char enabled) {
		this.enabled = enabled;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	

	
	
}
