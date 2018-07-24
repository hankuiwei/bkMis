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
	<title>添加或是编辑项目费用</title>
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
			if("" == document.getElementById("projectCosts.year").value){
				alert("年度不能为空!");
				return;
			}
			/*if("" == document.getElementById("projectCosts.month").value){
				alert("月份不能为空!");
				return;
			}*/
			calculate();
			document.forms[0].action="<%=basePath %>financialReport.do?method=saveOrUpdateProject";
			document.forms[0].submit();
		}
		function return1(){
			this.close();
		}
		
		function jsMoney(){
			var copeWage = document.getElementById("projectCosts.copeWage").value;
			if(copeWage != null && copeWage !=""){
				document.getElementById("projectCosts.copeWelfare").value = js(parseFloat(copeWage*0.14));
				document.getElementById("projectCosts.unionFunds").value = js(parseFloat(copeWage*0.02));
				document.getElementById("projectCosts.educationFunds").value = js(parseFloat(copeWage*0.025));
				document.getElementById("projectCosts.supplementaryMedical").value = js(parseFloat(copeWage*0.04));
				document.getElementById("projectCosts.enterpriseAnnuity").value = js(parseFloat(copeWage*0.04));
			}
		}
		
		function calculate(){
			
			var copeWage = document.getElementById("projectCosts.copeWage").value;
			var copeWelfare = document.getElementById("projectCosts.copeWelfare").value;
			var unionFundsunionFunds = document.getElementById("projectCosts.unionFunds").value;
			var educationFunds = document.getElementById("projectCosts.educationFunds").value;
			var supplementaryMedical = document.getElementById("projectCosts.supplementaryMedical").value;
			var enterpriseAnnuity = document.getElementById("projectCosts.enterpriseAnnuity").value;
			var pensionInsurance = document.getElementById("projectCosts.pensionInsurance").value;
			var healthInsurance = document.getElementById("projectCosts.healthInsurance").value;
			var housingFund = document.getElementById("projectCosts.housingFund").value;
			var unemployInsurance = document.getElementById("projectCosts.unemployInsurance").value;
			var injuryInsurance = document.getElementById("projectCosts.injuryInsurance").value;
			var maternityInsurance = document.getElementById("projectCosts.maternityInsurance").value;
			var trafficFee = document.getElementById("projectCosts.trafficFee").value;
			var phoneFee = document.getElementById("projectCosts.phoneFee").value;
			var depreciationFee = document.getElementById("projectCosts.depreciationFee").value;
			var hospitalityFee = document.getElementById("projectCosts.hospitalityFee").value;
			var travelFee = document.getElementById("projectCosts.travelFee").value;
			var officeFee = document.getElementById("projectCosts.officeFee").value;
			var auditFee = document.getElementById("projectCosts.auditFee").value;
			var boardFee  = document.getElementById("projectCosts.boardFee").value;
			var stampDuty = document.getElementById("projectCosts.stampDuty").value;
			var conferenceFee = document.getElementById("projectCosts.conferenceFee").value;
			var otherFee = document.getElementById("projectCosts.otherFee").value;
			var hj = 0;
			hj += parseFloat(copeWage)+parseFloat(copeWelfare)+parseFloat(unionFundsunionFunds)+parseFloat(educationFunds)+parseFloat(supplementaryMedical)+parseFloat(enterpriseAnnuity)+parseFloat(pensionInsurance)+parseFloat(healthInsurance)+parseFloat(housingFund);
			hj += parseFloat(unemployInsurance)+parseFloat(injuryInsurance)+parseFloat(maternityInsurance)+parseFloat(trafficFee)+parseFloat(phoneFee)+parseFloat(depreciationFee)+parseFloat(hospitalityFee)+parseFloat(travelFee);
			hj += parseFloat(officeFee)+parseFloat(auditFee)+parseFloat(boardFee)+parseFloat(stampDuty)+parseFloat(conferenceFee)+parseFloat(otherFee);
			hj = Math.round(hj*1000)/1000;
			//alert(hj);
			document.getElementById("projectCosts.glfHj").value=hj;
		}
		
		//保留2位小数
		function js(money){
			return Math.round(money*100)/100;
		}
	</script>
	
