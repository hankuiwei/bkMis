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
  	<form name="formEdit" method="post" id="formEdit">
  	<input type="hidden" name="role" value="${CBillForm.role}" />
    <table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="100" nowrap="nowrap" class="form_line">客户退款</td>
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
		  				<td nowrap>单据号：<input type="text" name="charge.billNum" value="${CBillForm.charge.billNum }"></td>
		  				<td nowrap>收款日期：<input type="text"  style="width:88px;" name="begin" readonly onclick="WdatePicker();" class="Wdate" value="${CBillForm.begin }"><!--
		  				至<input type="text" name="end" readonly  style="width:88px;" onclick="WdatePicker();" class="Wdate" value="${CBillForm.end }">-->
		  				</td>
		  				<c:if test="${CBillForm.role == '0'}">
		  				<td>操作员：<select name="userId">
		  					<option value="">请选择...</option>
		  					<c:forEach var="v" items="${userList}">
		  						<option value="${v.userid }" <c:if test="${CBillForm.userId==v.userid }">selected</c:if> >${v.username }</option>
		  					</c:forEach>
		  				</select></td>
		  				</c:if>
		  				<td width="30%">&nbsp;</td>
		  				<td align="right"><input type="button" value="查询" onclick="cx()" class="button"></td>
		  			</tr>
		  			<tr>
						<td colspan="8">
							<span style=" color: blue;">注：在某一行上双击鼠标，打开退款弹出框</span>
						</td>
					</tr>
		  		</table></td>		
		  	</tr>
		  	<tr height="80%">
			    <td valign = "top">
			    	<!-- 表单内容区域 -->
					<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height = "95%">
			        		<td width="100%">
						  		<div id = "div1" class = "RptDiv"  ><table width="100%"  cellpadding="0" cellspacing="0" class="RptTable">
			    	<tr>
			    		<!--<th class="RptTableHeadCellLock"  width="5%"><input type="checkbox" onclick="checkAll(this,'checkbox')"></th>
			    		--><th class="RptTableHeadCellLock"  width="5%">序号</th>
			    		<th class="RptTableHeadCellLock"  width="9%">客户名称</th>
			    		<th class="RptTableHeadCellLock"  width="9%">单据类型</th>
			    		<th class="RptTableHeadCellLock"  width="9%">单据号</th>
			    		<th class="RptTableHeadCellLock"  width="9%">总金额</th>
			    		<th class="RptTableHeadCellLock"  width="9%">收款日期</th>
			    		<th class="RptTableHeadCellLock"  width="9%">合同金额</th>
			    		<th class="RptTableHeadCellLock"  width="9%">暂存款</th>
			    		<th class="RptTableHeadCellLock"  width="9%">预收款</th>
			    		<th class="RptTableHeadCellLock"  width="9%">预收物业费</th>
			    		<th class="RptTableHeadCellLock"  width="9%">押金</th>
			    		<th class="RptTableHeadCellLock"  width="9%">订金</th>
			    	</tr>
			    	<c:choose>
			    		<c:when test="${empty billList}">
			    			<tr align="center">
								<td colspan="12" align="center" class="head_form1">
									<font color="red">没有信息!</font>
								</td>
							</tr>
			    		</c:when>
			    		<c:otherwise>
			    			<c:forEach items="${billList}" var="v" varStatus="vs">
			    				<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="refund('${v[0].id }','${v[0].billId }')">
						    		<!--<td class="RptTableBodyCell"  align="center"><input type="checkbox" name="checkbox" value="${v[0].id }"></td>
						    		--><td class="RptTableBodyCell" align="center">${vs.count }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v[1].name }</td>
						    		<td class="RptTableBodyCell">&nbsp;<c:if test="${v[0].billType=='0' }" >收据</c:if><c:if test="${v[0].billType=='1' }" >发票</c:if> </td>
						    		<td class="RptTableBodyCell">&nbsp;${v[0].billNum }</td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].amount }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;${v[0].date }</td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].billAmount }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].temporalAmount }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].advanceAmount }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].advanceWuYFAmount }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].depositAmount }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].earnestAmount }).toFixed(2).toString()))</script></td>
						    	</tr>
			    			</c:forEach>
			    		</c:otherwise>
			    	</c:choose>
			    			</table></div>
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
				<td  align="right">
					<input type="button" value="去收款" onclick="draw()" class="button">
				</td>
			</tr>
			<!--<tr>
				<td  align="right" ><input type="button" class="button" value="退款" onclick="save()"></td>
			</tr>
	    -->
	 </table>
	</form>
  </body>
  <script type="text/javascript">
  	function draw(){
  		// document.forms[0].action = "<%=path%>/bill.do?method=getBillList";
  		document.forms[0].action = "<%=path%>/bill.do?method=queryCollectClient";
  		document.forms[0].submit();
  	}
  	function save(){
  		var x = Validator.Validate(document.getElementById("formEdit"),2);
  		if(x){
  			/**
	  		if(confirm("是否打印凭证？")){
	  			var yfje = document.getElementsByName("amount")[0].value;
	  			var sfje = "";
	  			var fbh = "";
	  			var sqye = "";
	  			var bqye = "";
	  			var URL = encodeURI(encodeURI("<%=path%>/zc13/bkmis/costManage/c_receipt.jsp?title=凭证&flag=khtk&yfje="+yfje+"&sfje="+sfje+"&fbh="+fbh+"&sqye="+sqye+"&bqye="+bqye));
	  			var return_value = showModalDialog(URL,"","dialogWidth=800px;dialogHeight=300px;");
	  		}*/
	  		document.forms[0].action = "<%=path%>/bill.do?method=editRefund";
	  		document.forms[0].submit();
  		}
  	}
  	function cx(){
  		document.forms[0].action = "<%=path%>/bill.do?method=getRefundList";
  		document.forms[0].submit();
  	}
  	function refund(id,billId){
  		var URL = "zc13/bkmis/costManage/public_pop.jsp?URL=bill.do?method=getRefundBill//chargeId="+id+"//billId="+billId;
  		var return_value = showModalDialog(URL,"","dialogWidth=600px;dialogHeight=330px;");
  		if(typeof(return_value)!="undefined" && return_value=="1"){
  			cx();
  		}
  	}
  </script>
</html>
