<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>收据列表-打印</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<style type="text/css">
	.STYLE1 {font-size: 12px}
	.style3 {text-decoration: underline;}
	.head { 
		border-bottom: 1px solid #000000 ;
		border-right: 1px solid #000000;
		font-size:16px;
	}
	.form { 
		border-bottom: 1px solid #000000 ;
		border-right: 1px solid #000000;
		font-size:14px;
	}
	.tab{
		border-top: 1px solid #000000 ;
		border-left: 1px solid #000000;
	}
	</style>
	<OBJECT   id=WebBrowser   classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2   height=0   width=0></OBJECT>   
	<style>
	@media print {
	.noprint {display:none}
	.PageNext{page-break-after: always;}   
	}
	.STYLE2 {font-size: 12}
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
		function formatNum(strNum) {
	    	if(strNum.length <= 3){  
	        	return strNum;  
	    	}  
	    	if(!/^(\+|-)?(\d+)(\.\d+)?$/.test(strNum)){  
	        	return strNum;  
			}  
			var a = RegExp.$1, b = RegExp.$2, c = RegExp.$3;  
	    	var re = new RegExp();  
	    	re.compile("(\\d)(\\d{3})(,|$)");  
	    	while(re.test(b)) {  
	        	b = b.replace(re, "$1,$2$3");  
	    	}
	    	return a +""+ b +""+ c;  
		}
	</script>
  </head>
  <!-- 加载页面div -->
	<jsp:include page="../../../../loading.jsp"></jsp:include>
  <!-- 加载页面div -->
  <body>
  <table width="1200" align="center" cellpadding="0" cellspacing="0">
  	<tr>
		<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
  	 	<tr class="noprint">                
         	<td>&nbsp;</td>    
                   	<TD width="81" align=right><input type="button" onclick="javascript:window.print()"  value="打印"></TD>
                   <TD width="81" align=right><input type="button" onclick="window.close();"  value="返回"></TD>
    		</tr>
    	</table></td>
    </tr>
    <tr>
      <td><table width="1200" align="center" cellpadding="0" cellspacing="0" style="border: 0px solid #000000;">
          <tr>
            <td width="30">&nbsp;</td>
            <td><TABLE  width="100%" align="center" border=0>
                <tr>
                    <td colspan="3" align="center" style="font-size:16px;">收据列表&nbsp;</td>
                 </tr>
          	</table></td>
          	<td width="30">&nbsp;</td>
          </tr>
		  
          <tr>
          	 <td width="30">&nbsp;</td>
			 <td>
				<table width="100%" cellpadding="0" cellspacing="0" class="tab">
					<tr>
			    		<th class="form" width="6%">序号</th>
			    		<th class="form" width="8%">客户名称</th>
			    		<th class="form" width="8%">收款日期</th>
			    		<th class="form" width="8%">收据号</th>
			    		<th class="form" width="8%">收款员</th>
			    		<th class="form" width="8%">总金额</th>
			    		<th class="form" width="8%">项目明细</th>
			    		<th class="form" width="8%">金额明细</th>
			    		<th class="form" width="8%">已开发票金额</th>
			    		<th class="form" width="8%">发票号</th>
			    		<th class="form" width="8%">未开发票金额</th>
					</tr>
					<c:choose>
			    		<c:when test="${empty invoiceForm.receiptList}">
			    			<tr align="center">
								<td colspan="10" align="center" class="head_form1">
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
										<td class="form"  align="center" rowspan="${v.rowspan }">${vs.count }</td>
										<td class="form" rowspan="${v.rowspan }">&nbsp;${v.clientName }</td>
										<td class="form" rowspan="${v.rowspan }">&nbsp;${v.reciveCostDate }</td>
										<td class="form" rowspan="${v.rowspan }">&nbsp;${v.billNum }</td>
										<td class="form" rowspan="${v.rowspan }">&nbsp;${v.reciveUserName }</td>
										<td class="form" rowspan="${v.rowspan }">&nbsp;<script>document.write(formatNum(parseFloat(${v.amount }).toFixed(2).toString()));</script></td>
										</c:if>
										<td class="form">&nbsp;${item.standardName }</td>
										<td class="form">&nbsp;${item.moneydetail }</td>
										<td class="form">&nbsp;<script>document.write(formatNum(parseFloat(${item.invoicAmount }).toFixed(2).toString()));</script></td>
										<td class="form">&nbsp;${item.invoiceId }</td>
										<td class="form">&nbsp;<script>document.write(formatNum(parseFloat(${item.notalreadyopen }).toFixed(2).toString()));</script></td>    		
										<c:set var="totalopenamount" value="${totalopenamount + item.invoicAmount}"></c:set>
				    					<c:set var="totalnotopenamount" value="${totalnotopenamount + item.notalreadyopen}"></c:set>
									</tr>
								</c:forEach>	
			    			</c:forEach>
			    		</c:otherwise>
			    	</c:choose>
				</table>
			 </td>
			 <td width="30">&nbsp;</td>
		  </tr>
		  <tr><td height="10">&nbsp;</td></tr>
	  </table></td>
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
						</tr>
					</table>
				</td>
	</tr>
  </table>
  </body>
</html>
