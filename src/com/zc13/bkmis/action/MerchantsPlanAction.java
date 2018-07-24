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

import com.zc13.bkmis.form.MerchantsPlanForm;
import com.zc13.bkmis.mapping.MerchantsPlan;
import com.zc13.bkmis.service.IMerchantsPlanService;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/*******************************************************************************
 * @author 李影
 */
public class MerchantsPlanAction extends BasicAction {
	Logger logger = Logger.getLogger(this.getClass());
	private IMerchantsPlanService imerchantsPlanService = null;

	/** 从spring容器里得到imerchantsPlanService */
	public void setServlet(ActionServlet actionservlet) {
		super.setServlet(actionservlet);
		imerchantsPlanService = (IMerchantsPlanService) getBean("imerchantsPlanService");
	}
	
public ActionForward getList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	
	MerchantsPlanForm merchantsPlanForm = (MerchantsPlanForm) form;
	/*
	String year = GlobalMethod.NullToSpace(request.getParameter("year"));
	String month = GlobalMethod.NullToSpace(request.getParameter("month"));
	merchantsPlanForm.setYear(year);
	merchantsPlanForm.setMonth(month);
	String currentpage = request.getParameter("currentpage");
	String pagesize = request.getParameter("pagesize");*/
	List<MerchantsPlan> list = null;
	//List<MUser> userList = null;
	List<SysCode> codeList = new ArrayList<SysCode>();
	try {
		list = imerchantsPlanService.getList(merchantsPlanForm, true);
		//userList = imerchantsPlanService.getUserList();
		codeList = imerchantsPlanService.queryCodeByType();
		// 取总条数
		int totalcount = imerchantsPlanService.queryCounttotal(merchantsPlanForm);
		// 添加分页信息
		String htmlPagination = "&nbsp;";
		if (null == list || list.size() <= 0) {// 如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath() + "/merchantsPlan.do?method=getList", 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath() + "/merchantsPlan.do?method=getList", Integer.parseInt(GlobalMethod.NullToParam(merchantsPlanForm.getPagesize(), "10")), Integer.parseInt(GlobalMethod.NullToParam(merchantsPlanForm.getCurrentpage(), "1")), totalcount);
		}
		request.setAttribute("pagination", htmlPagination);
		//request.setAttribute("month", month);
		//request.setAttribute("year", year);
		request.setAttribute("form", merchantsPlanForm);
		request.setAttribute("codeList", codeList);
		request.setAttribute("list", list);
	} catch (Exception e) {
		logger.error("高级查询失败!MerchantsPlanAction.getList().详细信息：" + e.getMessage());
		throw new BkmisWebException("高级查询失败，MerchantsPlanAction.getList()!", e);
	}

	return mapping.findForward("list");
}


		//根据id得到招商计划
		public ActionForward getById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			String id = request.getParameter("id");
			MerchantsPlan bean = null;
			List<SysCode> codeList = new ArrayList<SysCode>();
			try {
				bean = imerchantsPlanService.getMerchantsPlan(id);
				codeList = imerchantsPlanService.queryCodeByType();
			} catch (Exception e) {
				logger.error("根据id得到招商计划失败!imerchantsPlanService.getMerchantsPlan(id).详细信息：" + e.getMessage());
				throw new BkmisWebException("根据id得到招商计划失败!imerchantsPlanService.getMerchantsPlan(id)!", e);
			}
			request.setAttribute("bean", bean);
			request.setAttribute("codeList", codeList);
			return mapping.findForward("edit");
		}
		
		/***
		 * 添加页面
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * Date:2013-5-8 
		 * Time:下午10:47:00
		 */
		public ActionForward addPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			List<SysCode> codeList = new ArrayList<SysCode>();
			try {
				codeList = imerchantsPlanService.queryCodeByType();
			} catch (Exception e) {
				logger.error("根据id得到招商计划失败!imerchantsPlanService.getMerchantsPlan(id).详细信息：" + e.getMessage());
				throw new BkmisWebException("根据id得到招商计划失败!imerchantsPlanService.getMerchantsPlan(id)!", e);
			}
			request.setAttribute("codeList", codeList);
			return mapping.findForward("add");
		}

//添加招商计划
	public ActionForward addmerchantsPlan(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		MerchantsPlanForm merchantsPlanForm = (MerchantsPlanForm)form;
		
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer userid = (Integer) map1.get("userid");
		merchantsPlanForm.setCreateUser(userid);
		try{
			imerchantsPlanService.addClient(merchantsPlanForm);
		}catch(Exception e){
			logger.error("添加招商计划内容失败!MerchantsPlanAction.addmerchantsPlan(form2).详细信息：" + e.getMessage());
			throw new BkmisWebException("添加招商计划内容失败!MerchantsPlanAction.addmerchantsPlan(form2)!",e);
		}
		return null;
	}
	
	
	// 编辑招商计划
			public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

				MerchantsPlanForm form2 = (MerchantsPlanForm) form;
				try {
					imerchantsPlanService.editMerchantsPlan(form2);

				} catch (Exception e) {
					logger.error("编辑招商计划失败!imerchantsPlanService.editMerchantsPlan(form2).详细信息：" + e.getMessage());
					throw new BkmisWebException("编辑招商计划失败!imerchantsPlanService.editMerchantsPlan(form2)!", e);
				}
				return null;
			}
	
	// 根据id删除招商计划
			public ActionForward delById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

				String ids = request.getParameter("ids");
				try {
					String[] strings = ids.split(",");
					for (int i = 0; i < strings.length; i++) {
						imerchantsPlanService.delMerchantsPlan(strings[i]);
					}
					response.sendRedirect("merchantsPlan.do?method=getList");
				} catch (Exception e) {
					logger.error("删除招商计划内容失败!MerchantsPlanAction.delById(form2).详细信息：" + e.getMessage());
					throw new BkmisWebException("删除招商计划内容失败!MerchantsPlanAction.delById(form2)!", e);
				}
				return null;
			}
}
