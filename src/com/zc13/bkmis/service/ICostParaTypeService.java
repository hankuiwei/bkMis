package com.zc13.bkmis.service;

import java.util.List;

import com.zc13.bkmis.form.CostParaTypeForm;
import com.zc13.bkmis.mapping.CCostparatype;

/**
 * 计费参数类型维护相关
 * @author 王正伟
 * Date：Nov 26, 2010
 * Time：2:02:43 PM
 */
public interface ICostParaTypeService {
	/**
	 * 获得计费参数类型列表
	 * @return
	 * @throws Exception
	 */
	public List<CCostparatype> getCostParaTypeList(CostParaTypeForm form) throws Exception;
	
	/**
	 * 保存或更新计费参数类型
	 * @param form
	 * @throws Exception
	 */
	public void saveOrUpdateCostParaType(CostParaTypeForm form) throws Exception;
	
	/**
	 * 删除计费参数类型
	 * @param form
	 * @throws Exception
	 */
	public void deleteCostParaType(CostParaTypeForm form) throws Exception;

}
