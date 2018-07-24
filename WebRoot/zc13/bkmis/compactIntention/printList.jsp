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
<title>意向书客户列表</title>
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
                    <td colspan="3" align="center"><B>北控宏创意向书列表</B>&nbsp;</td>
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
						<th width="8%" nowrap="nowrap" class="form1" align="center">客户名称</th>
						<th width="8%" nowrap="nowrap" class="form1" align="center">意向书编号</th>
						<th width="8%" nowrap="nowrap" class="form1" align="center">联系电话</th>
						<th width="8%" nowrap="nowrap" class="form1" align="center">房间号</th>
						<th width="8%" nowrap="nowrap" class="form1" align="center">面积</th>
						<th width="8%" nowrap="nowrap" class="form1" align="center">客户类型</th>
						<th width="8%" nowrap="nowrap" class="form1" align="center">定金金额</th>
						<th width="8%" nowrap="nowrap" class="form1" align="center">是否已交定金</th>
						<th width="8%" nowrap="nowrap" class="form1" align="center">是否已转为合同</th>
						<th width="8%" nowrap="nowrap" class="form1" align="center">审批状态</th>
					</tr>
					<c:forEach items="${intentionForm.intentionList}" varStatus="tag" var="v">
						<tr>
							<td  nowrap="nowrap" class="form" align="center">${tag.count }&nbsp;</td>
							<td  nowrap="nowrap" class="form" align="center">${v.name }&nbsp;</td>
							<td  nowrap="nowrap" class="form" align="center">${v.intentionCode }&nbsp;</td>
							<td  nowrap="nowrap" class="form" align="center">${v.phone }&nbsp;</td>
							<td  nowrap="nowrap" class="form" align="center">${v.roomCodes }&nbsp;</td>
							<td  nowrap="nowrap" class="form" align="center">${v.totalArea }&nbsp;</td>
							<td  nowrap="nowrap" class="form" align="center">${v.clientType }&nbsp;</td>
							<td  nowrap="nowrap" class="form" align="center">${v.earnest }&nbsp;</td>
							<td  nowrap="nowrap" class="form" align="center">
							<c:choose>
								<c:when test="${v.isEarnest eq 1}">已缴</c:when>
								<c:otherwise >未缴</c:otherwise>
							</c:choose>
							</td>
							<td  nowrap="nowrap" class="form" align="center">
							<c:choose>
								<c:when test="${v.isConvertCompact eq 1}">是</c:when>
								<c:otherwise >否</c:otherwise>
							</c:choose>
							</td>
							<td  nowrap="nowrap" class="form" align="center">${v.status }&nbsp;</td>
						</tr>
					</c:forEach>
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

