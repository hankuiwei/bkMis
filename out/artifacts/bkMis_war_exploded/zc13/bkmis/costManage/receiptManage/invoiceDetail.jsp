<%@ page language="java" import="com.zc13.util.*"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>发票详情</title>
	<base href="<%=basePath%>" target="_parent">
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript">
	</script>
	
</head>
<body>
<form name = "form1" method="post">
	<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0">

				<tr>
    				<td height="5"></td>
  				</tr>
		  		<tr>
		    		<td>
		    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      				<tr>
		        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">发票详情</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
		        			</tr>
		    			</table>
		    		</td>
		  		</tr>
				<tr>
					<td class="menu_tab2" align="center">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="5" align="center">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="10" colspan="1"></td>
										</tr>

										<tr>
											<td>
												<table width="80%" align="center" border="0" cellpadding="3"
													cellspacing="0" class="form_tab">
													<tr>
														<td class="head_rols" align="right" width="20%">
															客户名称：
														</td>
														<td class="fist_rows" width="30%">
															&nbsp;${invoiceDetail.clientName }
 														</td>
 														<td class="head_rols" align="right" width="20%">
															发票号：
														</td>
														<td class="fist_rows" width="40%">
															&nbsp;${invoiceDetail.invoiceNum }
 														</td>
													</tr>
													<tr>
														<td class="head_rols" align="right">
															操作员：
														</td>
														<td class="fist_rows">
															&nbsp;${invoiceDetail.realName }
 														</td>
 														<td class="head_rols" align="right">
															操作日期：
														</td>
														<td class="fist_rows">
															&nbsp;${invoiceDetail.date }
 														</td>
													</tr>
													<tr>
														<td class="head_rols" align="right">
															总金额：
														</td>
														<td class="fist_rows" colspan="3">
															&nbsp;<script>document.write(formatNum(parseFloat(${invoiceDetail.totalInvoiceAmount }).toFixed(2).toString()));</script>
 														</td>
													</tr>
													
													<c:if test="${!empty invoiceForm.invoiceList}">
													<tr><td width="100%" colspan="4">
						  								<div id="div1" class="RptDiv"  >
		  													<table width="100%"  cellpadding="0" cellspacing="0" class="">
			    												<tr>
			    													<td class="RptTableHeadCellFullLock">收据号</td>
			    													<td class="RptTableHeadCellLock">收款员</td>
			    													<td class="RptTableHeadCellLock">收款日期</td>
			    													<td class="RptTableHeadCellLock">发票内容</td>
			    													<td class="RptTableHeadCellLock">项目明细</td>
			    													<td class="RptTableHeadCellLock">发票金额</td>
			    													<td class="RptTableHeadCellLock">对应账单金额</td>
			    												</tr>
			    												<c:forEach items="${invoiceForm.invoiceList}" var="v" varStatus="vs"	>
			    													<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" >
																		<td class="RptTableBodyCellLock">&nbsp;${v.billNum }</td>
																		<td class="RptTableBodyCell">&nbsp;${v.realName }</td>
																		<td class="RptTableBodyCell">&nbsp;${v.date }</td>
																		<td class="RptTableBodyCell">&nbsp;${v.invoiceContent }</td>
																		<td class="RptTableBodyCell">&nbsp;${v.itemName }</td>
																		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v.invoiceAmount }).toFixed(2).toString()));</script></td>
																		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v.actuallyPaid }).toFixed(2).toString()));</script></td>
																	</tr>
			    												</c:forEach>
			    											</table>
			    										</div></td>	
													</tr>
													</c:if>
												</table>
											</td>
										</tr>
										<tr>
											<td height="10" colspan="" align="center"><input type="button" value="返回" onclick="window.close();" class="button"></td>
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
