<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>info页面</title>    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="../../../resources/css/menu.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	  <style type="text/css">
		<!--
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
		}
		-->
		</style>
		<script language = "javascript">
			function encode(url){
				//setInterval("showLoadingDiv()",300);
				setTimeout("showLoadingDiv()",100);
				encodeURI(encodeURI(url));
				window.location=url;
			}
			//查看分析图
 		function seeGraphic(itemIds){
 			
 			window.open("<%=path%>/analysisEng.do?method=selectGraphic&itemId="+itemIds);
 			
 		}
		</script>
  </head> 
<body>

<!-- 加载页面div -->
<jsp:include page="/loading.jsp"></jsp:include>
<!-- 加载页面div -->
	<input type="hidden" name="show_bkMis_index" value="1" />
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
	    	<td height="18"></td>
	  	</tr>
	  	<tr>
	    	<td height="8">
				<table cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr>
						<td nowrap="nowrap" width="60%" class="zw_font_d">
							总房间数：${adminLoginForm.roomInfoMap.totalNum }间。<br />其中正在装修的有：${adminLoginForm.roomInfoMap.decorationNum }间；&nbsp;&nbsp;未租：${adminLoginForm.roomInfoMap.unrentalNum }间；&nbsp;&nbsp;预租：${adminLoginForm.roomInfoMap.bookNum }间；&nbsp;&nbsp;已租：${adminLoginForm.roomInfoMap.rentalNum }间。<br />出租率：<script>document.write(parseFloat(${(adminLoginForm.roomInfoMap.rentalNum+adminLoginForm.roomInfoMap.bookNum)/adminLoginForm.roomInfoMap.totalNum }*100).toFixed(2).toString());</script>%。&nbsp;&nbsp;		
						</td>
						<td width="20%"></td>
						<td width="20%" align="right" nowrap>
							<!-- <iframe src="http://weather.265.com/weather.htm" width="168" height="54" frameborder="no" border="0" marginwidth="0&quoat; marginheight="0" scrolling="no"></iframe>-->
							<!-- <IFRAME ID='ifm2' WIDTH='260' HEIGHT='70' ALIGN='CENTER' MARGINWIDTH='0' MARGINHEIGHT='0' HSPACE='0' VSPACE='0' FRAMEBORDER='0' SCROLLING='NO' src="http://news.sina.com.cn/iframe/weather/130101.html"></iframe>-->
							<!-- <iframe src="http://m.weather.com.cn/m/pn4/weather.htm?id=101090101T " width="160" height="20" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe>-->
							<img  src="../../../resources/images/carLimit/td${adminLoginForm.today }.gif"/>
							<img  src="../../../resources/images/carLimit/td${adminLoginForm.week }.gif"/>
							<img  src="../../../resources/images/carLimit/tm${adminLoginForm.tomorrow }.gif"/>
							<img  src="../../../resources/images/carLimit/tm${adminLoginForm.week1 }.gif"/>
							<!-- <iframe src="http://tianqi.365myt.com/weather/53698.htm" width="168" height="50" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" name="365myt"></iframe>-->
							<!-- <iframe src="http://tianqi.365myt.com/weather.html" width="168" height="50" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" name="365myt"></iframe>-->
							<!-- <iframe src="http://xianxing.911cha.com" width="668" height="50"  marginwidth="0" MARGINHEIGHT="0"   frameborder="1" marginwidth="122" marginheight="330" scrolling="no" name="365myt"></iframe>-->
							
						</td>
					</tr>
				</table>
			</td>
	  	</tr>
	  	<tr>
	    <td height="18"></td>
	  	</tr>
	  	
	  	<tr>
	    	<td class="menu_tab2">
	    		
	    	</td>
		</tr>
	</table>
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
	
	  	
	  	<tr>
	    	<td>
	    		<table width="100%" border="0" cellspacing="0" cellpadding="0">
				    <tr>
				        <td width="14"><img src="../../../resources/images/index_31.jpg" width="14" height="35" /></td>
				        <td class="form_line" nowrap="nowrap">待办任务</td>
				        <td width="5"><img src="../../../resources/images/index_39.jpg" width="5" height="35" /></td>
					</tr>
	    		</table>
	    	</td>
	  	</tr>
	  	<tr>
	    	<td class="menu_tab2">
	    		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			      	<tr align="center">
			        	<td colspan="8">
			        		<table width="100%" border="0" cellpadding="0" align="center" cellspacing="0" class="table_DataArea">
			           		<c:choose>
			          			<c:when test="${!empty adminLoginForm.tipsList}">
			             			<c:forEach items="${adminLoginForm.tipsList}"  var="tips" varStatus="loop" >
				             			<c:if test = "${loop.index%3 == 0}">
				             	<tr class="row0">
				             			</c:if>
									<td align="center">
										<c:if test = "${tips.amount > 0}">
			           						<img height="15px" width="15px" src="../../../resources/images/Alert_04.png"/>
			           					</c:if>
			           					<c:if test = "${tips.amount > 0}">
			           						<a href = "javascript:encode('${ tips.url}');"><font color="red">${tips.amount }</font>${tips.description }&nbsp;</a>
			           					</c:if>
			           					<c:if test = "${tips.amount == 0}">
			           						<a href = "javascript:encode('${ tips.url}');"><font color="red">${tips.amount }</font>${tips.description }&nbsp;</a>
			           					</c:if>
									</td>
									<!-- c:set是用来判断当前的任务数量是否足够填充所有的单元格的，后面的两个if就是来配合这个c:set。 -->
									<c:set var="pa" value="${loop.index}" />
						            </c:forEach>
			           				<c:if test = "${pa%3 eq 0}">
			           					<td align="center"></td>
			           					<td align="center"></td>
			           				</c:if>
			           				<c:if test = "${pa%3 eq 1}">
			           					<td align="center"></td>
			           				</c:if>
				          		</c:when>
				          		<c:otherwise>
			          			<tr>
			          				<td colspan="5" align="center"><font color="red">暂无任务要处理</font></td>
			          			</tr>
			         			</c:otherwise>
			          		</c:choose> 
			        		</table>
			        	</td>
					</tr>
	      			<tr>
			        	<td colspan="8">&nbsp;</td>
			      	</tr>
	    		</table>
	    	</td>
		</tr>
	</table>
	<p>&nbsp;</p>
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="14"><img src="../../../resources/images/index_31.jpg" width="14" height="35" /></td>
	        <td class="form_line" nowrap="nowrap">最新能源统计记录</td>
	        <td width="5"><img src="../../../resources/images/index_39.jpg" width="5" height="35" /></td>
	      </tr>
	    </table></td>
	  </tr>
	  <tr>
	    <td class="menu_tab2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr align="center">
	        <td colspan="8"><table width="100%" border="0" cellpadding="0" align="center" cellspacing="0" class="table_DataArea">
				<tr class="table_caption_gb">
		            <td align="center" nowrap="nowrap">序号</td>            
		            <td align="center" nowrap="nowrap">创建时间</td>			
		            <td align="center" nowrap="nowrap">开始时间</td>
		            <td align="center" nowrap="nowrap">截止时间</td>
		            <td align="center" nowrap="nowrap">用电总量(kw/h)</td>
		            <td align="center" nowrap="nowrap">用水总量(m³)</td>
		            <td align="center" nowrap="nowrap">煤气总量(m³)</td>
		            <td align="center" nowrap="nowrap">操作</td> 
	          	</tr>
				<c:choose>
				<c:when test="${empty adminLoginForm.frontEngList}">
				<tr align="center">
					<td colspan="13" align="center" class="head_form1">
						<font color="red">对不起，没有相应能源信息分析记录!</font>
					</td>
				</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${adminLoginForm.frontEngList}" var="engl" varStatus="vs">
						<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
							<td class="RptTableBodyCellLock" align="center" title="${vs.count }">${vs.count }&nbsp;</td>
							<td class="RptTableBodyCellLock" align="center" title="${engl.createDate }">${engl.createDate }&nbsp;</td>
							<td class="RptTableBodyCell" align="center" title="${engl.beginDate }">${engl.beginDate }&nbsp;</td>
							<td class="RptTableBodyCell" align="center" title="${engl.endDate }">${engl.endDate }&nbsp;</td>
							<td class="RptTableBodyCell" align="center" title="${engl.totalElectricity }">${engl.totalElectricity }&nbsp;</td>
							<td class="RptTableBodyCell" align="center" title="${engl.totalWater }">${engl.totalWater }&nbsp;</td>
							<td class="RptTableBodyCell" align="center" title="${engl.totalGas }">${engl.totalGas }&nbsp;</td>
							<td class="RptTableBodyCell" align="center" title="${engl.totalGas }">
								<input type="button" class="button" value="查看分析图" onclick="seeGraphic(${engl.id })">
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		    </table></td>
	      </tr>
	      <tr>
	        <td colspan="8">&nbsp;</td>
	      </tr>
	    </table></td>
	  </tr>
</table>
  </body>
</html>
