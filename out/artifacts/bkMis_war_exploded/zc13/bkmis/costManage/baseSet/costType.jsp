<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>费用类型</title>
	
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
			document.getElementsByName("costTypeCode")[0].value=document.getElementsByName("code")[index].value;
			document.getElementsByName("costTypeName")[0].value=document.getElementsByName("name")[index].value;
			var c_ids=","+document.getElementsByName("c_ids")[index].value+",";
			var costparatypeobjs = document.getElementsByName("costparatypeIds");
			for(var i = 0;i<costparatypeobjs.length;i++){
				if(c_ids.indexOf(","+costparatypeobjs[i].value+",")>=0){
					costparatypeobjs[i].checked=true;
				}else{
					costparatypeobjs[i].checked=false;
				}
			}
			document.getElementById("id").value=document.getElementsByName("checkbox")[index].value;
		}
		
		function del(){
			if(window.confirm("您确定要删除吗？")){
				document.actionForm.action="<%=path%>/costType.do?method=deleteCostType";
				document.actionForm.submit();
			}
		}
		
		function query(){
			document.actionForm.action="<%=path%>/costType.do?method=getCostTypeList";
			document.actionForm.submit();
		}
		
		function save(){
			if(!validate()){
		      	return false;
		    }
			document.actionForm2.action="<%=path%>/costType.do?method=editCostType";
			document.actionForm2.submit();
		}
		
		//验证
		function validate(){
			var f = 0;
			var str = "以下原因造成保存失败：\n\n";
			var costTypeCode = document.getElementById("costTypeCode").value;
			var costTypeName = document.getElementById("costTypeName").value;
			if(costTypeCode==""||costTypeCode==null){
				str = str+"    费用类型编码不能为空！\n";
				document.getElementById("costTypeCode").focus();
				f = 1;
			}
			if(costTypeName==""||costTypeName==null){
				str = str+"    费用类型名称不能为空！\n";
				if(f==0)
					document.getElementById("costTypeName").focus();
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
						<td width="165" nowrap="nowrap" class="form_line">费用类型</td>
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
					            	<td class="txt"><input name="typeName" type="text"  value="${typeName }" /></td>
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
								                <th width="16%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">费用类型编码</th>
								                <th width="16%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">费用类型名称</th>
												<th width="32%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">计费参数</th>
											</tr>
											<c:choose>
												<c:when test="${empty costTypeList}">
													<tr><td colspan="12" align="center" class="head_form1">暂时没有费用类型信息!</td></tr>
												</c:when>
												<c:otherwise>
													<c:forEach items="${costTypeList}" var="c" varStatus="vs">
														<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
												    		<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value="${c.id }"></td>
												    		<td class="RptTableBodyCellLock"  align="center">
												    			&nbsp;${startNum }
												    			<c:set var="startNum" value="${startNum+1}"></c:set>
												    		</td>
												    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="code" value="${c.costTypeCode }">${c.costTypeCode }</td>
												    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="name" value="${c.costTypeName }">${c.costTypeName }</td>
												    		
												    		
												    		<!-- 收费参数的显示start -->
												    		<!-- 定义变量cpnnames用来保存收费参数类型名称字符串 -->
															<c:set var="cpnnames" value="" scope="page" ></c:set>
															
															<!-- 定义变量cpnids用来保存收费参数id字符串 -->
												    		<c:set var="cpnids" value="" scope="page" ></c:set>
												    		
												    		<c:forEach items="${c.costparaTypes}" var="g" varStatus="ga">
												    			<c:if test="${ga.index!=0}">
												    				<c:set var="cpnnames" value="${cpnnames}," scope="page" ></c:set>
												    				<c:set var="cpnids" value="${cpnids}," scope="page" ></c:set>
												    			</c:if>
												    			<c:set var="cpnnames" value="${cpnnames}${g.typeName }" scope="page" ></c:set>
												    			<c:set var="cpnids" value="${cpnids}${g.id }" scope="page" ></c:set>
												    		</c:forEach>
												    		
												    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')" title="${cpnnames }">&nbsp;<input type="hidden" name="c_ids" value="${cpnids }" />
												    			<c:choose>
													    			<c:when test="${fn:length(cpnnames)>30}">
													    				<c:out value="${fn:substring(cpnnames,0,30)}......"></c:out>
													    			</c:when>
													    			<c:otherwise>
													    				<c:out value="${cpnnames}"></c:out>
													    			</c:otherwise>
													    		</c:choose>
												    		</td>
												    		<!-- 收费参数的显示end -->
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
            					<c:if test="${!empty costTypeList}">
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
						  	<input type="hidden" name="id" maxlength="30" id="id" />		
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>费用类型维护：</td>
								</tr>
								<tr>
									<td>
									    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="Rpt1">
											<tr>
								                <td width="17%"  nowrap="nowrap" class="head_form1" align="right">费用类型编码：</td>
								                <td nowrap="nowrap" class="head_form1">&nbsp;
													<input type="text" name="costTypeCode" maxlength="20" onchange="test_code()" id="costTypeCode"><font color="red" >*</font></td>
								                <td width="19%" nowrap="nowrap" class="head_form1" align="right">费用类型名称：</td>
								                <td width="26%" nowrap="nowrap" class="head_form1">&nbsp;
								                	<input type="text" name="costTypeName" maxlength="30" id="costTypeName"><font color="red">*</font></td>
							              	</tr>
							              	<tr>
								                <td width="17%"  nowrap="nowrap" class="head_form1" align="right">计费参数类型选择：</td>
								                <td nowrap="nowrap" class="head_form1" colspan="3">
								                	<table width="100%" border="0" cellpadding="0" cellspacing="0">
								                		<tr>
											                <c:forEach items="${costparatypeList}" var="c" varStatus="vs">
											                	<c:if test="${vs.index>0&&vs.index%5==0}"></tr><tr></c:if>
											                	<td width="20%"><input type="checkbox" name="costparatypeIds" value="${c.id }" />${c.typeName }</td>
											                </c:forEach>
									                	</tr>
									                </table>
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
</script>
</html>
