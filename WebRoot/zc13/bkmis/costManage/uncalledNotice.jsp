<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ page import="com.zc13.util.*;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String lp = java.net.URLDecoder.decode(GlobalMethod.NullToParam(request.getParameter("lp"),"默认"),"utf-8");
	String fylx = java.net.URLDecoder.decode(GlobalMethod.NullToParam(request.getParameter("fylx"),"默认"),"utf-8");
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
		function query(){
			document.actionForm.action="<%=path%>/zc13/bkmis/costManage/writeMeter2.jsp";
			document.actionForm.submit();
		}
		function add(){
			//var srcpath = "/zc13/bkmis/costManage/addNotice.jsp";
			var srcpath = "/notice.do?method=getNoticePop";
			var fileName = encodeURI(encodeURI("<%=path%>/zc13/bkmis/costManage/showmd.jsp?mk=添加通知单&srcpath="+srcpath));
			var return_value = showModalDialog(fileName,"","dialogWidth:600px;dialogHeight:600px;status:no;help:no;");	
			if(typeof(return_value)!="undefined"){
				if(return_value=='save'){
					cx();
				}
			}
		}
		function showDetail(client,date,noticeType){
			var srcpath = "/notice.do?method=getNotice@id="+client+"@noticeDate="+date+"@noticeType="+noticeType;
			var fileName = encodeURI(encodeURI("<%=path%>/zc13/bkmis/costManage/showmd.jsp?mk=通知单明细&srcpath="+srcpath));
			var return_value = showModalDialog(fileName,"","dialogWidth:600px;dialogHeight:600px;status:no;help:no;");	
		}
		function show1(){
			var checkbox = document.getElementsByName("checkbox");
			var count = 0;
			var client = "";
			var date = "";
			var noticeType = "";
			for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					count++;
					var str = checkbox[i].value.split(",");
					client = str[0];
					date = str[1];
					noticeType = str[2];
				}
			}
			if(count==0){
				alert("请选择要查看的记录！");
			}else if(count==1){
				showDetail(client,date,noticeType);
			}else{
				alert("请选择一项！");
			}
		}
		function del(){
			var isCheck = false;
			var checkbox = document.getElementsByName("checkbox");
			for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					isCheck = true;
				}
			}
			if(!isCheck){
				alert("请选择要删除的项！");
				return false;
			}
			document.actionForm.action="<%=path%>/notice.do?method=delete";
			document.actionForm.submit();
		}
		function cx(){
			document.actionForm.action="<%=path%>/notice.do?method=getList";
			document.actionForm.submit();
		}
		function exportExcel(size){
			if(size=='0'){
				alert("没有记录可以导出！");
			}else{
				document.actionForm.action="<%=path%>/notice.do?method=exportExcel";
				document.actionForm.submit();
			}
		}
		function printAll(){
			var URL = "<%=path%>/notice.do?method=printAll";
			window.open(URL);
		}
		function print1(){
			var where = "";
			var isCheck = false;
			var checkbox = document.getElementsByName("checkbox");
			for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					where += "&checkbox="+checkbox[i].value;
					isCheck = true;
				}
			}
			if(!isCheck){
				alert("请选择要打印的项！");
				return false;
			}
			var URL = "<%=path%>/notice.do?method=print"+where;
			window.open(URL);
		}
	</script>
