<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.Contants"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>车辆限行号编辑</title>
		<base target="_self" />
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
		<script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
		<script type="text/javascript">
		//如果当前是编辑完后又返回的，那么关闭并刷新父页面
		   if('${isEdit}'=='true'){
				returnValue = "editOk";
		   		this.close();
		   	}
        </script>
	</head>
	<body>
		<form method="post" name="form1" action="<%=path%>/limit.do?method=edit">
			<input type="hidden" name="id" value="${limitCar.id }">
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">车辆限行号编辑</td>
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
											<td height="10" colspan="9">日期：从
											<input type="text" name="beginDate" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" value="${limitCar.beginDate }" >
											到<input type="text" name="endDate" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" value="${limitCar.endDate }" ></td>
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
											<td align="right" class="head_form1" width="12%">周一限行号码：<font color="red">*</font></td>
											<td align="left"  class="head_form1" width="33%">&nbsp;
											    <select name="mon" id="mon" style="width: 130px;" >
													<option value="<%=Contants.L_ONE %>"><%=Contants.L_ONE %></option>
													<option value="<%=Contants.L_TWO %>"><%=Contants.L_TWO %></option>
													<option value="<%=Contants.L_THREE %>"><%=Contants.L_THREE %></option>
													<option value="<%=Contants.L_FOUR %>"><%=Contants.L_FOUR %></option>
													<option value="<%=Contants.L_FIVE %>"><%=Contants.L_FIVE %></option>
												</select>
											</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">周二限行号码：<font color="red">*</font></td>
											<td align="left"  class="head_form1" width="33%">&nbsp;
											    <select name="tues" id="tues" style="width: 130px;"   >
													<option value="<%=Contants.L_ONE %>"><%=Contants.L_ONE %></option>
													<option value="<%=Contants.L_TWO %>"><%=Contants.L_TWO %></option>
													<option value="<%=Contants.L_THREE %>"><%=Contants.L_THREE %></option>
													<option value="<%=Contants.L_FOUR %>"><%=Contants.L_FOUR %></option>
													<option value="<%=Contants.L_FIVE %>"><%=Contants.L_FIVE %></option>
												</select>
											</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">周三限行号码：<font color="red">*</font></td>
											<td align="left"  class="head_form1" width="33%">&nbsp;
											    <select name="wed" id="wed" style="width: 130px;"  >
													<option value="<%=Contants.L_ONE %>"><%=Contants.L_ONE %></option>
													<option value="<%=Contants.L_TWO %>"><%=Contants.L_TWO %></option>
													<option value="<%=Contants.L_THREE %>"><%=Contants.L_THREE %></option>
													<option value="<%=Contants.L_FOUR %>"><%=Contants.L_FOUR %></option>
													<option value="<%=Contants.L_FIVE %>"><%=Contants.L_FIVE %></option>
												</select>
											</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">周四限行号码：<font color="red">*</font></td>
											<td align="left"  class="head_form1" width="33%">&nbsp;
											    <select name="thrus" id="thrus" style="width: 130px;" >
													<option value="<%=Contants.L_ONE %>"><%=Contants.L_ONE %></option>
													<option value="<%=Contants.L_TWO %>"><%=Contants.L_TWO %></option>
													<option value="<%=Contants.L_THREE %>"><%=Contants.L_THREE %></option>
													<option value="<%=Contants.L_FOUR %>"><%=Contants.L_FOUR %></option>
													<option value="<%=Contants.L_FIVE %>"><%=Contants.L_FIVE %></option>
												</select>
											</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">周五限行号码：<font color="red">*</font></td>
											<td align="left"  class="head_form1" width="33%">&nbsp;
											    <select name="friday" id="friday" style="width: 130px;" >
													<option value="<%=Contants.L_ONE %>"><%=Contants.L_ONE %></option>
													<option value="<%=Contants.L_TWO %>"><%=Contants.L_TWO %></option>
													<option value="<%=Contants.L_THREE %>"><%=Contants.L_THREE %></option>
													<option value="<%=Contants.L_FOUR %>"><%=Contants.L_FOUR %></option>
													<option value="<%=Contants.L_FIVE %>"><%=Contants.L_FIVE %></option>
												</select>
											</td>
										</tr>
										<tr height="20">
											<td></td>
										</tr>
										<tr>
											<td align="center" colspan="3">
												<table>
													<tr>
														<td nowrap="nowrap" align="center"><input type="submit" class="button" value="确定"></td>
														<td nowrap="nowrap"><input type="button" class="button" value="取消" onclick="window.close()"></td>
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
	<script type="text/javascript">
			document.getElementById("mon").value='${limitCar.mon }';
			document.getElementById("tues").value='${limitCar.tues }';
			document.getElementById("wed").value='${limitCar.wed }';
			document.getElementById("thrus").value='${limitCar.thrus }';
			document.getElementById("friday").value='${limitCar.friday }';
		    
		   	
        </script>
</html>