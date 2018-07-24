<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	 <title>房间详细信息</title>
	    <base target="_self" />
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/main.js" charset="UTF-8"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
		<script type="text/javascript">
		  function getHistoryClient(){
             var roomId = document.getElementById("roomId").value;
             if(roomId != null){
                document.roomInfoForm.action = "<%=path%>/room.do?method=getHistoryClient&roomId="+roomId;
                document.roomInfoForm.submit();
             }
		  }
        </script> 
  </head>
	<body>
		<form method="post" name="roomInfoForm">
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">房间详细信息</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" 	height="35"></td>
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
											<td height="10" colspan="9"><input type="hidden" name="roomId" id="roomId" value="${eroom.roomId}"></td>
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
											<td align="left" class="head_one" colspan="5">房间基本信息</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">房&nbsp;&nbsp;间&nbsp;&nbsp;号：</td>
											<td align="left" class="head_form1" width="33%">&nbsp;&nbsp;${eroom.roomCode}</td>
											<td align="right" class="head_form1" width="23%">所属楼栋：</td>
											<td align="left" class="head_form1">&nbsp;${eroom.EBuilds.buildName }</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="23%">房&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：</td>
											<td align="left" class="head_form1">&nbsp;${eroom.roomType }</td>
											<td align="right" class="head_form1" width="12%">单&nbsp;&nbsp;元&nbsp;&nbsp;号：</td>
											<td align="left" class="head_form1" width="33%">&nbsp;&nbsp;${eroom.unitId }</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="23%">&nbsp;楼&nbsp;&nbsp;层&nbsp;&nbsp;数：</td>
											<td align="left" class="head_form1" >&nbsp;&nbsp;${eroom.floor }</td>
											<td align="right" class="head_form1" width="12%">建筑面积：</td>
											<td align="left" class="head_form1" width="33%">&nbsp;&nbsp;${eroom.constructionArea }</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="23%">使用面积：</td>
											<td align="left" class="head_form1" >&nbsp;&nbsp;${eroom.useArea }</td>
											<td align="right" class="head_form1" width="12%">房间朝向：</td>
											<td align="left" class="head_form1" width="33%">&nbsp;&nbsp;${eroom.toward }</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="23%">使用状态：</td>
											<td align="left" class="head_form1">&nbsp;&nbsp;${eroom.status }</td>
											<td align="right" class="head_form1" width="12%">所属区域：</td>
											<td align="left" class="head_form1" width="33%">&nbsp;&nbsp;${eroom.regional }</td>
										</tr>
											<td align="right" class="head_form1" width="23%">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
											<td align="left" class="head_form1" colspan="3">&nbsp;&nbsp;${eroom.bz }</td>
										<tr></tr>
										<tr height="20"><td></td></tr>
											<tr>
											<td width="100%" colspan = "4">
													<table border="0" cellpadding="0" cellspacing="0" class="RptTable">
														<tr>
															<td align="left" class="head_one" colspan="5">房间表具信息</td>
														</tr>
														<tr>
															<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</th>
															<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">表具编号</th>
															<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">表具名称</th>
														</tr>
														<c:choose>
															<c:when test="${empty meterlist}">
																<tr align="center" id="bjInfo">
																	<td colspan="5" align="center" class="head_form1">
																		<font color="red">暂时没有表具信息!</font>
																	</td>
																</tr>
															</c:when>
															<c:otherwise>
																<c:forEach items="${meterlist}" var="m" varStatus="vs">
																	<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${m[0].code}">${m[0].code}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${m[0].type}">${m[0].type}&nbsp;</td>
																	</tr>
																</c:forEach>
															</c:otherwise>
														</c:choose>
													</table>
											</td>
										</tr>
										<tr height="20"><td></td></tr>
											<tr>
											<td width="100%" colspan = "4">
													<table border="0" cellpadding="0" cellspacing="0" class="RptTable">
														<tr>
															<td align="left" class="head_one" colspan="8">房间设施设备信息</td>
														</tr>
														<tr>
															<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</th>
											                <th nowrap="nowrap" class="RptTableHeadCellLock" align="center">设备编号</th>
											                <th nowrap="nowrap" class="RptTableHeadCellLock" align="center">设备名称</th>
											                <th nowrap="nowrap" class="RptTableHeadCellLock" align="center">设备类型</th>
											                <th nowrap="nowrap" class="RptTableHeadCellLock" align="center">数量</th>
											                <th nowrap="nowrap" class="RptTableHeadCellLock" align="center">备注</th>
														</tr>
														<c:choose>
															<c:when test="${empty equiplist}">
																<tr align="center">
																	<td colspan="8" align="center" class="head_form1"><font color="red">暂时没有房间设备信息!</font></td>
																</tr>
															</c:when>
															<c:otherwise>
																<c:forEach items="${equiplist}" var="e" varStatus="vs">
																	<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
																		<!-- equiplist里面存放的三个对象  -->
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${e[1].code}">${e[1].code}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${e[1].name}">${e[1].name}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${e[2].name}">${e[2].name}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${e[0].amount}">${e[0].amount}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${e[0].bz}">${e[0].bz}&nbsp;</td>
																	</tr>
																</c:forEach>
															</c:otherwise>
														</c:choose>
												</table>
											</td>
										</tr>
										<tr height="50"><td></td></tr>
										<tr>
											<td align="right" colspan="4">
												<table>
													<tr>
														<td nowrap="nowrap" align="right"><input class="button" onclick="getHistoryClient();" type="button" value="历史住户查询"></td>
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
