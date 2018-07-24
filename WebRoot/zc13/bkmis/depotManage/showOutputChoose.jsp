<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://struts.apache.org/tags-html" prefix = "html" %>


<html>
<c:choose>
		<c:when test="${empty materialList}">
			
		</c:when>
	<c:otherwise>
		<c:forEach items="${materialList}" var="ml" varStatus="vs">
		
			<tr id="tr1" onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
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
				<td class="RptTableBodyCell" align="center" title="${ml.codeName}">${ml.codeName}&nbsp;
					<input type="hidden" name="spec"  value="${ml.codeName}"/>
				</td>
				<td class="RptTableBodyCell" align="center" title="${ml.doStock }">${ml.doStock }&nbsp;
					<input type="hidden" name="doStock"  value="${ml.doStock }" id="doStock${ml.id }"/>
				</td>
				<td class="RptTableBodyCell" align="center" title="${ml.unitPrice }" id="amountUnit">${ml.unitPrice }&nbsp;
					<input type="hidden" name="unitPrice" value="${ml.unitPrice }"/>
				</td>
				<td class="RptTableBodyCell" align="center" title="amount">
					<input name="amount" id="amount${ml.id }" type="text" value="" t_value="" o_value="" datatype="Require" msg="出库数量不得为空！" onchange="getPerSum(this)" onkeyup="checkIsANum(this)"  />&nbsp;
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
