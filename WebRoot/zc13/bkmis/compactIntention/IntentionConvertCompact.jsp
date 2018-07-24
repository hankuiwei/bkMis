<%@ page language="java"   pageEncoding="UTF-8"%>
<%@page import="com.zc13.util.GlobalMethod"%>
<%@page import="com.zc13.util.Contants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String date = GlobalMethod.getTime();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>添加预入驻</title>
	
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
	<script type="text/javascript">
		//将客户代码保存，用于在清空时赋初值
		var clientCode = "${clientCode}";
	
		//所有的客户信息id
		var allIdArr = new Array("clientId","name","unitName","code","linkMan",
							  "phone","clientType","companyType","fax",
							  "trade","fundType","operation","corporation",
							  "loginFund","loginDate","isHighTech","taxNo",
							  "country","nation","residence","identityCard");
		//下拉框的客户信息
		var selectIdArr = new Array("clientType","companyType","trade","fundType","isHighTech");
		//输入框的客户信息
		var inputIdArr = new Array("clientId","name","unitName","code","linkMan",
							  "phone","fax","operation","corporation",
							  "loginFund","loginDate","taxNo","rentNo","country",
							  "nation","residence","identityCard");
	</script>
	<script type="text/javascript">
		var key = 1;
		function submitNextDay(s) {//下一天          
			d   =   new   Date(Date.parse(s.replace(/-/g,   '/')));       
			d.setDate(d.getDate()+1);   
			var month = (d.getMonth()+1)+'';
			if(month.length == 1){
				month = '0'+month;
			} 
			var date = d.getDate()+'';
			if(date.length == 1){
				date = '0'+date;
			} 
			t   =   [d.getYear(),   month,   date];       
			return t.join('-');     
		}
		//增加一行新的房租
		function addcost(){
			var oTable=document.getElementById("cost");  
			var td;
		    var oTr1 = oTable.insertRow(oTable.rows.length); 
		    for(var i=0;i<5;i++){
		    	if(i==0){
		    		td = oTr1.insertCell(i);
		    		td.nowrap="nowrap"; 
		    		td.align="center";
		    		td.className = "RptTableBodyCellLock";
		    		td.innerHTML = oTable.rows.length-1;
		    	}else if(i==3){
		    		td = oTr1.insertCell(i);
			    	td.width="30%"; 
			    	td.className = "RptTableBodyCell";
		    		td.nowrap="nowrap"; 
		    		td.align="center";
		    		td.innerHTML = "<input type=\"text\" name=\"rent\">";
		    	}else if(i==4){
			    	td = oTr1.insertCell(i);
			    	td.width="15%"; 
			    	td.nowrap="nowrap"; 
			    	td.className = "RptTableBodyCell";
			    	td.align="center";
			    	td.innerHTML = "<input type=\"button\" class=\"button\" value=\"删除\" onclick=\"del(this)\">";
		    	}else if(i==1){
		    		
		    		if(key==1){//如果这是第一行房租，那么开始日期直接取合同的开始日期
		    			var dd = document.getElementById("beginDate");
			    		var beginDate = "";
			    		if(dd!=null){
			    			beginDate = dd.value;
			    		}
		    		}else{//否则的话就要取上一行的结束日期作为开始日期
		    			var k2 = key-1;
		    			var dd = document.getElementById("end1"+k2);
			    		var beginDate = "";
			    		if(dd!=null){
			    			beginDate = submitNextDay(dd.value);
			    		}
		    		}
			    	td = oTr1.insertCell(i);
			    	td.width="30%"; 
			    	td.className = "RptTableBodyCell";
			    	td.nowrap="nowrap"; 
			    	td.align="center";
			    	td.innerHTML = "<input type=\"text\"  name=\"beginDateCost\" value=\""+beginDate+"\" readonly onclick=\"WdatePicker();\" class=\"Wdate\">";
		    	}else{
		    		var dd = document.getElementById("endDate");
		    		var endDate = "";
		    		if(dd!=null){
		    			endDate = dd.value;
		    		}
			    	td = oTr1.insertCell(i);
			    	td.width="30%"; 
			    	td.className = "RptTableBodyCell";
			    	td.nowrap="nowrap"; 
			    	td.align="center";
			    	td.innerHTML = "<input type=\"text\" id = \"end1"+key+"\" name=\"endDateCost\" value=\""+endDate+"\" readonly onclick=\"WdatePicker();\" class=\"Wdate\">";
		    	}
		    }
		    key ++;
		} 
		function addRoom(){
			var lpId = document.getElementById("lpId").value;
			var url = "<%=path%>/getRoom.do\?method=getMTree";;
			var options = "dialogWidth:1000px;dialogHeight:600px;status:no;scroll:no;";
			var win = window.showModalDialog(url,this.window, options);
			if(win!=null){
				var arr = win.split(",");
				var oTable = document.getElementById("tb");
				var roomids = document.getElementsByName("roomid1");
				var roomcodes = document.getElementsByName("roomCode");
				for(var j=0;j<arr.length;j++){
					var flag = true;
					for(var m=0;m<roomids.length;m++){
						if(trim(arr[j])== trim(roomids[m].value)){
							alert("房间号为"+roomcodes[m].value+"已经存在");
							flag = false;
						}
					}
					if(flag){
				    	$.post("<%=path%>/customer.do",{method:"getRoomById",roomId:arr[j]},function(data){
						var td;
					    var oTr1 = oTable.insertRow(oTable.rows.length); 
					    oTr1.onmouseover ="this.className = 'hover';";
				    	var arr1 = data.split(",");
					    for(var i=0;i<5;i++){
					    	if(i==0){
					    		td = oTr1.insertCell(i);
					    		td.nowrap="nowrap"; 
					    		td.align="center";
			    				td.className = "RptTableBodyCellLock";
					    		td.innerHTML = "<input type=\"checkbox\" name=\"roomId1\" value=\""+arr1[0]+"\"><input type=\"hidden\" name=\"clientRoomId\" value=\""+arr1[0]+"\">";
					    	}else if(i==1){
					    		td = oTr1.insertCell(i);
					    		td.nowrap="nowrap"; 
					    		td.align="center";
				    			td.className = "RptTableBodyCell";
					    		td.innerText = oTable.rows.length-2;
					    	}else if(i==2){
					    		td = oTr1.insertCell(i);
					    		td.nowrap="nowrap"; 
					    		td.align="center";
				    			td.className = "RptTableBodyCell";
					    		td.innerHTML = arr1[1] + "&nbsp;" + "<input type=\"hidden\" name=\"roomCode\" value=\""+arr1[1]+"\">";
					    	}else if(i==3){
					    		td = oTr1.insertCell(i);
					    		td.nowrap="nowrap"; 
					    		td.align="center";
				    			td.className = "RptTableBodyCell";
					    		td.innerHTML = arr1[2] + "&nbsp;" + "<input type=\"hidden\" name=\"roomFullName\" value=\""+arr1[2]+"\">";
					    	}else{
					    		td = oTr1.insertCell(i);
					    		td.nowrap="nowrap"; 
					    		td.align="center";
				    			td.className = "RptTableBodyCell";
					    		td.innerHTML = arr1[3] + "&nbsp;" + "<input type=\"hidden\" name=\"area\" value=\""+arr1[3]+"\">";
					    	}
					    }
					    document.getElementById("roomCodes").value += arr1[1] + ";";
					    document.getElementById("roomCodes2").value += arr1[1] + ";";
					    var list = document.getElementsByName("area");
					    var total = 0;
					    for(var m=0;m<list.length;m++){
					    	total = total + parseFloat(trim(list[m].value));
					    }
					    total = parseFloat(total).toFixed(2).toString();
					    document.getElementById("totalArea").value = total;
					    document.getElementById("totalArea2").value = total;
						var trs = oTable.getElementsByTagName("tr");
						for(var i=0;i<trs.length;i++){
							tds = trs[i].getElementsByTagName("td");
							if(tds.length==1&&tds[0].innerText=="请选择房间!"){
								oTable.deleteRow(trs[i].rowIndex);
							}
						}
						trs = oTable.getElementsByTagName("tr");
						for(var i=2;i<trs.length;i++){
							trs[i].getElementsByTagName("td")[1].innerText = trs[i].rowIndex-1;
						}
					});
					}		
				}
			}
		} 
		function del1(str,str1,str2){
			var tb = document.getElementById(str);
			var radios = document.getElementsByName(str1);
			var k = 0;
			for(var j=radios.length-1;j>=0;j--){
				if(radios[j].checked){
					k++;
				} 
			}
			if(k==0){
				alert("请选择要删除的记录");
				return;
			}
			if(window.confirm("确定删除"+k+"条记录吗")){
				for(var j=radios.length-1;j>=0;j--){
					if(radios[j].checked){
						tb.deleteRow(j + 2);
					} 
				}
				var tr = tb.getElementsByTagName("tr");
				var codes = "";
				for(var i=2;i<tr.length;i++){
					tr[i].getElementsByTagName("td")[1].innerText = tr[i].rowIndex-1;
					if(str=="tb"){
						codes += tr[i].getElementsByTagName("td")[2].innerText + ";";
					}
				}
				
				if(str=="tb"){
					var list = document.getElementsByName("area");
					document.getElementById("roomCodes").value = codes.substr(0,codes.length-1);
					document.getElementById("roomCodes2").value = codes.substr(0,codes.length-1);
					var total = 0;
					    for(var m=0;m<list.length;m++){
					    	total = total + parseFloat(list[m].value);
					   	}
					   	total = parseFloat(total).toFixed(2).toString();
					document.getElementById("totalArea").value = total;
					document.getElementById("totalArea2").value = total;
				}
			}
			var trs = tb.getElementsByTagName("tr");
			if(trs.length==2){
				var tr = tb.insertRow(2);
				var td = tr.insertCell();
				td.align = "center";
				td.colSpan = str2;
				if(str2=="5"){
					td.innerHTML = "<font color=\"red\">请选择房间!</font>";
				}else{
					td.innerHTML = "<font color=\"red\">请选择收费标准!</font>";
				}
			}
			
		}
		//添加收费标准
		function addRoomCost(){
			var roomIds = document.getElementsByName("roomId1");
			if(roomIds.length<1){
				alert("请选择房间");
				return;
			}
			var ids = ""; 
			for(var i=0;i<roomIds.length;i++){
				ids += roomIds[i].value + ";";
			}
			ids = ids.substr(0,ids.length-1);
			var bb = document.getElementById("beginDate");
    		var beginDate = "";
    		if(bb!=null){
    			beginDate = bb.value;
    		}
    		var ee = document.getElementById("endDate");
    		var endDate = "";
    		if(ee!=null){
    			endDate = ee.value;
    		}
			var url = "customer.do?method=addRoomCost&ids="+ids+"&beginDate="+beginDate+"&endDate="+endDate;
			var options = "dialogWidth:700px;dialogHeight:400px;status:no;scroll:no;";
			var returnVal = window.showModalDialog(url,this.window, options);
			if(returnVal==null){
				return;
			}
			//var arr = win.split(",");
			//var arrIds = arr[1].split(";");
			var roomIds = returnVal.roomIds.split(";");
			if(returnVal.flag=="flag"){
				for(var n=0;n<roomIds.length;n++){
			    	$.post("<%=path%>/customer.do",{method:"getNames",roomId:roomIds[n],costtype:returnVal.costtype,costStand:returnVal.costStand},function(data){
						var oTable=document.getElementById("tb1");  
						var td;
					    var oTr1 = oTable.insertRow(oTable.rows.length); 
			    		var arr1 = data.split(",");

					    td = oTr1.insertCell(0);
					    td.className = "RptTableBodyCellLock";
					    td.nowrap="nowrap"; 
					    td.align="center";
					    td.innerHTML = "<input type=\"checkbox\" name=\"check3\">";
	
						td = oTr1.insertCell(1);
						td.nowrap="nowrap"; 
						td.className = "RptTableBodyCell";
						td.align="center";
						td.innerText = oTable.rows.length-2;
							   
						td = oTr1.insertCell(2);
						td.className = "RptTableBodyCell";
						td.nowrap="nowrap"; 
						td.align="center";
						td.innerHTML = arr1[0] + "&nbsp;" + "<input type=\"hidden\" name=\"roomId\" value=\""+arr1[3]+"\">";
							  
						td = oTr1.insertCell(3);
						td.nowrap="nowrap"; 
						td.className = "RptTableBodyCell";
						td.align="center";
						td.innerHTML = arr1[1] + "&nbsp;" + "<input type=\"hidden\" name=\"costTypeId\" value=\""+returnVal.costtype+"\">";
							    
						td = oTr1.insertCell(4);
						td.nowrap="nowrap"; 
						td.align="center";
						td.className = "RptTableBodyCell";
						td.innerHTML = arr1[2] + "&nbsp;" + "<input type=\"hidden\" name=\"costStandartId\" value=\""+returnVal.costStand+"\">";
							    
						td = oTr1.insertCell(5);
						td.nowrap="nowrap"; 
						td.align="center";
						td.className = "RptTableBodyCell";
						td.innerHTML = returnVal.beginDate + "&nbsp;" + "<input type=\"hidden\" name=\"beginDateStand\" value=\""+returnVal.beginDate+"\">";
							    
						td = oTr1.insertCell(6);
						td.nowrap="nowrap"; 
						td.align="center";
						td.className = "RptTableBodyCell";
						td.innerHTML = returnVal.endDate + "&nbsp;" + "<input type=\"hidden\" name=\"endDateStand\" value=\""+returnVal.endDate+"\">";
				       
				        td = oTr1.insertCell(7);
						td.nowrap="nowrap"; 
						td.align="center";
						td.className = "RptTableBodyCell";
						td.innerHTML = returnVal.amount + "&nbsp;" + "<input type=\"hidden\" name=\"amount\" value=\""+returnVal.amount+"\">";
				        
				        var trs = oTable.getElementsByTagName("tr");
						for(var i=0;i<trs.length;i++){
							tds = trs[i].getElementsByTagName("td");
							if(tds.length==1&&tds[0].innerText=="请选择收费标准!"){
								oTable.deleteRow(trs[i].rowIndex);
							}
						}
						trs = oTable.getElementsByTagName("tr");
						for(var i=2;i<trs.length;i++){
							trs[i].getElementsByTagName("td")[1].innerText = trs[i].rowIndex-1;
						}
					})
				}
			}
		}
		function del(obj){
			var tr = obj.parentElement.parentElement;
			var tb = document.getElementById("cost");
			if(window.confirm("确定删除吗？")){
				tb.deleteRow(tr.rowIndex);
				var trs = tb.getElementsByTagName("tr");
				for(var i=1;i<trs.length;i++){
					trs[i].getElementsByTagName("td")[0].innerText = trs[i].rowIndex;
				}
			}
		}
		function mustNaN(str){
			if(trim(str).length!=0){    
		        reg=/^[-+]?\d*$/;    
		        if(!reg.test(trim(str))){    
		            alert("对不起，预收款周期必须为整数!");
		            document.getElementById("circle").value=""; 
		        }    
         	}
		}
		function selectClient(obj){
			var id = obj.value;
			if(id==null||id==""){
				clearClient();
			}else{
				$.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/customer.do?method=getClientString",
					data:{id:id},
					dataType:"json",
					success:function(data){
						document.getElementById("clientId").value = data.id;
						document.getElementById("unitName").value = data.unitName;
						document.getElementById("linkMan").value = data.linkMan;
						document.getElementById("phone").value = data.phone;
						document.getElementById("clientType").value = data.clientType;
						document.getElementById("country").value = data.country;
						document.getElementById("nation").value = data.nation;
						document.getElementById("residence").value = data.residence;
						document.getElementById("identityCard").value = data.identityCard;
						document.getElementById("companyType").value = data.companyType;
						document.getElementById("fax").value = data.fax;
						document.getElementById("trade").value = data.trade;
						document.getElementById("fundType").value = data.fundType;
						document.getElementById("operation").value = data.operation;
						document.getElementById("corporation").value = data.corporation;
						document.getElementById("taxNo").value = data.taxNo;
						document.getElementById("rentNo").value = data.rentNo;
						document.getElementById("code").value = data.code;
						document.getElementById("code2").value = data.code;
						document.getElementById("loginFund").value = data.loginFund;
						document.getElementById("loginDate").value = data.loginDate;
						document.getElementById("isHighTech").value = data.isHighTech;
						//影藏显示个人或公司信息
						seltype(document.getElementById("clientType"));
					}
				});
				for(var i=0;i<obj.options.length;i++){
					if(obj.options[i].selected){
						document.getElementById("name").value = obj.options[i].text;
					}
				}
			}
		}
		function return1(){
			history.back();
		}
		function getCusCode(){
			$.post("<%=path%>/customer.do",{method:"getClientCode"},function(data){
				document.getElementById("code").value = data;
				document.getElementById("code2").value = data;
			})
		}
		function save(){
			if(document.getElementById("name").value==""){
				alert("客户名称不能为空");
				return;
			}
			if(document.getElementById("unitName").value==""){
				alert("单位全称不能为空");
				return;
			}
			if(document.getElementById("linkMan").value==""){
				alert("联系人不能为空");
				return;
			}
			if(document.getElementById("phone").value==""){
				alert("联系电话不能为空");
				return;
			}
			selectType1();
			document.form1.action = "<%=path%>/destine.do?method=saveDestineCompact";
			document.form1.submit();
		}
		function seltype(obj){
			if(obj.value=="单位"){
				document.getElementById("tr11").style.display="none";
				document.getElementById("tr12").style.display="block";
			}
			if(obj.value=="个人"){
				document.getElementById("tr11").style.display="block";
				document.getElementById("tr12").style.display="none";
			}
		}
		function selectType1(){
			var obj = document.getElementById("clientType");
			if(obj.value=="单位"){
				document.getElementById("country").value="";
				document.getElementById("nation").value="";
				document.getElementById("residence").value="";
				document.getElementById("identityCard").value="";
			}
			if(obj.value=="个人"){
				document.getElementById("companyType").value="";
				document.getElementById("fax").value="";
				document.getElementById("trade").value="";
				document.getElementById("fundType").value="";
				document.getElementById("operation").value="";
				document.getElementById("corporation").value="";
				document.getElementById("loginFund").value="";
				document.getElementById("loginDate").value="";
				document.getElementById("isHighTech").value="";
				document.getElementById("taxNo").value="";
			}
		}
		
		//清空客户信息
		function clearClient(){
			for(var i = 0;i<inputIdArr.length;i++){
					setValue4DomById(inputIdArr[i],"value","");
					document.getElementById("code").value = clientCode;
					document.getElementById("code2").value = clientCode;
			}
			for(var i = 0;i<selectIdArr.length;i++){
					setValue4DomById(selectIdArr[i],"value",document.getElementById(selectIdArr[i]).options[0].value);
			}
			//isModify();
		}
		
		//根据给定的id的值获取dom对象，并根据指定的值设置指定的属性
		function setValue4DomById(id,property,value){
			var obj = document.getElementById(id);
			obj.setAttribute(property,value);
		}
	</script>
