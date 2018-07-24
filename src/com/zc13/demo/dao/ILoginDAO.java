package com.zc13.demo.dao;

import com.zc13.demo.form.LoginForm;
/**
 * 用户登录DAO
 * @author daokui
 * Date:Oct 25, 2010
 * Time:10:03:17 PM
 */
public interface ILoginDAO {
	/**
	 * 登录信息验证
	 * @param loginForm
	 * @return
	 */
	public boolean login (LoginForm loginForm);
}
