<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>客户列表</title>
	<c:if test="${compactRoomForm.forward=='sendMessage'}">
		<base target="_self" />
	</c:if>
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
		function selectAny(){
			document.form1.action = "customer.do?method=getClientList";
			document.form1.submit();
		}
		var k;
		var ids;
		function edit(){
			if(!check("编辑")){
				return;
			}
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					ids = arr[i].value;
				}
			}
			var url = "<%=path%>/customer.do?method=getClientById&id="+ids;
			var options = "dialogWidth:700px;dialogHeight:500px;status:no;scroll:no;";
			var win = window.showModalDialog(url, this.window, options);
			if(win == "flag"){
				alert("修改成功！");
				window.location.href("<%=path%>/customer.do?method=getClientList");
			}
		}
		function del(){
			k = 0;
			ids = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					k++;
					ids += arr[i].value + ",";
				}
			}
			ids = ids.substr(0,ids.length-1);
			if(k == 0){
				alert("请选择要删除的内容");
				return;
			}
			$.post("<%=path%>/customer.do",{method:"checkClient",ids:ids},function(data){
				if(data!=""&&data!=null){
					var arr1 = data.split(",");
					var arr = document.getElementsByName("checkbox1");
					var data1 = "";
					for(var i=0;i<arr1.length;i++){
						for(var j=0;j<arr.length;j++){
							if(arr[j].value==arr1[i]){
								var tr = arr[j].parentElement.parentElement;
								var tds = tr.getElementsByTagName("td");
								data1 += trim(tds[3].innerText) + ",";
							}
						}
					}
					data1 = data1.substr(0,data1.length-1);
					alert(data1+"客户的合同在有效期内,不能删除");
					return;
				}else{
				 	if(window.confirm("确定删除"+k+"条记录吗")){
						document.form1.action = "customer.do?method=delClientById&ids="+ids;
						document.form1.submit();
					}
				}
			})	
		}
		function check(aa){
			k = 0;
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					k++;
				}
			}
			if(k==0){
				alert("请选择要"+aa+"的记录");
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
			document.form1.action = "customer.do?method=explorReport";
			document.form1.submit();
		}
	</script>
