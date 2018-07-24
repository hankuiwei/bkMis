<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>客户信息</title>
	
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
		function cancal(){
			this.close();
		}
		function selectBy(obj){
			if(obj.value=="byUser"){
				document.getElementById("td11").innerHTML = "&nbsp;";
				document.getElementById("rooms").style.display = "none";
				document.getElementById("rooms").value = "";
			}else{
				document.getElementById("td11").innerText = "房间：";
				document.getElementById("rooms").style.display = "block";
				document.getElementById("rooms").value = document.getElementById("rooms").options[0].value;
			}
			selectCost();
		}
		function save(){
			var flag = "flag";
			var roomId = document.getElementById("rooms").value;
			var costStand = document.getElementById("costStand").value;
			var costtype = document.getElementById("costtype").value;
			//var rebate = document.getElementById("rebate").value;
			var beginDate = document.getElementById("beginDate").value;
			var endDate = document.getElementById("endDate").value;
			var amount = document.getElementById("amount").value;
			if(costStand==""){
				alert("请选择收费标准");
				return;
			}
			var roomIds = "";//房间id
			if(trim(roomId)!=""&&roomId!=null){
				roomIds = roomId.split("+")[1];
			}
			//returnValue = flag+","+cost+","+costtype+","+costStand+","+rebate+","+beginDate+","+endDate;
			//window.returnValue = flag+","+cost+","+costtype+","+costStand+","+beginDate+","+endDate;
			window.returnValue = {"flag":flag,"roomIds":roomIds,"costtype":costtype,"costStand":costStand,"beginDate":beginDate,"endDate":endDate,"amount":amount};
			this.close();
		}
		function selectCost(){
			var lpId1 = document.getElementById("rooms").value;
			var lpId = lpId1.split("+")[0];
			var costType = document.getElementById("costtype").value;
			var costStand = document.getElementById("costStand");
			var str = document.getElementById("hidden").value;
			var selectType = document.getElementById("selectType").value;
			var type = "";
			if(selectType=="byUser"){
				type = "person";
			}
			if(selectType=="byRoom"){
				type = "room";
			}
			var lpIds = document.getElementById("hidden1").value;
			var lpIds1 = lpIds.substr(0,lpIds.length-1);
			$.post("<%=path%>/customer.do",{method:"costStandList",lpId:lpId,costType:costType,str:str,lpIds:lpIds1,type:type},function(data){
				costStand.options.length = 0;
				var option = new Option("请选择","");
				costStand.add(option);
				var arr3 = data.split(";");
				for(var i=0;i<arr3.length;i++){
					var arr1 = arr3[i].split(",");
					var option = new Option(arr1[0], arr1[1]);
					costStand.add(option);
				}
			});		
		}
	</script>
</head>
<body>
<form name = "form1">
	<table width="99%" height = "96%" border="0" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"><input type="hidden" id="hidden" value="${string }"><input type="hidden" id="hidden1" value="${lpIds }"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line" onclick="">费用信息</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr height = "95%"><!-- 这个td负责画正式内容的左、右、下方的边框 -->
    		<td class="menu_tab2" valign ="top">
     			<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
     				<tr>
     					<th align="left" colspan="4" class="head_one">添加费用类型</th>
     				</tr>
     				<tr>
						<td class="head_left" align="right" width="15%">选择标准：</td>
						<td class="head_form1" id="td1"><select id="selectType" style="width: 130" onchange="selectBy(this)"><option value="byUser">按用户</option><option value="byRoom" selected="selected">按房间</option></select></td>
						<td class="head_form1" align="right" width="15%" id="td11">房间：</td>
						<td class="head_form1" width="35%">
						<select id="rooms" name="rooms" style="width: 130" onchange="selectCost()">
							<c:choose>
								<c:when test="${string}">
									<option value="${set }+${ids }">全部</option>
								</c:when>
							</c:choose>
							<c:choose>
								<c:when test="${empty roomList}">
								</c:when>
								<c:otherwise>
									<c:forEach items="${roomList}" var="v">
										<option value="${v.EBuilds.ELp.lpId }+${v.roomId }">${v.roomCode }</option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</select>
						</td>
					</tr>
     				<tr>
						<td class="head_left" align="right" width="15%">收费类型：</td>
						<td class="head_form1" width="35%">
						<select id="costtype" name="costtype" style="width: 130" onchange="selectCost()">
							<c:choose>
								<c:when test="${empty costList}">
								</c:when>
								<c:otherwise>
									<c:forEach items="${costList}" var="v" varStatus="tag">
										<option value="${v.id }">${v.costTypeName }</option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</select>
						</td>
						<td class="head_form1" align="right" width="15%">收费标准：</td>
						<td class="head_form1" id="td1"><select id="costStand" name="costStand" style="width: 160">
							<option value="">请选择</option>
						</select></td>
					</tr>
     				<tr>
						<td class="head_left" align="right" width="15%">开始日期：</td>
						<td class="head_form1" id="td1" width="35%"><input type="text" name="beginDate"  readonly onclick="WdatePicker();" value="${beginDate }" class="Wdate"></td>
						<td class="head_left" align="right" width="15%">结束日期：</td>
						<td class="head_form1" id="td1" width="35%"><input type="text" name="endDate"  readonly onclick="WdatePicker();" value="${endDate }" class="Wdate"></td>
					</tr>
					<tr>
						<td class="head_left" align="right" width="15%">数量：</td>
						<td class="head_form1" id="td1" width="35%" colspan="3"><input type="text" name="amount" value="1" onkeyup="this.value=this.value.replace(/\D/g,'')" /></td>
					</tr>
					<tr>
						<td class="head_left" align="left" width="15%" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;<font style="font-size:12px;color:red;">注：收费标准的开始和结束日期必须在合同日期之内，超出的部分无效！</font></td>
					</tr>
     				<!--<tr> 
						<td class="head_left" align="right" width="15%">折扣：</td>
						<td class="head_form1" id="td1" colspan = "3"><input type="text" name="rebate" id="rebate"></td>
					</tr> -->
     				<tr>
						<td colspan="4" align="center">
							<input type="button" class="button" value="添加" onclick="save()">
							<input type="button" class="button" value="取消" onclick="cancal()">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
	<script type="text/javascript">
		selectCost();
	</script>
</html>
