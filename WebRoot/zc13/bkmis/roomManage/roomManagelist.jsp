<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>房间信息列表</title>
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="<%=path%>/resources/js/main.js"
			charset="UTF-8"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/js/util/My97DatePicker/WdatePicker.js"
			defer="defer"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/js/RptTable.js" defer="defer"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/js/util/util.js"></script>

		<script type="text/javascript">
      //新增房间
      function addRoom(){
         var buildId = document.getElementById("buildId").value;
         var tablename = document.getElementById("tablename").value;
        if(buildId == "" || buildId == null){
            alert("请选择楼栋进行添加操作！");
            return;
        }else if(tablename == "e_lp"){
             alert("请选择楼栋进行添加操作！");
             return;
        }else{
           var a = window.showModalDialog("<%=path%>/room.do?method=goAddRoom&buildId="+buildId,"","dialogWidth=800px;dialogHeight=600px;");
            if(a=="flag"){
           		alert("添加房间成功！");
            	document.roomform.submit();
            }
        }
      }
      //修改房间信息
      function modifyRoom(){
          var e = document.getElementsByName("roomId");
          var count = 0;
          var roomId;
          for(var i=0;i<e.length;i++){
            if(e[i].checked && e[i].type == "checkbox"){
               count++;
               roomId = e[i].value;
            }
         }
        if(count == 0){
            alert("请选择房间进行修改！");
            return;
        }else if(count == 1){
            var a = window.showModalDialog("<%=path%>/room.do?method=getModifyInfo&roomId="+roomId,"","dialogWidth=800px;dialogHeight=600px;");
            if(a=="flag"){
           		alert("修改成功！"); 
           		document.roomform.submit();
           	}
        }else{
           alert("只能选择一间房间进行修改！");
           return;
        }
          
      }
      //删除
      /*
      function deleteRoom(){
         var temp = document.getElementsByName("roomId"); 
         var count = 0;
         for(var i=0;i<temp.length;i++){
             if(temp[0].type == "checkbox" && temp[i].checked){
                 count++;
             }
         }
         if(count == 0){
            alert("请至少选择一条记录进行删除！");
            return;
         }else{
           var url = "<%=path%>/room.do";
           $.post(url,{method:"getRoomClientInfo"},function(data){});
           //$.post(url,{method:"getRoomClientInfo", count:count},function(data){});
            if(confirm("确定要删除吗?")){
              document.roomform.action = "<%=path%>/room.do?method=deleteRoom";
              document.roomform.target = "_self";
              document.roomform.submit();
            }
         }
      }  
      */ 
     //批量增加房间
     function batchincreaseRoom(){
         var buildId = document.getElementById("buildId").value;
         var tablename = document.getElementById("tablename").value;
        if(buildId == "" || buildId == null){
            alert("请选择楼栋进行添加操作！");
            return;
        }else if(tablename == "e_lp"){
             alert("请选择楼栋进行添加操作！");
             return;
        }else{
              var a = window.showModalDialog("<%=path%>/room.do?method=goBatchincreaseRoom&buildId="+buildId,"","dialogWidth=800px;dialogHeight=600px;");
              if(a=="flag"){
           		alert("批量添加房间成功！");
              	document.roomform.submit();
              }
        }
     }
      //选中所有的checkbox
      var flag = "false";
      function selectedAll(){
         var list = document.getElementsByName("roomId");
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
    <script language="javascript">
    var xmlHttpRequest;   
  	
  	//创建一个XMLHttpRequest对象
	function createXMLHttpRequest(){   
	    if(window.XMLHttpRequest)   
	    {   
	        xmlHttpRequest = new XMLHttpRequest();   
	    }if(window.ActiveXObject)   
	    {   
	      try   
	      {    
	        xmlHttpRequest=new ActiveXObject("Msxml2.XMLHTTP");    
	      }catch(e)   
	      {    
	        try   
	        {    
	            xmlHttpRequest=new ActiveXObject("Microsoft.XMLHTTP");    
	        }catch(e){}    
	      }    
	    }      
	} 
	
	//回调函数
	function callback(){     
	    if(xmlHttpRequest.readyState==4){//对象状态      
	        if(xmlHttpRequest.status==200){//信息已成功返回，开始处理信息    
	            var message = xmlHttpRequest.responseText;
	            var roomCode;  
	            var temp;
	            if((message.valueOf())==0){
	                  
	            }else{
                   temp = message.split(",");
                   if(temp.length <= 2){
                       for(var i=0;i<=temp.length-2;i++){
	                       roomCode = temp[i];
	                       alert(roomCode + "房间还有客户入驻，无法执行对" + roomCode +"删除操作！");
	                   }  
                   }else{
                         roomCode = temp;
                         alert(roomCode + "房间还有客户入驻，无法执行对" + roomCode +"删除操作！");
                   }
	                  
	            }
	            document.roomform.target = "_self";
                document.roomform.submit();
	        }   
	    }   
	}
	//删除房间
	function deleteRoom(){
			createXMLHttpRequest();
			var temp = document.getElementsByName("roomId"); 
            var count = 0;
            var roomId = "";
            for(var i=0;i<temp.length;i++){
              if(temp[0].type == "checkbox" && temp[i].checked){
                 roomId += temp[i].value + ";";
                 count++;
              }
          }
         if(count == 0){
            alert("请至少选择一条记录进行删除！");
            return;
         }else{
             if(confirm("确定要删除吗?")){
			     var url = "<%=path%>/room.do?method=deleteRoom&timeStamp="+new Date().getTime();
			     xmlHttpRequest.open("POST",url,true);
			     xmlHttpRequest.onreadystatechange = callback;
			     xmlHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
			     var params = "&roomId=" + roomId;
			     xmlHttpRequest.send(params);
             }
	   }
	}
    </script>
	</head>
		<body style="overflow-y: hidden;">
		<!-- 加载页面div -->
		<jsp:include page="/loading.jsp"></jsp:include>
		<!-- 加载页面div -->
		<form method="post" name="roomform">
			<table width="99%" height="96%" border="0" align="center"
				cellpadding="0" cellspacing="0" style="layout: fixed">
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">房间信息列表</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr height="95%">
					<td class="menu_tab2" align="center" valign="top">
						<table width="100%" height="100%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td align="center">
									<!-- 查询条件start -->
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="10" colspan="9"><input type="hidden" id="tablename" name="tablename" value="${roomForm.tablename}"></td>
										</tr>
										<tr>
											<td height="10" colspan="9"><input type="hidden" id="buildId" name="buildId" value="${roomForm.buildId}">
											                           <input type="hidden" id="lpId" name="lpId" value="${roomForm.lpId}">
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr height="95%">
								<td valign="top">
									<!-- 表单内容区域 -->
									<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed">
										<tr height="95%">
											<td width="100%">
												<div id="div1" class="RptDiv">
													<table border="0" cellpadding="0" cellspacing="0" class="RptTable">
														<tr>
														    <th class = "RptTableHeadCellFullLock"><input type="checkbox" onclick="selectedAll();"></th>
															<th width="6%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
															<th width="10%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">房间号</th>
															<th width="9%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房型</th>
															<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">所属楼栋</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">单元号</th>
															<th width="6%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">楼层</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">建筑面积</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">使用面积</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">朝向</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">使用状态</th>
															<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">所属区域</th>
															<th width="13%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">备注</th>
														</tr>
														<c:choose>
															<c:when test="${empty roomlist}">
																<tr align="center">
																	<td colspan="14" align="center" class="head_form1">
																		<font color="red">暂时没有符合条件的房间信息!</font>
																	</td>
																</tr>
															</c:when>
															<c:otherwise>
																<c:forEach items="${roomlist}" var="r" varStatus="vs">
																	<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
																	    <td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="roomId" name="roomId" value="${r.roomId}">
																	    </td>
																		<td nowrap="nowrap" class="RptTableBodyCellLock" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCellLock" align="center" title="${r.roomCode}">${r.roomCode}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center"title="${r.roomType}">${r.roomType}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.EBuilds.buildName}">${r.EBuilds.buildName}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.unitId}">${r.unitId}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.floor}">${r.floor}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.constructionArea}">${r.constructionArea}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.useArea}">${r.useArea}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.toward}">${r.toward}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.status}">${r.status}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.regional}">${r.regional}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${r.bz}">
																		 <c:choose> 
																		    <c:when test="${fn:length(r.bz) > 20}"> 
																		     <c:out value="${fn:substring(r.bz, 0, 20)}......" /> 
																		    </c:when> 
																		    <c:otherwise> 
																		     	<c:out value="${r.bz}" /> 
																		    </c:otherwise> 
																		   </c:choose> &nbsp;
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
										<!-- 分页end -->
										<tr>
											<td align="right">
												<table>
													<tr>
														<td nowrap="nowrap" align="right">
															<input class="button" onclick="addRoom();" type="button" value="添加">
														</td>
														<td nowrap="nowrap">
															<input type="button" onclick="modifyRoom();" class="button" value="编辑">
														</td>
														<td nowrap="nowrap">
															<input type="button" onclick="deleteRoom();" class="button" value="删除">
														</td>
														<td nowrap="nowrap">
															<input type="button" onclick="batchincreaseRoom();" class="button" value="批量增加">
														</td>
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








