<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
	<title>电话计费列表</title>
	<meta http-equiv="pragma" content="no-cache" />
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript">
      //按名称查询
      function searchBy(){
         document.lpform.action = "info.jsp";
         document.lpform.submit();
      }
      function add(){

		    var td;
			var oTable=document.getElementById("table1");
		    var oTr1 = document.createElement ("tr");
		    for(var i=0;i<10;i++){
		    	td = document.createElement("td");
		    	if(i==0){
		    		td.setAttribute("className","RptTableBodyCellLock");
		    		td.setAttribute("align","center");
		    		//增加一个checkbox
		    		var textobj1 = document.createElement ("input");
		    		textobj1.setAttribute("type","checkbox");
			    	td.appendChild(textobj1);
		    	}else if(i==1){
		    		td.setAttribute("className","RptTableBodyCell");
		    		td.setAttribute("align","center");
		    		td.setAttribute("innerText",oTable.rows.length);
		    	}else{
			    	td.setAttribute ("className","RptTableBodyCell");
			    	var lableobj2 = document.createElement ("span");
  					lableobj2.setAttribute ("id","lb_line");
			    	td.appendChild(lableobj2);
			    	
		    	}
		    	oTr1.appendChild(td);
		    }
		    oTable.firstChild.appendChild(oTr1);
      }
      //查看明细
      function info(){
         var e = document.getElementsByName("lpId");
         var count = 0;
         var lpId;
         for(var i=0;i<e.length;i++){
            if(e[i].checked && e[i].type == "checkbox"){
               count++;
               lpId = e[i].value;
            }
         }
        if(count == 0){
            alert("请选择一条记录!");
            return;
        }else if(count == 1){
            window.location = "info.jsp";
        }else{
           alert("只能选择一条记录");
           return;
        }
      }
      //删除
      function deleteLp(){
         var temp = document.getElementsByName("lpId"); 
         var count = 0;
         for(var i=0;i<temp.length;i++){
             if(temp[0].type == "checkbox" && temp[i].checked){
                 count++;
             }
         }
         if(count == 0){
            alert("请至少选择一条记录进行删除！");
            return;
         }else{
            if(confirm("确定要删除吗?")){
              document.lpform.action = "list.jsp";
              document.lpform.target = "_self";
              document.lpform.submit();
            }
         }
      }
      //选中所有的checkbox
      var flag = "false";
      function selectedAll(){
         var list = document.getElementsByName("lpId");
         if(flag == "false"){
            for(var i=0;i<list.length;i++){
             list[i].checked = true;
          }
            flag = "true";
        }else{
           for(var i=0;i<list.length;i++){
              list[i].checked = false;
           }
           flag = "false";
        }
      }
      function exportLr(){
      	
      }
    </script>
