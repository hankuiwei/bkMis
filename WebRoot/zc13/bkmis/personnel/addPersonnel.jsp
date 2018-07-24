<%@ page language="java" import="java.util.*,com.zc13.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.apache.struts.taglib.html.Constants" %> 
<%@ page import="org.apache.struts.Globals" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加员工信息</title>
    
    <link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <!-- 使用日期控件时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<!-- 使用单元格在线编辑功能时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/jquery.form.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/validate.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
    <style type="text/css">
	.initiated_event_photo img{width:400px; height:320px; margin-left:78px; display:none;}
	#newPreview{float:left; display:none;width:90%; height:auto; text-align:left; margin:9px 0 0 110px; font-size:14px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);}
	</style>
	<!--  -->
	<script type="text/javascript">
		//跳转到上传文件页面
		function upFile(name,file,filename){
		     var s = window.showModalDialog("zc13/bkmis/personnel/upload.jsp",window,"dialogWidth=300px;dialogHeight=260px;resizable=yes;center=1");
		     if(s !="" && s!= null){
		     var data = s;
		     var arr = data.split("\\");
		     var subfilename = arr[arr.length-1];
		     var span = document.getElementById(name);
		     var ss = document.getElementById(file).value = data;
		     var sfilename = document.getElementById(filename).value = subfilename;
		      //alert(ss);
		     var newSpan = $("#" + name);
		     //下面是对文件打开保存的操作
		     var a1 = document.createElement("a");
		     a1.href = "upload.do?method=upLoad&path="+encodeURI(encodeURI(data));
		     //a1.href = "upload.do?method=upLoad&path="+arr[0]+"&filename="+arr[1];
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
		     }
		    // window.close();
		}
		function fn(){
			var x = Validator.Validate(document.getElementById('upLoadform'),2);
			if(!x){
				return;
			}
			document.upLoadform.action="personnel.do?method=addPersonnel";
			document.upLoadform.submit();
		}
		//上传图片
		function upPicture(){
			 var s = window.showModalDialog("zc13/bkmis/personnel/uploadPic.jsp",window,"dialogWidth=300px;dialogHeight=260px;resizable=yes;center=1");
		     var data = s;
		     //alert(s);
		     //document.getElementById("imageUrl").value = s;
		     //alert("======"+imageurl);
		     if(navigator.userAgent.indexOf("MSIE 6.0")>=1){ 
			     if(s != "undefined" && s != "" && s != null){
				     //var photo = $("#event_photo_div");
				     //var img = $("imageurl");
			     	 document.getElementById("personnel.imageUrl").value = s;
				     //img.style.display="block";
	  				 //photo.style.display="block";
				     $("#imageurl").attr("src",s);
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
		//删除图片
		$("#pic").ready(function(){
				$("#retur").live("click",function(){
					var picUri = document.getElementById("personnel.imageUrl").value;
					if(picUri == "" || picUri == null || picUri == "undefined"){
						alert("没有图片,不能执行重设功能!");
					}else{
					if(!window.confirm("确定要删除吗？"))  {return false;}
					var newPreview = document.getElementById("newPreview");  
					newPreview.style.display="none";
					var url = "<%=path%>/personnel.do";
					//alert("url : " + url);
					var imgurl = document.getElementById("personnel.imageUrl").value;
					//alert(imgurl);
					$.post(url,{method:"delPic",path:imgurl},function(data){
						if(data == 0){
							$("#imageurl").attr("src","");
							document.getElementById("personnel.imageUrl").value = "";
							alert("删除成功!");
						}else{
							alert("删除失败!");
						}
					})
				  }
				})
		});
					
		function cancel(){
			document.upLoadform.action = "personnel.do?method=showPersonnel";
			document.upLoadform.submit();
		}
	</script>
 </head>
  
  <body>
    	<form id="upLoadform" action="" method="post" name="upLoadform">
    		<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
    		<!-- 保存列表页面的查询条件start -->
    		<input type="hidden" name="cx_personnelName" value="${personnelForm.cx_personnelName }" />
    		<input type="hidden" name="cx_starttime" value="${personnelForm.cx_starttime }" />
    		<input type="hidden" name="cx_endtime" value="${personnelForm.cx_endtime }" />
    		<input type="hidden" name="cx_departmentcode" value="${personnelForm.cx_departmentcode }" />
    		<input type="hidden" name="cxStatus" value="${personnelForm.cxStatus }" />
    		<!-- 保存列表页面的查询条件end -->
    		<!-- 一些影藏的默认字段的值start -->
    		<input type="hidden" name="personnel.isInPost" value="<%=Contants.ISNOTINPOST %>" />
    		<input type="hidden" name="personnel.isOut" value="<%=Contants.ISNOTOUT %>" />
    		<!-- 一些影藏的默认字段的值end -->
    		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
    			<tr>
    				<td height="5"></td>
  				</tr>
  				<tr>
    			<td>
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
      					<tr>
        					<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
							<td width="165" nowrap="nowrap" class="form_line">员工登记</td>
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
    							用工状态：
    						</td>
    						<td class="head_form1" colspan="4">&nbsp;
    							<select id="personnel.status" name="personnel.status" style="width:130px;">
    								<option value="在职">
    									在职
    								</option>
    								<option value="离职">
    									离职
    								</option>
    								<option value="试用期">
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
    							<input id="personnel.personnelName" dataType="Require" msg="姓名不得为空！" type="text" name="personnel.personnelName"/>
    							<font color="red">*</font>
    						</td>
    						<td align="right" class="head_form1">性别：</td>
    						<td class="head_form1">&nbsp;
    							<select id="personnel.sex" name="personnel.sex" style="width:130px;">
    								<option value="男">男</option>
    								<option value="女">女</option>
    							</select>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">民族：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.nation"/>
    						</td>
    						<td align="right" class="head_form1">出生日期：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.birthday" readonly onclick="WdatePicker()" id="personnel.birthday" class="Wdate"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">年龄：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.age"/>
    						</td>
    						<td align="right" class="head_form1">员工编号：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.personnelCode"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">员工工号：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.personnelNum" onkeyup="value=value.replace(/[^\d]/g,'')" />
    						</td>
    						<td align="right" class="head_form1">是否为派工工人：</td>
    						<td class="head_form1">&nbsp;
    							<select name="personnel.isDispatch">
    								<option value="0">否</option>
    								<option value="1">是</option>
    							</select>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">
    							身份证号：
    						</td>
    						<td  class="head_form1">&nbsp;
    							<input type="text" name="personnel.identityCard"/>
    						</td>
    						<td align="right" class="head_form1">
    							联系电话：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.phone" id="personnel.phone"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">政治面貌：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.groups"/>
    						</td>
    						<td align="right" class="head_form1">健康状况：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.health"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">婚姻状况：</td>
    						<td class="head_form1">&nbsp;
    							<input type="radio" name="personnel.marriage" value="已婚"/>已婚
    							<input type="radio" name="personnel.marriage" value="未婚" checked="checked"/>未婚
    						</td>
    						<td align="right" class="head_form1">
    							血型：
    						</td>
    						<td class="head_form1">&nbsp;
    							<input type="radio" name="personnel.bloodType" value="A"/>A
    							<input type="radio" name="personnel.bloodType" value="B"/>B
    							<input type="radio" name="personnel.bloodType" value="AB"/>AB
    							<input type="radio" name="personnel.bloodType" value="0"/>O
    							<input type="radio" name="personnel.bloodType" value="其它">其它
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">兴趣爱好：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<input type="text" name="personnel.interest"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">一寸照片：</td>
    						<td id="pic" colspan ="3" class="head_form1">&nbsp;
    						  <div class="initiated_event_photo" id="event_photo_div">
    							<img id="imageurl"/>
    						  </div>
    						  <div id="newPreview"></div> 
    							<input id="personnel.imageUrl" type="hidden" name="personnel.imageUrl"/>
    							<input id="upPic" type="button" value="粘贴" onclick="upPicture();"/>
    						    <input id="retur" type="button" value="重设"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">学历/学位：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.academicCertificate"/>
    						</td>
    						<td align="right" class="head_form1">专业：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.speciality"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">职称/资格：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="pact.duty"/>
    						</td>
    						<td align="right" class="head_form1">外语语种：</td>
    						<td class="head_form1">&nbsp;
    							<input type="text" name="personnel.foreignLanguage"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">邮编：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<input type="text" name="personnel.post"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">户口所在地：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<input type="text" name="personnel.residence"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">现居住地址：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<input type="text" name="personnel.address"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">档案/劳动保险情况：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<textarea rows="3" cols="20" name="personnel.labourInsurance"></textarea>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">备注：</td>
    						<td class="head_form1" colspan="3">&nbsp;
    							<textarea rows="3" cols="20" name="personnel.bz"></textarea>
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
								<input type="radio" name="pact.engageChannel" value="外聘"/>外聘
								<input type="radio" name="pact.engageChannel" value="内部推荐"/>内部推荐
								<input type="radio" name="pact.engageChannel" value="门户网站"/>门户网站
							</td>
							<td align="right" class="head_form1">
								推荐人：
							</td>
							<td class="head_form1">&nbsp;
								<input type="text" name="pact.recommendPerson"/>
							</td>
						</tr>
						<tr>
							<td align="right" class="head_form1">所在部门：</td>
							<td class="head_form1" colspan="3">&nbsp;
								<select id="pact.departmentCode" name="pact.departmentCode" style="width:130px;">
									<option value="">
										--请选择--
									</option>
									<c:forEach items="${personnelForm.department}" var="depart">
									<option value="${depart.codeName}">
										${depart.codeName }
										</option>
									</c:forEach>
								</select>
							</td>
						</tr>   
						<tr>
							<td align="right" class="head_form1">合同编号：</td>
							<td class="head_form1">&nbsp;
								<input id="pact.pactCode" type="text" name="pact.pactCode"/>
							</td>
							<td align="right" class="head_form1">合同签订日期：</td>
							<td class="head_form1">&nbsp;
								<input type="text" name="pact.pactMakeDate" readonly onclick="WdatePicker()" id="pact.pactMakeDate" class="Wdate"/>
							</td>
						</tr> 
						<tr>
							<td align="right" class="head_form1">合同生效日期：</td>
							<td class="head_form1">&nbsp;
								<input type="text" name="pact.pactBeginDate" readonly onclick="WdatePicker()" id="pact.pactBeginDate" class="Wdate"/>
							</td>
							<td align="right" class="head_form1">合同终止日期：</td>
							<td class="head_form1">&nbsp;
								<input type="text" name="pact.pactEndDate" readonly onclick="WdatePicker()" id="pact.pactEndDate" class="Wdate"/>
							</td>
						</tr>	
						<tr>
							<td align="right" class="head_form1">到职日期：</td>
							<td class="head_form1">&nbsp;
								<input type="text" name="pact.startDate" readonly onclick="WdatePicker()" id="pact.startDate" class="Wdate"/>
							</td>
							<td align="right" class="head_form1">试用期截止日期：</td>
							<td class="head_form1">&nbsp;
								<input type="text" name="pact.tryoutEndDate" readonly onclick="WdatePicker()" id="pact.tryoutEndDate" class="Wdate"/>
							</td>
						</tr>			
    				</table>
    			  </td>
    			</tr>
    			<tr>
    			  <td>
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
    					<tr>
    						<td colspan="4" align="left" class="head_one"><font size="+1">相关附件</font></td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">
    							人事合同附件：
    						</td>
    						<td class="head_form1">&nbsp;
    							<span id="pact"></span>
    							<input type="hidden" id="pact.pactFile" name="pact.pactFile"/>
    							<input type="hidden" id="pact.pactFileName" name="pact.pactFileName"/>
    							<input id="btnShow" type="button" value="粘贴附件" onclick="upFile('pact','pact.pactFile','pact.pactFileName');"/>
    						</td>
    						<td align="right" class="head_form1">身份证复印件:</td>
    						<td class="head_form1">&nbsp;
    							<span id="idFace"></span>
    							<input type="hidden" id="pact.identityCopy" name="pact.identityCopy"/>
    							<input type="hidden" id="pact.identityCopyName" name="pact.identityCopyName"/>
    							<input id="btnShow" type="button" value="粘贴附件" onclick="upFile('idFace','pact.identityCopy','pact.identityCopyName');"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">学历证书复印件:</td>
    						<td class="head_form1">&nbsp;
    							<span id="Academic"></span>
    							<input type="hidden" id="pact.academicCertificateCopy" name="pact.academicCertificateCopy"/>
    							<input type="hidden" id="pact.academicCertificateCopyName" name="pact.academicCertificateCopyName"/>
    							<input id="btnShow" type="button" value="粘贴附件" onclick="upFile('Academic','pact.academicCertificateCopy','pact.academicCertificateCopyName');"/>
    						</td>
    						<td align="right" class="head_form1">职称、职业资格证书复印件:</td>
    						<td class="head_form1">&nbsp;
    							<span id="onduty"></span>
    							<input type="hidden" id="pact.qualificationCertificateCopy" name="pact.qualificationCertificateCopy"/>
    							<input type="hidden" id="pact.qualificationCertificateCopyName" name="pact.qualificationCertificateCopyName"/>
    							<input id="btnShow" type="button" value="粘贴附件" onclick="upFile('onduty','pact.qualificationCertificateCopy','pact.qualificationCertificateCopyName');"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="right" class="head_form1">入职体检报告:</td>
    						<td class="head_form1">&nbsp;
    							<span id="report"></span>
    							<input type="hidden" id="pact.physicalExaminationReport" name="pact.physicalExaminationReport"/>
    							<input type="hidden" id="pact.physicalExaminationReportName" name="pact.physicalExaminationReportName"/>
    							<input id="btnShow" type="button" value="粘贴附件" onclick="upFile('report','pact.physicalExaminationReport','pact.physicalExaminationReportName');"/>
    						</td>
    						<td align="right" class="head_form1">银行卡附件:</td>
    						<td class="head_form1">&nbsp;
    							<span id="card"></span>
    							<input type="hidden" id="pact.bankCardCopy" name="pact.bankCardCopy"/>
    							<input type="hidden" id="pact.bankCardCopyName" name="pact.bankCardCopyName"/>
    							<input id="btnShow" type="button" value="粘贴附件" onclick="upFile('card','pact.bankCardCopy','pact.bankCardCopyName');"/>
    						</td>
    					</tr>
    				</table>
    			  </td>
    			</tr>
    			<tr>
    				<td colspan="4" align="center">
    					<input id="button" type="button" name="button" value="提交" class="button" onclick="javascript:fn();"/>
    					<input type="button" name="retur" value="返回" onclick="javascript:cancel();" class="button"/>
    				</td>
    			</tr>
    		</table>
    	</form>
  </body>
</html>
