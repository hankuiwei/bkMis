<%@ page language="java"   pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.GlobalMethod"%>
<%@page import="com.zc13.util.Contants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String date = GlobalMethod.getTime();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>意向书详情</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript">
		function return1(){
			this.close();
		}
	</script>
</head>
<body onload="">
<form name = "form1" method="post">
	<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
	<!-- 返回到列表页面需要的参数start -->
	<input type="hidden" name="c_clientName" value="${intentionForm.c_clientName }" />
	<input type="hidden" name="c_roomCode" value="${intentionForm.c_roomCode }" />
	<input type="hidden" name="c_status" value="${intentionForm.c_status }" />
	<input type="hidden" name="c_isEarnest" value="${intentionForm.c_isEarnest }" />
	<!-- 返回到列表页面需要的参数end -->
	
	<input type="hidden" name="type" value="<%=Contants.INTENTION %>">
	<input type="hidden" name="id" id="id" value="${intentionForm.compactIntention.id }">
	<table width="99%" height = "96%" align="center" border="0" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5">&nbsp;</td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">意向书详情</td>
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
						<td class="head_form1" width="40%">
							<input type="hidden" id="compactIntention.clientId" name="compactIntention.clientId" value="${intentionForm.compactIntention.clientId }" />
							<input name="compactIntention.name" id="compactIntention.name" disabled="disabled" value="${intentionForm.compactIntention.name }" type="text" style="font-size:12px;width:200px">
						</td>
						<td class="head_form1" align="right">单位全称：</td>
						<td class="head_form1"><input type="text" id="compactIntention.unitName" name="compactIntention.unitName" disabled="disabled"  value="${intentionForm.compactIntention.unitName }"><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font></td>
					</tr>
     				<tr>
						<td class="head_left" align="right" width="10%">客户代码：</td>
						<td class="head_form1">
							<input type="text" name="compactIntention.code"  disabled="disabled"  id="compactIntention.code" value="${intentionForm.compactIntention.code }" /><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font>
						</td>
						<td class="head_form1" align="right" width="10%">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
					</tr>
     				<tr>
						<td class="head_left" align="right" width="10%">联系人：</td>
						<td class="head_form1"><input type="text" name="compactIntention.linkMan" id="compactIntention.linkMan" disabled="disabled"  value="${intentionForm.compactIntention.linkMan }" ></td>
						<td class="head_form1" align="right" width="10%">联系电话：</td>
						<td class="head_form1"><input type="text" name="compactIntention.phone" id="compactIntention.phone" disabled="disabled"  value="${intentionForm.compactIntention.phone }" ></td>
					</tr>
					<tr>
						<td class="head_left" align="right">住户类别：</td>
						<td class="head_form1">
							<span id="clientTypeSpan">
							<select onchange="seltype(this)" disabled="disabled"  name="compactIntention.clientType" id="compactIntention.clientType">
								<option value="单位" <c:if test="${intentionForm.compactIntention.clientType eq '单位'}">selected</c:if> >单位</option>
								<option value="个人" <c:if test="${intentionForm.compactIntention.clientType eq '个人'}">selected</c:if> >个人</option>
							</select>
							</span>
						</td>
						<td class="head_form1" align="right" width="10%">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
					</tr>
					<tr id="tr11" style="display:none">
						<td colspan="4">
							<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
								<tr>
			     					<th align="left" colspan="4" class="head_one">个人信息</th>
			     				</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">国籍：</td>
									<td class="head_form1" width="40%"><input type="text" name="compactIntention.country" id="compactIntention.country" value="${intentionForm.compactIntention.country }"></td>
									<td class="head_form1" align="right" width="10%">&nbsp;</td>
									<td class="head_form1">&nbsp;</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">民族：</td>
									<td class="head_form1" width="40%"><input type="text" name="compactIntention.nation" id="compactIntention.nation" value="${intentionForm.compactIntention.nation }"></td>
									<td class="head_form1" align="right" width="10%">&nbsp;</td>
									<td class="head_form1">&nbsp;</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">籍贯：</td>
									<td class="head_form1" width="40%"><input type="text" name="compactIntention.residence" id="compactIntention.residence" value="${intentionForm.compactIntention.residence }"></td>
									<td class="head_form1" align="right" width="10%">&nbsp;</td>
									<td class="head_form1">&nbsp;</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">身份证号：</td>
									<td class="head_form1" width="40%"><input type="text" name="compactIntention.identityCard" id="compactIntention.identityCard" value="${intentionForm.compactIntention.identityCard }"></td>
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
									<td class="head_left" align="right" width="10%">公司类别：</td>
									<td class="head_form1" width="40%">
									<span id="companyTypeSpan">
									<select name="compactIntention.companyType" disabled="disabled"  id="compactIntention.companyType" style="width: 130">
										<c:choose>
										<c:when test="${empty map.enterpriseType}">
										</c:when>
										<c:otherwise>
										<c:forEach items="${map.enterpriseType}" var="v">
											<option value="${v.codeName }" <c:if test="${intentionForm.compactIntention.companyType eq v.codeName }">selected</c:if> >${v.codeName }</option>
										</c:forEach>
									</c:otherwise>
									</c:choose>
									</select>
									</span>
									</td>
									<td class="head_form1" align="right" width="10%">传真：</td>
									<td class="head_form1" width="40%"><input type="text" name="compactIntention.fax" disabled="disabled"  id="compactIntention.fax" value="${intentionForm.compactIntention.fax }"></td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">所属行业：</td>
									<td class="head_form1" width="40%">
									<span id="tradeSpan">
									<select name="compactIntention.trade" disabled="disabled"  id="compactIntention.trade" style="width: 130">
										<c:choose>
										<c:when test="${empty map.tradeType}">
										</c:when>
										<c:otherwise>
										<c:forEach items="${map.tradeType}" var="v">
											<option value="${v.codeName }" <c:if test="${intentionForm.compactIntention.trade eq v.codeName }">selected</c:if>>${v.codeName }</option>
										</c:forEach>
									</c:otherwise>
									</c:choose>
									</select>
									</span>
									</td>
									<td class="head_form1" align="right" width="10%">资金类别：</td>
									<td class="head_form1">
										<span id="fundTypeSpan">
										<select name="compactIntention.fundType" disabled="disabled"  id="compactIntention.fundType" style="width: 130">
										<c:choose>
										<c:when test="${empty map.fundType}">
										</c:when>
										<c:otherwise>
										<c:forEach items="${map.fundType}" var="v">
											<option value="${v.codeName }" <c:if test="${intentionForm.compactIntention.fundType eq v.codeName }">selected</c:if>>${v.codeName }</option>
										</c:forEach>
										</c:otherwise>
										</c:choose>
										</select>
										</span>
									</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">主营业务：</td>
									<td class="head_form1" width="40%"><input type="text" name="compactIntention.operation" disabled="disabled"  id="compactIntention.operation" value="${intentionForm.compactIntention.operation }"></td>
									<td class="head_form1" align="right" width="10%">法人代表：</td>
									<td class="head_form1"><input type="text" name="compactIntention.corporation" disabled="disabled"  id="compactIntention.corporation" value="${intentionForm.compactIntention.corporation }"></td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">注册资金：</td>
									<td class="head_form1" width="40%"><input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" disabled="disabled"  name="compactIntention.loginFund" id="compactIntention.loginFund" value="${intentionForm.compactIntention.loginFund }"></td>
									<td class="head_form1" align="right" width="10%">成立时间：</td>
									<td class="head_form1"><input type="text" name="compactIntention.loginDate" disabled="disabled"  id="compactIntention.loginDate" readonly onclick="WdatePicker();" value="${intentionForm.compactIntention.loginDate }" class="Wdate"></td>
								</tr>
								<tr>
									<td class="head_left" align="right" width="10%">国税号：</td>
									<td class="head_form1" width="40%"><input type="text" name="compactIntention.taxNo" id="compactIntention.taxNo" disabled="disabled" value="${intentionForm.compactIntention.taxNo }"></td>
									<td class="head_form1" align="right" width="10%">地税号：</td>
									<td class="head_form1"><input type="text" name="compactIntention.rentNo" id="compactIntention.rentNo" disabled="disabled" value="${intentionForm.compactIntention.rentNo }"></td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%" nowrap="nowrap">是否高新技术企业：</td>
									<td class="head_form1" width="40%" colspan="3">
										<span id="isHighTechSpan">
										<select name="compactIntention.isHighTech" disabled="disabled"  id="compactIntention.isHighTech">
											<option value="是" <c:if test="${intentionForm.compactIntention.isHighTech eq '是' }">selected</c:if>>是</option>
											<option value="否" <c:if test="${intentionForm.compactIntention.isHighTech eq '否' }">selected</c:if>>否</option>
										</select>
										</span>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
		     			<th align="left" colspan="4" class="head_one">意向书信息</th>
		     		</tr>
		     		<tr>
						<td class="head_left" align="right" width="10%" >意向书编号：</td>
						<td class="head_form1" width="40%" colspan="3">
							<input type="text" name="compactIntention.intentionCode" disabled="disabled" id="compactIntention.intentionCode" value="${intentionForm.compactIntention.intentionCode }">
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
								<c:when test="${empty intentionForm.roomList}">
									<tr>
								    <td align="center" colspan="5"><font color="red">无房间!</font></td>
								</tr>
								</c:when>
								<c:otherwise>
								<c:forEach items="${intentionForm.roomList}" var="v" varStatus="tag">
								<tr>
									<td nowrap="nowrap" class="RptTableBodyCellLock" align="center">
										<input type="checkbox" name="roomId1" value="${v.roomId }">
										<input type="hidden" name="clientRoomId" value="${v.roomId }">
									</td>
									<td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center">${tag.count }</td>
									<td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.roomCode }<input type="hidden" name="roomCode" value="${v.roomCode }">&nbsp;</td>
								    <td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.roomFullName }<input type="hidden" name="roomFullName" value="${v.roomFullName }">&nbsp;</td>
								    <td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.constructionArea }<input type="hidden" name="area" value="${v.constructionArea }">&nbsp;</td>
								</tr>
								</c:forEach>
								</c:otherwise>
								</c:choose>
							</table>
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">房间号：</td>
						<td class="head_form1" width="40%">
							<input type="text" name="compactIntention.roomCodes" disabled="disabled" id="compactIntention.roomCodes" value="${intentionForm.compactIntention.roomCodes }" readonly="readonly" />
						</td>
						<td class="head_form1" align="right" width="10%">总面积：</td>
						<td class="head_form1">
							<input type="text" name="compactIntention.totalArea" disabled="disabled"  id="compactIntention.totalArea" value="${intentionForm.compactIntention.totalArea }" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">签订日期：</td>
						<td class="head_form1" width="40%" colspan="3"><input type="text" name="compactIntention.signDate"   disabled="disabled"  onclick="WdatePicker();" value="${intentionForm.compactIntention.signDate }" class="Wdate"></td>
						
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">租赁开始日期：</td>
						<td class="head_form1" width="40%"><input type="text" name="compactIntention.beginDate" disabled="disabled"  id="compactIntention.beginDate" value="${intentionForm.compactIntention.beginDate }"  readonly onclick="WdatePicker();" class="Wdate"></td>
						<td class="head_form1" align="right" width="10%">租赁结束日期：</td>
						<td class="head_form1"><input type="text" name="compactIntention.endDate"  disabled="disabled"  id="compactIntention.endDate" value="${intentionForm.compactIntention.endDate }"  readonly onclick="WdatePicker();" class="Wdate"></td>
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
								</tr>
								<c:choose>
								<c:when test="${empty intentionForm.rentList}">
								</c:when>
								<c:otherwise>
								<c:forEach items="${intentionForm.rentList}" var="v" varStatus="tag">
								<tr>
									<td nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count }</td>
									<td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center"><input type="text" name="beginDateCost" value="${v.beginDate }"  disabled="disabled"  onclick="WdatePicker();" class="Wdate"></td>
									<td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center"><input type="text" id="end1${tag.count }" name="endDateCost" disabled="disabled"  value="${v.endDate }"  onclick="WdatePicker();" class="Wdate"></td>
								    <td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center"><input type="text" name="rent" disabled="disabled"  value="${v.rent }"></td>
								</tr>
								</c:forEach>
								</c:otherwise>
								</c:choose>
							</table>
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">定金：</td>
						<td class="head_form1" width="40%"><input type="text" name="compactIntention.earnest" id="compactIntention.earnest" disabled="disabled"  value="${intentionForm.compactIntention.earnest }"></td>
						<td class="head_left" align="right" width="10%">装修押金：</td>
						<td class="head_form1" width="40%"><input type="text" name="compactIntention.decorationDeposit" id="compactIntention.decorationDeposit" disabled="disabled"  value="${intentionForm.compactIntention.decorationDeposit }"></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">房租押金：</td>
						<td class="head_form1" width="40%"><input type="text" name="compactIntention.rentDeposit" id="compactIntention.rentDeposit" disabled="disabled"  value="${intentionForm.compactIntention.rentDeposit }"></td>
						<td class="head_form1" align="right" width="10%">预收款周期：</td>
						<td class="head_form1" width="40%">
						<input type="text" name="compactIntention.circle" id="compactIntention.circle" value="${intentionForm.compactIntention.circle }" size="5" disabled="disabled"  onchange="mustNaN(value)">&nbsp;&nbsp;月</td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">经办人：</td>
						<td class="head_form1" width="40%"><input type="text" name="compactIntention.man" id="compactIntention.man" disabled="disabled"  value="${intentionForm.compactIntention.man }"></td>
						<td class="head_form1" align="right" width="10%">经办日期：</td>
						<td class="head_form1"><input type="text" name="compactIntention.date" readonly onclick="WdatePicker();" disabled="disabled"  value="${intentionForm.compactIntention.date }" class="Wdate"></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">附加说明：</td>
						<td class="head_form1" width="40%" colspan="3"><textarea rows="3" style="width: 80%" name="compactIntention.instruction" id="compactIntention.instruction">${intentionForm.compactIntention.instruction }</textarea></td>
					</tr>
					<tr>
     					<th align="left" colspan="4" class="head_one">收费信息</th>
     				</tr>
     				<tr>
						<td class="head_left" align="right" colspan="4">
							<table width="60%" border="0" cellpadding="0" cellspacing="0" class = "RptTable" id="tb1">
							<tr>
								<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">&nbsp;</td>
								<td width="5%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">费用类型</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">收费标准</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">开始日期</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">结束日期</td>
								<td width="15%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">数量</td>
							</tr>
							<c:choose>
							<c:when test="${empty intentionForm.standardList}">
							<tr>
								    <td align="center" colspan="5"><font color="red">无收费标准!</font></td>
							</tr>
							</c:when>
							<c:otherwise>
							<c:forEach items="${intentionForm.standardList}" var="v" varStatus="tag">
							<tr>
								<td nowrap="nowrap" class="RptTableBodyCellLock" align="center"><input type="checkbox" name="check3"></td>
								<td nowrap="nowrap" class="RptTableBodyCell" align="center">${tag.count }</td>
								<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.ERooms.roomCode }<input type="hidden" name="roomId" value="${v.ERooms.roomId }">&nbsp;</td>
								<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.CCosttype.costTypeName }<input type="hidden" name="costTypeId" value="${v.CCosttype.id}">&nbsp;</td>
								<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.CCoststandard.standardName }<input type="hidden" name="costStandartId" value="${v.CCoststandard.id }">&nbsp;</td>
								<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.beginDate }<input type="hidden" name="beginDateStand" value="${v.beginDate }">&nbsp;</td>
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
	if("${intentionForm.compactIntention.clientType}"=="单位"){
		document.getElementById("tr11").style.display="none";
		document.getElementById("tr12").style.display="block";
	}else{
		document.getElementById("tr11").style.display="block";
		document.getElementById("tr12").style.display="none";
	}
	
</script>
</html>
