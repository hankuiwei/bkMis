package com.zc13.msmis.service;

import java.util.List;

import com.zc13.exception.BkmisServiceException;

public interface ProcessService  {

	/**
	 * 取得当前用户角色的所有任务
	 * @param userRoleId
	 * @return
	 * @throws BkmisServiceException
	 * Date:2011-5-24
	 * Time:上午11:02:21
	 */
	public List getTask(Integer userRoleId) throws BkmisServiceException; 
}
