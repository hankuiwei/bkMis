package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.IDepotSupplierDAO;
import com.zc13.bkmis.form.DepotSupplierForm;
import com.zc13.bkmis.mapping.DepotSupplier;
import com.zc13.bkmis.service.IDepotSupplierService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.Contants;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 21, 2010
 * Time：3:22:33 PM
 */
public class DepotSupplierServiceImpl extends BasicServiceImpl implements IDepotSupplierService {

	Logger logger = Logger.getLogger(this.getClass());
	/** 对idepotSupplierDao 注入*/
	private IDepotSupplierDAO isupplierDAO;
	
	public IDepotSupplierDAO getIsupplierDAO() {
		return isupplierDAO;
	}
	public void setIsupplierDAO(IDepotSupplierDAO isupplierDAO) {
		this.isupplierDAO = isupplierDAO;
	}

	//查询显示供应商信息
	public void selectSupplierList(DepotSupplierForm dsf) throws DataAccessException {
		
		List list = new ArrayList();
		try{
			list = isupplierDAO.selectSupplierList(dsf);
		}catch(Exception e){
			logger.error("查询显示供应商信息失败！DepotSupplierServiceImpl.selectSupplierList()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询显示供应商信息失败！DepotSupplierServiceImpl.selectSupplierList()");
		}
		dsf.setSupplierList(list);
	}
	//查询显示供应商信息记录条数用于分页
	public int queryCount(DepotSupplierForm dsf) throws DataAccessException {
		
		int count = 0;
		try{
			count = isupplierDAO.queryCount(dsf);
		}catch(Exception e){
			logger.error("查询显示供应商信息条数失败！DepotSupplierServiceImpl.queryCount()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询显示供应商信息条数失败！DepotSupplierServiceImpl.queryCount()");
		}
		return count;
	}
	//执行添加供应商信息
	public void addSupplier(DepotSupplierForm dsf) throws DataAccessException {
		
		DepotSupplier ds = new DepotSupplier();
		try {
			BeanUtils.copyProperties(ds,dsf);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		try{
			isupplierDAO.saveObject(ds);
		}catch(Exception e){
			logger.error("添加供应商信息失败！DepotSupplierServiceImpl.addSupplier()。详细信息："+e.getMessage());
			throw new BkmisServiceException("添加供应商信息失败！DepotSupplierServiceImpl.addSupplier()");
		}
	}
	//查询要修改的供应商信息
	public void editSupplier(DepotSupplierForm dsf) throws DataAccessException {
		
		int id = dsf.getId();
		List list = new ArrayList();
		try{
			list = isupplierDAO.editSupplier(id);
		}catch(Exception e){
			logger.error("查询要修改供应商信息失败！DepotSupplierServiceImpl.editSupplier()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询要修改供应商信息失败！DepotSupplierServiceImpl.editSupplier()");
		}
		dsf.setEditSuplierList(list);
	}
	//执行修改操作
	public void doEditSupplier(DepotSupplierForm form) {
		
		DepotSupplier ds = new DepotSupplier();
		try {
			BeanUtils.copyProperties(ds,form);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		try{
			//写入系统日志
			isupplierDAO.logDetail(form.getUserName(), Contants.MODIFY,"供应商管理",Contants.L_Depot, "1", ds);
			isupplierDAO.saveOrUpdateObject(ds);
		}catch(Exception e){
			logger.error("修改供应商信息失败！DepotSupplierServiceImpl.doEditSupplier()。详细信息："+e.getMessage());
			throw new BkmisServiceException("修改供应商信息失败！DepotSupplierServiceImpl.doEditSupplier()");
		}
	}
	//按id删除信息
	public void delSupplier(int id) {
		
		DepotSupplier ds = new DepotSupplier();
		ds.setId(id);
		try{
			isupplierDAO.deleteObject(ds);
		}catch(Exception e){
			logger.error("删除供应商信息失败！DepotSupplierServiceImpl.delSupplier()。详细信息："+e.getMessage());
			throw new BkmisServiceException("删除供应商信息失败！DepotSupplierServiceImpl.delSupplier()");
		}
	}
	//按id查询要删除的供应商的信息
	public List<DepotSupplier> selectSupplier(Integer[] idArray) {
		
		List list = new ArrayList();
		try{
			list = isupplierDAO.selectSupplier(idArray);
		}catch (Exception e) {
			logger.error("查询供应商信息失败！DepotSupplierServiceImpl.selectSupplier()。详细信息："+e.getMessage());
			throw new BkmisServiceException("查询供应商信息失败！DepotSupplierServiceImpl.selectSupplier()");
		}
		return list;
	}
}
