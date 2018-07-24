package com.zc13.bkmis.dao;

import java.util.List;

import com.zc13.bkmis.form.MerForm;

public interface IMerchantsAandPDAO  extends BasicDAO {
	
	/**
	 * 查询列表
	 * @param finForm
	 * @return
	 * Date:2013-3-30 
	 * Time:上午9:55:03
	 */
	public List queryAandB(MerForm merForm);
	
	/**
	 * 列表数据总数
	 * @param finForm
	 * @return
	 * Date:2013-3-30 
	 * Time:上午9:55:15
	 */
	public int queryCounttotal(MerForm merForm);

}
