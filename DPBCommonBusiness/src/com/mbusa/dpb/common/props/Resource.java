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

/**
 * TODO Describe about this type
 */
public abstract class Resource {
	
	private String name;
	
	public Resource(String name) {
		if(name == null || "".equals(name)) {
			throw new IllegalArgumentException("Resource name should not be null or empty.");
		}
		this.name = name;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return
	 */
	abstract public long lastModified();
	
	/**
	 * 
	 * @return
	 */
	abstract public boolean isChanged();
	
	/**
	 * 
	 */
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		
		Resource that = (Resource) o;
		return this.name.equals(that.name);
	}

	/**
	 * 
	 */
	public int hashCode() {
		return name.hashCode();
	}
	
	public String toString() {
		return name;
	}
}
