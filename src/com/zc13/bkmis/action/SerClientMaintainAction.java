package com.zc13.bkmis.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
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

import com.zc13.bkmis.form.PersonnelForm;
import com.zc13.bkmis.form.RepairForm;
import com.zc13.bkmis.form.SerClientMaintainForm;
import com.zc13.bkmis.form.SetMaterialsForm;
import com.zc13.bkmis.mapping.DepotMaterialType;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.EMaintainDispatch;
import com.zc13.bkmis.mapping.ERooms;
import com.zc13.bkmis.mapping.RepairResult;
import com.zc13.bkmis.mapping.SerClientMaintain;
import com.zc13.bkmis.mapping.SerMaintainProject;
import com.zc13.bkmis.mapping.SerMaterialConsume;
import com.zc13.bkmis.service.IBuildsManageService;
import com.zc13.bkmis.service.IPersonnelService;
import com.zc13.bkmis.service.IRepairService;
import com.zc13.bkmis.service.IRoomManageService;
import com.zc13.bkmis.service.ISerClientMaintainService;
import com.zc13.bkmis.service.ISetMaterialManageService;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.msmis.service.ISysParamManagerService;
import com.zc13.util.Constant;
import com.zc13.util.Contants;
import com.zc13.util.DTree;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/***
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：13:35:50 PM
 */
