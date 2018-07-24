package com.zc13.msmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.dao.ISysParamManagerDAO;
import com.zc13.msmis.dao.IUserManagerDao;
import com.zc13.msmis.form.UserForm;
import com.zc13.msmis.mapping.MRole;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.MUserRole;
import com.zc13.msmis.mapping.SysCode;
import com.zc13.msmis.service.IUserManagerService;
import com.zc13.util.Contants;
import com.zc13.util.GlobalMethod;


public class UserManagerServiceImpl implements IUserManagerService {
	Logger logger = Logger.getLogger(this.getClass());

	private IUserManagerDao iUserManagerDao;
	/* 
	 * houxj
	 * 注入ISysParamManagerDAO */
	private ISysParamManagerDAO ISysParamManagerDAOImpl;
	
	public void setIUserManagerDao(IUserManagerDao userManagerDao) {
		iUserManagerDao = userManagerDao;
	}
	public ISysParamManagerDAO getISysParamManagerDAOImpl() {
		return ISysParamManagerDAOImpl;
	}

	public void setISysParamManagerDAOImpl(ISysParamManagerDAO sysParamManagerDAOImpl){
		this.ISysParamManagerDAOImpl = sysParamManagerDAOImpl;
	}

	public List findUser(UserForm userform,boolean isPage)  throws BkmisServiceException{
		List list = null;
		int totalcount = 0;
		try{
			list = iUserManagerDao.findUser(userform,isPage);
			if(isPage){
				List list2 = iUserManagerDao.findUser(userform,false);
				if(list2!=null){
					totalcount = list2.size();
				}
				userform.setTotalcount(totalcount);
			}
			userform.setUserList(list);
		}catch(Exception e){
			e.printStackTrace();
			throw new BkmisServiceException();
		}
		return list;
	}
	
