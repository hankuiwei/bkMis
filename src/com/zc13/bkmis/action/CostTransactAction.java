package com.zc13.bkmis.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.CostTransactForm;
import com.zc13.bkmis.mapping.ClientBill;
import com.zc13.bkmis.service.ICostTransactService;
/**
 * 费用处理action
 * @author 王正伟
 * Date：Jan 6, 2011
 * Time：10:43:34 AM
 */
public class CostTransactAction  extends BasicAction{
	Logger logger = Logger.getLogger(this.getClass());
	private ICostTransactService costTransactService= null;
	
	public ICostTransactService getCostTransactService() {
		return costTransactService;
	}

	public void setCostTransactService(ICostTransactService costTransactService) {
		this.costTransactService = costTransactService;
	}

	@Override
	public void setServlet(ActionServlet servlet) {
		super.setServlet(servlet);
		costTransactService = (ICostTransactService)getBean("costTransactService");
	}
	
	/**
	 * 获得需要缴费客户列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPressMoneyClient(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		CostTransactForm costTransactForm = (CostTransactForm)form;
		List<ClientBill> list = null;
		try {
			list = costTransactService.getPressMoneyClient(costTransactForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("list", list);
		return mapping.findForward("toShowPressMoneyClient");
	}
	
	/**
	 * 获取需要缴纳预收款的客户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getPressAdvanceClient(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		CostTransactForm costTransactForm = (CostTransactForm)form;
		List list = null;
		try {
			list = costTransactService.getPressAdvanceClient(costTransactForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("list", list);
		request.setAttribute("form", costTransactForm);
		return mapping.findForward("toShowPressAdvanceClient");
	}
	
	/**
	 * 获取需要缴纳押金的客户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getPressDepositClient(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		CostTransactForm costTransactForm = (CostTransactForm)form;
		List list = null;
		try {
			list = costTransactService.getPressDepositClient(costTransactForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("list", list);
		return mapping.findForward("toShowPressDepositClient");
	}
	/**
	 * 获取需要缴纳订金的客户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getPressEarnestClient(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		CostTransactForm costTransactForm = (CostTransactForm)form;
		List list = null;
		try {
			list = costTransactService.getPressEarnestClient(costTransactForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("list", list);
		return mapping.findForward("toShowPressEarnestClient");
	}
	
}
