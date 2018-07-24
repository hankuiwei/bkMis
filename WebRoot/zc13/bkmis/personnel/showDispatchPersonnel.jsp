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
	<script type="text/javascript">
			//执行查询员工信息的方法
			function searchPersonnel(){
				document.personnelForm.action = "personnel.do?method=getWorkingConditions4Personnel";
				document.personnelForm.submit();
				setTimeout("showLoadingDiv()",100);
			}
			
			//双击显示查询详细信息
			function detailMessage(id){
				document.personnelForm.action = "personnel.do?method=doViewWorkingConditionsDetail&personnelId="+id+"&cx_sendedMan="+id;
				document.personnelForm.target = "_self";
				document.personnelForm.submit();
			}
			
			//点击导出报表按钮
			function exportExcel(){
				document.personnelForm.action = "personnel.do?method=exportWorkingConditions4PersonExcel";
				document.personnelForm.submit();
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
						<td width="200" nowrap="nowrap" class="form_line">工人工作情况列表</td>
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
					            	<td align = "left" nowrap="nowrap">员工名称：<input type="text" style="width:80px;" name="cx_personnelName" id="cx_personnelName" value="${personnelForm.cx_personnelName }"/></td>
									<td align = "left" nowrap="nowrap">员工编号：<input type="text" style="width:80px;" name="cx_personnelCode" id="cx_personnelCode" value="${personnelForm.cx_personnelCode }"/></td>
									<td align="left" nowrap="nowrap">用工状态：
										<select id="cxStatus" name="cxStatus">
											<option value="">
		    									--全部--
		    								</option>
											<option value="在职" <c:if test="${personnelForm.cxStatus eq '在职' }">selected</c:if>>
		    									在职
		    								</option>
		    								<option value="离职" <c:if test="${personnelForm.cxStatus eq '离职' }">selected</c:if>>
		    									离职
		    								</option>
		    								<option value="试用期" <c:if test="${personnelForm.cxStatus eq '试用期' }">selected</c:if>>
		    									试用期
		    								</option>
										</select>
									</td>
									<td align="right" nowrap="nowrap">&nbsp;</td>
								</tr>
								<tr>
					           		<td class="txt" nowrap="nowrap">&nbsp;</td>
									<td align="left" nowrap="nowrap">是否在岗：
										<select id="cx_isInPost" name="cx_isInPost">
											<option value="">
		    									--全部--
		    								</option>
											<option value="<%=Contants.ISINPOST %>" <c:if test="${personnelForm.cx_isInPost eq '1' }">selected</c:if>>
		    									已上班
		    								</option>
		    								<option value="<%=Contants.ISNOTINPOST %>" <c:if test="${personnelForm.cx_isInPost eq '2' }">selected</c:if>>
		    									已下班
		    								</option>
										</select>
									</td>
									<td align="left" nowrap="nowrap">是否已派出：
										<select id="cx_isOut" name="cx_isOut">
											<option value="">
		    									--全部--
		    								</option>
											<option value="<%=Contants.ISOUT %>" <c:if test="${personnelForm.cx_isOut eq '已派出' }">selected</c:if>>
		    									已派出
		    								</option>
		    								<option value="<%=Contants.ISNOTOUT %>" <c:if test="${personnelForm.cx_isOut eq '已派出' }">selected</c:if>>
		    									未派出
		    								</option>
										</select>
									</td>
									<td align = "left" nowrap="nowrap">工作时间：
										<input type="text" style="width:80px;" name="cx_starttime" id="cx_starttime" readonly onclick="WdatePicker()" value="${personnelForm.cx_starttime }" class="Wdate"/>
										&nbsp;至&nbsp;
										<input type="text" style="width:80px;" name="cx_endtime" id="cx_endtime" readonly onclick="WdatePicker()" value="${personnelForm.cx_endtime }" class="Wdate"/>
									</td>
									<td align="right" nowrap="nowrap">
					            		<input type="button" name="focuson" value="查询" onclick="searchPersonnel()" class="button"/>
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
												<th class = "RptTableHeadCellLock">性别</th>
												<th class = "RptTableHeadCellLock">电话</th>
												<th class = "RptTableHeadCellLock">用工状态</th>
												<th class = "RptTableHeadCellLock">是否在岗</th>
												<th class = "RptTableHeadCellLock">是否已派出</th>
												<th class = "RptTableHeadCellLock">工作时间总和</th>
												<th class = "RptTableHeadCellLock">非常满意/满意/不满意</th>
												<th class = "RptTableHeadCellLock">工作次数</th>
											</tr>
											<c:choose>
											<c:when test="${empty personnelForm.personnelList}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的员工信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${personnelForm.personnelList}" var="per" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';" id="detail" ondblclick="detailMessage('${per.personnelId }')">
														<td class="RptTableBodyCellLock" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.personnelName }">${per.personnelName }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.personnelCode }">${per.personnelCode }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.sex}">${per.sex}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.phone}">${per.phone}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.status}">${per.status}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.isInPost}">${per.isInPost }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.isOut}">${per.isOut}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.totalHour}">${per.totalHour}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.verySatisfied}/${per.satisfied}/${per.notSatisfied}">${per.verySatisfied}/${per.satisfied}/${per.notSatisfied}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.number}">${per.number}&nbsp;</td>
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
								<!-- 分页start -->
								<tr>
									<td colspan="10">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr><td class="form_line3">&nbsp;</td>
												<td class="form_line3" colspan="8">${pagination }</td>
												<td class="form_line3">&nbsp;</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td align="right">
										<table>
											<tr>
					                			<td nowrap="nowrap"  align="right"><input name="addPersonnel" class="button" onclick="exportExcel()" type="button" value="导出报表"></td>
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

