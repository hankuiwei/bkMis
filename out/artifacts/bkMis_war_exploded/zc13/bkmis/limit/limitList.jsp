<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>车辆限行情况一览</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		//编辑
		function edit(id){
			var a = window.showModalDialog("limit.do?method=goEdit&id="+id);
			if(a=="editOk"){
				alert("修改成功！");
				form1.submit();
			}
		}
		//删除
		function dele(id){
			if(confirm("确实要删除这条记录吗？")){
				var url ="limit.do";
				$.post(url,{method:"deletByID",id:id},function(data){
				 	if(data == 'true'){
				 		alert("删除成功！");
				 		document.forms[0].action="limit.do?method=getList";
				 		document.forms[0].submit();
				 	}
				})
			}
		}
		//新增
		function open1(){
			var a = window.showModalDialog("limit.do?method=goAdd");
			if(a=="addOk"){
				alert("添加成功！");
				form1.submit();
			}
		}
	</script>
	
</head>
<body>
<form name = "form1" method="post" action = "limit.do?method=getList">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">车辆限行情况列表</td>
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
     				
  					<tr height="95%">
					    <td valign = "top">
					    	<!-- 表单内容区域 -->
							<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
								<tr height = "95%">
					        		<td width="100%">
					        		<div id = "div1" class = "RptDiv"  >
							   			<table border="0" cellpadding="0" cellspacing="0" class = "RptTable">
					              			<tr>
								                <th width="7%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">开始时间</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">截止时间</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">周一限行号码</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">周二限行号码</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">周三限行号码</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">周四限行号码</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">周五限行号码</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">操作</th>
											</tr>
											<c:choose>
											<c:when test="${empty list}">
											<tr align="center">
												<td colspan="10" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${list}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
											 
								                <td nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count}</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.beginDate }&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.endDate }&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.mon }&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.tues }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.wed }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.thrus }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.friday }&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">
								                	<input type="button" value="修改" onclick="edit('${v.id }')">
								                	<input type="button" value="删除" onclick="dele('${v.id }')">
								                </td>
											</tr>
											</c:forEach>
											</c:otherwise>
											</c:choose>
					             		</table>
					             		</div>
									</td>
		     		 			</tr>
								<tr>
								  	<td align="right">
								      	<input type="button" class="button" value="添加" onclick="open1()">
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
