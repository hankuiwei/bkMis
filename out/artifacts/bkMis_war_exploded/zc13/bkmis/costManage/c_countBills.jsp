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
    
    <title>账单统计</title>
    
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
  	<form action="" name="formEdit" method="post" id="formEdit">
    <table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="165" nowrap="nowrap" class="form_line">账单统计</td>
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
		  		<td align=""><table width="100%" cellpadding="0" cellspacing="0">
		  			<!--<tr height="30">
		  				<td colspan="5" nowrap>楼盘：${elp.lpName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  				费用：${standard.standardName }</td>
		  			</tr>
		  			--><tr>
		  				<td class="txt" nowrap="nowrap">
					       <img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					    </td>
		  				<td align="right" nowrap="nowrap" nowrap>客户名称：</td>
		  				<td><input type="text" name="clientName" value="${CBillForm.clientName }" style="width:80px;">
		  				</td>
		  				<td align="right" nowrap="nowrap" nowrap>房间：</td>
		  				<td><input type="text" name="roomCode" value="${CBillForm.roomCode }" style="width:80px;"></td>
		  				<td align="right" nowrap="nowrap" nowrap>账期：</td>
		  				<td nowrap="nowrap">
		  					<select name="before">
		  						<c:forEach items="${billDateList}" var="v">
		  							<option <c:if test="${CBillForm.before==v }" >selected</c:if> >${v }</option>
		  						</c:forEach>
		  					</select>
		  					—
		  					<select name="after">
			  					<c:forEach items="${billDateList}" var="v">
			  						<option <c:if test="${CBillForm.after==v }" >selected</c:if> >${v }</option>
			  					</c:forEach>
		  					</select>
		  				</td>
		  			
		  				<td align="right" nowrap>单据状态：</td>
		  				<td colspan="">
		  					<select name="status">
		  						<option value="">全部</option>
		  						<option value="0" <c:if test="${CBillForm.status=='0' }">selected</c:if>>未缴</option>
		  						<option value="1" <c:if test="${CBillForm.status=='1' }">selected</c:if>>已缴</option>
		  					</select>
		  				</td>
		  				<td align="right" nowrap >
		  					<input type="button" value="查询" onclick="cx()" class="button">
		  					<input type="button" value="导出报表" onclick="exportExcel('${size }')" class="button">
		  				</td>
		  			</tr>
		  		</table></td>		
		  	</tr>
		  	<tr height="84%">
			    <td valign = "top">
			    	<!-- 表单内容区域 -->
					<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height = "95%">
			        		<td width="100%">
						  		<div id = "div1" class = "RptDiv"  >
					  			<table width="100%"  cellpadding="0" cellspacing="0" class="">
						    	<tr>
						    		<th class="RptTableHeadCellFullLock" width="6%">序号</th>
						    		<th class="RptTableHeadCellFullLock" width="8%">客户名称</th>
						    		<th class="RptTableHeadCellFullLock" width="8%">费用名称</th>
						    		<th class="RptTableHeadCellLock" width="8%">房号</th>
						    		<th class="RptTableHeadCellLock"  width="8%">账单月份</th>
						    		<th class="RptTableHeadCellLock"  width="8%">单据状态</th>
						    		<th class="RptTableHeadCellLock"  width="8%">开始日期</th>
						    		<th class="RptTableHeadCellLock"  width="8%">结束日期</th>
						    		<th class="RptTableHeadCellLock"  width="8%">最后缴款日期</th>
						    		<th class="RptTableHeadCellLock"  width="8%">合同金额</th>
						    		<th class="RptTableHeadCellLock"  width="8%">滞纳金额</th>
						    		<th class="RptTableHeadCellLock"  width="8%">收款日期</th>
						    		<th class="RptTableHeadCellLock"  width="8%">实收金额</th>
						    	</tr>
						    	<c:choose>
						    		<c:when test="${empty list}">
						    			<tr align="center">
											<td colspan="14" align="center" class="head_form1">
												<font color="red">没有信息!</font>
											</td>
										</tr>
						    		</c:when>
						    		<c:otherwise>
						    			<c:forEach items="${list}" var="v" varStatus="vs">
						    				<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
									    		<td class="RptTableBodyCellLock"  align="center">${vs.count }</td>
									    		<td class="RptTableBodyCellLock">&nbsp;${v.compactClient.name }</td>
									    		<td class="RptTableBodyCellLock">&nbsp;${v.standardName }</td>
									    		<td class="RptTableBodyCell">&nbsp;${v.ERooms.roomCode }</td>
									    		<td class="RptTableBodyCell">&nbsp;${v.billDate }</td>
									    		<td class="RptTableBodyCell">&nbsp;<c:if test="${v.status=='0'}">未缴</c:if><c:if test="${v.status=='1'}">已缴</c:if></td>
									    		<td class="RptTableBodyCell">&nbsp;${v.startDate }</td>
									    		<td class="RptTableBodyCell">&nbsp;${v.endDate }</td>
									    		<td class="RptTableBodyCell">&nbsp;${v.closeDate }</td>
									    		<td class="RptTableBodyCell">&nbsp;${v.billsExpenses }</td>
									    		<td class="RptTableBodyCell">&nbsp;${v.delayingExpenses }</td>
									    		<td class="RptTableBodyCell">&nbsp;${v.collectionDate }</td>
									    		<td class="RptTableBodyCell">&nbsp;${v.actuallyPaid }</td>
									    	</tr>
						    			</c:forEach>
						    		</c:otherwise>
						    	</c:choose>
						    	</table>
			    				</div>
							</td>
		    			</tr>
		    		</table>
		    	</td>
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
  <script type="text/javascript">
  	function cx(){
  		document.forms[0].action = "<%=path%>/bill.do?method=getList4Count";
  		document.forms[0].submit();
  	}
  	function exportExcel(isNull){
      		if(isNull!='0') {
				document.forms[0].action = "<%=path%>/bill.do?method=exportExcel";
				document.forms[0].submit();
			} else {
				alert("没有记录可导出！");
			}
      }
  </script>
</html>
