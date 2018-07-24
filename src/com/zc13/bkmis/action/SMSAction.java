package com.zc13.bkmis.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.form.SMSForm;
import com.zc13.bkmis.service.ISMSService;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

import common.Logger;
/**
 * 短信服务
 * @author wangzw
 * @Date Jul 6, 2011
 * @Time 10:32:29 AM
 */
public class SMSAction  extends BasicAction{
	Logger logger = Logger.getLogger(this.getClass());
	private ISMSService SMSService;
	public void setServlet(ActionServlet servlet) {
		super.setServlet(servlet);
		SMSService = (ISMSService) super.getBean("SMSService");
	}
	
	/**
	 * 查询短信信息
	 */
	public ActionForward showSMSList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		SMSForm form1 = (SMSForm)form;
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
		//根据考勤机的信息更新数据库
		form1.setLpId(lpId);
		
		SMSService.getSMSList(form1,true);
		//添加分页信息
		String htmlPagination = "&nbsp;";
		if (null == form1.getList() || form1.getList().size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/sms.do?method=showSMSList", 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/sms.do?method=showSMSList", Integer.parseInt(GlobalMethod.NullToParam(form1.getPagesize(),"10")),
					Integer.parseInt(GlobalMethod.NullToParam(form1.getCurrentpage(),"1")), form1.getTotalcount());
		}
		request.setAttribute("pagination", htmlPagination);
		return mapping.findForward("showSMSList");
	} 
	
	/**
	 * 群发短信信息
	 */
	public ActionForward sendSMS(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		SMSForm form1 = (SMSForm)form;
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map.get("userlp").toString()),"0"));
		//根据考勤机的信息更新数据库
		form1.setLpId(lpId);
		
		SMSService.sendMessage(form1);
		response.sendRedirect("zc13/bkmis/SMS/SMSend.jsp");
		return null;
	} 
}
