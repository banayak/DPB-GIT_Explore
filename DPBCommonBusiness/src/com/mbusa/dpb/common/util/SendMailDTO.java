/*********************************************************************************************
 * Project Name	: DPB
 * Module Name	: 
 * Program Name	: SendMailDTO.java
 * Description	:  
 *
 * Created By	: SYNTEL 		Date: July 18, 2012		Version: 1.0
 * *******************************************************************************************
 * Modification Details:
 *
 * Name							Date					Purpose
 * -------------------------------------------------------------------------------------------
 * SYNTEL						July 18, 2013			First Draft
 *
 ********************************************************************************************/

/**
 * 
 * @author CB5002578
 *
 */
package com.mbusa.dpb.common.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SendMailDTO implements Serializable {
	
	private static final long serialVersionUID = -394309002434800625L;
	
	public static final String EMPTY_STRING = new String();
	
	private Set toRecipients;//srt1@syngdcdi0006.syntelorg.com
	
	private Set ccRecipients;
	
	private Set bccRecipients;
	
	private String subject;
	
	private String content;
	
	private String from;
	
	private Locale locale;
	
	private MailAttachment[] attachments;
	
	private static final String PROP_DPB_EMAIL_PLACEHLDR="dpb.mail.placeholder";
	
	private static final String PROP_DPB_EMAIL_RPLCHLDR = "dpb.mail.replaceholder";
	
	private static final String placeholder = "chetan_bakshe@syntelinc.com";
	
	private static final String replaceholder = "chetan_bakshe@syntelinc.com";
	
	

	/**
	 * @Description gets the property
	 * @return String property
	 */
	public Set getBCCRecipients() {
		if(bccRecipients == null) {
			bccRecipients = new LinkedHashSet();
		}
		if(placeholder != null && bccRecipients.contains(placeholder)){
			bccRecipients.remove(placeholder);
			bccRecipients.add(replaceholder);
		}
		return bccRecipients;
	}
	
	/**
	 * @param String bccRecipient
	 * @return void
	 */
	public void addBCCRecipient(String bccRecipient) {
		getBCCRecipients().add(bccRecipient);
	}


	/**
	 * @Description gets the ccRecipients
	 * @return Set ccRecipients
	 */
	public Set getCCRecipients() {
		if(ccRecipients == null) {
			ccRecipients = new LinkedHashSet();
		}
		if(placeholder != null && ccRecipients.contains(placeholder)){
			ccRecipients.remove(placeholder);
			ccRecipients.add(replaceholder);
		}
		return ccRecipients;
	}
	
	/**
	 * @param String ccRecipient
	 * @return void
	 */
	public void addCCRecipient(String ccRecipient) {
		getCCRecipients().add(ccRecipient);
	}


	/**
	 * @Description gets the content
	 * @return String content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @Description sets the content
	 * @param String content
	 * @return void
	 */
	public void setContent(String content) {
		this.content = content;
	}


	/**
	 * @Description gets the subject
	 * @return String subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @Description sets the subject
	 * @param String subject
	 * @return void
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}


	/**
	 * @Description gets the toRecipients
	 * @return Set toRecipients
	 */
	public Set getTORecipients() {
		if(toRecipients == null) {
			toRecipients = new LinkedHashSet();
		}
		if(placeholder != null && toRecipients.contains(placeholder)){
			toRecipients.remove(placeholder);
			toRecipients.add(replaceholder);
		}
		return toRecipients;
	}
	
	/**
	 * @param String toRecipient
	 * @return void
	 */
	public void addTORecipient(String toRecipient) {
		//getTORecipients().add(toRecipient);
		if (toRecipient != null && !toRecipient.isEmpty()) {
			getTORecipients().addAll(Arrays.asList(toRecipient.split(",")));
		}
	}
		
	/**
	 * @return String
	 */
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Locale: ")
		.append(locale)
		.append("\n")
		.append("From: ")
		.append(from)
		.append("\n")
		.append("TO: ")
		.append(getTORecipients())
		.append("\n")
		.append("CC: ")
		.append(getCCRecipients())
		.append("\n")
		.append("BCC: ")
		.append(getBCCRecipients())
		.append("\n")
		.append("Subject: ")
		.append(subject)
		.append("\n")
		.append("Content: ")
		.append(content)
		.append("\n");
		return result.toString();
	}
	/**
	 * @Description gets the from
	 * @return String from
	 */
	public String getFrom() {
		if(placeholder != null &&  from!=null && from.equalsIgnoreCase(placeholder)){
			from = replaceholder;
		}
		return from;
	}
	/**
	 * @Description sets the from
	 * @param String from
	 * @return void
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * @Description gets the locale
	 * @return Locale locale
	 */
	public Locale getLocale() {
		return locale;
	}
	/**
	 * @Description sets the locale
	 * @param Locale locale
	 * @return void
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	/**
	 * @Description gets the bccRecipientsAsString
	 * @return String bccRecipientsAsString
	 */	
	public void removeToRecipients() {
		toRecipients = null;
	}

	/**
	 * @return the attachments
	 */
	public MailAttachment[] getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(MailAttachment[] attachments) {
		this.attachments = attachments;
	}
}
