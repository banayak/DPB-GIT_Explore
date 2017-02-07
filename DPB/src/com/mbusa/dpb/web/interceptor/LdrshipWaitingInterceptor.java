package com.mbusa.dpb.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ExecuteAndWaitInterceptor;
import org.apache.struts2.interceptor.BackgroundProcess; 

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;

import java.util.Map;

import com.mbusa.dpb.web.Reports.action.LeadershipReportAction;
import com.mbusa.dpb.web.helper.IWebConstants;

public class LdrshipWaitingInterceptor extends ExecuteAndWaitInterceptor{

	private static final long serialVersionUID = 7157210169267384957L;
	private String reportName = "";
	private String token = "";
	private String reportNameSess = "";
	private String tokenSess = "";
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		
		String dealer = "";
		long reqSecs = 0;
		long refSecs = 0;
		int recs = 0;
		boolean recalculate = false;
		boolean first = false;
		
		if (httpSession.getAttribute(IWebConstants.SM_USER_SESSION) == null && request.getParameter("username") == null) {
			request.setAttribute("sessionExpire", "Y");
			return "ldrshipReportPopUp";
        }
		else{
		if(httpSession.getAttribute("rcount") != null){
			recs = Integer.parseInt(httpSession.getAttribute("rcount").toString());
		}
		
		if(httpSession.getAttribute("recal") != null){
			recalculate = Boolean.parseBoolean(String.valueOf(httpSession.getAttribute("recal")));
		}
		
		if(request.getAttribute("dealer") != null){
			dealer = request.getAttribute("dealer").toString();
		}
		
		if(httpSession.getAttribute("WAITTOKEN") != null){
			tokenSess = httpSession.getAttribute("WAITTOKEN").toString();
		}
		if(httpSession.getAttribute("RPTNM") != null){
			reportNameSess = httpSession.getAttribute("RPTNM").toString();
		}

		if(request.getAttribute("waitReq") != null){
			token = request.getAttribute("waitReq").toString();
		}

		if("".equals(tokenSess) || "".equals(reportNameSess)){
			tokenSess = token;
			reportNameSess = reportName;
			httpSession.setAttribute("WAITTOKEN", tokenSess);
			httpSession.setAttribute("RPTNM", reportNameSess);
		}
		else{
			if(tokenSess.equalsIgnoreCase(token) && reportNameSess.equalsIgnoreCase(reportName)){
				ActionContext context = invocation.getInvocationContext();
				Map session = context.getSession();

				ActionProxy proxy = invocation.getProxy();
				synchronized (session) {
					String name = getBackgroundProcessName(proxy) ;
					BackgroundProcess process = (BackgroundProcess) session.get(KEY + name);
					if(process != null & !(recs > 0)){
						Object obj = process.getAction();
						if(obj instanceof LeadershipReportAction){
							LeadershipReportAction action = (LeadershipReportAction) obj;
							if(action.getLdrshipRpt() != null){
								recs = action.getLdrshipRpt().getRecCount();
							}	
						}
					}
				}
			}else{
				ActionContext context = invocation.getInvocationContext();
				Map session = context.getSession();

				ActionProxy proxy = invocation.getProxy();
				synchronized (session) {
					String name = getBackgroundProcessName(proxy) ;
					session.remove(KEY + name);
				}

				httpSession.setAttribute("WAITTOKEN", token);
				httpSession.setAttribute("RPTNM", reportName);
				recs = 0;
				recalculate = false;
			}
			
			int dealersSize = dealer.split(",").length;
			
			if(httpSession.getAttribute("reqSecs") == null || !(tokenSess.equalsIgnoreCase(token) && reportNameSess.equalsIgnoreCase(reportName))){
				
				if(dealersSize != 0 && !"".equals(dealer)){
					recs = dealersSize;
				}
				
				reqSecs = Math.round(recs*secsPerDealer(recs));
				
				if(reqSecs <= 0){
					reqSecs = 5;
				}

				httpSession.setAttribute("reqSecs", reqSecs);
			}

			
			if(tokenSess.equalsIgnoreCase(token) && reportNameSess.equalsIgnoreCase(reportName) && !recalculate && recs >0){
				if(!"".equals(dealer)){
					recs = dealersSize;
				}
				reqSecs = Math.round(recs*secsPerDealer(recs));
			
				if(reqSecs <= 0){
					reqSecs = 5;
				}
				if(recalculate == false && recs > 0){
					recalculate = true;
					first = true;
					if(reqSecs > 30){
						refSecs = 30;
					}else{
						refSecs = reqSecs/4;
						if(refSecs < 2){
							refSecs = 2;
						}
					}
					httpSession.setAttribute("refSecs", refSecs);
				}
				
				httpSession.setAttribute("reqSecs", reqSecs);
			}
			
			if(httpSession.getAttribute("refSecs") == null || !(tokenSess.equalsIgnoreCase(token) && reportNameSess.equalsIgnoreCase(reportName))){

				refSecs = reqSecs/4;
				if(refSecs > 30){
					refSecs = 30;
				}
				
				if(refSecs < 2){
					refSecs = 2;
				}
				
				httpSession.setAttribute("refSecs", refSecs);
			}
			
			if(httpSession.getAttribute("reqSecs") != null && httpSession.getAttribute("refSecs") != null && !first &&(tokenSess.equalsIgnoreCase(token) && reportNameSess.equalsIgnoreCase(reportName)) ){
				reqSecs = Integer.parseInt(httpSession.getAttribute("reqSecs").toString());
				refSecs = Integer.parseInt(httpSession.getAttribute("refSecs").toString());
				reqSecs = reqSecs - refSecs;

				if(reqSecs <=0 ){

					reqSecs = Math.round(recs*0.15);
					
									if(reqSecs == 0){
						reqSecs = 10;
					}
					
					refSecs = reqSecs/4;
					if(refSecs > 30){
						refSecs = 30;
					}
					
					if(refSecs < 2){
						refSecs = 2;
					}
					
					httpSession.setAttribute("refSecs", refSecs);
				}

				httpSession.setAttribute("reqSecs", reqSecs);
			}
		}

		httpSession.setAttribute("rcount",recs);
		httpSession.setAttribute("recal", recalculate);
		return super.doIntercept(invocation);
		}
	}
	
	private double secsPerDealer(int recs){
		double secsPerDealer = 0;
		
		if(recs <= 100){
			secsPerDealer = 0.8;
		}
		
		else if(recs <= 200){
			secsPerDealer = 0.7;
		}
		
		else if(recs <= 300){
			secsPerDealer = 0.6;
		}
		
		else if(recs <= 500){
			secsPerDealer = 0.5;
		}
		
		else if(recs <= 1000){
			secsPerDealer = 0.3;
		}
		else{
			secsPerDealer = 0.2;
		}
		
		return secsPerDealer;
	}
}
