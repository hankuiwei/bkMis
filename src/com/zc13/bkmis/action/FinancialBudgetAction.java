package com.zc13.bkmis.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.FinancialBudgetForm;
import com.zc13.bkmis.mapping.FinancialBudget;
import com.zc13.bkmis.service.IFinancialBudgetService;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.MUser;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/*******************************************************************************
 * @author 李影
 */
public class FinancialBudgetAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private IFinancialBudgetService ifinancialBudgetService = null;
	public IFinancialBudgetService getIfinancialBudgetService() {
		return ifinancialBudgetService;
	}

	public void setIfinancialBudgetService(
			IFinancialBudgetService ifinancialBudgetService) {
		this.ifinancialBudgetService = ifinancialBudgetService;
	}

	/** 从spring容器里得到ifinancialBudgetService */
	public void setServlet(ActionServlet actionservlet) {
		super.setServlet(actionservlet);
		ifinancialBudgetService = (IFinancialBudgetService) getBean("ifinancialBudgetService");
	}

	public ActionForward getList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		FinancialBudgetForm financialBudgetForm=(FinancialBudgetForm)form;
		String year = GlobalMethod.NullToSpace(request.getParameter("year"));
		financialBudgetForm.setYear(year);
		
		String currentpage = request.getParameter("currentpage");
		
		String pagesize = request.getParameter("pagesize");
		List<FinancialBudget> financialBudgetList = null;
		List<MUser> userList = null;
		try{
			financialBudgetList = ifinancialBudgetService.getList(financialBudgetForm,true);
			
			userList = ifinancialBudgetService.getUserList();
			//取总条数
			int totalcount = ifinancialBudgetService.queryCounttotal(financialBudgetForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == financialBudgetList || financialBudgetList.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/financialBudget.do?method=getList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/financialBudget.do?method=getList" , Integer.parseInt(GlobalMethod.NullToParam(pagesize, "10")), 
						Integer.parseInt(GlobalMethod.NullToParam(currentpage, "1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			
		}catch(Exception e){
			logger.error("财务预算信息查询失败!FinancialBudgetAction.getList()。详细信息：" + e.getMessage());
		}
		request.setAttribute("financialBudgetList", financialBudgetList);
		request.setAttribute("year", year);
		request.setAttribute("userList", userList);
		return mapping.findForward("list");
	}
	
	
	//添加财务预算信息
	public ActionForward addfinan(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		FinancialBudgetForm form2 = (FinancialBudgetForm)form;
		
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer userid = (Integer) map1.get("userid");
		form2.setCreateUser(userid);
		
		try{
			ifinancialBudgetService.addfinan(form2);
		}catch(Exception e){
			logger.error("添加财务预算信息查询失败!FinancialBudgetAction.addfinan(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("添加财务预算信息查询失败!FinancialBudgetAction.addfinan(form2)!",e);
		}
		return null;
	}
	
	
		// 根据id得到财务预算信息
		public ActionForward getById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			String id = request.getParameter("id");
			FinancialBudget bean = null;
			try {
				bean = ifinancialBudgetService.getfin(id);
			} catch (Exception e) {
				logger.error("根据id得到财务预算信息失败!ifinancialBudgetService.getfin(id).详细信息：" + e.getMessage());
				throw new BkmisWebException("根据id得到财务预算信息失败!ifinancialBudgetService.getfin(id)", e);
			}
			request.setAttribute("bean", bean);
			return mapping.findForward("edit");
		}
	
			
		// 编辑财务预算信息
		public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			FinancialBudgetForm form2 = (FinancialBudgetForm) form;
			try {
				ifinancialBudgetService.editfin(form2);

			} catch (Exception e) {
				logger.error("编辑财务预算信息失败!ifinancialBudgetService.edit(form2).详细信息：" + e.getMessage());
				throw new BkmisWebException("编辑财务预算信息失败!ifinancialBudgetService.edit(form2)!", e);
			}
			return null;
		}
	
	
	// 根据id删除财务预算信息
		public ActionForward delById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			String ids = request.getParameter("ids");
			try {
				String[] strings = ids.split(",");
				for (int i = 0; i < strings.length; i++) {
					ifinancialBudgetService.delfin(strings[i]);
				}
				response.sendRedirect("financialBudget.do?method=getList");
			} catch (Exception e) {
				logger.error("删除财务预算信息失败!FinancialBudgetAction.delById(form2).详细信息：" + e.getMessage());
				throw new BkmisWebException("删除财务预算信息失败!FinancialBudgetAction.delById(form2)!", e);
			}
			return null;
		}

}