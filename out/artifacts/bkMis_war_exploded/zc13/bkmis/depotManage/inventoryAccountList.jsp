<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="loading">
	<table width=100% height=100%  cellspacing="0" cellpadding="0">
		<tr align="center" valign="middle">
			<td><img src="<%=path %>/resources/images/loading1.gif"  />
			<br />
			<span style="font: 14px;color:blue">正在分析数据，请稍候...</span></td>
		</tr>
	</table>
</div>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>库存核算</title>
    
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		function addAccount(){
			 var s = window.showModalDialog("accountDepot.do?method=goAdd",window,"dialogWidth=450px;dialogHeight=400px;resizable=yes;center=1");
			 if(s == "flag"){
				//window.location.href("accountDepot.do?method=showAccount");
			 	searchAccount();
			 }
		}
		function detailAccount(){
			 var chkx = document.getElementsByName("childBox");
			 var count = 0;
			 var itemIds = '';
			 for(var i = 0;i<chkx.length;i++){
			 	if(chkx[i].checked == true){
			 		count++;
			 		itemIds = chkx[i].value;
			 	}
			 }
			 if(count == 0){
			 	alert("请选择要查看的信息!");
			 	return ;
			 }else if(count == 1){
			 	accountForm.action = "accountDepot.do?method=detailAccount&editId="+itemIds;
			 	accountForm.submit();
			 }
			 else{
			 	alert("只能一次查看一条的详细记录!");
			 	return ;
			 }
		}
		//全选事件
		function selectedAll(){
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
		//反结算及删除
		function del(){
			var itemIds = '';
			var chkx = document.getElementsByName("childBox");
			var month = document.getElementsByName("month");
			var month1;
			for(var i = 0;i<chkx.length;i++){
				if(chkx[i].checked == true){
					itemIds += chkx[i].value + ','; 
					month1=month[i].value;
				}
			}
			if(itemIds.length>0){
				if(parseInt(month1)<parseInt('${month}')){
					alert("对不起，无法删除历史结算表！");
					return;
				}else if(!window.confirm("您确定要删除吗?")) {
					return ;
				}
				itemIds = itemIds.slice(0,itemIds.length-1);
				document.accountForm.action = "<%=path%>/accountDepot.do?method=delDepotAccount&delIds="+itemIds;
				document.accountForm.submit();
			}else{
				alert("请选择要删除的记录!");
				return ;
			}
		}
		//查询
		function searchAccount(){
			document.accountForm.action = "accountDepot.do?method=showAccount";
			document.accountForm.submit();
		}
		//双击查看详细信息
		function detail(id){
			window.open("accountDepot.do?method=detailAccount&editId="+id);
			//var s = window.showModalDialog("accountDepot.do?method=detailAccount&editId="+id,window,"dialogWidth=1000px;dialogHeight=900px;resizable=yes;center=1");
		}
	</script>
  </head>
  
  <body style="overflow-y:hidden;" onLoad="hideLoadingDiv();">
    <form action="" method="post" name="accountForm" id="">
    	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">库存核算信息列表</td>
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
					            	<td align = "left" nowrap="nowrap">年份：
					            		<input type="text" name="year" id="year" readonly onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" value="${year }" class="Wdate"/>
					            	</td>
									<td align="right" nowrap="nowrap">
					            		<input type="button" id = "focuson" class="button" value="确定" onclick="searchAccount();">
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
								                <th class = "RptTableHeadCellFullLock"><input type="checkbox" name="parentBox" id="parentBox" onclick="selectedAll();"></th>
												<th class = "RptTableHeadCellFullLock">序号</th>
												<th class = "RptTableHeadCellFullLock">创建时间</th>
												<th class = "RptTableHeadCellLock">年份</th>
												<th class = "RptTableHeadCellLock">月份</th>
												<th class = "RptTableHeadCellLock">开始日期</th>
												<th class = "RptTableHeadCellLock">结束日期</th>
												<th class = "RptTableHeadCellLock">本期入库数量</th>
												<th class = "RptTableHeadCellLock">本期出库数量</th>
												<th class = "RptTableHeadCellLock">本期库存余量</th>
											</tr>
											<c:choose>
											<c:when test="${empty accountForm.accountList}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的核算信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${accountForm.accountList}" var="al" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="detail('${al.id }')">
														<td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="childBox" name="childBox" value="${al.id }"></td>
														<td class="RptTableBodyCellLock" align="center" title="${vs.count }">${vs.count }&nbsp;</td>
														<td class="RptTableBodyCellLock" align="center" title="${al.makeDate }">${al.makeDate }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${al.year }">${al.year }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${al.month }">${al.month }&nbsp;
															<input type="hidden" name="month" value="${al.month }">
														</td>
														<td class="RptTableBodyCell" align="center" title="${al.beginDate }">${al.beginDate }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${al.endDate }">${al.endDate }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${al.inputAccount }">${al.inputAccount }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${al.outputAccount }">${al.outputAccount }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${al.residue }">${al.residue }&nbsp;</td>
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
					                			<td nowrap="nowrap"  align="right"><input class="button" onclick="addAccount();" type="button" value="添加月结算"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="反结算" onclick="del()"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" onclick="detailAccount();" value="查看明细"></td>
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
