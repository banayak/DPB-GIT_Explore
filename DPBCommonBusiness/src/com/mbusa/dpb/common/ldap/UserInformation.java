
package com.mbusa.dpb.common.ldap;


import java.util.Properties;



public class UserInformation {
	
	
	protected Properties properties = null;
	
	public UserInformation() {
		this.properties = new Properties();
	}
	

	public UserInformation (Properties props){
		this();
		if(props != null && !props.isEmpty()) {
			this.properties.putAll(props);
		}
	}
	
	 public String getRole(){ 
			return properties.getProperty(UserDirectory.CD_ATTRIB_ROLE);
		}
	
	public String getFaxNumber(){
		
		return properties.getProperty(UserDirectory.CD_ATTRIB_FAX_NUMBER);
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return properties.getProperty(UserDirectory.CD_ATTRIB_COUNTRY);
	}
	/**
	 * @return the department
	 */
	public String getMobile() {
		return properties.getProperty(UserDirectory.CD_ATTRIB_MOBILE);
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return properties.getProperty(UserDirectory.CD_ATTRIB_MAIL);
	}

	/**
	 * @return the givenName
	 */
	public String getGivenName() {
		return properties.getProperty(UserDirectory.CD_ATTRIB_GIVEN_NAME);
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return properties.getProperty(UserDirectory.CD_ATTRIB_DCX_MIDDLE_INITIAL);
	}

	/**
	 * @return the secondName
	 */
	public String getLastName() {
		return properties.getProperty(UserDirectory.CD_ATTRIB_LAST_NAME);
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return properties.getProperty(UserDirectory.CD_ATTRIB_TELEPHONE_NUMBER);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return properties.getProperty(UserDirectory.CD_ATTRIB_TITLE);
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return properties.getProperty(UserDirectory.CD_ATTRIB_USER_ID);
	}

	/**
	 * @return the commonName
	 */
	public String getCommonName() {
		return properties.getProperty(UserDirectory.CD_ATTRIB_COMMON_NAME);
	}
	
	/**
	 * @return the organizationUnit
	 */
	public String getOrganizationUnit() {
		return properties.getProperty(UserDirectory.CD_ATTRIB_ORGANIZATION_UNIT);
	}

	/**
	 * 
	 * @return
	 */
	public String getFirstName() {
		String givenName = getGivenName();
		return (givenName == null) ? new String().intern() : givenName.trim();
	}

	/**
	 * 
	 * @return
	 */
	public String getFullName() {
		StringBuffer result = new StringBuffer();
		result.append(getFirstName());
		String lastName = getLastName();
		if(lastName != null && !"".equals(lastName.trim())) {
			result.append(" ")
			.append(lastName);
		}
		return result.toString();
	}
	
	
	/**
	 * 
	 * @param name
	 * @param value
	 */
	protected void setProperty(String name, String value) {
		properties.setProperty(name, value);
	}
	
	public String getProperty(String name) {
		return properties.getProperty(name);
	}
	
	/**
	 * 
	 */
	public String toString() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("uid = " + getUserId())
				.append(" : role = " + getRole())
			   .append(" : cn = " + getCommonName())
			   .append(" : gn = "+getFirstName())
			   .append(" : sn = "+getLastName())
			   .append(" : mi = "+getMiddleName())
			   .append(" : mail = "+getEmail())
			   .append(" : title = "+getTitle())
			   .append(" : telephone = "+getTelephone())
			   .append(" : co = "+getCountry())
			   .append(" : ou = "+getOrganizationUnit())
			   .append(" : fax = "+getFaxNumber())
				.append(" : telephone = "+getTelephone())
				.append(" : mobile = "+getMobile());
		return sBuffer.toString();
	}
	
}
