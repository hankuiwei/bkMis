<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
<head>
	<title>楼盘管理员列表</title>
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
	  	//新增用户
		function addUser(){
		    var url = "<%=path%>/userManager.do?method=goAddManager";
			//var url = "zc13/msmis/userManager/addUser.jsp";
			var options = "dialogWidth:640px;dialogHeight:300px;status:no;scroll:no;";
			var win = window.showModalDialog(url, this.window, options);
			if(win=="flag"){
				alert("添加成功");
				window.location.href = "userManager.do?method=getAllManager";
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
	      	   
	           var a = window.showModalDialog("<%=path%>/userManager.do?method=goModifyManager&userid="+userId,"","dialogWidth=640px;dialogHeight=340px;");
               if(a=="flag"){
               		alert("修改成功！");
               		document.form1.submit();	
               }
               
               
	      }else{
	          alert("请选择一个用户进行修改！");
	          return;
	      }
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
      
      function del(){
      	var list = document.getElementsByName("usercheck");
      	var ids = "";
      	for(var i = 0;i<list.length;i++){
      		if(list[i].checked){
      			ids+=list[i].value+",";
      		}
      	}
      	if(ids==""){
      		alert("请选择要删除的记录！");
      		return;
      	}
      	if(window.confirm("您确定要删除吗？")){
	      	ids = ids.substring(0,ids.length-1);
	      	document.form1.action="<%=path%>/userManager.do?method=deletManager&ids="+ids;
	      	document.form1.submit();
      	}
      }
    </script>
    <c:if test="${!empty message}">
    	<script language = "javascript">
    		alert("${message}");
    	</script>
    </c:if>
</head>
<body onload="javascript:tableWH();">
<!-- 加载页面div -->
<jsp:include page="/loading.jsp"></jsp:include>
<!-- 加载页面div -->
<form  method="post" name="form1" action="<%=path%>/userManager.do?method=getAllManager">
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
						<td width="165" nowrap="nowrap" class="form_line">楼盘管理员列表</td>
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
					            	<td width = "60%" align = "left">用户名称：<input type="text" id="username" name="username" value ="${username }"></td>
									<td align="right">
					            		<input type="submit" id = "focuson" class="button" value="确定">
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
												<th class="RptTableHeadCellLock" width="20%">用户名</th>
												<th class="RptTableHeadCellLock" width="45%">描述</th>
												<th class="RptTableHeadCellLock">楼盘</th>
											</tr>
											<c:choose>
											<c:when test="${empty userManagerList}">
											<tr align="center">
												<td colspan="8" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的记录!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${userManagerList}" var="user" varStatus="tag">
													<tr  onmouseover="if(!isIE6)this.className = 'hover';" onmouseout="if(!isIE6)this.className = '';">
														<td class = "RptTableBodyCellLock" align="center"  width="2%">
															<input type="checkbox" name="usercheck" value="${user.userid}">
														</td>
														<td class="RptTableBodyCell" align="center"  width="5%">${tag.count}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" width="15%">${user.username}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" >${user.description}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" width="15%">
														<c:forEach items="${lpList}" var="lp">
															<c:if test="${lp.lpId eq user.lpId}">
																${lp.lpName}
															</c:if>
														</c:forEach>
														&nbsp;
														</td>
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
									<td colspan="5">
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
					               				<td nowrap="nowrap"><input type="button" onclick="del();" class="button" value="删除"></td>
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