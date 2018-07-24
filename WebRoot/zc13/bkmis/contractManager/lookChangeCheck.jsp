<%@ page language="java"   pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	String message = GlobalMethod.NullToSpace((String)request.getAttribute("message"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>变更合同校验</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	<base href="<%=basePath%>" target="_parent">
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript">
		function apply(){
			var compactId = document.getElementById("compactId").value;
			var changeId = document.getElementById("changeId").value;
			if(window.confirm("确定提交审批吗")){
				window.location.href="customer.do?method=applyCompact1&forword=lookChangeCheck&flag=change&id="+changeId+"&id1="+compactId;
				window.opener.location = window.opener.location;
				window.close();
			}
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
		function getCode(){
			var tb = document.getElementById("tb");
			var trs = tb.getElementsByTagName("tr");
			var codes = document.getElementById("roomCodes");
			codes.value = "";
			for(var i=2;i<trs.length;i++){
				codes.value += trs[i].getElementsByTagName("td")[2].innerText + ";";
			}
		}
		
		//取消提交
		function cancel(){
			if(window.confirm("您确定要取消提交吗？")){
				var id = "${compactRoomForm.compact.id}";
				document.form1.action = "<%=path %>/compact.do?method=cancelChecked&id="+id+"&forward=toLookChangeCheck";
				document.form1.submit();
				window.opener.location = window.opener.location;
				window.close();
			}
		}
	</script>
	<script type="text/javascript">
	<%if(!message.equals("")){%>
		alert("<%=message%>");
	<%}%>
	</script>
	
</head>
<body>
<form name = "form1" method="post">
	<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
	<table width="99%" height = "96%" align="center" border="0" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5">
    			<input type="hidden" id="compactId" value="${compactRoomForm.compact.id }">
    			<input type="hidden" id="clientId" value="${compactRoomForm.client.id }">
    			<input type="hidden" id="changeId" value="${id1 }">
    		</td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line" onclick="jiben()">变更合同校验</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr height = "95%"><!-- 这个td负责画正式内容的左、右、下方的边框 -->
    		<td class="menu_tab2">
     			<table width="95%" border="0" align="center" cellspacing="0" cellpadding="0">
     				<tr>
     					<th align="left" colspan="4" class="head_one">客户基本信息</th>
     				</tr>
					<tr>
						<td class="head_left" align="right" width="10%">客户简称：</td>
						<td class="head_form1" width="40%"><select name="name1" disabled="disabled" id="name1" onchange="selectClient(this)" style="width:220;position:absolute;clip:rect(2 100% 90% 201)">
								<c:choose>
								<c:when test="${empty list}">
								</c:when>
								<c:otherwise>
									<c:forEach items="${list}" var="v">
										<option value="${v.id }">${v.name }</option>
									</c:forEach>
								</c:otherwise>
								</c:choose>
							</select><input name="name" id="name" type="text" disabled="disabled" value="${compactRoomForm.client.name }" size="32" style="font-size:12px;"></td>
						<td class="head_form1" align="right">单位全称：</td>
						<td class="head_form1"><input type="text" id="unitName" disabled="disabled" name="unitName" value="${compactRoomForm.client.unitName }"></td>
					</tr>
     				<tr>
						<td class="head_left" align="right" width="10%">联系人：</td>
						<td class="head_form1"><input type="text" name="linkMan" disabled="disabled" id="linkMan" value="${compactRoomForm.client.linkMan }"></td>
						<td class="head_form1" align="right" width="10%">联系电话：</td>
						<td class="head_form1"><input type="text" name="phone" disabled="disabled" id="phone" value="${compactRoomForm.client.phone }"></td>
					</tr>
					<tr>
						<td class="head_left" align="right">住户类别：</td>
						<td class="head_form1"><select onchange="seltype(this)" name="clientType" id="clientType" disabled="disabled"><option value="单位" selected="selected">单位</option><option value="个人">个人</option></select></td>
						<td class="head_form1" align="right" width="10%">&nbsp;<input type="hidden" disabled="disabled" id="clientTypeIn" value="${compactRoomForm.client.clientType }"></td>
						<td class="head_form1">&nbsp;</td>
					</tr>
					<tr id="tr11" style="display:none ">
						<td colspan="4">
							<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
								<tr>
			     					<th align="left" colspan="4" class="head_one">个人信息</th>
			     				</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">国籍：</td>
									<td class="head_form1" width="40%"><input type="text" name="country" disabled="disabled" id="country" value="${compactRoomForm.compact.country }"></td>
									<td class="head_form1" align="right" width="10%">&nbsp;</td>
									<td class="head_form1">&nbsp;</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">民族：</td>
									<td class="head_form1" width="40%"><input type="text" disabled="disabled" name="nation" id="nation" value="${compactRoomForm.compact.nation }"></td>
									<td class="head_form1" align="right" width="10%">&nbsp;</td>
									<td class="head_form1">&nbsp;</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">籍贯：</td>
									<td class="head_form1" width="40%"><input type="text" disabled="disabled" name="residence" id="residence" value="${compactRoomForm.compact.residence }"></td>
									<td class="head_form1" align="right" width="10%">&nbsp;</td>
									<td class="head_form1">&nbsp;</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">身份证号：</td>
									<td class="head_form1" width="40%"><input type="text" disabled="disabled" name="identityCard" id="identityCard" value="${compactRoomForm.compact.identityCard }"></td>
									<td class="head_form1" align="right" width="10%">&nbsp;</td>
									<td class="head_form1">&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr id="tr12">
						<td colspan="4">
							<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
							<tr>
		     					<th align="left" colspan="4" class="head_one">公司信息</th>
		     				</tr>
		     				<tr>
								<td class="head_left" align="right" width="10%" nowrap="nowrap">公司类别：</td>
								<td class="head_form1" width="40%"><input type="hidden" disabled="disabled" name="companyTypeIn" id="companyTypeIn" value="${compactRoomForm.compact.companyType }">
									<select name="companyType" id="companyType" disabled="disabled" style="width: 130">
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
								<td class="head_form1" align="right" width="10%" nowrap="nowrap">传真：</td>
								<td class="head_form1" width="40%"><input type="text" disabled="disabled" name="fax" id="fax" value="${compactRoomForm.compact.fax }"></td>
							</tr>
		     				<tr>
								<td class="head_left" align="right" width="10%" nowrap="nowrap">所属行业：</td>
								<td class="head_form1" width="40%"><input type="hidden" disabled="disabled" name="tradeIn" id="tradeIn" value="${compactRoomForm.compact.trade }">
								<select name="trade" id="trade" style="width: 130" disabled="disabled">
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
								<td class="head_form1" align="right" width="10%" nowrap="nowrap">资金类别：</td>
								<td class="head_form1"><input type="hidden" disabled="disabled" name="fundTypeIn" id="fundTypeIn" value="${compactRoomForm.compact.fundType }">
								<select name="fundType" id="fundType" disabled="disabled" style="width: 130">
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
								<td class="head_left" align="right" width="10%" nowrap="nowrap">主营业务：</td>
								<td class="head_form1" width="40%"><input type="text" disabled="disabled" name="operation" id="operation" value="${compactRoomForm.compact.operation }"></td>
								<td class="head_form1" align="right" width="10%" nowrap="nowrap">法人代表：</td>
								<td class="head_form1"><input type="text" name="corporation" disabled="disabled" id="corporation" value="${compactRoomForm.compact.corporation }"></td>
							</tr>
		     				<tr>
								<td class="head_left" align="right" width="10%" nowrap="nowrap">注册资金：</td>
								<td class="head_form1" width="40%"><input type="text" disabled="disabled" name="loginFund" id="loginFund" value="${compactRoomForm.compact.loginFund }"></td>
								<td class="head_form1" align="right" width="10%" nowrap="nowrap">成立时间：</td>
								<td class="head_form1"><input type="text" name="loginDate" disabled="disabled" id="loginDate" value="${compactRoomForm.compact.loginDate }" readonly onclick="WdatePicker();" class="Wdate"></td>
							</tr>
							<tr>
								<td class="head_left" align="right" width="10%">国税号：</td>
								<td class="head_form1" width="40%"><input type="text" name="taxNo" disabled="disabled" id="taxNo" value="${compactRoomForm.compact.taxNo }"></td>
								<td class="head_form1" align="right" width="10%">地税号：</td>
								<td class="head_form1"><input type="text" name="rentNo" id="rentNo" disabled="disabled" value="${compactRoomForm.compact.rentNo }"></td>
							</tr>
			     			<tr>
								<td class="head_left" align="right" width="10%" nowrap="nowrap">是否高新技术企业：<input type="hidden" id="isHighTechIn" value="${compactRoomForm.compact.isHighTech }"></td>
								<td class="head_form1" width="40%" colspan="3"><select name="isHighTech" disabled="disabled" id="isHighTech"><option value="是" selected="selected">是</option><option value="否">否</option></select></td>
							</tr>
							</table>
						</td>
					</tr>
					<tr>
		     			<th align="left" colspan="4" class="head_one">合同信息</th>
		     		</tr>
		     		<tr>
						<td class="head_left" align="right" width="10%" >合同编号：</td>
						<td class="head_form1" width="40%"><input type="text" disabled="disabled" readonly="readonly" name="compactCode" id="compactCode" value="${compactRoomForm.compact.code }"></td>
						<td class="head_form1" align="right" width="10%">合同类型：
						<input type="hidden" id="typeIn" value="${compactRoomForm.compact.type }"></td>
						<td class="head_form1">
						<input type="hidden" value="<%=Contants.COMPACTCHANGE %>" id="type" name="type">
						<select onchange="seltype(this)" disabled="disabled" id="type" name="type">
							<option value="<%=Contants.COMPACTCHANGE %>"><%=Contants.COMPACTCHANGE %></option>
						</select>
						</td>
					</tr>
					<tr>
     					<th align="left" colspan="4" class="head_one">入住房间</th>
     				</tr>
					<tr id="tr1">
						<td class="head_left" colspan="4">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class = "RptTable" id="tb">
								<tr>
									<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">&nbsp;</td>
									<td width="22%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</td>
									<td width="22%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</td>
								    <td width="22%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间名称</td>
								    <td width="22%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间面积</td>
								</tr>
								<c:choose>
								<c:when test="${empty compactRoomForm.eroomList}">
								</c:when>
								<c:otherwise>
								<c:forEach items="${compactRoomForm.eroomList}" var="v" varStatus="tag">
								<tr>
									<td nowrap="nowrap" class="RptTableBodyCellLock" align="center"><input type="checkbox" name="roomId1" disabled="disabled" value="${v.roomId }"><input type="hidden" name="clientRoomId" value="${v.roomId }"></td>
									<td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center">${tag.count }</td>
									<td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.roomCode }<input type="hidden" name="roomCode" disabled="disabled" value="${v.roomCode }">&nbsp;</td>
								    <td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.roomFullName }<input type="hidden" name="roomFullName" disabled="disabled" value="${v.roomFullName }">&nbsp;</td>
								    <td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.constructionArea }<input type="hidden" name="area" disabled="disabled" value="${v.constructionArea }">&nbsp;</td>
								</tr>
								</c:forEach>
								</c:otherwise>
								</c:choose>
							</table>
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%" nowrap="nowrap">房间号：</td>
						<td class="head_form1" width="40%"><input type="text" disabled="disabled" name="roomCodes" id="roomCodes" readonly="readonly" value="${compactRoomForm.compact.roomCodes }"></td>
						<td class="head_form1" align="right" width="10%" nowrap="nowrap">总面积：</td>
						<td class="head_form1"><input type="text" name="totalArea" disabled="disabled" id="totalArea" readonly="readonly" value="${compactRoomForm.compact.totalArea }"></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%" nowrap="nowrap">签订日期：</td>
						<td class="head_form1" width="40%" colspan="3"><input type="text" disabled="disabled" name="signDate" readonly onclick="WdatePicker();" value="${compactRoomForm.compact.signDate }" class="Wdate"></td>
						
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%" nowrap="nowrap">租赁开始日期：</td>
						<td class="head_form1" width="40%"><input type="text" name="beginDate" disabled="disabled"  value="${compactRoomForm.compact.beginDate }" readonly onclick="WdatePicker();" class="Wdate"></td>
						<td class="head_form1" align="right" width="10%" nowrap="nowrap">租赁结束日期：</td>
						<td class="head_form1"><input type="text" name="endDate" disabled="disabled" value="${compactRoomForm.compact.endDate }" readonly onclick="WdatePicker();" class="Wdate"></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">租金自定义：</td>
						<td class="head_form1" colspan="3">
							<table width="100%" id="cost" cellpadding="0" cellspacing="0">
								<tr>
									<td class="head_form" align="center" width="10%">序号</td>
									<td class="head_form" align="center" width="30%">开始日期</td>
									<td class="head_form" align="center" width="30%">结束日期</td>
									<td class="head_form" align="center" width="30%">租金单价(元)</td>
									<td class="head_form" align="center" width="10%">&nbsp;</td>
								</tr>
								<c:choose>
								<c:when test="${empty compactRoomForm.rents}">
								</c:when>
								<c:otherwise>
								<c:forEach items="${compactRoomForm.rents}" var="v" varStatus="tag">
								<tr>
									<td nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count }</td>
									<td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center"><input type="text" name="beginDateCost" value="${v.beginDate }" disabled="disabled" readonly onclick="WdatePicker();" class="Wdate"></td>
									<td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center"><input type="text" name="endDateCost" value="${v.endDate }" disabled="disabled" readonly onclick="WdatePicker();" class="Wdate"></td>
								    <td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center"><input type="text" name="rent" value="${v.rent }" disabled="disabled"></td>
								    <td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;</td>
								</tr>
								</c:forEach>
								</c:otherwise>
								</c:choose>
							</table>
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">定金：</td>
						<td class="head_form1" width="40%"><input type="text" name="earnest" id="earnest" disabled="disabled" value="${compactRoomForm.compact.earnest }"><font color="red" size="3">&nbsp;&nbsp;&nbsp;</font></td>
						<td class="head_left" align="right" width="10%">装修押金：</td>
						<td class="head_form1" width="40%"><input type="text" name="decorationDeposit" id="decorationDeposit" disabled="disabled" value="${compactRoomForm.compact.decorationDeposit }"></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">房租押金：</td>
						<td class="head_form1" width="40%"><input type="text" name="rentDeposit" id="rentDeposit" disabled="disabled" value="${compactRoomForm.compact.rentDeposit }"><font color="red" size="3">&nbsp;&nbsp;&nbsp;</font></td>
						<td class="head_form1" align="right" width="10%">预收款周期：</td>
						<td class="head_form1" width="40%">
						<input type="text" name="circle" id="circle" disabled="disabled" size="5" value="${compactRoomForm.compact.circle }" onchange="mustNaN(value)">&nbsp;&nbsp;月</td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">经办人：</td>
						<td class="head_form1" width="40%"><input type="text" name="man" disabled="disabled" id="man" value="${compactRoomForm.compact.man }"></td>
						<td class="head_form1" align="right" width="10%">经办日期：</td>
						<td class="head_form1"><input type="text" name="date" disabled="disabled" value="${compactRoomForm.compact.date }" readonly onclick="WdatePicker();" class="Wdate"></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">合同附加说明：</td>
						<td class="head_form1" width="40%" colspan="3"><textarea rows="3" style="width: 80%" name="instruction" id="instruction">${compactRoomForm.compact.instruction }</textarea></td>
					</tr>
					<tr>
     					<th align="left" colspan="4" class="head_one">收费信息</th>
     				</tr>
     				<tr>
						<td class="head_left" align="right" colspan="4">
							<table width="60%" border="0" cellpadding="0" cellspacing="0" class = "RptTable" id="tb1">
							<tr>
								<td width="5%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">费用类型</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">收费标准</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">开始日期</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">结束日期</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">数量</td>
							</tr>
							<c:choose>
								<c:when test="${empty compactRoomForm.standardList}">
								</c:when>
								<c:otherwise>
								<c:forEach items="${compactRoomForm.standardList}" var="v" varStatus="tag">
								<tr>
									<td nowrap="nowrap" class="RptTableBodyCell" align="center">${tag.count }</td>
									<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.ERooms.roomCode }<input type="hidden" disabled="disabled" name="roomId" value="${v.ERooms.roomId }">&nbsp;</td>
								    <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.CCosttype.costTypeName }<input type="hidden" disabled="disabled" name="costTypeId" value="${v.CCosttype.id}">&nbsp;</td>
								    <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.CCoststandard.standardName }<input type="hidden" disabled="disabled" name="costStandartId" value="${v.CCoststandard.id }">&nbsp;</td>
								    <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.beginDate }<input type="hidden" name="beginDateStand" disabled="disabled" value="${v.beginDate }">&nbsp;</td>
								    <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.endDate }<input type="hidden" name="endDateStand" value="${v.beginDate }">&nbsp;</td>
									<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.amount }<input type="hidden" name="amount" value="${v.amount }">&nbsp;</td>
								</tr>
								</c:forEach>
								</c:otherwise>
								</c:choose>
							</table>
						</td>
					</tr>
					<tr>
     					<td align="center" colspan="4" class="head_left">
     						<c:set var="notsubmit" value="<%=Contants.NOTSUBMIT %>"></c:set>
     						<c:set var="onapproval" value="<%=Contants.ONAPPROVAL %>"></c:set>
     						<c:if test="${notsubmit eq compactRoomForm.compact.status}">
     						<input type="button" value="提交审批" class="button" onclick="apply()">
     						</c:if>
     						<c:if test="${onapproval eq compactRoomForm.compact.status}">
     						<input type="button" value="取消审批" class="button" onclick="cancel()">
     						</c:if>
     						<input type="button" value="返回" class="button" onclick="return1()">
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
		//document.getElementById("type").value = document.getElementById("typeIn").value;
		document.getElementById("companyType").value = document.getElementById("companyTypeIn").value;
		document.getElementById("trade").value = document.getElementById("tradeIn").value;
		document.getElementById("fundType").value = document.getElementById("fundTypeIn").value;
		document.getElementById("isHighTech").value = document.getElementById("isHighTechIn").value;
		seltype();
		getCode();
	</script>
	<c:if test="${flag1}">
		<script type="text/javascript">
			var type = decodeURI(decodeURI("<%=Contants.COMPACTCHANGE %>"));
			opener.location.href("<%=path%>/compact.do?method=getCheckList&option=decodeURI&type="+type);
			this.close();
		</script>
	</c:if>
</html>
