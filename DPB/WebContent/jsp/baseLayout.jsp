<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Mercedes-Benz Dealer Performance Bonus (DPB)</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
		<meta http-equiv="Content-Language" content="en-GB"/>
		<meta name="description" content="Dealer Performance Bonus (DPB) Application"/>
		<meta name="keywords" content="Mercedes-Benz, Mercedes, MB, Merc, Benz, DaimlerChrysler, Daimler-Chrysler, Daimler, Chrysler, Corporate Identity, CI, Corporate Design, CD, CI/CD, CICD, Brand Identity, BI, Brand Design, BD, Brand Design System, BDS, logo, logotype, star, brand mark, brand management, guidelines, artwork, art, work, downloads, brochures, brochure, manual, manuals, Richtlinien, Markendesign, Marke, Design, Markenidentitaet, Identitaet, Handbuch, Markenmanagement, Markenrichtlinien, Firmenzeichen, Stern"/>
		<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath() %>/css/master.css" />
		<script language="JavaScript" src="<%=request.getContextPath() %>/js/dpb.js" type="text/javascript"></script>
		<script type="text/javascript">
			var ctxPath = '<%=request.getContextPath() %>';
		</script>
</head>
<body id="widebkg">
	<div class="dpbscreen">
		<div id="headerareaframe" >
				<tiles:insertAttribute name="header" />
		</div>
		
			<tiles:insertAttribute name="body" />
		
		<div id="footer">
			<tiles:insertAttribute name="footer" />
		</div>
	</div>	
</body>
</html>