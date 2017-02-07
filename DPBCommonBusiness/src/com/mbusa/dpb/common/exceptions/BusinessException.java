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
 * This class represents Business Exceptions of DPB Application.
 * 
 * All the exceptions occured in business logic of the application 
 * should throw this exception or subclass of this.
 *@author 
 */
public class BusinessException extends BaseException implements Serializable {
	/**Generated serial version id*/
	private static final long serialVersionUID = 2047287081870744374L;
	
	/**
	 * constructor
	 */
	public BusinessException() {
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
	public BusinessException(String message) {
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
	public BusinessException(String messageKey, String message) {
		super(messageKey, message);
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
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * constructor
	 * 
	 * @param	code :
	 * 			the exception code.
	 * 			
	 * @param 	message :
	 * 			the message for the code.
	 * 
	 * @param	cause :
	 * 			the exception cause.
	 */
	public BusinessException(String messageKey, String message, Throwable cause) {
		super(messageKey, message, cause);
	}
	/**
	 * constructor
	 * 
	 * @param	cause :
	 * 			the exception cause.
	 * 
	 */
	public BusinessException(Throwable cause) {
		super(cause);
	}
	
}
