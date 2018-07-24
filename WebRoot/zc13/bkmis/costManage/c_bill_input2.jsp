<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>费用录入</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/util.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
	<c:if test="${success}">
		<script type="text/javascript">
			alert("成功！");
		</script>
	</c:if>
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
  	<form action="" method="post" id="formEdit">
  	<input type="hidden" id="Person" name="Person" value="${Person }" >
    <table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="165" nowrap="nowrap" class="form_line">费用录入</td>
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
		      <td><table width="100%"  cellpadding="0" cellspacing="0" class="">
		    	<tr>
	    			<td><table width="100%"  cellpadding="0" cellspacing="0"  class="Rpt1" >
	    				<tr>
	    				 	<td align="right" width="15%" class="head_form1" nowrap="nowrap">客户：</td>
	    				 	<td class="head_form1" colspan="1">
	    				 		<input type="text" id="Person2" value="${Person }"  disabled><input type="button" class="button" value="选择客户" onclick="openPerson('${lpId }')">
	    				 		<input type="hidden" name="ids" dataType="Require" msg="客户不得为空！" value="${ids }" />
	    				 	</td>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>申请单号：</td>
	    				 	<td class="head_form1"><input type="text" name="bill.billCode" dataType="Require" msg="申请单号不能为空！" value="${applyCode }" readonly="readonly"><font color="red">*</font></td>
	    				 	<td align="right" width="15%" class="head_form1" nowrap>收费项：</td>
	    				 	<td class="head_form1">
	    				 		<select name="itemId" >
				  					<c:forEach items="${itemList}" var="v">
				  						<c:if test="${v.itemName ne '装修押金'}">
										<option value="${v.id }" <c:if test="${v.id==itemId }">selected</c:if>>${v.itemName }</option>		  						
				  						</c:if>
				  					</c:forEach>
				  				</select>
	    				 	</td>
	    				</tr>
	    				<tr>
	    					<td class="head_form1" align="right" width="15%" nowrap>应收金额：</td>
	    				 	<td class="head_form1"><input type="text" name="bill.billsExpenses" dataType="NonNegativeDouble" msg="应付金额必须是正数且不为空！"><font color="red">*</font></td>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>账单日期：</td>
	    				 	<td class="head_form1" align=""><input type="text" name="bill.createDate" readonly onclick="WdatePicker();" class="Wdate" value="${today }"  dataType="Require" msg="账单日期不得为空！"><font color="red">*</font></td>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>账单月份：</td>
	    				 	<td class="head_form1"><input type="text" name="bill.billDate" value="${fn:substring(today,0,7) }" dataType="Require" msg="账单月份不得为空！"><font color="red">*</font></td>
	    				</tr>
	    				<tr><!--
	    				 	<td class="head_form1" align="right" width="15%" nowrap>收款日期：</td>
	    				 	<td class="head_form1" align=""><input type="text" name="bill.collectionDate" readonly onclick="WdatePicker();" class="Wdate" value="${today }"></td>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>开始日期：</td>
	    				 	<td class="head_form1"><input type="text" name="bill.startDate" readonly onclick="WdatePicker();" class="Wdate" value=""></td>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>终止日期：</td>
	    				 	<td class="head_form1" align=""><input type="text" name="bill.endDate" readonly onclick="WdatePicker();" class="Wdate"></td>-->
	    				 	<td class="head_form1" align="right" width="15%" nowrap>最晚缴款日期：</td>
	    				 	<td class="head_form1" colspan="5"><input type="text" name="bill.closeDate" readonly onclick="WdatePicker();" class="Wdate"></td>
	    				 </tr>
	    				<tr>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>备注：</td>
	    				 	<td class="head_form1" colspan="5">
	    				 		<textarea rows="3" style="width: 60%;" name="bill.notes"></textarea>
	    				 	</td>
	    				</tr>
	    			</table></td>
	    		</tr>
	    		<tr>
					<td align="right">
						<input type="button" class="button" value="添加" onclick="add()">
						<input type="reset"  class="button" value="取消">
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
	function openPerson(){
  		var ids = document.getElementsByName("ids")[0].value;
  		// var URL = "<%=path%>/choice.do?method=getClientList&ids="+ids;
  		var URL = "<%=path%>/zc13/bkmis/costManage/public.jsp?URL=bill.do?method=queryClient";
  		var return_value = showModalDialog(URL,"","dialogWidth=800px;dialogHeight=470px;");
  		if(typeof(return_value)!="undefined"){
  			document.getElementById("Person").value=return_value.name;
  			document.getElementById("Person2").value=return_value.name;
  			//document.getElementsByName("Person")[0].value=return_value.name;
  			document.getElementsByName("ids")[0].value=return_value.id;
  		}
  	}
  	function add(){
  		var x = Validator.Validate(document.getElementById('formEdit'),2);
  		if(x){
  			document.forms[0].action = "<%=path%>/choice.do?method=saveAccount2";
  			document.forms[0].submit();
  		}
  	}
  </script>
</html>
