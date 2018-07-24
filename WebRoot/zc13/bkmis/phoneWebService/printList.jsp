<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@page import="com.zc13.util.GlobalMethod"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head_4>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="robots" content="noindex, nofollow" />
<title>电话费用详细</title>
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

		//将秒转换成时分秒
		function formatSeconds(value) {   
			var theTime = Number(value);   
			var theTime1 = 0;   
			var theTime2 = 0;   
			//alert(theTime);   
			if(theTime > 60) {   
			    theTime1 = Number(theTime/60);   
			    theTime = Number(theTime%60);   
			   //alert(theTime1+"-"+theTime);   
				if(theTime1 > 60) {   
				   theTime2 = Number(theTime1/60);   
				   theTime1 = Number(theTime%60);   
				}   
			}   
			var result = ""+(theTime>9?theTime:"0"+theTime)+"：";   
			if(theTime1 > 0) {   
			    result = ""+(parseInt(theTime1)>9?parseInt(theTime1):"0"+parseInt(theTime1))+"："+result;   
			}else{
				result = "00："+result;   
			}   
			if(theTime2 > 0) {   
			    result = ""+(parseInt(theTime2)>9?parseInt(theTime2):"0"+parseInt(theTime2))+"："+result;   
			}else{
				result = "00："+result;
			}
			if(result!=null&&result!=""){
				result = result.substring(0,result.length-1);
			}
			return result;   
		} 
</script>
<style type="text/css">
.STYLE1 {font-size: 12px}
.style3 {text-decoration: underline;}
.form { 
	border-bottom: 1px solid #000000 ;
	border-right: 1px solid #000000;
	font-size: 14px;
	height: 25px;
}
.form1 { 
	border-bottom: 1px solid #000000 ;
	border-right: 1px solid #000000;
	height: 30px;
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
   <input type="hidden" name="clientId" value="${cilentId }">
  <table width="100%" align="center" cellpadding="0" cellspacing="0">
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
      <td><table width="100%" align="center" cellpadding="0" cellspacing="0" style="border: 0px solid #000000;">
      
          <tr>
            <td width="30">&nbsp;</td>
            <td><TABLE  width="100%" align="center" border=0>
                <tr>
                    <td colspan="3" align="center"><B>用户电话费用列表</B>&nbsp;</td>
                 </tr>
          	</table></td>
          	<td width="30">&nbsp;</td>
          </tr>
          <tr>
		 	 <td width="30">&nbsp;</td>
			 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
		  		  	<tr height="40">
						<td align="">打印时间：<%=GlobalMethod.getTime3() %></td>
				 	</tr>
	         	</table></td>
         	 <td width="30">&nbsp;</td>
          </tr>
          <tr>
          	 <td width="30">&nbsp;</td>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0" class="tab">
					<tr>
						<th width="6%" nowrap="nowrap" class="form1" align="center">序号</th>
						<th width="8%" nowrap="nowrap" class="form1" align="center">电话号码</th>
						<th width="8%" nowrap="nowrap" class="form1" align="center">所在房间号</th>
						<th width="8%" nowrap="nowrap" class="form1" align="center">住户名称</th>
						<th width="8%" nowrap="nowrap" class="form1" align="center">通话总时长（hh:mm:ss）</th>
						<th width="8%" nowrap="nowrap" class="form1" align="center">话费总金额(￥)</th>
					</tr>
					<c:forEach items="${phoneCostForm.phoneCostList}" varStatus="tag" var="v">
						<tr>
							<td  nowrap="nowrap" class="form" align="center">${tag.count }&nbsp;</td>
							<td  nowrap="nowrap" class="form" align="center">${v.phoneNumber }&nbsp;</td>
							<td  nowrap="nowrap" class="form" align="center">${v.roomFullName }&nbsp;</td>
							<td  nowrap="nowrap" class="form" align="center">${v.clientName }&nbsp;</td>
							<td  nowrap="nowrap" class="form" align="center"><script>document.write(formatSeconds(${v.callTime}))</script>&nbsp;</td>
							<td  nowrap="nowrap" class="form" align="center">${v.cost }&nbsp;</td>
						</tr>
					</c:forEach>
				</table>
			</td>
			 <td width="30">&nbsp;</td>
		</tr>
		<tr>
		 	 <td width="30">&nbsp;</td>
			 	<td><table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
		  		  	<tr height="40">
						<td align="">总金额：<script>document.write(parseFloat("${phoneCostForm.totalCost }").toFixed(2).toString());</script></td>
				 	</tr>
	         	</table></td>
         	 <td width="30">&nbsp;</td>
        </tr>
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

