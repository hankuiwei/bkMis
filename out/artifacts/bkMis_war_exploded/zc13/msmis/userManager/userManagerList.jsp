<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
<head>
	<title>用户列表</title>
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/AjaxObject.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/JavaProxy.js" defer="defer"></script>
	<script language = "javascript">
			function encode(url){
				//setInterval("showLoadingDiv()",300);
				setTimeout("showLoadingDiv()",1);
				encodeURI(encodeURI(url));
				window.location=url;
			}
		</script>
	<script type="text/javascript">
		//检查被选中的数量
		function checknum(isChecked){
			var k = 0;
	  		for(var i = 0;i<isChecked.length;i++){
	  			if(isChecked[i].checked){
	  				document.getElementById("isChecked").value = isChecked[i].value;
	  				k++;
	  			}
	  		}
	  		return k;
		}
		//跳至用户权限管理界面
		function roleManager(){
	  		var isChecked = document.getElementsByName("usercheck");
	  		var k = checknum(isChecked);
	  		if(k==0){
	  		    alert("请选择用户");
	  		    return;
	  		}if(k>1){
	  			alert("一次只能对一个用户进行操作");
	  			return;
	  		}
	  		else{
	  			form1.action = "userManager.do?method=findRole";
	  			form1.submit();
	  		}
	  	}
	  	//新增用户
		function addUser(){
		    var url = "<%=path%>/userManager.do?method=goAddUser";
			//var url = "zc13/msmis/userManager/addUser.jsp";
			var options = "dialogWidth:640px;dialogHeight:300px;status:no;scroll:no;";
			var win = window.showModalDialog(url, this.window, options);
			if(win=="flag"){
				alert("添加成功");
				window.location.href = "userManager.do?method=findUser";
			}
	  	}
	  	
	  //编辑用户信息
	  function modifyUser(){
	  	
	      
	      var e = document.getElementsByName("usercheck");
	      var count = 0;
	      var userId ;
	      for(var i=0;i<e.length;i++){
	          if(e[i].checked && e[i].type == "checkbox"){
	             count++;
	             userId = e[i].value;
	          }
	      }
	      if(count == 0){
	             alert("请先选择用户进行修改操作！");
	             return false;
	      }else if(count == 1){
	      	   
	           var a = window.showModalDialog("<%=path%>/userManager.do?method=getModifyInfo&userId="+userId,"","dialogWidth=640px;dialogHeight=340px;");
               if(a=="flag"){
               		alert("修改成功！");
               		document.form1.submit();	
               }
               
               
	      }else{
	          alert("请选择一个用户进行修改！");
	          return;
	      }
	  }
	  	
	  	//删除多条
	  	function delete2(){
	  		var isChecked = document.getElementsByName("usercheck");
	  		var k = checknum(isChecked);
	  		if(k==0){
	  		    alert("请选择至少一个用户");
	  		    return;
	  		}else{
	  			if(confirm("确定要删除这"+k+"条记录？")){
	  				var ids = "";
	  				for(var i=0;i<isChecked.length;i++){
		  				if(isChecked[i].checked){
		  					ids += isChecked[i].value+",";
		  				}
	  				}
	  				ids = ids.substring(0,ids.length-1);
	  				form1.action = "userManager.do?method=deleteUser&ids="+ids;
			  		form1.submit();
	  			}
	  		}
	  	}
	  //根据用户输入的字符进行模糊查询
	  function searchByUserName(){
	      document.form1.action = "<%=path%>/userManager.do?method=findUser";
	      document.form1.submit();
	  }
	   //选中所有的checkbox
      var flag = "false";
      function selectedAll(){
         var list = document.getElementsByName("usercheck");
         if(flag == "false"){
            for(var i=0;i<list.length;i++){
             list[i].checked = true;
          }
            flag = "true";
          }else{
          	for(var i=0;i<list.length;i++){
              list[i].checked = false;
           	}
            flag = "false";
      	  }
      }
    </script>
</head>
<body onload="javascript:tableWH();">
<!-- 加载页面div -->
<jsp:include page="/loading.jsp"></jsp:include>
<!-- 加载页面div -->
<form  method="post" name="form1">
	<input type="hidden" id="isChecked" name="isChecked">
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">用户信息管理</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr>
    		<td class="menu_tab2" align="center"><!-- 这个td负责画正式内容的左、右、下方的边框 -->
     			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 				<tr>
						<td  align="center">
							<!-- 查询条件start -->
		  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
					            	<td height="10"></td>
					         	</tr>
					          	<tr>
					           		<td class="txt" nowrap="nowrap">
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					           		</td>
					            	<td width = "60%" align = "left">用户名称：<input type="text" id="username" name="username" value ="${userForm.username }"></td>
									<td align="right">
					            		<input type="button" id = "focuson" onclick="searchByUserName();" class="button" value="确定">
									</td>
								</tr>
							 	 <tr>
					           		 <td height="10" colspan="9"></td>
					          	 </tr>
					        </table>
					        <!-- 查询条件end -->
		 			 	</td>
					</tr>
  					<tr>
					    <td>
					    	<!-- 表单内容区域 -->
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
					        		<td>
					        		<div id = "div1" class = "RptDiv" style="width:100%;height:10% ; overflow:auto" >
							   			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form_tab">
					              			<tr>
								                <th class="RptTableHeadCellLock"  width="2%"><input type="checkbox" onclick="selectedAll();"></th>
												<th class="RptTableHeadCellLock" width="5%">序号</th>
												<th class="RptTableHeadCellLock" width="15%">用户名</th>
												<th class="RptTableHeadCellLock" width="17%">真实姓名</th>
												<th class="RptTableHeadCellLock" width="17%">部门</th>
												<th class="RptTableHeadCellLock" width="23%">联系电话</th>
												<th class="RptTableHeadCellLock">描述</th>
											</tr>
											<c:choose>
											<c:when test="${empty userForm.userList}">
											<tr align="center">
												<td colspan="8" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${userForm.userList}" var="user" varStatus="tag">
													<tr  onmouseover="if(!isIE6)this.className = 'hover';" onmouseout="if(!isIE6)this.className = '';">
														<td class = "RptTableBodyCellLock" align="center"  width="2%">
															<input type="checkbox" name="usercheck" value="${user.userid}">
														</td>
														<td class="RptTableBodyCell" align="center"  width="5%">${tag.count}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" width="15%">${user.username}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" width="17%">${user.realName}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" width="17%">${user.department}&nbsp;</td>
														<td class="RptTableBodyCell" align="center"  width="23%">${user.phone}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" >${user.description}&nbsp;</td>
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
											<tr><td class="form_line3">&nbsp;</td>
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
					                			<td nowrap="nowrap"  align="right"><input class="button" onclick="addUser();" type="button" value="添加"></td>
					                			<td nowrap="nowrap"  align="right"><input class="button" onclick="modifyUser();" type="button" value="编辑"></td>
					               				<td nowrap="nowrap"><input type="button" onclick="roleManager();" class="button" value="权限管理"></td>
					               				<td nowrap="nowrap"><input type="button" onclick="delete2();" class="button" value="删除"></td>
					              			</tr>
					              		</table>
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