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
	<title>编辑客户</title>
	
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
		function check(){
			if(document.getElementById("name").value==""){
				alert("客户名称不能为空");
				return;
			}
			if(document.getElementById("unitName").value==""){
				alert("单位全称不能为空");
				return;
			}
			if(document.getElementById("linkMan").value==""){
				alert("联系人不能为空");
				return;
			}
			if(document.getElementById("phone").value==""){
				alert("联系电话不能为空");
				return;
			}
			var id = document.getElementById("id").value;
			selectType1();
			document.form1.action = "<%=path%>/customer.do?method=editClientById";
			document.form1.submit();
		}
		function return1(){
			this.close();
		}
		function seltype(){
			var obj = document.getElementById("clientType");
			if(obj.value=="单位"){
				document.getElementById("tr11").style.display="none";
				document.getElementById("tr12").style.display="block";
			}
			if(obj.value=="个人"){
				document.getElementById("tr11").style.display="block";
				document.getElementById("tr12").style.display="none";
			}
		}
		function selectType1(){
			var obj = document.getElementById("clientType");
			if(obj.value=="单位"){
				document.getElementById("country").value="";
				document.getElementById("nation").value="";
				document.getElementById("residence").value="";
				document.getElementById("identityCard").value="";
			}
			if(obj.value=="个人"){
				document.getElementById("companyType").value="";
				document.getElementById("fax").value="";
				document.getElementById("trade").value="";
				document.getElementById("fundType").value="";
				document.getElementById("operation").value="";
				document.getElementById("corporation").value="";
				document.getElementById("loginFund").value="";
				document.getElementById("loginDate").value="";
				document.getElementById("isHighTech").value="";
				document.getElementById("taxNo").value="";
			}
		}
	</script>
