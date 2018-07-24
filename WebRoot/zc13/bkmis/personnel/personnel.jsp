<%@ page language="java" import="com.zc13.util.*"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String message = (String)request.getAttribute("message");
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
	<script type="text/javascript">
	<%if(message!=null&&!message.equals("")){%>
	alert("<%=message%>");
	<%}%>
	</script>
	<script type="text/javascript">
			//执行查询员工信息的方法
			function searchPersonnel(){
				document.personnelForm.action = "personnel.do?method=showPersonnel";
				document.personnelForm.submit();
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
			//执行删除员工的事件
			function delPersonnel(){
	  			var itemIds = '';
				var chks = document.getElementsByName("childBox");
				//alert(chks.length);
				for(var i = 0;i < chks.length;i++){
					if(chks[i].checked==true){
						itemIds += chks[i].value + ',';
					}
				}
				//alert(itemIds);
				if (itemIds.length > 0) {
					if(!window.confirm("确定要删除吗？"))  {return ;}
					itemIds = itemIds.slice(0, itemIds.length-1);
					document.personnelForm.action="personnel.do?method=delPersonnel&ids="+itemIds;
					document.personnelForm.submit();
				}
				else{
					alert("请先选择记录，再进行操作!");
					return false;
				}
			}
			//执行员工添加的操作
			function AddPersonnel(){
			    //alert("ok");
				document.personnelForm.action="<%=path%>/personnel.do?method=addPersonnelFace";
				document.personnelForm.target = "_self";
				document.personnelForm.submit();
			}
			//修改员工的页面
			function modifyPersonnel(){
				var itemIds = '';
				var chks = document.getElementsByName("childBox");
				var count = 0;
				for(var i = 0;i < chks.length;i++){
					if(chks[i].checked==true){
						count++;
						itemIds = chks[i].value;
					}
				}
				if (count == 0) {
					alert("请选择记录进行修改!");
            		return;
            	}else if(count == 1){
					document.personnelForm.action = "personnel.do?method=editPersonnelFace&personnelId="+itemIds;
					document.personnelForm.target = "_self";
					document.personnelForm.submit();
				}
				else{
					alert("只能选择一条记录");
          		    return;
				}
			}
			//双击显示查询详细信息
			function detailMessage(id){
				document.personnelForm.action = "personnel.do?method=doViewDetail&personnelId="+id;
				document.personnelForm.target = "_self";
				document.personnelForm.submit();
			}
			//导出报表
			function exploertReport(){
				document.personnelForm.action = "personnel.do?method=exportPersonExcel";
				document.personnelForm.submit();
			}
		</script>
</head>
<body>
<form  action="personnel.do?method=showPersonnel" method="post" name="personnelForm">
	<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">员工信息列表</td>
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
									<td align = "left" nowrap="nowrap">到职时间：
										<input type="text" style="width:80px;" name="cx_starttime" id="cx_starttime" readonly onclick="WdatePicker()" value="${personnelForm.cx_starttime }" class="Wdate"/>
										&nbsp;至&nbsp;
										<input type="text" style="width:80px;" name="cx_endtime" id="cx_endtime" readonly onclick="WdatePicker()" value="${personnelForm.cx_endtime }" class="Wdate"/>
									</td>
									<td align="left" nowrap="nowrap">所属部门：
										<select id="cx_departmentcode" name="cx_departmentcode" onchange="searchPersonnel()">
											<option value="">
												--全部--
											</option>
											<c:forEach items="${personnelForm.department}" var="depart">
											<option value="${depart.codeName}" 
											<c:if test="${personnelForm.cx_departmentcode == depart.codeName}">
											selected
											</c:if>
											>
											${depart.codeName }
											</option>
											</c:forEach>
										</select>
									</td>
									<td align="right" nowrap="nowrap">
					            		&nbsp;
									</td>
								</tr>
								<tr>
					           		<td class="txt" nowrap="nowrap">
					           			&nbsp;
					           		</td>
					            	
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
									<td align = "left" nowrap="nowrap">
									是否是派工工人
									<select id="cx_isDispatch" name="cx_isDispatch">
											<option value="">
		    									--全部--
		    								</option>
											<option value="<%=Contants.ISDISPATCH %>" <c:if test="${personnelForm.cx_isDispatch eq '1' }">selected</c:if>>
		    									是
		    								</option>
		    								<option value="<%=Contants.ISNOTDISPATCH %>" <c:if test="${personnelForm.cx_isDispatch eq '0' }">selected</c:if>>
		    									否
		    								</option>
										</select>
									</td>
									<td align = "left" nowrap="nowrap">&nbsp;</td>
									<td align="right" nowrap="nowrap">
					            		<input type="button" name="focuson" value="确定" onclick="searchPersonnel()" class="button"/>
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
												<th class = "RptTableHeadCellFullLock">应聘渠道</th>
												<th class = "RptTableHeadCellLock">姓名</th>
												<th class = "RptTableHeadCellLock">性别</th>
												<th class = "RptTableHeadCellLock">到职日期</th>
												<th class = "RptTableHeadCellLock">学历/学位</th>
												<th class = "RptTableHeadCellLock">专业</th>
												<th class = "RptTableHeadCellLock">职称/资格</th>
												<th class = "RptTableHeadCellLock">政治面貌</th>
												<th class = "RptTableHeadCellLock">民族</th>
												<th class = "RptTableHeadCellLock">所属部门</th>
												<th class = "RptTableHeadCellLock">是否是派工工人</th>
												<th class = "RptTableHeadCellLock">用工状态</th>
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
														<td class="RptTableBodyCellLock" align="center"><input type="checkbox" name="childBox" id="childBox" value="${per.personnelId }"/></td>
														<td class="RptTableBodyCellLock" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
														<td class="RptTableBodyCellLock" align="center" title="${per.engageChannel}">${per.engageChannel}&nbsp;
														</td>
														<td class="RptTableBodyCell" align="center" title="${per.personnelName }">${per.personnelName }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.sex}">${per.sex}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.startDate}">${per.startDate}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.academicCertificate }">${per.academicCertificate }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.speciality }">${per.speciality }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.duty }">${per.duty }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.groups }">${per.groups }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.nation }">${per.nation }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${per.departmentCode }">${per.departmentCode }&nbsp;</td>
														<td class="RptTableBodyCell" align="center">&nbsp;
														<c:if test="${per.isDispatch eq '1' }">是</c:if>
														<c:if test="${per.isDispatch ne '1' }">否</c:if>
														</td>
														<td class="RptTableBodyCell" align="center" title="${per.status }">${per.status }&nbsp;</td>
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
					                			<td nowrap="nowrap"  align="right"><input name="addPersonnel" class="button" onclick="AddPersonnel()" type="button" value="添加"></td>
					               				<td nowrap="nowrap"><input name="editPersonel" type="button" onclick="modifyPersonnel()" class="button" value="编辑"></td>
					               				<td nowrap="nowrap"><input name="delPersonel" type="button" onclick="delPersonnel()" class="button" value="删除"></td>
					               				<c:choose>
					               				<c:when test="${empty personnelForm.personnelList}">
					               				<td nowrap="nowrap"><input name="reportPersonel" type="button" class="button" value="导出报表" onclick="alert('无记录,不能导出报表!')"></td>
					              				</c:when>
					              				<c:otherwise>
					              				<td nowrap="nowrap"><input name="reportPersonel" type="button" class="button" value="导出报表" onclick="exploertReport()"></td>
					              				</c:otherwise>
					              				</c:choose>
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

