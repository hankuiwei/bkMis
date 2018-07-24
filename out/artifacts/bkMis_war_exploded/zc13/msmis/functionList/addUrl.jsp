<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.struts.taglib.html.Constants" %> 
<%@ page import="org.apache.struts.Globals" %>
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
<title>新增URL菜单基本信息</title>
</head>

<body>
<div class="material" style="margin:0px;width:100%;">
	<div class="material_1_body" style="width:100%">
        
        <div class="material_main">
        
        
<form name="addUrl" id="addUrl" method="post" action="">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="right">
		
		<table width="95%" style="BORDER-COLLAPSE: collapse"  borderColor=#1D67CB cellSpacing=0 cellpadding="0"  border=1 >
		<tr id="type">
			  <td class="text_color right_margin">节点类型：</td>
			  <td align="left"><select name="nodeType" id="nodeType" onchange="changeSelect()">
			    <option value="1" selected>子节点</option>
			    <option value="2">兄弟节点</option>
		      </select></td>
		  </tr>
		<tr  id="obj">
		  <td class="text_color right_margin" >增加方向：</td>
		  <td align="left"><select name="direction" id="direction" onchange="selectChange()" disabled>
		    <option value="1">向前</option>
		    <option value="2" selected>向后</option>
		    </select></td>
		  </tr>
		<tr>
			  <td class="text_color right_margin" id="nodeName">父节点名称：</td>
		    <td align="left"><input name="parName" type="text" id="parName"  value="${function.functionname }" disabled></td>
		    
		  </tr>
		  <tr>
			  <td class="text_color right_margin">菜单名称：</td>
			  <td align="left"><input name="functionName" type="text" id="functionName" onkeyup="checkWordLen(this, 100);" maxlength="100" title="最多100个字符" value=""/></td>
		 	  <input name="sequence" type="hidden" id="sequence"  value="${function.sequence }" />
		    <input name="parentId" type="hidden" id="parentId"  value="${function.parentid }" />
		    <input name="functionId" type="hidden" id="functionId"  value="${function.functionid }" />
		  </tr>
			
		  <tr>
			  <td class="text_color right_margin">菜单路径：</td>
			  <td align="left"><textarea name="urlName" cols="50" rows="5" id="urlName" onkeyup="checkWordLen(this, 1000);" maxlength="1000" title="最多1000个字符">/</textarea></td>
		  </tr>
		  
		</table>
		
		</td>
	</tr>
	<tr>
		<td style="height:50px;" align="center" nowrap="nowrap">
		
		    <div style="width:150px">
            	<a onclick="checkBlank();"><div class="buttonBg"><div style="margin-top:3px;">提交</div></div></a>
            	<a onclick="returnList();"><div class="buttonBg"><div style="margin-top:3px;">返回</div></div></a>
            	<input name="resIds" id="resIds" type="hidden" />
         	</div>
		</td>
	</tr>
</table>
<input type="hidden" name="<%=Constants.TOKEN_KEY%>" value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>"/>
</form>

</div>
</div>
</div>
<script>
document.getElementById("obj").style.display="none";
function checkBlank(){

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
	
	if(document.getElementById("urlName").value.length>999){
			alert("菜单路径不能超过1000字！");
			return false;
	}	
	/*
	 p =/^(\/)?[^`~!@#$%^&*()+=|\\\][\]\{\}:;'\,.<>/?]{0,1}[^`~!@$%^&()+|\\\][\]\{\}:;'\,<>]{0,990}$/;
		
	if(trim(document.getElementById("urlName").value) != ''){
	  
		if(!p.test(document.getElementById("urlName").value)) {
			alert("菜单路径不能输入特殊字符");
			return false;
		}
	}
	*/
	if(confirm('你确定要增加吗？')) {
		closeWindow();
		document.addUrl.action="<%=request.getContextPath()%>/function.do?method=addFunction"
		document.addUrl.submit();	
	}
	/*
	var type = document.getElementById("nodeType").value;
	var name = trim(document.addUrl.functionName.value);
	var parname = trim(document.addUrl.parentId.value);	
		url = "<%=path%>/function.do"
			$.post(url,{method:"checkFuncName", functionName:name,parentId:parname,type:type},function(data){
				if(data=='false') {
					if(confirm('你确定要增加吗？')) {
						closeWindow();
						document.addUrl.action="<%=request.getContextPath()%>/function.do?method=addFunction"
						document.addUrl.submit();	
					}
				} else {
					alert("该菜单名称已存在");
					return false;
				}
			});	
	*/		
	
}
function returnList(){
	self.location.href="<%=request.getContextPath()%>/function.do?method=getFunctionList";
}
function changeSelect(){
	var value = document.getElementById("nodeType").value;
	var parname = trim(document.addUrl.parentId.value);
	if(parname == 0){
		alert("已是根节点，不能添加兄弟节点！");
		document.getElementById("nodeType").value = 1;
		document.getElementById("nodeName").innerText="父节点名称：";
	}else{
	if(value == 1){
		document.getElementById("direction").disabled = true;
		document.getElementById("parName").value = "${function.functionname }";
		document.getElementById("obj").style.display="none";
		document.getElementById("nodeName").innerText="父节点名称：";
	}else{
		
		document.getElementById("direction").disabled = false;
		document.getElementById("parName").value = "${function.functionname }";
		document.getElementById("obj").style.display="block";
		document.getElementById("nodeName").innerText="节点名称：";
	}}
	
}
</script>
</body>
</html>
