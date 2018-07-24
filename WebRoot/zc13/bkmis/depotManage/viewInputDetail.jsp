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
    
    <title>查看入库单信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/validate.js"></script>

  </head>
  
  <body>
     <form  method="post" name="form1">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">查看入库单信息</td>
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
									<td align="left" class="head_one" colspan="4">入库单</td>
								</tr>
								<c:forEach items="${inputList}" var="il">
					          	<tr>
					            	<td align="right" class="head_left">采购人员：</td>
					            	<td class="head_form1">&nbsp;
					            		<input type="text" id="man" name="man" value="${il.man }" disabled="disabled"/>
					            		<input type="hidden" id="id" name="id" value="${il.id }"/>
					            	</td>
					            	<td align="right" class="head_form1">供应商：</td>
					            	<td class="head_form1">&nbsp;
					            		<select name="supplierId" id="supplierId" disabled="disabled">
					            			<option value="">--请选择--</option>
					            			<c:forEach items="${supplierList}" var="sl">
					            				<option value="${sl[1] }"
					            					<c:if test="${il.supplierId == sl[1] }">selected</c:if>
					            				>
					            					${sl[1] }
					            				</option>
					            			</c:forEach>
					            		</select>
					            	</td>
								</tr>
								<tr>
					            	<td align="right" class="head_left">入库单编号：</td>
					            	<td class="head_form1">&nbsp;
					            		<input type="text" id="code1" name="code1" value ="${il.code }" disabled="disabled">
					            		<input type="hidden" id="code" name="code" value ="${il.code }" >
					            	</td>
					            	<td align="right" class="head_form1">入库时间：</td>
					            	<td class="head_form1">&nbsp;
					            		<input type="text" name="date" id="date" value="${il.date }" disabled="disabled"/>
					            	</td>
								</tr>
								<tr>
					            	<td align="right" class="head_left">发票编号：</td>
					            	<td class="head_form1" colspan="3">&nbsp;
					            		<input type="text" id="invoiceCode" name="invoiceCode" value ="${il.invoiceCode }" disabled="disabled">
					            	</td>
								</tr>
								</c:forEach>
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
								<tr>
					              	<td colspan="1" align="left" class="head_one">材料明细</td>
					            </tr>
								<tr height = "95%">
					        		<td width="100%">
					        		<div id = "div1" class = "RptDiv"  >
							   			<table class = "RptTable" border="0" cellpadding="0" cellspacing="0" id="tbl">
					              			<tr>
												<th class = "RptTableHeadCellFullLock">序号</th>
												<th class = "RptTableHeadCellFullLock">材料编号</th>
												<th class = "RptTableHeadCellLock">材料名称</th>
												<th class = "RptTableHeadCellLock">规格</th>
												<th class = "RptTableHeadCellLock">单价</th>
												<th class = "RptTableHeadCellLock">入库数量</th>
												<th class = "RptTableHeadCellLock">单位</th>
												<th class = "RptTableHeadCellLock">库存金额</th>
											</tr>
											<c:choose>
											<c:when test="${empty inoutputList}">
											</c:when>
											<c:otherwise>
												<c:forEach items="${inoutputList}" var="il" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
														<td class = "RptTableBodyCellLock" align="center" title="${vs.count }">${vs.count }&nbsp;</td>
														<td class="RptTableBodyCellLock" align="center" title="${il[1] }">${il[1] }&nbsp;
														</td>
														<td class="RptTableBodyCell" align="center" title="${il[2] }">${il[2]}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${il[3] }">${il[3]}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${il[0].unitPrice }"><script>document.write(formatNum(parseFloat(${il[0].unitPrice }).toFixed(2).toString()));</script>&nbsp;
														</td>
														<td class="RptTableBodyCell" align="center" title="${il[0].amount }">${il[0].amount }&nbsp;
														</td>
														<td class="RptTableBodyCell" align="center" title="${il[5]}">${il[5] }&nbsp;
														</td>
														<td class="RptTableBodyCell" align="center" title="${il[0].amountMoney }" id="rs${il[0].id }"><script>document.write(formatNum(parseFloat(${il[0].amountMoney }).toFixed(2).toString()));</script>&nbsp;</td>
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
									<td>总金额：
										<input type="text" name="money" value="${totalMoney }" disabled="disabled" id="money"/>
										<input type="hidden" name="oldMoney" id="oldMoney" value="${totalMoney }">
									</td>
								</tr>
								<tr>
									<td align="center">
										<input type="button" class="button" value="返回" onclick="javascript:history.go(-1)"/>
										<input type="button" class="button" value="打印" onclick="javascript:print1();"/>
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
 	function print1(){
 		window.open("<%=path%>/inputmanage.do?method=inputListPrint&inoutCode="+document.getElementById("code").value);
	}
</script>
</html>
