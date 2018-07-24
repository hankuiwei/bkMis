package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.MerchantsActualForm;
import com.zc13.bkmis.mapping.MerchantsActual;
import com.zc13.msmis.mapping.SysCode;

public interface IMerchantsActualDAO  extends BasicDAO {
	
	/**
	 * 查询报修项目列表
	 * @param repairForm
	 * @param isPage 是否分页
	 * @return
	 * @throws DataAccessException
	 */
	public List<MerchantsActual> queryActualResult(MerchantsActualForm MerchantsActualForm,boolean isPage) throws DataAccessException ;
	public int queryCounttotal(MerchantsActualForm MerchantsActualForm);

	/**
	 * 查询计划分配人
	 * @return
	 * Date:2013-5-8 
	 * Time:下午10:26:04
	 */
	public List<SysCode> queryCodeByType();
}
