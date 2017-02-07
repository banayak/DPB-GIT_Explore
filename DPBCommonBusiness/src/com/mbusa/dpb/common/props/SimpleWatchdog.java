/*********************************************************************************************
 * Project Name	: MyComponents
 * Module Name	: TODO
 * Program Name	: Resource.java
 * Description	: TODO
 *
 * Created By	: chaitanyanarvaneni		Date: Dec 23, 2007		Version: 1.0
 * *******************************************************************************************
 * Modification Details:
 *
 * Name							Date				Purpose
 * -------------------------------------------------------------------------------------------
 *
 *
 ********************************************************************************************/
package com.mbusa.dpb.common.props;

import java.util.Iterator;

import com.mbusa.dpb.common.logger.LogManager;
import com.mbusa.dpb.common.logger.Logger;


public class SimpleWatchdog extends Watchdog implements Runnable {
	public static final String WATCHDOG_DELAY_PROPERTY = "watchdog.delay";

	public static final int DEFAULT_WATCHDOG_DELAY = 2;

	private static final Logger LOGGER = LogManager.getLogger(SimpleWatchdog.class);

	private Thread watchdogThread = null;

	private boolean started, stop;

	private int delay;

	/**
	 * 
	 */
	protected void initialize() {
		LOGGER.enter(this.getClass(), "initialize()");

		Integer delay = null;

		try {
			delay = PropertyManagerFactory.getDefaultSystemPropertyManager().getPropertyAsInteger(
					WATCHDOG_DELAY_PROPERTY);
		} catch (RuntimeException re) {
			LOGGER.warn("Exception in reading the property " + WATCHDOG_DELAY_PROPERTY, re);
		}

		if (delay != null) {
			if (delay.intValue() <= 0) {
				LOGGER.warn("Value of property " + WATCHDOG_DELAY_PROPERTY + " should be greater than 0.");
			} else {
				this.delay = delay.intValue();
				LOGGER.info("Current Watchdog delay is " + this.delay);
			}
		}

		if (this.delay == 0) {
			LOGGER.info("Using default delay " + DEFAULT_WATCHDOG_DELAY + " minutes.");
			this.delay = DEFAULT_WATCHDOG_DELAY;
		}

		watchdogThread = new Thread(this);
		watchdogThread.setDaemon(true);
		LOGGER.exit(this.getClass(), "initialize()");
	}

	/**
	 * 
	 */
	protected boolean isRunnging() {
		if (watchdogThread.isAlive()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public void shutdown() {
		stop = true;
	}

	protected void start() {
		if (!started) {
			watchdogThread.start();
		} else if (!watchdogThread.isAlive()) {
			initialize();
			watchdogThread.start();
		}

		started = true;
	}

	/**
	 * 
	 */
	public void run() {
		while (!stop) {
			try {
				Thread.sleep(delay * 60 * 1000);
			} catch (InterruptedException e) {
				LOGGER.warn("SimpleWatchdog Thread interrupted.");
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Checking resources for modifications...");
			}

			Resource resource = null;
			for (Iterator resourceItr = super.listResources().iterator(); resourceItr.hasNext();) {
				resource = (Resource) resourceItr.next();
				if (resource.isChanged()) {
					LOGGER.append("Resource ")
					.append(resource)
					.append(" is modified at ")
					.append(String.valueOf(resource.lastModified()))
					.append(".")
					.info();
					super.invokeListeners(resource, null);
				}
			}
		}
	}
}
