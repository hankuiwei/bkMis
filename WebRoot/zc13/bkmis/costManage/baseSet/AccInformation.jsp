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
    
    <title>收费帐套</title>
    	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
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
  
  <body>
  <form name="formEidt" method="post" id="formEdit">
  <table width="98%" cellpadding="0" cellspacing="0" align="center">
  	<tr>
   		<td height="5"></td>
 		</tr>
 		<tr>
   		<td>
   			<table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="165" nowrap="nowrap" class="form_line">收费帐套</td>
					<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
					<td width="1080" class="form_line2"></td>
					<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
       			</tr>
   			</table>
   		</td>
 	</tr>
  	<tr>
  		<td><table width="100%" cellpadding="0" cellspacing="0"  class="menu_tab2">
		  	<tr height="30%">
			    <td valign = "top">
			    	<!-- 表单内容区域 -->
					<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height = "50%">
			        		<td width="100%">
			        		<div id = "div1" class = "RptDiv"  >
					   			<table class = "RptTable" border="0" cellpadding="0" cellspacing="0">
			    	<tr>
			    		<th class="RptTableHeadCellFullLock" ><input type="checkbox" name="radio0" id="radio0" onclick="selall()"></th>
			    		<th class="RptTableHeadCellFullLock" >序号</th>
			    		<th class="RptTableHeadCellLock" >楼盘名称</th>
			    		<th class="RptTableHeadCellLock" >开户银行</th>
			    		<th class="RptTableHeadCellLock" >银行账户</th>
			    		<th class="RptTableHeadCellLock" >银行户名</th>
			    		<th class="RptTableHeadCellLock" >币种</th>
			    		<th class="RptTableHeadCellLock" >每月关账日</th>
			    	</tr>
			    	<c:choose>
			    		<c:when test="${empty list}">
			    			<tr align="center">
								<td colspan="12" align="center" class="head_form1">
									<font color="red">没有收费帐套信息!</font>
								</td>
							</tr>
			    		</c:when>
			    		<c:otherwise>
			    			<c:forEach var="c" items="${list}" varStatus="vs">
			    				<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
						    		<td class="RptTableBodyCellLock" align="center"><input type="checkbox" name="checkbox" value="${c[0].id }"></td>
						    		<td onclick="edit('${vs.index }')" class="RptTableBodyCellLock" align="center">${vs.count }</td>
						    		<td onclick="edit('${vs.index }')" class="RptTableBodyCell" ><input type="hidden" name="lp_id" value="${c[0].lpid }">&nbsp;${c[1].lpName }</td>
						    		<td onclick="edit('${vs.index }')" class="RptTableBodyCell" ><input type="hidden" name="bank" value="${c[0].accountBank }">&nbsp;${c[0].accountBank }</td>
						    		<td onclick="edit('${vs.index }')" class="RptTableBodyCell" ><input type="hidden" name="acc" value="${c[0].account }">&nbsp;${c[0].account }</td>
						    		<td onclick="edit('${vs.index }')" class="RptTableBodyCell" ><input type="hidden" name="bank_name" value="${c[0].bankName }">&nbsp;${c[0].bankName }</td>
						    		<td onclick="edit('${vs.index }')" class="RptTableBodyCell" ><input type="hidden" name="hb" value="${c[0].currency }">&nbsp;${c[0].currency }</td>
						    		<td onclick="edit('${vs.index }')" class="RptTableBodyCell" ><input type="hidden" name="type" value="${c[0].closeType }">&nbsp;
						    			<c:choose>
						    				<c:when test="${c[0].closeType=='0' }">无</c:when>
						    				<c:when test="${c[0].closeType=='1' }">当月最后一天</c:when>
						    				<c:otherwise>指定日</c:otherwise>
						    			</c:choose>
						    		</td>
						    		<input type="hidden" name="jsjd" value="${c[0].precision }">
						    		<input type="hidden" name="jwfs" value="${c[0].rounding }">
						    		<input type="hidden" name="znj" value="${c[0].formula }">
						    		<input type="hidden" name="znjsm" value="${c[0].explanation }">
						    		<input type="hidden" name="produce" value="${c[0].produceDate }">
						    		<input type="hidden" name="month" value="${c[0].closeMonth }">
						    		<input type="hidden" name="day" value="${c[0].closeDay }">
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
							<td class="form_line3">&nbsp;</td>
							<td class="form_line3">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
		    <tr>
		  		<td align="right"><input type="button" value="删除" onclick="del()" class="button"></td>
		  	</tr>
			<tr>
		    	<td><table width="100%" cellpadding="0" cellspacing="0" class="">
			    	<input type="hidden" name="accounttemplate.id">
			    	<tr>
						<td>收费帐套维护(<span style=" color: blue;">一个楼盘下只能创建一个帐套</span>)：</td>
					</tr>
			    	<tr>
		    			<td>
		    				<table width="100%" cellpadding="0" cellspacing="0"  class="Rpt1">
		    				<tr>
		    				 	<td align="right" width="15%" class="head_form1">楼盘：</td>
		    				 	<td colspan="3" class="head_form1"><select name="accounttemplate.lpid">
		    				 		<c:if test="${!empty lpList}">
		    				 			<c:forEach items="${lpList}" var="lp">
		    				 				<option value="${lp.lpId }">${lp.lpName }</option>
		    				 			</c:forEach>
		    				 		</c:if>
		    				 	</select></td>
		    				</tr>
		    				<tr>
		    				 	<td align="right" width="15%" class="head_form1" nowrap="nowrap">开户银行：</td>
		    				 	<td class="head_form1"><input type="text" name="accounttemplate.accountBank" dataType="Require" msg="开户银行不得为空！"><font color="red">*</font></td>
		    				 	<td align="right" width="15%" class="head_form1" nowrap>银行户名：</td>
		    				 	<td class="head_form1"><input type="text" name="accounttemplate.bankName" dataType="Require" msg="银行户名不得为空！"><font color="red">*</font></td>
		    				</tr>
		    				<tr>
		    				 	<td align="right" width="15%" class="head_form1" nowrap>银行账号：</td>
		    				 	<td class="head_form1"><input type="text" name="accounttemplate.account"  dataType="Require" msg="银行账号不得为空！"><font color="red">*</font></td>
		    				 	<td align="right" width="15%" class="head_form1" nowrap>账单结算货币：</td>
		    				 	<td class="head_form1"><select style="width: 30%" name="accounttemplate.currency">
		    				 		<option value="RMB">RMB</option>
		    				 		<option value="USD">USD</option>
		    				 	</select></td>
		    				</tr>
		    				<tr>
		    				 	<td align="right" width="15%" class="head_form1" nowrap>生成账单日：</td>
		    				 	<td class="head_form1" colspan ="3" ><input type="text" value="" size="2" name="accounttemplate.produceDate"  dataType="Require" msg="生成账单日不得为空！">号<font color="red">*</font></td>
		    				</tr>
		    				<tr>
		    				 	<td align="right" width="15%" class="head_form1" nowrap>关帐日：</td>
		    				 	<td class="head_form1" id="rq3" colspan="3"><select name="accounttemplate.closeType" onchange="changeRq(this.value)">
		    				 		<option value="0">无</option>
		    				 		<option value="1">当月最后一天</option>
		    				 		<option value="2">以指定日</option>
		    				 	</select></td>
		    				 	<td align="right" width="15%" class="head_form1" id="rq1" style="display: none;" nowrap>指定关帐日：</td>
		    				 	<td class="head_form1" id="rq2" style="display: none;"  nowrap>
		    				 		<select name="accounttemplate.closeMonth">
		    				 			<option value="0">当前月</option>
		    				 			<option value="1">下一月</option>
		    				 		</select>
		    				 		<input type="text" name="accounttemplate.closeDay" size=2 dataType="Double2" maxValue="28" minValue="1" msg="指定关帐日为1~28号!">号{1~28}
		    				 	</td>
		    				</tr>
		    				<tr>
		    				 	<td align="right" width="15%" class="head_form1" nowrap>帐套默认设置：</td>
		    				 	<td colspan="3" class="head_form1">
		    				 		<table width="100%" cellpadding="0" cellspacing="0">
		    				 			<tr>
		    				 				<td width="20%" align="right" class="head_form1" nowrap>计算精度：</td>
		    				 				<td colspan="3" class="head_form1"><select name="accounttemplate.precision">
						    				 		<option value="1">元</option>
						    				 		<option value="2">角</option>
						    				 		<option value="3">分</option>
						    				 	</select>
						    				</td>
		    				 			</tr>
		    				 			<tr>
		    				 				<td width="20%" align="right" class="head_form1" nowrap>进位方式：</td>
		    				 				<td colspan="3" class="head_form1"><select name="accounttemplate.rounding">
						    				 		<option value="1">四舍五入</option>
						    				 		<option value="2">只入不舍</option>
						    				 		<option value="3">只舍不入</option>
						    				 	</select>
						    				</td>
		    				 			</tr>
		    				 			<tr>
		    				 				<td width="20%" align="right" class="head_form1" nowrap>滞纳金计算公式：</td>
		    				 				<td width="30%"  class="head_form1"><textarea rows="3" id="tea" name="accounttemplate.formula"></textarea></td>
		    				 				<td width="10%" align="right" class="head_form1" nowrap>选择参数：</td>
		    				 				<td class="head_form1">
		    				 					<input type="button" value="+" onclick="generation('+')">
		    				 					<input type="button" value="-" onclick="generation('-')">
		    				 					<input type="button" value="*" onclick="generation('*')">
		    				 					<input type="button" value="/" onclick="generation('/')">
		    				 					<input type="button" value="(" onclick="generation('(')">
		    				 					<input type="button" value=")" onclick="generation(')')">
		    				 					<input type="button" value="?" onclick="generation('?')">
		    				 					<input type="button" value=":" onclick="generation(':')">
		    				 					<input type="button" value="=" onclick="generation('=')">
		    				 					<input type="button" value="." onclick="generation('.')">
		    				 					<br>
		    				 					<input type="button" value="合同金额" onclick="generation('{合同金额}')">
		    				 					<input type="button" value="过期天数" onclick="generation('{过期天数}')">
		    				 					
		    				 				</td>
		    				 			</tr>
		    				 		</table>
		    				 	</td>
		    				</tr>
		    			</table></td>
		    		</tr>
		    		<tr align="right">
		    			<td class=""><input type="button" value="保存" onclick="save()" class="button">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="取消" class="button"></td>
		    		</tr>
		    	</table></td>
		    </tr>
    	</table></td>
    </tr>
  </table>  	
  </form>
  </body>
  <script type="text/javascript">
  	function edit(index){
  		document.getElementsByName("accounttemplate.id")[0].value = document.getElementsByName("checkbox")[index].value
  		document.getElementsByName("accounttemplate.lpid")[0].value = document.getElementsByName("lp_id")[index].value
  		document.getElementsByName("accounttemplate.accountBank")[0].value = document.getElementsByName("bank")[index].value
  		document.getElementsByName("accounttemplate.account")[0].value = document.getElementsByName("acc")[index].value
  		document.getElementsByName("accounttemplate.bankName")[0].value = document.getElementsByName("bank_name")[index].value
  		document.getElementsByName("accounttemplate.precision")[0].value = document.getElementsByName("jsjd")[index].value
  		document.getElementsByName("accounttemplate.rounding")[0].value = document.getElementsByName("jwfs")[index].value
  		document.getElementsByName("accounttemplate.produceDate")[0].value = document.getElementsByName("produce")[index].value
  		document.getElementsByName("accounttemplate.formula")[0].innerHTML = document.getElementsByName("znj")[index].value
  		document.getElementsByName("accounttemplate.closeType")[0].value = document.getElementsByName("type")[index].value
  		document.getElementsByName("accounttemplate.closeMonth")[0].value = document.getElementsByName("month")[index].value
  		document.getElementsByName("accounttemplate.closeDay")[0].value = document.getElementsByName("day")[index].value
  		changeRq(document.getElementsByName("accounttemplate.closeType")[0].value);
  	}
  	function save(){
  		var x = Validator.Validate(document.getElementById('formEdit'),2);
  		if(x){
  			document.forms[0].action="<%=path%>/account.do?method=save";
  			document.forms[0].submit();
  		}
  	}
  	function changeRq(val){
  		if(val=="2"&&val!=null){
  			document.getElementById("rq3").colSpan="1";
  			document.getElementById("rq1").style.display="";
  			document.getElementById("rq2").style.display="";
  		}else{
  			document.getElementById("rq3").colSpan="3";
  			document.getElementById("rq1").style.display="none";
  			document.getElementById("rq2").style.display="none";
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
  			document.forms[0].action="<%=path%>/account.do?method=delete";
  			document.forms[0].submit();
  		}
  	}
  </script>
</html>
