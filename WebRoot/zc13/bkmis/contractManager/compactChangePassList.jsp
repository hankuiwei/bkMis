<%@ page language="java"   pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.Contants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>变更合同审批</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
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
		function return1(str){
			if(!check("审批")){
				return;
			}
			var compactId = "";
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					compactId = arr[i].parentElement.getElementsByTagName("input")[1].value;
					id = arr[i].value;
				}
			}
			var flag = "通过审批";
			if(str=="0"){
				flag = "未通过审批";
			}
			if(window.confirm("确定"+flag+"吗?")){
				window.location.href("<%=path%>/compact.do?method=noapplyChangeOrRelet&type=change&flag="+str+"&compactId="+compactId+"&id="+id);
			}
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
		function edit(compactChangeId){
			window.open("<%=path%>/customer.do?method=gotoEditCompact&flag=lookChange&id="+compactChangeId);
		}
		function selectAny(){
			document.form1.action = "compact.do?method=getPassList";
			document.form1.submit();
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
						<td width="165" nowrap="nowrap" class="form_line">变更合同审批</td>
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
					            	<td height="10" nowrap="nowrap">住户名称：<input type="text" name="clientName" id="clientName" value="${compactManagerForm.clientName }"></td>
					            	<td height="10" nowrap="nowrap">合同类型：<input type="hidden" name="typeIn" id="typeIn" value="${compactManagerForm.type }">
										<select name="type" id="type" onchange="selectAny()">
											<option value="<%=Contants.INTENTION %>"><%=Contants.INTENTION %></option>
											<option value="<%=Contants.COMPACT %>"><%=Contants.COMPACT %></option>
											<option value="<%=Contants.COMPACTRELET %>"><%=Contants.COMPACTRELET %></option>
											<option value="<%=Contants.COMPACTCHANGE %>" selected="selected"><%=Contants.COMPACTCHANGE %></option>
											<!-- <option value="退租合同">退租合同</option> -->
										</select>
									</td>
					            	<td height="10" nowrap="nowrap">签订时间：<input type="text" name="beginDate" value="${compactManagerForm.beginDate }" readonly onclick="WdatePicker();" class="Wdate">&nbsp;至&nbsp;<input type="text" name="endDate" value="${compactManagerForm.endDate }" readonly onclick="WdatePicker();" class="Wdate"></td>
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
							<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed">
								<tr height = "95%">
					        		<td width="100%">
					        		<div id = "div1" class="RptDiv">
							   			<table border="0" cellpadding="0" cellspacing="0" class="RptTable">
					              			<tr>
								                <th width=""  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this)">
								                </th>
								                <th width="5%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
												<th width="15%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户名称</th>
												<th width="15%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">经办人</th>
												<th width="15%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">旧合同编号</th>
												<th width="15%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">新合同编号</th>
												<th width="15%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">申请日期</th>
												<th width="15%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">审批状态</th>
											</tr>
											<c:choose>
											<c:when test="${empty compactManagerForm.compactChanges}">
											<tr align="center">
												<td colspan="8" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${compactManagerForm.compactChanges}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="edit(${v.id })">
											 	<td width="" nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" id="checkbox1" name="checkbox1" value="${v.id }">
											 		<input type="hidden" id="" name="" value="${v.compactByNewId.id }">
												</td>
								                <td nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count }</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.clientName }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.theMan }&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.oldCode }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.newCode }&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.applyDate }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.status }&nbsp;</td>
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
			     					<td align="right" colspan="4" class="head_left">
			     						<input type="button" value="通过审批" class="button" onclick="return1('1')">
			     						<input type="button" value="未通过审批" class="button" onclick="return1('0')">
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
	//	if(document.getElementById("typeIn").value!=null&&document.getElementById("typeIn").value!=""){
	//		document.getElementById("type").value = document.getElementById("typeIn").value;
	//	}
	</script>
</html>
