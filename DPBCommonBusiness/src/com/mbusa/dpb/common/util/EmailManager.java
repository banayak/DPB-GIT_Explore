/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Chetan Bakshe 
 * Program Name				: EmailManager.java
 * Program Version			: 1.0
 * Program Description		: This call is used to call send the email. 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * CB5002578   Jul 16, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;

import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;

/**
 * The <code>EmailBoImpl</code> this interface is used for email.
 * 
 * @author CB5002578
 */
public class EmailManager {
	
	private static DPBLog LOGGER = DPBLog.getInstance(SendMailDTO.class);
	static final private String CLASSNAME = EmailManager.class.getName();
	
	static final private String PROP_HOST_NAME = "email.host.address";
	
	private static final EmailManager SINGLETON = new EmailManager();
	
	private String hostName;

	/**
	 * Constructor Declaration
	 * 
	 */
	private EmailManager() {	
			hostName = PropertyManager.getPropertyManager().getPropertyAsString(PROP_HOST_NAME);		
	}

	/**
	 * @Description gets the instance 
	 * @return SINGLETON
	 */
	public static final EmailManager getInstance() {
		return SINGLETON;
	}
	
	/**
	 * @param SendMailDTO mailDTO
	 * @return void
	 * @throws TechnicalException
	 */
	public void sendTextMail(SendMailDTO mailDTO) throws TechnicalException {
		final String methodName = "sendTextMail(SendMailDTO mailDTO)";	
		LOGGER.enter(CLASSNAME, methodName);
		
		try {
			MultiPartEmail email = new MultiPartEmail();
			setHeadersAndRecipients(email, mailDTO);
			email.setCharset("UTF-8");
			email.setSubject(mailDTO.getSubject());
			email.setMsg(mailDTO.getContent());
			email.setSentDate(new Date());
			email.send();
		} catch (EmailException e) {			
			throw new TechnicalException(e);
		}
		LOGGER.exit(CLASSNAME, methodName);
	}
	
	/**
	 * @Description This method is used to send mail to user.
	 * @param SendMailDTO mailData 
	 * @return void
	 * @throws TechnicalException 
	 */
	public void sendMail(SendMailDTO mailDTO) throws TechnicalException {
		final String methodName = "sendMail(SendMailDTO mailDTO)";	
		LOGGER.enter(CLASSNAME, methodName);

		try {
			HtmlEmail email = new HtmlEmail();
			//email.setAuthentication("MB02", "User123$");
			email.setCharset("UTF-8");
			setHeadersAndRecipients(email, mailDTO);
			email.setSubject(mailDTO.getSubject());
			email.setHtmlMsg(mailDTO.getContent());
			email.setSentDate(new Date());
			email.send();
		} catch (EmailException eex) {
			
			String errMsg = "Unable to connect SMTP Host";			
			throw new TechnicalException("", errMsg, eex);
		}
		LOGGER.exit(CLASSNAME, methodName);
	}
	/** 
	 * @param Email mail
	 * @param SendMailDTO mailDTO
	 * @throws EmailException 
	 */
	private void setHeadersAndRecipients(Email email, SendMailDTO mailDTO) throws EmailException {
		email.setHostName(hostName);
		
		for(Iterator itr = mailDTO.getTORecipients().iterator(); itr.hasNext(); ) {
			email.addTo((String)itr.next());
		}
		
		Collection recipients = mailDTO.getCCRecipients();
		if(recipients != null && recipients.size() > 0) {
			email.setCc(recipients);
		}

		recipients = mailDTO.getBCCRecipients();
		if(recipients != null && recipients.size() > 0) {
			email.setBcc(recipients);
		}

		String sender = mailDTO.getFrom();
		if(sender != null && sender.trim().length() > 0)
		email.setFrom(sender);
	}
	/**
	 *  This method is used for sends the Email with attachment.
	 * @param mailDTO SendMailDTO contains the all mail information.
	 */
	public void sendMailWithAttachment(SendMailDTO mailDTO) throws TechnicalException {
		
		final String methodName = "sendMailWithAttachment";	
		LOGGER.enter(CLASSNAME, methodName);
		
		try {
			MultiPartEmail mulpartMail = new MultiPartEmail();
			setHeadersAndRecipients(mulpartMail, mailDTO);
			mulpartMail.setSubject(mailDTO.getSubject());
			
			MimeMultipart mimeMultiPart = new MimeMultipart();
			
			//Construct Body Part
			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(mailDTO.getContent(), "text/html");
			mimeMultiPart.addBodyPart(bodyPart);
			
			MailAttachment[] attachments = mailDTO.getAttachments();
			
			for(int i = 0; i < attachments.length; i++) {
				MimeBodyPart filePart = new MimeBodyPart();
				filePart.setDataHandler(new DataHandler(new MailAttachmentDataSource(attachments[i])));
				filePart.setFileName(attachments[i].getFilename());
				mimeMultiPart.addBodyPart(filePart);
			}
			
			mulpartMail.setSentDate(new Date());
			mulpartMail.setContent(mimeMultiPart);
			mulpartMail.send();
		} catch (Exception ex) {
			LOGGER.error("Sending mail failed",ex);
			throw new TechnicalException(ex);
		}
	}
	
	protected class MailAttachmentDataSource implements DataSource {
		
		private MailAttachment attachment;
		
		/**
		 * Constructor
		 * @param MailAttachment attachment
		 */
		public MailAttachmentDataSource(MailAttachment attachment) {
			this.attachment = attachment;
		}

		/**
		 * @Description gets the contenttype
		 * @return String
		 */
		
		public String getContentType() {
			return attachment.getMimetype();
		}

		/**
		 * @Description gets the inputStream
		 * @return InputStream
		 * @throws IOException
		 */
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(attachment.getContent());
		}

		/**
		 * @Description gets the name
		 * @return String
		 */
		public String getName() {
			return attachment.getFilename();
		}

		/**
		 * @Description gets the outputStream
		 * @return OutputStream
		 * @throws IOException
		 */
		public OutputStream getOutputStream() throws IOException {
			return new ByteArrayOutputStream();
		}
	}
}


