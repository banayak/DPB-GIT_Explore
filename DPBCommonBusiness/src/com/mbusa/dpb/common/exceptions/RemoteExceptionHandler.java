package com.mbusa.dpb.common.exceptions;

import java.rmi.RemoteException;


/**
 * This class handles the {@link java.rmi.RemoteException} and converts into the DPB 
 * 
 * specific fatal {@link TechnicalException}.
 * 
 * @author 
 */
public class RemoteExceptionHandler extends DPBExceptionHandler {
	/**Declaration for Loggers*/
	//private IApplicationLogger LOGGER = ApplicationLogger.getApplicationLogger(HibernateExceptionHandler.class);

	/**
	 * This method converts {@link java.rmi.RemoteException} into fatal {@link TechnicalException}
	 */
	public Exception handleException(Exception exception) {
		Exception returnException = null;
		
		if(exception instanceof RemoteException) {
			returnException = new TechnicalException("Protocol Exception", exception);
			/*
			 * @description Exception wile Presentation layer is invoking the EJB.
			 * 
			 * @solution Please check the connection between the servers, if the Presentation and Business Layer is deployed 
			 * seperately.
			 * 
			 * @procedure Please check the corresponding EJB availability
			 * 
			 * @verification Please check the corresponding EJB availability
			 */
			//LOGGER.fatal(ILogConstants.RMI_REMOTE_0001, ILogConstants.RMI_REMOTE_0001_MSG, returnException);
		}
		return returnException;
	}

}
