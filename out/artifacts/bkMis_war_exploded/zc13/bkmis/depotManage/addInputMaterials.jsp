<%@ page language="java"   pageEncoding="UTF-8"%>
<%@page import="javax.xml.bind.annotation.XmlElementDecl.GLOBAL"%>
<%@page import="com.zc13.util.GlobalMethod"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
	<title>添加入库材料信息</title>
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/jquery.form.js"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/validate.js"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
	<script type="text/javascript">
		/**
		 * 判断是否是一个数字（只能输入+-号，小数点，数字）
		 * 输入框的格式为：<input type="text" t_value="" o_value="" onkeyup="checkIsANum(this)" >
		 * @param {} obj
		 */
		function checkIsANum(obj){
			if(!obj.value.match(/^[\+\-]?\d*?\.?\d*?$/)){
				obj.value=obj.t_value;
			}
			else{
				obj.t_value=obj.value;
			}
			if(obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/)){
				obj.o_value=obj.value;
			}
		}
	
		//统计材料总金额
		function getsum(){
			var sum = 0;
			var amount_money = document.getElementsByName("amountMoney");
			for(var i = 0;i<amount_money.length;i++){
				sum += parseFloat(amount_money[i].value);
			}
			//保留到分
			document.getElementById("money").value = parseFloat(sum).toFixed(2).toString();
		}
		
		//计算单种材料的总金额
		function getPerSum(obj){
			var rowindex = obj.parentNode.parentNode.rowIndex;
			var unitPrice = document.getElementsByName("unitPrice")[rowindex-1].value;
			var amount = document.getElementsByName("amount")[rowindex-1].value;
			var amount_money = 0;
			amount_money = parseFloat(parseFloat(amount)*parseFloat(unitPrice)).toFixed(2).toString();
			document.getElementsByName("amountMoney")[rowindex-1].value = amount_money;
			getsum();
		}
	
		function Addchoose(){
			var s;
			s = window.showModalDialog("<%=path%>/inputdepot.do?method=chooseMaterials",window,"dialogWidth=1000px;dialogHeight=500px;resizable=yes;center=1");
			var tb = document.getElementById("tbl");
			var tr = tb.getElementsByTagName("tr");
			if(tr.length-1>0){
				var idArr = s.split(",");
				var chkx = document.getElementsByName("childBox");
				var existId ='';
				var flag = '';
				for(var i = 0;i<chkx.length;i++){
					existId += chkx[i].value + ',';
				}
				existId = existId.slice(0,existId.length-1);
				var existIdArr = existId.split(",");
				for(var m = 0;m<idArr.length;m++){
					for(var n = 0;n<existIdArr.length;n++){
						if(idArr[m] == existIdArr[n]){
							flag = '0';
							alert("您已经选择了该项，请重新选择!");
							return ;
						}
					}
				}
			}
			$("#idNum").attr("value",s);
			if(flag != '0'){
				$.post("<%=path%>/inputdepot.do?",{method:"selectChooseMaterials",chooseIds:s},function(data){
					//获取表头的html
					var oldHtml = $("#tbl").html();
					$("#tbl").html(oldHtml + data);
					//给详单赋单据编号
					var inOutputCode = document.getElementsByName("inOutputCode");
					for(var i = 0;i<inOutputCode.length;i++){
						inOutputCode[i].value = document.getElementById("code").value;
					}
					getsum();
				},"html")
			}
		}

		//复选框全选事件
		function selectedAll(){
			var parentBox = document.getElementById("parentBox");
			var childBox = document.getElementsByName("childBox");
			if(parentBox.checked == true){
				for(var i = 0;i<childBox.length;i++){
					childBox[i].checked = true;
				}
			}else{
				for(var i = 0;i<childBox.length;i++){
					childBox[i].checked = false;
				}
			}
		}
		//删除选择的材料信息
		function delMaterial(){
			if(!window.confirm("您确定要删除吗?")) {return ;}
			var itemIds = '';
			var tb = document.getElementById("tbl");
			var tr = document.getElementsByName("tr1")
			var chkx = document.getElementsByName("childBox");
			for(var i = chkx.length -1;i>= 0;i--){
				if(chkx[i].checked == true){
					tb.deleteRow(i+1);
				}
			}
			getsum();
		}
		//提交添加信息
		function upInput(){
			var tb = document.getElementById("tbl");
			var tr = tb.getElementsByTagName("tr");
			getsum();
			var x = Validator.Validate(document.getElementById('form1'),2);
			if(!x){
				return;
			}
			if(tr.length-1<=0){
				alert("请选择要添加的入库材料信息!");
				return;
			}
			$("#form1").ajaxSubmit({success: function(data){window.close();}});
		}
	</script>
