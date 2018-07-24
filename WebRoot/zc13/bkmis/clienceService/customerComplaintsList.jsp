<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>客户投诉</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	 <script type="text/javascript"
			src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/js/util/jquery.js"></script>
	<c:if test="${!empty message}">
		<script type="text/javascript">
			alert("${message}");
		</script>
	</c:if>
	<script type="text/javascript">
		function openDeal(id){
			document.form1.action="complaint.do?method=getComplaint&id="+id+"&forward=openDetail";
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
			var url = "zc13/bkmis/clienceService/addComplaints.jsp";
			var options = "dialogWidth:600px;dialogHeight:400px;status:no;scroll:no;";
			var win = window.showModalDialog(url, this.window, options);
			if(win == "flag"){
				//window.location.href="complaint.do?method=getList";
				query();
			}
		}
		function query(){
			document.form1.action="complaint.do?method=getList";
			document.form1.submit();
		}
		var k;
		function edit(){
			if(!check()){
				return;
			}
			var id;
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = arr[i].value;
					var tr = arr[i].parentElement.parentElement;
					var tds = tr.getElementsByTagName("td");
					if(trim(tds[8].innerText)=="已处理"){
						alert("客户投诉已处理，不能编辑");
						return;
					}
				}
			}
			var url = "complaint.do?method=getComplaint&forward=edit&id="+id;
			var options = "dialogWidth:600px;dialogHeight:400px;status:no;scroll:no;";
			var win = window.showModalDialog(url, this.window, options);
			if(win == "flag"){
				//window.location.href="complaint.do?method=getList";
				query();
			}
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
				alert("至少选择一条记录");
				return false;
			}
			if(k>1){
				alert("最多只能选择一条记录");
				return false;
			}
			return true;
		}
		function deal(){
			if(!check()){
				return;
			}
			var id;
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = arr[i].value;
					var tr = arr[i].parentElement.parentElement;
					var tds = tr.getElementsByTagName("td");
					if(trim(tds[8].innerText)=="已处理"){
						if(confirm("该信息已处理，不能再进行处理，如果想查看处理信息，请点击确定")){
							//window.location.href="complaint.do?method=getComplaint&id="+id+"&forward=openDetail";
							document.form1.action="complaint.do?method=getComplaint&id="+id+"&forward=openDetail";
							document.form1.submit();
							return;
						}else{
							return;
						}
					}
				}
			}
			document.form1.action="complaint.do?method=getComplaint&id="+id+"&forward=deal";
			document.form1.submit();
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
				document.form1.action = "complaint.do?method=delComplaint&ids="+str;
				document.form1.submit();
			}
		}
		//导出报表
		function explorReport(){
			document.form1.action = "complaint.do?method=explorClient";
			document.form1.submit();
		}
	</script>
	
</head>
<body>
<form name = "form1" method="post">
	<input type="hidden" id="flag" name="flag" value="${SerClientComplaintForm.flag }">
	<input type="hidden" id="my_Rtoken" name="my_Rtoken" value="${my_Rtoken }">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">客户投诉</td>
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
					            	<td nowrap="nowrap">
					            		状态：
					            		<select id="cx_status" name="cx_status">
						            		<option value="">全部</option>
						            		<option value="未处理" <c:if test="${SerClientComplaintForm.cx_status eq '未处理'}">selected</c:if>>未处理</option>
						            		<option value="已处理" <c:if test="${SerClientComplaintForm.cx_status eq '已处理'}">selected</c:if>>已处理</option>
					            		</select>
					            		</td>
					            	<td nowrap="nowrap">投诉时间：<input type="text" name="begindate" value="${SerClientComplaintForm.begindate }" readonly onclick="WdatePicker();" class="Wdate">&nbsp;至&nbsp;<input type="text" name="enddate" value="${SerClientComplaintForm.enddate }" readonly onclick="WdatePicker();" class="Wdate"></td>
									<td align="right" nowrap="nowrap">
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
								                <th width="7%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this)">
								                </th>
								                <th width="7%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">投诉时间</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">业户名称</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">联系电话</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">接听(接待)人</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">投诉内容</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">处理时间</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">投诉状态</th>
											</tr>
											<c:choose>
											<c:when test="${empty SerClientComplaintForm.list}">
											<tr align="center">
												<td colspan="9" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的客户投诉记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${SerClientComplaintForm.list}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" onDblClick="openDeal(${v.id})">
											 	<td nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" id="checkbox1" name="checkbox1" value="${v.id }">
												</td>
								                <td nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count}</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.complaintDate }&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.name }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.phone }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.clerk }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.complaintContent }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.disposalDate }&nbsp;</td>
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
												<c:if test="${fn:contains(SerClientComplaintForm.flag,',add,')}">
					                			<td nowrap="nowrap"  align="right"><input class="button" type="button" value="添加" onclick="add()"></td>
					               				</c:if>
					               				<c:if test="${fn:contains(SerClientComplaintForm.flag,',del,')}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="删除" onclick="del()"></td>
					               				</c:if>
					               				<c:if test="${fn:contains(SerClientComplaintForm.flag,',edit,')}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="编辑" onclick="edit()"></td>
					               				</c:if>
					               				<c:if test="${fn:contains(SerClientComplaintForm.flag,',deal,')}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="处理" onclick="deal()"></td>
					               				</c:if>
					               				<c:choose>
					               				<c:when test="${empty SerClientComplaintForm.list}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="alert('无记录,不能导出报表!')"></td>
					               				</c:when>
					               				<c:otherwise>
					               				<td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="explorReport()"></td>
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
