<%@ page language="java" import="java.util.*,com.zc13.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String message = GlobalMethod.ObjToStr(request.getAttribute("message"));
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
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/jquery.form.js"></script>
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
	<%if(!message.equals("")){%>
	alert("<%=message%>");
	window.returnValue = 'flag';
	window.close();
	<%}%>
	</script>
	<script type="text/javascript">
		function countMoney(name,index, item){
			//计算未开发票金额
			var trObj = $("tr[name='"+name+"']");
			var amount = 0
			for(var i = 0;i<trObj.length;i++){
				var trInput = $(trObj[i]).find("input[name='invoice_amount']").val();
				if(trInput == ""){
					trInput = 0;
				}
				amount = amount + parseFloat(trInput);
			}
			
			var trnotOpenObj = $("#tdrows"+index);
			/*var td = $(trnotOpenObj).find("td:eq(7)");
			var notOpenAmount = $(td).text();
			if(notOpenAmount == ""){
				notOpenAmount = 0;
			}*/
			var hid = $("#"+name).val();
			var newamount = parseFloat(hid)-amount;
			var notopen = trnotOpenObj.text();
			if(newamount<0) {
				alert("发票金额不能大于收据金额");
				item.value = (parseFloat(hid)-parseFloat(notopen)).toFixed(2);
				return;
			}
			$(trnotOpenObj).html(newamount.toFixed(2));
		}
		//合计相同发票号的发票金额
		function amountInvoice(){
			var invoiceAmounts = document.getElementsByName("invoice_amount");
			var invoiceNum = document.getElementsByName("invoice_num");
			var clientId = document.getElementsByName("client_id")[0].value;
			var numArr = new Array();
			if(invoiceNum.length>0){
				for(var i = 0;i<invoiceNum.length>0;i++){
					if(invoiceNum[i].value!=null&&invoiceNum[i].value!=""){
						var isIn = false;
						for(var j = 0;j<numArr.length;j++){
							if(numArr[j]==invoiceNum[i].value){
								isIn = true;
								break;
							}
						}
						if(!isIn){
							numArr[numArr.length] = invoiceNum[i].value;
						}
					}
				}
			}
			var amountArr = new Array();
			if(numArr.length>0){
				for(var i = 0;i<numArr.length;i++){
					var amount = 0;
					for(var j = 0;j<invoiceNum.length;j++){
						if(numArr[i]==invoiceNum[j].value){
							if(invoiceAmounts[j].value==null||invoiceAmounts[j].value==""){
								invoiceAmounts[j].value = 0;
							}
							amount=parseFloat(parseFloat(amount)+parseFloat(invoiceAmounts[j].value)).toFixed(2).toString();
						}
					}
					amountArr[amountArr.length] = amount;
				}
			}
			$("#amountInvoiceTable").html("");
			if(numArr.length>0){
				for(var i = 0;i<numArr.length;i++){
					$("#amountInvoiceTable").append("<tr><td align='center' style='padding-left:3px;'>发票号："+numArr[i]+"&nbsp;&nbsp;&nbsp;&nbsp;"+"总金额："+amountArr[i]);
					$("#amountInvoiceTable").append("<input type='hidden' name='invoiceNum' value='"+numArr[i]+"'><input type='hidden' name='amount' value='"+amountArr[i]+"'><input type='hidden' name='clientId' value='"+clientId+"'></td></tr>");
				}
			}
		}
		
		//删除一行
		function delReceipt(obj){
			var trObj = $(obj).parent().parent();//删除行
			var tdlength = trObj.children("td").length;//删除行的列数
			//若有3个td，则更新第一个6td的rowspan-1，更新第一个11td的rowspan-1
			if(tdlength==4) {
				var tempTr=trObj.prev();//前一个元素
				var secondflag = 0;
				//找到删除行对应的账单元素
				while(tempTr.children("td").length!=12) {
					//更新第一个6td的rowspan-1
					var childTd = tempTr.children("td");
					if(childTd.length==7 && secondflag==0) {
		 				var nowspan = parseFloat(childTd.eq(0).attr("rowspan"))-1;//原来的rowspan
						childTd.slice(0,3).attr("rowspan", nowspan);
						secondflag = 1;
					} 
					tempTr = tempTr.prev();
				}
				//更新第一个11td的rowspan-1
				var childTd = tempTr.children("td");
 				var nowspan = parseFloat(childTd.eq(0).attr("rowspan"))-1;//原来的rowspan
				childTd.slice(0,5).attr("rowspan", nowspan);
				if(secondflag==0) {
					nowspan = parseFloat(childTd.eq(5).attr("rowspan"))-1;//原来的rowspan
					childTd.slice(5,8).attr("rowspan", nowspan);
				}
			//若有11个td，更新第一个11td的rowspan-1，若该项目明细下还有子元素，则将下面的上移
			} else if(tdlength==12) {
				var nextTr = trObj.next();
				var nextTd = trObj.next().children("td");
				if(nextTd.length==7) {
					var nowspan = parseFloat(trObj.children("td").eq(0).attr("rowspan"))-1;
					trObj.children("td").slice(0, 5).clone().prependTo(nextTr);
					nextTr.children("td").slice(0,5).attr("rowspan", nowspan);
				} else if(nextTd.length==4) {
					var nowspan = parseFloat(trObj.children("td").eq(0).attr("rowspan"))-1;
					var nowspan4second = parseFloat(trObj.children("td").eq(5).attr("rowspan"))-1;
					trObj.children("td").slice(0, 8).clone().prependTo(nextTr);
					nextTr.children("td").slice(0,5).attr("rowspan", nowspan);
					nextTr.children("td").slice(5,8).attr("rowspan", nowspan4second);
				}
			} else if(tdlength==7) {
				var tempTr411=trObj.prev();//前一个元素
				//找到删除行对应的第一个项目明细子元素
				while(tempTr411.children("td").length!=12) {
					tempTr411=tempTr411.prev();//前一个元素
				}
				//更新第一个11td的rowspan-1
				var childTd = tempTr411.children("td");
 				var nowspan = parseFloat(childTd.eq(0).attr("rowspan"))-1;//原来的rowspan
				childTd.slice(0,5).attr("rowspan", nowspan);
				
				var nextTr = trObj.next();
				var nextTd = trObj.next().children("td");
				if(nextTd.length==4) {
					trObj.children("td").slice(0, 3).clone().prependTo(nextTr);
 				    nowspan = parseFloat(trObj.children("td").eq(0).attr("rowspan"))-1;//原来的rowspan
				    nextTr.children("td").slice(0,3).attr("rowspan", nowspan);
				}
			}
			
			trObj.remove();
			if($("#receiptTable").find("tr").length==1) {
				$("amountInvoiceTable").empty();
			} else {
				amountInvoice();
			}
		}
		function over(obj) {
			obj.className = 'hover';
		}
		function out(obj) {
			obj.className = '';
		}
		function sepReceipt(obj,index){
			var trObj = $(obj).parent().parent();
			var trName = trObj.attr("name");
			var trSeq = $("#receiptTable").find("tr").index(trObj);
			var receiptId = $("#receiptTable tr:eq("+trSeq+") input:eq(4)").attr("value");
			var client_id = $("#receiptTable tr:eq("+trSeq+") input:eq(5)").attr("value");
			var billId = $("#receiptTable tr:eq("+trSeq+") input:eq(6)").attr("value");
			var insertStr ='<tr onmouseover="over(this)" onmouseout="out(this);" name="rows'+index+'">';
			insertStr += '<td class="RptTableBodyCell">&nbsp;<input type="text" name="invoice_amount" value="" dataType="Compare" operator="GreaterThanEqual" to="0.01" msg="发票金额必须是正数！" onkeyup="if(isNaN(value))execCommand(\'undo\');amountInvoice();" onafterpaste="if(isNaN(value))execCommand(\'undo\');amountInvoice();" onchange="amountInvoice()" onblur="countMoney(\'rows'+index+'\','+index+', this)"/></td>';
			insertStr += '<td class="RptTableBodyCell">&nbsp;<input type="text" name="invoice_num" value="" dataType="Require" msg="必须填写发票号！" onkeyup="amountInvoice()" onafterpaste="amountInvoice()" onchange="amountInvoice()" /></td>';
			insertStr += '<td class="RptTableBodyCell">&nbsp;';
			insertStr += 	'<select name="invoice_content" style="width:80px;">';
								<c:forEach var="content" items="${invoiceContents}">
			insertStr += 			'<option value="${content.codeName }">${content.codeName }</option>';
								</c:forEach>
			insertStr += 	'</select>';
			insertStr += '</td>';
			insertStr += '<td class="RptTableBodyCell"><input type="button" value="删除" onclick="delReceipt(this)" class="button">&nbsp;<input type="button" value="拆分" onclick="sepReceipt(this,'+index+')" class="button"></td>';
			insertStr += '<input type="hidden" name="receiptId" value="'+receiptId+'" />';
			insertStr += '<input type="hidden" name="client_id" value="'+client_id+'" />';
			insertStr += '<input type="hidden" name="billId" value="'+billId+'" />';
			insertStr += '</tr>';
			
			var td4TrObj = trObj.children("td");
			if(td4TrObj.length==12) {
				var nowspan = parseFloat(td4TrObj.eq(0).attr("rowspan")) + 1;
				var nowspan4second = parseFloat(td4TrObj.eq(5).attr("rowspan")) + 1;
				td4TrObj.slice(0,5).attr("rowspan", nowspan);
				td4TrObj.slice(5,8).attr("rowspan", nowspan4second);
			} else if(td4TrObj.length==7) {
				var nowspan4second = parseFloat(td4TrObj.eq(0).attr("rowspan")) + 1;
				td4TrObj.slice(0,3).attr("rowspan", nowspan4second);
				var tempTr411=trObj.prev();//前一个元素
				//找到对应的账单
				while(tempTr411.children("td").length!=12) {
					tempTr411=tempTr411.prev();//前一个元素
				}
				var nowspan = parseFloat(tempTr411.children("td").eq(0).attr("rowspan"))+1;
				tempTr411.children("td").slice(0,5).attr("rowspan", nowspan);
			} else if(td4TrObj.length==4){
				var secondflag = 0;
				var tempTr411=trObj.prev();//前一个元素
				//找到对应的账单
				while(tempTr411.children("td").length!=12) {
					//更新第一个6td的rowspan+1
					var childTd = tempTr411.children("td");
					if(childTd.length==7 && secondflag==0) {
		 				var nowspan = parseFloat(childTd.eq(0).attr("rowspan"))+1;//原来的rowspan
						childTd.slice(0,3).attr("rowspan", nowspan);
						secondflag = 1;
					} 
					tempTr411 = tempTr411.prev();
				}
				//更新第一个11td的rowspan+1
				var childTd = tempTr411.children("td");
 				var nowspan = parseFloat(childTd.eq(0).attr("rowspan"))+1;//原来的rowspan
				childTd.slice(0,5).attr("rowspan", nowspan);
				if(secondflag==0) {
					nowspan = parseFloat(childTd.eq(5).attr("rowspan"))+1;//原来的rowspan
					childTd.slice(5,8).attr("rowspan", nowspan);
				}
			}
			trObj.after(insertStr);
		}
		
		//开发票
		function openInvoice(){
			var x = Validator.Validate(document.getElementById('formEdit'),2);
			if(!x){
				return;
			}
			if(confirm("你确定要提交吗？")) {
				document.formEdit.action="<%=path%>/invoice.do?method=openInvoice";
				$("#formEdit").ajaxSubmit({
				   	success: function(data){
						window.returnValue = "flag";
						window.close();
					}
				});
			}
		}
	</script>
  </head>
  <!-- 加载页面div -->
	<jsp:include page="../../../../loading.jsp"></jsp:include>
  <!-- 加载页面div -->
  <body>
  	<form action="" name="formEdit" method="post" id="formEdit">
  	<c:forEach items="${invoiceForm.receiptList}" var="v" varStatus="vs">
   		<c:forEach var="item" items="${v.itemList}" varStatus="itemloop">
   			<input type="hidden" id="rows${itemloop.count }${vs.count }" value="${item.notalreadyopen }"/>
		</c:forEach>
   	</c:forEach>
    <table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="165" nowrap="nowrap" class="form_line">开发票</td>
					<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
					<td width="1080" class="form_line2"></td>
					<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
       			</tr>
   			</table>
   		</td>
 	  </tr>
	  <tr>
	  	<td><table width="100%" cellpadding="0" cellspacing="0" class="menu_tab2">
		  	<tr height="330px">
			    <td valign = "top">
			    	<!-- 表单内容区域 -->
					<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height = "95%">
			        		<td width="100%">
						  		<div id="div1" class="RptDiv"  >
		  			<table width="100%"  cellpadding="0" cellspacing="0" class="" id="receiptTable">
			    	<tr>
			    		<th class="RptTableHeadCellFullLock" width="8%">客户名称</th>
			    		<th class="RptTableHeadCellLock" width="8%">收款日期</th>
			    		<th class="RptTableHeadCellLock" width="8%">收据号</th>
			    		<th class="RptTableHeadCellLock" width="8%">收款员</th>
			    		<th class="RptTableHeadCellLock" width="8%">总金额</th>
			    		<th class="RptTableHeadCellLock" width="8%">项目明细</th>
			    		<th class="RptTableHeadCellLock" width="8%">金额明细</th>
			    		<th class="RptTableHeadCellLock" width="8%">未开发票金额</th>
			    		<th class="RptTableHeadCellLock" width="8%">发票金额</th>
			    		<th class="RptTableHeadCellLock" width="8%">发票号</th>
			    		<th class="RptTableHeadCellLock" width="8%">发票内容</th>
			    		<th class="RptTableHeadCellLock" width="12%">操作</th>
			    	</tr>
			    	<c:forEach items="${invoiceForm.receiptList}" var="v" varStatus="vs">
			    		<c:forEach var="item" items="${v.itemList}" varStatus="itemloop">
			    		<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" name="rows${itemloop.count }${vs.count }">
			    			<c:if test="${itemloop.count==1}">
							<td name="tds" class="RptTableBodyCellLock" rowspan="${v.rowspan }">&nbsp;${v.clientName }</td>
							<td name="tds" class="RptTableBodyCell" rowspan="${v.rowspan }">&nbsp;${v.reciveCostDate }</td>
							<td name="tds" class="RptTableBodyCell" rowspan="${v.rowspan }">&nbsp;${v.billNum }</td>
							<td name="tds" class="RptTableBodyCell" rowspan="${v.rowspan }">&nbsp;${v.reciveUserName }</td>
							<td name="tds" class="RptTableBodyCell" rowspan="${v.rowspan }">&nbsp;<script>document.write(formatNum(parseFloat(${v.amount }).toFixed(2).toString()));</script></td>
							</c:if>
							<td class="RptTableBodyCell">&nbsp;${item.standardName }</td>
							<td class="RptTableBodyCell">&nbsp;<fmt:formatNumber value="${item.moneydetail }" pattern="#,#00.00#" /></td> 
							<td class="RptTableBodyCell" id="tdrows${itemloop.count }${vs.count }">0.00</td>    		
							<td class="RptTableBodyCell">&nbsp;<input type="text" name="invoice_amount" value="<fmt:formatNumber value="${item.notalreadyopen }" pattern="#.00" />" dataType="Compare" operator="GreaterThanEqual" to="0.01" msg="发票金额必须是正数！" onkeyup="if(isNaN(value))execCommand('undo');amountInvoice();" onafterpaste="if(isNaN(value))execCommand('undo');amountInvoice();" onchange="amountInvoice()" onblur="countMoney('rows${itemloop.count }${vs.count }',${itemloop.count }${vs.count }, this)"/></td>
							<td class="RptTableBodyCell">&nbsp;<input type="text" name="invoice_num" value="" dataType="Require" msg="必须填写发票号！" onkeyup="amountInvoice()" onafterpaste="amountInvoice()" onchange="amountInvoice()" /></td> 	
							<td class="RptTableBodyCell">&nbsp;
								<select name="invoice_content" style="width:80px;">
									<c:forEach var="content" items="${invoiceContents}">
										<option value="${content.codeName }">${content.codeName }</option>
									</c:forEach>
								</select>
							</td>
							<td class="RptTableBodyCell"><input type="button" value="删除" onclick="delReceipt(this)" class="button">&nbsp;<input type="button" value="拆分" onclick="sepReceipt(this,${itemloop.count }${vs.count })" class="button" /></td>	
							<input type="hidden" name="receiptId" value="${v.id }" />
							<input type="hidden" name="client_id" value="${v.clientId }" />
							<input type="hidden" name="billId" value="${item.id }" />
						</tr>
						</c:forEach>
			    	</c:forEach>
			    	</table>
			    				</div>
							</td>
		    			</tr>
		    		</table>
		    	</td>
		    </tr>
			<tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0" id="amountInvoiceTable">
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td align="center"><input type="button" value="开发票" onclick="openInvoice()" class="button"></td>
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
