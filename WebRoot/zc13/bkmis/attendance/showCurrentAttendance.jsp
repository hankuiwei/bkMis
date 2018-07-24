<%@ page language="java" import="com.zc13.util.*"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
%>
<html>
<head>
	<title>员工信息列表</title>
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
			//执行查询员工信息的方法
			function search(){
				document.personnelForm.action = "attendance.do?method=showCurrentAttendance";
				document.personnelForm.submit();
				setTimeout("showLoadingDiv()",100);
			}
			
			//
			function attendance(personnelId,personnelNum,status){
				if(window.confirm("确定此操作吗？")){
					var options = "dialogWidth:300px;dialogHeight:300px;status:no;scroll:no;";
					var url = "<%=path%>/zc13/bkmis/attendance/setTime.jsp";
					//window.open(url);
					var returnVal = window.showModalDialog(url,this.window, options);
					if(typeof(returnVal)!="undefined"){
						$.post("<%=path%>/attendance.do",{method:"setAttendanceTime",personnelId:personnelId,personnelNum:personnelNum,status:status,time:returnVal},function(data){
							if(data=="1"){
								alert("成功！");
							}else{
								alert("失败！");
							}
							search();
						});
					}
				}
					
			}
		</script>
</head>
<!-- 加载页面div -->
<jsp:include page="../../../loading.jsp"></jsp:include>
<!-- 加载页面div -->
<body>
<form  action="personnel.do?method=showPersonnel" method="post" name="personnelForm">
	<input type="hidden" name="forward" value="${forward}" />
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="200" nowrap="nowrap" class="form_line">考勤信息维护</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr height = "95%">
    		<td class="menu_tab2" align="center" valign="top">
     			<table width="100%" height = "100%"  border="0" cellspacing="0" cellpadding="0">
	 				<tr>
						<td  align="center">
							<!-- 查询条件start -->
		  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
					            	<td height="10"></td>
					         	</tr>
					          	<tr>
					           		<td class="txt" nowrap="nowrap">
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					           		</td>
					            	<td align = "left" nowrap="nowrap">员工名称：<input type="text" style="width:80px;" name="cx_personnelName" id="cx_personnelName" value="${attendanceForm.cx_personnelName }"/></td>
									<td align = "left" nowrap="nowrap">员工编号：<input type="text" style="width:80px;" name="cx_personnelCode" id="cx_personnelCode" value="${attendanceForm.cx_personnelCode }"/></td>
									<td align = "left" nowrap="nowrap">
									是否是派工工人
									<select id="cx_isDispatch" name="cx_isDispatch">
											<option value="">
		    									--全部--
		    								</option>
											<option value="<%=Contants.ISDISPATCH %>" <c:if test="${attendanceForm.cx_isDispatch eq '1' }">selected</c:if>>
		    									是
		    								</option>
		    								<option value="<%=Contants.ISNOTDISPATCH %>" <c:if test="${attendanceForm.cx_isDispatch eq '0' }">selected</c:if>>
		    									否
		    								</option>
										</select>
									</td>
									<td align="right" nowrap="nowrap">
					            		<input type="button" name="focuson" value="查询" onclick="search()" class="button"/>
									</td>
								</tr>
							 	 <tr>
					           		 <td height="10" colspan="9"></td>
					          	 </tr>
					        </table>
					        <!-- 查询条件end -->
		 			 	</td>
					</tr>
  					<tr height="95%">
					    <td valign = "top">
					    	<!-- 表单内容区域 -->
							<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
								<tr height = "95%">
					        		<td width="100%">
					        		<div id = "div1" class = "RptDiv"  >
							   			<table class = "RptTable" border="0" cellpadding="0" cellspacing="0">
					              			<tr>
												<th class = "RptTableHeadCellFullLock">序号</th>
												<th class = "RptTableHeadCellLock">姓名</th>
												<th class = "RptTableHeadCellLock">编号</th>
												<th class = "RptTableHeadCellLock">工号</th>
												<th class = "RptTableHeadCellLock">上班</th>
												<th class = "RptTableHeadCellLock">下班</th>
											</tr>
											<c:choose>
											<c:when test="${empty attendanceForm.personnelList}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的员工信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${attendanceForm.personnelList}" var="per" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';" id="detail">
														<td class="RptTableBodyCellLock" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.personnelName }">${per.personnelName }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.personnelCode }">${per.personnelCode }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.personnelNum }">${per.personnelNum }&nbsp;</td>
														<td class="RptTableBodyCell" align="center">
														<c:if test="${per.isInPost eq '1'}">已上班(${per.time })</c:if>
														<c:if test="${per.isInPost ne '1'}"><input type="button" name="focuson" value="上班" onclick="attendance('${per.personnelId }','${per.personnelNum }','1');" class="button"/></c:if>
														</td>
														<td class="RptTableBodyCell" align="center">
														<c:if test="${per.isInPost eq '1'}"><input type="button" name="focuson" value="下班" onclick="attendance('${per.personnelId }','${per.personnelNum }','2');" class="button"/></c:if>
														<c:if test="${per.isInPost ne '1'}">已下班(${per.time })</c:if>
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

