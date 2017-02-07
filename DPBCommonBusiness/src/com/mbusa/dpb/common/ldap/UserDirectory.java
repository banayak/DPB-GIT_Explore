package com.mbusa.dpb.common.ldap;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;

abstract public class UserDirectory {
	
	static final String CLASSNAME = UserDirectory.class.getName();
	
	static final private DPBLog LOGGER = DPBLog.getInstance(UserDirectory.class);
	
	static final public String DEFAULT_SEARCH_BASE = "dc=cd,dc=dcx,dc=com";
	
	static final public String PROP_SEARCH_BASE = "ldap.searchbase";
	
	//	 Organizational data
	static public final String CD_ATTRIB_COUNTRY = "co";
	static public final String CD_ATTRIB_ROLE = "role";
	static public final String CD_ATTRIB_ORGANIZATION_UNIT = "ou";
	
	// Personal Data
	static public final String CD_ATTRIB_USER_ID = "uid";
	static public final String CD_ATTRIB_COMMON_NAME = "cn";
	static public final String CD_ATTRIB_GIVEN_NAME = "gn";
	static public final String CD_ATTRIB_LAST_NAME = "ln";
	static public final String CD_ATTRIB_DCX_MIDDLE_INITIAL = "mi";
	static public final String CD_ATTRIB_TITLE = "title";
	
	// Communication Data
	static public final String CD_ATTRIB_FAX_NUMBER = "fax";
	static public final String CD_ATTRIB_MAIL = "mail";
	static public final String CD_ATTRIB_TELEPHONE_NUMBER = "telephone";
	static public final String CD_ATTRIB_MOBILE = "mobile";
	 
	// Mislenious
	static public final String CD_WILDCARD_ALL = "*";
	static public final String EMPTY_STRING = "";
	
	static public final String[] CD_ALL_ATTRIBUTES = new String[] {
		CD_ATTRIB_COUNTRY, CD_ATTRIB_ROLE, CD_ATTRIB_ORGANIZATION_UNIT, CD_ATTRIB_USER_ID, CD_ATTRIB_COMMON_NAME,
		CD_ATTRIB_GIVEN_NAME, CD_ATTRIB_LAST_NAME, CD_ATTRIB_DCX_MIDDLE_INITIAL, CD_ATTRIB_TITLE, CD_ATTRIB_FAX_NUMBER,
		CD_ATTRIB_MAIL, CD_ATTRIB_TELEPHONE_NUMBER, CD_ATTRIB_MOBILE
	};
	
	static final public String KEY_VALUE_SEPERATOR = "=";
	
	static final public String FILTER_BEGIN_TAG = "(";
	
	static final public String FILTER_END_TAG = ")";
	
	static final public String VALUE_SEPERATOR = ",";
	
	// Search Constraint keys
	
	public static final String MAX_SEARCH_RESULT = "users.search.maxlimit";
	
	static private UserDirectory SINGLETON = null;
	
	public static final String PROP_LDAP_CONFIGURED = "dpb.ldap.configured";
	public static final PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
	public static final boolean isLdapConfigured = PROPERTY_MANAGER.getPropertyAsBoolean(PROP_LDAP_CONFIGURED);
	
	/**
	 * 
	 * @return
	 */
	synchronized static public UserDirectory getUserDirectory() {
		if(SINGLETON == null) {
			if(!isLdapConfigured) {
				SINGLETON = new FileBasedUserDirectory();
			} else {
				SINGLETON = new LDAPUserDirectory();
			}
		}
		return SINGLETON;
	}
	

	static public String prepareFilterString(String attrName, String attrValue) {
		final String methodName = "prepareFilterString(String, String)";
		LOGGER.enter(CLASSNAME, methodName);
		String resultFilter = null;
		if(attrName != null) {
			resultFilter = new StringBuffer()
				.append(FILTER_BEGIN_TAG)		
				.append(attrName)
				.append(KEY_VALUE_SEPERATOR)
				.append((attrValue == null) ? CD_WILDCARD_ALL : attrValue)
				.append(FILTER_END_TAG)
				.toString();
		}
		LOGGER.exit(CLASSNAME, methodName);
		return resultFilter;
	}
	

