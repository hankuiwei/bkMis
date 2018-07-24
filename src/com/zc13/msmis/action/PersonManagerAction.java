package com.zc13.msmis.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zc13.bkmis.action.BasicAction;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.service.IPersonManagerService;
import com.zc13.util.GlobalMethod;

public class PersonManagerAction extends BasicAction{
	Logger logger = Logger.getLogger(this.getClass());	
	
	/**
	 * 查询个人信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findPerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		int userId = Integer.parseInt(((Map)request.getSession().getAttribute("userInfo")).get("userid").toString());
		MUser person = new MUser();
		try {
			IPersonManagerService personManagerService = (IPersonManagerService)getBean("PersonManagerService");
			person = personManagerService.getUser(userId);
		} catch (Exception e) {
			logger.error("加载个人信息失败，PersionManagerAction.findPerson()。详细信息："+e.getMessage());
			throw new BkmisWebException("加载个人信息失败，PersionManagerAction.findPerson()!",e);
		}
		
		request.setAttribute("person", person);
		return mapping.findForward("person");
	}
	
	/** houxj
	 *  获取用户输入密码的md5形式 */
	public ActionForward getPasswordOfMd5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		PrintWriter out;
		String temp = GlobalMethod.NullToSpace(request.getParameter("temp"));
		String[] str = temp.split(";");
		String userName = str[0];
		String password = str[1];
		String md5Password = null;
		if(!"".equals(userName) && !"".equals(password)){
			md5Password = GlobalMethod.encryptPassword(userName, password);
			out = response.getWriter();
			out.print(md5Password);
		}
		return null;
	}
	/**
	 * 更新用户个人信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward updatePerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		IPersonManagerService personManagerService = (IPersonManagerService)getBean("PersonManagerService");
		MUser person = personManagerService.getUser(Integer.parseInt(request.getParameter("userid")));
		person.setUserid(Integer.parseInt(request.getParameter("userid")));
		String password = request.getParameter("password");
		person.setRealName(request.getParameter("realName"));
		person.setAddress(request.getParameter("address")==null?"":request.getParameter("address"));
		person.setIdentityCard(request.getParameter("identityCard")==null?"":request.getParameter("identityCard"));
		person.setPhone(request.getParameter("phone")==null?"":request.getParameter("phone"));
		try {
			personManagerService.updateUser(person,password);
		} catch (Exception e) {
			logger.error("更新个人信息失败，PersionManagerAction.updatePerson()。详细信息："+e.getMessage());
			throw new BkmisWebException("更新个人信息失败，PersionManagerAction.updatePerson()!",e);
		}
		
		request.setAttribute("flag",true);
		request.setAttribute("person", person);
		return mapping.findForward("person");
	}
	
	

}
