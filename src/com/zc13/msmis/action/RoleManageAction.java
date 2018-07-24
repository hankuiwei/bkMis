package com.zc13.msmis.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.action.BasicAction;
import com.zc13.bkmis.mapping.AwokeTask;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.form.RoleForm;
import com.zc13.msmis.mapping.MFunction;
import com.zc13.msmis.mapping.MRole;
import com.zc13.msmis.mapping.MRolePerm;
import com.zc13.msmis.mapping.MRolreUserPrem;
import com.zc13.msmis.service.IFunctionListService;
import com.zc13.msmis.service.IRoleManageService;
import com.zc13.util.GlobalMethod;
/**
 * 
 * @author 侯哓娟
 * Date：Nov 8, 2010
 * Time：10:57:12 AM
 */
public class RoleManageAction extends BasicAction {
   
	Logger logger = Logger.getLogger(this.getClass());
	/** 从spring容器中得到注入的Service */
	private IRoleManageService iroleManageService = null;
	private IFunctionListService ifunctionListService = null;
	public void setServlet(ActionServlet actionServlet){
		
		super.setServlet(actionServlet);
		iroleManageService = (IRoleManageService)getBean("iroleManageService");
		ifunctionListService = (IFunctionListService)getBean("functionListService");
	}
	
	/** 查询角色列表 */
	public ActionForward getRoleList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		/** 下面夹着的代码是为了实现多楼盘的 */
		Map map1 = (Map) request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
		/** 到此为止 */
		
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userRoleRange = GlobalMethod.NullToParam((map.get("userRoleRange").toString()),"0");
		int range = Integer.parseInt(userRoleRange);
		List<MRole> roleList = iroleManageService.getRole(range,lpId);
		if (roleList.size() > 0 && roleList != null) {
			request.setAttribute("roleList", roleList);
			request.setAttribute("lpId", lpId);
		}
		return mapping.findForward("success");
	}
	
	/** 跳至添加角色页面 */
	public ActionForward goAddRole(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		return mapping.findForward("add");
	}
	
	/** 添加角色 */
	public ActionForward addRole(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		RoleForm roleForm = (RoleForm) form;
		/** 下面夹着的代码是为了实现多楼盘的 */
		Map map1 = (Map) request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()), "0"));
		/** 到此为止 */
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		String userRoleRange = GlobalMethod.NullToParam((map.get("userRoleRange").toString()),"0");
		int range = Integer.parseInt(userRoleRange);
		roleForm.setUserName(userName);
		roleForm.setLpId(lpId);
		roleForm.setRange(range+1);//默认添加的角色等级比当前用户的角色等级小一级别

		iroleManageService.addRole(roleForm);
		request.setAttribute("flag", true);
		return mapping.findForward("add");
		}
	/** 检查角色名称 */
	public ActionForward checkRoleName(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		RoleForm roleForm = (RoleForm) form;
		MRole mrole = new MRole();
		try {
			BeanUtils.copyProperties(mrole, roleForm);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		/** 检查数据库中是否已存在用户想要添加的角色名称 */
		String forward = request.getParameter("forward");
		boolean arg = iroleManageService.checkRoleName(mrole.getRolename());
		PrintWriter out = null;
		try{
			out = response.getWriter();
		}catch(IOException e){
			e.printStackTrace();
		}
		if(arg){
			out.print(true);
		}else{
			if("addRole".equals(forward)){
				out.print(false);
			}
		}
		return null;
	}
	/** 更新角色信息 */
	public ActionForward updateRole(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		this.setLogParam(request);
		RoleForm roleForm = (RoleForm) form;
		roleForm.setLpId(lpId);
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		roleForm.setUserName(userName);
		iroleManageService.updateRole(roleForm);
		request.setAttribute("flag",true);
		return mapping.findForward("modify");
	}
	/** 从角色列表页面获得修改角色所需的信息 */
	public ActionForward getModifyInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		RoleForm roleForm = (RoleForm) form;
		MRole mrole = iroleManageService.setModifyInfo(roleForm);
		request.setAttribute("mrole",mrole);
		return mapping.findForward("modify");
	}
	/** 删除角色 */
	public ActionForward deleteRole(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleForm roleForm = (RoleForm) form;
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		roleForm.setUserName(userName);
		String roleName = iroleManageService.deleteRole(roleForm);
		try {
			response.sendRedirect("role.do?method=getRoleList");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/** 获得设置角色权限所需的操作 */
	public ActionForward getRolePerm(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		RoleForm roleForm = (RoleForm) form;
		MRole mrole = new MRole();
		try {
			BeanUtils.copyProperties(mrole, roleForm);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		Integer roleid = mrole.getRoleid();
		List<MRolePerm> rolePremlist = iroleManageService.getRolePerm(roleid);
		for(MRolePerm m:rolePremlist){
			System.out.println(m.getMFunction());
		}
		List<MFunction> functionlist = null;
		try {
			//查询功能列表,调用s的方法
			functionlist = ifunctionListService.getFunctionList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("roleid", roleid);
		request.setAttribute("rolePremlist", rolePremlist);
		request.setAttribute("functionlist", functionlist);
		return mapping.findForward("permissions");
	}
	
	/** 更新角色权限 */
	public ActionForward updateRolePerm(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		Integer roleid = Integer.parseInt(request.getParameter("roleid"));
		String functionids = request.getParameter("resIds");
		List permList = new ArrayList();
		if(!"".equals(functionids)){
			String[] ids = new String[]{functionids};
			if(functionids.indexOf(",")>0){
				ids = functionids.split(",");
			}
			for(int i=0;i<ids.length;i++){
				Integer functionid = Integer.parseInt(ids[i]);
				permList.add(functionid);
			}
			this.iroleManageService.updateRolePerm(permList, roleid);
		}
		
		request.setAttribute("permList", permList);
		request.setAttribute("roleid", roleid);
		try {
			response.sendRedirect("role.do?method=getRoleList");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** 得到指定角色所对应权限列表  */
	public ActionForward getRolePermList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		List<MRolreUserPrem> role_user_permList = iroleManageService.getRole_User_Perm();
		request.setAttribute("role_user_permList", role_user_permList);
		return mapping.findForward("roleuserperm");
	}
	
	/** 跳至设置某角色对应的任务提示信息 */
	public ActionForward goSetTips(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<AwokeTask> tipList = null;
		Integer roleid = null;
		try{
			String sroleid = GlobalMethod.NullToSpace(request.getParameter("roleid"));
			if(!"".equals(sroleid)){
				tipList = iroleManageService.getTips();
				/* 保存角色id，用于设置 */
				request.setAttribute("roleId", sroleid);
				roleid = Integer.parseInt(sroleid);
				/* 某角色对应的任务提示信息 */
				List rtipList = iroleManageService.getTipsByRoleId(roleid);
				request.setAttribute("rtipList", rtipList);
				request.setAttribute("tipList", tipList);
			}
		}catch(Exception e){
			logger.error("获取任务提示信息失败!iroleManageService.getTips()。详细信息：" + e.getMessage());
			throw new BkmisWebException("获取任务提示信息失败!RoleManageAction.goSetTips()!",e);
		}
		return mapping.findForward("tip");
	}
	
	/** 设置某角色对应的任务提示信息 */
	public ActionForward setTips(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		String sroleid = GlobalMethod.NullToSpace(request.getParameter("roleId"));
		Integer roleid = null;
		List rtipList = null;
		String[] stipId = request.getParameterValues("tipId");
		List<String> idList = Arrays.asList(stipId);
		try{
			if(!"".equals(sroleid)){
				roleid = Integer.parseInt(sroleid);
			}
			if(!idList.isEmpty()){
				iroleManageService.addRoleTips(idList,roleid,rtipList);
			}
		}catch(Exception e){
			logger.error("设置某角色对应的任务提示信息失败!RoleManageAction.setTips()。详细信息：" + e.getMessage());
			throw new BkmisWebException("设置某角色对应的任务提示信息失败!RoleManageAction.setTips()!",e);
		}
		return null;
				
	}
}
