<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.Contants"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户操作日志列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/AjaxObject.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/JavaProxy.js" defer="defer"></script>
	<script type="text/javascript">
		function queryLog(){
			document.form1.action="logManager.do?method=queryLog";
			document.form1.submit();
		}
		function selectClient(val){
			var selectValue = val.value;
			//alert(selectValue);
			if(val.value == ""){
				document.getElementById("sysLogType").value = selectValue;
			}
			document.getElementById("sysLogType").value = selectValue;
		}
		function selectClient2(val){
			var selectValue = val.value;
			//alert(selectValue);
			if(val.value == ""){
				document.getElementById("sysLogContext").value = selectValue;
			}
			document.getElementById("sysLogContext").value = selectValue;
		}
		//查看详情
		function lookDetail(id){
			window.open("logManager.do?method=queryLogById&id="+id);
		}
	</script>
  </head>
  
  <body>
  <form action="" name="form1" method="post">
    <input name="operateUserId" type="hidden" value="${operateUserId}"/>
  	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="layout:fixed">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">用户操作日志列表</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr><td class="menu_tab2"><table>
  		<tr>
	  		<td width="1004">
		  		<table border="0">
			  		<tr>
						<td class="txt" nowrap="nowrap">
							<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
						</td>
						<td nowrap="nowrap" align="right">操作人：</td>
						<td class="txt"><input name="sysLogName" type="text" value="${sysLogName}"/></td>
						<td align="right" nowrap="nowrap">操作类型：</td>
						<td>
							<select id="name" name="name" style="width:165;position:absolute;clip:rect(2 100% 90% 150)" onchange="selectClient(this)" >
								<option value="">--全部--</option>
								<option value="<%=Contants.ADD %>"><%=Contants.ADD %></option>
								<option value="<%=Contants.MODIFY %>"><%=Contants.MODIFY %></option>
								<option value="<%=Contants.DELETE %>"><%=Contants.DELETE %></option>
								<option value="<%=Contants.RELADAT %>"><%=Contants.RELADAT %></option>
								<option value="<%=Contants.CHECK%>"><%=Contants.CHECK%></option>
								<option value="<%=Contants.LEAVE%>"><%=Contants.LEAVE%></option>
								<option value="<%=Contants.DOMAINTAIN%>"><%=Contants.DOMAINTAIN%></option>
								<option value="<%=Contants.DOCOMPLAIN%>"><%=Contants.DOCOMPLAIN%></option>
							</select>
							<input name="sysLogType" id="sysLogType" type="text" style="font-size:12px;" value="${sysLogType }">
						</td>
						<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作模块：</td>
						<td>
							<select id="name2" name="name2" style="width:165;position:absolute;clip:rect(2 100% 90% 150)" onchange="selectClient2(this)" >
								<option value="">--全部--</option>
								<option value="<%=Contants.L_BUILD %>"><%=Contants.L_BUILD %></option>
								<option value="<%=Contants.L_CLIENT %>"><%=Contants.L_CLIENT %></option>
								<option value="<%=Contants.L_COST %>"><%=Contants.L_COST %></option>
								<option value="<%=Contants.L_SERVICE %>"><%=Contants.L_SERVICE %></option>
								<option value="<%=Contants.L_ANNLYSIS%>"><%=Contants.L_ANNLYSIS%></option>
								<option value="<%=Contants.L_COMPACTMANAGE%>"><%=Contants.L_COMPACTMANAGE%></option>
								<option value="<%=Contants.L_Depot%>"><%=Contants.L_Depot%></option>
								<option value="<%=Contants.L_HR%>"><%=Contants.L_HR%></option>
								<option value="<%=Contants.L_SYSTEM%>"><%=Contants.L_SYSTEM%></option>
							</select>
							<input type="text" id="sysLogContext" name="sysLogContext"  style="font-size:12px;" value="${sysLogContext}" />
						</td>
						<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="查询" class="button" onclick="queryLog()" id="focuson"></td>
					</tr>
					<tr>
						<td colspan="8">
							<span style=" color: blue;">注：在某一行上双击鼠标，可以查看详细内容</span>
						</td>
					</tr>
				</table>
		  </td>
		</tr>
  		<tr>
    		<td align="center">
     			<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					   <td>
					      <div id = "div1" class = "RptDiv"  >
							  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="form_tab">
					              <tr>
									  <th width="5%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</th>
									  <th width="15%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">操作时间</th>
								      <th width="5%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">操作人</th>
								      <th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">操作模块</th>
								      <th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">操作对象</th>
								      <th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">操作类型</th>
									  <th width="25%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">操作内容</th>
								  </tr>
								  <c:choose>
								  <c:when test="${!empty list}">
								  <c:forEach items="${list}" var="log" varStatus="var">
									  <tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="lookDetail('${log.id}');">
										  <td width="5%" nowrap="nowrap" class="RptTableBodyCell" align="center">${var.count}&nbsp;</td>
										  <td width="15%" nowrap="nowrap" class="RptTableBodyCell" align="center">${log.operateTime}&nbsp;</td>
										  <td width="5%" nowrap="nowrap" class="RptTableBodyCell" align="center">${log.operateUserName}&nbsp;</td>
									      <td width="8%" nowrap="nowrap" class="RptTableBodyCell" align="center">${log.operateModule}&nbsp;</td>
									      <td width="8%" nowrap="nowrap" class="RptTableBodyCell" align="center">${log.operateObj}&nbsp;</td>
										  <td width="8%" nowrap="nowrap" class="RptTableBodyCell" align="center">${log.operateType}&nbsp;</td>
										  <td width="25%" nowrap="nowrap" class="RptTableBodyCell" align="center" title="<c:out value="${log.operateContent}" />">
										  <c:choose> 
										    <c:when test="${fn:length(log.operateContent) > 20}"> 
										     <c:out value="${fn:substring(log.operateContent, 0, 20)}......" /> 
										    </c:when> 
										    <c:otherwise> 
										     	<c:out value="${log.operateContent}" /> 
										    </c:otherwise> 
										   </c:choose> 
										  </td>
									  </tr>
								  </c:forEach>
								  </c:when>
								  <c:otherwise>
								  <tr align="center">
									  <td colspan="7" align="center" class="head_form1">
										 <font color="red">对不起，没有符合条件的记录!</font>
									  </td>
								  </tr>
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

