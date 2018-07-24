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
		<title>添加房间设施</title>
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
			
		   function checkForm(){
		       var eqname = document.getElementById("eqname").value;
		       var eqtype = document.getElementById("eqtype").value;
		       var status = document.getElementById("status").value;
		       var url = "<%=path%>/room.do";
		       if(trim(eqname) == ""){
		          alert("请输入要添加物品的名称！");
		          return false;
		       }else if(trim(eqtype) == ""){
		         alert("请输入要添加物品的类型！");
		         return false;
		       }else if(status == ""){
		         alert("使用状态不能为空！");
		         return false;
		       }else{
		         $.post(url,{method:"checkLpName",eqname:eqname,forward:"addEquipment"},function(data){
		            if(data == 'true'){
		                if(confirm("确定要增加吗？")){
		                   //关闭窗口
		                   document.addEquipmentForm.submit();
		                   window.returnValue=true;
						   window.close();
		                }
		            }else{
		                alert("该楼盘已经存在！");
		                return false;
		            }
		         });
		       }
		   }
		    function closeModify(){
             window.close();
		  }
        </script>
	</head>
	<body>
		<form method="post" name="addEquipmentForm" action="<%=path%>/room.do?method=addEquipment">
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">新增房间设施</td>
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
											<td height="10" colspan="9"></td>
										</tr>
										<tr>
											<td height="10" colspan="9"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left">
										<tr>
											<td align="right" class="head_form1" width="12%">物品名称：<font color="red">*</font></td>
											<td align="left"  class="head_form1" width="33%">&nbsp;&nbsp;<input type="text" id="eqname" name="eqname"></td>
											<td align="right" class="head_form1" width="23%">物品类型：<font color="red">*</font></td>
											<td align="left" class="head_form1">&nbsp;&nbsp;<input type="text" id="eqtype" name="eqtype"></td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">启用日期：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;&nbsp;<input type="text" id="startDate" name="startDate" readonly onclick="WdatePicker();" class="Wdate"></td>
											<td align="right" class="head_form1" width="23%">报废日期：</td>
											<td align="left" class="head_form1">&nbsp;&nbsp;<input type="text" id="endDate" name="endDate" readonly onclick="WdatePicker();" class="Wdate"></td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="23%">使用状态：<font color="red">*</font></td>
											<td align="left" class="head_form1" width="33%" colspan="3">&nbsp;
												<select name="status" style="width: 130px;">
													<option value="">- - - 请选择- - -</option>
													<option value="0">不可用</option>
													<option value="1" selected="selected">可用</option>
												</select>
											</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">备 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
											<td align="left"  class="head_form1" width="33%" colspan="3">&nbsp;&nbsp;<textarea id="bz" name="bz"></textarea></td>
										</tr>
										<tr height="20">
											<td></td>
										</tr>
										<tr>
											<td align="right">
												<table>
													<tr>
														<td nowrap="nowrap" align="right"><input class="button" onclick="checkForm();" type="button" value="确定"></td>
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