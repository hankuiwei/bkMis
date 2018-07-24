<%@ page language="java" import="java.util.*,com.zc13.util.*,java.text.*"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String today = dateFormat.format(new Date());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>客户报修</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
	<script type="text/javascript">
		/**
		 * 判断是否是一个数字（只能输入+-号，小数点，数字）
		 * 输入框的格式为：<input type="text" t_value="" o_value="" onkeyup="checkIsANum(this)" >
		 * @param {} obj
		 */
		function checkIsANum(obj){
			if(!obj.value.match(/^[\+\-]?\d*?\.?\d*?$/))
				obj.value=obj.t_value;
			else 
				obj.t_value=obj.value;
			if(obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))
				obj.o_value=obj.value;
		}
		
		//是否领材料
		function sel(obj){
			var tr1 = document.getElementById("tr1");
			var tr2 = document.getElementById("tr2");
			var sel = obj.value;
			if(sel=="2"){
				tr1.style.display="none";
				tr2.style.display="none";
			}
			if(sel=="1"){
				tr1.style.display="block";
				tr2.style.display="block";
			}
		}
		function finish(){
			var x = Validator.Validate(document.getElementById('form1'),2);
			var bh=new Array();
			var sl=new Array();		
			var amount_bh = document.getElementsByName("materialCode");
			var amount_sl = document.getElementsByName("amount");
			for(var i = 0;i<amount_bh.length;i++){	//多个编号 
			bh +=amount_bh[i].value+",";
			}
			for(var i = 0;i<amount_sl.length;i++){//多个数量
			sl +=amount_sl[i].value+",";
			}
			if(x){
				
              //*****派工阶段所选材料数量小于库存提示用户信息*******
              $.ajax({
			    type:"post",
				url:"${pageContext.request.contextPath}/getMeterial.do?method=getKyslAnd",
				data:{"itemIds":bh,"sl":sl},
				dataType:"json",
				success:function(data){
				if(data.flag==2){
				alert("所选材料数量不能大于可用库存！");
				return false;
				}
				if(data.flag==0 || data.flag==1){
					if(window.confirm("是否打印派工单？")){
						document.getElementById("isPrint").value = "1";
					}
					document.form1.action = "<%=path%>/client.do?method=dealClient";
					document.form1.submit();
					}
				}
			});
              
			}
		}
		
		//添加材料
		function add(){
			var sum;
			var strr = ",";//页面上已经存在的所有商品编号
			var tbout=document.getElementById("tb"); 
			var trout=tbout.getElementsByTagName("tr");
			for(var i=2;i<trout.length;i++){
				tdout = trout[i].getElementsByTagName("td");
				strr = strr + tdout[2].innerText + ",";
			}
			url = "<%=path%>/getMeterial.do?method=chooseMaterials";
			var options = "dialogWidth:800px;dialogHeight:500px;status:no;scroll:no;";
			var win = window.showModalDialog(url, this.window, options);
			if(win!=null){
				var arr = win.split(",");
				for(var i=0;i<arr.length;i++){
					$.ajax({
						type:"post",
						url:"${pageContext.request.contextPath}/getMeterial.do?method=getMaterialById",
						data:{id:arr[i]},
						dataType:"json",
						success:function(data){
							var flag = true;
							//如果已选材料中包含了本次选取的材料，则将flag置为false
							if(contains(strr,data.code,true)){
								flag = false;
							}
							if(flag){
								var oTr1 = tbout.insertRow(tbout.getElementsByTagName("tr").length);
								
								var td = oTr1.insertCell();
								td.className = "RptTableBodyCellLock";
				    			td.align = "center";
				    			td.innerHTML = "<input type=\"checkbox\" name=\"checkm\">";
				    			
				    			var td = oTr1.insertCell();
						    	td.className = "RptTableBodyCellLock";
				    			td.align = "center";
				    			td.innerText = oTr1.rowIndex-1;
				    				
				    			var td = oTr1.insertCell();
						    	td.className = "RptTableBodyCell";
				    			td.align = "center";
				    			td.innerHTML = data.code+"<input type=\"hidden\" name=\"materialCode\" value=\""+data.code+"\">"+"&nbsp;";
						    	
						    	var td = oTr1.insertCell();
						    	td.className = "RptTableBodyCell";
				    			td.align = "center";
				    			td.innerHTML = data.name+"<input type=\"hidden\" name=\"materialName\" value=\""+data.name+"\">"+"&nbsp;";
				    			
						    	var td = oTr1.insertCell();
						    	td.className = "RptTableBodyCell";
				    			td.align = "center";
				    			td.innerHTML = data.spec+"<input type=\"hidden\" name=\"spec\" value=\""+data.spec+"\">"+"&nbsp;";
						    	
						    	var td = oTr1.insertCell();
						    	td.className = "RptTableBodyCell";
				    			td.align = "center";
				    			td.innerHTML = data.unitPrice+"<input type=\"hidden\" name=\"unitPrice\" value=\""+data.unitPrice+"\">"+"&nbsp;";
						    	
						    	var td = oTr1.insertCell();
						    	td.className = "RptTableBodyCell";
				    			td.align = "center";
				    			td.innerHTML = "<input type=\"text\" name=\"amount\" t_value=\"\" o_value=\"\" value=\"1\" onchange=\"getPerSum(this)\" onkeyup=\"checkIsANum(this)\" >";
						    	
						    	var td = oTr1.insertCell();
						    	td.className = "RptTableBodyCell";
				    			td.align = "center";
				    			td.innerHTML = "<select name='surchargeWay'  onchange='getPerSum(this)'><option value='<%=Contants.PROPORTIONAL%>'><%=Contants.PROPORTIONAL%></option><option value='<%=Contants.FIXED_VALUE%>'><%=Contants.FIXED_VALUE%></option></select>";
				    			
				    			var td = oTr1.insertCell();
						    	td.className = "RptTableBodyCell";
				    			td.align = "center";
				    			td.width = "60px";
				    			td.innerHTML = "<input type=\"text\"  name=\"surchargeAmount\" value=\"0.15\" onchange=\"getPerSum(this)\" t_value=\"\" o_value=\"\" onkeyup=\"checkIsANum(this)\" >";
						    	
						    	var td = oTr1.insertCell();
						    	td.className = "RptTableBodyCell";
				    			td.align = "center";
				    			td.innerHTML = "<input type=\"text\" name=\"amount_money\" t_value=\"\" o_value=\"\" onchange=\"getsum()\" value=\""+parseFloat(data.unitPrice*(1+0.15)).toFixed(2).toString()+"\" onkeyup=\"checkIsANum(this)\">"+"&nbsp;";
							}
							getsum();
						}
					});
				}
			}
		} 
		
		//统计材料总金额
		function getsum(){
			var sum = 0;
			var amount_money = document.getElementsByName("amount_money");
			for(var i = 0;i<amount_money.length;i++){
				sum += parseFloat(amount_money[i].value);
			}
			//保留到元
			document.getElementById("amountMoney").value = parseFloat(sum).toFixed(0).toString();
		}
		
		//计算单种材料的总金额
		function getPerSum(obj){
			var rowindex = obj.parentNode.parentNode.rowIndex;
			var unitPrice = document.getElementsByName("unitPrice")[rowindex-2].value;
			var amount = document.getElementsByName("amount")[rowindex-2].value;
			var surchargeWay = document.getElementsByName("surchargeWay")[rowindex-2].value;
			var surchargeAmount = document.getElementsByName("surchargeAmount")[rowindex-2].value;
			var amount_money = 0;
			amount_money = parseFloat(unitPrice)*parseFloat(amount);
			if(surchargeWay=="<%=Contants.PROPORTIONAL%>"){//如果是按比例
				amount_money = amount_money*(1+parseFloat(surchargeAmount));
			}else{
				amount_money = amount_money+parseFloat(surchargeAmount);
			}
			document.getElementsByName("amount_money")[rowindex-2].value = parseFloat(amount_money).toFixed(2).toString();
			getsum();
		}
		
		//删除一行材料
		function del(){
			var tb = document.getElementById("tb");
			var radios = document.getElementsByName("checkm");
			for( var j=radios.length-1;j>=0;j--){
				if(radios[j].checked){
					tb.deleteRow(j + 2);
				} 
			}
			var tr = tb.getElementsByTagName("tr");
			for(var i=2;i<tr.length;i++){
				tr[i].getElementsByTagName("td")[1].innerText = tr[i].rowIndex-1;
			}
			getsum();
		}
		
		function return1(){
			//window.location.href="client.do?method=getList";
			document.form1.action="client.do?method=getList";
			document.form1.submit();
		}
		
		//计算工时
		function getManHour(){
			var begin = document.getElementById("appearanceTime").value;
			var end = document.getElementById("leaveTime").value;
			if(trim(begin)!=""&&trim(end)!=""){
				$.post("<%=path%>/repair.do",{method:"getManHour",begin:begin,end:end},function(data){
					document.getElementById("manHour").value = data;
				})
			}
		}
		
		//选择人员
		var sendingPersonnel="";//状态为已派遣的员工
		var otherPersonnel="";//状态为不是已派遣的员工
		function selectPersonnel(){
			var ids = sendingPersonnel;
			var params = "personnel.do?method=getDispatchPersonnel@ids="+ids+"@forward=showDispatchPersonnelDialog@cx_isInPost=<%=Contants.ISINPOST%>@cx_isOut="+encodeURI(encodeURI("<%=Contants.ISNOTOUT%>"));
			var url = "jump.jsp?url="+params;
			var options = "dialogWidth:700px;dialogHeight:400px;status:no;scroll:no;";
			var returnVal = window.showModalDialog(url,this.window, options);
			if(typeof(returnVal)!="undefined"){
				var newIds = "";
				var newNames = "";
				$("#phones").html("");
				for(var i = 0;i<returnVal.length;i++){
					newIds += returnVal[i].personnelId+",";
					newNames += returnVal[i].personnelName+"("+returnVal[i].personnelCode+");";
					$("#phones").append("<span width='140px'>&nbsp;"+returnVal[i].personnelName+"("+returnVal[i].personnelCode+")：<input type='hidden' name='personnelName' id='personnelName' value='"+returnVal[i].personnelName+"' /></span><input type='text' name='personnelphone' value='"+returnVal[i].phone+"' /><br />");
				}
				newIds = newIds.substring(0,newIds.length-1);
				if(newIds!=null&&newIds!=""){
					if(otherPersonnel!=null&&otherPersonnel!=""){
						document.getElementById("sendedMan").value = otherPersonnel+","+newIds;
					}else{
						document.getElementById("sendedMan").value = newIds;
					}
					document.getElementById("newSendingMan").value = newIds;
				}else{
					document.getElementById("sendedMan").value=otherPersonnel;
				}
				document.getElementById("sendedManName").value=newNames;
			}
		}
		
		//发送短信
		function sendMessage(){
			var x = Validator.Validate(document.getElementById('form1'),2);
			if(!x){
				return;
			}
			var phoneObjs;
			var nameObjs;
			var phones = "";
			var names = "";
			try{
				phoneObjs = document.getElementsByName("personnelphone");
				nameObjs = document.getElementsByName("personnelName");
			}catch(e){
				alert("请选择要发送的人员！");
				return;
			}
			for(var i = 0;i<phoneObjs.length;i++){
				if(phoneObjs[i]!=null&&phoneObjs!=""){
					phones += phoneObjs[i].value+";";
				}
			}
			if(phones!=""){
				phones = phones.substring(0,phones.length-1);
			}else{
				alert("请选择要发送的人员并输入手机号码！");
				return;
			}
			for(var i=0;i<nameObjs.length;i++){
				if(nameObjs[i]!=null&&nameObjs!=""){
					names += nameObjs[i].value+";";
				}
			}
			if(names!=""){
				names = names.substring(0,names.length-1);
			}
			var contents = document.getElementById("contents").value;
			$.post("<%=path%>/client.do",{method:"sendMessage",phones:phones,names:names,contents:contents},function(data){
				if(data=="1"){
					alert("短信已发送！");
				}else{
					alert("短信发送失败！");
				}
			});
		}
	</script>
	
	
