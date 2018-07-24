<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>编辑楼盘信息</title>
		<base target="_self" />
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
		<script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
		<style type="text/css">
		.initiated_event_photo img{width:400px; height:320px; margin-left:78px; display:none;}
		#newPreview{float:left; display:none;width:90%; height:auto; text-align:left; margin:9px 0 0 110px; font-size:14px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);}
		</style>
		<script type="text/javascript">
		  
		   //验证表单楼盘名称和编号是否为空
		   function checkForm(){
		       var lpCode = document.getElementById("lpCode").value;
		       var lpName = document.getElementById("lpName").value;
		       var url = "<%=path%>/lp.do";
		       if(trim(lpCode) == "" ){
		          alert("请输入楼盘编号！");
		          return false;
		       }else if(trim(lpName) == ""){
		         alert("请输入楼盘名称！");
		         return false;
		       }else{
		          if(confirm("确定要修改吗？")){
		          //确定修改时删除之前上传的但不用于保存的垃圾图片
		          
		            document.modifyLpForm.action = "<%=path%>/lp.do?method=modifyLp2";
		            document.modifyLpForm.submit();
                    
		          }
		       }
		   }
		   //添加表具信息
		   function addMeter(){
		      var temp = window.showModalDialog("<%=path%>/lp.do?method=goaddMeter",this.window,"dialogWidth=550px;dialogHeight=400px;center=1");
			  //判断回传的值是否为空
			  if(temp!=null){
			     if(temp != ""){
			         var arr = temp.split(";");
			     }
				 var oTable=document.getElementById("metertb");
				 var td;
			     var oTr1 = oTable.insertRow(oTable.rows.length); 
			     //用来动态插入行
			     for(var i=0;i<4;i++){
			    	 if(i==0){
			    		td = oTr1.insertCell(i);
			    		td.align = "center";
			    	    td.className = "RptTableBodyCellLock";
			    		td.innerHTML = "<input type=\"checkbox\" name=\"meterId\">";
			    	}else if(i==1){
			    	    td = oTr1.insertCell(i);
			    		td.nowrap = "nowrap"; 
			    		td.align = "center";
			    		td.className = "RptTableBodyCell";
			    		td.innerText = oTable.rows.length-3;
			    	}else if(i==2){
				    	td = oTr1.insertCell(i);
			    		td.nowrap = "nowrap"; 
			    		td.align = "center";
			    		td.className = "RptTableBodyCell";
			    		td.title = arr[1];
				    	td.innerText = arr[1];
				    	var code = document.getElementById("code");
				    	//把回传回来的表具拼接成字符串，传给form
				    	code.value += arr[1] + ";";
			    	}else{
			    	     td = oTr1.insertCell(i);
			    	     td.nowrap = "nowrap"; 
			    		 td.align = "center";
			    		 td.className = "RptTableBodyCell";
			    		 td.title = arr[0];
			    		 td.innerText = arr[0];
			    		 var type = document.getElementById("type");
			    		 //把回传回来的表具名称拼接成字符串，传给form
			    		 type.value += arr[2] + ";"
			    	}
			  }
			  //对于显示“没有表具信息”的处理
			     var bjInfotr = oTable.getElementsByTagName("tr");
			     if(bjInfotr[3].id=="bjInfo"){
			          oTable.deleteRow(3);
			          bjInfotr[3].getElementsByTagName("td")[1].innerText = "1";
			     }
			} 
		}
		var flag = false;
		 //删除表具信息
		 function deleteMeter(str,str1){
			 var tb = document.getElementById(str);
			 var checkboxname = document.getElementsByName(str1);
			 //防止在没有任何表具信息的时候点击删除报js错误！
			 if(!(checkboxname.length >= 1)){
			    alert("暂时无表具信息，无法执行删除操作！");
			    return false;
			 }else{
			     for(var j=checkboxname.length-1;j>=0;j--){
				     if(checkboxname[j].checked){
				        //得到选中的checkbox的父对象的父对象，也就是一个tr
				        var tr = checkboxname[j].parentElement.parentElement;
				        //然后得到表具编号对应的td
				        var td1 = tr.getElementsByTagName("td")[2];
				        var td2 = tr.getElementsByTagName("td")[3];
				        //然后将得到的td里面除html标签之外的文本给一个表示表具编号的变量
				        var meterCode = td1.innerText;
				        var meterName = td2.innerText;
				       //删除一行
				       tb.deleteRow(checkboxname[j].parentElement.parentElement.rowIndex);
				       //使用jquery调用后台的方法删除数据库对应保存的表具信息
				       var url = "<%=path%>/lp.do";
				       $.post(url,{method:"getMeterTypeByCodeName",meterName:meterName},function(data){
				           var meterType = data;
				           $.post(url,{method:"deleteMeter",meterCode:meterCode,meterType:meterType},function(data){});
				           flag = false;
				       });
				     } 
			      }
		       //用来在删除一行之后重新设置序号的
			    var tr1 = tb.getElementsByTagName("tr");
				    for(var i=3;i<tr1.length;i++){
				        var td1 = tr1[i].getElementsByTagName("td");
				        td1[1].innerText = tr1[i].rowIndex-2;
				    }
		       //用来把标题行的chenkbox设置为未选中状态
			   document.getElementsByName("meter")[0].checked = "";
			
			  //删除掉所有的数据后再添加一行，用于显示没有表具信息的提示
			  var bjInfotr = tb.getElementsByTagName("tr");
			  if(bjInfotr.length<=3){
			        var temptr =  tb.insertRow(3);
			        var temptd = temptr.insertCell();
			        temptr.id = "bjInfo";
			        temptd.align = "center";
			        temptd.className = "head_form1";
			        temptd.colSpan = "5";
			        temptd.innerHTML = "<font color=\"red\">暂时没有表具信息!</font>";
			   }
			}
		}
		//选中所有的checkbox
         function selectedAll(){
            var list = document.getElementsByName("meterId");
            if(flag == false){
               for(var i=0;i<list.length;i++){
                   list[i].checked = true;
               }
            flag = true;
            }else{
               for(var i=0;i<list.length;i++){
                  list[i].checked = false;
               }
             flag = false;
           }
         }
         /* 点击取消关闭窗口 */
		  function closeModify(){
		    history.go(-1);
		  }
		  //加载已有的图片
		
        </script>
	</head>
	<body >
		<form method="post" name="modifyLpForm">
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
			
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">修改楼盘信息</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="menu_tab2" align="center">
						<table width="60%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="center">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="10" colspan="9"><input type="hidden" name="code" id="code"><input type="hidden" name="type" id="type"></td>
										</tr>
										<tr>
											<td height="10" colspan="9"><input type="hidden" id="lpId" name="lpId" value="${elp.lpId}"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left">
										<tr>
											<td align="right" class="head_form1" width="12%">楼盘编号：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;<input type="text" id="lpCode" name="lpCode" value="${elp.lpCode}"><font color="red">*</font></td>
											<td align="right" class="head_form1" width="23%">楼盘名称：</td>
											<td align="left" class="head_form1">&nbsp;<input type="text" id="lpName" name="lpName" value="${elp.lpName}"></td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">所属区域：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;<input type="text" id="regional" name="regional" value="${elp.regional}"></td>
											<td align="right" class="head_form1" width="23%">物业类型：</td>
											<td align="left" class="head_form1">&nbsp;<input type="text" id="ppType" name="ppType" value="${elp.ppType}"></td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">开发单位：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;<input type="text" id="developCompany" name="developCompany" value="${elp.developCompany}"></td>
											<td align="right" class="head_form1" width="23%">设计单位：</td>
											<td align="left" class="head_form1">&nbsp;<input type="text" id="designCompany" name="designCompany" value="${elp.designCompany}"></td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">建设单位：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;<input type="text" id="buildCompany" name="buildCompany" value="${elp.buildCompany}"></td>
											<td align="right" class="head_form1" width="23%">占地面积：</td>
											<td align="left" class="head_form1">&nbsp;<input type="text" id="constructionArea" name="constructionArea" value="${elp.constructionArea}"></td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">使用面积：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;<input type="text" id="useArea" name="useArea" value="${elp.useArea}"></td>
											<td align="right" class="head_form1" width="23%">绿化面积：</td>
											<td align="left" class="head_form1">&nbsp;<input type="text" id="virescenceArea" name="virescenceArea" value="${elp.virescenceArea}"></td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">开工日期：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;<input type="text" id="beginDate" name="beginDate" readonly onclick="WdatePicker();" class="Wdate" value="${elp.beginDate}"></td>
											<td align="right" class="head_form1" width="23%">竣工日期：</td>
											<td align="left" class="head_form1">&nbsp;<input type="text" id="endDate" name="endDate" readonly onclick="WdatePicker();" class="Wdate" value="${elp.endDate}"></td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">进驻日期：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;<input type="text" id="useDate" name="useDate" readonly onclick="WdatePicker();" class="Wdate" value="${elp.useDate}"></td>
											<td align="right" class="head_form1" width="23%">联系电话：</td>
											<td align="left" class="head_form1">&nbsp;<input type="text" id="phone" name="phone" value="${elp.phone}"></td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">传 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真：</td>
											<td align="left"  class="head_form1" width="33%">&nbsp;<input type="text" id="fax" name="fax" value="${elp.fax}"></td>
											<td align="right" class="head_form1" width="23%">地 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</td>
											<td align="left" class="head_form1">&nbsp;<input type="text" id="address" name="address" value="${elp.address}"></td>
										</tr>
										<tr height="20"><td></td></tr>
											<tr>
											<td width="100%" colspan = "4">
													<table border="0" cellpadding="0" cellspacing="0" class="RptTable" id="metertb" >
														<tr>
															<td align="left" class="head_one" colspan="5">表具添加</td>
														</tr>
														<tr>
															<td nowrap="nowrap" align="left"  colspan="5">
																<input class="button" type="button" onclick="addMeter();" value="添加">
																<input type="button" class="button" onclick="deleteMeter('metertb','meterId');" value="删除">
															</td>
														</tr>
														<tr>
															<th class="RptTableHeadCellLock" width="5%"><input type="checkbox" onclick="selectedAll();" name="meter" width="5%"></th>
															<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</th>
															<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">表具编号</th>
															<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">表具名称</th>
														</tr>
														<c:choose>
															<c:when test="${empty meterlist}">
																<tr align="center" id="bjInfo">
																	<td colspan="5" align="center" class="head_form1">
																		<font color="red">暂时没有表具信息!</font>
																	</td>
																</tr>
															</c:when>
															<c:otherwise>
																<c:forEach items="${meterlist}" var="m" varStatus="vs">
																	<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
																		<td class="RptTableBodyCellLock" align="center"><input type="checkbox" id="meterId" name="meterId" ></td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${m[0].code}">${m[0].code}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${m[1].codeName}">${m[1].codeName}&nbsp;</td>
																	</tr>
																</c:forEach>
															</c:otherwise>
														</c:choose>
													</table>
											</td>
										</tr>
										
										<tr>
											<td align="right" colspan="20">
												<table border="0">
													<tr align="right">
														<td  align="center"><input class="button" onclick="checkForm();" type="button" value="确定"></td>
														<td ><input type="button" class="button" value="返回" onclick="closeModify();"></td>
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
