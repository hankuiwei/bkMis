<%@ page language="java" import="java.util.*,com.zc13.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String todays = GlobalMethod.getTime();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>收费选择</title>
    
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
  
  <body>
  	<form action="" method="post" id="formEdit">
  	<input type="hidden" name="standardId" value="${standardId }">
  	<input type="hidden" name="billType" value="${billType }">
  	<input type="hidden" name="standardCode" value="${standardCode }">
  	<input type="hidden" name="lpId" value="${lpId }">
    <table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="165" nowrap="nowrap" class="form_line">收费选择</td>
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
					    <!-- 
		  				<td align="left" nowrap>楼幢：<select name="buildId">
		  					<option>全部</option>
		  					<c:forEach items="${EBuilds}" var="v" varStatus="vs">
		  						<option value="${v.buildId }" <c:if test="${v.buildId==buildId }">selected</c:if>>${v.buildName }</option>
		  					</c:forEach>
		  				</select></td>
		  				<td nowrap>楼层：<input type="text" name="floor" style="width: 80px" value="${floor }"></td>
		  				 -->
		  				 <td nowrap>客户名称：<input type="text" name="clientName" style="width: 80px" value="${clientName }"></td>
		  				<td align="right"><input type="button" value="查询" onclick="cx()" class="button"></td>
		  			</tr>
		  			<tr>
		  				<td colspan="5" nowrap><c:if test="${!empty standardId}">${standard.standardName } = ${standard.formula }</c:if>&nbsp;&nbsp;<input type="checkbox" onclick="checkAll(this,'checkbox')">全选</td>
		  			</tr>
		  		</table></td>		
		  	</tr>
		  	<tr height="80%">
			    <td valign = "top">
			    	<!-- 表单内容区域 -->
					<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height = "95%">
			        		<td width="100%">
						  		<div id = "div1" class = "RptDiv" >
									<table width="100%"  cellpadding="0" cellspacing="0" class="form_tab">
								    	<tr>
								    		<th class="RptTableHeadCellLock" width="5%">&nbsp;序号&nbsp;</th>
								    		<th class="RptTableHeadCellLock" width="20%">客户名称</th>
								    		<th class="RptTableHeadCellLock" width="15%">合同号</th>
								    		<c:if test="${billType=='room'}">
								    			<th class="RptTableHeadCellLock" width="15%">楼幢</th>
								    		</c:if>
								    		<c:if test="${billType=='person'}">
								    		</c:if>
								    		<th class="RptTableHeadCellLock" width="15%">房间号</th>
								    		<th class="RptTableHeadCellLock" width="10%">收取</th>
								    		<th class="RptTableHeadCellLock"  width="13%">起效日期</th>
								    		<th class="RptTableHeadCellLock"  width="13%">截止日期</th>
								    		<th class="RptTableHeadCellLock"  width="13%">数量</th>
								    	</tr>
								    	<c:if test="${billType=='room'}">
								    		<c:forEach items="${list}" var="v" varStatus="vs">
												<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
										    		<td class="RptTableBodyCell" align="center">${vs.count }</td>
										    		<td class="RptTableBodyCell">&nbsp;${v[0].name }</td>
										    		<td class="RptTableBodyCell">&nbsp;${v[4].code }</td>
										    		<td class="RptTableBodyCell">&nbsp;${v[2].EBuilds.buildName }</td>
										    		<td class="RptTableBodyCell">&nbsp;${v[2].roomCode }</td>
										    		<td class="RptTableBodyCell" align="center"><input type="checkbox" name="checkbox" value="${v[2].roomId }" <c:if test="${!empty v[1].id }">checked</c:if> ></td>
										    		<td class="RptTableBodyCell" align="">&nbsp;
										    			<!-- 如果合同下没有该收费标准，则开始时间默认为客户对应房间开始日期和今天日期之间的比较晚的那个日期 -->
										    			<c:choose>
										    				<c:when test="${empty v[1].id}">
										    					<%try{ %>
										    					<input type="text" name="crcList[${vs.index }].beginDate" value="${(today>v[3].beginDate)?today:v[3].beginDate }" onclick="WdatePicker();" style="width: 80;border: 0">
										    					<%}catch(Exception e){ %>
										    					<input type="text" name="crcList[${vs.index }].beginDate" value="<%=todays %>" onclick="WdatePicker();" style="width: 80;border: 0">
										    					<%} %>
										    				</c:when>
										    				<c:otherwise>
										    					<input type="text" name="crcList[${vs.index }].beginDate" value="${v[1].beginDate }" onclick="WdatePicker();" style="width: 80;border: 0">
										    				</c:otherwise>
										    			</c:choose>
										    		</td>
										    		<td class="RptTableBodyCell" align="">&nbsp;
										    		<!-- 如果合同下没有该收费标准，则结束时间默认为客户对应房间结束日期-->
										    		<c:choose>
										    			<c:when test="${empty v[1].id}">
										    				<input type="text" name="crcList[${vs.index }].endDate" value="${v[3].endDate }" onclick="WdatePicker();" style="width: 80;border: 0">
										    			</c:when>
										    			<c:otherwise>
										    				<input type="text" name="crcList[${vs.index }].endDate" value="${v[1].endDate }" onclick="WdatePicker();" style="width: 80;border: 0">
										    			</c:otherwise>
										    		</c:choose>
										    		</td>
										    		<td class="RptTableBodyCell"><input type="text" name="crcList[${vs.index }].amount" value="${(v[1].amount==''||v[1].amount==null)?1:v[1].amount }" onkeyup="value=value.replace(/[^\d]/g,'')" style="width: 50;border: 0"></td>
										    		<input type="hidden" name="crcList[${vs.index }].rebate" value="${v[1].rebate }" style="width: 80;border: 0">
										    		<input type="hidden" name="crcList[${vs.index }].id" value="${v[1].id }"> 
										    		<input type="hidden" name="crcList[${vs.index }].lastBuildDate" value="${v[1].lastBuildDate }"> 
										    		<input type="hidden" name="costtypeList[${vs.index }].id" value="${standard.costTypeId }"> 
										    		<input type="hidden" name="roomList[${vs.index }].roomId" value="${v[2].roomId }">
										    		<input type="hidden" name="clientList[${vs.index }].id" value="${v[0].id }"> 
										    		<input type="hidden" name="csList[${vs.index }].id" value="${standardId }"> 
										    		<input type="hidden" name="compactList[${vs.index }].id" value="${v[3].pactId }"> 
										    	</tr>
								    		</c:forEach>
							    		</c:if>
							    		<input type="hidden" name="standard.costTypeId" value="${standard.costTypeId }">
								    	<c:if test="${billType=='person'}">
								    		<c:forEach items="${list}" var="v" varStatus="vs">
												<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
										    		<td class="RptTableBodyCell" align="center">${vs.count }</td>
										    		<td class="RptTableBodyCell">&nbsp;${v[0].name }</td>
										    		<td class="RptTableBodyCell">&nbsp;${v[2].code }</td>
										    		<td class="RptTableBodyCell">&nbsp;${v[2].roomCodes }</td>
										    		<td class="RptTableBodyCell" align="center"><input type="checkbox" name="checkbox" value="${v[0].id }" <c:if test="${!empty v[1].id }">checked</c:if> /></td>
										    		<td class="RptTableBodyCell" align="">&nbsp;
										    			<!-- 如果合同下没有该收费标准，则开始时间默认为合同开始日期和今天日期之间的比较晚的那个日期 -->
										    			<c:choose>
										    				<c:when test="${empty v[1].id}">
										    					<%try{ %>
										    					<input type="text" name="crcList[${vs.index }].beginDate" value="${(today>v[2].beginDate)?(today):(v[2].beginDate) }" onclick="WdatePicker();" style="width: 80;border: 0">
										    					<%}catch(Exception e){ %>
										    					<input type="text" name="crcList[${vs.index }].beginDate" value="<%=todays %>" onclick="WdatePicker();" style="width: 80;border: 0">
										    					<%} %>
										    				</c:when>
										    				<c:otherwise>
										    					<input type="text" name="crcList[${vs.index }].beginDate" value="${v[1].beginDate }" onclick="WdatePicker();" style="width: 80;border: 0">
										    				</c:otherwise>
										    			</c:choose>
										    		</td>
										    		<td class="RptTableBodyCell" align="">&nbsp;
										    			<!-- 如果合同下没有该收费标准，则结束时间默认为客户对应房间结束日期-->
										    			<c:choose>
											    			<c:when test="${empty v[1].id}">
											    				<input type="text" name="crcList[${vs.index }].endDate" value="${v[2].endDate }" onclick="WdatePicker();" style="width: 80;border: 0">
											    			</c:when>
											    			<c:otherwise>
											    				<input type="text" name="crcList[${vs.index }].endDate" value="${v[1].endDate }" onclick="WdatePicker();" style="width: 80;border: 0">
											    			</c:otherwise>
											    		</c:choose>
										    		</td>
										    		<td class="RptTableBodyCell"><input type="text" name="crcList[${vs.index }].amount" value="${(v[1].amount==''||v[1].amount==null)?1:v[1].amount }" onkeyup="value=value.replace(/[^\d]/g,'')" style="width: 50;border: 0"></td>
										    		<input type="hidden" name="crcList[${vs.index }].rebate" value="${v[1].rebate }" style="width: 80;border: 0">
										    		<input type="hidden" name="crcList[${vs.index }].id" value="${v[1].id }"> 
										    		<input type="hidden" name="crcList[${vs.index }].lastBuildDate" value="${v[1].lastBuildDate }">
										    		<input type="hidden" name="costtypeList[${vs.index }].id" value="${standard.costTypeId }"> 
										    		<input type="hidden" name="roomList[${vs.index }].roomId" value="1">
										    		<input type="hidden" name="clientList[${vs.index }].id" value="${v[0].id }"> 
										    		<input type="hidden" name="csList[${vs.index }].id" value="${standardId }"> 
										    		<input type="hidden" name="compactList[${vs.index }].id" value="${v[2].id }"> 
										    	</tr>
								    		</c:forEach>
							    		</c:if>
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
							<td class="form_line3">&nbsp;</td>
							<td class="form_line3" style="text-align: right">&nbsp;<input type="button" class="button" value="保存" onclick="save()"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right">
					
				</td>
			</tr>
			<!--<tr>
		      <td><table width="100%"  cellpadding="0" cellspacing="0" class="">
		    	<tr>
					<td nowrap>费用录入：</td>
				</tr>
		    	<tr>
	    			<td><table width="100%"  cellpadding="0" cellspacing="0"  class="Rpt1" >
	    				<tr>
	    				 	<td align="right" width="15%" class="head_form1" nowrap="nowrap">客户：</td>
	    				 	<td class="head_form1" colspan="3">
	    				 		<input type="text" name="Person" readonly><input type="button" class="button" value="选择客户" onclick="openPerson('${lpId }')">
	    				 		<input type="hidden" name="ids" dataType="Require" msg="客户不得为空！">
	    				 	</td>
	    				 	<td align="right" width="15%" class="head_form1" nowrap>费用名称：</td>
	    				 	<td class="head_form1">${standard.standardName }
	    				 		<input type="hidden" name="bill.standardName" value="${standard.standardName }">
	    				 		<input type="hidden" name="standard.id" value="${standard.id }">
	    				 	</td>
	    				</tr>
	    				<tr>
	    					<td class="head_form1" align="right" width="15%" nowrap>应收金额：</td>
	    				 	<td class="head_form1"><input type="text" name="bill.billsExpenses" dataType="Double" msg="应付金额必须是数字且不为空！"><font color="red">*</font></td>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>账单日期：</td>
	    				 	<td class="head_form1" align=""><input type="text" name="bill.createDate" readonly onclick="WdatePicker();" class="Wdate" value="${today }"  dataType="Require" msg="账单日期不得为空！"><font color="red">*</font></td>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>账单月份：</td>
	    				 	<td class="head_form1"><input type="text" name="bill.billDate" value="${fn:substring(today,0,7) }" dataType="Require" msg="账单月份不得为空！"><font color="red">*</font></td>
	    				</tr>
	    				<tr>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>实收金额：</td>
	    				 	<td class="head_form1"><input type="text" name="bill.actuallyPaid" dataType="Double2" msg="缴款金额必须是数字！"></td>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>收款日期：</td>
	    				 	<td class="head_form1" align=""><input type="text" name="bill.collectionDate" readonly onclick="WdatePicker();" class="Wdate" value="${today }"></td>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>单据状态：</td>
	    				 	<td class="head_form1">
	    				 		<select name="bill.status">
	    				 			<option value="0">未缴</option>
	    				 			<option value="1">已缴</option>
	    				 		</select>
	    				 	</td>
	    				
	    				 	<td class="head_form1" align="right" width="15%" nowrap>开始日期：</td>
	    				 	<td class="head_form1"><input type="text" name="bill.startDate" readonly onclick="WdatePicker();" class="Wdate" value=""></td>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>终止日期：</td>
	    				 	<td class="head_form1" align=""><input type="text" name="bill.endDate" readonly onclick="WdatePicker();" class="Wdate"></td>
	    				 </tr>
	    				<tr>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>最后缴款日期：</td>
	    				 	<td class="head_form1" colspan="5"><input type="text" name="bill.closeDate" readonly onclick="WdatePicker();" class="Wdate"></td>
	    				 	
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
	    --></table></td>
	    </tr>
	</table>
	</form>
  </body>
  <script type="text/javascript">
	function save(){
		document.forms[0].action="<%=path%>/choice.do?method=save";
		document.forms[0].submit();
	}
	function cx(){
		document.forms[0].action="<%=path%>/choice.do?method=getList";
		document.forms[0].submit();
	}
	function openPerson(lpId){
  		// var URL = "<%=path%>/zc13/bkmis/costManage/person_pop.jsp";
  		var ids = document.getElementsByName("ids")[0].value;
  		var URL = "<%=path%>/choice.do?method=getClientList&ids="+ids+"&lpId"+lpId;
  		var return_value = showModalDialog(URL,"","dialogWidth=400px;dialogHeight=300px;");
  		if(typeof(return_value)!="undefined"){
  			document.getElementsByName("Person")[0].value=return_value.name;
  			document.getElementsByName("ids")[0].value=return_value.id;
  		}
  	}
  	function add(){
  		var standardId = document.getElementsByName("standard.id")[0].value;
  		if(standardId == null || standardId.length == 0){
  			alert("先请选择收费标准！");
  			return false;
  		}
  		var x = Validator.Validate(document.getElementById('formEdit'),2);
  		if(x){
  			document.forms[0].action = "<%=path%>/choice.do?method=saveAccount";
  			document.forms[0].submit();
  		}
  	}
  </script>
</html>