	static public String prepareFilterString(Properties props) {
		final String methodName = "prepareFilterString(Properties)";
		LOGGER.enter(CLASSNAME, methodName);
		
		String resultFilter = null;
		if(props != null && !props.isEmpty()) {
			StringBuffer filter = new StringBuffer();
			int criteriaSize = props.size();
			if(criteriaSize > 1){
				filter.append(FILTER_BEGIN_TAG);
				filter.append("&");
			}
			Map.Entry entry = null;
			for(Iterator itr = props.entrySet().iterator(); itr.hasNext(); ) {
				entry = (Map.Entry) itr.next();
				filter.append(prepareFilterString((String)entry.getKey(), (String)entry.getValue()));
			}
			if(criteriaSize > 1){
			filter.append(FILTER_END_TAG);
			}
			resultFilter = filter.toString();
		}
		StringBuffer filter = new StringBuffer();
		filter.append(FILTER_BEGIN_TAG);
		LOGGER.exit(CLASSNAME, methodName);
		return resultFilter;
	}
	

	protected UserDirectory() {
	}
	

	public UserInformation getUserInfo(String userId) throws TechnicalException {
		final String methodName = "getUserInfo(String)";
		LOGGER.enter(CLASSNAME, methodName);
		UserInformation userInfo = getUserInfo(userId, CD_ALL_ATTRIBUTES);
		LOGGER.exit(CLASSNAME, methodName);
		return userInfo;
	}
	

	public UserInformation getUserInfoByEmail(String email) throws TechnicalException {
		final String methodName = "getUserInfoByEmail(String)";
		LOGGER.enter(CLASSNAME, methodName);
		UserInformation userInfo = getUserInfoByEmail(email, CD_ALL_ATTRIBUTES);
		LOGGER.exit(CLASSNAME, methodName);
		return userInfo;
	}
	

	public UserInformation getUserInfo(String userId, String[] selectClause) throws TechnicalException {
		final String methodName = "getUserInfo(String, String[])";
		LOGGER.enter(CLASSNAME, methodName);
		
		UserInformation result = null;
		if(userId != null && userId.trim().length() != 0) {
			UserInformation[] usersInfo = search(null, prepareFilterString(CD_ATTRIB_USER_ID, userId), selectClause);
			if(usersInfo != null && usersInfo.length > 0) {
				result = usersInfo[0];
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return result;
	}
	

	public UserInformation getUserInfoByEmail(String email, String[] selectClause) throws TechnicalException {
		final String methodName = "getUserInfoByEmail(String, String[])";
		LOGGER.enter(CLASSNAME, methodName);
		
		UserInformation result = null;
		if(email != null && email.trim().length() != 0) {
			UserInformation[] usersInfo = search(null, prepareFilterString(CD_ATTRIB_MAIL, email), selectClause);
			if(usersInfo != null && usersInfo.length > 0) {
				result = usersInfo[0];
			}
		}
		LOGGER.exit(CLASSNAME, methodName);
		return result;
	}
	

	public UserInformation[] search(String filter, String[] selectClause) throws TechnicalException {
		final String methodName = "search(String, String[])";
		LOGGER.enter(CLASSNAME, methodName);
		UserInformation[] usersInfo = search(null, filter, selectClause);
		LOGGER.exit(CLASSNAME, methodName);
		return usersInfo;
	}
	

	public UserInformation[] search(Properties criterions, String[] selectClause) throws TechnicalException {
		final String methodName = "search(String, String[])";
		LOGGER.enter(CLASSNAME, methodName);
		String filter = prepareFilterString(criterions);
		UserInformation[] usersInfo = search(null, filter, selectClause);
		LOGGER.exit(CLASSNAME, methodName);
		return usersInfo;
	}
	

	public UserInformation[] search(Properties criterions, String[] selectClause, Properties constraints) throws TechnicalException {
		final String methodName = "search(String, String[], Map)";
		LOGGER.enter(CLASSNAME, methodName);
		String filter = prepareFilterString(criterions);
		UserInformation[] usersInfo = search(null, filter, selectClause, constraints);
		LOGGER.exit(CLASSNAME, methodName);
		return usersInfo;
	}
	

	public UserInformation[] search(String baseDN, String filter, String[] selectClause) throws TechnicalException {
		return search(baseDN, filter, selectClause, null);
	}
	

	abstract public UserInformation[] search(String baseDN, String filter, String[] selectClause, Properties constraints) throws TechnicalException;
	
	protected String getSearchBase() {
		return PropertyManager.getPropertyManager().getPropertyAsString(PROP_SEARCH_BASE, DEFAULT_SEARCH_BASE);
	}
}
