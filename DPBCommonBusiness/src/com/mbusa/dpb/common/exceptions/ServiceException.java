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
 * This class represent exceptions thrown by Service Components and Service Clients
 * of DPB Application.
 * 
 * The main use of this exception class is suppressing the exception at caller class.
 *
 *<b>
 *All Service Exceptions will be converted to {@link TechnicalException}s at Component level interface
 *</b>
 *implementations.
 *
 *@see TechnicalException
 *@author 
 */
public class ServiceException extends BaseRuntimeException implements Serializable {
	
	private static final long serialVersionUID = -5645834772273666309L;
	
	/**
	 * Constructor
	 *
	 */
	public ServiceException() {
		super();
	}

	/**
	 * constructor
	 * 
	 * @param 	message :
	 * 			the message for the code.
	 */
	public ServiceException(String message) {
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
	public ServiceException(String messageKey, String message) {
		super(messageKey, message);
	}

	/**
	 * constructor
	 * 
	 * @param	cause :
	 * 			the exception cause.
	 * 			
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}
	/**
	 * constructor
	 * 
	 * @param	cause :
	 * 			the exception cause.
	 * 			
	 * @param 	message :
	 * 			the message for the code.
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * constructor
	 * 
	 * @param	code :
	 * 			the exception code.
	 * 
	 * @param	cause :
	 * 			the exception cause.
	 * 			
	 * @param 	message :
	 * 			the message for the code.
	 */
	public ServiceException(String messageKey, String message, Throwable cause) {
		super(messageKey, message, cause);
	}
}
