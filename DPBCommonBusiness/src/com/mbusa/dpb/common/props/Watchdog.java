/*********************************************************************************************
 * Project Name	: DPB
 * Module Name	: TODO
 * Program Name	: 
 * Description	: TODO
 *
 * Created By	: 		Date: 			Version: 1.0
 * *******************************************************************************************
 * Modification Details:
 *
 * Name							Date				Purpose
 * -------------------------------------------------------------------------------------------
 *
 *
 ********************************************************************************************/
package com.mbusa.dpb.common.props;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.mbusa.dpb.common.logger.LogManager;
import com.mbusa.dpb.common.logger.Logger;


public abstract class Watchdog {

	public static final String WATCHDOG_CLASS_PROPERTY = "watchdog.class";

	public static final Class DEFAULT_WATCHDOG = SimpleWatchdog.class;

	public static final Logger LOGGER = LogManager.getLogger(Watchdog.class);

	private static Watchdog singleton = null;

	/**
	 * 
	 * @return
	 */
	public static Watchdog getInstance() {
		if (singleton != null) {
			return singleton;
		}

		Class watchdogClass = null;
		try {
			watchdogClass = PropertyManagerFactory.getDefaultSystemPropertyManager().getPropertyAsClass(
					WATCHDOG_CLASS_PROPERTY);
		} catch (Exception e) {
			LOGGER.warn("Exception in loading class configured to property " + WATCHDOG_CLASS_PROPERTY, e);
		}
		Watchdog watchdog = null;
		try {
			if (watchdogClass != null) {
				watchdog = (Watchdog) watchdogClass.newInstance();
			}
		} catch (Exception e) {
			LOGGER.warn("Exception in creating the watchdog instance.", e);
		}

		if (watchdog == null) {
			LOGGER.info("Using default watchdog " + SimpleWatchdog.class.getName());
			watchdog = new SimpleWatchdog();
		}

		// Initialize the watchdog
		watchdog.initialize();

		synchronized (Watchdog.class) {
			if (singleton == null) {
				singleton = watchdog;
			}
		}

		return singleton;
	}

	protected Map resources;

	protected Map resourceToListeners;

	/**
	 * 
	 * 
	 */
	public Watchdog() {
		resources = new HashMap(5);
		resourceToListeners = new HashMap(5);
	}

