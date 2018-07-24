<%@ page language="java"   pageEncoding="UTF-8"%>
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
	<title>合同退租</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	<base href="<%=basePath%>" target="_parent">
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		function save(str){
			var flag = "通过审批";
			if(str=="0"){
				flag = "未通过审批";
			}
			if(window.confirm("确定"+flag+"吗?")){
				document.form1.action = "<%=path%>/compact.do?method=applyQuit&flag="+str;
				document.form1.submit();
			}
		}
	</script>
</head>
<body>
<form name="form1" method="post">
	<table width="99%" height = "96%" align="center" border="0" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5">
    			<input type="hidden" name="compactId" id="compactId" value="${objs[1].id }">
    			<input type="hidden" name="id" id="id" value="${objs[0].id }">
    		</td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line" onclick="jiben()">合同退租</td>
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
     					<th align="center" colspan="4" class="head_one">退租申请表</th>
     				</tr>
     				<tr>
						<td class="head_left" align="right" width="10%">编号：</td>
						<td class="head_form1" width="40%"><input type="text" name="quitCode" id="quitCode" disabled="disabled" value="${objs[0].quitCode }" readonly="readonly"></td>
						<td class="head_form1" align="right" width="10%">申请日期：</td>
						<td class="head_form1"><input type="text" name="applayDate" id="applayDate" disabled="disabled" value="${objs[0].applayDate }" readonly onclick="WdatePicker();" class="Wdate"></td>
					</tr>
					<tr>
						<td class="head_left" align="right">新合同编号：</td>
						<td class="head_form1" colspan="3"><input type="text" name="compactCode" disabled="disabled" readonly="readonly" value="${objs[1].code }"></td>
					</tr>
					<tr>
						<td class="head_left" align="right">客户名称：</td>
						<td class="head_form1"><input type="text" name="clientName" id="clientName" disabled="disabled" readonly="readonly" value="${objs[1].name }"></td>
						<td class="head_form1" align="right" width="10%">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
					</tr>
					<tr>
						<td class="head_left" align="right">联系电话：</td>
						<td class="head_form1"><input type="text" name="phone" id="phone" disabled="disabled" readonly="readonly" value="${objs[1].phone }"></td>
						<td class="head_form1" align="right" width="10%">租赁单元：</td>
						<td class="head_form1"><input type="text" name="roomCodes" id="roomCodes" disabled="disabled" readonly="readonly" value="${objs[1].roomCodes }"></td>
					</tr>
					<tr>
						<td class="head_left" align="right">租赁面积：</td>
						<td class="head_form1" colspan="3"><input type="text" name="area" id="area" disabled="disabled" readonly="readonly" value="${objs[1].totalArea }"></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">租赁开始日期：</td>
						<td class="head_form1" width="40%"><input type="text" name="beginDate" disabled="disabled" value="${objs[1].beginDate }" readonly onclick="WdatePicker();" class="Wdate"></td>
						<td class="head_form1" align="right" width="10%">租赁结束日期：</td>
						<td class="head_form1"><input type="text" name="endDate"  value="${objs[1].endDate }" disabled="disabled" readonly onclick="WdatePicker();" class="Wdate"></td>
					</tr>
					<tr>
						<td class="head_left" align="right">押金：</td>
						<td class="head_form1"><input type="text" name="deposit" disabled="disabled" readonly="readonly" value="${objs[1].deposit }"></td>
						<td class="head_form1" align="right" width="10%">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
					</tr>
					<tr>
						<td class="head_left" align="right">实际退租日期：</td>
						<td class="head_form1" colspan="3"><input type="text" name="quitDate" disabled="disabled" readonly="readonly" value="${objs[0].quitDate }" readonly onclick="WdatePicker();" class="Wdate"></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">退租原因:</td>
						<td class="head_form1" colspan="3"><textarea rows="3" style="width: 80%" disabled="disabled" readonly="readonly" name="quitSeason" id="quitSeason">${objs[0].quitSeason }</textarea></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">租户确认:</td>
						<td class="head_form1" colspan="3"><textarea rows="3" style="width: 80%" disabled="disabled" readonly="readonly" name="validate" id="validate">${objs[0].validate }</textarea></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">备注:</td>
						<td class="head_form1" colspan="3"><textarea rows="3" style="width: 80%" disabled="disabled" readonly="readonly" name="bz" id="bz">${objs[0].bz }</textarea></td>
					</tr>
					<tr>
     					<th align="center" colspan="4" class="head_one">审批栏</th>
     				</tr>
     				<tr>
						<td class="head_left" align="right" width="10%">经办人:</td>
						<td class="head_form1" colspan="3"><textarea rows="3" style="width: 80%" disabled="disabled" readonly="readonly" name="doMan" id="doMan">${objs[0].doMan }</textarea></td>
					</tr>
     				<tr>
						<td class="head_left" align="right" width="10%">招商部意见:</td>
						<td class="head_form1" colspan="3"><textarea rows="3" style="width: 80%" disabled="disabled" readonly="readonly" name="zsbAttitude" id="zsbAttitude">${objs[0].zsbAttitude }</textarea></td>
					</tr>
     				<tr>
						<td class="head_left" align="right" width="10%">物业部意见:</td>
						<td class="head_form1" colspan="3"><textarea rows="3" style="width: 80%" disabled="disabled" readonly="readonly" name="wybAttitude" id="wybAttitude">${objs[0].wybAttitude }</textarea></td>
					</tr>
     				<tr>
						<td class="head_left" align="right" width="10%">财务部意见:</td>
						<td class="head_form1" colspan="3"><textarea rows="3" style="width: 80%" disabled="disabled" readonly="readonly" name="cwbAttitude" id="cwbAttitude">${objs[0].cwbAttitude }</textarea></td>
					</tr>
     				<tr>
						<td class="head_left" align="right" width="10%">总经理意见:</td>
						<td class="head_form1" colspan="3"><textarea rows="3" style="width: 80%" disabled="disabled" readonly="readonly" name="zjlAttitude" id="zjlAttitude">${objs[0].zjlAttitude }</textarea></td>
					</tr>
					<tr>
     					<td align="left" colspan="4" class="head_left"><input type="button" value="通过审批" class="button" onclick="save('1')"><input type="button" value="未通过审批" class="button" onclick="save('0')"></td>
     				</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
<c:if test="${compactManagerForm.flag eq 'true'}">
	<script type="text/javascript">
		opener.location.href("<%=path%>/compact.do?method=getPassList");
		this.close();
	</script>
</c:if>
<c:if test="${flag1}">
	<script type="text/javascript">
		opener.location.href("<%=path%>/compact.do?method=getPassList");
		this.close();
	</script>
</c:if>
</html>
