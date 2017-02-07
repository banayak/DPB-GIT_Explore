/*********************************************************************************************
 * Project Name	: DPB
 * Module Name	: PropertyManager 
 * Program Name	: PropertyManagerFactory
 * Description	: This component manages the properties files of the DPB project.
 *
 * Created By	: 						Date: 				Version: 1.0
 * *******************************************************************************************
 * Modification Details:
 *
 * Name							Date				Purpose
 * -------------------------------------------------------------------------------------------
 *
 *
 ********************************************************************************************/
package com.mbusa.dpb.common.props;

import java.util.Hashtable;
import java.util.Properties;

import com.mbusa.dpb.common.logger.LogManager;
import com.mbusa.dpb.common.logger.Logger;


/**
 * This class is factory class for PropertyManager.
 * 
 */
public abstract class PropertyManagerFactory {

	public static final String PROPERTY_DEFAULT_PROPERTY_MANAGER_FACTORY = "propertymanager.factory.default";

	protected static PropertyManager DEFAULT_SYSTEM_PROPERTY_MANAGER = null;

	/**
	 * <p>
	 * Returns PropertyManager for System properties. The resulted
	 * PropertyManager object provides all properties which can access by
	 * System.getProperties().
	 * 
	 * <p>
	 * This method helps sub classes to get System properties with all
	 * PropertyManager functionalities.
	 * 
	 * @return
	 */
	public static PropertyManager getDefaultSystemPropertyManager() {
		if (DEFAULT_SYSTEM_PROPERTY_MANAGER != null) {
			return DEFAULT_SYSTEM_PROPERTY_MANAGER;
		}

		synchronized (PropertyManagerFactory.class) {
			// Checking one more time, other thread may created the object.
			PropertyManager defaultSystemPropertyManager = null;
			if (DEFAULT_SYSTEM_PROPERTY_MANAGER == null) {
				defaultSystemPropertyManager = new PropertyManager("system", System.getProperties(), null) {

					public Properties getAllProperties() {
						return properties;
					}

					public Object getProperty(String name) {
						return properties.get(name);
					}
				};
			}
			DEFAULT_SYSTEM_PROPERTY_MANAGER = defaultSystemPropertyManager;
		}
		return DEFAULT_SYSTEM_PROPERTY_MANAGER;
	}

	private static PropertyManagerFactory configuredFactory = null;

	/**
	 * 
	 * Returns the Configured PropertyManager 
	 * 
	 * @return
	 */
	static PropertyManagerFactory getConfiguredPropertyManagerInstance() {
		if (configuredFactory != null) {
			return configuredFactory;
		}
		Logger logger = LogManager.getLogger(PropertyManagerFactory.class);
		PropertyManager systemPropertyManager = PropertyManagerFactory.getDefaultSystemPropertyManager();

		Class pmfClass = systemPropertyManager.getPropertyAsClass(PROPERTY_DEFAULT_PROPERTY_MANAGER_FACTORY);

		if (pmfClass == null) {
			logger.info("No PropertyManagerFactory is configured.");
			return null;
		}

		// Validating configured PropertyManagerFactory.
		if (!PropertyManagerFactory.class.isAssignableFrom(pmfClass)) {
			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append("Invalid PropertyManagerFactory ")
				.append(pmfClass.getName())
				.append(" - PropertyManagerFactory should be subclass of ")
				.append(PropertyManagerFactory.class.getName());
			logger.warn(sBuffer.toString());
		}

		// Creating the instance of configured PropertyManagerFactory
		PropertyManagerFactory requestedFactory = null;
		try {
			requestedFactory = (PropertyManagerFactory) pmfClass.newInstance();
		} catch (Exception e) {
			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append("Unable to create instance of PropertyManagerFactory ");
			sBuffer.append(pmfClass.getName());
			logger.warn(sBuffer.toString(), e);
		}

		synchronized (PropertyManagerFactory.class) {
			if (configuredFactory == null) {
				configuredFactory = requestedFactory;
				logger.info("Using configured PropertyManagerFactory " + requestedFactory.getClass().getName());
			}
		}

		return configuredFactory;
	}

