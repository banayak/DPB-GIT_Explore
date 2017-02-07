/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: RetailDateChangeAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used for retail end date change in the day federated 
 * 							  table, depending on the end date change the relevant processes also gets postponed. 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Feb 19, 2014     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.common.actions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import com.mbusa.dpb.common.constant.IConstants;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.web.helper.IWebConstants;

public class FileCopyAction extends DPBAction {

	private static final long serialVersionUID = 1L;
	static final private String CLASSNAME = FileCopyAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(ReportsAction.class);
	String actionForward = "";
	private static String pathSeparator = System.getProperty("file.separator");

	String fileName;
	String deleteFilePath;
	String outPath = IConstants.EMPTY_STR;
	String stagePath = IConstants.EMPTY_STR;
	String filePath = IConstants.EMPTY_STR;
	File uploadFile;
	String uploadFileContentType;
	String uploadFileFileName;
	String serverPath;
	String deleteFileName;
	String submitButtonValue;	
	ArrayList<String> filePathsArray; 
	ArrayList<String> deleteFilePathsArray;

	

	static PropertyManager PROPERTY_MANAGER = PropertyManager
			.getPropertyManager();

	public String previewPage() {
		final String methodName = "previewPage";
		LOGGER.enter(CLASSNAME, methodName);
		this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
		this.setFilePathCombo();
		String actionForward = "filecopyView";
		return actionForward;
	}

	public String submit() {
		final String methodName = "previewPage";
		LOGGER.enter(CLASSNAME, methodName);
		this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
		this.setFilePathCombo();
		String actionForward = "filecopyView";
		stagePath = PropertyManager.getPropertyManager().getPropertyAsString(
				"payment.path2")
				+ fileName;
		outPath = PropertyManager.getPropertyManager().getPropertyAsString(
				"payment.path");
		System.out.println(" stagePath" + stagePath);
		System.out.println(" outPath" + outPath);
		System.out.println(" fileName" + fileName);
		if (fileName != null) {
			moveFileTo(stagePath, outPath, fileName);
		}

		return actionForward;
	}
	public void setFilePathCombo(){
		
		try {
			PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
			String filePathsFromProperty = PROPERTY_MANAGER.getPropertyAsString("dpb.uploadfilePaths");
			ArrayList<String> filePathList = new ArrayList<String>(Arrays.asList(filePathsFromProperty.split(",")));		
			this.setFilePathsArray(filePathList);
			filePathsFromProperty = PROPERTY_MANAGER.getPropertyAsString("dpb.deletefilePaths");
			filePathList = new ArrayList<String>(Arrays.asList(filePathsFromProperty.split(",")));
			this.setDeleteFilePathsArray(filePathList);
		} catch (Exception e) {
			LOGGER.error("File paths not found");
		}
	}
	// File delete functionality - DPB Enhancement
	public String deleteFile() {
		final String methodName = "previewPage";
		LOGGER.enter(CLASSNAME, methodName);		
		this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
		this.setFilePathCombo();
		String actionForward = "filecopyView";
		System.out.println("deleteFilePath:" + getDeleteFilePath());
		boolean isDeleted = false;
		if (getDeleteFilePath() != null && getDeleteFileName() != null && !getDeleteFileName().trim().equals("")) {
				
			isDeleted=deleteFile(getDeleteFilePath(),getDeleteFileName());
		}
		if(isDeleted){
			addActionMessage("File Deleted Successfully!");
			return actionForward;
		}else{
			addActionMessage("Invalid delete file path!");
			return "input";
		}
		
	}

