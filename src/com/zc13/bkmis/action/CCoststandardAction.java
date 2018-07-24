package com.zc13.bkmis.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.CCoststandardForm;
import com.zc13.bkmis.service.ICCoststandardService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PageBean;

public class CCoststandardAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());

	private ICCoststandardService icCoststandardService;

	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		icCoststandardService = (ICCoststandardService) getBean("icCoststandardService");
	}
	/**
	 * 收费标准查询
	 * CCoststandardAction.getList
	 */
	public ActionForward getList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CCoststandardForm standardForm = (CCoststandardForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		standardForm.setLpId(lpId);
		/** 到此为止*/
		try {
			PageBean page = icCoststandardService.getCCoststandardList(standardForm);
			request.setAttribute("bzList", page.getList());
			request.setAttribute("pageHTML", page.toString("cx()"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("收费标准查询失败!CCoststandardAction.getList()。详细信息："
					+ e.getMessage());
			throw new BkmisServiceException(
					"收费标准查询失败!CCoststandardAction.getList()。");
		}
		return mapping.findForward("list");
	}
	/**-
	 * 收费标准保存
	 * CCoststandardAction.save
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/** 下面夹着的代码是为了实现多楼盘的*/
		CCoststandardForm standardForm = (CCoststandardForm)form;
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		standardForm.getStandard().setLpId(lpId);
		/** 到此为止*/
		try {
			icCoststandardService.saveCCoststandard(standardForm);
		} catch (Exception e) {
			logger.error("收费标准保存失败!CCoststandardAction.save()。详细信息："
					+ e.getMessage());
			throw new BkmisServiceException(
					"收费标准保存失败!CCoststandardAction.save()。");
		}
		return this.getList(mapping, form, request, response);
	}
	/**
	 * 收费标准删除
	 * CCoststandardAction.delete
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			icCoststandardService.deleteCCoststandard((CCoststandardForm) form);
		} catch (Exception e) {
			logger.error("收费标准删除失败!CCoststandardAction.delete()。详细信息："
					+ e.getMessage());
			throw new BkmisServiceException(
					"收费标准删除失败!CCoststandardAction.delete()。");
		}
		return this.getList(mapping, form, request, response);
	}
}
