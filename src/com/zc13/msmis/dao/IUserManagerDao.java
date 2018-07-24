package com.zc13.msmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.BasicDAO;
import com.zc13.bkmis.mapping.HrPersonnel;
import com.zc13.msmis.form.UserForm;
import com.zc13.msmis.mapping.MRole;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.MUserRole;

public interface IUserManagerDao extends BasicDAO{
	
	/**
	 * 获取用户列表
	 * @param userform
	 * @param isPage 是否分页
	 * @return
	 * Date:Oct 18, 2011 
	 * Time:11:03:36 AM
	 */
	public List findUser(UserForm userform,boolean isPage);
	
	public int findUser(int range);
	
	public List findRole();
	
	public void saveRole(MUserRole mUserRole);
	
	public List findUserById(MUser mUser);
	
	public void addUser(MUser mUser);
	
	
	/**
	 * 获得比用户角色低的角色信息
	 * 
	 * @param range
	 * @return
	 */
	public List findRole(int range);
	
	/**
	 * 获得用户的角色名
	 * @param userid
	 * @return
	 */
	public MRole getRoleOfUser(int userid);
	
	/**
	 * 通过用户名查找
	 * 
	 * @param username
	 * @return
	 */
	public List<MUser> getuser(String username);
	/**
	 * houxj
	 * 根据部门查询员工信息
	 */
	public List<HrPersonnel> getEmployeeName(String department) throws DataAccessException;
	/**
	 * 维护用户角色，修改之前先删除
	 * @throws DataAccessException
	 */
	public void deleteUserRole(String userId) throws DataAccessException;
}
