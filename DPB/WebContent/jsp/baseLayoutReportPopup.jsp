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
		<script type="text/javascript">
			var ctxPath = '<%=request.getContextPath() %>';
		</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
	<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
	<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>

<style type="text/css">
	.errors{
		font-weight:bold;
		color:red
		}

	.messages {
		font-weight:bold;
		color:blue
		}
		
	TABLE.wwFormTable{
		BORDER-BOTTOM: 0px; 
		BORDER-LEFT: 0px; 
		PADDING-BOTTOM: 0px; 
		MARGIN: 0px; 
		PADDING-LEFT: 0px; 
		WIDTH: 549px; 
		PADDING-RIGHT: 0px; 
		BORDER-TOP: 0px; 
		BORDER-RIGHT: 0px; 
		PADDING-TOP: 0px;
	}
	
	TABLE.wwFormTable TD{
		BORDER-LEFT: #eff1f3 1px solid; 
		PADDING-BOTTOM: 6px; 
		MARGIN: 0px; 
		PADDING-LEFT: 4px; 
		PADDING-RIGHT: 4px; 
		COLOR: #333333; 
		FONT-SIZE: 12px; 
		VERTICAL-ALIGN: top; 
		BORDER-TOP: #c1c1c1 1px solid; 
		PADDING-TOP: 6px	
	}	

	TABLE.wwFormTable TD SPAN{
		BORDER-BOTTOM: 0px; 
		BORDER-LEFT: 0px; 
		PADDING-BOTTOM: 0px; 
		MARGIN: 0px; 
		PADDING-LEFT: 0px; 
		PADDING-RIGHT: 0px; 
		BORDER-TOP: 0px; 
		BORDER-RIGHT: 0px; 
		PADDING-TOP: 0px
	}
	
	.errorMessage{
		font-weight:bold;
		color:red
	}
	
	</style>

</head>
<body id="widebkg">
	<div class="dpbscreen">
		
		<div id="headerareaframe">
		<tiles:insertAttribute name="header" />
		</div>
		<div id="contentareaPopup">
		<tiles:insertAttribute name="body" />
		</div>
		
		<tiles:insertAttribute name="footer" />
		
	</div>	
</body>
</html>