</head>
<body style="overflow-y:hidden;">
<form  method="post" name="lpform" action = "">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height = "5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">S00001电话费用详细</td>
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
					           		<td class="txt" nowrap="nowrap">
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					           		</td>
					            	<td align = "left">日期：
										<input type="text" name="starttime" id="starttime" readonly onclick="WdatePicker()" value="2010-09-09" class="Wdate"/>
										&nbsp;至&nbsp;
										<input type="text" name="endtime" id="endtime" readonly onclick="WdatePicker()" value="2010-12-06" class="Wdate"/>
									</td>
									<td align="right">
					            		<input type="button" id = "focuson" onclick="searchBy();" class="button" value="确定">
									</td>
								</tr>
							 	 <tr>
					           		 <td height="10" colspan="9"></td>
					          	 </tr>
					        </table>
					        <!-- 查询条件end -->
		 			 	</td>
					</tr>
  					<tr height="95%">
					    <td valign = "top">
					    	<!-- 表单内容区域 -->
							<table  width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" >
								<tr height = "95%">
					        		<td width="100%">
					        		<div id = "div1" class = "RptDiv"  >
							   			<table class = "RptTable" border="0" cellpadding="0" cellspacing="0" id = "table1">
					              			<tr >
								                <th width = "5%" class = "RptTableHeadCellFullLock"><input type="checkbox" onclick="selectedAll();"></th>
												<th width = "10%" class = "RptTableHeadCellFullLock">序号</th>
												<th width = "10%" class = "RptTableHeadCellLock">日期</th>
												<th width = "10%" class = "RptTableHeadCellLock">通话开始时间</th>
												<th width = "10%" class = "RptTableHeadCellLock">通话结束时间</th>
												<th width = "10%" class = "RptTableHeadCellLock">通话时长</th>
												<th width = "5%" class = "RptTableHeadCellLock">通话类型</th>
												<th width = "15%" class = "RptTableHeadCellLock">对方号码</th>
												<th width = "10%" class = "RptTableHeadCellLock">长途类型</th>
												<th width = "15%" class = "RptTableHeadCellLock">本次通话费用（￥）</th>
											</tr>
											<c:choose>
											<c:when test="">
											<tr align="center">
												<td colspan="13" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的信息!</font>
												</td>
											</tr>
											</c:when>
											<c:otherwise>
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
														<td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="lpId" name="lpId" value="${lp.lpId}"></td>
														<td class="RptTableBodyCell" align="center" title="${vs.count}">1</td>
														<td class="RptTableBodyCell" align="center" ondblclick="bindCellInputEditor(WdatePicker(),doCellEdit,'ddd');">2010-12-01</td>
														<td class="RptTableBodyCell" align="center" ondblclick="bindCellInputEditor(WdatePicker({skin:'whyGreen',dateFmt:'HH:mm:ss'}),doCellEdit,'ddd');">08:12:12</td>
														<td class="RptTableBodyCell" align="center" ondblclick="bindCellInputEditor(WdatePicker({skin:'whyGreen',dateFmt:'HH:mm:ss'}),doCellEdit,'ddd');">08:20:10</td>
														<td class="RptTableBodyCell" align="center" title="${lp.ppType}">00:07:58</td>
														<td class="RptTableBodyCell" align="center"  ondblclick="bindCellSelectEditor(arraytxt(),arrayval(),doCellEdit);">主叫</td>
														<td class="RptTableBodyCell" align="center"  ondblclick="bindCellInputEditor(doCellEdit,'ddd');">13456789876</td>
														<td class="RptTableBodyCell" align="center"  ondblclick="bindCellSelectEditor(arraytxt2(),arrayval2(),doCellEdit);">本地</td>
														<td class="RptTableBodyCell" align="center" title="${lp.fax }">3.2</td>
													</tr>
													<tr  onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
														<td class = "RptTableBodyCellLock" align="center"><input type="checkbox" id="lpId" name="lpId" value="${lp.lpId}"></td>
														<td class="RptTableBodyCell" align="center" title="${vs.count}">2</td>
														<td class="RptTableBodyCell" align="center" ondblclick="bindCellInputEditor(WdatePicker(),doCellEdit,'ddd');">2010-12-04</td>
														<td class="RptTableBodyCell" align="center" ondblclick="bindCellInputEditor(WdatePicker({skin:'whyGreen',dateFmt:'HH:mm:ss'}),doCellEdit,'ddd');">09:02:12</td>
														<td class="RptTableBodyCell" align="center" ondblclick="bindCellInputEditor(WdatePicker({skin:'whyGreen',dateFmt:'HH:mm:ss'}),doCellEdit,'ddd');">09:20:14</td>
														<td class="RptTableBodyCell" align="center" >00:18:02</td>
														<td class="RptTableBodyCell" align="center" id ="bdd" ondblclick="bindCellSelectEditor(arraytxt(),arrayval(),doCellEdit);">被叫</td>
														<td class="RptTableBodyCell" align="center" ondblclick="bindCellInputEditor(doCellEdit,'ddd');">15916253456</td>
														<td class="RptTableBodyCell" align="center" ondblclick="bindCellSelectEditor(arraytxt2(),arrayval2(),doCellEdit);">本地</td>
														<td class="RptTableBodyCell" align="center" title="${lp.fax }">0</td>
													</tr>
											</c:otherwise>
										</c:choose>
					             		</table>
					             		</div>
									</td>
		     		 			</tr>
								<tr>
								  <td>&nbsp;</td>
								</tr>
								<!-- 分页start -->
								<tr>
									<td colspan="10">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr><td class="form_line3">&nbsp;</td>
												<td class="form_line3" colspan="8">${pagination }</td>
												<td class="form_line3">&nbsp;</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td align="right">
										<table>
											<tr>
					               				<td nowrap="nowrap"><input type="button" onclick="add();" class="button" value="增加"></td>
					               				<td nowrap="nowrap"><input type="button" onclick="deleteLp();" class="button" value="删除"></td>
					               				<td nowrap="nowrap"><input type="button" onclick="exportLr();" class="button" value="导出报表"></td>
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
	<script language=javascript>
    function test(elem) {
        var rtn = eval('VS_FV_' + elem.format + '()');
        if (rtn.length > 0) {
            alert(rtn);
            return false;
        } else {
            return true;
        }
    }
   
     function doCellEdit(cellobj, newtext, column) {
       // var s = column.split(";");
        /*var type=null;
        //alert(column);
        if (newtext != "&nbsp;" && newtext!='' && newtext!=null) {
        	//alert("newtext="+newtext);
            try {
                var isValidated = true;
                VS_FV_Validator.Resource = elem = cellobj.childNodes[0];
                
				//alert(type);
                //elem.field = s[0];
                //elem.format = "NumberZ";
                if (isValidated && test(elem) == false) {
                    return false;
                }
            } catch(e) {
            }
            if (newtext == 0) {
                alert("值不允许为零");
                return false;
            }
        } else {
            newtext = "newtext";
        }
        //alert(newtext);
        var rowObj = getRowObjByInnerObj(cellobj);
        var jh = rowObj.childNodes[0].innerText;
        var ajax = new AjaxObject();
        ajax.addParameter("keyValue", jh);
        ajax.addParameter("keyField", "jh");
        ajax.addParameter("field", s[1]);
        ajax.addParameter("shyj", s[2]);//审核意见
        ajax.addParameter("value", newtext);
        ajax.addParameter("shbz", "sh");//以此判断是否处理的是审核页面
        ajax.addParameter("type", type);
        var rtn = ajax.getResponseText("/public/updateDjxx.jsp");
        var isEdited=/true/gi.test(rtn);
        */
        //if(isEdited==true){
        	cellobj.style.backgroundColor="#CDCCCC";
        //}
        //return /true/gi.test(rtn);
        return /true/gi.test(true);
    }
    function arraytxt(){
    	var menu=new Array(2);
		 menu[0]="主叫";
		 menu[1]="被叫";
    	return menu;
		 
    }
    function arrayval(){
    	var menu=new Array(2);
		 menu[0]="1";
		 menu[1]="2";
    	return menu;
    }
    function arraytxt2(){
    	var menu=new Array(3);
		 menu[0]="本地";
		 menu[1]="长途";
		 menu[2]="国际长途";
    	return menu;
		 
    }
    function arrayval2(){
    	var menu=new Array(3);
		 menu[0]="1";
		 menu[1]="2";
		 menu[2]="3";
    	return menu;
		 
    }
    

</script>
</html>
