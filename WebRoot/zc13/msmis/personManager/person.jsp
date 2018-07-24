<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>修改密码</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=request.getContextPath()%>/resources/css/menu.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	    <script type="text/javascript" src="<%=path%>/resources/js/validate.js" defer="defer"></script>
	</head>
	<script type="text/javascript">


</script>
	<script type="text/javascript">	   
	 //提交是判断所有输入框符合要求	 
	    /*function tijiao(){	      
	       with(document.updateUser){*/
	          /*if(realName.value==""||trim(realName.value).length==0){
	              alert("真实姓名不能为空！");
	              document.updateUser.realName.focus();
	              return false;
	          } 
	          var p="^([\u4e00-\u9fa5]+|[A-Za-z]+)$";		
			  if(!(realName.value.match(p)!=null)) {
		        	alert("真实姓名只能是纯英文或纯汉字");
		        	realName.value="";
		        	realName.focus();
		        	return false;
		        }
			
	       
			      if(identityCard.value == ""||identityCard.value == null){
			      	alert("身份证号码不能为空");
			      	return false;
			      }else{
	              	 var wordText = $("#identityCard").val();
		             var p1="^[0-9]{6}(19|20)[0-9]{2}(((0[13578]|10|12)(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[12][0-9])))[0-9]{3}[0-9X]$";
		             var p2="^[0-9]{8}(((0[13578]|10|12)(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[12][0-9])))[0-9]{3}$";
		             if(!(wordText.match(p1)!=null||wordText.match(p2)!=null)){
		                alert("身份证号码输入不正确");
		                document.updateUser.identityCard.value="";
		                document.updateUser.identityCard.focus();
		                return false;
		             }	     
			      }
				 if(phone.value == ""||phone.value == null){
			      	alert("手机号码不能为空");
			      	return false;
			     }else if(!checkmobile(phone.value)){
			                alert("手机号码不正确！");
			                phone.value="";
			                phone.focus();
			                return false;
		         }*/
		       /*  if(woldpassword.value == ""){
		              alert("请输入原始密码！");
		              woldpassword.focus();
		              return;
		         }else{
		            var url = "<%=path%>/personManager.do";
		            var realName = document.getElementById("realName").value;
		            $.post(url,{method:"getPasswordOfMd5",woldpassword:woldpassword,realName:realName},function(data){
		            alert(data);
		                  if(oldpassword.value != woldpassword.value){
		                       alert("您输入的密码不正确，请重新输入！");
		                       woldpassword.focus();
		                       return;
		                  }
		            });
		         }
		      
		        if(password.value != ""){
		            alert("请输入修改密码!");
		            password.focus();
		            return;
         		} 
         		if(password1.value != ""){
		            alert("确认密码不能为空！");
		            password.focus();
		            return;
         		}     	     	 
        	 	if(password.value != password1.value){
		            alert("密码输入不一致");
		            password.focus();
		            return;
         		}     	 
	       }
	       var i=confirm("确认修改吗？");
	       if(i){
	          document.updateUser.submit();
	       }
	    }	*/
	</script>
	 <script language="javascript">
    var xmlHttpRequest;   
  	
  	//创建一个XMLHttpRequest对象
	function createXMLHttpRequest(){   
	    if(window.XMLHttpRequest)   
	    {   
	        xmlHttpRequest = new XMLHttpRequest();   
	    }if(window.ActiveXObject)   
	    {   
	      try   
	      {    
	        xmlHttpRequest=new ActiveXObject("Msxml2.XMLHTTP");    
	      }catch(e)   
	      {    
	        try   
	        {    
	            xmlHttpRequest=new ActiveXObject("Microsoft.XMLHTTP");    
	        }catch(e){}    
	      }    
	    }      
	} 
	
	//回调函数
	function callback(){     
	    if(xmlHttpRequest.readyState==4){//对象状态      
	        if(xmlHttpRequest.status==200){//信息已成功返回，开始处理信息    
	            var message = xmlHttpRequest.responseText;
	            if((message.valueOf())==0){
	                  
	            }else{
	                var oldpassword = document.getElementById("oldpassword").value;
	                var password = document.getElementById("password").value;
	                var password1 = document.getElementById("password1").value;
	                if(oldpassword != message){
		                       alert("您输入的密码不正确，请重新输入！");
		                       woldpassword.focus();
		                       return;   
	                 }else if(password == ""){
		                 alert("请输入修改密码!");
		                 password.focus();
		                 return;
	                 }else if(password1 == ""){
		                alert("确认密码不能为空！");
		                password.focus();
		                return;
	                 }else if(password != password1){
		                alert("密码输入不一致");
		                password.focus();
		                return;
	                 }else{
	                    var i=confirm("确认修改吗？");
	                    if(i){
	                       document.updateUser.submit();
	                      }
	                }
	            }
	        }   
	    }   
	}
	//删除房间
	function tijiao(){
			createXMLHttpRequest();
			var woldpassword = document.getElementById("woldpassword").value;
			var userName = document.getElementById("userName").value;
			if(woldpassword == ""){
		              alert("请输入原始密码！");
		              woldpassword.focus();
		              return;
		         }else{
			         var url = "<%=path%>/personManager.do?method=getPasswordOfMd5&timeStamp="+new Date().getTime();
			         xmlHttpRequest.open("POST",url,true);
			         xmlHttpRequest.onreadystatechange = callback;
			         xmlHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
			         var params = "&temp=" + userName + ";" + woldpassword;
			         xmlHttpRequest.send(params);
			     }
            
	 
	}
    </script>
	<body>
		<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr><td height="18"></td></tr>
			<tr><td height="5"></td></tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
							<td width="165" nowrap="nowrap" class="form_line">修改密码</td>
							<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
							<td width="1080" class="form_line2"></td>
							<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="menu_tab2">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="8">
								<form name="updateUser" action="<%=path%>/personManager.do?method=updatePerson" method="post">
									<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form_tab1">
										<tr height="80%"><td height="2" nowrap="nowrap" colspan="2"></td></tr>
										<tr>
											<td height="2" align="right" nowrap="nowrap"><input name="userid" type="hidden" value="${person.userid }"></td>
											<td width="11%" nowrap="nowrap"><input maxlength="30" type="hidden" name="realName" id="realName" value="${person.realName }" readonly>
											                                <input type="hidden" name="userName" id="userName" value="${person.username }">
											</td>
										</tr>
										<tr>
											<td height="2" align="right" nowrap="nowrap">原始密码：&nbsp;</td>
											<td nowrap="nowrap"><input type="password" name="woldpassword" id="woldpassword">
												                <input type="hidden" name="oldpassword" id="oldpassword" value="${person.password }">
											</td>
										</tr>
										<tr>
											<td height="2" align="right" nowrap="nowrap">新密码：&nbsp;</td>
											<td nowrap="nowrap"><input type="password" name="password" id="password"></td>
										</tr>
										<tr>
											<td height="2" align="right" nowrap="nowrap">新确认密码：&nbsp;</td>
											<td nowrap="nowrap"><input type="password" name="password1" id="password1"></td>
										</tr>
										<tr align="center">
											<td nowrap="nowrap" align="right" colspan="3"><input type="button" value="修改" onclick="tijiao();" class="button">
											&nbsp;&nbsp;&nbsp;&nbsp;
												                                           <input type="button" value="取消" onclick="javascript:document.updateUser.reset();" class="button">
											</td>
										</tr>
										<tr>
											<td height="2" nowrap="nowrap" colspan="2">
												&nbsp;
											</td>
											<td nowrap="nowrap">
												&nbsp;
											</td>
											<td width="5%" nowrap="nowrap">
												&nbsp;
											</td>
											<td width="11%" nowrap="nowrap">
												&nbsp;
											</td>
											<td width="38%" nowrap="nowrap" class="form4">
												&nbsp;
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
						<tr>
							<td colspan="8">
								&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<p>
			&nbsp;
		</p>
	</body>
	<c:if test="${flag}">
	 <script type="text/javascript">
	 	alert("修改成功");
	 </script>
	 </c:if>
</html>
