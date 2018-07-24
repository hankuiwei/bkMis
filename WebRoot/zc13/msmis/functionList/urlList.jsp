<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.util.*"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%@ page import="com.zc13.msmis.mapping.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/zc13/msmis/functionList/global/css/dtree.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/zc13/msmis/functionList/global/js/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zc13/msmis/functionList/global/js/Non_js.js"></script>

<title>菜单url树状列表</title>
</head>
<body onselectstart="return false" id="example1">
<div class="material" id="example1" style="margin:0px;">
	<div class="material_1_body_Urllist" >
        
        <div class="material_main">
			
            <div class="dtree_button">
            	<a onclick="add();"><div class="buttonBg"><div style="margin-top:5px;">添加</div></div></a>
            	<a onclick="mod();"><div class="buttonBg"><div style="margin-top:5px;">修改</div></div></a>
            	<a onclick="del();"><div class="buttonBg"><div style="margin-top:5px;">删除</div></div></a>
            </div>
            <div style="height:20px"></div>
			<table width="100%" border="0" >
			  <tr>
			  	<td>
				<div class="dtree">
				<script>
				
					//定义全局变量：被选中的selectedId
					var selectedId = null;
					
					
					//创建tree
					urlTree = new dTree('urlTree');
					
					urlTree.add('0','-1','','');
					
					
							<c:forEach var="function" items="${functionList }">
							
								urlTree.add('${function.functionid }','${function.parentid }','${function.functionname }',"javascript:operate('${function.functionid }')");
							</c:forEach>
						
					//更新cookie中selectedId的值
					urlTree.setCookie('csurlTree', null);
			
					//节点展开位置
					urlTree.newroot = new Node(0);
					//tree输出
					document.write(urlTree);
					
					
					
					<%
					if(null == request.getParameter("selectedId")){
						%>
						//取cookie中获取缓存的selectedId
						selectedId = urlTree.getSelected();
						<%
					}else{
						%>
						//更新cookie中selectedId的值
						urlTree.setCookie('csurlTree', null);
						<%
					}
					%>
					
					
				function operate(arg){
						selectedId = arg;
					}
					
					function add(){
						if(isSelected()){
							window.location.href="<%=request.getContextPath()%>/function.do?method=addUrlBefore&parentId="+selectedId;
						}
					}
					
					function mod(){
						if(isSelected()){
							window.location.href="<%=request.getContextPath()%>/function.do?method=updateBefore&functionId="+selectedId;
						}	
					}
					
					function del(){
						if(isSelected()){
							if(confirm("该操作执行时，将会删除所有的子菜单，确定要删除吗？")){
								window.location.href="<%=request.getContextPath()%>/function.do?method=delFunction&functionId="+selectedId;
							}
						}
					}
					
					function moveUrl(){
						window.location.href="moveUrl.jsp";
					}
					
					function isSelected(){
						if(selectedId == null || selectedId == ""){
							alert('请首先选中您要操作的节点！');
							return false;
						}else{
							return true;
						}
					}
				</script>
				</div>
				</td>
			  </tr>
			</table>
			</div>
		</div>
	</div>
</body>
</html>


  <script>
		setGradient('example1','#43A5FE','#ffffff',0);
  </script>