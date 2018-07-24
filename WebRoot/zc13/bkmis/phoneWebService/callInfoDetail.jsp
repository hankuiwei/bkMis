<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
	<title>电话计费列表</title>
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		//将秒转换成时分秒
		function formatSeconds(value) {   
			var theTime = Number(value);   
			var theTime1 = 0;   
			var theTime2 = 0;   
			//alert(theTime);   
			if(theTime > 60) {   
			    theTime1 = Number(theTime/60);   
			    theTime = Number(theTime%60);   
			   //alert(theTime1+"-"+theTime);   
				if(theTime1 > 60) {   
				   theTime2 = Number(theTime1/60);   
				   theTime1 = Number(theTime%60);   
				}   
			}   
			var result = ""+(theTime>9?theTime:"0"+theTime)+"：";   
			if(theTime1 > 0) {   
			    result = ""+(parseInt(theTime1)>9?parseInt(theTime1):"0"+parseInt(theTime1))+"："+result;   
			}else{
				result = "00："+result;   
			}   
			if(theTime2 > 0) {   
			    result = ""+(parseInt(theTime2)>9?parseInt(theTime2):"0"+parseInt(theTime2))+"："+result;   
			}else{
				result = "00："+result;
			}
			if(result!=null&&result!=""){
				result = result.substring(0,result.length-1);
			}
			return result;   
		} 
	
      	function exportExcel() {
			actionForm.action = "<%=path%>/phoneCost.do?method=exportPhoneInfo";
			actionForm.target = "_self";
			actionForm.submit();
		}
		
		function goBack() {
			actionForm.action = "<%=path%>/bill.do?method=getBillList";
			actionForm.target = "_self";
			actionForm.submit();
		}
    </script>
</head>
<body>
<!-- 加载页面div -->
	<jsp:include page="/loading.jsp"></jsp:include>
	<!-- 加载页面div -->
<form  method="post" name="actionForm" action = "">
	<input type=hidden name="billid" value="${phoneCostForm.id }" />
	<input type="hidden" name="clientId" value="${phoneCostForm.clientId }">
	<input type="hidden" name="itemId" value="${phoneCostForm.itemId }">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">电话费用详细</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr height = "95%">
    		<td class="menu_tab2" align="center" valign="top">
     			<table width="100%" height = "100%"  border="0" cellspacing="0" cellpadding="0">
  					<tr height="95%">
					    <td valign = "top">
					    	<!-- 表单内容区域 -->
							<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
								<tr height = "95%">
					        		<td width="100%">
					        		<div id = "div1" class = "RptDiv"  >
							   			<table class = "RptTable" border="0" cellpadding="0" cellspacing="0" id = "table1">
					              			<tr >
												<th width = "10%" class = "RptTableHeadCellFullLock">序号</th>
												<th width = "10%" class = "RptTableHeadCellLock">通话号码</th>
												<th width = "10%" class = "RptTableHeadCellLock">通话开始时间</th>
												<th width = "10%" class = "RptTableHeadCellLock">通话结束时间</th>
												<th width = "10%" class = "RptTableHeadCellLock">通话时长</th>
												<th width = "15%" class = "RptTableHeadCellLock">地区名称</th>
												<th width = "15%" class = "RptTableHeadCellLock">对方号码</th>
												<th width = "15%" class = "RptTableHeadCellLock">本次通话费用（￥）</th>
												<th width = "15%" class = "RptTableHeadCellLock">缴费情况</th>
											</tr>
											<c:choose>
											<c:when test="${empty calllist}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${calllist}" var="v" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
														<td class="RptTableBodyCell" align="center" title="${vs.count}">${vs.count}</td>
														<td class="RptTableBodyCell" align="center">${v.callerPhone }</td>
														<td class="RptTableBodyCell" align="center">${v.startTime }</td>
														<td class="RptTableBodyCell" align="center">${v.endTime }</td>
														<td class="RptTableBodyCell" align="center"><script>document.write(formatSeconds(${v.callTime }))</script></td>
														<td class="RptTableBodyCell" align="center">${v.areaName }</td>
														<td class="RptTableBodyCell" align="center">${v.calledPhone }</td>
														<td class="RptTableBodyCell" align="center">${v.cost }</td>
														<td class="RptTableBodyCell" align="center">${v.isPaid }</td>
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
									<td>
										总金额：<script>document.write(parseFloat("${phoneCostForm.totalCost }").toFixed(2).toString());</script>
									</td>
								</tr>
								<!-- 分页start -->
								<tr>
									<td colspan="10">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr><td class="form_line3">&nbsp;</td>
												<td class="form_line3" colspan="8">${pagination }</td>
												<td class="form_line3">&nbsp;</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td align="right">
										<table>
											<tr>
					               				<c:if test="${!empty calllist}">
													<td nowrap="nowrap"><input type="button" onclick="exportExcel();" class="button" value="导出excel"></td>
												</c:if>
												<td nowrap="nowrap"><input type="button" onclick="goBack();" class="button" value="返回"></td>
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
