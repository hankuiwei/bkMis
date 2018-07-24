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
	<title>编辑实际能源耗用</title>
	<base href="<%=basePath%>" target="_parent">
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
     <script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript">
		function check(){
			var x = Validator.Validate(document.getElementById('form1'),2);
			if(!x){
				return;
			}
			document.form1.action="financialActual.do?method=edit";
			document.form1.submit();
		}
		function return1(){
			this.close();
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
								<td width="165" nowrap="nowrap" class="form_line">编辑实际能源耗用</td>
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
															年度：
														</td>
														<td class="fist_rows">
														<input type="text" name="year"  value="${bean.year }" style=" width: 138px"  onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" readonly onclick="WdatePicker();" class="Wdate">
														</td>
													</tr>
													 <tr>
														<td class="head_rols" align="right">
															月份：
														</td>
														<td class="fist_rows">
														<input type="text" name="month"  value="${bean.month}" style=" width: 138px"  onclick="WdatePicker({skin:'whyGreen',dateFmt:'MM'})" readonly onclick="WdatePicker();" class="Wdate">
														</td>
													</tr>
													
													<tr>
														<td class="head_rols" align="right">
															水：
														</td>
														<td class="fist_rows">
															<input type="text" name="water" value="${bean.water}"  dataType="Double2" msg="必须是数字！" id="rate"> 
														</td>
													</tr>
														<tr>
														<td class="head_rols" align="right">
															电：
														</td>
														<td class="fist_rows">
															<input type="text" name="electricity" value="${bean.electricity}" dataType="Double2" msg="必须是数字！" id="rate"> 
														</td>
													</tr>
													<tr>
														<td class="head_rols" align="right">
															气：
														</td>
														<td class="fist_rows">
															<input type="text" name="gas" value="${bean.gas}" dataType="Double2" msg="必须是数字！" id="rate"> 
														</td>
													</tr>
													<tr>
														<td class="head_rols" align="right">
															录入时间：
														</td>
														<td class="fist_rows">
															<input type="text" name="createDate"  style=" width: 138px" value="${bean.createDate }"  onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly onclick="WdatePicker();" class="Wdate">
															<input type="hidden" value="${bean.createUser }" name="createUser"/>
															<input type="hidden" value="${bean.id }" name="id"/>
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
