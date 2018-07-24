package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.WorkTaskForm;

public interface IWorkTaskDAO extends BasicDAO{

	/**
	 * 获取所有的正式入驻的客户的工作任务单
	 * @return
	 * @throws DataAccessException
	 */
	public List getWorkTaskList(WorkTaskForm workForm) throws DataAccessException;
}
