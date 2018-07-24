<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.zc13.util.*;"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String yfje = request.getParameter("yfje");
String sfje = request.getParameter("sfje");
String fbh = request.getParameter("fbh");
String sqye = request.getParameter("sqye");
String bqye = request.getParameter("bqye");
String ye = request.getParameter("ye");
String flag = request.getParameter("flag");
String title = java.net.URLDecoder.decode(GlobalMethod.NullToParam(request.getParameter("title"),""),"utf-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>收据</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<style type="text/css">
	<!--
	.Rpt1{
		width: 100%;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 10pt;
		border-top:4px #266898 double;
		border-left:1px #b2c2c9 solid;
	}
	-->
	</style>
  </head>
  
  <body>
    <table width="98%" cellpadding="0" cellspacing="0" align="center">
	  <tr>
	  	<td><table width="100%" cellpadding="0" cellspacing="0" class="menu_tab2">
		    <tr>
		      <td><table width="100%"  cellpadding="0" cellspacing="0" class="">
		    	<input type="hidden" name="id" id="id">
		    	<tr>
		    		<td><table width="100%" cellpadding="0" cellspacing="0"><tr>
					<td width="10%">&nbsp;</td>
					<td align="center"><H1><%=title %></H1></td>
					<td width="10%">No:<input type="text"  /></td>
					</tr></table></td>
				</tr>
				<tr>
					<td align="center">${today }</td>
				</tr>
				<%if("skxz".equals(flag)){//收款销账 %>
		    	<tr>
	    			<td><table width="100%"  cellpadding="0" cellspacing="0"  class="" style="border: 1px solid #000000;">
	    				<tr height="35">
	    				 	<td align="right" width="15%" class="">缴款人:</td>
	    				 	<td class=""  width="15%">海澜之家</td>
	    				 	<td align="right" width="15%" class="">本次应付</td>
	    				 	<td class=""  width="15%">&nbsp;<%=yfje %></td>
	    				 	<td align="right" width="15%" class="">本次实付</td>
	    				 	<td class=""  width="15%">&nbsp;<%=sfje %></td>
	    				</tr>
	    				<tr height="35">
	    				 	<td class="" align="right" width="">发票号:</td>
	    				 	<td class="" align="">&nbsp;</td>
	    				 	<td class="" align="right" width="">上期余额:</td>
	    				 	<td class="">&nbsp;<%=sqye %></td>
	    				 	<td class="" align="right" width="">本期余额</td>
	    				 	<td class="">&nbsp;<%=bqye %></td>
	    				</tr>
	    				<tr height="35">
	    				 	<td class="" align="right" width="">余额转入:</td>
	    				 	<td class="" colspan="">
	    				 		&nbsp;<%if("1".equals(ye)){out.print("暂存款");}else if("2".equals(ye)){out.print("预收款");} %>
	    				 	</td>
	    				 	<td class="" align="right" width="">经手人:</td>
	    				 	<td class="" align="">${userInfo.name}</td>
	    				</tr>
	    			</table></td>
	    		</tr>
	    		<%}if("khtk".equals(flag)){ %>
	    		<tr>
	    			<td><table width="100%"  cellpadding="0" cellspacing="0"  class="" style="border: 1px solid #000000;">
	    				<tr height="35">
	    				 	<td align="right" width="15%" class="">客户：</td>
	    				 	<td class=""  width="15%">海澜之家</td>
	    				 	<td align="right" width="15%" class="">退款金额：</td>
	    				 	<td class=""  width="15%">&nbsp;<%=yfje %></td>
	    				 	<td align="right" width="15%" class="">退款日期：</td>
	    				 	<td class=""  width="15%">&nbsp;<%=sfje %></td>
	    				</tr>
	    				<tr height="35">
	    				 	<td class="" align="right" width="">经办人：</td>
	    				 	<td class="" align="" colspan="5">${userInfo.name}</td>
	    				</tr>
	    				<tr height="35">
	    				 	<td class="" align="right" width="">说明：</td>
	    				 	<td class="" align="" colspan="5">XXXX</td>
	    				</tr>
	    			</table></td>
	    		</tr>
	    		<%}if("yj".equals(flag)){ %>
	    		<tr>
	    			<td><table width="100%"  cellpadding="0" cellspacing="0"  class="" style="border: 1px solid #000000;">
	    				<tr height="35">
	    				 	<td align="right" width="15%" class="">客户：</td>
	    				 	<td class=""  width="15%">海澜之家</td>
	    				 	<td align="right" width="15%" class="">缴纳金额：</td>
	    				 	<td class=""  width="15%">&nbsp;1000.00</td>
	    				 	<td align="right" width="15%" class="">缴纳日期：</td>
	    				 	<td class=""  width="15%">&nbsp;2010-12-05</td>
	    				</tr>
	    				<tr height="35">
	    				 	<td class="" align="right" width="">经办人：</td>
	    				 	<td class="" align="" colspan="5">${userInfo.name}</td>
	    				</tr>
	    				<tr height="35">
	    				 	<td class="" align="right" width="">备注：</td>
	    				 	<td class="" align="" colspan="5">XXXX</td>
	    				</tr>
	    			</table></td>
	    		</tr>
	    		<%}if("ysk".equals(flag)){ %>
	    		<tr>
	    			<td><table width="100%"  cellpadding="0" cellspacing="0"  class="" style="border: 1px solid #000000;">
	    				<tr height="35">
	    				 	<td align="right" width="15%" class="">客户：</td>
	    				 	<td class=""  width="15%">海澜之家</td>
	    				 	<td align="right" width="15%" class="">缴纳金额：</td>
	    				 	<td class=""  width="15%">&nbsp;1500.00</td>
	    				 	<td align="right" width="15%" class="">缴纳日期：</td>
	    				 	<td class=""  width="15%">&nbsp;2010-12-03</td>
	    				</tr>
	    				<tr height="35">
	    				 	<td class="" align="right" width="">经办人：</td>
	    				 	<td class="" align="" colspan="5">${userInfo.name}</td>
	    				</tr>
	    				<tr height="35">
	    				 	<td class="" align="right" width="">备注：</td>
	    				 	<td class="" align="" colspan="5">XXXX</td>
	    				</tr>
	    			</table></td>
	    		</tr>
	    		<%} %>
	    		<tr>
					<td  align="right" ><input type="button" class="button" value="打印" onclick="save()"><input type="button" value="取消" class="button" onclick="window.close()"></td>
				</tr>
	    	  </table></td>
	    	</tr>
	    </table></td>
	    </tr>
	</table>
  </body>
  <script type="text/javascript">
  	function checkAll(){
  		var obj = document.getElementsByName("checkbox");
  		for(var i=0;i<obj.length;i++){
  			obj[i].checked=true;
  		}
  	}
  	function compute(je,index){
  		var obj = document.getElementsByName("checkbox");
  		var val = document.getElementsByName("yfje")[0];
  		if(obj[index].checked){
  			document.getElementById("je"+index).innerHTML=je;
  			val.value =Number(val.value)+Number(je);
  		}else{
  			document.getElementById("je"+index).innerHTML="0";
  			val.value = Number(val.value)-Number(je);
  		}
  	}
  	function drawback(){
  		parent.document.URL="<%=path%>/meterInput.do?method=getMTree4";
  		// parent.document.forms[0].submit();
  	}
  	function save(){
  		window.close();
  	}
  </script>
</html>
