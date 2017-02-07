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
 * 	   		Jul 	, 	     	  1.0        First Draft
 * ------------------------------------------------------------------------------------------
 *********************************************************************************************/

package com.mbusa.dpb.common.logger;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;

import com.mbusa.dpb.common.props.PropertyManagerFactory;

public class Log4jManager extends LogManager implements Logger {
	
	static final public String PROP_CONFIGURATION_FILE = "log4j.configuration.file";
	
	static final public String DEFAULT_CONFIGURATION_FILE = "properties/dpb-log4j.properties";

    protected org.apache.log4j.Logger logger = null;

    private static ThreadLocal threadMessages = new ThreadLocal() {
        protected Object initialValue() {
            return new StringBuffer();
        }
    };
    
    /**
     * Default Constructor incase configuration file in not mentioned
     *
     */
    public Log4jManager() {
    }
    
    /**
     * 
     * @param logger
     */
    protected Log4jManager(org.apache.log4j.Logger logger) {
        this.logger = logger;
    }
    
  
    protected Logger createLogger(String name) {
        return new Log4jManager(org.apache.log4j.Logger.getLogger(name));
    }

 
   
    public String getName() {
        return logger.getName();
    }

 
  
    public boolean isEnterEnabled() {
        return logger.isDebugEnabled();
    }

 
    public void enter(String className, String methodName) {
        StringBuffer sb = new StringBuffer();
        sb.append(className);
        sb.append("#");
        sb.append(methodName);
        sb.append(" Entered");
        logger.debug(sb.toString());

    }

    
    public void enter(Class currentClass, String methodName) {
        StringBuffer sb = new StringBuffer();
        sb.append(currentClass.getName());
        sb.append("#");
        sb.append(methodName);
        sb.append(" Entered");
        logger.debug(sb.toString());
    }

    
    public boolean isExitEnabled() {
        return logger.isDebugEnabled();
    }

    public void exit(String className, String methodName) {
        StringBuffer sb = new StringBuffer();
        sb.append(className);
        sb.append("#");
        sb.append(methodName);
        sb.append(" Exit");
        logger.debug(sb.toString());
    }

   
    public void exit(Class currentClass, String methodName) {
        StringBuffer sb = new StringBuffer();
        sb.append(currentClass.getName());
        sb.append("#");
        sb.append(methodName);
        sb.append(" Exit");
        logger.debug(sb.toString());
    }

   
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

   
    public void debug(Object message) {
        logger.debug(message);
    }

    
    public void debug(Object message, Throwable t) {
        logger.debug(message, t);
    }

   
    public void debug() {
        logger.debug(threadMessages.get().toString());
        threadMessages.set(new StringBuffer());
    }

   
    public void debug(Throwable t) {
        logger.debug(threadMessages.get().toString(), t);
        threadMessages.set(new StringBuffer());
    }

   
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public void info(Object message) {
        logger.info(message);
    }

   
    public void info(Object message, Throwable t) {
        logger.info(message, t);
    }

   
    public void info() {
        logger.info(threadMessages.get().toString());
        threadMessages.set(new StringBuffer());

    }

  
    public void info(Throwable t) {
        logger.info(threadMessages.get().toString(), t);
        threadMessages.set(new StringBuffer());
    }

    
    public boolean isWarnEnabled() {
        return logger.isEnabledFor(Level.INFO);
    }

   
    public void warn(Object message) {
        logger.warn(message);
    }

   
    public void warn(Object message, Throwable t) {
        logger.warn(message, t);
    }

   
    public void warn() {
        logger.warn(threadMessages.get().toString());
        threadMessages.set(new StringBuffer());
    }

   
    public void warn(Throwable t) {
        logger.warn(threadMessages.get().toString(), t);
        threadMessages.set(new StringBuffer());
    }

   
    public boolean isErrorEnabled() {
        return logger.isEnabledFor(Level.ERROR);
    }

   
    public void error(Object message) {
        logger.error(message);
    }

   
    public void error(Object message, Throwable t) {
        logger.error(message, t);
    }

    
    public void error() {
        logger.error(threadMessages.get().toString());
        threadMessages.set(new StringBuffer());
    }

   
    public void error(Throwable t) {
        logger.error(threadMessages.get().toString(), t);
        threadMessages.set(new StringBuffer());
    }

   
    public boolean isFatalEnabled() {
        return logger.isEnabledFor(Level.FATAL);
    }

    public void fatal(Object message) {
        logger.fatal(message);
    }

   
    public void fatal(Object message, Throwable t) {
        logger.fatal(message, t);
    }

    public void fatal() {
        logger.fatal(threadMessages.get().toString());
        threadMessages.set(new StringBuffer());
    }

   
    public void fatal(Throwable t) {
        logger.fatal(threadMessages.get().toString(), t);
        threadMessages.set(new StringBuffer());
    }

   
    public Logger append(Object message) {
        StringBuffer sb = (StringBuffer) threadMessages.get();
        sb.append(message);
        return this;
    }
  
    public ResourceBundle getResourceBundle() {
    	return this.logger.getResourceBundle();
    }

   
	protected void configure() { 
		String configurationFile = PropertyManagerFactory.getDefaultSystemPropertyManager().getPropertyAsString(PROP_CONFIGURATION_FILE, DEFAULT_CONFIGURATION_FILE);
    	URL configFile = null; 
    	ClassLoader cloader = Log4jManager.class.getClassLoader();
    	// Trying with Log4jManager class loader.
    	if(cloader != null) {
    		configFile = cloader.getResource(configurationFile);
    	}
    	// Trying with ContextClassLoader
    	if(configFile == null && (cloader = Thread.currentThread().getContextClassLoader()) != null) {
    		configFile = cloader.getResource(configurationFile);
    	}
    	// Trying with SystemClassLoader
    	if(configFile == null) {
    		configFile = ClassLoader.getSystemResource(configurationFile);
    	}
    	try {
			PropertyConfigurator.configure(configFile);
		} catch (RuntimeException re) {
			new RuntimeException("Exception while configuring the log4j.", re);
		}
	}

	/**
	 * 
	 */
	public Object getOriginalLogger() {
		return this.logger;
	}
}