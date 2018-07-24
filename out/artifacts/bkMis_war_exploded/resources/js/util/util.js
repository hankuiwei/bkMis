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
	
	
//得到选中复选框的个数
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
	
	function isOK(str) {
		if(confirm(str)) {
			return true;
		} else {
			return false;
		}
	}