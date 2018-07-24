package com.zc13.msmis.dao;

import java.util.List;

import com.zc13.bkmis.dao.BasicDAO;
import com.zc13.msmis.mapping.SysLog;

/**
 * 
 * @author 秦彦韬
 * Date：Nov 30, 2010
 * Time：11:37:44 AM
 */
public interface ILogManagerDAO extends BasicDAO{
	//查询日志列表
	public List<SysLog> query(SysLog sysLog,String currentpage,String pagesize);
	//查询日志条数
	public List<SysLog> queryCount(SysLog sysLog);
}
