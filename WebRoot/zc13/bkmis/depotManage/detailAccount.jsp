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
    <base target="_self"></base>
    <title>详细库存盘点表</title>
    
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript">
		//复选框的全选
		function selectedAll(){
			var parentBox = document.getElementById("parentBox");
			var childBox = document.getElementsByName("childBox");
			if(parentBox.checked == true){
				for(var i = 0;i<childBox.length;i++){
					childBox[i].checked=true;
				}
			}else{
				for(var i = 0;i<childBox.length;i++){
					childBox[i].checked=false;
				}
			}
		}
		
		//查询
		function cx(){
			document.actionForm.action="<%=path%>/accountDepot.do?method=detailAccount&editId=${editId}";
			//document.actionForm.target="_self";
			document.actionForm.submit();
		}
		
		//打印
		function printKc(){
			//var name = document.getElementById("cx_name").value;
			//var code = document.getElementById("cx_code").value;
			document.actionForm.action="<%=path%>/accountDepot.do?method=printAccountDetail&editId=${editId}";
			document.actionForm.target="_blank";
			document.actionForm.submit();
			//window.open("<%=path%>/accountDepot.do?method=printAccountDetail&editId=${editId}&cx_name="+name+"&cx_code="+code);
		}
		
		//导出报表
		function exportAccountDetail(){
			document.actionForm.action="<%=path%>/accountDepot.do?method=exportExcel&editId=${editId}";
			document.actionForm.target="_self";
			document.actionForm.submit();
		}
	</script>
  </head>
  
  <body style="">
    <form action="" name="actionForm" id="" method="post">
    	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">库存盘点信息列表</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<c:forEach items="${list}" var="bl">
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
									<td colspan="4" align="center"><font size="12">材料出入库核算表明细</font></td>
								</tr>
					         	<tr>
					         		<td colspan="4" align="left" class="head_one">公司库存盘点表</td>
					         	</tr>
					         	
					          	<tr>
					            	<td align="right" class="head_left">
					            		统计日期：
					            	</td>
					            	<td class="head_form1">&nbsp;
					            		<input type="text" name="beginDate" id="beginDate" readonly class="Wdate" value="${bl.beginDate }" disabled="disabled"/>
										&nbsp;至&nbsp;
										<input type="text" name="endDate" id="endDate" readonly class="Wdate" value="${bl.endDate }" disabled="disabled"/>
					            	</td>
					            	<td align="right" class="head_form1">
					            		账期：
					            	</td>
					            	<td class="head_form1">&nbsp;
					            		<input type="text" id="" value="${bl.year }" readonly="readonly" disabled="disabled"/>年
					            		<input type="text" id="" value="${bl.month }" readonly="readonly" disabled="disabled"/>月
					            	</td>
								</tr>
								<tr>
					            	<td align="right" class="head_left">
					            		材料名称：<input type="text" name="cx_name" id="cx_name"  value="${accountForm.cx_name }" />
					            	</td>
					            	<td align="right" class="head_form1">
					            		材料编号：<input type="text" name="cx_code" id="cx_code"  value="${accountForm.cx_code }" />
					            	</td>
					            	<td class="head_form1" colspan="2">&nbsp;
					            		<input type="button" name="" value="查询" class="button" onclick="cx();"/>
					            	</td>
								</tr>
							 	 <tr>
					           		 <td height="10" colspan="4"></td>
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
												<th class = "RptTableHeadCellFullLock">材料名称</th>
												<th class = "RptTableHeadCellLock">材料编号</th>
												<th class = "RptTableHeadCellLock">单位</th>
												<th class = "RptTableHeadCellLock">规格型号</th>
												<th class = "RptTableHeadCellLock">单价</th>
												<th class = "RptTableHeadCellLock">期初数量</th>
												<th class = "RptTableHeadCellLock">期初金额</th>
												<th class = "RptTableHeadCellLock">本期入库数量</th>
												<th class = "RptTableHeadCellLock">本期入库金额</th>
												<th class = "RptTableHeadCellLock">本期出库数量</th>
												<th class = "RptTableHeadCellLock">本期出库金额</th>
												<th class = "RptTableHeadCellLock">期末数量</th>
												<th class = "RptTableHeadCellLock">期末金额</th>
											</tr>
											<c:set var="beginMoney" value="0" ></c:set>
											<c:set var="inputMomey" value="0" ></c:set>
											<c:set var="outputMoney" value="0" ></c:set>
											<c:set var="balance" value="0" ></c:set>
											<c:choose>
											<c:when test="${empty accountForm.detailAccountList}">
											<tr align="center">
												<td colspan="15" align="center" class="head_form1">
													<font color="red">对不起，没有相应的详细信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${accountForm.detailAccountList}" var="dl" varStatus="vs">
													<c:set var="beginMoney" value="${dl.qiMoney+beginMoney }" ></c:set>
													<c:set var="inputMomey" value="${dl.benInMoney+inputMomey }" ></c:set>
													<c:set var="outputMoney" value="${dl.benOutMoney+outputMoney }" ></c:set>
													<c:set var="balance" value="${dl.balance+balance }" ></c:set>
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
														<td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="childBox" name="childBox" value="${vs.count }"></td>
														<td class="RptTableBodyCellLock" align="center" title="${vs.count }">${vs.count }&nbsp;</td>
														<td class="RptTableBodyCellLock" align="center" title="${dl.name }">${dl.name }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.code }">${dl.code }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.unit }">${dl.unit }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.spec }">${dl.spec }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.unitPrice }"><script>document.write(formatNum(parseFloat(${dl.unitPrice }).toFixed(2).toString()));</script>&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.qiAmount }">${dl.qiAmount }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.qiMoney }"><script>document.write(formatNum(parseFloat(${dl.qiMoney }).toFixed(2).toString()));</script>&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.benInAmount }">${dl.benInAmount }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.benInMoney }"><script>document.write(formatNum(parseFloat(${dl.benInMoney }).toFixed(2).toString()));</script>&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.benOutAmount }">${dl.benOutAmount }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.benOutMoney }"><script>document.write(formatNum(parseFloat(${dl.benOutMoney }).toFixed(2).toString()));</script>&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.residue }">${dl.residue }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${dl.balance }"><script>document.write(formatNum(parseFloat(${dl.balance }).toFixed(2).toString()));</script>&nbsp;</td>
													</tr>
												</c:forEach>
											</c:otherwise>
										</c:choose>
					             		</table>
					             		</div>
									</td>
		     		 			</tr>
		     		 			<tr>
		     		 				<td>合计：
		     		 					期初金额:<script>document.write(formatNum(parseFloat(${beginMoney }).toFixed(2).toString()));</script>&nbsp;&nbsp;
		     		 					本期入库金额:<script>document.write(formatNum(parseFloat(${inputMomey }).toFixed(2).toString()));</script>&nbsp;&nbsp;
		     		 					本期出库金额:<script>document.write(formatNum(parseFloat(${outputMoney }).toFixed(2).toString()));</script>&nbsp;&nbsp;
		     		 					本期结余金额:<script>document.write(formatNum(parseFloat(${balance }).toFixed(2).toString()));</script>
		     		 				</td>
		     		 			</tr>
		     		 			<tr>
		     		 				<td align="center">
		     		 					<input type="button" name="" value="返回" class="button" onclick="javasript:history.back();"/>
		     		 					<input type="button" name="" value="打印" class="button" onclick="javasript:printKc()"/>
		     		 					<input type="button" name="" value="导出报表" class="button" onclick="javasript:exportAccountDetail()"/>
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
							</table>
    					</td>
  					</tr>
				</table>
			</td>
		</tr>
		</c:forEach>
	</table>
    </form>
  </body>
</html>
