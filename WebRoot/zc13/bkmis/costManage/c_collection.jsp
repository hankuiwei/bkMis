<%@ page language="java" import="java.util.*,com.zc13.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String isPrint = GlobalMethod.NullToSpace(request.getParameter("isPrint"));
String chargeId = GlobalMethod.NullToSpace(request.getParameter("chargeId"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>收款销账</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/VsValidator.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/transform.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<c:if test="${success}">
		<script type="text/javascript">
			alert("成功！");
		</script>
	</c:if>
	<%if(isPrint.equals("1")){ %>
		<script type="text/javascript">
			var URL = "<%=path%>/bill.do?method=printSj2&chargeId=<%=chargeId%>";
  			window.open(URL);
		</script>
	<%} %>
	<style type="text/css">
	<!--
	.Rpt1{
		width: 100%;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 10pt;
		border-top:4px #266898 double;
		border-left:1px #b2c2c9 solid;
	}
	-->
	</style>
	<script type="text/javascript">
		/**
		*v表示要转换的值 
		*e表示要保留的位数
		*/
		function round(v,e){ 
			var t=1; 
			for(;e>0;t*=10,e--); 
			for(;e<0;t/=10,e++); 
			return Math.round(v*t)/t; 
		}
		//查看通话记录
		function showCallInfo(billid) {
			formEdit.action = "<%=path%>/phoneCost.do?method=showCallInfo&billid="+billid;
			formEdit.submit();
		}
	</script>
  </head>
  <body>
  	<!-- 加载页面div -->
	<jsp:include page="/loading.jsp"></jsp:include>
	<!-- 加载页面div -->
  	<form action="" name="formEdit" method="post" id="formEdit">
    <table width="98%" cellpadding="0" cellspacing="0" align="center">
		<tr>
   			<td height="5"></td>
 		</tr>
 		<tr>
   			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     				<tr>
       				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
					<td width="100" nowrap="nowrap" class="form_line">收款销账</td>
					<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
					<td width="1080" class="form_line2"></td>
					<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
       			</tr>
   			</table>
   		</td>
 	  </tr>
	  <tr>
	  	<td><table width="100%" cellpadding="0" cellspacing="0" class="menu_tab2">
		  	<tr>
		  		<td align=""><table width="100%" cellpadding="0" cellspacing="0">
		  			<tr>
		  				<td colspan="4" style="padding-left: 16px;">
		  					客户名称：${client.name }<input type="hidden" name="clientId" value="${clientId }"><input type="hidden" name="clientName" value="${client.name}">
		  					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;房间号：
		  					<c:forEach items="${compacts}" var="v" varStatus="vs">
		  						${v.roomCodes }
		  					</c:forEach>
		  				</td>
		  			</tr>
		  			<tr>
		  				<td class="txt" nowrap="nowrap">
					       <img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					    </td>
		  				<td align="left" nowrap>收费项目：<select name="itemId">
		  					<option value="">请选择..</option>
		  					<c:forEach items="${itemList}" var="v">
								<option value="${v.id }" <c:if test="${v.id==itemId }">selected</c:if>>${v.itemName }</option>		  						
		  					</c:forEach>
		  				</select></td>
		  				<!--<td nowrap>房号：<input type="text" name="roomCode" value="${roomCode }"></td>-->
		  				<td width="30%">&nbsp;</td>
		  				<td align="right"><input type="button" value="查询" onclick="cx()" class="button">
		  					<input type="button" value="返回" onclick="query()" class="button"></td>
		  			</tr>
		  		</table></td>		
		  	</tr>
		  	<tr height="50%">
			    <td valign="top">
			    	<!-- 表单内容区域 -->
					<table width="100%" height="100%" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
						<tr height="95%">
			        		<td width="100%">
						  		<div id=div1 class="RptDiv"><table width="100%" cellpadding="0" cellspacing="0" class="RptTable">
							    	<tr>
							    		<th class="RptTableHeadCellFullLock"  width="6%">序号</th>
							    		<th class="RptTableHeadCellFullLock"  width="10%">费用类别</th>
							    		<th class="RptTableHeadCellFullLock"  width="6%">收取</th>
							    		<th class="RptTableHeadCellLock"  width="10%">房间号</th>
							    		<th class="RptTableHeadCellLock"  width="10%">申请单号</th>
							    		<th class="RptTableHeadCellLock"  width="10%">账单月</th>
							    		<th class="RptTableHeadCellLock"  width="10%">合同金额</th>
							    		<th class="RptTableHeadCellLock"  width="10%">滞纳金额</th>
							    		<th class="RptTableHeadCellLock"  width="10%">应收金额</th>
							    		<th class="RptTableHeadCellLock"  width="10%">已收金额</th>
							    		<th class="RptTableHeadCellLock"  width="10%">未收金额</th>
							    	</tr>
							    	<c:choose>
							    		<c:when test="${empty billList}">
							    			<tr align="center">
												<td colspan="12" align="center" class="head_form1">
													<font color="red">没有信息!</font>
												</td>
											</tr>
							    		</c:when>
							    		<c:otherwise>
							    			<c:forEach items="${billList}" var="v" varStatus="vs">
							    			<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
									    		<td class="RptTableBodyCellLock" align="center">&nbsp;${vs.count }</td>
									    		<td class="RptTableBodyCellLock">&nbsp;
									    			<c:choose>
									    				<c:when test="${!empty v.itemId}">
									    					<c:forEach items="${itemList}" var="a">
										    					<c:if test="${v.itemId==a.id }">
										    						<c:if test="${a.value=='phoneCost'}"><a href="#" onclick="showCallInfo('${v.id }')">${a.itemName}</a></c:if>
										    						<c:if test="${a.value!='phoneCost'}">${a.itemName}</c:if>
										    					</c:if>
										    				</c:forEach>
									    				</c:when>
										    			<c:otherwise>
										    				${v.standardName }
										    			</c:otherwise>	
									    			</c:choose>
									    		</td>
									    		<td class="RptTableBodyCellLock"  align="center"><input type="checkbox" name="checkbox" value="${v.id }"  onclick="compute('${vs.index }')"></td>
									    		<td class="RptTableBodyCell">&nbsp;${v.ERooms.roomCode }</td>
									    		<td class="RptTableBodyCell">&nbsp;${v.billCode }</td>
									    		<td class="RptTableBodyCell">&nbsp;${v.billDate }</td>
									    		<td class="RptTableBodyCell">&nbsp;${v.billsExpenses }</td>
									    		<td class="RptTableBodyCell">&nbsp;${v.delayingExpenses }</td>
									    		<td class="RptTableBodyCell">&nbsp;
									    		<script>
									    		document.write(round(${v.billsExpenses+v.delayingExpenses },2));
									    		</script>
									    		</td>
									    		<td class="RptTableBodyCell" id="je${vs.index }">&nbsp;${v.actuallyPaid }</td>
									    		<td class="RptTableBodyCell">&nbsp;
									    		<script>
									    		document.write(round(${v.billsExpenses+v.delayingExpenses-v.actuallyPaid },2));
									    		</script>
									    		</td>
									    		<input type="hidden" name="actuallyPaid" value="${v.actuallyPaid }" val="${v.actuallyPaid }">
									    		<input type="hidden" name="pay" value="${v.billsExpenses+v.delayingExpenses-v.actuallyPaid }">
									    		<input type="hidden" name="id" value="${v.id }">
									    		<input type="hidden" name="delayingExpenses" value="${v.delayingExpenses }">
									    	</tr>
									    	</c:forEach>
							    		</c:otherwise>
							    	</c:choose>
							    </table></div>
							</td>
		    			</tr>
		    		</table>
		    	</td>
		    </tr>
		    <tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr><td class="form_line3" style="text-align: left;">
						&nbsp;该客户需缴纳预收房租<script>document.write(round(${advancecost < 0 ? 0:advancecost},2));</script>
						，预收物业费<script>document.write(round(${advanceWuYFCost < 0 ? 0:advanceWuYFCost },2) );</script>
						，房租押金<script>document.write(round(${rentDepositcost<=0?0:rentDepositcost },2));</script>
						，装修押金<script>document.write(round(${decorationDepositcost<=0?0:decorationDepositcost },2));</script>
						，定金<script>document.write(round(${earnestcost<=0?0:earnestcost },2));</script></td>
							<td class="form_line3">&nbsp;</td>
							<td class="form_line3" style="text-align: right">&nbsp;<input type="button" class="button" value="全选" onclick="selectAll()">
								<input type="button" class="button" value="反选" onclick="reverse()">
								<input type="button" class="button" value="打印" onclick="printbill()"></td>
						</tr>
					</table>
				</td>
			</tr>
		    <tr>
		      <td><table width="100%"  cellpadding="0" cellspacing="0" class="">
		    	<tr>
					<td nowrap></td>
				</tr>
		    	<tr>
	    			<td><table width="100%" cellpadding="0" cellspacing="0" class="Rpt1" >
	    				<tr>
	    				 	<td align="right" width="10%" class="head_form1" nowrap>本次应付：</td>
	    				 	<td class="head_form1"><input type="text" name="charge.billAmount" readonly="readonly"></td>
	    				 	<td class="head_form1" align="right" width="10%" nowrap>暂存款余额：</td>
	    				 	<td class="head_form1"><input type="text" style="width:185px;" name="temporal.amount" value="${temporal.amount }" disabled="disabled"></td>
	    					<td class="head_form1" align="right" width="10%" nowrap>收费：</td>
	    				 	<td class="head_form1"><input type="text" name="amount" onchange="total();" dataType="Double2" msg="收费必须是数字！"></td>
	    				</tr>
	    				<tr>
	    					<td class="head_form1" align="right"  nowrap>收款日期：</td>
	    				 	<td class="head_form1" align=""  colspan=""><input type="text" name="bill.collectionDate" readonly onclick="WdatePicker();" class="Wdate" value="${today }" dataType="Require" msg="收款日期不得为空！"><font color="red"><b>*</b></font></td>
	    					<td class="head_form1" align="right"  nowrap>付款方式：</td>
	    				 	<td class="head_form1" align="">
	    				 		<select name="bill.paymentWay" onchange="selectPaymentWay();">
	    				 			<option value="0">现金</option>
	    				 			<option value="1">支票</option>
	    				 			<option value="2">转账</option>
	    				 			<option value="3">刷卡</option>
	    				 		</select>
	    				 		<span id="chequeDiv" style="display:none;">
	    				 		&nbsp;支票号：<input type="text" name="bill.chequeNo" id="bill.chequeNo"  style="width:80px;" value="" >
	    				 		</span>
	    				 	</td>
	    					<td class="head_form1" align="right"  nowrap>预收房租：</td>
	    				 	<td class="head_form1" colspan=""><input type="text" name="advance.amount" dataType="Double2" msg="预收房租必须是数字！"  onchange="total();"><!--当前金额：${advance.amount }--></td>
	    				</tr>
	    				<tr>
	    				 	<td class="head_form1" align="right"  nowrap>单据类型：</td>
	    				 	<td class="head_form1" align="">
	    				 		<select name="bill.billType" id="bill.billType" onchange="changeBillType()">
	    				 			<option value="0">收据</option>
	    				 			<option value="1">发票</option>
	    				 		</select>
	    				 	</td>
	    				 	<td class="head_form1" align="right"  nowrap>单据号：</td>
	    				 	<td class="head_form1" align=""><input type="text" name="bill.billNum" id="bill.billNum" style="width:185px;" dataType="Require" msg="单据号不得为空！" value="${billNum }" ><font color="red"><b>*</b></font></td>
	    					<td class="head_form1" align="right"  nowrap>房租押金：</td>
	    				 	<td class="head_form1" colspan=""><input type="text" name="rentDeposit.amount" dataType="Double2" msg="押金必须是数字！"  onchange="total();"><!--当前金额：${deposit.amount }--></td>
	    				</tr>
	    				<tr> 	
	    				 	<td align="right" class="head_form1" nowrap>本次实付：</td>
	    				 	<td class="head_form1" colspan=""><input type="text" name="charge.amount"  readonly></td>
	    				 	<td align="right" class="head_form1" nowrap>定金转入：</td>
	    				 	<td align=""  class="head_form1" nowrap>
		    				 	<input type="checkbox" name="earnestFlag" value="0" onclick="earnest(this);">房租
		    				 	<input type="checkbox" name="earnestFlag" value="1"  onclick="earnest(this);">房租押金
	    				 	</td>
	    					<td class="head_form1" align="right" nowrap>装修押金：</td>
	    				 	<td class="head_form1" colspan="">
	    				 		<input type="text" name="decorationDeposit.amount" dataType="Double2" msg="押金必须是数字！"  onchange="total();"><!--当前金额：${deposit.amount }-->
	    				 	</td>
	    				</tr>
	    				<tr> 	
	    				 	<td align="right" class="head_form1" nowrap>&nbsp;</td>
	    				 	<td class="head_form1" colspan="">&nbsp;</td>
	    				 	<td align="right" class="head_form1" nowrap>&nbsp;</td>
	    				 	<td align=""  class="head_form1" nowrap>&nbsp;</td>
	    					<td class="head_form1" align="right" nowrap>定金：</td>
	    				 	<td class="head_form1" colspan=""><input type="text" name="earnest.amount" dataType="Double2" msg="订金必须是数字！"  onchange="total();"></td>
	    				</tr>
	    				<tr> 	
	    				 	<td align="right" class="head_form1" nowrap>&nbsp;</td>
	    				 	<td class="head_form1" colspan="">&nbsp;</td>
	    				 	<td align="right" class="head_form1" nowrap>&nbsp;</td>
	    				 	<td align=""  class="head_form1" nowrap>&nbsp;</td>
	    					<td class="head_form1" align="right" nowrap>预收物业费：</td>
	    				 	<td class="head_form1" colspan=""><input type="text" name="advanceWuYF.amount" dataType="Double2" msg="物业费必须是数字！"  onchange="total();"></td>
	    				</tr>
	    			</table></td>
	    		</tr>
	    		<tr>
	    			<td><table width="100%" cellpadding="0" cellspacing="0" class="" >
						<tr><!-- currentWuYFJFAdvanceCost -->	
							<td nowrap>
							当前预收房租：（<script>document.write(round(${advance.amount },2));</script>）
							,当前预收物业费：（<script>document.write(round(${advanceWuYF.amount },2));</script>）
							,当前房租押金：（<script>document.write(round(${rentDeposit.amount },2));</script>）
							,当前装修押金：（<script>document.write(round(${decorationDeposit.amount },2));</script>）
							,当前定金：（<script>document.write(round(${earnest.amount },2));</script>）</td>
							<td  align="right" >
								<input type="button" class="button" value="收取" onclick="save()">
								<input type="button" class="button" value="回退" onclick="inverse('${clientId }','${userInfo.userid}')">
								<!-- 
								<input type="button" value="去退款" onclick="drawback()" class="button">
								 -->
							</td>
							<input type="hidden" name="charge.userId" value="${userInfo.userid}">
							<input type="hidden" name="bill.userId" value="${userInfo.userid}">
							<input type="hidden" name="earnestNum" value="${earnest.amount}">
							<!-- 是否打印标志 0:不打印；1：打印-->
							<input type="hidden" name="isPrint" id="isPrint" value="0">
						</tr>
	    			</table></td>
				</tr>
	    	  </table></td>
	    	</tr>
	    </table></td>
	    </tr>
	</table>
	</form>
  </body>
  <script type="text/javascript">
  	var billNum = "${billNum}";
  	//选择单据类型
  	function changeBillType(){
  		if(document.getElementById("bill.billType").value=="0"){
  			document.getElementById("bill.billNum").value=billNum;
  		}else{
  			document.getElementById("bill.billNum").value="";
  		}
  	}
  	
  	//选择付款类型
  	function selectPaymentWay(){
  		var paymentWay = document.getElementById("bill.paymentWay").value;
  		if(paymentWay=="1"){//如果选择的是支票，则需要输入支票号
  			$("#chequeDiv").css("display","inline-block");//此处不能用block,用block的话就会换行
  		}else{
  			$("#chequeDiv").css("display","none");
  		}
  	}
  	
  	//收取：计算费用
  	function compute(index){
  		var obj = document.getElementsByName("checkbox");//
  		var ele = document.getElementsByName("charge.billAmount")[0];//本次应付
  		var amount = document.getElementsByName("pay")[index].value;//未收金额
  		var yjje = document.getElementsByName("actuallyPaid")[index].val;//已收金额
  		if(obj[index].checked){
  			var actuallyPaid = (Number(amount)*10000+Number(yjje)*10000)/10000;
  			document.getElementsByName("actuallyPaid")[index].value = round(actuallyPaid,2);
  			ele.value = round((Number(ele.value)*10000 + Number(amount)*10000)/10000,2);
  		}else{
  			document.getElementsByName("actuallyPaid")[index].value = yjje;
  			ele.value = round((Number(ele.value)*10000-Number(amount)*10000)/10000,2);
  		}
  		// cal();//计算余额
  	}
  	//客户退款
  	function drawback(){
  		document.forms[0].action = "<%=path%>/bill.do?method=getRefundList";
  		document.forms[0].submit();
  	}
  	//收取
  	function save(){
  		//这是什么鬼?什么客户?
  		var clientId = document.getElementsByName("clientId")[0].value;
  		if(clientId==null||clientId.length==0){
  			alert("请先选择客户！");
  			return false;
  		}
  		var total = document.getElementsByName("charge.billAmount")[0].value;//应付
  		var temporal = document.getElementsByName("temporal.amount")[0].value;//暂存款
  		var amount = document.getElementsByName("amount")[0].value;//收费
  		if(Number(total)>(Number(temporal)+Number(amount))){//暂存款 + 收费 < 应付 
  			alert("暂存款不足于缴纳费用！");
  			return false;
  		}
  		var x = Validator.Validate(document.getElementById('formEdit'),2);
  		//其他选项为空,则不应提交请求.
		//收费 amount ,预收租金advance.amount,房租押金rentDeposit.amount,装修押金decorationDeposit.amount,定金earnest.amount,预收物业费advanceWuYF.amount
		var charge = document.getElementsByName("amount")[0].value;
		var advanceRent = document.getElementsByName("advance.amount")[0].value;
		var rentDeposit = document.getElementsByName("rentDeposit.amount")[0].value;
		var decorationDeposit = document.getElementsByName("decorationDeposit.amount")[0].value;
		var earnest = document.getElementsByName("earnest.amount")[0].value;
		var advanceWuYF = document.getElementsByName("advanceWuYF.amount")[0].value;
  		
  		if( (charge==null||charge.length==0||charge==0) 
  				&& (advanceRent==null||advanceRent.length==0||advanceRent==0) 
  				&& (rentDeposit==null||rentDeposit.length==0||rentDeposit==0) 
  				&& (decorationDeposit==null||decorationDeposit.length==0||decorationDeposit==0) 
  				&& (earnest==null||earnest.length==0||earnest==0) 
  				&& (advanceWuYF==null||advanceWuYF.length==0||advanceWuYF==0)){
  			alert("请选择或填写要缴纳的收费项！");
  			return false;
  		}
  		if(x){
  			var billType = document.getElementsByName("bill.billType")[0].value;
  			if(billType=='0' && confirm2("是否打印收据？")){
  				document.getElementById("isPrint").value="1";
  			}
  			setInterval("showLoadingDiv()",300);
			document.forms[0].action = "<%=path%>/bill.do?method=editBill";
			document.forms[0].submit();
		}
  	}
  	//全选
  	function selectAll(){
  		var obj = document.getElementsByName("checkbox");
  		var value = 0;
  		for(var i=0;i<obj.length;i++){
  			obj[i].checked=true;
  			var amount = document.getElementsByName("pay")[i].value;//应付金额
  			var yjje = document.getElementsByName("actuallyPaid")[i].val;//已缴金额初始值
  			var actuallyPaid = Number(amount)+Number(yjje);
  			//document.getElementById("je"+i).innerHTML=round(actuallyPaid,2);
  			document.getElementsByName("actuallyPaid")[i].value = round(actuallyPaid,2);
  			value += Number(amount)*10000;
  		}
  		document.getElementsByName("charge.billAmount")[0].value = round(value/10000,2);
  		// cal();//计算余额
  	}
  	//反选
  	function reverse(){
  		var obj = document.getElementsByName("checkbox");
  		var value = Number(document.getElementsByName("charge.billAmount")[0].value)*10000;
  		for(var i=0;i<obj.length;i++){
  			obj[i].checked=!obj[i].checked;
  			var amount = document.getElementsByName("pay")[i].value;//应付金额
  			var yjje = document.getElementsByName("actuallyPaid")[i].val;//已缴金额初始值
  			if(obj[i].checked){
  				var actuallyPaid = (Number(amount)*10000+Number(yjje)*10000)/10000;
  				//document.getElementById("je"+i).innerHTML=round(actuallyPaid,2);
  				document.getElementsByName("actuallyPaid")[i].value = round(actuallyPaid,2);
  				value += Number(amount)*10000;
  			}
  			else{
  				//document.getElementById("je"+i).innerHTML="&nbsp;"+round(yjje,2);
  				document.getElementsByName("actuallyPaid")[i].value = round(yjje,2);
  				value -= Number(amount)*10000;
  			}
  		}
  		document.getElementsByName("charge.billAmount")[0].value = round(value/10000,2);
  		// cal();//计算余额
  	}
  	//查询
  	function cx(){
  		document.forms[0].action = "<%=path%>/bill.do?method=getBillList";
  		document.forms[0].submit();
  	}
  	//计算余额 -->暂未起作用.
  	function cal(){
  		var sj = document.getElementsByName("charge.amount")[0].value;//金额
  		var jh = document.getElementsByName("charge.billAmount")[0].value;//账单金额
  		var yj = document.getElementsByName("deposit.amount")[0].value;//押金金额
  		var ysk = document.getElementsByName("advance.amount")[0].value;//预收款金额
  		if(!isNaN(Number(sj))){
  			document.getElementsByName("temporal.amount")[0].value = round((Number(sj)*10000-Number(jh)*10000-Number(yj)*10000-Number(ysk)*10000)/10000,2);
  		}
  	}
  	//合计
  	function total(){
  		var lastAmount = document.getElementsByName("amount")[0].value;//账单金额
  		var advance = document.getElementsByName("advance.amount")[0].value;//预收房租
  		var advanceWuYF = document.getElementsByName("advanceWuYF.amount")[0].value;//预收物业费
  		var rentDeposit = document.getElementsByName("rentDeposit.amount")[0].value;//房租押金
  		var decorationDeposit = document.getElementsByName("decorationDeposit.amount")[0].value;//装修押金
  		var earnest = document.getElementsByName("earnest.amount")[0].value;//订金
  		var val = (Number(lastAmount)*10000+Number(advance)*10000+Number(advanceWuYF)*10000+Number(rentDeposit)*10000+Number(decorationDeposit)*10000+Number(earnest)*10000)/10000
  		if(!isNaN(val)){
  			document.getElementsByName("charge.amount")[0].value = round(val,2);
  		}
  	}
  	//回退
  	function inverse(clientId,userId){
  		var clientId = document.getElementsByName("clientId")[0].value;
  		if(clientId==null||clientId.length==0){
  			alert("请先选择客户！");
  			return false;
  		}
  		var URL = "zc13/bkmis/costManage/public_pop.jsp?URL=bill.do?method=getNowCharge//clientId="+clientId+"//userId="+userId;
  		var return_value = showModalDialog(URL,"","dialogWidth=600px;dialogHeight=330px;");
  		if(typeof(return_value)!="undefined" && return_value=="1"){
  			cx();
  		}
  	}
  	//返回
  	function query(){
  		formEdit.action="<%=path%>/bill.do?method=queryCollectClient";
  		formEdit.submit();
  	}
  	//打印
  	function printbill(){
  		formEdit.action = "<%=path%>/bill.do?method=printBill";
  		formEdit.target = "_blank";
  		formEdit.submit();
  	//	var Url = "<%=path%>/bill.do?method=printBill";
  	//	window.open(Url);
  	}
  	function earnest(t){
  		if(t.checked){
	  		var box = document.getElementsByName("earnestFlag");
	  		for(var i=0;i<box.length;i++){
	  			if(t.value!=box[i].value){
	  				box[i].checked = false;
	  			}
	  		}
  		}
  	}
  </script>
</html>
