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
<meta name="robots" content="noindex, nofollow" />
<title>通知单</title>
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
	border-bottom: 1px solid #000000 ;
	border-right: 1px solid #000000;
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
<body>
<form name="" method="POST" >
   <%try{ %>
   <input type="hidden" name="clientId" value="">
   <input type="hidden" name="noticeDate" value="">
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
    <c:forEach items="${list}" var="v1" varStatus="vs1">
    <tr>
      <td><table width="600" align="center" cellpadding="0" cellspacing="0" style="border: 0px solid #000000;">
      
          <tr>
            <td width="30">&nbsp;</td>
            <td><TABLE  width="100%" align="center" border=0>
                <tr>
                    <td colspan="3" align="center">
	                    <font style="font-weight:700;font-size:20px;"><c:if test="${v1.noticeType eq '2'}">催款</c:if><c:if test="${v1.noticeType ne '2'}">缴款</c:if>通知单
	                   </font>&nbsp;
                   </td>
                 </tr>
          	</table></td>
          	<td width="30">&nbsp;</td>
          </tr>
          <tr>
			<td colspan="3">&nbsp;</td>
		 </tr>
		 <tr>
		 	 <td width="30">&nbsp;</td>
		 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	  		  	<tr>
					<td align="">${v1.clientName }(${v1.roomCode}房间)</td>
			 	</tr>
			 	<tr>
			 		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贵司所租赁的办公区域。依据合同约定，贵司应于本月20日前缴纳下列费用：</td>
			 	</tr>
			 	<tr>
			 		<td align="center">费用明细</td>
			 	</tr>
         	</table></td>
         	 <td width="30">&nbsp;</td>
          </tr>
          <tr>
          	 <td width="30">&nbsp;</td>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0" class="tab">
					<tr>
					    <td width="5%" nowrap="nowrap" class="form" align="center">序号</td>
					    <td width="30%" nowrap="nowrap" class="form" align="center">项目</td>
               			<td width="15%" nowrap="nowrap" class="form" align="center">账单月份</td>
               			<!-- 
               			<td width="15%" nowrap="nowrap" class="form" align="center">关帐日期</td>
               			<td width="20%" nowrap="nowrap" class="form" align="center">房间号</td>
               			 -->
               			<td width="20%" nowrap="nowrap" class="form" align="center">金额</td>
					</tr>
					<c:forEach items="${v1.noticeList}" varStatus="vs" var="v">
						<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
							<td class="form">&nbsp;${vs.index+1 }</td>
							<td class="form">&nbsp;${v.itemName }</td>
							<td class="form">&nbsp;${v.billDate }</td>
							<!-- 
							<td class="form">&nbsp;${v.closeDate }</td>
							<td class="form">&nbsp;${v.roomCode }</td> 
							-->
							<td class="form">&nbsp;<script>document.write(formatNum(parseFloat(${v.amount }).toFixed(2).toString()))</script></td>
						</tr>
					</c:forEach>
					
				</table>
			</td>
			 <td width="30">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td align="left">总金额：
			<script>
			document.write(formatNum(parseFloat(${v1.amount }).toFixed(2).toString()));
			</script>
			元
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为了维持优良的服务质量，敬请贵司按时缴纳，我司深表感谢！如果您对上述缴费金额有异议，请您直接致电我司财务部垂询，电话：</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td align="right">北京北控科技有限公司</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td align="right">年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr><td height="10">&nbsp;</td></tr>
		
		<c:if test="${vs1.index>0}">
		<div class="PageNext"></div> 
		</c:if>
	</table></td>
</tr>
<tr><td height="40">&nbsp;</td></tr>	
</c:forEach>
	
	<tr><td height="40">&nbsp;</td></tr>	
	
  </table>
</form>
<%}catch(Exception e){
	e.printStackTrace();
} %>
</body>
</html>

