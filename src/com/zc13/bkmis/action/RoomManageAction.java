package com.zc13.bkmis.action;

import java.io.PrintWriter;
import java.util.ArrayList;
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

import com.zc13.bkmis.form.RoomForm;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.EMeter;
import com.zc13.bkmis.mapping.ERoomClient;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.mapping.ViewBuild;
import com.zc13.bkmis.mapping.ViewBuildId;
import com.zc13.bkmis.service.IBuildsManageService;
import com.zc13.bkmis.service.IRoomManageService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Constant;
import com.zc13.util.DTree;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/***
 * @author 侯哓娟
 * Date：Nov 23, 2010
 * Time：5:15:50 PM
 */
public class RoomManageAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	
	private IRoomManageService iroomManageService = null;
	private IBuildsManageService ibuildsManageService = null;
	/** 从spring容器里得到iroomManageService*/
	public void setServlet(ActionServlet actionservlet){
		super.setServlet(actionservlet);
		iroomManageService = (IRoomManageService)getBean("iroomManageService");
		ibuildsManageService = (IBuildsManageService)getBean("ibuildsManageService");
		
	}
	
	/**
	 * 导出房间列表excel
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportRoomExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {
			//list中存放的就是当前页面上显示的所有数据
//			List list1 = (List)request.getSession().getAttribute("roomlist");
			RoomForm roomForm = (RoomForm)form;
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
			roomForm.setLpId(Integer.valueOf(lpid));

			List list = iroomManageService.getRoomE(roomForm);
			//表头
			String[] cellHeader = Constant.EXCEL_ROOM_HEADER;
			String[] cellValue = Constant.EXCEL_ROOM_VALUE;
			//定义文件名
			String sheetName = "房间信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list,sheetName,cellHeader,cellValue,new ERooms());
			response.setBufferSize(100*1024);
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "房间信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出房间excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/** 得到高级查询所需的参数 */
	public ActionForward getSysParameter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		List<SysCode> roomTypelist = null;
		List<SysCode> towardlist = null;
		List<SysCode> statuslist = null;
		Map<String, List<SysCode>> map = null;
		try{
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			/** 到此为止*/
			map = iroomManageService.getAdvancedInfo(lpId);
			roomTypelist =  map.get("roomTypelist");
			towardlist =  map.get("towardlist");
			statuslist = map.get("statuslist");
			request.setAttribute("roomTypelist", roomTypelist);
			request.setAttribute("towardlist", towardlist);
			request.setAttribute("statuslist", statuslist);
		}catch(Exception e){
			logger.error("高级查询所需的参数获取失败!RoomManageAction.getSysParameter().详细信息：" + e.getMessage());
			throw new BkmisWebException("高级查询所需的参数获取失败，RoomManageAction.getSysParameter()!",e);
		}
		return null;
	}
	
	/** 根据房间号，楼层，房型，使用状态，朝向，使用面积等条件进行查询 
	 * 添加分页 */
	public ActionForward searchRoom(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		RoomForm roomForm = (RoomForm)form;
		List<ERooms> roomlist = null;
		/* 用于在高级查询完了继续保留高级查询的条件的标志 */
		String adFlag = GlobalMethod.NullToSpace(request.getParameter("adFlag"));
		try{
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
			roomForm.setLpId(Integer.valueOf(lpid));
			
			roomlist = iroomManageService.getRoom(roomForm);
			//取总条数
			int totalcount = iroomManageService.queryCounttotal(roomForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == roomlist || roomlist.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/room.do?method=searchRoom", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/room.do?method=searchRoom", Integer.parseInt(GlobalMethod.NullToParam(roomForm.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(roomForm.getCurrentpage(),"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			getSysParameter(mapping,form,request,response);//得到高级查询所需的参数
			request.getSession().setAttribute("roomlist", roomlist);
			request.setAttribute("size", roomlist.size());
			request.setAttribute("adFlag", adFlag);
		}catch(Exception e){
			logger.error("高级查询失败!RoomManageAction.searchRoom().详细信息：" + e.getMessage());
			throw new BkmisWebException("高级查询失败，RoomManageAction.searchRoom()!",e);
		}
		
		return mapping.findForward("aroomList");
	}
	
	/** 用于房态信息查询的树 */
	public ActionForward getQTree(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		List<ViewBuild> treeforlist = null; 
		RoomForm roomForm = (RoomForm)form;
		List<DTree> treeList = new ArrayList<DTree>();
		try {
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
			roomForm.setLpId(Integer.valueOf(lpid));
			treeforlist = iroomManageService.getInfoForTree(roomForm);
		} catch (Exception e) {
			logger.error("tree的获得失败!RoomManageAction.getTree().详细信息：" + e.getMessage());
			throw new BkmisWebException("tree的获得失败，RoomManageAction.getTree()!",e);
		}
		
		treeList.add(new DTree("1","房产信息","0",""));
		for(ViewBuild v:treeforlist){
			DTree tree =  new DTree();
			ViewBuildId build = v.getId();
			tree.setId(build.getId().toString());
			tree.setName(build.getName());
			tree.setParentID(build.getParentId().toString());
			tree.setUrl("room.do?method=searchRoomByLp&id=" + build.getPk() + "&tablename=" + build.getTableName() + "&forward=bylp");
			treeList.add(tree);
		}
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp", "room.do?method=searchRoom");
		return mapping.findForward("tree");
	}
	
	/** 根据楼盘或者楼栋去查询房间查询 */
	public ActionForward searchRoomByLp(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		RoomForm roomForm = (RoomForm)form;
		String tablename = GlobalMethod.NullToSpace(request.getParameter("tablename"));
		/* 用于房间查询的 */
		String qId = GlobalMethod.NullToSpace(request.getParameter("id"));
		String forward = request.getParameter("forward");
		List<ERooms> roomlist = null;
		/* 用于房间管理的 */
		String slpOrbuildId = GlobalMethod.NullToSpace(request.getParameter("lpOrbuildId"));
		
		/* 用于房间管理显示房间信息列表页,否则显示的是用于房态信息查询所显示的列表页 */
		if(!"".equals(slpOrbuildId)){
			try {
				Integer id = Integer.parseInt(slpOrbuildId);
				/* 查询出每页显示的记录 */
				roomlist = iroomManageService.getRoomByLpOrBuild(id, tablename,roomForm);
				
				//取总条数
				int totalcount = iroomManageService.getRoomNumByTablename(id, tablename);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == roomlist || roomlist.size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/room.do?method=method=searchRoomByLp&lpOrbuildId=" + id + "&tablename=" + tablename + "&forward=manage", 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/room.do?method=searchRoomByLp&lpOrbuildId=" + id + "&tablename=" + tablename + "&forward=manage", Integer.parseInt(GlobalMethod.NullToParam(roomForm.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(roomForm.getCurrentpage(),"1")), totalcount);
				}
				
				request.setAttribute("pagination", htmlPagination);
				request.setAttribute("size", roomlist.size());
				
				/*如果是楼栋,则在form中保存一个楼栋id和表名
				 *如果是楼盘，则在form中保存lpId和表名
				 */
				if("e_builds".equals(tablename)){
					roomForm.setBuildId(id);
					roomForm.setTablename(tablename);
				}else{
					roomForm.setLpId(id);
					roomForm.setTablename(tablename);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			/* 房态信息查询 */
			if(!"".equals(qId)){
				try {
					Integer id = Integer.parseInt(qId);
					getSysParameter(mapping,form,request,response);//得到高级查询所需的参数
					/* 查询出每页显示的记录 */
					roomlist = iroomManageService.getRoomByLpOrBuild(id, tablename,roomForm);
					//取总条数
					int totalcount = iroomManageService.getRoomNumByTablename(id, tablename);
					//添加分页信息
					String htmlPagination = "&nbsp;";
					if (null == roomlist || roomlist.size() <= 0) {//如果没有记录，那么默认如下
						htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
								+ "/room.do?method=searchRoomByLp&id=" + id + "&tablename=" + tablename + "&forward=bylp", 10, 1, 0);
					} else {
						htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
								+ "/room.do?method=searchRoomByLp&id=" + id + "&tablename=" + tablename + "&forward=bylp", Integer.parseInt(GlobalMethod.NullToParam(roomForm.getPagesize(),"10")),
								Integer.parseInt(GlobalMethod.NullToParam(roomForm.getCurrentpage(),"1")), totalcount);
					}
					request.setAttribute("pagination", htmlPagination);
					
					/*如果是楼栋,则在form中保存一个楼栋id和表名
					 *如果是楼盘，则在form中保存lpId和表名
					 */
					if("e_builds".equals(tablename)){
						roomForm.setBuildId(id);
						roomForm.setTablename(tablename);
					}else{
						roomForm.setLpId(id);
						roomForm.setTablename(tablename);
					}
				} catch (BkmisServiceException e) {
					logger.error("查询楼盘或楼栋下的房间信息失败!iroomManageService.getRoomByLpOrBuild().详细信息：" + e.getMessage());
					throw new BkmisWebException("查询楼盘或楼栋下的房间信息失败，RoomManageAction.searchRoomByLp()!",e);
				}
		   }
		}
		request.getSession().setAttribute("roomlist", roomlist);
		
		return mapping.findForward(forward);
	}
	
	/** 根据房间id查询房间的详细信息 */
	public ActionForward getRoomInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String sroomId = GlobalMethod.NullToSpace(request.getParameter("roomId"));
		Integer roomId = null;
		if(!"".equals(sroomId)){
			roomId = Integer.parseInt(sroomId);
		}
		ERooms eroom = iroomManageService.getERoomsById(roomId);
		request.setAttribute("eroom", eroom);
		/* 根据楼盘id得到相应的表具信息 */
		List meterlist = iroomManageService.getEMeterByRoomId(roomId);
		request.setAttribute("meterlist",meterlist);
		/* 根据房间id的得到房间设备信息 */
		List equiplist = iroomManageService.getEquipByRoomId(roomId);
		request.setAttribute("equiplist", equiplist);
		return mapping.findForward("roomInfo");
	}
	
	/** 查询房间历史住户 */
	public ActionForward getHistoryClient(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		RoomForm roomForm = (RoomForm)form;
		String sroomId = GlobalMethod.NullToSpace(request.getParameter("roomId"));
		Integer roomId = null;
		if(!"".equals(sroomId)){
			roomId = Integer.parseInt(sroomId);
		}
		/* 得到房间号 */
		ERooms eroom = iroomManageService.getERoomsById(roomId);
		request.setAttribute("eroom", eroom);
		List<ERoomClient> historyClientInfoList =  iroomManageService.getHistoryClientInfo(roomId);
		request.setAttribute("historyClientInfoList", historyClientInfoList);
		
		//取总条数
		int totalcount = iroomManageService.queryCounttotal(roomId);
		/* 分页用：roomId传参 */
		String params = "&roomId="+roomId;
		//添加分页信息
		String htmlPagination = "&nbsp;";
		if (null == historyClientInfoList || historyClientInfoList.size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/room.do?method=getHistoryClient"+params, 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/room.do?method=getHistoryClient"+params, Integer.parseInt(GlobalMethod.NullToParam(roomForm.getPagesize(),"10")),
					Integer.parseInt(GlobalMethod.NullToParam(roomForm.getCurrentpage(),"1")), totalcount);
		}
		request.setAttribute("pagination", htmlPagination);
		return mapping.findForward("history");
	}
	
	/** manage得到房间管理所需的树 */
	public ActionForward getMTree(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		List<ViewBuild> treeforlist = null;
		RoomForm roomForm = (RoomForm)form;
		List<DTree> treeList = new ArrayList<DTree>();
		try {
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
			roomForm.setLpId(Integer.valueOf(lpid));
			treeforlist = iroomManageService.getInfoForTree(roomForm);
		} catch (Exception e) {
			logger.error("tree的获得失败!RoomManageAction.getTree().详细信息：" + e.getMessage());
			throw new BkmisWebException("tree的获得失败，RoomManageAction.getTree()!",e);
		}
		
		treeList.add(new DTree("1","房产信息","0",""));
		for(ViewBuild v:treeforlist){
			DTree tree =  new DTree();
			ViewBuildId build = v.getId();
			tree.setId(build.getId().toString());
			tree.setName(build.getName());
			tree.setParentID(build.getParentId().toString());
			tree.setUrl("room.do?method=searchRoomByLp&lpOrbuildId=" + build.getPk() + "&tablename=" + build.getTableName() + "&forward=manage");
			treeList.add(tree);
		}
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp", "room.do?method=getRoomList");
		return mapping.findForward("tree");
	}

	/** 得到房间列表 */
	public ActionForward getRoomList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		RoomForm roomForm = (RoomForm)form;
		List<ERooms> roomlist = null;
		try{
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
			roomForm.setLpId(Integer.valueOf(lpid));
			roomlist = iroomManageService.getRoom(roomForm);
			//取总条数
			int totalcount = iroomManageService.queryCounttotal(roomForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == roomlist || roomlist.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/room.do?method=getRoomList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/room.do?method=getRoomList", Integer.parseInt(GlobalMethod.NullToParam(roomForm.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(roomForm.getCurrentpage(),"1")), totalcount);
			}
			
			request.setAttribute("pagination", htmlPagination);
			request.setAttribute("roomlist", roomlist);
			request.setAttribute("size", roomlist.size());
		}catch(Exception e){
			logger.error("高级查询失败!RoomManageAction.searchRoom().详细信息：" + e.getMessage());
			throw new BkmisWebException("高级查询失败，RoomManageAction.searchRoom()!",e);
		}
		
		return mapping.findForward("roomList");
	}

	/** 跳至添加房间页面 */
	public ActionForward goAddRoom(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		//获得要添加房间的所属楼栋的id
		String sbuildId = GlobalMethod.NullToSpace(request.getParameter("buildId"));
		Integer buildId = null;
		if(!"".equals(sbuildId)){
			buildId = Integer.parseInt(sbuildId);
		}
		try{
			//得到一个楼栋对象用于显示楼层数和保存一个楼栋id在页面
			EBuilds ebuild = ibuildsManageService.getEBuildsById(buildId);
			request.setAttribute("ebuild", ebuild);
			
			//下面这个方法负责删除那些不符合条件的表具信息、垃圾信息
			ibuildsManageService.deleteRMeter();
			
			//获得房间的使用状态，房型，朝向的下拉列表框
			getSysParameter(mapping,form,request,response);
		}catch(Exception e){
			logger.error("跳至添加房间页面失败!RoomManageAction.goAddLp().详细信息：" + e.getMessage());
			throw new BkmisWebException("跳至添加房间页面失败，RoomManageAction.goAddLp()!",e);
		}
		
		return mapping.findForward("add");
	}
	
	/** 检查数据库里是否已经存在用户想要添加的房间,此处的房间号不重复是同意楼栋的不能有重复 */
	public ActionForward checkRoomCode(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String roomCode = request.getParameter("roomCode");
		String forward = request.getParameter("forward");
		String id = GlobalMethod.NullToSpace(request.getParameter("buildId"));
		PrintWriter out = null;
		Integer buildId = 0;
		try {
			/* 如果从添加页面得到的楼栋id不为空，则把它转换称整型*/
			if(!"".equals(id)){
				buildId = Integer.parseInt(id);
			}
			EBuilds ebuild = ibuildsManageService.getEBuildsById(buildId);
			boolean arg = iroomManageService.checkRoomCode(roomCode,ebuild);
			out = response.getWriter();
			if(arg){
				out.print(true);
			}else{
				if("addRoom".equals(forward)){
					out.print(false);
				}
			}
		} catch (Exception e) {
			logger.error("房间信息检查失败!RoomManageAction.checkRoomCode()。详细信息：" + e.getMessage());
			throw new BkmisWebException("房间信息检查失败!RoomManageAction.checkRoomCode()!",e);
		}
		return null;
	}
	
	/** 添加房间信息 */
	public ActionForward addRoom(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		RoomForm roomForm = (RoomForm)form;
		try{
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			roomForm.setLpId(lpId);
			/** 到此为止*/
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			roomForm.setUserName(userName);
			//writeLog(userName, "添加房间", "所添加的房间ID为" + roomForm.getRoomId() + "信息", Contants.BUILD);
			iroomManageService.addRoom(request,roomForm);
			
		} catch (Exception e) {
			logger.error("房间添加失败!RoomManageAction.addRoom()。详细信息：" + e.getMessage());
			throw new BkmisWebException("房间添加失败!RoomManageAction.addRoom()!",e);
		}

		request.setAttribute("flag", true);
		return mapping.findForward("add");
	}
	
	/** 获得修改房间所需的信息 */
	public ActionForward getModifyInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String sroomId = GlobalMethod.NullToSpace(request.getParameter("roomId"));
		Integer roomId = 0;
		try{
			if(!"".equals(sroomId)){
				roomId = Integer.parseInt(sroomId);
			}
			ERooms eroom = iroomManageService.getERoomsById(roomId);
			request.setAttribute("eroom", eroom);
			/* 显示房间下拉框的显示参数 */
			getSysParameter(mapping,form,request,response);
			/* 根据楼盘id得到相应的表具信息 */
			List<EMeter> meterlist = iroomManageService.getEMeterByRoomId(roomId);
			request.setAttribute("meterlist",meterlist);
			/* 根据房间id的得到房间设备信息 */
			List equiplist = iroomManageService.getEquipByRoomId(roomId);
			request.setAttribute("equiplist", equiplist);
			
		} catch (Exception e) {
			logger.error("获得修改房间所需的信息失败!RoomManageAction.getModifyInfo()。详细信息：" + e.getMessage());
			throw new BkmisWebException("获得修改房间所需的信息失败!RoomManageAction.getModifyInfo()!",e);
		}
		return mapping.findForward("modify");
	}
	
	/** 修该房间信息 */
	public ActionForward modifyRoom(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		RoomForm roomForm = (RoomForm)form;
		try{
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			roomForm.setLpId(lpId);
			/** 到此为止*/
			//加入日志的管理中
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			roomForm.setUserName(userName);
			iroomManageService.midifyRoom(request,roomForm);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("房间更新失败!RoomManageAction.modifyRoom()。详细信息：" + e.getMessage());
			throw new BkmisWebException("房间更新失败!RoomManageAction.modifyRoom()!",e);
		}
		request.setAttribute("flag", true);
		return mapping.findForward("modify");
	}
	
	/** 查询房间的客户信息 */
	public ActionForward deleteRoom(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		PrintWriter out = null;
		String stemp = null;
		String sroomId = GlobalMethod.NullToSpace(request.getParameter("roomId"));
		try{
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			if(!"".equals(sroomId)){
				stemp = iroomManageService.deleteRoom(sroomId,userName);
			}
			out = response.getWriter();
			out.print(stemp);
		}catch (Exception e) {
				logger.error("获取房间客户信息失败!iroomManageService.getgetRoomClientInfo()。详细信息：" + e.getMessage());
				throw new BkmisWebException("获取房间客户信息失败!RoomManageAction.getgetRoomClientInfo()!",e);
		}
		return null;
	}
	
