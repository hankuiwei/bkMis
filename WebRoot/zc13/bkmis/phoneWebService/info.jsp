<%@ page language="java"  import="com.zc13.util.*"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String message = GlobalMethod.NullToSpace((String)request.getAttribute("message"));
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
	<%if(!message.equals("")){%>
		alert("<%=message%>");
	<%}%>
	</script>
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
	
		function checkAll(obj){
			var arrrad1 = document.getElementsByName("checkbox");	
			if(obj.checked){
				for(var i=0;i<arrrad1.length;i++){
					arrrad1[i].checked="checked";
				}
			}else{
				for(var i=0;i<arrrad1.length;i++){
					arrrad1[i].checked="";
				}
			}		
		}
		
		//添加
		function add(){
			var url = "<%=path%>/phoneCost.do?method=toEditCallInfo&cxPhoneNumber=${phoneCostForm.cxPhoneNumber}";
			var options = "dialogWidth:600px;dialogHeight:400px;status:no;scroll:no;";
			var win = window.showModalDialog(url, this.window, options);
			if(win=="flag"){
				//window.location=window.location;
				query();
			}
		}
	
		function edit(){
			var checkboxs = document.getElementsByName("checkbox");
			var count = 0;
			var id;
			for(var i=0;i<checkboxs.length;i++){
				if(checkboxs[i].checked){
					count++;
					id = checkboxs[i].value;
				}
			}
			if(count!=1){
				alert("请选择一条需要编辑的信息！");
				return;
			}
			var url = "<%=path%>/phoneCost.do?method=toEditCallInfo&cxPhoneNumber=${phoneCostForm.cxPhoneNumber}&id="+id;
			var options = "dialogWidth:600px;dialogHeight:400px;status:no;scroll:no;";
			var win = window.showModalDialog(url, this.window, options);
			if(win=="flag"){
				//window.location=window.location;
				query();
			}
		}
	
		//删除
		function del(){
			if(window.confirm("您确定要删除吗？")){
				document.actionForm.action="<%=path%>/phoneCost.do?method=deleteCallInfo";
				document.actionForm.target = "_self";
				document.actionForm.submit();
			}
		}
	
      	//按名称查询
      	function query(){
         	document.actionForm.action = "<%=path%>/phoneCost.do?method=getPhoneCostInfoDetails";
         	document.actionForm.target = "_self";
         	document.actionForm.submit();
      	}
      	//导出报表
      	function exportLr(){
      		document.actionForm.action = "<%=path%>/phoneCost.do?method=exportPhoneCostInfoDetails";
      		document.actionForm.target = "_self";
         	document.actionForm.submit();
      	}
      	
      	//打印
      	function printLr(){
      		document.actionForm.action = "<%=path%>/phoneCost.do?method=printPhoneCostInfoDetails";
      		document.actionForm.target = "_blank";
         	document.actionForm.submit();
      	}
      	
    </script>
