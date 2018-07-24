<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加表具</title>
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
			
		   function checkForm(){
		       var type = document.getElementById("type").value;//得到参数的codeValue
		       var code = document.getElementById("code").value;
		       var url = "<%=path%>/lp.do";
		       if(trim(type) == ""){
		          alert("表具名称不能为空！");
		          return false;
		       }else if(trim(code) == ""){
		           alert("表具编号不能为空!");
		           return false;
		       }else{
		         $.post(url,{method:"checkMeter",code:code,type:type,forward:"addMeter"},function(data){
		            if(data == 'true'){
		                if(confirm("确定要增加吗？")){
		                   //获取表具名称
		                   $.post(url,{method:"getMeterName",type:type},function(data){
		                   //关闭窗口
		                         document.addMeterForm.submit();
		                         //用于返回表具信息，在楼盘添加页面
		                         //拼接用于回传的字符串
		                         var str = data + ";" + code + ";" + type;
		                        //回传该字符串给父页面
		                        returnValue = str;
		                        window.close();
		                        return true;
		                    });
		                 }
		             }else{
		                alert("该表已经存在！");
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
		<form method="post" name="addMeterForm" action="<%=path%>/lp.do?method=addMeter">
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">新增表具</td>
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
											<td align="right" class="head_form1" width="12%">表具名称：<font color="red">*</font></td>
											<td align="left"  class="head_form1" width="33%">&nbsp;
											    <select name="type" style="width: 130px;">
													<option value="" selected="selected">- - -请选择- - -</option>
													<c:forEach items="${metertypelist}" var="m">
														<option value="${m.codeValue}">${m.codeName}</option>
													</c:forEach>
												</select>
											</td>
											<td align="right" class="head_form1" width="23%">表具编号：<font color="red">*</font></td>
											<td align="left" class="head_form1">&nbsp;&nbsp;<input type="text" id="code" name="code"></td>
										</tr>
										<tr height="20">
											<td></td>
										</tr>
										<tr>
											<td align="right">
												<table align="right">
													<tr align="right">
														<td nowrap="nowrap" align="right"><input class="button" onclick="checkForm();"  id = "focuson" type="button" value="确定"></td>
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