</head>
  
  <body onunload="window.returnValue = 'flag';window.close();">
  	<form action="" method="post">
    <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
    				<td height="5"></td>
  				</tr>
		  		<tr>
		    		<td>
		    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      				<tr>
		        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">项目费用记录</td>
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
															类型：
														</td>
														<td class="fist_rows">
															<select id="" name="projectCosts.moneyType">
																<option value="实际" <c:if test="${form.projectCosts.moneyType == '实际' }">selected</c:if>>实际</option>
																<option value="预算" <c:if test="${form.projectCosts.moneyType == '预算' }">selected</c:if>>预算</option>
															</select>&nbsp;&nbsp;&nbsp;
														</td>
														<td class="head_rols" align="right">
															年度：
														</td>
														<td class="fist_rows">
															<input type="text" id="projectCosts.year" name="projectCosts.year" value="${form.projectCosts.year }"  style=" width: 138px"  onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" readonly onclick="WdatePicker();" class="Wdate"><font color="red">*</font>&nbsp;&nbsp;&nbsp;
															<input type="hidden" name="projectCosts.id" value="${form.projectCosts.id }">
 														</td>
 														<td class="head_rols" align="right">
															月份：
														</td>
														<td class="fist_rows">
															<input type="text" id="projectCosts.month" name="projectCosts.month" value="${form.projectCosts.month }" style=" width: 138px"  onclick="WdatePicker({skin:'whyGreen',dateFmt:'MM'})" readonly onclick="WdatePicker();" class="Wdate"><%--<font color="red">*</font> --%>
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															部门：
														</td>
														<td class="head_form1">
															<select id="projectCosts.department" name="projectCosts.department">
																<option value="管理部门" <c:if test="${form.projectCosts.department == '管理部门' }">selected</c:if>>管理部门</option>	
																<option value="工程部门" <c:if test="${form.projectCosts.department == '工程部门' }">selected</c:if>>工程部门</option>	
																<option value="招商部门" <c:if test="${form.projectCosts.department == '招商部门' }">selected</c:if>>招商部门</option>		
															</select>
														</td>
														<td class="head_left" align="right" nowrap="nowrap">
															应付工资：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.copeWage" name="projectCosts.copeWage"  style=" width: 138px" value="${form.projectCosts.copeWage }" onchange="jsMoney()" id="projectCosts.copeWage">
														</td>
														<td class="head_left" align="right" nowrap="nowrap">
															应付福利费：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.copeWelfare" name="projectCosts.copeWelfare"  style=" width: 138px" value="${form.projectCosts.copeWelfare }" id="projectCosts.copeWelfare">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															工会经费：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.unionFunds" name="projectCosts.unionFunds"  style=" width: 138px" value="${form.projectCosts.unionFunds }" id="projectCosts.unionFunds">
														</td>
														<td class="head_left" align="right">
															教育经费：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.educationFunds" name="projectCosts.educationFunds"  style=" width: 138px" value="${form.projectCosts.educationFunds }" id="projectCosts.educationFunds">
														</td>
														<td class="head_left" align="right">
															补充医疗：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.supplementaryMedical" name="projectCosts.supplementaryMedical"  style=" width: 138px" value="${form.projectCosts.supplementaryMedical }" id="projectCosts.supplementaryMedical">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															企业年金：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.enterpriseAnnuity" name="projectCosts.enterpriseAnnuity"  style=" width: 138px" value="${form.projectCosts.enterpriseAnnuity }" id="projectCosts.enterpriseAnnuity">
														</td>
														<td class="head_left" align="right" nowrap="nowrap">
															基本养老保险：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.pensionInsurance" name="projectCosts.pensionInsurance"  style=" width: 138px" value="${form.projectCosts.pensionInsurance }">
														</td>
														<td class="head_left" align="right" nowrap="nowrap">
															基本医疗保险：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.healthInsurance" name="projectCosts.healthInsurance"  style=" width: 138px" value="${form.projectCosts.healthInsurance }">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															住房公积金：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.housingFund" name="projectCosts.housingFund"  style=" width: 138px" value="${form.projectCosts.housingFund }">
														</td>
														<td class="head_left" align="right">
															失业保险：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.unemployInsurance" name="projectCosts.unemployInsurance"  style=" width: 138px" value="${form.projectCosts.unemployInsurance }">
														</td>
														<td class="head_left" align="right">
															工伤保险：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.injuryInsurance" name="projectCosts.injuryInsurance"  style=" width: 138px" value="${form.projectCosts.injuryInsurance }">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															生育保险：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.maternityInsurance" name="projectCosts.maternityInsurance"  style=" width: 138px" value="${form.projectCosts.maternityInsurance }">
														</td>
														<td class="head_left" align="right">
															交通费：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.trafficFee" name="projectCosts.trafficFee"  style=" width: 138px" value="${form.projectCosts.trafficFee }">
														</td>
														<td class="head_left" align="right">
															电话费：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.phoneFee" name="projectCosts.phoneFee"  style=" width: 138px" value="${form.projectCosts.phoneFee }">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															折旧费：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.depreciationFee" name="projectCosts.depreciationFee"  style=" width: 138px" value="${form.projectCosts.depreciationFee }">
														</td>
														<td class="head_left" align="right">
															业务招待费：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.hospitalityFee" name="projectCosts.hospitalityFee"  style=" width: 138px" value="${form.projectCosts.hospitalityFee }">
														</td>
														<td class="head_left" align="right">
															差旅费：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.travelFee" name="projectCosts.travelFee"  style=" width: 138px" value="${form.projectCosts.travelFee }">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															办公费：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.officeFee" name="projectCosts.officeFee"  style=" width: 138px" value="${form.projectCosts.officeFee }">
														</td>
														<td class="head_left" align="right" nowrap="nowrap">
															审计费、律师费：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.auditFee" name="projectCosts.auditFee"  style=" width: 138px" value="${form.projectCosts.auditFee }">
														</td>
														<td class="head_left" align="right">
															董事会费：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.boardFee" name="projectCosts.boardFee"  style=" width: 138px" value="${form.projectCosts.boardFee }">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															印花税：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.stampDuty" name="projectCosts.stampDuty"  style=" width: 138px" value="${form.projectCosts.stampDuty }">
														</td>
														<td class="head_left" align="right">
															会议费：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.conferenceFee" name="projectCosts.conferenceFee"  style=" width: 138px" value="${form.projectCosts.conferenceFee }">
														</td>
														<td class="head_left" align="right">
															其他：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.otherFee" name="projectCosts.otherFee"  style=" width: 138px" value="${form.projectCosts.otherFee }">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															房产、土地摊销：
														</td>
														<td class="head_form1">
															<input type="text" id="projectCosts.estateFee" name="projectCosts.estateFee"  style=" width: 138px" value="${form.projectCosts.estateFee }">
														</td>
														<td class="head_left" align="right">
															土地税：
														</td>
														<td class="head_form1">
															<input type="text" name="projectCosts.landTax"  style=" width: 138px" value="${form.projectCosts.landTax }" id="projectCosts.landTax">
														</td>
														<td class="head_left" align="right">
															房产税：
														</td>
														<td class="head_form1">
															<input type="text" name="projectCosts.propertyTax"  style=" width: 138px" value="${form.projectCosts.propertyTax }">
														</td>
													</tr>
													<tr>
														<td class="head_left" align="right">
															管理费用合计（不含房产折旧、土地税、房产税）：
														</td>
														<td class="head_form1">
															<input type="text" name="projectCosts.glfHj"  style=" width: 138px" value="${form.projectCosts.estateFee }" id="projectCosts.glfHj">
															<input type="button" onclick="calculate()" class="button" value="计算"/>
														</td>
													</tr>
													<tr>
														<td align="center" colspan="6" class="head_left">
															<input type="button" onclick="check()" class="button" value="确定">
															<input type="button" onclick="return1()" class="button" value="返回">
														</td>
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
