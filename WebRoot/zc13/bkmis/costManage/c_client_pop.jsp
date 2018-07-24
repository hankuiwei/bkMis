<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>客户</title>
    	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
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
  </head>
  
  <body>
	<form method="post" name="formEidt" id="formEdit">
	<table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="165" nowrap="nowrap" class="form_line">客户</td>
					<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
					<td width="1080" class="form_line2"></td>
					<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
       			</tr>
   			</table>
   		</td>
 	  </tr>
	  <tr>
	  	<td><table width="100%" cellpadding="0" cellspacing="0" class="menu_tab2">
			<tr>
		  	    <td align=""><table width="100%" cellpadding="0" cellspacing="0">
		  			<tr>
		  				<td class="txt" nowrap="nowrap">
					       <img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					    </td>
		  				<td align="left" nowrap>客户名称：<input type="text" name="clientName" value="${clientName }"></td>
		  				<td nowrap>房号：<input type="text" name="roomCode" value="${roomCode }"></td>
		  				<td width="30%">&nbsp;</td>
		  				<td align="right"><input type="button" value="查询" onclick="cx()" class="button"></td>
		  			</tr>
		  			<tr>
						<td colspan="5">
							<span style=" color: blue;">注：此处显示的客户为已经入住的客户</span>
						</td>
					</tr>
		  		</table></td>		
		    </tr>
		  	<tr height="77%">
			    <td valign="top">
			    	<!-- 表单内容区域 -->
					<table width="100%" height="100%" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height="95%">
			        		<td width="100%">
						  		<div id=div1 class="RptDiv"><table width="100%" cellpadding="0" cellspacing="0" class="RptTable">
			    	<tr>
			    		<th class="RptTableHeadCellFullLock"  width="4%" align="center">选择</th>
			    		<th class="RptTableHeadCellFullLock"  width="4%" align="center">序号</th>
			    		<th class="RptTableHeadCellLock"  width="15%">客户名称</th>
			    		<th class="RptTableHeadCellLock"  width="15%">客户代码</th> 
			    		<th class="RptTableHeadCellLock"  width="15%">房间号</th>
			    		<th class="RptTableHeadCellLock"  width="15%">客户类别</th>
			    		<th class="RptTableHeadCellLock"  width="15%">联系人</th>
			    		<th class="RptTableHeadCellLock"  width="15%">联系电话</th>
			    		<th class="RptTableHeadCellLock"  width="15%">单位名称</th> 
			    	</tr>
			    	<c:choose>
						<c:when test="${empty list}">
							<tr><td colspan="12" align="center" class="head_form1"><font color="red">暂时没有信息!</font></td></tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${list}" var="c" varStatus="vs">
								<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
						    		<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="client" value="${c.id },${c.name }"></td>
						    		<td class="RptTableBodyCellLock"  align="center">${vs.index+1 }</td>
						    		<td class="RptTableBodyCell">&nbsp;${c.name }</td>
						    		<td class="RptTableBodyCell">&nbsp;${c.code }</td>
						    		<td class="RptTableBodyCell">&nbsp;
						    			<c:forEach items="${compacts}" var="p">
						    				<c:if test="${p.clientId==c.id }">${p.roomCodes }</c:if>
						    			</c:forEach>
						    		</td>
						    		<td class="RptTableBodyCell">&nbsp;${c.clientType }</td>
						    		<td class="RptTableBodyCell">&nbsp;${c.linkMan }</td>
						    		<td class="RptTableBodyCell">&nbsp;${c.phone }</td>
						    		<td class="RptTableBodyCell">&nbsp;${c.unitName }</td>
						    	</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
			    </table></div>
							</td>
		    			</tr>
		    		</table>
		    	</td>
		    </tr>
		    <tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr><td class="form_line3">&nbsp;</td>
							<td class="form_line3">&nbsp;${pageHTML }</td>
							<td class="form_line3">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr><td align="center"><input type="button" class="button" value="确定" onclick="qd();"></td></tr>
	    </table></td>
	    </tr>
	</table>
    </form>
  </body>
  <script type="text/javascript">
  	function cx(){
  		formEdit.action ="<%=path%>/bill.do?method=queryClient";
  		formEdit.submit();
  	}
  	function qd(){
  		var result = new Object();
		var client = document.getElementsByName("client");
		var id = "";
		var name = "";
		for(var i=0;i<client.length;i++){
			if(client[i].checked){
				var temp = client[i].value.split(",");
				id += ","+temp[0];					
				name += ","+temp[1];
			}
		}
		if(id != "" && id.charAt(0) == ','){
			id = id.substring(1,id.length);
		}
		if(name != "" && name.charAt(0) == ','){
			name = name.substring(1,name.length);
		}
		result.id = id;
		result.name = name;
		window.returnValue=result;
		window.close();
  	}
  </script>
</html>
