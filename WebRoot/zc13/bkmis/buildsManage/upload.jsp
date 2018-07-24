<%@ page language="java" pageEncoding="UTF-8"%>
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
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/jquery.form.js"></script>
	<script type="text/javascript">
	     $("#up").live("click",function(){
	      		var filePath = document.getElementById("file").value;
	      		var index = filePath.lastIndexOf(".");
	      		var type = filePath.substring(index+1);
	      		type = type.toLowerCase();
	      		if(filePath == "" || filePath == null){
	      			alert("请选择上传文件");
	      			return false;
	      		}else{
	      		  if(type != 'jpg' && type != 'jpeg' && type != 'gif' && type != 'bmp'){
	      			 alert("格式不正确");
	      		 }else{
					$("#uploadForm").ajaxSubmit({
			    		 success: function(data){
						     window.returnValue = data;
						     window.close();
						}
					});
				  }
	      		}
			});
	</script>

  </head>

	<body>
		<form method="post" id="uploadForm" name="uploadForm" enctype="multipart/form-data" action="<%=path%>/build.do?method=upload">
			<table>
				<tr>
					<td>上传图片</td>
				</tr>
				<tr>
					<td><input type="file" id="file" name="file" ></td>
				</tr>
				<tr>
					<td><input type="button" value="提交" id="up" name="up">
					    <input type="reset" value="取消"></td>
				</tr>
			</table>
		</form>
	</body>
</html>
