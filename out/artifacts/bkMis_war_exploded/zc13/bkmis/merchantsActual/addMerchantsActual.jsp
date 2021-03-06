<%@ page language="java" import="com.zc13.util.*"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>添加实际招商</title>
	<base href="<%=basePath%>" target="_parent">
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
	<script type="text/javascript">
		function check(){
			document.form1.action="merchantsActual.do?method=addmerchantsActual";
			document.form1.submit();
		}
		//根据季度改变月份
		function changemonth(){
		var val=document.getElementById('quarter').value;
		if(val=="1"){
		 document.getElementById('getmm').innerHTML='<input type="text" name="month"  style=" width: 138px"  onclick="WdatePicker({skin:\'whyGreen\',dateFmt:\'MM\',minDate:\'YYYY-1\',maxDate:\'YYYY-3\'})" readonly onclick="WdatePicker();" class="Wdate">';
		}else if(val=="2"){
		 document.getElementById('getmm').innerHTML='<input type="text" name="month"  style=" width: 138px"  onclick="WdatePicker({skin:\'whyGreen\',dateFmt:\'MM\',minDate:\'YYYY-4\',maxDate:\'YYYY-6\'})" readonly onclick="WdatePicker();" class="Wdate">';
		}else if(val=="3"){
		 document.getElementById('getmm').innerHTML='<input type="text" name="month"  style=" width: 138px"  onclick="WdatePicker({skin:\'whyGreen\',dateFmt:\'MM\',minDate:\'YYYY-7\',maxDate:\'YYYY-9\'})" readonly onclick="WdatePicker();" class="Wdate">';
		}else if(val="4"){
		 document.getElementById('getmm').innerHTML='<input type="text" name="month"  style=" width: 138px"  onclick="WdatePicker({skin:\'whyGreen\',dateFmt:\'MM\',minDate:\'YYYY-10\',maxDate:\'YYYY-12\'})" readonly onclick="WdatePicker();" class="Wdate">';
		 }
		
		}
		function return1(){
			this.close();
		}
	</script>
	
</head>
<body onunload="window.returnValue = 'flag';window.close();">
<form name = "form1" method="post">
	<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0">

				<tr>
    				<td height="5"></td>
  				</tr>
		  		<tr>
		    		<td>
		    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      				<tr>
		        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">添加实际招商</td>
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
															年份：
														</td>
														<td class="fist_rows">
														<input type="text" name="year"  style=" width: 138px"  onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" readonly onclick="WdatePicker();" class="Wdate">
														</td>
													</tr>
													 <tr>
														<td class="head_rols" align="right">
															季度：
														</td>
														<td class="fist_rows">
															<select id="quarter" name="quarter" onchange="changemonth()">
						            		<option value="1" >1</option>
						            		<option value="2">2</option>
						            		<option value="3">3</option>
						            		<option value="4">4</option>
					            		</select>
														</td>
													</tr>
                                                    <tr>
														<td class="head_rols" align="right">
															月份：
														</td>
														<td class="fist_rows" id="getmm">
															<input type="text" name="month"  style=" width: 138px"  onclick="WdatePicker({skin:'whyGreen',dateFmt:'MM',minDate:'YYYY-1',maxDate:'YYYY-3'})" readonly onclick="WdatePicker();" class="Wdate">
														</td>
													</tr>	
													<tr>
														<td class="head_rols" align="right">
															计划分配人：
														</td>
														<td class="fist_rows" id="getmm">
															<select id="personType" name="personType">
															<option value="">--请选择--</option>
															<c:forEach items="${codeList }" var="code">
																<option value="${code.codeValue }">${code.codeName }</option>
															</c:forEach>
															</select>
														</td>
													</tr>	
													<tr>
														<td class="head_rols" align="right">
															实际出租面积：
														</td>
														<td class="fist_rows">
															<input type="text" name="actualArea"  dataType="Double2" msg="必须是数字！" id="rate"> 
														</td>
													</tr>
													<tr>
														<td class="head_rols" align="right">
															责任人：
														</td>
														<td class="fist_rows">
															<input type="text" name="responsePerson"> 
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
