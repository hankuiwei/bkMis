package com.zc13.demo.service;

import com.zc13.demo.form.LoginForm;
/**
 * 用户登录业务逻辑
 * @author daokui
 * Date:Oct 25, 2010
 * Time:9:59:04 PM
 */
public interface ILoginService {
	/**
	 * 登录信息验证
	 * @param loginForm
	 * @return
	 */
	public boolean login (LoginForm loginForm);
}
