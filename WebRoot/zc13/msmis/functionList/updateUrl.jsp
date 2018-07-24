<%@ page import="java.util.*" contentType="text/html; charset=utf-8" language="java"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.ArrayList"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/zc13/msmis/functionList/global/css/dtree.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/zc13/msmis/functionList/global/css/table.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/zc13/msmis/functionList/global/css/main_non.css" />
<script type="text/javascript" src="<%=path%>/zc13/msmis/functionList/scripts/common.js"></script>
<script type="text/javascript" src="<%=path %>/zc13/msmis/functionList/scripts/jquery.js"></script>
<title>修改菜单基本信息</title>
</head>
<script type="text/javascript">
	function validate(){
		
	 if(trim(document.getElementById("functionName").value) == ''){
		alert("菜单名称不能为空，请重新输入！");
		document.getElementById("functionName").focus();
		document.getElementById("functionName").value = '';
		return false;
	 }
	
	if(document.getElementById("functionName").value.length>99){
			alert("菜单名称不能超过100字！");
			return false;
	}
	/*
	var p = /^[^`~!@#$%^&*()+=|\\\][\]\{\}:;'\,.<>/?]{1}[^`~!@$%^&()+=|\\\][\]\{\}:;'\,.<>?]{0,99}$/;
	if(!p.test(document.getElementById("functionName").value)) {
		alert("菜单名称不能输入特殊字符");
		return false;
	}*/
	/*
	 p =/^(\/)?[^`~!@#$%^&*()+=|\\\][\]\{\}:;'\,.<>/?]{0,1}[^`~!@$%^&()+|\\\][\]\{\}:;'\,<>]{0,990}$/;
	if(trim(document.getElementById("urlName").value) != ''){
	  
		if(!p.test(document.getElementById("urlName").value)) {
			alert("菜单路径不能输入特殊字符");
			return false;
		}
	}   
	*/
	    var oldname = document.getElementById("oldName").value;
	    var fucname = document.getElementById("functionName").value;
	    var parentId = document.getElementById("parentId").value;
		url = "<%=path%>/function.do"
		if(fucname == oldname){
			document.modUrl.action="<%=request.getContextPath()%>/function.do?method=updateFunction";
			document.modUrl.submit();
		}else{
			if(confirm('你确定要修改吗？')) {
				closeWindow();
				document.modUrl.action="<%=request.getContextPath()%>/function.do?method=updateFunction";
				document.modUrl.submit();	
			}
			/*
			$.post(url,{method:"checkFuncName", functionName:fucname,parentId:parentId},function(data){
				if(data=='false') {
					if(confirm('你确定要修改吗？')) {
						closeWindow();
						document.modUrl.action="<%=request.getContextPath()%>/function.do?method=updateFunction";
						document.modUrl.submit();	
					}
				}else {
					alert("该菜单名称已存在");
					return false;
				}
			});	*/}			
	}
	function returnList(){
	self.location.href="<%=request.getContextPath()%>/function.do?method=getFunctionList";
}
</script>
<body >
<div class="material" style="margin:0px;width:100%;">
	<div class="material_1_body" style="width:100%">
        
        <div class="material_main">
		<form name="modUrl" id="modUrl" method="post">
		<input name="sequence" type="hidden" id="sequence" value="${function.sequence }"/>
		<input name="parentId" type="hidden" id="parentId" value="${function.parentid }"/>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right">
		
				<table width="90%" style="BORDER-COLLAPSE: collapse;margin-top:50px;"  borderColor=#1D67CB cellSpacing=0 cellpadding="0"  border=1 >
				  <tr>
					  <td class="text_color right_margin">菜单名称：</td>
					  <td align="left"><input name="functionName" type="text" id="functionName" value="${function.functionname }" maxlength="100"/>
					  	<input name="oldName" type="hidden" id="oldName" value="${function.functionname }"/>
					  	<input name="functionId" type="hidden" id="functionId" value="${function.functionid }"/>
					  	<input name="sequence" type="hidden" id="sequence" value="${function.sequence }"/>
					  </td>
				 		
				  </tr>
					
				  <tr>
					  <td class="text_color right_margin">菜单路径：</td>
					  <td align="left"><textarea name="urlName" cols="50" rows="5" id="urlName" >${function.urlname }</textarea></td>
				  </tr>
				  
				</table>
		
			  </td>
			 </tr> 
			<tr>
				<td style="height:50px;" align="center" nowrap="nowrap">
		
				    <div style="width:150px">
		            	<a onclick="validate();"><div class="buttonBg"><div style="margin-top:3px;">提交</div></div></a>
		            	<a onclick="returnList();"><div class="buttonBg"><div style="margin-top:3px;">返回</div></div></a>
		            	<input name="resIds" id="resIds" type="hidden" />
		         	</div>
				
				</td>
			</tr>
		</table>
		</form>
	</div>
  </div>
 </div> 		
</body>
</html>
