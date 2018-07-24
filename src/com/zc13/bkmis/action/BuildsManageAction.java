package com.zc13.bkmis.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.BuildForm;
import com.zc13.bkmis.form.RoomForm;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.EFloorMap;
import com.zc13.bkmis.mapping.EFloorMapCoordinate;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.EMeter;
import com.zc13.bkmis.mapping.ERoomClient;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.service.IBuildsManageService;
import com.zc13.bkmis.service.ILpManageService;
import com.zc13.bkmis.service.IRoomManageService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Constant;
import com.zc13.util.Contants;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;
/**
 * @author 侯哓娟
 * Date：Nov 20, 2010
 * Time：2:17:07 PM
 */
public class BuildsManageAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	
	private IBuildsManageService ibuildsManageService = null;
	private ILpManageService ilpManageService = null;
	private IRoomManageService iroomManageService = null;
	/** 得到service */
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		ibuildsManageService = (IBuildsManageService)getBean("ibuildsManageService");
		ilpManageService = (ILpManageService)getBean("ilpManageService");
		iroomManageService = (IRoomManageService)getBean("iroomManageService");
	}

	/**
	 * 导出楼栋列表excel
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportBuildExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {
			BuildForm buildForm = (BuildForm)form;
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
			buildForm.setLpId(Integer.valueOf(lpid));
			
			//list中存放的就是当前页面上显示的所有数据
			List list = ibuildsManageService.getBuild(buildForm);
			//表头
			String[] cellHeader = Constant.EXCEL_BUILD_HEADER;
			String[] cellValue = Constant.EXCEL_BUILD_VALUE;
			//定义文件名
			String sheetName = "楼栋信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list,sheetName,cellHeader,cellValue,new EBuilds());
			response.setBufferSize(100*1024);
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "楼栋信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出楼栋excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/** 得到楼栋信息列表
	 * 添加分页 */
	public ActionForward getBuildsList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		List<EBuilds> buildslist = null;
		List<ELp> lpList = null;
		BuildForm buildForm = (BuildForm)form;
		String buildName = buildForm.getBuildName();
		try{
			//将楼盘id存放到page，这样在点击添加楼栋的时候，默认所属就是当前用户所在的楼盘。
			//楼盘id的第二个地方就是查询的时候，要按用户的楼盘id过滤
			setLogParam(request);
			buildForm.setLpId(lpId);
			buildslist = ibuildsManageService.getBuilds(buildForm);
			if(buildName != null){
				request.setAttribute("buildName", buildName);
			}
			/* 用于获取楼盘列表 */
			lpList = ilpManageService.getLp();
			//取总条数
			int totalcount = ibuildsManageService.queryCounttotal(buildForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == buildslist || buildslist.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/build.do?method=getBuildsList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/build.do?method=getBuildsList", Integer.parseInt(GlobalMethod.NullToParam(buildForm.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(buildForm.getCurrentpage(),"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
		}catch(Exception e){
			logger.error("楼栋信息查询失败!BuildsManageAction.getBuildsList().详细信息：" + e.getMessage());
			throw new BkmisWebException("楼栋信息查询失败，BuildsManageAction.getBuildsList()!",e);
		}
		request.setAttribute("lpList", lpList);
		request.getSession().setAttribute("buildslist", buildslist);
		request.setAttribute("size", buildslist.size()); // list的长度，以便导出excel的时候判断有无数据
		return mapping.findForward("buildsList");
	}
	
	/** 添加楼栋 */
	public ActionForward addBuild(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		BuildForm buildForm = (BuildForm)form;
		try{
			//为添加日志做准备
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			buildForm.setUserName(userName);
			
			ibuildsManageService.addBuild(buildForm);
			
			//writeLog(userName, "添加楼栋", "所添加的楼栋ID为" + buildForm.getLpId() + "信息", Contants.BUILD);
			
		}catch(Exception e){
			logger.error("楼栋信息添加失败!BuildsManageAction.addBuild().详细信息：" + e.getMessage());
			throw new BkmisWebException("楼栋信息添加失败，BuildsManageAction.addBuild()!",e);
		}
		request.setAttribute("flag", true);
		return mapping.findForward("add");
				
	}
	
	/** 检查楼栋编号是否已经存在 */
	public ActionForward checkBuildCode(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		BuildForm buildForm = (BuildForm)form;
		PrintWriter out = null;
		try{
			boolean arg = ibuildsManageService.checkBuildCode(buildForm);
			out = response.getWriter();
			if(arg){
				out.print(true);
			}else{
					out.print(false);
			}
		}catch(Exception e){
			logger.error("楼栋名称检测失败!BuildsManageAction.checkBuildName.详细信息：" + e.getMessage());
			throw new BkmisWebException("楼栋名称检测失败,BuildsManageAction.checkBuildName!",e);
		}
		return null;
	}
	
	/** 检查楼栋名称是否已经存在 */
	public ActionForward checkBuildName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		BuildForm buildForm = (BuildForm)form;
		String forward =request.getParameter("forward");
		PrintWriter out = null;
		try{
			boolean arg = ibuildsManageService.checkBuildName(buildForm);
			out = response.getWriter();
			if(arg){
				out.print(true);
			}else{
				if("addBuild".equals(forward)){
					out.print(false);
				}
			}
		}catch(Exception e){
			logger.error("楼栋名称检测失败!BuildsManageAction.checkBuildName.详细信息：" + e.getMessage());
			throw new BkmisWebException("楼栋名称检测失败,BuildsManageAction.checkBuildName!",e);
		}
		return null;
	}
	
	/** 跳至添加楼栋页面 */
	public ActionForward goAddBuild(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		String lpId = null;
		
		try {
			lpId = GlobalMethod.NullToSpace(request.getParameter("lpId"));
			if(!"".equals(lpId)){
				request.setAttribute("lpId", lpId);
			}
		}catch(Exception e){
			logger.error("跳至添加楼栋页面失败!BuildsManageAction.goAddBuild.详细信息：" + e.getMessage());
			throw new BkmisWebException("跳至添加楼栋页面失败！,BuildsManageAction.goAddBuild!",e);
		}
		return mapping.findForward("add");
	}

	/** 修改楼栋信息 */
	public ActionForward modifyBuild(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		/* 从页面上得到楼栋的所属楼盘id */
		BuildForm buildForm = (BuildForm)form;
		try{
			//加入日志的管理中
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			buildForm.setUserName(userName);
			ibuildsManageService.modifyBuild(buildForm);
			
		
		}catch(Exception e){
			logger.error("修改楼栋信息失败!BuildsManageAction.modifyBuild.详细信息：" + e.getMessage());
			throw new BkmisWebException("修改楼栋信息失败,BuildsManageAction.modifyBuild!",e);
		}
		request.setAttribute("flag", true);
		return mapping.findForward("modify");
	}
	
	/** 获得修改楼栋信息 */
	public ActionForward getModifyInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		 String temp = GlobalMethod.NullToSpace(request.getParameter("buildId")); 
		 Integer buildId = 0;
		 if(!"".equals(temp)){
			 buildId = Integer.parseInt(temp);
		 }
		try{
			/* 用于实现在页面上的楼盘的下拉框 */
			List<ELp> lpList = ilpManageService.getLp();
			EBuilds ebuild = ibuildsManageService.getEBuildsById(buildId);
			/* 根据楼栋id得到相应的表具信息 */
			List meterlist = ibuildsManageService.getEMeterByBuildId(buildId);
			request.setAttribute("meterlist", meterlist);
			request.setAttribute("lpList", lpList);
			request.setAttribute("build", ebuild);
		}catch(Exception e){
			logger.error("获得修改楼栋信息失败!BuildsManageAction.checkBuildName().详细信息：" + e.getMessage());
			throw new BkmisWebException("获得修改楼栋信息失败,BuildsManageAction.checkBuildName()!",e);
		}
		return mapping.findForward("modify");
	}

	/** 删除楼栋 */
	public ActionForward deleteBuild(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String[] buildId = request.getParameterValues("buildId");
	    List<String>  buildList = Arrays.asList(buildId);
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
	    try{
	    	if(!buildList.isEmpty()){
	    		ibuildsManageService.deletebuild(buildList,userName);
	    		/* 删除对应楼栋下的表具信息 */
	    		ibuildsManageService.deleteMeterByBuildId(buildList);
	    		response.sendRedirect("build.do?method=getBuildsList");
	    		
	    	}
	    }catch(Exception e){
	    	logger.error("楼栋信息删除失败!BuildsManageAction.deleteBuild().详细信息：" + e.getMessage());
			throw new BkmisWebException("楼栋信息删除失败,BuildsManageAction.deleteBuild()!",e);
	    }
		return null;
	}
	
	/** 楼层平面图的展示 */
	public ActionForward getFloorPlan(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		Map pictureSize = null;//用来得到图片的大小
		EBuilds build = null;//楼栋对象
		String floor = null;//用来得到楼层数
		Integer id = null;//楼盘或楼栋id
		String tablename = null;//用来得到表名
		String url = null;//用来得到楼层平面图的路径
		Integer  nrentalnum = 0;//用于统计未出租房间数量
		Integer  rentalnum = 0;//用于统计已出租房间数量
		Integer  destinenum = 0;//用于统计已出租房间数量
		Integer  infitmentnum = 0;//用于统计装修中的房间数量
		//得到楼层数
		try {
			/* 从树得到一个id,楼栋或楼盘的id*/
			id = Integer.parseInt(GlobalMethod.NullToParam(request.getParameter("id"),"0"));
			/* 得到表名 */
			tablename = GlobalMethod.NullToSpace(request.getParameter("tablename"));
			/* 得到楼层数 */
			floor = GlobalMethod.NullToSpace(request.getParameter("floor"));
			EFloorMap floorMap = null;
			//根据参数得到平面图
			floorMap = ibuildsManageService.getEFloorMap(floor,id,tablename);
			if("e_builds".equals(tablename)){//如果是楼栋
				/* 得到一个楼栋对象用于显示楼层数 */
				build = ibuildsManageService.getEBuildsById(id);
			}
			
			/* 用于显示房间的出租情况 */
			//未出租
			nrentalnum = iroomManageService.getRoomNumber(floor,id,tablename, Contants.OUTUSE);
			request.setAttribute("nrentalnum", nrentalnum);
			//已出租
			rentalnum = iroomManageService.getRoomNumber(floor,id,tablename, Contants.BELEASED);
			request.setAttribute("rentalnum", rentalnum);
			//已预租
			destinenum = iroomManageService.getRoomNumber(floor,id,tablename, Contants.DESTINE);
			request.setAttribute("destinenum", destinenum);
			//装修中
			infitmentnum = iroomManageService.getRoomNumber(floor,id,tablename, Contants.INFITMENT);
			request.setAttribute("infitmentnum", infitmentnum);
			
			/* 用于显示房间客户入住信息 */
			List roomflist = iroomManageService.getRoomInfo(floor,id,tablename);
			request.setAttribute("roomflist", roomflist);
			
			/* 用于显示图片 */
			request.setAttribute("floorMap", floorMap);
			
			//从页面上要得到要显示图片的大小的参数，如果是0，就小图，1，大图。
			String psize = GlobalMethod.NullToParam(request.getParameter("psize"),"0");
			//从数据库里获得所要显示的图片的大小的参数
			pictureSize = ibuildsManageService.getPSize(psize);
			/* 根据从页面上得到的参数在数据库的系统参数表里查出具体的图片显示大小的值 */
			request.setAttribute("pictureSize", pictureSize);
			/* 用于显示上次查询的条件是大图还是小图 */
			request.setAttribute("psize", psize);
			/* 在请求里传出表名 */
			request.setAttribute("tablename", tablename);
			/*页面上用于显示楼层下拉框*/
			request.setAttribute("build", build);
			/*楼盘或楼栋id*/
			request.setAttribute("id", id);
			/*楼层号*/
			request.setAttribute("floor", floor);
			
			
		} catch (Exception e) {
			logger.error("楼层平面图的展示获得失败!BuildsManageAction.getFloorPlan().详细信息：" + e.getMessage());
			throw new BkmisWebException("楼层平面图的展示的获得失败，BuildsManageAction.getFloorPlan()!",e);
		}
		this.my_saveToken(request);//自定义token机制，防止重复刷新
		return mapping.findForward("floorPlan");
	}
	
	/** 文件上传 */
	public ActionForward upload(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try{
			BuildForm uploadForm = (BuildForm)form;
			//得到当前系统环境的根路径
			String filePath = this.getServlet().getServletContext().getRealPath("/");
			System.out.println("上传地址："+filePath);
			String path = ibuildsManageService.upload(uploadForm,filePath);
			response.getWriter().println(path);

	} catch (IOException e) {
		}catch(Exception e){
			logger.error("楼层平面展示图片上传失败!BuildsManageAction.upload()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼层平面展示图片上传失败!BuildsManageAction.upload()!",e);
		}
		return null;
		
	}
	/** 添加平面展示图片  */
	public ActionForward addPlan(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuildForm buildForm = (BuildForm)form;
		if(!this.my_isTokenValid(request)){
			return this.getFloorPlan(mapping, form, request, response);
		}
		try {
				ibuildsManageService.addPlanMap(buildForm);
				request.setAttribute("message", "操作成功！");
				//response.sendRedirect("build.do?method=getFloorPlan&tablename=e_builds&floor="+floor + "&id="+buildId);
		} catch (IOException e) {
			request.setAttribute("message", "操作失败！");
			e.printStackTrace();
			logger.error("楼层平面展示图片添加失败!BuildsManageAction.addPlan()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼层平面展示图片添加失败!BuildsManageAction.addPlan()!",e);
		}
		return this.getFloorPlan(mapping, form, request, response);
	}
	
	/** 跳至在平面图上动态添加房间图的房间客户入住信息页面 */
	public ActionForward goGetRoomClientInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		String floor = GlobalMethod.NullToParam(request.getParameter("floor"), "1");
		Integer buildId = Integer.parseInt(GlobalMethod.NullToParam(request.getParameter("buildId"), "0"));
		RoomForm roomForm = new RoomForm();
		roomForm.setFloor(floor);
		roomForm.setBuildId(buildId);
		roomForm.setPagesize("9999");
		List<ERooms> roomlist = iroomManageService.getRoom(roomForm);
		request.setAttribute("roomlist", roomlist);
		return mapping.findForward("roomplan");
	}
	/** 获得房间的使用状态信息 */
	public ActionForward getRoomStatusByRoomId(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		PrintWriter out ; 
		String status = null;
		try{
			String sroomId = GlobalMethod.NullToSpace(request.getParameter("roomId"));
			if(!"".equals(sroomId)){
				ERooms room = iroomManageService.getERoomsById(Integer.parseInt(sroomId));
				status = room.getStatus();
				out = response.getWriter();
				out.print(status);
			}
		} catch (BkmisServiceException e) {
			logger.error("楼层平面展示图片添加失败!ibuildsManageService.getERoomsById()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼层平面展示图片添加失败!BuildsManageAction.getRoomStatusByRoomId()!",e);
		}
		return null;
	}
	
	/** 获得房间的入住客户信息 */
	public ActionForward getClientInfoByRoomId(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		PrintWriter out;
		String clientName = null;
		Integer clientId = null;
		String status = null;
		String str = null;
		String sroomId = GlobalMethod.NullToSpace(request.getParameter("roomId"));
		List list = iroomManageService.getClient(sroomId);
		if(list.size() != 0){
			Object[] s = (Object[])list.get(0);
			/* 获得房间客户信息 */
			ERoomClient roomClient = (ERoomClient) s[1];
		    clientName = roomClient.getClientName();
		    clientId = roomClient.getClientId();
		}
		if(clientName == null && clientId == null){
			str = "" + ";" + "";
		}else{
			str = clientName + ";" + clientId.toString();
		}
		out = response.getWriter();
		out.print(str);
		
		return null;
	}
	
	/** 检查楼层平面图上的房间图，避免重复添加房间图 */
	public ActionForward checkRoomMapByRoomId(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		PrintWriter out = null;
		Boolean flag = null;
		String sroomId = GlobalMethod.NullToSpace(request.getParameter("roomId"));
		try{
			flag = ibuildsManageService.checkRoomMap(sroomId);
			out = response.getWriter();
			out.print(flag);
		} catch (BkmisServiceException e) {
			logger.error("楼层平面展示图片添加失败!ibuildsManageService.checkRoomMap()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼层平面展示图片添加失败!BuildsManageAction.checkRoomMapByRoomId()!",e);
		}
		return null;
	}
	
	/** 保存在楼层平面图上的房间图的坐标信息和图片的宽度 */
	public ActionForward addRoomAxis(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		PrintWriter out = null;
		String x = GlobalMethod.NullToSpace(request.getParameter("x"));
		String y = GlobalMethod.NullToSpace(request.getParameter("y"));
	    String roomWidth = GlobalMethod.NullToSpace(request.getParameter("roomWidth"));
		String chartId = GlobalMethod.NullToSpace(request.getParameter("chartId"));
		String sroomId = GlobalMethod.NullToSpace(request.getParameter("roomId"));
		String planSize = GlobalMethod.NullToSpace(request.getParameter("planSize"));
		Integer rcpId = null;
		try {
			rcpId = ibuildsManageService.addRoomClient(x,y,roomWidth,chartId,sroomId,planSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out = response.getWriter();
		out.print(rcpId);
		return null;
	}
	
	/** 删除在楼层平面图上的房间图的坐标信息和图片的宽度 */
	public ActionForward deleteRoomAxis(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		PrintWriter out = null;
		Integer chartId = Integer.parseInt(GlobalMethod.NullToParam(request.getParameter("chartId"),"0"));
		out = response.getWriter();
		try {
			ibuildsManageService.deleteRoomMark(chartId);
			out.print("1");
		} catch (Exception e) {
			out.print("0");
			e.printStackTrace();
		}
		return null;
	}
	
	/**houxj
	 * 将list 转换为xml格式的字符串
	 * @param tName 表名
	 * @param list HashMap的集合
	 * @return xml格式的字符串
	 * @throws Exception 
	 */
	public String getMapXml(String tName,EFloorMapCoordinate EFloorMapCoordinate, List<EFloorMapCoordinate> list,String psize) throws Exception {
		
		String bwidth = null;
		String bheight = null;
		String swidth = null;
		String sheigth = null;
		String xml = "<" + tName + ">";
		if(list.size() > 0) {
				for(EFloorMapCoordinate o:list){
					Integer roomId = o.getRoomId();//房间id
					String clientName = "";//客户名称
					String status = Contants.OUTUSE;//状态初始化为未出租
					
					ERooms room = iroomManageService.getERoomsById(roomId);
					status = room.getStatus();
					String roomCode = room.getRoomCode();//房间号
					List<Map> tempList = ibuildsManageService.getClientAndRoomById(roomId);
					if(tempList!=null&&tempList.size()>0){
						clientName = GlobalMethod.NullToSpace((String)(tempList.get(0).get("client_name")));
					}
					String[] strSize = psize.split(";");
					String width = strSize[0];
					String height = strSize[1];
					Map map = ibuildsManageService.getChartSize();
					swidth = (String)map.get("swidth");
				    sheigth = (String)map.get("sheigth");
				    bwidth = (String)map.get("bwidth");
				    bheight = (String)map.get("bheight");
				    
				    xml += "<item>";
					if(width.equals(swidth) && height.equals(sheigth)){
						xml += "<XCoordinate>";
						xml += o.getXCoordinate();
						xml += "</XCoordinate>";
						
						xml += "<YCoordinate>";
						xml += o.getYCoordinate();
						xml += "</YCoordinate>";
						
						xml += "<mapWidth>";
						xml += o.getMapWidth();
						xml += "</mapWidth>";
					}else{
						if(width.equals(bwidth) && height.equals(bheight)){
							Map proportionChart = ibuildsManageService.getProportionOfChart(map);
							xml += "<XCoordinate>";
							xml += o.getXCoordinate() /((Double)(proportionChart.get("widthProportion")));
							xml += "</XCoordinate>";
							
							xml += "<YCoordinate>";
							xml += o.getYCoordinate() / ((Double)(proportionChart.get("heightProportion")));
							xml += "</YCoordinate>";
							
							xml += "<mapWidth>";
							xml += o.getMapWidth() / ((Double)(proportionChart.get("widthProportion")));
							xml += "</mapWidth>";
						}
					}
					
					
					xml += "<clientName>";
					xml += clientName;
					xml += "</clientName>";
					
					xml += "<roomCode>";
					xml += roomCode;
					xml += "</roomCode>";
					
					xml += "<status>";
					xml += status;
					xml += "</status>";
					
					xml += "<roomChartid>";
					xml += o.getId();
					xml += "</roomChartid>";
					xml += "</item>";
				}
		}		
		xml += "</" + tName + ">";
		return xml;
	}
	
	/** 获取楼层房间图的信息 */
	public ActionForward getRoomMap(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		PrintWriter out ;
		String browserFlag = GlobalMethod.NullToSpace(request.getParameter("browserFlag"));
		String schartId = GlobalMethod.NullToSpace(request.getParameter("chartId"));//楼层平面图的id
		String psize = GlobalMethod.NullToSpace(request.getParameter("psize"));
		if(!schartId.equals("")){
			/* 获取房间图信息 */
			try {
				/* 获取房间楼层平面的所有的房间的信息 */
				List<EFloorMapCoordinate> roomChartlist = ibuildsManageService.getRoomMap(schartId);
				
				/* 得到浏览器的类型，设置浏览器的响应内容类型 */
				if("ie".equals(browserFlag)){
					response.setContentType("text/xml");
				}
				EFloorMapCoordinate EFloorMapCoordinate = null;
				String str = getMapXml("roomChartlist",EFloorMapCoordinate,roomChartlist,psize);
				out = response.getWriter();
				out.print(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/** 调至添加表具页面 */
	public ActionForward goaddMeter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		/* 得到表具类型 */
		getMeterType(mapping,form,request,response);
		String slpId = GlobalMethod.NullToSpace(request.getParameter("lpId"));
		Integer lpId = null;
		if(!"".equals(slpId)){
			lpId = Integer.parseInt(slpId);
			request.setAttribute("lpId", lpId);
		}
		return mapping.findForward("bj");
	}
	
	/** 所属总表(楼盘的表)所需调用的方法 */
	public ActionForward getMeterByLpIdAndName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		PrintWriter out = null;
		String str1 = "";
		String str2 = "";
		String str3 = null;
		String slpId = GlobalMethod.NullToSpace(request.getParameter("lpId"));
		String meterName = GlobalMethod.NullToSpace(request.getParameter("type"));
		Integer lpId = 0;
		List<EMeter> lpmeterlist = null;
		try {
			if(!"".equals(slpId)){
				lpId = Integer.parseInt(slpId);
			}
			/* 根据表名和楼栋id */
			lpmeterlist = ibuildsManageService.getMeterByLpIdAndName(lpId,meterName);
			for(EMeter e:lpmeterlist){
				str1 += e.getCode() + ",";
				str2 += e.getId() + ",";
			}
			str3 = str1 + ";" + str2;
			out = response.getWriter();
			out.print(str3);
		} catch (Exception e) {
			logger.error("房间表具所属总表的查询失败!RoomManageAction.getMeterByBuildId()。详细信息：" + e.getMessage());
			throw new BkmisWebException("房间表具所属总表的查询失败!RoomManageAction.getMeterByBuildId()!",e);
		}
		return null;
	}
	
	/** 检查数据库里是否已经存在用户想要添加的表具,根据表具编号 */
	public ActionForward checkMeter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		BuildForm buildForm = (BuildForm)form;
		String forward = request.getParameter("forward");
		PrintWriter out = null;
		try {
			boolean arg = ibuildsManageService.checkMeter(buildForm);
			out = response.getWriter();
			if(arg){
				out.print(true);
			}else{
				if("addMeter".equals(forward)){
					out.print(false);
				}
			}
		} catch (Exception e) {
			logger.error("表具信息检查失败!BuildsManageAction.checkMeter()。详细信息：" + e.getMessage());
			throw new BkmisWebException("表具信息检查失败!BuildsManageAction.checkMeter()!",e);
		}
		return null;
	}
	
	/** 添加表具 */
	public ActionForward addMeter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		BuildForm buildForm = (BuildForm)form;
		try {
			
			//加入日志的管理中
//			String meterCode = buildForm.getCode();
//			String meterName = buildForm.getType();
//			Integer meterId = 0;
//			List<EMeter> list = ibuildsManageService.getMeterByCodeAndName(meterCode, meterName);
//			for(EMeter m:list){
//				meterId = m.getId();
//			}
			
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			buildForm.setUserName(userName);
			ibuildsManageService.addMeter(buildForm);
			//writeLog(userName, "添加表具", "所添加的表具ID为:" + meterId + "信息", Contants.BUILD);
		
		} catch (Exception e) {
			logger.error("表具信息添加失败!BuildsManageAction.addMeter()。详细信息：" + e.getMessage());
			throw new BkmisWebException("表具信息添加失败!BuildsManageAction.addMeter()!",e);
		}
		return null;
	}
	
	/** 从系统参数里获取表的名称，用于页面显示 */
	public ActionForward getMeterName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		BuildForm buildForm = (BuildForm)form;
		PrintWriter out = null;
		try {
			String meterName = ibuildsManageService.getMeterName(buildForm);
			out = response.getWriter();
			if(meterName != null){
				out.print(meterName);
			}
		} catch (Exception e) {
			logger.error("楼盘信息检查失败!LpManageAction.checkMeter()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼盘信息检查失败!LpManageAction.checkMeter()!",e);
		}
		return null;
	}
	
	/** 从数据库里获得表具类型 */
	public ActionForward getMeterType(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		List<SysCode> metertypelist = null;
		try{
			metertypelist = ibuildsManageService.getMeterType();
			request.setAttribute("metertypelist", metertypelist);
		} catch (Exception e) {
			logger.error("表具类型查询失败!BuildsManageAction.getMeterType()。详细信息：" + e.getMessage());
			throw new BkmisWebException("表具类型查询失败!BuildsManageAction.getMeterType()!",e);
		}
		return null;
	}
	
	 /** 删除表具信息 */
	public ActionForward deleteMeter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String code = GlobalMethod.NullToSpace(request.getParameter("code"));
	    String type = GlobalMethod.NullToSpace(request.getParameter("type"));
	    String[] rCode = null;
	    String[] rType = null;
	    String meterCode = GlobalMethod.NullToSpace(request.getParameter("meterCode"));
	    String meterType = GlobalMethod.NullToSpace(request.getParameter("meterType"));
		try {
//			if(!"".equals("meterCode")&& !"".equals(meterType)){
//				/* 用来得到一个EMeter对象的id，做添加日志之用*/
//				Integer meterId = 0;
//				List<EMeter> list = ibuildsManageService.getMeterByCodeAndName(meterCode, meterType);
//				for(EMeter m:list){
//					meterId = m.getId();
//				}
				/* 删除表具信息 */
			/* 删除表具信息 */
			if(!"".equals(code) && !"".equals(type)){
				rCode = code.split(";");
				rType = type.split(";");
				for(int i=0,j=0;i<rCode.length && j<rType.length;i++,j++){
					ibuildsManageService.deleteMeter(rCode[i],rType[j]);
				}
			}else{
				if(!"".equals(meterCode) && !"".equals(meterType)){
					ibuildsManageService.deleteMeter(meterCode,meterType);
				}
			}
				
				//加入日志的管理中
				Map map = (Map)request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
//				writeLog(userName, "删除表具", "删除了表具ID为" + meterId + "信息", Contants.BUILD);
//			}
		}catch(Exception e){
			logger.error("表具信息删除失败!BuildsManageAction.deleteMeter()。详细信息：" + e.getMessage());
			throw new BkmisWebException("表具信息查询失败!BuildsManageAction.deleteMeter()!",e);
		}
		return null;
	}
	
	/** 根据表具的名称得到系统参数表里对应的codeValue值 */
	public ActionForward getMeterTypeByCodeName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String meterName = GlobalMethod.NullToSpace(request.getParameter("meterName"));
		PrintWriter out = null;
		try {
			String meterType = ibuildsManageService.getMeterTypeByCodeName(meterName);
			out = response.getWriter();
			if(meterType != null){
				out.print(meterType);
			}
		} catch (Exception e) {
			logger.error("楼盘信息检查失败!LpManageAction.checkMeter()。详细信息：" + e.getMessage());
			throw new BkmisWebException("楼盘信息检查失败!LpManageAction.checkMeter()!",e);
		}
		return null;
	}
	
	
}
