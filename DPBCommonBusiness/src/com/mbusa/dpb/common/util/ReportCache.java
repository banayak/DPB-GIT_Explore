package com.mbusa.dpb.common.util;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.LRUMap;

import com.mbusa.dpb.common.logger.DPBLog;

/**
 * @author banayak
 * 
 * @param <K>
 * @param <T>
 */
public class ReportCache<K, T> {

	private long timeToLive;
	private LRUMap crunchifyCacheMap;
	private static ReportCache<?, ?> reportCacheRef;
	// Time to Live Objects for 7 days
	private long crunchifyTimeToLive = 604800;
	// Time Interval in day to run the thread to cleanup
	private long crunchifyTimerInterval = 1;
	// Maximum Number of items to be stored in cache
	private int maxItems = 30;
	public static boolean dlrCompReportProcessing;
	private static DPBLog LOGGER = DPBLog.getInstance(ReportCache.class);

	static {
		reportCacheRef = new ReportCache<String, Object>();
	}

	protected class CacheObject {
		public long lastAccessed = System.currentTimeMillis();
		public T value;

		protected CacheObject(T value) {
			this.value = value;
		}
	}

	@SuppressWarnings("rawtypes")
	public static ReportCache getInstance() {
		return reportCacheRef;
	}

	private ReportCache() {
		this.timeToLive = crunchifyTimeToLive * 1000;

		crunchifyCacheMap = new LRUMap(maxItems);

		if (timeToLive > 0 && crunchifyTimerInterval > 0) {

			Thread t = new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {
							// Thread.sleep(crunchifyTimerInterval * 1000);
							TimeUnit.DAYS.sleep(crunchifyTimerInterval);
						} catch (InterruptedException ex) {
						}
						cleanup();
					}
				}
			});

			t.setDaemon(true);
			t.start();
		}
	}

	public void put(K key, T value) {
		synchronized (crunchifyCacheMap) {
			crunchifyCacheMap.put(key, new CacheObject(value));
		}
	}

	public boolean containsKey(K key) {
		return crunchifyCacheMap.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public T get(K key) {
		synchronized (crunchifyCacheMap) {
			CacheObject c = (CacheObject) crunchifyCacheMap.get(key);

			if (c == null)
				return null;
			else {
				c.lastAccessed = System.currentTimeMillis();
				LOGGER.info("Report cache key :"+key+" 's last accessed time :"+c.lastAccessed);
				return c.value;
			}
		}
	}

	public void remove(K key) {
		synchronized (crunchifyCacheMap) {
			crunchifyCacheMap.remove(key);
		}
	}

	public int size() {
		synchronized (crunchifyCacheMap) {
			return crunchifyCacheMap.size();
		}
	}

	@SuppressWarnings("unchecked")
	public void cleanup() {
		LOGGER.info("Thread wakeup and cleaning process started");
		long now = System.currentTimeMillis();
		ArrayList<K> deleteKey = null;

		synchronized (crunchifyCacheMap) {
			MapIterator itr = crunchifyCacheMap.mapIterator();

			deleteKey = new ArrayList<K>((crunchifyCacheMap.size() / 2) + 1);
			K key = null;
			CacheObject c = null;

			while (itr.hasNext()) {
				key = (K) itr.next();
				c = (CacheObject) itr.getValue();

				if (c != null && (now > (timeToLive + c.lastAccessed))) {
					LOGGER.info("Cleanup Time ::"+now+" , cache Key :"+key+" lifespan time :"+ (timeToLive + c.lastAccessed));
					deleteKey.add(key);
				}
			}
		}

		for (K key : deleteKey) {
			synchronized (crunchifyCacheMap) {
				LOGGER.info("Removing Key :" + key);
				crunchifyCacheMap.remove(key);
			}

			Thread.yield();
		}
	}

	@SuppressWarnings("unchecked")
	public void printAll() {
		MapIterator itr = crunchifyCacheMap.mapIterator();
		CacheObject c = null;
		K key = null;
		while (itr.hasNext()) {
			key = (K) itr.next();
			c = (CacheObject) itr.getValue();
			System.out.print(c.value + ",");
		}
	}
}