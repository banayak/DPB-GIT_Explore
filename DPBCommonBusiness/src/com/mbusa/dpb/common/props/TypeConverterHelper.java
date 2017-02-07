/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				: Services  
 * Author					: 
 * Program Name				: TypeConverterHelper
 * Program Version			: 1.0
 * Program Description		: 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 *    			  1.0        First Draft
 * ------------------------------------------------------------------------------------------
 *********************************************************************************************/

package com.mbusa.dpb.common.props;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



/**
 * Helper class to convert Java String type to others.
 *
 * @version
 * @author bengine
 */
public class TypeConverterHelper {

    /**
     * Comment for <code>SYPROP_PREFIX</code>
     *
     */
    public static final String SYPROP_PREFIX = "converterutils.converter.";

    /**
     *  
     */
    public static final TypeConverter BOOLEAN_CONVERTER = new TypeConverter() {
       
        public String getName() {
            return Boolean.class.getName();
        }

       
        public Object convert(Object obj) {
            if (obj == null) {
                return null;
            }

            if (obj instanceof Boolean) {
                return obj;
            }

            String stringValue = obj.toString();
            if (stringValue.equalsIgnoreCase("yes") || stringValue.equalsIgnoreCase("y") || stringValue.equalsIgnoreCase("true") || stringValue.equalsIgnoreCase("on")
                    || stringValue.equalsIgnoreCase("1") || stringValue.equalsIgnoreCase("t")) {
                return Boolean.TRUE;
            } else if (stringValue.equalsIgnoreCase("no") || stringValue.equalsIgnoreCase("n") || stringValue.equalsIgnoreCase("false") || stringValue.equalsIgnoreCase("off")
                    || stringValue.equalsIgnoreCase("0") || stringValue.equalsIgnoreCase("f")) {
                return Boolean.FALSE;
            } else {
                //ServiceHelper.throwException(new ServiceException("Can not conver " + obj + " to " + getName()));
            }
            return null;
        }
    };

    /**
     * 
     */
    public static final TypeConverter STRING_CONVERTER = new TypeConverter() {
    	public String getName() {
            return String.class.getName();
        }

    	public Object convert(Object obj) {
            if (obj == null) {
                return obj;
            }

            try {
                return obj.toString();
            } catch (Exception e) {
            	//ServiceHelper.throwException(new ServiceException(this.getClass() + ": Can not conver " + obj + " to " + getName(), e));
            }
            return null;
    	}
    };
    
    /**
     *  
     */
    public static final TypeConverter SHORT_CONVERTER = new TypeConverter() {
        public String getName() {
            return Short.class.getName();
        }

        public Object convert(Object obj) {
            if (obj == null) {
                return obj;
            }

            if (obj instanceof Short) {
                return obj;
            } else if (obj instanceof Number) {
                return new Short(((Number) obj).shortValue());
            }

            try {
                return new Short(obj.toString());
            } catch (Exception e) {
            	//ServiceHelper.throwException(new ServiceException(this.getClass() + ": Can not conver " + obj + " to " + getName(), e));
            }
            return null;
        }
    };

    /**
     *  
     */
    public static final TypeConverter INTEGER_CONVERTER = new TypeConverter() {
        public String getName() {
            return Integer.class.getName();
        }

        public Object convert(Object obj) {
            if (obj == null) {
                return obj;
            }

            if (obj instanceof Integer) {
                return obj;
            } else if (obj instanceof Number) {
                return new Integer(((Number) obj).intValue());
            }

            try {
                return new Integer(obj.toString());
            } catch (Exception e) {
            	//ServiceHelper.throwException(new ServiceException(this.getClass() + ": Can not conver " + obj + " to " + getName(), e));
            }
            return null;
        }
    };

    /**
     *  
     */
    public static final TypeConverter LONG_CONVERTER = new TypeConverter() {
        public String getName() {
            return Long.class.getName();
        }

        public Object convert(Object obj) {
            if (obj == null) {
                return obj;
            }

            if (obj instanceof Long) {
                return obj;
            } else if (obj instanceof Number) {
                return new Long(((Number) obj).longValue());
            }

            try {
                return new Long(obj.toString());
            } catch (Exception e) {
            	//ServiceHelper.throwException(new ServiceException(this.getClass() + ": Can not conver " + obj + " to " + getName(), e));
            }
            return null;
        }
    };

