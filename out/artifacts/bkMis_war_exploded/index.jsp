<%@ page language="java"  pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>

  </head>
  
  <body>
   	<form name="f1" action = "demo.do?method=loginTest" method = "post">
	    <table align = "center" border = "2">
	    	<tr>
	    		<td>用户名</td><td><input type ="text"></td>
	    	</tr>
	    	<tr>
	    		<td>密码</td><td><input type ="password"></td>
	    	</tr>
	    	<tr><td colspan="2" align="center"><input type = "submit" value = "确定"></tr>
	 	</table>
    </form>
  </body>
</html>
