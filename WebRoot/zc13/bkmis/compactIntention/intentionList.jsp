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
	<title>意向书管理</title>
	
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
		//注意：由于在打印的时候，设定了document.actionForm.target = "_blank";
		//所以，在其他的当前页跳转的需要指定document.actionForm.target = "_self";
		//否则，点击打印后，在去点击查询或编辑等链接，就会默认为上一次target设定的值"_blank"，即会新打开一个页面
		
		//查看意向书
		function look(id){
			window.open("<%=path%>/intention.do?method=gotoEditIntention&forward=lookIntention&id="+id);
		}
		
		//全选
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
		
		//查询
		function query(){
			document.actionForm.action = "<%=path%>/intention.do?method=getIntentionList";
			document.actionForm.target = "_self";
			document.actionForm.submit();
		}
		
		//编辑意向书
		function edit(){
			if(!check("编辑",false)){
				return;
			}
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = arr[i].value;
				}
			}
			$.post("<%=path%>/intention.do",{method:"checkIntentionStatus",id:id},function(data){
				if(data=="0"){
					alert("有正在审批或审批通过的意向书,不允许编辑！");
					return;
				}else{
					document.actionForm.action="<%=path%>/intention.do?method=gotoEditIntention&id="+id;
					document.actionForm.target = "_self";
					document.actionForm.submit();
				}
			})
		}
		
		//添加意向书
		function add(){
			document.actionForm.action="<%=path%>/intention.do?method=goAdd";
			document.actionForm.target = "_self";
			document.actionForm.submit();
		}
		
		//删除意向书
		function del(){
			if(!check("删除",true)){
				return;
			}
			var ids = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					ids += arr[i].value+",";
				}
			}
			ids = ids.substring(0,ids.length-1);
			$.post("<%=path%>/intention.do",{method:"checkIntentionStatus",id:ids},function(data){
				if(data=="0"){
					alert("有正在审批或审批通过的意向书,不能删除！");
					return;
				}else{
					if(window.confirm("确定删除吗?")){
						document.actionForm.action="<%=path%>/intention.do?method=delIntention&id="+ids;
						document.actionForm.target = "_self";
						document.actionForm.submit();
					}				
				}
			})
		}
		
		//意向书转成合同
		function convertCompact(){
			if(!check("编辑",false)){
				return;
			}
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			var status;
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = arr[i].value;
					status = trim(arr[i].parentElement.parentElement.lastChild.innerText);
				}
			}
			if(status!="<%=Contants.THROUGHAPPROVAL%>"){
				alert("该意向书尚未审核通过，不能转为合同！");
				return;
			}
			$.post("<%=path%>/intention.do",{method:"checkIsIsEarnest",id:id},function(data){
				if(data=="0"){
					alert("该意向书的定金尚未缴纳！不能够转为合同！");
					return;
				}else{
					document.actionForm.action="<%=path%>/intention.do?method=convertCompact&id="+id;
					document.actionForm.target = "_self";
					document.actionForm.submit();				
				}
			})
		}
		
		/*
		*验证选择记录条数
		*param str 动作 
		*param allowMore 是否允许选择多条
		*/
		function check(str,allowMore){
			var k = 0;
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
			if(k>1&&!allowMore){
				alert("最多只能选择一条记录");
				return false;
			}
			return true;
		}
		
		
		//导出报表
		function explorReport(){
			document.actionForm.action = "<%=path%>/intention.do?method=explorReport";
			document.actionForm.target = "_self";
			document.actionForm.submit();
		}
		
		//打印
        function print(){
			document.actionForm.action = "<%=path%>/intention.do?method=printIntention";
	  		document.actionForm.target = "_blank";
	  		document.actionForm.submit();
		} 
	</script>
