<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>查看任务单详单</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript">
	function printAndNotice(){
		var id = '${compactTask.id}';
		form1.action="<%=path%>/workTask.do?method=lookTaskInfo&forward=printWorkTask&compactId="+id;
		form1.submit();
	
	}
	function cc(){
		window.close();
	}
	</script>
</head>
<body>
<form name="form1" method="post">
	<table width="59%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">客户入驻通知单</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2">&nbsp;</td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		
  		<tr height = "55%"><!-- 这个td负责画正式内容的左、右、下方的边框 -->
    		<td class="menu_tab2" align="center">
     			<table width="100%"  height = "100%" border="0" cellspacing="0" cellpadding="0">
	 				<tr>
						<td align="center">&nbsp;
							
		 			 	</td>
					</tr>
  					<tr height="95%">
					    <td valign = "top">
					    	<!-- 表单内容区域 -->
							<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
					  		  	<tr>
					  		  		<td align="center"><font style=" font-size: 20; font-weight:bold ">客户入驻通知单&nbsp;</font></td>
					          	</tr>
					  		  	<tr>
					            	<td nowrap="nowrap" align="right" >通知单编号：<span class="style3"></span>TZD${compactTask.code }</td>
					          	</tr>
				         	</table>
				       </td>
				    </tr>
	          		<tr>
			 			<td >
			 				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form_tab" >
		          				<tr>
		          	 				<td style="width:25%;height:28px; text-align: center" class="head_one">客户：</td>
						            <td style="width:75%;height:28px"  >
			 							<table width="100%" border="0" cellpadding="0" cellspacing="0"  >
						            		<tr>
		          	 							<td style="width:25%;height:28px; text-align: right" class="fist_rows" >公司名称：</td>
									            <td colspan="3"  style="width:25%;height:28px;text-align: center" class="fist_rows">${compactTask.clientName }&nbsp;</td>
						            		</tr>
						            		<tr>
		          	 							<td style="width:25%;height:28px; text-align: right" class="head_form1">房号：</td>
									            <td style="width:25%;height:28px;text-align: left" class="head_form1">${compactTask.rooms }&nbsp;</td>
									            <td style="width:25%;height:28px; text-align: right" class="head_form1">合同号：</td>
									            <td style="width:25%;height:28px;text-align: left" class="head_form1">${compactTask.compactCode }&nbsp;</td>
						            		</tr>
						            		<tr>
		          	 							<td style="width:25%;height:28px; text-align: right" class="head_form1">联系人：</td>
									            <td style="width:25%;height:28px;text-align: left" class="head_form1">${compactTask.linkMan }&nbsp;</td>
									            <td style="width:25%;height:28px; text-align: right" class="head_form1">联系电话：</td>
									            <td style="width:25%;height:28px;text-align: left" class="head_form1">${compactTask.linkPhone }&nbsp;</td>
						            		</tr>
						            		<tr>
		          	 							<td style="width:25%;height:28px; text-align: right" class="head_form1">入驻日期：</td>
									            <td style="width:25%;height:28px;text-align: left" class="head_form1">${compactTask.inDate }&nbsp;</td>
									            <td style="width:25%;height:28px; text-align: right" class="head_form1">迁出日期：</td>
									            <td style="width:25%;height:28px;text-align: left" class="head_form1">${compactTask.goDate }&nbsp;</td>
						            		</tr>
						            		<!--	<tr>
						            		
									            <td colspan="4"  style="width:25%;height:28px; text-align: left" class="head_form1">
													开通电话__部、 网络__M、 交接钥匙__把。
												</td> 
						            		</tr> -->
						            	</table>
						            </td>
						        </tr>
						        <tr>
		          	 				<td style="width:25%;height:28px;text-align: center"  class="head_one">招商部：</td>
						            <td style="width:25%;height:28px;text-align: left"  class="head_form1">
						        		<table width="100%" border="0" cellpadding="0" cellspacing="0" >
						            		<tr style="width:25%;height:158px">
									            <td >${compactTask.zhaos }&nbsp;</td>
						            		</tr>
			          	 					<tr>
							            		<td><div style=" text-align: center">部门主管签字：</div></td>
			          	 					</tr>
			          	 				</table>
						            </td>
						        </tr>
						        <!-- 
						        <tr>
		          	 				<td style="width:25%;height:28px;text-align: center" class="head_one">财务部：</td>
						            <td style="width:25%;height:28px;text-align: left"  class="head_form1">
						        		<table width="100%" border="0" cellpadding="0" cellspacing="0" >
						            		<tr style="width:25%;height:158px">
									            <td >${compactTask.caiwu }&nbsp;</td>
						            		</tr>
			          	 					<tr>
							            		<td><div style=" text-align: center">部门主管签字：</div></td>
			          	 					</tr>
			          	 				</table>
						            </td>
						        </tr>
						         -->
						        <tr>
		          	 				<td style="width:25%;height:28px;text-align: center" class="head_one">客服部：</td>
						            <td style="width:25%;height:28px;text-align: left"  class="head_form1">
						        		<table width="100%" border="0" cellpadding="0" cellspacing="0" >
						            		<tr style="width:25%;height:158px">
									            <td >${compactTask.wuye }&nbsp;</td>
						            		</tr>
			          	 					<tr>
							            		<td><div style=" text-align: center">部门主管签字：</div></td>
			          	 					</tr>
			          	 				</table>
						            </td>
						        </tr>
						        <tr>
		          	 				<td style="width:25%;height:28px;text-align: center" class="head_one">工程部：</td>
						            <td style="width:25%;height:28px;text-align: left"  class="head_form1">
						        		<table width="100%" border="0" cellpadding="0" cellspacing="0" >
						            		<tr style="width:25%;height:158px">
									            <td >${compactTask.gongc }&nbsp;</td>
						            		</tr>
			          	 					<tr>
							            		<td><div style=" text-align: center">部门主管签字：</div></td>
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
		<tr>
			<td align="center">
				<table>
					<tr>
						<TD width="81" align=center><input type="button"  onclick="printAndNotice()" class="button"  value="打印"></TD>
	                   	<TD width="81" align=center><input type="button" onclick="cc()" class="button" value="返回"></TD>
        			</tr>
        		</table>
        	</td>
       	</tr>
	</table>
</form>
</body>

</html>
