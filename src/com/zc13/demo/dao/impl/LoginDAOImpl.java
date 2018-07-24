package com.zc13.demo.dao.impl;

import org.hibernate.Query;

import com.zc13.bkmis.dao.impl.BasicDAOImpl;
import com.zc13.demo.dao.ILoginDAO;
import com.zc13.demo.form.LoginForm;

public class LoginDAOImpl extends BasicDAOImpl implements ILoginDAO{
	
	public boolean login (LoginForm loginForm){
		
		boolean b = false;
		String hql = " from UserLogin where u_id = ? and u_pw = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, loginForm.getName());
		query.setParameter(1, loginForm.getPassword());
		
		if(query.list().size()>0){
			b = true;
		}
		return b;
	}
	
	
}