	// File copy functionality - DPB Enhancement
	public String fileCopy() {
		final String methodName = "previewPage";
		LOGGER.enter(CLASSNAME, methodName);		
		this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
		this.setFilePathCombo();
		String actionForward = null;
		
		File fileToCreate = new File(getServerPath(), uploadFileFileName);
		File filePath = new File(getServerPath());
		if(filePath.exists()){
			try {
				FileUtils.copyFile(this.uploadFile, fileToCreate);
				addActionMessage("File Copied Successfully!");
				actionForward = "filecopyView";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			addActionMessage("Invalid server path!");
			actionForward = "input";
		}
		
		return actionForward;
	}
	
	//added for field validations - DPB Enhancement
	public void validate() {
		
		if(this.submitButtonValue != null && this.submitButtonValue.equals("Upload")){
			System.out.println("Upload Clicked.........");
			if(uploadFile==null || uploadFile.getName().length()<0){
				addFieldError("uploadFile", "File name required!");
			}
			if(serverPath==null || serverPath.equals("") || serverPath.trim().length()<0){
				addFieldError("serverPath", "Path name required!");
			}
						
		}else if(this.submitButtonValue != null && this.submitButtonValue.equals("Delete")){
			if(deleteFilePath==null || deleteFilePath.equals("") || deleteFilePath.trim().length()<0){
				addFieldError("deleteFilePath", "Path name required!");
			}
			if(deleteFileName==null || deleteFileName.equals("") || deleteFileName.trim().length()<0){
				addFieldError("deleteFileName", "File name required!");
			}
		}
	}

	public boolean moveFileTo(String fromPath, String toPath, String newFileName) {

		final String methodName = "moveFileTo";
		LOGGER.enter(CLASSNAME, methodName);

		boolean success = false;

		File fromFile = new File(fromPath);
		File toFile = new File(toPath);
		try {
			if (fromFile.exists()) {
				success = copyFileTo(fromPath, toPath, true, newFileName);
				if (success) {
					System.out.println("Copied successfully!");
					if (!fromFile.delete()) {
						success = fromFile.renameTo(new File(fromFile
								.getAbsolutePath() + ".deleted"));

					}
				}
				if (!success) {
				}

			}
		} catch (Exception e) {
			LOGGER.error("While Moving File Error Occured");
		}
		LOGGER.exit(CLASSNAME, methodName);
		return success;
	}	
	// added for file delete functionality - Amit
	public boolean deleteFile(String fromPath,String fileNM) {

		final String methodName = "moveFileTo";
		LOGGER.enter(CLASSNAME, methodName);
		boolean success = false;
		if(fileNM.trim().equals("*")){
			File fromFile = new File(fromPath);
			try {
				if (fromFile.exists()) {
					FileUtils.cleanDirectory(fromFile);
					success = true;
				}
				
			} catch (IOException e) {				
				LOGGER.error("While deleting File Error Occured");
			}
		}else{
			String deletePath =fromPath+"/"+fileNM;	
			File fromFile = new File(deletePath);
			try {
				if (fromFile.exists()) {
					success = fromFile.delete();
				}
			} catch (Exception e) {
				LOGGER.error("While deleting File Error Occured");
			}
		}
		
		LOGGER.exit(CLASSNAME, methodName);
		return success;
	}

	public boolean copyFileTo(String sourcePath, String targetPath,
			boolean overWrite, String newFileName) {

		final String methodName = "copyFileTo";
		LOGGER.enter(CLASSNAME, methodName);
		try {
			if (sourcePath == null || sourcePath.length() < 1)
				throw (new IllegalArgumentException("sourcePath"));

			if (targetPath == null || targetPath.length() < 1)
				throw (new IllegalArgumentException("targetPath"));

			File sourceFile = new File(sourcePath);

			if (!sourceFile.exists())
				throw (new FileNotFoundException(sourcePath));

			if (!sourceFile.isFile())
				throw (new UnsupportedOperationException(
						"Directory copy is not supported"));

			File targetFile = new File(targetPath);

			if (newFileName != null && newFileName.length() > 0) {
				if (targetFile.isDirectory())
					targetFile = new File(targetPath + pathSeparator
							+ newFileName);

			} else {
				if (targetFile.isDirectory())
					targetFile = new File(targetPath + pathSeparator
							+ sourceFile.getName());
			}
			if (targetFile.exists() && !overWrite)
				return false;

			BufferedInputStream inputStream = new BufferedInputStream(
					new FileInputStream(sourceFile));
			BufferedOutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(targetFile));

			byte[] buffer = new byte[32000];
			int readCount;

			while ((readCount = inputStream.read(buffer)) > -1)
				outputStream.write(buffer, 0, readCount);

			inputStream.close();
			outputStream.close();

		} catch (Exception e) {
			LOGGER.error("Error Occured While Copying File");
		}
		LOGGER.exit(CLASSNAME, methodName);
		return true;
	}

	public String getDeleteFilePath() {
		return deleteFilePath;
	}

	public void setDeleteFilePath(String deleteFilePath) {
		this.deleteFilePath = deleteFilePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String getSubmitButtonValue() {
		return submitButtonValue;
	}

	public void setSubmitButtonValue(String submitButtonValue) {
		this.submitButtonValue = submitButtonValue;
	}
	public ArrayList<String> getFilePathsArray() {
		return filePathsArray;
	}

	public void setFilePathsArray(ArrayList<String> filePathsArray) {		
		this.filePathsArray = filePathsArray;
	}
	
	public String getDeleteFileName() {
		return deleteFileName;
	}

	public void setDeleteFileName(String deleteFileName) {
		this.deleteFileName = deleteFileName;
	}

	public ArrayList<String> getDeleteFilePathsArray() {
		return deleteFilePathsArray;
	}

	public void setDeleteFilePathsArray(ArrayList<String> deleteFilePathsArray) {
		this.deleteFilePathsArray = deleteFilePathsArray;
	}
	
}