</head>
<body>
<form name="form1" method="post">
	<table width="99%" height = "96%" border="0" cellpadding="0" align="center" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5">
    			<input type="hidden" id="isPrint" name="isPrint" value="0">
    			<!-- 查询条件start -->
    			<input type="hidden" id="flag" name="flag" value="${SerClientMaintainForm.flag }">
    			<input type="hidden" id="cx_buildId" name="cx_buildId" value="${SerClientMaintainForm.cx_buildId }">
    			<input type="hidden" id="cx_sendedMan" name="cx_sendedMan" value="${SerClientMaintainForm.cx_sendedMan }">
    			<input type="hidden" id="selstatus" name="selstatus" value="${SerClientMaintainForm.selstatus }">
    			<input type="hidden" id="begindate" name="begindate" value="${SerClientMaintainForm.begindate }">
    			<input type="hidden" id="enddate" name="enddate" value="${SerClientMaintainForm.enddate }">
    			<input type="hidden" id="cx_isEnabled" name="cx_isEnabled" value="${SerClientMaintainForm.cx_isEnabled }">
    			<!-- 查询条件end -->
    		
    			<!-- 数据中的一些未显示的信息start -->
    			<input type="hidden" id="lpId" name="lpId" value="${bean.lpId }">
    			<input type="hidden" id="id" name="id" value="${bean.id }">
    			<input type="hidden" id="sendDutyMan" name="sendDutyMan" value="${bean.sendDutyMan }">
    			<input type="hidden" id="appearanceTime" name="appearanceTime" value="${bean.appearanceTime }">
    			<input type="hidden" id="leaveTime" name="leaveTime" value="${bean.leaveTime }">
    			<input type="hidden" id="status" name="status" value="<%=Contants.BEING_REPAIRED %>">
    			<!-- 数据中的一些未显示的信息end -->
    			<input type="hidden" id="oldSendedMan" name="oldSendedMan" value="${bean.sendedMan }">
    			<!-- 原来的状态为已派遣的员工id -->
    			<input type="hidden" id="oldSendingMan" name="oldSendingMan" value="">
    			<!-- 修改后的状态为已派遣的员工id -->
    			<input type="hidden" id="newSendingMan" name="newSendingMan" value="">
    		</td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">客户报修处理</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr height = "95%"><!-- 这个td负责画正式内容的左、右、下方的边框 -->
    		<td class="menu_tab2">
     			<table width="95%" align="center" border="0" cellspacing="0" cellpadding="0">
     				<tr>
     					<th align="left" colspan="4" class="head_one">来电来访记录</th>
     				</tr>
     				<tr>
							<td class="head_rols" align="right" width="120px;">报修类型：</td>
							<td class="fist_rows">
								&nbsp;${bean.type }
								<input type="hidden" name="type" id="type" value="${bean.type }" />
							</td>
							<td class="fist_rows" align="right" width="185px;">报修项目：</td>
							<td class="fist_rows">
								&nbsp;${bean.project }
								<input type="hidden" name="project" id="project" value="${bean.project }" />
								<input type="hidden" name="projectId" id="projectId" value="${bean.projectId }" />
							</td>
					</tr>
					<c:if test="${bean.type eq '业主报修'}">
					<tr>
						<td class="head_rols" align="right">楼幢：</td>
						<td class="head_form1" align="left">
							<select id="buildId1" style="width: 130" disabled="disabled">
								<option value="0">请选择</option>
							<c:choose>
							<c:when test="${empty list3}">
							</c:when>
							<c:otherwise>
							<c:forEach items="${list3}" var="v">
								<option value="${v.buildId }" <c:if test="${bean.buildId == v.buildId}">selected</c:if>>${v.buildName }</option>
							</c:forEach>
							</c:otherwise>
							</c:choose>
							</select>
							<input type="hidden" name="buildId" value="${bean.buildId }" />
						</td>
						<td class="head_form1" align="right" id="td1">房间号：</td>
						<td class="head_form1" align="left">
							<select id="roomId1" style="width: 130" disabled="disabled">
								<option value="0">请选择</option>
							<c:choose>
							<c:when test="${empty list4}">
							</c:when>
							<c:otherwise>
							<c:forEach items="${list4}" var="v">
								<option value="${v.roomId }" <c:if test="${bean.roomId == v.roomId}">selected</c:if> >${v.roomCode }</option>
							</c:forEach>
							</c:otherwise>
							</c:choose>
							</select>
							<input type="hidden" name="roomId" value="${bean.roomId }" />
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right">联系电话：</td>
						<td class="head_form1">&nbsp;${bean.phone }<input type="hidden" value="${bean.phone }" name="phone" ></td>
						<td class="head_form1" align="right">报修人：</td>
						<td class="head_form1">&nbsp;${bean.name }<input type="hidden" value="${bean.name }" name="name"></td>
					</tr>
					</c:if>
					
					<c:if test="${bean.type eq '公共区域'}">
					<tr>
						<td class="head_left" align="right">区域：</td>
						<td class="head_form1" align="left">
							<select id="area1" style="display: block;" disabled="disabled">
							<c:choose>
							<c:when test="${empty list2}">
							</c:when>
							<c:otherwise>
							<c:forEach items="${list2}" var="v">
								<option value="${v.codeName }" <c:if test="${bean.area eq v.codeName }">selected</c:if> >${v.codeName }</option>
							</c:forEach>
							</c:otherwise>
							</c:choose>
							</select>
							<input type="hidden" name="area" value="${bean.area }" />
						</td>
						<td class="head_form1" align="right">联系电话：</td>
						<td class="head_form1"><input type="text" value="${bean.phone }" name="phone" readonly="readonly" ></td>
					</tr>
					<tr>
						<td class="head_left" align="right">报修人：</td>
						<td class="head_form1" colspan="3">&nbsp;${bean.name }<input type="hidden" value="${bean.name }" name="name"></td>
					</tr>
					</c:if>
					<tr>
						<td class="head_left" align="right">报修内容：</td>
						<td class="head_form1" colspan="3">
							<textarea rows="3" name="contentExplain" style="width: 80%" id="contentExplain" disabled="disabled">${bean.contentExplain }</textarea>
							<input type="hidden" name="contentExplain" value="${bean.contentExplain }" />
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right">报修时间：</td>
						<td class="head_form1">&nbsp;${bean.date}<input type="hidden" value="${bean.date}" name="date"></td>
						<td class="head_form1" align="right">接听(接待)人：</td>
						<td class="head_form1">&nbsp;${bean.clerk }<input type="hidden" value="${bean.clerk }" name="clerk"></td>
					</tr>
     				<tr>
     					<th align="left" colspan="4" class="head_one">工作情况</th>
     				</tr>
     				<tr style=" display: none">
						<td class="head_left" align="right">报修状态：</td>
						<td class="head_form1"><select id="status" name="status">
							<option selected value="已处理">已处理</option>
							</select></td>
						<td class="head_form1" align="right">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
					</tr>
					<tr>
						<td class="head_left" align="right">派工单编号：</td>
						<td class="head_form1" align="left">
							<c:if test="${!empty bean.sendcardCode }">
								<input type="text" name="sendcardCode" id="sendcardCode" value="${bean.sendcardCode }" datatype="Require" msg="派工单编号不得为空！">
							</c:if>
							<c:if test="${empty bean.sendcardCode }">
								<input type="text" readonly name="sendcardCode" id="sendcardCode" value="${newCode }" datatype="Require" msg="派工单编号不得为空！">
							</c:if>
							<font color="red">*</font>
						</td>
						<td class="head_form1" align="right">派工执行人：</td>
						<td class="head_form1" align="left"><input type="text" name="sendDutyMan" id="sendDutyMan" value="${bean.sendDutyMan }"></td>
					</tr>
					<tr>
     					<td align="left" colspan="4" class="head_left">历史派工人员列表：</td>
     				</tr>
     				<tr>
     					<td colspan="4"  class="head_left">
     						<table width="100%" border="0" cellpadding="0" cellspacing="0" class = "RptTable">
	     						<tr>
	     							<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</td>
			     					<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">员工姓名</td>
			     					<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">到场时间</td>
			     					<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">离场时间</td>
			     					<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">工作时间</td>
			     					<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">状态</td>
	     						</tr>
	     						<c:choose>
	     							<c:when test="${empty dispatchList}">
	     								<tr><td colspan="6" align="center"><font color="red">暂无历史派遣信息!</font></td></tr>
	     							</c:when>
	     							<c:otherwise>
	     								<c:forEach items="${dispatchList}" var="v" varStatus="vs">
	     									<c:if test="${v.status ne '已派遣'}">
	     									<script>
	     										otherPersonnel += "${v.hrPersonnel.personnelId},";
	     									</script>
	     									<tr>
	     										<td  nowrap="nowrap" class="RptTableBodyCellLock" align="center">&nbsp;${vs.index+1 }</td>
	     										<td  nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.hrPersonnel.personnelName }</td>
	     										<td  nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.appearanceTime }</td>
	     										<td  nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.leaveTime }</td>
	     										<td  nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.workHours }</td>
	     										<td  nowrap="nowrap" class="RptTableBodyCell" align="center">&nbsp;${v.status }</td>
	     									</tr>
	     									</c:if>
	     								</c:forEach>
	     								<script>
	     									if(otherPersonnel == ""){
	     										document.write("<tr><td colspan=\"6\" align=\"center\"><font color=\"red\">暂无历史派遣信息!</font></td></tr>");
	     									}
	     								</script>
	     							</c:otherwise>
	     						</c:choose>
     						</table>
     					</td>
     					
     				</tr>
     				
					<tr>
						<td class="head_left" align="right">当前派工人员：</td>
						<td class="head_form1">
						<input type="text" name="sendedManName" datatype="Require" msg="请选择派工人员！"  id="sendedManName" style="width:80%" value="" onclick="selectPersonnel();" readonly="readonly" /><font color="red">*</font>
						<input type="hidden" name="sendedMan" id="sendedMan" value="${bean.sendedMan }" />
						</td> 
						<td class="head_form1" align="right">派工时间：</td>
						<td class="head_form1"><input type="text" name="sendTime" style=" width: 138px" id="sendTime" value="${bean.sendTime }" readonly onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"></td>
						<script>
							var sendTime = document.getElementById("sendTime").value;
							if(sendTime==null||sendTime==""){
								document.getElementById("sendTime").value = "<%=today%>";
							}
						</script>
					</tr>
					<tr>
						<td class="head_left" align="right">手机号码：</td>
						<td class="head_form1">
							<span id="phones"></span>
						</td>
						<td class="head_form1" align="right">
							发送内容：<br />
							<span style="font-size:12px;color:red;">(中文长度超过70的自动拆分发送)</span>
						</td>
						<td class="head_form1" colspan="1">
							<textarea rows="3" style="width: 80%;" dataType="Require" msg='请输入要发送的内容！' name="contents" id="contents">
							<c:if test="${bean.type eq '业主报修'}">报修项目：${bean.project };房间号：<c:choose><c:when test="${empty list4}"></c:when><c:otherwise><c:forEach items="${list4}" var="v"><c:if test="${bean.roomId == v.roomId}">${v.roomCode }</c:if></c:forEach></c:otherwise></c:choose></c:if>
							<c:if test="${bean.type eq '公共区域'}">报修项目：${bean.project };区域：${bean.area }</c:if>
							</textarea>
							<script>
								document.getElementById("contents").value = trim(document.getElementById("contents").value);
							</script> 
						</td>
					</tr>
					<tr>
						<td class="head_left" colspan="4" align="center"><input type="button" class="button" value="发送短信" onclick="sendMessage();"></td>
					</tr>
					<tr>
						<td class="head_left" align="right">处理方法：</td>
						<td class="head_form1" colspan="3">
							<select name="doMethod" id="doMethod">
								<option value="<%=Contants.DOMETHOD_MAINTENANCE %>" <c:if test="${bean.doMethod eq '物业维修' }">selected</c:if>  ><%=Contants.DOMETHOD_MAINTENANCE %></option>
								<option value="<%=Contants.DOMETHOD_SERVICE %>" <c:if test="${bean.doMethod eq '维保期' }">selected</c:if> ><%=Contants.DOMETHOD_SERVICE %></option>
							</select>
						</td>
					</tr>
					<!--
					<tr>
						<td class="head_left" align="right">维修内容：</td>
						<td class="head_form1" colspan="3"><textarea rows="3" style="width: 80%" name="maintainContent" id="maintainContent">${bean.maintainContent }</textarea></td>
					</tr>
					
					<tr>
						<td class="head_left" align="right">到场时间：</td>
						<td class="head_form1"><input type="text" name="appearanceTime"  style=" width: 138px" onchange="getManHour()" id="appearanceTime" value="${bean.appearanceTime }" readonly  onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"></td>
						<td class="head_form1" align="right">离场时间：</td>
						<td class="head_form1"><input type="text" name="leaveTime"  style=" width: 138px" onchange="getManHour()"id="leaveTime" value="${bean.leaveTime }" readonly  onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"></td>
					</tr>
					<tr>
						<td class="head_left" align="right">工作：</td>
						<td class="head_form1"><input type="text" name="manHour" id="manHour" value="${bean.manHour }">小时</td>
						<td class="head_form1" align="right">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
					</tr>
					-->
					
					<tr>
     					<th align="left" colspan="4" class="head_one">耗用材料：</th>
     				</tr>
					<tr>
						<td class="head_left" align="right" width="15%">是否领料：</td>
						<td class="head_form1" width="35%">
							<select onchange="sel(this)" id="select11">
								<option value="1">是</option>
								<option value="2" selected="selected">否</option>
							</select>
						</td>
						<td class="head_form1" align="right">出料部门：</td>
						<td class="head_form1" width="35%">
							<select name="department" id="department" style="width: 130">
							<c:choose>
							<c:when test="${empty list5}">
							</c:when>
							<c:otherwise>
							<c:forEach items="${list5}" var="v">
								<option value="${v.codeName }">${v.codeName }</option>
							</c:forEach>
							</c:otherwise>
							</c:choose>
							</select>
						</td>
					</tr>
					<tr id="tr1" style="display: none;">
						<td class="head_left" colspan="4">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class = "RptTable" id="tb">
								<tr>
									<td colspan="7"><input type="button" class="button" value="添加" onclick="add()"><input type="button" class="button" value="删除" onclick="del()"></td>
								</tr>
								<tr>
									<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">&nbsp;</td>
									<td width="5%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</td>
									<td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">材料编号</td>
								    <td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">材料名称</td>
									<td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">规格</td>
									<td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">单价</td>
									<td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">数量</td>
									<td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">加收方式</td>
									<td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">加收金额</td>
									<td width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">金额</td>
								</tr>
									<c:choose>
										<c:when test="${empty list6}">
											<script type="text/javascript">
												document.getElementById("select11").value = "2";
											</script>
										</c:when>
										<c:otherwise>
											<script type="text/javascript">
												document.getElementById("select11").value = "1";
											</script>
											<c:forEach items="${list6}" var="v" varStatus="tag">
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';" id="trm">
											 	<td nowrap="nowrap" class="RptTableBodyCellLock" align="center">
											 		<input type="checkbox" id="checkbox1" name="checkm" value="${v.id}">
												</td>
								                <td nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count}</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.materialCode}<input type="hidden" name="materialCode" value="${v.materialCode}">&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.materialName}<input type="hidden" name="materialName" value="${v.materialName}">&nbsp;</td>
												<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.spec}<input type="hidden" name="spec" value="${v.spec}">&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.unitPrice}<input type="hidden" name="unitPrice" value="${v.unitPrice}">&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center"><input type="text" name="amount"  t_value="" o_value="" value="${v.amount}" onchange="getPerSum(this)" onkeyup="checkIsANum(this)">&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center">
								                	<select name='surchargeWay'  onchange='getPerSum(this)'>
								                		<option value='<%=Contants.PROPORTIONAL%>' <c:if test="${v.surchargeWay eq '按比例'}">selected</c:if>><%=Contants.PROPORTIONAL%></option>
								                		<option value='<%=Contants.FIXED_VALUE%>' <c:if test="${v.surchargeWay eq '固定值'}">selected</c:if>><%=Contants.FIXED_VALUE%></option>
								                	</select>
								                </td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center"><input type="text" name="surchargeAmount" value="${v.surchargeAmount}" onchange="getPerSum(this)" t_value="" o_value="" onkeyup="checkIsANum(this)">&nbsp;</td>
								                <td nowrap="nowrap" class="RptTableBodyCell" align="center"><input type="text" name="amount_money" t_value="" o_value="" onchange="getsum()" value="${v.amountMoney}" onkeyup="checkIsANum(this)">&nbsp;</td>
								                <script type="text/javascript">
													document.getElementById("department").value="${v.department}";
												</script>
											</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
							</table>
						</td>
					</tr>
					<tr id="tr2" style="display: none;">
						<td class="head_left" align="right">材料总金额：</td>
						<td class="head_form1" align="left" colspan="3"><input type="text" id="amountMoney"  name="amountMoney" value="${bean.amountMoney }"></td>
					</tr>
					
					<!-- 
					<tr>
     					<th align="left" colspan="4" class="head_one">验收记录：</th>
     				</tr>
     				<tr>
						<td class="head_left" align="right">记录：</td>
						<td class="head_form1" colspan="3"><textarea rows="3" style="width: 80%" name="checkRecord" id="checkRecord">${bean.checkRecord }</textarea></td>
					</tr>
					<tr>
						<td class="head_left" align="right">住户意见：</td>
						<td class="head_form1" colspan="3">&nbsp;&nbsp;&nbsp;<input type="radio" name="clientNotion" value="非常满意">非常满意&nbsp;&nbsp;&nbsp;<input type="radio" name="clientNotion" value="满意">满意&nbsp;&nbsp;&nbsp;<input type="radio" name="clientNotion" value="不满意">不满意</td>
					</tr>
     				 -->
					<tr>
     					<td align="center" colspan="4" class="head_left"><input type="button" value="提交" class="button" onclick="finish()"><input type="button" value="返回" class="button" onclick="return1()"></td>
     				</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
