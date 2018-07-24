<%@ page language="java" import="com.zc13.util.*"  pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.GlobalMethod"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>新增客户报修</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	<base href="<%=basePath%>" target="_parent">
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	 <script type="text/javascript"
			src="<%=path%>/resources/js/util/util.js"></script>

		<script type="text/javascript"
			src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript">
		function add(){
			var lp = document.getElementById("buildId").value;
			var type = document.getElementById("type").value;
			if(lp == "0" && type=="业主报修" ){
				alert("请选择楼盘!");
				return;
			}
			document.form1.action = "client.do?method=addClient";
			document.form1.submit();
		}
		function selroom(){
			var type = document.getElementById("type").value;
			var area = document.getElementById("area");
			var roomCode = document.getElementById("roomId");
			if(type=="业主报修"){
				document.getElementById("td1").innerText = "房间号"+"：";
				document.getElementById("tr11").style.display="block";
				roomCode.style.display = "block";
				area.style.display = "none";
				area.options.length=0;
			}
			if(type=="公共区域"){
				document.getElementById("td1").innerText = "区域"+"：";
				document.getElementById("tr11").style.display="none";
				roomCode.style.display = "none";
				area.style.display = "block";
				$.post("<%=path%>/client.do",{method:"codeList"},function(data){
					area.options.length=0;
					area.style.width="130";
					var arr = data.split(",");
					for(var i=0;i<arr.length;i++){
						var option = new Option(arr[i], arr[i]);
						area.add(option);
					}
				});			
			}
		}
		function return1(){
			this.close();
		}
		
		function selectRoom(id){
			var area = document.getElementById("roomId");
			area.options.length=0;
			var option = new Option("请选择","0");
			area.add(option);
			$.post("<%=path%>/client.do",{method:"roomList",id:id},function(data){
					var arr3 = data.split(";");
					var arr1 = arr3[0].split(",");
					var arr2 = arr3[1].split(",");
					for(var i=0;i<arr1.length;i++){
						var option = new Option(arr1[i], arr2[i]);
						area.add(option);
					}
				});		
		}
		
		//选择报修项目
		function changeProject(){
			var projects = document.getElementById("maintainProject").value;
			var pro = projects.split(",");
			document.getElementById("projectId").value = pro[0];
			document.getElementById("project").value = pro[1];
		}
		
	</script>
</head>
<body onload="changeProject();" onunload="window.returnValue = 'flag';window.close();">
<form name = "form1" method="post">
	<!-- 新增的客户报修默认状态为未处理 -->
	<input type="hidden" name="status" value="<%=Contants.DISPATCHING_WAIT %>" />
	
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
		  		<tr>
		    		<td>
		    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      				<tr>
		        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">新增客户报修</td>
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
								<td colspan="5" align="center">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="10" colspan="9"></td>
										</tr>

										<tr>
											<td>
												<table align="center" border="0" cellpadding="3"
													cellspacing="0" class="form_tab">
													<tr>
														<td class="head_rols" align="right">
															报修类型：
														</td>
														<td class="fist_rows">
															<select id="type" name="type" onchange="selroom()" style="width: 130">
																<option value="<%=Contants.MAINTAIN_TYPE_OWNER %>"><%=Contants.MAINTAIN_TYPE_OWNER %></option>
																<option value="<%=Contants.MAINTAIN_TYPE_PUBLIC %>"><%=Contants.MAINTAIN_TYPE_PUBLIC %></option>
															</select>
														</td>
														<td class="fist_rows" align="right">
															报修项目：
														</td>
														<td class="fist_rows">
															<select id="maintainProject" name="maintainProject" style="width: 130" onchange="changeProject();">
															<c:choose>
																<c:when test="${empty list}">
																</c:when>
																<c:otherwise>
																	<c:forEach items="${list}" var="v">
																		<option value="${v.id },${v.name }">${v.name }</option>
																	</c:forEach>
																</c:otherwise>
															</c:choose>
															</select>
															<input type="hidden" name="project" id="project" value="" />
															<input type="hidden" name="projectId" id="projectId" value="" />
														</td>
													</tr>
													<tr id="tr11" style="display: none;">
														<td class="head_left" align="right">
															楼幢：
														</td>
														<td class="head_form1" colspan="3">
															<select  onchange="selectRoom(value)" style="width: 130" name="buildId" id="buildId">
																<option value="0">请选择</option>
																<c:choose>
																<c:when test="${empty list2}">
																</c:when>
																<c:otherwise>
																	<c:forEach items="${list2}" var="v">
																		<option value="${v.buildId }">${v.buildName }</option>
																	</c:forEach>
																</c:otherwise>
															</c:choose>
															</select>
															<font color="red">*</font>
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right" id="td1">
															房间号：
														</td>
														<td class="head_form1" align="center">
															<select name="roomId" id="roomId" style="display: block;" style="width: 130"><option>请选择</option></select>
															<select id="area" name="area" style="display: none;"></select>
														</td>
														<td class="head_form1" align="right">
															报修人：
														</td>
														<td class="head_form1">
															<input type="text" name="name">
														</td>
													</tr>
													
													<tr>
														<td class="head_left" align="right">
															联系电话：
														</td>
														<td class="head_form1">
															<input type="text" name="phone">
														</td>
														<td class="head_form1" align="right">
															&nbsp;
														</td>
														<td class="head_form1">
															&nbsp;
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															报修内容：
														</td>
														<td class="head_form1" colspan="3">
															<textarea rows="3" style="width: 80%" id="contentExplain" name="contentExplain"></textarea>
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															报修时间：
														</td>
														<td class="head_form1">
															<input type="text" name="date"  style=" width: 138px" value="<%=GlobalMethod.getTime3() %>"  onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly onclick="WdatePicker();" class="Wdate">
														</td>
														<td class="head_form1" align="right">
															接听(接待)人：
														</td>
														<td class="head_form1">
															<input type="text" name="clerk" >
														</td>
													</tr>
													<tr>
														<td align="center" colspan="4" class="head_left">
															<input type="button" onclick="add()" class="button" value="确定">
															<input type="button" onclick="return1()" class="button" value="返回">
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="10" colspan="9"></td>
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
<script type="text/javascript">
		selroom();
	</script>
</html>
