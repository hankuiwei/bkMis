<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*,com.zc13.util.*" errorPage="" %>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page import="com.zc13.util.*,com.zc13.bkmis.mapping.*;"%>
<%String path = request.getContextPath();
CCharge charge = (CCharge)request.getAttribute("charge");
String date = charge.getDate();
String[] time = date.split("-");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head_4>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="robots" content="noindex, nofollow" />
<title>收据</title>
<script type="text/javascript" src="<%=path %>/resources/js/transform.js"></script>
<script language="javascript">
	//document.all.WebBrowser.ExecWB(7,1);
	//window.close();
	//使界面最大化
 	maxWin();
	function maxWin()
	{
		var aw = screen.availWidth;
		var ah = screen.availHeight;
		window.moveTo(0, 0);
		window.resizeTo(aw, ah);
	}
	//数字按三位一逗号显示
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
<style type="text/css">
.STYLE1 {font-size: 12px}
.style3 {text-decoration: underline;}
</style>
<OBJECT   id=WebBrowser   classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2   height=0   width=0></OBJECT>   
<style>
@media print {
.noprint {display:none}
}
.STYLE2 {font-size: 12}
</style>
<body>
<form name="" method="POST" >
   <%try{ %>
  <table width="600" align="center" cellpadding="0" cellspacing="0">
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
      <td ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="border: 1px solid #000000;">
          <tr>
            <td><TABLE  width="100%" align="center" border=0>
                <tr>
                    <td colspan="3" align="center">北控宏创产业园&nbsp;</td>
                 </tr>
          	</table></td>
          </tr>
		 <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	  		  <tr>
	  		  	<td width="30">&nbsp;</td>
	            <td width="260" height="30"><span class="style3"><%=time[0] %></span>年<span class="style3"><%=time[1] %></span>月<span class="style3"><%=time[2] %></span>日</td>
	            <td nowrap="nowrap" align="right" colspan="2">NO.<span class="style3">${charge.billNum }</span></td>
	            <td width="30">&nbsp;</td>
	          </tr>
         	</table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	          <tr>
	          	 <td width="30">&nbsp;</td>
	            <td width="70" height="30" rowspan="" colspan="" style="white-space: nowrap;vertical-align: bottom;">今收到</td>
	            <td width="470" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom">${compactClient.unitName }(${roomCode }房间)</td>
	            <td width="30">&nbsp;</td>
	          </tr>
          	</table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	          <tr>
	            <td width="30">&nbsp;</td>
	            <td width="50" height="30" nowrap="nowrap" style="vertical-align: bottom;">交来</td>
	          	<td width="490" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom">
	          	(<c:if test="${charge.payType=='0' }" >现金</c:if>
				<c:if test="${charge.payType=='1' }" >支票，支票号：${charge.chequeNo }</c:if>
				<c:if test="${charge.payType=='2' }" >转账</c:if>
				<c:if test="${charge.payType=='3' }" >刷卡</c:if>
				<c:if test="${charge.payType=='4' }" >定金转押金</c:if>
				<c:if test="${charge.payType=='5' }" >定金转预收房租</c:if>)
				<script>document.write(formatNum(parseFloat(${charge.amount }).toFixed(2).toString()));</script>元整
	          	</td>
	            <td width="30">&nbsp;</td>
	          </tr>
          </table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	          <tr>
	          	 <td width="30">&nbsp;</td>
	            <td width="130" height="30" nowrap="nowrap" style="vertical-align: bottom;">人民币（大写）</td>
	          	<td width="410" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom;">
	          		<script>document.write(transform1(${charge.amount}));</script>整
	          	</td>
	            <td width="30">&nbsp;</td>
	          </tr>
          </table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
		 	  <tr>
	          	 <td width="30">&nbsp;</td>
	            <td width="540" height="30" nowrap="nowrap" style="vertical-align: bottom;" colspan="2">具体费用包括：</td>
	            <td width="30">&nbsp;</td>
	          </tr>
	          <c:if test="${!empty list}">
	          <tr>
	          	<td width="30">&nbsp;</td>
	          	<td width="540" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom;" colspan="2">
	          	<c:forEach items="${list}" var="v" varStatus="vs">
	          		<c:choose>
						<c:when test="${!empty v[0].itemId}">
							<c:forEach items="${itemList}" var="a">
								<c:if test="${v[0].itemId==a.id }">${a.itemName}:</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							${v[0].standardName }:
						</c:otherwise>	
					</c:choose>
					<script>document.write(formatNum(parseFloat(${v[0].actuallyPaid }).toFixed(2).toString()));</script>元整；
					<c:if test="${vs.count%3==0}">
						</td>
			            <td width="30">&nbsp;</td>
			          </tr>
			          <tr>
			          	<td width="30">&nbsp;</td>
			          	<td width="540" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom;" colspan="2">
					</c:if>
	          	</c:forEach>
	          	</td>
	            <td width="30">&nbsp;</td>
	          </tr>
	          </c:if>
          </table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	          <tr>
	          	<td width="30"></td>
	            <td width="240" nowrap="nowrap" height="30" style="vertical-align: bottom;">收款单位 财务章______________</td>
	            <td width="150" nowrap="nowrap" height="30" style="vertical-align: bottom;">收款人：<span class="style3">&nbsp;&nbsp;&nbsp;&nbsp;${userName }&nbsp;&nbsp;&nbsp;&nbsp;</font></td>
	            <td width="150" nowrap="nowrap" height="30" style="vertical-align: bottom;">交款人：__________</td>
	            <td width="30">&nbsp;</td>
	          </tr>   
	          <tr>
	          	<td width="30"></td>
	            <td width="570" nowrap="nowrap" height="40" colspan="4">第一联 存根</td>
	          </tr>   
		</table></td></tr>		
</table></td></tr>
	<tr><td height="40">&nbsp;</td></tr>	
	<tr>
      <td ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="border: 1px solid #000000;">
          <tr>
            <td><TABLE  width="100%" align="center" border=0>
                <tr>
                    <td colspan="3" align="center">北控宏创产业园&nbsp;</td>
                 </tr>
          	</table></td>
          </tr>
		 <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	  		  <tr>
	  		  	<td width="30">&nbsp;</td>
	            <td width="260" height="30"><span class="style3"><%=time[0] %></span>年<span class="style3"><%=time[1] %></span>月<span class="style3"><%=time[2] %></span>日</td>
	            <td nowrap="nowrap" align="right" colspan="2">NO.<span class="style3">${charge.billNum }</span></td>
	            <td width="30">&nbsp;</td>
	          </tr>
         	</table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	          <tr>
	          	 <td width="30">&nbsp;</td>
	            <td width="70" height="30" rowspan="" colspan="" style="white-space: nowrap;vertical-align: bottom;">今收到</td>
	            <td width="470" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom">${compactClient.unitName }(${roomCode }房间)</td>
	            <td width="30">&nbsp;</td>
	          </tr>
          	</table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	          <tr>
	            <td width="30">&nbsp;</td>
	            <td width="50" height="30" nowrap="nowrap" style="vertical-align: bottom;">交来</td>
	          	<td width="490" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom">
	          	(<c:if test="${charge.payType=='0' }" >现金</c:if>
				<c:if test="${charge.payType=='1' }" >支票，支票号：${charge.chequeNo }</c:if>
				<c:if test="${charge.payType=='2' }" >转账</c:if>
				<c:if test="${charge.payType=='3' }" >刷卡</c:if>
				<c:if test="${charge.payType=='4' }" >定金转押金</c:if>
				<c:if test="${charge.payType=='5' }" >定金转预收房租</c:if>)
	          	<script>document.write(formatNum(parseFloat(${charge.amount }).toFixed(2).toString()));</script>元整
	          	</td>
	            <td width="30">&nbsp;</td>
	          </tr>
          </table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	          <tr>
	          	 <td width="30">&nbsp;</td>
	            <td width="130" height="30" nowrap="nowrap" style="vertical-align: bottom;">人民币（大写）</td>
	          	<td width="410" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom;">
	          		<script>document.write(transform1(${charge.amount}));</script>整
	          	</td>
	            <td width="30">&nbsp;</td>
	          </tr>
          </table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
		 	  <tr>
	          	 <td width="30">&nbsp;</td>
	            <td width="540" height="30" nowrap="nowrap" style="vertical-align: bottom;" colspan="2">具体费用包括：</td>
	            <td width="30">&nbsp;</td>
	          </tr>
	          <c:if test="${!empty list}">
	          <tr>
	          	<td width="30">&nbsp;</td>
	          	<td width="540" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom;" colspan="2">
	          	<c:forEach items="${list}" var="v" varStatus="vs">
	          		<c:choose>
						<c:when test="${!empty v[0].itemId}">
							<c:forEach items="${itemList}" var="a">
								<c:if test="${v[0].itemId==a.id }">${a.itemName}:</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							${v[0].standardName }:
						</c:otherwise>	
					</c:choose>
					<script>document.write(formatNum(parseFloat(${v[0].actuallyPaid }).toFixed(2).toString()));</script>元整；
					<c:if test="${vs.count%3==0}">
						</td>
			            <td width="30">&nbsp;</td>
			          </tr>
			          <tr>
			          	<td width="30">&nbsp;</td>
			          	<td width="540" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom;" colspan="2">
					</c:if>
	          	</c:forEach>
	          	</td>
	            <td width="30">&nbsp;</td>
	          </tr>
	          </c:if>
          </table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	          <tr>
	          	<td width="30"></td>
	            <td width="240" nowrap="nowrap" height="30" style="vertical-align: bottom;">收款单位 财务章______________</td>
	            <td width="150" nowrap="nowrap" height="30" style="vertical-align: bottom;">收款人：<span class="style3">&nbsp;&nbsp;&nbsp;&nbsp;${userName }&nbsp;&nbsp;&nbsp;&nbsp;</font></td>
	            <td width="150" nowrap="nowrap" height="30" style="vertical-align: bottom;">交款人：__________</td>
	            <td width="30">&nbsp;</td>
	          </tr>   
	          <tr>
	          	<td width="30"></td>
	            <td width="570" nowrap="nowrap" height="40" colspan="4">第二联 记账凭证</td>
	          </tr>   
		</table></td></tr>		
</table></td></tr>
	<tr><td height="40">&nbsp;</td></tr>	
	<tr>
      <td ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="border: 1px solid #000000;">
          <tr>
            <td><TABLE  width="100%" align="center" border=0>
                <tr>
                    <td colspan="3" align="center">北控宏创产业园&nbsp;</td>
                 </tr>
          	</table></td>
          </tr>
		 <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	  		  <tr>
	  		  	<td width="30">&nbsp;</td>
	            <td width="260" height="30"><span class="style3"><%=time[0] %></span>年<span class="style3"><%=time[1] %></span>月<span class="style3"><%=time[2] %></span>日</td>
	            <td nowrap="nowrap" align="right" colspan="2">NO.<span class="style3">${charge.billNum }</span></td>
	            <td width="30">&nbsp;</td>
	          </tr>
         	</table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	          <tr>
	          	 <td width="30">&nbsp;</td>
	            <td width="70" height="30" rowspan="" colspan="" style="white-space: nowrap;vertical-align: bottom;">今收到</td>
	            <td width="470" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom">${compactClient.unitName }(${roomCode }房间)</td>
	            <td width="30">&nbsp;</td>
	          </tr>
          	</table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	          <tr>
	            <td width="30">&nbsp;</td>
	            <td width="50" height="30" nowrap="nowrap" style="vertical-align: bottom;">交来</td>
	          	<td width="490" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom">
	          	(<c:if test="${charge.payType=='0' }" >现金</c:if>
				<c:if test="${charge.payType=='1' }" >支票，支票号：${charge.chequeNo }</c:if>
				<c:if test="${charge.payType=='2' }" >转账</c:if>
				<c:if test="${charge.payType=='3' }" >刷卡</c:if>
				<c:if test="${charge.payType=='4' }" >定金转押金</c:if>
				<c:if test="${charge.payType=='5' }" >定金转预收房租</c:if>)
	          	<script>document.write(formatNum(parseFloat(${charge.amount }).toFixed(2).toString()));</script>元整
	          	</td>
	            <td width="30">&nbsp;</td>
	          </tr>
          </table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	          <tr>
	          	 <td width="30">&nbsp;</td>
	            <td width="130" height="30" nowrap="nowrap" style="vertical-align: bottom;">人民币（大写）</td>
	          	<td width="410" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom;">
	          		<script>document.write(transform1(${charge.amount}));</script>整
	          	</td>
	            <td width="30">&nbsp;</td>
	          </tr>
          </table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
		 	  <tr>
	          	 <td width="30">&nbsp;</td>
	            <td width="540" height="30" nowrap="nowrap" style="vertical-align: bottom;" colspan="2">具体费用包括：</td>
	            <td width="30">&nbsp;</td>
	          </tr>
	          <c:if test="${!empty list}">
	          <tr>
	          	<td width="30">&nbsp;</td>
	          	<td width="540" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom;" colspan="2">
	          	<c:forEach items="${list}" var="v" varStatus="vs">
	          		<c:choose>
						<c:when test="${!empty v[0].itemId}">
							<c:forEach items="${itemList}" var="a">
								<c:if test="${v[0].itemId==a.id }">${a.itemName}:</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							${v[0].standardName }:
						</c:otherwise>	
					</c:choose>
					<script>document.write(formatNum(parseFloat(${v[0].actuallyPaid }).toFixed(2).toString()));</script>元整；
					<c:if test="${vs.count%3==0}">
						</td>
			            <td width="30">&nbsp;</td>
			          </tr>
			          <tr>
			          	<td width="30">&nbsp;</td>
			          	<td width="540" style="white-space: nowrap;border-bottom: 1px solid #000000;padding-right: 10;vertical-align: bottom;" colspan="2">
					</c:if>
	          	</c:forEach>
	          	</td>
	            <td width="30">&nbsp;</td>
	          </tr>
	          </c:if>
          </table></td>
          </tr>
          <tr>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	          <tr>
	          	<td width="30"></td>
	            <td width="240" nowrap="nowrap" height="30" style="vertical-align: bottom;">收款单位 财务章______________</td>
	            <td width="150" nowrap="nowrap" height="30" style="vertical-align: bottom;">收款人：<span class="style3">&nbsp;&nbsp;&nbsp;&nbsp;${userName }&nbsp;&nbsp;&nbsp;&nbsp;</font></td>
	            <td width="150" nowrap="nowrap" height="30" style="vertical-align: bottom;">交款人：__________</td>
	            <td width="30">&nbsp;</td>
	          </tr>   
	          <tr>
	          	<td width="30"></td>
	            <td width="570" nowrap="nowrap" height="40" colspan="4">第三联 收据</td>
	          </tr>   
		</table></td></tr>		
</table></td></tr>
  </table>
</form>
<%}catch(Exception e){
	e.printStackTrace();
} %>
</body>
</html>

