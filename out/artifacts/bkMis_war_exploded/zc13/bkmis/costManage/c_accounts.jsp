<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>应收账款</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/util.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
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
	<script type="text/javascript">
		/**
		*v表示要转换的值 
		*e表示要保留的位数
		*/
		function round(v,e){ 
			var t=1; 
			for(;e>0;t*=10,e--); 
			for(;e<0;t/=10,e++); 
			return Math.round(v*t)/t; 
		}
	</script>
  </head>
  <!-- 加载页面div -->
	<jsp:include page="../../../loading.jsp"></jsp:include>
  <!-- 加载页面div -->
  <body>
  	<form action="" name="formEdit" method="post" id="formEdit">
    <table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="165" nowrap="nowrap" class="form_line">账单统计</td>
					<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
					<td width="1080" class="form_line2"></td>
					<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
       			</tr>
   			</table>
   		</td>
 	  </tr>
	  <tr>
	  	<td><table width="100%" cellpadding="0" cellspacing="0" class="menu_tab2">
		  	<tr>
		  		<td align=""><table width="100%" cellpadding="0" cellspacing="0">
		  			<tr>
		  				<td class="txt" nowrap="nowrap">
					       <img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					    </td>
		  				<td align="right" nowrap="nowrap" nowrap>客户名称：</td>
		  				<td><input type="text" name="clientName" value="${CBillForm.clientName }" style="width:150px;">
		  				</td>
		  				<td align="right" nowrap>单据状态：</td>
		  				<td colspan="">
		  					<select name="status">
		  						<option value="">全部</option>
		  						<option value="0" <c:if test="${CBillForm.status=='0' }">selected</c:if>>未缴</option>
		  						<option value="1" <c:if test="${CBillForm.status=='1' }">selected</c:if>>已缴</option>
		  					</select>
		  				</td>
		  				<td align="right" nowrap="nowrap" nowrap>收费日期：</td>
		  				<td>
		  					<input type="text" name="begin" value="${CBillForm.begin }" style="width:80px;" readonly onclick="WdatePicker();" class="Wdate">
		  					至
		  					<input type="text" name="end" value="${CBillForm.end }" style="width:80px;" readonly onclick="WdatePicker();" class="Wdate">
		  				</td>
		  				<td>
		  					支票号：<input type="text" name="chequeNo" value="${CBillForm.chequeNo }" style="width:100px;">
		  				</td>
		  			</tr>
		  			<tr>
		  				<td></td>
		  				<td align="right" nowrap>收费项目：</td>
		  				<td colspan="">
		  					<select name="itemIds">
		  					<option value="">全部</option>
		  					<c:forEach items="${itemList}" var="v">
								<option value="${v.id }" <c:if test="${v.id==itemIds }">selected</c:if>>${v.itemName }</option>		  						
		  					</c:forEach>
		  					<c:if test="${ysfzANDfz ne ''}">
		  						<option value="${ysfzANDfz }" <c:if test="${ysfzANDfz eq itemIds }">selected</c:if>>房租与预收房租</option>
		  					</c:if>
		  					</select>
		  					
		  				</td>
		  				<td align="right" nowrap>付款方式：</td>
		  				<td colspan="">
    				 		<select name="payType">
    				 			<option value="">全部</option>
    				 			<option value="0" <c:if test="${payType=='0' }">selected</c:if> >现金</option>
    				 			<option value="1" <c:if test="${payType=='1' }">selected</c:if> >支票</option>
    				 			<option value="2" <c:if test="${payType=='2' }">selected</c:if> >转账</option>
    				 			<option value="3" <c:if test="${payType=='3' }">selected</c:if> >刷卡</option>
    				 			<option value="4" <c:if test="${payType=='4' }">selected</c:if> >定金转押金</option>
    				 			<option value="5" <c:if test="${payType=='5' }">selected</c:if> >定金转预收房租</option>
    				 		</select>
    				 	</td>
		  				<td align="right" nowrap="nowrap" nowrap>账期：</td>
		  				<td nowrap="nowrap">
		  					<input type="text" name="before" value="${CBillForm.before }" style="width:100px;" readonly onclick="WdatePicker({dateFmt:'yyyy-MM'});" />
		  					<input type="text" name="after" value="${CBillForm.after }" style="width:100px;" readonly onclick="WdatePicker({dateFmt:'yyyy-MM'});" />
		  				</td>
		  				<td align="right" nowrap >
		  					<input type="button" value="查询" onclick="cx()" class="button">
		  					<input type="button" value="打印" onclick="print()" class="button">
		  					<input type="button" value="导出报表" onclick="exportExcel('${size }')" class="button">
		  				</td>
		  			</tr>
		  			
		  		</table></td>		
		  	</tr>
		  	<tr height="70%">
			    <td valign = "top">
			    	<!-- 表单内容区域 -->
					<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height = "95%">
			        		<td width="100%">
						  		<div id="div1" class="RptDiv"  >
		  			<table width="100%"  cellpadding="0" cellspacing="0" class="">
			    	<tr>
			    		<th class="RptTableHeadCellFullLock" width="6%"><input type="checkbox" name="checkboxAll" value="" onclick="checkAll(this,'checkbox')"></th>
			    		<th class="RptTableHeadCellFullLock" width="6%">序号</th>
			    		<th class="RptTableHeadCellFullLock" width="8%">客户名称</th>
			    		<th class="RptTableHeadCellFullLock" width="8%">收费项</th>
			    		<th class="RptTableHeadCellLock" width="8%">付款方式</th>
			    		<th class="RptTableHeadCellLock" width="8%">房号</th>
			    		<th class="RptTableHeadCellLock" width="8%">申请单号</th>
			    		<th class="RptTableHeadCellLock" width="8%">支票号</th>
			    		<th class="RptTableHeadCellLock"  width="8%">账单月份</th>
			    		<th class="RptTableHeadCellLock"  width="8%">单据状态</th>
			    		<!--<th class="RptTableHeadCellLock"  width="8%">开始日期</th>
			    		<th class="RptTableHeadCellLock"  width="8%">结束日期</th>-->
			    		<th class="RptTableHeadCellLock"  width="8%">最后缴款日期</th>
			    		<th class="RptTableHeadCellLock"  width="8%">合同金额</th>
			    		<th class="RptTableHeadCellLock"  width="8%">滞纳金额</th>
			    		<th class="RptTableHeadCellLock"  width="8%">收费日期</th>
			    		<th class="RptTableHeadCellLock"  width="8%">实收金额</th>
			    		<th class="RptTableHeadCellLock"  width="8%">录入人</th>
			    	</tr>
			    	<c:choose>
			    		<c:when test="${empty list}">
			    			<tr align="center">
								<td colspan="14" align="center" class="head_form1">
									<font color="red">没有信息!</font>
								</td>
							</tr>
			    		</c:when>
			    		<c:otherwise>
			    			<c:forEach items="${list}" var="v" varStatus="vs">
			    				<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
									<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value="${v.id }"></td>
						    		<td class="RptTableBodyCellLock"  align="center">${vs.count }</td>
						    		<td class="RptTableBodyCellLock">&nbsp;${v.compactClient.name }</td>
						    		<td class="RptTableBodyCellLock">&nbsp;
										<c:choose>
						    				<c:when test="${!empty v.itemId}">
						    					<c:forEach items="${itemList}" var="a">
							    					<c:if test="${v.itemId==a.id }">${a.itemName}</c:if>
							    				</c:forEach>
						    				</c:when>
							    			<c:otherwise>
							    				${v.standardName }
							    			</c:otherwise>	
						    			</c:choose>
									</td>
						    		<td class="RptTableBodyCell">&nbsp;
											<c:if test="${v.paymentWay=='0' }" >现金</c:if>
							    			<c:if test="${v.paymentWay=='1' }" >支票</c:if>
							    			<c:if test="${v.paymentWay=='2' }" >转账</c:if>
							    			<c:if test="${v.paymentWay=='3' }" >刷卡</c:if>
							    			<c:if test="${v.paymentWay=='4' }" >定金转押金</c:if>
							    			<c:if test="${v.paymentWay=='5' }" >定金转预收房租</c:if>
									</td>
						    		<td class="RptTableBodyCell">&nbsp;${v.ERooms.roomCode }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v.billCode }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v.chequeNo }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v.billDate }</td>
						    		<td class="RptTableBodyCell">&nbsp;<c:if test="${v.status=='0'}">未缴</c:if><c:if test="${v.status=='1'}">已缴</c:if></td>
						    		<!--<td class="RptTableBodyCell">&nbsp;${v.startDate }</td>
						    		<td class="RptTableBodyCell">&nbsp;${v.endDate }</td>-->
						    		<td class="RptTableBodyCell">&nbsp;${v.closeDate }</td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(round(${v.billsExpenses },2)));</script></td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(round(${v.delayingExpenses },2)));</script></td>
						    		<td class="RptTableBodyCell">&nbsp;${v.collectionDate }</td>
						    		<td class="RptTableBodyCell">&nbsp;<script>document.write(formatNum(round(${v.actuallyPaid },2)));</script></td>
						    		<td class="RptTableBodyCell">&nbsp;
						    			<c:forEach items="${usersList}" var="a">
							    			<c:if test="${v.entryUserId==a.userid }">${a.realName }(${a.username })</c:if>
							    		</c:forEach>
						    		</td>
						    	</tr>
			    			</c:forEach>
			    		</c:otherwise>
			    	</c:choose>
			    	</table>
			    				</div>
							</td>
		    			</tr>
		    		</table>
		    	</td>
		    </tr>
		    <tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr><td class="form_line3">&nbsp;</td>
							<td class="form_line3">&nbsp;${pageHTML }</td>
							<td class="form_line3">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right" style="padding-left: 3px;">
								实收总金额：</td><td>（<script>document.write(formatNum(round(${totalAmount },2)));</script>），</td>
							<td align="right">合同金额：</td><td>（<script>document.write(formatNum(round(${billAmount },2)));</script>），</td>
							<td align="right">定金转房租押金：</td><td>（<script>document.write(formatNum(round(${toyj },2)));</script>），</td>
							<td align="right">定金转预收房租：</td><td>（<script>document.write(formatNum(round(${tofz },2)));</script>）， </td>
							<td align="right">预收房租：</td><td>（<script>document.write(formatNum(round(${adv },2)));</script>）
						</tr>
						<tr>
							<td align="right" style="padding-left: 3px;">
								预收房租余额：</td><td>（<script>document.write(formatNum(round(${CBillForm.advance.amount },2)));</script>），</td>
							<td align="right">房租押金：</td><td>（<script>document.write(formatNum(round(${fzdes },2)));</script>），</td>
							<td align="right">装修押金：</td><td>（<script>document.write(formatNum(round(${zxdes },2)));</script>），</td>
							<td align="right">暂存款：</td><td>（<script>document.write(formatNum(round(${tem },2)));</script>），</td>
							<td align="right">定金：</td><td>（<script>document.write(formatNum(round(${ear },2)));</script>） </td>
							
						</tr>
						<tr>
							<td  align="right" style="padding-left: 3px;">
								现金：</td><td>（<script>document.write(formatNum(round(${xj },2)));</script>），</td>
							<td align="right">支票：</td><td>（<script>document.write(formatNum(round(${zp },2)));</script>），</td>
							<td align="right">转账：</td><td>（<script>document.write(formatNum(round(${zz },2)));</script>），</td>
							<td align="right">刷卡：</td><td>（<script>document.write(formatNum(round(${sk },2)));</script>） </td>
							<td>&nbsp;</td>
						</tr>
						
					</table>
				</td>
			</tr>
			<!--<tr>
				<td align="">
					实收总金额：（${totalAmount }），合同金额（${billAmount }），预收房租：（${adv }），押金（${des }），暂存款（${tem }），定金（${ear }），现金(${xj })，支票(${zp })，转账(${zz })，刷卡(${sk })，定金转押金(${toyj })，定金转预收房租(${tofz })
				</td>
			</tr>-->
	     	</table></td>
	    </tr>
	</table>
	</form>
  </body>
  <script type="text/javascript">
  	function cx(){
  		document.forms[0].action = "<%=path%>/bill.do?method=getList";
  		document.forms[0].target = "_self";
  		document.forms[0].submit();
  		setTimeout("showLoadingDiv()",100);
  	}
  	function print(){
  		document.forms[0].action = "<%=path%>/bill.do?method=getList&flag=print";
  		document.forms[0].target = "_blank";
  		document.forms[0].submit();
  		//setTimeout("showLoadingDiv()",100);
  	}
  	function del(){
  		if(!checked()){
  			alert("请选择要删除的信息！");
  			return false;
  		}
  		document.forms[0].action = "<%=path%>/bill.do?method=delete";
  		document.forms[0].target = "_self";
  		document.forms[0].submit();
  	}
  	function checked(){
  		var checkbox = document.getElementsByName("checkbox");
  		for(var i=0;i<checkbox.length;i++){
  			if(checkbox[i].checked){
  				return true;
  				break;
  			}
  		}
  		return false;
  	}
  	function exportExcel(isNull){
      		if(isNull!='0') {
				document.forms[0].action = "<%=path%>/bill.do?method=exportExcel";
				document.forms[0].target = "_self";
				document.forms[0].submit();
			} else {
				alert("没有记录可导出！");
			}
      }
  </script>
</html>
