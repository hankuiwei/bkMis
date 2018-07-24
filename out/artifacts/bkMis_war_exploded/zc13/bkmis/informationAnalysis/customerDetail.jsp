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
    
    <title>查看客户详细信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/validate.js"></script>
	<script type="text/javascript">
		//按条件查询详细信息
		function selectDatail(){
			document.detailForm.action = "analysisCus.do?method=selectCustomerDetail";
			document.detailForm.submit();
		}
		//导出报表
		function exportReport(){
			document.detailForm.action = "analysisCus.do?method=exportCusDetailExcel";
			document.detailForm.submit();
		}
		//复选框的全选
		function selectAll(){
			var parentBox = document.getElementById("parentBox");
			var childBox = document.getElementsByName("childBox");
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
  
  <body style="overflow-y:hidden;">
     <form action="" method="post" name="detailForm">
     	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">客户详细信息</td>
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
					         		<td colspan="4" align="left" class="head_one">客户信息统计</td>
					         	</tr>
					         	<c:forEach var="al" items="${accountList}">
					          	<tr>
					            	<td align="left" nowrap="nowrap" class="head_left">创建时间：
					            	<input type="hidden" value="${detailId }" name="detailId" id="detailId"/>
					            	<input type="text" name="createDate" id="createDate" readonly value="${al.createDate}" class="Wdate"/></td>
									<td align = "left" nowrap="nowrap" class="head_form1">开始时间：
										<input type="text" name="beginDate" id="beginDate" readonly value="${al.beginDate }" class="Wdate"/>
										&nbsp;至&nbsp;
										<input type="text" name="endDate" id="endDate" readonly value="${al.endDate }" class="Wdate"/>
									</td>
									<td align = "left" nowrap="nowrap" class="head_form1">条件查询：
										<select name="paramType" id="paramType" onchange="selectDatail()">
											<option value="all" 
													<c:if test="${paramerValue eq 'all' }">selected</c:if>
											>--全部--</option>
											<option value="between"
													<c:if test="${paramerValue eq 'between' }">selected</c:if>
											>本段时间内入住客户</option>
										</select>
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
								<tr height = "95%">
					        		<td width="100%">
					        		<div id = "div1" class = "RptDiv"  >
							   			<table class = "RptTable" border="0" cellpadding="0" cellspacing="0">
					              			<tr>
								                <th class = "RptTableHeadCellFullLock"><input type="checkbox" id="parentBox" name="parentBox" onclick="selectAll();"></th>
												<th class = "RptTableHeadCellFullLock">序号</th>
												<th class = "RptTableHeadCellFullLock">客户名称</th>
												<th class = "RptTableHeadCellLock">客户房间</th>
												<th class = "RptTableHeadCellLock">客户类别</th>
												<th class = "RptTableHeadCellLock">房间面积</th>
												<th class = "RptTableHeadCellLock">入住时间</th>
												<th class = "RptTableHeadCellLock">迁出时间</th>
											</tr>
											<c:choose>
											<c:when test="${empty detailList}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有相应客户详细信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${detailList}" var="dl" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
														<td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="childBox" name="childBox" value=""></td>
														<td class="RptTableBodyCellLock" align="center" title="${vs.count }">${vs.count }&nbsp;</td>
														<td class="RptTableBodyCellLock" align="center" title="${dl.customerName }">${dl.customerName }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.roomFullName }">${dl.roomFullName }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.clientType }">${dl.clientType }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.userArea }">${dl.userArea }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.beginDate }">${dl.beginDate} &nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.endDate }">${dl.endDate }&nbsp;</td>
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
									<td align="center">
										<table>
											<tr>
												<td><input type="button" class="button" value="导出报表" onclick="exportReport()"></td>
					               				<td><input type="button" class="button" value="返回" onclick="javascript:window.location.href='analysisCus.do?method=showAnalysis'"></td>
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
