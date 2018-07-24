package com.zc13.demo.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zc13.bkmis.action.BasicAction;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.form.AdminLoginForm;
import com.zc13.msmis.mapping.MFunction;
import com.zc13.msmis.service.IAdminLoginService;
import com.zc13.util.DTree;
/**
 * 
 * @author daokui
 * Date：Nov 24, 2010
 * Time：8:28:39 AM
 */
public class TreeAction extends BasicAction{
	
	Logger logger = Logger.getLogger(this.getClass());	
	/**
	 * 登录信息验证
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward treeTest(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response){

		String forward = "fail";
		AdminLoginForm adminLoginForm = (AdminLoginForm)actionForm;
		adminLoginForm.setForward(forward);
		try {
			IAdminLoginService adminLoginService = (IAdminLoginService)getBean("adminLoginService");
			adminLoginService.login(adminLoginForm);
		} catch (Exception e) {
			logger.error("加载登录信息失败，AdminLoginAction.login()。详细信息："+e.getMessage());
			throw new BkmisWebException("加载登录信息失败，AdminLoginAction.login()!",e);
		}
		
		List<MFunction> fundesc = new ArrayList<MFunction>();
		List<DTree> treeList = new ArrayList<DTree>();
		fundesc = adminLoginForm.getFunctionList();
		for(MFunction m: fundesc){
			DTree tree =  new DTree();
			tree.setId(m.getFunctionid().toString());
			tree.setName(m.getFunctionname());
			tree.setParentID(m.getParentid().toString());
			tree.setUrl(m.getUrlname());
			treeList.add(tree);
		}
		System.out.println(treeList);
		request.getSession().setAttribute("dd", "ddddd");
		request.getSession().setAttribute("treeList", treeList);
		request.getSession().setAttribute("mainFramJsp", "error.jsp");
		
		return actionMapping.findForward(adminLoginForm.getForward());
	}
	
}
