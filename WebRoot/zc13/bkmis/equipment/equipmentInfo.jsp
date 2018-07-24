<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>设备_设备台帐视图--明细</title><link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/util/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>



<script type="text/javascript" src="/bpm/resources/libs/dojo/v1.2.0/dojo/dojo.js" djConfig="parseOnLoad:true"></script>
<script type="text/javascript">
    dojo.require("dijit.layout.ContentPane");
    dojo.require("dijit.layout.TabContainer");
</script>
<script type='text/javascript' src='../../JS/dv_runtime.js'></script>
<script type='text/javascript' src='../../JS/validator.js'></script>

<script type="text/javascript">
function postTask(){

	//检查表单，没有错误再提交
	//prepareForm方法将会在TAGLIB输出
	var valid=prepareForm();
	if(valid==true){
		//禁用提交按钮，防止重复提交
		var btn=document.getElementById("_dvSubmitBtn");
		if(btn){ btn.disabled=true; }
		executionForm.submit();
	}
}

    function initDynaView() {//页面初始化
		dynaview.onLoad();
    }
    
    dojo.addOnLoad(initDynaView);

</script>

</head>

<body>
		<form name="executionForm" id="executionForm" action="updateDynaViewData.do" method="post">
		<input type="hidden" name="creator" value="商业用户A">
		<input type="hidden" name="viewUID" value="Sbgl_FixingCardView">
<table width="100%" border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td height="27" background="../../images/workflow/pics/bt_11.gif"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="22" align="center"><img src="../../images/workflow/pics/bt_12.gif" width="15" height="27"></td>
        <td width="884" height="27" class="font1">设备_设备台帐视图--明细</td>
      </tr>
    </table></td>
  </tr>
  
  <tr><td height="5"></td></tr>
  <tr>
    <td align="center" valign="top"><table width="98%" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#BFBFBF">
          <tr>
            <td align="left" bgcolor="#FFFFFF">
   			<script type='text/javascript'>
function prepareForm(){ 
try{ 
  var ret=false;
ret=prepareFormDynaView();
  return ret; 
}catch(e){ 
alert('表单校验错误'+e); 
return false; 
} 
} 
</script>





 
 
  

<script type="text/javascript">
if("null"!="null"){
	dynaview.addOnLoad(addonInit); 
}
function addonInit(){
	var rid='null';
	var fid='null';
	var sName='null';
	var statu='null';
	document.getElementById("f_BSFC_status").value=statu;
	document.getElementById("f_BSFC_PropRegion_ID").value=rid;
	document.getElementById("field_1582").value=fid;
	document.getElementById("f_1476101545").value=sName;

}
function validateTime(){
	var beginTime=document.getElementById("field_1578").value;
	var endTime=document.getElementById("field_1579").value;
	if(endTime<=beginTime){
		alert("截止时间应该大于接管时间！");
		return;
	}else{return true;}
}
dynaview.addOnSubmit(validateTime);
 
</script>






