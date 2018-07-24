<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
<head>
	<title>楼幢信息列表</title>
	
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
	
	<script type="text/javascript">
	 //按照楼盘名称和楼栋名称查询对应的楼栋
      function searchByLpName(){
          document.buildform.action = "<%=path%>/build.do?method=getBuildsList";
          document.buildform.submit();
      }
      //添加
      function addBuild(){
         var lpId = document.getElementById("lpId").value;
         
         //window.showModalDialog("<%=path%>/build.do?method=goAddBuild&lpName=" + encodeURI(encodeURI(lpName)),"","dialogWidth=800px;dialogHeight=600px;");
         var a = window.showModalDialog("<%=path%>/build.do?method=goAddBuild&lpId=" + lpId,"","dialogWidth=800px;dialogHeight=600px;");
         if(a=="flag"){
      		alert("添加成功！");
         	document.buildform.submit();
         }
         
      }
      //修改
      function modifyBuild(){
         var e = document.getElementsByName("buildId");
         var count = 0;
         var buildId;
         for(var i=0;i<e.length;i++){
            if(e[i].checked && e[i].type == "checkbox"){
               count++;
               buildId = e[i].value;
            }
         }
        if(count == 0){
            alert("请选择记录进行修改!");
            return;
        }else if(count == 1){
            var a = window.showModalDialog("<%=path%>/build.do?method=getModifyInfo&buildId="+buildId,"","dialogWidth=800px;dialogHeight=600px;");
             if(a=="flag"){
           		alert("修改成功！");
	            document.buildform.action = "<%=path%>/build.do?method=getBuildsList";
	            document.buildform.submit();
	         }
        }else{
           alert("只能选择一条记录");
           return;
        }
      }
      //删除
      function deleteBuild(){
         var temp = document.getElementsByName("buildId"); 
         var count = 0;
         for(var i=0;i<temp.length;i++){
             if(temp[0].type == "checkbox" && temp[i].checked){
                 count++;
             }
         }
         if(count == 0){
            alert("请至少选择一条记录进行删除！");
            return;
         }else{
            if(confirm("确定要删除吗?")){
              document.buildform.action = "<%=path%>/build.do?method=deleteBuild";
              document.buildform.target = "_self";
              document.buildform.submit();
            }
         }
      }
      //选中所有的checkbox
      var flag = "false";
      function selectedAll(){
         var list = document.getElementsByName("buildId");
         if(flag == "false"){
            for(var i=0;i<list.length;i++){
             list[i].checked = true;
          }
            flag = "true";
        }else{
           for(var i=0;i<list.length;i++){
              list[i].checked = false;
           }
           flag = "false";
        }
      }
      //导出楼栋报表
      function exportLr(isNull){
      		if(isNull!='0') {
				document.forms[0].action = "<%=path%>/build.do?method=exportBuildExcel";
				document.forms[0].submit();
			} else {
				alert("没有记录可导出");
			}
      }
    </script>
</head>
<body>
<form  method="post" name="buildform">
	<input type="hidden" value="${buildForm.lpId}" name="lpId">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">楼栋信息列表</td>
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
									<td nowrap="nowrap" align="right">楼幢名称：</td>
									<td class="txt"><input type="text" id=buildName name="buildName" value="${buildName }"></td>
									<td align="right">
					            		<input type="button" id = "focuson" onclick="searchByLpName();" class="button" value="确定">
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
								                <th class = "RptTableHeadCellFullLock"><input type="checkbox" onclick="selectedAll();"></th>
												<th class = "RptTableHeadCellFullLock">序号</th>
												<th class = "RptTableHeadCellFullLock">楼幢编号</th>
												<th class = "RptTableHeadCellLock">楼幢名称</th>
												<th class = "RptTableHeadCellLock">使用面积</th>
												<th class = "RptTableHeadCellLock">楼层数</th>
												<th class = "RptTableHeadCellLock">区块</th>
												<th class = "RptTableHeadCellLock">用途</th>
												<th class = "RptTableHeadCellLock">结构</th>
												<th class = "RptTableHeadCellLock">开工日期</th>
												<th class = "RptTableHeadCellLock">竣工日期</th>
											</tr>
											<c:choose>
											<c:when test="${empty buildslist}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，暂时没有符合条件的楼栋信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${buildslist}" var="b" varStatus="vs">
														<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
														<td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="buildId" name="buildId" value="${b.buildId}"></td>
														<td class = "RptTableBodyCellLock" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
														<td class = "RptTableBodyCellLock" align="center" title="${b.buildCode}">${b.buildCode}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${b.buildName}">${b.buildName}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${b.useArea }">${b.useArea }&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${b.floor}">${b.floor}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${b.block}">${b.block}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${b.purpose}">${b.purpose}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="${b.structure}">${b.structure}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="开工日期">&nbsp;${b.beginDate}&nbsp;</td>
														<td class="RptTableBodyCell" align="center" title="竣工日期">&nbsp;${b.endDate}&nbsp;</td>
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
					                			<td nowrap="nowrap"  align="right"><input class="button" onclick="addBuild();" type="button" value="添加"></td>
					               				<td nowrap="nowrap"><input type="button" onclick="modifyBuild();" class="button" value="编辑"></td>
					               				<td nowrap="nowrap"><input type="button" onclick="deleteBuild();" class="button" value="删除"></td>
					               				<td nowrap="nowrap"><input type="button" onclick="exportLr('${size}');" class="button" value="导出报表"></td>
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

