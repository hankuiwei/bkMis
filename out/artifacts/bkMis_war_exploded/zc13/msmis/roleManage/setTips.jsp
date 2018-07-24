<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>设置提示信息</title>
		<base target="_self" />
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
		<script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
		<script type="text/javascript">
		   //设置任务提示信息
		   function checkForm(){
		       var list = document.getElementsByName("tipId");
		       var roleId = document.getElementById("roleId").value;
		       var tipId;
		       var count;
		       for(var i=0;i<list.lenght;i++){
		            if(list[i].checked){
		                tipId = list[i].value;
		                 count++;
		            }
		       }
		       if(count == 0){
		          alert("没有选择任务提示！");
		          return;
		       }else{
		          document.tipForm.action = "<%=path%>/role.do?method=setTips&roleId="+roleId;
		          document.tipForm.submit();
		          window.close();
		       }
		   }
		   function closeWnidow(){
		      window.close();
		   }

        </script>
	</head>
	<body>
		<form method="post" name="tipForm">
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">设置角色的任务提示</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
							</tr>
						</table>
					</td>
				</tr>
			    <tr height="85%">
					<td class="menu_tab2" align="center">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="center">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="2" colspan="9"><input type="hidden" name="roleId" id="roleId" value="${roleId}"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table width="65%" border="0" cellspacing="0" cellpadding="0" align="center">
										<tr>
											<td>全选<input type ="checkbox" id="all" onclick="selectAll('tipId');"></td>
										</tr>
										<!-- 这是给任务分类了，暂时没有好的解决方法，所以每种类型单独循环一次输出 -->
										<tr>
											<td colspan="3"><span style=" color: blue;">合同状态相关任务:</span></td>
										</tr>
										<tr>
											<c:forEach items="${tipList }" var="tip" varStatus="loop">
											<c:if test="${tip.type==0}">
											<c:if test = "${loop.index%3 == 0}">
				             			<tr>
				             				</c:if>
												 <td><input type="checkbox"  name="tipId" value="${tip.id }" <c:forEach items="${rtipList}" var="rt"><c:if test="${rt[0].name == tip.name}">checked</c:if></c:forEach>/>${tip.name}</td>
											</c:if>
											</c:forEach>
										</tr>
										<!-- end这是给任务分类了，暂时没有好的解决方法，所以每种类型单独循环一次输出 -->
										<!-- 这是给任务分类了，暂时没有好的解决方法，所以每种类型单独循环一次输出 -->
										<tr>
											<td colspan="3"><span style=" color: blue;">合同校验相关任务:</span></td>
										</tr>
										<tr>
											<c:forEach items="${tipList }" var="tip" varStatus="loop">
											<c:if test="${tip.type==1}">
											<c:if test = "${loop.index%3 == 0}">
				             			<tr>
				             				</c:if>
												 <td><input type="checkbox"  name="tipId" value="${tip.id }" <c:forEach items="${rtipList}" var="rt"><c:if test="${rt[0].name == tip.name}">checked</c:if></c:forEach>/>${tip.name}</td>
											</c:if>
											</c:forEach>
										</tr>
										<!-- end这是给任务分类了，暂时没有好的解决方法，所以每种类型单独循环一次输出 -->
										<!-- 这是给任务分类了，暂时没有好的解决方法，所以每种类型单独循环一次输出 -->
										<tr>
											<td colspan="3"><span style=" color: blue;">合同审批相关任务:</span></td>
										</tr>
										<tr>
											<c:forEach items="${tipList }" var="tip" varStatus="loop1">
											<c:if test="${tip.type==2}">
											<c:if test = "${loop1.index%3 == 0}">
				             			<tr>
				             				</c:if>
												 <td><input type="checkbox" name="tipId" value="${tip.id }" <c:forEach items="${rtipList}" var="rt"><c:if test="${rt[0].name == tip.name}">checked</c:if></c:forEach>/>${tip.name}</td>
											</c:if>
											</c:forEach>
										</tr>
										<!-- end这是给任务分类了，暂时没有好的解决方法，所以每种类型单独循环一次输出 -->
										<!-- 这是给任务分类了，暂时没有好的解决方法，所以每种类型单独循环一次输出 -->
										<tr>
											<td colspan="3"><span style=" color: blue;">报修投诉相关任务:</span></td>
										</tr>
										<tr>
											<c:forEach items="${tipList }" var="tip" varStatus="loop2">
											<c:if test="${tip.type==4}">
											<c:if test = "${loop2.index%3 == 0}">
				             			<tr>
				             				</c:if>
												 <td><input type="checkbox"  name="tipId" value="${tip.id }" <c:forEach items="${rtipList}" var="rt"><c:if test="${rt[0].name == tip.name}">checked</c:if></c:forEach>/>${tip.name}</td>
											</c:if>
											</c:forEach>
										</tr>
										<!-- end这是给任务分类了，暂时没有好的解决方法，所以每种类型单独循环一次输出 -->
										<!-- 这是给任务分类了，暂时没有好的解决方法，所以每种类型单独循环一次输出 -->
										<tr>
											<td colspan="3"><span style=" color: blue;">费用催缴相关任务:</span></td>
										</tr>
										<tr>
											<c:forEach items="${tipList }" var="tip" varStatus="loop3">
											<c:if test="${tip.type==5}">
											<c:if test = "${loop3.index%3 == 0}">
				             			<tr>
				             				</c:if>
												 <td><input type="checkbox"  name="tipId" value="${tip.id }" <c:forEach items="${rtipList}" var="rt"><c:if test="${rt[0].name == tip.name}">checked</c:if></c:forEach>/>${tip.name}</td>
											</c:if>
											</c:forEach>
										</tr>
										<!-- end这是给任务分类了，暂时没有好的解决方法，所以每种类型单独循环一次输出 -->
										<!--  
										<tr>
											<c:forEach items="${tipList }" var="tip" begin="6" end="7">
												<td><input type="checkbox" id="tipId" name="tipId" value="${tip.id }"/>${tip.name}</td>
											</c:forEach>
										</tr>
									   -->
										<tr height="2%">
											<td></td>
										</tr>
										<tr>
											<td colspan="3" align="center">
												<table align="center">
													<tr align="center">
														<td nowrap="nowrap" align="center"><input class="button" onclick="checkForm();" type="button" value="确定">
														    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button" value="取消" onclick="closeWnidow();"></td>
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