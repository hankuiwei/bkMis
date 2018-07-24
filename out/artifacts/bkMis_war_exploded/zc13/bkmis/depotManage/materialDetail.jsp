<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>材料的详细信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>

  </head>
  
  <body style="overflow-y:hidden;">
    <form action="" method="post" name="detailForm">
    	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
    			<tr>
    				<td height="5"></td>
  				</tr>
  				<tr>
    			<td>
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
      					<tr>
        					<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
							<td width="165" nowrap="nowrap" class="form_line">材料详细信息</td>
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
    					<c:forEach items="${detail}" var="dl">
    					<tr>
    						<td align="right" class="head_left">
    							材料编号：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="code" id="code" value="${dl.materCode }"/>
    							<font color="red">*</font>
    						</td>
    						<td align="right" class="head_form1">
    							材料名称：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input id="" type="text" name="name" id="name" value = "${dl.materName }"/>
    							<font color="red">*</font>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							规格：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input id="spec" type="text" name="spec" value="${dl.spec }"/>
    						</td>
    						<td align="right" class="head_form1">单位：</td>
    						<td class="head_form1">&nbsp;
    							<select id="unit" name="unit" style="width:130px;">
    								<option value="">--请选择--</option>
    								<c:forEach items="${materialsForm.unitList}" var="ul">
    									<option value="${ul.codeValue }"
    										<c:if test="${dl.unit == ul.codeValue }">selected</c:if>
    									>
    									${ul.codeName }</option>
    								</c:forEach>
    							</select>
    							&nbsp;<font color="red">*</font>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">库存量：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="nowStock" value="${dl.nowStock }"/>
    						</td>
    						<td align="right" class="head_form1">可用库存：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="doStock" id="doStock"  value="${dl.doStock }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">库存上限：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="upperLimit" value="${dl.upperLimit }"/>
    						</td>
    						<td align="right" class="head_form1">库存下限：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="lowerLimit" id="lowerLimit" value="${dl.lowerLimit }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">单价：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="unitPrice" value="${dl.unitPrice }"/>
    						</td>
    						<td align="right" class="head_form1">材料类别：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="materialType" id="materialType" value="${dl.typeName }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							备注：
    						</td>
    						<td  class="head_form1" colspan="3">&nbsp;
    							<textarea rows="4" cols="60" name="bz">${dl.bz }</textarea>
    						</td>
    					</tr>
    					</c:forEach>
    			<tr>
    				<td colspan="4" align="center">
    					<input type="button" name="retur" value="返回" onclick="javascript:history.go(-1);" class="button"/>
    				</td>
    			</tr>
    		</table>
    	</td>
    	</tr>
    	</table>
    </form>
  </body>
</html>
