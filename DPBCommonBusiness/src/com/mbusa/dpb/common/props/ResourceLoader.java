/*********************************************************************************************
 * Project Name	: DPB
 * Module Name	: TODO
 * Program Name	: ResourceLoader
 * Description	: TODO
 *
 * Created By	: 						Date: 			Version: 1.0
 * *******************************************************************************************
 * Modification Details:
 *
 * Name							Date				Purpose
 * -------------------------------------------------------------------------------------------
 *
 *
 ********************************************************************************************/
package com.mbusa.dpb.common.props;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.mbusa.dpb.common.logger.LogManager;
import com.mbusa.dpb.common.logger.Logger;


/**
 * @author 
 * 
 */
public class ResourceLoader {

	static private Logger LOGGER = LogManager.getLogger(ResourceLoader.class);

	static private ResourceLoader singleton = null;

	/**
	 * 
	 * @return
	 */
	static public ResourceLoader getInstance() {
		if (singleton != null) {
			return singleton;
		}

		synchronized (ResourceLoader.class) {
			if (singleton == null) {
				singleton = new ResourceLoader();
			}
		}
		return singleton;
	}

	/**
	 * 
	 * 
	 */
	private ResourceLoader() {
	}

	/**
	 * 
	 * @param resourceName
	 * @return
	 */
	public URL findResource(String resourceName) {
		URL resource = null;
		ClassLoader cloader = null;

		// Trying with SystemClassLoader
		resource = ClassLoader.getSystemResource(resourceName);

		if(resource != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.append("Resource ");
				LOGGER.append(resourceName);
				LOGGER.append(" is loaded using the SystemClassLoader.");
				LOGGER.debug();
			}
			return resource;
		}

		// Trying with ContextClassLoader
		cloader = Thread.currentThread().getContextClassLoader();
		if (cloader != null) {
			resource = cloader.getResource(resourceName);
		}
		if (resource != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.append("Resource ");
				LOGGER.append(resourceName);
				LOGGER.append(" is loaded using the ContextClassLoader.");
				LOGGER.debug();
			}
			return resource;
		}
		
		// Trying with classloader of ResourceLoader
		cloader = ResourceLoader.class.getClassLoader();
		if (cloader != null) {
			resource = cloader.getResource(resourceName);
		}

		if (resource != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.append("Resource ");
				LOGGER.append(resourceName);
				LOGGER.append(" is loaded using the classloader of "
						+ ResourceLoader.class);
				LOGGER.debug();
			}
			return resource;
		}
		
		if(resource == null) {
			LOGGER.warn("Unable to load the resource " + resourceName);
		}
		return resource;
	}

	/**
	 * 
	 * @param resource
	 * @return
	 * @throws IOException
	 */
	public InputStream getResourceAsStream(String resourceName)
	throws IOException {
		URL resource = findResource(resourceName);
		return (resource == null) ? null : resource.openStream();
	}

	/**
	 * 
	 * @param className
	 * @return
	 */
	public Class loadClass(String className) {
		return loadClass(className, null);
	}

	/**
	 * 
	 * @param className
	 * @return
	 */
	public Class loadClass(String className, Class callingClass) {
		Class clazz = null;

		// Trying with Caller Class Loader
		ClassLoader classLoader = null;
		try {
			if (callingClass != null
					&& (classLoader = callingClass.getClassLoader()) != null) {
				clazz = Class.forName(className, true, classLoader);
			}
		} catch (ClassNotFoundException e) {
			LOGGER.append("Failed to load class ");
			LOGGER.append(className);
			LOGGER
					.append(" with CallerClassLoader; Trying with ResourceLoader ClassLoader.");
			LOGGER.info();
		}

		if (clazz != null) {
			return clazz;
		}

		// Trying with this class loader
		classLoader = ResourceLoader.class.getClassLoader();
		try {
			if (classLoader != null) {
				clazz = Class.forName(className, true, classLoader);
			}
		} catch (ClassNotFoundException e) {
			LOGGER.append("Failed to load class ");
			LOGGER.append(className);
			LOGGER
					.append(" with ResourceLoader ClassLoader; Trying with ContextClassLoader");
			LOGGER.info();
		}
		if (clazz != null) {
			return clazz;
		}

		// Trying with ContextClassLoader
		classLoader = Thread.currentThread().getContextClassLoader();
		try {
			if (classLoader != null) {
				clazz = classLoader.loadClass(className);
			}
		} catch (ClassNotFoundException e) {
			LOGGER.append("Failed to load class ");
			LOGGER.append(className);
			LOGGER.append(" with ContextClassLoader.");
			LOGGER.info();
		}
		return clazz;
	}
}
