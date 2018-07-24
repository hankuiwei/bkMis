<%@ page language="java" import="java.util.*,com.zc13.util.*"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>客户反馈</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	 <script type="text/javascript"
			src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript">
		//是否领材料
		function sel(obj){
			var tr1 = document.getElementById("tr1");
			var tr2 = document.getElementById("tr2");
			var sel = obj.value;
			if(sel=="2"){
				tr1.style.display="none";
				tr2.style.display="none";
			}
			if(sel=="1"){
				tr1.style.display="block";
				tr2.style.display="block";
			}
		}
		function finish(){
			document.form1.action = "<%=path%>/client.do?method=dealClient";
			document.form1.submit();
		}
		
		function return1(){
			//window.location.href="client.do?method=getList";
			document.form1.action="client.do?method=getList";
			document.form1.submit();
		}
		
	</script>
	
	
</head>
<body>
<form name="form1" method="post">
	<table width="99%" height = "96%" border="0" cellpadding="0" align="center" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5">
    			<!-- 查询条件start -->
    			<input type="hidden" id="flag" name="flag" value="${SerClientMaintainForm.flag }">
    			<input type="hidden" id="cx_buildId" name="cx_buildId" value="${SerClientMaintainForm.cx_buildId }">
    			<input type="hidden" id="cx_sendedMan" name="cx_sendedMan" value="${SerClientMaintainForm.cx_sendedMan }">
    			<input type="hidden" id="selstatus" name="selstatus" value="${SerClientMaintainForm.selstatus }">
    			<input type="hidden" id="begindate" name="begindate" value="${SerClientMaintainForm.begindate }">
    			<input type="hidden" id="enddate" name="enddate" value="${SerClientMaintainForm.enddate }">
    			<input type="hidden" id="cx_isEnabled" name="cx_isEnabled" value="${SerClientMaintainForm.cx_isEnabled }">
    			<!-- 查询条件end -->
    		
    			<!-- 数据中的一些未显示的信息start -->
    			<input type="hidden" id="lpId" name="lpId" value="${bean.lpId }">
    			<input type="hidden" id="id" name="id" value="${bean.id }">
    			<input type="hidden" id="sendDutyMan" name="sendDutyMan" value="${bean.sendDutyMan }">
    			<input type="hidden" id="status" name="status" value="${bean.status }">
    			<input type="hidden" id="sendedMan" name="sendedMan" value="${bean.sendedMan }">
    			<input type="hidden" id="appearanceTime" name="appearanceTime" value="${bean.appearanceTime }">
    			<input type="hidden" id="leaveTime" name="leaveTime" value="${bean.leaveTime }">
    			<!-- 数据中的一些未显示的信息end -->
    			
    		</td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">客户反馈</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr height = "95%"><!-- 这个td负责画正式内容的左、右、下方的边框 -->
    		<td class="menu_tab2">
     			<table width="95%" align="center" border="0" cellspacing="0" cellpadding="0">
     				<tr>
     					<th align="left" colspan="4" class="head_one">来电来访记录</th>
     				</tr>
     				<tr>
							<td class="head_rols" align="right">报修类型：</td>
							<td class="fist_rows">
								&nbsp;${bean.type }
								<input type="hidden" name="type" id="type" value="${bean.type }" />
							</td>
							<td class="fist_rows" align="right">报修项目：</td>
							<td class="fist_rows">
								&nbsp;${bean.project }
								<input type="hidden" name="project" id="project" value="${bean.project }" />
								<input type="hidden" name="projectId" id="projectId" value="${bean.projectId }" />
							</td>
					</tr>
					<c:if test="${bean.type eq '业主报修'}">
					<tr>
						<td class="head_rols" align="right">楼幢：</td>
						<td class="head_form1" align="left">
							<select id="buildId1" style="width: 130" disabled="disabled">
								<option value="0">请选择</option>
							<c:choose>
							<c:when test="${empty list3}">
							</c:when>
							<c:otherwise>
							<c:forEach items="${list3}" var="v">
								<option value="${v.buildId }" <c:if test="${bean.buildId == v.buildId}">selected</c:if>>${v.buildName }</option>
							</c:forEach>
							</c:otherwise>
							</c:choose>
							</select>
							<input type="hidden" name="buildId" value="${bean.buildId }" />
						</td>
						<td class="head_form1" align="right" id="td1">房间号：</td>
						<td class="head_form1" align="left">
							<select id="roomId1" style="width: 130" disabled="disabled">
								<option value="0">请选择</option>
							<c:choose>
							<c:when test="${empty list4}">
							</c:when>
							<c:otherwise>
							<c:forEach items="${list4}" var="v">
								<option value="${v.roomId }" <c:if test="${bean.roomId == v.roomId}">selected</c:if> >${v.roomCode }</option>
							</c:forEach>
							</c:otherwise>
							</c:choose>
							</select>
							<input type="hidden" name="roomId" value="${bean.roomId }" />
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right">联系电话：</td>
						<td class="head_form1">&nbsp;${bean.phone }<input type="hidden" value="${bean.phone }" name="phone" ></td>
						<td class="head_form1" align="right">报修人：</td>
						<td class="head_form1">&nbsp;${bean.name }<input type="hidden" value="${bean.name }" name="name"></td>
					</tr>
					</c:if>
					
					<c:if test="${bean.type eq '公共区域'}">
					<tr>
						<td class="head_left" align="right">区域：</td>
						<td class="head_form1" align="left">
							<select id="area1" style="display: block;" disabled="disabled">
							<c:choose>
							<c:when test="${empty list2}">
							</c:when>
							<c:otherwise>
							<c:forEach items="${list2}" var="v">
								<option value="${v.codeName }" <c:if test="${bean.area eq v.codeName }">selected</c:if> >${v.codeName }</option>
							</c:forEach>
							</c:otherwise>
							</c:choose>
							</select>
							<input type="hidden" name="area" value="${bean.area }" />
						</td>
						<td class="head_form1" align="right">联系电话：</td>
						<td class="head_form1"><input type="text" value="${bean.phone }" name="phone" readonly="readonly" ></td>
					</tr>
					<tr>
						<td class="head_left" align="right">报修人：</td>
						<td class="head_form1" colspan="3">&nbsp;${bean.name }<input type="hidden" value="${bean.name }" name="name"></td>
					</tr>
					</c:if>
					<tr>
						<td class="head_left" align="right">报修内容：</td>
						<td class="head_form1" colspan="3">
							<textarea rows="3" name="contentExplain" style="width: 80%" id="contentExplain" disabled="disabled">${bean.contentExplain }</textarea>
							<input type="hidden" name="contentExplain" value="${bean.contentExplain }" />
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right">报修时间：</td>
						<td class="head_form1">&nbsp;${bean.date}<input type="hidden" value="${bean.date}" name="date"></td>
						<td class="head_form1" align="right">接听(接待)人：</td>
						<td class="head_form1">&nbsp;${bean.clerk }<input type="hidden" value="${bean.clerk }" name="clerk"></td>
					</tr>
     				<tr>
     					<th align="left" colspan="4" class="head_one">工作情况</th>
     				</tr>
     				
					<tr>
						<td class="head_left" align="right">派工单编号：</td>
						<td class="head_form1" align="left">&nbsp;${bean.sendcardCode }<input type="hidden" name="sendcardCode" id="sendcardCode" value="${bean.sendcardCode }"></td>
						<td class="head_form1" align="right">派工执行人：</td>
						<td class="head_form1" align="left">&nbsp;${bean.sendDutyMan }<input type="hidden" name="sendDutyMan" id="sendDutyMan" value="${bean.sendDutyMan }"></td>
					</tr>
					<tr>
						<td class="head_left" align="right">派工时间：</td>
						<td class="head_form1" colspan="3" align="left">&nbsp;${bean.sendTime }<input type="hidden" name="sendTime" id="sendTime" value="${bean.sendTime }"></td>
					</tr>
					<tr>
     					<td colspan="4">
     						<table width="100%" border="0" cellpadding="0" cellspacing="0" class = "RptTable">
	     						<tr>
	     							<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</td>
			     					<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">员工姓名</td>
			     					<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">到场时间</td>
			     					<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">离场时间</td>
			     					<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">工作时间</td>
			     					<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">状态</td>
	     						</tr>
	     						<c:choose>
	     							<c:when test="${empty dispatchList}">
	     							</c:when>
	     							<c:otherwise>
	     								<c:forEach items="${dispatchList}" var="v" varStatus="vs">
	     									<tr>
	     										<td  nowrap="nowrap" class="RptTableBodyCellLock" align="center">${vs.index+1 }</td>
	     										<td  nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.hrPersonnel.personnelName }</td>
	     										<td  nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.appearanceTime }</td>
	     										<td  nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.leaveTime }</td>
	     										<td  nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.workHours }</td>
	     										<td  nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.status }</td>
	     									</tr>
	     								</c:forEach>
	     							</c:otherwise>
	     						</c:choose>
     						</table>
     					</td>
     					
     				</tr>
					<tr>
						<td class="head_left" align="right">处理方法：</td>
						<td class="head_form1">
							&nbsp;${bean.doMethod }<input type="hidden" name="doMethod" id="doMethod" value="${bean.doMethod }" >
						</td>
						<c:if test="${bean.doMethod eq '物业维修'}">
						<td class="head_form1" align="right">服务费总额：</td>
						<td class="head_form1">
							&nbsp;
							<script type="text/javascript">
								document.write(parseFloat(${bean.amountRate }).toFixed(2).toString());
							</script>
							<input type="hidden" name="amountRate" id="amountRate" value="${bean.amountRate }">
						</td>
						</c:if>
						<c:if test="${bean.doMethod ne '物业维修'}">
						<td class="head_form1" align="right">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
						</c:if>
					</tr>
					<tr>
						<td class="head_left" align="right">维修内容：</td>
						<td class="head_form1" colspan="3">
						<textarea rows="3" style="width: 80%" disabled="disabled" name="maintainContent" id="maintainContent">${bean.maintainContent }</textarea>
						<input type="hidden" name="maintainContent" id="maintainContent" value="${bean.maintainContent }" >
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right">工作：</td>
						<td class="head_form1">&nbsp;
						<script>
						var manHours = "${bean.manHour }";
						if(manHours==""||manHours==null)manHours="0";
						document.write(parseFloat(manHours).toFixed(2).toString());
						</script>
						<input type="hidden" name="manHour" id="manHour" value="${bean.manHour }">小时</td>
						<td class="head_form1" align="right">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
					</tr>
					<tr>
     					<th align="left" colspan="4" class="head_one">耗用材料：</th>
     				</tr>
					<tr>
						<td class="head_left" align="right" width="15%">是否领料：</td>
						<td class="head_form1" width="35%">
							<select id="select11" disabled="disabled">
								<option value="1">是</option>
								<option value="2" selected="selected">否</option>
							</select>
						</td>
						<td class="head_form1" align="right">出料部门：</td>
						<td class="head_form1" width="35%">
							<select name="department1" id="department1" style="width: 130" disabled="disabled">
							<c:choose>
							<c:when test="${empty list5}">
							</c:when>
							<c:otherwise>
							<c:forEach items="${list5}" var="v">
								<option value="${v.codeName }">${v.codeName }</option>
							</c:forEach>
							</c:otherwise>
							</c:choose>
							</select>
							<input type="hidden" name="department" id="department" />
						</td>
					</tr>
					<tr id="tr1" style="display: none;">
						<td class="head_left" colspan="4">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class = "RptTable" id="tb">
								<tr>
									<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">&nbsp;</td>
									<td width="5%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</td>
									<td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">材料编号</td>
								    <td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">材料名称</td>
									<td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">规格</td>
									<td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">单价</td>
									<td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">数量</td>
									<td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">加收方式</td>
									<td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">加收金额</td>
									<td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">金额</td>
								</tr>
									<c:choose>
										<c:when test="${empty list6}">
											<script type="text/javascript">
												document.getElementById("select11").value = "2";
											</script>
										</c:when>
										<c:otherwise>
											<script type="text/javascript">
												document.getElementById("select11").value = "1";
											</script>
											<c:forEach items="${list6}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" id="trm">
											 	<td nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" id="checkbox1" name="checkm" value="${v.id}">
												</td>
								                <td nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count}</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.materialCode}<input type="hidden" name="materialCode" value="${v.materialCode}">&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.materialName}<input type="hidden" name="materialName" value="${v.materialName}">&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.spec}<input type="hidden" name="spec" value="${v.spec}">&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.unitPrice}<input type="hidden" name="unitPrice" value="${v.unitPrice}">&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.amount}<input type="hidden" name="amount"  value="${v.amount}">&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.surchargeWay }<input type="hidden" name="surchargeWay"  value="${v.surchargeWay }">&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.surchargeAmount}<input type="hidden" name="surchargeAmount" value="${v.surchargeAmount}">&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.amountMoney}<input type="hidden" name="amount_money" value="${v.amountMoney}">&nbsp;</td>
								                <script type="text/javascript">
													document.getElementById("department").value="${v.department}";
												</script>
											</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
							</table>
						</td>
					</tr>
					<tr id="tr2" style="display: none;">
						<td class="head_left" align="right">材料总金额：</td>
						<td class="head_form1" align="left" colspan="3">&nbsp;${bean.amountMoney }<input type="hidden" name="amountMoney" value="${bean.amountMoney }"></td>
					</tr>
					
					<tr>
     					<th align="left" colspan="4" class="head_one">验收记录：</th>
     				</tr>
     				<tr>
						<td class="head_left" align="right">记录：</td>
						<td class="head_form1" colspan="3">
							<textarea rows="3" style="width: 80%" name="checkRecord1" id="checkRecord1">${bean.checkRecord }</textarea>
							<input type="hidden" name="checkRecord" id="checkRecord" value="${bean.checkRecord }">
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right">住户意见：</td>
						<td class="head_form1" colspan="3">
							&nbsp;&nbsp;&nbsp;
							<input type="radio" name="clientNotion" <c:if test="${bean.clientNotion eq '非常满意' }">checked</c:if> value="非常满意">非常满意
							&nbsp;&nbsp;&nbsp;
							<input type="radio" name="clientNotion" <c:if test="${bean.clientNotion eq '满意' }">checked</c:if> value="满意">满意
							&nbsp;&nbsp;&nbsp;
							<input type="radio" name="clientNotion" <c:if test="${bean.clientNotion eq '不满意' }">checked</c:if> value="不满意">不满意
						</td>
					</tr>
     				
					<tr>
     					<td align="center" colspan="4" class="head_left"><input type="button" value="提交" class="button" onclick="finish()"><input type="button" value="返回" class="button" onclick="return1()"></td>
     				</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
<script type="text/javascript">
	sel(document.getElementById("select11"));
</script>
</html>
