<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ page import="com.zc13.util.*"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	List publicMeterList = (List)request.getAttribute("list");
	request.getSession().setAttribute("publicMeterList",publicMeterList);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>表具抄录</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <!-- 使用日期控件时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<!-- 使用单元格在线编辑功能时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<style type="text/css">
	<!--
	.Rpt1{
		width: 100%;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 10pt;
		border-top:4px #266898 double;
		border-left:1px #b2c2c9 solid;
	}
	-->
	</style>
	<c:if test="${!empty alertMessage}">
	<script type="text/javascript">
		alert("${alertMessage}");
	</script>
	</c:if>
	<script type="text/javascript">
	</script>
	<script type="text/javascript">
	//导出报表
		function explortExcel(){
			window.location.href = "<%=path%>/meterInput.do?method=explortPublicExcel2";
		}
		
		//关闭窗口执行的操作
		function closeWindows(){
			delList();
			//setInterval("delList()",5000);
			opener.location.reload();
		}
		
		//删除session中的list
		function delList(){
			<%
			request.getSession().removeAttribute("userMeterList");
			request.getSession().removeAttribute("publicMeterList");
			%>
			//window.location.href = "<%=path%>/meterInput.do?method=deleteSessionInfo";
		}
		
		//查询
		function query(){
			document.roomform.action = "<%=path%>/meterInput.do?method=getPublicReadMeterByYearAndId";
			document.roomform.submit();
		}
	</script>
</head>
	<body style="" onUnLoad="closeWindows();">
		<form method="post" name="roomform">
			<table width="99%" height="96%" border="0" align="center"
				cellpadding="0" cellspacing="0" style="layout: fixed">
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">表具明细</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr height="95%">
					<td class="menu_tab2" align="center" valign="top">
						<table width="100%" height="100%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td align="center">
									<!-- 查询条件start -->
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
									  	<tr>
							            	<td height="10" colspan="6"></td>
							         	</tr>
							         	<tr>
							            	<td height="10" colspan="3">
							            		&nbsp;&nbsp;期间：${years }年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公摊表具：${meterName}
							            	</td>
							            	<td colspan="2">
							            		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;表具编号：<input type="text" style="width:100px;" name="meterCode" value="${meterCode }" />
							            	</td>
							            	<td>
							            		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button" id = "focuson" onclick="query();" value="查询">
							            	</td>
							         	</tr>
							         	<tr>
							            	<td height="10" colspan="6">
							           		 	<input type="button" class="button" id = "focuson" onclick="explortExcel();" value="导出报表">
							            	</td>
							         	</tr>
							          	
							          	 <tr>
							            	<td height="10" colspan="6"></td>
							         	</tr>
							        </table>
								</td>
							</tr>
							<tr height="95%">
								<td valign="top">
									<!-- 表单内容区域 -->
									<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed">
										<tr height="95%">
											<td width="100%">
												<div id="div1" class="RptDiv">
													<table border="0" cellpadding="0" cellspacing="0" class="RptTable" id="tab">
														<tr>
														    <th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">表具编号</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">年月</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">开始日期</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">结束日期</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">上月读表</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">本月读表</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">本月用量</th>
														</tr>
														<c:choose>
															<c:when test="${empty list}">
																
															</c:when>
															<c:otherwise>
																<c:forEach items="${list}" var="c" varStatus="vs">
																	<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
																		<td class="RptTableBodyCellLock"  align="center">${vs.index+1 }</td>
																		<td class="RptTableBodyCell">${c.code }</td>
																		<td class="RptTableBodyCell">${c.yearMonth }</td>
																		<td class="RptTableBodyCell">${c.beginDate }</td>
																		<td class="RptTableBodyCell">${c.endDate }</td>
																		<td class="RptTableBodyCell">${c.lastRecord }</td>
																		<td class="RptTableBodyCell">${c.thisRecord }</td>
																		<td class="RptTableBodyCell">
																		<script>
																			document.write(parseFloat(${c.thisRecord-c.lastRecord }).toFixed(2).toString());
																		</script>
																		</td>
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
