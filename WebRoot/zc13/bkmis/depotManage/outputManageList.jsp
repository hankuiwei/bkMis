<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<div id="loading">
	<table width=100% height=100%  cellspacing="0" cellpadding="0">
		<tr align="center" valign="middle">
			<td><img src="<%=path %>/resources/images/loading1.gif"  />
			<br />
			<span style="font: 14px;color:blue">正在分析数据，请稍候...</span></td>
		</tr>
	</table>
</div>
<html>
<head>
	<title>出库管理</title>
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/zc13/msmis/functionList/scripts/jquery.js"></script>
	<script type="text/javascript">
		//添加
		function addOutputAdd(){
			
			$.post("<%=path%>/inputdepot.do",{method:"checkIfDate"},function(date){
		
				if(date=="true"){
					alert("对不起，今天正在结算，无法进行出库操作！");
					return;
				}else{
					//window.open("<%=path%>/outputdepot.do?method=addOutputMaterials");
					window.showModalDialog("<%=path%>/outputdepot.do?method=addOutputMaterials",window,"dialogWidth=1000px;dialogHeight=800px;resizable=yes;center=1");
					document.outManageForm.submit();
				}
			})
		}
		//编辑
		function editOutputMaterials(){
			var itemIds = '';
			var count = 0;
			var chkx = document.getElementsByName("childBox");
			for(var i = 0;i<chkx.length;i++){
				if(chkx[i].checked == true){
					count++;
					itemIds = chkx[i].value;
				}
			}	
			if(count == 0){
				alert("请选择要编辑的记录!");
				return ;
			}else if(count == 1){	
				var status = document.getElementById(itemIds).innerHTML;
				var substatus = status.substring(0,3);
				if(substatus == '已结算'){
					alert("该记录已结算，不能再编辑！");
				}else{	
					//window.open("<%=path%>/outputmanage.do?method=editOutput&editId="+itemIds);
					var s = window.showModalDialog("<%=path%>/outputmanage.do?method=editOutput&editId="+itemIds,window,"dialogWidth=1000px;dialogHeight=800px;resizable=yes;center=1");
					if(s == "flag"){
						window.location.href("outputdepot.do?method=showOutputMaterialsList");
					}
				}
			}else{
				alert("只能选择一条编辑的记录!");
				return ;
			}
		}
		//复选框的全选事件
		function selectedAll(){
			var parentBox = document.getElementById("parentBox");
			var childBox = document.getElementsByName("childbox");
			if(parentBox.checked == true){
				for(var i = 0;i<childBox.length;i++){
					childBox[i].checked = true;
				}
			}else{
				for(var i = 0;i<childBox.length;i++){
					childBox[i].checked = false;
				}
			}
		}
		
		//删除信息
		function delOutput(){
			
			var itemIds = '';
			var count = 0;
			var chkx = document.getElementsByName("childbox");
			for(var i = 0;i<chkx.length;i++){
				if(chkx[i].checked == true){
					itemIds +=chkx[i].value + ',';
				}
			}
			if(itemIds!=null&&itemIds!=""){
				itemIds = itemIds.substring(0,itemIds.length-1);
			}
			if(itemIds.length>0){
			  var itemStatus = itemIds.split(',');
			  for(var i = 0;i<itemStatus.length;i++){
			  	var status = document.getElementById(itemStatus[i]).innerHTML;
			  	var substatus = status.substring(0,3);
				if(substatus == '已结算'){
				  	count = '1';
				  	break;
				}
			  }
			  if(count == '1'){	
			  	alert("已结算的记录不能被删除，请重新选择！"); 
			  }else{
					if(!window.confirm("您确定要删除吗?")) {return ;}
					//itemIds = itemIds.slice(0,itemIds.length-1);
					document.outManageForm.action = "outputmanage.do?method=doDelOutput&inOutputIds="+itemIds;
					document.outManageForm.submit();
			   }
			}else{
				alert("请选择要删除的记录!");
			}
		}
		//查询
		function searchOutput(){
			document.outManageForm.action="<%=path%>/outputdepot.do?method=showOutputMaterialsList";
			document.outManageForm.submit();
		}
		//结算
		function acountOutput(){
			var itemIds = '';
			var statusId = '';
			var count = 0;
			var chkx = document.getElementsByName("childbox");
			for(var i = 0;i<chkx.length;i++){
				if(chkx[i].checked == true){
					itemIds +=chkx[i].value + ',';
					statusId += chkx[i].value + ',';
				}
			}
			if(itemIds.length>0){
			   var itemStatus = statusId.split(',');
			   for(var i = 0;i<itemStatus.length;i++){
				   var status = document.getElementById(itemStatus[i]).innerHTML;
				   var substatus = status.substring(0,3);
				   if(substatus == "已结算"){
					  	count = '1';
				   }else{
				   		count = '0';
				   }
				 break ;
			  }
			  if(count == '1'){
			  	  alert("记录已经结算，请重新选择！");
			  }else{
					if(!window.confirm("您确定要结算吗？结算后将不能编辑该信息！")) {return ;}
					itemIds = itemIds.slice(0,itemIds.length-1);
					document.outManageForm.action = "outputmanage.do?method=doAccountOutput&accountIds="+itemIds;
					document.outManageForm.submit();
				}
			}else{
				alert("请选择要结算的记录!");
			}
		}
		//查看出库单信息
		function viewOutput(id){
			document.outManageForm.action ="outputmanage.do?method=doViewOutput&editId="+id;
			document.outManageForm.submit();
		}
		//导出报表
		function explortReport(){
			document.outManageForm.action = "outputdepot.do?method=exportOutputExcel";
			document.outManageForm.submit();
		}
	</script>
