/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				: Logger  
 * Author					: JK32658
 * Program Name				: LogManager
 * Program Version			: 1.0
 * Program Description		: 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 *  JK32658   	06/08/2013     	  1.0        First Draft
 * ------------------------------------------------------------------------------------------
 *********************************************************************************************/

package com.mbusa.dpb.common.logger;

import com.mbusa.dpb.common.props.PropertyManagerFactory;


public abstract class LogManager {
	private static final String LOG_FACTORY_PROPERTY = "logger.factory.default";

	private static Class logManagerClass = null;

	private static LogManager configuredLogManager = null;

	static {
		try {
			logManagerClass = PropertyManagerFactory.getDefaultSystemPropertyManager().getPropertyAsClass(LOG_FACTORY_PROPERTY);
		} catch (RuntimeException e) {
		}
	}

	/**
	 * 
	 * 
	 */
	private static void createLogManager() {
		Class tempLogManagerClass = logManagerClass;
		// Use default LogManager i.e. Log4jManager
		if (tempLogManagerClass == null) {
			tempLogManagerClass = Log4jManager.class;
		}

		if (!LogManager.class.isAssignableFrom(tempLogManagerClass)) {
			throw new RuntimeException("The specified class " + tempLogManagerClass + " is not a valid LogManager.");
		}

		LogManager tempLogManager = null;
		try {
			tempLogManager = (LogManager) tempLogManagerClass.newInstance();
			tempLogManager.configure();
		} catch (Exception e) {
			throw new RuntimeException("Unable to instantiate configured LogManager", e);
		}

		synchronized (LogManager.class) {
			if (configuredLogManager != null) {
				throw new RuntimeException("LogManager class is already configured.");
			}
			configuredLogManager = tempLogManager;
		}
	}

	/**
	 * 
	 * @param logManagerClassName
	 */
	public static void setLogManagerClass(String logManagerClassName) {
		logManagerClass = loadClass(logManagerClassName);
	}

	/**
	 * 
	 * @param clazz
	 */
	public static void setLogManagerClass(Class clazz) {
		logManagerClass = clazz;
	}

	/**
	 * 
	 * @return
	 */
	public static Class getLogManagerClass() {
		return logManagerClass;
	}

	/**
	 * @param name
	 * @return
	 */
	public static Logger getLogger(String name) {
		if (configuredLogManager == null) {
			createLogManager();
		}
		return configuredLogManager.createLogger(name);
	}

	/**
	 * 
	 * TODO Method description
	 * 
	 * @param classObject
	 * @return
	 */
	public static Logger getLogger(Class classObject) {
		if (configuredLogManager == null) {
			createLogManager();
		}
		return getLogger(classObject.getName());
	}

	/**
	 * 
	 * TODO Method description
	 * 
	 * @param name
	 * @return
	 */
	protected abstract Logger createLogger(String name);

	/**
	 * 
	 * 
	 */
	protected abstract void configure();
	
	private static Class loadClass(String className) {
		if(className == null || "".equals(className)) {
    		throw new IllegalArgumentException("ClassName should not be null or empty.");
    	}
    	
    	Class clazz = null;
    	
    	// 
    	ClassLoader cl = ClassLoader.getSystemClassLoader();
    	
    	if(cl != null) {
    		try {
				clazz = cl.loadClass(className);
			} catch (ClassNotFoundException cnfe) {
			}
    	}
    	
		// Try to load The Logger Manager using Context ClassLoader
    	if(clazz == null) {
			if((cl = Thread.currentThread().getContextClassLoader()) != null) {
				try {
					clazz = cl.loadClass(className);
				} catch (ClassNotFoundException cnfe) {
				}
			}
    	}

		// Try to load with Fallback classloader
		if(clazz == null) {
			try {
				clazz = LogManager.class.getClass().getClassLoader().loadClass(className);
			} catch (ClassNotFoundException cnfe) {
				//throw new Exception("Could not found class " + clazz, cnfe);
			}
		}
		return clazz;
	}
}