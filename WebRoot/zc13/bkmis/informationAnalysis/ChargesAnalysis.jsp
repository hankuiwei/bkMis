<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>收费信息分析列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/validate.js"></script>
	<script type="text/javascript">
		//添加
		function addChaAnalysis(){
			var s = window.showModalDialog("analysisCha.do?method=addCharge",window,"dialogWidth=450px;dialogHeight=400px;resizable=yes;center=1");
			if(s == "flag"){
				window.location.href("analysisCha.do?method=showCharge");
			 }
		}
		//全选
		function selectAll(){
			var parentBox = document.getElementById("parentBox");
			var childBox = document.getElementsByName("childBox");
			if(parentBox.checked == true){
				for(var i = 0;i<childBox.length;i++){
					childBox[i].checked = true;
				}
			}else{
				for(var i = 0;i<childBox.length;i++){
					childBox[i].checked = false;
				}
			}
		}
		//删除
		function del(){
			var itemIds = '';
			var chkx = document.getElementsByName("childbox");
			for(var i = 0;i<chkx.length;i++){
				if(chkx[i].checked == true){
					itemIds +=chkx[i].value + ',';
				}
			}
			if(itemIds.length>0){
				if(!window.confirm("您确定要删除吗?")) {return ;}
				itemIds = itemIds.slice(0,itemIds.length-1);
				document.chargeForm.action = "analysisCha.do?method=doDelCharge&delIds="+itemIds;
				document.chargeForm.submit();
			}else{
				alert("请选择要删除的记录!");
			}
		}
		//查询
		function searchAnalysis(){
			document.chargeForm.action = "analysisCha.do?method=showCharge";
			document.chargeForm.submit();
		}
		//查看分析图
		function seeGraphic(){
			var chkx = document.getElementsByName("childBox");
			var itemIds = '';
			var count = 0;
			for(var i = 0;i<chkx.length;i++){
				if(chkx[i].checked == true){
					count++;
					itemIds = chkx[i].value;
				}
			}
			if(count == 0){
				alert("请选择要查看分析图的记录!");
				return ;
			}else if(count == 1){
				document.chargeForm.action = "analysisCha.do?method=selectAnalysis&analysisId="+itemIds;
				document.chargeForm.submit();
			}else{
				alert("只能选择一条记录!");
				return ;
			}
		}
	</script>
  </head>
  
  <body style="overflow-y:hidden;">
    <form action="" method="post" name="chargeForm">
    	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">收费信息分析</td>
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
					            	<td align="left" nowrap="nowrap">创建时间：</td>
					            	<td><input type="text" name="createDate" id="createDate" readonly onclick="WdatePicker()" value="${analysisChaForm.createDate }" class="Wdate"/></td>
									<td align = "left" nowrap="nowrap">开始时间：
										<input type="text" name="beginDate" id="beginDate" readonly onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="${analysisChaForm.beginDate }" class="Wdate"/>
										&nbsp;至&nbsp;
										<input type="text" name="endDate" id="endDate" readonly onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="${analysisChaForm.endDate }" class="Wdate"/>
									</td>
									<td align="right" nowrap="nowrap">
					            		<input type="button" id = "focuson" class="button" value="确定" onclick="searchAnalysis();">
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
								                <th class = "RptTableHeadCellLock"><input type="checkbox" id="parentBox" name="parentBox" onclick="selectAll();"></th>
												<th class = "RptTableHeadCellLock">序号</th>
												<th class = "RptTableHeadCellLock">创建时间</th>
												<th class = "RptTableHeadCellLock">开始时间</th>
												<th class = "RptTableHeadCellLock">截止时间</th>
												<th class = "RptTableHeadCellLock">收费总金额</th>
											</tr>
											<c:choose>
											<c:when test="${empty analysisChaForm.chaAnalysisList}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有相应收费信息分析!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${analysisChaForm.chaAnalysisList}" var="cal" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
														<td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="childBox" name="childBox" value="${cal.id }"></td>
														<td class="RptTableBodyCellLock" align="center" title="${vs.count }">${vs.count }&nbsp;</td>
														<td class="RptTableBodyCellLock" align="center" title="${cal.createDate }">${cal.createDate }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${cal.beginDate}">${cal.beginDate}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${cal.endDate}">${cal.endDate}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${cal.amountMoney}"><script>document.write(parseFloat(${cal.amountMoney}).toFixed(2).toString());</script>&nbsp;</td>
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
					                			<td nowrap="nowrap"  align="right"><input class="button" type="button" id="add" value="添加" onclick="addChaAnalysis()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="查看分析图" onclick="seeGraphic()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="删除" onclick="del()"></td>
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
