package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.CostTypeForm;
import com.zc13.bkmis.mapping.CCostparatype;
import com.zc13.bkmis.mapping.CCosttype;
import com.zc13.msmis.mapping.SysCode;

/**
 * 费用类型维护相关
 * @author 王正伟
 * Date：Nov 26, 2010
 * Time：2:02:43 PM
 */
public interface ICostTypeService {
	/**
	 * 获得费用类型列表
	 * @return
	 * @throws Exception
	 */
	public List<CCosttype> getCostTypeList(CostTypeForm form) throws Exception;
	
	/**
	 * 保存或更新费用类型
	 * @param form
	 * @throws Exception
	 */
	public void saveOrUpdateCostType(CostTypeForm form) throws Exception;
	
	/**
	 * 删除费用类型
	 * @param form
	 * @throws Exception
	 */
	public void deleteCostType(CostTypeForm form) throws Exception;

	/**
	 * 获得表具列表
	 * @return
	 */
	public List<SysCode> getGaugeList() throws Exception;

	/**
	 * 获得收费参数类型列表
	 * @return
	 */
	public List<CCostparatype> getCostparatypeList() throws Exception;
}
