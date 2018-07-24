
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



//选中的行变量
var selectedId = null;

//设置某个表的某一行被选中
function setSelected(tr,tab_id,tr_head,selectedId_){
	
	//循环表格中所有的行
	var tab = document.getElementById(tab_id);
	if(tab){
		for(var i=0;i<tab.rows.length;i++){
			var row = tab.rows[i];
			//除去表头
			if(row.id != tr_head){
				row.style.background = "white";
			}
		}
	}
	
	tr.style.background = "#FFFDC9";
	
	selectedId = selectedId_;

}

function getSelected(){
	if(null == selectedId){
		alert("请首先选择您要操作的行！");
	    return false;
	}else{
		return true;
	}
}

//显示下拉列表框的值
function getSelectionValue(obj,value){
	var options = document.getElementById(obj).options;
	if(options.length>0){
		for(var i=0;i<options.length;i++){
			var option = options[i];
			if(option.value == value){
				option.selected = "selected";
			}
		}
	}
}

//用空字符串代替空白符
function trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g,"");
} 

//简单验证手机号码
function checkmobile(code){
	var p="^[1][3,5,8][0-9]{9}$"
	return (code.match(p)!=null);
}

//验证电话号码
function isTel(tel){
    var reg="^([0-9]{3,4}[-]?[0-9]{7,8}|[0-9]{7,8})$";
    return (tel.match(reg)!=null);
}

//简单身份证验证
function checkpersonid(personid){
    var p="^[0-9]{6}(19|20)[0-9]{2}(((0[13578]|10|12)(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[12][0-9])))[0-9]{3}[0-9X]$"
	return (personid.match(p)!=null);
}

//验证是否是汉字或英文字母，一般用于判断姓名
function checkname(code){
	var p="^([\u4e00-\u9fa5]+|[A-Za-z]+)$"
	return (code.match(p)!=null);
}

//得到当前日期
function format() {
	var date = new Date();
	var now = date.getYear() + "-";
	if(date.getMonth()+1<10) {
		now += "0";
		now += date.getMonth()+1 + "-";
	} else {
		now += date.getMonth()+1 + "-";
	}
		
	if(date.getDate()<10) {
		now += "0" + date.getDate(); 
	} else {
		now += date.getDate();
	}	
	return now;
}

function isIE(){
    if(window.navigator.userAgent.toLowerCase().indexOf("msie")>=1){
       	return true;
    }else{
        return false;
    }
}

//is number
    function isLegal(str){
			var retstr = 0;
			if(null == str || "" == str){//is null
			
			}else{
				//is float
				var len = str.length;
				var dotNum=0;
				for(var i=0;i<len;i++){
					var oneNum = str.substring(i,i+1);
					
					if (oneNum=="."){
						dotNum++;
					}
					
					if (((oneNum<"0" || oneNum>"9") && oneNum!=".") || dotNum>1){
						retstr = "f";
						 break;  
					}else{
						retstr = str;
					}
				}
			}
			return retstr;
		}




	function checkAll(e,itemName){
		var a = document.getElementsByName(itemName);
		for(var i = 0; i<a.length; i++){
			a[i].checked = e.checked
		}
	}	

	//checked checkbox's count
	function checkednum(str) {
		boxs = document.getElementsByName(str);
		boxlength = boxs.length;
		count = 0;
		for(i=0;i<=boxlength-1;i++) {
			if(boxs[i].type=="checkbox"&&boxs[i].checked){
				count++;
			}
		}		
		return count;
	}
	
	//返回选中记录的value
	function checkedvalue(str) {
		var checkedvalue = null;
		boxs = document.getElementsByName(str);
		boxlength = boxs.length;
		for(i=0;i<=boxlength-1;i++) {
			if(boxs[i].type=="checkbox"&&boxs[i].checked){
				checkedvalue = boxs[i].value;
				break;
			}
		}		
		return checkedvalue;
	}
	
	function checkeditemindex(str) {
		var checkeditem = null;
		boxs = document.getElementsByName(str);
		boxlength = boxs.length;
		for(i=0;i<=boxlength-1;i++) {
			if(boxs[i].type=="checkbox"&&boxs[i].checked){
				checkeditem = i;
				break;
			}
		}		
		return checkeditem;
	}
	
	//tishi
	function isOK(str) {
		if(confirm(str)) {
			return true;
		} else {
			return false;
		}
	}
	
	//文本框字数限制
	function checkWordLen(textitem,wordlen){
		var words = textitem.value;
		if(words.length>wordlen) {
			alert('输入文字已超过限定字数');			
			textitem.value = words.substring(0,wordlen);
		}
	}
	
	//输入框文字长度限制
	function substr(str, len){        
	  if(!str || !len) {    
	    return '';    
	  }         
	  var a = 0;            
	  var i = 0;             
	  var temp = '';         
	  for (i=0;i<str.length;i++){   
	    if (str.charCodeAt(i)>255){            
	      a+=2;   
	    }else{   
	      a++;            
	    }                   
	    if(a > len) {    
	      return temp;    
	    }                    
	    temp += str.charAt(i);        
	  }          
	  return str;    
	}   
	
	
	function MM_swapImgRestore() { //v3.0
	  var i,x,a=document.MM_sr; 
	  for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) 
	  x.src=x.oSrc;
	}
function MM_preloadImages() { //v3.0
  var d=document; 
  if(d.images){ 
  	if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; 
    for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ 
    	d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];
    }
   }
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

//关闭窗口
function closeWindow(){
window.opener=null;
window.open("","_self");
window.close();  
}


//检查网址是否合法
function IsURL(str_url){ 
var strRegex = "^((https|http|ftp|rtsp|mms)?://)" 
+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@ 
+ "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184 
+ "|" // 允许IP和DOMAIN（域名） 
+ "([0-9a-z_!~*'()-]+\.)*" // 域名- www. 
+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名 
+ "[a-z]{2,6})" // first level domain- .com or .museum 
+ "(:[0-9]{1,4})?" // 端口- :80 
+ "((/?)|" // a slash isn’t required if there is no file name 
+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$"; 
var re=new RegExp(strRegex); 
//re.test() 
if (re.test(str_url)){ 
return (true); 
}else{ 
return (false); 
} 
} 
