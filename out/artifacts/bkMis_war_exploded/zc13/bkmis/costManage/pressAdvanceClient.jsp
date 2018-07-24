<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List list = (List)request.getAttribute("list");
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
		
		function search(){
			document.forms[0].action = "costTransact.do?method=getPressAdvanceClient";
			document.forms[0].target = "_self";
			document.forms[0].submit();
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
					<td width="165" nowrap="nowrap" class="form_line">需缴纳预收款的客户</td>
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
	  			<td>
		  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
					            	<td height="10"></td>
					         	</tr>
					          	<tr>
					           		<td class="txt" nowrap="nowrap">
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					           		</td>
					            	<td align = "left" nowrap="nowrap">入住状态
					            		<select name="statyStatus">
					            			<option value="">--所有--</option>
					            			<option value="1" <c:if test="${form.statyStatus=='1' }">selected</c:if>>已入住</option>
					            			<option value="0" <c:if test="${form.statyStatus=='0' }">selected</c:if>>未入住</option>
					            		</select>
					            	</td>
									<td align="right" nowrap="nowrap">
					            		<input type="button" name="focuson" value="查询" onclick="search()" class="button"/>
									</td>
								</tr>
								
					        </table>
	  			</td>
	  		</tr>
		  	<tr>
			  	<td><table width="100%"  cellpadding="0" cellspacing="0" class="form_tab">
			    	<tr>
			    		<th class="RptTableHeadCellFullLock"  width="4%" align="center">序号</th>
			    		<th class="RptTableHeadCellLock"  width="15%">客户名称</th>
			    		<th class="RptTableHeadCellLock"  width="15%">当前周期还需要缴纳的房租</th>
			    		<th class="RptTableHeadCellLock"  width="15%">下一周期需缴房租</th>
			    		<th class="RptTableHeadCellLock"  width="15%">当前预收款金额</th>
			    		<th class="RptTableHeadCellLock"  width="15%">至少需缴金额</th>
			    	</tr>
					<%if(list==null||list.size()<=0){ %>
					<tr><td colspan="12" align="center" class="head_form1"><font color="red">暂时没有预收款余额不足的客户!</font></td></tr>
					<%}else{ %>	
						<%for(int i = 0;i<list.size();i++){ 
							Map map = (Map)list.get(i);
						%>
							
						<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
						    <td class="RptTableBodyCellLock"  align="center"><%=i+1 %></td>
						    <td class="RptTableBodyCell">&nbsp;<a href="<%=path %>/bill.do?method=getBillList&clientId=<%=map.get("clientId") %>"><%=map.get("clientName") %></a></td>
						    <td class="RptTableBodyCell">&nbsp;<script>document.write(round(<%=map.get("curCircleRent") %>,2));</script></td>
						    <td class="RptTableBodyCell">&nbsp;<script>document.write(round(<%=map.get("nextRent") %>,2));</script></td>
						    <td class="RptTableBodyCell">&nbsp;<script>document.write(round(<%=map.get("curAdvance") %>,2));</script></td>
						    <td class="RptTableBodyCell">&nbsp;<script>document.write(round(<%=map.get("mustPay") %>,2));</script></td>
						   </tr>
						   <%} %>
					<%} %>
								
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
