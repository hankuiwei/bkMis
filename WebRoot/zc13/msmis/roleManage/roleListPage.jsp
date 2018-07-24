<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	  <meta http-equiv="pragma" content="no-cache" />
	  <title>角色管理</title>
	  <link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
      <link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
      <link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src="<%=path%>/resources/js/main.js" charset="UTF-8"></script>
	  <script type="text/javascript" src="<%=path%>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	  <script type="text/javascript" src="<%=path%>/resources/js/RptTable.js" defer="defer"></script>
      <script type="text/javascript">
      //设置权限
      function setPerm(){
         var list = document.getElementsByName("roleid");
         var roleid = 0;
         var index = -1;
         for(var i = 0; i <list.length;i++){
             var elem = list[i];
             if(elem.checked){
                 roleid = elem.value;
                 break;
             }else{
             	index++;
             }
         } 
         if(roleid == 0){
            alert("请选择记录,再进行操作");
            return;
         }
        var status = document.getElementById("enabled"+index).value;
         if(status == 0){
               alert("该角色不可用!");
                  return;
        }else{
           document.roleListForm.action = "<%=path%>/role.do?method=getRolePerm&roleid="+roleid;
           document.roleListForm.target = "_self";
           document.roleListForm.submit();
         }
      }
      
     // 添加角色
     function addRole(){
         var win = window.showModalDialog("<%=path%>/role.do?method=goAddRole","","dialogWidth=600px;dialogHeight=400px;");
         if(win=="flag"){
         	alert("添加成功！");
         	document.roleListForm.submit();
         }
     }
        
     //修改角色
      function modifyRole(){
       var list = document.getElementsByName("roleid");
       var roleid = 0;
       for(var i=0;i<list.length;i++){
           var elem = list[i];
           if(elem.checked){
               roleid = elem.value;
           }
         }
         if(roleid == 0){
             alert("请选择一条记录,进行修改");
             return;
         }else{
           var a = window.showModalDialog("<%=path%>/role.do?method=getModifyInfo&roleid="+roleid,"","dialogWidth=600px;dialogHeight=400px;");
           if(a	=="flag"){
           		alert("修改成功！");
           		document.roleListForm.submit();
           }
         }  
      }
    //删除角色
     function deleteRole(){
       var list = document.getElementsByName("roleid");
       var roleid = 0;
       for(var i=0;i<list.length;i++){
           var elem = list[i];
           if(elem.checked){
               roleid = elem.value;
           }
         }
         if(roleid == 0){
             alert("请选择一条记录,进行删除");
             return;
         }else{
            if(confirm("确定要删除吗?")){
              document.roleListForm.action = "role.do?method=deleteRole&roleid="+roleid;
              document.roleListForm.target = "_self";
              document.roleListForm.submit();
         }  
      }
   }
   //设置角色提示信息            
   function setTip(){
       var list = document.getElementsByName("roleid");
       var roleid = 0;
       for(var i=0;i<list.length;i++){
           var elem = list[i];
           if(elem.checked){
               roleid = elem.value;
           }
         }
       if(roleid == 0){
           alert("请先选择角色，再进行次操作！");
           return;
       }else{
           window.showModalDialog("<%=path%>/role.do?method=goSetTips&roleid="+roleid,"","dialogWidth=600px;dialogHeight=400px;");
           document.roleListForm.submit();
       }
   }
   </script>
	</head>
	<body style="overflow-y: hidden;">
		<form id="roleListForm" name="roleListForm" method="post">
			<table width="99%" height="96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout: fixed">
				<tr><td height="5"></td></tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">角色维护</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr height="95%">
					<td class="menu_tab2" align="center" valign="top">
						<!-- 表单内容区域 -->
						<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed">
							<tr height="95%">
								<td width="100%">
									<div id="div1" class="RptDiv">
										<table class="RptTable" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<th class = "RptTableHeadCellFullLock"><input type="hidden" name="roleId" />&nbsp;</th>
												<th class="RptTableHeadCellFullLock">角色名称</th>
												<th class="RptTableHeadCellFullLock">角色描述</th>
												<th class="RptTableHeadCellLock">使用状态</th>
												<th class="RptTableHeadCellLock">更新时间</th>
											</tr>
											<c:choose>
												<c:when test="${empty roleList}">
													<tr>
														<td colspan="5" align="center">暂时没有角色信息!</td>
													</tr>
												</c:when>
												<c:otherwise>
													<c:forEach items="${roleList}" var="role" varStatus="aaa">
														<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
															<td class="RptTableBodyCellLock" align="center"><input type="radio" id="roleid" name="roleid" value="${role.roleid}" /></td>
															<td class="RptTableBodyCellLock" align="center" title="${role.rolename}">${role.rolename}</td>
															<td class="RptTableBodyCell" align="center" title="${role.roledesc}">&nbsp;${role.roledesc}</td>
															<td class="RptTableBodyCell" align="center">
																<c:if test="${role.enabled == 0}">不可用&nbsp;</c:if>
																<c:if test="${role.enabled == 1}">可用&nbsp;</c:if>
																<input type="hidden" id="enabled${aaa.index }" name="enabled" value="${role.enabled}" />
															</td>
															<td class="RptTableBodyCell" align="center" title="更新时间">${role.updatetime}</td>
														</tr>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<!-- 分页start -->
							<tr>
								<td colspan="10">
									<table width="100%" cellpadding="0" cellspacing="0">
										<tr>
											<td class="form_line3">&nbsp;</td>
											<td class="form_line3" colspan="8">${pagination }</td>
											<td class="form_line3">&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td align="right">
									<table>
										<tr>
											<td nowrap="nowrap" align="right"><input class="button" onclick="setPerm();" type="button" value="设置权限"></td>
											<td nowrap="nowrap"><input type="button" onclick="addRole();" class="button" value="添加"></td>
											<td nowrap="nowrap"><input type="button" onclick="modifyRole();" class="button" value="编辑"></td>
											<td nowrap="nowrap"><input type="button" onclick="deleteRole();" class="button" value="删除"></td>
											<td nowrap="nowrap"><input type="button" onclick="setTip();" class="button" value="设置角色提示信息"></td>
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
