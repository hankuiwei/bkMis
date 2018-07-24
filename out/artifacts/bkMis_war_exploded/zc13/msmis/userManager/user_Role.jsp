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
		<base href="<%=basePath%>">
		<title>设置权限</title>
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet"
			type="text/css">
		<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
	    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
	    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"
			src="<%=path%>/resources/js/util/util.js"></script>

		<script type="text/javascript"
			src="<%=path%>/resources/js/util/jquery.js"></script>

		<title>My JSP 'user_Manager.jsp' starting page</title>
		<script language="JavaScript" type="text/JavaScript">
		var checkedAll = false;
		
		/* 是否全选标记 */
		/* 全选/取消全选
		* formName 所在form的name值
		* checkboxName checkbox的name值
		* 注意：所有checkbox的name值都必须一样，这样才能达到全选的效果
		*/
		function selectAll(form1,role){
			var form = document.all.item(form1);
			var elements = form.elements[role];
	        for (var i=0;i<elements.length;i++){ 
				var e = elements[i];
					if(checkedAll){
						e.checked = false;
						form.alls.checked = false;
					} else {
						e.checked = true;
							form.alls.checked = true;
						}
						}
					if(checkedAll){
					checkedAll = false;
					} else {
					checkedAll = true;
					}
					} 
		/* 检查是否有checkbox被选中
		* formName 所在form的name值
		* checkboxName checkbox的name值
		* 注意：所有checkbox的name值都必须一样，这样才能达到全选的效果
		*/
		function checkAll(form1,role){
			var hasCheck = false;
			var form = document.all.item(form1);
			var elements = form.elements[role];
				for (var i=0;i<elements.length;i++){
				var e = elements[i];
					if(e.checked){
						hasCheck = true;
					}
					}
						return hasCheck;
						}
	/* 执行操作 */
		function do_action(){
			if (!checkAll("form1","roleid")){
				alert("没有checkbox被选中，提示用户至少选择一个！");
			} else {
				alert("已有checkbox被选中，可以继续后续操作！");
					}
					} 
		function save(){
	  		var isChecked = document.getElementsByName("roleid");
	  		for(var k = 0;k<isChecked.length;k++){
	  			if(isChecked[k].checked){
	  				document.getElementById("roleid").value = isChecked[k].value;
	  			}
	  		}
	  		form1.action = "userManager.do?method=saveRole";
	  		form1.submit();
	  	}
	  	function return1(){
	  		window.location.href="userManager.do?method=findUser";
	  	}
		
	</script>
	</head>

	<body>
		<form action="" method="post" name="form1">
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td height="10"></td>
				</tr>

				<tr>
    				<td height="5"></td>
  				</tr>
		  		<tr>
		    		<td>
		    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      				<tr>
		        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">操作员权限设置</td>
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
											<td height="10" colspan="9"><input type="hidden" id="isChecked" name="isChecked"></td>
										</tr>
										<tr>
											<td height="10" colspan="9"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table border="0" width="60%" cellspacing="0" cellpadding="0"
										align="center">
										<tr>
											<td align="left" colspan="10" align="center">
												  <input type="hidden" id="userid" name="userid" value="${userid}">
												<c:if test="${role.rolename == null }">
													<font size="3"><b>当前用户没有角色！</b></font>
												</c:if>
												<c:if test="${role.rolename != null}">
													<font size="3"><b>当前用户的角色为：${role.rolename }</b></font>
												</c:if>
												<c:set value="${role.rolename }" var="temp"></c:set>
											</td>
										</tr>
									</table>
								</td>
							</tr>

							<tr>
								<td>
									<table width="60%" align="center" border="0" cellspacing="0"
										cellpadding="0">
										<tr>
											<td colspan="10">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0" class="form_tab">
													<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
														<td width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center"><input type="hidden" id="isChecked" name="isChecked"></td>
														<td width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">角色编号</td>
														<td width="20%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">角色名称</td>
														<td width="20%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">角色描述</td>
													</tr>
													<c:forEach var="role" items="${list }">
														<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
															<c:choose>
																<c:when test="${role.rolename == temp}">
																	<td nowrap="nowrap" class="RptTableBodyCellLock" align="center"><input type="radio" name="roleid" id="roleid" value="${role.roleid}" checked="checked"></td>
																</c:when>
																<c:otherwise>
																	<td nowrap="nowrap" class="RptTableBodyCellLock" align="center"><input type="radio" name="roleid" id="roleid" value="${role.roleid}"></td>
																</c:otherwise>
															</c:choose>
															<td nowrap="nowrap" class="RptTableBodyCell" align="center">${role.roleid}</td>
															<td nowrap="nowrap" class="RptTableBodyCell" align="center">${role.rolename}</td>
															<td nowrap="nowrap" class="RptTableBodyCell" align="center">${role.roledesc}</td>
														</tr>
													</c:forEach>
												</table>
											</td>
										</tr>
										<tr><td colspan="10" height="10"></td></tr>
										<tr>
											<td align="center">
												<table>
													<tr>
														<td nowrap="nowrap" align=""><input type="button" value="确定" onclick="save()" class="button"></td>
														<td nowrap="nowrap"><input type="button" value="返回" onclick="return1()" class="button"></td>
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
