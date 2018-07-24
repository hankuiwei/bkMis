<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>" target="_parent">
    
    <title>材料信息_编辑</title>
    <meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<!-- 使用单元格在线编辑功能时，引入下面的js -->
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<!-- <base target="_self" /> -->
	
	<script type="text/javascript">
		//执行修改
		function edit(){
			var name = document.getElementById("name").value;
			var code = document.getElementById("code").value;
			var unit = document.getElementById("unit").value
			if(code == "" || code == null){
				alert("材料编号不能为空!")
			}else if(name == "" || name == null){
				alert("材料名称不能为空!");
			}else if(document.getElementById("name").value.indexOf(" ")!=-1){
				alert("材料名称中不能有空格!");
			}else if(unit == "" || unit == null){
				alert("请选择单位!");
			}else{
				//alert("修改成功!");
				document.form1.action="<%=path %>/setmaterials.do?method=doEditMaterials";
				document.form1.submit();
				//window.close();
			}
		}
		//执行材料编号验证
		function changeCode(){
			var oldCode = document.getElementById("oldCode").value;
			var code = document.getElementById("code").value;
			if(code != oldCode){
				$.post("<%=path%>/setmaterials.do?",{method:"checkAddCode",code:code},function(data){
					if(data == 0){
						alert("材料编号已经存在!");
						document.getElementById("code").value="";
					}
				})
			}
		}
		//执行名称的验证
		function changeName(){
			var oldName = document.getElementById("oldName").value;
			var name = document.getElementById("name").value;
			if(name != oldName){
				$.post("<%=path%>/setmaterials.do?",{method:"checkAddName",name:name},function(data){
					if(data == 0){
						alert("材料名称已经存在!");
						document.getElementById("name").value;
					}
				})
			}
		}
	</script>
  </head>
  
  <body style="">
    <form id="form1" action="" method="post" name="form1">
    		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
    			<tr>
    				<td height="5"></td>
  				</tr>
  				<tr>
    			<td>
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
      					<tr>
        					<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
							<td width="165" nowrap="nowrap" class="form_line">材料信息_编辑</td>
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
    				    <c:if test="${not empty materialsForm.materialList }">
    					<c:forEach items="${materialsForm.materialList}" var="mf">
    					<tr>
    						<td colspan="4" class="head_one" align="left">基本信息
    						   <c:choose>
    						    <c:when test="${not empty typeCode}">
    							<input type="hidden" name="type" id="type" value="${typeCode }"/>
    							</c:when>
    							<c:otherwise>
    							<input type="hidden" name="type" id="type" value="${tpCode }">
    							</c:otherwise>
    						   </c:choose>
    							<input type="hidden" name="id" id="id" value="${mf.id }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							材料编号：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input id="code" type="text" name="code" value="${mf.code }" onblur="changeCode()"/>
    							<input id="oldCode" type="hidden" name="oldCode" value="${mf.code }"/>
    							<font color="red">*</font>
    						</td>
    						<td align="right" class="head_form1">
    							材料名称：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input id="name" type="text" name="name" value="${mf.name }" onblur="changeName()"/>
    							<input id="oldName" type="hidden" name="oldName" value="${mf.name }">
    							<font color="red">*</font>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							规格：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input id="spec" type="text" name="spec" value="${mf.spec }"/>
    						</td>
    						<td align="right" class="head_form1">单位：</td>
    						<td class="head_form1">&nbsp;
    							<select id="unit" name="unit" style="width:130px;">
    								<option value="">--请选择--</option>
    								<c:forEach items="${materialsForm.unitList}" var="ul">
    									<option value="${ul.codeValue }"
    										<c:if test="${mf.unit == ul.codeValue }">selected</c:if>
    									>
    									${ul.codeName }</option>
    								</c:forEach>
    							</select>
    							&nbsp;&nbsp;<font color="red">*</font>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">库存上限：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="upperLimit" value="${mf.upperLimit }"/>
    						</td>
    						<td align="right" class="head_form1">库存下限：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="lowerLimit" id="lowerLimit" value="${mf.lowerLimit }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">参考单价：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<input type="text" name="unitPrice" value="${mf.unitPrice }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							备注：
    						</td>
    						<td  class="head_form1" colspan="3">&nbsp;
    							<textarea rows="4" cols="60" name="bz">${mf.bz }</textarea>
    						</td>
    					</tr>
    					</c:forEach>
    					</c:if>
    			<tr>
    				<td colspan="4" align="center">
    					<input id="button" type="button" name="button" value="提交" class="button" onclick="edit()"/>
    					<input type="button" name="retur" value="返回" onclick="javascript:window.close();" class="button"/>
    				</td>
    			</tr>
    			<c:if test="${flag}">
    			   <script type="text/javascript">
    				  returnValue = "flag";
    				  this.close();
    				</script>
    			</c:if>
    		</table>
	</td>
	</tr>
	</table>
	</form>
  </body>
</html>
