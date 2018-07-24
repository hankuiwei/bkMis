package com.zc13.msmis.service;

import java.util.List;

import com.zc13.msmis.mapping.SysLog;

public interface ILogManagerService {
	
	public List<SysLog> query(SysLog sysLog,String currentpage,String pagesize);
	
	public int queryCount(SysLog sysLog);
	
	public SysLog findbyID(int id);
}
