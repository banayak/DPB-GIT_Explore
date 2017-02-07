package com.mbusa.dpb.common.exceptions;

import java.io.Serializable;

import javax.ejb.EJBException;

public class PersistanceException extends EJBException implements Serializable {

	private static final long serialVersionUID = 5436263166639968888L;
	
	/**Declaration for exception code.*/
	protected String messageKey;

	/**
	 * constructor
	 */
	public PersistanceException() {
		super();
	}

	/**
	 * constructor
	 * 
	 * @param 	message
	 * 			the message.
	 * 
	 */
	public PersistanceException(String message) {
		super(message);
	}
	
	/**
	 * constructor
	 * 
	 * @param	code :
	 * 			the exception code.
	 * 			
	 * @param 	message :
	 * 			the message for the code.
	 */
	public PersistanceException(String messageKey, String message) {
		super(message);
		this.messageKey = messageKey;
	}


	/**
	 * constructor
	 * 
	 * @param 	message
	 * 			the message.
	 * 
	 * @param	cause :
	 * 			the cause.
	 * 	
	 */
	public PersistanceException(String message, Exception cause) {
		super(message, cause);
	}
	
	/**
	 * constructor
	 * 
	 * @param 	message
	 * 			the message.
	 * 
	 * @param	cause :
	 * 			the cause.
	 * 
	 * @param   code :
	 * 			the business code.
	 * 	
	 */
	public PersistanceException(String messageKey, String message, Exception cause) {
		super(message, cause);
		this.messageKey = messageKey;
	}

	/**
	 * constructor
	 * 
	 * @param 	cause:
	 * 			the cause of exception.
	 */
	public PersistanceException(Exception cause) {
		super(cause);
	}

	/**
	 * @return the messageKey
	 */
	public String getMessageKey() {
		return messageKey;
	}

	/**
	 * @param messageKey the messageKey to set
	 */
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}
}
