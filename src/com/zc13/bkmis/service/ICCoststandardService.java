package com.zc13.bkmis.service;

import com.zc13.bkmis.form.CCoststandardForm;
import com.zc13.util.PageBean;

public interface ICCoststandardService extends IBasicService {
	
	public PageBean getCCoststandardList(CCoststandardForm form) throws Exception;
	
	public void saveCCoststandard(CCoststandardForm form) throws Exception;

	public void deleteCCoststandard(CCoststandardForm form) throws Exception;
	
}
