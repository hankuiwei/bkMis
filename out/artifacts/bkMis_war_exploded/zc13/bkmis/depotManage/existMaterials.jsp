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
    
    <title>材料库存</title>
    
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		/*function addMaterials(){
			 var s = window.showModalDialog("zc13/bkmis/depotManage/addInputMaterials.jsp",window,"dialogWidth=1000px;dialogHeight=900px;resizable=yes;center=1");
			 window.close();
		}*/
		//查询
		function Search(){
			document.form1.action="existdepot.do?method=selectExistMaterials";
			document.form1.submit();
		}
		//导出报表
		function explortReport(){
			document.form1.action = "existdepot.do?method=exportExistDepotExcel";
			document.form1.submit();
		}
		//全选
		function selectedAll(){
			var parentBox = document.getElementById("parentBox");
			var childBox = document.getElementsByName("childbox");
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
		//查看详细信息
		function viewDetail(id){
			document.form1.action = "setmaterials.do?method=selectMaterialDetail&id="+id;
			document.form1.submit();
		}
		
		//验证双精度数字
		function checkDouble(obj,obj_name, oldValue){
			var reg = /^[0-9]+(\.[0-9]+)?$/;
			if(obj.value!=""&&!reg.test(obj.value)){
		 		alert(obj_name+'所填必须为有效的双精度数字');
		 		obj.value = oldValue;
				obj.focus();
		 		return false;
			}
		}
	</script>
  </head>
  
  <body style="" onLoad="hideLoadingDiv();">
    <form action="" method="post" name="form1" id="form1">
    	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">库存信息列表</td>
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
					            	<td align = "left" nowrap="nowrap">材料名称：
					            		<input type="text" id="name" name="name" value ="${materialsForm.name }">
					            	</td>
					            	<td align = "left" nowrap="nowrap">材料编号：
					            		<input type="text" id="code" name="code" value ="${materialsForm.code }">
					            		<input type="hidden" id="type" name="type" value="${typeCode }">
					            		<input type="hidden" id="dmtId" name="dmtId" value="${dmtId }">
					            	</td>
					            	<td align = "left" nowrap="nowrap">库存：
					            		<input type="text" id="nowStockStart" onchange="checkDouble(this,'库存', '${materialsForm.nowStockStart }')" name="nowStockStart" value ="${materialsForm.nowStockStart }" style="width:60px">至
					            		<input type="text" id="nowStockEnd"  onchange="checkDouble(this,'库存', '${materialsForm.nowStockEnd }')" name="nowStockEnd" value ="${materialsForm.nowStockEnd }" style="width:60px">
					            	</td>
									<td align="right" nowrap="nowrap">
					            		<input type="button" id = "focuson" onclick="Search();" class="button" value="确定">
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
								                <th class = "RptTableHeadCellFullLock"><input type="checkbox" name="parentBox" onclick="selectedAll();"></th>
												<th class = "RptTableHeadCellFullLock">序号</th>
												<th class = "RptTableHeadCellFullLock">材料名称</th>
												<th class = "RptTableHeadCellLock">材料编号</th>
												<th class = "RptTableHeadCellLock">库存上限</th>
												<th class = "RptTableHeadCellLock">库存下限</th>
												<th class = "RptTableHeadCellLock">当前库存</th>
												<th class = "RptTableHeadCellLock">单位</th>
												<th class = "RptTableHeadCellLock">单价</th>
												<th class = "RptTableHeadCellLock">库存金额</th>
												<th class = "RptTableHeadCellLock">可用库存</th>
											</tr>
											<c:choose>
											<c:when test="${empty materialsForm.materialList}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有相应的库存信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${materialsForm.materialList}" var="mf" varStatus="vs">
														<td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="childBox" name="childBox" value="${mf.id}"></td>
														<td class="RptTableBodyCellLock" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
														<td class="RptTableBodyCellLock" align="center" title="${mf.materName }">${mf.materName }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.materCode }">${mf.materCode }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.upperLimit }">${mf.upperLimit }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.lowerLimit }">${mf.lowerLimit }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.nowStock }">${mf.nowStock }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.codeName }">${mf.codeName }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.unitPrice }"><c:if test="${mf.nowStock <= 0}">0</c:if><c:if test="${mf.nowStock > 0}"><script>document.write(formatNum(parseFloat(${mf.unitPrice }).toFixed(2).toString()));</script></c:if> &nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.money }"><script>document.write(formatNum(parseFloat(${mf.money }).toFixed(2).toString()));</script>&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.doStock }">${mf.doStock }&nbsp;</td>
													</tr>
												</c:forEach>
											</c:otherwise>
										</c:choose>
					             		</table>
					             		</div>
									</td>
		     		 			</tr>
		     		 			<tr>
		     		 				<td>
		     		 					库存金额：<script>document.write(formatNum(parseFloat(${sum }).toFixed(2).toString()));</script>
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
					               				<td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="explortReport()"></td>
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
