<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>上传文件</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/jquery.form.js"></script>
	<script type="text/javascript">
	      $("#up").live("click",function(){
	      		var filePath = document.getElementById("myfile").value;
	      		if(filePath == "" || filePath == null){
	      			alert("请选择要上传的文件!");
	      		}
	      		var index = filePath.lastIndexOf(".");
	      		var type = filePath.substring(index+1);
	      		//截取上传文件的名字
	      		var fileIndex = filePath.lastIndexOf("\\");
	      		var subFileName = filePath.substring(fileIndex+1); 
	      		type = type.toLowerCase();
	      		//if(type != 'doc' && type != 'xls' && type != 'ppt' && type != 'txt'){
	      		//	alert("文件格式不正确!");
	      		//}else{
	      		  $.post("personnel.do?",{method:"checkFileName",fileName:subFileName},function(data){
	      		  	if(data == 1){
	      		  		if(!window.confirm("该文件已存在,您是否要覆盖该文件?")) {return window.close();}
	      		  		$("#uploadForm").ajaxSubmit({
			   			 success: function(data){
			    		 //alert(data);
						 window.returnValue = data;
						 window.close();
						}
					  });
	      		  	}else{
	      		  		$("#uploadForm").ajaxSubmit({
			   			 success: function(data){
			    		 //alert(data);
						 window.returnValue = data;
						 window.close();
						}
					  });
	      		  	}
	      		  })
					
				//window.opener=null;
             	//window.open("","_self");
             	//}
			});
	</script>
  </head>
  <body>
    <form id="uploadForm" action="personnel.do?method=upLoadFile" name="uploadForm" method="post" enctype="multipart/form-data">
    	<table>
    		<tr>
    			<td>  
    				上传文件  
    			</td>
    		</tr>
    		<tr>
    			<td>
    				<input type="file" name="myfile" id="myfile"/>
    			</td>
    		</tr>
    		<tr>
    			<td>
    				<input id="up" type="button" name="up" value="上传"/>
    				<input type="reset" name="reset" value="重设"/>
    			</td>
    		</tr>
    	</table>
    </form>
  </body>
</html>
