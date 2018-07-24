<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>资费参数</title>
	
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
			document.getElementsByName("phoneParameter.id")[0].value=document.getElementsByName("checkbox")[index].value;
			document.getElementsByName("phoneParameter.providerId")[0].value=document.getElementsByName("providerId")[index].value;
			document.getElementsByName("phoneParameter.frontSeconds")[0].value=document.getElementsByName("frontSeconds")[index].value;
			document.getElementsByName("phoneParameter.frontCost")[0].value=document.getElementsByName("frontCost")[index].value;
			document.getElementsByName("phoneParameter.nextEachSeconds")[0].value=document.getElementsByName("nextEachSeconds")[index].value;
			document.getElementsByName("phoneParameter.eachCost")[0].value=document.getElementsByName("eachCost")[index].value;
			document.getElementsByName("phoneParameter.prefix")[0].value=document.getElementsByName("prefix")[index].value;
		}
		
		function del(){
			if(window.confirm("您确定要删除吗？")){
				document.actionForm.action="<%=path%>/phoneCost.do?method=deletePhoneParameter";
				document.actionForm.submit();
			}
		}
		
		function query(){
			document.actionForm.action="<%=path%>/phoneCost.do?method=getPhoneParameterList";
			document.actionForm.submit();
		}
		
		function save(){
			document.actionForm2.action="<%=path%>/phoneCost.do?method=savePhoneParameter";
			document.actionForm2.submit();
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
						<td width="165" nowrap="nowrap" class="form_line">资费参数</td>
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
					            	<td nowrap="nowrap" align="right">被叫字头：</td>
					            	<td class="txt"><input name="cxPrefix" type="text"  value="${phoneCostForm.cxPrefix }" /></td>
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
					        			<div id = "div1" class = "RptDiv" style="height:180px;">
							   			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="RptTable">
					              			<tr>
								                <th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this)">
								                </th>
								                <th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
								                <th width="16%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">被叫字头</th>
								                <th width="16%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">前x秒</th>
												<th width="32%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">前x秒费用</th>
												<th width="16%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">之后每x秒</th>
												<th width="32%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">之后每x秒费用</th>
											</tr>
											<c:choose>
												<c:when test="${empty phoneCostForm.phoneParameterList}">
													<tr><td colspan="7" align="center" class="head_form1">暂时没有资费参数信息!</td></tr>
												</c:when>
												<c:otherwise>
													<c:forEach items="${phoneCostForm.phoneParameterList}" var="c" varStatus="vs">
														<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
												    		<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value="${c.id }"></td>
												    		<td class="RptTableBodyCellLock"  align="center">${vs.count }<input type="hidden" name="providerId" value="${c.providerId }"></td>
												    		<td class="RptTableBodyCellLock"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="prefix" value="${c.prefix }">${c.prefix }</td>
												    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="frontSeconds" value="${c.frontSeconds }">${c.frontSeconds }</td>
												    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="frontCost" value="${c.frontCost }">${c.frontCost }</td>
												    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="nextEachSeconds" value="${c.nextEachSeconds }">${c.nextEachSeconds }</td>
												    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="eachCost" value="${c.eachCost }">${c.eachCost }</td>
												    	</tr>
													</c:forEach>
												</c:otherwise>
											</c:choose>
					             		</table>
					             		</div>
									</td>
		     		 			</tr>
								<tr>
								  <td>&nbsp;</td>
								</tr>
								<!-- 分页start -->
            					<c:if test="${!empty phoneCostForm.phoneParameterList}">
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
						  	<input type="hidden" name="phoneParameter.id" maxlength="30" id="phoneParameter.id" />		
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>资费参数维护：</td>
								</tr>
								<tr>
									<td>
									    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="Rpt1">
											<tr>
								                <td width="17%"  nowrap="nowrap" class="head_form1" align="right">运营商：</td>
								                <td nowrap="nowrap" class="head_form1">&nbsp;
													<select name="phoneParameter.providerId" id="phoneParameter.providerId">
														<c:forEach items="${phoneCostForm.serviceProviderList}" var="c" varStatus="vs">
															<option value="${c.id }">${c.name }</option>
														</c:forEach>
													</select>
								                <td width="19%" nowrap="nowrap" class="head_form1" align="right">被叫字头：</td>
								                <td width="26%" nowrap="nowrap" class="head_form1">&nbsp;
								                	<input type="text" name="phoneParameter.prefix" maxlength="30" id="phoneParameter.prefix"></td>
							              	</tr>
							              	<tr>
								                <td nowrap="nowrap" class="head_form1" align="left" colspan="4">
								                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;前<input type="text" name="phoneParameter.frontSeconds" id="phoneParameter.frontSeconds" />秒&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="phoneParameter.frontCost" id="phoneParameter.frontCost" />元
								                </td>
							              	</tr>
							              	<tr>
								                <td nowrap="nowrap" class="head_form1" align="left" colspan="4">
								                	&nbsp;&nbsp;&nbsp;&nbsp;之后每<input type="text" name="phoneParameter.nextEachSeconds" id="phoneParameter.nextEachSeconds" />秒&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="phoneParameter.eachCost" id="phoneParameter.eachCost" />元
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
