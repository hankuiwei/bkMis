<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>客户曾住/在住房间列表</title>
		<base target="_self" />
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/resources/js/main.js" charset="UTF-8"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/RptTable.js" defer="defer"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript">
        function backRoomInfo(roomId){
            if(roomId != null){
               document.historyClientroomform.action = "<%=path%>/room.do?method=getRoomInfo&roomId="+roomId;
               document.historyClientroomform.submit();
            }
        }
      </script>
	</head>
	<body style="overflow-y: hidden;">
		<form method="post" name="historyClientroomform">
			<table width="99%" height="96%" border="0" align="center"
				cellpadding="0" cellspacing="0" style="layout: fixed">
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">客户曾住/在住房间列表</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr height="95%">
					<td class="menu_tab2" align="center" valign="top">
						<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="center">
									<!-- 查询条件start -->
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="10" colspan="9"></td>
										</tr>
										
										<tr>
											<td height="10" colspan="9"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr height="95%">
								<td valign="top">
									<!-- 表单内容区域 -->
									<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed">
										<tr height="95%">
											<td width="100%">
												<div id="div1" class="RptDiv">
													<table border="0" cellpadding="0" cellspacing="0" class="RptTable">
														<tr>
															<th width="10%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
															<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</th>
															<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">室号全称</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间建筑面积</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间使用面积</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">入驻日期</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">迁出日期</th>
														</tr>
														<c:choose>
															<c:when test="${empty list}">
																<tr align="center">
																	<td colspan="13" align="center" class="head_form1">
																		<font color="red">暂时没有信息!</font>
																	</td>
																</tr>
															</c:when>
															<c:otherwise>
																<c:forEach items="${list}" var="r" varStatus="vs">
																	<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
																		<td width="10%" nowrap="nowrap" class="RptTableBodyCellLock" align="center">${vs.count}&nbsp;</td>
																		<td width="10%" nowrap="nowrap" class="RptTableBodyCell" align="center" >${r[0].roomCode}&nbsp;</td>
																		<td width="10%" nowrap="nowrap" class="RptTableBodyCell" align="center" >${r[0].roomFullName}&nbsp;</td>
																		<td width="10%" nowrap="nowrap" class="RptTableBodyCell" align="center" >${r[0].constructionArea}&nbsp;</td>
																		<td width="10%" nowrap="nowrap" class="RptTableBodyCell" align="center">${r[0].useArea}&nbsp;</td>
																		<td width="10%" nowrap="nowrap" class="RptTableBodyCell" align="center" >${r[1].beginDate}&nbsp;</td>
																		<td width="10%" nowrap="nowrap" class="RptTableBodyCell" align="center" >${r[1].endDate}&nbsp;</td>
																	</tr>
																</c:forEach>
															</c:otherwise>
														</c:choose>
													</table>
												</div>
											</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<!-- 分页start -->
										<tr>
											<td colspan="10">
												<table width="100%" cellpadding="0" cellspacing="0">
													<tr>
														<td class="form_line3">&nbsp;</td>
														<td class="form_line3" colspan="8">${pagination }</td>
														<td class="form_line3">&nbsp;</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td align="right">
												<!-- 如果想在enter键敲下的时候默认被点击，那么只需将button的id设置为focuson即可 -->
												<input type="button" class="button" id="focuson" value="返回" onclick="window.close();" align="right">
											</td>
										</tr>
										<!-- 分页end -->
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








