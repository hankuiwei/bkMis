<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.zc13.util.Contants"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>入驻管理</title>
	
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
		
		function isNotice(){
			if('${notice}'==1){
				alert("通知入驻成功！请该客户尽快缴纳押金和预收款");
			}else if('${ruzhu}'==1){
				if(confirm("入驻成功！是否下发任务单？")){
					id = '${compactId}';
					window.open("<%=path%>/customer.do?method=downTask&compactId="+id);
				}
			}
		}
		
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
		function add(){
			var lpId = document.getElementById("lpId").value;
			window.location.href("customer.do?method=goAdd&lpId="+lpId);
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
					alert("该合同正在审批中");
					return;
				}else if(data=="通过审批"){
					alert("该合同已通过审批");
					return;
				}else{
					if(window.confirm("确定删除这条记录吗?")){
						window.location.href="<%=path%>/customer.do?method=delCompact&id="+id;
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
			document.form1.action = "<%=path%>/customer.do?method=getList";
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
					window.location.href="<%=path%>/customer.do?method=gotoEditCompact&id="+id;
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
		function explorReport1(){
			document.form1.action = "customer.do?method=explorReport1";
			document.form1.submit();
		}
		
		//选中或取消选中所作的动作
		function isSelect(obj,status){
			if(obj.checked){
				if(status=='<%=Contants.NOTNOTICE%>'||status==""||status==null){//如果状态是未通知入住
					document.getElementById("b1").disabled="";
					document.getElementById("b2").disabled="disabled";
					document.getElementById("b3").disabled="disabled";
				}
				else if(status=='<%=Contants.HAVENOTICE%>'){//如果状态是已通知入住
					document.getElementById("b1").disabled="disabled";
					document.getElementById("b2").disabled="";
					document.getElementById("b3").disabled="";
				}
				else if(status=='<%=Contants.HAVEIN%>'){//如果状态是已入住
					document.getElementById("b1").disabled="disabled";
					document.getElementById("b2").disabled="disabled";
					document.getElementById("b3").disabled="";
				}else{
					document.getElementById("b1").disabled="disabled";
					document.getElementById("b2").disabled="disabled";
					document.getElementById("b3").disabled="";
				}
			}else{
				document.getElementById("b1").disabled="";
				document.getElementById("b2").disabled="";
				document.getElementById("b3").disabled="";
			}
			
		}
	</script>
</head>
<body onload="isNotice()">
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
						<td width="165" nowrap="nowrap" class="form_line">入驻管理</td>
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
					            	
					            	<td height="10" nowrap="nowrap">住户名称：<input type="text" name="clientName" id="clientName" value="${clientName }">
					            	</td>
					            	<td>
					            		房间号：<input type="text" name="roomCode" value="${roomCode }">&nbsp;&nbsp;&nbsp;&nbsp;
					            	</td>
					            	<td height="10" nowrap="nowrap">合同到期时间：<input type="text" name="beginDate" value="${beginDate }" readonly onclick="WdatePicker();" class="Wdate">&nbsp;至&nbsp;<input type="text" name="endDate" value="${endDate }" readonly onclick="WdatePicker();" class="Wdate"></td>
					            </tr>
					            <tr>
					            	<td height="4"></td>
					         	</tr>
					            <tr>
					            	<td height="10"></td>
									<td height="10" nowrap="nowrap">入驻状态：
										<select name="isNotice" id="isNotice">
											<option value="" selected="selected">全部</option>
											<option value="<%=Contants.NOTNOTICE %>"><%=Contants.NOTNOTICE %></option>
											<option value="<%=Contants.HAVENOTICE %>"><%=Contants.HAVENOTICE %></option>
											<option value="<%=Contants.HAVEIN %>"><%=Contants.HAVEIN %></option>
											<option value="<%=Contants.HASRELET %>"><%=Contants.HASRELET %></option>
											<option value="<%=Contants.HASCHANGE %>"><%=Contants.HASCHANGE %></option>
											<option value="<%=Contants.HASAPPLYCHANGE %>"><%=Contants.HASAPPLYCHANGE %></option>
											<option value="<%=Contants.HASNOTICE %>"><%=Contants.HASNOTICE %></option>
											<option value="<%=Contants.HASGO %>"><%=Contants.HASGO %></option>
										</select>
									</td>
									<td>合同号：<input align="left" type="text" name="code" value="${code }"></td>
									<td align="center" nowrap="nowrap" >
					            		<input type="button" class="button" value="查询" onclick="selectAny()">
									</td>
								</tr>
							 	 <tr>
					           		 <td height="10" colspan="9"><br></td>
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
								                <th width="6%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户名称</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">合同号</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">联系电话</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</th>
												<!-- 根据2011年3月1号确认回来的需求，这里不再需要显示面积和房租
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">面积</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房租单价</th>  -->
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户类型</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">合同类型</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">合同开始日期</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">合同到期时间</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">入驻时间</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">入驻状态</th>
											</tr>
											<c:choose>
											<c:when test="${empty list}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${list}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="look(${v[0].id })">
											 	<td  nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" id="checkbox1" name="checkbox1" value="${v[0].id }" onclick="isSelect(this,'${v[0].isNotice }');" >
												</td>
								                <td  nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v[0].name }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v[0].code }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v[0].phone }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v[0].roomCodes }&nbsp;</td>
								                <!--<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v[0].totalArea }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v[1] }&nbsp;</td>  -->
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v[0].clientType }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v[0].type }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v[0].beginDate }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v[0].endDate }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v[0].inDate }&nbsp;</td>
								                
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">
								                <input type="hidden" id="clientId${tag.count-1 }" name="clientId" value="${v[0].clientId }">
								                <input type="hidden" id="isNotice${tag.count-1 }" name="isNotice" value="${v[0].isNotice }">
								                <input type="hidden" id="isIn${tag.count-1 }" name="isIn" value="${v[0].inDate }">
								                	${v[0].isNotice }
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
					               				<!--
					                			<td nowrap="nowrap"  align="right"><input class="button" type="button" value="入住" onclick="add()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="迁出" onclick="outRoom()"></td><td nowrap="nowrap"><input type="button" class="button" value="反悔迁出" onclick="returnOutRoom()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="编辑" onclick="edit()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="删除" onclick="delCompact()"></td>-->
												<td nowrap="nowrap"><input type="button" id="b1" class="button" value="通知入住" onclick="noticecome()"></td>
												<td nowrap="nowrap"><input type="button" id="b2" class="button" value="正式入住" onclick="income()"></td>
												<td nowrap="nowrap"><input type="button" id="b3" class="button" value="下发任务单" onclick="down()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="打印" onclick="print()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="explorReport1()"></td>
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
	
		document.getElementById("isNotice").value = '${isNotice}';
	
