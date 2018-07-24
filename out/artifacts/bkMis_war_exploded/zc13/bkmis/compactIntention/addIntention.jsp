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
	<title>添加意向书</title>
	
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
		var allIdArr = new Array("compactIntention.clientId","compactIntention.name","compactIntention.unitName","compactIntention.code","compactIntention.linkMan",
							  "compactIntention.phone","compactIntention.clientType","compactIntention.companyType","compactIntention.fax",
							  "compactIntention.trade","compactIntention.fundType","compactIntention.operation","compactIntention.corporation",
							  "compactIntention.loginFund","compactIntention.loginDate","compactIntention.isHighTech","compactIntention.taxNo","compactIntention.rentNo",
							  "compactIntention.country","compactIntention.nation","compactIntention.residence","compactIntention.identityCard");
		//下拉框的客户信息
		var selectIdArr = new Array("compactIntention.clientType","compactIntention.companyType","compactIntention.trade","compactIntention.fundType","compactIntention.isHighTech");
		//输入框的客户信息
		var inputIdArr = new Array("compactIntention.clientId","compactIntention.name","compactIntention.unitName","compactIntention.code","compactIntention.linkMan",
							  "compactIntention.phone","compactIntention.fax","compactIntention.operation","compactIntention.corporation",
							  "compactIntention.loginFund","compactIntention.loginDate","compactIntention.taxNo","compactIntention.rentNo","compactIntention.country",
							  "compactIntention.nation","compactIntention.residence","compactIntention.identityCard");
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
		    			var dd = document.getElementById("compactIntention.beginDate");
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
		    		var dd = document.getElementById("compactIntention.endDate");
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
		
		//增加房间
		function addRoom(){
			var lpId = document.getElementById("compactIntention.lpId").value;
			var url = "<%=path%>/getRoom.do\?method=getMTree";
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
					    document.getElementById("compactIntention.roomCodes").value += arr1[1] + ";";
					    document.getElementById("compactIntention.roomCodes2").value += arr1[1] + ";";
					    var list = document.getElementsByName("area");
					    var total = 0;
					    for(var m=0;m<list.length;m++){
					    	total = total + parseFloat(trim(list[m].value));
					    }
					    total = parseFloat(total).toFixed(2).toString();
					    document.getElementById("compactIntention.totalArea").value = total;
					    document.getElementById("compactIntention.totalArea2").value = total;
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
		
		//删除房间或收费标准
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
					document.getElementById("compactIntention.roomCodes").value = codes.substr(0,codes.length-1);
					document.getElementById("compactIntention.roomCodes2").value = codes.substr(0,codes.length-1);
					var total = 0;
					    for(var m=0;m<list.length;m++){
					    	total = total + parseFloat(list[m].value);
					   	}
					   	total = parseFloat(total).toFixed(2).toString();
					document.getElementById("compactIntention.totalArea").value = total;
					document.getElementById("compactIntention.totalArea2").value = total;
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
			var ids = ""; //房间id(以;分割)
			for(var i=0;i<roomIds.length;i++){
				ids += roomIds[i].value + ";";
			}
			ids = ids.substr(0,ids.length-1);
			var bb = document.getElementById("compactIntention.beginDate");
    		var beginDate = "";//意向书开始日期
    		if(bb!=null){
    			beginDate = bb.value;
    		}
    		var ee = document.getElementById("compactIntention.endDate");
    		var endDate = "";//意向书结束日期
    		if(ee!=null){
    			endDate = ee.value;
    		}
			var url = "customer.do?method=addRoomCost&ids="+ids+"&beginDate="+beginDate+"&endDate="+endDate;
			var options = "dialogWidth:700px;dialogHeight:400px;status:no;scroll:no;";
			//var win = window.showModalDialog(url,this.window, options);
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
		
		//删除一行租金信息
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
		            document.getElementById("compactIntention.circle").value=""; 
		        }    
         	}
		}
		
		//选择客户
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
						document.getElementById("compactIntention.clientId").value = data.id;
						document.getElementById("compactIntention.unitName").value = data.unitName;
						document.getElementById("compactIntention.linkMan").value = data.linkMan;
						document.getElementById("compactIntention.phone").value = data.phone;
						document.getElementById("compactIntention.clientType").value = data.clientType;
						document.getElementById("compactIntention.country").value = data.country;
						document.getElementById("compactIntention.nation").value = data.nation;
						document.getElementById("compactIntention.residence").value = data.residence;
						document.getElementById("compactIntention.identityCard").value = data.identityCard;
						document.getElementById("compactIntention.companyType").value = data.companyType;
						document.getElementById("compactIntention.fax").value = data.fax;
						document.getElementById("compactIntention.trade").value = data.trade;
						document.getElementById("compactIntention.fundType").value = data.fundType;
						document.getElementById("compactIntention.operation").value = data.operation;
						document.getElementById("compactIntention.corporation").value = data.corporation;
						document.getElementById("compactIntention.taxNo").value = data.taxNo;
						document.getElementById("compactIntention.rentNo").value = data.rentNo;
						document.getElementById("compactIntention.code").value = data.code;
						document.getElementById("compactIntention.code2").value = data.code;
						document.getElementById("compactIntention.loginFund").value = data.loginFund;
						document.getElementById("compactIntention.loginDate").value = data.loginDate;
						document.getElementById("compactIntention.isHighTech").value = data.isHighTech;
						//影藏显示个人或公司信息
						seltype(document.getElementById("compactIntention.clientType"));
					}
				});
				for(var i=0;i<obj.options.length;i++){
					if(obj.options[i].selected){
						document.getElementById("compactIntention.name").value = obj.options[i].text;
					}
				}
			}
			
		}
		
		//返回
		function back(){
			document.form1.action = "<%=path%>/intention.do?method=getIntentionList";
			document.form1.submit();
		}
		
		//获取客户代码
		function getCusCode(){
			$.post("<%=path%>/customer.do",{method:"getClientCode"},function(data){
				document.getElementById("compactIntention.code").value = data;
			})
		}
		
		//保存
		function save(){
			if(document.getElementById("compactIntention.name").value==""){
				alert("客户名称不能为空");
				document.getElementById("compactIntention.name").focus();
				return;
			}
			if(document.getElementById("compactIntention.unitName").value==""){
				alert("单位全称不能为空");
				document.getElementById("compactIntention.unitName").focus();
				return;
			}
			selectType1();
			document.form1.action = "<%=path%>/intention.do?method=saveIntention";
			document.form1.submit();
		}
		
		//选择住户类别
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
			var obj = document.getElementById("compactIntention.clientType");
			if(obj.value=="单位"){
				document.getElementById("compactIntention.country").value="";
				document.getElementById("compactIntention.nation").value="";
				document.getElementById("compactIntention.residence").value="";
				document.getElementById("compactIntention.identityCard").value="";
			}
			if(obj.value=="个人"){
				document.getElementById("compactIntention.companyType").value="";
				document.getElementById("compactIntention.fax").value="";
				document.getElementById("compactIntention.trade").value="";
				document.getElementById("compactIntention.fundType").value="";
				document.getElementById("compactIntention.operation").value="";
				document.getElementById("compactIntention.corporation").value="";
				document.getElementById("compactIntention.loginFund").value="";
				document.getElementById("compactIntention.loginDate").value="";
				document.getElementById("compactIntention.isHighTech").value="";
				document.getElementById("compactIntention.taxNo").value="";
			}
		}
		
		//清空客户信息
		function clearClient(){
			for(var i = 0;i<inputIdArr.length;i++){
					setValue4DomById(inputIdArr[i],"value","");
					document.getElementById("compactIntention.code").value = clientCode;
					document.getElementById("compactIntention.code2").value = clientCode;
			}
			for(var i = 0;i<selectIdArr.length;i++){
					setValue4DomById(selectIdArr[i],"value",document.getElementById(selectIdArr[i]).options[0].value);
			}
			//isModify();
		}
		
		//判断客户信息是否可以编辑
		function isModify(){
			var clientId = document.getElementById("compactIntention.clientId").value;
			//如果客户id不为空，则不允许编辑客户信息
			if(clientId!=null&&clientId!=""&&clientId!="null"){
				for(var i = 0;i<allIdArr.length;i++){
					setValue4DomById(allIdArr[i],"readOnly",true);
				}
				selectReadOnly("clientTypeSpan");
				selectReadOnly("companyTypeSpan");
				selectReadOnly("tradeSpan");
				selectReadOnly("fundTypeSpan");
				selectReadOnly("isHighTechSpan");
			}else{
				for(var i = 0;i<allIdArr.length;i++){
					setValue4DomById(allIdArr[i],"readOnly",false);
				}
				cancelSelectReadOnly("clientTypeSpan");
				cancelSelectReadOnly("companyTypeSpan");
				cancelSelectReadOnly("tradeSpan");
				cancelSelectReadOnly("fundTypeSpan");
				cancelSelectReadOnly("isHighTechSpan");
			}
		}
		
		//根据给定的id的值获取dom对象，并根据指定的值设置指定的属性
		function setValue4DomById(id,property,value){
			var obj = document.getElementById(id);
			obj.setAttribute(property,value);
		}
		
		//设置下拉框为只读
		//需要在select外层套一层:<span id=""></span>
		//传递的参数selectedId便是span的id的值
		function selectReadOnly(selectedId){
	  		var obj = document.getElementById(selectedId);
	     	obj.onmouseover = function(){
	    		obj.setCapture();
	    	}
	    	obj.onmouseout = function(){
	     		obj.releaseCapture();
	    	}
	    	obj.onfocus = function(){
	     		obj.blur();
	    	}
	    	obj.onbeforeactivate = function(){
	     		return false;
	    	}
 		}
 		
 		//取消下拉框只读
 		function cancelSelectReadOnly(selectedId){
 			var obj = document.getElementById(selectedId);
	     	obj.onmouseover = function(){
	    	}
	    	obj.onmouseout = function(){
	    	}
	    	obj.onfocus = function(){
	    	}
	    	obj.onbeforeactivate = function(){
	    	}
 		}
	</script>
