<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>设备_设备报废视图--新增</title>
	
	<!-- 下面一句禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache" />
	
	<link href="<%=path %>/resources/css/menu.css" rel="stylesheet" type="text/css" />
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

function postDialog(){
	//检查表单，没有错误再提交
	//prepareForm方法将会在TAGLIB输出
	var valid=prepareForm();
	if(valid==true){
		var item=dynaview.prepareForm2Item("executionForm");
		parent.window.returnValue=item;
		window.parent.close();//firefox不支持
		//document.getElementById("_addviewcontent").innerHTML="数据提交成功，请关闭窗口！";
	}
}



function initDynaView() {//页面初始化
	dynaview.onLoad();
	
}

dojo.addOnLoad(initDynaView);

</script>

</head>

<body>
<form name="executionForm" id="executionForm" action="addDynaViewData.do" method="post">
<input type="hidden" name="creator" value="商业用户A">
<input type="hidden" name="viewUID" value="Sbgl_FixingRejectView">
<input type="hidden" name="refreshMode" value="">
<table width="100%" border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td height="27" background="../../images/workflow/pics/bt_11.gif"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="22" align="center"><img src="../../images/workflow/pics/bt_12.gif" width="15" height="27"></td>
        <td width="884" height="27" class="font1">设备_设备报废视图--新增</td>
      </tr>
    </table></td>
  </tr>

  <tr><td height="5"></td></tr>
  <tr>
    <td align="center" valign="top" id="_addviewcontent"><table width="99%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="left">
   		

<!--ViewTempletStart-->

