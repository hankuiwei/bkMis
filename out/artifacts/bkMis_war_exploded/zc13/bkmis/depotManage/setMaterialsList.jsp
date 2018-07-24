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
    
    <title>材料设置</title>
    
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		//添加材料设置信息
		function addMaterials(){
			var judgeList = document.getElementById("dmt").value;
			var typeCode = document.getElementById("typeCode").value;
			if( typeCode == "" || typeCode == null){
				alert("请选择要添加的材料类别!");
			}else{
				var s = window.showModalDialog("<%=path%>/setmaterials.do?method=addMaterials&typeCode="+typeCode,window,"dialogWidth=1000px;dialogHeight=500px;resizable=yes;center=1");
				//document.form1.submit();
				//window.close();
				var dmtId = document.getElementById("dmtId").value;
				if(s == "flag"){
					window.location.href("setmaterials.do?method=selectMaterials&Code="+typeCode+"&dmtId="+dmtId);
			 	}
			}
		}
		function onload1(){
			var judgeList = document.getElementById("dmt").value;
			
			var add = document.getElementById("add");
			if(judgeList != 0){
				add.disabled=true;
			}
		
		}
		//编辑材料设置信息
		function editMaterials(){
			var typeCode = document.getElementById("typeCode").value;
			var itemIds = '';
			var chks = document.getElementsByName("childBox");
			var count = 0;
			for(var i = 0;i < chks.length;i++){
					if(chks[i].checked==true){
						count++;
						//alert(count);
						itemIds = chks[i].value;
					}
			}
			if (count == 0) {
				alert("请选择记录进行修改!");
            	return;
            }else if(count == 1){
				var s = window.showModalDialog("<%=path%>/setmaterials.do?method=editMaterials&id="+itemIds+"&typeCode="+typeCode,window,"dialogWidth=1000px;dialogHeight=500px;resizable=yes;center=1");
				//document.form1.submit();
				//window.close();
				//添加修改后让选择类别保存住
				var dmtId = document.getElementById("dmtId").value;
				if(s == "flag"){
					window.location.href("setmaterials.do?method=selectMaterials&Code="+typeCode+"&dmtId="+dmtId);
			 	}
			}else{
				alert("只能选择一条记录");
          		return;
			}
		}
		//复选框的全选
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
		//删除
		function del(){
			var itemIds = '';
			var chkx = document.getElementsByName("childBox");
			for(var i = 0;i<chkx.length;i++){
				if(chkx[i].checked == true){
					itemIds += chkx[i].value + ',';
				}
			}
			if(itemIds.length>0){
				if(!window.confirm("您确定要删除吗?")) {return ;}
				itemIds = itemIds.slice(0,itemIds.length-1);
				document.form1.action="setmaterials.do?method=delMaterials&delId="+itemIds;
				document.form1.submit();
				//window.parent.location.href="setmaterials.do?method=showMaterials";
			}else{
				alert("请选择要删除的记录!");
				return false;
			}
		}
		//查询
		function search(){
			document.form1.action="setmaterials.do?method=selectMaterials";
			document.form1.submit();
		}
		//导出报表
		function explortReport(){
			document.form1.action = "setmaterials.do?method=exportMaterialExcel";
			document.form1.submit();
		}
		//查看详细信息
		function viewDetail(id){
			document.form1.action = "setmaterials.do?method=selectMaterialDetail&id="+id;
			document.form1.submit();
		}
	</script>
  </head>
  
<body onload=onload1();>
<form  method="post" name="form1">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">材料设置列表</td>
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
					            	<td align = "left" nowrap="nowrap">当前类别：
					            		<c:choose>
					            			<c:when test="${empty materialsForm.showName}">全部</c:when>
					            			<c:otherwise>${materialsForm.showName}</c:otherwise>
					            		</c:choose>
					            		<input type="hidden" name="id" id="id"/>
					            		<input type="hidden" name="typeCode" id="typeCode" value="${typeCode }"/>
					            		<input type="hidden" name="dmt" id="dmt" value="${judgeList }"/>
					            	</td>
					            	<td align = "left" nowrap="nowrap">编号</td>
					            	<td nowrap="nowrap">
					            		<input type="text" name="code" id="code" value="${materialsForm.code }"/>
					            		<input type="hidden" name="dmtId" id="dmtId" value="${dmtId }"/>
					            		<input type="hidden" name="type" id="type" value="${typeCode }">
					            	</td>
					            	<td align = "left" nowrap="nowrap">名称：</td>
					            	<td align="left" nowrap="nowrap">
					            		<input type="text" name="name" id="name" value="${materialsForm.name }"/>
					            	</td>
									<td align="right" nowrap="nowrap">
					            		<input type="button" id = "focuson" class="button" value="确定" onclick="search()">
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
												<th class = "RptTableHeadCellFullLock">材料编号</th>
												<th class = "RptTableHeadCellLock">材料名称</th>
												<th class = "RptTableHeadCellLock">规格</th>
												<th class = "RptTableHeadCellLock">单位</th>
												<th class = "RptTableHeadCellLock">单价</th>
												<th class = "RptTableHeadCellLock">库存上限</th>
												<th class = "RptTableHeadCellLock">库存下限</th>
												<th class = "RptTableHeadCellLock">材料类别</th>
											</tr>
											<c:choose>
											<c:when test="${empty materialsForm.materialList}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的材料设置信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${materialsForm.materialList}" var="mf" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';" ondblclick="viewDetail('${mf.id}')">
														<td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="childBox" name="childBox" value="${mf.id}"></td>
														<td class="RptTableBodyCellLock" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
														<td class="RptTableBodyCellLock" align="center" title="${mf.materCode }">${mf.materCode }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.materName }">${mf.materName }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.spec }">${mf.spec }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.codeName }">${mf.codeName }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.codeName }"><script>document.write(formatNum(parseFloat(${mf.unitPrice }).toFixed(2).toString()));</script>&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.upperLimit }">${mf.upperLimit }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.lowerLimit }">${mf.lowerLimit }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${mf.typeName }">${mf.typeName }&nbsp;</td>
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
					                			<td nowrap="nowrap"  align="right"><input class="button" type="button" onclick="addMaterials();" id="add" value="添加"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" onclick="editMaterials();" value="编辑"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="删除" onclick="del()"></td>
					               				<c:choose>
					               				<c:when test="${empty materialsForm.materialList}">
					               				<td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="alert('无记录,不能导出报表!')"></td>
					              				</c:when>
					              				<c:otherwise>
					              				<td nowrap="nowrap"><input type="button" class="button" value="导出报表" onclick="explortReport()"></td>
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
