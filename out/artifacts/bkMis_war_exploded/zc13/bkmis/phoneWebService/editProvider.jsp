<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>编辑供应商信息</title>
		<base target="_self" />
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
		<script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
		<style type="text/css">
		.initiated_event_photo img{width:400px; height:320px; margin-left:78px; display:none;}
		#newPreview{float:left; display:none;width:90%; height:auto; text-align:left; margin:9px 0 0 110px; font-size:14px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);}
		</style>
		<script type="text/javascript">
			function save(){
				document.actionForm.action="<%=path%>/phoneCost.do?method=saveProvider";
				document.actionForm.submit();
			}
        </script>
        <c:if test="${!empty alertMessage}">
		<script type="text/javascript">
			alert("${alertMessage}");
		</script>
		</c:if>
	</head>
	<body >
		<form method="post" name="actionForm">
			<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
			<input type="hidden" name="provider.id" value="${provider.id }" />
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
			
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">修改供应商信息</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="menu_tab2" align="center">
						<table width="60%" border="0" cellspacing="0" cellpadding="0">
							
							<tr>
								<td>
									<table id="RptTable" width="100%" border="0" cellspacing="0" cellpadding="0" align="left">
										<tr>
											<td align="right" class="head_form1" width="12%">供应商代码：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;<input type="text" id="provider.code" name="provider.code" value="${provider.code }"></td>
											<td align="right" class="head_form1" width="23%">供应商名称：</td>
											<td align="left" class="head_form1">&nbsp;<input type="text" id="provider.name" name="provider.name" value="${provider.name }"></td>
										</tr>
										
										<tr height="20"><td></td></tr>
										<tr>
											<td width="100%" colspan = "4">
													<table border="0" cellpadding="0" cellspacing="0" class="RptTable" id="metertb" >
														<tr>
															<td align="left" class="head_one">市话资费标准</td>
														</tr>
														<tr>
															<td nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;前<input type="text" name="provider.frontSeconds" value="${provider.frontSeconds }" />秒&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="provider.frontCost" value="${provider.frontCost }" />元</td>
														</tr>
														<tr>
															<td nowrap="nowrap" class="RptTableBodyCell" align="center">之后每<input type="text" name="provider.nextEachSeconds" value="${provider.nextEachSeconds }" />秒&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="provider.eachCost" value="${provider.eachCost }" />元</td>
														</tr>
													</table>
											</td>
										</tr>
										
										<tr>
											<td align="center" colspan="20">
												<table border="0">
													<tr align="right">
														<td  align="center"><input class="button" onclick="save();" type="button" value="修改"></td>
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
