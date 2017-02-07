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
 * @author CB5002578
 *
 */
public class ConverterException extends ServiceException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3131865594867579418L;

	/**
	 * constructor
	 */
	public ConverterException() {
	}

	/**
	 * constructor
	 * 
	 * @param message
	 */
	public ConverterException(String message) {
		super(message);
	}

	/**
	 * constructor
	 * 
	 * @param code
	 * 
	 * @param message
	 */
	public ConverterException(String messageKey, String message) {
		super(messageKey, message);
	}

	/**
	 * constructor
	 * 
	 * @param cause
	 */
	public ConverterException(Throwable cause) {
		super(cause);
	}

	/**
	 * constructor
	 * 
	 * @param message
	 * 
	 * @param cause
	 */
	public ConverterException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * constructor
	 * 
	 * @param code
	 * 
	 * @param message
	 * 
	 * @param cause
	 */
	public ConverterException(String messageKey, String message, Throwable cause) {
		super(messageKey, message, cause);
	}

}
