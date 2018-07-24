<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>设备类别</title>
    
    <meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <!-- 使用日期控件时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<!-- 使用单元格在线编辑功能时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/jquery.form.js"></script>
	<script type="text/javascript">
		
		function del(){
				var name = document.getElementById("name").value;
				var code = document.getElementById("code").value;
				if(name == null || name == ""){
					alert("请选择要删除的类别名称!");
				}else if(code == null || code == ""){
					alert("请选择要删除的类别代码!");
				}else{
					if(!window.confirm("您确定要删除吗?")) {return ;}
					document.form1.action="depot.do?method=delTpey";
					document.form1.submit();
				}
		}
		//编辑信息提交
		function edit(){
			var name = document.getElementById("name").value;
			var code = document.getElementById("code").value;
			var oldname = document.getElementById("oldname").value;
			var oldcode = document.getElementById("oldcode").value;
			var id = document.getElementById("id").value;
			//alert(id);
			if(id == null || id == ""){
				alert("请选择要修改的设备类别!");
			}else{
				if(name == null || name ==""){
					alert("类别名称不能为空!");
				}else if(code == null || code == ""){
					alert("类别代码不能为空!");
				}else if(name == oldname && code == oldcode){
					alert("您没做任何修改!");
				}else{
					alert("修改成功!");
					document.form1.action="depot.do?method=editTpey";
					document.form1.submit();
				    //window.parent.location.href="depot.do?method=showMaterialsTpey";
				    //window.parent.frames[0].reload;
				}
			}
		}
		//添加信息
		function add(){
			//alert("添加成功!");
			var id = document.getElementById("id").value;
			if(id == null || id == ""){
				alert("请选择要添加的设备类别父类别!");
			}else{
				var childName = document.getElementById("childName").value;
				var childCode = document.getElementById("childCode").value;
				if(childName == "" || childName == null){
					alert("类别名称不能为空!");
				}else if(childCode == "" || childCode == null){
					alert("类别代码不能为空!");
				}else{
				$.post("<%=path%>/depot.do?",{method:"checkAddTypeName",childName:childName},function(data){
					if(data == 0){
						alert("类别名称已经存在!");
						return false;
					}else{
						$.post("<%=path%>/depot.do?",{method:"checkAddTypeCode",childCode:childCode},function(data){
						if(data == 0){
							alert("类别代码已经存在!");
							return false;
						}else{
							//document.form1.action="depot.do?method=addTpey";
							//document.form1.submit();
							$("#form1").ajaxSubmit({
				   			 	success: function(data){
				    		   	alert(data);
				    		   	window.parent.location.href="depot.do?method=showMaterialsTpey";
							 }
							});
							
						}
					  })
					}
				})
			  }
		  }
		}
		//执行修改验证材料名称
		function changeName(){
			//alert("ooo");
			var name = document.getElementById("name").value;
			var oldname = document.getElementById("oldname").value;
			if(name != oldname){
				$.post("<%=path%>/depot.do?",{method:"checkEditTypeName",currentName:name},function(data){
					if(data == 0){
						alert("类别名称已经存在!");
						//return false;
						document.getElementById("name").value="";
						}
					})
				}
		}
		//检验类别代码
		function changeCode(){
			var code = document.getElementById("code").value;
			var oldcode = document.getElementById("oldcode").value;
			if(code != oldcode){
			$.post("<%=path%>/depot.do?",{method:"checkEditTypeCode",currentCode:code},function(data){
				if(data == 0){
					alert("类别代码已经存在!");
					//return false;
					document.getElementById("code").value="";
				}
			})
		   }	
		}
	</script>
  </head>
  
  <body style="overflow-y:hidden;">
    <form action="depot.do?method=addTpey" method="post" name="form1" id="form1">
    	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
    			<tr>
    				<td height="5"></td>
  				</tr>
  				<tr>
    			<td>
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
      					<tr>
        					<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
							<td width="165" nowrap="nowrap" class="form_line">设备类别信息</td>
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
    				   <c:choose>
    					<c:when test="${empty list}">
    						<tr>
    						<td colspan="2" class="head_one" align="left">当前类别信息
    						<input type="hidden" name="codeList" id="codeList" value="${codeList }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							类别名称：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="name" value="" id="name"/>
    							<input type="hidden" name="oldname" value="" id="oldname"/>
    							<input type="hidden" name="id" id="id" value=""/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							类别代码：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input id="code" type="text" name="code" value=""/>
    							<input id="oldcode" type="hidden" name="oldcode" value=""/>
    						</td>
    					</tr>
    					</c:when>
    					<c:otherwise>
    					<c:forEach items="${list}" var="type">
    					<tr>
    						<td colspan="2" class="head_one" align="left">当前类别信息
    							<input type="hidden" name="codeList" id="codeList" value="${codeList }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							类别名称：
    							<input type="hidden" name="id" id="id" value="${type.id }"/>
    							<input type="hidden" name="description" id="description" value="${type.description }"/>
    							<input type="hidden" name="parentid" id="parentid" value="${type.parentid }"/>
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="name" value="${type.name }" id="name" onblur="changeName()"/>
    							<input type="hidden" name="oldname" value="${type.name }" id="oldname"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							类别代码：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input id="code" type="text" name="code" value="${type.code }" onblur="changeCode()"/>
    							<input id="oldcode" type="hidden" name="oldcode" value="${type.code }"/>
    						</td>
    					</tr>
    					</c:forEach>
    					</c:otherwise>
    					</c:choose>
    					<tr>
    						<td></td>
    						<td>
    							<input type="button" name="" class="button" value="修改" onclick="edit()"/>
    							<input type="button" name="" class="button" value="删除" onclick="del()" id="delType"/>
    						</td>
    					</tr>
    				</table>
    			</td>
    		</tr>
    		<tr>
    			<td class="menu_tab2" align="center">
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
    					<tr>
    						<td colspan="2" class="head_one" align="left">添加子类别</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							类别名称：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="childName" id="childName"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							类别代码：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input id="childCode" type="text" name="childCode"/>
    						</td>
    					</tr>
    					<tr>
    						<td></td>
    						<td>
    							<input type="button" name="" class="button" value="添加" onclick="add()">
    						</td>
    					</tr>
    				</table>
    			</td>
    		</tr>
    	</table>
    </form>
  </body>
</html>