</head>
<body style="margin: 0" marginheight="0" marginwidth="0">
	<form name="actionForm" method="post">
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">催缴通知</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr><!-- 这个td负责画正式内容的左、右、下方的边框 -->
    		<td class="menu_tab2" align="center">
     			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 				
	 				<tr>
						<td  align="center">
							<!-- 查询条件start -->
							<input type="hidden" name="lp" value="<%=lp %>" />
							<input type="hidden" name="fylx" value="<%=fylx %>" />
							<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
		  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
					            	<td height="10" colspan="7"></td>
					         	</tr>
					          	<tr>
					          		<!--
					           		<td class="txt" nowrap="nowrap" width="">
					           			&nbsp;&nbsp;楼盘：
					           			<select name="lpId">
					           			<option value="">请选择...</option>
					           				<c:forEach items="${lpList}" var="v">
					           					<option value="${v.lpId }" <c:if test="${CNoticeForm.lpId==v.lpId }">selected</c:if> >${v.lpName }</option>
					           				</c:forEach>
					           			</select>
					           		</td>
					           		-->
					           		<!--<td>
					           			楼幢：
					           			<select name="buildId">
					           				<option value="">请选择...</option>
					           				<c:forEach items="${buildList}" var="v">
					           					<option value="${v.buildId }">${v.buildName }</option>
					           				</c:forEach>
					           			</select>
					           		</td>
					           		--><td>
					           			客户：
					           			<input type="text" name="clientName" value="${CNoticeForm.clientName }" style="width:80px" />
					           		</td>
					           		<td>
					           			通知单：
					           			<select name="noticeType">
					           				<option value="">全部</option>
					           				<option value="2" <c:if test="${CNoticeForm.noticeType=='2' }">selected</c:if> >催款通知</option>
					           				<option value="1" <c:if test="${CNoticeForm.noticeType=='1' }">selected</c:if> >缴款通知</option>
					           			</select>
					           		</td>
					            	<td>
					           			发出日期：
					           			<input type="text" name="start" id="start" readonly onclick="WdatePicker()" value="${CNoticeForm.start }" style="width:80px" />
					           			至
					           			<input type="text" name="end" id="end" readonly onclick="WdatePicker()" value="${CNoticeForm.end }" style="width:80px" />
					           		</td>
					            	<td nowrap="nowrap" align="right"></td>
					            	<td align="right">
										<!-- 如果想在enter键敲下的时候默认被点击，那么只需将button的id设置为focuson即可 -->
					           		 	<input type="button" class="button" id = "focuson" onclick="cx()" value="查询">
									</td>
					         	 </tr>
					          	 <tr>
					            	<td height="10" colspan="7">
					            		&nbsp;&nbsp;<input type="button" class="button" id = "focuson" onclick="add()" value="添加">
					            		&nbsp;&nbsp;<input type="button" class="button" id = "focuson" onclick="del()" value="删除">
					            		&nbsp;&nbsp;<input type="button" class="button" id = "focuson" onclick="print1()" value="打印">
					            		&nbsp;&nbsp;<input type="button" class="button" id = "focuson" onclick="printAll()" value="全部打印">
					            		&nbsp;&nbsp;<!--<input type="button" class="button" id = "focuson" onclick="exportExcel('${size }')" value="导出报表">
					            		&nbsp;&nbsp;--><input type="button" class="button" id = "focuson" onclick="show1()" value="查看详细">
					            	</td>
					         	</tr>
					         	<tr>
					            	<td height="10" colspan="7"></td>
					         	</tr>
					        </table>
					        
					        <!-- 查询条件end -->
		 			 	</td>
					</tr>
  					<tr height="84%">
			    <td valign = "top">
			    	<!-- 表单内容区域 -->
					<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height = "95%">
			        		<td width="100%">
						  		<div id = "div1" class = "RptDiv" >
									<table width="100%"  cellpadding="0" cellspacing="0" class="form_tab">
					              			<tr>
					              				<th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">选择</th>
								                <th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
								                <th width="20%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">客户</th>
								                <th width="20%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">通知单类型</th>
												<th width="20%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">标题</th>
												<th width="15%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">发出日期</th>
												<th width="15%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">当前金额</th>
											</tr>
											<c:choose>
												<c:when test="${empty list}">
													<tr align="center">
														<td colspan="12" align="center" class="head_form1">
															<font color="red">没有信息!</font>
														</td>
													</tr>
												</c:when>
												<c:otherwise>
													<c:forEach items="${list}" var="v" varStatus="vs">
														<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="showDetail('${v[1] }','${v[0] }','${v[4] }');">
															<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value="${v[1] },${v[0] },${v[4] }"></td>
															<td class="RptTableBodyCellLock"  align="center">${vs.count }</td>
															<td class="RptTableBodyCell">&nbsp;${v[2] }</td>
															<td class="RptTableBodyCell">&nbsp;
															<c:if test="${v[4] eq '1' }">缴款单</c:if>
															<c:if test="${v[4] eq '2' }">催款单</c:if>
															</td>
															<td class="RptTableBodyCell">&nbsp;北控宏创物业服务收费通知单</td>
															<td class="RptTableBodyCell">&nbsp;${v[0] }</td>
															<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[3] }).toFixed(2).toString()))</script></td>
														</tr>
													</c:forEach>
												</c:otherwise>
											</c:choose>
					             		</table></div>
									</td>
		     		 			</tr>
							<tr>
								<td>
									<table width="100%" cellpadding="0" cellspacing="0">
										<tr><td class="form_line3">&nbsp;</td>
											<td class="form_line3">&nbsp;${pageHTML }</td>
											<td class="form_line3">&nbsp;</td>
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
