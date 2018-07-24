<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>财务报表</title>
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript">
		function query(){
			document.form1.action="<%=path %>/financialReport.do?method=queryShow";
			document.form1.submit();
		}
		
		function exportExcel(){
			//alert("");
			document.form1.action="<%=path %>/financialReport.do?method=exportExcel";
			document.form1.submit();
		}
	</script>
</head>
<body>
<form name="form1" method="post">
	<table width="100%" height = "100%" border="1" align="center" cellpadding="0" cellspacing="0">
		  
		  <tr>
		  	<td colspan="11">
		  		年度：<input type="text" id="year" name="year" value="${form.year }"  style=" width: 138px"  onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" readonly onclick="WdatePicker();" class="Wdate">
		  		月度：<input type="text" id="month" name="month" value="${form.month }" style=" width: 138px"  onclick="WdatePicker({skin:'whyGreen',dateFmt:'MM'})" readonly onclick="WdatePicker();" class="Wdate">
		  		<input type="button" value="查询" class="button" onclick="query()">
		  		<input type="button" value="导出excel" class="button" onclick="exportExcel()">
		  	</td>
		  </tr>
		  <tr>
		    <td colspan="11" nowrap="nowrap" align="center"><font size="4px">${form.year }年${form.month }月实际费用与预算费用比较表</font></td>
		  </tr>
		  <tr>
		    <td rowspan="2" nowrap="nowrap" align="center">项目</td>
		    <td colspan="4" nowrap="nowrap" align="center">${form.year }年预算金额</td>
		    <td colspan="5" nowrap="nowrap" align="center">${form.year }年${form.month }月实际金额</td>
		    <td rowspan="2" nowrap="nowrap" align="center">占全年</td>
		  </tr>
		  <tr>
		    <td nowrap="nowrap" align="center">管理部门</td>
		    <td nowrap="nowrap" align="center">工程部门</td>
		    <td nowrap="nowrap" align="center">招商部门</td>
		    <td nowrap="nowrap" align="center">合计</td>
		    <td nowrap="nowrap" align="center">管理部门</td>
		    <td nowrap="nowrap" align="center">工程部门</td>
		    <td nowrap="nowrap" align="center">招商部门</td>
		    <td nowrap="nowrap" align="center">当月合计</td>
		    <td nowrap="nowrap" align="center">年累计</td>
		  </tr>
		  <tr>
		    <td>应付工资&nbsp;</td>
		    <td>
		    	<fmt:formatNumber value="${glysPeoject.copeWage }" pattern="#.##"></fmt:formatNumber>
		    	&nbsp;
		    </td>
		    <td><fmt:formatNumber value="${gcysPeoject.copeWage }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.copeWage }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.copeWage+gcysPeoject.copeWage+zsysPeoject.copeWage }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.copeWage }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.copeWage }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.copeWage }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.copeWage+gcsjPeoject.copeWage+zssjPeoject.copeWage }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.copeWage }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.copeWage+gcysPeoject.copeWage+zsysPeoject.copeWage !=0.0 }">
		    	<fmt:formatNumber value="${nljProject.copeWage/(glysPeoject.copeWage+gcysPeoject.copeWage+zsysPeoject.copeWage)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;
		    </td>
		  </tr>
		  <tr>
		    <td>应付福利费(14%)&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.copeWelfare }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.copeWelfare }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.copeWelfare }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.copeWelfare+gcysPeoject.copeWelfare+zsysPeoject.copeWelfare }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.copeWelfare }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.copeWelfare }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.copeWelfare }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.copeWelfare+gcsjPeoject.copeWelfare+zssjPeoject.copeWelfare }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.copeWelfare }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.copeWelfare+gcysPeoject.copeWelfare+zsysPeoject.copeWelfare != 0.0 }">
		    	<fmt:formatNumber value="${nljProject.copeWelfare/(glysPeoject.copeWelfare+gcysPeoject.copeWelfare+zsysPeoject.copeWelfare)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;
		    </td>
		  </tr>
		  <tr>
		    <td>工会经费(2%)&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.unionFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.unionFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.unionFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.unionFunds+gcysPeoject.unionFunds+zsysPeoject.unionFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.unionFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.unionFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.unionFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.unionFunds+gcsjPeoject.unionFunds+zssjPeoject.unionFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.unionFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.unionFunds+gcysPeoject.unionFunds+zsysPeoject.unionFunds != 0.0 }">
		    	<fmt:formatNumber value="${nljProject.unionFunds/(glysPeoject.unionFunds+gcysPeoject.unionFunds+zsysPeoject.unionFunds)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;
		    </td>
		  </tr>
		  <tr>
		    <td>教育经费(2.5%)&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.educationFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.educationFunds }" pattern="#.##"></fmt:formatNumber>a&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.educationFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.educationFunds+gcysPeoject.educationFunds+zsysPeoject.educationFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.educationFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.educationFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.educationFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.educationFunds+gcsjPeoject.educationFunds+zssjPeoject.educationFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.educationFunds }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.educationFunds+gcysPeoject.educationFunds+zsysPeoject.educationFunds != 0.0 }">
		    	<fmt:formatNumber value="${nljProject.educationFunds/(glysPeoject.educationFunds+gcysPeoject.educationFunds+zsysPeoject.educationFunds)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;
		    </td>
		  </tr>
		  <tr>
		    <td>补充医疗(4%)&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.supplementaryMedical }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.supplementaryMedical }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.supplementaryMedical }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.supplementaryMedical+gcysPeoject.supplementaryMedical+zsysPeoject.supplementaryMedical }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.supplementaryMedical }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.supplementaryMedical }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.supplementaryMedical }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.supplementaryMedical+gcsjPeoject.supplementaryMedical+zssjPeoject.supplementaryMedical }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.supplementaryMedical }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.supplementaryMedical+gcysPeoject.supplementaryMedical+zsysPeoject.supplementaryMedical != 0.0 }">
		    	<fmt:formatNumber value="${nljProject.supplementaryMedical/(glysPeoject.supplementaryMedical+gcysPeoject.supplementaryMedical+zsysPeoject.supplementaryMedical)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;
		    </td>
		  </tr>
		  <tr>
		    <td>企业年金(4%)&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.enterpriseAnnuity }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.enterpriseAnnuity }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.enterpriseAnnuity }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.enterpriseAnnuity+gcysPeoject.enterpriseAnnuity+zsysPeoject.enterpriseAnnuity }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.enterpriseAnnuity }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.enterpriseAnnuity }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.enterpriseAnnuity }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.enterpriseAnnuity+gcsjPeoject.enterpriseAnnuity+zssjPeoject.enterpriseAnnuity }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.enterpriseAnnuity }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.enterpriseAnnuity+gcysPeoject.enterpriseAnnuity+zsysPeoject.enterpriseAnnuity != 0.0}">
		    	<fmt:formatNumber value="${nljProject.enterpriseAnnuity/(glysPeoject.enterpriseAnnuity+gcysPeoject.enterpriseAnnuity+zsysPeoject.enterpriseAnnuity)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>基本养老保险&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.pensionInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.pensionInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.pensionInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.pensionInsurance+gcysPeoject.pensionInsurance+zsysPeoject.pensionInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.pensionInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.pensionInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.pensionInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.pensionInsurance+gcsjPeoject.pensionInsurance+zssjPeoject.pensionInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.pensionInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.pensionInsurance+gcysPeoject.pensionInsurance+zsysPeoject.pensionInsurance != 0.0}">
		    	<fmt:formatNumber value="${nljProject.pensionInsurance/(glysPeoject.pensionInsurance+gcysPeoject.pensionInsurance+zsysPeoject.pensionInsurance)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>基本医疗保险&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.healthInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.healthInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.healthInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.healthInsurance+gcysPeoject.healthInsurance+zsysPeoject.healthInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.healthInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.healthInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.healthInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.healthInsurance+gcsjPeoject.healthInsurance+zssjPeoject.healthInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.healthInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		     <c:if test="${glysPeoject.healthInsurance+gcysPeoject.healthInsurance+zsysPeoject.healthInsurance != 0.0}">
		    	<fmt:formatNumber value="${nljProject.healthInsurance/(glysPeoject.healthInsurance+gcysPeoject.healthInsurance+zsysPeoject.healthInsurance)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>住房公积金&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.housingFund }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.housingFund }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.housingFund }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.housingFund+gcysPeoject.housingFund+zsysPeoject.housingFund }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.housingFund }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.housingFund }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.housingFund }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.housingFund+gcsjPeoject.housingFund+zssjPeoject.housingFund }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.housingFund }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.housingFund+gcysPeoject.housingFund+zsysPeoject.housingFund != 0.0}">
		    	<fmt:formatNumber value="${nljProject.housingFund/(glysPeoject.housingFund+gcysPeoject.housingFund+zsysPeoject.housingFund)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>失业保险&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.unemployInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.unemployInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.unemployInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.unemployInsurance+gcysPeoject.unemployInsurance+zsysPeoject.unemployInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.unemployInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.unemployInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.unemployInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.unemployInsurance+gcsjPeoject.unemployInsurance+zssjPeoject.unemployInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.unemployInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.unemployInsurance+gcysPeoject.unemployInsurance+zsysPeoject.unemployInsurance != 0.0}">
		    	<fmt:formatNumber value="${nljProject.unemployInsurance/(glysPeoject.unemployInsurance+gcysPeoject.unemployInsurance+zsysPeoject.unemployInsurance)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>工伤保险&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.injuryInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.injuryInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.injuryInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.injuryInsurance+gcysPeoject.injuryInsurance+zsysPeoject.injuryInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.injuryInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.injuryInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.injuryInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.injuryInsurance+gcsjPeoject.injuryInsurance+zssjPeoject.injuryInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.injuryInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.injuryInsurance+gcysPeoject.injuryInsurance+zsysPeoject.injuryInsurance != 0.0}">
		    	<fmt:formatNumber value="${nljProject.injuryInsurance/(glysPeoject.injuryInsurance+gcysPeoject.injuryInsurance+zsysPeoject.injuryInsurance)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>生育保险&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.maternityInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.maternityInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.maternityInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.maternityInsurance+gcysPeoject.maternityInsurance+zsysPeoject.maternityInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.maternityInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.maternityInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.maternityInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.maternityInsurance+gcsjPeoject.maternityInsurance+zssjPeoject.maternityInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.maternityInsurance }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.maternityInsurance+gcysPeoject.maternityInsurance+zsysPeoject.maternityInsurance != 0.0}">
		    	<fmt:formatNumber value="${nljProject.maternityInsurance/(glysPeoject.maternityInsurance+gcysPeoject.maternityInsurance+zsysPeoject.maternityInsurance)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>交通费&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.trafficFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.trafficFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.trafficFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.trafficFee+gcysPeoject.trafficFee+zsysPeoject.trafficFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.trafficFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.trafficFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.trafficFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.trafficFee+gcsjPeoject.trafficFee+zssjPeoject.trafficFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.trafficFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		     <c:if test="${glysPeoject.trafficFee+gcysPeoject.trafficFee+zsysPeoject.trafficFee != 0.0}">
		    	<fmt:formatNumber value="${nljProject.trafficFee/(glysPeoject.trafficFee+gcysPeoject.trafficFee+zsysPeoject.trafficFee)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>电话费&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.phoneFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.phoneFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.phoneFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.phoneFee+gcysPeoject.phoneFee+zsysPeoject.phoneFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.phoneFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.phoneFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.phoneFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.phoneFee+gcsjPeoject.phoneFee+zssjPeoject.phoneFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.phoneFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.phoneFee+gcysPeoject.phoneFee+zsysPeoject.phoneFee != 0.0}">
		    	<fmt:formatNumber value="${nljProject.phoneFee/(glysPeoject.phoneFee+gcysPeoject.phoneFee+zsysPeoject.phoneFee)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>折旧费&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.depreciationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.depreciationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.depreciationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.depreciationFee+gcysPeoject.depreciationFee+zsysPeoject.depreciationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.depreciationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.depreciationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.depreciationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.depreciationFee+gcsjPeoject.depreciationFee+zssjPeoject.depreciationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.depreciationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.depreciationFee+gcysPeoject.depreciationFee+zsysPeoject.depreciationFee != 0.0}">
		    	<fmt:formatNumber value="${nljProject.depreciationFee/(glysPeoject.depreciationFee+gcysPeoject.depreciationFee+zsysPeoject.depreciationFee)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>业务招待费&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.hospitalityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.hospitalityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.hospitalityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.hospitalityFee+gcysPeoject.hospitalityFee+zsysPeoject.hospitalityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.hospitalityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.hospitalityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.hospitalityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.hospitalityFee+gcsjPeoject.hospitalityFee+zssjPeoject.hospitalityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.hospitalityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.hospitalityFee+gcysPeoject.hospitalityFee+zsysPeoject.hospitalityFee != 0.0}">
		    	<fmt:formatNumber value="${nljProject.hospitalityFee/(glysPeoject.hospitalityFee+gcysPeoject.hospitalityFee+zsysPeoject.hospitalityFee)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>差旅费&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.travelFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.travelFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.travelFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.travelFee+gcysPeoject.travelFee+zsysPeoject.travelFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.travelFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.travelFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.travelFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.travelFee+gcsjPeoject.travelFee+zssjPeoject.travelFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.travelFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.travelFee+gcysPeoject.travelFee+zsysPeoject.travelFee != 0.0}">
		    	<fmt:formatNumber value="${nljProject.travelFee/(glysPeoject.travelFee+gcysPeoject.travelFee+zsysPeoject.travelFee)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>办公费&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.officeFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.officeFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.officeFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.officeFee+gcysPeoject.officeFee+zsysPeoject.officeFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.officeFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.officeFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.officeFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.officeFee+gcsjPeoject.officeFee+zssjPeoject.officeFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.officeFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.officeFee+gcysPeoject.officeFee+zsysPeoject.officeFee != 0.0}">
		    	<fmt:formatNumber value="${nljProject.officeFee/(glysPeoject.officeFee+gcysPeoject.officeFee+zsysPeoject.officeFee)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>审计费、律师费&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.auditFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.auditFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.auditFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.auditFee+gcysPeoject.auditFee+zsysPeoject.auditFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.auditFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.auditFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.auditFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.auditFee+gcsjPeoject.auditFee+zssjPeoject.auditFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.auditFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.auditFee+gcysPeoject.auditFee+zsysPeoject.auditFee != 0.0}">
		    	<fmt:formatNumber value="${nljProject.auditFee/(glysPeoject.auditFee+gcysPeoject.auditFee+zsysPeoject.auditFee)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>董事会费&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.boardFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.boardFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.boardFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.boardFee+gcysPeoject.boardFee+zsysPeoject.boardFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.boardFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.boardFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.boardFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.boardFee+gcsjPeoject.boardFee+zssjPeoject.boardFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.boardFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.boardFee+gcysPeoject.boardFee+zsysPeoject.boardFee != 0.0}">
		    	<fmt:formatNumber value="${nljProject.boardFee/(glysPeoject.boardFee+gcysPeoject.boardFee+zsysPeoject.boardFee)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>印花税&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.stampDuty }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.stampDuty }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.stampDuty }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.stampDuty+gcysPeoject.stampDuty+zsysPeoject.stampDuty }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.stampDuty }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.stampDuty }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.stampDuty }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.stampDuty+gcsjPeoject.stampDuty+zssjPeoject.stampDuty }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.stampDuty }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.stampDuty+gcysPeoject.stampDuty+zsysPeoject.stampDuty != 0.0}">
		    	<fmt:formatNumber value="${nljProject.stampDuty/(glysPeoject.stampDuty+gcysPeoject.stampDuty+zsysPeoject.stampDuty)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>会议费&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.conferenceFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.conferenceFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.conferenceFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.conferenceFee+gcysPeoject.conferenceFee+zsysPeoject.conferenceFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.conferenceFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.conferenceFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.conferenceFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.conferenceFee+gcsjPeoject.conferenceFee+zssjPeoject.conferenceFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.conferenceFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.conferenceFee+gcysPeoject.conferenceFee+zsysPeoject.conferenceFee != 0.0}">
		    	<fmt:formatNumber value="${nljProject.conferenceFee/(glysPeoject.conferenceFee+gcysPeoject.conferenceFee+zsysPeoject.conferenceFee)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>其他&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.otherFee+gcysPeoject.otherFee+zsysPeoject.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.otherFee+gcsjPeoject.otherFee+zssjPeoject.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.otherFee+gcysPeoject.otherFee+zsysPeoject.otherFee != 0.0}">
		    	<fmt:formatNumber value="${nljProject.otherFee/(glysPeoject.otherFee+gcysPeoject.otherFee+zsysPeoject.otherFee)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>管理费用合计(不<br>含房产折旧、土地<br>税、房产税)&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.glfHj }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.glfHj }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.glfHj }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.glfHj+gcysPeoject.glfHj+zsysPeoject.glfHj }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.glfHj }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.glfHj }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.glfHj }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.glfHj+gcsjPeoject.glfHj+zssjPeoject.glfHj }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.glfHj }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${glysPeoject.glfHj+gcysPeoject.glfHj+zsysPeoject.glfHj != 0.0}">
		    	<fmt:formatNumber value="${nljProject.glfHj/(glysPeoject.glfHj+gcysPeoject.glfHj+zsysPeoject.glfHj)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>房产、土地摊销&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.estateFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.estateFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.estateFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.estateFee+gcysPeoject.estateFee+zsysPeoject.estateFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.estateFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.estateFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.estateFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.estateFee+gcsjPeoject.estateFee+zssjPeoject.estateFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.estateFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>&nbsp;</td>
		  </tr>
		  <tr>
		    <td>土地税&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.landTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.landTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.landTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.landTax+gcysPeoject.landTax+zsysPeoject.landTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.landTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.landTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.landTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.landTax+gcsjPeoject.landTax+zssjPeoject.landTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.landTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>&nbsp;</td>
		  </tr>
		  <tr>
		    <td>房产税&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.propertyTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcysPeoject.propertyTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zsysPeoject.propertyTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.propertyTax+gcysPeoject.propertyTax+zsysPeoject.propertyTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.propertyTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.propertyTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.propertyTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.propertyTax+gcsjPeoject.propertyTax+zssjPeoject.propertyTax+glysPeoject.landTax+glysPeoject.propertyTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.propertyTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>&nbsp;</td>
		  </tr>
		  <tr>
		    <td>费用合计&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td><fmt:formatNumber value="${glysPeoject.glfHj+gcysPeoject.glfHj+glysPeoject.landTax+gcysPeoject.landTax+zsysPeoject.landTax+glysPeoject.propertyTax+gcysPeoject.propertyTax+zsysPeoject.propertyTax+glysPeoject.estateFee+gcysPeoject.estateFee+zsysPeoject.estateFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.glfHj+glsjPeoject.estateFee+glsjPeoject.landTax+glsjPeoject.propertyTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${gcsjPeoject.glfHj+gcsjPeoject.estateFee+gcsjPeoject.landTax+gcsjPeoject.propertyTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${zssjPeoject.glfHj+zssjPeoject.estateFee+zssjPeoject.landTax+zssjPeoject.propertyTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${glsjPeoject.glfHj+glsjPeoject.estateFee+glsjPeoject.landTax+glsjPeoject.propertyTax+gcsjPeoject.glfHj+gcsjPeoject.estateFee+gcsjPeoject.landTax+gcsjPeoject.propertyTax+zssjPeoject.glfHj+zssjPeoject.estateFee+zssjPeoject.landTax+zssjPeoject.propertyTax  }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljProject.glfHj+nljProject.estateFee+nljProject.landTax+nljProject.propertyTax }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>&nbsp;</td>
		  </tr>
		  <tr>
		    <td colspan="12">&nbsp;</td>
		  </tr>
		  <tr>
		    <td align="center">经营成本</td>
		    <td align="center">本月</td>
		    <td align="center">年累计</td>
		    <td align="center">预算金额</td>
		    <td align="center">占全年%</td>
		    <td>&nbsp;</td>
		    <td align="center">经营收入</td>
		    <td align="center">本月</td>
		    <td align="center">年累计</td>
		    <td align="center">预算金额</td>
		    <td align="center">占全年%</td>
		  </tr>
		  <tr>
		    <td>保洁费</td>
		    <td><fmt:formatNumber value="${byCost.cleanFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljCost.cleanFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeCost.cleanFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeCost.cleanFee != 0.0 }">
		    	<%--
		    	${(nljCost.cleanFee+ysjeCost.cleanFee)/ysjeCost.cleanFee*100 }% --%>
		    	<fmt:formatNumber value="${(nljCost.cleanFee)/ysjeCost.cleanFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		    <td>&nbsp;</td>
		    <td>房租收入</td>
		    <td><fmt:formatNumber value="${income.rentFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljIncome.rentFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeIncome.rentFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeIncome.rentFee != 0.0 }">
		    	<%--
		    	${(nljIncome.rentFee+ysjeIncome.rentFee)/ysjeIncome.rentFee*100 }% --%>
		    	<fmt:formatNumber value="${(nljIncome.rentFee)/ysjeIncome.rentFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>保安费</td>
		    <td><fmt:formatNumber value="${byCost.securityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljCost.securityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeCost.securityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeCost.securityFee != 0.0 }">
		    	<%--
		    	${(nljCost.securityFee+ysjeCost.securityFee)/ysjeCost.securityFee*100 }% --%>
		    	<fmt:formatNumber value="${(nljCost.securityFee)/ysjeCost.securityFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		    <td>&nbsp;</td>
		    <td>装修管理费</td>
		    <td><fmt:formatNumber value="${income.decorationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljIncome.decorationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeIncome.decorationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeIncome.decorationFee != 0.0 }">
		    	<%--
		    	${(nljIncome.decorationFee+ysjeIncome.decorationFee)/ysjeIncome.decorationFee*100 }% --%>
		    	<fmt:formatNumber value="${(nljIncome.decorationFee)/ysjeIncome.decorationFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	% 
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>通讯费</td>
		    <td><fmt:formatNumber value="${byCost.communicationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljCost.communicationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeCost.communicationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeCost.communicationFee != 0.0 }">
		    	<%--
		    	${(nljCost.communicationFee+ysjeCost.communicationFee)/ysjeCost.communicationFee*100 }% --%>
		    	<fmt:formatNumber value="${(nljCost.communicationFee)/ysjeCost.communicationFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		    <td>&nbsp;</td>
		    <td>通讯费</td>
		    <td><fmt:formatNumber value="${income.communicationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljIncome.communicationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeIncome.communicationFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeIncome.communicationFee != 0.0 }">
		    	<%--
		    	${(nljIncome.communicationFee+ysjeIncome.communicationFee)/ysjeIncome.communicationFee*100 }% --%>
		    	<fmt:formatNumber value="${(nljIncome.communicationFee)/ysjeIncome.communicationFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	% 
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>水费</td>
		    <td><fmt:formatNumber value="${byCost.waterFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljCost.waterFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeCost.waterFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeCost.waterFee != 0.0 }">
		    	<%--
		    	${(nljCost.waterFee+ysjeCost.waterFee)/ysjeCost.waterFee*100 }% --%>
		    	<fmt:formatNumber value="${(nljCost.waterFee)/ysjeCost.waterFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		    <td>&nbsp;</td>
		    <td>电费</td>
		    <td><fmt:formatNumber value="${income.electricityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljIncome.electricityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeIncome.electricityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeIncome.electricityFee != 0.0 }">
		    	<%--
		    	${(nljIncome.electricityFee+ysjeIncome.electricityFee)/ysjeIncome.electricityFee*100 }%
		    	 --%>
		    	 <fmt:formatNumber value="${(nljIncome.electricityFee)/ysjeIncome.electricityFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	 %
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>电费</td>
		    <td><fmt:formatNumber value="${byCost.electricityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljCost.electricityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeCost.electricityFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeCost.electricityFee != 0.0 }">
		    	<%--
		    	${(nljCost.electricityFee+ysjeCost.electricityFee)/ysjeCost.electricityFee*100 }% --%>
		    	<fmt:formatNumber value="${(nljCost.electricityFee)/ysjeCost.electricityFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		    <td>&nbsp;</td>
		    <td>停车管理费</td>
		    <td><fmt:formatNumber value="${income.stopCarFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljIncome.stopCarFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeIncome.stopCarFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeIncome.stopCarFee != 0.0 }">
		    	<%-- 
		    	${(nljIncome.stopCarFee+ysjeIncome.stopCarFee)/ysjeIncome.stopCarFee*100 }%--%>
		    	<fmt:formatNumber value="${(nljIncome.stopCarFee)/ysjeIncome.stopCarFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <%--
		  <tr>
		    <td>电费</td>
		    <td>${byCost.electricityFee }&nbsp;</td>
		    <td>${nljCost.electricityFee }&nbsp;</td>
		    <td>${ysjeCost.electricityFee }&nbsp;</td>
		    <td>
		    <c:if test="${ysjeCost.electricityFee != 0.0 }">
		    	${(nljCost.electricityFee+ysjeCost.electricityFee)/ysjeCost.electricityFee*100 }% 
		    	<fmt:formatNumber value="${(nljCost.electricityFee)/ysjeCost.electricityFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		    <td>&nbsp;</td>
		    <td>停车管理费</td>
		    <td>${income.stopCarFee }&nbsp;</td>
		    <td>${nljIncome.stopCarFee }&nbsp;</td>
		    <td>${ysjeIncome.stopCarFee }&nbsp;</td>
		    <td>
		    <c:if test="${ysjeIncome.stopCarFee != 0.0 }">
		    	${(nljIncome.stopCarFee+ysjeIncome.stopCarFee)/ysjeIncome.stopCarFee*100 }% 
		    	<fmt:formatNumber value="${(nljIncome.stopCarFee)/ysjeIncome.stopCarFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		   --%>
		  <tr>
		    <td>燃气费</td>
		    <td><fmt:formatNumber value="${byCost.gasFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljCost.gasFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeCost.gasFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeCost.gasFee != 0.0 }">
		    	<%--
		    	${(nljCost.gasFee+ysjeCost.gasFee)/ysjeCost.gasFee*100 }% --%>
		    	<fmt:formatNumber value="${(nljCost.gasFee)/ysjeCost.gasFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		  </tr>
		  <tr>
		    <td>物料</td>
		    <td><fmt:formatNumber value="${byCost.materials }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljCost.materials }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeCost.materials }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeCost.materials != 0.0 }">
		    	<%--
		    	${(nljCost.materials+ysjeCost.materials)/ysjeCost.materials*100 }% --%>
		    	<fmt:formatNumber value="${(nljCost.materials)/ysjeCost.materials*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		    <td>&nbsp;</td>
		    <td>其他收入</td>
		    <td><fmt:formatNumber value="${income.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljIncome.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeIncome.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeIncome.otherFee != 0.0 }">
		    	<%--
		    	${(nljIncome.otherFee+ysjeIncome.otherFee)/ysjeIncome.otherFee*100 }% --%>
		    	<fmt:formatNumber value="${(nljIncome.otherFee)/ysjeIncome.otherFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>设备维修、保养、检测费</td>
		    <td><fmt:formatNumber value="${byCost.eqmCheckFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljCost.eqmCheckFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeCost.eqmCheckFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeCost.eqmCheckFee != 0.0 }">
		    	<%--
		    	${(nljCost.eqmCheckFee+ysjeCost.eqmCheckFee)/ysjeCost.eqmCheckFee*100 }% --%>
		    	<fmt:formatNumber value="${(nljCost.eqmCheckFee)/ysjeCost.eqmCheckFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		    <td>&nbsp;</td>
		    <td>供暖费</td>
		    <td><fmt:formatNumber value="${income.heatFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljIncome.heatFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeIncome.heatFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeIncome.heatFee != 0.0 }">
		    	<%--
		    	${(nljIncome.heatFee+ysjeIncome.heatFee)/ysjeIncome.heatFee*100 }% --%>
		    	<fmt:formatNumber value="${(nljIncome.heatFee)/ysjeIncome.heatFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	% 
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>其他（保险及绿化）</td>
		    <td><fmt:formatNumber value="${byCost.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljCost.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeCost.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeCost.otherFee != 0.0 }">
		    	<%-- 
		    	${(nljCost.otherFee+ysjeCost.otherFee)/ysjeCost.otherFee*100 }%--%>
		    	<fmt:formatNumber value="${(nljCost.otherFee)/ysjeCost.otherFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		    <td>&nbsp;</td>
		    <td>物业费</td>
		    <td><fmt:formatNumber value="${income.propertyFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljIncome.propertyFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeIncome.propertyFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${ysjeIncome.propertyFee != 0.0 }">
		    	<%--
		    	${(nljIncome.propertyFee+ysjeIncome.propertyFee)/ysjeIncome.propertyFee*100 }% --%>
		    	<fmt:formatNumber value="${(nljIncome.propertyFee)/ysjeIncome.propertyFee*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
		  <tr>
		    <td>合计</td>
		    <td><fmt:formatNumber value="${byCost.cleanFee+byCost.securityFee+byCost.communicationFee+byCost.waterFee+byCost.electricityFee+byCost.materials+byCost.eqmCheckFee+byCost.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljCost.cleanFee+nljCost.securityFee+nljCost.communicationFee+nljCost.waterFee+nljCost.electricityFee+nljCost.materials+nljCost.eqmCheckFee+nljCost.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeCost.cleanFee+ysjeCost.securityFee+ysjeCost.communicationFee+ysjeCost.waterFee+ysjeCost.electricityFee+ysjeCost.materials+ysjeCost.eqmCheckFee+ysjeCost.otherFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${(ysjeCost.cleanFee+ysjeCost.securityFee+ysjeCost.communicationFee+ysjeCost.waterFee+ysjeCost.electricityFee+ysjeCost.materials+ysjeCost.eqmCheckFee+ysjeCost.otherFee) != 0.0 }">
		    <fmt:formatNumber value="${(nljCost.cleanFee+nljCost.securityFee+nljCost.communicationFee+nljCost.waterFee+nljCost.electricityFee+nljCost.materials+nljCost.eqmCheckFee+nljCost.otherFee)/(ysjeCost.cleanFee+ysjeCost.securityFee+ysjeCost.communicationFee+ysjeCost.waterFee+ysjeCost.electricityFee+ysjeCost.materials+ysjeCost.eqmCheckFee+ysjeCost.otherFee)*100 }" pattern="#.##"></fmt:formatNumber>
		    %
		    </c:if>
		    &nbsp;</td>
		    <td>&nbsp;</td>
		    <td>合计</td>
		    <td><fmt:formatNumber value="${income.rentFee+income.decorationFee+income.communicationFee+income.electricityFee+income.stopCarFee+income.otherFee+income.heatFee+income.propertyFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${nljIncome.rentFee+nljIncome.decorationFee+nljIncome.communicationFee+nljIncome.electricityFee+nljIncome.stopCarFee+nljIncome.otherFee+nljIncome.heatFee+nljIncome.propertyFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td><fmt:formatNumber value="${ysjeIncome.rentFee+ysjeIncome.decorationFee+ysjeIncome.communicationFee+ysjeIncome.electricityFee+ysjeIncome.stopCarFee+ysjeIncome.otherFee+ysjeIncome.heatFee+ysjeIncome.propertyFee }" pattern="#.##"></fmt:formatNumber>&nbsp;</td>
		    <td>
		    <c:if test="${(ysjeIncome.rentFee+ysjeIncome.decorationFee+ysjeIncome.communicationFee+ysjeIncome.electricityFee+ysjeIncome.stopCarFee+ysjeIncome.otherFee+ysjeIncome.heatFee+ysjeIncome.propertyFee) != 0.0 }">
		    	<fmt:formatNumber value="${(nljIncome.rentFee+nljIncome.decorationFee+nljIncome.communicationFee+nljIncome.electricityFee+nljIncome.stopCarFee+nljIncome.otherFee+nljIncome.heatFee+nljIncome.propertyFee)/(ysjeIncome.rentFee+ysjeIncome.decorationFee+ysjeIncome.communicationFee+ysjeIncome.electricityFee+ysjeIncome.stopCarFee+ysjeIncome.otherFee+ysjeIncome.heatFee+ysjeIncome.propertyFee)*100 }" pattern="#.##"></fmt:formatNumber>
		    	%
		    </c:if>
		    &nbsp;</td>
		  </tr>
	</table>
</form>
</body>
</html>
