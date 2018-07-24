<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*,com.zc13.util.*,java.util.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.zc13.util.GlobalMethod"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List list = (List)request.getAttribute("inputDatilList");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>详细库存盘点表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <script type="text/javascript" src="<%=path %>/resources/js/transform.js"></script>
  <script language="javascript">
	//document.all.WebBrowser.ExecWB(7,1);
	//window.close();
	//使界面最大化
	maxWin();
	function maxWin()
	{
	      var aw = screen.availWidth;
	      var ah = screen.availHeight;
	      window.moveTo(0, 0);
	      window.resizeTo(aw, ah);
	}
	function formatNum(strNum) {
    	if(strNum.length <= 3){  
        	return strNum;  
    	}  
    	if(!/^(\+|-)?(\d+)(\.\d+)?$/.test(strNum)){  
        	return strNum;  
		}  
		var a = RegExp.$1, b = RegExp.$2, c = RegExp.$3;  
    	var re = new RegExp();  
    	re.compile("(\\d)(\\d{3})(,|$)");  
    	while(re.test(b)) {  
        	b = b.replace(re, "$1,$2$3");  
    	}
    	return a +""+ b +""+ c;  
	}
</script>
<style type="text/css">
.STYLE1 {font-size: 12px}
.style3 {text-decoration: underline;}
</style>
<OBJECT   id=WebBrowser   classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2   height=0   width=0></OBJECT>   
<style>
@media print {
.noprint {display:none}
}
.STYLE2 {font-size: 12}

.zsTd{
	border: 1px solid #000000;
	text-align:center;
}
.fisttrTd{
	border-top :1px solid #000000;
	border-right: 1px solid #000000;
	border-bottom :1px solid #000000;
	text-align:center;
}.fistTd{
	border-left :1px solid #000000;
	border-right: 1px solid #000000;
	border-bottom :1px solid #000000;
	text-align:center;
}
.nomalTd{
	border-right: 1px solid #000000;
	border-bottom :1px solid #000000;
	text-align:center;
}
.rightTd{
	border-bottom :1px solid #000000;
	text-align:center;
}
.botTd{
	border-left: 1px solid #000000;
	border-bottom :1px solid #000000;
	text-align:center;
}
.form { 
	border-bottom: 1px solid #000000 ;
	border-right: 1px solid #000000;
	font-size: 14px;
	height: 25px;
	text-align:center;
}
</style>
<body>
    <form action="" name="actionForm" id="" method="post">
    	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
    	<tr>
			<td>
				<table width="95%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
	  	 			<tr class="noprint">
	         			<td>&nbsp;</td>    
                   		<TD width="81" align=right><input type="button" onclick="javascript:window.print()"  value="打印"></TD>
	                   	<TD width="81" align=right><input type="button" onclick="window.close();"  value="返回"></TD>
	    			</tr>
	    		</table>
	    	</td>
	    </tr>
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="95%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
				        <th align="center"> <font style=" font-size:30px">材料出入库核算表明细</font>&nbsp;</th>
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
					            	<td align="center" class="style3">
					            		统计日期：${bl.beginDate }&nbsp;至&nbsp;${bl.endDate }&nbsp;&nbsp;账期：${bl.year }年${bl.month }月
					            	</td>
								</tr>
								
							 	 <tr>
					           		 <td height="10" colspan="1"></td>
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
					        		
							   			<table class = "" border="0" cellpadding="0" cellspacing="0">
							   			
					              			<tr>
												<th  style="height:30px;border-top :1px solid #000000;" class="fistTd">序号</th>
												<th class = "fisttrTd">材料名称</th>
												<th class = "fisttrTd">材料编号</th>
												<th class = "fisttrTd">单位</th>
												<th class = "fisttrTd">规格型号</th>
												<th class = "fisttrTd">单价</th>
												<th class = "fisttrTd">期初数量</th>
												<th class = "fisttrTd">期初金额</th>
												<th class = "fisttrTd">本期入库数量</th>
												<th class = "fisttrTd">本期入库金额</th>
												<th class = "fisttrTd">本期出库数量</th>
												<th class = "fisttrTd">本期出库金额</th>
												<th class = "fisttrTd">期末数量</th>
												<th class = "fisttrTd">期末金额</th>
											</tr>
											<c:set var="beginMoney" value="0" ></c:set>
											<c:set var="inputMomey" value="0" ></c:set>
											<c:set var="outputMoney" value="0" ></c:set>
											<c:set var="balance" value="0" ></c:set>
											<c:choose>
											<c:when test="${empty accountForm.detailAccountList}">
											</c:when>
											<c:otherwise>
												<c:forEach items="${accountForm.detailAccountList}" var="dl" varStatus="vs">
													<c:set var="beginMoney" value="${dl.qiMoney+beginMoney }" ></c:set>
													<c:set var="inputMomey" value="${dl.benInMoney+inputMomey }" ></c:set>
													<c:set var="outputMoney" value="${dl.benOutMoney+outputMoney }" ></c:set>
													<c:set var="balance" value="${dl.balance+balance }" ></c:set>
													<tr>
														<td class="fistTd" align="center" title="${vs.count }">${vs.count }&nbsp;</td>
														<td class="form" align="center" title="${dl.name }">${dl.name }&nbsp;</td>
														<td class="form" align="center" title="${dl.code }">${dl.code }&nbsp;</td>
														<td class="form" align="center" title="${dl.unit }">${dl.unit }&nbsp;</td>
														<td class="form" align="center" title="${dl.spec }">${dl.spec }&nbsp;</td>
														<td class="form" align="center" title="${dl.unitPrice }"><script>document.write(formatNum(parseFloat(${dl.unitPrice }).toFixed(2).toString()));</script>&nbsp;</td>
														<td class="form" align="center" title="${dl.qiAmount }">${dl.qiAmount }&nbsp;</td>
														<td class="form" align="center" title="${dl.qiMoney }"><script>document.write(formatNum(parseFloat(${dl.qiMoney }).toFixed(2).toString()));</script>&nbsp;</td>
														<td class="form" align="center" title="${dl.benInAmount }">${dl.benInAmount }&nbsp;</td>
														<td class="form" align="center" title="${dl.benInMoney }"><script>document.write(formatNum(parseFloat(${dl.benInMoney }).toFixed(2).toString()));</script>&nbsp;</td>
														<td class="form" align="center" title="${dl.benOutAmount }">${dl.benOutAmount }&nbsp;</td>
														<td class="form" align="center" title="${dl.benOutMoney }"><script>document.write(formatNum(parseFloat(${dl.benOutMoney }).toFixed(2).toString()));</script>&nbsp;</td>
														<td class="form" align="center" title="${dl.residue }">${dl.residue }&nbsp;</td>
														<td class="form" align="center" title="${dl.balance }"><script>document.write(formatNum(parseFloat(${dl.balance }).toFixed(2).toString()));</script>&nbsp;</td>
													</tr>
												</c:forEach>
											</c:otherwise>
										</c:choose>
					             		</table>
					             		
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
								  <td>&nbsp;</td>
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
