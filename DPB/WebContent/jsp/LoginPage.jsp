<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>	
<html>

	<head>
	
		<title>Mercedes-Benz Dealer Performance Bonus (DPB)</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
		<meta http-equiv="Content-Language" content="en-GB"/>
		<meta name="description" content="Dealer Performance Bonus (DPB) Application"/>
		<meta name="keywords" content="Mercedes-Benz, Mercedes, MB, Merc, Benz, DaimlerChrysler, Daimler-Chrysler, Daimler, Chrysler, Corporate Identity, CI, Corporate Design, CD, CI/CD, CICD, Brand Identity, BI, Brand Design, BD, Brand Design System, BDS, logo, logotype, star, brand mark, brand management, guidelines, artwork, art, work, downloads, brochures, brochure, manual, manuals, Richtlinien, Markendesign, Marke, Design, Markenidentitaet, Identitaet, Handbuch, Markenmanagement, Markenrichtlinien, Firmenzeichen, Stern"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
		<script  src="<%=request.getContextPath() %>/js/dpb.js" type="text/javascript"></script>	
		 <style>
	#error {
		color: red;
		padding-left: 3px;
		
	}
	em {
		color: red;
	}
	</style>
	</head>
	
	<body id="widebkg">
    	
		<form name="Login" method="post" action="login" id="Login">

		    <div id="wrap">
			
				<div id="contwrap">
				
			        <div id="headerareaframe">
				        
							<div id="brandmark"><img src="<%=request.getContextPath() %>/img/mb_logo.gif" width="200" height="50" alt="Mercedes-Benz." border="0" /></div>
							
			        </div>
					
						<div id="FLA1">

					<img
						src="<%=request.getContextPath()%>/imageupload/login_splash_eb8a874f-3b40-445f-a6a3-7af16e0b30bc.jpg"
						title="" alt="" width="1000" height="204" />

				</div>
						
						<div id="homecontentarea" >
						
									<div id="homecolumnleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
									
								<div id="homecolumncenter">
								
									<div id="HL1">
										 <span class="pageTitle">Dealer Performance Bonus (DPB)</span><br/>
									</div>
									
									<div class="loginBox">
									
										<span id="lblLogin" class="loginLabel">Enter Username:</span>
										<input name="username" type="text" id="txtLogin" class="login" />
										<span id="lblPwd" class="loginLabel">Enter Password:</span>
										<input name="password" type="password" id="txtPwd" class="login"  />
										<div class="logindiv">
										   <table>
										   <tr>
										   	<td align="center"><s:submit method="execute" key="Login" align="left"  id="Login" value="Login"  /></td>
										   	</tr>
											</table> 
										</div>
										<!-- <div class="hr clear"></div>
										<a id="hlForgotPassWord" class="LNK2" href="">Forgot your password?</a>
										<a id="hlSignUp" class="LNK2" href="">Register here</a>-->
											
									</div> 
									
								<br/>
        
							</div>
				       
						</div>  
						
						<br style="clear:both" />
						
			    </div><!-- CONTENTwrap -->
				
			</div><!-- wrap -->
	 
			<div id="footer" >
 
				<ul id="right">
				
					<li id="ucFooter_languageCtrl_repeaterLang_ctl01_liLang">
						<a id="ucFooter_languageCtrl_repeaterLang_ctl01_btnSelect" href="javascript:__doPostBack('ucFooter$languageCtrl$repeaterLang$ctl01$btnSelect','')">Deutsch</a>
					</li>
			 
					<li>
						<a href="javascript:popupMedium('/bds/scripts/home/provider.aspx','fixed')" onclick="window.status=' ';return true;" onmouseover="window.status=' ';return true;" onmouseout="window.status=' ';" >
							2005-2013 Daimler (Provider)</a>
					</li>
					 
					<li>
						<a id="ucFooter_hlcookies" href="javascript:popupMedium('/bds/scripts/home/cookies.aspx','fixed')">Cookies</a>
					</li>
					
					<li>
						<a id="ucFooter_hlprivacy" href="javascript:popupMedium('/bds/scripts/home/privacy.aspx','fixed')">Privacy Statement</a>
					</li>
					
					<li class="last">
						<a id="ucFooter_hllegal" href="javascript:popupMedium('/bds/scripts/home/legal.aspx','fixed')">Legal Notice</a>
					</li>
						
				</ul>
				
			</div>
		<script language="javascript"> 
function loginAction()
{
	document.forms.Login.submit();
}
</script>		
		</form>
	</body>
</html>