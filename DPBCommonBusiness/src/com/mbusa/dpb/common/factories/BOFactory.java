package com.mbusa.dpb.common.factories;

import java.lang.reflect.Constructor;

import com.mbusa.dpb.common.exceptions.ServiceException;
import com.mbusa.dpb.common.props.PropertyManager;

/**
 * This is factory class for all DPB BusinessObjects. 
 * 
 * This class fetches the mapping between interfaces and implementations from the DPB 
 * property file.
 *
 * Usage: 
 * <p>
 * To get the singleton instance of implementation class of speicified interface.
 * <code>
 * BOFactory boFactory = BOFactory.getInstance(); 
 * </code>
 * or 
 */
public class BOFactory extends AbstractFactory  {

	private static final PropertyManager PROP_MNGR = PropertyManager.getPropertyManager();
	    
	private static BOFactory INSTANCE = null;

	    /**
	     * Constructor of class
	     * This should call super class constructor to initialize.
	     */
	    private BOFactory() {
	    	super();
	    }

	    /**
	     * Factory method to return 'Singleton' instance of object.
	     *
	     * @return - Singleton instance of this class
	     */
	    public static BOFactory getInstance() {
	    	if(INSTANCE == null) {
	    		synchronized (BOFactory.class) {
	    			// Needs double checking
	    			if(INSTANCE == null) {
	    				INSTANCE = new BOFactory();
	    			}
				}
	        }
			return INSTANCE;
	    }

	    /**
	     * Implementation of {@link AbstractFactory#getImplementation(Class)}
	     */
	    public Object getImplementation(Class interfaceClass){
	        String implClassName = PROP_MNGR.getPropertyAsString(interfaceClass.getName());
	       
	        
	        if(implClassName == null) {
	        	/*
				 * @description The implementation class for specified interface is not defined.
				 * @solution Please declare the mapping for implementation class of specified interface in application
				 * default properties file.
				 * @procedure Open the application default properties file (For external application external.properties, For internal application internal.properties) 
				 * and check for the implementation declaration of specified interface.
				 * @verification Verify the application default properties file for mapping of specified interface.
				 */
			
	        	throw new ServiceException("No Implementation found for the interface " + interfaceClass.getName());
	        }

	        Object implClass = getInstanceOfClass(implClassName);
	        
	        if(!interfaceClass.isAssignableFrom(implClass.getClass())) {
	         	/*
				 * @description The mapped implementation class is not implementation of speicified interface.
				 * @solution Please map correct implementation class for specified interface
				 * @procedure All implementation classes of specified interface should implement the same interface.
				 * @verification Make sure that implementation class of speicfied interface is implementing the same interface.
				 */
			
				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append("The class ");
				sBuffer.append(implClass.getClass().getName());
				sBuffer.append(" is not implementing the interface ");
				sBuffer.append(interfaceClass.getName());
	        	throw new ServiceException(sBuffer.toString());
	        }
	      
	        return implClass;
	 }
	    
	    /**
	     *This method crates the instance of speicified class object.
	     *
	     *The class object should implement public default constructor.
	     *
	     * @param interfaceName 
	     *
	     * @return 
	     */
	    private Object getInstanceOfClass(String implClass) {
	        Class clazz = null;
	        Object returnObject = null;

	        try {
	            clazz = Class.forName(implClass);
	        } catch (ClassNotFoundException cnfe) {
	        	/*
				 * @description Could not load the specified class.
				 * @solution Please make sure that specifed class is in application package and application classpath.
				 * @procedure Put the class applicatin deployment unit and application classpath
				 * @verification Please check the application deployment unit for specified class.
				 */
			
	            throw new ServiceException("Could not load the class " + implClass, cnfe);
	        }

	        if (clazz != null) {
	            Constructor defaultConstructor = null;

	            try {
	                defaultConstructor = clazz.getConstructor(null);
	                returnObject = defaultConstructor.newInstance(null);
	            } catch (NoSuchMethodException nsme) {
	            	/*
					 * @description The implementation class do not implemented the public default constructor.
					 * @solution Plese implement public default constructor for the specified class
					 * @procedure Please contact the application development team with proper log.
					 * @verification Please make sure that the implementation class has public default constructor.
					 */
	            	
	                throw new ServiceException("No public constructor for the class " + implClass, nsme);
	            } catch (Exception e) {
	            	/*
					 * @description Could not create the instance of specified class
					 * @solution Please put requried libraries in classpath, if the implementation class needs. If the exeption 
					 * is defferent please contact the development team.
					 * @procedure Please contact the application development team with proper log.
					 * @verification Please make sure that all libraries required by the implementation class are available
					 */
	           
	                throw new ServiceException("Could not create the instance of class " + implClass, e);
	            }
	        }
	        return returnObject;
	    }

}