</head>
<body onload="getCusCode();">
<form name = "form1" method="post">
	<input type="hidden" name="my_Rtoken" value="${my_Rtoken }" />
	<!-- 返回到列表页面需要的参数start -->
	<input type="hidden" name="c_clientName" value="${intentionForm.c_clientName }" />
	<input type="hidden" name="c_roomCode" value="${intentionForm.c_roomCode }" />
	<input type="hidden" name="c_status" value="${intentionForm.c_status }" />
	<input type="hidden" name="c_isEarnest" value="${intentionForm.c_isEarnest }" />
	<!-- 返回到列表页面需要的参数end -->
	<input type="hidden" name="compactIntention.isConvertCompact" value="0" />
	<input type="hidden" id="compactIntention.lpId" name="compactIntention.lpId" value="${intentionForm.lpId }" />
	
	<table width="99%" height = "96%" align="center" border="0" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5">&nbsp;</td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">意向书</td>
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
							<input type="hidden" id="compactIntention.clientId" name="compactIntention.clientId" />
							<select name="name1" id="name1" onchange="selectClient(this)" style="width:220px;position:absolute;clip:rect(2 100% 90% 201)">
								<option value="">&nbsp;</option>
							<c:choose>
								<c:when test="${empty list}">
								</c:when>
								<c:otherwise>
									<c:forEach items="${list}" var="v">
										<option value="${v.id }">${v.name }</option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							</select>
							<input name="compactIntention.name" id="compactIntention.name" type="text" style="font-size:12px;width:200px">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="清空" class="button" onclick="clearClient();" />
						</td>
						<td class="head_form1" align="right">单位全称：</td>
						<td class="head_form1"><input type="text" id="compactIntention.unitName" name="compactIntention.unitName"><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font></td>
					</tr>
     				<tr>
						<td class="head_left" align="right" width="10%">客户代码：</td>
						<td class="head_form1">
							<input type="text"  disabled id="compactIntention.code2" value="${clientCode }" /><font color="red" size="3">&nbsp;&nbsp;&nbsp;*</font>
							<input type="hidden" name="compactIntention.code" readonly id="compactIntention.code" value="${clientCode }" />
						</td>
						<td class="head_form1" align="right" width="10%">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
					</tr>
     				<tr>
						<td class="head_left" align="right" width="10%">联系人：</td>
						<td class="head_form1"><input type="text" name="compactIntention.linkMan" id="compactIntention.linkMan"></td>
						<td class="head_form1" align="right" width="10%">联系电话：</td>
						<td class="head_form1"><input type="text" name="compactIntention.phone" id="compactIntention.phone"></td>
					</tr>
					<tr>
						<td class="head_left" align="right">住户类别：</td>
						<td class="head_form1">
							<span id="clientTypeSpan">
							<select onchange="seltype(this)" name="compactIntention.clientType">
								<option value="单位" selected="selected">单位</option>
								<option value="个人">个人</option>
							</select>
							</span>
						</td>
						<td class="head_form1" align="right" width="10%">&nbsp;</td>
						<td class="head_form1">&nbsp;</td>
					</tr>
					<tr id="tr11" style="display:none">
						<td colspan="4">
							<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
								<tr>
			     					<th align="left" colspan="4" class="head_one">个人信息</th>
			     				</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">国籍：</td>
									<td class="head_form1" width="40%"><input type="text" name="compactIntention.country" id="compactIntention.country"></td>
									<td class="head_form1" align="right" width="10%">&nbsp;</td>
									<td class="head_form1">&nbsp;</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">民族：</td>
									<td class="head_form1" width="40%"><input type="text" name="compactIntention.nation" id="compactIntention.nation"></td>
									<td class="head_form1" align="right" width="10%">&nbsp;</td>
									<td class="head_form1">&nbsp;</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">籍贯：</td>
									<td class="head_form1" width="40%"><input type="text" name="compactIntention.residence" id="compactIntention.residence"></td>
									<td class="head_form1" align="right" width="10%">&nbsp;</td>
									<td class="head_form1">&nbsp;</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">身份证号：</td>
									<td class="head_form1" width="40%"><input type="text" name="compactIntention.identityCard" id="compactIntention.identityCard"></td>
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
									<span id="companyTypeSpan">
									<select name="compactIntention.companyType" id="compactIntention.companyType" style="width: 130">
										<c:choose>
										<c:when test="${empty map.enterpriseType}">
										</c:when>
										<c:otherwise>
										<c:forEach items="${map.enterpriseType}" var="v">
											<option value="${v.codeName }">${v.codeName }</option>
										</c:forEach>
									</c:otherwise>
									</c:choose>
									</select>
									</span>
									</td>
									<td class="head_form1" align="right" width="10%">传真：</td>
									<td class="head_form1" width="40%"><input type="text" name="compactIntention.fax" id="compactIntention.fax"></td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">所属行业：</td>
									<td class="head_form1" width="40%">
									<span id="tradeSpan">
									<select name="compactIntention.trade" id="compactIntention.trade" style="width: 130">
										<c:choose>
										<c:when test="${empty map.tradeType}">
										</c:when>
										<c:otherwise>
										<c:forEach items="${map.tradeType}" var="v">
											<option value="${v.codeName }">${v.codeName }</option>
										</c:forEach>
									</c:otherwise>
									</c:choose>
									</select>
									</span>
									</td>
									<td class="head_form1" align="right" width="10%">资金类别：</td>
									<td class="head_form1">
										<span id="fundTypeSpan">
										<select name="compactIntention.fundType" id="compactIntention.fundType" style="width: 130">
										<c:choose>
										<c:when test="${empty map.fundType}">
										</c:when>
										<c:otherwise>
										<c:forEach items="${map.fundType}" var="v">
											<option value="${v.codeName }">${v.codeName }</option>
										</c:forEach>
										</c:otherwise>
										</c:choose>
										</select>
										</span>
									</td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">主营业务：</td>
									<td class="head_form1" width="40%"><input type="text" name="compactIntention.operation" id="compactIntention.operation"></td>
									<td class="head_form1" align="right" width="10%">法人代表：</td>
									<td class="head_form1"><input type="text" name="compactIntention.corporation" id="compactIntention.corporation"></td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%">注册资金：</td>
									<td class="head_form1" width="40%"><input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" name="compactIntention.loginFund" id="compactIntention.loginFund"></td>
									<td class="head_form1" align="right" width="10%">成立时间：</td>
									<td class="head_form1"><input type="text" name="compactIntention.loginDate" id="compactIntention.loginDate" readonly onclick="WdatePicker();" class="Wdate"></td>
								</tr>
								<tr>
									<td class="head_left" align="right" width="10%">国税号：</td>
									<td class="head_form1" width="40%"><input type="text" name="compactIntention.taxNo" id="compactIntention.taxNo"></td>
									<td class="head_form1" align="right" width="10%">地税号：</td>
									<td class="head_form1"><input type="text" name="compactIntention.rentNo" id="compactIntention.rentNo"></td>
								</tr>
			     				<tr>
									<td class="head_left" align="right" width="10%" nowrap="nowrap">是否高新技术企业：</td>
									<td class="head_form1" width="40%" colspan="3">
										<span id="isHighTechSpan">
										<select name="compactIntention.isHighTech" id="compactIntention.isHighTech">
											<option value="是" selected="selected">是</option>
											<option value="否">否</option>
										</select>
										</span>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
		     			<th align="left" colspan="4" class="head_one">意向书信息</th>
		     		</tr>
		     		<tr>
						<td class="head_left" align="right" width="10%" >意向书编号：</td>
						<td class="head_form1" width="40%" colspan="3">
							<input type="text"  disabled  value="${intentionCode }">
							<input type="hidden" name="compactIntention.intentionCode" disabled id="compactIntention.intentionCode" value="${intentionCode }">
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
								<tr>
								    <td align="center" colspan="5"><font color="red">请选择房间!</font></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">房间号：</td>
						<td class="head_form1" width="40%">
							<input type="text"  id="compactIntention.roomCodes2" disabled="disabled" />
							<input type="hidden" name="compactIntention.roomCodes" id="compactIntention.roomCodes" readonly="readonly" />
						</td>
						<td class="head_form1" align="right" width="10%">总面积：</td>
						<td class="head_form1">
							<input type="text"  id="compactIntention.totalArea2" disabled="disabled"/>
							<input type="hidden" name="compactIntention.totalArea" id="compactIntention.totalArea" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">签订日期：</td>
						<td class="head_form1" width="40%" colspan="3"><input type="text" name="compactIntention.signDate"  readonly onclick="WdatePicker();" value="<%=date %>" class="Wdate"></td>
						
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">租赁开始日期：</td>
						<td class="head_form1" width="40%"><input type="text" name="compactIntention.beginDate" id="compactIntention.beginDate"  readonly onclick="WdatePicker();" class="Wdate"></td>
						<td class="head_form1" align="right" width="10%">租赁结束日期：</td>
						<td class="head_form1"><input type="text" name="compactIntention.endDate"  id="compactIntention.endDate"  readonly onclick="WdatePicker();" class="Wdate"></td>
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
							</table>
						</td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">定金：</td>
						<td class="head_form1" width="40%"><input type="text" name="compactIntention.earnest" id="compactIntention.earnest"><font color="red" size="3">&nbsp;&nbsp;&nbsp;</font></td>
						<td class="head_left" align="right" width="10%">装修押金：</td>
						<td class="head_form1" width="40%"><input type="text" name="compactIntention.decorationDeposit" id="compactIntention.decorationDeposit"><font color="red" size="3">&nbsp;&nbsp;&nbsp;</font></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">房租押金：</td>
						<td class="head_form1" width="40%"><input type="text" name="compactIntention.rentDeposit" id="compactIntention.rentDeposit"><font color="red" size="3">&nbsp;&nbsp;&nbsp;</font></td>
						<td class="head_form1" align="right" width="10%">预收款周期：</td>
						<td class="head_form1" width="40%">
						<input type="text" name="compactIntention.circle" id="compactIntention.circle" size="5" onchange="mustNaN(value)">&nbsp;&nbsp;月</td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">经办人：</td>
						<td class="head_form1" width="40%"><input type="text" name="compactIntention.man" id="compactIntention.man"></td>
						<td class="head_form1" align="right" width="10%">经办日期：</td>
						<td class="head_form1"><input type="text" name="compactIntention.date" readonly onclick="WdatePicker();" value="<%=date %>" class="Wdate"></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="10%">附加说明：</td>
						<td class="head_form1" width="40%" colspan="3"><textarea rows="3" style="width: 80%" name="compactIntention.instruction" id="compactIntention.instruction"></textarea></td>
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
							<tr>
								<td align="center" colspan="8"><font color="red">请选择收费标准!</font></td>
							</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="head_left"  colspan="4"  height="15px"></td>
					</tr>
					<tr>
     					<td align="center" colspan="4">
     					<input type="button" value="确定" class="button" onclick="save()">
     					<input type="button" value="取消" class="button" onclick="back()"></td>
     				</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
