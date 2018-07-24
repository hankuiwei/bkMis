package com.zc13.bkmis.service;

import com.zc13.bkmis.form.AwokeTaskForm;
import com.zc13.exception.BkmisServiceException;

/**
 * 代办任务service
 * @author wangzw
 * @Date May 17, 2011
 * @Time 4:10:54 PM
 */
public interface IAwokeTaskService  extends IBasicService{
	
	/**
	 * 更新代办任务数量
	 * @param form
	 * @throws BkmisServiceException
	 * Date:May 17, 2011 
	 * Time:4:17:59 PM
	 */
	public void updateAwokeTaskAmount(AwokeTaskForm form) throws BkmisServiceException;
}
