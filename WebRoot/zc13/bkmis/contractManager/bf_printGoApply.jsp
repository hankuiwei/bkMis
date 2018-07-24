<%@ page contentType="text/html; charset=UTF-8" language="java"  %>
<%@page import="com.zc13.util.GlobalMethod"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="robots" content="noindex, nofollow" />
<title>迁出审核单</title>
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
		var id = '${compactRoomForm.compact.id}';
		$.post("<%=path%>/customer.do",{method:"notice",compactId:id},function(data){
			window.print();
			//document.all.WebBrowser.ExecWB(7,1);
		})
		
		
	}
		function return1(){
			this.close();
		}
		function save(){
			//document.all.WebBrowser.ExecWB(7,1);
			window.print();
			document.form1.action = "<%=path%>/compact.do?method=saveQuit";
			document.form1.submit();
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
	<input type = "hidden" value="${compact.name }" name="clientName">
	<input type = "hidden" value="${compact.code }" name="compactCode">
	<input type = "hidden" value="${compact.phone }" name="phone">
	<input type = "hidden" value="${compact.roomCodes }" name="roomCodes">
	<input type = "hidden" value="${compact.totalArea }" name="area">
	<input type = "hidden" value="${compact.beginDate }" name="beginDate">
	<input type = "hidden" value="${compact.endDate }" name="endDate">
	<input type = "hidden" value="${code }" name="quitCode">
	<input type="hidden" name="compactId" id="compactId" value="${compact.id }">
   <%try{ %>
	<table width="600" align="center" cellpadding="0" cellspacing="0">
	  	
	    <tr>
			<td>
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
				    <tr>
			          	<td>
			          		<table  width="100%" align="center" border=0>
				                <tr>
				                    <td align="center"><font style=" font-size: 20px"></font><br /><font style=" font-size: 30px	">迁出申请单</font>&nbsp;</td>
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
					            	<td nowrap="nowrap" align="left" >申请日期：<span class="style3"></span></td>
					            	<td nowrap="nowrap" ><%=GlobalMethod.getTime() %></td>
					            	<td width="60%"></td>
					            	<td nowrap="nowrap" align="right" >编号：<span class="style3"></span></td>
					            	<td nowrap="nowrap" >${code }</td>
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
									            <td colspan="3"  style="width:25%;height:28px;text-align: center" class="fisttrTd">${compact.name }&nbsp;</td>
						            		</tr>
						            		<tr>
		          	 							<td style="width:25%;height:28px" class="nomalTd">房号：</td>
									            <td style="width:25%;height:28px;text-align: left" class="nomalTd">${compact.roomCodes }&nbsp;</td>
									            <td style="width:25%;height:28px" class="nomalTd">合同号：</td>
									            <td style="width:25%;height:28px;text-align: left" class="nomalTd">${compact.code }&nbsp;</td>
						            		</tr>
						            		<tr>
		          	 							<td style="width:25%;height:28px" class="nomalTd">联系人：</td>
									            <td style="width:25%;height:28px;text-align: left" class="nomalTd">${compact.linkMan }&nbsp;</td>
									            <td style="width:25%;height:28px" class="nomalTd">联系电话：</td>
									            <td style="width:25%;height:28px;text-align: left" class="nomalTd">${compact.phone }&nbsp;</td>
						            		</tr>
						            		<tr>
		          	 							<td style="width:25%;height:28px" class="nomalTd">入驻日期：</td>
									            <td style="width:25%;height:28px;text-align: left" class="nomalTd">${compact.beginDate }&nbsp;</td>
									            <td style="width:25%;height:28px" class="nomalTd">迁出日期：</td>
									            <td style="width:25%;height:28px;text-align: left" class="nomalTd">${compact.endDate }&nbsp;</td>
						            		</tr>
						            		<!-- 
						            		<tr>
									            <td colspan="4"  style="width:25%;height:28px; text-align: left" class="nomalTd">
													开通电话__部、 网络__M、 交接钥匙__把。
												</td>
						            		</tr>
						            		 -->
						            	</table>
						            </td>
						        </tr>
						        <tr>
		          	 				<td style="width:25%;height:28px" class="fistTd">招商部：</td>
						            <td style="width:25%;height:28px;text-align: left"  class="nomalTd">
						            	<table style="width: 100%">
						            		<tr>
						            			<td colspan="2">退租原因：</td>
						            		</tr>
						            		<tr>
						            			<td colspan="2">
						            				<textarea rows="4"  style="width: 90%"  name="quitSeason" style=" overflow: hidden"></textarea>
						            			</td>
						            		</tr>
						            		<tr>
						            			<td colspan="2">违约金情况：</td>
						            		</tr>
						            		<tr>
						            			<td colspan="2">
						            				<textarea rows="4"  name="zsbAttitude"  style="width: 90%" style=" overflow: hidden"></textarea>
						            			</td>
						            		</tr>
						            		<tr>
						            			<td>
						            				<div style=" text-align: left">招商部负责人：</div>
						            			</td>
						            			<td>
						            				<div style=" text-align: left">客户签字：</div>
						            			</td>
						            		</tr>
						            	</table>
						            </td>
						        </tr>
						        <tr>
		          	 				<td style="width:25%;height:28px" class="fistTd">物业部：</td>
						            <td style="width:25%;height:28px;text-align: left"  class="nomalTd">
						            	<table style="width: 100%">
						            		<tr>
						            			<td colspan="2">对房间的检查情况：</td>
						            		</tr>
						            		<tr>
						            			<td colspan="2">
						            				<textarea rows="8" style="width: 90%" id="wybAttitude" name="wybAttitude" style=" overflow: hidden"></textarea>
						            			</td>
						            		</tr>
						            		<tr>
						            			<td>
						            				<div style=" text-align: left">物业部负责人：</div>
						            			</td>
						            			<td>
						            				<div style=" text-align: left">客户签字：</div>
						            			</td>
						            		</tr>
						            	</table>
						            </td>
						        </tr>
						        <tr>
		          	 				<td style="width:25%;height:28px" class="fistTd">财务部：</td>
						            <td style="width:25%;height:28px;text-align: left"  class="nomalTd">
						            	<table style="width: 100%">
						            		<tr>
						            			<td colspan="2">收费退费情况：</td>
						            		</tr>
						            		<tr>
						            			<td colspan="2">
						            				<textarea rows="8" style="width: 90%" name="cwbAttitude" id="zjlAttitude" style=" overflow: hidden"></textarea>
						            			</td>
						            		</tr>
						            		<tr>
						            			<td>
						            				<div style=" text-align: left">财务部负责人签字：</div>
						            			</td>
						            			<td>
						            				<div style=" text-align: left">财务部盖章处</div>
						            			</td>
						            		</tr>
						            	</table>
						            </td>
						        </tr>
						        
						        <tr>
		          	 				<td style="width:25%;height:28px" class="fistTd">总经理批示：</td>
						            <td style="width:25%;height:28px;text-align: left"  class="nomalTd">
						            	<table style="width: 100%">
						            		<tr>
						            			<td colspan="2">
						            				<textarea rows="5" style="width: 90%" name="zjlAttitude" id="zjlAttitude" style=" overflow: hidden"></textarea>
						            			</td>
						            		</tr>
						            		<tr>
						            			<td>
						            				<div style=" text-align: center">总经理签字：</div>
						            			</td>
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
				<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	  	 			<tr class="noprint">
	         			
	                   	<td  align=center>
		                   	<input type="button" onclick="save()"  value="提交申请并打印">
		                   	<input type="button" onclick="return1()"  value="返回">
	                   	</td>
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
<c:if test="${flag}">
	<script type="text/javascript">
		opener.location.href("compact.do?method=getDelContractList");
		this.close();
	</script>
</c:if>
</html>

