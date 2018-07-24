<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>供应商维护列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		//执行查询
		function searchSupplier(){
			document.supplierForm.action = "<%=path%>/supplier.do?method=showSupplier";
			document.supplierForm.submit();
		}
		//跳转到添加页面
		function addSupplier(){
			document.supplierForm.action = "<%=path%>/supplier.do?method=addSupplier";
			document.supplierForm.submit();
		}
		//全选事件
		function selectedAll(){
			var parentBox = document.getElementById("parentBox");
			var childBox = document.getElementsByName("childBox");
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
		//对供应商信息的编辑
		function editSupplier(){
			var count = 0;
			var itemIds = '';
			var chkx = document.getElementsByName("childBox");
			for(var i = 0;i<chkx.length;i++){
				if(chkx[i].checked == true){
					count++;
					itemIds = chkx[i].value;
				}
			}
			if(count == 0){
				alert("请选择要修改的信息!");
				return ;
			}else if(count == 1){
				document.supplierForm.action = "<%=path%>/supplier.do?method=editSupplier&editId="+itemIds;
				document.supplierForm.submit();
			}else{
				alert("只能选择一条记录");
          	    return;
			}
		}
		//执行删除
		function delSupplier(){
			if(!window.confirm("您确定要删除吗?")) {return ;}
			var itemIds = '';
			var chkx = document.getElementsByName("childBox");
			for(var i = 0;i<chkx.length;i++){
				if(chkx[i].checked == true){
					itemIds += chkx[i].value + ',';
				}
			}
			if(itemIds.length>0){
				itemIds = itemIds.slice(0,itemIds.length-1);
				document.supplierForm.action = "<%=path%>/supplier.do?method=delSupplier&delIds="+itemIds;
				document.supplierForm.submit();
			}else{
				alert("请选择要删除的记录!");
				return ;
			}
		}
	</script>
  </head>
  
  <body style="overflow-y:hidden;" onLoad="hideLoadingDiv();">
    <form action="" method="post" name="supplierForm">
    	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">供应商管理列表</td>
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
					            	<td align="left" nowrap="nowrap">供应商简称：
					            	<input type="text" name="name" id="name" value="${supplierForm.name }"/>
					            	</td>
									<td align ="left" nowrap="nowrap">供应商全称：
										<input type="text" name="fullName" id="fullName" value="${supplierForm.fullName }"/>
									</td>
									<td align="right" colspan="3" nowrap="nowrap">
					            		<input type="button" id = "focuson" class="button" value="确定" onclick="searchSupplier()">
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
								                <th class = "RptTableHeadCellLock"><input type="checkbox" name="parentBox" id="parentBox" onclick="selectedAll();"></th>
												<th class = "RptTableHeadCellLock">序号</th>
												<th class = "RptTableHeadCellLock">供应商简称</th>
												<th class = "RptTableHeadCellLock">供应商全称</th>
												<th class = "RptTableHeadCellLock">联系人</th>
												<th class = "RptTableHeadCellLock">联系电话</th>
											</tr>
											<c:choose>
											<c:when test="${empty supplierForm.supplierList }">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有相应的供应商信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${supplierForm.supplierList }" var="sl" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
														<td class="RptTableBodyCell" align="center"><input type="checkbox" id="childBox" name="childBox" value="${sl.id}"></td>
														<td class="RptTableBodyCell" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${sl.name }">${sl.name }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${sl.fullName }">${sl.fullName }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${sl.linkMan }">${sl.linkMan }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${sl.phone }">${sl.phone }&nbsp;</td>
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
					                			<td nowrap="nowrap"  align="right"><input class="button" onclick="addSupplier();" type="button" value="添加"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" onclick="editSupplier();" value="编辑"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="删除" onclick="delSupplier()"></td>
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
