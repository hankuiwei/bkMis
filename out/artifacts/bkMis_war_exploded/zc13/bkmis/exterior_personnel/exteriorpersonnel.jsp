<%@ page language="java"  import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.zc13.bkmis.form.ExteriorPerForm;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
	<title>留学人员信息列表</title>
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	
	<script type="text/javascript">
		//跳转到添加界面
		function addPer(){
			document.perform.action="exterior.do?method=addExteriorPer";
			document.perform.submit();
		}
		//跳转到编辑界面
		function editPer(){
			var itemIds = '';
			var chks = document.getElementsByName("childBox");
			var count = 0;
			for(var i = 0;i < chks.length;i++){
			if(chks[i].checked==true){
				count++;
				itemIds = chks[i].value;
				}
			}
			if (count == 0) {
				alert("请选择记录进行修改!");
            	return;
            }else if(count == 1){
				document.perform.action="exterior.do?method=editPer&Id="+itemIds;
				document.perform.submit();
			}else{
					alert("只能选择一条记录");
          		    return;
			}
		}
		//查询
		function search(){
			document.perform.action="exterior.do?method=showExteriorPer";
			document.perform.submit();
		}
		//复选框的全选
		function selectedAll(){
			var parentBox=document.getElementById("parentBox");
				var childBox=document.getElementsByName("childBox");
				if(parentBox.checked==true){
				for(var i=0;i<childBox.length;i++)
				{childBox[i].checked=true;}
				}else{
				for(var i=0;i<childBox.length;i++)
				{childBox[i].checked=false;}
			  }
		}
		//删除
		function delPer(){
			var itemIds = '';
			var chks = document.getElementsByName("childBox");
			for(var i = 0;i<chks.length;i++){
				if(chks[i].checked == true){
					itemIds += chks[i].value + ',';
				}
			}
			if(itemIds.length>0){
				if(!window.confirm("您确定要删除吗?")) {return ;}
				itemIds = itemIds.slice(0, itemIds.length-1);
				document.perform.action="exterior.do?method=delPer&Id="+itemIds;
				document.perform.submit();
			}else{
					alert("请先选择记录，再进行操作!");
					return false;
			}
			
		}
		//查看详细信息
		function detailMessage(id){
			document.perform.action="exterior.do?method=doViewPer&Id="+id;
			document.perform.submit();
		}
		//导出报表
		function exploertReport(){
			document.perform.action="exterior.do?method=exportForeignPerExcel";
			document.perform.submit();
		}
	</script>
</head>
<body style="overflow-y:hidden;">
<form  method="post" name="perform">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">留学人员信息列表</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr height = "95%">
    		<td class="menu_tab2" align="center" valign="top">
     			<table width="100%" height = "100%"  border="0" cellspacing="0" cellpadding="0">
	 				<tr>
						<td  align="center">
							<!-- 查询条件start -->
		  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
					            	<td height="10"></td>
					         	</tr>
					          	<tr>
					           		<td class="txt" nowrap="nowrap">
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					           		</td>
					            	<td align = "left" nowrap="nowrap">姓名：<input type="text" id="name" name="name" value ="${exteriorForm.name }"></td>
									<td align = "left" nowrap="nowrap">现住国家：<input type="text" id="currentCountry" name="currentCountry" value="${exteriorForm.currentCountry }"/></td>
									<td align = "left" nowrap="nowrap">国外学位：<input type="text" id="foreignDegree" name="foreignDegree" value="${exteriorForm.foreignDegree }"/></td>
									<td align = "left" nowrap="nowrap">工作单位：<input type="text" id="company" name="company" value="${exteriorForm.company }"/></td>
									<td align="right" nowrap="nowrap">
					            		<input type="button" id = "focuson" onclick="search();" class="button" value="确定">
									</td>
								</tr>
							 	 <tr>
					           		 <td height="10" colspan="9"></td>
					          	 </tr>
					        </table>
					        <!-- 查询条件end -->
		 			 	</td>
					</tr>
  					<tr height="95%">
					    <td valign = "top">
					    	<!-- 表单内容区域 -->
							<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
								<tr height = "95%">
					        		<td width="100%">
					        		<div id = "div1" class = "RptDiv"  >
							   			<table class = "RptTable" border="0" cellpadding="0" cellspacing="0">
					              			<tr>
								                <th class = "RptTableHeadCellFullLock"><input id="parentBox" name="parentBox" type="checkbox" onclick="selectedAll();"></th>
												<th class = "RptTableHeadCellFullLock">序号</th>
												<th class = "RptTableHeadCellFullLock">姓名</th>
												<th class = "RptTableHeadCellLock">性别</th>
												<th class = "RptTableHeadCellLock">现住国家</th>
												<th class = "RptTableHeadCellLock">国外学位</th>
												<th class = "RptTableHeadCellLock">研究成果</th>
												<th class = "RptTableHeadCellLock">工作单位</th>
												<th class = "RptTableHeadCellLock">享受政策</th>
											</tr>
											<c:choose>
											<c:when test="${empty exteriorForm.exteriorperList}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的留学人员信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${exteriorForm.exteriorperList}" var="ep" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';" id="detail" ondblclick="detailMessage('${ep.id }')">
														<td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="childBox" name="childBox" value="${ep.id }"></td>
														<td class="RptTableBodyCellLock" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
														<td class="RptTableBodyCellLock" align="center" title="${ep.name }">${ep.name }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${ep.sex }">${ep.sex }
														<td class="RptTableBodyCell" align="center" title="${ep.currentCountry }">${ep.currentCountry }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${ep.foreignDegree }">${ep.foreignDegree }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${ep.nativeDegree }">${ep.nativeDegree }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${ep.company }">${ep.company }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${ep.enjoyPolicy }">${ep.enjoyPolicy }&nbsp;</td>
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
								<tr>
									<td align="right">
										<table>
											<tr>
					                			<td nowrap="nowrap"  align="right"><input class="button" onclick="addPer()" type="button" value="添加"></td>
					               				<td nowrap="nowrap"><input type="button" onclick="editPer()" class="button" value="编辑"></td>
					               				<td nowrap="nowrap"><input type="button" onclick="delPer()" class="button" value="删除"></td>
					               				<c:choose>
					               				<c:when test="${empty exteriorForm.exteriorperList}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="alert('无记录,不能导出报表!');"></td>
					               				</c:when>
					               				<c:otherwise>
					               				<td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="exploertReport()"></td>
					               				</c:otherwise>
					              				</c:choose>
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
</html>
