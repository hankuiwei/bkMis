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

import com.zc13.bkmis.form.CAccounttemplateForm;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.service.ICAccounttemplateService;
import com.zc13.exception.BkmisWebException;
import com.zc13.util.GlobalMethod;

public class CAccounttemplateAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());

	private ICAccounttemplateService icAccounttemplateService = null;

	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		icAccounttemplateService = (ICAccounttemplateService) getBean("icAccounttemplateService");
	}
	/**
	 * 帐套查询
	 * CAccounttemplateAction.getList
	 */
	public ActionForward getList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List list = null;
		CAccounttemplateForm account = (CAccounttemplateForm) form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		account.setLpId(lpId);
		/** 到此为止*/
		try {
			list = icAccounttemplateService.getCAccounttemplateList(account);
		} catch (Exception e) {
			logger.error("收费帐套查询失败!CAccounttemplateAction.getList()。详细信息："
					+ e.getMessage());
			throw new BkmisWebException(
					"收费帐套查询失败!CAccounttemplateAction.getList()。", e);
		}
		List<ELp> lpList = icAccounttemplateService.getLpList(account);
		request.setAttribute("list", list);
		request.setAttribute("lpList", lpList);
		return mapping.findForward("list");
	}
	/**
	 * 帐套保存
	 * CAccounttemplateAction.save
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CAccounttemplateForm items = (CAccounttemplateForm) form;
		try {
			icAccounttemplateService.saveCAccounttemplate(items);
		} catch (Exception e) {
			logger.error("收费帐套保存失败!CAccounttemplateAction.save()。详细信息："
					+ e.getMessage());
			throw new BkmisWebException(
					"收费帐套保存失败!CAccounttemplateAction.save()。", e);
		}
		return this.getList(mapping, form, request, response);
	}
	/**
	 * 帐套删除
	 * CAccounttemplateAction.delete
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CAccounttemplateForm items = (CAccounttemplateForm) form;
		try {
			icAccounttemplateService.deleteCAccounttemplate(items);
		} catch (Exception e) {
			logger.error("收费帐套删除失败!CAccounttemplateAction.delete()。详细信息："
					+ e.getMessage());
			throw new BkmisWebException(
					"收费帐套删除失败!CAccounttemplateAction.delete()。", e);
		}
		return this.getList(mapping, form, request, response);
	}
}
