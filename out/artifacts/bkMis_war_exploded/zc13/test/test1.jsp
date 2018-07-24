<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test1.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <input type="text" id="date" />
        <input type="button" value="加" id="Add" />
        <input type="button" value="减" id="Minus" />

        <script type="text/javascript">

            $ = function(o) { return typeof o === 'string' ? document.getElementById(o) : o };

            $('Add').onclick = function() {
                var date = $('date').value;
     var result = submitNextDay(date);
     $('date').value = result;
            }
            $('Minus').onclick = function() {
              var date = $('date').value;
     var result = submitTopDay(date);
     $('date').value = result;
            }        

function submitTopDay(s) {//上一天          
d   =   new   Date(Date.parse(s.replace(/-/g,   '/')));       
d.setDate(d.getDate()-1);
var month = (d.getMonth()+1)+'';
if(month.length == 1){
month = '0'+month;
} 
var date = d.getDate()+'';
if(date.length == 1){
date = '0'+date;
}
t   =   [d.getYear(),   month,   date];       
return t.join('-');     
    }    
   
   


        </script>
 

  
  <body>
    This is my JSP page. <br>
  </body>
</html>
