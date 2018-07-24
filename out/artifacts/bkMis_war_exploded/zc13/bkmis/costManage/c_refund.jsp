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
    
    <title>客户退款</title>
    
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
  <c:if test="${!empty save}">
  <script type="text/javascript">
  	alert('${save}');
  	window.returnValue = "1";
  	window.close();
  </script>
  </c:if>
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
					<td width="100" nowrap="nowrap" class="form_line">退款</td>
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
						    		<th class="RptTableHeadCellLock"  width="5%"><input type="checkbox" onclick="checkAll(this,'checkbox')"></th>
						    		<th class="RptTableHeadCellLock"  width="5%">序号</th>
						    		<th class="RptTableHeadCellLock"  width="10%">费用类别</th>
						    		<th class="RptTableHeadCellLock"  width="10%">账单月</th>
						    		<th class="RptTableHeadCellLock"  width="10%">合同金额</th>
						    		<th class="RptTableHeadCellLock"  width="10%">滞纳金额</th>
						    		<th class="RptTableHeadCellLock"  width="10%">已收金额</th>
						    	</tr>
			    			<c:forEach items="${list}" var="v" varStatus="vs">
			    				<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" >
						    		<td class="RptTableBodyCell"  align="center"><input type="checkbox" name="checkbox" value="${v[0].id }"></td>
						    		<td class="RptTableBodyCell" align="center">${vs.count }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v[1].itemName }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v[0].billDate }</td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].billsExpenses }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].delayingExpenses }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].actuallyPaid }).toFixed(2).toString()))</script></td>
						    		<input type="hidden" name="id" value="${v[0].id }">
						    		<input type="hidden" name="actuallyPaid" value="${v[0].actuallyPaid }">
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
    				 		<td align="" width="5%" class="head_form1" nowrap><input type="checkbox" name="temporal.amount" value="${charge.temporalAmount }">&nbsp;暂存款：${charge.temporalAmount }</td>
    				 	</c:if>
    				 	<c:if test="${!empty charge.advanceAmount && charge.advanceAmount!=0}">
    				 		<td align="" width="5%" class="head_form1" nowrap><input type="checkbox" name="advance.amount" value="${charge.advanceAmount }">&nbsp;预收款：${charge.advanceAmount }</td>
    				 	</c:if>
    				 	<c:if test="${!empty charge.depositAmount && charge.depositAmount!=0}">
    				 		<td align="" width="5%" class="head_form1" nowrap><input type="checkbox" name="deposit.amount" value="${charge.depositAmount }">&nbsp;押金：${charge.depositAmount }</td>
    					</c:if>
    					-->
    					<td align="" width="5%" class="head_form1" nowrap >旧单据号：${charge.billNum }</td>
    					<td align="" width="5%" class="head_form1" nowrap >新单据号：<input type="text" name="charge.billNum" style="width: 80"></td>
    				</tr>
    				<input type="hidden" name="chargeId" value="${charge.id }">
    				<input type="hidden" name="charge.id" value="${charge.id }">
    				<input type="hidden" name="charge.amount" value="${charge.amount }">
    				<input type="hidden" name="charge.clientId" value="${charge.clientId }">
    				<input type="hidden" name="charge.billType" value="${charge.billType }">
    				<input type="hidden" name="charge.billAmount" value="${charge.billAmount }">
    				<input type="hidden" name="charge.temporalAmount" value="${charge.temporalAmount }">
    				<input type="hidden" name="charge.advanceAmount" value="${charge.advanceAmount }">
    				<input type="hidden" name="charge.depositAmount" value="${charge.depositAmount }">
    				<input type="hidden" name="charge.date" value="${charge.date }">
	    		</table></td>
	    	</tr>			
			<tr>
				<td  align="right" ><input type="button" class="button" value="退款" onclick="save()"></td>
			</tr>
		</table>
	</form>
  </body>
  <script type="text/javascript">
  	function draw(){
  		parent.document.URL="<%=path%>/meterInput.do?method=getMTree3";
  	}
  	function save(){
  		var x = Validator.Validate(document.getElementById("formEdit"),2);
  		if(x){
  			var checkbox = document.getElementsByName("checkbox");
  			var sfqx = "1";
  			var isSelect = false;
  			for(var i=0;i<checkbox.length;i++){
  				if(checkbox[i].checked==false){
  					sfqx = "0";
  				}else {
  					isSelect = true;
  				}
  			}
  			/**
  			var temporal = document.getElementsByName("temporal.amount")[0];
  			var advance = document.getElementsByName("advance.amount")[0];
  			var deposit = document.getElementsByName("deposit.amount")[0];
  			if(typeof(temporal)!="undefined" ){
  				if(temporal.checked)
  					isSelect = true;
  				else	
  					sfqx = "0";
  			}
  			if(typeof(advance)!="undefined"){
  				if(advance.checked)
  					isSelect = true;
  				else 
  					sfqx = "0";
  			}
  			if(typeof(deposit)!="undefined" ){
  				if(deposit.checked)
  					isSelect = true;
  				else	
  					sfqx = "0";
  			}**/
  			if(isSelect){
  				var billNum = document.getElementsByName("charge.billNum")[0].value;
	  			if(sfqx=="0" && (billNum == null || billNum == "" ) ){
	  				alert("请输入单据号！");
	  				return false;
	  			}
	  			document.forms[0].action = "<%=path%>/bill.do?method=refundBill&sfqx="+sfqx;
	  			document.forms[0].submit();
	  		}else{
	  			alert("请选择要退的款项！");
	  			return false;
	  		}
  		}
  	}
  	function cx(){
  		document.forms[0].action = "<%=path%>/bill.do?method=getRefundList";
  		document.forms[0].submit();
  	}
  </script>
</html>
