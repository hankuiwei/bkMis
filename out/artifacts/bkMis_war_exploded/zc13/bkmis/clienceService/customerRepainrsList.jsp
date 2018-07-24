<%@ page language="java" import="java.util.*,com.zc13.util.*"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>客户报修</title>
	
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
		function openDeal(id){
			document.form1.action="client.do?method=getById&id="+id+"&forward=openDeal";
			document.form1.submit();
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
			var url = "<%=path%>/client.do?method=gotoAdd";
			var options = "dialogWidth:600px;dialogHeight:400px;status:no;scroll:no;";
			var win = window.showModalDialog(url, this.window, options);
			if(win=="flag"){
				//可能是由于编辑页面已经提交过一次，所有如果用下面的语句，就会丢失查询条件
				//window.location = window.location;
				query();
			}
		}
		var k;
		//编辑
		function edit(){
			if(!check("编辑")){
				return;
			}
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = id + arr[i].value;
					$.post("<%=path%>/client.do",{method:"getRepairAndDispatchStatus",id:id},function(data){
						var arrs = data.split(",");
						if(arrs[0]=="<%=Contants.DISPATCHING_WAIT%>"&&arrs[1]=="1"){
							var url = "client.do?method=getById&id="+id+"&forword=edit";
							var options = "dialogWidth:600px;dialogHeight:400px;status:no;scroll:no;";
							var win = window.showModalDialog(url, this.window, options);
							if(win=="flag"){
								//可能是由于编辑页面已经提交过一次，所有如果用下面的语句，就会丢失查询条件
								//window.location = window.location;
								query();
							}
						}else{
							alert("该报修记录已派工，不能进行修改！");
						}
					});
				}
			}
			
		}
		
		//派工
		function dispatch(){
			if(!check("派工")){
				return;
			}
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = id + arr[i].value;
					$.post("<%=path%>/client.do",{method:"getRepairAndDispatchStatus",id:id},function(data){
						var arrs = data.split(",");
						if((arrs[0]=="<%=Contants.DISPATCHING_WAIT%>"||arrs[0]=="<%=Contants.REPAIR_WAIT%>"||arrs[2]=="<%=Contants.IS_DISPATCH%>")&&arrs[1]=="1"){
							document.form1.action="<%=path%>/client.do?method=getById&id="+id+"&forward=editWorkSituation";
							document.form1.submit();
						}else{
							alert("请确认该客户报修尚未派工或工人尚未到场！");
						}
					});
				}
			}
		}
		
		//客户反馈
		function feedback(){
			if(!check("客户反馈")){
				return;
			}
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = id + arr[i].value;
					$.post("<%=path%>/client.do",{method:"getRepairAndDispatchStatus",id:id},function(data){
						var arrs = data.split(",");
						if(arrs[0]=="<%=Contants.REPAIR_COMPLETED%>"&&arrs[1]=="1"){
							document.form1.action="client.do?method=getById&id="+id+"&forward=feedback";
							document.form1.submit();
						}else{
							alert("客户信息尚未维修结束，不能进行反馈！");
						}
					});
				}
			}
		}
		
		function check(a){
			k = 0;
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					k++;
				}
			}
			if(k==0){
				alert("请选择要"+a+"的内容");
				return false;
			}
			if(k>1){
				alert("最多只能选择一条记录");
				return false;
			}
			return true;
		}
		
		//处理
		function deal(){
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = id + arr[i].value;
					$.post("<%=path%>/client.do",{method:"getRepairAndDispatchStatus",id:id},function(data){
						var arrs = data.split(",");
						if(arrs[0]=="<%=Contants.BEING_REPAIRED%>"&&arrs[2]=="<%=Contants.PERSONNEL_IS_REACH%>"&&arrs[1]=="1"){
							document.form1.action="client.do?method=getById&id="+id+"&forward=deal";
							document.form1.submit();
						}else{
							alert("请确认该客户报修处于维修中，并且工人已到场！");
						}
					});
				}
			}
		
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
				document.form1.action = "client.do?method=delById&ids="+trim(str);
				document.form1.submit();
			}
		}
		
		//到场
		function reach(){
			if(!check("到场")){
				return;
			}
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = id + arr[i].value;
					$.post("<%=path%>/client.do",{method:"getRepairAndDispatchStatus",id:id},function(data){
						var arrs = data.split(",");
						if(arrs[0]=="<%=Contants.BEING_REPAIRED%>"&&arrs[2]=="<%=Contants.IS_DISPATCH%>"&&arrs[1]=="1"){
							var options = "dialogWidth:300px;dialogHeight:300px;status:no;scroll:no;";
							var url = "<%=path%>/zc13/bkmis/clienceService/setReachTime.jsp?id="+id;
							//window.open(url);
							var returnVal = window.showModalDialog(url,this.window, options);
							if(returnVal=="flag"){
								query();
							}
						}else{
							alert("请确定人员已派出并且尚未到场！");	
						}
					});
				}
			}
		}
		
		//离场
		function leave(){
			if(!check("离场")){
				return;
			}
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = id + arr[i].value;
					var appearanceTime = arr[i].appTime;//nextSibling.value;
					$.post("<%=path%>/client.do",{method:"getRepairAndDispatchStatus",id:id},function(data){
						var arrs = data.split(",");
						if((arrs[0]=="<%=Contants.REPAIR_WAIT%>"||(arrs[0]=="<%=Contants.BEING_REPAIRED%>"&&arrs[2]=="<%=Contants.PERSONNEL_IS_REACH%>"))&&arrs[1]=="1"){
							var options = "dialogWidth:300px;dialogHeight:320px;status:no;scroll:no;";
							var url = "<%=path%>/zc13/bkmis/clienceService/setLeaveTime.jsp?id="+id+"&appearanceTime="+appearanceTime;
							var returnVal = window.showModalDialog(url,this.window, options);
							if(returnVal=="flag"){
								query();
							}
						}else{
							alert("请确定派工人员已到场并且尚未离场！");
						}
					});
				}
			}
		}
		
		//置为无效
		function setInvalid(){
			if(window.confirm("您确定要置为无效吗？")){
				if(!check("置为无效")){
					return;
				}
				var id = "";
				var arr = document.getElementsByName("checkbox1");
				for(var i=0;i<arr.length;i++){
					if(arr[i].checked){
						id = id + arr[i].value;
						$.post("<%=path%>/client.do",{method:"getRepairAndDispatchStatus",id:id},function(data){
							var arrs = data.split(",");
							if(arrs[0]!="<%=Contants.REPAIR_COMPLETED%>"&&arrs[1]=="1"){
								$.post("<%=path%>/client.do",{method:"setInvalid",id:id},function(data){
									if(data=="1"){
										alert("操作成功");
										query();
									}else{
										alert("操作失败");
									}
								});
							}else if(arrs[0]=="<%=Contants.REPAIR_COMPLETED%>"){
								alert("维修结束的报修不能置为无效！");
							}else{
								alert("该维修记录已经置为无效！");
							}
						});
					}
				}
			}
		}
		
		function query(){
			document.form1.action="<%=path %>/client.do?method=getList";
			document.form1.submit();
		}
		
		function printPGD(){
			if(!check("打印派工单")){
				return;
			}
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = id + arr[i].value;
				}
			}
			window.open("<%=path %>/client.do?method=getById&forward=printPGD&id="+id);
			//window.open('<%=path %>/zc13/bkmis/clienceService/pgdPrint.jsp');
		}
		
		//导出报表
		function explorReport(){
			document.form1.action="client.do?method=explorClient";
			document.form1.submit();
		}
	</script>
	<c:if test="${isPrint eq '1'}">
	<script type="text/javascript">
		window.open("client.do?method=getById&forward=printPGD&id=${SerClientMaintainForm.id}");
	</script>
	</c:if>