</head>
<body onload="">
<form name = "form1" method="post">
	<input type="hidden" name="my_Rtoken" value="${my_Rtoken }">
	
	<input type="hidden" id="lpId" name="lpId" value="${intentionForm.compactIntention.lpId }">
	<input type="hidden" id="intentionId" name="intentionId" value="${intentionForm.compactIntention.id }">
	<input type="hidden" id="isEarnest" name="isEarnest" value="${intentionForm.compactIntention.isEarnest }">
	<table width="99%" height = "96%" align="center" border="0" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5">&nbsp;</td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line" onclick="jiben()">客户从意向书入驻</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr height = "95%"><!-- 这个td负责画正式内容的左、右、下方的边框 -->
    		<td class="menu_tab2">
     			<table width="95%" border="0" align="center" cellspacing="0" cellpadding="0">
     				<tr>
     					<th align="left" colspan="4" class="head_one">客户基本信息</th>
     				</tr>
					<tr>
						<td class="head_left" align="right" width="10%">客户简称：</td>
						<td class="head_form1" width="40%">
							<input type="hidden" id="clientId" name="clientId" value="${intentionForm.compactIntention.clientId }">
							<select name="name1" id="name1" onchange="selectClient(this)" style="width:220px;position:absolute;clip:rect(2 100% 90% 201)">
								<option value="">&nbsp;</option>
							<c:choose>
								<c:when test="${empty list}">
								</c:when>
								<c:otherwise>
									<c:forEach items="${list}" var="v">
										<option value="${v.id }" <c:if test="${intentionForm.compactIntention.clientId eq v.id }">selected</c:if> >${v.name }</option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							</select>
							<input name="name" id="name" type="text" value="${intentionForm.compactIntention.name }" style="font-size:12px;width:200px">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="清空" class="button" onclick="clearClient();" />
						</td>
						<td class="head_form1" align="right">单位全称：</td>
						<td class="head_form1"><input type="text" id="unitName" name="unitName" value="${intentionForm.compactIntention.unitName }"><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font></td>
					</tr>
     				<tr>
						<td class="head_left" align="right" width="10%">客户代码：</td>
						<td class="head_form1">
							<input type="hidden" name="code" readonly="readonly" id="code" value="${intentionForm.compactIntention.code }">
							<input type="text" name="code2" disabled id="code2" value="${intentionForm.compactIntention.code }"><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font>
						</td>
						<td class="head_form1" align="right" width="10%">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
					</tr>
     				<tr>
						<td class="head_left" align="right" width="10%">联系人：</td>
						<td class="head_form1"><input type="text" name="linkMan" id="linkMan" value="${intentionForm.compactIntention.linkMan }"><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font></td>
						<td class="head_form1" align="right" width="10%">联系电话：</td>
						<td class="head_form1"><input type="text" name="phone" id="phone" value="${intentionForm.compactIntention.phone }"><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font></td>
					</tr>
					<tr>
						<td class="head_left" align="right">住户类别：</td>
						<td class="head_form1">
							<select onchange="seltype(this)" name="clientType">
								<option value="单位" <c:if test="${intentionForm.compactIntention.clientType eq '单位'}">selected</c:if>>单位</option>
								<option value="个人" <c:if test="${intentionForm.compactIntention.clientType eq '个人'}">selected</c:if>>个人</option>
							</select>
						</td>
						<td class="head_form1" align="right" width="10%">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
					</tr>
					<tr id="tr11" style="display:none ">
						<td colspan="4">
							<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
								<tr>
			     					<th align="left" colspan="4" class="head_one">个人信息</th>
			     				</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">国籍：</td>
									<td class="head_form1" width="40%"><input type="text" name="country" id="country" value="${intentionForm.compactIntention.country }"></td>
									<td class="head_form1" align="right" width="10%">&nbsp;</td>
									<td class="head_form1">&nbsp;</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">民族：</td>
									<td class="head_form1" width="40%"><input type="text" name="nation" id="nation" value="${intentionForm.compactIntention.nation }"></td>
									<td class="head_form1" align="right" width="10%">&nbsp;</td>
									<td class="head_form1">&nbsp;</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">籍贯：</td>
									<td class="head_form1" width="40%"><input type="text" name="residence" id="residence" value="${intentionForm.compactIntention.residence }"></td>
									<td class="head_form1" align="right" width="10%">&nbsp;</td>
									<td class="head_form1">&nbsp;</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">身份证号：</td>
									<td class="head_form1" width="40%"><input type="text" name="identityCard" id="identityCard" value="${intentionForm.compactIntention.identityCard }"></td>
									<td class="head_form1" align="right" width="10%">&nbsp;</td>
									<td class="head_form1">&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr id="tr12">
						<td colspan="4">
							<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
								<tr>
			     					<th align="left" colspan="4" class="head_one">公司信息</th>
			     				</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">公司类别：</td>
									<td class="head_form1" width="40%">
									<select name="companyType" id="companyType" style="width: 130">
										<c:choose>
										<c:when test="${empty map.enterpriseType}">
										</c:when>
										<c:otherwise>
										<c:forEach items="${map.enterpriseType}" var="v">
											<option value="${v.codeName }" <c:if test="${intentionForm.compactIntention.companyType eq v.codeName }">selected</c:if> >${v.codeName }</option>
										</c:forEach>
									</c:otherwise>
									</c:choose>
									</select>
									</td>
									<td class="head_form1" align="right" width="10%">传真：</td>
									<td class="head_form1" width="40%"><input type="text" name="fax" id="fax" value="${intentionForm.compactIntention.fax }"></td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">所属行业：</td>
									<td class="head_form1" width="40%">
									<select name="trade" id="trade" style="width: 130">
										<c:choose>
										<c:when test="${empty map.tradeType}">
										</c:when>
										<c:otherwise>
										<c:forEach items="${map.tradeType}" var="v">
											<option value="${v.codeName }" <c:if test="${intentionForm.compactIntention.trade eq v.codeName }">selected</c:if>>${v.codeName }</option>
										</c:forEach>
									</c:otherwise>
									</c:choose>
									</select>
									</td>
									<td class="head_form1" align="right" width="10%">资金类别：</td>
									<td class="head_form1">
										<select name="fundType" id="fundType" style="width: 130">
										<c:choose>
										<c:when test="${empty map.fundType}">
										</c:when>
										<c:otherwise>
										<c:forEach items="${map.fundType}" var="v">
											<option value="${v.codeName }" <c:if test="${intentionForm.compactIntention.fundType eq v.codeName }">selected</c:if>>${v.codeName }</option>
										</c:forEach>
									</c:otherwise>
									</c:choose>
									</select>
									</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">主营业务：</td>
									<td class="head_form1" width="40%"><input type="text" name="operation" id="operation" value="${intentionForm.compactIntention.operation }"></td>
									<td class="head_form1" align="right" width="10%">法人代表：</td>
									<td class="head_form1"><input type="text" name="corporation" id="corporation" value="${intentionForm.compactIntention.corporation }"></td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">注册资金：</td>
									<td class="head_form1" width="40%"><input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" name="loginFund" id="loginFund" value="${intentionForm.compactIntention.loginFund }"></td>
									<td class="head_form1" align="right" width="10%">成立时间：</td>
									<td class="head_form1"><input type="text" name="loginDate" id="loginDate" readonly onclick="WdatePicker();" class="Wdate" value="${intentionForm.compactIntention.loginDate }"></td>
								</tr>
								<tr>
									<td class="head_left" align="right" width="10%">国税号：</td>
									<td class="head_form1" width="40%"><input type="text" name="taxNo" id="taxNo" value="${intentionForm.compactIntention.taxNo }"></td>
									<td class="head_form1" align="right" width="10%">地税号：</td>
									<td class="head_form1"><input type="text" name="rentNo" id="rentNo" value="${intentionForm.compactIntention.rentNo }"></td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%" nowrap="nowrap">是否高新技术企业：</td>
									<td class="head_form1" width="40%" colspan="3">
									<select name="isHighTech" id="isHighTech">
										<option value="是" <c:if test="${intentionForm.compactIntention.isHighTech eq '是' }">selected</c:if>>是</option>
										<option value="否" <c:if test="${intentionForm.compactIntention.isHighTech eq '否' }">selected</c:if>>否</option>
									</select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
		     			<th align="left" colspan="4" class="head_one">合同信息</th>
		     		</tr>
		     		<tr>
						<td class="head_left" align="right" width="10%" >合同编号：</td>
						<td class="head_form1" width="40%">
							<input type="text" disabled name="compactCode2" id="compactCode2" value="${compactCode }">
							<input type="hidden" name="compactCode" id="compactCode" value="${compactCode }">
						</td>
						<td class="head_form1" align="right" width="10%">合同类型：</td>
						<td class="head_form1">
							<select id="type" name="type">
								<option value="<%=Contants.COMPACT %>" selected="selected"><%=Contants.COMPACT %></option>
							</select>
						</td>
					</tr>
					<tr>
     					<th align="left" colspan="4" class="head_one">入住房间</th>
     				</tr>
					<tr id="tr1">
						<td class="head_left" colspan="4">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class = "RptTable" id="tb">
								<tr>
									<td colspan="5"><input type="button" class="button" value="添加" onclick="addRoom()">
									<input type="button" class="button" value="删除" onclick="del1('tb','roomId1','5')"></td>
								</tr>
								<tr>
									<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">&nbsp;</td>
									<td width="22%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</td>
									<td width="22%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</td>
								    <td width="22%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间名称</td>
								    <td width="22%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间面积</td>
								</tr>
								<c:choose>
								<c:when test="${empty intentionForm.roomList}">
									<tr>
								    <td align="center" colspan="5"><font color="red">请选择房间!</font></td>
								</tr>
								</c:when>
								<c:otherwise>
								<c:forEach items="${intentionForm.roomList}" var="v" varStatus="tag">
								<tr>
									<td nowrap="nowrap" class="RptTableBodyCellLock" align="center">
										<input type="checkbox" name="roomId1" value="${v.roomId }">
										<input type="hidden" name="clientRoomId" value="${v.roomId }">
									</td>
									<td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center">${tag.count }</td>
									<td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.roomCode }<input type="hidden" name="roomCode" value="${v.roomCode }">&nbsp;</td>
								    <td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.roomFullName }<input type="hidden" name="roomFullName" value="${v.roomFullName }">&nbsp;</td>
								    <td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center">${v.constructionArea }<input type="hidden" name="area" value="${v.constructionArea }">&nbsp;</td>
								</tr>
								</c:forEach>
								</c:otherwise>
								</c:choose>
							</table>
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">房间号：</td>
						<td class="head_form1" width="40%">
							<input type="text" name="roomCodes2" id="roomCodes2" value="${intentionForm.compactIntention.roomCodes }" disabled>
							<input type="hidden" name="roomCodes" id="roomCodes" value="${intentionForm.compactIntention.roomCodes }" >
						</td>
						<td class="head_form1" align="right" width="10%">总面积：</td>
						<td class="head_form1">
							<input type="text" name="totalArea2" id="totalArea2" value="${intentionForm.compactIntention.totalArea }" disabled>
							<input type="hidden" name="totalArea" id="totalArea" value="${intentionForm.compactIntention.totalArea }" >
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">签订日期：</td>
						<td class="head_form1" width="40%" colspan="3"><input type="text" name="signDate"  readonly onclick="WdatePicker();" value="<%=date %>" class="Wdate"></td>
						
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">租赁开始日期：</td>
						<td class="head_form1" width="40%"><input type="text" name="beginDate" id="beginDate"  readonly onclick="WdatePicker();" value="${intentionForm.compactIntention.beginDate }" class="Wdate"><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font></td>
						<td class="head_form1" align="right" width="10%">租赁结束日期：</td>
						<td class="head_form1"><input type="text" name="endDate"  id="endDate"  readonly onclick="WdatePicker();" value="${intentionForm.compactIntention.endDate }" class="Wdate"><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">租金自定义：</td>
						<td class="head_form1" colspan="3">
							<table width="100%" id="cost" cellpadding="0" cellspacing="0">
								<tr>
									<td class="head_form" align="center" width="10%">序号</td>
									<td class="head_form" align="center" width="30%">开始日期</td>
									<td class="head_form" align="center" width="30%">结束日期</td>
									<td class="head_form" align="center" width="30%">租金单价(元)</td>
									<td class="head_form" align="center" width="10%">
										<input type="button" class="button" value="添加" onclick="addcost()">
									</td>
								</tr>
								<c:choose>
								<c:when test="${empty intentionForm.rentList}">
								</c:when>
								<c:otherwise>
								<c:forEach items="${intentionForm.rentList}" var="v" varStatus="tag">
								<tr>
									<td nowrap="nowrap" class="RptTableBodyCellLock" align="center">${tag.count }</td>
									<td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center"><input type="text" name="beginDateCost" value="${v.beginDate }" readonly onclick="WdatePicker();" class="Wdate"></td>
									<td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center"><input type="text" id="end1${tag.count }" name="endDateCost" value="${v.endDate }" readonly onclick="WdatePicker();" class="Wdate"></td>
								    <td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center"><input type="text" name="rent" value="${v.rent }"></td>
								    <td width="22%" nowrap="nowrap" class="RptTableBodyCell" align="center"><input type="button" class="button" value="删除" onclick="del(this)"></td>
								</tr>
								</c:forEach>
								</c:otherwise>
								</c:choose>
							</table>
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">已缴定金：</td>
						<td class="head_form1" width="40%"><input type="text" name="earnest" id="earnest" readonly="readonly" value="${intentionForm.compactIntention.earnest }"><font color="red" size="3">&nbsp;&nbsp;&nbsp;</font></td>
						<td class="head_left" align="right" width="10%">装修押金：</td>
						<td class="head_form1" width="40%"><input type="text" name="decorationDeposit" id="decorationDeposit" value="${intentionForm.compactIntention.decorationDeposit }"></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">房租押金：</td>
						<td class="head_form1" width="40%"><input type="text" name="rentDeposit" id="rentDeposit" value="${intentionForm.compactIntention.rentDeposit }"><font color="red" size="3">&nbsp;&nbsp;&nbsp;</font></td>
						<td class="head_form1" align="right" width="10%">预收款周期：</td>
						<td class="head_form1" width="40%">
						<input type="text" name="circle" id="circle" size="5" onchange="mustNaN(value)" value="${intentionForm.compactIntention.circle }">&nbsp;&nbsp;月<font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">经办人：</td>
						<td class="head_form1" width="40%"><input type="text" name="man" id="man" value="${intentionForm.compactIntention.man }"></td>
						<td class="head_form1" align="right" width="10%">经办日期：</td>
						<td class="head_form1"><input type="text" name="date" readonly onclick="WdatePicker();" value="<%=date %>" class="Wdate"></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">合同附加说明：</td>
						<td class="head_form1" width="40%" colspan="3"><textarea rows="3" style="width: 80%" name="instruction" id="instruction"></textarea></td>
					</tr>
					<tr>
     					<th align="left" colspan="4" class="head_one">收费信息</th>
     				</tr>
     				<tr>
						<td class="head_left" align="right" colspan="4">
							<table width="60%" border="0" cellpadding="0" cellspacing="0" class = "RptTable" id="tb1">
							<tr>
								<td colspan="8">
									<input type="button" class="button" value="添加" onclick="addRoomCost()">
									<input type="button" class="button" value="删除" onclick="del1('tb1','check3','8')">
								</td>
							</tr>
							<tr>
								<td nowrap="nowrap" class="RptTableHeadCellLock" align="center">&nbsp;</td>
								<td width="5%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">序号</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">费用类型</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">收费标准</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">开始日期</td>
								<td width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">结束日期</td>
								<td width="15%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">数量</td>
							</tr>
							<c:choose>
							<c:when test="${empty intentionForm.standardList}">
							<tr>
								    <td align="center" colspan="5"><font color="red">请选择收费标准!</font></td>
							</tr>
							</c:when>
							<c:otherwise>
							<c:forEach items="${intentionForm.standardList}" var="v" varStatus="tag">
							<tr>
								<td nowrap="nowrap" class="RptTableBodyCellLock" align="center"><input type="checkbox" name="check3"></td>
								<td nowrap="nowrap" class="RptTableBodyCell" align="center">${tag.count }</td>
								<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.ERooms.roomCode }<input type="hidden" name="roomId" value="${v.ERooms.roomId }">&nbsp;</td>
								<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.CCosttype.costTypeName }<input type="hidden" name="costTypeId" value="${v.CCosttype.id}">&nbsp;</td>
								<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.CCoststandard.standardName }<input type="hidden" name="costStandartId" value="${v.CCoststandard.id }">&nbsp;</td>
								<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.beginDate }<input type="hidden" name="beginDateStand" value="${v.beginDate }">&nbsp;</td>
								<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.endDate }<input type="hidden" name="endDateStand" value="${v.beginDate }">&nbsp;</td>
								<td nowrap="nowrap" class="RptTableBodyCell" align="center">${v.amount }<input type="hidden" name="amount" value="${v.amount }">&nbsp;</td>
							</tr>
							</c:forEach>
							</c:otherwise>
							</c:choose>
							</table>
						</td>
					</tr>
					<tr>
						<td class="head_left"  colspan="4"  height="15px"></td>
					</tr>
					<tr>
     					<td align="center" colspan="4">
     					<input type="button" value="确定" class="button" onclick="save()">
     					<input type="button" value="取消" class="button" onclick="return1()"></td>
     				</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
<script type="text/javascript">
	seltype(document.getElementById("clientType"));
</script>
</html>
