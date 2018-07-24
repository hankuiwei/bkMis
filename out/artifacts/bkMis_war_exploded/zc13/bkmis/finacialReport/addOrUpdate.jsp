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
	<title>添加或是编辑经营成本</title>
	<base href="<%=basePath%>" target="_parent">
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
	<script type="text/javascript">
		function check(){
			if("" == document.getElementById("operateCost.year").value){
				alert("年度不能为空!");
				return;
			}
			/*if("" == document.getElementById("operateCost.month").value){
				alert("月份不能为空!");
				return;
			}*/
			document.forms[0].action="<%=basePath %>financialReport.do?method=saveOrUpdate";
			document.forms[0].submit();
		}
		function return1(){
			this.close();
		}
	</script>
	
</head>
  
  <body onunload="window.returnValue = 'flag';window.close();">
  	<form action="" method="post">
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
								<td width="165" nowrap="nowrap" class="form_line">经营成本记录</td>
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
															类型：
														</td>
														<td class="fist_rows">
															<select id="" name="operateCost.moneyType">
																<option value="实际" <c:if test="${form.operateCost.moneyType == '实际' }">selected</c:if>>实际</option>
																<option value="预算" <c:if test="${form.operateCost.moneyType == '预算' }">selected</c:if>>预算</option>
															</select>&nbsp;&nbsp;&nbsp;
														</td>
														<td class="head_rols" align="right">
															年度：
														</td>
														<td class="fist_rows">
															<input type="text" id="operateCost.year" name="operateCost.year" value="${form.operateCost.year }"  style=" width: 138px"  onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" readonly onclick="WdatePicker();" class="Wdate"><font color="red">*</font>&nbsp;&nbsp;&nbsp;
															<input type="hidden" name="operateCost.id" value="${form.operateCost.id }">
 														</td>
													</tr>
													<tr>
														
													</tr>
													<tr>
														<td class="head_left" align="right">
															月份：
														</td>
														<td class="head_form1">
															<input type="text" id="operateCost.month" name="operateCost.month" value="${form.operateCost.month }" style=" width: 138px"  onclick="WdatePicker({skin:'whyGreen',dateFmt:'MM'})" readonly onclick="WdatePicker();" class="Wdate"><%--<font color="red">*</font> --%>
														</td>
														<td class="head_left" align="right">
															保洁费：
														</td>
														<td class="head_form1">
															<input type="text" name="operateCost.cleanFee"  style=" width: 138px" value="${form.operateCost.cleanFee }">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															保安费：
														</td>
														<td class="head_form1">
															<input type="text" name="operateCost.securityFee"  style=" width: 138px" value="${form.operateCost.securityFee }">
														</td>
														<td class="head_left" align="right">
															通讯费：
														</td>
														<td class="head_form1">
															<input type="text" name="operateCost.communicationFee"  style=" width: 138px" value="${form.operateCost.communicationFee }">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															水费：
														</td>
														<td class="head_form1">
															<input type="text" name="operateCost.waterFee"  style=" width: 138px" value="${form.operateCost.waterFee }">
														</td>
														<td class="head_left" align="right">
															电费：
														</td>
														<td class="head_form1">
															<input type="text" name="operateCost.electricityFee"  style=" width: 138px" value="${form.operateCost.electricityFee }">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															燃气费：
														</td>
														<td class="head_form1">
															<input type="text" name="operateCost.gasFee"  style=" width: 138px" value="${form.operateCost.gasFee }">
														</td>
														<td class="head_left" align="right">
															物料：
														</td>
														<td class="head_form1">
															<input type="text" name="operateCost.materials"  style=" width: 138px" value="${form.operateCost.materials }">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															设备维修、保养、检测费：
														</td>
														<td class="head_form1">
															<input type="text" name="operateCost.eqmCheckFee"  style=" width: 138px" value="${form.operateCost.eqmCheckFee }">
														</td>
														<td class="head_left" align="right">
															其他(保险及绿化)：
														</td>
														<td class="head_form1" colspan="3">
															<input type="text" name="operateCost.otherFee"  style=" width: 138px" value="${form.operateCost.otherFee }">
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
