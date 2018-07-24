<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
    <title>My JSP '2.jsp' starting page</title>
    <link href="../resources/css/RptTable.css" rel="stylesheet" type="text/css">
        <link href="../resources/css/css.css" rel="stylesheet" type="text/css">
     <script type="text/javascript" src="../resources/js/RptTable.js" charset="UTF-8"></script>
     <script type="text/javascript" src="../resources/js/main.js" charset="UTF-8"></script>

  </head>
  
  <body >
 
   <div  style="width:100%; height:10%;" >
   
   <input type="text" />
   	 <div class="button" onmouseover="this.className='button_over'" onmouseout="this.className='button_out'"
             onmousedown="this.className='button_down'" onclick = ""><div style="margin-top:4px;">确定</div></div>
   	
   	 <div class="button" onmouseover="this.className='button_over'" onmouseout="this.className='button_out'"
             onmousedown="this.className='button_down'" onclick = ""><div style="margin-top:4px;">取消</div></div>

   </div>
 
	<div  style="width:100%; height:90% ; overflow:auto" >
	
	
	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="List_table">
		<tr>
		<th width = "100"  class="RptTableHeadCellFullLock">表头</th>
		
			<%for(int a = 0 ;a<35;a++){
			%>
			
			<th class = "RptTableHeadCellLock">表头<%=a+1 %></th>
		<%	} %>

	<%for(int k = 0 ;k<43;k++){
			%>
		<tr onmouseover="if(!isIE6)this.className = 'hover';" onmouseout="if(!isIE6)this.className = '';">
		<td width = "100%"  class="RptTableBodyCellLock"><%=k+1 %></td>
		
			<%for(int i = 0 ;i<35;i++){
			%>
			
			<td class="RptTableCellClip">了</td>
		<%	} %>
			
		</tr>
			<%	} %>
	
	</table>
	</div>
  </body>
</html>