	/**
	 * 
	 * @param resource
	 */
	protected void registerResource(Resource resource) {
		String resourceName = resource.getName();
		if (resourceName == null || "".equals(resourceName)) {
			throw new IllegalArgumentException("Resource name should not be null or empty.");
		}
		
		if(resources.get(resource.getName()) != null) {
			return;
		}

		// Not allowed override existing resource
		synchronized (resources) {
			if (resources.get(resourceName) == null) {
				resources.put(resourceName, resource);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Resource " + resource + " is registered with watchdog.");
				}
			}
		}
	}

	/**
	 * This method provides the following functionalities.
	 * 
	 * <li> Registers resource to Watchdog
	 * <li> Maps lister to specified resource
	 * 
	 * It adds resource to resource list, if specified resource is not
	 * registered with Watchdog. It maps specified listerner to the existing
	 * resource.
	 * 
	 * @param resource -
	 *            Resource to register
	 * @param listener
	 *            to map with the resource
	 */
	public void addResource(Resource resource, ResourceObserver listener) {
		Collection listeners = new LinkedList();
		listeners.add(listener);
		addResource(resource, listeners);
	}

	/**
	 * This method provides the following functionalities.
	 * 
	 * <li> Registers resource to Watchdog
	 * <li> Maps listers to specified resource
	 * 
	 * It adds resource to resource list, if specified resource is not
	 * registered with Watchdog. It maps specified listerners to the existing
	 * resource.
	 * 
	 * @param resource -
	 *            Resource to register
	 * @param listeners -
	 */
	public void addResource(Resource resource, Collection listeners) {
		if (resource == null) {
			throw new IllegalArgumentException("Resource should not be null.");
		}

		if (listeners == null || listeners.isEmpty()) {
			return;
		}

		validateListeners(listeners);

		// Add resource if resource is not already added.
		registerResource(resource);

		synchronized (resourceToListeners) {
			List existingListeners = (List) resourceToListeners.get(resource);

			if (existingListeners == null) {
				existingListeners = new LinkedList();
				resourceToListeners.put(resource, existingListeners);
			}

			ResourceObserver listenerToAdd = null;
			for (Iterator itr = listeners.iterator(); itr.hasNext();) {
				listenerToAdd = (ResourceObserver) itr.next();
				existingListeners.add(listenerToAdd);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Listenr " + listenerToAdd.getClass().getName() + " is registered for the resource "
							+ resource);
				}
			}
		}

		// Make sure that watchdog is running.
		if (!singleton.isRunnging()) {
			singleton.start();
		}
	}

	/**
	 * 
	 * @param resourceName
	 * @param listener
	 */
	public void addResource(String resourceName, ResourceObserver listener) throws Exception {
		if (resourceName == null || "".equals(resourceName)) {
			throw (new Exception("ResourceName should not be empty or null"));
		}

		Resource resource = (Resource) resources.get(resourceName);
		if (resource == null) {
			throw (new Exception("Resource " + resourceName
					+ " is not registered."));
		}

		List listeners = new LinkedList();
		listeners.add(listener);
		addResource(resource, listeners);
	}

	/**
	 * 
	 * @param resourceName
	 * @param listeners
	 */
	public void addResource(String resourceName, Collection listeners) throws Exception {
		Resource resource = getResource(resourceName);
		if (resource == null) {
			throw (new Exception("Resource " + resourceName
					+ " is not registered."));
		}

		addResource(resource, listeners);
	}

	/**
	 * Validates every listener is implementing the ResourceObserver interface.
	 * If not this method throws IllegalArgumentException.
	 * 
	 * @param listeners
	 */
	protected void validateListeners(Collection listeners) {
		Object eachListener = null;

		for (Iterator itr = listeners.iterator(); itr.hasNext();) {
			eachListener = itr.next();
			if (!ResourceObserver.class.isAssignableFrom(eachListener.getClass())) {
				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append("Invalid listener ");
				sBuffer.append(eachListener);
				sBuffer.append(" for the watchdog, ignoring it.");
				LOGGER.warn(sBuffer.toString());
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public Collection listResources() {
		HashMap result = (HashMap) ((HashMap) resources).clone();
		return result.values();
	}

	/**
	 * 
	 * @param resourceName
	 * @return
	 */
	public Resource getResource(String resourceName) {
		if (resourceName == null || "".equals(resourceName)) {
			throw new IllegalArgumentException("ResourceName should not be empty or null");
		}
		return (Resource) resources.get(resourceName);
	}

	/**
	 * 
	 * @param resourceName
	 * @return
	 */
	public Collection listListeners(String resourceName) {
		Resource resource = getResource(resourceName);
		if (resource == null) {
			return null;
		}
		LinkedList listeners = (LinkedList) resourceToListeners.get(resource);
		return (Collection) listeners.clone();
	}

	/**
	 * 
	 * @param resource
	 */
	protected void invokeListeners(Resource resource, Object info) {
		Collection listeners = listListeners(resource.getName());

		if (listeners == null || listeners.isEmpty()) {
			return;
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Invoking the listeners registered with the resource " + resource);
		}

		ResourceObserver eachListener = null;
		for (Iterator itr = listeners.iterator(); itr.hasNext();) {
			eachListener = (ResourceObserver) itr.next();
			try {
				eachListener.resourceChanged(resource, info);
			} catch (Exception e) {
				LOGGER.warn("Exception in invoking the listener " + eachListener, e);
			}
		}
	}

	/**
	 * 
	 * 
	 */
	protected abstract void initialize();

	/**
	 * 
	 * 
	 */
	protected abstract void start();

	/**
	 * 
	 * @return
	 */
	protected abstract boolean isRunnging();

	/**
	 * 
	 * 
	 */
	public abstract void shutdown();
}
