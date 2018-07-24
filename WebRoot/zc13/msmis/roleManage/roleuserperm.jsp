<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <base href="<%=basePath%>">
    
    <title>角色功能列表</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"></meta>
	<script src="<%=path %>/zc13/msmis/functionList/global/js/main.js" type="text/javascript"></script>
	<link href="<%=path %>/zc13/msmis/functionList/global/css/main.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/zc13/msmis/functionList/global/SpryAssets/sample.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
       function goRoleList(){
          document.rupform.action = "<%=path%>/role.do?method=getRoleList";
          document.rupform.target = "_self";
          document.rupform.submit();
       }
    </script>
  </head>
	<body>
		<form name="rupform" method="post">
			<div id="rightDiv" style="_padding-top: 352px;">

				<div class="file_manager_serch_x" id="final_serch_bg">
				</div>
				<div class="final_menu">
					<div class="final_lift">
						<a onclick="goRoleList();"><div class="buttonBg">
								<div style="margin-top: 3px;">返回角色列表</div>
							</div>
						</a>
					</div>
					<div class="final_right" style="margin-top: 2px;">
					</div>
				</div>
				<div class="final_bottom_height QP" id="centerDiv"
					style="background: #FFF; top: 43px;">
					<table class="final_table_EW" width="100%" border="0"
						cellpadding="0" cellspacing="0">
						<tr>
							<th>角色名称</th>
							<th>成员名称</th>
							<th>使用状态</th>
							<th>功能名称</th>
						</tr>
						<c:choose>
							<c:when test="${empty role_user_permList}">
								<tr>
									<td colspan="5" align="center">没有符合条件的记录</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${role_user_permList}" var="rp">
									<tr id="trigger" onmouseover="this.className = 'hover';"
										onmouseout="this.className = '';">
										<td class="smg_right_td">&nbsp;${rp.rolename}</td>
										<td class="smg_right_td">&nbsp;${rp.username}</td>
										<td class="smg_right_td">
											<c:if test="${rp.enabled == 0}">不可用</c:if>
											<c:if test="${rp.enabled == 1}">可用</c:if>
										</td>
										<td title="${rp.functionname}">&nbsp;${rp.functionname}</td>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
			</div>
		</form>
	</body>
</html>
