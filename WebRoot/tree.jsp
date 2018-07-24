<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" type="text/css" href="resources/css/dtree.css" />
<script type="text/javascript" src="resources/js/dtree.js"></script>
<script type="text/javascript" src="resources/js/Non_js.js"></script>

<title>菜单url树状列表</title>
</head>
<body >
	<div style="height:100%;width:98%;border-right: 1px solid #ccc; position: absolute;">
	<script>
		//创建tree
		urlTree = new dTree('urlTree');
		var frameId = "mainFrame";
		<c:forEach var="tree" items="${treeList }">
			urlTree.add('${tree.id }','${tree.parentID }','${tree.name }','${tree.url }','${tree.name }',frameId);
		</c:forEach>
		//更新cookie中selectedId的值,下面这就话起作用的情况下，一旦展开了树，只要不关闭浏览器或者重新登录，那么树的结构会继续保持
		urlTree.setCookie('csurlTree', null);
		//节点展开位置
		urlTree.newroot = new Node(0);
		//tree输出
		document.write(urlTree);
	</script>
	</div>
</body>
</html>

