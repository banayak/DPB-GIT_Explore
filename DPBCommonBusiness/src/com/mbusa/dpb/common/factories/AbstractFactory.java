package com.mbusa.dpb.common.factories;

import java.util.HashMap;
import java.util.Map;

import com.mbusa.dpb.common.exceptions.ServiceException;

public abstract class AbstractFactory  {	
	
	private Map instanceCache = null;

    /**
     * Protected Constructor, insitializes the instance cache HashMap of factory.
     */
    protected AbstractFactory() {
        instanceCache = new HashMap(10);
    }

    /**
     *
     * This method returns the implementation class object of specified interface.
     * 
     * @param 	interfaceClass : Interface class
     *
     * @return  Object : Returns the implementation object
     * 			
     */
    public Object getObject(Class interfaceClass) {
    	Object obj = null;
    	if(interfaceClass != null) {
    		obj = getImplementationObject(interfaceClass, true);
    	}
        return obj;
    }

    /**
     * This method returns the object of class which implements specified interface
     * on the basis of 'singleton' parameter.
     * 
     * if 'singleton' is true, this method returns the object from cache.
     * In case of false, this method creates new instance of implementation class and returns.
     *
     * @param  	interfaceClass - The {@link java.lang.Class} object of interface.
     * 
     * @param   singleton 
     *
     * @return 	Object - The object of the implementation class.
     */
    public Object getObject(Class interfaceClass, boolean singleton) {
    	Object obj = null;
    	if(interfaceClass != null) {
    		obj = getImplementationObject(interfaceClass, singleton);
    	}
        return obj;
    }

    /**
     * This private method implements the total behaviour of this class.
     * 
     * @param 	interfaceName
     * 
     * @param   singleton
     * 
     * @return	Object - The implementation object of the interface.
     */
    private Object getImplementationObject(Class interfaceClass, boolean singleton) {
    	 Object returnObject = null;
	    	if (interfaceClass != null) {	
		        if (singleton) {
		        	
		        	
		        	synchronized(AbstractFactory.this) {
		        		returnObject = instanceCache.get(interfaceClass.getName());
		        		 if (returnObject == null) {
		        			returnObject = getImplementation(interfaceClass);
		            		instanceCache.put(interfaceClass.getName(), returnObject);
		        		 }
		        	}
		        } else {
		        	
		            returnObject = getImplementation(interfaceClass);
		        }
	    	}
        return returnObject;
    }

    /**
     * This method returns the instance of Imlementation class of speicified interface
     * 
     *  All subclasses of this class should override this method. Mapping between interfaces 
     *  and implemetations will depends on subclasses.
     *
     *	If subclasses not able to find or create the instace of implementation class, those should 
     *	throw {@link ServiceException}. Because it is Runtime Exception API could not force.
     *
     * @param   interfaceClass
     *
     * @return  Implementation class
     */
    protected abstract Object getImplementation(Class interfaceClass);
}
