var showTimeSysTime = 1253580089000;
function show_time ()
{
	//var obj = $("#showTimeContainer");
	var obj = document.getElementById("showTimeContainer");
	var objDate = new Date ();
	showTimeCurTime = Date.UTC ( objDate.getYear (), objDate.getMonth (), objDate.getDate (), objDate.getHours (), objDate.getMinutes (), objDate.getSeconds () ) + showTimeOffset;
	//showTimeCurTime = Date.UTC ( objDate.getYear (), objDate.getMonth (), objDate.getDate (), objDate.getHours (), objDate.getMinutes ());
	//var objDate = new Date ( showTimeCurTime );
	
	var year = objDate.getFullYear().toString();
	var month = objDate.getMonth() + 1;
	month = month.toString ();
	var day = objDate.getDate().toString();
	var hours = objDate.getHours().toString();
	var minutes = objDate.getMinutes().toString();
	var seconds = objDate.getSeconds().toString();
	if ( month.length < 2 ) month = '0' + month;
	if ( day.length < 2 ) day = '0' + day;
	if ( hours.length < 2 ) hours = '0' + hours;
	if ( minutes.length < 2 ) minutes = '0' + minutes;
	if ( seconds.length < 2 ) seconds = '0' + seconds;
	obj.innerHTML = year + '/' + month + '/' + day + ' ' + hours + ':' + minutes + ':' + seconds + ' ';
	var showTimeID = setTimeout ( show_time, 1000 ); 
}

function init_time ()
{
	var objDate = new Date ();
	var showTimeCurTime = Date.UTC ( objDate.getYear (), objDate.getMonth (), objDate.getDate (), objDate.getHours (), objDate.getMinutes (), objDate.getSeconds () );
	showTimeOffset = showTimeSysTime - showTimeCurTime;
	showTimeOffset = 0;
	document.write ( "<span id='showTimeContainer'></span>" );
	show_time ();
}

var showTimeOffset;
init_time ();
