<%@ page language="java" isErrorPage="true" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'errorPage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript">
		function back(){
			{
				history.back();
			}
			if(true){
				window.close();
			}
		}
	</script>
  </head>
  
  <body>
		<table  align="center" style="width:100%;height:100%;valign:middle">
			<tr>
				<td align="center" style="width:100%;height:100%;valign:middle">
		  			<font style="size:15" color="red">对不起，系统暂时无法处理您的请求，请确认操作无误或联系系统管理员解决！</font>
		  			<br />
		  			<input type = "button" class="button" value="返回" onclick="back();" />
  				</td>
  				</tr>
  		</table>
  </body>
</html>
