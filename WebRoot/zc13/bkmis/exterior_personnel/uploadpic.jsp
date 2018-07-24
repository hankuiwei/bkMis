<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>上传图片</title>
    
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
	      		var filepath = document.getElementById("myfile").value;
	      		if(filepath == null || filepath == ""){
	      			alert("请选择要上传的图片!");
	      		}
	      		var index = filepath.lastIndexOf(".");
	      		var type = filepath.substring(index+1);
	      		//截取图片名
	      		var nameIndex = filepath.lastIndexOf("\\");
	      		var picName = filepath.substring(nameIndex+1);
	      		type = type.toLowerCase();
	      		if(filepath != "" && filepath != null){
	      			if(type != 'jpg' && type != 'jpeg' && type != 'gif' && type != 'bmp'){
	      				alert("格式不正确!");
	      			}else{
	      			 $.post("exterior.do?",{method:"checkPicName",picName:picName},function(data){
	      			 	if(data == 1){
	      			 		if(!window.confirm("该图片已存在,您是否要覆盖?")) {return window.close();}
			      			   if(window.navigator.userAgent.indexOf("MSIE 8.0")>=1){
				      		       var fileImgpath = document.getElementById("myfile");
		 						   fileImgpath.select();
		  					       var fileinfo = document.selection.createRange().text;
				      		       $("#uploadForm").ajaxSubmit({
						    		 success: function(data){
						    		 //alert(data);
									 var union = data + "," + fileinfo;
									 window.returnValue = union;
									 window.close();
									}
								});
			      			}else{
			      				$("#uploadForm").ajaxSubmit({
					    		 success: function(data){
								 window.returnValue = data;
								 window.close();
							     }
						        });
			      			}
			      			 }else{
			      				   if(window.navigator.userAgent.indexOf("MSIE 8.0")>=1){
				      		       var fileImgpath = document.getElementById("myfile");
		 						   fileImgpath.select();
		  					       var fileinfo = document.selection.createRange().text;
				      		       $("#uploadForm").ajaxSubmit({
						    		 success: function(data){
						    		 //alert(data);
									 var union = data + "," + fileinfo;
									 window.returnValue = union;
									 window.close();
									 }
								    });
					      		   }else{
					      				$("#uploadForm").ajaxSubmit({
							    		 success: function(data){
										 window.returnValue = data;
										 window.close();
									     }
								        });
					      			}
	      				     }
	      			  })
	      		  }
	      	  }
			});
	</script>
  </head>
  
  <body>
    <form id="uploadForm" action="exterior.do?method=uploadPic" name="uploadForm" method="post" enctype="multipart/form-data">
    	<table>
    		<tr>
    			<td>  
    				上传图片  
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