</head>
<body>
<form action="inputmanage.do?method=doAddIutput" id="form1" method="post" name="form1">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">添加材料入库单信息</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr height = "95%">
    		<td class="menu_tab2" align="center" valign="top">
     			<table width="100%" height = "100%"  border="0" cellspacing="0" cellpadding="0">
	 				<tr>
						<td  align="center">
							<!-- 查询条件start -->
		  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
					            	<td height="10"></td>
					         	</tr>
					         	<tr>
									<td align="left" class="head_one" colspan="4">入库单</td>
								</tr>
					          	<tr>
					            	<td align="right" class="head_left">采购人员：</td>
					            	<td class="head_form1">&nbsp;
					            		<input type="text" id="man" name="man" dataType="Require" msg="采购人员不得为空！" /><font color="red">*</font>
					            	</td>
					            	<td align="right" class="head_form1">供应商：</td>
					            	<td class="head_form1">&nbsp;
					            		<select id="supplierId" name="supplierId">
					            			<c:forEach items="${supplierList}" var="sl">
					            				<option value="${sl[1] }">
					            					${sl[1] }
					            				</option>
					            			</c:forEach>
					            		</select>
					            	</td>
								</tr>
								<tr>
					            	<td align="right" class="head_left">入库单编号：</td>
					            	<td class="head_form1">&nbsp;
					            		<input type="text" id="code" name="code" value ="${num }" readonly="readonly">
					            	</td>
					            	<td align="right" class="head_form1">入库时间：</td>
					            	<td class="head_form1">&nbsp;
					            		<input type="text" name="date" id="date" style=" width: 138"  dataType="Require" msg="入库时间不得为空！" readonly onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<%=GlobalMethod.getTime3() %>" class="Wdate"/>
					            	</td>
								</tr>
								<tr>
					            	<td align="right" class="head_left">发票编号：</td>
					            	<td class="head_form1" colspan="3">&nbsp;
					            		<input type="text" id="invoiceCode" name="invoiceCode">
					            	</td>
								</tr>
							 	 <tr>
					           		 <td height="10" colspan="2"></td>
					          	 </tr>
					        </table>
					        <!-- 查询条件end -->
		 			 	</td>
					</tr>
  					<tr height="95%">
					    <td valign = "top">
					    	<!-- 表单内容区域 -->
							<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
								<tr>
					              	<td align="left" class="head_one">材料明细</td>
					            </tr>
					            <tr>
					                <td nowrap="nowrap" align="left">
					                	<input class="button" type="button" onclick="Addchoose();" value="添加">
					               		<input type="button" class="button" value="删除" onclick="delMaterial()">
					               	</td>
					            </tr>
					            
								<tr height = "95%">
					        		<td width="100%">
					        		<div id = "div1" class = "RptDiv"  >
							   			<table class = "RptTable" border="0" cellpadding="0" cellspacing="0" id="tbl">
					              			<tr>
								                <th class = "RptTableHeadCellFullLock"><input type="checkbox" name="parentBox" id="parentBox" onclick="selectedAll();"></th>
												<th class = "RptTableHeadCellFullLock">材料编号</th>
												<th class = "RptTableHeadCellLock">材料名称</th>
												<th class = "RptTableHeadCellLock">规格</th>
												<th class = "RptTableHeadCellLock">单位</th>
												<th class = "RptTableHeadCellLock">单价</th>
												<th class = "RptTableHeadCellLock">入库数量</th>
												<th class = "RptTableHeadCellLock">库存金额</th>
											</tr>
					             		</table>
					             	</div>
									</td>
		     		 			</tr>
								<tr>
								  <td>&nbsp;</td>
								</tr>
								<tr>
									<td>总金额：
										<input type="text" name="money" id="money" readonly="readonly"/>
										<input type="hidden" name="oldMoney" id="oldMoney"/>
									</td>
								</tr>
								<tr>
									<td align="center">
										<input type="button" name="up" value="提交" class="button" onclick="upInput()"/>
										<input type="button" name="retur" value="返回" class="button" onclick="javascript:window.close()"/>
									</td>
								</tr>
								<!-- 分页start -->
								<tr>
									<td colspan="1">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr><td class="form_line3">&nbsp;</td>
												<td class="form_line3" colspan="8">${pagination }</td>
												<td class="form_line3">&nbsp;</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
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
