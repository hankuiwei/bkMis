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
	<title>${str }客户跟踪记录</title>
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
			if("" == document.getElementById("trackRecord.customerName").value){
				alert("客户名称不能为空!");
				return;
			}
			if("" == document.getElementById("trackRecord.status").value){
				alert("状态不能为空!");
				return;
			}
			document.forms[0].action="<%=basePath %>trackRecord.do?method=saveOrUpdate";
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
								<td width="165" nowrap="nowrap" class="form_line">${str }客户跟踪记录</td>
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
															客户名称：
														</td>
														<td class="fist_rows">
															<input type="text" name="trackRecord.customerName" id="trackRecord.customerName" value="${trackRecordForm.trackRecord.customerName }" >&nbsp;&nbsp;&nbsp;
														</td>
														
													</tr>
													<tr>
														<td class="head_rols" align="right">
															编号：
														</td>
														<td class="fist_rows">
															<input type="text" name="trackRecord.code" id="trackRecord.code" value="${trackRecordForm.trackRecord.code }" >&nbsp;&nbsp;&nbsp;
															<input type="hidden" name="trackRecord.id" value="${trackRecordForm.trackRecord.id }">
															<input type="hidden" name="trackRecord.userId" value="${trackRecordForm.trackRecord.userId }">
															<input type="hidden" name="trackRecord.createDate" value="${trackRecordForm.trackRecord.createDate }">
 														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															状态：
														</td>
														<td class="head_form1">
															<select name="trackRecord.status">
																<option value="">--请选择--</option>
																<option value="成交" <c:if test="${trackRecordForm.trackRecord.status eq '成交'}">selected</c:if>>成交${trackRecordForm.trackRecord.status}</option>
																<option value="未成交" <c:if test="${trackRecordForm.trackRecord.status eq '未成交'}">selected</c:if>>未成交</option>
																<option value="正在跟踪" <c:if test="${trackRecordForm.trackRecord.status eq '正在跟踪'}">selected</c:if>>正在跟踪</option>
															</select>
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															所属行业：
														</td>
														<td class="head_form1">
															<select name="trackRecord.workField" id="trackRecord.workField" style="width: 130">
																	<option value="">--请选择--</option>
																	<c:forEach items="${codeList}" var="v">
																		<option value="${v.codeName }" <c:if test="${trackRecordForm.trackRecord.workField == v.codeName}">selected</c:if>>${v.codeName }</option>
																	</c:forEach>
															</select>
														</td>
													</tr>
													<tr>
														<td class="head_rols" align="right">
															联系方式：
														</td>
														<td class="fist_rows">
															<input type="text" name="trackRecord.linkWay" id="name" value="${trackRecordForm.trackRecord.linkWay }">&nbsp;&nbsp;&nbsp;
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															说明：
														</td>
														<td class="head_form1">
															<textarea rows="5" name="trackRecord.bz" cols="50">${trackRecordForm.trackRecord.bz }</textarea>
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
