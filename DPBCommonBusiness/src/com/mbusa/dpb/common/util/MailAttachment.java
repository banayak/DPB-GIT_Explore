/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: 
 * Program Name				: MailAttachment.java
 * Program Version			: 1.0
 * Program Description		: This call is used to call send the email with Attachment. 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * 
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.util;

import java.io.Serializable;
/**
 * 
 * @author SK5008848
 *
 */
public class MailAttachment implements Serializable{

	private static final long serialVersionUID = 1L;

	private String filename;
	
	private String mimetype;
	
	private byte[] content;
	
	/**
	 * 
	 * @param filename
	 * @param mimetype
	 * @param content
	 */
	public MailAttachment(String filename, String mimetype, byte[] content) {
		this.filename = filename;
		this.mimetype = mimetype;
		this.content = content;
	}

	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @return the mimetype
	 */
	public String getMimetype() {
		return mimetype;
	}
}