</head>
<body>
<form name="actionForm" method="post">
	<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">意向书管理</td>
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
					            	<td height="10" colspan="3"></td>
					         	</tr>
					          	<tr>
					           		<td class="txt" nowrap="nowrap">
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					           		</td>
					            	<td height="10" nowrap="nowrap" colspan="2">
					            		
					            		住户名称：
					            		<input type="text" name="c_clientName" id="c_clientName" value="${intentionForm.c_clientName }">
					            		&nbsp;&nbsp;&nbsp;
					            		房间号：
					            		<input type="text" name="c_roomCode" value="${intentionForm.c_roomCode }">
					            		&nbsp;&nbsp;&nbsp;
					            		意向书编号：
										<input type="text" name="compactIntention.intentionCode" value="${intentionForm.compactIntention.intentionCode}">
									</td>
									<td>&nbsp;</td>
					            </tr>
					            <tr>
					            	<td height="4" colspan="3"></td>
					         	</tr>
					            <tr>
					            	<td height="10"></td>
									<td height="10" nowrap="nowrap">审批状态：<input type="hidden" name="statusIn" id="statusIn" value="${intentionForm.c_status }">
										<select name="c_status" id="c_status">
											<option value="" selected="selected">全部</option>
											<option value="<%=Contants.NOTSUBMIT %>"><%=Contants.NOTSUBMIT %></option>
											<option value="<%=Contants.ONAPPROVAL %>"><%=Contants.ONAPPROVAL %></option>
											<option value="<%=Contants.THROUGHAPPROVAL %>"><%=Contants.THROUGHAPPROVAL %></option>
											<option value="<%=Contants.NOTTHROUGH %>"><%=Contants.NOTTHROUGH %></option>
										</select>
										&nbsp;&nbsp;&nbsp;
										是否缴纳定金：
					            		<select name="c_isEarnest" id="c_isEarnest">
											<option value="">全部</option>
											<option  value="1" <c:if test="${intentionForm.c_isEarnest eq 1}">selected</c:if>>已缴</option>
											<option value="0" <c:if test="${intentionForm.c_isEarnest eq 0}">selected</c:if>>未缴</option>
										</select>
										&nbsp;&nbsp;&nbsp;
										是否已转为合同：
					            		<select name="c_isConvertCompact" id="c_isConvertCompact">
											<option value="">全部</option>
											<option value="1" <c:if test="${intentionForm.c_isConvertCompact eq 1}">selected</c:if>>是</option>
											<option value="0" <c:if test="${intentionForm.c_isConvertCompact eq 0}">selected</c:if>>否</option>
										</select>
									</td>
									
									<td align="right" nowrap="nowrap" >
					            		<input type="button" class="button" value="查询" onclick="query()">
									</td>
								</tr>
							 	 <tr>
					           		 <td height="10" colspan="3"></td>
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
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户名称</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">意向书编号</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">联系电话</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">面积</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户类型</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">定金金额</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">是否已交定金</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">是否已转为合同</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">审批状态</th>
											</tr>
											<c:choose>
											<c:when test="${empty intentionForm.intentionList}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${intentionForm.intentionList}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="look(${v.id })">
											 	<td  nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" id="checkbox1" name="checkbox1" value="${v.id }">
												</td>
								                <td  nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.name }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.intentionCode }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.phone }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.roomCodes }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.totalArea }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.clientType }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.earnest }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">
								                	<c:choose>
								                		<c:when test="${v.isEarnest eq 1}">已缴</c:when>
								                		<c:otherwise >未缴</c:otherwise>
								                	</c:choose>
								                </td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">
								                	<c:choose>
								                		<c:when test="${v.isConvertCompact eq 1}">是</c:when>
								                		<c:otherwise >否</c:otherwise>
								                	</c:choose>
								                </td>
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
					                			<td nowrap="nowrap"  align="right"><input class="button" type="button" value="意向书录入" onclick="add()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="编辑" onclick="edit()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="删除" onclick="del()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="意向书转合同" onclick="convertCompact()"></td>
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
	if(document.getElementById("statusIn").value!=null&&document.getElementById("statusIn").value!=""){
		document.getElementById("c_status").value = document.getElementById("statusIn").value;
	}
</script>
</html>