<TABLE id="part_canvas1" border="0" cellSpacing="0" cellPadding="0" width="100%">
<TBODY>
<TR>
<TD>
<TABLE id="part_1261711812828" class="part_base" border="0" cellSpacing="1" cellPadding="2" width="100%" align="center" dvType="part"><INPUT value="0" type="hidden" name="_displayStyle"> <INPUT type="hidden" name="_dataSource"> 
<TBODY>
<TR style="DISPLAY: none">
<TD height="23" colSpan="4">&nbsp;<SPAN id="part_1261711812828_partName">Table</SPAN> 
</TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">管理处</SPAN> 
</TD>
<TD class="part_td_base" height="23"><?xml:namespace prefix = bpm />
<SPAN dvType="text"><INPUT id='field_1630' name='dv.BpmSbgl_FixingReject.guanlichu' type='text' value='' size='20' class='dvfield_readonly' readonly /></SPAN> 
</TD>
<TD class="part_td_base" height="23" align="right">&nbsp; 
</TD>
<TD class="part_td_base" height="23">&nbsp; 
</TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">设备名称</SPAN>
<SPAN style="BACKGROUND: none transparent scroll repeat 0% 0%; COLOR: red" class="lable_title_3" dvType="lable">*</SPAN> 
</TD>
<TD class="part_td_base" height="23">
<SPAN dvType="refText"><INPUT id='field_1688' name='dv.BpmSbgl_FixingReject.shebeimingchen' type='text' value='' size='20' class='dvfield_readonly' readonly require='true'  msg='请选择设备'  ondblclick="selectRefView({vuid:'Sbgl_FixingRelationList',ds:'SbglFixingCard_DS',id:'field_1688',kf:'shebeimingchen',kvf:'shebeimingchen',map:{'field_1687':'shebeiNo','field_1703':'ID','field_1689':'xinghao','field_1690':'anzhuangdidian','f_BSFR_tourushiyongrqi':'yanshourqi'},onFilter:select})" /><IMG onclick="selectRefView({vuid:'Sbgl_FixingRelationList',ds:'SbglFixingCard_DS',id:'field_1688',kf:'shebeimingchen',kvf:'shebeimingchen',map:{'field_1687':'shebeiNo','field_1703':'ID','field_1689':'xinghao','field_1690':'anzhuangdidian','f_BSFR_tourushiyongrqi':'yanshourqi'},onFilter:select})" border="0" align="absMiddle" src="/bpm/management/workflow/dynaview/images/guanxi.gif" width="20" height="20"></SPAN> 
</TD>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">设备编号</SPAN> 
</TD>
<TD class="part_td_base" height="23">
<SPAN dvType="text"><INPUT id='field_1687' name='dv.BpmSbgl_FixingReject.shebeiNo' type='text' value='' size='20' class='dvfield_readonly' readonly /></SPAN> 
</TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">型号</SPAN> 
</TD>
<TD class="part_td_base" height="23">
<SPAN dvType="text"><INPUT id='field_1689' name='dv.BpmSbgl_FixingReject.xinghao' type='text' value='' size='20' class='dvfield_readonly' readonly /></SPAN> 
</TD>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">安装位置</SPAN> 
</TD>
<TD class="part_td_base" height="23">
<SPAN dvType="text"><INPUT id='field_1690' name='dv.BpmSbgl_FixingReject.anzhuangweizhi' type='text' value='' size='20' class='dvfield_readonly' readonly /></SPAN> 
</TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">投入使用日期</SPAN>
<SPAN style="BACKGROUND: none transparent scroll repeat 0% 0%; COLOR: red" class="lable_title_3" dvType="lable">*</SPAN> 
</TD>
<TD class="part_td_base" height="23">
<SPAN dvType="date"><INPUT id='f_BSFR_tourushiyongrqi' name='dv.BpmSbgl_FixingReject.tourushiyongrqi' type='text' value='' size='20' class='dvfield_readonly' readonly /><IMG onclick="calendar(this);" border="0" align="absMiddle" src="/bpm/management/workflow/dynaview/images/rili.gif" width="16" height="16"></SPAN> 
</TD>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">拟报废日期</SPAN>
<SPAN style="BACKGROUND: none transparent scroll repeat 0% 0%; COLOR: red" class="lable_title_3" dvType="lable">*</SPAN> 
</TD>
<TD class="part_td_base" height="23">
<SPAN dvType="date"><INPUT id='field_1692' name='dv.BpmSbgl_FixingReject.nibaofeirqi' type='text' value='' size='20' class='dvfield_readonly' readonly require='true'  msg='请选择拟报废日期'  /><IMG onclick="calendar(this);" border="0" align="absMiddle" src="/bpm/management/workflow/dynaview/images/rili.gif" width="16" height="16"></SPAN> 
</TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">报废处理方案</SPAN> 
</TD>
<TD class="part_td_base" height="23">
<SPAN dvType="text"><INPUT id='field_1693' name='dv.BpmSbgl_FixingReject.baofeichulifangan' type='text' value='' size='20' class='dvfield_editable'  /></SPAN> 
</TD>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">申请人</SPAN> 
</TD>
<TD class="part_td_base" height="23">
<SPAN dvType="refText"><INPUT id='field_1694' name='dv.BpmSbgl_FixingReject.shenqingren' type='text' value='商业用户A' size='20' class='dvfield_readonly' readonly ondblclick="selectRefView({vuid:'Rlzy_IncumbencyDossierList',ds:'RlzyIncumbencyDossier_DS',id:'field_1694',kf:'xingming',kvf:'xingming',map:{},onFilter:''})" /><IMG onclick="selectRefView({vuid:'Rlzy_IncumbencyDossierList',ds:'RlzyIncumbencyDossier_DS',id:'field_1694',kf:'xingming',kvf:'xingming',map:{},onFilter:''})" border="0" align="absMiddle" src="/bpm/management/workflow/dynaview/images/guanxi.gif" width="20" height="20"></SPAN> 
</TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">报废原因</SPAN> 
</TD>
<TD class="part_td_base" height="23" colSpan="3">
<SPAN dvType="textarea"><TEXTAREA id='field_1695' name='dv.BpmSbgl_FixingReject.baofeiyuanyin' cols='93' rows='3' class='dvfield_editable'  require='' msg='' ></TEXTAREA></SPAN> 
</TD></TR>
<TR>
<TD class="part_td_base" height="23" align="right"><SPAN onclick="span_onClick(this,'lable');">备注</SPAN> 
</TD>
<TD class="part_td_base" height="23" colSpan="3">
<SPAN dvType="textarea"><TEXTAREA id='field_1696' name='dv.BpmSbgl_FixingReject.beizhu' cols='93' rows='3' class='dvfield_editable'  require='' msg='' ></TEXTAREA></SPAN> 
</TD></TR></TBODY></TABLE>
</TD></TR>
<TR id="part_lastTr">
<TD height="8">
<SPAN dvType="text"><INPUT id='field_1703' name='dv.BpmSbgl_FixingReject.shebeitaizhangNo' type='hidden' value='' size='20' class='text_title_1'  /></SPAN>
<SPAN dvType="text"><INPUT id='f_BSFR_PropRegion_ID' name='dv.BpmSbgl_FixingReject.PropRegion_ID' type='hidden' value='' size='20' class='text_title_1'  /></SPAN> 
</TD></TR></TBODY></TABLE>
<!-- 框架自动生成代码 开始-->
<script type='text/javascript' src='/bpm/dwr/interface/DWRServiceComponentAdapter.js'> </script>
<INPUT type='hidden' name='dv.BpmSbgl_FixingReject.ID' id='dv.BpmSbgl_FixingReject.ID' value=''/>
<script type='text/javascript'>
function onEvent_dv_BpmSbgl_FixingReject_gongsibangongshiyijian(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_baofeiyuanyin(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_regionID(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_xinghao(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_shenqingbumenyijian(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_shebeitaizhangNo(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_guanlichuyijian(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_gongsicaiwubuyijian(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_shebeiNo(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_gongsigongchengbuyijian(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_shenqingren(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_shebeimingchen(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误(请选择设备)--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_PropRegion_ID(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_zongjinglishenpi(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_beizhu(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_guanlichu(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_baofeichulifangan(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_nibaofeirqi(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误(请选择拟报废日期)--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_tourushiyongrqi(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误(请选择拟投入使用日期)--'+e); 
     return false; 
  } 
  return true; 
} 
function onEvent_dv_BpmSbgl_FixingReject_anzhuangweizhi(theField){
  try{ 
  }catch(e){ 
     alert('视图脚本错误()--'+e); 
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
		
			<input name="Submit22" id="_dvSubmitBtn" type="button" class="butter2" value="提交" onClick="return postTask();" />
		
		</td>
      </tr>
    </table></td>
  </tr>
</table>
<br/>
</form> 
</body>
</html>
