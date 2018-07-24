<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="loading" style="position:absolute;width:100%;height:100%;background:#a0c0ff;filter:alpha(opacity = 50);display:none;"></div>
	<div id="loading2" style="position:absolute;width:20%;height:20%; display:none; top: 150px;left:35%;" align="center">
		<table width="100" height="100"  cellspacing="0" cellpadding="0" align="center">
			<tr height="100%">
				<td align="center"><img src="<%=path %>/resources/images/loading1.gif"   />
				<br />
				<span style="font: 14px;color:#000000;">正在加载，请稍候...</span></td>
			</tr>
		</table>
</div>