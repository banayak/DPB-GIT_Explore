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
 * This is the base exception class for all DPB application Runtime 
 * exceptions.
 * This provides the exception code functionality compared to normal 
 * java exceptions.
 * 
 * This code will be used to handle the corresponding scenarios of the
 * application.
 *
 *@see ServiceException and PersistanceException
 *
 *@author 
 */
public abstract class BaseRuntimeException extends RuntimeException implements Serializable{
	/**Declaration for generated serial version*/
	private static final long serialVersionUID = 1773394214294139205L;


	private String messageKey;

	

	/**
	 * Constructor
	 */
	public BaseRuntimeException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param 	message :
	 * 			the message.
	 */
	public BaseRuntimeException(String message) {
		super(message);
	}
	
	/**
	 * Constructor
	 * 
	 * @param	code :
	 * 			the exception code.
	 * 
	 * @param 	message :
	 * 			the exception message
	 */
	public BaseRuntimeException(String messageKey, String message) {
		super(message);
		this.messageKey = messageKey;
	}

	/**
	 * Constructor
	 * 
	 * @param	cause :
	 * 			the exception cause.
	 * 
	 */
	public BaseRuntimeException(Throwable cause) {
		super(cause);
		if(BaseException.class.isAssignableFrom(cause.getClass())) {
			this.messageKey = ((BaseException) cause).messageKey;
		}
	}
	/**
	 * Constructor
	 * 
	 * @param	cause :
	 * 			the exception cause.
	 * 
	 * @param	message :
	 * 			the message.
	 * 
	 */
	public BaseRuntimeException(String message, Throwable cause) {
		super(message, cause);
		if(BaseException.class.isAssignableFrom(cause.getClass())) {
			this.messageKey = ((BaseException) cause).messageKey;
		}
	}
	
	/**
	 * Constructor
	 * 
	 * @param	cause :
	 * 			the exception cause.
	 * 
	 * @param	message :
	 * 			the message.
	 * 
	 * @param	code :
	 * 			the exception code.
	 * 
	 */
	public BaseRuntimeException(String messageKey, String message, Throwable cause) {
		super(message, cause);
		this.messageKey = messageKey;
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
