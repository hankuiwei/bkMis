<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>客户催缴</title>
    	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
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
	<script type="text/javascript">
		/**
		*v表示要转换的值 
		*e表示要保留的位数
		*/
		function round(v,e){ 
			var t=1; 
			for(;e>0;t*=10,e--); 
			for(;e<0;t/=10,e++); 
			return Math.round(v*t)/t; 
		}
	</script>
  </head>
  
  <body>
	<form method="post" name="formEidt" id="formEdit">
	<table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="165" nowrap="nowrap" class="form_line">未缴费客户</td>
					<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
					<td width="1080" class="form_line2"></td>
					<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
       			</tr>
   			</table>
   		</td>
 	  </tr>
	  <tr>
	  	<td><table width="100%" cellpadding="0" cellspacing="0" class="menu_tab2">
		  	<tr>
			  	<td><table width="100%"  cellpadding="0" cellspacing="0" class="form_tab">
			    	<tr>
			    		<th class="RptTableHeadCellFullLock"  width="4%" align="center">序号</th>
			    		<th class="RptTableHeadCellLock"  width="15%">客户名称</th>
			    		<th class="RptTableHeadCellLock"  width="15%">合同金额</th>
			    		<th class="RptTableHeadCellLock"  width="15%">滞纳金额</th>
			    		<th class="RptTableHeadCellLock"  width="15%">应付金额</th>
			    		<th class="RptTableHeadCellLock"  width="15%">实付金额</th> 
			    		<th class="RptTableHeadCellLock"  width="15%">需付金额</th> 
			    	</tr>
			    	<c:choose>
						<c:when test="${empty list}">
							<tr><td colspan="12" align="center" class="head_form1"><font color="red">暂时没有未缴费客户!</font></td></tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${list}" var="c" varStatus="vs">
								<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
						    		<td class="RptTableBodyCellLock"  align="center">${vs.index+1 }</td>
						    		<td class="RptTableBodyCell">&nbsp;<a href="<%=path %>/bill.do?method=getBillList&clientId=${c.clientId }">${c.clientName }</a></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(round(${c.billExpenses },2));</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(round(${c.delayingExpenses },2));</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(round(${c.payExpenses },2));</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(round(${c.actuallyPaid },2));</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(round(${c.requireExpenses },2));</script></td>
						    	</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
			    </table></td>
		    </tr>
		    <tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr><td class="form_line3">&nbsp;</td>
							<td class="form_line3">&nbsp;${pageHTML }</td>
							<td class="form_line3">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
	    </table></td>
	    </tr>
	</table>
    </form>
  </body>
</html>
