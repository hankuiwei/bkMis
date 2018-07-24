package com.zc13.msmis.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

import com.zc13.bkmis.action.BasicAction;
import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.exception.BkmisWebException;
import com.zc13.msmis.form.UserForm;
import com.zc13.msmis.mapping.MRole;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.msmis.service.IUserManagerService;
import com.zc13.util.GlobalMethod;
import com.zc13.util.PaginationHTMLUtil;

/**
 * 用户查询
 * @author qinyantao
 * Date：Nov 8, 2010
 * Time：11:49:46 AM
 */
public class UserManagerAction extends BasicAction{
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private IUserManagerService userManagerServiceImpl = null;
	public void setServlet(ActionServlet actionServlet){
		super.setServlet(actionServlet);
		userManagerServiceImpl = (IUserManagerService)getBean("IUserManagerService");
	}
	/**
	 * 用户列表
	 */
	public ActionForward findUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserForm userform = (UserForm)form;
		setLogParam(request);
		userform.setLpId(lpId);
		userform.setUserName(userName);
		String srange = ((Map)request.getSession().getAttribute("userInfo")).get("userRoleRange").toString();
		int range = Integer.parseInt(GlobalMethod.NullToParam(srange, "0"));
		userform.setRange(range);
		List<MUser> list = (List<MUser>)userManagerServiceImpl.findUser(userform,true);
		
		String htmlPagination = "&nbsp;";
		if (null == list || list.size() <= 0) {//如果没有记录，那么默认如下
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/userManager.do?method=findUser", 10, 1, 0);
		} else {
			htmlPagination = PaginationHTMLUtil.getPaginationHTML(request.getContextPath()
					+ "/userManager.do?method=findUser", Integer.parseInt(GlobalMethod.NullToParam(userform.getPagesize(),"10")),
					Integer.parseInt(GlobalMethod.NullToParam(userform.getCurrentpage(),"1")), userform.getTotalcount());
		}
		request.setAttribute("pagination", htmlPagination);
		return mapping.findForward("user");
	}
	
	/**
	 * 
	 * 转到添加页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward findRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		/* houxj改 */
		String suserid = GlobalMethod.NullToSpace(request.getParameter("isChecked"));
//		int userid = Integer.parseInt(request.getParameter("isChecked"));
		int userid = 0;
		if(!"".equals(suserid)){
			userid = Integer.parseInt(suserid);
		}
		String srange = ((Map)request.getSession().getAttribute("userInfo")).get("userRoleRange").toString();
		Integer range = null;
        if(!"".equals("srange")){
        	range = Integer.parseInt(srange);
        }
