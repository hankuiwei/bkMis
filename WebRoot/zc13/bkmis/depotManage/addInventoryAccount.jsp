<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.GlobalMethod"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.apache.struts.taglib.html.Constants" %> 
<%@ page import="org.apache.struts.Globals" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String date = GlobalMethod.getTime();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <base href="<%=basePath%>" target="_parent">
    <title>添加月结算</title>
    
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/zc13/msmis/functionList/scripts/jquery.js"></script>
	<script type="text/javascript">
		function addAccount(){
			var beginDate = document.getElementById("beginDate").value;
			var endDate = document.getElementById("endDate").value;
			if(beginDate == null || beginDate == ""){
				alert("请选择起始时间!");
				document.getElementById("beginDate").focus();
				return;
			}else if(endDate == null || endDate == "" ){
				alert("请选择截止时间!");
				document.getElementById("endDate").focus();
				return;
			}else{
				$.post("<%=path%>/accountDepot.do",{method:"checkLast",year:'${year }',month:'${month }'},function(date){
					
						if(date=="true"){
							//if(confirm("当前月份已有结算，重新结算吗？重新结算会覆盖掉上次的结算记录")){
							//	document.addForm.action = "<%=path%>/accountDepot.do?method=doAddAccount";
							//	document.addForm.submit();
							//}
							alert("当前月份已经结算过，无法再次结算，如要重新结算，请先删除当月的结算记录!");
							window.close();
						}else{
							document.addForm.action = "<%=path%>/accountDepot.do?method=doAddAccount";
							document.addForm.submit();
						}
					}
				)
				
			}
			
		}
	</script>
  </head>
  
  <body style="overflow-y:hidden;">
    <form action="" method="post" name="addForm" id="">
    	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
    			<tr>
    				<td height="5"></td>
  				</tr>
  				<tr>
    			<td>
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
      					<tr>
        					<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
							<td width="165" nowrap="nowrap" class="form_line">添加月结算</td>
							<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
							<td width="1080" class="form_line2"><br></td>
							<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        				</tr>
    				</table>
    			</td>
  				</tr>
    			<tr>
    				<td class="menu_tab2" align="center">
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
    					<tr>
    						<td align="right" class="head_left">
    							说明：
    						</td>
    						<td class="head_form1">&nbsp;
    							1.结算表每月只能结算一次。<br>&nbsp;
    							2.结算表创建后，当月的出入库单将不能再做修改。<br>&nbsp;
    							3.进行核算之后，当天无法再进行出入库操作。<br>&nbsp;
    							4.为了防止重复结算和遗漏结算，统计日期的开始日期默认是上次结算的结束日期的下一天，且无法修改
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">账期：</td>
    						<td class="head_form1">&nbsp;
    							${year } 年
    							${month }月
    							<input type="hidden" name="year" value="${year }">
    							<input type="hidden" name="month" value="${month }">
    						</td>
    					<tr>
    						<td align="right" class="head_left" nowrap="nowrap">统计日期：</td>
    						<td class="head_form1" nowrap="nowrap">&nbsp;
    							<c:if test="${lastDate =='' }">
    							<input type="text" name="beginDate" id="beginDate" readonly onclick="WdatePicker()" class="Wdate"/>
    							</c:if>
    							<c:if test="${lastDate !='' }">
    								<input type="hidden" name="beginDate" value="${lastDate}" id="beginDate" />
    								${lastDate}
    							</c:if>
										&nbsp;至&nbsp;
								<input type="text" name="endDate" id="endDate" readonly onclick="WdatePicker()" value="<%=date %>" class="Wdate"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="center" colspan="2">
    							<input type="button" name="" value="创建结算表" class="button" onclick="addAccount();"/>
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
