<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>添加房间</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		function add(){
		}
		function return1(){
			this.close();
		}
	</script>
	
</head>
<body>
<form name = "form1">
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
								<td width="165" nowrap="nowrap" class="form_line">投诉建议</td>
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
															室号全称：
														</td>
														<td class="fist_rows">
															<input type="text" name="text">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															建筑面积：
														</td>
														<td class="head_form1">
															<input type="text" name="text">
														</td>
													</tr>
													
													<tr>
														<td class="head_left" align="right">
															联系电话：
														</td>
														<td class="head_form1" colspan="3">
															<input type="text" name="text">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															联系人：
														</td>
														<td class="head_form1" colspan="3">
															<input type="text" name="text">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															单位名称：
														</td>
														<td class="head_form1" colspan="3">
															<input type="text" name="text">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															录入时间：
														</td>
														<td class="head_form1" colspan="3">
															<input type="text" name="begin" value="" readonly onclick="WdatePicker();" class="Wdate">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															接待人：
														</td>
														<td class="head_form1" colspan="3">
															<input type="text" name="text">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															客户类别：
														</td>
														<td class="head_form1">
															<select><option>单位</option><option>个人</option></select>
														</td>
													</tr>
													
													<tr>
														<td align="center" colspan="4" class="head_left">
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
