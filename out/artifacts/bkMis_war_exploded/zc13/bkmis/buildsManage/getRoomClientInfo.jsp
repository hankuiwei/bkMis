<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>房间入住客户信息</title>
		<base target="_self" />
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
		<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
		<script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
		<script type="text/javascript">
		   //检查表单数据,添加房间图
		  function checkForm(){
		     var roomId = document.getElementById("roomCode").value;
		     if(roomId == ""){
		         alert("请选择房间！");
		         return false;
		     }
		     var clientName = document.getElementById("clientName").value; 
		     var clientId = document.getElementById("clientId").value;
		     var pwidth = document.getElementById("pwidth").value;
		     var rStatus = document.getElementById("rStatus").value;
		     var roomCodeObj = document.getElementById("roomCode");
		     var roomCode = roomCodeObj.options[roomCodeObj.selectedIndex].text;
		     var str = new Array(roomId,clientName,pwidth,clientId,rStatus,roomCode);
		     $.post("<%=path%>/build.do",{method:"checkRoomMapByRoomId",roomId:roomId},function(data){
		         if(data == 'false'){
		            if(confirm("确定要增加吗？")) {
	          		   window.returnValue = str;  //回传数据
	          		    self.close();    
          		     }
		         }else{
		             alert("该房间在楼层平面图上已经存在，请选择操作其他房间，或者取消本次操作！");
		             return false;
		         }
		     });
		  }
		  
		 /* 下拉菜单级联出显入住客户名称 */
		 function getClent(roomId){
		     var roomCode = document.getElementById("roomCode");
		     var clientName = document.getElementById("clientName");
		     var clientId = document.getElementById("clientId");
		     var rStatus = document.getElementById("rStatus");
		     $.post("<%=path%>/build.do",{method:"getRoomStatusByRoomId",roomId:roomId},function(data){
		         if(data != null || data != "null"){
		                rStatus.value = data;
		         }
		     });
		     //根据房间查询客户入住信息
		     $.post("<%=path%>/build.do",{method:"getClientInfoByRoomId",roomId:roomId},function(data){
		           if(data == null|| data == "null"){
		               clientName.value = "";
		           }else{
		              var temp = data.split(";");
		               clientName.value = temp[0];
		               clientId.value = temp[1];
		           }
		       });
		 }
		 //关闭窗口
		 function closeModify(){
             window.close();
             
		  }
        </script>
	</head>
	<body>
		<form method="post" name="getRoomClientForm">
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">房间客户入住情况</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
							</tr>
						</table>
					</td>
				</tr>
			    <tr>
					<td class="menu_tab2" align="center">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="center">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="10" colspan="9"><input type="hidden" id="lpId" name="lpId" value="${lpId }"></td>
										</tr>
										<tr>
											<td height="10" colspan="9"><input type="hidden" id="clientId" name="clientId">
											                            <input type="hidden" id="rStatus" name="rStatus">
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left">
										<tr>
											<td align="right" class="head_form1" width="12%">房间号：<font color="red">*</font></td>
											<td align="left"  class="head_form1" width="33%">&nbsp;
											    <select name="roomCode" style="width: 130px;" onchange="getClent(value);">
													<option value="" selected="selected">- - -请选择- - -</option>
													<c:forEach items="${roomlist}" var="r">
														<option value="${r.roomId}">${r.roomCode}</option>
													</c:forEach>
												</select>
											</td>
										</tr>
											<td align="right" class="head_form1" width="23%">客户名称：</td>
											<td align="left" class="head_form1">&nbsp;&nbsp;<input type="text" id="clientName" name="clientName" readonly="readonly"></td>
										<tr>
											<td align="right" class="head_form1" width="23%">图片宽度：<font color="red">*</font></td>
											<td align="left" class="head_form1">&nbsp;&nbsp;<input type="text" id="pwidth" name="pwidth" value="50"></td>
										</tr>
										<tr></tr>
										<tr height="20">
											<td></td>
										</tr>
										<tr>
											<td align="right">
												<table align="right">
													<tr align="right">
														<td nowrap="nowrap" align="right"><input class="button" onclick="checkForm();" type="button" id = "focuson" value="确定"></td>
														<td nowrap="nowrap"><input type="button" class="button" value="取消" onclick="closeModify();"></td>
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