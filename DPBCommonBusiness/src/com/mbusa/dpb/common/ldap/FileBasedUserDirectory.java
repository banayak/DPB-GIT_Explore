
package com.mbusa.dpb.common.ldap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.props.PropertyManager;



public class FileBasedUserDirectory extends UserDirectory {
	
	static final String CLASSNAME = FileBasedUserDirectory.class.getName();
	
	//static final private DPBLog //LOGGER = DPBLog.getInstance(FileBasedUserDirectory.class);
	
	static final private Pattern SINGL_FILTER_PATTERN = Pattern.compile("[^=\\(]*=[^=\\)]*");
	
	static final private String PROP_USERS_CATEGORY = "dpb.userinformation";
	
	static final private String WILD_CARD = "\\*";
	
	static final private String REGEX_WILE_CARD = ".*";
	

	static protected class FileUserInformation extends UserInformation {

		private static final long serialVersionUID = 1L;

		public FileUserInformation() {
			super();
		}
		

		public FileUserInformation(Properties props) {
			super(props);
		}
		

		public boolean matches(Properties props) {
			boolean matched = true;
			Map.Entry eachEntry = null;
			for(Iterator itr = props.entrySet().iterator(); itr.hasNext(); ) {
				eachEntry = (Entry) itr.next();
				String actualValue = properties.getProperty((String)eachEntry.getKey());
				String matchString = (String) eachEntry.getValue();
				matchString = matchString.replaceAll(WILD_CARD, REGEX_WILE_CARD);
				if(matchString != null) {
					matchString = matchString.toLowerCase();
				}
				if(actualValue != null) {
					actualValue = actualValue.toLowerCase();
				}
				matched = Pattern.matches(matchString, actualValue);
				if(!matched) {
					break;
				}
			}
			return matched;
		}
	}
	
	private Map idToUserMap = null;
	
	private boolean initialized = false;
	
	/**
	 * 
	 *
	 */
	private void initialize() {
		final String methodName = "initialize";
		//LOGGER.enter(CLASSNAME, methodName);
		
		idToUserMap = new HashMap();
		Properties userdata = PropertyManager.getPropertyManager("dpb-ldap").getProperties(PROP_USERS_CATEGORY);
		if(userdata != null && !userdata.isEmpty()) {
			Map.Entry eachProp = null;
			for(Iterator itr = userdata.entrySet().iterator(); itr.hasNext(); ) {
				eachProp = (Entry) itr.next();
				setProperty((String)eachProp.getKey(), (String)eachProp.getValue());
			}
		} else {
			//LOGGER.warn("User data empty");
		}
		initialized = true;
		//LOGGER.exit(CLASSNAME, methodName);
	}
	

	private void setProperty(String name, String value) {
		String userId = name.substring(PROP_USERS_CATEGORY.length() + 1, name.lastIndexOf('.'));
		String relativeProperty = name.substring(name.lastIndexOf('.') + 1);
		
		FileUserInformation userInfo = (FileUserInformation) idToUserMap.get(userId);
		if(userInfo == null) {
			userInfo = new FileUserInformation();
			userInfo.setProperty(UserDirectory.CD_ATTRIB_USER_ID, userId);
			idToUserMap.put(userId, userInfo);
		}
		userInfo.setProperty(relativeProperty, value);
	}


	public UserInformation[] search(String baseDN, String filter, String[] selectClause, Properties constraints) throws TechnicalException {
		final String methodName = "search";
		//LOGGER.enter(CLASSNAME, methodName);
		
		if(!initialized) {
			initialize();
		}
		
		int limit = -1;
		if(constraints != null && constraints.size() > 0) {
			String maxResultValue = constraints.getProperty(UserDirectory.MAX_SEARCH_RESULT);
			try {
				if(maxResultValue != null) {
					limit = Integer.parseInt(maxResultValue);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				//LOGGER.error(CLASSNAME+" Method Name ="+methodName,e);
				// Consider Max Result
			}
		}
		
		Properties props = compileFilter(filter);
		Set matchedUsers = new HashSet();
		FileUserInformation fileUser = null;
		for(Iterator itr = idToUserMap.values().iterator(); itr.hasNext(); ) {
			fileUser = (FileUserInformation) itr.next();
			if(fileUser.matches(props)) {
				matchedUsers.add(fileUser);
			}
			
			if(limit != -1 && matchedUsers.size() == limit) {
				break;
			}
		}
		
		UserInformation[] results = new UserInformation[matchedUsers.size()];
		results = (UserInformation[]) matchedUsers.toArray(results);
		//LOGGER.exit(CLASSNAME, methodName);
		return results;
	}

	/**
	 * 
	 * @return
	 */
	protected Properties compileFilter(String filter) {
		final String methodName = "search";
		//LOGGER.enter(CLASSNAME, methodName);
		
		Properties props = new Properties();
		Matcher matcher = SINGL_FILTER_PATTERN.matcher(filter);
		String eachCondition = null;
		if(matcher != null) {
			while(matcher.find()) {
				eachCondition = filter.substring(matcher.start(), matcher.end());
				StringTokenizer tokens = new StringTokenizer(eachCondition, UserDirectory.KEY_VALUE_SEPERATOR);
				props.put(tokens.nextToken(), tokens.nextToken());
			}
		}
		
		//LOGGER.exit(CLASSNAME, methodName);
		return props;
	}
}