</head>
<body>
<form name = "form1" method="post">
	<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0">

				<tr>
    				<td height="5">
	    				<input type="hidden" id="id" name="id" value="${client.id }">
	    				<input type="hidden" id="clientTypeIn" name="clientTypeIn" value="${client.clientType }">
	    				<input type="hidden" id="companyTypeIn" name="companyTypeIn" value="${client.companyType }">
	    				<input type="hidden" id="tradeIn" name="tradeIn" value="${client.trade }">
	    				<input type="hidden" id="fundTypeIn" name="fundTypeIn" value="${client.fundType }">
    				</td>
  				</tr>
		  		<tr>
		    		<td>
		    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      				<tr>
		        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">客户信息编辑页面</td>
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
												<table align="center" border="0" cellpadding="3" cellspacing="0" class="form_tab">
													<tr>
								     					<th align="left" colspan="4" class="head_one">客户基本信息</th>
								     				</tr>
													<tr>
														<td class="head_rols" align="right" width="20%" nowrap="nowrap">客户简称：</td>
														<td class="fist_rows" width="30%"><input type="text" name="name" id="name" value="${client.name }"><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font></td>
														<td class="fist_rows" align="right" nowrap="nowrap" width="20%">单位全称：</td>
														<td class="fist_rows"><input type="text" id="unitName" name="unitName" value="${client.unitName }"><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font></td>
													</tr>
													<tr>
														<td class="head_left" align="right" width="20%">客户代码：</td>
														<td class="head_form1" width="30%"><input type="text" name="code" id="code" value="${client.code }"><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font></td>
														<td class="head_form1" align="right" width="20%">&nbsp;</td>
														<td class="head_form1">&nbsp;</td>
													</tr>
								     				<tr>
														<td class="head_left" align="right" width="20%" nowrap="nowrap">联系人：</td>
														<td class="head_form1"><input type="text" name="linkMan" id="linkMan" value="${client.linkMan }"><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font></td>
														<td class="head_form1" align="right" width="20%" nowrap="nowrap">联系电话：</td>
														<td class="head_form1" width="30%"><input type="text" name="phone" id="phone" value="${client.phone }"><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font></td>
													</tr>
													<tr>
														<td class="head_left" align="right" nowrap="nowrap" width="20%">住户类别：</td>
														<td class="head_form1" width="30%"><select onchange="seltype()" name="clientType"><option value="单位" selected="selected">单位</option><option value="个人">个人</option></select></td>
														<td class="head_form1" align="right" width="20%">&nbsp;</td>
														<td class="head_form1">&nbsp;</td>
													</tr>
													<tr id="tr11" style="display:none ">
														<td colspan="4" class="head_left">
															<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
																<tr>
											     					<th align="left" colspan="4" class="head_one" nowrap="nowrap">个人信息</th>
											     				</tr>
											     				<tr>
																	<td class="head_left" align="right" width="20%" nowrap="nowrap">国籍：</td>
																	<td class="head_form1" width="30%"><input type="text" name="country" id="country" value="${client.country }"></td>
																	<td class="head_form1" align="right" width="20%">&nbsp;</td>
																	<td class="head_form1">&nbsp;</td>
																</tr>
											     				<tr>
																	<td class="head_left" align="right" width="20%" nowrap="nowrap">民族：</td>
																	<td class="head_form1" width="30%"><input type="text" name="nation" id="nation" value="${client.nation }"></td>
																	<td class="head_form1" align="right" width="20%">&nbsp;</td>
																	<td class="head_form1">&nbsp;</td>
																</tr>
											     				<tr>
																	<td class="head_left" align="right" width="20%" nowrap="nowrap">籍贯：</td>
																	<td class="head_form1" width="30%"><input type="text" name="residence" id="residence" value="${client.residence }"></td>
																	<td class="head_form1" align="right" width="20%">&nbsp;</td>
																	<td class="head_form1">&nbsp;</td>
																</tr>
											     				<tr>
																	<td class="head_left" align="right" width="20%" nowrap="nowrap">身份证号：</td>
																	<td class="head_form1" width="30%"><input type="text" name="identityCard" id="identityCard" value="${client.identityCard }"></td>
																	<td class="head_form1" align="right" width="20%">&nbsp;</td>
																	<td class="head_form1">&nbsp;</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr id="tr12">
														<td colspan="4" class="head_left">
															<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
															<tr>
										     					<th align="left" colspan="4" class="head_one" nowrap="nowrap">公司信息</th>
										     				</tr>
										     				<tr>
																<td class="head_left" align="right" width="20%" nowrap="nowrap">公司类别：</td>
																<td class="head_form1" width="30%">
																<select name="companyType" id="companyType" style="width: 130">
																	<c:choose>
																	<c:when test="${empty map.enterpriseType}">
																	</c:when>
																	<c:otherwise>
																	<c:forEach items="${map.enterpriseType}" var="v">
																		<option value="${v.codeName }">${v.codeName }</option>
																	</c:forEach>
																</c:otherwise>
																</c:choose>
																</select>
																</td>
																<td class="head_form1" align="right" width="20%" nowrap="nowrap">传真：</td>
																<td class="head_form1" width="30%"><input type="text" name="fax" id="fax" value="${client.fax }"></td>
															</tr>
										     				<tr>
																<td class="head_left" align="right" width="20%" nowrap="nowrap">所属行业：</td>
																<td class="head_form1" width="30%">
																<select name="trade" id="trade" style="width: 130">
																	<c:choose>
																	<c:when test="${empty map.tradeType}">
																	</c:when>
																	<c:otherwise>
																	<c:forEach items="${map.tradeType}" var="v">
																		<option value="${v.codeName }">${v.codeName }</option>
																	</c:forEach>
																</c:otherwise>
																</c:choose>
																</select>
																</td>
																<td class="head_form1" align="right" width="20%" nowrap="nowrap">资金类别：</td>
																<td class="head_form1">
																	<select name="fundType" id="fundType" style="width: 130">
																	<c:choose>
																	<c:when test="${empty map.fundType}">
																	</c:when>
																	<c:otherwise>
																	<c:forEach items="${map.fundType}" var="v">
																		<option value="${v.codeName }">${v.codeName }</option>
																	</c:forEach>
																</c:otherwise>
																</c:choose>
																</select>
																</td>
															</tr>
										     				<tr>
																<td class="head_left" align="right" width="20%" nowrap="nowrap">主营业务：</td>
																<td class="head_form1" width="30%"><input type="text" name="operation" id="operation" value="${client.operation }"></td>
																<td class="head_form1" align="right" width="20%" nowrap="nowrap">法人代表：</td>
																<td class="head_form1"><input type="text" name="corporation" id="corporation" value="${client.corporation }"></td>
															</tr>
															<tr>
																<td class="head_left" align="right" width="20%" nowrap="nowrap">注册资金：</td>
																<td class="head_form1" width="30%"><input type="text" name="loginFund" id="loginFund" value="${client.loginFund }"></td>
																<td class="head_form1" align="right" width="20%" nowrap="nowrap">成立时间：</td>
																<td class="head_form1"><input type="text" name="loginDate" id="loginDate" readonly onclick="WdatePicker();" value="${client.loginDate }" class="Wdate"></td>
															</tr>
															<tr>
																<td class="head_left" align="right" width="10%">国税号：</td>
																<td class="head_form1" width="30%"><input type="text" name="taxNo" id="taxNo" value="${client.taxNo }"></td>
																<td class="head_form1" align="right" width="10%">地税号：</td>
																<td class="head_form1"><input type="text" name="rentNo" id="rentNo" value="${client.rentNo }"></td>
															</tr>
										     				<tr>
																<td class="head_left" align="right" width="20%" nowrap="nowrap" nowrap="nowrap">是否高新技术企业：<input type="hidden" id="isHighTechIn" value="${client.isHighTech }"></td>
																<td class="head_form1" width="30%" colspan="3"><select name="isHighTech" id="isHighTech"><option value="是" selected="selected">是</option><option value="否">否</option></select></td>
															</tr>
															</table>
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
										<c:if test="${flag}">
											<script type="text/javascript">
												returnValue = "flag";
												this.close();
											</script>
										</c:if>
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
	<script type="text/javascript">
		document.getElementById("clientType").value = document.getElementById("clientTypeIn").value;
		document.getElementById("companyType").value = document.getElementById("companyTypeIn").value;
		document.getElementById("trade").value = document.getElementById("tradeIn").value;
		document.getElementById("fundType").value = document.getElementById("fundTypeIn").value;
		document.getElementById("isHighTechIn").value = document.getElementById("isHighTech").value;
		seltype();
	</script>
</html>
