/*********************************************************************************************
 * Project Name				: DPB   
 * Module Name				: Logger  
 * Author					: JK32658
 * Program Name				: Logger
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

/**
 * TODO Discription of Type
 *
 * @version 
 */
public interface Logger {

    /**
     * 
     * TODO Method description
     * 
     * @return
     */
    String getName();

    /**
     * TODO Method description
     *
     * @return boolean
     */
    boolean isEnterEnabled();

    /**
     * 
     * TODO Method description
     * 
     * @param className
     * @param methodName
     */
    void enter(String className, String methodName);

    /**
     * 
     * TODO Method description
     * 
     * @param currentClass
     * @param methodName
     */
    void enter(Class currentClass, String methodName);

    /**
     * 
     * TODO Method description
     * 
     * @return
     */
    boolean isExitEnabled();

    /**
     * 
     * TODO Method description
     * 
     * @param className
     * @param methodName
     */
    void exit(String className, String methodName);

    /**
     * 
     * TODO Method description
     * 
     * @param currentClass
     * @param methodName
     */
    void exit(Class currentClass, String methodName);

    /**
     * 
     * TODO Method description
     * 
     * @return
     */
    boolean isDebugEnabled();

    /**
     * 
     * TODO Method description
     * 
     * @param message
     */
    void debug(Object message);

    /**
     * 
     * TODO Method description
     * 
     * @param message
     * @param t
     */
    void debug(Object message, Throwable t);

    /**
     * 
     * TODO Method description
     *  
     */
    void debug();

    /**
     * 
     * TODO Method description
     * 
     * @param t
     */
    void debug(Throwable t);

    /**
     * 
     * TODO Method description
     * 
     * @return
     */
    boolean isInfoEnabled();

    /**
     * 
     * TODO Method description
     * 
     * @param message
     */
    void info(Object message);

    /**
     * 
     * TODO Method description
     * 
     * @param message
     * @param t
     */
    void info(Object message, Throwable t);

    /**
     * 
     * TODO Method description
     *  
     */
    void info();

    /**
     * 
     * TODO Method description
     * 
     * @param t
     */
    void info(Throwable t);

    /**
     * 
     * TODO Method description
     * 
     * @return
     */
    boolean isWarnEnabled();

    /**
     * 
     * TODO Method description
     * 
     * @param message
     */
    void warn(Object message);

    /**
     * 
     * TODO Method description
     * 
     * @param message
     * @param t
     */
    void warn(Object message, Throwable t);

    /**
     * 
     * TODO Method description
     *  
     */
    void warn();

    /**
     * 
     * TODO Method description
     * 
     * @param t
     */
    void warn(Throwable t);

    /**
     * 
     * TODO Method description
     * 
     * @return
     */
    boolean isErrorEnabled();

    /**
     * 
     * TODO Method description
     * 
     * @param message
     */
    void error(Object message);

    /**
     * 
     * TODO Method description
     * 
     * @param message
     * @param t
     */
    void error(Object message, Throwable t);

    /**
     * 
     * TODO Method description
     *  
     */
    void error();

    /**
     * 
     * TODO Method description
     * 
     * @param t
     */
    void error(Throwable t);

    /**
     * 
     * TODO Method description
     * 
     * @return
     */
    boolean isFatalEnabled();

    /**
     * 
     * TODO Method description
     * 
     * @param message
     */
    void fatal(Object message);

    /**
     * 
     * TODO Method description
     * 
     * @param message
     * @param t
     */
    void fatal(Object message, Throwable t);

    /**
     * 
     * TODO Method description
     *  
     */
    void fatal();

    /**
     * 
     * TODO Method description
     * 
     * @param t
     */
    void fatal(Throwable t);

    /**
     * 
     * TODO Method description
     * 
     * @param message
     * @return
     */
    Logger append(Object message);
    
    /**
     * 
     * @return
     */
    ResourceBundle getResourceBundle();
    
    /**
     * 
     * @return
     */
    Object getOriginalLogger();
}