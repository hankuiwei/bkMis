package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.MerForm;
/***
 * @author qinyantao
 * Date：Dec 7, 2010
 * Time：13:35:50 PM
 */
public interface IMerchantsAandPService extends IBasicService {
	

	/**
	 * 列表统计数
	 * @param finForm
	 * @return
	 * Date:2013-3-30 
	 * Time:上午9:52:01
	 */
	public int queryCounttotal(MerForm merForm);
	
	/**
	 * 列表数据
	 * @param finForm
	 * @return
	 * Date:2013-3-30 
	 * Time:上午9:52:15
	 */
	public List getAandBList (MerForm merForm);
}
