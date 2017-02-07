/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: FileFormatAction.java
 * Program Version			: 1.0
 * Program Description		: This class is used to handle all request related to file format definition.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 14, 2013     	  1.0        First Draft
 * Syntel   Aug 27, 2013     	  1.0        Done changes as per new requirement
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/
package com.mbusa.dpb.web.common.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.ibm.ws.gridcontainer.exceptions.PersistenceException;
import com.mbusa.dpb.business.util.MasterDataLookup;
import com.mbusa.dpb.common.domain.DefStatus;
import com.mbusa.dpb.common.domain.FieldColumnMapBean;
import com.mbusa.dpb.common.domain.FileFormatBean;
import com.mbusa.dpb.common.domain.Kpi;
import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.web.delegate.BusinessDelegate;
import com.mbusa.dpb.web.form.FileFormatForm;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;

public class FileFormatAction extends DPBAction {
	static final private String CLASSNAME = FileFormatAction.class.getName();
	private static DPBLog LOGGER = DPBLog.getInstance(FileFormatAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileFormatForm formatForm;
	private FileFormatBean formatBean;
	private FieldColumnMapBean mapBean;
	private String fileId;
	private String view;
	private String tableName;
	private List<String> columnNames = new ArrayList<String>();
	private List<Kpi> kpList = new ArrayList<Kpi>();
	private String actionForward = IWebConstants.DEFAULT_ERROR_PAGE;
	private String message = "";
	List<DefStatus> defSts = null;
	boolean fromDPBList;
	
	BusinessDelegate bDelegate = new BusinessDelegate();

	public String getFileFormatDetails() {
		final String methodName = "getFileFormatDetails";
		LOGGER.enter(CLASSNAME, methodName);
		try {
		this.setMenuTabFocus(IWebConstants.DEFINITION_ID);
		defSts = MasterDataLookup.getInstance().getDefStatusCodes();
			if (fileId == null && !"view".equalsIgnoreCase(view) && formatForm == null) {
				formatForm = new FileFormatForm();
				formatForm.setFileFormatId(0);
				formatForm.setInbountFileIndicator(IWebConstants.CONSTANT_Y);
				formatForm.setIndHeader(IWebConstants.CONSTANT_N);
				formatForm.setDefStatusCode(IWebConstants.DRAFT);
				actionForward = "fileFormatView";
			} else if (formatForm != null && formatForm.getColumnCount() > 0 && 
					formatForm.getTableName() != null && !"0".equals(formatForm.getTableName())) {
				actionForward = "fileFormatView";
				message = "File column mapping details retrieved successfully.";
				int cCount = formatForm.getColumnCount();
				tableName = formatForm.getTableName();
				if (IWebConstants.TBL_NAM_KPI.equals(tableName)) {
					kpList = (ArrayList<Kpi>) MasterDataLookup.getInstance().getKPIWithOldList();
					formatForm.setKpList(kpList);
				}
				columnNames = MasterDataLookup.getInstance().getColumnNames(tableName);
				formatForm.setColumnNames(columnNames);
				populateSequenceNumberColums(cCount, tableName);
			} else if (fileId != null) {
				actionForward = "fileFormatView";
				fromDPBList = true;
				formatBean = bDelegate.getEditFileFormatDetails(fileId);
				tableName = formatBean.getTableName();
				if (tableName != null && formatBean.getFileMapingList().size() < 0 && !"0".equals(tableName) && !"".equals(tableName)|| 
						tableName != null && !"0".equals(tableName) && !"".equals(tableName) && formatBean.getFileMapingList().size() > 0) {
					if (tableName.trim().equals(IWebConstants.TBL_NAM_KPI)) {
						kpList = (ArrayList<Kpi>) MasterDataLookup.getInstance().getKPIWithOldList();
					}
					columnNames = MasterDataLookup.getInstance().getColumnNames(tableName);
					// populateFileFormatForm(columnNames, kpList);
					formatForm = new FileFormatForm();
					BeanUtils.copyProperties(formatForm, formatBean);
					formatForm.setStatus(formatBean.getDefStatusCode());
					formatForm.setColumnNames(columnNames);
					formatForm.setKpList(kpList);
				}else {
					populateFormatForm();
					message = IWebConstants.FMT_NO_MAP_DETL;
				}
				if (IWebConstants.INACTIVE.equals(formatBean.getDefStatusCode()) || IWebConstants.ACTIVE.equals(formatBean.getDefStatusCode())) {
					formatForm.setFlagActive(true);
				}
				}
				if (!"".equalsIgnoreCase(view) && "view".equalsIgnoreCase(view)) {
					formatForm.setDefStatusCode(WebHelper.getStatusString(formatForm.getDefStatusCode(),defSts));
					formatForm.setInbountFileIndicator(WebHelper.getYesOrNoString(formatForm.getInbountFileIndicator()));
					formatForm.setIndHeader(WebHelper.getYesOrNoString(formatForm.getIndHeader()));
					if(formatForm.getFormatDescription() != null){
					formatForm.setDescription(formatForm.getFormatDescription().split(IWebConstants.DESC_EXPSN));
					}
					actionForward = "fileFormatReadOnlyView";
				}
		} catch(BusinessException be){
			LOGGER.error("BusinessException occured");
			addActionError(IWebConstants.BUSS_EXC_RETV);
		}catch (TechnicalException te) {
			LOGGER.error("TechnicalException occured");
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (PersistenceException pe) {
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			LOGGER.error("Exception occured");
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		addActionMessage(message);
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}

	public String saveFileFormatDetails() {
		final String methodName = "saveFileFormatDetails";
		LOGGER.enter(CLASSNAME, methodName);
		try {
			defSts = MasterDataLookup.getInstance().getDefStatusCodes();
			validateFileFormat();
			actionForward = "fileFormatViewDefine";
			if (!hasActionErrors()) {
				formatBean = new FileFormatBean();
				BeanUtils.copyProperties(formatBean, formatForm);
				formatBean.setCreatedUserId(this.getUserId());
				formatBean.setLastUpdUserId(this.getUserId());
				if(formatBean.getFileFormatId() > 0){
					message = "File Format updated successfully.";
				}else{
					message = "File Format created successfully.";
				}
				tableName =formatForm.getTableName();
				if("0".equals(tableName) && formatForm.getFileMapingList().size() > 0){
					formatForm.getFileMapingList().removeAll(formatForm.getFileMapingList());
				}
				if (tableName != null && !"0".equals(tableName) && !"".equals(tableName)) {
					if (tableName.trim().equals(IWebConstants.TBL_NAM_KPI)) {
						kpList = (ArrayList<Kpi>) MasterDataLookup.getInstance().getKPIWithOldList();
					}
					columnNames = MasterDataLookup.getInstance().getColumnNames(tableName);
					// populateFileFormatForm(columnNames, kpList);
					formatForm.setColumnNames(columnNames);
					formatForm.setKpList(kpList);
					formatBean = bDelegate.saveFileFormatDetails(formatBean);
					}else{
						formatBean = bDelegate.saveFileFormatDetails(formatBean);
					}
					if (IWebConstants.INACTIVE.equals(formatBean.getDefStatusCode()) || IWebConstants.ACTIVE.equals(formatBean.getDefStatusCode())) {
						formatForm.setFlagActive(true);
					}
					addActionMessage(message);
					/*formatBean = bDelegate.saveFileFormatDetails(formatBean);*/
					BeanUtils.copyProperties(formatForm, formatBean);
					formatForm.setStatus(formatBean.getDefStatusCode());
				}
				
		} catch(BusinessException be){
			LOGGER.error("BusinessException occured");
			if("msg".equals(be.getMessageKey())){
				addActionError(be.getMessage());
				formatForm.setDefStatusCode("A");
			}else{
				addActionError(IWebConstants.BUSS_EXC_SAVE);
			}
		}catch (TechnicalException te) {
			LOGGER.error("TechnicalException occured");
			request.setAttribute (IWebConstants.jspExe, te);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch (PersistenceException pe) {
			LOGGER.error("PersistenceException occured");
			request.setAttribute (IWebConstants.jspExe, pe);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}catch(Exception e){
			LOGGER.error("Exception occured");
			request.setAttribute (IWebConstants.jspExe, e);
			actionForward =  IWebConstants.DPB_ERROR_URL;
		}
		LOGGER.exit(CLASSNAME, methodName);
		return actionForward;
	}

	private FileFormatForm populateSequenceNumberColums(int cCount, String tableName) throws BusinessException, TechnicalException {
		final String methodName = "populateSequenceNumberColums";
		LOGGER.enter(CLASSNAME, methodName);
		List<FieldColumnMapBean> fileMapingList = new ArrayList<FieldColumnMapBean>();
		for (int cnt = 1; cnt <= cCount; cnt++) {
			mapBean = new FieldColumnMapBean();
			mapBean.setFileColumnSeqNumber(cnt);
			mapBean.setTableName(tableName);
			fileMapingList.add(mapBean);
		}
		formatForm.setFileMapingList(fileMapingList);
		LOGGER.exit(CLASSNAME, methodName);
		return formatForm;
	}

public void	populateFormatForm(){
		formatForm = new FileFormatForm();
		formatForm.setFileFormatId(formatBean.getFileFormatId());
		formatForm.setFormatName(formatBean.getFormatName());
		formatForm.setFormatDescription(formatBean.getFormatDescription());
		formatForm.setInbountFileIndicator(formatBean.getInbountFileIndicator());
		formatForm.setIndHeader(formatBean.getIndHeader());
		formatForm.setIndDelimited(formatBean.getIndDelimited());
		formatForm.setFixedLineWidth(formatBean.getFixedLineWidth());
		formatForm.setColumnCount(formatBean.getColumnCount());
		formatForm.setTableName(formatBean.getTableName());
		formatForm.setDefStatusCode(formatBean.getDefStatusCode());
	}

	public void validateFileFormat() {
		if (IWebConstants.INACTIVE.equals(formatForm.getDefStatusCode()) && formatForm.getFileFormatId() <= 0) {
			addActionError("Cannot create a new Inactive File Format.");
		}
		else if(IWebConstants.ACTIVE.equalsIgnoreCase(formatForm.getDefStatusCode()) && formatForm.isFlagActive() && formatForm.getFileFormatId() > 0){
			addActionError("Cannot re-submit an Active File Format.");
		}
		else if(IWebConstants.ACTIVE.equalsIgnoreCase(formatForm.getDefStatusCode()) && formatForm.getTableName() == "0" || IWebConstants.ACTIVE.equalsIgnoreCase(formatForm.getDefStatusCode()) && formatForm.getColumnCount() == 0){
			addActionError("Enter valid No of columns (number) and select table name.");
		}
		else if("".equals(formatForm.getFormatName()) || formatForm.getFormatName().length() > 40){
			addActionError("Format Name should contain 40 characters or less and should be alphanumeric with spaces.");
		}
		else if(formatForm.getFormatDescription().length() > 255){
			addActionError("Description should be less than 255 characters.");
		}
		else if(IWebConstants.ACTIVE.equalsIgnoreCase(formatForm.getDefStatusCode()) && formatForm.getIndDelimited().length() > 1){
			addActionError("Enter single character Delimiter.");
		}
	}

	public FileFormatForm getFormatForm() {
		return formatForm;
	}

	public void setFormatForm(FileFormatForm formatForm) {
		this.formatForm = formatForm;
	}
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	/**
	 * @return the defSts
	 */
	public List<DefStatus> getDefSts() {
		return defSts;
	}

	/**
	 * @param defSts the defSts to set
	 */
	public void setDefSts(List<DefStatus> defSts) {
		this.defSts = defSts;
	}

	public boolean isFromDPBList() {
		return fromDPBList;
	}

	public void setFromDPBList(boolean fromDPBList) {
		this.fromDPBList = fromDPBList;
	}
	
}