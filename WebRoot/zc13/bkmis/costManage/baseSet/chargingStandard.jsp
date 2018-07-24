<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>收费标准</title>
    
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
	<script type="text/javascript">
		function selall(){
			var rad0 = document.getElementById("radio0");
			var arrrad1 = document.getElementsByName("checkbox");	
			if(rad0.checked){
				for(var i=0;i<arrrad1.length;i++){
					arrrad1[i].checked="checked";
				}
			}else{
				for(var i=0;i<arrrad1.length;i++){
					arrrad1[i].checked="";
				}
			}		
		}
		function generation(str){
			var area = document.getElementById("tea").value;
			document.getElementById("tea").value = area+str;
		}
	</script>

  </head>
  
  <body onload="init();">
   <form method="post" name="formEdit" id="formEdit">
   <table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     			<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="165" nowrap="nowrap" class="form_line">收费标准</td>
					<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
					<td width="1080" class="form_line2"></td>
					<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
       			</tr>
   			</table></td>
 		</tr>
 		<tr>
 			<td><table width="100%" cellpadding="0" cellspacing="0" class="menu_tab2">
			  	<tr>
		  		<td align=""><table width="100%" cellpadding="0" cellspacing="0">
		  			<tr>
		  				 <td class="txt" nowrap="nowrap">
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					     </td>
					     <td align="" width="" class="">账套：
			    			<select name="accountid">
	    				 		<c:choose>
	    				 			<c:when test="${!empty CCoststandardForm.ztList}">
	    				 				<c:forEach items="${CCoststandardForm.ztList}" var="zt">
	    				 					<option value="${zt[0].id }" <c:if test="${CCoststandardForm.accountid==zt[0].id }">selected</c:if>>${zt[1].lpName }</option>
	    				 				</c:forEach>
	    				 			</c:when>
	    				 		</c:choose>
	    				 	</select></td>
		  				<td align="left">费用名称：<input type="text" name="typeName" value="${CCoststandardForm.typeName }"></td>
		  				<td align="right"><input type="button" value="查询" onclick="cx()" class="button"></td>
		  			</tr>
		  		</table></td>		
		  	</tr>
	  	<tr height="30%">
			    <td valign = "top">
			    	<!-- 表单内容区域 -->
					<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height = "50%">
			        		<td width="100%">
			        		<div id = "div1" class = "RptDiv"  >
					   			<table class = "RptTable" border="0" cellpadding="0" cellspacing="0">
				    	<tr>
				    		<th class="RptTableHeadCellFullLock"><input type="checkbox" name="radio0" id="radio0" onclick="selall()"></th>
				    		<th class="RptTableHeadCellFullLock">序号</th>
				    		<th class="RptTableHeadCellLock">费用名称</th>
				    		<th class="RptTableHeadCellLock">费用代码</th>
				    		<th class="RptTableHeadCellLock">费用类型</th>
				    		<th class="RptTableHeadCellLock">计算公式</th>
				    		<th class="RptTableHeadCellLock">计算周期</th>
				    		<th class="RptTableHeadCellLock">结算周期</th>
				    	</tr>
				    	<c:choose>
				    		<c:when test="${empty bzList}">
				    			<tr align="center">
								<td colspan="12" align="center" class="head_form1">
									<font color="red">没有收费标准信息!</font>
								</td>
							</tr>
				    		</c:when>
				    		<c:otherwise>
				    			<c:forEach items="${bzList}" var="bz" varStatus="vs">
				    				<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
							    		<td class="RptTableBodyCellLock" align="center"><input type="checkbox" name="checkbox" value="${bz[0].id }"></td>
							    		<td onclick="edit('${vs.index }')" class="RptTableBodyCellLock" align="center">${vs.count }</td>
							    		<td onclick="edit('${vs.index }')" class="RptTableBodyCell"><input type="hidden" value="${bz[0].standardName }" name="c_name">&nbsp;${bz[0].standardName }</td>
							    		<td onclick="edit('${vs.index }')" class="RptTableBodyCell"><input type="hidden" value="${bz[0].standardCode }" name="c_code">&nbsp;${bz[0].standardCode }</td>
							    		<td onclick="edit('${vs.index }')" class="RptTableBodyCell"><input type="hidden" value="${bz[0].costTypeId }" name="c_type">&nbsp;${bz[3].costTypeName }</td>
							    		<td onclick="edit('${vs.index }')" class="RptTableBodyCell"><input type="hidden" value="${bz[0].formula }" name="c_formula">&nbsp;${bz[0].formula }</td>
							    		<td onclick="edit('${vs.index }')" class="RptTableBodyCell"><input type="hidden" value="${bz[0].computeCycle }" name="c_cycle">
							    			<c:if test="${bz[0].computeCycle==1 }">年</c:if>
							    			<c:if test="${bz[0].computeCycle==2 }">半年</c:if>
							    			<c:if test="${bz[0].computeCycle==3 }">季度</c:if>
							    			<c:if test="${bz[0].computeCycle==4 }">月</c:if>
							    			<c:if test="${bz[0].computeCycle==5 }">天</c:if>
							    		</td>
							    		<td onclick="edit('${vs.index }')" class="RptTableBodyCell">
							    			<c:if test="${bz[0].balanceCycle==1 }">年</c:if>
							    			<c:if test="${bz[0].balanceCycle==2 }">半年</c:if>
							    			<c:if test="${bz[0].balanceCycle==3 }">季度</c:if>
							    			<c:if test="${bz[0].balanceCycle==4 }">月</c:if>
							    			<c:if test="${bz[0].balanceCycle==5 }">天</c:if>
							    		</td>
							    		<input type="hidden" value="${bz[0].accountTemplateId }" name="c_account">
							    		<input type="hidden" value="${bz[0].itemId }" name="c_item">
							    		<input type="hidden" value="${bz[0].billType }" name="c_bill">
							    		<input type="hidden" value="${bz[0].explanation }" name="c_bz">
							    		<input type="hidden" value="${bz[0].balanceCycle }" name="c_balance">
							    	</tr>
				    			</c:forEach>
				    		</c:otherwise>
				    	</c:choose>
				    	</table></div></td>
		    	</tr>
			    	</table></td>
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
			  		<td><div align="right">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="删除" class="button" onclick="del()"></div></td>
			  	</tr>
			    <tr>
			    	<td><table width="100%" cellpadding="0" cellspacing="0" class="">
			    		<tr>
									<td height="" nowrap>收费标准维护</td>
								</tr>
		    			<tr>
		    				<td><table width="100%" cellpadding="0" cellspacing="0" class="Rpt1"> 
		    					<input type="hidden" name="standard.id">
			    				<tr>
			    				 	<td align="right" width="15%" class="head_form1" nowrap>账套：</td>
			    				 	<td colspan="3" class="head_form1"><select name="standard.accountTemplateId">
			    				 		<c:choose>
			    				 			<c:when test="${!empty CCoststandardForm.ztList}">
			    				 				<c:forEach items="${CCoststandardForm.ztList}" var="zt">
			    				 					<option value="${zt[0].id }">${zt[1].lpName }</option>
			    				 				</c:forEach>
			    				 			</c:when>
			    				 		</c:choose>
			    				 	</select></td>
			    				</tr>
			    				<tr>
			    				 	<td align="right" width="15%" class="head_form1" nowrap="nowrap">费用类型：</td>
			    				 	<td class="head_form1"><select name="standard.costTypeId"  id="costTypeId" onchange="changeType(this)">
			    				 		<c:choose>
			    				 			<c:when test="${!empty CCoststandardForm.fylxList}">
			    				 				<c:forEach items="${CCoststandardForm.fylxList}" var="lx">
			    				 					<option value="${lx.id }">${lx.costTypeName }</option>
			    				 				</c:forEach>
			    				 			</c:when>
			    				 		</c:choose>
			    				 	</select></td>
			    				 	<td align="right" width="15%" class="head_form1" nowrap>收费项：</td>
			    				 	<td class="head_form1"><select name="standard.itemId">
			    				 		<c:choose>
			    				 			<c:when test="${!empty CCoststandardForm.itemsList}">
			    				 				<c:forEach items="${CCoststandardForm.itemsList}" var="vs">
			    				 					<option value="${vs.id }">${vs.itemName }(${vs.itemCode })</option>
			    				 				</c:forEach>
			    				 			</c:when>
			    				 		</c:choose>
			    				 	</select></td>
			    				</tr>
			    				<tr>
			    				 	<td align="right" width="15%" class="head_form1" nowrap="nowrap">费用代码：</td>
			    				 	<td class="head_form1"><input type="text" name="standard.standardCode" dataType="Require" msg="费用代码不得为空！"><font color="red">*</font></td>
			    				 	<td align="right" width="15%" class="head_form1" nowrap>费用名称：</td>
			    				 	<td class="head_form1"><input type="text" name="standard.standardName" dataType="Require" msg="费用名称不得为空！"><font color="red">*</font></td>
			    				</tr>
			    				<tr>
			    				 	<td align="right" width="15%" class="head_form1" nowrap>计算周期：</td>
			    				 	<td class="head_form1"><select name="standard.computeCycle">
			    				 		<option value="0">无</option>
			    				 		<option value="1">年</option>
			    				 		<option value="2">半年</option>
			    				 		<option value="3">季度</option>
			    				 		<option value="4">月</option>
			    				 		<option value="5">天</option>
			    				 	</select></td>
			    				 	<td align="right" width="15%" class="head_form1" nowrap>结算周期：</td>
			    				 	<td class="head_form1"><select name="standard.balanceCycle">
			    				 		<option value="1">年</option>
			    				 		<option value="2">半年</option>
			    				 		<option value="3">季度</option>
			    				 		<option value="4">月</option>
			    				 		<option value="5">天</option>
			    				 	</select></td>
			    				</tr>
			    				<tr> 	
			    				 	<td align="right" width="15%" class="head_form1" nowrap>账单类型：</td>
			    				 	<td class="head_form1" colspan="3"><select name="standard.billType">
			    				 		<option value="person">按住户</option>
			    				 		<option value="room" selected="selected">按房间</option>
			    				 	</select></td>
			    				</tr>
			    				<tr>
			    				 	<td align="right" width="15%" class="head_form1" nowrap>计算公式：</td>
			    				 	<td colspan="3" class="head_form1">
			    				 		<table width="100%" cellpadding="0" cellspacing="0">
			    				 			<tr>
			    				 				<td width="30%"  class="head_form1"><textarea rows="3" name="standard.formula" id="tea" style="width: 99%" dataType="Require" msg="计算公式不得为空!"></textarea><font color="red">*</font></td>
			    				 			</tr>
			    				 			<tr>
			    				 				<td class="head_form1">
			    				 					<input type="button" value="+" onclick="generation('+')">&nbsp;
			    				 					<input type="button" value="-" onclick="generation('-')">&nbsp;
			    				 					<input type="button" value="*" onclick="generation('*')">&nbsp;
			    				 					<input type="button" value="/" onclick="generation('/')">&nbsp;
			    				 					<input type="button" value="(" onclick="generation('(')">&nbsp;
			    				 					<input type="button" value=")" onclick="generation(')')">&nbsp;
			    				 					<input type="button" value="?" onclick="generation('?')">&nbsp;
			    				 					<input type="button" value=":" onclick="generation(':')">&nbsp;
			    				 					<input type="button" value="=" onclick="generation('=')">&nbsp;
			    				 					<input type="button" value="." onclick="generation('.')"><br>
			   				 						<c:forEach items="${CCoststandardForm.ctypeParas}" var="vs" varStatus="v">
			   				 							<input type="button" value="${vs.typeName }" id="${vs.typeCode }" style="display:none" name="para" onclick="generation('{${vs.typeName }}')" >
			   				 						</c:forEach>
			    				 				</td>
			    				 			</tr>
			    				 		</table>
			    				 	</td>
				    			</tr>
			    				<tr>
			    					<td align="right" class="head_form1" nowrap="nowrap">说明：</td>
			    					<td colspan="3" class="head_form1"><textarea rows="3" style="width: 99%" name="standard.explanation"></textarea></td>
			    				</tr>
			    				<tr align="right">
			    					<td colspan="4" class="head_form1"><input type="button" value="保存" class="button" onclick="save()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="取消" class="button"></td>
			    				</tr>
		    				</table></td>
		    			</tr>
		    		</table></td>
		    	</tr>	
	    	</table></td>
	    </tr>
	    </table>
    </form>
  </body>
  <script type="text/javascript">
  	var DataArr = new Array();//计费参数
  	<c:forEach items="${CCoststandardForm.fylxList}" var="p" varStatus="vs">
  		DataArr[${p.id}] = new Array();;
	  	<c:forEach items="${p.costparaTypes}" var="c" varStatus="v">
		  	DataArr[${p.id}][${v.index}] = "${c.typeCode}";
	   	</c:forEach>
   	</c:forEach>
   </script>
   <script type="text/javascript"><!--
   		 function changeType(obj){
   			changeCostPara(obj.value);
   		 }
   		 function changeCostPara(value){
   		 	var ob = document.getElementsByName("para");
   		 	for(var i=0;i<ob.length;i++){
   		 		ob[i].style.display = "none";
   		 	}
   		 	for(var j=0;j<DataArr[value].length;j++){
   		 		document.getElementById(DataArr[value][j]).style.display="";
   		 	}
   		 	
   		 }
   		 function changeGauge(value){
		 	var obj = document.getElementById("useGauge");
			obj.options.length=0;
			for(var i=0;i<Datas[value].length;i++){
				var opt = document.createElement("OPTION");
				obj.add(opt);
				opt.value=Datas[value][i].value;
				opt.text =Datas[value][i].text;
			}		
   		 	if(obj.options.length==0){
   		 		document.getElementById("gauge").style.display="none";
   		 	}else{
   		 		document.getElementById("gauge").style.display="";
   		 	}
   		 }
   		 function init(){
   		 	var obj = document.getElementById("costTypeId");
   		 	changeType(obj);
   		 }
   		 function save(){
   		 	var x = Validator.Validate(document.getElementById('formEdit'),2);
   		 	if(x){
   		 		document.forms[0].action="<%=path%>/standard.do?method=save";
   		 		document.forms[0].submit();
   		 	}
   		 }
   		 function del(){
   		 	var isCheck = false;
	  		var checkbox = document.getElementsByName("checkbox");
	  		for(var i=0;i<checkbox.length;i++){
	  			if(checkbox[i].checked){
	  				isCheck = true;
	  				break;
	  			}
	  		}
	  		if (!isCheck){
	  			alert("请选择要删除的项！");
	  			return false;
	  		}
	  		if(confirm("你确定要删除吗？")){
   		 		document.forms[0].action="<%=path%>/standard.do?method=delete";
   		 		document.forms[0].submit();
   		 	}
   		 }
   		 function cx(){
   		 	document.forms[0].action="<%=path%>/standard.do?method=getList";
   		 	document.forms[0].submit();
   		 }
   		 function edit(index){
   		 	var value = document.getElementsByName("c_type")[index].value;
   		 	changeCostPara(value);
   		 	document.getElementsByName("standard.id")[0].value=document.getElementsByName("checkbox")[index].value;
   		 	document.getElementsByName("standard.accountTemplateId")[0].value=document.getElementsByName("c_account")[index].value;
   		 	document.getElementsByName("standard.standardCode")[0].value=document.getElementsByName("c_code")[index].value;
   		 	document.getElementsByName("standard.standardName")[0].value=document.getElementsByName("c_name")[index].value;
   		 	document.getElementsByName("standard.costTypeId")[0].value=value;
   		 	document.getElementsByName("standard.itemId")[0].value=document.getElementsByName("c_item")[index].value;
   		 	document.getElementsByName("standard.computeCycle")[0].value=document.getElementsByName("c_cycle")[index].value;
   		 	document.getElementsByName("standard.billType")[0].value=document.getElementsByName("c_bill")[index].value;
   		 	document.getElementsByName("standard.formula")[0].innerHTML=document.getElementsByName("c_formula")[index].value;
   		 	document.getElementsByName("standard.explanation")[0].innerHTML=document.getElementsByName("c_bz")[index].value;
   		 	document.getElementsByName("standard.balanceCycle")[0].value=document.getElementsByName("c_balance")[index].value;
   		 }
   --></script>
</html>
