<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>编辑员工信息</title>
    
    <link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <!-- 使用日期控件时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
    
    <script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/jquery.form.js"></script>
    <style type="text/css">
	.initiated_event_photo img{width:400px; height:320px; margin-left:78px; display:none;}
	#newPreview{float:left; display:none;width:90%; height:auto; text-align:left; margin:9px 0 0 110px; font-size:14px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);}
	</style>
	<script type="text/javascript">
		function edit(){
			if(!window.confirm("确定要修改吗？"))  {return ;}
			document.form1.action = "personnel.do?method=editPersonnel";
			document.form1.submit();
		}
		//上传图片
		function upPicture(){
			 var s = window.showModalDialog("zc13/bkmis/personnel/uploadPic.jsp",window,"dialogWidth=300px;dialogHeight=260px;resizable=yes;center=1");
		     var data = s;
		     //alert(s);
		     //var imageurl = document.getElementById("imageUrl").value = s;
		     if(navigator.userAgent.indexOf("MSIE 6.0")>=1){
		        if(s != "undefined" && s != "" && s != null){ 
				    document.getElementById("personnel.imageUrl").value = s;
				    $("#imgUrl").attr("src",s);
				 }
			 }
			 if(navigator.userAgent.indexOf("MSIE 7.0")>=1){
			  	 if(s != "undefined" && s != "" && s != null){
			     	 document.getElementById("personnel.imageUrl").value = s;
					 var newPreview = document.getElementById("newPreview");  
					 newPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = s;  
					 newPreview.style.width = "400px";   
	 				 newPreview.style.height = "320px"; 
					 newPreview.style.display="block";
				 }
			 }
			 if(window.navigator.userAgent.indexOf("MSIE 8.0")>=1){
			     if(s != "undefined" && s != "" && s != null){
					 var array = s.split(",");
					 document.getElementById("personnel.imageUrl").value = array[0];
					 var newPreview = document.getElementById("newPreview");
					 newPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = array[1];
					 newPreview.style.width = "400px";
					 newPreview.style.height = "320px";
					 newPreview.style.display="block";
			  	 }
			}
		}
		$("#pic").ready(function(){
				$("#retur").live("click",function(){
					var existUrl = document.getElementById("exitsUrl").value;
					var imageurl = document.getElementById("personnel.imageUrl").value;
					if(existUrl == "" && imageurl == ""){
						alert("没有图片,不能执行重设功能!");
					}else{
						if(!window.confirm("确定要删除吗？"))  {return ;}
						var newPreview = document.getElementById("newPreview");  
						newPreview.style.display="none";
						var url = "<%=path%>/personnel.do";
						if(document.getElementById("exitsUrl").value != ""){
							$.post(url,{method:"delExitsPic",imgurl:existUrl},function(data){
								//if(data == 0){
									$("#imgUrl").attr("src","");
									document.getElementById('personnel.imageUrl').value="";
									//alert("删除成功!");
								//}else{
									//alert("删除失败!");
								//}
							})
						}else if(imageurl != "" && imageurl != null){
							$.post(url,{method:"delPic",path:imageurl},function(data){
								if(data == 0){
									$("#imgUrl").attr("src","");
									document.getElementById('personnel.imageUrl').value="";
									alert("删除成功！");
								}else{
									alert("删除失败！");
								}
							})
						}
					}
				})
			});
		//对上传文件的操作
	   	function upFile(name,file,filename){
		     var s = window.showModalDialog("zc13/bkmis/personnel/upload.jsp",window,"dialogWidth=300px;dialogHeight=260px;resizable=yes;center=1");
		     if(s != null || s!= ""){
			     var data = s;
			     var arr = data.split("\\");
			     var subfilename = arr[arr.length-1];
			     var span = document.getElementById(name);
			     var sfile = document.getElementById(file).value = data;
			     var sfilname = document.getElementById(filename).value = subfilename;
			     //alert(file);
			     var newSpan = $("#" + name);
			     //下面是对文件打开保存的操作
			     var a1 = document.createElement("a");
			     a1.href = "upload.do?method=upLoad&path="+encodeURI(encodeURI(data));
			     var show1 = document.createTextNode(subfilename);
			     
			     span.appendChild(a1);
			     a1.appendChild(show1);
			     //对文件删除的操作
			     var realpath = encodeURI(encodeURI(data));
			     var newA = $("<a href='#'>删除</a>");
			     newA.click(function(event){
			     	if(!window.confirm("确定要删除吗？"))  {return false;}
			     	var url = "<%=path%>/personnel.do";
					$.post(url,{method:"delFile",path:realpath},function(data){
						newSpan.empty();//把整个span中的东西清空
						//newA.remove();
					})
					return false;
			     });
			     newSpan.append(newA);
			     //window.dialogArguments.location.reload();
			     //window.close();
		     }
		}
		
		//对现有文件的操作
		$("#loadfile").live("click",function(event){
			if(event.target.title=='del'){
				var url = "personnel.do";
				//var exitspath = event.target.id;
				exitspath = document.getElementById('pact.pactFile').value;
				span = $("#pact");
				var realpath = encodeURI(encodeURI(exitspath));
				if(!window.confirm("确定要删除吗？"))  {return false;}
				$.post(url,{method:"delFile",path:realpath},function(data){
					span.empty();
					document.getElementById('pact.pactFile').value="";
					document.getElementById('pact.pactFileName').value="";
				})
			}
			if(event.target.title=='delidFace'){
				var url = "personnel.do";
				//var exitspath = event.target.id;
				exitspath = document.getElementById('pact.identityCopy').value;
				span = $("#idFace");
				var realpath = encodeURI(encodeURI(exitspath));
				if(!window.confirm("确定要删除吗？"))  {return false;}
				$.post(url,{method:"delFile",path:realpath},function(data){
					span.empty();
					document.getElementById('pact.identityCopy').value="";
					document.getElementById('pact.identityCopyName').value="";
				})
				
			}
			if(event.target.title=='delAcademic'){
				var url = "personnel.do";
				//var exitspath = event.target.id;
				exitspath = document.getElementById('pact.academicCertificateCopy').value;
				span = $("#Academic");
				var realpath = encodeURI(encodeURI(exitspath));
				if(!window.confirm("确定要删除吗？"))  {return false;}
				$.post(url,{method:"delFile",path:realpath},function(data){
					span.empty();
					document.getElementById('pact.academicCertificateCopy').value="";
					document.getElementById('pact.academicCertificateCopyName').value="";
				})
			}
			if(event.target.title=='delduty'){
				var url = "personnel.do";
				//var exitspath = event.target.id;
				exitspath = document.getElementById('pact.qualificationCertificateCopy').value;
				span = $("#duty");
				var realpath = encodeURI(encodeURI(exitspath));
				if(!window.confirm("确定要删除吗？"))  {return false;}
				$.post(url,{method:"delFile",path:realpath},function(data){
					span.empty();
					document.getElementById('pact.qualificationCertificateCopy').value="";
					document.getElementById('pact.qualificationCertificateCopyName').value="";
				})
			}
			if(event.target.title=='delreport'){
				var url = "personnel.do";
				//var exitspath = event.target.id;
				exitspath = document.getElementById('pact.physicalExaminationReport').value;
				span = $("#report");
				var realpath = encodeURI(encodeURI(exitspath));
				if(!window.confirm("确定要删除吗？"))  {return false;}
				$.post(url,{method:"delFile",path:realpath},function(data){
					span.empty();
					document.getElementById('pact.physicalExaminationReport').value="";
					document.getElementById('pact.physicalExaminationReportName').value=""
				})
			}
			if(event.target.title=='delcard'){
				var url = "personnel.do";
				//var exitspath = event.target.id;
				exitspath = document.getElementById('pact.bankCardCopy').value;
				span = $("#card");
				var realpath = encodeURI(encodeURI(exitspath));
				if(!window.confirm("确定要删除吗？"))  {return false;}
				$.post(url,{method:"delFile",path:realpath},function(data){
					span.empty();
					document.getElementById('pact.bankCardCopy').value="";
					document.getElementById('pact.bankCardCopyName').value="";
				})
			}
			if(event.target.title=='loadPact'){
				//var href = event.target.href;
				var  path = document.getElementById("pact.pactFile").value;
				path = encodeURI(encodeURI(path));
				$("#form1").attr("action","upload.do?method=loadExitsFile&path="+path);
				$("#form1").submit();
			}
			if(event.target.title=='loadidFace'){
				//var href = event.target.href;
				var path = document.getElementById("pact.identityCopy").value;//$("#pact.identityCopy").attr("value");
				path = encodeURI(encodeURI(path));
				$("#form1").attr("action","upload.do?method=loadExitsFile&path="+path);
				$("#form1").submit();
			}
			if(event.target.title=='loadAcademic'){
				//var href = event.target.href;
				var path = document.getElementById("pact.academicCertificateCopy").value;//$("#pact.academicCertificateCopy").attr("value");
				path = encodeURI(encodeURI(path));
				$("#form1").attr("action","upload.do?method=loadExitsFile&path="+path);
				$("#form1").submit();
			}
			if(event.target.title=='loadOnduty'){
				//var href = event.target.href;
				var path = document.getElementById("pact.qualificationCertificateCopy").value;//$("#pact.qualificationCertificateCopy").attr("value");
				path = encodeURI(encodeURI(path));
				$("#form1").attr("action","upload.do?method=loadExitsFile&path="+path);
				$("#form1").submit();
			}
			if(event.target.title=='loadReport'){
				//var href = event.target.href;
				var path = document.getElementById("pact.physicalExaminationReport").value;//$("#pact.physicalExaminationReport").attr("value");
				path = encodeURI(encodeURI(path));
				$("#form1").attr("action","upload.do?method=loadExitsFile&path="+path);
				$("#form1").submit();
			}
			if(event.target.title=='bankcard'){
				//var href = event.target.href;
				var path = document.getElementById("pact.bankCardCopy").value;//$("#pact.bankCardCopy").attr("value");
				path = encodeURI(encodeURI(path));
				$("#form1").attr("action","upload.do?method=loadExitsFile&path="+path);
				$("#form1").submit();
			}
			return false;
		});
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
			}
			if(window.navigator.userAgent.indexOf("MSIE 8.0")>=1){
				
				 var index = s.lastIndexOf("\\");
				 var picName = s.substring(index+1);
				 var posiation = document.getElementById("pic");
				 var img = document.createElement("img");
				 posiation.appendChild(img);
				 img.src = "<%=path%>/uploadPerFile/"+picName;
			}
		 }
		}
		
		function cancel(){
			document.form1.action = "personnel.do?method=showPersonnel";
			document.form1.submit();
		}
	</script>
 </head>
  
  <body onload="loadPic()">
    	<form id="form1" action="personnel.do?method=upLoad" method="post" name="form1">
    		<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
    		<!-- 保存列表页面的查询条件start -->
    		<input type="hidden" name="cx_personnelName" value="${personnelForm.cx_personnelName }" />
    		<input type="hidden" name="cx_starttime" value="${personnelForm.cx_starttime }" />
    		<input type="hidden" name="cx_endtime" value="${personnelForm.cx_endtime }" />
    		<input type="hidden" name="cx_departmentcode" value="${personnelForm.cx_departmentcode }" />
    		<input type="hidden" name="cxStatus" value="${personnelForm.cxStatus }" />
    		<!-- 保存列表页面的查询条件end -->
    	
    		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
    			<tr>
    				<td height="5"></td>
  				</tr>
  				<tr>
    			<td>
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
      					<tr>
        					<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
							<td width="165" nowrap="nowrap" class="form_line">员工 ${personnelForm.personnel.personnelName }的信息</td>
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
    						<td align="right" class="head_form1">用工状态:
    						</td>
    						<td class="head_form1" colspan="4">&nbsp;
    							<select id="personnel.status" name="personnel.status" style="width:130px;" disabled="disabled">
    								<option <c:if test="${personnelForm.personnel.status == '离职'}">selected</c:if> value="离职"
    								>
    								离职
    								</option>
    								<option <c:if test="${personnelForm.personnel.status == '在职'}">selected</c:if> value="在职"
    								>
    								在职
    								</option>
    							    <option <c:if test="${personnelForm.personnel.status == '试用期'}">selected</c:if> value="试用期"
    								>
    								试用期
    								</option>
    							</select>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">
    							姓名：
    						</td>
    						<td class="head_form1">&nbsp;											 
    							<input id="personnel.personnelName" type="text" name="personnel.personnelName" disabled="disabled" value="${personnelForm.personnel.personnelName }"/>
    						</td>
    						<td align="right" class="head_form1">
    							性别：
    						</td>
    						<td class="head_form1">&nbsp;
    							<select id="personnel.sex" name="personnel.sex" style="width:130px;"  disabled="disabled">
    								<option <c:if test="${personnelForm.personnel.sex == '男'}">selected</c:if> value="男"
    								>
    								男
    								</option>
    								<option <c:if test="${personnelForm.personnel.sex == '女'}">selected</c:if> value="女"
    								>
    								女
    								</option>
    							</select>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">
    							民族：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.nation" value="${personnelForm.personnel.nation }" disabled="disabled"/>
    						</td>
    						<td align="right" class="head_form1">
    							出生日期：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.birthday" readonly onclick="WdatePicker()" disabled="disabled" id="personnel.birthday" value="${personnelForm.personnel.birthday }" class="Wdate"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">年龄：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.age" disabled="disabled" <c:if test="${personnelForm.personnel.age ne '0'}">  value="${personnelForm.personnel.age }" </c:if>/>
    						</td>
    						<td align="right" class="head_form1">员工编号：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.personnelCode" disabled="disabled" value="${personnelForm.personnel.personnelCode }" id="personnel.personnelCode"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">员工工号：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.personnelNum" disabled="disabled" value="${personnelForm.personnel.personnelNum }"/>
    						</td>
    						<td align="right" class="head_form1">是否为派工工人：</td>
    						<td class="head_form1">&nbsp;<c:if test=""></c:if>
    							<select name="personnel.isDispatch" disabled="disabled">
    								<option value="0" <c:if test="${personnelForm.personnel.isDispatch eq '0' }">selected</c:if>>否</option>
    								<option value="1" <c:if test="${personnelForm.personnel.isDispatch eq '1' }">selected</c:if>>是</option>
    							</select>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">
    							身份证号：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.identityCard" disabled="disabled" value="${personnelForm.personnel.identityCard }"/>
    						</td>
    						<td align="right" class="head_form1">
    							联系电话：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.phone" disabled="disabled" value="${personnelForm.personnel.phone }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">政治面貌：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.groups" disabled="disabled" value="${personnelForm.personnel.groups }"/>
    						</td>
    						<td align="right" class="head_form1">健康状况：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.health" disabled="disabled" value="${personnelForm.personnel.health}"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">婚姻状况：</td>
    						<td class="head_form1">&nbsp;
    							<c:choose>
    							<c:when test="${personnelForm.personnel.marriage == '已婚'}">
    								<input type="radio" name="personnel.marriage" checked="checked" disabled="disabled" value="已婚"/>已婚
    								<input type="radio" name="personnel.marriage" disabled="disabled" value="未婚"/>未婚
    							</c:when>
    							<c:otherwise>
    								<input type="radio" name="personnel.marriage" disabled="disabled" value="已婚"/>已婚
    								<input type="radio" name="personnel.marriage" disabled="disabled" checked="checked" value="未婚"/>未婚
    							</c:otherwise> 
    							</c:choose>
    						</td>
    						<td align="right" class="head_form1">
    							血型：
    						</td>
    						<td class="head_form1">&nbsp;
    						   <input name="personnel.bloodType" type="radio" disabled="disabled" <c:if test="${personnelForm.personnel.bloodType == 'O'}">checked</c:if> value="O"/>O
    						   <input name="personnel.bloodType" type="radio" disabled="disabled" <c:if test="${personnelForm.personnel.bloodType == 'A'}">checked</c:if> value="A"/>A
    						   <input name="personnel.bloodType" type="radio" disabled="disabled" <c:if test="${personnelForm.personnel.bloodType == 'B'}">checked</c:if> value="B"/>B
    						   <input name="personnel.bloodType" type="radio" disabled="disabled" <c:if test="${personnelForm.personnel.bloodType == 'AB'}">checked</c:if> value="AB"/>AB
    						   <input name="personnel.bloodType" type="radio" disabled="disabled" <c:if test="${personnelForm.personnel.bloodType == '其它'}">checked</c:if> value="其它">其它
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">兴趣爱好：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<input type="text" name="personnel.interest" disabled="disabled" value="${personnelForm.personnel.interest}"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">一寸照片：</td>
    						<td id="pic" colspan="3" class="head_form1">&nbsp;
    						  <div class="initiated_event_photo" id="event_photo_div">
    							<img src="${personnelForm.personnel.imageUrl}" id="imgUrl" name="imgUrl"/>
    						   </div> 
    						   <div id="newPreview"></div> 
    							<input type="hidden" id="exitsUrl" name="exitsUrl" value="${personnelForm.personnel.imageUrl }"/>
    							<input id="personnel.imageUrl" type="hidden" name="personnel.imageUrl" value="${personnelForm.personnel.imageUrl }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">学历/学位：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.academicCertificate" disabled="disabled" value="${personnelForm.personnel.academicCertificate }"/>
    						</td>
    						<td align="right" class="head_form1">专业：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.speciality" disabled="disabled" value="${personnelForm.personnel.speciality }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">职称/资格：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="pact.duty" disabled="disabled" value="${personnelForm.pact.duty }"/>
    						</td>
    						<td align="right" class="head_form1">外语语种：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.foreignLanguage" disabled="disabled" value="${personnelForm.personnel.foreignLanguage }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">邮编：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<input type="text" name="personnel.post" disabled="disabled" value="${personnelForm.personnel.post }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">户口所在地：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<input type="text" name="personnel.residence" disabled="disabled" value="${personnelForm.personnel.residence }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">现居住地址：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<input type="text" name="personnel.address" disabled="disabled" value="${personnelForm.personnel.address}"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">档案/劳动保险情况：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<textarea rows="3" cols="20" disabled="disabled" name="personnel.labourInsurance">${personnelForm.personnel.labourInsurance}</textarea>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">备注：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<textarea rows="3" cols="20" name="personnel.bz" disabled="disabled">${personnelForm.personnel.bz }</textarea>
    						</td>
    					</tr>
    				</table>
    			   </td>
    			</tr>
    			<tr>
    			  <td class="menu_tab2" align="center">
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="4" align="left" class="head_one"><font size="+1">合同信息</font></td>
						</tr>
						<tr>
							<td align="right" class="head_form1">应聘渠道：</td>
							<td class="head_form1">&nbsp;
								<input name="pact.engageChannel" type="radio" disabled="disabled" <c:if test="${personnelForm.pact.engageChannel == '外聘'}">checked</c:if> value="外聘">外聘
								
								<input name="pact.engageChannel" type="radio" disabled="disabled" <c:if test="${personnelForm.pact.engageChannel == '内部推荐'}">checked</c:if> value="内部推荐"/>内部推荐
								
								<input name="pact.engageChannel" type="radio" disabled="disabled" <c:if test="${personnelForm.pact.engageChannel == '门户网站'}">checked</c:if> value="门户网站"/>门户网站
							</td>
							<td align="right" class="head_form1">
								推荐人：
							</td>
							<td class="head_form1">&nbsp;
								<input type="text" name="pact.recommendPerson" disabled="disabled" value="${personnelForm.pact.recommendPerson }"/>
							</td>
						</tr>
						<tr>
							<td align="right" class="head_form1">所在部门：</td>
							<td class="head_form1" colspan="3">&nbsp;
								<select id="pact.departmentCode" name="pact.departmentCode" disabled="disabled" style="width:130px;">
									<option value="">
										--请选择--
									</option>
									<c:forEach items="${personnelForm.department}" var="depart">
									<option value="${depart.codeName}" 
										<c:if test="${personnelForm.pact.departmentCode == depart.codeName}">
										selected
										</c:if>
										>
										${depart.codeName }
										</option>
									</c:forEach>
								</select>
							</td>
						</tr>   
						<tr>
							<td align="right" class="head_form1">合同编号：</td>
							<td class="head_form1">&nbsp;
								<input id="pact.pactCode" type="text" disabled="disabled" name="pact.pactCode" value="${personnelForm.pact.pactCode }"/>
							</td>
							<td align="right" class="head_form1">合同签订日期：</td>
							<td class="head_form1">&nbsp;
								<input type="text" name="pact.pactMakeDate" readonly disabled="disabled" onclick="WdatePicker()" id="pact.pactMakeDate" value="${personnelForm.pact.pactMakeDate }" class="Wdate"/>
							</td>
						</tr> 
						<tr>
							<td align="right" class="head_form1">合同生效日期：</td>
							<td class="head_form1">&nbsp;
								<input type="text" name="pact.pactBeginDate" disabled="disabled" readonly onclick="WdatePicker()" id="pact.pactBeginDate" value="${personnelForm.pact.pactBeginDate }" class="Wdate"/>
							</td>
							<td align="right" class="head_form1">合同终止日期：</td>
							<td class="head_form1">&nbsp;
								<input type="text" name="pact.pactEndDate" readonly disabled="disabled" onclick="WdatePicker()" id="pact.pactEndDate" value="${personnelForm.pact.pactEndDate }" class="Wdate"/>
							</td>
						</tr>	
						<tr>
							<td align="right" class="head_form1">到职日期：</td>
							<td class="head_form1">&nbsp;
								<input type="text" name="pact.startDate" readonly disabled="disabled" onclick="WdatePicker()" id="pact.startDate" value="${personnelForm.pact.startDate }" class="Wdate"/>
							</td>
							<td align="right" class="head_form1">试用期截止日期：</td>
							<td class="head_form1">&nbsp;
								<input type="text" name="pact.tryoutEndDate" readonly disabled="disabled" onclick="WdatePicker()" id="pact.tryoutEndDate" value="${personnelForm.pact.tryoutEndDate }" class="Wdate"/>
							</td>
						</tr>			
    				</table>
    			  </td>
    			</tr>
    			<tr>
    			  <td class="menu_tab2" align="center">
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
    					<tr>
    						<td colspan="4" align="left" class="head_one"><font size="+1">相关附件</font></td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">
    							人事合同附件：
    						</td>
    						<td class="head_form1">&nbsp;
    							<span id="pact">
    								<a id="loadfile" title="loadPact" class="${personnelForm.pact.pactFile }" href="upload.do?method=loadExitsFile&path=${personnelForm.pact.pactFile }&name=${personnelForm.pact.pactFileName }">${personnelForm.pact.pactFileName }</a>
    							</span>
    							<input type="hidden" id="pact.pactFile" name="pact.pactFile" value="${personnelForm.pact.pactFile }"/>
    							<input type="hidden" id="pact.pactFileName" name="pact.pactFileName" value="${personnelForm.pact.pactFileName }"/>
    						</td>
    						<td align="right" class="head_form1">身份证复印件：</td>
    						<td class="head_form1">&nbsp;
    							<span id="idFace">
    								<a id="loadfile" title="loadidFace" href="upload.do?method=loadExitsFile&path=${personnelForm.pact.identityCopy }&name=${personnelForm.pact.identityCopyName }">${personnelForm.pact.identityCopyName }</a>
    							</span>
    							<input type="hidden" id="pact.identityCopy" name="pact.identityCopy" value="${personnelForm.pact.identityCopy }"/>
    							<input type="hidden" id="pact.identityCopyName" name="pact.identityCopyName" value="${personnelForm.pact.identityCopyName }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">学历证书复印件：</td>
    						<td class="head_form1">&nbsp;
    							<span id="Academic">
    								<a id="loadfile" title="loadAcademic" href="upload.do?method=loadExitsFile&path=${personnelForm.pact.academicCertificateCopy }&name=${personnelForm.pact.academicCertificateCopyName }">${personnelForm.pact.academicCertificateCopyName }</a>
    							</span>
    							<input type="hidden" id="pact.academicCertificateCopy" name="pact.academicCertificateCopy" value="${personnelForm.pact.academicCertificateCopy }"/>
    							<input type="hidden" id="pact.academicCertificateCopyName" name="pact.academicCertificateCopyName" value="${personnelForm.pact.academicCertificateCopyName }"/>
    						</td>
    						<td align="right" class="head_form1">职称、职业资格证书复印件：</td>
    						<td class="head_form1">&nbsp;
    							<span id="onduty">
    								<a id="loadfile" title="loadOnduty" href="upload.do?method=loadExitsFile&path=${personnelForm.pact.qualificationCertificateCopy }&name=${personnelForm.pact.qualificationCertificateCopyName }">${personnelForm.pact.qualificationCertificateCopyName }</a>
    							</span>
    							<input type="hidden" id="pact.qualificationCertificateCopy" name="pact.qualificationCertificateCopy" value="${personnelForm.pact.qualificationCertificateCopy }"/>
    							<input type="hidden" id="pact.qualificationCertificateCopyName" name="pact.qualificationCertificateCopyName" value="${personnelForm.pact.qualificationCertificateCopyName }"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">入职体检报告：</td>
    						<td class="head_form1">&nbsp;
    							<span id="report">
    								<a id="loadfile" title="loadReport" href="upload.do?method=loadExitsFile&path=${personnelForm.pact.physicalExaminationReport }&name=${personnelForm.pact.physicalExaminationReportName }">${personnelForm.pact.physicalExaminationReportName }</a>
    							</span>
    							<input type="hidden" id="pact.physicalExaminationReport" name="pact.physicalExaminationReport" value="${personnelForm.pact.physicalExaminationReport }"/>
    							<input type="hidden" id="pact.physicalExaminationReportName" name="pact.physicalExaminationReportName" value="${personnelForm.pact.physicalExaminationReportName }"/>
    						</td>
    						<td align="right" class="head_form1">银行卡附件：</td>
    						<td class="head_form1">&nbsp;
    							<span id="card">
    								<a id="loadfile" title="bankcard" href="upload.do?method=loadExitsFile&path=${personnelForm.pact.bankCardCopy }&name=${personnelForm.pact.bankCardCopyName }">${personnelForm.pact.bankCardCopyName }</a>
    							</span>
    							<input type="hidden" id="pact.bankCardCopy" name="pact.bankCardCopy" value="${personnelForm.pact.bankCardCopy }"/>
    							<input type="hidden" id="pact.bankCardCopyName" name="pact.bankCardCopyName" value="${personnelForm.pact.bankCardCopyName }"/>
    						</td>
    					</tr>
    				</table>
    			  </td>
    			</tr>
    			<tr>
    				<td colspan="4" align="center">
    					<input type="button" name="retur" value="返回" onclick="javascript:cancel();" class="button"/>
    				</td>
    			</tr>
    		</table>
    	</form>
  </body>
</html>
