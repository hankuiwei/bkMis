<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>区号</title>
	
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
			document.getElementsByName("regionCode.id")[0].value=document.getElementsByName("checkbox")[index].value;
			document.getElementsByName("regionCode.areaCode")[0].value=document.getElementsByName("areaCode")[index].value;
			document.getElementsByName("regionCode.areaName")[0].value=document.getElementsByName("areaName")[index].value;
		}
		
		function del(){
			if(window.confirm("您确定要删除吗？")){
				document.actionForm.action="<%=path%>/phoneCost.do?method=deleteRegionCode";
				document.actionForm.submit();
			}
		}
		
		function query(){
			document.actionForm.action="<%=path%>/phoneCost.do?method=getRegionCodeList";
			document.actionForm.submit();
		}
		
		function save(){
			document.actionForm.action="<%=path%>/phoneCost.do?method=saveRegionCode";
			document.actionForm.submit();
		}
		
	</script>
</head>
<body>
	<form name="actionForm" method="post">
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">区号</td>
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
							
							<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
		  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
					            	<td height="10" colspan="9"></td>
					         	</tr>
					          	<tr>
					           		<td class="txt" nowrap="nowrap">
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					           		</td>
					            	<td nowrap="nowrap" align="right">区号：</td>
					            	<td class="txt"><input name="cxAreaCode" type="text"  value="${phoneCostForm.cxAreaCode }" /></td>
					            	<td nowrap="nowrap" align="right">地区名称：</td>
					            	<td class="txt"><input name="cxAreaName" type="text"  value="${phoneCostForm.cxAreaName }" /></td>
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
					        			<div id = "div1" class = "RptDiv" style="height:210px;">
							   			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="RptTable">
					              			<tr>
								                <th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this)">
								                </th>
								                <th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
								                <th width="16%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">区号</th>
								                <th width="16%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">地区名称</th>
											</tr>
											<c:choose>
												<c:when test="${empty phoneCostForm.regionCodeList}">
													<tr><td colspan="7" align="center" class="head_form1">暂时没有区号信息!</td></tr>
												</c:when>
												<c:otherwise>
													<c:forEach items="${phoneCostForm.regionCodeList}" var="c" varStatus="vs">
														<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
												    		<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value="${c.id }"></td>
												    		<td class="RptTableBodyCellLock"  align="center">${vs.count }</td>
												    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="areaCode" value="${c.areaCode }">${c.areaCode }</td>
												    		<td class="RptTableBodyCell"  onclick="edit('${vs.index }')">&nbsp;<input type="hidden" name="areaName" value="${c.areaName }">${c.areaName }</td>
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
            					<c:if test="${!empty phoneCostForm.regionCodeList}">
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
						  	<input type="hidden" name="regionCode.id" maxlength="30" id="regionCode.id" />		
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>区号维护：</td>
								</tr>
								<tr>
									<td>
									    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="Rpt1">
											<tr>
								                <td width="17%"  nowrap="nowrap" class="head_form1" align="right">区号：</td>
								                <td nowrap="nowrap" class="head_form1">&nbsp;
													<input type="text" name="regionCode.areaCode" maxlength="30" id="regionCode.areaCode">
								                <td width="19%" nowrap="nowrap" class="head_form1" align="right">地区名称：</td>
								                <td width="26%" nowrap="nowrap" class="head_form1">&nbsp;
								                	<input type="text" name="regionCode.areaName" maxlength="30" id="regionCode.areaName">
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
    					</td>
  					</tr>
				</table>
			</td>
		</tr>
	</table>
	</form>
</body>
<script language=javascript>
</script>
</html>
