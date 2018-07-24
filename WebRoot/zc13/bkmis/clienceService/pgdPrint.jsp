<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% 
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="robots" content="noindex, nofollow" />
<title>派工单</title>
<script type="text/javascript" src="<%=path %>/resources/js/transform.js"></script>
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
	text-align:center;
}
.fisttrTd{
	border-top :1px solid #000000;
	border-right: 1px solid #000000;
	border-bottom :1px solid #000000;
	text-align:center;
}.fistTd{
	border-left :1px solid #000000;
	border-right: 1px solid #000000;
	border-bottom :1px solid #000000;
	text-align:center;
}
.nomalTd{
	border-right: 1px solid #000000;
	border-bottom :1px solid #000000;
	text-align:center;
}
.rightTd{
	border-bottom :1px solid #000000;
	text-align:center;
}
.botTd{
	border-left: 1px solid #000000;
	border-bottom :1px solid #000000;
	text-align:center;
}
</style>
<body>
<form name="" method="POST" >
   <%try{ %>
	<table width="700" align="center" cellpadding="0" cellspacing="0">
	  	<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	  	 			<tr class="noprint">
	         			<td>&nbsp;</td>    
	                   <!--	<TD width="81" align=right><input type="button" onclick="javascript:document.all.WebBrowser.ExecWB(7,1);"  value="打印"></TD>  -->
	                   	<TD width="81" align=right><input type="button" onclick="javascript:window.print()"  value="打印"></TD>
	                   	<TD width="81" align=right><input type="button" onclick="window.close();"  value="返回"></TD>
	    			</tr>
	    		</table>
	    	</td>
	    </tr>
	    <tr>
	    	<td height="10px"></td>
	 	</tr>
	    <tr>
			<td>
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
				    <tr>
			          	<td>
			          		<table  width="100%" align="center" border=0>
				                <tr>
				                    <th align="center"> <font style=" font-size:30px">维修服务申请单</font>&nbsp;</th>
				                 </tr>
				          	</table>
				        </td>
			        </tr>
			        <tr>
			        	<td height="20px"></td>
			        </tr>
			 		<tr>
			 			<td>
			 				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					  		  	<tr>
					  		  		<td width="30">&nbsp;</td>
					  		  		<td nowrap="nowrap" align="left" >班组：<span class="style3"></span></td>
					  		  		<td nowrap="nowrap" align="center" >维修时间: &nbsp;&nbsp;年 &nbsp;&nbsp;月 &nbsp;&nbsp;日 &nbsp;&nbsp;时 &nbsp;&nbsp;分<span class="style3"></span></td>
					            	<td nowrap="nowrap" align="right" >编号：<span class="style3"></span></td>
					            	<td width="30">&nbsp;${bean.sendcardCode }</td>
					          	</tr>
				         	</table>
				       </td>
				    </tr>
				     <tr>
			        	<td height="5px"></td>
			        </tr>
	          		<tr>
			 			<td>
			 				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
			 					<c:if test="${bean.type eq '业主报修'}">
		          				<tr>
		          	 				<td style="width:12%;height:30px" class="zsTd">客户名称</td>
						            <td style="width:30%;height:30px" class="fisttrTd" colspan="2">&nbsp;${clientName }</td>
						            <td style="width:15%;height:30px" class="fisttrTd">房号</td>
						            <td style="width:33%;height:30px" class="fisttrTd" colspan="2">&nbsp;
						            <c:choose>
							            <c:when test="${empty list4}">
										</c:when>
										<c:otherwise>
								            <c:forEach items="${list4}" var="v">
								            	<c:if test="${bean.roomId == v.roomId}">
								            	${v.roomCode }
								            	</c:if>
								            </c:forEach>
								        </c:otherwise>
									</c:choose>
						            </td>
						        </tr>
			 					</c:if>
			 					<c:if test="${bean.type ne '业主报修'}">
			 						<tr>
		          	 				<td style="height:56px" class="zsTd">报修类型</td>
						            <td style="height:56px" class="fisttrTd" colspan="2">&nbsp;${bean.project }</td>
						            <td style="height:56px" class="fisttrTd">区域</td>
						            <td style="height:56px" class="fisttrTd" colspan="2">&nbsp;${bean.area }</td>
						        </tr>
			 					</c:if>
						        <tr>
		          	 				<td style="height:56px" class="fistTd">报修内容</td>
						            <td style="height:56px" class="nomalTd" colspan="2">&nbsp;${bean.contentExplain }</td>
						            <td style="height:56px" class="nomalTd">维修内容</td>
						            <td style="height:56px" class="nomalTd" colspan="2">&nbsp;${bean.maintainContent }</td>
						        </tr>
						        <tr>
		          	 				<td style="width:12%;height:56px" class="fistTd">派工时间</td>
						            <td style="width:20%;height:56px" class="nomalTd" colspan="1">&nbsp;${bean.sendTime }</td>
						            <td style="width:12%;height:56px" class="nomalTd">到场时间</td>
						            <td style="width:20%;height:56px" class="nomalTd" colspan="1">&nbsp;</td>
						            <td style="width:15%;height:56px" class="nomalTd">完工时间</td>
						            <td style="width:20%;height:56px" class="nomalTd" colspan="1">&nbsp;</td>
						        </tr>
						        <tr>
		          	 				<td style="height:56px" class="fistTd">完成情况：</td>
						            <td style="height:56px" class="nomalTd" colspan="2">
						            	<input type="checkbox" />完成
						            	<input type="checkbox" />未完成
						            </td>
						            <td style="height:56px" class="nomalTd">未完成后续跟进：</td>
						            <td style="height:56px" class="nomalTd" colspan="2">&nbsp;</td>
						        </tr>
						        <tr>
		          	 				<td style="height:28px" class="fistTd">维修材料</td>
						            <td style="height:28px" class="nomalTd">规格</td>
						            <td style="height:28px" class="nomalTd">数量</td>
						            <td style="height:28px" class="nomalTd">单价（元）</td>
						            <td style="height:28px" class="nomalTd">计价（元）</td>
						            <td style="height:28px" class="nomalTd">备注</td>
						        </tr>
						        
						        <c:choose>
									<c:when test="${empty list6}">
									<tr>
							            <td style="height:28px"  class="fistTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							        </tr>
							        <tr>
							            <td style="height:28px"  class="fistTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							        </tr>
							        <tr>
							            <td style="height:28px"  class="fistTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							        </tr>
							        <tr>
							            <td style="height:28px"  class="fistTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							        </tr>
							        <tr>
							            <td style="height:28px"  class="fistTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							        </tr>
							        <tr>
							            <td style="height:28px"  class="fistTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							            <td style="height:28px"  class="nomalTd">&nbsp;</td>
							        </tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${list6}" var="v" varStatus="tag">
										<tr>
											<td nowrap="nowrap" class="fistTd" align="center">${v.materialName}&nbsp;</td>
											<td nowrap="nowrap" class="nomalTd" align="center">${v.spec}&nbsp;</td>
									        <td nowrap="nowrap" class="nomalTd" align="center">${v.amount}&nbsp;</td>
									        <td nowrap="nowrap" class="nomalTd" align="center">
									        <script>
									        document.write(parseFloat(${v.amountMoney/v.amount}).toFixed(2).toString());
									        </script>
									        &nbsp;
									        </td>
								        	<td nowrap="nowrap" class="nomalTd" align="center">${v.amountMoney}&nbsp;</td>
								        	<td nowrap="nowrap" class="nomalTd" align="center">&nbsp;</td>
										</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
						        
						        <tr>
						            <td style="width:15%;height:36px" class="botTd"><font style=" font-size:20px"> 服务费:</td>
						            <td style="width:15%;height:36px" class="nomalTd" colspan="2"><font style=" font-size:20px">
						            <script type="text/javascript">
										var amountRate;
										try{amountRate = parseFloat(${bean.amountRate }).toFixed(2).toString();}catch(e){}
										if(amountRate == 0.00){
											document.write("0");
										}else{
											document.write(amountRate);
										}
										
									</script>
						            (元）
						            </td>
						            <td style="height:36px" class="rightTd"><font style=" font-size:20px"> 合计大写 :</td>
						            <td style="height:36px;font-size:20px;text-align:left" class="nomalTd" colspan="3">
							        <script>document.write(transform1(${bean.amountRate+bean.amountMoney}));</script>(￥:${bean.amountRate+bean.amountMoney})
						            </td>
						        </tr>
						        <tr>
		          	 				<td style="height:56px" class="fistTd" style="text-align:left;" colspan="6">
		          	 				&nbsp;业主意见：
		          	 				<input type="checkbox" />满意
		          	 				<input type="checkbox" />一般
		          	 				<input type="checkbox" />不满意
		          	 				其他反馈意见：
		          	 				</td>
						        </tr>
	          				</table>
	          			</td>
	          		</tr>
	          		<tr>
			 			<td>
			 				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					  		  	<tr>
					  		  		<td style="height:10px"></td>
					  		  	</tr>
					          
					          	<tr>
					  		  		<td style="width:50%;height:36px">用户签字：</td>
					  		  		<td style="width:50%;height:36px">维修人员签字： </td>
					  		  	</tr>					          						         
					          	<tr>
					  		  		<td style="height:10px"></td>
					  		  	</tr>					         
					  		  	<tr>
					  		  		<td colspan="2">					  		  		
					  		  		备注：本表一式三份客户、财务部、物业部各执一份
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
<%}catch(Exception e){
	e.printStackTrace();
} %>
</body>
</html>

