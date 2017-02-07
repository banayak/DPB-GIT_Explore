<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
 <%@page import="java.io.*" %> 
 <%@page import="java.util.*" %> 
 <%@page import = com.mbusa.dpb.common.props.PropertyManager" %> 
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<style type="text/css">
	.errorMessage {
  font-size : 12;
  font-weight: bold;
  color: red;
}

</style>
<title>File Copy</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
			
</head>	

<script type="text/javascript">
function getRadioVal() {
   
    var radios = document.getElementsByName('fileName');
	for (var i = 0; i < radios.length; i++) {
    	if (radios[i].checked )
        alert( "You have selected " + radios[i].value +" " +"file");
    }
}
</script>	

<script type="text/javascript">
function setHiddenField(submitButton) {
 
   document.getElementById('submitButtonValue').value = submitButton;
}
</script>

			
<body>

<s:if test="hasActionErrors()">
		<p></p>
		<div class="errors">
			<s:actionerror />
		</div>
	</s:if>
<sj:head jquerytheme="flick"/>
<s:form action="filecopy" method="post"  name="myform"   >
<s:hidden name="stage"   id="stage" /> 
<div id="widecontentarea">
<div class="pageTitle" id="HL1">
	DPB 
</div>
<div class="T8">
	<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0" ></td>
			</tr>
			<tr>
				<td width="500"  class="left" id="ctl00_tdCopyText"><div class="TX1">The Dealer Performance Bonus Programs provide
						 all qualified authorized dealers in good standing an opportunity to earn a performance bonus of up to 5.50%
						 for MB and 3.5% for Vans & smart of Manufacturers Suggested Retail Price (MSRP) on eligible transactions.</div></td>
			<td width="363"  class="right">
	<div align="left">
			<img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
	</div>
			</td>
			</tr>
			<tr>
				<td colspan="2">	
				<div class="template8BottomLine"></div>
				</td>
			</tr>
	</table>
</div>

<table width="200" border="0" class="TBL2">
 <tr>
<td colspan=2><b><font color="red">Note: This page is created to move files from stage folder to Out folder</font></b><br><br></td>
</tr>
	
	 <%!public void GetDirectory(String path, Vector files) {
	 			//added try catch block for handling NullPointerException(09/15/2015)
        try {
			File Directory = new File(path);
			File[] f = Directory.listFiles();
			if (f != null && f.length > 0) {
				for (int i = 0; i < f.length; i++) {
					files.add(f[i].getName());
				}
			}

		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

	}%> 
        
        <%
        
            Vector stageFiles = new Vector();
            //GetDirectory("C:/DPBFileProcess/In", stageFiles);
            GetDirectory("/usr/appdata/share/COFICO/DPB/files/stage/", stageFiles);
            String s = null;
           			
            Vector outFiles = new Vector();
            //GetDirectory("C:/DPBFileProcess/temp", outFiles);
          	GetDirectory("/usr/appdata/share/COFICO/DPB/files/Out/", outFiles);
        %> 
	
	<tr>
	<td width="40%"><b>Stage Folder
	<br><br>
	   <% 
	 	for (int a = 0; a < stageFiles.size(); a++) {
                System.out.println("<file>" + stageFiles.elementAt(a).toString() + "</file>");
                 s = stageFiles.elementAt(a).toString();
		%>
		<input type="radio" name="fileName"  value="<%=s%>" />	<%=s%> <br>
		<%}%>
	    
	<td>  <br><br> <br><br>
	<s:submit  name="submitButton" id = "submit" method="submit" style="float:center;margin-left:60px;" onclick = "getRadioVal()" />
	</b></td>	 
	<td width="40%"><b>Out Folder
	<br><br>
	
	
	
	<textarea name="out"  cols="60" rows="10" id="out"  readonly = "readonly"  wrap = "off"  > 
	<% 
	 for (int a = 0; a < outFiles.size(); a++) {
                System.out.println("<file>" + outFiles.elementAt(a).toString() + "</file>");
                 s = outFiles.elementAt(a).toString();
	%><%=s%> 
	<%}%>
	
	 </textarea>
</tr>



</table>
</s:form>
<%--Added for file copy and delete functionality -DPB Enhancement
Start
--%>
<br/><br/><br/><br/>

				<s:if test="hasActionMessages()">
   						<div class="welcome">
      					<s:actionmessage cssClass="errorMessage"/>
   						</div>
				</s:if>
 	<div>
 		
 		<h3>File Upload:</h3>
		 <br />
		<s:form action="filecopy" method="post" enctype="multipart/form-data" >
		
			<table>
				<tr>
					<td></td>
					<td>
						<s:fielderror fieldName="uploadFile" cssClass="errorMessage"/>
					</td>
					
				</tr>
				<tr>
					<td>
						Select a file to upload:
					</td>
					<td>						
						<s:file name="uploadFile" size="30"/>					
						
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<s:fielderror fieldName="serverPath" cssClass="errorMessage"/>
						
					</td>
				</tr>
				<tr>
					<td>
						File Path:
					</td>
					
					<td>
						<s:select list="filePathsArray" name="serverPath"></s:select>
					</td>
					
				</tr>				
				
				<tr>
					<td>
						
					</td>
					<td>
						<!--<input type="submit" value="Upload" size="30">-->						
						<s:submit name="submitButton" value="Upload" method="fileCopy" onclick="setHiddenField('Upload');"></s:submit>
					</td>
				</tr>
			</table>
				
		</s:form>
  		
 	</div>
 	
 	<div>
 		<h3>File Delete:</h3>
		 <br />
		<s:form action="filecopy" method="post" enctype="multipart/form-data">
			<table>
			
				<tr>
					<td>
						<s:fielderror fieldName="deleteFilePath" cssClass="errorMessage"/>
					</td>
				</tr>
				<tr>
					<td>
						File path to delete file:
					</td>
					<td>
						<s:select list="deleteFilePathsArray" name="deleteFilePath"></s:select>
						
					</td>
					
				</tr>
				<tr>
					<td>
						<s:fielderror fieldName="deleteFileName" cssClass="errorMessage"/>
					</td>
				</tr>
				<tr>
					<td>
						File Name:
					</td>
					<td>
												
						<input type="text" name="deleteFileName" size="30">						
					
					</td>
				</tr>
				<tr>
					<td>
						
					</td>
					<td>			
						
						<s:submit name="submitButton" method="deleteFile" value="Delete" onclick="setHiddenField('Delete');"></s:submit>
					</td>
				</tr>
			</table>
				
		</s:form>
  		
 	</div>
 <!-- End -->
<br /><br /><br/>


</body>

</html>