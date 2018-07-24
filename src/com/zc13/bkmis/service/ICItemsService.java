package com.zc13.bkmis.service;

import com.zc13.bkmis.form.CItemsForm;
import com.zc13.util.PageBean;

public interface ICItemsService extends IBasicService {
	
	public PageBean getCItems(CItemsForm form) throws Exception;
	
	public void saveItems(CItemsForm form) throws Exception;
	
	public void deleteItems(CItemsForm form) throws Exception;
}
