<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'coststandard.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		function selall(){
			var rad0 = document.getElementById("radio0");
			var arrrad1 = document.getElementsByName("radio1");	
			if(rad0.checked){
				for(var i=0;i<arrrad1.length;i++){
					arrrad1[i].checked="checked";
				}
			}else{
				for(var i=0;i<arrrad1.length;i++){
					arrrad1[i].checked="";
				}
			}		
		}
	</script>

  </head>
  
  <body>
		<table border="1" width="950">
		    	<tr>
		    		<td width="10%"><input type="checkbox" name="radio0" id="radio0" onclick="selall()"></td><td width="15%">类型编码</td><td width="15%">类型名称</td><td width="15%">是否使用表具</td><td align="center">sql语句</td>
		    	</tr>
		    	<tr>
		    		<td><input type="checkbox" name="radio1"></td><td>1</td><td>1</td><td align="center"><input type="checkbox" name="checkbox1"></td><td>1</td>
		    	</tr>
		    	<tr>
		    		<td><input type="checkbox" name="radio1"></td><td>1</td><td>1</td><td align="center"><input type="checkbox" name="checkbox2"></td><td>1</td>
		    	</tr>
		    	<tr>
		    		<td><input type="checkbox" name="radio1"></td><td>1</td><td>1</td><td align="center"><input type="checkbox" name="checkbox3"></td><td>1</td>
		    	</tr>
		    	<tr>
		    		<td><input type="checkbox" name="radio1"></td><td>1</td><td>1</td><td align="center"><input type="checkbox" name="checkbox4"></td><td>1</td>
		    	</tr>
   		</table>
    <br><br>
    <form>
    	<table>
    	<tr>
    		<td>收费项：</td>
    	</tr>
    	<tr>
    		<td>
    			<table border="1" width="950">
    				<tr>
    				 	<td align="right" width="10%">类型编码:</td>
    				 	<td width="20%"><input type="text" name=""></td>
    				 	<td align="right" width="10%">类型名称:</td>
    				 	<td width="20%"><input type="text" name=""></td>
    				 	<td align="right" width="15%">是否使用表具:</td>
    				 	<td width="25%"><input type="radio" name="radio2" width="35%">&nbsp;&nbsp;是<input type="radio" name="radio2" width="35%">&nbsp;&nbsp;否</td>
    				</tr>
    				<tr>
    				 	<td align="right" width="10%">sql语句:</td>
    				 	<td colspan="5"><textarea rows="3" style="width: 50%"></textarea></td>
    				</tr>
    				<tr align="right">
    					<td colspan="6"><input type="button" value="保存">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="取消"></td>
    				</tr>
    			</table>
    		</td>
    	</tr>
    	</table>
    </form>
  </body>
</html>