</head>
<body onLoad="hideLoadingDiv();">
<form  method="post" name="outManageForm">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">出库管理单列表</td>
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
					            	<td align="left" nowrap="nowrap">出库编号：</td>
					            	<td nowrap="nowrap"><input type="text" name="outputCode" id="outputCode" value="${materialsForm.outputCode }"/></td>
									<td align = "left" nowrap="nowrap">出库时间：
										<input type="text" name="startDate" id="startDate" readonly onclick="WdatePicker()" value="${materialsForm.startDate }" class="Wdate"/>
										&nbsp;至&nbsp;
										<input type="text" name="endDate" id="endDate" readonly onclick="WdatePicker()" value="${materialsForm.endDate }" class="Wdate"/>
									</td>
									<td align="right" nowrap="nowrap">
						            	<input type="button" id = "focuson" class="button" value="确定" onclick="searchOutput();">
									</td>
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
								                <th class = "RptTableHeadCellFullLock"><input type="checkbox" id="parentBox" name="parentBox" onclick="selectedAll();"></th>
												<th class = "RptTableHeadCellFullLock">序号</th>
												<th class = "RptTableHeadCellFullLock">出库单编号</th>
												<th class = "RptTableHeadCellLock">出库时间</th>
												<th class = "RptTableHeadCellLock">领用人员</th>
												<th class = "RptTableHeadCellLock">领用部门</th>
												
												<th class = "RptTableHeadCellLock">金额</th>
												
												<th class = "RptTableHeadCellLock">状态</th>
											</tr>
											<c:choose>
											<c:when test="${empty materialsForm.outputList}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有相应的出库单信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${materialsForm.outputList}" var="ml" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="viewOutput('${ml.id}')">
														<td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="childbox" name="childbox" value="${ml.id}"></td>
														<td class="RptTableBodyCellLock" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
														<td class="RptTableBodyCellLock" align="center" title="${ml.outputCode }">${ml.outputCode }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${ml.outputDate }">${ml.outputDate }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${ml.man }">${ml.man }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${ml.codeName}">${ml.codeName }&nbsp;</td>
														
														<td class="RptTableBodyCell" align="center" title="${ml.money }"><script>document.write(formatNum(parseFloat(${ml.money }).toFixed(2).toString()));</script>&nbsp;</td>
														
														<td class="RptTableBodyCell" align="center" title="${ml.status }" id="${ml.id}">${ml.status }&nbsp;</td>
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
								<tr>
									<td>
										<c:forEach items="${summaryList}" var="total">
											出库总金额：<script>document.write(formatNum(parseFloat(${total.totalMoney }).toFixed(2).toString()));</script>
										</c:forEach>
									</td>
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
					                			<td nowrap="nowrap"  align="right"><input class="button" onclick="addOutputAdd();" type="button" value="添加"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" onclick="editOutputMaterials()" value="编辑" id="edit"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="删除" onclick="delOutput()"></td>
					               				<%--<td nowrap="nowrap"><input type="button" class="button" value="保存" onclick="acountOutput();"/></td>--%>
					               				<c:choose>
					               				<c:when test="${empty materialsForm.outputList}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="alert('无记录,不能导出报表!')"></td>
					               				</c:when> 
					               				<c:otherwise>
					               				<td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="explortReport()"></td>
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
