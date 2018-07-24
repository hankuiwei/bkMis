<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>计费参数类型</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <!-- 使用日期控件时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<!-- 使用单元格在线编辑功能时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<style type="text/css">
	<!--
	.Rpt1{
		width: 100%;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 10pt;
		border-top:4px #266898 double;
		border-left:1px #b2c2c9 solid;
	}
	-->
	</style>
</head>
<body>
	<form name="actionForm" method="post">
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">客户</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr><!-- 这个td负责画正式内容的左、右、下方的边框 -->
    		<td class="menu_tab2" align="center">
     			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 				
  					<tr>
					    <td>
					    	<!-- 表单内容区域 -->
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
					        		<td>
							   			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="RptTable">
					              			<tr>
								                <th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this)">
								                </th>
								                <th width="16%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">客户ID</th>
								                <th width="16%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">客户代码</th>
								                <th width="16%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户名称</th>
											</tr>
											<c:choose>
												<c:when test="${empty list}">
													<tr><td colspan="12" align="center" class="head_form1">暂时没有信息!</td></tr>
												</c:when>
												<c:otherwise>
													<c:forEach items="${list}" var="c" varStatus="vs">
														<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
												    		<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value="${c.id }"></td>
												    		<td class="RptTableBodyCell">&nbsp;${c.id }</td>
												    		<td class="RptTableBodyCell">&nbsp;${c.code }</td>
												    		<td class="RptTableBodyCell">&nbsp;${c.name }</td>
												    	</tr>
													</c:forEach>
												</c:otherwise>
											</c:choose>
					             		</table>
					             		
									</td>
		     		 			</tr>
								<tr>
								  <td>&nbsp;</td>
								</tr>
								<tr>
									<td align="right">
										<table>
											<tr>
					               				<td nowrap="nowrap"><input type="button" class="button" onclick="del()" value="删除"></td>
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
</body>
<script language=javascript>
	function checkAll(obj){
		var arrrad1 = document.getElementsByName("checkbox");	
		if(obj.checked){
			for(var i=0;i<arrrad1.length;i++){
				arrrad1[i].checked="checked";
			}
		}else{
			for(var i=0;i<arrrad1.length;i++){
				arrrad1[i].checked="";
			}
		}		
	}
</script>
</html>
