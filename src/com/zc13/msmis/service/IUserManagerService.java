package com.zc13.msmis.service;

import java.util.List;

import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.form.UserForm;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.SysCode;

public interface IUserManagerService {

	/**
	 * 返回用户列表
	 * @param userform
	 * @param isPage是否需要分页
	 * @return
	 * @throws BkmisServiceException
	 * Date:Oct 18, 2011 
	 * Time:10:57:01 AM
	 */
	public List findUser(UserForm userform,boolean isPage) throws BkmisServiceException;
	
	/** houxj 
	 * 根据系统参数里面的codevalue值得到codeName */
	public String getDepartment(String departmentCode)throws BkmisServiceException;

	/**查找符合条件的所有用户*/
	public int findUser(int range) throws BkmisServiceException;
	/**查找符合条件的所有角色*/
	public List findRole() throws BkmisServiceException;
	/**根据用户id查找用户*/
	public List findUserById(MUser mUser) throws BkmisServiceException;
	/**为用户分配角色*/
	public void saveRole(int userid,int roleid) throws BkmisServiceException;
	/** 添加操作员 */
	public void addUser(UserForm form) throws BkmisServiceException;
	
	public void deleteUser(String userid,String userName) throws BkmisServiceException;
	
	/**
	 * 设置用户的角色
	 * @param userid
	 * @return
	 */
	public List setRoleOfUser(int userid,int range) throws BkmisServiceException;
	
	/** 查询用户名是否可用 */
	public boolean checkUserName(String username) throws BkmisServiceException;
	
	/** 
	 * houxj
	 * 获得部门 */
	public List<SysCode> getDepartment() throws BkmisServiceException;
	
	/** 
	 * houxj
	 * 根据部门获取该部门的人员名单 */
	public List<HrPersonnel> getUserNameByDepartment(String department) throws BkmisServiceException;
	
	/** 获得用户修改所需的信息 */
	public MUser getUser(String sUserId) throws BkmisServiceException;
	
	/** houxj
	 * 检查密码 */
	public boolean checkPassword(String suserId,String password)throws BkmisServiceException;
	
	/**  修改用户信息 */
	public Integer modifyUser(UserForm userForm)throws BkmisServiceException;
	
	/**
	 * 根用户获取所有楼盘的所有管理员用户列表
	 * @return
	 * @throws BkmisServiceException
	 * Date:2011-6-2 
	 * Time:上午11:47:27
	 */
	public List getManagerUser() throws BkmisServiceException;
	/**
	 * 得到所有的楼盘列表
	 * @return
	 * @throws BkmisServiceException
	 * Date:2011-6-7 
	 * Time:上午11:33:33
	 */
	public List getLp() throws BkmisServiceException;
	/**
	 * 增加楼盘管理员
	 * @param userForm
	 * @throws BkmisServiceException
	 * Date:2011-6-7 
	 * Time:上午11:50:46
	 */
	public void addManager(UserForm userForm ) throws BkmisServiceException;
	/**
	 * 跳转到修改楼盘管理员的界面
	 * @throws BkmisServiceException
	 * Date:2011-6-7 
	 * Time:上午11:54:54
	 */
	public void goModifyManager(UserForm userForm) throws BkmisServiceException;
	/**
	 * 修改楼盘管理员
	 * @throws BkmisServiceException
	 * Date:2011-6-7 
	 * Time:上午11:55:30
	 */
	public void modifyManger(UserForm userForm) throws BkmisServiceException;
	/**
	 * 删除楼盘管理员
	 * @throws BkmisServiceException
	 * Date:2011-6-7 
	 * Time:上午11:56:12
	 */
	public void deleteManager(int userId) throws BkmisServiceException;
	
} 
