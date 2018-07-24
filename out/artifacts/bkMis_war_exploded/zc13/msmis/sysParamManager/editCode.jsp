<%@ page language="java"  pageEncoding="UTF-8"%>
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
		<script type="text/javascript" src="<%=path%>/zc13/msmis/functionList/scripts/jquery.js"></script>
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<base href="<%=basePath%>">
		<title>系统参数维护</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
	function check(){ 
		var codeType = document.getElementById("codeType").value;
		var codeName = document.getElementById("codeName").value;
		var oldName = document.getElementById("oldName").value;
		if(codeName == null || codeName == ""){
			alert("请输入数据名称！");
			return false;
		}
		var codeValue = document.getElementById("codeValue").value;
		var oldValue = document.getElementById("oldValue").value;
		if(codeValue == null || codeValue == ""){
			alert("请输入数据代码！");
			return false;
		}
		if(oldName == codeName&&oldValue == codeValue){
			alert("没有可修改数据！");
			return false;
		}
		if(oldName != codeName){
			$.post("<%=path%>/sysParamManager.do",{method:"checkCodeNameTwo",codeName:codeName,codeType:codeType},function(data){
				if(data=="true"){
					alert("数据名称已经存在");
					flag = false;
					return flag;
				}else{ 
					if(oldValue != codeValue){
						$.post("<%=path%>/sysParamManager.do",{method:"checkValue",value:codeValue},function(data){
							if(data=="true"){
								alert("数据代码已经存在");
								flag = false;
								return flag;
							}else{
								document.form1.submit();
								//window.parent.location.href="sysParamManager.do?method=getSysParam";
								//window.parent.location.href="sysParamManager.do?method=goAdd&table=sys_code&id=${id}";
							}
						})
					}else{
						document.form1.submit();
						//window.parent.location.href="sysParamManager.do?method=getSysParam";
						//window.parent.location.href="sysParamManager.do?method=goAdd&table=sys_code&id=${id}";
					}
				}
			})
		}else{
			if(oldValue != codeValue){
				$.post("<%=path%>/sysParamManager.do",{method:"checkValue",value:codeValue},function(data){
					if(data == "true"){
						alert("数据代码已经存在");
						flag = false;
						return flag;
					}else{
						document.form1.submit();
						//window.parent.location.reload();
					}
				})
			}
		}
	}
	
	
	function del(){
		document.form1.action="sysParamManager.do?method=delCode";
		document.getElementById("table").value="sys_code_type";
		document.getElementById("id").value=document.getElementById("parentId").value;
		document.form1.submit();
	  	//window.parent.location.reload();
	}
	</script>

	</head>
	<body onunload="javascript:window.parent.butFrame.location.reload();">
		<form action="sysParamManager.do?method=updateCode" method="post" name="form1">
			<!-- 修改完成后返回到修改页面需要的参数 -->
			<input type="hidden" id="table" name="table" value="${table }" />
			<input type="hidden" id="id" name="id" value="${id }" />
			<input type="hidden" id="parentId" name="parentId" value="${parentId }" />
			
			
			<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr><td height="5"></td></tr>
				<tr>
					<td align="center">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
						<table align="left" width="70%" height="99%">
							<tr height="99%">
								<td class="head_form1" width="40%" height="80%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据名称:</td>
								<td class="head_form1" width="99%" height="80%" align="left">&nbsp;&nbsp;&nbsp;<input name="codeName" id="codeName" value="${map.code.codeName }">
									                                                                           <input name="codeId" id="codeId" type="hidden" value="${map.code.codeId }"><span><font color="red">&nbsp;*</font></span>
									                                                                           <input name="oldName" id="oldName" type="hidden" value="${map.code.codeName }">
									                                                                           <input name="codeType" id="codeType" type="hidden" value="${map.code.codeType }">
								</td>
							</tr>
							<tr>
								<td class="head_form1" width="40%" height="80%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据代码:</td>
								<td class="head_form1" width="99%" height="80%" align="left">&nbsp;&nbsp;&nbsp;<input name="codeValue" id="codeValue" value="${map.code.codeValue }">
									                                                                           <input name="oldValue" id="oldValue" type="hidden" value="${map.code.codeValue }"><span><font color="red">&nbsp;*</font></span>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="right" class="head_form1" width="50%" height="99%">
									<input type="button" onclick="check()" class="button" value="修改">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" onclick="del()" class="button" value="删除">
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
