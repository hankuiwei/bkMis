<%@ page language="java" pageEncoding="UTF-8"%>
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
		<title>修改角色</title>
		<base target="_self" />
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/main.js" charset="UTF-8"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
		<script type="text/javascript">
   function checkForm(){
      var rolename = document.getElementById("rolename").value; 
      if(rolename == ""){
         alert("请输入角色名称");
         return;
      }else{
          if(confirm("确定要修改吗?")){
            document.modifyForm.action = "<%=path%>/role.do?method=updateRole";
            document.modifyForm.submit();
            //window.close();
          }
      } 
   }
 //屏蔽回车事件
  document.onkeydown=function(evt){
       var evt=window.event?window.event:evt;
       if(evt.keyCode == 13){
           evt.keyCode = 0;
           evt.returnValue = false;
       }
   }
</script>
</head>
	<body>
		<form id="modifyForm" name="modifyForm" method="post" >
		    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="5" colspan="9">
						<c:if test="${flag}">
							<script type="text/javascript">
								returnValue = "flag";
								this.close();
							</script>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">修改角色</td>
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
								<td align="center">
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
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left">
										<tr>
											<td align="right" class="head_form1" width="12%">角色名称：</td>
											<td align="left" class="head_form1" width="33%">&nbsp;&nbsp;
												<input type="text" id="rolename" name="rolename" value="${mrole.rolename}"><font color="red">*</font>
											    <input type="hidden" id="roleid" name="roleid" value="${mrole.roleid}" />
												<input type="hidden" id="oldnames" name="oldname" value="${mrole.rolename}" />
											</td>
											<td align="right" class="head_form1" width="23%">使用状态：</td>
											<td align="left" class="head_form1">&nbsp;
												<select name="enabled" style="width: 130px;">
													<option value="0" <c:if test="${mrole.enabled == 0}">selected</c:if>>不可用</option>
													<option value="1" <c:if test="${mrole.enabled == 1}">selected</c:if>>可用</option>
												</select>
												<font color="red">*</font>
											</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">角色等级：</td>
											<td align="left" class="head_form1" colspan="3">&nbsp;
												<select name="range" style="width: 130px;">
													<option value="2" <c:if test="${mrole.range == 2}">selected</c:if>>管理员</option>
													<option value="3" <c:if test="${mrole.range == 3}">selected</c:if>>普通用户</option>
												</select>
												<font color="red">*</font>
											</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">角色描述：</td>
											<td align="left" class="head_form1" colspan="3">&nbsp;	
												<textarea style="width: 98%; height: 50px; background-color: #F0F8FA" name="roledesc" title="最多1000个字符">${mrole.roledesc}</textarea>
										   </td>
										</tr>
										<tr height="40"><td></td></tr>
										<tr>
											<td align="center" colspan="9">
												<table>
													<tr>
														<td nowrap="nowrap" align="right">
															<input class="button" onclick="checkForm();" type="button" value="确定">
														</td>
														<td nowrap="nowrap">
															<input type="button" class="button" value="取消" onclick="javascript:window.close();">
														</td>
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
