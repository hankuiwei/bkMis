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
    
    <title>电费收取</title>
    
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
					<td width="165" nowrap="nowrap" class="form_line">电费收取</td>
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
		  				<td align="right" nowrap="nowrap" nowrap>客户名称：</td>
		  				<td><input type="text" name="clientName" value="${CBillForm.clientName }" style="width:80px;">
		  				</td>
		  				<!--<td align="right" nowrap="nowrap" nowrap>房间:</td>
		  				<td><input type="text" name="roomCode" value="${CBillForm.roomCode }" style="width:80px;"></td>
		  				--><td align="right" nowrap="nowrap" nowrap>账期：</td>
		  				<td nowrap="nowrap">
		  					<select name="before">
		  						<c:forEach items="${billDateList}" var="v">
		  							<option <c:if test="${CBillForm.before==v }" >selected</c:if> >${v }</option>
		  						</c:forEach>
		  					</select>
		  					—
		  					<select name="after">
			  					<c:forEach items="${billDateList}" var="v">
			  						<option <c:if test="${CBillForm.after==v }" >selected</c:if> >${v }</option>
			  					</c:forEach>
		  					</select>
		  				</td>
		  				<td align="right">
		  					<input type="button" value="查询" onclick="cx()" class="button">
		  					<input type="button" value="导出报表" onclick="exportExcel('${size }')" class="button">
		  				</td>
		  			</tr>
		  		</table></td>		
		  	</tr>
		  	<tr height="58%">
			    <td valign = "top">
			    	<!-- 表单内容区域 -->
					<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height = "95%">
			        		<td width="100%">
						  		<div id = "div1" class = "RptDiv"  >
		  			<table width="100%"  cellpadding="0" cellspacing="0" class="">
			    	<tr>
			    		<th class="RptTableHeadCellFullLock"  width="5%"><input type="checkbox" name="checkboxAll" value=""></th>
			    		<!--<th class="RptTableHeadCellFullLock"  width="15%">房号</th>
			    		--><th class="RptTableHeadCellFullLock" width="25%" >客户名称</th>
			    		<th class="RptTableHeadCellLock"  width="15%">账单月份</th>
			    		<th class="RptTableHeadCellLock"  width="15%">缴款日期</th>
			    		<th class="RptTableHeadCellLock"  width="15%">缴款金额</th>
			    	</tr>
			    	<c:choose>
			    		<c:when test="${empty billList}">
			    			<tr align="center">
								<td colspan="12" align="center" class="head_form1">
									<font color="red">没有信息!</font>
								</td>
							</tr>
			    		</c:when>
			    		<c:otherwise>
			    			<c:forEach items="${billList}" var="v" varStatus="vs">
			    				<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
						    		<td class="RptTableBodyCell"  align="center"><input type="checkbox" name="checkbox" value="${v.id }"></td>
						    		<!--<td class="RptTableBodyCell">&nbsp;${v.ERooms.roomCode }</td>
						    		--><td class="RptTableBodyCell">&nbsp;${v.compactClient.name }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v.billDate }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v.createDate }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v.billsExpenses }</td>
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
				<td align="right"><input type="button" class="button" value="删除" onclick="del()">&nbsp;&nbsp;
				</td>
			</tr>
			<tr><td height="15">&nbsp;</td></tr>
		    <tr>
		      <td><table width="100%"  cellpadding="0" cellspacing="0" class="">
		    	<tr>
					<td>电费收取：</td>
				</tr>
		    	<tr>
	    			<td><table width="100%"  cellpadding="0" cellspacing="0"  class="Rpt1" >
	    				<tr>
	    				 	<td align="right" width="15%" class="head_form1">客户：</td>
	    				 	<td class="head_form1" colspan="">
	    				 		<input type="text" name="Person" readonly><input type="button" class="button" value="选择客户" onclick="openPerson()">
	    				 		<input type="hidden" name="ids"> 
	    				 		<input type="hidden" name="bill.standardName" value="电费"> 
	    				 		<input type="hidden" name="bill.status" value="0"> 
	    				 	</td>
	    				 	<td class="head_form1" align="right" width="15%">账单月份：</td>
	    				 	<td class="head_form1" align=""><input type="text" name="bill.billDate" value="${fn:substring(today,0,7) }"></td>
	    				</tr>
	    				<tr>
	    				 	<td class="head_form1" align="right" width="15%">缴款金额：</td>
	    				 	<td class="head_form1"><input type="text" name="bill.billsExpenses"></td>
	    				 	<td class="head_form1" align="right" width="15%">缴款日期：</td>
	    				 	<td class="head_form1"><input type="text" name="bill.createDate" readonly onclick="WdatePicker();" class="Wdate" value="${today }"></td>
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
	</form>
  </body>
  <script type="text/javascript">
  	function openPerson(){
  		var ids = document.getElementsByName("ids")[0].value;
  		var URL = "<%=path%>/bill.do?method=getClientList&ids="+ids;
  		var return_value = showModalDialog(URL,"","dialogWidth=400px;dialogHeight=300px;");
  		if(typeof(return_value)!="undefined"){
  			document.getElementsByName("Person")[0].value=return_value.name;
  			document.getElementsByName("ids")[0].value=return_value.id;
  		}
  	}
  	function save(){
  		document.forms[0].action = "<%=path%>/bill.do?method=saveElectricity";
  		document.forms[0].submit();
  	}
  	function cx(){
  		document.forms[0].action = "<%=path%>/bill.do?method=getElectricity";
  		document.forms[0].submit();
  	}
  	function del(){
  		document.forms[0].action = "<%=path%>/bill.do?method=deleteElectricity";
  		document.forms[0].submit();
  	}
  	function exportExcel(isNull){
      		if(isNull!='0') {
				document.forms[0].action = "<%=path%>/bill.do?method=exportExcel1";
				document.forms[0].submit();
			} else {
				alert("没有记录可导出");
			}
      }
  </script>
</html>
