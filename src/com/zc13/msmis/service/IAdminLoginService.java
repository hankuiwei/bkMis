package com.zc13.msmis.service;

import java.sql.SQLException;

import com.zc13.msmis.form.AdminLoginForm;
/**
 * 管理用户登录
 * @author daokui
 * Date：Oct 27, 2010
 * Time：1:08:38 PM
 */
public interface IAdminLoginService {
	/**
	 * 用户登录及权限设置
	 * @param username
	 * @param password
	 * @throws SQLException
	 */
	public void login(AdminLoginForm adminLoginForm) throws Exception ;
	

}