    /**
     *  
     */
    public static final TypeConverter FLOAT_CONVERTER = new TypeConverter() {
        public String getName() {
            return Float.class.getName();
        }

        public Object convert(Object obj) {
            if (obj == null) {
                return obj;
            }

            if (obj instanceof Float) {
                return obj;
            } else if (obj instanceof Number) {
                return new Float(((Number) obj).floatValue());
            }

            try {
                return new Float(obj.toString());
            } catch (Exception e) {
            	//ServiceHelper.throwException(new ServiceException(this.getClass() + ": Can not conver " + obj + " to " + getName(), e));
            }
            return null;
        }
    };

    /**
     *  
     */
    public static final TypeConverter DOUBLE_CONVERTER = new TypeConverter() {
        public String getName() {
            return Double.class.getName();
        }

        public Object convert(Object obj) {
            if (obj == null) {
                return obj;
            }

            if (obj instanceof Double) {
                return obj;
            } else if (obj instanceof Number) {
                return new Double(((Number) obj).doubleValue());
            }

            try {
                return new Double(obj.toString());
            } catch (Exception e) {
            	//ServiceHelper.throwException(new ServiceException(this.getClass() + ": Can not conver " + obj + " to " + getName(), e));
            }
            return null;
        }
    };

    /**
     *  
     */
    public static final TypeConverter BIGINTEGER_CONVERTER = new TypeConverter() {
        public String getName() {
            return BigInteger.class.getName();
        }

        public Object convert(Object obj) {
            if (obj == null) {
                return null;
            }

            if (obj instanceof BigInteger) {
                return obj;
            }

            try {
                return new BigInteger(obj.toString());
            } catch (Exception e) {
            	//ServiceHelper.throwException(new ServiceException(this.getClass() + ": Can not conver " + obj + " to " + getName(), e));
            }
            return null;
        }
    };

    /**
     *  
     */
    public static final TypeConverter BIGDECIMAL_CONVERTER = new TypeConverter() {
        public String getName() {
            return BigDecimal.class.getName();
        }

        public Object convert(Object obj) {
            if (obj == null) {
                return null;
            }

            if (obj instanceof BigDecimal) {
                return obj;
            }

            try {
                return new BigDecimal(obj.toString());
            } catch (Exception e) {
            	//ServiceHelper.throwException(new ServiceException(this.getClass() + ": Can not conver " + obj + " to " + getName(), e));
            }
            return null;
        }
    };

    /**
     *  
     */
    public static final TypeConverter DATE_CONVERTER = new TypeConverter() {
        public String getName() {
            return Date.class.getName();
        }

        public Object convert(Object obj) {
            if(obj == null) {
                return null;
            }
            
            if(obj instanceof Date) {
                return obj;
            }
            return null;
        }
    };

    /**
     *  
     */
    public static final TypeConverter FILE_CONVERTER = new TypeConverter() {
        public String getName() {
            return File.class.getName();
        }

        public Object convert(Object obj) {
            if (obj == null) {
                return obj;
            }

            if (obj instanceof File) {
                return obj;
            }

            return new File(obj.toString());
        }
    };

    /**
     *  
     */
    public static final TypeConverter URL_CONVERTER = new TypeConverter() {
        public String getName() {
            return URL.class.getName();
        }

        public Object convert(Object obj) {
            if (obj == null) {
                return obj;
            }

            if (obj instanceof URL) {
                return obj;
            }

            try {
                return new URL(obj.toString());
            } catch (MalformedURLException murle) {
            	//ServiceHelper.throwException(new ServiceException(this.getClass() + ": Can not conver " + obj + " to " + getName(), murle));
            }
            return null;
        }
    };

