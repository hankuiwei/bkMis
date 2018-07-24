package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.CItemsForm;
import com.zc13.bkmis.mapping.CItems;
import com.zc13.util.PageBean;

public interface ICItemsDAO extends BasicDAO {
	/**
	 * 获取收费项列表
	 * @param form
	 * @param isShowSystemItems 是否获取系统收费项
	 * @return
	 * @throws DataAccessException
	 */
	public List getItemsList(CItemsForm form,boolean isShowSystemItems) throws DataAccessException ;
	
	public PageBean getCItems(CItemsForm form)throws DataAccessException;
	
	public void saveItems(CItems items) throws DataAccessException ;
	
	public void updateItems(CItems items) throws DataAccessException ;
	
	public void deleteItems(CItems items) throws DataAccessException ;
	
}
