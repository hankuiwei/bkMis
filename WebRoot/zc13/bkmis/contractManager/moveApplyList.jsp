<%@ page language="java"   pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.Contants"%>
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
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript"></script>
	<script type="text/javascript">
		
		function selectAny(){
			document.form1.action = "compact.do?method=getDelContractList";
			document.form1.submit();
		}
		
		function getDetail(id){
			document.form1.action = "compact.do?method=goEditQuit&flag=gotolookDetail&id="+id;
			document.form1.submit();
		}
	</script>
</head>
<body>
<form name = "form1" method="post">
	<input type="hidden" name="forward" value="${forward }" />
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">迁出申请单</td>
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
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件:
					           		</td>
					            	<td height="10" nowrap="nowrap">客户名称：<input type="text" name="clientName" id="clientName" value="${compactManagerForm.clientName }"></td>
					            	
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
					        		<div id = "div1" class = "RptDiv"  >
							   			<table border="0" cellpadding="0" cellspacing="0" class = "RptTable">
					              			<tr>
								                <th width="6%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">迁出单编号</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">合同编号</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户名称</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">申请日期</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">迁出日期</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">退租原因</th>
												<th width="11%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">经办人</th>
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
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="getDetail(${v.id });">
								                <td width="6%" nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count }</td>
												<td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.quitCode }&nbsp;</td>
												<td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.compact.code }&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.compact.name}&nbsp;</td>
												<td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.applayDate }&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.quitDate }&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.quitSeason }&nbsp;</td>
								                <td width="11%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.doMan }&nbsp;
									                <input type="hidden" id="status${tag.count-1 }" value="${v.compact.isNotice }">
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
