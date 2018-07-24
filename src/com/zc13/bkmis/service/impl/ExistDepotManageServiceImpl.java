package com.zc13.bkmis.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.zc13.bkmis.dao.IExistDepotManageDAO;
import com.zc13.bkmis.service.IExistDepotManageService;
/**
 * 
 * @author 赵玉龙
 * Date：Dec 4, 2010
 * Time：1:58:00 PM
 */
public class ExistDepotManageServiceImpl extends BasicServiceImpl implements IExistDepotManageService {
	
	Logger logger = Logger.getLogger(this.getClass());
	//从spring中得到seervice的注入
	private IExistDepotManageDAO existdepotDao;
	
	public IExistDepotManageDAO getExistdepotDao() {
		return existdepotDao;
	}
	public void setExistdepotDao(IExistDepotManageDAO existdepotDao) {
		this.existdepotDao = existdepotDao;
	}
	//获取树形图
	public List getMaterialsList() {
		
		return existdepotDao.getMaterialsList();
	}
	
}
