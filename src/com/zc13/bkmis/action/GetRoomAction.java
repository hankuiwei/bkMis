package com.zc13.bkmis.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.RoomForm;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.mapping.ViewBuild;
import com.zc13.bkmis.mapping.ViewBuildId;
import com.zc13.bkmis.service.IRoomManageService;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Contants;
import com.zc13.util.DTree;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/***
 * @author 秦彦韬
 * Date：Dec 16, 2010
 * Time：5:15:50 PM
 */
public class GetRoomAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private IRoomManageService iroomManageService = null;
	/** 从spring容器里得到iroomManageService*/
	/** 从spring容器里得到iCustomerRoomService*/
	public void setServlet(ActionServlet actionservlet){
		super.setServlet(actionservlet);
		iroomManageService = (IRoomManageService)getBean("iroomManageService");
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
	
	/** 根据楼盘查询 */
	public ActionForward searchRoomByLp(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		RoomForm roomForm = (RoomForm)form;
		Integer id = Integer.parseInt(GlobalMethod.NullToParam(request.getParameter("id"),"0"));
		String tablename = GlobalMethod.NullToSpace(roomForm.getTablename());
		String url = request.getContextPath() + "/getRoom.do?method=searchRoomByLp&id="+id+"&tablename="+tablename.replaceAll("'", "");
		if(Contants.E_LP.equals(tablename)){
			roomForm.setLpId(id);
		}
		if(Contants.E_BUILD.equals(tablename)){
			roomForm.setBuildId(id);
		}
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		roomForm.setLpId(lpId);
		/** 到此为止*/
		List<ERooms> roomlist = null;
		/* 用于在高级查询完了继续保留高级查询的条件的标志 */
		String adFlag = GlobalMethod.NullToSpace(request.getParameter("adFlag"));
		try{
			roomlist = iroomManageService.getRoom(roomForm);
			//取总条数
			int totalcount = iroomManageService.queryCounttotal(roomForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == roomlist || roomlist.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(url, 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(url, Integer.parseInt(GlobalMethod.NullToParam(roomForm.getPagesize(),"10")),
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
		return mapping.findForward("byLp");
	}
	
	/**得到房间管理所需的树 */
	public ActionForward getMTree(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		List<ViewBuild> treeforlist = null; 
		List<DTree> treeList = new ArrayList<DTree>();
		RoomForm roomForm = (RoomForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		roomForm.setLpId(lpId);
		/** 到此为止*/
		try {
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
			tree.setUrl("getRoom.do?method=searchRoomByLp&id=" + build.getPk() + "&tablename=" + build.getTableName());
			treeList.add(tree);
		}
		
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp", "getRoom.do?method=searchRoomByLp&id="+lpId+ "&tablename= 'e_lp'") ;
		return mapping.findForward("tree");
	}
	
}

