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

import java.io.File;

public class FileResource extends Resource {

	private File file = null;

	private long lastModified = 0;

	public FileResource(String name) {
		this(new File(name));
	}

	public FileResource(File file) {
		super(file.getName());
		if (!file.exists()) {
			throw new IllegalArgumentException("File " + file
					+ " is not existed.");
		}
		this.file = file;
		lastModified = file.lastModified();
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return file.getAbsolutePath() + File.separator + file.getName();
	}

	/**
	 * 
	 */
	public boolean isChanged() {
		if (lastModified != file.lastModified()) {
			lastModified = file.lastModified();
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		FileResource that = (FileResource) o;
		return this.file.equals(that.file);
	}

	/**
	 * 
	 */
	public int hashCode() {
		return 30 * file.hashCode();
	}

	/**
	 * 
	 */
	public long lastModified() {
		return lastModified;
	}

}
