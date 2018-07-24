<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String url = request.getParameter("url");
url = url.replaceAll("@","&");
if(url.startsWith("/")){
	url = path+url;
}else{
	url = path+"/"+url;
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <body>
  <!-- 加载页面div -->
  <jsp:include page="/loading.jsp"></jsp:include>
  <!-- 加载页面div -->
  <form method="post" name="actionForm">
  </form>
  </body>
  <script type="text/javascript">
  	document.actionForm.action = "<%=url%>";
  	document.actionForm.submit();
  	setInterval("showLoadingDiv()",300);
  </script>
</html>
