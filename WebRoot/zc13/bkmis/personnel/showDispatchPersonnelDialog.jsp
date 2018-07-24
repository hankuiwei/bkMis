<%@ page language="java"   pageEncoding="UTF-8"%>
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
				document.personnelForm.action = "personnel.do?method=getDispatchPersonnel";
				document.personnelForm.submit();
				setTimeout("showLoadingDiv()",100);
			}
			//删除复选框的全选事件
			function selectall(){
				var parentBox=document.getElementById("parentBox");
				var childBox=document.getElementsByName("childBox");
				if(parentBox.checked==true){
				for(var i=0;i<childBox.length;i++)
				{childBox[i].checked=true;}
				}else{
				for(var i=0;i<childBox.length;i++)
				{childBox[i].checked=false;}
			  }
			}
			
			//点击确定按钮
			function determine(){
				var childBox=document.getElementsByName("childBox");
				var arrs = new Array();
				var count = 0;
				for(var i=0;i<childBox.length;i++){
					if(childBox[i].checked){
						var tr = childBox[i].parentElement.parentElement;
						var tds = tr.getElementsByTagName("td");
						arrs[count]={"personnelId":childBox[i].value,"personnelName":trim(tds[2].innerText),"personnelCode":trim(tds[3].innerText),"phone":trim(tds[5].innerText)};
						count++;
					}
				}
				window.returnValue = arrs;
				this.close();
			}
			
			//点击返回按钮
			function cancel(){
				this.close();
			}
			
		</script>
</head>
<!-- 加载页面div -->
<jsp:include page="../../../loading.jsp"></jsp:include>
<!-- 加载页面div -->
<body>
<form  action="personnel.do?method=showPersonnel" method="post" name="personnelForm">
	<input type="hidden" name="cx_isInPost" value="${personnelForm.cx_isInPost }" />
	<input type="hidden" name="cx_isOut" value="${personnelForm.cx_isOut }" />
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
						<td width="200" nowrap="nowrap" class="form_line">可派遣的在岗员工信息列表</td>
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
					            	<td align = "left" nowrap="nowrap">员工名称1：<input type="text" style="width:80px;" name="cx_personnelName" id="cx_personnelName" value="${personnelForm.cx_personnelName }"/></td>
									<td align = "left" nowrap="nowrap">员工编号2：<input type="text" style="width:80px;" name="cx_personnelCode" id="cx_personnelCode" value="${personnelForm.cx_personnelCode }"/></td>
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
								                <th class = "RptTableHeadCellFullLock"><input type="checkbox" name="parentBox" onclick="selectall()"/></th>
												<th class = "RptTableHeadCellFullLock">序号</th>
												<th class = "RptTableHeadCellLock">姓名</th>
												<th class = "RptTableHeadCellLock">编号</th>
												<th class = "RptTableHeadCellLock">性别</th>
												<th class = "RptTableHeadCellLock">电话</th>
												<th class = "RptTableHeadCellLock">所属部门</th>
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
														<td class="RptTableBodyCellLock" align="center"><input type="checkbox" name="childBox" id="childBox" <c:if test="${fn:contains(ids,per.personnelId)}">checked</c:if> value="${per.personnelId }"/></td>
														<td class="RptTableBodyCellLock" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.personnelName }">${per.personnelName }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.personnelCode }">${per.personnelCode }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.sex}">${per.sex}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.sex}">${per.phone}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.departmentCode }">${per.departmentCode }&nbsp;</td>
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
					                			<td nowrap="nowrap"  align="right"><input name="addPersonnel" class="button" onclick="determine()" type="button" value="确定"></td>
					               				<td nowrap="nowrap"><input name="editPersonel" type="button" onclick="cancel()" class="button" value="返回"></td>
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

