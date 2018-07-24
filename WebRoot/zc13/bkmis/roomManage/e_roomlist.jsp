<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>房间信息列表</title>
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/resources/js/main.js" charset="UTF-8"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/RptTable.js" defer="defer"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/validate.js"></script>
		<script type="text/javascript">
      //按照楼盘名称查询对应的楼栋
      function search(){
      	 var dd = document.getElementById("suseArea").value;
      	 var dd2 = document.getElementById("guseArea").value;
      	 if(dd!=null && dd!=""){
      	 	if(!isNumber(dd)){
	      	 	alert("使用面积只能是数字");
	      	 	return false;
	      	 	}
      	 }else if(dd2!=null && dd2!="" ){
      	 	if(!isNumber(dd2)){
      	 		alert("使用面积只能是数字");
	      	 	return false;
      	 	}
      	 }
      	 /* 用于初始化显示高级查询条件的标志的影藏域的值为空 */
      	 document.getElementById("adFlag").value = "";
         var ad = document.getElementById("ad").value;
         if(ad == "基本查询"){
            document.getElementById("adFlag").value = "true";
         }
         document.roomform.action = "<%=path%>/room.do?method=searchRoom";
         document.roomform.submit();
      }
      //高级查询
      function advancedSearch(){
           var temp ;
           var status = document.getElementById("advanced").style.display;
           if(status == "block"){
             temp= document.getElementById("ad").value = "高级查询";
             document.getElementById("advanced").style.display = "none";
             document.getElementById("roomType").value = "";
             document.getElementById("suseArea").value = "";
             document.getElementById("guseArea").value = "";
             document.getElementById("toward").value = "";
             document.getElementById("status").value = "";
           }else{
            temp= document.getElementById("ad").value = "基本查询";
            document.getElementById("advanced").style.display = "block";
            document.getElementById("roomType").value = "";
            document.getElementById("suseArea").value = "";
            document.getElementById("guseArea").value = "";
            document.getElementById("toward").value = "";
            document.getElementById("status").value = "";
           }
      }
      //导出楼栋报表
      function exportLr(isNull){
      		if(isNull!='0') {
				document.roomform.action = "<%=path%>/room.do?method=exportRoomExcel";
				document.roomform.submit();
			} else {
				alert("没有记录可导出");
			}
      }
    //房间详细信息查询
    function getRoomInfo(roomId){
        if(roomId!= null){
            window.showModalDialog("<%=path%>/room.do?method=getRoomInfo&roomId="+roomId,"","dialogWidth=760px;dialogHeight=550px;center=1;status:no;");
        }
    }
    /* 用于显示高级查询 */
    function showAdance(){
       var adFlag =  document.getElementById("adFlag");
       var temp ;
       if(adFlag.value == "true"){
            temp= document.getElementById("ad").value = "基本查询";
            document.getElementById("advanced").style.display = "block";
            /* 设置查询条件使用面积 ，如果没输入值，则在查询完了显示为空，避免显示从form中得到的0.0 */
            var suseArea = document.getElementById("suseArea").value;
            var guseArea = document.getElementById("guseArea").value;
            if(suseArea == 0.0){
                document.getElementById("suseArea").value = "";
            }
            if(guseArea == 0.0){
                document.getElementById("guseArea").value = "";
            }
         }
    }
    </script>
	</head>
	<body style="overflow-y: hidden;" onload="showAdance();">
		<!-- 加载页面div -->
		<jsp:include page="/loading.jsp"></jsp:include>
		<!-- 加载页面div -->
		<form method="post" name="roomform">
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
								<td width="165" nowrap="nowrap" class="form_line">房间信息列表</td>
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
											<td height="10" colspan="9"><!-- 用于保持显示高级查询的条件的 --><input type="hidden" id="adFlag" value="${adFlag }" name="adFlag">
											                            <input type="hidden" id="lpId" name="lpId" value="${roomForm.lpId}">
											                            <input type="hidden" id="tablename" name="tablename" value="${roomForm.tablename}">
											                            <input type="hidden" id="buildId" name="buildId" value="${roomForm.buildId}">
											</td>
										</tr>
										<tr>
											<td class="txt" nowrap="nowrap"><img src="<%=path%>/resources/images/fdj.gif" width="15" height="14" />查询条件：</td>
											<td nowrap="nowrap" align="right">房&nbsp;&nbsp;间&nbsp;&nbsp;号：</td>
											<td class="txt"><input type="text" id=roomCode name="roomCode" value="${roomForm.roomCode }"></td>
											<td align="right" nowrap="nowrap">楼&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;层：</td>
											<td class="txt"><input type="text" onKeyUp="this.value=this.value.replace(/\D/g,'')" id="floor" name="floor" value="${roomForm.floor }"></td>
											<td>
												<!-- 如果想在enter键敲下的时候默认被点击，那么只需将button的id设置为focuson即可 -->
												<input type="button" class="button" id="focuson" value="查询" onclick="search();" align="right">
												<input type="button" class="button" id="ad" value="高级查询" onclick="advancedSearch();">
											</td>
										</tr>
										<tr>
											<td width = "15px"></td>
											<td colspan = "6">
												<div style="display:none" id="advanced">
												<table width="100%" border="0" cellspacing="0" cellpadding="0"  bgcolor="#CDCCCC">			
													<tr>
														<td class="txt" nowrap="nowrap"></td>
														<td nowrap="nowrap" align="right">房&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：</td>
														<td class="txt">
															<select name="roomType" id = "roomType" style="width: 130px;">
																<option value="" selected="selected">- - -请选择- - -</option>
																<c:forEach items="${roomTypelist }" var="r">
																	<option value="${r.codeName }"
																	<c:if test="${roomForm.roomType eq r.codeName}">selected</c:if>>${r.codeName }</option>
																</c:forEach>
															</select>
														</td>
														<td nowrap="nowrap" align="right">使用面积：</td>
														<td class="txt">
															<input type="text" id="suseArea" name="suseArea"  value="${roomForm.suseArea }">㎡&nbsp;
															&nbsp;至&nbsp;
															<input type="text" id="guseArea" name="guseArea"  value="${roomForm.guseArea }">㎡&nbsp;
														</td>
														<td nowrap="nowrap" align="right"></td>
														<td colspan = "2"></td>
													</tr>
													<tr>
														<td class="txt" nowrap="nowrap"></td>
														<td nowrap="nowrap" align="right">房间朝向：</td>
														<td class="txt">
															<select name="toward" id="toward" style="width: 130px;">
																<option value="" selected="selected">- - -请选择- - -</option>
																<c:forEach items="${towardlist}" var="t">
																	<option value="${t.codeName}" 
																	<c:if test="${roomForm.toward eq t.codeName}">
																		selected
																	</c:if>
																	>${t.codeName}</option>
																</c:forEach>
															</select>
														</td>
														<td nowrap="nowrap" align="right">使用状态：</td>
														<td class="txt">
															<select name="status" id = "status" style="width: 130px;">
																<option value="" selected="selected">- - -请选择- - -
																</option>
																<c:forEach items="${statuslist}" var="s">
																	<option value="${s.codeName}"
																	<c:if test="${roomForm.status eq s.codeName}">
																		selected
																	</c:if>
																	
																	>${s.codeName}</option>
																</c:forEach>
															</select>
														</td>
														<td nowrap="nowrap" align="right"></td>
														<td class="txt"></td>
													</tr>
												</table>
												</div>
											</td>	
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
									<table width="100%" height="100%" border="0" cellspacing="0"
										cellpadding="0" style="table-layout: fixed">
										<tr height="95%">
											<td width="100%">
												<div id="div1" class="RptDiv">
													<table border="0" cellpadding="0" cellspacing="0"
														class="RptTable">
														<tr>
															<th width="6%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
															<th width="10%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">房间号</th>
															<th width="9%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">房型</th>
															<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">所属楼栋</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">单元号</th>
															<th width="6%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">楼层</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">建筑面积</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">使用面积</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">朝向</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">使用状态</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">所属区域</th>
															<th width="13%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">备注</th>
														</tr>
														<c:choose>
															<c:when test="${empty roomlist}">
																<tr align="center">
																	<td colspan="13" align="center" class="head_form1">
																		<font color="red">暂时没有房间信息!</font>
																	</td>
																</tr>
															</c:when>
															<c:otherwise>
																<c:forEach items="${roomlist}" var="r" varStatus="vs">
																	<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="getRoomInfo(${r.roomId});">
																		<td nowrap="nowrap" class="RptTableBodyCellLock" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCellLock" align="center" title="${r.roomCode}">${r.roomCode}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCellLock" align="center" title="${r.roomType}">${r.roomType}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.EBuilds.buildName}">${r.EBuilds.buildName}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.unitId}">${r.unitId}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.floor}">${r.floor}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.constructionArea}">${r.constructionArea}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.useArea}">${r.useArea}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.toward}">${r.toward}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.status}">${r.status}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.regional}">${r.regional}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.bz}">
																		 <c:choose> 
																		    <c:when test="${fn:length(r.bz) > 20}"> 
																		     <c:out value="${fn:substring(r.bz, 0, 20)}......" /> 
																		    </c:when> 
																		    <c:otherwise> 
																		     	<c:out value="${r.bz}" /> 
																		    </c:otherwise> 
																		   </c:choose> &nbsp;
																		</td>
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
										<tr>
											<td align="right">
												<table>
													<tr>
														<td nowrap="nowrap"><input type="button" onclick="exportLr('${size}');" class="button" value="导出报表"></td>
													</tr>
												</table>
											</td>
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








