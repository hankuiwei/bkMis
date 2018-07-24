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
	    <base target="_self" />
		<title>查看日志详情</title>
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
	    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
	    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
		<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
		<script type="text/javascript">
     
    </script>
	</head>
	<body>
		<form method="post" name="form1">
			<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
				
		  		<tr>
		    		<td>
		    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      				<tr>
		        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">查看日志详细信息</td>
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
										<tr><td height="10" colspan="9"><input type="hidden" name="userid" id ="userId" value="${user.userid }" ></td></tr>
										<tr>
											<td>
												<table align="center" width="58%"  border="0" cellpadding="3" cellspacing="0" class="form_tab">
													<tr>
														<td class="head_rols" align="right">操&nbsp;&nbsp;作&nbsp;&nbsp;人：</td>
														<td class="fist_rows" colspan="3">${sysLog.operateUserName }</td>		
													</tr>
													<tr>
														<td class="head_left" align="right">操&nbsp;&nbsp;作&nbsp;&nbsp;类&nbsp;&nbsp;型：</td>
														<td class="head_form1" colspan="3">${sysLog.operateType}&nbsp;</td>		
													</tr>
													<tr>
														<td class="head_left" align="right">操&nbsp;&nbsp;作&nbsp;&nbsp;时&nbsp;&nbsp;间：</td>
														<td class="head_form1" colspan="3" >${sysLog.operateTime}&nbsp;</td>
													</tr>
													<tr>
														<td class="head_left" align="right">操&nbsp;&nbsp;作&nbsp;&nbsp;模&nbsp;&nbsp;块：</td>
														<td class="head_form1" colspan="3" >${sysLog.operateModule}&nbsp;</td>
													</tr>
													<tr>
														<td class="head_left" align="right">操&nbsp;&nbsp;作&nbsp;&nbsp;内&nbsp;&nbsp;容：</td>
														<td class="head_form1" colspan="3">
														<c:out value="${sysLog.operateContent}" />
														&nbsp;</td>
													</tr>
													<tr>
														<td align="center" colspan="4" class="head_left" >
															<input type="button" onclick="window.close()" class="button" value="确定" id = "focuson">
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
