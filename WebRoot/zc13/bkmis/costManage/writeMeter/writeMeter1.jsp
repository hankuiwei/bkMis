<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ page import="com.zc13.util.*,java.text.*,java.util.*;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String lpId = request.getParameter("lpId");
	String meterTypeCode = request.getParameter("meterTypeCode");
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Calendar calendar = Calendar.getInstance();
	String nowDate = dateFormat.format(new Date());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>添加抄录信息</title>
	
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
	<c:if test="${!empty alertMessage}">
	<script type="text/javascript">
		alert("${alertMessage}");
	</script>
	</c:if>
	<script type="text/javascript">
		function next(){
			var lookUpMan = document.getElementById("lookUpMan").value;
			var lookUpTime = document.getElementById("lookUpTime").value;
			var enterMan = document.getElementById("enterMan").value;
			if(lookUpMan==""||lookUpMan==null){
				alert("请输入抄表人");
				document.getElementById("lookUpMan").select();
				return;
			}
			if(lookUpTime==""||lookUpTime==null){
				alert("请输入抄表时间");
				document.getElementById("lookUpTime").select();
				return;
			}
			if(enterMan==""||enterMan==null){
				alert("请输入录入人");
				document.getElementById("enterMan").select();
				return;
			}
			document.actionForm.action="<%=path%>/meterInput.do?method=getUserReadMeter";
			document.actionForm.submit();
		}
		
	</script>
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
						<td width="165" nowrap="nowrap" class="form_line">表具抄录向导</td>
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
									<td width="38%">&nbsp;</td>
					        		<td align="center">
					        		
							   			<table width="20%" border="0" cellpadding="0" cellspacing="0" class="RptTable" align="center">
											<tr>
												<td class="RptTableBodyCellLock" align="right">期间：</td>
												<td class="RptTableBodyCell">
													<select name="years">
														<%for(int i=calendar.get(Calendar.YEAR)-1;i<calendar.get(Calendar.YEAR)+2;i++){ %>
														<option value="<%=i %>" <%if(i==calendar.get(Calendar.YEAR)){ %>selected<%} %>><%=i %></option>
														<%} %>
													</select>
													<select name="months">
														<%for(int i = 1;i<=12;i++){ %>
														<option value="<%=i<10?"0"+i:i %>" <%if(i==calendar.get(Calendar.MONTH)){ %>selected<%} %>><%=i %></option>
														<%} %>
													</select>
												</td>
											</tr>
											<tr>
												<td class="RptTableBodyCellLock" align="right">抄表人：</td>
												<td class="RptTableBodyCell"><input type="text" name="lookUpMan" id="lookUpMan" value="" width="98%" /></td>
											</tr>
											<tr>
												<td class="RptTableBodyCellLock" align="right">抄表时间：</td>
												<td class="RptTableBodyCell"><input type="text" name="lookUpTime" id="lookUpTime" readonly onclick="WdatePicker()" value="<%=nowDate %>" class="Wdate" width="98%"/></td>
											</tr>
											<tr>
												<td class="RptTableBodyCellLock" align="right">录入人：</td>
												<td class="RptTableBodyCell"><input type="text" name="enterMan" id="enterMan" value="" width="98%"/></td>
											</tr>
											<tr>
												<td align="center" colspan="2"><input type="button" class="button" id = "focuson" onclick="next()" value="下一步"></td>
											</tr>
					             		</table>
									</td>
									<td width="38%">&nbsp;</td>
		     		 			</tr>
								<tr>
								  <td colspan="3">&nbsp;</td>
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
<script language=javascript>
</script>
</html>
