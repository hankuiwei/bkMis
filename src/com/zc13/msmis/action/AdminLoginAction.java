package com.zc13.msmis.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zc13.bkmis.action.BasicAction;
import com.zc13.bkmis.service.IAnalysisEnergyService;
import com.zc13.bkmis.service.IAwokeTaskService;
import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.form.AdminLoginForm;
import com.zc13.msmis.service.IAdminLoginService;
/**
 * 用户登录相关
 * @author daokui
 * Date：Nov 3, 2010
 * Time：2:47:23 PM
 */
public class AdminLoginAction extends BasicAction {
	
	Logger logger = Logger.getLogger(this.getClass());	
	/**
	 * 用户登录及权限设置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public ActionForward login(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

		String forward = "fail";
		AdminLoginForm adminLoginForm = (AdminLoginForm)form;
		adminLoginForm.setForward(forward);
		try {
			IAdminLoginService adminLoginService = (IAdminLoginService)getBean("adminLoginService");
			adminLoginService.login(adminLoginForm);
			//统计能源
			IAnalysisEnergyService ienergyService = (IAnalysisEnergyService)getBean("ienergyService");
			ienergyService.selectEngAnalysis(adminLoginForm);
			//更新代办任务数量
			IAwokeTaskService awokeTaskService = (IAwokeTaskService)getBean("awokeTaskService");
			ICostTransactService costTransactService = (ICostTransactService)getBean("costTransactService");
			awokeTaskService.updateAwokeTaskAmount(null);
			costTransactService.updateAwokeTask4PressAdvance();//更新预收房租余额不足的客户的数量
			costTransactService.updateAwokeTask4PressRentDeposit();//更新需要缴纳房租押金的客户的数量
			costTransactService.updateAwokeTask4PressDecorationDeposit();//更新需要缴纳装修押金的客户的数量
			//以下设置cookie
			Cookie cookie=new Cookie("bkUserName",adminLoginForm.getUsername());
			cookie.setMaxAge(60*6000);
			response.addCookie(cookie);
	        
		} catch (Exception e) {
			logger.error("加载登录信息失败，AdminLoginAction.login()。详细信息："+e.getMessage());
			throw new BkmisWebException("加载登录信息失败，AdminLoginAction.login()!",e);
		}
		
		request.setAttribute("functionList", adminLoginForm.getFunctionList());
		request.getSession().setAttribute("userInfo", adminLoginForm.getUserinfoMap());
		
		return mapping.findForward(adminLoginForm.getForward());
	}

	}
