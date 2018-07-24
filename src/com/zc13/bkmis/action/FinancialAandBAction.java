package com.zc13.bkmis.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.FinForm;
import com.zc13.bkmis.service.IFinancialAandBService;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/*******************************************************************************
 * @author 李影
 */
public class FinancialAandBAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private IFinancialAandBService ifinancialAandBService = null;
	
	

	public IFinancialAandBService getIfinancialAandBService() {
		return ifinancialAandBService;
	}

	public void setIfinancialAandBService(
			IFinancialAandBService ifinancialAandBService) {
		this.ifinancialAandBService = ifinancialAandBService;
	}

	/** 从spring容器里得到iCustomerRepairService */
	public void setServlet(ActionServlet actionservlet) {
		super.setServlet(actionservlet);
		ifinancialAandBService = (IFinancialAandBService) getBean("ifinancialAandBService");
	}

	@SuppressWarnings("rawtypes")
	public ActionForward getList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		FinForm finForm = (FinForm)form;
		List financialAandBList= ifinancialAandBService.getAandBList(finForm);
		//取总条数
		int totalcount = ifinancialAandBService.queryCounttotal(finForm);
		//添加分页信息
		String htmlPagination = "&nbsp;";
		if (null == financialAandBList || financialAandBList.size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/financialAandB.do?method=getList", 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/financialAandB.do?method=getList" , Integer.parseInt(GlobalMethod.NullToParam(GlobalMethod.ObjToStr(finForm.getPagesize()), "10")), 
					Integer.parseInt(GlobalMethod.NullToParam(finForm.getCurrentpage(), "1")), totalcount);
		}
		request.setAttribute("pagination", htmlPagination);
		
		request.setAttribute("financialAandBList", financialAandBList);
		request.setAttribute("finForm", finForm);
		return mapping.findForward("list");
	}
}