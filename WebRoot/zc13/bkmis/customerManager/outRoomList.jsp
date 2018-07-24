<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>迁出管理</title>
	
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
		function outRoom(){
			if(!check("反悔")){
				return;
			}
			document.form1.action = "<%=path%>/customer.do?method=returnOutRoom";
			document.form1.submit();
		}
		function check(str){
			var isCheck = false;
			var arr = document.getElementsByName("checkbox1");
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					isCheck = true;
					break;
				}
			}
			if(!isCheck){
				alert("请选择要"+str+"的记录");
				return false;
			}
			return true;
		}
		function selectOne(th){
			var arr = document.getElementsByName("checkbox1");
			if(th.checked){
				for(var i=0;i<arr.length;i++){
					arr[i].checked = false;
				}
				th.checked = true;
			}
		}
	</script>
</head>
<body>
<form name="form1" method="post">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">迁出管理</td>
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
								                	选择
								                </th>
								                <th width="6%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">客户名称</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">合同号</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">联系电话</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">面积</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户类型</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">合同类型</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">到期时间</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">入住时间</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">迁出时间</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">状态</th>
											</tr>
											<c:choose>
											<c:when test="${empty list}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${list}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" >
											 	<td  nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" id="checkbox1" name="checkbox1" value="${v.id }" onclick="selectOne(this)">
												</td>
								                <td  nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCellLock" align="center">${v.name }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.code }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.phone }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.roomCodes }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.totalArea }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.clientType }&nbsp;</td>
												<td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.type }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.endDate }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.beginDate }&nbsp;</td>
								                <td  nowrap="nowrap" class="RptTableBodyCell" align="center">${v.goDate }&nbsp;</td>
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
												<td class="form_line3" colspan="8">&nbsp;</td>
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
					               				<td nowrap="nowrap"><input type="button" class="button" value="反悔" onclick="outRoom()"></td>
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
<c:if test="${flag}">
<script type="text/javascript">
	// window.close();
	alert("成功！");
</script>
</c:if>
</html>
