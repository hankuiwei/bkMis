<%@ page language="java" import="java.util.*,com.zc13.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<title>楼层平面图展示</title>
		<meta http-equiv="pragma" content="no-cache" />
		<link href="<%=path%>/resources/css/menu.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/resources/css/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/resources/css/RptTable.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path%>/resources/js/util/util.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/main.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/RptTable.js" defer="defer"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/util/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/jquery.form.js"></script>
	<style type="text/css" mce_bogus="1">
 	     .mark {
	       position: absolute;
	       height: 30px;
	       font-size: 0px;
	       background:  #B5C7D1;
          }

       /**未出租*/
       .freemark {
	      position: absolute;
	      height: 30px;
	      font-size: 0px;
	      background:  #edeb32;
       }

      /**出租 */
      .duemark {
	      position: absolute;
	      height: 30px;
	      font-size: 0px;
	      background:  #efc1b1;
      }
      /**预租 */
      .destinemark {
      	  position: absolute;
	      height: 30px;
	      font-size: 0px;
	      background:  #40ffff;
      }
       </style>
       
       
       <script type="text/javascript">
       		<c:if test="${!message eq ''&&!message eq null}">
       			alert('${message}');
       		</c:if>
       </script>
	   <script type="text/javascript">
		   	function upload(){
		   	   var s = window.showModalDialog("zc13/bkmis/buildsManage/upload.jsp",window,"dialogWidth=300px;dialogHeight=260px;resizable=yes;center=1");
		       if(s != "" && s != null){
			       var s1 = s.substring(0,s.lastIndexOf("\\")+1);
			       //截取文件名
			       var s4 = s.substring(s1.length,s.length);
			       //截取文件夹
			       var s2=s1.substring(0,s1.length-5);
			       var s3 = s.substring(s2.length,s1.length-1);
			       //得到上传文件的相对路径
			       var s5 = "/" + s3 + "/" + s4;
			       //将截取好的文件路径，用于向数据库里保存之用
			       $("#mapUrl").attr("value",s5);
			       var url = document.getElementById("mapUrl").value;
			       /* 用于标识是否上传图片 */
			       document.planForm.action = "<%=path%>/build.do?method=addPlan&url="+url; 
			       document.planForm.submit();
		       }
		   }
		  //删除图片
		  $("#pic").ready(function(){
				$("#retur").live("click",function(){
					if(!window.confirm("确定要删除吗？"))  {return false;}
					var url = "<%=path%>/personnel.do";
					//alert("url : " + url);
					var imgurl = $("#imageurl").attr("value");
					//alert(imgurl);
					$.post(url,{method:"delPic",path:imgurl},function(data){
						if(data == 0){
							$("#imageurl").attr("src","");
							document.getElementById("imageurl").value = "";
							alert("删除成功!");
						}else{
							alert("删除失败!");
						}
					})
				})
		    });
           //根据输入信息查询平面图
		   function getRoomPlan() {
				document.planForm.action = "<%=path%>/build.do?method=getFloorPlan";
				document.planForm.submit();
		   }
 	    
 	    //动态在平面图上显示入驻的客户或者/可添加空房间或者/可添加入驻了客户的房间，显示入驻的客户
 	     /* 房间图的颜色 */

 	     var globalX = 0;
 	     var globalY = 0;
 	    //在图片上画图
 	    function addMark(p, x, y ,show, index,rStatus,freeflag,divwidth,chartId){  
             var div = document.createElement("div");  
             div.id = "mark" + index;
             /* 添加span的宽度设置 */
   	         div.style.width = divwidth;
   	         /* 根据房间的状态显示不同颜色的房间图 */
             if(rStatus == "<%=Contants.BELEASED%>") {  
       		    div.className = "duemark"; //已出租
             }else if(rStatus == "<%=Contants.OUTUSE%>"){
       	        div.className = "freemark";//未出租
             }else if(rStatus == "<%=Contants.DESTINE%>"){
       	        div.className = "destinemark";//已预租
             }else{
       		   div.className = "mark";
             }
             div.innerHTML = "<span style='font-size:8pt' id='axis"+index+"' ondblclick=\"deleteMark("+chartId+","+index+");\">"+show+"</span>";
             div.style.left = x + "px";  
             div.style.top = y + "px";
             div.title="";  
             p.appendChild(div);  
        }  
        
        //删除房间标记
        function deleteMark(chartId,divId){
        	if(window.confirm("您确定要删除此房间吗？")){
        		$.post("<%=path%>/build.do",{method:"deleteRoomAxis",chartId:chartId},function(data){
        			if(data.valueOf()=="1"){
        				alert("删除成功！");
		        		$("#mark"+divId).css({"display":"none"});
		        		$("#axis"+divId).css({"display":"none"});
	        		}else{
	        			alert("删除失败！");
	        		}
				});
        	}
        }
        
 	     /* 获得一个对象的在页面上的位置信息，用于设置添加房间的坐标信息 */
 	    function getLocation(obj){  
            var x = 0, y = 0;  
            while(obj){  
               x += obj.offsetLeft;  //当前对象到其上级层左边的距离. 
               y += obj.offsetTop;  //当前对象到其上级层顶部的距离        
               obj = obj.offsetParent; //当前对象的上级层对象
            }  
            return {x : x, y : y };  
         }
        /* 平面图滚动，获取滚动条滚动的相对的坐标信息 */ 
        function scrollEvent(){
                 var div1 = document.getElementById("div1");
                 div1.onscroll = function(){
                 globalX = div1.scrollLeft;
                 globalY = div1.scrollTop;
              }
        }
        /* 添加房间图 */
 	    function roomClientInfo(){
 	        var map = document.getElementById("map");
 	        //为div添加双击事件
 	        map.ondblclick = function(oEvent){
 	             oEvent = oEvent || event; 
 	             var mapContainer = document.getElementById("mapContainer");
 	             //获得mapContainer在页面上的位置信息
 	             var location = getLocation(mapContainer);
 	             var x = oEvent.clientX - location.x + globalX;  
	             var y = oEvent.clientY - location.y + globalY;
	             var floor = document.getElementById("floor").value;
	             var buildId = document.getElementById("id").value;
	             var type = document.getElementById("type").value;
	             //如果是楼层平面图
	             if(type=="<%=Contants.FLOOR_MAP%>"){
		             var str = window.showModalDialog("<%=path %>/build.do?method=goGetRoomClientInfo&floor="+floor+"&buildId="+buildId,"","dialogWidth=450px;dialogHeight=350px"); 
		             if(str != null){
		                var width = document.getElementById("floorPlan").width;
	   			  		var height = document.getElementById("floorPlan").height;			
						var planSize = width + "-" + height;
						var chartId = document.getElementById("chartId").value;
						$.post("<%=path%>/build.do",{method:"addRoomAxis",x:x,y:y,roomId:str[0],chartId:chartId,roomWidth:str[2],planSize:planSize},function(data){
						   addMark(mapContainer, x, y,"<center>"+str[5]+"<br />"+str[1]+ "</center>", data, str[4],"==",str[2],data.valueOf());
						});
		             } 
	             }
 	        }
 	    }
 	    
 	    //判断是否是ie浏览器
 	    function isMyIE(){
		    if(window.navigator.userAgent.toLowerCase().indexOf("msie")>=1){
		       	return true;
		    }else{
		        return false;
		    }
		}
 	    
 	    /* 读取房间图的信息，在页面上显示（查询楼层图时显示） */
 	    function getRoomMap(){
 	        /* 判断浏览器的类型,用于回传(回传的格式是xml的) */
 	        //判断是否IE
 	       
 	        var browserFlag = "fox";
		    if(isMyIE()) {
			   browserFlag = "ie";
		    }
 	        var chartId = document.getElementById("chartId").value;
 	        var width = document.getElementById("floorPlan").width;
   			var height = document.getElementById("floorPlan").height;
   			var psize = width + ";" + height;
   			$.post("<%=path%>/build.do",{method:"getRoomMap",browserFlag:browserFlag,chartId:chartId,psize:psize},function(data){
   			   var mapContainer = document.getElementById("mapContainer");
   			   var tempLength = $(data).find("item").length; 
   			   $(data).find("item").each(function(i){
   			         var x = $(this).find("XCoordinate").text();
   			         var y = $(this).find("YCoordinate").text();
   			         var clientName = $(this).find("clientName").text();
   			         var roomChartid = $(this).find("roomChartid").text();
   			         var mapWidth = $(this).find("mapWidth").text();
   			         var status = $(this).find("status").text();
   			         var roomCode = $(this).find("roomCode").text();
   			         addMark(mapContainer,x,y,"<center>"+ roomCode +"<br />"+clientName+ "</center>",roomChartid,status,"==",mapWidth,roomChartid);
   			   });
   			});
 	    }
         </script>
	</head>
	<body  >
		<form name="planForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="my_Rtoken" value="${my_Rtoken}" />
			<table width="99%" height="96%" border="0" align="center" cellpadding="0" cellspacing="0" style="layout: fixed">
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="14px"><img src="<%=path%>/resources/images/index_31.jpg" width="14" height="35"></td>
								<td width="315px" nowrap="nowrap" class="form_line" style="display:block">${build.ELp.lpName}${build.buildName}${floor }楼平面图</td>
								<td width="24px"><img src="<%=path%>/resources/images/index_34.jpg" width="24" height="35"></td>
								<td width="1080px" class="form_line2"></td>
								<td width="5"><img src="<%=path%>/resources/images/index_38.jpg" width="5" height="35"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr height="95%">
					<td class="menu_tab2" align="center" valign="top">
						<table width="100%" height="100%" border="0" cellspacing="0"
							cellpadding="0" height="100%">
							<tr>
								<td align="center">
									<!-- 查询条件start -->
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="10" colspan="9">
												<!-- 楼盘或楼栋id -->
												<input type="hidden" id="id" name="id" value="${id }">
												<input type="hidden" id="tablename" name="tablename" value="${tablename }">
												<input type="hidden" id="lpName" name="lpName" value="${build.ELp.lpName}" >
											</td>
										</tr>
										<tr>
											<td class="txt" nowrap="nowrap"><img src="<%=path%>/resources/images/fdj.gif" width="15" height="14" />查询条件：</td>
											<td align="right" nowrap="nowrap">楼层：</td>
											<td class="txt"> 
											  <select name="floor" style="width: 130px;" >
													<option value="">- - -请选择- - -</option>
													<c:forEach  begin="1" end="${build.floor}" varStatus="vs">
														<option value="${vs.count}" <c:if test="${vs.count == floor }">selected</c:if>>${vs.count}</option>
													</c:forEach>
												</select>
											</td>
											<td align="right" nowrap="nowrap">图片大小：</td>
											<td class="txt">
												<input type="radio" name="psize" id="psize" value="0"
													<c:if test="${psize=='0' }">checked</c:if> checked="checked">小
												<input type="radio" name="psize" id="psize" value="1"
													<c:if test="${psize=='1' }">checked</c:if>>大
											</td>
											<td align="right">
												<input type="button" id="focuson" onclick="getRoomPlan();" class="button" value="确定"><input type="button" id="upPic" onclick="upload();" class="button" value="上传">
										<tr>
											<td height="10" colspan="9"><input id="mapUrl" type="hidden" name="mapUrl"/></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr height="95%">
								<td valign="top">
									<!-- 表单内容区域 -->
									<table width="100%" height="100%" border="0" cellspacing="0"
										cellpadding="0" style="table-layout: fixed" height="100%">
										<tr height="95%">
											<td width="100%" height="100%">
												<div id="div1" class="RptDiv">
													<table class="RptTable" border="0" cellpadding="0"
														cellspacing="0">
														
															
																	<tr>
																		<td>
																		<c:choose>
																		<c:when test="${(!empty floorMap)}">
																			<div id="mapContainer">
																				<div id="map">
																		          <span style="font-size:8pt;width:28px;height:12px" class="duemark"></span><span style="padding-left:28px;">已出租</span>
																		          <span style="font-size:8pt;width:28px;height:12px" class="destinemark"></span><span style="padding-left:28px;">已预租</span>
																		          <span style="font-size:8pt;width:28px;height:12px" class="freemark"></span><span style="padding-left:28px;">未出租</span>
																		          <span style="font-size:8pt;width:28px;height:12px" class="mark"></span><span style="padding-left:28px;">装修中</span>
																					<img src="<%=path%>${floorMap.url}" id="floorPlan" width="${pictureSize.width }" height="${pictureSize.height }" />
																					<input type="hidden" name="chartId" id="chartId" value="${floorMap.id}">
																					<input type="hidden" name="type" id="type" value="${floorMap.type}">
																				</div>
																			</div>
																		</c:when>
																		<c:otherwise>
																			<center>
																				<br />
																				<font color="red">暂时没有平面显示图片！</font>
																				<br />
																			</center>
																		</c:otherwise>
																		</c:choose>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" class="head_form1" >已出租房间为${rentalnum }间。</td>
																	</tr>
																	<tr>
																		<td align="left" class="head_form1" >已预租房间为${destinenum }间。</td>
																	</tr>
																	<tr>
																		<td align="left" class="head_form1" >未出租房间为${nrentalnum }间。</td>
																	</tr>
																	<tr>
																		<td align="left" class="head_form1" >装修中房间为${infitmentnum }间。</td>
																	</tr>
																	<tr>
																		<td width="100%" colspan="4">
																			<table border="0" cellpadding="0" cellspacing="0" class="RptTable" id="metertb">
																				<tr height="20"><td></td></tr>
																				<tr>
																					<td align="left" class="head_one" colspan="5"><font size="4">房间客户入驻详细信息</font></td>
																				</tr>
																				<tr>
																					<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间号</th>
																					<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">客户名称</th>
																					<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">公司名称</th>
																					<th nowrap="nowrap" class="RptTableHeadCellLock" align="center">房间状态</th>
																				</tr>
																				<c:choose>
																					<c:when test="${empty roomflist}">
																						<tr align="center">
																							<td colspan="13" align="center"
																								class="head_form1">
																								<font color="red">对不起，暂时没有该楼层的房间的客户入驻信息!</font>
																							</td>
																						</tr>
																					</c:when>
																					<c:otherwise>
																						<c:forEach items="${roomflist}" var="f">
																							<tr onmouseover="this.className = 'hover';"  onmouseout="this.className = '';">
																								<td class="RptTableBodyCell" align="center" title="${f[0].roomCode}">${f[0].roomCode}&nbsp;</td>
																								<td class="RptTableBodyCell" align="center" title="${f[1].clientName}">${f[1].clientName}&nbsp;</td>
																								<td class="RptTableBodyCell" align="center" title="${f[1].unitName}">${f[1].unitName}&nbsp;
																								<td class="RptTableBodyCell" align="center" title="${f[1].status}">${f[0].status}&nbsp;
																						</c:forEach>
																					</c:otherwise>
																				</c:choose>
																			</table>
																		</td>
																	</tr>
															
														
													</table>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
					</table>
			 </table>
		</form>
	</body>
	<script type="text/javascript">
		/* 页面加载时调用 */
 	     window.onload = function(){
 	     	if(document.getElementById("chartId").value != null){
	 	        //添加房间图
	 		    roomClientInfo();  
	 		    //平面图滚动，获取滚动条滚动的相对的坐标信息
		        scrollEvent();
		        //读取房间图的信息，在页面上显示（查询楼层图时显示）
		        getRoomMap(); 
 	        }
 	    }
	</script>
</html>
