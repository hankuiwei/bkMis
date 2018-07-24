<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title>My JSP '2.jsp' starting page</title>
	<link href="../resources/css/css.css" rel="stylesheet" type="text/css"/>
    <link href="../resources/css/RptTable.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../resources/js/main.js" charset="UTF-8"></script>

</head>
  
  <body>
   

		<div  style="width:100%; height:90%; margin:10px 30px" >
	
	
	<table border="0" cellpadding="0" cellspacing="0" width="90%" class="List_table_pt">
		<tr>
		<th width="100" class = "RptTableHeadCellLock" >表头</th>
		
			<%for(int a = 0 ;a<15;a++){
			%>
			
			<th class = "RptTableHeadCellLock">第一行<%=a+1 %></th>
		<%	} %>

	<%for(int k = 0 ;k<15;k++){
			%>
		<tr onmouseover="if(!isIE6)this.className = 'hover';" onmouseout="if(!isIE6)this.className = '';" >
		<td width = "100"  class="RptTableBodyCellLock"><%=k+1 %></td>
		
			<%for(int i = 0 ;i<15;i++){
			%>
			
			<td class="RptTableCellClip">了</td>
		<%	} %>
			
		</tr>
			<%	} %>
	
	</table>
	</div>

  </body>
</html>
