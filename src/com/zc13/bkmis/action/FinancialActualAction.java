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

import com.zc13.bkmis.form.FinancialActualForm;
import com.zc13.bkmis.mapping.FinancialActual;
import com.zc13.bkmis.service.IFinancialActualService;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.MUser;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/*******************************************************************************
 * @author 李影
 */
public class FinancialActualAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private IFinancialActualService ifinancialActualService = null;
	
	public IFinancialActualService getIfinancialActualService() {
		return ifinancialActualService;
	}

	public void setIfinancialActualService(
			IFinancialActualService ifinancialActualService) {
		this.ifinancialActualService = ifinancialActualService;
	}

	/** 从spring容器里得到ifinancialActualService */
	public void setServlet(ActionServlet actionservlet) {
		super.setServlet(actionservlet);
		ifinancialActualService = (IFinancialActualService) getBean("ifinancialActualService");
	}

	public ActionForward getList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		FinancialActualForm financialActualForm=(FinancialActualForm)form;
		String year = GlobalMethod.NullToSpace(request.getParameter("year"));
		financialActualForm.setYear(year);
		
		String currentpage = request.getParameter("currentpage");
		String pagesize = request.getParameter("pagesize");
		
		List<FinancialActual> financialActualList = null;
		List<MUser> userList = null;
		try{
			financialActualList = ifinancialActualService.getList(financialActualForm,true);
			
			userList = ifinancialActualService.getUserList();
			//取总条数
			int totalcount = ifinancialActualService.queryCounttotal(financialActualForm);
			//添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == financialActualList || financialActualList.size() <= 0) {//如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/financialActual.do?method=getList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
						+ "/financialActual.do?method=getList" , Integer.parseInt(GlobalMethod.NullToParam(pagesize, "10")), 
						Integer.parseInt(GlobalMethod.NullToParam(currentpage, "1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			
		}catch(Exception e){
			logger.error("实际财物查询失败!FinancialActualAction.getList()。详细信息：" + e.getMessage());
		}
		request.setAttribute("financialActualList", financialActualList);
		request.setAttribute("year", year);
		request.setAttribute("userList", userList);
		return mapping.findForward("list");
	}
	
	
	//添加实际财务内容
	public ActionForward addfinan(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		FinancialActualForm form2 = (FinancialActualForm)form;
		
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer userid = (Integer) map1.get("userid");
		form2.setCreateUser(userid);
		
		try{
			ifinancialActualService.addfinan(form2);
		}catch(Exception e){
			logger.error("添加实际财务内容失败!FinancialActualAction.addfinan(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("添加实际财务内容失败!FinancialActualAction.addfinan(form2)",e);
		}
		return null;
	}
	
	
	// 根据id得到实际财务
		public ActionForward getById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			String id = request.getParameter("id");
			FinancialActual bean = null;
			try {
				bean = ifinancialActualService.getfin(id);
			} catch (Exception e) {
				logger.error("根据ID得到实际财务!ifinancialActualService.getfin(id).详细信息：" + e.getMessage());
				throw new BkmisWebException("根据ID得到实际财务!ifinancialActualService.getfin(id)", e);
			}
			request.setAttribute("bean", bean);
			return mapping.findForward("edit");
		}
	
			
		// 编辑实际财务
		public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			FinancialActualForm form2 = (FinancialActualForm) form;
			try {
				ifinancialActualService.editfin(form2);

			} catch (Exception e) {
				logger.error("编辑实际财务失败!ifinancialActualService.edit(form2).详细信息：" + e.getMessage());
				throw new BkmisWebException("编辑实际财务失败!ifinancialActualService.edit(form2)!", e);
			}
			return null;
		}
	
	// 根据id删除实际财务内容
		public ActionForward delById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			String ids = request.getParameter("ids");
			try {
				String[] strings = ids.split(",");
				for (int i = 0; i < strings.length; i++) {
					ifinancialActualService.delfin(strings[i]);
				}
				response.sendRedirect("financialActual.do?method=getList");
			} catch (Exception e) {
				logger.error("删除实际财务失败!ifinancialActualService.delById(form2).详细信息：" + e.getMessage());
				throw new BkmisWebException("删除实际财务失败!ifinancialActualService.delById(form2)!", e);
			}
			return null;
		}

}