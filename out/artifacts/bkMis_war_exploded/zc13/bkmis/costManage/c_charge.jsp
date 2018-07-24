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
    
    <title>逆收费操作</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/util.js" defer="defer"></script>
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
  <c:if test="${!empty save}">
  	<script type="text/javascript">
		alert('${save}');
		window.returnValue="1";
		window.close();
	</script>
  </c:if>
  <body>
    <!-- 加载页面div -->
	<jsp:include page="/loading.jsp"></jsp:include>
	<!-- 加载页面div -->
  	<form name="formEdit" method="post" id="formEdit">
    <table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="100" nowrap="nowrap" class="form_line">回退</td>
					<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
					<td width="1080" class="form_line2"></td>
					<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
       			</tr>
   			</table>
   		</td>
 	  </tr>
	  <tr>
	  	<td><table width="100%" cellpadding="0" cellspacing="0" class="menu_tab2">
		  	<tr height="200">
			    <td valign="top">
			    	<!-- 表单内容区域 -->
					<table  width="100%" height="100%" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height="95%">
			        		<td width="100%">
						  		<div id="div1" class="RptDiv"><table width="100%" cellpadding="0" cellspacing="0" class="RptTable">
			    	<tr>
			    		<th class="RptTableHeadCellLock"  width="5%"><input type="checkbox" onclick="checkAll(this,'checkbox')"></th>
			    		<th class="RptTableHeadCellLock"  width="5%">序号</th>
			    		<th class="RptTableHeadCellLock"  width="10%">单据类型</th>
			    		<th class="RptTableHeadCellLock"  width="10%">单据号</th>
			    		<th class="RptTableHeadCellLock"  width="10%">总金额</th>
			    		<th class="RptTableHeadCellLock"  width="10%">收款日期</th>
			    		<th class="RptTableHeadCellLock"  width="10%">合同金额</th>
			    		<th class="RptTableHeadCellLock"  width="10%">暂存款</th>
			    		<th class="RptTableHeadCellLock"  width="10%">预收房租</th>
			    		<th class="RptTableHeadCellLock"  width="10%">押金</th>
			    		<th class="RptTableHeadCellLock"  width="10%">定金</th>
			    		<th class="RptTableHeadCellLock"  width="10%">总金额</th>
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
						    		<td class="RptTableBodyCell"  align="center"><input type="checkbox" name="checkbox" value="${v[0].id }"></td>
						    		<td class="RptTableBodyCell" align="center">${vs.count }</td>
						    		<td class="RptTableBodyCell">&nbsp;<c:if test="${v[0].billType=='0' }" >收据</c:if><c:if test="${v[0].billType=='1' }" >发票</c:if> </td>
						    		<td class="RptTableBodyCell">&nbsp;${v[0].billNum }</td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].amount }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;${v[0].date }</td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].billAmount }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].temporalAmount }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].advanceAmount }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].depositAmount }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].earnestAmount }).toFixed(2).toString()))</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(parseFloat(${v[0].amount }).toFixed(2).toString()))</script></td>
						    		<input type="hidden" name="id" value="${v[0].id }">
						    		<input type="hidden" name="chargeList[${vs.index }].id" value="${v[0].id }">
						    		<input type="hidden" name="chargeList[${vs.index }].billId" value="${v[0].billId }">
						    		<input type="hidden" name="chargeList[${vs.index }].clientId" value="${v[0].clientId }">
						    		<input type="hidden" name="chargeList[${vs.index }].temporalAmount" value="${v[0].temporalAmount }">
						    		<input type="hidden" name="chargeList[${vs.index }].advanceAmount" value="${v[0].advanceAmount }">
						    		<input type="hidden" name="chargeList[${vs.index }].depositAmount" value="${v[0].depositAmount }">
						    		<input type="hidden" name="chargeList[${vs.index }].earnestAmount" value="${v[0].earnestAmount }">
						    	</tr>
			    			</c:forEach>
			    		</c:otherwise>
			    	</c:choose>
			    			</table></div>
							</td>
		    			</tr>
		    		</table>
		    	</td>
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
				<td  align="right"><input type="button" class="button" value="回退" onclick="save()"></td>
			</tr>
	</table>
	</form>
  </body>
  <script type="text/javascript">
  	function save(){
  		var isCheck=false;
  		var checkbox = document.getElementsByName("checkbox");
  		for(var i=0;i<checkbox.length;i++){
  			if(checkbox[i].checked){
  				isCheck = true;
  			}
  		}
  		if(!isCheck){
  			alert("请选择要回退的项！");
  			return false;
  		}
  		setInterval("showLoadingDiv()",300);
  		document.forms[0].action = "<%=path%>/bill.do?method=returnCharge";
  		document.forms[0].submit();
  	}
  </script>
</html>
