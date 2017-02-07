/*********************************************************************************************
 * Project Name	: DPB
 * Module Name	: PropertyManager
 * Program Name	: FilePropertyManagerFactory
 * Description	: This component manages the properties files of the DPB project.
 *
 * Created By	: JK32658						Date: Aug 06, 2013			Version: 1.0
 * *******************************************************************************************
 * Modification Details:
 *
 * Name							Date				Purpose
 * -------------------------------------------------------------------------------------------
 *
 *
 ********************************************************************************************/
package com.mbusa.dpb.common.props;

import com.mbusa.dpb.common.logger.Log4jManager;
import com.mbusa.dpb.common.logger.Logger;



/**
 * This is the factory class for file based PropertyManagers.
 * 
 */
public class FilePropertyManagerFactory extends PropertyManagerFactory {

	public static final String PROPERTY_DEFAULT_PROPERTY_MANAGER = "FilePropertyManager.default";
	
	private static final Logger LOGGER = Log4jManager.getLogger(FilePropertyManagerFactory.class);

	protected PropertyManager defaultPropertyManager = null;

	protected boolean triedForDefaultManager = false;

	
	public FilePropertyManagerFactory() {
	}
	
	private String validateName(String name) {
		String fileSeperator = String.valueOf(FilePropertyManager.FILE_SEPERATOR);
		if(!name.endsWith(fileSeperator) && !name.startsWith(fileSeperator)) {
			return name;
		}
		
		StringBuffer sBuffer = new StringBuffer(name);
		
		// Remove extra FileSeperators at the begin
		for(int i=0; sBuffer.charAt(i) != FilePropertyManager.FILE_SEPERATOR && i < sBuffer.length(); i++) {
			sBuffer.deleteCharAt(i);
		}
		
		// Remove extra FileSeperators at the end
		for(int i=sBuffer.length(); sBuffer.charAt(i) != FilePropertyManager.FILE_SEPERATOR && i>0; i--) {
			sBuffer.deleteCharAt(i);
		}
		return sBuffer.toString();
	}

	
	protected PropertyManager createPropertyManager(String name) {
		name = validateName(name);
		FilePropertyManager requestedManager = new FilePropertyManager(name, this);
		// Assigning Parent
		if(name.indexOf(FilePropertyManager.FILE_SEPERATOR) > 0) {
			String parentName = name.substring(0, name.lastIndexOf(FilePropertyManager.FILE_SEPERATOR));
			requestedManager.parent = (FilePropertyManager) PropertyManager.getPropertyManager(parentName);
		} 
		return requestedManager;
	}


	protected synchronized void tryForDefaultPropertyManager() {
		// Second Checking in Double Checking Pattern
		if (triedForDefaultManager) {
			return;
		}
		String defaultPropertyManager = PropertyManagerFactory.getDefaultSystemPropertyManager().getPropertyAsString(PROPERTY_DEFAULT_PROPERTY_MANAGER);
		if (defaultPropertyManager != null && !"".equalsIgnoreCase(defaultPropertyManager)) {
			this.defaultPropertyManager = super.getPropertyManager(defaultPropertyManager);
		} else {
			this.defaultPropertyManager = super.getPropertyManager("DPB");
		}
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Using \'" + this.defaultPropertyManager.getName() + "\' as default PropertyManager.");
		}
		triedForDefaultManager = true;
	}


	public PropertyManager getDefaultPropertyManager() {
		if (!triedForDefaultManager) {
			tryForDefaultPropertyManager();
		}
		return this.defaultPropertyManager;
	}


	public PropertyManager getParent(PropertyManager propManager) {
		if(propManager == null) {
			return null;
		}
		return ((FilePropertyManager)propManager).parent;
	}

}
