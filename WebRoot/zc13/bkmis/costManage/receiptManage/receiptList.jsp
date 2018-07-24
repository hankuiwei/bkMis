<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>收据管理</title>
    
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
		
		function clickCheckbox(obj){
			var checkboxs = document.getElementsByName("checkbox");
			var values = obj.value.split(",");
			var clientId = values[1];
			if(obj.checked){
				for(var i = 0;i<checkboxs.length;i++){
					var tempValues = checkboxs[i].value.split(",");
					var tempClientId = tempValues[1];
					//将和选中的收据不是同一客户的收据置为disabled，即不能同时选中不用客户的收据
					if(tempClientId!=clientId){
						checkboxs[i].checked=false;
						checkboxs[i].disabled = true;
					}
				}
			}else{
				var flag = true;
				for(var i = 0;i<checkboxs.length;i++){
					var tempValues = checkboxs[i].value.split(",");
					var tempClientId = tempValues[1];
					if(clientId==tempClientId&&checkboxs[i].checked){
						flag = false;
						break;
					}
				}
				if(flag){
					for(var i = 0;i<checkboxs.length;i++){
						var tempValues = checkboxs[i].value.split(",");
						var tempNotOpenBill = tempValues[2];
						if(tempNotOpenBill>0.01){
							checkboxs[i].disabled = false;
						}
					}
				}
			}
		}
		
		function cx(){
			document.formEdit.action="<%=path%>/invoice.do?method=getReceiptList";
			document.formEdit.target = "_self";
			document.formEdit.submit();
		}
		
		function printReceipt(){
			document.formEdit.action="<%=path%>/invoice.do?method=printReceiptList";
			document.formEdit.target = "_blank";
			document.formEdit.submit();
		}
		
		function exportReceipt(){
			document.formEdit.action="<%=path%>/invoice.do?method=exportReceiptList";
			document.formEdit.target = "_self";
			document.formEdit.submit();
		}
		
		//开发票
		function openInvoice(){
			var ids = "";
			var checkboxs = document.getElementsByName("checkbox");
			for(var i = 0;i<checkboxs.length;i++){
				var tempValues = checkboxs[i].value.split(",");
				var tempId = tempValues[0];
				if(checkboxs[i].checked){
					ids += tempId+",";
				}
			}
			if(ids!=""){
				ids = ids.substring(0,ids.length-1);
			}else{
				alert("请选择要开发票的收据！");
				return;
			}
			//var url = "zc13/bkmis/costManage/receiptManage/openInvoice.jsp";
			//var url = "openInvoice.jsp";
			var url = "<%=path%>/invoice.do?method=toOpenInvoice&receiptIds="+ids;
			//window.open(url);
			var options = "dialogWidth:1200px;dialogHeight:600px;status:no;scroll:no;";
			var win = window.showModalDialog(url, this.window, options);
			if(win=="flag"){
				cx();
			}
		}
		
		//查看明细
		function openDeal(id){
			window.open("<%=path%>/invoice.do?method=getReceiptById&receiptIds="+id);
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
					<td width="165" nowrap="nowrap" class="form_line">收据管理</td>
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
		  					客户名称：<input type="text" name="cxClientName" value="${invoiceForm.cxClientName }" style="width:150px;">
		  				</td>
		  				<td nowrap="nowrap">
		  					收据号：<input type="text" name="cxReceiptNo" value="${invoiceForm.cxReceiptNo }" style="width:100px;">
		  				</td>
		  				<td nowrap="nowrap">
		  					发票号：<input type="text" name="cxInvoiceNo" value="${invoiceForm.cxInvoiceNo }" style="width:100px;">
		  				</td>
		  				<td align="" nowrap>收款员：
		  					<select name="cxReciveUserId">
		  						<option value="">全部</option>
		  						<c:if test="${!empty usersList}">
		  							<c:forEach items="${usersList}" var="v" varStatus="vs">
		  								<option value="${v.userid }" <c:if test="${v.userid==invoiceForm.cxReciveUserId }">selected</c:if> >${v.realName }(${v.username })</option>
		  							</c:forEach>
		  						</c:if>
		  					</select>
		  				</td>
		  			</tr>
		  			<tr>
		  				<td></td>
		  				<td nowrap="nowrap">
		  					收费日期：
		  					<input type="text" name="cxBeginDate" value="${invoiceForm.cxBeginDate }" style="width:80px;" readonly onclick="WdatePicker();" class="Wdate">
		  					至
		  					<input type="text" name="cxEndDate" value="${invoiceForm.cxEndDate }" style="width:80px;" readonly onclick="WdatePicker();" class="Wdate">
		  				</td>
		  				<td colspan="2" align="center" nowrap="nowrap">
		  					<input type="text" name="cxStartAmount" value="${invoiceForm.cxStartAmount }" style="width:70px" onkeyup="if(isNaN(value))execCommand('undo');" onafterpaste="if(isNaN(value))execCommand('undo');" />
		  					&nbsp;&lt;&nbsp;未开发票金额&nbsp;&lt;&nbsp;
		  					<input type="text" name="cxEndAmount" value="${invoiceForm.cxEndAmount }" style="width:70px" onkeyup="if(isNaN(value))execCommand('undo');" onafterpaste="if(isNaN(value))execCommand('undo');" />
		  				</td>
		  				<td>&nbsp;</td>
		  				<td align="right" nowrap >
		  					<input type="button" value="查询" onclick="cx()" class="button">
		  					<input type="button" value="打印" onclick="printReceipt()" class="button">
		  					<input type="button" value="导出报表" onclick="exportReceipt()" class="button">
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
			    		<th class="RptTableHeadCellFullLock" width="6%">选择</th>
			    		<th class="RptTableHeadCellFullLock" width="6%">序号</th>
			    		<th class="RptTableHeadCellFullLock" width="8%">客户名称</th>
			    		<th class="RptTableHeadCellFullLock" width="8%">收款日期</th>
			    		<th class="RptTableHeadCellLock" width="8%">收据号</th>
			    		<th class="RptTableHeadCellLock" width="8%">收款员</th>
			    		<th class="RptTableHeadCellLock" width="8%">总金额</th>
			    		<th class="RptTableHeadCellLock" width="8%">项目明细</th>
			    		<th class="RptTableHeadCellLock" width="8%">金额明细</th>
			    		<th class="RptTableHeadCellLock" width="8%">已开发票金额</th>
			    		<th class="RptTableHeadCellLock" width="8%">发票号</th>
			    		<th class="RptTableHeadCellLock" width="8%">未开发票金额</th>
			    	</tr>
			    	<c:choose>
			    		<c:when test="${empty invoiceForm.receiptList}">
			    			<tr align="center">
								<td colspan="12" align="center" class="head_form1">
									<font color="red">对不起，没有符合条件的信息!</font>
								</td>
							</tr>
			    		</c:when>
			    		<c:otherwise>
			    			<c:set var="totalamount" value="0"></c:set>
			    			<c:set var="totalopenamount" value="0"></c:set>
			    			<c:set var="totalnotopenamount" value="0"></c:set>
			    			<c:forEach items="${invoiceForm.receiptList}" var="v" varStatus="vs">
			    				<c:set var="totalamount" value="${totalamount + v.amount}"></c:set>
					    		<c:forEach var="item" items="${v.itemList}" varStatus="itemloop">
				    				<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" onDblClick="openDeal(${v.id})" >
				    					<c:if test="${itemloop.count==1}">
										<td class="RptTableBodyCellLock"  align="center" rowspan="${v.rowspan }"><input type="checkbox" name="checkbox" value="${v.id },${v.clientId },${v.notOpenBill }" onclick="clickCheckbox(this)" <c:if test="${v.notOpenBill<0.01 }">disabled</c:if> ></td>
										<td class="RptTableBodyCellLock"  align="center" rowspan="${v.rowspan }">${vs.count }</td>
										<td class="RptTableBodyCellLock" rowspan="${v.rowspan }">&nbsp;${v.clientName }</td>
										<td class="RptTableBodyCell" rowspan="${v.rowspan }">&nbsp;${v.reciveCostDate }</td>
										<td class="RptTableBodyCell" rowspan="${v.rowspan }">&nbsp;${v.billNum }</td>
										<td class="RptTableBodyCell" rowspan="${v.rowspan }">&nbsp;${v.reciveUserName }</td>
										<td class="RptTableBodyCell" rowspan="${v.rowspan }">&nbsp;<script>document.write(formatNum(parseFloat(${v.amount }).toFixed(2).toString()));</script></td>
										</c:if>
										<td class="RptTableBodyCell">&nbsp;${item.standardName }</td>
										<td class="RptTableBodyCell">&nbsp;${item.moneydetail }</td>
										<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${item.invoicAmount }).toFixed(2).toString()));</script></td>
										<td class="RptTableBodyCell">&nbsp;${item.invoiceId }</td>
										<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${item.notalreadyopen }).toFixed(2).toString()));</script></td>    		
										<c:set var="totalopenamount" value="${totalopenamount + item.invoicAmount}"></c:set>
				    					<c:set var="totalnotopenamount" value="${totalnotopenamount + item.notalreadyopen}"></c:set>
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
							<td class="form_line3">&nbsp;${pageHTML }</td>
							<td class="form_line3">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td align="" style="padding-left: 3px;">
							收据总金额：（<script>document.write(formatNum(parseFloat(${totalamount}).toFixed(2).toString()));</script>），&nbsp;&nbsp;
							已开发票金额：（<script>document.write(formatNum(parseFloat(${totalopenamount}).toFixed(2).toString()));</script>），&nbsp;&nbsp;
							未开发票金额：（<script>document.write(formatNum(parseFloat(${totalnotopenamount}).toFixed(2).toString()));</script>）
							</td>
							<td align="right"><input type="button" value="开发票" onclick="openInvoice()" class="button"></td>
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
