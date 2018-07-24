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
    
    <title>收费项</title>
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
		function checkAll(){
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
		function edit(index){
			document.getElementsByName("itemCode")[0].value=document.getElementsByName("code")[index].value;
			document.getElementsByName("itemName")[0].value=document.getElementsByName("name")[index].value;
			document.getElementsByName("explanation")[0].value=document.getElementsByName("exp")[index].value;
			document.getElementsByName("indexs")[0].value=document.getElementsByName("index")[index].value;
			var sjl=document.getElementsByName("pay")[index].value;
			if(sjl=="1"){
				document.getElementsByName("countPaymentrate")[0].checked=true;
			}else if(sjl=="0"){
				document.getElementsByName("countPaymentrate")[1].checked=true;
			}
			document.getElementById("id").value=document.getElementsByName("checkbox")[index].value;
		}
		function save(){
			var x = Validator.Validate(document.getElementById('formEdit'),2);
			if(x){
				document.forms[0].action="<%=path%>/items.do?method=saveItems";
				document.forms[0].submit();
			}
		}
		function cx(){
			document.forms[0].action="<%=path%>/items.do?method=getItemsList";
			document.forms[0].submit();
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
				document.forms[0].action="<%=path%>/items.do?method=deleteItems";
				document.forms[0].submit();
			}
		}
	</script>

  </head>
  
  <body>
	<form method="post" name="formEidt" id="formEdit">
	<table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="165" nowrap="nowrap" class="form_line">收费项目</td>
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
		  				<td align="left">收费项名称：<input type="text" name="typeName" value="${typeName }"></td>
		  				<td align="right"><input type="button" value="查询" onclick="cx()" class="button"></td>
		  			</tr>
		  		</table></td>		
		  	</tr>
		  	<tr height="45%">
			    <td valign = "top">
			    	<!-- 表单内容区域 -->
					<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height = "50%">
			        		<td width="100%">
			        		<div id = "div1" class = "RptDiv"  >
					   			<table class = "RptTable" border="0" cellpadding="0" cellspacing="0">
			    	<tr>
			    		<th class="RptTableHeadCellFullLock"  width="4%" align="center"><input type="checkbox" name="radio0" id="radio0" onclick="checkAll()"></th>
			    		<th class="RptTableHeadCellFullLock"  width="4%" align="center">序号</th>
			    		<th class="RptTableHeadCellLock"  width="15%">收费项代码</th>
			    		<th class="RptTableHeadCellLock"  width="15%">收费项名称</th>
			    		<th class="RptTableHeadCellLock" >是否统计收缴率</th>
			    		<th class="RptTableHeadCellLock"  width="15%">说明</th>
			    		<th class="RptTableHeadCellLock"  width="15%">排序</th>
			    	</tr>
			    	<c:choose>
						<c:when test="${empty itemslist}">
							<tr align="center">
								<td colspan="12" align="center" class="head_form1">
									<font color="red">没有收费项信息!</font>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${itemslist}" var="c" varStatus="vs">
								<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
						    		<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value="${c.id }"></td>
						    		<td onclick="edit('${vs.index }')" class="RptTableBodyCellLock"  align="center">${vs.count }</td>
						    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="code" value="${c.itemCode }">${c.itemCode }</td>
						    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="name" value="${c.itemName }">${c.itemName }</td>
						    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="pay" value="${c.countPaymentrate }"><c:if test="${c.countPaymentrate==0 }">否</c:if><c:if test="${c.countPaymentrate==1 }">是</c:if></td>
						    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="exp" value="${c.explanation }">${c.explanation }</td>
						    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="index" value="${c.indexs }">${c.indexs }</td>
						    	</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					</table>
	             		</div>
					</td>
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
		  		<td><div align="right"><input type="button" value="删除" onclick="del()" class="button"></div></td>
		  	</tr>
		  	<tr><td height="15">&nbsp;</td></tr>
		    <tr>
		      <td><table width="100%"  cellpadding="0" cellspacing="0" class="">
		    	<input type="hidden" name="id" id="id">
		    	<tr>
					<td  nowrap>收费项目维护：</td>
				</tr>
		    	<tr>
	    			<td><table width="100%"  cellpadding="0" cellspacing="0"  class="Rpt1" >
	    				<tr>
	    				 	<td align="right" width="15%" class="head_form1" nowrap>收费项代码：</td>
	    				 	<td class="head_form1"><input type="text" name="itemCode" width="35%" dataType="Require" msg="收费项代码不得为空！"><font color="red">*</font></td>
	    				 	<td align="right" width="15%" class="head_form1" nowrap>收费项名称：</td>
	    				 	<td class="head_form1"><input type="text" name="itemName"  dataType="Require" msg="收费项名称不得为空！"><font color="red">*</font></td>
	    				</tr>
	    				<tr>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>是否统计收缴率：</td>
	    				 	<td class="head_form1" align=""><input type="radio" name="countPaymentrate" width="35%" value="1" checked="checked">是<input type="radio" name="countPaymentrate" width="35%" value="0">否</td>
	    				 	<td class="head_form1" align="right" width="15%" nowrap>排序：</td>
	    				 	<td class="head_form1"><input type="text" name="indexs"></td>
	    				</tr>
	    				<tr>
	    				 	<td class="head_form1" align="right" width="15%"  nowrap>说明：</td>
	    				 	<td class="head_form1" colspan="3"><textarea rows="3" style="width: 50%" name="explanation"></textarea></td>
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
</html>
