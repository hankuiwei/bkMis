package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.zc13.bkmis.dao.IWorkTaskDAO;
import com.zc13.bkmis.form.WorkTaskForm;
import com.zc13.bkmis.mapping.CompactTask;
import com.zc13.bkmis.service.IWorkTaskService;
import com.zc13.exception.BkmisServiceException;

public class WorkTaskServiceImpl implements IWorkTaskService {

	IWorkTaskDAO workTaskDAOImpl;
	
	public IWorkTaskDAO getWorkTaskDAOImpl() {
		return workTaskDAOImpl;
	}

	public void setWorkTaskDAOImpl(IWorkTaskDAO workTaskDAOImpl) {
		this.workTaskDAOImpl = workTaskDAOImpl;
	}

	public void getWorkTaskList(WorkTaskForm workForm) throws BkmisServiceException {
		
		List list = workTaskDAOImpl.getWorkTaskList(workForm);
		workForm.setWorkTaskList(list);
	}

	@Override
	/**
	 * 修改
	 */
	public void modify(WorkTaskForm workForm) throws BkmisServiceException {
		// TODO Auto-generated method stub
		
		CompactTask compactTask = new CompactTask();
		try {
			BeanUtils.copyProperties(compactTask, workForm);
			workTaskDAOImpl.updateObject(compactTask);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