    /**
     *  
     */
    public static final TypeConverter CLASS_CONVERTER = new TypeConverter() {
        public String getName() {
            return Class.class.getName();
        }

        public Object convert(Object obj) {
            if (obj == null) {
                return obj;
            }

            if (obj instanceof Class) {
                return obj;
            }

            try {
        		Class clazz = null;
        		String className = obj.toString();
        		// Trying with this class loader
        		ClassLoader classLoader = TypeConverterHelper.class.getClassLoader();
        		try {
        			if (classLoader != null) {
        				clazz = Class.forName(className, true, classLoader);
        			}
        		} catch (ClassNotFoundException e) {
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
         		}
            } catch (Exception e) {
            	//ServiceHelper.throwException(new ServiceException(this.getClass() + ": Can not conver " + obj + " to " + getName(), e));
            }
            return null;
        }
    };

    /**
     *  
     */
    public static final TypeConverter INPUTSTREAM_CONVERTER = new TypeConverter() {
        public String getName() {
            return InputStream.class.getName();
        }

        public Object convert(Object obj) {
            if (obj == null) {
                return obj;
            }

            if (obj instanceof InputStream) {
                return obj;
            }

            try {
            	String resourceName = obj.toString();
        		URL resource = null;
        		ClassLoader cloader = null;

        		// Trying with SystemClassLoader
        		resource = ClassLoader.getSystemResource(resourceName);

        		if(resource != null) {
        			return resource.openStream();
        		}

        		// Trying with ContextClassLoader
        		cloader = Thread.currentThread().getContextClassLoader();
        		if (cloader != null) {
        			resource = cloader.getResource(resourceName);
        		}
        		if (resource != null) {
        			return resource.openStream();
        		}
        		
        		// Trying with classloader of ResourceLoader
        		cloader = TypeConverterHelper.class.getClassLoader();
        		if (cloader != null) {
        			resource = cloader.getResource(resourceName);
        		}

        		if (resource != null) {
        			return resource.openStream();
        		}
            } catch (Exception e) {
            	//ServiceHelper.throwException(new ServiceException(this.getClass() + ": Can not conver " + obj + " to " + getName(), e));
            }
            return null;
        }
    };
    /**
     *  
     */
    private static Map DEFAULT_CONVERTERS = new HashMap();

    static {
        DEFAULT_CONVERTERS.put(BOOLEAN_CONVERTER.getName(), BOOLEAN_CONVERTER);
        DEFAULT_CONVERTERS.put(SHORT_CONVERTER.getName(), SHORT_CONVERTER);
        DEFAULT_CONVERTERS.put(INTEGER_CONVERTER.getName(), INTEGER_CONVERTER);
        DEFAULT_CONVERTERS.put(LONG_CONVERTER.getName(), LONG_CONVERTER);
        DEFAULT_CONVERTERS.put(FLOAT_CONVERTER.getName(), FLOAT_CONVERTER);
        DEFAULT_CONVERTERS.put(DOUBLE_CONVERTER.getName(), DOUBLE_CONVERTER);
        DEFAULT_CONVERTERS.put(BIGINTEGER_CONVERTER.getName(), BIGINTEGER_CONVERTER);
        DEFAULT_CONVERTERS.put(BIGDECIMAL_CONVERTER.getName(), BIGDECIMAL_CONVERTER);
        DEFAULT_CONVERTERS.put(DATE_CONVERTER.getName(), DATE_CONVERTER);
        DEFAULT_CONVERTERS.put(FILE_CONVERTER.getName(), FILE_CONVERTER);
        DEFAULT_CONVERTERS.put(URL_CONVERTER.getName(), URL_CONVERTER);
        DEFAULT_CONVERTERS.put(CLASS_CONVERTER.getName(), CLASS_CONVERTER);
        DEFAULT_CONVERTERS.put(STRING_CONVERTER.getName(), STRING_CONVERTER);
        DEFAULT_CONVERTERS.put(INPUTSTREAM_CONVERTER.getName(), INPUTSTREAM_CONVERTER);
    }

    private static Map CONFIGURED_CONVERTERS = new HashMap();

