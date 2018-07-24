<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>编辑楼盘信息</title>
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
		  function goEdit(){
		  	window.location="lp.do?method=getLpById&key=1";
		  }
		
		
        </script>
	</head>
	<body >
		<form method="post" name="modifyLpForm">
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">

				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">楼盘基本信息</td>
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
								<td align="center">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="10" colspan="9"><input type="hidden" name="code" id="code"><input type="hidden" name="type" id="type"></td>
										</tr>
										<tr>
											<td height="10" colspan="9"><input type="hidden" id="lpId" name="lpId" value="${elp.lpId}"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left">
										<tr>
											<td align="right" class="head_form1" width="12%">楼盘编号：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;${elp.lpCode}</td>
											<td align="right" class="head_form1" width="13%">楼盘名称：</td>
											<td align="left" class="head_form1">&nbsp;${elp.lpName}</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">所属区域：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;${elp.regional}</td>
											<td align="right" class="head_form1" width="23%">物业类型：</td>
											<td align="left" class="head_form1">&nbsp;${elp.ppType}</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">开发单位：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;${elp.developCompany}</td>
											<td align="right" class="head_form1" width="23%">设计单位：</td>
											<td align="left" class="head_form1">&nbsp;${elp.designCompany}</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">建设单位：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;${elp.buildCompany}</td>
											<td align="right" class="head_form1" width="23%">占地面积：</td>
											<td align="left" class="head_form1">&nbsp;${elp.constructionArea}</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">使用面积：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;${elp.useArea}</td>
											<td align="right" class="head_form1" width="23%">绿化面积：</td>
											<td align="left" class="head_form1">&nbsp;${elp.virescenceArea}</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">开工日期：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;${elp.beginDate}</td>
											<td align="right" class="head_form1" width="23%">竣工日期：</td>
											<td align="left" class="head_form1">&nbsp;${elp.endDate}</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">进驻日期：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;${elp.useDate}</td>
											<td align="right" class="head_form1" width="23%">联系电话：</td>
											<td align="left" class="head_form1">&nbsp;${elp.phone}</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">传 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;${elp.fax}</td>
											<td align="right" class="head_form1" width="23%">地 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</td>
											<td align="left" class="head_form1">&nbsp;${elp.address}</td>
										</tr>
										<tr height="20"><td></td></tr>
											<tr>
											<td width="100%" colspan = "4">
													<table border="0" cellpadding="0" cellspacing="0" class="RptTable" id="metertb" >
														<tr>
															<td align="left" class="head_one" colspan="5">表具添加</td>
														</tr>
														<tr>
															<th class="RptTableHeadCellLock" width="5%"><input type="checkbox" onclick="selectedAll();" name="meter" width="5%"></th>
															<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</th>
															<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">表具编号</th>
															<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">表具名称</th>
														</tr>
														<c:choose>
															<c:when test="${empty meterlist}">
																<tr align="center" id="bjInfo">
																	<td colspan="5" align="center" class="head_form1">
																		<font color="red">没有表具信息!</font>
																	</td>
																</tr>
															</c:when>
															<c:otherwise>
																<c:forEach items="${meterlist}" var="m" varStatus="vs">
																	<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
																		<td class="RptTableBodyCellLock" align="center"><input type="checkbox" id="meterId" name="meterId" ></td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${m[0].code}">${m[0].code}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${m[1].codeName}">${m[1].codeName}&nbsp;</td>
																	</tr>
																</c:forEach>
															</c:otherwise>
														</c:choose>
													</table>
											</td>
										</tr>
										<tr height="20"><td></td></tr>
										<tr>
											<td align="right" colspan="20">
												<table border="0">
													<tr align="right">
														<td  align="center"><input class="button" onclick="goEdit();" type="button" value="修改"></td>
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
