<%@ page language="java"   pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String message = GlobalMethod.NullToSpace((String)request.getAttribute("message"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>预入驻管理</title>
	
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
	</script>
	<script type="text/javascript">
		
		function look(id){
			window.open("<%=path%>/customer.do?method=gotoLookCompact&flag=gotolook&id="+id);
		}
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
		function income(){
		
			if(!check("正式入住")){
				return;
			}
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = arr[i].value;
				}
			}
			$.post("<%=path%>/customer.do",{method:"checkCompactStatus",id:id},function(data){
				if(data=="通过审批"){
					if(window.confirm("确定执行正式入住吗?")){
						document.form1.action = "<%=path%>/destine.do?method=inCome&compactId="+id;
						document.form1.submit();
					}
				}else{
					alert("该合同未通过审批，不能入住");
					return;
				}
			})
			
		}
		function add(){
			window.location.href("destine.do?method=goAdd");
		}
		function delCompact(){
			if(!check("删除")){
				return;
			}
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = arr[i].value;
				}
			}
			$.post("<%=path%>/customer.do",{method:"checkCompactStatus",id:id},function(data){
				if(data=="待审批"){
					alert("该合同正在审批中,不能删除");
					return;
				}else{
					if(window.confirm("确定删除这条记录吗?")){
						window.location.href="<%=path%>/destine.do?method=delCompact&id="+id;
					}				
				}
			})
			
				
		}
		
		function returnOutRoom(){
			var url = "zc13/bkmis/costManage/public_pop.jsp?URL=customer.do?method=getOutCompact";
			var options = "dialogWidth:800px;dialogHeight:330px;status:no;scroll:no;";
			win = window.showModalDialog(url, this.window, options);
		}
		function selectAny(){
			document.form1.action = "<%=path%>/destine.do?method=getDestineList";
			document.form1.submit();
		}
		var k;
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
			$.post("<%=path%>/customer.do",{method:"checkCompactStatus",id:id},function(data){
				if(data=="待审批"){
					alert("该合同正在审批中,不允许编辑");
					return;
				}else if(data=="通过审批"){
					alert("该合同已通过审批,不允许编辑");
					return;
				}else{
					window.location.href="<%=path%>/destine.do?method=gotoEditCompact&id="+id;
				}
			})
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
		//导出报表
		function explorReport(){
			document.form1.action = "destine.do?method=explorReport";
			document.form1.submit();
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
						<td width="165" nowrap="nowrap" class="form_line">预入驻管理</td>
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
					            	<td height="10" nowrap="nowrap">
					            	住户名称：<input type="text" name="clientName" id="clientName" value="${clientName }">
					            	&nbsp;&nbsp;&nbsp;
					            	房间号：<input type="text" name="roomCode" value="${roomCode }">
					            	</td>
					            	<td>
										&nbsp;&nbsp;&nbsp;是否缴纳定金：
					            		<select name="isEarnest" id="isEarnest">
											<option value="">全部</option>
											<option  value="1" 
													<c:if test="${destineForm.isEarnest eq 1}">
															selected
													</c:if>>已缴</option>
											<option value="0"
												<c:if test="${destineForm.isEarnest eq 0}">
															selected
													</c:if>>未缴</option>
										</select>
									</td>
								<td align="left">合同号：<input align="left" type="text" name="code" value="${code }"></td>
					            </tr>
					            <tr>
					            	<td height="4"></td>
					         	</tr>
					            <tr>
					            	<td height="10"></td>
					            	<td height="10" nowrap="nowrap">入住时间：<input type="text" name="beginDate" value="${beginDate }" readonly onclick="WdatePicker();" class="Wdate">&nbsp;至&nbsp;<input type="text" name="endDate" value="${endDate }" readonly onclick="WdatePicker();" class="Wdate"></td>
									
									<td height="10" nowrap="nowrap">合同审批状态：<input type="hidden" name="statusIn" id="statusIn" value="${status }">
										<select name="status" id="status">
											<option value="" selected="selected">全部</option>
											<option value="<%=Contants.NOTSUBMIT %>"><%=Contants.NOTSUBMIT %></option>
											<option value="<%=Contants.ONAPPROVAL %>"><%=Contants.ONAPPROVAL %></option>
											<option value="<%=Contants.THROUGHAPPROVAL %>"><%=Contants.THROUGHAPPROVAL %></option>
											<option value="<%=Contants.NOTTHROUGH %>"><%=Contants.NOTTHROUGH %></option>
										</select>
									</td>
									<td></td>
									<td align="right" nowrap="nowrap" >
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
							   			<table border="0" cellpadding="0" cellspacing="0" class = "RptTable" id="tb1">
					              			<tr>
								                <th width="6%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this)">
								                </th>
								                <th width="6%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">客户名称</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">合同号</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">联系电话</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">面积</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户类型</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">合同类型</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">合同开始日期</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">合同到期时间</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">合同审批状态</th>
											</tr>
											<c:choose>
											<c:when test="${empty destineForm.destineList}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${destineForm.destineList}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" 
											    onmouseout="this.className = '';" 
											    ondblclick="look(${v.id })">
											    
											 	<td  nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" id="checkbox1" name="checkbox1" value="${v.id }">
												</td>
								                <td  nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCellLock" align="center">${v.name }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.code }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.phone }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.roomCodes }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.totalArea }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.clientType }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.type }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.beginDate }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.endDate }&nbsp;</td>
								               
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.status }&nbsp;</td>
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
					                			<td nowrap="nowrap"  align="right"><input class="button" type="button" value="合同录入" onclick="add()"></td>
					               				<!--<td nowrap="nowrap"><input type="button" class="button" value="反悔迁出" onclick="returnOutRoom()"></td>
					               				--><td nowrap="nowrap"><input type="button" class="button" value="编辑" onclick="edit()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="违约删除" onclick="delCompact()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="打印" onclick="print()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="explorReport()"></td>
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
	document.getElementById("status").value='${status}';
	if(document.getElementById("statusIn").value!=null&&document.getElementById("statusIn").value!=""){
		document.getElementById("status").value = document.getElementById("statusIn").value;
	}
	function print(){
		form1.action = "<%=path%>/destine.do?method=printBill";
  		form1.target = "_blank";
  		form1.submit();
	}
	
</script>
</html>
