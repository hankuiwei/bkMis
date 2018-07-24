<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
	<title>电话计费列表</title>
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		//将秒转换成时分秒
		function formatSeconds(value) {   
			var theTime = Number(value);   
			var theTime1 = 0;   
			var theTime2 = 0;   
			//alert(theTime);   
			if(theTime > 60) {   
			    theTime1 = Number(theTime/60);   
			    theTime = Number(theTime%60);   
			   //alert(theTime1+"-"+theTime);   
				if(theTime1 > 60) {   
				   theTime2 = Number(theTime1/60);   
				   theTime1 = Number(theTime%60);   
				}   
			}   
			var result = ""+(theTime>9?theTime:"0"+theTime)+"：";   
			if(theTime1 > 0) {   
			    result = ""+(parseInt(theTime1)>9?parseInt(theTime1):"0"+parseInt(theTime1))+"："+result;   
			}else{
				result = "00："+result;   
			}   
			if(theTime2 > 0) {   
			    result = ""+(parseInt(theTime2)>9?parseInt(theTime2):"0"+parseInt(theTime2))+"："+result;   
			}else{
				result = "00："+result;
			}
			if(result!=null&&result!=""){
				result = result.substring(0,result.length-1);
			}
			return result;   
		} 
	
      	//按名称查询
      	function query(){
         	document.actionForm.action = "<%=path%>/phoneCost.do?method=getPhoneCostInfoList";
         	document.actionForm.target = "_self";
         	document.actionForm.submit();
      	}
      	
      	//查看明细
      	function getInfo(phoneNumber){
      		window.open("<%=path%>/phoneCost.do?method=getPhoneCostInfoDetails&cxStartDate=${phoneCostForm.cxStartDate }&cxEndDate=${phoneCostForm.cxEndDate }&cxPhoneNumber="+phoneNumber);
      	}
      
      	//导出
      	function exportLr(){
      		document.actionForm.action = "<%=path%>/phoneCost.do?method=exportPhoneCostInfoList";
      		document.actionForm.target = "_self";
         	document.actionForm.submit();
      	}
      	
      	//打印
      	function printLr(){
      		document.actionForm.action = "<%=path%>/phoneCost.do?method=printPhoneCostInfoList";
      		document.actionForm.target = "_blank";
         	document.actionForm.submit();
      	}
      	
      	//批量更新话单
      	function batchUpdateCallInfo(){
      		var url = "<%=path%>/zc13/bkmis/phoneWebService/batchUpdateCallInfo.jsp";
			var options = "dialogWidth:630px;dialogHeight:300px;status:no;scroll:no;";
			var win = window.showModalDialog(url, this.window, options);
			if(win=="flag"){
				//window.location=window.location;
				query();
			}
      	}
      	
    </script>
</head>
<body>
<!-- 加载页面div -->
	<jsp:include page="/loading.jsp"></jsp:include>
	<!-- 加载页面div -->
<form  method="post" name="actionForm" action = "">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">用户电话费用列表</td>
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
					            	<td  align = "left">房间号：<input type="text" id="cxRoomCode" name="cxRoomCode" value ="${phoneCostForm.cxRoomCode }"></td>
									
									<td  align = "left">客户名称：<input type="text" id="cxClientName" name="cxClientName" value ="${phoneCostForm.cxClientName }"></td>
									<td align = "left">缴费情况：
										<select name="cxIsPaid">
											<option value="">所有</option>
											<option value="已缴" <c:if test="${phoneCostForm.cxIsPaid eq '已缴' }">selected</c:if> >已缴</option>
											<option value="未缴" <c:if test="${phoneCostForm.cxIsPaid eq '未缴' }">selected</c:if> >未缴</option>
										</select>
									</td>
									<td align="right">
										<input type="button" style="width:100px;" onclick="batchUpdateCallInfo();" class="button" value="批量更新话单" />
									</td>
								</tr>
								<tr>
					           		<td class="txt" nowrap="nowrap">
					           			&nbsp;
					           		</td>
									<td align = "left" colspan="3">日期：
										<input type="text" name="cxStartDate" id="cxStartDate" readonly onclick="WdatePicker()" value="${phoneCostForm.cxStartDate }" class="Wdate"/>
										&nbsp;至&nbsp;
										<input type="text" name="cxEndDate" id="cxEndDate" readonly onclick="WdatePicker()" value="${phoneCostForm.cxEndDate }" class="Wdate"/>
									</td>
									<td align="right">
					            		<input type="button" id = "focuson" onclick="query();" class="button" value="确定">
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
								<tr><td width="100%"><span style=" color: blue;">注：在某一行上双击鼠标，可以查看详细内容</span></td></tr>
								<tr height = "95%">
					        		<td width="100%">
					        		<div id = "div1" class = "RptDiv"  >
							   			<table class = "RptTable" border="0" cellpadding="0" cellspacing="0">
					              			<tr >
												<th class = "RptTableHeadCellFullLock">序号</th>
												<th class = "RptTableHeadCellFullLock">电话号码</th>
												<th class = "RptTableHeadCellLock">所在房间号</th>
												<th class = "RptTableHeadCellLock">住户名称</th>
												<th class = "RptTableHeadCellLock">通话总时长（hh:mm:ss）</th>
												<th class = "RptTableHeadCellLock">话费总金额(￥)</th>
											</tr>
											<c:choose>
											<c:when test="${empty phoneCostForm.phoneCostList}">
											<tr align="center">
												<td colspan="6" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${phoneCostForm.phoneCostList}" var="v" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick = "getInfo('${v.phoneNumber }');">
														<td class="RptTableBodyCellLock" align="center" title="${vs.count}">${vs.count}</td>
														<td class="RptTableBodyCellLock" align="center" title="${v.phoneNumber}">${v.phoneNumber}</td>
														<td class="RptTableBodyCell" align="center" title="${v.roomFullName}">${v.roomFullName}</td>
														<td class="RptTableBodyCell" align="center" title="${v.clientName}">${v.clientName}</td>
														<td class="RptTableBodyCell" align="center" title="${v.callTime}"><script>document.write(formatSeconds(${v.callTime}))</script></td>
														<td class="RptTableBodyCell" align="center" title="${v.cost}">${v.cost}</td>
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
								<tr>
									<td>
										总金额：<script>document.write(parseFloat("${phoneCostForm.totalCost }").toFixed(2).toString());</script>
									</td>
								</tr>
								<!-- 分页start -->
								<tr>
									<td colspan="10">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr><td class="form_line3">&nbsp;</td>
												<td class="form_line3" colspan="7">${pagination }</td>
												<td class="form_line3">&nbsp;</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td align="right">
										<table>
											<tr>
					               				<td nowrap="nowrap"><input type="button" onclick="printLr();" class="button" value="打印"></td>
					               				<td nowrap="nowrap"><input type="button" onclick="exportLr();" class="button" value="导出报表"></td>
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
