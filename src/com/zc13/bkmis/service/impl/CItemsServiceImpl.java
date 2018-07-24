package com.zc13.bkmis.service.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.dao.ICItemsDAO;
import com.zc13.bkmis.form.CItemsForm;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.bkmis.service.ICItemsService;
import com.zc13.exception.BkmisServiceException;
import com.zc13.util.ExtendUtil;
import com.zc13.util.PageBean;
/**
 * @author zhaosg
 * Date：Nov 26, 2010
 * Time：10:55:10 AM
 */
public class CItemsServiceImpl extends BasicServiceImpl implements
		ICItemsService {
	
	Logger logger = Logger.getLogger(this.getClass());
	private ICItemsDAO icItemsDao;

	public ICItemsDAO getIcItemsDao() {
		return icItemsDao;
	}

	public void setIcItemsDao(ICItemsDAO icItemsDao) {
		this.icItemsDao = icItemsDao;
	}

	public PageBean getCItems(CItemsForm form) throws Exception{
		PageBean page = null;
		if (ExtendUtil.checkNull(form.getPagesize())) {
			form.setPagesize(5);
		}		try{
			page = icItemsDao.getCItems(form);
		}catch(Exception e){
			logger.error("收费项信息查询失败!CItemsServiceImpl.getItemsList()。详细信息：" + e.getMessage());
			throw new BkmisServiceException("收费项信息查询失败!CItemsServiceImpl.getItemsList()。");
		}
		return page;
	}
	/**
	 * 保存或更新
	 */
	public void saveItems(CItemsForm form) throws Exception{
		CItems items = new CItems();
		try {
			BeanUtils.copyProperties(items, form);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(form.getId()==null||form.getId()==0){
			try {
				icItemsDao.saveItems(items);
			} catch (DataAccessException e) {
				logger.error("收费项信息添加失败!CItemsServiceImpl.saveItems()。详细信息：" + e.getMessage());
				throw new BkmisServiceException("收费项信息添加失败!CItemsServiceImpl.saveItems()。");
			}
		}else if(items.getId().intValue()>0){
			try {
				icItemsDao.updateItems(items);
			} catch (DataAccessException e) {
				logger.error("收费项信息更新失败!CItemsServiceImpl.saveItems()。详细信息：" + e.getMessage());
				throw new BkmisServiceException("收费项信息更新失败!CItemsServiceImpl.saveItems()。");
			}
		}
		
		
	}
	/**
	 * 删除
	 */
	public void deleteItems(CItemsForm form) throws Exception{
		Integer[] id = form.getCheckbox(); 
		if (id!=null) {
			for (int i = 0; i < id.length; i++) {
				CItems items = new CItems();
				items.setId(id[i]);
				try {
					icItemsDao.deleteItems(items);
				} catch (DataAccessException e) {
					logger.error("收费项信息删除失败!CItemsServiceImpl.deleteItems()。详细信息：" + e.getMessage());
					throw new BkmisServiceException("收费项信息删除失败!CItemsServiceImpl.deleteItems()。");
				}
			}
		}
	}
}
