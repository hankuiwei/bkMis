<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<title>index</title>
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
<SCRIPT language=javascript>
function switchSysBar(){
    var img=document.all.img1;
if (img.src.lastIndexOf("so_left.jpg")>0){
	img.src="../../../resources/images/index_44.jpg";
	document.all("frmTitle").style.display="none" ;
} 
else{ 
	img.src="../../../resources/images/so_left.jpg";
	document.all("frmTitle").style.display="" ;
} 
} 
</SCRIPT>
<link href="../../../resources/css/menu.css" rel="stylesheet" type="text/css">
<link href="../../../resources/css/tree.css" rel="stylesheet" type="text/css" />
</head>
<body bgcolor="#FFFFFF">
<table height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0 bgcolor="#e7f3f9">
<TBODY>
  <tr>
    <td id=frmTitle vAlign=top noWrap valign="middle" width=220><IFRAME  name=I1 src="../../../zc13/msmis/login/left.jsp" frameBorder=0 width=220 scrolling=no height="100%"> 浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</IFRAME></td>
    
    <td width="10"><SPAN class=navPoint 
      id=switchPoint title=关闭/打开左栏><IMG id=img1 onclick=switchSysBar() height="48" 
      src="../../../resources/images/so_left.jpg" width=10 name=img1 style="cursor:hand"> </SPAN></td>
     
    <td valign="top" class="menu_tab"><TABLE style="TABLE-LAYOUT: fixed" height="100%" cellSpacing=0 
      cellPadding=0 width="100%" border=0>
      <TBODY>
        <TR>
          <TD valign="top"><TABLE style="TABLE-LAYOUT: fixed" height="100%" cellSpacing=0 
            cellPadding=0 width="100%" border=0>
              <TR>
                <TD valign="top"><IFRAME id="fraMain" 
                  name=fraMain src="main_Desktop.jsp" style=" vertical-align: top"
                  frameBorder=0 width="100%" scrolling=no 
                  height="100%"> 浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</IFRAME>
          </TABLE></TD>
        </TR>
      </TBODY>
    </TABLE></td>
  </tr>
  </TBODY>
</table>
<!-- End Save for Web Slices -->
</body>
</html>