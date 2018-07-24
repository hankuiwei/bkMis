<%@ page language="java"   pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.Contants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>合同续租情况列表</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/util.js" charset="UTF-8"></script>
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
		var k;
		function add(){
			window.open("compact.do?method=getList&flag=relet");
		}
		function edit(){
			if(!check("编辑")){
				return;
			}
			
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = arr[i].parentElement.getElementsByTagName("input")[1].value;
					var status = trim(arr[i].parentNode.parentNode.lastChild.innerText);
					if(status != "<%=Contants.NOTSUBMIT%>" && status!="<%=Contants.NOTTHROUGH%>"){
						alert("请选择未提交或未通过审批的合同！");
						return;
					}
				}
			}
			window.open("<%=path%>/customer.do?method=gotoEditCompact&flag=changeCompact&id="+id);
		}
		
		//删除
		function del(){
			if(!window.confirm("您确定要删除吗？")){
				return;
			}
			k = 0;
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					k++;
				}
			}
			if(k==0){
				alert("请选择要删除的内容");
				return false;
			}
			var ids = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					ids += arr[i].parentElement.getElementsByTagName("input")[1].value+",";
					var status = trim(arr[i].parentNode.parentNode.lastChild.innerText);
					if(status != "<%=Contants.NOTSUBMIT%>" && status!="<%=Contants.NOTTHROUGH%>"){
						alert("请选择未提交或未通过审批的合同！");
						return;
					}
				}
			}
			ids = ids.substring(0,ids.length-1);
			document.form1.action="<%=path%>/compact.do?method=deleteCompact&forward=toContractList&ids="+ids;
			document.form1.submit();
		}
		
		function look(id){
			//window.open("<%=path%>/customer.do?method=gotoEditCompact&flag=changeCompact&id="+id);
			window.open("<%=path%>/customer.do?method=gotoLookCompact&flag=gotolook&id="+id);
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
			document.form1.action = "compact.do?method=getContractList";
			document.form1.submit();
		}
	</script>
</head>
<body>
<form name = "form1" method="post">
	<input type="hidden" name="type" value="<%=Contants.COMPACTRELET%>" />
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">合同续租情况列表</td>
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
					            	<td height="10" nowrap="nowrap">客户名称：<input type="text" name="clientName" id="clientName" value="${compactManagerForm.clientName }"></td>
					            	<td height="10" nowrap="nowrap">合同状态：<input type="hidden" name="statusIn" id="statusIn" value="${compactManagerForm.status }">
										<select name="status" id="status">
											<option value="" selected="selected">全部</option>
											<option value="<%=Contants.NOTSUBMIT %>" ><%=Contants.NOTSUBMIT %></option>
											<option value="<%=Contants.ONAPPROVAL %>" ><%=Contants.ONAPPROVAL %></option>
											<option value="<%=Contants.THROUGHAPPROVAL %>" ><%=Contants.THROUGHAPPROVAL %></option>
											<option value="<%=Contants.NOTTHROUGH %>" ><%=Contants.NOTTHROUGH %></option>
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
											<c:when test="${empty list}">
											<tr align="center">
												<td colspan="8" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${list}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="look(${v.compactByNewId.id })">
											 	<td width="" nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" id="checkbox1" name="checkbox1" value="${v.id }">
											 		<input type="hidden" id="hidden1" value="${v.compactByNewId.id }">
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
									<td align="right">
										<table>
											<tr>
					               				<td nowrap="nowrap"><input type="button" class="button" value="添加" onclick="add()"></td>
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
	<script type="text/javascript">
		if(document.getElementById("statusIn").value!=null&&document.getElementById("statusIn").value!=""){
			document.getElementById("status").value = document.getElementById("statusIn").value;
		}
	</script>
</html>
