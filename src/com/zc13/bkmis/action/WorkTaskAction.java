package com.zc13.bkmis.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.CostTransactForm;
import com.zc13.bkmis.form.WorkTaskForm;
import com.zc13.bkmis.mapping.CompactTask;
import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.bkmis.service.ICustomerRoomService;
import com.zc13.bkmis.service.IWorkTaskService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/**
 * 客服平台工作任务单查询等操作的管理
 * @author daokui
 * @Date Mar 22, 2011
 * @Time 10:13:02 AM
 */
public class WorkTaskAction extends BasicAction{

	Logger logger = Logger.getLogger(this.getClass());
	private IWorkTaskService workTaskService;
	private ICustomerRoomService iCustomerRoomService ;
	private ICostTransactService icostransactServiceImpl;
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		workTaskService = (IWorkTaskService)getBean("workTaskService");
		iCustomerRoomService = (ICustomerRoomService)getBean("ICustomerRoomService");
		icostransactServiceImpl = (ICostTransactService)getBean("costTransactService");
	}
	/**
	 * 得到所有已经正式入驻的客户的工作任务单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getWorkTaskList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		WorkTaskForm workTaskForm = (WorkTaskForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		workTaskForm.setLpId(lpId);
		/** 到此为止*/
		try {
			//得到所有预定客户和其数量
			workTaskService.getWorkTaskList(workTaskForm);
			int totalcount = 0;
			if(workTaskForm.getWorkTaskList()!=null&&workTaskForm.getWorkTaskList().size()>0){

				totalcount = workTaskForm.getWorkTaskList().get(0).getTotalcount();
			}
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == workTaskForm.getWorkTaskList() || workTaskForm.getWorkTaskList().size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/workTask.do?method=getWorkTaskList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/workTask.do?method=getWorkTaskList", Integer.parseInt(GlobalMethod.NullToParam(workTaskForm.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(workTaskForm.getCurrentpage(),"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
		} catch (Exception e) {
			logger.error("获取工作任务单失败!CustomerRoomAction.getDestineList().详细信息：" + e.getMessage());
			throw new BkmisWebException("获取工作任务单失败，CustomerRoomAction.getDestineList()!",e);
		}
		request.setAttribute("list", workTaskForm.getWorkTaskList());
		return mapping.findForward("list");
	}
	/**
	 * 任务回馈页面调用的方法。方法的内容几乎和上面的一样，不一样的只在返回的页面不同而已。我也想过要把俩写在一个方法力，然后传一个key值以区别哪一个，但是那样的话肯定显的更乱。
	 * 俗话说嘛，不怕代码多，反正不会因为单纯的多而影响效率，就怕代码乱，自己都找不到北了，那还弄个屁啊！
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getWorkTaskList2(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		WorkTaskForm workTaskForm = (WorkTaskForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		workTaskForm.setLpId(lpId);
		/** 到此为止*/
		String modify = GlobalMethod.NullToParam(request.getParameter("modify"), "0");
		try {
			//得到所有预定客户和其数量
			workTaskService.getWorkTaskList(workTaskForm);
			int totalcount = 0;
			if(workTaskForm.getWorkTaskList()!=null&&workTaskForm.getWorkTaskList().size()>0){
				totalcount = workTaskForm.getWorkTaskList().get(0).getTotalcount();
			}
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == workTaskForm.getWorkTaskList() || workTaskForm.getWorkTaskList().size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+"/workTask.do?method=getWorkTaskList2", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/workTask.do?method=getWorkTaskList2", Integer.parseInt(GlobalMethod.NullToParam(workTaskForm.getPagesize(),"10")),
						Integer.parseInt(GlobalMethod.NullToParam(workTaskForm.getCurrentpage(),"1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
		} catch (Exception e) {
			logger.error("获取工作任务单失败!CustomerRoomAction.getDestineList().详细信息：" + e.getMessage());
			throw new BkmisWebException("获取工作任务单失败，CustomerRoomAction.getDestineList()!",e);
		}
		request.setAttribute("list", workTaskForm.getWorkTaskList());
		request.setAttribute("modify", modify);
		return mapping.findForward("list2");
	}
	/**
	 * 查看一条任务单详情
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward lookTaskInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		String compactId = GlobalMethod.NullToParam(request.getParameter("compactId"), "");
		String forward = GlobalMethod.NullToSpace(request.getParameter("forward"));
		if(forward.equals("")){
			forward = "lookInfo";
		}
		CompactTask compactTask = new CompactTask();
		try {
			compactTask = iCustomerRoomService.getTask(Integer.parseInt(compactId));
		} catch (Exception e) {
			logger.error("获得任务单!CustomerRoomAction.getList().详细信息：" + e.getMessage());
			throw new BkmisWebException("获得任务单，CustomerRoomAction.getList()!",e);
		}
		request.setAttribute("compactTask", compactTask);
		return mapping.findForward(forward);
		
	}
	/**
	 * 跳转到编辑一条任务单详情的页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editTaskInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		String compactId = GlobalMethod.NullToParam(request.getParameter("compactId"), "");
		CompactTask compactTask = new CompactTask();
		try {
			compactTask = iCustomerRoomService.getTask(Integer.parseInt(compactId));
		} catch (Exception e) {
			logger.error("获得任务单!CustomerRoomAction.getList().详细信息：" + e.getMessage());
			throw new BkmisWebException("获得任务单，CustomerRoomAction.getList()!",e);
		}
		this.my_saveToken(request);//使用自定义token机制，防止重复刷新
		request.setAttribute("compactTask", compactTask);
		return mapping.findForward("edit");
		
	}
	/**
	 * 修改一个任务单的记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward modify(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		//防止重复刷新
		if(!this.my_isTokenValid(request)){
			return this.getWorkTaskList2(mapping, form, request, response);
		}
		WorkTaskForm workTaskForm = (WorkTaskForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		workTaskForm.setLpId(lpId);
		/** 到此为止*/
		try {
			workTaskService.modify(workTaskForm);
			request.setAttribute("message", "修改成功!");
//			response.sendRedirect("workTask.do?method=getWorkTaskList2&modify=1");
		} catch (Exception e) {
			request.setAttribute("message", "修改失败!");
			logger.error("修改任务单失败!CustomerRoomAction.getList().详细信息：" + e.getMessage());
			throw new BkmisWebException("修改任务单失败，CustomerRoomAction.getList()!",e);
		}
		
		return this.getWorkTaskList2(mapping, form, request, response);
		
	}
	/**
	 * 判断该客户是否已经缴纳完所有押金和预售房租
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward checkIsDepositAndAdvance(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		

		PrintWriter out = null;
		CostTransactForm form2 = new CostTransactForm();
		Integer clientId = Integer.parseInt(GlobalMethod.NullToParam(request.getParameter("clientId"), "0"));
		try {
			String isDepositAndAdvance = "1";
			out = response.getWriter();
			form2.setClientId(clientId);
			List depositlist = icostransactServiceImpl.getPressDepositClient(form2);
			List advancelist = icostransactServiceImpl.getPressAdvanceClient(form2);
			if(depositlist!=null && depositlist.size()>0){//即如果能查到该客户的押金记录，表明该客户没有缴纳全
				isDepositAndAdvance = "0";
			}
//			if(advancelist!=null && advancelist.size()>0){//即如果能查到该客户的预收房租记录，表明该客户没有缴纳全
//				isDepositAndAdvance = "0";
//			}
			out.print(isDepositAndAdvance);
		} catch (Exception e) {
			logger.error("检验合同状态!CustomerRoomAction.checkCompactStatus().详细信息：" + e.getMessage());
			throw new BkmisWebException("检验合同状态，CustomerRoomAction.checkCompactStatus()!",e);
		}
		
		return null;
		
	}
}
