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
 *This exception represents All technical exceptions occured in DPB Application.
 *
 *<b>
 * The technical exception could be the exceptions while invoking remote component or
 * involing database.
 * 
 * <p>
 * All Technical Exceptions with the code below 500 will be treeted as "Fatal" exceptions.
 * <p>
 * All Technical Exceptions with the code above 500 will be treeted as "Severe" exceptions.
 * </b>
 *
 *@see ServiceException
 *@author 
 */
public class TechnicalException extends BaseException implements Serializable {
	
	private static final long serialVersionUID = -6707661104237147563L;
	
	/**
	 *	Constructor 
	 */
	public TechnicalException() {
		super();
	}

	/**
	 * constructor
	 * 
	 * @param 	message :
	 * 			the message for the code.
	 */
	public TechnicalException(String message) {
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
	public TechnicalException(String messageKey, String message) {
		super(messageKey, message);
	}
	/**
	 * constructor
	 * 
	 * @param	cause :
	 * 			the exception cause.
	 * 			
	 */
	public TechnicalException(Throwable cause) {
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
	public TechnicalException(String message, Throwable cause) {
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
	 * @param 	cause :
	 * 			the cause.
	 */
	public TechnicalException(String messageKey, String message, Throwable cause) {
		super(messageKey, message, cause);
	}
}
