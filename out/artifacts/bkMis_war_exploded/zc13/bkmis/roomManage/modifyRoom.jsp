<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	 <title>修改房间</title>
	    <base target="_self" />
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
		<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/main.js" charset="UTF-8"></script>
		<script type="text/javascript" src="<%=path%>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
		<script type="text/javascript">
		   //验证表单楼盘名称和编号是否为空
		   function checkForm(){
		       var roomCode = document.getElementById("roomCode").value;
		       if(trim(roomCode) == ""){
		          alert("请输入楼盘编号！");
		          return false;
		       }else{
		          if(confirm("确定要修改吗？")){
		            window.opener=null;
                    window.open("","_self");
		            document.modifyRoomForm.action = "<%=path%>/room.do?method=modifyRoom";
		            document.modifyRoomForm.submit();
                    //window.close();  
		          }
		       }
		   }
		   //添加表具信息
		   function addMeter(){
		     var temp;
			 var buildId = document.getElementById("buildId").value;
			 var temp = window.showModalDialog("<%=path%>/room.do?method=goAddMeter&buildId="+buildId,this.window,"dialogWidth=550px;dialogHeight=400px;center=1");
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
		
		//给全选和反选参数初始化
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
				        var url = "<%=path%>/room.do";
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
		
		//添加房间设施
		  function addEquipment(){
		      var temp = window.showModalDialog("<%=path%>/getMeterial.do?method=chooseMaterials","","dialogWidth=760px;dialogHeight=400px;center=1;status:no;scroll:no;");
		      var oTable=document.getElementById("equipmentTb"); 
		       //用于判断所要添加了的设备信息已经添加了
		       var idTr = oTable.getElementsByTagName("tr");
		       var mark = false;
		       for(var i=3;i<idTr.length;i++){
		          var ids = document.getElementsByName("fixtureId");
		          for(var j=0; j<ids.length;j++){
		             var equipId = "";
		             var equipId = equipId +ids[j].value + ",";
		             var array = temp.split(",");
		             for(var k=0;k<array.length;k++){
		                if(ids[j].value == array[k]){
		                  mark = true;
		                  break;
		                }
		             }
		          }
		        }
		       if(temp != null && mark == false){
				var arr = temp.split(",");
				for(var i=0;i<arr.length;i++){
					$.post("<%=path%>/getMeterial.do",{method:"getMaterialById",id:arr[i]},function(data){
						var td;
						//var oTable=document.getElementById("equipmentTb"); 
					   	var oTr1 = oTable.insertRow(oTable.getElementsByTagName("tr").length);
					    for(var i=0;i<8;i++){
					    	if(i==0){
					    		td = oTr1.insertCell();
					    		td.className = "RptTableBodyCellLock";
			    				td.align = "center";
			    				td.innerHTML = "<input type=\"checkbox\" name=\"fixture\" value=\""+data.id+ "\">"+"<input type=\"hidden\" name=\"fixtureId\" value=\""+data.id+"\">"+"&nbsp;";
					    	}else if(i==1){
					    		td = oTr1.insertCell();
					    		td.className = "RptTableBodyCellLock";
			    				td.align = "center";
			    				td.innerText = oTr1.rowIndex-2;
					    	}else if(i==2){
					    		td = oTr1.insertCell();
					    		td.className = "RptTableBodyCell";
			    				td.align = "center";
			    				td.innerHTML = data.code+"<input type=\"hidden\" name=\"equipCode\" value=\""+data.code+"\">"+"&nbsp;";
					    	}else if(i==3){
					    		td = oTr1.insertCell();
					    		td.className = "RptTableBodyCell";
			    				td.align = "center";
			    				td.innerHTML = data.name+"<input type=\"hidden\" name=\"equipName\" value=\""+data.name+"\">"+"&nbsp;";
					    	}else if(i==4){
					    		td = oTr1.insertCell();
					    		td.className = "RptTableBodyCell";
			    				td.align = "center";
			    				td.innerHTML = data.spec+"<input type=\"hidden\" name=\"category\" value=\""+data.spec+"\">"+"&nbsp;";
					    	}else if(i==5){
					    		td = oTr1.insertCell();
					    		td.className = "RptTableBodyCell";
			    				td.align = "center";
			    				td.innerHTML = "<input type=\"text\" name=\"number\" value=\"1\">";
					    	}else if(i==7){
					    		td = oTr1.insertCell();
					    		td.className = "RptTableBodyCell";
			    				td.align = "center";
			    				td.innerHTML = "<input type=\"text\" name=\"ebz\">";
					    	}
					    }
					});		
				}
				//用于设置复选框
				flag = false;
				//对于显示“没有房间设备信息”的处理
			     var equipInfotr = oTable.getElementsByTagName("tr");
			     
			     if(equipInfotr[equipInfotr.length-1].id == "equipInfo"){
			          oTable.deleteRow(equipInfotr.length-1);
			     }
			 }
		   }
		   
		  //删除房间设施
		    function deleteEquipment(){
		       var tb = document.getElementById("equipmentTb");
			   var radios = document.getElementsByName("fixture");
			    //防止在没有任何表具信息的时候点击删除报js错误！
			 if(!(radios.length >= 1)){
			      alert("暂时无房间设施信息，无法执行删除操作！");
			      return false;
			 }else{
			     for( var j=radios.length-1;j>=0;j--){
				    if(radios[j].checked){
				       //用与在后台删除选中的房间设备信息
			           var url = "<%=path%>/room.do";
				       $.post(url,{method:"deleteEquip",equipId:radios[j].value},function(data){});
					   tb.deleteRow(j + 3);
				    } 
			     }
			     //用来在删除一行之后重新设置序号的
			     var tr = tb.getElementsByTagName("tr");
			     for(var i=3;i<tr.length;i++){
				      tr[i].getElementsByTagName("td")[1].innerText = tr[i].rowIndex-2;
			     }
		         //用来把标题行的chenkbox设置为未选中状态
			     document.getElementsByName("equipment")[0].checked = "";
			 
			     //删除掉所有的数据后再添加一行，用于显示没有表具信息的提示
			     var equipInfotr = tb.getElementsByTagName("tr");
			     if(equipInfotr.length<=3){
			          var temptr =  tb.insertRow(3);
			          var temptd = temptr.insertCell();
			          temptr.id = "equipInfo";
			          temptd.align = "center";
			          temptd.className = "head_form1";
			          temptd.colSpan = "8";
			         temptd.innerHTML = "<font color=\"red\">暂时没有房间设备信息!</font>";
			     }
			  }
           }
        
		//选中所有的checkbox
        function selectedAll(name){
        
           var list = document.getElementsByName(name);
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
		  function closeWindow(){
               
              window.close();
		  }
        </script> 
  </head>
	<body>
		<form method="post" name="modifyRoomForm">
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="5" colspan="9">
						<c:if test="${flag}">
							<script type="text/javascript">
								returnValue = "flag";
								this.close();
							</script>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="165" nowrap="nowrap" class="form_line">修改房间信息</td>
								<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" 	height="35"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="menu_tab2" align="center">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="center">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="10" colspan="9"><input type="hidden" name="roomId" value="${eroom.roomId }"><input type="hidden" name="code" id="code"><input type="hidden" name="type" id="type"></td>
										</tr>
										<tr>
											<td height="10" colspan="9"><input type="hidden" id="buildId" name="buildId" value="${eroom.EBuilds.buildId}">
											                            <input type="hidden" name="roomFullName" value="${eroom.roomFullName }">
											                            <input type="hidden" name="status" value="${eroom.status }">
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left">
										<tr>
											<td align="right" class="head_form1" width="12%">房&nbsp;&nbsp;间&nbsp;&nbsp;号：</td>
											<td align="left" class="head_form1" width="33%">&nbsp;&nbsp;<input type="text" id="roomCode" name="roomCode" value="${eroom.roomCode}"><font color="red">*</font></td>
											<td align="right" class="head_form1" width="23%">房&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：</td>
											<td align="left" class="head_form1">&nbsp;
												<select name="roomType" style="width: 130px;">
													<option value="">- - -请选择- - -</option>
													<c:forEach items="${roomTypelist }" var="r">
														<option value="${r.codeName }" <c:if test="${r.codeName == eroom.roomType }">selected</c:if>>${r.codeName }</option>
													</c:forEach>
												</select>
											</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">单&nbsp;&nbsp;元&nbsp;&nbsp;号：</td>
											<td align="left" class="head_form1" width="33%">&nbsp;&nbsp;<input type="text" id="unitId" name="unitId" value="${eroom.unitId }"></td>
											<td align="right" class="head_form1" width="23%">&nbsp;楼&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;层：</td>
											<td align="left" class="head_form1" >&nbsp;
												<select name="floor" style="width: 130px;">
													<c:forEach begin="1" end="${eroom.EBuilds.floor}" varStatus="vs">
														<option value="${vs.count}" <c:if test="${vs.count == eroom.floor }">selected</c:if>>${vs.count}</option>
													</c:forEach>
												</select>
												<font color="red">*</font>
											</td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">建筑面积：</td>
											<td align="left" class="head_form1" width="33%">&nbsp;&nbsp;<input type="text" id="constructionArea" name="constructionArea" value="${eroom.constructionArea }"></td>
											<td align="right" class="head_form1" width="23%">使用面积：</td>
											<td align="left" class="head_form1" >&nbsp;&nbsp;<input type="text" id="useArea" name="useArea" value="${eroom.useArea }"></td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">房间朝向：</td>
											<td align="left" class="head_form1" width="33%">&nbsp;
												<select name="toward" style="width: 130px;">
													<option value="" selected="selected">- - -请选择- - -</option>
													<c:forEach items="${towardlist}" var="t">
														<option value="${t.codeName}" <c:if test="${t.codeName == eroom.toward }">selected</c:if>>${t.codeName}</option>
													</c:forEach>
												</select>
											</td>
											<td align="right" class="head_form1" width="12%">所属区域：</td>
											<td align="left" class="head_form1" width="33%">&nbsp;&nbsp;<input type="text" id="regional" name="regional" value="${eroom.regional }"></td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="12%">电话号码：</td>
											<td align="left" class="head_form1"  colspan="3">&nbsp;&nbsp;<input type="text" id="phoneNumber" name="phoneNumber" value="${eroom.phoneNumber }">&nbsp;<span style=" color: blue;">注：多个电话号码以分号隔开，如：5605;5602</span></td>
										</tr>
										<tr>
											<td align="right" class="head_form1" width="23%">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
											<td align="left" class="head_form1" colspan="3">&nbsp;&nbsp;<textarea style="width: 98%; height: 50px; background-color: #F0F8FA" name="bz" title="最多1000个字符">${eroom.bz }</textarea></td>
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
															<th class="RptTableHeadCellLock" width="5%"><input type="checkbox" onclick="selectedAll('meterId');;" name="meter" width="5%"></th>
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
										<tr height="20"><td></td></tr>
											<tr>
											<td width="100%" colspan = "4">
													<table border="0" cellpadding="0" cellspacing="0" class="RptTable" id="equipmentTb">
														<tr>
															<td align="left" class="head_one" colspan="8">设施设备</td>
														</tr>
														<tr>
														<td nowrap="nowrap" align="left" colspan="8">
															<input class="button" type="button" onclick="addEquipment();" value="添加">
															<input type="button" class="button" onclick="deleteEquipment('metertb','meterId');" value="删除">
														</td>
													</tr>
														<tr>
															<th class="RptTableHeadCellLock" width="5%"><input type="checkbox" onclick="selectedAll('fixture');" name="equipment" width="5%"></th>
															<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</th>
											                <th nowrap="nowrap" class="RptTableHeadCellLock" align="center">设备编号</th>
											                <th nowrap="nowrap" class="RptTableHeadCellLock" align="center">设备名称</th>
											                <th nowrap="nowrap" class="RptTableHeadCellLock" align="center">设备类型</th>
											                <th nowrap="nowrap" class="RptTableHeadCellLock" align="center">数量</th>
											                <th nowrap="nowrap" class="RptTableHeadCellLock" align="center">备注</th>
														</tr>
														<c:choose>
															<c:when test="${empty equiplist}">
																<tr align="center" id="equipInfo">
																	<td colspan="8" align="center" class="head_form1">
																		<font color="red">暂时没有房间设备信息!</font>
																	</td>
																</tr>
															</c:when>
															<c:otherwise>
																<c:forEach items="${equiplist}" var="e" varStatus="vs">
																	<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
																		<td class="RptTableBodyCellLock" align="center"><input type="checkbox" id="fixture" name="fixture" value="${e[0].id}"></td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${vs.count}">${vs.count}&nbsp;</td>
																		<!-- equiplist里面存放的三个对象  -->
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${e[1].code}">${e[1].code}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${e[1].name}">${e[1].name}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${e[2].name}">${e[2].name}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${e[0].amount}">${e[0].amount}&nbsp;</td>
																		<td nowrap="nowrap" class="RptTableBodyCell" align="center" title="${e[0].bz}">${e[0].bz}&nbsp;</td>
																	</tr>
																</c:forEach>
															</c:otherwise>
														</c:choose>
													</table>
											</td>
										</tr>
										<tr height="40"><td></td></tr>
										<tr>
											<td colspan="9" align="center">
												<table>
													<tr >
														<td  align="center"><input class="button" onclick="checkForm();" type="button" id = "focuson" value="确定">
															
														</td>
														<td >
															<input type="button" class="button" value="取消" onclick="closeWindow();">
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
