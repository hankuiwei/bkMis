// JavaScript Document
var sUserAgent = navigator.userAgent; 
var isIE=isIE6=isIE7=isIE8=isFirefox=false; 
if(sUserAgent.indexOf("MSIE") > -1){ 
	isIE = true; 
	var version = sUserAgent.substr(sUserAgent.indexOf("MSIE")+5,3); 
	if(version == "6.0"){isIE6=true;} 
	if(version == "7.0"){isIE7=true;} 
	if(version == "8.0"){isIE8=true;} 
} 
//自定义弹出的是否窗口
	function confirm2(str){
		execScript("n = (msgbox('"+ str +"',vbYesNo, '来自网页的消息')=vbYes)", "vbscript"); 
		return(n); 
	} 
//日期，获取上一天
	function submitTopDay(s) {//上一天          
		d   =   new   Date(Date.parse(s.replace(/-/g,   '/')));       
		d.setDate(d.getDate()-1);
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

 var checkflag = "false";   
    // 全选功能   
    function selectAll(name){
        var field = document.getElementsByName(name);   
        // 如果全选按钮状态是未选中   
        if (checkflag == "false"){   
            for (i = 0; i < field.length; i++){   
                field[i].checked = true;   
            }   
            // 更改全选按钮选中标志   
            checkflag = "true";   
        }else{   
            for (i = 0; i < field.length; i++){   
                field[i].checked = false;    
            }   
            // 更改全选按钮选中标志   
            checkflag = "false";   
        }   
    }
    //反选
    function selectDif(name){
    	 var field = document.getElementsByName(name);
    	 for (i = 0; i < field.length; i++){
    	 		var e = field[i];
                e.checked = !e.checked;   
            } 
    }
    //隐藏加载图片
    function hideLoadingDiv(){
			document.getElementById("loading").style.display="none";
		}



var divMain = document.getElementById("contentcolumn");


// function closeLeftMenu(){
//	document.getElementById("contentcolumn_right").style.marginTop ="-23px";
//	document.getElementById("contentcolumn_left").style.display="block";
//	document.getElementById("leftcolumn").style.display="none";
//	changecss('#contentcolumn','margin-left','0px');
//  }

//  function openLeftMenu(){
//	document.getElementById("contentcolumn_right").style.marginTop ="0px";
//	document.getElementById("contentcolumn_left").style.display="none";
//	document.getElementById("leftcolumn").style.display="block";
//	changecss('#contentcolumn','margin-left','220px');
//   }



function closeLeftMenu(){
	document.getElementById("divOpenLeftManu").style.display = "block";
	
	if(isIE6){
		document.getElementById('mainDiv').style.paddingLeft = 0 + 'px';
		document.getElementById('leftDiv').style.display = 'none';
	}else{
		document.getElementById('rightDiv').style.left= 0 + 'px';
	}
	
}

function openLeftMenu(){
	document.getElementById("divOpenLeftManu").style.display = "none";
	if(isIE6){
		document.getElementById('mainDiv').style.paddingLeft = 220 + 'px';
		document.getElementById('leftDiv').style.display = 'block';
	}else{
		document.getElementById('rightDiv').style.left= 225 + 'px';
	}
}




function openHighSerch(){
	document.getElementById("serch").style.display="none";
	document.getElementById("high_serch").style.display="none";
	document.getElementById("serchBtn").style.display="block";
	document.getElementById("common_serch").style.display="block";
	document.getElementById("final_high_serch_body").style.display="block";
	document.getElementById("final_serch_bg").style.backgroundColor="#7b94a8";
	
}

function closeHighSerch(){
	document.getElementById("serch").style.display="block";
	document.getElementById("high_serch").style.display="block";
	document.getElementById("serchBtn").style.display="none";
	document.getElementById("common_serch").style.display="none";
	document.getElementById("final_high_serch_body").style.display="none";
	document.getElementById("final_serch_bg").style.backgroundColor="#80a6c2";
}





//*************************************************************************

function openHighSerch_t(){
	document.getElementById("Players").style.display="none";
	document.getElementById("high_serch_t").style.display="none";//查询按钮隐藏
	//document.getElementById("serchBtn").style.display="block";
	document.getElementById("common_serch_t").style.display="block";//播放按钮显示
	document.getElementById("Inquiry").style.display="block";
	//document.getElementById("final_serch_bg").style.backgroundColor="#7b94a8";
	document.getElementById("Players_nr").style.display="none";
	document.getElementById("Inquiry_nr").style.display="block";
	
}

function closeHighSerch_t(){
	document.getElementById("Players").style.display="block";
	document.getElementById("high_serch_t").style.display="block";//查询按钮显示
	//document.getElementById("serchBtn").style.display="none";
	document.getElementById("common_serch_t").style.display="none";//播放按钮隐藏
	document.getElementById("Inquiry").style.display="none";
	//document.getElementById("final_serch_bg").style.backgroundColor="#80a6c2";
	document.getElementById("Players_nr").style.display="block";
	document.getElementById("Inquiry_nr").style.display="none";
}
//*************************************************************************





var cp1 = true;
function upAndDown1(){
	if(cp1){
		document.getElementById("cp1up").style.visibility="visible";
		document.getElementById("cp1down").style.visibility="hidden";
		cp1 = false;
	}else{
		document.getElementById("cp1down").style.visibility="visible";
		document.getElementById("cp1up").style.visibility="hidden";
		cp1 = true;
	}	
}

var cp2 = true;
function upAndDown2(){
	if(cp2){
		document.getElementById("cp2up").style.visibility="visible";
		document.getElementById("cp2down").style.visibility="hidden";
		cp2 = false;
	}else{
		document.getElementById("cp2down").style.visibility="visible";
		document.getElementById("cp2up").style.visibility="hidden";
		cp2 = true;
	}	
}

var cp3 = true;
function upAndDown3(){
	if(cp3){
		document.getElementById("cp3up").style.visibility="visible";
		document.getElementById("cp3down").style.visibility="hidden";
		cp3 = false;
	}else{
		document.getElementById("cp3down").style.visibility="visible";
		document.getElementById("cp3up").style.visibility="hidden";
		cp3 = true;
	}	
}

var cp4 = true;
function upAndDown4(){
	if(cp4){
		document.getElementById("cp4up").style.visibility="visible";
		document.getElementById("cp4down").style.visibility="hidden";
		cp4 = false;
	}else{
		document.getElementById("cp4down").style.visibility="visible";
		document.getElementById("cp4up").style.visibility="hidden";
		cp4 = true;
	}	
}




//

function findplayChange()
	{
		var v=document.getElementById("findplay");
		var div_player=document.getElementById("div_player");
		if('播放'==v.value){
			div_player.style.display = 'block';
			v.value="查询";
			sTable= document.getElementById("ServiceListTable");
			crows=sTable.rows.length;
			//alert("crows=:"+crows);
			onLoadPlay();
		}else{
			v.value="播放";
			div_player.style.display = 'none';
		
		}
	}

function showLoadingDiv(){
	document.getElementById("loading").style.display="block";
	document.getElementById("loading2").style.display="block";
}

/**
 * 获取服务器时间并显示在页面
 * 页面的obj对象上需要事先显示服务器上的时间，可以在页面上直接用java代码获取并显示 如：<%=date%>
 * @param {} obj
 */
function RunTime(obj){
	//获取服务器时间和本地时间的差值
    var TimeDifference = new Date() - new Date(obj.innerHTML.replace(/-/g,"/"));
    (function (){
        var d = new Date();
        d.setTime(d.getTime()-TimeDifference);
        obj.innerHTML = d.toLocaleString();
        setTimeout(arguments.callee,1000);
    })();
}

//禁止点击右键
/**
if   (window.Event)   
    document.captureEvents(Event.MOUSEUP);   
function   nocontextmenu()   
{ 
  event.cancelBubble   =   true 
  event.returnValue   =   false; 
  return   false; 
} 
  
function   norightclick(e)   
{ 
  if   (window.Event)   
  { 
    if   (e.which   ==   2   ||   e.which   ==   3) 
      return   false; 
  } 
  else 
    if   (event.button   ==   2   ||   event.button   ==   3) 
    { 
      event.cancelBubble   =   true 
      event.returnValue   =   false; 
      return   false; 
    }
} 
document.oncontextmenu   =   nocontextmenu;     //   for   IE5+ 
document.onmousedown   =   norightclick;     //   for   all   others 
**/


/*
* js的contains方法
*string:原始字符串
*substr:子字符串
*isIgnoreCase:忽略大小写
*/
function contains(string,substr,isIgnoreCase){
	if(isIgnoreCase){
		string=string.toLowerCase();
		substr=substr.toLowerCase();
	}
	var startChar=substr.substring(0,1);
	var strLen=substr.length;
	for(var j=0;j<string.length-strLen+1;j++){
		if(string.charAt(j)==startChar){//如果匹配起始字符,开始查找
			if(string.substring(j,j+strLen)==substr){
				return true;
			}   
		}
	}
	return false;
}

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

/**
 * 
 * @param {} str 原字符串
 * @param {} sptr 被替换的字符串
 * @param {} sptr1 替换成的字符串
 * @return {}
 */
function ReplaceAll(str, sptr, sptr1){
	while (str.indexOf(sptr) >= 0){
		 str = str.replace(sptr, sptr1);
	}
	return str;
}

function formatNum(strNum) {
    if(strNum.length <= 3){  
        return strNum;  
    }  
    if(!/^(\+|-)?(\d+)(\.\d+)?$/.test(strNum)){  
        return strNum;  
	}  
	var a = RegExp.$1, b = RegExp.$2, c = RegExp.$3;  
    var re = new RegExp();  
    re.compile("(\\d)(\\d{3})(,|$)");  
    while(re.test(b)) {  
        b = b.replace(re, "$1,$2$3");  
    }
    return a +""+ b +""+ c;  
}

//

