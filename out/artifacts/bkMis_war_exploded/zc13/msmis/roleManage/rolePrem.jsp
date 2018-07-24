<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.zc13.msmis.mapping.*" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/zc13/msmis/functionList/global/css/dtree.css" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"
			src="<%=path%>/zc13/msmis/functionList/global/js/dtree.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/RptTable.js" defer="defer"></script>
		<title>设置角色权限</title>
	</head>
	<body onload="getSelected(),changeArrayToString()"
		onselectstart="return false">
		<form method="post" name="rolePermForm" action="">
			<table width="99%" height="96%" border="0" align="center"
				cellpadding="0" cellspacing="0" style="layout: fixed">
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></img></td>
								<td width="165" nowrap="nowrap" class="form_line">设置角色权限</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></img></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></img></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr height="95%">
					<td valign="top">
						<!-- 表单内容区域 -->
						<table width="100%" height="100%" border="0" cellspacing="0"
							cellpadding="0" style="table-layout: fixed">
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td>
												<div class="dtree">
													<script>
				   //定义checkbox的长度
				   var len;
				   //定义初始值
				   var ivalue = new Array();
				   //创建 tree
				   urlTree = new dTree('urlTree');
				   //虚拟根节点
				   urlTree.add('0','-1','','');
				   <%
				     //加载url列表
				     List<MRolePerm> list = null;
				     list = (List<MRolePerm>)request.getAttribute("rolePremlist");
				     String resIds = "[";
				     for(MRolePerm m:list){
				         resIds = resIds + "'" + m.getMFunction().getFunctionid().toString() + "',";
				     }
				     resIds = resIds + "]";
				     String ids = resIds;
				     list.clear();
				    //判断是否是超级管理员,如果是,则加载全部url;不是,则从足管理员的权限中取得url列表
				    List<MFunction> list2 =(List<MFunction>)request.getAttribute("functionlist");
				    //按照序号顺序排序
				    if(null != list2 && list2.size() > 0){
				      for(MFunction m:list2){
				         Integer parentid = m.getParentid();
				         Integer functionid = m.getFunctionid();
				         String functionname = m.getFunctionname(); 
				   %>
				   urlTree.add('<%=functionid%>','<%=parentid%>',"<input type='checkbox' disabled name='ck_<%=functionid%>' id ='ck_<%=functionid%>'/><%=functionname%>","javascript:setChecked('<%=functionid%>')");
				   <%
				        }
				      }  
				   %>
				   len = <%=list2.size()%>;
				   //节点展开位置
				   urlTree.newroot = new Node('0');
				   //tree输出
				   document.write(urlTree);
				   ivalue = <%=ids%>;
			 //显示当前已被选中的节点
				 function getSelected(){
				   var resIds = ivalue;
				   for(var i=0;i<urlTree.aNodes.length;i++){
				       var resId = urlTree.aNodes[i].id;
				       var ck_obj = document.getElementById('ck_'+resId);
				       for(var j=0; j<resIds.length;j++){
				          if(resIds[j] == resId){
				          ck_obj.checked = "checked";
				        }
				    }
				  }
			   }
				  //选中设置
				  function setChecked(obj){
				     var ck_obj = document.getElementById('ck_'+obj);
				     if(ck_obj){
				         ck_obj.checked = !ck_obj.checked;
				     }
				     //获取当前节点
				     var node = urlTree.aNodes[urlTree.selectedNode];
				    
				     //点击时将取消所有其子孙节点的选择
				    setChildDisChecked2(node.id,ck_obj);
				    //点击将设置其所有的父、祖父...被选中
					setParentChecked(node,ck_obj);
					
				  } 
				  //定义基本变量
				  var urlArray = urlTree.aNodes;
				  var length = urlArray.length;
				  var urlStr = null;
				  //递归函数,设置当前节点所有的子节点,孙节点...discheked
				  function setChildDisChecked(node,ck_obj){
				     var id = node.id;
				     for(var i=0;i<length;i++){
				       var nodes = urlArray[i];
				       var pid = nodes.pid;
				       var ck_child = document.getElementById('ck_'+nodes.id);
				       if(id == pid){
				           if(!ck_obj.checked){
				              ck_child.checked = null;
				           }else{
				              ck_child.checked = "checked";
				           }
				           setChildDisChecked(nodes,ck_child);
				       }
				     }
				  }
				  //onload加载时,将节点数组转化成字符串
				  function changeArrayToString(){
				     var strArray = new Array(length);
				     for(var i=0;i<length;i++){
				        var id = urlArray[i].id;
				        var pid = urlArray[i].pid;
				        strArray[i] = pid + ";" + id;
				     }
				     urlStr = strArray.toString();
				  }
				//正则表达式重写向下递归函数
				function setChildDisChecked2(id,ck_obj){
				   if(urlStr != null && urlStr != ''){
				      var reg = new RegExp("," + id + ";" , "g");
				      var show;
				      while((show = reg.exec(urlStr)) != null){
				         var index =show.lastIndex;
				         var sub = urlStr.substring(index,urlStr.length);
				         var lastIndex = sub.indexOf(",");
				         var id_ = sub.substring(0,lastIndex);
				         //"," 末端处理
				         if(-1 == lastIndex){
				            id_ = sub;
				         }
				        var ck_child = document.getElementById('ck_' + id_);
				        if(!ck_obj.checked){
				            ck_child.checked = null;
				        }else{
				           ck_child.checked = "checked";
				        }
				        setChildDisChecked2(id_,ck_child);
				      }
				   }
				}   
			//递归函数,设置当前节点的父,祖父... checked
			function setParentChecked(node,ck_obj){
			    for(var i=0;i<urlTree.aNodes.length;i++){
			       var nodes = urlTree.aNodes[i];
			       if(node.pid == nodes.id && nodes.id != 0){
			           if(ck_obj.checked){
			              var ck_parent = document.getElementById('ck_' + nodes.id);
			              ck_parent.checked = "checked";
			              //递归下去
			              var node_ = nodes;
			              setParentChecked(node_,ck_parent);
			           }
			       }
			    }
			}
			function setSelectedIds(){
			   var resIds = '';
			   for(var i=0;i<urlTree.aNodes.length;i++){
			     var resId = urlTree.aNodes[i].id;
			     var ck_obj = document.getElementById('ck_' + resId);
			     if(ck_obj && ck_obj.checked){
			        resIds = ('' == resIds) ? resId : (resIds + ',' + resId); 
			     }
			   }
			   document.getElementById('resIds').value = resIds;
			   if(resIds == null || resIds == ""){
			      alert("请选择权限");
			      return;
			   }else{
			      document.rolePermForm.action = "<%=path%>/role.do?method=updateRolePerm&roleid=${roleid}";
			      document.rolePermForm.target = "_self";
			      document.rolePermForm.submit();
			   }
			}		
		   function returnList(){
		     window.location.href = "<%=path%>/role.do?method=getRoleList";
		   }
	</script>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
					            	<td height="20"></td>
					         	</tr>
							<tr>
								<td align="left">
									<table>
										<tr>
											<td nowrap="nowrap" align="right">
												<input class="button" onclick="if(confirm('你确定要设置吗？')){setSelectedIds();}" type="button" value="确定"></input>
											</td>
											<td nowrap="nowrap"><input type="button" onclick="returnList();" class="button" value="取消"></input>
											                    <input name="resIds" id="resIds" type="hidden" />
											</td>
											
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
