<%@ page language="java" pageEncoding="UTF-8"%>
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
		<script type="text/javascript"
			src="<%=path%>/zc13/msmis/functionList/scripts/jquery.js"></script>

		<base href="<%=basePath%>">

		<title>系统参数维护</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript">
	var flag = true;
	function del(){
		document.form1.action="sysParamManager.do?method=delCode";
		document.form1.submit();
	  	window.parent.location.reload();
	}
	function add(){
		codeName = document.getElementById("codeName1").value;
		codeValue = document.getElementById("codeValue1").value;
		codeType = document.getElementById("codeValue").value;
		if(codeName == null||codeName==""){
			alert("数据名称不能为空！");
			return false;
		}
		if(codeValue == null||codeValue==""){
			alert("数据代码不能为空！");
			return false;
		}
			$.post("<%=path%>/sysParamManager.do",{method:"checkCodeNameTwo",codeName:codeName,codeType:codeType},function(data){
				if(data=="true"){
					alert("数据名称已经存在");
					flag = false;
					return flag;
				}else{
					$.post("<%=path%>/sysParamManager.do",{method:"checkValue",value:codeValue},function(data){
						if(data=="true"){
							alert("数据代码已经存在");
							flag = false;
							return flag;
						}else{
							document.form1.action="sysParamManager.do?method=insertCode";
							document.form1.submit();
							window.parent.location.href="sysParamManager.do?method=getSysParam&id="+document.getElementById("id").value;
						}
					})
				}
			})
			
	}
	function save(){
		document.getElementById("tb").style.display="block";
	}
	</script>


</head>
<body>
	<form action="sysParamManager.do?method=updateCodeType" method="post" name="form1">
			<input type="hidden" name="id" value="${id }" />
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr><td height="5"></td></tr>
				<c:if test="${empty map}">
					<script type="text/javascript">
			           document.form1.action="sysParamManager.do?method=goAdd";
			           document.form1.submit();
		            </script>
				</c:if>
				<tr>
					<td align="center">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">系统参数信息</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="menu_tab2">
						<table align="left" width="99%" height="99%">
							<tr height="99%">
								<td>
									<table width="70%" height="99%">
										<tr>
											<td class="head_form1" width="40%" height="80%" align="right" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类别名称:</td>
											<td class="head_form1" width="99%"  height="80%" align="left" >&nbsp;&nbsp;&nbsp;${map.codeType.codeTypeName }
												   <input name="codeName" id="codeName" type="hidden" value="${map.codeType.codeTypeName }">
								                   <input name="codeId" id="codeId" type="hidden" value="${map.codeType.codeTypeId }">
								                   <input name="oldName" id="oldName" type="hidden" value="${map.codeType.codeTypeName }">
								                   <input name="codeType" id="codeType" type="hidden" value="${map.codeType.codeTypeValue }">
												                  
											</td>
										</tr>
										<tr>
											<td class="head_form1" width="40%"  height="80%" align="right" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类别代码:</td>
											<td class="head_form1" width="99%"  height="80%" align="left">&nbsp;&nbsp;&nbsp;${map.codeType.codeTypeValue }
													<input name="codeValue" id="codeValue" type="hidden" value="${map.codeType.codeTypeValue }" >
												    <input name="oldValue" id="oldValue" type="hidden" value="${map.codeType.codeTypeValue }">
											</td>
										</tr>
										<tr height="99%">
											<td colspan="2" align="right" class="head_form1" width="50%" height="99%">
												<input type="button" onclick="save()" value="添加子菜单" class="button">
										    </td>                                        
										</tr>
									</table>
								</td>
							</tr>
							<tr height="40%"><td></td></tr>
							<tr id="tb" style="display: none;">
								<td>
									<table width="70%" height="99%">
										<tr>
											<td class="head_form1" width="40%" height="80%" align="right" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类别名称:</td>
											<td class="head_form1" width="99%"  height="80%" align="left">&nbsp;&nbsp;&nbsp;<input name="codeName1" id="codeName1" value=""></td>
										</tr>
										<tr>
											<td class="head_form1" width="40%" height="80%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据代码:</td>
											<td class="head_form1" width="99%"  height="80%" align="left">&nbsp;&nbsp;&nbsp;<input name="codeValue1" id="codeValue1" value=""></td>
										</tr>
										<tr height="99%">
											<td colspan="2" align="right" class="head_form1" width="50%" height="99%"><input type="button" onclick="add()" value="保存" class="button">
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
