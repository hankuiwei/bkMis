<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="loading">
	<table width=100% height=100%  cellspacing="0" cellpadding="0">
		<tr align="center" valign="middle">
			<td><img src="<%=path %>/resources/images/loading1.gif"  />
			<br />
			<span style="font: 14px;color:blue">正在分析数据，请稍候...</span></td>
		</tr>
	</table>
</div>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>入库明细表</title>
    
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		function searchDetail(){
			document.inDepotForm.action = "<%=path%>/inputmanage.do?method=showDetailIutput";
			document.inDepotForm.submit();
		}
		//导出报表
		function explortReport(){
			document.inDepotForm.action = "<%=path%>/inputmanage.do?method=exportInputDetailExcel";	
			document.inDepotForm.submit();	
		}
		//全选
		function selectedAll(){
			var parentBox = document.getElementById("parentBox");
			var childBox = document.getElementsByName("childbox");
			if(parentBox.checked == true){
				for(var i = 0;i<childBox.length;i++){
					childBox[i].checked = true;
				}
			}else{
				for(var i = 0;i<childBox.length;i++){
					childBox[i].checked = false;
				}
			}
		}
	</script>
  </head>
  
  <body onLoad="hideLoadingDiv();">
    <form  action="" method="post" name="inDepotForm">
    <input type="hidden" name="dmtId" id="dmtId" value="${inputForm.dmtId }"/>
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">材料入库明细表</td>
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
	 				<tr>
						<td  align="center">
							<!-- 查询条件start -->
		  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
					            	<td height="10"></td>
					         	</tr>
					          	<tr>
					           		<td class="txt" nowrap="nowrap">
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					           		</td>
					            	<td nowrap="nowrap" align="left">入库单编号：</td>
					            	<td nowrap="nowrap"><input type="text" name="inoutCode" id="inoutCode" value="${inputForm.inoutCode}"/></td>
									<td align = "left" nowrap="nowrap">入库时间：
										<input type="text" name="startDate" id="startDate" readonly onclick="WdatePicker()" value="${inputForm.startDate }" class="Wdate"/>
										&nbsp;至&nbsp;
										<input type="text" name="endDate" id="endDate" readonly onclick="WdatePicker()" value="${inputForm.endDate }" class="Wdate"/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;供应商：
										<select id="supplierId" name="supplierId" onchange="searchDetail()">
											<option value="">
					            				--请选择--
					            			</option>
					            			<c:forEach items="${supplierList}" var="sl">
					            				<option value="${sl[1] }"
					            					<c:if test="${select eq sl[1] }">
					            					selected
					            					</c:if>
					            				>
					            					${sl[1] }
					            				</option>
					            			</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td class="txt" nowrap="nowrap"></td>
									<td nowrap="nowrap" align="left">材料名称：</td>
									<td nowrap="nowrap">
										<input type="text" name="materName" id="materName" value="${inputForm.materName }"/>
									</td>
									<td nowrap="nowrap" align="left">材料编号：
										<input type="text" name="materCode" id="materCode" value="${inputForm.materCode }"/>
									</td>
									<td align="right" nowrap="nowrap">
					            		<input type="button" id = "focuson" class="button" value="确定" name="search" onclick="searchDetail()">
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
							   			<table class = "RptTable" border="0" cellpadding="0" cellspacing="0">
					              			<tr>
								                <th class = "RptTableHeadCellFullLock"><input type="checkbox" id="parentBox" name="parentBox" onclick="selectedAll()"></th>
												<th class = "RptTableHeadCellFullLock">序号</th>
												<th class = "RptTableHeadCellFullLock">入库单编号</th>
												<th class = "RptTableHeadCellLock">材料名称</th>
												<th class = "RptTableHeadCellLock">材料编号</th>
												<th class = "RptTableHeadCellLock">规格</th>
												<th class = "RptTableHeadCellLock">入库数量</th>
												<th class = "RptTableHeadCellLock">单位</th>
												<th class = "RptTableHeadCellLock">单价</th>
												<th class = "RptTableHeadCellLock">金额小计</th>
												<th class = "RptTableHeadCellLock">入库人员</th>
												<th class = "RptTableHeadCellLock">入库时间</th>
												<th class = "RptTableHeadCellLock">供应商</th>
											</tr>
											<c:choose>
											<c:when test="${empty inputForm.inputDatilList}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有相应入库表的详细信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												  <c:forEach items="${inputForm.inputDatilList}" var="dl" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
														<td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="childBox" name="childBox" value="${dl.id}"></td>
														<td class="RptTableBodyCellLock" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
														<td class="RptTableBodyCellLock" align="center" title="${dl.inoutCode}">${dl.inoutCode}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.name}">${dl.name}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.code}">${dl.code}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.spec}">${dl.spec}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.amount}">${dl.amount}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.unit}">${dl.unit}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.unitPrice}"><script>document.write(formatNum(parseFloat(${dl.unitPrice}).toFixed(2).toString()));</script>&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.amountMoney}"><script>document.write(formatNum(parseFloat(${dl.amountMoney}).toFixed(2).toString()));</script>&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.man}">${dl.man}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.date}">${dl.date}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.suplyName}">${dl.suplyName}&nbsp;</td>
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
										<c:forEach items="${summaryList}" var="total">
											入库总数量：${total.amount }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											入库总金额：<script>document.write(formatNum(parseFloat(${total.totalMoney }).toFixed(2).toString()));</script>
										</c:forEach>
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
					                			<td colspan="6" align="left">
													<c:choose>
													<c:when test="${empty inputForm.inputDatilList}">
													<input type="button" name="report" id="report" class="button" value="导出报表" onclick="alert('无记录,不能导出报表!')"/>
													</c:when>
													<c:otherwise>
													<input type="button" name="report" id="report" class="button" value="导出报表" onclick="explortReport()"/>
													</c:otherwise>
													</c:choose>													
												<td nowrap="nowrap"><input type="button" class="button" value="打印入库单" onclick="print()"></td>												
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
 <script type="text/javascript">
 function print(){
		inDepotForm.action = "<%=path%>/inputmanage.do?method=inputListPrint";  
		inDepotForm.target = "_blank";		
  		inDepotForm.submit();
	}
</script> 
</html>
