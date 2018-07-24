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

import com.zc13.bkmis.form.MerchantsActualForm;
import com.zc13.bkmis.mapping.MerchantsActual;
import com.zc13.bkmis.service.IMerchantsActualService;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/**
 * @author 李影
 * 招商实际
 */

public class MerchantsActualAction extends BasicAction {
	
	Logger logger = Logger.getLogger(this.getClass());
	private IMerchantsActualService imerchantsActualService = null;

	/** 从spring容器里得到imerchantsActualService */
	public void setServlet(ActionServlet actionservlet) {
		super.setServlet(actionservlet);
		imerchantsActualService = (IMerchantsActualService) getBean("imerchantsActualService");
	}
	
	public ActionForward getList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		MerchantsActualForm merchantsActualForm = (MerchantsActualForm) form;
		
		/*String year = GlobalMethod.NullToSpace(request.getParameter("year"));
		String month = GlobalMethod.NullToSpace(request.getParameter("month"));
		merchantsActualForm.setYear(year);
		merchantsActualForm.setMonth(month);
		String currentpage = request.getParameter("currentpage");
		String pagesize = request.getParameter("pagesize");*/
		List<MerchantsActual> list = null;
		//List<MUser> userList = null;
		List<SysCode> codeList = new ArrayList<SysCode>();
		try {
			list = imerchantsActualService.getList(merchantsActualForm, true);
			//userList = imerchantsActualService.getUserList();
			codeList = imerchantsActualService.queryCodeByType();
			// 取总条数
			int totalcount = imerchantsActualService.queryCounttotal(merchantsActualForm);
			// 添加分页信息
			String htmlPagination = "&nbsp;";
			if (null == list || list.size() <= 0) {// 如果没有记录，那么默认如下
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath() + "/merchantsActual.do?method=getList", 10, 1, 0);
			} else {
				htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath() + "/merchantsActual.do?method=getList", Integer.parseInt(GlobalMethod.NullToParam(merchantsActualForm.getPagesize(), "10")), Integer.parseInt(GlobalMethod.NullToParam(merchantsActualForm.getCurrentpage(), "1")), totalcount);
			}
			request.setAttribute("pagination", htmlPagination);
			//request.setAttribute("month", month);
			//request.setAttribute("year", year);
			request.setAttribute("form", merchantsActualForm);
			request.setAttribute("codeList", codeList);
			request.setAttribute("list", list);
		} catch (Exception e) {
			logger.error("高级查询失败!MerchantsActualAction.getList().详细信息：" + e.getMessage());
			throw new BkmisWebException("高级查询失败，MerchantsActualAction.getList()!", e);
		}
	
		return mapping.findForward("list");
	}


	//根据id得到招商计划
	public ActionForward getById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("id");
		MerchantsActual bean = null;
		List<SysCode> codeList = new ArrayList<SysCode>();
		try {
			bean = imerchantsActualService.getMerchantsActual(id);
			codeList = imerchantsActualService.queryCodeByType();
		} catch (Exception e) {
			logger.error("根据id得到招商计划失败!imerchantsActualService.getMerchantsActual(id).详细信息：" + e.getMessage());
			throw new BkmisWebException("根据id得到招商计划失败!imerchantsActualService.getMerchantsActual(id)!", e);
		}
		request.setAttribute("bean", bean);
		request.setAttribute("codeList", codeList);
		return mapping.findForward("edit");
	}
	
	/**
	 * 添加页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2013-5-9 
	 * Time:下午9:53:20
	 */
	public ActionForward addPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		List<SysCode> codeList = new ArrayList<SysCode>();
		try {
			codeList = imerchantsActualService.queryCodeByType();
		} catch (Exception e) {
			logger.error("根据id得到招商计划失败!imerchantsActualService.getMerchantsActual(id).详细信息：" + e.getMessage());
			throw new BkmisWebException("根据id得到招商计划失败!imerchantsActualService.getMerchantsActual(id)!", e);
		}
		request.setAttribute("codeList", codeList);
		return mapping.findForward("add");
	}

		//添加招商计划
	public ActionForward addmerchantsActual(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		MerchantsActualForm merchantsActualForm = (MerchantsActualForm)form;
		
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer userid = (Integer) map1.get("userid");
		merchantsActualForm.setCreateUser(userid);
		try{
			imerchantsActualService.addClient(merchantsActualForm);
		}catch(Exception e){
			logger.error("添加招商计划内容失败!MerchantsActualAction.addmerchantsActual(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("添加招商计划内容失败!MerchantsActualAction.addmerchantsActual(form2)!",e);
		}
		return null;
	}
	
	
	// 编辑招商计划
			public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

				MerchantsActualForm form2 = (MerchantsActualForm) form;
				try {
					imerchantsActualService.editMerchantsActual(form2);

				} catch (Exception e) {
					logger.error("编辑招商计划失败!imerchantsActualService.editMerchantsActual(form2).详细信息：" + e.getMessage());
					throw new BkmisWebException("编辑招商计划失败!imerchantsActualService.editMerchantsActual(form2)!", e);
				}
				return null;
			}
	
	// 根据id删除招商计划
			public ActionForward delById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

				String ids = request.getParameter("ids");
				try {
					String[] strings = ids.split(",");
					for (int i = 0; i < strings.length; i++) {
						imerchantsActualService.delMerchantsActual(strings[i]);
					}
					response.sendRedirect("merchantsActual.do?method=getList");
				} catch (Exception e) {
					logger.error("删除招商计划内容失败!MerchantsActualAction.delById(form2).详细信息：" + e.getMessage());
					throw new BkmisWebException("删除招商计划内容失败!MerchantsActualAction.delById(form2)!", e);
				}
				return null;
			}
}
