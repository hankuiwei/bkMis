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
    
    <title>收费统计</title>
    
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
  	<input type="hidden" name="isShowByUser" value="${isShowByUser}" />
    <table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="100" nowrap="nowrap" class="form_line">收费统计</td>
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
		  				<!--<td nowrap>单据号：<input type="text" style="width:80px;" name="charge.billNum" value="${CBillForm.charge.billNum }"></td>-->
		  				<td nowrap>付款方式：
	    				 		<select name="payType">
	    				 			<option value="">全部</option>
	    				 			<option value="0" <c:if test="${payType=='0' }">selected</c:if> >现金</option>
	    				 			<option value="1" <c:if test="${payType=='1' }">selected</c:if> >支票</option>
	    				 			<option value="2" <c:if test="${payType=='2' }">selected</c:if> >转账</option>
	    				 			<option value="3" <c:if test="${payType=='3' }">selected</c:if> >刷卡</option>
	    				 			<option value="4" <c:if test="${payType=='4' }">selected</c:if> >定金转押金</option>
	    				 			<option value="5" <c:if test="${payType=='5' }">selected</c:if> >定金转预收房租</option>
	    				 		</select>
	    				 	</td>
		  				<td nowrap>收款日期：<input type="text" style="width:88px;" name="begin" readonly onclick="WdatePicker();" class="Wdate" value="${CBillForm.begin }">
		  				至<input type="text" name="end" style="width:88px;" readonly onclick="WdatePicker();" class="Wdate" value="${CBillForm.end }">
		  				</td>
		  				<c:if test="${isShowByUser!='1'}">
		  				<td>
		  					收款人：
		  					<select name="userId">
		  								<option value="">所有</option>
		  						<c:if test="${!empty usersList}">
		  							<c:forEach items="${usersList}" var="v" varStatus="vs">
		  								<option value="${v.userid }" <c:if test="${v.userid==CBillForm.userId }">selected</c:if> >${v.realName }(${v.username })</option>
		  							</c:forEach>
		  						</c:if>
		  					</select>
		  				</td>
		  				</c:if>
		  				<td>
		  					支票号：<input type="text" name="chequeNo" value="${CBillForm.chequeNo }" style="width:100px;">
		  				</td>
		  				<td align="right">
		  					<input type="button" value="查询" onclick="cx()" class="button">
		  					<input type="button" value="导出报表" onclick="exportExcel('${size }')" class="button">
		  				</td>
		  			</tr>
		  			<tr>
						<td colspan="8">
							<span style=" color: blue;">注：在某一行上双击鼠标，可以查看详细内容</span>
						</td>
					</tr>
		  		</table></td>		
		  	</tr>
		  	<tr height="85%">
			    <td valign = "top">
			    	<!-- 表单内容区域 -->
					<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height = "95%">
			        		<td width="100%">
						  		<div id = "div1" class = "RptDiv"  ><table width="100%"  cellpadding="0" cellspacing="0" class="RptTable">
			    	<tr>
			    		<!--<th class="RptTableHeadCellLock"  width="5%"><input type="checkbox" onclick="checkAll(this,'checkbox')"></th>
			    		--><th class="RptTableHeadCellLock"  width="5%">序号</th>
			    		<th class="RptTableHeadCellLock"  width="10%">收款日期</th>
			    		<th class="RptTableHeadCellLock"  width="10%">付款方式</th>
			    		<th class="RptTableHeadCellLock"  width="10%">单据类型</th>
			    		<th class="RptTableHeadCellLock"  width="10%">单据号</th>
			    		<th class="RptTableHeadCellLock"  width="10%">支票号</th>
			    		<th class="RptTableHeadCellLock"  width="10%">收款员</th>
			    		<th class="RptTableHeadCellLock"  width="10%">合同金额</th>
			    		<th class="RptTableHeadCellLock"  width="10%">暂存款</th>
			    		<!--<th class="RptTableHeadCellLock"  width="10%">预收房租</th>
			    		<th class="RptTableHeadCellLock"  width="10%">押金</th>-->
			    		<th class="RptTableHeadCellLock"  width="10%">总金额</th>
			    		
			    	</tr>
			    	<c:set var="countAmount" value="0"></c:set>
			    	<!--<c:set var="countBillAmount" value="0"></c:set>
			    	<c:set var="countTemporalAmount" value="0"></c:set>
			    	<c:set var="countAdvanceAmount" value="0"></c:set>
			    	<c:set var="countDepositAmount" value="0"></c:set>-->
			    	<c:set var="xj" value="0"></c:set><!-- 现金 -->
			    	<c:set var="zp" value="0"></c:set><!-- 支票 -->
			    	<c:set var="zz" value="0"></c:set><!-- 转账 -->
			    	<c:set var="sk" value="0"></c:set><!-- 刷卡 -->
			    	<c:set var="yj" value="0"></c:set><!-- 订金转押金 -->
			    	<c:set var="ysfz" value="0"></c:set><!-- 订金转预收房租 -->
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
						    		<td class="RptTableBodyCell">&nbsp;${v[0].date }</td>
						    		<td class="RptTableBodyCell">&nbsp;
						    			<c:if test="${v[0].payType=='0' }" >现金</c:if>
						    			<c:if test="${v[0].payType=='1' }" >支票</c:if>
						    			<c:if test="${v[0].payType=='2' }" >转账</c:if>
						    			<c:if test="${v[0].payType=='3' }" >刷卡</c:if>
						    			<c:if test="${v[0].payType=='4' }" >定金转押金</c:if>
						    			<c:if test="${v[0].payType=='5' }" >定金转预收房租</c:if>
						    		</td>
						    		<td class="RptTableBodyCell">&nbsp;<c:if test="${v[0].billType=='0' }" >收据</c:if><c:if test="${v[0].billType=='1' }" >发票</c:if> </td>
						    		<td class="RptTableBodyCell">&nbsp;${v[0].billNum }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v[0].chequeNo }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v[2].realName }</td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(round(${v[0].billAmount },2)));</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(round(${v[0].temporalAmount },2)));</script></td>
						    		<!--<td class="RptTableBodyCell">&nbsp;${v[0].advanceAmount }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v[0].depositAmount }</td>-->
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(round(${v[0].amount },2)));</script></td>
						    		
						    	</tr>
						    	<c:set var="countAmount" value="${countAmount+v[0].amount}"></c:set>
						    	<!--<c:set var="countBillAmount" value="${countBillAmount+v[0].billAmount}"></c:set>
						    	<c:set var="countTemporalAmount" value="${countTemporalAmount+v[0].temporalAmount}"></c:set>
						    	<c:set var="countAdvanceAmount" value="${countAdvanceAmount+v[0].advanceAmount}"></c:set>
						    	<c:set var="countDepositAmount" value="${countDepositAmount+v[0].depositAmount}"></c:set>-->
						    	<c:if test="${v[0].payType=='0'}"><c:set var="xj" value="${xj+v[0].amount}"></c:set></c:if>
						    	<c:if test="${v[0].payType=='1'}"><c:set var="zp" value="${zp+v[0].amount}"></c:set></c:if>
						    	<c:if test="${v[0].payType=='2'}"><c:set var="zz" value="${zz+v[0].amount}"></c:set></c:if>
						    	<c:if test="${v[0].payType=='3'}"><c:set var="sk" value="${sk+v[0].amount}"></c:set></c:if>
						    	<c:if test="${v[0].payType=='4'}"><c:set var="yj" value="${yj+v[0].amount}"></c:set></c:if>
						    	<c:if test="${v[0].payType=='5'}"><c:set var="ysfz" value="${ysfz+v[0].amount}"></c:set></c:if>
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
						<tr>
							<td class="form_line3" style="text-align:left;">&nbsp;合计：总金额(<script>document.write(formatNum(round(${countAmount },2)));</script>)，现金(<script>document.write(formatNum(round(${xj },2)));</script>)，支票(<script>document.write(formatNum(round(${zp },2)));</script>)，转账(<script>document.write(formatNum(round(${zz },2)));</script>)，刷卡(<script>document.write(formatNum(round(${sk },2)));</script>)，定金转押金(<script>document.write(formatNum(round(${yj },2)));</script>)，定金转预收房租(<script>document.write(formatNum(round(${ysfz },2)));</script>)</td>
							<td class="form_line3">&nbsp;<input type="button" class="button" value="打印" onclick="printCharge()"></td>
						</tr>
					</table>
				</td>
			</tr>
			<!--<tr>
				<td  align="right" ><input type="button" class="button" value="退款" onclick="save()"></td>
			</tr>
	    -->
	</table>
	</td></tr></table></form>
  </body>
  <script type="text/javascript">
  	function cx(){
  		document.forms[0].action = "<%=path%>/bill.do?method=getChargeList";
  		document.forms[0].submit();
  	}
  	function refund(id,billId){
  		var URL = "zc13/bkmis/costManage/public_pop.jsp?URL=bill.do?method=getChargeBill//chargeId="+id+"//billId="+billId;
  		//window.open(URL);
  		var return_value = showModalDialog(URL,"","dialogWidth=600px;dialogHeight=340px;");
  		if(typeof(return_value)!="undefined" && return_value=="1"){
  			cx();
  		}
  	}
  	
  	function exportExcel(isNull){
      		if(isNull!='0') {
				document.forms[0].action = "<%=path%>/bill.do?method=explortExcel4Charge";
				document.forms[0].submit();
			} else {
				alert("没有记录可导出！");
			}
      }
     function printCharge(){
     	formEdit.action = "<%=path%>/bill.do?method=printCharge";
     	formEdit.target = "_blank";
     	formEdit.submit();
     	// var url = "<%=path%>/bill.do?method=printCharge";
     	// window.open(url);
     } 
  </script>
</html>
