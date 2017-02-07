/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: DPBAction.java
 * Program Version			: 1.0
 * Program Description		: This class Class will be parent class for all action classes  
 * 							  and handle common functionality like setting menu and session.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   July 12, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/

package com.mbusa.dpb.web.common.actions;

import java.text.MessageFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.mbusa.dpb.web.helper.UserInformation;
//import com.mbusa.dpb.common.ldap.UserInformation;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;
import com.opensymphony.xwork2.ActionSupport;


public class DPBAction extends ActionSupport implements   SessionAware, ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 1L;
	public HttpServletRequest request = ServletActionContext.getRequest();
	public HttpServletResponse response = ServletActionContext.getResponse();
	
	public String navigateToHomePage(){		
		return IWebConstants.HOME_PAGE;
	}
	/**

	* @return the userId

	*/

	public String getUserId() {
		HttpSession session = request.getSession();
		String userId = IWebConstants.DEFAULT_USER_ID;
		UserInformation userInfo = (UserInformation) session.getAttribute(IWebConstants.USER_INFO_SESSION);
		userId = (String) session.getAttribute(IWebConstants.SM_USER_SESSION);
		if(WebHelper.isEmptyOrNullString(userId) && userInfo!= null && WebHelper.isEmptyOrNullString(userInfo.getUserId())){
			userId = userInfo.getUserId();
		}
		return userId;
	} 
	public void setMenuTabFocus(int menuNo)
	{	
		Object  objD[]= setMenuFlag(menuNo);		
		HttpSession session = request.getSession();
		String displayTree = (String) session.getAttribute(IWebConstants.MENU_ITEMS);
		StringBuffer strBuffer = new StringBuffer(displayTree);
		String formattedStr = IWebConstants.EMPTY_STR;
		formattedStr = MessageFormat.format(strBuffer.toString(), objD);
		session.setAttribute(IWebConstants.TREE_DISPLAY,formattedStr);
		
	}
	private Object[] setMenuFlag(int menuNo)
	{		
		Object objD[]= null;
		switch (menuNo) {		
			case 1:
					objD = new Object[] {"on","off","off","off","off"};
					break;	
			case 2:
					objD = new Object[] {"off","on","off","off","off"};
				 	break;
			case 3:
					objD = new Object[] {"off","off","on","off","off"};
					break;			
			case 4:
					objD = new Object[] {"off","off","off","on","off"};
					break;
			case 5:
					objD = new Object[] {"off","off","off","off","on"};
					break;
			default :
					objD = new Object[] {"on","off","off","off","off"};
				break;
		}	
		return objD;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}
	

	public String getUserRole() {
		HttpSession session = request.getSession();
		String userRole = "";
		UserInformation userInfo = (UserInformation) session.getAttribute(IWebConstants.USER_INFO_SESSION);
		userRole = (String) session.getAttribute(IWebConstants.USER_ROLE);
		if(WebHelper.isEmptyOrNullString(userRole) && userInfo!= null && WebHelper.isEmptyOrNullString(userInfo.getRole())){
			userRole = userInfo.getRole();
		}
		return userRole;
	}
	/*
	1	REPORT USER                                  
	2	NORMAL USER                             
	3	ADMIN                                   
	4	BUSINESS USER
	5   ACCOUNTING USER
	public static final String ADMIN_ROLE_ACCESS ="A";
	public static final String FINANCE_ROLE_ACCESS ="F";
	public static final String VIEWONLY_ROLE_ACCESS ="V";
	*/
	public String getAccessType(){
		String access = IWebConstants.EMPTY_STR;
		String userRole =getUserRole();
		if(userRole!= null && userRole.trim().length() > 0){
			if(IWebConstants.REPORT_USER_ROLE_CODE.equalsIgnoreCase(userRole)){//admin				
				access = IWebConstants.VIEWONLY_ROLE_ACCESS;
			}else if(IWebConstants.NORMAL_USER_ROLE_CODE.equalsIgnoreCase(userRole)){//SRD
				access = IWebConstants.TREASURY_ROLE_ACCESS;
			}else  if(IWebConstants.ADMIN_ROLE_CODE.equalsIgnoreCase(userRole)){	//treasury TRSRY{
				access = IWebConstants.ADMIN_ROLE_ACCESS;
			}else  if(IWebConstants.BUSINESS_USER_ROLE_CODE.equalsIgnoreCase(userRole)){//Accout
				access = IWebConstants.ADMIN_ROLE_ACCESS;
			}else{
				access = IWebConstants.VIEWONLY_ROLE_ACCESS;
			}
		}		
		return access;
	}
}
