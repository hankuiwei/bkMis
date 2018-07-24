<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>出库选择材料信息列表</title>
    
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	
	<script type="text/javascript">
		//按名称查询
		function searchByName(){
			document.form1.action="<%=path%>/outputdepot.do?method=selectExistMaterials";
			document.form1.submit();
		}
		//复选框的全选
		function selectedAll(){
			var parentBox = document.getElementById("parentBox");
			var childBox = document.getElementsByName("childBox");
			if(parentBox.checked == true){
				for(var i = 0;i<childBox.length;i++){
					childBox[i].checked=true;
				}
			}else{
				for(var i = 0;i<childBox.length;i++){
					childBox[i].checked=false;
				}
			}
		}
		//提交选择的材料信息类别
		function choose(){
			var itemIds = '';
			var chkx = document.getElementsByName("childBox");
			for(var i = 0;i<chkx.length;i++){
				if(chkx[i].checked==true){
					itemIds += chkx[i].value + ',';
					//alert(itemIds);
				}
			}
			if (itemIds.length > 0) {
					itemIds = itemIds.slice(0, itemIds.length-1);
					window.returnValue = itemIds;
					window.close();
			}
			else{
				alert("请先选择记录，再进行操作!");
				return false;
			}
		}
		function closea(){
			returnValue = "null";
			window.close();
		}
	</script>
  </head>
  
  <body>
  <!-- 加载页面div -->
	<jsp:include page="/loading.jsp"></jsp:include>
	<!-- 加载页面div -->
   	<form action="" method="post" name="form1">
   		<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">出库选择材料信息列表</td>
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
					            	<td align="left">材料名称：
					            		<input type="text" name="name" value="${materialsForm.name }"/>
					            	</td>
									<td align="right">
					            		<input type="button" id = "focuson" class="button" value="确定" onclick="searchByName()">
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
							   			<table class = "RptTable" border="0" cellpadding="0" cellspacing="0">
					              			<tr>
								                <th class = "RptTableHeadCellFullLock"><input type="checkbox" name="parentBox" id="parentBox" onclick="selectedAll();"></th>
												<th class = "RptTableHeadCellFullLock">序号</th>
												<th class = "RptTableHeadCellFullLock">材料类别</th>
												<th class = "RptTableHeadCellLock">材料编号</th>
												<th class = "RptTableHeadCellLock">材料名称</th>
												<th class = "RptTableHeadCellLock">库存数量</th>
												<th class = "RptTableHeadCellLock">可用库存数</th>
												<th class = "RptTableHeadCellLock">单位</th>
												<th class = "RptTableHeadCellLock">入库单价</th>
											</tr>
											<c:choose>
											<c:when test="${empty materialsForm.materialList}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有相应的库存信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												  <c:forEach items="${materialsForm.materialList}" var="mf" varStatus="vs">
												  	
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
														<td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="childBox" name="childBox" value="${mf.materCode }"></td>
														<td class="RptTableBodyCellLock" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
														<td class="RptTableBodyCellLock" align="center" title="${mf.typeName }">${mf.typeName }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.materCode }">${mf.materCode }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.materName }">${mf.materName }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.nowStock }">${mf.nowStock }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.doStock }">${mf.doStock }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.codeName }">${mf.codeName }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.unitPrice }">${mf.unitPrice }&nbsp;</td>
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
					                			<td nowrap="nowrap"  align="right"><input class="button" type="button" value="确定" onclick="choose()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="取消" onclick="closea()"></td>
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
