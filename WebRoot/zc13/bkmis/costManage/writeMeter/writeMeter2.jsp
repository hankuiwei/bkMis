<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ page import="com.zc13.util.*"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>表具抄录</title>
	
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
		var tdObj;//保存上次操作的td对象
		var v_id;//保存上次操作的input对象的id的值
		var v_name;//保存上次操作的input对象的name的值
		var modifyRows = new Array();//保存修改过的行号
		var old_value;//保存旧值
		function query(){
			
		}
		//编辑td
		function editTd(obj,str){
			try{
				//将上一个编辑框置的边框置为不可见
				var now_value = document.getElementById(v_id).value;
				tdObj.innerHTML="<input type='text' name='"+v_name+"' id='"+v_id+"' value='"+now_value+"' style='width:83px;BORDER:none;' readonly />";
				if(now_value!=old_value){
					tdObj.parentElement.firstChild.innerHTML = "*"+tdObj.parentElement.rowIndex;
					tdObj.parentElement.style.backgroundColor = 'a0c0ff';
					old_value = now_value;
					var temp = true;
					//将修改过的行号保存到modifyRows中，重复的将不再重复保存
					for(var i = 0;i<modifyRows.length;i++){
						if(modifyRows[i]==tdObj.parentElement.rowIndex){
							temp = false;
							break;
						}
					}
					if(temp){
						modifyRows[modifyRows.length]=tdObj.parentElement.rowIndex;
					}
					//alert(tdObj.parentElement.firstChild.innerHTML);
				}
			}catch(e){}
			var row_index = obj.parentElement.rowIndex;//当前td所在的tr是第几行,算上表头，从0开始算
			v_id = str+row_index;
			v_name = str;
			old_value = document.getElementById(v_id).value;
			if(str=="beginDate"||str=="endDate"){
				obj.innerHTML="<input type='text' name='"+str+"' id='"+v_id+"' value='"+old_value+"' style='width:83px' onclick='WdatePicker()' readonly />";
				document.getElementById(v_id).click();
			}else{
				obj.innerHTML="<input type='text' name='"+str+"' id='"+v_id+"' value='"+old_value+"' onchange='counts(this)' style='width:83px' />";
			}
			//document.getElementById(v_id).focus();
			document.getElementById(v_id).select();
			tdObj = obj;
		}
		
		//取消上一个td的编辑
		function cancelEdit(ids){
			try{
				if(v_id==ids){
					return;
				}
				//将上一个编辑框置的边框置为不可见
				var now_value = document.getElementById(v_id).value;
				tdObj.innerHTML="<input type='text' name='"+v_name+"' id='"+v_id+"' value='"+now_value+"' style='width:83px;BORDER:none;' readonly />";
				if(now_value!=old_value){
					tdObj.parentElement.firstChild.innerHTML = "*"+tdObj.parentElement.rowIndex;
					tdObj.parentElement.style.backgroundColor = 'a0c0ff';
					old_value = now_value;
					var temp = true;
					//将修改过的行号保存到modifyRows中，重复的将不再重复保存
					for(var i = 0;i<modifyRows.length;i++){
						if(modifyRows[i]==tdObj.parentElement.rowIndex){
							temp = false;
							break;
						}
					}
					if(temp){
						modifyRows[modifyRows.length]=tdObj.parentElement.rowIndex;
					}
				}
			}catch(e){}
		}
		
		//读表改变时调用的方法
		function counts(obj){
			//alert("所在行号："+obj.parentElement.parentElement.rowIndex);
			//alert("name:"+obj.name);
			if(obj.name=="thisRecord"||obj.name=="lastRecord"){
				var tempRowNum = obj.parentElement.parentElement.rowIndex;
				var record1 = document.getElementById("thisRecord"+tempRowNum).value;
				var record2 = document.getElementById("lastRecord"+tempRowNum).value;
				document.getElementById("useRecord"+tempRowNum).innerHTML=accSub(record1,record2);
			}
		}
		
		//加法函数，用来得到精确的加法结果
		//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
		//调用：accAdd(arg1,arg2)
		//返回值：arg1加上arg2的精确结果
		function accAdd(arg1,arg2){
			var r1,r2,m;
			try{
				r1=arg1.toString().split(".")[1].length;
			}
			catch(Exception){
				r1=0;
			}
			try{
				r2=arg2.toString().split(".")[1].length;
			} catch(Exception) {
				r2=0;
			}
			m=Math.pow(10,Math.max(r1,r2))
			return (arg1*m+arg2*m)/m;
		}
		
		//减法函数，用来得到精确的减法结果
		//返回值：arg1-arg2的精确结果
		function accSub(arg1,arg2){
			var r1,r2,m;
			try{
				r1=arg1.toString().split(".")[1].length;
			}
			catch(Exception){
				r1=0;
			}
			try{
				r2=arg2.toString().split(".")[1].length;
			} catch(Exception) {
				r2=0;
			}
			m=Math.pow(10,Math.max(r1,r2))
			return (arg1*m-arg2*m)/m;
		}
		
		//导出报表
		function explortExcel(){
			window.location.href = "<%=path%>/meterInput.do?method=explortUserExcel";
		}
		
		//关闭窗口执行的操作
		function closeWindows(){
			delList();
			opener.location.reload();
		}
		
		//删除session中的list
		function delList(){
			<%
			request.getSession().removeAttribute("userMeterList");
			request.getSession().removeAttribute("publicMeterList");
			%>
			//window.location.href = "<%=path%>/meterInput.do?method=deleteSessionInfo";
		}
		
		//查询
		function query(){
			document.actionForm.action = "<%=path%>/meterInput.do?method=getUserReadMeter";
			document.actionForm.submit();
		}
	</script>
