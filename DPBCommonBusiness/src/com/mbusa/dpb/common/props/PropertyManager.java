/*********************************************************************************************
 * Project Name	: DPB
 * Module Name	: Property Component
 * Program Name	: PropertyManager
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

import com.mbusa.dpb.common.logger.LogManager;
import com.mbusa.dpb.common.logger.Logger;

/**
 * TODO Please provide the Description for the type PropertyManager
 * 
 */
public abstract class PropertyManager {

	private static final Logger LOGGER = LogManager.getLogger(PropertyManager.class);

	private static PropertyManagerFactory propManagerFactory = null;

	private static boolean initialized = false;

	static private void initializeFactory() {
		propManagerFactory = PropertyManagerFactory.getConfiguredPropertyManagerInstance();
		if (propManagerFactory == null) {
			LOGGER.info("Using default PropertyManagerFactory " + FilePropertyManagerFactory.class.getName());
			propManagerFactory = new FilePropertyManagerFactory();
		}
		initialized = true;
	}

	/**
	 * 
	 * This method returns the PropertyManager for JVM System properties.
	 * 
	 * @return
	 */
	public static PropertyManager getSystemPropertyManager() {
		if (!initialized) {
			initializeFactory();
		}
		return propManagerFactory.getSystemPropertyManager();
	}

	/**
	 * 
	 * This method returns default PropertyManager.
	 * 
	 * @return - Default PropertyManager
	 */
	public static PropertyManager getPropertyManager() {
		if (!initialized) {
			initializeFactory();
		}
		return propManagerFactory.getDefaultPropertyManager();
	}

	/**
	 * 
	 * This method returns the PropertyManager for requested hierachy.
	 * 
	 * @param name requested properties files hierarchy
	 * @return  PropertyManager for requested hierarchy.
	 */
	public static PropertyManager getPropertyManager(String name) {
		if (!initialized) {
			initializeFactory();
		}
		return propManagerFactory.getPropertyManager(name);
	}

	protected String name;

	protected Properties properties;

	protected PropertyManagerFactory factory;

	/**
	 * Constructor to create the instance of PropertyManager.
	 * 
	 * @param name - Name of the PropertyManager
	 * @param factory - PropertyManagerFactory used to create the PropertyManager
	 */
	protected PropertyManager(String name, PropertyManagerFactory factory) {
		if (name == null || "".equalsIgnoreCase(name)) {
			/*ServiceHelper.throwException(new ServiceException(
					"PropertyManager name should not be null or empty."));*/
		}
		this.name = name;
		this.factory = factory;
		this.properties = new Properties();
	}

	/**
	 * Constructor to create PropertyManager for specified parameters.
	 * 
	 * @param name - Name of the PropertyManager (Hierarchy)
	 * @param properties - Initial Properties
	 * @param factory - PropertyManagerFactory used to create the PropertyManager
	 */
	protected PropertyManager(String name, Properties properties, PropertyManagerFactory factory) {
		this(name, factory);
		if (properties != null) {
			this.properties = properties;
		}
	}

	/**
	 * This method provides chance for sub-classes to initialize.
	 * 
	 * Sub-classes can have code required to initialize themself.
	 * 
	 */
	protected void initialize() {
	}

	/**
	 * Returns the name of the current PropertyManager.
	 * 
	 * @return - Name of the PropertyManager.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter Method for factory
	 * 
	 * @return the factory
	 */
	public PropertyManagerFactory getFactory() {
		return factory;
	}

	/**
	 * Returns the value of property to specified key
	 * 
	 * @param name
	 * @return
	 */
	public abstract Object getProperty(String name);

	/**
	 * TODO Please provide description for the method getProperty
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Object getProperty(String name, Object defaultValue) {
		Object value = getProperty(name);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	/**
	 * 
	 * TODO Please provide description for the method getProperty
	 * 
	 * @param name
	 * @param converter
	 * @return
	 */
	public Object getProperty(String name, TypeConverter converter) {
		Object value = getProperty(name);
		if (value == null) {
			return null;
		}
		return converter.convert(value);
	}

