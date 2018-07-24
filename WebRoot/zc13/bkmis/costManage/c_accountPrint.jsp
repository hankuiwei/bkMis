<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>账单统计-打印</title>
    
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
	<jsp:include page="../../../loading.jsp"></jsp:include>
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
                    <td colspan="3" align="center" style="font-size:16px;">账单统计&nbsp;</td>
                 </tr>
          	</table></td>
          	<td width="30">&nbsp;</td>
          </tr>
		  <tr>
		 	 <td width="30">&nbsp;</td>
		 	 <td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	  		  	<tr>
					<td align="">客户：${CBillForm.clientName }</td>
					<td align="">单据状态：<c:if test="${CBillForm.status=='0' }">未缴</c:if><c:if test="${CBillForm.status=='1' }">已缴</c:if></td>
					<td align="">收费日期：${CBillForm.begin }至${CBillForm.end }</td>
			 	</tr>
			 	<tr>
					<td align="">收费项目：
						<select name="itemId" disabled="disabled">
		  					<option value="">全部</option>
		  					<c:forEach items="${itemList}" var="v">
								<option value="${v.id }" <c:if test="${v.id==itemId }">selected</c:if>>${v.itemName }</option>		  						
		  					</c:forEach>
		  				</select>
					</td>
					<td align="">付款方式：
						<select name="payType" disabled="disabled">
    				 			<option value="">全部</option>
    				 			<option value="0" <c:if test="${payType=='0' }">selected</c:if> >现金</option>
    				 			<option value="1" <c:if test="${payType=='1' }">selected</c:if> >支票</option>
    				 			<option value="2" <c:if test="${payType=='2' }">selected</c:if> >转账</option>
    				 			<option value="3" <c:if test="${payType=='3' }">selected</c:if> >刷卡</option>
    				 			<option value="4" <c:if test="${payType=='4' }">selected</c:if> >定金转押金</option>
    				 			<option value="5" <c:if test="${payType=='5' }">selected</c:if> >定金转预收房租</option>
    				 	</select>
					</td>
					<td align="">账期：${CBillForm.before }至${CBillForm.after }</td>
			 	</tr>
         	 </table></td>
         	 <td width="30">&nbsp;</td>
          </tr>
          <tr>
          	 <td width="30">&nbsp;</td>
			 <td>
				<table width="100%" cellpadding="0" cellspacing="0" class="tab">
					<tr>
               			<th nowrap="nowrap" class="head" width="6%">序号</th>
			    		<th nowrap="nowrap" class="head" width="12%">客户名称</th>
			    		<th nowrap="nowrap" class="head" width="8%">收费项</th>
			    		<th nowrap="nowrap" class="head" width="8%">付款方式</th>
			    		<th nowrap="nowrap" class="head" width="6%">房号</th>
			    		<th nowrap="nowrap" class="head" width="8%">申请单号</th>
			    		<th nowrap="nowrap" class="head" width="8%">支票号</th>
			    		<th nowrap="nowrap" class="head"  width="7%">账单月份</th>
			    		<th nowrap="nowrap" class="head"  width="8%">单据状态</th>
			    		<th nowrap="nowrap" class="head"  width="8%">最后缴款日期</th>
			    		<th nowrap="nowrap" class="head"  width="7%">合同金额</th>
			    		<th nowrap="nowrap" class="head"  width="7%">滞纳金额</th>
			    		<th nowrap="nowrap" class="head"  width="12%">收费日期</th>
			    		<th nowrap="nowrap" class="head"  width="8%">实收金额</th>
					</tr>
					<c:choose>
			    		<c:when test="${empty list}">
			    			<tr align="center">
								<td colspan="13" align="center" class="head_form1">
									<font color="red">没有信息!</font>
								</td>
							</tr>
			    		</c:when>
			    		<c:otherwise>
			    			<c:forEach items="${list}" var="v" varStatus="vs">
			    				<tr>
						    		<td class="form"  align="center">${vs.count }</td>
						    		<td class="form">&nbsp;${v.compactClient.name }</td>
						    		<td class="form">&nbsp;
										<c:choose>
						    				<c:when test="${!empty v.itemId}">
						    					<c:forEach items="${itemList}" var="a">
							    					<c:if test="${v.itemId==a.id }">${a.itemName}</c:if>
							    				</c:forEach>
						    				</c:when>
							    			<c:otherwise>
							    				${v.standardName }
							    			</c:otherwise>	
						    			</c:choose>
									</td>
						    		<td class="form">&nbsp;
											<c:if test="${v.paymentWay=='0' }" >现金</c:if>
							    			<c:if test="${v.paymentWay=='1' }" >支票</c:if>
							    			<c:if test="${v.paymentWay=='2' }" >转账</c:if>
							    			<c:if test="${v.paymentWay=='3' }" >刷卡</c:if>
							    			<c:if test="${v.paymentWay=='4' }" >定金转押金</c:if>
							    			<c:if test="${v.paymentWay=='5' }" >定金转预收房租</c:if>
									</td>
						    		<td class="form">&nbsp;${v.ERooms.roomCode }</td>
						    		<td class="form">&nbsp;${v.billCode }</td>
						    		<td class="form">&nbsp;${v.chequeNo }</td>
						    		<td class="form">&nbsp;${v.billDate }</td>
						    		<td class="form">&nbsp;<c:if test="${v.status=='0'}">未缴</c:if><c:if test="${v.status=='1'}">已缴</c:if></td>
						    		<td class="form">&nbsp;${v.closeDate }</td>
						    		<td class="form">&nbsp;<script>document.write(formatNum(round(${v.billsExpenses },2)));</script></td>
						    		<td class="form">&nbsp;<script>document.write(formatNum(round(${v.delayingExpenses },2)));</script></td>
						    		<td class="form">&nbsp;${v.collectionDate }</td>
						    		<td class="form">&nbsp;<script>document.write(formatNum(round(${v.actuallyPaid },2)));</script></td>
						    	</tr>
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
							<td align="right" style="padding-left: 3px;">
								实收总金额：</td><td>（<script>document.write(formatNum(round(${totalAmount },2)));</script>），</td><td align="right">合同金额：</td><td>（<script>document.write(formatNum(round(${billAmount },2)));</script>），</td><td align="right">定金转押金：</td><td>（<script>document.write(formatNum(round(${toyj },2)));</script>），</td><td align="right">定金转预收房租：</td><td>（<script>document.write(formatNum(round(${tofz },2)));</script>）
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td align="right" style="padding-left: 3px;">
								预收房租：</td><td>（<script>document.write(formatNum(round(${adv },2)));</script>），</td><td align="right">房租押金：</td><td>（<script>document.write(formatNum(round(${fzdes },2)));</script>），</td><td align="right">装修押金：</td><td>（<script>document.write(formatNum(round(${zxdes },2)));</script>），</td><td align="right">暂存款：</td><td>（<script>document.write(formatNum(round(${tem },2)));</script>），</td><td align="right">定金：</td><td>（<script>document.write(formatNum(round(${ear },2)));</script>）
							</td>
						</tr>
						<tr>
							<td  align="right" style="padding-left: 3px;">
								现金：</td><td>（<script>document.write(formatNum(round(${xj },2)));</script>），</td><td align="right">支票：</td><td>（<script>document.write(formatNum(round(${zp },2)));</script>），</td><td align="right">转账：</td><td>（<script>document.write(formatNum(round(${zz },2)));</script>），</td><td align="right">刷卡：</td><td>（<script>document.write(formatNum(round(${sk },2)));</script>）
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
				</td>
	</tr>
  </table>
  </body>
</html>
