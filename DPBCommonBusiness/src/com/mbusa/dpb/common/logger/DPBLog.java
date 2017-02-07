/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				: Logger  
 * Author					: JK32658
 * Program Name				: Log4jManager
 * Program Version			: 1.0
 * Program Description		: 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * JK32658   	06/08/2013     	  1.0        First Draft
 * ------------------------------------------------------------------------------------------
 *********************************************************************************************/
package com.mbusa.dpb.common.logger;

import java.util.ResourceBundle;


public class DPBLog implements Logger {
	protected Logger logger = null;
	
	/**
	 * 
	 * @param cls
	 * @return
	 */
	public static DPBLog getInstance(Class cls) {
		Logger logger = LogManager.getLogger(cls);
		return new DPBLog(logger);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static DPBLog getInstance(String name) {
		Logger logger = LogManager.getLogger(name);
		return new DPBLog(logger);
	}

	/**
	 * 
	 * @param logger
	 */
	protected DPBLog(Logger logger) {
		this.logger = logger;
	}

	/**
	 * 
	 * @param assertion
	 * @param msg
	 */
	public void assertLog(boolean assertion, String msg) {
		if (!assertion)
			logger.error(msg);
	}
	public Logger append(Object message) {
		return logger.append(message);
	}
	public void debug(Object message) {
		logger.debug(message);
	}
	public void debug(Object message, Throwable t) {
		logger.debug(message, t);
	}
	public void debug() {
		logger.debug();
	}
	public void debug(Throwable t) {
		logger.debug(t);
	}
	public void enter(String className, String methodName) {
		logger.enter(className, methodName);
	}
	public void enter(Class currentClass, String methodName) {
		logger.enter(currentClass, methodName);
	}
	public void error(Object message) {
		logger.error(message);
	}
	public void error(Object message, Throwable t) {
		logger.error(message, t);
	}
	public void error() {
		logger.error();
	}
	public void error(Throwable t) {
		logger.error(t);
	}
	public void exit(String className, String methodName) {
		logger.exit(className, methodName);
	}
	public void exit(Class currentClass, String methodName) {
		logger.exit(currentClass, methodName);
	}
	public void fatal(Object message) {
		logger.fatal(message);
	}
	public void fatal(Object message, Throwable t) {
		logger.fatal(message, t);
	}
	public void fatal() {
		logger.fatal();
	}
	public void fatal(Throwable t) {
		logger.fatal(t);
	}
	public String getName() {
		return logger.getName();
	}
	public Object getOriginalLogger() {
		return logger.getOriginalLogger();
	}
	public ResourceBundle getResourceBundle() {
		return logger.getResourceBundle();
	}
	public void info(Object message) {
		logger.info(message);
	}
	public void info(Object message, Throwable t) {
		logger.info(message, t);
	}
	public void info() {
		logger.info();
	}
	public void info(Throwable t) {
		logger.info(t);
	}
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
	public boolean isEnterEnabled() {
		return logger.isEnterEnabled();
	}
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}
	public boolean isExitEnabled() {
		return logger.isExitEnabled();
	}
	public boolean isFatalEnabled() {
		return logger.isFatalEnabled();
	}
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}
	public void warn(Object message) {
		logger.warn(message);
	}
	public void warn(Object message, Throwable t) {
		logger.warn(message, t);
	}
	public void warn() {
		logger.warn();
	}
	public void warn(Throwable t) {
		logger.warn(t);
	}
}
