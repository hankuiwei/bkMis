<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>合同预算账单列表</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript">
    	function exportLr(){
    		document.form1.action="<%=path%>/calculate.do?method=exportCompactDetailExcel";
			document.form1.submit();
    	}
    </script>
</head>
<body>
<form name="form1" method="post">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">合同预算账单列表</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr height = "95%"><!-- 这个td负责画正式内容的左、右、下方的边框 -->
    		<td class="menu_tab2" align="center">
     			<table width="100%"  height = "100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
						<td align="center">
							<!-- 查询条件start -->
					        <!-- 查询条件end -->
		 			 	</td>
					</tr>
  					<tr height="95%">
					    <td valign = "top">
					    	<!-- 表单内容区域 -->
							<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
								<tr height = "95%">
					        		<td width="100%">
					        		<div id = "div1" class = "RptDiv"  >
							   			<table border="0" cellpadding="0" cellspacing="0" class = "RptTable">
					              			<tr>
					              				<input type="hidden" name="compactId" value="${calculateForm.compactId }"/>
								                <th width="5%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">周期开始时间</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">周期结束时间</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">账单开始时间</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">账单结束时间</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">账单月份</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">金额</th>
											</tr>
											<c:choose>
											<c:when test="${empty compactBillList}">
											<tr align="center">
												<td colspan="9" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${compactBillList}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
												<c:set value="${v.money+hj }" var="hj"></c:set>
								                <td width="" nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count }&nbsp;</td>
												<td width="" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.circleStart }&nbsp;</td>
												<td width="" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.circleEnd }&nbsp;</td>
												<td width="" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.startTime }&nbsp;</td>
												<td width="" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.endTime }&nbsp;</td>
												<td width="" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.billDate }&nbsp;</td>
												<td width="" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.money }&nbsp;</td>
											</tr>
											</c:forEach>
											</c:otherwise>
											</c:choose>
					             		</table>
					             		</div>
									</td>
		     		 			</tr>
								<tr>
								  <td>&nbsp;</td>
								</tr>
								<tr>
									<td align="right"><font color="red">合计:</font><fmt:formatNumber value="${hj}" pattern="#.00"/>&nbsp;</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><input type="button" onclick="exportLr();" class="button" value="导出报表"></td>
								</tr>
							</table>
    					</td>
  					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
