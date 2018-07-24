<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.Contants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <!-- 刷新父页面 -->
		<base href="<%=basePath%>" target="_parent">
		<title>业务流程图查看</title>
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
	    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
	    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
		
	</head>
	<body>
		<form method="post" name="form1">
		<c:forEach var="p" items="${taskList}">
			<input type="hidden" id="${p.code}" value="${p.name}">
		</c:forEach>
		<input type="hidden" name="password" value="<%=Contants.DEFAULTPASSWORD %>">
			<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
		  		<tr>
		    		<td>
		    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      				<tr>
		        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">系统业务流程</td>
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
							<tr><td height="10" colspan="9"></td></tr>
							<tr>
								<td>
									<table align="center" border="0" cellpadding="3" cellspacing="0" class="form_tab">
									<!-- 意向书部分图片的显示区域begin -->
										<tr>
											<td nowrap="nowrap" align="center">意向书：</td>
												<c:forEach var="p" items="${taskList}" varStatus="c">
													<c:if test="${c.count<5}">
											<td  align="center"><img onMouseOver="runtimeStyle.filter='alpha(opacity=100)'" onMouseOut="runtimeStyle.filter='alpha(opacity=50)'" style="cursor:pointer;" onclick="set('${p.url}');" src="${pageContext.request.contextPath }${p.mapurl }"/></td>
													</c:if>
													<c:if test="${c.count<4}">
											<td><img src="<%=path%>/resources/afterImage/111.png"/></td>
													</c:if>
												</c:forEach>
										</tr>
									<!-- 意向书部分图片的显示区域end -->
									<!-- 意向书部分文字以及任务个数的显示区域begin -->
										<tr>
											<td align="center"></td>
												<c:forEach var="p" items="${taskList}" varStatus="c">
													<c:if test="${c.count<5}">
											<td  align="center">
												<!-- 如果任务个数大于0，则显示小叹号的图标begin -->
												<c:if test = "${p.amount > 0}">
				           							<img height="15px" width="15px" src="<%=path%>/resources/images/Alert_04.png"/>
				           						</c:if>
												<!-- 如果任务个数大于0，则显示小叹号的图标end -->
												<font color="red">${p.amount}</font>${p.description}
											</td>
													</c:if>
													<c:if test="${c.count<4}">
											<td></td>
													</c:if>
												</c:forEach>
										</tr>
									<!-- 意向书部分文字以及任务个数的显示区域end -->
									<!-- 合同部分图片的显示区域begin -->
										<tr>
											<td><img src="<%=path%>/resources/afterImage/111.png"/></td>
										</tr>
										<tr>
											<td nowrap="nowrap" align="center">合同：</td>
												<c:forEach var="p" items="${taskList}" varStatus="c">
													<c:if test="${c.count>4}">
											<td align="center"><img style="cursor:pointer;" onMouseOver="runtimeStyle.filter='alpha(opacity=100)'" onMouseOut="runtimeStyle.filter='alpha(opacity=40)'" onclick="set('${p.url}');" src="${pageContext.request.contextPath }${p.mapurl }"/></td>
													</c:if>
													<c:if test="${c.count<10 && c.count>4}">
											<td><img src="<%=path%>/resources/afterImage/111.png"/></td>
													</c:if>
												</c:forEach>
										</tr>
									<!-- 合同部分图片的显示区域end -->
									<!-- 合同部分文字以及任务个数的显示区域begin -->
										<tr>
											<td align="center"></td>
												<c:forEach var="p" items="${taskList}" varStatus="c">
													<c:if test="${c.count>4}">
											<td  align="center">
												<!-- 如果任务个数大于0，则显示小叹号的图标begin -->
												<c:if test = "${p.amount > 0}">
				           							<img height="15px" width="15px" src="<%=path%>/resources/images/Alert_04.png"/>
				           						</c:if>
												<!-- 如果任务个数大于0，则显示小叹号的图标end -->
												<font color="red">${p.amount}</font>${p.description}
											</td>
													</c:if>
													<c:if test="${c.count<10 && c.count>4}">
											<td></td>
													</c:if>
												</c:forEach>
										</tr>
									<!-- 合同部分文字以及任务个数的显示区域end -->
									</table>
								</td>
							</tr>
							<tr><td height="10" colspan="9"></td></tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
		<script type="text/javascript">
      		function set(url){
      			if("${pageContext.request.contextPath }"!="/"){
      				url = url.substring(9,url.length);
      			}
      			window.location="${pageContext.request.contextPath }/"+url;
      		}
    	</script>
</html>
