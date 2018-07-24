<%@ page language="java"  pageEncoding="utf-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%@ include file="../../../common/noCache.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Integer count = (Integer)request.getSession().getAttribute("count");
	if(count==null){
		count = 0;
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>index</title>
<link href="../../../resources/css/bottom.css" rel="stylesheet" type="text/css" />
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
<META content="MSHTML 6.00.2900.5764" name=GENERATOR>
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
</script>
<script>
	function conrel(){
		if(parent.conFrame.rows == "34,14,*,26"){
			parent.conFrame.rows = "0,14,*,26"
			document.all.imgId.src='../../../resources/images/index_13.jpg';
		}else{
			parent.conFrame.rows = "34,14,*,26"
			document.all.imgId.src='../../../resources/images/index_13.jpg';
		}
	}
	function setRefrash()//返回首页
	{
	    window.top.location.reload();
	}
	
	//每隔一分钟刷新一次首页
	function refresh(){
		//alert(window.parent.document.frames["centerFrame"].document.frames["fraMain"].document.frames["butFrame"].document.getElementById("show_bkMis_index")==null);
		if(window.parent.document.frames["centerFrame"].document.frames["fraMain"].document.frames["butFrame"].document.getElementById("show_bkMis_index")!=null){
			window.parent.location.href='<%=path %>/adminLogin.do?method=login';
		}
	}
	setInterval(refresh,60000);
	
	function returnIndex(){
		//window.location.target="_parent";
		<%request.getSession().setAttribute("count",1);%>
		window.parent.location.href="<%=path %>/adminLogin.do?method=login";
	}
	<%if(count!=0){
		request.getSession().setAttribute("count",0);
	%>
	window.parent.location.href="<%=path %>/adminLogin.do?method=login";
	<%}%>
</script>
</head>
<body bgcolor="#FFFFFF" onLoad="MM_preloadImages('<%=path %>/resources/images/index_04-2.jpg','<%=path %>/resources/images/index_05-2.jpg','<%=path %>/resources/images/index_06-2.jpg','<%=path %>/resources/images/index_07-2.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr style="background-image:url(<%=path %>/resources/images/index_02.jpg)">
        <td width="430" height="34" valign="top"><img src="<%=path %>/resources/images/index_01_t1.jpg" width="430" height="34" alt=""></td>
        <td>&nbsp;</td>
        <td width="250"><table width="229" border="0" align="right" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right"><!-- 系统首页 -->
            <a  href="javascript:returnIndex();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image111','','<%=path %>/resources/images/index_tc_02.gif',1)"><img src="<%=path %>/resources/images/index_tc.gif" name="Image111" width="80" height="34" border="0"></a>&nbsp;<!-- 系统首页结束 -->
            <a href="<%=path %>/adminLogout.do?method=logout"  target="_parent" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image112','','<%=path %>/resources/images/index_tc_02.jpg',1)"><img src="<%=path %>/resources/images/index_tc.jpg" name="Image112" width="80" height="34" border="0"></a></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
 
</table>
<!-- End Save for Web Slices -->
</body>
</html>