	/**
	 * Getter Method for configuredFactory
	 * 
	 * @return the configuredFactory
	 */
	public static PropertyManagerFactory getFactory() {
		return configuredFactory;
	}

	/**
	 * Setter Method for configuredFactory
	 * 
	 * @param configuredFactory
	 *            the configuredFactory to set
	 */
	static synchronized public void setFactory(PropertyManagerFactory configuredFactory) {
		if (configuredFactory != null) {
			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append("PropertyManagerFactory is already configured to ");
			sBuffer.append(configuredFactory);
			sBuffer.append(" - Can not be override with ");
			sBuffer.append(configuredFactory);
			LogManager.getLogger(PropertyManagerFactory.class).warn(sBuffer.toString());
		} else {
			PropertyManagerFactory.configuredFactory = configuredFactory;
		}
	}

	/*
	 * ============================================================================================
	 * IMPLEMENTING NOT STATIC METHODS
	 * ==============================================================================================
	 */
	protected Hashtable propertyManagersCache;

	/**
	 * 
	 * @param defaultPropertyManager
	 */
	protected PropertyManagerFactory() {
		this.propertyManagersCache = new Hashtable(5);
	}

	/**
	 * This method is factory method to create the PropertyManager objects.
	 * 
	 * <b> Validation: </b>
	 * <li> If the value of the parameter 'name' is null, it returns default
	 * PropertyManager. </li>
	 * <li> If the value of the parameter 'name' is empty, throws exception.
	 * </li>
	 * 
	 * First this method search the cache for requested PropertyManager. If not
	 * found it invokes the method createPropertyManager(String) and stores the
	 * created PropertyManager into the cache for future use.
	 * 
	 * @param name
	 * @return
	 */
	public PropertyManager getPropertyManager(String name) {
		Logger logger = LogManager.getLogger(PropertyManagerFactory.class);
		logger.enter(PropertyManagerFactory.class, "getPropertyManager(String)");

		if (name == null) {
			return getDefaultPropertyManager();
		}

		if ("".equalsIgnoreCase(name)) {
			//ServiceHelper.throwException(new ServiceException("Requested PropertyManager name should not be empty."));
		}

		PropertyManager requestedManager = (PropertyManager) propertyManagersCache.get(name);

		if (requestedManager == null) {
			if (logger.isDebugEnabled()) {
				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append("Creating PropertyManager ")
					.append(name)
					.append(" ...");
				logger.debug(sBuffer.toString());
			}

			// Create the PropertyManager
			PropertyManager newManager = createPropertyManager(name);
			newManager.initialize();

			synchronized (this) {
				// Check once again, other thread may has created the same
				// PropertyManager.
				requestedManager = (PropertyManager) propertyManagersCache.get(name);
				if (requestedManager == null) {
					propertyManagersCache.put(newManager.getName(), newManager);
					logger.info("PropertyManager " + name + " is created.");
					requestedManager = newManager;
				}
			}
		}

		logger.exit(PropertyManagerFactory.class, "getPropertyManager(String)");
		return requestedManager;
	}

	/**
	 * Returns the PropertyManager with System Properties.
	 * 
	 * @return
	 */
	public PropertyManager getSystemPropertyManager() {
		return PropertyManagerFactory.getDefaultSystemPropertyManager();
	}

	/**
	 * 
	 * This method returns the default PropertyManager of configured PropertyManagerFactory.
	 * 
	 * All sub-classes has to implement this method to return default PropertyManager.
	 * 
	 * The configuration of DefaultPropertyManager is left to sub-classes.
	 * 
	 * @return
	 */
	abstract public PropertyManager getDefaultPropertyManager();

	/**
	 * 
	 * Returns the parent of specified PropertyManager.
	 * 
	 * @param propManager
	 * @return
	 */
	abstract public PropertyManager getParent(PropertyManager propManager);

	/**
	 * 
	 * Returns the parent of specified PropertyManager.
	 * 
	 * @param name
	 * @return
	 */
	public PropertyManager getParent(String name) {
		return getParent(getPropertyManager(name));
	}

	/**
	 * Creates the PropertyManager for specified hierarchy.
	 * 
	 * @param name
	 * @return
	 */
	abstract protected PropertyManager createPropertyManager(String name);

}
