package com.zc13.demo.service.impl;

import com.zc13.demo.dao.impl.LoginDAOImpl;
import com.zc13.demo.form.LoginForm;

public class LoginServiceImpl {
	
	LoginDAOImpl loginDAOImpl;

	public LoginDAOImpl getLoginDAOImpl() {
		return loginDAOImpl;
	}
	public void setLoginDAOImpl(LoginDAOImpl loginDAOImpl) {
		this.loginDAOImpl = loginDAOImpl;
	}
	

	public boolean login(LoginForm loginForm){
		
		boolean b = false;
		b = loginDAOImpl.login(loginForm);
		
		return b;
	}




}