</head>
	<body style="" OnUnload="javascript:closeWindows();">
		<form method="post" name="actionForm">
			<table width="99%" height="96%" border="0" align="center"
				cellpadding="0" cellspacing="0" style="layout: fixed">
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">表具抄录</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr height="95%">
					<td class="menu_tab2" align="center" valign="top">
						<table width="100%" height="100%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td align="center">
									<!-- 查询条件start -->
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
									  	<tr>
							            	<td height="10" colspan="6"></td>
							         	</tr>
							         	<tr>
							            	<td height="10" colspan="3">
							            		&nbsp;&nbsp;期间：${years }年${months }月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户表具：${meterName}
							            	</td>
							            	<td align="right">
							            		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;房间号：<input type="text" style="width:100px;" name="roomCode" value="${roomCode }" />
							            	</td>
							            	<td>
							            		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;表具编号：<input type="text" style="width:100px;" name="meterCode" value="${meterCode }" />
							            	</td>
							            	<td>
							            		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button" id = "focuson" onclick="query();" value="查询">
							            	</td>
							         	</tr>
							         	<tr>
							            	<td height="10" colspan="6">
							            		<input type="button" class="button" id = "focuson" onclick="saveMeterInfo()" value="保存">
							           		 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							           		 	<!-- 
							           		 	<input type="button" class="button" id = "focuson" onclick="" value="Excel模板下载">
							           		 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							           		 	<input type="button" class="button" id = "focuson" onclick="" value="Excel导入">
							           		 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							           		 	 
							            		-->
							            		<c:choose>
							            		<c:when test="${empty list}">
													<input type="button" class="button" id = "" onclick="alert('无记录!');" value="导出报表">			
												</c:when>
												<c:otherwise>
													<input type="button" class="button" id = "" onclick="explortExcel();" value="导出报表">
												</c:otherwise>
												</c:choose>
							           		 	
							            	</td>
							         	</tr>
							          	
							          	 <tr>
							            	<td height="10" colspan="6"></td>
							         	</tr>
							        </table>
								</td>
							</tr>
							<tr height="95%">
								<td valign="top">
									<!-- 表单内容区域 -->
									<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed">
										<tr height="95%">
											<td width="100%">
												<div id="div1" class="RptDiv">
													<table border="0" cellpadding="0" cellspacing="0" class="RptTable" id="tab">
														<tr>
														    <th width="5%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">序号</th>
								                			<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">楼幢名称</th>
								                			<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户名称</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">表具编号</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">开始日期</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">结束日期</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">上月读表</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">本月读表</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">本月用量</th>
															<!--
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">费用计算公式</th>
															<th width="24%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">金额</th>
															  -->
														</tr>
														<c:choose>
															<c:when test="${empty list}">
																
															</c:when>
															<c:otherwise>
																<c:forEach items="${list}" var="c" varStatus="vs">
																	<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
																		<td class="RptTableBodyCellLock"  align="center" onclick="cancelEdit('');">${vs.index+1 }</td>
																		<td class="RptTableBodyCell" onclick="cancelEdit('');">${c.buildName }</td>
																		<td class="RptTableBodyCell" onclick="cancelEdit('');">${c.unitName }</td>
																		<td class="RptTableBodyCell" onclick="cancelEdit('');">${c.roomCode }</td>
																		<td class="RptTableBodyCell" onclick="cancelEdit('');">${c.code }</td>
																		<td class="RptTableBodyCell" ondblclick="editTd(this,'beginDate');" onclick="cancelEdit('beginDate${vs.index+1 }');">
																			<c:choose>
																				<c:when test="${c.beginDate==''}">
																					<input type="text" name="beginDate" id="beginDate${vs.index+1 }" style="BORDER:none;width:83px;" value="${startDate }" readonly/>
																				</c:when>
																				<c:otherwise>
																					<input type="text" name="beginDate" id="beginDate${vs.index+1 }" style="BORDER:none;width:83px;" value="${c.beginDate }" readonly/>
																				</c:otherwise>
																			</c:choose>
																		</td>
																		<td class="RptTableBodyCell" ondblclick="editTd(this,'endDate');" onclick="cancelEdit('endDate${vs.index+1 }');">
																			<c:choose>
																				<c:when test="${c.endDate==''}">
																					<input type="text" name="endDate" id="endDate${vs.index+1 }" style="BORDER:none;width:83px;" value="${lastDate }" readonly/>
																				</c:when>
																				<c:otherwise>
																					<input type="text" name="endDate" id="endDate${vs.index+1 }" style="BORDER:none;width:83px;" value="${c.endDate }" readonly/>
																				</c:otherwise>
																			</c:choose>
																		</td>
																		<td class="RptTableBodyCell" ondblclick="editTd(this,'lastRecord');" onclick="cancelEdit('lastRecord${vs.index+1 }');">
																			<input type="text" name="lastRecord" id="lastRecord${vs.index+1 }" onchange="counts(this);" style="BORDER:none;width:83px;" value="${c.lastRecord }" readonly/>
																		</td>
																		<td class="RptTableBodyCell" ondblclick="editTd(this,'thisRecord');" onclick="cancelEdit('thisRecord${vs.index+1 }');">
																			<input type="text" name="thisRecord" id="thisRecord${vs.index+1 }" onchange="counts(this);" style="BORDER:none;width:83px;" value="${c.thisRecord }" readonly/>
																		</td>
																		<td class="RptTableBodyCell">
																			<span id="useRecord${vs.index+1 }">
																			<script>
																				document.write(parseFloat(${c.thisRecord-c.lastRecord }).toFixed(2).toString());
																			</script>
																			</span>
																		</td>
																		<!-- 
																		<td class="RptTableBodyCell" onclick="cancelEdit('');">{该表用量}*1.2-50</td>
																		<td class="RptTableBodyCell" ondblclick="editTd(this,'cost');" onclick="cancelEdit('');">0</td>
																		 -->
																		<input type="hidden" name="id" id="id${vs.index+1 }" value="${c.id }" />
																		<input type="hidden" name="meterId" value="${c.meterId }" />
																		<input type="hidden" name="code" value="${c.code }" />
																		<input type="hidden" name="meterType" value="${c.meterType }" />
																		<input type="hidden" name="years" value="${years }" />
																		<input type="hidden" name="months" value="${months }" />
																		<input type="hidden" name="lookUpMan" value="${lookUpMan }" />
																		<input type="hidden" name="lookUpTime" value="${lookUpTime }" />
																		<input type="hidden" name="enterMan" value="${enterMan }" />
																	</tr>
																</c:forEach>
															</c:otherwise>
														</c:choose>
														  
														<%for(int i = 1;i<=50;i++){ %>
														<!--
														<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
															<td class="RptTableBodyCellLock"  align="center" onclick="cancelEdit();"><%//=i %></td>
															<td class="RptTableBodyCell" onclick="cancelEdit();">1号楼</td>
															<td class="RptTableBodyCell" onclick="cancelEdit();">客户<%//=i %></td>
															<td class="RptTableBodyCell" onclick="cancelEdit();">10<%//=i>9?i:"0"+i %>室</td>
															<td class="RptTableBodyCell" onclick="cancelEdit();">0200<%//=i>9?i:"0"+i %></td>
															<td class="RptTableBodyCell" ondblclick="editTd(this,'starttime');" onclick="cancelEdit();">2010-11-01</td>
															<td class="RptTableBodyCell" ondblclick="editTd(this,'endtime');" onclick="cancelEdit();">2010-11-30</td>
															<td class="RptTableBodyCell" ondblclick="editTd(this,'p_readMeter');" onclick="cancelEdit();">0</td>
															<td class="RptTableBodyCell" ondblclick="editTd(this,'t_readMeter');" onclick="cancelEdit();">0</td>
															<td class="RptTableBodyCell">0</td>
															<td class="RptTableBodyCell" onclick="cancelEdit();">{该表用量}*1.2-50</td>
															<td class="RptTableBodyCell" ondblclick="editTd(this,'cost');" onclick="cancelEdit();">0</td>
														</tr>
														-->
														<%} %>
														
													</table>
												</div>
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
	<script language="javascript">
	var xmlHttpRequest;   
  	
  	//创建一个XMLHttpRequest对象
	function createXMLHttpRequest(){   
	    if(window.XMLHttpRequest)   
	    {   
	        xmlHttpRequest = new XMLHttpRequest();   
	    }if(window.ActiveXObject)   
	    {   
	      try   
	      {    
	        xmlHttpRequest=new ActiveXObject("Msxml2.XMLHTTP");    
	      }catch(e)   
	      {    
	        try   
	        {    
	            xmlHttpRequest=new ActiveXObject("Microsoft.XMLHTTP");    
	        }catch(e){}    
	      }    
	    }      
	} 
	
	//回调函数
	function callback(){     
	    if(xmlHttpRequest.readyState==4){//对象状态      
	        if(xmlHttpRequest.status==200){//信息已成功返回，开始处理信息    
	            var message = xmlHttpRequest.responseText;   
	            if((message.valueOf())==0){
	            	alert("保存失败！");
	            } else{
	            	alert("保存成功！");
	            	var temp_ids = message.valueOf().split(",");
	            	//将序号前的*去掉
	            	for(var i = 0;i<modifyRows.length;i++){
	            		//将被保存的记录的id值赋给对于的input
	            		document.getElementById("id"+modifyRows[i]).value=temp_ids[i];
	            		document.getElementById("tab").rows[modifyRows[i]].firstChild.innerHTML = modifyRows[i];
	            		document.getElementById("tab").rows[modifyRows[i]].style.backgroundColor = '#ffffff';
	            	}
	            	//将modifyRows置空
	            	modifyRows = new Array();
	            }
	        }   
	    }   
	}


	function saveMeterInfo(){
		cancelEdit('');
		if(modifyRows.length>0){
			createXMLHttpRequest();
			var url = "<%=path%>/meterInput.do?method=saveUserReadMeter&timeStamp="+new Date().getTime();
			xmlHttpRequest.open("POST",url,true);
			xmlHttpRequest.onreadystatechange = callback;
			xmlHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
			var params = createParam();
			xmlHttpRequest.send(params);
		}
	}
	
	//创建参数字符串
	function createParam(){
		if(modifyRows.length<=0){
			return "";
		}
		var params = "&years="+valByName("years",0)+"&months="+valByName("months",0)+"&lookUpMan="+valByName("lookUpMan",0)+"&lookUpTime="+valByName("lookUpTime",0)+"&enterMan="+valByName("enterMan",0);
		var ids = "";
		var meterIds = "";
		var codes = "";
		var meterTypes = "";
		var beginDates = "";
		var endDates = "";
		var lastRecords = "";
		var thisRecords = "";
		for(var i = 0;i<modifyRows.length;i++){
			ids = ids+valByName("id",modifyRows[i]-1);
			meterIds = meterIds+valByName("meterId",modifyRows[i]-1);
			codes = codes+valByName("code",modifyRows[i]-1);
			meterTypes = meterTypes+valByName("meterType",modifyRows[i]-1);
			beginDates = beginDates+valByName("beginDate",modifyRows[i]-1);
			endDates = endDates+valByName("endDate",modifyRows[i]-1);
			lastRecords = lastRecords+valByName("lastRecord",modifyRows[i]-1);
			thisRecords = thisRecords+valByName("thisRecord",modifyRows[i]-1);
			if(i<modifyRows.length-1){
				ids+=",";
				meterIds+=",";
				codes+=",";
				meterTypes+=",";
				beginDates+=",";
				endDates+=",";
				lastRecords+=",";
				thisRecords+=",";
			}
		}
		params = params+"&ids="+ids+"&meterIds="+meterIds+"&codes="+codes+"&meterTypes="+meterTypes+"&beginDates="+beginDates+"&endDates="+endDates+"&lastRecords="+lastRecords+"&thisRecords="+thisRecords;
		return params;
	}
	
	//通过name和index获得值
	function valByName(names,indexs){
		return document.getElementsByName(names)[indexs].value;
	}
	//通过id获得value值
	function valById(ids){
		return document.getElementById(ids).value;
	}
	</script>
</html>
