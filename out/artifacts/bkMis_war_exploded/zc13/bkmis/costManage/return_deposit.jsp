<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String deposit = request.getParameter("deposit");
request.setAttribute("deposit",deposit);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>返还押金</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
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
  	<form name="formEdit" id="formEdit" method="post">
    <table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="165" nowrap="nowrap" class="form_line">返还押金</td>
					<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
					<td width="1080" class="form_line2"></td>
					<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
       			</tr>
   			</table>
   		</td>
 	  </tr>
	  <tr>
	  	<td><table width="100%" cellpadding="0" cellspacing="0" class="menu_tab2">
		  	<tr>
			  	<td><table width="100%"  cellpadding="0" cellspacing="0" class="form_tab">
			    	<tr>
			    		<td align="center">请输入归还金额:</td>
			    	</tr>
			    	<tr>
			    		<td align="center"><input type="text" name="deposit" value="${deposit }" maxValue="${deposit }" dataType="Double2" msg="金额必须是数字;"></td>
			    	</tr>
			    	<tr>
						<td align="center">
							<input type="button" class="button" value="确定" onclick="save()">&nbsp;&nbsp;
						</td>
					</tr>
			    </table></td>
		    </tr>
	    </table></td>
	    </tr>
	</table>
	</form>
  </body>
  <script type="text/javascript">
  	function save(){
  		var x = Validator.Validate(document.getElementById('formEdit'),2);
  		if(x){
  			var deposit = document.getElementsByName("deposit")[0].value;
  			result = deposit;
  			window.returnValue  = result;
  			window.close();
  		}
  	}
  </script>
</html>
