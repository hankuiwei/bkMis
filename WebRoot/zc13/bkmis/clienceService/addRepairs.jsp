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
	<title>添加报修项目</title>
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
	<script type="text/javascript">
		
		function return1(){
			this.close();
		}
		
		//检查项目编码是否存在
		function change1(obj){
			var code = obj.value;
			$.post("repair.do",{method:"checkcode", code:code},function(data){
				if(data=='true') {
					alert("该项目编码已存在");
					//obj.focus();
					obj.style.color = "red";
					obj.style.borderColor='#CC99FF';
					obj.focus();
					return;
				}else{
					obj.style.color = "#000000";
					obj.style.borderColor='#FFFFFF';
				}
			});			
		}
		function change2(obj){
			var name = obj.value;
			$.post("repair.do",{method:"checkname", name:name},function(data){
				if(data=='true') {
					alert("该项目名称已存在");
					//obj.focus();
					obj.style.color = "red";
					obj.style.borderColor='#CC99FF';
					obj.focus();
					return;
				}else{
					obj.style.color = "#000000";
					obj.style.borderColor='#FFFFFF';
				}
			});			
		}
		function check(){
			var x = Validator.Validate(document.getElementById('form1'),2);
			if(!x){
				return;
			}
			document.form1.action="repair.do?method=addRepair";
			document.form1.submit();
		}
	</script>
	
</head>
<body onunload="window.returnValue = 'flag';window.close();">
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
								<td width="165" nowrap="nowrap" class="form_line">添加客户报修</td>
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
															项目编码：
														</td>
														<td class="fist_rows">
															<input type="text" name="code" id="code" dataType="Require" msg="项目编码不得为空！" onblur="change1(this)">&nbsp;&nbsp;&nbsp;<font color="red">*</font>
														</td>
													</tr>
													<tr>
														<td class="head_rols" align="right">
															项目名称：
														</td>
														<td class="fist_rows">
															<input type="text" name="name" id="name" dataType="Require" msg="项目名称不得为空！" onblur="change2(this)">&nbsp;&nbsp;&nbsp;<font color="red">*</font>
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															类别：
														</td>
														<td class="head_form1">
															<select id="type" name="type"><option value="<%=Contants.PROJECT_TYPE_PAID %>" selected="selected"><%=Contants.PROJECT_TYPE_PAID %></option><option value="<%=Contants.PROJECT_TYPE_FREE %>"><%=Contants.PROJECT_TYPE_FREE %></option></select>
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															计费类型：
														</td>
														<td class="head_form1">
															<select id="chargeType" name="chargeType"><option value="<%=Contants.CHARGE_TYPE_HOUR %>" selected="selected"><%=Contants.CHARGE_TYPE_HOUR %></option><option value="<%=Contants.CHARGE_TYPE_TIMES %>"><%=Contants.CHARGE_TYPE_TIMES %></option></select>
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															服务价格(元)：
														</td>
														<td class="head_form1">
															<input type="text" name="rate"  dataType="Double2" msg="服务价格必须是数字！" id="rate"> 
														</td>
													</tr>
													<tr>
														<td class="head_left"  dataType="Double2" msg="响应时间必须是数字！" align="right">
															要求响应时间(小时)：
														</td>
														<td class="head_form1">
															<input type="text" name="responseTime">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															说明：
														</td>
														<td class="head_form1">
															<textarea rows="3" style="width: 150" name="bz"></textarea>
														</td>
													</tr>
													
													<tr>
														<td align="center" colspan="2" class="head_left">
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
