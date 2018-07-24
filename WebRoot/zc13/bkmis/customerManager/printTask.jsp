<%@ page contentType="text/html; charset=UTF-8" language="java"  %>
<%@page import="com.zc13.util.GlobalMethod"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="robots" content="noindex, nofollow" />
<title>客户入驻通知单</title>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
<script language="javascript">
	//document.all.WebBrowser.ExecWB(7,1);
	//window.close();
	//使界面最大化
 maxWin();
function maxWin()
{
      var aw = screen.availWidth;
      var ah = screen.availHeight;
      window.moveTo(0, 0);
      window.resizeTo(aw, ah);
}
	
	function printAndNotice(){
			window.print();
	}
	function cc(){
		window.close();
	}
</script>
<style type="text/css">
.STYLE1 {font-size: 12px}
.style3 {text-decoration: underline;}
</style>
<OBJECT   id=WebBrowser   classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2   height=0   width=0></OBJECT>   
<style>
@media print {
.noprint {display:none}
}
.STYLE2 {font-size: 12}

.zsTd{
	border: 1px solid #000000;
	text-align:right;
}
.fisttrTd{
	border-top :1px solid #000000;
	border-right: 1px solid #000000;
	border-bottom :1px solid #000000;
	text-align:right;
}.fistTd{
	border-left :1px solid #000000;
	border-right: 1px solid #000000;
	border-bottom :1px solid #000000;
	text-align:right;
}
.nomalTd{
	border-right: 1px solid #000000;
	border-bottom :1px solid #000000;
	text-align:right;
}
.rightTd{
	border-bottom :1px solid #000000;
}
.botTd{
	border-right :1px solid #000000;
}
</style>
<body>
<form name="form1" method="POST" >
   <%try{ %>
	<table width="600" align="center" cellpadding="0" cellspacing="0">
	  	
	    <tr>
			<td>
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
				    <tr>
			          	<td>
			          		<table  width="100%" align="center" border=0>
				                <tr>
				                    <td align="center"><font style=" font-size: 20px"></font><br /><font style=" font-size: 30px	">客户入驻通知单</font>&nbsp;</td>
				                 </tr>
				          	</table>
				        </td>
			        </tr>
			        <tr>
			        	<td height="30px"></td>
			        </tr>
			 		<tr>
			 			<td>
			 				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					  		  	<tr>
					  		  		<td width="30">&nbsp;</td>
					            	<td nowrap="nowrap" align="right" >通知单编号：<span class="style3"></span></td>
					            	<td width="10%" nowrap="nowrap" >${compactTask.code }</td>
					          	</tr>
				         	</table>
				       </td>
				    </tr>
	          		<tr>
			 			<td>
			 				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
		          				<tr>
		          	 				<td style="width:25%;height:28px" class="zsTd">客户：</td>
						            <td style="width:75%;height:28px" >
			 							<table width="100%" border="0" cellpadding="0" cellspacing="0" >
						            		<tr>
		          	 							<td style="width:25%;height:28px;" class="fisttrTd" >公司名称：</td>
									            <td colspan="3"  style="width:25%;height:28px;text-align: center" class="fisttrTd">${compactTask.clientName }&nbsp;</td>
						            		</tr>
						            		<tr>
		          	 							<td style="width:25%;height:28px" class="nomalTd">房号：</td>
									            <td style="width:25%;height:28px;text-align: left" class="nomalTd">${compactTask.rooms }&nbsp;</td>
									            <td style="width:25%;height:28px" class="nomalTd">合同号：</td>
									            <td style="width:25%;height:28px;text-align: left" class="nomalTd">${compactTask.compactCode }&nbsp;</td>
						            		</tr>
						            		<tr>
		          	 							<td style="width:25%;height:28px" class="nomalTd">联系人：</td>
									            <td style="width:25%;height:28px;text-align: left" class="nomalTd">${compactTask.linkMan }&nbsp;</td>
									            <td style="width:25%;height:28px" class="nomalTd">联系电话：</td>
									            <td style="width:25%;height:28px;text-align: left" class="nomalTd">${compactTask.linkPhone }&nbsp;</td>
						            		</tr>
						            		<tr>
		          	 							<td style="width:25%;height:28px" class="nomalTd">入驻日期：</td>
									            <td style="width:25%;height:28px;text-align: left" class="nomalTd">${compactTask.inDate }&nbsp;</td>
									            <td style="width:25%;height:28px" class="nomalTd">迁出日期：</td>
									            <td style="width:25%;height:28px;text-align: left" class="nomalTd">${compactTask.goDate }&nbsp;</td>
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
						        	<td style="width:25%;height:188px" class="fistTd">招商部：</td>
						        	<td style="width:25%;height:28px;text-align: left"  class="nomalTd">
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
		          	 				<td style="width:25%;height:188px" class="fistTd">财务部：</td>
		          	 				<td style="width:25%;height:28px;text-align: left"  class="nomalTd">
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
		          	 				<td style="width:25%;height:188px" class="fistTd">客服部：</td>
						            <td style="width:25%;height:28px;text-align: left"  class="nomalTd">
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
		          	 				<td style="width:25%;height:188px" class="fistTd">工程部：</td>
						            <td style="width:25%;height:28px;text-align: left"  class="nomalTd">
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
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	  	 			<tr class="noprint">
	         			<td>&nbsp;</td>
	                   	<TD width="81" align=center><input type="button"  onclick="printAndNotice()"  value="打印"></TD>
	                   	<TD width="81" align=center><input type="button" onclick="cc()"  value="关闭"></TD>
	    			</tr>
	    		</table>
	    	</td>
	    </tr>
	</table>
</form>
<%}catch(Exception e){
	e.printStackTrace();
} %>

</body>
</html>