public class SerClientMaintainAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private ISerClientMaintainService  iSerClientMaintainService= null;
	private ISetMaterialManageService setmaterialsService;
	private IRepairService iRepairService;
	private IPersonnelService ipersonnelServicce;
	ISysParamManagerService iSysParamManagerService = null; 
	private IRoomManageService iroomManageService = null;
	private IBuildsManageService ibuildsManageService = null;
	/** 从spring容器里得到iCustomerRepairService*/
	public void setServlet(ActionServlet actionservlet){
		super.setServlet(actionservlet);
		iRepairService = (IRepairService)getBean("IRepairService");
		iSerClientMaintainService = (ISerClientMaintainService)getBean("ISerClientMaintainService");
		setmaterialsService = (ISetMaterialManageService)getBean("setmaterialsService");
		iSysParamManagerService = (ISysParamManagerService)getBean("ISysParamManagerService");
		ipersonnelServicce = (IPersonnelService)getBean("ipersonnelService");
		iroomManageService = (IRoomManageService)getBean("iroomManageService");
		ibuildsManageService = (IBuildsManageService)getBean("ibuildsManageService");
	}
	
	/** 查询客户报修列表 */
	public ActionForward getList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		SerClientMaintainForm form2 = (SerClientMaintainForm)form;	
		String isPrint = GlobalMethod.NullToSpace(request.getParameter("isPrint"));
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		form2.setLpId(lpId);
		/** 到此为止*/
		try {
			// 进行转码
			//form2.setCx_sendedMan(java.net.URLDecoder.decode(GlobalMethod.NullToSpace(form2.getCx_sendedMan()), "UTF-8"));
			form2.setSelstatus(java.net.URLDecoder.decode(GlobalMethod.NullToSpace(form2.getSelstatus()), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		List<SerClientMaintain> list = null;
		List<EBuilds> list2 = null;
		List list3 = null;
		List<ERooms> list4 = null;
		try{
			PersonnelForm personnelForm = new PersonnelForm();
			personnelForm.setLpId(lpId);
			//可派工人列表
			list3 = ipersonnelServicce.showPersonnel(personnelForm,false);
			//房间列表
			list4 = iroomManageService.getRoomE(null);
			if(form2.getCx_isEnabled() == null){
				form2.setCx_isEnabled("1");
			}
			//报修列表
			list = iSerClientMaintainService.getClientList(form2,true);
			//将sendedMan的id替换为name
			if(list!=null&&list.size()>0){
				for(int i = 0;i<list.size();i++){
					SerClientMaintain s = list.get(i);
					String sendedMan = s.getSendedMan();
					String sendedManName = ipersonnelServicce.getNamesByPersonnelIds(sendedMan);
					s.setSendedMan(sendedManName);
				}
			}
			//楼栋列表
			list2 = iRepairService.buildList(lpId);
			//取总条数
			int totalcount = iSerClientMaintainService.getCountList(form2);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			String params = "&cx_buildId="+form2.getCx_buildId()+"&cx_sendedMan="+GlobalMethod.NullToSpace(form2.getCx_sendedMan())+"&selstatus="+GlobalMethod.NullToSpace(form2.getSelstatus())+"&begindate="+GlobalMethod.NullToSpace(form2.getBegindate())+"&enddate="+GlobalMethod.NullToSpace(form2.getEnddate())+"&flag="+GlobalMethod.NullToSpace(form2.getFlag());
			if (null == list || list.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/client.do?method=getList"+params, 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/client.do?method=getList"+params, Integer.parseInt(GlobalMethod.NullToParam(form2.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(form2.getCurrentpage(),"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			request.setAttribute("list", list);
			request.setAttribute("list2", list2);
			request.setAttribute("list3", list3);
			request.setAttribute("list4", list4);
			request.setAttribute("isPrint", isPrint);
		}catch(Exception e){
			logger.error("高级查询失败!SerClientMaintainAction.getList().详细信息：" + e.getMessage());
			throw new BkmisWebException("高级查询失败，SerClientMaintainAction.getList()!",e);
		}

		String forward = "list";
		return mapping.findForward(forward);
	}
	
	/**
	 * 转向添加报修信息页面
	 */
	public ActionForward gotoAdd(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		RepairForm repairForm = new RepairForm();
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		repairForm.setLpId(lpId);
		/** 到此为止*/
		//报修项目列表
		List<SerMaintainProject> list = null;
		//楼栋列表
		List<EBuilds> list2 = null;
		try{
			list = iRepairService.getRepairList(repairForm,false);
			list2 = iRepairService.buildList(lpId);
		} catch (Exception e) {
			logger.error("查询项目名称和楼幢菜单，SerClientMaintainAction.gotoAdd()。详细信息："+e.getMessage());
			throw new BkmisWebException("查询项目名称和楼幢菜单，SerClientMaintainAction.gotoAdd()!",e);
		}
		request.setAttribute("list",list);
		request.setAttribute("list2",list2);
		return mapping.findForward("goto");
	}
	
	//添加客户报修
	@SuppressWarnings("unchecked")
	public ActionForward addClient(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		SerClientMaintainForm form2 = (SerClientMaintainForm)form;
		try{
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
			form2.setLpId(lpId);
			form2.setUserName(userName);
			iSerClientMaintainService.addClient(form2);
			//writeLog(userName, "添加客户报修", "添加了报修NAME" + form2.getName() + "信息", Contants.CUSTOMERREPAIRS);
		}catch(Exception e){
			logger.error("添加客户报修项目失败!SerClientMaintainAction.addClient(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("添加客户报修项目失败!SerClientMaintainAction.addClient(form2)!",e);
		}
		return null;
	}
	
	/**
	 * 派工
	 */
	public ActionForward dispatching(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response){
		
		return null;
	}
	
	//处理客户报修项目
	@SuppressWarnings("unchecked")
	public ActionForward dealClient(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response){
		SerClientMaintainForm form2 = (SerClientMaintainForm)form;	
		String flag = request.getParameter("flag");
		if(flag.equals("dispose")){
			String [] bhArr= request.getParameter("bh").split(",");//获得编号
			String [] slArr= request.getParameter("sl").split(",");//获得数量
			if( bhArr[0]!=""){//判断第一个字符是否为空
				for(int i=0;i<bhArr.length;i++){
					//开始减少材料数量
					iSerClientMaintainService.updateDepotMaterial(bhArr[i], slArr[i]);	
				}
			}
		}
		try{
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			Integer userId = (Integer)map.get("userid");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
			form2.setUserName(userName);
			form2.setUserId(userId);
			form2.setLpId(lpId);
			iSerClientMaintainService.dealClient(request,form2);
			//response.sendRedirect("client.do?method=getList");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("处理客户报修失败!SerClientMaintainAction.dealClient(form2).详细信息：" + e.getMessage());
		}
		return this.getList(mapping, form, request, response);
	}
	
	public ActionForward updateSL(HttpServletRequest request,HttpServletResponse response){
		String shs = request.getParameter("sh");
		System.out.println(shs);
		
		return null;
		 
	}
	
	//得到区域列表
	public ActionForward codeList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String string = "";
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try{
			SysCode sysCode = new SysCode();
			sysCode.setCodeType("publicarea");
			List<SysCode> sysCodeList = iSysParamManagerService.getSysCode(sysCode);
			for(int i=0;i<sysCodeList.size();i++){
				string = string + sysCodeList.get(i).getCodeName() + ",";			
			}
			string = string.substring(0,string.length()-1);
		}catch(Exception e){
			logger.error("查询区域列表失败!SerClientMaintainAction.codeList().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询区域列表失败!SerClientMaintainAction.codeList()!",e);
		}
		out.print(string);
		return null;
	}
	
	/**
	 * 根据id得到客户报修项目
	 */
	public ActionForward getById(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		RepairForm repairForm = new RepairForm();
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		repairForm.setLpId(lpId);
		/** 到此为止*/
		String userName = GlobalMethod.NullToSpace(map1.get("username").toString());
		List<SerMaintainProject> list = null;
		List<SysCode> list2 = null;
		List<EBuilds> list3 = null;
		List<ERooms> list4 = null;
		List<SysCode> list5 = null;
		List<SerMaterialConsume> list6 = null;
		List listperson = null;
		//List<HrPersonnel> sendedManList = null;//派工人员列表
		List<EMaintainDispatch> dispatchList = null;//该报修已被派工人员列表
		String id = request.getParameter("id");
		String forward = request.getParameter("forward");
		SerClientMaintain bean = null;
		String clientName = "";//客户名称
		List<RepairResult> repairResultList = ipersonnelServicce.queryRepairResult();//2013-04-09
		request.setAttribute("repairResultList", repairResultList);
		try{
			PersonnelForm personnelForm = new PersonnelForm();
			listperson = ipersonnelServicce.showPersonnel(personnelForm,false);
			list = iRepairService.getRepairList(repairForm,false);
			dispatchList = iSerClientMaintainService.getDispatchListByMaintainId(id);
			SysCode sysCode = new SysCode();
			sysCode.setCodeType("publicarea");
			list2 = iSysParamManagerService.getSysCode(sysCode);
			bean = (SerClientMaintain)iSerClientMaintainService.getClientMaintainById(id,forward);
			if(bean.getRoomId()!=null&&bean.getRoomId()!=0){
				//根据房间id获取客户名称
				List<Map> tempList = ibuildsManageService.getClientAndRoomById(bean.getRoomId());
				if(tempList!=null&&tempList.size()>0){
					clientName = (String)tempList.get(0).get("client_name");
				}
			}
			//设置派工执行人
			if(GlobalMethod.NullToSpace(bean.getSendDutyMan()).equals("")&&!"openDeal".equals(forward)){
				bean.setSendDutyMan(userName);
			}
			
			//sendedManList = ipersonnelServicce.selectPersonalByIds(bean.getSendedMan());
			list3 = iRepairService.buildList(lpId);
			if(bean.getBuildId()!=0){
				list4 = iRepairService.roomList(bean.getBuildId());
			}
			list5 = iSysParamManagerService.getCodeList();
			list6 = iSerClientMaintainService.getConsume(id);
		}catch(Exception e){
			logger.error("查询客户报修失败!SerClientMaintainAction.getById(id).详细信息：" + e.getMessage());
			throw new BkmisWebException("查询客户报修失败!SerClientMaintainAction.getById(id)!",e);
		}
		request.setAttribute("clientName",clientName);
		request.setAttribute("bean",bean);
		request.setAttribute("list",list);
		request.setAttribute("list2",list2);
		request.setAttribute("list3",list3);
		request.setAttribute("list4",list4);
		request.setAttribute("list5",list5);
		request.setAttribute("list6",list6);
		//request.setAttribute("sendedManList",sendedManList);
		request.setAttribute("dispatchList",dispatchList);
		request.setAttribute("listperson", listperson);
		if("deal".equals(forward)){//转向处理报修
			return mapping.findForward("deal");
		}
		if("editWorkSituation".equals(forward)){//转向派工页面
			String newCode = iSerClientMaintainService.getNewCode();
			request.setAttribute("newCode", newCode);
			return mapping.findForward("editWorkSituation");
		}
		if("openDeal".equals(forward)){//转向查询报修信息页面
			return mapping.findForward("openDeal");
		}
		if("feedback".equals(forward)){//转向客户反馈页面
			return mapping.findForward("feedback");
		}
		if("printPGD".equals(forward)){//转向打印派工单页面
			return mapping.findForward("printPGD");
		}
		return mapping.findForward("edit");
	}
	
	/**
	 * 根据id删除客户报修
	 */
	public ActionForward delById(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String ids = request.getParameter("ids");	
		try{			
			String[] strings = ids.split(",");
			for(int i=0;i<strings.length;i++){
				SerClientMaintain bean = (SerClientMaintain)iSerClientMaintainService.getClientMaintainById(strings[i],"");
				Map map = (Map)request.getSession().getAttribute("userInfo");
				String userName = GlobalMethod.NullToSpace(map.get("username").toString());
				logDetail(userName, Contants.DELETE,"客户报修",Contants.L_SERVICE, "3", bean);
				iSerClientMaintainService.delete(bean);
			}
			//response.sendRedirect("client.do?method=getList");		
		}catch(Exception e){
			logger.error("删除客户报修失败!iSerClientMaintainService.delById().详细信息：" + e.getMessage());
			throw new BkmisWebException("删除客户报修失败!iSerClientMaintainService.delById()!",e);
		}
		return this.getList(mapping, form, request, response);
	}
	
	/**
	 * 编辑客户报修项目
	 */
	public ActionForward edit(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		SerClientMaintainForm form2 = (SerClientMaintainForm)form;
		
		try{
			
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
			form2.setUserName(userName);
			form2.setLpId(lpId);
			iSerClientMaintainService.editClient(form2);
		}catch(Exception e){
			logger.error("编辑客户报修失败!iSerClientMaintainService.addRepair(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("编辑客户报修失败!iSerClientMaintainService.addRepair(form2)!",e);
		}
		return null;
	}
	
	/**
	 * 设置到场时间
	 */
	public ActionForward setReachTime(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		SerClientMaintainForm form2 = (SerClientMaintainForm)form;
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
		form2.setUserName(userName);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			iSerClientMaintainService.setReachTime(form2);
			out.print("1");
		} catch (Exception e) {
			out.print("0");
			e.printStackTrace();
		}
		out.close();
		return null;
	}
	
	/**
	 * 设置离场时间
	 */
	public ActionForward setLeaveTime(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		SerClientMaintainForm form2 = (SerClientMaintainForm)form;
		
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		Integer userId = (Integer)map.get("userid");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
		form2.setUserName(userName);
		form2.setUserId(userId);
		form2.setLpId(lpId);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			iSerClientMaintainService.setLeaveTime(form2);
			out.print("1");
		} catch (Exception e) {
			out.print("0");
			e.printStackTrace();
		}
		out.close();
		return null;
	}
	
	//保存材料消耗
	public ActionForward saveConsume(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		try{
			SerClientMaintainForm form2 = (SerClientMaintainForm)form;
			iSerClientMaintainService.saveConsume(request,form2);
		} catch (Exception e) {
			logger.error("保存材料消耗，SerClientMaintainAction.saveConsume()。详细信息："+e.getMessage());
			throw new BkmisWebException("保存材料消耗，SerClientMaintainAction.saveConsume()!",e);
		}
		return null;
	}
	
	//查询材料出处
	public ActionForward queryConsume(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		SerClientMaintainForm form2 = (SerClientMaintainForm)form;
		List<SerMaterialConsume> list = null;
		List<SysCode> list1 = null;
		try{
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			form2.setLpId(lpId);
			/** 到此为止*/
			list = iSerClientMaintainService.queryConsume(form2,true);
			list1 = iSysParamManagerService.getCodeList();
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == list || list.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/client.do?method=queryConsume", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/client.do?method=queryConsume", Integer.parseInt(GlobalMethod.NullToParam(form2.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(form2.getCurrentpage(),"1")), form2.getTotalcount());
			}
			request.setAttribute("pagination", htmlPagination);
			request.setAttribute("list", list);
			request.setAttribute("list1", list1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询材料出处，SerClientMaintainAction.saveConsume()。详细信息："+e.getMessage());
			//throw new BkmisWebException("查询材料出处，SerClientMaintainAction.saveConsume()!",e);
		}
		return mapping.findForward("consume");
	}
	
	//根据楼幢名称得到房间列表
	public ActionForward roomList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		PrintWriter out = null;
		String string1 = "";
		String string2 = "";
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int id = Integer.parseInt(request.getParameter("id"));
		List<ERooms> list = null;
		try{
			list = iRepairService.roomList(id);
			for(int i=0;i<list.size();i++){
				string1 =string1 + list.get(i).getRoomCode() + "," ;
				string2 = string2 + list.get(i).getRoomId() + ",";
			}
		} catch (Exception e) {
			logger.error("查询房间列表信息失败，SerClientMaintainAction.buildList()。详细信息："+e.getMessage());
			throw new BkmisWebException("查询房间列表信息失败，SerClientMaintainAction.buildList()!",e);
		}
		String string = string1 + ";" + string2;
		out.print(string);
		return null;
	}
	
	//显示材料设置
	public ActionForward showMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			List materialsList = new ArrayList();
			List<DTree> treeList = new ArrayList<DTree>();
			try {
				materialsList = setmaterialsService.getMaterialsList();
			}catch(Exception e){
				logger.error("tree的获得失败!SetMaterialsManageAction.showMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("tree的获得失败，SetMaterialsManageAction.showMaterials()!",e);
			}
			treeList.add(new DTree("1","材料设置信息","",""));
			for(int i = 0;i<materialsList.size();i++){
				DTree tree =  new DTree();
				DepotMaterialType dst = (DepotMaterialType)materialsList.get(i);
				tree.setId(dst.getId().toString());
				tree.setName(dst.getName());
				tree.setParentID(dst.getParentid().toString());
				tree.setUrl("client.do?method=selectMaterials&Code="+dst.getCode()+"&dmtId="+dst.getId().toString()+"&typeName="+dst.getName());
				treeList.add(tree);
			}
			request.getSession().setAttribute("treeList", treeList);
			request.getSession().setAttribute("mainFramJsp", "setmaterials.do?method=selectMaterials");
			return mapping.findForward("MaterialsList");
	}
	
	//点击不同的类别显示不同信息
	public ActionForward selectMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
			
			try{
				SetMaterialsForm smf = (SetMaterialsForm)form;
				/** 下面夹着的代码是为了实现多楼盘的*/
				Map map1 = (Map)request.getSession().getAttribute("userInfo");
				Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
				smf.setLpId(lpId);
				/** 到此为止*/
				//获取点击类别代号及名称,id
				String code = request.getParameter("Code");
				String name = request.getParameter("typeName");
				String strdmtId = request.getParameter("dmtId");
				
				List list = new ArrayList();
				if(strdmtId != null && strdmtId != ""){
					int dmtId = Integer.parseInt(strdmtId);
					smf.setDmtId(dmtId);
					//判断是否为根节点
					list = setmaterialsService.judgeNode(smf);
				}
				String s = "";
				if(name != "" && name != null){
					s = new String(name.getBytes("iso-8859-1"),"utf-8");
				}
				smf.setType(code);
				setmaterialsService.selectMaterials(smf,true);
				//添加分页信息
				String htmlPagination = "&nbsp;";
				if (null == smf.getMaterialList() || smf.getMaterialList().size() <= 0) {//如果没有记录，那么默认如下
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/client.do?method=selectMaterials&Code="+code, 10, 1, 0);
				} else {
					htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
							+ "/client.do?method=selectMaterials&Code="+code, Integer.parseInt(GlobalMethod.NullToParam(smf.getPagesize(),"10")),
							Integer.parseInt(GlobalMethod.NullToParam(smf.getCurrentpage(),"1")), smf.getTotalcount());
				}
				request.setAttribute("pagination", htmlPagination);
				
				request.setAttribute("typeCode", code);
				request.setAttribute("typeName", s);
				request.setAttribute("judgeList", list.size());
			}catch(Exception e){
				logger.error("点击tree获得信息失败!SetMaterialsManageAction.selectMaterials().详细信息：" + e.getMessage());
				throw new BkmisWebException("点击tree获得信息失败，SetMaterialsManageAction.selectMaterials()!",e);
			}
			return mapping.findForward("mainFramJsp");
	}
	//导出报表
	public ActionForward explorClient(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response){
		
		SerClientMaintainForm form2 = (SerClientMaintainForm)form;	
		String isPrint = GlobalMethod.NullToSpace(request.getParameter("isPrint"));
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		form2.setLpId(lpId);
		/** 到此为止*/
		try{
			List<SerClientMaintain> list = iSerClientMaintainService.getClientList(form2,true);
			List<ERooms> list4 = iroomManageService.getRoomE(null);//房间列表
			//将sendedMan的id替换为name
			if(list!=null&&list.size()>0){
				for(int i = 0;i<list.size();i++){
					SerClientMaintain s = list.get(i);
					String sendedMan = s.getSendedMan();
					String sendedManName = ipersonnelServicce.getNamesByPersonnelIds(sendedMan);
					s.setSendedMan(sendedManName);
				}
			}
			//表头
			String[] cellHeader = Constant.EXCEL_CLIENT_DETAIL;
			String[] cellValue = Constant.EXCEL_CLIENT_VALUE;
			//定义文件名
			String sheetName = "客户报修";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list, sheetName, cellHeader, cellValue, new SerClientMaintain());
			
			response.setBufferSize(100*1024);
			
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "客户报修"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}catch(Exception e){
			log.error("导出员工excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	//导出材料出处的报表
	public ActionForward explorMaterial(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response){
		
		String department = request.getParameter("cx_department");
		String materialName = request.getParameter("materialName");
		String begin = request.getParameter("begindate");
		String end = request.getParameter("enddate");
		try{
			List list = iSerClientMaintainService.explorMaterialList(department, materialName, begin, end);
			//表头
			String[] cellHeader = Constant.EXCEL_CLIENTMATERIAL_DETAIL;
			String[] cellValue = Constant.EXCEL_CLIENTMATERIAL_VALUE;
			//定义文件名
			String sheetName = "材料出处";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list, sheetName, cellHeader, cellValue, new SerMaterialConsume());
			
			response.setBufferSize(100*1024);
			
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "材料出处"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}catch(Exception e){
			log.error("导出材料excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	//根据不同的id得到材料类别
	public ActionForward sendMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, SQLException {
		SerClientMaintainForm clientForm = (SerClientMaintainForm)form;
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
		clientForm.setUserName(userName);
		clientForm.setLpId(lpId);
		PrintWriter out = null;
		String string = "";
		try{
			out = response.getWriter();
			String phones = GlobalMethod.NullToSpace(request.getParameter("phones"));//电话号码
			String names = GlobalMethod.NullToSpace(request.getParameter("names"));//姓名
			String contents = GlobalMethod.NullToSpace(request.getParameter("contents"));//短信内容
			iSerClientMaintainService.sendMessage(clientForm);
			out.print("1");
		}catch(Exception e){
			e.printStackTrace();
			out.print("0");
			//throw new BkmisWebException("点击tree获得信息失败，GetMeterialAction.selectMaterials()!",e);
		}
		return null;
	}
	
	/**
	 * 获得报修状态和派工状态
	 * 报修状态包括：派工等待，维修中，维修等待，维修结束
	 * 派工状态包括：已派出，已到场，已离场
	 */
	public ActionForward getRepairAndDispatchStatus(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String maintainId = GlobalMethod.NullToSpace(request.getParameter("id"));//报修id
		String statuss = "";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			statuss = iSerClientMaintainService.getRepairAndDispatchStatusByMaintainId(maintainId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(statuss);
		return null;
	}
	
	/**
	 * 将报修状态置为无效
	 */
	public ActionForward setInvalid(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
		String maintainId = GlobalMethod.NullToSpace(request.getParameter("id"));//报修id
		PrintWriter out = null;
		try {
			out = response.getWriter();
			iSerClientMaintainService.setInvalid(maintainId,userName);
			out.print("1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("0");
		}
		return null;
	}
}

