<%@ page language="java"  import="java.util.*,com.zc13.util.*"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String message = GlobalMethod.NullToSpace((String)request.getAttribute("message"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>客户入驻通知单</title>
	
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
		<%if(!message.equals("")){%>
			alert("<%=message%>");
		<%}%>
		/*function onload1(){
			if('${modify}'==1){
				alert("修改成功！");
			}
		}*/
		
		
		function look(id){
			window.open("workTask.do?method=lookTaskInfo&compactId="+id);
			
		}
		function edit(){
			if(!check("编辑")){
				return;
			}
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = arr[i].value;
				}
			}
			document.forms[0].action="workTask.do?method=editTaskInfo&compactId="+id;
			document.forms[0].submit();
		}
		function check(str){
			k = 0;
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					k++;
				}
			}
			if(k==0){
				alert("请选择要"+str+"的记录");
				return false;
			}
			if(k>1){
				alert("最多只能选择一条记录");
				return false;
			}
			return true;
		}
	</script>
	
</head>
<!--  <body onload="onload1();">-->
<body>
<form name = "form1" method="post" action = "workTask.do?method=getWorkTaskList2">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">客户入驻通知单列表</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr height = "95%"><!-- 这个td负责画正式内容的左、右、下方的边框 -->
    		<td class="menu_tab2" align="center">
     			<table width="100%"  height = "100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
						<td align="center">
							<!-- 查询条件start -->
		  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
					            	<td height="10"></td>
					         	</tr>
					          	<tr>
					           		<td class="txt" nowrap="nowrap">
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					           		</td>
					            	<td nowrap="nowrap">客户名称：<input type="text" name="cxClientName" value="${workTaskForm.cxClientName }" /></td>
					            	<td nowrap="nowrap">房间号：<input type="text" name="cxRooms" value="${workTaskForm.cxRooms }" /></td>
					            	<td height="10" nowrap="nowrap">通知单日期： 
					            		<input type="text" name="cxNoteDate" value="${workTaskForm.cxNoteDate }" readonly onclick="WdatePicker();" class="Wdate">&nbsp;至&nbsp;
					            		<input type="text" name="cxNoteDateEnd" value="${workTaskForm.cxNoteDateEnd }" readonly onclick="WdatePicker();" class="Wdate"></td>
									<td align="right" nowrap="nowrap">
					            		<input type="submit" class="button" value="查询" />
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
							   			<table border="0" cellpadding="0" cellspacing="0" class = "RptTable">
					              			<tr>
								                <th width="7%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this)">
								                </th>
								                <th width="7%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">通知单编号</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户名称</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">合同号</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">联系人</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">联系电话</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">通知单下发时间</th>
											</tr>
											<c:choose>
											<c:when test="${empty list}">
											<tr align="center">
												<td colspan="10" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${list}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="look('${v.id }');">
											 	<td nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" id="checkbox1" name="checkbox1" value="${v.id }">
												</td>
								                <td nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count}</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.code }&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.clientName }&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.compactCode }&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.rooms }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.linkMan }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.linkPhone }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.noteDate }&nbsp;</td>
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
									<td colspan="5">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr>
												<td class="form_line3">&nbsp;</td>
												<td class="form_line3" colspan="8">${pagination }</td>
												<td class="form_line3">&nbsp;</td>
											</tr>
										</table>
									</td>
								</tr>
								<!-- 分页end -->
								<tr>
									<td align="right">
										<table>
											<tr>
											    <td nowrap="nowrap"><input type="button" class="button" value="编辑" onclick="edit();">
											    
											    </td>
											   
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
