<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>材料出处</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		function checkAll(str){
			var arrrad1 = document.getElementsByName("checkbox1");	
			if(str.checked){
				for(var i=0;i<arrrad1.length;i++){
					arrrad1[i].checked="checked";
				}
			}else{
				for(var i=0;i<arrrad1.length;i++){
					arrrad1[i].checked="";
				}
			}		
		}
		function query(){
			document.form1.action="<%=path%>/client.do?method=queryConsume";
			document.form1.submit();
		}
		var k;
		function check(){
			k = 0;
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					k++;
				}
			}
			if(k==0){
				alert("至少选择一条记录");
				return false;
			}
			if(k>1){
				alert("最多只能选择一条记录");
				return false;
			}
			return true;
		}
		//导出报表
		function explorReport(){
			document.form1.action = "client.do?method=explorMaterial";
			document.form1.submit();
		}
	</script>
	
</head>
<body>
<form name = "form1" method="post">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">材料出处</td>
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
		  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
					            	<td height="10"></td>
					         	</tr>
					          	<tr>
					           		<td class="txt" nowrap="nowrap">
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					           		</td>
					           		<td nowrap="nowrap">派工单号：<input type="text" name="cx_sendcardCode" value="${SerClientMaintainForm.cx_sendcardCode }"></td>
					            	<td nowrap="nowrap">出料部门：
					            	<select name="cx_department" id="cx_department" style="width: 130">
										<option value="" selected="selected">请选择</option>
										<c:choose>
										<c:when test="${empty list1}">
										</c:when>
										<c:otherwise>
										<c:forEach items="${list1}" var="v">
											<option value="${v.codeName }" <c:if test="${SerClientMaintainForm.cx_department eq v.codeName}">selected</c:if>>${v.codeName }</option>
										</c:forEach>
										</c:otherwise>
										</c:choose>
									</select>
					            	</td>
					            	<td nowrap="nowrap">材料名称：<input type="text" name="materialName" value="${SerClientMaintainForm.cx_materialName }"></td>
									<td align="right" nowrap="nowrap">
					            		&nbsp;
									</td>
								</tr>
								
								<tr>
					           		<td class="txt" nowrap="nowrap">
					           			&nbsp;
					           		</td>
					           		
					            	<td nowrap="nowrap">消耗详情：<input type="text" name="cx_consumeDetails" value="${SerClientMaintainForm.cx_consumeDetails }"></td>
					            	<td height="10" nowrap="nowrap" colspan="2">出料日期：<input type="text" name="begindate" value="${SerClientMaintainForm.begindate }" readonly onclick="WdatePicker();" class="Wdate">&nbsp;至&nbsp;
					            	<input type="text" name="enddate" value="${SerClientMaintainForm.enddate }" readonly onclick="WdatePicker();" class="Wdate"></td>
									<td align="right" nowrap="nowrap">
					            		<input type="button" class="button" value="查询" onclick="query()">
									</td>
								</tr>
							 	 <tr>
					           		 <td height="10" colspan="9"></td>
					          	 </tr>
					        </table>
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
								                <th width="7%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this)">
								                </th>
								                <th width="7%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">派工单号</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">出料部门</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">材料编号</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">材料名称</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">个数</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">单价</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">总金额</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">出料时间</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">材料消耗详情</th>
											</tr>
											<c:choose>
											<c:when test="${empty list}">
											<tr align="center">
												<td colspan="11" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:set var="totalMoney" value="0"></c:set>
											<c:forEach items="${list}" var="v" varStatus="tag">
											<c:set var="totalMoney" value="${totalMoney+v.amountMoney}"></c:set>
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
											 	<td nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" id="checkbox1" name="checkbox1" value="${v.id }">
												</td>
								                <td nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count}</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.serClientMaintain.sendcardCode }&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.department }&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.materialCode }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.materialName }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.amount }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.unitPrice }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.amountMoney }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.outDate }&nbsp;</td>
								                <c:if test="${empty v.roomName}">
								                	<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.areaName }&nbsp;</td>
								                </c:if>
								                <c:if test="${empty v.areaName}">
								                	<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.roomName }&nbsp;</td>
								                </c:if>
											</tr>
											</c:forEach>
											</c:otherwise>
											</c:choose>
					             		</table>
					             		</div>
									</td>
		     		 			</tr>
								<tr>
								  <td>合计：
								  <script>document.write(parseFloat(${totalMoney }).toFixed(2).toString());</script>
								  元</td>
								</tr>
								<!-- 分页start -->
								<tr>
									<td colspan="5">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr>
												<td class="form_line3">&nbsp;</td>
												<td class="form_line3" colspan="8">${pagination }</td>
												<td class="form_line3">&nbsp;</td>
											</tr>
										</table>
									</td>
								</tr>
								<!-- 分页end -->
								<tr>
									<td align="right">
										<table>
											<tr>
											    <c:choose>
											    <c:when test="${empty list}">
											    <td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="alert('无记录,不能导出报表!')"></td>
											    </c:when>
											    <c:otherwise>
											    <td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="explorReport()"></td>
											    </c:otherwise>
											    </c:choose>
					              			</tr>
					              		</table>
					              	</td>
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
