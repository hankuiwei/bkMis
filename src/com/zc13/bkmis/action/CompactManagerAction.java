package com.zc13.bkmis.action;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

import com.zc13.bkmis.form.CompactManagerForm;
import com.zc13.bkmis.mapping.Compact;
import com.zc13.bkmis.mapping.CompactChange;
import com.zc13.bkmis.mapping.CompactQuit;
import com.zc13.bkmis.mapping.CompactRelet;
import com.zc13.bkmis.service.ICompactManagerService;
import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.bkmis.service.ICustomerRoomService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.Constant;
import com.zc13.util.Contants;
import com.zc13.util.ExplortExcel;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/***
 * @author 秦彦韬
 * Date：Dec 24, 2010
 * Time：15:15:50 PM
 */
public class CompactManagerAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private ICompactManagerService iCompactManagerService;
	private ICustomerRoomService iCustomerRoomService;
	private ICostTransactService costTransactService;
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public ICompactManagerService getICompactManagerService() {
		return iCompactManagerService;
	}
	public void setICompactManagerService(ICompactManagerService compactManagerService) {
		iCompactManagerService = compactManagerService;
	}
	/** 从spring容器里得到*/
	public void setServlet(ActionServlet actionservlet){
		super.setServlet(actionservlet);
		iCompactManagerService = (ICompactManagerService)getBean("ICompactManagerService");
		iCustomerRoomService = (ICustomerRoomService)getBean("ICustomerRoomService");
		costTransactService = (ICostTransactService)getBean("costTransactService");
	}
	/** 获取合同管理列表 */
	public ActionForward getList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactManagerForm form2 = (CompactManagerForm)form;
		List<Compact> list = null;
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			form2.setLpId(lpId);
			/** 到此为止*/
			list = iCompactManagerService.getCompactList(form2);
			int totalcount = iCompactManagerService.getCount(form2);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			String url = null;
			if(form2.getFlag()!=null&&!"".equals(form2.getFlag())){
				url = "/compact.do?method=getList&flag="+form2.getFlag();
			}else{
				url = "/compact.do?method=getList";
			}
			if (null == list || list.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+url, 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+url, Integer.parseInt(GlobalMethod.NullToParam(form2.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(form2.getCurrentpage(),"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			request.setAttribute("compactList", list);
		} catch (Exception e) {
			logger.error("查询合同列表!CompactManagerAction.getList().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询合同列表，CompactManagerAction.getList()!",e);
		}
		if("relet".equals(form2.getFlag())){
			return mapping.findForward("getList");
		}else if("quit".equals(form2.getFlag())){
			return mapping.findForward("quit");
		}else if("change".equals(form2.getFlag())){
			return mapping.findForward("change");
		}else{
		return mapping.findForward("list");
	}
	}

	/** 保存合同退租 */
	public ActionForward saveQuit(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactManagerForm form2 = (CompactManagerForm)form;
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			form2.setLpId(lpId);
			/** 到此为止*/
			iCompactManagerService.saveQuit(form2);
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			writeLog(userName, "添加", "添加了退租合同代码为" + form2.getQuitCode() + "的信息", Contants.L_COMPACTMANAGE,"迁出");
		} catch (Exception e) { 
			logger.error("保存合同退租!CompactManagerAction.saveQuit().详细信息：" + e.getMessage());
			throw new BkmisWebException("保存合同退租，CompactManagerAction.saveQuit()!",e);
		}
		request.setAttribute("flag",true);
		return mapping.findForward("gotoQuit");
	}

	/** 编辑合同退租 */
	public ActionForward editQuit(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactManagerForm form2 = (CompactManagerForm)form;
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			form2.setLpId(lpId);
			/** 到此为止*/
			iCompactManagerService.editQuit(form2);
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			writeLog(userName, "编辑", "编辑了退租合同代码为" + form2.getQuitCode() + "信息", Contants.COMPACTDEAL,"迁出");
		} catch (Exception e) { 
			logger.error("编辑合同退租!CompactManagerAction.editQuit().详细信息：" + e.getMessage());
			throw new BkmisWebException("编辑合同退租，CompactManagerAction.editQuit()!",e);
		}
		request.setAttribute("flag",true);
		return mapping.findForward("edit");
	}
	/** 根据退租id找到退租合同的信息 */
	public ActionForward goEditQuit(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		int id = Integer.parseInt(request.getParameter("id"));
		String flag = request.getParameter("flag");
		Object[] objects = null;
		try {
			objects = iCompactManagerService.goEditQuit(id);
		} catch (Exception e) { 
			logger.error("查询退租!CompactManagerAction.goEditQuit().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询退租，CompactManagerAction.goEditQuit()!",e);
		}
		request.setAttribute("objs",objects);
		if("look".equals(flag)){
			return mapping.findForward("look");
		}if("gotolookDetail".equals(flag)){
			return mapping.findForward("gotolookDetail");
		}if("printMoveApplyDetail".equals(flag)){
			return mapping.findForward("printMoveApplyDetail");
		}else{
			return mapping.findForward("gotoEditQuit");
		}
	}
	/** 获取合同续租列表 */
	public ActionForward getContractList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactManagerForm form2 = (CompactManagerForm)form;
		List<CompactRelet> list = null;
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			form2.setLpId(lpId);
			/** 到此为止*/
			list = iCompactManagerService.getContractList(form2);
			int totalcount = iCompactManagerService.getContractCount(form2);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == list || list.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/compact.do?method=getContractList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/compact.do?method=getContractList", Integer.parseInt(GlobalMethod.NullToParam(form2.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(form2.getCurrentpage(),"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			request.setAttribute("list", list);
		} catch (Exception e) { 
			logger.error("查询合同续租列表!CompactManagerAction.getContractList().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询合同续租列表，CompactManagerAction.getContractList()!",e);
		}
		return mapping.findForward("contractList");
	}
	
	/** 删除合同 */
	public ActionForward deleteCompact(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		CompactManagerForm form2 = (CompactManagerForm)form;
		//跳转路径标志
		String forward = GlobalMethod.NullToSpace(request.getParameter("forward"));
		iCompactManagerService.deleteCompact(form2);
		if(forward.equals("toContractList")){//跳转到合同续租情况列表
			return this.getContractList(mapping, form, request, response);
		}
		return null;
	}
	
	/** 得到合同变更列表 */
	public ActionForward getCompactChangeList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactManagerForm form2 = (CompactManagerForm)form;
		List<CompactChange> list = null;
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			form2.setLpId(lpId);
			/** 到此为止*/
			list = iCompactManagerService.getCompactChangeList(form2);
			int totalcount = iCompactManagerService.getCompactChangeCount(form2);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == list || list.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/compact.do?method=getCompactChangeList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/compact.do?method=getCompactChangeList", Integer.parseInt(GlobalMethod.NullToParam(form2.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(form2.getCurrentpage(),"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			request.setAttribute("list", list);
		} catch (Exception e) { 
			logger.error("查询合同变更列表!CompactManagerAction.getContractList().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询合同变更列表，CompactManagerAction.getContractList()!",e);
		}
		return mapping.findForward("changeList");
	}
	
	/** 得到合同退租列表 */
	public ActionForward getDelContractList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactManagerForm form2 = (CompactManagerForm)form;
		String forward = GlobalMethod.NullToSpace(request.getParameter("forward"));
		if(forward.equals("")){
			forward = "quitList";
		}
		List<CompactQuit> list = null;
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			form2.setLpId(lpId);
			/** 到此为止*/
			list = iCompactManagerService.getDelContractList(form2);
			int totalcount = iCompactManagerService.getDelContractCount(form2);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == list || list.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/compact.do?method=getDelContractList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/compact.do?method=getDelContractList", Integer.parseInt(GlobalMethod.NullToParam(form2.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(form2.getCurrentpage(),"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			request.setAttribute("list", list);
			request.setAttribute("forward", forward);
		} catch (Exception e) { 
			logger.error("查询合同续租列表!CompactManagerAction.getContractList().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询合同续租列表，CompactManagerAction.getContractList()!",e);
		}
		return mapping.findForward(forward);
	}
	
	//导出合同列表excel
	@SuppressWarnings("unchecked")
	public ActionForward exportCompactExcel(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {
			CompactManagerForm form2 = (CompactManagerForm)form;
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			form2.setLpId(lpId);
			/** 到此为止*/
			//list中存放的就是当前页面上显示的所有数据
			iCompactManagerService.getCompactListForE(form2);
			List<Compact> list = form2.getList();
			
			//表头
			String[] cellHeader = Constant.EXCEL_COMPACT_PERSON;
			String[] cellValue = Constant.EXCEL_COMPACT_VALUE;
			//定义文件名
			String sheetName = "合同信息列表";
			HSSFWorkbook workbook = ExplortExcel.creatWorkbook(list,sheetName,cellHeader,cellValue,new Compact());
			response.setBufferSize(100*1024);//设置最大缓存
			workbook.write(response.getOutputStream());
			//弹出另存为窗口
			super.setResponseHeader(response, "合同信息列表"+GlobalMethod.getTime2()+".xls");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			log.error("导出合同excel出错，详细信息："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/** 根据合同id得到合同的信息 */
	public ActionForward gotoQuit(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String compactId = request.getParameter("id");
		Compact compact = null;
		String code = null;
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			/** 到此为止*/
			compact = iCompactManagerService.getCompactById(compactId);
			//2012-12-13进行修改
			code = iCustomerRoomService.getQuitCode(lpId);
			//code = iCustomerRoomService.getCompactCode(lpId);//2012-12-13修改合同迁出的编号，应该从迁出表获取最大的编号不是利用合同的编号
		} catch (Exception e) { 
			logger.error("根据合同id查询合同!CompactManagerAction.gotoQuit().详细信息：" + e.getMessage());
			throw new BkmisWebException("根据合同id查询合同，CompactManagerAction.gotoQuit()!",e);
		}
		request.setAttribute("code",code);
		request.setAttribute("compact",compact);
		return mapping.findForward("gotoQuit");
	}
	
	
	/** 得到需要审批的合同列表 */
	public ActionForward getPassList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactManagerForm form1 = (CompactManagerForm)form;
		String forword = null;
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			form1.setLpId(lpId);
			/** 到此为止*/
			forword = iCompactManagerService.getPassList(form1);
		} catch (Exception e) { 
			logger.error("编辑合同退租!CompactManagerAction.editQuit().详细信息：" + e.getMessage());
			throw new BkmisWebException("编辑合同退租，CompactManagerAction.editQuit()!",e);
		}
		this.my_saveToken(request);
		System.out.println("forword:"+forword);
		return mapping.findForward(forword);
	}
	
	/** 得到需要校验的合同列表 */
	public ActionForward getCheckList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactManagerForm form1 = (CompactManagerForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		form1.setLpId(lpId);
		/** 到此为止*/
		String option = GlobalMethod.NullToSpace(request.getParameter("option"));
		if(option.equals("decodeURI")){//如果需要转码
			try {
				form1.setType(java.net.URLDecoder.decode(GlobalMethod.NullToSpace(request.getParameter("type")), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		String forword = null;
		try {
			forword = iCompactManagerService.getCheckList(form1);
		} catch (Exception e) {
			logger.error("编辑合同退租!CompactManagerAction.getCheckList().详细信息：" + e.getMessage());
			throw new BkmisWebException("编辑合同退租，CompactManagerAction.getCheckList()!",e);
		}
		this.my_saveToken(request);
		return mapping.findForward(forword);
	}
	
	/**
	 * 取消提交（取消校验）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:Apr 18, 2011 
	 * Time:10:53:43 AM
	 */
	public ActionForward cancelChecked(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		if(!this.my_isTokenValid(request)){
			return this.getCheckList(mapping, form, request, response);
		}
		String id = GlobalMethod.NullToSpace(request.getParameter("id"));
		String type = GlobalMethod.NullToSpace(request.getParameter("type"));
		String forward = GlobalMethod.NullToSpace(request.getParameter("forward"));//用在进入合同详细页面点击取消审批
		try {
			iCustomerRoomService.cancelChecked(id,type);
			request.setAttribute("message", "操作成功！");
		} catch (BkmisServiceException e) {
			request.setAttribute("message", "操作失败！");
			e.printStackTrace();
		}
		if(!forward.equals("")){
			request.setAttribute("flag1", true);
			return mapping.findForward(forward);
		}else{
			return this.getCheckList(mapping, form, request, response);
		}
		
	}
	
	
	/** 审批租赁合同 
	 *  与下面方法的区别，内部详情使用
	 * */
	public ActionForward applyCompact(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactManagerForm form1 = (CompactManagerForm)form;
		try {
			//调用执行计算未交定金的用户数量
			costTransactService.updateAwokeTask4PressEarnest();
			//更新任务表中未缴纳押金和预收款的客户数量
			costTransactService.updateAwokeTask4PressRentDeposit();
			costTransactService.updateAwokeTask4PressDecorationDeposit();
			costTransactService.updateAwokeTask4PressAdvance();
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			form1.setUserName(userName);
			iCompactManagerService.applyCompact(form1);
			//writeLog(userName, "审批", "审批通过了租赁合同id为" + form1.getId() + "的信息", Contants.COMPACT);
		} catch (Exception e) { 
			logger.error("审批租赁合同!CompactManagerAction.applyCompact().详细信息：" + e.getMessage());
			throw new BkmisWebException("审批租赁合同，CompactManagerAction.applyCompact()!",e);
		}
		return mapping.findForward("lookCompact");
	}
	
	/** 审批租赁合同 
	 * 	外部列表使用
	 * */
	public ActionForward nolookApplyCompact(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactManagerForm form1 = (CompactManagerForm)form;
		try {
			
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			form1.setUserName(userName);
			iCompactManagerService.applyCompact(form1);
			//调用执行计算未交定金的用户数量
			costTransactService.updateAwokeTask4PressEarnest();
			//更新任务表中未缴纳押金和预收款的客户数量
			costTransactService.updateAwokeTask4PressRentDeposit();
			costTransactService.updateAwokeTask4PressDecorationDeposit();
			costTransactService.updateAwokeTask4PressAdvance();
			//writeLog(userName, "审批", "审批通过了租赁合同id为" + form1.getId() + "的信息", Contants.COMPACT);
		} catch (Exception e) { 
			logger.error("审批租赁合同!CompactManagerAction.applyCompact().详细信息：" + e.getMessage());
			throw new BkmisWebException("审批租赁合同，CompactManagerAction.applyCompact()!",e);
		}
		request.setAttribute("flag11", true);
		return mapping.findForward("pass1");
	}
	
	/** 检查退租合同状态 */
	public ActionForward checkQuit(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String id = request.getParameter("id");
		String data = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			data = iCompactManagerService.checkQuit(Integer.parseInt(id));
		} catch (Exception e) { 
			logger.error("检查退租合同状态!CompactManagerAction.checkQuit().详细信息：" + e.getMessage());
			throw new BkmisWebException("检查退租合同状态，CompactManagerAction.checkQuit()!",e);
		}
		out.print(data);
		return null;
	}
	
	/**
	 * 审批变更合同和续租合同
	 * 进入详情的页面使用，与下面的那个方法不同之处在于，返回时的处理不同。
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:Apr 19, 2011 
	 * Time:2:57:59 PM
	 */
	public ActionForward applyChangeOrRelet(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		//合同id
		String compactId = request.getParameter("compactId");
		//变更或者续租表记录的id
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String flag = request.getParameter("flag");
		String str = "";
		String str1 = null;
		try {
			if("change".equals(flag)){
				str = "变更";
				str1 = Contants.COMPACTCHANGE;
			}else{
				str = "续租";
				str1 = Contants.COMPACTRELET;
			}
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			iCompactManagerService.applyCompact(compactId,id,type,flag,userName);
			if("1".equals(type)){
				costTransactService.updateAwokeTask4PressRentDeposit();
				costTransactService.updateAwokeTask4PressDecorationDeposit();
			}
		} catch (Exception e) { 
			logger.error("审批变更合同和续租合同!CompactManagerAction.applyChangeOrRelet().详细信息：" + e.getMessage());
			throw new BkmisWebException("审批变更合同和续租合同，CompactManagerAction.applyChangeOrRelet()!",e);
		}
		request.setAttribute("flag",true);
		if("change".equals(type)){
			return mapping.findForward("lookChange");
		}else{
			return mapping.findForward("lookRelet");
		}
	}
	
	/**
	 * 列表页面使用
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:Apr 19, 2011 
	 * Time:2:57:42 PM
	 */
	public ActionForward noapplyChangeOrRelet(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String compactId = request.getParameter("compactId");
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String flag = request.getParameter("flag");
		String str = "";
		String str1 = null;
		try {
//			if("change".equals(flag)){
//				str = "变更";
//				str1 = Contants.COMPACTCHANGE;
//			}else{
//				str = "续租";
//				str1 = Contants.COMPACTRELET;
//			}
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
			iCompactManagerService.applyCompact(compactId,id,type,flag,userName);
			if("1".equals(type)){
				costTransactService.updateAwokeTask4PressRentDeposit();
				costTransactService.updateAwokeTask4PressDecorationDeposit();
			}
		} catch (Exception e) { 
			logger.error("审批变更合同和续租合同!CompactManagerAction.applyChangeOrRelet().详细信息：" + e.getMessage());
			throw new BkmisWebException("审批变更合同和续租合同，CompactManagerAction.applyChangeOrRelet()!",e);
		}
		request.setAttribute("flag11",true);
		if("change".equals(type)){
			return mapping.findForward("pass4");
		}else{
			return mapping.findForward("pass2");
		}
	}
	
	public ActionForward quitDetail(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		String quitId = GlobalMethod.NullToSpace(request.getParameter("quitId"));
		int id = Integer.parseInt(quitId);
		Object[] objects = null;
		try {
			objects = iCompactManagerService.goEditQuit(id);
		} catch (Exception e) { 
			logger.error("查询退租!CompactManagerAction.goEditQuit().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询退租，CompactManagerAction.goEditQuit()!",e);
		}
		request.setAttribute("objs",objects);
		return mapping.findForward("quitDetail");
	}
	
	/** 获取所有合同管理列表 */
	public ActionForward getAllCompactList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		CompactManagerForm form2 = (CompactManagerForm)form;
		List<Compact> list = null;
		try {
			/** 下面夹着的代码是为了实现多楼盘的*/
			Map map1 = (Map)request.getSession().getAttribute("userInfo");
			Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
			form2.setLpId(lpId);
			/** 到此为止*/
			list = iCompactManagerService.getAllCompactList(form2);
			int totalcount = iCompactManagerService.getAllCompactCount(form2);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			String url = null;
			if(form2.getFlag()!=null&&"".equals(form2.getFlag())){
				url = "/compact.do?method=getAllCompactList";
			}else{
				url = "/compact.do?method=getAllCompactList";
			}
			if (null == list || list.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+url, 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+url, Integer.parseInt(GlobalMethod.NullToParam(form2.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(form2.getCurrentpage(),"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			request.setAttribute("compactList", list);
		} catch (Exception e) {
			logger.error("查询合同列表!CompactManagerAction.getList().详细信息：" + e.getMessage());
			throw new BkmisWebException("查询合同列表，CompactManagerAction.getList()!",e);
		}
		return mapping.findForward("allList");
	}
}

