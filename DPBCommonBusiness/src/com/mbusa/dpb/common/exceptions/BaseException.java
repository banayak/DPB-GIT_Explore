/*********************************************************************************************
 * Project Name	: DPB
 * Module Name	: 
 * Program Name	: BaseException.java
 * Description	: 
 *
 * Created By	: Syntel		Date: 		Version: 1.0
 * *******************************************************************************************
 * Modification Details:
 *
 * Name							Date				Purpose
 * -------------------------------------------------------------------------------------------
 *
 *
 ********************************************************************************************/

package com.mbusa.dpb.common.exceptions;

import java.io.Serializable;


/**
 * 
 * This is the base exception class for all DPB Application checked 
 * exceptions.
 * This provides the exception code functionality compared to normal 
 * java exceptions.
 * 
 * This code will be used to handle the corresponding scenarios of the
 * application.
 *
 *@see BusinessException and TechnicalException
 *@author cbakshe
 */
public abstract class BaseException extends Exception implements Serializable {
	/**Generated serial version id.*/
	private static final long serialVersionUID = 1915439216331826657L;
	
	/**Declaration for Message key.*/
	protected String messageKey;

	/**
	 * constructor
	 */
	public BaseException() {
		super();
	}

	/**
	 * constructor
	 * 
	 * @param 	message
	 * 			the message.
	 * 
	 */
	public BaseException(String message) {
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
	public BaseException(String messageKey, String message) {
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
	public BaseException(String message, Throwable cause) {
		super(message, cause);
		if(BaseException.class.isAssignableFrom(cause.getClass())) {
			this.messageKey = ((BaseException) cause).messageKey;
		}
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
	public BaseException(String messageKey, String message, Throwable cause) {
		super(message, cause);
		this.messageKey = messageKey;
	}

	/**
	 * constructor
	 * 
	 * @param 	cause:
	 * 			the cause of exception.
	 */
	public BaseException(Throwable cause) {
		super(cause);
		if(BaseException.class.isAssignableFrom(cause.getClass())) {
			this.messageKey = ((BaseException) cause).messageKey;
		}
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