<!--ViewTempletStart-->
<TABLE id="part_canvas1" border="0" cellSpacing="0" cellPadding="0" width="100%">
<TBODY>
<TR>
<TD>
<TABLE id="part_1261554802828" class="part_base" border="0" cellSpacing="1" cellPadding="2" width="100%" align="center" dvType="part"><INPUT value="0" type="hidden" name="_displayStyle"> <INPUT type="hidden" name="_dataSource"> 
<TBODY>
<TR style="DISPLAY: none">
<TD height="23" colSpan="4">&nbsp;<SPAN id="part_1261554802828_partName">Table</SPAN> </TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN style="BACKGROUND: none transparent scroll repeat 0% 0%" onclick="span_onClick(this,'lable');">设备类别<FONT color="red">*</FONT></SPAN> </TD>
<TD class="part_td_base" height="23"><?xml:namespace prefix = bpm /><SPAN dvType="refText"><INPUT id='f_1476101545' name='dv.BpmSbgl_FixingCard.shebeileibie' type='text' value='安防系统' size='20' class='dvfield_readonly' readonly require='true'  msg='设备类别必填'  ondblclick="selectRefView({vuid:'Sbgl_FixingSortSelectList',ds:'BpmSbgl_FixingSort',id:'f_1476101545',kf:'fenleimingcheng',kvf:'fenleimingcheng',map:{'field_1582':'ID'},onFilter:''})" /><IMG onclick="selectRefView({vuid:'Sbgl_FixingSortSelectList',ds:'BpmSbgl_FixingSort',id:'f_1476101545',kf:'fenleimingcheng',kvf:'fenleimingcheng',map:{'field_1582':'ID'},onFilter:''})" border="0" align="absMiddle" src="/bpm/management/workflow/dynaview/images/guanxi.gif" width="20" height="20"></SPAN></TD>
<TD class="part_td_base" height="23">&nbsp;</TD>
<TD class="part_td_base" height="23">&nbsp;</TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN style="BACKGROUND: none transparent scroll repeat 0% 0%" onclick="span_onClick(this,'lable');">设备编号<FONT color="red">*</FONT></SPAN> </TD>
<TD class="part_td_base" height="23"><SPAN dvType="text"><INPUT id='field_1566' name='dv.BpmSbgl_FixingCard.shebeiNo' type='text' value='VHⅢ-RY-Ⅱ-02-20' size='20' class='dvfield_editable'  require='true'  msg='设备编号必填'  /></SPAN></TD>
<TD class="part_td_base" height="23" align="right"><SPAN style="BACKGROUND: none transparent scroll repeat 0% 0%" onclick="span_onClick(this,'lable');">设备名称<FONT color="red">*</FONT></SPAN> </TD>
<TD class="part_td_base" height="23"><SPAN dvType="text"><INPUT id='field_1567' name='dv.BpmSbgl_FixingCard.shebeimingchen' type='text' value='火灾报警18' size='20' class='dvfield_editable'  require='true'  msg='设备名称必填'  /></SPAN></TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN style="BACKGROUND: none transparent scroll repeat 0% 0%" onclick="span_onClick(this,'lable');">设备等级</SPAN> </TD>
<TD class="part_td_base" height="23"><SPAN dvType="select"><SELECT id='field_1568' name='dv.BpmSbgl_FixingCard.shebeidengji' class='dvfield_editable' require='' msg=''><OPTION VALUE='一级'>一级</OPTION>
<OPTION VALUE='二级'>二级</OPTION>
<OPTION VALUE='三级'>三级</OPTION>
</SELECT>&nbsp;</SPAN></TD>
<TD class="part_td_base" height="23" align="right"><SPAN style="BACKGROUND: none transparent scroll repeat 0% 0%" onclick="span_onClick(this,'lable');">型号<FONT color="red">*</FONT></SPAN> </TD>
<TD class="part_td_base" height="23"><SPAN dvType="text"><INPUT id='field_1569' name='dv.BpmSbgl_FixingCard.xinghao' type='text' value='JB-QB/LH207' size='20' class='dvfield_editable'  require='true'  msg='型号必填'  /></SPAN></TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">技术规格</SPAN> </TD>
<TD class="part_td_base" height="23" colSpan="3"><SPAN dvType="textarea"><TEXTAREA id='field_1570' name='dv.BpmSbgl_FixingCard.jishuguige' cols='93' rows='3' class='dvfield_editable'  require='false' msg='' >功率75千瓦</TEXTAREA></SPAN></TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">供应商</SPAN> </TD>
<TD class="part_td_base" height="23"><SPAN dvType="text"><INPUT id='field_1574' name='dv.BpmSbgl_FixingCard.gongyingshang' type='text' value='VRAKOS-PCLEMIS/希腊' size='20' class='dvfield_editable'  /></SPAN></TD>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">生产厂家</SPAN> </TD>
<TD class="part_td_base" height="23"><SPAN dvType="text"><INPUT id='field_1573' name='dv.BpmSbgl_FixingCard.shengchanchangjia' type='text' value='VRAKOS-PCLEMIS/希腊' size='20' class='dvfield_editable'  require='false'  msg=''  /></SPAN></TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">单价</SPAN> </TD>
<TD class="part_td_base" height="23"><SPAN dvType="text"><INPUT id='field_1572' name='dv.BpmSbgl_FixingCard.danjia' type='text' value='' size='20' class='dvfield_editable'  /></SPAN></TD>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">数量</SPAN> </TD>
<TD class="part_td_base" height="23"><SPAN dvType="text"><INPUT id='field_1571' name='dv.BpmSbgl_FixingCard.shuliang' type='text' value='1' size='20' class='dvfield_editable'  /></SPAN></TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">安装地点</SPAN> </TD>
<TD class="part_td_base" height="23"><SPAN dvType="text"><INPUT id='field_1575' name='dv.BpmSbgl_FixingCard.anzhuangdidian' type='text' value='塔二' size='20' class='dvfield_editable'  require='false'  msg=''  /></SPAN></TD>
<TD class="part_td_base" height="23" align="right"><SPAN style="BACKGROUND: none transparent scroll repeat 0% 0%" onclick="span_onClick(this,'lable');">服务区域</SPAN> </TD>
<TD class="part_td_base" height="23"><SPAN dvType="text"><INPUT id='field_1576' name='dv.BpmSbgl_FixingCard.fuwuquyu' type='text' value='' size='20' class='dvfield_editable'  require='false'  msg=''  /></SPAN></TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">责任人</SPAN> </TD>
<TD class="part_td_base" height="23"><SPAN dvType="text"><INPUT id='field_1577' name='dv.BpmSbgl_FixingCard.zerenren' type='text' value='陈工' size='20' class='dvfield_editable'  /></SPAN></TD>
<TD class="part_td_base" height="23" align="right">&nbsp;</TD>
<TD class="part_td_base" height="23">&nbsp;</TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">验收接管日期</SPAN> <SPAN style="BACKGROUND: none transparent scroll repeat 0% 0%; COLOR: red" class="lable_title_3" dvType="lable">*</SPAN></TD>
<TD class="part_td_base" height="23"><SPAN dvType="date"><INPUT id='field_1578' name='dv.BpmSbgl_FixingCard.yanshourqi' type='text' value='' size='20' class='dvfield_readonly' readonly require='true'  msg='请选择验收接管日期'  /><IMG onclick="calendar(this);" border="0" align="absMiddle" src="/bpm/management/workflow/dynaview/images/rili.gif" width="16" height="16"></SPAN></TD>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">维保截止日期</SPAN> <SPAN style="BACKGROUND: none transparent scroll repeat 0% 0%; COLOR: red" class="lable_title_3" dvType="lable">*</SPAN></TD>
<TD class="part_td_base" height="23"><SPAN dvType="date"><INPUT id='field_1579' name='dv.BpmSbgl_FixingCard.weibaojiezhirqi' type='text' value='' size='20' class='dvfield_readonly' readonly require='true'  msg='请选择维保截止日期'  /><IMG onclick="calendar(this);" border="0" align="absMiddle" src="/bpm/management/workflow/dynaview/images/rili.gif" width="16" height="16"></SPAN></TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">备注</SPAN> </TD>
<TD class="part_td_base" height="23" colSpan="3"><SPAN dvType="textarea"><TEXTAREA id='field_1580' name='dv.BpmSbgl_FixingCard.beizhu' cols='93' rows='3' class='dvfield_editable'  require='' msg='' ></TEXTAREA></SPAN></TD></TR></TBODY></TABLE></TD></TR>
<TR id="part_lastTr">
<TD height="8"><SPAN dvType="text"><INPUT id='field_1582' name='dv.BpmSbgl_FixingCard.shebeifenleiNo' type='hidden' value='21' size='20' class='text_title_1'  /></SPAN><SPAN dvType="text"><INPUT id='f_BSFC_PropRegion_ID' name='dv.BpmSbgl_FixingCard.PropRegion_ID' type='hidden' value='3' size='20' class='text_title_1'  /></SPAN><SPAN dvType="text"><INPUT id='f_BSFC_status' name='dv.BpmSbgl_FixingCard.status' type='hidden' value='0' size='20' class='text_title_1'  /></SPAN></TD></TR></TBODY></TABLE>
<!-- 框架自动生成代码 开始-->
<script type='text/javascript' src='/bpm/dwr/interface/DWRServiceComponentAdapter.js'> </script>
<INPUT type='hidden' name='dv.BpmSbgl_FixingCard.ID' id='dv.BpmSbgl_FixingCard.ID' value='851'/>
<script type='text/javascript'>
function onEvent_dv_BpmSbgl_FixingCard_jishuguige(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_fuwuquyu(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_anzhuangdidian(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_shuliang(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_xinghao(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误(型号必填)--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_zerenren(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_shebeiNo(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误(设备编号必填)--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_status(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_shebeidengji(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_shengchanchangjia(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_gongyingshang(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_PropRegion_ID(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_regionID(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_beizhu(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_danjia(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_shebeifenleiNo(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_weibaojiezhirqi(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误(请选择维保截止日期)--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_shebeileibie(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误(设备类别必填)--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_shebeimingchen(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误(设备名称必填)--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingCard_yanshourqi(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误(请选择验收接管日期)--'+e); 
     return false; 
  } 
  return true; 
} 
</script>
<script type='text/javascript'>
</script>

<br/>

<!-- 框架自动生成代码 结束-->

			</td>
          </tr>
        </table></td>
      </tr>
      <tr id="_submitDiv" style="display:">
      
      <td height="35">
      
        <input name="Submit22" id="_dvSubmitBtn" type="button" class="butter2" value="提交" onclick="return postTask();" />
      
      
      
	    <input name="Submit23" type="button" class="butter2" value="打印" onclick="window.open('printview.jsp?viewUID=Sbgl_FixingCardView&dataKey=851&_tm=1308050509891','视图打印','');" />
	      
      </td>
      </tr>
      
    </table></td>
  </tr>
</table>
<br/>
</form> 
</body>
</html>
