<%@ page language="java"   pageEncoding="UTF-8"%>
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
	<title>编辑客户投诉</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	<base href="<%=basePath%>" target="_parent">
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		function edit(){
			if(document.getElementById("name").value==""){
				alert("业主不能为空");
				return;
			}
			if(document.getElementById("phone").value==""){
				alert("电话号码不能为空");
				return;
			}
			if(document.getElementById("complaintContent").value==""){
				alert("投诉内容不能为空");
				return;
			}
			//if((document.getElementById("phone").value).match(/^[1]\d{10}$/)==null){
			//	alert("电话号码格式不正确");
			//	return;
			//}
			document.form1.action ="<%=path%>/complaint.do?method=editComplaint";
			document.form1.submit();
		}
		function return1(){
			this.close();
		}
	</script>
</head>
<c:if test="${!empty message}">
	<script type="text/javascript">
		alert("${message}");
		window.returnValue = "flag";
		this.close();
	</script>
</c:if>
<body>
<form name ="form1" method="post">
	<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
    				<td height="5">
					<input type="hidden" value="${bean.type }" name="typeIn" id="typeIn" />
					<input type="hidden" value="${bean.id }" name="id" id="id" />
    				</td>
  				</tr>
		  		<tr>
		    		<td>
		    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      				<tr>
		        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">编辑客户投诉信息</td>
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
									<table align="center" border="0" cellpadding="3"
													cellspacing="0" class="form_tab">
										<tr>
											<td class="head_rols" align="right">
												业户名称：
											</td>
											<td class="fist_rows">
												<input type="text" name="name" id="name" value="${bean.name }"><font color="red">*</font>
											</td>
											<td class="fist_rows" align="right">
												&nbsp;
											</td>
											<td class="fist_rows">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td class="head_left" align="right">
												联系电话：
											</td>
											<td class="head_form1">
												<input type="text" name="phone" id="phone" value="${bean.phone }"><font color="red">*</font>
											</td>
											<td class="head_form1" align="right">
												投诉类别：
											</td>
											<td class="head_form1">
												<select id="type" name="type"><option value="电话">电话</option><option value="上门">上门</option></select>
											</td>
										</tr>
										<tr>
											<td class="head_left" align="right">
												投诉内容：
											</td>
											<td class="head_form1" colspan="3">
												<textarea rows="3" style="width: 80%" name="complaintContent" id="complaintContent">${bean.complaintContent }</textarea><font color="red">*</font>
											</td>
										</tr>
										<tr>
											<td class="head_left" align="right">
												投诉时间：
											</td>
											<td class="head_form1">
												<input type="text" name="complaintDate" id="complaintDate" value="${bean.complaintDate }" onclick="WdatePicker();" class="Wdate">
											</td>
											<td class="head_form1" align="right">
												接听(接待)人：
											</td>
											<td class="head_form1">
												<input type="text" name="clerk" id="clerk" value="${bean.clerk }">
											</td>
										</tr>
										<tr>
											<td align="center" colspan="4" class="head_left">
												<input type="button" onclick="edit()" class="button" value="确定">
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
		</form>
	</body>
	
	<script type="text/javascript">
		document.getElementById("type").value = document.getElementById("typeIn").value;
	</script>
</html>
