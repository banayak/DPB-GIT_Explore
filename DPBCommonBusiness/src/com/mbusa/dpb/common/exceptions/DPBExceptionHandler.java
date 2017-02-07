package com.mbusa.dpb.common.exceptions;

import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.ejb.AccessLocalException;


/**
 * This class provides the functionality of handling different kinds of exceptions 
 * in DPB application.
 *
 */
public abstract class DPBExceptionHandler {
	
	static final private DPBExceptionHandler GEN_EXCP_HANDLER = new GenericExceptionHandler();
	
	static final private DPBExceptionHandler DB_EXCP_HANDLER = new DBExceptionHandler();
	
	static final private DPBExceptionHandler REMOTE_EXCP_HANDLER = new RemoteExceptionHandler();
	
	/**
	 * Returns the Singleton instance of the class.
	 * 
	 * @return
	 */
	public static DPBExceptionHandler getInstance() {
		return GEN_EXCP_HANDLER;
	}
	
	/**
	 * Returns the Singleton instance of the class.
	 * 
	 * @return
	 */
	public static DPBExceptionHandler getInstance(Class expType) {
		DPBExceptionHandler resultHandler = null;
		if(expType == DBExceptionHandler.class) {
			resultHandler = DB_EXCP_HANDLER;
		} else if(expType == RemoteExceptionHandler.class) {
			resultHandler = REMOTE_EXCP_HANDLER;
		} else {
			resultHandler = GEN_EXCP_HANDLER;
		}
		return resultHandler;
	}
	
	/**
	 * Constructor of the class.
	 *
	 */
	protected DPBExceptionHandler() {
	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	public String getExceptionKey(Exception e) {
		String key = "Undefined";
		if(BaseException.class.isAssignableFrom(e.getClass())) {
			key = ((BaseException) e).getMessageKey();
		} else if(BaseRuntimeException.class.isAssignableFrom(e.getClass())) {
			key = ((BaseRuntimeException) e).getMessageKey();
		}
		return key;
	}
	
	/**
	 * This method handles all Database Exceptions.
	 * 
	 * @param 	hexc :
	 * 			the exception to handle.
	 * 
	 * @throws 	PersistanceException 
	 * 			throws the persistance exception.
	 */
	public void handleDatabaseException(SQLException sqle) throws PersistanceException {
		PersistanceException pexc = (PersistanceException) DB_EXCP_HANDLER.handleException(sqle);
		throw pexc;
	}
	
	
	/**
	 * This method Handles {@link java.rmi.RemoteException}
	 * 
	 * @param 	rexc :
	 * 			the exception to handle.
	 * 
	 * @throws 	TechnicalException 
	 * 			throws the technical exception.
	 */
	public void handleRemoteException(RemoteException rexc) throws TechnicalException {
		TechnicalException texc = (TechnicalException) REMOTE_EXCP_HANDLER.handleException(rexc);
		throw texc;
	}
	
	/**
	 * This method handles all kinds of exceptions in DPB Application.
	 * 
	 * Using corresponding ExceptionType methods are recommended.
	 * 
	 * @param 	exc :
	 * 			the exception to handle.
	 * 
	 * @throws 	BusinessException 
	 * 			throws the business exception.
	 */
	abstract public Exception handleException(Exception exc);
	
	
	
	/**
	 * This method Handles BussinessException , notify container to rollback the transaction and 
	 * re-throw the exception
	 * 
	 * @param bexp
	 * @param mySessionCtx
	 * @throws BusinessException
	 */
	public static void performRollback(BusinessException e,javax.ejb.SessionContext mySessionCtx) throws BusinessException {
		if(mySessionCtx!=null){
			 mySessionCtx.setRollbackOnly();
		 }
		throw e;
	}
	
	
	/**
	 * This method Handles BussinessException , notify container to rollback the transaction and 
	 * re-throw the exception
	 * 
	 * @param bexp
	 * @param mySessionCtx
	 * @throws BusinessException
	 */
	public static void performRollback(TechnicalException e,javax.ejb.SessionContext mySessionCtx) throws TechnicalException {
		if(mySessionCtx!=null){
			 mySessionCtx.setRollbackOnly();
		 }
		throw e;
	}
	
	/**
	 * This method Handles BussinessException , notify container to rollback the transaction and 
	 * re-throw the exception
	 * 
	 * @param bexp
	 * @param mySessionCtx
	 * @throws BusinessException
	 */
	public static void performRollback(Exception e,javax.ejb.SessionContext mySessionCtx) throws Exception {
		if(mySessionCtx!=null){
			 mySessionCtx.setRollbackOnly();
		 }
		throw e;
	}
	
	public static void handleAccessException(AccessLocalException e)throws TechnicalException{
		/*
		 * @description User try to call the function for which he is not authorized 
		 * @solution User is not allowed for this function as 
		 * User not have required role to call this EJB is.so if user need this ,role should be change.
		 */
		throw e;
		
	}

}
