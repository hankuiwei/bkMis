package com.zc13.bkmis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zc13.bkmis.form.MerchantsPlanForm;
import com.zc13.bkmis.mapping.MerchantsPlan;
import com.zc13.msmis.mapping.SysCode;

public interface IMerchantsPlanDAO  extends BasicDAO {
	
	/**
	 * 查询报修项目列表
	 * @param repairForm
	 * @param isPage 是否分页
	 * @return
	 * @throws DataAccessException
	 */
	public List<MerchantsPlan> queryPlanResult(MerchantsPlanForm merchantsPlanForm,boolean isPage) throws DataAccessException ;
	public int queryCounttotal(MerchantsPlanForm merchantsPlanForm);

	/**
	 * 查询计划分配人
	 */
	public List<SysCode> queryCodeByType();
}
