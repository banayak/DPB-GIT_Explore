package com.mbusa.dpb.web.common.actions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.ibm.ws.http.HttpResponse;
import com.mbusa.dpb.business.util.FileReadingHelper;
import com.mbusa.dpb.business.util.TreeStructure;
import com.mbusa.dpb.common.exceptions.TechnicalException;
import com.mbusa.dpb.common.logger.DPBLog;
import com.mbusa.dpb.common.props.PropertyManager;

/**
 * @author sneh_brat
 * Performance improvement on netstar report
 *
 */
public class NetstarTreeAction extends DPBAction{
	String filePath = PropertyManager.getPropertyManager().getPropertyAsString("payment.path5");
    
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	private static DPBLog LOGGER = DPBLog.getInstance(FileReadingHelper.class);
	static final private String CLASSNAME = FileReadingHelper.class.getName();
	PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
	private String actionForward = "errorPage";
	
	public void getMonths() throws IOException {
		
		final String methodName = "getMonths";
		LOGGER.enter(CLASSNAME, methodName);
		String year = (String) ServletActionContext.getRequest().getParameter("year");
		TreeStructure ts = new TreeStructure();
		//String filePath = PropertyManager.getPropertyManager().getPropertyAsString("payment.path2");
		//ArrayList months = ts.listDirectoryMonth("D:\\report"+"\\"+year,0);
		ArrayList months = ts.listDirectoryMonth(filePath+year.trim(),0);
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		response.setStatus(HttpServletResponse.SC_OK);
		out.write(months.toString());
		out.flush();
		LOGGER.exit(CLASSNAME, methodName);
	
	}
	public void getFiles() throws IOException {
		
		final String methodName = "getFiles";
		LOGGER.enter(CLASSNAME, methodName);
		String year = (String) ServletActionContext.getRequest().getParameter("year");
		String month = (String) ServletActionContext.getRequest().getParameter("month");
		String filePath = PropertyManager.getPropertyManager().getPropertyAsString("payment.path5");
		TreeStructure ts = new TreeStructure();
		//ArrayList files =ts.listDirectoryFile("D:\\report"+"\\"+year+"\\"+month,0);
		ArrayList files =ts.listDirectoryFile(filePath.trim()+year.trim()+"/"+month.trim(),0);
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		response.setStatus(HttpServletResponse.SC_OK);
		out.write(files.toString());
		out.flush();
		LOGGER.exit(CLASSNAME, methodName);
	}
	
	public String savePDFFile() throws IOException {
		actionForward = "savePDFFile";
		response.setContentType("application/pdf");
		String f = request.getParameter("fpath");
		String[] s = f.split("/");
		String fileName = s[s.length-1];
		response.setHeader("Content-Disposition", "attachment; filename="+ fileName);
        ServletOutputStream sos = response.getOutputStream();
        
        System.out.println(f);
        if(f != null)
        {
        FileInputStream fis = new FileInputStream(f.trim());
        byte[] buffer = new byte[64000];
        int bytesRead = 0;
                           
        while(true)
            {
                   bytesRead = fis.read(buffer);
                   if (bytesRead == -1)
                          break;
                   sos.write(buffer,0,bytesRead);
            }
        sos.flush();
        sos.close();
        }
        
	
	return actionForward;
	}
	
	
}
	

	