//	if(document.getElementById("lpIdIn").value!=null&&document.getElementById("lpIdIn").value!=""){
//		document.getElementById("lpId").value = document.getElementById("lpIdIn").value;
//	}
	function print(){
		form1.action = "<%=path%>/customer.do?method=printCustomerCompactList";
  		form1.target = "_blank";
  		form1.submit();
	}
	//下发任务单，其实就是打印
	function down(){
		if(!check("下发任务单的合同")){
			return;
		}
		var id = "";
		var arr = document.getElementsByName("checkbox1");
		var isNotice;//入住状态
		for(var i=0;i<arr.length;i++){
			if(arr[i].checked){
				id = arr[i].value;
				isNotice = trim(arr[i].parentElement.parentElement.lastChild.innerText);
			}
		}
		//if(isNotice=="<%=Contants.NOTNOTICE%>"||isNotice=="<%=Contants.HAVENOTICE%>"||isNotice==null||isNotice==""){
			//alert("该合同尚未正式入住，不能下发任务单！");
			//return;
		//}
		window.open("<%=path%>/customer.do?method=downTask&compactId="+id);
		
	}
	//通知入驻
	function noticecome(){
		if(!check("通知入住")){
				return;
		}
		var id = "";
		var arr = document.getElementsByName("checkbox1");
		for(var i=0;i<arr.length;i++){
			if(arr[i].checked){
				id = arr[i].value;
				var b  = document.getElementById("isNotice"+i);
				if(b!=null){
					isNotice = b.value;
				}
				var c  = document.getElementById("isIn"+i);
				if(c!=null){
					isin = c.value;
				}
			}
		}
		if(isin!=null && isin != ""){
			alert("该客户已经入驻！无须再次通知入驻");
			return;
		}else if(isNotice=="<%=Contants.HAVENOTICE%>"){
			alert("已经通知过入驻，无须再次通知！");
			return;
		}else{
			if(window.confirm("确定通知入住吗?")){
				window.location="<%=path%>/customer.do?method=notice&compactId="+id;
			}
		}
	}
	//正式入驻
	function income(){
		
		if(!check("正式入住")){
			return;
		}
		var id = "";
		var isNotice = "";
		var clientId = "";
		var arr = document.getElementsByName("checkbox1");
		var arr2 = document.getElementsByName("clientId");
		var isin = "";
		for(var i=0;i<arr.length;i++){
			if(arr[i].checked){
				id = arr[i].value;
				clientId = arr2[i].value;
				var b  = document.getElementById("isNotice"+i);
				if(b!=null){
					isNotice = b.value;
				}
				var c  = document.getElementById("isIn"+i);
				if(c!=null){
					isin = c.value;
				}
			}
		}
		if(isin!=null && isin != ""){
			alert("该客户已经入驻！无须再次入驻");
			return;
		}else if(isNotice=="<%=Contants.HAVENOTICE%>"){
			if(window.confirm("确定执行正式入住吗?")){
				document.form1.action = "<%=path%>/destine.do?method=inCome&compactId="+id;
				document.form1.submit();
			}
			
			//$.post("<%=path%>/workTask.do",{method:"checkIsDepositAndAdvance",clientId:clientId},function(data){
				//if(data=="0"){
					/*
					if(confirm("该客户仍有押金未缴纳，是否同意正式入驻？")){
						document.form1.action = "<%=path%>/destine.do?method=inCome&compactId="+id;
						document.form1.submit();
					}
					*/
					//alert("该客户有未缴的押金或预售房租，请缴纳完后再正式入住！");
				//}else if(window.confirm("确定执行正式入住吗?")){
					//document.form1.action = "<%=path%>/destine.do?method=inCome&compactId="+id;
					//document.form1.submit();
				//}
			//})
			
		}else{
			alert("请先通知该客户入住！");
			return;
		}
	}
</script>
</html>
