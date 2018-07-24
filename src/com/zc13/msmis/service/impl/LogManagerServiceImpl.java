package com.zc13.msmis.service.impl;


import java.util.List;

import com.zc13.bkmis.service.impl.BasicServiceImpl;
import com.zc13.exception.BkmisServiceException;
import com.zc13.msmis.dao.ILogManagerDAO;
import com.zc13.msmis.mapping.SysLog;
import com.zc13.msmis.service.ILogManagerService;

public class LogManagerServiceImpl extends BasicServiceImpl implements
		ILogManagerService {

	private ILogManagerDAO ILogManagerDAO;

	public void setILogManagerDAO(ILogManagerDAO logManagerDAO) {
		ILogManagerDAO = logManagerDAO;
	}

	public List<SysLog> query(SysLog sysLog,String currentpage,String pagesize) {
		
		return ILogManagerDAO.query(sysLog,currentpage,pagesize);
	}

	@Override
	public int queryCount(SysLog sysLog) {
		List<SysLog> list = ILogManagerDAO.queryCount(sysLog);
		if(list==null){
			return 0;
		}else{
			return list.size();
		}
	}

	@Override
	public SysLog findbyID(int id) throws BkmisServiceException{
		// TODO Auto-generated method stub
		SysLog sysLog = (SysLog)ILogManagerDAO.getObject(SysLog.class, id);
		return sysLog;
	}
	
}
