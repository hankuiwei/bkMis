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
    
    <title>查看出库单信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/validate.js"></script>

  </head>
  
  <body>
    <form action="" name="viewForm" method="post">
    	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">查看出库单信息</td>
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
					         	<c:forEach items="${outputList}" var="output">
					         	<tr>
									<td align="left" class="head_one" colspan="4"><font size="2">出库单</font></td>
								</tr>
					          	<tr>
					            	<td align="right" class="head_left">领用人员：</td>
					            	<td class="head_form1">&nbsp;
					            		<input type="text" id="man" name="man" value="${output.man }" disabled="disabled"/>
					            	</td>
					            	<td align="right" class="head_form1">所属部门：</td>
					            	<td class="head_form1">&nbsp;
					            		<select id="department" name="department" disabled="disabled">
					            			<option value="">--请选择--</option>
					            			<c:forEach items="${departList}" var="depart">
					            				<option value="${depart.codeValue }"
					            					<c:if test="${output.department == depart.codeValue }">
					            						selected
					            					</c:if>
					            				>
					            					${depart.codeName }
					            				</option>
					            			</c:forEach>
					            		</select>
					            	</td>
								</tr>
								<tr>
					            	<td align="right" class="head_left">出库单编号：</td>
					            	<td class="head_form1">&nbsp;
					            		<input type="text" id="codes" name="codes" value ="${output.code }" disabled="disabled">
					            		<input type="hidden" id="code" name="code" value ="${output.code }">
					            	</td>
					            	<td align="right" class="head_form1">出库时间：</td>
					            	<td class="head_form1">&nbsp;
					            		<input type="text" name="date" id="date" value="${output.date }" disabled="disabled"/>
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
					              			</tr>
					              			<tr>
								                <th class = "RptTableHeadCellFullLock">序号</th>
												<th class = "RptTableHeadCellFullLock">材料编号</th>
												<th class = "RptTableHeadCellLock">材料名称</th>
												<th class = "RptTableHeadCellLock">规格</th>
												<th class = "RptTableHeadCellLock">可用库存数</th>
												<th class = "RptTableHeadCellLock">入库单价</th>
												<th class = "RptTableHeadCellLock">出库数量</th>
												<th class = "RptTableHeadCellLock">单位</th>
												<th class = "RptTableHeadCellLock">金额</th>
											</tr>
											<c:choose>
											<c:when test="${empty inoutputList}">
											</c:when>
											<c:otherwise>
												<c:forEach items="${inoutputList}" var="inout" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';" id="tr1">
														<td class = "RptTableBodyCellLock" align="center" title="${vs.count }">${vs.count }</td>
														<td class="RptTableBodyCellLock" align="center" title="${inout[1] }">${inout[1] }&nbsp;
														</td>
														<td class="RptTableBodyCell" align="center" title="${inout[2] }">${inout[2] }&nbsp;
														</td>
														<td class="RptTableBodyCell" align="center" title="${inout[3] }">${inout[3] }&nbsp;
														</td>
														<td class="RptTableBodyCell" align="center" title="${inout[4] }">${inout[4] }&nbsp;
														</td>
														<td class="RptTableBodyCell" align="center" title="${inout[0].unitPrice }"><script>document.write(formatNum(parseFloat(${inout[0].unitPrice }).toFixed(2).toString()));</script>&nbsp;
														</td>
														<td class="RptTableBodyCell" align="center" title="${inout[0].amount }">${inout[0].amount }&nbsp;
														</td>
														<td class="RptTableBodyCell" align="center" title="${inout[6] }">${inout[6] }&nbsp;
														</td>
														<td class="RptTableBodyCell" align="center" title="${inout[0].amountMoney }"><script>document.write(formatNum(parseFloat(${inout[0].amountMoney }).toFixed(2).toString()));</script>&nbsp;</td>
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
										<input type="text" name="money" id="money" value="${totalMoney }" disabled="disabled"/>
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
		window.open("<%=path%>/outputmanage.do?method=outputListPrint&inoutCode="+document.getElementById("code").value);
	}
  </script> 
  
</html>
