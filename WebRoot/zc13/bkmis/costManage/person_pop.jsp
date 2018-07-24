<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" type="text/css" href="resources/css/dtree.css" />
<script type="text/javascript" src="resources/js/dtree.js"></script>
<script type="text/javascript" src="resources/js/Non_js.js"></script>
<script type="text/javascript" src="resources/js/util/util.js"></script>
<link href="resources/css/css.css" rel="stylesheet" type="text/css" />

<title>菜单url树状列表</title>
</head>
<body >
	<table width="100%">
		<tr>
			<td>
			<div style="height:100%;width:98%;border-right: 1px solid #ccc;border-bottom: 1px solid #ccc;">
			<script>
				//创建tree
				urlTree = new dTree('urlTree');
				var frameId = "mainFrame";
				<c:forEach var="tree" items="${treeList }">
					urlTree.add('${tree.id }','${tree.parentID }','${tree.url}'+'${tree.name }','','${tree.name }',frameId);
				</c:forEach>
				//更新cookie中selectedId的值,下面这就话起作用的情况下，一旦展开了树，只要不关闭浏览器或者重新登录，那么树的结构会继续保持
				urlTree.setCookie('csurlTree', null);
				//节点展开位置
				urlTree.newroot = new Node(0);
				//tree输出
				document.write(urlTree);
			</script>
			</div>
			</td>
		</tr>
		<tr>
			<td align="center"><input type="button" class="button" value="确定" onclick="qd();"><input type="button" class="button" value="关闭" onclick="window.close()"></td>
		</tr>
	</table>
</body>
</html>
<script type="text/javascript">
<!--
function qd(){
	var result = new Object();
	var client = document.getElementsByName("client");
	var id = "";
	var name = "";
	for(var i=0;i<client.length;i++){
		if(client[i].checked){
			var temp = client[i].value.split(",");
			id += ","+temp[0];					
			name += ","+temp[1];
		}
	}
	if(id != "" && id.charAt(0) == ','){
		id = id.substring(1,id.length);
	}
	if(name != "" && name.charAt(0) == ','){
		name = name.substring(1,name.length);
	}
	result.id = id;
	result.name = name;
	window.returnValue=result;
	window.close();
}
//-->
</script>
<script type="text/javascript">
<!--
function setChecked(This,id){
	var temp =document.getElementsByName("client");
	for(var i=0;i<temp.length; i++){
		var arrays = temp[i].value.split(',');
		if(arrays[2] == id){
			temp[i].checked = This.checked;
		}
	}
}
//-->
</script>
