<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>投诉建议</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		function return1(){
			//window.location.href="complaint.do?method=getList";
			history.back();
		}
	</script>
	
	
</head>
<body>
<form name = "form1" method="post">
	<input type="hidden" name="status" id="status" value="${bean.status }" />
	<table width="99%" height = "96%" align="center" border="0" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"><input type="hidden" name="id" id="id" value="${bean.id }"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">查看投诉建议处理情况</td>
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
     					<th align="left" colspan="4" class="head_one">来电来访记录</th>
     				</tr>
     				<tr>
						<td class="head_rols" align="right">业户名称：</td>
						<td class="fist_rows"><input type="text" disabled="disabled" name="name" id="name" value="${bean.name }"></td>
						<td class="fist_rows" align="right">&nbsp;</td>
						<td class="fist_rows">&nbsp;</td>
					</tr>
					<tr>
						<td class="head_left" align="right">联系电话：</td>
						<td class="head_form1"><input type="text" disabled="disabled" name="phone" id="phone" value="${bean.phone }"></td>
						<td class="head_form1" align="right">投诉类别：</td>
						<td class="head_form1"><select id="type" name="type" disabled="disabled"><option value="电话">电话</option><option value="上门">上门</option></select></td>
					</tr>
					<tr>
						<td class="head_left" align="right">投诉内容：</td>
						<td class="head_form1" colspan="3"><textarea rows="3" style="width: 80%" disabled="disabled" name="complaintContent" id="complaintContent">${bean.complaintContent }</textarea></td>
					</tr>
					<tr>
						<td class="head_left" align="right">投诉时间：</td>
						<td class="head_form1"><input type="text" disabled="disabled" style=" width: 138px"  id="complaintDate" name="complaintDate" value="${bean.complaintDate }" class="Wdate"></td>
						<td class="head_form1" align="right">接听(接待)人：</td>
						<td class="head_form1"><input type="text" disabled="disabled" name="clerk" id="clerk" value="${bean.clerk }"></td>
					</tr>
     				<tr>
     					<th align="left" colspan="4" class="head_one">核实投诉内容</th>
     				</tr>
     				<tr>
						<td class="head_left" align="right">投诉单号：</td>
						<td class="head_form1"><input type="text" disabled="disabled" name="code" id="code" value="${bean.code }"></td>
						<td class="head_form1" align="right">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
						
					</tr>
					<tr>
						<td class="head_left" align="right">核实情况：</td>
						<td class="head_form1" colspan="3"><textarea rows="3" disabled="disabled" name="verifyContent" id="verifyContent" style="width: 80%">${bean.verifyContent }</textarea></td>
					</tr>
					<tr>
						<td class="head_left" align="right">核实结果：</td>
						<td class="head_form1"><input type="hidden" disabled="disabled" name="verifyResultIn" id="verifyResultIn" value="${bean.verifyResult }"><select name="verifyResult" disabled="disabled" id="verifyResult"><option value="有效投诉" selected="selected">有效投诉</option><option value="无效投诉">无效投诉</option></select></td>
						<td class="head_form1" align="right">核实人</td>
						<td class="head_form1"><input type="text" disabled="disabled" name="verifyMan" id="verifyMan" value="${bean.verifyMan }"></td>
					</tr>
					<tr>
						<td class="head_left" align="right">核实时间：</td>
						<td class="head_form1"><input type="text" name="verifyDate" readonly id="verifyDate" value="${bean.verifyDate }" disabled="disabled" class="Wdate"></td>
						<td class="head_form1" align="right">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
					</tr>
					<tr>
     					<th align="left" colspan="4" class="head_one">处理投诉：</th>
     				</tr>
     				<tr>
						<td class="head_left" align="right">处理方法：</td>
						<td class="head_form1" colspan="3"><textarea rows="3" disabled="disabled" name="disposalWay" id="disposalWay" style="width: 80%">${bean.disposalWay }</textarea></td>
					</tr>
					<tr>
						<td class="head_left" align="right">责任人：</td>
						<td class="head_form1"><input type="text" disabled="disabled" name="dutyMan" id="dutyMan" value="${bean.dutyMan }"></td>
						<td class="head_form1" align="right">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
					</tr>
					<tr>
						<td class="head_left" align="right">处理时间：</td>
						<td class="head_form1"><input type="text" readonly id="disposalDate" name="disposalDate" value="${bean.disposalDate }" disabled="disabled" class="Wdate"></td>
						<td class="head_form1" align="right">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
					</tr>
					<tr>
						<td class="head_left" align="right">处理结果：</td>
						<td class="head_form1" colspan="3"><textarea rows="3" disabled="disabled" name="disposalResult" id="disposalResult" style="width: 80%">${bean.disposalResult }</textarea></td>
					</tr>
					<tr>
						<td class="head_left" align="right">业户意见：</td>
						<td class="head_form1" colspan="3"><textarea rows="3" disabled="disabled" name="ownerOpinion" id="ownerOpinion" style="width: 80%">${bean.ownerOpinion }</textarea></td>
					</tr>
					<tr>
						<td class="head_left" align="right">主管领导意见：</td>
						<td class="head_form1" colspan="3"><textarea rows="3" disabled="disabled" name="leadOpinion" id="leadOpinion" style="width: 80%">${bean.leadOpinion }</textarea></td>
					</tr>
					<tr>
						<td class="head_left" align="right">总经理意见：</td>
						<td class="head_form1" colspan="3"><textarea rows="3" disabled="disabled" name="generalOpinion" id="generalOpinion" style="width: 80%">${bean.generalOpinion }</textarea></td>
					</tr>
					<tr>
     					<td align="center" colspan="4" class="head_left"><input type="button" value="返回" class="button" onclick="return1()"></td>
     				</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
