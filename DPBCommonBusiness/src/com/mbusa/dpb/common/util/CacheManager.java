/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Suresh Karnati 
 * Program Name				: CacheManager.java
 * Program Version			: 1.0
 * Program Description		: This class is helps for cache. it store data in cache and give it from cache to application. 
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 29, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.common.util;

import com.ibm.websphere.cache.DistributedMap;
import com.ibm.websphere.command.CacheableCommandImpl;
/**
 * 
 * @author SK5008848
 *
 */
public class CacheManager extends CacheableCommandImpl {

	private static final long serialVersionUID = -8098013422300089468L;
	static public DistributedMap cache = null;

	public void setCache(DistributedMap cache) {
		this.cache = cache;
	}

	@Override
	public boolean isReadyToCallExecute() {
		return true;
	}
	/**
	 * 
	 * This method is used to store the data into cache object
	 * This is predefined method which is available in CacheableCommandImpl class
	 * 
	 */
	@Override
	public void performExecute(){
		/*DPBKPIDAOImpl dpbkpidaoImpl = new DPBKPIDAOImpl();
		dpbkpidaoImpl.getDPBKPIData();*/
	}
	/** 
	 * 
	 * Adding the Data to cache
	 * @param key
	 * @param Value
	 * 
	 */
	public void addtoCache(Object key, Object Value) {
		cache.put(key, Value);
	}
	/** 
	 * 
	 * Getting the Data from cache
	 * @param key
	 * @return
	 * 
	 */
	public Object getFromCache(Object key) {
		return cache.get(key);

	}
	/**
	 * 
	 * Removing the cache data form cache
	 * @param key
	 * 
	 */
	public void removeFromCache(Object key) {
		cache.remove(key);
	}
	/**
	 * 
	 * Setting time to cache after the particular time it will remove from cache
	 * @param seconds
	 * 
	 */
	public void setTimeToCache(Integer seconds){
		cache.setTimeToLive(seconds);
	}
}
