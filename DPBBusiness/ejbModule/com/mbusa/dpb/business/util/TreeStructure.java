package com.mbusa.dpb.business.util;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;

/**
 * Performance improvement on netstar report
 * @author sneh_brat
 *
 */
public class TreeStructure {
	
		private static DPBLog LOGGER = DPBLog.getInstance(FileReadingHelper.class);
		static final private String CLASSNAME = FileReadingHelper.class.getName();
		PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
		ArrayList<String> year = new ArrayList<String>();
		ArrayList<String> month = new ArrayList<String>();
		ArrayList<String> file = new ArrayList<String>();
		
	public ArrayList listDirectoryYear(String dirPath, int level) {
	    
		final String methodName = "listDirectoryYear";
		LOGGER.enter(CLASSNAME, methodName);
		if(dirPath != null)
		{
			File dir = new File(dirPath);
			Map author1 = new HashMap();
			File[] firstLevelFiles = dir.listFiles();
			if (firstLevelFiles != null && firstLevelFiles.length > 0) {
				for (File aFile : firstLevelFiles) {
					for (int i = 0; i < level; i++) {
						System.out.print("\t");
					}
					if (aFile.isDirectory()) {
						year.add(aFile.getName());
					} else {
						listDirectoryYear(aFile.getAbsolutePath(), level + 1);
					}
				}
			}
		}
	    LOGGER.exit(CLASSNAME, methodName);
	    return year;
	     
	}
	
	
	public ArrayList listDirectoryMonth(String dirPath, int level) {
	    
		final String methodName = "listDirectoryMonth";
		LOGGER.enter(CLASSNAME, methodName);
		File dir = new File(dirPath);
	   
	    Map author1 = new HashMap();
	    File[] firstLevelFiles = dir.listFiles();
	    if (firstLevelFiles != null && firstLevelFiles.length > 0) {
	        for (File aFile : firstLevelFiles) {
	            for (int i = 0; i < level; i++) {
	                System.out.print("\t");
	            }
	            if (aFile.isDirectory()) {
	                month.add(aFile.getName());
	            } 
	        }
	        
	    }
	    LOGGER.exit(CLASSNAME, methodName);
	     return month;
	}
     
	public ArrayList listDirectoryFile(String dirPath, int level) {
	   
		final String methodName = "listDirectoryFile";
		LOGGER.enter(CLASSNAME, methodName);
		
		File dir = new File(dirPath);
	    Map author1 = new HashMap();
	    File[] firstLevelFiles = dir.listFiles();
	    if (firstLevelFiles != null && firstLevelFiles.length > 0) {
	        for (File aFile : firstLevelFiles) {
	            if (aFile.isDirectory()) {
	            	file.add(aFile.getName());
	            } else {
	            	file.add(aFile.getName());
	            }
	            
	        }
	        
	    }
	    LOGGER.exit(CLASSNAME, methodName);
	     return file;
	}
}