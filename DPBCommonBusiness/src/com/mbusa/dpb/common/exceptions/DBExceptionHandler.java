package com.mbusa.dpb.common.exceptions;

import java.sql.BatchUpdateException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.sql.SQLTransientException;



public class DBExceptionHandler extends DPBExceptionHandler {
	/**Declaratin for Loggers*/
	//private DPBLog LOGGER = DPBLog.getApplicationLogger(DBExceptionHandler.class);

	public Exception handleException(Exception exception) {
		Exception returnException = null;
		
		
		if(exception instanceof BatchUpdateException) {
			returnException = new PersistanceException("Exception while creating Database Connection", exception);
			//LOGGER.fatal(ILogConstants.EH_DBCON_0001, ILogConstants.EH_DBCON_0001_MSG, returnException);
			return returnException;
		}
		
		if(exception instanceof SQLDataException) {
			returnException = new PersistanceException("Data Exception", exception);
			//LOGGER.severe(ILogConstants.EH_DATA_0001, ILogConstants.EH_DATA_0001_MSG, returnException);
			return returnException;
		}
		
		if(exception instanceof SQLIntegrityConstraintViolationException) {
			returnException = new PersistanceException("Error in constraint violation", exception);
			//LOGGER.fatal(ILogConstants.EH_DATA_0002, ILogConstants.EH_DATA_0002_MSG, returnException);
			return returnException;
		}
		
		if(exception instanceof SQLSyntaxErrorException) {
			returnException = new PersistanceException("Syntax error exception", exception);
			//LOGGER.fatal(ILogConstants.EH_DATA_0003, ILogConstants.EH_DATA_0003_MSG, returnException);
			return returnException;
		}
		
		if(exception instanceof SQLTransientException) {
			returnException = new PersistanceException("SQL transient exception", exception);
			//LOGGER.fatal(ILogConstants.EH_DATA_0003, ILogConstants.EH_DATA_0003_MSG, returnException);
			return returnException;
		}
		
		if(exception instanceof SQLException) {
			returnException = new PersistanceException("SQL exception", exception);
			//LOGGER.fatal(ILogConstants.EH_DATA_0003, ILogConstants.EH_DATA_0003_MSG, returnException);
			return returnException;
		}
		
		returnException = new PersistanceException("Database Exception", exception);
		//LOGGER.fatal(ILogConstants.EH_HIB_0001, ILogConstants.EH_HIB_0001_MSG, returnException);
		return returnException;
	}

}
