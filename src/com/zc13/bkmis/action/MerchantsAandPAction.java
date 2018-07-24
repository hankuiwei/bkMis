package com.zc13.bkmis.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.MerForm;
import com.zc13.bkmis.service.IMerchantsAandPService;
import com.zc13.bkmis.service.IMerchantsPlanService;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/*******************************************************************************
 * @author 李影
 * 招商计划与实际对比
 */
public class MerchantsAandPAction extends BasicAction {

	Logger logger = Logger.getLogger(this.getClass());
	private IMerchantsAandPService imerchantsAandPService= null;
	private IMerchantsPlanService iMerchantsPlanService;
	
	
	public IMerchantsPlanService getiMerchantsPlanService() {
		return iMerchantsPlanService;
	}

	public void setiMerchantsPlanService(IMerchantsPlanService iMerchantsPlanService) {
		this.iMerchantsPlanService = iMerchantsPlanService;
	}

	public IMerchantsAandPService getImerchantsAandPService() {
		return imerchantsAandPService;
	}

	public void setImerchantsAandPService(
			IMerchantsAandPService imerchantsAandPService) {
		this.imerchantsAandPService = imerchantsAandPService;
	}

	/** 从spring容器里得到iCustomerRepairService */
	public void setServlet(ActionServlet actionservlet) {
		super.setServlet(actionservlet);
		imerchantsAandPService = (IMerchantsAandPService) getBean("imerchantsAandPService");
		iMerchantsPlanService = (IMerchantsPlanService)getBean("imerchantsPlanService");
	}

	@SuppressWarnings("rawtypes")
	public ActionForward getList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		MerForm merForm = (MerForm)form;
		List merchantsAandPList= imerchantsAandPService.getAandBList(merForm);
		List<SysCode> codeList = new ArrayList<SysCode>();
		codeList = iMerchantsPlanService.queryCodeByType();
		//取总条数
		int totalcount = imerchantsAandPService.queryCounttotal(merForm);
		
		//添加分页信息
		String htmlPagination = "&nbsp;";
		if (null == merchantsAandPList || merchantsAandPList.size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/merchantsAandP.do?method=getList", 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/merchantsAandP.do?method=getList" , Integer.parseInt(GlobalMethod.NullToParam(GlobalMethod.ObjToStr(merForm.getPagesize()), "10")), 
					Integer.parseInt(GlobalMethod.NullToParam(merForm.getCurrentpage(), "1")), totalcount);
		}
		request.setAttribute("codeList", codeList);
		request.setAttribute("pagination", htmlPagination);
		
		request.setAttribute("merchantsAandPList", merchantsAandPList);
		request.setAttribute("merForm", merForm);
		return mapping.findForward("list");
	}
}