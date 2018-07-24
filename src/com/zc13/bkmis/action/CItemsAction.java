package com.zc13.bkmis.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.CItemsForm;
import com.zc13.bkmis.service.ICItemsService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PageBean;
/**
 * @author zhaosg
 * Date：Nov 26, 2010
 * Time：11:09:55 AM
 */
public class CItemsAction extends BasicAction {
	Logger logger = Logger.getLogger(this.getClass());
	
	private ICItemsService icItemsService = null;
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		icItemsService = (ICItemsService)getBean("icItemsService");
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	public ActionForward getItemsList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PageBean page = null;
		CItemsForm items = (CItemsForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		items.setLpId(lpId);
		/** 到此为止*/
		try {
			page = icItemsService.getCItems(items);
		} catch (RuntimeException e) {
			logger.error("收费项信息查询失败!CItemsAction.getItemsList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("收费项信息查询失败!CItemsAction.getItemsList()。");
		}
		request.setAttribute("itemslist", page.getList());
		request.setAttribute("pageHTML", page.toString("cx()"));
		request.setAttribute("itemName", items.getItemName());
		return mapping.findForward("itemsList");
	}
	//保存或更新
	public ActionForward saveItems(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		CItemsForm items = (CItemsForm)form;
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		items.setLpId(lpId);
		/** 到此为止*/
		try {
			icItemsService.saveItems(items);
		} catch (RuntimeException e) {
			logger.error("收费项信息保存失败!CItemsAction.saveItems()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("收费项信息保存失败!CItemsAction.saveItems()。");
		}
		return this.getItemsList(mapping, form, request, response);
	}
	//删除
	public ActionForward deleteItems(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		CItemsForm items = (CItemsForm)form;
		try {
			icItemsService.deleteItems(items);
		} catch (RuntimeException e) {
			logger.error("收费项信息删除失败!CItemsAction.saveItems()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("收费项信息删除失败!CItemsAction.saveItems()。");
		}
		return this.getItemsList(mapping, form, request, response);
	}
}
