<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>客户跟踪记录列表</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript">
		function checkAll(str){
			var arrrad1 = document.getElementsByName("checkbox1");	
			if(str.checked){
				for(var i=0;i<arrrad1.length;i++){
					arrrad1[i].checked="checked";
				}
			}else{
				for(var i=0;i<arrrad1.length;i++){
					arrrad1[i].checked="";
				}
			}		
		}
		function add(){
			var url = "<%=basePath %>trackRecord.do?method=goAddOrEdit";
			var options = "dialogWidth:600px;dialogHeight:400px;status:no;scroll:no;";
			var win = window.showModalDialog(url, this.window, options);
			if(win=="flag"){
				query();
			}
		}
		var k;
		function edit(){
			if(!check()){
				return;
			}
			var id="";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = arr[i].value;
				}
			}
			var url = "<%=basePath %>trackRecord.do?method=goAddOrEdit&trackRecord.id="+id;
			var options = "dialogWidth:800px;dialogHeight:600px;status:no;scroll:no;";
			var win = window.showModalDialog(url, this.window, options);
			if(win=="flag"){
				query();
			}
		}
		function query(){
			document.form1.action="<%=basePath%>trackRecord.do?method=getList";
			document.form1.submit();
		}
		function check(){
			k = 0;
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					k++;
				}
			}
			if(k==0){
				alert("请选择要编辑的内容");
				return false;
			}
			if(k>1){
				alert("最多只能选择一条记录");
				return false;
			}
			return true;
		}
		function del(){
			k = 0;
			var str = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					str = str + arr[i].value + ",";
					k++;
				}
			}
			str = str.substr(0,str.length-1);
			if(k==0){
				alert("请选择要删除的记录");
				return;
			}
			if(confirm("确定要删除这"+k+"条记录？")){
				document.form1.action = "<%=basePath %>trackRecord.do?method=delById&ids="+str;
				document.form1.submit();
			}
		}
	</script>
</head>
<body>
<form name="form1" method="post">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">客户跟踪记录列表</td>
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
					           		<td class="txt" nowrap="nowrap" width="30%" align="center">
					           			客户名称：<input type="text" name="customerName" value="${trackRecordForm.customerName}" style=" width: 138px"a>
					           		</td>
									<td class="txt" nowrap="nowrap" width="30%" align="center">
					           			状态：
					           			<select name="status">
					           				<option value="">--请选择--</option>
					           				<option value="成交" <c:if test="${trackRecordForm.status == '成交'}">selected</c:if>>成交</option>
					           				<option value="未成交" <c:if test="${trackRecordForm.status == '未成交'}">selected</c:if>>未成交</option>
					           				<option value="正在跟踪" <c:if test="${trackRecordForms.tatus == '正在跟踪'}">selected</c:if>>正在跟踪</option>
					           			</select>
					           		</td>
					           		<td align="left" nowrap="nowrap" width="20%">
					            		<input type="button" class="button" value="查询" onclick="query()">
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
								                <th width="2%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this)">
								                </th>
								                <th width="5%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户名称</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">编号</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">状态</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">所属行业</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">联系方式</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">录入时间</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">录入人</th>
											</tr>
											<c:choose>
											<c:when test="${empty trackRecordList}">
											<tr align="center">
												<td colspan="9" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${trackRecordList}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
											 	<td width="" nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" name="checkbox1" value="${v.id }">
												</td>
								                <td width="" nowrap="nowrap" class="RptTableBodyCellLock" align="center">&nbsp;${tag.count }</td>
												<td width="" nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.customerName }</td>
												<td width="" nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.code}</td>
												<td width="" nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.status}</td>
												<td width="" nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.workField}</td>
												<td width="" nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.linkWay}</td>
												<td width="" nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.createDate}</td>
											 <td class="RptTableBodyCell">&nbsp;
						    			<c:forEach items="${userList}" var="a">
							    			<c:if test="${v.userId==a.userid }">${a.realName }(${a.username })</c:if>
							    		</c:forEach>
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
					                			<td nowrap="nowrap"  align="right"><input class="button" type="button" value="添加" onclick="add()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="编辑" onclick="edit()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="删除" onclick="del()"></td>
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
