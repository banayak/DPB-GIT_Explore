package com.mbusa.dpb.common.exceptions;

public class GenericExceptionHandler extends DPBExceptionHandler {

	public Exception handleException(Exception exc) {
		Exception finalException = null;
		if(!BaseException.class.isAssignableFrom(exc.getClass()) && !BaseRuntimeException.class.isAssignableFrom(exc.getClass())) {
			finalException = new TechnicalException(IExceptionIDs.UN_HANDLED, exc.getMessage(), exc);
		}
		if(finalException == null) {
			finalException = exc;
		}
		return finalException;
	}

}
