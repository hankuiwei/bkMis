package com.zc13.bkmis.service;

import com.zc13.bkmis.form.WorkTaskForm;
import com.zc13.exception.BkmisServiceException;

/**
 * 
 * @author daokui
 * @Date Mar 22, 2011
 * @Time 10:22:31 AM
 */
public interface IWorkTaskService {
	
	/**
	 * 获取所有的正式入驻的客户的工作任务单
	 * @param workForm
	 * @throws BkmisServiceException
	 */
	public void getWorkTaskList(WorkTaskForm workForm) throws BkmisServiceException;
	/**
	 * 修改记录
	 * @param workForm
	 * @throws BkmisServiceException
	 */
	public void modify(WorkTaskForm workForm) throws BkmisServiceException;
}
