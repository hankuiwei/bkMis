<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>预收款管理</title>
    
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
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="165" nowrap="nowrap" class="form_line">预收款管理</td>
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
		  		<td align=""><table width="100%" cellpadding="0" cellspacing="0">
		  			<tr>
		  				<td class="txt" nowrap="nowrap">
					       <img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					    </td>
		  				<td align="left">客户名称:<input type="text">
		  				</td>
		  				<td>缴纳时间:<input type="text" readonly onclick="WdatePicker();" class="Wdate">至<input type="text" readonly onclick="WdatePicker();" class="Wdate">
		  				</td>
		  				<td align="right">
		  					<input type="button" value="查询" onclick="" class="button">
		  				</td>
		  			</tr>
		  		</table></td>		
		  	</tr>
		  	<tr>
			  	<td><table width="100%"  cellpadding="0" cellspacing="0" class="form_tab">
			    	<tr>
			    		<th class="RptTableHeadCellFullLock"  width="5%"><input type="checkbox" name="checkboxAll" value=""></th>
			    		<th class="RptTableHeadCellFullLock"  width="5%">序号</th>
			    		<th class="RptTableHeadCellFullLock" >客户名称</th>
			    		<th class="RptTableHeadCellFullLock"  width="15%">缴纳金额</th>
			    		<th class="RptTableHeadCellLock"  width="15%">归还金额</th>
			    		<th class="RptTableHeadCellLock"  width="15%">缴纳日期</th>
			    		<th class="RptTableHeadCellLock"  width="15%">备注</th>
			    	</tr>
					<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
						<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value=""></td>
						<td class="RptTableBodyCellLock"  align="center">1</td>
			    		<td class="RptTableBodyCell">&nbsp;海澜之家</td>
			    		<td class="RptTableBodyCell">&nbsp;10000</td>
			    		<td class="RptTableBodyCell">&nbsp;0</td>
			    		<td class="RptTableBodyCell">&nbsp;2010-11-01</td>
			    		<td class="RptTableBodyCell">&nbsp;</td>
			    	</tr>
					<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
						<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value=""></td>
						<td class="RptTableBodyCellLock"  align="center">2</td>
			    		<td class="RptTableBodyCell">&nbsp;逸夏</td>
			    		<td class="RptTableBodyCell">&nbsp;10000</td>
			    		<td class="RptTableBodyCell">&nbsp;0</td>
			    		<td class="RptTableBodyCell">&nbsp;2010-11-01</td>
			    		<td class="RptTableBodyCell">&nbsp;</td>
			    	</tr>
					<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
						<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value=""></td>
						<td class="RptTableBodyCellLock"  align="center">3</td>
			    		<td class="RptTableBodyCell">&nbsp;东临物业</td>
			    		<td class="RptTableBodyCell">&nbsp;10000</td>
			    		<td class="RptTableBodyCell">&nbsp;0</td>
			    		<td class="RptTableBodyCell">&nbsp;2010-11-01</td>
			    		<td class="RptTableBodyCell">&nbsp;</td>
			    	</tr>
					<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
						<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value=""></td>
						<td class="RptTableBodyCellLock"  align="center">4</td>
			    		<td class="RptTableBodyCell">&nbsp;寰宇</td>
			    		<td class="RptTableBodyCell">&nbsp;10000</td>
			    		<td class="RptTableBodyCell">&nbsp;0</td>
			    		<td class="RptTableBodyCell">&nbsp;2010-11-01</td>
			    		<td class="RptTableBodyCell">&nbsp;</td>
			    	</tr>
			    </table></td>
		    </tr>
		    <tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr><td class="form_line3">&nbsp;</td>
							<td class="form_line3">&nbsp;</td>
							<td class="form_line3">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right">
					<input type="button" class="button" value="导出EXECL">&nbsp;&nbsp;
					<input type="button" class="button" value="删除">&nbsp;&nbsp;
				</td>
			</tr>
			<tr><td height="15">&nbsp;</td></tr>
		    <tr>
		      <td><table width="100%"  cellpadding="0" cellspacing="0" class="">
		    	<input type="hidden" name="id" id="id">
		    	<tr>
					<td>预收款维护：</td>
				</tr>
		    	<tr>
	    			<td><table width="100%"  cellpadding="0" cellspacing="0"  class="Rpt1" >
	    				<tr>
	    				 	<td align="right" width="15%" class="head_form1">客户:</td>
	    				 	<td class="head_form1" ><input type="text" name="Person" readonly><input type="button" class="button" value="选择客户" onclick="openPerson()"></td>
	    				 	<td align="right" width="15%" class="head_form1">缴纳金额:</td>
	    				 	<td class="head_form1"><input type="text" name="itemName"></td>
	    				 	<td align="right" width="15%" class="head_form1">缴纳日期:</td>
	    				 	<td class="head_form1"><input type="text" name="itemName" readonly onclick="WdatePicker();" class="Wdate"></td>
	    				</tr>
	    				<tr>
	    				 	<td class="head_form1" align="right" width="15%">备注:</td>
	    				 	<td class="head_form1" align="" colspan="5"><textarea rows="3" cols=""></textarea></td>
	    				</tr>
	    			</table></td>
	    		</tr>
	    		<tr>
					<td  align="right" ><input type="button" class="button" value="保存" onclick="save()"><input type="reset" value="取消" class="button"></td>
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
  	function openPerson(){
  		var URL = "<%=path%>/standard.do?method=getMTreePerson";
  		var return_value = showModalDialog(URL,"","dialogWidth=400px;dialogHeight=300px;");
  		if(typeof(return_value)!="undefined"){
  			document.getElementsByName("Person")[0].value=return_value;
  		}
  	}
  	function returnDeposit(){
  		var URL = "<%=path%>/zc13/bkmis/costManage/return_deposit.jsp";
  		var return_value = showModalDialog(URL,"","dialogWidth=400px;dialogHeight=150px;");
  	}
  	function save(){
  		if(confirm("是否打印收据？")){
  			
  			var URL = encodeURI(encodeURI("<%=path%>/zc13/bkmis/costManage/c_receipt.jsp?title=收据&flag=ysk"));
  			var return_value = showModalDialog(URL,"","dialogWidth=800px;dialogHeight=300px;");
  		}else{
  			
  		}
  	}
  </script>
</html>
