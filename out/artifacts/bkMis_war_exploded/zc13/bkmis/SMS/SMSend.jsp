<%@ page language="java" import="java.util.*,com.zc13.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%@ page import="org.apache.struts.taglib.html.Constants" %> 
<%@ page import="org.apache.struts.Globals" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加员工信息</title>
    
    <link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <!-- 使用日期控件时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<!-- 使用单元格在线编辑功能时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/jquery.form.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/validate.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
    <style type="text/css">
	.initiated_event_photo img{width:400px; height:320px; margin-left:78px; display:none;}
	#newPreview{float:left; display:none;width:90%; height:auto; text-align:left; margin:9px 0 0 110px; font-size:14px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);}
	</style>
	<!--  -->
	<script type="text/javascript">
		function changeCus(item) {
			if(item.checked) {
				$("#cusId").attr("disabled", true);
				$("#cusName").attr("disabled", true);
				$("#cusPhone").attr("disabled", true);
			} else {
				$("#cusId").attr("disabled", false);
				$("#cusName").attr("disabled", false);
				$("#cusPhone").attr("disabled", false);
			}
		}
		
		function selectCus() {
			var nowCusId = $("#cusId").val();
			var url = "<%=path %>/customer.do?method=getClientList&forward=sendMessage&ids="+nowCusId;
			var cusInfo = window.showModalDialog(url, null, "dialogWidth:800px;dialogHeight:500px;status:no;");
			if(null!=cusInfo) {
				$("#cusId").val(cusInfo.cusId);
				$("#cusName").text(cusInfo.cusName);
				$("#cusPhone").val(cusInfo.cusPhone);
			}
		}
		
		function sendMessage() {
			var isSend = smsForm.selectAll.checked;
			if(!isSend && $("#cusId").val()=='') {
				alert("请选择客户");
				return;
			}
			if($.trim($("textarea[name='message']").text())=='') {
				alert("请输入短讯内容");
				return;
			}
			smsForm.action = "<%=path %>/sms.do?method=sendSMS";
			smsForm.submit();
		}
	</script>
 </head>
  
  <body>
    	<form name="smsForm" action="<%=path %>/sms.do" method="post">
    		<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
    		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
    			<tr>
    				<td height="5"></td>
  				</tr>
  				<tr>
    			<td>
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
      					<tr>
        					<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
							<td width="165" nowrap="nowrap" class="form_line">群发短讯</td>
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
    						<td colspan="4" align="left" class="head_one"><font size="+1">基本信息</font></td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							全部客户：
    						</td>
    						<td class="head_form1">
    							<input type="checkbox" name="selectAll" checked onclick="changeCus(this)" />
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">
    							选择客户：
    						</td>
    						<td class="head_form1">&nbsp;
    							<textarea rows="5" readonly cols="70" id="cusName" name="cusName" onclick="selectCus()" disabled></textarea>
    							<input type="hidden" id="cusId" name="ids" disabled />
    							<input type="hidden" id="cusPhone" name="cusPhone" disabled />
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_left">短讯内容：<font color="red">*</font></td>
    						<td class="head_form1">&nbsp;
    							<textarea rows="10" cols="70" name="message"></textarea>
    						</td>
    					</tr>
    				</table>
    			  </td>
    			</tr>
    			<tr>
    				<td colspan="4" align="center">
    					<input type="button" name="button" value="发送" class="button" onclick="javascript:sendMessage();"/>
    					<input type="reset" name="button" value="重置" class="button" />
    				</td>
    			</tr>
    		</table>
    	</form>
  </body>
</html>
