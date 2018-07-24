package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.FinForm;

/***
 * @author 李影
 */
public interface IFinancialAandBService extends IBasicService {
	

	/**
	 * 列表统计数
	 * @param finForm
	 * @return
	 * Date:2013-3-30 
	 * Time:上午9:52:01
	 */
	public int queryCounttotal(FinForm finForm);
	
	/**
	 * 列表数据
	 * @param finForm
	 * @return
	 * Date:2013-3-30 
	 * Time:上午9:52:15
	 */
	public List getAandBList (FinForm finForm);
	

}
