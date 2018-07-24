<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.apache.struts.taglib.html.Constants" %> 
<%@ page import="org.apache.struts.Globals" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>" target="_parent">
    
    <title>添加客户分析</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		function addAnalysis(){
			var lpId = document.getElementById("lpId").value;
			var beginDate = document.getElementById("beginDate").value;
			var endDate = document.getElementById("endDate").value;
			if(lpId == null || lpId == ""){
				alert("请选择要统计的楼盘!");
			}else if(beginDate == null || beginDate == ""){
				alert("请选择开始日期!");
			}else if(endDate == null || endDate == ""){
				alert("请选择截止日期!");
			}else{
				document.addForm.action = "analysisCus.do?method=doAddAnalysis";
				document.addForm.submit();
			}
		}
	</script>
  </head>
  
  <body style="overflow-y:hidden;">
    <form action="" name="addForm" method="post">
    	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
    			<tr>
    				<td height="5"></td>
  				</tr>
  				<tr>
    			<td>
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
      					<tr>
        					<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
							<td width="165" nowrap="nowrap" class="form_line">添加客户分析</td>
							<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
							<td width="1080" class="form_line2"></td>
							<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        				</tr>
    				</table>
    			</td>
  				</tr>
    			<tr>
    				<td class="menu_tab2" align="center">
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
    					<tr>
    						<td align="right" class="head_left">创建时间：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="createDate" id="createDate" value="${current }" readonly="readonly">
    						</td>
    						<td class="head_form1">楼盘名称：</td>
    						<td class="head_form1">
    							<select id="lpId" name="lpId">
    								<c:forEach items="${lpList}" var="lp">
    								<option value="${lp.lpId }">
    									${lp.lpName }
    								</option>
    								</c:forEach>
    							</select>
    						</td>
    					<tr>
    						<td align="right" class="head_left">统计日期：</td>
    						<td class="head_form1" nowrap="nowrap" colspan="3">&nbsp;
    							<input type="text" name="beginDate" id="beginDate" readonly onclick="WdatePicker()" class="Wdate"/>
										&nbsp;至&nbsp;
								<input type="text" name="endDate" id="endDate" readonly onclick="WdatePicker()" class="Wdate"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="center" colspan="4">
    							<input type="button" name="" value="创建统计记录" class="button" onclick="addAnalysis();"/>
    						</td>
    					</tr>
    					<c:if test="${flag }">
    						<script type="text/javascript">
    							returnValue = "flag";
    							this.close();
							</script>
    					</c:if>
    				</table>
    			  </td>
    			</tr>
    		</table>
    		<input type="hidden" name="<%=Constants.TOKEN_KEY%>" value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>"/>
    </form>
  </body>
</html>
