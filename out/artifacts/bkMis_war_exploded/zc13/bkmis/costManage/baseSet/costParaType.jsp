<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>计费参数类型</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <!-- 使用日期控件时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<!-- 使用单元格在线编辑功能时，引入下面的js -->
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
	<c:if test="${!empty alertMessage}">
	<script type="text/javascript">
		alert("${alertMessage}");
	</script>
	</c:if>
	<script type="text/javascript">
		function checkAll(obj){
			var arrrad1 = document.getElementsByName("checkbox");	
			if(obj.checked){
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
			document.getElementsByName("typeCode")[0].value=document.getElementsByName("t_code")[index].value;
			document.getElementsByName("typeName")[0].value=document.getElementsByName("name")[index].value;
			document.getElementsByName("code")[0].value=document.getElementsByName("c_code")[index].value;
			var c_typeValue=document.getElementsByName("c_type")[index].value;
			document.getElementById("id").value=document.getElementsByName("checkbox")[index].value;
			//获得下拉框对象
			var codeType = document.getElementsByName("codeType")[0];
			//下拉框option的数量
			var optionLength = codeType.options.length;
			for(var i = 0;i<optionLength;i++){
				if(codeType.options[i].value==c_typeValue){
					codeType.options[i].selected=true;
				}
			}
		}
		
		function del(){
			if(window.confirm("您确定要删除吗？")){
				document.actionForm.action="<%=path%>/costParaType.do?method=deleteCostParaType";
				document.actionForm.submit();
			}
		}
		
		function query(){
			document.actionForm.action="<%=path%>/costParaType.do?method=getCostParaTypeList";
			document.actionForm.submit();
		}
		
		function save(){
			if(!validate()){
		      	return false;
		    }
			document.actionForm2.action="<%=path%>/costParaType.do?method=editCostParaType";
			document.actionForm2.submit();
		}
		
		//验证
		function validate(){
			var f = 0;
			var str = "以下原因造成保存失败：\n\n";
			var typeCode = document.getElementById("typeCode").value;
			var typeName = document.getElementById("typeName").value;
			if(typeCode==""||typeCode==null){
				str = str+"    计费参数类型编码不能为空！\n";
				document.getElementById("typeCode").focus();
				f = 1;
			}
			if(typeName==""||typeName==null){
				str = str+"    计费参数类型名称不能为空！\n";
				if(f==0)
					document.getElementById("typeName").focus();
				f = 1;
			}
			if(f==0){
				return true;
			}else{
				alert(str);
				return false;
			}
		}
		
		
	</script>
</head>
<body>

	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">计费参数类型</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr><!-- 这个td负责画正式内容的左、右、下方的边框 -->
    		<td class="menu_tab2" align="center">
     			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 				
	 				<tr>
						<td  align="center">
							<!-- 查询条件start -->
							<form name="actionForm" method="post">
							<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
		  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
					            	<td height="10" colspan="9"></td>
					         	</tr>
					          	<tr>
					           		<td class="txt" nowrap="nowrap">
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					           		</td>
					            	<td nowrap="nowrap" align="right">类型名称：</td>
					            	<td class="txt"><input name="type_Name" type="text"  value="${type_Name }" /></td>
					            	<td nowrap="nowrap" align="right"></td>
									<td class="txt"></td>
					            	<td align="right">
										<!-- 如果想在enter键敲下的时候默认被点击，那么只需将button的id设置为focuson即可 -->
					            		<input type="button" class="button" id = "focuson" onclick="query()" value="查询">
									</td>
					         	 </tr>
							 	 <tr>
					           		 <td height="10" colspan="9"></td>
					          	 </tr>
					        </table>
					        
					        <!-- 查询条件end -->
		 			 	</td>
					</tr>
  					<tr>
					    <td>
					    	<!-- 表单内容区域 -->
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
					        		<td>
					        		
							   			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="RptTable">
					              			<tr>
								                <th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this)">
								                </th>
								                <th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
								                <th width="16%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">类型编码</th>
								                <th width="16%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">类型名称</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">代码类型</th>
												<th width="54%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">代码</th>
											</tr>
											<c:choose>
												<c:when test="${empty costParaTypeList}">
													<tr><td colspan="12" align="center" class="head_form1">暂时没有计费参数类型信息!</td></tr>
												</c:when>
												<c:otherwise>
													<c:forEach items="${costParaTypeList}" var="c" varStatus="vs">
														<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
												    		<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value="${c.id }"></td>
												    		<td class="RptTableBodyCellLock"  align="center">
												    			&nbsp;${startNum }
												    			<c:set var="startNum" value="${startNum+1}"></c:set>
												    		</td>
												    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="t_code" value="${c.typeCode }">${c.typeCode }</td>
												    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="name" value="${c.typeName }">${c.typeName }</td>
												    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">
												    			<input type="hidden" name="c_type" value="${c.codeType }">
												    			<c:if test="${c.codeType==1}">sql语句</c:if>
												    			<c:if test="${c.codeType==2}">普通数据</c:if>
												    			<c:if test="${c.codeType==3}">java代码</c:if>
												    			&nbsp;
												    		</td>
												    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')" title="<c:out value="${c.code}"></c:out>">
												    			<input type="hidden" name="c_code" value="<c:out value="${c.code}"></c:out>">
												    			<c:choose>
												    				<c:when test="${fn:length(c.code)>60}">
												    					<c:out value="${fn:substring(c.code,0,60)}......"></c:out>
												    				</c:when>
												    				<c:otherwise>
												    					<c:out value="${c.code}"></c:out>
												    				</c:otherwise>
												    			</c:choose>
												    		</td>
												    		
												    	</tr>
													</c:forEach>
												</c:otherwise>
											</c:choose>
					             		</table>
					             		
									</td>
		     		 			</tr>
								<tr>
								  <td>&nbsp;</td>
								</tr>
								<!-- 分页start -->
            					<c:if test="${!empty costParaTypeList}">
								<tr>
									<td colspan="10">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr><td class="form_line3">&nbsp;</td>
												<td class="form_line3" colspan="8">${pagination }</td>
												<td class="form_line3">&nbsp;</td>
											</tr>
										</table>
									</td>
								</tr>
								</c:if> 
								<!-- 分页end -->
								<tr>
									<td align="right">
										<table>
											<tr>
					               				<td nowrap="nowrap"><input type="button" class="button" onclick="del()" value="删除"></td>
					              			</tr>
					              		</table>
					              	</td>
				              	</tr>
							</table>
							</form>
							<!-- 扩展业务start（没有扩展业务，则删除下面一段） -->
							<form name="actionForm2" method="post">
							<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
						  	<input type="hidden" name="id" id="id" />		
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>计费参数类型维护：</td>
								</tr>
								<tr>
									<td>
									    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="Rpt1">
											<tr>
								                <td width="17%"  nowrap="nowrap" class="head_form1" align="right">类型编码：</td>
								                <td nowrap="nowrap" class="head_form1">&nbsp;
													<input type="text" name="typeCode" maxlength="20" onchange="test_code()" id="costTypeCode"><font color="red" >*</font></td>
								                <td width="19%" nowrap="nowrap" class="head_form1" align="right">类型名称：</td>
								                <td width="26%" nowrap="nowrap" class="head_form1">&nbsp;
								                	<input type="text" name="typeName" maxlength="30" id="costTypeName"><font color="red">*</font></td>
							              		<td width="19%" nowrap="nowrap" class="head_form1" align="right">代码类型：</td>
								                <td width="26%" nowrap="nowrap" class="head_form1">&nbsp;
								                	<select name="codeType">
								                		<option value="1">sql语句</option>
								                		<option value="2">普通数据</option>
								                		<option value="3">java代码</option>
								                	</select><font color="red">*</font>
								                	
								                </td>
							              	</tr>
							              	<tr>
								                <td width="17%"  nowrap="nowrap" class="head_form1" align="right">代码：</td>
								                <td nowrap="nowrap" class="head_form1" colspan="5">
								                	<textarea name="code" rows="5" style="width:90%"></textarea>
								                </td>
							              	</tr>
							            </table>			
									</td>
				      			</tr>
				      			<tr>
				        			<td colspan="10" align="right"> 
										<table>
											<tr>
					                			<td nowrap="nowrap"  align="right"><input class="button" type="button" onclick="save()" value="保存"></td>
					               				<td nowrap="nowrap"><input type="reset" class="button" value="取消"></td>
					              			</tr>
					              		</table>
									</td>
				      			</tr>
				    		</table>
				    		</form>
				    		<!-- 扩展业务end（如果没有扩展业务，则删除上面一段） -->
    					</td>
  					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
<script language=javascript>
	function test_code(){
		
	}
</script>
</html>
