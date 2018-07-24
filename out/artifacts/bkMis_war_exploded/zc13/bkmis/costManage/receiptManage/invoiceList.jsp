<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>已开发票列表</title>
    
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
	<script type="text/javascript">
		
		function cx(){
			document.formEdit.action="<%=path%>/invoice.do?method=getInvoiceList";
			document.formEdit.target = "_self";
			document.formEdit.submit();
		}
		
		function printInvoice(){
			document.formEdit.action="<%=path%>/invoice.do?method=printInvoiceList";
			document.formEdit.target = "_blank";
			document.formEdit.submit();
		}
		
		function exportInvoice(){
			document.formEdit.action="<%=path%>/invoice.do?method=exportInvoiceList";
			document.formEdit.target = "_self";
			document.formEdit.submit();
		}
		
		//查看明细
		function openDeal(id){
			window.open("<%=path%>/invoice.do?method=getInvoiceById&invoiceIds="+id);
		}
		
	</script>
  </head>
  <!-- 加载页面div -->
	<jsp:include page="../../../../loading.jsp"></jsp:include>
  <!-- 加载页面div -->
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
					<td width="165" nowrap="nowrap" class="form_line">已开发票列表</td>
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
		  			<tr>
		  				<td class="txt" nowrap="nowrap">
					       <img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					    </td>
		  				<td align="" nowrap="nowrap">
		  					客户名称：&nbsp;<input type="text" name="cxClientName" value="${invoiceForm.cxClientName }" style="width:150px;">
		  				</td>
		  				<td nowrap="nowrap">
		  					发&nbsp;&nbsp;票&nbsp;&nbsp;号：&nbsp;<input type="text" name="cxInvoiceNo" value="${invoiceForm.cxInvoiceNo }" style="width:100px;">
		  				</td>
		  				<td align="" nowrap>操作员：
		  					<select name="cxInvoiceUserId">
		  						<option value="">全部</option>
		  						<c:if test="${!empty usersList}">
		  							<c:forEach items="${usersList}" var="v" varStatus="vs">
		  								<option value="${v.userid }" <c:if test="${v.userid==invoiceForm.cxInvoiceUserId }">selected</c:if> >${v.realName }(${v.username })</option>
		  							</c:forEach>
		  						</c:if>
		  					</select>
		  				</td>
		  				<td nowrap="nowrap">
		  					开票日期：
		  					<input type="text" name="cxBeginDate" value="${invoiceForm.cxBeginDate }" style="width:80px;" readonly onclick="WdatePicker();" class="Wdate">
		  					至
		  					<input type="text" name="cxEndDate" value="${invoiceForm.cxEndDate }" style="width:80px;" readonly onclick="WdatePicker();" class="Wdate">
		  				</td>
		  			</tr>
		  			<tr>
		  				<td></td>
		  				<td nowrap="nowrap">
		  					发票内容：
		  					<select name="cxInvoiceContent" style="width:150px;">
		  						<option value="">---全部---</option>
		  						<c:forEach var="content" items="${invoiceContents}">
									<option value="${content.codeName }" <c:if test="${content.codeName==invoiceForm.cxInvoiceContent }">selected</c:if>>${content.codeName }</option>
								</c:forEach>
		  					</select>
		  				</td>
		  				<td nowrap="nowrap">
		  					项目明细：
		  					<select name="cxInvoiceItem" style="width:100px;">
		  						<option value="">---全部---</option>
		  						<c:forEach var="item" items="${itemList}">
									<option value="${item.id }" <c:if test="${item.id==invoiceForm.cxInvoiceItem }">selected</c:if>>${item.itemName }</option>
								</c:forEach>
		  					</select>
		  				</td>
		  				<td align="center" nowrap="nowrap">
		  					<input type="text" name="cxStartAmount" value="${invoiceForm.cxStartAmount }" style="width:70px" onkeyup="if(isNaN(value))execCommand('undo');" onafterpaste="if(isNaN(value))execCommand('undo');" />
		  					&nbsp;&lt;&nbsp;发票金额&nbsp;&lt;&nbsp;
		  					<input type="text" name="cxEndAmount" value="${invoiceForm.cxEndAmount }" style="width:70px" onkeyup="if(isNaN(value))execCommand('undo');" onafterpaste="if(isNaN(value))execCommand('undo');" />
		  				</td>
		  				<td align="right" nowrap >
		  					<input type="button" value="查询" onclick="cx()" class="button">
		  					<input type="button" value="打印" onclick="printInvoice()" class="button">
		  					<input type="button" value="导出报表" onclick="exportInvoice()" class="button">
		  				</td>
		  			</tr>
		  			
		  		</table></td>		
		  	</tr>
		  	<tr>
				<td colspan="8">
					<span style=" color: blue;">注：在某一行上双击鼠标，可以查看详细内容</span>
				</td>
			</tr>
		  	<tr height="70%">
			    <td valign = "top">
			    	<!-- 表单内容区域 -->
					<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height = "95%">
			        		<td width="100%">
						  		<div id="div1" class="RptDiv"  >
		  			<table width="100%"  cellpadding="0" cellspacing="0" class="">
			    	<tr>
			    		<th class="RptTableHeadCellFullLock" width="6%">序号</th>
			    		<th class="RptTableHeadCellFullLock" width="8%">客户名称</th>
			    		<th class="RptTableHeadCellLock" width="8%">操作员</th>
			    		<th class="RptTableHeadCellFullLock" width="8%">开票日期</th>
			    		<th class="RptTableHeadCellLock" width="8%">发票号</th>
			    		<th class="RptTableHeadCellLock" width="8%">发票总金额</th>
			    		<th class="RptTableHeadCellLock" width="8%">发票内容</th>
			    		<th class="RptTableHeadCellLock" width="8%">项目明细</th>
			    		<th class="RptTableHeadCellLock" width="8%">发票金额</th>
			    		
			    	</tr>
			    	<c:choose>
			    		<c:when test="${empty invoiceForm.invoiceList}">
			    			<tr align="center">
								<td colspan="10" align="center" class="head_form1">
									<font color="red">对不起，没有符合条件的信息!</font>
								</td>
							</tr>
			    		</c:when>
			    		<c:otherwise>
			    			<c:set var="totalamount" value="0"></c:set>
			    			<c:forEach items="${invoiceForm.invoiceList}" var="v" varStatus="vs">
			    				<c:set var="totalamount" value="${totalamount + v.amount}"></c:set>
			    				<c:forEach var="item" items="${v.itemList}" varStatus="itemloop">
				    				<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" onDblClick="openDeal(${v.id})" >
										<c:if test="${itemloop.count==1}">
										<td class="RptTableBodyCellLock"  align="center" rowspan="${v.rowspan }">${vs.count }</td>
										<td class="RptTableBodyCellLock" rowspan="${v.rowspan }">&nbsp;${v.clientName }</td>
										<td class="RptTableBodyCell" rowspan="${v.rowspan }">&nbsp;${v.operatorName }</td>
										<td class="RptTableBodyCell" rowspan="${v.rowspan }">&nbsp;${v.date }</td>
										<td class="RptTableBodyCell" rowspan="${v.rowspan }">&nbsp;${v.invoiceNum }</td>
										<td class="RptTableBodyCell" rowspan="${v.rowspan }">&nbsp;<fmt:formatNumber value="${v.totalInvoiceAmount }" pattern="#,#00.00#" /></td>
										</c:if>
										<td class="RptTableBodyCell">&nbsp;${item.invoiceContent }</td>
										<td class="RptTableBodyCell">&nbsp;${item.itemName }</td>
										<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${item.invoiceAmount }).toFixed(2).toString()));</script></td>
									</tr>
								</c:forEach>
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
							<td class="form_line3">&nbsp;${pagination }</td>
							<td class="form_line3">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right" style="padding-left: 3px;" nowrap>发票总金额：</td>
							<td align="" style="padding-left: 3px;">
								（<fmt:formatNumber value="${invoiceForm.totalAmount }" pattern="#,#00.00#" />）
							</td>
						</tr>
						<tr>
							<td align="right" style="padding-left: 3px;" nowrap>发票内容汇总：</td>
							<td align="" style="padding-left: 3px;">
								<c:forEach var="con" items="${summaryContent}" varStatus="loop">
									${con[1] }：（<fmt:formatNumber value="${con[0] }" pattern="#,#00.00#" />）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td align="right" style="padding-left: 3px;" valign="top" nowrap>项目明细汇总：</td>
							<td align="">
								<c:forEach var="con" items="${summaryItem}" varStatus="loop">
									${con[1] }：（<fmt:formatNumber value="${con[0] }" pattern="#,#00.00#" />）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</c:forEach>
							</td>
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
  	
  </script>
</html>
