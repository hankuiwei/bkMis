/**
 * AJAX对象
 * @Author     dongdaokui
 */
function AjaxObject(){
	this.xmlHttp=null;
	this.parameters=new Array();
	if(window.XMLHttpRequest){
		// 为Mozilla等其它浏览器实例化AJAX对象
		try{
			this.xmlHttp=this.xmlHttp!=null?this.xmlHttp:new XMLHttpRequest();
		} catch(e){
			this.xmlHttp=null;
		}
		// 设置MIME类别
		if(this.xmlHttp!=null && this.xmlHttp.overrideMimeType){
			this.xmlHttp.overrideMimeType('text/xml');
		}
	} else if(window.ActiveXObject){
		// 为于基于IE内核的浏览器实例化AJAX对象
		try{
			this.xmlHttp=this.xmlHttp!=null?this.xmlHttp:new ActiveXObject("Msxml2.XMLHTTP");
		} catch(e){
			this.xmlHttp=null;
		}
		try{
			this.xmlHttp=this.xmlHttp!=null?this.xmlHttp:new ActiveXObject("Microsoft.XMLHTTP");
		} catch(e){
			this.xmlHttp=null;
		}
	}
	if(this.xmlHttp==null){
		alert("您的浏览器不支持AJAX!");
	}
}
AjaxObject.prototype.addParameter=function(_key,_value){
	_key=encodeURIComponent(_key);
	_value=encodeURIComponent(_value);
	this.parameters.push(new Array(_key,_value));
	return this;
}
AjaxObject.prototype.delParameters=function(){
	this.parameters.splice(0,this.parameters.length);
	return this;
}
AjaxObject.prototype.sendRequest=function(_url,_Async){
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
AjaxObject.prototype.getResponseBody=function(_url){
	this.sendRequest(_url,false);
	return this.xmlHttp.responseBody;	
}
AjaxObject.prototype.getResponseText=function(_url){
	this.sendRequest(_url,false);	
	return this.xmlHttp.responseText;
}
AjaxObject.prototype.getResponseXML=function(_url){
	this.sendRequest(_url,false);
	return this.xmlHttp.responseXML;	
}
AjaxObject.prototype.getResponseStream=function(_url){
	this.sendRequest(_url,false);
	return this.xmlHttp.responseStream;	
}
AjaxObject.prototype.handleResponseEvent=function(_url,_Async,_processFunc){
	this.xmlHttp.onreadystatechange=_processFunc;
	this.sendRequest(_url,_Async);
}