</head>
<body onunload="window.opener.location.href=window.opener.location.href">
<form  method="post" name="actionForm" action = "">
	<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
	<input type="hidden" name="cxPhoneNumber" value="${phoneCostForm.cxPhoneNumber }" />
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">${phoneCostForm.cxPhoneNumber }电话费用详细</td>
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
					            	<td align = "left">日期：
										<input type="text" name="cxStartDate" id="cxStartDate" readonly onclick="WdatePicker()" value="${phoneCostForm.cxStartDate }" class="Wdate"/>
										&nbsp;至&nbsp;
										<input type="text" name="cxEndDate" id="cxEndDate" readonly onclick="WdatePicker()" value="${phoneCostForm.cxEndDate }" class="Wdate"/>
									</td>
									<td align = "left">缴费情况：
										<select name="cxIsPaid">
											<option value="">所有</option>
											<option value="已缴" <c:if test="${phoneCostForm.cxIsPaid eq '已缴' }">selected</c:if> >已缴</option>
											<option value="未缴" <c:if test="${phoneCostForm.cxIsPaid eq '未缴' }">selected</c:if> >未缴</option>
										</select>
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
								<tr height = "95%">
					        		<td width="100%">
					        		<div id = "div1" class = "RptDiv"  >
							   			<table class = "RptTable" border="0" cellpadding="0" cellspacing="0" id = "table1">
					              			<tr >
					              				<th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this)">
								                </th>
												<th width = "10%" class = "RptTableHeadCellFullLock">序号</th>
												<th width = "10%" class = "RptTableHeadCellLock">通话开始时间</th>
												<th width = "10%" class = "RptTableHeadCellLock">通话结束时间</th>
												<th width = "10%" class = "RptTableHeadCellLock">通话时长</th>
												<th width = "5%" class = "RptTableHeadCellLock">通话类型</th>
												<th width = "15%" class = "RptTableHeadCellLock">地区名称</th>
												<th width = "15%" class = "RptTableHeadCellLock">对方号码</th>
												<th width = "15%" class = "RptTableHeadCellLock">本次通话费用（￥）</th>
												<th width = "15%" class = "RptTableHeadCellLock">缴费情况</th>
											</tr>
											<c:choose>
											<c:when test="${empty phoneCostForm.phoneCallInfoList}">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${phoneCostForm.phoneCallInfoList}" var="v" varStatus="vs">
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
														<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value="${v.id }"></td>
														<td class="RptTableBodyCell" align="center" title="${vs.count}">${vs.count}</td>
														<td class="RptTableBodyCell" align="center">${v.startTime }</td>
														<td class="RptTableBodyCell" align="center">${v.endTime }</td>
														<td class="RptTableBodyCell" align="center"><script>document.write(formatSeconds(${v.callTime }))</script></td>
														<td class="RptTableBodyCell" align="center">${v.callType }</td>
														<td class="RptTableBodyCell" align="center">${v.areaName }</td>
														<td class="RptTableBodyCell" align="center">${v.phone }</td>
														<td class="RptTableBodyCell" align="center">${v.cost }</td>
														<td class="RptTableBodyCell" align="center">${v.isPaid }</td>
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
					               				<td nowrap="nowrap"><input type="button" onclick="add();" class="button" value="新增"></td>
					               				<td nowrap="nowrap"><input type="button" onclick="edit();" class="button" value="修改"></td>
					               				<td nowrap="nowrap"><input type="button" onclick="del();" class="button" value="删除"></td>
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
	<script language=javascript>
    function test(elem) {
        var rtn = eval('VS_FV_' + elem.format + '()');
        if (rtn.length > 0) {
            alert(rtn);
            return false;
        } else {
            return true;
        }
    }
   
     function doCellEdit(cellobj, newtext, column) {
       // var s = column.split(";");
        /*var type=null;
        //alert(column);
        if (newtext != "&nbsp;" && newtext!='' && newtext!=null) {
        	//alert("newtext="+newtext);
            try {
                var isValidated = true;
                VS_FV_Validator.Resource = elem = cellobj.childNodes[0];
                
				//alert(type);
                //elem.field = s[0];
                //elem.format = "NumberZ";
                if (isValidated && test(elem) == false) {
                    return false;
                }
            } catch(e) {
            }
            if (newtext == 0) {
                alert("值不允许为零");
                return false;
            }
        } else {
            newtext = "newtext";
        }
        //alert(newtext);
        var rowObj = getRowObjByInnerObj(cellobj);
        var jh = rowObj.childNodes[0].innerText;
        var ajax = new AjaxObject();
        ajax.addParameter("keyValue", jh);
        ajax.addParameter("keyField", "jh");
        ajax.addParameter("field", s[1]);
        ajax.addParameter("shyj", s[2]);//审核意见
        ajax.addParameter("value", newtext);
        ajax.addParameter("shbz", "sh");//以此判断是否处理的是审核页面
        ajax.addParameter("type", type);
        var rtn = ajax.getResponseText("/public/updateDjxx.jsp");
        var isEdited=/true/gi.test(rtn);
        */
        //if(isEdited==true){
        	cellobj.style.backgroundColor="#CDCCCC";
        //}
        //return /true/gi.test(rtn);
        return /true/gi.test(true);
    }
    function arraytxt(){
    	var menu=new Array(2);
		 menu[0]="主叫";
		 menu[1]="被叫";
    	return menu;
		 
    }
    function arrayval(){
    	var menu=new Array(2);
		 menu[0]="1";
		 menu[1]="2";
    	return menu;
    }
    function arraytxt2(){
    	var menu=new Array(3);
		 menu[0]="本地";
		 menu[1]="长途";
		 menu[2]="国际长途";
    	return menu;
		 
    }
    function arrayval2(){
    	var menu=new Array(3);
		 menu[0]="1";
		 menu[1]="2";
		 menu[2]="3";
    	return menu;
		 
    }
    

</script>
</html>
