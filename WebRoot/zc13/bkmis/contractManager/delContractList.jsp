<%@ page language="java"   pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.Contants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>迁出管理</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript"></script>
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
		var k;
		function add(){
			window.open("<%=path%>/compact.do?method=getList&flag=quit");
		}
		function edit(){
			if(!check("编辑")){
				return;
			}
			var id = "";
			var isNotice = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = arr[i].value;
					var b  = document.getElementById("isNotice"+i);
					if(b!=null){
						isNotice = b.value;
					}
				}
			}
			if(isNotice=="<%=Contants.HASGO%>"){
				//alert("该客户当前已经迁出！");
				//return;
			}
			if(isNotice=="<%=Contants.HASNOTICE%>"){
				//alert("已经通知过迁出,无法编辑！");
				//return;
			}
			window.open("<%=path%>/compact.do?method=goEditQuit&id="+id);
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
				alert("请选择要"+str+"的内容");
				return false;
			}
			if(k>1){
				alert("最多只能选择一条记录");
				return false;
			}
			return true;
		}
		function selectAny(){
			document.form1.action = "compact.do?method=getDelContractList";
			document.form1.submit();
		}
		
		//120928查看详细信息
		function viewDetail(quitId){
			window.open("<%=path%>/compact.do?method=quitDetail&quitId="+quitId);
		}
	</script>
</head>
<body>
<form name = "form1" method="post">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">迁出管理	</td>
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
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件:
					           		</td>
					            	<td height="10" nowrap="nowrap">客户名称：<input type="text" name="clientName" id="clientName" value="${compactManagerForm.clientName }"></td>
					            	<td height="10" nowrap="nowrap">合同状态：<input type="hidden" name="statusIn" id="statusIn" value="${compactManagerForm.status }">
										<select name="status" id="status">
											<option value="" selected="selected">全部</option>
											<option value="<%=Contants.NOTSUBMIT %>"><%=Contants.NOTSUBMIT %></option>
											<option value="<%=Contants.ONAPPROVAL %>"><%=Contants.ONAPPROVAL %></option>
											<option value="<%=Contants.THROUGHAPPROVAL %>"><%=Contants.THROUGHAPPROVAL %></option>
											<option value="<%=Contants.NOTTHROUGH %>"><%=Contants.NOTTHROUGH %></option>
										</select>
									</td>
					            	<td height="10" nowrap="nowrap">申请日期：<input type="text" name="beginDate" value="${compactManagerForm.beginDate }" readonly onclick="WdatePicker();" class="Wdate">&nbsp;至&nbsp;<input type="text" name="endDate" value="${compactManagerForm.endDate }" readonly onclick="WdatePicker();" class="Wdate"></td>
									<td align="right" nowrap="nowrap">
					            		<input type="button" class="button" value="查询" onclick="selectAny()">
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
								                <th width=""  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this)">
								                </th>
								                <th width="6%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">退租编号</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">合同编号</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户名称</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">申请日期</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">迁出日期</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">退租原因</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">经办人</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">状态</th>
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
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="viewDetail('${v.id }')">
											 	<td width="" nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" id="checkbox1" name="checkbox1" value="${v.id }">
									                <input type="hidden" id="pactId${tag.count-1 }" value="${v.compact.id }">
												</td>
								                <td width="6%" nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count }</td>
												<td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.quitCode }&nbsp;</td>
												<td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.compact.code }&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.compact.name}&nbsp;</td>
												<td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.applayDate }&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.quitDate }&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.quitSeason }&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.doMan }&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.compact.isNotice }&nbsp;
									                <input type="hidden" id="status${tag.count-1 }" value="${v.compact.isNotice }">
									                <input type="hidden" id="isNotice${tag.count-1 }" name="isNotice" value="${v.compact.isNotice }">
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
					               				<td nowrap="nowrap"><input type="button" class="button" value="迁出申请" onclick="add()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="编辑" onclick="edit()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="通知迁出" onclick="outRoom()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="确定迁出" onclick="isOk()"></td>
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
	<script type="text/javascript">
		if(document.getElementById("statusIn").value!=null&&document.getElementById("statusIn").value!=""){
			document.getElementById("status").value = document.getElementById("statusIn").value;
		}
		function outRoom(){
			if(!check("通知迁出")){
				return;
			}
			var id = "";
			var isNotice = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = document.getElementById("pactId"+i).value;
					var b  = document.getElementById("isNotice"+i);
					if(b!=null){
						isNotice = b.value;
					}
				}
			}
			if(isNotice=="<%=Contants.HASGO%>"){
				alert("该客户当前已经迁出！");
				return;
			}else if(isNotice=="<%=Contants.HASNOTICE%>"){
				alert("已经通知过迁出！请按照流程缴纳费用！");
				return;
			}else{
			var win = null;
				if(confirm("确定通知迁出吗？通知迁出意味着客户通过了迁出审批，会在接下来的手续后正式迁出园区。\n此项操作会迁出客户入住的所有房间，该住户转为历史住户，并生成客户自上次账单日至今的账单费用。\n迁出后请对住户的收费设置和收费单据进行处理！\n注：通知迁出后的一天内，可以在后悔迁出中撤销此操作，时间一旦超过一天，将无法撤销！")){
					var url = "zc13/bkmis/customerManager/outRoom.jsp?id="+id;
					var options = "dialogWidth:300px;dialogHeight:300px;status:no;scroll:no;";
					win = window.showModalDialog(url, this.window, options);
					
					if(win == "123"){
						window.location.href="<%=path%>/compact.do?method=getDelContractList";
					}
				}
			}
		}
		function isOk(){
			if(!check("迁出")){
				return;
			}
			var id = "";
			var isNotice = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = document.getElementById("pactId"+i).value;
					var b  = document.getElementById("isNotice"+i);
					if(b!=null){
						isNotice = b.value;
					}
				}
			} 
			if(isNotice=="<%=Contants.HASGO%>"){
				alert("已经迁出！无须再次确认迁出");
				return;
			}else if(isNotice=="<%=Contants.HASAPPLYGO%>"){
				alert("请首先通知迁出，并缴纳相关费用！");
				return;
			}else{
				$.post("<%=path%>/customer.do",{method:"checkIsPayment",compactId:id},function(data){
					if(data=="1"){
						if(confirm("确定迁出吗，确定迁出前请确认收到财务部盖章的迁出审核单。\n确定迁出后本条记录会变成历史记录，以后将无法编辑，只能查看!")){
							window.location.href="<%=path%>/customer.do?method=enterToGo&compactId="+id;
						}
					}else{
						alert("该客户还有未缴纳的费用，不能迁出！");
						return;
					}
				})
			}
		}
	</script>
</html>
