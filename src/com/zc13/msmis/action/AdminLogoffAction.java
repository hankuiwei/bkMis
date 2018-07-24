package com.zc13.msmis.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zc13.bkmis.action.BasicAction;

/**
 * 
 * @author daokui
 * Date：Nov 31, 2010
 * Time：2:26:25 PM
 */
public class AdminLogoffAction extends BasicAction {
	@SuppressWarnings("unchecked")
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		session.removeAttribute("userInfo");
		session.invalidate();
		response.sendRedirect("login.jsp");
		return null;
	}

}
