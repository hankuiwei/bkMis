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
		<title>新增计费</title>
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
		   function upload(){
		       window.showModalDialog("zc13/bkmis/lpManage/upload.jsp",window,"dialogWidth=300px;dialogHeight=260px;resizable=yes;center=1");
		       window.dialogArguments.location.reload();
		       window.close();
		   }
		   
		    function closeModify(){
             window.close();
		  }
        </script>
	</head>
	<body>
		<form method="post" name="addLpForm" action="<%=path%>/lp.do?method=addLp">
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">新增计费</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
							</tr>
						</table>
					</td>
				</tr>
			    <tr>
					<td class="menu_tab2" align="center">
						
						<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left">
							<tr>
								<td nowrap = "nowrap" align="right" class="head_form1" width="22%">日期：</td>
								<td nowrap = "nowrap" align="left"  class="head_form1" width="33%">&nbsp;<input type="text" name="starttime" id="starttime" readonly onclick="WdatePicker()" value="2010-12-06" class="Wdate"/><font color="red">*</font></td>
								<td nowrap = "nowrap" align="right" class="head_form1" width="23%">通话开始时间：</td>
								<td nowrap = "nowrap" align="left" class="head_form1">&nbsp;<input type="text" id="lpName" name="lpName"></td>
							</tr>
							<tr>
								<td nowrap = "nowrap" align="right" class="head_form1" width="22%">通话结束时间：</td>
								<td nowrap = "nowrap" align="left"  class="head_form1" width="33%">&nbsp;<input type="text" id="regional" name="regional"></td>
								<td nowrap = "nowrap" align="right" class="head_form1" width="23%">物业类型：</td>
								<td nowrap = "nowrap" align="left" class="head_form1">&nbsp;<input type="text" id="ppType" name="ppType"></td>
							<tr>
							</tr>
							<tr>
								<td nowrap = "nowrap" align="right" class="head_form1" width="22%">通话时长：</td>
								<td nowrap = "nowrap" align="left"  class="head_form1" width="33%">&nbsp;<input type="text" id="developCompany" name="developCompany"></td>
								<td nowrap = "nowrap" align="right" class="head_form1" width="23%">通话类型：</td>
								<td nowrap = "nowrap" align="left" class="head_form1">&nbsp;<input type="text" id="designCompany" name="designCompany"></td>
							</tr>
							<tr>
								<td nowrap = "nowrap" align="right" class="head_form1" width="22%">对方号码：</td>
								<td nowrap = "nowrap" align="left"  class="head_form1" width="33%">&nbsp;<input type="text" id="buildCompany" name="buildCompany"></td>
								<td nowrap = "nowrap" align="right" class="head_form1" width="23%">长途类型：</td>
								<td nowrap = "nowrap" align="left" class="head_form1">&nbsp;<input type="text" id="constructionArea" name="constructionArea"></td>
							</tr>
							<tr height="20"><td nowrap = "nowrap"></td></tr>
							<tr height="20">
								<td nowrap = "nowrap"></td>
							</tr>
							<tr>
								<td nowrap = "nowrap" align="right" align = "center" colspan = "4">
									<table align = "center">
										<tr align = "center">
											<td nowrap = "nowrap"><input class="button" onclick="checkForm();" type="button" value="确定"></td>
											<td nowrap = "nowrap"><input type="button" class="button" value="取消" onclick="closeModify();"></td>
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