//	/** 删除房间 */
//	public ActionForward deleteRoom(ActionMapping mapping,ActionForm form,
//			HttpServletRequest request,HttpServletResponse response){
//		
//		RoomForm roomForm = (RoomForm)form;
//		String[] roomId = request.getParameterValues("roomId");
//		List<String> idList = Arrays.asList(roomId);
//		try{
//			String sroomId = GlobalMethod.NullToSpace(request.getParameter("sroomId"));
//			if(!"".equals(sroomId)){
////				sroomId.split(";");
//				iroomManageService.deleteRoom(sroomId);
//			}else{
//				iroomManageService.deleteRoom(idList);
//				
//			}
////			if(!idList.isEmpty()){
//				//加入日志的管理中
////				Iterator it = idList.iterator();
////				while(it.hasNext()){
////					Integer roomid = Integer.parseInt((String) it.next());
////					Map map = (Map)request.getSession().getAttribute("userInfo");
////					String userName = GlobalMethod.NullToSpace(map.get("username").toString());
////					writeLog(userName, "删除房间", "删除了房间ID为" + roomid + "信息", Contants.BUILD);
////				}
////			}
//			Integer buildId = roomForm.getBuildId();
//			Integer lpId = roomForm.getLpId();
//			/* 如果是选择楼栋下的页面，则删除后重定向到该楼栋页面
//			 * 如果是楼盘，则删除后重定向到楼盘对应的页面
//			 * 如果没有选择则重定向到有所遇房间信息的页面 */
//			if(buildId != null && buildId != 0){
//				response.sendRedirect("room.do?method=searchRoomByLp&lpOrbuildId=" + buildId + "&tablename=" + roomForm.getTablename() + "&forward=manage");
//			}else if(lpId != null  && lpId != 0){
//				response.sendRedirect("room.do?method=searchRoomByLp&lpOrbuildId=" + lpId + "&tablename=" + roomForm.getTablename() + "&forward=manage");
//			}else{
//				response.sendRedirect("room.do?method=getRoomList");
//			}
//		} catch (Exception e) {
//			logger.error("房间删除失败!RoomManageAction.deleteRoom()。详细信息：" + e.getMessage());
//			throw new BkmisWebException("房间删除失败!RoomManageAction.deleteRoom()!",e);
//		}
//		return null;
//	}
	
	/** 跳转至批量增加房间 */
	public ActionForward goBatchincreaseRoom(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		//获得跟房间有关的参数
		getSysParameter(mapping,form,request,response);
		
		//获得要添加房间的所属楼栋的id病传到房间添加页面
		String sbuildId = GlobalMethod.NullToSpace(request.getParameter("buildId"));
		Integer buildId = 0;
		if(!"".equals(sbuildId)){
			 buildId = Integer.parseInt(sbuildId);
		}
		try {
			//得到一个楼栋对象用于显示楼层数和保存一个楼栋id在页面
			EBuilds ebuild;
			ebuild = ibuildsManageService.getEBuildsById(buildId);
			request.setAttribute("ebuild", ebuild);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		request.setAttribute("buildId", buildId);
		return mapping.findForward("batch");
	}
	
	/** 批量增加房间 */
	public ActionForward batchincreaseRoom(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		RoomForm roomForm = (RoomForm)form;
		try{
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			roomForm.setLpId(lpId);
			/** 到此为止*/
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			roomForm.setUserName(userName);
			iroomManageService.batchincreaseRoom(roomForm);
		} catch (Exception e) {
			logger.error("房间批量增加失败!RoomManageAction.batchincreaseRoom()。详细信息：" + e.getMessage());
			throw new BkmisWebException("房间批量增加失败!RoomManageAction.batchincreaseRoom()!",e);
		}

		request.setAttribute("flag", true);
		return mapping.findForward("batch");
	}
	
	/** 调至添加房间表具 */
	/** 调至添加表具页面 
	 * @throws Exception */
	public ActionForward goAddMeter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		/* 得到表具类型 */
		getMeterType(mapping,form,request,response);
		/* 向添加表具页面传递一个buildId用于查询房间表具的所属总表 */
		String sbuildId = GlobalMethod.NullToSpace(request.getParameter("buildId"));
		Integer buildId = 0;
		if(!"".equals(sbuildId)){
			buildId = Integer.parseInt(sbuildId);
		}
		request.setAttribute("buildId", buildId);
		return mapping.findForward("bj");
	}
	
	/** 从数据库里获得表具类型 */
	public ActionForward getMeterType(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		List<SysCode> metertypelist = null;
		try{
			metertypelist = iroomManageService.getMeterType();
			request.setAttribute("metertypelist", metertypelist);
		} catch (Exception e) {
			logger.error("表具类型查询失败!RoomManageAction.getMeterType()。详细信息：" + e.getMessage());
			throw new BkmisWebException("表具类型查询失败!RoomManageAction.getMeterType()!",e);
		}
		return null;
	}
	/** 所属总表所需调用的方法 */
	public ActionForward getMeterByBuildIdAndName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		PrintWriter out = null;
		String str1 = "";
		String str2 = "";
		String str3 = null;
		String sbuildId = GlobalMethod.NullToSpace(request.getParameter("buildId"));
		String meterName = GlobalMethod.NullToSpace(request.getParameter("type"));
		Integer buildId = 0;
		List<EMeter> bmeterlist = null;
		try {
			if(!"".equals(sbuildId)){
				buildId = Integer.parseInt(sbuildId);
			}
			/* 根据表名和楼栋id */
			bmeterlist = iroomManageService.getMeterByBuildIdAndName(buildId,meterName);
			for(EMeter e:bmeterlist){
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
	
	/** 所属总表所需调用的方法 */
	public ActionForward getMeterByBuildId(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		String sbuildId = GlobalMethod.NullToSpace(request.getParameter("buildId"));
		Integer buildId = 0;
		try {
			if(!"".equals(sbuildId)){
				buildId = Integer.parseInt(sbuildId);
			}
			/* 根据表名和楼栋id */
			boolean flag = iroomManageService.getMeterByBuildId(buildId);
			out = response.getWriter();
			out.print(flag);
		} catch (Exception e) {
			logger.error("房间表具所属总表的查询失败!RoomManageAction.getMeterByBuildId()。详细信息：" + e.getMessage());
			throw new BkmisWebException("房间表具所属总表的查询失败!RoomManageAction.getMeterByBuildId()!",e);
		}
		return null;
	}
	/** 检查数据库里是否已经存在用户想要添加的表具,根据表具编号 */
	public ActionForward checkMeter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		RoomForm roomForm = (RoomForm)form;
		String forward = request.getParameter("forward");
		PrintWriter out = null;
		try {
			boolean arg = iroomManageService.checkMeter(roomForm);
			out = response.getWriter();
			if(arg){
				out.print(true);
			}else{
				if("addMeter".equals(forward)){
					out.print(false);
				}
			}
		} catch (Exception e) {
			logger.error("表具信息检查失败!RoomManageAction.checkMeter()。详细信息：" + e.getMessage());
			throw new BkmisWebException("表具信息检查失败!RoomManageAction.checkMeter()!",e);
		}
		return null;
	}
	/** 添加表具 */
	public ActionForward addMeter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		RoomForm roomForm = (RoomForm)form;
		try {
			roomForm.setBuildId(null);
			
			/*//加入日志的管理中
			String meterCode = roomForm.getCode();
			String meterName = roomForm.getType();
			Integer parentId = roomForm.getParentId();
			Integer meterId = 0;
			List<EMeter> list = iroomManageService.getMeterByCTP(meterCode, meterName, parentId);
			for(EMeter m:list){
				meterId = m.getId();
			}
			*/
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			roomForm.setUserName(userName);
			iroomManageService.addMeter(roomForm);
			//writeLog(userName, "添加表具", "所添加的表具ID为:" + meterId + "信息", Contants.BUILD);
		
		} catch (Exception e) {
			logger.error("表具信息添加失败!RoomManageAction.addMeter()。详细信息：" + e.getMessage());
			throw new BkmisWebException("表具信息添加失败!RoomManageAction.addMeter()!",e);
		}
		return null;
	}
	
	/** 从系统参数里获取表的名称，用于页面显示 */
	public ActionForward getMeterName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		RoomForm roomForm = (RoomForm)form;
		PrintWriter out = null;
		try {
			String meterName = iroomManageService.getMeterName(roomForm);
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
//			if(!"".equals(meterCode)&& !"".equals(meterType)){
//				/* 用来得到一个EMeter对象的id，做添加日志之用*/
//				Integer meterId = 0;
//				List<EMeter> list = iroomManageService.getMeterByCodeAndName(meterCode, meterType);
//				for(EMeter m:list){
//					meterId = m.getId();
//				}
//				/* 删除表具信息 */
//				iroomManageService.deleteMeter(meterCode,meterType);
				
			/* 删除表具信息 */
			if(!"".equals(code) && !"".equals(type)){
				rCode = code.split(";");
				rType = type.split(";");
				for(int i=0,j=0;i<rCode.length && j<rType.length;i++,j++){
					iroomManageService.deleteMeter(rCode[i],rType[j]);
				}
			}else{
				if(!"".equals(meterCode) && !"".equals(meterType)){
					iroomManageService.deleteMeter(meterCode,meterType);
				}
//			}
				//加入日志的管理中
				Map map = (Map)request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
//				writeLog(userName, "删除表具", "删除了表具ID为" + meterId + "信息", Contants.BUILD);
			}
		}catch(Exception e){
			logger.error("表具信息删除失败!RoomManageAction.deleteMeter()。详细信息：" + e.getMessage());
			throw new BkmisWebException("表具信息查询失败!RoomManageAction.deleteMeter()!",e);
		}
		return null;
	}
	
	/** 根据表具的名称得到系统参数表里对应的codeValue值 */
	public ActionForward getMeterTypeByCodeName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String meterName = GlobalMethod.NullToSpace(request.getParameter("meterName"));
		PrintWriter out = null;
		try {
			String meterType = iroomManageService.getMeterTypeByCodeName(meterName);
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
	
	/** 删除房间设备信息 */
	public ActionForward deleteEquip(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
	    String sequipId = GlobalMethod.NullToSpace(request.getParameter("equipId"));
	    Integer equipId = 0;
		try {
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			if(!"".equals(equipId)){
				equipId = Integer.parseInt(sequipId);
				/* 删除表具信息 */
				iroomManageService.deleteEquip(equipId,userName);
			
			}
		}catch(Exception e){
			logger.error("房间设备信息删除失败!RoomManageAction.deleteEquip()。详细信息：" + e.getMessage());
			throw new BkmisWebException("房间设备信息删除失败!RoomManageAction.deleteEquip()!",e);
		}
		return null;
	}
	/** 楼层平面图展示 
	 * q得到树
	 * @throws Exception 
	 */
	public ActionForward getTree(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		List<ViewBuild> treeforlist = null; 
		RoomForm roomForm = (RoomForm)form;
		List<DTree> treeList = new ArrayList<DTree>();
		try {
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
			roomForm.setLpId(Integer.valueOf(lpid));
			treeforlist = iroomManageService.getInfoForTree(roomForm);
		} catch (Exception e) {
			logger.error("tree的获得失败!BuildsManageAction.getTree().详细信息：" + e.getMessage());
			throw new BkmisWebException("tree的获得失败，BuildsManageAction.getTree()!",e);
		}
		int count = 0;
		String id = null;
		treeList.add(new DTree("1","房产信息","0",""));
		for(ViewBuild v:treeforlist){
			DTree tree =  new DTree();
			ViewBuildId build = v.getId();
			tree.setId(build.getId().toString());
			tree.setName(build.getName());
			tree.setParentID(build.getParentId().toString());
			tree.setUrl("build.do?method=getFloorPlan&id=" + build.getPk() + "&tablename=" + build.getTableName() + "&forward=bylp");
			treeList.add(tree);
			//获取第一个楼盘id并赋值给id
			if(count==0&&"e_lp".equals(build.getTableName())){
				count++;
				id = String.valueOf(build.getPk());
			}
		}
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp", "build.do?method=getFloorPlan&tablename=e_lp&id="+id);
		return mapping.findForward("tree");
	}
	
}


