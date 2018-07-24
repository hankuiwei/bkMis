<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
<%@page import="org.apache.log4j.Logger"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.zc13.util.*;"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head_4>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta name="robots" content="noindex, nofollow" />
<title>账单</title>
<script language="javascript">
	//使界面最大化
	maxWin();
	function maxWin()
	{
	      var aw = screen.availWidth;
	      var ah = screen.availHeight;
	      window.moveTo(0, 0);
	      window.resizeTo(aw, ah);
	}
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
<style type="text/css">
.STYLE1 {font-size: 12px}
.style3 {text-decoration: underline;}
.form { 
	/**border-bottom: 1px solid #000000 ;
	border-right: 1px solid #000000;
	*/
	font-size: 14px;
	text-align: center;
	height: 25px;
}
.tab{
	/**border-top: 1px solid #000000 ;
	border-left: 1px solid #000000;**/
}
</style>
<OBJECT   id=WebBrowser   classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2   height=0   width=0 VIEWASTEXT></OBJECT>   
<style>
@media print {
.noprint {display:none}
.PageNext{page-break-after: always;}   
}
.STYLE2 {font-size: 12}
</style>
<body>
<form name="" method="POST" >
   <%try{ %>
  <table width="800" align="center" cellpadding="0" cellspacing="0">
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
      <td><table width="800" align="center" cellpadding="0" cellspacing="0" style="border: 0px solid #000000;">
      
          <tr>
            <td width="30">&nbsp;</td>
            <td><TABLE  width="100%" align="center" border=0>
                <tr>
                    <td colspan="3" align="center">收费统计&nbsp;</td>
                 </tr>
          	</table></td>
          	<td width="30">&nbsp;</td>
          </tr>
		 <!--<tr>
		 	 <td width="30">&nbsp;</td>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	  		  	<tr>
					<td align="">客户：${client.name }</td>
					<td align="">总金额：${amount }</td><td></td>
			 	</tr>
         	</table></td>
         	 <td width="30">&nbsp;</td>
          </tr>-->
          <tr>
          	 <td width="30">&nbsp;</td>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0" class="" border="1">
					<tr>
					    <th width="5%" nowrap="nowrap" class="" align="center">序号</th>
               			<th nowrap="nowrap" class="" align="center" width="10%">收款日期</th>
			    		<th nowrap="nowrap" class="" align="center" width="10%">付款方式</th>
			    		<th nowrap="nowrap" class="" align="center" width="10%">单据类型</th>
			    		<th nowrap="nowrap" class="" align="center" width="10%">单据号</th>
			    		<th nowrap="nowrap" class="" align="center" width="10%">支票号</th>
			    		<th nowrap="nowrap" class="" align="center" width="10%">收款员</th>
			    		<th nowrap="nowrap" class="" align="center" width="10%">合同金额</th>
			    		<th nowrap="nowrap" class="" align="center" width="10%">暂存款</th>
			    		<th nowrap="nowrap" class="" align="center" width="10%">总金额</th>
					</tr>
					<c:set var="countAmount" value="0"></c:set>
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
						    		<td class="form" align="center">${vs.count }</td>
						    		<td class="form">&nbsp;${v[0].date }</td>
						    		<td class="form">&nbsp;
						    			<c:if test="${v[0].payType=='0' }" >现金</c:if>
						    			<c:if test="${v[0].payType=='1' }" >支票</c:if>
						    			<c:if test="${v[0].payType=='2' }" >转账</c:if>
						    			<c:if test="${v[0].payType=='3' }" >刷卡</c:if>
						    			<c:if test="${v[0].payType=='4' }" >定金转押金</c:if>
						    			<c:if test="${v[0].payType=='5' }" >定金转预收房租</c:if>
						    		</td>
						    		<td class="form">&nbsp;<c:if test="${v[0].billType=='0' }" >收据</c:if><c:if test="${v[0].billType=='1' }" >发票</c:if> </td>
						    		<td class="form">&nbsp;${v[0].billNum }</td>
						    		<td class="form">&nbsp;${v[0].chequeNo }</td>
						    		<td class="form">&nbsp;${v[2].realName }</td>
						    		<td class="form">&nbsp;<script>document.write(formatNum(round(${v[0].billAmount },2)));</script></td>
						    		<td class="form">&nbsp;<script>document.write(formatNum(round(${v[0].temporalAmount },2)));</script></td>
						    		<td class="form">&nbsp;<script>document.write(formatNum(round(${v[0].amount },2)));</script></td>
						    		
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
					<tr>
						<td class="" style="text-align:left;" colspan="10">&nbsp;合计：总金额(<script>document.write(formatNum(round(${countAmount },2)));</script>)，现金(<script>document.write(formatNum(round(${xj },2)));</script>)，支票(<script>document.write(formatNum(round(${zp },2)));</script>)，转账(<script>document.write(formatNum(round(${zz },2)));</script>)，刷卡(<script>document.write(formatNum(round(${sk },2)));</script>)，定金转押金(<script>document.write(formatNum(round(${yj },2)));</script>)，定金转预收房租(<script>document.write(formatNum(round(${ysfz },2)));</script>)</td>
					</tr>
				</table>
			</td>
			 <td width="30">&nbsp;</td>
		</tr>
		<tr><td height="10">&nbsp;</td></tr>
	</table></td>
</tr>
<tr><td height="40">&nbsp;</td></tr>	
	
	<tr><td height="40">&nbsp;</td></tr>	
	
  </table>
</form>
<%}catch(Exception e){
	e.printStackTrace();
} %>
</body>
</html>

