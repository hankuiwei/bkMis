<%@ page language="java" import="com.zc13.util.*"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
String message = GlobalMethod.NullToSpace((String)request.getAttribute("message"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>电话费用信息</title>
	<base href="<%=basePath%>" target="_parent">
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript">
	<%if(!message.equals("")){%>
		alert("<%=message%>");
	<%}%>
	</script>
	<script type="text/javascript">
		//清空查询条件
		function clears(){
			document.getElementById("cxEndDate").value="";
			document.getElementById("cxStartDate").value="";
		}
		
		//手动生成话单
		function buildCallInfo(){
			document.form1.action="<%=path%>/phoneCost.do?method=buildCallInfo";
			document.form1.submit();
			setTimeout("showLoadingDiv()",100);
		}
		//更新话费
		function updatePhoneCost(){
			document.form1.action="<%=path%>/phoneCost.do?method=updatePhoneCost";
			document.form1.submit();
			setTimeout("showLoadingDiv()",100);
		}
		//更新地区名称
		function updateAreaName(){
			document.form1.action="<%=path%>/phoneCost.do?method=updateAreaName";
			document.form1.submit();
			setTimeout("showLoadingDiv()",100);
		}
	</script>
	
</head>
<!-- 加载页面div -->
<jsp:include page="../../../loading.jsp"></jsp:include>
<!-- 加载页面div -->
<body onunload="window.returnValue = 'flag';">
	
	<form name = "form1" method="post">
		<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
		<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
    				<td height="5"></td>
  				</tr>
		  		<tr>
		    		<td>
		    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      				<tr>
		        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">批量更新电话计费信息</td>
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
											<td height="10" colspan="9"></td>
										</tr>

										<tr>
											<td>
												<table align="center" border="0" cellpadding="3"
													cellspacing="0" class="form_tab">
													<tr>
														<td class="head_rols" align="right">
															开始时间：
														</td>
														<td class="fist_rows">
															<input type="text" name="cxStartDate" id="cxStartDate" readonly onclick="WdatePicker()" value="${phoneCostForm.cxStartDate }" class="Wdate"/>
														</td>
														<td class="head_rols" align="right">
															结束时间：
														</td>
														<td class="fist_rows">
															<input type="text" name="cxEndDate" id="cxEndDate" readonly onclick="WdatePicker()" value="${phoneCostForm.cxEndDate }" class="Wdate"/>
															&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="clears()" class="button" alt="清空开始时间和结束时间" value="清空">
														</td>
													</tr>
													<tr>
														<td align="center" colspan="4" class="head_left">
															<input type="button" onclick="buildCallInfo()" class="button"  value="手动生成话单">&nbsp;&nbsp;
															<input type="button" onclick="updatePhoneCost()" class="button" alt="根据资费标准更新指定时间段内的话费金额" value="更新话费">&nbsp;&nbsp;
															<input type="button" onclick="updateAreaName()" class="button" alt="根据区号设置更新指定时间段内的地区名称" value="更新地区名称">
														</td>
													</tr>
													<tr>
														<td align="left" colspan="4" class="head_left">
															<span style=" color: blue;">
																说明：<br>1.“手动生成话单”：手动将指定时间段内没有同步到数据库的话单同步进去(开始时间在此处不起作用)；
																<br>2.“更新话费”：根据最新的资费标准更新指定时间段内的话费金额，如果未指定时间段，则默认更新所有；
																<br>3.“更新地区名称”：根据最新的区号设置更新指定时间段内的地区名称，如果未指定时间段，则默认更新所有。
															</span>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="10" colspan="9"></td>
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
