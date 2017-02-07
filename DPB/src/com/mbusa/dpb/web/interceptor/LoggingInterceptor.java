package com.mbusa.dpb.web.interceptor;
/*
import com.mbusa.dpb.common.logger.DPBLog;*/
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.ibm.websphere.security.UserRegistry;
import com.ibm.wsspi.security.registry.RegistryHelper;
import com.mbusa.dpb.common.ldap.UserDirectory;
import com.mbusa.dpb.web.helper.UserInformation;
//import com.mbusa.dpb.common.ldap.UserInformation;
import com.mbusa.dpb.common.props.PropertyManager;
import com.mbusa.dpb.web.helper.IWebConstants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoggingInterceptor implements Interceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private static DPBLog logger = DPBLog.getInstance(LoggingInterceptor.class);
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
				//logger.enter(invocation.getAction().getClass().getName(), invocation.getProxy().getMethod());
				PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
				boolean isDevelopment = PROPERTY_MANAGER.getPropertyAsBoolean("dpb.development");	
				String dept = PROPERTY_MANAGER.getPropertyAsString("dpb.department");
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpSession session = request.getSession();
				String userIdFromSSO = null;
				UserInformation user = null;
				if (!isDevelopment) {
				userIdFromSSO = session.getAttribute("UserID") == null ? "" : (String) session.getAttribute("UserID");
				String CostCenter = session.getAttribute("CostCenter") == null ? "" : (String) session.getAttribute("CostCenter");		
				if (userIdFromSSO.equals("")) {
					return "loginOnSessionExpire";
		        }
				else{
					session.setAttribute(IWebConstants.SM_USER_SESSION, userIdFromSSO);
					//session.setAttribute(IWebConstants.USER_DEPT_SESSION, CostCenter);
				}
				} else {
					if (!isDevelopment && userIdFromSSO == null) {
						return "loginwithoutSSO";
					} else {
						// Development
						if(isDevelopment && userIdFromSSO == null && request.getParameter("username") != null) {
							try {
								//UserRegistry userRegistry = RegistryHelper.getUserRegistry("defaultWIMFileBasedRealm");
								//userIdFromSSO = userRegistry.checkPassword(request.getParameter("username"), request.getParameter("password"));
						        	String login = request.getParameter("username");
								if(null != login ){
									if(login.equalsIgnoreCase("admin")){
										userIdFromSSO = "ADMIN";
									}
									else if(login.equalsIgnoreCase("reports")){
										userIdFromSSO = "REPORTS";
									}
									else if(login.equalsIgnoreCase("accounting")){
										userIdFromSSO = "ACCOUNTING";
									}
									else if(login.equalsIgnoreCase("srd")){
										userIdFromSSO = "SRD";
									}
									else if(login.equalsIgnoreCase("treasury")){
										userIdFromSSO = "TREASURY";
									}
								}
								else{
									return "loginwithoutSSO";
								}
	
                         } catch (Exception e) {
								return "loginwithoutSSO";
							}
							session.setAttribute(IWebConstants.SM_USER_SESSION, userIdFromSSO);
						} else if (session.getAttribute(IWebConstants.SM_USER_SESSION) == null && request.getParameter("username") == null) {
							return "loginwithoutSSO";
						}
					}
					if(session.getAttribute(IWebConstants.USER_INFO_SESSION) == null) {
						//user = UserDirectory.getUserDirectory().getUserInfo(userIdFromSSO);
                         if(userIdFromSSO.equalsIgnoreCase("admin")){
							user = new UserInformation();
							user.setUserId("ADMIN");
							user.setUserName("ADMIN");
							user.setRole("3");
							dept = "3";
						}
						else if(userIdFromSSO.equalsIgnoreCase("reports")){
							user = new UserInformation();
							user.setUserId("REPORT_USER");
							user.setUserName("REPORT USER");
							user.setRole("1");
							dept = "1";
						}
						else if(userIdFromSSO.equalsIgnoreCase("treasury")){
							user = new UserInformation();
							user.setUserId("TREASURY_USER");
							user.setUserName("TREASURY USER");
							user.setRole("2");
							dept = "2";
						}
						else if(userIdFromSSO.equalsIgnoreCase("srd")){
							user = new UserInformation();
							user.setUserId("BUSINESS_USER");
							user.setUserName("BUSINESS USER");
							user.setRole("4");
							dept = "4";
						}
						else if(userIdFromSSO.equalsIgnoreCase("accounting")){
							user = new UserInformation();
							user.setUserId("ACCOUNTING_USER");
							user.setUserName("ACCOUNTING USER");
							user.setRole("5");
							dept = "5";
						}
						session.setAttribute(IWebConstants.USER_INFO_SESSION, user);
                                                session.setAttribute(IWebConstants.USER_DEPT_SESSION, dept);						
					}
					
				}	
				
				//logger.exit(invocation.getAction().getClass().getName(), invocation.getProxy().getMethod());
				return invocation.invoke();
	}
}
