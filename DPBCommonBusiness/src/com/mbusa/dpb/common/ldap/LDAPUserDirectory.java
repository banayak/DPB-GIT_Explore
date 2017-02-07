package com.mbusa.dpb.common.ldap;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPCache;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPReferralException;
import netscape.ldap.LDAPSearchConstraints;
import netscape.ldap.LDAPSearchResults;
import netscape.ldap.LDAPUrl;
import netscape.ldap.LDAPv2;

import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.props.PropertyManager;


public class LDAPUserDirectory extends UserDirectory {
	
	static final private String CLASSNAME = LDAPUserDirectory.class.getName();
	
	//private static final DPBLog //LOGGER = DPBLog.getInstance(LDAPUserDirectory.class);
	
	static final public String PROP_CD_HOST = "ldap.host";
	
	static final public String PROP_CD_PORT = "ldap.port";
	
	static final public String PROP_CD_USER_NAME = "ldap.user.dn";
	
	static final public String PROP_CD_PASSWORD = "ldap.password";
	
	static final public String PROP_CD_CACHE_SIZE = "ldap.cache.size";
	
	static final public String PROP_CD_CACHE_REFRESH_TIME = "ldap.cache.refresh.time";
	
	static final public int DEFAULT_CACHE_SIZE = 1000000;	// Approxipately 1MB
	
	static final public int DEFAULT_CACHE_REFRESH_TIME = 10 * 60 * 60; // 10 Hours
	
	private boolean initialized;
	
	protected String hostname;

	protected int port; 
	
	protected String bindUsername;
	
	protected String bindUserPassword;
	
	protected LDAPConnection baseConnection;
	
	protected LDAPCache cache;
	

	protected void initialize() throws TechnicalException  {
		final String methodName = "initialize";
		//LOGGER.enter(CLASSNAME, methodName);

		/*if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Initializing LDAP User Directory");
		}*/
		
		hostname = PropertyManager.getPropertyManager().getPropertyAsString(PROP_CD_HOST);
		if(hostname == null || "".equals(hostname)) {
			String errMsg = MessageFormat.format("Mandatory Property {0} is not found.", new Object[]{PROP_CD_HOST});
			/*if(LOGGER.isInfoEnabled()) {
				LOGGER.info(errMsg);
			}*/
			throw new TechnicalException(errMsg);
		}
		
		port = PropertyManager.getPropertyManager().getPropertyAsInteger(PROP_CD_PORT, LDAPv2.DEFAULT_PORT);
		/*if(port == LDAPv2.DEFAULT_PORT && LOGGER.isInfoEnabled()) {
			LOGGER.info("Using default LDAP port ...");
		}*/
		
		bindUsername = PropertyManager.getPropertyManager().getPropertyAsString(PROP_CD_USER_NAME);
		if(bindUsername == null) {
			String errMsg = MessageFormat.format("Mandatory Property {0} is not found.", new Object[]{PROP_CD_HOST});
			/*if(LOGGER.isInfoEnabled()) {
				LOGGER.info(errMsg);
			}*/
			throw new TechnicalException(errMsg);
		}
		
		bindUserPassword = PropertyManager.getPropertyManager().getPropertyAsString(PROP_CD_PASSWORD);
		if(bindUserPassword == null) {
			String errMsg = MessageFormat.format("Mandatory Property {0} is not found.", new Object[]{PROP_CD_HOST});
			/*if(LOGGER.isInfoEnabled()) {
				LOGGER.info(errMsg);
			}*/
			throw new TechnicalException(errMsg);
		}
		
		int cacheTime = PropertyManager.getPropertyManager().getPropertyAsInteger(PROP_CD_CACHE_REFRESH_TIME, DEFAULT_CACHE_REFRESH_TIME);
		/*if(cacheTime == DEFAULT_CACHE_REFRESH_TIME && LOGGER.isInfoEnabled()) {
			LOGGER.info("Using default LDAP cache refresh time ...");
		}*/
		
		int cacheSize = PropertyManager.getPropertyManager().getPropertyAsInteger(PROP_CD_CACHE_SIZE, DEFAULT_CACHE_SIZE);
		/*if(cacheSize == DEFAULT_CACHE_SIZE && LOGGER.isInfoEnabled()) {
			LOGGER.info("Using default LDAP cache size...");
		}*/
		
		cache = new LDAPCache(cacheTime, cacheSize, new String[]{bindUsername});
		initialized = true;
		//LOGGER.exit(CLASSNAME, methodName);
	}
	

