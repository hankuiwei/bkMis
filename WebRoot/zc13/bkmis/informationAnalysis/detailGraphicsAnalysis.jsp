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
    
    <title>客户信息图形分析</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
		function selectGra(){
			sl();
			document.graphicForm.action = "analysisCus.do?method=selectGraphicByParam";
			document.graphicForm.submit();
		}
		function sl(){
			var index  = document.getElementById("type").selectedIndex;
			var text = document.getElementById("type").options[index].text;
			document.getElementById("selectValue").value = text;
		}
	</script>
  </head>
  
  <body style="overflow-y:hidden;"> 
     <form action="" method="post" name="graphicForm">
     	  <table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
			<tr>
	    		<td height = "5"></td>
	  		</tr>
	  		<tr>
	    		<td>
	    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
	      				<tr>
	        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
							<td width="165" nowrap="nowrap" class="form_line">客户信息分析</td>
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
					         		<td colspan="6" align="left" class="head_one">客户信息分析图</td>
					         	</tr>
					         	<c:forEach items="${list}" var="al">
					          	<tr>
					            	<td align="right" class="head_left">
					            		统计日期：
					            	</td>
					            	<td class="head_form1">&nbsp;
					            		<input type="hidden" name="accountId" id="accountId" value="${accountId }"/>
					            		<input type="hidden" name="selectValue" id="selectValue"/>
					            		<input type="hidden" name="lpId" id="lpId" value="${al.lpId }"/>
					            		<input type="hidden" name="beginDate" id="beginDate" value="${al.beginDate }"/>${al.beginDate }
										&nbsp;至&nbsp;${al.endDate }
										<input type="hidden" name="endDate" id="endDate" value="${al.endDate }"/>
					            	</td>
					            	<td align="right" class="head_form1">
					            		创建日期：
					            	</td>
					            	<td class="head_form1">&nbsp;${al.createDate }
					            		<input type="hidden" name="createDate" id="createDate" value="${al.createDate }"/>
					            	</td>
					            	<td align="right" class="head_form1">
					            		分析图条件：
					            	</td>
					            	<td class="head_form1">&nbsp;
					            		<select id="type" name="type" onchange="selectGra();">
					            			<option value="">--全部--</option>
					            			<option value="client_type">客户类别</option>
					            			<option value="trade">所属行业</option>
					            		</select>
					            	</td>
								</tr>
								</c:forEach>
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
								               <td>
								               		<img src="${inRealPath }">
								               </td>
								               <td>
								               		<img src="${outRealPath }">
								               </td>
											</tr>
					             		</table>
					             	</div>
								 </td>
		     		 		  </tr>
				            </table>
			           </td>
		          </tr>
		          <tr>
		          	<td align="center">
		          		<input type="button" name="retur" id="retur" onclick="javascript:history.go(-1)" value="返回" class="button">
		          	</td>
		          </tr>
		 </table>
	 </td>
   </tr>
 </table>	
     </form>
  </body>
</html>
