package com.zc13.msmis.service.impl;


import org.apache.log4j.Logger;

import com.zc13.bkmis.service.impl.BasicServiceImpl;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.mapping.MUser;
import com.zc13.msmis.service.IPersonManagerService;
import com.zc13.util.GlobalMethod;

public class PersonManagerServiceImpl extends BasicServiceImpl implements
		IPersonManagerService {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public MUser getUser(int userid) throws Exception {
		try{
			return (MUser)getObject(MUser.class, userid);
		}catch (Exception e) {
			logger.error("用户个人信息加载失败！PersonManagerServiceImpl.getUser()。详细信息："+e.getMessage());
			throw new BkmisServiceException("用户个人信息加载失败！PersonManagerServiceImpl.getUser()");
		}
		
	}

	@Override
	public void updateUser(MUser user, String password) throws Exception {
		
		try{
			if(password == null || password.equals("")){
				saveOrUpdateObject(user);
			}else{
				password = GlobalMethod.encryptPassword(user.getUsername(), password);
				user.setPassword(password);
				saveOrUpdateObject(user);
			}
		}catch (Exception e) {
			logger.error("更新个人信息加载失败！PersonManagerServiceImpl.saveUser()。详细信息："+e.getMessage());
			throw new BkmisServiceException("更新个人信息加载失败！PersonManagerServiceImpl.saveUser()");
		}
	}

	
}
