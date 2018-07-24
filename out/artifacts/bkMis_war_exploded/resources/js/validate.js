//各种验证
//author：董道奎

	function isPhone(s){//电话号码

		var isPhone2=/^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;
		if(!isPhone2.test(s)){
			return false;
		}else{
			return true;
		}
	}
	function isEmail(s){//邮箱
		var isEmail2=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		if(!isEmail2.test(s)){
			return false;
		}else{
			return true;
		}
	}
	function isNumberZ(s){//非负整数
		var regStrL=/^\s+/g;//正则表达式去除字符串左侧空格
		var regStrR=/\s+$/g;//正则表达式去除字符串右侧空格	
		var str=(s.replace(regStrL,"")).replace(regStrR,"");
		var isnumber=/^[\+]?\d+$/;
		if(!isnumber.test(str)){
			return false;
		}else {
			return true;
		}
	}
	function isNumber(s){//非负数
		var regStrL=/^\s+/g;//正则表达式去出字符串左侧空格
		var regStrR=/\s+$/g;//正则表达式去出字符串右侧空格	
		var str=(s.replace(regStrL,"")).replace(regStrR,"");
		var isnumber=/^[\+]?\d+(\.\d+)?$/;
		if(!isnumber.test(str)){
			return false;
		}else {
			return true;
		}
	}
 
   
   function IsIdCard(number) {//身份证号
   
		var resx=/^\d{15}(\d{2}[A-Za-z0-9])?$/;
		if(!resx.test(number)){
			return false;
		}else{
			return true ;
		}
	}
	function isNormalName(user){//判断当前变量只允许由“汉字、数字，字母、AT符、DOT符、减号和下划线”七种字符组成，其他任何字符均不被允许，且只能以汉字、数字、字母开头；


	  var patten =/^[0x4E00-0x9FA5|\w|0-9]*[@|\.|\-|_]*[0x4E00-0x9FA5|\w|0-9|@|\.|\-|_]*$/i;
	  
	  if(!patten.test(user))
	  {
	   return false;
	  }
	  return true;
		 
	}
	function VS_FV_Chinese(chinese){//中文
		var resx=/^[\u0391-\uFFE5]+$/;
		
		if(!resx.test(chinese)){
			return false;
		}else{
			return true;
		}
	}
	