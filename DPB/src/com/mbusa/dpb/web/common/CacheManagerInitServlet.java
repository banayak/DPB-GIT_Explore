/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: CacheManagerInitServlet.java
 * Program Version			: 1.0
 * Program Description		: This Servlet Class is used for Caching the data.
 * 
 * Modification History		: 
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * 
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.common;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.websphere.cache.DistributedMap;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.common.util.CacheManager;

/**
 * @author SK5008848
 * 
 */
public class CacheManagerInitServlet extends HttpServlet {
	
	private static DPBLog LOGGER = DPBLog.getInstance(CacheManagerInitServlet.class);
	static final private String CLASSNAME = CacheManagerInitServlet.class.getName();
	
	static public DistributedMap cache = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#init(ServletConfig config)
	 * 
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {

		final String methodName = "init";
		LOGGER.enter(CLASSNAME, methodName);
		
		super.init(config);
		InitialContext ic;
		try {
			ic = new InitialContext();
			cache = (DistributedMap) ic.lookup("services/cache/bpmCache");
		} catch (NamingException e) {

			e.printStackTrace();
		}
		CacheManager cacheManager = new CacheManager();
		cache.setTimeToLive(PropertyManager.getPropertyManager().getPropertyAsInteger("cache.time.live"));
		cacheManager.setCache(cache);
		
		LOGGER.exit(CLASSNAME, methodName);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		int kpiId = Integer.parseInt(request.getParameter("kpiId"));
		CacheManager cacheManager = new CacheManager();
		try {
			cacheManager.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void destroy() {
		super.destroy();
		cache.clear();
		cache = null;

	}
}
