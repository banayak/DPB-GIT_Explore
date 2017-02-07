/**
 * 
 */
package com.mbusa.dpb.common.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * @author RK5005820
 *
 */
public class FileDetailBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getProcessTime() {
		return processTime;
	}
	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	private String fileName;
	private Date processTime;
	private String status;
	}
