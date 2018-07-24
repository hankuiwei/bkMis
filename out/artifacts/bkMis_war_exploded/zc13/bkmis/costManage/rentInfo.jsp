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
    
    <title>财务对账账单</title>
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
		function cx(){
			document.forms[0].action = "<%=path%>/bill.do?method=queryRentInfo";
			//document.forms[0].target = "_self";
			document.forms[0].submit();
		}
		//导出excel
		function exportExc(size){
			if(size!='0') {
				document.forms[0].action = "<%=path%>/bill.do?method=exportExcelRentInfo";
				document.forms[0].submit();
			} else {
				alert("没有记录可导出！");
			}
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
					<td width="165" nowrap="nowrap" class="form_line">客户房租信息</td>
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
			           		<td nowrap="nowrap">客户名称<input type="text" style="width:100px;" name="clientName" value="${form.clientName }"/></td>
			            	<td nowrap="nowrap">时间<input type="text" style="width:100px;" name="startTime" id="startTime" readonly onclick="WdatePicker({dateFmt:'yyyy-MM'})" value="${form.startTime }" class="Wdate"/></td>
							<td align="right" nowrap="nowrap">
			            		<input type="button" name="focuson" value="查询" onclick="cx()" class="button"/>
			            		<input type="button" name="exportExcel" value="导出报表" onclick="exportExc('${size }')" class="button"/>
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
			    		<th class="RptTableHeadCellLock"  width="15%">房间号</th>
			    		<th class="RptTableHeadCellLock"  width="15%">房租</th>
			    		<th class="RptTableHeadCellLock"  width="15%">房租余额</th>
			    		<th class="RptTableHeadCellLock"  width="15%">房租押金</th>
			    		<th class="RptTableHeadCellLock"  width="15%">装修押金</th>
			    	</tr>
			    	<c:choose>
			    	<c:when test="${empty list}">
					<tr><td colspan="12" align="center" class="head_form1"><font color="red">暂时没有客户房租信息!</font></td></tr>
					</c:when>
					<c:otherwise>
					<c:forEach items="${list}" var="li" varStatus="vs">
						<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
						    <td class="RptTableBodyCellLock"  align="center">${vs.count}</td>
						    <td class="RptTableBodyCell">&nbsp;${li.clientName}</td>
						    <td class="RptTableBodyCell">&nbsp;${li.roomCodes}</td>
						    <td class="RptTableBodyCell">&nbsp;<fmt:formatNumber value="${li.rent}" pattern="#.00"/></td>
						    <td class="RptTableBodyCell">&nbsp;<fmt:formatNumber value="${li.advanceRent}" pattern="#.00"/></td>
						    <td class="RptTableBodyCell">&nbsp;<fmt:formatNumber value="${li.rentDeposit}" pattern="#.00"/></td>
						    <td class="RptTableBodyCell">&nbsp;<fmt:formatNumber value="${li.decorationDeposit}" pattern="#.00"/></td>
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
