package com.zc13.demo.form;

import org.apache.struts.action.ActionForm;

/**
 * 登录用户验证
 * @author daokui
 * Date:Oct 25, 2010
 * Time:9:43:44 PM
 */
public class LoginForm extends ActionForm{

	
	private static final long serialVersionUID = 1L;
	
	public String name;
	public String password;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
