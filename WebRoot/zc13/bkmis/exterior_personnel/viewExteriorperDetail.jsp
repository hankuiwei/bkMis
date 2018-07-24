<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查看留学人员的信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <!-- 使用日期控件时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<!-- 使用单元格在线编辑功能时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
    <style type="text/css">
	.initiated_event_photo img{width:400px; height:320px; margin-left:78px; display:none;}
	#newPreview{float:left; display:none;width:90%; height:auto; text-align:left; margin:9px 0 0 110px; font-size:14px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);}
	</style>
	<script type="text/javascript">
	//加载已有的图片
		function loadPic(){
		   var s = $("#exitsUrl").attr("value",s);
		   if(s != null && s != ""){
		    if(navigator.userAgent.indexOf("MSIE 7.0")>=1){
				 var newPreview = document.getElementById("newPreview");  
				 newPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = s;  
				 newPreview.style.width = "400px";   
 				 newPreview.style.height = "320px"; 
				 newPreview.style.display="block";
				 //newPreview.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src=s,sizingMethod='scale')"; 
			}
			if(window.navigator.userAgent.indexOf("MSIE 8.0")>=1){
				var posiation = document.getElementById("pic");
				var index = s.lastIndexOf("\\");
				var picName = s.substring(index+1);
				var img = document.createElement("img");
				posiation.appendChild(img);
				img.src = "<%=path%>/upExteriorPer/"+picName;
		    }
		 }
		}
	</script>
  </head>
  
  <body onload="loadPic()">
   	 <form action="" name="viewForm" method="post">
   	 	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
    			<tr>
    				<td height="5"></td>
  				</tr>
  				<c:forEach items="${exteriorForm.exteriorperList}" var="ep">
  				<tr>
    			<td>
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
      					<tr>
        					<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
							<td width="165" nowrap="nowrap" class="form_line">留学人员 ${ep.name }的信息</td>
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
    						<td colspan="4" align="left" class="head_one"><font size="+1">基本信息</font></td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">
    							姓名：
    							<input type="hidden" id="id" name="id" value="${ep.id }"/>
    						</td>
    						<td class="head_form1">&nbsp;
    							<input id="name" type="text" name="name" value="${ep.name }" disabled="disabled"/>
    							<font color="red">*</font>
    						</td>
    						<td align="right" class="head_form1">性别：</td>
    						<td class="head_form1">&nbsp;
    							<select id="sex" name="sex" style="width:130px;" disabled="disabled">
    								<option <c:if test="${ep.sex == '男' }">selected</c:if> value="男"
    								>
    								男
    								</option>
    								<option <c:if test="${ep.sex == '女' }">selected</c:if> value="女"
    								>
    								女
    								</option>
    							</select>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">民族：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="natioanality" value="${ep.natioanality }" disabled="disabled"/>
    						</td>
    						<td align="right" class="head_form1">出生日期：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="birtyday" readonly id="birtyday" value="${ep.birtyday }" disabled="disabled"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">年龄：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="age" <c:if test="${ep.age ne '0'}"> value="${ep.age }" </c:if> disabled="disabled"/>
    						</td>
    						<td align="right" class="head_form1">籍贯：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="nativePalace" value="${ep.nativePalace }" disabled="disabled"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">
    							国外学位：
    						</td>
    						<td  class="head_form1">&nbsp;
    							<input type="text" name="foreignDegree" value="${ep.foreignDegree }" disabled="disabled"/>
    						</td>
    						<td align="right" class="head_form1">
    							国内学位：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="nativeDegree" value="${ep.nativeDegree }" disabled="disabled"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">
    							身份证号：
    						</td>
    						<td  class="head_form1">&nbsp;
    							<input type="text" name="identityCard" value="${ep.identityCard }" disabled="disabled"/>
    						</td>
    						<td align="right" class="head_form1">
    							联系电话：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="phone" value="${ep.phone }" disabled="disabled"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">现住国家：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="currentCountry" value="${ep.currentCountry }" disabled="disabled"/>
    						</td>
    						<td align="right" class="head_form1">研究课题：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="researchTopic" value="${ep.researchTopic }" disabled="disabled"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">参与公司的方式：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="joincompanyType" value="${ep.joincompanyType }" disabled="disabled"/>
    						</td>
    						<td align="right" class="head_form1">
    							工作单位：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" id="company" name="company" value="${ep.company}" disabled="disabled"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">一寸照片：</td>
    						<td id="pic" colspan ="3" class="head_form1">&nbsp;
    						  <div class="initiated_event_photo" id="event_photo_div">
    							<img id="imageurl" src="${ep.imageUrl }"/>
    						  </div>
    						  <div id="newPreview"></div>	
    							<input id="imageUrl" type="hidden" name="imageUrl" value="${ep.imageUrl }"/>
    							<input type="hidden" id="exitsUrl" name="exitsUrl" value="${ep.imageUrl }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">研究成果：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="researchResult" value="${ep.researchResult }" disabled="disabled"/>
    						</td>
    						<td align="right" class="head_form1">研究领域：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="researchField" value="${ep.researchField }" disabled="disabled"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">曾获奖励：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="getRewart" value="${ep.getRewart }" disabled="disabled"/>
    						</td>
    						<td align="right" class="head_form1">
    							邮箱：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="mailbox" value="${ep.mailbox }" disabled="disabled"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">需要解决的问题：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" value="${ep.resolveProblem }" name="resolveProblem" disabled="disabled"/>
    						</td>
    						<td align="right" class="head_form1">联系人邮编：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" value="${ep.post }" name="post" disabled="disabled"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">归国意向：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<select id="homecomingDisposition" name="homecomingDisposition" disabled="disabled">
    								<option>--请选择--</option>
    								<option value="归国"
    									<c:if test="${ep.homecomingDisposition == '归国'}">selected</c:if>
    								>
    									归国
    								</option>
    								<option value="不归国"
    									<c:if test="${ep.homecomingDisposition == '不归国'}">selected</c:if>
    								>不归国</option>
    								<option value="待定"
    									<c:if test="${ep.homecomingDisposition == '待定'}">selected</c:if>
    								>待定</option>
    							</select>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">住址：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<input type="text" value="${ep.address }" name="address" disabled="disabled"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">享受政策：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<textarea rows="4" cols="50" name="enjoyPolicy" disabled="disabled">${ep.enjoyPolicy }</textarea>
    						</td>
    					</tr>
    				</table>
    			  </td>
    			</tr>
    			</c:forEach>
    			<tr>
    				<td colspan="4" align="center">
    					<input type="button" name="retur" value="返回" onclick="javascript:history.go(-1);" class="button"/>
    				</td>
    			</tr>
    		</table>
   	 </form>
  </body>
</html>
