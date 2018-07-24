/**
* 在线编辑单元格效果
*
*/

//增加响应Enter。当敲下enter键时，系统会默认执行点击id为submit的那个button 
    document.onkeydown = function(evt){
		var evt = window.event?window.event:evt;
		var src = evt.srcElement||ev.target;
		if(evt.keyCode==13)
		{
			document.all('focuson').focus(); //指向
			document.all('focuson').click();
		}
		//防止按回退键(backspace)返回上级页面
		if(evt.keyCode==8){//如果按的是回退键(backspace)
			if (src.readOnly || !(src.tagName == "INPUT" || src.tagName == "TEXTAREA")) {
                return false;
            }
		}
	}

//初始化表单的宽度和高度
function tableWH(a,b) {
		var w =  window.screen.width;
		var h =  window.screen.height;
		if(a=="" || a == undefined){
			a=3.6;
		}if(b=="" || b == undefined){
			b=2.5;
		}
		//document.getElementById("div1").style.width = w/a;
		document.getElementById("div1").style.height = h/b;
}
		
function handleWindowEvent(method){
      var args=[];
      for(var i=1;i<arguments.length;i++) args.push(arguments[i]);
      return function(){return window[method].apply(window,args);}
}
function getRowObjByInnerObj(innerObj){
	var rowObj=innerObj;
	while(rowObj!=null && rowObj!=undefined && typeof(rowObj)!=undefined){
			if(/tr/gi.test(rowObj.tagName) && /RptTableBody/gi.test(rowObj.parentElement.className)){
				break;
			}
			rowObj=rowObj.parentElement;
	}
	return rowObj;
}
function getCellObjByInnerObj(innerObj){
	var cellObj=innerObj;
	while(cellObj!=null && cellObj!=undefined && typeof(cellObj)!=undefined){
			if(/td/gi.test(cellObj.tagName) && /RptTableBodyCell/gi.test(cellObj.className)){
				break;
			}
			cellObj=cellObj.parentElement;
	}
	return cellObj;
}
function doMouseOverRptTable(){
	var obj=event.srcElement, div=null;
	try{div=obj.childNodes[0];}catch(e){}
	if(/^div$/gi.test(obj.tagName)&&/RptTableCellClip/gi.test(obj.className)){
		if(!/^[( )(&nbsp;)]*$/gi.test(obj.innerText)) obj.title=obj.innerText;
	} else if(/^td$/gi.test(obj.tagName)&&/RptTableCellClip/gi.test(div.className)){
		if(!/^[( )(&nbsp;)]*$/gi.test(obj.innerText)) obj.title=obj.innerText;
	}
}
function doMouseClickRptTable(){
	var obj=event.srcElement, div=null;
	try{div=obj.childNodes[0];}catch(e){}
    if(!/RptTableBodyCell/gi.test(obj.className)&&!/RptTableCellClip/gi.test(obj.className)){
        return;
    }
    var view=document.getElementById("InfoViewerDiv"),area=null;
    try{area=view.childNodes[0];}catch(e){}
    if(view==null||view==undefined){
        view=document.createElement('div');
        view.id="InfoViewerDiv";
        view.name="InfoViewerDiv";
        with(view.style){position='absolute';zIndex='0';}
        area=document.createElement('textarea');
        area.className='RptTableCellTextAreaEditor';
        view.appendChild(area);
        document.body.appendChild(view);
    }
    if(/div/gi.test(obj.tagName)&&/RptTableCellClip/gi.test(obj.className)) obj=obj.parentElement;
    var absPos=getAbsolutePosition(obj,true);
    with(view.style){left=absPos[0];top=absPos[1];}
    area.value = obj.innerText;
}
function addRptTableRow(cellObj,addRowCallBack,addRowCellCallBack){
	var rowObj=getRowObjByInnerObj(cellObj),tbody=rowObj.parentElement;
	var index=0;
	for(var i=0;i<tbody.childNodes.length;i++){
		if(tbody.childNodes[i]==rowObj) index=i;
	}
	var tr=tbody.insertRow(index+1);
	if(addRowCallBack){
		handleWindowEvent(addRowCallBack,rowObj,tr,index+1)();
	}
	for(var i=0;i<rowObj.childNodes.length;i++){
		var td=tr.insertCell();
		td.className=rowObj.childNodes[i].className;
		td.ondblclick=rowObj.childNodes[i].ondblclick;
		var div=document.createElement("div");
		div.className="RptTableCellClip";
		td.appendChild(div);		
		if(addRowCellCallBack){
			handleWindowEvent(addRowCellCallBack,rowObj,td,div,index+1,i)();
		}
	}
}
function getRowSpan(rowObj,cellIndex){
	var rowSpan=1,cellObj=rowObj.childNodes[cellIndex];
	if(cellObj.rowSpan!=undefined){
		return cellObj.rowSpan;
	}
	return rowSpan;
}
function getSibling(rowObj,cellIndex,ctrl){
	var tmpObj=rowObj;
	while(tmpObj!=null){
		tmpObj=(ctrl==true)?tmpObj.nextSibling:tmpObj.previousSibling;
		if(tmpObj==null||typeof(tmpObj)==undefined) break;
		var cellObj=tmpObj.childNodes[cellIndex];
		if(cellObj.isRowSpaned!="true") break;
		if(tmpObj.rowSpan!=undefined && tmpObj.rowSpan>1) break;
	}
	return tmpObj;
}
function setRptTableRowSpan(cellObj,prevNum,nextNum,rowSpanCellIndexes){
	var tmpRowObj=cellObj.parentElement;
	if(tmpRowObj.childNodes[rowSpanCellIndexes[0]].isRowSpaned=="true"){
		tmpRowObj=getSibling(tmpRowObj,rowSpanCellIndexes[0],false);
	}
	for(var i=0;i<prevNum;i++){
		tmpRowObj=getSibling(tmpRowObj,rowSpanCellIndexes[0],false);
	}
	var curTopRowObj=tmpRowObj;
	for(var k=0;k<rowSpanCellIndexes.length;k++){
		tmpRowObj=curTopRowObj;
		var tmpCell=tmpRowObj.childNodes[rowSpanCellIndexes[k]];
		for(var i=0;i<prevNum+nextNum;i++){
			tmpRowObj=getSibling(tmpRowObj,rowSpanCellIndexes[k],true);
			tmpCell.rowSpan+=getRowSpan(tmpRowObj,rowSpanCellIndexes[k]);
			tmpRowObj.childNodes[rowSpanCellIndexes[k]].isRowSpaned="true";
			tmpRowObj.childNodes[rowSpanCellIndexes[k]].style.display="none";
		}
	}
}
function delRptTableRow(cellObj,idCellIndex,rowSpanCellIndexes){
	var rowObj=getRowObjByInnerObj(cellObj),tbody=rowObj.parentElement;
	var curId=rowObj.childNodes[idCellIndex?idCellIndex:0].innerText;
	if(/^[( )(&nbsp;)]*$/gi.test(curId)==true){
		rowObj.removeNode(true);
		return;
	}
	if(rowSpanCellIndexes==null||rowSpanCellIndexes.length==0){
		rowObj.removeNode(true);
		return;
	}
	var tmpRowObj=rowObj;
	var tmpCellObj=tmpRowObj.childNodes[rowSpanCellIndexes[0]];
	if(tmpCellObj.rowSpan>1){
		for(var i=0;i<rowSpanCellIndexes.length;i++){
			tmpCellObj=tmpRowObj.childNodes[rowSpanCellIndexes[i]];
			var rowSpan=tmpCellObj.rowSpan;
			tmpCellObj=tmpRowObj.nextSibling.childNodes[rowSpanCellIndexes[i]];
			tmpCellObj.rowSpan=rowSpan-1;
			tmpCellObj.isRowSpaned=tmpCellObj.rowSpan>1?"true":"false";
			tmpCellObj.style.display="block";
		}
	} else if(tmpCellObj.isRowSpaned=="true"){
		tmpRowObj=getSibling(tmpRowObj,rowSpanCellIndexes[0],false);
		for(var i=0;i<rowSpanCellIndexes.length;i++){
			tmpCellObj=tmpRowObj.childNodes[rowSpanCellIndexes[i]];
			tmpCellObj.rowSpan=tmpCellObj.rowSpan-1;
		}
	}
	cellObj.parentElement.removeNode(true);
	/*
	for(var i=0;i<tbody.childNodes.length;i++){
		var tmpId=tbody.childNodes[i].childNodes[idCellIndex?idCellIndex:0].innerText;
		if(tmpId.indexOf(curId)==0){
			if(tbody.childNodes[i].removeNode(true)) i--;
		}
	}
	*/
}
function getRowCellValue(cellObject,format){
	var newtxt="";
	try{
		var obj=cellObject.childNodes[0];
		if(/select/gi.test(obj.tagName)){
			if(format=="txt"){
				var selectedIndex=obj.selectedIndex==-1?0:obj.selectedIndex;
				newtxt=obj.childNodes[selectedIndex].innerText;
			} else if(format=="val"){
				newtxt=obj.value;
			}
		} else {
			newtxt=obj.value;
		}
	} catch(e){
		newtxt=cellObject.innerText;
	}
	return newtxt==undefined?cellObject.innerText:newtxt;
}
function bindRowEditor(rowObject,doSetRowEditorCallBack,doRowChangeCallBack,escapeIndexes){
	try{
		if(/div/gi.test(rowObject.tagName)) rowObject=rowObject.parentElement.parentElement;
		if(/td/gi.test(rowObject.tagName)) rowObject=rowObject.parentElement;
		if(/tr/gi.test(rowObject.tagName)==false) return;
		var oldtxtArray=[];
		for(var i=0;i<rowObject.childNodes.length;i++){
			oldtxtArray[i]=getRowCellValue(rowObject.childNodes[i],"txt");
		}
		handleWindowEvent(doSetRowEditorCallBack,rowObject)();
		document.onclick=handleWindowEvent("doRowChangeFunc",rowObject,doRowChangeCallBack,oldtxtArray,escapeIndexes);
	} catch(e){
	}
}
function isEscapeIndexes(index,escapeIndexes){
	for(var i=0;i<escapeIndexes.length;i++){
		if(i==index) return true;
	}
	return false;
}
function doRowChangeFunc(rowObject,doRowChangeCallBack,oldtxtArray,escapeIndexes){
	try{
		var rowValueArray=[],object=event.srcElement;
		while(object!=null){
			if(object==rowObject) return;
			object=object.parentElement;
		}
		var isRowValuesChanged=false;
		for(var i=0;i<rowObject.childNodes.length;i++){
			if(isEscapeIndexes(i,escapeIndexes)) continue;
			var cell=rowObject.childNodes[i];
			var curtxt=getRowCellValue(cell,"txt");
			curtxt=curtxt==undefined?cell.innerText:curtxt;
			if(curtxt!=oldtxtArray[i]) isRowValuesChanged=true;
			rowValueArray[i]=getRowCellValue(cell,"val");
		}
		if(isRowValuesChanged==false){
			for(var i=0;i<rowObject.childNodes.length;i++){
				if(isEscapeIndexes(i,escapeIndexes)) continue;
				oldtxtArray[i]=/^[( )(&nbsp;)]*$/gi.test(oldtxtArray[i])?"&nbsp;":oldtxtArray[i];
				rowObject.childNodes[i].innerHTML=oldtxtArray[i];
			}
			document.onclick=null;
			return;
		}
		if(handleWindowEvent(doRowChangeCallBack,rowObject,rowValueArray)()==true){
			for(var i=0;i<rowObject.childNodes.length;i++){
				if(isEscapeIndexes(i,escapeIndexes)) continue;
				var cell=rowObject.childNodes[i],txt=getRowCellValue(cell,"txt");
				cell.innerHTML=/^[( )(&nbsp;)]*$/gi.test(txt)?"&nbsp;":txt;
			}
		} else{
			for(var i=0;i<rowObject.childNodes.length;i++){				
				if(isEscapeIndexes(i,escapeIndexes)) continue;
				oldtxtArray[i]=/^[( )(&nbsp;)]*$/gi.test(oldtxtArray[i])?"&nbsp;":oldtxtArray[i];
				rowObject.childNodes[i].innerHTML=oldtxtArray[i];
			}
		}
		document.onclick=null;
	} catch(e){
	}
}
function setRowCellEditor(cellobj,editor){
	try{
		var args=[];
		for(var i=2;i<arguments.length;i++) args.push(arguments[i]);
		cellobj.onclick=function(){return window[editor].apply(window,args)};
		cellobj.click();
		cellobj.onclick=null;
	} catch(e){
	}
}
function setCellEditor(cellobj,editor){
	try{
		var args=[];
		for(var i=2;i<arguments.length;i++) args.push(arguments[i]);
		cellobj.ondblclick=function(){return window[editor].apply(window,args)};
	} catch(e){
	}
}
function bindRowInputEditor(){
	bindInputEditor(false);
}
function bindRowSelectEditor(txtArray,valArray){
	bindSelectEditor(false,txtArray,valArray);
}
function bindCellInputEditor(callback,column){
	bindInputEditor(true,callback,column);
}
function bindCellTextAreaEditor(callback,column){
	bindTextAreaEditor(true,callback,column);
}
function bindCellSelectEditor(txtArray,valArray,callback,column){
	bindSelectEditor(true,txtArray,valArray,callback,column);
}
function bindInputEditor(oncell,callback,column){
	try{
		var cellobj=event.srcElement,oldtxt=cellobj.innerText;
		oldtxt=/^[( )(&nbsp;)]*$/gi.test(oldtxt)?"&nbsp;":oldtxt;
		var input=document.createElement('input');
		input.className='RptTableCellInputEditor';
		if(oncell==true){
			input.onblur=handleWindowEvent("doInputChange",cellobj,callback,oldtxt,column);
		}
		with(cellobj){innerHTML='';appendChild(input);}
		input.value=oldtxt=="&nbsp;"?"":oldtxt;
		input.focus();
	} catch(e){
	}
}
function bindTextAreaEditor(oncell,callback,column){
	try{
		var cellobj=event.srcElement,oldtxt=cellobj.innerText;
		oldtxt=/^[( )(&nbsp;)]*$/gi.test(oldtxt)?"&nbsp;":oldtxt;
		var div=document.createElement('div'),absPos=getAbsolutePosition(cellobj);
        with(div.style){position='absolute';zIndex='100';border='1px solid gray';}
        with(div.style){left=absPos[0];top=absPos[1];width="100%";}
		var area=document.createElement('textarea');
		area.className='RptTableCellTextAreaEditor';
		if(oncell==true){
			area.onblur=handleWindowEvent("doTextAreaChange",cellobj,callback,oldtxt,column);
		}
        div.appendChild(area);
		with(cellobj){innerHTML='';appendChild(div);}
		area.value=oldtxt=="&nbsp;"?"":oldtxt;
		area.focus();
	} catch(e){
	}
}
function bindSelectEditor(oncell,txtArray,valArray,callback,column){
	try{
		var cellobj=event.srcElement,oldtxt=cellobj.innerText;
		oldtxt=/^[( )(&nbsp;)]*$/gi.test(oldtxt)?"&nbsp;":oldtxt;
		var select=document.createElement('select');
		select.className='RptTableCellEditor';
		if(oncell==true){
			var doSelectChange=handleWindowEvent("doSelectChange",cellobj,callback,oldtxt,column);
			document.onclick=select.onchange=doSelectChange;
		}
		var selectedValue=0;
		for(var i=0;i<txtArray.length;i++){
			var option=document.createElement('option');
			with(option){value=valArray[i];innerHTML=txtArray[i];}
			if(txtArray[i]==oldtxt) selectedValue=valArray[i];
			select.appendChild(option);
		}
		with(cellobj){innerHTML='';appendChild(select);}
		select.value=selectedValue=="&nbsp;"?"":selectedValue;
		select.focus();
	} catch(e){
	}
}
function doInputChange(cellobj,callback,oldtxt,column){
	try{
		var newtxt=cellobj.childNodes[0].value;
		newtxt=/^[ ]*$/gi.test(newtxt)?"&nbsp;":newtxt;
		if(newtxt==oldtxt) {
			cellobj.innerHTML=oldtxt;
			return;
		}
		cellobj.innerHTML=callback(cellobj,newtxt,column)==true?newtxt:oldtxt;
	} catch(e){
	}
}
function doTextAreaChange(cellobj,callback,oldtxt,column){
	try{
		var newtxt=cellobj.childNodes[0].childNodes[0].value;
		newtxt=/^[ ]*$/gi.test(newtxt)?"&nbsp;":newtxt;
		if(newtxt==oldtxt) {
			cellobj.innerHTML=oldtxt;
			return;
		}
		cellobj.innerHTML=callback(cellobj,newtxt,column)==true?newtxt:oldtxt;
	} catch(e){
	}
}
function doSelectChange(cellobj,callback,oldtxt,column){
	try{
		if(/click/gi.test(event.type)&&/select/gi.test(event.srcElement.tagName)==true) return;
		var select=cellobj.childNodes[0];
		var selectedIndex=select.selectedIndex==-1?0:select.selectedIndex;
		var selopt=select.childNodes[selectedIndex].innerText;
		if(selopt==oldtxt) {
			cellobj.innerHTML=oldtxt;
			return;
		}
		cellobj.innerHTML=callback(cellobj,select.childNodes[selectedIndex].value,column)==true?selopt:oldtxt;
	} catch(e){
	}
}
function getAbsolutePosition( ob ){
    if(!ob){return null;};
    var mendingOb = ob, mendingLeft = mendingOb.offsetLeft,mendingTop = mendingOb.offsetTop;
    while( mendingOb != null && mendingOb.offsetParent != null && mendingOb.offsetParent.tagName!="BODY" ){
        mendingLeft += mendingOb.offsetParent.offsetLeft;
        mendingTop += mendingOb.offsetParent.offsetTop;
        mendingOb = mendingOb.offsetParent;
    }
    mendingOb = ob;
    while(mendingOb!=null && mendingOb.className!="RptTable"){
         mendingOb=mendingOb.parentElement;
    }
    var subLeft=0,subTop=0;
    while( mendingOb != null && mendingOb.offsetParent != null && mendingOb.offsetParent.tagName!="BODY" ){
        subLeft += mendingOb.offsetParent.offsetLeft;
        subTop += mendingOb.offsetParent.offsetTop;
        mendingOb = mendingOb.offsetParent;
    }
    return [mendingLeft-subLeft,mendingTop-subTop];
}