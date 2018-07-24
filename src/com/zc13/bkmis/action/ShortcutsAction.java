package com.zc13.bkmis.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.ShortcutsForm;
import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.bkmis.service.IShortcutsService;

/**
 * 快捷操作的action
 * @author wangzw
 * @Date May 3, 2011
 * @Time 4:30:06 PM
 */
public class ShortcutsAction  extends BasicAction{
	private IShortcutsService shortcutsService;
	private ICostTransactService costTransactService;
	
	public ICostTransactService getCostTransactService() {
		return costTransactService;
	}
	public void setCostTransactService(ICostTransactService costTransactService) {
		this.costTransactService = costTransactService;
	}
	public IShortcutsService getShortcutsService() {
		return shortcutsService;
	}
	public void setShortcutsService(IShortcutsService shortcutsService) {
		this.shortcutsService = shortcutsService;
	}
	@Override
	public void setServlet(ActionServlet servlet) {
		super.setServlet(servlet);
		shortcutsService = (IShortcutsService)getBean("shortcutsService");
		costTransactService = (ICostTransactService)getBean("costTransactService");
	}
	
	/**
	 * 测试生成账单
	 * CostTransactAction.test
	 */
	public ActionForward buildBills(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		ShortcutsForm shortcutsForm = (ShortcutsForm)form;
		costTransactService.autoBuildBills();
		return null;
	}
	
	/**
	 * 清除账单
	 * CostTransactAction.clear
	 */
	public ActionForward clearBill(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		shortcutsService.clearBill();
		return null;
	}
	
	/**
	 * 删除费用基础信息(不包括计费参数类型)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 3, 2011 
	 * Time:5:52:05 PM
	 */
	public ActionForward deleteCostBase(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		ShortcutsForm shortcutsForm = (ShortcutsForm)form;
		shortcutsService.deleteCostBase(shortcutsForm);
		return null;
	}
	
	/**
	 * 清除账单日志，用于可以再次生成账单 
	 * CostTransactAction.clear
	 */
	public ActionForward clearBillLog(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		shortcutsService.clearBillLog();
		return null;
	}
	
	/**
	 * 获得所有客户信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 3, 2011 
	 * Time:6:02:37 PM
	 */
	public ActionForward getAllClient(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		ShortcutsForm shortcutsForm = (ShortcutsForm)form;
		List<CompactClient> list = null;
		try {
			list = shortcutsService.getObjects(CompactClient.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("list", list);
		return mapping.findForward("showAllClient");
	}
	
	/**
	 * 删除客户的押金、定金、暂存款、预收房租等信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 3, 2011 
	 * Time:5:52:05 PM
	 */
	public ActionForward deleteFees(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		ShortcutsForm shortcutsForm = (ShortcutsForm)form;
		shortcutsService.deleteFees(shortcutsForm);
		return null;
	}
	
	/**
	 * 删除客户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 3, 2011 
	 * Time:5:52:05 PM
	 */
	public ActionForward deleteClient(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		ShortcutsForm shortcutsForm = (ShortcutsForm)form;
		shortcutsService.deleteClient(shortcutsForm);
		return null;
	}
	
	/**
	 * 删除统计信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 3, 2011 
	 * Time:5:52:05 PM
	 */
	public ActionForward deleteAnalysis(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		ShortcutsForm shortcutsForm = (ShortcutsForm)form;
		shortcutsService.deleteAnalysis(shortcutsForm);
		return null;
	}
	
	
	/**
	 * 删除库存管理信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 3, 2011 
	 * Time:5:52:05 PM
	 */
	public ActionForward deleteDepot(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		ShortcutsForm shortcutsForm = (ShortcutsForm)form;
		shortcutsService.deleteDepot(shortcutsForm);
		return null;
	}
	
	/**
	 * 删除库存材料和类型信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 3, 2011 
	 * Time:5:52:05 PM
	 */
	public ActionForward deleteDepotBase(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		ShortcutsForm shortcutsForm = (ShortcutsForm)form;
		shortcutsService.deleteDepotBase(shortcutsForm);
		return null;
	}
	
	/**
	 * 删除房产信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 3, 2011 
	 * Time:5:52:05 PM
	 */
	public ActionForward deleteEstate(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		ShortcutsForm shortcutsForm = (ShortcutsForm)form;
		shortcutsService.deleteEstate(shortcutsForm);
		return null;
	}
	
	/**
	 * 删除人事信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 3, 2011 
	 * Time:5:52:05 PM
	 */
	public ActionForward deleteHr(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		ShortcutsForm shortcutsForm = (ShortcutsForm)form;
		shortcutsService.deleteHr(shortcutsForm);
		return null;
	}
	
	/**
	 * 删除所有信息
	 * 不删除的表有：awoke_task、awoke_task_role、c_costparatype、m_function、m_role、m_roleprem、m_user
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 4, 2011 
	 * Time:2:59:14 PM
	 */
	public ActionForward deleteAll(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		ShortcutsForm shortcutsForm = (ShortcutsForm)form;
		return null;
	}
	
	/**
	 * 删除客户服务信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:May 4, 2011 
	 * Time:3:48:45 PM
	 */
	public ActionForward deleteSer(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		ShortcutsForm shortcutsForm = (ShortcutsForm)form;
		shortcutsService.deleteSer(shortcutsForm);
		return null;
	}
}