</head>
<body>
<form name = "form1" method="post">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5">
    			<input type="hidden" id="flag" name="flag" value="${SerClientMaintainForm.flag }">
    		</td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">客户报修列表</td>
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
					            	<td height="10" nowrap>楼幢：
					            		<select id="cx_buildId" name="cx_buildId" style="width: 130">
											<option value="" selected="selected">请选择</option>
										<c:choose>
										<c:when test="${empty list2}">
										</c:when>
										<c:otherwise>
										<c:forEach items="${list2}" var="v">
											<option value="${v.buildId }" <c:if test="${SerClientMaintainForm.cx_buildId eq v.buildId }">selected</c:if> >${v.buildName }</option>
										</c:forEach>
										</c:otherwise>
										</c:choose>
										</select>
					            	</td>
					            	<td height="10" nowrap>派工人：
					            		<select id="cx_sendedMan" name="cx_sendedMan" style="width: 130">
											<option value="" selected="selected">-请选择-</option>								
											<c:forEach items="${list3}" var="v">
												<option value="${v.personnelId }" <c:if test="${SerClientMaintainForm.cx_sendedMan eq v.personnelId}">selected</c:if>>${v.personnelName }</option>
											</c:forEach>									
										</select>
										&nbsp;&nbsp;是否有效：
										<select id="cx_isEnabled" name="cx_isEnabled">
											<option value="" selected="selected">-全部-</option>								
											<option value="1" <c:if test="${SerClientMaintainForm.cx_isEnabled eq '1'}">selected</c:if>>有效</option>							
											<option value="0" <c:if test="${SerClientMaintainForm.cx_isEnabled eq '0'}">selected</c:if>>无效</option>							
										</select>
					            	</td>
					            	<td height="10"></td>		            	
					            </tr>
					            <tr>
					            	<td height="3"></td>
					         	</tr>
					            <tr>
					            	<td height="10"></td>
					            	<td height="10" nowrap>报修状态：
					            		<select name="selstatus" id="selstatus">
					            			<option value="" selected="selected">全部</option>	
					            			<option value="<%=Contants.DISPATCHING_WAIT %>,<%=Contants.BEING_REPAIRED %>,<%=Contants.REPAIR_WAIT %>" <c:if test="${SerClientMaintainForm.selstatus eq '派工等待,维修中,维修等待' }">selected</c:if> ><%=Contants.DISPATCHING_WAIT %>,<%=Contants.BEING_REPAIRED %>,<%=Contants.REPAIR_WAIT %></option>				            		
					            			<option value="<%=Contants.DISPATCHING_WAIT %>,<%=Contants.REPAIR_WAIT %>" <c:if test="${SerClientMaintainForm.selstatus eq '派工等待,维修等待' }">selected</c:if> ><%=Contants.DISPATCHING_WAIT %>,<%=Contants.REPAIR_WAIT %></option>	
					            			<option value="<%=Contants.DISPATCHING_WAIT %>" <c:if test="${SerClientMaintainForm.selstatus eq '派工等待' }">selected</c:if> ><%=Contants.DISPATCHING_WAIT %></option>
					            			<option value="<%=Contants.BEING_REPAIRED %>" <c:if test="${SerClientMaintainForm.selstatus eq '维修中' }">selected</c:if> ><%=Contants.BEING_REPAIRED %></option>
					            			<option value="<%=Contants.REPAIR_WAIT %>" <c:if test="${SerClientMaintainForm.selstatus eq '维修等待' }">selected</c:if> ><%=Contants.REPAIR_WAIT %></option>
					            			<option value="<%=Contants.REPAIR_COMPLETED %>" <c:if test="${SerClientMaintainForm.selstatus eq '维修结束' }">selected</c:if> ><%=Contants.REPAIR_COMPLETED %></option>
					            		</select>
					            	</td>					       
					            	<td height="10" colspan="2" nowrap>报修时间：<input type="text" name="begindate" value="${SerClientMaintainForm.begindate }" readonly onclick="WdatePicker();" class="Wdate" >&nbsp;至&nbsp;<input type="text" id="enddate" name="enddate" value="${SerClientMaintainForm.enddate }" readonly onclick="WdatePicker();" class="Wdate"></td>
									<td align="right">
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
								                <th width="6%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this)">
								                </th>
								                <th width="6%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
								                <th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">派工单号</th>
								                <th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">报修项目名称</th>
								                <th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">报修时间</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">报修人</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">联系电话</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">接听(接待)人</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">维修人</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">报修内容</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">报修状态</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">是否有效</th>
											</tr>
											<c:choose>
											<c:when test="${empty list}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的客户报修记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${list}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" onDblClick="openDeal(${v.id})">
											 	<td width="6%" nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" id="checkbox1" name="checkbox1" value="${v.id}" appTime="${v.appearanceTime}" />
											 		<input type="hidden" name="appearanceTime" value="${v.appearanceTime}">
												</td>
								                <td width="6%" nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count}</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.sendcardCode}&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.project}&nbsp;</td>
												<td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">
												&nbsp;
												
												<c:choose>
													<c:when test="${empty list4}">
													</c:when>
													<c:otherwise>
														<c:forEach items="${list4}" var="c">
															<c:if test="${c.roomId eq v.roomId}">${c.roomCode }</c:if>
														</c:forEach>
													</c:otherwise>
												</c:choose>
												${v.area}
												</td>
												<td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.date}&nbsp;</td>
												<td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.name}&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.phone}&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.clerk}&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.sendedMan}&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center" title="${v.contentExplain}"><div class="titletest" style="width:100px">${v.contentExplain}&nbsp;</div></td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.status}&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">
									                <c:if test="${v.isEnabled eq '1'}">有效</c:if>
									                <c:if test="${v.isEnabled ne '1'}">无效</c:if>
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
										<c:if test="${flag}">
					    					<script type="text/javascript">
					    						window.location.href="client.do?method=getList";
					    					</script>
					    				</c:if>
									</td>
								</tr>
								<!-- 分页end -->
								<tr>
									<td align="right">
										<table>
											<tr>
												<c:if test="${fn:contains(SerClientMaintainForm.flag,',add,')}">
					                			<td nowrap="nowrap"  align="right"><input class="button" type="button" value="添加" onclick="add()"></td>
					               				</c:if>
					               				<c:if test="${fn:contains(SerClientMaintainForm.flag,',del,')}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="删除" onclick="del()"></td>
					               				</c:if>
					               				<c:if test="${fn:contains(SerClientMaintainForm.flag,',edit,')}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="编辑" onclick="edit()"></td>
					               				</c:if>
					               				<c:if test="${fn:contains(SerClientMaintainForm.flag,',dispatch,')}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="派工" onclick="dispatch()"></td>
					               				</c:if>
					               				<c:if test="${fn:contains(SerClientMaintainForm.flag,',reach,')}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="到场" onclick="reach()"></td>
					               				</c:if>
					               				<c:if test="${fn:contains(SerClientMaintainForm.flag,',deal,')}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="处理" onclick="deal()"></td>
					               				</c:if>
					               				<c:if test="${fn:contains(SerClientMaintainForm.flag,',leave,')}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="离场" onclick="leave()"></td>
					               				</c:if>
					               				<c:if test="${fn:contains(SerClientMaintainForm.flag,',feedback,')}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="住户反馈" onclick="feedback()"></td>
					               				</c:if>
					               				<c:if test="${fn:contains(SerClientMaintainForm.flag,',setInvalid,')}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="置为无效" onclick="setInvalid()"></td>
					               				</c:if>
					               				<c:choose>
					               				<c:when test="${empty list}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="alert('无记录,不能导出报表!')"></td>
					               				</c:when>
					               				<c:otherwise>
					               				<td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="explorReport()"></td>
					               				</c:otherwise>
					               				</c:choose>
					               				<c:if test="${fn:contains(SerClientMaintainForm.flag,',print,')}">
					               				<td nowrap="nowrap"><input type="button" class="button" onclick="javascript:printPGD();" value="打印派工单"></td>
					              				</c:if>
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
