<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加材料类别</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<!-- 使用单元格在线编辑功能时，引入下面的js -->
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	
	<script type="text/javascript">
		//执行添加操作
		function add(){
			var name = document.getElementById("name").value;
			var code = document.getElementById("code").value;
			if(name == "" || name == null){
				alert("类别名称不能为空!");
			}else if(code == "" || code == null){
				alert("类别代码不能为空!");
			}else{
			$.post("<%=path%>/depot.do",{method:"checkAddTypeName",childName:name},function(data){
				if(data == 0){
					alert("类别名称已经存在!");
					return false;
				}else{
					$.post("<%=path%>/depot.do",{method:"checkAddTypeCode",childCode:code},function(data){
						if(data == 0){
							alert("类别代码已经存在!");
							return false;
						}else{
							document.form1.action="depot.do?method=doAddSecond";
							document.form1.submit();
						}
				
					})
				}
			})
		  }
		}
	</script>
  </head>
  
  <body style="overflow-y:hidden;">
     <form action="" method="post" name="form1">
    	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
    			<tr>
    				<td height="5"></td>
  				</tr>
  				<tr>
    			<td>
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
      					<tr>
        					<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
							<td width="165" nowrap="nowrap" class="form_line">添加材料类别信息</td>
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
    						<td colspan="2" class="head_one" align="left">添加材料类别信息</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							类别名称：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="name" id="name"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							类别代码：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input id="code" type="text" name="code"/>
    						</td>
    					</tr>
    					<tr>
    						<td></td>
    						<td>
    							<input type="button" name="" class="button" value="添加" onclick="add()"/>
    							<input type="button" name="" class="button" value="返回" onclick="javascript:history.go(-1)"/>
    						</td>
    					</tr>
    				</table>
    			</td>
    		</tr>
    	</table>
    </form>
  </body>
</html>
