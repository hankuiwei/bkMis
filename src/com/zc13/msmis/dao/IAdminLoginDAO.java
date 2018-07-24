package com.zc13.msmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.BasicDAO;
import com.zc13.bkmis.mapping.AwokeTask;
import com.zc13.msmis.mapping.LimitCar;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.mapping.MUserRole;

/**
 * 管理用户登录相关
 * @author daokui
 * Date：Oct 27, 2010
 * Time：1:46:15 PM
 * houxj 改，让其继承BasicDAO
 */
public interface IAdminLoginDAO extends BasicDAO{
	
	/**
	 * 根据用户名和密码得到用户详细信息
	 * @param username
	 * @param password
	 * @return
	 */
	public MUser getUserInfo(String username,String password) throws DataAccessException;
	/**
	 * 
	 * 根据用户id得到用户角色
	 * @param userid
	 * @return
	 * @throws DataAccessException
	 */
	public MUserRole getRoleByUserID(Integer userid) throws DataAccessException;
	/**
	 * 得到属于角色id为roleid的功能树列表
	 * @param roleid
	 * @return
	 */
	public List getFundesc(Integer roleid) throws DataAccessException;
	
	/**
	 * 得到角色的任务列表
	 * @param roleid
	 * @return
	 * @throws DataAccessException
	 */
	public List<AwokeTask> getTask(Integer roleid) throws DataAccessException;
	/**
	 * 返回机动车尾号限行
	 * @return
	 * @throws DataAccessException
	 */
	public List<LimitCar> getCarList() throws DataAccessException;
}







