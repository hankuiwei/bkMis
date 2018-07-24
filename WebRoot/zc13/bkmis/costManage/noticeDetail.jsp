<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ page import="com.zc13.util.*"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String lp = java.net.URLDecoder.decode(GlobalMethod.NullToParam(request.getParameter("lp"),"默认"),"utf-8");
	String fylx = java.net.URLDecoder.decode(GlobalMethod.NullToParam(request.getParameter("fylx"),"默认"),"utf-8");
	String year = GlobalMethod.NullToParam(request.getParameter("year"),"2010");
	String month = GlobalMethod.NullToParam(request.getParameter("month"),"11");
	int i_month = 0;
	try{
		i_month = Integer.parseInt(month);
		if(i_month<10&&i_month!=0){
			month = "0"+month;
		}
	}catch(Exception e){}
	//获取当月第一天
	String firstDay = year+"-"+month+"-01";
	//获取当月最后一天
	Calendar c = Calendar.getInstance();
	c.set(Integer.parseInt(year),i_month-1,1);
	//c.set(Calendar.DATE, 1);
    c.roll(Calendar.DATE, -1);
	Date endTime = c.getTime();
    String endDay = dateFormat.format(endTime);
	
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>计费参数类型</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <!-- 使用日期控件时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<!-- 使用单元格在线编辑功能时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<style type="text/css">
	<!--
	.Rpt1{
		width: 100%;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 10pt;
		border-top:4px #266898 double;
		border-left:1px #b2c2c9 solid;
	}
	-->
	</style>
	<c:if test="${!empty alertMessage}">
	<script type="text/javascript">
		alert("${alertMessage}");
	</script>
	</c:if>
	<script type="text/javascript">
		var tdObj;//保存上次操作的td对象
		var v_id;//保存上次操作的input对象的id的值
		function query(){
			
		}
		function editTd(obj,str){
			try{
				tdObj.innerHTML=document.getElementById(v_id).value+"&nbsp;";
			}catch(e){}
			var row_index = obj.parentElement.rowIndex;//当前td所在的tr是第几行
			var td_text = obj.innerHTML;//得到td文本域的值
			v_id = str+row_index;
			if(str=="starttime"||str=="endtime"){
				obj.innerHTML="<input type='text' name='"+str+"' id='"+v_id+"' value='"+td_text+"' style='width:83px' onclick='WdatePicker()' readonly />";
				document.getElementById(v_id).click();
			}else{
				obj.innerHTML="<input type='text' name='"+str+"' id='"+v_id+"' value='"+td_text+"' style='width:83px' />";
			}
			
			document.getElementById(v_id).focus();
			tdObj = obj;
		}
		
		function cancelEdit(){
			try{
				tdObj.innerHTML=document.getElementById(v_id).value;
			}catch(e){}
		}
		
		function checkAll(obj){
			var arrrad1 = document.getElementsByName("checkbox");	
			if(obj.checked){
				for(var i=0;i<arrrad1.length;i++){
					arrrad1[i].checked="checked";
				}
			}else{
				for(var i=0;i<arrrad1.length;i++){
					arrrad1[i].checked="";
				}
			}		
		}
		function checkAll2(obj){
			var arrrad1 = document.getElementsByName("checkbox2");	
			if(obj.checked){
				for(var i=0;i<arrrad1.length;i++){
					arrrad1[i].checked="checked";
				}
			}else{
				for(var i=0;i<arrrad1.length;i++){
					arrrad1[i].checked="";
				}
			}		
		}
		
		/*确定*/
		function qd(){
			var returnArray = new Object();
			returnArray["jsbh_checked"] = document.all.js_checked.value;
			window.returnValue = returnArray;
			window.close();	
		}
		/*关闭*/
		function gb(){
			window.close();
		}
		function print(clientId,date,noticeType){
			var where = "&checkbox="+clientId+","+date+","+noticeType;
			var URL = "<%=path%>/notice.do?method=print"+where;
			window.open(URL);
			window.close();
		}
	</script>
</head>
	<body>
		<form method="post" name="roomform">
			<table width="99%" height="100%" border="0" align="center"
				cellpadding="0" cellspacing="0" style="layout: fixed">
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">通知单明细打印<br></td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr height="100%">
					<td class="menu_tab2" align="center" valign="top">
						<table width="100%" height="100%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td align="center">
									<!-- 查询条件start -->
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
									  	<tr>
							            	<td height="10" colspan="6" align="center">
							            		<h3>标题：北控宏创物业服务收费通知单</h3>
							            	</td>
							         	</tr>
							          	 <tr>
							            	<td height="10" colspan="6"></td>
							         	</tr>
							        </table>
								</td>
							</tr>
							<tr>
								<td><table  width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td align="right">客户：</td><td>${notice.clientName }</td>
										<td align="right">总金额：</td><td><script>document.write(formatNum(parseFloat(${amount }).toFixed(2).toString()));</script></td>
										<td align="right">发出日期：</td><td>${notice.noticeDate }</td>
									</tr>
									<!--<tr>
										<td>账单开始日期：</td><td><input type="text" readonly  value="2010-11-10"></td>
										<td>账单结束日期：</td><td><input type="text" readonly  value="2010-11-30"></td>
									</tr>
									<tr>
										<td>最后缴款日：</td><td colspan="3"><input type="text" readonly  value="${notice.closeDate }"></td>
									</tr>
									--><tr>
										<td colspan="6" align="center">&nbsp;</td>
									</tr>
									<tr>
										<td colspan="6" align="center">费用明细</td>
									</tr>
								</table></td>
							</tr>
							<tr height="100%">
								<td valign="top">
									<!-- 表单内容区域 -->
									<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed">
										<tr height="95%">
											<td width="100%">
												<div id="div1" class="RptDiv">
													<table border="0" cellpadding="0" cellspacing="0" class="RptTable">
														<tr>
														    <th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
														    <th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">项目</th>
								                			<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">账单月份</th>
								                			<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">关帐日期</th>
								                			<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</th>
								                			<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">金额</th>
														</tr>
														<c:forEach items="${noticeList}" varStatus="vs" var="v">
															<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
																<td class="RptTableBodyCellLock"  align="center">${vs.count }</td>
																<td class="RptTableBodyCell">&nbsp;${v.itemName }</td>
																<td class="RptTableBodyCell">&nbsp;${v.billDate }</td>
																<td class="RptTableBodyCell">&nbsp;${v.closeDate }</td>
																<td class="RptTableBodyCell">&nbsp;${v.roomCode }</td>
																<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v.amount }).toFixed(2).toString()))</script></td>
															</tr>
														</c:forEach>
													</table>
												</div>
												
											</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										
									</table>
								</td>
							</tr>
							<tr>
								<td align="center"><input type="button" class="button" value="打印" onclick="print('${notice.clientId }','${notice.noticeDate }','${notice.noticeType }')"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
<script language=javascript>
</script>
</html>
