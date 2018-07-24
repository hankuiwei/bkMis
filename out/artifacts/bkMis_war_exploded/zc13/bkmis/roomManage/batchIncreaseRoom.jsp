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
	 <title> 批量添加房间</title>
	    <base target="_self" />
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/main.js" charset="UTF-8"></script>
		<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
		<script type="text/javascript">
		   function checkForm(){
		   	
		    with(document.batchIncreseForm){
		     if((trim(beginRoomCode.value)=="")){
		           alert("请输入开始数字！");
				   return false;
		     }else if((trim(endRoomCode.value)=="")){
		            alert("请输入截至数字！");
		            return false;
		     }else if(parseInt(endRoomCode.value)< parseInt(beginRoomCode.value)){
		            alert("开始数字不能大于截止数字！");
		            return false;
		     }else{
		     floor
		         if((trim(floor.value)=="")){
		            alert("请选择楼层！");
		            return false;
		         }
		     }
	         /*if((trim(croomCode.value) + trim(beginRoomCode.value)=="")&&(trim(croomCode.value) + trim(endRoomCode.value)=="")){
				alert("请输入房间号");
				return false;
			}*/
			
	         /*var p="^[^0-9]+$";
	         if(!(croomCode.value==""||trim(croomCode.value).length==0)){
	         	if(!(croomCode.value.match(p)!=null)){
	         	    alert("非数字列不能包含数字");
	         	    croomCode.value="";
	         	    croomCode.focus();
	         	    return false;
	         	}
	         }*/
	         
	        var p="^([0-9]|[1-9][0-9]+)$";
	        var k=0;
	        var z=0;
		 
	        /*if(!(beginRoomCode.value==""||trim(beginRoomCode.value).length==0)){
	            if(!(beginRoomCode.value.match(p)!=null)){
		                alert("开始数字必须是大于等于0的整数");
		                beginRoomCode.value="";
		                beginRoomCode.focus();
		                return false;
		          }else{
		                k=parseFloat(beginRoomCode.value);
		          }
	        }
	       
	        if(!(endRoomCode.value==""||trim(endRoomCode.value).length==0)){
	            if(!(endRoomCode.value.match(p)!=null)){
		                alert("截止数字必须是大于等于0的整数");
		                endRoomCode.value="";
		                endRoomCode.focus();
		                return false;
		         }else{
		                z=parseFloat(endRoomCode.value);
		         }
	        }*/
	        var j=parseFloat(z)-parseFloat(k);
	        if(k!=0&&z!=0){
	            if(j>99){
	                alert("每次最多能增加100间房");
	                return false;
	            }
	        }
	       // if(status.value==""){
	         //     alert("请输入使用状态！");
	          //    status.focus();
	           //   return false;
	         // }
	         
		 }
		document.batchIncreseForm.action = "<%=path%>/room.do?method=batchincreaseRoom";
		var zj = confirm("你确定要增加吗？");
	    	if(zj) {
	    		document.batchIncreseForm.submit();
	    		//window.close(); 
	    	}
	  }
		  function closeWindow(){
             window.close();
		  }
        </script> 
  </head>
	<body>
		<form method="post" name="batchIncreseForm" action="<%=path%>/room.do?method=addRoom">
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="5" colspan="9">
						<c:if test="${flag}">
							<script type="text/javascript">
								returnValue = "flag";
								this.close();
							</script>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">批量添加房间</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" 	height="35"></td>
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
											<td height="10" colspan="9"></td>
										</tr>
										<tr>
											<td height="10" colspan="9"><input type="hidden" id="buildId" name="buildId" value="${ebuild.buildId }"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left">
										<tr>	
											<td align="right" class="head_form1" width="0.5%">房间号&nbsp;&nbsp;：非数字列：</td>
											<td align="left" class="head_form1" width="12%">&nbsp;&nbsp;<input type="text" id="croomCode" name="croomCode"></td>
											<td align="right" class="head_form1" width="22%">开始数字：</td>
											<td align="left" class="head_form1" width="23%">&nbsp;&nbsp;<input type="text" id="beginRoomCode" name="beginRoomCode" onKeyUp="this.value=this.value.replace(/\D/g,'')"><font color="red">*</font></td>
											<td align="right" class="head_form1" width="33%">截至数字：</td>
											<td align="left" class="head_form1" width="12%">&nbsp;&nbsp;<input type="text" id="endRoomCode" name="endRoomCode" onKeyUp="this.value=this.value.replace(/\D/g,'')"><font color="red">*</font></td>
										</tr>
										<tr>
										    <td align="right" class="head_form1" width="0.5%">房&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：</td>
											<td align="left" class="head_form1" width="12%">&nbsp;
												<select name="roomType" style="width: 130px;">
													<option value="" selected="selected">- - -请选择- - -</option>
													<c:forEach items="${roomTypelist }" var="r">
														<option value="${r.codeName }">${r.codeName }</option>
													</c:forEach>
												</select>
											</td>
											<td align="right" class="head_form1" width="22%">单&nbsp;&nbsp;元&nbsp;&nbsp;号：</td>
											<td align="left" class="head_form1" width="23%">&nbsp;&nbsp;<input type="text" id="unitId" name="unitId"></td>
											<td align="right" class="head_form1" width="33%">&nbsp;楼&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;层：</td>
											<td align="left" class="head_form1" width="12%">&nbsp;
												<select name="floor" style="width: 130px;">
													<option value="1" selected="selected">1</option>
													<c:if test="${!empty ebuild}">
													<c:forEach begin="1" end="${ebuild.floor-1}" varStatus="vs">
														<option value="${vs.count+1}">${vs.count+1}</option>
													</c:forEach>
													</c:if>
												</select><font color="red">*</font>
											</td>
										</tr>
										<tr>
										    <td align="right" class="head_form1" width="0.5%">建筑面积：</td>
											<td align="left" class="head_form1" width="12%">&nbsp;&nbsp;<input type="text" id="constructionArea" name="constructionArea"></td>
											<td align="right" class="head_form1" width="22%">使用面积：</td>
											<td align="left" class="head_form1" width="23%">&nbsp;&nbsp;<input type="text" id="useArea" name="useArea"></td>
											<td align="right" class="head_form1" width="33%">房间朝向：</td>
											<td align="left" class="head_form1" width="12%">&nbsp;
												<select name="toward" style="width: 130px;">
													<option value="" selected="selected">- - -请选择- - -</option>
													<c:forEach items="${towardlist}" var="t">
														<option value="${t.codeName}">${t.codeName}</option>
													</c:forEach>
												</select>
											</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="0.5%" >所属区域：</td>
											<td align="left" class="head_form1" width="12%" colspan="5">&nbsp;&nbsp;<input type="text" id="regional" name="regional" ></td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="33%" >备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
											<td align="left" class="head_form1" width="33%" colspan="5">&nbsp;&nbsp;<textarea style="width: 98%; height: 50px; background-color: #F0F8FA" name="bz" title="最多1000个字符"></textarea></td>
										</tr>
										<tr height="60">
											<td></td>
										</tr>
										<tr>
											<td align="center" colspan="6">
												<table >
													<tr>
														<td nowrap="nowrap" ><input class="button" onclick="checkForm();" type="button" id = "focuson" value="确定"></td>
														<td nowrap="nowrap"><input type="button" class="button" value="取消" onclick="javascript:window.close();"></td>
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
