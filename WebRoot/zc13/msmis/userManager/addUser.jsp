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
		<title>新增用户</title>
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
	    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
	    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
		<script type="text/javascript">
          
       function init(){
          document.getElementsByName("username").focus();
       }
       //验证表单，添加操作员
       function check(){
          var form = document.getElementById("form1");
          if(form.username.value==""){
            alert("名字不能为空");
            form.username.focus();
            return;
          }
          if(form.username.value.indexOf(" ")!=-1){
            alert("名字内不能有空格");
            form.username.focus();
            return;
          }
         
		//用户名验证
		var username = trim(form.username.value);
		var url = "<%=path%>/userManager.do";
			$.post(url,{method:"checkUserName", username:username},function(data){
				if(data=='false') {
					if(confirm('你确定要增加吗？')) {
						document.form1.action="userManager.do?method=addUser";
						document.form1.submit();
						//window.close();
					}
				} else {
					alert("该用户名已存在");
					return false;
				}
			});			
        
       }
       function return1(){
       		this.close();
       }
       //用来实现某部门下员工的级联下拉框
		  function selectbelong(department){
		       var employeeName = document.getElementById("employeeName");
		       //用来设置在结束一次联动之后，下次开始时置空
		       employeeName.options.length=0;
		       var url = "<%=path%>/userManager.do";
		       $.post(url,{method:"getUserNameByDepartment",department:department},function(data){
			       if(data != null){
		                var str = data.split(";");
		                for(var i=0;i<str.length-1;i++){
		                   var option = new Option(str[i],str[i]);
		                    employeeName.add(option);
		               }
			       }
		       });
		  }
    </script>
	</head>
	<body>
		<form method="post" name="form1">
		<input type="hidden" name="password" value="<%=Contants.DEFAULTPASSWORD %>">
			<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
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
								<td width="165" nowrap="nowrap" class="form_line">新增用户</td>
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
										<tr><td height="10" colspan="9"></td></tr>
										<tr>
											<td>
												<table align="center" border="0" cellpadding="3" cellspacing="0" class="form_tab">
													<tr>
														<td class="head_rols" align="right">用&nbsp;&nbsp;户&nbsp;&nbsp;名：</td>
														<td class="fist_rows" colspan="3"><input type="text" name="username"><font color="red">*</font></td>
													</tr>
													<tr>
														<td class="head_left">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
														<td class="head_form1" colspan="3"><font color="red">初始密码默认为：<%=Contants.DEFAULTPASSWORD %></font></td>
														<!-- <td class="head_form1" align="right">确认密码：</td>
														<td class="head_form1"><input type="password" name="password1"><font color="red">*</font></td>
														 -->
													</tr>
													<tr>
														<td class="head_left">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门：</td>
														<td class="head_form1">
															<select name="department" id="department" style="width: 130px;" onchange="selectbelong(value);" >
															    <option value="" selected="selected">- - -请选择- - -</option>
																<c:forEach items="${departmentlist }" var="d">
																	<option value="${d.codeName }">${d.codeName }</option>
																</c:forEach>
															</select>
														</td>
														<td class="head_form1">真实姓名：</td>
														<td class="head_form1">
															<select name="realName" id="employeeName" style="width: 130px;">
															</select>
														
													</tr>
													<tr>
														<td class="head_left" align="right">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话：</td>
														<td class="head_form1" ><input type="text" name="phone"></td>
														<td class="head_form1" align="right">是否可用：</td>
														<td class="head_form1">
															<select name="enabled" id="enabled">
																<option value="1" selected="selected">可用</option>
																<option value="0">不可用</option>
															</select><font color="red">*</font>
													</tr>
													<tr>
														<td class="head_left" align="right">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</td>
														<td class="head_form1" colspan="3"><textarea style="width: 98%; height: 50px; background-color: #F0F8FA" name="description"></textarea></td>
													</tr>
													<tr>
														<td align="center" colspan="4" class="head_left" >
															<input type="button" onclick="check()" class="button" value="确定">
															<input type="button" onclick="return1()" class="button" value="返回">
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
