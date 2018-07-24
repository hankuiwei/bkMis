<%@ page language="java" import="java.util.*,com.zc13.util.*"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>客户报修</title>
	
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
		//导出报表
		function explorReport(){
			document.form1.action="personnel.do?method=exportExcel4PersonalWorkingConditions&personnelId=${personnelForm.personnel.personnelId}";
			document.form1.submit();
		}
		
		function openDeal(id){
			document.form1.action="client.do?method=getById&id="+id+"&forward=openDeal";
			document.form1.submit();
		}
	</script>
</head>
<body>
<form name = "form1" method="post">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5">&nbsp;</td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">
						${personnelForm.personnel.personnelName }的派工明细
						</td>
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
								                <th width="6%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">报修时间</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">报修人</th>
								                <th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">报修项目名称</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">联系电话</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">接听(接待)人</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">派工人</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">报修内容</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">报修状态</th>
											</tr>
											<c:choose>
											<c:when test="${empty personnelForm.serClientMainList}">
											<tr align="center">
												<td colspan="10" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的客户报修记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
											<c:forEach items="${personnelForm.serClientMainList}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" onDblClick="openDeal(${v.id})">
								                <td width="6%" nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count}</td>
												<td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.date}&nbsp;</td>
												<td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.name}&nbsp;</td>
												<td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.project}&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.phone}&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.clerk}&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.sendedMan}&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.contentExplain}&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.status}&nbsp;</td>
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
								<tr>
									<td align="right">
										<table>
											<tr>
												<td nowrap="nowrap"><input type="button" class="button" value="返回" onclick="javascript:history.go(-1)"></td>
					               				<c:choose>
					               				<c:when test="${empty personnelForm.serClientMainList}">
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
