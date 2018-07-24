<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ page import="com.zc13.util.*,java.text.*,java.util.*;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Calendar calendar = Calendar.getInstance();
	String nowDate = dateFormat.format(new Date());
	String years = GlobalMethod.ObjToStr(request.getAttribute("years"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>费用抄录</title>
	
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
		function query(){
			document.actionForm.action="<%=path%>/meterInput.do?method=showTotalWriteMeterInfo";
			document.actionForm.submit();
		}
		function writeMeter(lpId,meterTypeCode){
			if(meterTypeCode==""||meterTypeCode==null){
				alert("请选择相应的表具，然后再添加抄表！");
				return;
			}
			var url = "<%=path%>/zc13/bkmis/costManage/writeMeter/writeMeter1.jsp";
			window.open (url, '添加抄录信息', 'height=650, width=1000, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no') //这句要写成一行
		}
		function query2(years,months){
			var url="<%=path%>/meterInput.do?method=getUserReadMeter&years="+years+"&months="+months;
			window.open (url, '抄录信息', 'height=650, width=1000, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no') //这句要写成一行
		}
		
		function query3(){
			var values = "";
			var checkboxs = document.getElementsByName("checkbox");
			for(var i = 0;i<checkboxs.length;i++){
				if(checkboxs[i].checked){
					values = checkboxs[i].value;
				}
			}
			if(values==""){
				alert("请选择一条记录");
				return;
			}
			var url="<%=path%>/meterInput.do?method=getUserReadMeter"+values;
			window.open (url, '抄录信息', 'height=650, width=1000, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no') //这句要写成一行
		}
		
		//控制复选框只能选中一个
		function selectOne(obj){
			var checkboxs = document.getElementsByName("checkbox");
			if(obj.checked){
				for(var i = 0;i<checkboxs.length;i++){
					if(obj!=checkboxs[i])
						checkboxs[i].checked=false;
				}
			}
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
						<td width="165" nowrap="nowrap" class="form_line">费用抄录</td>
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
							<input type="hidden" name="lpId" value="${lp.lpId }" />
							<input type="hidden" name="meterTypeCode" value="${meterTypeCode }" />
							<input type="hidden" name="meterName" value="${meterName }" />
							<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
		  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
					            	<td height="10" colspan="6"></td>
					         	</tr>
					          	<tr>
					           		<td class="txt" nowrap="nowrap" width="33%">
					           			&nbsp;&nbsp;楼盘：${lp.lpName }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户表具：${meterName }
					           		</td>
					            	<td nowrap="nowrap" align="right">年度：</td>
					            	<td class="txt">
					            		<select name="years">
					            			<%for(int i = 2009;i<calendar.get(Calendar.YEAR)+2;i++){ %>
					            			<option value="<%=i %>" <%if(years.equals(String.valueOf(i))){ %>selected<%} %>><%=i %></option>
					            			<%} %>
					            		</select>
					            		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					            		<input type="button" class="button" id = "focuson" onclick="query()" value="查询">
					            	</td>
					            	<td nowrap="nowrap" align="right"></td>
									<td class="txt"></td>
					            	<td align="right">
										<!-- 如果想在enter键敲下的时候默认被点击，那么只需将button的id设置为focuson即可 -->
					            		<input type="button" class="button" id = "focuson" onclick="writeMeter('${lp.lpId }','${meterTypeCode }')" value="添加抄表">
					           		 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					           		 	<input type="button" class="button" id = "focuson" onclick="query3()" value="查看明细">
									</td>
					         	 </tr>
					          	 <tr>
					            	<td height="10" colspan="6"></td>
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
					              				<th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">选择</th>
								                <th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
								                <th width="24%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">年度</th>
								                <th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">月份</th>
								                <!-- 
												<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">金额</th>
												 -->
												<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">用量</th>
											</tr>
											<c:choose>
												<c:when test="${empty list}">
													<tr><td class="RptTableBodyCell" colspan="6" align="center">暂无记录</td></tr>
												</c:when>
												<c:otherwise>
													<c:forEach items="${list}" var="c" varStatus="vs">
														<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="query2('${c.years }','${c.months }');">
															<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" onclick="selectOne(this);" value="&years=${c.years }&months=${c.months }"></td>
															<td class="RptTableBodyCellLock"  align="center">${vs.index+1 }</td>
															<td class="RptTableBodyCell">${c.years }</td>
															<td class="RptTableBodyCell">${c.months }</td>
															<!-- 
															<td class="RptTableBodyCell">0</td>
															 -->
															<td class="RptTableBodyCell">${c.total }</td>
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
