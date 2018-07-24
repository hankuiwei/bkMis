<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <c:choose>
	<c:when test="${empty materialList}">
	</c:when>
	<c:otherwise>
		<c:forEach items="${materialList}" var="ml">
			<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';" id="tr1">
				<td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="childBox" name="childBox" value="${ml.code }"></td>
				<td class="RptTableBodyCellLock" align="center" title="${ml.code }">${ml.code }&nbsp;
					<input type="hidden" name="materialCode"  value="${ml.code }"/>
					<input type="hidden" name="ids" id="ids" value="${ml.id }"/>
				</td>
				<td class="RptTableBodyCell" align="center" title="${ml.name }">${ml.name }&nbsp;
					<input type="hidden" name="name"  value="${ml.name }"/>
				</td>
				<td class="RptTableBodyCell" align="center" title="${ml.spec }">${ml.spec }&nbsp;
					<input type="hidden" name="spec"  value="${ml.spec }"/>
				</td>
				<td class="RptTableBodyCell" align="center" title="${ml.codeName }">${ml.codeName }&nbsp;
					<input type="hidden" name="codeName"  value="${ml.codeName }"/>
				</td>
				<td class="RptTableBodyCell" align="center" title="${ml.unitPrice }" id="amountUnit">
					<input type="text" name="unitPrice" value="${ml.unitPrice }" t_value="" o_value="" datatype="Require" msg="单价不得为空！" onchange="getPerSum(this)" onkeyup="checkIsANum(this)"/>
				</td>
				<td class="RptTableBodyCell" align="center" title="amount"><input name="amount" id="amount${ml.id }" type="text" value="" t_value="" o_value="" datatype="Require" msg="出库数量不得为空！" onchange="getPerSum(this)" onkeyup="checkIsANum(this)"  />&nbsp;
					<input type="hidden" name="inOutputCode" id="inOutputCode${ml.id }"/>
				</td>
				<td class="RptTableBodyCell" align="center" title="" id="rs${ml.id }">
					<input type="text" name="amountMoney" readonly="readonly" id="amountMoney${ml.id }" value="0"/>
				</td>
			</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>
</html>