    /**
     * Tries to load the configured converter for specific type. If converter is
     * not configured uses default converter of specified type.
     * If default converter is also not available, this method throws
     * erviceException.
     *
     * @param classType
     * @return
     * @param name 
     */
    private static TypeConverter loadConfiguredConverter(String name) {
        String converterClassName = getSystemProperty(SYPROP_PREFIX + name, null);
        Class converterClass = null;
        TypeConverter converter = null;
        if (converterClassName != null) {
            converterClass = loadClass(converterClassName, null);

            if (!TypeConverter.class.isAssignableFrom(converterClass)) {
            	//ServiceHelper.throwException(new ServiceException("Invalid TypeConverter: " + name));
            }

            if (converterClass != null) {
                try {
                    converter = (TypeConverter) converterClass.newInstance();
                } catch (Exception e) {
                    //ServiceHelper.throwException(new ServiceException(TypeConverter.class + ": Unable to instantiate configured converter" + name, e));
                }
            }
        }

        // If specified converter is not configured, use default converter if
        // exists.
        if (converter == null) {
            converter = (TypeConverter) DEFAULT_CONVERTERS.get(name);
        }

        if (converter == null) {
        	//ServiceHelper.throwException(new ServiceException("Converter not found for the type " + name));
        }
        return converter;
    }

    /**
     * Returns the converter for specified class. If converter is not exists for
     * specified type, this method throws the ServiceException.
     * 
     * @param name
     * @return
     */
    public static TypeConverter getConverter(String name) {
        if (name == null || "".equalsIgnoreCase(name)) {
        	//ServiceHelper.throwException(new ServiceException("Invalid converter name, converter name should not be null or empty."));
        }

        // Checks if configured converter is loaded
        TypeConverter result = (TypeConverter) CONFIGURED_CONVERTERS.get(name);

        if (result != null) {
            return result;
        }

        // If not loaded, try to load if specified converter is configured
        result = loadConfiguredConverter(name);

        CONFIGURED_CONVERTERS.put(name, result);
        return result;
    }

    /**
     * 
     * Returns the converter for specified class. If converter is not exists for
     * specified type, this method throws the ServiceException.
     * 
     * @param classType
     * @return
     */
    public static TypeConverter getConverter(Class classType) {
        return getConverter(classType.getName());
    }

    /**
     * Registers the specified converter with this class.
     * 
     * @param converter
     */
    public void registerConverter(TypeConverter converter) {
        if (converter == null) {
            return;
        }

        if (converter.getName() == null || "".equalsIgnoreCase(converter.getName())) {
        	//ServiceHelper.throwException(new ServiceException("Invalid converter name, converter name should not be null or empty."));
        }

        CONFIGURED_CONVERTERS.put(converter.getName(), converter);
    }

    /**
     * Removes the registered converter
     * 
     * @param name
     */
    public void removeConverter(String name) {
        CONFIGURED_CONVERTERS.remove(name);
    }

    /**
     * This method is used by PropertyManager and this class for internal usage.
     * This method is used to fetch System properties.
     *
     * @param propName -
     *            Property Name to be fetched
     * @param defaultValue -
     *            Default Value to return if specified property in not
     *            discribed.
     * @return - Defined property value for specified property name
     * @param name 
     */
    static String getSystemProperty(String name, String defaultValue) {
        String value = null;

        try {
            value = System.getProperty(name, defaultValue);
        } catch (SecurityException se) {
        	//ServiceHelper.throwException(new ServiceException(TypeConverter.class + ": Unable to load System property " + name, se));
        }

        return value;
    }

    /**
     * This method try to load the class specified by name parameter. Fallowing
     * are ClassLoader priority.
     * 
     * 1. ContextClassLoader 2. Caller ClassLoader 3. Class loader of this
     * class.
     * 
     * If the specified class is not loaded by any of above class loader, this
     * method returns the class specified by defaultClass parameter.
     * 
     * @param name
     * @param defaultClass
     * @return
     */
    static Class loadClass(String name, Class defaultClass) {
        Class resultClass = null;

        // Trying to load class from Context ClassLoader and caller class
        // ClassLoader
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            resultClass = Class.forName(name, true, contextClassLoader);
        } catch (ClassNotFoundException cnfe) {
        }

        // Trying to load class from the class loader of this class.
        if (resultClass == null) {
            try {
                resultClass = Class.forName(name);
            } catch (ClassNotFoundException e) {
            }
        }

        // use default class
        if (resultClass == null) {
            resultClass = defaultClass;
        }
        return resultClass;
    }

    /**
     * Avoids the instance creation of this class.
     *  
     */
    private TypeConverterHelper() {
    }
}