	/**
	 * 
	 * TODO Please provide description for the method getProperty
	 * 
	 * @param name
	 * @param defaultValue
	 * @param converter
	 * @return
	 */
	public Object getProperty(String name, Object defaultValue, TypeConverter converter) {
		Object value = getProperty(name, converter);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsString
	 * 
	 * @param name
	 * @return
	 */
	public String getPropertyAsString(String name) {
		return (String) getProperty(name, TypeConverterHelper.getConverter(String.class));

	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsString
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public String getPropertyAsString(String name, String defaultValue) {
		String result = getPropertyAsString(name);
		return (result == null) ? defaultValue : result;
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsBoolean
	 * 
	 * @param name
	 * @return
	 */
	public Boolean getPropertyAsBoolean(String name) {
		return (Boolean) getProperty(name, TypeConverterHelper.getConverter(Boolean.class));
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsBoolean
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public boolean getPropertyAsBoolean(String name, boolean defaultValue) {
		Boolean result = getPropertyAsBoolean(name);
		return (result == null) ? defaultValue : result.booleanValue();
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsShort
	 * 
	 * @param name
	 * @return
	 */
	public Short getPropertyAsShort(String name) {
		return (Short) getProperty(name, TypeConverterHelper.getConverter(Short.class));
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsShort
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public short getPropertyAsShort(String name, short defaultValue) {
		Short value = getPropertyAsShort(name);
		return (value == null) ? defaultValue : value.shortValue();
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsInteger
	 * 
	 * @param name
	 * @return
	 */
	public Integer getPropertyAsInteger(String name) {
		return (Integer) getProperty(name, TypeConverterHelper.getConverter(Integer.class));
	}
	
	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsInteger
	 *
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public int getPropertyAsInteger(String name, int defaultValue) {
		Integer value = getPropertyAsInteger(name);
		return (value == null) ? defaultValue : value.intValue();
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsLong
	 * 
	 * @param name
	 * @return
	 */
	public Long getPropertyAsLong(String name) {
		return (Long) getProperty(name, TypeConverterHelper.getConverter(Long.class));
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsLong
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public long getPropertyAsLong(String name, long defaultValue) {
		Long value = getPropertyAsLong(name);
		return (value == null) ? defaultValue : value.longValue();
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsFloat
	 * 
	 * @param name
	 * @return
	 */
	public Float getPropertyAsFloat(String name) {
		return (Float) getProperty(name, TypeConverterHelper.getConverter(Float.class));
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsFloat
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public float getPropertyAsFloat(String name, float defaultValue) {
		Float value = getPropertyAsFloat(name);
		return (value == null) ? defaultValue : value.floatValue();
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsDouble
	 * 
	 * @param name
	 * @return
	 */
	public Double getPropertyAsDouble(String name) {
		return (Double) getProperty(name, TypeConverterHelper.getConverter(Double.class));
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsDouble
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public double getPropertyAsDouble(String name, double defaultValue) {
		Double value = getPropertyAsDouble(name);
		return (value == null) ? defaultValue : value.doubleValue();
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsBigInteger
	 * 
	 * @param name
	 * @return
	 */
	public BigInteger getPropertyAsBigInteger(String name) {
		return (BigInteger) getProperty(name, TypeConverterHelper.getConverter(BigInteger.class));
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsBigInteger
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public BigInteger getPropertyAsBigInteger(String name, BigInteger defaultValue) {
		BigInteger value = getPropertyAsBigInteger(name);
		return (value == null) ? defaultValue : value;
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsBigDecimal
	 * 
	 * @param name
	 * @return
	 */
	public BigDecimal getPropertyAsBigDecimal(String name) {
		return (BigDecimal) getProperty(name, TypeConverterHelper.getConverter(BigDecimal.class));
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsBigDecimal
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public BigDecimal getPropertyAsBigDecimal(String name, java.math.BigDecimal defaultValue) {
		BigDecimal value = getPropertyAsBigDecimal(name);
		return (value == null) ? defaultValue : value;
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsDate
	 * 
	 * @param name
	 * @return
	 */
	public Date getPropertyAsDate(String name) {
		return (Date) getProperty(name, TypeConverterHelper.getConverter(Date.class));
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsDate
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Date getPropertyAsDate(String name, Date defaultValue) {
		Date value = getPropertyAsDate(name);
		return (value == null) ? defaultValue : value;
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsFile
	 * 
	 * @param name
	 * @return
	 */
	public File getPropertyAsFile(String name) {
		return (File) getProperty(name, TypeConverterHelper.getConverter(File.class));
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsFile
	 * 
	 * @param name
	 * @param defaultFile
	 * @return
	 */
	public File getPropertyAsFile(String name, File defaultFile) {
		File result = getPropertyAsFile(name);

		return (result == null) ? defaultFile : result;
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsFile
	 * 
	 * @param name
	 * @param defaultFileName
	 * @return
	 */
	public File getPropertyAsFile(String name, String defaultFileName) {
		File result = getPropertyAsFile(name);

		return (result == null) ? new File(defaultFileName) : result;
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsURL
	 * 
	 * @param name
	 * @return
	 */
	public URL getPropertyAsURL(String name) {
		return (URL) getProperty(name, TypeConverterHelper.getConverter(URL.class));
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsURL
	 * 
	 * @param name
	 * @param defaultURL
	 * @return
	 */
	public URL getPropertyAsURL(String name, URL defaultURL) {
		URL value = getPropertyAsURL(name);
		return (value == null) ? defaultURL : value;
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsURL
	 * 
	 * @param name
	 * @param defaultURL
	 * @return
	 */
	public URL getPropertyAsURL(String name, String defaultURL) {
		URL value = null;
		try {
			value = getPropertyAsURL(name, new URL(defaultURL));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsClass
	 * 
	 * @param name
	 * @return
	 */
	public Class getPropertyAsClass(String name) {
		return (Class) getProperty(name, TypeConverterHelper.getConverter(Class.class));
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsClass
	 * 
	 * @param name
	 * @param defaultClass
	 * @return
	 */
	public Class getPropertyAsClass(String name, Class defaultClass) {
		Class value = getPropertyAsClass(name);
		return (value == null) ? defaultClass : value;
	}
	
	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsClassInstance
	 * 
	 * @param name
	 * @return
	 */
	public Object getPropertyAsClassInstance(String name) {
		return null;
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsClassInstance
	 * 
	 * @param name
	 * @param defaulInstance
	 * @return
	 */
	public Object getPropertyAsClassInstance(String name, Object defaulInstance) {
		return defaulInstance;
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsStream
	 * 
	 * @param name
	 * @return
	 */
	public InputStream getPropertyAsInputStream(String name) {
		return (InputStream) getProperty(name, TypeConverterHelper.getConverter(InputStream.class));
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyAsStream
	 * 
	 * @param name
	 * @param defaultResource
	 * @return
	 */
	public InputStream getPropertyAsInputStream(String name, InputStream defaultStream) {
		InputStream value = getPropertyAsInputStream(name);
		return (value == null) ? defaultStream : value;
	}

	/**
	 * 
	 * TODO Please provide description for the method isPropertyExists
	 * 
	 * @param name
	 * @return
	 */
	public boolean isPropertyExists(String name) {
		return getProperty(name) != null;
	}

	/**
	 * 
	 * TODO Please provide description for the method getPropertyNames
	 * 
	 * @param category
	 * @return
	 */
	public String[] getPropertyNames(String category) {
		Set names = properties.keySet();
		return (String[]) names.toArray(new String[0]);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public URL getPropertyAsResource(String name) {
		String value = getPropertyAsString(name);
		return ResourceLoader.getInstance().findResource(value);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws IOException 
	 */
	public InputStream getPropertyAsResourceStream(String name) throws IOException {
		String value = getPropertyAsString(name);
		return ResourceLoader.getInstance().getResourceAsStream(value);
	}

	/**
	 * 
	 * TODO Please provide description for the method getCategories
	 * 
	 * @return
	 */
	public String[] getCategories() {
		return null;
	}

	/**
	 * 
	 * TODO Please provide description for the method getSubCategories
	 * 
	 * @param category
	 * @return
	 */
	public String[] getSubCategories(String category) {
		return null;
	}

	/**
	 * Retusn all properties of current PropertyManager. If current
	 * PropertyManager Supports Hierarchy and parent is not null; It also
	 * returns properties of parent also.
	 * 
	 * @return
	 */
	public abstract Properties getAllProperties();

	/**
	 * 
	 * TODO Please provide description for the method getKeys
	 * 
	 * @param prefix
	 * @return
	 */
	public Enumeration getKeys(String prefix) {
		ArrayList entries = new ArrayList(8);

		for (Enumeration allKeys = getAllProperties().keys(); allKeys.hasMoreElements();) {
			String key = (String) allKeys.nextElement();
			if (key.startsWith(prefix)) {
				entries.add(key);
			}
		}
		String[] ret = new String[entries.size()];
		ret = (String[]) entries.toArray(ret);
		Arrays.sort(ret, String.CASE_INSENSITIVE_ORDER);
		return Collections.enumeration(Arrays.asList(ret));
	}

	/**
	 * 
	 * TODO Please provide description for the method getProperties
	 * 
	 * @param name
	 * @return
	 */
	public String[] getIndexedProperty(String name) {
		if (name == null || "".equalsIgnoreCase(name)) {
			return null;
		}
		ArrayList list = new ArrayList();
		if(!name.endsWith(".")) {
			name = name + ".";
		}
		String prop = null;

		for (int i = 0; (prop = getPropertyAsString(name + i)) != null; list.add(prop), i++)
			;

		String[] result = new String[list.size()];
		return (String[]) list.toArray(result);
	}

	/**
	 * 
	 * TODO Please provide description for the method getProperties
	 * 
	 * @param startsWith
	 * @return
	 */
	public Properties getProperties(String startsWith) {
		if (name == null || "".equalsIgnoreCase(name)) {
			return null;
		}
		Properties props = new Properties();
		String keyPrefix = (!startsWith.endsWith(".")) ? startsWith + "." : startsWith;

		String propertyName = null;
		Properties allProperties = getAllProperties();
		for (Enumeration allKeys = allProperties.keys(); allKeys.hasMoreElements();) {
			propertyName = (String) allKeys.nextElement();
			if (propertyName.startsWith(keyPrefix)) {
				props.put(propertyName, allProperties.get(propertyName));
			}
		}
		return props;
	}

	/**
	 * 
	 * Added for Login changes to fetch user roles from property file - Amit
	 * 
	 * @param userid
	 * @return
	 */
	public Properties getUserRole(String userid) {
		if (name == null || "".equalsIgnoreCase(name)) {
			return null;
		}
		Properties props = new Properties();

		String propertyName = null;
		Properties allProperties = getAllProperties();
		for (Enumeration allKeys = allProperties.keys(); allKeys
				.hasMoreElements();) {
			propertyName = (String) allKeys.nextElement();
			if (propertyName.equals(userid)) {
				props.put(propertyName, allProperties.get(propertyName));
			}
		}
		return props;
	}
	

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Name: ");
		buffer.append(this.name);
		buffer.append("\nFactory: ");
		buffer.append(factory);
		buffer.append("\nProperties: ");
		buffer.append(properties);
		return buffer.toString();
	}
}
