<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ page import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String queryUrl = request.getParameter("URL");
	String p=queryUrl.replaceAll("//","&");

	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
</head>
<body>

<IFRAME name="contentFrame" id="contentFrame" width="100%" height="470" scrolling="auto" src="<%=path%>/<%=p%>"></IFRAME>

</body>
</html>
<script language="javascript">
function resizeContent(){
	var td = document.getElementById('contentFrame');
	if(contentFrame.document){
		td.width=document.body.scrollWidth+15;
	}
}
</script>
