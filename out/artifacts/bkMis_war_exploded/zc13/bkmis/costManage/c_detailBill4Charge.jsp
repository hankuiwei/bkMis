<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>账单详情</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/util.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
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
  	<form name="formEdit" method="post" id="formEdit">
    <table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="100" nowrap="nowrap" class="form_line">账单详情</td>
					<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
					<td width="1080" class="form_line2"></td>
					<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
       			</tr>
   			</table>
   		</td>
 	  </tr>
	  <tr>
	  	<td><table width="100%" cellpadding="0" cellspacing="0" class="menu_tab2">
		  	<c:if test="${!empty list}">
		  	<tr height="180">
			    <td valign="top">
			    	<!-- 表单内容区域 -->
					<table width="100%" height="100%" cellspacing="0" cellpadding="0" style="table-layout:fixed">
						<tr height="100%">
			        		<td width="100%">
						  		<div id="div1" class="RptDiv"><table width="100%" cellpadding="0" cellspacing="0" class="RptTable">
						    	<tr>
						    		<th class="RptTableHeadCellLock"  width="5%">序号</th>
						    		<th class="RptTableHeadCellLock"  width="10%">收费项</th>
						    		<th class="RptTableHeadCellLock"  width="10%">账单月</th>
						    		<th class="RptTableHeadCellLock"  width="10%">合同金额</th>
						    		<th class="RptTableHeadCellLock"  width="10%">滞纳金额</th>
						    		<th class="RptTableHeadCellLock"  width="10%">已收金额</th>
						    	</tr>
			    			<c:forEach items="${list}" var="v" varStatus="vs">
			    				<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" >
						    		<td class="RptTableBodyCell" align="center">${vs.count }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v[1].itemName }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v[0].billDate }</td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].billsExpenses }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].delayingExpenses }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].actuallyPaid }).toFixed(2).toString()))</script></td>
						    	</tr>
			    			</c:forEach>
			    			</table></div>
							</td>
		    			</tr>
		    		</table>
		    	</td>
		    </tr>
		    </c:if>
		    <tr>
	    		<td><table width="100%"  cellpadding="0" cellspacing="0"  class="Rpt1" >
    				<tr>
    					<!--<c:if test="${!empty charge.temporalAmount && charge.temporalAmount!=0}">
    				 		<td align="" width="5%" class="head_form1" nowrap>&nbsp;暂存款：${charge.temporalAmount }</td>
    				 	</c:if>
    				 	<c:if test="${!empty charge.advanceAmount && charge.advanceAmount!=0}">
    				 		<td align="" width="5%" class="head_form1" nowrap>&nbsp;预收款：${charge.advanceAmount }</td>
    				 	</c:if>
    				 	<c:if test="${!empty charge.depositAmount && charge.depositAmount!=0}">
    				 		<td align="" width="5%" class="head_form1" nowrap>&nbsp;押金：${charge.depositAmount }</td>
    					</c:if>
    					--><td align="" width="5%" class="head_form1" nowrap >单据号：${charge.billNum }</td>
    				</tr>
	    		</table></td>
	    	</tr>
	    				
	    </table></td></tr>
	    <tr>
	    	<td align="center"><input type="button" value="打印" onclick="printCharge();"></td>
	    </tr>
	    </table></form>
  </body>
  <script type="text/javascript">
  	function printCharge(){
  		var URL = "<%=path%>/bill.do?method=printSj2&chargeId=${charge.id}";
  		window.open(URL);
  	}
  </script>
</html>
