<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.apache.struts.taglib.html.Constants" %> 
<%@ page import="org.apache.struts.Globals" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加供应商信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/validate.js"></script>
	<script type="text/javascript">
		function addSupplier(){
			var name = document.getElementById("name").value;
			var fullName = document.getElementById("fullName").value;
			if(name == null || name == ""){
				alert("供应商简称不能为空!");
				document.getElementById("name").focus();
			}else if(document.getElementById("name").value.indexOf(" ")!=-1){
				alert("不能含有空格!");
				document.getElementById("name").focus();
			}else if(document.getElementById("fullName").value.indexOf(" ")!=-1){
				alert("不能含有空格!");
				document.getElementById("fullName").focus();
			}else if(fullName == null || fullName == ""){
				alert("供应商全称不能为空!");
				document.getElementById("fullName").focus();
			}else{
				document.addForm.action = "<%=path%>/supplier.do?method=doAddSupplier";
				document.addForm.submit();
			}
		}
	</script>
  </head>
  
  <body style="overflow-y:hidden;">
     <form action="" method="post" name="addForm">
     	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
    			<tr>
    				<td height="5"></td>
  				</tr>
  				<tr>
    			<td>
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
      					<tr>
        					<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
							<td width="165" nowrap="nowrap" class="form_line">供应商信息_新增</td>
							<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
							<td width="1080" class="form_line2"></td>
							<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        				</tr>
    				</table>
    			</td>
  				</tr>
    			<tr>
    				<td class="menu_tab2" align="center">
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
    					<tr>
    						<td colspan="4" class="head_one" align="left">基本信息
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							供应商简称：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="name" id="name"/>
    							<font color="red">*</font>
    						</td>
    						<td align="right" class="head_form1">
    							供应商全称：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="fullName" id="fullName"/>
    							<font color="red">*</font>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							联系人：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input id="linkMan" type="text" name="linkMan"/>
    						</td>
    						<td align="right" class="head_form1">联系方式：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="phone" id="phone"/>
    						</td>
    					</tr>
    			<tr>
    				<td colspan="4" align="center">
    					<input id="button" type="button" name="button" value="提交" class="button" onclick="addSupplier()"/>
    					<input type="button" name="retur" value="返回" onclick="javascript:history.go(-1);" class="button"/>
    				</td>
    			</tr>
    		</table>
    		<input type="hidden" name="<%=Constants.TOKEN_KEY%>" value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>"/>
    	</td>
    	</tr>
    	</table>
     </form>
  </body>
</html>
