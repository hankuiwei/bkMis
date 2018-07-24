<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*,com.zc13.util.*,java.util.*" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.zc13.util.GlobalMethod"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List list = (List)request.getAttribute("outputDatilList");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>出库单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
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
}
.fistTd{
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
.form { 
	border-bottom: 1px solid #000000 ;
	border-right: 1px solid #000000;
	font-size: 14px;
	height: 25px;
	text-align:center;
}
</style>
<body>
<form name="" method="POST" >
   <%try{ %>
	<table width="600" align="center" cellpadding="0" cellspacing="0">
	  	<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	  	 			<tr class="noprint">
	         			<td>&nbsp;</td>    
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
				                    <th align="center"> <font style=" font-size:30px">出库单</font>&nbsp;</th>
				                 </tr>
				          	</table>
				        </td>
			        </tr>
			        <tr>
			        	<td height="20px"></td>
			        </tr>
			        
			 		<%
			        double allTotalMoney = 0.0f;
			        double totalMoney = 0.0f;
			        if(list!=null&&list.size()>0){ 
			        	int index = 0;
			        	String code = "";
			        %>
			        
			        <%
			        	for(index = 0;index<list.size();index++){
			        		Map map = (Map)list.get(index);
			        		if(!code.equals(map.get("inoutCode"))){
			        			
			        			if(index!=0){
			        			%>
			        			<!-- 
			        			<tr>
							        <td style="height:36px" class="fistTd">合计</td>						            
							        <td style="height:36px" class="rightTd" colspan="2"><font style=" font-size:20px">大写：<script>document.write(transform1(<%=totalMoney %>));</script></font></td>
							        <td style="height:36px" class="nomalTd" colspan="1"><font style=" font-size:20px">（￥：<%=totalMoney %>元）</font></td>
							    </tr>
							     -->
			        		</table>
	          			</td>
	          		</tr>
	          					<%
	          						totalMoney = 0.0f;
	          					} %>
	          		<tr>
	          			<td>&nbsp;</td>
	          		</tr>
	          		<tr>
			 			<td>
			 				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					  		  	<tr>
					  		  		<td width="30">&nbsp;</td>
					  		  		<td nowrap="nowrap" align="left" >出库单编号：<span class="style3"><%=map.get("inoutCode") %></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;领用单位：<span class="style3"><%=map.get("codeName") %></span></td>
					            	<td width="30">&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
		          				<tr>
		          	 				<td style="height:30px" class="zsTd">名称</td>
		          	 				<td style="height:30px" class="fisttrTd">规格</td>
		          	 				<td style="height:30px" class="fisttrTd">单位</td>
						            <td style="height:30px" class="fisttrTd">数量</td>
						            <!-- 
		          	 				<td style="width:16%;height:30px" class="fisttrTd">单价（元）</td>
						            <td style="width:16%;height:30px" class="fisttrTd">金额（元）</td>
						             -->
						            <td style="height:30px" class="fisttrTd">备注</td>
						        </tr>
						        <%
			        			code = (String)map.get("inoutCode");
			        		}
			        		allTotalMoney += (Double)map.get("money");
			        		totalMoney += (Double)map.get("money");
			        %>
						   	 	
									<tr>
										<td nowrap="nowrap" class="fistTd"><%=map.get("name") %>&nbsp;</td>
										<td nowrap="nowrap" class="form"><%=map.get("spec") %>&nbsp;</td>
										<td nowrap="nowrap" class="form"><%=map.get("unit") %>&nbsp;</td>
			               	 			<td nowrap="nowrap" class="form"><%=map.get("amount") %>&nbsp;</td>
			               	 			<!-- 
										<td nowrap="nowrap" class="form"><%=map.get("unitPrice") %>&nbsp;</td>
										<td nowrap="nowrap" class="form"><%=map.get("money") %>&nbsp;</td>
										 -->
			                			<td nowrap="nowrap" class="form">&nbsp;&nbsp;</td>
									</tr>						       
							        
	          		<%} }%>	 
	          					<!-- 
						        <tr>
							        <td style="height:36px" class="fistTd">合计</td>						            
							        <td style="height:36px" class="rightTd" colspan="2"><font style=" font-size:20px">大写：<script>document.write(transform1(<%=totalMoney %>));</script></font></td>
							        <td style="height:36px" class="nomalTd" colspan="1"><font style=" font-size:20px">（￥：<%=totalMoney %>元）</font></td>
							    </tr>
							     -->
	          				</table>
	          			</td>
	          		</tr>
	          		<tr>
			 			<td>
			 				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					  		  	<tr>
					  		  		<td style="height:5px"></td>
					  		  	</tr>
					  		  	<!-- 
					            <tr>
					  		  		<td style="width:50%;height:36px" colspan="2">总金额(大写)：<script>document.write(transform1(<%=allTotalMoney %>));</script>（￥：<%=allTotalMoney %>元）</td>
					  		  	</tr> 
					  		  	-->
					          	<tr>
					  		  		<td style="width:50%;height:36px">库管员：</td>
					  		  		<td style="width:50%;height:36px">领用人： </td>
					  		  	</tr>					          						         
					          	<tr>
					  		  		<td style="height:5px"></td>
					  		  	</tr>					         
					  		  	<tr>
					  		  		<td colspan="2"> <font color="red">				  		  		
					  		  		备注：本表一式三份领用部门、财务部、库房部各执一份 
					  		  		</font>	
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