	public UserInformation[] search(String baseDN, String filter, String[] selectParams, Properties constraints) throws TechnicalException {
		final String methodName = "search(String, String, String[])";
		//LOGGER.enter(CLASSNAME, methodName);

		if(!initialized) { initialize(); }
		if(baseDN == null) { baseDN = getSearchBase(); }
		List results = new ArrayList();
		LDAPConnection connection = null;
		try {
			connection = getConnection();
			connection.connect(hostname, port, bindUsername, bindUserPassword);
			
			LDAPSearchConstraints ldapConstraints = null;
			
			// Set connection constraints
			if(constraints != null && constraints.size() > 0) {
				ldapConstraints = new LDAPSearchConstraints();
				
				// Max limit constraint
				if(constraints.containsKey(UserDirectory.MAX_SEARCH_RESULT)) {
					String value = constraints.getProperty(UserDirectory.MAX_SEARCH_RESULT);
					int limit = 0;
					try {
						if(value != null) {
							limit = Integer.parseInt(value);
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
						//LOGGER.error(CLASSNAME+" Method Name ="+methodName);
						// Consider Max result
					}
					ldapConstraints.setMaxResults(limit);
				}
			}
			
			LDAPSearchResults searchResults = null;
			if(ldapConstraints == null) {
				searchResults = connection.search(baseDN, LDAPv2.SCOPE_SUB, filter, selectParams, false);
			} else {
				searchResults = connection.search(baseDN, LDAPv2.SCOPE_SUB, filter, selectParams, false, ldapConstraints);
			}
			
			LDAPEntry entry = null;
			while(searchResults.hasMoreElements()) {
				try {
					entry = searchResults.next();
					results.add(processEntryResult(entry));
				} catch (LDAPReferralException ldapre) {
					ldapre.printStackTrace();
					String errMsg = MessageFormat.format("Exception while parsing the Corportate Directory result {0}.", new Object[]{entry});
					/*
					 * @description : Exception while searching  the Corporate Directory.
					 * @solution : Please check the LDAP Sever availability.
					 */
					//LOGGER.warn("Exception while parsing the Corportate Directory result");
					/*if(LOGGER.isInfoEnabled()) {
						LDAPUrl refUrls[] = ldapre.getURLs();
						for ( int i=0; i < refUrls.length; i++ ) {
							LOGGER.info( "\t" + refUrls[i].getUrl());				
						}
					}*/
					continue;
				} catch (LDAPException ldape) {
					ldape.printStackTrace();
					String errMsg = MessageFormat.format("Exception while parsing the Corporate Directory result {0}.", new Object[]{entry});
					/*
					 * @description : Exception while searching  the Corporate Directory.
					 * @solution : Please check the LDAP Sever availability.
					 */
					//LOGGER.warn("Exception while parsing the Corporate Directory result");
					continue;
				}
			}
		} catch (LDAPException ldape) {
			ldape.printStackTrace();
			String errMsg = MessageFormat.format("Exception while searching Corporate Directory searchbase {0} with filter {1}.", new Object[]{baseDN, filter});
			/*
			 * @description : Exception while searching  the Corporate Directory.
			 * @solution : Please check the LDAP Sever availability.
			 */
			//LOGGER.error("Exception while searching Corporate Directory searchbase");
			throw new TechnicalException("Exception while searching Corporate Directory searchbase");
		} finally {
			if(connection != null) {
				try {
					connection.disconnect();
				} catch (LDAPException ldape) {
					ldape.printStackTrace();
					/*
					 * @description : Exception while searching  the Corporate Directory.
					 * @solution : Please check the LDAP Sever availability.
					 */
					//LOGGER.warn("Exception while closing LDAP Connection.");
				}
			}
		}
		/*if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Corporate Directory search result for searchbase "+baseDN+" and filter is "+results);
		}*/
		UserInformation[] usersInfo = new UserInformation[results.size()];
		usersInfo = (UserInformation[]) results.toArray(usersInfo);
		//LOGGER.exit(CLASSNAME, methodName);
		return usersInfo;
	}
	

	private UserInformation processEntryResult(LDAPEntry entry) {
		final String methodName = "processEntryResult";
		//LOGGER.enter(CLASSNAME, methodName);
		LDAPAttributeSet attributeSet = entry.getAttributeSet();
		LDAPAttribute eachAttribute = null;
		Properties allAttributes = new Properties();
		for(Enumeration attributes = attributeSet.getAttributes(); attributes.hasMoreElements(); ) {
			eachAttribute = (LDAPAttribute) attributes.nextElement();
			allAttributes.put(eachAttribute.getName(), getAttribteValuesAsString(eachAttribute.getStringValues()));
		}
		UserInformation userInfo = new UserInformation(allAttributes);
		//LOGGER.exit(CLASSNAME, methodName);
		return userInfo;
	}
	

	private String getAttribteValuesAsString(Enumeration values) {
		final String methodName = "getAttribteValuesAsString";
		//LOGGER.enter(CLASSNAME, methodName);
		StringBuffer finalValue = new StringBuffer();
		while(values.hasMoreElements()) {
			finalValue.append(values.nextElement());
			if(values.hasMoreElements()) {
				finalValue.append(VALUE_SEPERATOR);
			}
		}
		//LOGGER.exit(CLASSNAME, methodName);
		return finalValue.toString();
	}


	protected LDAPConnection getConnection() throws LDAPException {
		final String methodName = "getConnection";
		//LOGGER.enter(CLASSNAME, methodName);
		LDAPConnection baseConnection = new LDAPConnection();
		baseConnection.setCache(cache);
		baseConnection.setOption(LDAPv2.REFERRALS, new Boolean(false));
		baseConnection.setOption(LDAPv2.PROTOCOL_VERSION, new Integer(3));
		//LOGGER.exit(CLASSNAME, methodName);
		return baseConnection;
	}
}

