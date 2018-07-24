<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
	<title>demo</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <!-- 使用日期控件时，引入下面的js -->
	<script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	
</head>
<body>
<form name = "form1">
	<table width="99%" height = "96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout:fixed">
		<tr>
    		<td height="5"></td>
  		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellspacing="0" cellpadding="0">
      				<tr>
        				<td width="14"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
						<td width="165" nowrap="nowrap" class="form_line">demo页面</td>
						<td width="24"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
						<td width="1080" class="form_line2"></td>
						<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
        			</tr>
    			</table>
    		</td>
  		</tr>
  		<tr height = "95%"><!-- 这个td负责画正式内容的左、右、下方的边框 -->
    		<td class="menu_tab2" align="center">
     			<table width="100%"  height = "100%" border="0" cellspacing="0" cellpadding="0">
	 				<tr>
						<td  align="center">
							<!-- 查询条件start -->
		  					<table width="100%" height = "100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
					            	<td height="10" colspan="9"></td>
					         	</tr>
					          	<tr>
					           		<td class="txt" nowrap="nowrap">
					           			<img src="<%=path %>/resources/images/fdj.gif" width="15" height="14" />查询条件：
					           		</td>
					            	<td nowrap="nowrap" align="right">查询名称demo：</td>
					            	<td class="txt"><input name="" type="text"  value="" /></td>
					           		<td align="right" nowrap="nowrap">查询日期区间demo：</td>
					            	<td class="txt">
					              		<input type="text" name="begin" value="" readonly onclick="WdatePicker();" class="Wdate">
										&nbsp;至&nbsp;
										<input type="text" name="end" value="" readonly onclick="WdatePicker();" class="Wdate">
									</td>
					            	<td nowrap="nowrap" align="right"> </td>
					            	<td class="txt"> </td>
					            	
					         	 </tr>
					         	 <tr> 
					         	 	<td class="txt" nowrap="nowrap"></td>
									<td nowrap="nowrap" align="right">demo：</td>
									<td class="txt"><input type="text" name="" value="" ></td>
									<td align="right" nowrap="nowrap">select框demo：</td>
									<td class="txt">
										<select name="type_validity" style="width:130px;">
											<option value="">- - -请选择- - -</option>
											<option <c:if test="${1==1 }">selected</c:if>>是</option>
											<option <c:if test="${1==2 }">selected</c:if>>否</option>
										</select>
									</td>
									<td nowrap="nowrap" align="right"></td>
									<td class="txt"></td>
									<td align="right">
										<!-- 如果想在enter键敲下的时候默认被点击，那么只需将button的id设置为focuson即可 -->
					            		<input type="button" class="button" id = "focuson" value="确定">
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
							   			<table border="0" cellpadding="0" cellspacing="0" class = "RptTable">
					              			<tr>
								                <th width="7%"  nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">
								                	<input type="checkbox" id="checkboxAll" name="checkboxAll" onclick="checkAll(this, 'id')">
								                </th>
								                <th width="10%" nowrap="nowrap" class="RptTableHeadCellFullLock" align="center">表头</th>
								                <th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">表头</th>
												<th width="10%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">表头</th>
												<th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">表头</th>
								                <th width="12%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">表头</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">表头</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">表头</th>
												<th width="8%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">表头</th>
												<th width="18%" nowrap="nowrap" class="RptTableHeadCellLock" align="center">表头</th>
											</tr>
											<%for(int i=0;i<10;i++){
											
											 %>
											<tr onmouseover="this.className = 'hover';" onmouseout="this.className = '';">
											 	<td width="10%" nowrap="nowrap" class="RptTableBodyCellLock" align="center">列头</td>
								                <td width="10%" nowrap="nowrap" class="RptTableBodyCell" align="center">单元格</td>
												<td width="10%" nowrap="nowrap" class="RptTableBodyCell" align="center">单元格</td>
												<td width="10%" nowrap="nowrap" class="RptTableBodyCell" align="center">单元格</td>
												<td width="12%" nowrap="nowrap" class="RptTableBodyCell" align="center">单元格</td>
								                <td width="12%" nowrap="nowrap" class="RptTableBodyCell" align="center">单元格</td>
												<td width="8%" nowrap="nowrap" class="RptTableBodyCell" align="center">单元格</td>
												<td width="8%" nowrap="nowrap" class="RptTableBodyCell" align="center">单元格</td>
												<td width="8%" nowrap="nowrap" class="RptTableBodyCell" align="center">单元格</td>
												<!-- 如果单元格需要实现在线编辑功能，请参照如下代码 -->
												<td width="8%" nowrap="nowrap" class="RptTableBodyCell"  align="center" 
													ondblclick="bindCellInputEditor(doCellEdit,'ddd');">可编辑单元格</td>
											</tr>
											<%} %>
											<tr align="center">
												<td colspan="10" align="center" class="head_form1">
													<font color="red">对不起，没有符合条件的费用类型记录!</font>
												</td>
											</tr>
					             		</table>
					             		</div>
									</td>
		     		 			</tr>
								<tr>
								  <td>&nbsp;</td>
								</tr>
								<!-- 分页start -->
            					<c:if test="${!empty chargeTypeList}">
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
								</c:if> 
								<!-- 分页end -->
								<tr>
									<td align="right">
										<table>
											<tr>
					                			<td nowrap="nowrap"  align="right"><input class="button" type="button" value="确定"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="取消"></td>
					              			</tr>
					              		</table>
					              	</td>
				              	</tr>
							</table>
							
							<!-- 扩展业务start（没有扩展业务，则删除下面一段） -->
						 	<br>
						  	<br>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>扩展业务名称demo：</td>
								</tr>
								<tr>
									<td>
									    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="RptTable">
											<tr>
								                <td width="17%"  nowrap="nowrap" class="head_form1" align="right">扩展：</td>
								                <td nowrap="nowrap" class="head_form1">&nbsp;
													<input type="text" name="" maxlength="20" onchange="test_code()" id="cha_code"><font color="red" >*</font></td>
								                <td width="19%" nowrap="nowrap" class="head_form1" align="right">扩展：</td>
								                <td width="26%" nowrap="nowrap" class="head_form1">&nbsp;
								                	<input type="text" name="" maxlength="30"><font color="red">*</font></td>
												<td width="19%" nowrap="nowrap" class="head_form1" align="right">扩展：</td>
								                <td width="26%" nowrap="nowrap" class="head_form1">&nbsp;
							                  	<input type="text" name="cha_price" maxlength="8"><font color="red">*</font></td>
							              	</tr>
							            </table>			
									</td>
				      			</tr>
				      			<tr>
				        			<td colspan="10" align="right"> 
										<table>
											<tr>
					                			<td nowrap="nowrap"  align="right"><input class="button" type="button" value="确定"></td>
					               				<td nowrap="nowrap"><input type="button" class="button" value="取消"></td>
					              			</tr>
					              		</table>
									</td>
				      			</tr>
				    		</table>
				    		<!-- 扩展业务end（如果没有扩展业务，则删除上面一段） -->
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
</script>
</html>
