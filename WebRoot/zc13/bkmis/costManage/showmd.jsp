<%@ page language="java" import="java.util.*, java.text.*" pageEncoding="utf-8"%>
<%@page import="org.apache.log4j.Logger" %>
<%@ page import="com.zc13.util.*;"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<% String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
   String srcpath = request.getParameter("srcpath");
   srcpath = srcpath.replaceAll("@","&");
   String title = java.net.URLDecoder.decode(GlobalMethod.NullToParam(request.getParameter("mk"),""),"utf-8");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%try{ %>
<html>
  <head>
    <title><%=title %></title>    
  </head>
  
  <body>
	<IFRAME name="contentFrame" id="contentFrame" width="600" height="100%" scrolling="auto"
	 frameborder="0" src="<%=basePath%><%=srcpath %>"></IFRAME>
	 
  </body>
</html>
<%
}catch(Exception e){ e.printStackTrace(); }
%>
