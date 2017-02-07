/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: LoginAction.java
 * Program Version			: 1.0
 * Program Description		: This class is first action called once user successfully login into DPB application.
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.mbusa.dpb.common.exceptions.BusinessException;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.helper.MenuNode;
import com.mbusa.dpb.common.helper.Tree;
//import com.mbusa.dpb.common.ldap.UserInformation;
import com.mbusa.dpb.web.helper.UserInformation;
import com.mbusa.dpb.web.delegate.CommonDelegate;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.mbusa.dpb.web.helper.WebHelper;
import com.mbusa.dpb.common.props.PropertyManager;

public class LoginAction extends DPBAction{
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	protected HttpServletRequest request;    
	protected HttpServletResponse response;
	private String setMenu="dash_board";
	private CommonDelegate cDelegate = new CommonDelegate();
	private String actionForward = IWebConstants.DPB_ERROR_URL;
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String contextPath = request.getContextPath();
		request.setAttribute("Dashboard",setMenu);	
		HttpSession session=request.getSession(true);
		PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
		boolean isDevelopment = PROPERTY_MANAGER.getPropertyAsBoolean("dpb.development");
		UserInformation user = (UserInformation) session.getAttribute(IWebConstants.USER_INFO_SESSION);
		
		actionForward  = "success";
		try{
			if(!isDevelopment) {
			String userId = (String) session.getAttribute("UserID");
			String costCenter = (String) session.getAttribute("CostCenter");	
			//String uRole = WebHelper.decideRole(user,sessiondept);
			String uRole = WebHelper.getUserRole(userId, costCenter);
			session.setAttribute(IWebConstants.USER_ROLE, uRole);
				if(uRole.equals("-1"))
				{
					actionForward =  IWebConstants.DPB_ERROR_URL;
					request.setAttribute (IWebConstants.jspExe, new BusinessException("DPB.LOGIN.0","Please contact DPB application technical support team for application access."));
				}
				else{
			List<MenuNode> menuList = new ArrayList<MenuNode>();
			List<String> actionList=new ArrayList<String>();
			//menuList = cDelegate.getMenuItems("A");
			menuList = cDelegate.getMenuItems(uRole);
			for(MenuNode item: menuList)
			{
				actionList.add(item.getMethodName().trim());
			}
			session.setAttribute("actionList", actionList);			
			Tree tree =  WebHelper.generateTreeMenu(menuList);
			String displayTree = tree.displayTree(tree,"",contextPath);			
			if(!WebHelper.isEmptyOrNullString(displayTree))
			{
				session.setAttribute(IWebConstants.MENU_ITEMS, displayTree);
				session.setAttribute(IWebConstants.TREE_DISPLAY,setMenuTabFocus(displayTree));
				session.setAttribute("dashboard","dash_board");	
			}else
			{
				actionForward =  IWebConstants.DPB_ERROR_URL;
				request.setAttribute (IWebConstants.jspExe, new BusinessException("DPB.LOGIN.01","Please contact DPB application technical support team for application access."));
			}
				}
			} else {
				String uRole = user.getRole();
				if(uRole.equals("-1"))
				{
					actionForward =  IWebConstants.DPB_ERROR_URL;
					request.setAttribute (IWebConstants.jspExe, new BusinessException("DPB.LOGIN.00","Please contact DPB application technical support team for application access."));
				}
				else {
				List<MenuNode> menuList = new ArrayList<MenuNode>();
				List<String> actionList=new ArrayList<String>();
				menuList = cDelegate.getMenuItems(uRole);
				session.setAttribute(IWebConstants.USER_ROLE, uRole);				
				for(MenuNode item: menuList)
				{
					actionList.add(item.getMethodName().trim());
				}
				session.setAttribute("actionList", actionList);
				Tree tree =  WebHelper.generateTreeMenu(menuList);
				String displayTree = tree.displayTree(tree,"",contextPath);
				if(!WebHelper.isEmptyOrNullString(displayTree))
				{
					session.setAttribute(IWebConstants.MENU_ITEMS, displayTree);
					session.setAttribute(IWebConstants.TREE_DISPLAY,setMenuTabFocus(displayTree));
					session.setAttribute("dashboard","dash_board");	
				}else
				{
					actionForward =  IWebConstants.DPB_ERROR_URL;
					request.setAttribute (IWebConstants.jspExe, new BusinessException("DPB.LOGIN.02","Please contact DPB application technical support team for application access."));
				}
			}
			}
		}catch(BusinessException be){
			actionForward = IWebConstants.DPB_ERROR_URL;
			request.setAttribute (IWebConstants.jspExe, new BusinessException("DPB.LOGIN.03","Please contact DPB application technical support team for application access."));
		}catch (TechnicalException te) {
			actionForward = IWebConstants.DPB_ERROR_URL ;
			request.setAttribute (IWebConstants.jspExe, new BusinessException("DPB.LOGIN.04","Please contact DPB application technical support team for application access."));
		}					
		return actionForward;
	}
	
	public String setMenuTabFocus(String displayTree)
	{	
		String formattedStr = "";
		Object  objD[] = {"on","off","off","off","off"};	
		StringBuffer bf = new StringBuffer(displayTree);
		formattedStr = MessageFormat.format(bf.toString(), objD);

		return formattedStr;
		
	}
	
	public String logout() {		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "loginPage";				
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		
		
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}


