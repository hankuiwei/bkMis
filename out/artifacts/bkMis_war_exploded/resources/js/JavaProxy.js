/**
 * Js调用J2EE注册方法控件
 * Department: 胜利软件油田应用开发事业部
 * Author    : dongdaokui
 * Date      : 2010-11-27 8:35:43 AM
 * Version   : 1.0
 */
//=================================Declare Json, EventHandler, AjaxProxy, JavaProxy===============================
if (!this.JSON) { JSON = {}; }
(function () {
	function f(n) { return n < 10 ? '0' + n : n; }
	if (typeof Date.prototype.toJSON !== 'function') {
		Date.prototype.toJSON = function (key) { 
			var d=new Object();
			d.date=this.getDate(); d.day=this.getDay(); d.hours=this.getHours(); d.minutes=this.getMinutes(); d.month=this.getMonth();
			d.seconds=this.getSeconds(); d.time=this.getTime(); d.timezoneOffset=this.getTimezoneOffset(); d.year=this.getYear();
			return d;
		};
		String.prototype.toJSON=Number.prototype.toJSON=Boolean.prototype.toJSON=function (key) { return this.valueOf(); };
	}
	var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
		escapable=/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
		gap,indent,meta={'\b':'\\b','\t':'\\t','\n':'\\n','\f':'\\f','\r':'\\r','"':'\\"','\\':'\\\\'},rep;
	function quote(string) { escapable.lastIndex = 0; return escapable.test(string)?'"'+string.replace(escapable, function(a){var c=meta[a];return typeof c === 'string'?c :'\\u'+('0000'+a.charCodeAt(0).toString(16)).slice(-4);})+'"':'"'+string+'"'; }
	function str(key, holder) {
		var i,k,v,length,mind = gap,partial,value = holder[key];
	        if (value && typeof value === 'object' && typeof value.toJSON === 'function') { value = value.toJSON(key); }
	        if (typeof rep === 'function') { value = rep.call(holder, key, value); }
	        switch (typeof value) {
	        	case 'string': return quote(value);
	        	case 'number': return isFinite(value) ? String(value) : 'null';
	        	case 'boolean': return String(value);
	        	case 'null': return String(value);
	        	case 'object':
	        		if (!value) return 'null';
	        		gap += indent;
	        		partial = [];
	        		if (Object.prototype.toString.apply(value) === '[object Array]') {
	        			length = value.length;
	        			for (i = 0; i < length; i += 1) { partial[i] = str(i, value) || 'null'; }
	        			v = partial.length === 0 ? '[]' :gap ? '[\n' + gap + partial.join(',\n' + gap) + '\n'+mind + ']' :'[' + partial.join(',') + ']';
	        			gap = mind;
	        			return v;
	        		}
	        		if (rep && typeof rep === 'object') {
	        			length = rep.length;
	        			for (i = 0; i < length; i += 1) { k = rep[i]; if (typeof k === 'string') { v = str(k, value); if (v) { partial.push(quote(k) + (gap ? ': ' : ':') + v); } } }
	        		} else {
	        			for (k in value) { if (Object.hasOwnProperty.call(value, k)) { v = str(k, value); if (v) { partial.push(quote(k) + (gap ? ': ' : ':') + v); } } }
	        		}
	        		v = partial.length === 0 ? '{}' : gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}' : '{' + partial.join(',') + '}';
	        		gap = mind;
	        		return v;
	        }
	}
	if (typeof JSON.stringify !== 'function') {
		JSON.stringify = function (value, replacer, space) {
			var i; gap = ''; indent = '';
			if (typeof space === 'number') { for (i = 0; i < space; i += 1) { indent += ' '; } } else if (typeof space === 'string') { indent = space; }
			rep = replacer;
			if (replacer && typeof replacer !== 'function' && (typeof replacer !== 'object' ||typeof replacer.length !== 'number')) { throw new Error('JSON.stringify'); }
			return str('', {'': value});
		};
	}
	if (typeof JSON.parse !== 'function') {
		JSON.parse = function (text, reviver) {
			var j;
			function walk(holder, key) {
				var k, v, value = holder[key];
				if (value && typeof value === 'object') { for (k in value) { if (Object.hasOwnProperty.call(value, k)) { v = walk(value, k); if (v !== undefined) { value[k] = v; } else { delete value[k]; } } } }
				return reviver.call(holder, key, value);
			}
			cx.lastIndex = 0;
			if (cx.test(text)) { text = text.replace(cx, function (a) { return '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4); }); }
			if (/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
				j = eval('(' + text + ')');
				return typeof reviver === 'function' ? walk({'': j}, '') : j;
			}
			throw new SyntaxError('JSON.parse');
		};
	}
}());
if (!Object.prototype.toJSONString) {
	Object.prototype.toJSONString = function (filter) { return JSON.stringify(this, filter); };
	Object.prototype.parseJSON = function (filter) { return JSON.parse(this, filter); };
}
//===============================================End Json=====================================================
function handleObjectEvent(object,method){
      var args=[];
      for(var i=2;i<arguments.length;i++) args.push(arguments[i]);
      return function(){return object[method].apply(object,args);}
}
//===========================================End Event Handler================================================
function AjaxProxy(){
	this.xmlHttp=this.init();
	this.parameters=new Array();
}
AjaxProxy.prototype.init=function(){
	var o=null;
	if(window.XMLHttpRequest){
		try{o=o==null?new XMLHttpRequest():o;}catch(e){}
		if(o!=null && o.overrideMimeType){o.overrideMimeType('text/xml');}
	} else if(window.ActiveXObject){
		try{o=o==null?new ActiveXObject("Microsoft.XMLHTTP"):o;} catch(e){}
	}
	if(o==null){alert("您的浏览器不支持AJAX!");}
	return o;
}
AjaxProxy.prototype.toAbsoluteUrl=function(_url){
	var url=window.location.href;
	var tmp=url.substring(7).replace(/\\/g,"/").split("/");
	if(_url.indexOf("http://")==0){
		return _url;
	} else if(_url.indexOf("/")==0){
		return "http://"+tmp[0]+"/"+tmp[1]+_url;
	} else{
		var utl="";
		if(_url.indexOf("./")==0) _url=_url.substring(2);
		var a=_url.replace(/\\/g,"/").split("/");
		for(var i=0;i<a.length;i++){
			if(a[i]==".."&&tmp.length>3){
				try{tmp.pop();}catch(e){}
			} else{
				utl+="/"+a[i];
			}
		}
		try{tmp.pop();}catch(e){}
		for(var i=tmp.length-1;i>=0;i--){utl="/"+tmp[i]+utl;}
		return "http://"+utl.substring(1);
	}
}
AjaxProxy.prototype.addParameter=function(_key,_value){
	this.parameters.push([encodeURIComponent(_key),encodeURIComponent(_value)]);
	return this;
}
AjaxProxy.prototype.delParameters=function(){
	this.parameters.splice(0,this.parameters.length);
	return this;
}
AjaxProxy.prototype.sendRequest=function(_url,_Async){
	var _parameters="";
	var postType="application/x-www-form-urlencoded;charset=utf-8";
	for(var i=0;i<this.parameters.length;i++){
		_parameters+="&"+this.parameters[i][0]+"="+this.parameters[i][1];
	}
	this.xmlHttp.open("POST", _url?_url:"", _Async?_Async:false);
	this.xmlHttp.setRequestHeader("Content-Type",postType);
	if(_parameters.length>0){
		this.xmlHttp.send(_parameters.substr(1));
	} else{
		this.xmlHttp.send();
	}
}
AjaxProxy.prototype.getResponseText=function(_url){
	this.sendRequest(this.toAbsoluteUrl(_url),false);
	return this.xmlHttp.responseTEXT;
}
AjaxProxy.prototype.getResponseXML=function(_url){
	this.sendRequest(_url,false);
	return this.xmlHttp.responseXML;	
}
AjaxProxy.instance=new AjaxProxy();
AjaxProxy.getInstance=function(){
	return AjaxProxy.instance.delParameters();
}
//===========================================End AjaxProxy================================================
function JavaProxy(){
}
JavaProxy.prototype=AjaxProxy.getInstance();
JavaProxy.prototype.getInstance=function(beanId){
	var instance=window["JavaProxy_"+beanId.toUpperCase()]=function(){};
	this.delParameters();
	this.addParameter("JavaProxyCommand","getMethods");
	this.addParameter("JavaProxyBeanId",beanId);
	var rtn=this.getResponseText("/JavaProxyServlet");
	if(rtn=="JavaProxyError[0]") {
		alert("会话超时或没有权限，请重新登录！"); return;
	} else if(rtn=="JavaProxyError[1]") {
		alert("["+beanId+"]没有注册！"); return;
	}
	var methods=rtn.split(";");
	for(var i=0;i<methods.length;i++){
		instance[methods[i]]=handleObjectEvent(this,"getMethod",instance,beanId,methods[i]);
	}
	return instance;
}
JavaProxy.prototype.getMethod=function(instance,beanId,method){
	var args=instance[method].arguments;
	this.delParameters();
	this.addParameter("JavaProxyCommand","cmtMethod");
	this.addParameter("JavaProxyBeanId",beanId);
	this.addParameter("JavaProxyMethodId",method);
	this.addParameter("JavaProxyArgsNum",args.length);
	try{
		for(var i=0;i<args.length;i++){
			this.addParameter("JavaProxyArgs"+i,args[i].toJSONString());
		}
	} catch(e){
		alert("Js对象转Json失败！");
		return;
	}
	var rtn=this.getResponseText("/JavaProxyServlet");
	if(rtn=="JavaProxyError[0]") {
		alert("会话超时或没有权限，请重新登录！"); return;
	} else if(rtn=="JavaProxyError[2]"){
		alert("["+beanId+"."+method+"]参数个数与对应的注册方法不相等！"); return;
	} else if(rtn=="JavaProxyError[3]"){
		alert("["+beanId+"."+method+"]参数无法转为注册方法的参数类型！"); return; 
	} else if(rtn=="JavaProxyError[4]"){
		alert("["+beanId+"."+method+"]参数类型与对应的注册方法不相同！"); return;
	} else{
		try{return rtn.parseJSON();} catch(e){alert("Json转Js对象失败！"); return;}
	}
}
JavaProxy.instance=new JavaProxy();
JavaProxy.fetch=function(beanId){
	return JavaProxy.instance.getInstance(beanId);
}
//===========================================End JavaProxy================================================