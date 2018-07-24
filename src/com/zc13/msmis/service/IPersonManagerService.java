package com.zc13.msmis.service;

import com.zc13.bkmis.service.IBasicService;
import com.zc13.msmis.mapping.MUser;

public interface IPersonManagerService extends IBasicService {
	
	/**
	 * 通过id得到用户信息
	 * 
	 * @param userid
	 * @return
	 */
	public MUser getUser(int userid) throws Exception;
	
	/**
	 * 保存用户信息
	 * @param password
	 * @param user
	 * @throws Exception
	 */
	public void updateUser(MUser user,String password) throws Exception;
	
	

}
