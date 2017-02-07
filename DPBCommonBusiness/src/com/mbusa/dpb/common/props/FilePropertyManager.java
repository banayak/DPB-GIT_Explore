/*********************************************************************************************
 * Project Name	: DPB
 * Module Name	: PropertyManager
 * Program Name	: FilePropertyManager
 * Description	: This component manages the properties files of the DPB project.
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.mbusa.dpb.common.logger.LogManager;
import com.mbusa.dpb.common.logger.Logger;

/**
 * File based PropertyManager
 * 
 */
public class FilePropertyManager extends PropertyManager implements ResourceObserver {
	
	public static final char FILE_SEPERATOR = '.';
	
	public static final String FILE_EXTENSTION = ".properties";
	
	public static final String BASE_DIR = "properties";
	
	private static final Logger LOGGER = LogManager.getLogger(FilePropertyManager.class);
	
	protected URL fileURL;
	
	FilePropertyManager parent = null;

	/**
	 * Constructor for FilePropertyManager
	 * 
	 * @param name
	 * @param factory
	 */
	protected FilePropertyManager(String name, FilePropertyManagerFactory factory) {
		super(name, factory);
	}

	/**
	 * 
	 * @param name
	 * @param props
	 * @param factory
	 */
	protected FilePropertyManager(String name, Properties props, FilePropertyManagerFactory factory) {
		super(name, props, factory);
	}
	
	
	protected void initialize() {
		String shortName = (name.indexOf(FILE_SEPERATOR) < 0) ? name : name.substring(name.lastIndexOf(FILE_SEPERATOR) + 1);
		String fileName = shortName + FILE_EXTENSTION;
		
		fileURL = ResourceLoader.getInstance().findResource(BASE_DIR + "/" + fileName);
		
		if (fileURL != null) { 
			File file = new File(fileURL.getFile());
			Watchdog.getInstance().addResource(new FileResource(file), this);
		} else {
			LOGGER.append("Resource ")
			.append(fileName)
			.append(" is not found.")
			.warn();
		}
		refresh();
	}

	
	public Properties getAllProperties() {
		 Properties props = new Properties();
        if (parent != null) {
            props.putAll(parent.getAllProperties());
        }
        props.putAll(properties);
        return props;
	}


	public Object getProperty(String name) {
		Object result = properties.get(name);
		
		if(result == null && parent != null) {
			result = parent.getProperty(name);
		}
        return result;
	}

	/**
	 * Reloads the properties from the file.
	 * 
	 */
	void refresh() {
		Properties props = new Properties();
		if (fileURL != null) {
			LOGGER.info("Loading properties from the file " + fileURL);
			InputStream inStream = null;
			try {
				inStream = fileURL.openStream();
				props.load(inStream);
				LOGGER.info("Properties loaded successfully from the file " + fileURL);
			} catch (IOException ioe) {
				LOGGER.warn("Exception in reading properties from the file " + fileURL, ioe );
			} finally {
				if (inStream != null) {
					try {
						inStream.close();
					} catch (IOException ioe) {
						LOGGER.warn("Unable to close the file " + fileURL + " properly.", ioe);
					}
				}
			}
		}
		super.properties = props;
	}

	public void resourceChanged(Resource resource, Object info) {
		refresh();
	}
}
