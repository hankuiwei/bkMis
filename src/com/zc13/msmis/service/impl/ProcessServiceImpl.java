package com.zc13.msmis.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.zc13.bkmis.mapping.AwokeTask;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.dao.IAdminLoginDAO;
import com.zc13.msmis.service.ProcessService;

public class ProcessServiceImpl implements ProcessService {
Logger logger = Logger.getLogger(this.getClass());	
	
	IAdminLoginDAO adminLoginDAOImpl;

	public IAdminLoginDAO getAdminLoginDAOImpl() {
		return adminLoginDAOImpl;
	}
	public void setAdminLoginDAOImpl(IAdminLoginDAO adminLoginDAOImpl) {
		this.adminLoginDAOImpl = adminLoginDAOImpl;
	}
	@Override
	public List getTask(Integer userRoleId) throws BkmisServiceException {
		// TODO Auto-generated method stub
		
		List<AwokeTask> tipsList = adminLoginDAOImpl.findObject("from AwokeTask where mapurl is not null order by sequence");
		return tipsList;
	}

}
