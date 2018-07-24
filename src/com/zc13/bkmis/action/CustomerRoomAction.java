package com.zc13.bkmis.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.CompactRoomForm;
import com.zc13.bkmis.form.CostTransactForm;
import com.zc13.bkmis.form.DestineForm;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.bkmis.service.ICustomerRoomService;
import com.zc13.bkmis.service.IRoomManageService;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.Constant;
import com.zc13.util.Contants;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

import net.sf.json.JSONObject;

/***
 * 客户信息管理模块的相关
 * @author 秦彦韬
 * Date：Dec 16, 2010
 * Time：5:15:50 PM
 */
public class CustomerRoomAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private IRoomManageService iroomManageService = null;
	private ICustomerRoomService iCustomerRoomService = null;
	private ICostTransactService iCostTransactService = null;
	private ICostTransactService costTransactService;
	/** 从spring容器里得到iroomManageService*/
	/** 从spring容器里得到iCustomerRoomService*/
	public void setServlet(ActionServlet actionservlet){
		super.setServlet(actionservlet);
		iroomManageService = (IRoomManageService)getBean("iroomManageService");
		iCustomerRoomService = (ICustomerRoomService)getBean("ICustomerRoomService");
		iCostTransactService = (ICostTransactService)getBean("costTransactService");
		costTransactService = (ICostTransactService)getBean("costTransactService");
	}
	
	/** 得到高级查询所需的参数 */
	public ActionForward getSysParameter(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		List<SysCode> roomTypelist = null;
		List<SysCode> towardlist = null;
		List<SysCode> statuslist = null;
		Map<String, List<SysCode>> map = null;

		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		/** 到此为止*/
		try{
			map = iroomManageService.getAdvancedInfo(lpId);
			roomTypelist =  map.get("roomTypelist");
			towardlist =  map.get("towardlist");
			statuslist = map.get("statuslist");
			request.setAttribute("roomTypelist", roomTypelist);
			request.setAttribute("towardlist", towardlist);
			request.setAttribute("statuslist", statuslist);
		}catch(Exception e){
			logger.error("高级查询所需的参数获取失败!CustomerRoomAction.getSysParameter().详细信息：" + e.getMessage());
			throw new BkmisWebException("高级查询所需的参数获取失败，CustomerRoomAction.getSysParameter()!",e);
		}
		return null;
	}
	
	/** 查询合同管理列表 */
	public ActionForward getList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		List<Object[]> list = null;
		List<ELp> list1 = null;
		String currentpage = request.getParameter("currentpage");
		String pagesize = request.getParameter("pagesize");
		String clientName = request.getParameter("clientName");
		String roomCode = request.getParameter("roomCode");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String isNotice = request.getParameter("isNotice");
		String code = request.getParameter("code");
		String key = GlobalMethod.NullToParam(request.getParameter("key"), "0");
		String notice = GlobalMethod.NullToParam(request.getParameter("notice"), "0");
		String ruzhu = GlobalMethod.NullToParam(request.getParameter("ruzhu"), "0");
		String compactId = GlobalMethod.NullToParam(request.getParameter("compactId"), "0");
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		String lpId = GlobalMethod.NullToParam((map1.get("userlp").toString()),"0");
		/** 到此为止*/
		if("1".equals(key)){
			
			//endDate = GlobalMethod.getEndDate(GlobalMethod.getTime(), 120);//2013-03-02 提前一个月进行提醒
			endDate = GlobalMethod.getEndDate(GlobalMethod.getTime(), 30);
		}
		
		try {
		
			list = iCustomerRoomService.getCompactList(key,currentpage,code, pagesize,clientName,roomCode,beginDate,endDate,lpId,isNotice);
			int totalcount = iCustomerRoomService.getCount(clientName,roomCode,beginDate,endDate,lpId,isNotice);
			list1 = iCustomerRoomService.getElp();
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == list || list.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/customer.do?method=getList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/customer.do?method=getList", Integer.parseInt(GlobalMethod.NullToParam(pagesize,"10")),
						Integer.parseInt(GlobalMethod.NullToParam(currentpage,"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
		} catch (Exception e) {
			logger.error("查询合同列表!CustomerRoomAction.getList().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询合同列表，CustomerRoomAction.getList()!",e);
		}
		request.setAttribute("list",list);
		request.setAttribute("list1",list1);
		request.setAttribute("clientName",clientName);
		request.setAttribute("roomCode",roomCode);
		request.setAttribute("beginDate",beginDate);
		request.setAttribute("endDate",endDate);
		request.setAttribute("lpId",lpId);
		request.setAttribute("isNotice",isNotice);
		request.setAttribute("notice",notice);
		request.setAttribute("ruzhu",ruzhu);
		request.setAttribute("compactId",compactId);
		request.setAttribute("code",code);
		return mapping.findForward("list");
	}
	
	/** 跳转到入住页面 */
	public ActionForward goAdd(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String compactCode = null;
		String code = null;
		List<CompactClient> list = null;
		Map<String,List<SysCode>> map = null;
		DestineForm destineForm = (DestineForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		destineForm.setLpId(Integer.valueOf(lpId));
		/** 到此为止*/
		try {
			list = iCustomerRoomService.getAllClientList(lpId);
			compactCode = iCustomerRoomService.getCompactCode(lpId);
			code = iCustomerRoomService.getCustomerCode(lpId);
			map = iCustomerRoomService.getSysCode(lpId);
		} catch (Exception e) {
			logger.error("合同代码的获得失败!CustomerRoomAction.goAdd().详细信息：" + e.getMessage());
			throw new BkmisWebException("合同代码的获得失败，CustomerRoomAction.goAdd()!",e);
		}
		request.setAttribute("compactCode",compactCode);
		request.setAttribute("code",code);
		request.setAttribute("list",list);
		request.setAttribute("lpId",lpId);
		request.setAttribute("map",map);
		return mapping.findForward("goAdd");
	}
	
	/** 根据楼盘id和费用类型id得到收费标准列表 */
	public ActionForward costStandList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String lpId = request.getParameter("lpId");
		String costType = request.getParameter("costType");
		String str = request.getParameter("str");
		String lpids = request.getParameter("lpIds");
		String type = request.getParameter("type");
		PrintWriter out = null;
		String data = "";
		try {
			out = response.getWriter();
			data = iCustomerRoomService.costStandList(lpId,costType,str,lpids,type);
		} catch (Exception e) {
			logger.error("收费标准的获得失败!CustomerRoomAction.coatStandList().详细信息：" + e.getMessage());
			throw new BkmisWebException("收费标准的获得失败，CustomerRoomAction.coatStandList()!",e);
		}
		out.print(data);
		return null;
	}
	
	/** 添加收费标准 */
	public ActionForward addRoomCost(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String ids = GlobalMethod.NullToSpace(request.getParameter("ids"));//房间id，以;分割
		String beginDate = GlobalMethod.NullToParam(request.getParameter("beginDate"), "");
		String endDate = GlobalMethod.NullToParam(request.getParameter("endDate"), "");
		
		List<ERooms> list = new ArrayList<ERooms>();
		List<CCosttype> list1 = new ArrayList<CCosttype>();
		Set<Object> set = new HashSet<Object>();//楼盘id集合
		boolean string = true;//房间是否属于同一个楼盘
		String lpIds = "";
		try {
			String[] strings = ids.split(";");
			for(int i=0;i<strings.length;i++){
				list.add(iCustomerRoomService.getRoomById(strings[i]));
			}
			for(int i=0;i<list.size();i++){
				set.add(list.get(i).getEBuilds().getELp().getLpId());
			}
			Iterator<Object> iterator = set.iterator();
			while (iterator.hasNext()) {
				lpIds += String.valueOf(iterator.next()) + ",";
			}
			if(set!=null&&set.size()>1){
				string = false;
			}
			list1 = iCustomerRoomService.getCostType();
		} catch (Exception e) {
			logger.error("添加房间费用!CustomerRoomAction.addRoomCost().详细信息：" + e.getMessage());
			throw new BkmisWebException("添加房间费用，CustomerRoomAction.addRoomCost()!",e);
		}
		if(string){//如果房间属于同一个楼盘
			request.setAttribute("set",set.iterator().next());//将楼盘id的值赋给set
		}
		request.setAttribute("roomList", list);//
		request.setAttribute("beginDate", beginDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("ids", ids);//房间id，以;分割
		request.setAttribute("costList", list1);//费用类型列表
		request.setAttribute("string", string);
		request.setAttribute("lpIds",lpIds);
		return mapping.findForward("addCost");
	}
	
	/** 根据房间id得到房间 */
	public ActionForward getRoomById(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String roomId = request.getParameter("roomId");
		PrintWriter out = null;
		String data = "";
		try {
			out = response.getWriter();
			ERooms rooms = iCustomerRoomService.getRoomById(roomId);
			data = rooms.getRoomId()+","+rooms.getRoomCode()+","+rooms.getRoomFullName()+","+rooms.getConstructionArea();
		} catch (Exception e) {
			logger.error("查询房间失败!CustomerRoomAction.getRoomById().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询房间失败，CustomerRoomAction.getRoomById()!",e);
		}
		out.print(data);
		return null;
	}
	
	/** 根据客户id查找该客户是否有合同存在 */
	public ActionForward checkClient(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String ids = request.getParameter("ids");
		PrintWriter out = null;
		String data = "";
		try {
			out = response.getWriter();
			data = iCustomerRoomService.checkClientById(ids);
		} catch (Exception e) {
			logger.error("检查客户失败!CustomerRoomAction.checkClient().详细信息：" + e.getMessage());
			throw new BkmisWebException("检查客户失败，CustomerRoomAction.checkClient()!",e);
		}
		out.print(data);
		return null;
	}
	
	/** 客户迁出 */
	public ActionForward outRoom(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String id = request.getParameter("id");//合同
		String endDate = request.getParameter("endDate");
		try {
			List list = iCostTransactService.buildBillsByClientId(null, Integer.parseInt(id), endDate);
			iCustomerRoomService.saveCompactBill(list, id);

			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			iCustomerRoomService.outRoom(id,endDate,userName);
			
		} catch (Exception e) {
			logger.error("客户迁出失败!CustomerRoomAction.outRoom().详细信息：" + e.getMessage());
			throw new BkmisWebException("客户迁出失败，CustomerRoomAction.outRoom()!",e);
		}
		request.setAttribute("flag",true);
		return mapping.findForward("outRoom");
	}
	//反悔迁出
	public ActionForward returnOutRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		String id = request.getParameter("checkbox1");
		if(id != null && id.length()>0){
			iCustomerRoomService.returnOutRoom(id);
		}
		request.setAttribute("flag", true);
		return getOutCompact(mapping, form, request, response);
	}
	//查询当日迁出合同
	public ActionForward getOutCompact(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		List<Compact> list = iCustomerRoomService.getOutCompactList();
		request.setAttribute("list", list);
		return mapping.findForward("returnOutRoom");
	}

	/** 根据房间名称和费用类型名称和收费标准名称 */
	public ActionForward getNames(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String roomId = GlobalMethod.NullToSpace(request.getParameter("roomId"));//房间id
		String costtype = GlobalMethod.NullToSpace(request.getParameter("costtype"));//费用类型id
		String costStand = GlobalMethod.NullToSpace(request.getParameter("costStand"));//收费标准id
		PrintWriter out = null;
		String data = "";
		try {
			out = response.getWriter();
			data = iCustomerRoomService.getNames(roomId,costtype,costStand);
		} catch (Exception e) {
			logger.error("收费标准的获得失败!CustomerRoomAction.coatStandList().详细信息：" + e.getMessage());
			throw new BkmisWebException("收费标准的获得失败，CustomerRoomAction.coatStandList()!",e);
		}
		out.print(data + "," +roomId);
		return null;
	}
	
	
	
	/** 根据客户id得到客户 */
	public ActionForward getClientById(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String id = request.getParameter("id");
		CompactClient client = null;
		Map<String,List<SysCode>> map = null;
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			/** 到此为止*/
			client = iCustomerRoomService.getClientById(id);
			map = iCustomerRoomService.getSysCode(lpId);
		} catch (Exception e) {
			logger.error("查询客户失败!CustomerRoomAction.getClientById().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询客户失败，CustomerRoomAction.getClientById()!",e);
		}
		request.setAttribute("client",client);
		request.setAttribute("map",map);
		return mapping.findForward("gotoEditClient");
	}
	
	/** 根据客户id得到客户的信息 */
	public ActionForward getClientString(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String id = request.getParameter("id");
		//String client = null;
		CompactClient client = null;
		try {
			client = iCustomerRoomService.getClientById(id);
			client.setAnalysisClientComeGos(null);
			client.setCBills(null);
			client.setCompactRoomCoststandards(null);
			client.setEFloorMapCoordinates(null);
			JSONObject json = JSONObject.fromObject(client);
			this.render(response, json);
			//client = iCustomerRoomService.getClientString(id);
		} catch (Exception e) {
			logger.error("查询客户失败!CustomerRoomAction.getClientById().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询客户失败，CustomerRoomAction.getClientById()!",e);
		}
		return null;
	}
	
	/** 得到客户代码 */
	public ActionForward getClientCode(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String client = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			/** 到此为止*/
			client = iCustomerRoomService.getCustomerCode(lpId);
		} catch (Exception e) {
			logger.error("查询客户失败!CustomerRoomAction.getClientById().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询客户失败，CustomerRoomAction.getClientById()!",e);
		}
		out.print(client);
		return null;
	}
	
	/** 检验合同状态 */
	public ActionForward checkCompactStatus(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String id = request.getParameter("id");
		String status = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			status = iCustomerRoomService.checkCompactStatus(id);
		} catch (Exception e) {
			logger.error("检验合同状态!CustomerRoomAction.checkCompactStatus().详细信息：" + e.getMessage());
			throw new BkmisWebException("检验合同状态，CustomerRoomAction.checkCompactStatus()!",e);
		}
		out.print(status);
		return null;
	}
	
	/** 保存合同 */
	public ActionForward saveCompact(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactRoomForm form2 = (CompactRoomForm)form;
		String flag = request.getParameter("flag")==null?"":request.getParameter("flag");
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			form2.setLpId(lpId);
			/** 到此为止*/
			iCustomerRoomService.saveRoomRent(form2,flag);
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			if("relet".equals(flag)){//保存续租合同
				iCustomerRoomService.saveCompactRelet(form2);
				writeLog(userName, "添加", "对租赁合同代码为" + form2.getCode() + "的合同添加了续租信息", Contants.COMPACTRELET,"合同续租");
				writeLog(userName, "添加", "添加的续租合同code为" + form2.getCode(), Contants.COMPACT,"合同续租");
				response.sendRedirect("zc13/bkmis/contractManager/addContract.jsp?flag=true");
			}else if("change".equals(flag)) {//保存变更合同
				iCustomerRoomService.saveCompactChange(form2);
				writeLog(userName, "添加", "对租赁合同代码为" + form2.getCode() + "的合同添加了变更信息", Contants.COMPACTCHANGE,"合同变更");
				writeLog(userName, "添加", "添加的租赁合同code为" + form2.getCode(), Contants.COMPACT,"合同变更");
				response.sendRedirect("zc13/bkmis/contractManager/addCompactChange.jsp?flag=true");
			}else{
				writeLog(userName, "添加", "添加的租赁合同code为" + form2.getCode(), Contants.COMPACT,"合同");
				response.sendRedirect("customer.do?method=getList");
			}
		} catch (Exception e) {
			logger.error("保存合同失败!CustomerRoomAction.saveCompact().详细信息：" + e.getMessage());
			throw new BkmisWebException("保存合同失败，CustomerRoomAction.saveCompact()!",e);
		}
		return null;
	}
	
	/** 修改合同信息。
	 * 变更合同和续租合同的增加和编辑用到此方法
	 *  */
	public ActionForward editCompact(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactRoomForm form2 = (CompactRoomForm)form;
		String compactId = request.getParameter("compactId");
		String clientId = request.getParameter("clientId");
		String flag = request.getParameter("flag");
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			form2.setLpId(lpId);
			/** 到此为止*/
			iCustomerRoomService.editCompact(form2,compactId,clientId);
			
			/*String earneString = GlobalMethod.NullToParam(request.getParameter("earnest"), "0");
			if("1".equals(earneString)){//如果是1，那么表示应该跳转到预租界面
				response.sendRedirect("destine.do?method=getDestineList");
			}*/
			
			if("relet".equals(flag)){
				response.sendRedirect("zc13/bkmis/contractManager/addContract.jsp?flag=true");
			}else if("change".equals(flag)) {
				response.sendRedirect("zc13/bkmis/contractManager/addCompactChange.jsp?flag=true");
			}else{
				response.sendRedirect("customer.do?method=getList");
			}
			
		} catch (Exception e) {
			logger.error("编辑合同失败!CustomerRoomAction.editCompact().详细信息：" + e.getMessage());
			throw new BkmisWebException("编辑合同失败，CustomerRoomAction.editCompact()!",e);
		}
		return null;
	}
	
	/** 根据合同号得到合同信息，跳转到编辑页面 */
	public ActionForward gotoEditCompact(ActionMapping mapping,ActionForm form1,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactRoomForm form = (CompactRoomForm)form1;
		String compactChangeId = request.getParameter("id");
		String flag = request.getParameter("flag");
		List<CompactClient> list = null;
		Map<String,List<SysCode>> map = null;
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			/** 到此为止*/
			list = iCustomerRoomService.getAllClientList(lpId);
			iCustomerRoomService.getCompactMassage(form,compactChangeId,flag);
			map = iCustomerRoomService.getSysCode(lpId);
		} catch (Exception e) {
			logger.error("获取合同信息失败!CustomerRoomAction.gotoEditCompact().详细信息：" + e.getMessage());
			throw new BkmisWebException("获取合同信息失败，CustomerRoomAction.gotoEditCompact()!",e);
		}
		request.setAttribute("list",list);
		request.setAttribute("map",map);
		request.setAttribute("id1",compactChangeId);
		if(!"".equals(flag)&&flag!=null){
			return mapping.findForward(flag);
		}else{
			return mapping.findForward("edit");
		}
	}
	
	/** 根据合同号得到合同信息 */
	public ActionForward delClientById(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String ids = request.getParameter("ids");
		try {
			iCustomerRoomService.delClientById(ids);
			response.sendRedirect("customer.do?method=getClientList");
		} catch (Exception e) {
			logger.error("删除客户失败!CustomerRoomAction.delClientById().详细信息：" + e.getMessage());
			throw new BkmisWebException("删除客户失败，CustomerRoomAction.delClientById()!",e);
		}
		return null;
	}
	
	/** 根据客户id编辑客户 */
	public ActionForward editClientById(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactRoomForm form1 = (CompactRoomForm)form;
		try {
			Map map2 = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map2.get("username").toString());
			String lpId = GlobalMethod.NullToSpace(map2.get("userlp").toString());
			form1.setUserName(userName);
			form1.setLpId(Integer.valueOf(lpId));
			iCustomerRoomService.editClientById(form1);
		} catch (Exception e) {
			logger.error("编辑客户失败!CustomerRoomAction.editClientById().详细信息：" + e.getMessage());
			throw new BkmisWebException("编辑客户失败，CustomerRoomAction.editClientById()!",e);
		}
		request.setAttribute("flag",true);
		return mapping.findForward("gotoEditClient");
	}
	
	/** 根据客户管理列表 */
	public ActionForward getClientList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		List<CompactClient> list = null;
		String currentpage = request.getParameter("currentpage");
		String pagesize = request.getParameter("pagesize");
		String clientName = request.getParameter("clientName");
		String clientType = request.getParameter("clientType");
		CompactRoomForm compactRoomForm = (CompactRoomForm)form; 
		try {
			/** 下面夹着的代码是为了实现多楼盘的 */
			Map map1 = (Map) request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
			compactRoomForm.setLpId(lpId);
			/** 到此为止 */
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String lpid = GlobalMethod.NullToParam((map.get("userlp").toString()), "0");
			compactRoomForm.setLpId(Integer.valueOf(lpid));
			
			list = iCustomerRoomService.getClientList(currentpage, pagesize,compactRoomForm);
			int totalcount = iCustomerRoomService.getClientCount(compactRoomForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == list || list.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/customer.do?method=getClientList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/customer.do?method=getClientList", Integer.parseInt(GlobalMethod.NullToParam(pagesize,"10")),
						Integer.parseInt(GlobalMethod.NullToParam(currentpage,"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
		} catch (Exception e) {
			logger.error("查询客户列表!CustomerRoomAction.getClientList().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询客户列表，CustomerRoomAction.getClientList()!",e);
		}
		request.setAttribute("list",list);
		request.setAttribute("clientName",clientName);
		request.setAttribute("clientType",clientType);
		
		request.setAttribute("cusId", request.getParameter("cusId"));
		request.setAttribute("cusName", request.getParameter("cusName"));
		request.setAttribute("cusPhone", request.getParameter("cusPhone"));
		return mapping.findForward("clientList");
	}
	
	/** 得到新合同编号 */
	public ActionForward getNewCode(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		PrintWriter out = null;
		String data = null;
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			/** 到此为止*/
			out = response.getWriter();
			data = iCustomerRoomService.getCompactCode(lpId);
		} catch (Exception e) {
			logger.error("合同代码的获得失败!RoomManageAction.getNewCode().详细信息：" + e.getMessage());
			throw new BkmisWebException("合同代码的获得失败，RoomManageAction.getNewCode()!",e);
		}
		out.print(data);
		return null;
	}
	
	/** 提交审批 */
	public ActionForward applyCompact(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String id = request.getParameter("id");
		String id1 = request.getParameter("id1");
		String flag = request.getParameter("flag");
		String forword = "";
		try {
			Map map2 = (Map)request.getSession().getAttribute("userInfo");
			String userName2 = GlobalMethod.NullToSpace(map2.get("username").toString());
			//writeLog(userName2, "校验", "对租赁合同id为" + id + "的合同提交了审批", Contants.COMPACT);
			forword = iCustomerRoomService.applyCompact(id1,id,flag,userName2);
			response.sendRedirect("compact.do?method=getCheckList");
		} catch (Exception e) {
			logger.error("提交审批!RoomManageAction.applyCompact().详细信息：" + e.getMessage());
			throw new BkmisWebException("提交审批，RoomManageAction.applyCompact()!",e);
		}
		return null;
	}
	
	/** 提交审批 */
	public ActionForward applyCompact1(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String id = request.getParameter("id");
		String id1 = request.getParameter("id1");
		String flag = request.getParameter("flag");
		String forword = request.getParameter("forword");
		try {
			Map map2 = (Map)request.getSession().getAttribute("userInfo");
			String userName2 = GlobalMethod.NullToSpace(map2.get("username").toString());
			iCustomerRoomService.applyCompact(id1,id,flag,userName2);
		} catch (Exception e) {
			logger.error("提交审批!RoomManageAction.applyCompact1().详细信息：" + e.getMessage());
			throw new BkmisWebException("提交审批，RoomManageAction.applyCompact1()!",e);
		}
		request.setAttribute("flag1",true);
		return mapping.findForward(forword);
	}
	
	
	/** 根据合同号得到合同信息 */
	public ActionForward gotoLookCompact(ActionMapping mapping,ActionForm form1,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactRoomForm form = (CompactRoomForm)form1;
		String id = request.getParameter("id");
		String id1 = request.getParameter("id1");
		String flag = request.getParameter("flag");
		List<CompactClient> list = null;
		Map<String,List<SysCode>> map = null;
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			/** 到此为止*/
			list = iCustomerRoomService.getAllClientList(lpId);
			iCustomerRoomService.getCompactMassage(form,id,flag);
			map = iCustomerRoomService.getSysCode(lpId);
		} catch (Exception e) {
			logger.error("查看合同信息失败!RoomManageAction.gotoLookCompact().详细信息：" + e.getMessage());
			throw new BkmisWebException("查看合同信息失败，RoomManageAction.gotoLookCompact()!",e);
		}
		this.my_saveToken(request);
		request.setAttribute("list",list);
		request.setAttribute("map",map);
		request.setAttribute("id1",id1);
		return mapping.findForward(flag);
	}
	/** 打印客户列表 */
	public ActionForward printCustomerList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		List<CompactClient> list = null;
		String currentpage = request.getParameter("currentpage");
		String pagesize = request.getParameter("pagesize");
		String clientName = request.getParameter("clientName");
		String clientType = request.getParameter("clientType");
		CompactRoomForm compactRoomForm = (CompactRoomForm)form; 
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			compactRoomForm.setLpId(lpId);
			/** 到此为止*/
			list = iCustomerRoomService.getClientList(currentpage, pagesize,compactRoomForm);
			
		} catch (Exception e) {
			logger.error("打印客户列表!CustomerRoomAction.print().详细信息：" + e.getMessage());
			throw new BkmisWebException("打印客户列表，CustomerRoomAction.print()!",e);
		}
		request.setAttribute("list",list);
		request.setAttribute("clientName",clientName);
		request.setAttribute("clientType",clientType);
		
		return mapping.findForward("printCustomerList");
	}
	/** 打印客户入驻列表 */
	public ActionForward printCustomerCompactList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		List<Object[]> list = null;
		List<ELp> list1 = null;
		String currentpage = request.getParameter("currentpage");
		String pagesize = request.getParameter("pagesize");
		String clientName = request.getParameter("clientName");
		String roomCode = request.getParameter("roomCode");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String code = request.getParameter("code");
		String isNotice = request.getParameter("isNotice");
		String key = GlobalMethod.NullToParam(request.getParameter("status"), "0");
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		String lpId = GlobalMethod.NullToParam((map1.get("userlp").toString()),"0");
		/** 到此为止*/
		try {
			list = iCustomerRoomService.getCompactList(key,currentpage,code,pagesize,clientName,roomCode,beginDate,endDate,lpId,isNotice);
			list1 = iCustomerRoomService.getElp();
		} catch (Exception e) {
			logger.error("查询合同列表!CustomerRoomAction.getList().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询合同列表，CustomerRoomAction.getList()!",e);
		}
		request.setAttribute("list",list);
		request.setAttribute("list1",list1);
		request.setAttribute("clientName",clientName);
		request.setAttribute("roomCode",roomCode);
		request.setAttribute("beginDate",beginDate);
		request.setAttribute("endDate",endDate);
		request.setAttribute("lpId",lpId);
		request.setAttribute("isNotice",isNotice);
		return mapping.findForward("printCustomerComPactList");
	}
	/**  保存并打印工作任务单 */
	public ActionForward printTask(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactRoomForm compactRoomForm = (CompactRoomForm)form;
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			compactRoomForm.setLpId(lpId);
			/** 到此为止*/
			iCustomerRoomService.saveTask(compactRoomForm);
		} catch (Exception e) {
			logger.error("打印工作任务单失败!CustomerRoomAction.getList().详细信息：" + e.getMessage());
			throw new BkmisWebException("打印工作任务单失败，CustomerRoomAction.getList()!",e);
		}

		request.setAttribute("compactTask", compactRoomForm.getCompactTask());
		return mapping.findForward("printTask");
	}
	
	/**
	 * 通知入驻
	 * CustomerRoomAction.notice
	 */
	public ActionForward notice(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String compactId = GlobalMethod.NullToParam(request.getParameter("compactId"), "");
		//CompactRoomForm form1 = (CompactRoomForm)form;
		//CompactTask compactTask = new CompactTask();
		try {
			/**1.通知入住*/
			iCustomerRoomService.notice(Integer.parseInt(compactId));
			/**2.更新提醒表*/
			costTransactService.updateAwokeTask4PressAdvance();
			costTransactService.updateAwokeTask4PressRentDeposit();
			costTransactService.updateAwokeTask4PressDecorationDeposit();
			costTransactService.updateAwokeTask4PressENotIn();
			//iCustomerRoomService.saveTask(form1);
			//compactTask = iCustomerRoomService.getTask(Integer.parseInt(compactId));
			response.sendRedirect("customer.do?method=getList&notice=1");
		} catch (Exception e) {
			logger.error("通知入驻!CustomerRoomAction.getList().详细信息：" + e.getMessage());
			throw new BkmisWebException("通知入驻，CustomerRoomAction.getList()!",e);
		}
		//request.setAttribute("compactTask", compactTask);
		return null;
		//return mapping.findForward("printTask");
	}
	
	/**
	 * 确定正式迁出
	 * CustomerRoomAction.noticeToGo
	 */
	public ActionForward enterToGo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String compactId = request.getParameter("compactId");
		try {

			iCustomerRoomService.enterToGo(compactId);
			response.sendRedirect("compact.do?method=getDelContractList");
		} catch (Exception e) {
			logger.error("确定正式迁出!CustomerRoomAction.getList().详细信息：" + e.getMessage());
			throw new BkmisWebException("确定正式迁出，CustomerRoomAction.getList()!",e);
		}
		
		return null;
	}
	


	//查询客户信息，导出报表
	public ActionForward explorReport(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactRoomForm compactRoomForm = (CompactRoomForm)form;
		Map map2 = (Map)request.getSession().getAttribute("userInfo");
		String lpId = GlobalMethod.NullToSpace(map2.get("userlp").toString());
		compactRoomForm.setLpId(Integer.valueOf(lpId));
		try{
			List<CompactClient> list = iCustomerRoomService.explorCustomerRoomList(compactRoomForm);
			//表头
			String[] cellHeader = Constant.EXCEL_CUSTOMERMANAGE_HEADER;
			String[] cellValue = Constant.EXCEL_CUSTOMERMANAGE_VALUE;
			//定义文件名
			String sheetName = "客户列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list, sheetName, cellHeader, cellValue, new CompactClient());
			response.setBufferSize(100*1024);
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "客户列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}catch(Exception e){
			log.error("导出客户列表excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	//查询入驻信息，导出报表
	public ActionForward explorReport1(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		

		String clientName = request.getParameter("clientName");
		String roomCode = request.getParameter("roomCode");
		String status = request.getParameter("status");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		String lpId = GlobalMethod.NullToParam((map1.get("userlp").toString()),"0");
		/** 到此为止*/
		try{
			List<Compact> list = iCustomerRoomService.explorCustomerRoomList1(lpId,clientName,roomCode,status,beginDate,endDate);
			//表头
			String[] cellHeader = Constant.EXCEL_CUSTOMERRMANAGER_HEADER;
			String[] cellValue = Constant.EXCEL_CUSTOMERRMANAGER_VALUE;
			//定义文件名
			String sheetName = "入驻管理列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list, sheetName, cellHeader, cellValue, new Compact());
			response.setBufferSize(100*1024);
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, sheetName+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}catch(Exception e){
			log.error("导出入驻列表excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 下发任务通知单
	 * CustomerRoomAction.downTask
	 */
	public ActionForward downTask (ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String compactId = request.getParameter("compactId");
		CompactRoomForm form1 = (CompactRoomForm)form;
		try {
			iCustomerRoomService.getCompactMassage(form1,compactId,"");
		} catch (Exception e) {
			logger.error("打印工作任务单失败!CustomerRoomAction.downTask().详细信息：" + e.getMessage());
			throw new BkmisWebException("打印工作任务单失败，CustomerRoomAction.downTask()!",e);
		}
		
		return mapping.findForward("workTask");
	}
	/**
	 * 得到某客户的历史住房记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:Mar 22, 2011 
	 * Time:7:06:57 PM
	 */
	public ActionForward getClientsHistoryRooms (ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		try {
			String clientId = request.getParameter("clientId");
			List list = iCustomerRoomService.getClientsHistoryRooms(clientId);
			request.setAttribute("list", list);
		} catch (Exception e) {
			logger.error("得到某客户的历史住房记录!CustomerRoomAction.getClientsHistoryRooms().详细信息：" + e.getMessage());
			throw new BkmisWebException("得到某客户的历史住房记录，CustomerRoomAction.getClientsHistoryRooms()!",e);
		}
		return mapping.findForward("getClientsHistoryRoomsList");
	}
	
	/** 检验客户是否还有未缴纳的费用 */
	public ActionForward checkIsPayment(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String compactId = GlobalMethod.NullToSpace(request.getParameter("compactId"));
		String isPayment = "1";//默认为都已经缴纳
		PrintWriter out = null;
		CostTransactForm form2 = new CostTransactForm();
		try {
			out = response.getWriter();
			Compact c = iCustomerRoomService.getCompactById(compactId);
			form2.setClientId(c.getClientId());
			List depositlist = iCostTransactService.getPressDepositClient(form2);
			List advancelist = iCostTransactService.getPressAdvanceClient(form2);
			List moneylist = iCostTransactService.getPressMoneyClient(form2);
			if((depositlist!=null&&depositlist.size()>0)||(advancelist!=null&&advancelist.size()>0)||(moneylist!=null&&moneylist.size()>0)){
				isPayment = "0";
			}
		} catch (Exception e) {
			logger.error("检验客户是否还有未缴纳的费用!CustomerRoomAction.checkIsPayment().详细信息：" + e.getMessage());
			throw new BkmisWebException("检验客户是否还有未缴纳的费用，CustomerRoomAction.checkIsPayment()!",e);
		}
		out.print(isPayment);
		return null;
	}
}

