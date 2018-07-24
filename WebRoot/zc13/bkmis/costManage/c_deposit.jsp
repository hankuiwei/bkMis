<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>押金管理</title>
    
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
  	<form name="formEdit" id="formEdit" method="post">
    <table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="165" nowrap="nowrap" class="form_line">押金管理</td>
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
		  				<td align="left">客户名称：<input type="text" name="clientName" value="${CBillForm.clientName }">
		  				</td>
		  				<td>缴纳时间：<input type="text" readonly onclick="WdatePicker();" class="Wdate" name="begin" value="${CBillForm.begin }">
		  					至<input type="text" readonly onclick="WdatePicker();" class="Wdate" name="end" value="${CBillForm.end }">
		  				</td>
		  				<!--<td align="left">押金状态：<select name="depositType">
		  					<option value="">请选择...</option>
		  					<option value="0" <c:if test="${CBillForm.depositType=='0' }">selected</c:if> >未缴</option>
		  					<option value="1" <c:if test="${CBillForm.depositType=='1' }">selected</c:if> >已缴</option>
		  				</td>
		  				--><td align="right">
		  					<input type="button" value="查询" onclick="cx()" class="button">
		  				</td>
		  			</tr>
		  		</table></td>		
		  	</tr>
		  	<tr height="78%">
			    <td valign = "top">
			    	<!-- 表单内容区域 -->
					<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height = "95%">
			        		<td width="100%">
						  		<div id = "div1" class = "RptDiv"  >
		  			<table width="100%"  cellpadding="0" cellspacing="0" class="">
			    	<tr>
			    		<th class="RptTableHeadCellFullLock"  width="5%">选择</th>
			    		<th class="RptTableHeadCellFullLock"  width="5%">序号</th>
			    		<th class="RptTableHeadCellFullLock" >客户名称</th>
			    		<th class="RptTableHeadCellFullLock"  width="15%">缴纳金额</th>
			    		<th class="RptTableHeadCellLock"  width="15%">归还金额</th>
			    		<th class="RptTableHeadCellLock"  width="15%">缴纳日期</th>
			    		<th class="RptTableHeadCellLock"  width="15%">返还日期</th>
			    		<th class="RptTableHeadCellLock"  width="15%">备注</th>
			    	</tr>
			    	<c:choose>
			    		<c:when test="${empty list}">
			    			<tr align="center">
								<td colspan="12" align="center" class="head_form1">
									<font color="red">没有信息!</font>
								</td>
							</tr>
			    		</c:when>
			    		<c:otherwise>
			    			<c:forEach items="${list}" var="v" varStatus="vs">
			    				<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
									<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value="${v[0].id }" val="${v[0].amount-v[0].amountReturn }" onclick="selectOne(this);"></td>
									<td class="RptTableBodyCellLock"  align="center">${vs.count }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v[1].name }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v[0].amount }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v[0].amountReturn }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v[0].date }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v[0].returnDate }</td>
						    		<td class="RptTableBodyCell">&nbsp;</td>
						    	</tr>
			    			</c:forEach>
			    		</c:otherwise>
			    	</c:choose>
			    </table>
		    </div>
							</td>
		    			</tr>
		    		</table>
		    	</td>
		    </tr>
		    <tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr><td class="form_line3">&nbsp;</td>
							<td class="form_line3">&nbsp;${pageHTML }</td>
							<td class="form_line3">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right">
					<input type="button" class="button" value="返还押金" onclick="returnDeposit()">&nbsp;&nbsp;
				</td>
			</tr>
	    </table></td>
	    </tr>
	</table>
	</form>
  </body>
  <script type="text/javascript">
  	function returnDeposit(){
  		var checkbox = document.getElementsByName("checkbox");
  		var id="";
  		var deposit="";
  		for(var i=0;i<checkbox.length;i++){
  			if(checkbox[i].checked){
  				id=checkbox[i].value;
  				deposit = checkbox[i].val;
  			}
  		}
  		if(id == null||id.length==0){
  			alert("请选择要返还的客户！");
  		}else if(Number(deposit)<=0){
  			alert("押金已归还！");
  		}else{
  			var URL = "<%=path%>/zc13/bkmis/costManage/return_deposit.jsp?deposit="+deposit;
  			var return_value = showModalDialog(URL,"","dialogWidth=400px;dialogHeight=150px;");
  			if(typeof(return_value)!="undefined"){
  				document.forms[0].action="<%=path%>/bill.do?method=returnDeposit&depositId="+id+"&amount="+return_value;
  				document.forms[0].submit();
  			}
  		}
  	}
  	function cx(){
  		document.forms[0].action="<%=path%>/bill.do?method=getDeposit";
  		document.forms[0].submit();
  	}
  	function selectOne(This){
  		var checkbox = document.getElementsByName("checkbox");
  		if(This.checked){
  			for(var i=0;i<checkbox.length;i++){
  				if(This!=checkbox[i]){
  					checkbox[i].checked=false;
  				}
  			}
  		}
  	}
  </script>
</html>
