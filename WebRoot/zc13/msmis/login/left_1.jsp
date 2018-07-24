<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../../common/comTagLibs.jsp"%>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<head>
	    <title>left_1.jsp</title>
		<link href="../../../resources/css/css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
		function ReplaceAll(str, sptr, sptr1){
			while (str.indexOf(sptr) >= 0){
			   str = str.replace(sptr, sptr1);
			}
			return str;
		}
		
		
		function toggleMenu(el){//三级菜单的展开与隐藏控制
			var firm = document.getElementById("firm"+el).value;
			if(0 == firm){//0显示,显示状态下，点击则变为隐藏
				document.getElementById("firm"+el).value = "1";
				document.getElementById('menu_sub_'+el).style.display = "block";
				document.getElementById("firdiv"+el).className = "left_lift_Reduce";
			}else{//1隐藏
				document.getElementById("firm"+el).value = "0";
				document.getElementById('menu_sub_'+el).style.display = "none";
				document.getElementById("firdiv"+el).className = "menu_master";
			}
		}
		
		function toggleMenu1(el){//四级菜单的展开与隐藏控制
			var firm = document.getElementById("firm"+el).value;
			if(0 == firm){//0显示,显示状态下，点击则变为隐藏
				document.getElementById("firm"+el).value = "1";
				document.getElementById('menu_sub_'+el).style.display = "block";
				document.getElementById("firdiv"+el).className = "secondchoose2";
			}else{//1隐藏
				document.getElementById("firm"+el).value = "0";
				document.getElementById('menu_sub_'+el).style.display = "none";
				document.getElementById("firdiv"+el).className = "secondunchoose2";
			}
		}
		
		function fun_secondURL(url,el){//二级菜单的点击效果
			
			var count = document.getElementById("secondeIndex").value;
			for(var id=1; id<=count; id++){
				if(document.getElementById("secondtd"+id)!=null){
					if(id == el){
						document.getElementById("secondtd"+id).className="secondchoose";
					}else{
						document.getElementById("secondtd"+id).className="secondunchoose";
					}
				}
			}
			url = ReplaceAll(url,"&","@");
			form1.action = "../../../leftTreeJump.jsp?url="+url;
			//form1.action ="../../../"+url;
			form1.target="butFrame";//目标框架
			form1.submit();
		}
		</script>
		<style type="text/css">
		<!--
		#warp {
			width: 220px;
			margin: 0px auto; <!-- 导航位置（距顶部）-- > height : 100px;
			margin-left: 0px;
			text-align: left;
			cursor: pointer;
		}
		
		.menu_master {
			height:26px;
			font-size:14px;
			color:#FFFFFF;
			line-height:26px;   
		 	overflow:hidden;   
			background:url(../../../resources/images/lift_add1.gif);
			color:#000000;
		}
		
		.menu_master img {
			vertical-align: middle;
		}
		
		.sub_menu {
			display: none;
		}
		
		.sub_menu ul, .sub_menu{
			margin:0;
			padding:0;
		}
		
		.sub_menu a {
			margin: 0 0 0px 10px;
			padding: 3px 0 2px 25px;
			display: block;
			height: 10px;
			text-align: left;
			border-bottom: 0px;
			color: #000000;
			text-decoration: none;
		}
		.sub_menu a:link{
		 color: #000000;
		}
		
		.sub_menu a:active{
		 color: #0066ff;
		}
		
		.sub_menu a:visited{
		 color: #000000;
		}
		.sub_menu a:hover{
			font-weight:bold;
		}
		ul{
			font-size:13px;
			color:#000000;
			list-style-type:none;
		}
		
		li{
			width: auto;
			background:url(../../../resources/images/lift_jt.gif) no-repeat;
			margin-left:6px;					
		}
		.secondunchoose{
			height:22px; 
			*height:22px !important;
			*height:20px; 
		  	vertical-align:middle;
			background:url(../../../resources/images/lift_jt1.gif) no-repeat;
		}
		.secondchoose{
			height:22px; 
			*height:22px !important;
			*height:20px; 
			vertical-align:middle;
			color:#FFFFFF;
			background:url(../../../resources/images/lift_jt2.gif) repeat;
		}
		.secondchoose a:visited{
			color:#FFFFFF;
		}
		
		.secondunchoose2{
			height:22px; 
			*height:22px !important;
			*height:20px; 
		  	vertical-align:middle;
		  	padding-left:28px;
			background:url(../../../resources/images/nolines_plus.gif) no-repeat;
		}
		.secondchoose2{
			height:22px; 
			*height:22px !important;
			*height:20px; 
			vertical-align:middle;
			padding-left:28px;
			background:url(../../../resources/images/nolines_minus.gif) no-repeat;
		}
		-->
		</style>
	</head>
	<body bgcolor="#0B589E" >
		<div class="left_qj">
	 		<div id="warp"  style="height:460px;width:217px;overflow-x:hidden; overflow-y:scroll;">
	 		<form name="form1" method="post">
	 			<c:set var = "secondtd" value = "0" />
				<c:forEach var = "first" items = "${adminLoginForm.functionList}">
					<c:if test="${first.parentid == 1}">
						<input type="hidden" id="firm${first.functionid }" value="0">
						<div class="menu_master" id="firdiv${first.functionid }" onclick="toggleMenu('${first.functionid }')" align="center">
								${first.functionname}
						</div>
						<div id="menu_sub_${first.functionid }"  class="sub_menu">
							<table align="center" width="100%" border="0" cellpadding="0" cellspacing="0" >
								<c:forEach var = "second" items = "${adminLoginForm.functionList}" varStatus="state">
									<c:if test="${second.parentid == first.functionid}">
										<c:choose>
										<c:when test="${second.urlname != '' && second.urlname != null}">
											<tr>
												<td id="secondtd${state.index }"  class="secondunchoose" valign="middle">
													<a href="javascript:fun_secondURL('${second.urlname}','${state.index }')">
														${second.functionname}
													</a>
												</td>
											</tr>
										</c:when>
										<c:otherwise>
											<tr><td>
											<input type="hidden" id="firm${second.functionid }" value="0">
											<div>
													<table width="95%" align="center"><tr><td class="secondunchoose2" id="firdiv${second.functionid }" onclick="toggleMenu1('${second.functionid }')">
													${second.functionname}
													</td></tr></table>
											</div>
											<div id="menu_sub_${second.functionid }" class="sub_menu">
											<table align="center" width="85%" border="0" cellpadding="0" cellspacing="0" >
												<c:forEach var = "third" items = "${adminLoginForm.functionList}" varStatus="state">
													<c:if test="${third.parentid == second.functionid}">
														<tr>
															<td id="secondtd${state.index }"  class="secondunchoose" valign="middle">
																<a href="javascript:fun_secondURL('${third.urlname}','${state.index }')">
																	${third.functionname}
																</a>
															</td>
														</tr>
													</c:if>
												</c:forEach>
												</table>
											</div>
											</td>
											</tr>
										</c:otherwise>
										</c:choose>
										<!-- 下面的这个set用来得到最后一个二级菜单的index号，并把值给之后的id为secondeIndex的hidden
											这些都是为了实现fun_secondURL()函数的效果 -->
										<c:if test="${state.index>secondtd}">
											<c:set var = "secondtd" value = "${state.index }" />
										</c:if>
									</c:if>
								</c:forEach>
							</table>
						</div>
					</c:if>
				</c:forEach>
				<input type = "hidden" id="secondeIndex" value ="${secondtd }">
			</form>
			</div>
		</div>
	</body>
</html>
