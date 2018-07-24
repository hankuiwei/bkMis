<%@ page language="java"   pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.Contants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.zc13.util.*"%>
<%
	String path = request.getContextPath();
	String message = GlobalMethod.NullToSpace((String)request.getAttribute("message"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>需校验的新合同列表</title>
	
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
		//点击列表页的提交审批所触发的方法。董道奎于2011-03-07修改为“下一步”。注视掉的为之前的代码
		function apply(){
			if(!check("校验")){
				return;
			}
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = arr[i].value;
				}
			}
			window.open("<%=path%>/intention.do?method=gotoEditIntention&forward=lookCompactIntentionCheck&id="+id);
			void 0;
		}
		
		//查看详情
		function look(id){
			window.open("<%=path%>/intention.do?method=gotoEditIntention&forward=lookCompactIntentionCheck&id="+id);
			void 0;
		}
		
		//取消提交
		function cancel(){
			if(!check("取消提交")){
				return;
			}
			var id = "";
			var arr = document.getElementsByName("checkbox1");
			var obj;
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					id = arr[i].value;
					obj = arr[i];
				}
			}
			//如果是未提交审批，则不能进行取消审批操作
			var start = trim(obj.parentNode.parentNode.lastChild.innerText);
			if("<%=Contants.NOTSUBMIT%>"==start){
				alert("该合同还未提交审批！");
				return;
			}
			if(window.confirm("您确认要取消提交吗？")){
				document.form1.action = "<%=path %>/compact.do?method=cancelChecked&id="+id;
				document.form1.submit();
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
		function edit(id){
			window.open("<%=path%>/customer.do?method=gotoLookCompact&flag=lookCompactCheck&id="+id);
		}
		function selectAny(){
			document.form1.action = "compact.do?method=getCheckList";
			document.form1.submit();
		}
	</script>
	<script type="text/javascript">
	<%if(!message.equals("")){%>
		alert("<%=message%>");
	<%}%>
	</script>
</head>
<body>
<form name = "form1" method="post">
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
						<td width="165" nowrap="nowrap" class="form_line">未审批的意向书列表</td>
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
											<option value="<%=Contants.INTENTION %>" selected="selected"><%=Contants.INTENTION %></option>
											<option value="<%=Contants.COMPACT %>"><%=Contants.COMPACT %></option>
											<option value="<%=Contants.COMPACTRELET %>"><%=Contants.COMPACTRELET %></option>
											<option value="<%=Contants.COMPACTCHANGE %>"><%=Contants.COMPACTCHANGE %></option>
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
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">意向书编号</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">联系电话</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">面积</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">签订日期</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户类型</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">租赁开始日期</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">租赁到期日期</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">是否已交定金</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">审批状态</th>
											</tr>
											<c:choose>
											<c:when test="${empty compactManagerForm.compactIntentions}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${compactManagerForm.compactIntentions}" var="v" varStatus="tag">
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
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.signDate }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.clientType }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.beginDate }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.endDate }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">
								                	<c:choose>
								                		<c:when test="${v.isEarnest eq 1}">已缴</c:when>
								                		<c:otherwise >未缴</c:otherwise>
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
			     					<td align="right" colspan="4" class="head_left">
			     						<input type="button" value="下一步" class="button" onclick="apply()">
			     						<input type="button" value="取消提交" class="button" onclick="cancel()">
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