</head>
<body>
<jsp:include page="../../../loading.jsp"></jsp:include>
<form name = "form1" method="post">
	<input type="hidden" name="forward" value="${compactRoomForm.forward }">
	<input type="hidden" name="cusName" value="${cusName }">
	<input type="hidden" name="cusPhone" value="${cusPhone }">
	<input type="hidden" name="cusId" value="${cusId }">
	
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">客户列表</td>
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
									<td height="10" colspan="2" nowrap="nowrap">客户名称：<input type="text" name="clientName" value="${clientName }"></td>
									<td height="10" nowrap="nowrap">注册时间：<input type="text" name="loginDate" id="loginDate" value="${compactRoomForm.loginDate }"  readonly onclick="WdatePicker()" class="Wdate"/>
										&nbsp;至&nbsp;
										<input type="text" name="loginDateEnd" id="loginDateEnd" value="${compactRoomForm.loginDateEnd }"  readonly onclick="WdatePicker()" class="Wdate"/>
									</td>
					            	
									<td align="right" nowrap="nowrap">
					            		<input type="button" class="button" value="查询" onclick="selectAny()">
									</td>
								</tr>
								<tr>
									<td></td>
					            	<td height="10" nowrap="nowrap">客户类别：<input type="hidden" name="clientTypeIn" id="clientTypeIn" value="${clientType }">
					            		<select name="clientType" id="clientType">
						            		<option value="">请选择</option>
						            		<option value="个人">个人</option>
						            		<option value="单位">单位</option>
						            	</select>
					            	</td>
					            	<td height="10" nowrap="nowrap">是否高新：<input type="hidden" name="isHighTechIn" id="isHighTechIn" value="${compactRoomForm.isHighTech }">
					            		<select name="isHighTech" id="isHighTech">
					            			<option value="">请选择</option>
					            			<option value="是">是</option>
					            			<option value="否">否</option>
					            		</select>
					            	</td>
									<td height="10" colspan="3" nowrap="nowrap">注册资金：<input type="text" onKeyUp="this.value=this.value.replace(/\D/g,'')" name="loginFund"  id="loginFund" value="${compactRoomForm.loginFund}" />
										&nbsp;至&nbsp;
										<input type="text" name="loginFundEnd" id="loginFundEnd" value="${compactRoomForm.loginFundEnd }" />（元）
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
					    <td valign="top">
					    	<!-- 表单内容区域 -->
							<table width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed">
								<tr height = "95%">
					        		<td width="100%">
					        		<div id = "div1" class="RptDiv">
							   			<table border="0" cellpadding="0" cellspacing="0" class = "RptTable">
					              			<tr>
								                <th width=""  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this)">
								                </th>
								                <th width="7%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户代码</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户名称</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">证件号</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">联系人</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">联系电话</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户类别</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">是否高新技术企业</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">注册时间</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">注册资金</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">单位全称</th>
											</tr>
											<c:choose>
											<c:when test="${empty list}">
											<tr align="center">
												<td colspan="12" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${list}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
											 	<td nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" id="checkbox1" name="checkbox1" value="${v.id }" />
											 		<input type="hidden" value="${v.name }">
											 		<input type="hidden" value="${v.phone }">
												</td>
								                <td nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count }</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.code }&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.name }&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.identityCard }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.linkMan }&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.phone }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.clientType }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.isHighTech }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.loginDate }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.loginFund }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.unitName }&nbsp;</td>
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
												<td class="form_line3" colspan="8">
													<c:choose>
														<c:when test="${compactRoomForm.forward!='sendMessage'}">
															${pagination }
														</c:when>
														<c:otherwise>
															${fn:replace(pagination, "document.forms[0].setAttribute('target','_self');","") }
														</c:otherwise>
													</c:choose>
												</td>
												<td class="form_line3">&nbsp;</td>
											</tr>
										</table>
									</td>
								</tr>
								<!-- 分页end -->
								<tr>
									<td align="right">
										<table>
										<c:choose>
											<c:when test="${compactRoomForm.forward=='sendMessage'}">
												<tr>
													<td nowrap="nowrap"><input type="button" class="button" value="确定" onclick="sendMessage()"></td>
												</tr>
											</c:when>
											<c:otherwise>
												<tr>
						               				<td nowrap="nowrap"><input type="button" class="button" value="删除" onclick="del()"></td>
						               				<td nowrap="nowrap"><input type="button" class="button" value="编辑" onclick="edit()"></td>
						               				<td nowrap="nowrap"><input type="button" class="button" value="打印" onclick="print()"></td>
						               				<td nowrap="nowrap" ><input type="button" class="button" value="导出报表" onclick="explorReport()"></td>
						               				<td nowrap="nowrap" ><input type="button" class="button" value="居住历史查询" onclick="history()"></td>
						              			</tr>
											</c:otherwise>
										</c:choose>
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
		//这个js用意何在呢？光看是看不出来的。服了，我后来仔细的通过自己实现某个东西发现，这个js就是为了让当前页面默认选中刚才选则的查询条件的
		
		document.getElementById("clientType").value = document.getElementById("clientTypeIn").value;
		if(document.getElementById("clientTypeIn").value==null||document.getElementById("clientTypeIn").value==""){
			document.getElementById("clientType").value  = "";
		}
		document.getElementById("isHighTech").value = document.getElementById("isHighTechIn").value;
		if(document.getElementById("isHighTechIn").value==null||document.getElementById("isHighTechIn").value==""){
			document.getElementById("isHighTech").value  = "";
		}
		
	function print(){
		form1.action = "<%=path%>/customer.do?method=printCustomerList";
  		form1.target = "_blank";
  		form1.submit();
	}
	function history(){
	
		if(!check("查询")){
			return;
		}
		var arr = document.getElementsByName("checkbox1");
		for(var i=0;i<arr.length;i++){
			if(arr[i].checked){
				ids = arr[i].value;
			}
		}
		window.open("<%=path%>/customer.do?method=getClientsHistoryRooms&clientId="+ids);
	}
	
	//群发短讯使用
	function sendMessage() {
		var cusId = '';
		var cusName = '';
		var cusPhone = '';
		var items = $("input[name='checkbox1']:checked");
		if(items.length==0) {
			alert("请选择客户发送消息！");
			return;
		}
		
		items.each(function(i) {
			cusId += $(this).val() + ";";
			cusName += $(this).next().val() + ";";
			cusPhone += $(this).nextAll().eq(1).val() + ";";
		})
		
		var selInfo = {
			cusId : cusId,
			cusName : cusName,
			cusPhone : cusPhone
		}
		
		window.returnValue = selInfo;
		window.close();
	} 
	
	function initCusId(item) {
		var cusId = $("input:hidden[name='cusId']").val();
		var cusName = $("input:hidden[name='cusName']").val();
		var cusPhone = $("input:hidden[name='cusPhone']").val();
		
		if(item.checked) {
		
		} else {
		
		}
	}
	</script>
</html>