<script type="text/javascript">
	sel(document.getElementById("select11"));
	getsum();
	
	/*初始化派工人员和手机号码start*/
	var sendedManNames = "";
	var phones="";
	<c:if test="${!empty dispatchList}">
		<c:forEach items="${dispatchList}" var="v">
			<c:if test="${v.status eq '已派遣'}">
			sendingPersonnel += "${v.hrPersonnel.personnelId},";
			sendedManNames+="${v.hrPersonnel.personnelName }(${v.hrPersonnel.personnelCode });";
			$("#phones").append("<span>&nbsp;${v.hrPersonnel.personnelName }(${v.hrPersonnel.personnelCode })：<input type='hidden' name='personnelName' id='personnelName' value='${v.hrPersonnel.personnelName }' /></span><input type='text' name='personnelphone' value='${v.hrPersonnel.phone }' /><br />");
			</c:if>
		</c:forEach>
	</c:if>
	if(sendingPersonnel!=""){
		sendingPersonnel = sendingPersonnel.substring(0,sendingPersonnel.length-1);
	}
	if(otherPersonnel!=""){
		otherPersonnel = otherPersonnel.substring(0,otherPersonnel.length-1);
	}
	document.getElementById("oldSendingMan").value=sendingPersonnel;
	document.getElementById("newSendingMan").value=sendingPersonnel;
	//初始化派工人员
	document.getElementById("sendedManName").value=sendedManNames;
	/*初始化派工人员和手机号码end*/
</script>
</html>
