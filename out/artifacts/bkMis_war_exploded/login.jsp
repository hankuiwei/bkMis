<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.GlobalMethod"%>
<%
	String flag = request.getParameter("flag");
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<title>北控宏创昌平产业园物业管理软件</title>
	<link href="resources/css/menu.css"" rel="stylesheet" type="text/css">
	
	<script type="text/javascript">
	<!--
	function MM_swapImgRestore() { //v3.0
	  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
	}
	function MM_preloadImages() { //v3.0
	  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
	}
	
	function MM_findObj(n, d) { //v4.01
	  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
	    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
	  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
	  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
	  if(!x && d.getElementById) x=d.getElementById(n); return x;
	}
	
	function MM_swapImage() { //v3.0
	  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
	   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
	}
	//-->
<!--	
	 	  	function init(){
				document.getElementById("username").focus();
			}
			document.onkeydown = function(evt){
				var evt = window.event?window.event:evt;
				if(evt.keyCode==13)
				{
					validateForm();
				}
			}
			
	 	  	function validateForm(){
	 	  		form = document.all.loginForm;
	 	  		if(form.username.value==""){
	 	  			alert("请输入用户名！");
	 	  			document.getElementById("username").focus();
	 	  			return;
	 	  		}
				if(form.username.value.indexOf(" ") != -1){
					alert("用户名不正确,请重新输入！注意不要使用空格");
					form.username.focus();
					form.username.select();
					return;
				}
	 	  		if(form.password.value==""){
	 	  			alert("请输入密码！");
	 	  			document.getElementById("password").focus();
	 	  			return;
	 	  		}
				form.action="adminLogin.do?method=login";
				//form.target="_self";
				form.submit();
			}
//-->
</script>
	<script language="JavaScript">
		javascript:window.history.forward(1);
		function resett(){
			document.loginForm.reset();
		}
		function resett(){
			document.getElementById("username").value="";
			document.getElementById("password").value="";
		}
	</script>
	<%if("outTime".equals(flag)){ %>
	<script language="JavaScript">
		alert("登录已超时，请重新登录！");
	</script>
	<%} %>
</head>
  <%
   String name="";
   Cookie[] CookieSet =request.getCookies();
   Cookie cookie=null;

		if(CookieSet!=null){
			for(int i=0;i<CookieSet.length;i++){
				if("bkUserName".equals(CookieSet[i].getName()) ){
					 cookie = CookieSet[i];
					 name=GlobalMethod.NullToSpace(cookie.getValue());
					 if(!"null".equals(name)){
					 	 request.setAttribute("username",name);
					 }
					
					 cookie.setMaxAge(60600);
					 continue;
				}
			}
		}
   %>
 <body class="login_bg" onLoad="init();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="20%"></td>
			<td width="80%">
				<table width="652" border="0" cellpadding="0" cellspacing="0">
		<tr height="160">
			<td></td>
		</tr>
		<tr>
			<td class="login_qg">
				<form  name="loginForm" method="post" action="">
				<table width="652" height="258" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="401" height="89">&nbsp;</td>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td rowspan="4">&nbsp;</td>
						<td width="206" height="30" align="center" class="login_caption_wz">用户登录</td>
					    <td height="25" align="center" class="login_caption_wz">&nbsp;</td>
					</tr>
					<tr>
					  <td height="60">
					  	<table width="206" height="60" border="0" cellpadding="0" cellspacing="0">
							<tr height="30">
								<td width="50" align="center" class="login_Text_wz">账户：</td>
								<td>
								    <input type="text" id="username" name="username"
										value="${username}" title="请输入用户名"> 
								</td>
							</tr>
							<tr height="30">
								<td align="center" class="login_Text_wz">密码：</td>
								<td><input type="password" id="password" name="password"
										value="${password}" title="请输入密码"></td>
							</tr>
						</table>
						
					  </td>
				      <td>&nbsp;</td>
				  </tr>
					<tr>
					  <td align="center">
					  	<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<a href="javascript:validateForm();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image01','','resources/images/button_Login_h.gif',1)"><img src="resources/images/button_Login.gif" name="Image01" width="52" height="22" border="0"></a>
								</td>
								<td width="6"></td>
								<td>
									<a href="javascript:resett();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image02','','resources/images/button_Reset_h.gif',1)"><img src="resources/images/button_Reset.gif" name="Image02" width="52" height="22" border="0"></a>
								</td>
							</tr>
						</table>
					  </td>
				      <td>&nbsp;</td>
				  </tr>
				  
					<tr>
					  <td colspan="2">&nbsp;</td>
				  </tr>
			  </table>
			 </form>
			</td>
		</tr>
	</table>
			</td>
		</tr>
	</table>

</body>
</html>