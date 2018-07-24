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






//

