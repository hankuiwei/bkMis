<%@ page language="java" import="java.text.*,java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String path = request.getContextPath();
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>bottom</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
//-->
</script>
<script type="text/javascript">
		//setInterval("document.getElementById('').innerHTML=new Date()",1000);
		//setInterval("document.getElementById('Layer3').innerHTML='<%=dateFormat.format(new Date())%>';",1000);
		//setInterval("document.getElementById('Layer3').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);
	</script>
<link href="../../../resources/css/bottom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/resources/js/main.js"></script>
</head>
<body bgcolor="#FFFFFF" onload="RunTime(document.getElementById('Layer3'));">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr style="background-image:url(../../../resources/images/index_55.jpg)" >
    <td width="60" nowrap="nowrap"><img src="../../../resources/images/win_xp_d.gif" width="49" height="26" alt=""></td>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="166" class="fooder_txt" nowrap="nowrap"></td>
        <td width="207" >&nbsp;</td>
        <td width="454" class="fooder_txt" align="right">
			
		</td>
        <td width="8" nowrap="nowrap"></td>
        <td width="2"></td>
        <td width="238" class="fooder_txt"  nowrap="nowrap">
       		<!--<c:if test="${userInfo.userRoleRange == 2}">
       			操作员：
       		</c:if>
       		<c:if test="${userInfo.userRoleRange == 1}">
       			管理员：
       		</c:if>
       		<c:if test="${userInfo.userRoleRange == 0}">
       			超级管理员：
       		</c:if>-->
       		欢迎你：
       		${userInfo.name}
 		</td>
        <td width="20px"></td>
          <td width="185" id="Layer3" class="fooder_txt" nowrap="nowrap"><%=dateFormat.format(new Date())%></td>
      </tr>
    </table></td>
    <td width="12"><img src="../../../resources/images/index_61.jpg" width="12" height="26" alt=""></td>
  </tr>
</table>
<!-- End Save for Web Slices -->
</body>
</html>