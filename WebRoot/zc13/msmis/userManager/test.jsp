<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>My JSP 'user_Manager.jsp' starting page</title>
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript"
			src="<%=path%>/resources/js/util/util.js"></script>

		<script type="text/javascript"
			src="<%=path%>/resources/js/util/jquery.js"></script>

	</head>

	<body>
		<form method="post" name="actionForm">
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0">

				<tr>
					<!-- 距离页面顶部的距离 -->
					<td height="10"></td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg"	width="14" height="35">
								</td>
								<td class="form_line" nowrap="nowrap">
									货品信息维护
								</td>
								<td width="5"><img src="<%=path%>/resources/images/index_39.jpg" width="5" height="35">
								</td>
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
</html>