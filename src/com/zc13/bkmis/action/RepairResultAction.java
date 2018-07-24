package com.zc13.bkmis.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.RepairResultForm;
import com.zc13.bkmis.mapping.RepairResult;
import com.zc13.bkmis.service.IRepairResultService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/*******************************************************************************
 * @author 李影
 */
public class RepairResultAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private IRepairResultService irepairResultService = null;
	
	public IRepairResultService getIrepairResultService() {
		return irepairResultService;
	}
	public void setIrepairResultService(IRepairResultService irepairResultService) {
		this.irepairResultService = irepairResultService;
	}

	/** 从spring容器里得到irepairResultService */
	public void setServlet(ActionServlet actionservlet) {
		super.setServlet(actionservlet);
		irepairResultService = (IRepairResultService) getBean("irepairResultService");
	}

	public ActionForward getList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		RepairResultForm repairResultForm=(RepairResultForm)form;
		String currentpage = request.getParameter("currentpage");
		
		String pagesize = request.getParameter("pagesize");
		List<RepairResult> repairResultList = null;
		String forward = "repairResultList";
		if(request.getParameter("flag")!=null&&!"1".equals(request.getParameter("flag"))){
			forward = "repairResultRadioList";
		}
		try{
			repairResultList = irepairResultService.getList(repairResultForm,true);
			//取总条数
			int totalcount = irepairResultService.queryCounttotal(repairResultForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == repairResultList || repairResultList.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/RepairResult.do?method=getList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/RepairResult.do?method=getList" , Integer.parseInt(GlobalMethod.NullToParam(pagesize, "10")), 
						Integer.parseInt(GlobalMethod.NullToParam(currentpage, "1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			
		}catch(Exception e){
			logger.error("客户报修内容查询失败!RepairResultAction.getList()。详细信息：" + e.getMessage());
		}
		request.setAttribute("repairResultList", repairResultList);
		return mapping.findForward(forward);
	}
	
	
	//添加客户报修内容
	public ActionForward addClient(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		RepairResultForm form2 = (RepairResultForm)form;
		try{
			irepairResultService.addClient(form2);
		}catch(Exception e){
			logger.error("添加客户报修内容失败!RepairResultAction.addClient(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("添加客户报修内容失败!RepairResultAction.addClient(form2)!",e);
		}
		return null;
	}
	
	
	// 根据id得到客户报修项目
		public ActionForward getById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			String id = request.getParameter("id");
			RepairResult bean = null;
			try {
				bean = irepairResultService.getRepair(id);
			} catch (Exception e) {
				logger.error("添加客户报修内容失败!irepairResultService.addRepair(form2).详细信息：" + e.getMessage());
				throw new BkmisWebException("添加客户报修失败!irepairResultService.addRepair(form2)!", e);
			}
			request.setAttribute("bean", bean);
			return mapping.findForward("edit");
		}
	
		
		// 编辑客户报修项目
		public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			RepairResultForm form2 = (RepairResultForm) form;
			try {
				irepairResultService.editRepair(form2);

			} catch (Exception e) {
				logger.error("编辑客户报修内容失败!irepairResultService.edit(form2).详细信息：" + e.getMessage());
				throw new BkmisWebException("编辑客户报修内容失败!irepairResultService.edit(form2)!", e);
			}
			return null;
		}
	
	
	// 根据id删除客户报修内容
		public ActionForward delById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			String ids = request.getParameter("ids");
			try {
				String[] strings = ids.split(",");
				for (int i = 0; i < strings.length; i++) {
					irepairResultService.delRepair(strings[i]);
				}
				response.sendRedirect("RepairResult.do?method=getList");
			} catch (Exception e) {
				logger.error("删除客户报修内容失败!RepairResultAction.delById(form2).详细信息：" + e.getMessage());
				throw new BkmisWebException("删除客户报修内容失败!RepairResultAction.delById(form2)!", e);
			}
			return null;
		}

}