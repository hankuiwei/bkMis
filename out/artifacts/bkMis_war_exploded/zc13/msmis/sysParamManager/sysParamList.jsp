<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%@ page import="com.zc13.msmis.form.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="<%=path %>/zc13/msmis/functionList/scripts/jquery.js"></script>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'sysParamList.jsp' starting page</title>
    <script type="text/javascript">
    function delCode(){
    var codeIds = document.getElementsByName("codeId");
    var choose = false;
    for(var i = 0;i<codeIds.length;i++){
    	if(codeIds[i].checked){
    	choose = true;
    	}
    }
    if(choose){
    	document.form1.action="sysParamManager.do?method=delCode&value=" + value;
		document.form1.submit();
    }else(
    	alert("请选择删除对象！")
    )
    }
   function disp(obj){
  	var value = document.getElementById("choose").value;
	
	document.form1.action="sysParamManager.do?method=chooseType&value=" + value;
	document.form1.submit();
  	}
  	function addTypeCode(){
	document.form1.action="sysParamManager.do?method=goAdd";
	document.form1.submit();
  	}
    
    </script>
    
  </head>
  
  <body> 
  <form action="sysParamManager.do?method=del" method="post" name="form1">
   <table border="1" align="center">
   	<tr >
   		<td colspan="2"><select name="choose" onchange="disp()" id="choose">
   		<option id="1">全部类型</option>
   		<c:forEach var="codeType" items="${list2 }">
   			<c:choose>
   				<c:when test="${codeType.codeTypeValue == value }">
   				<option value="${codeType.codeTypeValue }" selected="selected">${codeType.codeTypeName }</option>
   				</c:when>
   				<c:otherwise>
	   			<option value="${codeType.codeTypeValue }" >${codeType.codeTypeName }</option>
   				</c:otherwise>
   			</c:choose>
   		</c:forEach>
   		<td id="name" colspan="2"><input name="codeInfo" type="button" value="数据类型维护" onclick="addCodeType()"></td>
   	</tr>
    <tr>
    	<td>&nbsp;</td><th>数据类型</th><td>数据编号</td><td>数据名称</td><td>操作</td>
    </tr>
    <c:forEach var="sysParam" items="${list }" varStatus="num">
    	<tr id="${sysParam.typeName }+${num.index }">
    		<td><input name="codeId" type="checkbox" value="${sysParam.codeId }" >
    			<input id="codeid${num.index }" name="codeid${num.index }" type="hidden" value="${sysParam.codeId }"></td>
    		<td>${sysParam.typeName }&nbsp;</td>
    		<td align="center">${sysParam.codeValue }&nbsp;</td>
    		<td id="id2${num.index }" width="100px" align="center">${sysParam.codeName }&nbsp;</td><td id="id1${num.index }" style="display:none" ><input size="10" id="codeName${num.index }" type="text" value="${sysParam.codeName }" onblur="change(${num.index },'codeName${num.index }')">&nbsp;</td>
    		<td><input name="button" type="button" value="修改" src="sysParamManager.do?" onclick="display(${num.index })">
    	</tr>
    </c:forEach>
    <tr>
    <td>
    </tr>
   </table>
   <table align="center">
   	<tr>
   		<td><input name="button" type="button" value="添加" onclick="addTypeCode()"></td>
   		<td></td><td></td><td></td>
   		<td><input name="button" type="button" value="删除" onclick="delCode()"></td>
   	</tr>
   </table>
   </form>
   
  </body>
  <script type="text/javascript">
  	function display(obj){
  	document.getElementById("id2" + obj).style.display="none";
 	document.getElementById("id1" + obj).style.display="block";
  	document.getElementById("codeName" + obj).focus();
  	
  	}
  	
  	function change(obj,codeName){
  	
  	if(document.getElementById(codeName).value == ''){
		alert("菜单名称不能为空，请重新输入！");
		document.getElementById(codeName).focus();
		return false;
	}
	var codeId = document.getElementById("codeId" + obj).value;
	var codeName = document.getElementById(codeName).value;
		url = "<%=path%>/sysParamManager.do"
			$.post(url,{method:"checkCodeName", codeName:codeName,codeId:codeId},function(data){
				if(data=='true') {
					alert("该菜单名称已存在或未修改！");
					return false;
				}
			});		
	$.post(url,{method:"saveCodeById", codeName:codeName,codeId:codeId});		
	
  	document.getElementById("id2" + obj).innerHTML = codeName;
  	document.getElementById("id2" + obj).style.display="block";
  	document.getElementById("id1" + obj).style.display="none";
  	
  	}
  	
  	
  
  </script>
</html>
