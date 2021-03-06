<%@ page language="java" import="java.util.*,java.text.*,com.zc13.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String today = dateFormat.format(new Date());
String id = request.getParameter("id");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>考勤时间</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
	<script type="text/javascript">
  		function setTime(){
  			var x = Validator.Validate(document.getElementById('form1'),2);
			if(!x){
				return;
			}
			var time = document.getElementById("time").value;
			window.returnValue = time;
			window.close();
  		}
  	</script>

  </head>
  
  <body>
  	<form name = "form1" method="post">
		<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
		  	<tr>
		    	<td>
		    		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      			<tr>
		        			<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
							<td width="165" nowrap="nowrap" class="form_line">考勤时间</td>
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
											<table align="center" border="0" cellpadding="3" cellspacing="0" class="form_tab">
												<tr>
													<td class="head_rols" align="right">
														时间：
													</td>
													<td class="fist_rows">
														<input type="text" name="time"  style=" width: 138px" id="time" value="<%=today %>" dataType="Require" msg="请输入时间！" readonly  onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate">
													</td>
												</tr>
												<tr>
													<td align="center" colspan="4" class="head_left">
														<input type="button" value="确定" class="button" onclick="setTime()" />
														<input type="button" onclick="javascript:window.close();" class="button" value="取消">
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
</html>