//		int range = Integer.parseInt(((Map)request.getSession().getAttribute("userInfo")).get("userRoleRange").toString());

		
		request.setAttribute("userid", userid);
		List<MRole> list = userManagerServiceImpl.setRoleOfUser(userid,range);
		MRole role = (MRole)list.get(list.size()-1);
		request.setAttribute("role", list.get(list.size()-1));
		request.setAttribute("list", list.subList(0, list.size()-1));
		return mapping.findForward("role");
	}
	
	/**
	 * 为用户分配角色
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/* houxj改 */
		String sroleid = GlobalMethod.NullToSpace(request.getParameter("roleid"));
		String suserid = GlobalMethod.NullToSpace(request.getParameter("userid"));
		int roleid = 0;
		int userid = 0;
		if(!"".equals(sroleid)){
			roleid = Integer.parseInt(sroleid);
		}
		if(!"".equals(suserid)){
			userid = Integer.parseInt(suserid);
		}
		
        userManagerServiceImpl.saveRole(userid,roleid);
		return this.findUser(mapping, form, request, response);
	}
	
	/**
	 * 添加用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		UserForm items = (UserForm)form;
		/* houxj
		 * 添加日志 */
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		items.setUserName(userName);
		/** 下面夹着的代码是为了实现多楼盘的*/
		Map map1 = (Map)request.getSession().getAttribute("userInfo");
		Integer lpId = Integer.parseInt(GlobalMethod.NullToParam((map1.get("userlp").toString()),"0"));
		items.setLpId(lpId);
		/** 到此为止*/
		userManagerServiceImpl.addUser(items);
	
		//writeLog(userName, "添加用户", "所添加的用户NAME为" + items.getUsername() + "信息", Contants.USER);
		request.setAttribute("flag", true);
		return mapping.findForward("success");
	}
	
	
	/**
	 * 删除用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		/* houxj改 */
		String userids = GlobalMethod.NullToSpace(request.getParameter("ids"));
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		//writeLog(userName, "删除角色", "所删除的角色ID为" + userid + "信息", Contants.ROLE);
		userManagerServiceImpl.deleteUser(userids,userName);
	    return this.findUser(mapping, form, request, response);
	}
	
	/** 查询操作员名称 */
	public ActionForward checkUserName(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
		/* houxj改,规整代码 */
		PrintWriter out = null;
		 boolean isExist = false;;
		try {
			out = response.getWriter();
		    String userName = request.getParameter("username").trim();
			isExist = userManagerServiceImpl.checkUserName(userName);
			} catch (Exception e) {
				logger.error("检查操作员名称失败，UserManagerAction.checkUserName()。详细信息："+e.getMessage());
				throw new BkmisWebException("检查操作员名称失败，UserManagerAction.checkUserName()!",e);
			}
		out.print(isExist);
		return null;
	}
	
	/** houxj
	 * 跳至添加操作员界面
	 */
	public ActionForward goAddUser(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		try {
		   /* 获得部门类型 */
			List<SysCode> departmentlist = userManagerServiceImpl.getDepartment();
			request.setAttribute("departmentlist", departmentlist);
		}catch(Exception e){
			logger.error("跳至添加操作员界面失败!UserManagerAction.goAddUser.详细信息：" + e.getMessage());
			throw new BkmisWebException("跳至添加操作员界面失败！,UserManagerAction.goAddUser!",e);
		}
		return mapping.findForward("add");
	}
	
	/** 
	 * houxj
	 *  添加操作员时根据所选的部门获取该部门的人员名单,用于在选择部门后级联显示该部门的人员名单 */
	public ActionForward getUserNameByDepartment(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;
		String str = "";
		List<HrPersonnel> employeeNamelist = null;
		try{
			String department = GlobalMethod.NullToSpace(request.getParameter("department"));
			employeeNamelist = userManagerServiceImpl.getUserNameByDepartment(department);
			if(employeeNamelist != null && employeeNamelist.size() != 0){
				for(HrPersonnel p:employeeNamelist){
					str += p.getPersonnelName() + ";";
				}
				out = response.getWriter();
				out.print(str);
			}
		}catch(Exception e){
			logger.error("跳至添加操作员界面失败!UserManagerAction.goAddUser.详细信息：" + e.getMessage());
			throw new BkmisWebException("跳至添加操作员界面失败！,UserManagerAction.goAddUser!",e);
		}
		return null;
	}
	
	
	/** houxj
	 * 获得修改用户所需的信息 */
	public ActionForward getModifyInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
         String sUserId = GlobalMethod.NullToSpace(request.getParameter("userId"));
         MUser user = userManagerServiceImpl.getUser(sUserId);
		 request.setAttribute("user", user);
		 try {
			   /* 获得部门类型 */
				List<SysCode> departmentlist = userManagerServiceImpl.getDepartment();
				request.setAttribute("departmentlist", departmentlist);
			}catch(Exception e){
				logger.error("跳至添加操作员界面失败!UserManagerAction.goAddUser.详细信息：" + e.getMessage());
				throw new BkmisWebException("跳至添加操作员界面失败！,UserManagerAction.goAddUser!",e);
			}

		return mapping.findForward("modify");
	}
	
	/** houxj
	 * 验证用户输入的密码是否正确 */
	public ActionForward checkPassword(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		
		PrintWriter out = null;
		String password = GlobalMethod.NullToSpace(request.getParameter("oldPassword"));
		String suserId = GlobalMethod.NullToSpace(request.getParameter("userId"));
		boolean flag;
		try{
			
			flag = userManagerServiceImpl.checkPassword(suserId,password);
			out = response.getWriter();
			out.print(flag);
		}catch(Exception e){
			logger.error("用户密码检测失败!userManagerServiceImpl.checkPassword.详细信息：" + e.getMessage());
			throw new BkmisWebException("用户密码检测失败！,UserManagerAction.checkPassword!",e);
		}
		return null;
	}
	
	/** 修改用户信息 */
	public ActionForward modifyUser(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		this.setLogParam(request);
		UserForm userForm = (UserForm)form;
		userForm.setLpId(lpId);
		Integer userId = 0;
		try{
			userId = userManagerServiceImpl.modifyUser(userForm);
			
			//加入日志的管理中
			Map map = (Map)request.getSession().getAttribute("userInfo");
			String userName = GlobalMethod.NullToSpace(map.get("username").toString());
//			writeLog(userName, "修改用户信息", "修改了用户" + roomForm.getRoomId() + "信息", Contants.BUILD);
		}catch(Exception e){
			logger.error("用户信息修改失败!userManagerServiceImpl.modifyUser.详细信息：" + e.getMessage());
			throw new BkmisWebException("用户信息修改失败！,UserManagerAction.modifyUser!",e);
		}
		request.setAttribute("flag",true);
		return mapping.findForward("modify");
	}
	//*******************************往后的都是董道奎在实现多楼盘功能的时候添加的方法***************************//
	//********************************涉及的一般都是根用户处理楼盘管理员信息的功能******************************//
	/**
	 * 获取所有楼盘的所有管理员级别的用户信息
	 */
	public ActionForward getAllManager(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			List<MUser> list = (List<MUser>)userManagerServiceImpl.getManagerUser();
			List lpList = userManagerServiceImpl.getLp();
			//取出楼盘列表的目的是，用户列表的楼盘字段显示中文。因为hql一次性关联楼盘表将楼盘的中文放入结果集比较麻烦，所以采用了现在的这种方法
			request.setAttribute("lpList", lpList);
			request.setAttribute("userManagerList", list);
		}catch(Exception e){
			logger.error("获取用户信息失败!userManagerServiceImpl.modifyUser.详细信息：" + e.getMessage());
		}
		return mapping.findForward("userManagerList");
	}
	/**
	 * 跳转到新增楼盘管理员的界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2011-6-7
	 * Time:上午11:30:28
	 */
	public ActionForward goAddManager(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			List list = userManagerServiceImpl.getLp();
			request.setAttribute("lpList", list);
		}catch(Exception e){
			logger.error("获取信息修改!userManagerServiceImpl.modifyUser.详细信息：" + e.getMessage());
			throw new BkmisWebException("用户信息修改失败！,UserManagerAction.modifyUser!",e);
		}
		return mapping.findForward("addUserManager");
	}
	/**
	 *	增加楼盘管理员
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2011-6-7 
	 * Time:上午11:48:45
	 */
	public ActionForward addManager(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			UserForm userForm = (UserForm)form;
			userManagerServiceImpl.addManager(userForm);
			request.setAttribute("flag", true);
		}catch(Exception e){
			logger.error("楼盘管理员添加失败!userManagerServiceImpl.modifyUser.详细信息：" + e.getMessage());
			throw new BkmisWebException("楼盘管理员添加失败！,UserManagerAction.modifyUser!",e);
		}
		return mapping.findForward("addUserManager");
	}
	/**
	 * 跳转到修改楼盘管理员的界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2011-6-7
	 * Time:上午11:30:28
	 */
	public ActionForward goModifyManager(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			UserForm userForm = (UserForm)form;
			userManagerServiceImpl.goModifyManager(userForm);
			request.setAttribute("lpList", userForm.getLpList());
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("modifyUserManager");
	}
	/**
	 * 修改楼盘管理员信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2011-6-7 
	 * Time:下午12:15:41
	 */
	public ActionForward modifyManager(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			UserForm userForm = (UserForm)form;
			userManagerServiceImpl.modifyManger(userForm);
		}catch(Exception e){
			logger.error("楼盘管理员信息修改失败!userManagerServiceImpl.modifyUser.详细信息：" + e.getMessage());
			throw new BkmisWebException("楼盘管理员信息修改失败！,UserManagerAction.modifyUser!",e);
		}
		return mapping.findForward("addUserManager");
		
	}
	/**
	 * 删除楼盘管理员
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Date:2011-6-7 
	 * Time:下午12:15:56
	 */
	public ActionForward deletManager(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		String userids = GlobalMethod.NullToSpace(request.getParameter("ids"));
		Map map = (Map)request.getSession().getAttribute("userInfo");
		String userName = GlobalMethod.NullToSpace(map.get("username").toString());
		//writeLog(userName, "删除角色", "所删除的角色ID为" + userid + "信息", Contants.ROLE);
		try {
			userManagerServiceImpl.deleteUser(userids,userName);
			request.setAttribute("message", "删除成功！");
		} catch (Exception e) {
			request.setAttribute("message", "删除失败！");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.getAllManager(mapping, form, request, response);
	}
	
}
