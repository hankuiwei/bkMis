<%@ page language="java" import="com.zc13.util.*"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
String message = GlobalMethod.NullToSpace((String)request.getAttribute("message"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>电话费用信息</title>
	<base href="<%=basePath%>" target="_parent">
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript">
	<%if(!message.equals("")){%>
		alert("<%=message%>");
		window.returnValue = 'flag';
		window.close();
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
		
		
		function return1(){
			this.close();
		}
		
		//根据对方电话号码获取地区信息
		function getAreaName(){
			var otherPhone = document.getElementById("otherPhone").value;
			if(otherPhone!=null&&otherPhone!=""){
				$.post("phoneCost.do",{method:"getAreaName",phone:otherPhone},function(data){
					document.getElementById("callInfo.areaName").value=data;
				});
				getCost();
			}
		}
		
		//获取时长
		function getCallTime(){
			var starttime = document.getElementById("callInfo.startTime").value;
			var endtime = document.getElementById("callInfo.endTime").value;
			if(starttime!=null&&starttime!=""&&endtime!=null&&endtime!=""){
				//未完
				document.getElementById("callInfo.callTime").value = getTimeInterval(starttime,endtime);
				getCost();
			}
		}
		
		//获取2个时间间得时间差
		function getTimeInterval(starttime,endtime){
			var stime = new Date(starttime.replace("-","/")); 
			var etime = new Date(endtime.replace("-","/"));
			var temp = etime.getTime()-stime.getTime();
			return temp/1000;
		}
		
		//获取通话费用
		function getCost(){
			var callType = document.getElementById("callInfo.type").value; 
			if(callType=="被叫"){
				document.getElementById("callInfo.cost").value = "0";
			}else{
				var otherPhone = document.getElementById("otherPhone").value;
				var callTime = document.getElementById("callInfo.callTime").value;
				if(otherPhone!=null&&otherPhone!=""&&callTime!=null&&callTime!=""){
					$.post("phoneCost.do",{method:"getCost",phone:otherPhone,callTime:callTime},function(data){
						document.getElementById("callInfo.cost").value=data;
					});
				}
			}
		}
		
		//保存
		function check(){
			var x = Validator.Validate(document.getElementById('form1'),2);
			if(!x){
				return;
			}
			var callType = document.getElementById("callInfo.type").value;
			if(callType=="被叫"){
				document.getElementById("callInfo.callerPhone").value = document.getElementById("otherPhone").value;
				document.getElementById("callInfo.calledPhone").value = document.getElementById("localPhone").value;
			}else{
				document.getElementById("callInfo.callerPhone").value = document.getElementById("localPhone").value;
				document.getElementById("callInfo.calledPhone").value = document.getElementById("otherPhone").value;
			}
			var starttime = document.getElementById("callInfo.startTime").value;
			var endtime = document.getElementById("callInfo.endTime").value;
			if(starttime>endtime){
				alert("结束时间不能早于开始时间！");
				return;
			}
			document.form1.action="<%=path%>/phoneCost.do?method=editCallInfo";
			document.form1.submit();
		}
	</script>
	
</head>
<body>
	
	<form name = "form1" method="post">
		<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
		<input type="hidden" name="callInfo.callerPhone" id="callInfo.callerPhone" value="" />
		<input type="hidden" name="callInfo.calledPhone" id="callInfo.calledPhone" value="" />
		<input type="hidden" name="callInfo.id" id="callInfo.id" value="${phoneCostForm.callInfo.id }" />
		<input type="hidden" name="" id="" value="" />
		<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
    				<td height="5"></td>
  				</tr>
		  		<tr>
		    		<td>
		    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      				<tr>
		        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">电话费用信息</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
		        			</tr>
		    			</table>
		    		</td>
		  		</tr>
				<tr>
					<td class="menu_tab2" align="center">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="5" align="center">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="10" colspan="9"></td>
										</tr>

										<tr>
											<td>
												<table align="center" border="0" cellpadding="3"
													cellspacing="0" class="form_tab">
													<tr>
														<td class="head_rols" align="right">
															本机号码：
														</td>
														<td class="fist_rows">
															<input type="text" name="localPhone" id="localPhone" readonly value="${phoneCostForm.cxPhoneNumber }" />
														</td>
													</tr>
													<tr>
														<td class="head_rols" align="right">
															对方号码：
														</td>
														<td class="fist_rows">
															<input type="text" name="otherPhone" id="otherPhone" dataType="Require" msg="对方号码不得为空！" value="" onchange="getAreaName()" >&nbsp;&nbsp;&nbsp;<font color="red">*</font>
															<script>
																if("${phoneCostForm.cxPhoneNumber}"=="${phoneCostForm.callInfo.callerPhone}"){
																	document.getElementById("otherPhone").value="${phoneCostForm.callInfo.calledPhone}";
																}else{
																	document.getElementById("otherPhone").value="${phoneCostForm.callInfo.callerPhone}";
																}
															</script>
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															通话开始时间：
														</td>
														<td class="head_form1">
															<input type="text" name="callInfo.startTime" id="callInfo.startTime" onchange="getCallTime()"  dataType="Require" msg="通话开始时间不得为空！" readonly onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${phoneCostForm.callInfo.startTime }" class="Wdate"/>&nbsp;&nbsp;&nbsp;<font color="red">*</font>
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															通话结束时间：
														</td>
														<td class="head_form1">
															<input type="text" name="callInfo.endTime" id="callInfo.endTime" onchange="getCallTime()"  dataType="Require" msg="通话开始时间不得为空！" readonly onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${phoneCostForm.callInfo.endTime }" class="Wdate"/>&nbsp;&nbsp;&nbsp;<font color="red">*</font>
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															通话时长：
														</td>
														<td class="head_form1">
															<input type="text" name="callInfo.callTime" id="callInfo.callTime" readonly value="${phoneCostForm.callInfo.callTime }" /> 
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															通话类型：
														</td>
														<td class="head_form1">
															<select id="callInfo.type" name="callInfo.type" onchange="getCost()">
																<option value="主叫">主叫</option>
																<option value="被叫" <c:if test="${phoneCostForm.cxPhoneNumber eq phoneCostForm.callInfo.calledPhone }">selected</c:if> >被叫</option>
															</select>
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															地区名称：
														</td>
														<td class="head_form1">
															<input type="text" name="callInfo.areaName" id="callInfo.areaName" value="${phoneCostForm.callInfo.areaName }" />
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															本次通话费用：
														</td>
														<td class="head_form1">
															<input type="text" name="callInfo.cost" id="callInfo.cost"  dataType="Double2" msg="通话费用必须是数字！" value="${phoneCostForm.callInfo.cost }" />
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															缴费情况：
														</td>
														<td class="head_form1">
															<select id="callInfo.isPaid" name="callInfo.isPaid">
																<option value="未缴" <c:if test="${phoneCostForm.callInfo.isPaid eq '未缴' }">selected</c:if> >未缴</option>
																<option value="已缴" <c:if test="${phoneCostForm.callInfo.isPaid eq '已缴' }">selected</c:if> >已缴</option>
															</select>
														</td>
													</tr>
													<tr>
														<td align="center" colspan="2" class="head_left">
															<input type="button" onclick="check()" class="button" value="确定">
															<input type="button" onclick="return1()" class="button" value="返回">

														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="10" colspan="9"></td>
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