	public int findUser(int range) throws BkmisServiceException {
		
		return iUserManagerDao.findUser(range);
	}

	
	public List findRole() throws BkmisServiceException{
		
		return iUserManagerDao.findRole();
	}

	
	public List findUserById(MUser mUser)throws BkmisServiceException {
		
		return iUserManagerDao.findUserById(mUser);
	}

	
	public void saveRole(int userid,int roleid)throws BkmisServiceException {
		
		MUser user = new MUser();
		MRole role = new MRole();
		user.setUserid(userid);
		role.setRoleid(roleid);
		
		MUserRole uRole = new MUserRole();
		uRole.setMRole(role);
		uRole.setMUser(user);
		iUserManagerDao.deleteUserRole(String.valueOf(userid));
		iUserManagerDao.saveRole(uRole);
        
	}

/**
 * 添加用户
 */
	public void addUser(UserForm form)throws BkmisServiceException {
		
		MUser user = new MUser();
		try {
			BeanUtils.copyProperties(user, form);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		String username = user.getUsername();
		String password = user.getPassword();
		/* houxj
		 * 使用md5进行加密 */
		String userpass = GlobalMethod.encryptPassword(username, password);
		user.setPassword(userpass);
		/* houxj改 */
		this.iUserManagerDao.saveObject(user);
		
		//添加系统日志
		try {
			//iUserManagerDao.logDetail(form.getUserName(),Contants.ADD,"","用户","2",user);
			iUserManagerDao.log(form.getUsername(), Contants.ADD, "添加了登录名为"+user.getUsername()+"的系统用户", Contants.L_SYSTEM,"用户管理");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteUser(String suserids,String userName)throws BkmisServiceException {
		/* houxj改 */
		
		if(!"".equals(suserids)){
			String[] ids = suserids.split(",");
			for(int i = 0;i<ids.length;i++){
				Integer userid = null;
				userid = Integer.parseInt(ids[i]);
				MUser mUser = (MUser)iUserManagerDao.getObject(MUser.class, userid);
				//添加系统日志
				try {
					iUserManagerDao.logDetail2(userName,Contants.DELETE,"用户管理",Contants.L_SYSTEM,"3",mUser);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				iUserManagerDao.deleteObject(mUser);
			}
	   }
	    
    }

	@Override
	public List setRoleOfUser(int userid,int range)throws BkmisServiceException {
		MRole role = iUserManagerDao.getRoleOfUser(userid);
		List list = iUserManagerDao.findRole(range);
		list.add(role);
		return list;
	}
	
	@Override
	public boolean checkUserName(String username) throws BkmisServiceException {
		
		boolean isExist = false;
		try{
			List<MUser> list = iUserManagerDao.getuser(username);
			if(list.size()!=0){
				isExist = true;
			}
		}catch (DataAccessException e) {
			logger.error("用户名检查失败！iUserManagerDao.getuser()。详细信息："+e.getMessage());
			throw new BkmisServiceException("用户名检查失败！IUserManagerService.checkUserName()");
		}
		return isExist;
	}

	@Override
	public List<SysCode> getDepartment() throws BkmisServiceException {
		
		List<SysCode> departmentlist = null;
		SysCode code = new SysCode();
		code.setCodeType("department");
		try{
			departmentlist = ISysParamManagerDAOImpl.getSysCode(code);
		}catch (DataAccessException e) {
			logger.error("获取添加操作员所需部门信息失败！ISysParamManagerDAOImpl.getCodeByDepartment()。详细信息："+e.getMessage());
			throw new BkmisServiceException("获取添加操作员所需部门信息失败！IUserManagerService.getDepartment()");
		}
		return departmentlist;
	}
	@Override
	public List<HrPersonnel> getUserNameByDepartment(String department) throws BkmisServiceException {
			
		List<HrPersonnel> employeeNamelist = null;
		try{
			if(!"".equals(department)){
//				SysCode syscode =ISysParamManagerDAOImpl.getCodeByValue(department);
//				employeeNamelist = iUserManagerDao.getEmployeeName(syscode.getCodeName());
				employeeNamelist = iUserManagerDao.getEmployeeName(department);
			}
		}catch (DataAccessException e) {
			logger.error("获取添加操作员所需指定部门的人员名单信息失败！iUserManagerDao.getEmployeeName()。详细信息："+e.getMessage());
			throw new BkmisServiceException("获取添加操作员所需指定部门的人员名单信息失败！IUserManagerService.getUserNameByDepartment()");
		}
		return employeeNamelist;
	}
	
	
	@Override
	public MUser getUser(String suserId) throws BkmisServiceException {
		
		Integer userId;
		MUser user = null;
		if(!"".equals(suserId)){
			userId = Integer.parseInt(suserId);
			user = (MUser) iUserManagerDao.getObject(MUser.class, userId);
		}
		return user;
	}
	
	@Override
	public boolean checkPassword(String suserId, String password)
			throws BkmisServiceException {

		Integer userId;
		boolean flag = false;
		MUser user = null;
		try{
			if(!"".equals(suserId)){
				userId = Integer.parseInt(suserId);
				user = (MUser) iUserManagerDao.getObject(MUser.class, userId);
			}
			
		}catch (DataAccessException e) {
			logger.error("获取用户信息失败！iUserManagerDao.getObject()。详细信息："+e.getMessage());
			throw new BkmisServiceException("获取用户信息失败！IUserManagerService.checkPassword()");
		}
		if(!"".equals(password)){
			String md5Password =  GlobalMethod.encryptPassword(user.getUsername(),password);
			if(md5Password.equals(user.getPassword())){
				flag = true;
			}else{
				flag = false;
			}
		}
		return flag;
	}
	@Override
	public Integer modifyUser(UserForm userForm) throws BkmisServiceException {
		
		Integer userId = userForm.getUserid();
		MUser user;
		String pass = userForm.getPassword();
		//加密
		if(pass.length()<31){
			pass = GlobalMethod.encryptPassword(userForm.getUsername(), userForm.getPassword());
			userForm.setPassword(pass);
		}
		try{
			user = (MUser) iUserManagerDao.getObject(MUser.class, userId);
			BeanUtils.copyProperties(user, userForm);
			iUserManagerDao.updateObject(user);
		}catch (Exception e) {
			logger.error("修改用户信息失败！iUserManagerDao.modifyUser()。详细信息："+e.getMessage());
			throw new BkmisServiceException("修改用户信息失败！IUserManagerService.modifyUser()");
		}
		return userId;
	}
	@Override
	public String getDepartment(String departmentCode)
			throws BkmisServiceException {
		
		String department;
		SysCode syscode = ISysParamManagerDAOImpl.getCodeByValue(departmentCode);
		department = syscode.getCodeName();
		return department;
	}
	@Override
	public List getManagerUser() throws BkmisServiceException {
		// TODO Auto-generated method stub
		
		String hql = "select u from MUser as u left join u.MUserRoles as ur left join ur.MRole as r where r.rolename='Administrator' ";
		
		return ISysParamManagerDAOImpl.findObject(hql);
	}
	@Override
	public List getLp() throws BkmisServiceException {
		// TODO Auto-generated method stub
		String hql = "from ELp";
		
		return ISysParamManagerDAOImpl.findObject(hql);
	}
	@Override
	public void addManager(UserForm userForm) throws BkmisServiceException {
		// TODO Auto-generated method stub
		
		MUser mUser = new MUser();
		try {
			BeanUtils.copyProperties(mUser, userForm);
			//MD5加密
			String userpass = GlobalMethod.encryptPassword(userForm.getUsername(), userForm.getPassword());
			mUser.setPassword(userpass);
			ISysParamManagerDAOImpl.saveObject(mUser);
			int i=mUser.getUserid();
			List list = ISysParamManagerDAOImpl.findObject("from MRole where rolename = 'Administrator'");
			MRole mRole = (MRole)list.get(0);
			//保存好用户以后，因为这里的用户默认为楼盘管理员，角色已经确定无须用户手动设置，所以下面就直接在后台给分配了默认角色了。
			saveRole(i,mRole.getRoleid());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void goModifyManager(UserForm userForm) throws BkmisServiceException {
		
		userForm.setLpList(getLp());
		MUser mUser = (MUser)ISysParamManagerDAOImpl.getObject(MUser.class, userForm.getUserid());
		try {
			BeanUtils.copyProperties(userForm, mUser);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}
	@Override
	public void modifyManger(UserForm userForm) throws BkmisServiceException {
		// TODO Auto-generated method stub
		MUser mUser = new MUser();
		try {
			BeanUtils.copyProperties(mUser, userForm);
			ISysParamManagerDAOImpl.updateObject(mUser);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void deleteManager(int userId) throws BkmisServiceException {
		// TODO Auto-generated method stub
		MUser mUser = (MUser)ISysParamManagerDAOImpl.getObject(MUser.class, userId);
		ISysParamManagerDAOImpl.deleteObject(mUser);
	}
	

}
